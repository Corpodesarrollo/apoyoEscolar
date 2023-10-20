/**
 * 
 */
package siges.reporte.beans;

import siges.common.vo.Params;

/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class ParamsVO extends Params{

	public static final int FICHA_CARME=1;
	
	public static final int CMD_AJAX_INST = 11;
	public static final int CMD_AJAX_LOC = 12;
	
	public static final int CMD_AJAX_SED = 5;
	public static final int CMD_AJAX_JORD = 1;
	public static final int CMD_AJAX_METD = 2; 
	public static final int CMD_AJAX_GRAD = 3;
	public static final int CMD_AJAX_GRUP = 4;
	
	public static final int FICHA_DEFAULT = 11;
	

	public   int getCMD_AJAX_INST() {
		return CMD_AJAX_INST;
	}

	public   int getFICHA_CARME() {
		return FICHA_CARME;
	}

	public   int getCMD_AJAX_LOC() {
		return CMD_AJAX_LOC;
	}

	public  int getCMD_AJAX_GRAD() {
		return CMD_AJAX_GRAD;
	}

	public  int getCMD_AJAX_GRUP() {
		return CMD_AJAX_GRUP;
	}

	public  int getCMD_AJAX_JORD() {
		return CMD_AJAX_JORD;
	}

	public  int getCMD_AJAX_METD() {
		return CMD_AJAX_METD;
	}

	public  int getCMD_AJAX_SED() {
		return CMD_AJAX_SED;
	}

	public  int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}
	}
