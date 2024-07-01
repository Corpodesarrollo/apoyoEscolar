/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

angular.module('AgendaApp')
        .run(['$rootScope', '$location', '$cookieStore', '$http',
            function ($rootScope, $location, $cookieStore, $http) {
                // keep user logged in after page refresh
                $rootScope.globals = $cookieStore.get('globals') || {};
                if ($rootScope.globals.currentUser) {
                    $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
                }

//                $rootScope.$on('$locationChangeStart', function (event, next, current) {
//                    // redirect to login page if not logged in
//                    if ($location.path() !== '/login' && !$rootScope.globals.currentUser) {
//                        $location.path('/login');
//                    }
//                });
            }])
		.run(dtLanguageConfig)
        ;
		
	function dtLanguageConfig(DTDefaultOptions) {
    DTDefaultOptions.setLanguageSource('lib/Spanish.json');
}