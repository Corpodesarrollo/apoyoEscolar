package util;

import com.google.gson.annotations.SerializedName;

public class LogPorcentajeAsignaturaDto {
	@SerializedName("Colegio")
	private String insttucion;

	public void setInsttucion(String insttucion)
	{
		this.insttucion=insttucion;
	}

	public String getInsttucion(){
		return this.insttucion;
	}

	@SerializedName("Sede")
	private String sede;

	public void setSede(String sede)
	{
		this.sede=sede;
	}

	public String getSede(){
		return this.sede;
	}

	@SerializedName("Jornda")
	private String jornada;

	public void setJornada(String jornada)
	{
		this.jornada=jornada;
	}

	public String getJornada(){
		return this.jornada;
	}

	@SerializedName("Nombre Asignatura")
	private String asignatura;

	public void setAsignatura(String asignatura)
	{
		this.asignatura=asignatura;
	}

	public String getAsignatura(){
		return this.asignatura;
	}

	@SerializedName("Porcentaje")
	private String porcentaje;

	public void setPorcentaje(String porcentaje)
	{
		this.porcentaje=porcentaje;
	}

	public String getPorcentaje(){
		return this.porcentaje;
	}

	@SerializedName("&Aacute;rea")
	private String area;

	public void setArea(String area)
	{
		this.area=area;
	}

	public String getArea(){
		return this.area;
	}
}
