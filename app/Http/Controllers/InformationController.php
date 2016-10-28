<?php

namespace MONITORING\Http\Controllers;

use Illuminate\Http\Request;
use MONITORING\Http\Requests;
use MONITORING\Http\Controllers\Controller;
use MONITORING\Province;
use MONITORING\Condition;
use MONITORING\EntityDefinedFieldSearchValue;
use MONITORING\EntityDefinedFieldCondition;
use DB;

class InformationController extends Controller {
    private $language_id;

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
            case 'village':
                $db->where("VILLAGE_CODE", $request->input('gp_village'));
                break;
            default: break;
        }

        if (count($conditions) > 0) {
            $conditions[0]->conjunction = "AND";
        }

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
        // get province code
        if ($this->language_id == 1) {
            $provinces = Province::select(DB::raw("PROCODE AS ProvinceCode, PROVINCE AS ProvinceName"))->get();
        } elseif ($this->language_id == 2) {
            $provinces = Province::select(DB::raw("PROCODE AS ProvinceCode, PROVINCE_KH AS ProvinceName"))->get();
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

        return response()->view('content.monitor.information', ['table' => $table->TableName, 'provinces' => $provinces, 'conditions' => $conditions, 'categories' => $categories, 'fields' => $fields]);
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

}
