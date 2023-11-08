package util;

import com.google.gson.annotations.SerializedName;

public class LogInformacionMeritosDto {
	@SerializedName("Tipo de aspecto positivo")
	private String tipoDeAspectoPositivo;

	public void setTipoDeAspectoPositivo(String tipoDeAspectoPositivo)
	{
		this.tipoDeAspectoPositivo=tipoDeAspectoPositivo;
	}

	public String getTipoDeAspectoPositivo()
	{
		return this.tipoDeAspectoPositivo;
	}

	@SerializedName("T&eacute;cnica de reconocimiento")
	private String tecnicaDeReconocimiento;

	public void setTecnicaDeReconocimiento(String tecnicaDeReconocimiento)
	{
		this.tecnicaDeReconocimiento=tecnicaDeReconocimiento;
	}

	public String getTecnicaDeReconocimiento()
	{
		return this.tecnicaDeReconocimiento;
	}

	@SerializedName("&Aacute;rea de conocimiento o asignatura")
	private String areaDeConocimientoOAsignatura;

	public void setAreaDeConocimientoOAsignatura(String areaDeConocimientoOAsignatura)
	{
		this.areaDeConocimientoOAsignatura=areaDeConocimientoOAsignatura;
	}

	public String getAreaDeConocimientoOAsignatura()
	{
		return this.areaDeConocimientoOAsignatura;
	}

	@SerializedName("Descripci&oacute;n, observaci&oacute;n o recomendaci&oacute;n")
	private String descripcionObservacionORecomendacion;

	public void setDescripcionObservacionORecomendacion(String descripcionObservacionORecomendacion)
	{
		this.descripcionObservacionORecomendacion=descripcionObservacionORecomendacion;
	}

	public String getDescripcionObservacionORecomendacion()
	{
		return this.descripcionObservacionORecomendacion;
	}

	@SerializedName("Reportado por")
	private String reportadoPor;

	public void setReportadoPor(String reportadoPor)
	{
		this.reportadoPor=reportadoPor;
	}

	public String getReportadoPor()
	{
		return this.reportadoPor;
	}
}
