package util;

import com.google.gson.annotations.SerializedName;

public class LogSeccionFamiliarDto {
	@SerializedName("Secci&oacute;n Parentesco")
	private String seccionParentesco;

	public void setSeccionParentesco(String seccionParentesco)
	{
		this.seccionParentesco=seccionParentesco;
	}

	public String getSeccionParentesco()
	{
		return this.seccionParentesco;
	}

	@SerializedName("Tipo documento")
	private String tipoDocumento;

	public void setTipoDocumento(String tipoDocumento)
	{
		this.tipoDocumento=tipoDocumento;
	}

	public String getTipoDocumento()
	{
		return this.tipoDocumento;
	}

	@SerializedName("N° de identificaci&oacute;n")
	private String noDeIdentificacion;

	public void setNoDeIdentificacion(String noDeIdentificacion)
	{
		this.noDeIdentificacion=noDeIdentificacion;
	}

	public String getNoDeIdentificacion()
	{
		return this.noDeIdentificacion;
	}

	@SerializedName("Primer nombre	")
	private String primerNombre;

	public void setPrimerNombre(String primerNombre)
	{
		this.primerNombre=primerNombre;
	}

	public String getPrimerNombre()
	{
		return this.primerNombre;
	}

	@SerializedName("Segundo nombre")
	private String segundoNombre;

	public void setSegundoNombre(String segundoNombre)
	{
		this.segundoNombre=segundoNombre;
	}

	public String getSegundoNombre()
	{
		return this.segundoNombre;
	}

	@SerializedName("Primer apellido")
	private String primerApellido;

	public void setPrimerApellido(String primerApellido)
	{
		this.primerApellido=primerApellido;
	}

	public String getPrimerApellido()
	{
		return this.primerApellido;
	}

	@SerializedName("Segundo apellido")
	private String segundoApellido;

	public void setSegundoApellido(String segundoApellido)
	{
		this.segundoApellido=segundoApellido;
	}

	public String getSegundoApellido()
	{
		return this.segundoApellido;
	}

	@SerializedName("Edad")
	private String edad;

	public void setEdad(String edad)
	{
		this.edad=edad;
	}

	public String getEdad()
	{
		return this.edad;
	}

	@SerializedName("¿Es acudiente?")
	private String esAcudiente;

	public void setEsAcudiente(String esAcudiente)
	{
		this.esAcudiente=esAcudiente;
	}

	public String getEsAcudiente()
	{
		return this.esAcudiente;
	}

	@SerializedName("Tel&eacute;fono")
	private String teléfono;

	public void setTeléfono(String teléfono)
	{
		this.teléfono=teléfono;
	}

	public String getTeléfono()
	{
		return this.teléfono;
	}

}
