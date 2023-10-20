/**
 * 
 */
package articulacion.artEncuesta.vo;

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
	private String estEstado;
	private String estFechaEnc;
	private String estTipoDoc;
	private String estNumDoc;
	
	public String getEstApellido() {
		return estApellido;
	}
	public void setEstApellido(String estApellido) {
		this.estApellido = estApellido;
	}
	public long getEstCodigo() {
		return estCodigo;
	}
	public void setEstCodigo(long estCodigo) {
		this.estCodigo = estCodigo;
	}
	public int getEstConsecutivo() {
		return estConsecutivo;
	}
	public void setEstConsecutivo(int estConsecutivo) {
		this.estConsecutivo = estConsecutivo;
	}
	public String getEstEstado() {
		return estEstado;
	}
	public void setEstEstado(String estEstado) {
		this.estEstado = estEstado;
	}
	public String getEstFechaEnc() {
		return estFechaEnc;
	}
	public void setEstFechaEnc(String estFechaEnc) {
		this.estFechaEnc = estFechaEnc;
	}
	public String getEstNombre() {
		return estNombre;
	}
	public void setEstNombre(String estNombre) {
		this.estNombre = estNombre;
	}
	public List getEstNota() {
		return estNota;
	}
	public void setEstNota(List estNota) {
		this.estNota = estNota;
	}
	public String getEstNumDoc() {
		return estNumDoc;
	}
	public void setEstNumDoc(String estNumDoc) {
		this.estNumDoc = estNumDoc;
	}
	public String getEstTipoDoc() {
		return estTipoDoc;
	}
	public void setEstTipoDoc(String estTipoDoc) {
		this.estTipoDoc = estTipoDoc;
	}
	

}
