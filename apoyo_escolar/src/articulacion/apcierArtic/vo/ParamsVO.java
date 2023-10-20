package articulacion.apcierArtic.vo;

import siges.common.vo.Params;

public class ParamsVO extends Params{
	
	public static final int FICHA_EVALUACION=1;
	public static final int CMD_FILTRAR=1;
	public static final int FICHA_DEFAULT=FICHA_EVALUACION;
	public static final int CMD_ACTUALIZAR=2;
	public static final int CMD_AJAX_GRUPO=8;
	public static final int CMD_AJAX_ASIGNATURA=9;
	public static final int CMD_AJAX_PRUEBA=9;
	
	
	public int getCMD_AJAX_GRUPO() {
		return CMD_AJAX_GRUPO;
	}
	public int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}
	public int getFICHA_EVALUACION() {
		return FICHA_EVALUACION;
	}
	public int getFILTRAR() {
		return CMD_FILTRAR;
	}
	public int getCMD_ACTUALIZAR() {
		return CMD_ACTUALIZAR;
	}
	public int getCMD_AJAX_PRUEBA() {
		return CMD_AJAX_PRUEBA;
	}
	public int getCMD_FILTRAR() {
		return CMD_FILTRAR;
	}
	public int getCMD_AJAX_ASIGNATURA() {
		return CMD_AJAX_ASIGNATURA;
	}
	

}
