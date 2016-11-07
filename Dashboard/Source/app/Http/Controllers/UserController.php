<?php

namespace MONITORING\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use MONITORING\Http\Requests;
use MONITORING\Http\Controllers\Controller;

//use MONITORING\Http\Requests\UserRequest;
use DB;
use MONITORING\User;
use MONITORING\Role;
use MONITORING\Level;
use MONITORING\Province;
use MONITORING\District;
use Validator;
use Redirect;
use View;

class UserController extends Controller {
    private $language_id;

    public function __construct()
    {
        $this->middleware('user');

        if (session()->has('locale')) {
            if (session()->get('locale') == 'km') {
                $this->language_id = 2;
            } elseif (session()->get('locale') == 'en') {
                $this->language_id = 1;
            }
        } else {
            $this->language_id = 2;
        }
    }

    public function rules(Request $request) {
//        $user = User::find($this->users);

        $method = $request->method();

        switch($method)
        {
            case 'GET':
            case 'DELETE':
            {
                return [];
            }
            case 'POST':
            {
                return [
                    'name' => 'required',
                    'email' => 'required|email|max:255|unique:users',
                    'password' => 'required|min:3|confirmed',
                    'password_confirmation' => 'required|min:3',
                    'role_id' => 'required',
                    'level_id' => 'required'
                ];
            }
            case 'PUT':
            case 'PATCH':
            {
                return [
                    'name' => 'required',
                    'email' => 'required|email|max:255',
                    'role_id' => 'required',
                    'level_id' => 'required'
                ];
            }
            default:break;
        }
    }

    public function index() {
        $users = User::where([['user_id', '=', Auth::user()->id], ['is_deleted', '=', '0']])->get();
        return response()->view('users.index', ['users' => $users]);
    }

    public function create() {
        $role_level = Auth::user()->role->level;

        if ($this->language_id == 1) {
            switch ($role_level){
                case 1:
                    $roles = Role::select(DB::raw("id AS role_id, display_name AS role_name"))->get();
                    break;
                case 2:
                    $roles = Role::select(DB::raw("id AS role_id, display_name AS role_name"))->where('level', '>', '2')->get();
                    break;
                case 3:
                    $roles = Role::select(DB::raw("id AS role_id, display_name AS role_name"))->where('level', '>', '3')->get();
                    break;
                default: break;
            }
            $provinces = Province::select(DB::raw("PROCODE AS province_id, PROVINCE AS province_name"))->get();
            $levels = Level::select(DB::raw("id AS level_id, display_name AS level_name"))->get();

        } elseif ($this->language_id == 2) {
            switch ($role_level){
                case 1:
                    $roles = Role::select(DB::raw("id AS role_id, display_name_kh AS role_name"))->get();
                    break;
                case 2:
                    $roles = Role::select(DB::raw("id AS role_id, display_name_kh AS role_name"))->where('level', '>', '2')->get();
                    break;
                case 3:
                    $roles = Role::select(DB::raw("id AS role_id, display_name_kh AS role_name"))->where('level', '>', '3')->get();
                    break;
                default: break;
            }
            $provinces = Province::select(DB::raw("PROCODE AS province_id, PROVINCE_KH AS province_name"))->get();
            $levels = Level::select(DB::raw("id AS level_id, display_name_kh AS level_name"))->get();
        }

        return View::make('users.create', compact('roles', 'levels', 'provinces'));
    }

    public function store(Request $request) {

        $validator = Validator::make($request->all(), $this->rules($request));

        if ($validator->fails()) {
            return \Redirect::to('users/create')->withErrors($validator)->withInput();
        }

        $role_level = Role::select('id', 'level')->where('id', '=', $request->input('role_id'))->get();

        if($role_level->first()->level <= 2) {
            $request['province_code'] = "0";
            $request['district_code'] = "0";
        } elseif ($role_level->first()->level == 3) {
            $request['district_code'] = "0";
        }

//        dd($request->all());

        $user = $request->all();
        $user['user_id'] = Auth::user()->id;
        User::create($user);

        return \Redirect::to('users');

    }

    public function edit($id) {
        $user = User::findOrFail($id);

        if ($this->language_id == 1) {
            switch ($user->role->level){
                case 1:
                    $roles = Role::select(DB::raw("id AS role_id, display_name AS role_name"))->get();
                    break;
                case 2:
                    $roles = Role::select(DB::raw("id AS role_id, display_name AS role_name"))->where('level', '>=', '2')->get();
                    break;
                case 3:
                    $roles = Role::select(DB::raw("id AS role_id, display_name AS role_name"))->where('level', '>=', '3')->get();
                    break;
                case 4:
                    $roles = Role::select(DB::raw("id AS role_id, display_name AS role_name"))->where('level', '>=', '4')->get();
                    break;
                default: break;
            }
            $provinces = Province::select(DB::raw("PROCODE AS province_id, PROVINCE AS province_name"))->get();
            $levels = Level::select(DB::raw("id AS level_id, display_name AS level_name"))->get();
            if ($user->role->level == 4) {
                $districts = District::select(DB::raw("DCODE AS district_id, DName_en AS district_name"))->where('PCode','=',$user->province_code);
            }
        }
        elseif ($this->language_id == 2) {
            switch ($user->role->level){
                case 1:
                    $roles = Role::select(DB::raw("id AS role_id, display_name_kh AS role_name"))->get();
                    break;
                case 2:
                    $roles = Role::select(DB::raw("id AS role_id, display_name_kh AS role_name"))->where('level', '>=', '2')->get();
                    break;
                case 3:
                    $roles = Role::select(DB::raw("id AS role_id, display_name_kh AS role_name"))->where('level', '>=', '3')->get();
                    break;
                case 4:
                    $roles = Role::select(DB::raw("id AS role_id, display_name_kh AS role_name"))->where('level', '>=', '4')->get();
                    break;
                default: break;
            }
            $provinces = Province::select(DB::raw("PROCODE AS province_id, PROVINCE_KH AS province_name"))->get();
            $levels = Level::select(DB::raw("id AS level_id, display_name_kh AS level_name"))->get();
            if ($user->role->level == 4) {
                $districts = District::select(DB::raw("DCODE AS district_id, DName_kh AS district_name"))->where('PCode', '=', $user->province_code);
            }
        }

        return View::make('users.edit', compact('user', 'roles', 'levels', 'provinces', 'districts'));
    }

    public function update($id, Request $request) {
        $user = User::findOrFail($id);
        $newPassword = $request->input('password');

//        dd($request->all());

        $validator = Validator::make($request->all(), $this->rules($request));

        if ($validator->fails()) {
            return \Redirect::to('users/'.$user->id.'/edit')->withErrors($validator)->withInput();
        }

        if(empty($newPassword)){
            $user->update($request->except('password'));
        }else{
            $user->update($request->all());
        }
        return \Redirect::to('users');
    }

    public function destroy($user_id) {
//        dd($user_id);
        $user = User::findOrFail($user_id);
        $user->is_deleted = 1;
        $user->update();

//        $result = "success";

//        return \Redirect::back();
        return response(200);
    }

    public function getDistrict($p_code) {
        if ($this->language_id == 1) {
            $results = District::select(DB::raw("DCode AS id, DName_en AS name"))->where('PCode', '=', $p_code)->get();
        } elseif ($this->language_id == 2) {
            $results = District::select(DB::raw("DCode AS id, DName_kh AS name"))->where('PCode', '=', $p_code)->get();
        }

        return response($results, 200);
    }
}
