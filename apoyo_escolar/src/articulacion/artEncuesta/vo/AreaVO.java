/**
 * 
 */
package articulacion.artEncuesta.vo;

import java.util.List;

/**
 * 18/11/2007 
 * @author Latined
 * @version 1.2
 */
public class AreaVO {
	private long codigo;
	private String nombre;
	private List listaAsignatura;
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
	/**
	 * @return Return the listaAsignatura.
	 */
	public List getListaAsignatura() {
		return listaAsignatura;
	}
	/**
	 * @param listaAsignatura The listaAsignatura to set.
	 */
	public void setListaAsignatura(List listaAsignatura) {
		this.listaAsignatura = listaAsignatura;
	}
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
}
