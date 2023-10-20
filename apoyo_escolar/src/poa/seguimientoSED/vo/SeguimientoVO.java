package poa.seguimientoSED.vo;

//	VERSION		FECHA		AUTOR			DESCRIPCION
//		1.0		02/12/2019	JORGE CAMACHO	Se agregó la propiedad PLACOTROCUAL y plaPonderador

import siges.common.vo.Vo;

/**
 * Value Object para el manejo de las actividades
 * 14/01/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class SeguimientoVO extends Vo {
	
	private int plaConsecutivo;
	private long plaInstitucion;
	private long plaCodigo;
	private long plaOrden;
	private int plaVigencia;
	private String plaObjetivo;
	private String plaActividad;
	private int plaAreaGestion;
	private int plaLineaAccion;
	private int plaTipoMeta;
	private String plaFecha;
	private long plaCantidad;
	private int plaUnidadMedida;
	private String lblAreaGestion;
	
	private String plaCronograma1;
	private String plaCronograma2;
	private String plaCronograma3;
	private String plaCronograma4;
	
	private String plaFechaReal;
	
	private String plaSeguimiento1;
	private String plaSeguimiento2;
	private String plaSeguimiento3;
	private String plaSeguimiento4;
	
//	private float plaPorcentaje1;
//	private float plaPorcentaje2;
//	private float plaPorcentaje3;
//	private float plaPorcentaje4;

	private String plaPorcentaje1;
	private String plaPorcentaje2;
	private String plaPorcentaje3;
	private String plaPorcentaje4;
	
	private String plaVerificacion1;
	private String plaVerificacion2;
	private String plaVerificacion3;
	private String plaVerificacion4;

	private String plaLogros1;
	private String plaLogros2;
	private String plaLogros3;
	private String plaLogros4;
	
	private String plaDisabled1;
	private String plaDisabled2;
	private String plaDisabled3;
	private String plaDisabled4;
	
	private boolean plaMostrarPorcentaje;
	private String plaDisabled;
	private boolean plaDesHabilitado;
	private String plaAreaGestionNombre;
	private String plaLineaAccionNombre;
	
	private int placcodobjetivo;
	private String placcodobjetivoText;
	
	private String ACCIONMEJORAMIENTO;
	
	private String TIPOGASTO;
	private String RUBROGASTO;
	private String SUBNIVELGASTO;
	private String PROYECTOINVERSION;
	private String FUENTEFINANCIACION;
	private long MONTOANUAL;
	private long PPTOPARTICIPATIVO;
	private String TIPOACTIVIDAD;
	
	private String SEGFECHACUMPLIMT;
	private String SEGFECHAREALCUMPLIM;
	
	private int PRESUPUESTOEJECUTADO;
	
	private String RESPONSABLE;
	
	private String CUAL;
	
	private String tipoActividad;
	private String accionMejoramiento;
	private String accionMejoramientoOtras;
	
	private String FUENTEFINANCIACIONOTROS;
	
	private String PLACOTROCUAL;
	private int plaPonderador;
	
	private String califEvalSeg1;
	private String califEvalSeg2;
	private String califEvalSeg3;
	private String califEvalSeg4;
	private String obserEvalSeg1;
	private String obserEvalSeg2;
	private String obserEvalSeg3;
	private String obserEvalSeg4;
	private String evalTexto1;
	private String evalTexto2;
	private String evalTexto3;
	private String evalTexto4;
	
	private int rojoNaranjaSeguimiento1;
	private int rojoNaranjaSeguimiento2;
	private int rojoNaranjaSeguimiento3;
	private int rojoNaranjaSeguimiento4;
	
	private String califActividad;
	private String califTotalActividad;
		
	
	public String getRESPONSABLE() {
		return RESPONSABLE;
	}
	public void setRESPONSABLE(String rESPONSABLE) {
		RESPONSABLE = rESPONSABLE;
	}
	
	public String getTIPOGASTO() {
		return TIPOGASTO;
	}
	public void setTIPOGASTO(String tIPOGASTO) {
		TIPOGASTO = tIPOGASTO;
	}
	public String getRUBROGASTO() {
		return RUBROGASTO;
	}
	public void setRUBROGASTO(String rUBROGASTO) {
		RUBROGASTO = rUBROGASTO;
	}
	public String getSUBNIVELGASTO() {
		return SUBNIVELGASTO;
	}
	public void setSUBNIVELGASTO(String sUBNIVELGASTO) {
		SUBNIVELGASTO = sUBNIVELGASTO;
	}
	public String getPROYECTOINVERSION() {
		return PROYECTOINVERSION;
	}
	public void setPROYECTOINVERSION(String pROYECTOINVERSION) {
		PROYECTOINVERSION = pROYECTOINVERSION;
	}
	public String getFUENTEFINANCIACION() {
		return FUENTEFINANCIACION;
	}
	public void setFUENTEFINANCIACION(String fUENTEFINANCIACION) {
		FUENTEFINANCIACION = fUENTEFINANCIACION;
	}
	public String getFUENTEFINANCIACIONOTROS() {
		return FUENTEFINANCIACIONOTROS;
	}
	public void setFUENTEFINANCIACIONOTROS(String fUENTEFINANCIACIONOTROS) {
		FUENTEFINANCIACIONOTROS = fUENTEFINANCIACIONOTROS;
	}
	public long getMONTOANUAL() {
		return MONTOANUAL;
	}
	public void setMONTOANUAL(long mONTOANUAL) {
		MONTOANUAL = mONTOANUAL;
	}
	public long getPPTOPARTICIPATIVO() {
		return PPTOPARTICIPATIVO;
	}
	public void setPPTOPARTICIPATIVO(long pPTOPARTICIPATIVO) {
		PPTOPARTICIPATIVO = pPTOPARTICIPATIVO;
	}
	public String getTIPOACTIVIDAD() {
		return TIPOACTIVIDAD;
	}
	public void setTIPOACTIVIDAD(String tIPOACTIVIDAD) {
		TIPOACTIVIDAD = tIPOACTIVIDAD;
	}
	public String getSEGFECHACUMPLIMT() {
		return SEGFECHACUMPLIMT;
	}
	public void setSEGFECHACUMPLIMT(String sEGFECHACUMPLIMT) {
		SEGFECHACUMPLIMT = sEGFECHACUMPLIMT;
	}
	public String getSEGFECHAREALCUMPLIM() {
		return SEGFECHAREALCUMPLIM;
	}
	public void setSEGFECHAREALCUMPLIM(String sEGFECHAREALCUMPLIM) {
		SEGFECHAREALCUMPLIM = sEGFECHAREALCUMPLIM;
	}
	public int getPRESUPUESTOEJECUTADO() {
		return PRESUPUESTOEJECUTADO;
	}
	public void setPRESUPUESTOEJECUTADO(int pRESUPUESTOEJECUTADO) {
		PRESUPUESTOEJECUTADO = pRESUPUESTOEJECUTADO;
	}
	public String getCUAL() {
		return CUAL;
	}
	public void setCUAL(String cUAL) {
		CUAL = cUAL;
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
	/**
	 * @return Return the plaDisabled1.
	 */
	public final String getPlaDisabled1() {
		return plaDisabled1;
	}
	/**
	 * @param plaDisabled1 The plaDisabled1 to set.
	 */
	public final void setPlaDisabled1(String plaDisabled1) {
		this.plaDisabled1 = plaDisabled1;
	}
	/**
	 * @return Return the plaDisabled2.
	 */
	public final String getPlaDisabled2() {
		return plaDisabled2;
	}
	/**
	 * @param plaDisabled2 The plaDisabled2 to set.
	 */
	public final void setPlaDisabled2(String plaDisabled2) {
		this.plaDisabled2 = plaDisabled2;
	}
	/**
	 * @return Return the plaDisabled3.
	 */
	public final String getPlaDisabled3() {
		return plaDisabled3;
	}
	/**
	 * @param plaDisabled3 The plaDisabled3 to set.
	 */
	public final void setPlaDisabled3(String plaDisabled3) {
		this.plaDisabled3 = plaDisabled3;
	}
	/**
	 * @return Return the plaDisabled4.
	 */
	public final String getPlaDisabled4() {
		return plaDisabled4;
	}
	/**
	 * @param plaDisabled4 The plaDisabled4 to set.
	 */
	public final void setPlaDisabled4(String plaDisabled4) {
		this.plaDisabled4 = plaDisabled4;
	}
	/**
	 * @return Return the plaFechaReal.
	 */
	public final String getPlaFechaReal() {
		return plaFechaReal;
	}
	/**
	 * @param plaFechaReal The plaFechaReal to set.
	 */
	public final void setPlaFechaReal(String plaFechaReal) {
		this.plaFechaReal = plaFechaReal;
	}
	/**
	 * @return Return the plaLogros1.
	 */
	public final String getPlaLogros1() {
		return plaLogros1;
	}
	/**
	 * @param plaLogros1 The plaLogros1 to set.
	 */
	public final void setPlaLogros1(String plaLogros1) {
		this.plaLogros1 = plaLogros1;
	}
	/**
	 * @return Return the plaLogros2.
	 */
	public final String getPlaLogros2() {
		return plaLogros2;
	}
	/**
	 * @param plaLogros2 The plaLogros2 to set.
	 */
	public final void setPlaLogros2(String plaLogros2) {
		this.plaLogros2 = plaLogros2;
	}
	/**
	 * @return Return the plaLogros3.
	 */
	public final String getPlaLogros3() {
		return plaLogros3;
	}
	/**
	 * @param plaLogros3 The plaLogros3 to set.
	 */
	public final void setPlaLogros3(String plaLogros3) {
		this.plaLogros3 = plaLogros3;
	}
	/**
	 * @return Return the plaLogros4.
	 */
	public final String getPlaLogros4() {
		return plaLogros4;
	}
	/**
	 * @param plaLogros4 The plaLogros4 to set.
	 */
	public final void setPlaLogros4(String plaLogros4) {
		this.plaLogros4 = plaLogros4;
	}
	/**
	 * @return Return the plaPorcentaje1.
	 */
//	public final float getPlaPorcentaje1() {
//		return plaPorcentaje1;
//	}
//	/**
//	 * @param plaPorcentaje1 The plaPorcentaje1 to set.
//	 */
//	public final void setPlaPorcentaje1(float plaPorcentaje1) {
//		this.plaPorcentaje1 = plaPorcentaje1;
//	}
//	/**
//	 * @return Return the plaPorcentaje2.
//	 */
//	public final float getPlaPorcentaje2() {
//		return plaPorcentaje2;
//	}
//	/**
//	 * @param plaPorcentaje2 The plaPorcentaje2 to set.
//	 */
//	public final void setPlaPorcentaje2(float plaPorcentaje2) {
//		this.plaPorcentaje2 = plaPorcentaje2;
//	}
//	/**
//	 * @return Return the plaPorcentaje3.
//	 */
//	public final float getPlaPorcentaje3() {
//		return plaPorcentaje3;
//	}
//	/**
//	 * @param plaPorcentaje3 The plaPorcentaje3 to set.
//	 */
//	public final void setPlaPorcentaje3(float plaPorcentaje3) {
//		this.plaPorcentaje3 = plaPorcentaje3;
//	}
//	/**
//	 * @return Return the plaPorcentaje4.
//	 */
//	public final float getPlaPorcentaje4() {
//		return plaPorcentaje4;
//	}
//	/**
//	 * @param plaPorcentaje4 The plaPorcentaje4 to set.
//	 */
//	public final void setPlaPorcentaje4(float plaPorcentaje4) {
//		this.plaPorcentaje4 = plaPorcentaje4;
//	}
	/**
	 * @return Return the plaSeguimiento1.
	 */
	public final String getPlaSeguimiento1() {
		return plaSeguimiento1;
	}
	/**
	 * @param plaSeguimiento1 The plaSeguimiento1 to set.
	 */
	public final void setPlaSeguimiento1(String plaSeguimiento1) {
		this.plaSeguimiento1 = plaSeguimiento1;
	}
	/**
	 * @return Return the plaSeguimiento2.
	 */
	public final String getPlaSeguimiento2() {
		return plaSeguimiento2;
	}
	/**
	 * @param plaSeguimiento2 The plaSeguimiento2 to set.
	 */
	public final void setPlaSeguimiento2(String plaSeguimiento2) {
		this.plaSeguimiento2 = plaSeguimiento2;
	}
	/**
	 * @return Return the plaSeguimiento3.
	 */
	public final String getPlaSeguimiento3() {
		return plaSeguimiento3;
	}
	/**
	 * @param plaSeguimiento3 The plaSeguimiento3 to set.
	 */
	public final void setPlaSeguimiento3(String plaSeguimiento3) {
		this.plaSeguimiento3 = plaSeguimiento3;
	}
	/**
	 * @return Return the plaSeguimiento4.
	 */
	public final String getPlaSeguimiento4() {
		return plaSeguimiento4;
	}
	/**
	 * @param plaSeguimiento4 The plaSeguimiento4 to set.
	 */
	public final void setPlaSeguimiento4(String plaSeguimiento4) {
		this.plaSeguimiento4 = plaSeguimiento4;
	}
	/**
	 * @return Return the plaVerificacion1.
	 */
	public final String getPlaVerificacion1() {
		return plaVerificacion1;
	}
	/**
	 * @param plaVerificacion1 The plaVerificacion1 to set.
	 */
	public final void setPlaVerificacion1(String plaVerificacion1) {
		this.plaVerificacion1 = plaVerificacion1;
	}
	/**
	 * @return Return the plaVerificacion2.
	 */
	public final String getPlaVerificacion2() {
		return plaVerificacion2;
	}
	/**
	 * @param plaVerificacion2 The plaVerificacion2 to set.
	 */
	public final void setPlaVerificacion2(String plaVerificacion2) {
		this.plaVerificacion2 = plaVerificacion2;
	}
	/**
	 * @return Return the plaVerificacion3.
	 */
	public final String getPlaVerificacion3() {
		return plaVerificacion3;
	}
	/**
	 * @param plaVerificacion3 The plaVerificacion3 to set.
	 */
	public final void setPlaVerificacion3(String plaVerificacion3) {
		this.plaVerificacion3 = plaVerificacion3;
	}
	/**
	 * @return Return the plaVerificacion4.
	 */
	public final String getPlaVerificacion4() {
		return plaVerificacion4;
	}
	/**
	 * @param plaVerificacion4 The plaVerificacion4 to set.
	 */
	public final void setPlaVerificacion4(String plaVerificacion4) {
		this.plaVerificacion4 = plaVerificacion4;
	}
	/**
	 * @return Return the plaMostrarPorcentaje.
	 */
	public final boolean isPlaMostrarPorcentaje() {
		return plaMostrarPorcentaje;
	}
	/**
	 * @param plaMostrarPorcentaje The plaMostrarPorcentaje to set.
	 */
	public final void setPlaMostrarPorcentaje(boolean plaMostrarPorcentaje) {
		this.plaMostrarPorcentaje = plaMostrarPorcentaje;
	}
	/**
	 * @return Return the plaTipoMeta.
	 */
	public final int getPlaTipoMeta() {
		return plaTipoMeta;
	}
	/**
	 * @param plaTipoMeta The plaTipoMeta to set.
	 */
	public final void setPlaTipoMeta(int plaTipoMeta) {
		this.plaTipoMeta = plaTipoMeta;
	}
	/**
	 * @return Return the plaCantidad.
	 */
	public final long getPlaCantidad() {
		return plaCantidad;
	}
	/**
	 * @param plaCantidad The plaCantidad to set.
	 */
	public final void setPlaCantidad(long plaCantidad) {
		this.plaCantidad = plaCantidad;
	}
	/**
	 * @return Return the plaUnidadMedida.
	 */
	public final int getPlaUnidadMedida() {
		return plaUnidadMedida;
	}
	/**
	 * @param plaUnidadMedida The plaUnidadMedida to set.
	 */
	public final void setPlaUnidadMedida(int plaUnidadMedida) {
		this.plaUnidadMedida = plaUnidadMedida;
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
	
	public String getLblAreaGestion() {
		return lblAreaGestion;
	}
	
	public void setLblAreaGestion(String lblAreaGestion) {
		this.lblAreaGestion = lblAreaGestion;
	}
	
	public String getPlaPorcentaje1() {
		return plaPorcentaje1;
	}
	
	public void setPlaPorcentaje1(String plaPorcentaje1) {
		this.plaPorcentaje1 = plaPorcentaje1;
	}
	
	public String getPlaPorcentaje2() {
		return plaPorcentaje2;
	}
	
	public void setPlaPorcentaje2(String plaPorcentaje2) {
		this.plaPorcentaje2 = plaPorcentaje2;
	}
	
	public String getPlaPorcentaje3() {
		return plaPorcentaje3;
	}
	
	public void setPlaPorcentaje3(String plaPorcentaje3) {
		this.plaPorcentaje3 = plaPorcentaje3;
	}
	
	public String getPlaPorcentaje4() {
		return plaPorcentaje4;
	}
	
	public void setPlaPorcentaje4(String plaPorcentaje4) {
		this.plaPorcentaje4 = plaPorcentaje4;
	}
	
	public String getACCIONMEJORAMIENTO() {
		return ACCIONMEJORAMIENTO;
	}
	
	public void setACCIONMEJORAMIENTO(String aCCIONMEJORAMIENTO) {
		ACCIONMEJORAMIENTO = aCCIONMEJORAMIENTO;
	}
	
	public String getPLACOTROCUAL() {
		return PLACOTROCUAL;
	}
	
	public void setPLACOTROCUAL(String pLACOTROCUAL) {
		PLACOTROCUAL = pLACOTROCUAL;
	}
	
	public int getPlaPonderador() {
		return plaPonderador;
	}
	
	public void setPlaPonderador(int plaPonderador) {
		this.plaPonderador = plaPonderador;
	}
	public String getCalifEvalSeg1() {
		return califEvalSeg1;
	}
	public void setCalifEvalSeg1(String califEvalSeg1) {
		this.califEvalSeg1 = califEvalSeg1;
	}
	public String getCalifEvalSeg2() {
		return califEvalSeg2;
	}
	public void setCalifEvalSeg2(String califEvalSeg2) {
		this.califEvalSeg2 = califEvalSeg2;
	}
	public String getCalifEvalSeg3() {
		return califEvalSeg3;
	}
	public void setCalifEvalSeg3(String califEvalSeg3) {
		this.califEvalSeg3 = califEvalSeg3;
	}
	public String getCalifEvalSeg4() {
		return califEvalSeg4;
	}
	public void setCalifEvalSeg4(String califEvalSeg4) {
		this.califEvalSeg4 = califEvalSeg4;
	}	
	public String getObserEvalSeg1() {
		return obserEvalSeg1;
	}
	public void setObserEvalSeg1(String obserEvalSeg1) {
		this.obserEvalSeg1 = obserEvalSeg1;
	}
	public String getObserEvalSeg2() {
		return obserEvalSeg2;
	}
	public void setObserEvalSeg2(String obserEvalSeg2) {
		this.obserEvalSeg2 = obserEvalSeg2;
	}
	public String getObserEvalSeg3() {
		return obserEvalSeg3;
	}
	public void setObserEvalSeg3(String obserEvalSeg3) {
		this.obserEvalSeg3 = obserEvalSeg3;
	}
	public String getObserEvalSeg4() {
		return obserEvalSeg4;
	}
	public void setObserEvalSeg4(String obserEvalSeg4) {
		this.obserEvalSeg4 = obserEvalSeg4;
	}
	public String getEvalTexto1() {
		return evalTexto1;
	}
	public void setEvalTexto1(String evalTexto1) {
		this.evalTexto1 = evalTexto1;
	}
	public String getEvalTexto2() {
		return evalTexto2;
	}
	public void setEvalTexto2(String evalTexto2) {
		this.evalTexto2 = evalTexto2;
	}
	public String getEvalTexto3() {
		return evalTexto3;
	}
	public void setEvalTexto3(String evalTexto3) {
		this.evalTexto3 = evalTexto3;
	}
	public String getEvalTexto4() {
		return evalTexto4;
	}
	public void setEvalTexto4(String evalTexto4) {
		this.evalTexto4 = evalTexto4;
	}
	public int getRojoNaranjaSeguimiento1() {
		return rojoNaranjaSeguimiento1;
	}
	public void setRojoNaranjaSeguimiento1(int rojoNaranjaSeguimiento1) {
		this.rojoNaranjaSeguimiento1 = rojoNaranjaSeguimiento1;
	}	
	public int getRojoNaranjaSeguimiento2() {
		return rojoNaranjaSeguimiento2;
	}
	public void setRojoNaranjaSeguimiento2(int rojoNaranjaSeguimiento2) {
		this.rojoNaranjaSeguimiento2 = rojoNaranjaSeguimiento2;
	}
	public int getRojoNaranjaSeguimiento3() {
		return rojoNaranjaSeguimiento3;
	}
	public void setRojoNaranjaSeguimiento3(int rojoNaranjaSeguimiento3) {
		this.rojoNaranjaSeguimiento3 = rojoNaranjaSeguimiento3;
	}
	public int getRojoNaranjaSeguimiento4() {
		return rojoNaranjaSeguimiento4;
	}
	public void setRojoNaranjaSeguimiento4(int rojoNaranjaSeguimiento4) {
		this.rojoNaranjaSeguimiento4 = rojoNaranjaSeguimiento4;
	}
	public String getCalifActividad() {
		return califActividad;
	}
	public void setCalifActividad(String califActividad) {
		this.califActividad = califActividad;
	}
	public String getCalifTotalActividad() {
		return califTotalActividad;
	}
	public void setCalifTotalActividad(String califTotalActividad) {
		this.califTotalActividad = califTotalActividad;
	}
		
}
