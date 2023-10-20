package siges.convocatoria;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import siges.dao.*;
import siges.estudiante.beans.Convivencia;
import siges.login.beans.Login;
import siges.personal.beans.Personal;
import siges.usuario.dao.UsuarioDAO;
import siges.util.Recursos;
import siges.exceptions.InternalErrorException;

/**
*	Nombre:	ControllerFiltroEdit<br>
*	Descripcinn:	Filtro de usuarios<br>
*	Funciones de la pngina:	Envia al formulario los filtros de usario<br>
*	Entidades afectadas:	Usuario<br>
*	Fecha de modificacinn:	20/07/2005 <br>
*	@author Latined <br>
*	@version v 1.0 <br>
*/
public class ControllerFiltro extends HttpServlet{

	private String mensaje;//mensaje en caso de error
	private String buscar;
	private boolean err;//variable que inidica si hay o no errores en la validacion de los datos del formulario
	private Cursor cursor;//objeto que maneja las sentencias sql
	private Util util;
	private Personal personal;
	private Login login;
	private HttpSession session;
	private Collection col1, list;
	private Object o[];
	private ResourceBundle rbUsuario;
	private Integer cadena=new Integer(java.sql.Types.VARCHAR);
	private Integer entero=new Integer(java.sql.Types.INTEGER);
	private Integer fecha=new Integer(java.sql.Types.DATE);
	private Integer nulo=new Integer(java.sql.Types.NULL);
	private Integer doble=new Integer(java.sql.Types.DOUBLE);
	private Integer caracter=new Integer(java.sql.Types.CHAR);
	private Integer enterolargo=new Integer(java.sql.Types.BIGINT);
	
	/**
	*	Funcinn: Procesa la peticion HTTP
	*	@param	HttpServletRequest request
	*	@param	HttpServletResponse response
	**/
		public String process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		    rbUsuario=ResourceBundle.getBundle("convocatoria");
		    session = request.getSession();
		    list = new ArrayList();
		    String sig,sig1;
		    String ant;
		    String er;
		    String home;
		    String consulta;
		    String depar;
		    String munic;
		    String nucleo;
		    String inst;
		    String sede;
		    String jornada;
		    String forma;
		    int combo;
		    sig="/convocatoria/filtro.jsp";
		    sig1="/convocatoria/filtro.jsp";
		    String p_inte=this.getServletContext().getInitParameter("integrador");
		    String p_home=this.getServletContext().getInitParameter("home");
		    String p_login=this.getServletContext().getInitParameter("login");
		    String p_error=this.getServletContext().getInitParameter("er");
		    cursor=new Cursor();
		    err=false;
		    mensaje=null;
		    try{
		        util=new Util(cursor);
		        combo=request.getParameter("combo")!=null?Integer.parseInt((String)request.getParameter("combo")):-1;
		        forma=(String)request.getParameter("forma");
		        //System.out.println("combo: --- "+combo+" ---"+forma);
		        request.setAttribute("forma",forma);

		        switch(combo){
		        case 3:
		            //carga instituciones
		            munic=(String)request.getParameter("loc");
		            //nucleo=(String)request.getParameter("nucleo");
		            list = new ArrayList();
		            o=new Object[2];
		            o[0]=enterolargo;
		            o[1]=munic;
		            list.add(o);
		            col1=util.getFiltro(rbUsuario.getString("CargaInstituciones"),list);
		            request.setAttribute("var","3");
		            request.setAttribute("col1",col1);
		            return sig1;
		        case 4:
		            //Carga sedes
		            inst=(String)request.getParameter("col");
		            list = new ArrayList();
		            o=new Object[2];
		            o[0]=enterolargo;
		            o[1]=inst;
		            list.add(o);
		            col1=util.getFiltro(rbUsuario.getString("CargaSedes"),list);
		            request.setAttribute("var","4");
		            request.setAttribute("col1",col1);
		            return sig1;
		        case 5:
		            //Carga jornada
		            inst=(String)request.getParameter("col");
		            sede=(String)request.getParameter("sed");
		            list = new ArrayList();
		            o=new Object[2];
		            o[0]=enterolargo;
		            o[1]=inst;
		            list.add(o);
		            o=new Object[2];
		            o[0]=enterolargo;
		            o[1]=sede;
		            list.add(o);
		            col1=util.getFiltro(rbUsuario.getString("CargaJornadas"),list);
		            request.setAttribute("var","5");
		            request.setAttribute("col1",col1);
		            return sig1;
		        }
		        return sig;	
		    }catch(Exception e){
		        System.out.println("Error "+this+": "+e.toString());
		        throw new	ServletException(e);
		    }finally{
		        if(cursor!=null)cursor.cerrar();if(util!=null)util.cerrar();
		    }
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
		  	ir(2,s,request,response);
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
