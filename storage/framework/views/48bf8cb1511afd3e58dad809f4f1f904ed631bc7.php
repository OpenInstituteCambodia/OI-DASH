<?php $__env->startSection('content'); ?>
    <div class="content">
        <div class="row">
            <div class="col-md-12" style="padding: 0 35px">
                <div class="panel panel-primary" style="border-color: #5cb85c">
                    <div class="panel-heading" style="background-color: #5cb85c;">
                        <h4 style="display:inline" class="pull-left">
                            <b><i class="fa fa-lg fa-users"></i> <?php echo e(trans('user_content.user-management')); ?></b>
                        </h4>
                        <a href="/users/create" class="pull-right" style="margin-top: 5px !important; cursor: pointer; color: #fff"><i class="fa fa-plus fa-2x"></i></a>
                        <div class="clearfix"></div>
                    </div>

                    <div class="panel-body">
                        <table class="dataTable display table table-bordered" cellspacing="0" width="100%" >
                            <thead>
                            <tr>
                                <th>#</th>
                                <th><?php echo e(trans('user_content.field.name')); ?></th>
                                <th><?php echo e(trans('user_content.field.email')); ?></th>
                                <th><?php echo e(trans('user_content.field.role')); ?></th>
                                <th><?php echo e(trans('user_content.field.level')); ?></th>
                                <th><?php echo e(trans('user_content.field.actions')); ?></th>
                            </tr>
                            </thead>
                            <tbody>
                                <?php foreach($users as $i => $user): ?>
                                    <tr>
                                        <td><?php echo e($i + 1); ?></td>
                                        <td><?php echo e($user->name); ?></td>
                                        <td><?php echo e($user->email); ?></td>
                                        <?php if(session()->get('locale')): ?>
                                            <?php if(session()->get('locale') == 'en'): ?>
                                                <td><?php echo e($user->role->display_name); ?></td>
                                                <td><?php echo e($user->level->display_name); ?></td>
                                            <?php elseif(session()->get('locale') == 'km'): ?>
                                                <td><?php echo e($user->role->display_name_kh); ?></td>
                                                <td><?php echo e($user->level->display_name_kh); ?></td>
                                            <?php endif; ?>
                                        <?php else: ?>
                                            <td><?php echo e($user->role->display_name_kh); ?></td>
                                            <td><?php echo e($user->level->display_name_kh); ?></td>
                                        <?php endif; ?>
                                        <td>
                                            <a href="users/<?php echo e($user->id); ?>/edit" class="btn btn-xs btn-warning"><?php echo e(trans('button.edit')); ?></a>
                                            <button type="button" class="btn btn-danger btn-xs" data-toggle="modal" data-target="#modal-delete" data-user_id="<?php echo e($user->id); ?>" data-name="<?php echo e($user->name); ?>">
                                                <?php /*                                        <button type="button" class="btn btn-danger btn-xs" data-toggle="modal" data-target="#modal-delete" onclick="deactivateUser(<?php echo e($user->id); ?>, '<?php echo e($user->name); ?>')">*/ ?>
                                                <?php echo e(trans('button.delete')); ?>

                                            </button>
                                        </td>
                                    </tr>
                                <?php endforeach; ?>
                            </tbody>
                        </table>
                        <?php echo $__env->make('users.modal-delete', array_except(get_defined_vars(), array('__data', '__path')))->render(); ?>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12" style="padding: 0 35px">
                <div class="panel-footer" style="background-color: #fcfdf8; min-height: 60px; border: none;">
                    <h5 class="pull-right">
                        <a href="/" title="<?php echo e(trans('auth.dashboard')); ?>"><i class="fa fa-lg fa-fw fa-arrow-left"></i> <span class="menu-item-parent"><?php echo e(trans('auth.dashboard')); ?></span></a>
                    </h5>
                </div>
            </div>
        </div>

    </div>

    <script type="text/javascript">
        $(document).ready(function () {
            $('.dataTable').DataTable({});
    //        Calling Model delete
            $('#modal-delete').on('show.bs.modal', function(e) {
                var userId = $(e.relatedTarget).data('user_id');
                var userName = $(e.relatedTarget).data('name');
                $("#p-label-user-id").html(userId);
                $("#label-username").html(userName);
            });

        });
    </script>
<?php $__env->stopSection(); ?>








<?php echo $__env->make('layouts.user', array_except(get_defined_vars(), array('__data', '__path')))->render(); ?>