/**
 * 
 */


package siges.subirFotografia.vo;

import siges.common.vo.Vo;
/**
 * 24/09/2008 
 * @author Latined
 * @version 1.2
 */
public class SubirFotografiaVO extends Vo{
	private String ayuNombre;
	private byte[] ayuArchivo;
	private long estCodigo;
	
	/**
	 * @return Returns the estCodigo.
	 */
	public long getEstCodigo() {
		return estCodigo;
	}
	/**
	 * @param estCodigo The estCodigo to set.
	 */
	public void setEstCodigo(long estCodigo) {
		this.estCodigo = estCodigo;
	}
	/**
	 * @return Return the ayuArchivo.
	 */
	public byte[] getAyuArchivo() {
		return ayuArchivo;
	}
	/**
	 * @param ayuArchivo The ayuArchivo to set.
	 */
	public void setAyuArchivo(byte[] ayuArchivo) {
		this.ayuArchivo = ayuArchivo;
	}
	/**
	 * @return Return the ayuNombre.
	 */
	public String getAyuNombre() {
		return ayuNombre;
	}
	/**
	 * @param ayuNombre The ayuNombre to set.
	 */
	public void setAyuNombre(String ayuNombre) {
		this.ayuNombre = ayuNombre;
	}

}
