package poa.seguimiento.vo;

import siges.common.vo.Vo;

/**
 * Value Object de manejo del formulario de filtro de seguimiento
 * 14/01/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class FiltroSeguimientoVO  extends Vo{
	private long filInstitucion;
	private int filVigencia;
	private float filPonderado;
	private int filEstatoPOA;
	private int filHabilitado;
	private boolean filFechaHabil;
	private int filPeriodoHabil;
	private String filRangoFechas;
	private String filRangoFechas2;
	
	private String lblVigencia;
	private String lblObjetivo;
	private String lblActividad;
	private String lblAreaGestion;
	private String lblLineaAccion;
	private String lblTipoMeta;
	private String lblFecha;
	private String lblFechaReal;
	private String lblCronograma1;
	private String lblCronograma2;
	private String lblCronograma3;
	private String lblCronograma4;
	private String lblSeguimiento1;
	private String lblSeguimiento2;
	private String lblSeguimiento3;
	private String lblSeguimiento4;
	private String lblPorcentaje1;
	private String lblPorcentaje2;
	private String lblPorcentaje3;
	private String lblPorcentaje4;
	private String lblVerificacion1;
	private String lblVerificacion2;
	private String lblVerificacion3;
	private String lblVerificacion4;
	private String lblLogros1;
	private String lblLogros2;
	private String lblLogros3;
	private String lblLogros4;
	
	private String fechaPrevistaEntrega;
	private String fechaRealEntrega;
	private Long presupuestoEjecutado;
	
	private String filVigenciaPoa;
	
	public Long getPresupuestoEjecutado() {
		return presupuestoEjecutado;
	}
	public void setPresupuestoEjecutado(Long presupuestoEjecutado) {
		this.presupuestoEjecutado = presupuestoEjecutado;
	}
	public String getFechaPrevistaEntrega() {
		return fechaPrevistaEntrega;
	}
	public void setFechaPrevistaEntrega(String fechaPrevistaEntrega) {
		this.fechaPrevistaEntrega = fechaPrevistaEntrega;
	}
	public String getFechaRealEntrega() {
		return fechaRealEntrega;
	}
	public void setFechaRealEntrega(String fechaRealEntrega) {
		this.fechaRealEntrega = fechaRealEntrega;
	}
	
		
	
	
	/**
	 * @return Returns the filInstitucion.
	 */
	public long getFilInstitucion() {
		return filInstitucion;
	}
	/**
	 * @param filInstitucion The filInstitucion to set.
	 */
	public void setFilInstitucion(long filInstitucion) {
		this.filInstitucion = filInstitucion;
	}
	/**
	 * @return Returns the filVigencia.
	 */
	public int getFilVigencia() {
		return filVigencia;
	}
	/**
	 * @param filVigencia The filVigencia to set.
	 */
	public void setFilVigencia(int filVigencia) {
		this.filVigencia = filVigencia;
	}
	/**
	 * @return Return the filPonderado.
	 */
	public float getFilPonderado() {
		return filPonderado;
	}
	/**
	 * @param filPonderado The filPonderado to set.
	 */
	public void setFilPonderado(float filPonderado) {
		this.filPonderado = filPonderado;
	}
	/**
	 * @return Return the lblActividad.
	 */
	public String getLblActividad() {
		return lblActividad;
	}
	/**
	 * @param lblActividad The lblActividad to set.
	 */
	public void setLblActividad(String lblActividad) {
		this.lblActividad = lblActividad;
	}
	/**
	 * @return Return the lblAreaGestion.
	 */
	public String getLblAreaGestion() {
		return lblAreaGestion;
	}
	/**
	 * @param lblAreaGestion The lblAreaGestion to set.
	 */
	public void setLblAreaGestion(String lblAreaGestion) {
		this.lblAreaGestion = lblAreaGestion;
	}
	/**
	 * @return Return the lblCronograma1.
	 */
	public String getLblCronograma1() {
		return lblCronograma1;
	}
	/**
	 * @param lblCronograma1 The lblCronograma1 to set.
	 */
	public void setLblCronograma1(String lblCronograma1) {
		this.lblCronograma1 = lblCronograma1;
	}
	/**
	 * @return Return the lblCronograma2.
	 */
	public String getLblCronograma2() {
		return lblCronograma2;
	}
	/**
	 * @param lblCronograma2 The lblCronograma2 to set.
	 */
	public void setLblCronograma2(String lblCronograma2) {
		this.lblCronograma2 = lblCronograma2;
	}
	/**
	 * @return Return the lblCronograma3.
	 */
	public String getLblCronograma3() {
		return lblCronograma3;
	}
	/**
	 * @param lblCronograma3 The lblCronograma3 to set.
	 */
	public void setLblCronograma3(String lblCronograma3) {
		this.lblCronograma3 = lblCronograma3;
	}
	/**
	 * @return Return the lblCronograma4.
	 */
	public String getLblCronograma4() {
		return lblCronograma4;
	}
	/**
	 * @param lblCronograma4 The lblCronograma4 to set.
	 */
	public void setLblCronograma4(String lblCronograma4) {
		this.lblCronograma4 = lblCronograma4;
	}
	/**
	 * @return Return the lblFecha.
	 */
	public String getLblFecha() {
		return lblFecha;
	}
	/**
	 * @param lblFecha The lblFecha to set.
	 */
	public void setLblFecha(String lblFecha) {
		this.lblFecha = lblFecha;
	}
	/**
	 * @return Return the lblLineaAccion.
	 */
	public String getLblLineaAccion() {
		return lblLineaAccion;
	}
	/**
	 * @param lblLineaAccion The lblLineaAccion to set.
	 */
	public void setLblLineaAccion(String lblLineaAccion) {
		this.lblLineaAccion = lblLineaAccion;
	}
	/**
	 * @return Return the lblObjetivo.
	 */
	public String getLblObjetivo() {
		return lblObjetivo;
	}
	/**
	 * @param lblObjetivo The lblObjetivo to set.
	 */
	public void setLblObjetivo(String lblObjetivo) {
		this.lblObjetivo = lblObjetivo;
	}
	/**
	 * @return Return the lblVigencia.
	 */
	public String getLblVigencia() {
		return lblVigencia;
	}
	/**
	 * @param lblVigencia The lblVigencia to set.
	 */
	public void setLblVigencia(String lblVigencia) {
		this.lblVigencia = lblVigencia;
	}
	/**
	 * @return Return the filEstatoPOA.
	 */
	public int getFilEstatoPOA() {
		return filEstatoPOA;
	}
	/**
	 * @param filEstatoPOA The filEstatoPOA to set.
	 */
	public void setFilEstatoPOA(int filEstatoPOA) {
		this.filEstatoPOA = filEstatoPOA;
	}
	/**
	 * @return Return the filHabilitado.
	 */
	public int getFilHabilitado() {
		return filHabilitado;
	}
	/**
	 * @param filHabilitado The filHabilitado to set.
	 */
	public void setFilHabilitado(int filHabilitado) {
		this.filHabilitado = filHabilitado;
	}
	/**
	 * @return Return the filFechaHabil.
	 */
	public final boolean isFilFechaHabil() {
		return filFechaHabil;
	}
	/**
	 * @param filFechaHabil The filFechaHabil to set.
	 */
	public final void setFilFechaHabil(boolean filFechaHabil) {
		this.filFechaHabil = filFechaHabil;
	}
	/**
	 * @return Return the filRangoFechas.
	 */
	public final String getFilRangoFechas() {
		return filRangoFechas;
	}
	/**
	 * @param filRangoFechas The filRangoFechas to set.
	 */
	public final void setFilRangoFechas(String filRangoFechas) {
		this.filRangoFechas = filRangoFechas;
	}
	/**
	 * @return Return the lblFechaReal.
	 */
	public final String getLblFechaReal() {
		return lblFechaReal;
	}
	/**
	 * @param lblFechaReal The lblFechaReal to set.
	 */
	public final void setLblFechaReal(String lblFechaReal) {
		this.lblFechaReal = lblFechaReal;
	}
	/**
	 * @return Return the lblLogros1.
	 */
	public final String getLblLogros1() {
		return lblLogros1;
	}
	/**
	 * @param lblLogros1 The lblLogros1 to set.
	 */
	public final void setLblLogros1(String lblLogros1) {
		this.lblLogros1 = lblLogros1;
	}
	/**
	 * @return Return the lblLogros2.
	 */
	public final String getLblLogros2() {
		return lblLogros2;
	}
	/**
	 * @param lblLogros2 The lblLogros2 to set.
	 */
	public final void setLblLogros2(String lblLogros2) {
		this.lblLogros2 = lblLogros2;
	}
	/**
	 * @return Return the lblLogros3.
	 */
	public final String getLblLogros3() {
		return lblLogros3;
	}
	/**
	 * @param lblLogros3 The lblLogros3 to set.
	 */
	public final void setLblLogros3(String lblLogros3) {
		this.lblLogros3 = lblLogros3;
	}
	/**
	 * @return Return the lblLogros4.
	 */
	public final String getLblLogros4() {
		return lblLogros4;
	}
	/**
	 * @param lblLogros4 The lblLogros4 to set.
	 */
	public final void setLblLogros4(String lblLogros4) {
		this.lblLogros4 = lblLogros4;
	}
	/**
	 * @return Return the lblSeguimiento1.
	 */
	public final String getLblSeguimiento1() {
		return lblSeguimiento1;
	}
	/**
	 * @param lblSeguimiento1 The lblSeguimiento1 to set.
	 */
	public final void setLblSeguimiento1(String lblSeguimiento1) {
		this.lblSeguimiento1 = lblSeguimiento1;
	}
	/**
	 * @return Return the lblSeguimiento2.
	 */
	public final String getLblSeguimiento2() {
		return lblSeguimiento2;
	}
	/**
	 * @param lblSeguimiento2 The lblSeguimiento2 to set.
	 */
	public final void setLblSeguimiento2(String lblSeguimiento2) {
		this.lblSeguimiento2 = lblSeguimiento2;
	}
	/**
	 * @return Return the lblSeguimiento3.
	 */
	public final String getLblSeguimiento3() {
		return lblSeguimiento3;
	}
	/**
	 * @param lblSeguimiento3 The lblSeguimiento3 to set.
	 */
	public final void setLblSeguimiento3(String lblSeguimiento3) {
		this.lblSeguimiento3 = lblSeguimiento3;
	}
	/**
	 * @return Return the lblSeguimiento4.
	 */
	public final String getLblSeguimiento4() {
		return lblSeguimiento4;
	}
	/**
	 * @param lblSeguimiento4 The lblSeguimiento4 to set.
	 */
	public final void setLblSeguimiento4(String lblSeguimiento4) {
		this.lblSeguimiento4 = lblSeguimiento4;
	}
	/**
	 * @return Return the lblVerificacion1.
	 */
	public final String getLblVerificacion1() {
		return lblVerificacion1;
	}
	/**
	 * @param lblVerificacion1 The lblVerificacion1 to set.
	 */
	public final void setLblVerificacion1(String lblVerificacion1) {
		this.lblVerificacion1 = lblVerificacion1;
	}
	/**
	 * @return Return the lblVerificacion2.
	 */
	public final String getLblVerificacion2() {
		return lblVerificacion2;
	}
	/**
	 * @param lblVerificacion2 The lblVerificacion2 to set.
	 */
	public final void setLblVerificacion2(String lblVerificacion2) {
		this.lblVerificacion2 = lblVerificacion2;
	}
	/**
	 * @return Return the lblVerificacion3.
	 */
	public final String getLblVerificacion3() {
		return lblVerificacion3;
	}
	/**
	 * @param lblVerificacion3 The lblVerificacion3 to set.
	 */
	public final void setLblVerificacion3(String lblVerificacion3) {
		this.lblVerificacion3 = lblVerificacion3;
	}
	/**
	 * @return Return the lblVerificacion4.
	 */
	public final String getLblVerificacion4() {
		return lblVerificacion4;
	}
	/**
	 * @param lblVerificacion4 The lblVerificacion4 to set.
	 */
	public final void setLblVerificacion4(String lblVerificacion4) {
		this.lblVerificacion4 = lblVerificacion4;
	}
	/**
	 * @return Return the lblPorcentaje1.
	 */
	public final String getLblPorcentaje1() {
		return lblPorcentaje1;
	}
	/**
	 * @param lblPorcentaje1 The lblPorcentaje1 to set.
	 */
	public final void setLblPorcentaje1(String lblPorcentaje1) {
		this.lblPorcentaje1 = lblPorcentaje1;
	}
	/**
	 * @return Return the lblPorcentaje2.
	 */
	public final String getLblPorcentaje2() {
		return lblPorcentaje2;
	}
	/**
	 * @param lblPorcentaje2 The lblPorcentaje2 to set.
	 */
	public final void setLblPorcentaje2(String lblPorcentaje2) {
		this.lblPorcentaje2 = lblPorcentaje2;
	}
	/**
	 * @return Return the lblPorcentaje3.
	 */
	public final String getLblPorcentaje3() {
		return lblPorcentaje3;
	}
	/**
	 * @param lblPorcentaje3 The lblPorcentaje3 to set.
	 */
	public final void setLblPorcentaje3(String lblPorcentaje3) {
		this.lblPorcentaje3 = lblPorcentaje3;
	}
	/**
	 * @return Return the lblPorcentaje4.
	 */
	public final String getLblPorcentaje4() {
		return lblPorcentaje4;
	}
	/**
	 * @param lblPorcentaje4 The lblPorcentaje4 to set.
	 */
	public final void setLblPorcentaje4(String lblPorcentaje4) {
		this.lblPorcentaje4 = lblPorcentaje4;
	}
	/**
	 * @return Return the filPeriodoHabil.
	 */
	public final int getFilPeriodoHabil() {
		return filPeriodoHabil;
	}
	/**
	 * @param filPeriodoHabil The filPeriodoHabil to set.
	 */
	public final void setFilPeriodoHabil(int filPeriodoHabil) {
		this.filPeriodoHabil = filPeriodoHabil;
	}
	/**
	 * @return Return the lblTipoMeta.
	 */
	public final String getLblTipoMeta() {
		return lblTipoMeta;
	}
	/**
	 * @param lblTipoMeta The lblTipoMeta to set.
	 */
	public final void setLblTipoMeta(String lblTipoMeta) {
		this.lblTipoMeta = lblTipoMeta;
	}
	/**
	 * @return Return the filRangoFechas2.
	 */
	public final String getFilRangoFechas2() {
		return filRangoFechas2;
	}
	/**
	 * @param filRangoFechas2 The filRangoFechas2 to set.
	 */
	public final void setFilRangoFechas2(String filRangoFechas2) {
		this.filRangoFechas2 = filRangoFechas2;
	}
	public String getFilVigenciaPoa() {
		return filVigenciaPoa;
	}
	public void setFilVigenciaPoa(String filVigenciaPoa) {
		this.filVigenciaPoa = filVigenciaPoa;
	}
}
