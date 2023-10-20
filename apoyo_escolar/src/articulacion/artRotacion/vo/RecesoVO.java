/**
 * 
 */
package articulacion.artRotacion.vo;

import siges.common.vo.Vo;

/**
 * 9/10/2007 
 * @author Latined
 * @version 1.2
 */
public class RecesoVO extends Vo{

	private long resCodigo;
	private long resEstructura;
	private int resTipo;
	private String resTipoNombre;
	private String resNombre; 
	private int resAnhoVigencia;
	private int resPerVigencia;
	private int resClase;
	private int resDuracionHor;
	private int resDuracionMin;
	
	/**
	 * @return Return the resAnhoVigencia.
	 */
	public int getResAnhoVigencia() {
		return resAnhoVigencia;
	}
	/**
	 * @param resAnhoVigencia The resAnhoVigencia to set.
	 */
	public void setResAnhoVigencia(int resAnhoVigencia) {
		this.resAnhoVigencia = resAnhoVigencia;
	}
	/**
	 * @return Return the resClase.
	 */
	public int getResClase() {
		return resClase;
	}
	/**
	 * @param resClase The resClase to set.
	 */
	public void setResClase(int resClase) {
		this.resClase = resClase;
	}
	/**
	 * @return Return the resCodigo.
	 */
	public long getResCodigo() {
		return resCodigo;
	}
	/**
	 * @param resCodigo The resCodigo to set.
	 */
	public void setResCodigo(long resCodigo) {
		this.resCodigo = resCodigo;
	}
	/**
	 * @return Return the resDuracionHor.
	 */
	public int getResDuracionHor() {
		return resDuracionHor;
	}
	/**
	 * @param resDuracionHor The resDuracionHor to set.
	 */
	public void setResDuracionHor(int resDuracionHor) {
		this.resDuracionHor = resDuracionHor;
	}
	/**
	 * @return Return the resDuracionMin.
	 */
	public int getResDuracionMin() {
		return resDuracionMin;
	}
	/**
	 * @param resDuracionMin The resDuracionMin to set.
	 */
	public void setResDuracionMin(int resDuracionMin) {
		this.resDuracionMin = resDuracionMin;
	}
	/**
	 * @return Return the resEstructura.
	 */
	public long getResEstructura() {
		return resEstructura;
	}
	/**
	 * @param resEstructura The resEstructura to set.
	 */
	public void setResEstructura(long resEstructura) {
		this.resEstructura = resEstructura;
	}
	/**
	 * @return Return the resNombre.
	 */
	public String getResNombre() {
		return resNombre;
	}
	/**
	 * @param resNombre The resNombre to set.
	 */
	public void setResNombre(String resNombre) {
		this.resNombre = resNombre;
	}
	/**
	 * @return Return the resPerVigencia.
	 */
	public int getResPerVigencia() {
		return resPerVigencia;
	}
	/**
	 * @param resPerVigencia The resPerVigencia to set.
	 */
	public void setResPerVigencia(int resPerVigencia) {
		this.resPerVigencia = resPerVigencia;
	}
	/**
	 * @return Return the resTipo.
	 */
	public int getResTipo() {
		return resTipo;
	}
	/**
	 * @param resTipo The resTipo to set.
	 */
	public void setResTipo(int resTipo) {
		this.resTipo = resTipo;
	}
	/**
	 * @return Return the resTipoNombre.
	 */
	public final String getResTipoNombre() {
		return resTipoNombre;
	}
	/**
	 * @param resTipoNombre The resTipoNombre to set.
	 */
	public final void setResTipoNombre(String resTipoNombre) {
		this.resTipoNombre = resTipoNombre;
	}
}
