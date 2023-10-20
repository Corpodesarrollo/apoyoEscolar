/**
 * 
 */
package siges.gestionAcademica.planDeEstudios.vo;

import siges.common.vo.Vo;

/**
 * 28/10/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class FiltroPlanDeEstudiosVO extends Vo{
	private long filInstitucion;
	private int filVigencia;
	/**
	 * @return Return the filInstitucion.
	 */
	public final long getFilInstitucion() {
		return filInstitucion;
	}
	/**
	 * @param filInstitucion The filInstitucion to set.
	 */
	public final void setFilInstitucion(long filInstitucion) {
		this.filInstitucion = filInstitucion;
	}
	/**
	 * @return Return the filVigencia.
	 */
	public final int getFilVigencia() {
		return filVigencia;
	}
	/**
	 * @param filVigencia The filVigencia to set.
	 */
	public final void setFilVigencia(int filVigencia) {
		this.filVigencia = filVigencia;
	}

}
