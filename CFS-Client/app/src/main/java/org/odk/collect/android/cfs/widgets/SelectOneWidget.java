/*
 * Copyright (C) 2009 University of Washington
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
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import org.javarosa.core.model.SelectChoice;
import org.javarosa.core.model.data.IAnswerData;
import org.javarosa.core.model.data.SelectOneData;
import org.javarosa.core.model.data.helper.Selection;
import org.javarosa.form.api.FormEntryCaption;
import org.javarosa.form.api.FormEntryPrompt;
import org.javarosa.xpath.expr.XPathFuncExpr;
import org.odk.collect.android.cfs.R;
import org.odk.collect.android.cfs.application.Collect;
import org.odk.collect.android.cfs.external.ExternalDataUtil;
import org.odk.collect.android.cfs.external.ExternalSelectChoice;
import org.odk.collect.android.cfs.utilities.PropertiesUtils;
import org.odk.collect.android.cfs.views.MediaLayout;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;

/**
 * SelectOneWidgets handles select-one fields using radio buttons.
 *
 * @author Carl Hartung (carlhartung@gmail.com)
 * @author Yaw Anokwa (yanokwa@gmail.com)
 */
@SuppressLint("NewApi")
public class SelectOneWidget extends QuestionWidget implements
        OnCheckedChangeListener {

    Vector<SelectChoice> mItems; // may take a while to compute
    ArrayList<RadioButton> buttons;
    String fieldName;

    public SelectOneWidget(Context context, FormEntryPrompt prompt) {
        super(context, prompt);

        // SurveyCTO-added support for dynamic select content (from .csv files)
        XPathFuncExpr xPathFuncExpr = ExternalDataUtil.getSearchXPathExpression(prompt.getAppearanceHint());
        if (xPathFuncExpr != null) {
            mItems = ExternalDataUtil.populateExternalChoices(prompt, xPathFuncExpr);
        } else {
            mItems = prompt.getSelectChoices();
        }
        buttons = new ArrayList<RadioButton>();

        //setOrientation(LinearLayout.HORIZONTAL);
        //setBackgroundColor(Color.LTGRAY);


        // Layout holds the vertical list of buttons
        LinearLayout buttonLayout = new LinearLayout(context);
        buttonLayout.setOrientation(LinearLayout.VERTICAL);
        buttonLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        // The buttons take up the right half of the screen
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT, 0.25f);

        String fieldElementReference = prompt.getFormElement().getBind().getReference().toString();
        fieldName = getFieldID(fieldElementReference);
        String appearance = prompt.getAppearanceHint();
        if (appearance == null) appearance = "";

        appearance = appearance.toLowerCase(Locale.ENGLISH);
        if (appearance.equalsIgnoreCase("hide")) {
            buttonLayout.setVisibility(INVISIBLE);
        }

        String s = null;
        if (prompt.getAnswerValue() != null) {
            s = ((Selection) prompt.getAnswerValue().getValue()).getValue();
        }

        if (mItems != null) {
            for (int i = 0; i < mItems.size(); i++) {
                RadioButton r = new RadioButton(getContext());
                r.setText(prompt.getSelectChoiceText(mItems.get(i)));
                r.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mAnswerFontsize);
                r.setTag(Integer.valueOf(i));
                r.setId(QuestionWidget.newUniqueId());
                r.setEnabled(!prompt.isReadOnly());
                r.setFocusable(!prompt.isReadOnly());
                r.setPadding(0, 10, 0, 10);
                // add dotted below question
                if (i==0){
                    View ruler = new View(getContext()); ruler.setBackground(getResources().getDrawable(R.drawable.dotted));
                    LinearLayout.LayoutParams rulerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
                    rulerParams.setMargins(0, 15, 0, 15);
                    addView(ruler, rulerParams);
                }
                //end dotted

                // add color background to answer /2

                if (i%2==0){
                    r.setBackgroundColor(getResources().getColor(R.color.grey));
                } else r.setBackgroundColor(getResources().getColor(R.color.white));
                buttons.add(r);
                // end color background

                if (mItems.get(i).getValue().equals(s)) {
                    r.setChecked(true);
                }

                r.setOnCheckedChangeListener(this);

                String audioURI = null;
                audioURI = prompt.getSpecialFormSelectChoiceText(mItems.get(i),
                        FormEntryCaption.TEXT_FORM_AUDIO);

                String imageURI;
                if (mItems.get(i) instanceof ExternalSelectChoice) {
                    imageURI = ((ExternalSelectChoice) mItems.get(i)).getImage();
                } else {
                    imageURI = prompt.getSpecialFormSelectChoiceText(mItems.get(i), FormEntryCaption.TEXT_FORM_IMAGE);
                }

                String videoURI = null;
                videoURI = prompt.getSpecialFormSelectChoiceText(mItems.get(i),
                        "video");

                String bigImageURI = null;
                bigImageURI = prompt.getSpecialFormSelectChoiceText(
                        mItems.get(i), "big-image");

                MediaLayout mediaLayout = new MediaLayout(getContext());
                mediaLayout.setAVT(prompt.getIndex(), "." + Integer.toString(i), r, audioURI, imageURI,
                        videoURI, bigImageURI);
                mediaLayout.setLayoutParams(params);

                buttonLayout.addView(mediaLayout);
                //buttonLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE | LinearLayout.SHOW_DIVIDER_BEGINNING | LinearLayout.SHOW_DIVIDER_END);
                //buttonLayout.setDividerDrawable(context.getResources().getDrawable(R.drawable.divider));
            }

        }

        addView(buttonLayout, params);

    }

    @Override
    public void clearAnswer() {
        for (RadioButton button : this.buttons) {
            if (button.isChecked()) {
                button.setChecked(false);
                return;
            }
        }
    }

    @Override
    public IAnswerData getAnswer() {
        int i = getCheckedId();
        if (i == -1) {
           /* for (RadioButton button: buttons){
                button.setTextColor(getResources().getColor(R.color.text_red));
            }
            setFocus(getContext());*/
            return null;
        } else {
            SelectChoice sc = mItems.elementAt(i);
            return new SelectOneData(new Selection(sc));
        }
    }

    @Override
    public void setFocus(Context context) {
        // Hide the soft keyboard if it's showing.
        InputMethodManager inputManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.getWindowToken(), 0);
    }

    public int getCheckedId() {
        for (int i = 0; i < buttons.size(); ++i) {
            RadioButton button = buttons.get(i);
            if (button.isChecked()) {
                return i;
            }
        }
        return -1;
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!isChecked) {
            // If it got unchecked, we don't care.
            return;
        }

        for (RadioButton button : buttons) {
            if (button.isChecked() && !(buttonView == button)) {
                button.setChecked(false);
            }
        }


       /* if (fieldName.equalsIgnoreCase("purpose_visit")) {
            if (buttons.get(3).isChecked()) {
                PropertiesUtils.getTextViewOtherPurpose().setVisibility(VISIBLE);
                PropertiesUtils.getEditTextOtherPurpose().setVisibility(VISIBLE);
            } else {
                PropertiesUtils.getTextViewOtherPurpose().setVisibility(GONE);
                PropertiesUtils.getEditTextOtherPurpose().setVisibility(GONE);
            }

        } */
       /* if(fieldName.equalsIgnoreCase("activities_list")){
            if(buttons.get(14).isChecked()){
                PropertiesUtils.getTextViewOtherActivity().setVisibility(VISIBLE);
                PropertiesUtils.getEditTextOtherActivity().setVisibility(VISIBLE);
            } else{
                PropertiesUtils.getTextViewOtherActivity().setVisibility(GONE);
                PropertiesUtils.getEditTextOtherActivity().setVisibility(GONE);
            }
        }*/
        /*if(fieldName.equalsIgnoreCase("ngo_name")){
            if(buttons.get(8).isChecked()){
                PropertiesUtils.getTextViewOtherOrg().setVisibility(VISIBLE);
                PropertiesUtils.getEditTextOtherOrg().setVisibility(VISIBLE);
            } else{
                PropertiesUtils.getTextViewOtherOrg().setVisibility(GONE);
                PropertiesUtils.getEditTextOtherOrg().setVisibility(GONE);
            }
        }*/

        Collect.getInstance().getActivityLogger().logInstanceAction(this, "onCheckedChanged",
                mItems.get((Integer) buttonView.getTag()).getValue(), mPrompt.getIndex());
    }

    @Override
    public void setOnLongClickListener(OnLongClickListener l) {
        for (RadioButton r : buttons) {
            r.setOnLongClickListener(l);
        }
    }

    @Override
    public void cancelLongPress() {
        super.cancelLongPress();
        for (RadioButton button : this.buttons) {
            button.cancelLongPress();
        }
    }

    @Override
    public void setDefaultValue(String value) {

    }

}
