/**
 * 
 */
package siges.plantilla.beans;

import siges.common.vo.Params;

/**
 * 14/09/2007 
 * @author Latined
 * @version 1.2
 */
public class ParamsVO extends Params{
	public static final int FICHA_COMPORTAMIENTO=10;
	public static final int FICHA_DEFAULT=FICHA_COMPORTAMIENTO;
	
	public static final int CMD_AJAX_DOCENTE=11;
	public static final int ESCALA_NUMERICA=1;
	public static final int ESCALA_CONCEPTUAL=2;
	public static final int ESCALA_PORCENTUAL=3;
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
	/**
	 * @return Return the cMD_AJAX_DOCENTE.
	 */
	public int getCMD_AJAX_DOCENTE() {
		return CMD_AJAX_DOCENTE;
	}

}
