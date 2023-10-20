/**
 * 
 */
package articulacion.artPlantillaFinal.vo;

import java.util.List;

/**
 * 19/09/2007 
 * @author Latined
 * @version 1.2
 */
public class UrlImportar {
	private int tipo;
	private int estado;
	private String pathPlantilla;
	private String nombrePlantilla;
	private String pathDescarga;
	private String nombreDescarga;
	private String pathRelativo;
	private List resultado;
	
	/**
	 * @return Return the estado.
	 */
	public int getEstado() {
		return estado;
	}
	/**
	 * @param estado The estado to set.
	 */
	public void setEstado(int estado) {
		this.estado = estado;
	}
	/**
	 * @return Return the nombreDescarga.
	 */
	public String getNombreDescarga() {
		return nombreDescarga;
	}
	/**
	 * @param nombreDescarga The nombreDescarga to set.
	 */
	public void setNombreDescarga(String nombreDescarga) {
		this.nombreDescarga = nombreDescarga;
	}
	/**
	 * @return Return the nombrePlantilla.
	 */
	public String getNombrePlantilla() {
		return nombrePlantilla;
	}
	/**
	 * @param nombrePlantilla The nombrePlantilla to set.
	 */
	public void setNombrePlantilla(String nombrePlantilla) {
		this.nombrePlantilla = nombrePlantilla;
	}
	/**
	 * @return Return the pathDescarga.
	 */
	public String getPathDescarga() {
		return pathDescarga;
	}
	/**
	 * @param pathDescarga The pathDescarga to set.
	 */
	public void setPathDescarga(String pathDescarga) {
		this.pathDescarga = pathDescarga;
	}
	/**
	 * @return Return the pathPlantilla.
	 */
	public String getPathPlantilla() {
		return pathPlantilla;
	}
	/**
	 * @param pathPlantilla The pathPlantilla to set.
	 */
	public void setPathPlantilla(String pathPlantilla) {
		this.pathPlantilla = pathPlantilla;
	}
	/**
	 * @return Return the pathRelativo.
	 */
	public String getPathRelativo() {
		return pathRelativo;
	}
	/**
	 * @param pathRelativo The pathRelativo to set.
	 */
	public void setPathRelativo(String pathRelativo) {
		this.pathRelativo = pathRelativo;
	}
	/**
	 * @return Return the tipo.
	 */
	public int getTipo() {
		return tipo;
	}
	/**
	 * @param tipo The tipo to set.
	 */
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return Return the resultado.
	 */
	public List getResultado() {
		return resultado;
	}
	/**
	 * @param resultado The resultado to set.
	 */
	public void setResultado(List resultado) {
		this.resultado = resultado;
	}	

}
