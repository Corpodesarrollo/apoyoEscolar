package articulacion.artAusencias.vo;

import siges.common.vo.Vo;

public class AusenciasVO extends Vo{

	private long artAusCodEstud;
	private long[] artAusCodAsig;
	private int[] artAusClaseAsig;
	private String artAusFecha;
	private long artAusGrupoArt;
	private long artAusTipMotivo;
	private String artAusDescripcion;
	
	public int[] getArtAusClaseAsig() {
		return artAusClaseAsig;
	}
	public void setArtAusClaseAsig(int[] artAusClaseAsig) {
		this.artAusClaseAsig = artAusClaseAsig;
	}
	public long[] getArtAusCodAsig() {
		return artAusCodAsig;
	}
	public void setArtAusCodAsig(long[] artAusCodAsig) {
		this.artAusCodAsig = artAusCodAsig;
	}
	public long getArtAusCodEstud() {
		return artAusCodEstud;
	}
	public void setArtAusCodEstud(long artAusCodEstud) {
		this.artAusCodEstud = artAusCodEstud;
	}
	public String getArtAusDescripcion() {
		return artAusDescripcion;
	}
	public void setArtAusDescripcion(String artAusDescripcion) {
		this.artAusDescripcion = artAusDescripcion;
	}
	public String getArtAusFecha() {
		return artAusFecha;
	}
	public void setArtAusFecha(String artAusFecha) {
		this.artAusFecha = artAusFecha;
	}
	public long getArtAusGrupoArt() {
		return artAusGrupoArt;
	}
	public void setArtAusGrupoArt(long artAusGrupoArt) {
		this.artAusGrupoArt = artAusGrupoArt;
	}
	public long getArtAusTipMotivo() {
		return artAusTipMotivo;
	}
	public void setArtAusTipMotivo(long artAusTipMotivo) {
		this.artAusTipMotivo = artAusTipMotivo;
	}
	

}
