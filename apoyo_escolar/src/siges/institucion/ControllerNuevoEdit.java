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
import siges.exceptions.InternalErrorException;
import siges.institucion.beans.*;
import siges.institucion.dao.InstitucionDAO;
import siges.login.beans.*;
import siges.util.Properties;

/**
*	Nombre:	
*	Descripcion:	
*	Parametro de entrada:	HttpServletRequest request, HttpServletResponse response
*	Parametro de salida:	HttpServletRequest request, HttpServletResponse response
*	Funciones de la pagina:	
*	Entidades afectadas:	
*	Fecha de modificacinn:	
*	@author 
*	@version 
*/
public class ControllerNuevoEdit extends HttpServlet{
	private String mensaje;//mensaje en caso de error
	private boolean err;//variable que inidica si hay o no errores en la validacion de los datos del formulario
	private Cursor cursor;//objeto que maneja las sentencias sql
	private InstitucionDAO institucionDAO;//
	private ResourceBundle rb;
	private Collection list;
	private Object[] o;

/**
*	Procesa la peticion HTTP
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
	public String process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
	  HttpSession session = request.getSession();
		rb=ResourceBundle.getBundle("siges.institucion.bundle.institucion");
		String ant;//pagina a la que ira en caso de que no se pueda procesar esta pagina
		String er;//nombre de la pagina a la que ira si hay errores
		String home;//nombre de la pagina a la que ira si hay errores
		int tipo;
		String sig="/institucion/NuevoInstitucion.jsp";
		String sig2="/institucion/NuevoSede.jsp";
		String sig3="/institucion/NuevoEspacio.jsp";
		String sig4="/institucion/NuevoSimbolo.jsp";
		String sig6="/institucion/NuevoTransporte.jsp";
		String sig7="/institucion/NuevoCafeteria.jsp";
		String sig8="/institucion/FiltroConflicto.jsp";
		cursor=new Cursor();
		ant="/index.jsp";
		er="/error.jsp";
		home="/bienvenida.jsp";
		err=false;
		mensaje=null;
		try{
	    institucionDAO=new InstitucionDAO(cursor);
		if(!asignarBeans(request)){
			setMensaje("Error capturando datos de sesinn para el usuario");
			request.setAttribute("mensaje",mensaje);
			return(er);
		}
		Login login=(Login)session.getAttribute("login");
		Institucion institucion=(Institucion)session.getAttribute("nuevoInstitucion2");
		
		if(request.getParameter("tipo2")==null || ((String)request.getParameter("tipo2")).equals("")){
			borrarBeans(request,session);
			session.removeAttribute("editar2");
			session.removeAttribute("subframe2");
			tipo=1;
		}else
			tipo=Integer.parseInt((String)request.getParameter("tipo2"));
		switch(tipo){
			case 1:
				editarJornada(request,session,institucion);
				editarNivel(request,session,institucion);
			break;	
			case 2:
				editarSede(request,session,institucion);
				sig=sig2;
			break;	
			case 3:
				editarEspacio(request,session,institucion);
				sig=sig3;
			break;	
			case 4:
				editarSimbolo(request,session,institucion);
				sig=sig4;
			break;	
			case 6:
				editarTransporte(request,session,institucion);
				sig=sig6;
			break;	
			case 7:
				editarCafeteria(request,session,institucion);
				sig=sig7;
			break;
			case 8:
			    cargarConflicto(request,session,institucion);
			    sig=sig8;
				break;
		}
		if(mensaje!=null){
			request.setAttribute("mensaje",mensaje);
		}
		if(err){
			return er;
		}	
		return sig;
		}catch(Exception e){
				System.out.println("Error "+this+": "+e.toString());
				throw new	ServletException(e);
			}finally{
			  if(cursor!=null)cursor.cerrar();if(institucionDAO!=null)institucionDAO.cerrar();
			}
	}
	
/**
 * Elimina del contexto de la sesion los beans de informacion del usuario
 *	@param HttpServletRequest request
 */
	public void borrarBeans(HttpServletRequest request,HttpSession session) throws ServletException, IOException{
		session.removeAttribute("nuevoInstitucion");
		session.removeAttribute("nuevoInstitucion2");
		session.removeAttribute("nuevoJornada");
		session.removeAttribute("nuevoJornada2");
		session.removeAttribute("nuevoSede");
		session.removeAttribute("nuevoSede2");
		session.removeAttribute("nuevoEspacio");
		session.removeAttribute("nuevoEspacio2");
		session.removeAttribute("nuevoNivel");
		session.removeAttribute("nuevoNivel2");
		session.removeAttribute("nuevoGobierno");
		session.removeAttribute("nuevoGobierno2");
		session.removeAttribute("nuevoCafeteria");
		session.removeAttribute("nuevoCafeteria2");
		session.removeAttribute("filtroSedes");
		session.removeAttribute("filtroGobiernos");
		session.removeAttribute("filtroEspacios");
		session.removeAttribute("filtroTransportes");
	}

/**
 *	Inserta los valores por defecto para el bean de información bnsica con respecto a la informacion de la institucinn educativa
 *	@param HttpServletRequest request
 *	@return boolean 
 */
	public boolean asignarBeans(HttpServletRequest request)throws ServletException, IOException{ 
	    HttpSession session = request.getSession();
	    if(session.getAttribute("login")==null || session.getAttribute("nuevoInstitucion2")==null)
		    return false;
	    return true;
	}		

/**
 *	trae de la base de datos los registros de las tablas maestras dependiendo del tipo de 
 *	información que se desee mostrar de la tabla de información basica
 *	@param HttpServletRequest request
 */
	public void editarJornada(HttpServletRequest request,HttpSession session,Institucion institucion) throws ServletException, IOException{
		Jornada jornada=(Jornada)session.getAttribute("nuevoJornada");
		if(institucion!=null && !institucion.getInscodigo().equals("")){
			if(jornada==null){
				jornada=institucionDAO.asignarJornada(institucion.getInscodigo());
				session.setAttribute("nuevoJornada",jornada);
			}
		}
	}
	
	public void cargarConflicto(HttpServletRequest request,HttpSession session,Institucion institucion) throws ServletException, IOException{
	    try{
	        int z=0;
	  		    list = new ArrayList();
	  		    o=new Object[2];
	  		    o[z++]=new Integer(java.sql.Types.BIGINT);
	  		    o[z++]=institucion.getInscodigo();
	  		    list.add(o);
	  		    session.setAttribute("filtroSedeJerarquia",institucionDAO.getFiltro(rb.getString("filtroSedeJerarquia"),list));
	        /*int z=0;
	        list = new ArrayList();
	        o=new Object[2];
	        o[z++]=new Integer(java.sql.Types.BIGINT);
	    				o[z++]=institucion.getInscodigo();
	    				list.add(o);
	    				request.setAttribute("tipoConflicto",institucionDAO.getFiltro("SELECT IDTIPO, VALORTIPO, CATEGORIA FROM CONFLICTO_TIPO",list));
	    				
	    				z=0;
	        list = new ArrayList();
	        o=new Object[2];
	        o[z++]=new Integer(java.sql.Types.BIGINT);
	    				o[z++]=institucion.getInscodigo();
	    				list.add(o);
	    				request.setAttribute("itemConflicto",institucionDAO.getFiltro("SELECT IDITEM, IDTIPO, NOMBREITEM FROM CONFLICTO_ITEM",list));*/
	    }catch(Throwable th){
	     			throw new	ServletException(th);
	  		}
	}

/**
 *	trae de la base de datos los registros de las tablas maestras dependiendo del tipo de 
 *	información que se desee mostrar de la tabla de información basica
 *	@param HttpServletRequest request
 */
	public void editarSede(HttpServletRequest request,HttpSession session,Institucion institucion) throws ServletException, IOException{
		String boton=(request.getParameter("cmd")!=null)?request.getParameter("cmd"):new String("");
		String r=(request.getParameter("r")!=null)?request.getParameter("r"):new String("");
		String codsede=request.getParameter("danesede_");
		String codinst=request.getParameter("daneinst_");
		String nomsede=request.getParameter("nombresede_");
		int z=0;
		try{		
			if(boton.equals("Nuevo")){
				session.removeAttribute("nuevoSede");
				session.removeAttribute("nuevoSede2");
	    }			
			if(boton.equals("Eliminar") && institucion!=null){
				if(institucionDAO.eliminarSede(institucion.getInscodigo(),r)){
					session.removeAttribute("nuevoSede");
					session.removeAttribute("nuevoSede2");
				}else
					setMensaje(institucionDAO.getMensaje());
	    }
			if(institucion!=null && !institucion.getInscodigo().equals("")){
		    z=0;
		    list = new ArrayList();
		    o=new Object[2];
				o[z++]=new Integer(java.sql.Types.BIGINT);
				o[z++]=institucion.getInscodigo();
				list.add(o);
				//SEDES QUE YA ESTAN
				 	session.setAttribute("filtroSedes",institucionDAO.getFiltro(rb.getString("filtroSedesInstitucion"),list));
				//JORNADAS DE ESA INSTITUCION
				 	session.setAttribute("filtroJornadasInstitucion",institucionDAO.getFiltro(rb.getString("filtroJornadasInstitucion"),list));
				//NIVELES DE ESA INSTITUCION
				 	session.setAttribute("filtroNivelesInstitucion",institucionDAO.getFiltro(rb.getString("filtroNivelesInstitucion"),list));
			}else{
				Collection co=null;
				session.setAttribute("filtroSedes",co);
			}
			if(boton.equals("Editar") && institucion!=null){
				Sede sede=new Sede();
				Sede temp=new Sede();
				temp=institucionDAO.asignarSedeJornada(temp,institucion.getInscodigo(),r);
				cursor=new Cursor();
				if(!cursor.abrir(2)){
					request.setAttribute("mensaje",mensaje);
					sede.setSedcodigo(r);
					sede.setSedcodins(institucion.getInscodigo());
					sede.setSednombre(nomsede);
					sede.setSedcoddaneanterior(codsede);
					sede.setEstado("1");
					session.setAttribute("nuevoSede",sede);
					session.setAttribute("nuevoSede2",sede.clone());
					return;
				}
				institucionDAO=new InstitucionDAO(cursor);
				sede=institucionDAO.asignarSede(institucion.getInscodigo(),r,codinst,codsede);
				if(sede!=null){
					sede.setSedjorcodjor(temp.getSedjorcodjor());
					sede.setSedjornivcodnivel(temp.getSedjornivcodnivel());
					sede.setEstado("1");
					session.setAttribute("nuevoSede",sede);
					session.setAttribute("nuevoSede2",sede.clone());
				}else{
					sede=new Sede();
					sede.setSedcodigo(r);
					sede.setSedcodins(institucion.getInscodigo());
					sede.setSednombre(nomsede);
					sede.setSedcoddaneanterior(codsede);
					sede.setSedjorcodjor(temp.getSedjorcodjor());
					sede.setSedjornivcodnivel(temp.getSedjornivcodnivel());
					sede.setEstado("1");
					session.setAttribute("nuevoSede",sede);
					session.setAttribute("nuevoSede2",sede.clone());
					return;
				}
	    }
		}catch(Throwable th){
			throw new	ServletException(th);
		}
	}

/**
 *	trae de la base de datos los registros de las tablas maestras dependiendo del tipo de 
 *	informacion que se desee mostrar de la tabla de informacion educativa
 *	@param HttpServletRequest request
 */
	public void editarTransporte(HttpServletRequest request,HttpSession session,Institucion institucion) throws ServletException, IOException{
		String a;
		int z=0;
		String boton=(request.getParameter("cmd")!=null)?request.getParameter("cmd"):new String("");
		String r=(request.getParameter("r")!=null)?request.getParameter("r"):new String("");
		String s=(request.getParameter("s")!=null)?request.getParameter("s"):new String("");
		if(boton.equals("Editar") && institucion!=null){
			Transporte transporte=institucionDAO.asignarTransporte(s,r);
			if(transporte!=null){
				transporte.setEstado("1");
				session.setAttribute("nuevoTransporte",transporte);
				session.setAttribute("nuevoTransporte2",transporte.clone());
			}else
				setMensaje(institucionDAO.getMensaje());
    }
		if(boton.equals("Nuevo")){
			session.removeAttribute("nuevoTransporte");
			session.removeAttribute("nuevoTransporte2");
    }
		if(boton.equals("Eliminar") && institucion!=null){
			if(institucionDAO.eliminarTransporte(s,r)){
				session.removeAttribute("nuevoTransporte");
				session.removeAttribute("nuevoTransporte2");
			}else
				setMensaje(institucionDAO.getMensaje());
    }
		try{
			if(institucion!=null  && !institucion.getInscodigo().equals("")){
		    z=0;
		    list = new ArrayList();
		    o=new Object[2];
				o[z++]=new Integer(java.sql.Types.BIGINT);
				o[z++]=institucion.getInscodigo();
				list.add(o);
			 	session.setAttribute("filtroTransportes",institucionDAO.getFiltro(rb.getString("filtroTransportesInstitucion"),list));
			 	session.setAttribute("filtroSedeJerarquia",institucionDAO.getFiltro(rb.getString("filtroSedeJerarquia"),list));
			}else{
				Collection co=null;
				session.setAttribute("filtroEspacios",co);
			}
		}catch(Throwable th){
			throw new	ServletException(th);
		}
	}

/**
 *	trae de la base de datos los registros de las tablas maestras dependiendo del tipo de 
 *	informacion que se desee mostrar de la tabla de informacion educativa
 *	@param HttpServletRequest request
 */
	public void editarCafeteria(HttpServletRequest request,HttpSession session,Institucion institucion) throws ServletException, IOException{
		String a;
		int z=0;
		String boton=(request.getParameter("cmd")!=null)?request.getParameter("cmd"):new String("");
		String r=(request.getParameter("r")!=null)?request.getParameter("r"):new String("");
		if(boton.equals("Editar") && institucion!=null){
			Cafeteria cafeteria=institucionDAO.asignarCafeteria(institucion.getInscodigo(),r);
			if(cafeteria!=null){
				cafeteria.setEstado("1");
				session.setAttribute("nuevoCafeteria",cafeteria);
				session.setAttribute("nuevoCafeteria2",cafeteria.clone());
			}else
				setMensaje(institucionDAO.getMensaje());
    }
		if(boton.equals("Nuevo")){
			session.removeAttribute("nuevoCafeteria");
			session.removeAttribute("nuevoCafeteria2");
			session.removeAttribute("filtroCafeterias");
    }
		if(boton.equals("Eliminar") && institucion!=null){
			if(institucionDAO.eliminarCafeteria(institucion.getInscodigo(),r)){
			session.removeAttribute("nuevoCafeteria");
			session.removeAttribute("nuevoCafeteria2");
			}else
				setMensaje(institucionDAO.getMensaje());
    }
		try{
			if(institucion!=null  && !institucion.getInscodigo().equals("")){
		    z=0;
		    list = new ArrayList();
		    o=new Object[2];
				o[z++]=new Integer(java.sql.Types.BIGINT);
				o[z++]=institucion.getInscodigo();
				list.add(o);
			 	session.setAttribute("filtroCafeterias",institucionDAO.getFiltro(rb.getString("filtroCafeteriaInstitucion"),list));
			}else{
				Collection co=null;
				session.setAttribute("filtroCafeterias",co);
			}
		}catch(Throwable th){
			throw new	ServletException(th);
		}
	}

/**
 *	trae de la base de datos los registros de las tablas maestras dependiendo del tipo de 
 *	informacion que se desee mostrar de la tabla de informacion educativa
 *	@param HttpServletRequest request
 */
	public void editarEspacio(HttpServletRequest request,HttpSession session,Institucion institucion) throws ServletException, IOException{
		String a;
		int z=0;
		String boton=(request.getParameter("cmd")!=null)?request.getParameter("cmd"):new String("");
		String r=(request.getParameter("r")!=null)?request.getParameter("r"):new String("");
		String s=(request.getParameter("s")!=null)?request.getParameter("s"):new String("");
		if(boton.equals("Editar") && institucion!=null){
			Espacio espacio=institucionDAO.asignarEspacio(institucion.getInscodigo(),s,r);
			if(espacio!=null){
				espacio.setEstado("1");
				session.setAttribute("nuevoEspacio",espacio);
				session.setAttribute("nuevoEspacio2",espacio.clone());
			}else
				setMensaje(institucionDAO.getMensaje());
    }
		if(boton.equals("Nuevo")){
			session.removeAttribute("nuevoEspacio");
			session.removeAttribute("nuevoEspacio2");
    }
		if(boton.equals("Eliminar") && institucion!=null){
			if(institucionDAO.eliminarEspacio(institucion.getInscodigo(),s,r)){
				session.removeAttribute("nuevoEspacio");
				session.removeAttribute("nuevoEspacio2");
				setMensaje("La información fue eliminada satisfactoriamente");
				err=false;
			}else
				setMensaje(institucionDAO.getMensaje());
    }
		try{
			if(institucion!=null  && !institucion.getInscodigo().equals("")){
		    z=0;
		    list = new ArrayList();
		    o=new Object[2];
				o[z++]=Properties.ENTEROLARGO;
				o[z++]=institucion.getInscodigo();
				list.add(o);
			 	session.setAttribute("filtroEspacios",institucionDAO.getFiltro(rb.getString("filtroEspaciosInstitucion"),list));
			 	session.setAttribute("filtroSede",institucionDAO.getFiltro(rb.getString("filtroSedesEspaciosInstitucion"),list));
			 	session.setAttribute("filtroJornadaSede",institucionDAO.getFiltro(rb.getString("filtroJornadaSede"),list));
			 	session.setAttribute("filtroJornadasInstitucion",institucionDAO.getFiltro(rb.getString("filtroJornadasInstitucion"),list));
			}else{
				Collection co=null;
				session.setAttribute("filtroEspacios",co);
			}
		}catch(Exception th){
			throw new	ServletException(th);
		}
	}

/**
 *	trae de la base de datos los registros de las tablas maestras dependiendo del tipo de 
 *	informacion que se desee mostrar de la tabla de informacion educativa
 *	@param HttpServletRequest request
 */
	public void editarNivel(HttpServletRequest request,HttpSession session,Institucion institucion) throws ServletException, IOException{
		Nivel nivel=(Nivel)session.getAttribute("nuevoNivel");
		if(institucion!=null && !institucion.getInscodigo().equals("")){			
			if(nivel==null){
				nivel=institucionDAO.asignarNivel(institucion.getInscodigo());
				session.setAttribute("nuevoNivel",nivel);
			}
		}
	}

/**
 *	trae de la base de datos los registros de las tablas maestras dependiendo del tipo de 
 *	información que se desee mostrar de la tabla de información basica
 *	@param HttpServletRequest request
 */
	public void editarSimbolo(HttpServletRequest request,HttpSession session,Institucion institucion) throws ServletException, IOException{
		if(institucion!=null  && !institucion.getInscodigo().equals("")){
			ResourceBundle rb=ResourceBundle.getBundle("path",request.getLocale());
			String pathSubirDefault=rb.getString("path.subirDefault");
			String pathSubirBandera=rb.getString("path.subirBandera");
			String pathSubirEscudo=rb.getString("path.subirEscudo");
			if(!institucion.getInsbandera().equals(""))
				request.setAttribute("bandera",pathSubirBandera.replace('.','/')+"/"+institucion.getInsbandera());
			else
				request.setAttribute("bandera",pathSubirDefault.replace('.','/')+"/default.jpg");
			if(!institucion.getInsescudo().equals(""))
				request.setAttribute("escudo",pathSubirEscudo.replace('.','/')+"/"+institucion.getInsescudo());
			else
				request.setAttribute("escudo",pathSubirDefault.replace('.','/')+"/default.jpg");
		}
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