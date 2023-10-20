package siges.ruta;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import siges.dao.*;
import siges.io.*;
import java.util.*;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import siges.ruta.vo.FiltroBeanVO;
import siges.ruta.vo.FiltroBeanGestVO;
import org.apache.commons.io.CopyUtils;
import siges.login.beans.Login;
import siges.ruta.dao.ReporteDAO;

;


/**
*	Nombre:	ControllerReporteFiltroSave<BR>
*	Descripcinn:	Controla la negociacion de busqueda para la vista de resultados	<BR>
*	Funciones de la pngina:	Busca los datos y redirige el control a la vista de resultados	<BR>
*	Entidades afectadas:	<BR>
*	Fecha de modificacinn:	04/10/2005	<BR>
*	@author Latined	<BR>
*	@version v 1.0	<BR>
*/

public class ControllerReporteFiltroSave extends HttpServlet{

	
	private static final long serialVersionUID = 1L;
	private Cursor cursor;//objeto que maneja las sentencias sql
	private Zip zip;
	private Login login;
	private HttpSession session;
	private String mensaje;
	private boolean err;//variable que inidica si hay o no errores en la validacion de los datos del formulario
	private ResourceBundle rb3;
	private Collection list;
	private Object[] o;
	private java.sql.Timestamp f2;
	private FiltroBeanVO filtro;
	private FiltroBeanGestVO filtro1;
	private Util util;
	private String [] codigo;
	private String buscarcodigo;
	private final String modulo="29";
	private final String moduloG="30";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private ServletConfig config;
	private String s="/Reportes.do";
	private String s1="/ruta/reportes/ControllerReporteFiltroEdit.do";
	private String archivo,archivozip;	
	private String insertar;
	private String ant;
	private String er;
	private String sig;
	private String home;
	private ReporteDAO reporteDao;
	private static int parametro;
	private int estudiantes=0;
	private String param;
	private String alert;
	private int tipoN=1;
	private int tipoG=2;
	    	
    	
/**
*	Procesa la peticion HTTP
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/

  	public String process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {			    

    	Connection con=null;
    	PreparedStatement pst=null;
    	Collection list = new ArrayList();
    	ResultSet rs=null;
    	int posicion=1;
    	int count;
    	int tipo;
 	   	rb3=ResourceBundle.getBundle("siges.ruta.reportes_ruta");
 	   	f2=new java.sql.Timestamp(new java.util.Date().getTime());
 	   	String existeboletin,boton;
 	   	ant="/ruta/reportes/filtroReporte.jsp";
 	  	sig="/ruta/reportes/filtroReporte.jsp";
 	  	er="/error.jsp";
 	  	home="/bienvenida.jsp";
 	  	insertar=existeboletin=null; 	  	
 	  	cursor=new Cursor();
 	  	session = request.getSession();	
 	  	param=rb3.getString("parametro");
 	  	alert="El Reporte se esta generando en este momento. \nPulse en el hipervinculo 'Listado de Reportes' para ver si el reporte ya fue generado. \nO vaya a la opción de menu 'Reportes generados'";
 	  	util=new Util(cursor); 	  	
 	  	boton=(request.getParameter("cmd")!=null)?request.getParameter("cmd"):new String("");
 	  	reporteDao=new ReporteDAO(cursor);
 	  	
 	  	
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
	 	 		ir(2,er,request,response);
	 	 		return null;
	 	 	}
 	  	ServletContext context=(ServletContext)request.getSession().getServletContext();
 	  	String contextoTotal=context.getRealPath("/");
  		parametro=Integer.parseInt(param);

			switch(tipo){
			case 1:
			  if(!procesoNutricion(request)){
			      System.out.println("false");
			      return s1;
			  }
			break;
			case 2:
				  if(!procesoGestacion(request)){
				      System.out.println("false");
				      return s1;
				  }
			break; 
		}	 
		if(err){
		    request.setAttribute("mensaje",mensaje);
		    return er;
		}					
		System.out.println("s:"+s);
		return s;
	}
	 	 	
  		
  		
	public boolean procesoNutricion(HttpServletRequest request) throws ServletException, IOException{

    	Connection con=null;
  		PreparedStatement pst=null;
	  	ResultSet rs=null;
  	  int posicion;
  	  String cont=null;
  	  String sede=null;String jornada=null;String met=null;String grado=null;String grupo=null;String id=null;
  	  String param=null;
  	 	String nom=null;
  		
  		try{
  		    con=cursor.getConnection();

  		    System.out.println("***ProcesoNutricion***");	
	   			pst=con.prepareStatement(rb3.getString("existe_mismo_reporte_en_cola"));
	   			posicion=1;
	   			pst.clearParameters();
		   		pst.setLong(posicion++,Long.parseLong(login.getInstId()));
		   		pst.setLong(posicion++,Long.parseLong(!filtro.getSede().equals("-9")?filtro.getSede():"-9"));
		   		pst.setLong(posicion++,Long.parseLong(!filtro.getJornada().equals("-9")?filtro.getJornada():"-9"));
		   		pst.setLong(posicion++,Long.parseLong(!filtro.getMetodologia().equals("-9")?filtro.getMetodologia():"-9"));
		   		pst.setLong(posicion++,Long.parseLong(!filtro.getGrado().equals("-9")?filtro.getGrado():"-9"));
		   		pst.setLong(posicion++,Long.parseLong(!filtro.getGrupo().equals("-9")?filtro.getGrupo():"-9"));
		   		pst.setString(posicion++,!filtro.getEstudiante().equals("-9")?filtro.getEstudiante():"-9");
		   		pst.setInt(posicion++,tipoN);
	   			rs=pst.executeQuery();
	   			if(!rs.next()){
	   			    System.out.println("******nNO HAY NINGUNO CON LOS MISMOS PARAMETROS!...EN ESTADO CERO O -1!*********");
	   			    rs.close();pst.close();
	   			    /*consulta de numero de estudiantes para el nivel del boletin*/
	   			   if(filtro.getGrado().equals("-9") && filtro.getGrupo().equals("-9")){
	   			       System.out.println("NIVEL:METODOLOGIA");
	   			       System.out.println("metod:"+filtro.getMetodologia());
			  	   			pst=con.prepareStatement(rb3.getString("numero_estudiantes.buscar"));
			  	   			posicion=1;
			  	   			pst.clearParameters();
			  	   			pst.setLong(posicion++,Long.parseLong(login.getInstId()));
			  		   		pst.setLong(posicion++,Long.parseLong(!filtro.getSede().equals("-9")?filtro.getSede():"-9"));
			  		   		pst.setLong(posicion++,Long.parseLong(!filtro.getJornada().equals("-9")?filtro.getJornada():"-9"));
			  		   		pst.setLong(posicion++,Long.parseLong(!filtro.getMetodologia().equals("-9")?filtro.getMetodologia():"-9"));
	   			   }else{
	  	   			   if(!filtro.getGrado().equals("-9") && filtro.getGrupo().equals("-9")){
	  	   			      System.out.println("NIVEL:METODOLOGIA-GRADO00000000000000");
	 			  	   			pst=con.prepareStatement(rb3.getString("numero_estudiantes.buscar.grado"));
	 			  	   			posicion=1;
	 			  	   			pst.clearParameters();
	 			  	   			pst.setLong(posicion++,Long.parseLong(login.getInstId()));
	 			  		   		pst.setLong(posicion++,Long.parseLong(!filtro.getSede().equals("-9")?filtro.getSede():"-9"));
	 			  		   		pst.setLong(posicion++,Long.parseLong(!filtro.getJornada().equals("-9")?filtro.getJornada():"-9"));
	 			  		   		pst.setLong(posicion++,Long.parseLong(!filtro.getMetodologia().equals("-9")?filtro.getMetodologia():"-9"));
	 			  		   	  pst.setLong(posicion++,Long.parseLong(!filtro.getGrado().equals("-9")?filtro.getGrado():"-9"));
	 	   			   }else{
		  	   			   if(!filtro.getGrado().equals("-9") && !filtro.getGrupo().equals("-9")){
		  	   			        System.out.println("NIVEL:METODOLOGIA-GRADO-GRUPO11111111111111");
			 			  	   			pst=con.prepareStatement(rb3.getString("numero_estudiantes.buscar.grado.grupo"));
			 			  	   			posicion=1;
			 			  	   			pst.clearParameters();
			 			  	   			pst.setLong(posicion++,Long.parseLong(login.getInstId()));
			 			  		   		pst.setLong(posicion++,Long.parseLong(!filtro.getSede().equals("-9")?filtro.getSede():"-9"));
			 			  		   		pst.setLong(posicion++,Long.parseLong(!filtro.getJornada().equals("-9")?filtro.getJornada():"-9"));
			 			  		   		pst.setLong(posicion++,Long.parseLong(!filtro.getMetodologia().equals("-9")?filtro.getMetodologia():"-9"));
			 			  		   	  pst.setLong(posicion++,Long.parseLong(!filtro.getGrado().equals("-9")?filtro.getGrado():"-9"));
			 			  		   	  pst.setLong(posicion++,Long.parseLong(!filtro.getGrupo().equals("-9")?filtro.getGrupo():"-9"));
		  	   			   }else{
				  			   	  	setMensaje("Nivel de reporte indefinido");
				  			   	  	request.setAttribute("mensaje",mensaje);
				  			   	  	return false;
			 	   			   }
	 	   			   }
	   			   }
	  		   		rs=pst.executeQuery();
	  	   			if(rs.next()){
	  	   			   estudiantes=rs.getInt(1);
	  	   				 rs.close();pst.close();
	  	   				 	if(estudiantes!=0){
		  	   				    if(estudiantes<=parametro){
		  	   				        sede=(!filtro.getSede().equals("-9")?"_Sede_"+filtro.getSede().trim():"");
		  	   				        jornada=(!filtro.getJornada().equals("-9")?"_Jornada_"+filtro.getJornada().trim():"");
		  	   				        met=(!filtro.getMetodologia().equals("-9")?"_Metodologia_"+filtro.getMetodologia():"");
		  	   				        grado=(!filtro.getGrado().equals("-9")?"_Grado_"+filtro.getGrado():"");
		  	   				        grupo=(!filtro.getGrupo().equals("-9")?"_Grupo_"+filtro.getGrupo():"");
		  	   				        id=(!filtro.getEstudiante().equals("-9")?"_Doc_"+filtro.getEstudiante():"");
								   		   
		  	   				        nom=sede+jornada+met+grado+grupo+id+"_Fecha_"+f2.toString().replace(':','-').replace('.','-');
		  	   				        archivo="Reporte_Datos_Nutricion_"+nom+".xls";
		  	   				        archivozip="Reporte_Datos_Nutricion_"+nom+".zip";
								   			 
		  	   				        if(!reporteDao.insertarDatosReporte(filtro,login.getInstId(),f2,archivozip,archivo,login.getUsuarioId())){
		  	   				            setMensaje("No se insertn en DATOS_REPORTE_NUTRICION");
		  	   				            request.setAttribute("mensaje",mensaje);
				  	   				        return false;
		  	   				        }
		  	   				        System.out.println("Se insertn el ZIP en Reporte con estado -1");
		  	   				        if(!reporteDao.ponerReporte(modulo,login.getUsuarioId(),rb3.getString("reportes.PathReportes")+archivozip+"","zip",""+archivozip,"-1","ReporteInsertarEstado")){
		  	   				            setMensaje("No se insertn en la tabla Reporte");
		  	   				            request.setAttribute("mensaje",mensaje);
				  	   				        return false;
		  	   				        }
									   		 	siges.util.Logger.print(login.getUsuarioId(),"Peticinn de Reporte_Nutricion:Institucion:_"+login.getInstId()+"_Usuario:_"+login.getUsuarioId()+"_NombreReporte:_"+archivozip+"",3,1,this.toString());
												  request.setAttribute("mensaje",getMensaje());
								   			 	cont=reporteDao.colaReportes(tipoN);
								   			 	if(cont!=null && !cont.equals(""))
								   			 		if(!reporteDao.updatePuestoReporte(cont,archivozip,login.getUsuarioId(),"update_puesto_reporte",tipoN)){
								   			 		    setMensaje("No se insertn el puesto en la tabla Reporte");
								   			 		    request.setAttribute("mensaje",mensaje);
								   			 		    return false;
								   			 		}
		  	   				    }	 	
		  	   				    else{
		  	   				        setMensaje("No se puede generar el reporte a nivel de metodologna, debe generarlo a nivel de grupo, \nya que excede la capacidad permitida para la descarga de archivos del sistema");
		  	   				        request.setAttribute("mensaje",mensaje);
		  	   				        return false;
		  	   				    }
	  	   				 	}
	  	   				 	else{
	  	   				 	    setMensaje("No se encontraron estudiantes para la solicitud");
	  	   				 	    request.setAttribute("mensaje",mensaje);
	  	   				 	    return false;
	  	   				 	}
	  	   			}
	  	   			else{
	  	   			    rs.close();pst.close();
	  	   			    setMensaje("No se encontraron estudiantes en el filtro");
	  	   			    request.setAttribute("mensaje",mensaje);
	  	   			    return false;
	  	   			}
	   			}
	   			else{
	   			    rs.close();pst.close();
	   			    setMensaje("nYa existe una peticinn del reporte con los mismos parnmetros!");
	   			    request.setAttribute("mensaje",mensaje);
	   			    return false;
	   			}
  		}	
  		catch(Exception e){
  			System.out.println("se jodio por aca filtrosave");
  		    e.printStackTrace();
  		    reporteDao.ponerReporteMensaje("2",modulo,login.getUsuarioId(),rb3.getString("reportes.PathReportes")+archivozip+"","zip",""+archivozip,"ReporteActualizarBoletin","Ocurrin excepcinn ControllerFiltroSave");
  		}finally{
  		    try{
  		        if(cursor!=null)cursor.cerrar();
  		        if(util!=null)util.cerrar();
  		        OperacionesGenerales.closeResultSet(rs);
  		        OperacionesGenerales.closeStatement(pst);    		    
  		        OperacionesGenerales.closeConnection(con);
  		    }catch(Exception e){}
  		}  		
  		return true;
  	}

  	
	public boolean procesoGestacion(HttpServletRequest request) throws ServletException, IOException{

    	Connection con=null;
  		PreparedStatement pst=null;
	  	ResultSet rs=null;
  	  int posicion;
  	  String cont=null;
  	  String sede=null;String jornada=null;String met=null;String grado=null;String grupo=null;String id=null;
  	  String param=null;
  	 	String nom=null;
  		
  		try{
  		    con=cursor.getConnection();

  		    System.out.println("***ProcesoGestacion***");	
	   			pst=con.prepareStatement(rb3.getString("existe_mismo_reporte_en_cola"));
	   			posicion=1;
	   			pst.clearParameters();
		   		pst.setLong(posicion++,Long.parseLong(login.getInstId()));
		   		pst.setLong(posicion++,Long.parseLong(!filtro1.getSede().equals("-9")?filtro1.getSede():"-9"));
		   		pst.setLong(posicion++,Long.parseLong(!filtro1.getJornada().equals("-9")?filtro1.getJornada():"-9"));
		   		pst.setLong(posicion++,Long.parseLong(!filtro1.getMetodologia().equals("-9")?filtro1.getMetodologia():"-9"));
		   		pst.setLong(posicion++,Long.parseLong(!filtro1.getGrado().equals("-9")?filtro1.getGrado():"-9"));
		   		pst.setLong(posicion++,Long.parseLong(!filtro1.getGrupo().equals("-9")?filtro1.getGrupo():"-9"));
		   		pst.setString(posicion++,!filtro1.getEstudiante().equals("-9")?filtro1.getEstudiante():"-9");
		   		pst.setInt(posicion++,tipoG);
	   			rs=pst.executeQuery();
	   			if(!rs.next()){
	   			    System.out.println("******nNO HAY NINGUNO CON LOS MISMOS PARAMETROS!...EN ESTADO CERO O -1!*********");
	   			    rs.close();pst.close();
	   			    /*consulta de numero de estudiantes para el nivel del boletin*/
	   			   if(filtro1.getGrado().equals("-9") && filtro1.getGrupo().equals("-9")){
	   			       System.out.println("NIVEL:METODOLOGIA");
	   			       System.out.println("metod:"+filtro1.getMetodologia());
			  	   			pst=con.prepareStatement(rb3.getString("numero_estudiantes.buscar"));
			  	   			posicion=1;
			  	   			pst.clearParameters();
			  	   			pst.setLong(posicion++,Long.parseLong(login.getInstId()));
			  		   		pst.setLong(posicion++,Long.parseLong(!filtro1.getSede().equals("-9")?filtro1.getSede():"-9"));
			  		   		pst.setLong(posicion++,Long.parseLong(!filtro1.getJornada().equals("-9")?filtro1.getJornada():"-9"));
			  		   		pst.setLong(posicion++,Long.parseLong(!filtro1.getMetodologia().equals("-9")?filtro1.getMetodologia():"-9"));
	   			   }else{
	  	   			   if(!filtro1.getGrado().equals("-9") && filtro1.getGrupo().equals("-9")){
	  	   			      System.out.println("NIVEL:METODOLOGIA-GRADO");
	 			  	   			pst=con.prepareStatement(rb3.getString("numero_estudiantes.buscar.grado"));
	 			  	   			posicion=1;
	 			  	   			pst.clearParameters();
	 			  	   			pst.setLong(posicion++,Long.parseLong(login.getInstId()));
	 			  		   		pst.setLong(posicion++,Long.parseLong(!filtro1.getSede().equals("-9")?filtro1.getSede():"-9"));
	 			  		   		pst.setLong(posicion++,Long.parseLong(!filtro1.getJornada().equals("-9")?filtro1.getJornada():"-9"));
	 			  		   		pst.setLong(posicion++,Long.parseLong(!filtro1.getMetodologia().equals("-9")?filtro1.getMetodologia():"-9"));
	 			  		   	  pst.setLong(posicion++,Long.parseLong(!filtro1.getGrado().equals("-9")?filtro1.getGrado():"-9"));
	 	   			   }else{
		  	   			   if(!filtro1.getGrado().equals("-9") && !filtro1.getGrupo().equals("-9")){
		  	   			        System.out.println("NIVEL:METODOLOGIA-GRADO-GRUPO");
			 			  	   			pst=con.prepareStatement(rb3.getString("numero_estudiantes.buscar.grado.grupo"));
			 			  	   			posicion=1;
			 			  	   			pst.clearParameters();
			 			  	   			pst.setLong(posicion++,Long.parseLong(login.getInstId()));
			 			  		   		pst.setLong(posicion++,Long.parseLong(!filtro1.getSede().equals("-9")?filtro1.getSede():"-9"));
			 			  		   		pst.setLong(posicion++,Long.parseLong(!filtro1.getJornada().equals("-9")?filtro1.getJornada():"-9"));
			 			  		   		pst.setLong(posicion++,Long.parseLong(!filtro1.getMetodologia().equals("-9")?filtro1.getMetodologia():"-9"));
			 			  		   	  pst.setLong(posicion++,Long.parseLong(!filtro1.getGrado().equals("-9")?filtro1.getGrado():"-9"));
			 			  		   	  pst.setLong(posicion++,Long.parseLong(!filtro1.getGrupo().equals("-9")?filtro1.getGrupo():"-9"));
		  	   			   }else{
				  			   	  	setMensaje("Nivel de reporte indefinido");
				  			   	  	request.setAttribute("mensaje",mensaje);
				  			   	  	return false;
			 	   			   }
	 	   			   }
	   			   }
	  		   		rs=pst.executeQuery();
	  	   			if(rs.next()){
	  	   			   estudiantes=rs.getInt(1);
	  	   				 rs.close();pst.close();
	  	   				 	if(estudiantes!=0){
		  	   				    if(estudiantes<=parametro){
		  	   				        sede=(!filtro1.getSede().equals("-9")?"_Sede_"+filtro1.getSede().trim():"");
		  	   				        jornada=(!filtro1.getJornada().equals("-9")?"_Jornada_"+filtro1.getJornada().trim():"");
		  	   				        met=(!filtro1.getMetodologia().equals("-9")?"_Metodologia_"+filtro1.getMetodologia():"");
		  	   				        grado=(!filtro1.getGrado().equals("-9")?"_Grado_"+filtro1.getGrado():"");
		  	   				        grupo=(!filtro1.getGrupo().equals("-9")?"_Grupo_"+filtro1.getGrupo():"");
		  	   				        id=(!filtro1.getEstudiante().equals("-9")?"_Doc_"+filtro1.getEstudiante():"");
								   		   
		  	   				        nom=sede+jornada+met+grado+grupo+id+"_Fecha_"+f2.toString().replace(':','-').replace('.','-');
		  	   				        archivo="Reporte_Datos_Gestacion_"+nom+".xls";
		  	   				        archivozip="Reporte_Datos_Gestacion_"+nom+".zip";
								   			 
		  	   				        if(!reporteDao.insertarDatosReporteG(filtro1,login.getInstId(),f2,archivozip,archivo,login.getUsuarioId())){
		  	   				            setMensaje("No se insertn en DATOS_REPORTE_NUTRICION");
		  	   				            request.setAttribute("mensaje",mensaje);
				  	   				        return false;
		  	   				        }
		  	   				        System.out.println("Se insertn el ZIP en Reporte con estado -1");
		  	   				        if(!reporteDao.ponerReporte(moduloG,login.getUsuarioId(),rb3.getString("reportes.PathReportes")+archivozip+"","zip",""+archivozip,"-1","ReporteInsertarEstado")){
		  	   				            setMensaje("No se insertn en la tabla Reporte");
		  	   				            request.setAttribute("mensaje",mensaje);
				  	   				        return false;
		  	   				        }
									   		 	siges.util.Logger.print(login.getUsuarioId(),"Peticinn de Reporte_Gestacinn:Institucion:_"+login.getInstId()+"_Usuario:_"+login.getUsuarioId()+"_NombreReporte:_"+archivozip+"",3,1,this.toString());
												  request.setAttribute("mensaje",getMensaje());
								   			 	cont=reporteDao.colaReportes(tipoG);
								   			 	if(cont!=null && !cont.equals(""))
								   			 		if(!reporteDao.updatePuestoReporte(cont,archivozip,login.getUsuarioId(),"update_puesto_reporte",tipoG)){
								   			 		    setMensaje("No se insertn el puesto en la tabla Reporte");
								   			 		    request.setAttribute("mensaje",mensaje);
								   			 		    return false;
								   			 		}
		  	   				    }	 	
		  	   				    else{
		  	   				        setMensaje("No se puede generar el reporte a nivel de metodologna, debe generarlo a nivel de grupo, \nya que excede la capacidad permitida para la descarga de archivos del sistema");
		  	   				        request.setAttribute("mensaje",mensaje);
		  	   				        return false;
		  	   				    }
	  	   				 	}
	  	   				 	else{
	  	   				 	    setMensaje("No se encontraron estudiantes para la solicitud");
	  	   				 	    request.setAttribute("mensaje",mensaje);
	  	   				 	    return false;
	  	   				 	}
	  	   			}
	  	   			else{
	  	   			    rs.close();pst.close();
	  	   			    setMensaje("No se encontraron estudiantes en el filtro");
	  	   			    request.setAttribute("mensaje",mensaje);
	  	   			    return false;
	  	   			}
	   			}
	   			else{
	   			    rs.close();pst.close();
	   			    setMensaje("nYa existe una peticinn del reporte con los mismos parnmetros!");
	   			    request.setAttribute("mensaje",mensaje);
	   			    return false;
	   			}
  		}	
  		catch(Exception e){
  		    e.printStackTrace();
  		    reporteDao.ponerReporteMensaje("2",moduloG,login.getUsuarioId(),rb3.getString("reportes.PathReportes")+archivozip+"","zip",""+archivozip,"ReporteActualizarBoletin","Ocurrin excepcinn ControllerFiltroSave");
  		}finally{
  		    try{
  		        if(cursor!=null)cursor.cerrar();
  		        if(util!=null)util.cerrar();
  		        OperacionesGenerales.closeResultSet(rs);
  		        OperacionesGenerales.closeStatement(pst);    		    
  		        OperacionesGenerales.closeConnection(con);
  		    }catch(Exception e){}
  		}  		
  		return true;//se va a reportes.do
  	}
  	
  	

  	
	/**
	 *	Inserta los valores por defecto para el bean de información bnsica con respecto a la informacion de la institucinn educativa
	 *	@param HttpServletRequest request
	 *	@return boolean 
	 */
		public boolean asignarBeans(HttpServletRequest request)throws ServletException, IOException{
			login=(Login)session.getAttribute("login");
			filtro=(FiltroBeanVO)session.getAttribute("filtroNut");
			filtro1=(FiltroBeanGestVO)session.getAttribute("filtroGest");
			String sentencia="";		    
			return true;
		}


	/**
*	Recibe la peticion por el metodo get de HTTP
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doPost(request,response);
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
	*	Concatena el parametro en una variable que contiene los mensajes que se van a enviar a la vista
	*	@param	String s
	**/
		public void setMensaje(String s){
			if (!err){
				err=true;
				mensaje="VERIFIQUE LA SIGUIENTE información: \n\n";
			}
			mensaje="  - "+s+"\n";
		}	
		
		/**
		*	Retorna una variable que contiene los mensajes que se van a enviar a la vista
		*	@return String	
		**/
			public String getMensaje(){
				return mensaje;
			}
		
	/**
	*	Redirige el control a otro servlet
	*	@param	int a: 1=redirigir como 'include', 2=redirigir como 'forward'
	*	@param	String s
	*	@param	HttpServletRequest request
	*	@param	HttpServletResponse response
	**/
		public void ir(int a,String s,HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException{
			RequestDispatcher rd=getServletContext().getRequestDispatcher(s);
			if(a==1) rd.include(rq,rs);
			else rd.forward(rq,rs);
		}

	/*
	*Cierra cursor
	*
	*/
	public void destroy(){
   if(cursor!=null)cursor.cerrar();
 		cursor=null;				
	}	
	
}

