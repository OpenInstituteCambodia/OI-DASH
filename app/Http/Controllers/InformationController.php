<?php

namespace MONITORING\Http\Controllers;

use Illuminate\Http\Request;
use MONITORING\District;
use MONITORING\Http\Requests;
use MONITORING\Http\Controllers\Controller;
use MONITORING\Province;
use MONITORING\Condition;
use MONITORING\EntityDefinedFieldSearchValue;
use MONITORING\EntityDefinedFieldCondition;
use Auth;
use DB;

class InformationController extends Controller {
    private $language_id;
    private $user_role_level;

    public function __construct() {
        if (session()->has('locale')) {
            if (session()->get('locale') == 'km') {
                $this->language_id = 2;
            } elseif (session()->get('locale') == 'en') {
                $this->language_id = 1;
            }
        } else {
            $this->language_id = 2;
        }

        if (Auth::check()) {
            $this->user_role_level = Auth::user()->role->level;
        }

//        dd($this->user_role_level);
    }

    // Triggerred when Searching fired
    public function index(Request $request) {
        // show the entity information related to condition
        //$data = $request->input('key');
        $table = DB::table('table')->where('TableName', $request->input('table_name'))->first();
        $gp_type = $request->input('gp_type');
        // Data here is from dropdown1 ,2 and 3
        $conditions = json_decode($request->input('data'));
        // selections here are checkboxes chosen
        $selections = json_decode($request->input('selections'));
        $db = DB::table($request->input('table_name'));
        $db->select(DB::raw("EDF_CODE,".implode(",", $selections)));

//        dd($gp_type);
//        dd($request->input('gp_province'));

        switch ($gp_type) {
            case 'province':
                $db->where("PROVINCE_CODE", $request->input('gp_province'));
                break;
            case 'district':
                $db->where("DISTRICT_CODE", $request->input('gp_district'));
                break;
            case 'commune':
                $db->where("COMMUNE_CODE", $request->input('gp_commune'));
                break;
//            case 'village':
//                $db->where("VILLAGE_CODE", $request->input('gp_village'));
//                break;
            default: break;
        }

//        dd($conditions);

        if (count($conditions) > 0) {
            $conditions[0]->conjunction = "AND";
        }

//        $conditions[i]->keyValue = "RESULT_SECTION_CAL_COM1_SHOW_RESULT";
//        $conditions[i]->condition = ">=";
//        $conditions[i]->value = "yes" or "2";

        for ($i = 0; $i < count($conditions); $i++) {
            // keyValue is from dropdown1
            if (strlen($conditions[$i]->keyValue) !== 0 && strlen($conditions[$i]->condition) !== 0 && strlen($conditions[$i]->value) !== 0) {
                if (strcmp($conditions[$i]->conjunction, "AND") === 0) {
                    $db->where($conditions[$i]->keyValue, $conditions[$i]->condition, $conditions[$i]->value);
                } else {
                    // Ex Full Option equal Yes
                    $db->orWhere($conditions[$i]->keyValue, $conditions[$i]->condition, $conditions[$i]->value);
                }
            }
        }

//        dd($selections);

        // Selecting column header according to selections from checkboxes and displaying them in result form
        $col_header = DB::table('entitydefinedfieldwithlistfull')
                ->select('EntityDefinedFieldListName')
                ->whereIn('EntityDefinedFieldNameInTable', $selections)
                ->where('LanguageID', $this->language_id)
                ->orderBy('EntityDefinedCategoryCode', 'asc')
                ->orderBy('id', 'asc')
                ->get();

        return response()->view('content.monitor.information-result', ['col_headers' => $col_header, 'rows' => $db->get(), 'table' => $table]);
    }


    // show category
    public function show($tableID) {
        // Load geographical areas

        $country = trans('information_content.geography.country');
        $province = trans('information_content.geography.province');
        $district = trans('information_content.geography.district');
        $commune = trans('information_content.geography.commune');


        switch ($this->user_role_level){
            case 1:
            case 2:
                $geographical_areas = [
                    ['name' => 'country', 'label' =>  $country, 'selected' => true],
                    ['name' => 'province', 'label' => $province, 'selected' => false],
                    ['name' => 'district', 'label' => $district, 'selected' => false],
                    ['name' => 'commune', 'label' => $commune, 'selected' => false],
                ];

                // get province code
                if ($this->language_id == 1) {
                    $provinces = Province::select(DB::raw("PROCODE AS ProvinceCode, PROVINCE AS ProvinceName"))->get();
                    $districts = District::select(DB::raw("DCode AS DistrictCode, DName_kh AS DistrictName"))->get();
                } elseif ($this->language_id == 2) {
                    $provinces = Province::select(DB::raw("PROCODE AS ProvinceCode, PROVINCE_KH AS ProvinceName"))->get();
                    $districts = District::select(DB::raw("DCode AS DistrictCode, DName_kh AS DistrictName"))->get();
                }
                break;
            case 3:
                $geographical_areas = [
                    ['name' => 'province', 'label' => $province, 'selected' => true],
                    ['name' => 'district', 'label' => $district, 'selected' => false],
                    ['name' => 'commune', 'label' => $commune, 'selected' => false]
                ];

                // get province code
                if (Auth::check()) {
                    if ($this->language_id == 1) {
                        $provinces = Province::select(DB::raw("PROCODE AS ProvinceCode, PROVINCE AS ProvinceName"))->where('PROCODE','=', Auth::user()->province_code)->get();
                        $districts = District::select(DB::raw("DCode AS DistrictCode, DName_kh AS DistrictName"))->get();
                    } elseif ($this->language_id == 2) {
                        $provinces = Province::select(DB::raw("PROCODE AS ProvinceCode, PROVINCE_KH AS ProvinceName"))->where('PROCODE','=', Auth::user()->province_code)->get();
                        $districts = District::select(DB::raw("DCode AS DistrictCode, DName_kh AS DistrictName"))->get();
                    }
                }
                break;
            case 4:
                $geographical_areas = [
                    ['name' => 'district', 'label' => $district, 'selected' => true],
                    ['name' => 'commune', 'label' => $commune, 'selected' => false]
                ];

                // get province code
                if (Auth::check()) {
                    if ($this->language_id == 1) {
                        $provinces = Province::select(DB::raw("PROCODE AS ProvinceCode, PROVINCE AS ProvinceName"))->where('PROCODE', '=', Auth::user()->province_code)->get();
                        $districts = District::select(DB::raw("DCode AS DistrictCode, DName_en AS DistrictName"))->where('DCode', '=', Auth::user()->district_code)->get();
                    } elseif ($this->language_id == 2) {
                        $provinces = Province::select(DB::raw("PROCODE AS ProvinceCode, PROVINCE_KH AS ProvinceName"))->where('PROCODE', '=', Auth::user()->province_code)->get();
                        $districts = District::select(DB::raw("DCode AS DistrictCode, DName_kh AS DistrictName"))->where('DCode', '=', Auth::user()->district_code)->get();
                    }
                }
                break;
            default: break;
        }

        $conditions = Condition::where('LanguageID', $this->language_id)->get();
        
        // get category
        $table = DB::table('table')->where('id', $tableID)->first();

        // Selecting data from Database View called EntityDefinedFieldWithListFull
        // Checkbox
        $categories = DB::table('entitydefinedfieldwithlistfull')
                ->where([['LanguageID', $this->language_id], ['TableID', $tableID]])
                ->groupBy('EntityDefinedCategoryName')
                ->orderBy('EntityDefinedCategoryCode', 'asc')
                ->get();

        for ($i = 0; $i < count($categories); $i++) {
            $categories[$i]->fields = DB::table('entitydefinedfieldwithlistfull')
                    ->where([['LanguageID', $this->language_id], ['TableID', $tableID], ['EntityDefinedCategoryName', $categories[$i]->EntityDefinedCategoryName]])
                    ->get();
        }

        // get categories to first select option
        $fields = DB::table('entitydefinedfieldwithlistfull')
                ->where([['LanguageID', $this->language_id], ['TableID', $tableID]])
                ->orderBy('EntityDefinedFieldListCode', 'asc')
                ->get();

        return response()->view('content.monitor.information', ['table' => $table->TableName, 'provinces' => $provinces, 'districts' => $districts, 'geographical_areas' => $geographical_areas, 'conditions' => $conditions, 'categories' => $categories, 'fields' => $fields, 'user_role_level' => $this->user_role_level]);
    }

    // Not used
    // public function showFieldRelatedWithCategory($tableID, $category) {
    //     $fields = DB::table('entitydefinedfieldwithlistfull')
    //             ->where([['LanguageID', 1], ['TableID', $tableID], ['EntityDefinedFieldListCategory', $category]])
    //             ->get();

    //     return response($fields, 200);
    // }

    // Selecting values and conditions of related fields
    public function showFieldListValue($fieldName) {
        $obj = array('values' => DB::table('edf_entitydefinedfieldsearch')
                    ->select('id', 'Value', 'Description', 'Description_KH', 'EDFSearchType')
                    ->where('EntityDefinedFieldNameInTable', $fieldName)
                    ->get(),
                    'conditions' => EntityDefinedFieldCondition::getConditionByFieldID($fieldName, $this->language_id));

        return response($obj, 200);
    }

    // Comparing All fields when clicking on a row in result form
    // It is in result form
    public function compareEDF($tableID, $edfCode) {
        $colHeaders = DB::table('entitydefinedfieldwithlistfull')
                ->select('EntityDefinedFieldNameInTable', 'EntityDefinedFieldListName')
                ->whereIn('DisplayField', [1, 2])
                ->where('TableID', $tableID)
                ->where('LanguageID', $this->language_id)
                ->orderBy('id', 'asc')
                ->get();
        $fields =[];
        foreach($colHeaders as $col){
            $fields[]=$col->EntityDefinedFieldNameInTable;
        }
        $table = DB::table('table')->where('id', $tableID)->first();
        $rows = DB::table($table->TableName)
                ->select(DB::raw(implode(",", $fields)))
                ->where('EDF_CODE', $edfCode)
                ->orderBy('_URI','desc')
                ->take(2)->get();

        return response()->view('content.monitor.edf-comparison', ['colHeaders' => $colHeaders,'rows'=>  array_reverse($rows), 'table' => $table]);
    }

    /**
     * @return int
     */
    public function test()
    {
        return response()->view('content.monitor.test');
    }

}
