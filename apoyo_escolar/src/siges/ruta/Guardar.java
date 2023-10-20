package siges.ruta;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.dao.Cursor;
import siges.login.beans.Login;
import siges.ruta.dao.RutaDAO;
import siges.ruta.vo.ParamsVO;
import siges.ruta.vo.RutaVO;
import siges.ruta.vo.FiltroVO;
import siges.ruta.vo.NutricionVO;
import siges.ruta.vo.GestacionVO;

public class Guardar  extends HttpServlet{

	private Cursor cursor;//objeto que maneja las sentencias sql
	private RutaDAO dao;
	private ResourceBundle rb;
	private final String REP_OK="La información fue ingresada satisfactoriamente ";
	private static String FICHA_NUTRICION_FILTRO;
	private static String FICHA_NUTRICION_RESULTADO;
	private static String FICHA_GESTACION_FILTRO;
	private static String FICHA_GESTACION_RESULTADO;
	private static String FICHA_ANT_;
	private static String FICHA_HOME_;
	private static String FICHA_ERR_;
	
	/**
	*	Recibe la peticion por el metodo Post de HTTP
	*	@param	HttpServletRequest request
	*	@param	HttpServletResponse response
	**/
		public String process (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		  HttpSession session = request.getSession();
			RutaVO rutaVO=null,rutaVO2=null;
			NutricionVO nutricionVO=null,nutricionVO2=null;
			GestacionVO gestacionVO=null,gestacionVO2=null;
			FiltroVO filtroVO=null,filtroVO2=null;
			Login login=null;
			int tipo;
			String boton;
			FICHA_NUTRICION_FILTRO="/ruta/Nuevo.do?tipo="+ParamsVO.FICHA_NUTRICION_FILTRO;
			FICHA_NUTRICION_RESULTADO="/ruta/Resultado.jsp";
			FICHA_GESTACION_FILTRO="/ruta/Nuevo.do?tipo="+ParamsVO.FICHA_GESTACION_FILTRO;
			FICHA_GESTACION_RESULTADO="/ruta/GestacionResultado.jsp";
			FICHA_ANT_="/ruta/Nuevo.do";
			FICHA_ERR_="/error.jsp";
			FICHA_HOME_="/bienvenida.jsp";
			cursor=new Cursor();
			try{
			dao=new RutaDAO(cursor);
			boton=(request.getParameter("cmd")!=null)?request.getParameter("cmd"):new String("Cancelar");
			if(boton.equals("Cancelar")){
				borrarBeans(request,session);
				return FICHA_HOME_;
			}
			login=(Login)session.getAttribute("login");
			rutaVO=(RutaVO)session.getAttribute("rutaVO");
			filtroVO=(FiltroVO)session.getAttribute("filtroVO");
			nutricionVO=(NutricionVO)session.getAttribute("nutricionVO");
			gestacionVO=(GestacionVO)session.getAttribute("gestacionVO");
			tipo=getTipo(request,session);
			System.out.println("VALOR DE BOTON="+boton+"=tipo="+tipo);
			if(boton.equals("Buscar")){
				switch(tipo){
					case ParamsVO.FICHA_NUTRICION_FILTRO:
						getResultadoNutricion(request,login,filtroVO);
						return FICHA_NUTRICION_RESULTADO;
					case ParamsVO.FICHA_GESTACION_FILTRO:
						getResultadoGestacion(request,login,filtroVO);
						return FICHA_GESTACION_RESULTADO;
				}			
			}
			if(boton.equals("Guardar")){
				switch(tipo){
					case ParamsVO.FICHA_NUTRICION_RESULTADO:
						insertarNutricion(request,login,filtroVO,nutricionVO);
						getResultadoNutricion(request,login,filtroVO);
						return FICHA_NUTRICION_RESULTADO;
					case ParamsVO.FICHA_GESTACION_RESULTADO:
						insertarGestacion(request,login,filtroVO,gestacionVO);
						getResultadoGestacion(request,login,filtroVO);
						return FICHA_GESTACION_RESULTADO;
				}			
			}
		return FICHA_ERR_;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Error "+this+": "+e.toString());
			throw new ServletException(e);
		}finally{
			if(cursor!=null)cursor.cerrar();
		}
	  }

		private void getResultadoNutricion(HttpServletRequest request,Login login,FiltroVO filtroVO){
			request.setAttribute("resultadoBusqueda",dao.getResultadoNutricion(login,filtroVO));
		}
		
		private void getResultadoGestacion(HttpServletRequest request,Login login,FiltroVO filtroVO){
			request.setAttribute("resultadoBusqueda",dao.getResultadoGestacion(login,filtroVO));
		}
		
		private void insertarNutricion(HttpServletRequest request,Login login,FiltroVO filtroVO,NutricionVO nutricionVO){
			if(dao.insertar(nutricionVO,login,filtroVO)){
				request.setAttribute("mensaje",ParamsVO.SMS+"La información fue guardada satisfactoriamente");
			}else{
				request.setAttribute("mensaje",ParamsVO.SMS+"Error insertando informacion de nutricion: "+dao.getMensaje());
			}
		}
		
		private void insertarGestacion(HttpServletRequest request,Login login,FiltroVO filtroVO,GestacionVO gestacionVO){
			if(dao.insertar(gestacionVO,login,filtroVO)){
				request.setAttribute("mensaje",ParamsVO.SMS+"La información fue guardada satisfactoriamente");
			}else{
				request.setAttribute("mensaje",ParamsVO.SMS+"Error insertando informacion de gestacinn: "+dao.getMensaje());
			}
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
				session.removeAttribute("filtroVO");
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
