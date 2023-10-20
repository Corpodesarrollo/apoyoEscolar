package pei.registro.vo;

import pei.common.vo.ParamPEI;

public class ParamsVO extends ParamPEI{
	public static final int FICHA_IDENTIFICACION=1;
	public static final int FICHA_HORIZONTE=2;
	public static final int FICHA_DESARROLLO_CURRICULAR=3;
	public static final int FICHA_DESARROLLO_ADMINISTRATIVO=4;
	public static final int FICHA_PROGRAMA_SENA=5;
	public static final int FICHA_PROGRAMA_UNIVERSIDAD=6;
	public static final int FICHA_OTRO_PROYECTO=7;
	public static final int FICHA_CAPACITACION=8;
	public static final int FICHA_DOCUMENTO=9;
	/* Ajustes Junio 2013*/
	public static final int FICHA_CURRICULO=10;
	public static final int FICHA_CRITERIO_EVALUACION=11;
	public static final int FICHA_CIUDADANIA=12;
	public static final int FICHA_PROYECTO_40_HORAS=13;
	public static final int FICHA_POBLACIONES=14;
	public static final int FICHA_AJUSTES=15;
	public static final int FICHA_PRIMERA_INFANCIA=16;

	public static final int FICHA_DEFAULT=FICHA_IDENTIFICACION;
	public static final String BEAN_IDENTIFICACION="identificacionPEIVO";
	public static final String BEAN_HORIZONTE="horizontePEIVO";
	public static final String BEAN_DESARROLLO_CURRICULAR="desarrolloCurricularPEIVO";
	public static final String BEAN_DESARROLLO_ADMINISTRATIVO="desarrolloAdministrativoPEIVO";
	public static final String BEAN_PROGRAMA_SENA="programaSenaPEIVO";
	public static final String BEAN_PROGRAMA_UNIVERSIDAD="programaUniversidadPEIVO";
	public static final String BEAN_OTRO_PROYECTO="otroProyectoPEIVO";
	public static final String BEAN_CAPACITACION="capacitacionPEIVO";
	public static final String BEAN_FILTRO_REGISTRO="filtroRegistroPEIVO";
	public static final String BEAN_DOCUMENTO="documentoPEIVO";
	
	/* Ajustes Junio 2013*/
	public static final String BEAN_AJUSTE="ajustePEIVO";
	public static final String BEAN_CURRICULO="curriculoPEIVO";
	public static final String BEAN_CRITERIO_EVALUACION="criterioEvaluacionPEIVO";
	public static final String BEAN_CIUDADANIA="ciudadaniaPEIVO";
	public static final String BEAN_PROYECTO_40_HORAS="proyecto40HorasPEIVO";
	public static final String BEAN_POBLACIONES="poblacionesPEIVO";
	public static final String BEAN_PRIMERA_INFANCIA="primeraInfanciaPEIVO";
	
	/**
	 * @return the fichaIdentificacion
	 */
	public int getFICHA_IDENTIFICACION() {
		return FICHA_IDENTIFICACION;
	}
	/**
	 * @return the fichaHorizonte
	 */
	public int getFICHA_HORIZONTE() {
		return FICHA_HORIZONTE;
	}
	/**
	 * @return the fichaDesarrolloCurricular
	 */
	public int getFICHA_DESARROLLO_CURRICULAR() {
		return FICHA_DESARROLLO_CURRICULAR;
	}
	/**
	 * @return the fichaDesarrolloAdministrativo
	 */
	public int getFICHA_DESARROLLO_ADMINISTRATIVO() {
		return FICHA_DESARROLLO_ADMINISTRATIVO;
	}
	/**
	 * @return the fichaDefault
	 */
	public int getFICHA_DEFAULT() {
		return FICHA_DEFAULT;
	}
	/**
	 * @return the beanIdentificacion
	 */
	public String getBEAN_IDENTIFICACION() {
		return BEAN_IDENTIFICACION;
	}
	/**
	 * @return the beanHorizonte
	 */
	public String getBEAN_HORIZONTE() {
		return BEAN_HORIZONTE;
	}
	/**
	 * @return the beanDesarrolloCurricular
	 */
	public String getBEAN_DESARROLLO_CURRICULAR() {
		return BEAN_DESARROLLO_CURRICULAR;
	}
	/**
	 * @return the beanDesarrolloAdministrativo
	 */
	public String getBEAN_DESARROLLO_ADMINISTRATIVO() {
		return BEAN_DESARROLLO_ADMINISTRATIVO;
	}
	/**
	 * @return the fichaProgramaSena
	 */
	public int getFICHA_PROGRAMA_SENA() {
		return FICHA_PROGRAMA_SENA;
	}
	/**
	 * @return the fichaProgramaUniversidad
	 */
	public int getFICHA_PROGRAMA_UNIVERSIDAD() {
		return FICHA_PROGRAMA_UNIVERSIDAD;
	}
	/**
	 * @return the fichaOtroProyecto
	 */
	public int getFICHA_OTRO_PROYECTO() {
		return FICHA_OTRO_PROYECTO;
	}
	/**
	 * @return the fichaCapacitacion
	 */
	public int getFICHA_CAPACITACION() {
		return FICHA_CAPACITACION;
	}
	/**
	 * @return the beanProgramaSena
	 */
	public String getBEAN_PROGRAMA_SENA() {
		return BEAN_PROGRAMA_SENA;
	}
	/**
	 * @return the beanProgramaUniversidad
	 */
	public String getBEAN_PROGRAMA_UNIVERSIDAD() {
		return BEAN_PROGRAMA_UNIVERSIDAD;
	}
	/**
	 * @return the beanOtroProyecto
	 */
	public String getBEAN_OTRO_PROYECTO() {
		return BEAN_OTRO_PROYECTO;
	}
	/**
	 * @return the beanCapacitacion
	 */
	public String getBEAN_CAPACITACION() {
		return BEAN_CAPACITACION;
	}
	/**
	 * @return the fichaDocumento
	 */
	public int getFICHA_DOCUMENTO() {
		return FICHA_DOCUMENTO;
	}
	/*
	 * 							FICHA_CURRICULO=10;
		public static final int FICHA_CRITERIO_EVALUACION=11;
		public static final int FICHA_CIUDADANIA=12;
		public static final int FICHA_PRIMERA_INFANCIA=12;
		public static final int FICHA_PROYECTO_40_HORAS=13;
		public static final int FICHA_POBLACIONES=14;
		public static final int FICHA_AJUSTES=15;
	 * 
	 * */
	public int getFICHA_CURRICULO() {
		return FICHA_CURRICULO;
	}
	
	public int getFICHA_CRITERIO_EVALUACION() {
		return FICHA_CRITERIO_EVALUACION;
	}
	
	public int getFICHA_CIUDADANIA() {
		return FICHA_CIUDADANIA;
	}
	
	public int getFICHA_PRIMERA_INFANCIA() {
		return FICHA_PRIMERA_INFANCIA;
	}
	
	public int getFICHA_PROYECTO_40_HORAS() {
		return FICHA_PROYECTO_40_HORAS;
	}
	
	public int getFICHA_POBLACIONES() {
		return FICHA_POBLACIONES;
	}
	
	public int getFICHA_AJUSTES() {
		return FICHA_AJUSTES;
	}
	
}
