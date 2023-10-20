package siges.reportePlan;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import siges.dao.*;
import siges.io.*;
import siges.reportePlan.*;
import siges.exceptions.InternalErrorException;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.*;

import com.lowagie.text.Image;
import com.lowagie.text.pdf.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.awt.SystemColor;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import siges.reportePlan.beans.*;
import org.apache.commons.io.CopyUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.validator.GenericValidator;

import siges.login.beans.Login;
import siges.util.Logger;
import siges.util.beans.ReporteVO;

;


/**
*	Nombre:	reportes<BR>
*	Descripcinn:	Controla la negociacion de busqueda para la vista de resultados	<BR>
*	Funciones de la pngina:	Busca los datos y redirige el control a la vista de resultados	<BR>
*	Entidades afectadas:	Boletin	<BR>
*	Fecha de modificacinn:	04/10/2005	<BR>
*	@author Latined	<BR>
*	@version v 1.0	<BR>
*/

public class ReportesPlan extends HttpServlet{

	private Cursor cursor;//objeto que maneja las sentencias sql
	private Zip zip;
	private Login login;
	private HttpSession session;
	private String mensaje;
	private boolean err;//variable que inidica si hay o no errores en la validacion de los datos del formulario
	private Integer gradocod=new Integer(java.sql.Types.INTEGER);
	private Integer entero=new Integer(java.sql.Types.INTEGER);
	private Integer cadena=new Integer(java.sql.Types.VARCHAR);
	private Integer fecha=new Integer(java.sql.Types.TIMESTAMP);
	private Integer nulo=new Integer(java.sql.Types.NULL);
	private Integer doble=new Integer(java.sql.Types.DOUBLE);
	private Integer caracter=new Integer(java.sql.Types.CHAR);
	private Integer enterolargo=new Integer(java.sql.Types.BIGINT);
	private ResourceBundle r,rb2,rb3;
	private Collection list;
	private Object[] o;
	private java.sql.Timestamp f2;
	private FiltroBeanReportesAreas filtroAreas;
	private FiltroBeanReportesDescriptores filtroDescriptores;
	private FiltroBeanReportesLogros filtroLogros; 
	private Util util;
	private final String moduloarea="9";
	private final String modulologro="10";
	private final String modulodescriptor="11";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private ServletConfig config;
	private String s;
	private String s1;
	private String archivo,archivozip;	
	private String buscar,buscarjasper;
	private String insertar;
	private String ant;
	private String er;
	private String sig;
	private String sig2;
	private String sig3;
	private String home;
	private byte[] bytes,bytes2,bytes3;	
	private Map parameters;
	private File reportFile;
	private File reportFile2;
	private File reportFile3;
	private String path;
	private String path1;
	private String requerido;
	private int requeridos; 
	private String alert;
	private String contextoTotal; 
	private Dao dao;
	private String context;
	
	
	    	
    	
/**
*	Procesa la peticion HTTP
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/

  	public String process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
  	  System.out.println("ENTRn PROCESS REPORTES PLANcfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff"); 
  	  session = request.getSession();
    	Connection con=null;
    	PreparedStatement pst=null;
    	Collection list = new ArrayList();
    	ResultSet rs=null;    	
    	int posicion=1;
    	int count;
 	    r=ResourceBundle.getBundle("path");
 	   	rb2=ResourceBundle.getBundle("operaciones");
 	    //rb3=ResourceBundle.getBundle("preparedstatements_reportesPlan");
 	    rb3=ResourceBundle.getBundle("siges.reportePlan.bundle.ReportesPlan");
 	   
 	   	f2=new java.sql.Timestamp(new java.util.Date().getTime());
 	   	String existeboletin,boton;
 	   	String nom;
 	   Login usuVO = (Login)request.getSession().getAttribute("login");
 	    s=getServletConfig().getInitParameter("s");
 	    s1=getServletConfig().getInitParameter("s1"); 	    
 	   	sig=getServletConfig().getInitParameter("sig");
 			sig2=getServletConfig().getInitParameter("sig2"); 			
 			sig3=getServletConfig().getInitParameter("sig3"); 				
 			ant=getServletConfig().getInitParameter("ant");
 			er=getServletContext().getInitParameter("error"); 
 			home=getServletContext().getInitParameter("home");
 	  	buscar=buscarjasper=insertar=existeboletin=null;
 	  	err=false;
 	  	
 	  	if(request.getParameter("reporte")==null){
 	  	 	 setMensaje("**Error al mandar generar el tipo de reporte solicitado**");
 	  	 	 request.setAttribute("mensaje",mensaje);
 	  	 	 ir(1,er,request,response);
 	  	 	 return er;
 	  	}
 	  	System.out.println("REPORTE: "+request.getParameter("reporte"));
 	    alert="El Reporte se esta generando en este momento. \nPulse en el hipervinculo 'Listado de Reportes' para ver si el reporte ya fue generado. \nO vaya a la opción de menu 'Reportes generados'"; 	  	
 	 	
  	   if(!asignarBeans(request)){
  	  	 		setMensaje("Error capturando datos de sesinn para el usuario");
  	  	 		request.setAttribute("mensaje",mensaje);
  	  	 		ir(1,er,request,response);
  	  	 		return null;
  	  	 	}
  		try{
  		 	cursor=new Cursor();
  			util=new Util(cursor); 
  			dao =new Dao(cursor);  			
//  		  if(usuVO != null && GenericValidator.isInt(usuVO.getInstId())){
//  			  vigencia=""+dao.getVigenciaInst(Long.parseLong(usuVO.getInstId()));
//  		  }else{
//  			  vigencia=dao.getVigencia();
//  		  }
//
//  			if(vigencia==null){
//   			   System.out.println("*W* LA VIGENCIA ES NULA *W* ");
//   			   return er;
//   			}
  	 	 	requeridos=Integer.parseInt(request.getParameter("reporte"));
  	 	 	System.out.println("REPORTE REQUERIDO: "+requeridos);
  	 	 
	 	  	ServletContext context=(ServletContext)request.getSession().getServletContext();
	 	  	contextoTotal=context.getRealPath("/");
	  	  	path=Ruta2.get(context.getRealPath("/"),rb3.getString("ruta_jaspers"));
	  	  	path1=Ruta2.get(context.getRealPath("/"),rb3.getString("ruta_img"));
	    	reportFile=new File(path+rb3.getString("jasper_areas"));	
	    	reportFile2=new File(path+rb3.getString("jasper_logros"));	  		  		
	    	reportFile3=new File(path+rb3.getString("jasper_descriptores"));
	    	if(filtroAreas != null){
	    		System.out.println("filtroAreas != null");
	    		filtroAreas.setInstitucion(login.getInstId());
	    		filtroAreas.setDane(getDane(Long.parseLong(filtroAreas.getInstitucion())));
	    	}
	    	if(filtroDescriptores != null){
	    		filtroDescriptores.setInstitucion(login.getInstId());
	    		filtroDescriptores.setDane(getDane(Long.parseLong(filtroDescriptores.getInstitucion())));
	    	}
	    	if(filtroLogros != null){
	    		filtroLogros .setInstitucion(login.getInstId());
	    		filtroLogros.setDane(getDane(Long.parseLong(filtroLogros.getInstitucion())));
	    	}
  	 		switch(requeridos){
  				case 1:
  				  if(!areas(request,contextoTotal)){
  					  return sig;
  				  }
  				break;
  				case 2:
    				if(!logros(request,contextoTotal)){
    					System.out.println("bien logros");
    					return sig2;
    				}
    				System.out.println("bien logros");
  				break;
  				case 3:
   				  if(!descriptores(request,contextoTotal))
   				     return sig3;
  				break;
  			}	
  			if(err){
  				request.setAttribute("mensaje",mensaje);
  				return er;
  			}					
  		}	 
	  catch(Exception e){
	   e.printStackTrace();
	   System.out.println("Error en Process: "+e.toString());
	   }finally{
	     try{
	      if(cursor!=null)cursor.cerrar();if(util!=null)util.cerrar();
	      }catch(Exception e){}
	    }
	    
	   ReporteVO reporteVO = new ReporteVO(); 
	    reporteVO.setRepTipo(ReporteVO._REP_PLAN_ESTUDIO);
	    reporteVO.setRepOrden(ReporteVO.ORDEN_DESC);
	    request.getSession().setAttribute("reporteVO",reporteVO);
	    System.out.println("s " + s);
     	return s;
	}

  	  	
  	public boolean areas(HttpServletRequest request,String contextoTotal) throws ServletException, IOException{
  		Connection con=null;
    	PreparedStatement pst=null;
    	ResultSet rs=null;
  	  	String met=null,grado=null,area=null,nom=null;
  	  	int posicion=1;
  	  	parameters=new HashMap();
  	  	String archivosalida=null;
  	  	int zise;
  	  	String nombresede=null,nombrejornada=null;
  	  	Collection list = new ArrayList();
  	  	Zip zip= new Zip();
  	  
  		try{
  			 con=cursor.getConnection();		    			 
  			 System.out.println("*********////*********nENTRn A GENERAR AREAS Y ASIGNATURAS!**********//**********");
  			 if(!asignarBeansAreas(request)){
  	 	 	 		setMensaje("Error capturando datos de areas para el reporte");
  	 	 	 		request.setAttribute("mensaje",mensaje);
  	 	 	 		ir(1,er,request,response);
  	 	 	 		return false;
  	 	 	 	}  			 
  			 nombresede=request.getParameter("sedenom");
  			 nombrejornada=request.getParameter("jornadanom");
  			 System.out.println("SEDE: "+nombresede); System.out.println("JORNADA: "+nombrejornada);
  			 /*PARAMETROS PARA el JASPER DE AREAS*/
  			 parameters.put("INSTITUCION",login.getInst()); 
  			 parameters.put("SEDE",nombresede);
  			 parameters.put("JORNADA",nombrejornada);  			  
  			 parameters.put("USUARIO",login.getUsuarioId());
  			
  			String rutaImagen = Ruta.get(contextoTotal, rb3.getString("consulta.rutaImagen"));
			String archivoImagen  = rutaImagen + rb3.getString("imagen") ;
			if (isValidaImagen(new File(archivoImagen))) {
				System.out.println("img de sed correcta");
			}else{
				System.out.println("img de sed error");
			}
			archivoImagen = (new File(archivoImagen)).getPath();
            System.out.println("archivoImagen " + archivoImagen);
  			File escudo=new File(path1+"e"+ getDane(Long.parseLong(login.getInstId())).trim()+".GIF");
  			if (escudo.exists()){
  				System.out.println("EXISTE EN MAYUSCULA");
  				parameters.put("ESCUDO_COLEGIO",path1+"e" + getDane(Long.parseLong(login.getInstId())).trim() +".GIF");
  		 }else{ 
  				parameters.put("ESCUDO_COLEGIO",path1+"e" + getDane(Long.parseLong(login.getInstId())).trim() +".gif");
  			}
  			parameters.put("ESCUDO_SED", ""+archivoImagen);
  		
			
   	  		
  			 System.out.println("PARAMETERS DEL JASPER DE AREAS: "+parameters.values());
  			  
  			 pst=con.prepareStatement(rb3.getString("existe_area_generandose"));
  			 posicion=1;
  			 pst.clearParameters();
  			 pst.setLong(posicion++,Long.parseLong(login.getUsuarioId()));
  			 rs=pst.executeQuery();
  			 
  			 if(!rs.next()){
  				 rs.close();pst.close();con.close();
  				 System.out.println("******nNO HAY NINGnN REPORTE DE AREAS GENERANDOSE PARA ESTE USUARIO EN ESTADO CERO!*********");  				
  				 //met=(!filtroAreas.getMetodologia().equals("-9")?"_Metodologia_"+filtroAreas.getMetodologia():"");
  				 grado=(!filtroAreas.getGrado().equals("-9")?"_Grado_"+filtroAreas.getGrado():"");
  				 area=(!filtroAreas.getArea().equals("-9")?"_Area_"+filtroAreas.getArea():"");
	
  				 //nom="Sede_"+filtroAreas.getSede()+"_Jornada_"+filtroAreas.getJornada()+grado+area+"_Fecha_"+f2.toString().replace(' ','_').replace(':','-').replace('.','-');
  				nom="_Fecha_"+f2.toString().replace(' ','_').replace(':','-').replace('.','-');
  				 System.out.println("NOMBRE REPORTE: "+nom);
  				 archivo="Reporte_Plan_Estudios_"+nom+".pdf";
  				 archivozip="Reporte_Plan_Estudios_"+nom+".zip";
		   		  
  				 ponerReporte(moduloarea,login.getUsuarioId(),rb3.getString("reporte.PathReporte")+archivozip+"","zip2",""+archivozip,"-1","ReporteInsertarEstado");//Estado -1
  				 System.out.println("Se insertn el ZIP en Reporte con estado -1");
						
  				 if(!proceso_areas(archivozip,""+filtroAreas.getFilvigencia())){//estado cero del reporte
			  		   System.out.println("nNO TERMINn PROCESO DE AREAS... OCURRIn ERROR..MIRAR TRACE!");			  		  
			  		   ponerReporteMensaje("2",moduloarea,login.getUsuarioId(),rb3.getString("reporte.PathReporte")+archivozip+"","zip2",""+archivozip,"ReporteActualizarBoletinPaila","Ocurrin problema en process de areas");
			  		   limpiarTablasAreas(login.getUsuarioId());
			  		   if(!updateReporteFecha("update_reporte_general",moduloarea,archivozip,login.getUsuarioId(),"2")){
			  			   limpiarTablasAreas(login.getUsuarioId());
			  			   return false;
			  		   }
			  		   return false;
			  		}
			  		System.out.println("SALE DE PROCESS");
			  		con=cursor.getConnection();
			  		pst=con.prepareStatement(rb3.getString("jasper_area"));
			  		posicion=1;
			  		pst.clearParameters();
			  		pst.setLong(posicion++,Long.parseLong(login.getUsuarioId()));
			  		rs=pst.executeQuery();
		   	  	
			  		if(rs.next()){
			  			rs.close();pst.close();
			  			System.out.println("nnnnnnnnnnnnnnnnSE DEBE EJECUTAR EL JASPER DE AREASnnnnnnnnnnnnnnnnnnnnn");
			  			if((reportFile.getPath()!=null) && (parameters!=null) &&(!parameters.values().equals("0")) && (con!=null)){      	  	    
			  				System.out.println("***Se mandn ejecutar el jasper de AREAS****");
			  				bytes=JasperRunManager.runReportToPdf(reportFile.getPath(),parameters,con);
			  			}         	  	      		         
			  			if(!updateReporteEstado(moduloarea,login.getUsuarioId(),rb3.getString("reporte.PathReporte")+archivozip+"","zip2",""+archivozip,"ReporteActualizarListo")) 
			  				limpiarTablasAreas(login.getUsuarioId());
			  			if(!ponerArchivo(moduloarea,path,bytes,archivo,contextoTotal))
			  				limpiarTablasAreas(login.getUsuarioId());
		       		 
			  			archivosalida=Ruta.get(contextoTotal,rb3.getString("reportes.PathReportesPlanEstudios"));
			  			System.out.println("ARCHIVO DE SALIDA : "+archivosalida);
			  			list.add(archivosalida+archivo);//pdf
			  			zise=100000;
			  			System.out.println("archivosalida : "+archivosalida);
			  			System.out.println("archivozip : "+archivozip);
			  			System.out.println("zise: "+zise);
			  			System.out.println("list: "+list.size());
			  			if(zip.ponerZip(archivosalida,archivozip,zise,list)){
			  				if(!updateReporteFecha("update_reporte_general",moduloarea,archivozip,login.getUsuarioId(),"1")){
			  					limpiarTablasAreas(login.getUsuarioId());
			  					return false;
			  				}
			  				limpiarTablasAreas(login.getUsuarioId());
			  				return true;
			  			}	          		       	  	      	  	      	  	
			  		}else{
			  			rs.close();pst.close();
			  			ponerReporteMensaje("2",moduloarea,login.getUsuarioId(),rb3.getString("reporte.PathReporte")+archivozip+"","zip2",""+archivozip,"ReporteActualizarBoletinGenerando","nNo se encontraron registros para generar el reporte de áreas!");
		    	  		System.out.println("nNo se encontraron registros para generar el reporte de áreas!");
		    	  		if(!updateReporteFecha("update_reporte_general",moduloarea,archivozip,login.getUsuarioId(),"2")){
		    	  			limpiarTablasAreas(login.getUsuarioId());
		    	  			return false;
		    	  		}
		    	  		limpiarTablasAreas(login.getUsuarioId());
		    	  		System.out.println("nSe limpio la tabla de Reporte_Area!");
		    	  		return true;       
			  		}
	   			}
	   			else{
	   				setMensaje("Usted ya mandn generar un repote de Plan de Estudios \nPor favor espere que termine, para solicitar uno nuevo");
	   				request.setAttribute("mensaje",mensaje);
	   				return false;
	   			}
	   		return true;
  			}catch(Exception e){
  			  e.printStackTrace();
  			  System.out.println("Error en process de Areas: "+e);
  			  ponerReporteMensaje("2",moduloarea,login.getUsuarioId(),rb3.getString("reporte.PathReporte")+archivozip+"","zip2",""+archivozip,"ReporteActualizarBoletin","Ocurrin excepcinn en Areas y Asignaturas");
  			  return false;
  			}
  			finally{
  				try{
  					OperacionesGenerales.closeResultSet(rs);
  					OperacionesGenerales.closeStatement(pst);    		    
  					OperacionesGenerales.closeConnection(con);
  				}catch(Exception e){}    
  			}  			
  	}
  	
  	
  	
  	
  	public boolean logros(HttpServletRequest request,String contextoTotal) throws ServletException, IOException{
      	Connection con=null;
      	PreparedStatement pst=null;
      	ResultSet rs=null;
      	String met=null,grado=null,asignatura=null,nom=null;
      	int posicion=1;
      	parameters=new HashMap();
      	String archivosalida=null;
      	int zise;
      	String nombresede=null,nombrejornada=null;
      	Collection list = new ArrayList();
      	Zip zip= new Zip();
    	  
    		try{
    			con=cursor.getConnection();		  
    			System.out.println("*********////*********nENTRn A GENERAR LOGROS!**********//**********");

    			if(!asignarBeansLogros(request)){
    				setMensaje("Error capturando datos de logros para el reporte");
    				request.setAttribute("mensaje",mensaje);
    				ir(1,er,request,response);
    				return false;
    			}  			 
    			nombresede=request.getParameter("sedenom");
    			nombrejornada=request.getParameter("jornadanom");
    			System.out.println("SEDE: "+nombresede); System.out.println("JORNADA: "+nombrejornada);
    			System.out.println("SEDECOD: "+filtroLogros.getSede()); System.out.println("JORNADACOD: "+filtroLogros.getJornada());
    			/*PARAMETROS PARA el JASPER DE LOGROS*/
    			parameters.put("INSTITUCION",login.getInst()); 
    			parameters.put("SEDE",nombresede);
    			parameters.put("JORNADA",nombrejornada);  
    			parameters.put("USUARIO",login.getUsuarioId());
    			String rutaImagen = Ruta.get(contextoTotal, rb3.getString("consulta.rutaImagen"));
    			String archivoImagen  = rutaImagen + rb3.getString("imagen") ;
    			if (isValidaImagen(new File(archivoImagen))) {
    				System.out.println("img de sed correcta");
    			}else{
    				System.out.println("img de sed error");
    			}
    			archivoImagen = (new File(archivoImagen)).getPath();
                System.out.println("archivoImagen " + archivoImagen);
      			File escudo=new File(path1+"e"+ getDane(Long.parseLong(login.getInstId())).trim()+".GIF");
      			if (escudo.exists()){
      				System.out.println("EXISTE EN MAYUSCULA");
      				parameters.put("ESCUDO_COLEGIO",path1+"e" + getDane(Long.parseLong(login.getInstId())).trim() +".GIF");
      		 }else{ 
      				parameters.put("ESCUDO_COLEGIO",path1+"e" + getDane(Long.parseLong(login.getInstId())).trim() +".gif");
      			}
      			parameters.put("ESCUDO_SED", ""+archivoImagen);
      			
    			System.out.println("PARAMETERS DEL JASPER DE LOGROS: "+parameters.values());
    			  
  	   			pst=con.prepareStatement(rb3.getString("existe_logro_generandose"));
  	   			posicion=1;
  	   			pst.clearParameters();
  	   			pst.setLong(posicion++,Long.parseLong(login.getUsuarioId()));
  	   			rs=pst.executeQuery();
  	   			  
  	   			if(!rs.next()){
  	   			  rs.close();pst.close(); 
  	   			  System.out.println("******nNO HAY NINGnN REPORTE DE LOGROS GENERANDOSE PARA ESTE USUARIO EN ESTADO CERO!*********");  				
  		   		  //met=(!filtroLogros.getMetodologia().equals("-9")?"_Metodologia_"+filtroLogros.getMetodologia():"");
  		   		  grado=(!filtroLogros.getGrado().equals("-9")?"_Grado_"+filtroLogros.getGrado():"");
  		   		  asignatura=(!filtroLogros.getAsignatura().equals("-9")?"_Asignatura_"+filtroLogros.getAsignatura():"");
  	
  		   		  nom="Sede_"+filtroLogros.getSede()+"_Jornada_"+filtroLogros.getJornada()+grado+asignatura+"_Fecha_"+f2.toString().replace(' ','_').replace(':','-').replace('.','-');
  		   		  System.out.println("NOMBRE REPORTE ASIGNATURA: "+nom);
  		   		  archivo="Reporte_Logros_"+nom+".pdf";
  		   		  archivozip="Reporte_Logros_"+nom+".zip";
  		   		  
  		   		  ponerReporte(modulologro,login.getUsuarioId(),rb3.getString("reporte.PathReporte")+archivozip+"","zip2",""+archivozip,"-1","ReporteInsertarEstado");//Estado -1
  		   		  System.out.println("Se insertn el ZIP en Reporte con estado -1");
  						
  		   		  if(!proceso_logros(archivozip,filtroLogros.getFilvigencia())){//estado cero del reporte
  		   			  System.out.println("nNO TERMINn PROCESO DE LOGROS... OCURRIn ERROR..MIRAR TRACE!");			  		  
  		   			  ponerReporteMensaje("2",modulologro,login.getUsuarioId(),rb3.getString("reporte.PathReporte")+archivozip+"","zip2",""+archivozip,"ReporteActualizarBoletinPaila","Ocurrin problema en process de logros");
  		   			  limpiarTablasLogros(login.getUsuarioId());
  		   			  if(!updateReporteFecha("update_reporte_general",modulologro,archivozip,login.getUsuarioId(),"2")){
  		   				  limpiarTablasLogros(login.getUsuarioId());
  		   				  return false;
  		   			  }
  		   			  return false;
  		   		  }
  		   		  System.out.println("SALE DE PROCESS");
  		   		  con=cursor.getConnection();
  		   		  pst=con.prepareStatement(rb3.getString("jasper_logro"));
  		   		  posicion=1;
  		   		  pst.clearParameters();
  		   		  pst.setLong(posicion++,Long.parseLong(login.getUsuarioId()));
  		   		  rs=pst.executeQuery();
  		   	  	
  		   		  if(rs.next()){
  		   			  rs.close();pst.close();
  		   			  System.out.println("nnnnnnnnnnnnnnnnSE DEBE EJECUTAR EL JASPER DE LOGROSnnnnnnnnnnnnnnnnnnnnn");
  		   			  if((reportFile2.getPath()!=null) && (parameters!=null) &&(!parameters.values().equals("0")) && (con!=null)){      	  	    
  		   				  System.out.println("***Se mandn ejecutar el jasper de logros****");
  		   				  bytes2=JasperRunManager.runReportToPdf(reportFile2.getPath(),parameters,con);
  		   			  }         	  	      		         
  		   			  if(!updateReporteEstado(modulologro,login.getUsuarioId(),rb3.getString("reporte.PathReporte")+archivozip+"","zip2",""+archivozip,"ReporteActualizarListo")) 
  		   				  limpiarTablasLogros(login.getUsuarioId());
  		   			  if(!ponerArchivo(modulologro,path,bytes2,archivo,contextoTotal))
  		   				  limpiarTablasLogros(login.getUsuarioId());
  		       		 
  		   			  archivosalida=Ruta.get(contextoTotal,rb3.getString("reportes.PathReportesPlanEstudios"));
  		   			  System.out.println("ARCHIVO DE SALIDA : "+archivosalida);
  		   			  list.add(archivosalida+archivo);//pdf
  		   			  zise=100000;
  		   			  System.out.println("archivosalida : "+archivosalida);
  		   			  System.out.println("archivozip : "+archivozip);
  		   			  System.out.println("zise: "+zise);
  		   			  System.out.println("list: "+list.size());
  		   			  if(zip.ponerZip(archivosalida,archivozip,zise,list)){
  		   				  if(!updateReporteFecha("update_reporte_general",modulologro,archivozip,login.getUsuarioId(),"1")){
  		   					  limpiarTablasLogros(login.getUsuarioId());
  		   					  System.out.println("return false; 1 " );
  		   					  return false;
  		   				  }
  		   				  limpiarTablasLogros(login.getUsuarioId());
  		   				  return true;
  		   			  }	          		       	  	      	  	      	  	
  		   		  }else{
  		   			  rs.close();pst.close(); 
  		   			  ponerReporteMensaje("2",modulologro,login.getUsuarioId(),rb3.getString("reporte.PathReporte")+archivozip+"","zip2",""+archivozip,"ReporteActualizarBoletinGenerando","nNo se encontraron registros para generar el reporte de Logros!");
  		   			  System.out.println("nNo se encontraron registros para generar el reporte de Logros!");
  		   			  if(!updateReporteFecha("update_reporte_general",modulologro,archivozip,login.getUsuarioId(),"2")){
  		   				  limpiarTablasLogros(login.getUsuarioId());
  		   				System.out.println("return false; 2 " );
  		   				  return false;
  		   			  }
  		   			  limpiarTablasLogros(login.getUsuarioId());
  		   			  System.out.println("nSe limpio la tabla de Reporte_Logro!");
  		   			  return true;       
  		   		  }
  	   			}
  		   		else{
  		   			rs.close();pst.close(); 
  		   			setMensaje("Usted ya mandn generar un repote de Plan de Logros \nPor favor espere que termine, para solicitar uno nuevo");
  		   			request.setAttribute("mensaje",mensaje);
  		   		System.out.println("return false; 3 " );
  		   			return false;
  		   		}
  	   			return true;	
    		}catch(Exception e){
    			e.printStackTrace();
    			System.out.println("Error en process de Logros: "+e);
    			ponerReporteMensaje("2",modulologro,login.getUsuarioId(),rb3.getString("reporte.PathReporte")+archivozip+"","zip2",""+archivozip,"ReporteActualizarBoletin","Ocurrin excepcinn en Logros");
    			System.out.println("return false; 5" );
    			return false;
    		}
    		finally{
    			try{
    				OperacionesGenerales.closeResultSet(rs);
    				OperacionesGenerales.closeStatement(pst);    		    
    				OperacionesGenerales.closeConnection(con);
    			}catch(Exception e){}    
    		}  			
  	}
  	
  	
  	public boolean descriptores(HttpServletRequest request,String contextoTotal) throws ServletException, IOException{
      	Connection con=null;
      	PreparedStatement pst=null;
      	ResultSet rs=null;
      	String met=null,grado=null,area=null,nom=null;
      	int posicion=1;
      	parameters=new HashMap();
      	String archivosalida=null;
      	int zise;
      	String nombresede=null,nombrejornada=null;
      	Collection list = new ArrayList();
      	Zip zip= new Zip();
    	  
      	try{
      		con=cursor.getConnection();		  
      		System.out.println("*********////*********nENTRn A GENERAR DESCRIPTORES!**********//**********");
      		if(!asignarBeansDescriptores(request)){
      			setMensaje("Error capturando datos de descriptores para el reporte");
      			request.setAttribute("mensaje",mensaje);
      			ir(1,er,request,response);
      			return false;
      		}  			 
      		nombresede=request.getParameter("sedenom");
      		nombrejornada=request.getParameter("jornadanom");
      		System.out.println("SEDE: "+nombresede); System.out.println("JORNADA: "+nombrejornada);
      		/*PARAMETROS PARA el JASPER DE AREAS*/
      		parameters.put("INSTITUCION",login.getInst()); 
      		parameters.put("SEDE",nombresede);
      		parameters.put("JORNADA",nombrejornada);  			  
      		parameters.put("USUARIO",login.getUsuarioId());
      		String rutaImagen = Ruta.get(contextoTotal, rb3.getString("consulta.rutaImagen"));
			String archivoImagen  = rutaImagen + rb3.getString("imagen") ;
			if (isValidaImagen(new File(archivoImagen))) {
				System.out.println("img de sed correcta");
			}else{
				System.out.println("img de sed error");
			}
			archivoImagen = (new File(archivoImagen)).getPath();
            System.out.println("archivoImagen " + archivoImagen);
  			File escudo=new File(path1+"e"+ getDane(Long.parseLong(login.getInstId())).trim()+".GIF");
  			if (escudo.exists()){
  				System.out.println("EXISTE EN MAYUSCULA");
  				parameters.put("ESCUDO_COLEGIO",path1+"e" + getDane(Long.parseLong(login.getInstId())).trim() +".GIF");
  		 }else{ 
  				parameters.put("ESCUDO_COLEGIO",path1+"e" + getDane(Long.parseLong(login.getInstId())).trim() +".gif");
  			}
  			parameters.put("ESCUDO_SED", ""+archivoImagen);		  		
      		System.out.println("PARAMETERS DEL JASPER DE DESCRIPTORES: "+parameters.values());
    			  
      		pst=con.prepareStatement(rb3.getString("existe_descriptor_generandose"));
      		posicion=1;
      		pst.clearParameters();
      		pst.setLong(posicion++,Long.parseLong(login.getUsuarioId()));
      		rs=pst.executeQuery();
  	   			  
      		if(!rs.next()){
      			rs.close();pst.close();con.close();
      			System.out.println("******nNO HAY NINGnN REPORTE DE DESCRIPTORES GENERANDOSE PARA ESTE USUARIO EN ESTADO CERO!*********");  				
      			//met=(!filtroDescriptores.getMetodologia().equals("-9")?"_Metodologia_"+filtroDescriptores.getMetodologia():"");
      			grado=(!filtroDescriptores.getGrado().equals("-9")?"_Grado_"+filtroDescriptores.getGrado():"");
      			area=(!filtroDescriptores.getArea().equals("-9")?"_Area_"+filtroDescriptores.getArea():"");
  	
      			nom="Sede_"+filtroDescriptores.getSede()+"_Jornada_"+filtroDescriptores.getJornada()+grado+area+"_Fecha_"+f2.toString().replace(' ','_').replace(':','-').replace('.','-');
      			System.out.println("NOMBRE REPORTE: "+nom);
      			archivo="Reporte_Descriptores_"+nom+".pdf";
      			archivozip="Reporte_Descriptores_"+nom+".zip";
  		   		  
      			ponerReporte(modulodescriptor,login.getUsuarioId(),rb3.getString("reporte.PathReporte")+archivozip+"","zip2",""+archivozip,"-1","ReporteInsertarEstado");//Estado -1
      			System.out.println("Se insertn el ZIP en Reporte con estado -1");
  						
      			if(!proceso_descriptores(archivozip,filtroDescriptores.getFilvigencia())){//estado cero del reporte
      				System.out.println("nNO TERMINn PROCESO DE DESCRPTORES... OCURRIn ERROR..MIRAR TRACE!");			  		  
      				ponerReporteMensaje("2",modulodescriptor,login.getUsuarioId(),rb3.getString("reporte.PathReporte")+archivozip+"","zip2",""+archivozip,"ReporteActualizarBoletinPaila","Ocurrin problema en process de descriptores");
      				limpiarTablasDescriptores(login.getUsuarioId());
      				if(!updateReporteFecha("update_reporte_general",modulodescriptor,archivozip,login.getUsuarioId(),"2")){
      					limpiarTablasDescriptores(login.getUsuarioId());
      					return false;
      				}
      				return false;
      			}
      			System.out.println("SALE DE PROCESS");
      			con=cursor.getConnection();
      			pst=con.prepareStatement(rb3.getString("jasper_descriptor"));
      			posicion=1;
      			pst.clearParameters();
      			pst.setLong(posicion++,Long.parseLong(login.getUsuarioId()));
  		   	  	rs=pst.executeQuery();
  		   	  	
  		   	  	if(rs.next()){
  		   	  		rs.close();pst.close();
  		   	  		System.out.println("nnnnnnnnnnnnnnnnSE DEBE EJECUTAR EL JASPER DE DESCRPTORESnnnnnnnnnnnnnnnnnnnnn");
  		   	  		if((reportFile3.getPath()!=null) && (parameters!=null) &&(!parameters.values().equals("0")) && (con!=null)){      	  	    
  		   	  			System.out.println("***Se mandn ejecutar el jasper de descriptores****");
  		   	  			bytes=JasperRunManager.runReportToPdf(reportFile3.getPath(),parameters,con);
  		   	  		}         	  	      		         
  		   	  		if(!updateReporteEstado(modulodescriptor,login.getUsuarioId(),rb3.getString("reporte.PathReporte")+archivozip+"","zip2",""+archivozip,"ReporteActualizarListo")) 
  		   	  			limpiarTablasDescriptores(login.getUsuarioId());
  		   	  		if(!ponerArchivo(modulodescriptor,path,bytes,archivo,contextoTotal))
  		   	  			limpiarTablasDescriptores(login.getUsuarioId());
  		       		 
  		   	  		archivosalida=Ruta.get(contextoTotal,rb3.getString("reportes.PathReportesPlanEstudios"));
  		   	  		System.out.println("ARCHIVO DE SALIDA : "+archivosalida);
  		   	  		list.add(archivosalida+archivo);//pdf
  		   	  		zise=100000;
  		   	  		System.out.println("archivosalida : "+archivosalida);
  		   	  		System.out.println("archivozip : "+archivozip);
  		   	  		System.out.println("bytes: "+bytes);
  		   	  		System.out.println("list: "+list.size());
  		   	  		if(zip.ponerZip(archivosalida,archivozip,zise,list)){
  		   	  			if(!updateReporteFecha("update_reporte_general",modulodescriptor,archivozip,login.getUsuarioId(),"1")){
  		   	  				limpiarTablasDescriptores(login.getUsuarioId());
  		   	  				return false;
  		   	  			}
  		   	  			limpiarTablasDescriptores(login.getUsuarioId());
  		   	  			return true;
  		   	  		}	          		       	  	      	  	      	  	
  		   	  	}else{
  		   	  		rs.close();pst.close();con.close();
  		   	  		ponerReporteMensaje("2",modulodescriptor,login.getUsuarioId(),rb3.getString("reporte.PathReporte")+archivozip+"","zip2",""+archivozip,"ReporteActualizarBoletinGenerando","nNo se encontraron registros para generar el reporte de Descriptores!");
  		   	  		System.out.println("nNo se encontraron registros para generar el reporte de Descriptores!");
  		   	  		if(!updateReporteFecha("update_reporte_general",modulodescriptor,archivozip,login.getUsuarioId(),"2")){
  		   	  			limpiarTablasDescriptores(login.getUsuarioId());
  		   	  			return false;
  		   	  		}
  		   	  		limpiarTablasDescriptores(login.getUsuarioId());
  		   	  		System.out.println("nSe limpio la tabla de Reporte_Descriptor!");
  		   	  		return true;       
  		   	  	}
      		}
      		else{
      			rs.close();pst.close();con.close();
      			setMensaje("Usted ya mandn generar un repote de Plan de Descriptores \nPor favor espere que termine, para solicitar uno nuevo");
      			request.setAttribute("mensaje",mensaje);
      			return false;
      		}
      		return true;	
      	}catch(Exception e){
      		e.printStackTrace();
      		System.out.println("Error en process de Descriptores: "+e);
      		ponerReporteMensaje("2",modulodescriptor,login.getUsuarioId(),rb3.getString("reporte.PathReporte")+archivozip+"","zip2",""+archivozip,"ReporteActualizarBoletin","Ocurrin excepcinn en Descriptor");
      		return false;
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
  	*	Funcinn: Ejecuta el proceso para generar el reporte de Areas y Asignaturas <BR>
  	*	@param	String us
  	*	@param	String rec
  	*	@param	String tipo
  	*	@param	String nombre
  	*	@param	String prepared
  	**/

  	public boolean proceso_areas(String archivozip1,String vigencia){
  		Connection con=null;
  		PreparedStatement pst=null;
  		int posicion=1;  		
  		bytes=null;
  	 		
  		try{
    		limpiarTablasAreas(login.getUsuarioId());
		  	System.out.println("nSe limpin la tabla antes de ser llenada!");
		  	updateReporteEstado(moduloarea,login.getUsuarioId(),rb3.getString("reporte.PathReporte")+archivozip1+"","zip2",""+archivozip1,"ReporteActualizarGenerando");
		  	updateReporteFecha("update_reporte_general",moduloarea,archivozip1,login.getUsuarioId(),"0");    				  	
		  	con=cursor.getConnection(); 
  		     
  		  if(filtroAreas.getGrado().equals("-9") && filtroAreas.getArea().equals("-9")){
  		    System.out.println("SEDE-JORNADA-METODOLOGIA");  
  		    System.out.println("consulta === " + rb3.getString("insert_reporte_area"));
  	     	pst=con.prepareStatement(rb3.getString("insert_reporte_area"));
  	      posicion=1;
  	      pst.clearParameters();
  	      pst.setLong(posicion++,Long.parseLong(login.getUsuarioId()));
  	      System.out.println("login.getUsuarioId() " +login.getUsuarioId());
  	      pst.setLong(posicion++,Long.parseLong(vigencia));
  	      System.out.println("vigencia " + vigencia);
  	      pst.setLong(posicion++,Long.parseLong(filtroAreas.getInstitucion()));
  	      System.out.println("filtroAreas.getInstitucion() " + filtroAreas.getInstitucion());
  	      pst.setLong(posicion++,Long.parseLong(filtroAreas.getMetodologia()));
  	      System.out.println("filtroAreas.getMetodologia() " + filtroAreas.getMetodologia());
  	      pst.setLong(posicion++,Long.parseLong(vigencia));
  	      pst.executeUpdate();
  	      pst.close();
  	      System.out.println("ejecutn insert_reporte_area");
  		  }else{
  	  		  if(!filtroAreas.getGrado().equals("-9") && filtroAreas.getArea().equals("-9")){
  	    		    System.out.println("SEDE-JORNADA-METODOLOGIA-GRADO");  
  	    	     	pst=con.prepareStatement(rb3.getString("insert_reporte_area_grado"));
  	    	      posicion=1;
  	    	      pst.clearParameters();
  	    	      pst.setLong(posicion++,Long.parseLong(login.getUsuarioId()));
  	    	      pst.setLong(posicion++,Long.parseLong(vigencia));
  	    	      pst.setLong(posicion++,Long.parseLong(filtroAreas.getInstitucion()));
  	    	      pst.setLong(posicion++,Long.parseLong(filtroAreas.getMetodologia()));
  	    	      pst.setLong(posicion++,Long.parseLong(filtroAreas.getGrado()));
  	    	      pst.setLong(posicion++,Long.parseLong(vigencia));
  	    	      pst.executeUpdate();
  	    	      pst.close();
  	    	      System.out.println("ejecutn insert_reporte_area_grado");  		  
  	    		  }else{
  	    	  		  if(!filtroAreas.getGrado().equals("-9") && !filtroAreas.getArea().equals("-9")){
  	    	    		    System.out.println("SEDE-JORNADA-METODOLOGIA-GRADO-AREA");  
  	    	    		    pst=con.prepareStatement(rb3.getString("insert_reporte_area_grado_area"));
  	    	    	      posicion=1;
  	    	    	      pst.clearParameters();
  	    	    	      pst.setLong(posicion++,Long.parseLong(login.getUsuarioId()));
  	    	    	      pst.setLong(posicion++,Long.parseLong(vigencia));
  	    	    	      pst.setLong(posicion++,Long.parseLong(filtroAreas.getInstitucion()));
  	    	    	      pst.setLong(posicion++,Long.parseLong(filtroAreas.getMetodologia()));
  	    	    	      pst.setLong(posicion++,Long.parseLong(filtroAreas.getArea()));
  	    	    	      pst.setLong(posicion++,Long.parseLong(filtroAreas.getGrado()));
  	    	    	      pst.setLong(posicion++,Long.parseLong(vigencia));
  	    	    	      pst.executeUpdate();
  	    	    	      pst.close();
  	    	    	      System.out.println("ejecutn insert_reporte_area_grado_area");  		  
  	    	    		  }  	    		      
  	    		  }
  		    }  
  		  } 	
  			catch(InternalErrorException e){
  					System.out.println("Error Interno: "+e.toString());
  				return false;	
  			}
  			catch(Exception e ){
  					System.out.println("Error Exception: "+e.toString());
  				return false;
  			}
  			finally{
  				try{
  				    OperacionesGenerales.closeStatement(pst);
  				    OperacionesGenerales.closeConnection(con);
  				    }catch(Exception e){}
  		  }
  			return true;
  	}
  	

  	/**
  	*	Funcinn: Ejecuta el proceso para generar el reporte de Logros <BR>
  	*	@param	String us
  	*	@param	String rec
  	*	@param	String tipo
  	*	@param	String nombre
  	*	@param	String prepared
  	**/

  	public boolean proceso_logros(String archivozip1,String vigencia){	
  		Connection con=null;
  		PreparedStatement pst=null;
  		int posicion=1;  		
  		bytes=null;
  	 		
  		try{
  		  limpiarTablasLogros(login.getUsuarioId());
		  	System.out.println("nSe limpin la tabla antes de ser llenada!");
		  	updateReporteEstado(modulologro,login.getUsuarioId(),rb3.getString("reporte.PathReporte")+archivozip1+"","zip2",""+archivozip1,"ReporteActualizarGenerando");
		  	updateReporteFecha("update_reporte_general",modulologro,archivozip1,login.getUsuarioId(),"0");    				  	
		  	con=cursor.getConnection(); 
  		    
  		  if(filtroLogros.getGrado().equals("-9") && filtroLogros.getAsignatura().equals("-9") ){
  		    if(filtroLogros.getPeriodoinicial().equals("-9") && filtroLogros.getPeriodofinal().equals("-9")){  
	  		    System.out.println("SEDE-JORNADA-METODOLOGIA-SIN PERIODOS");
	  	     	pst=con.prepareStatement(rb3.getString("insert_reporte_logro"));
	  	      posicion=1;
	  	      pst.clearParameters();
	  	      pst.setLong(posicion++,Long.parseLong(login.getUsuarioId()));
	  	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getInstitucion()));
	  	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getMetodologia()));
	  	      pst.setLong(posicion++,Long.parseLong(vigencia));
	  	      pst.executeUpdate();
	  	      pst.close();
	  	      System.out.println("ejecutn insert_reporte_logro");
  		    }else{
  		       if(!filtroLogros.getPeriodoinicial().equals("-9") && !filtroLogros.getPeriodofinal().equals("-9")){
  		           System.out.println("SEDE-JORNADA-METODOLOGIA-CON PERIODOS");
  			  	     	pst=con.prepareStatement(rb3.getString("insert_reporte_logro_perini_perfin"));
  			  	      posicion=1;
  			  	      pst.clearParameters();
  			  	      pst.setLong(posicion++,Long.parseLong(login.getUsuarioId()));
  			  	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getInstitucion()));
  			  	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getMetodologia()));
  			  	      pst.setLong(posicion++,Long.parseLong(vigencia));
  			  	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getPeriodoinicial()));
  			  	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getPeriodofinal()));
  			  	      pst.executeUpdate();
  			  	      pst.close();
  			  	      System.out.println("ejecutn insert_reporte_logro_perini_perfin");
  		       }else{
  	  		       if(!filtroLogros.getPeriodoinicial().equals("-9") && filtroLogros.getPeriodofinal().equals("-9")){
  	  		           System.out.println("SEDE-JORNADA-METODOLOGIA-CON PERIODO_INICIAL");
  	  			  	     	pst=con.prepareStatement(rb3.getString("insert_reporte_logro_perini"));
  	  			  	      posicion=1;
  	  			  	      pst.clearParameters();
  	  			  	      pst.setLong(posicion++,Long.parseLong(login.getUsuarioId()));
  	  			  	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getInstitucion()));
  	  			  	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getMetodologia()));
  	  			  	      pst.setLong(posicion++,Long.parseLong(vigencia));
  	  			  	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getPeriodoinicial()));
  	  			  	      pst.executeUpdate();
  	  			  	      pst.close();
  	  			  	      System.out.println("ejecutn insert_reporte_logro_perini");
  	  		       }else{
  	  		          if(filtroLogros.getPeriodoinicial().equals("-9") && !filtroLogros.getPeriodofinal().equals("-9")){
  	  		           System.out.println("SEDE-JORNADA-METODOLOGIA-CON PERIODO_FINAL");
 	  			  	     	 pst=con.prepareStatement(rb3.getString("insert_reporte_logro_perfin"));
 	  			  	       posicion=1;
 	  			  	       pst.clearParameters();
 	  			  	       pst.setLong(posicion++,Long.parseLong(login.getUsuarioId()));
 	  			  	       pst.setLong(posicion++,Long.parseLong(filtroLogros.getInstitucion()));
 	  			  	       pst.setLong(posicion++,Long.parseLong(filtroLogros.getMetodologia()));
 	  			  	       pst.setLong(posicion++,Long.parseLong(vigencia));
 	  			  	       pst.setLong(posicion++,Long.parseLong(filtroLogros.getPeriodofinal()));
 	  			  	       pst.executeUpdate();
 	  			  	       pst.close();
 	  			  	       System.out.println("ejecutn insert_reporte_logro_perfin");
  	  		          }
  	  		       }
  		       }  		           
  		    }
	  	      
  		  }else{
  	  		  if(!filtroLogros.getGrado().equals("-9") && filtroLogros.getAsignatura().equals("-9")){
  	  		      if(filtroLogros.getPeriodoinicial().equals("-9") && filtroLogros.getPeriodofinal().equals("-9")){  
	  	  		      System.out.println("SEDE-JORNADA-METODOLOGIA-GRADO_SIN PERIODOS");  
	  	    	     	pst=con.prepareStatement(rb3.getString("insert_reporte_logro_grado"));
	  	    	      posicion=1;
	  	    	      pst.clearParameters();
	  	    	      pst.setLong(posicion++,Long.parseLong(login.getUsuarioId()));
	  	    	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getInstitucion()));
	  	    	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getMetodologia()));
	  	    	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getGrado()));
	  	    	      pst.setLong(posicion++,Long.parseLong(vigencia));
	  	    	      pst.executeUpdate();
	  	    	      pst.close();
	  	    	      System.out.println("ejecutn insert_reporte_logro_grado");
  	  		      }else{
  	  		         if(!filtroLogros.getPeriodoinicial().equals("-9") && !filtroLogros.getPeriodofinal().equals("-9")){
  	 	  	  		      System.out.println("SEDE-JORNADA-METODOLOGIA-GRADO_CON PERIODOS");  
  		  	    	     	pst=con.prepareStatement(rb3.getString("insert_reporte_logro_grado_perini_perfin"));
  		  	    	      posicion=1;
  		  	    	      pst.clearParameters();
  		  	    	      pst.setLong(posicion++,Long.parseLong(login.getUsuarioId()));
  		  	    	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getInstitucion()));
  		  	    	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getMetodologia()));
  		  	    	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getGrado()));
  		  	    	      pst.setLong(posicion++,Long.parseLong(vigencia));
  	  			  	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getPeriodoinicial().trim()));
  	  			  	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getPeriodofinal().trim()));  		  	    	      
  		  	    	      pst.executeUpdate();
  		  	    	      pst.close();
  		  	    	      System.out.println("ejecutn insert_reporte_logro_grado_perini_perfin");
  	  		         }else{
  	  		             if(!filtroLogros.getPeriodoinicial().equals("-9") && filtroLogros.getPeriodofinal().equals("-9")){
  	  		                System.out.println("SEDE-JORNADA-METODOLOGIA-GRADO_CON PERIODO INICIAL");
  	   		  	    	     	pst=con.prepareStatement(rb3.getString("insert_reporte_logro_grado_perini"));
  	  		  	    	      posicion=1;
  	  		  	    	      pst.clearParameters();
  	  		  	    	      pst.setLong(posicion++,Long.parseLong(login.getUsuarioId()));
  	  		  	    	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getInstitucion()));
  	  		  	    	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getMetodologia()));
  	  		  	    	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getGrado()));
  	  		  	    	      pst.setLong(posicion++,Long.parseLong(vigencia));
  	  	  			  	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getPeriodoinicial()));
  	  		  	    	      pst.executeUpdate();
  	  		  	    	      pst.close();
  	  		  	    	      System.out.println("ejecutn insert_reporte_logro_grado_perini");
  	  		             }else{
  	  		                 if(filtroLogros.getPeriodoinicial().equals("-9") && !filtroLogros.getPeriodofinal().equals("-9")){
  	   	  		                System.out.println("SEDE-JORNADA-METODOLOGIA-GRADO_CON PERIODO FINAL");
  	  	   		  	    	     	pst=con.prepareStatement(rb3.getString("insert_reporte_logro_grado_perfin"));
  	  	  		  	    	      posicion=1;
  	  	  		  	    	      pst.clearParameters();
  	  	  		  	    	      pst.setLong(posicion++,Long.parseLong(login.getUsuarioId()));
  	  	  		  	    	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getInstitucion()));
  	  	  		  	    	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getMetodologia()));
  	  	  		  	    	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getGrado()));
  	  	  		  	    	      pst.setLong(posicion++,Long.parseLong(vigencia));
  	  	  	  			  	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getPeriodofinal()));
  	  	  		  	    	      pst.executeUpdate();
  	  	  		  	    	      pst.close();
  	  	  		  	    	      System.out.println("ejecutn insert_reporte_logro_grado_perfin");
  	  		                 }
  	  		              }
  	  		           }   	  		         
  	  		      }
  	    		  }else{
  	    	  		  if(!filtroLogros.getGrado().equals("-9") && !filtroLogros.getAsignatura().equals("-9")){
  	    	  		     if(filtroLogros.getPeriodoinicial().equals("-9") && filtroLogros.getPeriodofinal().equals("-9")){  
  	    	    		    System.out.println("SEDE-JORNADA-METODOLOGIA-GRADO-ASIGNATURA-SIN PERIODOS");  
  	    	    	     	pst=con.prepareStatement(rb3.getString("insert_reporte_logro_grado_asignatura"));
  	    	    	      posicion=1;
  	    	    	      pst.clearParameters();
  	    	    	      pst.setLong(posicion++,Long.parseLong(login.getUsuarioId()));
  	    	    	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getInstitucion()));
  	    	    	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getMetodologia()));
  	    	    	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getAsignatura()));
  	    	    	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getGrado()));
  	    	    	      pst.setLong(posicion++,Long.parseLong(vigencia));
  	    	    	      pst.executeUpdate();
  	    	    	      pst.close();
  	    	    	      System.out.println("ejecutn insert_reporte_logro_grado_asignatura");
  	    	  		     }else{
  	    	  		        if(!filtroLogros.getPeriodoinicial().equals("-9") && !filtroLogros.getPeriodofinal().equals("-9")){
  	    	    	    		    System.out.println("SEDE-JORNADA-METODOLOGIA-GRADO-ASIGNATURA-CON PERIODOS");  
  	    	    	    	     	pst=con.prepareStatement(rb3.getString("insert_reporte_logro_grado_asignatura_perini_perfin"));
  	    	    	    	      posicion=1;
  	    	    	    	      pst.clearParameters();
  	    	    	    	      pst.setLong(posicion++,Long.parseLong(login.getUsuarioId()));
  	    	    	    	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getInstitucion()));
  	    	    	    	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getMetodologia()));
  	    	    	    	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getAsignatura()));
  	    	    	    	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getGrado()));
  	    	    	    	      pst.setLong(posicion++,Long.parseLong(vigencia));
  	    	  			  	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getPeriodoinicial()));
  	    	  			  	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getPeriodofinal()));
  	    	    	    	      pst.executeUpdate();
  	    	    	    	      pst.close();
  	    	    	    	      System.out.println("ejecutn insert_reporte_logro_grado_asignatura_perini_perfin");
  	    	  		       }else{  	    	  		           
  	    	  		          if(!filtroLogros.getPeriodoinicial().equals("-9") && filtroLogros.getPeriodofinal().equals("-9")){
  	    	  		              System.out.println("SEDE-JORNADA-METODOLOGIA-GRADO-ASIGNATURA-CON PERIODO INICIAL");
    	    	    	    	     	pst=con.prepareStatement(rb3.getString("insert_reporte_logro_grado_asignatura_perini"));
    	    	    	    	      posicion=1;
    	    	    	    	      pst.clearParameters();
    	    	    	    	      pst.setLong(posicion++,Long.parseLong(login.getUsuarioId()));
    	    	    	    	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getInstitucion()));
    	    	    	    	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getMetodologia()));
    	    	    	    	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getAsignatura()));
    	    	    	    	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getGrado()));
    	    	    	    	      pst.setLong(posicion++,Long.parseLong(vigencia));
    	    	  			  	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getPeriodoinicial()));
    	    	    	    	      pst.executeUpdate();
    	    	    	    	      pst.close();
    	    	    	    	      System.out.println("ejecutn insert_reporte_logro_grado_asignatura_perini");
  	    	  		          }else{
  	    	  		              if(filtroLogros.getPeriodoinicial().equals("-9") && !filtroLogros.getPeriodofinal().equals("-9")){
  	  	    	  		              System.out.println("SEDE-JORNADA-METODOLOGIA-GRADO-ASIGNATURA-CON PERIODO FINAL");
  	    	    	    	    	     	pst=con.prepareStatement(rb3.getString("insert_reporte_logro_grado_asignatura_perfin"));
  	    	    	    	    	      posicion=1;
  	    	    	    	    	      pst.clearParameters();
  	    	    	    	    	      pst.setLong(posicion++,Long.parseLong(login.getUsuarioId()));
  	    	    	    	    	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getInstitucion()));
  	    	    	    	    	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getMetodologia()));
  	    	    	    	    	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getAsignatura()));
  	    	    	    	    	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getGrado()));
  	    	    	    	    	      pst.setLong(posicion++,Long.parseLong(vigencia));
  	    	    	  			  	      pst.setLong(posicion++,Long.parseLong(filtroLogros.getPeriodofinal()));
  	    	    	    	    	      pst.executeUpdate();
  	    	    	    	    	      pst.close();
  	    	    	    	    	      System.out.println("ejecutn insert_reporte_logro_grado_asignatura_perfin");
  	    	  		              }
  	    	  		           }
  	    	  		        }
  	    	  		      }
  	    	    		  }  	    		      
  	    		    }
  		      }  
  		 
  	 		 
  		  }	
  			catch(InternalErrorException e){
  					System.out.println("Error Interno: "+e.toString());
  				return false;	
  			}
  			catch(Exception e ){
  					System.out.println("Error Exception: "+e.toString());
  				return false;
  			}
  			finally{
  				try{
  				    OperacionesGenerales.closeStatement(pst);
  				    OperacionesGenerales.closeConnection(con);
  				    }catch(Exception e){}
  		  }
  			return true;
  	}
  	

  	/**
  	*	Funcinn: Ejecuta el proceso para generar el reporte de Areas y Asignaturas <BR>
  	*	@param	String us
  	*	@param	String rec
  	*	@param	String tipo
  	*	@param	String nombre
  	*	@param	String prepared
  	**/

  	public boolean proceso_descriptores(String archivozip1,String vigencia){	
  		Connection con=null;
  		PreparedStatement pst=null;
  		int posicion=1;  		
  		bytes=null;
  	 		
  		try{
  		  limpiarTablasDescriptores(login.getUsuarioId());  		  
		  	System.out.println("nSe limpin la tabla antes de ser llenada!");
		  	updateReporteEstado(modulodescriptor,login.getUsuarioId(),rb3.getString("reporte.PathReporte")+archivozip1+"","zip2",""+archivozip1,"ReporteActualizarGenerando");
		  	updateReporteFecha("update_reporte_general",moduloarea,archivozip1,login.getUsuarioId(),"0");    				  	
		  	con=cursor.getConnection();
		  	System.out.println("ENTRn POR PROCESO DESCRIPTORES");
  		  if(!filtroDescriptores.getSede().equals("-9") && !filtroDescriptores.getJornada().equals("-9") && !filtroDescriptores.getMetodologia().equals("-9")){
  		    
  		  if(filtroDescriptores.getGrado().equals("-9") && filtroDescriptores.getArea().equals("-9")){
  		   if(filtroDescriptores.getPeriodoinicial().equals("-9") && filtroDescriptores.getPeriodofinal().equals("-9")){   
	  		    System.out.println("SEDE-JORNADA-METODOLOGIA_SIN PERIODOS");
	  	     	pst=con.prepareStatement(rb3.getString("insert_reporte_descriptor"));
	  	      posicion=1;
	  	      pst.clearParameters();
	  	      pst.setLong(posicion++,Long.parseLong(login.getUsuarioId()));
	  	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getInstitucion()));
	  	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getMetodologia()));
	  	      pst.setLong(posicion++,Long.parseLong(vigencia));
	  	      pst.executeUpdate();
	  	      pst.close();
	  	      System.out.println("ejecutn insert_reporte_descriptor");
  		   }else{
  		      if(!filtroDescriptores.getPeriodoinicial().equals("-9") && !filtroDescriptores.getPeriodofinal().equals("-9")){
  		  		    System.out.println("SEDE-JORNADA-METODOLOGIA_CON PERIODOS");  		    
  		  	     	pst=con.prepareStatement(rb3.getString("insert_reporte_descriptor_perini_perfin"));
  		  	      posicion=1;
  		  	      pst.clearParameters();
  		  	      pst.setLong(posicion++,Long.parseLong(login.getUsuarioId()));
  		  	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getInstitucion()));
  		  	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getMetodologia()));
  		  	      pst.setLong(posicion++,Long.parseLong(vigencia));
			  	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getPeriodoinicial()));
			  	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getPeriodofinal()));
  		  	      pst.executeUpdate();
  		  	      pst.close();
  		  	      System.out.println("ejecutn insert_reporte_descriptor_perini_perfin");
  		      }else{
  		          if(!filtroDescriptores.getPeriodoinicial().equals("-9") && filtroDescriptores.getPeriodofinal().equals("-9")){
  		              System.out.println("SEDE-JORNADA-METODOLOGIA_CON PERIODO INICIAL");
  		              pst=con.prepareStatement(rb3.getString("insert_reporte_descriptor_perini"));
  	  		  	      posicion=1;
  	  		  	      pst.clearParameters();
  	  		  	      pst.setLong(posicion++,Long.parseLong(login.getUsuarioId()));
  	  		  	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getInstitucion()));
  	  		  	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getMetodologia()));
  	  		  	      pst.setLong(posicion++,Long.parseLong(vigencia));
  				  	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getPeriodoinicial()));
  	  		  	      pst.executeUpdate();
  	  		  	      pst.close();
  	  		  	      System.out.println("ejecutn insert_reporte_descriptor_perini");
  		          }else{
  		              if(filtroDescriptores.getPeriodoinicial().equals("-9") && !filtroDescriptores.getPeriodofinal().equals("-9")){
  	  		              System.out.println("SEDE-JORNADA-METODOLOGIA_CON PERIODO FINAL");
  	  		              pst=con.prepareStatement(rb3.getString("insert_reporte_descriptor_perfin"));
  	  	  		  	      posicion=1;
  	  	  		  	      pst.clearParameters();
  	  	  		  	      pst.setLong(posicion++,Long.parseLong(login.getUsuarioId()));
  	  	  		  	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getInstitucion()));
  	  	  		  	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getMetodologia()));
  	  	  		  	      pst.setLong(posicion++,Long.parseLong(vigencia));
  	  				  	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getPeriodofinal()));
  	  	  		  	      pst.executeUpdate();
  	  	  		  	      pst.close();
  	  	  		  	      System.out.println("ejecutn insert_reporte_descriptor_perfin");
  		              }
  		          }
  		      }    
  		   }
  	      
  		  }else{
  	  		  if(!filtroDescriptores.getGrado().equals("-9") && filtroDescriptores.getArea().equals("-9")){
  	  		    if(filtroDescriptores.getPeriodoinicial().equals("-9") && filtroDescriptores.getPeriodofinal().equals("-9")){  	  		      
  	  		      System.out.println("SEDE-JORNADA-METODOLOGIA-GRADO_SIN PERIODOS");  
  	    	     	pst=con.prepareStatement(rb3.getString("insert_reporte_descriptor_grado"));
  	    	      posicion=1;
  	    	      pst.clearParameters();
  	    	      pst.setLong(posicion++,Long.parseLong(login.getUsuarioId()));
  	    	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getInstitucion()));
  	    	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getMetodologia()));
  	    	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getGrado()));
  	    	      pst.setLong(posicion++,Long.parseLong(vigencia));
  	    	      pst.executeUpdate();
  	    	      pst.close();
  	    	      System.out.println("ejecutn insert_reporte_descriptor_grado");  		  
  	  		    }else{
  	  		      if(!filtroDescriptores.getPeriodoinicial().equals("-9") && !filtroDescriptores.getPeriodofinal().equals("-9")){
  	  	  		      System.out.println("SEDE-JORNADA-METODOLOGIA-GRADO_CON PERIODOS");  
  	  	    	     	pst=con.prepareStatement(rb3.getString("insert_reporte_descriptor_grado_perini_perfin"));
  	  	    	      posicion=1;
  	  	    	      pst.clearParameters();
  	  	    	      pst.setLong(posicion++,Long.parseLong(login.getUsuarioId()));
  	  	    	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getInstitucion()));
  	  	    	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getMetodologia()));
  	  	    	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getGrado()));
  	  	    	      pst.setLong(posicion++,Long.parseLong(vigencia));
  				  	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getPeriodoinicial()));
  				  	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getPeriodofinal()));
  	  	    	      pst.executeUpdate();
  	  	    	      pst.close();
  	  	    	      System.out.println("ejecutn insert_reporte_descriptor_grado_perini_perfin");  		  
  	  		      }else{
  	  		          if(!filtroDescriptores.getPeriodoinicial().equals("-9") && filtroDescriptores.getPeriodofinal().equals("-9")){
  	  	  	  		      System.out.println("SEDE-JORNADA-METODOLOGIA-GRADO_CON PERIODO INICIAL");  
  	  	  	    	     	pst=con.prepareStatement(rb3.getString("insert_reporte_descriptor_grado_perini"));
  	  	  	    	      posicion=1;
  	  	  	    	      pst.clearParameters();
  	  	  	    	      pst.setLong(posicion++,Long.parseLong(login.getUsuarioId()));
  	  	  	    	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getInstitucion()));
  	  	  	    	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getMetodologia()));
  	  	  	    	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getGrado()));
  	  	  	    	      pst.setLong(posicion++,Long.parseLong(vigencia));
  	  				  	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getPeriodoinicial()));
  	  	  	    	      pst.executeUpdate();
  	  	  	    	      pst.close();
  	  	  	    	      System.out.println("ejecutn insert_reporte_descriptor_grado_perini");
  	  		          }else{
  	  		              if(filtroDescriptores.getPeriodoinicial().equals("-9") && !filtroDescriptores.getPeriodofinal().equals("-9")){
  	  	  	  	  		      System.out.println("SEDE-JORNADA-METODOLOGIA-GRADO_CON PERIODO FINAL");  
  	  	  	  	    	     	pst=con.prepareStatement(rb3.getString("insert_reporte_descriptor_grado_perfin"));
  	  	  	  	    	      posicion=1;
  	  	  	  	    	      pst.clearParameters();
  	  	  	  	    	      pst.setLong(posicion++,Long.parseLong(login.getUsuarioId()));
  	  	  	  	    	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getInstitucion()));
  	  	  	  	    	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getMetodologia()));
  	  	  	  	    	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getGrado()));
  	  	  	  	    	      pst.setLong(posicion++,Long.parseLong(vigencia));
  	  	  				  	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getPeriodofinal()));
  	  	  	  	    	      pst.executeUpdate();
  	  	  	  	    	      pst.close();
  	  	  	  	    	      System.out.println("ejecutn insert_reporte_descriptor_grado_perfin");
  	  		              }
  	  		          }
  	  		      }  
  	  		    }
  	    		  }else{
  	    	  		  if(!filtroDescriptores.getGrado().equals("-9") && !filtroDescriptores.getArea().equals("-9")){
  	    	  		    if(filtroDescriptores.getPeriodoinicial().equals("-9") && filtroDescriptores.getPeriodofinal().equals("-9")){  
  	    	    		    System.out.println("SEDE-JORNADA-METODOLOGIA-GRADO-AREA_SIN PERIODOS");
  	    	    		    pst=con.prepareStatement(rb3.getString("insert_reporte_descriptor_grado_area"));
  	    	    	      posicion=1;
  	    	    	      pst.clearParameters();
  	    	    	      pst.setLong(posicion++,Long.parseLong(login.getUsuarioId()));
  	    	    	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getInstitucion()));
  	    	    	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getMetodologia()));
  	    	    	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getArea()));
  	    	    	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getGrado()));
  	    	    	      pst.setLong(posicion++,Long.parseLong(vigencia));
  	    	    	      pst.executeUpdate();
  	    	    	      pst.close();
  	    	    	      System.out.println("ejecutn insert_reporte_descriptor_grado_area");
  	    	  		    }else{
  	    	  		      if(!filtroDescriptores.getPeriodoinicial().equals("-9") && !filtroDescriptores.getPeriodofinal().equals("-9")){
  	  	    	    		    System.out.println("SEDE-JORNADA-METODOLOGIA-GRADO-AREA_CON PERIODOS");
  	  	    	    		    pst=con.prepareStatement(rb3.getString("insert_reporte_descriptor_grado_area_perini_perfin"));
  	  	    	    	      posicion=1;
  	  	    	    	      pst.clearParameters();
  	  	    	    	      pst.setLong(posicion++,Long.parseLong(login.getUsuarioId()));
  	  	    	    	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getInstitucion()));
  	  	    	    	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getMetodologia()));
  	  	    	    	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getArea()));
  	  	    	    	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getGrado()));
  	  	    	    	      pst.setLong(posicion++,Long.parseLong(vigencia));
  	  	  			  	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getPeriodoinicial()));
  	  	  			  	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getPeriodofinal()));
  	  	    	    	      pst.executeUpdate();
  	  	    	    	      pst.close();
  	  	    	    	      System.out.println("ejecutn insert_reporte_descriptor_grado_area_perini_perfin");
  	    	  		      }else{
  	    	  		         if(!filtroDescriptores.getPeriodoinicial().equals("-9") && filtroDescriptores.getPeriodofinal().equals("-9")){
  	  	  	    	    		    System.out.println("SEDE-JORNADA-METODOLOGIA-GRADO-AREA_CON PERIODO INICIAL");
  	  	  	    	    		    pst=con.prepareStatement(rb3.getString("insert_reporte_descriptor_grado_area_perini"));
  	  	  	    	    	      posicion=1;
  	  	  	    	    	      pst.clearParameters();
  	  	  	    	    	      pst.setLong(posicion++,Long.parseLong(login.getUsuarioId()));
  	  	  	    	    	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getInstitucion()));
  	  	  	    	    	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getMetodologia()));
  	  	  	    	    	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getArea()));
  	  	  	    	    	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getGrado()));
  	  	  	    	    	      pst.setLong(posicion++,Long.parseLong(vigencia));
  	  	  	  			  	      pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getPeriodoinicial()));
  	  	  	    	    	      pst.executeUpdate();
  	  	  	    	    	      pst.close();
  	  	  	    	    	      System.out.println("ejecutn insert_reporte_descriptor_grado_area_perini");
  	    	  		         }else{
  	    	  		             if(filtroDescriptores.getPeriodoinicial().equals("-9") && !filtroDescriptores.getPeriodofinal().equals("-9")){
  	  	  	  	    	    		   System.out.println("SEDE-JORNADA-METODOLOGIA-GRADO-AREA_CON PERIODO FINAL");
  	  	  	  	    	    		   pst=con.prepareStatement(rb3.getString("insert_reporte_descriptor_grado_area_perfin"));
  	  	  	  	    	    	     posicion=1;
  	  	  	  	    	    	     pst.clearParameters();
  	  	  	  	    	    	     pst.setLong(posicion++,Long.parseLong(login.getUsuarioId()));
  	  	  	  	    	    	     pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getInstitucion()));
  	  	  	  	    	    	     pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getMetodologia()));
  	  	  	  	    	    	     pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getArea()));
  	  	  	  	    	    	     pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getGrado()));
  	  	  	  	    	    	     pst.setLong(posicion++,Long.parseLong(vigencia));
  	  	  	  	  			  	     pst.setLong(posicion++,Long.parseLong(filtroDescriptores.getPeriodofinal()));
  	  	  	  	    	    	     pst.executeUpdate();
  	  	  	  	    	    	     pst.close();
  	  	  	  	    	    	     System.out.println("ejecutn insert_reporte_descriptor_grado_area_perfin");
  	    	  		             }
  	    	  		         }
  	    	  		      }
  	    	  		    }  
  	    	  		    
  	    	    		}  	    		      
  	    		  }
  		    }  
  		  }else{ System.out.println("nnnSEDE-JORNADA-METODOLOGIA NULOS EN PROCESO DESCRIPTOR!!!");return false;}
  	 		return true;
  		  }	
  			catch(InternalErrorException e){
  					System.out.println("Error Interno: "+e.toString());
  				return false;	
  			}
  			catch(Exception e ){
  			    e.printStackTrace();
  					System.out.println("Error Exception: "+e.toString());
  				return false;
  			}
  			finally{
  				try{
  				    OperacionesGenerales.closeStatement(pst);
  				    OperacionesGenerales.closeConnection(con);
  				    }catch(Exception e){}
  		  }
  	}
  	
  	
  	/**
  	*	Funcinn: Pone el reporte generado por el reporteador en la tabla de reportes <BR>
  	*	@param	String us
  	*	@param	String rec
  	*	@param	String tipo
  	*	@param	String nombre
  	*	@param	String prepared
  	**/

  	public void ponerReporte(String modulo,String us,String rec,String tipo,String nombre,String estado,String prepared){	
  		Connection con=null;
  		PreparedStatement pst=null;
  		int posicion=1;
    		
  		try{ 
  		  con=cursor.getConnection(); 
  		  
  			pst=con.prepareStatement(rb2.getString(prepared));
  			posicion=1;
  			pst.clearParameters();
  			pst.setString(posicion++,(us));
  			pst.setString(posicion++,(rec));
  			pst.setString(posicion++,(tipo));
  			pst.setString(posicion++,(nombre));
  			pst.setString(posicion++,(modulo));
  			pst.setString(posicion++,(estado));		
  			pst.executeUpdate();
  			pst.close();
  			System.out.println("nnnInsertn en la tabla Reporte!!!");
  					
  	  }	
  		catch(InternalErrorException e ){
  				System.out.println("Error Interno: "+e.toString());
  			}
  		catch(Exception e ){
  				System.out.println("Error Exception: "+e.toString());
  			}
  		finally{
  			try{
  			    OperacionesGenerales.closeStatement(pst);
  			    OperacionesGenerales.closeConnection(con);
  			    }catch(Exception e){}
  	  }
  }

  	/**
  	*	Funcinn: Pone el reporte generado por el reporteador en la tabla de reportes <BR>
  	*	@param	String us
  	*	@param	String rec
  	*	@param	String tipo
  	*	@param	String nombre
  	*	@param	String prepared
  	**/
  						
  	public void ponerReporteMensaje(String estado,String modulo,String us,String rec,String tipo,String nombre,String prepared,String mensaje){
  	    
  			Connection con=null;
  			PreparedStatement pst=null;
  			int posicion=1;
  	  		
  			try{ 
  			  con=cursor.getConnection(); 
  			  
  				pst=con.prepareStatement(rb2.getString(prepared));
  				posicion=1;
  				pst.clearParameters();
  				pst.setString(posicion++,(estado));
  				pst.setString(posicion++,(mensaje));
  				pst.setString(posicion++,(us));
  				pst.setString(posicion++,(rec));
  				pst.setString(posicion++,(tipo));
  				pst.setString(posicion++,(nombre));
  				pst.setString(posicion++,(modulo));	
  				pst.executeUpdate();
  				pst.close();
  				System.out.println("nActualizn la tabla Reporte con el mensaje recibido!");
  				con.commit();  						
  		  }	
  			catch(InternalErrorException e ){
  					System.out.println("Error Interno: "+e.toString());
  				}
  			catch(Exception e ){
  					System.out.println("Error Exception: "+e.toString());
  				}
  			finally{
  				try{
  				 OperacionesGenerales.closeStatement(pst);
  				 OperacionesGenerales.closeConnection(con);
  				}
  				catch(Exception e){}
  		  }    		    	    			
  	}	
  
  	
  	/**
  	*	Funcinn: Pone el reporte generado por el reporteador en la tabla de reportes <BR>
  	*	@param	String us
  	*	@param	String rec
  	*	@param	String tipo
  	*	@param	String nombre
  	*	@param	String prepared
  	**/

  	public boolean updateReporteEstado(String modulo,String us,String rec,String tipo,String nombre,String prepared){
  			Connection con=null;
  			PreparedStatement pst=null;
  			int posicion=1;

  			try{ 
  				  con=cursor.getConnection(); 
  				  
  					pst=con.prepareStatement(rb2.getString(prepared));
  					posicion=1;
  					pst.clearParameters();
  					pst.setString(posicion++,(us));
  					pst.setString(posicion++,(rec));
  					pst.setString(posicion++,(tipo));
  					pst.setString(posicion++,(nombre));
  					pst.setString(posicion++,(modulo));	
  					pst.executeUpdate();
  					con.commit();
  					pst.close();		
  					System.out.println("nnSE ACTUALIZn EL ESTADO DEL REPORTE!!");
  			  }	
  				catch(InternalErrorException e ){
  						System.out.println("Error Interno: "+e.toString());
  						return false;
  					}
  				catch(Exception e ){
  						System.out.println("Error Exception: "+e.toString());
  						return false;
  					}
  				finally{
  					try{
  					OperacionesGenerales.closeStatement(pst);
  					OperacionesGenerales.closeConnection(con);
  					}
  					catch(Exception e){}
  			  }
  		return true;		
  	}

  	
 	/**
 	 *	Inserta los valores por defecto para el bean de información bnsica con respecto a la informacion de la institucinn educativa
 	 *	@param HttpServletRequest request
 	 *	@return boolean 
 	 */
 		public boolean asignarBeans(HttpServletRequest request)throws ServletException, IOException{
 			login=(Login)session.getAttribute("login");
 			String sentencia="";
 			return true;
	 }
  	
  	
	/**
	 *	Inserta los valores por defecto para el bean de información bnsica con respecto a la informacion de la institucinn educativa
	 *	@param HttpServletRequest request
	 *	@return boolean 
	 */
		public boolean asignarBeansAreas(HttpServletRequest request)throws ServletException, IOException{
			filtroAreas=(FiltroBeanReportesAreas)session.getAttribute("filtroAreas");
			filtroAreas.setInstitucion(login.getInstId());
			String sentencia="";
			return true;
		}


	/**
	 *	Inserta los valores por defecto para el bean de información bnsica con respecto a la informacion de la institucinn educativa
	 *	@param HttpServletRequest request
	 *	@return boolean 
	 */
		public boolean asignarBeansLogros(HttpServletRequest request)throws ServletException, IOException{
			filtroLogros=(FiltroBeanReportesLogros)session.getAttribute("filtroLogros");
			filtroLogros.setInstitucion(login.getInstId());
			String sentencia="";
			return true;
		}
		
	/**
	 *	Inserta los valores por defecto para el bean de información bnsica con respecto a la informacion de la institucinn educativa
	 *	@param HttpServletRequest request
	 *	@return boolean 
	 */
		public boolean asignarBeansDescriptores(HttpServletRequest request)throws ServletException, IOException{
		  filtroDescriptores=(FiltroBeanReportesDescriptores)session.getAttribute("filtroDescriptores");
			filtroDescriptores.setInstitucion(login.getInstId());
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
	 * 
	 *	limpia las tablas de los datos generados por la consulta de generacinn del reporte 
	 *	@param HttpServletRequest request
	 */	

	public void limpiarTablasAreas(String usuarioid){
		Connection con=null;
		PreparedStatement pst=null;
		try{
			con=cursor.getConnection();	    
			pst=con.prepareStatement(rb3.getString("delete_reporte_area"));
			pst.clearParameters();
			pst.setLong(1,Long.parseLong(usuarioid));
			pst.executeUpdate();
			pst.close();
			con.commit();		
			System.out.println("**SE LIMPIn REPORTE_AREA**");
		}
		catch(InternalErrorException e ){
			System.out.println("Error: "+e.toString());
		}
		catch(Exception e){
				System.out.println("Error Exception: "+e.toString());
			}
		finally{
			try{
			    OperacionesGenerales.closeStatement(pst);
			    OperacionesGenerales.closeConnection(con);
			    }catch(Exception e){}
    }
	}
	
	
	/**
	 * 
	 *	limpia las tablas de los datos generados por la consulta de generacinn del reporte 
	 *	@param HttpServletRequest request
	 */	

	public void limpiarTablasLogros(String usuarioid){
		Connection con=null;
		PreparedStatement pst=null;
		try{
			con=cursor.getConnection();	    
			pst=con.prepareStatement(rb3.getString("delete_reporte_logro"));
			pst.clearParameters();
			pst.setLong(1,Long.parseLong(usuarioid));
			pst.executeUpdate();
			pst.close();
			con.commit();		
			System.out.println("**SE LIMPIn REPORTE_LOGRO**");
		}
		catch(InternalErrorException e ){
			System.out.println("Error: "+e.toString());
		}
		catch(Exception e){
				System.out.println("Error Exception: "+e.toString());
			}
		finally{
			try{
			    OperacionesGenerales.closeStatement(pst);
			    OperacionesGenerales.closeConnection(con);
			    }catch(Exception e){}
    }
	}
	
	
	/**
	 * 
	 *	limpia las tablas de los datos generados por la consulta de generacinn del reporte 
	 *	@param HttpServletRequest request
	 */	

	public void limpiarTablasDescriptores(String usuarioid){
		Connection con=null;
		PreparedStatement pst=null;
		try{
			con=cursor.getConnection();	    
			pst=con.prepareStatement(rb3.getString("delete_reporte_descriptor"));
			pst.clearParameters();
			pst.setLong(1,Long.parseLong(usuarioid));
			pst.executeUpdate();
			pst.close();
			con.commit();		
			System.out.println("**SE LIMPIn REPORTE_DESCRIPTOR**");
		}
		catch(InternalErrorException e ){
			System.out.println("Error: "+e.toString());
		}
		catch(Exception e){
				System.out.println("Error Exception: "+e.toString());
			}
		finally{
			try{
			    OperacionesGenerales.closeStatement(pst);
			    OperacionesGenerales.closeConnection(con);
			    }catch(Exception e){}
    }
	}
	
	
	/**
	*	Funcinn: Pone el reporte generado por el reporteador en la tabla de reportes <BR>
	*	@param	String us
	*	@param	String rec
	*	@param	String tipo
	*	@param	String nombre
	*	@param	String prepared
	**/

	public boolean updateReporteFecha(String preparedstatement,String modulo,String nombreboletin,String user,String estado){	
		Connection con=null;
		PreparedStatement pst=null;
		int posicion=1;
	 		
		try{ 
		  con=cursor.getConnection(); 
		  
			pst=con.prepareStatement(rb3.getString(preparedstatement));
			posicion=1;
			pst.clearParameters();
			pst.setString(posicion++,(modulo));
			pst.setString(posicion++,(nombreboletin));
			pst.setString(posicion++,(user));
			pst.setLong(posicion++,Long.parseLong(estado));		
			pst.executeUpdate();
			pst.close();
			con.commit();
	 		System.out.println("nnSe actualizn la fecha en la tabla Reporte!!!!");
	 		
		  }	
			catch(InternalErrorException e){
					System.out.println("Error Interno: "+e.toString());
					return false;
				}
			catch(Exception e ){
					System.out.println("Error Exception: "+e.toString());
					return false;
				}
			finally{
				try{
				    OperacionesGenerales.closeStatement(pst);
				    OperacionesGenerales.closeConnection(con);
				    }catch(Exception e){}
		  }
			return true;
	}

	
	/**
	*	Funcinn: Pone el reporte generado por el reporteador en la tabla de reportes, y luego poder ser visualizado en <BR> la lista de reportes generados <BR>
	*	@param	byte bit
	**/
	
	public boolean ponerArchivo(String modulo,String path,byte[] bit,String archivostatic,String context1){
	     	    
		Connection con=null;
		try{
			con=cursor.getConnection();	
			System.out.println("ENTRO A PONER EL ARCHIVO");			
			String archivosalida=Ruta.get(context1,rb3.getString("reportes.PathReportesPlanEstudios"));			
			System.out.println("archivosalida: "+archivosalida);
			File f=new File(archivosalida);
			if(!f.exists()) FileUtils.forceMkdir(f);			
			FileOutputStream fileOut= new FileOutputStream(f+File.separator+archivostatic);
			CopyUtils.copy(bit,fileOut);
			fileOut.flush();
			fileOut.close();
		}catch(IOException e){		    
			System.out.println("Error poniendo archivo"+e);
			return false;
		}
		catch(InternalErrorException e ){
			System.out.println("Error:"+e.toString());
			return false;
		}
		finally{
				try{
				  OperacionesGenerales.closeConnection(con);
				}catch(InternalErrorException inte){}
			}		
		return true;
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

	
	
	
	/**
	 * @param codInst
	 * @return
	 * @throws Exception
	 */
	public String getDane(long codInst) throws Exception{
  		Connection con=null;
    	PreparedStatement pst=null;
    	ResultSet rs=null; 
  	  	int posicion=1;
  	   
  	  
  		try{
  			 con=cursor.getConnection();		    			 
  			  pst=con.prepareStatement(rb3.getString("getDane"));
  			 posicion=1;
  			 pst.clearParameters();
  			 pst.setLong(posicion++,codInst);
  			 rs=pst.executeQuery();
  			 
  			 if(!rs.next()){
  				 posicion=1;
  				 return ""+rs.getLong(posicion++);
  			 }
  				 
  			}catch(Exception e){
  			  e.printStackTrace();
  			  throw new Exception(e);  
  			}finally{
  				try{
  					OperacionesGenerales.closeResultSet(rs);
  					OperacionesGenerales.closeStatement(pst);    		    
  					OperacionesGenerales.closeConnection(con);
  				}catch(Exception e){
  					e.printStackTrace();
  				}    
  			}  	
  			return "";
  	}
	
	private boolean isValidaImagen(File imgRuta){
		BufferedImage image = null;
		try{
			image = ImageIO.read(imgRuta);
			if(image != null){
				System.out.println("imagen valida");
				return true;
			}
		}
		catch (IOException e){
			System.out.println("IO exception imagen " +imgRuta.toString() );
			e.printStackTrace();
		}
		System.out.println("imagen no valida " +imgRuta.toString());
		return false;
	}
}


