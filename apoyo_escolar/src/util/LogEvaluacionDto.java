package util;

import com.google.gson.annotations.SerializedName;

public class LogEvaluacionDto {
	
	@SerializedName("Tipo de identificaci&oacute;n")
	private String tipoIdentificacion;
	
	public void setTipoIdentificacion(String tipoIdentificacion){
		this.tipoIdentificacion=tipoIdentificacion;
	}

	public String getTipoIdentificacion(){
		return this.tipoIdentificacion;
	}

	@SerializedName("N&uacute;mero Identificaci&oacute;n")
	private String numeroIdentificacion;

	public void setNumeroIdentificacion(String numeroIdentificacion){
		this.numeroIdentificacion=numeroIdentificacion;
	}

	public String getNumeroIdentificacion(){
		return this.numeroIdentificacion;
	}
	
	@SerializedName("Nombres y Apellidos")
	private String nombreCompleto;

	public void setNombreCompleto(String nombreCompleto){
		this.nombreCompleto=nombreCompleto;
	}

	public String getNombreCompleto(){
		return this.nombreCompleto;
	}
	
	@SerializedName("Grado")
	private String grado;

	public void setGrado(String grado){
		this.grado=grado;
	}

	public String getGrado(){
		return this.grado;
	}
	
	@SerializedName("Grupo")
	private String grupo;

	public void setGrupo(String grupo){
		this.grupo=grupo;
	}

	public String getGrupo(){
		return this.grupo;
	}

	@SerializedName("Periodo estudiante")
	private String periodo;
	
	public void setPeriodo(String periodo){
		this.periodo=periodo;
	}

	public String getPeriodo(){
		return this.periodo;
	}	

	@SerializedName("Materia")
	private String materia;

	public void setMateria(String materia){
		this.materia=materia;
	}

	public String getMateria(){
		return this.materia;
	}

	@SerializedName("Nota recuperada")
	private String notaRecuperada;

	public void setNotaRecuperada(String notaRecuperada){
		this.notaRecuperada=notaRecuperada;
	}

	public String getNotaRecuperada(){
		return this.notaRecuperada;
	}	

	@SerializedName("Nota anterior")
	private String notaAnterior;

	public void setNotaAnterior(String notaAnterior){
		this.notaAnterior=notaAnterior;
	}

	public String getNotaAnterior(){
		return this.notaAnterior;
	}	

	@SerializedName("Nota actualizada")
	private String notaActualizada;

	public void setNotaActualizada(String notaActualizada){
		this.notaActualizada=notaActualizada;
	}

	public String getNotaActualizada(){
		return this.notaActualizada;
	}	
	
}
