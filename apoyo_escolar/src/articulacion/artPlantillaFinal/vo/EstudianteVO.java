/**
 * 
 */
package articulacion.artPlantillaFinal.vo;

import java.util.List;

import siges.common.vo.Vo;

/**
 * 1/09/2007 
 * @author Latined
 * @version 1.2
 */
public class EstudianteVO extends Vo{
	private long estCodigo;
	private int estConsecutivo;
	private String estNombre;
	private String estApellido;
	private List estNota;
	
	/**
	 * @return Return the estApellido.
	 */
	public String getEstApellido() {
		return estApellido;
	}
	/**
	 * @param estApellido The estApellido to set.
	 */
	public void setEstApellido(String estApellido) {
		this.estApellido = estApellido;
	}
	/**
	 * @return Return the estCodigo.
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
	 * @return Return the estConsecutivo.
	 */
	public int getEstConsecutivo() {
		return estConsecutivo;
	}
	/**
	 * @param estConsecutivo The estConsecutivo to set.
	 */
	public void setEstConsecutivo(int estConsecutivo) {
		this.estConsecutivo = estConsecutivo;
	}
	/**
	 * @return Return the estNombre.
	 */
	public String getEstNombre() {
		return estNombre;
	}
	/**
	 * @param estNombre The estNombre to set.
	 */
	public void setEstNombre(String estNombre) {
		this.estNombre = estNombre;
	}
	/**
	 * @return Return the estNota.
	 */
	public List getEstNota() {
		return estNota;
	}
	/**
	 * @param estNota The estNota to set.
	 */
	public void setEstNota(List estNota) {
		this.estNota = estNota;
	}
	
}
