'use strict';

angular.module('Clark', [])
  .config(function ($locationProvider, $routeProvider) {
	//$locationProvider.html5Mode(true);
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html'
        /*,
        controller: 'MainCtrl'*/
      }).when('/expediente',{templateUrl:'views/expediente.html'})
      .when('/movimiento',{templateUrl:'views/movimiento.html'})
      .when('/main',{templateUrl:'views/main.html'})
        .otherwise({ redirectTo: '/' });
      //$routeProvider.when('/expediente',{templateUrl:'views/expediente.html'});
  });
