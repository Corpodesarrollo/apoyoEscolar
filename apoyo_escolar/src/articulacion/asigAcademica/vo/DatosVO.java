package articulacion.asigAcademica.vo;

import siges.common.vo.Vo;

public class DatosVO extends Vo{

	private long institucion;
	private int jornada;
	private int sede;
	private long docente;
	private boolean buscado;
	private int asigIntHorTotal;
	private int asigIntHorExtra;
	private int asigIntHorReal;
	private int anho;
	private int periodo;
	
	/**
	 * @return Return the buscado.
	 */
	public boolean isBuscado() {
		return buscado;
	}
	/**
	 * @param buscado The buscado to set.
	 */
	public void setBuscado(boolean buscado) {
		this.buscado = buscado;
	}
	/**
	 * @return Return the docente.
	 */
	public long getDocente() {
		return docente;
	}
	/**
	 * @param docente The docente to set.
	 */
	public void setDocente(long docente) {
		this.docente = docente;
	}
	/**
	 * @return Return the institucion.
	 */
	public long getInstitucion() {
		return institucion;
	}
	/**
	 * @param institucion The institucion to set.
	 */
	public void setInstitucion(long institucion) {
		this.institucion = institucion;
	}
	/**
	 * @return Return the jornada.
	 */
	public int getJornada() {
		return jornada;
	}
	/**
	 * @param jornada The jornada to set.
	 */
	public void setJornada(int jornada) {
		this.jornada = jornada;
	}
	/**
	 * @return Return the sede.
	 */
	public int getSede() {
		return sede;
	}
	/**
	 * @param sede The sede to set.
	 */
	public void setSede(int sede) {
		this.sede = sede;
	}
	/**
	 * @return Return the asigIntHorExtra.
	 */
	public int getAsigIntHorExtra() {
		return asigIntHorExtra;
	}
	/**
	 * @param asigIntHorExtra The asigIntHorExtra to set.
	 */
	public void setAsigIntHorExtra(int asigIntHorExtra) {
		this.asigIntHorExtra = asigIntHorExtra;
	}
	/**
	 * @return Return the asigIntHorTotal.
	 */
	public int getAsigIntHorTotal() {
		return asigIntHorTotal;
	}
	/**
	 * @param asigIntHorTotal The asigIntHorTotal to set.
	 */
	public void setAsigIntHorTotal(int asigIntHorTotal) {
		this.asigIntHorTotal = asigIntHorTotal;
	}
	/**
	 * @return Return the asigIntHorReal.
	 */
	public int getAsigIntHorReal() {
		return asigIntHorReal;
	}
	/**
	 * @param asigIntHorReal The asigIntHorReal to set.
	 */
	public void setAsigIntHorReal(int asigIntHorReal) {
		this.asigIntHorReal = asigIntHorReal;
	}
	/**
	 * @return Return the anho.
	 */
	public int getAnho() {
		return anho;
	}
	/**
	 * @param anho The anho to set.
	 */
	public void setAnho(int anho) {
		this.anho = anho;
	}
	/**
	 * @return Return the periodo.
	 */
	public int getPeriodo() {
		return periodo;
	}
	/**
	 * @param periodo The periodo to set.
	 */
	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}
	
	
}
