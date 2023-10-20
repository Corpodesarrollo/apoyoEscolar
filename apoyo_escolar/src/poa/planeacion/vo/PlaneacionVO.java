package poa.planeacion.vo;

import siges.common.vo.Vo;

/**
 * Value Object del manejo del formulario de ingreso/edicinn de actividades
 * 14/01/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class PlaneacionVO  extends Vo{
	private int plaConsecutivo;
	private long plaInstitucion;
	private long plaCodigo;
	private long plaOrden;
	private int plaVigencia;
	private String plaObjetivo;
	private String plaActividad;
	private int plaAreaGestion;
	private float plaPonderador;
	private int plaLineaAccion;
	private int plaTipoMeta;
	private long plaMetaAnualCantidad;
	private int plaMetaAnualUnidad;
	private String plaMetaAnualCual;
	private int[] plaFuenteFinanciera;
	private long plaPresupuesto;
	private String plaResponsable;
	private String plaFecha;
	private String plaCronograma1;
	private String plaCronograma2;
	private String plaCronograma3;
	private String plaCronograma4;
	private int placcodobjetivo;
	private String placcodobjetivoText;
	private String plaDisabled;
	private boolean plaDesHabilitado;
	
	private String plaAreaGestionNombre;
	private String plaLineaAccionNombre;
	
	
	//************* PIMA ****************
	private String tipoActividad;
	private String accionMejoramiento;
	private String accionMejoramientoOtras;
	private String tipoGasto; //Inversion o Funcionamiento
	private String rubroGasto;
	/*Inversion -----------
		Lista para inversinn
		Vitrina pedagngica
		Escuela ciudad escuela
		Medio Ambiente y prevencinn de desastres
		Aprovechamiento  del tiempo libre
		Educacinn sexual
		Compra equipos beneficio de los estudiantes 
		Formacinn de valores
		Formacinn tncnica y para el trabajo
		Fomento de la cultura
		Investigacinn y estudios
		Otros proyectos
		Pasivos exigibles inversinn
	Funcionamiento
		SERVICIOS PERSONALES
		GASTOS GENERALES
		PASIVOS EXIGIBLES */	
	
	private String subnivelGasto;
	private String proyectoInversion;
	private String fuenteFinanciacion;
	private String fuenteFinanciacionOtros;
	private Long montoAnual;
	private Long presupuestoParticipativo; // no obligatorio
	
	private String SEGFECHACUMPLIMT;
	private String plaIdUsuario;

	//*****************************************************
	
	public String getSEGFECHACUMPLIMT() {
		return SEGFECHACUMPLIMT;
	}
	public void setSEGFECHACUMPLIMT(String sEGFECHACUMPLIMT) {
		SEGFECHACUMPLIMT = sEGFECHACUMPLIMT;
	}
	public String getTipoActividad() {
		return tipoActividad;
	}
	public void setTipoActividad(String tipoActividad) {
		this.tipoActividad = tipoActividad;
	}
	
	public String getAccionMejoramiento() {
		return accionMejoramiento;
	}
	public void setAccionMejoramiento(String accionMejoramiento) {
		this.accionMejoramiento = accionMejoramiento;
	}
	
	public String getAccionMejoramientoOtras() {
		return accionMejoramientoOtras;
	}
	public void setAccionMejoramientoOtras(String accionMejoramientoOtras) {
		this.accionMejoramientoOtras = accionMejoramientoOtras;
	}
	
	public String getTipoGasto() {
		return tipoGasto;
	}
	public void setTipoGasto(String tipoGasto) {
		this.tipoGasto = tipoGasto;
	}
	public String getRubroGasto() {
		return rubroGasto;
	}
	public void setRubroGasto(String rubroGasto) {
		this.rubroGasto = rubroGasto;
	}
	public String getSubnivelGasto() {
		return subnivelGasto;
	}
	public void setSubnivelGasto(String subnivelGasto) {
		this.subnivelGasto = subnivelGasto;
	}
	public String getProyectoInversion() {
		return proyectoInversion;
	}
	public void setProyectoInversion(String proyectoInversion) {
		this.proyectoInversion = proyectoInversion;
	}
	public String getFuenteFinanciacion() {
		return fuenteFinanciacion;
	}
	public void setFuenteFinanciacion(String fuenteFinanciacion) {
		this.fuenteFinanciacion = fuenteFinanciacion;
	}
	
	public String getFuenteFinanciacionOtros() {
		return fuenteFinanciacionOtros;
	}
	public void setFuenteFinanciacionOtros(String fuenteFinanciacionOtros) {
		this.fuenteFinanciacionOtros = fuenteFinanciacionOtros;
	}
	
	public Long getMontoAnual() {
		return montoAnual;
	}
	public void setMontoAnual(Long montoAnual) {
		this.montoAnual = montoAnual;
	}
	public Long getPresupuestoParticipativo() {
		return presupuestoParticipativo;
	}
	public void setPresupuestoParticipativo(Long presupuestoParticipativo) {
		this.presupuestoParticipativo = presupuestoParticipativo;
	}

	/**
	 * @return Returns the plaActividad.
	 */
	public String getPlaActividad() {
		return plaActividad;
	}
	/**
	 * @param plaActividad The plaActividad to set.
	 */
	public void setPlaActividad(String plaActividad) {
		this.plaActividad = plaActividad;
	}
	/**
	 * @return Returns the plaAreaGestion.
	 */
	public int getPlaAreaGestion() {
		return plaAreaGestion;
	}
	/**
	 * @param plaAreaGestion The plaAreaGestion to set.
	 */
	public void setPlaAreaGestion(int plaAreaGestion) {
		this.plaAreaGestion = plaAreaGestion;
	}
	/**
	 * @return Returns the plaCodigo.
	 */
	public long getPlaCodigo() {
		return plaCodigo;
	}
	/**
	 * @param plaCodigo The plaCodigo to set.
	 */
	public void setPlaCodigo(long plaCodigo) {
		this.plaCodigo = plaCodigo;
	}
	/**
	 * @return Returns the plaFecha.
	 */
	public String getPlaFecha() {
		return plaFecha;
	}
	/**
	 * @param plaFecha The plaFecha to set.
	 */
	public void setPlaFecha(String plaFecha) {
		this.plaFecha = plaFecha;
	}
	/**
	 * @return Returns the plaInstitucion.
	 */
	public long getPlaInstitucion() {
		return plaInstitucion;
	}
	/**
	 * @param plaInstitucion The plaInstitucion to set.
	 */
	public void setPlaInstitucion(long plaInstitucion) {
		this.plaInstitucion = plaInstitucion;
	}
	/**
	 * @return Returns the plaLineaAccion.
	 */
	public int getPlaLineaAccion() {
		return plaLineaAccion;
	}
	/**
	 * @param plaLineaAccion The plaLineaAccion to set.
	 */
	public void setPlaLineaAccion(int plaLineaAccion) {
		this.plaLineaAccion = plaLineaAccion;
	}
	/**
	 * @return Returns the plaMetaAnualCual.
	 */
	public String getPlaMetaAnualCual() {
		return plaMetaAnualCual;
	}
	/**
	 * @param plaMetaAnualCual The plaMetaAnualCual to set.
	 */
	public void setPlaMetaAnualCual(String plaMetaAnualCual) {
		this.plaMetaAnualCual = plaMetaAnualCual;
	}
	/**
	 * @return Returns the plaMetaAnualUnidad.
	 */
	public int getPlaMetaAnualUnidad() {
		return plaMetaAnualUnidad;
	}
	/**
	 * @param plaMetaAnualUnidad The plaMetaAnualUnidad to set.
	 */
	public void setPlaMetaAnualUnidad(int plaMetaAnualUnidad) {
		this.plaMetaAnualUnidad = plaMetaAnualUnidad;
	}
	/**
	 * @return Returns the plaObjetivo.
	 */
	public String getPlaObjetivo() {
		return plaObjetivo;
	}
	/**
	 * @param plaObjetivo The plaObjetivo to set.
	 */
	public void setPlaObjetivo(String plaObjetivo) {
		this.plaObjetivo = plaObjetivo;
	}
	/**
	 * @return Returns the plaOrden.
	 */
	public long getPlaOrden() {
		return plaOrden;
	}
	/**
	 * @param plaOrden The plaOrden to set.
	 */
	public void setPlaOrden(long plaOrden) {
		this.plaOrden = plaOrden;
	}
	/**
	 * @return Returns the plaPonderador.
	 */
	public float getPlaPonderador() {
		return plaPonderador;
	}
	/**
	 * @param plaPonderador The plaPonderador to set.
	 */
	public void setPlaPonderador(float plaPonderador) {
		this.plaPonderador = plaPonderador;
	}
	/**
	 * @return Returns the plaPresupuesto.
	 */
	public long getPlaPresupuesto() {
		return plaPresupuesto;
	}
	/**
	 * @param plaPresupuesto The plaPresupuesto to set.
	 */
	public void setPlaPresupuesto(long plaPresupuesto) {
		this.plaPresupuesto = plaPresupuesto;
	}
	/**
	 * @return Returns the plaResponsable.
	 */
	public String getPlaResponsable() {
		return plaResponsable;
	}
	/**
	 * @param plaResponsable The plaResponsable to set.
	 */
	public void setPlaResponsable(String plaResponsable) {
		this.plaResponsable = plaResponsable;
	}
	/**
	 * @return Returns the plaTipoMeta.
	 */
	public int getPlaTipoMeta() {
		return plaTipoMeta;
	}
	/**
	 * @param plaTipoMeta The plaTipoMeta to set.
	 */
	public void setPlaTipoMeta(int plaTipoMeta) {
		this.plaTipoMeta = plaTipoMeta;
	}
	/**
	 * @return Returns the plaCronograma1.
	 */
	public String getPlaCronograma1() {
		return plaCronograma1;
	}
	/**
	 * @param plaCronograma1 The plaCronograma1 to set.
	 */
	public void setPlaCronograma1(String plaCronograma1) {
		this.plaCronograma1 = plaCronograma1;
	}
	/**
	 * @return Returns the plaCronograma2.
	 */
	public String getPlaCronograma2() {
		return plaCronograma2;
	}
	/**
	 * @param plaCronograma2 The plaCronograma2 to set.
	 */
	public void setPlaCronograma2(String plaCronograma2) {
		this.plaCronograma2 = plaCronograma2;
	}
	/**
	 * @return Returns the plaCronograma3.
	 */
	public String getPlaCronograma3() {
		return plaCronograma3;
	}
	/**
	 * @param plaCronograma3 The plaCronograma3 to set.
	 */
	public void setPlaCronograma3(String plaCronograma3) {
		this.plaCronograma3 = plaCronograma3;
	}
	/**
	 * @return Returns the plaCronograma4.
	 */
	public String getPlaCronograma4() {
		return plaCronograma4;
	}
	/**
	 * @param plaCronograma4 The plaCronograma4 to set.
	 */
	public void setPlaCronograma4(String plaCronograma4) {
		this.plaCronograma4 = plaCronograma4;
	}
	/**
	 * @return Returns the plaMetaAnualCantidad.
	 */
	public long getPlaMetaAnualCantidad() {
		return plaMetaAnualCantidad;
	}
	/**
	 * @param plaMetaAnualCantidad The plaMetaAnualCantidad to set.
	 */
	public void setPlaMetaAnualCantidad(long plaMetaAnualCantidad) {
		this.plaMetaAnualCantidad = plaMetaAnualCantidad;
	}
	/**
	 * @return Returns the plaVigencia.
	 */
	public int getPlaVigencia() {
		return plaVigencia;
	}
	/**
	 * @param plaVigencia The plaVigencia to set.
	 */
	public void setPlaVigencia(int plaVigencia) {
		this.plaVigencia = plaVigencia;
	}
	/**
	 * @return Returns the plaAreaGestionNombre.
	 */
	public String getPlaAreaGestionNombre() {
		return plaAreaGestionNombre;
	}
	/**
	 * @param plaAreaGestionNombre The plaAreaGestionNombre to set.
	 */
	public void setPlaAreaGestionNombre(String plaAreaGestionNombre) {
		this.plaAreaGestionNombre = plaAreaGestionNombre;
	}
	/**
	 * @return Returns the plaLineaAccionNombre.
	 */
	public String getPlaLineaAccionNombre() {
		return plaLineaAccionNombre;
	}
	/**
	 * @param plaLineaAccionNombre The plaLineaAccionNombre to set.
	 */
	public void setPlaLineaAccionNombre(String plaLineaAccionNombre) {
		this.plaLineaAccionNombre = plaLineaAccionNombre;
	}
	/**
	 * @return Returns the plaConsecutivo.
	 */
	public int getPlaConsecutivo() {
		return plaConsecutivo;
	}
	/**
	 * @param plaConsecutivo The plaConsecutivo to set.
	 */
	public void setPlaConsecutivo(int plaConsecutivo) {
		this.plaConsecutivo = plaConsecutivo;
	}
	/**
	 * @return Returns the plaFuenteFinanciera.
	 */
	public int[] getPlaFuenteFinanciera() {
		return plaFuenteFinanciera;
	}
	/**
	 * @param plaFuenteFinanciera The plaFuenteFinanciera to set.
	 */
	public void setPlaFuenteFinanciera(int[] plaFuenteFinanciera) {
		this.plaFuenteFinanciera = plaFuenteFinanciera;
	}
	/**
	 * @return Return the plaDisabled.
	 */
	public String getPlaDisabled() {
		return plaDisabled;
	}
	/**
	 * @param plaDisabled The plaDisabled to set.
	 */
	public void setPlaDisabled(String plaDisabled) {
		this.plaDisabled = plaDisabled;
	}
	/**
	 * @return Return the plaDesHabilitado.
	 */
	public boolean isPlaDesHabilitado() {
		return plaDesHabilitado;
	}
	/**
	 * @param plaDesHabilitado The plaDesHabilitado to set.
	 */
	public void setPlaDesHabilitado(boolean plaDesHabilitado) {
		this.plaDesHabilitado = plaDesHabilitado;
	}
	public int getPlaccodobjetivo() {
		return placcodobjetivo;
	}
	public void setPlaccodobjetivo(int placcodobjetivo) {
		this.placcodobjetivo = placcodobjetivo;
	}
	public String getPlaccodobjetivoText() {
		return placcodobjetivoText;
	}
	public void setPlaccodobjetivoText(String placcodobjetivoText) {
		this.placcodobjetivoText = placcodobjetivoText;
	}
	public String getPlaIdUsuario() {
		return plaIdUsuario;
	}
	public void setPlaIdUsuario(String plaIdUsuario) {
		this.plaIdUsuario = plaIdUsuario;
	}
	 
}
