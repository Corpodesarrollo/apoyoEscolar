package articulacion.repInformesArt;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import siges.dao.*;
import siges.io.*;
import articulacion.repInformesArt.dao.RepInfArtDAO;
import articulacion.repInformesArt.vo.FiltroBeanReporteConstancias;
import java.util.*;
import java.io.*;
import javax.servlet.ServletContext;
import siges.login.beans.Login;

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

	private boolean err;// variable que inidica si hay o no errores en la
						// validacion de los datos del formulario

	private ResourceBundle rb3;

	private java.sql.Timestamp f2;

	private Util util;

	private final String modulo = "56";

	private String s;

	private String ant;

	private String er;

	private String sig;

	private String home;

	private File reportFile;

	private String path;

	private String path1;

	private String path2;

	private Dao dao;

	private File escudo;

	private RepInfArtDAO reporteDAO;

	/**
	 * Procesa la peticion HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */

	public String process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		rb3 = ResourceBundle
				.getBundle("articulacion.repInformesArt.bundle.repInfArt");
		f2 = new java.sql.Timestamp(new java.util.Date().getTime());
		s = getServletConfig().getInitParameter("s");
		ant = getServletConfig().getInitParameter("ant");
		er = getServletContext().getInitParameter("error");
		home = getServletContext().getInitParameter("home");
		err = false;

		if (request.getParameter("reporte") == null) {
			request.setAttribute("mensaje",
					"Error al mandar generar el tipo de reporte solicitado");
			ir(1, er, request, response);
			return er;
		}
		Login login = (Login) session.getAttribute("login");
		try {
			cursor = new Cursor();
			util = new Util(cursor);
			dao = new Dao(cursor);
			reporteDAO = new RepInfArtDAO(cursor);
			String vigencia = dao.getVigencia();

			if (vigencia == null) {
				// /System.out.println("*W* LA VIGENCIA ES NULA *W* ");
				return er;
			}

			int requeridos = Integer.parseInt(request.getParameter("reporte"));
			// System.out.println("REPORTE REQUERIDO: "+requeridos);

			ServletContext context = (ServletContext) request.getSession()
					.getServletContext();
			String contextoTotal = context.getRealPath("/");
			path = Ruta2.get(context.getRealPath("/"),
					rb3.getString("ruta_jaspers"));
			path1 = Ruta2.get(context.getRealPath("/"),
					rb3.getString("ruta_img"));
			path2 = Ruta2.get(context.getRealPath("/"),
					rb3.getString("ruta_img_inst"));

			reportFile = new File(path + rb3.getString("jasper_constancias"));

			switch (requeridos) {
			case 1:
				if (!constanciasArt(login, session, request, vigencia,
						contextoTotal))
					return s;
				break;

			}
			if (err) {
				request.setAttribute("mensaje", "Error interno");
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

	public boolean constanciasArt(Login login, HttpSession session,
			HttpServletRequest request, String vigencia, String contextoTotal)
			throws ServletException, IOException {
		String nombreReporte = null, cod_estudiante = null;
		Map parameters = new HashMap();
		String archivosalida = null;
		int zise;
		Collection list = new ArrayList();
		Zip zip = new Zip();
		String archivo = null, archivozip = null;
		byte[] bytes2;
		try {
			// System.out.println("*********////*********nENTRn A GENERAR CONSTANCIAS ART!**********//**********");
			FiltroBeanReporteConstancias filtrobeanReporteConstancia = (FiltroBeanReporteConstancias) session
					.getAttribute("filtroC");
			filtrobeanReporteConstancia.setInstitucion(new Long(login
					.getInstId()));
			filtrobeanReporteConstancia.setInstitucionDane(reporteDAO
					.getDaneInstitucion(new Long(login.getInstId())));

			/* PARAMETROS PARA el JASPER DE HORARIOS DE PLAN DE ESTUDIOS */
			parameters.put("INSTITUCION", new Long(login.getInstId()));
			parameters.put("SEDE", filtrobeanReporteConstancia.getSede());
			parameters.put("JORNADA", filtrobeanReporteConstancia.getJornada());
			parameters.put("ESPECIALIDAD",
					filtrobeanReporteConstancia.getFilEspecialidad());
			parameters.put("MOTIVO", filtrobeanReporteConstancia.getMotivo());
			if (filtrobeanReporteConstancia.getFilEstudiante().longValue() > 0)
				parameters.put("COD_ESTUDIANTE",
						filtrobeanReporteConstancia.getFilEstudiante());
			else
				parameters.put("COD_ESTUDIANTE", new Long(-99));

			parameters.put("USUARIO", login.getUsuario());
			escudo = new File(path2 + "e"
					+ filtrobeanReporteConstancia.getInstitucionDane() + ".gif");

			if (escudo.exists())
				parameters.put("PATH_ICONO_INSTITUCION", path2 + "e"
						+ filtrobeanReporteConstancia.getInstitucionDane()
						+ ".gif");
			else
				parameters.put("PATH_ICONO_INSTITUCION",
						path1 + rb3.getString("imagen"));

			parameters.put("PATH_ICONO_SECRETARIA",
					path1 + rb3.getString("imagen"));
			parameters.put("SUBREPORT_DIR", path);
			// System.out.println("ESTUDIANTE "+filtrobeanReporteConstancia.getFilEstudiante().longValue());

			escudo = new File(path2 + "e"
					+ filtrobeanReporteConstancia.getInstitucionDane() + ".gif");

			// System.out.println("PATH_ICONO_SECRETARIA:
			// "+path1+rb3.getString("imagen"));
			// System.out.println("SUBREPORT_DIR: "+path);

			if (!reporteDAO.isWorking(login.getUsuarioId(), modulo)) {

				// sede = (!(filtrobeanReporteConstancia.getSede().intValue() ==
				// -9) ? "Sede_"+
				// filtrobeanReporteConstancia.getSede().intValue(): "");
				// System.out.println("*SEDE*"+sede);
				// jornada =
				// (!filtrobeanReporteConstancia.getJornada().equals("-9") ?
				// "_Jornada_"+ filtrobeanReporteConstancia.getJornada() : "");
				// // System.out.println("*JORNADA*"+jornada);

				if (filtrobeanReporteConstancia.getFilEstudiante().longValue() != -99)
					cod_estudiante = (!filtrobeanReporteConstancia
							.getFilEstudiante().equals("-99") ? "_Estudiante_"
							+ filtrobeanReporteConstancia.getFilEstudiante()
							: "");
				nombreReporte = cod_estudiante
						+ "_Fecha_"
						+ f2.toString().replace(' ', '_').replace(':', '-')
								.replace('.', '-');
				archivo = "Constancia_Est_Art_" + nombreReporte + ".pdf";
				archivozip = "Constancia_Est_Art_" + nombreReporte + ".zip";
				reporteDAO.ponerReporte(modulo, login.getUsuarioId(),
						rb3.getString("reporte.PathReporte") + archivozip + "",
						"zip", "" + archivozip, "0", "ReporteInsertarEstado");// Estado
																				// -1
				if (!reporteDAO.isEmptyConstancia(filtrobeanReporteConstancia)) {
					// System.out.println("Hay datos para ejecutar el jasper");
					bytes2 = reporteDAO.getArrayBytes(
							filtrobeanReporteConstancia.getInstitucion(),
							login.getUsuarioId(), archivozip, reportFile,
							parameters, modulo);
					reporteDAO.updateReporteEstado(modulo,
							login.getUsuarioId(),
							rb3.getString("reporte.PathReporte") + archivozip
									+ "", "zip", "" + archivozip,
							"ReporteActualizarListo");
					reporteDAO.ponerArchivo(bytes2, archivo, contextoTotal);
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
						reporteDAO.ponerReporteMensaje("4", modulo,
								login.getUsuarioId(),
								rb3.getString("reporte.PathReporte")
										+ archivozip + "", "zip", ""
										+ archivozip,
								"ReporteActualizarHorarioFallo",
								"Ocurrin problema al hacer zip");
						reporteDAO.updateReporteFecha("update_reporte_general",
								modulo, archivozip, login.getUsuarioId(), "2");
						request.setAttribute("mensaje", "Error al crear zip");
						return false;
					}
				} else {
					// System.out.println("El Reporte esta vacio");
					// Insertarlo en la tabla REPORTE -- ESTADO 2
					reporteDAO.ponerReporteMensaje("2", modulo,
							login.getUsuarioId(),
							rb3.getString("reporte.PathReporte") + archivozip
									+ "", "zip", "" + archivozip,
							"ReporteActualizarHorarioFallo",
							"Ocurrin problema en PLan de estudios");
					reporteDAO.updateReporteFecha("update_reporte_general",
							modulo, archivozip, login.getUsuarioId(), "2");
					request.setAttribute("mensaje",
							"No hay datos suficientes para generar el reporte Constancia Art.");
					return false;
				}

			} else {
				request.setAttribute(
						"mensaje",
						"Usted ya mandn generar un reporte de Informes Art.\nPor favor espere que termine, para solicitar uno nuevo");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			reporteDAO.ponerReporteMensaje("2", modulo, login.getUsuarioId(),
					rb3.getString("reporte.PathReporte") + archivozip + "",
					"zip", "" + archivozip, "ReporteActualizarBoletin",
					"Ocurrin excepcinn en Plan Estudios");
			return false;
		}
	}

	/**
	 * Recibe la peticion por el metodo get de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
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
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String s = process(request, response);
		if (s != null && !s.equals(""))
			ir(1, s, request, response);
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
	 */
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
