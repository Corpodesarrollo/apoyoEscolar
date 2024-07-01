package util;

import com.google.gson.annotations.SerializedName;

public class LogObservacionAsignaturaDto {

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
	
	@SerializedName("Asignatura")
	private String asignatura;

	public void setAsignatura(String asignatura){
		this.asignatura=asignatura;
	}

	public String getAsignatura(){
		return this.asignatura;
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
