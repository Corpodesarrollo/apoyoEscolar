package siges.comision;

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
import siges.dao.*;
import siges.comision.beans.*;
import siges.login.beans.*;

/**
*	Nombre:	ControllerFiltroEdit<br>
*	Descripcinn:	Controla los filtros del formulario <br>
*	Funciones de la pngina:	Procesar la peticion y enviar al filtro<br>
*	Entidades afectadas:	Tablas maestras en modo de solo lectura<br>
*	Fecha de modificacinn:	20/07/2005 <br>
*	@author Latined <br>
*	@version v 1.0 <br>
*/
public class ControllerFiltroEdit extends HttpServlet{
	private String mensaje;//mensaje en caso de error
	private String buscar;
	private boolean err;//variable que inidica si hay o no errores en la validacion de los datos del formulario
	private Cursor cursor;//objeto que maneja las sentencias sql
	private Util util;//
	private Login login;
	private HttpSession session;
	private ResourceBundle rb;

/**
*	Funcinn: Procesa la peticion HTTP
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
	public String process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		session = request.getSession();
		rb=ResourceBundle.getBundle("preparedstatements");
		String sig;
		String ant;
		String er;
		String home;
		int tipo;
		sig="/comision/Filtro.jsp";
		ant="/index.jsp";
		er="/error.jsp";
		home="/bienvenida.jsp";
		cursor=new Cursor();
		err=false;
		mensaje=null;
//		if(!cursor.abrir(1)){
//			setMensaje("No se pudo abrir la conexinn a la base de datos");
//			request.setAttribute("mensaje",mensaje);
//			return er;
//		}
		util=new Util(cursor);
			if(!asignarBeans(request)){
				setMensaje("Error capturando datos de sesinn para el usuario");
				request.setAttribute("mensaje",mensaje);
				return er;
			}
			try{
				session.removeAttribute("editar");
		    Collection list;
			  Object[] o;
				String s;
				int z;
		    z=0;
		    list = new ArrayList();
		    o=new Object[2];
				o[z++]=new Integer(java.sql.Types.INTEGER);
				o[z++]=login.getInstId();
				list.add(o);
				if(session.getAttribute("filtroSedeF")==null)
				 	session.setAttribute("filtroSedeF",util.getFiltro(rb.getString("filtroSedeInstitucion"),list));
				if(session.getAttribute("filtroJornadaF")==null)
				 	session.setAttribute("filtroJornadaF",util.getFiltro(rb.getString("filtroSedeJornadaInstitucion"),list));
				if(session.getAttribute("filtroGradoF")==null)
				 	session.setAttribute("filtroGradoF",util.getFiltro(rb.getString("filtroSedeJornadaGradoInstitucion"),list));
				if(session.getAttribute("filtroGrupoF")==null)
				 	session.setAttribute("filtroGrupoF",util.getFiltro(rb.getString("filtroSedeJornadaGradoGrupoInstitucion"),list));
				if(session.getAttribute("filtroMetodologiaF")==null)
					session.setAttribute("filtroMetodologiaF",util.getFiltro(rb.getString("listaMetodologiaInstitucion"),list));
			}catch(Throwable th){
				System.out.println("Error "+th);
				throw new	ServletException(th);
			}
		return sig;
	}

/**
 *	Funcinn: Inserta los valores por defecto para el bean de información bnsica con respecto a la informacion de la institucinn educativa
 *	@param HttpServletRequest request
 *	@return boolean 
 */
	public boolean asignarBeans(HttpServletRequest request)throws ServletException, IOException{ 
		login=(Login)session.getAttribute("login");
		return true;
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
		if (!err){
			err=true;
			mensaje="VERIFIQUE LA SIGUIENTE INFORMACION: \n\n";
		}
		mensaje+="  - "+s+"\n";
	}
}