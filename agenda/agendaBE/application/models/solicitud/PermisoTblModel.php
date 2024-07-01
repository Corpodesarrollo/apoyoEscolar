<?php
/*Modelo tabla USuario
 */
if (!defined('BASEPATH'))
    exit('No direct script access allowed');

class PermisoTblModel extends CI_Model {

    function __construct() {
        parent::__construct();
        $this->db = $this->load->database('default', TRUE);
    }
       
    /*guardar el formulario en tabla TERR_FMR_PER 
     */
    public function saveFormulario($formulario) {
        $query = $this->db->set(
                        $this->_setFormulario($formulario)
                )
                ->insert("TERR_FRM_PER");
        
//        $consulta = $this->_setFormulario($formulario);
//        $query = $this->db->query($consulta);
        
//        var_dump($this->db->last_query());
       
         $this->_validateDB($query);
        
        if ($this->db->affected_rows() == 1) {
            return TRUE;
        } else {
            return NULL;
        }
        echo "final";
    }
    
    private function _setFormulario($formulario) {
        $datos = array();
        $cadenaInsert = "INSERT INTO TERR_FRM_PER (ID_USUARIOS,ID_TIPO_SOLPERFK,FEC_FORM,NOMB_DOC,RUTA_DOC) "
                . "VALUES ( ";
        if (isset($formulario["numDoc"])) {
            $cadenaInsert=$cadenaInsert.$formulario["numDoc"].",";
            $datos["ID_USUARIOS"]=$formulario["numDoc"];
        }
        if (isset($formulario["tipoSolicitud"])) {
            $cadenaInsert=$cadenaInsert.$formulario["tipoSolicitud"].",";
            $datos["ID_TIPO_SOLPERFK"]=$formulario["tipoSolicitud"];
        }
        if (isset($formulario["AUT0"])) {
            $datos["AUT0"]=$formulario["AUT0"];
        }
        if (isset($formulario["ID_AUT_0"])) {
            $datos["ID_AUT_0"]=$formulario["ID_AUT_0"];
        }
        if (isset($formulario["FEC_AUT_0"])) {
            $cadenaInsert=$cadenaInsert."TO_DATE('".$formulario["FEC_AUT_0"]."','YYYY/MM/DD'),";
            $date= date("d/F/Y", strtotime($formulario["FEC_AUT_0"]));
            $datos["FEC_AUT_0"]=$date;
        }
        if (isset($formulario["AUT1"])) {
            $datos["AUT1"]=$formulario["AUT1"];
        }
        if (isset($formulario["ID_AUT_1"])) {
            $datos["ID_AUT_1"]=$formulario["ID_AUT_1"];
        }
        if (isset($formulario["FEC_AUT_1"])) {
            $cadenaInsert=$cadenaInsert."TO_DATE('".$formulario["FEC_AUT_1"]."','YYYY/MM/DD'),";
            $date= date("d/F/Y", strtotime($formulario["FEC_AUT_1"]));
            $datos["FEC_AUT_1"]=$date;
        }
        if (isset($formulario["AUT2"])) {
            $datos["AUT2"]=$formulario["AUT2"];
        }
        if (isset($formulario["ID_AUT_2"])) {
            $datos["ID_AUT_2"]=$formulario["ID_AUT_2"];
        }
        if (isset($formulario["FEC_AUT_2"])) {
            $cadenaInsert=$cadenaInsert."TO_DATE('".$formulario["FEC_AUT_2"]."','YYYY/MM/DD'),";
            $date= date("d/F/Y", strtotime($formulario["FEC_AUT_2"]));
            $datos["FEC_AUT_2"]=$date;
        }
        if (isset($formulario["fecha_solicitud"])) {
            $cadenaInsert=$cadenaInsert."TO_DATE('".$formulario["fecha_solicitud"]."','YYYY/MM/DD'),";
            $date= date("d/F/Y", strtotime($formulario["fecha_solicitud"]));
            $datos["FEC_FORM"]=$date;
        }
        if (isset($formulario["NOMB_DOC"])) {
            $cadenaInsert=$cadenaInsert.$formulario["NOMB_DOC"].",";
        }
        if (isset($formulario["RUTA_DOC"])) {
            $cadenaInsert=$cadenaInsert.$formulario["RUTA_DOC"].")";
        }
        
        return $datos;
    }
    
     /*Guardar en la tabla de permisos*/
    public function saveSolicitudPermiso($formulario) {
        $query = $this->db->set(
                        $this->_setSolicitud($formulario)
                )
                ->insert("TERR_SOL_PERMISO");
        $this->_validateDB($query);
        if ($this->db->affected_rows() == 1) {
            return TRUE;
        } else {
            return NULL;
        }
    }
    
    private function _setSolicitud($formulario) {
        $datos = array();
        if (isset($formulario["ID_FRM_PERFK"])) {
            $datos['ID_FRM_PERFK'] = $formulario["ID_FRM_PERFK"]["ID_FRM_PER"];
        }
        if (isset($formulario["motPermiso"])) {
            $datos['ID_MOTIVOFK'] = $formulario["motPermiso"];
        }
        if (isset($formulario["DIAS_PERMISO"])) {
            $datos['DIAS_PERMISO'] = $formulario["DIAS_PERMISO"];
        }
        if (isset($formulario["CANTIDAD"])) {
            $datos['CANTIDAD'] = $formulario["CANTIDAD"];
        }
        if (isset($formulario["fInicio"])) {
            $date= date("d/F/Y", strtotime($formulario["fInicio"]));
            $datos["FEC_INI_PERM"]=$date;
        }
        if (isset($formulario["fHasta"])) {
            $date= date("d/F/Y", strtotime($formulario["fHasta"]));
            $datos["FEC_FIN_PERM"]=$date;
        }
        if (isset($formulario["horaIni"])) {
            $datos['HOR_INI_PERM'] = $formulario["horaIni"];
        }
        if (isset($formulario["horaFin"])) {
            $datos['HOR_FIN_PERM'] = $formulario["horaFin"];
        }
        if (isset($formulario["minIni"])) {
            $datos['MIN_INI_PERM'] = $formulario["minIni"];
        }
        if (isset($formulario["minFin"])) {
            $datos['MIN_FIN_PERM'] = $formulario["minFin"];
        }
        if (isset($formulario["NUM_RESOLUCION"])) {
            $datos['NUM_RESOLUCION'] = $formulario["NUM_RESOLUCION"];
        }
        if (isset($formulario["FEC_RESOLUCION"])) {
            $date= date("d/F/Y", strtotime($formulario["FEC_RESOLUCION"]));
            $datos["FEC_RESOLUCION"]=$date;
        }
        if (isset($formulario["DIAS_DISFRUTE"])) {
            $datos['DIAS_DISFRUTE'] = $formulario["DIAS_DISFRUTE"];
        }
        if (isset($formulario["DIAS_PEND"])) {
            $datos['DIAS_PEND'] = $formulario["DIAS_PEND"];
        }
        if (isset($formulario["OTRO_MOTIVO"])) {
            $datos['OTRO_MOTIVO'] = $formulario["OTRO_MOTIVO"];
        }
        if(isset($formulario["tipoVacaciones"])){
            $datos['TIPO_VACACIONES'] = $formulario["tipoVacaciones"];
        }
        return $datos;
    }
    
    /*obtener id formulario generado*/
    function getIdForm() {
        $this->db->select_max("ID_FRM_PER");
        $query = $this->db->get("TERR_FRM_PER");
        $this->_validateDB($query);
        if ($query->num_rows() == 1) {
            return $query->row_array();
        } else {
            return NULL;
        }
    }
    
    /*obtener id Solicitud Permisos generado TERR_SOL_PERMISO*/
    function getIdSolicitudPermisos() {
        $this->db->select_max("ID_SOL_PERM");
        $query = $this->db->get("TERR_SOL_PERMISO");
        $this->_validateDB($query);
        if ($query->num_rows() == 1) {
            return $query->row_array();
        } else {
            return NULL;
        }
    }
    
    /*obtener lista de dias*/
    function getDiasReposicion($id) {
        $this->db->select("*");
        $this->db->where("ID_SOL_PERMFK",$id);
        $query = $this->db->get("TERR_PERM_REPO");
        $this->_validateDB($query);
        if ($query->num_rows() > 0) {
            return $query->result_array();
        } else {
            return NULL;
        }
    }
    
    
     /*cuenta la cantidad de permisos que no tienen documento adjunto*/
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
    
    /*devuelve la cantidad de permisos autorizados dentro de un rango de fechas por ciom*/
    public function validarFechas($tipo,$fec1,$fec2,$hora1,$hora2,$idCiom){
        $consulta = "SELECT count(ID_FRM_PER) from TERR_FRM_PER FMR
    INNER JOIN TERR_SOL_PERMISO PER ON FMR.ID_FRM_PER = PER.ID_FRM_PERFK
    INNER JOIN TERR_CIOM_FUNCIONARIAS FUN ON FMR.ID_USUARIOS = FUN.ID_USUARIOS
    WHERE FMR.AUT0 = '1'
    AND FUN.ID_CIOMFK ='.$idCiom.'
    AND ((FEC_INI_PERM BETWEEN TO_DATE ('.$fec1.', 'yyyy/mm/dd') AND TO_DATE ('.$fec1.', 'yyyy/mm/dd'))
        OR (FEC_FIN_PERM BETWEEN TO_DATE ('.$fec2.', 'yyyy/mm/dd') AND TO_DATE ('.$fec2.', 'yyyy/mm/dd')));";
        
        if($tipo="horas"){
            $consulta = $consulta.' AND (PER.HOR_INI_PERM BETWEEN 2 AND 5) OR (HOR_INI_PERM BETWEEN 2 AND 4)';
        }
        
        $this->db->select($consulta);
        $query = $this->db->get(); 
        if ($query->num_rows() == 1) {
            return $query->row_array();
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

    private function _validateDB($resultado) {
        if (!$resultado) {
            throw new Exception();
        }
    }
    
    public function updateFormulario($formulario,$id){
         $query = $this->db->set(
                        $this->_set_formulario($formulario))
                ->where("FOR.ID_FRM_PER", $id)
                ->update("TERR_FRM_PER FOR"); 
        $this->_validateDB($query);
        if ($this->db->affected_rows() == 1) {
            return TRUE;
        } else {
            return NULL;
        }
    }
    
    private function _set_formulario($formulario) {
        $datos = array();
        if (isset($formulario["nomb_doc"])) {
            $datos['NOMB_DOC'] = $formulario["nomb_doc"];
        }
        if (isset($formulario["ruta_doc"])) {
            $datos['RUTA_DOC'] = $formulario["ruta_doc"];
        }
        return $datos;
    }
    
    public function saveTraSegSoc($formulario) {
        $query = $this->db->set(
                        $this->_setTraSegSoc($formulario)
                )
                ->insert("TERR_SOL_TRASEGSOC");
        $this->_validateDB($query);
        if ($this->db->affected_rows() == 1) {
            return TRUE;
        } else {
            return NULL;
        }
    }
    
    private function _setTraSegSoc($formulario) {
        $datos = array();
        if (isset($formulario["ID_FRM_PERFK"])) {
            $datos['ID_FRM_PERFK'] = $formulario["ID_FRM_PERFK"]["ID_FRM_PER"];
        }
        if (isset($formulario["epsDe"])) {
            $datos['EPS_ORIGEN'] = $formulario["epsDe"];
        }
        if (isset($formulario["epsA"])) {
            $datos['EPS_DESTINO'] = $formulario["epsA"];
        }
        if (isset($formulario["feEps"])) {
             $date= date("d/F/Y", strtotime($formulario["feEps"]));
            $datos["FEC_AFL_EPS"]=$date;
        }
        if (isset($formulario["pensionesDe"])) {
            $datos['PEN_ORIGEN'] = $formulario["pensionesDe"];
        }
        if (isset($formulario["pensionesA"])) {
            $datos['PEN_DESTINO'] = $formulario["pensionesA"];
        }
        if (isset($formulario["fePensiones"])) {
            $date= date("d/F/Y", strtotime($formulario["fePensiones"]));
            $datos["FEC_AFL_PEN"]=$date;
        }
        if (isset($formulario["cesantiasDe"])) {
            $datos['CES_ORIGEN'] = $formulario["cesantiasDe"];
        }
        if (isset($formulario["cesantiasA"])) {
            $datos['CES_DESTINO'] = $formulario["cesantiasA"];
        }
        if (isset($formulario["feCesantias"])) {
            $date= date("d/F/Y", strtotime($formulario["feCesantias"]));
            $datos["FEC_AFL_CES"]=$date;
        }
        return $datos;
    }
    
    public function savePriTec($formulario){
        $query = $this->db->set(
                        $this->_setPriTec($formulario)
                )
                ->insert("TERR_SOL_PRITEC");
        $this->_validateDB($query);
        if ($this->db->affected_rows() == 1) {
            return TRUE;
        } else {
            return NULL;
        }
    }

    
    public function _setPriTec($formulario){
        $datos = array();
        if (isset($formulario["ID_FRM_PERFK"])) {
            $datos['ID_FRM_PERFK'] = $formulario["ID_FRM_PERFK"]["ID_FRM_PER"];
        }
        if (isset($formulario["checkReconocimiento"])) {
            $datos['RECONOCIMIENTO'] = $formulario["checkReconocimiento"];
        }
        if (isset($formulario["checkReajuste"])) {
            $datos['REAJUSTE'] = $formulario["checkReajuste"];
        }
        if (isset($formulario["checkEstudios"])) {
            $datos['ESTUDIOS'] = $formulario["checkEstudios"];
        }
        if (isset($formulario["checkExperiencia"])) {
            $datos['EXPERIENCIA'] = $formulario["checkExperiencia"];
        }
        return $datos;
    }
    
    
     public function saveDiasRepocision($datos){
        $query = $this->db->set(
                        $this->_setPerRep($datos)
                )
                ->insert("TERR_PERM_REPO");
        $this->_validateDB($query);
        if ($this->db->affected_rows() == 1) {
            return TRUE;
        } else {
            return NULL;
        }
    }
    
    public function _setPerRep($formulario){
        $datos = array();
        if (isset($formulario["idForm"])) {
            $datos['ID_SOL_PERMFK'] = $formulario["idForm"];
        }
        if (isset($formulario["NOMBRE_DIA_REPO"])) {
            $datos['NOMBRE_DIA_REPO'] = $formulario["NOMBRE_DIA_REPO"];
        }
        if (isset($formulario["FEC_INI_REPO"])) {
            $date= date("d/F/Y", strtotime($formulario["FEC_INI_REPO"]));
            $datos["FEC_INI_REPO"]=$date;
        }
        if (isset($formulario["FEC_FIN_REPO"])) {
            $date= date("d/F/Y", strtotime($formulario["FEC_FIN_REPO"]));
            $datos["FEC_FIN_REPO"]=$date;
        }
        if (isset($formulario["HOR_INI_REPO"])) {
            $datos['HOR_INI_REPO'] = $formulario["HOR_INI_REPO"];
        }
        if (isset($formulario["HOR_FIN_REPO"])) {
            $datos['HOR_FIN_REPO'] = $formulario["HOR_FIN_REPO"];
        }
        if (isset($formulario["HOR_INI_REPO"])) {
            $datos['HOR_INI_REPO'] = $formulario["HOR_INI_REPO"];
        }
        if (isset($formulario["MIN_INI_REPO"])) {
            $datos['MIN_INI_REPO'] = $formulario["MIN_INI_REPO"];
        }
        if (isset($formulario["MIN_FIN_REPO"])) {
            $datos['MIN_FIN_REPO'] = $formulario["MIN_FIN_REPO"];
        }
        return $datos;
    }
}

?>