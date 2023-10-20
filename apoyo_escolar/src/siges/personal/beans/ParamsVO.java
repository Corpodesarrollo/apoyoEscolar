/**
 * 
 */
package siges.personal.beans;

import siges.common.vo.Params;

/**
 * 14/09/2007 
 * @author Latined
 * @version 1.2
 */
public class ParamsVO extends Params{
	

	public  static final String PERFIL_RECTOR = "410";
	public static final String PERFIL_ADMIN_ACADEMICO = "421";
	public   String getPERFIL_ADMIN_ACADEMICO() {
		return PERFIL_ADMIN_ACADEMICO;
	}
	public   String getPERFIL_RECTOR() {
		return PERFIL_RECTOR;
	}
	
	 
}
