package articulacion.gAsignatura.vo;

import siges.common.vo.Vo;

public class GAsignaturaVO extends Vo{
	
	private long geArtAsigCodigo;
	private String geArtAsigNombre;
	private long geArtAsigCodArea;
	
	public long getGeArtAsigCodArea() {
		return geArtAsigCodArea;
	}
	public void setGeArtAsigCodArea(long geArtAsigCodArea) {
		this.geArtAsigCodArea = geArtAsigCodArea;
	}
	public long getGeArtAsigCodigo() {
		return geArtAsigCodigo;
	}
	public void setGeArtAsigCodigo(long geArtAsigCodigo) {
		this.geArtAsigCodigo = geArtAsigCodigo;
	}
	public String getGeArtAsigNombre() {
		return geArtAsigNombre;
	}
	public void setGeArtAsigNombre(String geArtAsigNombre) {
		this.geArtAsigNombre = geArtAsigNombre;
	}
	
		
}
