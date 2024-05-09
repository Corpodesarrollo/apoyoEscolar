package util;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class LogEvaluacionLogroDto {
	
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
	
	@SerializedName("Logros")
	private List<LogEvaluacionLogroDetalleDto> logros;

	public void setLogros(List<LogEvaluacionLogroDetalleDto> logros){
		this.logros=logros;
	}

	public List<LogEvaluacionLogroDetalleDto> getLogros(){
		return this.logros;
	}
	
}
