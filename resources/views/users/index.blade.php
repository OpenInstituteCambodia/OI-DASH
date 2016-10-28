@extends('layouts.user')

@section('content')
    <div class="content">
        <div class="row">
            <div class="col-md-12" style="padding: 0 35px">
                <div class="panel panel-primary" style="border-color: #5cb85c">
                    <div class="panel-heading" style="background-color: #5cb85c;">
                        <h4 style="display:inline" class="pull-left">
                            <b><i class="fa fa-lg fa-users"></i> {{ trans('user_content.user-management') }}</b>
                        </h4>
                        <a href="/users/create" class="pull-right" style="margin-top: 5px !important; cursor: pointer; color: #fff"><i class="fa fa-plus fa-2x"></i></a>
                        <div class="clearfix"></div>
                    </div>

                    <div class="panel-body">
                        <table class="dataTable display table table-bordered" cellspacing="0" width="100%" >
                            <thead>
                            <tr>
                                <th>#</th>
                                <th>{{ trans('user_content.field.name')}}</th>
                                <th>{{ trans('user_content.field.email')}}</th>
                                <th>{{ trans('user_content.field.role')}}</th>
                                <th>{{ trans('user_content.field.level')}}</th>
                                <th>{{ trans('user_content.field.actions')}}</th>
                            </tr>
                            </thead>
                            <tbody>
                                @foreach($users as $i => $user)
                                    <tr>
                                        <td>{{ $i + 1 }}</td>
                                        <td>{{ $user->name }}</td>
                                        <td>{{ $user->email }}</td>
                                        @if (session()->get('locale'))
                                            @if (session()->get('locale') == 'en')
                                                <td>{{ $user->role->display_name }}</td>
                                                <td>{{ $user->level->display_name }}</td>
                                            @elseif (session()->get('locale') == 'km')
                                                <td>{{ $user->role->display_name_kh }}</td>
                                                <td>{{ $user->level->display_name_kh }}</td>
                                            @endif
                                        @else
                                            <td>{{ $user->role->display_name_kh }}</td>
                                            <td>{{ $user->level->display_name_kh }}</td>
                                        @endif
                                        <td>
                                            <a href="users/{{ $user->id }}/edit" class="btn btn-xs btn-warning">{{ trans('button.edit')}}</a>
                                            <button type="button" class="btn btn-danger btn-xs" data-toggle="modal" data-target="#modal-delete" data-user_id="{{ $user->id }}" data-name="{{ $user->name }}">
                                                {{--                                        <button type="button" class="btn btn-danger btn-xs" data-toggle="modal" data-target="#modal-delete" onclick="deactivateUser({{ $user->id }}, '{{ $user->name }}')">--}}
                                                {{ trans('button.delete')}}
                                            </button>
                                        </td>
                                    </tr>
                                @endforeach
                            </tbody>
                        </table>
                        @include('users.modal-delete')
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12" style="padding: 0 35px">
                <div class="panel-footer" style="background-color: #fcfdf8; min-height: 60px; border: none;">
                    <h5 class="pull-right">
                        <a href="/" title="{{ trans('auth.dashboard') }}"><i class="fa fa-lg fa-fw fa-arrow-left"></i> <span class="menu-item-parent">{{ trans('auth.dashboard') }}</span></a>
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
@stop







