<?php

namespace MONITORING\Http\Controllers;

use Illuminate\Http\Request;
use MONITORING\Http\Requests;
use MONITORING\Http\Controllers\Controller;
use DB;
use MONITORING\Language;

class LanguageController extends Controller {

    public function index() { // get   
        $languages = Language::all();
        return response()
                        ->view('content.system.language', ['languages' => $languages]);
    }

    public function store() { // post
        $operation = $_POST['oper'];
        switch ($operation) {
            case 'add':
                $language = new Language;
                $language->id = $_POST['id'];
                $language->LanguageName = $_POST['LanguageName'];
                $language->save();
                break;
            case 'edit':
                $language = Language::find($_POST['id']);
                $language->LanguageName = $_POST['LanguageName'];
                $language->save();
                break;
            case 'del':
                $ids = explode(',', $_POST['id']);
                Language::destroy($ids);
                break;
            default :break;
        }
    }
    
    public function changeLanguage($lang){
//        echo "$lang";
//        App::setLocale($lang);
//        echo "dfds";
//        return Redirect::route('home');
    }

}
