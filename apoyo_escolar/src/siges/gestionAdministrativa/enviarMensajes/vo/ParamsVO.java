/**
 * 
 */
package siges.gestionAdministrativa.enviarMensajes.vo;

import siges.common.vo.Params;

/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class ParamsVO extends Params{

	
	public static final int FICHA_LISTA = 1;
	public static final int FICHA_MSJ = 2; 
	public static final int FICHA_DEFAULT = FICHA_LISTA;
	
	public static final String REP_MODULO = "25";
	
	public static final int CMD_NUEVO_MSJ = 10;
	
	public static final int CMD_ADD_CAMPO = 15;
	
 
	
	public static final int ADD_PER = 11;
	public static final int ADD_LOC = 22;
	public static final int ADD_INST = 33;
	public static final int ADD_SEDE = 44;
	public static final int ADD_JORD = 55;
	
	public static final int ELI_PER = 6;
	public static final int ELI_LOC = 7;
	public static final int ELI_INST = 8;
	public static final int ELI_SEDE = 9;
	public static final int ELI_JORD = 10;
	
	
	
	
	
	public static final int ENV_SED = 2;
	public static final int ENV_LOC = 3;
	public static final int ENV_INST = 4;
	
	
	
	public static int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}
	public   int getFICHA_LISTA() {
		return FICHA_LISTA;
	}
	public   int getFICHA_MSJ() {
		return FICHA_MSJ;
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
 
	public int getCMD_NUEVO_MSJ() {
		return CMD_NUEVO_MSJ;
	}
	
	public int getADD_PER() {
		return ADD_PER;
	}
	public int getADD_LOC() {
		return ADD_LOC;
	}
	public int getADD_INST() {
		return ADD_INST;
	}
	public int getADD_SEDE() {
		return ADD_SEDE;
	}
	public int getADD_JORD() {
		return ADD_JORD;
	}
	public int getELI_PER() {
		return ELI_PER;
	}
	public int getELI_LOC() {
		return ELI_LOC;
	}
	public int getELI_INST() {
		return ELI_INST;
	}
	public int getELI_SEDE() {
		return ELI_SEDE;
	}
	public int getELI_JORD() {
		return ELI_JORD;
	}
	
}
