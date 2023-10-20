package siges.datoMaestro.beans;

import java.io.*;

/**
*	Nombre:	ActualizarFiltro 
*	Descripcion:	Bean para almacenar los datos referentes al filtro de edicion de registro
*	Parametro de entrada:	no apllica
*	Parametro de salida:	no apllica
*	Funciones de la pagina:	Almacena los datos para la consulta de registros previa a la edicion de registros
*	Entidades afectadas:	no apllica
*	Fecha de modificacinn:	01/12/04
*	@author Pasantes UD
*	@version $v 1.2 $
*/
public class Encabezado implements Serializable{

	private String departamentoid;
	private String departamento;
	private String municipioid;
	private String municipio;
	private String localidadid;
	private String localidad;
	private String institucionid;
	private String institucion;

//departamento,sede,jornada,grado,grupo

/**
 * asigna el campo departamentoid
 *	@param String s
 */
	public void setDepartamentoid(String s){
		this.departamentoid=s;
	}	
/**
 * retorna el campo 
 *	@return String
 */
	public String getDepartamentoid(){
		return departamentoid !=null ? departamentoid : "";
	}

/**
 * asigna el campo departamento
 *	@param String s
 */
	public void setDepartamento(String s){
		this.departamento=s;
	}	
/**
 * retorna el campo departamento
 *	@return String
 */
	public String getDepartamento(){
		return departamento !=null ? departamento : "";
	}


/**
 * asigna el campo municipioid
 *	@param String s
 */
	public void setMunicipioid(String s){
		this.municipioid=s;
	}	
/**
 * retorna el campo 
 *	@return String
 */
	public String getMunicipioid(){
		return municipioid !=null ? municipioid : "";
	}

/**
 * asigna el campo municipio
 *	@param String s
 */
	public void setMunicipio(String s){
		this.municipio=s;
	}	
/**
 * retorna el campo municipio
 *	@return String
 */
	public String getMunicipio(){
		return municipio !=null ? municipio : "";
	}

/**
 * asigna el campo localidadid
 *	@param String s
 */
	public void setLocalidadid(String s){
		this.localidadid=s;
	}	
/**
 * retorna el campo localidadid
 *	@return String
 */
	public String getLocalidadid(){
		return localidadid !=null ? localidadid : "";
	}

/**
 * asigna el campo localidad
 *	@param String s
 */
	public void setLocalidad(String s){
		this.localidad=s;
	}	
/**
 * retorna el campo localidad
 *	@return String
 */
	public String getLocalidad(){
		return localidad !=null ? localidad : "";
	}

/**
 * asigna el campo institucionid
 *	@param String s
 */
	public void setInstitucionid(String s){
		this.institucionid=s;
	}	
/**
 * retorna el campo institucionid
 *	@return String
 */
	public String getInstitucionid(){
		return institucionid !=null ? institucionid : "";
	}

/**
 * asigna el campo institucion
 *	@param String s
 */
	public void setInstitucion(String s){
		this.institucion=s;
	}	
/**
 * retorna el campo institucion
 *	@return String
 */
	public String getInstitucion(){
		return institucion !=null ? institucion : "";
	}

/**
 *	Retorna "selected en el caso de que el parametro s coincida con el almacenado en el campo respectivo"
 *	@param String s
 *	@param int n
 *	@return String 
 */
	public String seleccionar(String s,int n){
		switch(n){
			/*
			case 1:
				if(nivel!=null){
					if(nivel.equals(s))
						return "selected";
				}
			break;
			case 2:			
				if(ano!=null){
					if(ano.equals(s))
						return "selected";
				}
			break;
			case 3:			
				if(orden!=null){
					if(orden.equals(s))
						return "selected";
				}
			break;
			*/
		}
		return "";
	}		
}