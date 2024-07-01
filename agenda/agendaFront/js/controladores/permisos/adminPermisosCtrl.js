/* global angular */

'use strict';

angular.module('AgendaApp.AdminPermisos')
        .controller('adminPermisosCtrl',
                ['$scope',
                    '$route',
                    'messageCenterService',
                    '$location',
                    'constantsFront', '$http', 'serveData', 'usuarioAgendaSrv','funcionariaSrv',
                    function ($scope,
                            $route, messageCenterService,
                            $location, CONSTANTS, $http, serveData,usuarioAgendaSrv,funcionariaSrv)
                    {
                        $scope.datosFunCiom = {};
                        $scope.datosFunCiom.numDoc = "";
                        
                        $scope.emailModel = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
                        $scope.letrasModel = /^[_a-zA-Z0-9ñÑ_,.-\s]*$/;
                        $scope.direccionModel = /^[_a-zA-Z0-9ñÑ#_,.-\s]*$/;
                        $scope.numModel = /^[0-9]*$/;
                        $scope.telModel = /^[0-9]{7}$/;
                        $scope.celModel = /^[3]{1}[0-3]{1}[0-9]{8}$/;
                        $scope.btnHabilitarPermiso=true;
                        
                        $scope.datosUsuario = {};
                        $scope.datosUsuario.numDoc="";
                                               
                        $scope.tipo1=false;
                        $scope.tipo2=false;
                        
                        $scope.tipoSolicitudList=[ {id : "1", ds : "1. Habilitar solicitud de permiso prioritario"}, 
                                                    {id : "2", ds : "2. Ver y/o aprobar solicitudes de permiso"} 
                                                 ];
                                          
                                                    
                        $scope.obtenerIdUrl = function (){
                            $scope.datosUsuario.numDoc = getUrlVars()["id"];
                            $scope.datosUsuario.nomUsu = getUrlVars()["usu"];
                            console.log($scope.datosUsuario.numDoc);
                            console.log($scope.datosUsuario.nomUsu);
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
                        
                        /*buscar funcionario(a) CIOM*/
                        $scope.buscarFuncionarioCiom = function (){
                            $scope.btnBuscarFunc=true;
                            console.log("llega opbtener prioritario");
                            console.log($scope.datosFunCiom.numDoc);
                            if($scope.datosFunCiom.numDoc != "" ){
                                funcionariaSrv.query({numDoc: $scope.datosFunCiom.numDoc}).$promise.then(function(data){
                                console.log(data.response);
                                    if(data.response){
                                       $scope.btnHabilitarPermiso=false;
                                       $scope.mostrarResultado=true;
                                       $scope.result=data.response;
                                       $scope.datosFunCiom=$scope.result;
                                       $scope.datosFunCiom.nombreCompleto=$scope.datosFunCiom.PRIMER_NOMBRE+" "+$scope.datosFunCiom.PRIMER_APELLIDO;
                                       $scope.datosFunCiom.cargoCompleto=$scope.datosFunCiom.CARGO+" "+$scope.datosFunCiom.CARGO_ESPEC;
                                       $scope.datosFunCiom.numDoc=$scope.datosFunCiom.ID_USUARIOS;
                                       console.log($scope.nombreCompleto);
                                       messageCenterService.add(CONSTANTS.TYPE_SUCCESS,"Funcionario(a) CIOM encontrado(a)",{icon : CONSTANTS.TYPE_SUCCES_ICON,messageIcon : CONSTANTS.TYPE_SUCCESS_MESSAGE_ICON,timeout : CONSTANTS.TYPE_SUCCESS_TIME});
                                    }else{
                                       console.log("entrando");
                                        $scope.mostrarResultado=false;
                                       messageCenterService.add(CONSTANTS.TYPE_DANGER,data.error,{icon : CONSTANTS.TYPE_DANGER_ICON,messageIcon : CONSTANTS.TYPE_DANGER_MESSAGE_ICON,timeout : CONSTANTS.TYPE_DANGER_TIME});
                                    }
                                    $scope.btnBuscarFunc=false;  
                                      
                                }, function(reason){
                                    $scope.btnBuscarFunc=false; 
                                    $scope.mostrarResultado=false;
                                    console.log(reason);
                                    messageCenterService.add(CONSTANTS.TYPE_DANGER,"Funcionario(a) CIOM no encontrado(a)",{icon : CONSTANTS.TYPE_DANGER_ICON,messageIcon : CONSTANTS.TYPE_DANGER_MESSAGE_ICON,timeout : CONSTANTS.TYPE_DANGER_TIME});
                                              
                                });
                            }else{
                                $scope.btnBuscarFunc=false; 
                                messageCenterService.add(CONSTANTS.TYPE_DANGER,"Campo requerido",{icon : CONSTANTS.TYPE_DANGER_ICON,messageIcon : CONSTANTS.TYPE_DANGER_MESSAGE_ICON,timeout : CONSTANTS.TYPE_DANGER_TIME});
                            }   
                        };
                        
                         /*habilitar permiso prioritario (extemporaneo) funcionario(a) CIOM*/
                        $scope.habilitarFuncionarioCiom = function (){
                            var target = document.getElementById('divLoadingGeneral');
                            var spinner = new Spinner().spin(target);
                            $scope.btnHabilitarPermiso=true;
                            console.log("llega habilitar");
                            $scope.datos={};
                            $scope.datos.accion="autorizar";
                            $scope.datos.ID_USUARIOS= $scope.datosFunCiom.ID_USUARIOS;
                            funcionariaSrv.update({datos: $scope.datos}).$promise.then(function(data){
                                if(data.response){
                                    $scope.datosFunCiom={};
                                    $scope.btnBuscarFunc=false;
                                    $scope.btnHabilitarPermiso=false;
                                    $scope.mostrarResultado=false;
                                    messageCenterService.add(CONSTANTS.TYPE_SUCCESS,"Funcionario(a) autorizada",{icon : CONSTANTS.TYPE_SUCCES_ICON,messageIcon : CONSTANTS.TYPE_SUCCESS_MESSAGE_ICON,timeout : CONSTANTS.TYPE_SUCCESS_TIME});
                                }else{
                                    $scope.btnHabilitarPermiso=false;
                                    messageCenterService.add(CONSTANTS.TYPE_DANGER,"Funcionario(a) no autorizado, consulte al administrador",{icon : CONSTANTS.TYPE_DANGER_ICON,messageIcon : CONSTANTS.TYPE_DANGER_MESSAGE_ICON,timeout : CONSTANTS.TYPE_DANGER_TIME});
                                }
                                if (spinner) {
                                    spinner.stop();
                                }
                            }, function(reason){
                                  //error q paso   
                                  if (spinner) {
                                    spinner.stop();
                                }
                            });
                            
                        };
                        
                        $scope.cargarCampos = function (){
                            switch ($scope.datosUsuario.tipoSolicitud){
                                case "1":
                                    $scope.tipo1=true;
                                    $scope.tipo2=false;
                                    $scope.datosUsuario.dsTipoSolicitud="1. Habilitar solicitud de permiso prioritario";
                                    break;
                                case "2":
                                    $scope.tipo1=false;
                                    $scope.tipo2=true;
                                    $scope.datosUsuario.dsTipoSolicitud="2. Ver y/o aprobar solicitudes de permiso";
                                  break;
                                
                            }
                        };
                       
                    }]);

