package util;

import com.google.gson.annotations.SerializedName;

public class LogEvaluacionDimensionDto {
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
	
	@SerializedName("Metodolog&iacute;a")
	private String metodologia;

	public void setMetodologia(String metodologia){
		this.metodologia=metodologia;
	}

	public String getMetodologia(){
		return this.metodologia;
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

	@SerializedName("Periodo")
	private String periodo;
	
	public void setPeriodo(String periodo){
		this.periodo=periodo;
	}

	public String getPeriodo(){
		return this.periodo;
	}	

	@SerializedName("Dimensi&oacute;n")
	private String dimension;

	public void setDimension(String dimension){
		this.dimension=dimension;
	}

	public String getDimension(){
		return this.dimension;
	}

	@SerializedName("Nota")
	private String nota;

	public void setNota(String nota){
		this.nota=nota;
	}

	public String getNota(){
		return this.nota;
	}	

	@SerializedName("Observaci&oacute;n")
	private String observacion;

	public void setObservacion(String observacion){
		this.observacion=observacion;
	}

	public String getObservacion(){
		return this.observacion;
	}	

}
