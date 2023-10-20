package siges.ruta;
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
import java.util.ResourceBundle;
import siges.ruta.vo.FiltroBeanVO;
import siges.ruta.vo.FiltroBeanGestVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.dao.Util;
import siges.login.beans.*;
import siges.util.Properties;


/** siges.reporte<br>
 * Funcinn:   
 * <br>
 */
public class ControllerReporteFiltroEdit extends HttpServlet{
	private String mensaje;//mensaje en caso de error
	private String buscar;
	private boolean err;//variable que inidica si hay o no errores en la validacion de los datos del formulario
	private Login login;
	private Collection list;	
	private Object[] o;
	private Util util;
	private Cursor cursor;
	private ResourceBundle rb;
	private Integer entero=new Integer(java.sql.Types.INTEGER);
	private FiltroBeanVO filtroNut;
	private FiltroBeanGestVO filtroGest;
	private HttpSession session;
	private String vigencia;
	private Dao dao;
	

	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException<br>
	 * Return Type: String<br>
	 * Version 1.1.<br>
	 */
	public String process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
	  session = request.getSession();
		rb=ResourceBundle.getBundle("siges.ruta.filtro_reportes_ruta");
		String sig,sig2;		
		String ant;
		String er;
		String home;
		int tipo;
		sig="/ruta/reportes/filtroReporte.jsp";
		sig2="/ruta/reportes/filtroReporteGestacion.jsp";
		ant=getServletConfig().getInitParameter("ant");
		er=getServletContext().getInitParameter("error");
		home=getServletContext().getInitParameter("home");
		err=false;
		mensaje=null;

		try{
		    cursor=new Cursor();
		    util=new Util(cursor);
		    dao= new Dao(cursor);
				
		    vigencia=dao.getVigencia();
			  
		    if(vigencia==null){
		        System.out.println("VIGENCIA NULA");  
		        ir(2,home,request,response);				
		        return er;
		    }				
			
		if(request.getParameter("tipo")==null || ((String)request.getParameter("tipo")).equals("")){
			tipo=1;
		}else{
		  String valorCampo=(String)request.getParameter("tipo");
			try{
			   tipo=Integer.parseInt((String)request.getParameter("tipo"));			   
			}catch(NumberFormatException e){
			  tipo=Integer.parseInt(valorCampo.substring(valorCampo.length()-1,valorCampo.length()));
			}
		}
		if(!asignarBeans(request)){
			setMensaje("Error capturando datos de sesinn para el usuario");
			request.setAttribute("mensaje",mensaje);
			return er;
		}
		
		switch(tipo){
			case 1:
			  filtroNutricion(request);
			break;
			case 2:
			  filtroGestacion(request);
				sig=sig2;
			break; 
		}	 
		if(err){
		    request.setAttribute("mensaje",mensaje);
		    return er;
		}					
		}catch(Exception e){System.out.println("Error "+this+": "+e.toString());
		throw new	ServletException(e);
		}finally{
		    if(cursor!=null)cursor.cerrar();if(util!=null)util.cerrar();
		}
		System.out.println("sig:"+sig);
		return sig;
	}

	
	
	public void filtroNutricion(HttpServletRequest request) throws ServletException, IOException{

	    Connection con=null;
  		PreparedStatement pst=null;
  		ResultSet rs=null;
  		int posicion=1;
  		String accion=null;
			try{
			  borrarBeansFiltroNutricion(request);			
				System.out.println("*********////*********nENTRn POR FILTRO NUTRICInN!**********//**********");
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
				  Collection cc=util.getFiltro(rb.getString("filtroSedeJornadaGradoInstitucion2"),list);
					request.setAttribute("filtroGradoF2",cc);
				if(request.getAttribute("filtroGrupoF")==null)
				 	request.setAttribute("filtroGrupoF",util.getFiltro(rb.getString("filtroSedeJornadaGradoGrupoInstitucion"),list));
				if(request.getAttribute("filtroPeriodoF")==null)
				    request.setAttribute("filtroPeriodoF",util.getFiltro(rb.getString("listaPeriodosInstitucion")));
				
				accion=request.getParameter("accion");
				if(accion!=null && accion.equals("1")){
					if((!filtroNut.getSede().equals("-9")) && (!filtroNut.getJornada().equals("-9")) && (!filtroNut.getGrado().equals("-9")) && (!filtroNut.getGrupo().equals("-9"))){					    
	   			   con=cursor.getConnection();
					   pst=con.prepareStatement(rb.getString("lista_estudiantes"));
	  			   posicion=1;
	  			   pst.clearParameters();
	  			   pst.setLong(posicion++,Long.parseLong(login.getInstId()));
	  			   pst.setLong(posicion++,Long.parseLong(filtroNut.getSede()));
	  			   pst.setLong(posicion++,Long.parseLong(filtroNut.getJornada()));
	  			   pst.setLong(posicion++,Long.parseLong(filtroNut.getGrado()));
	  			   pst.setLong(posicion++,Long.parseLong(filtroNut.getMetodologia()));	  			   
	  			   pst.setLong(posicion++,Long.parseLong(filtroNut.getGrupo()));
	  			   rs=pst.executeQuery();	  			   
	  			   list=new ArrayList();	  			   
	  			   while(rs.next()){
	  			      o=new Object[4];
	  			      o[0]=rs.getString(1);
	  			      o[1]=rs.getString(2);
	  			      o[2]=rs.getString(3);
	  			      o[3]=rs.getString(4);
	  			      list.add(o);
	  			   }System.out.println("size: "+list.size());
	  			   rs.close();
	  			   pst.close();
	  			   request.setAttribute("ruta_estudiantes",list);
					}
				}
			}catch(Throwable th){
			   th.printStackTrace();
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

	
	public void filtroGestacion(HttpServletRequest request) throws ServletException, IOException{

	    Connection con=null;
  		PreparedStatement pst=null;
  		ResultSet rs=null;
  		int posicion=1;
  		String accion=null;
			try{
			  borrarBeansFiltroGestacion(request);			
				System.out.println("*********////*********nENTRn POR FILTRO GESTACInN!**********//**********");
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
				  Collection cc=util.getFiltro(rb.getString("filtroSedeJornadaGradoInstitucion3"),list);	
					request.setAttribute("filtroGradoF2",cc);
				if(request.getAttribute("filtroGrupoF")==null)
				 	request.setAttribute("filtroGrupoF",util.getFiltro(rb.getString("filtroSedeJornadaGradoGrupoInstitucion"),list));
				if(request.getAttribute("filtroPeriodoF")==null)
				    request.setAttribute("filtroPeriodoF",util.getFiltro(rb.getString("listaPeriodosInstitucion")));
				
				accion=request.getParameter("accion");
				if(accion!=null && accion.equals("1")){
					if((!filtroGest.getSede().equals("-9")) && (!filtroGest.getJornada().equals("-9")) && (!filtroGest.getGrado().equals("-9")) && (!filtroGest.getGrupo().equals("-9"))){
	   			   con=cursor.getConnection();
					   pst=con.prepareStatement(rb.getString("lista_estudiantes"));
	  			   posicion=1;
	  			   pst.clearParameters();
	  			   pst.setLong(posicion++,Long.parseLong(login.getInstId()));
	  			   pst.setLong(posicion++,Long.parseLong(filtroGest.getSede()));
	  			   pst.setLong(posicion++,Long.parseLong(filtroGest.getJornada()));
	  			   pst.setLong(posicion++,Long.parseLong(filtroGest.getGrado()));
	  			   pst.setLong(posicion++,Long.parseLong(filtroGest.getMetodologia()));	  			   
	  			   pst.setLong(posicion++,Long.parseLong(filtroGest.getGrupo()));
	  			   rs=pst.executeQuery();	  			   
	  			   list=new ArrayList();	  			   
	  			   while(rs.next()){
	  			      o=new Object[4];
	  			      o[0]=rs.getString(1);
	  			      o[1]=rs.getString(2);
	  			      o[2]=rs.getString(3);
	  			      o[3]=rs.getString(4);
	  			      list.add(o);
	  			   }System.out.println("size: "+list.size());
	  			   rs.close();
	  			   pst.close();
	  			   request.setAttribute("ruta_estudiantes_gestacion",list);
					}
				}
			}catch(Throwable th){
			   th.printStackTrace();
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
 * Elimina del contexto de la sesion los beans de informacion del usuario
 *	@param HttpServletRequest request
 */
	public void borrarBeansFiltroNutricion(HttpServletRequest request) throws ServletException, IOException{
    request.getSession().removeAttribute("filtroNut");
    System.out.println("nnnnnBorrn de sesinn el filtro del Formulario!!!!");	    
	}

/**
 * Elimina del contexto de la sesion los beans de informacion del usuario
 *	@param HttpServletRequest request
 */
	public void borrarBeansFiltroGestacion(HttpServletRequest request) throws ServletException, IOException{
    request.getSession().removeAttribute("filtroGest");
    System.out.println("nnnnnBorrn de sesinn el filtro del Formulario!!!!");	    
	}
	
	
	/**
	 * @param request
	 * @return
	 * @throws ServletException
	 * @throws IOException<br>
	 * Return Type: boolean<br>
	 * Version 1.1.<br>
	 */
	public boolean asignarBeans(HttpServletRequest request)throws ServletException, IOException{ 
		HttpSession session= request.getSession();
		login=(Login)session.getAttribute("login");
		filtroNut=(FiltroBeanVO)session.getAttribute("filtroNut");
		filtroGest=(FiltroBeanGestVO)session.getAttribute("filtroGest");
		return true;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doPost(request,response);//redirecciona la peticion a doPost
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
  	String s=process(request, response);
	  if(s!=null && !s.equals(""))
	  	ir(1,s,request,response);
	}

	/**
	 * @param a
	 * @param s
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException<br>
	 * Return Type: void<br>
	 * Version 1.1.<br>
	 */
	public void ir(int a,String s,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher rd=getServletContext().getRequestDispatcher(s);
		if(a==1) rd.include(request, response);
		else rd.forward(request, response);
	}

	/**
	 * @param s<br>
	 * Return Type: void<br>
	 * Version 1.1.<br>
	 */
	public void setMensaje(String s){
		if(!err){
			err=true;
			mensaje="VERIFIQUE LA SIGUIENTE información: \n\n";
		}
		mensaje+="  - "+s+"\n";
	}
}