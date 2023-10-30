package util;

import com.google.gson.annotations.SerializedName;

public class LogSeccionSaludDto {
	@SerializedName("Tipo de sangre")
	private String tipoDeSangre;

	public void setTipoDeSangre(String tipoDeSangre)
	{
		this.tipoDeSangre=tipoDeSangre;
	}

	public String getTipoDeSangre()
	{
		return this.tipoDeSangre;	
	}

	@SerializedName("Talla (cm)")
	private String talla;

	public void setTalla(String talla)
	{
		this.talla=talla;
	}

	public String getTalla()
	{
		return this.talla;	
	}

	@SerializedName("Peso (Kg)")
	private String peso;

	public void setPeso(String peso)
	{
		this.peso=peso;
	}

	public String getPeso()
	{
		return this.peso;	
	}

	@SerializedName("Discapacidad")
	private String discapacidad;

	public void setDiscapacidad(String discapacidad)
	{
		this.discapacidad=discapacidad;
	}

	public String getDiscapacidad()
	{
		return this.discapacidad;	
	}

	@SerializedName("Alergia")
	private String alergia;

	public void setAlergia(String alergia)
	{
		this.alergia=alergia;
	}

	public String getAlergia()
	{
		return this.alergia;	
	}

	@SerializedName("Enfermedades")
	private String enfermedades;

	public void setEnfermedades(String enfermedades)
	{
		this.enfermedades=enfermedades;
	}

	public String getEnfermedades()
	{
		return this.enfermedades;	
	}

	@SerializedName("Medicamentos")
	private String medicamentos;

	public void setMedicamentos(String medicamentos)
	{
		this.medicamentos=medicamentos;
	}

	public String getMedicamentos()
	{
		return this.medicamentos;	
	}

}
