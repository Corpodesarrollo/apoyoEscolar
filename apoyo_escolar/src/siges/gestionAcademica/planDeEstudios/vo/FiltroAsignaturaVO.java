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
public class FiltroAsignaturaVO extends Vo{
	private long filInstitucion;
	private int filMetodologia;
	private int filVigencia;
	private long filArea;
	
	/**
	 * @return Return the filArea.
	 */
	public final long getFilArea() {
		return filArea;
	}
	/**
	 * @param filArea The filArea to set.
	 */
	public final void setFilArea(long filArea) {
		this.filArea = filArea;
	}
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
