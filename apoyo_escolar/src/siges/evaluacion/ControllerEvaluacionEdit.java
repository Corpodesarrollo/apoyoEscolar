package siges.evaluacion;


//	VERSION		FECHA		AUTOR			DESCRIPCION
//		1.0		04/06/2020	JORGE CAMACHO	Se editó el método editarLogro() para que el seguimiento en el debug fue comprensible


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
import siges.evaluacion.beans.FiltroBeanEvaluacion;
import siges.evaluacion.beans.ParamsVO;
import siges.evaluacion.beans.TipoEvalVO;
import siges.evaluacion.dao.Evaluacion2DAO;
import siges.evaluacion.dao.EvaluacionDAO;
import siges.login.beans.Login;
import siges.util.Acceso;
import siges.util.Properties;

/**
 * Nombre: ControllerEvaluacionEdit<BR>
 * Descripcinn: Controla la vista de los formularios de evaluacion en linea de
 * logros, descriptores, areas ,asignaturas y promocion <BR>
 * Funciones de la pngina: Proporciona los datos necesarios a la vista para
 * pintar los formularios de evaluacion en linea y redirige el control a la
 * vista respectiva <BR>
 * Entidades afectadas: tablas de evaluacion en modo de solo lectura <BR>
 * Fecha de modificacinn: 20/07/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */
public class ControllerEvaluacionEdit extends HttpServlet {
	private Cursor cursor;// objeto que maneja las sentencias sql
	private EvaluacionDAO evaluacionDAO;//
	private Evaluacion2DAO evaluacion2DAO;//
	private ResourceBundle rb;

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
		HttpSession session = request.getSession();
		rb = ResourceBundle.getBundle("siges.evaluacion.bundle.evaluacion");
		Login login = null;
		String sig, sig2, sig3, sig4, sig5, sig6, sigComp;
		String ant;
		String er;
		String home;
		int tipo;
		sig = getServletConfig().getInitParameter("sig");
		sig2 = getServletConfig().getInitParameter("sig2");
		sig3 = getServletConfig().getInitParameter("sig3");
		sig4 = getServletConfig().getInitParameter("sig4");
		sig5 = getServletConfig().getInitParameter("sig5");
		sig6 = getServletConfig().getInitParameter("sig6");
		sigComp = getServletConfig().getInitParameter("sigComp");
		ant = getServletConfig().getInitParameter("ant");
		er = getServletContext().getInitParameter("error");
		home = getServletContext().getInitParameter("home");
		cursor = new Cursor();
		evaluacionDAO = new EvaluacionDAO(cursor);
		evaluacion2DAO = new Evaluacion2DAO(cursor);
		if (request.getParameter("tipo") == null
				|| ((String) request.getParameter("tipo")).equals("")) {
			borrarBeans(request);
			request.getSession().removeAttribute("editar");
			tipo = 2;
		} else
			tipo = Integer.parseInt((String) request.getParameter("tipo"));
		if (!asignarBeans(request)) {
			request.setAttribute(
					"mensaje",
					setMensaje("Error capturando datos de sesinn para el usuario"));
			return er;
		}
		// System.out.println("valor de TIPO=="+tipo);
		login = (Login) request.getSession().getAttribute("login");
		FiltroBeanEvaluacion filtroEvaluacion = (FiltroBeanEvaluacion) session
				.getAttribute("filtroEvaluacion");
		request.getSession().setAttribute(
				"logroanddesc",
				Acceso.getlogrosdesc(login.getInstId(),
						Long.toString(login.getVigencia_inst())));
		switch (tipo) {
		case 1:// logro
			editarLogro(request, session, login, filtroEvaluacion);
			break;
		case 2:// asig
			editarLogro(request, session, login, filtroEvaluacion);
			sig = sig2;
			break;
		case 3:// desc
			editarDescriptor(request, session, login, filtroEvaluacion);
			sig = sig3;
			break;
		case 4:// area
			editarDescriptor(request, session, login, filtroEvaluacion);
			sig = sig4;
			break;
		case 5:// rec
			editarRecuperacion(request, session, login, filtroEvaluacion);
			sig = sig5;
			break;
		case ParamsVO.EVAL_PRE:// pree
			editarPreescolar(request, login);
			sig = sig6;
			break;
		case ParamsVO.EVAL_COMP:
			editarComportamiento(request, login);
			sig = sigComp;
			break;
		}
		return sig;
	}

	/**
	 * Elimina del contexto de la sesion los beans de informacion del usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void borrarBeans(HttpServletRequest request) {
		request.getSession().removeAttribute("filtroEvaluacion");
	}

	private void editarComportamiento(HttpServletRequest request, Login login)
			throws ServletException {
		long inst = Long.parseLong(login.getInstId());
		int sed = Integer.parseInt(login.getSedeId());
		int jor = Integer.parseInt(login.getJornadaId());
		request.setAttribute("lMetodologia",
				evaluacion2DAO.getAllMetodologia(inst));
		request.setAttribute("lGrado",
				evaluacion2DAO.getAllGrado(inst, sed, jor));
		request.setAttribute("lGrupo",
				evaluacion2DAO.getAllGrupo(inst, sed, jor));
		// request.setAttribute("lPeriodo",Recursos.recursoEstatico[Recursos.PERIODO]);
		request.setAttribute("lPeriodo",
				getListaPeriodo(login.getLogNumPer(), login.getLogNomPerDef()));
	}
	
	
	
	
	
	/**
	 * Funcion: Pone en session los datos necesarios para la vista del
	 * formulario de filtro de evalucion de logros y de Asignaturas<BR>
	 * 
	 * @param HttpServletRequest
	 *            request
	 **/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void editarLogro(HttpServletRequest request, HttpSession session, Login login, FiltroBeanEvaluacion filtro) throws ServletException,	IOException {
		
		// Collection list;
		Object[] o;
		
		try {
			
			if (filtro == null) {
				filtro = new FiltroBeanEvaluacion();
				filtro.setInstitucion(login.getInstId());
				filtro.setVigencia(evaluacionDAO.getVigencia());
				filtro.setFilPlanEstudios(evaluacionDAO.getEstadoPlan(Long.parseLong(login.getInstId())));
				session.setAttribute("filtroEvaluacion", filtro);
			}
			
			if (filtro.getAsignatura() != null && !filtro.getAsignatura().equals("") && !filtro.getAsignatura().equals("-99")) {
				long asig = Long.parseLong(filtro.getAsignatura().substring(filtro.getAsignatura().lastIndexOf("|") + 1, filtro.getAsignatura().length()));
				request.setAttribute("listaDocente", evaluacionDAO.getListaDocenteAsignatura(Long.parseLong(filtro.getInstitucion()), Integer.parseInt(filtro.getMetodologia()), Integer.parseInt(filtro.getVigencia()), Integer.parseInt(filtro.getGrado()), asig));
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
			o[1] = evaluacionDAO.getVigencia();
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
			
			listas = evaluacionDAO.getFiltro(consultas, parametros);
			request.setAttribute("filtroMetodologia", listas[0]);
			request.setAttribute("filtroGrado2", listas[1]);
			request.setAttribute("filtroGradoAsignatura", listas[2]);
			request.setAttribute("filtroGrupo2", listas[3]);
			// request.setAttribute("filtroPeriodo",Recursos.recursoEstatico[Recursos.PERIODO]);
			request.setAttribute("filtroPeriodo", getListaPeriodo(login.getLogNumPer(), login.getLogNomPerDef()));
		} catch (Exception th) {
			throw new ServletException(th);
		}
	}
	
	
	
	
	
	/**
	 * Funcion: Pone en session los datos necesarios para la vista del
	 * formulario de filtro de recuperacinn
	 * 
	 * @param HttpServletRequest
	 *            request
	 **/
	public void editarRecuperacion(HttpServletRequest request,
			HttpSession session, Login login, FiltroBeanEvaluacion filtro)
			throws ServletException, IOException {
		Object[] o;
		try {
			if (filtro == null) {
				filtro = new FiltroBeanEvaluacion();
				filtro.setInstitucion(login.getInstId());
				filtro.setVigencia(evaluacionDAO.getVigencia());
				filtro.setFilPlanEstudios(evaluacionDAO.getEstadoPlan(Long
						.parseLong(login.getInstId())));
				session.setAttribute("filtroEvaluacion", filtro);
			}
			if (filtro.getAsignatura() != null
					&& !filtro.getAsignatura().equals("")
					&& !filtro.getAsignatura().equals("-99")) {
				long asig = Long.parseLong(filtro.getAsignatura().substring(
						filtro.getAsignatura().lastIndexOf("|") + 1,
						filtro.getAsignatura().length()));
				request.setAttribute("listaDocente", evaluacionDAO
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
			// vigecia en collection [2]
			o = new Object[2];
			o[0] = Properties.ENTEROLARGO;
			o[1] = evaluacionDAO.getVigencia();
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
			listas = evaluacionDAO.getFiltro(consultas, parametros);
			request.setAttribute("filtroMetodologia", listas[0]);
			request.setAttribute("filtroGrado2", listas[1]);
			request.setAttribute("filtroGradoAsignatura", listas[2]);
			request.setAttribute("filtroGrupo2", listas[3]);
			// request.setAttribute("filtroPeriodo",Recursos.recursoEstatico[Recursos.PERIODO]);
			request.setAttribute(
					"filtroPeriodo",
					getListaPeriodo(login.getLogNumPer(),
							login.getLogNomPerDef()));
		} catch (Throwable th) {
			throw new ServletException(th);
		}
	}

	/**
	 * Funcion: Pone en session los datos necesarios para la vista del
	 * formulario de filtro de evalucion de descriptores y de areas<BR>
	 * 
	 * @param HttpServletRequest
	 *            request
	 **/
	public void editarDescriptor(HttpServletRequest request,
			HttpSession session, Login login, FiltroBeanEvaluacion filtro)
			throws ServletException, IOException {
		Object[] o;
		try {
			if (filtro == null) {
				filtro = new FiltroBeanEvaluacion();
				filtro.setInstitucion(login.getInstId());
				filtro.setVigencia(evaluacionDAO.getVigencia());
				filtro.setFilPlanEstudios(evaluacionDAO.getEstadoPlan(Long
						.parseLong(login.getInstId())));
				session.setAttribute("filtroEvaluacion", filtro);
			}
			if (filtro.getArea() != null && !filtro.getArea().equals("")
					&& !filtro.getArea().equals("-99")) {
				long area = Long.parseLong(filtro.getArea().substring(
						filtro.getArea().lastIndexOf("|") + 1,
						filtro.getArea().length()));
				request.setAttribute("listaDocente", evaluacionDAO
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
			o[1] = evaluacionDAO.getVigencia();
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
			listas = evaluacionDAO.getFiltro(consultas, parametros);
			request.setAttribute("filtroGrado2", listas[0]);
			request.setAttribute("filtroGradoArea", listas[1]);
			request.setAttribute("filtroMetodologia", listas[2]);
			request.setAttribute("filtroGrupo2", listas[3]);
			// request.setAttribute("filtroPeriodo",Recursos.recursoEstatico[Recursos.PERIODO]);
			request.setAttribute(
					"filtroPeriodo",
					getListaPeriodo(login.getLogNumPer(),
							login.getLogNomPerDef()));
		} catch (Exception th) {
			throw new ServletException(th);
		}
	}

	/**
	 * Funcion: Pone en session los datos necesarios para la vista del
	 * formulario de filtro de evaluacinn de preescolar<BR>
	 * 
	 * @param HttpServletRequest
	 *            request
	 **/

	public void editarPreescolar(HttpServletRequest request, Login login)
			throws ServletException, IOException {
		long inst = Long.parseLong(login.getInstId());
		int sed = Integer.parseInt(login.getSedeId());
		int jor = Integer.parseInt(login.getJornadaId());
		request.setAttribute("lMetodologia",
				evaluacion2DAO.getAllMetodologia(inst));
		request.setAttribute("lGrado",
				evaluacion2DAO.getAllGradoDimension(inst, sed, jor));
		request.setAttribute("lGrupo",
				evaluacion2DAO.getAllGrupo(inst, sed, jor));
		// request.setAttribute("lPeriodo",Recursos.recursoEstatico[Recursos.PERIODO]);
		request.setAttribute("lPeriodo",
				getListaPeriodo(login.getLogNumPer(), login.getLogNomPerDef()));
		request.setAttribute("lDimension",
				evaluacion2DAO.getAllDimension(inst, sed, jor));
		/*
		 * CAMBIO PARA CARGAR TIPO DE EVALUACION
		 */
		try {
			TipoEvalVO tipoEval = evaluacionDAO
					.getTipoEval("-99", "-99", login);
			if (tipoEval != null) {
				// System.out
				// .println("NO ES NULO SE PUEDE VALIDAR TIPO PREESCOLAR: "
				// + tipoEval.getTipo_eval_prees());
				request.getSession().setAttribute("TIPO_EVAL_PREESCOLAR",
						new Integer(tipoEval.getTipo_eval_prees()));
			} else {
				request.getSession().setAttribute("TIPO_EVAL_PREESCOLAR",
						new Integer(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Trae de session la credencial de acceso del usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeans(HttpServletRequest request)
			throws ServletException, IOException {
		if (((Login) request.getSession().getAttribute("login")) == null)
			return false;
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
		doPost(request, response);// redirecciona la peticion a doPost
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
	 * Concatena el parametro en una variable que contiene los mensajes que se
	 * van a enviar a la vista
	 * 
	 * @param String
	 *            s
	 **/
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