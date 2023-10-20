/**
 * 
 */
package siges.observacion.vo;

/**
 * 25/11/2007 
 * @author Latined
 * @version 1.2
 */
public class ObservacionEstudianteVO {
	
	
	private long obsInstitucion;
	private int obsMetodologia;
	private int obsSede;
	private int obsJornada;
	private int obsGrado;
	private int obsGrupo;
	private long obsJerarquia;
	private int obsPeriodo;
	private int obsOrden;
	private String []obsEstudiante;
	private String []obsObservacion;
	
 
	/**
	 * @return Return the obsGrado.
	 */
	public int getObsGrado() {
		return obsGrado;
	}
	/**
	 * @param obsGrado The obsGrado to set.
	 */
	public void setObsGrado(int obsGrado) {
		this.obsGrado = obsGrado;
	}
	/**
	 * @return Return the obsGrupo.
	 */
	public int getObsGrupo() {
		return obsGrupo;
	}
	/**
	 * @param obsGrupo The obsGrupo to set.
	 */
	public void setObsGrupo(int obsGrupo) {
		this.obsGrupo = obsGrupo;
	}
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
	 * @return Return the obsJornada.
	 */
	public int getObsJornada() {
		return obsJornada;
	}
	/**
	 * @param obsJornada The obsJornada to set.
	 */
	public void setObsJornada(int obsJornada) {
		this.obsJornada = obsJornada;
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
	 * @return Return the obsSede.
	 */
	public int getObsSede() {
		return obsSede;
	}
	/**
	 * @param obsSede The obsSede to set.
	 */
	public void setObsSede(int obsSede) {
		this.obsSede = obsSede;
	}
	/**
	 * @return Return the obsJerarquiaGrupo.
	 */
 
	public String[] getObsEstudiante() {
		return obsEstudiante;
	}
	public void setObsEstudiante(String[] obsEstudiante) {
		this.obsEstudiante = obsEstudiante;
	}
	public long getObsJerarquia() {
		return obsJerarquia;
	}
	public void setObsJerarquia(long obsJerarquia) {
		this.obsJerarquia = obsJerarquia;
	}
	public int getObsOrden() {
		return obsOrden;
	}
	public void setObsOrden(int obsOrden) {
		this.obsOrden = obsOrden;
	}

}
