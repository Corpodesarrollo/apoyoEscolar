package siges.common.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import siges.common.vo.ItemVO;
import siges.common.vo.Params;

public class Service extends HttpServlet {
	public ServletConfig config=null;
	public ServletContext context=null;
	public static String PAG_INTEGRADOR;
	public static String PAG_HOME;
	public static String PAG_LOGIN;
	public static String PAG_LOGIN2;
	public static String PAG_ERROR;
	public static String PAG_CONTRASENA;
	public static String PAG_INICIO;
	public static String PAG_MENU;
	public static String PAG_MAIN;

	public void init(ServletConfig config) throws ServletException{
		this.config=config;
		context=config.getServletContext();
		PAG_CONTRASENA=context.getInitParameter("contrasena");
		PAG_HOME=context.getInitParameter("home");
		PAG_LOGIN=context.getInitParameter("login");
		PAG_LOGIN2=context.getInitParameter("login2");
		PAG_ERROR=context.getInitParameter("error");
		PAG_INTEGRADOR=context.getInitParameter("integrador");
		PAG_INICIO=context.getInitParameter("inicio");
		PAG_MENU=context.getInitParameter("menu");
		PAG_MAIN=context.getInitParameter("main");
	}
	
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		return null;
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
	  	String []s=process(request, response);	  	
		  if(s!=null && s[1]!=null){
			  if(s[0].equals(""+Params.FORWARD)){
				  ir(Params.FORWARD,s[1],request,response);
			  }else{
				  ir(Params.INCLUDE,s[1],request,response);
			  }
		  }
		}
		
	/**
	*	Redirige el control a otro servlet
	*	@param	int a: 1=redirigir como 'include', 2=redirigir como 'forward'
	*	@param	String s
	*	@param	HttpServletRequest request
	*	@param	HttpServletResponse response
	**/
		public void ir(int a,String s,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
			//RequestDispatcher rd=getServletContext().getRequestDispatcher(s);
			RequestDispatcher rd=context.getRequestDispatcher(s);
			if(a==1) rd.include(request, response);
			else rd.forward(request, response);
		}
		
		public String getRequest(HttpServletRequest req, String param){
			return req.getParameter(param);
		}
		
		public String getRequest(HttpServletRequest req, String param, String nulo){
			return req.getParameter(param)==null?nulo:req.getParameter(param);
		}
		
		public String getRequest2(HttpServletRequest req, String param){
			return (String)req.getAttribute(param);
		}
		
		public String getRequest2(HttpServletRequest req, String param, String nulo){
			return (String)(req.getAttribute(param)==null?nulo:req.getAttribute(param));
		}
		
		public String[] getRequestParam(HttpServletRequest req, String param){
			return req.getParameterValues(param);
		}
		
		public int getTipo(HttpServletRequest request,HttpSession session){
			int tip=1;
			if(request.getParameter("tipo")!=null && !((String)request.getParameter("tipo")).equals("")){
			    return Integer.parseInt((String)request.getParameter("tipo"));
			}    
			return tip;
		}
		
		public int getTipo(HttpServletRequest request,HttpSession session, int defecto){
			int tip=defecto;
			if(request.getParameter("tipo")!=null && !((String)request.getParameter("tipo")).equals("")){
			    return Integer.parseInt((String)request.getParameter("tipo"));
			}    
			return tip;
		}
		
		public int getCmd(HttpServletRequest request,HttpSession session){
			int cmd=1;
			if(request.getParameter("cmd")!=null && !((String)request.getParameter("cmd")).equals("")){
				return Integer.parseInt((String)request.getParameter("cmd"));
			}
			return cmd;
		}
		
		public int getCmd(HttpServletRequest request,HttpSession session,int defecto){
			int cmd=defecto;
			if(request.getParameter("cmd")!=null && !((String)request.getParameter("cmd")).equals("")){
				return Integer.parseInt((String)request.getParameter("cmd"));
			}
			return cmd;
		}
		
		
		/**
		 * @param numPer
		 * @param nomPerDef
		 * @return
		 */
		public List getListaPeriodo(long numPer, String nomPerDef){
			List l=new ArrayList();
			ItemVO item=null;
			for(int i=1;i<=numPer;i++){
				item=new ItemVO(i,""+i); l.add(item);
			}		
			item=new ItemVO(7,nomPerDef); l.add(item);
			return l;
		}
		


}
