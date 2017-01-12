/*
 * Copyright (C) 2011 University of Washington
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.odk.collect.android.cfs.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.javarosa.core.model.data.IAnswerData;
import org.javarosa.form.api.FormEntryPrompt;
import org.odk.collect.android.cfs.R;
import org.odk.collect.android.cfs.application.Collect;
import org.odk.collect.android.cfs.utilities.PropertiesUtils;
import org.odk.collect.android.cfs.views.MediaLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SuppressLint("NewApi")
public abstract class QuestionWidget extends LinearLayout {

    @SuppressWarnings("unused")
    private final static String t = "QuestionWidget";

    private static int idGenerator = 1211322;
    protected final int mQuestionFontsize;
    protected final int mAnswerFontsize;
    protected FormEntryPrompt mPrompt;
    private LinearLayout.LayoutParams mLayout;
    private LayoutParams qLayout;
    private TextView mQuestionText;
    private MediaLayout mediaLayout;
    private TextView mHelpText;

    private static int iQuestion;

    public QuestionWidget(Context context, FormEntryPrompt p) {
        super(context);

        mQuestionFontsize = Collect.getQuestionFontsize();
        mAnswerFontsize = mQuestionFontsize + 2;

        mPrompt = p;

        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.TOP);
        setPadding(0, 7, 0, 0); // place from the top seng added

//        LinearLayout spacing = new LinearLayout(context);
//		spacing.setPadding(0, 40, 0, 40);
//		addView(spacing);

        mLayout =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        mLayout.setMargins(10, 0, 10, 0);


        qLayout = mLayout;
        //Add a line
        View ruler = new View(getContext()); ruler.setBackgroundColor(getResources().getColor(R.color.text_link));
        LinearLayout.LayoutParams rulerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 5);
        rulerParams.setMargins(0, 15, 0, 15);
        addView(ruler, rulerParams);
        //End a line
        if (p.getQuestionText().length()>0){
            iQuestion += 1;
        }
        //if ((iQuestion % 2) ==1) setBackgroundColor(getResources().getColor(R.color.light_gray));
        addQuestionText(p);
        addHelpText(p);
    }

    /**
     * Generate a unique ID to keep Android UI happy when the screen orientation
     * changes.
     *
     * @return
     */
    public static int newUniqueId() {
        return ++idGenerator;
    }

    //Add by Bunhann
    public static String getFieldID(String refFieldString) {

        String fieldID = "";
        if (null != refFieldString && refFieldString.length() > 0) {
            String[] mySplit = refFieldString.split("/");
            fieldID = mySplit[mySplit.length - 1];
        }
        return fieldID;
    }

    public void playAudio() {
        mediaLayout.playAudio();
    }

    public void playVideo() {
        mediaLayout.playVideo();
    }

    public FormEntryPrompt getPrompt() {
        return mPrompt;
    }

    // http://code.google.com/p/android/issues/detail?id=8488
    private void recycleDrawablesRecursive(ViewGroup viewGroup, List<ImageView> images) {

        int childCount = viewGroup.getChildCount();
        for (int index = 0; index < childCount; index++) {
            View child = viewGroup.getChildAt(index);
            if (child instanceof ImageView) {
                images.add((ImageView) child);
            } else if (child instanceof ViewGroup) {
                recycleDrawablesRecursive((ViewGroup) child, images);
            }
        }
        viewGroup.destroyDrawingCache();
    }

    // http://code.google.com/p/android/issues/detail?id=8488
    public void recycleDrawables() {
        List<ImageView> images = new ArrayList<ImageView>();
        // collect all the image views
        recycleDrawablesRecursive(this, images);
        for (ImageView imageView : images) {
            imageView.destroyDrawingCache();
            Drawable d = imageView.getDrawable();
            if (d != null && d instanceof BitmapDrawable) {
                imageView.setImageDrawable(null);
                BitmapDrawable bd = (BitmapDrawable) d;
                Bitmap bmp = bd.getBitmap();
                if (bmp != null) {
                    bmp.recycle();
                }
            }
        }
    }

    // Abstract methods
    public abstract IAnswerData getAnswer();

    public abstract void clearAnswer();

    public abstract void setFocus(Context context);

    public abstract void setOnLongClickListener(OnLongClickListener l);

    /**
     * Override this to implement fling gesture suppression (e.g. for embedded WebView treatments).
     *
     * @param e1
     * @param e2
     * @param velocityX
     * @param velocityY
     * @return true if the fling gesture should be suppressed
     */
    public boolean suppressFlingGesture(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    /**
     * Add a Views containing the question text, audio (if applicable), and image (if applicable).
     * To satisfy the RelativeLayout constraints, we add the audio first if it exists, then the
     * TextView to fit the rest of the space, then the image if applicable.
     */
    protected void addQuestionText(FormEntryPrompt p) {
        String imageURI = p.getImageText();
        String audioURI = p.getAudioText();
        String videoURI = p.getSpecialFormQuestionText("video");

        // shown when image is clicked
        String bigImageURI = p.getSpecialFormQuestionText("big-image");

        String promptText;
        String pText = p.getLongText();

        //Check The Note Label Question Start With ($)
        if (pText.startsWith("$")) {
            StringBuilder sb = new StringBuilder(pText);
            promptText = sb.deleteCharAt(0).toString();
        } else {
            promptText = pText;
        }

        // Add the text view. Textview always exists, regardless of whether there's text.
        mQuestionText = new TextView(getContext());
        mQuestionText.setText(promptText == null ? "" : promptText);
        mQuestionText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mQuestionFontsize + 3);
        mQuestionText.setTypeface(null, Typeface.BOLD);
        mQuestionText.setPadding(0, 0, 0, 10);
        mQuestionText.setId(QuestionWidget.newUniqueId()); // assign random id
        //seng added
        mQuestionText.setLayoutParams(qLayout);
        // Wrap to the size of the parent view
        mQuestionText.setHorizontallyScrolling(false);

        if (promptText == null || promptText.length() == 0) {
            mQuestionText.setVisibility(GONE);
        }
        String fieldElementReference = p.getFormElement().getBind().getReference().toString();
        String fieldName = getFieldID(fieldElementReference);



       /* if (fieldName.equalsIgnoreCase("other_purpose")) {
            mQuestionText.setVisibility(GONE);
            PropertiesUtils.setiAnswerOtherPurpose(0);
            PropertiesUtils.setTextViewOtherPurpose(mQuestionText);
        }*/
        if (fieldName.equalsIgnoreCase("other_activity")) {
            if(PropertiesUtils.getiAnswerOtherActivity() == 0){
                mQuestionText.setVisibility(GONE);
            } else {
                mQuestionText.setVisibility(VISIBLE);
            }
            PropertiesUtils.setTextViewOtherActivity(mQuestionText);
        }
        //For testing
//        if (fieldName.equals("student1a")){
//            mQuestionText.setTextColor(getResources().getColor(R.color.text_link));
//            mQuestionText.setTypeface(mQuestionText.getTypeface(), Typeface.BOLD);
//        }
        //End testing
        /**
         * Add Appearance to Label
         * */
        String appearance = p.getAppearanceHint();
        if (appearance == null) appearance = "";
        appearance = appearance.toLowerCase(Locale.ENGLISH);
        if (appearance.equals("hide")) {
            //Change from Invisible to GONE
            mQuestionText.setVisibility(GONE);
        } else if (appearance.equals("oi_style")){
            mQuestionText.setTextColor(getResources().getColor(R.color.text_blue));
            mQuestionText.setTypeface(mQuestionText.getTypeface(), Typeface.BOLD);
        }
        /**
         * End of Appearance
         * */

        // Create the layout for audio, image, text
        mediaLayout = new MediaLayout(getContext());
        mediaLayout.setAVT(p.getIndex(), "", mQuestionText, audioURI, imageURI, videoURI, bigImageURI);

        addView(mediaLayout, mLayout);
    }

    /**
     * Add a TextView containing the help text.
     */
    private void addHelpText(FormEntryPrompt p) {

        String s = p.getHelpText();

        if (s != null && !s.equals("")) {
            mHelpText = new TextView(getContext());
            mHelpText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mQuestionFontsize - 3);
            mHelpText.setPadding(0, -5, 0, 7);
            // wrap to the widget of view
            mHelpText.setHorizontallyScrolling(false);
            mHelpText.setText(s);
            mHelpText.setTypeface(null, Typeface.ITALIC);

            addView(mHelpText, mLayout);
        }
    }

    /**
     * Every subclassed widget should override this, adding any views they may contain, and calling
     * super.cancelLongPress()
     */
    public void cancelLongPress() {
        super.cancelLongPress();
        if (mQuestionText != null) {
            mQuestionText.cancelLongPress();
        }
        if (mHelpText != null) {
            mHelpText.cancelLongPress();
        }
    }

    abstract public void setDefaultValue(String value);

}
