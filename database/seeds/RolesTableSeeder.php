<?php

use Carbon\Carbon;
use Illuminate\Database\Seeder;

class RolesTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('roles')->delete();

        DB::table('roles')->insert([
            [
                'name' => 'admin',
                'display_name' => 'Administrator',
                'display_name_kh' => 'ថ្នាក់គ្រប់គ្រង',
                'level' => 1,
                'description' => 'It is for the role of administration',
                'description_kh' => 'សំរាប់តួនាទីជាអ្នកគ្រប់គ្រង',
                'created_at' => Carbon::now()->format('Y-m-d H:i:s')
            ],
            [
                'name' => 'national',
                'display_name' => 'National',
                'display_name_kh' => 'ក្រសួង(ថ្នាក់ជាតិ)',
                'level' => 2,
                'description' => 'It is for the role of national employment',
                'description_kh' => 'សំរាប់តួនាទីជាថ្នាក់ជាតិ',
                'created_at' => Carbon::now()->format('Y-m-d H:i:s')
            ],
            [
                'name' => 'province',
                'display_name' => 'Province',
                'display_name_kh' => 'មន្ទីរ(ថ្នាក់ខេត្ត)',
                'level' => 3,
                'description' => 'It is for the role of province employment',
                'description_kh' => 'សំរាប់តួនាទីជាថ្នាក់ខេត្ត',
                'created_at' => Carbon::now()->format('Y-m-d H:i:s')
            ],
            [
                'name' => 'district',
                'display_name' => 'District',
                'display_name_kh' => 'ស្រុក(ថ្នាក់ស្រុក)',
                'level' => 4,
                'description' => 'It is for the role of district employment',
                'description_kh' => 'សំរាប់តួនាទីជាថ្នាក់ស្រុក',
                'created_at' => Carbon::now()->format('Y-m-d H:i:s')
            ]
        ]);
    }
}
