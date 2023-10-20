package articulacion.horarioEstudiante.vo;

import siges.common.vo.Params;

public class ParamsVO  extends Params{

	public static final int FICHA_PARAMETROS=1;
	public static final int FICHA_HORARIO=2;
	public static final int FICHA_HORARIO_GRUPO=3;
	public static final int FICHA_HORARIO_DOCENTE=4;
	public static final int FICHA_DEFAULT=FICHA_PARAMETROS;
	public static final int CMD_AJAX_DOCENTE=11;
	public static final int CMD_AJAX_ESPECIALIDAD=12;
	public static final int CMD_AJAX_ASIGNATURA=13;
	public static final int CMD_AJAX_ASIGNATURA2=15;
	public static final int CMD_AJAX_GRUPO=14;
	
	/**
	 * @return Returns the fICHA_DEFAULT.
	 */
	public int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}
	/**
	 * @return Returns the fICHA_HORARIO.
	 */
	public int getFICHA_HORARIO() {
		return FICHA_HORARIO;
	}
	/**
	 * @return Returns the fICHA_PARAMETROS.
	 */
	public int getFICHA_PARAMETROS() {
		return FICHA_PARAMETROS;
	}
	/**
	 * @return Returns the cMD_AJAX_ASIGNATURA.
	 */
	public int getCMD_AJAX_ASIGNATURA() {
		return CMD_AJAX_ASIGNATURA;
	}
	/**
	 * @return Returns the cMD_AJAX_DOCENTE.
	 */
	public int getCMD_AJAX_DOCENTE() {
		return CMD_AJAX_DOCENTE;
	}
	/**
	 * @return Returns the cMD_AJAX_GRUPO.
	 */
	public int getCMD_AJAX_GRUPO() {
		return CMD_AJAX_GRUPO;
	}
	/**
	 * @return Returns the cMD_AJAX_ESPECIALIDAD.
	 */
	public int getCMD_AJAX_ESPECIALIDAD() {
		return CMD_AJAX_ESPECIALIDAD;
	}
	/**
	 * @return Returns the fICHA_HORARIO_DOCENTE.
	 */
	public int getFICHA_HORARIO_DOCENTE() {
		return FICHA_HORARIO_DOCENTE;
	}
	/**
	 * @return Returns the fICHA_HORARIO_GRUPO.
	 */
	public int getFICHA_HORARIO_GRUPO() {
		return FICHA_HORARIO_GRUPO;
	}
	/**
	 * @return Returns the cMD_AJAX_ASIGNATURA2.
	 */
	public int getCMD_AJAX_ASIGNATURA2() {
		return CMD_AJAX_ASIGNATURA2;
	}
}
