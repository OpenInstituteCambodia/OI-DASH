@extends('layouts.app')

@section('content')
<div class="container-fluid" style="margin-top: 80px;">
    <div class="row">
        <div class="col-md-5 col-md-offset-1 pull-left">
            <div class="panel panel-default">
                <div class="panel-heading" style="background-color: #f0efee; height: 55px;">
                    <h4 class="panel-title pull-left" style="font-size: 20px; padding-top: 5px;">
                        <strong>{{ trans('auth.form.sign-in-form') }}</strong>
                    </h4>

                    <li class="dropdown pull-right" style="list-style: none; padding-top: 7px">
                        @if (session()->has('locale'))
                            @if (session()->get('locale') == 'km')
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown"> <img src="img/kh.png" alt="ភាសាខ្មែរ"> <span>ខ្មែរ</span> <i class="fa fa-angle-down"></i> </a>
                                <ul class="dropdown-menu">
                                    <li>
                                        <a href="lang/en"><img src="img/uk.png" alt="English"> English (EN)</a>
                                    </li>
                                </ul>
                            @elseif (session()->get('locale') == 'en')
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown"> <img src="img/uk.png" alt="English"> <span>EN</span> <i class="fa fa-angle-down"></i> </a>
                                <ul class="dropdown-menu">
                                    <li>
                                        <a href="lang/km"><img src="img/kh.png" alt="ភាសាខ្មែរ"> ភាសាខ្មែរ (KH)</a>
                                    </li>
                                </ul>
                            @endif
                        @else
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown"> <img src="img/kh.png" class="flag flag-kh" alt="ភាសាខ្មែរ"> <span>ខ្មែរ</span> <i class="fa fa-angle-down"></i> </a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href="lang/en"><img src="img/uk.png" alt="English"> English (EN)</a>
                                </li>
                            </ul>
                        @endif

                    </li>
                    <div class="clearfix"></div>
                </div>
                <div class="panel-body" style="background-color: #f5f5f5">
                    <form class="form-horizontal" role="form" method="POST" action="{{ url('/login') }}">
                        {{ csrf_field() }}

                        @if($errors->any())
                            <div class="alert alert-danger" style="text-align: center">
                                @foreach($errors->all() as $error)
                                    <p>{{ $error }}</p>
                                @endforeach
                            </div>
                        @endif

                        <div class="form-group{{ $errors->has('email') ? ' has-error' : '' }} no-margin-bottom">
                            <label for="email" class="col-md-4 control-label">{{ trans('auth.form.email') }}</label>
                            <div class="col-md-7">
                                <input id="email" type="email" class="form-control" name="email" value="{{ old('email') }}" placeholder="{{ trans('auth.form.enter-email') }}" required>
                            </div>
                        </div>

                        <div class="form-group{{ $errors->has('password') ? ' has-error' : '' }} no-margin-bottom">
                            <label for="password" class="col-md-4 control-label">{{ trans('auth.form.password') }}</label>
                            <div class="col-md-7">
                                <input id="password" type="password" class="form-control" name="password" placeholder="{{ trans('auth.form.enter-password') }}" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-4">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" name="remember"> {{ trans('auth.form.remember') }}
                                    </label>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-4">
                                <button type="submit" class="btn btn-success">
                                    <i class="fa fa-btn fa-sign-in"></i> {{ trans('button.sign-in') }}
                                </button>
                            </div>
                        </div>

                    </form>
                </div>
            </div>
        </div>
    </div>

    {{--<div class="col-md-2 pull-right">--}}
        {{--Hello--}}
    {{--</div>--}}
    {{----}}
    {{--<div class="clearfix"></div>--}}
</div>
@endsection
