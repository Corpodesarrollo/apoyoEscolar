package siges.adminGrupo.vo;

import siges.common.vo.Params;

/**
 * Value Object para el manejo de parametros del modulo
 * 14/01/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class ParamsVO  extends Params{

	
	public static final int FICHA_GRUPOS=1;
	public static final int FICHA_DEFAULT=FICHA_GRUPOS; 
	public static final int CONST_TIPO_PERIODOS = 41;
	public static final int CONST_TIPO_COMBINACION = 40;
	public static final int CMD_DEFAULT = -1;
	public static final int CMD_AJAX = -1;
	
	public static final int CMD_JORN = 1;
	public static final int CMD_GRADO = 2;
	public static final int CMD_ESPACIO = 3;
	
	
	public   int getCMD_DEFAULT() {
		return CMD_DEFAULT;
	}
	public   int getCONST_TIPO_COMBINACION() {
		return CONST_TIPO_COMBINACION;
	}
	public   int getCONST_TIPO_PERIODOS() {
		return CONST_TIPO_PERIODOS;
	}
	
	public   int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}
	public int getFICHA_GRUPOS() {
		return FICHA_GRUPOS;
	}
	public int getCMD_AJAX() {
		return CMD_AJAX;
	}

	public  int getCMD_JORN() {
		return CMD_JORN;
	}
	public  int getCMD_GRADO() {
		return CMD_GRADO;
	}
	public  int getCMD_ESPACIO() {
		return CMD_ESPACIO;
	}
	

 
 }
