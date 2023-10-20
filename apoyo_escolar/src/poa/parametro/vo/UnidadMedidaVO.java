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
public class UnidadMedidaVO extends Vo{
	private int uniCodigo;
	private String uniNombre;
	private String uniDescripcion;
	private int uniOrden;
	private boolean uniCual;
	
	/**
	 * @return Return the uniCodigo.
	 */
	public int getUniCodigo() {
		return uniCodigo;
	}
	/**
	 * @param uniCodigo The uniCodigo to set.
	 */
	public void setUniCodigo(int uniCodigo) {
		this.uniCodigo = uniCodigo;
	}
	/**
	 * @return Return the uniCual.
	 */
	public boolean isUniCual() {
		return uniCual;
	}
	/**
	 * @param uniCual The uniCual to set.
	 */
	public void setUniCual(boolean uniCual) {
		this.uniCual = uniCual;
	}
	/**
	 * @return Return the uniDescripcion.
	 */
	public String getUniDescripcion() {
		return uniDescripcion;
	}
	/**
	 * @param uniDescripcion The uniDescripcion to set.
	 */
	public void setUniDescripcion(String uniDescripcion) {
		this.uniDescripcion = uniDescripcion;
	}
	/**
	 * @return Return the uniNombre.
	 */
	public String getUniNombre() {
		return uniNombre;
	}
	/**
	 * @param uniNombre The uniNombre to set.
	 */
	public void setUniNombre(String uniNombre) {
		this.uniNombre = uniNombre;
	}
	/**
	 * @return Return the uniOrden.
	 */
	public int getUniOrden() {
		return uniOrden;
	}
	/**
	 * @param uniOrden The uniOrden to set.
	 */
	public void setUniOrden(int uniOrden) {
		this.uniOrden = uniOrden;
	}
}
