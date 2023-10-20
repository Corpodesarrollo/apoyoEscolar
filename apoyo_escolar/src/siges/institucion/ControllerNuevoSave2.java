package siges.institucion;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletConfig;
import java.io.IOException;
import java.io.*;
import java.net.*;
import java.util.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.util.ResourceBundle;
import siges.institucion.beans.*;
import siges.dao.*;

/**
*	nombre:	
*	Descripcion:	Controla la peticion de insertar un nuevo registro 
*	Parametro de entrada:	HttpServletRequest request, HttpServletResponse response
*	Parametro de salida:	HttpServletRequest request, HttpServletResponse response
*	Funciones de la pagina:	Valida, inserta un nuevo registro y sede el control a la pagina de resultados 
*	Entidades afectadas:	Tablas de información del egresado 
*	Fecha de modificacinn:	01/12/04
*	@author Pasantes UD
*	@version $v 1.2 $
*/
public class ControllerNuevoSave2 extends HttpServlet{	
	private Institucion institucion,institucion2;
	private Jornada jornada,jornada2;
	private Sede sede,sede2;
	private Espacio espacio,espacio2;
	private Nivel nivel,nivel2;
	private Cursor cursor;//objeto que maneja las sentencias sql
	private Util util;
	private String mensaje;//mensaje en caso de error
	private boolean band;
	private boolean err;//variable que inidica si hay o no errores en la validacion de los datos del formulario
	private int tipo;
	private String sig;//nombre de la pagina a la que ira despues de ejecutar los comandos de esta
	private String ant;//pagina a la que ira en caso de que no se pueda procesar esta pagina
	private String er;//nombre de la pagina a la que ira si hay errores
	private String home;//nombre de la pagina a la que ira si hay errores
	private String nombre[];
	private HttpSession session;

/**
*	Recibe la peticion por el metodo Post de HTTP
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
	public String process (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		session = request.getSession();
    nombre=new String[2];
		String buscar=null;		
		String respuesta="La información fue ingresada satisfactoriamente ";
		String fileName="";
		String nombreCampo,valorCampo;
    String valor[]=new String[3];
    Iterator i;
    List fileItems;
    File fichero=null;
    FileItem item = null;

		ant="/institucion/ControllerNuevoEdit.do";//pagina a la que se dara el control si algo falla
		er="/error.jsp";
		home="/bienvenida.jsp";
		err=false;
		band=true;
		mensaje=null;
		cursor=new Cursor();
		util=new Util(cursor);
		tipo=4;
		
//		if(!cursor.abrir(1)){
//			setMensaje("No se pudo abrir la conexinn a la base de datos");
//			request.setAttribute("mensaje",getMensaje());
//			return er;
//		}
		asignarBeans(request);
		if(institucion2==null || institucion2.getInscodigo().equals("")){
			setMensaje("No se puede insertar los simbolos ya que la ficha de informacion bnsica no se ha guardado");
			request.setAttribute("mensaje",getMensaje());
			return (ant+="?tipo2="+tipo);
		}
		ResourceBundle rb=ResourceBundle.getBundle("path",request.getLocale());
		String pathSubirDefault=Ruta.get(getServletContext(),rb.getString("path.subirDefault"));
		String pathSubirBandera=Ruta.get(getServletContext(),rb.getString("path.subirBandera"));
		String pathSubirEscudo=Ruta.get(getServletContext(),rb.getString("path.subirEscudo"));
		String pathTemporal=Ruta.get(getServletContext(),rb.getString("path.temporal"));
		try{
//			DiskFileUpload fu = new DiskFileUpload();
//			fileItems = fu.parseRequest(request,4096,1024*1024,pathTemporal);
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			fileItems = upload.parseRequest(request);
			/*TRAER LOS CAMPOS QUE NO SON FILE*/
			i = fileItems.iterator();
			while (i.hasNext()){
				fileName=null;
				item = (FileItem)i.next();
  			if(item.isFormField()){
				  nombreCampo = item.getFieldName();
    			valorCampo = item.getString();
    			if(nombreCampo.equals("inslema")){
    				valor[0]=valorCampo;
    			}
    			if(nombreCampo.equals("inshimno")){
    				valor[1]=valorCampo;
    			}
    			if(nombreCampo.equals("inshistoria")){
    				valor[2]=valorCampo;
    			}
  			}
			}
			/*VALIDAR TIPO DE LOS ARCHIVOS*/
      i = fileItems.iterator();
      if(!validarTipo(i)){
				request.setAttribute("mensaje",getMensaje());
				return (ant+="?tipo2="+tipo);
      }
      /*SUBIR LOS ARCHIVOS*/
      i = fileItems.iterator();
			while (i.hasNext()){
				fileName=null;
				item = (FileItem)i.next();
  			if(!item.isFormField()){
				  fileName = item.getName();
					if(fileName.trim().compareTo("")!=0){
    				if(item.getFieldName().equals("insescudo")){
    					nombre[0]=getNombre(item.getContentType());
    					fichero = new  File(pathSubirEscudo+nombre[0]);
    				}
    				if(item.getFieldName().equals("insbandera")){
    					nombre[1]=getNombre(item.getContentType());
    					fichero = new  File(pathSubirBandera+nombre[1]);
    				}
						item.write(fichero);						
  				}
  			}
			}
		}
		catch(FileUploadBase.InvalidContentTypeException e){
			setMensaje("El archivo no es del tipo solicitado :"+e.toString());
			request.setAttribute("mensaje",getMensaje());
			return (ant+="?tipo2="+tipo);
		}
		catch(FileUploadBase.SizeLimitExceededException e) {
			setMensaje("El archivo se pasa del tamano permitido :"+e.toString());
			request.setAttribute("mensaje",getMensaje());
			return (ant+="?tipo2="+tipo);
		}
		catch(FileUploadBase.UnknownSizeException e) {
			setMensaje("El archivo no tiene un tamano especificado :"+e.toString());
			request.setAttribute("mensaje",getMensaje());
			return (ant+="?tipo2="+tipo);
		}
		catch(FileNotFoundException e) {
			setMensaje("No hay un archivo especificado :"+e.toString());
			request.setAttribute("mensaje",getMensaje());
			return (ant+="?tipo2="+tipo);
		}
		catch(FileUploadException e) {
			setMensaje("Error tratando de subir el archivo: "+e.toString());
			request.setAttribute("mensaje",getMensaje());
			return (ant+="?tipo2="+tipo);
		}
		catch(Exception e) {
			setMensaje("Error de Aplicacinn: "+ e.toString());
			request.setAttribute("mensaje",getMensaje());
			return (ant+="?tipo2="+tipo);
		}finally{
		  if(cursor!=null)cursor.cerrar();if(util!=null)util.cerrar();
		}
		if(nombre[0]!=null)
			institucion.setInsescudo(nombre[0]);
		if(nombre[1]!=null)
			institucion.setInsbandera(nombre[1]);
		institucion.setInshimno(valor[1]);
		institucion.setInslema(valor[0]);
		institucion.setInshistoria(valor[2]);
//		if(!util.actualizar(institucion)){
//			setMensaje(util.getMensaje());
//			restaurarBeans(request,1);
//			return (ant+="?tipo2="+tipo);
//		}
		recargarBeans(request,1);
		setMensaje(respuesta);
		request.setAttribute("mensaje",getMensaje());
		return (ant+="?tipo2="+tipo);
  }

  public boolean validarTipo(Iterator it){
    boolean band=true;
    boolean archivo=false;
    String fileName;
    String a;
    FileItem item;
    while (it.hasNext()){
  	  fileName=null;
  		item = (FileItem)it.next();
  		if(!item.isFormField()){
			  fileName = item.getName();
				if(fileName.trim().compareTo("")!=0){
				  archivo=true;
				  a=item.getContentType();
  			  if(!a.equals("image/gif") && !a.equals("image/pjpeg") && !a.equals("image/jpeg")){
      		  a="Error con el archivo . posible problema : ";
		        a+="1. No es un archivo de imagen gif.";
		        a+="2. El archivo esta abierto(cierrelo e intente de nuevo)";
		        setMensaje(a);
		        band=false;
		      }
		    }
		  }		
		}
		return band;
  }

	public String getNombre(String a){
		if(a.equals("image/gif")) return institucion2.getInscodigo()+".gif";
		if(a.equals("image/pjpeg") || a.equals("image/jpeg")) return institucion2.getInscodigo()+".jpg";
		return "notienenombre.jpg";
	}

/**
 *	Asigna a variables locales el contenido de los beans de session
 *	@param HttpServletRequest request
 *	@return boolean  true=asigno todos los beans;	false=no existe alguno de los beans en la session
 */
	public void asignarBeans (HttpServletRequest request) throws ServletException, IOException{
		institucion=(Institucion)session.getAttribute("nuevoInstitucion");
		if(institucion.getEstado().equals("1"))
			institucion2=(Institucion)session.getAttribute("nuevoInstitucion2");
		jornada=(Jornada)session.getAttribute("nuevoJornada");
		if(jornada.getEstado().equals("1"))
			jornada2=(Jornada)session.getAttribute("nuevoJornada2");
		sede=(Sede)session.getAttribute("nuevoSede");
		if(sede.getEstado().equals("1"))
			sede2=(Sede)session.getAttribute("nuevoSede2");
		espacio=(Espacio)session.getAttribute("nuevoEspacio");
		if(espacio.getEstado().equals("1"))
			espacio2=(Espacio)session.getAttribute("nuevoEspacio2");
		nivel=(Nivel)session.getAttribute("nuevoNivel");
		if(nivel.getEstado().equals("1"))
			nivel2=(Nivel)session.getAttribute("nuevoNivel2");		
	}

/**
 *	Referencia al bean del usuario con la información proporcionada por el bean de respaldo
 *	@param int n
 *	@param HttpServletRequest request
 */
	public void restaurarBeans(HttpServletRequest request,int n) throws ServletException, IOException{
		switch(n){
			case 1: 
				session.setAttribute("nuevoInstitucion",(Institucion)institucion2.clone());
			break;
			case 12: 
				session.setAttribute("nuevoJornada",(Jornada)jornada2.clone());
			break;
			case 2: 
				session.setAttribute("nuevoSede",(Sede)sede2.clone());
			break;
			case 3: 
				session.setAttribute("nuevoEspacio",(Espacio)espacio2.clone());
			break;
			case 13: 
				session.setAttribute("nuevoNivel",(Nivel)nivel2.clone());
			break;
		}
	}
	
/**
 *	Referencia al bean de respaldo con la nueva información proporcionada por el bean modificado por el usuario
 *	@param int n
 *	@param HttpServletRequest request
 */
	public void recargarBeans(HttpServletRequest request,int n) throws ServletException, IOException{
		switch(n){
			case 1:
				session.setAttribute("nuevoInstitucion2",(Institucion)institucion.clone());
			break;
			case 12:
				session.setAttribute("nuevoJornada2",(Jornada)jornada.clone());
			break;
			case 2:
				session.setAttribute("nuevoSede2",(Sede)sede.clone());
			break;
			case 3:
				session.setAttribute("nuevoEspacio2",(Espacio)espacio.clone());
			break;
			case 13:
				session.setAttribute("nuevoNivel2",(Nivel)nivel.clone());
			break;
		}
	}

/**
*	Recibe la peticion por el metodo get de HTTP
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doPost(request,response);//redirecciona la peticion a doPost
	}

/**
*	Recibe la peticion por el metodo Post de HTTP
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
	public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
  	String s=process(request, response);
	  if(s!=null && !s.equals(""))
	  	ir(1,s,request,response);
	}

/**
*	Redirige el control a otro servlet
*	@param	int a: 1=redirigir como 'include', 2=redirigir como 'forward'
*	@param	String s
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
	public void ir(int a,String s,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		if(cursor!=null)cursor.cerrar();
		RequestDispatcher rd=getServletContext().getRequestDispatcher(s);
		if(a==1) rd.include(request, response);
		else rd.forward(request, response);
	}

/**
*	Concatena el parametro en una variable que contiene los mensajes que se van a enviar a la vista
*	@param	String s
**/
	public void setMensaje(String s){
		band=false;
		if (!err){
			err=true;
			mensaje="VERIFIQUE LA SIGUIENTE INFORMACION: \n\n";
		}	
		mensaje+="  - "+s+"\n";
	}

/**
*	Retorna una variable que contiene los mensajes que se van a enviar a la vista
*	@return String	
**/
	public String getMensaje(){
		return mensaje;
	}
}