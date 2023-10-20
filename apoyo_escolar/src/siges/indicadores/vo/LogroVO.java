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
public class LogroVO extends Vo{
	private long logInstitucion;
	private int logMetodologia;
	private int logGrado=-99;
	private long logAsignatura;
	private long logCodigo;
	private long logCodigoJerarquia;
	private long logDocente;
	private int logPeriodoIni;
	private int logPeriodoFin;
	private int logVigencia;
	private String logNombre;
	private String logAbreviatura;
	private String logDescripcion;
	private int logConsecutivo;
	private int logPlanEstudios;
	private String logDisabled;
	
	private int logOrden;
	
	/**
	 * @return Return the logAbreviatura.
	 */
	public String getLogAbreviatura() {
		return logAbreviatura;
	}
	/**
	 * @param logAbreviatura The logAbreviatura to set.
	 */
	public void setLogAbreviatura(String logAbreviatura) {
		this.logAbreviatura = logAbreviatura;
	}
	/**
	 * @return Return the logCodigo.
	 */
	public long getLogCodigo() {
		return logCodigo;
	}
	/**
	 * @param logCodigo The logCodigo to set.
	 */
	public void setLogCodigo(long logCodigo) {
		this.logCodigo = logCodigo;
	}
	/**
	 * @return Return the logCodigoJerarquia.
	 */
	public long getLogCodigoJerarquia() {
		return logCodigoJerarquia;
	}
	/**
	 * @param logCodigoJerarquia The logCodigoJerarquia to set.
	 */
	public void setLogCodigoJerarquia(long logCodigoJerarquia) {
		this.logCodigoJerarquia = logCodigoJerarquia;
	}
	/**
	 * @return Return the logDescripcion.
	 */
	public String getLogDescripcion() {
		return logDescripcion;
	}
	/**
	 * @param logDescripcion The logDescripcion to set.
	 */
	public void setLogDescripcion(String logDescripcion) {
		this.logDescripcion = logDescripcion;
	}
	/**
	 * @return Return the logGrado.
	 */
	public int getLogGrado() {
		return logGrado;
	}
	/**
	 * @param logGrado The logGrado to set.
	 */
	public void setLogGrado(int logGrado) {
		this.logGrado = logGrado;
	}
	/**
	 * @return Return the logInstitucion.
	 */
	public long getLogInstitucion() {
		return logInstitucion;
	}
	/**
	 * @param logInstitucion The logInstitucion to set.
	 */
	public void setLogInstitucion(long logInstitucion) {
		this.logInstitucion = logInstitucion;
	}
	/**
	 * @return Return the logMetodologia.
	 */
	public int getLogMetodologia() {
		return logMetodologia;
	}
	/**
	 * @param logMetodologia The logMetodologia to set.
	 */
	public void setLogMetodologia(int logMetodologia) {
		this.logMetodologia = logMetodologia;
	}
	/**
	 * @return Return the logNombre.
	 */
	public String getLogNombre() {
		return logNombre;
	}
	/**
	 * @param logNombre The logNombre to set.
	 */
	public void setLogNombre(String logNombre) {
		this.logNombre = logNombre;
	}
	/**
	 * @return Return the logPeriodoFin.
	 */
	public int getLogPeriodoFin() {
		return logPeriodoFin;
	}
	/**
	 * @param logPeriodoFin The logPeriodoFin to set.
	 */
	public void setLogPeriodoFin(int logPeriodoFin) {
		this.logPeriodoFin = logPeriodoFin;
	}
	/**
	 * @return Return the logPeriodoIni.
	 */
	public int getLogPeriodoIni() {
		return logPeriodoIni;
	}
	/**
	 * @param logPeriodoIni The logPeriodoIni to set.
	 */
	public void setLogPeriodoIni(int logPeriodoIni) {
		this.logPeriodoIni = logPeriodoIni;
	}
	/**
	 * @return Return the logVigencia.
	 */
	public int getLogVigencia() {
		return logVigencia;
	}
	/**
	 * @param logVigencia The logVigencia to set.
	 */
	public void setLogVigencia(int logVigencia) {
		this.logVigencia = logVigencia;
	}
	/**
	 * @return Return the logAsignatura.
	 */
	public long getLogAsignatura() {
		return logAsignatura;
	}
	/**
	 * @param logAsignatura The logAsignatura to set.
	 */
	public void setLogAsignatura(long logAsignatura) {
		this.logAsignatura = logAsignatura;
	}
	/**
	 * @return Return the logConsecutivo.
	 */
	public int getLogConsecutivo() {
		return logConsecutivo;
	}
	/**
	 * @param logConsecutivo The logConsecutivo to set.
	 */
	public void setLogConsecutivo(int logConsecutivo) {
		this.logConsecutivo = logConsecutivo;
	}
	/**
	 * @return Return the logPlanEstudios.
	 */
	public int getLogPlanEstudios() {
		return logPlanEstudios;
	}
	/**
	 * @param logPlanEstudios The logPlanEstudios to set.
	 */
	public void setLogPlanEstudios(int logPlanEstudios) {
		this.logPlanEstudios = logPlanEstudios;
	}
	/**
	 * @return Return the logDocente.
	 */
	public long getLogDocente() {
		return logDocente;
	}
	/**
	 * @param logDocente The logDocente to set.
	 */
	public void setLogDocente(long logDocente) {
		this.logDocente = logDocente;
	}
	/**
	 * @return Return the logDisabled.
	 */
	public String getLogDisabled() {
		return logDisabled;
	}
	/**
	 * @param logDisabled The logDisabled to set.
	 */
	public void setLogDisabled(String logDisabled) {
		this.logDisabled = logDisabled;
	}
	public int getLogOrden() {
		return logOrden;
	}
	public void setLogOrden(int logOrden) {
		this.logOrden = logOrden;
	}
}
