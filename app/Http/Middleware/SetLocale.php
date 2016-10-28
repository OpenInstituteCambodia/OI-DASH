<?php

namespace MONITORING\Http\Middleware;

use Closure;
use Config;
use App;

class SetLocale
{
    /**
     * Handle an incoming request.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \Closure  $next
     * @return mixed
     */
    public function handle($request, Closure $next)
    {
        if (session()->has('locale')) {
            $locale = session()->get('locale', Config::get('app.locale'));
            App::setLocale($locale);
        }

        return $next($request);
    }
}
