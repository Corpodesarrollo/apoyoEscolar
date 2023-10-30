package util;

import com.google.gson.annotations.SerializedName;

public class LogEstudianteDto {
	@SerializedName("Tipo Documento")
	private String tipoDocumento;

	public void setTipodocumento(String tipoDocumento){
		this.tipoDocumento=tipoDocumento;
	}

	public String getTipodocumento(){
		return this.tipoDocumento;
	}
	
	@SerializedName("Numero Identificacion")
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
	
	@SerializedName("Estado")
	private String estado;

	public void setEstado(String estado){
		this.estado=estado;
	}

	public String getEstado(){
		return this.estado;
	}
	
	@SerializedName("Log de accion")
	private LogDetalleAccionEstudianteDto logAccion;

	public void setLogAccion(LogDetalleAccionEstudianteDto  logAccion){
		this.logAccion=logAccion;
	}

	public LogDetalleAccionEstudianteDto getLogAccion(){
		return this.logAccion;
	}
	
	@SerializedName("Nombre del Colegio")
	private String nombreInstitucion;

	public void setNombreInstitucion(String nombreInstitucion){
		this.nombreInstitucion=nombreInstitucion;
	}

	public String getNombreInstitucion(){
		return this.nombreInstitucion;
	}
	
	@SerializedName("Sede")
	private String sede;

	public void setSede(String sede){
		this.sede=sede;
	}

	public String getSede(){
		return this.sede;
	}
	
	@SerializedName("Jornada")
	private String jornada;

	public void setJornada(String jornada){
		this.jornada=jornada;
	}

	public String getJornada(){
		return this.jornada;
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
	
	
	
	
}
