<?php

if (!defined('BASEPATH'))
    exit('No direct script access allowed');

require APPPATH . "/libraries/REST_Controller.php";

class EmpresaRest extends REST_Controller {

    function __construct() {
        parent::__construct();
        $this->load->model("/SolicitudFachada");
    }

    public function index_get() {
        log_message('info', 'Ingreso - index_get', false);
        $id = $this->input->get('idUsuario');
        if (!$id) {
            $this->response(NULL, 400);
        }
        $resultado = $this->permisoFachada->consultarUsuarioId($id);
        log_message('info', 'Salida Solicitud - index_get', false);
        if (!is_null($resultado)) {
            $this->response($resultado, 200);
        } else {
            $this->response(array("error" => "No hay Solicitud"), 404);
        }
    }

    public function find_get($id) {
//        var_dump("llega a find_get  "+$id);
        log_message('info', 'Ingreso Solicitud - find_get', false);
        if (!$id) {
            $this->response(NULL, 400);
        }
        $resultado = $this->SolicitudFachada->consultarEmpesaPorId($id);
        log_message('info', 'Salida Solicitud - find_get', false);
        if (!is_null($resultado)) {
            $this->response(array("response" => $resultado), 200);
        } else {
            $this->response(array("error" => "No se encuentra recurso"), 404);
        }
    }
    
    public function find_sucursal_get() {
//        var_dump("llega a find_get  "+$id);
        $numdoc = $this->input->get('num_doc');
        $tipoDoc = $this->input->get('id_tipodocumento');
        log_message('info', 'Ingreso sucursal_Get - find_get', false);
        if (!$numdoc) {
            $this->response(NULL, 400);
        }
        $resultado = $this->SolicitudFachada->consultarSucursalPorNit($numdoc, $tipoDoc);
        log_message('info', 'Salida Solicitud - find_get', false);
        if (!is_null($resultado)) {
            $this->response(array("response" => $resultado), 200);
        } else {
            $this->response(array("error" => "No se encuentra en pila"), 404);
        }
    }

    public function findDatos_get() {
//        var_dump("llega a find_get  tipo doc" + $this->input->get('tipoDoc'));
//        var_dump("llega a find_get  numdoc " + $this->input->get('numdoc'));
//        var_dump("llega a find_get  sucursal" + $this->input->get('sucursal'));
        log_message('info', 'Ingreso datos get - find_get', false);
        $numdoc = $this->input->get('num_doc');
        $sucursal = $this->input->get('sucursal');
        $tipoDoc = $this->input->get('id_tipodocumento');
        if (!$this->input->get('num_doc') && !$this->input->get('sucursal')) {
            $this->response(NULL, 400);
        }
        $resultado = $this->SolicitudFachada->consultarEmpresaPorNitSuc($numdoc, $sucursal, $tipoDoc);
        log_message('info', 'Salida Solicitud - find_get', false);
        if (!is_null($resultado)) {
            log_message('info', 'resultado ok fing get Solicitud Rest', false);
            $this->response(array("response" => $resultado), 200);
        } else {
            $this->response(array("error" => "No se encuentra recurso"), 400);
        }
    }

    public function index_post() {
        log_message('info', 'Ingreso Solicitud - index_post', false);
        if (!$this->post("data")) {
            $this->response(NULL, 400);
        }
        $formularioId = $this->SolicitudFachada->guardarEmpresa($this->post("data"));
        log_message('info', 'Salida Solicitud - index_post', false);
        if (!is_null($formularioId)) {
            $this->response(array("response" => "Empresa pila creado"), 200);
        } else {
            $this->response(array("error" => "Se ha presentado un error"), 400);
        }
    }

    public function index_put($id) {
        log_message('info', 'Ingreso Solicitud - index_put', false);
        if (!$this->put("Solicitud") || !$id) {
            $this->response(NULL, 400);
        }
        $update = $this->SolicitudFachada->actualizarEmpresa($id, $this->put("Solicitud"));
        log_message('info', 'Salida Empresa - index_put', false);
        if (!is_null($update)) {
            $this->response(array("response" => "Solicitud actualizado"), 200);
        } else {
            $this->response(array("error" => "Se ha presentado un error"), 400);
        }
    }

    public function index_delete($id) {
        log_message('info', 'Ingreso Empresa - index_delete', false);
        if (!$id) {
            $this->response(NULL, 400);
        }
        $delete = NULL;
        try {
            $delete = $this->SolicitudFachada->eliminarEmpresa($id);
            log_message('info', 'Salida Empresa - index_delete', false);
            $this->response(array("response" => "Empresa eliminado"), 200);
        } catch (Exception $ex) {
            $this->response(array("error" => "Se ha presentado un error"), 400);
        }
    }

}
