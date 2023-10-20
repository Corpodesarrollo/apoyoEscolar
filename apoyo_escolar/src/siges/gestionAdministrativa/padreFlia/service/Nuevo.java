/**
 * 
 */
package siges.gestionAdministrativa.padreFlia.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.validator.GenericValidator;



import siges.adminParamsAcad.vo.NivelEvaluacionVO;
import siges.common.service.Service;
import siges.common.vo.ItemVO;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import siges.dao.Ruta;
import siges.dao.Ruta2;
import siges.gestionAdministrativa.padreFlia.dao.PadreFliaDAO;
import siges.gestionAdministrativa.padreFlia.vo.EstudianteMarcarVO;
import siges.gestionAdministrativa.padreFlia.vo.FiltroBoletinVO;
import siges.gestionAdministrativa.padreFlia.vo.ParamsVO;
import siges.gestionAdministrativa.padreFlia.vo.ResultadoVO;
import siges.io.Zip;
import siges.login.beans.Login;
/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class Nuevo extends Service{
	
	public String FICHA_PADRE_FLIA;
	private ResourceBundle rb;
	private String contextoTotal;
	private PadreFliaDAO padreFliaDAO;
	private Login usuVO;
	private String mensaje;
	/**
	 *  
	 
	 /*
	  * @function: 
	  * @param config
	  * @throws ServletException
	  * (non-Javadoc)
	  * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
	  */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		padreFliaDAO = new PadreFliaDAO(new Cursor());
		rb = ResourceBundle.getBundle("siges.gestionAdministrativa.padreFlia.bundle.padreFlia");
		FICHA_PADRE_FLIA= config.getInitParameter("FICHA_PADRE_FLIA");
		System.out.println("FICHA_PADRE_FLIA " + FICHA_PADRE_FLIA);
	}
	
	/*
	 * Procesa la peticinn
	 * @param request peticinn
	 * @param response respuesta
	 * @return Ruta de redireccinn
	 * @throws ServletException
	 * @throws IOException
	 * (non-Javadoc)
	 * @see siges.common.service.Service#process(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String dispatcher[]=new String[2];
		contextoTotal=context.getRealPath("/");
		context=config.getServletContext();
		String FICHA=null;
		FICHA_PADRE_FLIA= config.getInitParameter("FICHA_PADRE_FLIA");
		HttpSession session = request.getSession();
		usuVO = (Login) session.getAttribute("login");
		int CMD=getCmd(request,session,ParamsVO.CMD_NUEVO);
		int TIPO=getTipo(request,session,ParamsVO.FICHA_PADRE_FLIA); 
		System.out.println("++++++++++++++++++FICHA_PADRE_FLIA= " + FICHA_PADRE_FLIA);
		System.out.println("padre flia TIPO " + TIPO +"   CMD "+ CMD);
		switch (TIPO){
		case ParamsVO.FICHA_PADRE_FLIA:
			
			FICHA = FICHA_PADRE_FLIA;				
			cargarListas(request,session, usuVO);
			switch (CMD){
			case ParamsVO.CMD_DEFAULT:
				request.getSession().removeAttribute("listaEst");
				request.getSession().removeAttribute("listaJornada");
				request.getSession().removeAttribute("listaMetodo");
				request.getSession().removeAttribute("listaGrado");
				request.getSession().removeAttribute("listaGrupo");
				request.getSession().removeAttribute("filtroBoletinVO");
				
			case ParamsVO.CMD_NUEVO:
				break;
			case ParamsVO.CMD_GUARDAR:
				guardarEstudiante(request,session, usuVO);
				buscarEstudiante(request,session, usuVO);
				break;
			case ParamsVO.CMD_BUSCAR:
				buscarEstudiante(request,session, usuVO);
				FICHA=generar2(request,response, usuVO);
				break;
			case ParamsVO.CMD_GENERAR:
				System.out.println("ENTRO A GENERAR REPORTE CONSULTA BOLETIN");
				FICHA=generar(request,response, usuVO);
				buscarEstudiante(request,session, usuVO);
				break;
				
			}
			
			break;
		}
		FICHA = FICHA_PADRE_FLIA;	
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		System.out.println("FICHA " + FICHA);
		dispatcher[1]=FICHA;
		return dispatcher;
	}
	
	
	
	/**
	 * @function:  Carga por defecto las lista
	 * @param request
	 * @param session
	 * @param usuVO
	 * @throws ServletException
	 */
	private void cargarListas(HttpServletRequest request, HttpSession session,Login usuVO ) throws ServletException {
		System.out.println("cargarListas ");
		
		try {	
			request.getSession().setAttribute("listaSede", padreFliaDAO.getSede(Long.parseLong(usuVO.getInstId())   ));
			request.getSession().setAttribute("filtroPeriodo",getListaPeriodo(usuVO.getLogNumPer(), usuVO.getLogNomPerDef()));
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new  ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	
	/**
	 * @function:  Carga la lista de los periodos
	 * @param numPer
	 * @param nomPerDef
	 * @return
	 */
	public List getListaPeriodo(long numPer, String nomPerDef){
		List l=new ArrayList();
		ItemVO item=null;
		for(int i=1;i<=numPer;i++){
			item=new ItemVO(i,""+i); l.add(item);
		}		
		item=new ItemVO(7,nomPerDef); l.add(item);
		return l;
	}
	
	
	
	/**
	 * @function:  
	 * @param request
	 * @param session
	 * @param usuVO
	 * @throws ServletException
	 */
	public void buscarEstudiante(HttpServletRequest request, HttpSession session, Login usuVO) throws ServletException {
		System.out.println("buscarEstudiante ");
		try {
			FiltroBoletinVO filtroBoletinVO =  (FiltroBoletinVO)request.getSession().getAttribute("filtroBoletinVO");
			if(filtroBoletinVO != null){
				filtroBoletinVO.setPlabolinst(Long.parseLong(usuVO.getInstId()));
				List listaEst  = padreFliaDAO.getListaEst(filtroBoletinVO);
			 
				request.setAttribute("listaEst",listaEst );
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, e.getMessage());
		} 
	}
	
	/**
	 * @function:  
	 * @param request
	 * @param session
	 * @param usuVO
	 * @throws ServletException
	 */
	public void guardarEstudiante(HttpServletRequest request, HttpSession session, Login usuVO) throws ServletException {
		System.out.println("buscarEstudiante ");
		
		try {
			String listaEstados_[]=null;
			List listaEst = new ArrayList();
			listaEstados_=request.getParameterValues("listaEstados_");
			FiltroBoletinVO filtroBoletinVO =  (FiltroBoletinVO)request.getSession().getAttribute("filtroBoletinVO");
			 for (int i = 0; i < listaEstados_.length ; i++) {
				System.out.println("listaEstados_[i] " + listaEstados_[i]);
				EstudianteMarcarVO n = new EstudianteMarcarVO();
				long codigoEst =   Long.valueOf( listaEstados_[i].substring(0,listaEstados_[i].lastIndexOf("_"))   ).longValue();
				long consulta=   Long.valueOf( listaEstados_[i].substring(listaEstados_[i].lastIndexOf("_")+1,listaEstados_[i].length())  ).longValue();
				n.setEstcodigo(codigoEst);
				n.setEstconsulta((int)consulta);
				listaEst.add(n);
			}
			
			
			if(listaEst != null){
				System.out.println("params " + listaEst.size() );
				padreFliaDAO.guardarEst(filtroBoletinVO,listaEst);
				request.setAttribute("mensaje", "Información registrada satisfactoriamente.");
			}
			
			
			
			//Permite Ocultar el link de descarga del reporte
			ResultadoVO resultado = new ResultadoVO();
			resultado.setGenerado(false);
			request.getSession().setAttribute("resultadoReportes", resultado);
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, e.getMessage());
		} 
	}
	
	
	///REPORTE
	public String generar(HttpServletRequest request,HttpServletResponse response, Login usuVO) throws ServletException {
		response.setContentType("application/zip");
		BufferedInputStream origin = null;
		ServletOutputStream ouputStream = null;
		FileInputStream fi = null;
		int BUFFER_SIZE = 100000;
		int count;
		System.out.println("ENTRO A GENERAR REPORTE CONSULTA BOLETIN ********* 1 ****** ");
		try {			
			ResultadoVO resultado=null;			
				
				//request.setAttribute("listaConsulta", consultaDAO.getListaPOA(filtro));
				//Logger.print(usuVO.getUsuarioId(),"POA: Buscar actividad con recursos. "+filtro.getFilInstitucion()+". "+filtro.getFilVigencia(),2,1,this.toString());
				String rutaBase=context.getRealPath("/");
				String usuario=usuVO.getUsuarioId();
				String sede=request.getParameter("sede");
				String jornada=request.getParameter("jornada");
				String metod=request.getParameter("metod");
				String grado=request.getParameter("grado");
				String grupo=request.getParameter("grupo");
				String periodo=request.getParameter("periodo");
				long inst_=0;
				long sede_=0;
				long jornada_=0;
				long metod_=0;
				long grado_=0;
				long grupo_=0;
				long periodo_=0;
				if(usuVO.getInstId() !=null && GenericValidator.isDouble(usuVO.getInstId())){
					inst_=Long.parseLong(usuVO.getInstId());
				}
				if(sede !=null && GenericValidator.isDouble(sede)){
					sede_=Long.parseLong(sede);
				}
				if(jornada !=null && GenericValidator.isDouble(jornada)){
					jornada_=Long.parseLong(jornada);
				}
				if(metod !=null && GenericValidator.isDouble(metod)){
					metod_=Long.parseLong(metod);
				}
				if(grado !=null && GenericValidator.isDouble(grado)){
					grado_=Long.parseLong(grado);
				}
				if(grupo !=null && GenericValidator.isDouble(grupo)){
					grupo_=Long.parseLong(grupo);
				}
				if(periodo !=null && GenericValidator.isDouble(periodo)){
					periodo_=Long.parseLong(periodo);
				}
				//System.out.println("PARAMETROS REPORTES ENCUESTAS, ENCUESTA: "+encuesta+" ****** APLICACION: "+aplicacion);
				resultado=generarReporte(inst_,sede_, jornada_,metod_,grado_,grupo_,periodo_,rutaBase, usuario);	
				
				if(resultado.isGenerado()){
					System.out.println("REPORTE GENERADO CONSULTA BOLETIN ****** ");
			        StringBuffer contentDisposition = new StringBuffer();
			        contentDisposition.append("attachment;");
			        contentDisposition.append("filename=\"");
			        contentDisposition.append(resultado.getArchivo());
			        contentDisposition.append("\"");
			        response.setHeader("Content-Disposition", contentDisposition.toString());
					File f = new File(resultado.getRutaDisco());
					if (!f.exists()) {
						return null;
					}
					byte[] data = new byte[BUFFER_SIZE];
					fi = new FileInputStream(f);
					origin = new BufferedInputStream(fi, BUFFER_SIZE);
					ouputStream = response.getOutputStream();
					while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
						ouputStream.write(data, 0, count);
					}
					ouputStream.flush();
					ouputStream.close();
					origin.close();
					fi.close();
					return null;
				}else{
					System.out.println("REPORTE NOOOOOOOOO GENERADO CONSULTA BOLETIN ****** ");					
					mensaje="No se pudo generar el reporte.";
					request.setAttribute("mensaje",mensaje);
					return FICHA_PADRE_FLIA;
				}
				
				
		
		} catch (Exception e) {
			e.printStackTrace();
			
			mensaje="No se pudo generar el reporte, error en la generacinn";
			request.setAttribute("mensaje",mensaje);
			return FICHA_PADRE_FLIA;
		} finally {
			try {
				if (ouputStream != null)
					ouputStream.close();
				if (origin != null)
					origin.close();
				if (fi != null)
					fi.close();
			} catch (Exception e) {
			}
		}
	}

	private ResultadoVO generarReporte(long inst, long sede, long jornada, long metod, long grado, long grupo, long per,String rutaBase, String usuario) {
		ResultadoVO resultado = new ResultadoVO();
		try{
			if(inst<0){resultado.setGenerado(false); return resultado;}			
			Calendar c=Calendar.getInstance();
			String fecha=c.get(Calendar.DAY_OF_MONTH)+"_"+(c.get(Calendar.MONTH)+1)+"_"+c.get(Calendar.YEAR)+"_"+c.get(Calendar.HOUR_OF_DAY)+"_"+c.get(Calendar.MINUTE);
			String rutaJasper = Ruta2.get(rutaBase, rb.getString("rutaJasper"));
			String archivoJasper = rb.getString("jasper");
			String rutaExcel= Ruta.get(rutaBase, rb.getString("path")+usuario);
			//String archivoExcel = "Reporte_ENCUESTA_"+enc+"_aplicacion_"+apl+"_FECHA_"+fecha+".xls";
			String archivoPdf = "Listado_Consulta_Boletin_FECHA_"+fecha+".pdf";
			String archivoZip ="Listado_Consulta_Boletin_FECHA_"+fecha+".zip";
			String rutaExcelRelativo= Ruta.getRelativo(rutaBase, rb.getString("path")+usuario);
			String rutaImagen = Ruta.get(rutaBase, rb.getString("rutaImagen"));
			String archivoImagen  = rb.getString("imagen");

			File reportFile= new File(rutaJasper + archivoJasper);

			//Parametros del Reporte
			Map map=new HashMap();
			//map.put("SUBREPORT_DIR", rutaJasper);			
			map.put("INST", new Long(inst));
			map.put("SEDE", new Long(sede));
			map.put("JORNADA", new Long(jornada));
			map.put("METOD", new Long(metod));
			map.put("GRADO", new Long(grado));
			map.put("GRUPO", new Long(grupo));
			map.put("PERIODO", new Long(per));
			map.put("ESCUDO_SED", rutaImagen + archivoImagen);
						
			//String rutaXLS=getArchivoXLS(reportFile,map,rutaExcel,archivoExcel);	
			String rutaPDF=getArchivoPDF(reportFile,map,rutaExcel,archivoPdf);
			List l=new ArrayList();
			l.add(rutaPDF);
			Zip zip=new Zip();
			if(zip.ponerZip(rutaExcel,archivoZip,100000,l)){
				resultado.setGenerado(true);
				resultado.setRuta(rutaExcelRelativo+archivoZip);
				resultado.setRutaDisco(rutaExcel+archivoZip);
				resultado.setArchivo(archivoZip);
				resultado.setTipo("zip");
			}	
		}catch(Exception e){
			resultado.setGenerado(false);
			//return null;
			e.printStackTrace();
		}
		return resultado;
		
	}
	
	/**
	 *  Construye el archivo y lo almacena en el servidor
	 * @param reportFile Archivo del reporte
	 * @param parameterscopy Parametros del reporte
	 * @param rutaExcel Ruta de almacenamiento del reporte
	 * @param archivo Nombre del reporte
	 * @return Ruta de disco del reporte
	 * @throws Exception
	 */
	public String getArchivoPDF(File reportFile, Map parameterscopy, String rutaExcel,String archivo)throws Exception {
		Connection con = null;
		String archivoCompleto = null;
		try {
			con = padreFliaDAO.getConnection();
			byte[] bytes=JasperRunManager.runReportToPdf(reportFile.getPath(),parameterscopy,con);
			String xlsFileName = archivo;
			String xlsFilesSource = rutaExcel;
			archivoCompleto = xlsFilesSource + xlsFileName;
			File f = new File(xlsFilesSource);
			if (!f.exists())
				FileUtils.forceMkdir(f);
			FileOutputStream fileOut= new FileOutputStream(archivoCompleto);
			IOUtils.write(bytes,fileOut);
			fileOut.close();
		} catch (JRException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (java.lang.OutOfMemoryError e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return archivoCompleto;
	}
	
	
	
	///REPORTE
		public String generar2(HttpServletRequest request,HttpServletResponse response, Login usuVO) throws ServletException {
			
			BufferedInputStream origin = null;
			ServletOutputStream ouputStream = null;
			
			
			
			FileInputStream fi = null;
			int BUFFER_SIZE = 100000;
			int count;
			System.out.println("ENTRO A GENERAR REPORTE CONSULTA BOLETIN ********* 1 ****** ");
			try {			
				ResultadoVO resultado=null;			
					
				
					String rutaBase=context.getRealPath("/");
					String usuario=usuVO.getUsuarioId();

			
					FiltroBoletinVO filtroBoletinVO =  (FiltroBoletinVO)request.getSession().getAttribute("filtroBoletinVO");
					
					
					
					long inst_=0;
					long sede_=0;
					long jornada_=0;
					long metod_=0;
					long grado_=0;
					long grupo_=0;
					long periodo_=0;
					if(usuVO.getInstId() !=null && GenericValidator.isDouble(usuVO.getInstId())){
						inst_=Long.parseLong(usuVO.getInstId());
					}
				
					//System.out.println("PARAMETROS REPORTES ENCUESTAS, ENCUESTA: "+encuesta+" ****** APLICACION: "+aplicacion);
					
					
					sede_=filtroBoletinVO.getPlabolsede();
					jornada_=filtroBoletinVO.getPlaboljornd();
					metod_=filtroBoletinVO.getPlabolmetodo();
					grado_=filtroBoletinVO.getPlabolgrado();
					grupo_=filtroBoletinVO.getPlabolgrupo();
					periodo_=	filtroBoletinVO.getPlabolperido();	
					
					resultado=generarReporte(inst_,sede_, jornada_,metod_,grado_,grupo_,periodo_,rutaBase, usuario);	
					
				
					
					if(resultado.isGenerado()){
						System.out.println("REPORTE GENERADO CONSULTA BOLETIN ****** ");
						
						request.getSession().setAttribute("resultadoReportes", resultado);

						 return FICHA_PADRE_FLIA;
					}else{
						System.out.println("REPORTE NOOOOOOOOO GENERADO CONSULTA BOLETIN ****** ");					
						mensaje="No se pudo generar el reporte.";
						request.setAttribute("mensaje",mensaje);
						return FICHA_PADRE_FLIA;
					}
					
					
			
			} catch (Exception e) {
				e.printStackTrace();
				
				mensaje="No se pudo generar el reporte, error en la generacinn";
				request.setAttribute("mensaje",mensaje);
				return FICHA_PADRE_FLIA;
			} finally {
				try {
					if (ouputStream != null)
						ouputStream.close();
					if (origin != null)
						origin.close();
					if (fi != null)
						fi.close();
				} catch (Exception e) {
				}
			}
		}
	
}
