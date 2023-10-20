package articulacion.asigTutor.vo;

import siges.common.vo.Params;

public class ParamsVO extends Params{
	
	public static final int FICHA_ASIG_TUTOR=1;
	public static final int CMD_FILTRAR=1;
	public static final int FICHA_DEFAULT=FICHA_ASIG_TUTOR;
	public static final int CMD_MODIFICAR=2;
	public static final int CMD_AJAX_DOCENTE=8;
	
	
	public int getCMD_AJAX_DOCENTE() {
		return CMD_AJAX_DOCENTE;
	}
	public int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}
	public int getFICHA_ASIG_TUTOR() {
		return FICHA_ASIG_TUTOR;
	}
	public int getFILTRAR() {
		return CMD_FILTRAR;
	}
	public int getCMD_MODIFICAR() {
		return CMD_MODIFICAR;
	}
	

}
