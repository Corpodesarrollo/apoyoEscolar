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
public class TipoMetaVO extends Vo{
	private int tipCodigo;
	private String tipNombre;
	private String tipDescripcion;
	private int tipOrden;
	private boolean tipPorcentaje;
	
	/**
	 * @return Return the tipCodigo.
	 */
	public int getTipCodigo() {
		return tipCodigo;
	}
	/**
	 * @param tipCodigo The tipCodigo to set.
	 */
	public void setTipCodigo(int tipCodigo) {
		this.tipCodigo = tipCodigo;
	}
	/**
	 * @return Return the tipDescripcion.
	 */
	public String getTipDescripcion() {
		return tipDescripcion;
	}
	/**
	 * @param tipDescripcion The tipDescripcion to set.
	 */
	public void setTipDescripcion(String tipDescripcion) {
		this.tipDescripcion = tipDescripcion;
	}
	/**
	 * @return Return the tipNombre.
	 */
	public String getTipNombre() {
		return tipNombre;
	}
	/**
	 * @param tipNombre The tipNombre to set.
	 */
	public void setTipNombre(String tipNombre) {
		this.tipNombre = tipNombre;
	}
	/**
	 * @return Return the tipOrden.
	 */
	public int getTipOrden() {
		return tipOrden;
	}
	/**
	 * @param tipOrden The tipOrden to set.
	 */
	public void setTipOrden(int tipOrden) {
		this.tipOrden = tipOrden;
	}
	/**
	 * @return Return the tipPorcentaje.
	 */
	public final boolean isTipPorcentaje() {
		return tipPorcentaje;
	}
	/**
	 * @param tipPorcentaje The tipPorcentaje to set.
	 */
	public final void setTipPorcentaje(boolean tipPorcentaje) {
		this.tipPorcentaje = tipPorcentaje;
	}

}
