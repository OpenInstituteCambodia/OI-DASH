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