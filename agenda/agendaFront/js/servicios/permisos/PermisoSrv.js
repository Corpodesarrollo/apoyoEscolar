'use strict';
//var urlParametrosModule = 'http://localhost:80/agenda/agendaBE/';
var urlParametrosModule = urlBackEnd;

angular.module('AgendaApp.PermisoSrv')
        .factory("serveData", function () {
            return {
                data: {}
            };
        })
        .factory('usuarioAgendaSrv', [ '$resource', '$rootScope',
                function($resource, $rootScope) {
                    return $resource(urlParametrosModule
                                    + 'buscarUsuario/:idUsuario', {
                            idUsuario: '@idUsuario'
                    }, {
                            update : {
                                    method : 'PUT'
                            },
                            query : {
                                    method : 'GET',
                                    isArray : false
                            },
                            consultarDatos:{
                                url : urlParametrosModule + 'usuario/buscarUsuario/:numDoc/nombre/:usuario',
                                method : "GET",
                                isArray : false
                            },
                            cargarDias:{
                                url : urlParametrosModule + 'formulario/buscarDias/idForm/:idForm',
                                method : "GET",
                                isArray : false
                            },
                            guardarDias:{
                                url : urlParametrosModule + 'formulario/guardarDias',
                                method : "POST",
                                isArray : false
                            },
                            guardarFormulario:{
                                url : urlParametrosModule + 'formulario/guardarFormulario',
                                headers: {  "Content-type": undefined },
                                method : "POST",
                                isArray : false,
                                transformRequest: angular.identity
                            },
                            fileUpload:{
                                url : urlParametrosModule + 'formulario/adjuntar',
                                headers: {  "Content-type": undefined },
                                method : "POST",
                                isArray : false,
                                transformRequest: angular.identity
                            }
                    });
	} ])
        .factory('cargoSrv', [ '$resource', '$rootScope',
                function($resource, $rootScope) {
                    return $resource(urlParametrosModule
                                    + 'obtenerCargos/:idUsuario', {
                            idUsuario: '@idUsuario'
                    }, {
                            update : {
                                    method : 'PUT'
                            },
                            query : {
                                    method : 'GET',
                                    isArray : false
                            }
                    });
	} ])
        .factory('MotivoSrv', [ '$resource', '$rootScope',
                function($resource, $rootScope) {
                    return $resource(urlParametrosModule
                                    + 'obtenerMotivos/:idUsuario', {
                            idUsuario: '@idUsuario'
                    }, {
                            update : {
                                    method : 'PUT'
                            },
                            query : {
                                    method : 'GET',
                                    isArray : false
                            }
                    });
	} ])
        
        .factory('formUgppSrv',
                [ '$resource',
                    function ($resource) {
                        var formUgppSrv = $resource(
                                urlParametrosModule + 'solicitud/:path',
                                {
                                    path: '@path'
                                },
                                {
                                    update: {
                                        method: 'PUT'
                                    },
                                    query: {
                                        method: 'GET',
                                        isArray: false
                                    }
                                });
                        return {
                            validar: formUgppSrv.bind({path: 'validar'}).get,
                            consultar: formUgppSrv.bind({path: 'consultar'}).get,
                            subir: formUgppSrv.bind({path: 'subir'}).get,
                            eliminar: formUgppSrv.bind({path: 'eliminar'}).get,
                            verLog: formUgppSrv.bind({path: 'verLog'}).get,
                            avance: formUgppSrv.bind({path: 'avance'}).get,
                            query: formUgppSrv.query
                        };
                    }
                ])
        ;
