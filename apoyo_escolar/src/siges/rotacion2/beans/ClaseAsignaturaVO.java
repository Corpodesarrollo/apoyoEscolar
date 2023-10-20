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
public class ClaseAsignaturaVO {
	private String nombreClase;
	private List listaDia;
	/**
	 * @return Return the listaDia.
	 */
	public List getListaDia() {
		return listaDia;
	}
	/**
	 * @param listaDia The listaDia to set.
	 */
	public void setListaDia(List listaDia) {
		this.listaDia = listaDia;
	}
	/**
	 * @return Return the nombreClase.
	 */
	public String getNombreClase() {
		return nombreClase;
	}
	/**
	 * @param nombreClase The nombreClase to set.
	 */
	public void setNombreClase(String nombreClase) {
		this.nombreClase = nombreClase;
	}
}
