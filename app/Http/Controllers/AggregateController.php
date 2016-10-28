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
use Auth;
use DB;

class AggregateController extends Controller {
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
                $province_id = $request->input('gp_province');
                break;
            case 'district':
                $db->where("DISTRICT_CODE", $request->input('gp_district'));
                $province_id = $request->input('gp_province');
                $district_id = $request->input('gp_district');
//                dd($province_id);
                break;
            case 'commune':
                $db->where("COMMUNE_CODE", $request->input('gp_commune'));
                $province_id = $request->input('gp_province');
                $district_id = $request->input('gp_district');
                $commune_id = $request->input('gp_commune');
//                dd($commune_id);
                break;
            case 'village':
                $db->where("VILLAGE_CODE", $request->input('gp_village'));
                $province_id = $request->input('gp_province');
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
//        dd($request->all());

        if ($gp_type === 'country') {
//            dd('trigger');
            $country->provinces = Province::select(DB::raw("PROCODE AS ProvinceCode, PROVINCE AS ProvinceName, PROVINCE_KH AS ProvinceKhmerName"))->get();
        } else {
            $country->provinces = Province::select(DB::raw("PROCODE AS ProvinceCode, PROVINCE AS ProvinceName, PROVINCE_KH AS ProvinceKhmerName"))->where('PROCODE', '=', $province_id)->get();
        }

//        dd($province_id);

        if (strcmp($gp_aggType, 'country') !== 0) {
            foreach ($country->provinces as $province) {
                $tempDB = clone $db;
                $province->NumberOfEntity = $tempDB->where('PROVINCE_CODE', $province->ProvinceCode)->first()->NumberOfEntity;

                if (strcmp($gp_aggType, 'province') === 0) {
                    continue;
                }

                if(isset($commune_id)) {
                    $province->districts = District::select(DB::raw("DCode AS DistrictCode, DName_en AS DistrictName, DName_kh AS DistrictKhmerName"))->where('DCode', $district_id)->get();

                    foreach ($province->districts as $district) {
                        $tempDB = clone $db;

                        $district->NumberOfEntity = $tempDB->where('DISTRICT_CODE', $district->DistrictCode)->first()->NumberOfEntity;

                        if (strcmp($gp_aggType, 'district') === 0) {
                            continue;
                        }

                        $district->communes = Commune::select(DB::raw("CCode AS CommuneCode, CName_en AS CommuneName, CName_kh AS CommuneKhmerName"))->where([['CCode', '=', $commune_id], ['DCode', '=', $district->DistrictCode]])->get();

                        foreach ($district->communes as $commune) {
                            $tempDB = clone $db;
                            $commune->NumberOfEntity = $tempDB->where('COMMUNE_CODE', $commune->CommuneCode)->first()->NumberOfEntity;
                        }
                    }
                }
                else {
                    if(isset($district_id)) {
                        $province->districts = District::select(DB::raw("DCode AS DistrictCode, DName_en AS DistrictName, DName_kh AS DistrictKhmerName"))->where('DCode', $district_id)->get();
//                    continue;
                    }
                    else {
                        $province->districts = District::select(DB::raw("DCode AS DistrictCode, DName_en AS DistrictName, DName_kh AS DistrictKhmerName"))->where('PCode', $province->ProvinceCode)->get();
                    }

                    foreach ($province->districts as $district) {
                        $tempDB = clone $db;

                        $district->NumberOfEntity = $tempDB->where('DISTRICT_CODE', $district->DistrictCode)->first()->NumberOfEntity;

                        if (strcmp($gp_aggType, 'district') === 0) {
                            continue;
                        }

                        if(isset($commune_id)) {
                            $district->communes = Commune::select(DB::raw("CCode AS CommuneCode, CName_en AS CommuneName, CName_kh AS CommuneKhmerName"))->where('CCode', $commune_id)->get();
//                        dd($commune_id);
                        } else {
                            $district->communes = Commune::select(DB::raw("CCode AS CommuneCode, CName_en AS CommuneName, CName_kh AS CommuneKhmerName"))->where('DCode', $district->DistrictCode)->get();
//                        dd('triggered');
                        }

//                    dd($district->communes);

                        foreach ($district->communes as $commune) {
                            $tempDB = clone $db;
                            $commune->NumberOfEntity = $tempDB->where('COMMUNE_CODE', $commune->CommuneCode)->first()->NumberOfEntity;
//                        $commune->villages = Village::select(DB::raw("VCode AS VillageCode, VName_en AS VillageName, VName_kh AS VillageKhmerName"))->where('CCode', $commune->CommuneCode)->get();
                        }
                    }
                }
            }
        }

        return response()->view('content.monitor.aggregate-result', ["country" => $country, "gp_aggType" => $gp_aggType, 'gp_type' => $gp_type, '']);
    }


    public function show($tableID) { // show category
        // get province code

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

        return response()->view('content.monitor.aggregate', ['table' => $table->TableName, 'geographical_areas' => $geographical_areas, 'provinces' => $provinces, 'districts' => $districts, 'conditions' => $conditions, 'categories' => $categories, 'fields' => $fields, 'user_role_level' => $this->user_role_level]);
    }
}
