package siges.perfil.beans;

import java.io.*;

/**
*	Nombre:	 Perfil <br>
*	Descripcion:	Bean de los datos de Perfil <br> 
*	Funciones de la pagina:	Subir al Bean los campos de la entidad Perfil <br>
*	Entidades afectadas:	Perfil <br>
*	Fecha de modificacinn:	20/07/2005 <br>
*	@author Latined <br>
*	@version v 1.0 <br>
*/
public class Perfil implements Serializable, Cloneable{
	public static int PERFIL_DOCENTE = 422;
	private String perfcodigo;
	private String perfnombre;
	private String perfdescripcion;
	private String perfabreviatura;
	private String perfnivel;
	private String estado;

	/**
	 * @return Devuelve perfabreviatura.
	 */
	public String getPerfabreviatura() {
		return perfabreviatura;
	}
	/**
	 * @param perfabreviatura El perfabreviatura a establecer.
	 */
	public void setPerfabreviatura(String perfabreviatura) {
		this.perfabreviatura = perfabreviatura;
	}
	/**
	 * @return Devuelve perfcodigo.
	 */
	public String getPerfcodigo() {
		return perfcodigo;
	}
	/**
	 * @param perfcodigo El perfcodigo a establecer.
	 */
	public void setPerfcodigo(String perfcodigo) {
		this.perfcodigo = perfcodigo;
	}
	/**
	 * @return Devuelve perfdescripcion.
	 */
	public String getPerfdescripcion() {
		return perfdescripcion;
	}
	/**
	 * @param perfdescripcion El perfdescripcion a establecer.
	 */
	public void setPerfdescripcion(String perfdescripcion) {
		this.perfdescripcion = perfdescripcion;
	}
	/**
	 * @return Devuelve perfnombre.
	 */
	public String getPerfnombre() {
		return perfnombre;
	}
	/**
	 * @param perfnombre El perfnombre a establecer.
	 */
	public void setPerfnombre(String perfnombre) {
		this.perfnombre = perfnombre;
	}

	/**
	 * @return Devuelve perfnivel.
	 */
	public String getPerfnivel() {
		return perfnivel;
	}
	/**
	 * @param perfnivel El perfnivel a establecer.
	 */
	public void setPerfnivel(String perfnivel) {
		this.perfnivel = perfnivel;
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

	/**
	 * @return Devuelve estado.
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * @param estado El estado a establecer.
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
}