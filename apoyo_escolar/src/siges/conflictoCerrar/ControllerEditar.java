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
import java.util.List;
import java.util.ResourceBundle;
import siges.dao.*;
import siges.exceptions.InternalErrorException;
import siges.login.beans.*;
import siges.util.Logger;
import siges.util.Properties;
import siges.util.Recursos;
import siges.conflictoCerrar.dao.*;
import siges.conflictoCerrar.beans.*;

/**
 * @author Administrador
 *
 * Ventana - Preferencias - Java - Estilo de cndigo - Plantillas de cndigo
 */
public class ControllerEditar extends HttpServlet{
    
    private String mensaje;//mensaje en caso de error
   	private String buscar;
   	private boolean err;//variable que inidica si hay o no errores en la validacion de los datos del formulario
   	private Cursor cursor;//objeto que maneja las sentencias sql
   	private ConflictoCerrarDAO ccDAO;
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
   		  String sig="/conflictoCerrar/AbrirGrupo.jsp";
   		  String sig1="/conflictoCerrar/CerrarGrupo.jsp";
   		  String sig2="/conflictoCerrar/CerrarPeriodo.jsp";
   		  String p_inte=this.getServletContext().getInitParameter("integrador");
	    		String p_home=this.getServletContext().getInitParameter("home");
	    		String p_login=this.getServletContext().getInitParameter("login");
	    		String p_error=this.getServletContext().getInitParameter("er");
	    		cursor=new Cursor();
	    		ccDAO=new ConflictoCerrarDAO(cursor);
	    		int tipo, categoria;
	    		String readonly;
	    		try{
	    		    Login login=(Login)session.getAttribute("login");
	    		    ConflictoCerrar cc=(ConflictoCerrar)session.getAttribute("conflictocerrar");
	    		    if(!asignarBeans(request)){
	    		        setMensaje("Error capturando datos de sesinn para el usuario");
	    		     			request.setAttribute("mensaje",mensaje);
	    		     			return(p_error);
	    		    }
	    		    if(request.getParameter("tipo")==null || ((String)request.getParameter("tipo")).equals("")){
	    		        borrarBeans(request);
	    		        if(!(request.getParameter("dato")==null || ((String)request.getParameter("dato")).equals("")))
	    		            tipo=Integer.parseInt((String)request.getParameter("dato"));
	    		        else	tipo=0;
	    		    }
	    		    else{
	    		        tipo=Integer.parseInt((String)request.getParameter("tipo"));
	    		    }
	    		    List listaPeriodos = ccDAO.obtenerPeridos(login.getVigencia_actual());
	   		        if(listaPeriodos != null && listaPeriodos.size()>0){
	   		        	request.setAttribute("listaPeriodos", listaPeriodos);
	   		        }
	    		    
	    		    switch(tipo){
	    		    //entrada por defecto abrir/cerrar periodo
	    		    case 0:
	    		        request.setAttribute("filtroSedesJornadas",ccDAO.getSedesJornadas(login.getInstId()));
	    		        request.setAttribute("filtroPeriodosAbiertos",ccDAO.getPeriodos(login.getInstId()));
	    		        sig=sig2;
	    		        break;
	    		    //entrada por defecto abrir grupo
	    		    case 1:
	    		    	System.out.println("***case 1 - cargarConflicto***");
	    		        cargarConflicto(request,login);
	    		        
	    		        break;
	    		    //entrada por defecto cerrar grupo
	    		    case 2:
	    		        cargarConflicto(request,login);
	    		        sig=sig1;
	    		        break;
	    		    //abrir grupo
	    		    case 3:
	    		        cargarConflicto(request,login);

	    		        if(ccDAO.abrirGrupo(cc,login)){
	    		        	System.out.println("*** IF ***");
	    		            request.setAttribute("mensaje","El grupo fue abierto satisfactoriamente.");
	    		            Logger.print(login.getUsuarioId(),"Abrir grupo de conflicto escolar",7,1,this.toString());
	    		        }else{
	    		        	System.out.println("*** ELSE ***");
	    		            request.setAttribute("mensaje",ccDAO.getMensaje());
	    		        }
	    		        break;
	    		    //cerrar grupo
	    		    case 4:
	    		        cargarConflicto(request,login);
	    		        
	    		        cc.setJergrado(ccDAO.getJerar_1_7(cc.getSede(),cc.getJornada(),cc.getGrado(),cc.getGrupo(),login));
	    		        if(ccDAO.isDirectorGrupo(cc.getJergrado(),cc.getGrupo(),login.getUsuarioId())){
	    		            if(ccDAO.cerrarGrupo(cc,login)){
	    		                request.setAttribute("mensaje","El grupo fue cerrado satisfactoriamente.");
	    		                Logger.print(login.getUsuarioId(),"Cerrar grupo de conflicto escolar",7,1,this.toString());
	   	    		        }else{
	   	    		            request.setAttribute("mensaje",ccDAO.getMensaje());
	   	    		        }
	    		        }else{
	    		            request.setAttribute("mensaje","Usted no es el director de este grupo. No puede cerrar el grupo.");
	    		        }
	    		        
	    		        sig=sig1;
	    		        break;
	    		    //abrir periodo
	    		    case 5:
	    		        if(ccDAO.abrirPeriodo(cc,login)){
	    		            request.setAttribute("mensaje","El periodo fue abierto satisfactoriamente.");
	    		            Logger.print(login.getUsuarioId(),"Abrir periodo de conflicto escolar",7,1,this.toString());
	    		        }else{
	    		            request.setAttribute("mensaje",ccDAO.getMensaje());
	    		        }
	    		        request.setAttribute("filtroSedesJornadas",ccDAO.getSedesJornadas(login.getInstId()));
	    		        request.setAttribute("filtroPeriodosAbiertos",ccDAO.getPeriodos(login.getInstId()));
	    		        sig=sig2;
	    		        break;
	    		    //cerrar periodo
	    		    case 6:
	    		        list=ccDAO.getGruposAbiertos(cc,login);
	    		        if(list.isEmpty()){
	    		            if(ccDAO.cerrarPeriodo(cc,login)){
	    		                request.setAttribute("mensaje","El periodo fue cerrado satisfactoriamente.");
	    		                Logger.print(login.getUsuarioId(),"Cerrar periodo de conflicto escolar",7,1,this.toString());
	    		            }else{
	    		                request.setAttribute("mensaje",ccDAO.getMensaje());
	    		            }
	    		        }else{
	    		            request.setAttribute("mensaje","No se puede cerrar el periodo porque hay grupos aun abiertos, \n   Si desea cerrarlo de todas maneras, pulse en el botnn 'Cerrar Grupos y Periodo'\n");
	    		        				if(list.size()>170){
	    		        				    request.setAttribute("mensaje","Nota:\n   La lista de grupos abiertos mostrada a continuacinn es solo una parte de la cantidad total de grupos abiertos");
	    		        				}
	    		        				request.setAttribute("gruposAbiertos",list);    
	    		        }
	    		        request.setAttribute("filtroSedesJornadas",ccDAO.getSedesJornadas(login.getInstId()));
	    		        request.setAttribute("filtroPeriodosAbiertos",ccDAO.getPeriodos(login.getInstId()));
	    		        sig=sig2;
	    		        break;
	    		    //cerrar grupos y periodo
	    		    case 7:
	    		    	if(ccDAO.cerrarPeriodoAllGrupos(cc,login)){
	    		    		request.setAttribute("mensaje","El periodo y todos los grupos fueron cerrados satisfactoriamente.");
	    		    		Logger.print(login.getUsuarioId(),"Cerrados el periodo y todos los grupos de conflicto escolar",7,1,this.toString());
	    		    	}else{
	    		    		request.setAttribute("mensaje",ccDAO.getMensaje());
	    		    	}
	    		        request.setAttribute("filtroSedesJornadas",ccDAO.getSedesJornadas(login.getInstId()));
	    		        request.setAttribute("filtroPeriodosAbiertos",ccDAO.getPeriodos(login.getInstId()));
	    		        sig=sig2;
	    		        break;
	    		    }
	    		    request.removeAttribute("dato");
	    		    return sig;
	    		}catch(Exception e){
	    		    e.printStackTrace();
	    		    System.out.println("Error "+this+": "+e.toString());
	    		    throw new	ServletException(e);
	    		}finally{
	    		    if(cursor!=null)cursor.cerrar();
	    		}
   		}
   		
   		public void cargarConflicto(HttpServletRequest request,Login login) throws ServletException, Exception, InternalErrorException{
   		    int z;
 				    z=0;
 				    list = new ArrayList();
 				    o=new Object[2];
 				    o[z++]=new Integer(java.sql.Types.BIGINT);
 				    o[z++]=login.getInstId();						
 				    list.add(o);
 				    Collection parametros[]=new Collection[5];
 				    Collection listas[]=new Collection[5];
 								for(int zz=0;zz<parametros.length;zz++){
 										parametros[zz]=new ArrayList();
 										parametros[zz].add(o);
 								}
 								String consultas[]={
 								        rb.getString("filtroSedeInstitucion"),
 								        rb.getString("filtroSedeJornadaInstitucion"),
 								        rb.getString("listaMetodologiaInstitucion"),
 								        rb.getString("filtroSedeJornadaGradoInstitucion2"),
 								        rb.getString("filtroSedeJornadaGradoGrupoInstitucion"),
 								};
 								listas=ccDAO.getFiltro(consultas,parametros);
 								request.setAttribute("filtroSedeAbrir",listas[0]);
 								request.setAttribute("filtroJornadaAbrir",listas[1]);
 								request.setAttribute("filtroMetodologiaAbrir",listas[2]);
 								request.setAttribute("filtroGradoAbrir",listas[3]);
 								request.setAttribute("filtroGrupoAbrir",listas[4]);
 								request.setAttribute("filtroPeriodoAbrir",Recursos.recursoEstatico[Recursos.PERIODO]);
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
      	
      	/**
        *	Concatena el parametro en una variable que contiene los mensajes que se van a enviar a la vista
        *	@param	String s
        **/
        	public void setMensaje2(String s){
        		if (!err){
        			err=true;
        			mensaje="VERIFIQUE LA SIGUIENTE INFORMACION: \n\n";
        		}
        		mensaje="  - "+s+"\n";
        	}
}