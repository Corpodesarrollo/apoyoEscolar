/**
 * 
 */
package participacion.instancia.vo;

import siges.common.vo.Params;

/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class ParamsVO extends Params{

	public static final int FICHA_INSTANCIA=1;
	public static final int FICHA_OBJETIVO=2;
	public static final int FICHA_REPRESENTANTE=3;
	public static final int FICHA_DOCUMENTO=4;
	public static final int FICHA_RANGO=5;
	public static final int FICHA_DEFAULT=FICHA_INSTANCIA;
	
	public static final int CMD_AJAX_INSTANCIA=20;
	public static final int CMD_AJAX_INSTANCIA0=21;
	public static final int CMD_DESCARGAR=30;
	/**
	 * @return Return the fICHA_DEFAULT.
	 */
	public int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}
	/**
	 * @return Return the fICHA_DOCUMENTO.
	 */
	public int getFICHA_DOCUMENTO() {
		return FICHA_DOCUMENTO;
	}
	/**
	 * @return Return the fICHA_INSTANCIA.
	 */
	public int getFICHA_INSTANCIA() {
		return FICHA_INSTANCIA;
	}
	/**
	 * @return Return the fICHA_OBJETIVO.
	 */
	public int getFICHA_OBJETIVO() {
		return FICHA_OBJETIVO;
	}
	/**
	 * @return Return the fICHA_RANGO.
	 */
	public int getFICHA_RANGO() {
		return FICHA_RANGO;
	}
	/**
	 * @return Return the cMD_AJAX_INSTANCIA.
	 */
	public int getCMD_AJAX_INSTANCIA() {
		return CMD_AJAX_INSTANCIA;
	}
	/**
	 * @return Return the fICHA_REPRESENTANTE.
	 */
	public int getFICHA_REPRESENTANTE() {
		return FICHA_REPRESENTANTE;
	}
	/**
	 * @return Return the cMD_AJAX_INSTANCIA0.
	 */
	public int getCMD_AJAX_INSTANCIA0() {
		return CMD_AJAX_INSTANCIA0;
	}
	/**
	 * @return Return the cMD_DESCARGAR.
	 */
	public int getCMD_DESCARGAR() {
		return CMD_DESCARGAR;
	}
}
