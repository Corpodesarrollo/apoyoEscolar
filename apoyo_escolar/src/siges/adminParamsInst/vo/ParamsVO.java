package siges.adminParamsInst.vo;

//	VERSION		FECHA			AUTOR			DESCRIPCION
//		1.0		18/04/2020		JORGE CAMACHO	Se agregan cuatro constantes para los PARAMETROS DE COLEGIO

import siges.common.vo.Params;

/**
 * Value Object para el manejo de parametros del modulo
 * 14/01/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class ParamsVO  extends Params{

	public static final int FICHA_PERIODO=1; 
	public static final int FICHA_ESCL_CONCEPT = 2;
	public static final int FICHA_ESCL_NUM = 3;
	public static final int FICHA_INST_NIVEL_EVAL = 4;
	public static final int FICHA_PER_PROG_FECHAS = 40;
	public static final int FICHA_PONDERACION_POR_PERIODO=110;
	
	
	public static final int FICHA_DEFAULT=FICHA_PERIODO;
	
	
	public static final int NUM_PERIODOS = 6;
	public static final int CONST_TIPO_PERIODOS = 41;
	public static final int CONST_TIPO_COMBINACION = 40;
	public static final int CMD_DEFAULT = -1;
	public static final int CMD_DEFAULT2 = 6;
	public static final int CMD_NUEVO = 112;
	
	public static final int CMD_AJAX_JORD = 3;
	public static final int CMD_AJAX_METD = 4;
	public static final int CMD_AJAX_NVL = 5;
	public static final int CMD_AJAX_GRAD = 6;
	
	
	
	
	public static final int NVLEVA_INST = 1;
	public static final int NVLEVA_SED = 2;
	public static final int NVLEVA_JORN = 3;
	public static final int NVLEVA_METD = 4;
	public static final int NVLEVA_NVL = 5;
	public static final int NVLEVA_GRD = 6;
	public static final int NVLEVA_SED_JORN = 7;
	public static final int NVLEVA_SED_METD = 8;
	public static final int NVLEVA_SED_NVL = 9;
	public static final int NVLEVA_SED_GRD = 10;
	public static final int NVLEVA_SED_JORN_METD = 11;
	public static final int NVLEVA_SED_JORN_NVL = 12;
	public static final int NVLEVA_SED_JORN_GRD = 13;
	public static final int NVLEVA_SED_METD_NVL = 14;
	public static final int NVLEVA_SED_METD_GRD = 15;
	public static final int NVLEVA_SED_NVL_GRD = 16;
	public static final int NVLEVA_SED_JORD_METD_NVL = 17;
	public static final int NVLEVA_SED_JORD_METD_GRD = 18;
	public static final int NVLEVA_SED_METD_NVL_GRD = 19;
	public static final int NVLEVA_SED_JORD_METD_NVL_GRD = 20;
	
	public static final int CORREO_SEC = 5;
	
	public static final String SUBTIT_BOLETIN = "LOGROS Y DESCRIPTORES";
	
	public static final int PROMEDIO_ARITMETICO = 2;
	public static final int CRITERIO_DOCENTE = 1;
	public static final int PONDERACION_X_PERIODO = 3;
	public static final int ACUMULATIVO = 4;

	
	
	
	
	public   int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}
	
	public   int getFICHA_PERIODO() {
		return FICHA_PERIODO;
	}
	
	public   int getFICHA_ESCL_CONCEPT() {
		return FICHA_ESCL_CONCEPT;
	}
	
	public   int getFICHA_ESCL_NUM() {
		return FICHA_ESCL_NUM;
	}
	
	public   int getFICHA_INST_NIVEL_EVAL() {
		return FICHA_INST_NIVEL_EVAL;
	}
	
	public  int getFICHA_PONDERACION_POR_PERIODO() {
		return FICHA_PONDERACION_POR_PERIODO;
	}
	
	public  int getCMD_NUEVO() {
		return CMD_NUEVO;
	}
	
	public  int getCMD_DEFAULT2() {
		return CMD_DEFAULT2;
	}
	
	public   int getNVLEVA_GRD() {
		return NVLEVA_GRD;
	}
	
	public   int getNVLEVA_INST() {
		return NVLEVA_INST;
	}
	
	public   int getNVLEVA_JORN() {
		return NVLEVA_JORN;
	}
	
	public   int getNVLEVA_METD() {
		return NVLEVA_METD;
	}
 
	public   int getNVLEVA_NVL() {
		return NVLEVA_NVL;
	}
	
	public   int getNVLEVA_SED() {
		return NVLEVA_SED;
	}
	
	public   int getNVLEVA_SED_GRD() {
		return NVLEVA_SED_GRD;
	}
	
	public   int getNVLEVA_SED_JORD_METD_GRD() {
		return NVLEVA_SED_JORD_METD_GRD;
	}
	
	public   int getNVLEVA_SED_JORD_METD_NVL() {
		return NVLEVA_SED_JORD_METD_NVL;
	}
	
	public   int getNVLEVA_SED_JORD_METD_NVL_GRD() {
		return NVLEVA_SED_JORD_METD_NVL_GRD;
	}
	
	public   int getNVLEVA_SED_JORN() {
		return NVLEVA_SED_JORN;
	}
	
	public   int getNVLEVA_SED_JORN_GRD() {
		return NVLEVA_SED_JORN_GRD;
	}
	
	public   int getNVLEVA_SED_JORN_METD() {
		return NVLEVA_SED_JORN_METD;
	}
	
	public   int getNVLEVA_SED_JORN_NVL() {
		return NVLEVA_SED_JORN_NVL;
	}
	
	public   int getNVLEVA_SED_METD() {
		return NVLEVA_SED_METD;
	}
	
	public   int getNVLEVA_SED_METD_GRD() {
		return NVLEVA_SED_METD_GRD;
	}
	
	public   int getNVLEVA_SED_METD_NVL() {
		return NVLEVA_SED_METD_NVL;
	}
	
	public   int getNVLEVA_SED_NVL() {
		return NVLEVA_SED_NVL;
	}
	
	public   int getNVLEVA_SED_NVL_GRD() {
		return NVLEVA_SED_NVL_GRD;
	}
	
	public   int getCMD_AJAX_GRAD() {
		return CMD_AJAX_GRAD;
	}
	
	public   int getCMD_AJAX_JORD() {
		return CMD_AJAX_JORD;
	}
	
	public   int getCMD_AJAX_METD() {
		return CMD_AJAX_METD;
	}
	
	public   int getCMD_AJAX_NVL() {
		return CMD_AJAX_NVL;
	}
	
	public   int getNVLEVA_SED_METD_NVL_GRD() {
		return NVLEVA_SED_METD_NVL_GRD;
	}
	
	public int getFICHA_PER_PROG_FECHAS() {
		return FICHA_PER_PROG_FECHAS;
	}
	
	public String getSUBTIT_BOLETIN() {
		return SUBTIT_BOLETIN;
	}

	public static int getPromedioAritmetico() {
		return PROMEDIO_ARITMETICO;
	}

	public static int getCriterioDocente() {
		return CRITERIO_DOCENTE;
	}

	public static int getPonderacionXPeriodo() {
		return PONDERACION_X_PERIODO;
	}

	public static int getAcumulativo() {
		return ACUMULATIVO;
	}
 
	 
 
 
 
	 
 }
