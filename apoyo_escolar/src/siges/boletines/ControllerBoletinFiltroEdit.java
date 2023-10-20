package siges.boletines;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

import siges.boletines.beans.FiltroBeanCertificados;
import siges.boletines.beans.FiltroBeanConstancias;
import siges.boletines.beans.FiltroBeanFormulario;
import siges.boletines.beans.FiltroBeanReports;
import siges.boletines.dao.ReporteLogrosDAO;
import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import siges.dao.Util;
import siges.login.beans.Login;

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

public class ControllerBoletinFiltroEdit extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mensaje;
	private String buscar;
	private boolean err;
	private Cursor cursor;
	private Util util;
	private ReporteLogrosDAO reportesDAO;
	private HttpSession session;
	private ResourceBundle rb;
	private Collection list;
	private Object[] o;
	private Login login;
	private Integer entero = new Integer(java.sql.Types.INTEGER);
	private FiltroBeanReports filtro;
	private FiltroBeanCertificados filtroc;
	private FiltroBeanConstancias filtrocon;
	private FiltroBeanFormulario filtroLogros;
	private String sede;
	private String jornada;

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
		rb = ResourceBundle.getBundle("boletines_preparedstatements");

		String sig, sig2, sig3, sig4;
		String ant;
		String er;
		String home;
		int tipo;
		sig = "/boletines/GenerarBoletinesPorLogro.jsp";
		sig2 = "/boletines/GenerarCertificados.jsp";
		sig3 = "/boletines/GenerarConstancias.jsp";
		sig4 = "/boletines/filtroReporte.jsp";
		ant = "/index.jsp";
		er = "/error.jsp";
		home = "/bienvenida.jsp";
		err = false;
		mensaje = null;

		try {
			cursor = new Cursor();
			util = new Util(cursor);
			reportesDAO = new ReporteLogrosDAO(cursor);
			if (request.getParameter("tipo") == null
					|| ((String) request.getParameter("tipo")).equals("")) {
				session.removeAttribute("filtrob");
				session.removeAttribute("filtrobc");
				session.removeAttribute("filtrobcc");
				session.removeAttribute("filtroLog");
				tipo = 1;
			} else
				tipo = Integer.parseInt((String) request.getParameter("tipo"));
			if (!asignarBeans(request)) {
				setMensaje("Error capturando datos de sesinn para el usuario");
				request.setAttribute("mensaje", mensaje);
				return er;
			}
			sede = login.getSedeId();
			jornada = login.getJornadaId();
			// System.out.println("**SEDE**"+sede);
			// System.out.println("**JORNADA**"+jornada);
			if (sede.equals("") || jornada.equals("")) {
				setMensaje("Sede o Jornada son NULL");
				request.setAttribute("mensaje", mensaje);
				return home;
			}

			// System.out.println("TIPO:" + tipo);
			if(login.getPerfil().equalsIgnoreCase("510")){
				request.setAttribute("listaHijos", reportesDAO.getHijosFamilia(Long.parseLong(login.getUsuarioId())));
			}
			
			switch (tipo) {
			case 1:
				request.getSession().removeAttribute("filtrob");
				boletines(request, login);
				break;
			case 2:
				certificados(request);
				sig = sig2;
				break;
			case 3:
				constancias(request);
				sig = sig3;
				break;
			case 4:
				logrosPendientes(request);
				sig = sig4;
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

	public void boletines(HttpServletRequest request, Login login)
			throws ServletException, IOException {

		try {
			borrarBeansBoletines(request);
			// System.out
			// .println("*********////*********nENTRn POR BOLETINES!**********//**********");
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
			z = 0;
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = sede;
			list.add(o);
			if (request.getAttribute("filtroSedeF") == null)
				request.setAttribute("filtroSedeF", util.getFiltro(rb.getString("filtroSedeInstitucion"), list));
			z = 0;
			list = new ArrayList();
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = login.getInstId();
			// System.out.println("inst_" + login.getInstId());
			list.add(o);
			z = 0;
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = sede;
			// System.out.println("sede_" + sede);
			list.add(o);
			z = 0;
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = jornada;
			// System.out.println("jorn_" + jornada);
			list.add(o);
			if (request.getAttribute("filtroJornadaF") == null)
				request.setAttribute("filtroJornadaF", util.getFiltro(rb.getString("filtroSedeJornadaInstitucion"), list));
			z = 0;
			list = new ArrayList();
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = login.getInstId();
			list.add(o);
			if (request.getAttribute("filtroMetodologiaF") == null)
				request.setAttribute("filtroMetodologiaF", util.getFiltro(rb.getString("listaMetodologiaInstitucion"), list));
			z = 0;
			list = new ArrayList();
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = login.getInstId();
			list.add(o);
			z = 0;
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = sede;
			list.add(o);
			z = 0;
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = jornada;
			list.add(o);
			Collection cc = util.getFiltro(
					rb.getString("filtroSedeJornadaGradoInstitucion2"), list);
			request.setAttribute("filtroGradoF2", cc);
			if (request.getAttribute("filtroGrupoF") == null)
				request.setAttribute("filtroGrupoF", util.getFiltro(
						rb.getString("filtroSedeJornadaGradoGrupoInstitucion"),
						list));
			if (request.getAttribute("filtroPeriodoF") == null)
				request.setAttribute("filtroPeriodoF", util.getFiltro(rb
						.getString("listaPeriodosInstitucion")));
			// CAMBIOS BOLETIN ABRIL 2010 - WG
			long vigenciaActual = reportesDAO.getVigenciaInst(Long
					.parseLong(login.getInstId()));
			List vigencia = new ArrayList();
			// for(int vig=2007; vig<=vigenciaActual;vig++){
			ItemVO itemVig = new ItemVO();
			itemVig.setCodigo(vigenciaActual);
			itemVig.setNombre(String.valueOf(vigenciaActual));
			vigencia.add(itemVig);
			// }
			request.setAttribute("filtroVigencia", vigencia);
			request.setAttribute("filtroTiposDoc", reportesDAO.getTiposDoc());
			// if(filtro==null){
			filtro = new FiltroBeanReports();
			// }
			filtro.setFilVigencia(vigenciaActual);
			request.getSession().setAttribute("filtrob", filtro);
			request.setAttribute(
					"filtroPeriodo",
					getListaPeriodo(login.getLogNumPer(),
							login.getLogNomPerDef()));

		} catch (Throwable th) {
			th.printStackTrace();
			throw new ServletException(th);
		}
	}

	public void certificados(HttpServletRequest request)
			throws ServletException, IOException {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		String vig2 = null;
		try {
			borrarBeansCertificados(request);

			// System.out
			// .println("*********////*********nENTRn POR CERTIFICADOS!**********//**********");
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
			z = 0;
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = sede;
			list.add(o);
			if (request.getAttribute("filtroSedeF") == null)
				request.setAttribute("filtroSedeF", util.getFiltro(
						rb.getString("filtroSedeInstitucion"), list));
			z = 0;
			list = new ArrayList();
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = login.getInstId();
			// System.out.println("inst_" + login.getInstId());
			list.add(o);
			z = 0;
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = sede;
			System.out.println("sede_" + sede);
			list.add(o);
			z = 0;
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = jornada;
			// System.out.println("jorn_" + jornada);
			list.add(o);
			if (request.getAttribute("filtroJornadaF") == null)
				request.setAttribute("filtroJornadaF", util.getFiltro(
						rb.getString("filtroSedeJornadaInstitucion"), list));
			z = 0;
			list = new ArrayList();
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = login.getInstId();
			list.add(o);
			if (request.getAttribute("filtroMetodologiaF") == null)
				request.setAttribute("filtroMetodologiaF", util.getFiltro(
						rb.getString("listaMetodologiaInstitucion"), list));
			z = 0;
			list = new ArrayList();
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = login.getInstId();
			list.add(o);
			z = 0;
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = sede;
			list.add(o);
			z = 0;
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = jornada;
			list.add(o);
			Collection cc = util.getFiltro(
					rb.getString("filtroSedeJornadaGradoInstitucion2"), list);
			request.setAttribute("filtroGradoF2", cc);
			if (request.getAttribute("filtroGrupoF") == null)
				request.setAttribute("filtroGrupoF", util.getFiltro(
						rb.getString("filtroSedeJornadaGradoGrupoInstitucion"),
						list));
			/*
			 * con=cursor.getConnection();
			 * pst=con.prepareStatement(rb.getString("AnioActual"));
			 * rs=pst.executeQuery(); if(rs.next()) vig=rs.getString(1);
			 * System.out.println("***valor de vig: ***"+vig); rs.close();
			 * pst.close(); if(vig!=null){
			 * if(request.getAttribute("AnioActual")==null)
			 * request.setAttribute("AnioActual",new Integer(vig)); }
			 */
			long vigenciaActual = reportesDAO.getVigenciaInst(Long
					.parseLong(login.getInstId()));
			List vigencia = new ArrayList();
			for (int vig = 2005; vig <= vigenciaActual; vig++) {
				ItemVO itemVig = new ItemVO();
				itemVig.setCodigo(vig);
				itemVig.setNombre(String.valueOf(vig));
				vigencia.add(itemVig);
			}

			List l = new ArrayList();
			ItemVO item = null;
			item = new ItemVO(1, "Semestre 1");
			l.add(item);
			item = new ItemVO(2, "Semestre 2");
			l.add(item);
			item = new ItemVO(3, "Anual");
			l.add(item);
			request.setAttribute("filtroTipoProm", l);

			request.setAttribute("filtroVigencia", vigencia);
			request.setAttribute("filtroTiposDoc", reportesDAO.getTiposDoc());
			filtro = new FiltroBeanReports();
			// }
			filtro.setFilVigencia(vigenciaActual);
			filtro.setTipoProm(3);
			request.getSession().setAttribute("filtroc", filtro);
		} catch (Throwable th) {
			th.printStackTrace();
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

	public void constancias(HttpServletRequest request)
			throws ServletException, IOException {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		String vig = null;

		try {
			borrarBeansConstancias(request);
			// System.out
			// .println("*********////*********nENTRn POR CONSTANCIAS!**********//**********");
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
			z = 0;
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = sede;
			list.add(o);
			if (request.getAttribute("filtroSedeF") == null)
				request.setAttribute("filtroSedeF", util.getFiltro(
						rb.getString("filtroSedeInstitucion"), list));
			z = 0;
			list = new ArrayList();
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = login.getInstId();
			// System.out.println("inst_" + login.getInstId());
			list.add(o);
			z = 0;
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = sede;
			System.out.println("sede_" + sede);
			list.add(o);
			z = 0;
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = jornada;
			// System.out.println("jorn_" + jornada);
			list.add(o);
			if (request.getAttribute("filtroJornadaF") == null)
				request.setAttribute("filtroJornadaF", util.getFiltro(
						rb.getString("filtroSedeJornadaInstitucion"), list));
			z = 0;
			list = new ArrayList();
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = login.getInstId();
			list.add(o);
			if (request.getAttribute("filtroMetodologiaF") == null)
				request.setAttribute("filtroMetodologiaF", util.getFiltro(
						rb.getString("listaMetodologiaInstitucion"), list));
			z = 0;
			list = new ArrayList();
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = login.getInstId();
			list.add(o);
			z = 0;
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = sede;
			list.add(o);
			z = 0;
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = jornada;
			list.add(o);
			Collection cc = util.getFiltro(
					rb.getString("filtroSedeJornadaGradoInstitucion2"), list);
			request.setAttribute("filtroGradoF2", cc);
			if (request.getAttribute("filtroGrupoF") == null)
				request.setAttribute("filtroGrupoF", util.getFiltro(
						rb.getString("filtroSedeJornadaGradoGrupoInstitucion"),
						list));

			/*
			 * con=cursor.getConnection();
			 * pst=con.prepareStatement(rb.getString("AnioActual"));
			 * rs=pst.executeQuery(); if(rs.next()) vig=rs.getString(1);
			 * System.out.println("***valor de vig: ***"+vig); rs.close();
			 * pst.close();
			 */
			// con.close();
			vig = String.valueOf(reportesDAO.getVigenciaInst(Long
					.parseLong(login.getInstId())));

			if (vig != null) {
				if (request.getAttribute("AnioActual") == null)
					request.setAttribute("AnioActual", new Integer(vig));
			}

			String accion = request.getParameter("accion");
			if (accion != null && accion.equals("1")) {
				// System.out
				// .println("****ENTRn A HACER FILTRO DE ESTUDIANTES***");
				if ((!filtrocon.getSede().equals("-9"))
						&& (!filtrocon.getJornada().equals("-9"))
						&& (!filtrocon.getMetodologia().equals("-9"))
						&& (!filtrocon.getGrado().equals("-9"))
						&& (!filtrocon.getGrupo().equals("-9"))) {
					con = cursor.getConnection();
					pst = con.prepareStatement(rb
							.getString("lista_estudiantes"));
					posicion = 1;
					pst.clearParameters();
					pst.setLong(posicion++, Long.parseLong(login.getInstId()));
					pst.setLong(posicion++, Long.parseLong(filtrocon.getSede()));
					pst.setLong(posicion++,
							Long.parseLong(filtrocon.getJornada()));
					pst.setLong(posicion++,
							Long.parseLong(filtrocon.getGrado()));
					pst.setLong(posicion++,
							Long.parseLong(filtrocon.getMetodologia()));
					pst.setLong(posicion++,
							Long.parseLong(filtrocon.getGrupo()));

					rs = pst.executeQuery();
					list = new ArrayList();
					while (rs.next()) {
						o = new Object[4];
						o[0] = rs.getString(1);
						o[1] = rs.getString(2);
						o[2] = rs.getString(3);
						o[3] = rs.getString(4);
						list.add(o);
					}
					rs.close();
					pst.close();
					// con.close();
					request.setAttribute("estudiantes", list);
					// System.out.println("ejecutn lista_estudiantes");
				}
			}
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

	public void logrosPendientes(HttpServletRequest request)
			throws ServletException, IOException {

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		String accion = null;
		try {
			borrarBeansLogrosPendientes(request);
			// System.out
			// .println("*********////*********nENTRn POR FILTRO LOGROS PENDIENTES!**********//**********");
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
			z = 0;
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = sede;
			list.add(o);
			if (request.getAttribute("filtroSedeF") == null)
				request.setAttribute("filtroSedeF", util.getFiltro(
						rb.getString("filtroSedeInstitucion"), list));
			z = 0;
			list = new ArrayList();
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = login.getInstId();
			// System.out.println("inst_" + login.getInstId());
			list.add(o);
			z = 0;
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = sede;
			System.out.println("sede_" + sede);
			list.add(o);
			z = 0;
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = jornada;
			// System.out.println("jorn_" + jornada);
			list.add(o);
			if (request.getAttribute("filtroJornadaF") == null)
				request.setAttribute("filtroJornadaF", util.getFiltro(
						rb.getString("filtroSedeJornadaInstitucion"), list));
			z = 0;
			list = new ArrayList();
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = login.getInstId();
			list.add(o);
			if (request.getAttribute("filtroMetodologiaF") == null)
				request.setAttribute("filtroMetodologiaF", util.getFiltro(
						rb.getString("listaMetodologiaInstitucion"), list));
			z = 0;
			list = new ArrayList();
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = login.getInstId();
			list.add(o);
			z = 0;
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = sede;
			list.add(o);
			z = 0;
			o = new Object[2];
			o[z++] = new Integer(java.sql.Types.INTEGER);
			o[z++] = jornada;
			list.add(o);
			Collection cc = util.getFiltro(
					rb.getString("filtroSedeJornadaGradoInstitucionLogro"),
					list);
			request.setAttribute("filtroGradoF2", cc);
			if (request.getAttribute("filtroGrupoF") == null)
				request.setAttribute("filtroGrupoF", util.getFiltro(
						rb.getString("filtroSedeJornadaGradoGrupoInstitucion"),
						list));
			if (request.getAttribute("filtroPeriodoF") == null)
				request.setAttribute("filtroPeriodoF", util.getFiltro(rb
						.getString("listaPeriodosInstitucion")));
			accion = request.getParameter("accion");
			if (accion != null && accion.equals("1")) {
				if ((!filtroLogros.getSede().equals("-9"))
						&& (!filtroLogros.getJornada().equals("-9"))
						&& (!filtroLogros.getGrado().equals("-9"))
						&& (!filtroLogros.getGrupo().equals("-9"))) {
					con = cursor.getConnection();
					pst = con.prepareStatement(rb
							.getString("lista_estudiantes_logros"));
					posicion = 1;
					pst.clearParameters();
					pst.setLong(posicion++, Long.parseLong(login.getInstId()));
					pst.setLong(posicion++,
							Long.parseLong(filtroLogros.getSede()));
					pst.setLong(posicion++,
							Long.parseLong(filtroLogros.getJornada()));
					pst.setLong(posicion++,
							Long.parseLong(filtroLogros.getGrado()));
					pst.setLong(posicion++,
							Long.parseLong(filtroLogros.getMetodologia()));
					pst.setLong(posicion++,
							Long.parseLong(filtroLogros.getGrupo()));
					rs = pst.executeQuery();
					list = new ArrayList();
					while (rs.next()) {
						o = new Object[4];
						o[0] = rs.getString(1);
						o[1] = rs.getString(2);
						o[2] = rs.getString(3);
						o[3] = rs.getString(4);
						list.add(o);
					}
					// System.out.println("size: " + list.size());
					rs.close();
					pst.close();
					request.setAttribute("logros_estudiantes", list);
					// System.out.println("ejecutn logros_estudiantes");
				}
			}
		} catch (Throwable th) {
			th.printStackTrace();
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
		filtro = (FiltroBeanReports) session.getAttribute("filtrob");
		filtroc = (FiltroBeanCertificados) session.getAttribute("filtrobc");
		filtrocon = (FiltroBeanConstancias) session.getAttribute("filtrobcc");
		filtroLogros = (FiltroBeanFormulario) session.getAttribute("filtroLog");
		String sentencia = "";
		return true;
	}

	/**
	 * Elimina del contexto de la sesion los beans de informacion del usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void borrarBeansBoletines(HttpServletRequest request)
			throws ServletException, IOException {
		session.removeAttribute("filtrob");
		// System.out.println("nnnnnBorrn de sesinn el filtro de Boletines!!!!");
	}

	/**
	 * Elimina del contexto de la sesion los beans de informacion del usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void borrarBeansCertificados(HttpServletRequest request)
			throws ServletException, IOException {
		session.removeAttribute("filtroc");
		// System.out
		// .println("nnnnnBorrn de sesinn el filtro de Certificados!!!!");
	}

	/**
	 * Elimina del contexto de la sesion los beans de informacion del usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void borrarBeansConstancias(HttpServletRequest request)
			throws ServletException, IOException {
		session.removeAttribute("filtrobcc");
		// System.out.println("nnnnnBorrn de sesinn el filtro de Constancias!!!!");
	}

	/**
	 * Elimina del contexto de la sesion los beans de informacion del usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void borrarBeansLogrosPendientes(HttpServletRequest request)
			throws ServletException, IOException {
		request.getSession().removeAttribute("filtroLog");
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