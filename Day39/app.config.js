mod.config(['$routeProvider', function($routeProvider) {
    $routeProvider
    .when('/', {
        templateUrl: 'views/home.view.html'

     }).when('/students', {
         templateUrl: 'views/students.view.html',
         controller: 'AppCtrl',
         controllerAs: 'ctrl'

     }).when('/about', {
         templateUrl: 'views/about.view.html'

     }).when('/addstudent', {
         templateUrl: 'views/addstudent.view.html'

     }).when('/editstudent/:studentId', {
         templateUrl: 'views/editstudent.view.html',
         controller: 'AppCtrl',
         controllerAs: 'ctrl'
     })
         .otherwise({redirectTo: '/'});

}]); // end config










