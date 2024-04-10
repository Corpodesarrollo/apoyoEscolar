package util;

import com.google.gson.annotations.SerializedName;

public class LogDescargaArchivos {
	@SerializedName("Archivo")
	private String archivo;
	
	public String getArchivo() {
		return archivo;
	}
	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}
}
