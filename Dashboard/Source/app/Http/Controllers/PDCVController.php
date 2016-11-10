<?php

namespace MONITORING\Http\Controllers;

use Illuminate\Http\Request;
use MONITORING\Http\Requests;
use MONITORING\Http\Controllers\Controller;
use DB;
use MONITORING\Province;
use MONITORING\District;
use MONITORING\Commune;
use MONITORING\Village;

class PDCVController extends Controller {
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

    public function getLocation($type, $code) {
        if ($this->language_id == 1) {
            switch ($type) {
                case 'province':
                    $result = Province::select(DB::raw("PROCODE AS ProvinceCode, PROVINCE AS ProvinceName"))->get();
                    break;
                case 'district':
                    $result = District::select(DB::raw("DCode AS DistrictCode, DName_en AS DistrictName"))->where('PCode', $code)->get();
                    break;
                case 'commune':
                    $result = Commune::select(DB::raw("CCode AS CommuneCode, CName_en AS CommuneName"))->where('DCode', $code)->get();
                    break;
                case 'village':
                    $result = Village::select(DB::raw("VCode AS VillageCode, VName_en AS VillageName"))->where('CCode', $code)->get();
                    break;
                default: break;
            }
        } elseif ($this->language_id == 2) {
            switch ($type) {
                case 'province':
                    $result = Province::select(DB::raw("PROCODE AS ProvinceCode, PROVINCE_KH AS ProvinceName"))->get();
                    break;
                case 'district':
                    $result = District::select(DB::raw("DCode AS DistrictCode, DName_kh AS DistrictName"))->where('PCode', $code)->get();
                    break;
                case 'commune':
                    $result = Commune::select(DB::raw("CCode AS CommuneCode, CName_kh AS CommuneName"))->where('DCode', $code)->get();
                    break;
                case 'village':
                    $result = Village::select(DB::raw("VCode AS VillageCode, VName_kh AS VillageName"))->where('CCode', $code)->get();
                    break;
                default: break;
            }
        }

        return response($result, 200);
    }

}