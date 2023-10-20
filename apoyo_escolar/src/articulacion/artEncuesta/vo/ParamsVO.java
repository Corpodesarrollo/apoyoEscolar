/**
 * 
 */
package articulacion.artEncuesta.vo;

import siges.common.vo.Params;

/**
 * 5/12/2007 
 * @author Latined
 * @version 1.2
 */
public class ParamsVO extends Params{
	public static final int FICHA_FILTRO=1;
	public static final int FICHA_FILTRO2=2;
	public static final int FICHA_FILTRO3=3;
	public static final int FICHA_FILTRO4=4;
	public static final int FICHA_DEFAULT=FICHA_FILTRO;
	public static final int CMD_AJAX_GRUPO=11;
	public static final int CMD_AJAX_GRADO=12;
	public static final int CMD_DESCARGAR=13;
	public static final int CMD_AJAX_COLEGIO=14;
	public static final int CMD_AJAX_SEDE=15;
	public static final int CMD_AJAX_JORNADA=16;
	
	public static final int moduloListado=48;
	public static final int moduloConsolidado=49;
	public static final int moduloConsolidadoGeneral=50;
	public static final int moduloMotivosPreferencias=51;
	
	/**
	 * @return Return the fICHA_DEFAULT.
	 */
	public int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}
	/**
	 * @return Return the fICHA_FILTRO.
	 */
	public int getFICHA_FILTRO() {
		return FICHA_FILTRO;
	}
	/**
	 * @return Return the cMD_AJAX_GRUPO.
	 */
	public int getCMD_AJAX_GRUPO() {
		return CMD_AJAX_GRUPO;
	}
	/**
	 * @return Return the cMD_AJAX_GRADO.
	 */
	public final int getCMD_AJAX_GRADO() {
		return CMD_AJAX_GRADO;
	}
	public int getCMD_DESCARGAR() {
		return CMD_DESCARGAR;
	}
	public  int getModuloConsolidado() {
		return moduloConsolidado;
	}
	public  int getModuloConsolidadoGneral() {
		return moduloConsolidadoGeneral;
	}
	public  int getModuloListado() {
		return moduloListado;
	}
	public  int getFICHA_FILTRO2() {
		return FICHA_FILTRO2;
	}
	public  int getCMD_AJAX_COLEGIO() {
		return CMD_AJAX_COLEGIO;
	}
	public int getCMD_AJAX_JORNADA() {
		return CMD_AJAX_JORNADA;
	}
	public int getCMD_AJAX_SEDE() {
		return CMD_AJAX_SEDE;
	}
	public int getFICHA_FILTRO3() {
		return FICHA_FILTRO3;
	}
	public int getModuloConsolidadoGeneral() {
		return moduloConsolidadoGeneral;
	}
	public int getModuloMotivosPreferencias() {
		return moduloMotivosPreferencias;
	}	
}
