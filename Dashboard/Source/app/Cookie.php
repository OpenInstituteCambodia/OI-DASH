<?php

namespace MONITORING;

use Illuminate\Database\Eloquent\Model;

class Cookie extends Model
{
    public static function setCookie($key,$value) {
        return response()->withCookie(cookie("$key", "$value", 60));
    }
}