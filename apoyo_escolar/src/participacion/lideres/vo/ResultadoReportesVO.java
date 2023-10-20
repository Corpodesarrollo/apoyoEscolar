/**
 * 
 */
package participacion.lideres.vo;

import siges.common.vo.Vo;

/**
 * Value Object del manejo del formulario de edicinn de actividades
 * 15/01/2009 
 * @author 
 * @version 1.1
 */
public class ResultadoReportesVO extends Vo{
	private String archivo;
	private String ruta;
	private String tipo;
	private boolean generado;
	private String rutaDisco;
	
	/**
	 * @return Return the archivo.
	 */
	public String getArchivo() {
		return archivo;
	}
	/**
	 * @param archivo The archivo to set.
	 */
	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}
	/**
	 * @return Return the generado.
	 */
	public boolean isGenerado() {
		return generado;
	}
	/**
	 * @param generado The generado to set.
	 */
	public void setGenerado(boolean generado) {
		this.generado = generado;
	}
	/**
	 * @return Return the ruta.
	 */
	public String getRuta() {
		return ruta;
	}
	/**
	 * @param ruta The ruta to set.
	 */
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	/**
	 * @return Return the tipo.
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo The tipo to set.
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return Return the rutaDisco.
	 */
	public final String getRutaDisco() {
		return rutaDisco;
	}
	/**
	 * @param rutaDisco The rutaDisco to set.
	 */
	public final void setRutaDisco(String rutaDisco) {
		this.rutaDisco = rutaDisco;
	}
}
