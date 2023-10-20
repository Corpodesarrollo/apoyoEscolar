package articulacion.asigAcademica.vo;

import siges.common.vo.Vo;


public class AsignacionVO extends Vo{
	private long asigInstitucion;
	private long asigDocente;
	private long asigAsignatura;
	private int asigSemestre;
	private int asigIntHor;
	private String asigNombreAsignatura;
	private int asigComponente;
	private long asigEspecialidad;
	private int asigAnho;
	private int asigPeriodo;
	
	
	/**
	 * @return Return the asigAsignatura.
	 */
	public long getAsigAsignatura() {
		return asigAsignatura;
	}
	/**
	 * @param asigAsignatura The asigAsignatura to set.
	 */
	public void setAsigAsignatura(long asigAsignatura) {
		this.asigAsignatura = asigAsignatura;
	}
	/**
	 * @return Return the asigDocente.
	 */
	public long getAsigDocente() {
		return asigDocente;
	}
	/**
	 * @param asigDocente The asigDocente to set.
	 */
	public void setAsigDocente(long asigDocente) {
		this.asigDocente = asigDocente;
	}
	/**
	 * @return Return the asigIntHor.
	 */
	public int getAsigIntHor() {
		return asigIntHor;
	}
	/**
	 * @param asigIntHor The asigIntHor to set.
	 */
	public void setAsigIntHor(int asigIntHor) {
		this.asigIntHor = asigIntHor;
	}
	/**
	 * @return Return the asigNombreAsignatura.
	 */
	public String getAsigNombreAsignatura() {
		return asigNombreAsignatura;
	}
	/**
	 * @param asigNombreAsignatura The asigNombreAsignatura to set.
	 */
	public void setAsigNombreAsignatura(String asigNombreAsignatura) {
		this.asigNombreAsignatura = asigNombreAsignatura;
	}
	/**
	 * @return Return the asigSemestre.
	 */
	public int getAsigSemestre() {
		return asigSemestre;
	}
	/**
	 * @param asigSemestre The asigSemestre to set.
	 */
	public void setAsigSemestre(int asigSemestre) {
		this.asigSemestre = asigSemestre;
	}
	/**
	 * @return Return the asigComponente.
	 */
	public int getAsigComponente() {
		return asigComponente;
	}
	/**
	 * @param asigComponente The asigComponente to set.
	 */
	public void setAsigComponente(int asigComponente) {
		this.asigComponente = asigComponente;
	}
	/**
	 * @return Return the asigEspecialidad.
	 */
	public long getAsigEspecialidad() {
		return asigEspecialidad;
	}
	/**
	 * @param asigEspecialidad The asigEspecialidad to set.
	 */
	public void setAsigEspecialidad(long asigEspecialidad) {
		this.asigEspecialidad = asigEspecialidad;
	}
	/**
	 * @return Return the asigInstitucion.
	 */
	public long getAsigInstitucion() {
		return asigInstitucion;
	}
	/**
	 * @param asigInstitucion The asigInstitucion to set.
	 */
	public void setAsigInstitucion(long asigInstitucion) {
		this.asigInstitucion = asigInstitucion;
	}
	/**
	 * @return Return the asigAnho.
	 */
	public int getAsigAnho() {
		return asigAnho;
	}
	/**
	 * @param asigAnho The asigAnho to set.
	 */
	public void setAsigAnho(int asigAnho) {
		this.asigAnho = asigAnho;
	}
	/**
	 * @return Return the asigPeriodo.
	 */
	public int getAsigPeriodo() {
		return asigPeriodo;
	}
	/**
	 * @param asigPeriodo The asigPeriodo to set.
	 */
	public void setAsigPeriodo(int asigPeriodo) {
		this.asigPeriodo = asigPeriodo;
	}
	
}