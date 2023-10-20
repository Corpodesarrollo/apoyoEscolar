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
public class InstanciaVO extends Vo{
	private int instCodigo;
	private int instNivel;
	private String instNombre;
	private String instNorma;
	private String instConforma;
	private String instIntegrantes;
	
	/**
	 * @return Return the instConforma.
	 */
	public final String getInstConforma() {
		return instConforma;
	}
	/**
	 * @param instConforma The instConforma to set.
	 */
	public final void setInstConforma(String instConforma) {
		this.instConforma = instConforma;
	}
	/**
	 * @return Return the instIntegrantes.
	 */
	public final String getInstIntegrantes() {
		return instIntegrantes;
	}
	/**
	 * @param instIntegrantes The instIntegrantes to set.
	 */
	public final void setInstIntegrantes(String instIntegrantes) {
		this.instIntegrantes = instIntegrantes;
	}
	/**
	 * @return Return the instNivel.
	 */
	public final int getInstNivel() {
		return instNivel;
	}
	/**
	 * @param instNivel The instNivel to set.
	 */
	public final void setInstNivel(int instNivel) {
		this.instNivel = instNivel;
	}
	/**
	 * @return Return the instNombre.
	 */
	public final String getInstNombre() {
		return instNombre;
	}
	/**
	 * @param instNombre The instNombre to set.
	 */
	public final void setInstNombre(String instNombre) {
		this.instNombre = instNombre;
	}
	/**
	 * @return Return the instNorma.
	 */
	public final String getInstNorma() {
		return instNorma;
	}
	/**
	 * @param instNorma The instNorma to set.
	 */
	public final void setInstNorma(String instNorma) {
		this.instNorma = instNorma;
	}
	/**
	 * @return Return the instCodigo.
	 */
	public final int getInstCodigo() {
		return instCodigo;
	}
	/**
	 * @param instCodigo The instCodigo to set.
	 */
	public final void setInstCodigo(int instCodigo) {
		this.instCodigo = instCodigo;
	}
}
