/**
 * 
 */
package siges.gestionAdministrativa.boletin.vo;

import siges.common.vo.Params;

/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class ParamsVO extends Params{

	public static final int FICHA_ACTA=1;
	public static final int FICHA_PLANTILLA_BOLETIN = 8;
	public static final int FICHA_DEFAULT = FICHA_PLANTILLA_BOLETIN;
	
	public static final int CMD_AJAX_JORD = 1;
	public static final int CMD_AJAX_METD = 2; 
	public static final int CMD_AJAX_GRAD = 3;
	public static final int CMD_AJAX_GRUP = 4;
	public static final int CMD_AJAX_EST = 5;
	
	public static final int CMD_DEFAULT = 1;
	
	
	public   int getFICHA_ACTA() {
		return FICHA_ACTA;
	}
	public static int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}
	public   int getFICHA_PLANTILLA_BOLETIN() {
		return FICHA_PLANTILLA_BOLETIN;
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
	public   int getCMD_DEFAULT() {
		return CMD_DEFAULT;
	}
	 
}
