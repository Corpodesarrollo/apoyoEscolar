package util;

import com.google.gson.annotations.SerializedName;

public class LogObservadorEstudianteDto extends LogEstudianteDto{
	@SerializedName("Log de acción")
	private LogDetalleAccionEstudianteDto logAccionDetalle;
	
	public void setLogAccionDetalle(LogDetalleAccionEstudianteDto logAccionDetalle)
	{
		this.logAccionDetalle=logAccionDetalle;
	}
	public LogDetalleAccionEstudianteDto getLogAccionDetalle()
	{
		return this.logAccionDetalle;
	}
	
}
