/**
 * 
 */
package siges.gestionAdministrativa.RepPuestoEstudiante.vo;

import siges.common.vo.Params;

/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class ParamsVO extends Params{

	
	public static final int FICHA_REPORTES = 1;
	public static final int FICHA_REPORTES2 = 2;
	public static final int FICHA_REPORTES3 = 3;
	public static final int FICHA_REPORTES4 = 4;
	public static final int FICHA_REPORTES5 = 5;
	public static final int FICHA_REPORTES6 = 6;
	public static final int FICHA_REPORTES7 = 7;
	public static final int FICHA_DEFAULT = FICHA_REPORTES;
	
	public static final String REP_MODULO = "80";
	
	public static final int CMD_AJAX_MUN = 9;
	public static final int CMD_AJAX_INST = 6;
	public static final int CMD_AJAX_SEDE = 7;
	public static final int CMD_AJAX_JORD = 1;
	public static final int CMD_AJAX_METD = 2; 
	public static final int CMD_AJAX_GRAD = 3;
	public static final int CMD_AJAX_GRUP = 4;
	public static final int CMD_AJAX_EST = 5;
	public static final int CMD_AJAX_ASIG = 8;
	public static final int CMD_AJAX_RANGO = 10;
	
	public static final int CMD_AJAX_COLS = 11;
	public static final int CMD_AJAX_GRADOS = 12;
	

	
	public static final int TIPO_REPORTE_1 = 1;
	public static final int TIPO_REPORTE_2 = 2;
	public static final int TIPO_REPORTE_3 = 3;
	public static final int TIPO_REPORTE_4 = 4;
	public static final int TIPO_REPORTE_5 = 5;
	public static final int TIPO_REPORTE_6 = 6;
	public static final int TIPO_REPORTE_7 = 7;
	public static final int TIPO_REPORTE_8 = 8;
	public static final int TIPO_REPORTE_9 = 9;
	
	public static final String TIPO_REPORTE_1_ = "Reporte puesto por estudiante";
	public static final String TIPO_REPORTE_2_ = "Reporte puesto por Asignatura";
	public static final String TIPO_REPORTE_3_ = "Reporte puesto por Área";
	public static final String TIPO_REPORTE_4_ = "Reporte Mortalidad de colegios";
	public static final String TIPO_REPORTE_5_ = "Reporte indice de perdida";
	public static final String TIPO_REPORTE_6_ = "Reporte inasistencia";
	public static final String TIPO_REPORTE_7_ = "Reporte resumen inasistencia diaria por grupo";
	public static final String TIPO_REPORTE_8_ = "Reporte resumen inasistencia diaria por grupo";
	public static final String TIPO_REPORTE_9_ = "Reporte resumen inasistencia tiempo-jornada";
	
	public static final String TIPO_REPORTE_1_A = "Rep_puesto_x_Est";
	public static final String TIPO_REPORTE_2_A = "Rep_puesto_x_Asig";
	public static final String TIPO_REPORTE_3_A = "Rep_puesto_x_Area";
	public static final String TIPO_REPORTE_4_A = "Rep_Mortalidad";
	public static final String TIPO_REPORTE_5_A = "Rep_indice_perdida";
	public static final String TIPO_REPORTE_6_A = "Rep_list_inasistencia";
	public static final String TIPO_REPORTE_7_A = "Rep_resumen_inas_grupo";
	public static final String TIPO_REPORTE_8_A = "Rep_resumen_inas_grado";
	public static final String TIPO_REPORTE_9_A = "Rep_resumen_inas_sede";
	
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
	
	public static final int NIVEL_CENTRAL = 1;
	public static final int NIVEL_PROV = 2;
	public static final int NIVEL_MUN = 3;
	public static final int NIVEL_INST = 4;
	
	
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
	public int getTIPO_REPORTE_5() {
		return TIPO_REPORTE_5;
	}
	public  int getCMD_AJAX_MUN() {
		return CMD_AJAX_MUN;
	}
	public int getCMD_AJAX_RANGO() {
		return CMD_AJAX_RANGO;
	}
	
	public int getNIVEL_CENTRAL() {
		return NIVEL_CENTRAL;
	}
	public int getNIVEL_PROV() {
		return NIVEL_PROV;
	}
	public int getNIVEL_MUN() {
		return NIVEL_MUN;
	}
	public int getNIVEL_INST() {
		return NIVEL_INST;
	}
	
	
	
	public   int getFICHA_REPORTES2() {
		return FICHA_REPORTES2;
	}
	
	public   int getFICHA_REPORTES3() {
		return FICHA_REPORTES3;
	}
	
	public   int getFICHA_REPORTES4() {
		return FICHA_REPORTES4;
	}
	
	public   int getFICHA_REPORTES5() {
		return FICHA_REPORTES5;
	}
	
	public  int getCMD_AJAX_COLS() {
		return CMD_AJAX_COLS;
	}
	public  int getCMD_AJAX_GRADOS() {
		return CMD_AJAX_GRADOS;
	}
	
	public   int getFICHA_REPORTES6() {
		return FICHA_REPORTES6;
	}
	
	public   int getFICHA_REPORTES7() {
		return FICHA_REPORTES7;
	}
	 
}

