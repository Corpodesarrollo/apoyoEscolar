package util;

import com.google.gson.annotations.SerializedName;

public class LogLogroDto {
	@SerializedName("identificador de registro")
	private String identificadorRegistro;
	private String vigencia;
	private String metodolog�a;
	private String grado;
	private String asignatura;
	private int periodoInicial;
	private int periodoFinal;
	private String logro;
	private String abreviatura;
	private int orden;
	private String comentario;
	@SerializedName("tipo de cargue")
	private String tipoCargue;
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
	public String getMetodolog�a() {
		return metodolog�a;
	}
	public void setMetodolog�a(String metodolog�a) {
		this.metodolog�a = metodolog�a;
	}
	
	public String getAsignatura() {
		return asignatura;
	}
	public void setAsignatura(String asignatura) {
		this.asignatura = asignatura;
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
	public String getTipoCargue() {
		return tipoCargue;
	}
	public void setTipoCargue(String tipoCargue) {
		this.tipoCargue = tipoCargue;
	}
	public String getGrado() {
		return grado;
	}
	public void setGrado(String grado) {
		this.grado = grado;
	}


}
