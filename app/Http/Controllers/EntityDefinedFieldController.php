<?php

namespace MONITORING\Http\Controllers;

use Illuminate\Http\Request;
use MONITORING\Http\Requests;
use MONITORING\Http\Controllers\Controller;
use DB;
use MONITORING\EntityDefinedField;
use MONITORING\EntityDefinedFieldList;
use MONITORING\Table;
use MONITORING\EntityDefinedCategory;
use MONITORING\EntityDefinedFieldSearchValue;
use MONITORING\Condition;
use MONITORING\EntityDefinedFieldCondition;
use Excel;

class EntityDefinedFieldController extends Controller {

    private $table;

    public function index() {
        $tables = Table::all();
        return response()
                        ->view('content.system.entity_defined_field', ['tables' => $tables]);
    }

    public function show($id) { //table id
        $entity_defined_fields = DB::table('entitydefinedfieldwithlist')
                ->where('TableID', '=', $id)
                ->get();
        return response($entity_defined_fields, 200);
    }

    public function getColumnAndCategoryName($id) {
        $field['columns'] = DB::table('INFORMATION_SCHEMA.COLUMNS')
                ->select('COLUMN_NAME')
                ->where([['TABLE_SCHEMA', '=', 'dynamicfield'],
                    ['TABLE_NAME', '=', Table::find($id)->TableName]])
                ->whereNotIn('COLUMN_NAME', ['created_at', 'updated_at'])
                ->get();
        $field['categories'] = DB::table('entitydefinedcategory')
                ->select('EntityDefinedCategoryCode', 'EntityDefinedCategoryName')
                ->where([['TableID', $id], ['LanguageID', 1]])
                ->get();
        return response($field, 200);
    }

    public function destroy($id) {
        DB::table('entitydefinedfield')
                ->where('EntityDefinedFieldListCode', '=', $id)
                ->delete();
        DB::table('entitydefinedfieldlist')
                ->where('EntityDefinedFieldListCode', '=', $id)
                ->delete();
        return response('Your field has been deleted', 200);
    }

    public function store(Request $request) {
        // problem is list code :(
// find last code id

        $lastFieldCode = DB::table('entitydefinedfieldlist')
                ->select('EntityDefinedFieldListCode')
                ->orderBy('EntityDefinedFieldListCode', 'desc')
                ->first();
        if ($lastFieldCode === NULL) {
            $lastFieldCode = 1;
        } else {
            $lastFieldCode = $lastFieldCode->EntityDefinedFieldListCode + 1;
        }
        try {
            DB::beginTransaction();
            $entityList = New EntityDefinedFieldList;
            $entityList->EntityDefinedFieldListCode = $lastFieldCode;
            $entityList->EntityDefinedFieldListName = $request->input('fieldNameLatin');
            $entityList->LanguageID = 1;
            $entityList->save();

            $entityListKh = New EntityDefinedFieldList;
            $entityListKh->EntityDefinedFieldListCode = $lastFieldCode;
            $entityListKh->EntityDefinedFieldListName = $request->input('fieldNameKh');
            $entityListKh->LanguageID = 2;
            $entityListKh->save();

            $entity = new EntityDefinedField;
            $entity->TableID = $request->input('tableID');
            $entity->EntityDefinedCategoryCode = $request->input('catCode');
            $entity->EntityDefinedFieldListCode = $lastFieldCode;
            $entity->EntityDefinedFieldNameInTable = $request->input('fieldInTable');
            $entity->EDFSearchType = $request->input('edfSearchType');
            $entity->EDFType = $request->input('edfType');
            $entity->DisplayField = $request->input('displayField');
            $entity->save();
            DB::commit();

            $category = EntityDefinedCategory::where('EntityDefinedCategoryCode', $_POST['catCode'])
                    ->orderBy('LanguageID', 'asc')
                    ->get();
            $response['EntityDefinedFieldNameInTable'] = $request->input('fieldInTable');
            $response['EntityDefinedFieldListCode'] = $lastFieldCode;
            $response['EntityDefinedCategoryNameEN'] = $category[0]->EntityDefinedCategoryName;
            $response['EntityDefinedCategoryNameKH'] = $category[1]->EntityDefinedCategoryName;
            $response['EntityDefinedFiledListNameEN'] = $request->input('fieldNameLatin');
            $response['EntityDefinedFiledListNameKH'] = $request->input('fieldNameKh');
            $response['EDFSearchType'] = $request->input('edfSearchType');
            $response['EDFType'] = $request->input('edfType');
            $response['DisplayField'] = $request->input('displayField');
            return response($response, 200);
        } catch (Exception $ex) {
            DB::rollBack();
            return response('Error Database!', 500);
        }
    }

    public function importVariable(Request $request) {

        $file = $request->file('file');
        $inputFileName = $file->getClientOriginalName();
        $file_tmp_name = $file->getFileInfo();
        $tables = Excel::selectSheets('Tables')->load($file_tmp_name);
        $categories = Excel::selectSheets('Categories')->load($file_tmp_name);
        $variables = Excel::selectSheets('Variables')->load($file_tmp_name);

        $variable_options = Excel::selectSheets('VariableOptions')->load($file_tmp_name);
        $conditions = Excel::selectSheets('Conditions')->load($file_tmp_name);
        $variable_conditions = Excel::selectSheets('VariableConditions')->load($file_tmp_name);
        DB::beginTransaction();
        foreach ($tables->get() as $tab) {
            if ($tab->table_code === NULL) {
                break;
            }
            if (!DB::table('table')->where('id', $tab->table_code)->get()) {
                $table = new Table;
                $table->id = $tab->table_code;
                $table->TableName = $tab->table_name;
                $table->TableNameEN = $tab->alias_name_en;
                $table->TableNameKH = $tab->alias_name_kh;
                $table->save();
            }
            // delete everything related to table
            DB::table('entitydefinedcategory')->where('TableID', $tab->table_code)->delete();
            DB::table('entitydefinedfield')->where('TableID', $tab->table_code)->delete();
        }
        foreach ($categories->get() as $cat) {
            if ($cat->table_code === NULL) {
                break;
            }
            $category = new EntityDefinedCategory;
            $category->TableID = $cat->table_code;
            $category->EntityDefinedCategoryCode = $cat->category_code;
            $category->EntityDefinedCategoryName = $cat->name_en;
            $category->LanguageID = 1;
            $category->save();
            $category = new EntityDefinedCategory;
            $category->TableID = $cat->table_code;
            $category->EntityDefinedCategoryCode = $cat->category_code;
            $category->EntityDefinedCategoryName = $cat->name_kh;
            $category->LanguageID = 2;
            $category->save();
        }
        foreach ($variables->get() as $var) {
            if ($var->table_code === NULL) {
                break;
            }
            $lastFieldCode = DB::table('entitydefinedfieldlist')
                    ->select('EntityDefinedFieldListCode')
                    ->orderBy('EntityDefinedFieldListCode', 'desc')
                    ->first();
            if ($lastFieldCode === NULL) {
                $lastFieldCode = 1;
            } else {
                $lastFieldCode = $lastFieldCode->EntityDefinedFieldListCode + 1;
            }
            $entityList = New EntityDefinedFieldList;
            $entityList->EntityDefinedFieldListCode = $lastFieldCode;
            $entityList->EntityDefinedFieldListName = $var->variable_name_en;
            $entityList->LanguageID = 1;
            $entityList->save();

            $entityListKh = New EntityDefinedFieldList;
            $entityListKh->EntityDefinedFieldListCode = $lastFieldCode;
            $entityListKh->EntityDefinedFieldListName = $var->variable_name_kh;
            $entityListKh->LanguageID = 2;
            $entityListKh->save();

            $entity = new EntityDefinedField;
            $entity->TableID = $var->table_code;
            $entity->EntityDefinedCategoryCode = $var->category_code;
            $entity->EntityDefinedFieldListCode = $lastFieldCode;
            $entity->EntityDefinedFieldNameInTable = $var->variable_name_in_table;
            $entity->EDFSearchType = $var->variable_search_type;
            $entity->EDFType = $var->variable_type;
            $entity->DisplayField = $var->use_of_variable;
            $entity->DefaultSelected = $var->default_selected;
            $entity->save();
        }
        foreach ($variable_options->get() as $var_op) {
            if ($var_op->variable_name_in_table === NULL) {
                break;
            }
            // first get field id;
            $var_id = DB::table('entitydefinedfield')
                            ->select('id')
                            ->where('EntityDefinedFieldNameInTable', $var_op->variable_name_in_table)
                            ->first()->id;
            $entity_option = new EntityDefinedFieldSearchValue();
            $entity_option->EntityDefinedFieldID = $var_id;
            $entity_option->Value = $var_op->value;
            $entity_option->Description = $var_op->alias_value_en;
            $entity_option->Description_KH = $var_op->alias_value_kh;
            $entity_option->save();
        }

//        foreach ($conditions->get() as $con) {
//            if ($con->condition_code === NULL) {
//                break;
//            }
//            $condition = new Condition;
//            $condition->ConditionCode = $con->condition_code;
//            $condition->ConditionName = $con->name_en;
//            $condition->ConditionSymbol = $con->symbol;
//            $condition->LanguageID = 1;
//            $condition->save();
//            $condition = new Condition;
//            $condition->ConditionCode = $con->condition_code;
//            $condition->ConditionName = $con->name_kh;
//            $condition->ConditionSymbol = $con->symbol;
//            $condition->LanguageID = 2;
//            $condition->save();
//        }
        foreach ($variable_conditions->get() as $var_con) {
            if ($var_con->variable_name_in_table === NULL) {
                break;
            }
            $var_id = DB::table('entitydefinedfield')
                            ->select('id')
                            ->where('EntityDefinedFieldNameInTable', $var_con->variable_name_in_table)
                            ->first()->id;
            $entity_condition = new EntityDefinedFieldCondition();
            $entity_condition->EntityDefinedFieldID = $var_id;
            $entity_condition->ConditionCode = $var_con->condition_code;
            $entity_condition->save();
        }
        DB::commit();
    }

    public function exportVariable() {
        $tables = DB::table('table')
                ->get();
        return response()->view('content.system.export_field', ['tables' => $tables]);
    }

    public function exportProcess(Request $request) {
        $this->table = $request->input('table-name');
        Excel::load(resource_path() . '\assets\excel-template\variable-list.xlsx', function($reader) {
            $reader->sheet('Tables', function($sheet) {
                $table = DB::table('table')->where('id', $this->table)->first();

                $sheet->row(2, [$table->id, $table->TableName, $table->TableNameEN, $table->TableNameKH]);
            });
            $reader->sheet('Categories', function($sheet) {
                $categories = DB::table('entitydefinedfieldwithlist')
                                ->select('TableID', 'EntityDefinedCategoryCode', 'EntityDefinedCategoryNameEN', 'EntityDefinedCategoryNameKH')
                                ->where('TableID', $this->table)
                                ->distinct()->get();
                $i = 2;
                foreach ($categories as $category) {
                    $sheet->row($i++, [$category->TableID, $category->EntityDefinedCategoryCode, $category->EntityDefinedCategoryNameEN, $category->EntityDefinedCategoryNameKH]);
                }
            });
            $reader->sheet('Variables', function($sheet) {
                $variables = DB::table('entitydefinedfieldwithlist')
                                ->select('TableID', 'EntityDefinedCategoryCode', 'EntityDefinedFieldNameInTable', 'EntityDefinedFieldListNameEN', 'EntityDefinedFieldListNameKH', 'EDFType', 'EDFSearchType', 'DisplayField', 'DefaultSelected')
                                ->where('TableID', $this->table)
                                ->distinct()->get();
                $i = 2;
                foreach ($variables as $var) {
                    $sheet->row($i++, [$var->TableID, $var->EntityDefinedCategoryCode, $var->EntityDefinedFieldNameInTable, $var->EntityDefinedFieldListNameEN, $var->EntityDefinedFieldListNameKH, $var->EDFType, $var->EDFSearchType, $var->DisplayField, $var->DefaultSelected]);
                }
            });
            $reader->sheet('VariableOptions', function($sheet) {
                $options = DB::table('edf_entitydefinedfieldsearch')
                        ->select('EntityDefinedFieldNameInTable', 'Value', 'Description', 'Description_KH')
                        ->where('TableID', $this->table)
                        ->get();
                $i = 2;
                foreach ($options as $op) {
                    $sheet->row($i++, [$op->EntityDefinedFieldNameInTable, $op->Value, $op->Description, $op->Description_KH]);
                }
            });
            $reader->sheet('Conditions', function($sheet) {
                $conditions = DB::table('conditionlist')
                        ->get();
                $i = 2;
                foreach ($conditions as $condition) {
                    $sheet->row(2, [$condition->ConditionCode, $condition->ConditionNameEN, $condition->ConditionNameKH, $condition->ConditionSymbol]);
                }
            });
            $reader->sheet('VariableConditions', function($sheet) {
                $var_conditions = DB::table('edf_condition')
                                ->select('EntityDefinedFieldNameInTable', 'ConditionCode')
                                ->where('TableID', $this->table)->get();
                $i = 2;
                foreach ($var_conditions as $var_con) {
                    $sheet->row($i++, [$var_con->EntityDefinedFieldNameInTable, $var_con->ConditionCode]);
                }
            });
        })->export('xlsx');
        //return "http://localhost:8080/Monitoring/storage/exports/variable-list.xlsx";
    }

}
