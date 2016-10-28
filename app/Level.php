<?php

namespace MONITORING;

use Illuminate\Database\Eloquent\Model;

class Level extends Model
{
    public function users() {
        return $this->hasMany('MONITORING\User');
    }
}
