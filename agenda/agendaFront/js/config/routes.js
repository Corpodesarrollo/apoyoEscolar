
angular.module('AgendaApp').config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/', {
                controller: 'formPermisosCtrl',
                templateUrl: 'pages/formPermisos/formularioPermisos.html'
            })
            .when('/AdminPermisos', {
                controller: 'adminPermisosCtrl',
                templateUrl: 'pages/formPermisos/formularioAdminPermisos.html'
            })
            .when('/adicionarDias', {
                controller: 'reposicionCtrl',
                templateUrl: 'pages/formPermisos/diasReposicionPermisos.html'
            })
            .otherwise({redirectTo: '/'});
     }]);
