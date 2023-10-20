/**
 * 
 */
package participacion.instancia.vo;

import siges.common.vo.Vo;

/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class FiltroRangoVO extends Vo{
	private int filNivel;
	private int filInstancia;
	private int filRango;
	/**
	 * @return Return the filInstancia.
	 */
	public final int getFilInstancia() {
		return filInstancia;
	}
	/**
	 * @param filInstancia The filInstancia to set.
	 */
	public final void setFilInstancia(int filInstancia) {
		this.filInstancia = filInstancia;
	}
	/**
	 * @return Return the filNivel.
	 */
	public final int getFilNivel() {
		return filNivel;
	}
	/**
	 * @param filNivel The filNivel to set.
	 */
	public final void setFilNivel(int filNivel) {
		this.filNivel = filNivel;
	}
	/**
	 * @return Return the filRango.
	 */
	public final int getFilRango() {
		return filRango;
	}
	/**
	 * @param filRango The filRango to set.
	 */
	public final void setFilRango(int filRango) {
		this.filRango = filRango;
	}
}
