/**
 * 
 */
package siges.perfil.vo;

/**
 * 9/08/2007 
 * @author Latined
 * @version 1.2
 */
public class Url {
	private String pathPlantilla;
	private String nombrePlantilla;
	private String pathDescarga;
	private String nombreDescarga;
	private String pathRelativo;	
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
}
