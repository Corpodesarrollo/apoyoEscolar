/**
 * 
 */
package siges.rotacion2.beans;

import java.util.List;

/**
 * 18/12/2007 
 * @author Latined
 * @version 1.2
 */
public class HorarioAsignaturaVO {
	private String nombreAsignatura;
	private String nombreIteracion;
	private List listaClase;
	/**
	 * @return Return the listaClase.
	 */
	public List getListaClase() {
		return listaClase;
	}
	/**
	 * @param listaClase The listaClase to set.
	 */
	public void setListaClase(List listaClase) {
		this.listaClase = listaClase;
	}
	/**
	 * @return Return the nombreAsignatura.
	 */
	public String getNombreAsignatura() {
		return nombreAsignatura;
	}
	/**
	 * @param nombreAsignatura The nombreAsignatura to set.
	 */
	public void setNombreAsignatura(String nombreAsignatura) {
		this.nombreAsignatura = nombreAsignatura;
	}
	/**
	 * @return Return the nombreIteracion.
	 */
	public String getNombreIteracion() {
		return nombreIteracion;
	}
	/**
	 * @param nombreIteracion The nombreIteracion to set.
	 */
	public void setNombreIteracion(String nombreIteracion) {
		this.nombreIteracion = nombreIteracion;
	}
}
