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
public class FiltroInstanciaVO extends Vo{
	private int filNivel;
	//para editar/eliminar
	private int filInstancia;
	private int filObjetivo;
	private int filRepresentante;
	private int filDocumento;

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
	 * @return Return the filDocumento.
	 */
	public final int getFilDocumento() {
		return filDocumento;
	}

	/**
	 * @param filDocumento The filDocumento to set.
	 */
	public final void setFilDocumento(int filDocumento) {
		this.filDocumento = filDocumento;
	}

	/**
	 * @return Return the filObjetivo.
	 */
	public final int getFilObjetivo() {
		return filObjetivo;
	}

	/**
	 * @param filObjetivo The filObjetivo to set.
	 */
	public final void setFilObjetivo(int filObjetivo) {
		this.filObjetivo = filObjetivo;
	}

	/**
	 * @return Return the filRepresentante.
	 */
	public final int getFilRepresentante() {
		return filRepresentante;
	}

	/**
	 * @param filRepresentante The filRepresentante to set.
	 */
	public final void setFilRepresentante(int filRepresentante) {
		this.filRepresentante = filRepresentante;
	}
}
