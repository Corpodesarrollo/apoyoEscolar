/**
 * 
 */
package siges.gestionAcademica.repEstadisticos.io;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import siges.dao.OperacionesGenerales;
import siges.dao.Ruta;
import siges.dao.Ruta2; 
import siges.gestionAcademica.repEstadisticos.dao.RepEstadisticosDAO;
import siges.io.Zip;
import siges.gestionAcademica.repEstadisticos.vo.DatosBoletinVO;
import siges.gestionAcademica.repEstadisticos.vo.FiltroRepEstadisticoVO;
import siges.gestionAcademica.repEstadisticos.vo.ParamsVO;
import siges.gestionAcademica.repEstadisticos.vo.ReporteEstadisticoVO;
import siges.gestionAcademica.repEstadisticos.vo.ResultadoConsultaVO;;

/**
 * Objeto de acceso a disco para la generacinn de reportes
 * 15/01/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
/**
 * @author desarrollo
 *
 */
public class RepEstadisticosIO {
	private RepEstadisticosDAO repEstadisticosDAO= null;
	private ResourceBundle rb = null;
	private SimpleDateFormat formaFecha = new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss.SSS");
	
	
	public RepEstadisticosIO(RepEstadisticosDAO boletinDAO) {
		System.out.println("constructor RepEstadisticosIO");
		this.repEstadisticosDAO = boletinDAO;
		rb = ResourceBundle.getBundle("siges.gestionAcademica.repEstadisticos.bundle.repEstadisticos");
	}
	
	
	
	/** 
	 * @param filtroVO
	 * @param reporteVO
	 * @param rutaBase
	 * @return
	 * @throws Exception
	 */
	public ResultadoConsultaVO generarRepAsistencia(FiltroRepEstadisticoVO filtroVO,ReporteEstadisticoVO reporteVO, String  rutaBase)  {
		System.out.println(formaFecha.format(new Date())+  "  generarRepAsistencia");
		ResultadoConsultaVO resultado = new ResultadoConsultaVO();
		try{
			
			/** Armando ruta del jasper*/
			String rutaJasper = Ruta2.get(rutaBase, rb.getString("repEstadisticos.rutaJasper"));
			
			String archivoJasper = rb.getString("reporte_asistencia.jasper");
			File reportFile= new File(rutaJasper.concat(archivoJasper));	
			
			/** Armando ruta salida */
			String rutaExcel= Ruta.get(rutaBase, rb.getString("estadisticos.path").concat(filtroVO.getUsuario()));
			
			/** Ruta imagenes*/
			String rutaImagen = Ruta.get(rutaBase, rb.getString("consulta.rutaImagen")).concat(rb.getString("consulta.imagen") );
			System.out.println("rutaImagen " + rutaImagen);
			
			Map map=new HashMap(); 
			
			map.put("PATH_ICONO_SECRETARIA", rutaImagen );
			map.put("usuario", filtroVO.getUsuario()  ); 
			map.put("fecha", reporteVO.getFecha());
			
			
			String rutaXLS=getArchivoXLS(reportFile,map,rutaExcel,reporteVO.getNombre_xls());
			List l=new ArrayList();
			l.add(rutaXLS);
			Zip zip=new Zip();
			if(zip.ponerZip(rutaExcel,reporteVO.getNombre_zip(),100000,l)){
				resultado.setGenerado(true);
				resultado.setRuta(reporteVO.getRecurso());
				resultado.setRutaDisco(rutaExcel.concat(reporteVO.getNombre_zip()));
				resultado.setArchivo(reporteVO.getNombre_zip());
				resultado.setTipo(ParamsVO.ARCHIVO_TIPO_ZIP);
			}	
		}catch(Exception e){
			resultado.setGenerado(false);
			System.out.println("Error" + e.getMessage()  );
			e.printStackTrace();
			resultado.setGenerado(false);
			resultado.setMensaje(e.getMessage());
		}
		resultado.setGenerado(true);
		return resultado;
	}
	
	
	
	
	/** @funtion Ejecuta el jasper, crear el archivo en disco y lo empaqueta en un .zip
	 * @param filtroVO
	 * @param reporteVO
	 * @param rutaBase
	 * @return ResultadoConsultaVO
	 * @throws Exception
	 */
	public  ResultadoConsultaVO generarRep(DatosBoletinVO datosBoletinVO,ReporteEstadisticoVO reporteVO, String  rutaBase,int tipo){
		System.out.println(formaFecha.format(new Date())+  "  generarRep");
		ResultadoConsultaVO resultado = new ResultadoConsultaVO();
		String archivoJasper ="";
		try{
			
			/** Armando ruta del jasper*/
			String rutaJasper = Ruta2.get(rutaBase, rb.getString("repEstadisticos.rutaJasper"));
			 
			
			
			if(tipo == ParamsVO.TIPOREP_RepEstdcAsig ){ 
				archivoJasper = rb.getString("RepEstadisticaAsignatura");
			}else
				if(tipo == ParamsVO.TIPOREP_RepEstdcArea ){ 
					archivoJasper = rb.getString("RepEstadisticaArea");
				}else
					if(tipo == ParamsVO.TIPOREP_RepEstdcLogro ){ 
						archivoJasper = rb.getString("RepEstadisticaAsigLogros");
					}else
						if(tipo == ParamsVO.TIPOREP_RepEstdcDesct){ 
							archivoJasper = rb.getString("RepEstadisticaDescpt");
						}else
							if(tipo == ParamsVO.TIPOREP_RepEstdcLogroGrad){ 
								archivoJasper = rb.getString("RepEstadisticaAsigLogrosGrado");
							}
			
			
			
			
			File reportFile= new File(rutaJasper.concat(archivoJasper));	
			
			/** Armando ruta salida */
			String rutaExcel= Ruta.get(rutaBase, rb.getString("estadisticos.path").concat(datosBoletinVO.getDABOLUSUARIO()));
			//System.out.println("1. rutaExcel " +rutaExcel);
			
			/** Ruta imagenes*/
			String rutaImagen_escudo = Ruta.get(rutaBase, rb.getString("consulta.rutaImagen")).concat(rb.getString("consulta.imagen") );
			System.out.println("2. rutaImagen " +rutaImagen_escudo );
			
			/** Parametros para el reporte */
			Map map=new HashMap();
			map.put("PATH_ICONO_SECRETARIA", rutaImagen_escudo );
			map.put("CONSEC", new Long(datosBoletinVO.getDABOLCONSEC())); 
			//System.out.println("3. datosBoletinVO.getDABOLCONSEC() " + datosBoletinVO.getDABOLCONSEC());
			
			/** Modificar nombre */
			reporteVO.setNombre_pdf( reporteVO.getNombre_zip().substring(0,reporteVO.getNombre_zip().length() -4) +"."+ ParamsVO.ARCHIVO_TIPO_PDF);
			//System.out.println("4.  reporteVO.setNombre_pdf(  " + reporteVO.getNombre_pdf() );
			//String rutaXLS=getArchivoXLS(reportFile,map,rutaExcel,reporteVO.getNombre_xls());
			
			String rutaPDF = getArchivoPDF(reportFile,map,rutaExcel,reporteVO.getNombre_pdf());
	 
			if(rutaPDF == null){
				resultado.setGenerado(false);
				resultado.setMensaje("Error generado pdf.");
				return resultado;
			}
			//System.out.println("5. rutaPDF " + rutaPDF);
			
			
			List l=new ArrayList();
			//l.add(rutaXLS);
			l.add(rutaPDF);
			
			Zip zip=new Zip();
			if(zip.ponerZip(rutaExcel,reporteVO.getNombre_zip(),100000,l)){
				//System.out.println("6. zip.ponerZip( es TRUE");
				
				File f = new File(rutaExcel+ reporteVO.getNombre_zip());
			 
				
				//System.out.println("9.  SE COLOCO EN TRUE " );
				resultado.setGenerado(true);
				resultado.setRuta(reporteVO.getRecurso());
				//System.out.println("10. reporteVO.getRecurso() " + reporteVO.getRecurso());
				resultado.setRutaDisco(rutaExcel + reporteVO.getNombre_zip());
				resultado.setArchivo(reporteVO.getNombre_zip());
				resultado.setTipo(ParamsVO.ARCHIVO_TIPO_ZIP);
			}	
			datosBoletinVO.setDABOLFECHAGEN(new java.sql.Timestamp(System.currentTimeMillis()).toString());
			
			System.out.println("Termino");
			return resultado;
		}catch(Exception e){
			resultado.setGenerado(false);
			e.printStackTrace();
			System.out.println("Error" + e.getMessage()  );
			
			resultado.setGenerado(false);
			resultado.setMensaje(e.getMessage());
			//System.out.println("SE GENERO CON ERROR ================================================ " + resultado.isGenerado()+" "+ resultado.getMensaje() );
			return resultado;
		}
		 
		
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
	private String getArchivoPDF(File reportFile, Map parameterscopy, String rutaExcel,String archivo)throws Exception {
		
		String archivoCompleto = null;
		Connection con = null;
		try {
			con= repEstadisticosDAO.getConnection();
			
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
	
	
	/**
	 *  Construye el archivo y lo almacena en el servidor
	 * @param reportFile Archivo del reporte
	 * @param parameterscopy Parametros del reporte
	 * @param rutaExcel Ruta de almacenamiento del reporte
	 * @param archivo Nombre del reporte
	 * @return Ruta de disco del reporte
	 * @throws Exception
	 */
	public String getArchivoXLS(File reportFile, Map parameterscopy, String rutaExcel,String archivo)throws Exception {
		Connection con = null;
		String archivoCompleto = null;
		try {
			con = repEstadisticosDAO.getConnection();
			JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile.getPath(), parameterscopy, con);
			String xlsFileName = archivo;
			String xlsFilesSource = rutaExcel;
			archivoCompleto = xlsFilesSource + xlsFileName; 
			File f = new File(xlsFilesSource);
			if (!f.exists())
				FileUtils.forceMkdir(f);
			// USANDO API EXCEL
			JExcelApiExporter xslExporter = new JExcelApiExporter();
			xslExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			xslExporter.setParameter(JExcelApiExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			xslExporter.setParameter(JExcelApiExporterParameter.OUTPUT_FILE_NAME, xlsFilesSource + xlsFileName);
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
	
	
	
	
}
