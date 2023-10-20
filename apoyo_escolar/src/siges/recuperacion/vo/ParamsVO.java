/**
 * 
 */
package siges.recuperacion.vo;

import siges.common.vo.Params;

/**
 * 12/02/2009 
 * @author Latined
 * @version 1.2
 */
public class ParamsVO extends Params{
	public static final int FICHA_RECUPERACION_AREA=1;
	public static final int FICHA_RECUPERACION_ASIGNATURA=2;
	public static final int FICHA_DEFAULT=FICHA_RECUPERACION_AREA;
	
	public static final int CMD_AJAX_GRADO=11;
	public static final int CMD_AJAX_GRUPO=12;
	public static final int CMD_AJAX_ASIGNATURA=13;
	public static final int CMD_AJAX_AREA=14;

	public static final int ORDEN_APELLIDO=1;
	public static final int ORDEN_NOMBRE=2;
	public static final int ORDEN_IDENTIFICACION=3;
	
	public static final String ORDEN_APELLIDO_="Apellido";
	public static final String ORDEN_NOMBRE_="Nombre";
	public static final String ORDEN_IDENTIFICACION_="Identificacinn";
	/**
	 * @return Return the fICHA_DEFAULT.
	 */
	public int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}
	/**
	 * @return Return the fICHA_RECUPERACION_AREA.
	 */
	public int getFICHA_RECUPERACION_AREA() {
		return FICHA_RECUPERACION_AREA;
	}
	/**
	 * @return Return the fICHA_RECUPERACION_ASIGNATURA.
	 */
	public int getFICHA_RECUPERACION_ASIGNATURA() {
		return FICHA_RECUPERACION_ASIGNATURA;
	}
	/**
	 * @return Return the cMD_AJAX_AREA.
	 */
	public int getCMD_AJAX_AREA() {
		return CMD_AJAX_AREA;
	}
	/**
	 * @return Return the cMD_AJAX_ASIGNATURA.
	 */
	public int getCMD_AJAX_ASIGNATURA() {
		return CMD_AJAX_ASIGNATURA;
	}
	/**
	 * @return Return the cMD_AJAX_GRADO.
	 */
	public int getCMD_AJAX_GRADO() {
		return CMD_AJAX_GRADO;
	}
	/**
	 * @return Return the cMD_AJAX_GRUPO.
	 */
	public int getCMD_AJAX_GRUPO() {
		return CMD_AJAX_GRUPO;
	}
	/**
	 * @return Return the oRDEN_APELLIDO.
	 */
	public int getORDEN_APELLIDO() {
		return ORDEN_APELLIDO;
	}
	/**
	 * @return Return the oRDEN_IDENTIFICACION.
	 */
	public int getORDEN_IDENTIFICACION() {
		return ORDEN_IDENTIFICACION;
	}
	/**
	 * @return Return the oRDEN_NOMBRE.
	 */
	public int getORDEN_NOMBRE() {
		return ORDEN_NOMBRE;
	}

}
