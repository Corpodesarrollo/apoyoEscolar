/**
 * 
 */
package poa.parametro.vo;

import siges.common.vo.Vo;

/**
 * 14/02/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class LineaAccionVO extends Vo{
	private int linAreaGestion;
	private int linCodigo;
	private String linNombre;
	private String linDescripcion;
	private int linOrden;
	
	/**
	 * @return Return the linAreaGestion.
	 */
	public int getLinAreaGestion() {
		return linAreaGestion;
	}
	/**
	 * @param linAreaGestion The linAreaGestion to set.
	 */
	public void setLinAreaGestion(int linAreaGestion) {
		this.linAreaGestion = linAreaGestion;
	}
	/**
	 * @return Return the linCodigo.
	 */
	public int getLinCodigo() {
		return linCodigo;
	}
	/**
	 * @param linCodigo The linCodigo to set.
	 */
	public void setLinCodigo(int linCodigo) {
		this.linCodigo = linCodigo;
	}
	/**
	 * @return Return the linDescripcion.
	 */
	public String getLinDescripcion() {
		return linDescripcion;
	}
	/**
	 * @param linDescripcion The linDescripcion to set.
	 */
	public void setLinDescripcion(String linDescripcion) {
		this.linDescripcion = linDescripcion;
	}
	/**
	 * @return Return the linNombre.
	 */
	public String getLinNombre() {
		return linNombre;
	}
	/**
	 * @param linNombre The linNombre to set.
	 */
	public void setLinNombre(String linNombre) {
		this.linNombre = linNombre;
	}
	/**
	 * @return Return the linOrden.
	 */
	public int getLinOrden() {
		return linOrden;
	}
	/**
	 * @param linOrden The linOrden to set.
	 */
	public void setLinOrden(int linOrden) {
		this.linOrden = linOrden;
	}
}
