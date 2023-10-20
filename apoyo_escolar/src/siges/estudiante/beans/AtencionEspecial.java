package siges.estudiante.beans;

import java.io.*;

/**
*	Nombre:	Convivencia <BR>
*	Descripcion:	Datos de informacion de Convivencia del egresado	<BR>
*	Funciones de la pagina:	Almacenar los datos del egresado para un nuevo registro o para editar	<BR>
*	Entidades afectadas:	Convivencia	<BR>
*	Fecha de modificacinn:	20/07/2005	<BR>
*	@author Latined	<BR>
*	@version v 1.0	<BR>
*/
public class AtencionEspecial implements Serializable, Cloneable{

	private String atesid;
	private String atestipo;
	private String atesperiodo;
	private String atesfecha;
	private String atesasunto;
	private String atesdescripcion;
	private String atesmostrar;
	private String atescodest;
	private String estado;
	private String usuario;

    /**
     * @return Devuelve usuario.
     */
    public String getUsuario() {
        return (usuario != null) ? usuario : "";
    }
    /**
     * @param usuario El usuario a establecer.
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    /**
     * @return Devuelve estado.
     */
    public String getEstado() {
        return (estado != null) ? estado : "";
    }
    /**
     * @param estado El estado a establecer.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
    /**
     * @return Devuelve atesasunto.
     */
    public String getAtesasunto() {
        return (atesasunto != null) ? atesasunto : "";
    }
    /**
     * @param atesasunto El atesasunto a establecer.
     */
    public void setAtesasunto(String atesasunto) {
        this.atesasunto = atesasunto;
    }
    /**
     * @return Devuelve atescodest.
     */
    public String getAtescodest() {
        return (atescodest != null) ? atescodest : "";
    }
    /**
     * @param atescodest El atescodest a establecer.
     */
    public void setAtescodest(String atescodest) {
        this.atescodest = atescodest;
    }
    /**
     * @return Devuelve atesdescripcion.
     */
    public String getAtesdescripcion() {
        return (atesdescripcion != null) ? atesdescripcion : "";
    }
    /**
     * @param atesdescripcion El atesdescripcion a establecer.
     */
    public void setAtesdescripcion(String atesdescripcion) {
        this.atesdescripcion = atesdescripcion;
    }
    /**
     * @return Devuelve atesfecha.
     */
    public String getAtesfecha() {
        return (atesfecha != null) ? atesfecha : "";
    }
    /**
     * @param atesfecha El atesfecha a establecer.
     */
    public void setAtesfecha(String atesfecha) {
        this.atesfecha = atesfecha;
    }
    /**
     * @return Devuelve atesid.
     */
    public String getAtesid() {
        return (atesid != null) ? atesid : "";
    }
    /**
     * @param atesid El atesid a establecer.
     */
    public void setAtesid(String atesid) {
        this.atesid = atesid;
    }
    /**
     * @return Devuelve atesmostrar.
     */
    public String getAtesmostrar() {
        return (atesmostrar != null) ? atesmostrar : "";
    }
    /**
     * @param atesmostrar El atesmostrar a establecer.
     */
    public void setAtesmostrar(String atesmostrar) {
        this.atesmostrar = atesmostrar;
    }
    /**
     * @return Devuelve atesperiodo.
     */
    public String getAtesperiodo() {
        return (atesperiodo != null) ? atesperiodo : "";
    }
    /**
     * @param atesperiodo El atesperiodo a establecer.
     */
    public void setAtesperiodo(String atesperiodo) {
        this.atesperiodo = atesperiodo;
    }
    /**
     * @return Devuelve atestipo.
     */
    public String getAtestipo() {
        return (atestipo != null) ? atestipo : "";
    }
    /**
     * @param atestipo El atestipo a establecer.
     */
    public void setAtestipo(String atestipo) {
        this.atestipo = atestipo;
    }
/**
 *	Hace una copia del objeto mismo
 *	@return Object 
 */
	public Object clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch(CloneNotSupportedException e) {
			System.err.println("MyObject can't clone");
		}
		return o;
	}		
}