package org.odk.collect.android.cfc.utilities;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.odk.collect.android.cfc.model.Commune;
import org.odk.collect.android.cfc.model.District;
import org.odk.collect.android.cfc.model.Province;
import org.odk.collect.android.cfc.model.Village;

import java.util.HashMap;

/**
 * Created by oi on 9/5/14.
 *
 * @author sovannoty_chea
 */
public class PropertiesUtils {

    private static HashMap addresses;

    private static int iAnswer2_1 = 1; // 1 Visible, 0 Invisible
    private static int iAnswer312 = 1;
    private static int iAnswser213 = 1;

    public static int getiAnswser213() {
        return iAnswser213;
    }

    public static void setiAnswser213(int iAnswser213) {
        PropertiesUtils.iAnswser213 = iAnswser213;
    }

    public static int getiAnswer312() {
        return iAnswer312;
    }

    public static void setiAnswer312(int iAnswer312) {
        PropertiesUtils.iAnswer312 = iAnswer312;
    }

    private static Province _selectedProvince;
    private static District _selectedDistrict;
    private static Commune _selectedCommune;

    //Set Other for 7 EditText
    private static Village _selectedVillage;
    private static Spinner sp_province;
    private static Spinner sp_district;
    private static Spinner sp_commune;
    private static Spinner sp_village;
    private static EditText _q13Ref;
    private static String locationGPS;

    public static String getLocationGPS() {
        return locationGPS;
    }

    public static void setLocationGPS(String locationGPS) {
        PropertiesUtils.locationGPS = locationGPS;
    }

    public static int getiAnswer2_1() {
        return iAnswer2_1;
    }

    public static void setiAnswer2_1(int iAnswer2_1) {
        PropertiesUtils.iAnswer2_1 = iAnswer2_1;
    }

    public static void setSp_province(Spinner spProvince) {
        sp_province = spProvince;
    }

    public static void setSp_district(Spinner spDistrict) {
        sp_district = spDistrict;
    }

    public static Spinner getSp_commune() {
        return sp_commune;
    }

    public static void setSp_commune(Spinner spCommune) {
        sp_commune = spCommune;
    }

    public static void setSp_village(Spinner spVillage) {
        sp_village = spVillage;
    }

    public static Province getSelectedProvince() {
        return _selectedProvince;
    }

    public static District getSelectedDistrict() {
        return _selectedDistrict;
    }

    public static Commune getSelectedCommune() {
        return _selectedCommune;
    }

    public static Village getSelectedVillage() {
        return _selectedVillage;
    }

    private static TextView textViewOtherPurpose;
    private static TextView textViewOtherActivity;

    private static EditText editTextOtherPurpose;
    private static EditText editTextOtherActivity;
    private static int iAnswerOtherActivity = 0;

    public static int getiAnswerOtherActivity() {
        return iAnswerOtherActivity;
    }

    public static void setiAnswerOtherActivity(int iAnswerOtherActivity) {
        PropertiesUtils.iAnswerOtherActivity = iAnswerOtherActivity;
    }
//    public static TextView getTextViewOtherPurpose() {
//        return textViewOtherPurpose;
//    }
//
//    public static void setTextViewOtherPurpose(TextView textViewOtherPurpose) {
//        PropertiesUtils.textViewOtherPurpose = textViewOtherPurpose;
//    }

    public static TextView getTextViewOtherActivity() {
        return textViewOtherActivity;
    }

    public static void setTextViewOtherActivity(TextView textViewOtherActivity) {
        PropertiesUtils.textViewOtherActivity = textViewOtherActivity;
    }

//    public static EditText getEditTextOtherPurpose() {
//        return editTextOtherPurpose;
//    }
//
//    public static void setEditTextOtherPurpose(EditText editTextOtherPurpose) {
//        PropertiesUtils.editTextOtherPurpose = editTextOtherPurpose;
//    }

    public static EditText getEditTextOtherActivity() {
        return editTextOtherActivity;
    }

    public static void setEditTextOtherActivity(EditText editTextOtherActivity) {
        PropertiesUtils.editTextOtherActivity = editTextOtherActivity;
    }

    //address1
    private static String address1ProvinceCode;
    private static String address1DistrictCode;
    private static String address1CommuneCode;
    private static String address1VillageCode;

    public static String getAddress1ProvinceCode() {
        return address1ProvinceCode;
    }

    public static void setAddress1ProvinceCode(String address1ProvinceCode) {
        PropertiesUtils.address1ProvinceCode = address1ProvinceCode;
    }

    public static String getAddress1DistrictCode() {
        return address1DistrictCode;
    }

    public static void setAddress1DistrictCode(String address1DistrictCode) {
        PropertiesUtils.address1DistrictCode = address1DistrictCode;
    }

    public static String getAddress1CommuneCode() {
        return address1CommuneCode;
    }

    public static void setAddress1CommuneCode(String address1CommuneCode) {
        PropertiesUtils.address1CommuneCode = address1CommuneCode;
    }

    public static String getAddress1VillageCode() {
        return address1VillageCode;
    }

    public static void setAddress1VillageCode(String address1VillageCode) {
        PropertiesUtils.address1VillageCode = address1VillageCode;
    }

    //address2
    private static String address2ProvinceCode;
    private static String address2DistrictCode;
    private static String address2CommuneCode;
    private static String address2VillageCode;

    public static String getAddress2ProvinceCode() {
        return address2ProvinceCode;
    }

    public static void setAddress2ProvinceCode(String address2ProvinceCode) {
        PropertiesUtils.address2ProvinceCode = address2ProvinceCode;
    }

    public static String getAddress2DistrictCode() {
        return address2DistrictCode;
    }

    public static void setAddress2DistrictCode(String address2DistrictCode) {
        PropertiesUtils.address2DistrictCode = address2DistrictCode;
    }

    public static String getAddress2CommuneCode() {
        return address2CommuneCode;
    }

    public static void setAddress2CommuneCode(String address2CommuneCode) {
        PropertiesUtils.address2CommuneCode = address2CommuneCode;
    }

    public static String getAddress2VillageCode() {
        return address2VillageCode;
    }

    public static void setAddress2VillageCode(String address2VillageCode) {
        PropertiesUtils.address2VillageCode = address2VillageCode;
    }



//    private static TextView textViewOtherOrg;
//    private static TextView editTextOtherOrg;
//
//    public static TextView getTextViewOtherOrg() {
//        return textViewOtherOrg;
//    }
//
//    public static void setTextViewOtherOrg(TextView textViewOtherOrg) {
//        PropertiesUtils.textViewOtherOrg = textViewOtherOrg;
//    }
//
//    public static TextView getEditTextOtherOrg() {
//        return editTextOtherOrg;
//    }
//
//    public static void setEditTextOtherOrg(TextView editTextOtherOrg) {
//        PropertiesUtils.editTextOtherOrg = editTextOtherOrg;
//    }

    //CFS Question part 3 and part 5
    private static String inputView36;
    private static String inputView51;



    private static int answer52 = 0;
    private static int answer53 = 0;
    private static int answer54 = 0;

    private static TextView question52;
    private static LinearLayout inputView52;

    private static TextView question53;
    private static LinearLayout inputView53;

    private static TextView question54;
    private static LinearLayout inputView54;

    private static TextView question511;
    private static DatePicker inputView511;

    private static TextView question521;
    private static LinearLayout inputView521;

    private static TextView question521a;
    private static EditText inputView521a;

    private static TextView question531;
    private static DatePicker inputView531;

    private static TextView question532;
    private static DatePicker inputView532;

    private static TextView question541;
    private static EditText inputView541;

    private static int answer521Other;

    public static String getInputView36() {
        return inputView36;
    }

    public static void setInputView36(String inputView36) {
        PropertiesUtils.inputView36 = inputView36;
    }

    ///////////// part 5.1 ///////////////////

    public static String getInputView51() {
        return inputView51;
    }
    public static void setInputView51(String inputView51) {
        PropertiesUtils.inputView51 = inputView51;
    }

    public static TextView getQuestion511() {
        return question511;
    }
    public static void setQuestion511(TextView question511) {
        PropertiesUtils.question511 = question511;
    }

    public static DatePicker getInputView511() {
        return inputView511;
    }
    public static void setInputView511(DatePicker inputView511) {
        PropertiesUtils.inputView511 = inputView511;
    }

    ///////////// part 5.2 ///////////////////

    public static TextView getQuestion52() {
        return question52;
    }
    public static void setQuestion52(TextView question52) {
        PropertiesUtils.question52 = question52;
    }

    public static LinearLayout getInputView52() {
        return inputView52;
    }
    public static void setInputView52(LinearLayout inputView52) {
        PropertiesUtils.inputView52 = inputView52;
    }

    public static TextView getQuestion521() {
        return question521;
    }
    public static void setQuestion521(TextView question521) {
        PropertiesUtils.question521 = question521;
    }

    public static LinearLayout getInputView521() {
        return inputView521;
    }
    public static void setInputView521(LinearLayout inputView521) {
        PropertiesUtils.inputView521 = inputView521;
    }

    public static TextView getQuestion521a() {
        return question521a;
    }
    public static void setQuestion521a(TextView question521a) {
        PropertiesUtils.question521a = question521a;
    }

    public static EditText getInputView521a() {
        return inputView521a;
    }
    public static void setInputView521a(EditText inputView521a) {
        PropertiesUtils.inputView521a = inputView521a;
    }

    ///////////// part 5.3 ///////////////////

    public static TextView getQuestion53() {
        return question53;
    }

    public static void setQuestion53(TextView question53) {
        PropertiesUtils.question53 = question53;
    }

    public static LinearLayout getInputView53() {
        return inputView53;
    }
    public static void setInputView53(LinearLayout inputView53) {
        PropertiesUtils.inputView53 = inputView53;
    }

    public static TextView getQuestion531() {
        return question531;
    }
    public static void setQuestion531(TextView question531) {
        PropertiesUtils.question531 = question531;
    }

    public static DatePicker getInputView531() {
        return inputView531;
    }
    public static void setInputView531(DatePicker inputView531) {
        PropertiesUtils.inputView531 = inputView531;
    }

    public static TextView getQuestion532() {
        return question532;
    }
    public static void setQuestion532(TextView question532) {
        PropertiesUtils.question532 = question532;
    }

    public static DatePicker getInputView532() {
        return inputView532;
    }
    public static void setInputView532(DatePicker inputView532) {
        PropertiesUtils.inputView532 = inputView532;
    }

    ///////////// part 5.4 ///////////////////

    public static TextView getQuestion54() {
        return question54;
    }
    public static void setQuestion54(TextView question54) {
        PropertiesUtils.question54 = question54;
    }

    public static LinearLayout getInputView54() {
        return inputView54;
    }
    public static void setInputView54(LinearLayout inputView54) {
        PropertiesUtils.inputView54 = inputView54;
    }

    public static TextView getQuestion541() {
        return question541;
    }
    public static void setQuestion541(TextView question541) {
        PropertiesUtils.question541 = question541;
    }

    public static EditText getInputView541() {
        return inputView541;
    }
    public static void setInputView541(EditText inputView541) {
        PropertiesUtils.inputView541 = inputView541;
    }

    public static int getAnswer52() {
        return answer52;
    }
    public static void setAnswer52(int answer52) {
        PropertiesUtils.answer52 = answer52;
    }
    public static int getAnswer53() {
        return answer53;
    }
    public static void setAnswer53(int answer53) {
        PropertiesUtils.answer53 = answer53;
    }
    public static int getAnswer54() {
        return answer54;
    }
    public static void setAnswer54(int answer54) {
        PropertiesUtils.answer54 = answer54;
    }
    public static int getAnswer521Other() {
        return answer521Other;
    }
    public static void setAnswer521Other(int answer521Other) {
        PropertiesUtils.answer521Other = answer521Other;
    }


    //CSF Question Part 6
    private static TextView question61;
    private static LinearLayout inputView61;

    private static TextView question611;
    private static EditText inputView611;

    private static TextView question621;
    private static LinearLayout inputView621;

    private static TextView question621a;
    private static EditText inputView621a;

    private static TextView question621b;
    private static EditText inputView621b;

    private static TextView question622;
    private static LinearLayout inputView622;

    private static TextView question622a;
    private static EditText inputView622a;

    private static TextView question622b;
    private static EditText inputView622b;

    private static TextView question623;
    private static LinearLayout inputView623;

    private static TextView question623a;
    private static EditText inputView623a;

    private static TextView question623b;
    private static EditText inputView623b;

    private static TextView question624;
    private static LinearLayout inputView624;

    private static TextView question624a;
    private static EditText inputView624a;

    private static TextView question624b;
    private static EditText inputView624b;

    private static TextView question625;
    private static LinearLayout inputView625;

    private static TextView question625a;
    private static EditText inputView625a;

    private static TextView question625b;
    private static EditText inputView625b;

    private static TextView question626;
    private static LinearLayout inputView626;

    private static TextView question6261;
    private static EditText inputView6261;

    private static TextView question626a;
    private static EditText inputView626a;

    private static TextView question626b;
    private static EditText inputView626b;

    private static String inputView62;
    private static int answer621=0;
    private static int answer622=0;
    private static int answer623=0;
    private static int answer624=0;
    private static int answer625=0;
    private static int answer626=0;
    private static int answer611Other;

    public static TextView getQuestion61() {
        return question61;
    }

    public static void setQuestion61(TextView question61) {
        PropertiesUtils.question61 = question61;
    }

    public static LinearLayout getInputView61() {
        return inputView61;
    }

    public static void setInputView61(LinearLayout inputView61) {
        PropertiesUtils.inputView61 = inputView61;
    }

    public static int getAnswer611Other() {
        return answer611Other;
    }
    public static void setAnswer611Other(int answer611Other) {
        PropertiesUtils.answer611Other = answer611Other;
    }

    public static TextView getQuestion611() {
        return question611;
    }
    public static void setQuestion611(TextView question611) {
        PropertiesUtils.question611 = question611;
    }

    public static EditText getInputView611() {
        return inputView611;
    }
    public static void setInputView611(EditText inputView611) {
        PropertiesUtils.inputView611 = inputView611;
    }

    public static String getInputView62() {
        return inputView62;
    }
    public static void setInputView62(String inputView62) {
        PropertiesUtils.inputView62 = inputView62;
    }

    public static TextView getQuestion621() {
        return question621;
    }
    public static void setQuestion621(TextView question621) {
        PropertiesUtils.question621 = question621;
    }

    public static LinearLayout getInputView621() {
        return inputView621;
    }
    public static void setInputView621(LinearLayout inputView621) {
        PropertiesUtils.inputView621 = inputView621;
    }

    ///////
    public static TextView getQuestion621a() {
        return question621a;
    }
    public static void setQuestion621a(TextView question621a) {
        PropertiesUtils.question621a = question621a;
    }

    public static EditText getInputView621a() {
        return inputView621a;
    }
    public static void setInputView621a(EditText inputView621a) {
        PropertiesUtils.inputView621a = inputView621a;
    }
    /////
    public static TextView getQuestion621b() {
        return question621b;
    }

    public static void setQuestion621b(TextView question621b) {
        PropertiesUtils.question621b = question621b;
    }

    public static EditText getInputView621b() {
        return inputView621b;
    }

    public static void setInputView621b(EditText inputView621b) {
        PropertiesUtils.inputView621b = inputView621b;
    }

    public static TextView getQuestion622() {
        return question622;
    }

    public static void setQuestion622(TextView question622) {
        PropertiesUtils.question622 = question622;
    }

    public static LinearLayout getInputView622() {
        return inputView622;
    }

    public static void setInputView622(LinearLayout inputView622) {
        PropertiesUtils.inputView622 = inputView622;
    }
    ///////
    public static TextView getQuestion622a() {
        return question622a;
    }

    public static void setQuestion622a(TextView question622a) {
        PropertiesUtils.question622a = question622a;
    }

    public static EditText getInputView622a() {
        return inputView622a;
    }

    public static void setInputView622a(EditText inputView622a) {
        PropertiesUtils.inputView622a = inputView622a;
    }
    ///////
    public static TextView getQuestion622b() {
        return question622b;
    }

    public static void setQuestion622b(TextView question622b) {
        PropertiesUtils.question622b = question622b;
    }

    public static EditText getInputView622b() {
        return inputView622b;
    }

    public static void setInputView622b(EditText inputView622b) {
        PropertiesUtils.inputView622b = inputView622b;
    }

    public static TextView getQuestion623() {
        return question623;
    }

    public static void setQuestion623(TextView question623) {
        PropertiesUtils.question623 = question623;
    }

    public static LinearLayout getInputView623() {
        return inputView623;
    }

    public static void setInputView623(LinearLayout inputView623) {
        PropertiesUtils.inputView623 = inputView623;
    }

    //////
    public static TextView getQuestion623a() {
        return question623a;
    }

    public static void setQuestion623a(TextView question623a) {
        PropertiesUtils.question623a = question623a;
    }

    public static EditText getInputView623a() {
        return inputView623a;
    }

    public static void setInputView623a(EditText inputView623a) {
        PropertiesUtils.inputView623a = inputView623a;
    }
    ////
    public static TextView getQuestion623b() {
        return question623b;
    }

    public static void setQuestion623b(TextView question623b) {
        PropertiesUtils.question623b = question623b;
    }

    public static EditText getInputView623b() {
        return inputView623b;
    }

    public static void setInputView623b(EditText inputView623b) {
        PropertiesUtils.inputView623b = inputView623b;
    }

    public static TextView getQuestion624() {
        return question624;
    }

    public static void setQuestion624(TextView question624) {
        PropertiesUtils.question624 = question624;
    }

    public static LinearLayout getInputView624() {
        return inputView624;
    }

    public static void setInputView624(LinearLayout inputView624) {
        PropertiesUtils.inputView624 = inputView624;
    }

    ////
    public static TextView getQuestion624a() {
        return question624a;
    }

    public static void setQuestion624a(TextView question624a) {
        PropertiesUtils.question624a = question624a;
    }

    public static EditText getInputView624a() {
        return inputView624a;
    }

    public static void setInputView624a(EditText inputView624a) {
        PropertiesUtils.inputView624a = inputView624a;
    }
    /////
    public static TextView getQuestion624b() {
        return question624b;
    }

    public static void setQuestion624b(TextView question624b) {
        PropertiesUtils.question624b = question624b;
    }

    public static EditText getInputView624b() {
        return inputView624b;
    }

    public static void setInputView624b(EditText inputView624b) {
        PropertiesUtils.inputView624b = inputView624b;
    }
    /////////
    public static TextView getQuestion625() {
        return question625;
    }

    public static void setQuestion625(TextView question625) {
        PropertiesUtils.question625 = question625;
    }

    public static LinearLayout getInputView625() {
        return inputView625;
    }

    public static void setInputView625(LinearLayout inputView625) {
        PropertiesUtils.inputView625 = inputView625;
    }

    /////////
    public static TextView getQuestion625a() {
        return question625a;
    }

    public static void setQuestion625a(TextView question625a) {
        PropertiesUtils.question625a = question625a;
    }

    public static EditText getInputView625a() {
        return inputView625a;
    }

    public static void setInputView625a(EditText inputView625a) {
        PropertiesUtils.inputView625a = inputView625a;
    }
    //////////
    public static TextView getQuestion625b() {
        return question625b;
    }

    public static void setQuestion625b(TextView question625b) {
        PropertiesUtils.question625b = question625b;
    }

    public static EditText getInputView625b() {
        return inputView625b;
    }

    public static void setInputView625b(EditText inputView625b) {
        PropertiesUtils.inputView625b = inputView625b;
    }

    public static TextView getQuestion626() {
        return question626;
    }

    public static void setQuestion626(TextView question626) {
        PropertiesUtils.question626 = question626;
    }

    public static LinearLayout getInputView626() {
        return inputView626;
    }

    public static void setInputView626(LinearLayout inputView626) {
        PropertiesUtils.inputView626 = inputView626;
    }

    public static TextView getQuestion6261() {
        return question6261;
    }

    public static void setQuestion6261(TextView question6261) {
        PropertiesUtils.question6261 = question6261;
    }

    public static EditText getInputView6261() {
        return inputView6261;
    }

    public static void setInputView6261(EditText inputView6261) {
        PropertiesUtils.inputView6261 = inputView6261;
    }

    public static TextView getQuestion626a() {
        return question626a;
    }

    public static void setQuestion626a(TextView question626a) {
        PropertiesUtils.question626a = question626a;
    }

    public static EditText getInputView626a() {
        return inputView626a;
    }

    public static void setInputView626a(EditText inputView626a) {
        PropertiesUtils.inputView626a = inputView626a;
    }

    public static TextView getQuestion626b() {
        return question626b;
    }

    public static void setQuestion626b(TextView question626b) {
        PropertiesUtils.question626b = question626b;
    }

    public static EditText getInputView626b() {
        return inputView626b;
    }

    public static void setInputView626b(EditText inputView626b) {
        PropertiesUtils.inputView626b = inputView626b;
    }

    public static int getAnswer621() {
        return answer621;
    }

    public static void setAnswer621(int answer621) {
        PropertiesUtils.answer621 = answer621;
    }

    public static int getAnswer622() {
        return answer622;
    }

    public static void setAnswer622(int answer622) {
        PropertiesUtils.answer622 = answer622;
    }

    public static int getAnswer623(){
        return answer623;
    }

    public static void setAnswer623(int answer623) {
        PropertiesUtils.answer623 = answer623;
    }

    public static int getAnswer624() {
        return answer624;
    }

    public static void setAnswer624(int answer624) {
        PropertiesUtils.answer624 = answer624;
    }

    public static int getAnswer625() {
        return answer625;
    }

    public static void setAnswer625(int answer625) {
        PropertiesUtils.answer625 = answer625;
    }

    public static int getAnswer626() {
        return answer626;
    }

    public static void setAnswer626(int answer626) {
        PropertiesUtils.answer626 = answer626;
    }



    //CFC Question part 7
    private static String inputView74;

    private static TextView question741;
    private static EditText inputView741;

    private static TextView question742;
    private static EditText inputView742;

//    private static TextView question75;
//    private static LinearLayout inputView75;
    private static String inputView75;

    private static TextView question751a;
    private static EditText inputView751a;

    private static TextView question751b;
    private static EditText inputView751b;

    private static TextView question752a;
    private static EditText inputView752a;

    private static TextView question752b;
    private static EditText inputView752b;

    private static TextView question753a;
    private static EditText inputView753a;

    private static TextView question753b;
    private static EditText inputView753b;

    private static TextView question753c;
    private static EditText inputView753c;
    //End CFS Question Part 7

    public static String getInputView74() {
        return inputView74;
    }

    public static void setInputView74(String inputView74) {
        PropertiesUtils.inputView74 = inputView74;
    }

    public static TextView getQuestion741() {
        return question741;
    }

    public static void setQuestion741(TextView question741) {
        PropertiesUtils.question741 = question741;
    }

    public static EditText getInputView741() {
        return inputView741;
    }

    public static void setInputView741(EditText inputView741) {
        PropertiesUtils.inputView741 = inputView741;
    }

    public static TextView getQuestion742() {
        return question742;
    }

    public static void setQuestion742(TextView question742) {
        PropertiesUtils.question742 = question742;
    }

    public static EditText getInputView742() {
        return inputView742;
    }

    public static void setInputView742(EditText inputView742) {
        PropertiesUtils.inputView742 = inputView742;
    }

    ////
    public static String getInputView75() {
        return inputView75;
    }

    public static void setInputView75(String inputView75) {
        PropertiesUtils.inputView75 = inputView75;
    }
    ////

//    public static TextView getQuestion75() {
//        return question75;
//    }
//
//    public static void setQuestion75(TextView question75) {
//        PropertiesUtils.question75 = question75;
//    }
//
//    public static LinearLayout getInputView75() {
//        return inputView75;
//    }
//
//    public static void setInputView75(LinearLayout inputView75) {
//        PropertiesUtils.inputView75 = inputView75;
//    }

    public static TextView getQuestion751a() {
        return question751a;
    }

    public static void setQuestion751a(TextView question751a) {
        PropertiesUtils.question751a = question751a;
    }

    public static EditText getInputView751a() {
        return inputView751a;
    }

    public static void setInputView751a(EditText inputView751a) {
        PropertiesUtils.inputView751a = inputView751a;
    }

    public static TextView getQuestion751b() {
        return question751b;
    }

    public static void setQuestion751b(TextView question751b) {
        PropertiesUtils.question751b = question751b;
    }

    public static EditText getInputView751b() {
        return inputView751b;
    }

    public static void setInputView751b(EditText inputView751b) {
        PropertiesUtils.inputView751b = inputView751b;
    }

    public static TextView getQuestion752a() {
        return question752a;
    }

    public static void setQuestion752a(TextView question752a) {
        PropertiesUtils.question752a = question752a;
    }

    public static EditText getInputView752a() {
        return inputView752a;
    }

    public static void setInputView752a(EditText inputView752a) {
        PropertiesUtils.inputView752a = inputView752a;
    }

    public static TextView getQuestion752b() {
        return question752b;
    }

    public static void setQuestion752b(TextView question752b) {
        PropertiesUtils.question752b = question752b;
    }

    public static EditText getInputView752b() {
        return inputView752b;
    }

    public static void setInputView752b(EditText inputView752b) {
        PropertiesUtils.inputView752b = inputView752b;
    }

    public static TextView getQuestion753a() {
        return question753a;
    }

    public static void setQuestion753a(TextView question753a) {
        PropertiesUtils.question753a = question753a;
    }

    public static EditText getInputView753a() {
        return inputView753a;
    }

    public static void setInputView753a(EditText inputView753a) {
        PropertiesUtils.inputView753a = inputView753a;
    }

    public static TextView getQuestion753b() {
        return question753b;
    }

    public static void setQuestion753b(TextView question753b) {
        PropertiesUtils.question753b = question753b;
    }

    public static EditText getInputView753b() {
        return inputView753b;
    }

    public static void setInputView753b(EditText inputView753b) {
        PropertiesUtils.inputView753b = inputView753b;
    }

    public static TextView getQuestion753c() {
        return question753c;
    }

    public static void setQuestion753c(TextView question753c) {
        PropertiesUtils.question753c = question753c;
    }

    public static EditText getInputView753c() {
        return inputView753c;
    }

    public static void setInputView753c(EditText inputView753c) {
        PropertiesUtils.inputView753c = inputView753c;
    }


    /// test

    private static String inputView2321s1;
    private static String inputView2321s2;
    private static String inputView2321s3;
    private static String inputView2321s4;
    private static String inputView2321s5;

    private static TextView question2321a1;
    private static LinearLayout inputView2321a1;

    private static TextView question2321a2;
    private static LinearLayout inputView2321a2;

    private static TextView question2321a3;
    private static LinearLayout inputView2321a3;

    private static TextView question2321a4;
    private static LinearLayout inputView2321a4;

    private static TextView question2321a5;
    private static LinearLayout inputView2321a5;

    public static String getInputView2321s1() {
        return inputView2321s1;
    }

    public static void setInputView2321s1(String inputView2321s1) {
        PropertiesUtils.inputView2321s1 = inputView2321s1;
    }

    public static String getInputView2321s2() {
        return inputView2321s2;
    }

    public static void setInputView2321s2(String inputView2321s2) {
        PropertiesUtils.inputView2321s2 = inputView2321s2;
    }

    public static String getInputView2321s3() {
        return inputView2321s3;
    }

    public static void setInputView2321s3(String inputView2321s3) {
        PropertiesUtils.inputView2321s3 = inputView2321s3;
    }

    public static String getInputView2321s4() {
        return inputView2321s4;
    }

    public static void setInputView2321s4(String inputView2321s4) {
        PropertiesUtils.inputView2321s4 = inputView2321s4;
    }

    public static String getInputView2321s5() {
        return inputView2321s5;
    }

    public static void setInputView2321s5(String inputView2321s5) {
        PropertiesUtils.inputView2321s5 = inputView2321s5;
    }

//student 1
    public static TextView getQuestion2321a1() {
        return question2321a1;
    }

    public static void setQuestion2321a1(TextView question2321a1) {
        PropertiesUtils.question2321a1 = question2321a1;
    }

    public static LinearLayout getInputView2321a1() {
        return inputView2321a1;
    }

    public static void setInputView2321a1(LinearLayout inputView2321a1) {
        PropertiesUtils.inputView2321a1 = inputView2321a1;
    }

    //student 2
    public static TextView getQuestion2321a2() {
        return question2321a2;
    }

    public static void setQuestion2321a2(TextView question2321a2) {
        PropertiesUtils.question2321a2 = question2321a2;
    }

    public static LinearLayout getInputView2321a2() {
        return inputView2321a2;
    }

    public static void setInputView2321a2(LinearLayout inputView2321a2) {
        PropertiesUtils.inputView2321a2 = inputView2321a2;
    }

    //student 3
    public static TextView getQuestion2321a3() {
        return question2321a3;
    }

    public static void setQuestion2321a3(TextView question2321a3) {
        PropertiesUtils.question2321a3 = question2321a3;
    }

    public static LinearLayout getInputView2321a3() {
        return inputView2321a3;
    }

    public static void setInputView2321a3(LinearLayout inputView2321a3) {
        PropertiesUtils.inputView2321a3 = inputView2321a3;
    }

    //student 4
    public static TextView getQuestion2321a4() {
        return question2321a4;
    }

    public static void setQuestion2321a4(TextView question2321a4) {
        PropertiesUtils.question2321a4 = question2321a4;
    }

    public static LinearLayout getInputView2321a4() {
        return inputView2321a4;
    }

    public static void setInputView2321a4(LinearLayout inputView2321a4) {
        PropertiesUtils.inputView2321a4 = inputView2321a4;
    }

    //student 5
    public static TextView getQuestion2321a5() {
        return question2321a5;
    }

    public static void setQuestion2321a5(TextView question2321a5) {
        PropertiesUtils.question2321a5 = question2321a5;
    }

    public static LinearLayout getInputView2321a5() {
        return inputView2321a5;
    }

    public static void setInputView2321a5(LinearLayout inputView2321a5) {
        PropertiesUtils.inputView2321a5 = inputView2321a5;
    }

//    // student1
//    private static TextView question2321a;
//    private static LinearLayout inputView2321a;
//
//    public static TextView getQuestion2321a() {
//        return question2321a;
//    }
//
//    public static void setQuestion2321a(TextView question2321a) {
//        PropertiesUtils.question2321a = question2321a;
//    }
//
//    public static LinearLayout getInputView2321a() {
//        return inputView2321a;
//    }
//
//    public static void setInputView2321a(LinearLayout inputView2321a) {
//        PropertiesUtils.inputView2321a = inputView2321a;
//    }




//    private static TextView question2322a;
//    private static LinearLayout inputView2322a;
//
//    public static TextView getQuestion2322a() {
//        return question2322a;
//    }
//
//    public static void setQuestion2322a(TextView question2322a) {
//        PropertiesUtils.question2322a = question2322a;
//    }
//
//    public static LinearLayout getInputView2322a() {
//        return inputView2322a;
//    }
//
//    public static void setInputView2322a(LinearLayout inputView2322a) {
//        PropertiesUtils.inputView2322a = inputView2322a;
//    }


    private static String inputView2322i1;
    private static String inputView2322i2;
    private static String inputView2322i3;
    private static String inputView2322i4;
    private static String inputView2322i5;

    private static TextView question2322a1;
    private static LinearLayout inputView2322a1;

    private static TextView question2322a2;
    private static LinearLayout inputView2322a2;

    private static TextView question2322a3;
    private static LinearLayout inputView2322a3;

    private static TextView question2322a4;
    private static LinearLayout inputView2322a4;

    private static TextView question2322a5;
    private static LinearLayout inputView2322a5;


    public static String getInputView2322i1() {
        return inputView2322i1;
    }

    public static void setInputView2322i1(String inputView2322i1) {
        PropertiesUtils.inputView2322i1 = inputView2322i1;
    }

    public static String getInputView2322i2() {
        return inputView2322i2;
    }

    public static void setInputView2322i2(String inputView2322i2) {
        PropertiesUtils.inputView2322i2 = inputView2322i2;
    }

    public static String getInputView2322i3() {
        return inputView2322i3;
    }

    public static void setInputView2322i3(String inputView2322i3) {
        PropertiesUtils.inputView2322i3 = inputView2322i3;
    }

    public static String getInputView2322i4() {
        return inputView2322i4;
    }

    public static void setInputView2322i4(String inputView2322i4) {
        PropertiesUtils.inputView2322i4 = inputView2322i4;
    }

    public static String getInputView2322i5() {
        return inputView2322i5;
    }

    public static void setInputView2322i5(String inputView2322i5) {
        PropertiesUtils.inputView2322i5 = inputView2322i5;
    }

////// image1
    public static TextView getQuestion2322a1() {
        return question2322a1;
    }

    public static void setQuestion2322a1(TextView question2322a1) {
        PropertiesUtils.question2322a1 = question2322a1;
    }

    public static LinearLayout getInputView2322a1() {
        return inputView2322a1;
    }

    public static void setInputView2322a1(LinearLayout inputView2322a1) {
        PropertiesUtils.inputView2322a1 = inputView2322a1;
    }

    ///////image2

    public static TextView getQuestion2322a2() {
        return question2322a2;
    }

    public static void setQuestion2322a2(TextView question2322a2) {
        PropertiesUtils.question2322a2 = question2322a2;
    }

    public static LinearLayout getInputView2322a2() {
        return inputView2322a2;
    }

    public static void setInputView2322a2(LinearLayout inputView2322a2) {
        PropertiesUtils.inputView2322a2 = inputView2322a2;
    }

    //////image3

    public static TextView getQuestion2322a3() {
        return question2322a3;
    }

    public static void setQuestion2322a3(TextView question2322a3) {
        PropertiesUtils.question2322a3 = question2322a3;
    }

    public static LinearLayout getInputView2322a3() {
        return inputView2322a3;
    }

    public static void setInputView2322a3(LinearLayout inputView2322a3) {
        PropertiesUtils.inputView2322a3 = inputView2322a3;
    }

    ////////// image4

    public static TextView getQuestion2322a4() {
        return question2322a4;
    }

    public static void setQuestion2322a4(TextView question2322a4) {
        PropertiesUtils.question2322a4 = question2322a4;
    }

    public static LinearLayout getInputView2322a4() {
        return inputView2322a4;
    }

    public static void setInputView2322a4(LinearLayout inputView2322a4) {
        PropertiesUtils.inputView2322a4 = inputView2322a4;
    }

    //////////// image5

    public static TextView getQuestion2322a5() {
        return question2322a5;
    }

    public static void setQuestion2322a5(TextView question2322a5) {
        PropertiesUtils.question2322a5 = question2322a5;
    }

    public static LinearLayout getInputView2322a5() {
        return inputView2322a5;
    }

    public static void setInputView2322a5(LinearLayout inputView2322a5) {
        PropertiesUtils.inputView2322a5 = inputView2322a5;
    }

}