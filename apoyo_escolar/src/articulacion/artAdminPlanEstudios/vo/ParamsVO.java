/**
 * 
 */
package articulacion.artAdminPlanEstudios.vo;

import siges.common.vo.Params;

/**
 * 11/12/2007 
 * @author Latined
 * @version 1.2
 */
public class ParamsVO extends Params{
	public static final int FICHA_BORRAR=1;
	public static final int FICHA_DUPLICAR=2;
	public static final int FICHA_DEFAULT=FICHA_BORRAR;
	
	public static final int OPCION_TODO=1;
	public static final int OPCION_AREA=2;
	public static final int OPCION_ASIGNATURA=3;
	public static final int OPCION_PRUEBA=4;
	public static final int OPCION_ASIGNACION=5;
	public static final int OPCION_ESCALA=6;
	public static final int OPCION_GRUPO=7;
	public static final int OPCION_HORARIO=8;

	
	/**
	 * @return Return the fICHA_BORRAR.
	 */
	public int getFICHA_BORRAR() {
		return FICHA_BORRAR;
	}
	/**
	 * @return Return the fICHA_DEFAULT.
	 */
	public int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}
	/**
	 * @return Return the fICHA_DUPLICAR.
	 */
	public int getFICHA_DUPLICAR() {
		return FICHA_DUPLICAR;
	}
	/**
	 * @return Return the oPCION_AREA.
	 */
	public  int getOPCION_AREA() {
		return OPCION_AREA;
	}
	/**
	 * @return Return the oPCION_ASIGNACION.
	 */
	public  int getOPCION_ASIGNACION() {
		return OPCION_ASIGNACION;
	}
	/**
	 * @return Return the oPCION_ASIGNATURA.
	 */
	public  int getOPCION_ASIGNATURA() {
		return OPCION_ASIGNATURA;
	}
	/**
	 * @return Return the oPCION_ESCALA.
	 */
	public  int getOPCION_ESCALA() {
		return OPCION_ESCALA;
	}
	/**
	 * @return Return the oPCION_GRUPO.
	 */
	public  int getOPCION_GRUPO() {
		return OPCION_GRUPO;
	}
	/**
	 * @return Return the oPCION_HORARIO.
	 */
	public  int getOPCION_HORARIO() {
		return OPCION_HORARIO;
	}
	/**
	 * @return Return the oPCION_PRUEBA.
	 */
	public  int getOPCION_PRUEBA() {
		return OPCION_PRUEBA;
	}
	/**
	 * @return Return the oPCION_TODO.
	 */
	public  int getOPCION_TODO() {
		return OPCION_TODO;
	}

}
