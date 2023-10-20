package poa.aprobarCambios.vo;

import siges.common.vo.Vo;

/**
 * Value Object de manejo del formulario de filtro 14/01/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class FiltroCambiosVO extends Vo {
	private long filLoc;
	private long filNivel;
	private long filEntidad;
	private int filVigencia;
	private float filPonderado;
	private int filEstatoPOA;
	private int filHabilitado;
	private int filEstadoCambio;
	private String filEstado;
	private String filObservacion;
	private boolean filFechaHabil;
	private String filRangoFechas;
	private String filObservacionLinea;
	private String filFechaSol;
	private String filUsuario;
	private String filNombreColegio;

	public long getFilNivel() {
		return filNivel;
	}

	public void setFilNivel(long filNivel) {
		this.filNivel = filNivel;
	}

	public long getFilEntidad() {
		return filEntidad;
	}

	public void setFilEntidad(long filEntidad) {
		this.filEntidad = filEntidad;
	}

	public int getFilVigencia() {
		return filVigencia;
	}

	public void setFilVigencia(int filVigencia) {
		this.filVigencia = filVigencia;
	}

	public float getFilPonderado() {
		return filPonderado;
	}

	public void setFilPonderado(float filPonderado) {
		this.filPonderado = filPonderado;
	}

	public int getFilEstatoPOA() {
		return filEstatoPOA;
	}

	public void setFilEstatoPOA(int filEstatoPOA) {
		this.filEstatoPOA = filEstatoPOA;
	}

	public int getFilHabilitado() {
		return filHabilitado;
	}

	public void setFilHabilitado(int filHabilitado) {
		this.filHabilitado = filHabilitado;
	}

	public String getFilEstado() {
		return filEstado;
	}

	public void setFilEstado(String filEstado) {
		this.filEstado = filEstado;
	}

	public String getFilObservacion() {
		return filObservacion;
	}

	public void setFilObservacion(String filObservacion) {
		this.filObservacion = filObservacion;
	}

	public boolean isFilFechaHabil() {
		return filFechaHabil;
	}

	public void setFilFechaHabil(boolean filFechaHabil) {
		this.filFechaHabil = filFechaHabil;
	}

	public String getFilRangoFechas() {
		return filRangoFechas;
	}

	public void setFilRangoFechas(String filRangoFechas) {
		this.filRangoFechas = filRangoFechas;
	}

	public String getFilObservacionLinea() {
		return filObservacionLinea;
	}

	public void setFilObservacionLinea(String filObservacionLinea) {
		this.filObservacionLinea = filObservacionLinea;
	}

	public long getFilLoc() {
		return filLoc;
	}

	public void setFilLoc(long filLoc) {
		this.filLoc = filLoc;
	}

	public String getFilFechaSol() {
		return filFechaSol;
	}

	public void setFilFechaSol(String filFechaSol) {
		this.filFechaSol = filFechaSol;
	}

	public String getFilUsuario() {
		return filUsuario;
	}

	public void setFilUsuario(String filUsuario) {
		this.filUsuario = filUsuario;
	}

	public int getFilEstadoCambio() {
		return filEstadoCambio;
	}

	public void setFilEstadoCambio(int filEstadoCambio) {
		this.filEstadoCambio = filEstadoCambio;
	}

	public String getFilNombreColegio() {
		return filNombreColegio;
	}

	public void setFilNombreColegio(String filNombreColegio) {
		this.filNombreColegio = filNombreColegio;
	}

}
