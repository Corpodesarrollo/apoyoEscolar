package siges.conflictoCerrar;

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
import siges.conflictoGrupo.dao.*;
import siges.conflictoGrupo.beans.*;
import siges.login.beans.*;
import siges.util.Properties;


/**
 * @author Administrador
 *
 * Ventana - Preferencias - Java - Estilo de cndigo - Plantillas de cndigo
 */
public class ControllerNuevo extends HttpServlet{
    
    private String mensaje;//mensaje en caso de error
   	private String buscar;
   	private boolean err;//variable que inidica si hay o no errores en la validacion de los datos del formulario
   	private Cursor cursor;//objeto que maneja las sentencias sql
   	private ConflictoGrupoDAO conflictoDAO;
   	private ConflictoFiltro cf;
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
   		  rb=ResourceBundle.getBundle("conflictoCerrar");
   		  String sig="/conflictocerrar/AbrirGrupo.jsp";
   		  String p_inte=this.getServletContext().getInitParameter("integrador");
	    		String p_home=this.getServletContext().getInitParameter("home");
	    		String p_login=this.getServletContext().getInitParameter("login");
	    		String p_error=this.getServletContext().getInitParameter("er");
	    		cursor=new Cursor();
	    		int tipo;
	    		try{
	    		    Login login=(Login)session.getAttribute("login");

	    		    if(!asignarBeans(request)){
	    		     			setMensaje("Error capturando datos de sesinn para el usuario");
	    		     			request.setAttribute("mensaje",mensaje);
	    		     			return(p_error);
	    		    }
	    		    if(request.getParameter("tipo")==null || ((String)request.getParameter("tipo")).equals("")){
	    		        borrarBeans(request);
	    		        tipo=0;
	    		    }
	    		    else{
	    		        tipo=Integer.parseInt((String)request.getParameter("tipo"));
	    		    }
	    		    switch(tipo){
	    		    //filtro
	    		    case 0:
	    		        
	    		    break;
	    		    }
	    		    return sig;
	    		}
	    		catch(Exception e){
	    		    e.printStackTrace();
	    		    System.out.println("Error "+this+": "+e.toString());
	    		    throw new	ServletException(e);
	    		}finally{
	    		    if(cursor!=null)cursor.cerrar();
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
   		 *	Inserta los valores por defecto para el bean de información bnsica con respecto a la informacion de la institucinn educativa
   		 *	@param HttpServletRequest request
   		 *	@return boolean 
   		 */
   			public boolean asignarBeans(HttpServletRequest request)throws ServletException, IOException{ 
   			    HttpSession session = request.getSession();
   			    if(session.getAttribute("login")==null)
   				    return false;
   			    return true;
   			}		
   		
   		/**
    	* Funcinn: Elimina del contexto de la sesion los beans de informacion del usuario
    	*	@param HttpServletRequest request
    	*/
      public void borrarBeans(HttpServletRequest request) throws ServletException, IOException{
          //HttpSession session=request.getSession();
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