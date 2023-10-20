package siges.datoMaestro;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import parametros.calendario.vo.CalendarioVO;
import parametros.calendario.vo.FiltroCalendarioVO;
import parametros.calendario.vo.ParamsVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import siges.common.vo.ItemVO;
import siges.datoMaestro.beans.*;
import siges.dao.*;
import siges.login.beans.Login;

/**
*	Nombre: DatoMaestroSeleccionEdit.java	<BR>
*	Descripcinn:	Recibe la peticion de un dato maestro y la redirige a la pagina donde estan los frames 'lista' y 'nuevo'<BR>
*	Funciones de la pngina:	Preguntar por el valor de 'dato', crear el bean 'DatoMaestroBean', colocarlo en el ambito de la sesion y seder el control a la pagina de frames	DatoMaestro.jsp<BR>
*	Entidades afectadas:	No aplica	<BR>
*	Fecha de modificacinn:	20/07/2005	<BR>
*	@author Latined	<BR>
*	@version v 1.0	<BR>
*/

public class DatoMaestroSeleccionEdit extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Cursor cursor;//objeto que maneja las sentencias sql
	private Util util;//



/**
*	Recibe la peticion por el metodo get de HTTP
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doPost(request,response);//redirige la peticion al evento doPost
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
*	Procesa la peticion HTTP
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
			
	public String process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();  
		int dato;//variable que recibe el parametro get
		String sig;//pagina jsp que tomara el control despues de la gestion de este servlet
		String home;//pagina jsp que tomara el control despues de la gestion de este servlet
		String er;//pagina jsp que tomara el control si hay errores en el procesamiento de los datos
		DatoMaestroBean datoMaestro =new DatoMaestroBean();//Bean que contiene la informacion de todos los datos maestros
		sig="/DatoMaestroSeleccion.jsp";
		home="/bienvenida.jsp";
		er="/error.jsp";
		
		
		try{
		cursor=new Cursor();
		util=new Util(cursor);
		
		if(request.getParameter("dato")==null  || request.getParameter("dato").equals("")){
		   System.out.println("DATO:"+request.getParameter("dato"));
			 return er;
		}	
		dato=Integer.parseInt(request.getParameter("dato"));
		if(!datoMaestro.seleccionar(dato,cursor)){
			sig=er;
			
			request.setAttribute("mensaje",setMensaje(datoMaestro.getMensaje()));
		}else{
			request.getSession().setAttribute("datoMaestro",datoMaestro);//asigna a la sesion el objeto datoMaestro con el nombre 'dato maestro'
			request.getSession().setAttribute("dato",request.getParameter("dato"));//asigna a la sesion el objeto datoMaestro con el nombre 'dato maestro'
		}


		switch(dato){
		//DIVISInN POLnTICA
	  	case 70:
				sig="/datoMaestro/filtrocertificado.jsp";
			break;
	  	case 3:
 				sig="/datoMaestro/DivisionPolitica.jsp";
			break;
			
		  case 21:
				sig="/datoMaestro/DivisionPolitica1.jsp";
			break;

		  case 27:
					sig="/datoMaestro/DivisionPolitica2.jsp";
			break;
			
			//PARAMETROS ACADnMICOS			
			case 22:
				sig="/datoMaestro/ParametrosAcademicos.jsp";	
			break;	

			case 29:
				sig="/datoMaestro/ParametrosAcademicos1.jsp";	
			break;	

			case 1:
				sig="/datoMaestro/ParametrosAcademicos2.jsp";	
			break;	

			case 4:
				sig="/datoMaestro/ParametrosAcademicos3.jsp";	
			break;	

			case 23:
				sig="/datoMaestro/ParametrosAcademicos4.jsp";	
			break;	
			
			case 24:
				sig="/datoMaestro/ParametrosAcademicos5.jsp";	
			break;	

			case 9:
				sig="/datoMaestro/ParametrosAcademicos6.jsp";	
			break;	

			case 12:
				sig="/datoMaestro/ParametrosAcademicos18.jsp";	
			break;	
			
			case 26:
				sig="/datoMaestro/ParametrosAcademicos8.jsp";	
			break;	

			case 15:
				sig="/datoMaestro/ParametrosAcademicos9.jsp";	
			break;	

			case 16:
				sig="/datoMaestro/ParametrosAcademicos11.jsp";	
			break;	
			
			case 45:
				sig="/datoMaestro/ParametrosAcademicos14.jsp";	
			break;	

			case 46:
				sig="/datoMaestro/ParametrosAcademicos15.jsp";	
			break;	

			case 43:
				sig="/datoMaestro/ParametrosAcademicos16.jsp";	
			break;	
			
			case 53:
					sig="/datoMaestro/ParametrosAcademicos19.jsp";	
			break;	
			
			case 52:
					sig="/datoMaestro/ParametrosAcademicos20.jsp";	
			break;	
			
			case 51:
					sig="/datoMaestro/ParametrosAcademicos21.jsp";	
			break;	
			
		//PARAMETROS GENERALES	
			case 2:
				sig="/datoMaestro/ParametrosGenerales.jsp";				
			break;	

			case 25:
				sig="/datoMaestro/ParametrosGenerales1.jsp";				
			break;	

			case 6:
				sig="/datoMaestro/ParametrosGenerales2.jsp";				
			break;	

			case 11:
				sig="/datoMaestro/ParametrosGenerales3.jsp";				
			break;	
			
			case 19:
				sig="/datoMaestro/ParametrosGenerales4.jsp";				
			break;	
			
			case 8:
				sig="/datoMaestro/ParametrosGenerales5.jsp";				
			break;	
			
			case 14:
				sig="/datoMaestro/ParametrosGenerales6.jsp";				
			break;	
			
			case 18:
				sig="/datoMaestro/ParametrosGenerales7.jsp";				
			break;	

			case 17:
				sig="/datoMaestro/ParametrosGenerales8.jsp";	
			break;	

			case 13:
				sig="/datoMaestro/ParametrosGenerales9.jsp";	
			break;	

			case 44:
				sig="/datoMaestro/ParametrosGenerales10.jsp";	
			break;	

			case 47:
				sig="/datoMaestro/ParametrosGenerales11.jsp";	
			break;	

			case 40:
				sig="/datoMaestro/ParametrosGenerales12.jsp";
			break;

			case 42:
				sig="/datoMaestro/ParametrosGenerales13.jsp";
			break;
			
			case 49:
					sig="/datoMaestro/ParametrosGenerales15.jsp";
			break;
			
			case 50:
					sig="/datoMaestro/ParametrosGenerales14.jsp";
			break;
			
			
			//PLAN DE ESTUDIOS			
			case 31:
				sig="/plandeEstudios/NuevoPlanEstudios.jsp";				
			break;	
			
			case 32:
				sig="/plandeEstudios/NuevoPlanEstudios2.jsp";			
			break;	

			case 28:
				sig="/plandeEstudios/NuevoPlanEstudios3.jsp";			
			break;	

			case 30:
				sig="/plandeEstudios/NuevoPlanEstudios4.jsp";				
			break;	
				
			case 34:
				sig="/plandeEstudios/NuevoPlanEstudios5.jsp";
			break;	

			case 35:
				sig="/plandeEstudios/NuevoPlanEstudios6.jsp";				
			break;	

			case 41:
				sig="/plandeEstudios/NuevoPlanEstudios7.jsp";				
			break;	

			case 48:
				sig="/plandeEstudios/NuevoPlanEstudios8.jsp";
			break;	

			//PLAN DE ESTUDIOS I

			case 36:
				sig="/plandeEstudios/PlanEstudios4.jsp";
			break;

			case 37:
				sig="/plandeEstudios/PlanEstudios.jsp";
			break;	

			case 39:
				sig="/plandeEstudios/PlanEstudios3.jsp";
			break;	
			case 65:
				CalendarioVO calendario = new CalendarioVO();
				calendarioNuevo(request,session,calendario);
				FiltroCalendarioVO filtroCalendario = null;
				actaInit(request, session, filtroCalendario, calendario);
				sig="/parametros/calendario/Nuevo.jsp";
			break;
								
		}	
		return sig;
		}catch(Exception e){System.out.println("Error "+this+": "+e.toString());
		 throw new	ServletException(e);
		}finally{
			  if(cursor!=null)cursor.cerrar();if(util!=null)util.cerrar();
		}		
	}
	
	private void actaInit(HttpServletRequest request, HttpSession session,
			FiltroCalendarioVO filtroCalendario, CalendarioVO calendario) {
		try {
			if(filtroCalendario == null){
				filtroCalendario = new FiltroCalendarioVO();
				/**
				 * Por defecto se selecciona el mes actual
				 */
				int month = Calendar.getInstance().get(Calendar.MONTH);
				filtroCalendario.setMes(month+1);
				session.setAttribute("filtroCalendarioVO", filtroCalendario);
			}
			/**
			 * Se llena la lista con los meses del ano
			 * Por defecto se selecciona el mes actual
			 */	
			request.setAttribute("listaMeses", getMeses());
			
		} catch (Exception e) {
			request.setAttribute(ParamsVO.SMS,"Ocurrio un problema realizando la transaccinn");
			e.printStackTrace();
		}
	}
	
	/**
	 * Retorna una lista con todos los meses del ano
	 * se incluye la opcion de "TODOS"
	 */
	public List getMeses() throws Exception{
		ItemVO item=null;
		List l=new ArrayList();
		try{
			item=new ItemVO();
			item.setCodigo(1);
			item.setNombre("ENERO");
			l.add(item);
			
			item=new ItemVO();
			item.setCodigo(2);
			item.setNombre("FEBRERO");
			l.add(item);
			
			item=new ItemVO();
			item.setCodigo(3);
			item.setNombre("MARZO");
			l.add(item);
			
			item=new ItemVO();
			item.setCodigo(4);
			item.setNombre("ABRIL");
			l.add(item);
			
			item=new ItemVO();
			item.setCodigo(5);
			item.setNombre("MAYO");
			l.add(item);
			
			item=new ItemVO();
			item.setCodigo(6);
			item.setNombre("JUNIO");
			l.add(item);
			
			item=new ItemVO();
			item.setCodigo(7);
			item.setNombre("JULIO");
			l.add(item);
			
			item=new ItemVO();
			item.setCodigo(8);
			item.setNombre("AGOSTO");
			l.add(item);
			
			item=new ItemVO();
			item.setCodigo(9);
			item.setNombre("SEPTIEMBRE");
			l.add(item);
			
			item=new ItemVO();
			item.setCodigo(10);
			item.setNombre("OCTUBRE");
			l.add(item);
		
			item=new ItemVO();
			item.setCodigo(11);
			item.setNombre("NOVIEMBRE");
			l.add(item);
			
			item=new ItemVO();
			item.setCodigo(12);
			item.setNombre("DICIEMBRE");
			l.add(item);
			
			item=new ItemVO();
			item.setCodigo(13);
			item.setNombre("TODOS");
			l.add(item);
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}
		return l;
	}

	private void calendarioNuevo(HttpServletRequest request,
			HttpSession session, CalendarioVO calendario) throws ServletException {
		try{
			calendario = new CalendarioVO();
			calendario.setEstado(1); //Nuevo registro
			session.setAttribute("calendarioVO", calendario);
			request.setAttribute("listaCalendario", null);
		}catch(Exception e){
			throw new ServletException("Errror interno: " + e.getMessage());
		}
		
	}
	
	
	
/**
*	Redirige el control a otro servlet
*	@param	int a: 1=redirigir como 'include', 2=redirigir como 'forward'
*	@param	String s
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
	public void ir(int a,String s,HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException{
		RequestDispatcher rd=getServletContext().getRequestDispatcher(s);
		if(a==1) rd.include(rq,rs);
		else rd.forward(rq,rs);
	}
	/*
	*Cierra cursor
	*
	*/
		public void destroy(){
			if(cursor!=null)cursor.cerrar();
			cursor=null;
			util=null;
		}
	
 /**
*	Concatena el parametro en una variable que contiene los mensajes que se van a enviar a la vista
*	@param	String s
**/
	public String setMensaje(String s){
		String mensaje="VERIFIQUE LA SIGUIENTE información: \n\n";
		mensaje+="  - "+s+"\n";
		return mensaje;
	}	
}