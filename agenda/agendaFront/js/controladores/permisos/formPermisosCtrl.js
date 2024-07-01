'use strict';

angular.module('AgendaApp.formularioPermisos')
        .controller('formPermisosCtrl',
                ['$scope',
                    '$route',
                    'messageCenterService',
                    '$location',
                    'constantsFront', '$http', 'serveData', 'usuarioAgendaSrv','cargoSrv',
                    'MotivoSrv',
                    '$q',
                    function ($scope,
                            $route, messageCenterService,
                            $location, CONSTANTS, $http, serveData,usuarioAgendaSrv,cargoSrv,MotivoSrv,$q)
                    {
                        $scope.emailModel = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
                        $scope.letrasModel = /^[óáéíú_a-zA-Z0-9ñÑ_,.-\s]*$/;
                        $scope.direccionModel = /^[_a-zA-Z0-9ñÑ#_,.-\s]*$/;
                        $scope.numModel = /^[0-9]*$/;
                        $scope.telModel = /^[0-9]{7}$/;
                        $scope.celModel = /^[3]{1}[0-3]{1}[0-9]{8}$/;
                       
                        
                        $scope.datosUsuario = {};
                        $scope.datosUsuario.numDoc="";
                        
                        $scope.nuevo = true;
                        $scope.btnEnviar = false;
                        $scope.horas = false;
                        $scope.categoria = "";
                        $scope.pago = "";
                        
                        $scope.disableTipoCargo=true;
                        $scope.requeridoTipoCargo=false;
                        $scope.cargarOtroLic=false;
                        $scope.cargarOtroMot=false;
                        $scope.cargarEstudio=false;
                        $scope.tipo1=false;
                        $scope.tipo2=false;
                        $scope.tipo3=false;
                        $scope.tipo4=false;
                        $scope.tipo5=false;
                        $scope.adjuntar=true;
                        $scope.datosUsuario.id = "";
                        $scope.datosUsuario.fecha_solicitud = "";
                        $scope.cargoList={};
                        //tipo3
                        $scope.mostrarFechaVacaciones=false;
                        $scope.mostrarResolucion=false;
                        $scope.adjuntarDias=false;
                        //Cambios mcuellar
                        $scope.inputCheckFondoP=false;
                        $scope.checkboxModel={};
                        
                        $scope.tipoSolicitudList=[ {id : "1", ds : "1. Solicitud de permiso o ausencia de servidoras(es) públicos"}, 
                                                    {id : "2", ds : "2. Traslado en seguridad social y cesantías"}, 
                                                    {id : "3", ds : "3. Vacaciones"},
                                                    {id : "4", ds : "4. Prima técnica"},
                                                    {id : "5", ds : "5. Licencia no remunerada o licencia por luto"}];
                        $scope.tipoLicenciaList=[ {id : "1", ds : "1. Licencia no remunerada"}, 
                                                    {id : "2", ds : "2. Licencia por luto"}];
                        $scope.motPermisoList=[ {ID_MOTIVO : "1", DESC_MOTIVO : "1. Cita Medica"}, 
                                                    {ID_MOTIVO : "2", DESC_MOTIVO : "2. Dia de autocuidado"},
                                                    {ID_MOTIVO : "3", DESC_MOTIVO : "3. Estudio"},
                                                    {ID_MOTIVO : "4", DESC_MOTIVO : "4. Votación"},
                                                    {ID_MOTIVO : "5", DESC_MOTIVO : "5. Otro Motivo"}];
                        $scope.horaList=[ {h : "6"},{h : "7"},{h : "8"},{h : "9"},{h : "10"},{h : "11"},{h : "12"},
                                        {h : "13"},{h : "14"},{h : "15"},{h : "16"},{h : "17"},{h : "18"},{h : "19"},{h : "20"}];
                                                
                                                    
                        $scope.obtenerIdUrl = function (){
                            $scope.datosUsuario.numDoc = getUrlVars()["id"];
                            $scope.datosUsuario.nomUsu = getUrlVars()["usu"];
                            console.log($scope.datosUsuario.numDoc);
                            console.log($scope.datosUsuario.nomUsu);
//                            $scope.obtenerDatosUsuario();
                            $scope.tipo1=false;
//                          console.log($scope.fecha_solicitud);
                        };
                        $scope.obtenerIdUrl();                            
                        
//                        traer motivos licencia o permiso
                        $scope.getMotivo = function (){
                            MotivoSrv.query({cat: $scope.categoria}).$promise.then(function(data){
                                      console.log("MOTIVO"+data.response);
                                      $scope.motPermisoList=data.response;
                                    }, function(reason){
                                            
                            });
                        };
                        
                        $scope.cargarCargo = function (){
                            cargoSrv.query().$promise.then(function(data){
                                      console.log(data.response);
                                      $scope.cargoList=data.response;
                                    }, function(reason){
                                            
                            });
                        };
                        
                        /*obtener datos de usuario para el formulario*/
                        $scope.obtenerDatosUsuario = function (){
                             var target = document.getElementById('divLoadingGeneral');
                            var spinner = new Spinner().spin(target);
                            
                            console.log("llega obtenerDatosUsuario");
                            var fecha= new Date();
                            $scope.result={};
                            $scope.datosUsuario.numDoc ="10297434" ;
                            $scope.datosUsuario.nomUsu ="cllanten" ;
                            if($scope.datosUsuario.numDoc != ""){
                                    usuarioAgendaSrv.consultarDatos({numDoc: $scope.datosUsuario.numDoc,usuario: $scope.datosUsuario.nomUsu}).$promise.then(function(data){
                                      console.log(data.response);
                                      $scope.result=data.response;
                                      serveData.data.datosUsuario=$scope.result;
                                      $scope.datosUsuario=data.response;
                                      $scope.datosUsuario.idUser=$scope.result.ID_USUARIOS;
                                      $scope.datosUsuario.nombre=$scope.result.PRIMER_NOMBRE +" "+ $scope.result.PRIMER_APELLIDO;
                                      $scope.datosUsuario.fecha_solicitud = fecha.getFullYear()+"/"+(fecha.getMonth()+1)+"/"+fecha.getDate();
                                      messageCenterService.add(CONSTANTS.TYPE_SUCCESS,"Datos exitoso",{icon : CONSTANTS.TYPE_SUCCES_ICON,messageIcon : CONSTANTS.TYPE_SUCCESS_MESSAGE_ICON,timeout : CONSTANTS.TYPE_DANGER_TIME});
                                    }, function(reason){
                                            
                                    });
                            }else{
                                    messageCenterService.add(CONSTANTS.TYPE_DANGER,"Numero documento vacio",{icon : CONSTANTS.TYPE_DANGER_ICON,messageIcon : CONSTANTS.TYPE_DANGER_MESSAGE_ICON,timeout : CONSTANTS.TYPE_DANGER_TIME});
                            }   
                            $scope.cargarCargo();
                            if (spinner) {
                                    spinner.stop();
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
                        
                        $scope.validarDiasHoras = function (){
                            
                            if($scope.datosUsuario.fInicio){
                                var fechaSolPer = new Date($scope.datosUsuario.fInicio);
                                fechaSolPer.setHours(0, 0, 0, 0);
                                var fechaHabil = new Date();
                                fechaHabil.setHours(0, 0, 0, 0);
                                fechaHabil.setDate(fechaHabil.getDate()+2);
                                /*validar tres dias de anterioridad debo validar que tenga bandera activa para permiso normal*/
                                
                                if(fechaHabil<fechaSolPer || $scope.datosUsuario.BND=="1"){
                                    if($scope.datosUsuario.tipoPermiso == "horas"){
                                        console.log("horas");
                                        $scope.horas = true;
                                        $scope.datosUsuario.fHasta=$scope.datosUsuario.fInicio;
                                        $scope.datosUsuario.strfHasta = fechaSolPer.getFullYear()+"/"+(fechaSolPer.getMonth()+1)+"/"+fechaSolPer.getDate();
                                        $scope.datosUsuario.minFin=$scope.datosUsuario.minIni;
                                        $scope.datosUsuario.horaFin=parseInt($scope.datosUsuario.horaIni)+($scope.datosUsuario.cantidad);
                                        $scope.calHoraFinal=$scope.datosUsuario.horaFin+":"+$scope.datosUsuario.minFin;
                                    } 

                                    if($scope.datosUsuario.tipoPermiso == "dias"){
                                       console.log("dias");
                                       fechaSolPer.setHours(0, 0, 0, 0);
                                       if($scope.datosUsuario.cantidad==1){
                                        $scope.horas = false;
                                        $scope.datosUsuario.strfHasta = fechaSolPer.getFullYear()+"/"+(fechaSolPer.getMonth()+1)+"/"+fechaSolPer.getDate();
                                        $scope.datosUsuario.fHasta=$scope.datosUsuario.fInicio;
                                        }else{
                                            $scope.horas = false;
                                            var cant = $scope.datosUsuario.cantidad-1;
                                            fechaSolPer.setDate(fechaSolPer.getDate()+cant);
                                            $scope.datosUsuario.strfHasta = fechaSolPer.getFullYear()+"/"+(fechaSolPer.getMonth()+1)+"/"+fechaSolPer.getDate();
                                            $scope.datosUsuario.fHasta=fechaSolPer;
                                        } 
                                    }
                                }else{
                                  /* no puede solicitar permiso bandera excencion por fecha consultar de una vez las banderas
                                   * cuando me selecciones el tipo de permiso debo avisarle que no puede pedir permisos o por 
                                   * falta de anexar docuemntos BE*/
                                  var con = confirm("Debe solicitar un permiso con tres dias de anterioridad, por favor contactarse con la administradora");
                                    $scope.datosUsuario.strfHasta = "";
                                    $scope.datosUsuario.fInicio="";
                                    $scope.datosUsuario.fHasta="";
                                  if (con == true) {
                                        alert("true");
                                    } else {
                                        alert("true");
                                    }
                                }
                            }
                        };
                        
                        $scope.validarOtroMotivo = function (){
                            console.log("entra a cargar otro motivo");
                            console.log($scope.datosUsuario.motPermiso);
                            
                            $scope.cargarOtroMot = false;
                            switch ($scope.datosUsuario.motPermiso){
                                case "1"://Cita Medica
                                    $scope.adjuntar=false;
                                    $scope.cargarEstudio=false;
                                    break;
                                case "2"://dia de autocuidado
                                    $scope.adjuntar=false;
                                    $scope.cargarEstudio=false;
                                    break;
                                case "3"://estudio
                                    $scope.adjuntar=true;
                                    $scope.cargarEstudio=true;
                                    break;
                                case "4"://votacion
                                    $scope.adjuntar=true;
                                    $scope.cargarEstudio=false;
                                    break;
                                case "5"://otro
                                    $scope.adjuntar=true;
                                    $scope.cargarOtroMot = true;
                                    $scope.cargarEstudio=false;
                                    break;
                            }
                        };
                        
                        $scope.cargarCampos = function (){
                            $scope.datosUsuario.cantidad="";
                            $scope.datosUsuario.fInicio="";
                            $scope.datosUsuario.strfHasta="";
                            $scope.datosUsuario.fHasta="";
                            $scope.datosUsuario.minIni="";
                            $scope.datosUsuario.minFin="";
                            $scope.datosUsuario.horaIni="";
                            $scope.datosUsuario.horaFin="";
                            switch ($scope.datosUsuario.tipoSolicitud){
                                case "1":
                                    $scope.tipo1=true;
                                    $scope.tipo2=false;
                                    $scope.tipo3=false;
                                    $scope.tipo4=false;
                                    $scope.tipo5=false;
                                    $scope.categoria=1;
                                    $scope.getMotivo();
                                    $scope.datosUsuario.dsTipoSolicitud="1. Solicitud de permiso o ausencia de servidoras(es) públicos";
                                    break;
                                case "2":
                                    $scope.tipo1=false;
                                    $scope.tipo2=true;
                                    $scope.tipo3=false;
                                    $scope.tipo4=false;
                                    $scope.tipo5=false;
                                    $scope.datosUsuario.dsTipoSolicitud="2. Traslado en seguridad social y cesantías";
                                  break;
                                case "3":
                                    $scope.tipo1=false;
                                    $scope.tipo2=false;
                                    $scope.tipo3=true;
                                    $scope.tipo4=false;
                                    $scope.tipo5=false;
                                    $scope.datosUsuario.dsTipoSolicitud="3. Vacaciones";
                                    break;
                                case "4":
                                    $scope.tipo1=false;
                                    $scope.tipo2=false;
                                    $scope.tipo3=false;
                                    $scope.tipo4=true;
                                    $scope.tipo5=false;
                                    $scope.datosUsuario.dsTipoSolicitud="4. Prima técnica";
                                      break;
                                case "5":
                                    $scope.tipo1=false;
                                    $scope.tipo2=false;
                                    $scope.tipo3=false;
                                    $scope.tipo4=false;
                                    $scope.tipo5=true;
                                    $scope.categoria=5;
                                    $scope.getMotivo();
                                    $scope.datosUsuario.dsTipoSolicitud="5. Licencia no remunerada o licencia por luto";
                                    break;
                            }
                        };
                       
                        $scope.guardar = function (){
                            console.log($scope.datosUsuario);
                             var target = document.getElementById('divLoadingGeneral');
                            var spinner = new Spinner().spin(target);
                            
                            var guardar = false;
                            var formData = new FormData();
                            var file;
                            if($scope.datosUsuario){                                
                                if($scope.datosUsuario.tipoSolicitud == 2){
                                    guardar = validarTipo2FormularioPermisos();
                                }
                                if($scope.datosUsuario.tipoSolicitud == 4){
                                    guardar = validarTipo4FormularioPermisos();
                                }
                                if($scope.datosUsuario.tipoSolicitud == 5){
                                    file = $scope.datosUsuario.fileLicencia;
                                }else if($scope.datosUsuario.tipoSolicitud == 3){
                                    file = $scope.datosUsuario.fileVacaciones;
                                    }else{
                                        file = $scope.file;
                                    }
                                if($scope.datosUsuario.tipoSolicitud != 2 && $scope.datosUsuario.tipoSolicitud != 4){                                    
                                    var deferred = $q.defer();                                    
                                    formData.append("file", file);                                    
                                    guardar=validarArchivo(file);
                                }
                            }                                                  
                            if(guardar == true){
                                  formData.append("data",JSON.stringify($scope.datosUsuario));
//                                usuarioAgendaSrv.guardarFormulario({data: $scope.datosUsuario,file:formData}).$promise.then(function(data){
                                   usuarioAgendaSrv.guardarFormulario(formData).$promise.then(function(data){
                                    messageCenterService.add(CONSTANTS.TYPE_SUCCESS,data.response,{icon : CONSTANTS.TYPE_SUCCES_ICON,messageIcon : CONSTANTS.TYPE_SUCCESS_MESSAGE_ICON,timeout : CONSTANTS.TYPE_DANGER_TIME});
                                    $scope.formulario=data.formulario;
                                    if($scope.datosUsuario.motPermiso==3){
                                        //guardar dias de reposicion
                                        $scope.adicionarReposicion();
                                    }
                                    
                                    if (spinner) {
                                    spinner.stop();
                                    }
                                   
                                 }, function(reason){
                                     if (spinner) {
                                    spinner.stop();
                                    }
                                    messageCenterService.add(CONSTANTS.TYPE_DANGER,reason.response,{icon : CONSTANTS.TYPE_DANGER_ICON,messageIcon : CONSTANTS.TYPE_DANGER_MESSAGE_ICON,timeout : CONSTANTS.TYPE_DANGER_TIME});
                                });
                            }
                            
                        };
                        
                        function validarArchivo(file) {                            
                            if($scope.adjuntar==true){
                                console.log( file);
                                if(file.type=="application/pdf" || file.type=="image/jpeg"){
                                    if(file.size<3000000){
                                         $scope.datosUsuario.file=file;
                                          return true;
                                    }else{
                                        alert("El tamaño del adjunto debe ser inferior a 3MB");
                                        return false;
                                    }
                                }else{
                                    alert("solo es permitido archivos PDF o JPG");
                                    return false;
                                }
                            }else{
                                console.log("adjuntar no es obligatorio");
                                return true;
                            }
                        }
                        
                        function validarTipo2FormularioPermisos(){
                            if(!$scope.checkboxModel.valuePension && !$scope.checkboxModel.valueEps && !$scope.checkboxModel.valueCesantias){
                                messageCenterService.add(CONSTANTS.TYPE_DANGER,"Debe seleccionar una opción para el traslado",{icon : CONSTANTS.TYPE_DANGER_ICON,messageIcon : CONSTANTS.TYPE_DANGER_MESSAGE_ICON,timeout : CONSTANTS.TYPE_DANGER_TIME});
                                return false;
                            }                            
                            return true;
                        }
                        
                        function validarTipo4FormularioPermisos(){
                            if(!$scope.datosUsuario.checkReconocimiento){
                                $scope.datosUsuario.checkReconocimiento=0;
                            }else{
                                $scope.datosUsuario.checkReconocimiento=1;
                            }
                            if(!$scope.datosUsuario.checkReajuste){
                                $scope.datosUsuario.checkReajuste=0;
                            }else{
                                $scope.datosUsuario.checkReajuste=1;
                            }
                            if(!$scope.datosUsuario.checkEstudios){
                                $scope.datosUsuario.checkEstudios=0;
                            }else{
                                $scope.datosUsuario.checkEstudios=1;
                            }
                            if(!$scope.datosUsuario.checkExperiencia){
                                $scope.datosUsuario.checkExperiencia=0;
                            }else{
                                $scope.datosUsuario.checkExperiencia=1;
                            }
                            return true;
                        }
                        
                        $scope.validarVacaciones= function(){
                            console.log("validar vacaciones"+$scope.datosUsuario.tipoVacaciones);
                            if($scope.datosUsuario.tipoVacaciones==0){
                                $scope.mostrarFechaVacaciones=true;
                                $scope.mostrarResolucion=false;
                            }else{
                                $scope.mostrarFechaVacaciones=false;
                                $scope.mostrarResolucion=true;
                            }
                            
                        };
                        
                        $scope.adicionarReposicion = function () {
                            
                            serveData.data.formulario = $scope.formulario;
                            $location.path('/adicionarDias');
                        };
                        
                    }])
                
        .directive('uploaderModel', ["$parse", function ($parse) {
                return {
                        restrict: 'A',
                        link: function (scope, iElement, iAttrs) 
                        {
                                iElement.on("change", function(e)
                                {
                                        $parse(iAttrs.uploaderModel).assign(scope, iElement[0].files[0]);
                                });
                        }
                };
        }])  
        
        .directive('fileUpload', function () {
            return {
                scope: true,        //create a new scope
                link: function (scope, el, attrs) {
                    el.bind('change', function (event) {
                        var files = event.target.files;
                        //iterate files since 'multiple' may be specified on the element
                        for (var i = 0;i<files.length;i++) {
                            //emit event upward
                            scope.$emit("fileSelected", { file: files[i] });
                        }                                       
                    });
                }
            };
        })
;

