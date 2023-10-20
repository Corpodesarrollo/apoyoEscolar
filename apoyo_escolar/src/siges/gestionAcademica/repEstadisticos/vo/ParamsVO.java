/**
 * 
 */
package siges.gestionAcademica.repEstadisticos.vo;

import siges.common.vo.Params;

/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class ParamsVO extends Params{

	
	public static final int FICHA_REP_ASISTENCIA = 1301;
	public static final int FICHA_DEFAULT = FICHA_REP_ASISTENCIA;
	public static final int FICHA_REP_ASIGNATURAS = 1302;
	public static final int FICHA_REP_AREAS = 1303;
	public static final int FICHA_REP_LOGROS = 1304;
	public static final int FICHA_REP_DESCRIPTOR = 1305;
	public static final int FICHA_REP_LOGROS_GRADO = 1306;
	
	
	public static final int CMD_AJAX_EST = 15;
	public static final int CMD_GRUPO_AREA = 16;
	
	
	public static final int MODULO_RepEstadisticos=16;
	
	public static final String NOMBRE_REP_ASISTENCIA ="Rep_Asist";
	public static final String NOMBRE_REP_ASIG ="Rep_Asig";
	public static final String NOMBRE_REP_AREA ="Rep_Area";
	public static final String NOMBRE_REP_LOGRO ="Rep_Logros";
	public static final String NOMBRE_REP_DESCPT ="Rep_Descpt";
	public static final String NOMBRE_REP_LOGRO_GRAD ="Rep_LogroGrad";
	
	
	//Parametros orden
	public static final int ORDEN_APELLIDO = 1;
	public static final String ORDEN_APELLIDO_ = "Apellido";
	public static final int ORDEN_IDENTIFICACION = 2;
	public static final String ORDEN_IDENTIFICACION_ = "Identificacinn";
	public static final int FICHA_NOMBRE = 3;
	public static final String FICHA_NOMBRE_ = "Nombre";
	public static final int FICHA_COIDGO = 4;
	public static final String FICHA_COIDGO_ = "Codngo";
	
	public static final String REP_MODULO = "25";



	public   int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}



	public   int getFICHA_REP_AREAS() {
		return FICHA_REP_AREAS;
	}



	public   int getFICHA_REP_ASIGNATURAS() {
		return FICHA_REP_ASIGNATURAS;
	}



	public   int getFICHA_REP_ASISTENCIA() {
		return FICHA_REP_ASISTENCIA;
	}



	public   int getFICHA_REP_DESCRIPTOR() {
		return FICHA_REP_DESCRIPTOR;
	}



	public   int getFICHA_REP_LOGROS() {
		return FICHA_REP_LOGROS;
	}



	public  String getREP_MODULO() {
		return REP_MODULO;
	}



	public  int getCMD_AJAX_EST() {
		return CMD_AJAX_EST;
	}



	public  int getFICHA_REP_LOGROS_GRADO() {
		return FICHA_REP_LOGROS_GRADO;
	}



	public   int getCMD_AJAX_AREA() {
		return CMD_AJAX_AREA;
	}



	public   int getCMD_GRUPO_AREA() {
		return CMD_GRUPO_AREA;
	}
		 
}
