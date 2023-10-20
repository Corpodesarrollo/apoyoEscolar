package siges.reporte;

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
import siges.reporte.*;
import siges.reporte.beans.*;
import siges.boletines.beans.FiltroBeanConstancias;
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
public class ControllerFiltroEdit extends HttpServlet{
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
	private FiltroBeanAsistencia filtro;
	private FiltroBeanEvaluacionLogro filtroEv;
	private FiltroBeanEvaluacionAsignatura filtroEvAsi;
	private FiltroBeanEvaluacionArea filtroEvAre;
	private FiltroBeanEvaluacionLogroGrupo  filtroEvLog;
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
		System.out.println("***CONTROLLERFILTROEDIT***");
		session = request.getSession();
		rb=ResourceBundle.getBundle("estadisticos_preparedstatements");
		String sig,sig2,sig3,sig4,sig5,sig6,sig7,sig8;		
		String ant;
		String er;
		String home;
		int tipo;
		sig="/reporte/filtroAsistencia.jsp";
		sig2="/reporte/filtroEvaluacionLogro.jsp";
		sig3="/reporte/filtroEvaluacionAsignatura.jsp";
		sig4="/reporte/filtroEvaluacionArea.jsp";
		sig5="/reporte/filtroEvaluacionLogroGrupo.jsp";
		sig6="/reporte/filtroEvaluacionAsignaturaGrupo.jsp";
		sig7="/reporte/filtroEvaluacionAreaGrupo.jsp";
		sig8="/reporte/filtroEvaluacionTipoDescriptor.jsp";
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
		        ir(1,home,request,response);				
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
			    asistencia(request);
			    System.out.println("sale de asistencia");
			break;
			case 2:
			    evaluacionLogro(request);
				sig=sig2; 
			break;
			case 3: 
			    evaluacionAsignatura(request);
				sig=sig3; 
			break;
			case 4:
			    evaluacionArea(request);
					sig=sig4; 
				break;
			case 5: 
			    evaluacionLogroGrupo(request);
					sig=sig5; 
				break;
			case 6:
			    evaluacionAsignaturaGrupo(request);
					sig=sig6; 
				break;
			case 7:
			    evaluacionAreaGrupo(request);
					sig=sig7; 
				break;
			case 8:
			    evaluacionTipoDescriptor(request);
					sig=sig8; 
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
		System.out.println("sale para : "+sig);
		return sig;
	}

	
	public void asistencia(HttpServletRequest request) throws ServletException, IOException{

			try{
		    borrarBeansAsistencia(request);			
				System.out.println("*********////*********nENTRn POR ASISTENCIA!**********//**********");
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
			}catch(Throwable th){
				th.printStackTrace();
				throw new	ServletException(th);
			}
		}
	

	
	public void evaluacionLogro(HttpServletRequest request) throws ServletException, IOException{

	    Connection con=null;
  		PreparedStatement pst=null;
  		ResultSet rs=null;
  		int posicion=1;
  		String accion=null;
			try{
			  borrarBeansEvaluacionLogro(request);			
				System.out.println("*********////*********nENTRn POR EVALUACInN LOGRO!**********//**********");
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
					if((!filtroEv.getSede().equals("-9")) && (!filtroEv.getJornada().equals("-9")) && (!filtroEv.getGrado().equals("-9")) && (!filtroEv.getGrupo().equals("-9"))){					    
	   			   con=cursor.getConnection();
					   pst=con.prepareStatement(rb.getString("lista_estudiantes"));
	  			   posicion=1;
	  			   pst.clearParameters();
	  			   pst.setLong(posicion++,Long.parseLong(login.getInstId()));
	  			   pst.setLong(posicion++,Long.parseLong(filtroEv.getSede()));
	  			   pst.setLong(posicion++,Long.parseLong(filtroEv.getJornada()));
	  			   pst.setLong(posicion++,Long.parseLong(filtroEv.getGrado()));
	  			   pst.setLong(posicion++,Long.parseLong(filtroEv.getMetodologia()));	  			   
	  			   pst.setLong(posicion++,Long.parseLong(filtroEv.getGrupo()));
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
	  			   request.setAttribute("estudiantes_estadisticos",list);
	  			   System.out.println("***OK***");
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
	

	
	public void evaluacionAsignatura(HttpServletRequest request) throws ServletException, IOException{

	    Connection con=null;
  		PreparedStatement pst=null;
  		ResultSet rs=null;
  		int posicion=1;
  		String accion=null;
			try{
			  borrarBeansEvaluacionAsignatura(request);			
				System.out.println("*********////*********nENTRn POR EVALUACInN ASIGNATURA!**********//**********");
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
					if((!filtroEvAsi.getSede().equals("-9")) && (!filtroEvAsi.getJornada().equals("-9")) && (!filtroEvAsi.getGrado().equals("-9")) && (!filtroEvAsi.getGrupo().equals("-9"))){					    
	   			   con=cursor.getConnection();
					   pst=con.prepareStatement(rb.getString("lista_estudiantes"));
	  			   posicion=1;
	  			   pst.clearParameters();
	  			   pst.setLong(posicion++,Long.parseLong(login.getInstId()));
	  			   pst.setLong(posicion++,Long.parseLong(filtroEvAsi.getSede()));
	  			   pst.setLong(posicion++,Long.parseLong(filtroEvAsi.getJornada()));
	  			   pst.setLong(posicion++,Long.parseLong(filtroEvAsi.getGrado()));
	  			   pst.setLong(posicion++,Long.parseLong(filtroEvAsi.getMetodologia()));	  			   
	  			   pst.setLong(posicion++,Long.parseLong(filtroEvAsi.getGrupo()));
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
	  			   request.setAttribute("estudiantes_estadisticos1",list);
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
	
	
	public void evaluacionLogroGrupo(HttpServletRequest request) throws ServletException, IOException{

	    Connection con=null;
  		PreparedStatement pst=null;
  		ResultSet rs=null;
  		int posicion=1;
  		String accion=null;
			try{
			  borrarBeansEvaluacionLogroGrupo(request);			
				System.out.println("*********////*********nENTRn POR EVALUACInN LOGRO GRUPO!**********//**********");
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

	
	public void evaluacionAsignaturaGrupo(HttpServletRequest request) throws ServletException, IOException{

	    Connection con=null;
  		PreparedStatement pst=null;
  		ResultSet rs=null;
  		int posicion=1;
  		String accion=null;
			try{
			  borrarBeansEvaluacionAsignaturaGrupo(request);			
				System.out.println("*********////*********nENTRn POR EVALUACInN ASIGNATURA GRUPO!**********//**********");
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
				if(request.getAttribute("filtroEscalaValorativa")==null)
				  request.setAttribute("filtroEscalaValorativa",util.getFiltro(rb.getString("listaEscalaValorativa")));				
				
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
	

	public void evaluacionAreaGrupo(HttpServletRequest request) throws ServletException, IOException{

	    Connection con=null;
  		PreparedStatement pst=null;
  		ResultSet rs=null;
  		int posicion=1;
  		String accion=null;
			try{
			  borrarBeansEvaluacionAreaGrupo(request);			
				System.out.println("*********////*********nENTRn POR EVALUACInN AREA GRUPO!**********//**********");
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
				if(request.getAttribute("filtroEscalaValorativa")==null)
				  request.setAttribute("filtroEscalaValorativa",util.getFiltro(rb.getString("listaEscalaValorativa")));				
				
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
	

	public void evaluacionTipoDescriptor(HttpServletRequest request) throws ServletException, IOException{

	    Connection con=null;
  		PreparedStatement pst=null;
  		ResultSet rs=null;
  		int posicion=1;
  		String accion=null;
			try{
			  borrarBeansEvaluacionTipoDescriptor(request);			
				System.out.println("*********////*********nENTRn POR EVALUACInN AREA TIPO DE DESCRIPTOR!**********//**********");
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
				if(request.getAttribute("filtroTipoDescriptor")==null)
				  request.setAttribute("filtroTipoDescriptor",util.getFiltro(rb.getString("listaTipoDescriptor")));				
					z=0;
					list = new ArrayList();
			    o=new Object[2];
					o[z++]=new Integer(java.sql.Types.INTEGER);
					o[z++]=login.getInstId();
					list.add(o);
					z=0;
			    o=new Object[2];
					o[z++]=new Integer(java.sql.Types.INTEGER);
					o[z++]=vigencia;
					list.add(o);										
				if(request.getAttribute("filtroAreaF")==null)
				   request.setAttribute("filtroAreaF",util.getFiltro(rb.getString("filtroAreasInstitucion"),list));						
				
				
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
	
	public void evaluacionArea(HttpServletRequest request) throws ServletException, IOException{

	    Connection con=null;
  		PreparedStatement pst=null;
  		ResultSet rs=null;
  		int posicion=1;
  		String accion=null;
			try{
			  borrarBeansEvaluacionArea(request);			
				System.out.println("*********////*********nENTRn POR EVALUACInN AREA!**********//**********");
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
					if((!filtroEvAre.getSede().equals("-9")) && (!filtroEvAre.getJornada().equals("-9")) && (!filtroEvAre.getGrado().equals("-9")) && (!filtroEvAre.getGrupo().equals("-9"))){					    
	   			   con=cursor.getConnection();
					   pst=con.prepareStatement(rb.getString("lista_estudiantes"));
	  			   posicion=1;
	  			   pst.clearParameters();
	  			   pst.setLong(posicion++,Long.parseLong(login.getInstId()));
	  			   pst.setLong(posicion++,Long.parseLong(filtroEvAre.getSede()));
	  			   pst.setLong(posicion++,Long.parseLong(filtroEvAre.getJornada()));
	  			   pst.setLong(posicion++,Long.parseLong(filtroEvAre.getGrado()));
	  			   pst.setLong(posicion++,Long.parseLong(filtroEvAre.getMetodologia()));	  			   
	  			   pst.setLong(posicion++,Long.parseLong(filtroEvAre.getGrupo()));
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
	  			   request.setAttribute("estudiantes_estadisticos2",list);
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
	public void borrarBeansAsistencia(HttpServletRequest request) throws ServletException, IOException{
    request.getSession().removeAttribute("filtroA");
    System.out.println("nnnnnBorrn de sesinn el filtro de Asistencia!!!!");	    
	}
	
/**
 * Elimina del contexto de la sesion los beans de informacion del usuario
 *	@param HttpServletRequest request
 */
	public void borrarBeansEvaluacionLogro(HttpServletRequest request) throws ServletException, IOException{
    request.getSession().removeAttribute("filtroB");
    System.out.println("nnnnnBorrn de sesinn el filtro de EvaluacinnLogro!!!!");	    
	}
	
/**
 * Elimina del contexto de la sesion los beans de informacion del usuario
 *	@param HttpServletRequest request
 */
	public void borrarBeansEvaluacionAsignatura(HttpServletRequest request) throws ServletException, IOException{
    request.getSession().removeAttribute("filtroC");
    System.out.println("nnnnnBorrn de sesinn el filtro de EvaluacinnAsignatura!!!!");	    
	}

/**
 * Elimina del contexto de la sesion los beans de informacion del usuario
 *	@param HttpServletRequest request
 */
	public void borrarBeansEvaluacionArea(HttpServletRequest request) throws ServletException, IOException{
    request.getSession().removeAttribute("filtroD");
    System.out.println("nnnnnBorrn de sesinn el filtro de EvaluacinnArea!!!!");	    
	}

/**
 * Elimina del contexto de la sesion los beans de informacion del usuario
 *	@param HttpServletRequest request
 */
	public void borrarBeansEvaluacionLogroGrupo(HttpServletRequest request) throws ServletException, IOException{
    request.getSession().removeAttribute("filtroE");
    System.out.println("nnnnnBorrn de sesinn el filtro de EvaluacinnLogroGrupo!!!!");	    
	}
		
/**
 * Elimina del contexto de la sesion los beans de informacion del usuario
 *	@param HttpServletRequest request
 */
	public void borrarBeansEvaluacionAsignaturaGrupo(HttpServletRequest request) throws ServletException, IOException{
    request.getSession().removeAttribute("filtroF");
    System.out.println("nnnnnBorrn de sesinn el filtro de EvaluacinnAsignaturaGrupo!!!!");	    
	}

/**
 * Elimina del contexto de la sesion los beans de informacion del usuario
 *	@param HttpServletRequest request
 */
	public void borrarBeansEvaluacionAreaGrupo(HttpServletRequest request) throws ServletException, IOException{
    request.getSession().removeAttribute("filtroG");
    System.out.println("nnnnnBorrn de sesinn el filtro de EvaluacinnAreaGrupo!!!!");	    
	}
	
/**
 * Elimina del contexto de la sesion los beans de informacion del usuario
 *	@param HttpServletRequest request
 */
	public void borrarBeansEvaluacionTipoDescriptor(HttpServletRequest request) throws ServletException, IOException{
    request.getSession().removeAttribute("filtroH");
    System.out.println("nnnnnBorrn de sesinn el filtro de EvaluacinnTipoDescriptor!!!!");	    
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
		filtroEv=(FiltroBeanEvaluacionLogro)session.getAttribute("filtroB");
		filtroEvAsi=(FiltroBeanEvaluacionAsignatura)session.getAttribute("filtroC");
		filtroEvAre=(FiltroBeanEvaluacionArea)session.getAttribute("filtroD");
		filtroEvLog=(FiltroBeanEvaluacionLogroGrupo)session.getAttribute("filtroE");  
		
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