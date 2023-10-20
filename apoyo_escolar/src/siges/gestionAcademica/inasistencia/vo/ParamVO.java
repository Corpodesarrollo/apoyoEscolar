/**
 * 
 */
package siges.gestionAcademica.inasistencia.vo;

import siges.common.vo.Params;

/**
 * 3/09/2007 
 * @author Latined
 * @version 1.2
 */
public class ParamVO extends Params{
	public static final int FICHA_INASISTENCIA=1;
	public static final int FICHA_RETARDO=2;
	public static final int FICHA_SALIDA_TARDE=3;
	public static final int FICHA_INASISTENCIA_SIMPLE=4;
	public static final int FICHA_DEFAULT=FICHA_INASISTENCIA;
	public static final int CMD_AJAX_ASIGNATURA=11;
	public static final int QUINCENA_UNO = 1;
	public static final int QUINCENA_DOS = 2;
	/**
	 * @return Return the fICHA_DEFAULT.
	 */
	public int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}
	/**
	 * @return Return the fICHA_INASISTENCIA.
	 */
	public int getFICHA_INASISTENCIA() {
		return FICHA_INASISTENCIA;
	}
	/**
	 * @return Return the fICHA_RETARDO.
	 */
	public int getFICHA_RETARDO() {
		return FICHA_RETARDO;
	}
	/**
	 * @return Return the fICHA_SALIDA_TARDE.
	 */
	public int getFICHA_SALIDA_TARDE() {
		return FICHA_SALIDA_TARDE;
	}
	/**
	 * @return Return the cMD_AJAX_ASIGNATURA.
	 */
	public int getCMD_AJAX_ASIGNATURA() {
		return CMD_AJAX_ASIGNATURA;
	}
	/**
	 * @return Return the fICHA_INASISTENCIA_SIMPLE.
	 */
	public int getFICHA_INASISTENCIA_SIMPLE() {
		return FICHA_INASISTENCIA_SIMPLE;
	}
	public  int getQUINCENA_DOS() {
		return QUINCENA_DOS;
	}
	public  int getQUINCENA_UNO() {
		return QUINCENA_UNO;
	}
}
