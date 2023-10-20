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
public class FuenteFinanciacionVO extends Vo{
	private int fueCodigo;
	private String fueNombre;
	private String fueDescripcion;
	private int fueOrden;
	private boolean fuePresupuesto;
	private boolean fueOculta;
	
	/**
	 * @return Return the fueCodigo.
	 */
	public int getFueCodigo() {
		return fueCodigo;
	}
	/**
	 * @param fueCodigo The fueCodigo to set.
	 */
	public void setFueCodigo(int fueCodigo) {
		this.fueCodigo = fueCodigo;
	}
	/**
	 * @return Return the fueDescripcion.
	 */
	public String getFueDescripcion() {
		return fueDescripcion;
	}
	/**
	 * @param fueDescripcion The fueDescripcion to set.
	 */
	public void setFueDescripcion(String fueDescripcion) {
		this.fueDescripcion = fueDescripcion;
	}
	/**
	 * @return Return the fueNombre.
	 */
	public String getFueNombre() {
		return fueNombre;
	}
	/**
	 * @param fueNombre The fueNombre to set.
	 */
	public void setFueNombre(String fueNombre) {
		this.fueNombre = fueNombre;
	}
	/**
	 * @return Return the fueOculta.
	 */
	public boolean isFueOculta() {
		return fueOculta;
	}
	/**
	 * @param fueOculta The fueOculta to set.
	 */
	public void setFueOculta(boolean fueOculta) {
		this.fueOculta = fueOculta;
	}
	/**
	 * @return Return the fueOrden.
	 */
	public int getFueOrden() {
		return fueOrden;
	}
	/**
	 * @param fueOrden The fueOrden to set.
	 */
	public void setFueOrden(int fueOrden) {
		this.fueOrden = fueOrden;
	}
	/**
	 * @return Return the fuePresupuesto.
	 */
	public boolean isFuePresupuesto() {
		return fuePresupuesto;
	}
	/**
	 * @param fuePresupuesto The fuePresupuesto to set.
	 */
	public void setFuePresupuesto(boolean fuePresupuesto) {
		this.fuePresupuesto = fuePresupuesto;
	}
}
