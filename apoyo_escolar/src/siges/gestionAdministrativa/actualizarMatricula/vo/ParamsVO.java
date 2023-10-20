/**
 * 
 */
package siges.gestionAdministrativa.actualizarMatricula.vo;

import siges.common.vo.Params;

/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class ParamsVO extends Params{

	public static final int FICHA_ACTA=1;
	public static final int FICHA_DEFAULT=FICHA_ACTA;
	
	public static final int FICHA_ACTUALIZACION_MATR=1; 
	
	public static final int CMD_AJAX_INSTANCIA=20;
	public static final int CMD_AJAX_INSTANCIA0=21;
	public static final int CMD_AJAX_RANGO=22;
	public static final int CMD_AJAX_RANGO0=23;
	public static final int CMD_AJAX_COLEGIO=24;
	public static final int CMD_AJAX_COLEGIO0=25;
	public static final int CMD_AJAX_PARTICIPANTE=26;
	public static final int CMD_DESCARGAR=30;
	public static final int CMD_GENERAR=31;
	
	/*TIPOS DE PARAMETROS DE LA TABLA PRM_PARAMETRO*/	
	public static final int PAR_GENERAL=0;
	public static final int PAR_HILO=3;

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
	 * @return Return the cMD_DESCARGAR.
	 */
	public int getCMD_DESCARGAR() {
		return CMD_DESCARGAR;
	}

	/**
	 * @return Return the fICHA_ACTA.
	 */
	public int getFICHA_ACTA() {
		return FICHA_ACTA;
	}

	/**
	 * @return Return the fICHA_DEFAULT.
	 */
	public int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}

	/**
	 * @return Return the cMD_AJAX_PARTICIPANTE.
	 */
	public int getCMD_AJAX_PARTICIPANTE() {
		return CMD_AJAX_PARTICIPANTE;
	}

	public int getCMD_GENERAR() {
		return CMD_GENERAR;
	}

	public   int getFICHA_ACTUALIZACION_MATR() {
		return FICHA_ACTUALIZACION_MATR;
	}
}
