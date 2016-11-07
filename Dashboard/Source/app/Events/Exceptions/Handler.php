<?php

namespace MONITORING\Exceptions;

use Exception;
use Illuminate\Auth\Access\AuthorizationException;
use Illuminate\Database\Eloquent\ModelNotFoundException;
use Symfony\Component\HttpKernel\Exception\HttpException;
use Illuminate\Foundation\Validation\ValidationException;
use Symfony\Component\HttpKernel\Exception\NotFoundHttpException;
use Illuminate\Foundation\Exceptions\Handler as ExceptionHandler;

class Handler extends ExceptionHandler
{
    /**
     * A list of the exception types that should not be reported.
     *
     * @var array
     */
    protected $dontReport = [
        AuthorizationException::class,
        HttpException::class,
        ModelNotFoundException::class,
        ValidationException::class,
    ];

    /**
     * Report or log an exception.
     *
     * This is a great spot to send exceptions to Sentry, Bugsnag, etc.
     *
     * @param  \Exception  $e
     * @return void
     */
    public function report(Exception $e)
    {
        return parent::report($e);
    }

    /**
     * Render an exception into an HTTP response.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \Exception  $e
     * @return \Illuminate\Http\Response
     */
    public function render($request, Exception $e)
    {
//        if ($e instanceof ModelNotFoundException) {
//            $e = new NotFoundHttpException($e->getMessage(), $e);
//        }
//
//        // handle Angular routes when accessed directly from the browser without the need of the '#'
//        if ($e instanceof NotFoundHttpException) {
//
//            $url = parse_url($request->url());
//
//            $angular_url = $url['scheme'] . '://' . $url['host'] . '/#' . $url['path'];
//
//            return response()->redirectTo($angular_url);
//        }

        if($e instanceof NotFoundHttpException)
        {
            return Redirect::to('/#' . Request::path());
        }

        return parent::render($request, $e);
    }
}
