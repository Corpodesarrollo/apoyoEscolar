package siges.personal.beans;

import java.io.Serializable;

public class RotDocAsigGradoGrupoVO implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 1L;
	private Long rotDagGjergrado;
	private Long rotDagGdocente;
	private Long rotDagGasignatura;
	private Long rotDagGgrupo;
	private int rotDagGvigencia;
	private int rotDagGih;

	public RotDocAsigGradoGrupoVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RotDocAsigGradoGrupoVO(Long rotDagGjergrado, Long rotDagGdocente, Long rotDagGasignatura, Long rotDagGgrupo,
			int rotDagGvigencia, int rotDagGih) {
		super();
		this.rotDagGjergrado = rotDagGjergrado;
		this.rotDagGdocente = rotDagGdocente;
		this.rotDagGasignatura = rotDagGasignatura;
		this.rotDagGgrupo = rotDagGgrupo;
		this.rotDagGvigencia = rotDagGvigencia;
		this.rotDagGih = rotDagGih;
	}

	@Override
	public String toString() {
		return "RotDocAsigGradoGrupoVO [rotDagGjergrado=" + rotDagGjergrado + ", rotDagGdocente=" + rotDagGdocente
				+ ", rotDagGasignatura=" + rotDagGasignatura + ", rotDagGgrupo=" + rotDagGgrupo + ", rotDagGvigencia="
				+ rotDagGvigencia + ", rotDagGih=" + rotDagGih + "]";
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

	public Long getRotDagGjergrado() {
		return rotDagGjergrado;
	}

	public void setRotDagGjergrado(Long rotDagGjergrado) {
		this.rotDagGjergrado = rotDagGjergrado;
	}

	public Long getRotDagGdocente() {
		return rotDagGdocente;
	}

	public void setRotDagGdocente(Long rotDagGdocente) {
		this.rotDagGdocente = rotDagGdocente;
	}

	public Long getRotDagGasignatura() {
		return rotDagGasignatura;
	}

	public void setRotDagGasignatura(Long rotDagGasignatura) {
		this.rotDagGasignatura = rotDagGasignatura;
	}

	public Long getRotDagGgrupo() {
		return rotDagGgrupo;
	}

	public void setRotDagGgrupo(Long rotDagGgrupo) {
		this.rotDagGgrupo = rotDagGgrupo;
	}

	public int getRotDagGvigencia() {
		return rotDagGvigencia;
	}

	public void setRotDagGvigencia(int rotDagGvigencia) {
		this.rotDagGvigencia = rotDagGvigencia;
	}

	public int getRotDagGih() {
		return rotDagGih;
	}

	public void setRotDagGih(int rotDagGih) {
		this.rotDagGih = rotDagGih;
	}

}
