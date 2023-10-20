/**
 * 
 */
package siges.indicadores.vo;

import siges.common.vo.Vo;

/**
 * 22/01/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class DescriptorVO extends Vo{
	private long desInstitucion;
	private int desMetodologia;
	private int desVigencia;
	private int desGrado=-99;
	private long desArea;
	private long desCodigo;
	private long desCodigoJerarquia;
	private long desDocente;
	private int desTipo;
	private int desPeriodoIni;
	private int desPeriodoFin;
	private String desNombre;
	private String desAbreviatura;
	private String desDescripcion;
	private int desConsecutivo;
	private int desPlanEstudios;
	private String desDisabled;
	
	private int desOrden;
	
	/**
	 * @return Return the desAbreviatura.
	 */
	public String getDesAbreviatura() {
		return desAbreviatura;
	}
	/**
	 * @param desAbreviatura The desAbreviatura to set.
	 */
	public void setDesAbreviatura(String desAbreviatura) {
		this.desAbreviatura = desAbreviatura;
	}
	/**
	 * @return Return the desCodigo.
	 */
	public long getDesCodigo() {
		return desCodigo;
	}
	/**
	 * @param desCodigo The desCodigo to set.
	 */
	public void setDesCodigo(long desCodigo) {
		this.desCodigo = desCodigo;
	}
	/**
	 * @return Return the desCodigoJerarquia.
	 */
	public long getDesCodigoJerarquia() {
		return desCodigoJerarquia;
	}
	/**
	 * @param desCodigoJerarquia The desCodigoJerarquia to set.
	 */
	public void setDesCodigoJerarquia(long desCodigoJerarquia) {
		this.desCodigoJerarquia = desCodigoJerarquia;
	}
	/**
	 * @return Return the desDescripcion.
	 */
	public String getDesDescripcion() {
		return desDescripcion;
	}
	/**
	 * @param desDescripcion The desDescripcion to set.
	 */
	public void setDesDescripcion(String desDescripcion) {
		this.desDescripcion = desDescripcion;
	}
	/**
	 * @return Return the desInstitucion.
	 */
	public long getDesInstitucion() {
		return desInstitucion;
	}
	/**
	 * @param desInstitucion The desInstitucion to set.
	 */
	public void setDesInstitucion(long desInstitucion) {
		this.desInstitucion = desInstitucion;
	}
	/**
	 * @return Return the desMetodologia.
	 */
	public int getDesMetodologia() {
		return desMetodologia;
	}
	/**
	 * @param desMetodologia The desMetodologia to set.
	 */
	public void setDesMetodologia(int desMetodologia) {
		this.desMetodologia = desMetodologia;
	}
	/**
	 * @return Return the desNombre.
	 */
	public String getDesNombre() {
		return desNombre;
	}
	/**
	 * @param desNombre The desNombre to set.
	 */
	public void setDesNombre(String desNombre) {
		this.desNombre = desNombre;
	}
	/**
	 * @return Return the desPeriodoFin.
	 */
	public int getDesPeriodoFin() {
		return desPeriodoFin;
	}
	/**
	 * @param desPeriodoFin The desPeriodoFin to set.
	 */
	public void setDesPeriodoFin(int desPeriodoFin) {
		this.desPeriodoFin = desPeriodoFin;
	}
	/**
	 * @return Return the desPeriodoIni.
	 */
	public int getDesPeriodoIni() {
		return desPeriodoIni;
	}
	/**
	 * @param desPeriodoIni The desPeriodoIni to set.
	 */
	public void setDesPeriodoIni(int desPeriodoIni) {
		this.desPeriodoIni = desPeriodoIni;
	}
	/**
	 * @return Return the desTipo.
	 */
	public int getDesTipo() {
		return desTipo;
	}
	/**
	 * @param desTipo The desTipo to set.
	 */
	public void setDesTipo(int desTipo) {
		this.desTipo = desTipo;
	}
	/**
	 * @return Return the desVigencia.
	 */
	public int getDesVigencia() {
		return desVigencia;
	}
	/**
	 * @param desVigencia The desVigencia to set.
	 */
	public void setDesVigencia(int desVigencia) {
		this.desVigencia = desVigencia;
	}
	/**
	 * @return Return the desArea.
	 */
	public long getDesArea() {
		return desArea;
	}
	/**
	 * @param desArea The desArea to set.
	 */
	public void setDesArea(long desArea) {
		this.desArea = desArea;
	}
	/**
	 * @return Return the desGrado.
	 */
	public int getDesGrado() {
		return desGrado;
	}
	/**
	 * @param desGrado The desGrado to set.
	 */
	public void setDesGrado(int desGrado) {
		this.desGrado = desGrado;
	}
	/**
	 * @return Return the desConsecutivo.
	 */
	public int getDesConsecutivo() {
		return desConsecutivo;
	}
	/**
	 * @param desConsecutivo The desConsecutivo to set.
	 */
	public void setDesConsecutivo(int desConsecutivo) {
		this.desConsecutivo = desConsecutivo;
	}
	/**
	 * @return Return the desPlanEstudios.
	 */
	public int getDesPlanEstudios() {
		return desPlanEstudios;
	}
	/**
	 * @param desPlanEstudios The desPlanEstudios to set.
	 */
	public void setDesPlanEstudios(int desPlanEstudios) {
		this.desPlanEstudios = desPlanEstudios;
	}
	/**
	 * @return Return the desDocente.
	 */
	public long getDesDocente() {
		return desDocente;
	}
	/**
	 * @param desDocente The desDocente to set.
	 */
	public void setDesDocente(long desDocente) {
		this.desDocente = desDocente;
	}
	/**
	 * @return Return the desDisabled.
	 */
	public String getDesDisabled() {
		return desDisabled;
	}
	/**
	 * @param desDisabled The desDisabled to set.
	 */
	public void setDesDisabled(String desDisabled) {
		this.desDisabled = desDisabled;
	}
	public int getDesOrden() {
		return desOrden;
	}
	public void setDesOrden(int desorden) {
		this.desOrden = desorden;
	}
}
