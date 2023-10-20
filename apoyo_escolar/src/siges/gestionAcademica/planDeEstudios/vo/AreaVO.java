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
public class AreaVO extends Vo{
	private long areInstitucion;
	private int areMetodologia;
	private int areVigencia;
	private long areCodigo;
	private String areNombre;
	private String areAbreviatura;
	private long areOrden;
	private long [] areGrado;
	
	/**
	 * @return Return the areAbreviatura.
	 */
	public final String getAreAbreviatura() {
		return areAbreviatura;
	}
	/**
	 * @param areAbreviatura The areAbreviatura to set.
	 */
	public final void setAreAbreviatura(String areAbreviatura) {
		this.areAbreviatura = areAbreviatura;
	}
	/**
	 * @return Return the areCodigo.
	 */
	public final long getAreCodigo() {
		return areCodigo;
	}
	/**
	 * @param areCodigo The areCodigo to set.
	 */
	public final void setAreCodigo(long areCodigo) {
		this.areCodigo = areCodigo;
	}
	/**
	 * @return Return the areInstitucion.
	 */
	public final long getAreInstitucion() {
		return areInstitucion;
	}
	/**
	 * @param areInstitucion The areInstitucion to set.
	 */
	public final void setAreInstitucion(long areInstitucion) {
		this.areInstitucion = areInstitucion;
	}
	/**
	 * @return Return the areMetodologia.
	 */
	public final int getAreMetodologia() {
		return areMetodologia;
	}
	/**
	 * @param areMetodologia The areMetodologia to set.
	 */
	public final void setAreMetodologia(int areMetodologia) {
		this.areMetodologia = areMetodologia;
	}
	/**
	 * @return Return the areNombre.
	 */
	public final String getAreNombre() {
		return areNombre;
	}
	/**
	 * @param areNombre The areNombre to set.
	 */
	public final void setAreNombre(String areNombre) {
		this.areNombre = areNombre;
	}
	/**
	 * @return Return the areOrden.
	 */
	public final long getAreOrden() {
		return areOrden;
	}
	/**
	 * @param areOrden The areOrden to set.
	 */
	public final void setAreOrden(long areOrden) {
		this.areOrden = areOrden;
	}
	/**
	 * @return Return the areVigencia.
	 */
	public final int getAreVigencia() {
		return areVigencia;
	}
	/**
	 * @param areVigencia The areVigencia to set.
	 */
	public final void setAreVigencia(int areVigencia) {
		this.areVigencia = areVigencia;
	}
	/**
	 * @return Return the areGrado.
	 */
	public final long[] getAreGrado() {
		return areGrado;
	}
	/**
	 * @param areGrado The areGrado to set.
	 */
	public final void setAreGrado(long[] areGrado) {
		this.areGrado = areGrado;
	}
}
