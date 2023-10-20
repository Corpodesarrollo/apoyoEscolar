/**
 * 
 */
package siges.importar.beans;

import siges.common.vo.Params;

/**
 * 14/09/2007 
 * @author Latined
 * @version 1.2
 */
public class ParamsVO extends Params{
	public static final int FICHA_COMPORTAMIENTO=10;
	public static final int FICHA_DEFAULT=FICHA_COMPORTAMIENTO;
	/**
	 * @return Return the fICHA_COMPORTAMIENTO.
	 */
	public int getFICHA_COMPORTAMIENTO() {
		return FICHA_COMPORTAMIENTO;
	}
	/**
	 * @return Return the fICHA_DEFAULT.
	 */
	public int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}

}
