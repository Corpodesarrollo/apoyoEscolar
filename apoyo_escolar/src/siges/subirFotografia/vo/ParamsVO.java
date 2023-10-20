/**
 * 
 */
package siges.subirFotografia.vo;

import siges.common.vo.Params;

/**
 * 24/09/2008 
 * @author Latined
 * @version 1.2
 */
public class ParamsVO extends Params{
	public static final int FICHA_SUBIR=1;
	public static final int FICHA_SUBIR_PERSONAL=2;
	public static final int FICHA_DEFAULT=FICHA_SUBIR;
	public static final int FICHA_CAPTURA=3;
	
	/**
	 * @return Return the fICHA_DEFAULT.
	 */
	public int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}
	/**
	 * @return Return the fICHA_IMPORTAR.
	 */
	public int getFICHA_SUBIR() {
		return FICHA_SUBIR;
	}
	/**
	 * @return Returns the fICHA_SUBIR_PERSONAL.
	 */
	public int getFICHA_SUBIR_PERSONAL() {
		return FICHA_SUBIR_PERSONAL;
	}
	public int getFICHA_CAPTURA() {
		return FICHA_CAPTURA;
	}

}
