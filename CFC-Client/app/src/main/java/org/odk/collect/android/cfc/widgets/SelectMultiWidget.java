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

package org.odk.collect.android.cfc.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.javarosa.core.model.SelectChoice;
import org.javarosa.core.model.data.IAnswerData;
import org.javarosa.core.model.data.SelectMultiData;
import org.javarosa.core.model.data.helper.Selection;
import org.javarosa.form.api.FormEntryCaption;
import org.javarosa.form.api.FormEntryPrompt;
import org.javarosa.xpath.expr.XPathFuncExpr;
import org.odk.collect.android.cfc.application.Collect;
import org.odk.collect.android.cfc.external.ExternalDataUtil;
import org.odk.collect.android.cfc.external.ExternalSelectChoice;
import org.odk.collect.android.cfc.utilities.PropertiesUtils;
import org.odk.collect.android.cfc.views.MediaLayout;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;

/**
 * SelctMultiWidget handles multiple selection fields using checkboxes.
 *
 * @author Carl Hartung (carlhartung@gmail.com)
 * @author Yaw Anokwa (yanokwa@gmail.com)
 */

@SuppressLint("NewApi")
public class SelectMultiWidget extends QuestionWidget {
    Vector<SelectChoice> mItems;
    String fieldName;
    private boolean mCheckboxInit = true;
    private ArrayList<CheckBox> mCheckboxes;
    private LinearLayout ll;

    @SuppressWarnings("unchecked")
    public SelectMultiWidget(Context context, FormEntryPrompt prompt) {
        super(context, prompt);
        mPrompt = prompt;
        mCheckboxes = new ArrayList<CheckBox>();

        // SurveyCTO-added support for dynamic select content (from .csv files)
        XPathFuncExpr xPathFuncExpr = ExternalDataUtil.getSearchXPathExpression(prompt.getAppearanceHint());
        if (xPathFuncExpr != null) {
            mItems = ExternalDataUtil.populateExternalChoices(prompt, xPathFuncExpr);
        } else {
            mItems = prompt.getSelectChoices();
        }

        setOrientation(LinearLayout.VERTICAL);
        // Layout holds the vertical list of buttons
        LinearLayout buttonLayout = new LinearLayout(context);
        buttonLayout.setOrientation(LinearLayout.VERTICAL);
        buttonLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));


        String fieldElementReference = prompt.getFormElement().getBind().getReference().toString();
        fieldName = getFieldID(fieldElementReference);

        String appearance = prompt.getAppearanceHint();
        if (appearance == null) appearance = "";

        appearance = appearance.toLowerCase(Locale.ENGLISH);


        Vector<Selection> ve = new Vector<Selection>();
        if (prompt.getAnswerValue() != null) {
            ve = (Vector<Selection>) prompt.getAnswerValue().getValue();
        }

        Boolean isNewRow = false;
        Boolean isAddDirect = false;
        Boolean isTextView = false;
        Boolean isAddDivider = false;
        int itemCount = 0;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0.5f);
        if (mItems != null) {
            for (int i = 0; i < mItems.size(); i++) {
                // no checkbox group so id by answer + offset
                View c;
                //Check text of the Choice Start With ($), i must be category
                if (prompt.getSelectChoiceText(mItems.get(i)) != null) {
                    if (prompt.getSelectChoiceText(mItems.get(i)).startsWith("$")) {
                        StringBuilder sb = new StringBuilder(prompt.getSelectChoiceText(mItems.get(i)));
                        String str = sb.deleteCharAt(0).toString();

                        c = new TextView(getContext());
                        ((TextView) c).setText(str);
                        ((TextView) c).setTextSize(TypedValue.COMPLEX_UNIT_DIP, mQuestionFontsize + 1);
                        ((TextView) c).setTypeface(null, Typeface.BOLD_ITALIC);
                        c.setPadding(70, 10, 10, 10);
                        c.setId(QuestionWidget.newUniqueId()); // assign random id
                        isNewRow = true;
                        itemCount = 0;
                        isTextView = true;
                        mCheckboxes.add(new CheckBox(context));
                    } else { //  checkbox
                        isNewRow = true;
                        isTextView = false;
                        itemCount++;
                        c = new CheckBox(getContext());
                        c.setTag(Integer.valueOf(i));
                        c.setId(QuestionWidget.newUniqueId());
                        ((CheckBox) c).setText(prompt.getSelectChoiceText(mItems.get(i)));
                        ((CheckBox) c).setTextSize(TypedValue.COMPLEX_UNIT_DIP, mAnswerFontsize);
                        c.setFocusable(!prompt.isReadOnly());
                        c.setEnabled(!prompt.isReadOnly());
                        c.setPadding(10, 10, 10, 10);
                        ((CheckBox) c).setLineSpacing(1, 1.15f);

                        if (fieldName.equalsIgnoreCase("license_other_entities")){
                            if (mItems.get(2).getValue().equalsIgnoreCase("other")) {
                                PropertiesUtils.setAnswer521Other(1);
                            }  else{
                                PropertiesUtils.setAnswer521Other(0);
                            }
                        }
                        if (fieldName.equalsIgnoreCase("services")){
                            if (mItems.get(2).getValue().equalsIgnoreCase("other")) {
                                PropertiesUtils.setAnswer611Other(1);
//                                if (PropertiesUtils.getInputView611()!=null){
//                                    PropertiesUtils.getInputView611().setVisibility(VISIBLE);
//                                    PropertiesUtils.getQuestion611().setVisibility(VISIBLE);
//                                }

                            }  else{
                                PropertiesUtils.setAnswer611Other(0);
//                                if (PropertiesUtils.getInputView611()!=null){
//                                    PropertiesUtils.getInputView611().setVisibility(INVISIBLE);
//                                    PropertiesUtils.getQuestion611().setVisibility(INVISIBLE);
//                                }
                            }
                        }
                        mCheckboxes.add((CheckBox) c);

                    }

                    if (!isTextView)
                        for (int vi = 0; vi < ve.size(); vi++) {
                            // match based on value, not key
                            if (mItems.get(i).getValue().equals(ve.elementAt(vi).getValue())) {
                                ((CheckBox) c).setChecked(true);
                                break;
                            }

                        }

                    if (!prompt.getSelectChoiceText(mItems.get(i)).startsWith("$")) {
                        // when clicked, check for readonly before toggling


                        ((CheckBox) c).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                if (!mCheckboxInit && mPrompt.isReadOnly()) {
                                    if (buttonView.isChecked()) {
                                        buttonView.setChecked(false);
                                        Collect.getInstance().getActivityLogger().logInstanceAction(this, "onItemClick.deselect",
                                                mItems.get((Integer) buttonView.getTag()).getValue(), mPrompt.getIndex());
                                    } else {
                                        buttonView.setChecked(true);
                                        Collect.getInstance().getActivityLogger().logInstanceAction(this, "onItemClick.select",
                                                mItems.get((Integer) buttonView.getTag()).getValue(), mPrompt.getIndex());
                                    }
                                }
                            }
                        });
                    }

                    String audioURI = null;
                    audioURI =
                            prompt.getSpecialFormSelectChoiceText(mItems.get(i),
                                    FormEntryCaption.TEXT_FORM_AUDIO);

                    String imageURI;
                    if (mItems.get(i) instanceof ExternalSelectChoice) {
                        imageURI = ((ExternalSelectChoice) mItems.get(i)).getImage();
                    } else {
                        imageURI = prompt.getSpecialFormSelectChoiceText(mItems.get(i), FormEntryCaption.TEXT_FORM_IMAGE);
                    }

                    String videoURI = null;
                    videoURI = prompt.getSpecialFormSelectChoiceText(mItems.get(i), "video");

                    String bigImageURI = null;
                    bigImageURI = prompt.getSpecialFormSelectChoiceText(mItems.get(i), "big-image");

                    MediaLayout mediaLayout = new MediaLayout(getContext());
                    if (isTextView) {
                        mediaLayout.setAVT(prompt.getIndex(), "." + Integer.toString(i), (TextView) c, audioURI, imageURI, videoURI, bigImageURI);
                    } else {
                        mediaLayout.setAVT(prompt.getIndex(), "." + Integer.toString(i), (CheckBox) c, audioURI, imageURI, videoURI, bigImageURI);
                    }
                    mediaLayout.setLayoutParams(params);


//                row_index = i/2 +1;
//                col_index = i%2;


                    if (itemCount == 2) {
                        isNewRow = false;
                        itemCount = 0;
                    }

                    if (isNewRow) {
                        ll = null;
                        ll = new LinearLayout(getContext());
                        ll.setOrientation(LinearLayout.HORIZONTAL);
                        ll.addView(mediaLayout);
                        isNewRow = false;
                        isAddDirect = false;
                        isAddDivider = false;

                        try {
                            //if textview (category in choices) or the next element is textView , so must add direct
                            if (isTextView || prompt.getSelectChoiceText(mItems.get(i + 1)).startsWith("$"))
                                isAddDirect = true;
                        } catch (Exception e) {
                            isAddDirect = true;

                        }

                        if (isAddDirect) {
                            addView(ll);
                            isAddDivider = true;
                        }
                    } else {
                        ll.addView(mediaLayout);
                        addView(ll);
                        isAddDivider = true;
                    }


                    // add the dividing line between elements (except for the last element) but not last
                    ImageView divider = new ImageView(getContext());
                    divider.setBackgroundResource(android.R.drawable.divider_horizontal_bright);
                    int size = mItems.size() / 2;
                    // if ( (i+1)/2< size && (i+1)%2==0) {
                    if (isAddDivider && i < mItems.size() - 1)
                        addView(divider);

                    // }
                    // buttonLayout.addView(mediaLayout);


                }
            }


        }

        if (fieldName.equalsIgnoreCase("license_other_entities")){
            PropertiesUtils.setInputView521(this);
            if (PropertiesUtils.getAnswer52()==1){
                PropertiesUtils.getInputView521().setVisibility(VISIBLE);
            } else{
                PropertiesUtils.getInputView521().setVisibility(INVISIBLE);
            }
        }

        if (fieldName.equalsIgnoreCase("services")){
            PropertiesUtils.setInputView61(this);
            if (PropertiesUtils.getInputView611()!=null){
                PropertiesUtils.getInputView611().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion611().setVisibility(VISIBLE);
            }
        }

        if (fieldName.equalsIgnoreCase("c1_abuse")){
            PropertiesUtils.setInputView2321a1(this);
            String test = PropertiesUtils.getInputView2321s1();
            if (PropertiesUtils.getInputView2321s1()!=null){
                if (PropertiesUtils.getInputView2321s1().equalsIgnoreCase("4") || PropertiesUtils.getInputView2321s1().equalsIgnoreCase("0.00001")){
                    PropertiesUtils.getInputView2321a1().setVisibility(VISIBLE);
                }
            } else{
                PropertiesUtils.getInputView2321a1().setVisibility(INVISIBLE);
            }
        }
        if (fieldName.equalsIgnoreCase("c2_abuse")){
            PropertiesUtils.setInputView2321a2(this);
           // String test = PropertiesUtils.getInputView2321s2();
            if (PropertiesUtils.getInputView2321s2()!=null && PropertiesUtils.getInputView2321s2().equalsIgnoreCase("4")){
                PropertiesUtils.getInputView2321a2().setVisibility(VISIBLE);
            } if (PropertiesUtils.getInputView2321s2()!=null && PropertiesUtils.getInputView2321s2().equalsIgnoreCase("0.00001")){
                PropertiesUtils.getInputView2321a2().setVisibility(VISIBLE);
            }else{
                PropertiesUtils.getInputView2321a2().setVisibility(INVISIBLE);
            }
        }
        if (fieldName.equalsIgnoreCase("c3_abuse")){
            PropertiesUtils.setInputView2321a3(this);
            if (PropertiesUtils.getInputView2321s3()!=null && (PropertiesUtils.getInputView2321s3().equalsIgnoreCase("4") || PropertiesUtils.getInputView2321s3().equalsIgnoreCase("0.00001") )){
                PropertiesUtils.getInputView2321a3().setVisibility(VISIBLE);
            } else{
                PropertiesUtils.getInputView2321a3().setVisibility(INVISIBLE);
            }
        }
        if (fieldName.equalsIgnoreCase("c4_abuse")){
            PropertiesUtils.setInputView2321a4(this);
            if (PropertiesUtils.getInputView2321s4()!=null && (PropertiesUtils.getInputView2321s4().equalsIgnoreCase("4") || PropertiesUtils.getInputView2321s4().equalsIgnoreCase("0.00001") )){
                PropertiesUtils.getInputView2321a4().setVisibility(VISIBLE);
            } else{
                PropertiesUtils.getInputView2321a4().setVisibility(INVISIBLE);
            }
        }
        if (fieldName.equalsIgnoreCase("c5_abuse")){
            PropertiesUtils.setInputView2321a5(this);
            if (PropertiesUtils.getInputView2321s5()!=null && (PropertiesUtils.getInputView2321s5().equalsIgnoreCase("4") || PropertiesUtils.getInputView2321s5().equalsIgnoreCase("0.00001")) ){
                PropertiesUtils.getInputView2321a5().setVisibility(VISIBLE);
            } else{
                PropertiesUtils.getInputView2321a5().setVisibility(INVISIBLE);
            }
        }

        if (fieldName.equalsIgnoreCase("c1_image_abuse")){
            PropertiesUtils.setInputView2322a1(this);
            if (PropertiesUtils.getInputView2322i1()!=null &&  PropertiesUtils.getInputView2322i1().equalsIgnoreCase("yes")){
                PropertiesUtils.getInputView2322a1().setVisibility(VISIBLE);
            } else{
                PropertiesUtils.getInputView2322a1().setVisibility(INVISIBLE);
            }
        }
        if (fieldName.equalsIgnoreCase("c2_image_abuse")){
            PropertiesUtils.setInputView2322a2(this);
            if (PropertiesUtils.getInputView2322i2()!=null &&  PropertiesUtils.getInputView2322i2().equalsIgnoreCase("yes")){
                PropertiesUtils.getInputView2322a2().setVisibility(VISIBLE);
            } else{
                PropertiesUtils.getInputView2322a2().setVisibility(INVISIBLE);
            }
        }
        if (fieldName.equalsIgnoreCase("c3_image_abuse")){
            PropertiesUtils.setInputView2322a3(this);
            if (PropertiesUtils.getInputView2322i3()!=null &&  PropertiesUtils.getInputView2322i3().equalsIgnoreCase("yes")){
                PropertiesUtils.getInputView2322a3().setVisibility(VISIBLE);
            } else{
                PropertiesUtils.getInputView2322a3().setVisibility(INVISIBLE);
            }
        }
        if (fieldName.equalsIgnoreCase("c4_image_abuse")){
            PropertiesUtils.setInputView2322a4(this);
            if (PropertiesUtils.getInputView2322i4()!=null &&  PropertiesUtils.getInputView2322i4().equalsIgnoreCase("yes")){
                PropertiesUtils.getInputView2322a4().setVisibility(VISIBLE);
            } else{
                PropertiesUtils.getInputView2322a4().setVisibility(INVISIBLE);
            }
        }
        if (fieldName.equalsIgnoreCase("c5_image_abuse")){
            PropertiesUtils.setInputView2322a5(this);
            if (PropertiesUtils.getInputView2322i5()!=null &&  PropertiesUtils.getInputView2322i5().equalsIgnoreCase("yes")){
                PropertiesUtils.getInputView2322a5().setVisibility(VISIBLE);
            } else{
                PropertiesUtils.getInputView2322a5().setVisibility(INVISIBLE);
            }
        }

        if (appearance.equalsIgnoreCase("hide")) {
            addView(buttonLayout, params);
            this.setVisibility(GONE);
        } else{
            addView(buttonLayout, params);
        }


        if (fieldName.equalsIgnoreCase("activities_list")) {
            for (int j = 0; j < mCheckboxes.size(); j++) {
                if(!mCheckboxes.get(13).isChecked()){
                    PropertiesUtils.setiAnswerOtherActivity(0);
                }

                final CheckBox checkBox = mCheckboxes.get(j);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (mCheckboxes.get(13).isChecked()) {
                            PropertiesUtils.getTextViewOtherActivity().setVisibility(VISIBLE);
                            PropertiesUtils.getEditTextOtherActivity().setVisibility(VISIBLE);
                            PropertiesUtils.setiAnswerOtherActivity(1);
                            if(PropertiesUtils.getEditTextOtherActivity().getText().toString().equalsIgnoreCase("NA")){
                                PropertiesUtils.getEditTextOtherActivity().setText("");
                            }
                            //PropertiesUtils.getEditTextOtherActivity().setText("");
                        }
                        else {
                            PropertiesUtils.getTextViewOtherActivity().setVisibility(GONE);
                            PropertiesUtils.getEditTextOtherActivity().setVisibility(GONE);
                            PropertiesUtils.setiAnswerOtherActivity(0);
                            PropertiesUtils.getEditTextOtherActivity().setText("");
                            if(PropertiesUtils.getiAnswerOtherActivity() == 0){
                                PropertiesUtils.getEditTextOtherActivity().setText("NA");
                            }
                        }


                        if (fieldName.equalsIgnoreCase("license_other_entities")){
                            if (mCheckboxes.get(2).isChecked()){
                                PropertiesUtils.setAnswer521Other(1);
                            }
                            if (!mCheckboxes.get(2).isChecked()){
                                PropertiesUtils.setAnswer521Other(0);
                            }
                        }

                        if (fieldName.equalsIgnoreCase("services")){
                            if (mCheckboxes.get(2).isChecked()){
                                if (PropertiesUtils.getInputView611()!=null){
                                    PropertiesUtils.getInputView611().setVisibility(VISIBLE);
                                }
                                PropertiesUtils.setAnswer611Other(1);
                            }
                            if (!mCheckboxes.get(2).isChecked()){
                                PropertiesUtils.setAnswer611Other(0);
                            }
                        }
                    }
                });
            }
        }

        if (fieldName.equalsIgnoreCase("license_other_entities")){
            for (int j = 0; j < mCheckboxes.size(); j++) {

                final CheckBox checkBox = mCheckboxes.get(j);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (mCheckboxes.get(2).isChecked()) {
                            PropertiesUtils.getInputView521a().setVisibility(VISIBLE);
                            PropertiesUtils.getQuestion521a().setVisibility(VISIBLE);
                            PropertiesUtils.setAnswer521Other(1);
                        }
                        else if (!mCheckboxes.get(2).isChecked()){
                            PropertiesUtils.getInputView521a().setVisibility(GONE);
                            PropertiesUtils.getQuestion521a().setVisibility(GONE);
                            PropertiesUtils.getInputView521a().setText("");
                            PropertiesUtils.setAnswer521Other(0);
                        }
                    }
                });
            }
        }

        if (fieldName.equalsIgnoreCase("services")){
            for (int j = 0; j < mCheckboxes.size(); j++) {
                final CheckBox checkBox = mCheckboxes.get(j);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (mCheckboxes.get(2).isChecked()) {
//                            if(PropertiesUtils.getInputView611()!=null){
                                PropertiesUtils.getInputView611().setVisibility(VISIBLE);
                                PropertiesUtils.getQuestion611().setVisibility(VISIBLE);
//                            }
                            PropertiesUtils.setAnswer611Other(1);
                        }
                        else if (!mCheckboxes.get(2).isChecked()){
//                            if(PropertiesUtils.getInputView611()!=null){
                                PropertiesUtils.getInputView611().setVisibility(GONE);
                                PropertiesUtils.getQuestion611().setVisibility(GONE);
                                PropertiesUtils.getInputView611().setText("");
//                            }
                            PropertiesUtils.setAnswer611Other(0);
                        }

                    }
                });
            }
        }




        if (PropertiesUtils.getAnswer52()==1 && PropertiesUtils.getInputView36().equalsIgnoreCase("local")){
            if (PropertiesUtils.getInputView521()!=null && PropertiesUtils.getQuestion521()!=null){
                PropertiesUtils.getInputView521().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion521().setVisibility(VISIBLE);
            }
        }else if (PropertiesUtils.getAnswer52()==1 && PropertiesUtils.getInputView36().equalsIgnoreCase("international")) {
            if (PropertiesUtils.getInputView521()!=null && PropertiesUtils.getQuestion521()!=null){
                PropertiesUtils.getInputView521().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion521().setVisibility(VISIBLE);
            }
        }

        /// student1

        if (PropertiesUtils.getInputView2321s1()!=null && PropertiesUtils.getInputView2321s1().equalsIgnoreCase("4")){
            if (PropertiesUtils.getInputView2321a1()!=null && PropertiesUtils.getQuestion2321a1()!=null){
                PropertiesUtils.getInputView2321a1().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion2321a1().setVisibility(VISIBLE);
            }
        }else if (PropertiesUtils.getInputView2321s1()!=null && PropertiesUtils.getInputView2321s1().equalsIgnoreCase("0.00001")){
            if (PropertiesUtils.getInputView2321a1()!=null && PropertiesUtils.getQuestion2321a1()!=null){
                PropertiesUtils.getInputView2321a1().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion2321a1().setVisibility(VISIBLE);
            }
        }

        /// student2

        if (PropertiesUtils.getInputView2321s2()!=null && PropertiesUtils.getInputView2321s2().equalsIgnoreCase("4")){
            if (PropertiesUtils.getInputView2321a2()!=null && PropertiesUtils.getQuestion2321a2()!=null){
                PropertiesUtils.getInputView2321a2().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion2321a2().setVisibility(VISIBLE);
            }
        }
        else if (PropertiesUtils.getInputView2321s2()!=null && PropertiesUtils.getInputView2321s2().equalsIgnoreCase("0.00001")){
            if (PropertiesUtils.getInputView2321a2()!=null){
                PropertiesUtils.getInputView2321a2().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion2321a2().setVisibility(VISIBLE);
            }
        }

        /// student3

        if (PropertiesUtils.getInputView2321s3()!=null && PropertiesUtils.getInputView2321s3().equalsIgnoreCase("4")){
            if (PropertiesUtils.getInputView2321a3()!=null && PropertiesUtils.getQuestion2321a3()!=null){
                PropertiesUtils.getInputView2321a3().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion2321a3().setVisibility(VISIBLE);
            }
        }
        else if (PropertiesUtils.getInputView2321s3()!=null && PropertiesUtils.getInputView2321s3().equalsIgnoreCase("0.00001")){
            if (PropertiesUtils.getInputView2321a3()!=null && PropertiesUtils.getQuestion2321a3()!=null){
                PropertiesUtils.getInputView2321a3().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion2321a3().setVisibility(VISIBLE);
            }
        }

        /// student4

        if (PropertiesUtils.getInputView2321s4()!=null && PropertiesUtils.getInputView2321s4().equalsIgnoreCase("4")){
            if (PropertiesUtils.getInputView2321a4()!=null && PropertiesUtils.getQuestion2321a4()!=null){
                PropertiesUtils.getInputView2321a4().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion2321a4().setVisibility(VISIBLE);
            }
        }
        if (PropertiesUtils.getInputView2321s4()!=null && PropertiesUtils.getInputView2321s4().equalsIgnoreCase("0.00001")){
            if (PropertiesUtils.getInputView2321a4()!=null && PropertiesUtils.getQuestion2321a4()!=null){
                PropertiesUtils.getInputView2321a4().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion2321a4().setVisibility(VISIBLE);
            }
        }

        /// student5

        if (PropertiesUtils.getInputView2321s5()!=null && PropertiesUtils.getInputView2321s5().equalsIgnoreCase("4")){
            if (PropertiesUtils.getInputView2321a5()!=null && PropertiesUtils.getQuestion2321a5()!=null){
                PropertiesUtils.getInputView2321a5().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion2321a5().setVisibility(VISIBLE);
            }
        }
        else if (PropertiesUtils.getInputView2321s5()!=null && PropertiesUtils.getInputView2321s5().equalsIgnoreCase("0.00001")){
            if (PropertiesUtils.getInputView2321a5()!=null && PropertiesUtils.getQuestion2321a5()!=null){
                PropertiesUtils.getInputView2321a5().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion2321a5().setVisibility(VISIBLE);
            }
        }


     ////////////////////////////////


        if (fieldName.equalsIgnoreCase("c1_image_abuse")){
            if (PropertiesUtils.getInputView2322i1()!=null && PropertiesUtils.getInputView2322i1().equalsIgnoreCase("yes")){
                if (PropertiesUtils.getInputView2322a1()!=null){
                    PropertiesUtils.getInputView2322a1().setVisibility(VISIBLE);
                }
                if (PropertiesUtils.getQuestion2322a1()!=null){
                    PropertiesUtils.getQuestion2322a1().setVisibility(VISIBLE);
                }
            }
        }

        if (fieldName.equalsIgnoreCase("c2_image_abuse")){
            if (PropertiesUtils.getInputView2322i2()!=null && PropertiesUtils.getInputView2322i2().equalsIgnoreCase("yes")){
                if (PropertiesUtils.getInputView2322a2()!=null){
                    PropertiesUtils.getInputView2322a2().setVisibility(VISIBLE);
                }
                if (PropertiesUtils.getQuestion2322a2()!=null){
                    PropertiesUtils.getQuestion2322a2().setVisibility(VISIBLE);
                }
            }
        }

        if (fieldName.equalsIgnoreCase("c3_image_abuse")){
            if (PropertiesUtils.getInputView2322i3()!=null && PropertiesUtils.getInputView2322i3().equalsIgnoreCase("yes")){
                if (PropertiesUtils.getInputView2322a3()!=null){
                    PropertiesUtils.getInputView2322a3().setVisibility(VISIBLE);
                }
                if (PropertiesUtils.getQuestion2322a3()!=null){
                    PropertiesUtils.getQuestion2322a3().setVisibility(VISIBLE);
                }
            }
        }

        if (fieldName.equalsIgnoreCase("c4_image_abuse")){
            if (PropertiesUtils.getInputView2322i4()!=null && PropertiesUtils.getInputView2322i4().equalsIgnoreCase("yes")){
                if (PropertiesUtils.getInputView2322a4()!=null){
                    PropertiesUtils.getInputView2322a4().setVisibility(VISIBLE);
                }
                if (PropertiesUtils.getQuestion2322a4()!=null){
                    PropertiesUtils.getQuestion2322a4().setVisibility(VISIBLE);
                }
            }
        }

        if (fieldName.equalsIgnoreCase("c5_image_abuse")){
            if (PropertiesUtils.getInputView2322i5()!=null && PropertiesUtils.getInputView2322i5().equalsIgnoreCase("yes")){
                if (PropertiesUtils.getInputView2322a5()!=null){
                    PropertiesUtils.getInputView2322a5().setVisibility(VISIBLE);
                }
                if (PropertiesUtils.getQuestion2322a5()!=null){
                    PropertiesUtils.getQuestion2322a5().setVisibility(VISIBLE);
                }
            }
        }

//        if (fieldName.equalsIgnoreCase("future_plan")){
//            for (int j = 0; j < mCheckboxes.size(); j++) {
//
//                final CheckBox checkBox = mCheckboxes.get(j);
//                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (mCheckboxes.get(0).isChecked()) {
//                            PropertiesUtils.getInputView751a().setVisibility(VISIBLE);
//                            PropertiesUtils.getQuestion751a().setVisibility(VISIBLE);
//                            PropertiesUtils.getInputView751b().setVisibility(VISIBLE);
//                            PropertiesUtils.getQuestion751b().setVisibility(VISIBLE);
//                        }
//                        else if (!mCheckboxes.get(0).isChecked()){
//                            PropertiesUtils.getInputView751a().setVisibility(GONE);
//                            PropertiesUtils.getQuestion751a().setVisibility(GONE);
//                            PropertiesUtils.getInputView751b().setVisibility(GONE);
//                            PropertiesUtils.getQuestion751b().setVisibility(GONE);
//                            PropertiesUtils.getInputView751a().setText("");
//                            PropertiesUtils.getInputView751b().setText("");
//                        }
//
//                        if (mCheckboxes.get(1).isChecked()) {
//                            PropertiesUtils.getInputView752a().setVisibility(VISIBLE);
//                            PropertiesUtils.getQuestion752a().setVisibility(VISIBLE);
//                            PropertiesUtils.getInputView752b().setVisibility(VISIBLE);
//                            PropertiesUtils.getQuestion752b().setVisibility(VISIBLE);
//                        }
//                        else if (!mCheckboxes.get(1).isChecked()){
//                            PropertiesUtils.getInputView752a().setVisibility(GONE);
//                            PropertiesUtils.getQuestion752a().setVisibility(GONE);
//                            PropertiesUtils.getInputView752b().setVisibility(GONE);
//                            PropertiesUtils.getQuestion752b().setVisibility(GONE);
//                            PropertiesUtils.getInputView752a().setText("");
//                            PropertiesUtils.getInputView752b().setText("");
//                        }
//
//                        if (mCheckboxes.get(2).isChecked()) {
//                            PropertiesUtils.getInputView753a().setVisibility(VISIBLE);
//                            PropertiesUtils.getQuestion753a().setVisibility(VISIBLE);
//                            PropertiesUtils.getInputView753b().setVisibility(VISIBLE);
//                            PropertiesUtils.getQuestion753b().setVisibility(VISIBLE);
//                            PropertiesUtils.getInputView753c().setVisibility(VISIBLE);
//                            PropertiesUtils.getQuestion753c().setVisibility(VISIBLE);
//                        }
//                        else if (!mCheckboxes.get(2).isChecked()){
//                            PropertiesUtils.getInputView753a().setVisibility(GONE);
//                            PropertiesUtils.getQuestion753a().setVisibility(GONE);
//                            PropertiesUtils.getInputView753b().setVisibility(GONE);
//                            PropertiesUtils.getQuestion753b().setVisibility(GONE);
//                            PropertiesUtils.getInputView753c().setVisibility(GONE);
//                            PropertiesUtils.getQuestion753c().setVisibility(GONE);
//                            PropertiesUtils.getInputView753a().setText("");
//                            PropertiesUtils.getInputView753b().setText("");
//                            PropertiesUtils.getInputView753c().setText("");
//                        }
//                    }
//                });
//            }
//        }
        mCheckboxInit = false;
    }

    @Override
    public void clearAnswer() {
        for (CheckBox c : mCheckboxes) {
            if (c.isChecked()) {
                c.setChecked(false);
            }
        }
    }

    @Override
    public IAnswerData getAnswer() {
        Vector<Selection> vc = new Vector<Selection>();
        for (int i = 0; i < mCheckboxes.size(); ++i) {
            CheckBox c = mCheckboxes.get(i);
            if (c.isChecked()) {
                vc.add(new Selection(mItems.get(i)));
            }
        }
        if (vc.size() == 0) {
            return null;
        } else {
            return new SelectMultiData(vc);
        }

    }

    @Override
    public void setFocus(Context context) {
        // Hide the soft keyboard if it's showing.
        InputMethodManager inputManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.getWindowToken(), 0);
    }

    @Override
    public void setOnLongClickListener(OnLongClickListener l) {
        for (CheckBox c : mCheckboxes) {
            c.setOnLongClickListener(l);
        }
    }

    @Override
    public void cancelLongPress() {
        super.cancelLongPress();
        for (CheckBox c : mCheckboxes) {
            c.cancelLongPress();
        }
    }

    @Override
    public void setDefaultValue(String value) {

    }

}
