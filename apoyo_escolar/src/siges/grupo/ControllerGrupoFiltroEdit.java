package siges.grupo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import siges.grupo.beans.*;
import siges.dao.*;
import siges.login.beans.Login;
;

/**
*	Nombre:	ControllerGrupoFiltroEdit<BR>
*	Descripcinn:	Controla la vista del formulario de filtro de grupos	<BR>
*	Funciones de la pngina:	Poner los dartos del formulario y redirigir el control a la vista	<BR>
*	Entidades afectadas:	No aplica	<BR>
*	Fecha de modificacinn:	25/01/2006	<BR>
*	@author Latined	<BR>
*	@version v 1.0	<BR>
*/

public class ControllerGrupoFiltroEdit extends HttpServlet{
	private String mensaje;
	private String buscar;
	private boolean err;
	private Cursor cursor;
	private Util util;
	private HttpSession session;
	private ResourceBundle rb;
	private Collection list;
	private Object[] o;
	private Login login;
	private Integer entero=new Integer(java.sql.Types.INTEGER);
	private FiltroBeanGrupos filtro;
/**
*	Procesa la peticion HTTP
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
	public String process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		session = request.getSession();
		rb=ResourceBundle.getBundle("preparedstatementsgrupos");

		String sig;
		String ant;
		String er;
		String home;
		int tipo;
		sig="/grupo/FiltroAdminGrupos.jsp";
		ant="/index.jsp";
		er="/error.jsp";
		home="/bienvenida.jsp";
		err=false;
		mensaje=null;
		
	 try{
		cursor=new Cursor();		
		util=new Util(cursor);
		
		if(request.getParameter("tipo")==null || ((String)request.getParameter("tipo")).equals("")){
			session.removeAttribute("filtrogroup");
			tipo=1;
		}else
			tipo=Integer.parseInt((String)request.getParameter("tipo"));
		if(!asignarBeans(request)){
			setMensaje("Error capturando datos de sesinn para el usuario");
			request.setAttribute("mensaje",mensaje);
			return er;
		}	
		if(tipo==1)grupos(request);
		if(err){
			request.setAttribute("mensaje",mensaje);
			return er;
		}			
		return sig;
		
		}catch(Exception e){e.printStackTrace();
		 throw new	ServletException(e);
		}finally{
			  if(cursor!=null)cursor.cerrar();if(util!=null)util.cerrar();
		}		
		
	}	

	
	public void grupos(HttpServletRequest request) throws ServletException, IOException{

		try{
	    borrarBeansGrupos(request);			
			System.out.println("*********////*********nENTRn POR GRUPOS!**********//**********");
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
			if(request.getAttribute("filtroSedeF")==null)
			   request.setAttribute("filtroSedeF",util.getFiltro(rb.getString("filtroSedeInstitucion"),list));
			if(request.getAttribute("filtroJornadaF")==null)
			   request.setAttribute("filtroJornadaF",util.getFiltro(rb.getString("filtroSedeJornadaInstitucion"),list));
			if(request.getAttribute("filtroMetodologiaF")==null)
			   request.setAttribute("filtroMetodologiaF",util.getFiltro(rb.getString("listaMetodologiaInstitucion"),list));
			  Collection cc=util.getFiltro(rb.getString("filtroSedeJornadaGradoInstitucion2"),list);
			  request.setAttribute("filtroGradoF2",cc);
			
		}catch(Throwable th){
		  th.printStackTrace();
			throw new	ServletException(th);
		}
	}

	
/**
 *	Inserta los valores por defecto para el bean de información bnsica con respecto a la informacion de la institucinn educativa
 *	@param HttpServletRequest request
 *	@return boolean 
 */
	public boolean asignarBeans(HttpServletRequest request)throws ServletException, IOException{ 
		login=(Login)session.getAttribute("login");
		filtro=(FiltroBeanGrupos)session.getAttribute("filtrogroup");		
		String sentencia="";
		return true;
	}

	/**
	 * Elimina del contexto de la sesion los beans de informacion del usuario
	 *	@param HttpServletRequest request
	 */
		public void borrarBeansGrupos(HttpServletRequest request) throws ServletException, IOException{
	    session.removeAttribute("filtrogroup");
		}
		
/**
*	Recibe la peticion por el metodo get de HTTP
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doPost(request,response);
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
		if(cursor!=null)cursor.cerrar();if(util!=null)util.cerrar();
		RequestDispatcher rd=getServletContext().getRequestDispatcher(s);
		if(a==1) rd.include(request, response);
		else rd.forward(request, response);
	}

/**
*	Concatena el parametro en una variable que contiene los mensajes que se van a enviar a la vista
*	@param	String s
**/
	public void setMensaje(String s){
		if (!err){
			err=true;
			mensaje="VERIFIQUE LA SIGUIENTE información: \n\n";
		}
		mensaje+="  - "+s+"\n";
	}
}