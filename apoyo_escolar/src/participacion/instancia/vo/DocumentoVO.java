/**
 * 
 */
package participacion.instancia.vo;

import siges.common.vo.Vo;

/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class DocumentoVO extends Vo{
	private int docInstancia;
	private int docCodigo;
	private String docNombre;
	private String docDescripcion;
	private String docFecha;
	private String docTipoMime;
	private String docExtension;
	private String docRuta;
	private byte[] docArchivo;
	
	/**
	 * @return Return the docArchivo.
	 */
	public final byte[] getDocArchivo() {
		return docArchivo;
	}
	/**
	 * @param docArchivo The docArchivo to set.
	 */
	public final void setDocArchivo(byte[] docArchivo) {
		this.docArchivo = docArchivo;
	}
	/**
	 * @return Return the docCodigo.
	 */
	public final int getDocCodigo() {
		return docCodigo;
	}
	/**
	 * @param docCodigo The docCodigo to set.
	 */
	public final void setDocCodigo(int docCodigo) {
		this.docCodigo = docCodigo;
	}
	/**
	 * @return Return the docDescripcion.
	 */
	public final String getDocDescripcion() {
		return docDescripcion;
	}
	/**
	 * @param docDescripcion The docDescripcion to set.
	 */
	public final void setDocDescripcion(String docDescripcion) {
		this.docDescripcion = docDescripcion;
	}
	/**
	 * @return Return the docFecha.
	 */
	public final String getDocFecha() {
		return docFecha;
	}
	/**
	 * @param docFecha The docFecha to set.
	 */
	public final void setDocFecha(String docFecha) {
		this.docFecha = docFecha;
	}
	/**
	 * @return Return the docInstancia.
	 */
	public final int getDocInstancia() {
		return docInstancia;
	}
	/**
	 * @param docInstancia The docInstancia to set.
	 */
	public final void setDocInstancia(int docInstancia) {
		this.docInstancia = docInstancia;
	}
	/**
	 * @return Return the docNombre.
	 */
	public final String getDocNombre() {
		return docNombre;
	}
	/**
	 * @param docNombre The docNombre to set.
	 */
	public final void setDocNombre(String docNombre) {
		this.docNombre = docNombre;
	}
	/**
	 * @return Return the docTipoMime.
	 */
	public final String getDocTipoMime() {
		return docTipoMime;
	}
	/**
	 * @param docTipoMime The docTipoMime to set.
	 */
	public final void setDocTipoMime(String docTipoMime) {
		this.docTipoMime = docTipoMime;
	}
	/**
	 * @return Return the docExtension.
	 */
	public final String getDocExtension() {
		return docExtension;
	}
	/**
	 * @param docExtension The docExtension to set.
	 */
	public final void setDocExtension(String docExtension) {
		this.docExtension = docExtension;
	}
	/**
	 * @return Return the docRuta.
	 */
	public String getDocRuta() {
		return docRuta;
	}
	/**
	 * @param docRuta The docRuta to set.
	 */
	public void setDocRuta(String docRuta) {
		this.docRuta = docRuta;
	}
}
