/**
 * 
 */
package siges.indicadores.vo;

import siges.common.vo.Params;

/**
 * 22/01/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class ParamsVO extends Params{

	public static final int FICHA_LOGRO=1;
	public static final int FICHA_DESCRIPTOR=2;
	public static final int FICHA_DEFAULT=FICHA_LOGRO;

	public static final int CMD_AJAX_ASIGNATURA=10;
	public static final int CMD_AJAX_AREA=11;
	public static final int CMD_AJAX_DOCENTE=12;
	public static final int CMD_AJAX_ASIGNATURA2=13;
	public static final int CMD_AJAX_AREA2=14;
	public static final int CMD_AJAX_DOCENTE2=15;
	public static final int CMD_AJAX_GRADO=16;
	public static final int CMD_AJAX_GRADO2=17;
	/**
	 * @return Return the cMD_AJAX_AREA.
	 */
	public int getCMD_AJAX_AREA() {
		return CMD_AJAX_AREA;
	}
	/**
	 * @return Return the cMD_AJAX_ASIGNATURA.
	 */
	public int getCMD_AJAX_ASIGNATURA() {
		return CMD_AJAX_ASIGNATURA;
	}
	/**
	 * @return Return the cMD_AJAX_DOCENTE.
	 */
	public int getCMD_AJAX_DOCENTE() {
		return CMD_AJAX_DOCENTE;
	}
	/**
	 * @return Return the fICHA_DEFAULT.
	 */
	public int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}
	/**
	 * @return Return the fICHA_DESCRIPTOR.
	 */
	public int getFICHA_DESCRIPTOR() {
		return FICHA_DESCRIPTOR;
	}
	/**
	 * @return Return the fICHA_LOGRO.
	 */
	public int getFICHA_LOGRO() {
		return FICHA_LOGRO;
	}
	/**
	 * @return Return the cMD_AJAX_AREA2.
	 */
	public int getCMD_AJAX_AREA2() {
		return CMD_AJAX_AREA2;
	}
	/**
	 * @return Return the cMD_AJAX_ASIGNATURA2.
	 */
	public int getCMD_AJAX_ASIGNATURA2() {
		return CMD_AJAX_ASIGNATURA2;
	}
	/**
	 * @return Return the cMD_AJAX_DOCENTE2.
	 */
	public int getCMD_AJAX_DOCENTE2() {
		return CMD_AJAX_DOCENTE2;
	}
	/**
	 * @return Return the cMD_AJAX_GRADO.
	 */
	public int getCMD_AJAX_GRADO() {
		return CMD_AJAX_GRADO;
	}
	/**
	 * @return Return the cMD_AJAX_GRADO2.
	 */
	public int getCMD_AJAX_GRADO2() {
		return CMD_AJAX_GRADO2;
	}
}
