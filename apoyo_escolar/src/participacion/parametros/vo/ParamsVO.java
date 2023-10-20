package participacion.parametros.vo;

import siges.common.vo.Params;

public class ParamsVO extends Params{
	
	public static final int FICHA_PERFIL=1;
	public static final int FICHA_DEFAULT=FICHA_PERFIL;
	public static final int FICHA_DISCAPACIDAD = 1;
	public static final int FICHA_ETNIA = 2;
	public static final int FICHA_LGTB = 3;
	public static final int FICHA_OCUPACION= 4;
	public static final int FICHA_ROL = 5;
	
	/* esta clase debe tener informacion de los tabs que se encuentran
	 * en la interface; si como insertar, eliminar, etc
	 * no entiendo el objetivo de esta clase al tener dos variables con
	 * un unico valor*/
	public int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}
	public int getFICHA_PERFIL() {
		return FICHA_PERFIL;
	}
	public   int getFICHA_DISCAPACIDAD() {
		return FICHA_DISCAPACIDAD;
	}
	public   int getFICHA_ETNIA() {
		return FICHA_ETNIA;
	}
	public   int getFICHA_LGTB() {
		return FICHA_LGTB;
	}
	public   int getFICHA_OCUPACION() {
		return FICHA_OCUPACION;
	}
	public   int getFICHA_ROL() {
		return FICHA_ROL;
	}

}
