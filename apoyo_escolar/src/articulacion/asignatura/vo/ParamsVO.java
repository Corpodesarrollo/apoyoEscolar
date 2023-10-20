package articulacion.asignatura.vo;

import siges.common.vo.Params;

public class ParamsVO extends Params{
	
	public static final int FICHA_ASIGNATURA=1;
	public static final int FICHA_LISTA_ASIGNATURA=2;
	public static final int FICHA_PRUEBA=3;
	public static final int FILTRO_PRUEBA=4;
	public static final int FILTRO_ASIGNATURA=5;
	public static final int FICHA_LISTA_PRUEBA=6;
	public static final int FICHA_DEFAULT=FICHA_ASIGNATURA;
	public static final int CMD_FILTRAR=2;
	public static final int CMD_ELIM_COREQ=7;
	public static final int CMD_ELIM_PREREQ=8;
	public static final int CMD_ELIM_COMPLE=9;
	public static final int CMD_AJAX_AREA=11;
	public static final int CMD_AJAX_PRUEBA=12;
	public static final int CMD_AJAX_ASIGNATURA=12;
	
	public int getFICHA_LISTA_ASIGNATURA() {
		return FICHA_LISTA_ASIGNATURA;
	}
	public int getFICHA_LISTA_PRUEBA() {
		return FICHA_LISTA_PRUEBA;
	}
	public int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}
	public int getFICHA_ASIGNATURA() {
		return FICHA_ASIGNATURA;
	}
	public int getFILTRAR() {
		return CMD_FILTRAR;
	}
	public int getCMD_ELIM_COREQ() {
		return CMD_ELIM_COREQ;
	}
	public int getCMD_ELIM_PREREQ() {
		return CMD_ELIM_PREREQ;
	}
	public int getCMD_ELIM_COMPLE() {
		return CMD_ELIM_COMPLE;
	}
	public int getCMD_AJAX_AREA() {
		return CMD_AJAX_AREA;
	}
	public int getFICHA_PRUEBA() {
		return FICHA_PRUEBA;
	}
	public int getFILTRO_PRUEBA() {
		return FILTRO_PRUEBA;
	}
	public int getCMD_AJAX_ASIGNATURA() {
		return CMD_AJAX_ASIGNATURA;
	}
	

}
