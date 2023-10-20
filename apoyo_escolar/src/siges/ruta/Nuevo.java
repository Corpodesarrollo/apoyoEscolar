package siges.ruta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.dao.Cursor;
import siges.login.beans.Login;
import siges.ruta.vo.*;
import siges.ruta.dao.*;

public class Nuevo  extends HttpServlet{
	private Cursor cursor;//objeto que maneja las sentencias sql
	private RutaDAO dao;
	private RutaVO rutaVO;
	private ResourceBundle rb;
	private static String FICHA_NUTRICION_FILTRO;
	private static String FICHA_NUTRICION_RESULTADO;
	private static String FICHA_GESTACION_FILTRO;
	private static String FICHA_GESTACION_RESULTADO;
	private static String FICHA_ANT_;
	private static String FICHA_HOME_;
	private static String FICHA_ERR_;

	/**
	*	Procesa la peticion HTTP
	*	@param	HttpServletRequest request
	*	@param	HttpServletResponse response
	**/
		public String process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		  HttpSession session = request.getSession();
		  rb=ResourceBundle.getBundle("siges.institucion.organizacion.organizacion");
		  String sig=null;
			int tipo;
			FICHA_NUTRICION_FILTRO="/ruta/Filtro.jsp";
			FICHA_NUTRICION_RESULTADO="/ruta/Resultado.jsp";
			FICHA_GESTACION_FILTRO="/ruta/GestacionFiltro.jsp";
			FICHA_GESTACION_RESULTADO="/ruta/GestacionResultado.jsp";
			FICHA_ANT_="/index.jsp";
			FICHA_ERR_="/error.jsp";
			FICHA_HOME_="/bienvenida.jsp";
			cursor=new Cursor();
			try{
				dao=new RutaDAO(cursor);
				if(!asignarBeans(request)){
					request.setAttribute("mensaje",ParamsVO.SMS+"Error capturando datos de sesinn para el usuario");
					return (FICHA_ERR_);
				}
				Login login=(Login)session.getAttribute("login");
				rutaVO=(RutaVO)session.getAttribute("rutaVO");
				tipo=getTipo(request,session);
				switch(tipo){
					case ParamsVO.FICHA_NUTRICION_FILTRO:
						editarFiltroNutricion(request,login);
						sig=FICHA_NUTRICION_FILTRO;
					break;	
					case ParamsVO.FICHA_GESTACION_FILTRO:
						editarFiltroGestacion(request,login);
						sig=FICHA_GESTACION_FILTRO;
					break;	
				}
				return sig;
			}catch(Exception e){
			    e.printStackTrace();
				throw new ServletException(e);
			}finally{
			  if(cursor!=null)cursor.cerrar();
			}
		}
		
		private void editarFiltroNutricion(HttpServletRequest request,Login login){
			Collection []c=dao.getFiltrosBuscarNutricion(login);
			if(c!=null){
				request.setAttribute("filtroSedeF",c[0]);
				request.setAttribute("filtroJornadaF",c[1]);
				request.setAttribute("filtroMetodologiaF",c[2]);		
				request.setAttribute("filtroGradoF2",c[3]);
				request.setAttribute("filtroGrupoF",c[4]);						
			}
		}
		
		private void editarFiltroGestacion(HttpServletRequest request,Login login){
			Collection []c=dao.getFiltrosBuscarGestacion(login);
			if(c!=null){
				request.setAttribute("filtroSedeF",c[0]);
				request.setAttribute("filtroJornadaF",c[1]);
				request.setAttribute("filtroMetodologiaF",c[2]);		
				request.setAttribute("filtroGradoF2",c[3]);
				request.setAttribute("filtroGrupoF",c[4]);						
			}
		}
		
		/**
		 *	Inserta los valores por defecto para el bean de información bnsica con respecto a la informacion de la institucinn educativa
		 *	@param HttpServletRequest request
		 *	@return boolean 
		 */
			public boolean asignarBeans(HttpServletRequest request)throws ServletException, IOException{ 
			    if(request.getSession().getAttribute("login")==null)
			    	return false;
			    return true;
			}
			
		private int getTipo(HttpServletRequest request,HttpSession session){
			if(request.getParameter("tipo")==null || ((String)request.getParameter("tipo")).equals("")){
				borrarBeans(request,session);
				return ParamsVO.FICHA_DEFAULT;
			}else
				return Integer.parseInt((String)request.getParameter("tipo"));
		}
		
		/**
		 * Elimina del contexto de la sesion los beans de informacion del usuario
		 *	@param HttpServletRequest request
		 */
			public void borrarBeans(HttpServletRequest request,HttpSession session){
				session.removeAttribute("rutaVO");
				session.removeAttribute("rutaVO2");
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
}
