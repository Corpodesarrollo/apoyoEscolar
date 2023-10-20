package siges.estudiante.beans;

import java.io.Serializable;

/**
 * Nombre: Familiar<BR>
 * Descripcion: Almacena la informacion familiar del estudiante <BR>
 * Funciones de la pagina: Bean de datos de formulario <BR>
 * Entidades afectadas: Familia <BR>
 * Fecha de modificacinn: 20/07/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */
public class Familiar implements Serializable, Cloneable {
	private String madre;
	private String padre;
	private String famcodigo;
	private String famnommadre;
	private String famtipdocmadre;
	private String famnumdocmadre;
	private String famocumadre;
	private String famdirmadre;
	private String famtelmadre;
	private String famnompadre;
	private String famtipdocpadre;
	private String famnumdocpadre;
	private String famocupadre;
	private String famdirpadre;
	private String famtelpadre;
	private String famnomacudi;
	private String famtipdocacudi;
	private String famnumdocacudi;
	private String famocuacudi;
	private String famdiracudi;
	private String famtelacudi;
	private String famnumhermanos;
	private String famnomhijos;
	private String estado;

	/**
	 * asigna el campo madre
	 * 
	 * @param String
	 *            s
	 */
	public void setMadre(String s) {

		this.madre = s;
	}

	/**
	 * retorna el campo madre
	 * 
	 * @return String
	 */
	public String getMadre() {
		return madre != null ? madre : "";
	}

	/**
	 * asigna el campo padre
	 * 
	 * @param String
	 *            s
	 */
	public void setPadre(String s) {
		this.padre = s;
	}

	/**
	 * retorna el campo padre
	 * 
	 * @return String
	 */
	public String getPadre() {
		return padre != null ? padre : "";
	}

	/**
	 * asigna el campo famcodigo
	 * 
	 * @param String
	 *            s
	 */
	public void setFamcodigo(String s) {
		this.famcodigo = s;
	}

	/**
	 * retorna el campo famcodigo
	 * 
	 * @return String
	 */
	public String getFamcodigo() {
		return famcodigo != null ? famcodigo : "";
	}

	/**
	 * asigna el campo Famnommadre
	 * 
	 * @param String
	 *            s
	 */
	public void setFamnommadre(String s) {
		this.famnommadre = s;
	}

	/**
	 * retorna el campo famnommadre
	 * 
	 * @return String
	 */
	public String getFamnommadre() {
		return famnommadre != null ? famnommadre : "";
	}

	/**
	 * asigna el campo Famtipdocmadre
	 * 
	 * @param String
	 *            s
	 */
	public void setFamtipdocmadre(String s) {
		this.famtipdocmadre = s;
	}

	/**
	 * retorna el campo famtipdocmadre
	 * 
	 * @return String
	 */
	public String getFamtipdocmadre() {
		return famtipdocmadre != null ? famtipdocmadre : "";
	}

	/**
	 * asigna el campo Famnumdocmadre
	 * 
	 * @param String
	 *            s
	 */
	public void setFamnumdocmadre(String s) {
		this.famnumdocmadre = s;
	}

	/**
	 * retorna el campo famnumdocmadre
	 * 
	 * @return String
	 */
	public String getFamnumdocmadre() {
		return famnumdocmadre != null ? famnumdocmadre : "";
	}

	/**
	 * asigna el campo Famocumadre
	 * 
	 * @param String
	 *            s
	 */
	public void setFamocumadre(String s) {
		this.famocumadre = s;
	}

	/**
	 * retorna el campo famocumadre
	 * 
	 * @return String
	 */
	public String getFamocumadre() {
		return famocumadre != null ? famocumadre : "";
	}

	/**
	 * asigna el campo Famdirmadre
	 * 
	 * @param String
	 *            s
	 */
	public void setFamdirmadre(String s) {
		this.famdirmadre = s;
	}

	/**
	 * retorna el campo famdirmadre
	 * 
	 * @return String
	 */
	public String getFamdirmadre() {
		return famdirmadre != null ? famdirmadre : "";
	}

	/**
	 * asigna el campo Famtelmadre
	 * 
	 * @param String
	 *            s
	 */
	public void setFamtelmadre(String s) {
		this.famtelmadre = s;
	}

	/**
	 * retorna el campo famtelmadre
	 * 
	 * @return String
	 */
	public String getFamtelmadre() {
		return famtelmadre != null ? famtelmadre : "";
	}

	/**
	 * asigna el campo Famnompadre
	 * 
	 * @param String
	 *            s
	 */
	public void setFamnompadre(String s) {
		this.famnompadre = s;
	}

	/**
	 * retorna el campo famnompadre
	 * 
	 * @return String
	 */
	public String getFamnompadre() {
		return famnompadre != null ? famnompadre : "";
	}

	/**
	 * asigna el campo Famtipdocpadre
	 * 
	 * @param String
	 *            s
	 */
	public void setFamtipdocpadre(String s) {
		this.famtipdocpadre = s;
	}

	/**
	 * retorna el campo Famtipdocpadre
	 * 
	 * @return String
	 */
	public String getFamtipdocpadre() {
		return famtipdocpadre != null ? famtipdocpadre : "";
	}

	/**
	 * asigna el campo Famnumdocpadre
	 * 
	 * @param String
	 *            s
	 */
	public void setFamnumdocpadre(String s) {
		this.famnumdocpadre = s;
	}

	/**
	 * retorna el campo Famnumdocpadre
	 * 
	 * @return String
	 */
	public String getFamnumdocpadre() {
		return famnumdocpadre != null ? famnumdocpadre : "";
	}

	/**
	 * asigna el campo Famocupadre
	 * 
	 * @param String
	 *            s
	 */
	public void setFamocupadre(String s) {
		this.famocupadre = s;
	}

	/**
	 * retorna el campo famocupadre
	 * 
	 * @return String
	 */
	public String getFamocupadre() {
		return famocupadre != null ? famocupadre : "";
	}

	/**
	 * asigna el campo Famdirpadre
	 * 
	 * @param String
	 *            s
	 */
	public void setFamdirpadre(String s) {
		this.famdirpadre = s;
	}

	/**
	 * retorna el campo famdirpadre
	 * 
	 * @return String
	 */
	public String getFamdirpadre() {
		return famdirpadre != null ? famdirpadre : "";
	}

	/**
	 * asigna el campo Famtelpadre
	 * 
	 * @param String
	 *            s
	 */
	public void setFamtelpadre(String s) {
		this.famtelpadre = s;
	}

	/**
	 * retorna el campo famtelpadre
	 * 
	 * @return String
	 */
	public String getFamtelpadre() {
		return famtelpadre != null ? famtelpadre : "";
	}

	/**
	 * asigna el campo famnomacudi
	 * 
	 * @param String
	 *            s
	 */
	public void setFamnomacudi(String s) {
		this.famnomacudi = s;
	}

	/**
	 * retorna el campo famnomacudi
	 * 
	 * @return String
	 */
	public String getFamnomacudi() {
		return famnomacudi != null ? famnomacudi : "";
	}

	/**
	 * asigna el campo famtipdocacudi
	 * 
	 * @param String
	 *            s
	 */
	public void setFamtipdocacudi(String s) {
		this.famtipdocacudi = s;
	}

	/**
	 * retorna el campo famtipdocacudi
	 * 
	 * @return String
	 */
	public String getFamtipdocacudi() {
		return famtipdocacudi != null ? famtipdocacudi : "";
	}

	/**
	 * asigna el campo famnumdocacudi
	 * 
	 * @param String
	 *            s
	 */
	public void setFamnumdocacudi(String s) {
		this.famnumdocacudi = s;
	}

	/**
	 * retorna el campo famnumdocacudi
	 * 
	 * @return String
	 */
	public String getFamnumdocacudi() {
		return famnumdocacudi != null ? famnumdocacudi : "";
	}

	/**
	 * asigna el campo famocuacudi
	 * 
	 * @param String
	 *            s
	 */
	public void setFamocuacudi(String s) {
		this.famocuacudi = s;
	}

	/**
	 * retorna el campo famocuacudi
	 * 
	 * @return String
	 */
	public String getFamocuacudi() {
		return famocuacudi != null ? famocuacudi : "";
	}

	/**
	 * asigna el campo famdiracudi
	 * 
	 * @param String
	 *            s
	 */
	public void setFamdiracudi(String s) {
		this.famdiracudi = s;
	}

	/**
	 * retorna el campo famdiracudi
	 * 
	 * @return String
	 */
	public String getFamdiracudi() {
		return famdiracudi != null ? famdiracudi : "";
	}

	/**
	 * asigna el campo famtelacudi
	 * 
	 * @param String
	 *            s
	 */
	public void setFamtelacudi(String s) {
		this.famtelacudi = s;
	}

	/**
	 * retorna el campo famtelacudi
	 * 
	 * @return String
	 */
	public String getFamtelacudi() {
		return famtelacudi != null ? famtelacudi : "";
	}

	/**
	 * asigna el campo famnumhermanos
	 * 
	 * @param String
	 *            s
	 */
	public void setFamnumhermanos(String s) {
		this.famnumhermanos = s;
	}

	/**
	 * retorna el campo famnumhermanos
	 * 
	 * @return String
	 */
	public String getFamnumhermanos() {
		return famnumhermanos != null ? famnumhermanos : "";
	}

	/**
	 * asigna el campo famnomhijos
	 * 
	 * @param String
	 *            s
	 */
	public void setFamnomhijos(String s) {
		this.famnomhijos = s;
	}

	/**
	 * retorna el campo famnomhijos
	 * 
	 * @return String
	 */
	public String getFamnomhijos() {
		return famnomhijos != null ? famnomhijos : "";
	}

	/**
	 * asigna el campo estado
	 * 
	 * @param String
	 *            s
	 */
	public void setEstado(String s) {
		this.estado = s;
	}

	/**
	 * retorna el campo estado
	 * 
	 * @return String
	 */
	public String getEstado() {
		return estado != null ? estado : "";
	}

	/**
	 * Hace una copia del objeto mismo
	 * 
	 * @return Object
	 */
	public Object clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch (CloneNotSupportedException e) {
			System.err.println("MyObject can't clone");
		}
		return o;
	}
}