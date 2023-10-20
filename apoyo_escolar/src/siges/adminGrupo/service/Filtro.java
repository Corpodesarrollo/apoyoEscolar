package siges.adminGrupo.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;

import siges.adminGrupo.dao.AdminGrupoDAO;
import siges.adminGrupo.vo.FiltroGrupoVO;
import siges.adminGrupo.vo.GrupoVO;
import siges.adminGrupo.vo.ParamsVO;
import siges.adminParamsInst.vo.EscalaConceptualVO;
import siges.adminParamsInst.vo.FiltroEscalaVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.login.beans.Login;

import siges.util.Logger;

/**
 * Procesa las peticiones para el filtro de bnsqueda 26/01/2010
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class Filtro extends Service {
	/**
	 * Ruta de la pagina de filtro de actividades con recursos
	 */

	public String LISTA_GRUPOS;

	/**
	 * Objeto de acceso a datos
	 */
	private AdminGrupoDAO adminGruposDAO = new AdminGrupoDAO(new Cursor());

	/*
	 * Procesa la peticinn
	 * 
	 * @param request peticinn
	 * 
	 * @param response respuesta
	 * 
	 * @return Ruta de direccionamiento
	 * 
	 * @throws ServletException
	 * 
	 * @throws IOException (non-Javadoc)
	 * 
	 * @see
	 * siges.common.service.Service#process(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String FICHA = null;
		HttpSession session = request.getSession();
		String dispatcher[] = new String[2];
		LISTA_GRUPOS = config.getInitParameter("FICHA_FILTRO");

		int CMD = getCmd(request, session);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		Login usuVO = (Login) session.getAttribute("login");
		// System.out.println("FILTRO ADMIN GRUPOS CMD " + CMD + " TIPO " +
		// TIPO);

		FiltroGrupoVO filtroGrupos = (FiltroGrupoVO) session
				.getAttribute("filtroGrupos");
		switch (TIPO) {
		// GRUPOS 16-04-10 WG
		case ParamsVO.FICHA_GRUPOS:

			FICHA = LISTA_GRUPOS;
			filtroGrupos(request, session, usuVO, filtroGrupos);
			switch (CMD) {
			case ParamsVO.CMD_DEFAULT:
			case ParamsVO.CMD_BUSCAR:
				buscarGrupos(request, session, usuVO, filtroGrupos);
				break;
			case ParamsVO.CMD_EDITAR:
				editarGrupo(request, session, usuVO);
				// buscarGrupos(request, session, usuVO,filtroGrupos);
				break;

			case ParamsVO.CMD_ELIMINAR:
				eliminarGrupo(request, session, usuVO);
				// buscarGrupos(request, session, usuVO,filtroGrupos);
				break;

			default:
				break;
			}
			buscarGrupos(request, session, usuVO, filtroGrupos);

			break;

		}
		// System.out.println("FILTRO PLANE: FICHA: " + FICHA);
		dispatcher[0] = String.valueOf(Params.INCLUDE);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	// GRUPOS

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param filtroEscalaVO
	 * @throws ServletException
	 */
	private void filtroGrupos(HttpServletRequest request, HttpSession session,
			Login usuVO, FiltroGrupoVO filtroGrupos) throws ServletException {
		try {
			// System.out.println("ADMIN_GRUPOS: FILTRO");
			if (filtroGrupos == null) {
				// System.out.println("filtro es null");
				filtroGrupos = new FiltroGrupoVO();
				filtroGrupos.setFilCodInst(Long.valueOf(usuVO.getInstId())
						.longValue());
				session.removeAttribute("filtroGrupos");
				session.setAttribute("filtroGrupos", filtroGrupos);
			} else {
				filtroGrupos.setFilCodInst(Long.valueOf(usuVO.getInstId())
						.longValue());
				session.removeAttribute("filtroGrupos");
				session.setAttribute("filtroGrupos", filtroGrupos);
				// session.removeAttribute("dimensionVO");
			}
			request.setAttribute("listaSedes",
					adminGruposDAO.getSedes(filtroGrupos.getFilCodInst()));
			request.setAttribute("listaMetodologias",
					adminGruposDAO.getMetodologia(filtroGrupos.getFilCodInst()));
			if (filtroGrupos.getFilCodSede() > 0) {
				request.setAttribute("listaJornadas", adminGruposDAO
						.getJornadas(filtroGrupos.getFilCodInst(),
								filtroGrupos.getFilCodSede()));
				if (filtroGrupos.getFilCodMetodo() > 0) {
					request.setAttribute(
							"listaGrados",
							adminGruposDAO.getGrados(
									filtroGrupos.getFilCodInst(),
									filtroGrupos.getFilCodMetodo()));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();

			request.setAttribute(ParamsVO.SMS, "" + e.getMessage());

		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param filtroEscalaVO
	 * @throws ServletException
	 */
	private void filtroGruposEditar(HttpServletRequest request,
			HttpSession session, Login usuVO, GrupoVO grupoVO)
			throws ServletException {
		try {
			// System.out.println("ADMIN_GRUPOS: EDITAR");
			if (grupoVO != null) {
				request.setAttribute("listaSedesEditar",
						adminGruposDAO.getSedes(grupoVO.getCodInst()));
				request.setAttribute("listaMetodologiasEditar",
						adminGruposDAO.getMetodologia(grupoVO.getCodInst()));
				if (grupoVO.getCodSede() > 0) {
					request.setAttribute("listaJornadasEditar", adminGruposDAO
							.getJornadas(grupoVO.getCodInst(),
									grupoVO.getCodSede()));
					if (grupoVO.getCodMetodo() > 0) {
						request.setAttribute("listaGradosEditar",
								adminGruposDAO.getGrados(grupoVO.getCodInst(),
										grupoVO.getCodMetodo()));
					}
				}
				request.setAttribute("listaTiposEsp",
						adminGruposDAO.getTiposEspacio());
				request.setAttribute(
						"listaEspacios",
						adminGruposDAO.getEspaciosFisicos(
								grupoVO.getCodTipoEspacio(),
								grupoVO.getCodInst(), grupoVO.getCodSede()));
				request.setAttribute("listaDirectores", adminGruposDAO
						.getDirectores(grupoVO.getCodInst(),
								grupoVO.getCodSede(), grupoVO.getCodJorn()));
			}
		} catch (Exception e) {
			e.printStackTrace();

			request.setAttribute(ParamsVO.SMS, "" + e.getMessage());

		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param filtroEscalaVO
	 * @throws ServletException
	 */
	private void buscarGrupos(HttpServletRequest request, HttpSession session,
			Login usuVO, FiltroGrupoVO filtroGrupos) throws ServletException {
		try {
			// System.out.println("ADMIN_GRUPOS: BUSCAR");
			if (filtroGrupos != null)
				request.setAttribute("listaGrupos",
						adminGruposDAO.listaGrupos(filtroGrupos));

		} catch (Exception e) {
			e.printStackTrace();

			request.setAttribute(ParamsVO.SMS, "" + e.getMessage());

		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @throws ServletException
	 */
	public void editarGrupo(HttpServletRequest request, HttpSession session,
			Login usuVO) throws ServletException {
		try {

			// System.out.println("PLANE_DIMENSION: OBTENER DIMENSION EDITAR");

			String param[] = request.getParameterValues("param");
			String codjerGrado = param[0];
			String codCodigo = param[1];

			// System.out
			// .println("ADMIN_GRUPOS: OBTENER GRUPO EDITAR  PARAMS: codJerGrado"
			// + codjerGrado + "  *codigo : " + codCodigo);

			if (GenericValidator.isLong(codjerGrado)
					&& GenericValidator.isLong(codCodigo)) {

				GrupoVO d2 = new GrupoVO();

				d2.setCodJerGrado(Long.valueOf(codjerGrado).longValue());
				d2.setCodigo(Long.valueOf(codCodigo).longValue());
				session.removeAttribute("grupoVO");
				GrupoVO grupoVO = adminGruposDAO.obtenerGrupo(d2);
				session.setAttribute("grupoVO", grupoVO);
				filtroGruposEditar(request, session, usuVO, grupoVO);

			}
			// else {
			// System.out.println("ADMIN_GRUPOS: ERROR OBTENER GRUPO EDITAR");
			// }

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error: " + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @throws ServletException
	 */
	public void eliminarGrupo(HttpServletRequest request, HttpSession session,
			Login usuVO) throws ServletException {
		try {

			// System.out.println("PLANE_DIMENSION: OBTENER DIMENSION EDITAR");

			String param[] = request.getParameterValues("param");
			String codjerGrado = param[0];
			String codCodigo = param[1];
			long jerGrupo = 0;

			if (GenericValidator.isLong(codjerGrado)
					&& GenericValidator.isLong(codCodigo)) {

				GrupoVO d2 = new GrupoVO();

				d2.setCodJerGrado(Long.valueOf(codjerGrado).longValue());
				d2.setCodigo(Long.valueOf(codCodigo).longValue());

				GrupoVO grupoVO = adminGruposDAO.obtenerGrupo(d2);
				if (grupoVO != null)
					if (grupoVO.getCodJerGrupo() > 0) {
						jerGrupo = grupoVO.getCodJerGrupo();
					} else {
						grupoVO.setCodJerGrupo(adminGruposDAO
								.getJerGrupo(grupoVO));
						jerGrupo = grupoVO.getCodJerGrupo();
					}
				// System.out.println("GRUPOS: GRUPO A ELIMINAR: "+jerGrupo);
				if (!adminGruposDAO.validarGrupo(jerGrupo)) {
					// System.out.println("GRUPOS: SE PUEDEN ELIMINAR");
					if (adminGruposDAO.eliminarGrupo(grupoVO)) {
						session.removeAttribute("grupoVO");
						request.setAttribute(ParamsVO.SMS,
								"La información fue eliminada satisfactoriamente.");
					} else {
						request.setAttribute(ParamsVO.SMS,
								"No fue posible eliminar la información.");
					}
				} else {
					// System.out.println("GRUPOS: NOOO SE PUEDEN ELIMINAR");
					request.setAttribute(ParamsVO.SMS,
							"No es posible eliminar el grupo debido a datos asociados con estudiantes.");
				}

			} else {
				System.out
						.println("ADMIN_GRUPOS: ERROR OBTENER GRUPO ELIMINAR");
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error: " + e.getMessage());
		}
	}
}
