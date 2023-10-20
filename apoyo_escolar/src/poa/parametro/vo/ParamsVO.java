/**
 * 
 */
package poa.parametro.vo;

import siges.common.vo.Params;

/**
 * 14/02/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class ParamsVO extends Params{
	public static final int FICHA_AREA_GESTION=1;
	public static final int FICHA_LINEA_ACCION=2;
	public static final int FICHA_FUENTE_FINANCIACION=3;
	public static final int FICHA_TIPO_META=4;
	public static final int FICHA_UNIDAD_MEDIDA=5;
	public static final int FICHA_PROGRAMACION_FECHAS=6;
	public static final int FICHA_VIG=7;
	public static final int FICHA_DEFAULT=FICHA_AREA_GESTION;
	
	/**
	 * @return Return the fICHA_AREA_GESTION.
	 */
	public int getFICHA_AREA_GESTION() {
		return FICHA_AREA_GESTION;
	}
	/**
	 * @return Return the fICHA_DEFAULT.
	 */
	public int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}
	/**
	 * @return Return the fICHA_FUENTE_FINANCIACION.
	 */
	public int getFICHA_FUENTE_FINANCIACION() {
		return FICHA_FUENTE_FINANCIACION;
	}
	/**
	 * @return Return the fICHA_LINEA_ACCION.
	 */
	public int getFICHA_LINEA_ACCION() {
		return FICHA_LINEA_ACCION;
	}
	/**
	 * @return Return the fICHA_TIPO_META.
	 */
	public int getFICHA_TIPO_META() {
		return FICHA_TIPO_META;
	}
	/**
	 * @return Return the fICHA_UNIDAD_MEDIDA.
	 */
	public int getFICHA_UNIDAD_MEDIDA() {
		return FICHA_UNIDAD_MEDIDA;
	}
	/**
	 * @return Return the fICHA_PROGRAMACION_FECHAS.
	 */
	public final int getFICHA_PROGRAMACION_FECHAS() {
		return FICHA_PROGRAMACION_FECHAS;
	}
	public final int getFICHA_VIG() {
		return FICHA_VIG;
	}
	
}
