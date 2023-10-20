package siges.conflictoGrupo;

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
import siges.conflicto.beans.ConflictoEscolar;
import siges.conflictoGrupo.beans.*;
import siges.conflictoGrupo.dao.*;
import siges.login.beans.*;
import siges.util.Properties;


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
   	private ConflictoGrupoDAO conflictoDAO;
   	private ConflictoGrupo cg;
   	private ConflictoFiltro cf,cf2;
   	private ResourceBundle rb;
   	//private Collection list;
   	//private Object[] o;
   	
   	/**
   	*	Procesa la peticion HTTP
   	*	@param	HttpServletRequest request
   	*	@param	HttpServletResponse response
   	**/
   	public String process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
   		HttpSession session = request.getSession();
   		rb=ResourceBundle.getBundle("conflictoGrupo");
   		String sig="/conflictoGrupo/NuevoConflictoGrupo.jsp";
   		String p_inte=this.getServletContext().getInitParameter("integrador");
   		String p_home=this.getServletContext().getInitParameter("home");
   		String p_login=this.getServletContext().getInitParameter("login");
   		String p_error=this.getServletContext().getInitParameter("er");
   		cursor=new Cursor();
   		int tipo, categoria;
   		String readonly;
   		try{
   			Login login=(Login)session.getAttribute("login");
   			ConflictoEscolar ce=(ConflictoEscolar)session.getAttribute("nuevoConflicto");
   			conflictoDAO=new ConflictoGrupoDAO(cursor);
   			if(!asignarBeans(request)){
   				setMensaje("Error capturando datos de sesinn para el usuario");
   				request.setAttribute("mensaje",mensaje);
   				return(p_error);
   			}
   			if(request.getParameter("tipo")==null || ((String)request.getParameter("tipo")).equals("")){
   				borrarBeans(request);
   				tipo=0;
   			}else{
   				tipo=Integer.parseInt((String)request.getParameter("tipo"));
   			}
   			if(request.getParameter("categoria")==null || ((String)request.getParameter("categoria")).equals("")){
   				categoria=0;
   			}else{
   				categoria=Integer.parseInt((String)request.getParameter("categoria"));
   			}
   			if(request.getParameter("readonly")==null || ((String)request.getParameter("readonly")).equals("")){
   				readonly="0";
   			}else{
   				readonly=(String)request.getParameter("readonly");
   			}
   			if(conflictoDAO.isGrupoCerrado(cf,login)){
   				request.setAttribute("mensaje","Este grupo se encuentra cerrado para este periodo. Acceso de solo lectura");
   				readonly="1";
   			}else if(!conflictoDAO.getMensaje().equals("")){
   				request.setAttribute("mensaje",conflictoDAO.getMensaje());
   			}else{
   				if(readonly.equals("1")){
   					cf.setJergrupo(conflictoDAO.obtenerJerar_1_7(cf.getSede(),cf.getJornada(),cf.getMetodologia(),cf.getGrado(),login));
   					//System.out.println("jerarquiaGrupo: "+cf.getJergrupo());
   					cf=conflictoDAO.obtenerDirectorGrupo(cf.getJergrupo(),cf.getGrupo(),login.getUsuarioId(),cf.getPeriodo());
   					
   					if(cf!=null){
   						//System.out.println("**1**");
   						readonly="2";
   						session.setAttribute("nuevoConflictoFiltro",cf);
   						session.setAttribute("nuevoConflictoFiltro2",cf.clone());
   					}//else System.out.println("**2**"); 
   						if(!conflictoDAO.getMensaje().equals("")){
   							//System.out.println("**3**");
   							request.setAttribute("mensaje",conflictoDAO.getMensaje());
   						}else{
   							//System.out.println("**4**");
   							cf=(ConflictoFiltro)session.getAttribute("nuevoConflictoFiltro");
   							readonly="2";
   							//request.setAttribute("mensaje","Usted no es el director de este grupo. Acceso de solo lectura");
   						}
   					
   				}else if(!cambioDirector(cf,cf2)){
   					//System.out.println("**5**");
   					cf.setJergrupo(conflictoDAO.obtenerJerar_1_7(cf.getSede(),cf.getJornada(),cf.getMetodologia(),cf.getGrado(),login));
   					//request.setAttribute("mensaje","Usted no es el director de este grupo. Acceso de solo lectura");
   					readonly="1";
   				}
   			}
   			request.setAttribute("readonly",readonly);
   			cargarConflicto(request,categoria);
	    		    
   			switch(tipo){
   			case 11:
   				//System.out.println("***case 11***");	
   				agregarConflictoGrupo(request, cf, categoria);
   				request.setAttribute("mensaje",mensaje);
   			case 10:
   				//System.out.println("***case 10 ***");
   				//System.out.println("***case 10 - ConfilctoEscolarXGRUPO***getJergrupo: "+cf.getJergrupo()+" getGrupo: "+cf.getGrupo()+" getPeriodo: "+cf.getPeriodo());
   				cg=conflictoDAO.asignarConflictoGrupo(cf.getJergrupo(),cf.getGrupo(),cf.getPeriodo());
   				if(cg!=null){
   					session.setAttribute("nuevoConflictoGrupo",cg);
   				}else{
   					request.setAttribute("mensaje",conflictoDAO.getMensaje());
   				}
   				break;
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
   		
   		public boolean cambioDirector(ConflictoFiltro cf, ConflictoFiltro cf2){
   			if(cf==null || cf2==null ){
   				//System.out.println("OJO");
   				return true;
   			}
   		    if(!cf.getSede().equals(cf2.getSede())) return false;
   		    if(!cf.getJornada().equals(cf2.getJornada())) return false;
   		    if(!cf.getGrado().equals(cf2.getGrado())) return false;
   		    if(!cf.getGrupo().equals(cf2.getGrupo())) return false;
   		    return true;
   		}
   		
   		public void agregarConflictoGrupo(HttpServletRequest request, ConflictoFiltro cf, int cat)throws ServletException, IOException{
   			//System.out.println("***agregarConflictoGrupo***");
   		    try{
   		        String aux1,aux2,texto,texto2,texto3;
   		        String[][] cl=conflictoDAO.getFiltroMatriz(rb.getString("filtroTipoConflicto"));
   		        if(conflictoDAO.eliminarConflictoGrupo(cf.getJergrupo(), cf.getGrupo(),cf.getDocente(),cf.getPeriodo())){
   		        	for(int i=0;i<cl.length;i++){
   		        		aux1=(String)cl[i][0];
   		        		texto=request.getParameter("cg"+aux1)!=null?request.getParameter("cg"+aux1):"-1";
   		        		texto2=request.getParameter("cge"+aux1)!=null?request.getParameter("cge"+aux1):"-1";
   		        		texto3=request.getParameter("cgee"+aux1)!=null?request.getParameter("cgee"+aux1):"-1";
   		        		if(!texto.equals("")){
   		        			if(!texto.equals("-1")){
   		        				if(!texto2.equals("")){
   		        					if(!texto2.equals("-1")){
   		   		        				if(!texto3.equals("")){
   		   		        					if(!texto3.equals("-1")){
   		   		        						if(conflictoDAO.insertarConflictoGrupo(texto,aux1,cf.getJergrupo(),cf.getDocente(),cf.getPeriodo(),cf.getGrupo(),texto2,texto3)){
   		   		        							setMensaje2("Operacion satisfecha");
   		   		        						}else{
   		   		        							setMensaje2(conflictoDAO.getMensaje());
   		   		        							request.setAttribute("mensaje",conflictoDAO.getMensaje());
   		   		        						}
   		   		        					}
   		   		        				}
   		        					}
   		        				}
   		        			}
   		        		}
   		        	}
   		        }else{
   		        	request.setAttribute("mensaje",conflictoDAO.getMensaje());
   		        	setMensaje2(conflictoDAO.getMensaje());
   		        }
   		    }catch(Throwable th){
   		    	th.printStackTrace();
   		    	throw new	ServletException(th);
   		    }
   		}
   		
   		public void cargarConflicto(HttpServletRequest request, int cat) throws ServletException, IOException{
   		    try{
   		        request.setAttribute("filtroClase",conflictoDAO.getFiltro(rb.getString("filtroClaseConflicto")));
   		        request.setAttribute("filtroTipo",conflictoDAO.getFiltro(rb.getString("filtroTipoConflicto")));
   		    }catch(Throwable th){
   		        th.printStackTrace();
   		        throw new	ServletException(th);
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
   			    cg=(ConflictoGrupo)session.getAttribute("nuevoConflictoGrupo");
   			    cf=(ConflictoFiltro)session.getAttribute("nuevoConflictoFiltro");
   			    cf2=(ConflictoFiltro)session.getAttribute("nuevoConflictoFiltro2");
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