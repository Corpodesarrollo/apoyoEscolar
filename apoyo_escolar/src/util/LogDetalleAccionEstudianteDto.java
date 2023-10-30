package util;

import com.google.gson.annotations.SerializedName;

public class LogDetalleAccionEstudianteDto {
	@SerializedName("Log de Acci�n")
	private LogAccionDto logAccion;

	public void setLogAccion(LogAccionDto logAccion)
	{
		this.logAccion=logAccion;	
	}

	public LogAccionDto getLogAccion()
	{
		return this.logAccion;
	}

	@SerializedName("Secci�n familiar")
	private String seccionFamiliar;

	public void setSeccionFamiliar(String seccionFamiliar)
	{
		this.seccionFamiliar=seccionFamiliar;	
	}

	public String getSeccionFamiliar()
	{
		return this.seccionFamiliar;
	}

	@SerializedName("Secci�n salud")
	private String seccionSalud;

	public void setSeccionSalud(String seccionSalud)
	{
		this.seccionSalud=seccionSalud;	
	}

	public String getSeccionSalud()
	{
		return this.seccionSalud;
	}

	@SerializedName("Informacion de Convivenvia - Registrar")
	private String informacionConvivencia;

	public void setInformacionConvivencia(String informacionConvivencia)
	{
		this.informacionConvivencia=informacionConvivencia;	
	}

	public String getInformacionConvivencia()
	{
		return this.informacionConvivencia;
	}

	@SerializedName("Informaci�n sobre m�ritos, capacidades y/o talentos excepcionales")
	private String informacionMeritos;

	public void setInformacionMeritos(String informacionMeritos)
	{
		this.informacionMeritos=informacionMeritos;	
	}

	public String getInformacionMeritos()
	{
		return this.informacionMeritos;
	}
}
