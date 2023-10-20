package siges.plantilla.evaluacion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import siges.dao.Ruta;
import siges.dao.Ruta2;
import siges.exceptions.InternalErrorException;
import siges.io.Zip;
import siges.login.beans.Login;
import siges.plantilla.Excel;
import siges.plantilla.beans.DimensionesVO;
import siges.plantilla.beans.FiltroPlantilla;
import siges.plantilla.beans.ParamsVO;
import siges.plantilla.dao.PlantillaDAO;
import siges.util.Logger;
import siges.util.Properties;
import siges.util.beans.ReporteVO;

/**
 * siges.plantilla.evaluacion<br>
 * Funcinn: Recibe el formulario de solicitud de plantilla de evaluacion de
 * estudiantes y llama los servicios necesarios para generar la plantilla. <br>
 */
public class ControllerPlantillaSave extends HttpServlet {
	private Cursor cursor;

	private PlantillaDAO plantillaDAO;

	private String mensaje;

	private ResourceBundle rb;

	private Timestamp f2;

	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * <br>
	 *             Return Type: String<br>
	 *             Version 1.1.<br>
	 */
	public String process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		f2 = new Timestamp(new java.util.Date().getTime());
		rb = ResourceBundle.getBundle("siges.plantilla.bundle.plantilla");
		String[] ubicacion;
		ubicacion = new String[3];
		String sig, sig2, sig3, ant, er, home, boton;
		sig = getServletConfig().getInitParameter("sig");
		sig2 = getServletConfig().getInitParameter("sig2");
		sig3 = getServletConfig().getInitParameter("sig3");
		ant = getServletConfig().getInitParameter("ant");
		er = getServletContext().getInitParameter("error");
		home = getServletContext().getInitParameter("home");
		cursor = new Cursor();
		mensaje = null;
		plantillaDAO = new PlantillaDAO(cursor);
		boton = (request.getParameter("cmd") != null) ? request
				.getParameter("cmd") : new String("");
		if (!asignarBeans(request)) {
			setMensaje("Error capturando datos de sesinn para el usuario");
			request.setAttribute("mensaje", mensaje);
			return er;
		}
		FiltroPlantilla filtroPlantilla = (FiltroPlantilla) session
				.getAttribute("filtroPlantilla");
		// System.out.println("Nombre de docentes: "+filtroPlantilla.getDocente_());
		Login login = (Login) session.getAttribute("login");
		if (request.getParameter("tipo") == null
				|| request.getParameter("tipo").equals("")) {
			setMensaje("Acceso denegado no hay una ficha definida");
			request.setAttribute("mensaje", getMensaje());
			return er;
		}
		int tipo = Integer.parseInt((String) request.getParameter("tipo"));
		if (boton.equals("Buscar")) {
			String alert = "Las plantillas se estan generando en este momento. \nPulse en el hipervinculo 'Listado de Reportes' para ver si el reporte ya fue generado. \no vaya a la opción de menu 'Reportes generados'";
			ubicacion = getUbicacionZip(login.getUsuarioId(), f2, tipo,
					filtroPlantilla);
//			System.out.println("en plantilla EVALUACION TIPO: " + tipo);
			switch (tipo) {
			case Properties.PLANTILLALOGROASIG:// asignatura
				if (filtroPlantilla.getMetodologia().equals("-3")
						|| filtroPlantilla.getGrado().equals("-3")
						|| filtroPlantilla.getGrupo().equals("-3")
						|| filtroPlantilla.getAsignatura().equals("-3")) {
					try {
						plantillaDAO.getEscalaNivelEval(filtroPlantilla);
						if (filtroPlantilla.getRangosCompletos() != 1) {
							setMensaje(" No se puede generar la plantilla porque ann no se "
									+ "\n ha especificado n esta incompleta la escala valorativa"
									+ "\n para este grupo.");
							request.setAttribute("mensaje", mensaje);
							return (sig += "?tipo=" + tipo);
						}
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println(e.getMessage());
					}

					if (plantillaDAO.insertarPlantillas(login, filtroPlantilla,
							tipo, ubicacion)) {
						// plantillaDAO.setReporteZip(login.getUsuarioId(),ubicacion[1]+ubicacion[0],"zip","Plantillas
						// de Evaluacinn de Asignatura
						// "+f2.toString().replace(':','-').replace('.','-').substring(0,10)+"_H_"+f2.toString().replace(':','-').replace('.','-').substring(11,19),2,0);
						plantillaDAO.setReporteZip(login.getUsuarioId(),
								ubicacion[1] + ubicacion[0], "zip",
								ubicacion[0], 2, 0);
					} else {
						setMensaje(plantillaDAO.getMensaje());
						request.setAttribute("mensaje", getMensaje());
					}

					ReporteVO reporteVO = new ReporteVO();
					reporteVO.setRepTipo(ReporteVO._REP_PLANTILLA_MASIVAS);
					reporteVO.setRepOrden(ReporteVO.ORDEN_DESC);
					request.getSession().setAttribute("reporteVO", reporteVO);

					request.setAttribute("plantilla", "_");
					setMensaje(alert);
					request.setAttribute("mensaje", getMensaje());
					request.getSession().removeAttribute("filtroPlantilla");
				} else {
					try {
						plantillaDAO.getEscalaNivelEval(filtroPlantilla);
						if (filtroPlantilla.getRangosCompletos() != 1) {
							setMensaje(" No se puede generar la plantilla porque ann no se "
									+ "\n ha especificado n esta incompleta la escala valorativa"
									+ "\n para este grupo.");
							request.setAttribute("mensaje", mensaje);
							return (sig += "?tipo=" + tipo);
						}
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println(e.getMessage());
					}

					asignarListaAsignatura(request, login, filtroPlantilla);
					// asignarListaRecuperacion(request,login,filtroPlantilla);
				}
				return (sig += "?tipo=" + tipo);
			case Properties.PLANTILLAAREADESC:// area
				if (filtroPlantilla.getMetodologia().equals("-3")
						|| filtroPlantilla.getGrado().equals("-3")
						|| filtroPlantilla.getGrupo().equals("-3")
						|| filtroPlantilla.getArea().equals("-3")) {
					try {

						try {
							plantillaDAO.getEscalaNivelEval(filtroPlantilla);
							if (filtroPlantilla.getRangosCompletos() != 1) {
								setMensaje(" No se puede generar la plantilla porque ann no se "
										+ "\n ha especificado n esta incompleta la escala valorativa"
										+ "\n para este grupo.");
								request.setAttribute("mensaje", mensaje);
								return (sig += "?tipo=" + tipo);
							}
						} catch (Exception e) {
							e.printStackTrace();
							System.out.println(e.getMessage());
						}

						if (plantillaDAO.insertarPlantillas(login,
								filtroPlantilla, tipo, ubicacion)) {
							plantillaDAO.setReporteZip(login.getUsuarioId(),
									ubicacion[1] + ubicacion[0], "zip",
									ubicacion[0], 2, 0);
						} else {
							setMensaje(plantillaDAO.getMensaje());
							request.setAttribute("mensaje", getMensaje());
						}
						ReporteVO reporteVO = new ReporteVO();
						reporteVO.setRepTipo(ReporteVO._REP_PLANTILLA_MASIVAS);
						reporteVO.setRepOrden(ReporteVO.ORDEN_DESC);
						request.getSession().setAttribute("reporteVO",
								reporteVO);
						request.setAttribute("plantilla", "_");
						setMensaje(alert);
						request.setAttribute("mensaje", getMensaje());
						request.getSession().removeAttribute("filtroPlantilla");
					} catch (Exception e) {
						request.setAttribute("mensaje",
								"Error generando plantillas: " + e);
						request.getSession().removeAttribute("filtroPlantilla");
					}
				} else {
					asignarListaArea(request, login, filtroPlantilla);
					request.setAttribute("mensaje", getMensaje());
				}
				return (sig2 += "?tipo=" + tipo);
			case Properties.PLANTILLAPREE:// preescolar
				if (filtroPlantilla.getMetodologia().equals("-3")
						|| filtroPlantilla.getGrado().equals("-3")
						|| filtroPlantilla.getGrupo().equals("-3")) {
					try {

						if (plantillaDAO.insertarPlantillas(login,
								filtroPlantilla, tipo, ubicacion)) {
							plantillaDAO.setReporteZip(login.getUsuarioId(),
									ubicacion[1] + ubicacion[0], "zip",
									ubicacion[0], 2, 0);
						} else {
							setMensaje(plantillaDAO.getMensaje());
							request.setAttribute("mensaje", getMensaje());
						}
						ReporteVO reporteVO = new ReporteVO();
						reporteVO.setRepTipo(ReporteVO._REP_PLANTILLA_MASIVAS);
						reporteVO.setRepOrden(ReporteVO.ORDEN_DESC);
						request.getSession().setAttribute("reporteVO",
								reporteVO);
						request.setAttribute("plantilla", "_");
						setMensaje(alert);
						request.setAttribute("mensaje", getMensaje());
						request.getSession().removeAttribute("filtroPlantilla");
					} catch (Exception e) {
						request.setAttribute("mensaje",
								"Error generando plantillas: " + e);
						request.getSession().removeAttribute("filtroPlantilla");
					}
				} else
					asignarListaPreescolar(request, login, filtroPlantilla);
				return (sig3 += "?tipo=" + tipo);
			case Properties.PLANTILLARECUPERACION:
				if (filtroPlantilla.getMetodologia().equals("-3")
						|| filtroPlantilla.getGrado().equals("-3")
						|| filtroPlantilla.getGrupo().equals("-3")
						|| filtroPlantilla.getAsignatura().equals("-3")) {
					if (plantillaDAO.insertarPlantillas(login, filtroPlantilla,
							tipo, ubicacion)) {
						plantillaDAO.setReporteZip(login.getUsuarioId(),
								ubicacion[1] + ubicacion[0], "zip",
								ubicacion[0], 2, 0);
					} else {
						setMensaje(plantillaDAO.getMensaje());
						request.setAttribute("mensaje", getMensaje());
					}
					ReporteVO reporteVO = new ReporteVO();
					reporteVO.setRepTipo(ReporteVO._REP_PLANTILLA_MASIVAS);
					reporteVO.setRepOrden(ReporteVO.ORDEN_DESC);
					request.getSession().setAttribute("reporteVO", reporteVO);
					request.setAttribute("plantilla", "_");
					setMensaje(alert);
					request.setAttribute("mensaje", getMensaje());
					request.getSession().removeAttribute("filtroPlantilla");
				} else {
					asignarListaRecuperacion(request, login, filtroPlantilla);
				}
				return (sig += "?tipo=" + tipo);
			}
		}
		if (boton.equals("Cancelar")) {
			borrarBeans(request);
			sig = home;
		}
		return sig;
	}

	/**
	 * @param request
	 * <br>
	 *            Return Type: void<br>
	 *            Version 1.1.<br>
	 */
	public static void borrarBeans(HttpServletRequest request) {
		request.getSession().removeAttribute("filtroPlantilla");
	}

	/**
	 * @param request
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * <br>
	 *             Return Type: boolean<br>
	 *             Version 1.1.<br>
	 */
	public boolean asignarBeans(HttpServletRequest request)
			throws ServletException, IOException {
		HttpSession sess = request.getSession();
		if (sess.getAttribute("login") == null)
			return false;
		if (sess.getAttribute("filtroPlantilla") == null)
			return false;
		return true;
	}

	/**
	 * @param id
	 * @param t
	 * @return<br> Return Type: String[]<br>
	 *             Version 1.1.<br>
	 */
	public String[] getUbicacionZip(String id, Timestamp t, int tipo,
			FiltroPlantilla filtroPlantilla) {
		String path;
		String archivo = null;
		String relativo = null;
		String ruta = null;
		String[] ubicacion = new String[3];
		String parametros = "_Met_" + filtroPlantilla.getMetodologia()
				+ "_Gra_" + filtroPlantilla.getGrado();
		if (!filtroPlantilla.getGrupo().equals("-3")) {
			parametros += "_Grupo_" + filtroPlantilla.getGrupo_();
		}
		switch (tipo) {
		case Properties.PLANTILLALOGROASIG:
			if (!filtroPlantilla.getAsignatura().equals("-3")) {
				parametros += "_Asig_"
						+ formatear(filtroPlantilla.getAsignaturaAbre_());
			}
			parametros += "_Per_" + filtroPlantilla.getPeriodo();
			path = rb.getString("asignatura.PathAsignatura") + "." + id;
			archivo = "EvaluacionAsignatura_"
					+ parametros
					+ "["
					+ t.toString().replace(':', '-').replace('.', '-')
							.replace(' ', '_') + "].zip";
			relativo = Ruta.getRelativo(getServletContext(), path);
			ruta = Ruta.get(getServletContext(), path);// path del nuevo
			// archivo
			break;
		case Properties.PLANTILLAAREADESC:
			if (!filtroPlantilla.getArea().equals("-3")) {
				parametros += "_Area_" + formatear(filtroPlantilla.getArea_());
			}
			parametros += "_Per_" + filtroPlantilla.getPeriodo();
			path = rb.getString("area.PathArea") + "." + id;
			archivo = "EvaluacionArea_"
					+ parametros
					+ "["
					+ t.toString().replace(':', '-').replace('.', '-')
							.replace(' ', '_') + ".zip";
			relativo = Ruta.getRelativo(getServletContext(), path);
			ruta = Ruta.get(getServletContext(), path);// path del nuevo
			// archivo
			break;
		case Properties.PLANTILLAPREE:
			parametros += "_Per_" + filtroPlantilla.getPeriodo();
			path = rb.getString("preescolar.PathPreescolar") + "." + id;
			archivo = "EvaluacionPreescolar_"
					+ parametros
					+ "["
					+ t.toString().replace(':', '-').replace('.', '-')
							.replace(' ', '_') + ".zip";
			relativo = Ruta.getRelativo(getServletContext(), path);
			ruta = Ruta.get(getServletContext(), path);// path del nuevo
			// archivo
			break;
		case Properties.PLANTILLARECUPERACION:
			if (!filtroPlantilla.getAsignatura().equals("-3")) {
				parametros += "_Asig_"
						+ formatear(filtroPlantilla.getAsignatura_());
			}
			parametros += "_Per_" + filtroPlantilla.getPeriodo();
			path = rb.getString("recuperacion.PathRecuperacion") + "." + id;
			archivo = "RecuperacionLogro_"
					+ parametros
					+ "["
					+ t.toString().replace(':', '-').replace('.', '-')
							.replace(' ', '_') + ".zip";
			relativo = Ruta.getRelativo(getServletContext(), path);
			ruta = Ruta.get(getServletContext(), path);// path del nuevo
			// archivo
			break;
		}
		ubicacion[0] = archivo;
		ubicacion[1] = relativo;
		ubicacion[2] = ruta;
		return ubicacion;
	}

	/**
	 * @param filtro
	 * @param list
	 * @param id
	 * @param ubi
	 * @return<br> Return Type: String<br>
	 *             Version 1.1.<br>
	 */
	public String ponerZip(FiltroPlantilla filtro, Collection list, String id,
			String[] ubi, int tipo) {
		Zip zip = new Zip();
		String path;
		String archivo;
		String relativo;
		String ruta;
		int zise;
		switch (tipo) {
		case Properties.PLANTILLALOGROASIG:
			zise = 100000;
			if (zip.ponerZip(ubi[2], ubi[0], zise, list)) {
				Logger.print(
						id,
						"Plantilla (Zip) de Asignatura Inst:"
								+ filtro.getInstitucion() + " Sede:"
								+ filtro.getSede() + " Jorn:"
								+ filtro.getJornada() + " Per:"
								+ filtro.getPeriodo(),
						"Generar Plantillas de evaluacinn de Logros/Asignatura: Periodo '"
								+ filtro.getPeriodo_() + "'",
						filtro.getPeriodo(), 3, 1, this.toString());
				plantillaDAO.setReporteActualizar(id, ubi[1] + ubi[0], 1,
						"Listo");
				return ubi[1] + ubi[0];
			} else {
				plantillaDAO.setReporteActualizar(id, ubi[1] + ubi[0], 2,
						"Error Generando archivo Zip" + zip.getMensaje());
			}
			break;
		case Properties.PLANTILLAAREADESC:
			zise = 100000;
			if (zip.ponerZip(ubi[2], ubi[0], zise, list)) {
				Logger.print(
						id,
						"Plantilla (Zip)de Area Inst:"
								+ filtro.getInstitucion() + " Sede:"
								+ filtro.getSede() + " Jorn:"
								+ filtro.getJornada() + " Per:"
								+ filtro.getPeriodo(),
						"Generar Plantillas de evaluacinn de Area/Descriptor: Periodo '"
								+ filtro.getPeriodo_() + "'",
						filtro.getPeriodo(), 3, 1, this.toString());
				plantillaDAO.setReporteActualizar(id, ubi[1] + ubi[0], 1,
						"Listo");
				return ubi[1] + ubi[0];
			} else {
				plantillaDAO.setReporteActualizar(id, ubi[1] + ubi[0], 2,
						"Error Generando archivo Zip" + zip.getMensaje());
			}
			break;
		case Properties.PLANTILLAPREE:
			zise = 100000;
			if (zip.ponerZip(ubi[2], ubi[0], zise, list)) {
				Logger.print(
						id,
						"Plantilla (Zip)de Preescolar Inst:"
								+ filtro.getInstitucion() + " Sede:"
								+ filtro.getSede() + " Jorn:"
								+ filtro.getJornada() + " Per:"
								+ filtro.getPeriodo(),
						"Generar Plantillas de evaluacinn de Preescolar Periodo '"
								+ filtro.getPeriodo_() + "'",
						filtro.getPeriodo(), 3, 1, this.toString());
				plantillaDAO.setReporteActualizar(id, ubi[1] + ubi[0], 1,
						"Listo");
				return ubi[1] + ubi[0];
			} else {
				plantillaDAO.setReporteActualizar(id, ubi[1] + ubi[0], 2,
						"Error Generando archivo Zip" + zip.getMensaje());
			}
			break;
		}
		return "--";
	}

	/**
	 * @param request
	 * @throws ServletException
	 * @throws IOException
	 * <br>
	 *             Return Type: void<br>
	 *             Version 1.1.<br>
	 */
	public void asignarListaArea(HttpServletRequest request, Login login,
			FiltroPlantilla filtroPlantilla) throws ServletException,
			IOException {
		String s;
		Collection[] col = new Collection[6];
		try {
			/*
			 * 0=Estidiantes 1=Esala Area 2=NOtas Area 3=ausencia Area 4=Lista
			 * Descriptores 5=Notas Descriptor
			 */
			String per = filtroPlantilla.getPeriodoAbrev();
			String area = filtroPlantilla.getArea().substring(
					filtroPlantilla.getArea().lastIndexOf('|') + 1,
					filtroPlantilla.getArea().length());
			String grupo = filtroPlantilla.getGrupo().substring(
					filtroPlantilla.getGrupo().lastIndexOf("|") + 1,
					filtroPlantilla.getGrupo().length());
			// ESTUDIANTES
			col[0] = plantillaDAO.getEstudiantes(filtroPlantilla);
			// JERARQUIA DEL GRUPO AL QUE PERTENECE
			filtroPlantilla.setJerarquiagrupo(plantillaDAO
					.getJerarquiaGrupo(col[0]));
			// VALIDACION DE CIERRE DEL AREA
			filtroPlantilla.setCerrar(plantillaDAO
					.getEstadoNotasArea(filtroPlantilla));
			// NOTAS
			/**
			 * MODIFICADO 23-03-10
			 * **/
			// col[1] = plantillaDAO.getEscala(2);
			col[1] = plantillaDAO.getEscalaNivelEval(filtroPlantilla);
			/**
			 * FIN
			 * */
			// NOTAS ACTUALES
			Collection Coltemp = plantillaDAO.getNotasArea(filtroPlantilla);

			// Si es conceptual tenemos que buscar que equivalencia en
			// letras
			if (filtroPlantilla.getFilCodTipoEval() == ParamsVO.ESCALA_CONCEPTUAL) {
				colocarLiteral(col[1], Coltemp);
			}

			col[2] = Coltemp;
			// HORAS DE AUSENCIA JUSTIFICADAY NO JUSTIFICADA
			// col[3]=Coltemp[1];
			/* lo de descriptor */
			col[4] = plantillaDAO.getDescriptor(filtroPlantilla);// LISTA DE
			// DESCRIPTORES
			col[5] = plantillaDAO.getNotasDesc(filtroPlantilla);// NOTAS DE
			// DESCRIPTORES

			if (filtroPlantilla.getRangosCompletos() != 1) {
				setMensaje(" No se puede generar la plantilla porque ann no se "
						+ "\n ha especificado n esta incompleta la escala valorativa"
						+ "\n para este grupo.");
				request.setAttribute("mensaje", mensaje);
				return;
			}

			if (col[1].size() == 0 || col[0].size() == 0 /* || col[4].size() == 0 */) {
				request.setAttribute("plantilla", "--");

				if (col[1].isEmpty())
					setMensaje("La escala valorativa no esta definida");
				if (col[0].isEmpty())
					setMensaje("La consulta no arrojo estudiantes a evaluar");
				/*
				 * if (col[4].isEmpty())
				 * setMensaje("No hay descriptores a asignar para esta área ");
				 */
				request.setAttribute("mensaje", mensaje);
				return;
			}

			if (filtroPlantilla.getPlantilla().equals("1")) {
				String algo = plantillaDescArea(grupo, area, login,
						filtroPlantilla, col);
				// System.out.println(algo);
				request.setAttribute("plantilla", algo);
				request.setAttribute("tipoArchivo", "xls");
				Logger.print(
						login.getUsuarioId(),
						"Plantilla Area Inst:"
								+ filtroPlantilla.getInstitucion() + " Sede:"
								+ filtroPlantilla.getSede() + " Jorn:"
								+ filtroPlantilla.getJornada() + " Gra:"
								+ filtroPlantilla.getGrado() + " Grupo:"
								+ grupo + " Area:" + area + " Per:"
								+ filtroPlantilla.getPeriodo(),
						"Generacion de Plantilla de evaluacinn de Area:  Periodo '"
								+ filtroPlantilla.getPeriodo_()
								+ "', Metodologia '"
								+ filtroPlantilla.getMetodologia_()
								+ "', Area '" + filtroPlantilla.getArea_()
								+ "', Grado '" + filtroPlantilla.getGrado_()
								+ "', Grupo '" + grupo + "'",
						filtroPlantilla.getPeriodo(), 3, 1, this.toString());
			}
		} catch (Exception th) {
			th.printStackTrace();
			System.out.println("Plantilla Area con errores" + th);
			throw new ServletException(th);
		}
	}

	/**
	 * @param request
	 * @throws ServletException
	 * @throws IOException
	 * <br>
	 *             Return Type: void<br>
	 *             Version 1.1.<br>
	 */
	public void asignarListaPreescolar(HttpServletRequest request, Login login,
			FiltroPlantilla filtroPlantilla) throws ServletException,
			IOException {
//		System.out.println("asignarListaPreescolar ");
		Collection[] col = new List[4];
		try {
			String grupo = filtroPlantilla.getGrupo().substring(
					filtroPlantilla.getGrupo().lastIndexOf("|") + 1,
					filtroPlantilla.getGrupo().length());
			filtroPlantilla.setVigencia(""
					+ plantillaDAO.getVigenciaInst(Long.parseLong(login
							.getInstId().trim())));
			filtroPlantilla.setInstitucion(login.getInstId().trim());
			filtroPlantilla.setSede(login.getSedeId().trim());
			filtroPlantilla.setJornada(login.getJornadaId().trim());
			// ESTUDIANTES
			col[0] = plantillaDAO.getEstudiantes(filtroPlantilla);
			filtroPlantilla.setJerarquiagrupo(plantillaDAO
					.getJerarquiaGrupo(col[0]));
			// System.out.println("jer="+filtroPlantilla.getJerarquiagrupo());
			// filtroPlantilla.setCerrar("0");
			filtroPlantilla.setCerrar(plantillaDAO
					.getEstadoNotasPree(filtroPlantilla));
			// col[2] = plantillaDAO.getNotasPree(filtroPlantilla);

			/**
			 * MODIFICADO 29-03-10
			 * */

			// Si es conceptual tenemos que buscar LA equivalencia en
			// letras
			col[1] = plantillaDAO.getEscalaNivelEval(filtroPlantilla);
			col[1] = plantillaDAO.getEscalaPreescolar();
//			System.out.println("col[1] " + col[1].size());
			Collection Coltemp = plantillaDAO.getNotasPree(filtroPlantilla);
			// System.out.println("filtroPlantilla.getFilCodTipoEval() " +
			// filtroPlantilla.getFilCodTipoEval());
			// if(filtroPlantilla.getFilCodTipoEval() ==
			// ParamsVO.ESCALA_CONCEPTUAL ){
			// colocarLiteralPree(
			// plantillaDAO.getEscalaNivelEval(filtroPlantilla) , Coltemp);
			// }

			col[2] = Coltemp;
			/**
			 * -------
			 * */

			if (col[0].size() == 0) {
				request.setAttribute("plantilla", "--");
				setMensaje("La consulta no arrojo estudiantes a evaluar");
				request.setAttribute("mensaje", mensaje);
				return;
			}

			if (filtroPlantilla.getFilCodTipoEvalPres() == ParamsVO.TIPO_EVAL_PREES_ASIGTURA) {
				setMensaje("No se puede generar la plantilla. porque el tipo de evaluacion de preescolar para este grupo es 'Asignatura'. Por favor dirijase al tab de 'Evaluacinn Asignatura'.");
				request.setAttribute("mensaje", mensaje);
				return;

			}

			/**
			 * Traer dimensiones del colegio
			 * */
			List listaDim = plantillaDAO.getListaDimenciones(
					Long.parseLong(filtroPlantilla.getVigencia()),
					Long.parseLong(filtroPlantilla.getInstitucion()),
					Long.parseLong(filtroPlantilla.getMetodologia()));
//			System.out.println("listaDim.size() " + listaDim.size());
			if (listaDim.size() == 0) {
				setMensaje("No existen dimensiones para esta institucinn.");
				request.setAttribute("mensaje", mensaje);
				return;

			}

//			System.out.println("antes de plantillaPreescolar");
			if (filtroPlantilla.getPlantilla().equals("1")) {
				request.setAttribute("plantilla",
						plantillaPreescolar(grupo, login, filtroPlantilla, col));
				request.setAttribute("tipoArchivo", "xls");
				Logger.print(
						login.getUsuarioId(),
						"Plantilla Preescolar Inst:"
								+ filtroPlantilla.getInstitucion() + " Sede:"
								+ filtroPlantilla.getSede() + " Jorn:"
								+ filtroPlantilla.getJornada() + " Gra:"
								+ filtroPlantilla.getGrado() + " Grupo:"
								+ grupo + " Dim:" + filtroPlantilla.getArea()
								+ " Per:" + filtroPlantilla.getPeriodo(),
						"Generacinn de Plantilla de evaluacinn de Preescolar: Periodo '"
								+ filtroPlantilla.getPeriodo_()
								+ "', Metodologia '"
								+ filtroPlantilla.getMetodologia_()
								+ "', Dimensinn '" + filtroPlantilla.getArea_()
								+ "', Grado '" + filtroPlantilla.getGrado_()
								+ "', Grupo '" + grupo + "'",
						filtroPlantilla.getPeriodo(), 3, 1, this.toString());
			}
		} catch (Exception th) {
			th.printStackTrace();
			System.out.println("Plantilla Preescolar con errores: "
					+ th.getMessage());
			throw new ServletException(th);
		}
	}

	/**
	 * @param grupo
	 * @return<br> Return Type: String<br>
	 *             Version 1.1.<br>
	 */
	public String plantillaPreescolar(String grupo, Login login,
			FiltroPlantilla filtroPlantilla, Collection[] col) {
//		System.out.println("plantillaPreescolar ");
		Excel excel = new Excel();
		String relativo = null;
		filtroPlantilla.setGrupo(grupo);
		int i = 0;
		String[] config = new String[4];
		/* ENCABEZADO DE LA PLANTILLA */
		/* CONFIGURACION DE ARCHIVOS */
		String path;
		String archivo;
		try {
			filtroPlantilla.setTipoPlantilla("" + Properties.PLANTILLAPREE);
			filtroPlantilla.setNivelLogro("1");
			/* UBICACION ES DE ARCHIVOS */
			path = rb.getString("preescolar.PathPreescolar") + "."
					+ login.getUsuarioId();
			archivo = getNombrePreescolar("EvaluacionPreescolar",
					login.getSedeId(), login.getJornadaId(),
					filtroPlantilla.getMetodologia(),
					filtroPlantilla.getGrado_(), filtroPlantilla.getGrupo_(),
					filtroPlantilla.getPeriodo_());
			relativo = Ruta.getRelativo(getServletContext(), path);
			i = 0;
			config[i++] = Ruta2.get(getServletContext(),
					rb.getString("preescolar.PathPlantilla"));// path// de//
																// la//
																// plantilla
			config[i++] = rb.getString("preescolar.Plantilla");// nombre de la//
																// plantilla
			config[i++] = Ruta.get(getServletContext(), path);// path del nuevo
																// // archivo
			config[i++] = archivo;// nombre del nuevo archivo

			/**
			 * Traer dimensiones del colegio
			 * */
			List listaDim = plantillaDAO.getListaDimenciones(
					Long.parseLong(filtroPlantilla.getVigencia()),
					Long.parseLong(filtroPlantilla.getInstitucion()),
					Long.parseLong(filtroPlantilla.getMetodologia()));
//			System.out.println("listaDim.size() " + listaDim.size());

			if (listaDim.size() == 0) {
				setMensaje("No existen dimensiones para esta institucinn.");
				return "--";

			}

			if (!excel.plantillaPreescolar(config, col, filtroPlantilla,
					listaDim)) {
				setMensaje(excel.getMensaje());
				return "--";
			}
			plantillaDAO.setReporte(login.getUsuarioId(), relativo + config[3],
					"xls", config[3], 1);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return relativo + config[3];
	}

	/**
	 * @param grupo
	 * @param area
	 * @return<br> Return Type: String<br>
	 *             Version 1.1.<br>
	 */
	public String plantillaDescArea(String grupo, String area, Login login,
			FiltroPlantilla filtroPlantilla, Collection[] col) {
//		System.out.println(new Date() + " plantillaDescArea");
		Excel excel = new Excel();
		String relativo = null;
		filtroPlantilla.setGrupo(grupo);
		filtroPlantilla.setArea(area);
		String[] config = new String[4];
		String path;
		String archivo;
		int i;
		filtroPlantilla.setTipoPlantilla("" + Properties.PLANTILLAAREADESC);
		filtroPlantilla.setNivelLogro("1");
		/* UBICACION ES DE ARCHIVOS */
		path = rb.getString("area.PathArea") + "." + login.getUsuarioId();
		archivo = getNombreDescAre("EvaluacionArea", login.getSedeId(),
				login.getJornadaId(), filtroPlantilla.getMetodologia(),
				filtroPlantilla.getGrado_(), filtroPlantilla.getGrupo_(),
				filtroPlantilla.getArea_(), filtroPlantilla.getPeriodo_(),
				filtroPlantilla.getDocente(),
				filtroPlantilla.getFilPlanEstudios());
		relativo = Ruta.getRelativo(getServletContext(), path);
		try {
			i = 0;
			config[i++] = Ruta2.get(getServletContext(),
					rb.getString("area.PathPlantilla"));// path
			// de
			// la
			// plantilla
			config[i++] = rb.getString("area.Plantilla");// nombre de la
			// plantilla
			config[i++] = Ruta.get(getServletContext(), path);// path del nuevo
			// archivo
			config[i++] = archivo;// nombre del nuevo archivo

			// VALIDA QUE LA CONFIGURACION DE LAS NOTAS DE AREA SEA AUTOMATICA
			plantillaDAO.getEscalaNivelEval(filtroPlantilla);

			// System.out.println(filtroPlantilla.getFilCodTipoEval()+"=9="+
			// ParamsVO.TIPO_EVAL_ASIG_NUM );
			// System.out.println(filtroPlantilla.getFilCodModoEval() +"=9="+
			// ParamsVO.MODO_EVAL_PROMEDIADO );
			if ((filtroPlantilla.getFilCodTipoEval() == ParamsVO.TIPO_EVAL_ASIG_NUM || filtroPlantilla
					.getFilCodTipoEval() == ParamsVO.TIPO_EVAL_ASIG_PORCENTUAL)
					&& filtroPlantilla.getFilCodModoEval() == ParamsVO.MODO_EVAL_PROMEDIADO) {
				// System.out.println("ENTOR MENS");
				setMensaje("ADVERTENCIA: Recuerde que para este grupo la plantilla de área solo servirn para importar descriptores.");

			}

			if (!excel.plantillaAreaDescriptor(config, col, filtroPlantilla)) {
				System.out.println("excel.getMensaje() " + excel.getMensaje());
				setMensaje(excel.getMensaje());
				return "--";
			}

			plantillaDAO.setReporte(login.getUsuarioId(), relativo + config[3],
					"xls", config[3], 1);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("PLANTILLA AREA " + e.getMessage());
			e.printStackTrace();
		}
		return relativo + config[3];
	}

	/**
	 * @param grupo
	 * @param asig
	 * @return<br> Return Type: String<br>
	 *             Version 1.1.<br>
	 */
	public String plantillaLogroAsig(String grupo, String asig, Login login,
			FiltroPlantilla filtroPlantilla, Collection[] col) {
//		System.out.println("plantillaLogroAsig");

		Excel excel = new Excel();
		String relativo = "";
		String path = "", archivo = "";
		String[] config = new String[4];
		int i = 0;

		filtroPlantilla.setGrupo(grupo);
		filtroPlantilla.setAsignatura(asig);
		/* CONFIGURACION DE ARCHIVOS */
		filtroPlantilla.setTipoPlantilla("" + Properties.PLANTILLALOGROASIG);
		filtroPlantilla.setNivelLogro("1");
		/* UBICACIONES DE ARCHIVOS */
		path = rb.getString("asignatura.PathAsignatura") + "."
				+ login.getUsuarioId();
		archivo = getNombreLogroAsig("EvaluacionAsignatura", login.getSedeId(),
				login.getJornadaId(), filtroPlantilla.getMetodologia(),
				filtroPlantilla.getGrado_(), filtroPlantilla.getGrupo_(),
				filtroPlantilla.getAsignaturaAbre_(),
				filtroPlantilla.getPeriodo_(), filtroPlantilla.getDocente(),
				filtroPlantilla.getFilPlanEstudios());
		relativo = Ruta.getRelativo(getServletContext(), path);
		try {
			i = 0;
			config[i++] = Ruta2.get(getServletContext(),
					rb.getString("asignatura.PathPlantilla"));// path
			// de
			// la
			// plantilla
			config[i++] = rb.getString("asignatura.Plantilla");// nombre de la
			// plantilla
			config[i++] = Ruta.get(getServletContext(), path);// path del nuevo
			// archivo
			config[i++] = archivo;// nombre del nuevo archivo
			if (!excel.plantillaLogroAsignatura(config, col, filtroPlantilla)) {
				setMensaje(excel.getMensaje());
				return "--";
			}

			plantillaDAO.setReporte(login.getUsuarioId(), relativo + config[3],
					"xls", config[3], 1);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("PLATILLA ASIG " + e.getMessage());
			e.printStackTrace();
		}
		return relativo + config[3];
	}

	public String plantillaRecuperacion(String grupo, String asig, Login login,
			FiltroPlantilla filtroPlantilla, Collection[] col) {
		Excel excel = new Excel();
		String relativo = "";
		String path = "", archivo = "";
		int i = 0;
		String[] config = new String[4];
		filtroPlantilla.setGrupo(grupo);
		filtroPlantilla.setAsignatura(asig);
		/* CONFIGURACION DE ARCHIVOS */
		filtroPlantilla.setTipoPlantilla("" + Properties.PLANTILLARECUPERACION);
		filtroPlantilla.setNivelLogro("1");
		/* UBICACIONES DE ARCHIVOS */
		path = rb.getString("recuperacion.PathRecuperacion") + "."
				+ login.getUsuarioId();
		archivo = getNombreRecuperacion("RecuperacionLogro", login.getSedeId(),
				login.getJornadaId(), filtroPlantilla.getMetodologia(),
				filtroPlantilla.getGrado_(), filtroPlantilla.getGrupo_(),
				filtroPlantilla.getAsignaturaAbre_(),
				filtroPlantilla.getPeriodo_(), filtroPlantilla.getDocente(),
				filtroPlantilla.getFilPlanEstudios());
		relativo = Ruta.getRelativo(getServletContext(), path);
		i = 0;
		config[i++] = Ruta2.get(getServletContext(),
				rb.getString("recuperacion.PathPlantilla"));// path
		// de
		// la
		// plantilla
		config[i++] = rb.getString("recuperacion.Plantilla");// nombre de la
		// plantilla
		config[i++] = Ruta.get(getServletContext(), path);// path del nuevo
		// archivo
		config[i++] = archivo;// nombre del nuevo archivo
		if (!excel.plantillaRecuperacionLogro(config, col, filtroPlantilla)) {
			setMensaje(excel.getMensaje());
			return "--";
		}
		plantillaDAO.setReporte(login.getUsuarioId(), relativo + config[3],
				"xls", config[3], 1);
		return relativo + config[3];
	}

	/**
	 * @param ini
	 * @param sed
	 * @param jor
	 * @param meto
	 * @param grad
	 * @param gru
	 * @param asig
	 * @param per
	 * @return<br> Return Type: String<br>
	 *             Version 1.1.<br>
	 */
	public String getNombreLogroAsig(String ini, String sed, String jor,
			String meto, String grad, String gru, String asig, String per,
			String doc, int estado) {
		String jo = formatear((jor.length() > 10 ? meto.substring(0, 10) : jor));
		String met = formatear((meto.length() > 21 ? meto.substring(0, 21)
				: meto));
		String gra = formatear((grad.length() > 8 ? grad.substring(0, 8) : grad));
		String gr = formatear((gru.length() > 8 ? gru.substring(0, 8) : gru));
		String asi = formatear(asig);
		String pe = formatear((per.length() > 10 ? per.substring(0, 10) : per));
		String fe = f2.toString().replace(':', '-').replace('.', '-')
				.substring(0, 10)
				+ "_"
				+ f2.toString().replace(':', '-').replace('.', '-')
						.substring(11, 19);
		if (estado == 0)
			return (ini + "_Sed_" + sed + "_Jor_" + jo + "_Met_" + met
					+ "_Gra_" + gra + "_Gru_" + gr + "_Asi_" + asi + "_Per_"
					+ pe + "_(" + fe + ").xls");
		else
			return (ini + "_Sed_" + sed + "_Jor_" + jo + "_Met_" + met
					+ "_Gra_" + gra + "_Gru_" + gr + "_Asi_" + asi + "_Per_"
					+ pe + "_Doc_" + doc + "_(" + fe + ").xls");
	}

	public String getNombreRecuperacion(String ini, String sed, String jor,
			String meto, String grad, String gru, String asig, String per,
			String doc, int estado) {
		String jo = formatear((jor.length() > 10 ? meto.substring(0, 10) : jor));
		String met = formatear((meto.length() > 21 ? meto.substring(0, 21)
				: meto));
		String gra = formatear((grad.length() > 8 ? grad.substring(0, 8) : grad));
		String gr = formatear((gru.length() > 8 ? gru.substring(0, 8) : gru));
		String asi = formatear(asig);
		String pe = formatear((per.length() > 10 ? per.substring(0, 10) : per));
		String fe = f2.toString().replace(':', '-').replace('.', '-')
				.substring(0, 10)
				+ "_"
				+ f2.toString().replace(':', '-').replace('.', '-')
						.substring(11, 19);
		if (estado == 0)
			return (ini + "_Sed_" + sed + "_Jor_" + jo + "_Met_" + met
					+ "_Gra_" + gra + "_Gru_" + gr + "_Asi_" + asi + "_Per_"
					+ pe + "_(" + fe + ").xls");
		else
			return (ini + "_Sed_" + sed + "_Jor_" + jo + "_Met_" + met
					+ "_Gra_" + gra + "_Gru_" + gr + "_Asi_" + asi + "_Per_"
					+ pe + "_Doc_" + doc + "_(" + fe + ").xls");
	}

	/**
	 * @param ini
	 * @param sed
	 * @param jor
	 * @param meto
	 * @param grad
	 * @param gru
	 * @param per
	 * @return<br> Return Type: String<br>
	 *             Version 1.1.<br>
	 */
	public String getNombrePreescolar(String ini, String sed, String jor,
			String meto, String grad, String gru, String per) {
		String jo = formatear((jor.length() > 10 ? meto.substring(0, 10) : jor));
		String met = formatear((meto.length() > 21 ? meto.substring(0, 21)
				: meto));
		String gra = formatear((grad.length() > 8 ? grad.substring(0, 8) : grad));
		String gr = formatear((gru.length() > 8 ? gru.substring(0, 8) : gru));
		String pe = formatear((per.length() > 10 ? per.substring(0, 10) : per));
		String fe = f2.toString().replace(':', '-').replace('.', '-')
				.substring(0, 10)
				+ "_"
				+ f2.toString().replace(':', '-').replace('.', '-')
						.substring(11, 19);
		return (ini + "_Sed_" + sed + "_Jor_" + jo + "_Met_" + met + "_Gra_"
				+ gra + "_Gru_" + gr + "_Per_" + pe + "_(" + fe + ").xls");
	}

	/**
	 * @param ini
	 * @param sed
	 * @param jor
	 * @param meto
	 * @param grad
	 * @param gru
	 * @param area
	 * @param per
	 * @return<br> Return Type: String<br>
	 *             Version 1.1.<br>
	 */
	public String getNombreDescAre(String ini, String sed, String jor,
			String meto, String grad, String gru, String area, String per,
			String doc, int estado) {
		String jo = formatear((jor.length() > 10 ? meto.substring(0, 10) : jor));
		String met = formatear((meto.length() > 21 ? meto.substring(0, 21)
				: meto));
		String gra = formatear((grad.length() > 8 ? grad.substring(0, 8) : grad));
		String gr = formatear((gru.length() > 8 ? gru.substring(0, 8) : gru));
		String are = formatear(area);
		String pe = formatear((per.length() > 10 ? per.substring(0, 10) : per));
		String fe = f2.toString().replace(':', '-').replace('.', '-')
				.substring(0, 10)
				+ "_"
				+ f2.toString().replace(':', '-').replace('.', '-')
						.substring(11, 19);
		if (estado == 0)
			return (ini + "_Sed_" + sed + "_Jor_" + jo + "_Met_" + met
					+ "_Gra_" + gra + "_Gru_" + gr + "_Are_" + are + "_Per_"
					+ pe + "_(" + fe + ").xls");
		else
			return (ini + "_Sed_" + sed + "_Jor_" + jo + "_Met_" + met
					+ "_Gra_" + gra + "_Gru_" + gr + "_Are_" + are + "_Per_"
					+ pe + "_Doc_" + doc + "_(" + fe + ").xls");
	}

	/**
	 * @param ini
	 * @param meto
	 * @param grad
	 * @param asig
	 * @return<br> Return Type: String<br>
	 *             Version 1.1.<br>
	 */
	public String getNombre(String ini, String meto, String grad, String asig) {
		String met = formatear((meto.length() > 21 ? meto.substring(0, 21)
				: meto));
		String gra = formatear((grad.length() > 8 ? grad.substring(0, 8) : grad));
		String asi = formatear(asig);
		String fe = f2.toString().replace(':', '-').replace('.', '-')
				.substring(0, 10)
				+ "_"
				+ f2.toString().replace(':', '-').replace('.', '-')
						.substring(11, 19);
		return (ini + "_M_" + met + "_" + gra + "_" + asi + "_(" + fe + ").xls");
	}

	/**
	 * @param a
	 * @return<br> Return Type: String<br>
	 *             Version 1.1.<br>
	 */
	public String formatear(String a) {
		return (a.replace(' ', '_').replace('n', 'a').replace('n', 'e')
				.replace('n', 'i').replace('n', 'o').replace('n', 'u')
				.replace('n', 'A').replace('n', 'E').replace('n', 'I')
				.replace('n', 'O').replace('n', 'U').replace('n', 'n')
				.replace('n', 'N').replace('n', 'a').replace('n', 'e')
				.replace('n', 'i').replace('n', 'o').replace('n', 'u')
				.replace('n', 'A').replace('n', 'E').replace('n', 'I')
				.replace('n', 'O').replace('n', 'U').replace('n', 'c')
				.replace(':', '_').replace('.', '_').replace('/', '_').replace(
				'\\', '_'));
	}

	/**
	 * @param request
	 * @throws ServletException
	 * @throws IOException
	 * <br>
	 *             Return Type: void<br>
	 *             Version 1.1.<br>
	 */
	public void asignarListaAsignatura(HttpServletRequest request, Login login,
			FiltroPlantilla filtroPlantilla) throws ServletException,
			IOException {
//		System.out.println(new Date() + " asignarListaAsignatura --------");
		try {
			Collection[] col = new Collection[7];
			// 0=Estudiantes
			// 1=Escala asignatura
			// 2=nOTAS DE asignatura
			// 3=ausencias
			// 4=Logros
			// 5=Escala de logros
			// 6=Notas de logros
			String per = filtroPlantilla.getPeriodoAbrev();
			String asig = filtroPlantilla.getAsignatura().substring(
					filtroPlantilla.getAsignatura().lastIndexOf('|') + 1,
					filtroPlantilla.getAsignatura().length());
			String grupo = filtroPlantilla.getGrupo().substring(
					filtroPlantilla.getGrupo().lastIndexOf("|") + 1,
					filtroPlantilla.getGrupo().length());
			// ESTUDIANTES
			col[0] = plantillaDAO.getEstudiantes(filtroPlantilla);
			// JERARQUIA DEL GRUPO AL QUE PERTENECE
			filtroPlantilla.setJerarquiagrupo(plantillaDAO
					.getJerarquiaGrupo(col[0]));
			// REVISAR SI ESTA O NO ABIERTA PARA EDICION DEL GRUPO
			filtroPlantilla.setCerrar(plantillaDAO
					.getEstadoNotasAsig(filtroPlantilla));
			// NOTAS

			/**
			 * MODIFICADO Escala asignatura 20-03-10
			 * */
			// col[1] = plantillaDAO.getEscala(2);
			col[1] = plantillaDAO.getEscalaNivelEval(filtroPlantilla);

			/**
			 * FIN
			 * **/

			Collection Coltemp = plantillaDAO.getNotasAsig(filtroPlantilla);

			// Si es conceptual tenemos que buscar LA equivalencia en
			// letras
			if (filtroPlantilla.getFilCodTipoEval() == ParamsVO.ESCALA_CONCEPTUAL) {
				colocarLiteral(col[1], Coltemp);
			}

			col[2] = Coltemp;
			// HORAS DE AUSENCIA JUSTIFICADAY NO JUSTIFICADA
			// col[3]=Coltemp[1];
			/* LO DE LOGROS AQUI MISMO */
			col[4] = plantillaDAO.getLogro(filtroPlantilla);
			col[5] = plantillaDAO.getEscala(1);
			col[6] = plantillaDAO.getNotasLogro(filtroPlantilla);

			if (plantillaDAO
					.isGradoPreecolar(filtroPlantilla.getGrado().trim())
					&& filtroPlantilla.getFilCodTipoEvalPres() == ParamsVO.TIPO_EVAL_PREES_CUALITATIVA) {

				setMensaje("No se puede generar la plantilla. porque el tipo de evaluacion de preescolar para este grupo es 'Cualitativa'. Por favor dirijase al tab de 'Evaluacinn por Dimensiones'.");
				request.setAttribute("mensaje", mensaje);
				return;

			}

			if (filtroPlantilla.getRangosCompletos() != 1) {
				setMensaje(" No se puede generar la plantilla porque ann no se "
						+ "\n ha especificado n esta incompleta la escala valorativa"
						+ "\n para este grupo.");
				request.setAttribute("mensaje", mensaje);
				return;
			}

			if (col[1].size() == 0 || col[0].size() == 0) {
				request.setAttribute("plantilla", "--");

				if (col[0].isEmpty())
					setMensaje("La consulta no arrojo estudiantes a evaluar");
				if (col[1].isEmpty())
					setMensaje("La escala valorativa de asignaturas no esta definida");
				if (col[4].isEmpty()) {
					setMensaje("La consulta no arrojo logros a evaluar");
				}
				if (col[5].isEmpty()) {
					setMensaje("La escala valorativa de logros no esta definida");
				}
				request.setAttribute("mensaje", mensaje);
				return;
			}
			if (filtroPlantilla.getPlantilla().equals("1")) {
				request.setAttribute(
						"plantilla",
						plantillaLogroAsig(grupo, asig, login, filtroPlantilla,
								col));
				request.setAttribute("tipoArchivo", "xls");
				Logger.print(
						login.getUsuarioId(),
						"Plantilla Asignatura Inst:"
								+ filtroPlantilla.getInstitucion() + " Sede:"
								+ filtroPlantilla.getSede() + " Jorn:"
								+ filtroPlantilla.getJornada() + " Gra:"
								+ filtroPlantilla.getGrado() + " Grupo:"
								+ grupo + " Asig:" + asig + " Per:"
								+ filtroPlantilla.getPeriodo(),
						"Generacinn de Plantillas de evaluacinn de Asignatura: Periodo '"
								+ filtroPlantilla.getPeriodo_()
								+ "', Metodologia '"
								+ filtroPlantilla.getMetodologia_()
								+ "', Asignatura '"
								+ filtroPlantilla.getAsignaturaAbre_()
								+ "', Gra:" + filtroPlantilla.getGrado_()
								+ " Grupo:" + grupo + "'",
						filtroPlantilla.getPeriodo(), 3, 1, this.toString());
			}
		} catch (Exception th) {
			th.printStackTrace();
			System.out.println("Plantilla asignatura con errores: "
					+ th.getMessage());
			throw new ServletException(th);
		}
	}

	/**
	 * @param request
	 * @throws ServletException
	 * @throws IOException
	 * <br>
	 *             Return Type: void<br>
	 *             Version 1.1.<br>
	 */
	public void asignarListaRecuperacion(HttpServletRequest request,
			Login login, FiltroPlantilla filtroPlantilla)
			throws ServletException, IOException {
		try {
			Collection[] col = new Collection[4];
			// 0=Estudiantes
			// 1=Logros
			// 2=Escala de logros
			// 3=Notas de logros
			String per = filtroPlantilla.getPeriodoAbrev();
			String asig = filtroPlantilla.getAsignatura().substring(
					filtroPlantilla.getAsignatura().lastIndexOf('|') + 1,
					filtroPlantilla.getAsignatura().length());
			String grupo = filtroPlantilla.getGrupo().substring(
					filtroPlantilla.getGrupo().lastIndexOf("|") + 1,
					filtroPlantilla.getGrupo().length());
			// ESTUDIANTES
			col[0] = plantillaDAO.getEstudiantes(filtroPlantilla);
			// JERARQUIA DEL GRUPO AL QUE PERTENECE
			filtroPlantilla.setJerarquiagrupo(plantillaDAO
					.getJerarquiaGrupo(col[0]));
			// REVISAR SI ESTA O NO ABIERTA PARA EDICION DEL GRUPO
			filtroPlantilla.setCerrar(plantillaDAO
					.getEstadoNotasAsig(filtroPlantilla));
			/* LO DE LOGROS AQUI MISMO */
			col[1] = plantillaDAO.getLogro(filtroPlantilla);
			col[2] = plantillaDAO.getEscalaRecuperacion(1);
			col[3] = plantillaDAO.getNotasRecuperacionLogro(filtroPlantilla);
			if (col[0].size() == 0 || col[1].size() == 0 || col[2].size() == 0) {
				request.setAttribute("plantilla", "--");
				if (col[0].isEmpty())
					setMensaje("La consulta no arrojo estudiantes a evaluar");
				if (col[1].isEmpty()) {
					setMensaje("La consulta no arrojo logros a evaluar");
				}
				if (col[2].isEmpty()) {
					setMensaje("La escala valorativa de logros no esta definida");
				}
				request.setAttribute("mensaje", mensaje);
				return;
			}
			if (filtroPlantilla.getPlantilla().equals("1")) {
				request.setAttribute(
						"plantilla",
						plantillaRecuperacion(grupo, asig, login,
								filtroPlantilla, col));
				request.setAttribute("tipoArchivo", "xls");
				Logger.print(
						login.getUsuarioId(),
						"Plantilla Recuperacion Inst:"
								+ filtroPlantilla.getInstitucion() + " Sede:"
								+ filtroPlantilla.getSede() + " Jorn:"
								+ filtroPlantilla.getJornada() + " Gra:"
								+ filtroPlantilla.getGrado() + " Grupo:"
								+ grupo + " Asig:" + asig + " Per:"
								+ filtroPlantilla.getPeriodo(),
						"Generacinn de Plantillas de recuperacinn de Logro: Periodo '"
								+ filtroPlantilla.getPeriodo_()
								+ "', Metodologia '"
								+ filtroPlantilla.getMetodologia_()
								+ "', Asignatura '"
								+ filtroPlantilla.getAsignaturaAbre_()
								+ "', Gra:" + filtroPlantilla.getGrado_()
								+ " Grupo:" + grupo + "'",
						filtroPlantilla.getPeriodo(), 3, 1, this.toString());
			}
		} catch (Exception th) {
			th.printStackTrace();
			System.out.println("Plantilla recuperacion con errores: " + th);
			throw new ServletException(th);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);// redirecciona la peticion a doPost
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String s = process(request, response);
		if (s != null && !s.equals(""))
			ir(1, s, request, response);
	}

	/**
	 * @param a
	 * @param s
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * <br>
	 *             Return Type: void<br>
	 *             Version 1.1.<br>
	 */
	public void ir(int a, String s, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher(s);
		if (a == 1)
			rd.include(request, response);
		else
			rd.forward(request, response);
	}

	/**
	 * @param s
	 * <br>
	 *            Return Type: void<br>
	 *            Version 1.1.<br>
	 */
	public void setMensaje(String s) {
		mensaje = "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n";
		mensaje += "  - " + s + "\n";
	}

	/**
	 * @return<br> Return Type: String<br>
	 *             Version 1.1.<br>
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @function:
	 * @param col
	 * @param colTemp
	 * @throws Exception
	 */
	public void colocarLiteral(Collection col, Collection colTemp)
			throws Exception {
		try {
			java.util.Iterator iterator = colTemp.iterator();
			while (iterator.hasNext()) {
				Object[] o = (Object[]) iterator.next();
				o[1] = getNotaReal(col, (String) o[1]);
				o[3] = getNotaReal(col, (String) o[3]);
			}

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * @function: Se engarga de colocar el literal correpondiente al codigo que
	 *            este guardado en EVALUACION_PREESCOLAR
	 * @param col
	 * @param colTemp
	 * @throws Exception
	 */
	public void colocarLiteralPree(Collection col, Collection colTemp)
			throws Exception {
		try {
//			System.out.println("colocarLiteralPree ");
			java.util.Iterator iterator = colTemp.iterator();
			while (iterator.hasNext()) {
				Object[] o = (Object[]) iterator.next();
//				System.out.println("(String)o[1] " + (String) o[1]);
				o[3] = getNotaReal(col, (String) o[3]);
			}

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * @function:
	 * @param escala
	 * @param n
	 * @return
	 */
	public String getNotaReal(Collection escala, String n) {

		if (n == null)
			return null;
		java.util.Iterator iterator = escala.iterator();
		while (iterator.hasNext()) {
			Object[] o = (Object[]) iterator.next();
			String valor = (String) o[0];
			if (valor.trim().equals(n.trim())) {
				return (String) o[1];
			}
		}
		return "";
	}

	/**
	 * Metodo que obtiene todas las dimensiones creadas para una determinada
	 * institucion, metodologia y vigencia
	 * 
	 * @param inst
	 * @param metod
	 * @param vig
	 * @return
	 * @throws Exception
	 */
	public List getListaDimenciones(long vig, long inst, long metod)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getListaDimenciones"));
			st.setLong(i++, vig);
			st.setLong(i++, inst);
			st.setLong(i++, metod);

			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				DimensionesVO dim = new DimensionesVO();
				dim.setDimcodinst(rs.getLong(i++));
				dim.setDimcodmetod(rs.getLong(i++));
				dim.setDimcodigo(rs.getLong(i++));
				dim.setDimnombre(rs.getString(i++));
				dim.setDimorden(rs.getLong(i++));
				dim.setDimabrev(rs.getString(i++));
				dim.setDimvigencia(rs.getLong(i++));
				l.add(dim);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

}