package articulacion.repEstudiantesArt.vo;

import siges.common.vo.Params;

public class ParamsVO  extends Params{

	public static final int FICHA_EST_X_TUTOR=1;
	public static final int FICHA_HORARIO_DOCENTE=2;
	public static final int FICHA_HORARIO_ESPFISICO=3;
	public static final int FICHA_HORARIO_ESTUDIANTE=4;
	public static final int FICHA_DEFAULT=FICHA_EST_X_TUTOR;
	public static final int CMD_AJAX_ESTUDIANTE=11;
	public static final int CMD_AJAX_DOCENTE=16;
	public static final int CMD_AJAX_ESPECIALIDAD=12;
	public static final int CMD_AJAX_ASIGNATURA=13;
	public static final int CMD_AJAX_ESPFISICO=15;
	public static final int CMD_AJAX_GRUPO=14;
	
	/**
	 * @return Returns the fICHA_DEFAULT.
	 */
	public int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}
	/**
	 * @return Returns the fICHA_HORARIO_DOCENTE.
	 */
	public int getFICHA_HORARIO_DOCENTE() {
		return FICHA_HORARIO_DOCENTE;
	}
	
	
	/**
	 * @return Returns the fICHA_HORARIO_ESPFISICO.
	 */
	public int getFICHA_HORARIO_ESPFISICO() {
		return FICHA_HORARIO_ESPFISICO;
	}
	
	/**
	 * @return Returns the fICHA_HORARIO_ESPFISICO.
	 */
	public int getFICHA_HORARIO_ESTUDIANTE() {
		return FICHA_HORARIO_ESTUDIANTE;
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
	 * @return Returns the cMD_AJAX_ASIGNATURA2.
	 */
	public int getCMD_AJAX_ESPFISICO() {
		return CMD_AJAX_ESPFISICO;
	}
	/**
	 * @return Return the cMD_AJAX_DOCENTE2.
	 */
	public int getCMD_AJAX_ESTUDIANTE() {
		return CMD_AJAX_ESTUDIANTE;
	}
	public int getFICHA_EST_X_TUTOR() {
		return FICHA_EST_X_TUTOR;
	}
}
