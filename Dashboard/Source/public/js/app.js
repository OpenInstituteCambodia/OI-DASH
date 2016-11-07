var app = angular.module("app", ['entity'], function ($interpolateProvider) {
    $interpolateProvider.startSymbol('<%');
    $interpolateProvider.endSymbol('%>');
});

// var myApp = angular.module("app", ['ngRoute']);
//
// // Configure routing for the application
// myApp.config(['$routeProvider','$locationProvider', function($routeProvider, $locationProvider){
//
// // Setting html5Mode as true to remove hashtag
//     $locationProvider.html5Mode(true);
//     $routeProvider.
//     when('/users/create', {
//         templateUrl:'users/create.blade.php',
//         controller : 'UserController'
//     }).
//     otherwise(
//         {redirectTo : "/"}
//     );
// }]);