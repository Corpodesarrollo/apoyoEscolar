/**
 * 
 */
package poa.consulta.vo;

import siges.common.vo.Vo;

/**
 * Value Object para el manejo del formulario de filtro
 * 15/01/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class FiltroConsultaVO extends Vo{
	private int filLocalidad=-99;
	private long filInstitucion;
	private int filVigencia;
	private int filEstatoPOA;
	private String filEstado;
	private String filRutaBase;
	private String filUsuario;
	private int filNivel;
	
	private boolean filTieneLocalidad=false;
	
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
	/**
	 * @return the filTieneLocalidad
	 */
	public boolean isFilTieneLocalidad() {
		return filTieneLocalidad;
	}
	/**
	 * @param filTieneLocalidad the filTieneLocalidad to set
	 */
	public void setFilTieneLocalidad(boolean filTieneLocalidad) {
		this.filTieneLocalidad = filTieneLocalidad;
	}

}
