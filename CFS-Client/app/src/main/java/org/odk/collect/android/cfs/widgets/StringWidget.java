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

import org.javarosa.core.model.Constants;
import org.javarosa.core.model.data.IAnswerData;
import org.javarosa.core.model.data.StringData;
import org.javarosa.form.api.FormEntryPrompt;
import org.odk.collect.android.cfs.R;
import org.odk.collect.android.cfs.application.Collect;
import org.odk.collect.android.cfs.utilities.PropertiesUtils;

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


        if (fieldName.equalsIgnoreCase("other_activity")){
            PropertiesUtils.setEditTextOtherActivity(mAnswer);
        }
      /* if (fieldName.equalsIgnoreCase("other_purpose")) {
            mAnswer.setVisibility(GONE);
            PropertiesUtils.setEditTextOtherPurpose(mAnswer);


        }*/


        //address hide value
        else if(fieldName.equalsIgnoreCase("address_province_code")) {
            Log.i("province_code", PropertiesUtils.getAddress1ProvinceCode());
            mAnswer.setText(PropertiesUtils.getAddress1ProvinceCode());
        } else if(fieldName.equalsIgnoreCase("address_district_code")) {
            mAnswer.setText(PropertiesUtils.getAddress1DistrictCode());
        } else if(fieldName.equalsIgnoreCase("address_commune_code")) {
            mAnswer.setText(PropertiesUtils.getAddress1CommuneCode());
        } else if(fieldName.equalsIgnoreCase("address_village_code")) {
            mAnswer.setText(PropertiesUtils.getAddress1VillageCode());
        }
       /* if (fieldName.equalsIgnoreCase("other_ngo")){
            mAnswer.setVisibility(GONE);
            PropertiesUtils.setEditTextOtherOrg(mAnswer);
        }
*/
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
        if (appearance.equalsIgnoreCase("hide")) {
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

        if (fieldName.equalsIgnoreCase("other_activity")) {
            if(PropertiesUtils.getiAnswerOtherActivity() == 1){
                PropertiesUtils.getEditTextOtherActivity().setVisibility(VISIBLE);
                PropertiesUtils.getTextViewOtherActivity().setVisibility(VISIBLE);
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
