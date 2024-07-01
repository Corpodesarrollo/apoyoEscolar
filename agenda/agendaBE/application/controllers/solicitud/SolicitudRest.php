<?php

if (!defined('BASEPATH'))
    exit('No direct script access allowed');
    
require APPPATH . "/libraries/REST_Controller.php";    

class SolicitudRest extends REST_Controller {
	
	function __construct() {
        parent::__construct();
        $this->load->model("/SolicitudFachada");
    }
    
    public function index_get() {
        log_message('info', 'Ingreso Solicitud - index_get', false);
        $pag = $this->input->get('page');
        $regPagina = $this->input->get('regPagina');
        $filtros = NULL;
        if ($this->input->get('searchVO')) {
            $filtros = json_decode((string) $this->input->get('searchVO'));
        }
        if (!$pag) {
            $pag = 1;
        }
        if (!$regPagina) {
            $regPagina = 10;
        }
        $start = ($regPagina * $pag) - $regPagina;
        $resultado = $this->SolicitudFachada->SolicitudGetAll($regPagina, $start, $filtros);
        log_message('info', 'Salida Solicitud - index_get', false);
        if (!is_null($resultado)) {
            $this->response($resultado, 200);
        } else {
            $this->response(array("error" => "No hay Solicitud"), 215);
        }
    }
    public function findValor_get($cat) {
//        $cat = $this->input->get('categoria');
        log_message('info', 'Ingreso buscar operadores - find_get', false);
        $resultado = $this->SolicitudFachada->consultarValor($cat);
        log_message('info', 'Salida valor - find_get', false);
        if (!is_null($resultado)) {
            $this->response(array("response" => $resultado), 200);
        } else {
            $this->response(array("error" => "No se encuentra recurso"), 404);
        }
    }
    
    public function findCaja_get() {
//        var_dump("llega a find_get  "+$id);
        log_message('info', 'Ingreso buscar operadores - find_get', false);
        $resultado = $this->SolicitudFachada->consultarCaja();
        log_message('info', 'Salida Solicitud - find_get', false);
        if (!is_null($resultado)) {
            $this->response(array("response" => $resultado), 200);
        } else {
            $this->response(array("error" => "No se encuentra recurso"), 404);
        }
    }
    
    public function findDepto_get() {
//        var_dump("llega a find_get  "+$id);
        log_message('info', 'Ingreso buscar operadores - find_get', false);
        $resultado = $this->SolicitudFachada->consultarDepto();
        log_message('info', 'Salida Solicitud - find_get', false);
        if (!is_null($resultado)) {
            $this->response(array("response" => $resultado), 200);
        } else {
            $this->response(array("error" => "No se encuentra recurso"), 404);
        }
    }
    
    public function findCiudad_get($id) {
//        var_dump("llega a find_get  "+$id);
        log_message('info', 'Ingreso buscar operadores - find_get', false);
        $resultado = $this->SolicitudFachada->consultarCiudad($id);
        log_message('info', 'Salida Solicitud - find_get', false);
        if (!is_null($resultado)) {
            $this->response(array("response" => $resultado), 200);
        } else {
            $this->response(array("error" => "No se encuentra recurso"), 404);
        }
    }
    
    public function findOperador_get() {
//        var_dump("llega a find_get  "+$id);
        log_message('info', 'Ingreso buscar operadores - find_get', false);
        $resultado = $this->SolicitudFachada->consultarOperadores();
        log_message('info', 'Salida Solicitud - find_get', false);
        if (!is_null($resultado)) {
            $this->response(array("response" => $resultado), 200);
        } else {
            $this->response(array("error" => "No se encuentra recurso"), 404);
        }
    }
    
    public function find_get($id) {
        log_message('info', 'Ingreso Solicitud - find_get', false);
        if (!$id) {
            $this->response(NULL, 400);
        }
        $resultado = $this->SolicitudFachada->consultarSolicitudPorId($id);
        log_message('info', 'Salida Solicitud - find_get', false);
        if (!is_null($resultado)) {
            $this->response(array("response" => $resultado), 200);
        } else {
            $this->response(array("error" => "No se encuentra recurso"), 215);
        }
    }
    
    public function email_post() {
        log_message('info', 'Ingreso enviar email - find_get', false);
//      
//      //cargamos la libreria email de ci
	$num_doc=$this->post("num_doc");
        $tipoDoc=$this->post("tipoDoc");
        $sucursal=$this->post("sucursal");
        $resultado = $this->SolicitudFachada->enviarEmail($num_doc,$sucursal,$tipoDoc);
                
      if (!is_null($resultado)) {
            $this->response(array("response" => $resultado), 200);
        } else {
            $this->response(array("error" => "No se encuentra recurso"), 215);
        }
    }
    
     public function datosPago_post() {
        log_message('info', 'Ingreso enviar email - find_get', false);
//      
//      //cargamos la libreria email de ci
	$num_doc=$this->post("num_doc");
        $tipoDoc=$this->post("tipoDoc");
        $sucursal=$this->post("sucursal");
        $resultado = $this->SolicitudFachada->datosPago($num_doc,$sucursal,$tipoDoc);
                
      if (!is_null($resultado)) {
            $this->response(array("response" => $resultado), 200);
        } else {
            $this->response(array("error" => "No se encuentra recurso"), 215);
        }
    }
    
     public function validaciones_get() {
        log_message('info', 'Ingreso validaciones - find_get', false);
//        var_dump($this->input->get('num_doc'));
        $numdoc = $this->input->get('num_doc');
        $suc = $this->input->get('suc');
        $tipoDoc = $this->input->get('id_tipodocumento');
        $tipoPago = $this->input->get('presentacion');
        $operador = $this->input->get('cod_operador');
        if (!$numdoc) {
            $this->response(NULL, 400);
        }
        $resultado = $this->SolicitudFachada->validaciones($numdoc, $suc, $tipoDoc, $tipoPago, $operador);
        log_message('info', 'Salida validaciones - find_get', false);
        if (!is_null($resultado)) {
            $this->response(array("response" => $resultado), 200);
        } else {
            $this->response(array("error" => "No se encuentra recurso"), 215);
        }
    }
//    
//     public function findDatos_get() {
////        var_dump("llega a find_get  tipo doc" + $this->input->get('tipoDoc'));
////        var_dump("llega a find_get  numdoc " + $this->input->get('numdoc'));
////        var_dump("llega a find_get  sucursal" + $this->input->get('sucursal'));
//        log_message('info', 'Ingreso datos get - find_get', false);
//        $numdoc = $this->input->get('num_doc');
//        $sucursal = $this->input->get('sucursal');
//        $tipoDoc = $this->input->get('tipoDoc');
//        
//        if (!$this->input->get('num_doc') && !$this->input->get('sucursal')) {
//            $this->response(NULL, 400);
//        }
//        $resultado = $this->SolicitudFachada->consultarEmpresaPorNitSuc($numdoc,$sucursal,$tipoDoc);
//        log_message('info', 'Salida Solicitud - find_get', false);
//        if (!is_null($resultado)) {
//            log_message('info', 'resultado ok fing get Solicitud Rest', false);
//            $this->response(array("response" => $resultado), 200);
//        } else {
//            log_message('info', '404 fing get Solicitud Rest', false);
//            $this->response(array("error" => "No se encuentra recurso findget"), 404);
//        }
//    }
//    
    public function index_post() {
    	log_message('info', 'Ingreso Solicitud - index_post', false);
        if (!$this->post("data")) {
    		$this->response(NULL, 400);
    	}
    	$formularioId = $this->SolicitudFachada->guardarSolicitud($this->post("data"));
    	log_message('info', 'Salida Solicitud - index_post', false);
    	if (!is_null($formularioId)) {
    	    $this->response(array("response" => "Solicitud creado"), 200);
    	} else {
    	    $this->response(array("error" => "Se ha presentado un error"), 400);
    	}
    }
    public function index_put($id) {
    	log_message('info', 'Ingreso Solicitud - index_put', false);
    	if (!$this->put("Solicitud") || !$id) {
    		$this->response(NULL, 400);
    	}           
    	$update = $this->SolicitudFachada->actualizarSolicitud($id, $this->put("Solicitud"));
    	log_message('info', 'Salida Solicitud - index_put', false);
    	if (!is_null($update)) {
    	    $this->response(array("response" => "Solicitud actualizado"), 200);
    	} else {
    	    $this->response(array("error" => "Se ha presentado un error"), 400);
    	}
    }
    public function index_delete($id) {
    	log_message('info', 'Ingreso Solicitud - index_delete', false);
    	if (!$id) {
    	    $this->response(NULL, 400);
    	}
    	$delete = NULL;
    	try {
    	    $delete = $this->SolicitudFachada->eliminarSolicitud($id);
    	    log_message('info', 'Salida Solicitud - index_delete', false);
    	    $this->response(array("response" => "Solicitud eliminado"), 200);
    	} catch (Exception $ex) {
    	    $this->response(array("error" => "Se ha presentado un error"), 400);
    	}
    }
}
