package pei.common.vo;

import siges.common.vo.Params;

/**
 * @author john
 *
 */
public class ParamPEI extends Params{

	public static final int RESPUESTA_SI=1;
	public static final int RESPUESTA_NO=0;

	public static final String RESPUESTA_SI_="Si";
	public static final String RESPUESTA_NO_="No";

	public static final int ESTADO_PEI_SIN_INICIAR=0;
	public static final int ESTADO_PEI_EN_CONSTRUCCION=1;
	public static final int ESTADO_PEI_COMPLETO=2;
	public static final int ESTADO_PEI_A_ACTUALIZAR=3;

	public static final String ESTADO_PEI_SIN_INICIAR_="SIN INICIAR";
	public static final String ESTADO_PEI_EN_CONSTRUCCION_="EN CONSTRUCCION";
	public static final String ESTADO_PEI_COMPLETO_="COMPLETO";
	public static final String ESTADO_PEI_A_ACTUALIZAR_="A ACTUALIZAR";

	public static final int PROCESO_PEI_CONSULTA=1;
	public static final int PROCESO_PEI_SOCIALIZACION=2;
	public static final int PROCESO_PEI_ARTICULACION=3;

	public static final String PROCESO_PEI_CONSULTA_="Consulta";
	public static final String PROCESO_PEI_SOCIALIZACION_="Socializacinn";
	public static final String PROCESO_PEI_ARTICULACION_="Articulacinn al PEI";

	public static final int ETAPA_PEI_DEFINIDO=1;
	public static final int ETAPA_PEI_SOCIALIZADO=2;
	public static final int ETAPA_PEI_COMPARTIDO=3;
	
	public static final String ETAPA_PEI_DEFINIDO_="DEFINIDO";
	public static final String ETAPA_PEI_SOCIALIZADO_="SOCIALIZADO";
	public static final String ETAPA_PEI_COMPARTIDO_="COMPARTIDO CON LA COMUNIDAD";

	public static final int PEI_EXISTE_SI=1;
	public static final int PEI_EXISTE_NO=0;
	
	public static final int PARAM_PADRE_ETAPA=1;
	public static final int PARAM_PADRE_ENFASIS=2;
	public static final int PARAM_PADRE_ENFOQUE=3;
	public static final int PARAM_PADRE_FASE_CURRICULAR=4;
	public static final int PARAM_PADRE_PREPARACION=5;
	public static final int PARAM_PADRE_FORMULACION=6;
	public static final int PARAM_PADRE_EJECUCION=7;
	public static final int PARAM_PADRE_SEGUIMIENTO=8;
	public static final int PARAM_PADRE_PROGRAMAS=9;
	public static final int PARAM_PADRE_UNIVERSIDADES=10;
	public static final int PARAM_PADRE_PROYECTOS_LEY=11;
	
	/*Ajustes Junio 2013*/
	public static final int PARAM_PADRE_PROCESO_DIAGNOSTICO=12;
	public static final int PARAM_PADRE_HORIZONTE_INSTITUCIONAL=13;
	public static final int PARAM_PADRE_ESTRUCTURA_CURRICULAR=14;
	public static final int PARAM_PADRE_PLAN_ESTUDIOS=15;
	public static final int PARAM_PADRE_SISTEMA_EVALUACION=16;
	public static final int PARAM_PADRE_PROYECTO_CIUDADANIA=17;
	public static final int PARAM_PADRE_MANUAL_CONVIVENCIA=18;
	public static final int PARAM_PADRE_REGLAMENTO_DOCENTES=19;
	public static final int PARAM_PADRE_GOBIERNO_ESCOLAR=20;
	/* **************** */

	public static final int ELEMENTO_PEI_CONSULTA=1;
	public static final int ELEMENTO_PEI_SOCIALIZACION=2;
	public static final int ELEMENTO_PEI_ARTICULACION=3;
	public static final int ELEMENTO_PEI_IMPLEMENTACION=4;

	public static final String ELEMENTO_PEI_CONSULTA_="Consulta";
	public static final String ELEMENTO_PEI_SOCIALIZACION_="Socializacinn";
	public static final String ELEMENTO_PEI_ARTICULACION_="Articulacinn al curriculo";
	public static final String ELEMENTO_PEI_IMPLEMENTACION_="Implementacinn";

	
	public static final int ETAPAPROY_PEI_FORMULACION=1;
	public static final int ETAPAPROY_PEI_SOCIALIZACION=2;
	public static final int ETAPAPROY_PEI_IMPLEMENTACION=3;
	public static final int ETAPAPROY_PEI_CONSOLIDACION=4;

	public static final String ETAPAPROY_PEI_FORMULACION_="Formulacinn";
	public static final String ETAPAPROY_PEI_SOCIALIZACION_="Socializacinn";
	public static final String ETAPAPROY_PEI_IMPLEMENTACION_="Implementacinn";
	public static final String ETAPAPROY_PEI_CONSOLIDACION_="Consolidacinn";

	public static final int ESTRATEGIA_PEI_MEDIA=1;
	public static final int ESTRATEGIA_PEI_UNIVERSIDAD=2;
	public static final int ESTRATEGIA_PEI_SUPERIOR_SENA=3;
	public static final int ESTRATEGIA_PEI_SENA=4;

	public static final String ESTRATEGIA_PEI_MEDIA_="Media especializada";
	public static final String ESTRATEGIA_PEI_UNIVERSIDAD_="Articulacinn con Instituciones de Educacinn Superior";
	public static final String ESTRATEGIA_PEI_SUPERIOR_SENA_="Articulados a Nivel superior con el SENA";
	public static final String ESTRATEGIA_PEI_SENA_="Integrados con el SENA";

	public static final int FRECUENCIA_POCO=1;
	public static final int FRECUENCIA_ALGUNO=2;
	public static final int FRECUENCIA_FRECUENTE=3;
	public static final int FRECUENCIA_SIEMPRE=4;
	
	public static final String FRECUENCIA_POCO_="Pocas Veces";
	public static final String FRECUENCIA_ALGUNO_="Algunas Veces";
	public static final String FRECUENCIA_FRECUENTE_="Frecuentemente";
	public static final String FRECUENCIA_SIEMPRE_="Siempre";
	
	public static final int FRECUENCIA_EVALUACION_SEMESTRAL=1;
	public static final int FRECUENCIA_EVALUACION_ANUAL=2;
	
	public static final String FRECUENCIA_EVALUACION_SEMESTRAL_="Semestral";
	public static final String FRECUENCIA_EVALUACION_ANUAL_="Anual";
	
	public static final int ESTADO_PARAMETRO_ACTIVO=1;
	public static final int ESTADO_PARAMETRO_INACTIVO=0;

	public static final String ESTADO_PARAMETRO_ACTIVO_="Activo";
	public static final String ESTADO_PARAMETRO_INACTIVO_="Inactivo";
	
	/**
	 * @return the estadoPeiSinIniciar
	 */
	public int getESTADO_PEI_SIN_INICIAR() {
		return ESTADO_PEI_SIN_INICIAR;
	}
	/**
	 * @return the estadoPeiEnConstruccion
	 */
	public int getESTADO_PEI_EN_CONSTRUCCION() {
		return ESTADO_PEI_EN_CONSTRUCCION;
	}
	/**
	 * @return the estadoPeiCompleto
	 */
	public int getESTADO_PEI_COMPLETO() {
		return ESTADO_PEI_COMPLETO;
	}
	/**
	 * @return the estadoPeiAActualizar
	 */
	public int getESTADO_PEI_A_ACTUALIZAR() {
		return ESTADO_PEI_A_ACTUALIZAR;
	}
	/**
	 * @return the procesoPeiConsulta
	 */
	public int getPROCESO_PEI_CONSULTA() {
		return PROCESO_PEI_CONSULTA;
	}
	/**
	 * @return the procesoPeiSocializacion
	 */
	public int getPROCESO_PEI_SOCIALIZACION() {
		return PROCESO_PEI_SOCIALIZACION;
	}
	/**
	 * @return the procesoPeiArticulacion
	 */
	public int getPROCESO_PEI_ARTICULACION() {
		return PROCESO_PEI_ARTICULACION;
	}
	/**
	 * @return the peiExisteSi
	 */
	public static int getPEI_EXISTE_SI() {
		return PEI_EXISTE_SI;
	}
	/**
	 * @return the peiExisteNo
	 */
	public static int getPEI_EXISTE_NO() {
		return PEI_EXISTE_NO;
	}
	/**
	 * @return the elementoPeiConsulta
	 */
	public static int getELEMENTO_PEI_CONSULTA() {
		return ELEMENTO_PEI_CONSULTA;
	}
	/**
	 * @return the elementoPeiSocializacion
	 */
	public static int getELEMENTO_PEI_SOCIALIZACION() {
		return ELEMENTO_PEI_SOCIALIZACION;
	}
	/**
	 * @return the elementoPeiArticulacion
	 */
	public static int getELEMENTO_PEI_ARTICULACION() {
		return ELEMENTO_PEI_ARTICULACION;
	}
	/**
	 * @return the elementoPeiImplementacion
	 */
	public static int getELEMENTO_PEI_IMPLEMENTACION() {
		return ELEMENTO_PEI_IMPLEMENTACION;
	}
	/**
	 * @return the elementoPeiConsulta
	 */
	public static String getELEMENTO_PEI_CONSULTA_() {
		return ELEMENTO_PEI_CONSULTA_;
	}
	/**
	 * @return the elementoPeiSocializacion
	 */
	public static String getELEMENTO_PEI_SOCIALIZACION_() {
		return ELEMENTO_PEI_SOCIALIZACION_;
	}
	/**
	 * @return the elementoPeiArticulacion
	 */
	public static String getELEMENTO_PEI_ARTICULACION_() {
		return ELEMENTO_PEI_ARTICULACION_;
	}
	/**
	 * @return the elementoPeiImplementacion
	 */
	public static String getELEMENTO_PEI_IMPLEMENTACION_() {
		return ELEMENTO_PEI_IMPLEMENTACION_;
	}
	/**
	 * @return the etapaproyPeiFormulacion
	 */
	public static int getETAPAPROY_PEI_FORMULACION() {
		return ETAPAPROY_PEI_FORMULACION;
	}
	/**
	 * @return the etapaproyPeiSocializacion
	 */
	public static int getETAPAPROY_PEI_SOCIALIZACION() {
		return ETAPAPROY_PEI_SOCIALIZACION;
	}
	/**
	 * @return the etapaproyPeiImplementacion
	 */
	public static int getETAPAPROY_PEI_IMPLEMENTACION() {
		return ETAPAPROY_PEI_IMPLEMENTACION;
	}
	/**
	 * @return the etapaproyPeiConsolidacion
	 */
	public static int getETAPAPROY_PEI_CONSOLIDACION() {
		return ETAPAPROY_PEI_CONSOLIDACION;
	}
	/**
	 * @return the etapaproyPeiFormulacion
	 */
	public static String getETAPAPROY_PEI_FORMULACION_() {
		return ETAPAPROY_PEI_FORMULACION_;
	}
	/**
	 * @return the etapaproyPeiSocializacion
	 */
	public static String getETAPAPROY_PEI_SOCIALIZACION_() {
		return ETAPAPROY_PEI_SOCIALIZACION_;
	}
	/**
	 * @return the etapaproyPeiImplementacion
	 */
	public static String getETAPAPROY_PEI_IMPLEMENTACION_() {
		return ETAPAPROY_PEI_IMPLEMENTACION_;
	}
	/**
	 * @return the etapaproyPeiConsolidacion
	 */
	public static String getETAPAPROY_PEI_CONSOLIDACION_() {
		return ETAPAPROY_PEI_CONSOLIDACION_;
	}
	/**
	 * @return the estrategiaPeiMedia
	 */
	public static int getESTRATEGIA_PEI_MEDIA() {
		return ESTRATEGIA_PEI_MEDIA;
	}
	/**
	 * @return the estrategiaPeiUniversidad
	 */
	public static int getESTRATEGIA_PEI_UNIVERSIDAD() {
		return ESTRATEGIA_PEI_UNIVERSIDAD;
	}
	/**
	 * @return the estrategiaPeiSuperiorSena
	 */
	public static int getESTRATEGIA_PEI_SUPERIOR_SENA() {
		return ESTRATEGIA_PEI_SUPERIOR_SENA;
	}
	/**
	 * @return the estrategiaPeiSena
	 */
	public static int getESTRATEGIA_PEI_SENA() {
		return ESTRATEGIA_PEI_SENA;
	}
	/**
	 * @return the estrategiaPeiMedia
	 */
	public static String getESTRATEGIA_PEI_MEDIA_() {
		return ESTRATEGIA_PEI_MEDIA_;
	}
	/**
	 * @return the estrategiaPeiUniversidad
	 */
	public static String getESTRATEGIA_PEI_UNIVERSIDAD_() {
		return ESTRATEGIA_PEI_UNIVERSIDAD_;
	}
	/**
	 * @return the estrategiaPeiSuperiorSena
	 */
	public static String getESTRATEGIA_PEI_SUPERIOR_SENA_() {
		return ESTRATEGIA_PEI_SUPERIOR_SENA_;
	}
	/**
	 * @return the estrategiaPeiSena
	 */
	public static String getESTRATEGIA_PEI_SENA_() {
		return ESTRATEGIA_PEI_SENA_;
	}
	/**
	 * @return the frecuenciaPoco
	 */
	public static int getFRECUENCIA_POCO() {
		return FRECUENCIA_POCO;
	}
	/**
	 * @return the frecuenciaAlguno
	 */
	public static int getFRECUENCIA_ALGUNO() {
		return FRECUENCIA_ALGUNO;
	}
	/**
	 * @return the frecuenciaFrecuente
	 */
	public static int getFRECUENCIA_FRECUENTE() {
		return FRECUENCIA_FRECUENTE;
	}
	/**
	 * @return the frecuenciaSiempre
	 */
	public static int getFRECUENCIA_SIEMPRE() {
		return FRECUENCIA_SIEMPRE;
	}
	/**
	 * @return the frecuenciaPoco
	 */
	public static String getFRECUENCIA_POCO_() {
		return FRECUENCIA_POCO_;
	}
	/**
	 * @return the frecuenciaAlguno
	 */
	public static String getFRECUENCIA_ALGUNO_() {
		return FRECUENCIA_ALGUNO_;
	}
	/**
	 * @return the frecuenciaFrecuente
	 */
	public static String getFRECUENCIA_FRECUENTE_() {
		return FRECUENCIA_FRECUENTE_;
	}
	/**
	 * @return the frecuenciaSiempre
	 */
	public static String getFRECUENCIA_SIEMPRE_() {
		return FRECUENCIA_SIEMPRE_;
	}
	/**
	 * @return the frecuenciaEvaluacionSemestral
	 */
	public static int getFRECUENCIA_EVALUACION_SEMESTRAL() {
		return FRECUENCIA_EVALUACION_SEMESTRAL;
	}
	/**
	 * @return the frecuenciaEvaluacionAnual
	 */
	public static int getFRECUENCIA_EVALUACION_ANUAL() {
		return FRECUENCIA_EVALUACION_ANUAL;
	}
	/**
	 * @return the frecuenciaEvaluacionSemestral
	 */
	public static String getFRECUENCIA_EVALUACION_SEMESTRAL_() {
		return FRECUENCIA_EVALUACION_SEMESTRAL_;
	}
	/**
	 * @return the frecuenciaEvaluacionAnual
	 */
	public static String getFRECUENCIA_EVALUACION_ANUAL_() {
		return FRECUENCIA_EVALUACION_ANUAL_;
	}


}
