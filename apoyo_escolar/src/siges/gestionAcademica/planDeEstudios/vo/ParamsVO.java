/**
 * 
 */
package siges.gestionAcademica.planDeEstudios.vo;

import siges.common.vo.Params;

/**
 * 27/10/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class ParamsVO extends Params{

	public static final int FICHA_PLAN=1;
	public static final int FICHA_AREA=2;
	public static final int FICHA_ASIGNATURA=3;
	public static final int FICHA_DEFAULT=FICHA_PLAN;

	public static final int CMD_AJAX_AREA=11;
	public static final int CMD_AJAX_AREA_BASE=12;
	public static final int CMD_AJAX_ASIGNATURA_BASE=13;
	public static final int CMD_AJAX_GRADO=14;
	public static final int CMD_AJAX_ASIGNATURA=15;
	public static final int CMD_AJAX_AREA0=16;
	public static final int CMD_DEFAULT=-1;

	
	
	public static final String ESTADO_ACTIVO="A";
	public static final String ESTADO_INACTIVO="I";
	
	public static final String ESTADO_ACTIVO_NOM="Activa";
	public static final String ESTADO_INACTIVO_NOM="Inactiva";

	/**
	 * @return Return the cMD_AJAX_AREA.
	 */
	public int getCMD_AJAX_AREA() {
		return CMD_AJAX_AREA;
	}

	/**
	 * @return Return the fICHA_AREA.
	 */
	public int getFICHA_AREA() {
		return FICHA_AREA;
	}

	/**
	 * @return Return the fICHA_ASIGNATURA.
	 */
	public int getFICHA_ASIGNATURA() {
		return FICHA_ASIGNATURA;
	}

	/**
	 * @return Return the fICHA_DEFAULT.
	 */
	public int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}

	/**
	 * @return Return the fICHA_PLAN.
	 */
	public int getFICHA_PLAN() {
		return FICHA_PLAN;
	}

	/**
	 * @return Return the cMD_AJAX_AREA_BASE.
	 */
	public int getCMD_AJAX_AREA_BASE() {
		return CMD_AJAX_AREA_BASE;
	}

	/**
	 * @return Return the cMD_AJAX_ASIGNATURA_BASE.
	 */
	public int getCMD_AJAX_ASIGNATURA_BASE() {
		return CMD_AJAX_ASIGNATURA_BASE;
	}

	/**
	 * @return Return the cMD_AJAX_GRADO.
	 */
	public int getCMD_AJAX_GRADO() {
		return CMD_AJAX_GRADO;
	}

	public int getCMD_AJAX_ASIGNATURA() {
		return CMD_AJAX_ASIGNATURA;
	}

	public int getCMD_AJAX_AREA0() {
		return CMD_AJAX_AREA0;
	}

	public  String getESTADO_ACTIVO() {
		return ESTADO_ACTIVO;
	}

	public  String getESTADO_INACTIVO() {
		return ESTADO_INACTIVO;
	}

	public static String getESTADO_ACTIVO_NOM() {
		return ESTADO_ACTIVO_NOM;
	}

	public static String getESTADO_INACTIVO_NOM() {
		return ESTADO_INACTIVO_NOM;
	}
	
}
