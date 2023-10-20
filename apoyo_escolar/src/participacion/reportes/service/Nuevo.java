/**
 * 
 */
package participacion.reportes.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.sql.Connection;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import participacion.reportes.dao.ReportesDAO;
import participacion.reportes.vo.ItemsCaracterizacionVO;
import participacion.reportes.vo.FiltroItemsVO;
import participacion.reportes.vo.ParamsVO;
import participacion.reportes.vo.ResultadoReportesVO;
import participacion.common.exception.IntegrityException;
import participacion.common.exception.ParticipacionException;
import participacion.common.exception.ValidateException;
import participacion.common.vo.ParamParticipacion;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.login.beans.Login;
import siges.dao.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.servlet.ServletOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.servlet.ServletContext;
import siges.util.Logger;

import org.apache.commons.io.CopyUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;

/**
 * 28/04/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class Nuevo extends Service {

	public String FICHA_LIDERES;
	public String FICHA_ACTAS;
	public String FICHA_ITEMS_CARACTERIZACION_FILTRO;
	public String FICHA_LIDERES_OFICIALES;
	public String FICHA_LIDERES_OFICIALES_LOCALIDAD;
	public String FICHA_LIDERES_OFICIALES_DEPARTAMENTO;
	private String contextoTotal;
	/**
	 * 
	 */
	private ReportesDAO reportesDAO = new ReportesDAO(new Cursor());
	private ResourceBundle rb;

	/*
	 * @function:
	 * 
	 * @param config
	 * 
	 * @throws ServletException (non-Javadoc)
	 * 
	 * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		FICHA_LIDERES = config.getInitParameter("FICHA_LIDERES");
		FICHA_ITEMS_CARACTERIZACION_FILTRO = config
				.getInitParameter("FICHA_ITEMS_CARACTERIZACION_FILTRO");
		FICHA_ACTAS = config.getInitParameter("FICHA_ACTAS");
		/*
		 * FICHA_LIDERES_OFICIALES=config.getInitParameter("FICHA_LIDERES_OFICIALES"
		 * ); FICHA_LIDERES_OFICIALES_LOCALIDAD=config.getInitParameter(
		 * "FICHA_LIDERES_OFICIALES_LOCALIDAD");
		 * FICHA_LIDERES_OFICIALES_DEPARTAMENTO
		 * =config.getInitParameter("FICHA_LIDERES_OFICIALES_DEPARTAMENTO");
		 */
	}

	/*
	 * Procesa la peticinn
	 * 
	 * @param request peticinn
	 * 
	 * @param response respuesta
	 * 
	 * @return Ruta de redireccinn
	 * 
	 * @throws ServletException
	 * 
	 * @throws IOException (non-Javadoc)
	 * 
	 * @see
	 * siges.common.service.Service#process(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		rb = ResourceBundle.getBundle("participacion.reportes.bundle.reportes");
		String dispatcher[] = new String[2];
		String FICHA = null;
		HttpSession session = request.getSession();
		Login usuVO = (Login) session.getAttribute("login");
		FiltroItemsVO filtroItems = (FiltroItemsVO) session
				.getAttribute("filtroItemsVO");
		ItemsCaracterizacionVO itemsCaracterizacion = (ItemsCaracterizacionVO) session
				.getAttribute("itemsCaracterizacionVO");
		int CMD = getCmd(request, session, ParamsVO.CMD_NUEVO);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		int codigoSectorInstitucion = 0;
		// System.out.println("valores: "+TIPO+"_"+CMD);
		ServletContext context = (ServletContext) request.getSession()
				.getServletContext();
		contextoTotal = context.getRealPath("/");
		int nivel = Integer.parseInt(usuVO.getNivel());

		// /***************
		/*
		 * if(nivel==ParamParticipacion.LOGIN_NIVEL_DEPARTAMENTO){
		 * System.out.println("***Nivel DEPARTAMENTO - SECTOR OFICIAL***");
		 * FICHA_LIDERES
		 * =config.getInitParameter("FICHA_LIDERES_OFICIALES_DEPARTAMENTO");
		 * 
		 * 
		 * }
		 * 
		 * if(nivel==ParamParticipacion.LOGIN_NIVEL_LOCALIDAD){
		 * System.out.println("***Nivel LOCALIDAD - SECTOR OFICIAL***");
		 * FICHA_LIDERES
		 * =config.getInitParameter("FICHA_LIDERES_OFICIALES_LOCALIDAD");
		 * 
		 * 
		 * }
		 */
		// ****************

		// if(codigoSectorInstitucion!=0){

		// if(codigoSectorInstitucion==1 || codigoSectorInstitucion==2 ||
		// codigoSectorInstitucion==3){
		// System.out.println("***SECTOR OFICIAL***");//FICHA_LIDERES_OFICIALES
		// FICHA_LIDERES=config.getInitParameter("FICHA_LIDERES");

		switch (TIPO) {
		case ParamsVO.FICHA_LIDERES:
			FICHA = FICHA_LIDERES;
			switch (CMD) {
			case ParamsVO.CMD_NUEVO:
				// System.out.println("ParamsVO.CMD_NUEVO");
				session.removeAttribute("filtroItemsVO");
				filtroItems = null;
				break;
			case ParamsVO.CMD_BUSCAR:
				// System.out.println("ParamsVO.CMD_BUSCAR PARTICIPANTES REVISION 180310");
				if (filtroItems != null) {
					if (filtroItems.getFilColegio() > 0)
						codigoSectorInstitucion = getSectorInstitucion(filtroItems
								.getFilColegio());
				}
				lideresBuscar(request, session, usuVO, filtroItems,
						codigoSectorInstitucion);
				Logger.print(usuVO.getUsuarioId(), "Busqueda de Participacion,en el modulo de Reporte-Participantes. ",
						7, 1, this.toString());
				break;
			case ParamsVO.CMD_AJAX:
				// System.out.println("ParamsVO.CMD_AJAX");
				// filtroLider(request,session,usuVO,lideres);
				lideresInit(request, session, usuVO, filtroItems,
						itemsCaracterizacion);
				break;

			case ParamsVO.CMD_GENERAR:
				// System.out.println("*** GENERAR REPORTE DE PARTICIPANTES ***");
				if (filtroItems != null) {
					if (filtroItems.getFilColegio() > 0)
						codigoSectorInstitucion = getSectorInstitucion(filtroItems
								.getFilColegio());
				}
				generarReporte(request, response, session, usuVO, filtroItems,
						codigoSectorInstitucion, ParamsVO.FICHA_LIDERES);
				FICHA = null;
				Logger.print(usuVO.getUsuarioId(), "Generar RTeporte de Participacion,en el modulo de Reporte-Participantes. ",
						7, 1, this.toString());
				break;
			}
			lideresInit(request, session, usuVO, filtroItems,
					itemsCaracterizacion);
			Logger.print(usuVO.getUsuarioId(), "Consulta de Participacion,en el modulo de Reporte-Participantes. ",
					7, 1, this.toString());
			break;

		// /REPORTE ACTAS
		case ParamsVO.FICHA_ACTAS:
			FICHA = FICHA_ACTAS;
			switch (CMD) {
			case ParamsVO.CMD_NUEVO:
				// System.out.println("ParamsVO.CMD_NUEVO ACTAS");
				session.removeAttribute("filtroItemsVO");
				filtroItems = null;
				break;
			case ParamsVO.CMD_BUSCAR:
				// System.out.println("ParamsVO.CMD_BUSCAR");
				/*
				 * if(filtroItems!=null){ if(filtroItems.getFilColegio()>0)
				 * codigoSectorInstitucion
				 * =getSectorInstitucion(request,session,usuVO); }
				 */
				actasBuscar(request, session, usuVO, filtroItems,
						codigoSectorInstitucion);
				Logger.print(usuVO.getUsuarioId(), "Busqueda de Participacion,en el modulo de Reporte-Actas. ",
						7, 1, this.toString());
				break;
			case ParamsVO.CMD_AJAX:
				// System.out.println("ParamsVO.CMD_AJAX");
				// filtroLider(request,session,usuVO,lideres);
				lideresInit(request, session, usuVO, filtroItems,
						itemsCaracterizacion);
				break;

			case ParamsVO.CMD_GENERAR:
				// System.out.println("*** GENERAR REPORTE DE ACTAS ***");
				generarReporte(request, response, session, usuVO, filtroItems,
						codigoSectorInstitucion, ParamsVO.FICHA_ACTAS);
				FICHA = null;
				Logger.print(usuVO.getUsuarioId(), "Generar RTeporte de Participacion,en el modulo de Reporte-Actas. ",
						7, 1, this.toString());
				break;
			}
			lideresInit(request, session, usuVO, filtroItems,
					itemsCaracterizacion);
			Logger.print(usuVO.getUsuarioId(), "Consulta de Participacion,en el modulo de Reporte-Actas. ",
					7, 1, this.toString());
			break;
		}

		// }
		/*
		 * else{ if(codigoSectorInstitucion==4){
		 * System.out.println("***SECTOR PRIVADO***");
		 * FICHA_LIDERES=config.getInitParameter("FICHA_LIDERES");
		 * System.out.println("***ficha: ***"+FICHA_LIDERES); switch (TIPO){
		 * case ParamsVO.FICHA_LIDERES: FICHA=FICHA_LIDERES; switch (CMD){ case
		 * ParamsVO.CMD_BUSCAR:
		 * lideresBuscar(request,session,usuVO,filtroItems,codigoSectorInstitucion
		 * ); break; case ParamsVO.CMD_GENERAR:
		 * System.out.println("*** GENERAR REPORTE DE PARTICIPANTES ***");
		 * generarReporteItemsCaracterizacion
		 * (request,response,session,usuVO,filtroItems,codigoSectorInstitucion);
		 * FICHA=null; break;
		 * 
		 * }
		 * lideresInit(request,session,usuVO,filtroItems,itemsCaracterizacion);
		 * break; } } }
		 */
		// }
		// System.out.println("se fue para : " + FICHA);
		dispatcher[0] = String.valueOf(Params.FORWARD);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	private void lideresInit(HttpServletRequest request, HttpSession session,
			Login usuVO, FiltroItemsVO filtro, ItemsCaracterizacionVO lideres)
			throws ServletException {
		int nivel = Integer.parseInt(usuVO.getNivel());
		// System.out.println("***lideresInit ***");
		try {
			if (filtro == null) {
				filtro = new FiltroItemsVO();
				if (nivel == ParamParticipacion.LOGIN_NIVEL_LOCALIDAD) {
					filtro.setFilLocalidad(Integer.parseInt(usuVO.getMunId()));
					filtro.setFilTieneLocalidad(1);
				} else if (nivel == ParamParticipacion.LOGIN_NIVEL_COLEGIO
						|| nivel == ParamParticipacion.LOGIN_NIVEL_SEDE_JORNADA) {
					filtro.setFilLocalidad(Integer.parseInt(usuVO.getMunId()));
					filtro.setFilColegio(Integer.parseInt(usuVO.getInstId()));
					filtro.setFilTieneLocalidad(1);
					filtro.setFilTieneColegio(1);
				} else {
					filtro.setFilTieneLocalidad(0);
					filtro.setFilTieneColegio(0);
				}
				session.setAttribute("filtroItemsVO", filtro);
			}
			request.setAttribute("listaLocalidadVO",
					reportesDAO.getListaLocalidad());
			request.setAttribute("listaNivelVO",
					reportesDAO.getListaNivel(nivel));
			if (filtro != null && filtro.getFilNivel() > 0) {
				request.setAttribute("listaInstanciaVO",
						reportesDAO.getListaInstancia(filtro.getFilNivel()));
				if (filtro.getFilInstancia() > 0) {
					request.setAttribute("listaRangoVO",
							reportesDAO.getListaRango(filtro.getFilInstancia()));
					request.setAttribute("listaRolVO",
							reportesDAO.getListaRol(filtro.getFilInstancia()));
				}
				// if(filtro.getFilRango()>0){
				// request.setAttribute("listaRolVO",reportesDAO.getListaRol());
				// }
			}
			if (filtro != null && filtro.getFilLocalidad() > 0) {
				request.setAttribute("listaColegioVO",
						reportesDAO.getListaColegio(filtro.getFilLocalidad()));
			}

			/*
			 * if(usuVO!=null){ Collection
			 * []c=reportesDAO.getFiltrosLider(usuVO); if(c!=null){
			 * System.out.println("***c!=null***");
			 * request.setAttribute("filtroSedeF",c[0]);
			 * request.setAttribute("filtroJornadaF",c[1]);
			 * request.setAttribute("filtroMetodologiaF",c[2]);
			 * request.setAttribute("filtroGradoF2",c[3]);
			 * request.setAttribute("filtroGrupoF",c[4]); } }
			 */

		} catch (ParticipacionException e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	private void lideresBuscar(HttpServletRequest request, HttpSession session,
			Login usuVO, FiltroItemsVO filtro, int codigoSector)
			throws ServletException {
		try {
			if (filtro != null) {
				// System.out
				// .println("***lideresBuscar PARTICIAPNTES REV  ***-instancia"
				// + filtro.getFilInstancia()
				// + "rango: "
				// + filtro.getFilRango());
				request.setAttribute("listaItemsCaracterizacionVO",
						reportesDAO.getListaLideres(filtro, codigoSector));
			}
		} catch (ParticipacionException e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	// FUNCION BUSQUEDA ACTAS
	private void actasBuscar(HttpServletRequest request, HttpSession session,
			Login usuVO, FiltroItemsVO filtro, int codigoSector)
			throws ServletException {
		try {
			if (filtro != null) {
				// System.out.println("***lideresBuscar***-instancia"
				// + filtro.getFilInstancia() + "rango: "
				// + filtro.getFilRango());
				request.setAttribute("listaActasVO",
						reportesDAO.getListaActas(filtro, codigoSector));
			}
		} catch (ParticipacionException e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	private int getSectorInstitucion(long colegio) throws ServletException {
		int codigoSector = 0;
		try {
			if (colegio > 0) {
				codigoSector = reportesDAO.getSectorInstitucion(colegio);
			}
		} catch (ParticipacionException e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
		return codigoSector;
	}

	public void generarReporte(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Login usuVO,
			FiltroItemsVO filtro, int codigoSector, int ficha)
			throws ServletException {
		try {
			if (filtro != null) {
				response.setContentType("application/zip");
				BufferedInputStream origin = null;
				ServletOutputStream ouputStream = null;
				FileInputStream fi = null;
				int BUFFER_SIZE = 100000;
				int count;
				try {
					// System.out.println("**entro a generarReporte **");
					ResultadoReportesVO resultado = null;
					resultado = buscarReporte(usuVO, filtro, codigoSector,
							ficha);

					if (resultado.isGenerado()) {
						// System.out.println("***generado == true***");
						StringBuffer contentDisposition = new StringBuffer();
						contentDisposition.append("attachment;");
						contentDisposition.append("filename=\"");
						// System.out.println("getArchivo::::"
						// + resultado.getArchivo());
						contentDisposition.append(resultado.getArchivo());
						contentDisposition.append("\"");
						response.setHeader("Content-Disposition",
								contentDisposition.toString());
						// System.out.println("RUTA DISCO "
						// + resultado.getRutaDisco());
						File f = new File(resultado.getRutaDisco());
						if (!f.exists()) {
							return;
						}
						byte[] data = new byte[BUFFER_SIZE];
						fi = new FileInputStream(f);
						origin = new BufferedInputStream(fi, BUFFER_SIZE);
						ouputStream = response.getOutputStream();
						while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
							// System.out.println("entro/////");
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
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public ResultadoReportesVO buscarReporte(Login usuVO, FiltroItemsVO filtro,
			int codigo, int ficha) throws Exception {
		ResultadoReportesVO resultado = new ResultadoReportesVO();
		try {
			// System.out.println("***ENTRO A GENERAR EL REPORTE***");
			Calendar c = Calendar.getInstance();
			// String archivoExcel="";
			String fecha = c.get(Calendar.DAY_OF_MONTH) + "_"
					+ (c.get(Calendar.MONTH) + 1) + "_" + c.get(Calendar.YEAR)
					+ "_" + c.get(Calendar.HOUR_OF_DAY) + "_"
					+ c.get(Calendar.MINUTE);
			String rutaJasper = Ruta2.get(contextoTotal,
					rb.getString("rutaJasper"));
			String archivoJasper = rb.getString("jasperItemsCaracterizacion");
			String rutaExcel = Ruta.get(contextoTotal, rb.getString("path")
					+ usuVO.getUsuarioId());
			String rutaExcelRelativo = Ruta.getRelativo(contextoTotal,
					rb.getString("path") + usuVO.getUsuarioId());
			String archivoXLS = "Reporte_Items_Caracterizacion_Colegio_"
					+ usuVO.getInstId() + "_FECHA_" + fecha + ".xls";
			String archivoZIP = "Reporte_Items_Caracterizacion_Colegio_"
					+ usuVO.getInstId() + "_FECHA_" + fecha + ".zip";
			if (ficha == ParamsVO.FICHA_LIDERES) {
				archivoJasper = rb.getString("jasperItemsCaracterizacion");
				archivoXLS = "Reporte_Items_Caracterizacion_Colegio_"
						+ filtro.getFilColegio() + "_FECHA_" + fecha + ".xls";
				archivoZIP = "Reporte_Items_Caracterizacion_Colegio_"
						+ filtro.getFilColegio() + "_FECHA_" + fecha + ".zip";
			}
			if (ficha == ParamsVO.FICHA_ACTAS) {
				archivoJasper = rb.getString("jasperListadoActas");
				archivoXLS = "Reporte_Actas_Caracterizacion_Colegio_"
						+ filtro.getFilColegio() + "_FECHA_" + fecha + ".xls";
				archivoZIP = "Reporte_Actas_Caracterizacion_Colegio_"
						+ filtro.getFilColegio() + "_FECHA_" + fecha + ".zip";
			}

			String rutaImagen_logos = Ruta.get(contextoTotal,
					rb.getString("rutaImagen_logos"));
			String rutaImagen_escudos = Ruta.get(contextoTotal,
					rb.getString("rutaImagen_escudos"));
			String archivoImagen = rb.getString("imagen");
			String archivoImagen2 = rb.getString("imagen2");
			File reportFile = new File(rutaJasper + archivoJasper);

			// Parametros del Reporte
			Map map = new HashMap();
			map.put("SUBREPORT_DIR", rutaJasper);
			map.put("PATH_ICONO_SECRETARIA", rutaImagen_escudos + archivoImagen);
			map.put("PATH_ICONO_BOGOTA", rutaImagen_logos + archivoImagen2);
			map.put("USUARIO", String.valueOf(usuVO.getUsuarioId()));
			map.put("INSTITUCION", new Long(filtro.getFilColegio()));
			map.put("COD_COLEGIO", new Long(filtro.getFilLocalidad()));
			if (ficha == ParamsVO.FICHA_LIDERES) {
				map.put("CODIGO_INSTANCIA",
						new Integer(filtro.getFilInstancia()));
				map.put("CODIGO_RANGO", new Integer(filtro.getFilRango()));
				map.put("CODIGO_SECTOR_INSTITUCION", new Integer(codigo));
				map.put("COD_NIVEL", new Integer(filtro.getFilNivel()));
				map.put("LOCALIDAD", new Integer(filtro.getFilLocalidad()));
			}

			if (ficha == ParamsVO.FICHA_ACTAS) {
				map.put("COD_INSTANCIA", new Integer(filtro.getFilInstancia()));
				map.put("COD_RANGO", new Integer(filtro.getFilRango()));
				map.put("COD_NIVEL", new Integer(filtro.getFilNivel()));
				map.put("LOCALIDAD", new Integer(filtro.getFilLocalidad()));
			}

			String rutaXLS = getArchivoXLS(reportFile, map, rutaExcel,
					archivoXLS);
			List l = new ArrayList();
			l.add(rutaXLS);
			Zip zip = new Zip();
			if (zip.ponerZip(rutaExcel, archivoZIP, 100000, l)) {
				resultado.setGenerado(true);
				resultado.setRuta(rutaExcelRelativo + archivoZIP);
				resultado.setRutaDisco(rutaExcel + archivoZIP);
				resultado.setArchivo(archivoZIP);
				resultado.setTipo("zip");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// resultado.setGenerado(false);
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
		try {
			con = reportesDAO.getConnection();
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
