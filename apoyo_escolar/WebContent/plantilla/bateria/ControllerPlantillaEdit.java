package siges.plantilla.bateria;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.dao.Cursor;
import siges.login.beans.Login;
import siges.plantilla.beans.FiltroPlantilla;
import siges.plantilla.beans.Logros;
import siges.plantilla.dao.PlantillaDAO;
import siges.util.Properties;

/** siges.plantilla.bateria<br>
 * Funcinn: Recibe la solicitud de filto de plantillas y llena los objetos que mostrar la vista de formulario   
 * <br>
 */
public class ControllerPlantillaEdit extends HttpServlet{
	private Cursor cursor;//objeto que maneja las sentencias sql
	private PlantillaDAO plantillaDAO;//
	private ResourceBundle rb;
	
	/**
	 * Procesa la peticion HTTP
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException<br>
	 * Return Type: String<br>
	 * Version 1.1.<br>
	 */
	public String process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		rb=ResourceBundle.getBundle("siges.plantilla.bundle.plantilla");
		String sig=null,sig4,sig5,ant,er,home;
		int tipo;
		sig4=getServletConfig().getInitParameter("sig4");
		sig5=getServletConfig().getInitParameter("sig5");
		ant=getServletConfig().getInitParameter("ant");
		er=getServletContext().getInitParameter("error");
		home=getServletContext().getInitParameter("home");
		cursor=new Cursor();
		plantillaDAO=new PlantillaDAO(cursor);
		if(request.getParameter("tipo")==null || ((String)request.getParameter("tipo")).equals("")){
			borrarBeans(request);
			session.removeAttribute("editar");
			tipo=Properties.PLANTILLABATLOGRO;
		}else
			tipo=Integer.parseInt((String)request.getParameter("tipo"));
		if(!asignarBeans(request)){
			request.setAttribute("mensaje",setMensaje("Error capturando datos de sesinn para el usuario"));
			return er;
		}
		Login login=(Login)session.getAttribute("login");
		Logros logros=(Logros)session.getAttribute("logros");
		switch(tipo){
			case Properties.PLANTILLABATLOGRO:
				editarPlantillaLogro(request,login,logros);
				sig=sig4;
			break;
			case Properties.PLANTILLABATDESCRIPTOR:
				editarPlantillaDescriptor(request,login,logros);
				sig=sig5;
			break;
		}
		return sig;
	}	

	/**
	 * Elimina del contexto de la sesion los beans de informacion del formulario de filtro
	 * @param request<br>
	 * Return Type: void<br>
	 * Version 1.1.<br>
	 */
	public void borrarBeans(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.removeAttribute("filtroEvaluacion");
	}

	/**
	 * Pone en session los datos necesarios para la vista del formulario de generacinn de plantilla de bateria de logros<BR>
	 * @param request
	 * @throws ServletException
	 * @throws IOException<br>
	 * Return Type: void<br>
	 * Version 1.1.<br>
	 */
	public void editarPlantillaLogro(HttpServletRequest request,Login login,Logros logros) throws ServletException, IOException{
		HttpSession session = request.getSession();
		Collection list;Object[] o;
		try{
			if(logros==null){
				logros=new Logros();
				logros.setPlantillaInstitucion(login.getInstId());
				logros.setPlantillaInstitucion_(login.getInst());
				//logros.setPlantillaMetodologia(login.getMetodologiaId());
				//logros.setPlantillaMetodologia_(login.getMetodologia());
				logros.setPlantillaVigencia((int)plantillaDAO.getVigenciaNumerico());
				logros.setEstadoPlanEstudios(plantillaDAO.getEstadoPlan(Long.parseLong(login.getInstId())));
				//System.out.println("ESTADO DE PLAN "+logros.getEstadoPlanEstudios());
				session.setAttribute("logros",logros);
			}
			if(logros.getPlantillaAsignatura()!=null && !logros.getPlantillaAsignatura().equals("") && !logros.getPlantillaAsignatura().equals("-99")){
				String []val=logros.getPlantillaAsignatura().replace('|',':').split(":");
				if(val!=null && val.length==3){	
					long asig=Long.parseLong(val[2]);
					request.setAttribute("listaDocente",plantillaDAO.getListaDocenteAsignatura(Long.parseLong(logros.getPlantillaInstitucion()),Integer.parseInt(logros.getPlantillaMetodologia()),logros.getPlantillaVigencia(),Integer.parseInt(logros.getPlantillaGrado()),asig));
				}	
			}
			session.removeAttribute("editar");
			//metodologias por institucion
			list = new ArrayList();
			o=new Object[2];
			o[0]=Properties.ENTEROLARGO; o[1]=login.getInstId();
			list.add(o);
			Collection c=null;
			//metodologias por institucion
			if(request.getAttribute("filtroMetodologia")==null){
				c=plantillaDAO.getFiltro(rb.getString("listaMetodologiaInstitucion"),list);
				request.setAttribute("filtroMetodologia",c);
				//System.out.println("metodo:"+c.size());
			}	
			//grados por institucion
			if(request.getAttribute("filtroGrado2")==null){
				c=plantillaDAO.getFiltro(rb.getString("filtroGradoInstitucion"),list);
				request.setAttribute("filtroGrado2",c);
				//System.out.println("Grado:"+c.size());
			}	
			o=new Object[2];
			o[0]=Properties.ENTEROLARGO; o[1]=plantillaDAO.getVigencia();
			list.add(o);
			//asignaturas por grado
			if(request.getAttribute("filtroGradoAsignatura2")==null){
				c=plantillaDAO.getFiltro(rb.getString("filtroAsignaturaMetodologiaGradoInstitucion"),list);
				request.setAttribute("filtroGradoAsignatura2",c);
				//System.out.println("Asig:"+c.size());
			}	
		}catch(Exception th){
			throw new	ServletException(th);
		}
	}

	/**
	 * Pone los objetos para la vista de formulario de filtro de bateria de desc
	 * @param request
	 * @throws ServletException
	 * @throws IOException<br>
	 * Return Type: void<br>
	 * Version 1.1.<br>
	 */
	public void editarPlantillaDescriptor(HttpServletRequest request,Login login,Logros logros) throws ServletException, IOException{
		HttpSession session = request.getSession();
		Collection list;Object[] o;
		try{
			if(logros==null){
				logros=new Logros();
				logros.setPlantillaInstitucion(login.getInstId());
				logros.setPlantillaInstitucion_(login.getInst());
				logros.setPlantillaMetodologia(login.getMetodologiaId());
				logros.setPlantillaMetodologia_(login.getMetodologia());
				logros.setPlantillaVigencia((int)plantillaDAO.getVigenciaNumerico());
				logros.setEstadoPlanEstudios(plantillaDAO.getEstadoPlan(Long.parseLong(login.getInstId())));
				session.setAttribute("logros",logros);
			}
			if(logros.getPlantillaAsignatura()!=null && !logros.getPlantillaAsignatura().equals("") && !logros.getPlantillaAsignatura().equals("-99")){
				String []val=logros.getPlantillaAsignatura().replace('|',':').split(":");
				if(val!=null && val.length==2){	
					long asig=Long.parseLong(val[1]);
					request.setAttribute("listaDocente",plantillaDAO.getListaDocenteArea(Long.parseLong(logros.getPlantillaInstitucion()),Integer.parseInt(logros.getPlantillaMetodologia()),logros.getPlantillaVigencia(),Integer.parseInt(logros.getPlantillaGrado()),asig));
				}
			}
			session.removeAttribute("editar");
			//metodologias por institucion
			list = new ArrayList();
			o=new Object[2];
			o[0]=Properties.ENTEROLARGO; o[1]=login.getInstId();
			list.add(o);
			//metodologias por institucion
			if(request.getAttribute("filtroMetodologia")==null)
				request.setAttribute("filtroMetodologia",plantillaDAO.getFiltro(rb.getString("listaMetodologiaInstitucion"),list));
			//grados por institucion
			if(request.getAttribute("filtroGrado2")==null)
				request.setAttribute("filtroGrado2",plantillaDAO.getFiltro(rb.getString("filtroGradoInstitucion"),list));
			//areas por grado
			o=new Object[2];
			o[0]=Properties.ENTEROLARGO; o[1]=plantillaDAO.getVigencia();
			list.add(o);
			if(request.getAttribute("filtroGradoArea")==null){
				request.setAttribute("filtroGradoArea",plantillaDAO.getFiltro(rb.getString("filtroAreaGradoInstitucion"),list));
			}
		}catch(Exception th){
			throw new	ServletException(th);
		}
	}

	/**
	 * Trae de session los datos credenciales de usuario
	 * @param request
	 * @return
	 * @throws ServletException
	 * @throws IOException<br>
	 * Return Type: boolean<br>
	 * Version 1.1.<br>
	 */
	public boolean asignarBeans(HttpServletRequest request)throws ServletException, IOException{ 
		HttpSession session = request.getSession();
		if(session.getAttribute("login")==null)
		    return false;
		return true;
	}

	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doPost(request,response);//redirecciona la peticion a doPost
	}

	public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
  	String s=process(request, response);
	  if(s!=null && !s.equals(""))
	  	ir(1,s,request,response);
	}

	/**
	 * Redirige el control a otro servlet
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
		if(cursor!=null)cursor.cerrar();
		RequestDispatcher rd=getServletContext().getRequestDispatcher(s);
		if(a==1) rd.include(request, response);
		else rd.forward(request, response);
	}

	/**
	 * Concatena el parametro en una variable que contiene los mensajes que se van a enviar a la vista
	 * @param s<br>
	 * Return Type: void<br>
	 * Version 1.1.<br>
	 */
	public String setMensaje(String s){
		String mensaje="VERIFIQUE LA SIGUIENTE INFORMACION: \n\n";
		return mensaje+="  - "+s+"\n";
	}
}