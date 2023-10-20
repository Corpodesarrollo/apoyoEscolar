package articulacion.asigGrupo.vo;

import siges.common.vo.Vo;

public class DatosVO extends Vo{


	private long inst;
	private int anho;
	private int metodologia;
	private long especialidad;
	private long asignatura;
	private int semestre;
	
	private int grado;
	private long grupo;
	
	public long getInst() {
		return inst;
	}
	public void setInst(long inst) {
		this.inst = inst;
	}
	public int getAnho() {
		return anho;
	}
	public void setAnho(int anho) {
		this.anho = anho;
	}
	public int getMetodologia() {
		return metodologia;
	}
	public void setMetodologia(int metodologia) {
		this.metodologia = metodologia;
	}
	public long getEspecialidad() {
		return especialidad;
	}
	public void setEspecialidad(long especialidad) {
		this.especialidad = especialidad;
	}
	public int getSemestre() {
		return semestre;
	}
	public void setSemestre(int sememestre) {
		this.semestre = sememestre;
	}
	public long getAsignatura() {
		return asignatura;
	}
	public void setAsignatura(long asignatura) {
		this.asignatura = asignatura;
	}
	public int getGrado() {
		return grado;
	}
	public void setGrado(int grado) {
		this.grado = grado;
	}
	public long getGrupo() {
		return grupo;
	}
	public void setGrupo(long grupo) {
		this.grupo = grupo;
	}	
	
}
