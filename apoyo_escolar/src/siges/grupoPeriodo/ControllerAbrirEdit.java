package siges.grupoPeriodo;

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

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.exceptions.InternalErrorException;
import siges.grupoPeriodo.dao.GrupoPeriodoDAO;
import siges.login.beans.Login;
import siges.util.Properties;

/**
 * Nombre: ControllerAbrirEdit<BR>
 * Descripcinn: Controla la vista del formulario de apertura de grupos y cierre
 * de periodo<BR>
 * Funciones de la pngina: Pone los datos necesarios en la vista de formulario
 * de consulta y redirige el control a este <BR>
 * Entidades afectadas: No aplica <BR>
 * Fecha de modificacinn: 20/07/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */
public class ControllerAbrirEdit extends HttpServlet {
	private String mensaje;// mensaje en caso de error
	private String buscar;
	private boolean err;// variable que inidica si hay o no errores en la
						// validacion de los datos del formulario
	private Cursor cursor;// objeto que maneja las sentencias sql
	private GrupoPeriodoDAO grupoPeriodoDAO;
	private ResourceBundle rb;
	private Login login;
	private int tipo;

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
		rb = ResourceBundle.getBundle("grupoPeriodo");
		String sig, sig2;
		String ant;
		String er;
		String home;
		int tipo;
		sig = "/grupoPeriodo/AbrirGrupo.jsp";
		sig2 = "/grupoPeriodo/CerrarPeriodo.jsp";
		ant = "/index.jsp";
		er = "/error.jsp";
		home = "/bienvenida.jsp";
		cursor = new Cursor();
		err = false;
		mensaje = null;
		Collection list;
		Object[] o;
		grupoPeriodoDAO = new GrupoPeriodoDAO(cursor);
		if (!asignarBeans(request)) {
			setMensaje("Error capturando datos de sesinn para el usuario");
			request.setAttribute("mensaje", mensaje);
			return er;
		}
		if (request.getParameter("tipo") == null
				|| ((String) request.getParameter("tipo")).equals("")) {
			tipo = 1;
		} else
			tipo = Integer.parseInt((String) request.getParameter("tipo"));
		request.getSession().removeAttribute("observacionGrupoVO");
		request.getSession().removeAttribute("observacionPeriodoVO");
		System.out.println("tipo " + tipo);
		try {
			switch (tipo) {
			case 1:
				cargarGrupo(request, login);
				break;
			case 2:
				request.getSession().removeAttribute("abrirGrupo");
				cargarPeriodo(request, login);
				sig = sig2;
				break;
			}
		} catch (Exception e) {
			System.out.println("Error " + this + ":" + e.toString());
			throw new ServletException(e);
		} finally {
			if (cursor != null)
				cursor.cerrar();
			if (grupoPeriodoDAO != null)
				grupoPeriodoDAO.cerrar();
		}
		return sig;
	}

	public void cargarPeriodo(HttpServletRequest request, Login login)
			throws InternalErrorException {
		request.setAttribute("filtroPeriodos_per",
				getListaPeriodo(login.getLogNumPer(), login.getLogNomPerDef()));
		Collection datos = grupoPeriodoDAO.getPeriodos(login.getInstId());
		request.setAttribute("filtroPeriodosAbiertos", datos);
		request.getSession().setAttribute("periodosAbiertosList", datos);
		request.setAttribute("filtroSedesJornadas",
				grupoPeriodoDAO.getSedesJornadas(login.getInstId()));

	}

	public void cargarGrupo(HttpServletRequest request, Login login)
			throws InternalErrorException {
		Collection list;
		Object[] o;
		String s;
		int z;
		z = 0;
		list = new ArrayList();
		o = new Object[2];
		o[z++] = new Integer(java.sql.Types.BIGINT);
		o[z++] = login.getInstId();
		list.add(o);
		Collection listas[] = new Collection[7];
		Collection parametros[] = new Collection[8];
		for (int zz = 0; zz < parametros.length; zz++) {
			parametros[zz] = new ArrayList();
			parametros[zz].add(o);
		}
		// vigencia Asignatura
		o = new Object[2];
		o[0] = Properties.ENTEROLARGO;
		o[1] = grupoPeriodoDAO.getVigencia();
		parametros[5].add(o);
		// vigencia Area
		parametros[6].add(o);

		String consultas[] = { rb.getString("filtroSedeInstitucion"),
				rb.getString("filtroSedeJornadaInstitucion"),
				rb.getString("listaMetodologiaInstitucion"),
				rb.getString("filtroSedeJornadaGradoInstitucion2"),
				rb.getString("filtroSedeJornadaGradoGrupoInstitucion"),
				rb.getString("filtroAsignaturaInstitucion")

		};
		listas = grupoPeriodoDAO.getFiltro(consultas, parametros);
		request.setAttribute("filtroSedeAbrir", listas[0]);
		request.setAttribute("filtroJornadaAbrir", listas[1]);
		request.setAttribute("filtroMetodologiaAbrir", listas[2]);
		request.setAttribute("filtroGradoAbrir", listas[3]);
		request.setAttribute("filtroGrupoAbrir", listas[4]);
		request.setAttribute("filtroAsignaturaAbrir", listas[5]);
		request.setAttribute("filtroAreaAbrir",
				grupoPeriodoDAO.getAreasInst(Long.parseLong(login.getInstId())));
		request.setAttribute("filtroPeriodoAbrir",
				getListaPeriodo(login.getLogNumPer(), login.getLogNomPerDef()));
	}

	/**
	 * Trae de session la credencial de aceso del usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeans(HttpServletRequest request)
			throws ServletException, IOException {
		if (request.getSession().getAttribute("login") != null) {
			login = (Login) request.getSession().getAttribute("login");
			return true;
		}
		return false;
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
			mensaje = "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n";
		}
		mensaje += "  - " + s + "\n";
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