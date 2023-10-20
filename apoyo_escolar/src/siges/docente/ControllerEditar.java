package siges.docente;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.login.beans.Login;

/**
 * @author Administrador
 *
 * Ventana - Preferencias - Java - Estilo de cndigo - Plantillas de cndigo
 */
public class ControllerEditar extends HttpServlet{

    private String mensaje;
    private boolean err;
    private Cursor cursor;
    private ResourceBundle rb;
   	private Collection list;
   	private Object[] o;
   	private Integer enterolargo=new Integer(java.sql.Types.BIGINT);
   	private java.text.SimpleDateFormat sdf;
   	
   	/**
   	*	Procesa la peticion HTTP
   	*	@param	HttpServletRequest request
   	*	@param	HttpServletResponse response
   	**/
   		public String process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
   		  HttpSession session = request.getSession();
   		  rb=ResourceBundle.getBundle("docente");
   		  String sig="/docente/NuevoInfoDocente.jsp";
   		  String p_inte=this.getServletContext().getInitParameter("integrador");
	    		String p_home=this.getServletContext().getInitParameter("home");
	    		String p_login=this.getServletContext().getInitParameter("login");
	    		String p_error=this.getServletContext().getInitParameter("er");
	    		cursor=new Cursor();
	    		int tipo;
	    		try{
	    		    Login login=(Login)session.getAttribute("login");
	    		    cargarSedeJornada(request,login);
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
   		
   		public void cargarSedeJornada(HttpServletRequest request,Login login) throws ServletException, IOException{
     	  	String s;
     	  	ResourceBundle rb=ResourceBundle.getBundle("conflicto");
     	  		try{
     	  		    Collection list = new ArrayList();
     	  		    Object[] o=new Object[2];
     	  		    o[0]=enterolargo;
     	  		    o[1]=login.getInstId();
     	  		    list.add(o);
     	  		    request.setAttribute("filtroSedeF",getFiltro(rb.getString("filtroSedeInstitucion"),list));
     	  		    request.setAttribute("filtroJornadaF",getFiltro(rb.getString("filtroSedeJornadaInstitucion"),list));
     	  		    if(login.getSedeId()!="" && login.getJornadaId()!=""){
     	  		        o=new Object[2];
         	  		    o[0]=enterolargo;
         	  		    o[1]=login.getMetodologiaId();
         	  		    list.add(o);
     	   	        o=new Object[2];
     	   	        o[0]=enterolargo;
     	   	        o[1]=login.getSedeId();
     	   	        list.add(o);
     	   	        o=new Object[2];
     	   	        o[0]=enterolargo;
     	   	        o[1]=login.getJornadaId();
     	   	        list.add(o);
         	  		    request.setAttribute("filtroGradoF2",getFiltro(rb.getString("filtroSedeJornadaGradoInstitucion2"),list));
     	   	    }
     	  		}catch(InternalErrorException e){e.printStackTrace();System.out.println("excepcion "+e);}
   		}
   		
   		/**
   		*	Funcinn:  Ejecuta un consulta en la base de datos y devuelte el resultado<br>
   		*	@param	String consulta
   		* @param	Collection lista
   		*	@return	Collection
   		**/
   		public Collection getFiltro(String consulta,Collection lista)throws InternalErrorException{
   			Connection cn=null;
   			PreparedStatement pst=null,pst2=null,pst3=null;
   			ResultSet rs = null;
   			try{
   				cn=cursor.getConnection();		
   				pst = cn.prepareStatement(consulta);
   				pst.clearParameters();
   				Iterator iterator =lista.iterator();
   				int posicion=1;
   				while (iterator.hasNext()){
   					Object[] fila = (Object[])iterator.next();
   						switch(((Integer)fila[0]).intValue()){
   						case java.sql.Types.VARCHAR:
   							pst.setString(posicion++,((String)fila[1]).trim());
   						break;
   						case java.sql.Types.INTEGER:
   							pst.setInt(posicion++,Integer.parseInt(((String)fila[1]).trim()));
   						break;
   						case java.sql.Types.DATE:
   							pst.setDate(posicion++,new java.sql.Date(sdf.parse((String)fila[1]).getTime()));
   						break;
   						case java.sql.Types.NULL:
   							pst.setNull(posicion++,java.sql.Types.VARCHAR);
   						break;
   						case java.sql.Types.DOUBLE:
   							pst.setLong(posicion++,(((Long)fila[1])).longValue());
   						break;
   						case java.sql.Types.CHAR:
   							pst.setString(posicion++,((String)fila[1]).trim());
   						break;
   						case java.sql.Types.BIGINT:
   							pst.setLong(posicion++,Long.parseLong(((String)fila[1]).trim()));
   						break;
   					}
   				}
   				rs = pst.executeQuery();
   				int m=rs.getMetaData().getColumnCount();    
   		    Collection list = new ArrayList();        
   		    Object[] o;
   		    int i=0;
   		    int f=0;
   				while (rs.next()){
   					f++;
   					o=new Object[m];
   					for(i=1;i<=m;i++) o[i-1]=rs.getString(i);
   					list.add(o);
   				}
   				return list;
   			}catch(SQLException e){
   				throw new InternalErrorException(e);
   			}catch(java.text.ParseException e){
   				throw new InternalErrorException(e);
   			}finally{
   				OperacionesGenerales.closeResultSet(rs);
   				OperacionesGenerales.closeStatement(pst);
   				OperacionesGenerales.closeConnection(cn);
   				cursor.cerrar();
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
