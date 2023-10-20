package siges.grupo; 

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
import siges.grupo.beans.*;
import siges.grupo.dao.GrupoDAO;

/**
 *	Nombre:	ControllerGrupoNuevoEdit
 *	Descripcion:	 
 *	Parametro de entrada:	HttpServletRequest request, HttpServletResponse response
 *	Parametro de salida:	HttpServletRequest request, HttpServletResponse response
 *	Funciones de la pagina:	Procesar la peticion y enviar el control al formulario de nuevo registro
 *	Entidades afectadas:	
 *	Fecha de modificacinn:	25/01/06
 *	@author Latined
 *	@version $v 1.0 $
 */
public class ControllerGrupoNuevoEdit extends HttpServlet{
    private String mensaje;//mensaje en caso de error
    private String buscar;
    private boolean err;//variable que inidica si hay o no errores en la validacion de los datos del formulario
    private Cursor cursor;//objeto que maneja las sentencias sql
    private Login login;
    private Util util;    
    private ResourceBundle rb,rb1;
    private GrupoBean newgrupo;
    private HttpSession session;
    private GrupoDAO grupoDAO;
    
    
    /**
     *	Procesa la peticion HTTP
     *	@param	HttpServletRequest request
     *	@param	HttpServletResponse response
     **/
    public String process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        session = request.getSession();
        GrupoBean grupo;
        String sig,sig1;
        String ant;
        String er;
        String home;
        String sentencia;
        String boton;
        int tipo;
        String id;
        String id2;
        String id_grado;
        String id_metod;
        sig=getServletConfig().getInitParameter("sig");
        sig1=getServletConfig().getInitParameter("sig1");///grupo/NuevoGrupo.jsp
        ant=getServletConfig().getInitParameter("ant");
        er=getServletContext().getInitParameter("error");
        home=getServletContext().getInitParameter("home");
        rb=ResourceBundle.getBundle("grupo");
        rb1=ResourceBundle.getBundle("preparedstatementsgrupos");
        cursor=new Cursor();
        newgrupo=new GrupoBean();
        grupoDAO=new GrupoDAO(cursor);
        err=false;
        mensaje=null;
        
        try{
        	boton=(request.getParameter("cmd")!=null)?request.getParameter("cmd"):new String("Cancelar");        	
        	grupoDAO=new GrupoDAO(cursor);
      		grupo=(GrupoBean)session.getAttribute("newgroup");
      		login=(Login)session.getAttribute("login");        	
      		if(request.getParameter("tipo")==null || ((String)request.getParameter("tipo")).equals(""))
        	    tipo=1;
        	else
        		tipo=Integer.parseInt((String)request.getParameter("tipo"));
        		
        	if(boton.equals("Editar") && grupo!=null){

/*    	  	  System.out.println("GrupoBean: \n");
    	  	  System.out.println("Inst: "+grupo.getInsitucionnew());
    	  	  System.out.println("Sede: "+grupo.getSedenew());
    	  	  System.out.println("Jornada: "+grupo.getJornadanew());
    	  	  System.out.println("Metodologia: "+grupo.getMetodologianew());
    	  	  System.out.println("Grado: "+grupo.getGradonew());
    	  	  System.out.println("TipoEspacio: "+grupo.getTipoespacionew());
    	  	  System.out.println("Espacio: "+grupo.getEspacionew());
    	  	  System.out.println("Coordinador: "+grupo.getCoordinadornew());
    	  	  System.out.println("Codigo: "+grupo.getCodigonew());
    	  	  System.out.println("Nombre: "+grupo.getNombrenew());
    	  	  System.out.println("Cupo: "+grupo.getCuponew());
    	  	  System.out.println("Orden: "+grupo.getOrdennew());
    	  	  System.out.println("Jerarquia: "+grupo.getJerarquiagrupo());*/

    	  	  session.setAttribute("newgroup",grupo);
    				session.setAttribute("newgroup2",grupo.clone());    				
    				
        		switch(tipo){
        			case 1:
        				editarGrupo(request,login);
        				sig=sig1;
        			break;
        		}
        		if(err){
        			request.setAttribute("mensaje",mensaje);
        			return er;
        		}		
        	 }	
         return sig;        	
        }catch(Exception e){
        		e.printStackTrace();
        		throw new	ServletException(e);
        		}finally{
        		    if(cursor!=null)cursor.cerrar();if(grupoDAO!=null)grupoDAO.cerrar();
        			}
        }	
       
    
    
    /**
     *	Pone en la session los datos de informacion basica necesarios para la vista de formulario 
     *	@param HttpServletRequest request
     */
    	public void editarGrupo(HttpServletRequest request,Login login) throws ServletException, IOException{
    		HttpSession session=request.getSession();
    		try{
    	    Collection list;
    		  Object[] o;
    			String s;
    			int z;
    	    z=0;
    	    list = new ArrayList();
    	    o=new Object[2];
    			o[z++]=new Integer(java.sql.Types.INTEGER);
    			o[z++]=login.getInstId();
    			list.add(o);
    			request.setAttribute("filtroSedeF",grupoDAO.getFiltro(rb1.getString("filtroSedeInstitucion"),list));
    			request.setAttribute("filtroJornadaF",grupoDAO.getFiltro(rb1.getString("filtroSedeJornadaInstitucion"),list));
    			request.setAttribute("filtroMetodologiaF",grupoDAO.getFiltro(rb1.getString("listaMetodologiaInstitucion"),list));		
    			request.setAttribute("filtroGradoF2",grupoDAO.getFiltro(rb1.getString("filtroSedeJornadaGradoInstitucion2"),list));
    			request.setAttribute("filtroGrupoF",grupoDAO.getFiltro(rb1.getString("filtroSedeJornadaGradoGrupoInstitucion"),list));    			
    			request.setAttribute("filtroTipoEspacio",grupoDAO.getFiltro(rb1.getString("listaTipoEspacio")));
  				request.setAttribute("filtroEspacio",grupoDAO.getFiltro(rb1.getString("listaEspacioInstitucionSede"),list));		
  				request.setAttribute("filtroCoordinador",grupoDAO.getFiltro(rb1.getString("listaCoordinador"),list));
    			
    		}catch(Exception th){
    		    th.printStackTrace();
    			throw new	ServletException(th);
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
     *	Inserta los valores por defecto para el bean de información bnsica con respecto a la informacion de la institucinn educativa
     *	@param HttpServletRequest request
     *	@return boolean 
     */
    public boolean asignarBeans(HttpServletRequest request)throws ServletException, IOException{ 
        if(request.getSession().getAttribute("filtrogroup")==null)
            return false;
        newgrupo=(GrupoBean)request.getSession().getAttribute("newgroup");
        login=(Login)request.getSession().getAttribute("login");
        return true;
    }
 
    
    
   	/**
   	 * Elimina del contexto de la sesion los beans de informacion del usuario
   	 *	@param HttpServletRequest request
   	 */
   		public void borrarNewBeansGrupos(HttpServletRequest request) throws ServletException, IOException{  
   	    session.removeAttribute("newgroup");
   		}
          
      /**
       * Elimina del contexto de la sesion los beans de informacion del usuario
       *	@param HttpServletRequest request
       */
      public void borrarBeans(HttpServletRequest request) throws ServletException, IOException{       
          request.getSession().removeAttribute("filtrogroup"); 
          request.getSession().removeAttribute("newgroup");
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
            mensaje="VERIFIQUE LA SIGUIENTE información: \n\n";
        }
        mensaje+="  - "+s+"\n";
    }
}