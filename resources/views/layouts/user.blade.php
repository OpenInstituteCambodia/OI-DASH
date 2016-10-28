<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="csrf-token" content="{{ csrf_token() }}">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>{{ trans('auth.cfs') }}</title>

    <!--SmartAdmin-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" media="screen" href="{{asset('css/bootstrap.min.css')}}">
    <link rel="stylesheet" type="text/css" media="screen" href="{{asset('css/font-awesome.min.css')}}">
    <link rel="stylesheet" href="{{ URL::asset('css/dataTables.bootstrap.css') }}">

    <!-- Fonts -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css" integrity="sha384-XdYbMnZ/QjLh6iI4ogqCTaIjrFk87ip+ekIjefZch0Y+PvJ8CDYtEs1ipDmPorQ+" crossorigin="anonymous">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato:100,300,400,700">
    <link rel="stylesheet" href="{{ URL::asset('css/bootstrap.min.css') }}">
    <link rel="stylesheet" href="{{ URL::asset('css/dashboard.css') }}">
    <link rel="stylesheet" href="{{ URL::asset('css/custom_style.css') }}">

    <!-- Styles -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
    {{-- <link href="{{ elixir('css/app.css') }}" rel="stylesheet"> --}}
    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.3/jquery.min.js" integrity="sha384-I6F5OKECLVtK/BL+8iSLDEHowSAfUo76ZL9+kGAgTRdiByINKJaqTPH/QVNS1VDb" crossorigin="anonymous"></script>
    <script src="{{ URL::asset('js/plugin/datatables/jquery.dataTables.min.js') }}"></script>
    <script src="{{ URL::asset('js/plugin/datatables/dataTables.bootstrap.js') }}"></script>
    {{--<script src="{{ URL::asset('js/user.js') }}"></script>--}}

    <style>
        body {
            font-family: 'Lato';
        }

        .fa-btn {
            margin-right: 6px;
        }
    </style>
</head>
<body id="app-layout">

    <nav class="navbar navbar-default navbar-static-top web-header">
        <div class="container">
            <div class="navbar-header">
                <!-- Collapsed Hamburger -->
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#app-navbar-collapse">
                    <span class="sr-only">Toggle Navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>

                <ul class="nav navbar-nav navbar-left nav-with-color">
                    <li class="dropdown" style="list-style: none">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <span>
                                @if(Auth::check())
                                    {{{ isset(Auth::user()->name) ? Auth::user()->name : Auth::user()->email }}}
                                @endif
                            </span>
                            <i class="fa fa-angle-down"></i>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                @if(Auth::check())
                                    @if(Auth::user()->role)
                                        @if (session()->has('locale'))
                                            @if (session()->get('locale') == 'km')
                                                <a href="#">{{ trans('auth.role') }} {{ Auth::user()->role->display_name_kh }}</a>
                                            @elseif (session()->get('locale') == 'en')
                                                <a href="#">{{ trans('auth.role') }} {{ Auth::user()->role->display_name }}</a>
                                            @endif
                                        @else
                                            <a href="#">{{ trans('auth.role') }} {{ Auth::user()->role->display_name_kh }}</a>
                                        @endif
                                    @endif
                                @endif
                            </li>
{{--                            {{ \Request::segment(1) }}--}}
                            <li>
                                <a href="/logout">{{ trans('home.top-menu.sign-out') }}</a>
                            </li>
                        </ul>
                    </li>
                </ul>

            </div>

            <div class="collapse navbar-collapse nav-block" id="app-navbar-collapse">
                <ul class="nav navbar-nav navbar-right nav-with-color">
                    @if(!\Request::segment(1) == '')
                        <li>
                            <a href="/"><i class="fa fa-dashboard"></i> {{ trans('auth.dashboards') }}</a>
                        </li>

                        <li>
                            <a href="/home"><i class="fa fa-list-alt"></i> {{ trans('auth.report') }}</a>
                        </li>
                    @endif
                    <li class="dropdown pull-right" style="list-style: none">
                        @if (session()->has('locale'))
                            @if (session()->get('locale') == 'km')
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><img src="{{ URL::asset('img/kh.png') }}" alt="ភាសាខ្មែរ"> <span>ខ្មែរ</span> <i class="fa fa-angle-down"></i> </a>
                                <ul class="dropdown-menu">
                                    <li>
                                        <a href="{{ route('language', ['locale' => 'en']) }}"><img src="{{ URL::asset('img/uk.png') }}" alt="English"> English (EN)</a>
                                    </li>
                                </ul>
                            @elseif (session()->get('locale') == 'en')
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><img src="{{ URL::asset('img/uk.png') }}" alt="English"> <span>EN</span> <i class="fa fa-angle-down"></i> </a>
                                <ul class="dropdown-menu">
                                    <li>
                                        <a href="{{ route('language', ['locale' => 'km']) }}"><img src="{{ URL::asset('img/kh.png') }}" alt="ភាសាខ្មែរ"> ភាសាខ្មែរ (KH)</a>
                                    </li>
                                </ul>
                            @endif
                        @else
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><img src="{{ URL::asset('img/kh.png') }}" alt="ភាសាខ្មែរ"> <span>ខ្មែរ</span> <i class="fa fa-angle-down"></i> </a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href="{{ route('language', ['locale' => 'en']) }}"><img src="{{ URL::asset('img/uk.png') }}" alt="English"> English (EN)</a>
                                </li>
                            </ul>
                        @endif

                    </li>
                </ul>
            </div>
        </div>
    </nav>

    @yield('content')

    <!-- JavaScripts -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
{{-- <script src="{{ elixir('js/app.js') }}"></script> --}}

</body>
</html>
