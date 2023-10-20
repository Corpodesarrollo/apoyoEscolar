/**
 * 
 */
package siges.gestionAdministrativa.boletin.io;

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
import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import siges.dao.OperacionesGenerales;
import siges.dao.Ruta;
import siges.dao.Ruta2;
import siges.gestionAdministrativa.boletin.dao.BoletinDAO;
import siges.gestionAdministrativa.boletin.vo.PlantillaBoletionVO;
import siges.gestionAdministrativa.boletin.vo.ResultadoConsultaVO;
import siges.io.Zip;

/**
 * Objeto de acceso a disco para la generacinn de reportes
 * 15/01/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class BoletinIO {
	private BoletinDAO boletinDAO = null;
    private ResourceBundle rb = null;
    private SimpleDateFormat formaFecha = new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss.SSS");
	/**
	 * Constructor
	 * 
	 * @param consultaDAO Objet de acceso a datos
	 */
	public BoletinIO(BoletinDAO boletinDAO) {
		this.boletinDAO = boletinDAO;
		rb = ResourceBundle.getBundle("siges.gestionAdministrativa.boletin.bundle.boletin");
	}

	/**
	 * Genera el archivo de reporte de POA segun los parametros de filtro
	 * @param filtro Filtro de busqueda de actividadese
	 * @return Resultado de la generacinn del reporte
	 * @throws Exception
	 */
	public ResultadoConsultaVO buscarPlantillaBoletion(PlantillaBoletionVO plantillaBoletionVO, String path_escudo) throws Exception {
		System.out.println(formaFecha.format(new Date())+  "  buscarPlantillaBoletion");
		ResultadoConsultaVO resultado = new ResultadoConsultaVO();
		try{
			File escudo;
			String dane="Boletin";
			Calendar c=Calendar.getInstance();
			String fecha=c.get(Calendar.DAY_OF_MONTH)+"_"+(c.get(Calendar.MONTH)+1)+"_"+c.get(Calendar.YEAR)+"_"+c.get(Calendar.HOUR_OF_DAY)+"_"+c.get(Calendar.MINUTE);
			String rutaJasper = Ruta2.get(plantillaBoletionVO.getFilRutaBase(), rb.getString("consulta.rutaJasper"));
			String archivoJasper = rb.getString("consulta.jasper");
			
			String rutaExcel= Ruta.get(plantillaBoletionVO.getFilRutaBase(), rb.getString("consulta.path")+plantillaBoletionVO.getFilUsuario());
			String archivoPdf = dane + "_VIGENCIA_"+plantillaBoletionVO.getPlabolvigencia()+"_DOC_"+plantillaBoletionVO.getPlabolnumdoc2()+"_FECHA_"+fecha+".pdf";
			String archivoZip = dane + "_VIGENCIA_"+plantillaBoletionVO.getPlabolvigencia()+"_DOC_"+plantillaBoletionVO.getPlabolnumdoc2()+"_FECHA_"+fecha+".zip";
			String rutaExcelRelativo= Ruta.getRelativo(plantillaBoletionVO.getFilRutaBase(), rb.getString("consulta.path")+plantillaBoletionVO.getFilUsuario());
			String archivoImagen  =rb.getString("consulta.imagen");

			File reportFile= new File(rutaJasper + archivoJasper);

		

		  		
		  		
		  		
			
			Map map=new HashMap();
			map.put("SUBREPORT_DIR", rutaJasper); 
			map.put("PATH_ICONO_SECRETARIA", path_escudo + archivoImagen);
			
			//INSTITUCION(
			map.put("INSTITUCION", plantillaBoletionVO.getPlabolinstBNomb() );
			map.put("SEDE", plantillaBoletionVO.getPlabolsedeNomb() );
			map.put("JORNADA", plantillaBoletionVO.getPlaboljorndNomb() );
			map.put("GRADO_GRUPO", "0"+plantillaBoletionVO.getPlabolgrado() +""+plantillaBoletionVO.getPlabolgrupoNomb());
			map.put("PERIODO",new Integer( (int)plantillaBoletionVO.getPlabolperido()));
			map.put("VIGENCIA", new Integer( (int)plantillaBoletionVO.getPlabolvigencia()));
			map.put("RESOLUCION", ""+plantillaBoletionVO.getPlabolinstResolucion());
			map.put("DOCUMENTO_ESTUDIANTE", ""+plantillaBoletionVO.getPlabolnumdoc2());
			map.put("NOMBRE_ESTUDIANTE", ""+plantillaBoletionVO.getPlabolnomb());
			map.put("NOMBRE_DIRECTOR", ""+plantillaBoletionVO.getPlabolcoordNom());
			map.put("ESCUDO_SED", ""+path_escudo + archivoImagen);
			map.put("NUM_PERIODOS", new Integer( plantillaBoletionVO.getPlabolperidoNum()));
			map.put("SUBTITULO", "LOGROS Y DESCRIPTORES");
			map.put("NOMBRE_PER_FINAL", ""+plantillaBoletionVO.getPlabolperidoNomb());
		    map.put("CONVMEN", ""+plantillaBoletionVO.getPlabolconvmen());
		    map.put("CONVINST", ""+plantillaBoletionVO.getPlabolconinst());
			
		    escudo=new File(path_escudo+"e"+plantillaBoletionVO.getPlabolinstDane().trim()+".gif");
	  	 
	  		if (escudo.exists()){
	  			System.out.println("EXISTE");
	  			map.put("ESCUDO_COLEGIO",path_escudo+"e"+plantillaBoletionVO.getPlabolinstDane().trim()+".gif");
	  			map.put("ESCUDO_COLEGIO_EXISTE",new Integer(1));
	  		}else{
	  			escudo=new File(path_escudo+"e"+plantillaBoletionVO.getPlabolinstDane().trim()+".GIF");
	  			if (escudo.exists()){
	  				System.out.println("EXISTE EN MAYUSCULA");
	  				map.put("ESCUDO_COLEGIO",path_escudo+"e"+plantillaBoletionVO.getPlabolinstDane().trim()+".GIF");
	  				map.put("ESCUDO_COLEGIO_EXISTE",new Integer(1));
	  			}
	  			else{
	  				map.put("ESCUDO_COLEGIO_EXISTE",new Integer(0));
	  			}
	  			//parameters.put("ESCUDO_COLEGIO_EXISTE",new Integer(0));
	  		}
	  		
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
			//resultado.setGenerado(false);
			System.out.println("Error" + e.getMessage()  );
			e.printStackTrace();
			throw new Exception("Error generando el reporte");
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
			con = boletinDAO.getConnection();
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
