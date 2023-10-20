package siges.rotacion;

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

import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.dao.Util;
import siges.exceptions.InternalErrorException;
import siges.login.beans.Login;
import siges.rotacion.beans.FiltroDocXGrupoVO;
import siges.rotacion.beans.FiltroEspXGradoVO;
import siges.rotacion.beans.FiltroRotacion;
import siges.rotacion.beans.ParamsVO;
import siges.rotacion.dao.RotacionDAO;

/**
 * @author Administrador
 * 
 * Preferencias - Java - Estilo de cndigo - Plantillas de cndigo
 */
public class ControllerFiltro extends HttpServlet {
	public static final long serialVersionUID = 1;
	private String mensaje;// mensaje en caso de error
	private Cursor cursor=new Cursor();// objeto que maneja las sentencias sql

	private Util util=new Util(cursor);//

	private RotacionDAO rotacionDAO = new RotacionDAO(cursor);

	private ResourceBundle rbRot= ResourceBundle.getBundle("siges.rotacion.bundle.rotacion");

	private Integer enterolargo = new Integer(java.sql.Types.BIGINT);

	/**
	 * Funcinn: Procesa la peticion HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Collection col1;
			String consulta;
			String tiprec;
			Object o[] = null;
			HttpSession session = request.getSession();
			Collection list = new ArrayList();
			String sig, sig11, sig21, sig31, sig41, sig41f, sig41l, sig51, sig61, sig71, sig101, sig121;
			int tipo;
			sig = "/rotacion/ControllerGuardar.do";
			sig11 = "/rotacion/ListaEstructura.jsp";
			sig21 = "/rotacion/ListaFijarEspacios.jsp";
			sig31 = "/rotacion/ListaFijarDocentes.jsp";
			sig41 = "/rotacion/ListaInhabilitarEspacios.jsp";
			sig51 = "/rotacion/ListaInhabilitarDocentes.jsp";
			sig61 = "/rotacion/ListaFijarAsignatura.jsp";
			sig71 = "/rotacion/ListaFijarEspacioDocente.jsp";
			sig101 = "/rotacion/Horario.jsp";
			sig121 = "/rotacion/ListaInhabilitarHora.jsp";
			String FichaDocenteGrupo = "/rotacion/ListaDocenteGrupo.jsp";
			String FichaEspacioGrado = "/rotacion/ListaEspacioGrado.jsp";

			String p_error = this.getServletContext().getInitParameter("er");
			mensaje = null;
			
			if (request.getParameter("tipo") == null || ((String) request.getParameter("tipo")).equals("")) {
				borrarBeans(request);
				tipo = 10;
			} else {
				tipo = Integer.parseInt((String) request.getParameter("tipo"));
			}
			switch (tipo) {
				case 11:case 21:case 31:case 51:case 61:case 71:case 81:case 121: case ParamsVO.FICHA_DOCENTE_GRUPO_NUEVO:case ParamsVO.FICHA_ESPACIO_GRADO:case 10:case 12:case 13:case 16:case 20:case 22:case 23:case 26:case 29:case 30:case 32:case 33:case 36:case 39:case 40:case 41:case 47:case 42:case 43:case 46:case 49:case 50:case 52:case 53:case 56:case 59:case 60:case 62:case 63:case 66:case 69:case 70:case 72:case 73:case 76:case 79:case 80:case 90:case 100:case 101:case 102:case 110:case 111:case 112:case 120:case 122:case 123:case 126:case 129:					
					break;
				default:
					tipo=10;
			}
			int CMD = getCmd(request, session, Params.CMD_NUEVO);
			// System.out.println("valores critios en filtro=="+tipo+"//"+CMD);
			if (!asignarBeans(request)) {
				this.setMensaje("Error capturando datos de sesinn para el usuario");
				request.setAttribute("mensaje", mensaje);
				return p_error;
			}
			Login login = (Login) session.getAttribute("login");
			//Rotacion r = (Rotacion) session.getAttribute("rotacion");
			FiltroDocXGrupoVO filtro = (FiltroDocXGrupoVO) session.getAttribute("filtroDocXGrupoVO");
			FiltroEspXGradoVO filtroEspGra = (FiltroEspXGradoVO) session.getAttribute("filtroEspXGradoVO");
			// System.out.println("FILTRO de rot=" + tipo);
			session.setAttribute("isLockedRotacion", rotacionDAO.isLocked(login));
			initFiltro(request, login);
			// REVISION DE FILTRO
			int vig = (int) rotacionDAO.getVigenciaInst(Long.parseLong(login.getInstId()));
			FiltroRotacion filtroRotacion = (FiltroRotacion) request.getSession().getAttribute("filtroRotacion");
			if (filtroRotacion == null) {
				filtroRotacion = new FiltroRotacion();
			}
			if (filtroRotacion.getFilAnhoVigencia() == 0) {filtroRotacion.setFilAnhoVigencia(vig);}
			if (filtroRotacion.getFilMetodologia() == 0) {filtroRotacion.setFilMetodologia(rotacionDAO.getMetodologia(Long.parseLong(login.getInstId())));}
			request.getSession().setAttribute("filtroRotacion", filtroRotacion);
			// FIN DE REVISION DE FILTRO
			switch (tipo) {
			case ParamsVO.FICHA_DOCENTE_GRUPO_NUEVO:
				buscarDocenteGrupo(request, filtro);
				initFiltroDocenteGrupo(request, login, filtro);
				sig = FichaDocenteGrupo;
				break;
			case ParamsVO.FICHA_ESPACIO_GRADO:
				buscarEspacioGrado(request, filtroEspGra);
				initFiltroEspacioGrado(request, login, filtroEspGra);
				sig = FichaEspacioGrado;
				break;
			case 10:
			case 11:
			case 13:
			case 16:
				cargarEstructura(request, filtroRotacion, login);
				sig = sig11;
				break;
			case 12:
				cargarEstructura(request, filtroRotacion, login);
				sig = sig11;
				break;
			case 20:
			case 21:
			case 23:
			case 26:
			case 29:// DEFECTO ESPACIO
				cargarFijarEspacio(request, filtroRotacion, login);
				sig = sig21;
				break;
			case 22:
				cargarFijarEspacio(request, filtroRotacion, login);
				sig = sig21;
				break;
			case 30:
			case 31:
			case 33:
			case 36:
			case 39:
				// defecto docente
				consulta = rbRot.getString("Lista.FijarDocente");

				list = new ArrayList();
				o = new Object[2];
				o[0] = enterolargo;
				o[1] = login.getInstId();
				list.add(o);

				if (!login.getSedeId().equals("") && !login.getJornadaId().equals("")) {
					o = new Object[2];
					o[0] = enterolargo;
					o[1] = login.getSedeId();
					list.add(o);
					o = new Object[2];
					o[0] = enterolargo;
					o[1] = login.getJornadaId();
					list.add(o);

					consulta += " and g_jersede=? and g_jerjorn=?";
				}
				// System.out.println("lisdoc: "+consulta);
				col1 = util.getFiltro(consulta, list);
				request.setAttribute("docente", col1);
				request.removeAttribute("guia");
				request.removeAttribute("id");

				sig = sig31;
				break;
			case 32:
				// editar docente
				consulta = rbRot.getString("Lista.FijarDocente");

				list = new ArrayList();
				o = new Object[2];
				o[0] = enterolargo;
				o[1] = login.getInstId();
				list.add(o);

				if (!login.getSedeId().equals("") && !login.getJornadaId().equals("")) {
					o = new Object[2];
					o[0] = enterolargo;
					o[1] = login.getSedeId();
					list.add(o);
					o = new Object[2];
					o[0] = enterolargo;
					o[1] = login.getJornadaId();
					list.add(o);

					consulta += " and g_jersede=? and g_jerjorn=?";
				}

				col1 = util.getFiltro(consulta, list);
				request.setAttribute("docente", col1);
				tiprec = request.getParameter("id");
				request.setAttribute("guia", tiprec);

				sig = sig31;
				break;
			case 40:
			case 41:
				// filtro esp fis x jor

				cargarInhEspacios(request, util, login);
				// nuevo
				consultarEspFisJor(request, filtroRotacion, login);
				// borrarBeans(request);
				sig = sig41;
				break;
			case 43:
			case 46:
			case 49:
				// defecto esp fis x jor

				request.removeAttribute("guia");
				request.removeAttribute("id");
				consultarEspFisJor(request, filtroRotacion, login);
				sig = sig41;
				break;
			case 42:
				// editar esp fis x jor

				tiprec = request.getParameter("id");
				request.setAttribute("guia", tiprec);
				consultarEspFisJor(request, filtroRotacion, login);
				sig = sig41;
				break;
			case 47:
				// resultado esp fis x jor

				consultarEspFisJor(request, filtroRotacion, login);
				sig = sig41;
				break;

			case 50:
			case 51:
			case 53:
			case 56:
			case 59:
				cargarInhDoc(request, filtroRotacion, login);
				request.removeAttribute("guia");
				request.removeAttribute("id");
				sig = sig51;
				break;
			case 52:
				cargarInhDoc(request, filtroRotacion, login);
				tiprec = request.getParameter("id");
				request.setAttribute("guia", tiprec);
				sig = sig51;
				break;
			case 60:
			case 61:
			case 63:
			case 66:
			case 69:
				// defecto fijar asignaturas
				cargarFijarAsignatura(request, filtroRotacion, login);
				sig = sig61;
				break;
			case 62:
				// editar fijar asignaturas
				cargarFijarAsignatura(request, filtroRotacion, login);
				sig = sig61;
				break;
			case 70:
			case 71:
			case 73:
			case 76:
			case 79:
				// defecto fijar espacio por docente
				cargarFijarEspacioDocente(request, filtroRotacion, login);
				sig = sig71;
				break;
			case 72:
				// editar fijar espacio por docente
				cargarFijarEspacioDocente(request, filtroRotacion, login);
				sig = sig71;
				break;
			case 120:
			case 121:
			case 123:
			case 126:
			case 129:
				cargarInhHoras(request, filtroRotacion, login);
				request.removeAttribute("guia");
				request.removeAttribute("id");
				sig = sig121;
				break;
			case 122:
				cargarInhHoras(request, filtroRotacion, login);
				tiprec = request.getParameter("id");
				request.setAttribute("guia", tiprec);
				sig = sig121;
				break;
			}

			return sig;
		} catch (InternalErrorException e) {
			e.printStackTrace();
			System.out.println("Error " + this + ": " + e.toString());
			throw new ServletException(e);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error " + this + ": " + e.toString());
			throw new ServletException(e);
		} finally {
			if (cursor != null)
				cursor.cerrar();
			if (util != null)
				util.cerrar();
		}
	}

	private void initFiltro(HttpServletRequest request, Login login) throws NumberFormatException, Exception {
		int vig = (int) rotacionDAO.getVigenciaInst(Long.parseLong(login.getInstId()));
		//int vig = (int) rotacionDAO.getVigenciaNumerico();
		List l = new ArrayList();
		l.add(String.valueOf(vig-1));
		l.add(String.valueOf(vig));
		l.add(String.valueOf(vig + 1));
		request.setAttribute("listaVigencia", l);
		request.setAttribute("listaMetodologia", rotacionDAO.getAllMetodologia(Long.parseLong(login.getInstId())));
	}

	private void cargarEstructura(HttpServletRequest request, FiltroRotacion filtro, Login login) throws InternalErrorException {

		int jornada = -99;
		int sede = -99;
		if (!login.getSedeId().equals("")) {
			sede = Integer.parseInt(login.getSedeId());
		}
		if (!login.getJornadaId().equals("")) {
			jornada = Integer.parseInt(login.getJornadaId());
		}
		request.setAttribute("estructura", rotacionDAO.getAllEstructura(Long.parseLong(login.getInstId()), sede, jornada, filtro));
		request.removeAttribute("guia");
		request.removeAttribute("id");
	}

	private void cargarFijarEspacio(HttpServletRequest request, FiltroRotacion filtro, Login login) throws InternalErrorException {
		int jornada = -99;
		int sede = -99;
		if (!login.getSedeId().equals("")) {
			sede = Integer.parseInt(login.getSedeId());
		}
		if (!login.getJornadaId().equals("")) {
			jornada = Integer.parseInt(login.getJornadaId());
		}
		request.setAttribute("espacio", rotacionDAO.getAllFijarEspacio(Long.parseLong(login.getInstId()), sede, jornada, filtro));
		request.removeAttribute("guia");
		request.removeAttribute("id");

	}

	private void cargarFijarEspacioDocente(HttpServletRequest request, FiltroRotacion filtro, Login login) throws InternalErrorException {
		int jornada = -99;
		int sede = -99;
		if (!login.getSedeId().equals("")) {
			sede = Integer.parseInt(login.getSedeId());
		}
		if (!login.getJornadaId().equals("")) {
			jornada = Integer.parseInt(login.getJornadaId());
		}
		request.setAttribute("espaciodocente", rotacionDAO.getAllFijarEspacioDocente(Long.parseLong(login.getInstId()), sede, jornada, filtro.getFilAnhoVigencia()));
		request.removeAttribute("guia");
		request.removeAttribute("id");
	}

	private void cargarFijarAsignatura(HttpServletRequest request, FiltroRotacion filtro, Login login) throws InternalErrorException {
		int jornada = -99;
		int sede = -99;
		if (!login.getSedeId().equals("")) {
			sede = Integer.parseInt(login.getSedeId());
		}
		if (!login.getJornadaId().equals("")) {
			jornada = Integer.parseInt(login.getJornadaId());
		}
		request.setAttribute("asignatura", rotacionDAO.getAllFijarAsignatura(Long.parseLong(login.getInstId()), Integer.parseInt(login.getMetodologiaId()), sede, jornada, filtro.getFilAnhoVigencia()));
		request.removeAttribute("guia");
		request.removeAttribute("id");
	}

	public void cargarInhHoras(HttpServletRequest request, FiltroRotacion filtro, Login login) throws InternalErrorException {
		int jornada = -99;
		int sede = -99;
		if (!login.getSedeId().equals("")) {
			sede = Integer.parseInt(login.getSedeId());
		}
		if (!login.getJornadaId().equals("")) {
			jornada = Integer.parseInt(login.getJornadaId());
		}
		request.setAttribute("inhHora", rotacionDAO.getAllInhabilitarHora(Long.parseLong(login.getInstId()), sede, jornada, filtro));
	}

	public void cargarInhDoc(HttpServletRequest request, FiltroRotacion filtro, Login login) throws InternalErrorException {
		int jornada = -99;
		int sede = -99;
		if (!login.getSedeId().equals("")) {
			sede = Integer.parseInt(login.getSedeId());
		}
		if (!login.getJornadaId().equals("")) {
			jornada = Integer.parseInt(login.getJornadaId());
		}
		request.setAttribute("inhdocente", rotacionDAO.getAllInhabilitarDocente(Long.parseLong(login.getInstId()), sede, jornada, filtro));
	}

	public void consultarEspFisJor(HttpServletRequest request, FiltroRotacion filtro, Login login) throws Exception {
		int jornada = -99;
		int sede = -99;
		if (!login.getSedeId().equals("")) {
			sede = Integer.parseInt(login.getSedeId());
		}
		if (!login.getJornadaId().equals("")) {
			jornada = Integer.parseInt(login.getJornadaId());
		}
		request.setAttribute("espfisjor", rotacionDAO.getAllInhabilitarEspacio(Long.parseLong(login.getInstId()), sede, jornada, filtro));
	}

	public void cargarInhEspacios(HttpServletRequest request, Util util, Login login) throws Exception {
		Collection list = new ArrayList();
		Object o[] = null;
		o = new Object[2];
		o[0] = enterolargo;
		o[1] = login.getInstId();
		list.add(o);
		request.setAttribute("filtroSedeF", util.getFiltro(rbRot.getString("Lista.filtroSedeInstitucion"), list));
		request.setAttribute("filtroJornadaF", util.getFiltro(rbRot.getString("Lista.filtroSedeJornadaInstitucion"), list));
		request.setAttribute("filtroMetodologiaF", util.getFiltro(rbRot.getString("Lista.MetodologiaInstitucion"), list));
		request.setAttribute("filtroEspFis", util.getFiltro(rbRot.getString("Lista.EspFis"), list));
	}

	/**
	 * Funcinn: Inserta los valores por defecto para el bean de información
	 * bnsica con respecto a la informacion de la institucinn educativa
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeans(HttpServletRequest request) throws ServletException, IOException {
		if (request.getSession().getAttribute("login") == null) {
			return false;
		}
		return true;
	}

	/**
	 * Funcinn: Elimina del contexto de la sesion los beans de informacion del
	 * usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void borrarBeans(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("rotacion");
	}

	/**
	 * Funcinn: Recibe la peticion por el metodo get de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);// redirecciona la peticion a doPost
	}

	/**
	 * Funcinn: Recibe la peticion por el metodo Post de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String s = process(request, response);
		if (s != null && !s.equals(""))
			ir(1, s, request, response);
	}

	/**
	 * Funcinn: Redirige el control a otro servlet
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
	public void ir(int a, String s, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (cursor != null)
			cursor.cerrar();
		RequestDispatcher rd = getServletContext().getRequestDispatcher(s);
		if (a == 1)
			rd.include(request, response);
		else
			rd.forward(request, response);
	}

	/**
	 * Funcinn: Concatena el parametro en una variable que contiene los mensajes
	 * que se van a enviar a la vista
	 * 
	 * @param String
	 *            s
	 */
	public void setMensaje(String s) {
		mensaje = "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n";
		mensaje += "  - " + s + "\n";
	}

	private void initFiltroDocenteGrupo(HttpServletRequest request, Login login, FiltroDocXGrupoVO filtro) {
		long inst = Long.parseLong(login.getInstId());
		request.setAttribute("lSedeVO", rotacionDAO.getAllSede(inst));
		request.setAttribute("lJornadaVO", rotacionDAO.getAllJornada(inst));
		request.setAttribute("lMetodologiaVO", rotacionDAO.getAllMetodologia(inst));
		if (filtro != null && filtro.getFormaEstado().equals("1")) {
			// System.out.println("Entra a poner docentes");
			request.setAttribute("ajaxDocente", rotacionDAO.getAjaxDocente(filtro.getFilInstitucion(), filtro.getFilSede(), filtro.getFilJornada()));
		}
		request.setAttribute("listaVigencia", rotacionDAO.getAllVigencia(Long.parseLong(login.getInstId())));
	}

	public int getCmd(HttpServletRequest request, HttpSession session, int defecto) {
		int cmd = defecto;
		if (request.getParameter("cmd") != null && !((String) request.getParameter("cmd")).equals("")) {
			if (GenericValidator.isInt(request.getParameter("cmd"))) {
				return Integer.parseInt((String) request.getParameter("cmd"));
			} else {
				return 0;
			}
		}
		return cmd;
	}

	private void buscarDocenteGrupo(HttpServletRequest request, FiltroDocXGrupoVO filtro) {
		try {
			if (filtro != null) {
				// System.out.println("PARAMETOS DE
				// BUSQUEDA=="+filtro.getFilSede()+"--"+filtro.getFilJornada()+"--"+filtro.getFilDocente());
				request.setAttribute("lDocenteGrupo", rotacionDAO.getAllDocenteGrupo(filtro));
				filtro.setFormaEstado("1");
			} else {
				filtro = new FiltroDocXGrupoVO();
			}
			if (filtro.getFilVigencia() == 0) {
				int vig = (int) rotacionDAO.getVigenciaInst(Long.parseLong(filtro.getFilInstitucion()+""));
				filtro.setFilVigencia(vig);
			}
			request.getSession().setAttribute("filtroDocXGrupoVO",filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void buscarEspacioGrado(HttpServletRequest request, FiltroEspXGradoVO filtro) {
		try {
			if (filtro != null) {
				// System.out.println("PARAMETOS DE BUSQUEDA=="+filtro.getFilSede()+"--"+filtro.getFilJornada());
				request.setAttribute("lEspacioGrado", rotacionDAO.getAllEspacioGrado(filtro));
				filtro.setFormaEstado("1");
			}else {
				filtro = new FiltroEspXGradoVO();
			}
			if (filtro.getFilVigencia() == 0) {
				
				int vig = (int) rotacionDAO.getVigenciaInst(filtro.getFilInstitucion());
				filtro.setFilVigencia(vig);
			}
			request.getSession().setAttribute("filtroEspXGradoVO",filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	private void initFiltroEspacioGrado(HttpServletRequest request, Login login, FiltroEspXGradoVO filtro) {
		try {
			long inst = Long.parseLong(login.getInstId());
			request.setAttribute("lSedeVO", rotacionDAO.getAllSede(inst));
			request.setAttribute("lJornadaVO", rotacionDAO.getAllJornada(inst));
			request.setAttribute("lMetodologiaVO", rotacionDAO.getAllMetodologia(inst));
			request.setAttribute("listaVigencia", rotacionDAO.getAllVigencia(Long.parseLong(login.getInstId())));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}