package siges.perfil;

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
import siges.perfil.beans.*;
import siges.perfil.dao.*;
import siges.util.Recursos;
import siges.exceptions.InternalErrorException;
import siges.util.Logger;


/**
*	Nombre:	ControllerNuevoEdit <br>
*	Descripcinn:	Nuevo y Editar Perfil <br>
*	Funciones de la pngina:	Controla la vista del formulario nuevo y edicinn de Perfiles de usuario <br>
*	Entidades afectadas: Perfil	 <br>
*	Fecha de modificacinn:	20/07/2005 <br>
*	@author Latined <br>
*	@version v 1.0 <br>
*/
public class ControllerNuevoEdit extends HttpServlet{

  private String mensaje;
  private String buscar, consulta,perf;
  private boolean error;
  private Cursor cursor;
  private Perfil perfil;
  private Util util;
  private PerfilDAO perfDAO;
  private Login login;
  private HttpSession session;
  private String sig;
  private Collection col, list;
  private Object o[];
  private ResourceBundle rbPerfil;
	private Integer cadena=new Integer(java.sql.Types.VARCHAR);
	private Integer entero=new Integer(java.sql.Types.INTEGER);
	private Integer fecha=new Integer(java.sql.Types.DATE);
	private Integer nulo=new Integer(java.sql.Types.NULL);
	private Integer doble=new Integer(java.sql.Types.DOUBLE);
	private Integer caracter=new Integer(java.sql.Types.CHAR);
	private Integer enterolargo=new Integer(java.sql.Types.BIGINT);
  
  /**
  *	Funcinn: Procesa la peticion HTTP <br>
  *	@param	HttpServletRequest request
  *	@param	HttpServletResponse response
  **/
  public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  	
  	session=request.getSession();
  	rbPerfil=ResourceBundle.getBundle("perfil");
  	String p_inte=this.getServletContext().getInitParameter("integrador");
    String p_home=this.getServletContext().getInitParameter("home");
    String p_login=this.getServletContext().getInitParameter("login");
    String p_error=this.getServletContext().getInitParameter("er");      
    String sig="/perfil/FiltroResultado.jsp";
    String sig1="/perfil/NuevoPerfil.jsp";
    String sig3="bienvenida.jsp";
    String sig4="/perfil/NuevoGuardar.jsp";
    int tipo;
    cursor=new Cursor();
    error=false;
    mensaje=null;
		try{

	    util=new Util(cursor);
	    perfDAO=new PerfilDAO(cursor);
	    if(request.getParameter("tipo")==null || ((String)request.getParameter("tipo")).equals("")){
		    borrarBeans(request);
		    session.removeAttribute("editar");
		    tipo=0;
	    }
	    else{
	    	tipo=Integer.parseInt((String)request.getParameter("tipo"));
	    }
	    if(!asignarBeans(request)){
		    this.setMensaje("Error capturando datos de sesinn para el usuario");
		    request.setAttribute("mensaje",mensaje);
		    return p_error;
	    }
    
    switch(tipo){
    case 0:
    	//filtro
    	return sig;
    case 1:
			//nuevo
    	perfil.setEstado("0");
		return sig1;
		case 3:
			//eliminar
			perf=request.getParameter("idper");
			if(Integer.parseInt(login.getPerfil())<Integer.parseInt(perf)){
				if(validarServicioPerfil(perf)){
					setMensaje("Este perfil tiene permisos asignados, desasignelos e intente nuevamente");
				}
				else if(validarUsuarioPerfil(perf)){
					setMensaje("Este perfil esta asociado a un usuario(s), desasignelo e intente nuevamente");
				}
				else{
					perfDAO.eliminarPerfil(perf);
					setMensaje("Operacion satisfecha.");
					Logger.print(login.getUsuarioId(),"Eliminacion de perfil: "+perf,8,1,this.toString());
				}
				Recursos.setRecurso(Recursos.PERFIL);
			}else{
				setMensaje("No tiene permisos para eliminar este usuario");
			}

			request.setAttribute("mensaje",getMensaje());
		return sig;
		case 4:
			//editar
			
			perf=request.getParameter("idper");
			//System.out.println(login.getPerfil()+"perfil: "+perf);
			if(Integer.parseInt(login.getPerfil())<Integer.parseInt(perf)){
				sig=sig4;
			}
			else{
				setMensaje("No tiene permisos para editar este usuario");
				request.setAttribute("mensaje",getMensaje());
			}
			//System.out.println("sig4: "+sig);
		return sig;
    }

    if(error){
    	System.out.println("error");
    	request.setAttribute("mensaje",mensaje);
    	return p_error;
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
*	Funcinn: Validar la existencia de un perfil en la entidad Servicio_Perfil <br>
*	@param	String perf
*	@return	boolean
**/
public boolean validarServicioPerfil(String perf){
  	String val="select serperfperfcodigo from servicio_perfil where serperfperfcodigo='"+perf+"'";
  	if(cursor.existe(val))
  		return true;
  	else
  		return false;
  }


  /**
*	Funcinn: Validar el codigo de perfil en la entidad Usuario<br>
*	@param	String perf
*	@return	boolean
**/
public boolean validarUsuarioPerfil(String perf){
  	String val="select * from usuario where usuperfcodigo='"+perf+"'";
  	if(cursor.existe(val))
  		return true;
  	else
  		return false;
  }
  
  /**
  * Funcinn: Elimina del contexto de la sesion los beans de informacion del usuario
  *	@param HttpServletRequest request
  */
    	public void borrarBeans(HttpServletRequest request) throws ServletException, IOException{
    			session.removeAttribute("nuevoPerfil");
    	}

  	/**
  	*	Funcinn: Inserta los valores por defecto para el bean de información bnsica con respecto a la informacion de la institucinn educativa
  	*	@param HttpServletRequest request
  	*	@return boolean 
  	*/
    	public boolean asignarBeans(HttpServletRequest request)throws ServletException, IOException{ 
    		perfil=(Perfil)request.getSession().getAttribute("nuevoPerfil");
    		if(request.getSession().getAttribute("login")!=null){
    			login=(Login)request.getSession().getAttribute("login");
    			return true;
    		}
    		return true;
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
  *	Funcinn: Concatena el parametro en una variable que contiennne los mensajes que se van a enviar a la vista
  *	@param	String s
  **/
  	public void setMensaje(String s){
  		if (!error){
  			error=true;
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
