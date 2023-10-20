package poa.solicitudCambios.vo;

import siges.common.vo.Params;

/**
 * Value Object de parametros del modulo
 * 14/01/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class ParamsVO  extends Params{

	public static final int FICHA_CAMBIO=1;
	public static final int FICHA_ACTIVIDAD_SIN=2;
	public static final int FICHA_DEFAULT=FICHA_CAMBIO;

	public static final int CMD_APROBAR_COLEGIO=10;
	public static final int CMD_APROBAR_SED=11;
	public static final int CMD_RECHAZAR_SED=12;
	public static final int CMD_AJAX_LINEA=15;
	public static final int CMD_AJAX_AREA=16;
	
	public static final int ESTADO_NUEVO=-1;
	public static final int ESTADO_ENVIADO=0;
	public static final int ESTADO_APROBADO=1;
	public static final int ESTADO_RECHAZADO=2;
	
	public static final String ESTADO_NUEVO_="Sin enviar";
	public static final String ESTADO_ENVIADO_="Enviado";
	public static final String ESTADO_APROBADO_="Aprobado";
	public static final String ESTADO_RECHAZADO_="Rechazado";

	public static final String TABLA_ACTIVIDAD="POA_PLANACTIVIDAD";
	public static final String TABLA_FINANCIACION="POA_PLANACT_FTEFIN";

	

	/**
	 * @return Returns the fICHA_DEFAULT.
	 */
	public int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}
	/**
	 * @return Returns the FICHA_ACTIVIDAD.
	 */
	public int getFICHA_CAMBIO() {
		return FICHA_CAMBIO;
	}

	/**
	 * @return Return the cMD_AJAX_LINEA.
	 */
	public int getCMD_AJAX_LINEA() {
		return CMD_AJAX_LINEA;
	}
	/**
	 * @return Return the tABLA_ACTIVIDAD.
	 */
	public static String getTABLA_ACTIVIDAD() {
		return TABLA_ACTIVIDAD;
	}
	/**
	 * @return Return the tABLA_FINANCIACION.
	 */
	public static String getTABLA_FINANCIACION() {
		return TABLA_FINANCIACION;
	}
	/**
	 * @return Return the cMD_APROBAR_COLEGIO.
	 */
	public int getCMD_APROBAR_COLEGIO() {
		return CMD_APROBAR_COLEGIO;
	}
	/**
	 * @return Return the cMD_APROBAR_SED.
	 */
	public int getCMD_APROBAR_SED() {
		return CMD_APROBAR_SED;
	}
	/**
	 * @return Return the cMD_RECHAZAR_SED.
	 */
	public int getCMD_RECHAZAR_SED() {
		return CMD_RECHAZAR_SED;
	}
	/**
	 * @return Return the fICHA_ACTIVIDAD_SIN.
	 */
	public int getFICHA_ACTIVIDAD_SIN() {
		return FICHA_ACTIVIDAD_SIN;
	}
	/**
	 * @return Return the cMD_AJAX_AREA.
	 */
	public int getCMD_AJAX_AREA() {
		return CMD_AJAX_AREA;
	}
	public int getESTADO_ENVIADO() {
		return ESTADO_ENVIADO;
	}
	public int getESTADO_APROBADO() {
		return ESTADO_APROBADO;
	}
	public int getESTADO_RECHAZADO() {
		return ESTADO_RECHAZADO;
	}
}
