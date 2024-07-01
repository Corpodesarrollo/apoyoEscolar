angular.module("AgendaApp", ['ngRoute', 'ngResource',
    'AgendaApp.seguridad',
    'AgendaApp.PermisoSrv',
    'AgendaApp.AdminPermisos',
    'MessageCenterModule','datatables',
    'ngCookies', 'pagination.controllers', 
    'angularUtils.directives.dirPagination',
    'AgendaApp.modal',
    'ui.bootstrap',
    'AgendaApp.formularioPermisos',
    'angularjs-datetime-picker',
    'AgendaApp.administracion'
]);

/* Modulos */
angular.module('pagination.controllers', []);
angular.module('AgendaApp.seguridad', []);
angular.module('AgendaApp.PermisoSrv', []);
angular.module('AgendaApp.AdminPermisos', []);
angular.module('angularUtils.directives.dirPagination', []);
angular.module('AgendaApp.modal', []);
angular.module('AgendaApp.formularioPermisos', []);
angular.module('AgendaApp.administracion', []);