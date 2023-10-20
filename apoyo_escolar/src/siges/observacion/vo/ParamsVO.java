/**
 * 
 */
package siges.observacion.vo;

import siges.common.vo.Params;

/**
 * 25/11/2007 
 * @author Latined
 * @version 1.2
 */
public class ParamsVO extends Params{

	public static final int FICHA_OBSERVACION_PERIODO=1;
	public static final int FICHA_OBSERVACION_GRUPO=2;
	public static final int FICHA_OBSERVACION_ASIGNATURA=3;
	public static final int FICHA_OBSERVACION_ESTUDIANTE = 4;
	public static final int FICHA_AJAX=11;
	public static final int CMD_AJAX_GRADO=11;
	public static final int CMD_AJAX_GRUPO=12;
	public static final int CMD_AJAX_GRADO2=13;
	public static final int CMD_AJAX_GRUPO2=14;
	
	public static final int FICHA_DEFAULT=FICHA_OBSERVACION_PERIODO;
	
	/**
	 * @return Return the fICHA_DEFAULT.
	 */
	public int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}
	/**
	 * @return Return the fICHA_OBSERVACION_ASIGNATURA.
	 */
	public int getFICHA_OBSERVACION_ASIGNATURA() {
		return FICHA_OBSERVACION_ASIGNATURA;
	}
	/**
	 * @return Return the fICHA_OBSERVACION_GRUPO.
	 */
	public int getFICHA_OBSERVACION_GRUPO() {
		return FICHA_OBSERVACION_GRUPO;
	}
	/**
	 * @return Return the fICHA_OBSERVACION_PERIODO.
	 */
	public int getFICHA_OBSERVACION_PERIODO() {
		return FICHA_OBSERVACION_PERIODO;
	}
	/**
	 * @return Return the fICHA_AJAX.
	 */
	public int getFICHA_AJAX() {
		return FICHA_AJAX;
	}
	/**
	 * @return Return the cMD_AJAX_GRADO.
	 */
	public int getCMD_AJAX_GRADO() {
		return CMD_AJAX_GRADO;
	}
	/**
	 * @return Return the cMD_AJAX_GRUPO.
	 */
	public int getCMD_AJAX_GRUPO() {
		return CMD_AJAX_GRUPO;
	}
	public   int getFICHA_OBSERVACION_ESTUDIANTE() {
		return FICHA_OBSERVACION_ESTUDIANTE;
	}
	public   int getCMD_AJAX_GRADO2() {
		return CMD_AJAX_GRADO2;
	}
	public   int getCMD_AJAX_GRUPO2() {
		return CMD_AJAX_GRUPO2;
	}

}
