<?php

namespace MONITORING\Http\Controllers;

use Illuminate\Http\Request;
use MONITORING\Http\Requests;
use MONITORING\Http\Controllers\Controller;
use DB;
use MONITORING\Condition;

class ConditionController extends Controller {

    public function index() { // get   
        $conditions = Condition::all();
        return response()
                        ->view('content.system.condition', ['conditions' => $conditions]);
    }

    public function create() {///photo/create put
    }

    public function store() { // post
        $operation = $_POST['oper'];
        switch ($operation) {
            case 'add':
                $condition = new Condition;
                $condition->id = $_POST['id'];
                $condition->ConditionCode = $_POST['ConditionCode'];
                $condition->ConditionName = $_POST['ConditionName'];
                $condition->ConditionSymbol = $_POST['ConditionSymbol'];
                $condition->LanguageID = $_POST['LanguageID'];
                $condition->save();
                break;
            case 'edit':
                $condition = Condition::find($_POST['id']);
                $condition->ConditionCode = $_POST['ConditionCode'];
                $condition->ConditionName = $_POST['ConditionName'];
                $condition->ConditionSymbol = $_POST['ConditionSymbol'];
                $condition->LanguageID = $_POST['LanguageID'];
                $condition->save();
                break;
            case 'del':
                $ids = explode(',', $_POST['id']);
                Condition::destroy($ids);
                break;
            default :break;
        }
    }

    public function show($id) { // /photo/{photo} get
        
    }

    public function edit($id) { ///photo/{photo}/edit
    }

    public function destroy($id) { ///photo/{photo} delete
    }

}
