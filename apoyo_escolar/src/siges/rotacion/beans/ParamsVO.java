/**
 * 
 */
package siges.rotacion.beans;

import siges.common.vo.Params;

/**
 * 11/08/2007 
 * @author Latined
 * @version 1.2
 */
public class ParamsVO extends Params{

	public static final int FICHA_ESTRUCTURA=10;
	public static final int FICHA_FIJAR_ESPACIO=20;
	public static final int FICHA_DOCENTE_GRADO=30;//YA NO SE USA
	public static final int FICHA_INHABILITAR_ESPACIO=40;
	public static final int FICHA_INHABILITAR_DOCENTE=50;
	public static final int FICHA_FIJAR_ASIGNATURA=60;
	public static final int FICHA_ESPACIO_DOCENTE=70;
	public static final int FICHA_PRIORIZAR=80;
	
	public static final int FICHA_GENERAR_HORARIO=90;
	public static final int FICHA_FIJAR_HORARIO=100;
	public static final int FICHA_BORRAR_HORARIO=110;
	public static final int FICHA_INHABILITAR_HORA=120;

	public static final int FICHA_DOCENTE_GRUPO_NUEVO=130;
	
	public static final int FICHA_ESPACIO_GRADO=140;
	
	public static final int CMD_AJAX_DOCENTE_GRUPO_DOC0=135;
	public static final int CMD_AJAX_DOCENTE_GRUPO_DOC=131;
	public static final int CMD_AJAX_DOCENTE_GRUPO_ASIG=132;
	public static final int CMD_AJAX_DOCENTE_GRUPO_GRA=133;
	public static final int CMD_AJAX_DOCENTE_GRUPO_GRU=134;
	
	public static final int CMD_AJAX_ESPACIO_GRADO_ESP=141;
	/**
	 * @return Return the fICHA_BORRAR_HORARIO.
	 */
	public int getFICHA_BORRAR_HORARIO() {
		return FICHA_BORRAR_HORARIO;
	}
	/**
	 * @return Return the fICHA_DOCENTE_GRADO.
	 */
	public int getFICHA_DOCENTE_GRADO() {
		return FICHA_DOCENTE_GRADO;
	}
	/**
	 * @return Return the fICHA_ESPACIO_DOCENTE.
	 */
	public int getFICHA_ESPACIO_DOCENTE() {
		return FICHA_ESPACIO_DOCENTE;
	}
	/**
	 * @return Return the fICHA_ESPACIO_GRADO.
	 */
	public int getFICHA_ESPACIO_GRADO() {
		return FICHA_ESPACIO_GRADO;
	}
	/**
	 * @return Return the fICHA_ESTRUCTURA.
	 */
	public int getFICHA_ESTRUCTURA() {
		return FICHA_ESTRUCTURA;
	}
	/**
	 * @return Return the fICHA_FIJAR_ASIGNATURA.
	 */
	public int getFICHA_FIJAR_ASIGNATURA() {
		return FICHA_FIJAR_ASIGNATURA;
	}
	/**
	 * @return Return the fICHA_FIJAR_ESPACIO.
	 */
	public int getFICHA_FIJAR_ESPACIO() {
		return FICHA_FIJAR_ESPACIO;
	}
	/**
	 * @return Return the fICHA_FIJAR_HORARIO.
	 */
	public int getFICHA_FIJAR_HORARIO() {
		return FICHA_FIJAR_HORARIO;
	}
	/**
	 * @return Return the fICHA_GENERAR_HORARIO.
	 */
	public int getFICHA_GENERAR_HORARIO() {
		return FICHA_GENERAR_HORARIO;
	}
	/**
	 * @return Return the fICHA_INHABILITAR_DOCENTE.
	 */
	public int getFICHA_INHABILITAR_DOCENTE() {
		return FICHA_INHABILITAR_DOCENTE;
	}
	/**
	 * @return Return the fICHA_INHABILITAR_ESPACIO.
	 */
	public int getFICHA_INHABILITAR_ESPACIO() {
		return FICHA_INHABILITAR_ESPACIO;
	}
	/**
	 * @return Return the fICHA_INHABILITAR_HORA.
	 */
	public int getFICHA_INHABILITAR_HORA() {
		return FICHA_INHABILITAR_HORA;
	}
	/**
	 * @return Return the fICHA_PRIORIZAR.
	 */
	public int getFICHA_PRIORIZAR() {
		return FICHA_PRIORIZAR;
	}
	/**
	 * @return Return the cMD_AJAX_DOCENTE_GRUPO_ASIG.
	 */
	public int getCMD_AJAX_DOCENTE_GRUPO_ASIG() {
		return CMD_AJAX_DOCENTE_GRUPO_ASIG;
	}
	/**
	 * @return Return the cMD_AJAX_DOCENTE_GRUPO_DOC.
	 */
	public int getCMD_AJAX_DOCENTE_GRUPO_DOC() {
		return CMD_AJAX_DOCENTE_GRUPO_DOC;
	}
	/**
	 * @return Return the cMD_AJAX_DOCENTE_GRUPO_GRA.
	 */
	public int getCMD_AJAX_DOCENTE_GRUPO_GRA() {
		return CMD_AJAX_DOCENTE_GRUPO_GRA;
	}
	/**
	 * @return Return the cMD_AJAX_DOCENTE_GRUPO_GRU.
	 */
	public int getCMD_AJAX_DOCENTE_GRUPO_GRU() {
		return CMD_AJAX_DOCENTE_GRUPO_GRU;
	}
	/**
	 * @return Return the fICHA_DOCENTE_GRUPO_NUEVO.
	 */
	public int getFICHA_DOCENTE_GRUPO_NUEVO() {
		return FICHA_DOCENTE_GRUPO_NUEVO;
	}
	/**
	 * @return Return the cMD_AJAX_DOCENTE_GRUPO_DOC0.
	 */
	public int getCMD_AJAX_DOCENTE_GRUPO_DOC0() {
		return CMD_AJAX_DOCENTE_GRUPO_DOC0;
	}
	/**
	 * @return Return the cMD_AJAX_ESPACIO_GRADO_ESP.
	 */
	public int getCMD_AJAX_ESPACIO_GRADO_ESP() {
		return CMD_AJAX_ESPACIO_GRADO_ESP;
	}
}
