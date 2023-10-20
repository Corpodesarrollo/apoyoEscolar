package navegador.adminNavegador.vo;

import siges.common.vo.Params;

public class ParamsVO extends Params{
	
	public static final int FICHA_SECCION=1;
	public static final int FICHA_DEFAULT=FICHA_SECCION;
	
	

	/**
	 * @return Return the fICHA_SECCION.
	 */
	public final int getFICHA_SECCION() {
		return FICHA_SECCION;
	}



	/**
	 * @return Return the fICHA_DEFAULT.
	 */
	public final int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}

}
