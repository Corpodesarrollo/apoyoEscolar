'use strict';

angular.module('SDMujerApp.formularioPermisos')
        .controller('solicitudesXusuarioCtrl',
                ['$scope',
                    '$route',
                    'messageCenterService',
                    '$location',
                    'constantsFront', '$http', 'serveData', 'usuarioAgendaSrv',
                    function ($scope,
                            $route, messageCenterService,
                            $location, CONSTANTS, $http, serveData,usuarioAgendaSrv)
                    {
                        $scope.emailModel = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
                        $scope.letrasModel = /^[_a-zA-Z0-9ñÑ_,.-\s]*$/;
                        $scope.direccionModel = /^[_a-zA-Z0-9ñÑ#_,.-\s]*$/;
                        $scope.numModel = /^[0-9]*$/;
                        $scope.telModel = /^[0-9]{7}$/;
                        $scope.celModel = /^[3]{1}[0-3]{1}[0-9]{8}$/;
                       
                        
                        $scope.datosUsuario = {};
                                                    
                        $scope.obtenerIdUrl = function (){
                            $scope.datosUsuario.numDoc = getUrlVars()["id"];
                            console.log($scope.datosUsuario.id);
                            var fecha= new Date();
                            $scope.tipo1=false;
                        };
                        $scope.obtenerIdUrl();                            
                                                    
                        $scope.obtenerDatosUsuario = function (){
                            console.log("llega obtenerDatosUsuario segunda pag");
                            $scope.result={};
                            if($scope.datosUsuario.numDoc != ""){
                                    usuarioAgendaSrv.query({numDoc: $scope.datosUsuario.numDoc}).$promise.then(function(data){
                                      console.log(data.response[0]);
                                      $scope.result=data.response[0];
                                      serveData.data.datosUsuario=$scope.result;
                                      $scope.datosUsuario.idUser=$scope.result.ID_USUARIOS;
                                      $scope.datosUsuario.nombre=$scope.result.PRIMER_NOMBRE +" "+ $scope.result.PRIMER_APELLIDO;
                                       console.log($scope.result.ID_USUARIOS);
                                    }, function(reason){
                                            
                                    });
                            }else{
                                    messageCenterService.add(CONSTANTS.TYPE_DANGER,"Debe seleccionar criterios de busqueda",{icon : CONSTANTS.TYPE_DANGER_ICON,messageIcon : CONSTANTS.TYPE_DANGER_MESSAGE_ICON,timeout : CONSTANTS.TYPE_DANGER_TIME});
                            }   
                        };
                             
                       /* coloca los datos de usuario en el serverData*/
                        console.log("voy a preguntar si existe serverdata" + serveData.data.datosUsuario);
                        if (serveData.data.datosUsuario) {
                            if (serveData.data.datosUsuario.idUser) {
                                $scope.datosUsuario = serveData.data.datosUsuario;
                                console.log("datos consulta server: " + $scope.datosUsuario.idUser);
                            }
                        }else{
                            $scope.obtenerDatosUsuario();
                        }
                        
                         /* obtiene el numero de identificacion de la url*/
                        function getUrlVars()
                        {
                            var vars = [], hash;
                            var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
                            for(var i = 0; i < hashes.length; i++)
                            {
                                hash = hashes[i].split('=');
                                vars.push(hash[0]);
                                vars[hash[0]] = hash[1];
                            }
                            return vars;
                        }
                        

                    }]);

