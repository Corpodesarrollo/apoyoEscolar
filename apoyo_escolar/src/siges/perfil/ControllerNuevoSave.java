package siges.perfil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import siges.login.beans.Login;
import siges.perfil.beans.Perfil;
import siges.perfil.dao.*;
import siges.util.Recursos;
import siges.dao.*;
import siges.util.Logger;

/**
*	Nombre:	ControllerNuevoSave<br>
*	Descripcinn:	Guardar, Editar o Eliminar información del Perfil<br>
*	Funciones de la pngina:	Controla la vista del formulario para editar, guardar o eliminar<br>
*	Entidades afectadas:	Perfil<br>
*	Fecha de modificacinn:	20/07/2005 <br>
*	@author Latined <br>
*	@version v 1.0 <br>
*/
public class ControllerNuevoSave extends HttpServlet{
	
	private Perfil perfil, perfil2;//	private Personal personal,personal2;
	private Cursor cursor;//objeto que maneja las sentencias sql
	private Util util;
	private PerfilDAO perfDAO;
	private Login login;
	private String mensaje;//mensaje en caso de error
	private boolean band;
	private boolean err;//variable que inidica si hay o no errores en la validacion de los datos del formulario
	private int tipo;
	private String sig, sig1;//nombre de la pagina a la que ira despues de ejecutar los comandos de esta
	private String ant;//pagina a la que ira en caso de que no se pueda procesar esta pagina
	private String er;//nombre de la pagina a la que ira si hay errores
	private String home;//nombre de la pagina a la que ira si hay errores
	private String perf;
	private HttpSession session;
	
	/**
	*	Funcinn: Recibe la peticion por el metodo Post de HTTP
	*	@param	HttpServletRequest request
	*	@param	HttpServletResponse response
	**/
		public String process (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

			session = request.getSession();
			String boton;
			String buscar=null;		
			String respuesta="";

			sig="/perfil/NuevoPerfil.jsp";
			sig1="/perfil/NuevoPerfil.jsp";
			ant="/perfil/ControllerPerfilNuevoEdit.do";//pagina a la que se dara el control si algo falla
			er="/error.jsp";
			home="/bienvenida.jsp";
			err=false;
			band=true;
			mensaje=null;
			respuesta="La información fue ingresada satisfactoriamente ";
			cursor=new Cursor();
			
			try{
				util=new Util(cursor);
				perfDAO=new PerfilDAO(cursor);
				boton=(request.getParameter("cmd")!=null)?request.getParameter("cmd"):new String("Cancelar");
				if(boton.equals("Cancelar")){
					borrarBeans(request);
					return home;
		    }
				if(boton.equals("Guardar")){
					if(request.getParameter("tipo")==null || request.getParameter("tipo").equals("")){
						setMensaje("Acceso denegado no hay una ficha definida");
						request.setAttribute("mensaje",getMensaje());
						return er;
					}
					tipo=Integer.parseInt((String)request.getParameter("tipo"));
					asignarBeans(request);
					if(Integer.parseInt(request.getParameter("perfcodigo"))<Integer.parseInt(login.getPerfil())){
						setMensaje("No tiene autorizacion para crear este Perfil");
						request.setAttribute("mensaje",getMensaje());
						return er;
					}
	
					switch(tipo){
						case 1:
								if(perfil.getEstado().equals("1")){
									actualizarRegistro(request);
								}
								else{
									insertarRegistro(request);
								}
								Recursos.setRecurso(Recursos.PERFIL);
								request.setAttribute("mensaje",getMensaje());
								return (ant+="?tipo="+tipo);
						case 2:
								insertarRegistro(request);
								request.setAttribute("mensaje",getMensaje());
								return (ant+="?tipo="+tipo);
					}
				}
	
				if(boton.equals("Editar")){
					perf=request.getParameter("idper");
		  		request.setAttribute("perfil",perf);
		  		borrarBeans(request);
					request.setAttribute("tipo","1");
					session.setAttribute("editar","1");
					Perfil Perfil=null;
					perfil=perfDAO.asignarPerfil(perf);
					if(perfil!=null){
						session.setAttribute("nuevoPerfil",perfil);
						session.setAttribute("nuevoPerfil2",perfil.clone());
					}
					else{
						setMensaje(perfDAO.getMensaje());
						request.setAttribute("mensaje",mensaje);	
					}
					return sig1;
				}
				
				return er;
			}catch(Exception e){
				System.out.println("Error "+this+": "+e.toString());
				throw new	ServletException(e);
			}finally{
				if(cursor!=null)cursor.cerrar();if(util!=null)util.cerrar();
			}
		}
		
		public void actualizarRegistro(HttpServletRequest request) throws ServletException, IOException{
			switch(tipo){
			case 1:
				if(compararFichas()){
					setMensaje("La información fue actualizada satisfactoriamente -");
					Logger.print(login.getUsuarioId(),"Actualizacinn del Perfil: "+perf,7,1,this.toString());
					return;
				}
				if(!perfDAO.actualizar(perfil)){
					setMensaje(perfDAO.getMensaje());
					restaurarBeans(request);
					return;
				}
				setMensaje("La información fue actualizada satisfactoriamente");
				recargarBeans(request);
				break;
			}
		}
		
		public boolean compararFichas(){
			switch(tipo){
				case 1: return perfDAO.compararBeans(perfil,perfil2);
			}
			return true;		
		}
		
		public void insertarRegistro(HttpServletRequest request) throws ServletException, IOException{
			String[] resul;
			switch(tipo){
			case 1:
				if(validarExistencia(perfil.getPerfcodigo())){
					setMensaje("El Nnmero de identificacinn es de un personal que ya esta registrado, no se puede registrar");
					return;
				}
				if(!perfDAO.insertar(perfil)){
					setMensaje(perfDAO.getMensaje());
					return;
				}
				session.setAttribute("nuevoPerfil2",(Perfil)perfil.clone());
				setMensaje("La información fue ingresada satisfactoriamente1");
				Logger.print(login.getUsuarioId(),"Nuevo Perfil: "+perfil.getPerfcodigo(),6,1,this.toString());
			}		
		}

		/**
		* Funcinn: Elimina del contexto de la sesion los beans de informacion del usuario
		* @param HttpServletRequest request
		*/
		public void borrarBeans(HttpServletRequest request) throws ServletException, IOException{
			request.getSession().removeAttribute("nuevoPerfil");
			request.getSession().removeAttribute("nuevoPerfil2");		
		}

		/**
		*	Funcinn: Inserta los valores por defecto para el bean de información bnsica con respecto a la informacion de la institucinn educativa
		*	@param HttpServletRequest request
		*	@return boolean 
		*/
		public boolean asignarBeans(HttpServletRequest request)throws ServletException, IOException{ 
			perfil=(Perfil)request.getSession().getAttribute("nuevoPerfil");
			perfil2=(Perfil)request.getSession().getAttribute("nuevoPerfil2");	
			if(request.getSession().getAttribute("login")!=null){
				login=(Login)request.getSession().getAttribute("login");
				return true;
			}
			return true;
		}
		
		/**
		*	Funcinn: Busca en la base de datos un registro que coincida con el id ingresado por el usuario
		*	@return boolean 
		*/
		public boolean validarExistencia(String cod){	
			String sentencia="Select * from perfil where perfcodigo = "+cod;
			if(cursor.existe(sentencia)){
				return true;//si existe
			}
	  	return false;
	  }

		/**
		*	Funcinn: Referencia al bean del usuario con la información proporcionada por el bean de respaldo
		*	@param int n
		*	@param HttpServletRequest request
		*/
		public void restaurarBeans(HttpServletRequest request) throws ServletException, IOException{
			switch(tipo){
			case 1: 
				session.setAttribute("nuevoPerfil",(Perfil)perfil2.clone());
				break;
			}
		}
		
		/**
		*	Funcinn: Referencia al bean de respaldo con la nueva información proporcionada por el bean modificado por el usuario
		*	@param int n
		*	@param HttpServletRequest request
		*/
		public void recargarBeans(HttpServletRequest request) throws ServletException, IOException{
			switch(tipo){
			case 1:
				session.setAttribute("nuevoPerfil2",(Perfil)perfil.clone());
				break;
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
		  	ir(1,s,request,response);
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
			band=false;
			if (!err){
				err=true;
				mensaje="VERIFIQUE LA SIGUIENTE INFORMACION: \n\n";
			}	
			mensaje+="  - "+s+"\n";
		}

		/**
		*	Funcinn: Retorna una variable que contiene los mensajes que se van a enviar a la vista
		*	@return String	
		**/
		public String getMensaje(){
			return mensaje;
		}

}