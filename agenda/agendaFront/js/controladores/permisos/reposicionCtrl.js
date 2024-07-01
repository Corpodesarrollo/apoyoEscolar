/* global angular */

'use strict';

angular.module('AgendaApp.AdminPermisos')
        .controller('reposicionCtrl',
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
                        
                        $scope.dia = {};
                        
                        $scope.horaList=[ {h : "6"},{h : "7"},{h : "8"},{h : "9"},{h : "10"},{h : "11"},{h : "12"},
                                        {h : "13"},{h : "14"},{h : "15"},{h : "16"},{h : "17"},{h : "18"},{h : "19"},{h : "20"}];
                                                
                        $scope.semanaList=[ {nomDia: "Lunes"},{nomDia: "Martes"}, {nomDia: "Miercoles"}, {nomDia: "Jueves"}, {nomDia: "Viernes"}, {nomDia: "Sabado"}];
                        $scope.datosUsuario={};  
                        $scope.datosList = {};
                                                    
                        $scope.obtenerIdUrl = function (){
                            $scope.datosUsuario.numDoc = getUrlVars()["id"];
                            $scope.datosUsuario.nomUsu = getUrlVars()["usu"];
                            var fecha= new Date();
                            $scope.datosUsuario.fecha_solicitud = fecha.getFullYear()+"/"+fecha.getMonth()+"/"+fecha.getDate();
//                            $scope.obtenerDatosUsuario();
                            $scope.tipo1=false;
//                          console.log($scope.fecha_solicitud);
                        };
                        $scope.obtenerIdUrl();                            
                        
                         /*obtener datos de usuario logueado en sistema misional para el formulario*/
                        $scope.obtenerDatosUsuario = function (){
                            console.log("llega obtenerDatosUsuario");
                            $scope.result={};
                            $scope.datosUsuario.numDoc ="10297434" ;
                            $scope.datosUsuario.nomUsu ="cllanten" ;
                            if($scope.datosUsuario.numDoc != ""){
                                    usuarioAgendaSrv.consultarDatos({numDoc: $scope.datosUsuario.numDoc,usuario: $scope.datosUsuario.nomUsu}).$promise.then(function(data){
                                      console.log(data.response);
                                      $scope.result=data.response;
                                      serveData.data.datosUsuario=$scope.result;
                                      $scope.datosUsuario.idUser=$scope.result.ID_USUARIOS;
                                      $scope.datosUsuario.nombre=$scope.result.PRIMER_NOMBRE +" "+ $scope.result.PRIMER_APELLIDO;
                                      console.log($scope.result.ID_USUARIOS);
                                      messageCenterService.add(CONSTANTS.TYPE_SUCCESS,"Datos exitoso",{icon : CONSTANTS.TYPE_SUCCES_ICON,messageIcon : CONSTANTS.TYPE_DANGER_MESSAGE_ICON,timeout : CONSTANTS.TYPE_DANGER_TIME});
                                    }, function(reason){
                                            
                                    });
                            }else{
                                    messageCenterService.add(CONSTANTS.TYPE_DANGER,"Numero documento vacio",{icon : CONSTANTS.TYPE_DANGER_ICON,messageIcon : CONSTANTS.TYPE_DANGER_MESSAGE_ICON,timeout : CONSTANTS.TYPE_DANGER_TIME});
                            }   
                            
                        };
                        
                         /* coloca los datos de usuario en el serverData*/
                        console.log("voy a preguntar si existe serverdata" + serveData.data.datosUsuario);
                        if (serveData.data.datosUsuario) {
                            if (serveData.data.datosUsuario.idUser) {
                                $scope.dia.idForm =serveData.data.$formulario;
                                $scope.datosUsuario = serveData.data.datosUsuario;
                                console.log($scope.idForm);
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
                        
                                              
                         /*guardar dia*/
                        $scope.guardarDias = function (){
                            
                            var target = document.getElementById('divLoadingGeneral');
                            var spinner = new Spinner().spin(target);
                            
                            $scope.dia.idForm = 51;
                            usuarioAgendaSrv.guardarDias({datos: $scope.dia}).$promise.then(function(data){
                                if(data.response){
                                    
                                    messageCenterService.add(CONSTANTS.TYPE_SUCCESS,data.response,{icon : CONSTANTS.TYPE_SUCCES_ICON,messageIcon : CONSTANTS.TYPE_SUCCESS_MESSAGE_ICON,timeout : CONSTANTS.TYPE_SUCCESS_TIME});
                                    $route.reload();
                                
                                }else{
                                    $scope.btnHabilitarPermiso=false;
                                    messageCenterService.add(CONSTANTS.TYPE_DANGER,"Error al guardar",{icon : CONSTANTS.TYPE_DANGER_ICON,messageIcon : CONSTANTS.TYPE_DANGER_MESSAGE_ICON,timeout : CONSTANTS.TYPE_DANGER_TIME});
                                }
                                if (spinner) {
                                    spinner.stop();
                                }
                            }, function(reason){
                                  //error q paso 
                                  if (spinner) {
                                    spinner.stop();
                                }
                                messageCenterService.add(CONSTANTS.TYPE_DANGER,reason.error,{icon : CONSTANTS.TYPE_DANGER_ICON,messageIcon : CONSTANTS.TYPE_DANGER_MESSAGE_ICON,timeout : CONSTANTS.TYPE_DANGER_TIME});
                            });
                            
                        };
                        
                        $scope.cargarDias = function (){
                             $scope.dia.idForm = 51;
                             usuarioAgendaSrv.cargarDias({idForm: $scope.dia.idForm}).$promise.then(function(data){
                                 $scope.datosList=data.response;
                                 
                                 
                             }, function(reason){
                                messageCenterService.add(CONSTANTS.TYPE_DANGER,reason.error,{icon : CONSTANTS.TYPE_DANGER_ICON,messageIcon : CONSTANTS.TYPE_DANGER_MESSAGE_ICON,timeout : CONSTANTS.TYPE_DANGER_TIME});
                            });
                            
                        };
                        $scope.cargarDias();
                       
                    }]);

