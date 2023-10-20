package articulacion.importarArticulacion.objetos;

import siges.common.vo.Vo;

public class CodigoEstudianteVO extends Vo{


	private long codigo;
	private long especialidad;
	private long semestre;
	private long grupo;
	private String nivelado;
	

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public long getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(long especialidad) {
		this.especialidad = especialidad;
	}

	public long getGrupo() {
		return grupo;
	}

	public void setGrupo(long grupo) {
		this.grupo = grupo;
	}

	public String getNivelado() {
		return nivelado;
	}

	public void setNivelado(String nivelado) {
		this.nivelado = nivelado;
	}

	public long getSemestre() {
		return semestre;
	}

	public void setSemestre(long semestre) {
		this.semestre = semestre;
	}
	
	}
