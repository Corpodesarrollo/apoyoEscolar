/**
 * 
 */
package siges.observacion.vo;

import siges.common.vo.Vo;

/**
 * 25/11/2007 
 * @author Latined
 * @version 1.2
 */
public class ObservacionPeriodoVO extends Vo{
	private long obsInstitucion;
	private int obsMetodologia;
	private int obsPeriodo;
	private String []obsJerarquia;
	private String []obsObservacion;
	
	/**
	 * @return Return the obsInstitucion.
	 */
	public long getObsInstitucion() {
		return obsInstitucion;
	}
	/**
	 * @param obsInstitucion The obsInstitucion to set.
	 */
	public void setObsInstitucion(long obsInstitucion) {
		this.obsInstitucion = obsInstitucion;
	}
	/**
	 * @return Return the obsObservacion.
	 */
	public String[] getObsObservacion() {
		return obsObservacion;
	}
	/**
	 * @param obsObservacion The obsObservacion to set.
	 */
	public void setObsObservacion(String[] obsObservacion) {
		this.obsObservacion = obsObservacion;
	}
	/**
	 * @return Return the obsPeriodo.
	 */
	public int getObsPeriodo() {
		return obsPeriodo;
	}
	/**
	 * @param obsPeriodo The obsPeriodo to set.
	 */
	public void setObsPeriodo(int obsPeriodo) {
		this.obsPeriodo = obsPeriodo;
	}
	/**
	 * @return Return the obsMetodologia.
	 */
	public int getObsMetodologia() {
		return obsMetodologia;
	}
	/**
	 * @param obsMetodologia The obsMetodologia to set.
	 */
	public void setObsMetodologia(int obsMetodologia) {
		this.obsMetodologia = obsMetodologia;
	}
	/**
	 * @return Return the obsJerarquia.
	 */
	public String[] getObsJerarquia() {
		return obsJerarquia;
	}
	/**
	 * @param obsJerarquia The obsJerarquia to set.
	 */
	public void setObsJerarquia(String[] obsJerarquia) {
		this.obsJerarquia = obsJerarquia;
	}
}
