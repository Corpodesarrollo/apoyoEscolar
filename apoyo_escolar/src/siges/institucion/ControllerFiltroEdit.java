package siges.institucion;

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
import siges.login.beans.Login;
import siges.util.Properties;
import siges.institucion.beans.*;
import siges.institucion.dao.*;

/**
*	Nombre:	ControllerFiltroEdit
*	Descripcion:	Controla el formulario de nuevo registro por parte de la orientadora 
*	Parametro de entrada:	HttpServletRequest request, HttpServletResponse response
*	Parametro de salida:	HttpServletRequest request, HttpServletResponse response
*	Funciones de la pagina:	Procesar la peticion y enviar el control al formulario de nuevo registro
*	Entidades afectadas:	Tablas maestras en modo de solo lectura
*	Fecha de modificacinn:	01/12/04
*	@author Pasantes UD
*	@version $v 1.2 $
*/
public class ControllerFiltroEdit extends HttpServlet{
	private String mensaje;//mensaje en caso de error
	private boolean err;//variable que inidica si hay o no errores en la validacion de los datos del formulario
	private Cursor cursor;//objeto que maneja las sentencias sql
	private Login login;
	private InstitucionDAO institucionDAO;
	private FiltroInstitucion filtro;
	private HttpSession session;
	private ResourceBundle rb;

/**
*	Procesa la peticion HTTP
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
	public String process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		session = request.getSession();
		rb=ResourceBundle.getBundle("siges.institucion.bundle.institucion");
		String sig,sig2;
		String ant;
		String er;
		String home;
		int tipo;
		sig="/institucion/Filtro.jsp";
		sig2="/institucion/ControllerNuevoEdit.do";
		ant="/index.jsp";
		er="/error.jsp";
		home="/bienvenida.jsp";
		cursor=new Cursor();
		err=false;
		mensaje=null;
		try{
		institucionDAO=new InstitucionDAO(cursor);
		if(!asignarBeans(request)){
			setMensaje("Error capturando datos de sesinn para el usuario");
			request.setAttribute("mensaje",mensaje);
			return er;
		}
		String boton=(request.getParameter("cmd")!=null)?request.getParameter("cmd"):new String("");		
		if(!login.getInstId().equals("")){
			editarInstitucion(request);
			return sig2+"?tipo2=1&cmd=_";
		}
		if(boton.equals("")){
			filtro(request);
		}
		if(boton.equals("Guardar")){
			buscar(request);
		}
		if(boton.equals("Cancelar")){
			borrarBeans(request);
		}
		if(boton.equals("Editar")){			
			editar(request);
			sig=sig2+"?tipo2=1&cmd=_";
		}		
		return sig;
		}catch(Exception e){
			e.printStackTrace();
				System.out.println("Error "+this+": "+e.toString());
				throw new	ServletException(e);
			}finally{
			  if(cursor!=null)cursor.cerrar();
			}
	}

	public void editar(HttpServletRequest request)throws ServletException, IOException{
		String id,id2;
		borrarBeans(request);
		request.setAttribute("tipo","1");
		request.getSession().setAttribute("editar","1");
		id=(request.getParameter("id")!=null)?request.getParameter("id"):new String("");
		id2=(request.getParameter("id2")!=null)?request.getParameter("id2"):new String("");
		Institucion institucion=null;
		if(!cursor.abrir(2)){
			setMensaje("Error intentando obtener información del sistema de matriculas");
			request.setAttribute("mensaje",mensaje);
		}else{	
			institucionDAO=new InstitucionDAO(cursor);
			institucion=institucionDAO.asignarInstitucion(id2,id);
		}
			request.getSession().setAttribute("subframe2","/institucion/Filtro.jsp");
			if(institucion!=null){
			    request.getSession().setAttribute("nuevoInstitucion",institucion);
			    request.getSession().setAttribute("nuevoInstitucion2",institucion.clone()) ;				
			}else{
				request.setAttribute("mensaje","No fue posible obtener información del colegio");
				institucion=new Institucion();
				institucion.setInscoddane(id);
				institucion.setInscodigo(id2);
				institucion.setEstado("1");
				request.getSession().setAttribute("nuevoInstitucion",institucion);
			}				
		}
		
		public void editarInstitucion(HttpServletRequest request)throws ServletException, IOException{
			String id;
			borrarBeans(request);
			request.setAttribute("tipo","1");
			request.getSession().setAttribute("editar","1");
			id=login.getInstId();
			String dane=institucionDAO.getDane(id);
			Institucion institucion=null;
			cursor=new Cursor();
			if(!cursor.abrir(2)){
				setMensaje("No se pudo establecer la conexinn con el sistema de matriculas");
				request.setAttribute("mensaje",mensaje);
			}else{
				institucionDAO=new InstitucionDAO(cursor);
				institucion=institucionDAO.asignarInstitucion(id,dane);
			}
			if(institucion==null){
				institucion=new Institucion();
				institucion.setInscoddane(dane);
				institucion.setInscodigo(id);
				institucion.setInsnombre(login.getInst());
				institucion.setInscoddepto(login.getDepId());
				institucion.setInscodmun(login.getMunId());
				institucion.setInscodlocal(login.getLocId());
				institucion.setInsnucleocodigo(login.getLocId());
				institucion.setEstado("1");
			}
			request.getSession().setAttribute("nuevoInstitucion",institucion);
			request.getSession().setAttribute("nuevoInstitucion2",institucion.clone());				
	}
	
	public void filtro(HttpServletRequest request)throws ServletException, IOException{
		try{
			session.removeAttribute("editar");
			session.removeAttribute("filtroInstituciones");
			session.removeAttribute("buscado");
	    Collection list;
		  Object[] o;
			String s;
			int z;
			if(session.getAttribute("filtroMunicipioDep")==null || session.getAttribute("filtroNucleoMunicipio")==null){
		    z=0;
		    list = new ArrayList();
		    o=new Object[2];
				o[z++]=new Integer(java.sql.Types.INTEGER);
				o[z++]=login.getDepId();
				list.add(o);
			 	session.setAttribute("filtroMunicipioDep",institucionDAO.getFiltro(rb.getString("filtroMunicipioDepartamento"),list));
			 	session.setAttribute("filtroNucleoMunicipio",institucionDAO.getFiltro(rb.getString("filtroNucleoMunicipio")));
			}
		}catch(Throwable th){
			throw new	ServletException(th);
		}
	}
	
	public void buscar(HttpServletRequest request)throws ServletException, IOException{
		String s;
		int z;		
    Collection list=new ArrayList();
	  Object[] o;
		try{
			System.out.println(filtro.getMunicipio()+"//"+filtro.getNucleo()+"//"+filtro.getDane()+"//"+filtro.getNombre());
			s=rb.getString("filtro.consulta");
			o=new Object[2];
			o[0]=Properties.ENTEROLARGO;
			o[1]=login.getDepId();
			list.add(o);
			if(!filtro.getMunicipio().equals("-1")){
				s+=" "+rb.getString("filtro.where1");
				o=new Object[2];
				o[0]=Properties.ENTEROLARGO;
				o[1]=filtro.getMunicipio();
				list.add(o);
			}	
			if(!filtro.getNucleo().equals("-1")){
				s+=" "+rb.getString("filtro.where11");
				o=new Object[2];
				o[0]=Properties.ENTEROLARGO;
				o[1]=filtro.getNucleo();
				list.add(o);
			}	
			if(!filtro.getDane().equals("")){
				s+=" "+rb.getString("filtro.where2");
				o=new Object[2];
				o[0]=Properties.ENTEROLARGO;
				o[1]=filtro.getInstitucion();
				list.add(o);
			}	
			if(!filtro.getNombre().equals("")){
				s+=" "+rb.getString("filtro.where3");
				o=new Object[2];
				o[0]=Properties.CADENA;
				o[1]=filtro.getNombre();
				list.add(o);
			}	
			switch(Integer.parseInt(filtro.getOrden())){
				case -1://codigo interno
					s+=" "+rb.getString("filtro.orden1");
				break;
				case 0://codigo dane
					s+=" "+rb.getString("filtro.orden2");
				break;
				case 1://nombre
					s+=" "+rb.getString("filtro.orden3");
				break;
			}
			System.out.println(s);
			request.getSession().setAttribute("filtroInstituciones",institucionDAO.getFiltro(s,list));
			request.getSession().setAttribute("buscado","si");
		}catch(Exception e){
		  e.printStackTrace();
			throw new	ServletException(e);
		}		
	}

/**
 *	Inserta los valores por defecto para el bean de información bnsica con respecto a la informacion de la institucinn educativa
 *	@param HttpServletRequest request
 *	@return boolean 
 */
	public void borrarBeans(HttpServletRequest request)throws ServletException, IOException{ 
		request.removeAttribute("");
		session.removeAttribute("subframe2");
	}		

/**
 *	Inserta los valores por defecto para el bean de información bnsica con respecto a la informacion de la institucinn educativa
 *	@param HttpServletRequest request
 *	@return boolean
 */
	public boolean asignarBeans(HttpServletRequest request)throws ServletException, IOException{ 
		if(session.getAttribute("login")!=null){
			login=(Login)session.getAttribute("login");
			filtro=(FiltroInstitucion)session.getAttribute("filtroInstitucion");
			return true;
		}
		return false;
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
		if (!err){
			err=true;
			mensaje="VERIFIQUE LA SIGUIENTE INFORMACION: \n\n";
		}
		mensaje+="  - "+s+"\n";
	}
}