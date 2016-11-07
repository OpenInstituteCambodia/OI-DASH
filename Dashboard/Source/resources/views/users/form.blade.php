<div class="form-group">
    <h4>{{ trans('user_content.field.name')}}</h4>
    {!! Form::text('name', null, ['required', 'class'=>"form-control $errors->has('name') ? ' has-error' : ''", 'placeholder' => trans('user_content.field.name')]) !!}
    @if ($errors->has('name')) 
        <div style="margin-top: -10px !important;">
            <span style="color: red;"> <span > 
                <strong>{{ $errors->first('name') }}</strong> 
            </span>
        </div>
    @endif
</div>

<div class="form-group">
    <h4>{{ trans('user_content.field.email')}}</h4>
    {!! Form::text('email', null, ['required', 'class'=>"form-control $errors->has('email') ? ' has-error' : ''", 'placeholder' => trans('user_content.field.email')]) !!}
    @if ($errors->has('email')) 
        <div style="margin-top: -10px !important;">
            <span style="color: red;"> 
                <strong>{{ $errors->first('email') }}</strong> 
            </span>
        </div>
    @endif
{{--    {!! Form::text('email', null, array('class'=>'form-control', 'placeholder'=>'E-mail address')) !!}--}}
</div>

<div class="form-group">
    <h4>{{ trans('user_content.field.password')}}</h4>
    {!! Form::password('password', ['class'=>"form-control $errors->has('password') ? ' has-error' : ''", 'placeholder' => trans('user_content.field.password')]) !!}
    {{--@if ($errors->has('password')) --}}
        {{--<div style="margin-top: -10px !important;">--}}
            {{--<span style="color: red;"> --}}
                {{--<strong>{{ $errors->first('password') }}</strong> --}}
            {{--</span>--}}
        {{--</div>--}}
    {{--@endif--}}
</div>

<div class="form-group">
    <h4>{{ trans('user_content.field.confirmed-password')}}</h4>
    {!! Form::password('password_confirmation', ['class'=>"form-control $errors->has('password_confirmation') ? ' has-error' : ''", 'placeholder' => trans('user_content.field.confirmed-password')]) !!}
    @if ($errors->has('password')) 
        <div style="margin-top: -10px !important;">
            <span style="color: red;"> 
                <strong>{{ $errors->first('password') }}</strong> 
            </span>
        </div>
    @endif
</div>

<div class="form-group">
    <h4>{{ trans('user_content.field.role')}}</h4>
    {!! Form::select('role_id', $roles->lists('role_name','role_id'), null, ['id' => 'selected-role','class'=>"form-control $errors->has('role_id') ? ' has-error' : ''", 'placeholder' => trans('user_content.select-role')]) !!}
    @if ($errors->has('role_id')) 
        <div style="margin-top: -10px !important;">
            <span style="color: red;"> 
                <strong>{{ trans('validation.role') }}</strong> 
            </span>
        </div>
    @endif
</div>

<div class="form-group">
    <h4>{{ trans('user_content.field.level')}}</h4>
    {!! Form::select('level_id', $levels->lists('level_name', 'level_id')->toArray(), null, ['class'=>"form-control $errors->has('level_id') ? ' has-error' : ''", 'placeholder' => trans('user_content.select-level')]) !!}
    @if ($errors->has('level_id')) 
        <div style="margin-top: -10px !important;">
            <span style="color: red;"> 
                <strong>{{ trans('validation.level') }}</strong> 
            </span>
        </div>
    @endif
</div>

<div class="form-group" id="province-block">
    <h4>{{ trans('user_content.field.province')}}</h4>
    {!! Form::select('province_code', $provinces->lists('province_name', 'province_id')->toArray(), null, ['id' => 'selected-province' ,'class'=>'form-control', 'placeholder' => trans('user_content.select-province')]) !!}
</div>

<div class="form-group" id="district-block">
    <h4>{{ trans('user_content.field.district')}}</h4>
    {!! Form::select('district_code', isset($districts) ? $districts->lists('district_name', 'district_id')->toArray() : [], null, ['id' => 'district-list' ,'class'=>'form-control', 'placeholder' => trans('user_content.select-district')]) !!}
</div>

<div class="form-group">
    {!! Form::submit($submitButtonText, ['id' => 'btn_id', 'class'=>'btn btn-primary']) !!}
{{--    {!! Form::button('<i class="fa fa-btn fa-user"></i>', array('type' => 'submit', 'class' => 'btn btn-primary')) !!}--}}
</div>

<script>
    $(document).ready(function () {
        if($('#btn_id').val() === "Update User" || $('#btn_id').val() === "កែប្រែអ្នកប្រេីប្រាស់") {
//            selectingCondition();
//            var p_code = $('#selected-province').val();
//            getDistrict(p_code)

            selectingCondition();
        } else {
//            $('#province-block').empty();
            $('#province-block').hide();
            $('#district-block').hide();
        }

//        $('#province-block').hide();
//        $('#district-block').hide();

//        Showing or hiding Province or District
        $('#selected-role').change(function() {
            selectingCondition();
        });

//        Selecting Districts according to Province
        $('#selected-province').change(function () {
            if ($('#selected-province').val() != "") {
                var p_code = $('#selected-province').val();
                getDistrict(p_code);
            } else {
                $("#district-list").empty();
                $("#district-list").html("<option value=''>{{ trans('user_content.select-district') }}</option>");
            }
        });
    });

    function selectingCondition() {
        var selected_role_text = $("#selected-role option:selected").text();
        if(selected_role_text === "Province" || selected_role_text === "មន្ទីរ(ថ្នាក់ខេត្ត)") {
            $("#province-block").show();
            $('#district-block').hide();
//            alert('Province selected');
        } else if(selected_role_text === "District" || selected_role_text === "ស្រុក(ថ្នាក់ស្រុក)") {
            $('#province-block').show();
            $('#district-block').show();
        } else {
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
                $("#district-list").html("<option value=''>{{ trans('user_content.select-district') }}</option>");
                $.each(data, function(i, value) {
                    $('#district-list').append('<option value="' + value.id + '">' + value.name + '</option>');
                });
            }
        });
    }

</script>
