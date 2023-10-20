/**
 * 
 */
package poa.reportes.vo;

import siges.common.vo.Vo;

/**
 * Value Object para el manejo del formulario de filtro
 * 15/01/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class FiltroReportesVO extends Vo{
	private int filLocalidad=-99;
	private long filInstitucion;
	private int filVigencia;
	private int filEstatoPOA;
	private int filAreaGestionPOA;
	private int filLineaAccionPOA;
	private String filEstado;
	private String filRutaBase;
	private String filUsuario;
	private int filNivel;
	private int filTipoReporte;
	private int filAreaGestion;
	private int filLineaAccion;
	private int filFteFin;
	private int filPorEjec1;
	private int filPorEjec2;
	private int filObjetivo = -99;
	private int tipoActividad;
	private int filPeriodo;
	
	private String fechaModificacion1;
	private String fechaModificacion2;
	private String fechaModificacion3;
	private String fechaModificacion4;
	
	
	public int getTipoActividad() {
		return tipoActividad;
	}
	public void setTipoActividad(int tipoActividad) {
		this.tipoActividad = tipoActividad;
	}
	public int getFilObjetivo() {
		return filObjetivo;
	}
	public void setFilObjetivo(int filObjetivo) {
		this.filObjetivo = filObjetivo;
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
	 * @return Return the filInstitucion.
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
	 * @return Return the filLocalidad.
	 */
	public int getFilLocalidad() {
		return filLocalidad;
	}
	/**
	 * @param filLocalidad The filLocalidad to set.
	 */
	public void setFilLocalidad(int filLocalidad) {
		this.filLocalidad = filLocalidad;
	}
	/**
	 * @return Return the filVigencia.
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
	 * @return Return the filRutaBase.
	 */
	public String getFilRutaBase() {
		return filRutaBase;
	}
	/**
	 * @param filRutaBase The filRutaBase to set.
	 */
	public void setFilRutaBase(String filRutaBase) {
		this.filRutaBase = filRutaBase;
	}
	/**
	 * @return Return the filUsuario.
	 */
	public String getFilUsuario() {
		return filUsuario;
	}
	/**
	 * @param filUsuario The filUsuario to set.
	 */
	public void setFilUsuario(String filUsuario) {
		this.filUsuario = filUsuario;
	}
	/**
	 * @return Return the filNivel.
	 */
	public int getFilNivel() {
		return filNivel;
	}
	/**
	 * @param filNivel The filNivel to set.
	 */
	public void setFilNivel(int filNivel) {
		this.filNivel = filNivel;
	}
	public int getFilAreaGestionPOA() {
		return filAreaGestionPOA;
	}
	public void setFilAreaGestionPOA(int filAreaGestionPOA) {
		this.filAreaGestionPOA = filAreaGestionPOA;
	}
	public int getFilTipoReporte() {
		return filTipoReporte;
	}
	public void setFilTipoReporte(int filTipoReporte) {
		this.filTipoReporte = filTipoReporte;
	}
	public int getFilLineaAccionPOA() {
		return filLineaAccionPOA;
	}
	public void setFilLineaAccionPOA(int filLineaAccionPOA) {
		this.filLineaAccionPOA = filLineaAccionPOA;
	}
	
	public int getFilAreaGestion() {
		return filAreaGestion;
	}
	public void setFilAreaGestion(int filAreaGestion) {
		this.filAreaGestion = filAreaGestion;
	}
	public int getFilLineaAccion() {
		return filLineaAccion;
	}
	public void setFilLineaAccion(int filLineaAccion) {
		this.filLineaAccion = filLineaAccion;
	}
	public int getFilFteFin() {
		return filFteFin;
	}
	public void setFilFteFin(int filFteFin) {
		this.filFteFin = filFteFin;
	}
	public int getFilPorEjec1() {
		return filPorEjec1;
	}
	public void setFilPorEjec1(int filPorEjec1) {
		this.filPorEjec1 = filPorEjec1;
	}
	public int getFilPorEjec2() {
		return filPorEjec2;
	}
	public void setFilPorEjec2(int filPorEjec2) {
		this.filPorEjec2 = filPorEjec2;
	}
	public int getFilPeriodo() {
		return filPeriodo;
	}
	public void setFilPeriodo(int filPeriodo) {
		this.filPeriodo = filPeriodo;
	}
	public String getFechaModificacion1() {
		return fechaModificacion1;
	}
	public void setFechaModificacion1(String fechaModificacion1) {
		this.fechaModificacion1 = fechaModificacion1;
	}
	public String getFechaModificacion2() {
		return fechaModificacion2;
	}
	public void setFechaModificacion2(String fechaModificacion2) {
		this.fechaModificacion2 = fechaModificacion2;
	}
	public String getFechaModificacion3() {
		return fechaModificacion3;
	}
	public void setFechaModificacion3(String fechaModificacion3) {
		this.fechaModificacion3 = fechaModificacion3;
	}
	public String getFechaModificacion4() {
		return fechaModificacion4;
	}
	public void setFechaModificacion4(String fechaModificacion4) {
		this.fechaModificacion4 = fechaModificacion4;
	}
	
}
