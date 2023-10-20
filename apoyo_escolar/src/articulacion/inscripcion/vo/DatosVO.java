package articulacion.inscripcion.vo;

import siges.common.vo.Vo;

public class DatosVO extends Vo {

	private int componente;
	private long especialidad;
	private int semestre;
	private int jornada;
	private int asignatura;

	public int getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(int asignatura) {
		this.asignatura = asignatura;
	}

	public int getComponente() {
		return componente;
	}

	public long getEspecialidad() {
		return especialidad;
	}

	/**
	 * @param componente
	 *            The componente to set.
	 */
	public void setComponente(int componente) {
		// System.out.println("LLEGA A SETCOMPONENTE="+componente);
		this.componente = componente;
	}

	/**
	 * @param especialidad
	 *            The especialidad to set.
	 */
	public void setEspecialidad(long especialidad) {
		this.especialidad = especialidad;
	}

	public int getSemestre() {
		return semestre;
	}

	public void setSemestre(int semestre) {
		this.semestre = semestre;
	}

	public int getJornada() {
		return jornada;
	}

	public void setJornada(int jornada) {
		this.jornada = jornada;
	}

}
