<!DOCTYPE html>
<html lang="en-us" id="extr-page">
    <head>
        <meta charset="utf-8">
        <title>Login</title>
        <meta name="description" content="">
        <meta name="author" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <meta name="csrf-token" content="<?php echo e(csrf_token()); ?>">
        <!-- #CSS Links -->
        <!-- Basic Styles -->
        <link rel="stylesheet" type="text/css" media="screen" href="<?php echo e(asset('css/bootstrap.min.css')); ?>">
        <link rel="stylesheet" type="text/css" media="screen" href="<?php echo e(asset('css/font-awesome.min.css')); ?>">

        <!-- SmartAdmin Styles : Caution! DO NOT change the order -->
        <link rel="stylesheet" type="text/css" media="screen" href="<?php echo e(asset('css/smartadmin-production-plugins.min.css')); ?>">
        <link rel="stylesheet" type="text/css" media="screen" href="<?php echo e(asset('css/smartadmin-production.min.css')); ?>">
        <link rel="stylesheet" type="text/css" media="screen" href="<?php echo e(asset('css/smartadmin-skins.min.css')); ?>">

        <!-- SmartAdmin RTL Support -->
        <link rel="stylesheet" type="text/css" media="screen" href="<?php echo e(asset('css/smartadmin-rtl.min.css')); ?>"> 


        <!-- #FAVICONS -->
        <link rel="shortcut icon" href="<?php echo e(asset('img/favicon/favicon.ico')); ?>" type="image/x-icon">
        <link rel="icon" href="<?php echo e(asset('img/favicon/favicon.ico')); ?>" type="image/x-icon">

        <!-- #GOOGLE FONT -->
        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,700italic,300,400,700">

        <!-- #APP SCREEN / ICONS -->
        <!-- Specifying a Webpage Icon for Web Clip 
                 Ref: https://developer.apple.com/library/ios/documentation/AppleApplications/Reference/SafariWebContent/ConfiguringWebApplications/ConfiguringWebApplications.html -->
        <link rel="apple-touch-icon" href="<?php echo e(asset('img/splash/sptouch-icon-iphone.png')); ?>">
        <link rel="apple-touch-icon" sizes="76x76" href="<?php echo e(asset('img/splash/touch-icon-ipad.png')); ?>">
        <link rel="apple-touch-icon" sizes="120x120" href="<?php echo e(asset('img/splash/touch-icon-iphone-retina.png')); ?>">
        <link rel="apple-touch-icon" sizes="152x152" href="<?php echo e(asset('img/splash/touch-icon-ipad-retina.png')); ?>">

        <!-- iOS web-app metas : hides Safari UI Components and Changes Status Bar Appearance -->
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">

        <!-- Startup image for web apps -->
        <link rel="apple-touch-startup-image" href="<?php echo e(asset('img/splash/ipad-landscape.png')); ?>" media="screen and (min-device-width: 481px) and (max-device-width: 1024px) and (orientation:landscape)">
        <link rel="apple-touch-startup-image" href="<?php echo e(asset('img/splash/ipad-portrait.png')); ?>" media="screen and (min-device-width: 481px) and (max-device-width: 1024px) and (orientation:portrait)">
        <link rel="apple-touch-startup-image" href="<?php echo e(asset('img/splash/iphone.png')); ?>" media="screen and (max-device-width: 320px)">

    </head>

    <body class="animated fadeInDown">

        <header id="header">

            <div id="logo-group">
                <span id="logo"><img src="<?php echo e(url('img/oi-logo.gif')); ?>" alt="SmartAdmin" style="padding-bottom: 20px;"> </span>
            </div>

            <span id="extr-page-header-space"> <span class="hidden-mobile hidden-xs">Need an account?</span> <a href="register.html" class="btn btn-danger">Create account</a> </span>

        </header>

        <div id="main" role="main">

            <!-- MAIN CONTENT -->
            <div id="content" class="container">

                <div class="row">
                    <div class="col-xs-12 col-sm-12 col-md-7 col-lg-8 hidden-xs hidden-sm">
                        <h1 class="txt-color-red login-header-big">Open Institute</h1>
                        <div class="hero">

                            <div class="pull-left login-desc-box-l">
                                <h4 class="paragraph-header">It's Okay to be Smart. Experience the simplicity of SmartAdmin, everywhere you go!</h4>
                                <div class="login-app-icons">
                                    <a href="javascript:void(0);" class="btn btn-danger btn-sm">Frontend Template</a>
                                    <a href="javascript:void(0);" class="btn btn-danger btn-sm">Find out more</a>
                                </div>
                            </div>

                            <img src="<?php echo e(url('img/demo/iphoneview.png')); ?>" class="pull-right display-image" alt="" style="width:210px">

                        </div>

                        <div class="row">
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                <h5 class="about-heading">About SmartAdmin - Are you up to date?</h5>
                                <p>
                                    Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa.
                                </p>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                <h5 class="about-heading">Not just your average template!</h5>
                                <p>
                                    Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi voluptatem accusantium!
                                </p>
                            </div>
                        </div>

                    </div>
                    <div class="col-xs-12 col-sm-12 col-md-5 col-lg-4">
                        <div class="well no-padding">
                            <div id="login-form" class="smart-form client-form">
                                <header>
                                    Sign In
                                </header>

                                <fieldset>

                                    <section>
                                        <label class="label">E-mail</label>
                                        <label class="input"> <i class="icon-append fa fa-user"></i>
                                            <input id="inputUserName" type="text" name="username">
                                            <b class="tooltip tooltip-top-right"><i class="fa fa-user txt-color-teal"></i> Please enter email address/username</b></label>
                                    </section>

                                    <section>
                                        <label class="label">Password</label>
                                        <label class="input"> <i class="icon-append fa fa-lock"></i>
                                            <input id="inputPassword" type="password" name="password">
                                            <b class="tooltip tooltip-top-right"><i class="fa fa-lock txt-color-teal"></i> Enter your password</b> </label>
                                        <div class="note">
                                            <a href="forgotpassword.html">Forgot password?</a>
                                        </div>
                                    </section>

                                    <section>
                                        <label class="checkbox">
                                            <input type="checkbox" name="remember" checked="">
                                            <i></i>Stay signed in</label>
                                    </section>
                                </fieldset>
                                <footer>
                                    <button type="submit" class="btn btn-primary" onclick="login()">
                                        Sign in
                                    </button>
                                </footer>
                            </div>

                        </div>

                        <h5 class="text-center"> - Or sign in using -</h5>

                        <ul class="list-inline text-center">
                            <li>
                                <a href="javascript:void(0);" class="btn btn-primary btn-circle"><i class="fa fa-facebook"></i></a>
                            </li>
                            <li>
                                <a href="javascript:void(0);" class="btn btn-info btn-circle"><i class="fa fa-twitter"></i></a>
                            </li>
                            <li>
                                <a href="javascript:void(0);" class="btn btn-warning btn-circle"><i class="fa fa-linkedin"></i></a>
                            </li>
                        </ul>

                    </div>
                </div>
            </div>

        </div>

        <!--================================================== -->	

        <!-- PACE LOADER - turn this on if you want ajax loading to show (caution: uses lots of memory on iDevices)-->
        <script src="js/plugin/pace/pace.min.js"></script>

        <!-- Link to Google CDN's jQuery + jQueryUI; fall back to local -->
        <script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
        <script> if (!window.jQuery) {
                                            document.write('<script src="js/libs/jquery-2.1.1.min.js"><\/script>');}</script>

        <script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
        <script> if (!window.jQuery.ui) {
                                            document.write('<script src="js/libs/jquery-ui-1.10.3.min.js"><\/script>');}</script>

        <!-- IMPORTANT: APP CONFIG -->
        <script src="<?php echo e(asset('js/app.config.js')); ?>"></script>

        <!-- JS TOUCH : include this plugin for mobile drag / drop touch events 		
        <script src="js/plugin/jquery-touch/jquery.ui.touch-punch.min.js"></script> -->

        <!-- BOOTSTRAP JS -->		
        <script src="<?php echo e(asset('js/bootstrap/bootstrap.min.js')); ?>"></script>

        <!-- JQUERY VALIDATE -->
        <script src="<?php echo e(asset('js/plugin/jquery-validate/jquery.validate.min.js')); ?>"></script>

        <!-- JQUERY MASKED INPUT -->
        <script src="<?php echo e(asset('js/plugin/masked-input/jquery.maskedinput.min.js')); ?>"></script>

        <!--[if IE 8]>
                
                <h1>Your browser is out of date, please update your browser by going to www.microsoft.com/download</h1>
                
        <![endif]-->

        <!-- MAIN APP JS FILE -->
        <script src="<?php echo e(asset('js/app.min.js')); ?>"></script>

        <script type="text/javascript">
                                        runAllForms();

                                        $(function () {
                                            // Validation
                                            $("#login-form").validate({
                                                // Rules for form validation
                                                rules: {
                                                    username: {
                                                        required: true,
                                                    },
                                                    password: {
                                                        required: true,
                                                        minlength: 3,
                                                        maxlength: 20
                                                    }
                                                },
                                                // Messages for form validation
                                                messages: {
                                                    username: {
                                                        required: 'Please enter your username',
                                                        email: 'Please enter a VALID email address'
                                                    },
                                                    password: {
                                                        required: 'Please enter your password'
                                                    }
                                                },
                                                // Do not change code below
                                                errorPlacement: function (error, element) {
                                                    error.insertAfter(element.parent());
                                                }
                                            });
                                        });
        </script>
        <script>
            $(document).ready(function () {
                $("input").keypress(function (event) {
                    if (event.which === 13) {
                        event.preventDefault();
                        login();
                    }
                });
            });
            $.ajaxSetup({
                headers: {
                    'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
                }
            });
            var login = function () {
                var userName;
                var password;
                userName = $("#inputUserName").val();
                password = $("#inputPassword").val();
                $.ajax({
                    url: "<?php echo e(url('auth/process')); ?>",
                    data: {userName: userName, password: password},
                    type: 'POST',
                    success: function () {
                        window.location.replace("<?php echo e(url('/')); ?>");
                    }
                });
            };
        </script>
    </body>
</html>

<?php $__env->startSection('content'); ?>
<div class="container-fluid" style="margin-top: 80px;">
    <div class="row">
        <div class="col-md-5 col-md-offset-1 pull-left">
            <div class="panel panel-default">
                <div class="panel-heading" style="background-color: #f0efee; height: 55px;">
                    <h4 class="panel-title pull-left" style="font-size: 20px; padding-top: 5px;">
                        <strong><?php echo e(trans('auth.form.sign-in-form')); ?></strong>
                    </h4>

                    <li class="dropdown pull-right" style="list-style: none; padding-top: 7px">
                        <?php if(session()->has('locale')): ?>
                            <?php if(session()->get('locale') == 'km'): ?>
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown"> <img src="img/kh.png" alt="ភាសាខ្មែរ"> <span>ខ្មែរ</span> <i class="fa fa-angle-down"></i> </a>
                                <ul class="dropdown-menu">
                                    <li>
                                        <a href="lang/en"><img src="img/uk.png" alt="English"> English (EN)</a>
                                    </li>
                                </ul>
                            <?php elseif(session()->get('locale') == 'en'): ?>
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown"> <img src="img/uk.png" alt="English"> <span>EN</span> <i class="fa fa-angle-down"></i> </a>
                                <ul class="dropdown-menu">
                                    <li>
                                        <a href="lang/km"><img src="img/kh.png" alt="ភាសាខ្មែរ"> ភាសាខ្មែរ (KH)</a>
                                    </li>
                                </ul>
                            <?php endif; ?>
                        <?php else: ?>
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown"> <img src="img/kh.png" class="flag flag-kh" alt="ភាសាខ្មែរ"> <span>ខ្មែរ</span> <i class="fa fa-angle-down"></i> </a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href="lang/en"><img src="img/uk.png" alt="English"> English (EN)</a>
                                </li>
                            </ul>
                        <?php endif; ?>

                    </li>
                    <div class="clearfix"></div>
                </div>
                <div class="panel-body" style="background-color: #f5f5f5">
                    <form class="form-horizontal" role="form" method="POST" action="<?php echo e(url('/login')); ?>">
                        <?php echo e(csrf_field()); ?>


                        <?php if($errors->any()): ?>
                            <div class="alert alert-danger" style="text-align: center">
                                <?php foreach($errors->all() as $error): ?>
                                    <p><?php echo e($error); ?></p>
                                <?php endforeach; ?>
                            </div>
                        <?php endif; ?>

                        <div class="form-group<?php echo e($errors->has('email') ? ' has-error' : ''); ?> no-margin-bottom">
                            <label for="email" class="col-md-4 control-label"><?php echo e(trans('auth.form.email')); ?></label>
                            <div class="col-md-7">
                                <input id="email" type="email" class="form-control" name="email" value="<?php echo e(old('email')); ?>" placeholder="<?php echo e(trans('auth.form.enter-email')); ?>" required>
                            </div>
                        </div>

                        <div class="form-group<?php echo e($errors->has('password') ? ' has-error' : ''); ?> no-margin-bottom">
                            <label for="password" class="col-md-4 control-label"><?php echo e(trans('auth.form.password')); ?></label>
                            <div class="col-md-7">
                                <input id="password" type="password" class="form-control" name="password" placeholder="<?php echo e(trans('auth.form.enter-password')); ?>" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-4">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" name="remember"> <?php echo e(trans('auth.form.remember')); ?>

                                    </label>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-4">
                                <button type="submit" class="btn btn-success">
                                    <i class="fa fa-btn fa-sign-in"></i> <?php echo e(trans('button.sign-in')); ?>

                                </button>
                            </div>
                        </div>

                    </form>
                </div>
            </div>
        </div>
    </div>

    <?php /*<div class="col-md-2 pull-right">*/ ?>
        <?php /*Hello*/ ?>
    <?php /*</div>*/ ?>
    <?php /**/ ?>
    <?php /*<div class="clearfix"></div>*/ ?>
</div>
<?php $__env->stopSection(); ?>

<?php echo $__env->make('layouts.app', array_except(get_defined_vars(), array('__data', '__path')))->render(); ?>
