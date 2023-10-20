package articulacion.repEstudiantesArt;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import siges.dao.*;
import siges.io.*;
import articulacion.repEstudiantesArt.dao.repEstArtDAO;
import articulacion.repEstudiantesArt.vo.FiltroBeanReporteEstXTutor;
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

	private Cursor cursor;

	private boolean err;

	private ResourceBundle rb3;

	private java.sql.Timestamp f2;

	private Util util;

	private final String modulo = "55";

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

	private repEstArtDAO reporteDAO;

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
				.getBundle("articulacion.repEstudiantesArt.bundle.repEstArt");
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
			reporteDAO = new repEstArtDAO(cursor);
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

			reportFile = new File(path
					+ rb3.getString("jasper_estudiantes_x_tutor"));

			switch (requeridos) {
			case 1:
				if (!estudiantesXTutor(login, session, request, response,
						vigencia, contextoTotal))
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

	public boolean estudiantesXTutor(Login login, HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			String vigencia, String contextoTotal) throws ServletException,
			IOException {
		String nombreReporte = null, cod_docente = null;
		Map parameters = new HashMap();
		String archivosalida = null;
		int zise;
		Collection list = new ArrayList();
		Zip zip = new Zip();
		String archivo = null, archivozip = null;
		byte[] bytes2;
		try {
			// System.out.println("*********////*********nENTRn A GENERAR ESTUDIANTES ART!**********//**********");
			FiltroBeanReporteEstXTutor filtrobeanReporteEstXTutor = (FiltroBeanReporteEstXTutor) session
					.getAttribute("filtro");
			filtrobeanReporteEstXTutor.setInstitucion(new Long(login
					.getInstId()));
			filtrobeanReporteEstXTutor.setInstitucionDane(reporteDAO
					.getDaneInstitucion(new Long(login.getInstId())));

			/* PARAMETROS PARA el JASPER DE HORARIOS DE PLAN DE ESTUDIOS */
			parameters.put("INSTITUCION", new Long(login.getInstId()));
			parameters.put("SEDE", filtrobeanReporteEstXTutor.getSede());
			parameters.put("JORNADA", filtrobeanReporteEstXTutor.getJornada());
			if (filtrobeanReporteEstXTutor.getDocente().longValue() > 0)
				parameters.put("TUTOR", String
						.valueOf(filtrobeanReporteEstXTutor.getDocente()
								.longValue()));
			else
				parameters.put("TUTOR", "-99");

			parameters.put("USUARIO", login.getUsuario());
			File escudo = new File(path2 + "e"
					+ filtrobeanReporteEstXTutor.getInstitucionDane() + ".gif");

			if (escudo.exists())
				parameters.put("PATH_ICONO_INSTITUCION", path2 + "e"
						+ filtrobeanReporteEstXTutor.getInstitucionDane()
						+ ".gif");
			else
				parameters.put("PATH_ICONO_INSTITUCION",
						path1 + rb3.getString("imagen"));

			parameters.put("PATH_ICONO_SECRETARIA",
					path1 + rb3.getString("imagen"));
			parameters.put("SUBREPORT_DIR", path);
			// System.out.println("PATH EST ART TUTOR "+path);

			escudo = new File(path2 + "e"
					+ filtrobeanReporteEstXTutor.getInstitucionDane() + ".gif");
			/*
			 * if (escudo.exists())
			 * System.out.println("PATH_ICONO_INSTITUCION: " + path2 + "e"+
			 * filtrobeanReporteEstXTutor.getInstitucionDane()+ ".gif"); else
			 * System.out.println("PATH_ICONO_INSTITUCION: " + path1 +
			 * rb3.getString("imagen"));
			 */
			if (!reporteDAO.isWorking(login.getUsuarioId(), modulo)) {
				if (filtrobeanReporteEstXTutor.getFilEspecialidad().longValue() != -99)
					cod_docente = (!filtrobeanReporteEstXTutor.getDocente()
							.equals("-99") ? "_Tutor_"
							+ filtrobeanReporteEstXTutor.getDocente() : "");
				nombreReporte = cod_docente
						+ "_Fecha_"
						+ f2.toString().replace(' ', '_').replace(':', '-')
								.replace('.', '-');
				archivo = "Estudianes_x_Tutor_" + nombreReporte + ".pdf";
				archivozip = "Estudianes_x_Tutor_" + nombreReporte + ".zip";
				reporteDAO.ponerReporte(modulo, login.getUsuarioId(),
						rb3.getString("reporte.PathReporte") + archivozip + "",
						"zip", "" + archivozip, "0", "ReporteInsertarEstado");// Estado
																				// -1
				if (!reporteDAO.isEmptyEstXTutor(filtrobeanReporteEstXTutor)) {
					// System.out.println("Hay datos para ejecutar el jasper");
					bytes2 = reporteDAO.getArrayBytes(
							filtrobeanReporteEstXTutor.getInstitucion(),
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
							"No hay datos suficientes para generar el reporte plan de estudios");
					return false;
				}

			} else {
				request.setAttribute(
						"mensaje",
						"Usted ya mandn generar un reporte de Plan de estudios\nPor favor espere que termine, para solicitar uno nuevo");
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
