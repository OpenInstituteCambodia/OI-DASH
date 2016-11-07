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

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DateKeyListener;
import android.text.method.DigitsKeyListener;
import android.text.method.TextKeyListener;
import android.text.method.TextKeyListener.Capitalize;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import org.javarosa.core.model.Constants;
import org.javarosa.core.model.data.IAnswerData;
import org.javarosa.core.model.data.StringData;
import org.javarosa.form.api.FormEntryPrompt;
import org.odk.collect.android.cfc.R;
import org.odk.collect.android.cfc.application.Collect;
import org.odk.collect.android.cfc.utilities.PropertiesUtils;

import java.util.Locale;

/**
 * The most basic widget that allows for entry of any text.
 *
 * @author Carl Hartung (carlhartung@gmail.com)
 * @author Yaw Anokwa (yanokwa@gmail.com)
 */
public class StringWidget extends QuestionWidget {
    private static final String ROWS = "rows";
    protected EditText mAnswer;
    boolean mReadOnly = false;
    private SharedPreferences mAdminPreferences;

    public StringWidget(Context context, FormEntryPrompt prompt, boolean readOnlyOverride) {
        this(context, prompt, readOnlyOverride, true);
        setupChangeListener();
    }

    protected StringWidget(Context context, FormEntryPrompt prompt, boolean readOnlyOverride, boolean derived) {
        super(context, prompt);
        mAnswer = new EditText(context);
        mAnswer.setId(QuestionWidget.newUniqueId());
        mReadOnly = prompt.isReadOnly() || readOnlyOverride;
        mAnswer.setBackgroundResource(R.drawable.shape);
        mAnswer.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mAnswerFontsize);

        String fieldElementReference = prompt.getFormElement().getBind().getReference().toString();
        String fieldName = getFieldID(fieldElementReference);
        TableLayout.LayoutParams params = new TableLayout.LayoutParams();

        if (fieldName.equalsIgnoreCase("other_activity")) {
            if(PropertiesUtils.getiAnswerOtherActivity() == 0){
                mAnswer.setVisibility(GONE);
            } else {
                mAnswer.setVisibility(VISIBLE);
            }
            PropertiesUtils.setEditTextOtherActivity(mAnswer);

        }
        // CFC form address
        else if(fieldName.equalsIgnoreCase("address1_province_code")) {
            Log.i("province_code", PropertiesUtils.getAddress1ProvinceCode());
            mAnswer.setText(PropertiesUtils.getAddress1ProvinceCode());
        } else if(fieldName.equalsIgnoreCase("address1_district_code")) {
            mAnswer.setText(PropertiesUtils.getAddress1DistrictCode());
        } else if(fieldName.equalsIgnoreCase("address1_commune_code")) {
            mAnswer.setText(PropertiesUtils.getAddress1CommuneCode());
        } else if(fieldName.equalsIgnoreCase("address1_village_code")) {
            mAnswer.setText(PropertiesUtils.getAddress1VillageCode());
        }

        else if(fieldName.equalsIgnoreCase("address2_province_code")) {
            Log.i("province_code", PropertiesUtils.getAddress2ProvinceCode());
            mAnswer.setText(PropertiesUtils.getAddress2ProvinceCode());
        } else if(fieldName.equalsIgnoreCase("address2_district_code")) {
            mAnswer.setText(PropertiesUtils.getAddress2DistrictCode());
        } else if(fieldName.equalsIgnoreCase("address2_commune_code")) {
            mAnswer.setText(PropertiesUtils.getAddress2CommuneCode());
        } else if(fieldName.equalsIgnoreCase("address2_village_code")) {
            mAnswer.setText(PropertiesUtils.getAddress2VillageCode());
        }

// CFC Question 5
        if (fieldName.equalsIgnoreCase("name_other_entity")){
            PropertiesUtils.setInputView521a(mAnswer);
        }

        if (fieldName.equalsIgnoreCase("specify")){
            PropertiesUtils.setInputView541(mAnswer);
        }

// CFC Question 6

        if (fieldName.equalsIgnoreCase("other_service")){
            PropertiesUtils.setInputView611(mAnswer);
        }
//        if (fieldName.equalsIgnoreCase("foster_care")){
//          PropertiesUtils.setInputView621(mAnswer);
//        }
        if (fieldName.equalsIgnoreCase("total_foster_care")){
            PropertiesUtils.setInputView621a(mAnswer);
        }
        if (fieldName.equalsIgnoreCase("female_foster_care")){
            PropertiesUtils.setInputView621b(mAnswer);
        }
//        if (fieldName.equalsIgnoreCase("kinship_care")){
//            PropertiesUtils.setInputView622(mAnswer);
//        }
        if (fieldName.equalsIgnoreCase("total_kinship_care")){
            PropertiesUtils.setInputView622a(mAnswer);
        }
        if (fieldName.equalsIgnoreCase("female_kinship_care")){
            PropertiesUtils.setInputView622b(mAnswer);
        }
//        if (fieldName.equalsIgnoreCase("grouphome_care")){
//            PropertiesUtils.setInputView623(mAnswer);
//        }
        if (fieldName.equalsIgnoreCase("total_group_home")){
            PropertiesUtils.setInputView623a(mAnswer);
        }
        if (fieldName.equalsIgnoreCase("female_group_home")){
            PropertiesUtils.setInputView623b(mAnswer);
        }
//        if (fieldName.equalsIgnoreCase("independent_care")){
//            PropertiesUtils.setInputView624(mAnswer);
//        }
        if (fieldName.equalsIgnoreCase("total_independent")){
            PropertiesUtils.setInputView624a(mAnswer);
        }
        if (fieldName.equalsIgnoreCase("female_independent")){
            PropertiesUtils.setInputView624b(mAnswer);
        }
//        if (fieldName.equalsIgnoreCase("religious_care")){
//            PropertiesUtils.setInputView625(mAnswer);
//        }
        if (fieldName.equalsIgnoreCase("total_temple")){
            PropertiesUtils.setInputView625a(mAnswer);
        }
        if (fieldName.equalsIgnoreCase("female_temple")){
            PropertiesUtils.setInputView625b(mAnswer);
        }
//        if (fieldName.equalsIgnoreCase("outside_service")){
//            PropertiesUtils.setInputView626(mAnswer);
//        }
        if (fieldName.equalsIgnoreCase("other_outside_service")){
            PropertiesUtils.setInputView6261(mAnswer);
        }
        if (fieldName.equalsIgnoreCase("total_religious")){
            PropertiesUtils.setInputView626a(mAnswer);
        }
        if (fieldName.equalsIgnoreCase("female_religious")){
            PropertiesUtils.setInputView626b(mAnswer);
        }

// CFC Question 7

        if (fieldName.equalsIgnoreCase("amount_budget")){
            PropertiesUtils.setInputView741(mAnswer);
        }

        if (fieldName.equalsIgnoreCase("supporting_budget")){
            PropertiesUtils.setInputView742(mAnswer);
        }

        if (fieldName.equalsIgnoreCase("closure_when")){
            PropertiesUtils.setInputView751a(mAnswer);
        }

        if (fieldName.equalsIgnoreCase("closure_why")){
            PropertiesUtils.setInputView751b(mAnswer);
        }
        if (fieldName.equalsIgnoreCase("transit_when")){
            PropertiesUtils.setInputView752a(mAnswer);
        }

        if (fieldName.equalsIgnoreCase("transit_why")){
            PropertiesUtils.setInputView752b(mAnswer);
        }

        if (fieldName.equalsIgnoreCase("support_amount")){
            PropertiesUtils.setInputView753a(mAnswer);
        }

        if (fieldName.equalsIgnoreCase("support_when")){
            PropertiesUtils.setInputView753b(mAnswer);
        }
        if (fieldName.equalsIgnoreCase("support_why")){
            PropertiesUtils.setInputView753c(mAnswer);
        }




        /**
         * If a 'rows' attribute is on the input tag, set the minimum number of lines
         * to display in the field to that value.
         *
         * I.e.,
         * <input ref="foo" rows="5">
         *   ...
         * </input>
         *
         * will set the height of the EditText box to 5 rows high.
         */
        String height = prompt.getQuestion().getAdditionalAttribute(null, ROWS);
        if (height != null && height.length() != 0) {
            try {
                int rows = Integer.valueOf(height);
                mAnswer.setMinLines(rows);
                mAnswer.setGravity(Gravity.TOP); // to write test starting at the top of the edit area
            } catch (Exception e) {
                Log.e(this.getClass().getName(), "Unable to process the rows setting for the answer field: " + e.toString());
            }
        }

        params.setMargins(7, 5, 7, 5);
        mAnswer.setLayoutParams(params);

        // capitalize the first letter of the sentence
        mAnswer.setKeyListener(new TextKeyListener(Capitalize.SENTENCES, false));

        // needed to make long read only text scroll
        mAnswer.setHorizontallyScrolling(false);
        mAnswer.setSingleLine(false);

        String s = prompt.getAnswerText();
        if (s != null) {
            mAnswer.setText(s);


        }
        // seng added with appearance username
        String appearance = prompt.getAppearanceHint();
        if (appearance == null) appearance = "";
        // for now, all appearance tags are in english...
        appearance = appearance.toLowerCase(Locale.ENGLISH);
        //Hide hidden field
        if (appearance.startsWith("hide")) {
            mAnswer.setVisibility(GONE);
        }

        if (mReadOnly) {
            mAnswer.setBackgroundDrawable(null);
            mAnswer.setFocusable(false);
            mAnswer.setClickable(false);

            // add spacing to note seng added
            LinearLayout ll = new LinearLayout(context);
            ll.setPadding(0, 10, 0, 10);
            addView(ll);

            switch (prompt.getControlType()) {
                case Constants.CONTROL_INPUT:
                    switch (prompt.getDataType()) {
                        case Constants.DATATYPE_TEXT:
                            String query = prompt.getQuestion().getAdditionalAttribute(null, "query");
                            if (query != null) {

                            } else if (appearance.equals("box")) {
                                setBackgroundResource(R.drawable.bordortopbottom);
                            } else if (appearance.equalsIgnoreCase("readonly")) {
                                mAnswer.setKeyListener(null);
                                mAnswer.setEnabled(false);
                                mAnswer.setTextColor(getResources().getColor(R.color.text_red));
                            } else if (appearance.equalsIgnoreCase("hide")) {
                                mAnswer.setVisibility(INVISIBLE);
                                mAnswer.setKeyListener(null);
                                mAnswer.setEnabled(false);
                                mAnswer.setTextColor(getResources().getColor(R.color.text_red));
                            }
                            break;
                    }
            }
            // end appearance username

        } else {
            // seng added with appearance username

            switch (prompt.getControlType()) {
                case Constants.CONTROL_INPUT:
                    switch (prompt.getDataType()) {
                        case Constants.DATATYPE_TEXT:
                            String query = prompt.getQuestion().getAdditionalAttribute(null, "query");
                            if (query != null) {

                            } else if (appearance.equals("username")) {
                                // get admin preference settings
                                mAdminPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                                String name = mAdminPreferences.getString("username", "");
                                //String name = mAdminPreferences.getString(PreferencesActivity.KEY_USERNAME, "");
                                //EditTextPreference name = (EditTextPreference)PreferenceActivity.findPreference("KEY_USERNAME");
                                Log.i("seng", name);
                                mAnswer.setText(name);

                            } else if (appearance.equals("string-date")) {
                                mAnswer.setKeyListener(new DateKeyListener() {
                                    @Override
                                    protected char[] getAcceptedChars() {
                                        char[] accepted = {
                                                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '/'
                                        };
                                        return accepted;
                                    }
                                });
                            } else if (appearance.equalsIgnoreCase("readonly")) {
                                mAnswer.setKeyListener(null);
                                mAnswer.setEnabled(false);
                                mAnswer.setTextColor(getResources().getColor(R.color.black));
                            } else if (appearance.equals("phone")) {
                                mAnswer.setKeyListener(new DigitsKeyListener() {
                                    @Override
                                    protected char[] getAcceptedChars() {
                                        char[] accepted = {
                                                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ' '
                                        };
                                        return accepted;
                                    }
                                });
                                break;
                            }
                    }
                    addView(mAnswer);
            }
        }

//        if (PropertiesUtils.getInputView62()!=null && PropertiesUtils.getInputView62().equalsIgnoreCase("yes")){
//            if (PropertiesUtils.getQuestion621()!=null)
//                PropertiesUtils.getQuestion621().setVisibility(VISIBLE);
//            if (PropertiesUtils.getInputView621()!=null)
//                PropertiesUtils.getInputView621().setVisibility(VISIBLE);
//
//            if (PropertiesUtils.getInputView622()!=null)
//                PropertiesUtils.getInputView622().setVisibility(VISIBLE);
//            if (PropertiesUtils.getQuestion622()!=null)
//                PropertiesUtils.getQuestion622().setVisibility(VISIBLE);
//
//            if (PropertiesUtils.getInputView623()!=null)
//                PropertiesUtils.getInputView623().setVisibility(VISIBLE);
//            if (PropertiesUtils.getQuestion623()!=null)
//                PropertiesUtils.getQuestion623().setVisibility(VISIBLE);
//
//            if (PropertiesUtils.getInputView624()!=null)
//                PropertiesUtils.getInputView624().setVisibility(VISIBLE);
//            if (PropertiesUtils.getQuestion624()!=null)
//                PropertiesUtils.getQuestion624().setVisibility(VISIBLE);
//
//            if (PropertiesUtils.getInputView625()!=null)
//                PropertiesUtils.getInputView625().setVisibility(VISIBLE);
//            if (PropertiesUtils.getQuestion625()!=null)
//                PropertiesUtils.getQuestion625().setVisibility(VISIBLE);
//
//            if (PropertiesUtils.getInputView626()!=null)
//                PropertiesUtils.getInputView626().setVisibility(VISIBLE);
//            if (PropertiesUtils.getQuestion626()!=null)
//                PropertiesUtils.getQuestion626().setVisibility(VISIBLE);
//
//        }


        if (fieldName.equalsIgnoreCase("name_other_entity")){
            if (PropertiesUtils.getAnswer521Other()==1 && PropertiesUtils.getInputView36().equalsIgnoreCase("local")){
                PropertiesUtils.getInputView521a().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion521a().setVisibility(VISIBLE);
            }
            else if (PropertiesUtils.getAnswer521Other()==1 && PropertiesUtils.getInputView36().equalsIgnoreCase("international")){
                PropertiesUtils.getInputView521a().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion521a().setVisibility(VISIBLE);
            }
        }

        if (fieldName.equalsIgnoreCase("specify")){
            if (PropertiesUtils.getAnswer54()==1 && PropertiesUtils.getInputView36().equalsIgnoreCase("local")){
                if (PropertiesUtils.getInputView541()!=null)
                    PropertiesUtils.getInputView541().setVisibility(VISIBLE);
                if (PropertiesUtils.getQuestion541()!=null)
                    PropertiesUtils.getQuestion541().setVisibility(VISIBLE);
            }
            else if (PropertiesUtils.getAnswer54()==1 && PropertiesUtils.getInputView36().equalsIgnoreCase("international")){
                if (PropertiesUtils.getInputView541()!=null)
                    PropertiesUtils.getInputView541().setVisibility(VISIBLE);
                if (PropertiesUtils.getQuestion541()!=null)
                    PropertiesUtils.getQuestion541().setVisibility(VISIBLE);
            }
        }


        if (fieldName.equalsIgnoreCase("other_service")){
            if (PropertiesUtils.getAnswer611Other()==1){
                PropertiesUtils.getInputView611().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion611().setVisibility(VISIBLE);
            }
        }

        if (fieldName.equalsIgnoreCase("total_foster_care")){
            if (PropertiesUtils.getAnswer621()==1 && PropertiesUtils.getInputView62().equalsIgnoreCase("yes")){
                PropertiesUtils.getInputView621a().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion621a().setVisibility(VISIBLE);
            }
        }

        if (fieldName.equalsIgnoreCase("female_foster_care")){
            if (PropertiesUtils.getAnswer621()==1 && PropertiesUtils.getInputView62().equalsIgnoreCase("yes")){
                PropertiesUtils.getInputView621b().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion621b().setVisibility(VISIBLE);

            }
        }

        if (fieldName.equalsIgnoreCase("total_kinship_care")){
            if (PropertiesUtils.getAnswer622()==1 && PropertiesUtils.getInputView62().equalsIgnoreCase("yes")){
                PropertiesUtils.getInputView622a().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion622a().setVisibility(VISIBLE);
            }
        }

        if (fieldName.equalsIgnoreCase("female_kinship_care")){
            if (PropertiesUtils.getAnswer622()==1 && PropertiesUtils.getInputView62().equalsIgnoreCase("yes")){
                PropertiesUtils.getInputView622b().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion622b().setVisibility(VISIBLE);
            }
        }

        if (fieldName.equalsIgnoreCase("total_group_home")){
            if (PropertiesUtils.getAnswer623()==1 && PropertiesUtils.getInputView62().equalsIgnoreCase("yes")){
                PropertiesUtils.getInputView623a().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion623a().setVisibility(VISIBLE);
            }
        }

        if (fieldName.equalsIgnoreCase("female_group_home")){
            if (PropertiesUtils.getAnswer623()==1 && PropertiesUtils.getInputView62().equalsIgnoreCase("yes")){
                PropertiesUtils.getInputView623b().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion623b().setVisibility(VISIBLE);

            }
        }


        if (fieldName.equalsIgnoreCase("total_independent")){
            if (PropertiesUtils.getAnswer624()==1 && PropertiesUtils.getInputView62().equalsIgnoreCase("yes")){
                PropertiesUtils.getInputView624a().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion624a().setVisibility(VISIBLE);

            }
        }

        if (fieldName.equalsIgnoreCase("female_independent")){
            if (PropertiesUtils.getAnswer624()==1 && PropertiesUtils.getInputView62().equalsIgnoreCase("yes")){
                PropertiesUtils.getInputView624b().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion624b().setVisibility(VISIBLE);

            }
        }

        if (fieldName.equalsIgnoreCase("total_temple")){
            if (PropertiesUtils.getAnswer625()==1 && PropertiesUtils.getInputView62().equalsIgnoreCase("yes")){
                PropertiesUtils.getInputView625a().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion625a().setVisibility(VISIBLE);

            }
        }

        if (fieldName.equalsIgnoreCase("female_temple")){
            if (PropertiesUtils.getAnswer625()==1 && PropertiesUtils.getInputView62().equalsIgnoreCase("yes")){
                PropertiesUtils.getInputView625b().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion625b().setVisibility(VISIBLE);

            }
        }

        if (fieldName.equalsIgnoreCase("other_outside_service")){
            if (PropertiesUtils.getAnswer626()==1 && PropertiesUtils.getInputView62().equalsIgnoreCase("yes")){
                PropertiesUtils.getInputView6261().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion6261().setVisibility(VISIBLE);

            }
        }

        if (fieldName.equalsIgnoreCase("total_religious")){
            if (PropertiesUtils.getAnswer626()==1 && PropertiesUtils.getInputView62().equalsIgnoreCase("yes")){
                PropertiesUtils.getInputView626a().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion626a().setVisibility(VISIBLE);

            }
        }

        if (fieldName.equalsIgnoreCase("female_religious")){
            if (PropertiesUtils.getAnswer626()==1 && PropertiesUtils.getInputView62().equalsIgnoreCase("yes")){
                PropertiesUtils.getInputView626b().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion626b().setVisibility(VISIBLE);

            }
        }

        if (fieldName.equalsIgnoreCase("amount_budget")){
            if (PropertiesUtils.getInputView74()!=null && PropertiesUtils.getInputView74().equalsIgnoreCase("yes")){
                if (PropertiesUtils.getInputView741()!=null)
                    PropertiesUtils.getInputView741().setVisibility(VISIBLE);
                if (PropertiesUtils.getQuestion741()!=null)
                    PropertiesUtils.getQuestion741().setVisibility(VISIBLE);
            }
        }

        if (fieldName.equalsIgnoreCase("supporting_budget")){
            if (PropertiesUtils.getInputView74()!=null && PropertiesUtils.getInputView74().equalsIgnoreCase("yes")){
                if (PropertiesUtils.getInputView742()!=null)
                    PropertiesUtils.getInputView742().setVisibility(VISIBLE);
                if (PropertiesUtils.getQuestion742()!=null)
                    PropertiesUtils.getQuestion742().setVisibility(VISIBLE);
            }
        }

        if (fieldName.equalsIgnoreCase("closure_when")){
            if (PropertiesUtils.getInputView75()!=null && PropertiesUtils.getInputView75().equalsIgnoreCase("closure")){
                if (PropertiesUtils.getInputView751a()!=null)
                    PropertiesUtils.getInputView751a().setVisibility(VISIBLE);
                if (PropertiesUtils.getQuestion751a()!=null)
                    PropertiesUtils.getQuestion751a().setVisibility(VISIBLE);
            }
        }

        if (fieldName.equalsIgnoreCase("closure_why")){
            if (PropertiesUtils.getInputView75()!=null && PropertiesUtils.getInputView75().equalsIgnoreCase("closure")){
                if (PropertiesUtils.getInputView751b()!=null)
                    PropertiesUtils.getInputView751b().setVisibility(VISIBLE);
                if (PropertiesUtils.getQuestion751b()!=null)
                    PropertiesUtils.getQuestion751b().setVisibility(VISIBLE);
            }
        }

        if (fieldName.equalsIgnoreCase("transit_when")){
            if (PropertiesUtils.getInputView75()!=null && PropertiesUtils.getInputView75().equalsIgnoreCase("transition")){
                if (PropertiesUtils.getInputView752a()!=null)
                    PropertiesUtils.getInputView752a().setVisibility(VISIBLE);
                if (PropertiesUtils.getQuestion752a()!=null)
                    PropertiesUtils.getQuestion752a().setVisibility(VISIBLE);
            }
        }

        if (fieldName.equalsIgnoreCase("transit_why")){
            if (PropertiesUtils.getInputView75()!=null && PropertiesUtils.getInputView75().equalsIgnoreCase("transition")){
                if (PropertiesUtils.getInputView752b()!=null)
                    PropertiesUtils.getInputView752b().setVisibility(VISIBLE);
                if (PropertiesUtils.getQuestion752b()!=null)
                    PropertiesUtils.getQuestion752b().setVisibility(VISIBLE);
            }
        }

        if (fieldName.equalsIgnoreCase("support_amount")){
            if (PropertiesUtils.getInputView75()!=null && PropertiesUtils.getInputView75().equalsIgnoreCase("support_reintegration")){
                if (PropertiesUtils.getInputView753a()!=null)
                    PropertiesUtils.getInputView753a().setVisibility(VISIBLE);
                if (PropertiesUtils.getQuestion753a()!=null)
                    PropertiesUtils.getQuestion753a().setVisibility(VISIBLE);
            }
        }

        if (fieldName.equalsIgnoreCase("support_when")){
            if (PropertiesUtils.getInputView75()!=null && PropertiesUtils.getInputView75().equalsIgnoreCase("support_reintegration")){
                if (PropertiesUtils.getInputView753b()!=null)
                    PropertiesUtils.getInputView753b().setVisibility(VISIBLE);
                if (PropertiesUtils.getQuestion753b()!=null)
                    PropertiesUtils.getQuestion753b().setVisibility(VISIBLE);
            }
        }

        if (fieldName.equalsIgnoreCase("support_why")){
            if (PropertiesUtils.getInputView75()!=null && PropertiesUtils.getInputView75().equalsIgnoreCase("support_reintegration")){
                if (PropertiesUtils.getInputView753c()!=null)
                    PropertiesUtils.getInputView753c().setVisibility(VISIBLE);
                if (PropertiesUtils.getQuestion753c()!=null)
                    PropertiesUtils.getQuestion753c().setVisibility(VISIBLE);
            }
        }
    }

    protected void setupChangeListener() {
        mAnswer.addTextChangedListener(new TextWatcher() {
            private String oldText = "";

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(oldText)) {
                    Collect.getInstance().getActivityLogger()
                            .logInstanceAction(this, "answerTextChanged", s.toString(), getPrompt().getIndex());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                oldText = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });
    }

    @Override
    public void clearAnswer() {
        mAnswer.setText(null);
    }


    @Override
    public IAnswerData getAnswer() {
        clearFocus();
        String s = mAnswer.getText().toString();
        if (s == null || s.equals("")) {
            mAnswer.setFocusable(true);
            setFocus(getContext());
            return null;
        } else {
            return new StringData(s);
        }
    }


    @Override
    public void setFocus(Context context) {
        // Put focus on text input field and display soft keyboard if appropriate.
        mAnswer.requestFocus();
        InputMethodManager inputManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (!mReadOnly) {
            inputManager.showSoftInput(mAnswer, 0);
            /*
             * If you do a multi-question screen after a "add another group" dialog, this won't
             * automatically pop up. It's an Android issue.
             *
             * That is, if I have an edit text in an activity, and pop a dialog, and in that
             * dialog's button's OnClick() I call edittext.requestFocus() and
             * showSoftInput(edittext, 0), showSoftinput() returns false. However, if the edittext
             * is focused before the dialog pops up, everything works fine. great.
             */
        } else {
            inputManager.hideSoftInputFromWindow(mAnswer.getWindowToken(), 0);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.isAltPressed() == true) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void setOnLongClickListener(OnLongClickListener l) {
        mAnswer.setOnLongClickListener(l);
    }


    @Override
    public void cancelLongPress() {
        super.cancelLongPress();
        mAnswer.cancelLongPress();
    }

    @Override
    public void setDefaultValue(String value) {

    }

}
