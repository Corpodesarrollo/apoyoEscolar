package util;

import com.google.gson.annotations.SerializedName;

public class LogPersonalCargaDto {
	@SerializedName("Docente")
	private String docente;

	public void setDocente(String docente){
		this.docente=docente;
	}

	public String getDocente(){
		return this.docente;
	}
	
	@SerializedName("Institucion")
	private String institucion;

	public void setInstitucion(String institucion){
		this.institucion=institucion;
	}

	public String getInstitucion(){
		return this.institucion;
	}
	
	@SerializedName("Asignatura")
	private String asignatura;

	public void setAsignatura(String asignatura){
		this.asignatura=asignatura;
	}

	public String getAsignatura(){
		return this.asignatura;
	}

	@SerializedName("Grado")
	private String grado;

	public void setGrado(String grado){
		this.grado=grado;
	}

	public String getGrado(){
		return this.grado;
	}

	@SerializedName("Horas")
	private String horas;

	public void setHoras(String horas){
		this.horas=horas;
	}

	public String getHoras(){
		return this.horas;
	}

}
