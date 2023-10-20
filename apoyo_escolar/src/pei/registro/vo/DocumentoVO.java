package pei.registro.vo;

import siges.common.vo.Vo;

public class DocumentoVO extends Vo {
	private long docInstitucion;
	private int docCodigo;
	private String docNombre;
	private String docDescripcion;
	private String docUsuarioNombre;
	private long docUsuario;
	private String docFecha;
	private String docRuta;
	
	/**
	 * @return the docInstitucion
	 */
	public long getDocInstitucion() {
		return docInstitucion;
	}
	/**
	 * @param docInstitucion the docInstitucion to set
	 */
	public void setDocInstitucion(long docInstitucion) {
		this.docInstitucion = docInstitucion;
	}
	/**
	 * @return the docCodigo
	 */
	public int getDocCodigo() {
		return docCodigo;
	}
	/**
	 * @param docCodigo the docCodigo to set
	 */
	public void setDocCodigo(int docCodigo) {
		this.docCodigo = docCodigo;
	}
	/**
	 * @return the docNombre
	 */
	public String getDocNombre() {
		return docNombre;
	}
	/**
	 * @param docNombre the docNombre to set
	 */
	public void setDocNombre(String docNombre) {
		this.docNombre = docNombre;
	}
	/**
	 * @return the docDescripcion
	 */
	public String getDocDescripcion() {
		return docDescripcion;
	}
	/**
	 * @param docDescripcion the docDescripcion to set
	 */
	public void setDocDescripcion(String docDescripcion) {
		this.docDescripcion = docDescripcion;
	}
	/**
	 * @return the docFecha
	 */
	public String getDocFecha() {
		return docFecha;
	}
	/**
	 * @param docFecha the docFecha to set
	 */
	public void setDocFecha(String docFecha) {
		this.docFecha = docFecha;
	}
	/**
	 * @return the docRuta
	 */
	public String getDocRuta() {
		return docRuta;
	}
	/**
	 * @param docRuta the docRuta to set
	 */
	public void setDocRuta(String docRuta) {
		this.docRuta = docRuta;
	}
	/**
	 * @return the docUsuarioNombre
	 */
	public String getDocUsuarioNombre() {
		return docUsuarioNombre;
	}
	/**
	 * @param docUsuarioNombre the docUsuarioNombre to set
	 */
	public void setDocUsuarioNombre(String docUsuarioNombre) {
		this.docUsuarioNombre = docUsuarioNombre;
	}
	/**
	 * @return the docUsuario
	 */
	public long getDocUsuario() {
		return docUsuario;
	}
	/**
	 * @param docUsuario the docUsuario to set
	 */
	public void setDocUsuario(long docUsuario) {
		this.docUsuario = docUsuario;
	}
	
}
