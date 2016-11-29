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
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.javarosa.core.model.SelectChoice;
import org.javarosa.core.model.data.IAnswerData;
import org.javarosa.core.model.data.SelectOneData;
import org.javarosa.core.model.data.helper.Selection;
import org.javarosa.form.api.FormEntryCaption;
import org.javarosa.form.api.FormEntryPrompt;
import org.javarosa.xpath.expr.XPathFuncExpr;
import org.odk.collect.android.cfc.R;
import org.odk.collect.android.cfc.application.Collect;
import org.odk.collect.android.cfc.external.ExternalDataUtil;
import org.odk.collect.android.cfc.external.ExternalSelectChoice;
import org.odk.collect.android.cfc.utilities.PropertiesUtils;
import org.odk.collect.android.cfc.views.MediaLayout;

import java.math.BigDecimal;
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

        if (fieldName.equalsIgnoreCase("foster_care")){
            PropertiesUtils.setInputView621(buttonLayout);
        }

        if (fieldName.equalsIgnoreCase("kinship_care")){
            PropertiesUtils.setInputView622(buttonLayout);
        }

        if (fieldName.equalsIgnoreCase("grouphome_care")) {
            PropertiesUtils.setInputView623(buttonLayout);
        }

        if (fieldName.equalsIgnoreCase("independent_care")) {
            PropertiesUtils.setInputView624(buttonLayout);
        }

        if (fieldName.equalsIgnoreCase("religious_care")){
            PropertiesUtils.setInputView625(buttonLayout);
        }

        if (fieldName.equalsIgnoreCase("outside_service")){
            PropertiesUtils.setInputView626(buttonLayout);
        }

        ////// test 3.6
        if (fieldName.equalsIgnoreCase("license_others")) {
            PropertiesUtils.setInputView52(buttonLayout);

        }

        if (fieldName.equalsIgnoreCase("mou_mosvy")){
            PropertiesUtils.setInputView53(buttonLayout);
        }

        if (fieldName.equalsIgnoreCase("mou_ministries")){
            PropertiesUtils.setInputView54(buttonLayout);
        }
        ////////

        ///////// test condition when close program //////////
        String s = null;
        if (prompt.getAnswerValue() != null) {
            s = ((Selection) prompt.getAnswerValue().getValue()).getValue();

            /////// test 3.6
            if (fieldName.equalsIgnoreCase("donor")) {
                if (s.equalsIgnoreCase("international") || s.equalsIgnoreCase("local")){
                    PropertiesUtils.setInputView36(s);
                    if (PropertiesUtils.getInputView52()!=null)
                        PropertiesUtils.getInputView52().setVisibility(VISIBLE);
                    if (PropertiesUtils.getQuestion52()!=null)
                        PropertiesUtils.getQuestion52().setVisibility(VISIBLE);

                    if (PropertiesUtils.getInputView53()!=null)
                        PropertiesUtils.getInputView53().setVisibility(VISIBLE);
                    if (PropertiesUtils.getQuestion53()!=null)
                        PropertiesUtils.getQuestion53().setVisibility(VISIBLE);

                    if (PropertiesUtils.getInputView54()!=null)
                        PropertiesUtils.getInputView54().setVisibility(VISIBLE);
                    if (PropertiesUtils.getQuestion54()!=null)
                        PropertiesUtils.getQuestion54().setVisibility(VISIBLE);
                }
            }

            if (fieldName.equalsIgnoreCase("license_others")) {
                PropertiesUtils.setInputView52(buttonLayout);
                    if (PropertiesUtils.getInputView36()!=null && PropertiesUtils.getInputView36().equalsIgnoreCase("international") ) {
                        if (PropertiesUtils.getQuestion52() != null) {
                            PropertiesUtils.getQuestion52().setVisibility(VISIBLE);
                        }
                        if (PropertiesUtils.getInputView52() != null) {
                            PropertiesUtils.getInputView52().setVisibility(VISIBLE);
                        }
                    }else if (PropertiesUtils.getInputView36()!=null && PropertiesUtils.getInputView36().equalsIgnoreCase("local")) {
                        if (PropertiesUtils.getQuestion52() != null) {
                            PropertiesUtils.getQuestion52().setVisibility(VISIBLE);
                        }
                        if (PropertiesUtils.getInputView52() != null) {
                            PropertiesUtils.getInputView52().setVisibility(VISIBLE);
                        }
                    }
                    if (s.equalsIgnoreCase("yes")) {
                        PropertiesUtils.setAnswer52(1);
                        if (PropertiesUtils.getInputView521() != null) {
                            PropertiesUtils.getInputView521().setVisibility(VISIBLE);
                        }
                        if(PropertiesUtils.getInputView521a()!=null){
                            PropertiesUtils.getInputView521a().setVisibility(VISIBLE);
                        }
                    }
            }

            if (fieldName.equalsIgnoreCase("mou_mosvy")){
                PropertiesUtils.setInputView53(buttonLayout);

                    if (PropertiesUtils.getInputView36()!=null && PropertiesUtils.getInputView36().equalsIgnoreCase("international")) {
                        if (PropertiesUtils.getQuestion53() != null) {
                            PropertiesUtils.getQuestion53().setVisibility(VISIBLE);
                        }
                        if (PropertiesUtils.getInputView53() != null) {
                            PropertiesUtils.getInputView53().setVisibility(VISIBLE);
                        }
                    }else if (PropertiesUtils.getInputView36()!=null && PropertiesUtils.getInputView36().equalsIgnoreCase("local")) {
                        if (PropertiesUtils.getQuestion53() != null) {
                            PropertiesUtils.getQuestion53().setVisibility(VISIBLE);
                        }
                        if (PropertiesUtils.getInputView53() != null) {
                            PropertiesUtils.getInputView53().setVisibility(VISIBLE);
                        }
                    }

                    if (s.equalsIgnoreCase("yes")){
                        if (PropertiesUtils.getInputView531()!=null){
                            PropertiesUtils.getInputView531().setVisibility(VISIBLE);
                        }
                        if (PropertiesUtils.getInputView532()!=null){
                            PropertiesUtils.getInputView532().setVisibility(VISIBLE);
                        }
                        PropertiesUtils.setAnswer53(1);
                    }
            }

            if (fieldName.equalsIgnoreCase("mou_ministries")){
                PropertiesUtils.setInputView54(buttonLayout);
                    if (PropertiesUtils.getInputView36()!=null && PropertiesUtils.getInputView36().equalsIgnoreCase("international")) {
                        if (PropertiesUtils.getQuestion54() != null) {
                            PropertiesUtils.getQuestion54().setVisibility(VISIBLE);
                        }
                        if (PropertiesUtils.getInputView54() != null) {
                            PropertiesUtils.getInputView54().setVisibility(VISIBLE);
                        }
                    } else if (PropertiesUtils.getInputView36()!=null && PropertiesUtils.getInputView36().equalsIgnoreCase("local")) {
                        if (PropertiesUtils.getQuestion54() != null) {
                            PropertiesUtils.getQuestion54().setVisibility(VISIBLE);
                        }
                        if (PropertiesUtils.getInputView54() != null) {
                            PropertiesUtils.getInputView54().setVisibility(VISIBLE);
                        }
                    }
                    if (s.equalsIgnoreCase("yes")){
                        if (PropertiesUtils.getInputView541()!=null){
                            PropertiesUtils.getInputView541().setVisibility(VISIBLE);
                        }
                        PropertiesUtils.setAnswer54(1);
                    }
            }

            if (fieldName.equalsIgnoreCase("license_mosvy")) {
                if (s.equalsIgnoreCase("yes")){
                    PropertiesUtils.setInputView51(s);
                    if (PropertiesUtils.getInputView511()!=null){
                        PropertiesUtils.getInputView511().setVisibility(VISIBLE);
                    }
                }
            }
            ////////////////////
//            if (fieldName.equalsIgnoreCase("license_others")) {
//                if (s.equalsIgnoreCase("yes")){
//                    if(PropertiesUtils.getAnswer52()==1){
//                        PropertiesUtils.setInputView52(buttonLayout);
//                        if (PropertiesUtils.getInputView521()!=null){
//                            PropertiesUtils.getInputView521().setVisibility(VISIBLE);
//                        }
//                    }
//
//                }
//            }
//
//            if (fieldName.equalsIgnoreCase("mou_mosvy")) {
//                if (s.equalsIgnoreCase("yes")){
//                    PropertiesUtils.setInputView53(buttonLayout);
//                     if (PropertiesUtils.getInputView531()!=null){
//                         PropertiesUtils.getInputView531().setVisibility(VISIBLE);
//                     }
//                     if (PropertiesUtils.getInputView532()!=null){
//                         PropertiesUtils.getInputView532().setVisibility(VISIBLE);
//                     }
//                 }
//
//            }
//
//            if (fieldName.equalsIgnoreCase("mou_ministries")) {
//                if (s.equalsIgnoreCase("yes")) {
//                    PropertiesUtils.setInputView54(buttonLayout);
//                    if(PropertiesUtils.getAnswer54()==1){
//                        if (PropertiesUtils.getQuestion541()!=null)
//                            PropertiesUtils.getQuestion541().setVisibility(VISIBLE);
//                        if (PropertiesUtils.getInputView541()!=null)
//                            PropertiesUtils.getInputView541().setVisibility(VISIBLE);
//                    }
//
//                }
//            }
            //////////////////////////


            if (fieldName.equalsIgnoreCase("service_outside_center")){
                if (s.equalsIgnoreCase("yes")){
                    PropertiesUtils.setInputView62(s);
                    if (PropertiesUtils.getQuestion621()!=null)
                        PropertiesUtils.getQuestion621().setVisibility(VISIBLE);
                    if (PropertiesUtils.getInputView621()!=null)
                        PropertiesUtils.getInputView621().setVisibility(VISIBLE);

                    if (PropertiesUtils.getInputView622()!=null)
                        PropertiesUtils.getInputView622().setVisibility(VISIBLE);
                    if (PropertiesUtils.getQuestion622()!=null)
                        PropertiesUtils.getQuestion622().setVisibility(VISIBLE);

                    if (PropertiesUtils.getInputView623()!=null)
                        PropertiesUtils.getInputView623().setVisibility(VISIBLE);
                    if (PropertiesUtils.getQuestion623()!=null)
                        PropertiesUtils.getQuestion623().setVisibility(VISIBLE);


                    if (PropertiesUtils.getQuestion624()!=null)
                        PropertiesUtils.getQuestion624().setVisibility(VISIBLE);
                    if (PropertiesUtils.getInputView624()!=null)
                        PropertiesUtils.getInputView624().setVisibility(VISIBLE);

                    if (PropertiesUtils.getInputView625()!=null)
                        PropertiesUtils.getInputView625().setVisibility(VISIBLE);
                    if (PropertiesUtils.getQuestion625()!=null)
                        PropertiesUtils.getQuestion625().setVisibility(VISIBLE);

                    if (PropertiesUtils.getQuestion626()!=null)
                        PropertiesUtils.getQuestion626().setVisibility(VISIBLE);
                    if (PropertiesUtils.getInputView626()!=null)
                        PropertiesUtils.getInputView626().setVisibility(VISIBLE);

//                    if (PropertiesUtils.getInputView627()!=null)
//                        PropertiesUtils.getInputView627().setVisibility(VISIBLE);
//                    if (PropertiesUtils.getQuestion627()!=null)
//                        PropertiesUtils.getQuestion627().setVisibility(VISIBLE);
//
//                    if (PropertiesUtils.getInputView628()!=null)
//                        PropertiesUtils.getInputView628().setVisibility(VISIBLE);
//                    if (PropertiesUtils.getQuestion628()!=null)
//                        PropertiesUtils.getQuestion628().setVisibility(VISIBLE);
//
//                    if (PropertiesUtils.getInputView629()!=null)
//                        PropertiesUtils.getInputView629().setVisibility(VISIBLE);
//                    if (PropertiesUtils.getQuestion629()!=null)
//                        PropertiesUtils.getQuestion629().setVisibility(VISIBLE);
                }
            }

            /////////////////////////////

            if (fieldName.equalsIgnoreCase("foster_care")){
                PropertiesUtils.setInputView621(buttonLayout);
                if (PropertiesUtils.getInputView62()!=null && PropertiesUtils.getInputView62().equalsIgnoreCase("yes")) {
                    if (PropertiesUtils.getQuestion621() != null) {
                        PropertiesUtils.getQuestion621().setVisibility(VISIBLE);}
                    if (PropertiesUtils.getInputView621() != null) {
                        PropertiesUtils.getInputView621().setVisibility(VISIBLE);}
                    if (s.equalsIgnoreCase("yes")){
                        if (PropertiesUtils.getInputView621a()!=null){
                            PropertiesUtils.getInputView621a().setVisibility(VISIBLE);
                        }
                        if (PropertiesUtils.getInputView621b()!=null){
                            PropertiesUtils.getInputView621b().setVisibility(VISIBLE);
                        }
                        PropertiesUtils.setAnswer621(1);
                    }
                }

            }
            if (fieldName.equalsIgnoreCase("kinship_care")){
                PropertiesUtils.setInputView622(buttonLayout);
                if (PropertiesUtils.getInputView62()!=null && PropertiesUtils.getInputView62().equalsIgnoreCase("yes")) {
                    if (PropertiesUtils.getQuestion622() != null) {
                        PropertiesUtils.getQuestion622().setVisibility(VISIBLE);}
                    if (PropertiesUtils.getInputView622() != null) {
                        PropertiesUtils.getInputView622().setVisibility(VISIBLE);}
                    if (s.equalsIgnoreCase("yes")){
                        if (PropertiesUtils.getInputView622a()!=null){
                            PropertiesUtils.getInputView622a().setVisibility(VISIBLE);
                        }
                        if (PropertiesUtils.getInputView622b()!=null){
                            PropertiesUtils.getInputView622b().setVisibility(VISIBLE);
                        }
                        PropertiesUtils.setAnswer622(1);
                    }
                }
            }

            if (fieldName.equalsIgnoreCase("grouphome_care")) {
                PropertiesUtils.setInputView623(buttonLayout);
                if (PropertiesUtils.getInputView62()!=null && PropertiesUtils.getInputView62().equalsIgnoreCase("yes")) {
                    if (PropertiesUtils.getQuestion623() != null) {
                        PropertiesUtils.getQuestion623().setVisibility(VISIBLE);}
                    if (PropertiesUtils.getInputView623() != null) {
                        PropertiesUtils.getInputView623().setVisibility(VISIBLE);}
                    if (s.equalsIgnoreCase("yes")){
                        if (PropertiesUtils.getInputView623a()!=null){
                            PropertiesUtils.getInputView623a().setVisibility(VISIBLE);
                        }
                        if (PropertiesUtils.getInputView623b()!=null){
                            PropertiesUtils.getInputView623b().setVisibility(VISIBLE);
                        }
                        PropertiesUtils.setAnswer623(1);
                    }
                }
            }

            if (fieldName.equalsIgnoreCase("independent_care")) {
                PropertiesUtils.setInputView624(buttonLayout);
                if (PropertiesUtils.getInputView62()!=null && PropertiesUtils.getInputView62().equalsIgnoreCase("yes")) {
                    if (PropertiesUtils.getQuestion624() != null) {
                        PropertiesUtils.getQuestion624().setVisibility(VISIBLE);}
                    if (PropertiesUtils.getInputView624() != null) {
                        PropertiesUtils.getInputView624().setVisibility(VISIBLE);}
                    if (s.equalsIgnoreCase("yes")){
                        if (PropertiesUtils.getInputView624a()!=null){
                            PropertiesUtils.getInputView624a().setVisibility(VISIBLE);
                        }
                        if (PropertiesUtils.getInputView624b()!=null){
                            PropertiesUtils.getInputView624b().setVisibility(VISIBLE);
                        }
                        PropertiesUtils.setAnswer624(1);
                    }
                }
            }

            if (fieldName.equalsIgnoreCase("religious_care")){
                PropertiesUtils.setInputView625(buttonLayout);
                if (PropertiesUtils.getInputView62()!=null && PropertiesUtils.getInputView62().equalsIgnoreCase("yes")) {
                    if (PropertiesUtils.getInputView625() != null) {
                        PropertiesUtils.getInputView625().setVisibility(VISIBLE);}
                    if (PropertiesUtils.getQuestion625() != null) {
                        PropertiesUtils.getQuestion625().setVisibility(VISIBLE);}
                    if (s.equalsIgnoreCase("yes")){
                        if (PropertiesUtils.getInputView625a()!=null){
                            PropertiesUtils.getInputView625a().setVisibility(VISIBLE);
                        }
                        if (PropertiesUtils.getInputView625b()!=null){
                            PropertiesUtils.getInputView625b().setVisibility(VISIBLE);
                        }
                        PropertiesUtils.setAnswer625(1);
                    }
                }
            }

            if (fieldName.equalsIgnoreCase("outside_service")){
                PropertiesUtils.setInputView626(buttonLayout);
                if (PropertiesUtils.getInputView62()!=null && PropertiesUtils.getInputView62().equalsIgnoreCase("yes")){
                    if (PropertiesUtils.getQuestion626()!=null){
                        PropertiesUtils.getQuestion626().setVisibility(VISIBLE);}
                    if (PropertiesUtils.getInputView626()!=null){
                        PropertiesUtils.getInputView626().setVisibility(VISIBLE);}
                    if (s.equalsIgnoreCase("yes")){
                        if (PropertiesUtils.getInputView6261()!=null){
                            PropertiesUtils.getInputView6261().setVisibility(VISIBLE);
                        }
                        if (PropertiesUtils.getInputView626a()!=null){
                            PropertiesUtils.getInputView626a().setVisibility(VISIBLE);
                        }
                        if (PropertiesUtils.getInputView626b()!=null){
                            PropertiesUtils.getInputView626b().setVisibility(VISIBLE);
                        }
                        PropertiesUtils.setAnswer626(1);
                    }
                }
            }

            if (fieldName.equalsIgnoreCase("have_budget")) {
                if (s.equalsIgnoreCase("yes")){
                    PropertiesUtils.setInputView74(s);
                    if (PropertiesUtils.getInputView741()!=null){
                        PropertiesUtils.getInputView741().setVisibility(VISIBLE);
                    }
                    if (PropertiesUtils.getInputView742()!=null){
                        PropertiesUtils.getInputView742().setVisibility(VISIBLE);
                    }
                }
            }
            if (fieldName.equalsIgnoreCase("future_plan")) {
                if (s.equalsIgnoreCase("closure") || s.equalsIgnoreCase("transition") || s.equalsIgnoreCase("support_reintegration")){
                    PropertiesUtils.setInputView75(s);
                    if (PropertiesUtils.getInputView751a()!=null){
                        PropertiesUtils.getInputView751a().setVisibility(VISIBLE);
                    }
                    if (PropertiesUtils.getInputView751b()!=null){
                        PropertiesUtils.getInputView751b().setVisibility(VISIBLE);
                    }
                    if (PropertiesUtils.getInputView752a()!=null){
                        PropertiesUtils.getInputView752a().setVisibility(VISIBLE);
                    }
                    if (PropertiesUtils.getInputView752b()!=null){
                        PropertiesUtils.getInputView752b().setVisibility(VISIBLE);
                    }
                    if (PropertiesUtils.getInputView753a()!=null){
                        PropertiesUtils.getInputView753a().setVisibility(VISIBLE);
                    }
                    if (PropertiesUtils.getInputView753b()!=null){
                        PropertiesUtils.getInputView753b().setVisibility(VISIBLE);
                    }
                    if (PropertiesUtils.getInputView753c()!=null){
                        PropertiesUtils.getInputView753c().setVisibility(VISIBLE);
                    }
                }
            }


            ///////// student 1 ////////////

            if (fieldName.equalsIgnoreCase("ii_c1_2_31_1")) {
                if (s.equalsIgnoreCase("4") || new BigDecimal(s).toPlainString().equalsIgnoreCase("0.00001")){
                    if (new BigDecimal(s).toPlainString().equalsIgnoreCase("0.00001")){
                        PropertiesUtils.setInputView2321s1(new BigDecimal(s).toPlainString());
                    }else{
                        PropertiesUtils.setInputView2321s1(s);
                    }

                    if (PropertiesUtils.getInputView2321a1()!=null){
                        PropertiesUtils.getInputView2321a1().setVisibility(VISIBLE);
                    }
                    if (PropertiesUtils.getQuestion2321a1()!=null){
                        PropertiesUtils.getQuestion2321a1().setVisibility(VISIBLE);
                    }
                }
            }

            ///////// student 2 ////////////

            if (fieldName.equalsIgnoreCase("ii_c2_2_31_1")) {
                if (s.equalsIgnoreCase("4") || new BigDecimal(s).toPlainString().equalsIgnoreCase("0.00001")){
                    if (new BigDecimal(s).toPlainString().equalsIgnoreCase("0.00001")){
                        PropertiesUtils.setInputView2321s2(new BigDecimal(s).toPlainString());
                    }else{
                        PropertiesUtils.setInputView2321s2(s);
                    }
                    if (PropertiesUtils.getInputView2321a2()!=null){
                        PropertiesUtils.getInputView2321a2().setVisibility(VISIBLE);
                    }
                    if (PropertiesUtils.getQuestion2321a2()!=null){
                        PropertiesUtils.getQuestion2321a2().setVisibility(VISIBLE);
                    }
                }
            }

            ///////// student 3 ////////////

            if (fieldName.equalsIgnoreCase("ii_c3_2_31_1")) {
                if (s.equalsIgnoreCase("4") || new BigDecimal(s).toPlainString().equalsIgnoreCase("0.00001")){
                    if (new BigDecimal(s).toPlainString().equalsIgnoreCase("0.00001")){
                        PropertiesUtils.setInputView2321s3(new BigDecimal(s).toPlainString());
                    }else{
                        PropertiesUtils.setInputView2321s3(s);
                    }
                    if (PropertiesUtils.getInputView2321a3()!=null){
                        PropertiesUtils.getInputView2321a3().setVisibility(VISIBLE);
                    }
                    if (PropertiesUtils.getQuestion2321a3()!=null){
                        PropertiesUtils.getQuestion2321a3().setVisibility(VISIBLE);
                    }
                }
            }

            ///////// student 4 ////////////

            if (fieldName.equalsIgnoreCase("ii_c4_2_31_1")) {
                if (s.equalsIgnoreCase("4") || new BigDecimal(s).toPlainString().equalsIgnoreCase("0.00001")){
                    if (new BigDecimal(s).toPlainString().equalsIgnoreCase("0.00001")){
                        PropertiesUtils.setInputView2321s4(new BigDecimal(s).toPlainString());
                    }else{
                        PropertiesUtils.setInputView2321s4(s);
                    }
                    if (PropertiesUtils.getInputView2321a4()!=null){
                        PropertiesUtils.getInputView2321a4().setVisibility(VISIBLE);
                    }
                    if (PropertiesUtils.getQuestion2321a4()!=null){
                        PropertiesUtils.getQuestion2321a4().setVisibility(VISIBLE);
                    }
                }
            }

            ///////// student 5 ////////////

            if (fieldName.equalsIgnoreCase("ii_c5_2_31_1")) {
                if (s.equalsIgnoreCase("4") || new BigDecimal(s).toPlainString().equalsIgnoreCase("0.00001")){
                    if (new BigDecimal(s).toPlainString().equalsIgnoreCase("0.00001")){
                        PropertiesUtils.setInputView2321s5(new BigDecimal(s).toPlainString());
                    }else{
                        PropertiesUtils.setInputView2321s5(s);
                    }
                    if (PropertiesUtils.getInputView2321a5()!=null){
                        PropertiesUtils.getInputView2321a5().setVisibility(VISIBLE);
                    }
                    if (PropertiesUtils.getQuestion2321a5()!=null){
                        PropertiesUtils.getQuestion2321a5().setVisibility(VISIBLE);
                    }
                }
            }

            ///////// student1 image 1 ////////////

            if (fieldName.equalsIgnoreCase("ii_c1_2_31_2")) {
                if (s.equalsIgnoreCase("yes")){
                    PropertiesUtils.setInputView2322i1(s);
                    if (PropertiesUtils.getInputView2322a1()!=null){
                        PropertiesUtils.getInputView2322a1().setVisibility(VISIBLE);
                    }
                    if (PropertiesUtils.getQuestion2322a1()!=null){
                        PropertiesUtils.getQuestion2322a1().setVisibility(VISIBLE);
                    }
                }
            }

            ///////// student2 image 2 ////////////

            if (fieldName.equalsIgnoreCase("ii_c2_2_31_2")) {
                if (s.equalsIgnoreCase("yes")){
                    PropertiesUtils.setInputView2322i2(s);
                    if (PropertiesUtils.getInputView2322a2()!=null){
                        PropertiesUtils.getInputView2322a2().setVisibility(VISIBLE);
                    }
                    if (PropertiesUtils.getQuestion2322a2()!=null){
                        PropertiesUtils.getQuestion2322a2().setVisibility(VISIBLE);
                    }
                }
            }

            ///////// student3 image 3 ////////////

            if (fieldName.equalsIgnoreCase("ii_c3_2_31_2")) {
                if (s.equalsIgnoreCase("yes")){
                    PropertiesUtils.setInputView2322i3(s);
                    if (PropertiesUtils.getInputView2322a3()!=null){
                        PropertiesUtils.getInputView2322a3().setVisibility(VISIBLE);
                    }
                    if (PropertiesUtils.getQuestion2322a3()!=null){
                        PropertiesUtils.getQuestion2322a3().setVisibility(VISIBLE);
                    }
                }
            }

            ///////// student4 image 4 ////////////

            if (fieldName.equalsIgnoreCase("ii_c4_2_31_2")) {
                if (s.equalsIgnoreCase("yes")){
                    PropertiesUtils.setInputView2322i4(s);
                    if (PropertiesUtils.getInputView2322a4()!=null){
                        PropertiesUtils.getInputView2322a4().setVisibility(VISIBLE);
                    }
                    if (PropertiesUtils.getQuestion2322a4()!=null){
                        PropertiesUtils.getQuestion2322a4().setVisibility(VISIBLE);
                    }
                }
            }

            ///////// student5 image 5 ////////////

            if (fieldName.equalsIgnoreCase("ii_c5_2_31_2")) {
                if (s.equalsIgnoreCase("yes")){
                    PropertiesUtils.setInputView2322i5(s);
                    if (PropertiesUtils.getInputView2322a5()!=null){
                        PropertiesUtils.getInputView2322a5().setVisibility(VISIBLE);
                    }
                    if (PropertiesUtils.getQuestion2322a5()!=null){
                        PropertiesUtils.getQuestion2322a5().setVisibility(VISIBLE);
                    }
                }
            }

//            if (PropertiesUtils.getInputView62()!=null && PropertiesUtils.getInputView62().equalsIgnoreCase("yes")){
//                if (PropertiesUtils.getQuestion621()!=null){
//                    PropertiesUtils.getQuestion621().setVisibility(VISIBLE);}
//                if (PropertiesUtils.getInputView621()!=null){
//                    PropertiesUtils.getInputView621().setVisibility(VISIBLE);}
//
//
//                if (PropertiesUtils.getQuestion622()!=null){
//                    PropertiesUtils.getQuestion622().setVisibility(VISIBLE);}
//                if (PropertiesUtils.getInputView622()!=null){
//                    PropertiesUtils.getInputView622().setVisibility(VISIBLE);}
//
//                if (PropertiesUtils.getQuestion623()!=null){
//                    PropertiesUtils.getQuestion623().setVisibility(VISIBLE);}
//                if (PropertiesUtils.getInputView623()!=null){
//                    PropertiesUtils.getInputView623().setVisibility(VISIBLE);}
//
//                if (PropertiesUtils.getQuestion624()!=null){
//                    PropertiesUtils.getQuestion624().setVisibility(VISIBLE);}
//                if (PropertiesUtils.getInputView624()!=null){
//                    PropertiesUtils.getInputView624().setVisibility(VISIBLE);}
//
//                if (PropertiesUtils.getInputView625()!=null){
//                    PropertiesUtils.getInputView625().setVisibility(VISIBLE);}
//                if (PropertiesUtils.getQuestion625()!=null){
//                    PropertiesUtils.getQuestion625().setVisibility(VISIBLE)
//
//                if (PropertiesUtils.getQuestion626()!=null){
//                    PropertiesUtils.getQuestion626().setVisibility(VISIBLE);}
//                if (PropertiesUtils.getInputView626()!=null){
//                    PropertiesUtils.getInputView626().setVisibility(VISIBLE);}
//            }
//////////////////////////////

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

        // CFC Question 3.6

        if (fieldName.equalsIgnoreCase("donor")){
             if(buttons.get(0).isChecked()){
                PropertiesUtils.setInputView36("international");
                PropertiesUtils.getInputView52().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion52().setVisibility(VISIBLE);
                PropertiesUtils.getInputView53().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion53().setVisibility(VISIBLE);
                PropertiesUtils.getInputView54().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion54().setVisibility(VISIBLE);

                 if (PropertiesUtils.getInputView52()!=null) {
                     clearForm(PropertiesUtils.getInputView52());
                     if (PropertiesUtils.getInputView521()!=null) {
                         clearForm(PropertiesUtils.getInputView521());
                         PropertiesUtils.getInputView521().setVisibility(GONE);
                         PropertiesUtils.getQuestion521().setVisibility(GONE);
                     }
                     if (PropertiesUtils.getInputView521a()!=null) {
                         (PropertiesUtils.getInputView521a()).setText("");
                         PropertiesUtils.getQuestion521a().setVisibility(GONE);
                         PropertiesUtils.getInputView521a().setVisibility(GONE);
                     }
                 }
                 if (PropertiesUtils.getInputView53()!=null){
                     clearForm(PropertiesUtils.getInputView53());
                     if (PropertiesUtils.getInputView531()!=null) {
                         PropertiesUtils.getQuestion531().setVisibility(GONE);
                         PropertiesUtils.getInputView531().setVisibility(GONE);

                     } if (PropertiesUtils.getInputView532()!=null) {
                         PropertiesUtils.getQuestion532().setVisibility(GONE);
                         PropertiesUtils.getInputView532().setVisibility(GONE);
                     }
                 }
                 if (PropertiesUtils.getInputView54()!=null){
                     clearForm(PropertiesUtils.getInputView54());
                     if (PropertiesUtils.getInputView541()!=null) {
                         (PropertiesUtils.getInputView541()).setText("");
                         PropertiesUtils.getQuestion541().setVisibility(GONE);
                         PropertiesUtils.getInputView541().setVisibility(GONE);
                     }
                 }
             }
             else if(buttons.get(1).isChecked()){

                PropertiesUtils.setInputView36("local");
                PropertiesUtils.getInputView52().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion52().setVisibility(VISIBLE);
                PropertiesUtils.getInputView53().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion53().setVisibility(VISIBLE);
                PropertiesUtils.getInputView54().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion54().setVisibility(VISIBLE);

                 if (PropertiesUtils.getInputView52()!=null) {
                     clearForm(PropertiesUtils.getInputView52());
                     if (PropertiesUtils.getInputView521()!=null) {
                         clearForm(PropertiesUtils.getInputView521());
                         PropertiesUtils.getInputView521().setVisibility(GONE);
                         PropertiesUtils.getQuestion521().setVisibility(GONE);
                     }
                     if (PropertiesUtils.getInputView521a()!=null) {
                         (PropertiesUtils.getInputView521a()).setText("");
                         PropertiesUtils.getQuestion521a().setVisibility(GONE);
                         PropertiesUtils.getInputView521a().setVisibility(GONE);
                     }
                 }
                 if (PropertiesUtils.getInputView53()!=null){
                     clearForm(PropertiesUtils.getInputView53());
                     if (PropertiesUtils.getInputView531()!=null) {
                         PropertiesUtils.getQuestion531().setVisibility(GONE);
                         PropertiesUtils.getInputView531().setVisibility(GONE);

                     } if (PropertiesUtils.getInputView532()!=null) {
                         PropertiesUtils.getQuestion532().setVisibility(GONE);
                         PropertiesUtils.getInputView532().setVisibility(GONE);
                     }
                 }
                 if (PropertiesUtils.getInputView54()!=null){
                     clearForm(PropertiesUtils.getInputView54());
                     if (PropertiesUtils.getInputView541()!=null) {
                         (PropertiesUtils.getInputView541()).setText("");
                         PropertiesUtils.getQuestion541().setVisibility(GONE);
                         PropertiesUtils.getInputView541().setVisibility(GONE);
                     }
                 }
             }
             else{
                PropertiesUtils.setInputView36("state");
                PropertiesUtils.getInputView52().setVisibility(GONE);
                PropertiesUtils.getQuestion52().setVisibility(GONE);
                PropertiesUtils.getInputView521().setVisibility(GONE);
                PropertiesUtils.getQuestion521().setVisibility(GONE);
                PropertiesUtils.getInputView521a().setVisibility(GONE);
                PropertiesUtils.getQuestion521a().setVisibility(GONE);

                PropertiesUtils.getInputView53().setVisibility(GONE);
                PropertiesUtils.getQuestion53().setVisibility(GONE);
                PropertiesUtils.getInputView531().setVisibility(GONE);
                PropertiesUtils.getInputView532().setVisibility(GONE);
                PropertiesUtils.getQuestion532().setVisibility(GONE);
                PropertiesUtils.getQuestion531().setVisibility(GONE);

                PropertiesUtils.getInputView54().setVisibility(GONE);
                PropertiesUtils.getQuestion54().setVisibility(GONE);
                PropertiesUtils.getInputView541().setVisibility(GONE);
                PropertiesUtils.getQuestion541().setVisibility(GONE);

                 if (PropertiesUtils.getInputView52()!=null) {
                     clearForm(PropertiesUtils.getInputView52());
                     if (PropertiesUtils.getInputView521()!=null) {
                         clearForm(PropertiesUtils.getInputView521());
                         PropertiesUtils.getInputView521().setVisibility(GONE);
                         PropertiesUtils.getQuestion521().setVisibility(GONE);
                     }
                     if (PropertiesUtils.getInputView521a()!=null) {
                         (PropertiesUtils.getInputView521a()).setText("");
                         PropertiesUtils.getQuestion521a().setVisibility(GONE);
                         PropertiesUtils.getInputView521a().setVisibility(GONE);
                     }
                 }
                 if (PropertiesUtils.getInputView53()!=null){
                     clearForm(PropertiesUtils.getInputView53());
                     if (PropertiesUtils.getInputView531()!=null) {
                         PropertiesUtils.getQuestion531().setVisibility(GONE);
                         PropertiesUtils.getInputView531().setVisibility(GONE);

                     } if (PropertiesUtils.getInputView532()!=null) {
                         PropertiesUtils.getQuestion532().setVisibility(GONE);
                         PropertiesUtils.getInputView532().setVisibility(GONE);
                     }
                 }
                 if (PropertiesUtils.getInputView54()!=null){
                     clearForm(PropertiesUtils.getInputView54());
                     if (PropertiesUtils.getInputView541()!=null) {
                         (PropertiesUtils.getInputView541()).setText("");
                         PropertiesUtils.getQuestion541().setVisibility(GONE);
                         PropertiesUtils.getInputView541().setVisibility(GONE);
                     }
                 }

            }

        }

        //  CFC Question 5.1

        if (fieldName.equalsIgnoreCase("license_mosvy")) {
            if (buttons.get(0).isChecked()) {
                PropertiesUtils.getQuestion511().setVisibility(VISIBLE);
                PropertiesUtils.getInputView511().setVisibility(VISIBLE);
                PropertiesUtils.setInputView51("yes");
            } else {
                PropertiesUtils.getQuestion511().setVisibility(GONE);
                PropertiesUtils.getInputView511().setVisibility(GONE);
                PropertiesUtils.setInputView51("no");
                clearDatePickerForm(PropertiesUtils.getInputView511());
            }
        }

        //  CFC Question 5.2

        if (fieldName.equalsIgnoreCase("license_others")) {
            if (buttons.get(0).isChecked()) {
                PropertiesUtils.setAnswer52(1);
                PropertiesUtils.setInputView36("local");
                PropertiesUtils.setInputView36("international");
                if (PropertiesUtils.getQuestion521()!=null && PropertiesUtils.getInputView521()!=null){
                    PropertiesUtils.getQuestion521().setVisibility(VISIBLE);
                    PropertiesUtils.getInputView521().setVisibility(VISIBLE);
                }

            } else {
                PropertiesUtils.setAnswer52(0);
                PropertiesUtils.setInputView36("state");
                if (PropertiesUtils.getQuestion521()!=null ){
                    PropertiesUtils.getQuestion521().setVisibility(GONE);
                    PropertiesUtils.getInputView521().setVisibility(GONE);
                }
                if(PropertiesUtils.getQuestion521a()!=null){
                    PropertiesUtils.getInputView521a().setVisibility(GONE);
                    PropertiesUtils.getQuestion521a().setVisibility(GONE);
                }
                clearForm(PropertiesUtils.getInputView521());
                PropertiesUtils.getInputView521a().setText("");
            }
        }

        //  CFC Question 5.3

        if (fieldName.equalsIgnoreCase("mou_mosvy")) {
            if (buttons.get(0).isChecked()) {
                PropertiesUtils.setAnswer53(1);
                PropertiesUtils.setInputView36("local");
                PropertiesUtils.setInputView36("international");
                if (PropertiesUtils.getQuestion531()!=null && PropertiesUtils.getQuestion532()!=null){
                    PropertiesUtils.getQuestion531().setVisibility(VISIBLE);
                    PropertiesUtils.getInputView531().setVisibility(VISIBLE);
                    PropertiesUtils.getQuestion532().setVisibility(VISIBLE);
                    PropertiesUtils.getInputView532().setVisibility(VISIBLE);
                }
            } else {
                PropertiesUtils.setAnswer53(0);
                PropertiesUtils.setInputView36("local");
                PropertiesUtils.setInputView36("international");
                if (PropertiesUtils.getQuestion531()!=null && PropertiesUtils.getQuestion532()!=null){
                    PropertiesUtils.getQuestion531().setVisibility(GONE);
                    PropertiesUtils.getInputView531().setVisibility(GONE);
                    PropertiesUtils.getQuestion532().setVisibility(GONE);
                    PropertiesUtils.getInputView532().setVisibility(GONE);
                }
                clearDatePickerForm(PropertiesUtils.getInputView531());
                clearDatePickerForm(PropertiesUtils.getInputView532());
            }
        }

        //  CFC Question 5.4

        if (fieldName.equalsIgnoreCase("mou_ministries")) {
            if (buttons.get(0).isChecked()) {
                PropertiesUtils.setAnswer54(1);
                PropertiesUtils.setInputView36("local");
                PropertiesUtils.setInputView36("international");
                if (PropertiesUtils.getQuestion541()!=null && PropertiesUtils.getInputView541()!=null) {
                    PropertiesUtils.getQuestion541().setVisibility(VISIBLE);
                    PropertiesUtils.getInputView541().setVisibility(VISIBLE);
                }
            }
            else {
                PropertiesUtils.setAnswer54(0);
                PropertiesUtils.setInputView36("local");
                PropertiesUtils.setInputView36("international");
                if (PropertiesUtils.getQuestion541()!=null && PropertiesUtils.getInputView541()!=null) {
                    PropertiesUtils.getQuestion541().setVisibility(GONE);
                    PropertiesUtils.getInputView541().setVisibility(GONE);
                    PropertiesUtils.getInputView541().setText("");
                }
            }
        }


        if (fieldName.equalsIgnoreCase("service_outside_center")) {
            if (buttons.get(0).isChecked()) {
                PropertiesUtils.getQuestion621().setVisibility(VISIBLE);
                PropertiesUtils.getInputView621().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion622().setVisibility(VISIBLE);
                PropertiesUtils.getInputView622().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion623().setVisibility(VISIBLE);
                PropertiesUtils.getInputView623().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion624().setVisibility(VISIBLE);
                PropertiesUtils.getInputView624().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion625().setVisibility(VISIBLE);
                PropertiesUtils.getInputView625().setVisibility(VISIBLE);
                PropertiesUtils.getQuestion626().setVisibility(VISIBLE);
                PropertiesUtils.getInputView626().setVisibility(VISIBLE);
                PropertiesUtils.setInputView62("yes");
                if (PropertiesUtils.getInputView621()!=null){
                    clearForm(PropertiesUtils.getInputView621());
                    if (PropertiesUtils.getInputView621a()!=null) {
                        (PropertiesUtils.getInputView621a()).setText("");
                        PropertiesUtils.getQuestion621a().setVisibility(GONE);
                        PropertiesUtils.getInputView621a().setVisibility(GONE);

                    } if (PropertiesUtils.getInputView621b()!=null) {
                        (PropertiesUtils.getInputView621b()).setText("");
                        PropertiesUtils.getQuestion621b().setVisibility(GONE);
                        PropertiesUtils.getInputView621b().setVisibility(GONE);
                    }
                }
                if (PropertiesUtils.getInputView622()!=null){
                    clearForm(PropertiesUtils.getInputView622());
                    if (PropertiesUtils.getInputView622a()!=null) {
                        (PropertiesUtils.getInputView622a()).setText("");
                        PropertiesUtils.getQuestion622a().setVisibility(GONE);
                        PropertiesUtils.getInputView622a().setVisibility(GONE);

                    } if (PropertiesUtils.getInputView622b()!=null) {
                        (PropertiesUtils.getInputView622b()).setText("");
                        PropertiesUtils.getQuestion622b().setVisibility(GONE);
                        PropertiesUtils.getInputView622b().setVisibility(GONE);
                    }
                }
                if (PropertiesUtils.getInputView623()!=null){
                    clearForm(PropertiesUtils.getInputView623());
                    if (PropertiesUtils.getInputView623a()!=null) {
                        (PropertiesUtils.getInputView623a()).setText("");
                        PropertiesUtils.getQuestion623a().setVisibility(GONE);
                        PropertiesUtils.getInputView623a().setVisibility(GONE);

                    } if (PropertiesUtils.getInputView623b()!=null) {
                        (PropertiesUtils.getInputView623b()).setText("");
                        PropertiesUtils.getQuestion623b().setVisibility(GONE);
                        PropertiesUtils.getInputView623b().setVisibility(GONE);
                    }
                }
                if (PropertiesUtils.getInputView624()!=null){
                    clearForm(PropertiesUtils.getInputView624());
                    if (PropertiesUtils.getInputView624a()!=null) {
                        (PropertiesUtils.getInputView624a()).setText("");
                        PropertiesUtils.getQuestion624a().setVisibility(GONE);
                        PropertiesUtils.getInputView624a().setVisibility(GONE);

                    } if (PropertiesUtils.getInputView624b()!=null) {
                        (PropertiesUtils.getInputView624b()).setText("");
                        PropertiesUtils.getQuestion624b().setVisibility(GONE);
                        PropertiesUtils.getInputView624b().setVisibility(GONE);
                    }
                }
                if (PropertiesUtils.getInputView625()!=null){
                    clearForm(PropertiesUtils.getInputView625());
                    if (PropertiesUtils.getInputView625a()!=null) {
                        (PropertiesUtils.getInputView625a()).setText("");
                        PropertiesUtils.getQuestion625a().setVisibility(GONE);
                        PropertiesUtils.getInputView625a().setVisibility(GONE);

                    } if (PropertiesUtils.getInputView625b()!=null) {
                        (PropertiesUtils.getInputView625b()).setText("");
                        PropertiesUtils.getQuestion625b().setVisibility(GONE);
                        PropertiesUtils.getInputView625b().setVisibility(GONE);
                    }
                }
                if (PropertiesUtils.getInputView626()!=null){
                    clearForm(PropertiesUtils.getInputView626());
                    if (PropertiesUtils.getInputView6261()!=null) {
                        (PropertiesUtils.getInputView6261()).setText("");
                        PropertiesUtils.getQuestion6261().setVisibility(GONE);
                        PropertiesUtils.getInputView6261().setVisibility(GONE);

                    }if (PropertiesUtils.getInputView626a()!=null) {
                        (PropertiesUtils.getInputView626a()).setText("");
                        PropertiesUtils.getQuestion626a().setVisibility(GONE);
                        PropertiesUtils.getInputView626a().setVisibility(GONE);

                    } if (PropertiesUtils.getInputView626b()!=null) {
                        (PropertiesUtils.getInputView626b()).setText("");
                        PropertiesUtils.getQuestion626b().setVisibility(GONE);
                        PropertiesUtils.getInputView626b().setVisibility(GONE);
                    }
                }
//                PropertiesUtils.getQuestion627().setVisibility(VISIBLE);
//                PropertiesUtils.getInputView627().setVisibility(VISIBLE);
//                PropertiesUtils.getQuestion628().setVisibility(VISIBLE);
//                PropertiesUtils.getInputView628().setVisibility(VISIBLE);
//                PropertiesUtils.getQuestion629().setVisibility(VISIBLE);
//                PropertiesUtils.getInputView629().setVisibility(VISIBLE);

//                PropertiesUtils.getQuestion629a().setVisibility(VISIBLE);
//                PropertiesUtils.getInputView629a().setVisibility(VISIBLE);
//                PropertiesUtils.getQuestion6291().setVisibility(VISIBLE);
//                PropertiesUtils.getInputView6291().setVisibility(VISIBLE);
//                PropertiesUtils.getQuestion6292().setVisibility(VISIBLE);
//                PropertiesUtils.getInputView6292().setVisibility(VISIBLE);

            } else {
                PropertiesUtils.getQuestion621().setVisibility(GONE);
                PropertiesUtils.getInputView621().setVisibility(GONE);

                PropertiesUtils.getQuestion621a().setVisibility(GONE);
                PropertiesUtils.getInputView621a().setVisibility(GONE);

                PropertiesUtils.getQuestion621b().setVisibility(GONE);
                PropertiesUtils.getInputView621b().setVisibility(GONE);

                PropertiesUtils.getQuestion622().setVisibility(GONE);
                PropertiesUtils.getInputView622().setVisibility(GONE);

                PropertiesUtils.getQuestion622a().setVisibility(GONE);
                PropertiesUtils.getInputView622a().setVisibility(GONE);

                PropertiesUtils.getQuestion622b().setVisibility(GONE);
                PropertiesUtils.getInputView622b().setVisibility(GONE);

                PropertiesUtils.getQuestion623().setVisibility(GONE);
                PropertiesUtils.getInputView623().setVisibility(GONE);

                PropertiesUtils.getQuestion623a().setVisibility(GONE);
                PropertiesUtils.getInputView623a().setVisibility(GONE);

                PropertiesUtils.getQuestion623b().setVisibility(GONE);
                PropertiesUtils.getInputView623b().setVisibility(GONE);

                PropertiesUtils.getQuestion624().setVisibility(GONE);
                PropertiesUtils.getInputView624().setVisibility(GONE);

                PropertiesUtils.getQuestion624a().setVisibility(GONE);
                PropertiesUtils.getInputView624a().setVisibility(GONE);

                PropertiesUtils.getQuestion624b().setVisibility(GONE);
                PropertiesUtils.getInputView624b().setVisibility(GONE);

                PropertiesUtils.getQuestion625().setVisibility(GONE);
                PropertiesUtils.getInputView625().setVisibility(GONE);

                PropertiesUtils.getQuestion625a().setVisibility(GONE);
                PropertiesUtils.getInputView625a().setVisibility(GONE);

                PropertiesUtils.getQuestion625b().setVisibility(GONE);
                PropertiesUtils.getInputView625b().setVisibility(GONE);

                PropertiesUtils.getQuestion626().setVisibility(GONE);
                PropertiesUtils.getInputView626().setVisibility(GONE);

                PropertiesUtils.getQuestion6261().setVisibility(GONE);
                PropertiesUtils.getInputView6261().setVisibility(GONE);

                PropertiesUtils.getQuestion626a().setVisibility(GONE);
                PropertiesUtils.getInputView626a().setVisibility(GONE);

                PropertiesUtils.getQuestion626b().setVisibility(GONE);
                PropertiesUtils.getInputView626b().setVisibility(GONE);
                PropertiesUtils.setInputView62("no");
                if (PropertiesUtils.getInputView621()!=null){
                    clearForm(PropertiesUtils.getInputView621());
                    if (PropertiesUtils.getInputView621a()!=null) {
                        (PropertiesUtils.getInputView621a()).setText("");
                        PropertiesUtils.getQuestion621a().setVisibility(GONE);
                        PropertiesUtils.getInputView621a().setVisibility(GONE);

                    } if (PropertiesUtils.getInputView621b()!=null) {
                        (PropertiesUtils.getInputView621b()).setText("");
                        PropertiesUtils.getQuestion621b().setVisibility(GONE);
                        PropertiesUtils.getInputView621b().setVisibility(GONE);
                    }
                }
                if (PropertiesUtils.getInputView622()!=null){
                    clearForm(PropertiesUtils.getInputView622());
                    if (PropertiesUtils.getInputView622a()!=null) {
                        (PropertiesUtils.getInputView622a()).setText("");
                        PropertiesUtils.getQuestion622a().setVisibility(GONE);
                        PropertiesUtils.getInputView622a().setVisibility(GONE);

                    } if (PropertiesUtils.getInputView622b()!=null) {
                        (PropertiesUtils.getInputView622b()).setText("");
                        PropertiesUtils.getQuestion622b().setVisibility(GONE);
                        PropertiesUtils.getInputView622b().setVisibility(GONE);
                    }
                }
                if (PropertiesUtils.getInputView623()!=null){
                    clearForm(PropertiesUtils.getInputView623());
                    if (PropertiesUtils.getInputView623a()!=null) {
                        (PropertiesUtils.getInputView623a()).setText("");
                        PropertiesUtils.getQuestion623a().setVisibility(GONE);
                        PropertiesUtils.getInputView623a().setVisibility(GONE);

                    } if (PropertiesUtils.getInputView623b()!=null) {
                        (PropertiesUtils.getInputView623b()).setText("");
                        PropertiesUtils.getQuestion623b().setVisibility(GONE);
                        PropertiesUtils.getInputView623b().setVisibility(GONE);
                    }
                }
                if (PropertiesUtils.getInputView624()!=null){
                    clearForm(PropertiesUtils.getInputView624());
                    if (PropertiesUtils.getInputView624a()!=null) {
                        (PropertiesUtils.getInputView624a()).setText("");
                        PropertiesUtils.getQuestion624a().setVisibility(GONE);
                        PropertiesUtils.getInputView624a().setVisibility(GONE);

                    } if (PropertiesUtils.getInputView624b()!=null) {
                        (PropertiesUtils.getInputView624b()).setText("");
                        PropertiesUtils.getQuestion624b().setVisibility(GONE);
                        PropertiesUtils.getInputView624b().setVisibility(GONE);
                    }
                }
                if (PropertiesUtils.getInputView625()!=null){
                    clearForm(PropertiesUtils.getInputView625());
                    if (PropertiesUtils.getInputView625a()!=null) {
                        (PropertiesUtils.getInputView625a()).setText("");
                        PropertiesUtils.getQuestion625a().setVisibility(GONE);
                        PropertiesUtils.getInputView625a().setVisibility(GONE);

                    } if (PropertiesUtils.getInputView625b()!=null) {
                        (PropertiesUtils.getInputView625b()).setText("");
                        PropertiesUtils.getQuestion625b().setVisibility(GONE);
                        PropertiesUtils.getInputView625b().setVisibility(GONE);
                    }
                }
                if (PropertiesUtils.getInputView626()!=null){
                    clearForm(PropertiesUtils.getInputView626());
                    if (PropertiesUtils.getInputView6261()!=null) {
                        (PropertiesUtils.getInputView6261()).setText("");
                        PropertiesUtils.getQuestion6261().setVisibility(GONE);
                        PropertiesUtils.getInputView6261().setVisibility(GONE);

                    }if (PropertiesUtils.getInputView626a()!=null) {
                        (PropertiesUtils.getInputView626a()).setText("");
                        PropertiesUtils.getQuestion626a().setVisibility(GONE);
                        PropertiesUtils.getInputView626a().setVisibility(GONE);

                    } if (PropertiesUtils.getInputView626b()!=null) {
                        (PropertiesUtils.getInputView626b()).setText("");
                        PropertiesUtils.getQuestion626b().setVisibility(GONE);
                        PropertiesUtils.getInputView626b().setVisibility(GONE);
                    }
                }
//                PropertiesUtils.getQuestion627().setVisibility(GONE);
//                PropertiesUtils.getInputView627().setVisibility(GONE);
//                PropertiesUtils.getQuestion628().setVisibility(GONE);
//                PropertiesUtils.getInputView628().setVisibility(GONE);
//                PropertiesUtils.getQuestion629().setVisibility(GONE);
//                PropertiesUtils.getInputView629().setVisibility(GONE);


//                PropertiesUtils.getInputView621().setText("");
//                PropertiesUtils.getInputView622().setText("");
//                PropertiesUtils.getInputView623().setText("");
//                PropertiesUtils.getInputView624().setText("");
//                PropertiesUtils.getInputView625().setText("");
//                PropertiesUtils.getInputView626().setText("");
//                PropertiesUtils.getInputView627().setText("");
//                PropertiesUtils.getInputView628().setText("");


//                PropertiesUtils.getInputView629a().setText("");
//                PropertiesUtils.getInputView6291().setText("");
//                PropertiesUtils.getInputView6292().setText("")
            }
        }


        if (fieldName.equalsIgnoreCase("foster_care")) {
            if (buttons.get(0).isChecked()) {
                if (PropertiesUtils.getQuestion621a()!=null && PropertiesUtils.getQuestion621b()!=null) {
                    PropertiesUtils.getQuestion621a().setVisibility(VISIBLE);
                    PropertiesUtils.getInputView621a().setVisibility(VISIBLE);
                    PropertiesUtils.getQuestion621b().setVisibility(VISIBLE);
                    PropertiesUtils.getInputView621b().setVisibility(VISIBLE);
                }
                PropertiesUtils.setAnswer621(1);
                PropertiesUtils.setInputView62("yes");
            } else {
                if (PropertiesUtils.getQuestion621a()!=null && PropertiesUtils.getQuestion621b()!=null) {
                    PropertiesUtils.getQuestion621a().setVisibility(GONE);
                    PropertiesUtils.getInputView621a().setVisibility(GONE);
                    PropertiesUtils.getQuestion621b().setVisibility(GONE);
                    PropertiesUtils.getInputView621b().setVisibility(GONE);
                    PropertiesUtils.getInputView621a().setText("");
                    PropertiesUtils.getInputView621b().setText("");
                }
                PropertiesUtils.setAnswer621(0);
                PropertiesUtils.setInputView62("no");
            }
        }

        if (fieldName.equalsIgnoreCase("kinship_care")) {
            if (buttons.get(0).isChecked()) {
                if (PropertiesUtils.getQuestion622a()!=null && PropertiesUtils.getQuestion622b()!=null) {
                    PropertiesUtils.getQuestion622a().setVisibility(VISIBLE);
                    PropertiesUtils.getInputView622a().setVisibility(VISIBLE);
                    PropertiesUtils.getQuestion622b().setVisibility(VISIBLE);
                    PropertiesUtils.getInputView622b().setVisibility(VISIBLE);
                }
                PropertiesUtils.setAnswer622(1);
                PropertiesUtils.setInputView62("yes");
            } else {
                if (PropertiesUtils.getQuestion622a()!=null && PropertiesUtils.getQuestion622b()!=null) {
                    PropertiesUtils.getQuestion622a().setVisibility(GONE);
                    PropertiesUtils.getInputView622a().setVisibility(GONE);
                    PropertiesUtils.getQuestion622b().setVisibility(GONE);
                    PropertiesUtils.getInputView622b().setVisibility(GONE);
                    PropertiesUtils.getInputView622a().setText("");
                    PropertiesUtils.getInputView622b().setText("");
                }
                PropertiesUtils.setAnswer622(0);
                PropertiesUtils.setInputView62("no");
            }
        }

        if (fieldName.equalsIgnoreCase("grouphome_care")) {
            if (buttons.get(0).isChecked()) {
                if (PropertiesUtils.getQuestion623a()!=null && PropertiesUtils.getQuestion623b()!=null) {
                    PropertiesUtils.getQuestion623a().setVisibility(VISIBLE);
                    PropertiesUtils.getInputView623a().setVisibility(VISIBLE);
                    PropertiesUtils.getQuestion623b().setVisibility(VISIBLE);
                    PropertiesUtils.getInputView623b().setVisibility(VISIBLE);
                }
                PropertiesUtils.setAnswer623(1);
                PropertiesUtils.setInputView62("yes");
            } else {
                if (PropertiesUtils.getQuestion623a()!=null && PropertiesUtils.getQuestion623b()!=null) {
                    PropertiesUtils.getQuestion623a().setVisibility(GONE);
                    PropertiesUtils.getInputView623a().setVisibility(GONE);
                    PropertiesUtils.getQuestion623b().setVisibility(GONE);
                    PropertiesUtils.getInputView623b().setVisibility(GONE);
                    PropertiesUtils.getInputView623a().setText("");
                    PropertiesUtils.getInputView623b().setText("");
                }
                PropertiesUtils.setAnswer623(0);
                PropertiesUtils.setInputView62("no");
            }
        }

        if (fieldName.equalsIgnoreCase("independent_care")) {
            if (buttons.get(0).isChecked()) {
                if (PropertiesUtils.getQuestion624a()!=null && PropertiesUtils.getQuestion624b()!=null) {
                    PropertiesUtils.getQuestion624a().setVisibility(VISIBLE);
                    PropertiesUtils.getInputView624a().setVisibility(VISIBLE);
                    PropertiesUtils.getQuestion624b().setVisibility(VISIBLE);
                    PropertiesUtils.getInputView624b().setVisibility(VISIBLE);
                }
                PropertiesUtils.setAnswer624(1);
                PropertiesUtils.setInputView62("yes");
            }else {
                if (PropertiesUtils.getQuestion624a()!=null && PropertiesUtils.getQuestion624b()!=null) {
                    PropertiesUtils.getQuestion624a().setVisibility(GONE);
                    PropertiesUtils.getInputView624a().setVisibility(GONE);
                    PropertiesUtils.getQuestion624b().setVisibility(GONE);
                    PropertiesUtils.getInputView624b().setVisibility(GONE);
                    PropertiesUtils.getInputView624a().setText("");
                    PropertiesUtils.getInputView624b().setText("");
                }
                PropertiesUtils.setAnswer624(0);
                PropertiesUtils.setInputView62("no");
            }
        }

        if (fieldName.equalsIgnoreCase("religious_care")) {
            if (buttons.get(0).isChecked()) {
                if (PropertiesUtils.getQuestion625a()!=null && PropertiesUtils.getQuestion625b()!=null) {
                    PropertiesUtils.getQuestion625a().setVisibility(VISIBLE);
                    PropertiesUtils.getInputView625a().setVisibility(VISIBLE);
                    PropertiesUtils.getQuestion625b().setVisibility(VISIBLE);
                    PropertiesUtils.getInputView625b().setVisibility(VISIBLE);
                }
                PropertiesUtils.setAnswer625(1);
                PropertiesUtils.setInputView62("yes");
            } else {
                if (PropertiesUtils.getQuestion625a()!=null && PropertiesUtils.getQuestion625b()!=null) {
                    PropertiesUtils.getQuestion625a().setVisibility(GONE);
                    PropertiesUtils.getInputView625a().setVisibility(GONE);
                    PropertiesUtils.getQuestion625b().setVisibility(GONE);
                    PropertiesUtils.getInputView625b().setVisibility(GONE);
                    PropertiesUtils.getInputView625a().setText("");
                    PropertiesUtils.getInputView625b().setText("");
                }
                PropertiesUtils.setAnswer625(0);
                PropertiesUtils.setInputView62("no");
            }
        }

        if (fieldName.equalsIgnoreCase("outside_service")) {
            if (buttons.get(0).isChecked()) {
                if (PropertiesUtils.getQuestion626a()!=null && PropertiesUtils.getQuestion626b()!=null) {
                    PropertiesUtils.getQuestion6261().setVisibility(VISIBLE);
                    PropertiesUtils.getInputView6261().setVisibility(VISIBLE);
                    PropertiesUtils.getQuestion626a().setVisibility(VISIBLE);
                    PropertiesUtils.getInputView626a().setVisibility(VISIBLE);
                    PropertiesUtils.getQuestion626b().setVisibility(VISIBLE);
                    PropertiesUtils.getInputView626b().setVisibility(VISIBLE);
                }
                PropertiesUtils.setAnswer626(1);
                PropertiesUtils.setInputView62("yes");
            } else {
                if (PropertiesUtils.getQuestion626a()!=null && PropertiesUtils.getQuestion626b()!=null) {
                    PropertiesUtils.getQuestion6261().setVisibility(GONE);
                    PropertiesUtils.getInputView6261().setVisibility(GONE);
                    PropertiesUtils.getQuestion626a().setVisibility(GONE);
                    PropertiesUtils.getInputView626a().setVisibility(GONE);
                    PropertiesUtils.getQuestion626b().setVisibility(GONE);
                    PropertiesUtils.getInputView626b().setVisibility(GONE);
                    PropertiesUtils.getInputView6261().setText("");
                    PropertiesUtils.getInputView626a().setText("");
                    PropertiesUtils.getInputView626b().setText("");
                }
                PropertiesUtils.setAnswer626(1);
                PropertiesUtils.setInputView62("no");
//                PropertiesUtils.getInputView629a().setText("");
//                PropertiesUtils.getInputView6291().setText("");
//                PropertiesUtils.getInputView6292().setText("");
            }
        }

        //  CFC Question 7.4

        if (fieldName.equalsIgnoreCase("have_budget")) {
            if (buttons.get(0).isChecked()) {
                PropertiesUtils.setInputView74("yes");
                if (PropertiesUtils.getQuestion741()!=null && PropertiesUtils.getQuestion742()!=null) {
                    PropertiesUtils.getQuestion741().setVisibility(VISIBLE);
                    PropertiesUtils.getInputView741().setVisibility(VISIBLE);
                    PropertiesUtils.getQuestion742().setVisibility(VISIBLE);
                    PropertiesUtils.getInputView742().setVisibility(VISIBLE);
                }
            } else {
                PropertiesUtils.setInputView74("no");
                if (PropertiesUtils.getQuestion741()!=null && PropertiesUtils.getQuestion742()!=null) {
                    PropertiesUtils.getQuestion741().setVisibility(GONE);
                    PropertiesUtils.getInputView741().setVisibility(GONE);
                    PropertiesUtils.getQuestion742().setVisibility(GONE);
                    PropertiesUtils.getInputView742().setVisibility(GONE);
                    PropertiesUtils.getInputView741().setText("");
                    PropertiesUtils.getInputView742().setText("");
                }
            }
        }

        //  CFC Question 7.5

        if (fieldName.equalsIgnoreCase("future_plan")){
            if (buttons.get(0).isChecked()) {
                PropertiesUtils.setInputView75("closure");
                if (PropertiesUtils.getQuestion751a()!=null && PropertiesUtils.getQuestion751b()!=null) {
                    PropertiesUtils.getInputView751a().setVisibility(VISIBLE);
                    PropertiesUtils.getQuestion751a().setVisibility(VISIBLE);
                    PropertiesUtils.getInputView751b().setVisibility(VISIBLE);
                    PropertiesUtils.getQuestion751b().setVisibility(VISIBLE);
                }
            }
            else{
                if (PropertiesUtils.getQuestion751a()!=null && PropertiesUtils.getQuestion751b()!=null) {
                    PropertiesUtils.getInputView751a().setVisibility(GONE);
                    PropertiesUtils.getQuestion751a().setVisibility(GONE);
                    PropertiesUtils.getInputView751b().setVisibility(GONE);
                    PropertiesUtils.getQuestion751b().setVisibility(GONE);
                    PropertiesUtils.getInputView751a().setText("");
                    PropertiesUtils.getInputView751b().setText("");
                }
            }

            if (buttons.get(1).isChecked()) {
                PropertiesUtils.setInputView75("transition");
                if (PropertiesUtils.getQuestion752a()!=null && PropertiesUtils.getQuestion752b()!=null) {
                    PropertiesUtils.getInputView752a().setVisibility(VISIBLE);
                    PropertiesUtils.getQuestion752a().setVisibility(VISIBLE);
                    PropertiesUtils.getInputView752b().setVisibility(VISIBLE);
                    PropertiesUtils.getQuestion752b().setVisibility(VISIBLE);
                }
            }
            else {
                if (PropertiesUtils.getQuestion752a()!=null && PropertiesUtils.getQuestion752b()!=null) {
                    PropertiesUtils.getInputView752a().setVisibility(GONE);
                    PropertiesUtils.getQuestion752a().setVisibility(GONE);
                    PropertiesUtils.getInputView752b().setVisibility(GONE);
                    PropertiesUtils.getQuestion752b().setVisibility(GONE);
                    PropertiesUtils.getInputView752a().setText("");
                    PropertiesUtils.getInputView752b().setText("");
                }
            }

            if (buttons.get(2).isChecked()) {
                PropertiesUtils.setInputView75("support_reintegration");
                if (PropertiesUtils.getQuestion753a()!=null && PropertiesUtils.getQuestion753b()!=null&& PropertiesUtils.getQuestion753c()!=null) {
                    PropertiesUtils.getInputView753a().setVisibility(VISIBLE);
                    PropertiesUtils.getQuestion753a().setVisibility(VISIBLE);
                    PropertiesUtils.getInputView753b().setVisibility(VISIBLE);
                    PropertiesUtils.getQuestion753b().setVisibility(VISIBLE);
                    PropertiesUtils.getInputView753c().setVisibility(VISIBLE);
                    PropertiesUtils.getQuestion753c().setVisibility(VISIBLE);
                }
            }
            else{
                if (PropertiesUtils.getQuestion753a()!=null && PropertiesUtils.getQuestion753b()!=null && PropertiesUtils.getQuestion753c()!=null) {
                    PropertiesUtils.getInputView753a().setVisibility(GONE);
                    PropertiesUtils.getQuestion753a().setVisibility(GONE);
                    PropertiesUtils.getInputView753b().setVisibility(GONE);
                    PropertiesUtils.getQuestion753b().setVisibility(GONE);
                    PropertiesUtils.getInputView753c().setVisibility(GONE);
                    PropertiesUtils.getQuestion753c().setVisibility(GONE);
                    PropertiesUtils.getInputView753a().setText("");
                    PropertiesUtils.getInputView753b().setText("");
                    PropertiesUtils.getInputView753c().setText("");
                }
            }
        }
//                });
//            }
//        }


        //  CFC ask student1 Question 2.31.1
        if (fieldName.equalsIgnoreCase("ii_c1_2_31_1")) {
            if (buttons.get(1).isChecked()) {
                PropertiesUtils.setInputView2321s1("4");
                PropertiesUtils.getQuestion2321a1().setVisibility(VISIBLE);
                PropertiesUtils.getInputView2321a1().setVisibility(VISIBLE);
                if(PropertiesUtils.getQuestion2321a1()!=null){
                    clearForm(PropertiesUtils.getInputView2321a1());
                }
            }
            else if (buttons.get(2).isChecked()){
                PropertiesUtils.setInputView2321s1("0.00001");
                PropertiesUtils.getQuestion2321a1().setVisibility(VISIBLE);
                PropertiesUtils.getInputView2321a1().setVisibility(VISIBLE);
                if(PropertiesUtils.getQuestion2321a1()!=null){
                    clearForm(PropertiesUtils.getInputView2321a1());
                }
            }
            else {
                PropertiesUtils.getQuestion2321a1().setVisibility(GONE);
                PropertiesUtils.getInputView2321a1().setVisibility(GONE);
                if(PropertiesUtils.getQuestion2321a1()!=null){
                    clearForm(PropertiesUtils.getInputView2321a1());
                }
            }
        }

        // CFC ask student2 Question 2.31.1

        if (fieldName.equalsIgnoreCase("ii_c2_2_31_1")) {
            if (buttons.get(1).isChecked()) {
                PropertiesUtils.setInputView2321s2("4");
                PropertiesUtils.getQuestion2321a2().setVisibility(VISIBLE);
                PropertiesUtils.getInputView2321a2().setVisibility(VISIBLE);
                if(PropertiesUtils.getQuestion2321a2()!=null){
                    clearForm(PropertiesUtils.getInputView2321a2());
                }
            }
            else if (buttons.get(2).isChecked()){
                PropertiesUtils.setInputView2321s2("0.00001");
                PropertiesUtils.getQuestion2321a2().setVisibility(VISIBLE);
                PropertiesUtils.getInputView2321a2().setVisibility(VISIBLE);
                if(PropertiesUtils.getQuestion2321a2()!=null){
                    clearForm(PropertiesUtils.getInputView2321a2());
                }
            }
            else {
                PropertiesUtils.getQuestion2321a2().setVisibility(GONE);
                PropertiesUtils.getInputView2321a2().setVisibility(GONE);
                if(PropertiesUtils.getQuestion2321a2()!=null){
                    clearForm(PropertiesUtils.getInputView2321a2());
                }
            }
        }
        // CFC ask student3 Question 2.31.1
        if (fieldName.equalsIgnoreCase("ii_c3_2_31_1")) {
            if (buttons.get(1).isChecked()) {
                PropertiesUtils.setInputView2321s3("4");
                PropertiesUtils.getQuestion2321a3().setVisibility(VISIBLE);
                PropertiesUtils.getInputView2321a3().setVisibility(VISIBLE);
                if(PropertiesUtils.getQuestion2321a3()!=null){
                    clearForm(PropertiesUtils.getInputView2321a3());
                }
            }
            else if (buttons.get(2).isChecked()){
                PropertiesUtils.setInputView2321s3("0.00001");
                PropertiesUtils.getQuestion2321a3().setVisibility(VISIBLE);
                PropertiesUtils.getInputView2321a3().setVisibility(VISIBLE);
                if(PropertiesUtils.getQuestion2321a3()!=null){
                    clearForm(PropertiesUtils.getInputView2321a3());
                }
            }
            else {
                PropertiesUtils.getQuestion2321a3().setVisibility(GONE);
                PropertiesUtils.getInputView2321a3().setVisibility(GONE);
                if(PropertiesUtils.getQuestion2321a3()!=null){
                    clearForm(PropertiesUtils.getInputView2321a3());
                }
            }
        }
        // CFC ask student4 Question 2.31.1
        if (fieldName.equalsIgnoreCase("ii_c4_2_31_1")) {
            if (buttons.get(1).isChecked()) {
                PropertiesUtils.setInputView2321s4("4");
                PropertiesUtils.getQuestion2321a4().setVisibility(VISIBLE);
                PropertiesUtils.getInputView2321a4().setVisibility(VISIBLE);
                if(PropertiesUtils.getQuestion2321a4()!=null){
                    clearForm(PropertiesUtils.getInputView2321a4());
                }
            }
            else if (buttons.get(2).isChecked()){
                PropertiesUtils.setInputView2321s4("0.00001");
                PropertiesUtils.getQuestion2321a4().setVisibility(VISIBLE);
                PropertiesUtils.getInputView2321a4().setVisibility(VISIBLE);
                if(PropertiesUtils.getQuestion2321a4()!=null){
                    clearForm(PropertiesUtils.getInputView2321a4());
                }
            }
            else {
                PropertiesUtils.getQuestion2321a4().setVisibility(GONE);
                PropertiesUtils.getInputView2321a4().setVisibility(GONE);
                if(PropertiesUtils.getQuestion2321a4()!=null){
                    clearForm(PropertiesUtils.getInputView2321a4());
                }
            }
        }
        // CFC ask student5 Question 2.31.1
        if (fieldName.equalsIgnoreCase("ii_c5_2_31_1")) {
            if (buttons.get(1).isChecked()) {
                PropertiesUtils.setInputView2321s5("4");
                PropertiesUtils.getQuestion2321a5().setVisibility(VISIBLE);
                PropertiesUtils.getInputView2321a5().setVisibility(VISIBLE);
                if(PropertiesUtils.getQuestion2321a5()!=null){
                    clearForm(PropertiesUtils.getInputView2321a5());
                }
            }
            else if (buttons.get(2).isChecked()){
                PropertiesUtils.setInputView2321s5("0.00001");
                PropertiesUtils.getQuestion2321a5().setVisibility(VISIBLE);
                PropertiesUtils.getInputView2321a5().setVisibility(VISIBLE);
                if(PropertiesUtils.getQuestion2321a5()!=null){
                    clearForm(PropertiesUtils.getInputView2321a5());
                }
            }
            else {
                PropertiesUtils.getQuestion2321a5().setVisibility(GONE);
                PropertiesUtils.getInputView2321a5().setVisibility(GONE);
                if(PropertiesUtils.getQuestion2321a5()!=null){
                    clearForm(PropertiesUtils.getInputView2321a5());
                }
            }
        }

        // CFC ask student1 Question 2.31.2

        if (fieldName.equalsIgnoreCase("ii_c1_2_31_2")) {
            if (buttons.get(0).isChecked()) {
                PropertiesUtils.setInputView2322i1("yes");
                PropertiesUtils.getQuestion2322a1().setVisibility(VISIBLE);
                PropertiesUtils.getInputView2322a1().setVisibility(VISIBLE);
            } else {
                PropertiesUtils.setInputView2322i1("no");
                PropertiesUtils.getQuestion2322a1().setVisibility(GONE);
                PropertiesUtils.getInputView2322a1().setVisibility(GONE);
                if (PropertiesUtils.getInputView2322a1() != null) {
                    clearForm(PropertiesUtils.getInputView2322a1());
                }
            }
        }

        // CFC ask student2 Question 2.31.2

        if (fieldName.equalsIgnoreCase("ii_c2_2_31_2")) {
            if (buttons.get(0).isChecked()) {
                PropertiesUtils.setInputView2322i2("yes");
                PropertiesUtils.getQuestion2322a2().setVisibility(VISIBLE);
                PropertiesUtils.getInputView2322a2().setVisibility(VISIBLE);
            }else {
                PropertiesUtils.setInputView2322i2("no");
                PropertiesUtils.getQuestion2322a2().setVisibility(GONE);
                PropertiesUtils.getInputView2322a2().setVisibility(GONE);
                if (PropertiesUtils.getInputView2322a2()!=null){
                    clearForm(PropertiesUtils.getInputView2322a2());
                }
            }
        }

        // CFC ask student3 Question 2.31.2

        if (fieldName.equalsIgnoreCase("ii_c3_2_31_2")) {
            if (buttons.get(0).isChecked()) {
                PropertiesUtils.setInputView2322i3("yes");
                PropertiesUtils.getQuestion2322a3().setVisibility(VISIBLE);
                PropertiesUtils.getInputView2322a3().setVisibility(VISIBLE);
            }else {
                PropertiesUtils.setInputView2322i3("no");
                PropertiesUtils.getQuestion2322a3().setVisibility(GONE);
                PropertiesUtils.getInputView2322a3().setVisibility(GONE);
                if (PropertiesUtils.getInputView2322a3()!=null){
                    clearForm(PropertiesUtils.getInputView2322a3());
                }
            }
        }

        // CFC ask student4 Question 2.31.2

        if (fieldName.equalsIgnoreCase("ii_c4_2_31_2")) {
            if (buttons.get(0).isChecked()) {
                PropertiesUtils.setInputView2322i4("yes");
                PropertiesUtils.getQuestion2322a4().setVisibility(VISIBLE);
                PropertiesUtils.getInputView2322a4().setVisibility(VISIBLE);
            }else {
                PropertiesUtils.setInputView2322i4("no");
                PropertiesUtils.getQuestion2322a4().setVisibility(GONE);
                PropertiesUtils.getInputView2322a4().setVisibility(GONE);
                if (PropertiesUtils.getInputView2322a4()!=null){
                    clearForm(PropertiesUtils.getInputView2322a4());
                }
            }
        }

        // CFC ask student5 Question 2.31.2

        if (fieldName.equalsIgnoreCase("ii_c5_2_31_2")) {
            if (buttons.get(0).isChecked()) {
                PropertiesUtils.setInputView2322i5("yes");
                PropertiesUtils.getQuestion2322a5().setVisibility(VISIBLE);
                PropertiesUtils.getInputView2322a5().setVisibility(VISIBLE);
            }else {
                PropertiesUtils.setInputView2322i5("no");
                PropertiesUtils.getQuestion2322a5().setVisibility(GONE);
                PropertiesUtils.getInputView2322a5().setVisibility(GONE);
                if (PropertiesUtils.getInputView2322a5()!=null){
                    clearForm(PropertiesUtils.getInputView2322a5());
                }
            }
        }
//       /* if (fieldName.equalsIgnoreCase("purpose_visit")) {
//            if (buttons.get(3).isChecked()) {
//                PropertiesUtils.getTextViewOtherPurpose().setVisibility(VISIBLE);
//                PropertiesUtils.getEditTextOtherPurpose().setVisibility(VISIBLE);
//            } else {
//                PropertiesUtils.getTextViewOtherPurpose().setVisibility(GONE);
//                PropertiesUtils.getEditTextOtherPurpose().setVisibility(GONE);
//            }
//
//        } */
//       /* if(fieldName.equalsIgnoreCase("activities_list")){
//            if(buttons.get(14).isChecked()){
//                PropertiesUtils.getTextViewOtherActivity().setVisibility(VISIBLE);
//                PropertiesUtils.getEditTextOtherActivity().setVisibility(VISIBLE);
//            } else{
//                PropertiesUtils.getTextViewOtherActivity().setVisibility(GONE);
//                PropertiesUtils.getEditTextOtherActivity().setVisibility(GONE);
//            }
//        }*/
//        /*if(fieldName.equalsIgnoreCase("ngo_name")){
//            if(buttons.get(8).isChecked()){
//                PropertiesUtils.getTextViewOtherOrg().setVisibility(VISIBLE);
//                PropertiesUtils.getEditTextOtherOrg().setVisibility(VISIBLE);
//            } else{
//                PropertiesUtils.getTextViewOtherOrg().setVisibility(GONE);
//                PropertiesUtils.getEditTextOtherOrg().setVisibility(GONE);
//            }
//        }*/
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
