/**
 * 
 */
package siges.gestionAdministrativa.integracionNomina.vo;

import siges.common.vo.Params;

/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class ParamsVO extends Params{

	public static final int FICHA_INTEGRACION_NOMINA = 13401;
	public static final int FICHA_DEFAULT = FICHA_INTEGRACION_NOMINA;
 
	
	public static final int CMD_DEFAULT = 1;
	public static final int CMD_CALL_NOMINA = 11;


	public  int getFICHA_INTEGRACION_NOMINA() {
		return FICHA_INTEGRACION_NOMINA;
	}


	public   int getCMD_CALL_NOMINA() {
		return CMD_CALL_NOMINA;
	}
	
	 
}
