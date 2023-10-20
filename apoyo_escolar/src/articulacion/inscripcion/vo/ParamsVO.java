package articulacion.inscripcion.vo;

import siges.common.vo.Params;

public class ParamsVO extends Params{
	
	public static final int FICHA_PERFIL=1;
	public static final int FICHA_DEFAULT=FICHA_PERFIL;
	public static final int FICHA_NUEVO=5;
	
	
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
	public int getFICHA_NUEVO() {
		return FICHA_NUEVO;
	}

}
