package siges.permisos;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import siges.dao.*;
import siges.io.Zip;
import siges.permisos.vo.BitacoraVO;

/**
 * Nombre: ControllerFiltro<br>
 * Descripcinn: Filtro de Perfiles<br>
 * Funciones de la pngina: Controla la vista del formulario a la hora de filtrar
 * Privilegios<br>
 * Entidades afectadas: Permisos, Perfil<br>
 * Fecha de modificacinn: 20/07/2005 <br>
 * 
 * @author Latined <br>
 * @version v 1.0 <br>
 */
public class ControllerBitacora extends HttpServlet {

	private static final long serialVersionUID = 1L;


						// validacion de los datos del formulario

	private Cursor cursor;// objeto que maneja las sentencias sql

	private Util util;

	private HttpSession session;

	private BitacoraVO bitacora = null;
	
	private String contexto;
	
	private ResourceBundle rb;


	/**
	 * Función: Procesa la peticion HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		String sig = "/permisos/Bitacora.jsp";
		String boton;
		cursor = new Cursor();
		ServletContext context = getServletContext();
		contexto = context.getRealPath("/");

		try {
			util = new Util(cursor);
			asignarBeans(request);
			boton = (request.getParameter("cmd") != null) ? request.getParameter("cmd") : new String("");

			if (boton.equals("Buscar")) {
				
				rb = ResourceBundle
						.getBundle("siges.gestionAdministrativa.repResultadosAca.bundle.repResultadosAca");
				
				String path = Ruta2.get(context.getRealPath("/"),
						rb.getString("rep_ruta_jaspers"));
				
				String path_escudo = Ruta.get(context.getRealPath("/"),
						rb.getString("rep_escudo"));
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
				Date date = new Date();
				String key = dateFormat.format(date);
				
				String nombrePdf = "Reporte_bitacora_"+ key +".pdf";
				String nombreXls = "Reporte_bitacora_"+ key +".xls";
			
				String archivosalida = Ruta.get(contexto,"private.resultados.bitacora");
				
				String archivoZip = "bitacora_"+ key +".zip";
				
				String archivoImagen  = rb.getString("consulta.imagen");
				
				Map map = new HashMap();
				map.put("PATH_ICONO_SECRETARIA", path_escudo + archivoImagen);
				map.put("usuario", bitacora.getUsuario());
				map.put("fechainicio", bitacora.getFechainicio());
				map.put("fechafin", bitacora.getFechafin());
				
				File reporte1 = new File(path + rb.getString("rep.reporte5"));
				
				String rutaXLS = getArchivoXLS(reporte1, map,
						archivosalida, nombreXls);
				
				getArchivoPDF(reporte1,map,archivosalida,nombrePdf);
				
				List l=new ArrayList(); 
				l.add(rutaXLS);
				l.add(archivosalida+ "/" + nombrePdf);
				Zip zip=new Zip();
				zip.ponerZip(archivosalida,archivoZip,100000,l);
				
				bitacora.setFileName(archivoZip);
				bitacora.setGenerado(1);
				request.getSession().setAttribute("bitacora", bitacora);
			}
			
			return sig;
		} catch (Exception e) {
			System.out.println("Error " + this + ": " + e.toString());
			throw new ServletException(e);
		} finally {
			if (cursor != null)
				cursor.cerrar();
			if (util != null)
				util.cerrar();
		}
	}
	
	public String getArchivoPDF(File reportFile, Map parameterscopy, String rutaExcel,String archivo)throws Exception {
		Connection con = null;
		String archivoCompleto = null;
		try {
			con = cursor.getConnection();
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
	
	public String getArchivoXLS(File reportFile, Map parameterscopy,
			String rutaExcel, String archivo) throws Exception {
		Connection con = null;
		String archivoCompleto = null;
		try {
			con = cursor.getConnection();
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					reportFile.getPath(), parameterscopy, con);
			String xlsFileName = archivo;
			String xlsFilesSource = rutaExcel;
			archivoCompleto = xlsFilesSource + xlsFileName;
			File f = new File(xlsFilesSource);
			if (!f.exists())
				FileUtils.forceMkdir(f);
			// USANDO API EXCEL
			JExcelApiExporter xslExporter = new JExcelApiExporter();
			xslExporter.setParameter(JRExporterParameter.JASPER_PRINT,
					jasperPrint);
			xslExporter.setParameter(JExcelApiExporterParameter.MAXIMUM_ROWS_PER_SHEET, Integer.decode("65000"));
			xslExporter.setParameter(
					JExcelApiExporterParameter.IS_ONE_PAGE_PER_SHEET,
					Boolean.FALSE);
			xslExporter.setParameter(
					JExcelApiExporterParameter.OUTPUT_FILE_NAME, xlsFilesSource
							+ xlsFileName);
			xslExporter.exportReport();
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

	/**
	 * Funcinn: Elimina del contexto de la sesion los beans de informacion del
	 * usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void borrarBeans(HttpServletRequest request) throws ServletException, IOException {
		session.removeAttribute("nuevoPermiso");
	}

	/**
	 * Funcinn: Inserta los valores por defecto para el bean de información
	 * bnsica con respecto a la informacion de la institucinn educativa
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeans(HttpServletRequest request) throws ServletException, IOException {
		if (request.getSession().getAttribute("login") != null) {
			bitacora = (BitacoraVO)request.getSession().getAttribute("bitacora");
			if(bitacora == null){
				bitacora = new BitacoraVO();
				request.getSession().setAttribute("bitacora", bitacora);
			}
			return true;
		}
		return true;
	}


	/**
	 * Funcinn: Recibe la peticion por el metodo get de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);// redirecciona la peticion a doPost
	}

	/**
	 * Funcinn: Recibe la peticion por el metodo Post de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String s = process(request, response);
		if (s != null && !s.equals(""))
			ir(1, s, request, response);
	}

	/**
	 * Funcinn: Redirige el control a otro servlet
	 * 
	 * @param int
	 *            a: 1=redirigir como 'include', 2=redirigir como 'forward'
	 * @param String
	 *            s
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public void ir(int a, String s, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (cursor != null)
			cursor.cerrar();
		RequestDispatcher rd = getServletContext().getRequestDispatcher(s);
		if (a == 1)
			rd.include(request, response);
		else
			rd.forward(request, response);
	}



}