'use strict';

angular.module('pagination.controllers').
        controller('PaginationAngelusController', ['$scope', '$window', 'constantsFront', 'messageCenterService','$rootScope','$location', 
                                                   function ($scope, $window, constantsFront, messageCenterService,$rootScope,$location) {
                
                $scope.datos = [];
                $scope.totalDatos = 0;
                $scope.datosPerPage = constantsFront.REGISTROS_POR_PAGINA;
                $scope.mensajeDatos = '';
                $scope.pageNumerGlobal = null;
                var tokenP = null;
                //console.log($window.sessionStorage.getItem('token'));
				
                $scope.pagination = {
                    current: 1
                };

                if(!$rootScope.globals || !$rootScope.globals.currentUser || !$window.sessionStorage.getItem('token')){
            		$location.path('/login');
            	}else{
            		//tokenP = $rootScope.globals.currentUser.token;
					tokenP = $window.sessionStorage.getItem('token');
            	}
                
				$scope.pageChanged = function (pageNumber) {console.log(pageNumber)}
				
                $scope.initDatos = function (pageNumber) {
                    $scope.pageNumerGlobal = pageNumber;
                    $scope.queryFunction.query({page: pageNumber, regPagina: constantsFront.REGISTROS_POR_PAGINA, token: tokenP, searchVO: $scope.searchVO}, function (response) {
                        if (response) {
                        	$scope.datos = response;
							$scope.totalDatos = response.length;
                        } else {
                        	console.log("else" +response);
                        }
                    });
                };

                $scope.initDatos(1);

                $scope.buscar = function () {
                    $scope.queryFunction({page: 1, regPagina: constantsFront.REGISTROS_POR_PAGINA, searchVO: $scope.searchVO}).$promise
                            .then(function (result) {
                                $scope.datos = result.response;
                                $scope.totalDatos = result.Count;
                                $scope.mensajeDatos = result.mensaje;
                                $scope.pagination.current = 1;
                                if ($scope.totalDatos == 0) {
                                    messageCenterService.add(constantsFront.TYPE_INFO, constantsFront.ERROR_NO_HAY_REGISTROS, {icon: constantsFront.TYPE_INFO_ICON, messageIcon: constantsFront.TYPE_INFO_MESSAGE_ICON, timeout: constantsFront.TYPE_INFO_TIME_TC});
                                }
                            });
                };
            }]);
