<?php
/*Modelo tabla USuario
 */
if (!defined('BASEPATH'))
    exit('No direct script access allowed');

class UsuarioModel extends CI_Model {

    function __construct() {
        parent::__construct();
        $this->db = $this->load->database('default', TRUE);
    }
       
    /*obtener datos de ususario por numero de identificacion de la tabla SEGU_USUARIOS 
     */
    function getUsuarioById($id,$nomUsu) {
        $this->db->select("USU.PRIMER_NOMBRE, USU.PRIMER_APELLIDO,FUN.ID_USUARIOS AS numDoc,USU.NOMBRE_USUARIO as nomUsu,USU.ID_USUARIOS, FUN.ID_CARGOFK, FUN.DEPENDENCIA, FUN.TELEFONO, FUN.BND1");
        $this->db->from('TERR_CIOM_FUNCIONARIAS FUN');
        $this->db->join('SEGU_USUARIOS USU', 'FUN.ID_USUARIOS = USU.NUMERO_IDENTIFICACION', 'left');
        $this->db->where("NUMERO_IDENTIFICACION",$id);
        $this->db->where("NOMBRE_USUARIO",$nomUsu);
        $query = $this->db->get();
         
        $this->_validateDB($query);
        if ($query->num_rows() == 1) {
            return $query->row_array();
        } else {
            return NULL;
        }
    }
    
    /*consultar usuario CIOM 
     */
    public function consultarFuncionariaId($id) {
        $this->db->select("USU.PRIMER_NOMBRE, USU.PRIMER_APELLIDO, USU.ID_USUARIOS, FUN.ID_USUARIOS, FUN.ID_CARGOFK, FUN.DEPENDENCIA, FUN.TELEFONO, FUN.BND1, CAR.CARGO, CAR.CARGO_ESPEC");
        $this->db->from('TERR_CIOM_FUNCIONARIAS FUN');
        $this->db->join('SEGU_USUARIOS USU', 'USU.NUMERO_IDENTIFICACION = FUN.ID_USUARIOS', 'left');
        $this->db->join('TERR_FUNC_CARGO CAR', 'CAR.ID_CARGO = FUN.ID_CARGOFK', 'left');
        $this->db->where("FUN.ID_USUARIOS",$id);
        $query = $this->db->get();
         
        $this->_validateDB($query);
        if ($query->num_rows() == 1) {
            return $query->row_array();
        } else {
            return NULL;
        }
    }
    
    /*actualizar usuario CIOM 
     */
    public function updateFuncionaria($funcionaria,$id) {
        $query = $this->db->set(
                        $this->_set_funcionaria($funcionaria))
                ->where("FUN.ID_USUARIOS", $id)
                ->update("TERR_CIOM_FUNCIONARIAS FUN"); 
        $this->_validateDB($query);
        if ($this->db->affected_rows() == 1) {
            return TRUE;
        } else {
            return NULL;
        }
    }
    
    /*obtener cargos de CIOM 
     */
    function getCargoCiom() {
        $this->db->select("ID_CARGO, CARGO, CARGO_ESPEC");
        $query = $this->db->get("TERR_FUNC_CARGO");
        $this->_validateDB($query);
        if ($query->num_rows() > 0) {
            return $query->result_array();
        } else {
            return NULL;
        }
    }
    
    
     /*obtener id CIOM 
     */
    function obtenerCiom($idUsu) {
        $this->db->select("ID_CIOMFK");
        $this->db->where("ID_USUARIOS",$idUsu);
        $query = $this->db->get("TERR_CIOM_FUNCIONARIAS");
        $this->_validateDB($query);
        if ($query->num_rows() > 0) {
            return $query->result_array();
        } else {
            return NULL;
        }
    }
    
    /*obtener Motivos de permiso 
     */
    function getMotivo($cat) {
        $this->db->select("*");
        $this->db->where("CAT_MOTIVO",$cat);
        $query = $this->db->get("TERR_MOTIVO");
        $this->_validateDB($query);
        
        if ($query->num_rows() > 0) {
            return $query->result_array();
        } else {
            return NULL;
        }
    }
    
    
    function obtenerDocsPendientes($usu) {
        $this->db->select("*");
        $query = $this->db->get("TERR_MOTIVO");
        
        $this->_validateDB($query);
        
         var_dump($query);
         var_dump($query->result_array());
         
//        if ($query->num_rows() > 0) {
//            return $query->result_array();
//        } else {
//            return NULL;
//        }
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

    private function _set_funcionaria($funcionaria) {
        $datos = array();
        if (isset($funcionaria["numDoc"])) {
            $datos['ID_USUARIOS'] = $funcionaria["numDoc"];
        }
        if (isset($funcionaria["ID_CARGOFK"])) {
            $datos['ID_CARGOFK'] = $funcionaria["ID_CARGOFK"];
        }
        if (isset($funcionaria["DEPENDENCIA"])) {
            $datos['DEPENDENCIA'] = $funcionaria["DEPENDENCIA"];
        }
        if (isset($funcionaria["TELEFONO"])) {
            $datos['TELEFONO'] = $funcionaria["TELEFONO"];
        }
        if (isset($funcionaria["BND1"])) {
            $datos['BND1'] = $funcionaria["BND1"];
        }
        if (isset($funcionaria["BND2"])) {
            $datos['BND2'] = $funcionaria["BND2"];
        }
        if (isset($funcionaria["ID_CIOMfK"])) {
            $datos['ID_CIOMfK'] = $funcionaria["ID_CIOMfK"];
        }
        return $datos;
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