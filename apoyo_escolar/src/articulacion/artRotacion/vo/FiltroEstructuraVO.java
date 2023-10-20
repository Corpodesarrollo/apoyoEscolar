/**
 * 
 */
package articulacion.artRotacion.vo;

import siges.common.vo.Vo;

/**
 * 8/10/2007 
 * @author Latined
 * @version 1.2
 */
public class FiltroEstructuraVO extends Vo{
	private int anhoVigencia;
	private int perVigencia;
	/**
	 * @return Return the anhoVigencia.
	 */
	public int getAnhoVigencia() {
		return anhoVigencia;
	}
	/**
	 * @param anhoVigencia The anhoVigencia to set.
	 */
	public void setAnhoVigencia(int anhoVigencia) {
		this.anhoVigencia = anhoVigencia;
	}
	/**
	 * @return Return the perVigencia.
	 */
	public int getPerVigencia() {
		return perVigencia;
	}
	/**
	 * @param perVigencia The perVigencia to set.
	 */
	public void setPerVigencia(int perVigencia) {
		this.perVigencia = perVigencia;
	}
}
