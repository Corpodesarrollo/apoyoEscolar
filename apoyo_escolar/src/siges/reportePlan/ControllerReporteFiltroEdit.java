package siges.reportePlan;

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

import org.apache.commons.validator.GenericValidator;

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.Util;
import siges.login.beans.Login;
import siges.reportePlan.beans.FiltroBeanReportesAreas;
import siges.reportePlan.beans.FiltroBeanReportesDescriptores;
import siges.reportePlan.beans.FiltroBeanReportesLogros;

;

/**
 * Nombre: ControllerReporteFiltroEdit<BR>
 * Descripcinn: Controla la vista del formulario de filtro de Reporte de Plan de
 * estudios <BR>
 * Funciones de la pngina: Poner los dartos del formulario y redirigir el
 * control a la vista <BR>
 * Entidades afectadas: No aplica <BR>
 * Fecha de modificacinn: 29/07/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */

public class ControllerReporteFiltroEdit extends HttpServlet {
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
	private FiltroBeanReportesAreas filtroAreas;
	private FiltroBeanReportesLogros filtroLogros;
	private FiltroBeanReportesDescriptores filtroDescriptores;
	private Dao dao;
	private String vigencia;

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
		rb = ResourceBundle.getBundle("reportesPlan_preparedstatements");
		String sig, sig2, sig3;
		String ant;
		String er;
		String home;
		Login usuVO = (Login) request.getSession().getAttribute("login");
		int tipo;
		sig = getServletConfig().getInitParameter("sig");
		sig2 = getServletConfig().getInitParameter("sig2");
		sig3 = getServletConfig().getInitParameter("sig3");
		ant = getServletConfig().getInitParameter("ant");
		er = getServletContext().getInitParameter("error");
		home = getServletContext().getInitParameter("home");
		err = false;
		mensaje = null;

		try {
			cursor = new Cursor();
			util = new Util(cursor);
			dao = new Dao(cursor);

			if (usuVO != null && GenericValidator.isInt(usuVO.getInstId())) {
				vigencia = ""
						+ dao.getVigenciaInst(Long.parseLong(usuVO.getInstId()));
			} else {
				vigencia = dao.getVigencia();
			}

			if (vigencia == null) {
				System.out.println("VIGENCIA NULA");
				ir(2, home, request, response);
				return er;
			}

			if (request.getParameter("tipo") == null
					|| ((String) request.getParameter("tipo")).equals("")) {
				borrarBeansAreas(request);
				borrarBeansLogros(request);
				borrarBeansDescriptores(request);
				tipo = 21;
			} else
				tipo = Integer.parseInt((String) request.getParameter("tipo"));
			if (!asignarBeans(request)) {
				setMensaje("Error capturando datos de sesinn para el usuario");
				request.setAttribute("mensaje", mensaje);
				return er;
			}
			switch (tipo) {
			case 1:
				areas(request, vigencia);
				break;
			case 2:
				logros(request, vigencia);
				sig = sig2;
				break;
			case 3:
				descriptores(request, vigencia);
				sig = sig3;
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

	public void areas(HttpServletRequest request, String vigencia)
			throws ServletException, IOException {

		try {
			borrarBeansAreas(request);
			// System.out.println("*********////*********nENTRn POR AREAS Y ASIGNATURAS!**********//**********");
			Collection list;
			Object[] o;
			String s;
			int z;
			z = 0;
			list = new ArrayList();
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = login.getInstId();
			list.add(o);
			/*
			 * if(request.getAttribute("filtroSedeF")==null)
			 * request.setAttribute
			 * ("filtroSedeF",util.getFiltro(rb.getString("filtroSedeInstitucion"
			 * ),list)); if(request.getAttribute("filtroJornadaF")==null)
			 * request
			 * .setAttribute("filtroJornadaF",util.getFiltro(rb.getString(
			 * "filtroSedeJornadaInstitucion"),list));
			 */
			request.setAttribute("listaVigencia", util
					.getListaVigenciaInst(Long.parseLong(login.getInstId())));
			if (request.getAttribute("filtroMetodologiaF") == null)
				request.setAttribute("filtroMetodologiaF", util.getFiltro(
						rb.getString("listaMetodologiaInstitucion"), list));
			Collection cc = util.getFiltro(
					rb.getString("filtroGradoInstitucion2"), list);
			request.setAttribute("filtroGradoF2", cc);
			z = 0;
			list = new ArrayList();
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = login.getInstId();
			list.add(o);
			z = 0;
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = vigencia;
			list.add(o);
			if (request.getAttribute("filtroAreaF") == null)
				request.setAttribute("filtroAreaF", util.getFiltro(
						rb.getString("filtroAreasInstitucion"), list));
		} catch (Throwable th) {
			System.out.println("Error paila: " + th);
			throw new ServletException(th);
		}

	}

	public void logros(HttpServletRequest request, String vigencia)
			throws ServletException, IOException {
		try {
			borrarBeansLogros(request);

			// System.out.println("*********////*********nENTRn POR LOGROS!**********//**********");
			Collection list;
			Object[] o;
			String s;
			int z;
			z = 0;
			list = new ArrayList();
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = login.getInstId();
			list.add(o);
			// if(request.getAttribute("filtroSedeF")==null)
			// request.setAttribute("filtroSedeF",util.getFiltro(rb.getString("filtroSedeInstitucion"),list));
			// if(request.getAttribute("filtroJornadaF")==null)
			// request.setAttribute("filtroJornadaF",util.getFiltro(rb.getString("filtroSedeJornadaInstitucion"),list));
			//
			request.setAttribute("listaVigencia", util
					.getListaVigenciaInst(Long.parseLong(login.getInstId())));
			if (request.getAttribute("filtroMetodologiaF") == null)
				request.setAttribute("filtroMetodologiaF", util.getFiltro(
						rb.getString("listaMetodologiaInstitucion"), list));
			Collection cc = util.getFiltro(
					rb.getString("filtroGradoInstitucion2"), list);
			request.setAttribute("filtroGradoF2", cc);
			z = 0;
			list = new ArrayList();
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = login.getInstId();
			list.add(o);
			z = 0;
			o = new Object[2];
			// System.out.println("VIGENCIA: " + vigencia);
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = vigencia;
			list.add(o);
			if (request.getAttribute("filtroAsignaturaF") == null)
				request.setAttribute("filtroAsignaturaF", util.getFiltro(
						rb.getString("filtroAsignaturasInstitucion"), list));
			// if(request.getAttribute("filtroPeriodoF")==null)
			// request.setAttribute("filtroPeriodoF",util.getFiltro(rb.getString("listaPeriodosInstitucion")));
			//
			request.setAttribute(
					"filtroPeriodoF",
					getListaPeriodo(login.getLogNumPer(),
							login.getLogNomPerDef()));
		} catch (Throwable th) {
			System.out.println("Error " + th);
			throw new ServletException(th);
		}
	}

	public void descriptores(HttpServletRequest request, String vigencia)
			throws ServletException, IOException {
		try {
			borrarBeansDescriptores(request);

			// System.out.println("*********////*********nENTRn POR DESCRIPTORES!**********//**********");
			Collection list;
			Object[] o;
			String s;
			int z;
			z = 0;
			list = new ArrayList();
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = login.getInstId();
			list.add(o);
			// if(request.getAttribute("filtroSedeF")==null)
			// request.setAttribute("filtroSedeF",util.getFiltro(rb.getString("filtroSedeInstitucion"),list));
			// if(request.getAttribute("filtroJornadaF")==null)
			// request.setAttribute("filtroJornadaF",util.getFiltro(rb.getString("filtroSedeJornadaInstitucion"),list));
			//
			request.setAttribute("listaVigencia", util
					.getListaVigenciaInst(Long.parseLong(login.getInstId())));

			if (request.getAttribute("filtroMetodologiaF") == null)
				request.setAttribute("filtroMetodologiaF", util.getFiltro(
						rb.getString("listaMetodologiaInstitucion"), list));
			Collection cc = util.getFiltro(
					rb.getString("filtroGradoInstitucion2"), list);
			request.setAttribute("filtroGradoF2", cc);
			z = 0;
			list = new ArrayList();
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = login.getInstId();
			list.add(o);
			z = 0;
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = vigencia;
			list.add(o);
			if (request.getAttribute("filtroArea") == null)
				request.setAttribute("filtroArea", util.getFiltro(
						rb.getString("filtroAreasInstitucion"), list));
			// if(request.getAttribute("filtroPeriodo")==null)
			// request.setAttribute("filtroPeriodo",util.getFiltro(rb.getString("listaPeriodosInstitucion")));

			request.setAttribute(
					"filtroPeriodo",
					getListaPeriodo(login.getLogNumPer(),
							login.getLogNomPerDef()));

		} catch (Throwable th) {
			System.out.println("Error " + th);
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
		filtroAreas = (FiltroBeanReportesAreas) session
				.getAttribute("filtroAreas");
		filtroLogros = (FiltroBeanReportesLogros) session
				.getAttribute("filtroLogros");
		filtroDescriptores = (FiltroBeanReportesDescriptores) session
				.getAttribute("filtroDescriptores");
		String sentencia = "";
		return true;
	}

	/**
	 * Elimina del contexto de la sesion los beans de informacion del usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void borrarBeansAreas(HttpServletRequest request)
			throws ServletException, IOException {
		session.removeAttribute("filtroAreas");
	}

	/**
	 * Elimina del contexto de la sesion los beans de informacion del usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void borrarBeansLogros(HttpServletRequest request)
			throws ServletException, IOException {
		session.removeAttribute("filtroLogros");
	}

	/**
	 * Elimina del contexto de la sesion los beans de informacion del usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void borrarBeansDescriptores(HttpServletRequest request)
			throws ServletException, IOException {
		session.removeAttribute("filtroDescriptores");
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

	public List getListaPeriodo(long numPer, String nomPerDef) {
		List l = new ArrayList();
		ItemVO item = null;
		for (int i = 1; i <= numPer; i++) {
			item = new ItemVO(i, "" + i);
			l.add(item);
		}
		item = new ItemVO(7, nomPerDef);
		l.add(item);
		return l;
	}
}