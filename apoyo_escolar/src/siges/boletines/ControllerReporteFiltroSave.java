package siges.boletines;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import siges.dao.*;
import siges.io.*;
import siges.exceptions.InternalErrorException;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.*;
import com.lowagie.text.pdf.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.awt.SystemColor;
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
import siges.boletines.beans.FiltroBeanFormulario;
import org.apache.commons.io.CopyUtils;
import org.apache.commons.io.FileUtils;
import siges.login.beans.Login;
import siges.util.Logger;
import siges.util.beans.ReporteVO;
import siges.boletines.dao.ReporteLogrosDAO;
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
	private FiltroBeanFormulario filtro;
	private Util util;
	private String [] codigo;
	private String buscarcodigo;
	private final String modulo="28";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private ServletConfig config;
	private String s="/Reportes.do";
	private String s1="/boletines/ControllerBoletinFiltroEdit.do";
	private String archivo,archivozip;	
	private String insertar;
	private String ant;
	private String er;
	private String sig;
	private String home;
	private ReporteLogrosDAO reporteDao;
	private static int parametro;
	    	
    	
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
    	String cont=null;
    	String sede=null;String jornada=null;String met=null;String grado=null;String grupo=null;String id=null;
    	String param=null;
    	int estudiantes=0;
 	   	rb3=ResourceBundle.getBundle("preparedstatements_logros_pendientes");
 	   	f2=new java.sql.Timestamp(new java.util.Date().getTime());
 	   	String existeboletin,boton;
 	   	String nom;
 	   	ant="/boletines/filtroReporte.jsp";
 	  	sig="/boletines/filtroReporte.jsp";
 	  	er="/error.jsp";
 	  	home="/bienvenida.jsp";
 	  	insertar=existeboletin=null; 	  	
 	  	session = request.getSession();	
 	  	param=rb3.getString("parametro");
 	  	String alert="El Reporte se esta generando en este momento. \nPulse en el hipervinculo 'Listado de Reportes' para ver si el reporte ya fue generado. \nO vaya a la opción de menu 'Reportes generados'";
 	  	boton=(request.getParameter("cmd")!=null)?request.getParameter("cmd"):new String("");
 	  	
	 	 	if(!asignarBeans(request)){
	 	 		setMensaje("Error capturando datos de sesinn para el usuario");
	 	 		request.setAttribute("mensaje",mensaje);
	 	 		ir(2,er,request,response);
	 	 		return null;
	 	 	}
	 	 	
 	  	ServletContext context=(ServletContext)request.getSession().getServletContext();
 	  	String contextoTotal=context.getRealPath("/");
  		parametro=Integer.parseInt(param);
  		
  		try{
  		    cursor=new Cursor();
  		    util=new Util(cursor);
  	 	  	reporteDao=new ReporteLogrosDAO(cursor);
  		    
  		    con=cursor.getConnection();

	   			pst=con.prepareStatement(rb3.getString("existe_mismo_reporte_en_cola"));
	   			posicion=1;
	   			pst.clearParameters();
		   		pst.setLong(posicion++,Long.parseLong(login.getInstId()));
		   		pst.setLong(posicion++,Long.parseLong(!filtro.getSede().equals("-9")?filtro.getSede():"-9"));
		   		pst.setLong(posicion++,Long.parseLong(!filtro.getJornada().equals("-9")?filtro.getJornada():"-9"));
		   		pst.setLong(posicion++,Long.parseLong(!filtro.getMetodologia().equals("-9")?filtro.getMetodologia():"-9"));
		   		pst.setLong(posicion++,Long.parseLong(!filtro.getGrado().equals("-9")?filtro.getGrado():"-9"));
		   		pst.setLong(posicion++,Long.parseLong(!filtro.getGrupo().equals("-9")?filtro.getGrupo():"-9"));
		   		pst.setString(posicion++,filtro.getEstudiante().equals("-9")?filtro.getEstudiante():"-9");
	   			rs=pst.executeQuery();
	   			if(!rs.next()){
	   			    System.out.println("******nNO HAY NINGUNO CON LOS MISMOS PARAMETROS!...EN ESTADO CERO O -1!*********");
	   			    rs.close();pst.close();

 				        sede=(!filtro.getSede().equals("-9")?"_Sede_"+filtro.getSede().trim():"");
	   				    jornada=(!filtro.getJornada().equals("-9")?"_Jornada_"+filtro.getJornada().trim():"");
	   				    met=(!filtro.getMetodologia().equals("-9")?"_Metodologia_"+filtro.getMetodologia():"");
	   				    grado=(!filtro.getGrado().equals("-9")?"_Grado_"+filtro.getGrado():"");
	   				    grupo=(!filtro.getGrupo().equals("-9")?"_Grupo_"+filtro.getGrupo():"");
	   				    id=(!filtro.getEstudiante().equals("-9")?"_Doc_"+filtro.getEstudiante():"");
					   		   
	   				    nom=sede+jornada+met+grado+grupo+id+"_Fecha_"+f2.toString().replace(' ','_').replace(':','-').replace('.','-');
	   				    archivo="Reporte_Logros_Pendientes_"+nom+".pdf";
	   				    archivozip="Reporte_Logros_Pendientes_"+nom+".zip";
					   			 
	   				    if(!reporteDao.insertarDatosReporte(filtro,login.getInstId(),f2,archivozip,archivo,login.getUsuarioId())){
	   				    	setMensaje("No se insertn en DATOS_REPORTE_LOGRO");
	   				    	request.setAttribute("mensaje",mensaje);
	   				    	return s1;
	   				    }
	   				    System.out.println("Se insertn el ZIP en Reporte con estado -1");
	   				    if(!reporteDao.ponerReporte(modulo,login.getUsuarioId(),rb3.getString("reportes.PathReportes")+archivozip+"","zip",""+archivozip,"-1","ReporteInsertarEstado")){
	   				    	setMensaje("No se insertn en la tabla Reporte");
	   				    	request.setAttribute("mensaje",mensaje);
	   				    	return s1;
	   				    }
	   				    siges.util.Logger.print(login.getUsuarioId(),"Peticinn de Reporte_Logros_Pendientes:Institucion:_"+login.getInstId()+"_Usuario:_"+login.getUsuarioId()+"_NombreReporte:_"+archivozip+"",3,1,this.toString());
	   				    setMensaje(alert);
	   				    request.setAttribute("mensaje",getMensaje());
	   				    cont=reporteDao.colaReportes();
	   				    if(cont!=null && !cont.equals(""))
	   				    	if(!reporteDao.updatePuestoReporte(cont,archivozip,login.getUsuarioId(),"update_puesto_reporte")){
	   				    		setMensaje("No se insertn el puesto en la tabla Reporte");
	   				    		request.setAttribute("mensaje",mensaje);
	   				    		return s1;
	   				    	}
	   			}
	   			else{
	   			    rs.close();pst.close();
	   			    setMensaje("nYa existe una peticinn del reporte con los mismos parnmetros!");
	   			    request.setAttribute("mensaje",mensaje);
	   			    return s1;
	   			}
  		}	
  		catch(Exception e){	
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
  		ReporteVO reporteVO = new ReporteVO();
  		reporteVO.setRepTipo(ReporteVO._REP_BOLETINES);
  		reporteVO.setRepOrden(ReporteVO.ORDEN_DESC);
  		request.getSession().setAttribute("reporteVO",reporteVO);
  		return s;//se va a reportes.do
  	}


  	
	/**
	 *	Inserta los valores por defecto para el bean de información bnsica con respecto a la informacion de la institucinn educativa
	 *	@param HttpServletRequest request
	 *	@return boolean 
	 */
		public boolean asignarBeans(HttpServletRequest request)throws ServletException, IOException{
			login=(Login)session.getAttribute("login");
			filtro=(FiltroBeanFormulario)session.getAttribute("filtroLog");
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

