<?php $__env->startSection('content'); ?>
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 style="display:inline" class="pull-left">
                        <b><?php echo e(trans('auth.permission')); ?></b>
                    </h4>
                    <div class="clearfix"></div>
                </div>

                <div class="panel-body">
                    <h1 style="text-align: center"><?php echo e(trans('auth.permission-warning')); ?></h1>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-8 col-md-offset-2" style="padding: 0 35px">
            <div class="panel-footer" style="background-color: #fcfdf8; min-height: 60px; border: none;">
                <h5 class="pull-right">
                    <a href="/" title="<?php echo e(trans('auth.dashboard')); ?>"><i class="fa fa-lg fa-fw fa-arrow-left"></i> <span class="menu-item-parent"><?php echo e(trans('auth.dashboard')); ?></span></a>
                </h5>
            </div>
        </div>
    </div>
<?php $__env->stopSection(); ?>
<?php echo $__env->make('layouts.user', array_except(get_defined_vars(), array('__data', '__path')))->render(); ?>