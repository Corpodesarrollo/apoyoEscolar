package util;

import com.google.gson.annotations.SerializedName;

public class LogInformacionConvivenciaDto {
	@SerializedName("Fecha ocurrencia")
	private String fechaOcurrencia;

	public void setFechaOcurrencia(String fechaOcurrencia)
	{
		this.fechaOcurrencia=fechaOcurrencia;
	}

	public String getFechaOcurrencia()
	{
		return this.fechaOcurrencia;
	}

	@SerializedName("Periodo")
	private String periodo;

	public void setPeriodo(String periodo)
	{
		this.periodo=periodo;
	}

	public String getPeriodo()
	{
		return this.periodo;
	}

	@SerializedName("Tipo de situaci&oacute;n")
	private String tipoDeSituacion;

	public void setTipoDeSituacion(String tipoDeSituacion)
	{
		this.tipoDeSituacion=tipoDeSituacion;
	}

	public String getTipoDeSituacion()
	{
		return this.tipoDeSituacion;
	}

	@SerializedName("Tipo de evento")
	private String tipoDeEvento;

	public void setTipoDeEvento(String tipoDeEvento)
	{
		this.tipoDeEvento=tipoDeEvento;
	}

	public String getTipoDeEvento()
	{
		return this.tipoDeEvento;
	}

	@SerializedName("Lugar")
	private String lugar;

	public void setLugar(String lugar)
	{
		this.lugar=lugar;
	}

	public String getLugar()
	{
		return this.lugar;
	}

	@SerializedName("Qui&eacute;n informo del evento")
	private String qui�nInformoDelEvento;

	public void setQui�nInformoDelEvento(String qui�nInformoDelEvento)
	{
		this.qui�nInformoDelEvento=qui�nInformoDelEvento;
	}

	public String getQui�nInformoDelEvento()
	{
		return this.qui�nInformoDelEvento;
	}


}
