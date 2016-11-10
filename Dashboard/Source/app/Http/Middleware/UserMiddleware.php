<?php

namespace MONITORING\Http\Middleware;

use Closure;
use Redirect;

class UserMiddleware
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
        if ($request->user()->role->name == 'district')
        {
            return response()->view('users.forbidden');
        }

        return $next($request);
    }
}
