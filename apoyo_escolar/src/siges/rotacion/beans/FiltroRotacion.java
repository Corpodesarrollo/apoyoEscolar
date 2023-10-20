/**
 * 
 */
package siges.rotacion.beans;

import siges.common.vo.Vo;

/**
 * 11/10/2007 
 * @author Latined
 * @version 1.2
 */
public class FiltroRotacion extends Vo{
	private int filAnhoVigencia;
	private int filMetodologia;

	/**
	 * @return Return the filAnhoVigencia.
	 */
	public int getFilAnhoVigencia() {
		return filAnhoVigencia;
	}

	/**
	 * @param filAnhoVigencia The filAnhoVigencia to set.
	 */
	public void setFilAnhoVigencia(int filAnhoVigencia) {
		this.filAnhoVigencia = filAnhoVigencia;
	}

	/**
	 * @return Return the filMetodologia.
	 */
	public final int getFilMetodologia() {
		return filMetodologia;
	}

	/**
	 * @param filMetodologia The filMetodologia to set.
	 */
	public final void setFilMetodologia(int filMetodologia) {
		this.filMetodologia = filMetodologia;
	}
}
