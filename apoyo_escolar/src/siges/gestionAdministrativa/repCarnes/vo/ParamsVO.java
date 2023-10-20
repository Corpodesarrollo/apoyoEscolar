/**
 * 
 */
package siges.gestionAdministrativa.repCarnes.vo;

import siges.common.vo.Params;

/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class ParamsVO extends Params{

	
	public static final int FICHA_REPORTES = 1;
	public static final int FICHA_DEFAULT = FICHA_REPORTES;
	
	public static final String REP_MODULO = "25";
	
	public static final int CMD_AJAX_INST = 6;
	public static final int CMD_AJAX_SEDE = 7;
	public static final int CMD_AJAX_JORD = 1;
	public static final int CMD_AJAX_METD = 2; 
	public static final int CMD_AJAX_GRAD = 3;
	public static final int CMD_AJAX_GRUP = 4;
	public static final int CMD_AJAX_EST = 5;
	public static final int CMD_AJAX_ASIG = 8;
	
	public static final int TIPO_COL_OFICIAL = 1;
	public static final int TIPO_COL_CONCESION = 2;
	public static final int TIPO_COL_CONVENIO = 3;
	
	public static final String TIPO_COL_OFICIAL_ = "OFICIAL";
	public static final String TIPO_COL_CONCESION_ = "CONCESION";
	public static final String TIPO_COL_CONVENIO_ = "CONVENIO";
	
	public static final int ZONA_URBANA = 1;
	public static final int ZONA_RURAL = 2;
	public static final int ZONA_TODAS = 0;
	
	public static final String ZONA_URBANA_ = "Urbana";
	public static final String ZONA_RURAL_ = "Rural";
	public static final String ZONA_TODAS_ = "Todas";
	
	public static final int TIPO_REPORTE_1 = 1;
	public static final int TIPO_REPORTE_2 = 2;
	public static final int TIPO_REPORTE_3 = 3;
	public static final int TIPO_REPORTE_4 = 4;
	
	
	
	public static final String TIPO_REPORTE_1_ = "Comparativo por Asignaturas";
	public static final String TIPO_REPORTE_2_ = "Comparativo por Asignatura y Grado/Grupo";
	public static final String TIPO_REPORTE_3_ = "Comparativo por Asignatura y Pernodo";
	public static final String TIPO_REPORTE_4_ = "Listado por Rendimiento Acadnmico";
	
	public static final String TIPO_REPORTE_1_A = "Comp_x_Asig";
	public static final String TIPO_REPORTE_2_A = "Comp_x_Asig_Gru";
	public static final String TIPO_REPORTE_3_A = "Comp_Asig_Per";
	public static final String TIPO_REPORTE_4_A = "List_Rend_Acad";
	
	public static final int TIPO_REPORTE_1_ORDEN1 = 1;
	public static final int TIPO_REPORTE_1_ORDEN2 = 2;
	
	public static final String TIPO_REPORTE_1_ORDEN1_ = "Asignatura";
	public static final String TIPO_REPORTE_1_ORDEN2_ = "Rendimiento";
	
	public static final int TIPO_REPORTE_ESCALA_NUM = 1;
	public static final int TIPO_REPORTE_ESCALA_CON = 2;
	public static final int TIPO_REPORTE_ESCALA_MEN = 3;
	
	public static final int ESTADO_REPORTE_COLA = -1;
	public static final int ESTADO_REPORTE_EJE = 0;
	public static final int ESTADO_REPORTE_GENOK = 1;
	public static final int ESTADO_REPORTE_NOGEN = 2;
	
	
	public static int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}
	public   int getFICHA_REPORTES() {
		return FICHA_REPORTES;
	}
	public   int getCMD_AJAX_EST() {
		return CMD_AJAX_EST;
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
	public  int getCMD_AJAX_INST() {
		return CMD_AJAX_INST;
	}
	public int getCMD_AJAX_SEDE() {
		return CMD_AJAX_SEDE;
	}
	public int getCMD_AJAX_ASIG() {
		return CMD_AJAX_ASIG;
	}
	public int getTIPO_REPORTE_1() {
		return TIPO_REPORTE_1;
	}
	public int getTIPO_REPORTE_2() {
		return TIPO_REPORTE_2;
	}
	public int getTIPO_REPORTE_3() {
		return TIPO_REPORTE_3;
	}
	public int getTIPO_REPORTE_4() {
		return TIPO_REPORTE_4;
	}
	 
}
