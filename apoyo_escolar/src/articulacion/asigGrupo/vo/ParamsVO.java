package articulacion.asigGrupo.vo;

import siges.common.vo.Params;

public class ParamsVO extends Params{
	
	public static final int FICHA_ASIGNAR_GRUPO=1;
	
	public static final int FICHA_BUSCAR=2;
	public static final int FILTRO_ASIGNATURA=5;
	public static final int FICHA_LISTA_PRUEBA=6;
	public static final int FICHA_DEFAULT=FICHA_ASIGNAR_GRUPO;
	
	public static final int CMD_AJAX_GET_ASIGNATURA=10;
	public static final int CMD_AJAX_GET_GRUPOS=11;
	public static final int CMD_AJAX_BUSCAR=12;
	
	public static final int CMD_AJAX_GUARDAR=13;
	
	public int getFICHA_BUSCAR() {
		return FICHA_BUSCAR;
	}
	public int getFICHA_LISTA_PRUEBA() {
		return FICHA_LISTA_PRUEBA;
	}
	public int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}
	public int getFICHA_ASIGNAR_GRUPO() {
		return FICHA_ASIGNAR_GRUPO;
	}
	public int getCMD_AJAX_AREA() {
		return CMD_AJAX_AREA;
	}
	public int getCMD_AJAX_GET_ASIGNATURA() {
		return CMD_AJAX_GET_ASIGNATURA;
	}
	public int getCMD_AJAX_GET_GRUPOS() {
		return CMD_AJAX_GET_GRUPOS;
	}
	public int getCMD_AJAX_BUSCAR() {
		return CMD_AJAX_BUSCAR;
	}
	public int getCMD_AJAX_GUARDAR() {
		return CMD_AJAX_GUARDAR;
	}
	

}
