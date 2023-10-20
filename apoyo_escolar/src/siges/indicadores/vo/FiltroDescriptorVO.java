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
public class FiltroDescriptorVO  extends Vo{
	private long filInstitucion;
	private int filMetodologia;
	private int filGrado=-99;
	private int filTipo;
	private long filArea;
	private long filDocente;
	private int filVigencia;
	private int filPlanEstudios;
	private int filNivelPerfil;
	private int filInHabilitado;
	private long filUsuario;
	
	/**
	 * @return Return the filArea.
	 */
	public long getFilArea() {
		return filArea;
	}
	/**
	 * @param filArea The filArea to set.
	 */
	public void setFilArea(long filArea) {
		this.filArea = filArea;
	}
	/**
	 * @return Return the filDocente.
	 */
	public long getFilDocente() {
		return filDocente;
	}
	/**
	 * @param filDocente The filDocente to set.
	 */
	public void setFilDocente(long filDocente) {
		this.filDocente = filDocente;
	}
	/**
	 * @return Return the filGrado.
	 */
	public int getFilGrado() {
		return filGrado;
	}
	/**
	 * @param filGrado The filGrado to set.
	 */
	public void setFilGrado(int filGrado) {
		this.filGrado = filGrado;
	}
	/**
	 * @return Return the filInstitucion.
	 */
	public long getFilInstitucion() {
		return filInstitucion;
	}
	/**
	 * @param filInstitucion The filInstitucion to set.
	 */
	public void setFilInstitucion(long filInstitucion) {
		this.filInstitucion = filInstitucion;
	}
	/**
	 * @return Return the filMetodologia.
	 */
	public int getFilMetodologia() {
		return filMetodologia;
	}
	/**
	 * @param filMetodologia The filMetodologia to set.
	 */
	public void setFilMetodologia(int filMetodologia) {
		this.filMetodologia = filMetodologia;
	}
	/**
	 * @return Return the filTipo.
	 */
	public int getFilTipo() {
		return filTipo;
	}
	/**
	 * @param filTipo The filTipo to set.
	 */
	public void setFilTipo(int filTipo) {
		this.filTipo = filTipo;
	}
	/**
	 * @return Return the filVigencia.
	 */
	public int getFilVigencia() {
		return filVigencia;
	}
	/**
	 * @param filVigencia The filVigencia to set.
	 */
	public void setFilVigencia(int filVigencia) {
		this.filVigencia = filVigencia;
	}
	/**
	 * @return Return the filPlanEstudios.
	 */
	public int getFilPlanEstudios() {
		return filPlanEstudios;
	}
	/**
	 * @param filPlanEstudios The filPlanEstudios to set.
	 */
	public void setFilPlanEstudios(int filPlanEstudios) {
		this.filPlanEstudios = filPlanEstudios;
	}
	/**
	 * @return Return the filNivelPerfil.
	 */
	public int getFilNivelPerfil() {
		return filNivelPerfil;
	}
	/**
	 * @param filNivelPerfil The filNivelPerfil to set.
	 */
	public void setFilNivelPerfil(int filNivelPerfil) {
		this.filNivelPerfil = filNivelPerfil;
	}
	/**
	 * @return Return the filUsuario.
	 */
	public long getFilUsuario() {
		return filUsuario;
	}
	/**
	 * @param filUsuario The filUsuario to set.
	 */
	public void setFilUsuario(long filUsuario) {
		this.filUsuario = filUsuario;
	}
	/**
	 * @return Return the filInHabilitado.
	 */
	public int getFilInHabilitado() {
		return filInHabilitado;
	}
	/**
	 * @param filInHabilitado The filInHabilitado to set.
	 */
	public void setFilInHabilitado(int filInHabilitado) {
		this.filInHabilitado = filInHabilitado;
	}

}
