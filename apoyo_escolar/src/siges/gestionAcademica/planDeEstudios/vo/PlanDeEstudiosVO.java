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
public class PlanDeEstudiosVO extends Vo{
	private long plaInstitucion;
	private int plaMetodologia;
	private int plaVigencia;
	private String plaCriterio;
	private String plaProcedimiento;
	private String plaPlanEspecial;
	private String plaMetodologiaNombre;
	
	/**
	 * @return Return the plaCriterio.
	 */
	public final String getPlaCriterio() {
		return plaCriterio;
	}
	/**
	 * @param plaCriterio The plaCriterio to set.
	 */
	public final void setPlaCriterio(String plaCriterio) {
		this.plaCriterio = plaCriterio;
	}
	/**
	 * @return Return the plaInstitucion.
	 */
	public final long getPlaInstitucion() {
		return plaInstitucion;
	}
	/**
	 * @param plaInstitucion The plaInstitucion to set.
	 */
	public final void setPlaInstitucion(long plaInstitucion) {
		this.plaInstitucion = plaInstitucion;
	}
	/**
	 * @return Return the plaMetodologia.
	 */
	public final int getPlaMetodologia() {
		return plaMetodologia;
	}
	/**
	 * @param plaMetodologia The plaMetodologia to set.
	 */
	public final void setPlaMetodologia(int plaMetodologia) {
		this.plaMetodologia = plaMetodologia;
	}
	/**
	 * @return Return the plaPlanEspecial.
	 */
	public final String getPlaPlanEspecial() {
		return plaPlanEspecial;
	}
	/**
	 * @param plaPlanEspecial The plaPlanEspecial to set.
	 */
	public final void setPlaPlanEspecial(String plaPlanEspecial) {
		this.plaPlanEspecial = plaPlanEspecial;
	}
	/**
	 * @return Return the plaProcedimiento.
	 */
	public final String getPlaProcedimiento() {
		return plaProcedimiento;
	}
	/**
	 * @param plaProcedimiento The plaProcedimiento to set.
	 */
	public final void setPlaProcedimiento(String plaProcedimiento) {
		this.plaProcedimiento = plaProcedimiento;
	}
	/**
	 * @return Return the plaVigencia.
	 */
	public final int getPlaVigencia() {
		return plaVigencia;
	}
	/**
	 * @param plaVigencia The plaVigencia to set.
	 */
	public final void setPlaVigencia(int plaVigencia) {
		this.plaVigencia = plaVigencia;
	}
	/**
	 * @return Return the plaMetodologiaNombre.
	 */
	public final String getPlaMetodologiaNombre() {
		return plaMetodologiaNombre;
	}
	/**
	 * @param plaMetodologiaNombre The plaMetodologiaNombre to set.
	 */
	public final void setPlaMetodologiaNombre(String plaMetodologiaNombre) {
		this.plaMetodologiaNombre = plaMetodologiaNombre;
	}
}
