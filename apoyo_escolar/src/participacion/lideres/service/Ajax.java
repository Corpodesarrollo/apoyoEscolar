/**
 * 
 */
package participacion.lideres.service;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import participacion.lideres.dao.LideresDAO;
import participacion.lideres.vo.ParamsVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;

/**
 * 28/04/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class Ajax extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	public String FICHA_LIDERES;
	/**
	 * 
	 */
	private LideresDAO lideresDAO = new LideresDAO(new Cursor());

	/*
	 * @function:
	 * 
	 * @param config
	 * 
	 * @throws ServletException (non-Javadoc)
	 * 
	 * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		FICHA_LIDERES = config.getInitParameter("FICHA_LIDERES");
	}

	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String dispatcher[] = new String[2];
		String FICHA = null;
		HttpSession session = request.getSession();
		int CMD = getCmd(request, session, ParamsVO.CMD_NUEVO);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		switch (TIPO) {
		case ParamsVO.FICHA_LIDERES:
			FICHA = FICHA_LIDERES;
			switch (CMD) {
			case ParamsVO.CMD_AJAX_INSTANCIA:
			case ParamsVO.CMD_AJAX_INSTANCIA0:
				lideresInstancia(request, session, CMD);
				break;
			case ParamsVO.CMD_AJAX_RANGO:
			case ParamsVO.CMD_AJAX_RANGO0:
				lideresRango(request, session, CMD);
				break;
			case ParamsVO.CMD_AJAX_COLEGIO:
			case ParamsVO.CMD_AJAX_COLEGIO0:
				lideresColegio(request, session, CMD);
				break;
			case ParamsVO.CMD_AJAX_SEDE:
			case ParamsVO.CMD_AJAX_SEDE0:
				lideresSede(request, session, CMD);
				break;
			case ParamsVO.CMD_AJAX_SEDE_PRIVADO:
				lideresSedePrivado(request, session, CMD);
				break;
			case ParamsVO.CMD_AJAX_JORNADA:
			case ParamsVO.CMD_AJAX_JORNADA0:
				lideresJornada(request, session, CMD);
				break;
			case ParamsVO.CMD_AJAX_GRADO:
			case ParamsVO.CMD_AJAX_GRADO0:
				lideresGrado(request, session, CMD);
				break;
			case ParamsVO.CMD_AJAX_GRUPO:
			case ParamsVO.CMD_AJAX_GRUPO0:
				lideresGrupo(request, session, CMD);
				break;
			case ParamsVO.CMD_AJAX_ESTUDIANTE:
			case ParamsVO.CMD_AJAX_ESTUDIANTE0:
				lideresEstudiante(request, session, CMD);
				break;
			case ParamsVO.CMD_AJAX_SEDEP:
			case ParamsVO.CMD_AJAX_SEDEP0:
				lideresSedeP(request, session, CMD);
				break;
			case ParamsVO.CMD_AJAX_JORNADAP:
			case ParamsVO.CMD_AJAX_JORNADAP0:
				lideresJornadaP(request, session, CMD);
				break;
			case ParamsVO.CMD_AJAX_PERSONAL:
			case ParamsVO.CMD_AJAX_PERSONAL0:
				lideresPersonal(request, session, CMD);
				break;

			}
			break;
		}
		dispatcher[0] = String.valueOf(Params.INCLUDE);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	public void lideresInstancia(HttpServletRequest request,
			HttpSession session, int cmd) throws ServletException {
		try {
			String params[] = request.getParameterValues("ajax");
			if (params == null || params.length < 1 || params[0] == null)
				return;
			request.setAttribute("listaInstanciaVO",
					lideresDAO.getListaInstancia(Integer.parseInt(params[0])));
			request.setAttribute("listaRolVO",
					lideresDAO.getListaRol(Integer.parseInt(params[0])));
			request.setAttribute("ajaxParam", String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void lideresRango(HttpServletRequest request, HttpSession session,
			int cmd) throws ServletException {
		try {
			String params[] = request.getParameterValues("ajax");
			if (params == null || params.length < 1 || params[0] == null)
				return;
			request.setAttribute("listaRangoVO",
					lideresDAO.getListaRango(Integer.parseInt(params[0])));
			request.setAttribute("listaRolVO",
					lideresDAO.getListaRol(Integer.parseInt(params[0])));
			request.setAttribute("ajaxParam", String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void lideresColegio(HttpServletRequest request, HttpSession session,
			int cmd) throws ServletException {
		try {
			String params[] = request.getParameterValues("ajax");
			if (params == null || params.length < 1 || params[0] == null)
				return;
			request.setAttribute("listaColegioVO",
					lideresDAO.getListaColegio(Integer.parseInt(params[0])));
			request.setAttribute("listaRolVO",
					lideresDAO.getListaRol(Integer.parseInt(params[0])));
			request.setAttribute("ajaxParam", String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void lideresSede(HttpServletRequest request, HttpSession session,
			int cmd) throws ServletException {
		try {
			// System.out.println("entra a lideresSede");
			String params[] = request.getParameterValues("ajax");
			if (params == null || params.length < 1 || params[0] == null)
				return;
			request.setAttribute("listaSedeVO",
					lideresDAO.getListaSede(Long.parseLong(params[0])));
			request.setAttribute("listaMetodologiaVO",
					lideresDAO.getListaMetodologia(Long.parseLong(params[0])));
			// request.setAttribute("listaRolVO",
			// lideresDAO.getListaRol(Integer.parseInt(params[0])));
			request.setAttribute("ajaxParam", String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void lideresSedePrivado(HttpServletRequest request,
			HttpSession session, int cmd) throws ServletException {
		try {
			// System.out.println("entra a lideresSedePrivado");
			String params[] = request.getParameterValues("ajax");
			if (params == null || params.length < 1 || params[0] == null)
				return;
			request.setAttribute("listaSedeVO",
					lideresDAO.getListaSede(Long.parseLong(params[0])));
			request.setAttribute("ajaxParam", String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void lideresSedeP(HttpServletRequest request, HttpSession session,
			int cmd) throws ServletException {
		try {
			// System.out.println("entra a lideresSedeP");
			String params[] = request.getParameterValues("ajax");
			if (params == null || params.length < 1 || params[0] == null)
				return;
			request.setAttribute("listaSedeVO",
					lideresDAO.getListaSede(Long.parseLong(params[0])));
			// request.setAttribute("listaRolVO",
			// lideresDAO.getListaRol(Integer.parseInt(params[0])));
			request.setAttribute("ajaxParam", String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void lideresJornada(HttpServletRequest request, HttpSession session,
			int cmd) throws ServletException {
		try {
			// System.out.println("entra a lideresJornada");
			String params[] = request.getParameterValues("ajax");
			if (params == null || params.length < 1 || params[0] == null)
				return;
			request.setAttribute("listaJornadaVO", lideresDAO.getListaJornada(
					Long.parseLong(params[0]), Integer.parseInt(params[1])));
			// request.setAttribute("listaRolVO",
			// lideresDAO.getListaRol(Integer.parseInt(params[0])));
			request.setAttribute("ajaxParam", String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void lideresJornadaP(HttpServletRequest request,
			HttpSession session, int cmd) throws ServletException {
		try {
			// System.out.println("entra a lideresJornadaP");
			String params[] = request.getParameterValues("ajax");
			if (params == null || params.length < 1 || params[0] == null)
				return;
			request.setAttribute("listaJornadaVO", lideresDAO.getListaJornada(
					Long.parseLong(params[0]), Integer.parseInt(params[1])));
			// request.setAttribute("listaRolVO",
			// lideresDAO.getListaRol(Integer.parseInt(params[0])));
			request.setAttribute("ajaxParam", String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void lideresGrado(HttpServletRequest request, HttpSession session,
			int cmd) throws ServletException {
		try {
			// System.out.println("entra a lideresGrado");
			String params[] = request.getParameterValues("ajax");
			if (params == null || params.length < 1 || params[0] == null)
				return;
			request.setAttribute(
					"listaGradoVO",
					lideresDAO.getListaGrado(Long.parseLong(params[0]),
							Integer.parseInt(params[1]),
							Integer.parseInt(params[2]),
							Integer.parseInt(params[3])));
			// request.setAttribute("listaRolVO",
			// lideresDAO.getListaRol(Integer.parseInt(params[0])));
			request.setAttribute("ajaxParam", String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void lideresGrupo(HttpServletRequest request, HttpSession session,
			int cmd) throws ServletException {
		try {
			// System.out.println("entra a lideresGrupo");
			String params[] = request.getParameterValues("ajax");
			if (params == null || params.length < 1 || params[0] == null)
				return;
			request.setAttribute(
					"listaGrupoVO",
					lideresDAO.getListaGrupo(Long.parseLong(params[0]),
							Integer.parseInt(params[1]),
							Integer.parseInt(params[2]),
							Integer.parseInt(params[3]),
							Integer.parseInt(params[4])));
			// request.setAttribute("listaRolVO",
			// lideresDAO.getListaRol(Integer.parseInt(params[0])));
			request.setAttribute("ajaxParam", String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void lideresEstudiante(HttpServletRequest request,
			HttpSession session, int cmd) throws ServletException {
		try {
			// System.out.println("entra a lideresEstudiante");
			String params[] = request.getParameterValues("ajax");
			if (params == null || params.length < 1 || params[0] == null)
				return;
			request.setAttribute("listaEstudianteVO", lideresDAO
					.getListaEstudiante(Long.parseLong(params[0]),
							Integer.parseInt(params[1]),
							Integer.parseInt(params[2]),
							Integer.parseInt(params[3]),
							Integer.parseInt(params[4]),
							Integer.parseInt(params[5]),
							Integer.parseInt(params[6]),
							Integer.parseInt(params[7])));
			// request.setAttribute("listaRolVO",
			// lideresDAO.getListaRol(Integer.parseInt(params[0])));
			request.setAttribute("ajaxParam", String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void lideresPersonal(HttpServletRequest request,
			HttpSession session, int cmd) throws ServletException {
		try {
			// System.out.println("entra a lideresPersonal");
			String params[] = request.getParameterValues("ajax");
			if (params == null || params.length < 1 || params[0] == null)
				return;
			request.setAttribute("listaPersonalVO", lideresDAO
					.getListaPersonal(Long.parseLong(params[0]),
							Integer.parseInt(params[1]),
							Integer.parseInt(params[2]),
							Integer.parseInt(params[3]),
							Integer.parseInt(params[4])));
			// request.setAttribute("listaRolVO",
			// lideresDAO.getListaRol(Integer.parseInt(params[0])));
			request.setAttribute("ajaxParam", String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

}
