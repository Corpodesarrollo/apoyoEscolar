<?php

defined('BASEPATH') OR exit('No direct script access allowed');

$route['default_controller'] = 'PermisosRest';
$route['404_override'] = '';
$route['translate_uri_dashes'] = FALSE;

//Rutas Permisos
//$route['buscarUsuario']['get'] = "formularioPermisos/PermisosRest/index"; 
$route['obtenerCargos']['get'] = "formularioPermisos/PermisosRest/find_cargo";
$route['obtenerMotivos']['get'] = "formularioPermisos/PermisosRest/find_motivo";
$route['usuario/buscarUsuario/(:any)/nombre/(:any)']['get'] = "formularioPermisos/PermisosRest/index/$1/$2";
$route['formulario/guardarFormulario']['post'] = "formularioPermisos/PermisosRest/index";

$route['obtenerFuncionaria/numDoc/(:num)']['get'] = "formularioPermisos/PermisosRest/consultarFuncionaria/$1";
$route['funcionaria']['post'] = "formularioPermisos/PermisosRest/actualizarFuncionaria";

$route['formulario/adjuntar']['post'] = "formularioPermisos/PermisosRest/adjuntar";
$route['formulario/guardarDias']['post'] = "formularioPermisos/PermisosRest/guardar_dias";
$route['formulario/buscarDias/idForm/(:any)']['get'] = "formularioPermisos/PermisosRest/buscarDiasFormulario/$1";

$route['formulario/validarFechas/(:any)/(:any)/(:any)']['get'] = "formularioPermisos/PermisosRest/pendiente";
$route['uploadFilePermisos']['post'] = "formularioPermisos/PermisosRest/cargar";

//Rutas Empresa
$route['empresa']['get'] = "solicitud/EmpresaRest/index";
$route['empresa/(:num)']['get'] = "solicitud/EmpresaRest/find/$1";
$route['empresa']['post'] = "solicitud/EmpresaRest/index";
$route['empresa/(:num)']['put'] = "solicitud/EmpresaRest/index/$1";
$route['empresa/(:num)']['delete'] = "solicitud/EmpresaRest/index/$1";
$route['sucursal']['get'] = "solicitud/EmpresaRest/find_sucursal";
$route['cargarDatos']['get'] = "solicitud/EmpresaRest/findDatos";