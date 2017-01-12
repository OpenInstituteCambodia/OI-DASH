@extends('layouts.user')

@section('content')
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 style="display:inline" class="pull-left">
                        <b>{{ trans('auth.permission') }}</b>
                    </h4>
                    <div class="clearfix"></div>
                </div>

                <div class="panel-body">
                    <h1 style="text-align: center">{{ trans('auth.permission-warning') }}</h1>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-8 col-md-offset-2" style="padding: 0 35px">
            <div class="panel-footer" style="background-color: #fcfdf8; min-height: 60px; border: none;">
                <h5 class="pull-right">
                    <a href="/" title="{{ trans('auth.dashboard') }}"><i class="fa fa-lg fa-fw fa-arrow-left"></i> <span class="menu-item-parent">{{ trans('auth.dashboard') }}</span></a>
                </h5>
            </div>
        </div>
    </div>
@stop