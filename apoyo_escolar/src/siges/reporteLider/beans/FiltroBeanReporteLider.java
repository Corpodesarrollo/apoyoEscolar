package siges.reporteLider.beans;

import java.io.*;



/**
*	Nombre: FiltroBeanReporteLider.java	<BR>
*	Descripcinn:	Contiene los datos referentes al formulario de peticinn del Reporte<BR>
*	Funciones de la pngina:	Almacenar temporalmente los datos del formulario de peticinn del reporte a generar <BR>
*	Entidades afectadas:	No aplica <BR>
*	Fecha de modificacinn:	20/07/2005	<BR>
*	@author Latined	<BR>
*	@version v 1.0	<BR>
*/

public class FiltroBeanReporteLider implements Serializable, Cloneable{

   private Long localidad;
   private Long institucion; 
   private Long institucionDane;
   private Long sede;
   private Long jornada;
   private Long tipoLider;
   private Long vigencia;
   private String usuario;
   private String estadousu;
   
   

/**
 * @return Returns the estadousu.
 */
public String getEstadousu() {
	return estadousu;
}





/**
 * @param estadousu The estadousu to set.
 */
public void setEstadousu(String estadousu) {
	this.estadousu = estadousu;
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





/**
 * @return Returns the localidad.
 */
public Long getLocalidad() {
	return localidad;
}





/**
 * @param localidad The localidad to set.
 */
public void setLocalidad(Long localidad) {
	this.localidad = localidad;
}





/**
 * @return Returns the tipoLider.
 */
public Long getTipoLider() {
	return tipoLider;
}





/**
 * @param tipoLider The tipoLider to set.
 */
public void setTipoLider(Long tipoLider) {
	this.tipoLider = tipoLider;
}

}