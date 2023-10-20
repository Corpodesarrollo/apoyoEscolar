package siges.gestionAdministrativa.agenda.vo;

public class FiltroCircularesVO {
	
	private String mes;
	private String nivel;
	
	private String metodologia;
	private String grado;
	private String grupo;
	private String asignatura;
	long jerarquia;
	
	private int nivelUsuario;

	/*
	 * Todas 0
	 * Pendiente 1
	 * Entragada 2
	 */
	int estado = -1;
	
	public int getNivelUsuario() {
		return nivelUsuario;
	}

	public void setNivelUsuario(int nivelUsuario) {
		this.nivelUsuario = nivelUsuario;
	}
	
	
	public long getJerarquia() {
		return jerarquia;
	}

	public void setJerarquia(long jerarquia) {
		this.jerarquia = jerarquia;
	}
	
	public String getMetodologia() {
		return metodologia;
	}

	public void setMetodologia(String metodologia) {
		this.metodologia = metodologia;
	}

	public String getGrado() {
		return grado;
	}

	public void setGrado(String grado) {
		this.grado = grado;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(String asignatura) {
		this.asignatura = asignatura;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	
	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}


	
	

}
