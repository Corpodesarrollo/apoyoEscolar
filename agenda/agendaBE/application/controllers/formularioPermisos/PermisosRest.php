<?php
//Controlador Rest para el formulario de solicitud de permisos
if (!defined('BASEPATH'))
    exit('No direct script access allowed');
    
require APPPATH . "/libraries/REST_Controller.php";    

class PermisosRest extends REST_Controller {
	
	function __construct() {
        parent::__construct();
        $this->load->model("/PermisoFachada");
    }
    
    //obtiener los datos de usuario
    public function index_get($id, $nomUsu) {
        log_message('info', 'Ingreso - index_get', false);
        if (!$id ||!$nomUsu) { 
            $this->response(NULL, 210);
        }
        $resultado = $this->PermisoFachada->consultarUsuarioId($id,$nomUsu);
        log_message('info', 'Salida Solicitud - index_get', false);
        if (!is_null($resultado)) {
            $this->response(array("response" => $resultado), 200);
        } else {
            log_message('info', $resultado);
            $this->response(array("error" => "No se encontraron registros"), 401);
        }
    }
    
    //obtiener los cargos para las ciom
    public function find_cargo_get() {
        log_message('info', 'Ingreso - find_cargo_get', false);
      
        $resultado = $this->PermisoFachada->consultarCargoCiom();
        log_message('info', 'Salida find_cargo_get', false);
        if (!is_null($resultado)) {
            $this->response(array("response" => $resultado), 200);
        } else {
            log_message('info', $resultado);
            $this->response(array("error" => "No se encontraron registros"), 401);
        }
    }
    
     //obtiener los motivos de permiso o licencia para las ciom
    public function find_motivo_get() {
        log_message('info', 'Ingreso - find_cargo_get', false);
        $cat = $this->input->get('cat');
       
        $resultado = $this->PermisoFachada->getMotivos($cat);
        log_message('info', 'Salida find_cargo_get', false);
        if (!is_null($resultado)) {
            $this->response(array("response" => $resultado), 200);
        } else {
            log_message('info', $resultado);
            $this->response(array("error" => "No se encontraron registros"), 411);
        }
    }
    
     //obtener dias de reposicion por formulario de permiso educativo
    public function buscarDiasFormulario_get($idForm) {
        log_message('info', 'Ingreso - buscarDiasFormulario_get', false);
        if (!$idForm) { 
            $this->response(NULL, 210);
        }
       
        $resultado = $this->PermisoFachada->getDiasReposicion($idForm);
        
        log_message('info', 'Salida find_cargo_get', false);
        if (!is_null($resultado)) {
            $this->response(array("response" => $resultado), 200);
        } else {
            log_message('info', $resultado);
            $this->response(array("error" => "No se encontraron registros"), 411);
        }
    }
    
     //guardar dias
    public function guardar_dias_post() {
        log_message('info', 'Ingreso - guardar_dias_post', false);
        if (!$this->post("datos")) {
    		$this->response(NULL, 401);
    	}
        $resultado = $this->PermisoFachada->guardarDiasReposicion($this->post("datos"));
        
        log_message('info', 'Salida find_cargo_get', false);
        if ($resultado["estatus"] == "OK") {
    	    $this->response(array("response" => "Solicitud creada"), 200);
    	} else {
    	    $this->response(array("error" => $resultado["mensaje"]), 408);
    	}
    }
    
    public function index_post() {
    	log_message('info', 'Ingreso Guarda Formulario - index_post', false);
        if (!$this->post("data")) {
    		$this->response(NULL, 401);
    	}
        // Cuando se hace le decode de json a objet este queda como un objeto de tipo stdClass (Standar Class)
        // Para obtener una propiedad del sdclass se debe hacer con ->, si se quiere con [] se debe hacer un cass a array
        $form= (array)json_decode($this->post("data"));
        // Se hace el cass a array para que as propiedades se puedan obtener con [] y asi no cambiar mas parte del codigo
        //$form = (array)$data["data"];
        $file = null;
        if(isset($_FILES["file"])){
            $file = $_FILES["file"];
        }
    	$resultado = $this->PermisoFachada->guardarFormulario($form,$file);
    	log_message('info', 'Salida Guarda Formulario - index_post', false);
    	if ($resultado["estatus"] == "OK") {
    	    $this->response(array("response" => "Solicitud creada","formulario" => $resultado["formulario"]), 200);
    	} else {
    	    $this->response(array("error" => $resultado["mensaje"]), 408);
    	}
    }
    
//    Cargar Archivo soporte
    public function adjuntar_post() {
    	log_message('info', 'Ingreso Guarda Formulario - index_post', false);
        var_dump("entro al rest");
        var_dump($_FILES["file"]);
        var_dump("post");
        var_dump($this->post("data"));
        $file = $_FILES["file"]["name"];
        
        
        move_uploaded_file($_FILES["file"]["tmp_name"], 
                'C:/xampp/htdocs/agenda/soportesAdjuntoTerritorializacion/'.'_5'.$file);
                
        if (!$file) {
    		$this->response(NULL, 401);
    	}
        
    	$formularioId = $this->PermisoFachada->guardarArchivo($file);
    	log_message('info', 'Salida Guarda Formulario - index_post', false);
    	if (!is_null($formularioId)) {
    	    $this->response(array("response" => "Solicitud creada"), 200);
    	} else {
    	    $this->response(array("error" => "Se ha presentado un error"), 408);
    	}
    }
    
    
    public function validarFechas_get($fechas,$tipo,$idUsu) {
    	log_message('info', 'Ingreso validarFechas_get - index_post', false);
        if (!$fechas || $tipo || $idUsu) { 
            $this->response(array("error" => "parametros invalidos"), 210);
        }
        $resultado = $this->PermisoFachada->validarFechas($fechas,$tipo,$idUsu);
    	log_message('info', 'Salida validarFechas_get - index_post', false);
    	if (!is_null($resultado)) {
    	    $this->response(array("response" => $resultado), 200);
    	} else {
    	    $this->response(array("error" => "Se ha presentado un error"), 400);
    	}
    }
   
    public function cargar_post() {
        log_message('info', 'Ingreso - cargar_post', false);
        $data = $this->upload->data();
        echo $data['file_name'];
        $this->response(array("error" => $this->post("data")), 200);
    }
    
    
    //consultar Funcionaria CIOM
    public function consultarFuncionaria_get($id) {
        log_message('info', 'Ingreso - consultar funcionaria', false);
      
        $resultado = $this->PermisoFachada->consultarFuncionariaId($id);
        log_message('info', 'Ingreso - consultar funcionaria', false);
        if (!is_null($resultado)) {
            $this->response(array("response" => $resultado), 200);
        } else {
            log_message('info', $resultado);
            $this->response(array("error" => "No se ha encontrado funcionario(a)"), 411);
        }
    }
    
    //actualizar Funcionaria CIOM
    public function actualizarFuncionaria_post() {
        log_message('info', 'Ingreso - actualizar funcionaria', false);
        $funcionaria=$this->post("datos");
        if(!isset($funcionaria["ID_USUARIOS"]) ){
            $this->response("parametros invalidos", 401);
        }
        
        $resultado = $this->PermisoFachada->actualizarFuncionaria($funcionaria);
        log_message('info', 'Ingreso - actualizar funcionaria', false);
        if (!is_null($resultado)) {
            $this->response(array("response" => "Funcionaria actualizada correctamente"), 200);
        } else {
            log_message('info', $resultado);
            $this->response(array("error" => "No se pudo actualizar funcionario(a)"), 411);
        }
    }
    
//    public function findValor_get($cat) {
////        $cat = $this->input->get('categoria');
//        log_message('info', 'Ingreso buscar operadores - find_get', false);
//        $resultado = $this->SolicitudFachada->consultarValor($cat);
//        log_message('info', 'Salida valor - find_get', false);
//        if (!is_null($resultado)) {
//            $this->response(array("response" => $resultado), 200);
//        } else {
//            $this->response(array("error" => "No se encuentra recurso"), 404);
//        }
//    }
//    
//    public function findCaja_get() {
////        var_dump("llega a find_get  "+$id);
//        log_message('info', 'Ingreso buscar operadores - find_get', false);
//        $resultado = $this->SolicitudFachada->consultarCaja();
//        log_message('info', 'Salida Solicitud - find_get', false);
//        if (!is_null($resultado)) {
//            $this->response(array("response" => $resultado), 200);
//        } else {
//            $this->response(array("error" => "No se encuentra recurso"), 404);
//        }
//    }
//    
//    public function findDepto_get() {
////        var_dump("llega a find_get  "+$id);
//        log_message('info', 'Ingreso buscar operadores - find_get', false);
//        $resultado = $this->SolicitudFachada->consultarDepto();
//        log_message('info', 'Salida Solicitud - find_get', false);
//        if (!is_null($resultado)) {
//            $this->response(array("response" => $resultado), 200);
//        } else {
//            $this->response(array("error" => "No se encuentra recurso"), 404);
//        }
//    }
//    
//    public function findCiudad_get($id) {
////        var_dump("llega a find_get  "+$id);
//        log_message('info', 'Ingreso buscar operadores - find_get', false);
//        $resultado = $this->SolicitudFachada->consultarCiudad($id);
//        log_message('info', 'Salida Solicitud - find_get', false);
//        if (!is_null($resultado)) {
//            $this->response(array("response" => $resultado), 200);
//        } else {
//            $this->response(array("error" => "No se encuentra recurso"), 404);
//        }
//    }
//    
//    public function findOperador_get() {
////        var_dump("llega a find_get  "+$id);
//        log_message('info', 'Ingreso buscar operadores - find_get', false);
//        $resultado = $this->SolicitudFachada->consultarOperadores();
//        log_message('info', 'Salida Solicitud - find_get', false);
//        if (!is_null($resultado)) {
//            $this->response(array("response" => $resultado), 200);
//        } else {
//            $this->response(array("error" => "No se encuentra recurso"), 404);
//        }
//    }
//    
//    public function find_get($id) {
//        log_message('info', 'Ingreso Solicitud - find_get', false);
//        if (!$id) {
//            $this->response(NULL, 400);
//        }
//        $resultado = $this->SolicitudFachada->consultarSolicitudPorId($id);
//        log_message('info', 'Salida Solicitud - find_get', false);
//        if (!is_null($resultado)) {
//            $this->response(array("response" => $resultado), 200);
//        } else {
//            $this->response(array("error" => "No se encuentra recurso"), 215);
//        }
//    }
//    
//    public function email_post() {
//        log_message('info', 'Ingreso enviar email - find_get', false);
////      
////      //cargamos la libreria email de ci
//	$num_doc=$this->post("num_doc");
//        $tipoDoc=$this->post("tipoDoc");
//        $sucursal=$this->post("sucursal");
//        $resultado = $this->SolicitudFachada->enviarEmail($num_doc,$sucursal,$tipoDoc);
//                
//      if (!is_null($resultado)) {
//            $this->response(array("response" => $resultado), 200);
//        } else {
//            $this->response(array("error" => "No se encuentra recurso"), 215);
//        }
//    }
//    
//     public function datosPago_post() {
//        log_message('info', 'Ingreso enviar email - find_get', false);
////      
////      //cargamos la libreria email de ci
//	$num_doc=$this->post("num_doc");
//        $tipoDoc=$this->post("tipoDoc");
//        $sucursal=$this->post("sucursal");
//        $resultado = $this->SolicitudFachada->datosPago($num_doc,$sucursal,$tipoDoc);
//                
//      if (!is_null($resultado)) {
//            $this->response(array("response" => $resultado), 200);
//        } else {
//            $this->response(array("error" => "No se encuentra recurso"), 215);
//        }
//    }
//    
//     public function validaciones_get() {
//        log_message('info', 'Ingreso validaciones - find_get', false);
////        var_dump($this->input->get('num_doc'));
//        $numdoc = $this->input->get('num_doc');
//        $suc = $this->input->get('suc');
//        $tipoDoc = $this->input->get('id_tipodocumento');
//        $tipoPago = $this->input->get('presentacion');
//        $operador = $this->input->get('cod_operador');
//        if (!$numdoc) {
//            $this->response(NULL, 400);
//        }
//        $resultado = $this->SolicitudFachada->validaciones($numdoc, $suc, $tipoDoc, $tipoPago, $operador);
//        log_message('info', 'Salida validaciones - find_get', false);
//        if (!is_null($resultado)) {
//            $this->response(array("response" => $resultado), 200);
//        } else {
//            $this->response(array("error" => "No se encuentra recurso"), 215);
//        }
//    }
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
//    
//    public function index_put($id) {
//    	log_message('info', 'Ingreso Solicitud - index_put', false);
//    	if (!$this->put("Solicitud") || !$id) {
//    		$this->response(NULL, 400);
//    	}           
//    	$update = $this->SolicitudFachada->actualizarSolicitud($id, $this->put("Solicitud"));
//    	log_message('info', 'Salida Solicitud - index_put', false);
//    	if (!is_null($update)) {
//    	    $this->response(array("response" => "Solicitud actualizado"), 200);
//    	} else {
//    	    $this->response(array("error" => "Se ha presentado un error"), 400);
//    	}
//    }
//    public function index_delete($id) {
//    	log_message('info', 'Ingreso Solicitud - index_delete', false);
//    	if (!$id) {
//    	    $this->response(NULL, 400);
//    	}
//    	$delete = NULL;
//    	try {
//    	    $delete = $this->SolicitudFachada->eliminarSolicitud($id);
//    	    log_message('info', 'Salida Solicitud - index_delete', false);
//    	    $this->response(array("response" => "Solicitud eliminado"), 200);
//    	} catch (Exception $ex) {
//    	    $this->response(array("error" => "Se ha presentado un error"), 400);
//    	}
//    }
}
