/**
 * 
 */
package poa.common.vo;

import siges.common.vo.Params;

/**
 * Value Object para el manejo de parametros de POA
 * 13/01/2009 
 * @author Athenea
 * @version 1.2
 */
public class ParamPOA extends Params{
	/**
	 * No hay registro de poa para el colegio indicado
	 */
	public static final int ESTADO_POA_NINGUNO=-1;
	/**
	 * Estado de aprobacinn por parte del colegio
	 */
	public static final int ESTADO_POA_APROBADO_COLEGIO=0;
	/**
	 * Estado de aprobacinn por parte de la SED
	 */
	public static final int ESTADO_POA_APROBADO_SED=1;
	/**
	 * Estado de rechazado por parte de la SED
	 */
	public static final int ESTADO_POA_RECHAZADO_SED=2;

	/**
	 * Nombre del estado de "ninguno"
	 */
	public static final String ESTADO_POA_NINGUNO_="No enviado por el Colegio";
	/**
	 * Nombre del estado de aprobacinn del colegio
	 */
	public static final String ESTADO_POA_APROBADO_COLEGIO_="Enviado por el Colegio";
	/**
	 * Nombre del estado de aprobacinn de la SED
	 */
	public static final String ESTADO_POA_APROBADO_SED_="Aprobado por la Secretaría";
	/**
	 * Nombre del estado de rechazo de la SED
	 */
	public static final String ESTADO_POA_RECHAZADO_SED_="Devuelto por la Secretaría";

	/**
	 * Etapa de planeacinn del POA
	 */
	public static final int ETAPA_POA_PLANEACION=1;
	/**
	 * Estapa de seguimiento del POA
	 */
	public static final int ETAPA_POA_SEGUIMIENTO=2;
	
	/**
	 * @return Return the eSTADO_POA_APROBADO_COLEGIO.
	 */
	public int getESTADO_POA_APROBADO_COLEGIO() {
		return ESTADO_POA_APROBADO_COLEGIO;
	}
	/**
	 * @return Return the eSTADO_POA_APROBADO_SED.
	 */
	public int getESTADO_POA_APROBADO_SED() {
		return ESTADO_POA_APROBADO_SED;
	}
	/**
	 * @return Return the eSTADO_POA_NINGUNO.
	 */
	public int getESTADO_POA_NINGUNO() {
		return ESTADO_POA_NINGUNO;
	}
	/**
	 * @return Return the eSTADO_POA_RECHAZADO_SED.
	 */
	public int getESTADO_POA_RECHAZADO_SED() {
		return ESTADO_POA_RECHAZADO_SED;
	}
	/**
	 * @return Return the eTAPA_POA_PLANEACION.
	 */
	public int getETAPA_POA_PLANEACION() {
		return ETAPA_POA_PLANEACION;
	}
	/**
	 * @return Return the eTAPA_POA_SEGUIMIENTO.
	 */
	public int getETAPA_POA_SEGUIMIENTO() {
		return ETAPA_POA_SEGUIMIENTO;
	}

}
