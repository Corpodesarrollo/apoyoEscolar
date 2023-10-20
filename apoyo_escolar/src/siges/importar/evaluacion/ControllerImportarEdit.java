package siges.importar.evaluacion;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.util.Properties;

/** siges.importar.evaluacion<br>
 * Funcinn: Servicio que recibe la solicitud de importacion de plantillas de evaluacion y devuelve los foirmularios de imprtacion de archivo plantilla    
 * <br>
 */
public class ControllerImportarEdit extends HttpServlet{
	private String mensaje;
	private boolean err;//variable que inidica si hay o no errores en la validacion de los datos del formulario

	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException<br>
	 * Return Type: String<br>
	 * Version 1.1.<br>
	 */
	public String process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session= request.getSession();
		String sig,sig2,sig3;
		String ant;
		String er;
		String home;
		int tipo;
		sig=getServletConfig().getInitParameter("sig");
		sig2=getServletConfig().getInitParameter("sig2");
		sig3=getServletConfig().getInitParameter("sig3");
		ant=getServletConfig().getInitParameter("ant");
		er=getServletContext().getInitParameter("error");
		home=getServletContext().getInitParameter("home");
		err=false;
		mensaje=null;
		if(request.getParameter("tipo")==null || ((String)request.getParameter("tipo")).equals("")){
			borrarBeans(request);
			session.removeAttribute("editar");
			tipo=1;
		}else{
		  String valorCampo=(String)request.getParameter("tipo");
			try{
			  tipo=Integer.parseInt((String)request.getParameter("tipo"));
			}catch(NumberFormatException e){
			  tipo=Integer.parseInt(valorCampo.substring(valorCampo.length()-1,valorCampo.length()));
			}
		}
		if(!asignarBeans(request)){
			setMensaje("Error capturando datos de sesinn para el usuario");
			request.setAttribute("mensaje",mensaje);
			return er;
		}		
		switch(tipo){
			case Properties.PLANTILLALOGROASIG: 
			break;
			case Properties.PLANTILLAAREADESC: 
				sig=sig2; 
			break;
			case Properties.PLANTILLAPREE: 
				sig=sig3; 
			break;
		}
		if(err){
			request.setAttribute("mensaje",mensaje);
			return er;
		}
		return sig;
	}	

	/**
	 * @param request<br>
	 * Return Type: void<br>
	 * Version 1.1.<br>
	 */
	public void borrarBeans(HttpServletRequest request){
		request.getSession().removeAttribute("filtroEvaluacion");
	}

	/**
	 * @param request
	 * @return
	 * @throws ServletException
	 * @throws IOException<br>
	 * Return Type: boolean<br>
	 * Version 1.1.<br>
	 */
	public boolean asignarBeans(HttpServletRequest request)throws ServletException, IOException{ 
		HttpSession session= request.getSession();
		if(session.getAttribute("login")==null)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doPost(request,response);//redirecciona la peticion a doPost
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
  	String s=process(request, response);
	  if(s!=null && !s.equals(""))
	  	ir(1,s,request,response);
	}

	/**
	 * @param a
	 * @param s
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException<br>
	 * Return Type: void<br>
	 * Version 1.1.<br>
	 */
	public void ir(int a,String s,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher rd=getServletContext().getRequestDispatcher(s);
		if(a==1) rd.include(request, response);
		else rd.forward(request, response);
	}

	/**
	 * @param s<br>
	 * Return Type: void<br>
	 * Version 1.1.<br>
	 */
	public void setMensaje(String s){
		if(!err){
			err=true;
			mensaje="VERIFIQUE LA SIGUIENTE INFORMACION: \n\n";
		}
		mensaje+="  - "+s+"\n";
	}
}