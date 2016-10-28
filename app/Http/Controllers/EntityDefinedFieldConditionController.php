<?php

namespace MONITORING\Http\Controllers;

use Illuminate\Http\Request;
use MONITORING\Http\Requests;
use MONITORING\Http\Controllers\Controller;
use MONITORING\Table;

class EntityDefinedFieldConditionController extends Controller {

    public function index() {
        $tables = Table::all();
        return response()
                        ->view('content.system.entity_defined_field_condition', ['tables' => $tables]);
    }

}
