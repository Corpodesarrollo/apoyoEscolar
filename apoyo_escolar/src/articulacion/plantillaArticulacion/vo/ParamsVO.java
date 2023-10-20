package articulacion.plantillaArticulacion.vo;

import siges.common.vo.Params;

public class ParamsVO extends Params{
	
	public static final int FICHA_PERFIL=1;
	public static final int CMD_FILTRAR=2;
	public static final int FICHA_DEFAULT=FICHA_PERFIL;
	public static final int CMD_AJAX_GRUPO=12;
	
	public int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}
	public int getFICHA_PERFIL() {
		return FICHA_PERFIL;
	}
	public int getFILTRAR() {
		return CMD_FILTRAR;
	}
	
	public int getCMD_AJAX_GRUPO() {
		return CMD_AJAX_GRUPO;
	}
	

}
