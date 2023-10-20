package siges.plantilla.evaluacion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.login.beans.Login;
import siges.plantilla.beans.FiltroPlantilla;
import siges.plantilla.dao.PlantillaDAO;
import siges.util.Properties;

/**
 * siges.plantilla.evaluacion<br>
 * Funcinn: Servicio que recibe la solicitud de plantillas de evaluacion de
 * estudiantes y devuelve lso objetos necesarios para la vista del formulario de
 * filtro <br>
 */
public class ControllerPlantillaEdit extends HttpServlet {
	// private String mensaje;//mensaje en caso de error
	// private boolean err;//variable que inidica si hay o no errores en la
	// validacion de los datos del formulario
	private Cursor cursor;// objeto que maneja las sentencias sql

	private PlantillaDAO plantillaDAO;//

	private ResourceBundle rb;

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
		rb = ResourceBundle.getBundle("siges.plantilla.bundle.plantilla");
		String sig, sig2, sig3, sig4, ant, er, home;
		int tipo;
		sig = getServletConfig().getInitParameter("sig");
		sig2 = getServletConfig().getInitParameter("sig2");
		sig3 = getServletConfig().getInitParameter("sig3");
		sig4 = getServletConfig().getInitParameter("sig4");
		ant = getServletConfig().getInitParameter("ant");
		er = getServletContext().getInitParameter("error");
		home = getServletContext().getInitParameter("home");
		cursor = new Cursor();
		plantillaDAO = new PlantillaDAO(cursor);
		if (request.getParameter("tipo") == null
				|| ((String) request.getParameter("tipo")).equals("")) {
			borrarBeans(request);
			session.removeAttribute("editar");
			tipo = Properties.PLANTILLALOGROASIG;
		} else
			tipo = Integer.parseInt((String) request.getParameter("tipo"));
		if (!asignarBeans(request)) {
			request.setAttribute(
					"mensaje",
					setMensaje("Error capturando datos de sesinn para el usuario"));
			return er;
		}
		Login login = (Login) session.getAttribute("login");
		FiltroPlantilla filtroPlantilla = (FiltroPlantilla) session
				.getAttribute("filtroPlantilla");
		switch (tipo) {
		case Properties.PLANTILLALOGROASIG:// asig
			editarLogro(request, session, login, filtroPlantilla);
			break;
		case Properties.PLANTILLAAREADESC:// area
			editarDescriptor(request, session, login, filtroPlantilla);
			sig = sig2;
			break;
		case Properties.PLANTILLAPREE:// pree
			editarPreescolar(request, login);
			sig = sig3;
			break;
		case Properties.PLANTILLARECUPERACION:// pree
			editarLogro(request, session, login, filtroPlantilla);
			sig = sig4;
			break;
		}
		return sig;
	}

	/**
	 * @param request
	 * <br>
	 *            Return Type: void<br>
	 *            Version 1.1.<br>
	 */
	public void borrarBeans(HttpServletRequest request) {
		request.getSession().removeAttribute("filtroEvaluacion");
	}

	/**
	 * @param request
	 * @param login
	 * @throws ServletException
	 * @throws IOException
	 * <br>
	 *             Return Type: void<br>
	 *             Version 1.1.<br>
	 */
	public void editarLogro(HttpServletRequest request, HttpSession session,
			Login login, FiltroPlantilla filtro) throws ServletException,
			IOException {
		Object[] o;
		try {
			if (filtro == null) {
				filtro = new FiltroPlantilla();
				filtro.setMunicipio(login.getMunId());
				filtro.setLocalidad(login.getLocId());
				filtro.setInstitucion(login.getInstId());
				filtro.setInstitucion_(login.getInst());
				filtro.setSede(login.getSedeId());
				filtro.setSede_(login.getSede());
				filtro.setJornada(login.getJornadaId());
				filtro.setJornada_(login.getJornada());
				filtro.setVigencia(""
						+ plantillaDAO.getVigenciaInst(Long.parseLong(login
								.getInstId())));
				filtro.setFilPlanEstudios(plantillaDAO.getEstadoPlan(Long
						.parseLong(login.getInstId())));
				session.setAttribute("filtroPlantilla", filtro);
			}
			if (filtro.getAsignatura() != null
					&& !filtro.getAsignatura().equals("")
					&& !filtro.getAsignatura().equals("-99")) {
				// long asig=Long.parseLong(filtro.getAsignatura());
				long asig = Long.parseLong(filtro.getAsignatura().substring(
						filtro.getAsignatura().lastIndexOf("|") + 1,
						filtro.getAsignatura().length()));
				request.setAttribute("listaDocente", plantillaDAO
						.getListaDocenteAsignatura(
								Long.parseLong(filtro.getInstitucion()),
								Integer.parseInt(filtro.getMetodologia()),
								Integer.parseInt(filtro.getVigencia()),
								Integer.parseInt(filtro.getGrado()), asig));
			}
			request.getSession().removeAttribute("editar");
			Collection listas[] = new Collection[4];
			Collection parametros[] = new Collection[4];
			o = new Object[2];
			o[0] = Properties.ENTEROLARGO;
			o[1] = login.getInstId();
			for (int zz = 0; zz < 3; zz++) {
				parametros[zz] = new ArrayList();
				parametros[zz].add(o);
			}
			// vigencia para collection [2]
			o = new Object[2];
			o[0] = Properties.ENTEROLARGO;
			o[1] = ""
					+ plantillaDAO.getVigenciaInst(Long.parseLong(login
							.getInstId()));
			parametros[2].add(o);

			parametros[3] = new ArrayList();
			o = new Object[2];
			o[0] = Properties.ENTEROLARGO;
			o[1] = login.getInstId();
			parametros[3].add(o);
			o = new Object[2];
			o[0] = Properties.ENTEROLARGO;
			o[1] = login.getSedeId();
			parametros[3].add(o);
			o = new Object[2];
			o[0] = Properties.ENTEROLARGO;
			o[1] = login.getJornadaId();
			parametros[3].add(o);
			String consultas[] = { rb.getString("listaMetodologiaInstitucion"),
					rb.getString("filtroGradoInstitucion2"),
					rb.getString("filtroAsignaturaGradoInstitucion"),
					rb.getString("filtroGrupoGradoInstitucion") };
			listas = plantillaDAO.getFiltro(consultas, parametros);
			request.setAttribute("filtroMetodologia", listas[0]);
			request.setAttribute("filtroGrado2", listas[1]);
			request.setAttribute("filtroGradoAsignatura", listas[2]);
			request.setAttribute("filtroGrupo2", listas[3]);
			// request.setAttribute("filtroPeriodo",
			// Recursos.recursoEstatico[Recursos.PERIODO]);
			request.setAttribute(
					"filtroPeriodo",
					getListaPeriodo(login.getLogNumPer(),
							login.getLogNomPerDef()));
		} catch (Exception th) {
			throw new ServletException(th);
		}
	}

	/**
	 * @param request
	 * @param login
	 * @throws ServletException
	 * @throws IOException
	 * <br>
	 *             Return Type: void<br>
	 *             Version 1.1.<br>
	 */
	public void editarPreescolar(HttpServletRequest request, Login login)
			throws ServletException, IOException {
		Collection list;
		Object[] o;
		try {
			Collection listas[] = new Collection[3];
			Collection parametros[] = new Collection[3];
			list = new ArrayList();
			o = new Object[2];
			o[0] = Properties.ENTEROLARGO;
			o[1] = login.getInstId();
			list.add(o);
			request.getSession().removeAttribute("editar");
			for (int zz = 0; zz < parametros.length; zz++) {
				parametros[zz] = new ArrayList();
			}
			String consultas[] = { rb.getString("filtroGradoInstitucion2"),
					rb.getString("listaMetodologiaInstitucion"),
					rb.getString("filtroGrupoGradoInstitucion") };
			// grados por institucion
			parametros[0].add(o);
			// metodologias por institucion
			parametros[1].add(o);
			// grupos por grado,institucion,sede,jornada
			o = new Object[2];
			o[0] = Properties.ENTEROLARGO;
			o[1] = login.getInstId();
			parametros[2].add(o);
			o = new Object[2];
			o[0] = Properties.ENTEROLARGO;
			o[1] = login.getSedeId();
			parametros[2].add(o);
			o = new Object[2];
			o[0] = Properties.ENTEROLARGO;
			o[1] = login.getJornadaId();
			parametros[2].add(o);
			listas = plantillaDAO.getFiltro(consultas, parametros);
			request.setAttribute("filtroGrado2", listas[0]);
			request.setAttribute("filtroMetodologia", listas[1]);
			request.setAttribute("filtroGrupo2", listas[2]);
			// request.setAttribute("filtroPeriodo",
			// Recursos.recursoEstatico[Recursos.PERIODO]);
			request.setAttribute(
					"filtroPeriodo",
					getListaPeriodo(login.getLogNumPer(),
							login.getLogNomPerDef()));
		} catch (Exception th) {
			throw new ServletException(th);
		}
	}

	/**
	 * @param request
	 * @param login
	 * @throws ServletException
	 * @throws IOException
	 * <br>
	 *             Return Type: void<br>
	 *             Version 1.1.<br>
	 */
	public void editarDescriptor(HttpServletRequest request,
			HttpSession session, Login login, FiltroPlantilla filtro)
			throws ServletException, IOException {
		Object[] o;
		try {
			if (filtro == null) {
				filtro = new FiltroPlantilla();
				filtro.setMunicipio(login.getMunId());
				filtro.setLocalidad(login.getLocId());
				filtro.setInstitucion(login.getInstId());
				filtro.setInstitucion_(login.getInst());
				filtro.setSede(login.getSedeId());
				filtro.setSede_(login.getSede());
				filtro.setJornada(login.getJornadaId());
				filtro.setJornada_(login.getJornada());
				filtro.setVigencia(""
						+ plantillaDAO.getVigenciaInst(Long.parseLong(filtro
								.getInstitucion())));
				filtro.setFilPlanEstudios(plantillaDAO.getEstadoPlan(Long
						.parseLong(login.getInstId())));
				session.setAttribute("filtroPlantilla", filtro);
			}
			if (filtro.getArea() != null && !filtro.getArea().equals("")
					&& !filtro.getArea().equals("-99")) {
				// long area=Long.parseLong(filtro.getArea());
				long area = Long.parseLong(filtro.getArea().substring(
						filtro.getArea().lastIndexOf("|") + 1,
						filtro.getArea().length()));
				request.setAttribute("listaDocente", plantillaDAO
						.getListaDocenteArea(
								Long.parseLong(filtro.getInstitucion()),
								Integer.parseInt(filtro.getMetodologia()),
								Integer.parseInt(filtro.getVigencia()),
								Integer.parseInt(filtro.getGrado()), area));
			}
			request.getSession().removeAttribute("editar");
			Collection listas[] = new Collection[4];
			Collection parametros[] = new Collection[4];
			String consultas[] = { rb.getString("filtroGradoInstitucion2"),
					rb.getString("filtroAreaGradoInstitucion"),
					rb.getString("listaMetodologiaInstitucion"),
					rb.getString("filtroGrupoGradoInstitucion") };
			for (int zz = 0; zz < parametros.length; zz++) {
				parametros[zz] = new ArrayList();
			}
			int z = 0;
			o = new Object[2];
			o[0] = Properties.ENTEROLARGO;
			o[1] = login.getInstId();
			// grados por institucion
			parametros[0].add(o);
			// areas por grado
			parametros[1].add(o);
			o = new Object[2];
			o[0] = Properties.ENTEROLARGO;
			o[1] = ""
					+ plantillaDAO.getVigenciaInst(Long.parseLong(login
							.getInstId()));
			parametros[1].add(o);
			// metodologia
			o = new Object[2];
			o[0] = Properties.ENTEROLARGO;
			o[1] = login.getInstId();
			parametros[2].add(o);
			// grupos por grado,institucion,sede,jornada
			z = 0;
			o = new Object[2];
			o[z++] = Properties.ENTEROLARGO;
			o[z++] = login.getInstId();
			parametros[3].add(o);
			o = new Object[2];
			z = 0;
			o[z++] = Properties.ENTEROLARGO;
			o[z++] = login.getSedeId();
			parametros[3].add(o);
			o = new Object[2];
			z = 0;
			o[z++] = Properties.ENTEROLARGO;
			o[z++] = login.getJornadaId();
			parametros[3].add(o);
			listas = plantillaDAO.getFiltro(consultas, parametros);
			request.setAttribute("filtroGrado2", listas[0]);
			request.setAttribute("filtroGradoArea", listas[1]);
			request.setAttribute("filtroMetodologia", listas[2]);
			request.setAttribute("filtroGrupo2", listas[3]);
			// request.setAttribute("filtroPeriodo",
			// Recursos.recursoEstatico[Recursos.PERIODO]);
			request.setAttribute(
					"filtroPeriodo",
					getListaPeriodo(login.getLogNumPer(),
							login.getLogNomPerDef()));
		} catch (Exception th) {
			throw new ServletException(th);
		}
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
		HttpSession session = request.getSession();
		Login login = (Login) session.getAttribute("login");
		if (login == null)
			return false;
		return true;
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
		if (cursor != null)
			cursor.cerrar();
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
	public String setMensaje(String s) {
		String mensaje = "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n";
		return mensaje += "  - " + s + "\n";
	}

	/*
	 * MODIFICACION PERIODOS EVVALUACION EN LINEA 12-03-10 WG
	 */

	public List getListaPeriodo(long numPer, String nomPerDef) {
		List l = new ArrayList();
		ItemVO item = null;
		// System.out.println("ENTRO A CARGAR PERIODOS ** FILTRO EVALUACION EDIT"+numPer);
		for (int i = 1; i <= numPer; i++) {
			item = new ItemVO(i, "" + i);
			l.add(item);
		}
		item = new ItemVO(7, nomPerDef);
		l.add(item);
		return l;
	}

}