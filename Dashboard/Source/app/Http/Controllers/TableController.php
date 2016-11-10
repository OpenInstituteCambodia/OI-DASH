<?php

namespace MONITORING\Http\Controllers;

use Illuminate\Http\Request;
use MONITORING\Http\Requests;
use MONITORING\Http\Controllers\Controller;
use DB;
use MONITORING\Table;

class TableController extends Controller {

    public function index() { // get   
        $tables = Table::all()->toJson();
        return response()
                        ->view('content.system.table', ['tables' => $tables]);
    }

    public function store() { // post
        $operation = $_POST['oper'];
        switch ($operation) {
            case 'add':
                $table = new Table;
                $table->id = $_POST['id'];
                $table->TableName = $_POST['TableName'];
                $table->TableNameEN = $_POST['TableNameEN'];
                $table->TableNameKH = $_POST['TableNameKH'];
                $table->save();
                break;
            case 'edit':
                $table = Table::find($_POST['id']);
                $table->TableName = $_POST['TableName'];
                $table->TableNameEN = $_POST['TableNameEN'];
                $table->TableNameKH = $_POST['TableNameKH'];
                $table->save();
                break;
            case 'del':
                $ids = explode(',', $_POST['id']);
                Table::destroy($ids);
                break;
            default :break;
        }
    }

}
