package articulacion.reporteHorario;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import siges.dao.*;
import articulacion.horarioArticulacion.vo.FiltroHorarioVO;
import siges.io.*;
import articulacion.reporteHorario.beans.*;
import articulacion.reporteHorario.dao.*;
import siges.exceptions.InternalErrorException;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.*;
import com.lowagie.text.pdf.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.awt.SystemColor;
import java.io.*;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import org.apache.commons.io.CopyUtils;
import org.apache.commons.io.FileUtils;
import siges.login.beans.Login;
import siges.util.Logger;

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
	private FiltroBeanReporte filtrobeanReporte;
	private FiltroBeanReporteDocente filtroBeanReporteDocente;
	private FiltroBeanReporteEspacio filtroBeanReporteEspacio;
	private Util util;
	private final String modulo = "24";
	private final String moduloDoc = "25";
	private final String moduloEsp = "26";
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
	private String home;
	private byte[] bytes, bytes2, bytes3;
	private Map parameters;
	private File reportFile;
	private File reportFile2;
	private File reportFile3;
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
	private reporteHorarioDAO reporteDAO;

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
		rb3 = ResourceBundle.getBundle("reporteHorario");
		f2 = new java.sql.Timestamp(new java.util.Date().getTime());
		s = getServletConfig().getInitParameter("s");
		s1 = getServletConfig().getInitParameter("s1");
		sig = getServletConfig().getInitParameter("sig");// FiltroGrupo.jsp
		sig2 = getServletConfig().getInitParameter("sig2");// FiltroDocente.jsp
		sig3 = getServletConfig().getInitParameter("sig3");// FiltroEspacioFisico.jsp
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
		alert = "El Reporte se esta generando en este momento. \nPulse en el hipervinculo 'Listado de Reportes' para ver si el reporte ya fue generado. \nO vaya a la Opción de menu 'Reportes generados'";

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
			reporteDAO = new reporteHorarioDAO(cursor);
			vigencia = dao.getVigencia();

			if (vigencia == null) {
				// System.out.println("*W* LA VIGENCIA ES NULA *W* ");
				return er;
			}
			requeridos = Integer.parseInt(request.getParameter("reporte"));
			// System.out.println("REPORTE REQUERIDO: "+requeridos);

			ServletContext context = (ServletContext) request.getSession()
					.getServletContext();
			contextoTotal = context.getRealPath("/");
			path = Ruta2.get(context.getRealPath("/"),
					rb3.getString("ruta_jaspers"));
			path1 = Ruta2.get(context.getRealPath("/"),
					rb3.getString("ruta_img"));
			path2 = Ruta2.get(context.getRealPath("/"),
					rb3.getString("ruta_img_inst"));

			reportFile = new File(path + rb3.getString("jasper_horario_grupo"));
			reportFile2 = new File(path
					+ rb3.getString("jasper_horario_docente"));
			reportFile3 = new File(path
					+ rb3.getString("jasper_horario_espacio"));

			switch (requeridos) {
			case 1:
				if (!horarioGrupo(request, vigencia, contextoTotal))
					return sig;
				break;
			case 2:
				if (!horarioDocente(request, vigencia, contextoTotal))
					return sig2;
				break;
			case 3:
				if (!horarioEspacio(request, vigencia, contextoTotal))
					return sig3;
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
		}// System.out.println("S: "+s);
		return s;
	}

	public boolean horarioGrupo(HttpServletRequest request, String vigencia,
			String contextoTotal) throws ServletException, IOException {
		String sede = null, jornada = null, grado = null, grupo = null, nombreReporte = null;
		parameters = new HashMap();
		String archivosalida = null;
		int zise;
		Collection list = new ArrayList();
		Zip zip = new Zip();

		try {
			// System.out.println("*********////*********nENTRn A GENERAR HORARIO X GRUPO!**********//**********");
			if (!asignarBeanHorarioGrupo(request)) {
				setMensaje("Error capturando bean del reporte");
				request.setAttribute("mensaje", mensaje);
				ir(1, er, request, response);
				return false;
			}
			filtrobeanReporte.setInstitucionDane(reporteDAO
					.getDaneInstitucion(new Long(login.getInstId())));
			filtrobeanReporte.setVigencia(new Long(vigencia));

			/* PARAMETROS PARA el JASPER DE AREAS */
			parameters.put("INSTITUCION", new Long(login.getInstId()));
			parameters.put("SEDE", new Long(login.getSedeId()));
			parameters.put("JORNADA", new Long(login.getJornadaId()));
			parameters.put("GRADO", filtrobeanReporte.getGrado());
			parameters.put("GRUPO", filtrobeanReporte.getGrupo());
			parameters.put("METODOLOGIA", new Long(login.getMetodologiaId()));
			parameters.put("VIGENCIA", new Long(vigencia));
			parameters.put("USUARIO", login.getUsuario());
			escudo = new File(path2 + "e"
					+ filtrobeanReporte.getInstitucionDane() + ".gif");
			// System.out.println("escudo: " + escudo);
			if (escudo.exists())
				parameters.put("PATH_ICONO_INSTITUCION", path2 + "e"
						+ filtrobeanReporte.getInstitucionDane() + ".gif");
			else
				parameters.put("PATH_ICONO_INSTITUCION",
						path1 + rb3.getString("imagen"));

			parameters.put("PATH_ICONO_SECRETARIA",
					path1 + rb3.getString("imagen"));
			parameters.put("SUBREPORT_DIR", path);

			// System.out.println("**PARAMETERS DEL JASPER**");
			// System.out.println("INSTITUCION: " + new
			// Long(login.getInstId()));
			// System.out.println("SEDE:" + new Long(login.getSedeId()));
			// System.out.println("JORNADA: " + new Long(login.getJornadaId()));
			// System.out.println("GRADO: " + filtrobeanReporte.getGrado());
			// System.out.println("GRUPO: " + filtrobeanReporte.getGrupo());
			// System.out.println("METODOLOGIA: "
			// + new Long(login.getMetodologiaId()));
			// System.out.println("VIGENCIA: " + new Long(vigencia));
			// System.out.println("USUARIO: " + login.getUsuario());
			escudo = new File(path2 + "e"
					+ filtrobeanReporte.getInstitucionDane() + ".gif");
			// if (escudo.exists())
			// System.out.println("PATH_ICONO_INSTITUCION: " + path2 + "e"
			// + filtrobeanReporte.getInstitucionDane() + ".gif");
			// else
			// System.out.println("PATH_ICONO_INSTITUCION: " + path1
			// + rb3.getString("imagen"));
			//
			// System.out.println("PATH_ICONO_SECRETARIA: " + path1
			// + rb3.getString("imagen"));
			// System.out.println("SUBREPORT_DIR: " + path);

			if (!reporteDAO.isWorking(login.getUsuarioId(), modulo)) {
				// System.out
				// .println("******nNO HAY NINGnN REPORTE DE HORARIOS POR GRUPO GENERANDOSE PARA ESTE USUARIO EN ESTADO CERO!*********");
				sede = (!filtrobeanReporte.getSede().equals("-9") ? "Sede_"
						+ filtrobeanReporte.getSede() : "");
				jornada = (!filtrobeanReporte.getJornada().equals("-9") ? "_Jornada_"
						+ filtrobeanReporte.getJornada()
						: "");
				grado = (!filtrobeanReporte.getGrado().equals("-9") ? "_Grado_"
						+ filtrobeanReporte.getGrado() : "");
				grupo = (!filtrobeanReporte.getGrupo().equals("-9") ? "_Grupo_"
						+ filtrobeanReporte.getGrupo() : "");
				nombreReporte = sede
						+ jornada
						+ grado
						+ grupo
						+ "_Fecha_"
						+ f2.toString().replace(' ', '_').replace(':', '-')
								.replace('.', '-');
				archivo = "Horario_por_Grupo_" + nombreReporte + ".pdf";
				archivozip = "Horario_por_Grupo_" + nombreReporte + ".zip";
				reporteDAO.ponerReporte(modulo, login.getUsuarioId(),
						rb3.getString("reporte.PathReporte") + archivozip + "",
						"zip", "" + archivozip, "0", "ReporteInsertarEstado");// Estado
																				// -1
				if (!reporteDAO.isEmpty(filtrobeanReporte)) {
					// System.out.println("Hay datos para ejecutar el jasper");
					bytes = reporteDAO.getArrayBytes(
							filtrobeanReporte.getInstitucion(),
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
					// System.out.println("archivosalida: " + archivosalida);
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
							"Ocurrin problema en horarioGrupo");
					reporteDAO.updateReporteFecha("update_reporte_general",
							modulo, archivozip, login.getUsuarioId(), "2");
					setMensaje("No hay datos para generar el reporte");
					request.setAttribute("mensaje", mensaje);
					return false;
				}
			} else {
				setMensaje("Usted ya mandn generar un reporte de Horarios por grupo \nPor favor espere que termine, para solicitar uno nuevo");
				request.setAttribute("mensaje", mensaje);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			reporteDAO.ponerReporteMensaje("2", modulo, login.getUsuarioId(),
					rb3.getString("reporte.PathReporte") + archivozip + "",
					"zip", "" + archivozip, "ReporteActualizarBoletin",
					"Ocurrin excepcinn en horarioGrupo");
			return false;
		}
	}

	public boolean horarioDocente(HttpServletRequest request, String vigencia,
			String contextoTotal) throws ServletException, IOException {
		String sede = null, jornada = null, docente = null, nombreReporte = null;
		parameters = new HashMap();
		String archivosalida = null;
		int zise;
		Collection list = new ArrayList();
		Zip zip = new Zip();

		try {
			// System.out
			// .println("*********////*********nENTRn A GENERAR HORARIO X DOCENTE!**********//**********");
			if (!asignarBeanHorarioDocente(request)) {
				setMensaje("Error capturando bean del reporte");
				request.setAttribute("mensaje", mensaje);
				ir(1, er, request, response);
				return false;
			}
			filtroBeanReporteDocente.setInstitucionDane(reporteDAO
					.getDaneInstitucion(new Long(login.getInstId())));
			filtroBeanReporteDocente.setVigencia(new Long(vigencia));

			/* PARAMETROS PARA el JASPER DE AREAS */
			parameters.put("INSTITUCION", new Long(login.getInstId()));
			parameters.put("SEDE", new Long(login.getSedeId()));
			parameters.put("JORNADA", new Long(login.getJornadaId()));
			parameters.put("DOCENTE",
					String.valueOf(filtroBeanReporteDocente.getDocente()));
			parameters.put("COD_ESTRUCTURA",
					filtroBeanReporteDocente.getEstructura());
			parameters.put("USUARIO", login.getUsuario());
			parameters.put("VIGENCIA", new Long(2007));

			escudo = new File(path2 + "e"
					+ filtroBeanReporteDocente.getInstitucionDane() + ".gif");
			// System.out.println("escudo: " + escudo);
			if (escudo.exists())
				parameters.put("PATH_ICONO_INSTITUCION", path2 + "e"
						+ filtroBeanReporteDocente.getInstitucionDane()
						+ ".gif");
			else
				parameters.put("PATH_ICONO_INSTITUCION",
						path1 + rb3.getString("imagen"));

			parameters.put("PATH_ICONO_SECRETARIA",
					path1 + rb3.getString("imagen"));
			parameters.put("SUBREPORT_DIR", path);

			// System.out.println("**PARAMETERS DEL JASPER**");
			// System.out.println("INSTITUCION: " + new
			// Long(login.getInstId()));
			// System.out.println("SEDE:" + new Long(login.getSedeId()));
			// System.out.println("JORNADA: " + new Long(login.getJornadaId()));
			// System.out.println("DOCENTE: "
			// + filtroBeanReporteDocente.getDocente());
			// System.out.println("COD_ESTRUCTURA: "
			// + filtroBeanReporteDocente.getEstructura());
			// System.out.println("USUARIO: " + login.getUsuario());
			escudo = new File(path2 + "e"
					+ filtroBeanReporteDocente.getInstitucionDane() + ".gif");
			// if (escudo.exists())
			// System.out.println("PATH_ICONO_INSTITUCION: " + path2 + "e"
			// + filtroBeanReporteDocente.getInstitucionDane()
			// + ".gif");
			// else
			// System.out.println("PATH_ICONO_INSTITUCION: " + path1
			// + rb3.getString("imagen"));
			//
			// System.out.println("PATH_ICONO_SECRETARIA: " + path1
			// + rb3.getString("imagen"));
			// System.out.println("SUBREPORT_DIR: " + path);

			if (!reporteDAO.isWorking(login.getUsuarioId(), moduloDoc)) {
				// System.out
				// .println("******nNO HAY NINGnN REPORTE DE HORARIOS POR DOCENTE GENERANDOSE PARA ESTE USUARIO EN ESTADO CERO!*********");
				sede = (!filtroBeanReporteDocente.getSede().equals("-9") ? "Sede_"
						+ filtroBeanReporteDocente.getSede()
						: "");
				jornada = (!filtroBeanReporteDocente.getJornada().equals("-9") ? "_Jornada_"
						+ filtroBeanReporteDocente.getJornada()
						: "");
				docente = (!filtroBeanReporteDocente.getDocente().equals("-9") ? "_Docente_"
						+ filtroBeanReporteDocente.getDocente()
						: "");
				nombreReporte = sede
						+ jornada
						+ docente
						+ "_Fecha_"
						+ f2.toString().replace(' ', '_').replace(':', '-')
								.replace('.', '-');
				archivo = "Horario_por_Docente_" + nombreReporte + ".pdf";
				archivozip = "Horario_por_Docente_" + nombreReporte + ".zip";
				reporteDAO.ponerReporte(moduloDoc, login.getUsuarioId(),
						rb3.getString("reporte.PathReporte") + archivozip + "",
						"zip", "" + archivozip, "0", "ReporteInsertarEstado");// Estado
																				// -1
				if (!reporteDAO.isEmptyDoc(filtroBeanReporteDocente)) {
					// System.out.println("Hay datos para ejecutar el jasper");
					bytes2 = reporteDAO.getArrayBytes(
							filtroBeanReporteDocente.getInstitucion(),
							login.getUsuarioId(), archivozip, reportFile2,
							parameters, moduloDoc);
					reporteDAO.updateReporteEstado(moduloDoc,
							login.getUsuarioId(),
							rb3.getString("reporte.PathReporte") + archivozip
									+ "", "zip", "" + archivozip,
							"ReporteActualizarListo");
					reporteDAO.ponerArchivo(bytes2, archivo, contextoTotal);
					archivosalida = Ruta.get(contextoTotal,
							rb3.getString("reportes.PathReporte"));
					// System.out.println("archivosalida: " + archivosalida);
					list.add(archivosalida + archivo);// pdf
					zise = 100000;
					if (zip.ponerZip(archivosalida, archivozip, zise, list)) {
						reporteDAO.updateReporteFecha("update_reporte_general",
								moduloDoc, archivozip, login.getUsuarioId(),
								"1");
						return true;
					} else {
						reporteDAO.ponerReporteMensaje("2", moduloDoc,
								login.getUsuarioId(),
								rb3.getString("reporte.PathReporte")
										+ archivozip + "", "zip", ""
										+ archivozip,
								"ReporteActualizarBoletinPaila",
								"Ocurrin problema al hacer zip");
						reporteDAO.updateReporteFecha("update_reporte_general",
								moduloDoc, archivozip, login.getUsuarioId(),
								"2");
						setMensaje("Error al hacer zip");
						request.setAttribute("mensaje", mensaje);
						return false;
					}
				} else {
					// System.out.println("El Reporte esta vacio");
					// Insertarlo en la tabla REPORTE -- ESTADO 2
					reporteDAO.ponerReporteMensaje("2", moduloDoc,
							login.getUsuarioId(),
							rb3.getString("reporte.PathReporte") + archivozip
									+ "", "zip", "" + archivozip,
							"ReporteActualizarBoletinPaila",
							"Ocurrin problema en horarioDocente");
					reporteDAO.updateReporteFecha("update_reporte_general",
							moduloDoc, archivozip, login.getUsuarioId(), "2");
					setMensaje("No hay datos para generar el reporte");
					request.setAttribute("mensaje", mensaje);
					return false;
				}
			} else {
				setMensaje("Usted ya mandn generar un reporte de Horarios por docente \nPor favor espere que termine, para solicitar uno nuevo");
				request.setAttribute("mensaje", mensaje);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			reporteDAO.ponerReporteMensaje("2", moduloDoc,
					login.getUsuarioId(), rb3.getString("reporte.PathReporte")
							+ archivozip + "", "zip", "" + archivozip,
					"ReporteActualizarBoletin",
					"Ocurrin excepcinn en horarioDocente");
			return false;
		}
	}

	public boolean horarioEspacio(HttpServletRequest request, String vigencia,
			String contextoTotal) throws ServletException, IOException {
		String sede = null, jornada = null, espacio = null, nombreReporte = null;
		parameters = new HashMap();
		String archivosalida = null;
		int zise;
		Collection list = new ArrayList();
		Zip zip = new Zip();

		try {
			// System.out
			// .println("*********////*********nENTRn A GENERAR HORARIO X ESPACIO!**********//**********");
			if (!asignarBeanHorarioEspacio(request)) {
				setMensaje("Error capturando bean del reporte");
				request.setAttribute("mensaje", mensaje);
				ir(1, er, request, response);
				return false;
			}
			filtroBeanReporteEspacio.setInstitucionDane(reporteDAO
					.getDaneInstitucion(new Long(login.getInstId())));
			filtroBeanReporteEspacio.setVigencia(new Long(vigencia));

			/* PARAMETROS PARA el JASPER DE AREAS */
			parameters.put("INSTITUCION", new Long(login.getInstId()));
			parameters.put("SEDE", new Long(login.getSedeId()));
			parameters.put("JORNADA", new Long(login.getJornadaId()));
			parameters.put("ESPACIO_FISICO",
					filtroBeanReporteEspacio.getEspacioFisico());
			parameters.put("COD_ESTRUCTURA",
					filtroBeanReporteEspacio.getEstructura());
			parameters.put("USUARIO", login.getUsuario());
			parameters.put("VIGENCIA", new Long(2007));
			escudo = new File(path2 + "e"
					+ filtroBeanReporteEspacio.getInstitucionDane() + ".gif");
			// System.out.println("escudo: " + escudo);
			if (escudo.exists())
				parameters.put("PATH_ICONO_INSTITUCION", path2 + "e"
						+ filtroBeanReporteEspacio.getInstitucionDane()
						+ ".gif");
			else
				parameters.put("PATH_ICONO_INSTITUCION",
						path1 + rb3.getString("imagen"));

			parameters.put("PATH_ICONO_SECRETARIA",
					path1 + rb3.getString("imagen"));
			parameters.put("SUBREPORT_DIR", path);

			// System.out.println("**PARAMETERS DEL JASPER**");
			// System.out.println("INSTITUCION: " + new
			// Long(login.getInstId()));
			// System.out.println("SEDE:" + new Long(login.getSedeId()));
			// System.out.println("JORNADA: " + new Long(login.getJornadaId()));
			// System.out.println("ESPACIO_FISICO: "
			// + filtroBeanReporteEspacio.getEspacioFisico());
			// System.out.println("COD_ESTRUCTURA: "
			// + filtroBeanReporteEspacio.getEstructura());
			// System.out.println("USUARIO: " + login.getUsuario());
			escudo = new File(path2 + "e"
					+ filtroBeanReporteEspacio.getInstitucionDane() + ".gif");
			// if (escudo.exists())
			// System.out.println("PATH_ICONO_INSTITUCION: " + path2 + "e"
			// + filtroBeanReporteEspacio.getInstitucionDane()
			// + ".gif");
			// else
			// System.out.println("PATH_ICONO_INSTITUCION: " + path1
			// + rb3.getString("imagen"));

			// System.out.println("PATH_ICONO_SECRETARIA: " + path1
			// + rb3.getString("imagen"));
			// System.out.println("SUBREPORT_DIR: " + path);

			if (!reporteDAO.isWorking(login.getUsuarioId(), moduloEsp)) {
				// System.out
				// .println("******nNO HAY NINGnN REPORTE DE HORARIOS POR ESPACIO GENERANDOSE PARA ESTE USUARIO EN ESTADO CERO!*********");
				sede = (!filtroBeanReporteEspacio.getSede().equals("-9") ? "Sede_"
						+ filtroBeanReporteEspacio.getSede()
						: "");
				jornada = (!filtroBeanReporteEspacio.getJornada().equals("-9") ? "_Jornada_"
						+ filtroBeanReporteEspacio.getJornada()
						: "");
				espacio = (!filtroBeanReporteEspacio.getEspacioFisico().equals(
						"-9") ? "_EspacioFisico_"
						+ filtroBeanReporteEspacio.getEspacioFisico() : "");
				nombreReporte = sede
						+ jornada
						+ espacio
						+ "_Fecha_"
						+ f2.toString().replace(' ', '_').replace(':', '-')
								.replace('.', '-');
				archivo = "Horario_por_Espacio_Fisico_" + nombreReporte
						+ ".pdf";
				archivozip = "Horario_por_Espacio_Fisico_" + nombreReporte
						+ ".zip";
				reporteDAO.ponerReporte(moduloEsp, login.getUsuarioId(),
						rb3.getString("reporte.PathReporte") + archivozip + "",
						"zip", "" + archivozip, "0", "ReporteInsertarEstado");// Estado
																				// -1
				if (!reporteDAO.isEmptyEsp(filtroBeanReporteEspacio)) {
					// System.out.println("Hay datos para ejecutar el jasper");
					bytes3 = reporteDAO.getArrayBytes(
							filtroBeanReporteEspacio.getInstitucion(),
							login.getUsuarioId(), archivozip, reportFile3,
							parameters, moduloEsp);
					reporteDAO.updateReporteEstado(moduloEsp,
							login.getUsuarioId(),
							rb3.getString("reporte.PathReporte") + archivozip
									+ "", "zip", "" + archivozip,
							"ReporteActualizarListo");
					reporteDAO.ponerArchivo(bytes3, archivo, contextoTotal);
					archivosalida = Ruta.get(contextoTotal,
							rb3.getString("reportes.PathReporte"));
					// System.out.println("archivosalida: " + archivosalida);
					list.add(archivosalida + archivo);// pdf
					zise = 100000;
					if (zip.ponerZip(archivosalida, archivozip, zise, list)) {
						reporteDAO.updateReporteFecha("update_reporte_general",
								moduloEsp, archivozip, login.getUsuarioId(),
								"1");
						return true;
					} else {
						reporteDAO.ponerReporteMensaje("2", moduloEsp,
								login.getUsuarioId(),
								rb3.getString("reporte.PathReporte")
										+ archivozip + "", "zip", ""
										+ archivozip,
								"ReporteActualizarBoletinPaila",
								"Ocurrin problema al hacer zip");
						reporteDAO.updateReporteFecha("update_reporte_general",
								moduloEsp, archivozip, login.getUsuarioId(),
								"2");
						setMensaje("Error al hacer zip");
						request.setAttribute("mensaje", mensaje);
						return false;
					}
				} else {
					// System.out.println("El Reporte esta vacio");
					// Insertarlo en la tabla REPORTE -- ESTADO 2
					reporteDAO.ponerReporteMensaje("2", moduloEsp,
							login.getUsuarioId(),
							rb3.getString("reporte.PathReporte") + archivozip
									+ "", "zip", "" + archivozip,
							"ReporteActualizarBoletinPaila",
							"Ocurrin problema en horarioEspacio");
					reporteDAO.updateReporteFecha("update_reporte_general",
							moduloEsp, archivozip, login.getUsuarioId(), "2");
					setMensaje("No hay datos para generar el reporte");
					request.setAttribute("mensaje", mensaje);
					return false;
				}
			} else {
				setMensaje("Usted ya mandn generar un reporte de Horarios por espacio fisico \nPor favor espere que termine, para solicitar uno nuevo");
				request.setAttribute("mensaje", mensaje);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			reporteDAO.ponerReporteMensaje("2", moduloEsp,
					login.getUsuarioId(), rb3.getString("reporte.PathReporte")
							+ archivozip + "", "zip", "" + archivozip,
					"ReporteActualizarBoletin",
					"Ocurrin excepcinn en horarioEspacio");
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
	public boolean asignarBeanHorarioGrupo(HttpServletRequest request)
			throws ServletException, IOException {
		filtrobeanReporte = (FiltroBeanReporte) session.getAttribute("filtro");
		filtrobeanReporte.setInstitucion(new Long(login.getInstId()));
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
	public boolean asignarBeanHorarioDocente(HttpServletRequest request)
			throws ServletException, IOException {
		filtroBeanReporteDocente = (FiltroBeanReporteDocente) session
				.getAttribute("filtroDoc");
		filtroBeanReporteDocente.setInstitucion(new Long(login.getInstId()));
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
	public boolean asignarBeanHorarioEspacio(HttpServletRequest request)
			throws ServletException, IOException {
		filtroBeanReporteEspacio = (FiltroBeanReporteEspacio) session
				.getAttribute("filtroEsp");
		filtroBeanReporteEspacio.setInstitucion(new Long(login.getInstId()));
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
