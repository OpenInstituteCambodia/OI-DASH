<?php

namespace MONITORING\Http\Controllers;

use Illuminate\Http\Request;
use MONITORING\Http\Requests;
use MONITORING\Http\Controllers\Controller;
use MONITORING\Province;
use MONITORING\District;
use MONITORING\Commune;
use MONITORING\Village;
use MONITORING\Condition;
use MONITORING\EntityDefinedFieldSearchValue;
use MONITORING\EntityDefinedFieldCondition;
use DB;

class AggregateController extends Controller {

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

    public function index(Request $request) {
        // show the entity information related to condition
        //$data = $request->input('key');
        $table = DB::table('table')->where('TableName', $request->input('table_name'))->first();
        $gp_type = $request->input('gp_type');
        $gp_aggType = $request->input('gp_aggType');
        $conditions = json_decode($request->input('data'));
        $db = DB::table($request->input('table_name'));
        $db->select(DB::raw("COUNT(*) as NumberOfEntity"));
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
            default :break;
        }
        if (count($conditions) > 0) {
            $conditions[0]->conjunction = "AND";
        }
        for ($i = 0; $i < count($conditions); $i++) {
            if (strlen($conditions[$i]->keyValue) !== 0 && strlen($conditions[$i]->condition) !== 0 && strlen($conditions[$i]->value) !== 0) {
                if (strcmp($conditions[$i]->conjunction, "AND") === 0) {
                    $db->where($conditions[$i]->keyValue, $conditions[$i]->condition, $conditions[$i]->value);
                } else {
                    $db->orWhere($conditions[$i]->keyValue, $conditions[$i]->condition, $conditions[$i]->value);
                }
            }
        }

        // get geographical hierachy 
        //echo $tempDB->where('PROVINCE_CODE', 21)->first()->NumberOfEntity;   
        $tempDB = clone $db;
        $country = $tempDB->first();

        $country->provinces = Province::select(DB::raw("PROCODE AS ProvinceCode, PROVINCE AS ProvinceName, PROVINCE_KH AS ProvinceKhmerName"))->get();

        if (strcmp($gp_aggType, 'country') !== 0) {
            foreach ($country->provinces as $province) {
                $tempDB = clone $db;
                $province->NumberOfEntity = $tempDB->where('PROVINCE_CODE', $province->ProvinceCode)->first()->NumberOfEntity;

                if (strcmp($gp_aggType, 'province') === 0) {
                    continue;
                }

                $province->districts = District::select(DB::raw("DCode AS DistrictCode, DName_en AS DistrictName, DName_kh AS DistrictKhmerName"))->where('PCode', $province->ProvinceCode)->get();

                foreach ($province->districts as $district) {
                    $tempDB = clone $db;
                    $district->NumberOfEntity = $tempDB->where('DISTRICT_CODE', $district->DistrictCode)->first()->NumberOfEntity;

                    if (strcmp($gp_aggType, 'district') === 0) {
                        continue;
                    }

                    $district->communes = Commune::select(DB::raw("CCode AS CommuneCode, CName_en AS CommuneName, CName_kh AS CommuneKhmerName"))->where('DCode', $district->DistrictCode)
                            ->get();
                    foreach ($district->communes as $commune) {
                        $tempDB = clone $db;
                        $commune->villages = Village::select(DB::raw("VCode AS VillageCode, VName_en AS VillageName, VName_kh AS VillageKhmerName"))->where('CCode', $commune->CommuneCode)
                                ->get();
                        $commune->NumberOfEntity = $tempDB->where('COMMUNE_CODE', $commune->CommuneCode)->first()->NumberOfEntity;
                    }
                }
            }
        }

        return response()->view('content.monitor.aggregate-result', ["country" => $country,"gp_aggType" => $gp_aggType]);
    }

    
    public function show($tableID) { // show category
        // get province code

        if ($this->language_id == 1) {
            $provinces = Province::select(DB::raw("PROCODE AS ProvinceCode, PROVINCE AS ProvinceName"))->get();
        } elseif ($this->language_id == 2) {
            $provinces = Province::select(DB::raw("PROCODE AS ProvinceCode, PROVINCE_KH AS ProvinceName"))->get();
        }

        $conditions = Condition::where('LanguageID', $this->language_id)->get();
        // get category
        $table = DB::table('table')->where('id', $tableID)->first();

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
//        get list of field related to each 
        $fields = DB::table('entitydefinedfieldwithlistfull')
                ->where([['LanguageID', $this->language_id], ['TableID', $tableID]])
                ->orderBy('EntityDefinedFieldListCode', 'asc')
                ->get();

        return response()->view('content.monitor.aggregate', ['table' => $table->TableName, 'provinces' => $provinces, 'conditions' => $conditions, 'categories' => $categories, 'fields' => $fields]);
    }
}
