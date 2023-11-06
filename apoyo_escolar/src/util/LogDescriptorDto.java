package util;

import com.google.gson.annotations.SerializedName;

public class LogDescriptorDto {
	@SerializedName("Identificador de registro")
	private	String identificadorRegistro;
	@SerializedName("Vigencia")
	private String vigencia;
	@SerializedName("Metodologia")
	private String metodología;
	@SerializedName("Docente")
	private String docente;
	@SerializedName("Grado")
	private String grado;
	@SerializedName("Area")
	private String area;
	@SerializedName("Periodo inicial")
	private int periodoInicial;
	@SerializedName("Periodo final")
	private int periodoFinal;
	@SerializedName("Logro")
	private String logro;
	@SerializedName("Abreviatura")
	private String abreviatura;
	private int orden;
	private String comentario;
	@SerializedName("tipo de descriptor")
	private String tipoDescriptor; 
	@SerializedName("tipo de origen de dato")
	private String tipoOrigenDato;
	private String descripcion;
	
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
	public String getMetodología() {
		return metodología;
	}
	public void setMetodología(String metodología) {
		this.metodología = metodología;
	}
	public String getDocente() {
		return docente;
	}
	public void setDocente(String docente) {
		this.docente = docente;
	}
	
	public String getGrado() {
		return grado;
	}
	public void setGrado(String grado) {
		this.grado = grado;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public int getPeriodoInicial() {
		return periodoInicial;
	}
	public void setPeriodoInicial(int periodoInicial) {
		this.periodoInicial = periodoInicial;
	}
	public int getPeriodoFinal() {
		return periodoFinal;
	}
	public void setPeriodoFinal(int periodoFinal) {
		this.periodoFinal = periodoFinal;
	}
	public String getLogro() {
		return logro;
	}
	public void setLogro(String logro) {
		this.logro = logro;
	}
	public String getAbreviatura() {
		return abreviatura;
	}
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
	public int getOrden() {
		return orden;
	}
	public void setOrden(int orden) {
		this.orden = orden;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public String getTipoDescriptor() {
		return tipoDescriptor;
	}
	public void setTipoDescriptor(String tipoDescriptor) {
		this.tipoDescriptor = tipoDescriptor;
	}
	public String getTipoOrigenDato() {
		return tipoOrigenDato;
	}
	public void setTipoOrigenDato(String tipoOrigenDato) {
		this.tipoOrigenDato = tipoOrigenDato;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
}
