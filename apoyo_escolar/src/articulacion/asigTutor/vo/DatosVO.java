package articulacion.asigTutor.vo;

import siges.common.vo.Vo;

public class DatosVO extends Vo{

	private long institucion;
	private long sede;
	private long jornada;
	private long metodologia;
	private int especialidad;
	private int semestre;
	private long docente;
	
	public long getDocente() {
		return docente;
	}
	public void setDocente(long docente) {
		this.docente = docente;
	}
	public int getEspecialidad() {
		return especialidad;
	}
	public long getInstitucion() {
		return institucion;
	}
	public long getJornada() {
		return jornada;
	}
	public long getSede() {
		return sede;
	}
	public int getSemestre() {
		return semestre;
	}
	public void setEspecialidad(int especialidad) {
		this.especialidad = especialidad;
	}
	public void setInstitucion(long institucion) {
		this.institucion = institucion;
	}
	public void setJornada(long jornada) {
		this.jornada = jornada;
	}
	public void setSede(long sede) {
		this.sede = sede;
	}
	public void setSemestre(int semestre) {
		this.semestre = semestre;
	}
	public long getMetodologia() {
		return metodologia;
	}
	public void setMetodologia(long metodologia) {
		this.metodologia = metodologia;
	}
	

	
}
