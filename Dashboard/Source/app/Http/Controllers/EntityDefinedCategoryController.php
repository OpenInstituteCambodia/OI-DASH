<?php

namespace MONITORING\Http\Controllers;

use Illuminate\Http\Request;
use MONITORING\Http\Requests;
use MONITORING\Http\Controllers\Controller;
use DB;
use MONITORING\EntityDefinedCategory;

class EntityDefinedCategoryController extends Controller {

    public function index() { // get   
        $categories = EntityDefinedCategory::all();
        return response()
                        ->view('content.system.entity_defined_category', ['categories' => $categories]);
    }

    public function create() {///photo/create put
    }

    public function store() { // post
        $operation = $_POST['oper'];
        switch ($operation) {
            case 'add':
                $category = new EntityDefinedCategory;
                $category->id = $_POST['id'];
                $category->TableID = $_POST['TableID'];
                $category->EntityDefinedCategoryCode = $_POST['EntityDefinedCategoryCode'];
                $category->EntityDefinedCategoryName = $_POST['EntityDefinedCategoryName'];
                $category->LanguageID = $_POST['LanguageID'];
                $category->save();
                break;
            case 'edit':
                $category = EntityDefinedCategory::find($_POST['id']);
                $category->TableID = $_POST['TableID'];
                $category->EntityDefinedCategoryCode = $_POST['EntityDefinedCategoryCode'];
                $category->EntityDefinedCategoryName = $_POST['EntityDefinedCategoryName'];
                $category->LanguageID = $_POST['LanguageID'];
                $category->save();
                break;
            case 'del':
                $ids = explode(',', $_POST['id']);
                EntityDefinedCategory::destroy($ids);
                break;
            default :break;
        }
    }

}
