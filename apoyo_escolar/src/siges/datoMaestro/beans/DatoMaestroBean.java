package siges.datoMaestro.beans;

import java.util.*;
import java.sql.*;
//import sae.sql.*;
import siges.dao.*;

/**
*	Nombre: DatoMaestroBean.java	<BR>
*	Descripcinn:	Contiene los datos referentes al dato maestro que se quiere manipular <BR>
*	Funciones de la pngina:	Almacenar temporalmente los datos del dato maestro solicitado para luego ser manipulado por la <BR> respectiva pagina que lo procesarn <BR>
*	Entidades afectadas:	No aplica <BR>
*	Fecha de modificacinn:	20/07/2005	<BR>
*	@author Latined	<BR>
*	@version v 1.0	<BR>
*/

public class DatoMaestroBean{

	private String tabla;//nombre de la tabla en la base de datos
	private String campo[];//array de los campos de la tabla
	private String titulo;//titulo a mostrar en los formularios
	private String dato;//el numero que corresponde a cada dato maestro
	private String mensaje;//el numero que corresponde a cada dato maestro
/**
*	constructor de la clase
**/
	public DatoMaestroBean(){
		campo=new String[15];
		mensaje="";
	}

/**
*	inicializa todas las variables para un determinado dato maestro
*	@param	int numerodato
*	@param	Cursor cursor
*	@return boolean true=seleccion aprovada, false=numero fuera del rango
**/
	public boolean seleccionar(int numerodato,Cursor cursor){
		dato=String.valueOf(numerodato);

		switch (numerodato){
			case 1:  tabla="G_Constante "; titulo="Jornada";	break;
			case 2:  tabla="G_Constante "; titulo="Tipo de documento"; break;
			case 3:	 tabla="G_Constante "; titulo="Departamento";	break;
			case 4:	 tabla="G_Constante "; titulo="Nivel";	break;
			case 5:	 tabla="G_Constante "; titulo="Grupo";	break;
			case 6:	 tabla="G_TipoEspacio";titulo="Tipo de espacio"; break;
			case 8:	 tabla="G_TipoReconocimiento"; titulo="Tipo de reconocimiento"; break;
			case 9:  tabla="G_Constante"; titulo="Metodologna"; break;
			case 10: tabla="G_Constante ";titulo="Localidad";	break;
			case 11: tabla="G_Constante ";titulo="Tipo ocupante (espacio fnsico)";	break;
			case 12: tabla="G_Constante"; titulo="Especialidad";	break;
			case 13: tabla="Condicion"; 	titulo="Condicinn";	break;
			case 14: tabla="G_Constante"; titulo="Tipo de convivencia";	break;
			case 15: tabla="G_Constante"; titulo="Discapacidad del estudiante";	break;
			case 16: tabla="G_Constante"; titulo="Capacidad excepcional";	break;
			case 17: tabla="G_Constante"; titulo="Etnia";	break;
			case 18: tabla="G_Constante"; titulo="Motivos de ausencia";	break;
			case 19: tabla="G_Constante"; titulo="Tipos de programa para docentes";	break;
			case 21: tabla="G_Constante"; titulo="Municipio";	break;
			case 22: tabla="G_area";      titulo="área";	break;
			case 23: tabla="G_grado"; titulo="Grado";	break;
			case 24: tabla="G_Constante"; titulo="Calendario";	break;
			case 25: tabla="G_Constante"; titulo="Propiedad";	break;	
			case 26: tabla="G_Constante"; titulo="Discapacidad del colegio";	break;
			case 27: tabla="G_Nucleo";titulo="Nncleo"; break;
			case 28: tabla="Area";titulo="áreas"; break;
			case 29: tabla="G_asignatura";titulo="Asignatura";	break;
			case 30: tabla="Asignatura";titulo="Asignaturas"; break;
			case 31: tabla="Plan_Estudios";titulo="Plan de estudios "; break;
			case 32: tabla="Grado";titulo="Grado"; break;
			case 33: tabla="Grado_Asignatura";titulo="Asignaturas para los Grados"; break;
			case 34: tabla="Contenido";titulo="Contenidos para las Asignaturas"; break;
			case 35: tabla="Tema";titulo="Temas para los Contenidos de las Asignaturas"; break;
			case 36: tabla="Periodo";titulo="Periodo Escolar"; break;
			case 37: tabla="Logro";titulo="Logros"; break;
			case 38: tabla="Indicador_Logro";titulo="Indicadores de Logros"; break;
			case 39: tabla="Descriptor_Valorativo";titulo="Descriptores valorativos"; break;
			case 40: tabla="Tipo_Descriptor";titulo="Tipos de Descriptores valorativos"; break;
			case 41: tabla="Grupo";titulo="Grupos"; break;
			case 42: tabla="Escala_Valorativa";titulo="Escala valorativa"; break;
			case 43: tabla="G_Constante";titulo="Tipo de Escala valorativa"; break;
			case 44: tabla="G_Constante";titulo="Rango Tarifa"; break;
			case 45: tabla="G_Constante";titulo="Asociaciones privadas"; break;
			case 46: tabla="G_Constante";titulo="Idioma"; break;
			case 47: tabla="G_Constante";titulo="Cargo Gobierno escolar"; break;
			case 48: tabla="Metodologia";titulo="Metodolognas del colegio"; break;
			case 49: tabla="G_Constante";titulo="Tipos de Atencinn Especial"; break;
			case 50: tabla="Rot_Tipo_Receso";titulo="Tipos de Receso"; break;
			case 51: tabla="G_Constante";titulo="Tipos de Especialidad"; break;
			case 52: tabla="G_Constante";titulo="Tipos de Modalidad"; break;
			case 53: tabla="G_Constante";titulo="Tipos de nnfasis"; break;
			
			default: return false;
		}
		campo=cursor.getColumnas(tabla);
		if(campo==null)
			return false;
		return true;
	}
	
/**
 * asigna el campo dato
 *	@param String s
 */
	
	public void setDato(String s){
		this.dato=s;
	}

/**
 * retorna el campo dato
 *	@return String
 */
	public String getDato(){
		return dato !=null ? dato : "";
	}

/**
 * asigna el campo Tabla
 *	@param String s
 */
	public void setTabla(String s){
		this.tabla=s;
	}

/**
 * retorna el campo dato
 *	@return String
 */
	public String getTabla(){
		return tabla !=null ? tabla : "";
	}

/**
 * asigna el campo Campo
 *	@param String s
 *	@param int n
 */
	public void setCampo(String s,int n){
		this.campo[n]=s;
	}

/**
 * retorna el campo Campo
 *	@return String
 */
	public String getCampo(int n){
		return campo[n] !=null ? campo[n] : "";
	}

/**
 * asigna el campo titulo
 *	@param String s
 */
	public void setTitulo(String s){
		this.titulo=s;
	}

/**
 * retorna el campo titulo
 *	@return String
 */
	public String getTitulo(){
		return titulo !=null ? titulo : "";
	}
	
/**
*	Concatena el parametro en una variable que contiene los mensajes que se van a enviar a la vista
*	@param	String s
**/
	public void setMensaje(String s){		
		mensaje+=s;
	}	

/**
*	Retorna una variable que contiene los mensajes que se van a enviar a la vista
*	@return String	
**/
	public String getMensaje(){
		return mensaje;
	}	
}