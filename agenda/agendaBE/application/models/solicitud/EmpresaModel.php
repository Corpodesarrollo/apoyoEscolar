<?php

if (!defined('BASEPATH'))
    exit('No direct script access allowed');

class EmpresaModel extends CI_Model {

    function __construct() {
        parent::__construct();
        $this->db = $this->load->database('pila', TRUE);
    }

    function get($limit, $start, $filtros) {
        $this->db->limit($limit, $start);
        $this->db->order_by("Nit", "desc");
        if ($filtros) {
            $this->_filter_formulario($filtros);
        }
        $query = $this->db->get("tbl_empresa");
        $this->_validateDB($query);
//        var_dump($this->db->last_query());
        if ($query->num_rows() > 0) {
            return $query->result_array();
        } else {
            return NULL;
        }
    }

    function getAll() {
        $this->db->order_by("Nit", "desc");
        $query = $this->db->get("tbl_empresa");
        $this->_validateDB($query);
        if ($query->num_rows() > 0) {
            return $query->result_array();
        } else {
            return NULL;
        }
    }

    function count($filtros) {
//        var_dump("last_query(");
        if ($filtros) {
            $this->_filter_Recurso($filtros);
        }
        $count = $this->db->count_all_results("tbl_empresa");
//        var_dump($this->db->last_query());
        return $count;
    }

    function getById($id) {
        $this->db->where("Nit", $id);
        $query = $this->db->get("tbl_empresa");
        $this->_validateDB($query);
        if ($query->num_rows() == 1) {
            return $query->row_array();
        } else {
            return NULL;
        }
    }
    
    function getSucById($id,$tipo,$ano,$mes) {
        $consulta = "distinct `Código Sucursal` as suc 
from tbl_pagos_diarios where `Número Identificación` = '".$id."' and `Tipo Identificación` = '".$tipo."'
and  `Año Pago`='".$ano."' and `Mes Pago` = '".$mes."' order by `Código Sucursal`";
        $this->db->select($consulta);
        $query = $this->db->get();
        $this->_validateDB($query);
        log_message('error', 'en base de datos retorna', false);
        if ($query->num_rows() > 0) {
            return $query->result_array();
        } else {
            return NULL;
        }
    }
    
    function getByIdSucDatos($nit,$sucursal,$tipoDoc) {
        if($tipoDoc='1'){
            $tipoDoc="CC";
        }
        if($tipoDoc='2'){
            $tipoDoc="NI";
        }
        $consulta = "pagos.`Tipo Identificación` as id_tipodocumento,pagos.`Número Identificación` as num_doc, pagos.`Nombre Aportante` as nombre,
pagos.`Código Sucursal` as sucursal, emp.`caja` as ccf, ope.`NOMBRE` as nombre_ccf, emp.`Operador_Actual` as cod_operador,
emp.`Persona_Contacto` as contacto, pagos.`Dirección` as direccion, pagos.`Teléfono Fijo` as telefono, pagos.`Teléfono Celular` as celular,
pagos.`E-mail` as email, depto.`DEP_NOMBRE` as nombre_depto, municipio.`MUN_NOMBRE` as nombre_ciudad
FROM `tbl_pagos_diarios` as pagos
	INNER JOIN `tbl_empresa` as emp
		ON pagos.`Número Identificación` = emp.`Nit`
	INNER JOIN `operadores_administradoras` as ope
		ON emp.`caja` = ope.`CODIGO`
	INNER JOIN `tbl_departamentos` as depto
		ON pagos.`Departamento` = depto.`DEP_CODIGO`
	INNER JOIN `tbl_municipios` as municipio
		 ON pagos.`Municipio` = municipio.`MUN_CODIGO`
		 AND pagos.`Departamento` = municipio.`MUN_DEP_CODIGO`
WHERE pagos.`Número Identificación`= '" . $nit . "' and `Código Sucursal` = '" . $sucursal . "' and `Tipo Identificación` = '" . $tipoDoc . "'
group by num_doc";
        $this->db->select($consulta);
        $query = $this->db->get(); 
        if ($query->num_rows() == 1) {
            return $query->row_array();
        } else {
            return NULL;
        }
        log_message('error', 'en base de datos retorna', false);
        return 1;
    }
    
    function getEmpleadosByNitBySucursal($nit,$sucursal,$ano,$mes) {
        $consulta = "sum(`Número Empleados`) as empleados from `tbl_pagos_diarios` where `Número Identificación` = '".$nit."' and `Año Pago`='".$ano."' and `Mes Pago`='".$mes."' and `Código Sucursal` = '".$sucursal."'";
        $query = $this->db->select($consulta);
        $query = $this->db->get(); 
        if ($query->num_rows() == 1) {
            return $query->row_array();
        } else {
            return NULL;
        }
        log_message('error', 'en base de datos retorna', false);
        return 1;
    }
    
    function getByIdSuc($nit,$suc) {
        $this->db->where("Nit", $nit);
        $this->db->where("cs_v_sucursal",$suc);
        $query = $this->db->get("tbl_empresa");
        $this->_validateDB($query);
        if ($query->num_rows() == 1) {
            return $query->row_array();
        } else {
            return NULL;
        }
    }
    
    public function save($formulario) {
        $query = $this->db->set(
                        $this->_set_formulario($formulario))
                ->insert("tbl_empresa");
        $this->_validateDB($query);
        if ($this->db->affected_rows() == 1) {
            return TRUE;
        } else {
            return NULL;
        }
    }

    public function update($id, $formulario) {
        $query = $this->db->set(
                        $this->_set_formulario($formulario))//NOMBRE VARIABLE
                ->where("Nit", $id)//CAMPO
                ->update("tbl_empresa"); //TABLA
        $this->_validateDB($query);
        if ($this->db->affected_rows() == 1) {
            return TRUE;
        } else {
            return NULL;
        }
    }

    public function delete($id) {
        $query = $this->db->where("Nit", $id)->delete("tbl_empresa");
        $this->_validateDB($query);
        if ($this->db->affected_rows() == 1) {
            return TRUE;
        } else {
            return NULL;
        }
    }

    private function _set_Formulario($Formulario) {
        $datos = array();
        var_dump($Formulario);
        if (isset($Formulario["caja"])) {
            $datos["Caja"] = $Formulario["caja"];
        }
        if (isset($Formulario["nit"])) {
            $datos["Nit"] = $Formulario["nit"];
        }
        if (isset($Formulario["nom_empresa"])) {
            $datos["Nombre_Empresa"] = $Formulario["nom_empresa"];
        }
        if (isset($Formulario["contacto"])) {
            $datos["Persona_Contacto"] = $Formulario["contacto"];
        }
        if (isset($Formulario["direccion"])) {
            $datos["Direccion"] = $Formulario["direccion"];
        }
        if (isset($Formulario["fijo_sol"])) {
            $datos["Telefono_Fijo"] = $Formulario["fijo_sol"];
        }
        if (isset($Formulario["cel_sol"])) {
            $datos["Celular"] = $Formulario["cel_sol"];
        }
        if (isset($Formulario["email"])) {
            $datos["Mail"] = $Formulario["email"];
        }
        if (isset($Formulario["operador"])) {
            $datos["Operador_Actual"] = $Formulario["operador"];
        }
        if (isset($Formulario["comentario_sol"])) {
            $datos["comentario_sol"] = $Formulario["comentario_sol"];
        }
        if (isset($Formulario["sucursal"])) {
            $datos["cs_v_sucursal"] = $Formulario["sucursal"];
        }
        return $datos;
    }

    private function _validateDB($resultado) {
        if (!$resultado) {
            throw new Exception();
        }
    }

}
