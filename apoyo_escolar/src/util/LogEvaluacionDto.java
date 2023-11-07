package util;

import com.google.gson.annotations.SerializedName;

public class LogEvaluacionDto {

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
	private Integer periodo;
	
	public void setPeriodo(Integer periodo){
		this.periodo=periodo;
	}

	public Integer getPeriodo(){
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
	private Float notaRecuperada;

	public void setNotaRecuperada(Float notaRecuperada){
		this.notaRecuperada=notaRecuperada;
	}

	public Float getNotaRecuperada(){
		return this.notaRecuperada;
	}	

	@SerializedName("Nota anterior")
	private Float notaAnterior;

	public void setNotaAnterior(Float notaAnterior){
		this.notaAnterior=notaAnterior;
	}

	public Float getNotaAnterior(){
		return this.notaAnterior;
	}	

	@SerializedName("Nota actualizada")
	private Float notaActualizada;

	public void setNotaActualizada(Float notaActualizada){
		this.notaActualizada=notaActualizada;
	}

	public Float getNotaActualizada(){
		return this.notaActualizada;
	}	
	
}
