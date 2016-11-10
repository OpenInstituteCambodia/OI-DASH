<?php

namespace MONITORING\Http\Controllers;

use Illuminate\Http\Request;
use MONITORING\Http\Requests;
use MONITORING\Http\Controllers\Controller;
use DB;
use MONITORING\Table;

class EntityDefinedSearchController extends Controller {
    private $language_id;

    public function __construct() {
        if (session()->get('locale')) {
            if (session()->get('locale') == 'km') {
                $this->language_id = 2;
            } elseif (session()->get('locale') == 'en') {
                $this->language_id = 1;
            }
        } else {
            $this->language_id = 1;
        }
    }

    public function index() {
        $tables = Table::all(['id', 'TableName', 'TableCorespondingName']);
        for ($i = 0; $i < count($tables); $i++) {
            $tables[$i]->asset = DB::table('entitydefinedfield')
                    ->leftJoin('entitydefinedfieldlist', 'entitydefinedfield.EntityDefinedFieldListCode', '=', 'entitydefinedfieldlist.EntityDefinedFieldListCode')
                    ->select('entitydefinedfieldlist.EntityDefinedFiledListName', 'entitydefinedfield.EntityDefinedFieldNameInTable', 'entitydefinedfieldlist.EntityDefinedFieldListCategory', 'entitydefinedfieldlist.LanguageID')
                    ->orderBy('entitydefinedfieldlist.EntityDefinedFieldListCategory')
                    ->where('entitydefinedfield.TableID', '=', $tables[$i]->id)
                    ->where('entitydefinedfieldlist.LanguageID','=',$this->language_id)
                    ->get();
        }
        return response()
                        ->view('content.system.entity_defined_field_search', ['tabledetails' => $tables]);
    }

}
