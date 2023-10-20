/**
 * 
 */
package poa.consulta.vo;

import siges.common.vo.Params;

/**
 * Value Object de los parametros del modulo
 * 15/01/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class ParamsVO extends Params{

	public static final int FICHA_CONSULTA=1;
	public static final int FICHA_DEFAULT=FICHA_CONSULTA;

	public static final int CMD_AJAX_COLEGIO=11;
	public static final int CMD_BUSCAR2=20;
	public static final int CMD_BUSCAR_SEGUIMIENTO=21;

	/**
	 * @return Return the cMD_AJAX_COLEGIO.
	 */
	public int getCMD_AJAX_COLEGIO() {
		return CMD_AJAX_COLEGIO;
	}

	/**
	 * @return Return the fICHA_CONSULTA.
	 */
	public int getFICHA_CONSULTA() {
		return FICHA_CONSULTA;
	}

	/**
	 * @return Return the fICHA_DEFAULT.
	 */
	public int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}

	/**
	 * @return Return the cMD_BUSCAR2.
	 */
	public final int getCMD_BUSCAR2() {
		return CMD_BUSCAR2;
	}

	/**
	 * @return Return the cMD_BUSCAR_SEGUIMIENTO.
	 */
	public final int getCMD_BUSCAR_SEGUIMIENTO() {
		return CMD_BUSCAR_SEGUIMIENTO;
	}
}
