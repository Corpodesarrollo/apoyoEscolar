package poa.planeacion.vo;


import siges.common.vo.Vo;

/**
 * Value Object de manejo del formulario de filtro
 * 14/01/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class FiltroPlaneacionVO  extends Vo{
	private long filInstitucion;
	private int filVigencia;
	private int filAreaGestion;
	private float filPonderado;
	private int filEstatoPOA;
	private int filHabilitado;
	private String filEstado;
	private String filObservacion;
	private String filObservacionLinea;
	private boolean filFechaHabil;
	private String filRangoFechas;
	
	private String lblVigencia;
	private String lblObjetivo;
	private String lblActividad;
	private String lblAreaGestion;
	private String lblPonderador;
	private String lblLineaAccion;
	private String lblTipoMeta;
	private String lblMetaAnualCantidad;
	private String lblMetaAnualUnidad;
	private String lblMetaAnualCual;
	private String lblFuenteFinanciera;
	private String lblPresupuesto;
	private String lblResponsable;
	private String lblFecha;
	private String lblCronograma1;
	private String lblCronograma2;
	private String lblCronograma3;
	private String lblCronograma4;
	
	/**
	 * @return Returns the filAreaGestion.
	 */
	public int getFilAreaGestion() {
		return filAreaGestion;
	}
	/**
	 * @param filAreaGestion The filAreaGestion to set.
	 */
	public void setFilAreaGestion(int filAreaGestion) {
		this.filAreaGestion = filAreaGestion;
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
	 * @return Return the lblFuenteFinanciera.
	 */
	public String getLblFuenteFinanciera() {
		return lblFuenteFinanciera;
	}
	/**
	 * @param lblFuenteFinanciera The lblFuenteFinanciera to set.
	 */
	public void setLblFuenteFinanciera(String lblFuenteFinanciera) {
		this.lblFuenteFinanciera = lblFuenteFinanciera;
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
	 * @return Return the lblMetaAnualCantidad.
	 */
	public String getLblMetaAnualCantidad() {
		return lblMetaAnualCantidad;
	}
	/**
	 * @param lblMetaAnualCantidad The lblMetaAnualCantidad to set.
	 */
	public void setLblMetaAnualCantidad(String lblMetaAnualCantidad) {
		this.lblMetaAnualCantidad = lblMetaAnualCantidad;
	}
	/**
	 * @return Return the lblMetaAnualCual.
	 */
	public String getLblMetaAnualCual() {
		return lblMetaAnualCual;
	}
	/**
	 * @param lblMetaAnualCual The lblMetaAnualCual to set.
	 */
	public void setLblMetaAnualCual(String lblMetaAnualCual) {
		this.lblMetaAnualCual = lblMetaAnualCual;
	}
	/**
	 * @return Return the lblMetaAnualUnidad.
	 */
	public String getLblMetaAnualUnidad() {
		return lblMetaAnualUnidad;
	}
	/**
	 * @param lblMetaAnualUnidad The lblMetaAnualUnidad to set.
	 */
	public void setLblMetaAnualUnidad(String lblMetaAnualUnidad) {
		this.lblMetaAnualUnidad = lblMetaAnualUnidad;
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
	 * @return Return the lblPonderador.
	 */
	public String getLblPonderador() {
		return lblPonderador;
	}
	/**
	 * @param lblPonderador The lblPonderador to set.
	 */
	public void setLblPonderador(String lblPonderador) {
		this.lblPonderador = lblPonderador;
	}
	/**
	 * @return Return the lblPresupuesto.
	 */
	public String getLblPresupuesto() {
		return lblPresupuesto;
	}
	/**
	 * @param lblPresupuesto The lblPresupuesto to set.
	 */
	public void setLblPresupuesto(String lblPresupuesto) {
		this.lblPresupuesto = lblPresupuesto;
	}
	/**
	 * @return Return the lblResponsable.
	 */
	public String getLblResponsable() {
		return lblResponsable;
	}
	/**
	 * @param lblResponsable The lblResponsable to set.
	 */
	public void setLblResponsable(String lblResponsable) {
		this.lblResponsable = lblResponsable;
	}
	/**
	 * @return Return the lblTipoMeta.
	 */
	public String getLblTipoMeta() {
		return lblTipoMeta;
	}
	/**
	 * @param lblTipoMeta The lblTipoMeta to set.
	 */
	public void setLblTipoMeta(String lblTipoMeta) {
		this.lblTipoMeta = lblTipoMeta;
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
	 * @return Return the filEstado.
	 */
	public String getFilEstado() {
		return filEstado;
	}
	/**
	 * @param filEstado The filEstado to set.
	 */
	public void setFilEstado(String filEstado) {
		this.filEstado = filEstado;
	}
	/**
	 * @return Return the filObservacion.
	 */
	public String getFilObservacion() {
		return filObservacion;
	}
	/**
	 * @param filObservacion The filObservacion to set.
	 */
	public void setFilObservacion(String filObservacion) {
		this.filObservacion = filObservacion;
	}
	/**
	 * @return Return the filObservacionLinea.
	 */
	public String getFilObservacionLinea() {
		return filObservacionLinea;
	}
	/**
	 * @param filObservacionLinea The filObservacionLinea to set.
	 */
	public void setFilObservacionLinea(String filObservacionLinea) {
		this.filObservacionLinea = filObservacionLinea;
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
}
