/**
 * 
 */
package articulacion.area.vo;

import siges.common.vo.Vo;

/**
 * 31/07/2007 
 * @author Latined
 * @version 1.2
 */
public class FiltroVO extends Vo{
	private long filInstitucion;
	private int filMetodologia;
	private long filAnho;
	private int filPer;
	
	/**
	 * @return Return the filAnho.
	 */
	public long getFilAnho() {
		return filAnho;
	}
	/**
	 * @param filAnho The filAnho to set.
	 */
	public void setFilAnho(long filAnho) {
		this.filAnho = filAnho;
	}
	/**
	 * @return Return the filPer.
	 */
	public int getFilPer() {
		return filPer;
	}
	/**
	 * @param filPer The filPer to set.
	 */
	public void setFilPer(int filPer) {
		this.filPer = filPer;
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
	 * @return Return the filMetodologia.
	 */
	public int getFilMetodologia() {
		return filMetodologia;
	}
	/**
	 * @param filMetodologia The filMetodologia to set.
	 */
	public void setFilMetodologia(int filMetodologia) {
		this.filMetodologia = filMetodologia;
	}
}
