package siges.reporteLider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.Ruta;
import siges.dao.Ruta2;
import siges.dao.Util;
import siges.io.Zip;
import siges.login.beans.Login;
import siges.reporteLider.beans.FiltroBeanReporteConsolidadoGobierno;
import siges.reporteLider.beans.FiltroBeanReporteConsolidadoLider;
import siges.reporteLider.beans.FiltroBeanReporteGobierno;
import siges.reporteLider.beans.FiltroBeanReporteLider;
import siges.reporteLider.dao.reporteLiderDAO;

/**
 * Nombre: ControllerFiltroSave<BR>
 * Descripcinn: Controla la negociacion de busqueda para la vista de resultados <BR>
 * Funciones de la pngina: Busca los datos y redirige el control a la vista de
 * resultados <BR>
 * Entidades afectadas: <BR>
 * Fecha de modificacinn: <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */

public class ControllerFiltroSave extends HttpServlet {

	private Cursor cursor;// objeto que maneja las sentencias sql
	private Zip zip;
	private Login login;
	private HttpSession session;
	private String mensaje;
	private boolean err;// variable que inidica si hay o no errores en la
						// validacion de los datos del formulario
	private Integer gradocod = new Integer(java.sql.Types.INTEGER);
	private Integer entero = new Integer(java.sql.Types.INTEGER);
	private Integer cadena = new Integer(java.sql.Types.VARCHAR);
	private Integer fecha = new Integer(java.sql.Types.TIMESTAMP);
	private Integer nulo = new Integer(java.sql.Types.NULL);
	private Integer doble = new Integer(java.sql.Types.DOUBLE);
	private Integer caracter = new Integer(java.sql.Types.CHAR);
	private Integer enterolargo = new Integer(java.sql.Types.BIGINT);
	private ResourceBundle rb3;
	private Collection list;
	private Object[] o;
	private java.sql.Timestamp f2;
	private FiltroBeanReporteLider filtroBeanReporteLider;
	private FiltroBeanReporteGobierno filtroBeanReporteGobierno;
	private FiltroBeanReporteConsolidadoLider filtroBeanReporteConsolidadoLider;
	private FiltroBeanReporteConsolidadoGobierno filtroBeanReporteConsolidadoGobierno;
	private Util util;
	private final String modulo = "31";
	private final String moduloGob = "32";
	private final String moduloConLid = "33";
	private final String moduloConGob = "34";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private ServletConfig config;
	private String s;
	private String s1;
	private String archivo, archivozip;
	private String ant;
	private String er;
	private String sig;
	private String sig2;
	private String sig3;
	private String sig4;
	private String home;
	private byte[] bytes, bytes2, bytes3;
	private Map parameters;
	private File reportFile;
	private File reportFile2;
	private File reportFile3;
	private File reportFile4;
	private String path;
	private String path1;
	private String path2;
	private String requerido;
	private int requeridos;
	private String alert;
	private String contextoTotal;
	private String vigencia;
	private Dao dao;
	private String context;
	private File escudo;
	private reporteLiderDAO reporteDAO;

	/**
	 * Procesa la peticion HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/

	public String process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// System.out.println("*********////*********nENTRn A GENERAR FILTROSAVE!**********//**********");
		session = request.getSession();
		Collection list = new ArrayList();
		int posicion = 1;
		rb3 = ResourceBundle.getBundle("reporteLider");
		f2 = new java.sql.Timestamp(new java.util.Date().getTime());
		s = getServletConfig().getInitParameter("s");
		s1 = getServletConfig().getInitParameter("s1");
		sig = getServletConfig().getInitParameter("sig");// filtroLider.jsp
		sig2 = getServletConfig().getInitParameter("sig2");// filtroGobierno.jsp
		sig3 = getServletConfig().getInitParameter("sig3");// filtroConsolidadoLider.jsp
		sig4 = getServletConfig().getInitParameter("sig4");// filtroConsolidadoGobierno.jsp
		ant = getServletConfig().getInitParameter("ant");
		er = getServletContext().getInitParameter("error");
		home = getServletContext().getInitParameter("home");
		err = false;

		if (request.getParameter("reporte") == null) {
			setMensaje("**Error al mandar generar el tipo de reporte solicitado**");
			request.setAttribute("mensaje", mensaje);
			ir(1, er, request, response);
			return er;
		}
		alert = "El Reporte se esta generando en este momento. \nPulse en el hipervinculo 'Listado de Reportes' para ver si el reporte ya fue generado. \nO vaya a la opción de menu 'Reportes generados'";

		if (!asignarBeans(request)) {
			setMensaje("Error capturando datos de sesinn para el usuario");
			request.setAttribute("mensaje", mensaje);
			ir(1, er, request, response);
			return null;
		}
		try {
			cursor = new Cursor();
			util = new Util(cursor);
			dao = new Dao(cursor);
			reporteDAO = new reporteLiderDAO(cursor);
			vigencia = dao.getVigencia();

			if (vigencia == null) {
				System.out.println("*W* LA VIGENCIA ES NULA *W* ");
				return er;
			}
			requeridos = Integer.parseInt(request.getParameter("reporte"));
			System.out.println("REPORTE REQUERIDO: " + requeridos);

			ServletContext context = (ServletContext) request.getSession()
					.getServletContext();
			contextoTotal = context.getRealPath("/");
			path = Ruta2.get(context.getRealPath("/"),
					rb3.getString("ruta_jaspers"));
			path1 = Ruta2.get(context.getRealPath("/"),
					rb3.getString("ruta_img"));
			path2 = Ruta2.get(context.getRealPath("/"),
					rb3.getString("ruta_img_inst"));

			reportFile = new File(path
					+ rb3.getString("jasper_lider_estudiantil"));
			reportFile2 = new File(path
					+ rb3.getString("jasper_gobierno_escolar"));
			reportFile3 = new File(path
					+ rb3.getString("jasper_consolidado_lider"));
			reportFile4 = new File(path
					+ rb3.getString("jasper_consolidado_gobierno"));

			switch (requeridos) {
			case 1:
				if (!liderEstudiantil(request, vigencia, contextoTotal))
					return sig;
				break;
			case 2:
				if (!gobiernoEscolar(request, vigencia, contextoTotal))
					return sig2;
				break;
			case 3:
				if (!consolidadoLiderEstudiantil(request, vigencia,
						contextoTotal))
					return sig3;
				break;
			case 4:
				if (!consolidadoGobiernoEscolar(request, vigencia,
						contextoTotal))
					return sig4;
				break;
			}
			if (err) {
				request.setAttribute("mensaje", mensaje);
				return er;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (cursor != null)
					cursor.cerrar();
				if (util != null)
					util.cerrar();
			} catch (Exception e) {
			}
		}
		System.out.println("S: " + s);
		return s;
	}

	public boolean liderEstudiantil(HttpServletRequest request,
			String vigencia, String contextoTotal) throws ServletException,
			IOException {
		String colegio = null, sede = null, jornada = null, nombreReporte = null;
		parameters = new HashMap();
		String archivosalida = null;
		int zise;
		Collection list = new ArrayList();
		Zip zip = new Zip();

		try {
			// System.out.println("*********////*********nENTRn A GENERAR LIDER ESTUDIANTIL!**********//**********");
			if (!asignarBeanLiderEstudiantil(request)) {
				setMensaje("Error capturando bean del reporte");
				request.setAttribute("mensaje", mensaje);
				ir(1, er, request, response);
				return false;
			}
			filtroBeanReporteLider.setInstitucionDane(reporteDAO
					.getDaneInstitucion(new Long(login.getInstId())));
			filtroBeanReporteLider.setVigencia(new Long(vigencia));

			/* PARAMETROS PARA el JASPER DE AREAS */
			parameters.put("CODLOCAL", filtroBeanReporteLider.getLocalidad());
			parameters.put("INSTITUCION",
					filtroBeanReporteLider.getInstitucion());
			parameters.put("SEDE", filtroBeanReporteLider.getSede());
			parameters.put("JORNADA", filtroBeanReporteLider.getJornada());
			parameters.put("VIGENCIA", new Long(-999));
			parameters.put("TIPO_LIDER", filtroBeanReporteLider.getTipoLider());
			parameters.put("USUARIO", login.getUsuario());
			escudo = new File(path2 + "e"
					+ filtroBeanReporteLider.getInstitucionDane() + ".gif");
			// System.out.println("escudo: "+escudo);
			if (escudo.exists())
				parameters.put("PATH_ICONO_INSTITUCION", path2 + "e"
						+ filtroBeanReporteLider.getInstitucionDane() + ".gif");
			else
				parameters.put("PATH_ICONO_INSTITUCION",
						path1 + rb3.getString("imagen"));

			parameters.put("PATH_ICONO_SECRETARIA",
					path1 + rb3.getString("imagen"));
			parameters.put("SUBREPORT_DIR", path);

			// System.out.println("**PARAMETERS DEL JASPER**");
			// System.out.println("CODLOCAL: "+filtroBeanReporteLider.getLocalidad());
			// System.out.println("INSTITUCION: "+filtroBeanReporteLider.getInstitucion());
			// System.out.println("SEDE: "+filtroBeanReporteLider.getSede());
			// System.out.println("JORNADA: "+filtroBeanReporteLider.getJornada());
			// System.out.println("VIGENCIA: "+filtroBeanReporteLider.getVigencia());
			// System.out.println("TIPO_LIDER: "+filtroBeanReporteLider.getTipoLider());
			// System.out.println("USUARIO: "+login.getUsuario());
			// if (escudo.exists())
			// System.out.println("PATH_ICONO_INSTITUCION: "+path2+"e"+filtroBeanReporteLider.getInstitucionDane()+".gif");
			// else
			// System.out.println("PATH_ICONO_INSTITUCION: "+path1+rb3.getString("imagen"));
			//
			// System.out.println("PATH_ICONO_SECRETARIA: "+path1+rb3.getString("imagen"));
			// System.out.println("SUBREPORT_DIR: "+path);

			if (!reporteDAO.isWorking(login.getUsuarioId(), modulo)) {
				// System.out.println("******nNO HAY NINGnN REPORTE DE LIDERES ESTUDIANTILES GENERANDOSE PARA ESTE USUARIO EN ESTADO CERO!*********");
				colegio = (!filtroBeanReporteLider.getInstitucion().equals(
						"-999") ? "_Colegio_"
						+ filtroBeanReporteLider.getInstitucion() : "");
				sede = (!filtroBeanReporteLider.getSede().equals("-999") ? "_Sede_"
						+ filtroBeanReporteLider.getSede()
						: "");
				jornada = (!filtroBeanReporteLider.getJornada().equals("-999") ? "_Jornada_"
						+ filtroBeanReporteLider.getJornada()
						: "");
				vigencia = (!filtroBeanReporteLider.getVigencia()
						.equals("-999") ? "_Vigencia_"
						+ filtroBeanReporteLider.getVigencia() : "");

				nombreReporte = colegio
						+ sede
						+ jornada
						+ "_Fecha_"
						+ f2.toString().replace(' ', '_').replace(':', '-')
								.replace('.', '-');

				archivo = "Listado_Lideres_Estudiantiles_" + nombreReporte
						+ ".pdf";
				archivozip = "Listado_Lideres_Estudiantiles_" + nombreReporte
						+ ".zip";
				reporteDAO.ponerReporte(modulo, login.getUsuarioId(),
						rb3.getString("reporte.PathReporte") + archivozip + "",
						"zip", "" + archivozip, "0", "ReporteInsertarEstado");// Estado
																				// -1
				if (!reporteDAO.isEmpty(filtroBeanReporteLider)) {
					// System.out.println("Hay datos para ejecutar el jasper");
					bytes = reporteDAO.getArrayBytes(
							filtroBeanReporteLider.getInstitucion(),
							login.getUsuarioId(), archivozip, reportFile,
							parameters, modulo);
					reporteDAO.updateReporteEstado(modulo,
							login.getUsuarioId(),
							rb3.getString("reporte.PathReporte") + archivozip
									+ "", "zip", "" + archivozip,
							"ReporteActualizarListo");
					reporteDAO.ponerArchivo(bytes, archivo, contextoTotal);
					archivosalida = Ruta.get(contextoTotal,
							rb3.getString("reportes.PathReporte"));
					// System.out.println("archivosalida: "+archivosalida);
					list.add(archivosalida + archivo);// pdf
					zise = 100000;
					if (zip.ponerZip(archivosalida, archivozip, zise, list)) {
						reporteDAO.updateReporteFecha("update_reporte_general",
								modulo, archivozip, login.getUsuarioId(), "1");
						return true;
					} else {
						reporteDAO.ponerReporteMensaje("2", modulo,
								login.getUsuarioId(),
								rb3.getString("reporte.PathReporte")
										+ archivozip + "", "zip", ""
										+ archivozip,
								"ReporteActualizarBoletinPaila",
								"Ocurrin problema al hacer zip");
						reporteDAO.updateReporteFecha("update_reporte_general",
								modulo, archivozip, login.getUsuarioId(), "2");
						setMensaje("Error al hacer zip");
						request.setAttribute("mensaje", mensaje);
						return false;
					}
				} else {
					// System.out.println("El Reporte esta vacio");
					// Insertarlo en la tabla REPORTE -- ESTADO 2
					reporteDAO.ponerReporteMensaje("2", modulo,
							login.getUsuarioId(),
							rb3.getString("reporte.PathReporte") + archivozip
									+ "", "zip", "" + archivozip,
							"ReporteActualizarBoletinPaila",
							"Ocurrin problema en liderEstudiantil");
					reporteDAO.updateReporteFecha("update_reporte_general",
							modulo, archivozip, login.getUsuarioId(), "2");
					setMensaje("No hay datos para generar el reporte");
					request.setAttribute("mensaje", mensaje);
					return false;
				}
			} else {
				setMensaje("Usted ya mandn generar un reporte de Lideres estudiantiles \nPor favor espere que termine, para solicitar uno nuevo");
				request.setAttribute("mensaje", mensaje);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			reporteDAO.ponerReporteMensaje("2", modulo, login.getUsuarioId(),
					rb3.getString("reporte.PathReporte") + archivozip + "",
					"zip", "" + archivozip, "ReporteActualizarBoletin",
					"Ocurrin excepcinn en liderEstudiantil");
			return false;
		}
	}

	public boolean gobiernoEscolar(HttpServletRequest request, String vigencia,
			String contextoTotal) throws ServletException, IOException {
		String colegio = null, sede = null, jornada = null, nombreReporte = null;
		parameters = new HashMap();
		String archivosalida = null;
		int zise;
		Collection list = new ArrayList();
		Zip zip = new Zip();

		try {
			// System.out.println("*********////*********nENTRn A GENERAR GOBIERNO ESCOLAR!**********//**********");
			if (!asignarBeanGobiernoEscolar(request)) {
				setMensaje("Error capturando bean del reporte");
				request.setAttribute("mensaje", mensaje);
				ir(1, er, request, response);
				return false;
			}
			filtroBeanReporteGobierno.setInstitucionDane(reporteDAO
					.getDaneInstitucion(new Long(login.getInstId())));

			/* PARAMETROS PARA el JASPER DE AREAS */
			parameters
					.put("CODLOCAL", filtroBeanReporteGobierno.getLocalidad());
			parameters.put("INSTITUCION",
					filtroBeanReporteGobierno.getInstitucion());
			parameters.put("SEDE", filtroBeanReporteGobierno.getSede());
			parameters.put("JORNADA", filtroBeanReporteGobierno.getJornada());
			parameters.put("TIPO_CARGO",
					filtroBeanReporteGobierno.getTipoLider());
			parameters.put("USUARIO", login.getUsuario());
			escudo = new File(path2 + "e"
					+ filtroBeanReporteGobierno.getInstitucionDane() + ".gif");
			// System.out.println("escudo: "+escudo);
			// if (escudo.exists())
			// parameters.put("PATH_ICONO_INSTITUCION",path2+"e"+filtroBeanReporteGobierno.getInstitucionDane()+".gif");
			// else
			// parameters.put("PATH_ICONO_INSTITUCION",path1+rb3.getString("imagen"));

			parameters.put("PATH_ICONO_SECRETARIA",
					path1 + rb3.getString("imagen"));
			parameters.put("SUBREPORT_DIR", path);

			// System.out.println("**PARAMETERS DEL JASPER**");
			// System.out.println("CODLOCAL: "+filtroBeanReporteGobierno.getLocalidad());
			// System.out.println("INSTITUCION: "+filtroBeanReporteGobierno.getInstitucion());
			// System.out.println("SEDE: "+filtroBeanReporteGobierno.getSede());
			// System.out.println("JORNADA: "+filtroBeanReporteGobierno.getJornada());
			// System.out.println("TIPO_LIDER: "+filtroBeanReporteGobierno.getTipoLider());
			// System.out.println("USUARIO: "+login.getUsuario());
			// if (escudo.exists())
			// System.out.println("PATH_ICONO_INSTITUCION: "+path2+"e"+filtroBeanReporteGobierno.getInstitucionDane()+".gif");
			// else
			// System.out.println("PATH_ICONO_INSTITUCION: "+path1+rb3.getString("imagen"));
			//
			// System.out.println("PATH_ICONO_SECRETARIA: "+path1+rb3.getString("imagen"));
			// System.out.println("SUBREPORT_DIR: "+path);

			if (!reporteDAO.isWorking(login.getUsuarioId(), moduloGob)) {
				// System.out.println("******nNO HAY NINGnN REPORTE DE LIDERES ESTUDIANTILES GENERANDOSE PARA ESTE USUARIO EN ESTADO CERO!*********");
				colegio = (!filtroBeanReporteGobierno.getInstitucion().equals(
						"-999") ? "_Colegio_"
						+ filtroBeanReporteGobierno.getInstitucion() : "");
				sede = (!filtroBeanReporteGobierno.getSede().equals("-999") ? "_Sede_"
						+ filtroBeanReporteGobierno.getSede()
						: "");
				jornada = (!filtroBeanReporteGobierno.getJornada().equals(
						"-999") ? "_Jornada_"
						+ filtroBeanReporteGobierno.getJornada() : "");
				nombreReporte = colegio
						+ sede
						+ jornada
						+ "_Fecha_"
						+ f2.toString().replace(' ', '_').replace(':', '-')
								.replace('.', '-');

				archivo = "Listado_Gobierno_Escolar_" + nombreReporte + ".pdf";
				archivozip = "Listado_Gobierno_Escolar_" + nombreReporte
						+ ".zip";
				reporteDAO.ponerReporte(moduloGob, login.getUsuarioId(),
						rb3.getString("reporte.PathReporte") + archivozip + "",
						"zip", "" + archivozip, "0", "ReporteInsertarEstado");// Estado
																				// -1
				if (!reporteDAO.isEmptyGob(filtroBeanReporteGobierno)) {
					// System.out.println("Hay datos para ejecutar el jasper");
					bytes = reporteDAO.getArrayBytes(
							filtroBeanReporteGobierno.getInstitucion(),
							login.getUsuarioId(), archivozip, reportFile2,
							parameters, moduloGob);
					reporteDAO.updateReporteEstado(moduloGob,
							login.getUsuarioId(),
							rb3.getString("reporte.PathReporte") + archivozip
									+ "", "zip", "" + archivozip,
							"ReporteActualizarListo");
					reporteDAO.ponerArchivo(bytes, archivo, contextoTotal);
					archivosalida = Ruta.get(contextoTotal,
							rb3.getString("reportes.PathReporte"));
					// System.out.println("archivosalida: "+archivosalida);
					list.add(archivosalida + archivo);// pdf
					zise = 100000;
					if (zip.ponerZip(archivosalida, archivozip, zise, list)) {
						reporteDAO.updateReporteFecha("update_reporte_general",
								moduloGob, archivozip, login.getUsuarioId(),
								"1");
						return true;
					} else {
						reporteDAO.ponerReporteMensaje("2", moduloGob,
								login.getUsuarioId(),
								rb3.getString("reporte.PathReporte")
										+ archivozip + "", "zip", ""
										+ archivozip,
								"ReporteActualizarBoletinPaila",
								"Ocurrin problema al hacer zip");
						reporteDAO.updateReporteFecha("update_reporte_general",
								moduloGob, archivozip, login.getUsuarioId(),
								"2");
						setMensaje("Error al hacer zip");
						request.setAttribute("mensaje", mensaje);
						return false;
					}
				} else {
					// System.out.println("El Reporte esta vacio");
					// Insertarlo en la tabla REPORTE -- ESTADO 2
					reporteDAO.ponerReporteMensaje("2", moduloGob,
							login.getUsuarioId(),
							rb3.getString("reporte.PathReporte") + archivozip
									+ "", "zip", "" + archivozip,
							"ReporteActualizarBoletinPaila",
							"Ocurrin problema en gobiernoEscolar");
					reporteDAO.updateReporteFecha("update_reporte_general",
							moduloGob, archivozip, login.getUsuarioId(), "2");
					setMensaje("No hay datos para generar el reporte");
					request.setAttribute("mensaje", mensaje);
					return false;
				}
			} else {
				setMensaje("Usted ya mandn generar un reporte de Gobierno Escolar \nPor favor espere que termine, para solicitar uno nuevo");
				request.setAttribute("mensaje", mensaje);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			reporteDAO.ponerReporteMensaje("2", moduloGob,
					login.getUsuarioId(), rb3.getString("reporte.PathReporte")
							+ archivozip + "", "zip", "" + archivozip,
					"ReporteActualizarBoletin",
					"Ocurrin excepcinn en gobiernoEscolar");
			return false;
		}
	}

	public boolean consolidadoLiderEstudiantil(HttpServletRequest request,
			String vigencia, String contextoTotal) throws ServletException,
			IOException {
		String colegio = null, nombreReporte = null;
		parameters = new HashMap();
		String archivosalida = null;
		int zise;
		Collection list = new ArrayList();
		Zip zip = new Zip();

		try {
			// System.out.println("*********////*********nENTRn A GENERAR CONSOLIDADO LIDER ESTUDIANTIL!**********//**********");
			if (!asignarBeanConsolidadoLiderEstudiantil(request)) {
				setMensaje("Error capturando bean del reporte");
				request.setAttribute("mensaje", mensaje);
				ir(1, er, request, response);
				return false;
			}
			filtroBeanReporteConsolidadoLider.setInstitucionDane(reporteDAO
					.getDaneInstitucion(new Long(login.getInstId())));
			filtroBeanReporteConsolidadoLider.setVigencia(new Long(vigencia));

			/* PARAMETROS PARA el JASPER DE AREAS */
			parameters.put("CODLOCAL",
					filtroBeanReporteConsolidadoLider.getLocalidad());
			parameters.put("CODINSTITUCION",
					filtroBeanReporteConsolidadoLider.getInstitucion());
			parameters.put("VIGENCIA", new Long(-999));
			parameters.put("CARGO",
					filtroBeanReporteConsolidadoLider.getTipoLider());
			parameters.put("USUARIO", login.getUsuario());
			escudo = new File(path2 + "e"
					+ filtroBeanReporteConsolidadoLider.getInstitucionDane()
					+ ".gif");
			// System.out.println("escudo: "+escudo);
			if (escudo.exists())
				parameters.put(
						"PATH_ICONO_INSTITUCION",
						path2
								+ "e"
								+ filtroBeanReporteConsolidadoLider
										.getInstitucionDane() + ".gif");
			else
				parameters.put("PATH_ICONO_INSTITUCION",
						path1 + rb3.getString("imagen"));

			parameters.put("PATH_ICONO_SECRETARIA",
					path1 + rb3.getString("imagen"));
			parameters.put("SUBREPORT_DIR", path);

			// System.out.println("**PARAMETERS DEL JASPER**");
			// System.out.println("CODLOCAL: "+filtroBeanReporteConsolidadoLider.getLocalidad());
			// System.out.println("CODINSTITUCION: "+filtroBeanReporteConsolidadoLider.getInstitucion());
			// System.out.println("VIGENCIA: "+filtroBeanReporteConsolidadoLider.getVigencia());
			// System.out.println("CARGO: "+filtroBeanReporteConsolidadoLider.getTipoLider());
			// System.out.println("USUARIO: "+login.getUsuario());
			// if (escudo.exists())
			// System.out.println("PATH_ICONO_INSTITUCION: "+path2+"e"+filtroBeanReporteConsolidadoLider.getInstitucionDane()+".gif");
			// else
			// System.out.println("PATH_ICONO_INSTITUCION: "+path1+rb3.getString("imagen"));
			//
			// System.out.println("PATH_ICONO_SECRETARIA: "+path1+rb3.getString("imagen"));
			// System.out.println("SUBREPORT_DIR: "+path);

			if (!reporteDAO.isWorking(login.getUsuarioId(), moduloConLid)) {
				// System.out.println("******nNO HAY NINGnN REPORTE DE CONSOLIDADO DE LIDERES ESTUDIANTILES GENERANDOSE PARA ESTE USUARIO EN ESTADO CERO!*********");
				colegio = (!filtroBeanReporteConsolidadoLider.getInstitucion()
						.equals("-999") ? "Colegio_"
						+ filtroBeanReporteConsolidadoLider.getInstitucion()
						: "");
				vigencia = (!filtroBeanReporteConsolidadoLider.getVigencia()
						.equals("-999") ? "_Vigencia_"
						+ filtroBeanReporteConsolidadoLider.getVigencia() : "");

				nombreReporte = colegio
						+ "_Fecha_"
						+ f2.toString().replace(' ', '_').replace(':', '-')
								.replace('.', '-');

				archivo = "Consolidado_Lideres_Estudiantiles_" + nombreReporte
						+ ".pdf";
				archivozip = "Consolidado_Lideres_Estudiantiles_"
						+ nombreReporte + ".zip";
				reporteDAO.ponerReporte(moduloConLid, login.getUsuarioId(),
						rb3.getString("reporte.PathReporte") + archivozip + "",
						"zip", "" + archivozip, "0", "ReporteInsertarEstado");// Estado
																				// -1
				if (!reporteDAO
						.isEmptyConLid(filtroBeanReporteConsolidadoLider)) {
					// System.out.println("Hay datos para ejecutar el jasper");
					bytes = reporteDAO.getArrayBytes(
							filtroBeanReporteConsolidadoLider.getInstitucion(),
							login.getUsuarioId(), archivozip, reportFile3,
							parameters, moduloConLid);
					reporteDAO.updateReporteEstado(moduloConLid,
							login.getUsuarioId(),
							rb3.getString("reporte.PathReporte") + archivozip
									+ "", "zip", "" + archivozip,
							"ReporteActualizarListo");
					reporteDAO.ponerArchivo(bytes, archivo, contextoTotal);
					archivosalida = Ruta.get(contextoTotal,
							rb3.getString("reportes.PathReporte"));
					// System.out.println("archivosalida: "+archivosalida);
					list.add(archivosalida + archivo);// pdf
					zise = 100000;
					if (zip.ponerZip(archivosalida, archivozip, zise, list)) {
						reporteDAO.updateReporteFecha("update_reporte_general",
								moduloConLid, archivozip, login.getUsuarioId(),
								"1");
						return true;
					} else {
						reporteDAO.ponerReporteMensaje("2", moduloConLid,
								login.getUsuarioId(),
								rb3.getString("reporte.PathReporte")
										+ archivozip + "", "zip", ""
										+ archivozip,
								"ReporteActualizarBoletinPaila",
								"Ocurrin problema al hacer zip");
						reporteDAO.updateReporteFecha("update_reporte_general",
								moduloConLid, archivozip, login.getUsuarioId(),
								"2");
						setMensaje("Error al hacer zip");
						request.setAttribute("mensaje", mensaje);
						return false;
					}
				} else {
					// System.out.println("El Reporte esta vacio");
					// Insertarlo en la tabla REPORTE -- ESTADO 2
					reporteDAO.ponerReporteMensaje("2", moduloConLid,
							login.getUsuarioId(),
							rb3.getString("reporte.PathReporte") + archivozip
									+ "", "zip", "" + archivozip,
							"ReporteActualizarBoletinPaila",
							"Ocurrin problema en ConsolidadoLiderEstudiantil");
					reporteDAO
							.updateReporteFecha("update_reporte_general",
									moduloConLid, archivozip,
									login.getUsuarioId(), "2");
					setMensaje("No hay datos para generar el reporte");
					request.setAttribute("mensaje", mensaje);
					return false;
				}
			} else {
				setMensaje("Usted ya mandn generar un reporte de Consolidado Lideres estudiantiles \nPor favor espere que termine, para solicitar uno nuevo");
				request.setAttribute("mensaje", mensaje);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			reporteDAO.ponerReporteMensaje("2", moduloConLid,
					login.getUsuarioId(), rb3.getString("reporte.PathReporte")
							+ archivozip + "", "zip", "" + archivozip,
					"ReporteActualizarBoletin",
					"Ocurrin excepcinn en ConsolidadoLiderEstudiantil");
			return false;
		}
	}

	public boolean consolidadoGobiernoEscolar(HttpServletRequest request,
			String vigencia, String contextoTotal) throws ServletException,
			IOException {
		String colegio = null, nombreReporte = null;
		parameters = new HashMap();
		String archivosalida = null;
		int zise;
		Collection list = new ArrayList();
		Zip zip = new Zip();

		try {
			// System.out.println("*********////*********nENTRn A GENERAR CONSOLIDADO GOBIERNO ESCOLAR!**********//**********");
			if (!asignarBeanConsolidadoGobiernoEscolar(request)) {
				setMensaje("Error capturando bean del reporte");
				request.setAttribute("mensaje", mensaje);
				ir(1, er, request, response);
				return false;
			}
			filtroBeanReporteConsolidadoGobierno.setInstitucionDane(reporteDAO
					.getDaneInstitucion(new Long(login.getInstId())));

			/* PARAMETROS PARA el JASPER DE AREAS */
			parameters.put("CODLOCAL",
					filtroBeanReporteConsolidadoGobierno.getLocalidad());
			parameters.put("CODINSTITUCION",
					filtroBeanReporteConsolidadoGobierno.getInstitucion());
			parameters.put("CARGO",
					filtroBeanReporteConsolidadoGobierno.getTipoLider());
			parameters.put("USUARIO", login.getUsuario());
			escudo = new File(path2 + "e"
					+ filtroBeanReporteConsolidadoGobierno.getInstitucionDane()
					+ ".gif");
			// System.out.println("escudo: "+escudo);
			if (escudo.exists())
				parameters.put(
						"PATH_ICONO_INSTITUCION",
						path2
								+ "e"
								+ filtroBeanReporteConsolidadoGobierno
										.getInstitucionDane() + ".gif");
			else
				parameters.put("PATH_ICONO_INSTITUCION",
						path1 + rb3.getString("imagen"));

			parameters.put("PATH_ICONO_SECRETARIA",
					path1 + rb3.getString("imagen"));
			parameters.put("SUBREPORT_DIR", path);

			// System.out.println("**PARAMETERS DEL JASPER**");
			// System.out.println("CODLOCAL: "+filtroBeanReporteConsolidadoGobierno.getLocalidad());
			// System.out.println("CODINSTITUCION: "+filtroBeanReporteConsolidadoGobierno.getInstitucion());
			// System.out.println("CARGO: "+filtroBeanReporteConsolidadoGobierno.getTipoLider());
			// System.out.println("USUARIO: "+login.getUsuario());
			// if (escudo.exists())
			// System.out.println("PATH_ICONO_INSTITUCION: "+path2+"e"+filtroBeanReporteConsolidadoGobierno.getInstitucionDane()+".gif");
			// else
			// System.out.println("PATH_ICONO_INSTITUCION: "+path1+rb3.getString("imagen"));
			//
			// System.out.println("PATH_ICONO_SECRETARIA: "+path1+rb3.getString("imagen"));
			// System.out.println("SUBREPORT_DIR: "+path);

			if (!reporteDAO.isWorking(login.getUsuarioId(), moduloConGob)) {
				// System.out.println("******nNO HAY NINGnN REPORTE DE CONSOLIDADO DE GOBIERNO GENERANDOSE PARA ESTE USUARIO EN ESTADO CERO!*********");
				colegio = (!filtroBeanReporteConsolidadoGobierno
						.getInstitucion().equals("-999") ? "Colegio_"
						+ filtroBeanReporteConsolidadoGobierno.getInstitucion()
						: "");
				nombreReporte = colegio
						+ "_Fecha_"
						+ f2.toString().replace(' ', '_').replace(':', '-')
								.replace('.', '-');

				archivo = "Consolidado_Gobierno_Escolar_" + nombreReporte
						+ ".pdf";
				archivozip = "Consolidado_Gobierno_Escolar_" + nombreReporte
						+ ".zip";
				reporteDAO.ponerReporte(moduloConGob, login.getUsuarioId(),
						rb3.getString("reporte.PathReporte") + archivozip + "",
						"zip", "" + archivozip, "0", "ReporteInsertarEstado");// Estado
																				// -1
				if (!reporteDAO
						.isEmptyConGob(filtroBeanReporteConsolidadoGobierno)) {
					// System.out.println("Hay datos para ejecutar el jasper");
					bytes = reporteDAO.getArrayBytes(
							filtroBeanReporteConsolidadoGobierno
									.getInstitucion(), login.getUsuarioId(),
							archivozip, reportFile4, parameters, moduloConGob);
					reporteDAO.updateReporteEstado(moduloConGob,
							login.getUsuarioId(),
							rb3.getString("reporte.PathReporte") + archivozip
									+ "", "zip", "" + archivozip,
							"ReporteActualizarListo");
					reporteDAO.ponerArchivo(bytes, archivo, contextoTotal);
					archivosalida = Ruta.get(contextoTotal,
							rb3.getString("reportes.PathReporte"));
					// System.out.println("archivosalida: "+archivosalida);
					list.add(archivosalida + archivo);// pdf
					zise = 100000;
					if (zip.ponerZip(archivosalida, archivozip, zise, list)) {
						reporteDAO.updateReporteFecha("update_reporte_general",
								moduloConGob, archivozip, login.getUsuarioId(),
								"1");
						return true;
					} else {
						reporteDAO.ponerReporteMensaje("2", moduloConGob,
								login.getUsuarioId(),
								rb3.getString("reporte.PathReporte")
										+ archivozip + "", "zip", ""
										+ archivozip,
								"ReporteActualizarBoletinPaila",
								"Ocurrin problema al hacer zip");
						reporteDAO.updateReporteFecha("update_reporte_general",
								moduloConGob, archivozip, login.getUsuarioId(),
								"2");
						setMensaje("Error al hacer zip");
						request.setAttribute("mensaje", mensaje);
						return false;
					}
				} else {
					// System.out.println("El Reporte esta vacio");
					// Insertarlo en la tabla REPORTE -- ESTADO 2
					reporteDAO.ponerReporteMensaje("2", moduloConGob,
							login.getUsuarioId(),
							rb3.getString("reporte.PathReporte") + archivozip
									+ "", "zip", "" + archivozip,
							"ReporteActualizarBoletinPaila",
							"Ocurrin problema en consolidadoGobiernoEscolar");
					reporteDAO
							.updateReporteFecha("update_reporte_general",
									moduloConGob, archivozip,
									login.getUsuarioId(), "2");
					setMensaje("No hay datos para generar el reporte");
					request.setAttribute("mensaje", mensaje);
					return false;
				}
			} else {
				setMensaje("Usted ya mandn generar un reporte de Consolidado Gobierno Escolar \nPor favor espere que termine, para solicitar uno nuevo");
				request.setAttribute("mensaje", mensaje);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			reporteDAO.ponerReporteMensaje("2", moduloConGob,
					login.getUsuarioId(), rb3.getString("reporte.PathReporte")
							+ archivozip + "", "zip", "" + archivozip,
					"ReporteActualizarBoletin",
					"Ocurrin excepcinn en gobiernoEscolar");
			return false;
		}
	}

	/**
	 * Inserta los valores por defecto para el bean de información bnsica con
	 * respecto a la informacion de la institucinn educativa
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeans(HttpServletRequest request)
			throws ServletException, IOException {
		login = (Login) session.getAttribute("login");
		String sentencia = "";
		return true;
	}

	/**
	 * Inserta los valores por defecto para el bean de información bnsica con
	 * respecto a la informacion de la institucinn educativa
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeanLiderEstudiantil(HttpServletRequest request)
			throws ServletException, IOException {
		filtroBeanReporteLider = (FiltroBeanReporteLider) session
				.getAttribute("filtro");
		filtroBeanReporteLider.setInstitucion(new Long(login.getInstId()));
		String sentencia = "";
		return true;
	}

	/**
	 * Inserta los valores por defecto para el bean de información bnsica con
	 * respecto a la informacion de la institucinn educativa
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeanConsolidadoLiderEstudiantil(
			HttpServletRequest request) throws ServletException, IOException {
		filtroBeanReporteConsolidadoLider = (FiltroBeanReporteConsolidadoLider) session
				.getAttribute("filtroConLid");
		filtroBeanReporteConsolidadoLider.setInstitucion(new Long(login
				.getInstId()));
		String sentencia = "";
		return true;
	}

	/**
	 * Inserta los valores por defecto para el bean de información bnsica con
	 * respecto a la informacion de la institucinn educativa
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeanGobiernoEscolar(HttpServletRequest request)
			throws ServletException, IOException {
		filtroBeanReporteGobierno = (FiltroBeanReporteGobierno) session
				.getAttribute("filtroG");
		filtroBeanReporteGobierno.setInstitucion(new Long(login.getInstId()));
		String sentencia = "";
		return true;
	}

	/**
	 * Inserta los valores por defecto para el bean de información bnsica con
	 * respecto a la informacion de la institucinn educativa
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeanConsolidadoGobiernoEscolar(
			HttpServletRequest request) throws ServletException, IOException {
		filtroBeanReporteConsolidadoGobierno = (FiltroBeanReporteConsolidadoGobierno) session
				.getAttribute("filtroConGob");
		filtroBeanReporteConsolidadoGobierno.setInstitucion(new Long(login
				.getInstId()));
		String sentencia = "";
		return true;
	}

	/**
	 * Recibe la peticion por el metodo get de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * Recibe la peticion por el metodo Post de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String s = process(request, response);
		if (s != null && !s.equals(""))
			ir(1, s, request, response);
	}

	/**
	 * Concatena el parametro en una variable que contiene los mensajes que se
	 * van a enviar a la vista
	 * 
	 * @param String
	 *            s
	 **/
	public void setMensaje(String s) {
		if (!err) {
			err = true;
			mensaje = "VERIFIQUE LA SIGUIENTE información: \n\n";
		}
		mensaje = "  - " + s + "\n";
	}

	/**
	 * Retorna una variable que contiene los mensajes que se van a enviar a la
	 * vista
	 * 
	 * @return String
	 **/
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * Redirige el control a otro servlet
	 * 
	 * @param int a: 1=redirigir como 'include', 2=redirigir como 'forward'
	 * @param String
	 *            s
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/
	public void ir(int a, String s, HttpServletRequest rq,
			HttpServletResponse rs) throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher(s);
		if (a == 1)
			rd.include(rq, rs);
		else
			rd.forward(rq, rs);
	}

	/*
	 * Cierra cursor
	 */
	public void destroy() {
		if (cursor != null)
			cursor.cerrar();
		cursor = null;
	}

}
