package siges.grupo; 

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import java.sql.PreparedStatement;
import siges.exceptions.InternalErrorException;
import siges.grupo.dao.GrupoDAO;
import siges.dao.*;
import siges.login.beans.*;
import siges.util.Properties;
import siges.grupo.beans.*;

/**
*	Nombre:	
*	Descripcion:	Controla la peticion de insertar un nuevo registro 
*	Parametro de entrada:	HttpServletRequest request, HttpServletResponse response
*	Parametro de salida:	HttpServletRequest request, HttpServletResponse response
*	Funciones de la pagina:	Valida, inserta un nuevo registro y sede el control a la pagina de resultados 
*	Entidades afectadas:	Tablas de información del egresado 
*	Fecha de modificacinn:	01/12/04
*	@author Latined
*	@version $v 1.2 $
*/
public class ControllerGrupoNuevoSave extends HttpServlet{		
	private Cursor cursor;//objeto que maneja las sentencias sql
	private Util util;
	private Login login;
	private String mensaje;//mensaje en caso de error
	private boolean band;
	private boolean err;//variable que inidica si hay o no errores en la validacion de los datos del formulario
	private int tipo;
	private String sig;//nombre de la pagina a la que ira despues de ejecutar los comandos de esta
	private String sig1;//nombre de la pagina a la que ira despues de ejecutar los comandos de esta
	private String sig2;//nombre de la pagina a la que ira despues de ejecutar los comandos de esta
	private String ant;//pagina a la que ira en caso de que no se pueda procesar esta pagina
	private String er;//nombre de la pagina a la que ira si hay errores
	private String home;
	private GrupoDAO grupoDAO;
	private ResourceBundle rb; 
	private PreparedStatement pst=null;
	private HttpSession session;


/**
*	Recibe la peticion por el metodo Post de HTTP
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
	public String process (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	  rb=ResourceBundle.getBundle("grupo");
	  session=request.getSession();
	  String boton;
		String buscar=null;
		String respuesta="";
/*		
		sig=getServletConfig().getInitParameter("sig");
		sig1=getServletConfig().getInitParameter("sig1");
		sig2=getServletConfig().getInitParameter("sig2");
		ant=getServletConfig().getInitParameter("ant");
*/
		sig="/grupo/ControllerGrupoFiltroSave.do";
		sig1="/grupo/ControllerGrupoNuevoEdit.do";
		sig2="/grupo/ControllerGrupoFiltroSave.do";
		ant="/grupo/ControllerGrupoFiltroSave.do";	
		er=getServletContext().getInitParameter("error");
		home=getServletContext().getInitParameter("home");
		
		err=false;
		band=true;
		mensaje=null;
		respuesta="La información fue actualizada satisfactoriamente ";
		cursor=new Cursor();
		util=new Util(cursor);
		grupoDAO=new GrupoDAO(cursor);		
		GrupoBean grupo=null,grupo2=null;
		
		try{		    
		  boton=(request.getParameter("cmd")!=null)?request.getParameter("cmd"):new String("Cancelar");
			
		  if(boton.equals("Cancelar")){
				borrarBeans(request);
				return home;
	    }
		  
		  login=(Login)request.getSession().getAttribute("login");
			grupo=(GrupoBean)request.getSession().getAttribute("newgroup");
			grupo2=(GrupoBean)request.getSession().getAttribute("newgroup2");
			
			if(boton.equals("Guardar")){
		   		if(request.getParameter("tipo")==null || ((String)request.getParameter("tipo")).equals(""))
		   		   tipo=1;	        		
		   		else
		   			tipo=Integer.parseInt((String)request.getParameter("tipo"));
				  
		   		if(tipo==1){
						actualizarGrupo(request,grupo,grupo2);
						request.setAttribute("mensaje",getMensaje());
		   		 }
		   		boton="";
		   		return sig2; 
					}
		}catch(Exception e){
		    e.printStackTrace();
				throw new	ServletException(e);
			}finally{
			  if(cursor!=null)cursor.cerrar();if(util!=null)util.cerrar();if(grupoDAO!=null)grupoDAO.cerrar();			  
			}
		return sig;			
  }


/**
 *	Actualiza Grupos de la institucinn
 *	@param HttpServletRequest request
 *	@return boolean 
 */

	public void actualizarGrupo(HttpServletRequest request,GrupoBean grupo,GrupoBean grupo2) throws ServletException, IOException{
	  System.out.println("nnnnnnnnnACTUALIZAR GRUPO!!!!!!!!!!!");  
			if(compararFichasGrupo(grupo,grupo2)){
					setMensaje("La ficha seleccionada para guardar cambios no ha sido modificada");
					request.getSession().removeAttribute("newgroup");
					request.getSession().removeAttribute("newgroup2");
					return;
				}
			if(!grupoDAO.actualizar(grupo)){
					setMensaje(grupoDAO.getMensaje());
					restaurarBeansGrupo(request,grupo2);
					return;
			}
			setMensaje("La información fue actualizada satisfactoriamente");
			request.getSession().removeAttribute("newgroup");
			request.getSession().removeAttribute("newgroup2");
		}

/**
 * Elimina del contexto de la sesion los beans de informacion del usuario
 *	@param HttpServletRequest request
 */
	public void borrarBeans(HttpServletRequest request) throws ServletException, IOException{
	    request.getSession().removeAttribute("newgroup");
	    session.removeAttribute("newgroup");
	}


	
/**
 *	Busca en la base de datos un registro que coincida con el id ingresado por el usuario
 *	@return boolean 
 */
	public boolean compararFichasGrupo(GrupoBean grupo,GrupoBean grupo2){
			return grupoDAO.compararBeans(grupo,grupo2);
	}

	
	public void restaurarBeansGrupo(HttpServletRequest request,GrupoBean grupo2) throws ServletException, IOException{
			request.getSession().setAttribute("newgroup",(GrupoBean)grupo2.clone());
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
			mensaje="VERIFIQUE LA SIGUIENTE información: \n\n";
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