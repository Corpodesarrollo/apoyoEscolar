package siges.gestionAdministrativa.agenda.vo;

public class FiltroPermisosVO {
	
	private int permiso = -1; /* DoEminio: G_CONSTANTE, TIPO 15 */
	private String fechaInicio;
	private String fechaFin;
	

	/*
	 * Pendientes 0
	 * Aprobadas 1
	 * Rechazadas 2
	 */
	private int estado = -1;
	
	private boolean download = false;
	
	private String file;

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public boolean isDownload() {
		return download;
	}

	public void setDownload(boolean download) {
		this.download = download;
	}

	public int getPermiso() {
		return permiso;
	}

	public void setPermiso(int permiso) {
		this.permiso = permiso;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}


	
	

}
