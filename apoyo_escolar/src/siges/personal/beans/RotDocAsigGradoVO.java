package siges.personal.beans;

import java.io.Serializable;

public class RotDocAsigGradoVO implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	private Long rotDagJergrado;
	private Long rotDagDocente;
	private Long rotDagAsignatura;
	private int rotDagIhtotal;
	private int rotDagIhreal;
	private int rotDagIhprop;
	private int rotDagGvigencia;

	public RotDocAsigGradoVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RotDocAsigGradoVO(Long rotDagJergrado, Long rotDagDocente, Long rotDagAsignatura, int rotDagIhtotal,
			int rotDagIhreal, int rotDagIhprop, int rotDagGvigencia) {
		super();
		this.rotDagJergrado = rotDagJergrado;
		this.rotDagDocente = rotDagDocente;
		this.rotDagAsignatura = rotDagAsignatura;
		this.rotDagIhtotal = rotDagIhtotal;
		this.rotDagIhreal = rotDagIhreal;
		this.rotDagIhprop = rotDagIhprop;
		this.rotDagGvigencia = rotDagGvigencia;
	}

	@Override
	public String toString() {
		return "RotDocAsigGradoVO [rotDagJergrado=" + rotDagJergrado + ", rotDagDocente=" + rotDagDocente
				+ ", rotDagAsignatura=" + rotDagAsignatura + ", rotDagIhtotal=" + rotDagIhtotal + ", rotDagIhreal="
				+ rotDagIhreal + ", rotDagIhprop=" + rotDagIhprop + ", rotDagGvigencia=" + rotDagGvigencia + "]";
	}

	/**
	 * Hace una copia del objeto mismo
	 * 
	 * @return Object
	 */
	public Object clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch (CloneNotSupportedException e) {
			System.err.println("MyObject can't clone");
		}
		return o;
	}

	public Long getRotDagJergrado() {
		return rotDagJergrado;
	}

	public void setRotDagJergrado(Long rotDagJergrado) {
		this.rotDagJergrado = rotDagJergrado;
	}

	public Long getRotDagDocente() {
		return rotDagDocente;
	}

	public void setRotDagDocente(Long rotDagDocente) {
		this.rotDagDocente = rotDagDocente;
	}

	public Long getRotDagAsignatura() {
		return rotDagAsignatura;
	}

	public void setRotDagAsignatura(Long rotDagAsignatura) {
		this.rotDagAsignatura = rotDagAsignatura;
	}

	public int getRotDagIhtotal() {
		return rotDagIhtotal;
	}

	public void setRotDagIhtotal(int rotDagIhtotal) {
		this.rotDagIhtotal = rotDagIhtotal;
	}

	public int getRotDagIhreal() {
		return rotDagIhreal;
	}

	public void setRotDagIhreal(int rotDagIhreal) {
		this.rotDagIhreal = rotDagIhreal;
	}

	public int getRotDagIhprop() {
		return rotDagIhprop;
	}

	public void setRotDagIhprop(int rotDagIhprop) {
		this.rotDagIhprop = rotDagIhprop;
	}

	public int getRotDagGvigencia() {
		return rotDagGvigencia;
	}

	public void setRotDagGvigencia(int rotDagGvigencia) {
		this.rotDagGvigencia = rotDagGvigencia;
	}

}
