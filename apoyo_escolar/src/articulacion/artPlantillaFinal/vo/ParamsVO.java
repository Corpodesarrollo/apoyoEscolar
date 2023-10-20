/**
 * 
 */
package articulacion.artPlantillaFinal.vo;

import siges.common.vo.Params;

/**
 * 5/12/2007 
 * @author Latined
 * @version 1.2
 */
public class ParamsVO extends Params{
	public static final int FICHA_FILTRO=1;
	public static final int FICHA_FILTRO_IMP=2;
	public static final int FICHA_FILTRO_EVAL=3;
	public static final int FICHA_DEFAULT=FICHA_FILTRO_EVAL;
	public static final int PLANTILLA_FINAL=13;
	public static final int CMD_AJAX_GRUPO=11;
	public static final int CMD_AJAX_ESPECIALIDAD=12;
	public static final int CMD_AJAX_ASIGNATURA=13;
	
	/**
	 * @return Return the fICHA_DEFAULT.
	 */
	public int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}
	/**
	 * @return Return the fICHA_FILTRO.
	 */
	public int getFICHA_FILTRO() {
		return FICHA_FILTRO;
	}
	/**
	 * @return Return the pLANTILLA_FINAL.
	 */
	public int getPLANTILLA_FINAL() {
		return PLANTILLA_FINAL;
	}
	/**
	 * @return Return the fICHA_FILTRO_IMP.
	 */
	public int getFICHA_FILTRO_IMP() {
		return FICHA_FILTRO_IMP;
	}
	/**
	 * @return Return the cMD_AJAX_GRUPO.
	 */
	public int getCMD_AJAX_GRUPO() {
		return CMD_AJAX_GRUPO;
	}
	/**
	 * @return Return the cMD_AJAX_ESPECIALIDAD.
	 */
	public int getCMD_AJAX_ESPECIALIDAD() {
		return CMD_AJAX_ESPECIALIDAD;
	}
	/**
	 * @return Return the fICHA_FILTRO_EVAL.
	 */
	public int getFICHA_FILTRO_EVAL() {
		return FICHA_FILTRO_EVAL;
	}
	/**
	 * @return Return the CMD_AJAX_ASIGNATURA.
	 */
	public int getCMD_AJAX_ASIGNATURA() {
		return CMD_AJAX_ASIGNATURA;
	}

}
