package articulacion.retiroEstudiantes.vo;

import siges.common.vo.Params;

public class ParamsVO extends Params{
	
	public static final int FICHA_EVALUACION=1;
	public static final int CMD_FILTRAR=1;
	public static final int FICHA_DEFAULT=FICHA_EVALUACION;
	public static final int CMD_INSERTAR=2;
	public static final int CMD_AJAX_GRUPO=8;
	public static final int CMD_AJAX_ESTUDIANTE=10;
	
	public static final int CMD_POP_CANCELAR=21;
	public static final int CMD_POP_MOSTRAR=20;
	
	public int getCMD_AJAX_ESTUDIANTE() {
		return CMD_AJAX_ESTUDIANTE;
	}
	
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
	public int getCMD_INSERTAR() {
		return CMD_INSERTAR;
	}
	public int getCMD_FILTRAR() {
		return CMD_FILTRAR;
	}

	public int getCMD_POP_CANCELAR() {
		return CMD_POP_CANCELAR;
	}

	public int getCMD_POP_MOSTRAR() {
		return CMD_POP_MOSTRAR;
	}
	

}
