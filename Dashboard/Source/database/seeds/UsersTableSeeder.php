<?php


use Carbon\Carbon;
use Illuminate\Database\Seeder;
use MONITORING\Role;
use MONITORING\Level;

class UsersTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('users')->delete();

        $role = Role::first();

        $level = Level::first();

        $admin = [
            'name' => 'Administrator',
            'email' => 'admin@open.org.kh',
            'password' => bcrypt('admin12345678'),
            'role_id' => $role->id,
            'level_id' => $level->id,
            'province_code' => 0,
            'district_code' => 0,
            'created_at' => Carbon::now()->format('Y-m-d H:i:s')
        ];

        DB::table('users')->insert($admin);

    }
}
