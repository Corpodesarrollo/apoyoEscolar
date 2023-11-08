package util;

import com.google.gson.annotations.SerializedName;

public class LogAreaDto {

	@SerializedName("Identificador de registro")
	private String identificadorRegistro;
	@SerializedName("Vigencia")
	private String vigencia;
	@SerializedName("Metodolog&iacute;a")
	private String metodologia;
	@SerializedName("Area")
	private String area;
	@SerializedName("Nombre")
	private String nombre;
	@SerializedName("Abreviatura")
	private String abreviatura;
	@SerializedName("Orden")
	private String orden;
	@SerializedName("Grados")
	private String grados;
	
	public String getIdentificadorRegistro() {
		return identificadorRegistro;
	}
	public void setIdentificadorRegistro(String identificadorRegistro) {
		this.identificadorRegistro = identificadorRegistro;
	}
	public String getVigencia() {
		return vigencia;
	}
	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}
	public String getMetodologia() {
		return metodologia;
	}
	public void setMetodologia(String metodologia) {
		this.metodologia = metodologia;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getAbreviatura() {
		return abreviatura;
	}
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}
	public String getGrados() {
		return grados;
	}
	public void setGrados(String grados) {
		this.grados = grados;
	}
	
	
}
