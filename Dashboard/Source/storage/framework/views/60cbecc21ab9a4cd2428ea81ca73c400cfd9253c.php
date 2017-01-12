<?php $__env->startSection('content'); ?>
    <!-- #MAIN CONTENT -->
    <?php /*<div id="content"></div>*/ ?>
    <!-- END #MAIN CONTENT -->
<?php $__env->stopSection(); ?>

<?php echo $__env->make('layouts.admin', array_except(get_defined_vars(), array('__data', '__path')))->render(); ?>