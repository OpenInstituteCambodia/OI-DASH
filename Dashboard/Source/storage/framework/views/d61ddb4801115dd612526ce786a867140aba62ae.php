<?php $__env->startSection('content'); ?>
    <!-- widget div-->
    <div class="content">
        <div class="row">
            <div class="col-md-12" style="padding: 0 35px">
                <div class="panel panel-primary" style="border-color: #5cb85c">
                    <div class="panel-heading" style="background-color: #5cb85c;">
                        <h4 style="display:inline" class="pull-left">
                            <b><i class="fa fa-lg fa-users"></i> <?php echo e(trans('user_content.user-management')); ?></b>
                        </h4>
                        <a href="/users" class="pull-right" style="margin-top: 5px !important; cursor: pointer; color: #fff"><i class="fa fa-arrow-left"></i> <?php echo e(trans('auth.back-user')); ?></a>
                        <div class="clearfix"></div>
                    </div>
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4 style="display:inline" class="pull-left">
                                            <b><?php echo e(trans('user_content.create-new-user')); ?></b>
                                        </h4>
                                        <div class="clearfix"></div>
                                    </div>

                                    <div class="panel-body">
                                        <div class="col-md-7">

                                            <?php echo Form::model($user = new \MONITORING\User, ['url' => '/users', 'method' => 'POST', 'class'=>'form']); ?>

                                                <?php echo $__env->make('users.form', ['submitButtonText' => trans('button.create-user')], array_except(get_defined_vars(), array('__data', '__path')))->render(); ?>
                                            <?php echo Form::close(); ?>


                                            <?php /*<form class="form-horizontal" role="form" method="POST" action="<?php echo e(url('/users')); ?>">*/ ?>
                                            <?php /*<?php echo e(csrf_field()); ?>*/ ?>
                                            <?php /*<?php echo $__env->make('users.form', ['submitButtonText' => 'Add User'], array_except(get_defined_vars(), array('__data', '__path')))->render(); ?>*/ ?>
                                            <?php /*</form>*/ ?>

                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- end widget div -->
<?php $__env->stopSection(); ?>

<?php echo $__env->make('layouts.user', array_except(get_defined_vars(), array('__data', '__path')))->render(); ?>