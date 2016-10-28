@extends('layouts.user')

@section('content')
	<div class="container" style="margin-top: 20px">
		<div class="row">
			<div class="col-md-8 col-md-offset-2">
				<div class="panel panel-default" style="border: none">
					<div class="panel-heading heading" style="padding-top: 20px">{{ trans('auth.dashboards') }} - {{ trans('auth.cfs') }}</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-md-8 col-md-offset-2">
				<div class="panel panel-default" style="border: none">
					<div class="panel-body" style="background: #dff0d8; min-height: 100px; border: none">
						@if(Auth::check())
							@if(Auth::user()->role->level < 4)
								<h3>
									<a href="/users" title="user-management"><i class="fa fa-lg fa-fw fa-users"></i> <span class="menu-item-parent">{{ trans('user_content.user-management')}}</span></a>
								</h3>
							@endif
						@endif
						<h3 style="padding-top: 10px; padding-bottom: 15px">
							<a href="/home" title="cfs-report"><i class="fa fa-lg fa-fw fa-list-alt"></i> <span class="menu-item-parent">{{ trans('auth.report')}}</span></a>
						</h3>
					</div>
					<div class="panel-footer" style="background-color: #fcfdf8; min-height: 60px; border: none;">
						<h5 class="pull-left">
							<a href="/logout" title="cfs-report"><i class="fa fa-lg fa-fw fa-sign-out"></i> <span class="menu-item-parent">{{ trans('home.top-menu.sign-out')}}</span></a>
						</h5>
						<h5 class="pull-right">{{ trans('auth.cfs') }}</h5>
					</div>
				</div>
			</div>
		</div>
	</div>
@stop