package articulacion.asigGrupo.vo;

import siges.common.vo.Vo;

public class EstudianteArtVO extends Vo{

	private long estCodigo;
	private String estNombre1;
	private String estNombre2;
	private String estApellido1;
	private String estApellido2;
	private String artGrupoCodigo;
	
	public long getEstCodigo() {
		return estCodigo;
	}
	public void setEstCodigo(long estCodigo) {
		this.estCodigo = estCodigo;
	}
	public String getEstNombre1() {
		return estNombre1;
	}
	public void setEstNombre1(String estNombre1) {
		this.estNombre1 = estNombre1;
	}
	public String getEstNombre2() {
		return estNombre2;
	}
	public void setEstNombre2(String estNombre2) {
		this.estNombre2 = estNombre2;
	}
	public String getEstApellido1() {
		return estApellido1;
	}
	public void setEstApellido1(String estApellido1) {
		this.estApellido1 = estApellido1;
	}
	public String getEstApellido2() {
		return estApellido2;
	}
	public void setEstApellido2(String estApellido2) {
		this.estApellido2 = estApellido2;
	}
	public String getArtGrupoCodigo() {
		return artGrupoCodigo;
	}
	public void setArtGrupoCodigo(String artGrupoCodigo) {
		this.artGrupoCodigo = artGrupoCodigo;
	}
	
}
