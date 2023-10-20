/**
 * 
 */
package siges.gestionAcademica.regNotasVigencia.vo;

import siges.common.vo.Params;

/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class ParamsVO extends Params{

	public static final int FICHA_ACTA=-11;
	
	
	public static final int FICHA_FILTRO_REG_NOTAS=-11;
	public static final int FICHA_NUEVO_REG_NOTAS=-32;
	public static final int FICHA_DEFAULT=FICHA_FILTRO_REG_NOTAS;
  
	
	public static final int CMD_DESCARGAR=30;
	public static final int CMD_GENERAR=31;
	public static final int HILO_TIPO_CIERRE_VIG = 2;
	
	/*TIPOS DE PARAMETROS DE LA TABLA PRM_PARAMETRO*/	
	public static final int PAR_GENERAL=0;
	public static final int PAR_HILO=3;

 

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
 

	public int getCMD_GENERAR() {
		return CMD_GENERAR;
	}

	public   int getFICHA_FILTRO_REG_NOTAS() {
		return FICHA_FILTRO_REG_NOTAS;
	}

	public   int getFICHA_NUEVO_REG_NOTAS() {
		return FICHA_NUEVO_REG_NOTAS;
	}

	public   int getCMD_AJAX_GRAD() {
		return CMD_AJAX_GRAD;
	}

	public   int getCMD_AJAX_GRUP() {
		return CMD_AJAX_GRUP;
	}

	public   int getCMD_AJAX_JORD() {
		return CMD_AJAX_JORD;
	}

	public   int getCMD_AJAX_METD() {
		return CMD_AJAX_METD;
	}

	public   int getCMD_AJAX_SED() {
		return CMD_AJAX_SED;
	}

	public   int getHILO_TIPO_CIERRE_VIG() {
		return HILO_TIPO_CIERRE_VIG;
	}

	public   int getPAR_GENERAL() {
		return PAR_GENERAL;
	}

	public   int getPAR_HILO() {
		return PAR_HILO;
	}

 

 
}
