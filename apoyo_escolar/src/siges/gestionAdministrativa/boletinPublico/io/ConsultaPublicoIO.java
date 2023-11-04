package siges.gestionAdministrativa.boletinPublico.io;

//	VERSION		FECHA		AUTOR			DESCRIPCION
//		1.0		04/07/2020	JORGE CAMACHO	Se modificó el contenido del archivo para hacer más comprensible el debug


import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import siges.boletines.dao.ReporteLogrosDAO;
import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import siges.dao.Ruta;
import siges.dao.Ruta2;
import siges.gestionAdministrativa.boletinPublico.dao.BoletinPublicoDAO;
import siges.gestionAdministrativa.boletinPublico.vo.DatosBoletinVO;
import siges.gestionAdministrativa.boletinPublico.vo.ParamsVO;
import siges.gestionAdministrativa.boletinPublico.vo.PlantillaBoletionPubVO;
import siges.gestionAdministrativa.boletinPublico.vo.ResultadoConsultaVO;
import siges.io.Zip;


/**
 * Objeto de acceso a disco para la generacinn de reportes 15/01/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class ConsultaPublicoIO {
	
	private ResourceBundle rb = null;
	private BoletinPublicoDAO boletinPublicoDAO = null;
	private SimpleDateFormat formaFecha = new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss.SSS");
	private ReporteLogrosDAO reporteDAO;
	private Cursor cursor;// objeto que maneja las sentencias sql
	/**
	 * Constructor
	 * 
	 * @param consultaDAO
	 *            Objet de acceso a datos
	 */
	public ConsultaPublicoIO(BoletinPublicoDAO boletinDAO) {
		cursor = new Cursor();
		reporteDAO = new ReporteLogrosDAO(cursor);
		this.boletinPublicoDAO = boletinDAO;
		rb = ResourceBundle.getBundle("siges.gestionAdministrativa.boletinPublico.bundle.boletinPublico");
	}
	
	
	
	
	
	/**
	 * Genera el archivo de reporte de POA segun los parametros de filtro
	 * 
	 * @param filtro
	 *            Filtro de busqueda de actividadese
	 * @return Resultado de la generacinn del reporte
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResultadoConsultaVO buscarPlantillaBoletion(PlantillaBoletionPubVO plantillaBoletionVO, String path2) throws Exception {
		
		ResultadoConsultaVO resultado = new ResultadoConsultaVO();
		
		try {
			
			File escudo;
			String dane = "Boletin";
			Calendar c = Calendar.getInstance();
			String fecha = c.get(Calendar.DAY_OF_MONTH) + "_" + (c.get(Calendar.MONTH) + 1) + "_" + c.get(Calendar.YEAR) + "_" + c.get(Calendar.HOUR_OF_DAY) + "_" + c.get(Calendar.MINUTE);
			String rutaJasper = Ruta2.get(plantillaBoletionVO.getFilRutaBase(),	rb.getString("consulta.rutaJasper"));
			String archivoJasper = rb.getString("consulta.jasper");
			String rutaExcel = Ruta.get(plantillaBoletionVO.getFilRutaBase(), rb.getString("consulta.path") + plantillaBoletionVO.getFilUsuario());
			String archivoPdf = dane + "_VIGENCIA_"	+ plantillaBoletionVO.getPlabolvigencia() + "_FECHA_" + fecha + ".pdf";
			String archivoZip = dane + "_VIGENCIA_" + plantillaBoletionVO.getPlabolvigencia() + "_FECHA_" + fecha + ".zip";
			String rutaExcelRelativo = Ruta.getRelativo(plantillaBoletionVO.getFilRutaBase(), rb.getString("consulta.path")	+ plantillaBoletionVO.getFilUsuario());
			String rutaImagen = Ruta.get(plantillaBoletionVO.getFilRutaBase(), rb.getString("consulta.rutaImagen"));
			String archivoImagen = Ruta2.get(rutaImagen, "") + rb.getString("imagen");

			System.out.println("Archivo jasper a utilizar: " + rutaJasper + archivoJasper);
			File reportFile = new File(rutaJasper + archivoJasper);

			Map map = new HashMap();
			map.put("SUBREPORT_DIR", rutaJasper);
			map.put("PATH_ICONO_SECRETARIA", archivoImagen);

			// INSTITUCION
			map.put("INSTITUCION", plantillaBoletionVO.getPlabolinstBNomb());
			map.put("SEDE", plantillaBoletionVO.getPlabolsedeNomb());
			map.put("JORNADA", plantillaBoletionVO.getPlaboljorndNomb());
			map.put("GRADO_GRUPO", "0" + plantillaBoletionVO.getPlabolgrado() + "" + plantillaBoletionVO.getPlabolgrupoNomb());
			map.put("PERIODO", new Integer((int) plantillaBoletionVO.getPlabolperido()));
			map.put("VIGENCIA",	new Integer((int) plantillaBoletionVO.getPlabolvigencia()));
			map.put("RESOLUCION", "" + plantillaBoletionVO.getPlabolinstResolucion());
			map.put("DOCUMENTO_ESTUDIANTE",	"" + plantillaBoletionVO.getPlabolnumdoc());
			map.put("NOMBRE_ESTUDIANTE", "" + plantillaBoletionVO.getPlabolnomb());
			map.put("NOMBRE_DIRECTOR", "" + plantillaBoletionVO.getPlabolcoordNom());
			map.put("ESCUDO_SED", "" + archivoImagen);
			map.put("NUM_PERIODOS", new Integer(plantillaBoletionVO.getPlabolperidoNum()));
			map.put("SUBTITULO", "LOGROS Y DESCRIPTORES");
			map.put("NOMBRE_PER_FINAL","" + plantillaBoletionVO.getPlabolperidoNomb());
			map.put("CONVMEN", "" + plantillaBoletionVO.getPlabolconvmen());
			map.put("CONVINST", "" + plantillaBoletionVO.getPlabolconinst());

			escudo = new File(path2 + "e" + plantillaBoletionVO.getPlabolinstDane().trim() + ".gif");
			if (escudo.exists()) {
				map.put("ESCUDO_COLEGIO", path2 + "e" + plantillaBoletionVO.getPlabolinstDane().trim() + ".gif");
				map.put("ESCUDO_COLEGIO_EXISTE", new Integer(1));
			} else {
				escudo = new File(path2 + "e" + plantillaBoletionVO.getPlabolinstDane().trim() + ".GIF");
				if (escudo.exists()) {
					map.put("ESCUDO_COLEGIO", path2 + "e" + plantillaBoletionVO.getPlabolinstDane().trim() + ".GIF");
					map.put("ESCUDO_COLEGIO_EXISTE", new Integer(1));
				} else {
					map.put("ESCUDO_COLEGIO_EXISTE", new Integer(0));
				}

			}

			String rutaPDF = getArchivoPDF(reportFile, map, rutaExcel, archivoPdf);
			List l = new ArrayList();
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
	private String getArchivoPDF(File reportFile, Map parameterscopy, String rutaExcel, String archivo) throws Exception {

		Connection con = null;
		String archivoCompleto = null;
		
		try {
			
			con = boletinPublicoDAO.getConnection();

			byte[] bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parameterscopy, con);
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
	
	
	
	
	
	/**
	 * @function:
	 * @param dbt
	 * @param parameterscopy
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public ResultadoConsultaVO generarBoletin(DatosBoletinVO bdt, PlantillaBoletionPubVO plantillaBoletionVO, String pathImagen,
			String path_Imagen_sed, String archivosalida, String rutaExcelRelativo, String path_jasper) throws Exception {

		ResultadoConsultaVO resultado = new ResultadoConsultaVO();
		String archivoEscudo = reporteDAO.getPathEscudo(new Long(bdt.getDABOLINST()));
		Map parameters = new HashMap();

		File escudo = null;
		File reportFileAsig = null;
		File reportFileADim = null;

		String rutaPDF = "";
		
		try {

			String archivoImagen = Ruta.get(pathImagen, "")	+ rb.getString("imagen");

			parameters.put("usuario", bdt.getDABOLUSUARIO());

			escudo = new File(archivoEscudo);			
			
			if (escudo.exists()) {				
				parameters.put("ESCUDO_COLEGIO", archivoEscudo);
				parameters.put("ESCUDO_COLEGIO_EXISTE", new Integer(1));
			} else 
				parameters.put("ESCUDO_COLEGIO_EXISTE", new Integer(0));

			parameters.put("ESCUDO_SED",path_Imagen_sed + rb.getString("imagen"));
			parameters.put("PERIODO", new Integer((int) bdt.getDABOLPERIODO()));
			parameters.put("VIGENCIA", new Integer(bdt.getDABOLVIGENCIA()));
			parameters.put("MOSTRAR_DESCRIPTOR",new Integer(bdt.getDABOLDESCRIPTORES()));
			parameters.put("MOSTRAR_LOGROS", new Integer(bdt.getDABOLLOGROS()));
			parameters.put("MOSTRAR_LOGROS_P",new Integer(bdt.getDABOLTOTLOGROS()));
			parameters.put("MOSTRAR_AREAS", new Integer(bdt.getDABOLAREA()));
			parameters.put("MOSTRAR_ASIG", new Integer(bdt.getDABOLASIG()));
			parameters.put("CONSECBOL", new Long(bdt.getDABOLCONSEC()));
			parameters.put("NOMBRE_PER_FINAL", bdt.getDABOLNOMPERDEF());
			parameters.put("NUM_PERIODOS", new Integer(bdt.getDABOLNUMPER()));
			parameters.put("SUBTITULO", "LOGROS Y DECRIPTORES");
			parameters.put("RESOLUCION", bdt.getDABOLRESOLUCION());
			parameters.put("INSTITUCION", bdt.getDABOLINSNOMBRE());
			parameters.put("SEDE", bdt.getDABOLSEDNOMBRE());
			parameters.put("JORNADA", bdt.getDABOLJORNOMBRE());
			parameters.put("CONVINST", bdt.getDABOLCONVINST());
			parameters.put("CONVMEN", bdt.getDABOLCONVMEN());
			parameters.put("INSTITUCIONCOD", new Long(bdt.getDABOLINST()));
			
			// 07/06/2020
			parameters.put("METODOLOGIA", bdt.getDABOLMETODOLOGIA());

			if (bdt.getDABOLTIPOBOLETIN() == ParamsVO.BOLETIN_ASIG) {
				reportFileAsig = new File(path_jasper + rb.getString("rep"));
				System.out.println("Archivo jasper a utilizar: " + reportFileAsig);
				rutaPDF = getArchivoPDF(reportFileAsig, parameters,	archivosalida, bdt.getDABOLNOMBREPDF());
			} else if (bdt.getDABOLTIPOBOLETIN() == ParamsVO.BOLETIN_DIM) {
				reportFileADim = new File(path_jasper + rb.getString("repre"));
				System.out.println("Archivo jasper a utilizar: " + reportFileADim);
				rutaPDF = getArchivoPDF(reportFileADim, parameters, archivosalida, bdt.getDABOLNOMBREPDF());
			}

			List l = new ArrayList();
			l.add(rutaPDF);
			
			Zip zip = new Zip();

			if (zip.ponerZip(archivosalida, bdt.getDABOLNOMBREZIP(), 100000, l)) {
				
				resultado.setGenerado(true);
				resultado.setRuta(rutaExcelRelativo + bdt.getDABOLNOMBREZIP());
				resultado.setRutaDisco(archivosalida + bdt.getDABOLNOMBREZIP());
				resultado.setArchivo(bdt.getDABOLNOMBREZIP());
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

}
