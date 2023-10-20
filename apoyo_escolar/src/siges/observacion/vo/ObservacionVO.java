/**
 * 
 */
package siges.observacion.vo;

import siges.common.vo.Vo;

/**
 * 25/11/2007 
 * @author Latined
 * @version 1.2
 */
public class ObservacionVO extends Vo{
	private long codigo;
	private String nombre;
	private String apellido;
	private String observacion;
	
	/**
	 * @return Return the nombre.
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre The nombre to set.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return Return the observacion.
	 */
	public String getObservacion() {
		return observacion;
	}
	/**
	 * @param observacion The observacion to set.
	 */
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	/**
	 * @return Return the codigo.
	 */
	public long getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo The codigo to set.
	 */
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
}
