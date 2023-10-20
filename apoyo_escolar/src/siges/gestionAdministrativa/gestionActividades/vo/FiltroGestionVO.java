package siges.gestionAdministrativa.gestionActividades.vo;

public class FiltroGestionVO {
	
	int MES = 0;
	
	/**
	 * 1 - Colegio
	 * 2 - Sede
	 * 3 - Jornada
	 * 4 - Sede - Jornada
	 * 5 - Metodologia - Grado
	 * 6 - Grupo
	 */
	int nivel = 0;
	
	/**
	 * 1 - Todas
	 * 2 - Activas
	 * 3 - Inactivos
	 */
	int estado = 0;
	
	
	public int getMES() {
		return MES;
	}
	public void setMES(int mes) {
		this.MES = mes;
	}
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}

}
