/**
 * 
 */
package siges.importarFotografia.vo;

import siges.common.vo.Params;

/**
 * 24/09/2008 
 * @author Latined
 * @version 1.2
 */
public class ParamsVO extends Params{
	public static final int FICHA_IMPORTAR_ESTUDIANTE=1;
	public static final int FICHA_IMPORTAR_PERSONAL=2;
	public static final int FICHA_DEFAULT=FICHA_IMPORTAR_ESTUDIANTE;
	
	/**
	 * @return Return the fICHA_DEFAULT.
	 */
	public int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}

	/**
	 * @return Return the fICHA_IMPORTAR_ESTUDIANTE.
	 */
	public int getFICHA_IMPORTAR_ESTUDIANTE() {
		return FICHA_IMPORTAR_ESTUDIANTE;
	}

	/**
	 * @return Return the fICHA_IMPORTAR_PERSONAL.
	 */
	public int getFICHA_IMPORTAR_PERSONAL() {
		return FICHA_IMPORTAR_PERSONAL;
	}

}
