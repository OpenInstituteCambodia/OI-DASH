<?php

namespace App\Http\Middleware;

use Closure;

class AuthoricationMiddleware {

    /**
     * Handle an incoming request.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \Closure  $next
     * @return mixed
     */
    public function handle($request, Closure $next) {
//        if (!$request->cookie('lang')) {
//            return response('cookie')->withCookie(cookie()->forever('lang', '1'));
//        }
        return $next($request);
    }

}
