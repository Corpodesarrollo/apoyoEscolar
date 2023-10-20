/**
 * 
 */
package poa.reportes.vo;

import siges.common.vo.Params;

/**
 * Value Object de los parametros del modulo
 * 15/01/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class ParamsVO extends Params{

	public static final int FICHA_REPORTES=1;
	public static final int FICHA_DEFAULT=FICHA_REPORTES;
	public static final int CMD_AJAX_COLEGIO=11;
	public static final int CMD_AJAX_LINEAS=12;
	public static final int CMD_AJAX_DELETE_FILE=130;
	public static final int CMD_BUSCAR2=20;
	public static final int CMD_BUSCAR_SEGUIMIENTO=21;
	
	//TIPOS DE REPORTES
	public static final int CMD_REPORTE_NEC_AREA_GESTION=50;
	public static final int CMD_REPORTE_NEC_LIN_ACCION=51;
	public static final int CMD_REPORTE_GEN_SEGUIMIENTO=52;
	public static final int CMD_CONSULTA_GEN_ACT=53;
	public static final int CMD_CONSULTA_GEN_NEC=54;
	public static final int CMD_REPORTE_CUMP_METAS_LINEAS_ACCION=55;
	public static final int CMD_REPORTE_AVANCE_PON_AREAS=56;
	public static final int CMD_REPORTE_AVANCE_PON_LINEAS_ACCION=57;
	public static final int CMD_REPORTE_ESTADO_META_LINEAS_ACCION=58;
	public static final int CMD_REPORTE_POA=59;
	public static final int CMD_REPORTE_POA_CONSOLIDADO=60;
	public static final int CMD_POA=61;
	
	public int getCMD_POA() {
		return CMD_POA;
	}

	public int getCmdReportePoaConsolidado() {
		return CMD_REPORTE_POA_CONSOLIDADO;
	}

	/**
	 * @return Return the cMD_AJAX_COLEGIO.
	 */
	public int getCMD_AJAX_COLEGIO() {
		return CMD_AJAX_COLEGIO;
	}

	/**
	 * @return Return the fICHA_CONSULTA.
	 */
	public int getFICHA_REPORTES() {
		return FICHA_REPORTES;
	}

	/**
	 * @return Return the fICHA_DEFAULT.
	 */
	public int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}

	/**
	 * @return Return the cMD_BUSCAR2.
	 */
	public final int getCMD_BUSCAR2() {
		return CMD_BUSCAR2;
	}

	/**
	 * @return Return the cMD_BUSCAR_SEGUIMIENTO.
	 */
	public final int getCMD_BUSCAR_SEGUIMIENTO() {
		return CMD_BUSCAR_SEGUIMIENTO;
	}

	public int getCMD_AJAX_LINEAS() {
		return CMD_AJAX_LINEAS;
	}

	public  int getCMD_REPORTE_NEC_AREA_GESTION() {
		return CMD_REPORTE_NEC_AREA_GESTION;
	}

	public  int getCMD_REPORTE_NEC_LIN_ACCION() {
		return CMD_REPORTE_NEC_LIN_ACCION;
	}

	public  int getCMD_REPORTE_GEN_SEGUIMIENTO() {
		return CMD_REPORTE_GEN_SEGUIMIENTO;
	}

	public  int getCMD_CONSULTA_GEN_ACT() {
		return CMD_CONSULTA_GEN_ACT;
	}

	public  int getCMD_CONSULTA_GEN_NEC() {
		return CMD_CONSULTA_GEN_NEC;
	}

	public  int getCMD_REPORTE_CUMP_METAS_LINEAS_ACCION() {
		return CMD_REPORTE_CUMP_METAS_LINEAS_ACCION;
	}

	public  int getCMD_REPORTE_AVANCE_PON_AREAS() {
		return CMD_REPORTE_AVANCE_PON_AREAS;
	}

	public  int getCMD_REPORTE_AVANCE_PON_LINEAS_ACCION() {
		return CMD_REPORTE_AVANCE_PON_LINEAS_ACCION;
	}

	public int getCMD_AJAX_DELETE_FILE() {
		return CMD_AJAX_DELETE_FILE;
	}
}
