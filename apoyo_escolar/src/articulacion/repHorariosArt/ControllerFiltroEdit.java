package articulacion.repHorariosArt;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import articulacion.repHorariosArt.dao.repHorariosArtDAO;
import articulacion.repHorariosArt.vo.*;
import siges.dao.*;
import siges.common.vo.ItemVO;
import siges.login.beans.Login;

;

/**
 * Nombre: ControllerBoletinFiltroEdit<BR>
 * Descripcinn: Controla la vista del formulario de filtro de Boletines <BR>
 * Funciones de la pngina: Poner los dartos del formulario y redirigir el
 * control a la vista <BR>
 * Entidades afectadas: No aplica <BR>
 * Fecha de modificacinn: 29/07/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */

public class ControllerFiltroEdit extends HttpServlet {
	private boolean err;
	private Cursor cursor;
	private Util util;
	private repHorariosArtDAO repHorariosArt;

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
		HttpSession session;
		session = request.getSession();
		String sede;
		String jornada;
		String sig, sig2, sig3, sig4, sig5, sig6;
		String ant;
		String er;
		String home;
		int tipo;
		sig = "/articulacion/repHorariosArt/FiltroGrupo.jsp";
		sig2 = "/articulacion/repHorariosArt/FiltroDocente.jsp";
		sig3 = "/articulacion/repHorariosArt/FiltroEspacioFisico.jsp";
		sig4 = "/articulacion/repHorariosArt/FiltroEstudiante.jsp";
		sig5 = "/articulacion/repHorariosArt/FiltroEstudianteEst.jsp";
		sig6 = "/articulacion/repHorariosArt/FiltroGruposArt.jsp";
		ant = "/index.jsp";
		er = "/error.jsp";
		home = "/bienvenida.jsp";
		err = false;
		try {
			cursor = new Cursor();
			util = new Util(cursor);
			Login login = (Login) session.getAttribute("login");
			if (request.getParameter("tipo") == null
					|| ((String) request.getParameter("tipo")).equals("")) {
				session.removeAttribute("filtro");
				session.removeAttribute("filtroDoc");
				session.removeAttribute("filtroEspacio");
				session.removeAttribute("filtroEst");
				session.removeAttribute("filtroGrupos");
				tipo = 5;
			} else
				tipo = Integer.parseInt((String) request.getParameter("tipo"));
			sede = login.getSedeId();
			jornada = login.getJornadaId();
			// System.out.println("**SEDE**"+sede);
			// System.out.println("**JORNADA**"+jornada);
			if (sede.equals("") || jornada.equals("")) {
				request.setAttribute("mensaje", "Sede o Jornada son NULL");
				return home;
			}
			// System.out.println("TIPO:"+tipo);
			switch (tipo) {
			case 1:
				filtroGrupo(login, session, request);
				break;
			case 2:
				filtroDocente(login, session, request);
				sig = sig2;
				break;
			case 3:
				filtroEspacio(login, session, request);
				sig = sig3;
				break;
			case 4:
				filtroEstudiante(login, session, request);
				sig = sig4;
				break;
			case 5:
				filtroGruposArt(login, session, request);
				sig = sig6;
				break;
			}

			if (err) {
				request.setAttribute("mensaje", "Error interno");
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

	// FILTRO ESTUDIANTE (por:Wilmar Gnmez - 31/10/2007)
	public void filtroEstudiante(Login login, HttpSession session,
			HttpServletRequest request) throws ServletException, IOException {

		try {
			borrarBeansEstudiante(session, request);
			// FiltroBeanRepEstudiante filtroEst = (FiltroBeanRepEstudiante)
			// request.getSession().getAttribute("filtroEst");
			repHorariosArt = new repHorariosArtDAO(cursor);
			long inst = -99;
			int sede1 = -99;
			int jor1 = -99;
			inst = Long.parseLong(login.getInstId());
			// SEDE
			request.setAttribute("lSedeVO", repHorariosArt.getSede(inst, sede1));
			// JORNADA
			request.setAttribute("lJornadaVO",
					repHorariosArt.getJornada(inst, sede1, jor1));

			request.setAttribute("lEspecialidadVO",
					repHorariosArt.getAjaxEspecialidad(inst));
			// ano vigencia
			List l = new ArrayList();
			l.add(String.valueOf(repHorariosArt.getVigenciaSis() - 1));
			l.add(String.valueOf(repHorariosArt.getVigenciaSis()));
			l.add(String.valueOf(repHorariosArt.getVigenciaSis() + 1));
			request.setAttribute("VigenciaArtHor", l);
			// periodos de vigencia
			l = new ArrayList();
			l.add("1");
			l.add("2");
			request.setAttribute("PeriodoArtHor", l);
			// componente
			l = new ArrayList();
			ItemVO itemCom = null;
			itemCom = new ItemVO();
			itemCom.setCodigo(1);
			itemCom.setNombre("Acadnmico");
			l.add(itemCom);
			ItemVO itemCom2 = null;
			itemCom2 = new ItemVO();
			itemCom2.setCodigo(2);
			itemCom2.setNombre("Tncnico");
			l.add(itemCom2);
			request.setAttribute("comArt", l);
		} catch (Throwable th) {
			th.printStackTrace();
			throw new ServletException(th);
		}
	}

	public void filtroGrupo(Login login, HttpSession session,
			HttpServletRequest request) throws ServletException, IOException {

		try {
			borrarBeansGrupo(session, request);
			// FiltroBeanReporte filtroGru = (FiltroBeanReporte)
			// request.getSession().getAttribute("filtroGru");
			repHorariosArt = new repHorariosArtDAO(cursor);
			long inst = -99;
			int sede = -99;
			int jor = -99;
			inst = Long.parseLong(login.getInstId());
			// SEDE
			request.setAttribute("lSedeVO", repHorariosArt.getSede(inst, sede));
			// JORNADA
			request.setAttribute("lJornadaVO",
					repHorariosArt.getJornada(inst, sede, jor));

			request.setAttribute("lEspecialidadVO",
					repHorariosArt.getAjaxEspecialidad(inst));
			// ano vigencia
			List l = new ArrayList();
			l.add(String.valueOf(repHorariosArt.getVigenciaSis() - 1));
			l.add(String.valueOf(repHorariosArt.getVigenciaSis()));
			l.add(String.valueOf(repHorariosArt.getVigenciaSis() + 1));
			request.setAttribute("VigenciaArtHor", l);
			// periodos de vigencia
			l = new ArrayList();
			l.add("1");
			l.add("2");
			request.setAttribute("PeriodoArtHor", l);
			// componente
			l = new ArrayList();
			ItemVO itemCom = null;
			itemCom = new ItemVO();
			itemCom.setCodigo(1);
			itemCom.setNombre("Acadnmico");
			l.add(itemCom);
			ItemVO itemCom2 = null;
			itemCom2 = new ItemVO();
			itemCom2.setCodigo(2);
			itemCom2.setNombre("Tncnico");
			l.add(itemCom2);
			request.setAttribute("comArt", l);
		} catch (Throwable th) {
			th.printStackTrace();
			throw new ServletException(th);
		}
	}

	public void filtroDocente(Login login, HttpSession session,
			HttpServletRequest request) throws ServletException, IOException {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;

		try {
			borrarBeansDocente(session, request);
			// System.out.println("*********////*********nfiltroDocente!**********//**********");
			// FiltroBeanReporteDocente filtroDoc = (FiltroBeanReporteDocente)
			// request.getSession().getAttribute("filtroDoc");
			repHorariosArt = new repHorariosArtDAO(cursor);
			long inst = -99;
			int sede = -99;
			int jor = -99;
			inst = Long.parseLong(login.getInstId());
			// SEDE
			request.setAttribute("lSedeVO", repHorariosArt.getSede(inst, sede));
			// JORNADA
			request.setAttribute("lJornadaVO",
					repHorariosArt.getJornada(inst, sede, jor));
			// ano vigencia
			List l = new ArrayList();
			l.add(String.valueOf(repHorariosArt.getVigenciaSis() - 1));
			l.add(String.valueOf(repHorariosArt.getVigenciaSis()));
			l.add(String.valueOf(repHorariosArt.getVigenciaSis() + 1));
			request.setAttribute("VigenciaArtHor", l);
			// periodos de vigencia
			l = new ArrayList();
			l.add("1");
			l.add("2");
			request.setAttribute("PeriodoArtHor", l);
			// componente
			l = new ArrayList();
			ItemVO itemCom = null;
			itemCom = new ItemVO();
			itemCom.setCodigo(1);
			itemCom.setNombre("Acadnmico");
			l.add(itemCom);
			ItemVO itemCom2 = null;
			itemCom2 = new ItemVO();
			itemCom2.setCodigo(2);
			itemCom2.setNombre("Tncnico");
			l.add(itemCom2);
			request.setAttribute("comArt", l);

		} catch (Throwable th) {
			System.out.println("Error " + th);
			throw new ServletException(th);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	public void filtroEspacio(Login login, HttpSession session,
			HttpServletRequest request) throws ServletException, IOException {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;

		try {
			borrarBeansEspacio(session, request);
			// System.out.println("*********////*********nfiltroEspacio!**********//**********");
			// filtroEspacio = (FiltroBeanReporteEspacio)
			// request.getSession().getAttribute("filtroEspacio");
			repHorariosArt = new repHorariosArtDAO(cursor);
			long inst = -99;
			int sede = -99;
			int jor = -99;
			inst = Long.parseLong(login.getInstId());
			// SEDE
			request.setAttribute("lSedeVO", repHorariosArt.getSede(inst, sede));
			// JORNADA
			request.setAttribute("lJornadaVO",
					repHorariosArt.getJornada(inst, sede, jor));
			// ano vigencia
			List l = new ArrayList();
			l.add(String.valueOf(repHorariosArt.getVigenciaSis() - 1));
			l.add(String.valueOf(repHorariosArt.getVigenciaSis()));
			l.add(String.valueOf(repHorariosArt.getVigenciaSis() + 1));
			request.setAttribute("VigenciaArtHor", l);
			// periodos de vigencia
			l = new ArrayList();
			l.add("1");
			l.add("2");
			request.setAttribute("PeriodoArtHor", l);
			// componente
			l = new ArrayList();
			ItemVO itemCom = null;
			itemCom = new ItemVO();
			itemCom.setCodigo(1);
			itemCom.setNombre("Acadnmico");
			l.add(itemCom);
			ItemVO itemCom2 = null;
			itemCom2 = new ItemVO();
			itemCom2.setCodigo(2);
			itemCom2.setNombre("Tncnico");
			l.add(itemCom2);
			request.setAttribute("comArt", l);
			// TIPOS DE ESPACIOS FISICOS
			// if(request.getAttribute("TiposEspacio")==null)
			request.setAttribute("TiposEspacio",
					repHorariosArt.getTiposEspacio());

		} catch (Throwable th) {
			System.out.println("Error " + th);
			throw new ServletException(th);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	public void filtroGruposArt(Login login, HttpSession session,
			HttpServletRequest request) throws ServletException, IOException {

		try {
			borrarBeansGruposArt(session, request);
			// FiltroBeanReporteGrupos filtroGrupos = (FiltroBeanReporteGrupos)
			// request.getSession().getAttribute("filtroGrupos");
			repHorariosArt = new repHorariosArtDAO(cursor);
			long inst = -99;
			int sede = -99;
			int jor = -99;
			inst = Long.parseLong(login.getInstId());
			// SEDE
			request.setAttribute("lSedeVO", repHorariosArt.getSede(inst, sede));
			// JORNADA
			request.setAttribute("lJornadaVO",
					repHorariosArt.getJornada(inst, sede, jor));

			request.setAttribute("lEspecialidadVO",
					repHorariosArt.getAjaxEspecialidad(inst));
			// ano vigencia
			List l = new ArrayList();
			l.add(String.valueOf(repHorariosArt.getVigenciaSis() - 1));
			l.add(String.valueOf(repHorariosArt.getVigenciaSis()));
			l.add(String.valueOf(repHorariosArt.getVigenciaSis() + 1));
			request.setAttribute("VigenciaArtHor", l);
			// periodos de vigencia
			l = new ArrayList();
			l.add("1");
			l.add("2");
			request.setAttribute("PeriodoArtHor", l);
			// componente
			l = new ArrayList();
			ItemVO itemCom = null;
			itemCom = new ItemVO();
			itemCom.setCodigo(1);
			itemCom.setNombre("Acadnmico");
			l.add(itemCom);
			ItemVO itemCom2 = null;
			itemCom2 = new ItemVO();
			itemCom2.setCodigo(2);
			itemCom2.setNombre("Tncnico");
			l.add(itemCom2);
			request.setAttribute("comArt", l);
		} catch (Throwable th) {
			th.printStackTrace();
			throw new ServletException(th);
		}
	}

	/**
	 * Elimina del contexto de la sesion los beans de informacion del usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void borrarBeansEstudiante(HttpSession session,
			HttpServletRequest request) throws ServletException, IOException {
		session.removeAttribute("filtroEst");
	}

	/**
	 * Elimina del contexto de la sesion los beans de informacion del usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void borrarBeansGrupo(HttpSession session, HttpServletRequest request)
			throws ServletException, IOException {
		session.removeAttribute("filtro");
	}

	public void borrarBeansGruposArt(HttpSession session,
			HttpServletRequest request) throws ServletException, IOException {
		session.removeAttribute("filtroGrupos");
	}

	/**
	 * Elimina del contexto de la sesion los beans de informacion del usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void borrarBeansDocente(HttpSession session,
			HttpServletRequest request) throws ServletException, IOException {
		session.removeAttribute("filtroDoc");
	}

	/**
	 * Elimina del contexto de la sesion los beans de informacion del usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void borrarBeansEspacio(HttpSession session,
			HttpServletRequest request) throws ServletException, IOException {
		session.removeAttribute("filtroEspacio");
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
		// System.out.println("VAR S: "+s);
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

}