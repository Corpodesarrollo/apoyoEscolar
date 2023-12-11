package util;

import com.google.gson.annotations.SerializedName;

public class LogAccionDto {
	@SerializedName("Fecha de expedici&oacute;n")
	private String fechaExpedicion;

	public void setFechaExpedicion(String fechaExpedicion)
	{
		this.fechaExpedicion=fechaExpedicion;
	}

	public String getFechaExpedicion()
	{
		return this.fechaExpedicion;
	}

	@SerializedName("Departamento de expedici&oacute;n")
	private String departamentoExpedicion;

	public void setDepartamentoExpedicion(String departamentoExpedicion)
	{
		this.departamentoExpedicion=departamentoExpedicion;
	}

	public String getDepartamentoExpedicion()
	{
		return this.departamentoExpedicion;
	}

	@SerializedName("Municipio de expedici&oacute;n")
	private String municipioExpedicion;

	public void setMunicipioExpedicion(String municipioExpedicion)
	{
		this.municipioExpedicion=municipioExpedicion;
	}

	public String getMunicipioExpedicion()
	{
		return this.municipioExpedicion;
	}

	@SerializedName("Departamento de nacimiento")
	private String departamentoNacimiento;

	public void setDepartamentoNacimiento(String departamentoNacimiento)
	{
		this.departamentoNacimiento=departamentoNacimiento;
	}

	public String getDepartamentoNacimiento()
	{
		return this.departamentoNacimiento;
	}

	@SerializedName("Municipio de nacimiento")
	private String municipioNacieminto;

	public void setMunicipioNacieminto(String municipioNacieminto)
	{
		this.municipioNacieminto=municipioNacieminto;
	}

	public String getMunicipioNacieminto()
	{
		return this.municipioNacieminto;
	}

	@SerializedName("Tel&eacute;fono 1")
	private String telefono1;

	public void setTelefono1(String telefono1)
	{
		this.telefono1=telefono1;
	}

	public String getTelefono1()
	{
		return this.telefono1;
	}

	@SerializedName("Tel&eacute;fono 2")
	private String telefono2;

	public void setTelefono2(String telefono2)
	{
		this.telefono2=telefono2;
	}

	public String getTelefono2()
	{
		return this.telefono2;
	}

	@SerializedName("Correo electr&oacute;nico institucional")
	private String correoInstitucional;

	public void setCorreoInstitucional(String correoInstitucional)
	{
		this.correoInstitucional=correoInstitucional;
	}

	public String getCorreoInstitucional()
	{
		return this.correoInstitucional;
	}

	@SerializedName("Correo electr&oacute;nico personal")
	private String correoPersonal;

	public void setCorreoPersonal(String correoPersonal)
	{
		this.correoPersonal=correoPersonal;
	}

	public String getCorreoPersonal()
	{
		return this.correoPersonal;
	}

	@SerializedName("Direcci&oacute;n de residencia")
	private String direccionResidencia;

	public void setDireccionResidencia(String direccionResidencia)
	{
		this.direccionResidencia=direccionResidencia;
	}

	public String getDireccionResidencia()
	{
		return this.direccionResidencia;
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
