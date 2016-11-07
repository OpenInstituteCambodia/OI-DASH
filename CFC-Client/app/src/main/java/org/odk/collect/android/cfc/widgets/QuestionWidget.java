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

package org.odk.collect.android.cfc.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.analytics.Logger;

import org.javarosa.core.model.data.IAnswerData;
import org.javarosa.form.api.FormEntryPrompt;
import org.odk.collect.android.cfc.R;
import org.odk.collect.android.cfc.application.Collect;
import org.odk.collect.android.cfc.utilities.PropertiesUtils;
import org.odk.collect.android.cfc.views.MediaLayout;

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
        View ruler = new View(getContext());
        ruler.setBackgroundColor(getResources().getColor(R.color.text_link));
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
        //////test 3.6
//        if (fieldName.equalsIgnoreCase("license_others")) {
//            if (PropertiesUtils.getInputView36()!=null && PropertiesUtils.getInputView36().equalsIgnoreCase("state")){
//                mQuestionText.setVisibility(GONE);
//            }else {
//                mQuestionText.setVisibility(VISIBLE);
//            }
//            PropertiesUtils.setQuestion52(mQuestionText);
//        }
//        if (fieldName.equalsIgnoreCase("mou_mosvy")) {
//            if (PropertiesUtils.getInputView36()!=null && PropertiesUtils.getInputView36().equalsIgnoreCase("state")){
//                mQuestionText.setVisibility(GONE);
//            }else {
//                mQuestionText.setVisibility(VISIBLE);
//            }
//            PropertiesUtils.setQuestion53(mQuestionText);
//        }
//        if (fieldName.equalsIgnoreCase("mou_ministries")) {
//            if (PropertiesUtils.getInputView36()!=null && PropertiesUtils.getInputView36().equalsIgnoreCase("state")){
//                mQuestionText.setVisibility(GONE);
//            }else {
//                mQuestionText.setVisibility(VISIBLE);
//            }
//            PropertiesUtils.setQuestion54(mQuestionText);
//        }
        ///////// end test 3.6

        if (fieldName.equalsIgnoreCase("license_issued_date")) {
            if(PropertiesUtils.getInputView51()!=null && PropertiesUtils.getInputView51().equalsIgnoreCase("no")){
                mQuestionText.setVisibility(GONE);
            } else {
                mQuestionText.setVisibility(VISIBLE);
            }
            PropertiesUtils.setQuestion511(mQuestionText);
        }

        if (fieldName.equalsIgnoreCase("license_others")){
            PropertiesUtils.setQuestion52(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("mou_mosvy")){
            PropertiesUtils.setQuestion53(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("mou_ministries")){
            PropertiesUtils.setQuestion54(mQuestionText);
        }

        if (fieldName.equalsIgnoreCase("license_other_entities")) {
//            if(PropertiesUtils.getAnswer52()==0 && PropertiesUtils.getInputView36().equalsIgnoreCase("international") || PropertiesUtils.getInputView36().equalsIgnoreCase("local")) {
//                mQuestionText.setVisibility(GONE);
//            }else {
//                mQuestionText.setVisibility(VISIBLE);
//            }
            PropertiesUtils.setQuestion521(mQuestionText);
        }

        if (fieldName.equalsIgnoreCase("name_other_entity")){
            PropertiesUtils.setQuestion521a(mQuestionText);
        }

        if (fieldName.equalsIgnoreCase("starting_date")){
//            if(PropertiesUtils.getAnswer53()==0 && PropertiesUtils.getInputView36().equalsIgnoreCase("international") || PropertiesUtils.getInputView36().equalsIgnoreCase("local")){
//                mQuestionText.setVisibility(GONE);
//            } else {
//                mQuestionText.setVisibility(VISIBLE);
//            }
            PropertiesUtils.setQuestion531(mQuestionText);
        }

        if (fieldName.equalsIgnoreCase("to_date")){
//            if(PropertiesUtils.getAnswer53()==0 && PropertiesUtils.getInputView36().equalsIgnoreCase("international")|| PropertiesUtils.getInputView36().equalsIgnoreCase("local")){
//                mQuestionText.setVisibility(GONE);
//            } else {
//                mQuestionText.setVisibility(VISIBLE);
//            }
            PropertiesUtils.setQuestion532(mQuestionText);
        }

        if (fieldName.equalsIgnoreCase("specify")){
//            if(PropertiesUtils.getAnswer54()==0 && PropertiesUtils.getInputView36().equalsIgnoreCase("international") || PropertiesUtils.getInputView36().equalsIgnoreCase("local")){
//                mQuestionText.setVisibility(GONE);
//            } else {
//                mQuestionText.setVisibility(VISIBLE);
//            }
            PropertiesUtils.setQuestion541(mQuestionText);
        }

        if (fieldName.equalsIgnoreCase("amount_budget")){
            PropertiesUtils.setQuestion741(mQuestionText);
        }

        if (fieldName.equalsIgnoreCase("supporting_budget")){
            PropertiesUtils.setQuestion742(mQuestionText);
        }

        if (fieldName.equalsIgnoreCase("closure_when")){
            PropertiesUtils.setQuestion751a(mQuestionText);
        }

        if (fieldName.equalsIgnoreCase("closure_why")){
            PropertiesUtils.setQuestion751b(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("transit_when")){
            PropertiesUtils.setQuestion752a(mQuestionText);
        }

        if (fieldName.equalsIgnoreCase("transit_why")){
            PropertiesUtils.setQuestion752b(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("support_amount")){
            PropertiesUtils.setQuestion753a(mQuestionText);
        }

        if (fieldName.equalsIgnoreCase("support_when")){
            PropertiesUtils.setQuestion753b(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("support_why")){
            PropertiesUtils.setQuestion753c(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("other_service")){
            PropertiesUtils.setQuestion611(mQuestionText);
        }



        if (fieldName.equalsIgnoreCase("foster_care")){
            PropertiesUtils.setQuestion621(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("total_foster_care")){
            PropertiesUtils.setQuestion621a(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("female_foster_care")){
            PropertiesUtils.setQuestion621b(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("kinship_care")){
            PropertiesUtils.setQuestion622(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("total_kinship_care")){
            PropertiesUtils.setQuestion622a(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("female_kinship_care")){
            PropertiesUtils.setQuestion622b(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("grouphome_care")){
            PropertiesUtils.setQuestion623(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("total_group_home")){
            PropertiesUtils.setQuestion623a(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("female_group_home")){
            PropertiesUtils.setQuestion623b(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("independent_care")){
            PropertiesUtils.setQuestion624(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("total_independent")){
            PropertiesUtils.setQuestion624a(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("female_independent")){
            PropertiesUtils.setQuestion624b(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("religious_care")){
            PropertiesUtils.setQuestion625(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("total_temple")){
            PropertiesUtils.setQuestion625a(mQuestionText);
        }

        if (fieldName.equalsIgnoreCase("female_temple")){
            PropertiesUtils.setQuestion625b(mQuestionText);
        }

        if (fieldName.equalsIgnoreCase("outside_service")){
            PropertiesUtils.setQuestion626(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("other_outside_service")){
            PropertiesUtils.setQuestion6261(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("total_religious")){
            PropertiesUtils.setQuestion626a(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("female_religious")){
            PropertiesUtils.setQuestion626b(mQuestionText);
        }





        if (fieldName.equalsIgnoreCase("c1_abuse")) {
            PropertiesUtils.setQuestion2321a1(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("c2_abuse")) {
            PropertiesUtils.setQuestion2321a2(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("c3_abuse")) {
            PropertiesUtils.setQuestion2321a3(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("c4_abuse")) {
            PropertiesUtils.setQuestion2321a4(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("c5_abuse")) {
            PropertiesUtils.setQuestion2321a5(mQuestionText);
        }


        if (fieldName.equalsIgnoreCase("c1_image_abuse")) {
            PropertiesUtils.setQuestion2322a1(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("c2_image_abuse")) {
            PropertiesUtils.setQuestion2322a2(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("c3_image_abuse")) {
            PropertiesUtils.setQuestion2322a3(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("c4_image_abuse")) {
            PropertiesUtils.setQuestion2322a4(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("c5_image_abuse")) {
            PropertiesUtils.setQuestion2322a5(mQuestionText);
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
        if (appearance.startsWith("hide")) {
            //Change from Invisible to GONE
            mQuestionText.setVisibility(INVISIBLE);
        } else if (appearance.equals("oi_style")){
            int questionFontsize = Collect.getQuestionFontsize();
            mQuestionText.setTextColor(getResources().getColor(R.color.text_blue));
            mQuestionText.setTypeface(mQuestionText.getTypeface(), Typeface.BOLD);
            mQuestionText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, questionFontsize + 5);
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

    public void clearForm(ViewGroup group)
    {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);

            if (view instanceof CheckBox) {
                ((CheckBox)view).setChecked(false);
            }

            if (view instanceof RadioButton) {
                ((RadioButton)view).setChecked(false);
            }

            Log.d("class-name", view.getClass().toString());

            if (view instanceof MediaLayout) {
                Log.i("MediaLayout", "MediaLayout");
                clearForm((ViewGroup) view);
            }

            if (view instanceof EditText){
                ((EditText) view).setText("");
            }

            if (view instanceof Spinner) {
                ((Spinner) view).setSelection(0);
            }

            if(view instanceof ViewGroup && (((ViewGroup)view).getChildCount() > 0))
                clearForm((ViewGroup)view);
        }
    }

}
