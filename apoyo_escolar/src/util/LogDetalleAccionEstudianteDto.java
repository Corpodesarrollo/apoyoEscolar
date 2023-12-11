package util;

import com.google.gson.annotations.SerializedName;

public class LogDetalleAccionEstudianteDto {
	@SerializedName("Log de Acci&oacute;n")
	private LogAccionDto logAccion;

	public void setLogAccion(LogAccionDto logAccion)
	{
		this.logAccion=logAccion;	
	}

	public LogAccionDto getLogAccion()
	{
		return this.logAccion;
	}

	@SerializedName("Secci&oacute;n familiar")
	private String seccionFamiliar;

	public void setSeccionFamiliar(String seccionFamiliar)
	{
		this.seccionFamiliar=seccionFamiliar;	
	}

	public String getSeccionFamiliar()
	{
		return this.seccionFamiliar;
	}

	@SerializedName("Secci&oacute;n salud")
	private String seccionSalud;

	public void setSeccionSalud(String seccionSalud)
	{
		this.seccionSalud=seccionSalud;	
	}

	public String getSeccionSalud()
	{
		return this.seccionSalud;
	}

	@SerializedName("Informaci&oacute;n de Convivencia - Registrar")
	private String informacionConvivencia;

	public void setInformacionConvivencia(String informacionConvivencia)
	{
		this.informacionConvivencia=informacionConvivencia;	
	}

	public String getInformacionConvivencia()
	{
		return this.informacionConvivencia;
	}

	@SerializedName("Informaci&oacute;n sobre m&eacute;ritos, capacidades y/o talentos excepcionales")
	private String informacionMeritos;

	public void setInformacionMeritos(String informacionMeritos)
	{
		this.informacionMeritos=informacionMeritos;	
	}

	public String getInformacionMeritos()
	{
		return this.informacionMeritos;
	}

	@SerializedName("Acci&oacute;n")
	private String accion;

	public void setAccion(String accion)
	{
		this.accion=accion;
	}

	public String getAccion()
	{
		return this.accion;
	}
}
