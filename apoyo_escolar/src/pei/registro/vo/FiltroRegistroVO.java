package pei.registro.vo;

import siges.common.vo.Vo;

public class FiltroRegistroVO extends Vo{
	private long filInstitucion;
	private int filLocalidad=-99;
	private boolean filDisabled;
	private String filDisabled_;
	private boolean filEvaluado;
	private String filNombreInstitucion;
	private String filLocalidadBloqueada;
	private String filManual;

	private String lblEtapa;
	private String lblEnfasis;
	private String lblEnfoque;
	private String lblFase;
	private String lblPreparacion;
	private String lblFormulacion;
	private String lblEjecucion;
	private String lblSeguimiento;
	private String lblPrograma;
	private String lblUniversidad;
	private String lblProyecto;
	
	
	/**
	 * @return the filInstitucion
	 */
	public long getFilInstitucion() {
		return filInstitucion;
	}
	/**
	 * @param filInstitucion the filInstitucion to set
	 */
	public void setFilInstitucion(long filInstitucion) {
		this.filInstitucion = filInstitucion;
	}
	/**
	 * @return the filLocalidad
	 */
	public int getFilLocalidad() {
		return filLocalidad;
	}
	/**
	 * @param filLocalidad the filLocalidad to set
	 */
	public void setFilLocalidad(int filLocalidad) {
		this.filLocalidad = filLocalidad;
	}
	/**
	 * @return the filDisabled
	 */
	public boolean isFilDisabled() {
		return filDisabled;
	}
	/**
	 * @param filDisabled the filDisabled to set
	 */
	public void setFilDisabled(boolean filDisabled) {
		this.filDisabled = filDisabled;
	}
	/**
	 * @return the filEvaluado
	 */
	public boolean isFilEvaluado() {
		return filEvaluado;
	}
	/**
	 * @param filEvaluado the filEvaluado to set
	 */
	public void setFilEvaluado(boolean filEvaluado) {
		this.filEvaluado = filEvaluado;
	}
	/**
	 * @return the filLocalidadBloqueada
	 */
	public String getFilLocalidadBloqueada() {
		return filLocalidadBloqueada;
	}
	/**
	 * @param filLocalidadBloqueada the filLocalidadBloqueada to set
	 */
	public void setFilLocalidadBloqueada(String filLocalidadBloqueada) {
		this.filLocalidadBloqueada = filLocalidadBloqueada;
	}
	/**
	 * @return the filNombreInstitucion
	 */
	public String getFilNombreInstitucion() {
		return filNombreInstitucion;
	}
	/**
	 * @param filNombreInstitucion the filNombreInstitucion to set
	 */
	public void setFilNombreInstitucion(String filNombreInstitucion) {
		this.filNombreInstitucion = filNombreInstitucion;
	}
	/**
	 * @return the filManual
	 */
	public String getFilManual() {
		return filManual;
	}
	/**
	 * @param filManual the filManual to set
	 */
	public void setFilManual(String filManual) {
		this.filManual = filManual;
	}
	/**
	 * @return the lblEtapa
	 */
	public String getLblEtapa() {
		return lblEtapa;
	}
	/**
	 * @param lblEtapa the lblEtapa to set
	 */
	public void setLblEtapa(String lblEtapa) {
		this.lblEtapa = lblEtapa;
	}
	/**
	 * @return the lblEnfasis
	 */
	public String getLblEnfasis() {
		return lblEnfasis;
	}
	/**
	 * @param lblEnfasis the lblEnfasis to set
	 */
	public void setLblEnfasis(String lblEnfasis) {
		this.lblEnfasis = lblEnfasis;
	}
	/**
	 * @return the lblEnfoque
	 */
	public String getLblEnfoque() {
		return lblEnfoque;
	}
	/**
	 * @param lblEnfoque the lblEnfoque to set
	 */
	public void setLblEnfoque(String lblEnfoque) {
		this.lblEnfoque = lblEnfoque;
	}
	/**
	 * @return the lblFase
	 */
	public String getLblFase() {
		return lblFase;
	}
	/**
	 * @param lblFase the lblFase to set
	 */
	public void setLblFase(String lblFase) {
		this.lblFase = lblFase;
	}
	/**
	 * @return the lblPreparacion
	 */
	public String getLblPreparacion() {
		return lblPreparacion;
	}
	/**
	 * @param lblPreparacion the lblPreparacion to set
	 */
	public void setLblPreparacion(String lblPreparacion) {
		this.lblPreparacion = lblPreparacion;
	}
	/**
	 * @return the lblFormulacion
	 */
	public String getLblFormulacion() {
		return lblFormulacion;
	}
	/**
	 * @param lblFormulacion the lblFormulacion to set
	 */
	public void setLblFormulacion(String lblFormulacion) {
		this.lblFormulacion = lblFormulacion;
	}
	/**
	 * @return the lblEjecucion
	 */
	public String getLblEjecucion() {
		return lblEjecucion;
	}
	/**
	 * @param lblEjecucion the lblEjecucion to set
	 */
	public void setLblEjecucion(String lblEjecucion) {
		this.lblEjecucion = lblEjecucion;
	}
	/**
	 * @return the lblSeguimiento
	 */
	public String getLblSeguimiento() {
		return lblSeguimiento;
	}
	/**
	 * @param lblSeguimiento the lblSeguimiento to set
	 */
	public void setLblSeguimiento(String lblSeguimiento) {
		this.lblSeguimiento = lblSeguimiento;
	}
	/**
	 * @return the lblPrograma
	 */
	public String getLblPrograma() {
		return lblPrograma;
	}
	/**
	 * @param lblPrograma the lblPrograma to set
	 */
	public void setLblPrograma(String lblPrograma) {
		this.lblPrograma = lblPrograma;
	}
	/**
	 * @return the lblUniversidad
	 */
	public String getLblUniversidad() {
		return lblUniversidad;
	}
	/**
	 * @param lblUniversidad the lblUniversidad to set
	 */
	public void setLblUniversidad(String lblUniversidad) {
		this.lblUniversidad = lblUniversidad;
	}
	/**
	 * @return the lblProyecto
	 */
	public String getLblProyecto() {
		return lblProyecto;
	}
	/**
	 * @param lblProyecto the lblProyecto to set
	 */
	public void setLblProyecto(String lblProyecto) {
		this.lblProyecto = lblProyecto;
	}
	/**
	 * @return the filDisabled_
	 */
	public String getFilDisabled_() {
		return filDisabled_;
	}
	/**
	 * @param filDisabled the filDisabled_ to set
	 */
	public void setFilDisabled_(String filDisabled) {
		filDisabled_ = filDisabled;
	}
}
