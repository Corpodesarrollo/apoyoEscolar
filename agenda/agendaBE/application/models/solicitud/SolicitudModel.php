<?php

if (!defined('BASEPATH'))
    exit('No direct script access allowed');

class SolicitudModel extends CI_Model {

    function __construct() {
        parent::__construct();
        $this->db = $this->load->database('default', TRUE);
    }

    function get($limit, $start, $filtros) {
        $this->db->limit($limit, $start);
        $this->db->order_by("id_solicitud", "desc");
        if ($filtros) {
            $this->_filter_formulario($filtros);
        }
        $query = $this->db->get("asopagos_legalsafe.solicitud");
        $this->_validateDB($query);
        if ($query->num_rows() > 0) {
            return $query->result_array();
        } else {
            return NULL;
        }
    }
     function getOperadores() {
        $this->db->select("id_operador, cod_operador, operador");
        $this->db->order_by("cod_operador", "asc");
        $query = $this->db->get("operador");
        $this->_validateDB($query);
        if ($query->num_rows() > 0) {
            return $query->result_array();
        } else {
            return NULL;
        }
    }
    
    function getIdOperador($cod) {
//        var_dump($cod);
        $this->db->select("id_operador");
        $this->db->where("cod_operador",$cod);
        $this->db->order_by("operador", "asc");
        $query = $this->db->get("operador");
        $this->_validateDB($query);
        if ($query->num_rows() == 1) {
            return $query->row()->id_operador;
        } else {
            return NULL;
        }
    }
    
    function getDepto() {
        $this->db->select("*");
        $this->db->order_by("nombre_depto", "asc");
        $query = $this->db->get("departamento");
        $this->_validateDB($query);
//        var_dump($query);
        if ($query->num_rows() > 0) {
            return $query->result_array();
        } else {
            return NULL;
        }
    }
    
    function getIdDepto($nom_depto) {
        $this->db->select("id_departamento");
        $this->db->where("nombre_depto",$nom_depto);
        $query = $this->db->get("departamento");
        $this->_validateDB($query);
        if ($query->num_rows() == 1) {
            return $query->row()->id_departamento;
        } else {
            return NULL;
        }
    }
    function getCiudad($id) {
        $this->db->select("*");
        $this->db->where("id_depto",$id);
        $this->db->order_by("nombre_ciudad", "asc");
        $query = $this->db->get("ciudad");
        $this->_validateDB($query);
        if ($query->num_rows() > 0) {
            return $query->result_array();
        } else {
            return NULL;
        }
    }
    
    function getIdCiudad($nom_ciudad,$id_depto) {
        $this->db->select("id_ciudad");
        $this->db->where("nombre_ciudad",$nom_ciudad, "id_depto", $id_depto);
        $query = $this->db->get("ciudad");
        $this->_validateDB($query);
        if ($query->num_rows() == 1) {
            return $query->row()->id_ciudad;
        } else {
            return NULL;
        }
    }
    
    function getCaja() {
        $this->db->select("id_ccf, ccf, nombre_ccf, ccf_asopagos");
        $query = $this->db->get("ccf");
        $this->_validateDB($query);
        if ($query->num_rows() > 0) {
            return $query->result_array();
        } else {
            return NULL;
        }
    }
    
    
    function getIdCaja($ccf) {
        $this->db->select("id_ccf");
        $this->db->where("ccf",$ccf);
        $query = $this->db->get("ccf");
        $this->_validateDB($query);
        if ($query->num_rows() == 1) {
            return $query->row()->id_ccf;
        } else {
            return NULL;
        }
    }
    
    function pagoPorSucursal($numdoc) {
        $consulta="transaccion.id_transaccion, transaccion.valor, max(transaccion.fecha) as ultima_tran, transaccion.estado, 
suc.id_empresa, suc.id_sucursal 
FROM transaccion as transaccion
	INNER JOIN sucursal as suc
		ON transaccion.id_sucursal = suc.id_sucursal 
	INNER JOIN empresa as emp
		ON suc.id_empresa = emp.id_empresa 
 WHERE emp.num_doc = '".$numdoc."' ORDER BY transaccion.id_transaccion;";
        $this->db->select($consulta);
        $query = $this->db->get();
        if ($query->num_rows() > 0) {
            return $query->row_array();
        } else {
            return NULL;
        }
    }
    
        
    function pagoPorNit($numdoc) {
        $consulta="transaccion.id_transaccion, transaccion.valor, max(transaccion.fecha) as ultima_tran, transaccion.estado, 
suc.id_empresa, suc.id_sucursal 
FROM transaccion as transaccion
	INNER JOIN sucursal as suc
		ON transaccion.id_sucursal = suc.id_sucursal 
	INNER JOIN empresa as emp
		ON suc.id_empresa = emp.id_empresa 
 WHERE emp.num_doc = '".$numdoc."' ORDER BY transaccion.id_transaccion;";
        $this->db->select($consulta);
        $query = $this->db->get();
        if ($query->num_rows() > 0) {
            return $query->row_array();
        } else {
            return NULL;
        }
    }
        
    
    function getValorPago($cat) {
        var_dump($cat);
        $this->db->select("valor_pago");
         $this->db->where("categoria",$cat);
        $query = $this->db->get("categoria");
        $this->_validateDB($query);
        if ($query->num_rows() == 1) {
            return $query->row_array();
        } else {
            return NULL;
        }
    }
    
    function getCajaAfiliadoNitSuc($num_doc, $suc, $tipoId) {
        $this->db->distinct("a_ccf");
        $this->db->select("a_ccf");
        $this->db->where("a_num_doc",$num_doc,"a_suc",$suc, "a_tipo_id",$tipoId);
        $query = $this->db->get("asopagos_legalsafe.afiliados");
        $this->_validateDB($query);
        if ($query->num_rows() > 0) {
            return $query->row_array();
        } else {
            return NULL;
        }
    }
    
    function getCajaAfiliadoNit($num_doc, $tipoId) {
        $this->db->distinct("a_ccf");
        $this->db->select("a_ccf");
        $this->db->where("a_num_doc",$num_doc,"a_tipo_id",$tipoId);
        $query = $this->db->get("asopagos_legalsafe.afiliados");
        $this->_validateDB($query);
        if ($query->num_rows() == 1) {
            return $query->row_array();
        } else {
            return NULL;
        }
    }
    
    function getCajaByCodigo($cod_ccf) {
        $this->db->where("ccf", $cod_ccf);
        $query = $this->db->get("ccf");
        $this->_validateDB($query);
        if ($query->num_rows() == 1) {
            return $query->row_array();
        } else {
            return NULL;
        }
    }

    function count($filtros) {
        if ($filtros) {
            $this->_filter_Recurso($filtros);
        }
        $count = $this->db->count_all_results("asopagos_legalsafe.solicitud");
        return $count;
    }

    function getById($id) {
        $this->db->where("id_solicitud", $id);
        $query = $this->db->get("solicitud");
        $this->_validateDB($query);
        if ($query->num_rows() == 1) {
            return $query->row_array();
        } else {
            return NULL;
        }
    }

    function getByIdSucDatos($nit, $suc, $tipo) {
//        var_dump("aaaaaaa model solicitud");
//            $suc = 0;
            if ($tipo == 'NI') {
                $tipo = 1;
            } else if ($tipo == 'CC') {
                $tipo = 2;
            }
        $query = $this->db->query("call busarLegal('" . $nit . "','" . $suc . "','" . $tipo ."')");
        if ($query->num_rows() == 1) {
            return $query->row_array();
        } else {
            return NULL;
        }
    }

    public function save($formulario) {
        $consulta=$this->_set_formulario($formulario);
//                ->insert("asopagos_legalsafe.solicitud");
//        $this->_validateDB($query);
        $query = $this->db->query("call insertLegal(".$consulta.")"); 
//        $query->next_result($this->db->conn_id); 
//        $query->row_array();
//        $query->free_result(); 
        if ($this->db->affected_rows() == 1) {
            return TRUE;
        } else {
            return NULL;
        }
    }

    public function update($id, $formulario) {
        $query = $this->db->set(
                        $this->_set_formulario($formulario))//NOMBRE VARIABLE
                ->where("id_solicitud", $id)//CAMPO
                ->update("solicitud"); //TABLA
        $this->_validateDB($query);
        if ($this->db->affected_rows() == 1) {
            return TRUE;
        } else {
            return NULL;
        }
    }

    public function delete($id) {
        $query = $this->db->where("id_solicitud", $id)->delete("asopagos_legalsafe.solicitud");
        $this->_validateDB($query);
        if ($this->db->affected_rows() == 1) {
            return TRUE;
        } else {
            return NULL;
        }
    }

    private function _set_formulario($Formulario) {
        $datos = array();
        $fecha = date('Y-m-d');
        $cadena = "";
        if (isset($Formulario["id_tipodocumento"])) {
            $datos["id_tipodoc"] = $Formulario["id_tipodocumento"];
            $cadena=$cadena."'".$Formulario["id_tipodocumento"]."'";
        }
        if (isset($Formulario["num_doc"])) {
            $datos["num_doc"] = $Formulario["num_doc"];
            $cadena=$cadena.",'".$Formulario["num_doc"]."'";
        }
        if (isset($Formulario["nombre"])) {
            $datos["nombre"] = $Formulario["nombre"];
            $cadena=$cadena.",'".$Formulario["nombre"]."'";
        }
        if (isset($Formulario["direccion"])) {
            $datos["direccion"] = $Formulario["direccion"];
            $cadena=$cadena.",'".$Formulario["direccion"]."'";
        }
        if (isset($Formulario["presentacion"])) {
            $datos["presentacion"] = $Formulario["presentacion"];
            $cadena=$cadena.",'".$Formulario["presentacion"]."'";
        }
        if (isset($Formulario["telefono"])) {
            $datos["telefono"] = $Formulario["telefono"];
            $cadena=$cadena.",'".$Formulario["telefono"]."'";
        }
        if (isset($Formulario["telefono2"])) {
            $datos["telefono2"] = $Formulario["telefono2"];
            $cadena=$cadena.",'".$Formulario["telefono2"]."'";
        }
        if (isset($Formulario["celular"])) {
            $datos["celular"] = $Formulario["celular"];
            $cadena=$cadena.",'".$Formulario["celular"]."'";
        }
        if (isset($Formulario["email"])) {
            $datos["email"] = $Formulario["email"];
            $cadena=$cadena.",'".$Formulario["email"]."'";
        }
        if (isset($Formulario["contacto"])) {
            $datos["contacto"] = $Formulario["contacto"];
            $cadena=$cadena.",'".$Formulario["contacto"]."'";
        }
        if (isset($Formulario["identificacion"])) {
            $datos["identificacion"] = $Formulario["identificacion"];
            $cadena=$cadena.",'".$Formulario["identificacion"]."'";
        }
        if (isset($Formulario["contacto2"])) {
            $datos["contacto2"] = $Formulario["contacto2"];
            $cadena=$cadena.",'".$Formulario["contacto2"]."'";
        }
        if (isset($Formulario["identificacion2"])) {
            $datos["identificacion2"] = $Formulario["identificacion2"];
            $cadena=$cadena.",'".$Formulario["identificacion2"]."'";
        }
        if (isset($Formulario["contacto3"])) {
            $datos["contacto3"] = $Formulario["contacto3"];
            $cadena=$cadena.",'".$Formulario["contacto3"]."'";
        }
        if (isset($Formulario["identificacion3"])) {
            $datos["identificacion3"] = $Formulario["identificacion3"];
            $cadena=$cadena.",'".$Formulario["identificacion3"]."'";
        }
        if (isset($Formulario["sucursal"])) {
            $datos["sucursal"] = $Formulario["sucursal"];
            $cadena=$cadena.",'".$Formulario["sucursal"]."'";
        }

        if (isset($Formulario["empleados"])) {
            $datos["empleados"] = $Formulario["empleados"];
            $cadena=$cadena.",'".$Formulario["empleados"]."'";
        }
        if (isset($Formulario["id_operador"])) {
            $datos["id_operador"] = $Formulario["id_operador"];
            $cadena=$cadena.",'".$Formulario["id_operador"]."'";
        }
        if (isset($Formulario["id_ccf"])) {
            $datos["id_ccf"] = $Formulario["id_ccf"];
            $cadena=$cadena.",'".$Formulario["id_ccf"]."'";
        }
        if (isset($Formulario["comentario"])) {
            $datos["comentario"] = $Formulario["comentario"];
            $cadena=$cadena.",'".$Formulario["comentario"]."'";
        }
        $cadena=$cadena.",'".$fecha."'";
        
        if (isset($Formulario["id_categoria"])) {
            $datos["id_categoria"] = $Formulario["id_categoria"];
            $cadena=$cadena.",'".$Formulario["id_categoria"]."'";
        }
        
        if (isset($Formulario["id_ciudad"])) {
            $datos["id_ciudad"] = $Formulario["id_ciudad"];
            $cadena=$cadena.",'".$Formulario["id_ciudad"]."'";
        }
        if (isset($Formulario["id_empresa"])) {
            $datos["id_empresa"] = $Formulario["id_empresa"];
            $cadena=$cadena.",'".$Formulario["id_empresa"]."'";
        }
        if (isset($Formulario["id_sucursal"])) {
            $datos["id_sucursal"] = $Formulario["id_sucursal"];
            $cadena=$cadena.",'".$Formulario["id_sucursal"]."'";
        }
        return $cadena;
    }

    private function _validateDB($resultado) {
        if (!$resultado) {
            throw new Exception();
        }
    }
    
    function getSolicitud($nit, $suc, $tipo) {
//        var_dump("aaaaaaa model solicitud");
//            $suc = 0;
            if ($tipo == 'NI') {
                $tipo = 1;
            } else if ($tipo == 'CC') {
                $tipo = 2;
            }
        $query = $this->db->query("call buscarSolicitud('" . $nit . "','" . $suc . "','" . $tipo ."')");
//        var_dump($this->db->last_query());
        if ($query->num_rows() == 1) {
            return $query->row_array();
        } else {
            return NULL;
        }
    }

}

?>