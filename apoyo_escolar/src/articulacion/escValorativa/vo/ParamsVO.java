package articulacion.escValorativa.vo;

import siges.common.vo.Params;

public class ParamsVO extends Params{
	
	public static final int FICHA_ESCALA=1;
	public static final int CMD_FILTRAR=2;
	public static final int FICHA_DEFAULT=FICHA_ESCALA;
	
	
	public int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}
	public int getFICHA_ESCALA() {
		return FICHA_ESCALA;
	}
	public int getFILTRAR() {
		return CMD_FILTRAR;
	}
}
