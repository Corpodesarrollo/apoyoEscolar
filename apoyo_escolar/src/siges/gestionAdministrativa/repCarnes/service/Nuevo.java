/**
 * 
 */
package siges.gestionAdministrativa.repCarnes.service;

import java.io.File;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;

import org.apache.commons.io.FileUtils;

import siges.common.service.Service;
import siges.common.vo.ItemVO;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import siges.dao.Ruta;
import siges.dao.Ruta2;
import siges.gestionAdministrativa.repCarnes.dao.RepCarnesDAO;
import siges.gestionAdministrativa.repCarnes.vo.FiltroRepCarnesVO;
import siges.gestionAdministrativa.repCarnes.vo.ParamsVO;
import siges.gestionAdministrativa.repCarnes.vo.ResultadoReportesVO;
import siges.io.Zip;
import siges.login.beans.Login;

/**
 * 28/04/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class Nuevo extends Service {

	public String FICHA_FILTRO_REPORTES;
	private ResourceBundle rb;
	private String contextoTotal;
	private RepCarnesDAO repCarnesDAO;
	private Login usuVO;
	private java.sql.Timestamp f2;

	/**
	 * 
	 /*
	 * 
	 * @function:
	 * @param config
	 * @throws ServletException
	 *             (non-Javadoc)
	 * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		repCarnesDAO = new RepCarnesDAO(new Cursor());
		FICHA_FILTRO_REPORTES = config
				.getInitParameter("FICHA_FILTRO_REPORTES");
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
		String dispatcher[] = new String[2];
		contextoTotal = context.getRealPath("/");
		String FICHA = null;
		rb = ResourceBundle
				.getBundle("siges.gestionAdministrativa.repCarnes.bundle.repCarnes");
		HttpSession session = request.getSession();
		usuVO = (Login) session.getAttribute("login");
		FiltroRepCarnesVO filtroVO = (FiltroRepCarnesVO) session
				.getAttribute("filtroRepCarnesVO");
		int CMD = getCmd(request, session, ParamsVO.CMD_NUEVO);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_REPORTES);

		// System.out.println("REPORTE CARNES TIPO " + TIPO +"   CMD "+ CMD);
		switch (TIPO) {
		case ParamsVO.FICHA_REPORTES:

			FICHA = FICHA_FILTRO_REPORTES;

			switch (CMD) {

			case ParamsVO.CMD_NUEVO:
				request.removeAttribute("filtroRepCarnesVO");

				break;
			case ParamsVO.CMD_GUARDAR:
				// System.out.println("ENTRO A GENERACION REPORTE...");
				generar(request, session, usuVO, filtroVO);
				break;

			}
			cargarFiltroReportes(request, session, usuVO, filtroVO);
			break;
		}
		dispatcher[0] = String.valueOf(Params.INCLUDE);
		// System.out.println("APOYO:REP COMPARATIVOS FICHA " + FICHA);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	/**
	 * @param request
	 * @throws Exception
	 */
	private void cargarFiltroReportes(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroRepCarnesVO filtroVO)
			throws ServletException {

		// System.out.println("REP CARNES:cargarFiltroReportes ");

		try {

			int nivelUsuario = Integer.parseInt(usuVO.getNivel());

			// LISTADO DE REPORTES
			List tipoCol = new ArrayList();
			ItemVO itemReporte;
			itemReporte = new ItemVO();
			itemReporte.setCodigo(ParamsVO.TIPO_COL_OFICIAL);
			itemReporte.setNombre(ParamsVO.TIPO_COL_OFICIAL_);
			tipoCol.add(itemReporte);
			itemReporte = new ItemVO();
			itemReporte.setCodigo(ParamsVO.TIPO_COL_CONCESION);
			itemReporte.setNombre(ParamsVO.TIPO_COL_CONCESION_);
			tipoCol.add(itemReporte);
			itemReporte = new ItemVO();
			itemReporte.setCodigo(ParamsVO.TIPO_COL_CONVENIO);
			itemReporte.setNombre(ParamsVO.TIPO_COL_CONVENIO_);
			tipoCol.add(itemReporte);
			request.setAttribute("listaTiposCol", tipoCol);

			/*
			 * List reportes = new ArrayList(); ItemVO itemReporte; itemReporte
			 * = new ItemVO(); itemReporte.setCodigo(ParamsVO.TIPO_REPORTE_1);
			 * itemReporte.setNombre(ParamsVO.TIPO_REPORTE_1_);
			 * reportes.add(itemReporte); itemReporte = new ItemVO();
			 * itemReporte.setCodigo(ParamsVO.TIPO_REPORTE_2);
			 * itemReporte.setNombre(ParamsVO.TIPO_REPORTE_2_);
			 * reportes.add(itemReporte); itemReporte = new ItemVO();
			 * itemReporte.setCodigo(ParamsVO.TIPO_REPORTE_3);
			 * itemReporte.setNombre(ParamsVO.TIPO_REPORTE_3_);
			 * reportes.add(itemReporte); itemReporte = new ItemVO();
			 * itemReporte.setCodigo(ParamsVO.TIPO_REPORTE_4);
			 * itemReporte.setNombre(ParamsVO.TIPO_REPORTE_4_);
			 * reportes.add(itemReporte);
			 * request.setAttribute("listaReportes",reportes);
			 */

			// LISTADO DE ORDEN REPORTE
			/*
			 * List orden_reportes = new ArrayList(); ItemVO itemOrden;
			 * itemOrden = new ItemVO();
			 * itemOrden.setCodigo(ParamsVO.TIPO_REPORTE_1_ORDEN1);
			 * itemOrden.setNombre(ParamsVO.TIPO_REPORTE_1_ORDEN1_);
			 * orden_reportes.add(itemOrden); itemOrden = new ItemVO();
			 * itemOrden.setCodigo(ParamsVO.TIPO_REPORTE_1_ORDEN2);
			 * itemOrden.setNombre(ParamsVO.TIPO_REPORTE_1_ORDEN2_);
			 * orden_reportes.add(itemOrden);
			 * request.setAttribute("listaOrden",orden_reportes);
			 */

			if (filtroVO == null) {
				filtroVO = new FiltroRepCarnesVO();
				if (nivelUsuario >= 2) {
					if (usuVO.getLocId() != null
							&& usuVO.getLocId().length() > 0)
						filtroVO.setLoc(Long.parseLong(usuVO.getLocId()));
					filtroVO.setHab_loc(0);
					if (nivelUsuario >= 4) {
						if (usuVO.getLocId() != null
								&& usuVO.getLocId().length() > 0) {
							filtroVO.setInst(Long.parseLong(usuVO.getInstId()));
							filtroVO.setNivelEval((int) usuVO.getLogNivelEval());
							filtroVO.setNumPer(usuVO.getLogNumPer());
							filtroVO.setNomPerFinal(usuVO.getLogNomPerDef());
							filtroVO.setHab_inst(0);
						}
						if (nivelUsuario >= 6) {
							if (usuVO.getSedeId() != null
									&& usuVO.getSedeId().length() > 0)
								filtroVO.setSede(Long.parseLong(usuVO
										.getSedeId()));
							if (usuVO.getJornadaId() != null
									&& usuVO.getJornadaId().length() > 0)
								filtroVO.setJornd(Long.parseLong(usuVO
										.getJornadaId()));
						}
					}
				}
			}

			request.setAttribute("listaLocalidad",
					repCarnesDAO.getLocalidades());

			if (filtroVO.getLoc() >= 0) {
				if (filtroVO.getTipoCol() < 0)
					filtroVO.setTipoCol(-99);
				request.setAttribute(
						"listaColegio",
						repCarnesDAO.getColegios(filtroVO.getLoc(),
								filtroVO.getTipoCol()));
				if (filtroVO.getInst() >= 0) {
					request.setAttribute("listaSede",
							repCarnesDAO.getSede(filtroVO.getInst()));
					request.setAttribute("listaMetodo",
							repCarnesDAO.getMetodologia(filtroVO.getInst()));
					request.setAttribute(
							"listaPeriodo",
							getListaPeriodo(usuVO.getLogNumPer(),
									usuVO.getLogNomPerDef()));
					if (filtroVO.getSede() > 0) {
						request.setAttribute("listaJord", repCarnesDAO
								.getJornada(filtroVO.getInst(),
										filtroVO.getSede()));
						if (filtroVO.getJornd() > 0)
							request.setAttribute("listaGrado",
									repCarnesDAO.getGrado(filtroVO));
						if (filtroVO.getGrado() > 0)
							request.setAttribute("listaGrupo",
									repCarnesDAO.getGrupo(filtroVO));
					}
				}
			}

			// LISTADO VIGENCIAS
			long vigenciaActual = repCarnesDAO.getVigenciaNumerico();
			List vigencia = new ArrayList();
			for (int vig = 2007; vig <= vigenciaActual; vig++) {
				ItemVO itemVig = new ItemVO();
				itemVig.setCodigo(vig);
				itemVig.setNombre(String.valueOf(vig));
				vigencia.add(itemVig);
			}
			filtroVO.setVig(vigenciaActual);
			request.setAttribute("listaVigencia", vigencia);

			// LISTADO DE ZONAS
			/*
			 * List zonas = new ArrayList(); ItemVO itemZona; itemZona = new
			 * ItemVO(); itemZona.setCodigo(ParamsVO.ZONA_TODAS);
			 * itemZona.setNombre(ParamsVO.ZONA_TODAS_); zonas.add(itemZona);
			 * itemZona = new ItemVO();
			 * itemZona.setCodigo(ParamsVO.ZONA_URBANA);
			 * itemZona.setNombre(ParamsVO.ZONA_URBANA_); zonas.add(itemZona);
			 * itemZona = new ItemVO(); itemZona.setCodigo(ParamsVO.ZONA_RURAL);
			 * itemZona.setNombre(ParamsVO.ZONA_RURAL_); zonas.add(itemZona);
			 * request.setAttribute("listaZonas",zonas);
			 * filtroVO.setNumAsig(comparativosDAO.getNumAsigParam());
			 */
			request.getSession().setAttribute("filtroRepCarnesVO", filtroVO);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public String generar(HttpServletRequest request, HttpSession session,
			Login usuVO, FiltroRepCarnesVO filtro) throws ServletException {
		try {
			ResultadoReportesVO resultado = null;
			if (filtro != null) {
				filtro.setRutaBase(context.getRealPath("/"));
				filtro.setUsuario(usuVO.getUsuarioId());
				resultado = generarReporte(filtro, usuVO.getUsuarioId());
				if (resultado.isGenerado()) {
					request.setAttribute("resultadoReportes", resultado);
					request.setAttribute(ParamsVO.SMS,
							"Reporte generado satisfactoriamente");
					return FICHA_FILTRO_REPORTES;
				} else {
					request.setAttribute(ParamsVO.SMS,
							"Plantilla no generada: No hay datos para generar el reporte");
					return FICHA_FILTRO_REPORTES;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, e.getMessage());
		}
		return FICHA_FILTRO_REPORTES;
	}

	private ResultadoReportesVO generarReporte(FiltroRepCarnesVO filtro,
			String usuario) {
		ResultadoReportesVO resultado = new ResultadoReportesVO();
		try {
			if (filtro == null) {
				resultado.setGenerado(false);
				return resultado;
			}
			Calendar c = Calendar.getInstance();
			String fecha = c.get(Calendar.DAY_OF_MONTH) + "_"
					+ (c.get(Calendar.MONTH) + 1) + "_" + c.get(Calendar.YEAR)
					+ "_" + c.get(Calendar.HOUR_OF_DAY) + "_"
					+ c.get(Calendar.MINUTE);
			if (repCarnesDAO.llenarTabla(filtro, usuario, fecha)) {
				String rutaJasper = Ruta2.get(filtro.getRutaBase(),
						rb.getString("repCarnes_ruta_jaspers"));
				String archivoJasper = "";
				String rutaExcel = Ruta.get(filtro.getRutaBase(),
						rb.getString("repCarnes.path") + usuario);
				String archivoExcel = "";
				String archivoZip = "Rep_Consolidado_Estudiantes_SIN_Foto_FECHA_"
						+ fecha + ".zip";
				if (filtro.getTipoReporte() == 1) {
					archivoExcel = "Rep_Consolidado_Estudiantes_SIN_Foto_LOC_"
							+ filtro.getLoc() + "_FECHA_" + fecha + ".xls";
					archivoJasper = rb.getString("repCarnes.reporte1");
					archivoZip = "Rep_Consolidado_Estudiantes_SIN_Foto_LOC_"
							+ filtro.getLoc() + "_FECHA_" + fecha + ".zip";
				} else if (filtro.getTipoReporte() == 2) {
					archivoExcel = "Rep_Consolidado_Estudiantes_CON_Foto_LOC_"
							+ filtro.getLoc() + "_FECHA_" + fecha + ".xls";
					archivoJasper = rb.getString("repCarnes.reporte2");
					archivoZip = "Rep_Consolidado_Estudiantes_CON_Foto_LOC_"
							+ filtro.getLoc() + "_FECHA_" + fecha + ".zip";
				} else if (filtro.getTipoReporte() == 3) {
					archivoExcel = "Rep_Listado_Estudiantes_SIN_Foto_LOC_"
							+ filtro.getLoc() + "_FECHA_" + fecha + ".xls";
					archivoJasper = rb.getString("repCarnes.reporte3");
					archivoZip = "Rep_Listado_Estudiantes_SIN_Foto_LOC_"
							+ filtro.getLoc() + "_FECHA_" + fecha + ".zip";
				} else if (filtro.getTipoReporte() == 4) {
					archivoExcel = "Rep_Listado_Estudiantes_CON_Foto_LOC_"
							+ filtro.getLoc() + "_FECHA_" + fecha + ".xls";
					archivoJasper = rb.getString("repCarnes.reporte3");
					archivoZip = "Rep_Listado_Estudiantes_CON_Foto_LOC_"
							+ filtro.getLoc() + "_FECHA_" + fecha + ".zip";
				}

				// String archivoPdf =
				// "Reporte_POA_DANE_"+dane+"_VIGENCIA_"+filtro.getFilVigencia()+"_FECHA_"+fecha+".pdf";

				String rutaExcelRelativo = Ruta.getRelativo(
						filtro.getRutaBase(), rb.getString("repCarnes.path")
								+ usuario);
				String rutaImagen = Ruta2.get(filtro.getRutaBase(),
						rb.getString("repCarnes_imgs"));
				String archivoImagen = rb.getString("repCarnes.imagen");

				File reportFile = new File(rutaJasper + archivoJasper);

				// Parametros del Reporte
				Map map = new HashMap();
				// map.put("SUBREPORT_DIR", rutaJasper);
				map.put("USUARIO", usuario);
				map.put("FECHA", fecha);
				map.put("ESCUDO_SED", rutaImagen + archivoImagen);
				if (filtro.getTipoReporte() == 3
						|| filtro.getTipoReporte() == 4) {
					map.put("LOCALIDAD", new Long(filtro.getLoc()));
					map.put("INST", new Long(filtro.getInst()));
					map.put("SEDE", new Long(filtro.getSede()));
					map.put("JORNADA", new Long(filtro.getJornd()));
					map.put("GRADO", new Long(filtro.getGrado()));
					map.put("GRUPO", new Long(filtro.getGrupo()));
					map.put("TIPO_COL", new Integer((int) filtro.getTipoCol()));
					if (filtro.getTipoReporte() == 3) {
						map.put("ESTFOTO", new Integer(0));
					} else if (filtro.getTipoReporte() == 4) {
						map.put("ESTFOTO", new Integer(1));
					}
				}
				String rutaXLS = getArchivoXLS(reportFile, map, rutaExcel,
						archivoExcel);
				List l = new ArrayList();
				l.add(rutaXLS);
				Zip zip = new Zip();
				if (zip.ponerZip(rutaExcel, archivoZip, 100000, l)) {
					resultado.setGenerado(true);
					resultado.setRuta(rutaExcelRelativo + archivoZip);
					resultado.setRutaDisco(rutaExcel + archivoZip);
					resultado.setArchivo(archivoZip);
					resultado.setTipo("zip");
				}
			} else {

			}
		} catch (Exception e) {
			resultado.setGenerado(false);
			// return null;
			e.printStackTrace();
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
			con = repCarnesDAO.getConnection();
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

	public List getListaPeriodo(long numPer, String nomPerDef) {
		List l = new ArrayList();
		ItemVO item = null;
		// System.out.println("ENTRO A CARGAR PERIODOS ** FILTRO EVALUACION EDIT"
		// + numPer);
		for (int i = 1; i <= numPer; i++) {
			item = new ItemVO(i, "" + i);
			l.add(item);
		}
		item = new ItemVO(7, nomPerDef);
		l.add(item);
		return l;
	}
}
