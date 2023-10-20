package siges.librodeNotas;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import siges.librodeNotas.beans.*;
import siges.boletines.dao.ReporteLogrosDAO;
import siges.common.vo.ItemVO;
import siges.dao.*;
import siges.login.beans.Login;
;

/**
*	Nombre:	ControllerLibroFiltroEdit<BR>
*	Descripcinn:	Controla la vista del formulario de filtro de Libro de Notas<BR>
*	Funciones de la pngina:	Poner los dartos del formulario y redirigir el control a la vista	<BR>
*	Entidades afectadas:	No aplica	<BR>
*	Fecha de modificacinn:	29/07/2005	<BR>
*	@author Latined	<BR>
*	@version v 1.0	<BR>
*/

public class ControllerLibroFiltroEdit extends HttpServlet{
	private String mensaje;
	private String buscar;
	private boolean err;
	private Cursor cursor;
	private Util util;
	private HttpSession session;
	private ResourceBundle rb;
	private Collection list;
	private Object[] o;
	private Login login;
	private Integer entero=new Integer(java.sql.Types.INTEGER);
	private FiltroBeanLibro filtro;
	private ReporteLogrosDAO reportesDAO;
	
/**
*	Procesa la peticion HTTP
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
	public String process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		session = request.getSession();
		rb=ResourceBundle.getBundle("preparedstatements_libro");
		
		String sig;
		String ant;
		String er;
		String home;
		int tipo;
		sig=getServletConfig().getInitParameter("sig");
		ant=getServletConfig().getInitParameter("ant");
		er=getServletContext().getInitParameter("error"); 
		home=getServletContext().getInitParameter("home");
		err=false;
		mensaje=null;

	try{
		 cursor=new Cursor();
		 util=new Util(cursor);
		 reportesDAO = new ReporteLogrosDAO(cursor);
		
		if(request.getParameter("tipo")==null || ((String)request.getParameter("tipo")).equals("")){
			session.removeAttribute("filtrolibro");
			tipo=1;
		}else
			tipo=Integer.parseInt((String)request.getParameter("tipo"));
		if(!asignarBeans(request)){
			setMensaje("Error capturando datos de sesinn para el usuario");
			request.setAttribute("mensaje",mensaje);
			return er;
		}
		if(tipo==1)libro(request);
		
		if(err){
			request.setAttribute("mensaje",mensaje);
			return er;
		}		
		}catch(Exception e){System.out.println("Error "+this+": "+e.toString());
		 throw new	ServletException(e);
		}finally{
			  if(cursor!=null)cursor.cerrar();if(util!=null)util.cerrar();
		}				
		return sig;				
	}	

	
	public void libro(HttpServletRequest request) throws ServletException, IOException{
	    Connection con=null;
  		PreparedStatement pst=null;
  		ResultSet rs=null;
  		int posicion=1;
  		String vig=null;
		try{
	    borrarBeansLibro(request);			
			System.out.println("*********////*********nENTRn POR LIBRO!**********//**********");
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
			if(request.getAttribute("filtroSedeF")==null)
			    request.setAttribute("filtroSedeF",util.getFiltro(rb.getString("filtroSedeInstitucion"),list));
			if(request.getAttribute("filtroJornadaF")==null)
			 	request.setAttribute("filtroJornadaF",util.getFiltro(rb.getString("filtroSedeJornadaInstitucion"),list));
			if(request.getAttribute("filtroMetodologiaF")==null)
				request.setAttribute("filtroMetodologiaF",util.getFiltro(rb.getString("listaMetodologiaInstitucion"),list));		
			  Collection cc=util.getFiltro(rb.getString("filtroGradoInstitucion"),list);	
				request.setAttribute("filtroGradoF2",cc);
				//if(request.getAttribute("filtroAno")==null)
					//request.setAttribute("filtroAno",util.getFiltro(rb.getString("listaAno"),list));
				
				/*con=cursor.getConnection();
   			   	pst=con.prepareStatement(rb.getString("AnioActual"));
   			   	rs=pst.executeQuery();
   			   	if(rs.next())
   			   		vig=rs.getString(1);
   			   	System.out.println("***valor de vig: ***"+vig);
   			   	rs.close();
   			   	pst.close();*/
   			   	
   			   	vig=String.valueOf(reportesDAO.getVigenciaInst(Long.parseLong(login.getInstId())));
   			   	
				if(vig!=null){
					if(request.getAttribute("AnioActual")==null)
						request.setAttribute("AnioActual",new Integer(vig));
				}
				
				List l=new ArrayList();
				ItemVO item=null;				
				item=new ItemVO(1,"Semestre 1"); l.add(item);
				item=new ItemVO(2,"Semestre 2"); l.add(item);						
				item=new ItemVO(3,"Anual"); l.add(item);
				request.setAttribute("filtroTipoProm",l);
				
				if(filtro==null){
					filtro =  new FiltroBeanLibro();
					filtro.setAno(String.valueOf(reportesDAO.getVigenciaInst(Long.parseLong(login.getInstId()))));
					filtro.setTipoProm(3);
				}
				request.getSession().setAttribute("filtrolibro",filtro);
				
				
		}catch(Throwable th){
			System.out.println("Error "+th);
			throw new	ServletException(th);
		}
		finally{
    		try{
    		    OperacionesGenerales.closeResultSet(rs);
    		    OperacionesGenerales.closeStatement(pst);    		    
    		    OperacionesGenerales.closeConnection(con);
    		    }catch(Exception e){}    
    		}						
	}

/**
 *	Inserta los valores por defecto para el bean de información bnsica con respecto a la informacion de la institucinn educativa
 *	@param HttpServletRequest request
 *	@return boolean 
 */
	public boolean asignarBeans(HttpServletRequest request)throws ServletException, IOException{ 
		login=(Login)session.getAttribute("login");
		filtro=(FiltroBeanLibro)session.getAttribute("filtrolibro");	
		String sentencia="";
		return true;
	}

	/**
	 * Elimina del contexto de la sesion los beans de informacion del usuario
	 *	@param HttpServletRequest request
	 */
		public void borrarBeansLibro(HttpServletRequest request) throws ServletException, IOException{
	    session.removeAttribute("filtrolibro");
	    System.out.println("nnnnnBorrn de sesinn el filtro de Libro de Notas!!!!");	    
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
			mensaje="VERIFIQUE LA SIGUIENTE información: \n\n";
		}
		mensaje+="  - "+s+"\n";
	}
}