package participacion.reportes.vo;

import siges.common.vo.Params;

/**
 * Value Object de parametros del modulo
 * 14/01/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class ParamsVO  extends Params{

	public static final int FICHA_LIDERES=1;
	public static final int FICHA_ACTAS=2;
	public static final int FICHA_FILTRO_ITEMS_CARACTERIZACION=12;

	public static final int FICHA_DEFAULT=FICHA_LIDERES;
	
	public static final int CMD_AJAX_INSTANCIA=20;
	public static final int CMD_AJAX_INSTANCIA0=21;
	public static final int CMD_AJAX_RANGO=22;
	public static final int CMD_AJAX_RANGO0=23;
	public static final int CMD_AJAX_COLEGIO=24;
	public static final int CMD_AJAX_COLEGIO0=25;

	


	
	/**
	 * @return Return the fICHA_FILTRO_ITEMS_CARACTERIZACION.
	 */
	public int getFICHA_FILTRO_ITEMS_CARACTERIZACION() {
		return FICHA_FILTRO_ITEMS_CARACTERIZACION;
	}

	/**
	 * @return Return the fICHA_LIDERES.
	 */
	public int getFICHA_LIDERES() {
		return FICHA_LIDERES;
	}

	/**
	 * @return Return the fICHA_DEFAULT.
	 */
	public int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}

	/**
	 * @return Return the cMD_AJAX_INSTANCIA.
	 */
	public int getCMD_AJAX_INSTANCIA() {
		return CMD_AJAX_INSTANCIA;
	}

	/**
	 * @return Return the cMD_AJAX_INSTANCIA0.
	 */
	public int getCMD_AJAX_INSTANCIA0() {
		return CMD_AJAX_INSTANCIA0;
	}

	/**
	 * @return Return the cMD_AJAX_RANGO.
	 */
	public int getCMD_AJAX_RANGO() {
		return CMD_AJAX_RANGO;
	}

	/**
	 * @return Return the cMD_AJAX_RANGO0.
	 */
	public int getCMD_AJAX_RANGO0() {
		return CMD_AJAX_RANGO0;
	}

	/**
	 * @return Return the cMD_AJAX_COLEGIO.
	 */
	public int getCMD_AJAX_COLEGIO() {
		return CMD_AJAX_COLEGIO;
	}

	/**
	 * @return Return the cMD_AJAX_COLEGIO0.
	 */
	public int getCMD_AJAX_COLEGIO0() {
		return CMD_AJAX_COLEGIO0;
	}

	public int getFICHA_ACTAS() {
		return FICHA_ACTAS;
	}


	
}
