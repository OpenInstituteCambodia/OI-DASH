<div class="form-group">
    <h4><?php echo e(trans('user_content.field.name')); ?></h4>
    <?php echo Form::text('name', null, ['required', 'class'=>'form-control', 'placeholder'=>'ឈ្មោះ']); ?>

</div>

<div class="form-group">
    <h4><?php echo e(trans('user_content.field.email')); ?></h4>
    <?php echo Form::text('email', null, ['required', 'class'=>'form-control', 'placeholder'=>'អីុម៉ែល']); ?>

    <?php /*    <?php echo Form::text('email', null, array('class'=>'form-control', 'placeholder'=>'E-mail address')); ?>*/ ?>
</div>

<div class="form-group">
    <h4><?php echo e(trans('user_content.field.password')); ?></h4>
    <?php echo Form::password('password', array('placeholder'=>'លេខសំងាត់', 'class'=>'form-control')); ?>

</div>

<div class="form-group">
    <h4><?php echo e(trans('user_content.field.confirmed-password')); ?></h4>
    <?php echo Form::password('password_confirmation', array('placeholder'=>'ផ្ទៀងផ្ទាត់លេខសំងាត់', 'class'=>'form-control')); ?>

</div>

<div class="form-group">
    <h4><?php echo e(trans('user_content.field.role')); ?></h4>
    <?php echo Form::select('role_id', $roles->lists('display_name_kh','id'), null, ['id' => 'selected-role','class'=>'form-control', 'placeholder' => '--ជ្រេីសរេីសតួនាទី--']); ?>

</div>

<div class="form-group">
    <h4><?php echo e(trans('user_content.field.level')); ?></h4>
    <?php echo Form::select('level_id', $levels->lists('display_name_kh', 'id')->toArray(), null, ['class'=>'form-control', 'placeholder' => '--ជ្រេីសរេីសកំរិត--']); ?>

</div>

<div class="form-group" id="province-block">
    <h4><?php echo e(trans('user_content.field.province')); ?></h4>
    <?php echo Form::select('province_code', $provinces->lists('name_kh', 'id')->toArray(), null, ['id' => 'selected-province' ,'class'=>'form-control', 'placeholder' => '--ជ្រេីសរេីសខេត្ត--']); ?>

</div>

<div class="form-group" id="district-block">
    <h4><?php echo e(trans('user_content.field.district')); ?></h4>
    <?php echo Form::select('district_code', [], null, ['id' => 'district-list' ,'class'=>'form-control', 'placeholder' =>trans('user_content.select-district')]); ?>

</div>

<div class="form-group">
    <?php echo Form::submit($submitButtonText, ['id' => 'btn_id', 'class'=>'btn btn-primary']); ?>

    <?php /*    <?php echo Form::button('<i class="fa fa-btn fa-user"></i>', array('type' => 'submit', 'class' => 'btn btn-primary')); ?>*/ ?>
</div>

<script>
    $(document).ready(function () {
        if($('#btn_id').val() === "កែប្រែអ្នកប្រេីប្រាស់") {
//            selectingCondition();
//            var p_code = $('#selected-province').val();
//            getDistrict(p_code)
        } else {
            $('#province-block').hide();
            $('#district-block').hide();
        }

//        Showing or hiding Province or District
        $('#selected-role').change(function() {
            selectingCondition();
        });

//        Selecting Districts according to Province
        $('#selected-province').change(function () {
            if ($('#selected-province').val() != "") {
                var p_code = $('#selected-province').val();
                getDistrict(p_code);
            }
        })
    });

    function selectingCondition() {
        var selected_role_text = $("#selected-role option:selected").text();
        if(selected_role_text === "មន្ទីរ(ថ្នាក់ខេត្ត)") {
            $("#province-block").show();
            $('#district-block').hide();
        } else if(selected_role_text === "ស្រុក(ថ្នាក់ស្រុក)") {
            $('#province-block').show();
            $('#district-block').show();
        } else if(selected_role_text != "មន្ទីរ(ថ្នាក់ខេត្ត)"){
            $('#province-block').hide();
            $('#district-block').hide();
        }
    }

    function getDistrict(p_code) {
        jQuery.ajax({
            url: "/get-district-by-pro-code/" + p_code,
            type: "GET",
            data: {
                "p_code" : p_code
            },
            dataType: "json",
            success: function(data){
                $("#district-list").empty();
                $("#district-list").html("<option value=''><?php echo e(trans('user_content.select-district')); ?></option>");
                $.each(data, function(i, value) {
                    $('#district-list').append('<option value="' + value.id + '">' + value.name_kh + '</option>');
                });
            }
        });
    }

</script>