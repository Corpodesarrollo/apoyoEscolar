/**
 * 
 */
package articulacion.artEncuesta.vo;

import siges.common.vo.Vo;

/**
 * 5/12/2007 
 * @author Latined
 * @version 1.2
 */
public class MostrarEncuestaVO extends Vo{
	
	private long codigo;
	private String []pregunta1;
	private String pregunta2a;
	private String pregunta2b;
	private long pregunta3;
	private long pregunta4;
	private long pregunta5a;
	private int pregunta5b;
	private String pregunta5c;
	private String pregunta6;
	private long pregunta6a;
	private long pregunta6b;
	private String pregunta7;
	private long pregunta7a;
	private long pregunta7b;
	private int estadoForma;
	
	/**
	 * @return Return the pregunta1.
	 */
	public String[] getPregunta1() {
		return pregunta1;
	}
	/**
	 * @param pregunta1 The pregunta1 to set.
	 */
	public void setPregunta1(String[] pregunta1) {
		this.pregunta1 = pregunta1;
	}
	/**
	 * @return Return the pregunta2a.
	 */
	public String getPregunta2a() {
		return pregunta2a;
	}
	/**
	 * @param pregunta2a The pregunta2a to set.
	 */
	public void setPregunta2a(String pregunta2a) {
		this.pregunta2a = pregunta2a;
	}
	/**
	 * @return Return the pregunta2b.
	 */
	public String getPregunta2b() {
		return pregunta2b;
	}
	/**
	 * @param pregunta2b The pregunta2b to set.
	 */
	public void setPregunta2b(String pregunta2b) {
		this.pregunta2b = pregunta2b;
	}
	/**
	 * @return Return the pregunta3.
	 */
	public long getPregunta3() {
		return pregunta3;
	}
	/**
	 * @param pregunta3 The pregunta3 to set.
	 */
	public void setPregunta3(long pregunta3) {
		this.pregunta3 = pregunta3;
	}
	/**
	 * @return Return the pregunta4.
	 */
	public long getPregunta4() {
		return pregunta4;
	}
	/**
	 * @param pregunta4 The pregunta4 to set.
	 */
	public void setPregunta4(long pregunta4) {
		this.pregunta4 = pregunta4;
	}
	/**
	 * @return Return the pregunta5a.
	 */
	public long getPregunta5a() {
		return pregunta5a;
	}
	/**
	 * @param pregunta5a The pregunta5a to set.
	 */
	public void setPregunta5a(long pregunta5a) {
		this.pregunta5a = pregunta5a;
	}
	/**
	 * @return Return the pregunta5c.
	 */
	public String getPregunta5c() {
		return pregunta5c;
	}
	/**
	 * @param pregunta5c The pregunta5c to set.
	 */
	public void setPregunta5c(String pregunta5c) {
		this.pregunta5c = pregunta5c;
	}
	/**
	 * @return Return the pregunta6a.
	 */
	public long getPregunta6a() {
		return pregunta6a;
	}
	/**
	 * @param pregunta6a The pregunta6a to set.
	 */
	public void setPregunta6a(long pregunta6a) {
		this.pregunta6a = pregunta6a;
	}
	/**
	 * @return Return the pregunta6b.
	 */
	public long getPregunta6b() {
		return pregunta6b;
	}
	/**
	 * @param pregunta6b The pregunta6b to set.
	 */
	public void setPregunta6b(long pregunta6b) {
		this.pregunta6b = pregunta6b;
	}
	/**
	 * @return Return the codigo.
	 */
	public long getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo The codigo to set.
	 */
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	/**
	 * @return Return the pregunta6.
	 */
	public String getPregunta6() {
		return pregunta6;
	}
	/**
	 * @param pregunta6 The pregunta6 to set.
	 */
	public void setPregunta6(String pregunta6) {
		this.pregunta6 = pregunta6;
	}
	/**
	 * @return Return the pregunta7.
	 */
	public String getPregunta7() {
		return pregunta7;
	}
	/**
	 * @param pregunta7 The pregunta7 to set.
	 */
	public void setPregunta7(String pregunta7) {
		this.pregunta7 = pregunta7;
	}
	/**
	 * @return Return the pregunta7a.
	 */
	public long getPregunta7a() {
		return pregunta7a;
	}
	/**
	 * @param pregunta7a The pregunta7a to set.
	 */
	public void setPregunta7a(long pregunta7a) {
		this.pregunta7a = pregunta7a;
	}
	/**
	 * @return Return the pregunta7b.
	 */
	public long getPregunta7b() {
		return pregunta7b;
	}
	/**
	 * @param pregunta7b The pregunta7b to set.
	 */
	public void setPregunta7b(long pregunta7b) {
		this.pregunta7b = pregunta7b;
	}
	/**
	 * @return Return the pregunta5b.
	 */
	public int getPregunta5b() {
		return pregunta5b;
	}
	/**
	 * @param pregunta5b The pregunta5b to set.
	 */
	public void setPregunta5b(int pregunta5b) {
		this.pregunta5b = pregunta5b;
	}
	/**
	 * @return Return the estadoForma.
	 */
	public final int getEstadoForma() {
		return estadoForma;
	}
	/**
	 * @param estadoForma The estadoForma to set.
	 */
	public final void setEstadoForma(int estadoForma) {
		this.estadoForma = estadoForma;
	}
}
