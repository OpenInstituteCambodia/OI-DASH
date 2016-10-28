<?php

namespace MONITORING\Http\Requests;

use MONITORING\Http\Requests\Request;
use MONITORING\User;

class UserRequest extends Request
{
    /**
     * Determine if the user is authorized to make this request.
     *
     * @return bool
     */
    public function authorize()
    {
        return true;
    }

    /**
     * Get the validation rules that apply to the request.
     *
     * @return array
     */
    public function rules()
    {
//        $user = User::find($this->users);

        return [
            'name' => 'required',
            'email' => 'required|email|unique:users,email',
            'password' => 'required|confirmed',
            'confirmPassword' => 'required|confirmed',
            'role_id' => 'required',
            'level_id' => 'required'
        ];
    }
}
