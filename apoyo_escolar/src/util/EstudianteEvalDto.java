package util;

public class EstudianteEvalDto {

	private Long codigo;

	public void setCodigo(Long codigo){
		this.codigo=codigo;
	}

	public Long getCodigo(){
		return this.codigo;
	}

	private String tipoIdentificacion;

	public void setTipoIdentificacion(String tipoIdentificacion){
		this.tipoIdentificacion=tipoIdentificacion;
	}

	public String getTipoIdentificacion(){
		return this.tipoIdentificacion;
	}

	private String numeroIdentificacion;

	public void setNumeroIdentificacion(String numeroIdentificacion){
		this.numeroIdentificacion=numeroIdentificacion;
	}

	public String getNumeroIdentificacion(){
		return this.numeroIdentificacion;
	}

	private String nombreCompleto;

	public void setNombreCompleto(String nombreCompleto){
		this.nombreCompleto=nombreCompleto;
	}

	public String getNombreCompleto(){
		return this.nombreCompleto;
	}

	private Float nota;

	public void setNota(Float nota){
		this.nota=nota;
	}

	public Float getNota(){
		return this.nota;
	}
		
}
