<?php

namespace MONITORING;

use Illuminate\Database\Eloquent\Model;
use DB;

class EntityDefinedFieldCondition extends Model {

    protected $table = 'entitydefinedfieldcondition';

    /**
     * Getting conditions for dropdown. The middle dropdown
     */
    public static function getConditionByFieldID($fieldName, $languageID) {
        return DB::table('entitydefinedfield')
                        ->join('entitydefinedfieldcondition', 'entitydefinedfield.id', '=', 'entitydefinedfieldcondition.EntityDefinedFieldID')
                        ->join('condition', 'entitydefinedfieldcondition.ConditionCode', '=', 'condition.ConditionCode')
                        ->select('condition.ConditionName', 'condition.ConditionSymbol')
                        ->where('entitydefinedfield.EntityDefinedFieldNameInTable', $fieldName)
                        ->where('condition.LanguageID', $languageID)
                        ->get();
    }

}