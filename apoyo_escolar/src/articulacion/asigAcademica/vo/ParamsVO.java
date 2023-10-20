package articulacion.asigAcademica.vo;

import siges.common.vo.Params;

public class ParamsVO extends Params{
	
	public static final int FICHA_ASIGNACION=1;
	public static final int FICHA_DEFAULT=FICHA_ASIGNACION;
	public static final int CMD_AJAX_DOCENTE=11;
	public static final int CMD_AJAX_ESPECIALIDAD=12;
	public static final int CMD_AJAX_ASIGNATURA=13;

	public int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}

	/**
	 * @return Return the fICHA_ASIGNACION.
	 */
	public int getFICHA_ASIGNACION() {
		return FICHA_ASIGNACION;
	}

	/**
	 * @return Return the cMD_AJAX_DOCENTE.
	 */
	public int getCMD_AJAX_DOCENTE() {
		return CMD_AJAX_DOCENTE;
	}

	/**
	 * @return Return the cMD_AJAX_ASIGNATURA.
	 */
	public int getCMD_AJAX_ASIGNATURA() {
		return CMD_AJAX_ASIGNATURA;
	}

	/**
	 * @return Return the cMD_AJAX_ESPECIALIDAD.
	 */
	public int getCMD_AJAX_ESPECIALIDAD() {
		return CMD_AJAX_ESPECIALIDAD;
	}

}
