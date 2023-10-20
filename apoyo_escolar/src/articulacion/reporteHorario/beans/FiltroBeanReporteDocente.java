package articulacion.reporteHorario.beans;

import java.io.*;


/**
*	Nombre: FiltroBeanReporte.java	<BR>
*	Descripcinn:	Contiene los datos referentes al formulario de peticinn del Reporte<BR>
*	Funciones de la pngina:	Almacenar temporalmente los datos del formulario de peticinn del reporte a generar <BR>
*	Entidades afectadas:	No aplica <BR>
*	Fecha de modificacinn:	20/07/2005	<BR>
*	@author Latined	<BR>
*	@version v 1.0	<BR>
*/

public class FiltroBeanReporteDocente implements Serializable, Cloneable{

   private Long institucion;
   private Long institucionDane;
   private Long sede;
   private Long jornada;
   private Long metodologia;
   private Long docente;
   private Long estructura;
   private String usuario;
   private Long vigencia;



/**
 * @return Returns the vigencia.
 */
public Long getVigencia() {
	return vigencia;
}



/**
 * @param vigencia The vigencia to set.
 */
public void setVigencia(Long vigencia) {
	this.vigencia = vigencia;
}



/**
 * @return Returns the docente.
 */
public Long getDocente() {
	return docente;
}



/**
 * @param docente The docente to set.
 */
public void setDocente(Long docente) {
	this.docente = docente;
}



/**
 * @return Returns the estructura.
 */
public Long getEstructura() {
	return estructura;
}



/**
 * @param estructura The estructura to set.
 */
public void setEstructura(Long estructura) {
	this.estructura = estructura;
}



/**
 * @return Returns the institucion.
 */
public Long getInstitucion() {
	return institucion;
}



/**
 * @param institucion The institucion to set.
 */
public void setInstitucion(Long institucion) {
	this.institucion = institucion;
}



/**
 * @return Returns the jornada.
 */
public Long getJornada() {
	return jornada;
}



/**
 * @param jornada The jornada to set.
 */
public void setJornada(Long jornada) {
	this.jornada = jornada;
}



/**
 * @return Returns the metodologia.
 */
public Long getMetodologia() {
	return metodologia;
}



/**
 * @param metodologia The metodologia to set.
 */
public void setMetodologia(Long metodologia) {
	this.metodologia = metodologia;
}



/**
 * @return Returns the sede.
 */
public Long getSede() {
	return sede;
}



/**
 * @param sede The sede to set.
 */
public void setSede(Long sede) {
	this.sede = sede;
}



/**
 * @return Returns the usuario.
 */
public String getUsuario() {
	return usuario;
}



/**
 * @param usuario The usuario to set.
 */
public void setUsuario(String usuario) {
	this.usuario = usuario;
}



/**
 *	Hace una copia del objeto mismo
 *	@return Object 
 */
	public Object clone(){
		Object o = null;
		try {
			o = super.clone();
		} catch(CloneNotSupportedException e) {
			System.err.println("MyObject can't clone");
		}
		return o;
    }



/**
 * @return Returns the institucionDane.
 */
public Long getInstitucionDane() {
	return institucionDane;
}



/**
 * @param institucionDane The institucionDane to set.
 */
public void setInstitucionDane(Long institucionDane) {
	this.institucionDane = institucionDane;
}

}