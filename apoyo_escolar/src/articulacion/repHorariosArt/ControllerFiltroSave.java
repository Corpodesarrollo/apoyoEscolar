package articulacion.repHorariosArt;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import siges.dao.*;
import siges.io.*;
import articulacion.repHorariosArt.vo.*;
import articulacion.repHorariosArt.dao.*;
import java.util.*;
import java.io.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import siges.login.beans.Login;

/**
 * Nombre: ControllerFiltroSave<BR>
 * Descripcinn: Controla la negociacion de busqueda para la vista de resultados
 * <BR>
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

	private final String modulo = "44";

	private final String moduloDoc = "45";

	private final String moduloEspacio = "46";

	private final String moduloEstudiante = "47";
	
	private final String moduloGrupos = "54";

	private ServletConfig config;

	private String s;

	private String s1;

	private String ant;

	private String er;

	private String sig;

	private String sig2;

	private String sig3;

	private String sig4;

	private String sig5;
	
	private String sig6;

	private String home;

	private File reportFile;

	private File reportFile2;

	private File reportFile3;

	private File reportFile4;

	private File reportFile5;

	private File reportFile6;

	private File reportFile7;
	
	private File reportFile8;
	
	private File reportFileGrupos;

	private String path;

	private String path1;

	private String path2;

	private Dao dao;

	private File escudo;

	private repHorariosArtDAO reporteDAO;

	/**
	 * Procesa la peticion HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */

	public String process(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		HttpSession session;
		session = request.getSession();
		rb3 = ResourceBundle
				.getBundle("articulacion.repHorariosArt.bundle.repHorariosArt");
		f2 = new java.sql.Timestamp(new java.util.Date().getTime());
		s = getServletConfig().getInitParameter("s");
		s1 = getServletConfig().getInitParameter("s1");
		sig = getServletConfig().getInitParameter("sig");// FiltroGrupo.jsp
		sig2 = getServletConfig().getInitParameter("sig2");// FiltroDocente.jsp
		sig3 = getServletConfig().getInitParameter("sig3");// FiltroEspacioFisico.jsp
		sig4 = getServletConfig().getInitParameter("sig4");// FiltroEstudiante.jsp
		sig5 = getServletConfig().getInitParameter("sig5");// FiltroEstudianteEst.jsp
		sig6 = getServletConfig().getInitParameter("sig6");// FiltroGruposArt.jsp
		
		ant = getServletConfig().getInitParameter("ant");
		er = getServletContext().getInitParameter("error");
		home = getServletContext().getInitParameter("home");
		err = false;

		if (request.getParameter("reporte") == null) {
			request.setAttribute("mensaje", "Error al mandar generar el tipo de reporte solicitado");
			ir(1, er, request, response);
			return er;
		}
		Login login=(Login) session.getAttribute("login");
		try {
			cursor = new Cursor();
			util = new Util(cursor);
			dao = new Dao(cursor);
			reporteDAO = new repHorariosArtDAO(cursor);
			String vigencia = dao.getVigencia();
			if (vigencia == null) {
				return er;
			}

			int requeridos = Integer.parseInt(request.getParameter("reporte"));
			// System.out.println("REPORTE REQUERIDO: "+requeridos);

			ServletContext context = (ServletContext) request.getSession().getServletContext();
			String contextoTotal = context.getRealPath("/");
			path = Ruta2.get(context.getRealPath("/"), rb3.getString("ruta_jaspers"));
			path1 = Ruta2.get(context.getRealPath("/"), rb3.getString("ruta_img"));
			path2 = Ruta2.get(context.getRealPath("/"), rb3.getString("ruta_img_inst"));

			reportFile = new File(path + rb3.getString("jasper_horario_grupo"));
			reportFile2 = new File(path+ rb3.getString("jasper_horario_docente"));
			reportFile3 = new File(path+ rb3.getString("jasper_horario_espacio"));
			reportFile4 = new File(path+ rb3.getString("jasper_horario_estudiante"));
			reportFile5 = new File(path+ rb3.getString("jasper_horarios_estudiantes"));
			reportFile6 = new File(path+ rb3.getString("jasper_horarios_docentes"));
			reportFile7 = new File(path+ rb3.getString("jasper_horarios_espacios"));
			reportFile8 = new File(path+ rb3.getString("jasper_horarios_grupos"));
			reportFileGrupos = new File(path+ rb3.getString("jasper_grupos_art"));

			switch (requeridos) {
			case 1:
				if (!horarioGrupo(login,session,request,response, vigencia, contextoTotal))
					return s;
				break;
			case 2:
				if (!horarioDocente(login,session,request,response, vigencia, contextoTotal))
					return s;
				break;
			case 3:
				if (!horarioEspacio(login,session,request,response, vigencia, contextoTotal))
					return s;
				break;
			case 4:
				if (!horarioEstudiante(login,session,request,response, vigencia, contextoTotal))
					return s;
				break;
			case 5:
				if (!reporteGruposArt(login,session,request,response, vigencia, contextoTotal))
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

	public boolean horarioGrupo(Login login,HttpSession session,HttpServletRequest request, HttpServletResponse response,String vigencia,
			String contextoTotal) throws ServletException, IOException {
		String sede = null, jornada = null, nombreReporte = null, cod_grupo = null;
		Map parameters = new HashMap();
		String archivosalida = null;
		int zise;
		Collection list = new ArrayList();
		Zip zip = new Zip();
		String archivo=null, archivozip=null;
		byte[] bytes;
		try {
			//System.out.println("*********////*********nENTRO A GENERAR HORARIO X GRUPO!**********//**********");
			/*
			if (!asignarBeanHorarioGrupo(login,session,request)) {
				setMensaje("Error capturando bean del reporte Grupo");
				request.setAttribute("mensaje", mensaje);
				ir(1, er, request, response);
				return false;
			}
			*/
			FiltroBeanReporte filtrobeanReporte = (FiltroBeanReporte) session.getAttribute("filtroGru");
			filtrobeanReporte.setInstitucion(new Long(login.getInstId()));
			filtrobeanReporte.setInstitucionDane(reporteDAO.getDaneInstitucion(new Long(login.getInstId())));

			/* PARAMETROS PARA el JASPER DE HORARIOS DE DOCENTE */
			parameters.put("INSTITUCION", new Long(login.getInstId()));
			parameters.put("SEDE", filtrobeanReporte.getSede());
			parameters.put("JORNADA", filtrobeanReporte.getJornada());
			parameters.put("ANO_VIGENCIA", filtrobeanReporte.getFilAnoVigenciaArt());
			parameters.put("PER_VIGENCIA", filtrobeanReporte.getFilPerVigenciaArt());
			parameters.put("COMPONENTE", filtrobeanReporte.getFilComponenteArt());
			if (filtrobeanReporte.getFilGrupo().longValue() != -99)
				parameters.put("GRUPO", filtrobeanReporte.getFilGrupo());
			parameters.put("USUARIO", login.getUsuario());
			escudo = new File(path2 + "e"+ filtrobeanReporte.getInstitucionDane() + ".gif");

			if (escudo.exists())
				parameters.put("PATH_ICONO_INSTITUCION", path2 + "e"+ filtrobeanReporte.getInstitucionDane()+ ".gif");
			else
				parameters.put("PATH_ICONO_INSTITUCION", path1+ rb3.getString("imagen"));

			parameters.put("PATH_ICONO_SECRETARIA", path1+ rb3.getString("imagen"));
			parameters.put("SUBREPORT_DIR", path);

			/*
			 * System.out.println("**PARAMETERS DEL JASPER**");
			 * System.out.println("INSTITUCION: "+new Long(login.getInstId()));
			 * System.out.println("SEDE:"+filtroBeanReporteDocente.getSede());
			 * System.out.println("JORNADA:
			 * "+filtroBeanReporteDocente.getJornada());
			 * System.out.println("ESPACIO_FISICO:
			 * "+filtroBeanReporteEspacio.getEspacioFisico());
			 * System.out.println("COD_ESTRUCTURA:
			 * "+filtroBeanReporteEspacio.getEstructura());
			 * System.out.println("USUARIO: "+login.getUsuario());
			 */
			escudo = new File(path2 + "e"
					+ filtrobeanReporte.getInstitucionDane() + ".gif");
			//if (escudo.exists())
				//System.out.println("PATH_ICONO_INSTITUCION: " + path2 + "e"+ filtrobeanReporte.getInstitucionDane()+ ".gif");
			//else
				//System.out.println("PATH_ICONO_INSTITUCION: " + path1+ rb3.getString("imagen"));

			// System.out.println("PATH_ICONO_SECRETARIA:
			// "+path1+rb3.getString("imagen"));
			// System.out.println("SUBREPORT_DIR: "+path);

			if (!reporteDAO.isWorking(login.getUsuarioId(), modulo)) {
				if (filtrobeanReporte.getFilGrupo().longValue() != -99) {// SI
																				// SELECCIONO
																				// GRUPO
					// System.out.println("******nNO HAY NINGnN REPORTE DE
					// HORARIOS POR ESTUDIANTE GENERANDOSE PARA ESTE USUARIO EN
					// ESTADO CERO!*********");
					sede = (!(filtrobeanReporte.getSede().intValue() == -9) ? "Sede_"+ filtrobeanReporte.getSede().intValue(): "");
					// System.out.println("*SEDE*"+sede);
					jornada = (!filtrobeanReporte.getJornada().equals("-9") ? "_Jornada_"+ filtrobeanReporte.getJornada() : "");
					// System.out.println("*JORNADA*"+jornada);
					cod_grupo = (!filtrobeanReporte.getFilGrupo().equals("-99") ? "_Grupo_"+ filtrobeanReporte.getFilGrupo() : "");
					nombreReporte = sede+ jornada+ cod_grupo+ "_Fecha_"+ f2.toString().replace(' ', '_').replace(':', '-').replace('.', '-');
					archivo = "Horario_por_Grupo_" + nombreReporte + ".pdf";
					archivozip = "Horario_por_Grupo_" + nombreReporte+ ".zip";reporteDAO.ponerReporte(modulo, login.getUsuarioId(),rb3.getString("reporte.PathReporte") + archivozip+ "", "zip", "" + archivozip, "0","ReporteInsertarEstado");// Estado -1
					if (!reporteDAO.isEmptyGrupo(filtrobeanReporte)) {
						//System.out.println("Hay datos para ejecutar el jasper");
						bytes = reporteDAO.getArrayBytes(filtrobeanReporte.getInstitucion(),login.getUsuarioId(), archivozip, reportFile,parameters, modulo);
						reporteDAO.updateReporteEstado(modulo, login.getUsuarioId(), rb3.getString("reporte.PathReporte")+ archivozip + "", "zip", "" + archivozip,"ReporteActualizarListo");
						reporteDAO.ponerArchivo(bytes, archivo, contextoTotal);
						archivosalida = Ruta.get(contextoTotal, rb3.getString("reportes.PathReporte"));
						// System.out.println("archivosalida: "+archivosalida);
						list.add(archivosalida + archivo);// pdf
						zise = 100000;
						if (zip.ponerZip(archivosalida, archivozip, zise, list)) {
							reporteDAO.updateReporteFecha("update_reporte_general", modulo,archivozip, login.getUsuarioId(), "1");
							return true;
						} else {
							reporteDAO.ponerReporteMensaje("4", modulo,login.getUsuarioId(), rb3.getString("reporte.PathReporte")+ archivozip + "", "zip", ""+ archivozip,"ReporteActualizarHorarioFallo","Ocurrin problema al hacer zip");
							reporteDAO.updateReporteFecha("update_reporte_general", modulo,archivozip, login.getUsuarioId(), "2");
							request.setAttribute("mensaje", "Error al crear zip");
							return false;
						}
					} else {
						////System.out.println("El Reporte esta vacio");
						// Insertarlo en la tabla REPORTE -- ESTADO 2
						reporteDAO.ponerReporteMensaje("2", modulo, login.getUsuarioId(), rb3.getString("reporte.PathReporte")+ archivozip + "", "zip", "" + archivozip,"ReporteActualizarHorarioFallo","Ocurrin problema en horario Grupo");
						reporteDAO.updateReporteFecha("update_reporte_general",	modulo, archivozip, login.getUsuarioId(),"2");
						request.setAttribute("mensaje", "No hay datos suficientes para generar el reporte horario por Grupo");
						return false;
					}
				} else {// SI NO SELECCIONO GRUPO (TODOS)
					//System.out.println("entro a horario de GRUPOS");
					sede = (!(filtrobeanReporte.getSede().intValue() == -9) ? "Sede_"+ filtrobeanReporte.getSede().intValue(): "");
					// //System.out.println("*SEDE*"+sede);
					jornada = (!filtrobeanReporte.getJornada().equals("-9") ? "_Jornada_"+ filtrobeanReporte.getJornada() : "");
					// //System.out.println("*JORNADA*"+jornada);

					nombreReporte = sede+ jornada+ "_Fecha_"+ f2.toString().replace(' ', '_').replace(':', '-').replace('.', '-');
					archivo = "Horarios_por_Grupos_" + nombreReporte + ".pdf";
					archivozip = "Horarios_por_Grupos_" + nombreReporte	+ ".zip";
					reporteDAO.ponerReporte(modulo, login.getUsuarioId(),rb3.getString("reporte.PathReporte") + archivozip+ "", "zip", "" + archivozip, "0","ReporteInsertarEstado");// Estado -1
					if (!reporteDAO.isEmptyGrupos(filtrobeanReporte)) {
						//System.out.println("Hay datos para ejecutar el jasper de grupos");
						bytes = reporteDAO.getArrayBytes(filtrobeanReporte.getInstitucion(),login.getUsuarioId(), archivozip, reportFile8,	parameters, modulo);
						reporteDAO.updateReporteEstado(modulo, login.getUsuarioId(), rb3.getString("reporte.PathReporte")+ archivozip + "", "zip", "" + archivozip,	"ReporteActualizarListo");
						reporteDAO.ponerArchivo(bytes, archivo, contextoTotal);
						archivosalida = Ruta.get(contextoTotal, rb3.getString("reportes.PathReporte"));
						//System.out.println("archivosalida: " + archivosalida);
						list.add(archivosalida + archivo);// pdf
						zise = 100000;
						if (zip.ponerZip(archivosalida, archivozip, zise, list)) {
							reporteDAO.updateReporteFecha("update_reporte_general", modulo,	archivozip, login.getUsuarioId(), "1");
							return true;
						} else {
							reporteDAO.ponerReporteMensaje("2", modulo,	login.getUsuarioId(), rb3.getString("reporte.PathReporte")+ archivozip + "", "zip", ""+ archivozip,"ReporteActualizarHorarioFallo",	"Ocurrin problema al hacer zip");
							reporteDAO.updateReporteFecha("update_reporte_general", modulo,archivozip, login.getUsuarioId(), "2");
							request.setAttribute("mensaje", "Error al hacer zip");
							return false;
						}
					} else {
						// //System.out.println("El Reporte esta vacio");
						// Insertarlo en la tabla REPORTE -- ESTADO 2
						reporteDAO.ponerReporteMensaje("2", modulo, login.getUsuarioId(), rb3.getString("reporte.PathReporte")+ archivozip + "", "zip", "" + archivozip,"ReporteActualizarHorarioFallo","Ocurrin problema en horario Grupos");
						reporteDAO.updateReporteFecha("update_reporte_general",	modulo, archivozip, login.getUsuarioId(),"2");
						request.setAttribute("mensaje", "No hay datos para generar el reporte");
						return false;
					}

				}
			} else {
				request.setAttribute("mensaje", "Usted ya mandn generar un reporte de Horarios por Grupo \nPor favor espere que termine, para solicitar uno nuevo");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			reporteDAO.ponerReporteMensaje("2", modulo,	login.getUsuarioId(), rb3.getString("reporte.PathReporte")+ archivozip + "", "zip", "" + archivozip,"ReporteActualizarBoletin",	"Ocurrin excepcinn en horario Grupo");
			return false;
		}
	}

	public boolean horarioDocente(Login login,HttpSession session,HttpServletRequest request,HttpServletResponse response, String vigencia,
			String contextoTotal) throws ServletException, IOException {
		String sede = null, jornada = null, nombreReporte = null, cod_docente = null;
		Map parameters = new HashMap();
		String archivosalida = null;
		int zise;
		Collection list = new ArrayList();
		Zip zip = new Zip();
		String archivo=null, archivozip=null;
		byte[] bytes, bytes2, bytes3, bytes4, bytes5;
		try {
			//System.out.println("*********////*********nENTRn A GENERAR HORARIO X DOCENTE!**********//**********");
			FiltroBeanReporteDocente filtroBeanReporteDocente = (FiltroBeanReporteDocente) session.getAttribute("filtroDoc");
			filtroBeanReporteDocente.setInstitucion(new Long(login.getInstId()));
			filtroBeanReporteDocente.setInstitucionDane(reporteDAO
					.getDaneInstitucion(new Long(login.getInstId())));

			/* PARAMETROS PARA el JASPER DE HORARIOS DE DOCENTE */
			parameters.put("INSTITUCION", new Long(login.getInstId()));
			parameters.put("SEDE", filtroBeanReporteDocente.getSede());
			parameters.put("JORNADA", filtroBeanReporteDocente.getJornada());
			parameters.put("ANO_VIGENCIA", filtroBeanReporteDocente
					.getFilAnoVigenciaArt());
			parameters.put("PER_VIGENCIA", filtroBeanReporteDocente
					.getFilPerVigenciaArt());
			parameters.put("COMPONENTE", filtroBeanReporteDocente
					.getFilComponenteArt());
			if (filtroBeanReporteDocente.getDocente().longValue() != -99)
				parameters.put("DOCENTE", filtroBeanReporteDocente.getDocente()
						.toString());
			parameters.put("USUARIO", login.getUsuario());
			escudo = new File(path2 + "e"
					+ filtroBeanReporteDocente.getInstitucionDane() + ".gif");

			if (escudo.exists())
				parameters.put("PATH_ICONO_INSTITUCION", path2 + "e"
						+ filtroBeanReporteDocente.getInstitucionDane()
						+ ".gif");
			else
				parameters.put("PATH_ICONO_INSTITUCION", path1
						+ rb3.getString("imagen"));

			parameters.put("PATH_ICONO_SECRETARIA", path1
					+ rb3.getString("imagen"));
			parameters.put("SUBREPORT_DIR", path);

			/*
			 * //System.out.println("**PARAMETERS DEL JASPER**");
			 * //System.out.println("INSTITUCION: "+new Long(login.getInstId()));
			 * //System.out.println("SEDE:"+filtroBeanReporteDocente.getSede());
			 * //System.out.println("JORNADA:
			 * "+filtroBeanReporteDocente.getJornada());
			 * //System.out.println("ESPACIO_FISICO:
			 * "+filtroBeanReporteEspacio.getEspacioFisico());
			 * //System.out.println("COD_ESTRUCTURA:
			 * "+filtroBeanReporteEspacio.getEstructura());
			 * //System.out.println("USUARIO: "+login.getUsuario());
			 */
			escudo = new File(path2 + "e"
					+ filtroBeanReporteDocente.getInstitucionDane() + ".gif");
			

			// //System.out.println("PATH_ICONO_SECRETARIA:
			// "+path1+rb3.getString("imagen"));
			// //System.out.println("SUBREPORT_DIR: "+path);

			if (!reporteDAO.isWorking(login.getUsuarioId(), moduloDoc)) {
				if (filtroBeanReporteDocente.getDocente().longValue() != -99) {// SI
																				// SELECCIONO
																				// DOCENTE
					// //System.out.println("******nNO HAY NINGnN REPORTE DE
					// HORARIOS POR ESTUDIANTE GENERANDOSE PARA ESTE USUARIO EN
					// ESTADO CERO!*********");
					sede = (!(filtroBeanReporteDocente.getSede().intValue() == -9) ? "Sede_"
							+ filtroBeanReporteDocente.getSede().intValue()
							: "");
					// //System.out.println("*SEDE*"+sede);
					jornada = (!filtroBeanReporteDocente.getJornada().equals("-9") ? "_Jornada_"+ filtroBeanReporteDocente.getJornada() : "");
					// //System.out.println("*JORNADA*"+jornada);
					cod_docente = (!filtroBeanReporteDocente.getDocente().equals("-99") ? "_Docente_"+ filtroBeanReporteDocente.getDocente() : "");
					nombreReporte = sede+ jornada+ cod_docente+ "_Fecha_"+ f2.toString().replace(' ', '_').replace(':', '-').replace('.', '-');
					archivo = "Horario_por_Docente_" + nombreReporte + ".pdf";
					archivozip = "Horario_por_Docente_" + nombreReporte+ ".zip";
					reporteDAO.ponerReporte(moduloDoc, login.getUsuarioId(),
							rb3.getString("reporte.PathReporte") + archivozip
									+ "", "zip", "" + archivozip, "0",
							"ReporteInsertarEstado");// Estado -1
					if (!reporteDAO.isEmptyDocente(filtroBeanReporteDocente)) {
						//System.out.println("Hay datos para ejecutar el jasper");
						bytes2 = reporteDAO.getArrayBytes(
								filtroBeanReporteDocente.getInstitucion(),
								login.getUsuarioId(), archivozip, reportFile2,
								parameters, moduloDoc);
						reporteDAO.updateReporteEstado(moduloDoc, login
								.getUsuarioId(), rb3
								.getString("reporte.PathReporte")
								+ archivozip + "", "zip", "" + archivozip,
								"ReporteActualizarListo");
						reporteDAO.ponerArchivo(bytes2, archivo, contextoTotal);
						archivosalida = Ruta.get(contextoTotal, rb3
								.getString("reportes.PathReporte"));
						// //System.out.println("archivosalida: "+archivosalida);
						list.add(archivosalida + archivo);// pdf
						zise = 100000;
						if (zip.ponerZip(archivosalida, archivozip, zise, list)) {
							reporteDAO.updateReporteFecha(
									"update_reporte_general", moduloDoc,
									archivozip, login.getUsuarioId(), "1");
							return true;
						} else {
							reporteDAO.ponerReporteMensaje("4", moduloDoc,
									login.getUsuarioId(), rb3
											.getString("reporte.PathReporte")
											+ archivozip + "", "zip", ""
											+ archivozip,
									"ReporteActualizarHorarioFallo",
									"Ocurrin problema al hacer zip");
							reporteDAO.updateReporteFecha(
									"update_reporte_general", moduloDoc,
									archivozip, login.getUsuarioId(), "2");
							request.setAttribute("mensaje", "Error al crear zip");
							return false;
						}
					} else {
						//System.out.println("El Reporte esta vacio");
						// Insertarlo en la tabla REPORTE -- ESTADO 2
						reporteDAO.ponerReporteMensaje("2", moduloDoc, login
								.getUsuarioId(), rb3
								.getString("reporte.PathReporte")
								+ archivozip + "", "zip", "" + archivozip,
								"ReporteActualizarHorarioFallo",
								"Ocurrin problema en horario Docente");
						reporteDAO.updateReporteFecha("update_reporte_general",
								moduloDoc, archivozip, login.getUsuarioId(),
								"2");
						request.setAttribute("mensaje", "No hay datos suficientes para generar el reporte horario por Docente");
						return false;
					}
				} else {// SI NO SELECCIONO DOCENTE (TODOS)
					//System.out.println("entro a horario de docentes");
					sede = (!(filtroBeanReporteDocente.getSede().intValue() == -9) ? "Sede_"
							+ filtroBeanReporteDocente.getSede().intValue()
							: "");
					// //System.out.println("*SEDE*"+sede);
					jornada = (!filtroBeanReporteDocente.getJornada().equals(
							"-9") ? "_Jornada_"
							+ filtroBeanReporteDocente.getJornada() : "");
					// //System.out.println("*JORNADA*"+jornada);

					nombreReporte = sede
							+ jornada
							+ "_Fecha_"
							+ f2.toString().replace(' ', '_').replace(':', '-')
									.replace('.', '-');
					archivo = "Horarios_por_Docentes_" + nombreReporte + ".pdf";
					archivozip = "Horarios_por_Docentes_" + nombreReporte
							+ ".zip";
					reporteDAO.ponerReporte(moduloDoc, login.getUsuarioId(),
							rb3.getString("reporte.PathReporte") + archivozip
									+ "", "zip", "" + archivozip, "0",
							"ReporteInsertarEstado");// Estado -1
					if (!reporteDAO.isEmptyDocentes(filtroBeanReporteDocente)) {
						//System.out.println("Hay datos para ejecutar el jasper de docentes");
						bytes2 = reporteDAO.getArrayBytes(
								filtroBeanReporteDocente.getInstitucion(),
								login.getUsuarioId(), archivozip, reportFile6,
								parameters, moduloDoc);
						reporteDAO.updateReporteEstado(moduloDoc, login
								.getUsuarioId(), rb3
								.getString("reporte.PathReporte")
								+ archivozip + "", "zip", "" + archivozip,
								"ReporteActualizarListo");
						reporteDAO.ponerArchivo(bytes2, archivo, contextoTotal);
						archivosalida = Ruta.get(contextoTotal, rb3
								.getString("reportes.PathReporte"));
						//System.out.println("archivosalida: " + archivosalida);
						list.add(archivosalida + archivo);// pdf
						zise = 100000;
						if (zip.ponerZip(archivosalida, archivozip, zise, list)) {
							reporteDAO.updateReporteFecha(
									"update_reporte_general", moduloDoc,
									archivozip, login.getUsuarioId(), "1");
							return true;
						} else {
							reporteDAO.ponerReporteMensaje("2", moduloDoc,
									login.getUsuarioId(), rb3
											.getString("reporte.PathReporte")
											+ archivozip + "", "zip", ""
											+ archivozip,
									"ReporteActualizarHorarioFallo",
									"Ocurrin problema al hacer zip");
							reporteDAO.updateReporteFecha(
									"update_reporte_general", moduloDoc,
									archivozip, login.getUsuarioId(), "2");
							request.setAttribute("mensaje", "Error al hacer zip");
							return false;
						}
					} else {
						// //System.out.println("El Reporte esta vacio");
						// Insertarlo en la tabla REPORTE -- ESTADO 2
						reporteDAO.ponerReporteMensaje("2", moduloDoc, login
								.getUsuarioId(), rb3
								.getString("reporte.PathReporte")
								+ archivozip + "", "zip", "" + archivozip,
								"ReporteActualizarHorarioFallo",
								"Ocurrin problema en horario Docentes");
						reporteDAO.updateReporteFecha("update_reporte_general",
								moduloDoc, archivozip, login.getUsuarioId(),
								"2");
						request.setAttribute("mensaje", "No hay datos para generar el reporte");
						return false;
					}

				}
			} else {
				//System.out.println("YA MANDO GENERAR");
				request.setAttribute("mensaje", "Usted ya mandn generar un reporte de Horarios por Docente \nPor favor espere que termine, para solicitar uno nuevo");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			reporteDAO.ponerReporteMensaje("2", moduloDoc,
					login.getUsuarioId(), rb3.getString("reporte.PathReporte")
							+ archivozip + "", "zip", "" + archivozip,
					"ReporteActualizarBoletin",
					"Ocurrin excepcinn en horario Docente");
			return false;
		}
	}

	public boolean horarioEspacio(Login login,HttpSession session,HttpServletRequest request,HttpServletResponse response, String vigencia,
			String contextoTotal) throws ServletException, IOException {
		String sede = null, jornada = null, nombreReporte = null, cod_espacio = null;
		Map parameters= new HashMap();
		String archivosalida = null;
		int zise;
		Collection list = new ArrayList();
		Zip zip = new Zip();
		String archivo=null, archivozip=null;
		byte[] bytes3;
		try {
			//System.out.println("*********////*********nENTRn A GENERAR HORARIO X ESPACIO!**********//**********");
			FiltroBeanReporteEspacio filtroBeanReporteEspacio = (FiltroBeanReporteEspacio) session.getAttribute("filtroEspacio");
			filtroBeanReporteEspacio.setInstitucion(new Long(login.getInstId()));
			filtroBeanReporteEspacio.setInstitucionDane(reporteDAO
					.getDaneInstitucion(new Long(login.getInstId())));

			/* PARAMETROS PARA el JASPER DE HORARIOS DE DOCENTE */
			parameters.put("INSTITUCION", new Long(login.getInstId()));
			parameters.put("SEDE", filtroBeanReporteEspacio.getSede());
			parameters.put("JORNADA", filtroBeanReporteEspacio.getJornada());
			parameters.put("ANO_VIGENCIA", filtroBeanReporteEspacio
					.getFilAnoVigenciaArt());
			parameters.put("PER_VIGENCIA", filtroBeanReporteEspacio
					.getFilPerVigenciaArt());
			parameters.put("COMPONENTE", filtroBeanReporteEspacio
					.getFilComponenteArt());
			if (filtroBeanReporteEspacio.getEspacio().longValue() != -99)
				parameters.put("ESPACIO", filtroBeanReporteEspacio.getEspacio());
			parameters.put("USUARIO", login.getUsuario());
			escudo = new File(path2 + "e"
					+ filtroBeanReporteEspacio.getInstitucionDane() + ".gif");

			if (escudo.exists())
				parameters.put("PATH_ICONO_INSTITUCION", path2 + "e"
						+ filtroBeanReporteEspacio.getInstitucionDane()
						+ ".gif");
			else
				parameters.put("PATH_ICONO_INSTITUCION", path1
						+ rb3.getString("imagen"));

			parameters.put("PATH_ICONO_SECRETARIA", path1
					+ rb3.getString("imagen"));
			parameters.put("SUBREPORT_DIR", path);

			/*
			 * //System.out.println("**PARAMETERS DEL JASPER**");
			 * //System.out.println("INSTITUCION: "+new Long(login.getInstId()));
			 * //System.out.println("SEDE:"+filtroBeanReporteDocente.getSede());
			 * //System.out.println("JORNADA:
			 * "+filtroBeanReporteDocente.getJornada());
			 * //System.out.println("ESPACIO_FISICO:
			 * "+filtroBeanReporteEspacio.getEspacioFisico());
			 * //System.out.println("COD_ESTRUCTURA:
			 * "+filtroBeanReporteEspacio.getEstructura());
			 * //System.out.println("USUARIO: "+login.getUsuario());
			 */
			escudo = new File(path2 + "e"
					+ filtroBeanReporteEspacio.getInstitucionDane() + ".gif");
			
			// //System.out.println("PATH_ICONO_SECRETARIA:
			// "+path1+rb3.getString("imagen"));
			// //System.out.println("SUBREPORT_DIR: "+path);

			if (!reporteDAO.isWorking(login.getUsuarioId(), moduloEspacio)) {
				if (filtroBeanReporteEspacio.getEspacio().longValue() != -99) {// SI
																				// SELECCIONO
																				// ESPACIO
																				// FISICO
					// //System.out.println("******nNO HAY NINGnN REPORTE DE
					// HORARIOS POR ESTUDIANTE GENERANDOSE PARA ESTE USUARIO EN
					// ESTADO CERO!*********");
					sede = (!(filtroBeanReporteEspacio.getSede().intValue() == -9) ? "Sede_"+ filtroBeanReporteEspacio.getSede().intValue():"");
					// //System.out.println("*SEDE*"+sede);
					jornada = (!filtroBeanReporteEspacio.getJornada().equals("-9") ? "_Jornada_"+ filtroBeanReporteEspacio.getJornada() : "");
					// //System.out.println("*JORNADA*"+jornada);
					cod_espacio = (!filtroBeanReporteEspacio.getEspacio().equals("-99") ? "_Espacio_"+ filtroBeanReporteEspacio.getEspacio() : "");
					nombreReporte = sede+jornada+ cod_espacio+ "_Fecha_"+ f2.toString().replace(' ', '_').replace(':', '-').replace('.', '-');
					archivo = "Horario_por_EspFisico_" + nombreReporte + ".pdf";
					archivozip = "Horario_por_EspFisico_" + nombreReporte+ ".zip";
					reporteDAO.ponerReporte(moduloEspacio,login.getUsuarioId(), rb3.getString("reporte.PathReporte")+ archivozip + "", "zip", "" + archivozip,"0", "ReporteInsertarEstado");// Estado -1
					if (!reporteDAO.isEmptyEspacio(filtroBeanReporteEspacio)) {
						//System.out.println("Hay datos para ejecutar el jasper");
						bytes3 = reporteDAO.getArrayBytes(filtroBeanReporteEspacio.getInstitucion(),login.getUsuarioId(), archivozip, reportFile3,parameters, moduloEspacio);
						reporteDAO.updateReporteEstado(moduloEspacio, login.getUsuarioId(), rb3.getString("reporte.PathReporte")+ archivozip + "", "zip", "" + archivozip,"ReporteActualizarListo");
						reporteDAO.ponerArchivo(bytes3, archivo, contextoTotal);
						archivosalida = Ruta.get(contextoTotal, rb3.getString("reportes.PathReporte"));
						// //System.out.println("archivosalida: "+archivosalida);
						list.add(archivosalida + archivo);// pdf
						zise = 100000;
						if (zip.ponerZip(archivosalida, archivozip, zise, list)) {
							reporteDAO.updateReporteFecha("update_reporte_general", moduloDoc,archivozip, login.getUsuarioId(), "1");							
							return true;
						} else {
							reporteDAO.ponerReporteMensaje("4", moduloEspacio,login.getUsuarioId(), rb3.getString("reporte.PathReporte")+ archivozip + "", "zip", ""+ archivozip,"ReporteActualizarHorarioFallo","Ocurrin problema al hacer zip");
							reporteDAO.updateReporteFecha("update_reporte_general", moduloEspacio,archivozip, login.getUsuarioId(), "2");
							request.setAttribute("mensaje", "Error al crear zip");
							return false;
						}
					} else {
						//System.out.println("El Reporte esta vacio");
						// Insertarlo en la tabla REPORTE -- ESTADO 2
						reporteDAO.ponerReporteMensaje("2", moduloEspacio,login.getUsuarioId(), rb3.getString("reporte.PathReporte")+ archivozip + "", "zip", ""+ archivozip,"ReporteActualizarHorarioFallo","Ocurrin problema en horario Espacio");
						reporteDAO.updateReporteFecha("update_reporte_general",moduloEspacio, archivozip,login.getUsuarioId(), "2");
						request.setAttribute("mensaje", "No hay datos suficientes para generar el reporte horario por Espacio Fisico");
						return false;
					}
				} else {// SI NO SELECCIONO DOCENTE (TODOS)
					//System.out.println("entro a horario de Espacios");
					sede = (!(filtroBeanReporteEspacio.getSede().intValue() == -9) ? "Sede_"+ filtroBeanReporteEspacio.getSede().intValue():"");
					// //System.out.println("*SEDE*"+sede);
					jornada = (!filtroBeanReporteEspacio.getJornada().equals("-9") ? "_Jornada_"+ filtroBeanReporteEspacio.getJornada() : "");
					// //System.out.println("*JORNADA*"+jornada);

					nombreReporte = sede+ jornada+ "_Fecha_"+ f2.toString().replace(' ', '_').replace(':', '-').replace('.', '-');
					archivo = "Horarios_por_EspaciosFisicos_" + nombreReporte+ ".pdf";
					archivozip = "Horarios_por_EspaciosFisicos_"+ nombreReporte + ".zip";
					reporteDAO.ponerReporte(moduloEspacio,login.getUsuarioId(), rb3.getString("reporte.PathReporte")+ archivozip + "", "zip", "" + archivozip,"0", "ReporteInsertarEstado");// Estado -1
					if (!reporteDAO.isEmptyEspacios(filtroBeanReporteEspacio)) {
						//System.out.println("Hay datos para ejecutar el jasper de docentes");
						bytes3 = reporteDAO.getArrayBytes(filtroBeanReporteEspacio.getInstitucion(),login.getUsuarioId(), archivozip, reportFile7,parameters, moduloEspacio);
						reporteDAO.updateReporteEstado(moduloEspacio, login.getUsuarioId(), rb3.getString("reporte.PathReporte")+ archivozip + "", "zip", "" + archivozip,"ReporteActualizarListo");
						reporteDAO.ponerArchivo(bytes3, archivo, contextoTotal);
						archivosalida = Ruta.get(contextoTotal, rb3.getString("reportes.PathReporte"));						
						//System.out.println("archivosalida: " + archivosalida);
						list.add(archivosalida + archivo);// pdf
						zise = 100000;
						if (zip.ponerZip(archivosalida, archivozip, zise, list)) {
							reporteDAO.updateReporteFecha("update_reporte_general", moduloEspacio,archivozip, login.getUsuarioId(), "1");
							return true;
						} else {
							reporteDAO.ponerReporteMensaje("2", moduloEspacio,login.getUsuarioId(), rb3.getString("reporte.PathReporte")+ archivozip + "", "zip", ""	+ archivozip,"ReporteActualizarHorarioFallo","Ocurrin problema al hacer zip");
							reporteDAO.updateReporteFecha("update_reporte_general", moduloEspacio,	archivozip, login.getUsuarioId(), "2");
							request.setAttribute("mensaje", "Error al hacer zip");
							return false;
						}
					} else {
						// //System.out.println("El Reporte esta vacio");
						// Insertarlo en la tabla REPORTE -- ESTADO 2
						reporteDAO.ponerReporteMensaje("2", moduloEspacio,login.getUsuarioId(), rb3.getString("reporte.PathReporte")+ archivozip + "", "zip", ""+ archivozip,"ReporteActualizarHorarioFallo","Ocurrin problema en horario Espacios Fisicos");
						reporteDAO.updateReporteFecha("update_reporte_general",moduloEspacio, archivozip,login.getUsuarioId(), "2");						
						request.setAttribute("mensaje", "No hay datos para generar el reporte");
						return false;
					}

				}
			} else {
				request.setAttribute("mensaje", "Usted ya mandn generar un reporte de Horarios por Espacio Fisico \nPor favor espere que termine, para solicitar uno nuevo");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			reporteDAO.ponerReporteMensaje("2", moduloEspacio,login.getUsuarioId(), rb3.getString("reporte.PathReporte")+ archivozip + "", "zip", "" + archivozip,"ReporteActualizarBoletin","Ocurrin excepcinn en horario Espacio");
			return false;
		}
	}

	/* FUNCION REPORTE ESTUDIANTE */
	public boolean horarioEstudiante(Login login,HttpSession session,HttpServletRequest request,HttpServletResponse response,
		String vigencia, String contextoTotal) throws ServletException,
		IOException {
			String sede = null, jornada = null, nombreReporte = null, cod_estudiante = null, cod_especialidad = null;
			Map parameters= new HashMap();
			String archivosalida = null;
			int zise;
			Collection list = new ArrayList();
			Zip zip = new Zip();
			String archivo=null, archivozip=null;
			byte[] bytes4;
		try {
			// //System.out.println("*********////*********nENTRn A GENERAR
			// HORARIO X ESTUDIANTE!**********//**********");
			FiltroBeanRepEstudiante filtrobeanRepEstudiante = (FiltroBeanRepEstudiante) session.getAttribute("filtroEst");
			filtrobeanRepEstudiante.setInstitucion(new Long(login.getInstId()));
			filtrobeanRepEstudiante.setInstitucionDane(reporteDAO
					.getDaneInstitucion(new Long(login.getInstId())));
			filtrobeanRepEstudiante.setVigencia(new Long(vigencia));

			/* PARAMETROS PARA el JASPER DE HORARIOS DE ESTUDIANTE */
			parameters.put("INSTITUCION", new Long(login.getInstId()));
			parameters.put("SEDE", filtrobeanRepEstudiante.getSede());
			parameters.put("JORNADA", filtrobeanRepEstudiante.getJornada());
			parameters.put("ESPECIALIDAD", filtrobeanRepEstudiante
					.getFilEspecialidad());
			parameters.put("ANO_VIGENCIA", filtrobeanRepEstudiante
					.getFilAnoVigenciaArt());
			parameters.put("PER_VIGENCIA", filtrobeanRepEstudiante
					.getFilPerVigenciaArt());
			parameters.put("COMPONENTE", filtrobeanRepEstudiante
					.getFilComponenteArt());
			if (filtrobeanRepEstudiante.getFilEstudiante().longValue() != -99)
				parameters.put("COD_ESTUDIANTE", filtrobeanRepEstudiante
						.getFilEstudiante());
			parameters.put("USUARIO", login.getUsuario());
			escudo = new File(path2 + "e"
					+ filtrobeanRepEstudiante.getInstitucionDane() + ".gif");

			if (escudo.exists())
				parameters
						.put("PATH_ICONO_INSTITUCION", path2 + "e"
								+ filtrobeanRepEstudiante.getInstitucionDane()
								+ ".gif");
			else
				parameters.put("PATH_ICONO_INSTITUCION", path1
						+ rb3.getString("imagen"));

			parameters.put("PATH_ICONO_SECRETARIA", path1
					+ rb3.getString("imagen"));
			parameters.put("SUBREPORT_DIR", path);

			/*
			 * //System.out.println("**PARAMETERS DEL JASPER**");
			 * //System.out.println("INSTITUCION: "+new Long(login.getInstId()));
			 * //System.out.println("SEDE:"+new Long(login.getSedeId()));
			 * //System.out.println("JORNADA: "+new Long(login.getJornadaId()));
			 * //System.out.println("ESPACIO_FISICO:
			 * "+filtroBeanReporteEspacio.getEspacioFisico());
			 * //System.out.println("COD_ESTRUCTURA:
			 * "+filtroBeanReporteEspacio.getEstructura());
			 * //System.out.println("USUARIO: "+login.getUsuario());
			 */
			escudo = new File(path2 + "e"
					+ filtrobeanRepEstudiante.getInstitucionDane() + ".gif");
			

			// //System.out.println("PATH_ICONO_SECRETARIA:
			// "+path1+rb3.getString("imagen"));
			// //System.out.println("SUBREPORT_DIR: "+path);

			if (!reporteDAO.isWorking(login.getUsuarioId(), moduloEstudiante)) {
				if (filtrobeanRepEstudiante.getFilEstudiante().longValue() != -99) {// SI
																					// SELECCIONO
																					// ESTUDIANTE
					// //System.out.println("******nNO HAY NINGnN REPORTE DE
					// HORARIOS POR ESTUDIANTE GENERANDOSE PARA ESTE USUARIO EN
					// ESTADO CERO!*********");
					sede = (!(filtrobeanRepEstudiante.getSede().intValue() == -9) ? "Sede_"
							+ filtrobeanRepEstudiante.getSede().intValue()
							: "");
					// //System.out.println("*SEDE*"+sede);
					jornada = (!filtrobeanRepEstudiante.getJornada().equals(
							"-9") ? "_Jornada_"
							+ filtrobeanRepEstudiante.getJornada() : "");
					// //System.out.println("*JORNADA*"+jornada);
					cod_estudiante = (!filtrobeanRepEstudiante
							.getFilEstudiante().equals("-99") ? "_Estudiante_"
							+ filtrobeanRepEstudiante.getFilEstudiante() : "");
					nombreReporte = sede
							+ jornada
							+ cod_estudiante
							+ "_Fecha_"
							+ f2.toString().replace(' ', '_').replace(':', '-')
									.replace('.', '-');
					archivo = "Horario_por_Estudiante_" + nombreReporte
							+ ".pdf";
					archivozip = "Horario_por_Estudiante_" + nombreReporte
							+ ".zip";
					reporteDAO.ponerReporte(moduloEstudiante, login
							.getUsuarioId(), rb3
							.getString("reporte.PathReporte")
							+ archivozip + "", "zip", "" + archivozip, "0",
							"ReporteInsertarEstado");// Estado -1
					if (!reporteDAO.isEmptyEstudiante(filtrobeanRepEstudiante)) {
						// //System.out.println("Hay datos para ejecutar el
						// jasper");
						bytes4 = reporteDAO.getArrayBytes(
								filtrobeanRepEstudiante.getInstitucion(), login
										.getUsuarioId(), archivozip,
								reportFile4, parameters, moduloEstudiante);
						reporteDAO.updateReporteEstado(moduloEstudiante, login
								.getUsuarioId(), rb3
								.getString("reporte.PathReporte")
								+ archivozip + "", "zip", "" + archivozip,
								"ReporteActualizarListo");
						reporteDAO.ponerArchivo(bytes4, archivo, contextoTotal);
						archivosalida = Ruta.get(contextoTotal, rb3
								.getString("reportes.PathReporte"));
						// //System.out.println("archivosalida: "+archivosalida);
						list.add(archivosalida + archivo);// pdf
						zise = 100000;
						if (zip.ponerZip(archivosalida, archivozip, zise, list)) {
							reporteDAO.updateReporteFecha(
									"update_reporte_general", moduloEstudiante,
									archivozip, login.getUsuarioId(), "1");
							return true;
						} else {
							reporteDAO.ponerReporteMensaje("4",
									moduloEstudiante, login.getUsuarioId(), rb3
											.getString("reporte.PathReporte")
											+ archivozip + "", "zip", ""
											+ archivozip,
									"ReporteActualizarHorarioFallo",
									"Ocurrin problema al hacer zip");
							reporteDAO.updateReporteFecha(
									"update_reporte_general", moduloEstudiante,
									archivozip, login.getUsuarioId(), "2");
							request.setAttribute("mensaje", "Error al crear zip");
							return false;
						}
					} else {
						// //System.out.println("El Reporte esta vacio");
						// Insertarlo en la tabla REPORTE -- ESTADO 2
						reporteDAO.ponerReporteMensaje("2", moduloEstudiante,
								login.getUsuarioId(), rb3
										.getString("reporte.PathReporte")
										+ archivozip + "", "zip", ""
										+ archivozip,
								"ReporteActualizarHorarioFallo",
								"Ocurrin problema en horarioEstudiante");
						reporteDAO.updateReporteFecha("update_reporte_general",
								moduloEstudiante, archivozip, login
										.getUsuarioId(), "2");
						request.setAttribute("mensaje", "No hay datos suficientes para generar el reporte horario por estudiante");
						return false;
					}
				} else {// SI NO SELECCIONO ESTUDIANTES (TODOS)
					// //System.out.println("entro a horario de estudiantes");
					sede = (!(filtrobeanRepEstudiante.getSede().intValue() == -9) ? "Sede_"
							+ filtrobeanRepEstudiante.getSede().intValue()
							: "");
					// //System.out.println("*SEDE*"+sede);
					jornada = (!filtrobeanRepEstudiante.getJornada().equals(
							"-9") ? "_Jornada_"
							+ filtrobeanRepEstudiante.getJornada() : "");
					// //System.out.println("*JORNADA*"+jornada);
					cod_especialidad = (!filtrobeanRepEstudiante
							.getFilEspecialidad().equals("-99") ? "_Especialidad_"
							+ filtrobeanRepEstudiante.getFilEspecialidad()
							: "");
					nombreReporte = sede
							+ jornada
							+ cod_especialidad
							+ "_Fecha_"
							+ f2.toString().replace(' ', '_').replace(':', '-')
									.replace('.', '-');
					archivo = "Horarios_por_Estudiantes_" + nombreReporte
							+ ".pdf";
					archivozip = "Horarios_por_Estudiantes_" + nombreReporte
							+ ".zip";
					reporteDAO.ponerReporte(moduloEstudiante, login
							.getUsuarioId(), rb3
							.getString("reporte.PathReporte")
							+ archivozip + "", "zip", "" + archivozip, "0",
							"ReporteInsertarEstado");// Estado -1
					if (!reporteDAO.isEmptyEstudiantes(filtrobeanRepEstudiante)) {
						//System.out.println("Hay datos para ejecutar el jasper de estudiantes");
						bytes4 = reporteDAO.getArrayBytes(
								filtrobeanRepEstudiante.getInstitucion(), login
										.getUsuarioId(), archivozip,
								reportFile5, parameters, moduloEstudiante);
						reporteDAO.updateReporteEstado(moduloEstudiante, login
								.getUsuarioId(), rb3
								.getString("reporte.PathReporte")
								+ archivozip + "", "zip", "" + archivozip,
								"ReporteActualizarListo");
						reporteDAO.ponerArchivo(bytes4, archivo, contextoTotal);
						archivosalida = Ruta.get(contextoTotal, rb3
								.getString("reportes.PathReporte"));
						//System.out.println("archivosalida: " + archivosalida);
						list.add(archivosalida + archivo);// pdf
						zise = 100000;
						if (zip.ponerZip(archivosalida, archivozip, zise, list)) {
							reporteDAO.updateReporteFecha(
									"update_reporte_general", moduloEstudiante,
									archivozip, login.getUsuarioId(), "1");
							return true;
						} else {
							reporteDAO.ponerReporteMensaje("4",
									moduloEstudiante, login.getUsuarioId(), rb3
											.getString("reporte.PathReporte")
											+ archivozip + "", "zip", ""
											+ archivozip,
									"ReporteActualizarHorarioFallo",
									"Ocurrin problema al hacer zip");
							reporteDAO.updateReporteFecha(
									"update_reporte_general", moduloEstudiante,
									archivozip, login.getUsuarioId(), "2");
							request.setAttribute("mensaje", "Error al hacer zip");
							return false;
						}
					} else {
						// //System.out.println("El Reporte esta vacio");
						// Insertarlo en la tabla REPORTE -- ESTADO 2
						reporteDAO.ponerReporteMensaje("2", moduloEstudiante,
								login.getUsuarioId(), rb3
										.getString("reporte.PathReporte")
										+ archivozip + "", "zip", ""
										+ archivozip,
								"ReporteActualizarHorarioFallo",
								"Ocurrin problema en horario Estudiantes");
						reporteDAO.updateReporteFecha("update_reporte_general",
								moduloEstudiante, archivozip, login
										.getUsuarioId(), "2");
						request.setAttribute("mensaje", "No hay datos para generar el reporte");
						return false;
					}

				}
			} else {
				request.setAttribute("mensaje", "Usted ya mandn generar un reporte de Horarios por Estudiante \nPor favor espere que termine, para solicitar uno nuevo");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			reporteDAO.ponerReporteMensaje("2", moduloEstudiante, login
					.getUsuarioId(), rb3.getString("reporte.PathReporte")
					+ archivozip + "", "zip", "" + archivozip,
					"ReporteActualizarBoletin",
					"Ocurrin excepcinn en horarioEspacio");
			return false;
		}
	}
	
	public boolean reporteGruposArt(Login login,HttpSession session,HttpServletRequest request, HttpServletResponse response,String vigencia,
			String contextoTotal) throws ServletException, IOException {
		String sede = null, jornada = null, nombreReporte = null, cod_grupo = null;
		Map parameters = new HashMap();
		String archivosalida = null;
		int zise;
		Collection list = new ArrayList();
		Zip zip = new Zip();
		String archivo=null, archivozip=null;
		byte[] bytes5;
		try {
			//System.out.println("*********////*********nENTRO A GENERAR REPORT GRUPOS ART!**********//**********");
			FiltroBeanReporteGrupos filtroBeanReporteGrupos = (FiltroBeanReporteGrupos) session.getAttribute("filtroGrupos");
			filtroBeanReporteGrupos.setInstitucion(new Long(login.getInstId()));
			filtroBeanReporteGrupos.setInstitucionDane(reporteDAO.getDaneInstitucion(new Long(login.getInstId())));

			/* PARAMETROS PARA el JASPER DE HORARIOS DE GRUPOS */
			parameters.put("INSTITUCION", new Long(login.getInstId()));
			parameters.put("SEDE", filtroBeanReporteGrupos.getSede());
			parameters.put("JORNADA", filtroBeanReporteGrupos.getJornada());
			parameters.put("ANO_VIGENCIA", filtroBeanReporteGrupos.getFilAnoVigenciaArt());
			parameters.put("PER_VIGENCIA", filtroBeanReporteGrupos.getFilPerVigenciaArt());
			parameters.put("COMPONENTE", filtroBeanReporteGrupos.getFilComponenteArt());
			if (filtroBeanReporteGrupos.getFilComponenteArt().longValue() == 2)
				parameters.put("ESPECIALIDAD", filtroBeanReporteGrupos.getFilEspecialidad());
			else
				parameters.put("ESPECIALIDAD", new Long(-99));
			parameters.put("USUARIO", login.getUsuario());
			escudo = new File(path2 + "e"+ filtroBeanReporteGrupos.getInstitucionDane() + ".gif");

			if (escudo.exists())
				parameters.put("PATH_ICONO_INSTITUCION", path2 + "e"+ filtroBeanReporteGrupos.getInstitucionDane()+ ".gif");
			else
				parameters.put("PATH_ICONO_INSTITUCION", path1+ rb3.getString("imagen"));

			parameters.put("PATH_ICONO_SECRETARIA", path1+ rb3.getString("imagen"));
			parameters.put("SUBREPORT_DIR", path);

			/*
			 * //System.out.println("**PARAMETERS DEL JASPER**");
			 * //System.out.println("INSTITUCION: "+new Long(login.getInstId()));
			 * //System.out.println("SEDE:"+filtroBeanReporteGruposDocente.getSede());
			 * //System.out.println("JORNADA:
			 * "+filtroBeanReporteGruposDocente.getJornada());
			 * //System.out.println("ESPACIO_FISICO:
			 * "+filtroBeanReporteGruposEspacio.getEspacioFisico());
			 * //System.out.println("COD_ESTRUCTURA:
			 * "+filtroBeanReporteGruposEspacio.getEstructura());
			 * //System.out.println("USUARIO: "+login.getUsuario());
			 */
			escudo = new File(path2 + "e"+ filtroBeanReporteGrupos.getInstitucionDane() + ".gif");
			

			// //System.out.println("PATH_ICONO_SECRETARIA:
			// "+path1+rb3.getString("imagen"));
			// //System.out.println("SUBREPORT_DIR: "+path);

			if (!reporteDAO.isWorking(login.getUsuarioId(), moduloGrupos)) {				
					// //System.out.println("******nNO HAY NINGnN REPORTE DE
					// HORARIOS POR ESTUDIANTE GENERANDOSE PARA ESTE USUARIO EN
					// ESTADO CERO!*********");
					sede = (!(filtroBeanReporteGrupos.getSede().intValue() == -9) ? "Sede_"+ filtroBeanReporteGrupos.getSede().intValue(): "");
					// //System.out.println("*SEDE*"+sede);
					jornada = (!filtroBeanReporteGrupos.getJornada().equals("-9") ? "_Jornada_"+ filtroBeanReporteGrupos.getJornada() : "");
					// //System.out.println("*JORNADA*"+jornada);
					cod_grupo = (!filtroBeanReporteGrupos.getFilEspecialidad().equals("-99") ? "_Espacialidad_"+ filtroBeanReporteGrupos.getFilEspecialidad() : "");
					nombreReporte = sede+ jornada+ cod_grupo+ "_Fecha_"+ f2.toString().replace(' ', '_').replace(':', '-').replace('.', '-');
					archivo = "Grupos_Articulacion_" + nombreReporte + ".pdf";
					archivozip = "Grupos_Articulacion_" + nombreReporte+ ".zip";reporteDAO.ponerReporte(moduloGrupos, login.getUsuarioId(),rb3.getString("reporte.PathReporte") + archivozip+ "", "zip", "" + archivozip, "0","ReporteInsertarEstado");// Estado -1
					if (!reporteDAO.isEmptyGruposArt(filtroBeanReporteGrupos)) {
						////System.out.println("Hay datos para ejecutar el jasper ga");
						bytes5 = reporteDAO.getArrayBytes(filtroBeanReporteGrupos.getInstitucion(),login.getUsuarioId(), archivozip, reportFileGrupos,parameters, moduloGrupos);
						reporteDAO.updateReporteEstado(moduloGrupos, login.getUsuarioId(), rb3.getString("reporte.PathReporte")+ archivozip + "", "zip", "" + archivozip,"ReporteActualizarListo");
						reporteDAO.ponerArchivo(bytes5, archivo, contextoTotal);
						archivosalida = Ruta.get(contextoTotal, rb3.getString("reportes.PathReporte"));
						// //System.out.println("archivosalida: "+archivosalida);
						list.add(archivosalida + archivo);// pdf
						zise = 100000;
						if (zip.ponerZip(archivosalida, archivozip, zise, list)) {
							reporteDAO.updateReporteFecha("update_reporte_general", moduloGrupos,archivozip, login.getUsuarioId(), "1");
							return true;
						} else {
							reporteDAO.ponerReporteMensaje("4", moduloGrupos,login.getUsuarioId(), rb3.getString("reporte.PathReporte")+ archivozip + "", "zip", ""+ archivozip,"ReporteActualizarHorarioFallo","Ocurrin problema al hacer zip");
							reporteDAO.updateReporteFecha("update_reporte_general", moduloGrupos,archivozip, login.getUsuarioId(), "2");
							request.setAttribute("mensaje", "Error al crear zip");
							return false;
						}
					} else {
						//System.out.println("El Reporte esta vacio");
						// Insertarlo en la tabla REPORTE -- ESTADO 2
						reporteDAO.ponerReporteMensaje("2", moduloGrupos, login.getUsuarioId(), rb3.getString("reporte.PathReporte")+ archivozip + "", "zip", "" + archivozip,"ReporteActualizarHorarioFallo","Ocurrin problema en horario Grupo");
						reporteDAO.updateReporteFecha("update_reporte_general",	moduloGrupos, archivozip, login.getUsuarioId(),"2");
						request.setAttribute("mensaje", "No hay datos suficientes para generar el reporte horario por Grupo");
						return false;
					}
				
			} else {
				//System.out.println("YA MANDO GENERAR");
				request.setAttribute("mensaje", "Usted ya mandn generar un reporte de Horarios por Grupo \nPor favor espere que termine, para solicitar uno nuevo");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			reporteDAO.ponerReporteMensaje("2", moduloGrupos,	login.getUsuarioId(), rb3.getString("reporte.PathReporte")+ archivozip + "", "zip", "" + archivozip,"ReporteActualizarBoletin",	"Ocurrin excepcinn en horario Grupo");
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
	 * @param int
	 *            a: 1=redirigir como 'include', 2=redirigir como 'forward'
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
	 * 
	 */
	public void destroy() {
		if (cursor != null)
			cursor.cerrar();
		cursor = null;
	}

}
