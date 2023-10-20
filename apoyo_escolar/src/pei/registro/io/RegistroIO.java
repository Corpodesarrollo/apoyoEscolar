package pei.registro.io;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import pei.registro.dao.RegistroDAO;
import pei.registro.vo.IdentificacionVO;
import pei.registro.vo.ResultadoConsultaVO;

import siges.dao.OperacionesGenerales;
import siges.dao.Ruta;
import siges.dao.Ruta2;
import siges.io.Zip;
import siges.login.beans.Login;

public class RegistroIO {
	ResourceBundle rb = ResourceBundle.getBundle("pei.registro.bundle.IO");
	RegistroDAO registroDAO = null;

	public RegistroIO() {
	}

	public RegistroIO(RegistroDAO boletinDAO) {
		this.registroDAO = boletinDAO;
	}

	public String subirDocumento(FileItem item, String path, String colegio,
			int codigo) throws Exception {
		String pathSubir = Ruta.get(path, rb.getString("documento.pathSubir")
				+ "." + colegio);
		String pathRelativo = Ruta.getRelativo(path,
				rb.getString("documento.pathSubir") + "." + colegio);
		String fileName = null;
		try {
			// poner el archivo en disco
			fileName = item.getName();
			java.io.File f = new java.io.File(pathSubir);
			if (!f.exists()) {
				FileUtils.forceMkdir(f);
			}
			fileName = fileName.substring(fileName.lastIndexOf('/') + 1,
					fileName.length());
			fileName = fileName.substring(fileName.lastIndexOf('\\') + 1,
					fileName.length());
			fileName = codigo + "_" + fileName.replace(' ', '_');
			f = new java.io.File(pathSubir + fileName);
			item.write(f);
		} catch (Exception e) {
			System.out.println("IO" + ": error en subida de arhivos");
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return pathRelativo + fileName;
	}

	public static void borrarDocumento(String path, String archivo)
			throws Exception {
		try {
			String ruta = archivo.substring(0, archivo.lastIndexOf("/"))
					.replace('/', '.');
			String file = archivo.substring(archivo.lastIndexOf("/") + 1,
					archivo.length());
			java.io.File f = new java.io.File(Ruta.get(path, ruta) + file);
			if (f.exists()) {
				FileUtils.forceDelete(f);
			}
		} catch (Exception e) {
			System.out.println("IO" + ": error en borrando documento:" + path);
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * @param rutaBase
	 * @param rutaReal
	 * @param login
	 * @return
	 * @throws Exception
	 */
	public ResultadoConsultaVO generarReportePEI(
			IdentificacionVO identificacionVO, String rutaBase,
			String rutaReal, Login login) throws Exception {
		ResultadoConsultaVO resultado = new ResultadoConsultaVO();
		try {
			File escudo;
			String nombre = "Reporte_PEI_Inst_"
					+ identificacionVO.getIdenInstitucion();

			Calendar c = Calendar.getInstance();
			String fecha = c.get(Calendar.DAY_OF_MONTH) + "_"
					+ (c.get(Calendar.MONTH) + 1) + "_" + c.get(Calendar.YEAR)
					+ "_" + c.get(Calendar.HOUR_OF_DAY) + "_"
					+ c.get(Calendar.MINUTE);

			String rutaJasper = Ruta2.get(rutaBase,
					rb.getString("pei.rutaJasper"));
			String archivoJasper = rb.getString("reporte_pei.jasper");

			String rutaExcel = Ruta.get(rutaBase, rb.getString("pei.path")
					+ login.getUsuarioId());
			String archivoPdf = nombre + "_FECHA_" + fecha + ".pdf";
			String archivoZip = nombre + "_FECHA_" + fecha + ".zip";
			String rutaExcelRelativo = Ruta.getRelativo(rutaBase,
					rb.getString("pei.path") + login.getUsuarioId());

			File reportFile = new File(rutaJasper + archivoJasper);

			Map map = new HashMap();
			map.put("SUBREPORT_DIR", rutaJasper);

			// INSTITUCION(
			map.put("INST", new Long(identificacionVO.getIdenInstitucion()));

			// System.out.println("Borrar este directorio " + rutaExcel);
			File dir = new File(rutaExcel);
			if (dir.exists()) {
				String[] ficheros = dir.list();
				for (int x = 0; x < ficheros.length; x++) {
					File f = new File(rutaExcel + ficheros[x]);
					// if (f.delete()) {
					// System.out.println("DIRECTORIO BORRADO SIN PROBLEMA");
					// } else {
					// System.out.println("DIRECTORIO NO SE PUEDO BORRADO");
					// }
				}
			}

			// System.out.println("reportFile " + reportFile);
			// System.out.println("rutaExcel " + rutaExcel);
			String rutaPDF = getArchivoPDF(reportFile, map, rutaExcel,
					archivoPdf);
			List l = new ArrayList();
			// l.add(rutaXLS);
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
			System.out.println("Error" + e.getMessage());
			e.printStackTrace();
			throw new Exception("Error generando el reporte");
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
	public String getArchivoPDF(File reportFile, Map parameterscopy,
			String rutaExcel, String archivo) throws Exception {
		Connection con = null;
		String archivoCompleto = null;
		try {
			con = registroDAO.getConnection();
			byte[] bytes = JasperRunManager.runReportToPdf(
					reportFile.getPath(), parameterscopy, con);
			String xlsFileName = archivo;
			String xlsFilesSource = rutaExcel;
			archivoCompleto = xlsFilesSource + xlsFileName;
			File f = new File(xlsFilesSource);
			if (!f.exists())
				FileUtils.forceMkdir(f);
			FileOutputStream fileOut = new FileOutputStream(archivoCompleto);
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
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return archivoCompleto;
	}
}
