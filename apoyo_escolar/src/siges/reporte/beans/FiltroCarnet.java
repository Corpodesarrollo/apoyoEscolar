package siges.reporte.beans;

import java.io.Serializable;

/**
*	Nombre:	Filtro
*	Descripcion:	Datos de informacion basica del egresado
*	Parametro de entrada:	no aplica
*	Parametro de salida:	no aplica
*	Funciones de la pagina:	Almacenar los datos del egresado para un nuevo registro o para editar
*	Entidades afectadas:	no aplica
*	Fecha de modificacinn:	01/12/04
*	@author Pasantes UD
*	@version $v 1.2 $
*/

public class FiltroCarnet implements Serializable, Cloneable{

   private String sede;//1
   private String jornada;//2
   private String grado;//3
   private String grupo;//4
   private String id;//5
   private String nombre1;//6
   private String nombre2;//7
   private String apellido1;//8
   private String apellido2;//9
   private String[] id2;//9
   private String orden;//10

/**
 * asigna el campo sede
 *	@param String s
 */
	public void setSede(String s){
		this.sede=s;
	}	

/**
 * retorna el campo sede 
 *	@return String
 */
	public String getSede(){
		return sede !=null ? sede : "";
	}

/**
 * asigna el campo jornada
 *	@param String s
 */
	public void setJornada(String s){
		this.jornada=s;
	}	

/**
 * retorna el campo jornada
 *	@return String
 */
	public String getJornada(){
		return jornada !=null ? jornada : "";
	}

/**
 * asigna el campo 
 *	@param String s
 */
	public void setGrado(String s){
		this.grado=s;
	}	

/**
 * retorna el campo 
 *	@return String
 */
	public String getGrado(){
		return grado !=null ? grado : "";
	}

/**
 * asigna el campo grupo
 *	@param String s
 */
	public void setGrupo(String s){
		this.grupo=s;
	}	

/**
 * retorna el campo grupo
 *	@return String
 */
	public String getGrupo(){
		return grupo !=null ? grupo : "";
	}

/**
 * asigna el campo id
 *	@param String s
 */
	public void setId(String s){
		this.id=s;
	}	

/**
 * retorna el campo id
 *	@return String
 */
	public String getId(){
		return id !=null ? id : "";
	}

/**
 * asigna el campo nombre1
 *	@param String s
 */
	public void setNombre1(String s){
		this.nombre1=s;
	}	

/**
 * retorna el campo nombre1
 *	@return String
 */
	public String getNombre1(){
		return nombre1 !=null ? nombre1 : "";
	}

/**
 * asigna el campo nombre2
 *	@param String s
 */
	public void setNombre2(String s){
		this.nombre2=s;
	}	

/**
 * retorna el campo nombre2
 *	@return String
 */
	public String getNombre2(){
		return nombre2 !=null ? nombre2 : "";
	}

/**
 * asigna el campo apellido1
 *	@param String s
 */
	public void setApellido1(String s){
		this.apellido1=s;
	}	

/**
 * retorna el campo apellido1
 *	@return String
 */
	public String getApellido1(){
		return apellido1 !=null ? apellido1 : "";
	}

/**
 * asigna el campo apellido2
 *	@param String s
 */
	public void setApellido2(String s){
		this.apellido2=s;
	}	

/**
 * retorna el campo 
 *	@return String
 */
	public String getApellido2(){
		return apellido2 !=null ? apellido2 : "";
	}

/**
 * asigna el campo orden
 *	@param String s
 */
	public void setOrden(String s){
		this.orden=s;
	}	

/**
 * retorna el campo orden
 *	@return String
 */
	public String getOrden(){
		return orden !=null ? orden : "";
	}

/**
 * asigna el campo id2
 *	@param String s
 */
	public void setId2(String [] s){
		this.id2=s;
	}	

/**
 * retorna el campo id2
 *	@return String
 */
	public String[] getId2(){
		return id2;
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