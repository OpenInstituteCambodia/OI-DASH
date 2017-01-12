package org.odk.collect.android.cfs.utilities;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.odk.collect.android.cfs.model.Commune;
import org.odk.collect.android.cfs.model.District;
import org.odk.collect.android.cfs.model.Province;
import org.odk.collect.android.cfs.model.Village;

/**
 * Created by oi on 9/5/14.
 *
 * @author sovannoty_chea
 */
public class PropertiesUtils {

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

   // private static TextView textViewOtherPurpose;
    private static TextView textViewOtherActivity;

   // private static EditText editTextOtherPurpose;
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

    public static EditText getEditTextOtherActivity() {
        return editTextOtherActivity;
    }

    public static void setEditTextOtherActivity(EditText editTextOtherActivity) {
        PropertiesUtils.editTextOtherActivity = editTextOtherActivity;
    }

//    public static EditText getEditTextOtherPurpose() {
//        return editTextOtherPurpose;
//    }
//
//    public static void setEditTextOtherPurpose(EditText editTextOtherPurpose) {
//        PropertiesUtils.editTextOtherPurpose = editTextOtherPurpose;
//    }



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
}