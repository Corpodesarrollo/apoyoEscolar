package siges.adminParamsAcad.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;

import siges.adminParamsInst.dao.AdminParametroInstDAO;
import siges.adminParamsInst.vo.EscalaConceptualVO;
import siges.adminParamsInst.vo.EscalaNumericaVO;
import siges.adminParamsInst.vo.FiltroEscalaNumVO;
import siges.adminParamsInst.vo.FiltroEscalaVO;
import siges.adminParamsInst.vo.InstParVO;
import siges.adminParamsInst.vo.ParamsVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.login.beans.Login;

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
	public String LISTA_ESCALA_CONCEP;
	public String LISTA_ESCALA_NUM;

	/**
	 * Objeto de acceso a datos
	 */
	private AdminParametroInstDAO adminParametroInstDAO = new AdminParametroInstDAO(
			new Cursor());

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
		LISTA_ESCALA_CONCEP = config.getInitParameter("LISTA_ESCALA_CONCEP");
		LISTA_ESCALA_NUM = config.getInitParameter("LISTA_ESCALA_NUM");

		int CMD = getCmd(request, session);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		Login usuVO = (Login) session.getAttribute("login");
		// System.out.println("FILTRO  AAA CMD " + CMD + " TIPO " + TIPO);

		switch (TIPO) {
		case ParamsVO.FICHA_ESCL_CONCEPT:
			FICHA = LISTA_ESCALA_CONCEP;
			FiltroEscalaVO filtroEscalaVO = (FiltroEscalaVO) session
					.getAttribute("filtroEscalaVO");
			switch (CMD) {
			case ParamsVO.CMD_BUSCAR:
				buscarEscalaConcep(request, session, usuVO, filtroEscalaVO);
				break;
			case ParamsVO.CMD_EDITAR:
				editarEscalaConcep(request, session, usuVO);
				break;
			case ParamsVO.CMD_ELIMINAR:
				eliminarEscalaConcep(request, session, usuVO);
				break;
			default:
				break;
			}
			buscarEscalaConcep(request, session, usuVO, filtroEscalaVO);

			break;

		}
		dispatcher[0] = String.valueOf(Params.INCLUDE);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	/**
	 * Metodo que realiza la busqueda de las escelas de conceptos
	 * 
	 * @param request
	 * @throws Exception
	 */
	private void buscarEscalaConcep(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroEscalaVO filtroEscalaVO)
			throws ServletException {
		try {

			if (filtroEscalaVO == null) {
				// System.out.println("filtro es null");
				filtroEscalaVO = new FiltroEscalaVO();
				filtroEscalaVO.setFilInst(Long.valueOf(usuVO.getInstId())
						.longValue());
			} else {
				filtroEscalaVO.setFilInst(Long.valueOf(usuVO.getInstId())
						.longValue());
			}

			cargarFiltro(request, session, usuVO, filtroEscalaVO);
			request.setAttribute("listaAllEscalaConceptual",
					adminParametroInstDAO.getListaEscalaConcep(filtroEscalaVO));

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
	private void cargarFiltro(HttpServletRequest request, HttpSession session,
			Login usuVO, FiltroEscalaVO filtroEscalaVO) throws ServletException {
		try {

			session.setAttribute(
					"listaVigencia",
					""
							+ adminParametroInstDAO.getVigenciaInst(Long
									.parseLong(usuVO.getInstId())));

			EscalaConceptualVO escalaConceptualVO = new EscalaConceptualVO();
			InstParVO instParVO = new InstParVO();
			instParVO.setInsparcodinst(Long.valueOf(usuVO.getInstId())
					.longValue());
			instParVO.setInsparvigencia(adminParametroInstDAO
					.getVigenciaInst(Long.parseLong(usuVO.getInstId())));
			instParVO = adminParametroInstDAO
					.getPeriodoInstActual(instParVO.getInsparvigencia(),
							instParVO.getInsparcodinst());

			escalaConceptualVO.setInsconniveval(instParVO.getInsparniveval());

			filtroEscalaVO.setFilniveval(instParVO.getInsparniveval());
			filtroEscalaVO.setFilVigencia(instParVO.getInsparvigencia());

			session.removeAttribute("filtroEscalaVO");
			session.setAttribute("filtroEscalaVO", filtroEscalaVO);

		} catch (Exception e) {
			e.printStackTrace();

			throw new ServletException("" + e.getMessage());

		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param filtro
	 * @throws ServletException
	 */
	public void editarEscalaConcep(HttpServletRequest request,
			HttpSession session, Login usuVO) throws ServletException {
		try {

			String param[] = request.getParameterValues("param");
			String codInst = param[0];
			String codVig = param[1];
			String codNivelEva = param[2];
			String codCodigo = param[3];
			String codsede = param[4];
			String codjornd = param[5];
			String codmetodo = param[6];
			String codnivel = param[7];
			String codgrado = param[8];

			// System.out.println("codNivelEva " + codNivelEva);

			if (GenericValidator.isLong(codInst)
					&& GenericValidator.isLong(codVig)
					&& GenericValidator.isLong(codNivelEva)
					&& GenericValidator.isLong(codCodigo)) {

				session.removeAttribute("escalaConceptualVO");
				EscalaConceptualVO escalaConceptualVO = adminParametroInstDAO
						.getEscalaConcep(Long.valueOf(codInst).longValue(),
								Long.valueOf(codVig).longValue(),
								Long.valueOf(codNivelEva).longValue(), Long
										.valueOf(codCodigo).longValue(), Long
										.valueOf(codsede).longValue(), Long
										.valueOf(codjornd).longValue(), Long
										.valueOf(codmetodo).longValue(), Long
										.valueOf(codnivel).longValue(), Long
										.valueOf(codgrado).longValue());
				session.setAttribute("escalaConceptualVO", escalaConceptualVO);
				// cargarListasEscalaConcep(request,session,usuVO,escalaConceptualVO);
			}
			// else {
			// System.out.println("error al editar ");
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
	 * @param filtro
	 * @throws ServletException
	 */
	public void eliminarEscalaConcep(HttpServletRequest request,
			HttpSession session, Login usuVO) throws ServletException {
		try {

			String param[] = request.getParameterValues("param");
			String codInst = param[0];
			String codVig = param[1];
			String codNivelEva = param[2];
			String codCodigo = param[3];
			String codsede = param[4];
			// System.out.println("codsede " + codsede);
			String codjornd = param[5];
			String codmetodo = param[6];
			String codnivel = param[7];
			String codgrado = param[8];

			// System.out.println("codNivelEva " + codNivelEva);

			if (GenericValidator.isLong(codInst)
					&& GenericValidator.isLong(codVig)
					&& GenericValidator.isLong(codNivelEva)
					&& GenericValidator.isLong(codCodigo)) {

				EscalaConceptualVO escalaCncepVO = adminParametroInstDAO
						.getEscalaConcep(Long.valueOf(codInst).longValue(),
								Long.valueOf(codVig).longValue(),
								Long.valueOf(codNivelEva).longValue(), Long
										.valueOf(codCodigo).longValue(), Long
										.valueOf(codsede).longValue(), Long
										.valueOf(codjornd).longValue(), Long
										.valueOf(codmetodo).longValue(), Long
										.valueOf(codnivel).longValue(), Long
										.valueOf(codgrado).longValue());

				if (!adminParametroInstDAO
						.isvalidarLiteralEvaluacion(escalaCncepVO)) {

					// Eliminar registro
					session.removeAttribute("escalaConceptualVO");
					/*
					 * adminParametroInstDAO.eliminarEscalaConcep(Long.valueOf(
					 * codInst).longValue(), Long.valueOf(codVig).longValue(),
					 * Long.valueOf(codNivelEva).longValue(),
					 * Long.valueOf(codCodigo).longValue(),
					 * Long.valueOf(codsede).longValue(),
					 * Long.valueOf(codjornd).longValue(),
					 * Long.valueOf(codmetodo).longValue(),
					 * Long.valueOf(codnivel).longValue(),
					 * Long.valueOf(codgrado).longValue());
					 */
					request.setAttribute(ParamsVO.SMS,
							"información eliminada satisfactoriamente.");
				} else {
					request.setAttribute(
							ParamsVO.SMS,
							"Este literal ya fue utilizado en al menos una evaluacinn de un estudiante. No puede modificarlo.");
				}
				// cargarListasEscalaConcep(request,session,usuVO,new
				// EscalaConceptualVO());
			}
			// else {
			// System.out.println("error al editar ");
			// }

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error: " + e.getMessage());
		}
	}

	// ESCALA NUMERICA

	/**
	 * Metodo que coloca en el objeto request la lista de Escalar Numericas
	 * segun el fintro
	 * 
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param filtroEscalaNumVO
	 * @throws ServletException
	 */
	private void buscarEscalaNum(HttpServletRequest request,
			HttpSession session, Login usuVO,
			FiltroEscalaNumVO filtroEscalaNumVO) throws ServletException {
		try {

			if (filtroEscalaNumVO == null) {
				// System.out.println("filtro es null");
				filtroEscalaNumVO = new FiltroEscalaNumVO();
				filtroEscalaNumVO.setFilNumInst(Long.valueOf(usuVO.getInstId())
						.longValue());
				filtroEscalaNumVO.setFilNumVigencia(adminParametroInstDAO
						.getVigenciaInst(Long.parseLong(usuVO.getInstId())));
			} else {
				filtroEscalaNumVO.setFilNumInst(Long.valueOf(usuVO.getInstId())
						.longValue());
			}

			cargarFiltro(request, session, usuVO, filtroEscalaNumVO);
			List listaAllEscalaNum = adminParametroInstDAO
					.getListaEscalaNum(filtroEscalaNumVO);
			/*
			 * for (int i = 0; i < listaAllEscalaNum.size(); i++) {
			 * EscalaNumericaVO dato =
			 * (EscalaNumericaVO)listaAllEscalaNum.get(i);
			 * 
			 * 
			 * 
			 * dato.setInsnumequmen(adminParametroInstDAO.getListaEscalaInst_(
			 * dato.getInsnumvigencia(), dato.getInsnumcodinst(),
			 * dato.getInsnumniveval(), dato.getInsnumcodsede(),
			 * dato.getInsnumcodjorn(), dato.getInsnumcodmetod(),
			 * dato.getInsnumcodnivel(), dato.getInsnumcodgrado()
			 * 
			 * ) );
			 * dato.setInsnumequinst(adminParametroInstDAO.getListaEscalaInst_(
			 * dato.getInsnumvigencia(), dato.getInsnumcodinst(),
			 * dato.getInsnumniveval(), dato.getInsnumcodsede(),
			 * dato.getInsnumcodjorn(), dato.getInsnumcodmetod(),
			 * dato.getInsnumcodnivel(), dato.getInsnumcodgrado()
			 * 
			 * ) );
			 * 
			 * }
			 */
			request.setAttribute("listaAllEscalaNum", listaAllEscalaNum);

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
	private void cargarFiltro(HttpServletRequest request, HttpSession session,
			Login usuVO, FiltroEscalaNumVO filtroEscalaNumVO)
			throws ServletException {
		try {

			session.setAttribute(
					"listaVigencia",
					""
							+ adminParametroInstDAO.getVigenciaInst(Long
									.parseLong(usuVO.getInstId())));

			EscalaConceptualVO escalaConceptualVO = new EscalaConceptualVO();
			InstParVO instParVO = new InstParVO();
			instParVO.setInsparcodinst(Long.valueOf(usuVO.getInstId())
					.longValue());
			instParVO.setInsparvigencia(adminParametroInstDAO
					.getVigenciaInst(Long.parseLong(usuVO.getInstId())));
			instParVO = adminParametroInstDAO
					.getPeriodoInstActual(instParVO.getInsparvigencia(),
							instParVO.getInsparcodinst());

			escalaConceptualVO.setInsconniveval(instParVO.getInsparniveval());
			filtroEscalaNumVO.setFilNumniveval(instParVO.getInsparniveval());
			filtroEscalaNumVO.setFilNumniveval(instParVO.getInsparniveval());
			filtroEscalaNumVO.setFilNumVigencia(instParVO.getInsparvigencia());

			session.removeAttribute("filtroEscalaNumVO");
			session.setAttribute("filtroEscalaNumVO", filtroEscalaNumVO);

		} catch (Exception e) {
			e.printStackTrace();

			throw new ServletException("" + e.getMessage());

		}
	}

}
