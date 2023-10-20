package siges.comision;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import siges.comision.beans.*;
import siges.dao.*;
import siges.login.beans.*;

/**
*	Nombre:	ControllerNuevoSave<br>
*	Descripcinn:	Guarda información de Comisinn<br>
*	Funciones de la pngina:	Guarda o actualiza información del formulario de Comisinn de Evaluacinn<br>
*	Entidades afectadas:	<br>
*	Fecha de modificacinn:	20/07/2005 <br>
*	@author Latined <br>
*	@version v 1.0 <br>
*/
public class ControllerNuevoSave extends HttpServlet{	
	private Login login;
	private Cursor cursor;//objeto que maneja las sentencias sql
	private Util util;
	private String mensaje;//mensaje en caso de error
	private boolean band;
	private boolean err;//variable que inidica si hay o no errores en la validacion de los datos del formulario
	private int tipo;
	private String sig,sig1;//nombre de la pagina a la que ira despues de ejecutar los comandos de esta
	private String ant;//pagina a la que ira en caso de que no se pueda procesar esta pagina
	private String er;//nombre de la pagina a la que ira si hay errores
	private String home;//nombre de la pagina a la que ira si hay errores
	private HttpSession session;

/**
*	Funcinn: Recibe la peticion por el metodo Post de HTTP
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
	public String process (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		session = request.getSession();
		String boton;
		String buscar=null;
		String respuesta="";
		sig="/comision/NuevoResultado.jsp";
		sig1="/comision/FiltroResultado.jsp";
		ant="/comision/ControllerNuevoEdit.do";
		er="/error.jsp";
		home="/bienvenida.jsp";
		err=false;
		band=true;
		mensaje=null;
		respuesta="La información fue ingresada satisfactoriamente ";
		cursor=new Cursor();
		util=new Util(cursor);		
		
//		if(!cursor.abrir(1)){
//			setMensaje("No se pudo abrir la conexinn a la base de datos");
//			request.setAttribute("mensaje",getMensaje());
//			return er;
//		}
		boton=(request.getParameter("cmd")!=null)?request.getParameter("cmd"):new String("Cancelar");
		//System.out.println("cupido: "+boton);
		if(boton.equals("Cancelar")){
			return home;
    }
		if(boton.equals("Guardar")){
			if(request.getParameter("tipo")==null || request.getParameter("tipo").equals("")){
				setMensaje("Acceso denegado no hay una ficha definida");
				request.setAttribute("mensaje",getMensaje());
				return er;
			}
			tipo=Integer.parseInt((String)request.getParameter("tipo"));			
			switch(tipo){
				case 1:
					setMensaje("La información fue ingresada satisfactoriamente");
					request.setAttribute("mensaje",getMensaje());
					return (ant+="?tipo="+tipo);
				//return (sig+="?tipo="+tipo+"?ext2="+sig);
			}			
		}
	return er;
  }

/**
*	Funcinn: Recibe la peticion por el metodo get de HTTP
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doPost(request,response);//redirecciona la peticion a doPost
	}

/**
*	Funcinn: Recibe la peticion por el metodo Post de HTTP
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
	public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
  	String s=process(request, response);
	  if(s!=null && !s.equals(""))
	  	ir(1,s,request,response);
	}

/**
*	Funcinn: Redirige el control a otro servlet
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
*	Funcinn: Concatena el parametro en una variable que contiene los mensajes que se van a enviar a la vista
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
*	Funcinn: Retorna una variable que contiene los mensajes que se van a enviar a la vista
*	@return String	
**/
	public String getMensaje(){
		return mensaje;
	}
}