package siges.boletines;

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

import siges.boletines.beans.FiltroBeanResumenAreas;
import siges.boletines.beans.FiltroBeanResumenAsignaturas;
import siges.boletines.beans.FiltroConsultaAsignaturasPerdidas;
import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Util;
import siges.login.beans.Login;

/**
 * Nombre: ControllerResumenFiltroEdit<BR>
 * Descripcinn: Controla la vista del formulario de filtro de reporte<BR>
 * Funciones de la pngina: Poner los dartos del formulario y redirigir el
 * control a la vista <BR>
 * Entidades afectadas: No aplica <BR>
 * Fecha de modificacinn: <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */

public class ControllerResumenFiltroEdit extends HttpServlet {
	private String mensaje;
	private String buscar;
	private boolean err;
	private Cursor cursor;
	private Util util;
	private HttpSession session;
	private ResourceBundle rb;
	private Collection list;
	private Object[] o;
	private Login login;
	private Integer entero = new Integer(java.sql.Types.INTEGER);
	private FiltroBeanResumenAreas filtroAreas;
	private FiltroBeanResumenAsignaturas filtroAsignaturas;
    private FiltroConsultaAsignaturasPerdidas filtroConsultaAisgnaturasPerdidas;
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
		session = request.getSession();
		rb = ResourceBundle.getBundle("siges.boletines.bundle.resumen");
		String sig, sig2,sig3;
		String ant;
		String er;
		String home;
		String per;
		int tipo;
		sig = getServletConfig().getInitParameter("sig");
		sig2 = getServletConfig().getInitParameter("sig2");
		//url de acceso a consulta de asignaturas perdidas
		sig3= getServletConfig().getInitParameter("sig3");
		ant = getServletConfig().getInitParameter("ant");
		er = getServletContext().getInitParameter("error");
		home = getServletContext().getInitParameter("home");
		err = false;
		mensaje = null;
		try {
			cursor = new Cursor();
			util = new Util(cursor);

			if (request.getParameter("tipo") == null
					|| ((String) request.getParameter("tipo")).equals("")) {
				session.removeAttribute("filtroResumenAreas");
				tipo = 1;
			} else
				tipo = Integer.parseInt((String) request.getParameter("tipo"));
			if (!asignarBeans(request)) {
				setMensaje("Error capturando datos de sesinn para el usuario");
				request.setAttribute("mensaje", mensaje);
				return er;
			}

			switch (tipo) {
			case 1:
				filtrosAreas(request);
				break;
			case 2:
				filtrosAsignaturas(request);
				sig = sig2;
				break;
			case 3:
				session.removeAttribute("alarma");				
				filtroConsultaPerdidaAsignaturas(request);
				session.removeAttribute("filtroResultado");
				sig=sig3;
				break;
			}
			if (err) {
				request.setAttribute("mensaje", mensaje);
				return er;
			}
		} catch (Exception e) {
			System.out.println("Error " + this + ": " + e.toString());
			throw new ServletException(e);
		} finally {
			if (cursor != null)
				cursor.cerrar();
			if (util != null)
				util.cerrar();
		}
		return sig;
	}

	public void filtrosAreas(HttpServletRequest request)
			throws ServletException, IOException {

		try {
			borrarBeansFiltroAreas(request);
			// System.out.println("*********////*********nENTRn POR RESUMEN AREAS!**********//**********");
			Collection list;
			String per;
			Object[] o;
			String s;
			int z;
			z = 0;
			list = new ArrayList();
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = login.getInstId();
			list.add(o);
			if (request.getAttribute("filtroSedeF") == null)
				request.setAttribute("filtroSedeF", util.getFiltro(
						rb.getString("filtroSedeInstitucion"), list));
			if (request.getAttribute("filtroJornadaF") == null)
				request.setAttribute("filtroJornadaF", util.getFiltro(
						rb.getString("filtroSedeJornadaInstitucion"), list));
			if (request.getAttribute("filtroMetodologiaF") == null)
				request.setAttribute("filtroMetodologiaF", util.getFiltro(
						rb.getString("listaMetodologiaInstitucion"), list));
			Collection cc = util.getFiltro(
					rb.getString("filtroSedeJornadaGradoInstitucion2"), list);
			request.setAttribute("filtroGradoF2", cc);
			if (request.getAttribute("filtroGrupoF") == null)
				request.setAttribute("filtroGrupoF", util.getFiltro(
						rb.getString("filtroSedeJornadaGradoGrupoInstitucion"),
						list));
			if (request.getAttribute("filtroPeriodoF") == null)
				/*
				 * request.setAttribute("filtroPeriodoF",util.getFiltro(rb.getString
				 * ("listaPeriodosInstitucion")));
				 */
				request.setAttribute(
						"filtroPeriodoF",
						getListaPeriodo(login.getLogNumPer(),
								login.getLogNomPerDef()));
			per = login.getPerfil();
			// System.out.println("nombre perfil "+per);
			if (per.equals("422")) {
				// System.out.println("iddoc "+login.getUsuarioId()+" inst "+login.getInstId()+" vig "+Long.toString(login.getVigencia_inst()));
				Collection colgg[] = util.getfiltroasdoc(login.getInstId(),
						Long.toString(login.getVigencia_inst()),
						login.getUsuarioId());
				request.setAttribute("FiltroAreas", colgg[0]);
				request.setAttribute("FiltroAsignaturas", colgg[1]);
				request.setAttribute("roldocasig", "1");
			} else {
				Collection colgg[] = util.getfiltroas(login.getInstId(),
						Long.toString(login.getVigencia_inst()));
				request.setAttribute("FiltroAreas", colgg[0]);
				request.setAttribute("FiltroAsignaturas", colgg[1]);
				request.setAttribute("roldocasig", "0");
			}
		} catch (Throwable th) {
			th.printStackTrace();
			throw new ServletException(th);
		}
	}

	public void filtroConsultaPerdidaAsignaturas(HttpServletRequest request)
			throws ServletException, IOException {

		try {
			borrarBeansFiltroAsignaturas(request);
			// System.out
			// .println("*********////*********nENTRn CONSULTA ASIGNATURAS PERIDA !**********//**********");
			Collection list;
			String per;
			Object[] o;
			String s;
			int z;
			z = 0;
			list = new ArrayList();
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = login.getInstId();
			list.add(o);
			
			if (request.getAttribute("filtroSedeF") == null)
				request.setAttribute("filtroSedeF", util.getFiltro(
						rb.getString("filtroSedeInstitucion"), list));
			if (request.getAttribute("filtroJornadaF") == null)
				request.setAttribute("filtroJornadaF", util.getFiltro(
						rb.getString("filtroSedeJornadaInstitucion"), list));
			if (request.getAttribute("filtroMetodologiaF") == null)
				request.setAttribute("filtroMetodologiaF", util.getFiltro(
						rb.getString("listaMetodologiaInstitucion"), list));
			
			Collection cc = util.getFiltro(
					rb.getString("filtroSedeJornadaGradoInstitucion2"), list);
			request.setAttribute("filtroGradoF2", cc);
			if (request.getAttribute("filtroGrupoF") == null)
				request.setAttribute("filtroGrupoF", util.getFiltro(
						rb.getString("filtroSedeJornadaGradoGrupoInstitucion"),
						list));
			if (request.getAttribute("filtroPeriodoF") == null)
				/*
				 * request.setAttribute("filtroPeriodoF",util.getFiltro(rb.getString
				 * ("listaPeriodosInstitucion")));
				 */
				request.setAttribute(
						"filtroPeriodoF",
						getListaPeriodo(login.getLogNumPer(),
								login.getLogNomPerDef()));
			per = login.getPerfil();
			// System.out.println("nombre perfil "+per);
			if (per.equals("422")) {
				// System.out.println("iddoc "+login.getUsuarioId()+" inst "+login.getInstId()+" vig "+Long.toString(login.getVigencia_inst()));
				Collection colgg[] = util.getfiltroasdoc(login.getInstId(),
						Long.toString(login.getVigencia_inst()),
						login.getUsuarioId());
				request.setAttribute("FiltroAreas", colgg[0]);
				request.setAttribute("FiltroAsignaturas", colgg[1]);
				request.setAttribute("roldocasig", "1");
			} else {
				Collection colgg[] = util.getfiltroas(login.getInstId(),
						Long.toString(login.getVigencia_inst()));
				request.setAttribute("FiltroAreas", colgg[0]);
				request.setAttribute("FiltroAsignaturas", colgg[1]);
				request.setAttribute("roldocasig", "0");
			}
		} catch (Throwable th) {
			th.printStackTrace();
			throw new ServletException(th);
		}
	}

	
	public void filtrosAsignaturas(HttpServletRequest request)
			throws ServletException, IOException {

		try {
			borrarBeansFiltroAsignaturas(request);
			// System.out
			// .println("*********////*********nENTRn POR RESUMEN ASIGNATURAS!**********//**********");
			Collection list;
			String per;
			Object[] o;
			String s;
			int z;
			z = 0;
			list = new ArrayList();
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = login.getInstId();
			list.add(o);
			if (request.getAttribute("filtroSedeF") == null)
				request.setAttribute("filtroSedeF", util.getFiltro(
						rb.getString("filtroSedeInstitucion"), list));
			if (request.getAttribute("filtroJornadaF") == null)
				request.setAttribute("filtroJornadaF", util.getFiltro(
						rb.getString("filtroSedeJornadaInstitucion"), list));
			if (request.getAttribute("filtroMetodologiaF") == null)
				request.setAttribute("filtroMetodologiaF", util.getFiltro(
						rb.getString("listaMetodologiaInstitucion"), list));
			Collection cc = util.getFiltro(
					rb.getString("filtroSedeJornadaGradoInstitucion2"), list);
			request.setAttribute("filtroGradoF2", cc);
			if (request.getAttribute("filtroGrupoF") == null)
				request.setAttribute("filtroGrupoF", util.getFiltro(
						rb.getString("filtroSedeJornadaGradoGrupoInstitucion"),
						list));
			if (request.getAttribute("filtroPeriodoF") == null)
				/*
				 * request.setAttribute("filtroPeriodoF",util.getFiltro(rb.getString
				 * ("listaPeriodosInstitucion")));
				 */
				request.setAttribute(
						"filtroPeriodoF",
						getListaPeriodo(login.getLogNumPer(),
								login.getLogNomPerDef()));
			per = login.getPerfil();
			// System.out.println("nombre perfil "+per);
			if (per.equals("422")) {
				// System.out.println("iddoc "+login.getUsuarioId()+" inst "+login.getInstId()+" vig "+Long.toString(login.getVigencia_inst()));
				Collection colgg[] = util.getfiltroasdoc(login.getInstId(),
						Long.toString(login.getVigencia_inst()),
						login.getUsuarioId());
				request.setAttribute("FiltroAreas", colgg[0]);
				request.setAttribute("FiltroAsignaturas", colgg[1]);
				request.setAttribute("roldocasig", "1");
			} else {
				Collection colgg[] = util.getfiltroas(login.getInstId(),
						Long.toString(login.getVigencia_inst()));
				request.setAttribute("FiltroAreas", colgg[0]);
				request.setAttribute("FiltroAsignaturas", colgg[1]);
				request.setAttribute("roldocasig", "0");
			}
		} catch (Throwable th) {
			th.printStackTrace();
			throw new ServletException(th);
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
		filtroAreas = (FiltroBeanResumenAreas) session
				.getAttribute("filtroResumenAreas");
		filtroAsignaturas = (FiltroBeanResumenAsignaturas) session
				.getAttribute("filtroResumenAsignaturas");
		filtroConsultaAisgnaturasPerdidas= (FiltroConsultaAsignaturasPerdidas) session.getAttribute("filtroConsultaAsignaturasPerdidas");
		String sentencia = "";
		return true;
	}

	/**
	 * Elimina del contexto de la sesion los beans de informacion del usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void borrarBeansFiltroAreas(HttpServletRequest request)
			throws ServletException, IOException {
		session.removeAttribute("filtroResumenAreas");
		// System.out
		// .println("nnnnnBorrn de sesinn el filtro de Resumen de área!!!!");
	}

	/**
	 * Elimina del contexto de la sesion los beans de informacion del usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void borrarBeansFiltroAsignaturas(HttpServletRequest request)
			throws ServletException, IOException {
		session.removeAttribute("filtroResumenAsignaturas");
		// System.out
		// .println("nnnnnBorrn de sesinn el filtro de Resumen de Asignaturas!!!!");
	}
	
	/**
	 * Elimina del contexto de la sesion los beans de informacion del usuario de filtroConsultaAsignaturasPerdidas
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void borrarBeansFiltroConsultaAsignaturasPerdidas(HttpServletRequest request)
			throws ServletException, IOException {
		session.removeAttribute("filtroConsultaAsignaturasPerdidas");
		// System.out
		// .println("nnnnnBorrn de sesinn el filtro de Resumen de Asignaturas!!!!");
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
	public void setMensaje(String s) {
		if (!err) {
			err = true;
			mensaje = "VERIFIQUE LA SIGUIENTE información: \n\n";
		}
		mensaje += "  - " + s + "\n";
	}

	/*
	 * MODIFICACION PERIODOS EVVALUACION EN LINEA 12-03-10 WG
	 */

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