package articulacion.repHorariosArt.vo;

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
   private Long filAnoVigenciaArt;
   private Long filPerVigenciaArt;
   private Long filComponenteArt;



   /**
	 * @return Returns the filAnhoVigencia.
	 */
	public Long getFilAnoVigenciaArt() {
		return filAnoVigenciaArt;
	}
	/**
	 * @param filAnhoVigencia The filAnhoVigencia to set.
	 */
	public void setFilAnoVigenciaArt(Long filAnoVigenciaArt) {
		this.filAnoVigenciaArt = filAnoVigenciaArt;
	}   
  
	/**
	 * @return Returns the filComponente.
	 */
	public Long getFilComponenteArt() {
		return filComponenteArt;
	}
	/**
	 * @param filComponenteArt The filComponenteArt to set.
	 */
	public void setFilComponenteArt(Long filComponenteArt) {
		this.filComponenteArt = filComponenteArt;
	} 
	/**
	 * @return Returns the filPerVigencia.
	 */
	public Long getFilPerVigenciaArt() {
		return filPerVigenciaArt;
	}
	/**
	 * @param filPerVigenciaArt The filPerVigenciaArt to set.
	 */
	public void setFilPerVigenciaArt(Long filPerVigenciaArt) {
		this.filPerVigenciaArt = filPerVigenciaArt;
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