<div class="modal fade" id="modal-delete" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <b><h3 class="modal-title"><?php echo e(trans('user_content.deleting-user')); ?> - <span id="label-username"></span></h3></b>
            </div>
            <div class="modal-body">
                <span id="p-label-user-id" style="display:none;"></span>
                <h4><?php echo e(trans('user_content.confirmation')); ?></h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><?php echo e(trans('button.no')); ?></button>
                <button type="button" class="btn btn-danger" id="btn-deactivate"><?php echo e(trans('button.yes')); ?></button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script type="text/javascript">
    $(document).ready(function() {
        $('#btn-deactivate').click(function () {
            var user_id = $("#p-label-user-id").html();

//            console.log(user_id);

            $.ajax({
                url: '/users/' + user_id,
                type: 'DELETE',
                data: {"user_id": user_id, "_token": "<?php echo e(csrf_token()); ?>"},
//                dataType: "json",
                success: function (data) {
//                    console.log(data);
                    $('#modal-delete').modal('hide');
                    location.reload();
                },
                error: function () {
                    console.log('error');
                }
            });
        });
    });
</script>

