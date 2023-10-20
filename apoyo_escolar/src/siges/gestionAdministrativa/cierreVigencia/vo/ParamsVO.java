/**
 * 
 */
package siges.gestionAdministrativa.cierreVigencia.vo;

import siges.common.vo.Params;

/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class ParamsVO extends Params{

	public static final int FICHA_ACTA=1;
	public static final int FICHA_DEFAULT=FICHA_ACTA;
	
	public static final int FICHA_CIERRE_VIG=1;
	public static final int FICHA_DUPLI_PLAN_ESTUD=2;
	public static final int FICHA_CONSULTA_CIERRE=3;
	public static final int FICHA_CONSULTAR_CIERRE=4;
	
	public static final int CMD_AJAX_INSTANCIA=20;
	public static final int CMD_AJAX_INSTANCIA0=21;
	public static final int CMD_AJAX_RANGO=22;
	public static final int CMD_AJAX_RANGO0=23;
	public static final int CMD_AJAX_COLEGIO=24;
	public static final int CMD_AJAX_COLEGIO0=25;
	public static final int CMD_AJAX_PARTICIPANTE=26;
	public static final int CMD_DESCARGAR=30;
	public static final int CMD_GENERAR=31;
	public static final int CMD_AJAX_DETCIERRE=8;
	public static final int CMD_AJAX_CIERRE=9;
	public static final int HILO_TIPO_CIERRE_VIG = 2;
	
	
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

	public   int getFICHA_CIERRE_VIG() {
		return FICHA_CIERRE_VIG;
	}

	public   int getFICHA_DUPLI_PLAN_ESTUD() {
		return FICHA_DUPLI_PLAN_ESTUD;
	}

	public   int getFICHA_CONSULTA_CIERRE() {
		return FICHA_CONSULTA_CIERRE;
	}
}
