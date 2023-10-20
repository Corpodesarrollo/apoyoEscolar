package siges.adminParamsAcad.vo;

import siges.common.vo.Params;

/**
 * Value Object para el manejo de parametros del modulo
 * 14/01/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class ParamsVO  extends Params{

 
	public static final int FICHA_NIVEL_EVAL = 1;
	public static final int FICHA_DEFAULT = FICHA_NIVEL_EVAL;
	public static final int FICHA_DIMENSION = 2;
	public static final int NUM_PERIODOS = 6;
	public static final int CONST_TIPO_PERIODOS = 41;
	public static final int CONST_TIPO_COMBINACION = 40;
	public static final int CMD_DEFAULT = -1;
	
	public   int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}
	public   int getFICHA_NIVEL_EVAL() {
		return FICHA_NIVEL_EVAL;
	}
	public  int getFichaDimension() {
		return FICHA_DIMENSION;
	}
 
 
	 
 }
