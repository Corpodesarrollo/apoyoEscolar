/**
 * 
 */
package poa.reportes.io;

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

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;

import poa.reportes.dao.ReportesDAO;
import poa.reportes.vo.ParamsVO;
import poa.reportes.vo.ResultadoReportesVO;
import poa.reportes.vo.FechasSegColegioVO;
import poa.reportes.vo.FiltroReportesVO;
import siges.dao.OperacionesGenerales;
import siges.dao.Ruta;
import siges.dao.Ruta2;
import siges.io.Zip;

/**
 * Objeto de acceso a disco para la generacinn de reportes 15/01/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class ReportesIO {
	private ReportesDAO reportesDAO;

	private ResourceBundle rb;

	/**
	 * Constructor
	 * 
	 * @param reportesDAO
	 *            Objet de acceso a datos
	 */
	public ReportesIO(ReportesDAO reportesDAO) {
		this.reportesDAO = reportesDAO;
		rb = ResourceBundle.getBundle("poa.reportes.bundle.reportes");
	}

	/**
	 * Genera el archivo de reporte de POA segun los parametros de filtro
	 * @param filtro Filtro de busqueda de actividadese
	 * @return Resultado de la generacinn del reporte
	 * @throws Exception
	 */
	public ResultadoReportesVO buscarReporteSED(FiltroReportesVO filtro) throws Exception {
		
		ResultadoReportesVO resultado = new ResultadoReportesVO();
		FiltroReportesVO resultadoFechas = new FiltroReportesVO();
		
		try {
			
			Calendar c = Calendar.getInstance();
			
			String archivoPdf = "";
			String archivoZip = "";
			String archivoExcel = "";
			String nombreArchivo = "";
			String archivoJasper = "";
			String fechaAprobacion = "";
			String ultimaFechaMod = "";	
			Date fechaFinPlan = null;			
			FechasSegColegioVO resFechasSeg = new FechasSegColegioVO();
			Date fechaFinSeg1 = null;
			Date fechaFinSeg2 = null;
			Date fechaFinSeg3 = null;
			Date fechaFinSeg4 = null;

			
			String rutaJasper = Ruta2.get(filtro.getFilRutaBase(), rb.getString("reportes.rutaJasper"));
			String rutaExcel = Ruta.get(filtro.getFilRutaBase(), rb.getString("reportes.path") + filtro.getFilUsuario());
			String fecha = c.get(Calendar.DAY_OF_MONTH) + "_" + (c.get(Calendar.MONTH) + 1) + "_" + c.get(Calendar.YEAR) + "_" + c.get(Calendar.HOUR_OF_DAY) + "_" + c.get(Calendar.MINUTE);
			
			Map map = new HashMap();
			
			map.put("PERIODO", filtro.getFilPeriodo());
			
			if (filtro.getFilTipoReporte() == ParamsVO.CMD_REPORTE_NEC_AREA_GESTION) {
				
				nombreArchivo = "Reporte_NECES_AREA_GESTION_VIGENCIA_" + filtro.getFilVigencia() + "_FECHA_" + fecha;
				archivoJasper = rb.getString("reportes.neces_area_gestion");
				
			} else if (filtro.getFilTipoReporte() == ParamsVO.CMD_REPORTE_NEC_LIN_ACCION) {
				
				nombreArchivo = "Reporte_NECES_LINEAS_ACCION_VIGENCIA_" + filtro.getFilVigencia() + "_FECHA_" + fecha;
				archivoJasper = rb.getString("reportes.neces_lineas_accion");
				
			} else if (filtro.getFilTipoReporte() == ParamsVO.CMD_REPORTE_GEN_SEGUIMIENTO) {									// 52 - Reporte General Seguimiento
				
				nombreArchivo = "Reporte_GENERAL_SEGUIMIENTO_INSTITUCION_" + filtro.getFilInstitucion() + "_VIGENCIA_" + filtro.getFilVigencia() + "_FECHA_" + fecha;
				archivoJasper = rb.getString("reportes.poa");
				
				resultadoFechas= reportesDAO.getUltimaFechaModificacion(filtro.getFilVigencia(), filtro.getFilInstitucion());
				
				if(resultadoFechas != null) {
					
					switch (filtro.getFilPeriodo()) 
					{
		            	case 1:  ultimaFechaMod = resultadoFechas.getFechaModificacion1() != null ? resultadoFechas.getFechaModificacion1() : "";
		                     	break;
		            	case 2:  ultimaFechaMod = resultadoFechas.getFechaModificacion2() != null ? resultadoFechas.getFechaModificacion2() : "";
		                     	break;
		            	case 3:  ultimaFechaMod = resultadoFechas.getFechaModificacion3() != null ? resultadoFechas.getFechaModificacion3() : "";
		                     	break;
		            	case 4:  ultimaFechaMod = resultadoFechas.getFechaModificacion4() != null ? resultadoFechas.getFechaModificacion4() : "";
		                     	break;
		            
		            	default: ultimaFechaMod = "0";
		                     	break;
					}
				if(ultimaFechaMod != null && !ultimaFechaMod.equals("")){
					ultimaFechaMod = reportesDAO.convertirFecha(ultimaFechaMod);	
				}
				
			}
				
			map.put("ULTIMAFECHAMOD", ultimaFechaMod);
			
			
			resFechasSeg = reportesDAO.getFechaFinSeguimiento(filtro.getFilVigencia());
			
			if(resFechasSeg != null) {
				
				switch (filtro.getFilPeriodo()) 
				{
	            	case 1: fechaFinSeg1 = resFechasSeg.getProgFechaFin1() != null ? resFechasSeg.getProgFechaFin1() : null;
	            	
			            	if(fechaFinSeg1 != null){
								if(fechaFinSeg1.compareTo(new Date()) < 0){
									map.put("LEYENDA", "ULTIMA MODIFICACION AL DOCUMENTO");
								} else if(fechaFinSeg1.compareTo(new Date()) >= 0){
									map.put("LEYENDA", "");
								}
							} else{
								map.put("LEYENDA", "");
							}
	                break;
	                
	            	case 2:  fechaFinSeg2 = resFechasSeg.getProgFechaFin2() != null ? resFechasSeg.getProgFechaFin2() : null;
			            	if(fechaFinSeg2 != null){
								if(fechaFinSeg2.compareTo(new Date()) < 0){
									map.put("LEYENDA", "ULTIMA MODIFICACION AL DOCUMENTO");
								} else if(fechaFinSeg2.compareTo(new Date()) >= 0){
									map.put("LEYENDA", "");
								}
							} else{
								map.put("LEYENDA", "");
							 }
					
	                break;
	            	case 3:  fechaFinSeg3 = resFechasSeg.getProgFechaFin3() != null ? resFechasSeg.getProgFechaFin3() : null;
			            	if(fechaFinSeg3 != null){
								if(fechaFinSeg3.compareTo(new Date()) < 0){
									map.put("LEYENDA", "ULTIMA MODIFICACION AL DOCUMENTO");
								} else if(fechaFinSeg3.compareTo(new Date()) >= 0){
									map.put("LEYENDA", "");
								}
							} else{
								map.put("LEYENDA", "");
							 }
	                break;
	            	case 4:  fechaFinSeg4 = resFechasSeg.getProgFechaFin4() != null ? resFechasSeg.getProgFechaFin4() : null;
			            	if(fechaFinSeg4 != null){
								if(fechaFinSeg4.compareTo(new Date()) < 0){
									map.put("LEYENDA", "ULTIMA MODIFICACION AL DOCUMENTO");
								} else if(fechaFinSeg4.compareTo(new Date()) >= 0){
									map.put("LEYENDA", "");
								}
							} else{
								map.put("LEYENDA", "");
							 }
	                break;
	            
	            	default: map.put("LEYENDA", "");
	                     	break;
				 }
				
						
			  }
				
		} else if (filtro.getFilTipoReporte() == ParamsVO.CMD_CONSULTA_GEN_ACT) {
				
				nombreArchivo = "Reporte_CONSULTA_GENERAL_ACTIVIDADES_VIGENCIA_" + filtro.getFilVigencia() + "_FECHA_" + fecha;
				archivoJasper = rb.getString("reportes.consulta_gen_actividades");
				
			} else if (filtro.getFilTipoReporte() == ParamsVO.CMD_CONSULTA_GEN_NEC) {
				
				nombreArchivo = "Reporte_CONSULTA_GENERAL_NECESIDADES_VIGENCIA_" + filtro.getFilVigencia() + "_FECHA_" + fecha;
				archivoJasper = rb.getString("reportes.consulta_gen_necesidades");
				
			} else if (filtro.getFilTipoReporte() == ParamsVO.CMD_REPORTE_CUMP_METAS_LINEAS_ACCION) {
				
				nombreArchivo = "Reporte_CUMPLI_METAS_LINEAS_ACCION_VIGENCIA_" + filtro.getFilVigencia() + "_FECHA_" + fecha;
				archivoJasper = rb.getString("reportes.reporte_cump_metas_lineas");
				
			} else if (filtro.getFilTipoReporte() == ParamsVO.CMD_REPORTE_AVANCE_PON_AREAS) {									// 56 - Avance del Ponderador por �reas de Gesti�n
				
				nombreArchivo = "Reporte_AVANCE_POND_AREAS_VIGENCIA_" + filtro.getFilVigencia() + "_FECHA_" + fecha;
				archivoJasper = rb.getString("reportes.reporte_avances_pond_areas");
				
			} else if (filtro.getFilTipoReporte() == ParamsVO.CMD_REPORTE_AVANCE_PON_LINEAS_ACCION) {							// 57 - Avance del Ponderador por L�neas de Acci�n
				
				nombreArchivo = "Reporte_AVANCE_POND_LINEAS_ACCION_VIGENCIA_" + filtro.getFilVigencia() + "_FECHA_" + fecha;
				archivoJasper = rb.getString("reportes.reporte_avances_pond_lineas");
				
			} else if (filtro.getFilTipoReporte() == ParamsVO.CMD_REPORTE_ESTADO_META_LINEAS_ACCION) {
				
				reportesDAO.llenaTablaTmpReporte10(filtro.getFilVigencia(), filtro.getFilLocalidad(), filtro.getFilInstitucion(), filtro.getFilUsuario());
				nombreArchivo = "Reporte_ESTADO_META_LINEAS_ACCION_VIGENCIA_" + filtro.getFilVigencia() + "_FECHA_" + fecha;
				archivoJasper = rb.getString("reportes.reporte_estado_meta_lineas");
				
			} else if (filtro.getFilTipoReporte() == ParamsVO.CMD_REPORTE_POA) {												// 59 - Reporte POA
				
				nombreArchivo = "Reporte_POA_INST_"	+ filtro.getFilInstitucion() + "_VIGENCIA_" + filtro.getFilVigencia() + "_FECHA_" + fecha;
				archivoJasper = rb.getString("reportes.POA2");
				
				fechaAprobacion = reportesDAO.getFechaAprobacionPoa(filtro.getFilVigencia(), filtro.getFilInstitucion());	
				if(fechaAprobacion == null){
					fechaAprobacion = "";
				}
				fechaAprobacion = reportesDAO.convertirFecha(fechaAprobacion);				
				map.put("FECHAAPROBACIONPOA", fechaAprobacion);
				
				fechaFinPlan = reportesDAO.getFechaHastaPlaneacion(filtro.getFilVigencia());
				if(fechaFinPlan != null){
					if(fechaFinPlan.compareTo(new Date()) < 0){
						map.put("LEYENDA", "ULTIMA MODIFICACION AL DOCUMENTO");
					} else if(fechaFinPlan.compareTo(new Date()) >= 0){
						map.put("LEYENDA", "");
					}
				}
				/* fecha1.compareTo(fecha2)
				 * Fechas iguales, el método devolverá un cero (0). 
				 * Si fecha 1 < fecha 2, el método devolverá un valor menor a cero (<0). 
				 * Si fecha 1 > fecha 2, el método devolverá un valor mayor a cero (>0). 				 
				 */
				
			} else if (filtro.getFilTipoReporte() == ParamsVO.CMD_REPORTE_POA_CONSOLIDADO) {									// 60 - Reporte General Consolidado
				
				nombreArchivo = "Reporte_POA_CONSOLIDADO_INSTITUCION_" + filtro.getFilInstitucion() + "_VIGENCIA_" + filtro.getFilVigencia() + "_FECHA_" + fecha;
				archivoJasper = rb.getString("reportes.jasperPOA_Consolidado");
				
				if(filtro.getFilInstitucion() > 0 && filtro.getFilInstitucion() != -99) { 
					archivoJasper = rb.getString("reportes.jasperPOA_ConsolidadoCol");
				}
				
				resultadoFechas= reportesDAO.getUltimaFechaModificacion(filtro.getFilVigencia(), filtro.getFilInstitucion());
				
				if(resultadoFechas != null) {
					
					switch (filtro.getFilPeriodo()) 
					{
		            	case 1:  ultimaFechaMod = resultadoFechas.getFechaModificacion1() != null ? resultadoFechas.getFechaModificacion1() : "";
		                     	break;
		            	case 2:  ultimaFechaMod = resultadoFechas.getFechaModificacion2() != null ? resultadoFechas.getFechaModificacion2() : "";
		                     	break;
		            	case 3:  ultimaFechaMod = resultadoFechas.getFechaModificacion3() != null ? resultadoFechas.getFechaModificacion3() : "";
		                     	break;
		            	case 4:  ultimaFechaMod = resultadoFechas.getFechaModificacion4() != null ? resultadoFechas.getFechaModificacion4() : "";
		                     	break;
		            
		            	default: ultimaFechaMod = "";
		                     	break;
					}
				if(ultimaFechaMod != null && !ultimaFechaMod.equals("")){
					ultimaFechaMod = reportesDAO.convertirFecha(ultimaFechaMod);	
				}
				
			}
				
			map.put("ULTIMAFECHAMOD", ultimaFechaMod);
			
resFechasSeg = reportesDAO.getFechaFinSeguimiento(filtro.getFilVigencia());
			
			if(resFechasSeg != null) {
				
				switch (filtro.getFilPeriodo()) 
				{
	            	case 1: fechaFinSeg1 = resFechasSeg.getProgFechaFin1() != null ? resFechasSeg.getProgFechaFin1() : null;
	            	
			            	if(fechaFinSeg1 != null){
								if(fechaFinSeg1.compareTo(new Date()) < 0){
									map.put("LEYENDA", "ULTIMA MODIFICACION AL DOCUMENTO");
								} else if(fechaFinSeg1.compareTo(new Date()) >= 0){
									map.put("LEYENDA", "");
								}
							} else{
								map.put("LEYENDA", "");
							}
	                break;
	                
	            	case 2:  fechaFinSeg2 = resFechasSeg.getProgFechaFin2() != null ? resFechasSeg.getProgFechaFin2() : null;
			            	if(fechaFinSeg2 != null){
								if(fechaFinSeg2.compareTo(new Date()) < 0){
									map.put("LEYENDA", "ULTIMA MODIFICACION AL DOCUMENTO");
								} else if(fechaFinSeg2.compareTo(new Date()) >= 0){
									map.put("LEYENDA", "");
								}
							} else{
								map.put("LEYENDA", "");
							 }
					
	                break;
	            	case 3:  fechaFinSeg3 = resFechasSeg.getProgFechaFin3() != null ? resFechasSeg.getProgFechaFin3() : null;
			            	if(fechaFinSeg3 != null){
								if(fechaFinSeg3.compareTo(new Date()) < 0){
									map.put("LEYENDA", "ULTIMA MODIFICACION AL DOCUMENTO");
								} else if(fechaFinSeg3.compareTo(new Date()) >= 0){
									map.put("LEYENDA", "");
								}
							} else{
								map.put("LEYENDA", "");
							 }
	                break;
	            	case 4:  fechaFinSeg4 = resFechasSeg.getProgFechaFin4() != null ? resFechasSeg.getProgFechaFin4() : null;
			            	if(fechaFinSeg4 != null){
								if(fechaFinSeg4.compareTo(new Date()) < 0){
									map.put("LEYENDA", "ULTIMA MODIFICACION AL DOCUMENTO");
								} else if(fechaFinSeg4.compareTo(new Date()) >= 0){
									map.put("LEYENDA", "");
								}
							} else{
								map.put("LEYENDA", "");
							 }
	                break;
	            
	            	default: map.put("LEYENDA", "");
	                     	break;
				 }
				
						
			  }
				
		} else if (filtro.getFilTipoReporte() == ParamsVO.CMD_POA) {
				
				nombreArchivo = "Reporte_POA_INST_"	+ filtro.getFilInstitucion() + "_VIGENCIA_" + filtro.getFilVigencia() + "_FECHA_" + fecha;
				archivoJasper = rb.getString("reportes.reportepoa"); /** SE AGREGO PARA EVITAR CONFLICTOS CON CMD_REPORTE_POA */
				
			}

			String archivoImagen = rb.getString("reportes.imagen");
			String rutaImagen = Ruta.get(filtro.getFilRutaBase(), rb.getString("reportes.rutaImagen"));
			String rutaExcelRelativo = Ruta.getRelativo(filtro.getFilRutaBase(), rb.getString("reportes.path") + filtro.getFilUsuario());
			
			System.out.println("archivoJasper: " + archivoJasper);
			System.out.println("rutaJasper: " + rutaJasper);
			System.out.println("rutaExcel: " + rutaExcel);

			File reportFile = new File(rutaJasper + archivoJasper);

			// Parametros del Reporte
			map.put("SUBREPORT_DIR", rutaJasper);
			map.put("USUARIO", String.valueOf(filtro.getFilUsuario()));
			map.put("COD_OBJETIVO", new Long(filtro.getFilObjetivo()));
			map.put("VIGENCIA", String.valueOf(filtro.getFilVigencia()));
			map.put("PATH_ICONO_SECRETARIA", rutaImagen + archivoImagen);
			map.put("COD_LOCALIDAD", String.valueOf(filtro.getFilLocalidad()));
			map.put("LOCALIDAD", reportesDAO.getNombreLocalidad(filtro.getFilLocalidad()));
			
			if (filtro.getFilTipoReporte() == 50
					|| filtro.getFilTipoReporte() == 51
					|| filtro.getFilTipoReporte() == 55
					|| filtro.getFilTipoReporte() == 56
					|| filtro.getFilTipoReporte() == 57
					|| filtro.getFilTipoReporte() == 58
					) {
				
				if (filtro.getFilInstitucion() > 0) {
					map.put("INSTITUCION", new Long(filtro.getFilInstitucion()));
				} else
					map.put("INSTITUCION", new Long(-99));
				
			} else {
				
				if (filtro.getFilInstitucion() > 0) {
					map.put("INSTITUCION", new Long(filtro.getFilInstitucion()));
				} else {
					map.put("INSTITUCION", new Long("-99"));
				}
			}
			
			if (filtro.getFilTipoReporte() == 53) {
				if (filtro.getFilAreaGestion() > 0) {
					map.put("AREA", String.valueOf(filtro.getFilAreaGestion()));
				} else
					map.put("AREA", "-99");

				if (filtro.getFilLineaAccion() > 0) {
					map.put("LINEA", String.valueOf(filtro.getFilLineaAccion()));
				} else
					map.put("LINEA", "-99");

				if (filtro.getFilFteFin() > 0) {
					map.put("FUENTE_FIN", String.valueOf(filtro.getFilFteFin()));
				} else {
					map.put("FUENTE_FIN", "-99");
				}

				if (filtro.getFilPorEjec1() >= 0) {
					map.put("POREJEC1", String.valueOf(filtro.getFilPorEjec1()));
				} else
					map.put("POREJEC1", "-99");

				if (filtro.getFilPorEjec2() >= 0) {
					map.put("POREJEC2", String.valueOf(filtro.getFilPorEjec2()));
				} else
					map.put("POREJEC2", "-99");

			}
			
			if (filtro.getFilTipoReporte() == 54) {
				if (filtro.getFilAreaGestion() > 0) {
					map.put("AREA", String.valueOf(filtro.getFilAreaGestion()));
				} else
					map.put("AREA", "-99");

				if (filtro.getFilLineaAccion() > 0) {
					map.put("LINEA", String.valueOf(filtro.getFilLineaAccion()));
				} else
					map.put("LINEA", "-99");

				if (filtro.getFilFteFin() > 0) {
					map.put("FUENTE_FIN", String.valueOf(filtro.getFilFteFin()));
				} else
					map.put("FUENTE_FIN", "-99");
				
			}
			
			if (filtro.getFilLocalidad() > 0 && filtro.getFilInstitucion() > 0) {
				map.put("NOMBRE_COL", reportesDAO.getNombreInsititucion(filtro.getFilLocalidad(), filtro.getFilInstitucion()));
			}
			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			map.put("FECHA", formato.format(new Date()));
			
			// Nombre archivos segun tipo
			archivoExcel = nombreArchivo + ".xls";
			archivoPdf = nombreArchivo + ".pdf";
			archivoZip = nombreArchivo + ".zip";

			String rutaXLS = "";
			List l = new ArrayList();
			 
			if (filtro.getFilTipoReporte() == 60) {
				
				map.put("INSTITUCION", String.valueOf(filtro.getFilInstitucion()));
				map.put("NOMBRE_COL", "N.A.");
				
				if(filtro.getFilInstitucion() > 0 && filtro.getFilInstitucion() != -99)
					map.put("NOMBRE_COL", reportesDAO.getNombreInsititucion(filtro.getFilLocalidad(), filtro.getFilInstitucion()));
				
			} else {
				String rutaPDF = getArchivoPDF(reportFile, map, rutaExcel, archivoPdf);
				l.add(rutaPDF);
			}

			switch (filtro.getFilTipoReporte()) {
			case ParamsVO.CMD_REPORTE_POA:
			case ParamsVO.CMD_CONSULTA_GEN_ACT:
			case ParamsVO.CMD_REPORTE_GEN_SEGUIMIENTO:
			case ParamsVO.CMD_REPORTE_NEC_LIN_ACCION:
			case ParamsVO.CMD_CONSULTA_GEN_NEC:
			case ParamsVO.CMD_REPORTE_CUMP_METAS_LINEAS_ACCION:
			case ParamsVO.CMD_REPORTE_ESTADO_META_LINEAS_ACCION:
			case ParamsVO.CMD_REPORTE_NEC_AREA_GESTION:
			case ParamsVO.CMD_REPORTE_AVANCE_PON_AREAS:
			case ParamsVO.CMD_REPORTE_AVANCE_PON_LINEAS_ACCION:
			case ParamsVO.CMD_REPORTE_POA_CONSOLIDADO:	

				rutaXLS = getArchivoXLS(reportFile, map, rutaExcel, archivoExcel);
				l.add(rutaXLS);
				break;
			}

			Zip zip = new Zip();
			if (zip.ponerZip(rutaExcel, archivoZip, 100000, l)) {
			// if(zip.ponerZip("D:\\Reportes\\", archivoZip, 100000, l)) {
				resultado.setGenerado(true);
				resultado.setRuta(rutaExcelRelativo + archivoZip);
				resultado.setRutaDisco(rutaExcel + archivoZip);
				resultado.setArchivo(archivoZip);
				resultado.setTipo("zip");
			}
			
		} catch (Exception e) {
			resultado.setGenerado(false);
			throw new Exception("Error generando reporte " + e);
		}
		
		return resultado;
		
	}

	/**
	 * Construye el archivo y lo almacena en el servidor
	 * 
	 * @param reportFile
	 *            Archivo del reporte
	 * @param parameterscopy
	 *            Parametros del reporte
	 * @param rutaExcel
	 *            Ruta de almacenamiento del reporte
	 * @param archivo
	 *            Nombre del reporte
	 * @return Ruta de disco del reporte
	 * @throws Exception
	 */
	public String getArchivoXLS(File reportFile, Map parameterscopy,
			String rutaExcel, String archivo) throws Exception {
		Connection con = null;
		String archivoCompleto = null;
		try {
			con = reportesDAO.getConnection();
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					reportFile.getPath(), parameterscopy, con);
			String xlsFileName = archivo;
			String xlsFilesSource = rutaExcel;
			archivoCompleto = xlsFilesSource + xlsFileName;
			// xlsFilesSource = "D:\\Reportes\\";
			// archivoCompleto = "D:\\Reportes\\" + xlsFileName;
			File f = new File(xlsFilesSource);
			if (!f.exists())
				FileUtils.forceMkdir(f);
			// USANDO API EXCEL
			JExcelApiExporter xslExporter = new JExcelApiExporter();
			xslExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			 xslExporter.setParameter(JExcelApiExporterParameter.IS_DETECT_CELL_TYPE,Boolean.TRUE);
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

	/**
	 * Construye el archivo y lo z en el servidor
	 * 
	 * @param reportFile
	 *            Archivo del reporte
	 * @param parameterscopy
	 *            Parametros del reporte
	 * @param rutaExcel
	 *            Ruta de almacenamiento del reporte
	 * @param archivo
	 *            Nombre del reporte
	 * @return Ruta de disco del reporte
	 * @throws Exception
	 */
	public String getArchivoPDF(File reportFile, Map parameterscopy, String rutaExcel, String archivo) throws Exception {
		
		Connection con = null;
		String archivoCompleto = null;
		FileOutputStream fileOut = null;
		
		try {
			
			con = reportesDAO.getConnection();
			
			byte[] bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parameterscopy, con);
			
			String xlsFileName = archivo;
			String xlsFilesSource = rutaExcel;
			
			archivoCompleto = xlsFilesSource + xlsFileName;
			// archivoCompleto = "D:\\Reportes\\" + xlsFileName;
			
			File f = new File(xlsFilesSource);
			
			if (!f.exists())
				FileUtils.forceMkdir(f);
			
			fileOut = new FileOutputStream(archivoCompleto);
			
			IOUtils.write(bytes, fileOut);
			
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
				if (fileOut != null) {
					fileOut.close();
				}
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(e);
			}

		}
		
		return archivoCompleto;
		
	}

}
