package siges.usuario.beans;

import java.io.Serializable;

/**
 *	VERSION		FECHA			AUTOR				DESCIPCION
 *		1.0		23/04/2018		JORGE CAMACHO		CODIGO ESPAGUETI Y AGREGAR EL CAMPO percorreoinstitucional
 * 
*/


/**
 * Nombre: Usuario<br>
 * Descripcinn: bean de Usuario<br>
 * Funciones de la pngina: Subir a la Bean la información del Usuario<br>
 * Entidades afectadas: Usuario<br>
 * Fecha de modificacinn: 20/07/2005 <br>
 * 
 * @author Latined <br>
 * @version v 1.0 <br>
 */
public class Usuario implements Serializable, Cloneable {

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

	private String usulogin;
	private String usupassword;
	private String usucodjerar;
	private String usuperfcodigo;
	private String usupernumdocum;
	private String pertipdocum;
	private String pernumdocum;
	private String pernombre1;
	private String pernombre2;
	private String perapellido1;
	private String perapellido2;
	private String pernumdocum2;
	private String pertipdocum2;
	private String estadoper;
	private String estadousu;
	private String usucodjerar2;
	private String usuperfcodigo2;
	private String usucoddependencia;
	private String codNivel;
	private String percorreoinstitucional;
	
	
	/**
	 * @return Devuelve usulogin.
	 */
	public String getUsulogin() {
		return this.usulogin != null ? this.usulogin : "";
	}

	/**
	 * @param usulogin
	 * El usulogin a establecer.
	 */
	public void setUsulogin(String usulogin) {
		this.usulogin = usulogin;
	}
	
	/**
	 * @return Devuelve usupassword.
	 */
	public String getUsupassword() {
		return this.usupassword != null ? this.usupassword : "";
	}

	/**
	 * @param usupassword
	 * El usupassword a establecer.
	 */
	public void setUsupassword(String usupassword) {
		this.usupassword = usupassword;
	}
	
	/**
	 * @return Devuelve usucodjerar.
	 */
	public String getUsucodjerar() {
		return this.usucodjerar != null ? this.usucodjerar : "";
	}

	/**
	 * @param usucodjerar
	 * El usucodjerar a establecer.
	 */
	public void setUsucodjerar(String usucodjerar) {
		this.usucodjerar = usucodjerar;
	}
	
	/**
	 * @return Devuelve usuperfcodigo.
	 */
	public String getUsuperfcodigo() {
		return this.usuperfcodigo != null ? this.usuperfcodigo : "";
	}

	/**
	 * @param usuperfcodigo
	 * El usuperfcodigo a establecer.
	 */
	public void setUsuperfcodigo(String usuperfcodigo) {
		this.usuperfcodigo = usuperfcodigo;
	}
	
	/**
	 * @return Devuelve usupernumdocum.
	 */
	public String getUsupernumdocum() {
		return this.usupernumdocum != null ? this.usupernumdocum : "";
	}

	/**
	 * @param usupernumdocum
	 * El usupernumdocum a establecer.
	 */
	public void setUsupernumdocum(String usupernumdocum) {
		this.usupernumdocum = usupernumdocum;
	}
	
	/**
	 * @return Devuelve pertipdocum.
	 */
	public String getPertipdocum() {
		return this.pertipdocum != null ? this.pertipdocum : "";
	}

	/**
	 * @param pertipdocum
	 * El pertipdocum a establecer.
	 */
	public void setPertipdocum(String pertipdocum) {
		this.pertipdocum = pertipdocum;
	}
	
	/**
	 * @return Devuelve pernumdocum.
	 */
	public String getPernumdocum() {
		return this.pernumdocum != null ? this.pernumdocum : "";
	}

	/**
	 * @param pernumdocum
	 * El pernumdocum a establecer.
	 */
	public void setPernumdocum(String pernumdocum) {
		this.pernumdocum = pernumdocum;
	}
	
	/**
	 * @return Devuelve pernombre1.
	 */
	public String getPernombre1() {
		return this.pernombre1 != null ? this.pernombre1 : "";
	}

	/**
	 * @param pernombre1
	 * El pernombre1 a establecer.
	 */
	public void setPernombre1(String pernombre1) {
		this.pernombre1 = pernombre1;
	}
	
	/**
	 * @return Devuelve pernombre2.
	 */
	public String getPernombre2() {
		return this.pernombre2 != null ? this.pernombre2 : "";
	}

	/**
	 * @param pernombre2
	 * El pernombre2 a establecer.
	 */
	public void setPernombre2(String pernombre2) {
		this.pernombre2 = pernombre2;
	}
	
	/**
	 * @return Devuelve perapellido1.
	 */
	public String getPerapellido1() {
		return this.perapellido1 != null ? this.perapellido1 : "";
	}

	/**
	 * @param perapellido1
	 * El perapellido1 a establecer.
	 */
	public void setPerapellido1(String perapellido1) {
		this.perapellido1 = perapellido1;
	}

	/**
	 * @return Devuelve perapellido2.
	 */
	public String getPerapellido2() {
		return this.perapellido2 != null ? this.perapellido2 : "";
	}

	/**
	 * @param perapellido2
	 * El perapellido2 a establecer.
	 */
	public void setPerapellido2(String perapellido2) {
		this.perapellido2 = perapellido2;
	}
	
	/**
	 * @return Devuelve pernumdocum2.
	 */
	public String getPernumdocum2() {
		return this.pernumdocum2 != null ? this.pernumdocum2 : "";
	}

	/**
	 * @param pernumdocum2
	 * El pernumdocum2 a establecer.
	 */
	public void setPernumdocum2(String pernumdocum2) {
		this.pernumdocum2 = pernumdocum2;
	}
	
	/**
	 * @return Devuelve pertipdocum2.
	 */
	public String getPertipdocum2() {
		return this.pertipdocum2 != null ? this.pertipdocum2 : "";
	}

	/**
	 * @param pertipdocum2
	 * El pertipdocum2 a establecer.
	 */
	public void setPertipdocum2(String pertipdocum2) {
		this.pertipdocum2 = pertipdocum2;
	}
	
	/**
	 * @return Devuelve estadoper.
	 */
	public String getEstadoper() {
		return this.estadoper != null ? this.estadoper : "";
	}

	/**
	 * @param estadoper
	 * El estadoper a establecer.
	 */
	public void setEstadoper(String estadoper) {
		this.estadoper = estadoper;
	}

	/**
	 * @return Devuelve estadousu.
	 */
	public String getEstadousu() {
		return this.estadousu != null ? this.estadousu : "";
	}

	/**
	 * @param estadousu
	 * El estadousu a establecer.
	 */
	public void setEstadousu(String estadousu) {
		this.estadousu = estadousu;
	}
	
	/**
	 * @return Devuelve usucodjerar2.
	 */
	public String getUsucodjerar2() {
		return this.usucodjerar2 != null ? this.usucodjerar2 : "";
	}

	/**
	 * @param usucodjerar2
	 * El usucodjerar2 a establecer.
	 */
	public void setUsucodjerar2(String usucodjerar2) {
		this.usucodjerar2 = usucodjerar2;
	}
	
	/**
	 * @return Devuelve usuperfcodigo2.
	 */
	public String getUsuperfcodigo2() {
		return this.usuperfcodigo2 != null ? this.usuperfcodigo2 : "";
	}

	/**
	 * @param usuperfcodigo2
	 * El usuperfcodigo2 a establecer.
	 */
	public void setUsuperfcodigo2(String usuperfcodigo2) {
		this.usuperfcodigo2 = usuperfcodigo2;
	}
	
	public String getUsucoddependencia() {
		return usucoddependencia;
	}

	public void setUsucoddependencia(String usucoddependencia) {
		this.usucoddependencia = usucoddependencia;
	}

	public String getCodNivel() {
		return codNivel;
	}

	public void setCodNivel(String codNivel) {
		this.codNivel = codNivel;
	}

	public String getPercorreoinstitucional() {
		return percorreoinstitucional;
	}

	public void setPercorreoinstitucional(String percorreoinstitucional) {
		this.percorreoinstitucional = percorreoinstitucional;
	}
	
}
