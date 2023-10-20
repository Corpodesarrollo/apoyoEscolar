/**
 * 
 */
package participacion.acta.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import participacion.acta.dao.ActaDAO;
import participacion.acta.vo.ActaVO;
import participacion.acta.vo.FiltroActaVO;
import participacion.acta.vo.ParamsVO;
import participacion.common.exception.IntegrityException;
import participacion.common.exception.ParticipacionException;
import participacion.common.exception.ValidateException;
import participacion.common.vo.ParamParticipacion;
import participacion.reportes.service.Zip;
import participacion.reportes.vo.FiltroItemsVO;
import participacion.reportes.vo.ResultadoReportesVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import siges.dao.Ruta;
import siges.dao.Ruta2;
import siges.login.beans.Login;
import siges.util.Logger;

/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class Nuevo extends Service{

	public String FICHA_ACTA;
	private ResourceBundle rb;
	private String contextoTotal;
	
	/**
	 * 
	 */
	private ActaDAO actaDAO=new ActaDAO(new Cursor());

	/*
	 * @function: 
	 * @param config
	 * @throws ServletException
	 * (non-Javadoc)
	 * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		FICHA_ACTA=config.getInitParameter("FICHA_ACTA");
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
		rb = ResourceBundle.getBundle("participacion.acta.bundle.acta");
		contextoTotal=context.getRealPath("/");
		String FICHA=null;
		HttpSession session = request.getSession();
		Login usuVO = (Login) session.getAttribute("login");
		FiltroActaVO filtroActa=(FiltroActaVO)session.getAttribute("filtroActaVO");
		ActaVO acta=(ActaVO)session.getAttribute("actaVO");
			int CMD=getCmd(request,session,ParamsVO.CMD_NUEVO);
			int TIPO=getTipo(request,session,ParamsVO.FICHA_DEFAULT);
			//System.out.println("valoresACTA: "+TIPO+"_"+CMD);
			Logger.print(usuVO.getUsuarioId(), "Consulta de PARTICIPACION, módulo de Planeacion de Actas. ",
					7, 1, this.toString());
			switch (TIPO){
				case ParamsVO.FICHA_ACTA:
					FICHA=FICHA_ACTA;				
					switch (CMD){
						case ParamsVO.CMD_BUSCAR:
							actaCancelar(request,session,usuVO);
							//acta=actaBuscar(request,session,usuVO,filtroActa,acta);
							acta=actaBuscar(request,session,usuVO,filtroActa,null);
							actaNuevo(request,session,usuVO,null);
						break;	
						case ParamsVO.CMD_EDITAR:
							acta=actaEditar(request,session,usuVO,filtroActa);
							actaBuscar(request,session,usuVO,filtroActa,acta);
						break;	
						case ParamsVO.CMD_ELIMINAR:
							actaEliminar(request,session,usuVO,filtroActa);
							acta=actaBuscar(request,session,usuVO,filtroActa,null);
						break;	
						case ParamsVO.CMD_GUARDAR:
							actaGuardar(request,session,usuVO,acta);
							acta=actaBuscar(request,session,usuVO,filtroActa,null);
						break;	
						case ParamsVO.CMD_CANCELAR:
							actaCancelar(request,session,usuVO);
							actaNuevo(request,session,usuVO,null);
							acta=actaBuscar(request,session,usuVO,filtroActa,null);
						break;	
						case ParamsVO.CMD_NUEVO:
							actaNuevo(request,session,usuVO,acta);
							acta=actaBuscar(request,session,usuVO,filtroActa,null);
						break;	
						case ParamsVO.CMD_GENERAR:
							//System.out.println("*** GENERAR REPORTE DE ACTA ***");							
							actaImprimir(request,usuVO,filtroActa,response);
							actaBuscar(request,session,usuVO,filtroActa,acta);
							FICHA=null;
						break;	
					}
					actaInit(request,session,usuVO,filtroActa,acta);
				break;
			}
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FICHA;
		return dispatcher;
	}

	
	private void actaCancelar(HttpServletRequest request,HttpSession session,Login usuVO) throws ServletException{
		session.removeAttribute("actaVO");
	}
	
	private void actaInit(HttpServletRequest request,HttpSession session,Login usuVO,FiltroActaVO filtro,ActaVO acta) throws ServletException{
		int nivel=Integer.parseInt(usuVO.getNivel());
		try{
			if(filtro==null){
				filtro=new FiltroActaVO();
				if(nivel==ParamParticipacion.LOGIN_NIVEL_LOCALIDAD){
					filtro.setFilLocalidad(Integer.parseInt(usuVO.getMunId()));
					filtro.setFilTieneLocalidad(1);
				}
				if(nivel==ParamParticipacion.LOGIN_NIVEL_COLEGIO || nivel==ParamParticipacion.LOGIN_NIVEL_SEDE_JORNADA){
					filtro.setFilLocalidad(Integer.parseInt(usuVO.getMunId()));
					filtro.setFilColegio(Integer.parseInt(usuVO.getInstId()));
					filtro.setFilTieneLocalidad(1);
					filtro.setFilTieneColegio(1);
				}
				session.setAttribute("filtroActaVO",filtro);
			}
			request.setAttribute("listaHoraVO",actaDAO.getListaHora());
			request.setAttribute("listaMinutoVO",actaDAO.getListaMinuto());
			request.setAttribute("listaAsuntoVO",actaDAO.getListaAsunto());
			request.setAttribute("listaLocalidadVO",actaDAO.getListaLocalidad());
			request.setAttribute("listaNivelVO",actaDAO.getListaNivel(nivel));
			if(filtro!=null && filtro.getFilNivel()>0){
				request.setAttribute("listaInstanciaVO",actaDAO.getListaInstancia(filtro.getFilNivel()));
				if(filtro.getFilInstancia()>0){
					request.setAttribute("listaRangoVO",actaDAO.getListaRango(filtro.getFilInstancia()));	
				}
			}
			if(filtro!=null && filtro.getFilLocalidad()>0){
				request.setAttribute("listaColegioVO",actaDAO.getListaColegio(filtro.getFilLocalidad()));
			}
			if(acta!=null){
				//System.out.println("acta es diferente de null"+acta.getActInstancia());
				if(acta.getActInstancia()>0){
					request.setAttribute("listaRolVO",actaDAO.getListaRol(acta.getActInstancia()));
				}
				if(acta.getActRango()>0 && acta.getActRol()>0){
					request.setAttribute("listaParticipanteVO",actaDAO.getListaParticipante(acta.getActInstancia(),acta.getActRango(),acta.getActRol()));
				}
			}
		}catch(ParticipacionException e){
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	private ActaVO actaBuscar(HttpServletRequest request,HttpSession session,Login usuVO,FiltroActaVO filtro,ActaVO acta) throws ServletException{
		try{
			//System.out.println("ME LLEVA");
			if(filtro!=null && filtro.getFilRango()>0){
				request.setAttribute("listaActaVO",actaDAO.getListaActa(filtro));
				request.setAttribute("buscarActaVO","1");
				//System.out.println("VALORES DE ACTA ACTUALES: "+(acta==null?"NULL":String.valueOf(acta.getActNivel())));
				if(acta==null || acta.getActRango()<=0){
					acta=new ActaVO();
					acta.setActNivel(filtro.getFilNivel());
					acta.setActInstancia(filtro.getFilInstancia());
					acta.setActRango(filtro.getFilRango());
					acta.setActLocalidad(filtro.getFilLocalidad());
					acta.setActColegio(filtro.getFilColegio());
					session.setAttribute("actaVO",acta);
				}
			}
			//System.out.println("VALORES DE ACTA DOS: "+(acta==null?"NULL":String.valueOf(acta.getActNivel())));
		}catch(ParticipacionException e){
			throw new ServletException("Errror interno: " + e.getMessage());
		}
		return acta;
	}

	private ActaVO actaEditar(HttpServletRequest request,HttpSession session,Login usuVO,FiltroActaVO filtro) throws ServletException{
		ActaVO i=null;
		try{
			if(filtro!=null && filtro.getFilActa()>0){
				i=actaDAO.getActa(filtro);
				session.setAttribute("actaVO",i);
			}
		}catch(ParticipacionException e){
			throw new ServletException("Errror interno: " + e.getMessage());
		}
		return i;
	}

	private void actaEliminar(HttpServletRequest request,HttpSession session,Login usuVO,FiltroActaVO filtro) throws ServletException{
		try{
			if(filtro!=null && filtro.getFilActa()>0){
				actaDAO.eliminarActa(filtro);
				request.setAttribute(ParamsVO.SMS, "El acta fue eliminada satisfactoriamente");
				session.removeAttribute("actaVO");
			}
			actaNuevo(request,session,usuVO,null);
		}catch(IntegrityException e){
			request.setAttribute(ParamsVO.SMS, "El acta fue no eliminada:"+e.getMessage());
		}catch(ParticipacionException e){
			request.setAttribute(ParamsVO.SMS, "El acta fue no eliminada: Error interno");
		}
	}


	private void actaGuardar(HttpServletRequest request,HttpSession session,Login usuVO,ActaVO acta) throws ServletException{
		try{
			if(acta!=null){
				if(acta.getFormaEstado().equals("1")){
					actaDAO.actualizarActa(acta);
					request.setAttribute(ParamsVO.SMS, "El acta fue actualizada satisfactoriamente");
					session.removeAttribute("actaVO");
				}else{
					actaDAO.ingresarActa(acta);
					request.setAttribute(ParamsVO.SMS, "El acta fue ingresada satisfactoriamente");
					session.removeAttribute("actaVO");
				}
			}
			actaNuevo(request,session,usuVO,null);
		}catch(IntegrityException e){
			request.setAttribute(ParamsVO.SMS, "El acta fue no ingresada/actualizada:"+e.getMessage());
		}catch(Exception e){e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "El acta fue no ingresada/actualizada: Error interno");
		}
	}

	private void actaNuevo(HttpServletRequest request,HttpSession session,Login usuVO,ActaVO acta) throws ServletException{
		//int nivel=Integer.parseInt(usuVO.getNivel());
		try{
			/*
			if(acta==null){
				acta=new ActaVO();
				if(nivel==ParamParticipacion.LOGIN_NIVEL_LOCALIDAD){
					acta.setActLocalidad(Integer.parseInt(usuVO.getMunId()));
					acta.setActTieneLocalidad(1);
				}
				if(nivel==ParamParticipacion.LOGIN_NIVEL_COLEGIO || nivel==ParamParticipacion.LOGIN_NIVEL_SEDE_JORNADA){
					acta.setActLocalidad(Integer.parseInt(usuVO.getMunId()));
					acta.setActColegio(Integer.parseInt(usuVO.getInstId()));
					acta.setActTieneLocalidad(1);
					acta.setActTieneColegio(1);
				}
				session.setAttribute("actaVO",acta);
			}
			*/
			if(acta!=null && acta.getActLocalidad()>0){
				request.setAttribute("listaColegioVO",actaDAO.getListaColegio(acta.getActLocalidad()));
			}
		}catch(ParticipacionException e){
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	
	private void actaImprimir(HttpServletRequest request,Login usuVO,FiltroActaVO filtro,HttpServletResponse response) throws ServletException{
		try{
			if(filtro!=null){
				String codigo=(request.getParameter("filActa")!=null)?request.getParameter("filActa"):new String("");
				response.setContentType("application/zip");
				BufferedInputStream origin = null;
				ServletOutputStream ouputStream = null;
				FileInputStream fi = null;
				int BUFFER_SIZE = 100000;
				int count;
				try {
					//System.out.println("**entro a generarReporte **");
					ResultadoReportesVO resultado=null;
					resultado = buscarReporte(usuVO,filtro,codigo);
					
					if(resultado.isGenerado()){
						//System.out.println("***generado == true***");
				        StringBuffer contentDisposition = new StringBuffer();
				        contentDisposition.append("attachment;");
				        contentDisposition.append("filename=\"");
				        //System.out.println("getArchivo::::"+resultado.getArchivo());
				        contentDisposition.append(resultado.getArchivo());
				        contentDisposition.append("\"");
				        response.setHeader("Content-Disposition", contentDisposition.toString());
				        //System.out.println("RUTA DISCO "+resultado.getRutaDisco());
						File f = new File(resultado.getRutaDisco());
						if (!f.exists()) {
							return;
						}
						byte[] data = new byte[BUFFER_SIZE];
						fi = new FileInputStream(f);
						origin = new BufferedInputStream(fi, BUFFER_SIZE);
						ouputStream = response.getOutputStream();
						while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
							//System.out.println("entro/////");
							ouputStream.write(data, 0, count);
						}
						ouputStream.flush();
						ouputStream.close();
						origin.close();
						fi.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServletException(e.getMessage());
				} finally {
					try {
						if (ouputStream != null)
							ouputStream.close();
						if (origin != null)
							origin.close();
						if (fi != null)
							fi.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	
	
	
	//GENERAR REPORTE
	public ResultadoReportesVO buscarReporte(Login usuVO,FiltroActaVO filtro, String codigo) throws Exception {
		ResultadoReportesVO resultado = new ResultadoReportesVO();
		try{
			//System.out.println("***ENTRO A GENERAR EL REPORTE***");
			Calendar c=Calendar.getInstance();
			//String archivoExcel="";
			String fecha=c.get(Calendar.DAY_OF_MONTH)+"_"+(c.get(Calendar.MONTH)+1)+"_"+c.get(Calendar.YEAR)+"_"+c.get(Calendar.HOUR_OF_DAY)+"_"+c.get(Calendar.MINUTE);
			String rutaJasper = Ruta2.get(contextoTotal, rb.getString("rutaJasper"));
			String archivoJasper = rb.getString("jasperActa");
			String rutaExcel= Ruta.get(contextoTotal, rb.getString("path")+usuVO.getUsuarioId());			
			String rutaExcelRelativo= Ruta.getRelativo(contextoTotal, rb.getString("path")+usuVO.getUsuarioId());
			String archivoPDF = "Reporte_ACTA_"+codigo+"_FECHA_"+fecha+".pdf";			
			String archivoZIP = "Reporte_ACTA_"+codigo+"_FECHA_"+fecha+".zip";			
			//archivoJasper = rb.getString("jasperActa");			
						
			String rutaImagen = Ruta.get(contextoTotal, rb.getString("rutaImagen"));
			String archivoImagen  = rb.getString("imagen");
			String archivoImagen2  = rb.getString("imagen2");
			File reportFile= new File(rutaJasper + archivoJasper);

			//Parametros del Reporte
			Map map=new HashMap();
			map.put("SUBREPORT_DIR", rutaJasper);
			map.put("PATH_ICONO_SECRETARIA", rutaImagen + archivoImagen);			
			map.put("PATH_ICONO_BOGOTA", rutaImagen + archivoImagen2);
			map.put("USUARIO", String.valueOf(usuVO.getUsuarioId()));			
			
			map.put("COD_INSTANCIA", new Integer(filtro.getFilInstancia()));
			map.put("COD_RANGO", new Integer(filtro.getFilRango()));
			map.put("COD_NIVEL", new Integer(filtro.getFilNivel()));
			map.put("CODIGO", new Long(codigo));
			
			//System.out.println("PARAMETROS NIVEL "+filtro.getFilNivel()+"  INSTANCIA "+filtro.getFilInstancia()+"   RANGO "+filtro.getFilRango()+"   CODIGO  "+filtro.getFilActa());
			
			String rutaPDF=getArchivoPDF(reportFile,map,rutaExcel,archivoPDF);			
			List l=new ArrayList();
			l.add(rutaPDF);
			Zip zip=new Zip();			
			if(zip.ponerZip(rutaExcel,archivoZIP,100000,l)){
				resultado.setGenerado(true);				
				resultado.setRuta(rutaExcelRelativo+archivoZIP);
				resultado.setRutaDisco(rutaExcel+archivoZIP);
				resultado.setArchivo(archivoZIP);
				resultado.setTipo("zip");
			}	
		}catch(Exception e){
			e.printStackTrace();
			//resultado.setGenerado(false);
			throw new Exception("Error generando reporte "+e);
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
			con = actaDAO.getConnection();
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
}
