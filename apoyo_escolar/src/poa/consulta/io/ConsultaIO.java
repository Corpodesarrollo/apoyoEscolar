/**
 * 
 */
package poa.consulta.io;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
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

import poa.consulta.dao.ConsultaDAO;
import poa.consulta.vo.FiltroConsultaVO;
import poa.consulta.vo.ResultadoVO;
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
public class ConsultaIO {
	private ConsultaDAO consultaDAO;

	private ResourceBundle rb;

	/**
	 * Constructor
	 * 
	 * @param consultaDAO
	 *            Objet de acceso a datos
	 */
	public ConsultaIO(ConsultaDAO consultaDAO) {
		this.consultaDAO = consultaDAO;
		rb = ResourceBundle.getBundle("poa.consulta.bundle.Consulta");
	}

	/**
	 * Genera el archivo de reporte de POA segun los parametros de filtro
	 * 
	 * @param filtro
	 *            Filtro de busqueda de actividadese
	 * @return Resultado de la generacinn del reporte
	 * @throws Exception
	 */
	public ResultadoVO buscarPOA(FiltroConsultaVO filtro) throws Exception {
		ResultadoVO resultado = new ResultadoVO();
		try {
			if (!consultaDAO.validarDatos(filtro)) {
				resultado.setGenerado(false);
				return resultado;
			}
			String dane = consultaDAO.getDane(filtro.getFilInstitucion());
			Calendar c = Calendar.getInstance();
			String fecha = c.get(Calendar.DAY_OF_MONTH) + "_"
					+ (c.get(Calendar.MONTH) + 1) + "_" + c.get(Calendar.YEAR)
					+ "_" + c.get(Calendar.HOUR_OF_DAY) + "_"
					+ c.get(Calendar.MINUTE);
			String rutaJasper = Ruta2.get(filtro.getFilRutaBase(),
					rb.getString("consulta.rutaJasper"));
			String archivoJasper = rb.getString("consulta.jasper");
			String rutaExcel = Ruta.get(filtro.getFilRutaBase(),
					rb.getString("consulta.path") + filtro.getFilUsuario());
			String archivoExcel = "Reporte_POA_DANE_" + dane + "_VIGENCIA_"
					+ filtro.getFilVigencia() + "_FECHA_" + fecha + ".xls";
			String archivoPdf = "Reporte_POA_DANE_" + dane + "_VIGENCIA_"
					+ filtro.getFilVigencia() + "_FECHA_" + fecha + ".pdf";
			String archivoZip = "Reporte_POA_DANE_" + dane + "_VIGENCIA_"
					+ filtro.getFilVigencia() + "_FECHA_" + fecha + ".zip";
			String rutaExcelRelativo = Ruta.getRelativo(
					filtro.getFilRutaBase(), rb.getString("consulta.path")
							+ filtro.getFilUsuario());
			String rutaImagen = Ruta2.get(filtro.getFilRutaBase(),
					rb.getString("consulta.rutaImagen"));
			String archivoImagen = rb.getString("consulta.imagen");

			File reportFile = new File(rutaJasper + archivoJasper);

			// Parametros del Reporte
			Map map = new HashMap();
			map.put("SUBREPORT_DIR", rutaJasper);
			map.put("PATH_ICONO_SECRETARIA", rutaImagen + archivoImagen);
			map.put("INSTITUCION", new Long(filtro.getFilInstitucion()));
			map.put("VIGENCIA", String.valueOf(filtro.getFilVigencia()));
			map.put("COD_OBJETIVO", new Long(-99));

			String rutaXLS = getArchivoXLS(reportFile, map, rutaExcel,
					archivoExcel);
			String rutaPDF = getArchivoPDF(reportFile, map, rutaExcel,
					archivoPdf);
			List l = new ArrayList();
			l.add(rutaXLS);
			l.add(rutaPDF);
			Zip zip = new Zip();
			if (zip.ponerZip(rutaExcel, archivoZip, 100000, l)) {
				resultado.setGenerado(true);
				resultado.setRuta(rutaExcelRelativo + archivoZip);
				resultado.setRutaDisco(rutaExcel + archivoZip);
				resultado.setArchivo(archivoZip);
				resultado.setTipo("zip");
			}
		} catch (Exception e) {
			// resultado.setGenerado(false);
			throw new Exception("Error generando el reporte");
		}
		return resultado;
	}

	/**
	 * Genera el archivo de reporte de POA segun los parametros de filtro
	 * 
	 * @param filtro
	 *            Filtro de busqueda de actividadese
	 * @return Resultado de la generacinn del reporte
	 * @throws Exception
	 */
	public ResultadoVO buscarPOASED(FiltroConsultaVO filtro) throws Exception {
		ResultadoVO resultado = new ResultadoVO();
		try {
			//if (!consultaDAO.validarDatos(filtro)) {
			//	resultado.setGenerado(false);
			//	return resultado;
			//} else {
				Calendar c = Calendar.getInstance();
				String fecha = c.get(Calendar.DAY_OF_MONTH) + "_"
						+ (c.get(Calendar.MONTH) + 1) + "_" + c.get(Calendar.YEAR)
						+ "_" + c.get(Calendar.HOUR_OF_DAY) + "_"
						+ c.get(Calendar.MINUTE);
				String rutaJasper = Ruta2.get(filtro.getFilRutaBase(),
						rb.getString("consulta.rutaJasper"));
				String archivoJasper = rb.getString("consulta.jasperSED");
				String rutaExcel = Ruta.get(filtro.getFilRutaBase(),
						rb.getString("consulta.path") + filtro.getFilUsuario());
				String archivoExcel = "Reporte_POA_SED_VIGENCIA_"
						+ filtro.getFilVigencia() + "_FECHA_" + fecha + ".xls";
				String archivoPdf = "Reporte_POA_SED_VIGENCIA_"
						+ filtro.getFilVigencia() + "_FECHA_" + fecha + ".pdf";
				String archivoZip = "Reporte_POA_SED_VIGENCIA_"
						+ filtro.getFilVigencia() + "_FECHA_" + fecha + ".zip";
				String rutaExcelRelativo = Ruta.getRelativo(
						filtro.getFilRutaBase(), rb.getString("consulta.path")
								+ filtro.getFilUsuario());
				String rutaImagen = Ruta.get(filtro.getFilRutaBase(),
						rb.getString("consultaSeguimiento.rutaImagen_escudo"));
				String archivoImagen = rb.getString("consulta.imagen");
	
				File reportFile = new File(rutaJasper + archivoJasper);
	
				// Parametros del Reporte
				Map map = new HashMap();
				map.put("SUBREPORT_DIR", rutaJasper);
				map.put("PATH_ICONO_SECRETARIA", rutaImagen + archivoImagen);
				map.put("LOCALIDAD", String.valueOf(filtro.getFilLocalidad()));
				map.put("INSTITUCION", new Long(filtro.getFilInstitucion()));
				map.put("VIGENCIA", String.valueOf(filtro.getFilVigencia()));
				map.put("COD_OBJETIVO", new Long(-99));
	
				String rutaXLS = getArchivoXLS(reportFile, map, rutaExcel,
						archivoExcel);
				String rutaPDF = getArchivoPDF(reportFile, map, rutaExcel,
						archivoPdf);
				List l = new ArrayList();
				l.add(rutaXLS);
				l.add(rutaPDF);
				Zip zip = new Zip();
				if (zip.ponerZip(rutaExcel, archivoZip, 100000, l)) {
					resultado.setGenerado(true);
					resultado.setRuta(rutaExcelRelativo + archivoZip);
					resultado.setRutaDisco(rutaExcel + archivoZip);
					resultado.setArchivo(archivoZip);
					resultado.setTipo("zip");
				}
				return resultado;
			//}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error generando el reporte de SED");
		}
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
			con = consultaDAO.getConnection();
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
	public String getArchivoPDF(File reportFile, Map parameterscopy,
			String rutaExcel, String archivo) throws Exception {
		Connection con = null;
		String archivoCompleto = null;
		FileOutputStream fileOut = null;
		try {
			con = consultaDAO.getConnection();
			byte[] bytes = JasperRunManager.runReportToPdf(
					reportFile.getPath(), parameterscopy, con);
			String xlsFileName = archivo;
			String xlsFilesSource = rutaExcel;
			archivoCompleto = xlsFilesSource + xlsFileName;
			File f = new File(xlsFilesSource);

			if (!f.exists())
				FileUtils.forceMkdir(f);
			fileOut = new FileOutputStream(archivoCompleto);
			IOUtils.write(bytes, fileOut);
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
				if (fileOut != null) {
					fileOut.close();
				}
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return archivoCompleto;
	}

	/**
	 * Genera el archivo de reporte de POA segun los parametros de filtro
	 * 
	 * @param filtro
	 *            Filtro de busqueda de actividadese
	 * @return Resultado de la generacinn del reporte
	 * @throws Exception
	 */
	public ResultadoVO buscarPOASeguimientoSED(FiltroConsultaVO filtro)
			throws Exception {
		ResultadoVO resultado = new ResultadoVO();
		try {
			if (!consultaDAO.validarDatos(filtro)) {
				resultado.setGenerado(false);
				return resultado;
			}
			// String dane=consultaDAO.getDane(filtro.getFilInstitucion());
			Calendar c = Calendar.getInstance();
			String fecha = c.get(Calendar.DAY_OF_MONTH) + "_"
					+ (c.get(Calendar.MONTH) + 1) + "_" + c.get(Calendar.YEAR)
					+ "_" + c.get(Calendar.HOUR_OF_DAY) + "_"
					+ c.get(Calendar.MINUTE);
			String rutaJasper = Ruta2.get(filtro.getFilRutaBase(),
					rb.getString("consultaSeguimiento.rutaJasper"));
			String archivoJasper = rb
					.getString("consultaSeguimiento.jasperSED");
			String rutaExcel = Ruta.get(
					filtro.getFilRutaBase(),
					rb.getString("consultaSeguimiento.path")
							+ filtro.getFilUsuario());
			String archivoExcel = "Reporte_POA_SED_VIGENCIA_"
					+ filtro.getFilVigencia() + "_FECHA_" + fecha + ".xls";
			String archivoPdf = "Reporte_POA_SED_VIGENCIA_"
					+ filtro.getFilVigencia() + "_FECHA_" + fecha + ".pdf";
			String archivoZip = "Reporte_POA_SED_VIGENCIA_"
					+ filtro.getFilVigencia() + "_FECHA_" + fecha + ".zip";
			String rutaExcelRelativo = Ruta.getRelativo(
					filtro.getFilRutaBase(),
					rb.getString("consultaSeguimiento.path")
							+ filtro.getFilUsuario());
			String rutaImagen = Ruta.get(filtro.getFilRutaBase(),
					rb.getString("consultaSeguimiento.rutaImagen_escudo"));
			String archivoImagen = rb.getString("consultaSeguimiento.imagen");

			File reportFile = new File(rutaJasper + archivoJasper);

			// Parametros del Reporte
			Map map = new HashMap();
			map.put("SUBREPORT_DIR", rutaJasper);
			map.put("PATH_ICONO_SECRETARIA", rutaImagen + archivoImagen);
			map.put("LOCALIDAD", String.valueOf(filtro.getFilLocalidad()));
			map.put("INSTITUCION", new Long(filtro.getFilInstitucion()));
			map.put("VIGENCIA", String.valueOf(filtro.getFilVigencia()));

			String rutaXLS = getArchivoXLS(reportFile, map, rutaExcel,
					archivoExcel);
			String rutaPDF = getArchivoPDF(reportFile, map, rutaExcel,
					archivoPdf);
			List l = new ArrayList();
			l.add(rutaXLS);
			l.add(rutaPDF);
			Zip zip = new Zip();
			if (zip.ponerZip(rutaExcel, archivoZip, 100000, l)) {
				resultado.setGenerado(true);
				resultado.setRuta(rutaExcelRelativo + archivoZip);
				resultado.setRutaDisco(rutaExcel + archivoZip);
				resultado.setArchivo(archivoZip);
				resultado.setTipo("zip");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// resultado.setGenerado(false);
			throw new Exception("Error generando el reporte de SED");
		}
		return resultado;
	}

	/**
	 * Genera el archivo de reporte de POA segun los parametros de filtro
	 * 
	 * @param filtro
	 *            Filtro de busqueda de actividadese
	 * @return Resultado de la generacinn del reporte
	 * @throws Exception
	 */
	public ResultadoVO buscarPOASeguimiento(FiltroConsultaVO filtro)
			throws Exception {
		ResultadoVO resultado = new ResultadoVO();
		try {
			if (!consultaDAO.validarDatos(filtro)) {
				resultado.setGenerado(false);
				return resultado;
			}
			String dane = consultaDAO.getDane(filtro.getFilInstitucion());
			Calendar c = Calendar.getInstance();
			String fecha = c.get(Calendar.DAY_OF_MONTH) + "_"
					+ (c.get(Calendar.MONTH) + 1) + "_" + c.get(Calendar.YEAR)
					+ "_" + c.get(Calendar.HOUR_OF_DAY) + "_"
					+ c.get(Calendar.MINUTE);
			String rutaJasper = Ruta2.get(filtro.getFilRutaBase(),
					rb.getString("consultaSeguimiento.rutaJasper"));
			String archivoJasper = rb.getString("consultaSeguimiento.jasper");
			String rutaExcel = Ruta.get(
					filtro.getFilRutaBase(),
					rb.getString("consultaSeguimiento.path")
							+ filtro.getFilUsuario());
			String archivoExcel = "Reporte_POA_DANE_" + dane + "_VIGENCIA_"
					+ filtro.getFilVigencia() + "_FECHA_" + fecha + ".xls";
			String archivoPdf = "Reporte_POA_DANE_" + dane + "_VIGENCIA_"
					+ filtro.getFilVigencia() + "_FECHA_" + fecha + ".pdf";
			String archivoZip = "Reporte_POA_DANE_" + dane + "_VIGENCIA_"
					+ filtro.getFilVigencia() + "_FECHA_" + fecha + ".zip";
			String rutaExcelRelativo = Ruta.getRelativo(
					filtro.getFilRutaBase(),
					rb.getString("consultaSeguimiento.path")
							+ filtro.getFilUsuario());
			String rutaImagen = Ruta.get(filtro.getFilRutaBase(),
					rb.getString("consultaSeguimiento.rutaImagen_escudo"));
			String archivoImagen = rb.getString("consultaSeguimiento.imagen");

			File reportFile = new File(rutaJasper + archivoJasper);

			// Parametros del Reporte
			Map map = new HashMap();
			map.put("SUBREPORT_DIR", rutaJasper);
			map.put("PATH_ICONO_SECRETARIA", rutaImagen + archivoImagen);
			map.put("INSTITUCION", new Long(filtro.getFilInstitucion()));
			map.put("VIGENCIA", String.valueOf(filtro.getFilVigencia()));

			String rutaXLS = getArchivoXLS(reportFile, map, rutaExcel,
					archivoExcel);
			String rutaPDF = getArchivoPDF(reportFile, map, rutaExcel,
					archivoPdf);
			List l = new ArrayList();
			l.add(rutaXLS);
			l.add(rutaPDF);
			Zip zip = new Zip();
			if (zip.ponerZip(rutaExcel, archivoZip, 100000, l)) {
				resultado.setGenerado(true);
				resultado.setRuta(rutaExcelRelativo + archivoZip);
				resultado.setRutaDisco(rutaExcel + archivoZip);
				resultado.setArchivo(archivoZip);
				resultado.setTipo("zip");
			}
		} catch (Exception e) {
			// resultado.setGenerado(false);
			throw new Exception("Error generando el reporte");
		}
		return resultado;
	}
}
