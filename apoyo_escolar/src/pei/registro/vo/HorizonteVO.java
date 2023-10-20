package pei.registro.vo;

import siges.common.vo.Vo;

public class HorizonteVO extends Vo{
	private long horInstitucion;
	private int horProceso;
	private int horMision;
	private int horVision;
	private int horPerfil;
	private int horObjetivo;
	private String horDificultad;
	
	private int horEstado;
	private String horEstadoNombre;
	private boolean horDisabled;
	private String horDisabled_;
	

	/*Ajustes Junio 2013*/
	private int horDiagnostico;
	private int horEtapaPrincipios = 0;
	private int horImprontas = 0;
	
	
	public int getHorDiagnostico() {
		return horDiagnostico;
	}
	public void sethorDiagnostico(int horDiagnostico) {
		this.horDiagnostico = horDiagnostico;
	}
	public int getHorEtapaPrincipios() {
		return horEtapaPrincipios;
	}
	public void setHorEtapaPrincipios(int horEtapaPrincipios) {
		this.horEtapaPrincipios = horEtapaPrincipios;
	}
	public int getHorImprontas() {
		return horImprontas;
	}
	public void setHorImprontas(int horImprontas) {
		this.horImprontas = horImprontas;
	}
	
	/**
	 * @return the horInstitucion
	 */
	public long getHorInstitucion() {
		return horInstitucion;
	}
	/**
	 * @param horInstitucion the horInstitucion to set
	 */
	public void setHorInstitucion(long horInstitucion) {
		this.horInstitucion = horInstitucion;
	}
	/**
	 * @return the horProceso
	 */
	public int getHorProceso() {
		return horProceso;
	}
	/**
	 * @param horProceso the horProceso to set
	 */
	public void setHorProceso(int horProceso) {
		this.horProceso = horProceso;
	}
	/**
	 * @return the horMision
	 */
	public int getHorMision() {
		return horMision;
	}
	/**
	 * @param horMision the horMision to set
	 */
	public void setHorMision(int horMision) {
		this.horMision = horMision;
	}
	/**
	 * @return the horVision
	 */
	public int getHorVision() {
		return horVision;
	}
	/**
	 * @param horVision the horVision to set
	 */
	public void setHorVision(int horVision) {
		this.horVision = horVision;
	}
	/**
	 * @return the horObjetivo
	 */
	public int getHorObjetivo() {
		return horObjetivo;
	}
	/**
	 * @param horObjetivo the horObjetivo to set
	 */
	public void setHorObjetivo(int horObjetivo) {
		this.horObjetivo = horObjetivo;
	}
	/**
	 * @return the horEstado
	 */
	public int getHorEstado() {
		return horEstado;
	}
	/**
	 * @param horEstado the horEstado to set
	 */
	public void setHorEstado(int horEstado) {
		this.horEstado = horEstado;
	}
	/**
	 * @return the horEstadoNombre
	 */
	public String getHorEstadoNombre() {
		return horEstadoNombre;
	}
	/**
	 * @param horEstadoNombre the horEstadoNombre to set
	 */
	public void setHorEstadoNombre(String horEstadoNombre) {
		this.horEstadoNombre = horEstadoNombre;
	}
	/**
	 * @return the horDisabled
	 */
	public boolean isHorDisabled() {
		return horDisabled;
	}
	/**
	 * @param horDisabled the horDisabled to set
	 */
	public void setHorDisabled(boolean horDisabled) {
		this.horDisabled = horDisabled;
	}
	/**
	 * @return the horDisabled_
	 */
	public String getHorDisabled_() {
		return horDisabled_;
	}
	/**
	 * @param horDisabled the horDisabled_ to set
	 */
	public void setHorDisabled_(String horDisabled) {
		horDisabled_ = horDisabled;
	}
	/**
	 * @return the horPerfil
	 */
	public int getHorPerfil() {
		return horPerfil;
	}
	/**
	 * @param horPerfil the horPerfil to set
	 */
	public void setHorPerfil(int horPerfil) {
		this.horPerfil = horPerfil;
	}
	/**
	 * @return the horDificultad
	 */
	public String getHorDificultad() {
		return horDificultad;
	}
	/**
	 * @param horDificultad the horDificultad to set
	 */
	public void setHorDificultad(String horDificultad) {
		this.horDificultad = horDificultad;
	}
}
