package siges.adminParamsInst.service;

//	VERSION		FECHA			AUTOR			DESCRIPCION
//		1.0		17/04/2020		JORGE CAMACHO	Se organiz� el c�digo para una mejor lectura en el debug



import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;

import siges.dao.Cursor;
import siges.util.Logger;
import siges.common.vo.Params;
import siges.login.beans.Login;
import siges.common.service.Service;
import siges.adminParamsInst.vo.ParamsVO;
import siges.adminParamsInst.vo.InstParVO;
import siges.adminParamsInst.vo.FiltroEscalaVO;
import siges.adminParamsInst.vo.EscalaNumericaVO;
import siges.adminParamsInst.vo.FiltroNivelEvalVO;
import siges.adminParamsInst.vo.FiltroEscalaNumVO;
import siges.adminParamsInst.vo.EscalaConceptualVO;
import siges.adminParamsInst.vo.InstNivelEvaluacionVO;
import siges.adminParamsInst.dao.AdminParametroInstDAO;
import siges.adminParamsInst.vo.TipoEvaluacionInstAsigVO;


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
	public String LISTA_INST_NIVEL_EVAL;
	private SimpleDateFormat formaFecha = new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss.SSS");
	
	/**
	 * Objeto de acceso a datos
	 */
	private AdminParametroInstDAO adminParametroInstDAO = new AdminParametroInstDAO(new Cursor());

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
	public String[] process(HttpServletRequest request,	HttpServletResponse response) throws ServletException, IOException {
		
		String FICHA = null;
		String dispatcher[] = new String[2];
		HttpSession session = request.getSession();
		
		LISTA_ESCALA_NUM = config.getInitParameter("LISTA_ESCALA_NUM");
		LISTA_ESCALA_CONCEP = config.getInitParameter("LISTA_ESCALA_CONCEP");
		LISTA_INST_NIVEL_EVAL = config.getInitParameter("LISTA_INST_NIVEL_EVAL");

		int CMD = getCmd(request, session, ParamsVO.FICHA_DEFAULT);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		Login usuVO = (Login) session.getAttribute("login");

		switch (TIPO) {
		case ParamsVO.FICHA_ESCL_CONCEPT:
			FICHA = LISTA_ESCALA_CONCEP;
			FiltroEscalaVO filtroEscalaVO = (FiltroEscalaVO) session.getAttribute("filtroEscalaVO");
			cargarFiltro(request, session, usuVO, filtroEscalaVO);
			
			switch (CMD) {
			case ParamsVO.CMD_NUEVO:
			case ParamsVO.CMD_GUARDAR:
			case ParamsVO.CMD_BUSCAR:
				buscarEscalaConcep(request, session, usuVO, filtroEscalaVO);
				break;
			case ParamsVO.CMD_EDITAR:
				editarEscalaConcep(request, session, usuVO);
				buscarEscalaConcep(request, session, usuVO, filtroEscalaVO);
				break;
			case ParamsVO.CMD_ELIMINAR:
				eliminarEscalaConcep(request, session, usuVO);
				buscarEscalaConcep(request, session, usuVO, filtroEscalaVO);
				break;
			default:
				break;
			}

			break;
			
		case ParamsVO.FICHA_INST_NIVEL_EVAL:
			FICHA = LISTA_INST_NIVEL_EVAL;
			FiltroNivelEvalVO filtroNivelEvalVO = (FiltroNivelEvalVO) session.getAttribute("filtroNivelEvalVO");
			cargarFiltro(request, session, usuVO, filtroNivelEvalVO);
			
			switch (CMD) {
			case ParamsVO.CMD_NUEVO:
			case ParamsVO.CMD_GUARDAR:
			case ParamsVO.CMD_BUSCAR:
			case ParamsVO.FICHA_DEFAULT:
				buscarNivelEval(request, session, usuVO, filtroNivelEvalVO);
				break;
			case ParamsVO.CMD_EDITAR:
				editarInstNivelEval(request, session, usuVO);
				buscarNivelEval(request, session, usuVO, filtroNivelEvalVO);
				buscarAsignaturaNivelEval(request, session, filtroNivelEvalVO);
				break;
			case ParamsVO.CMD_ELIMINAR:
				eliminarInstNivelEval(request, session, usuVO);
				buscarNivelEval(request, session, usuVO, filtroNivelEvalVO);
				break;
			default:
				break;
			}

			break;
			
		case ParamsVO.FICHA_ESCL_NUM:
			FICHA = LISTA_ESCALA_NUM;
			FiltroEscalaNumVO filtroEscalaNumVO2 = (FiltroEscalaNumVO) session.getAttribute("filtroEscalaNumVO");
			cargarFiltro(request, session, usuVO, filtroEscalaNumVO2);
			
			switch (CMD) {
			case ParamsVO.CMD_NUEVO:
			case ParamsVO.CMD_GUARDAR:
			case ParamsVO.CMD_BUSCAR:
				buscarEscalaNum(request, session, usuVO, filtroEscalaNumVO2);
				break;
			case ParamsVO.CMD_EDITAR:
				editarEscalaNum(request, session, usuVO);
				buscarEscalaNum(request, session, usuVO, filtroEscalaNumVO2);
				break;
			case ParamsVO.CMD_ELIMINAR:
				eliminarEscalaNum(request, session, usuVO);
				buscarEscalaNum(request, session, usuVO, filtroEscalaNumVO2);
				break;
			default:
				break;
			}

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

			if (filtroEscalaVO == null) {
				// System.out.println("filtro es null");
				filtroEscalaVO = new FiltroEscalaVO();
				filtroEscalaVO.setFilInst(Long.parseLong(usuVO.getInstId()));

			} else {
				filtroEscalaVO.setFilInst(Long.parseLong(usuVO.getInstId()));

			}

			session.setAttribute("listaVigencia",
					adminParametroInstDAO.getlistaVigencia(usuVO));

			EscalaConceptualVO escalaConceptualVO = new EscalaConceptualVO();
			InstParVO instParVO = new InstParVO();
			instParVO.setInsparcodinst(Long.parseLong(usuVO.getInstId()));
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

			cargarListas(request, session, usuVO);

		} catch (Exception e) {
			e.printStackTrace();

			throw new ServletException("" + e.getMessage());

		}
	}

	/**
	 * Metodo que obtiene la informacion de la escala conceptual
	 * 
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
						.getEscalaConcep(Long.parseLong(codInst),
								Long.parseLong(codVig),
								Long.parseLong(codNivelEva),
								Long.parseLong(codCodigo),
								Long.parseLong(codsede),
								Long.parseLong(codjornd),
								Long.parseLong(codmetodo),
								Long.parseLong(codnivel),
								Long.parseLong(codgrado));

				// Validacion para saber si esta escala conceptual ya fue
				// utilizada
				// if
				// (adminParametroInstDAO.isEvaluacionArea(escalaConceptualVO)){
				// escalaConceptualVO.setYaFueUtilizado(1);
				// }

				// Validacion para saber si esta escala conceptual ya fue
				// utilizada
				/*
				 * if
				 * (adminParametroInstDAO.isEvaluacionAsignatura(escalaConceptualVO
				 * )){ escalaConceptualVO.setYaFueUtilizado(1); }
				 */

				/** Valida si ya se han utilizado el literal */
				String validar = adminParametroInstDAO.isExisteInstNivelEval(
						escalaConceptualVO.getInsconvigencia(),
						escalaConceptualVO.getInsconcodinst());
				if (validar.trim().length() > 0) {
					String msg = "";
					msg = "\n ADVERTENCIA:"
							+ "\n Existen estudiantes que ya fuernn evaluados."
							+ "\n nEsta seguro de realizar esta modificacinn?";
					escalaConceptualVO.setMsgValidarLiteral(msg);
				}

				session.setAttribute("escalaConceptualVO", escalaConceptualVO);
				// cargarListasEscalaConcep(request,session,usuVO,escalaConceptualVO);
			} else {
				// System.out.println("error al editar ");
			}

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
			String codjornd = param[5];
			String codmetodo = param[6];
			String codnivel = param[7];
			String codgrado = param[8];

			// System.out.println("codNivelEva " + codNivelEva);

			if (GenericValidator.isLong(codInst)
					&& GenericValidator.isLong(codVig)
					&& GenericValidator.isLong(codNivelEva)
					&& GenericValidator.isLong(codCodigo)) {

				/** Valida si ya se han utilizado el literal */
				String validar = adminParametroInstDAO.isExisteInstNivelEval(
						Long.parseLong(codVig), Long.parseLong(codInst));
				if (validar.trim().length() > 0) {
					String msg = "";
					msg = " No es posible eliminar la información."
							+ "\n Existen estudiantes que ya fuernn evaluados"
							+ "\n usando la escala valorativa:" + validar;
					request.setAttribute(ParamsVO.SMS, msg);
					return;
				}

				EscalaConceptualVO escalaCncepVO = adminParametroInstDAO
						.getEscalaConcep(Long.parseLong(codInst),
								Long.parseLong(codVig),
								Long.parseLong(codNivelEva),
								Long.parseLong(codCodigo),
								Long.parseLong(codsede),
								Long.parseLong(codjornd),
								Long.parseLong(codmetodo),
								Long.parseLong(codnivel),
								Long.parseLong(codgrado));

				/*
				 * if(!adminParametroInstDAO.isvalidarLiteralEvaluacion(
				 * escalaCncepVO)){
				 */
				// Eliminar registro
				session.removeAttribute("escalaConceptualVO");
				adminParametroInstDAO.eliminarEscalaConcep(
						Long.parseLong(codInst), Long.parseLong(codVig),
						Long.parseLong(codNivelEva), Long.parseLong(codCodigo),
						Long.parseLong(codsede), Long.parseLong(codjornd),
						Long.parseLong(codmetodo), Long.parseLong(codnivel),
						Long.parseLong(codgrado));

				request.setAttribute(ParamsVO.SMS,
						"información eliminada satisfactoriamente.");

				EscalaConceptualVO escalaConceptualVO = new EscalaConceptualVO();
				escalaConceptualVO.setInsconcodinst(Long.parseLong(codInst));
				escalaConceptualVO.setInsconvigencia(Long.parseLong(codVig));
				escalaConceptualVO
						.setInsconniveval(Long.parseLong(codNivelEva));
				escalaConceptualVO.setInsconcodsede(Long.parseLong(codsede));
				escalaConceptualVO.setInsconcodjorn(Long.parseLong(codjornd));
				escalaConceptualVO.setInsconcodmetod(Long.parseLong(codmetodo));
				escalaConceptualVO.setInsconcodnivel(Long.parseLong(codnivel));
				escalaConceptualVO.setInsconcodgrado(Long.parseLong(codgrado));

				String escalaMEN = adminParametroInstDAO
						.isAsignarEquiMEN(escalaConceptualVO);
				if (escalaMEN == null) {
					request.setAttribute(ParamsVO.SMS,
							"información eliminada satisfactoriamente.");
				} else {
					request.setAttribute(ParamsVO.SMS,
							"información eliminada satisfactoriamente."
									+ escalaMEN);
				}
				/*
				 * }else{ request.setAttribute(ParamsVO.SMS,
				 * "Este literal ya fue utilizado en al menos una evaluacinn de un estudiante. No puede modificarlo."
				 * ); }
				 */
				// cargarListasEscalaConcep(request,session,usuVO,new
				// EscalaConceptualVO());
			} else {
				// System.out.println("error al editar ");
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error: " + e.getMessage());
		}
	}

	// ESCALA NUMERICA

	/**
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

			cargarFiltro(request, session, usuVO, filtroEscalaNumVO);
			if (filtroEscalaNumVO != null)
				request.setAttribute("listaAllEscalaNum", adminParametroInstDAO
						.getListaEscalaNum(filtroEscalaNumVO));

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
			if (filtroEscalaNumVO == null) {
				// System.out.println("filtro es null");
				filtroEscalaNumVO = new FiltroEscalaNumVO();
				filtroEscalaNumVO.setFilNumInst(Long.parseLong(usuVO
						.getInstId()));
				filtroEscalaNumVO.setFilNumVigencia(adminParametroInstDAO
						.getVigenciaInst(Long.parseLong(usuVO.getInstId())));
			} else {
				filtroEscalaNumVO.setFilNumInst(Long.parseLong(usuVO
						.getInstId()));
			}

			session.setAttribute("listaVigencia",
					adminParametroInstDAO.getlistaVigencia(usuVO));

			EscalaConceptualVO escalaConceptualVO = new EscalaConceptualVO();
			InstParVO instParVO = new InstParVO();
			instParVO.setInsparcodinst(Long.parseLong(usuVO.getInstId()));
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

			if (filtroEscalaNumVO.getFilNumniveval() == ParamsVO.NVLEVA_SED) {
				request.setAttribute("listaSed", adminParametroInstDAO
						.getSede(Long.parseLong(usuVO.getInstId())));
			}
			if (filtroEscalaNumVO.getFilNumniveval() == ParamsVO.NVLEVA_JORN) {
				request.setAttribute(
						"listaJord",
						adminParametroInstDAO.getJornada(
								Long.parseLong(usuVO.getInstId()), -99));
			}

			request.setAttribute("listaMen",
					adminParametroInstDAO.getEscalaMEN());
			cargarListas(request, session, usuVO);
		} catch (Exception e) {
			e.printStackTrace();

			throw new ServletException("" + e.getMessage());

		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @throws ServletException
	 */
	public void editarEscalaNum(HttpServletRequest request,
			HttpSession session, Login usuVO) throws ServletException {
		String msg = "";
		try {

			String param[] = request.getParameterValues("param");
			String codInst = param[0];
			String codVig = param[1];
			String codNivelEva = param[2];
			String codEqvInst = param[3];
			String codEqvMEN = param[4];
			String codsede = param[5];
			String codjornd = param[6];
			String codmetodo = param[7];
			String codnivel = param[8];
			String codgrado = param[9];
			String minValor = param[10];
			String maxValor = param[11];

			// System.out.println("codNivelEva " + codNivelEva);

			if (GenericValidator.isLong(codInst)
					&& GenericValidator.isLong(codVig)
					&& GenericValidator.isLong(codNivelEva)
					&& GenericValidator.isLong(codEqvInst)
					&& GenericValidator.isLong(codEqvMEN)) {

				/** Valida si ya se han utilizado la escala */
				String validar = adminParametroInstDAO.isExisteInstNivelEval(
						Long.parseLong(codVig), Long.parseLong(codInst));
				// System.out.println("numero co " + validar.trim().length());
				if (validar.trim().length() > 0) {

					msg = "\n ADVERTENCIA:"
							+ "\n Existen estudiantes que ya fuernn evaluados."
							+ "\n nEsta seguro de realizar esta modificacinn?";

				}

				session.removeAttribute("escalaNumericaVO");
				EscalaNumericaVO escalaNumericaVO = adminParametroInstDAO
						.getEscalaNumerica(Long.parseLong(codInst),
								Long.parseLong(codVig),
								Long.parseLong(codNivelEva),
								Long.parseLong(codEqvInst),
								Long.parseLong(codEqvMEN),
								Long.parseLong(codsede),
								Long.parseLong(codjornd),
								Long.parseLong(codmetodo),
								Long.parseLong(codnivel),
								Long.parseLong(codgrado),
								Double.parseDouble(minValor),
								Double.parseDouble(maxValor));

				escalaNumericaVO.setMsgValidarRango(msg);
				session.setAttribute("escalaNumericaVO", escalaNumericaVO);

				// cargarListasEscalaConcep(request,session,usuVO,escalaConceptualVO);
			} else {
				// System.out.println("error al editar ");
			}

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
	public void eliminarEscalaNum(HttpServletRequest request,
			HttpSession session, Login usuVO) throws ServletException {
		try {

			String param[] = request.getParameterValues("param");
			String codInst = param[0];
			String codVig = param[1];
			String codNivelEva = param[2];
			String codEqInst = param[3];
			String codEqMEN = param[4];
			String codsede = param[5];
			String codjornd = param[6];
			String codmetodo = param[7];
			String codnivel = param[8];
			String codgrado = param[9];
			String valorMin = param[10];
			String valorMax = param[11];

			/** Valida si ya se han utilizado el literal */
			String validar = adminParametroInstDAO.isExisteInstNivelEval(
					Long.parseLong(codVig), Long.parseLong(codInst));
			if (validar.trim().length() > 0) {
				String msg = "";
				msg = " No es posible eliminar la información."
						+ "\n Existen estudiantes que ya fuernn evaluados"
						+ "\n usando la escala valorativa:" + validar;
				request.setAttribute(ParamsVO.SMS, msg);
				return;
			}

			// System.out.println("valorMin " + valorMin);
			// System.out.println("valorMax    " + valorMax );
			if (GenericValidator.isLong(codInst)
					&& GenericValidator.isLong(codVig)
					&& GenericValidator.isLong(codNivelEva)
					&& GenericValidator.isLong(codEqInst)
					&& GenericValidator.isLong(codEqMEN)) {

				session.removeAttribute("escalaNumericaVO");
				adminParametroInstDAO.eliminarEscalaNum_(
						Long.parseLong(codInst), Long.parseLong(codVig),
						Long.parseLong(codNivelEva), Long.parseLong(codEqInst),
						Long.parseLong(codEqMEN), Long.parseLong(codsede),
						Long.parseLong(codjornd), Long.parseLong(codmetodo),
						Long.parseLong(codnivel), Long.parseLong(codgrado),
						Double.parseDouble(valorMin),
						Double.parseDouble(valorMax));

				EscalaNumericaVO escalaNumericaVO2 = new EscalaNumericaVO();
				escalaNumericaVO2.setInsnumcodinst(Long.parseLong(codInst));
				escalaNumericaVO2.setInsnumvigencia(Long.parseLong(codVig));
				escalaNumericaVO2.setInsnumniveval(Long.parseLong(codNivelEva));
				escalaNumericaVO2.setInsnumequinst(Long.parseLong(codEqInst));
				escalaNumericaVO2.setInsnumequmen(Long.parseLong(codEqMEN));
				escalaNumericaVO2.setInsnumcodsede(Long.parseLong(codsede));
				escalaNumericaVO2.setInsnumcodjorn(Long.parseLong(codjornd));
				escalaNumericaVO2.setInsnumcodmetod(Long.parseLong(codmetodo));
				escalaNumericaVO2.setInsnumcodnivel(Long.parseLong(codnivel));
				escalaNumericaVO2.setInsnumcodgrado(Long.parseLong(codgrado));

				escalaNumericaVO2.setInsnumvalmax(""
						+ getDouble((int) (convertir(valorMax) * 100)));
				escalaNumericaVO2.setInsnumvalmin(""
						+ getDouble((int) (convertir(valorMin) * 100)));

				// Valida si faltar rangos por asingar
				// Valida si se cubrio el rango registrado en
				// INSTITUCION_NIVEL_EVALUACION
				String rangoPorAsignar = adminParametroInstDAO
						.isValidarRangosAsignarEliminar(escalaNumericaVO2);
				if (rangoPorAsignar == null) {

					request.setAttribute(ParamsVO.SMS,
							"La información fue eliminada satisfactoriamente.");
				} else {

					request.setAttribute(ParamsVO.SMS,
							"La información fue eliminada satisfactoriamente."
									+ rangoPorAsignar);
				}

				// request.setAttribute(ParamsVO.SMS,"información eliminada satisfactoriamente.");
				// cargarListasEscalaConcep(request,session,usuVO,new
				// EscalaConceptualVO());
			} else {
				// System.out.println("error al editar ");
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error: " + e.getMessage());
		}
	}

	// NIVEL EVALUACION
	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param filtroNivelEvalVO
	 * @throws ServletException
	 */
	private void buscarNivelEval(HttpServletRequest request, HttpSession session, Login usuVO, FiltroNivelEvalVO filtroNivelEvalVO) throws ServletException {

		try {

			long codInst_ = Long.parseLong(usuVO.getInstId());

			cargarFiltro(request, session, usuVO, filtroNivelEvalVO);
			request.setAttribute("listaInstNivelEvalVO", adminParametroInstDAO.getListaNivelEval(filtroNivelEvalVO));

			if (filtroNivelEvalVO.getFilniveval() == ParamsVO.NVLEVA_SED_JORN) {
				if (filtroNivelEvalVO.getFilSede() != 0) {
					request.getSession().setAttribute("listaJornada", adminParametroInstDAO.getJornada(codInst_, filtroNivelEvalVO.getFilSede()));
				}
			}

			if (filtroNivelEvalVO.getFilniveval() == ParamsVO.NVLEVA_SED_METD) {
				request.getSession().setAttribute("listaMetodo", adminParametroInstDAO.getMetodologia(codInst_));
			}

			if (filtroNivelEvalVO.getFilniveval() == ParamsVO.NVLEVA_SED_JORN_METD) {
				if (filtroNivelEvalVO.getFilSede() != 0) {
					request.getSession().setAttribute("listaJornada", adminParametroInstDAO.getJornada(codInst_, filtroNivelEvalVO.getFilSede()));
				}
				request.getSession().setAttribute("listaMetodo", adminParametroInstDAO.getMetodologia(codInst_));
			}

			if (filtroNivelEvalVO.getFilniveval() == ParamsVO.NVLEVA_SED_METD_NVL) {
				request.getSession().setAttribute("listaMetodo", adminParametroInstDAO.getMetodologia(codInst_));
				if (filtroNivelEvalVO.getFilMetodo() > 0) {
					request.getSession().setAttribute("listaNivel", adminParametroInstDAO.getNivel(codInst_, filtroNivelEvalVO.getFilMetodo()));
				}
			}

			if (filtroNivelEvalVO.getFilniveval() == ParamsVO.NVLEVA_SED_METD_GRD) {
				request.getSession().setAttribute("listaMetodo", adminParametroInstDAO.getMetodologia(codInst_));
				if (filtroNivelEvalVO.getFilMetodo() > 0) {
					request.getSession().setAttribute("listaGrado", adminParametroInstDAO.getGradoMetodologia(codInst_,	filtroNivelEvalVO.getFilMetodo()));
				}
			}

			if (filtroNivelEvalVO.getFilniveval() == ParamsVO.NVLEVA_SED_JORD_METD_GRD) {
				request.getSession().setAttribute("listaMetodo", adminParametroInstDAO.getMetodologia(codInst_));
				if (filtroNivelEvalVO.getFilMetodo() > 0) {
					request.getSession().setAttribute("listaGrado", adminParametroInstDAO.getGradoMetodologia(codInst_, filtroNivelEvalVO.getFilMetodo()));
				}
			}

			if (filtroNivelEvalVO.getFilniveval() == ParamsVO.NVLEVA_SED_METD_NVL_GRD) {
				request.getSession().setAttribute("listaMetodo", adminParametroInstDAO.getMetodologia(codInst_));
				if (filtroNivelEvalVO.getFilMetodo() > 0) {
					request.getSession().setAttribute("listaNivel", adminParametroInstDAO.getNivel(codInst_, filtroNivelEvalVO.getFilMetodo()));
				}

				if (filtroNivelEvalVO.getFilNivel() > -1 && filtroNivelEvalVO.getFilMetodo() > 0) {
					request.getSession().setAttribute("listaGrado", adminParametroInstDAO.getGrado(codInst_, filtroNivelEvalVO.getFilMetodo(), filtroNivelEvalVO.getFilNivel()));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();

			request.setAttribute(ParamsVO.SMS, "" + e.getMessage());

		}
	}

	public void editarInstNivelEval(HttpServletRequest request,	HttpSession session, Login usuVO) throws ServletException {
		
		try {

			String param[] = request.getParameterValues("param");
			String codInst = param[0];
			String codVig = param[1];
			String codNivelEva = param[2];
			String codsede = param[3];
			String codjornd = param[4];
			String codmetodo = param[5];
			String codnivel = param[6];
			String codgrado = param[7];

			if (GenericValidator.isLong(codInst) && GenericValidator.isLong(codVig) && GenericValidator.isLong(codNivelEva)) {

				session.removeAttribute("escalaNumericaVO");
				
				InstNivelEvaluacionVO instNivelEvaluacionVO = new InstNivelEvaluacionVO();
				instNivelEvaluacionVO.setInsnivcodinst(Long.parseLong(codInst));
				instNivelEvaluacionVO.setInsnivvigencia(Long.parseLong(codVig));
				instNivelEvaluacionVO.setInsnivcodniveleval(Long.parseLong(codNivelEva));
				instNivelEvaluacionVO.setInsnivcodsede(Long.parseLong(codsede));
				instNivelEvaluacionVO.setInsnivcodjorn(Long.parseLong(codjornd));
				instNivelEvaluacionVO.setInsnivcodmetod(Long.parseLong(codmetodo));
				instNivelEvaluacionVO.setInsnivcodnivel(Long.parseLong(codnivel));
				instNivelEvaluacionVO.setInsnivcodgrado(Long.parseLong(codgrado));

				instNivelEvaluacionVO = adminParametroInstDAO.obtenerInstNivelEval(instNivelEvaluacionVO);

				if (adminParametroInstDAO.isUtilizadoNivelEval(instNivelEvaluacionVO)) {
					instNivelEvaluacionVO.setYaFueUtilizado(1);
				}
				
				// System.out.println(" instNivelEvaluacionVO.setYaFueUtilizado  "
				// + instNivelEvaluacionVO.getYaFueUtilizado());
				session.removeAttribute("instNivelEvaluacionVO");
				instNivelEvaluacionVO.setEdicion(true);
				session.setAttribute("instNivelEvaluacionVO", instNivelEvaluacionVO);
				long codInst_ = Long.parseLong(usuVO.getInstId());

				if (instNivelEvaluacionVO.getInsnivcodniveleval() == ParamsVO.NVLEVA_SED_JORN) {
					
					if (instNivelEvaluacionVO.getInsnivcodsede() > -99) {
						
						request.getSession().setAttribute("listaJornada", adminParametroInstDAO.getJornada(codInst_, instNivelEvaluacionVO.getInsnivcodsede()));
					}
				}

				if (instNivelEvaluacionVO.getInsnivcodniveleval() == ParamsVO.NVLEVA_SED_METD) {
					request.getSession().setAttribute("listaMetodo", adminParametroInstDAO.getMetodologia(codInst_));
				}

				if (instNivelEvaluacionVO.getInsnivcodniveleval() == ParamsVO.NVLEVA_SED_JORN_NVL) {
					if (instNivelEvaluacionVO.getInsnivcodsede() > -99) {
						
						request.getSession().setAttribute("listaJornada", adminParametroInstDAO.getJornada(codInst_, instNivelEvaluacionVO.getInsnivcodsede()));
						
						if (instNivelEvaluacionVO.getInsnivcodmetod() > -99) {
							request.getSession().setAttribute("listaNivel", adminParametroInstDAO.getNivel(Long.parseLong(codInst), instNivelEvaluacionVO.getInsnivcodmetod()));
						} else {
							request.getSession().setAttribute("listaNivel", adminParametroInstDAO.getNivel(Long.parseLong(codInst)));
						}
					}
				}

				if (instNivelEvaluacionVO.getInsnivcodniveleval() == ParamsVO.NVLEVA_SED_JORD_METD_GRD) {
					if (instNivelEvaluacionVO.getInsnivcodsede() > -99) {
						request.getSession().setAttribute(
								"listaJornada",
								adminParametroInstDAO.getJornada(codInst_,
										instNivelEvaluacionVO
												.getInsnivcodsede()));
						if (instNivelEvaluacionVO.getInsnivcodmetod() > -99) {
							request.getSession().setAttribute(
									"listaNivel",
									adminParametroInstDAO.getNivel(Long
											.parseLong(codInst),
											instNivelEvaluacionVO
													.getInsnivcodmetod()));
						} else {
							request.getSession().setAttribute(
									"listaNivel",
									adminParametroInstDAO.getNivel(Long
											.parseLong(codInst)));
						}
						request.getSession().setAttribute(
								"listaGrado",
								adminParametroInstDAO.getGrado(Long
										.parseLong(codInst)));

					}
				}

				if (instNivelEvaluacionVO.getInsnivcodniveleval() == ParamsVO.NVLEVA_SED_JORD_METD_GRD) {
					if (instNivelEvaluacionVO.getInsnivcodsede() > -99) {
						request.getSession().setAttribute(
								"listaJornada",
								adminParametroInstDAO.getJornada(codInst_,
										instNivelEvaluacionVO
												.getInsnivcodsede()));
						if (instNivelEvaluacionVO.getInsnivcodmetod() > -99) {
							request.getSession().setAttribute(
									"listaNivel",
									adminParametroInstDAO.getNivel(Long
											.parseLong(codInst),
											instNivelEvaluacionVO
													.getInsnivcodmetod()));
						} else {
							request.getSession().setAttribute(
									"listaNivel",
									adminParametroInstDAO.getNivel(Long
											.parseLong(codInst)));
						}
						request.getSession().setAttribute(
								"listaGrado",
								adminParametroInstDAO.getGrado(Long
										.parseLong(codInst)));

					}
				}

				if (instNivelEvaluacionVO.getInsnivcodniveleval() == ParamsVO.NVLEVA_SED_METD_NVL_GRD) {
					request.getSession().setAttribute("listaMetodo",
							adminParametroInstDAO.getMetodologia(codInst_));
					if (instNivelEvaluacionVO.getInsnivcodmetod() > 0) {
						request.getSession().setAttribute(
								"listaNivel",
								adminParametroInstDAO.getNivel(codInst_,
										instNivelEvaluacionVO
												.getInsnivcodmetod()));
					}

					if (instNivelEvaluacionVO.getInsnivcodnivel() > -1
							&& instNivelEvaluacionVO.getInsnivcodmetod() > 0) {
						request.getSession().setAttribute(
								"listaGrado",
								adminParametroInstDAO.getGrado(codInst_,
										instNivelEvaluacionVO
												.getInsnivcodmetod(),
										instNivelEvaluacionVO
												.getInsnivcodnivel()));
					}

				}

				if (instNivelEvaluacionVO.getInsnivcodniveleval() == ParamsVO.NVLEVA_SED_JORD_METD_NVL_GRD) {
					request.getSession().setAttribute("listaMetodo",
							adminParametroInstDAO.getMetodologia(codInst_));

					if (instNivelEvaluacionVO.getInsnivcodsede() > 0) {
						request.getSession().setAttribute(
								"listaJornada",
								adminParametroInstDAO.getJornada(codInst_,
										instNivelEvaluacionVO
												.getInsnivcodsede()));
					}

					if (instNivelEvaluacionVO.getInsnivcodmetod() > 0) {
						request.getSession().setAttribute(
								"listaNivel",
								adminParametroInstDAO.getNivel(codInst_,
										instNivelEvaluacionVO
												.getInsnivcodmetod()));
					}

					if (instNivelEvaluacionVO.getInsnivcodnivel() > -1
							&& instNivelEvaluacionVO.getInsnivcodmetod() > 0) {
						request.getSession().setAttribute(
								"listaGrado",
								adminParametroInstDAO.getGrado(codInst_,
										instNivelEvaluacionVO
												.getInsnivcodmetod(),
										instNivelEvaluacionVO
												.getInsnivcodnivel()));
					}

				}
				cargarListas(request, session, usuVO);
			} else {
				// System.out.println("error al editar ");
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error: " + e.getMessage());
		}
	}

	public void eliminarInstNivelEval(HttpServletRequest request,
			HttpSession session, Login usuVO) throws ServletException {
		try {
			nuevoNivelEval(request, session, usuVO);

			String param[] = request.getParameterValues("param");
			String codInst = param[0];
			String codVig = param[1];
			String codNivelEva = param[2];
			String codsede = param[3];
			String codjornd = param[4];
			String codmetodo = param[5];
			String codnivel = param[6];
			String codgrado = param[7];

			if (GenericValidator.isLong(codInst)
					&& GenericValidator.isLong(codVig)
					&& GenericValidator.isLong(codNivelEva)) {

				session.removeAttribute("escalaNumericaVO");
				InstNivelEvaluacionVO instNivelEvaluacionVO = new InstNivelEvaluacionVO();
				instNivelEvaluacionVO.setInsnivcodinst(Long.parseLong(codInst));
				instNivelEvaluacionVO.setInsnivvigencia(Long.parseLong(codVig));
				instNivelEvaluacionVO.setInsnivcodniveleval(Long
						.parseLong(codNivelEva));
				instNivelEvaluacionVO.setInsnivcodsede(Long.parseLong(codsede));
				instNivelEvaluacionVO
						.setInsnivcodjorn(Long.parseLong(codjornd));
				instNivelEvaluacionVO.setInsnivcodmetod(Long
						.parseLong(codmetodo));
				instNivelEvaluacionVO.setInsnivcodnivel(Long
						.parseLong(codnivel));
				instNivelEvaluacionVO.setInsnivcodgrado(Long
						.parseLong(codgrado));

				/**
				 * Validacion para saber si existe escalas asociadas a este
				 * nivel de evalacion de la institucion
				 * */
				String validarEliminar = isValidareliminarInstNivelEval(
						request, instNivelEvaluacionVO);

				if (validarEliminar == null) {
					instNivelEvaluacionVO = adminParametroInstDAO
							.eliminarInstNivelEval(instNivelEvaluacionVO);
					request.setAttribute(ParamsVO.SMS,
							"información eliminada satisfactoriamente.");
					Logger.print(usuVO.getUsuarioId(),
							"Elimino información de nivel de evaluacion INSTITUCION_NIVEL_EVALUACION. "
									+ usuVO.getInstId(), 6, 1, this.toString());
				} else {
					request.setAttribute(
							ParamsVO.SMS,
							"No es posible eliminar la información: Existen escalas relacionadas a este nivel de evaluacinn."
									+ "Si desea modificar esta información primero debe eliminar dichas escalas.");
				}

				session.removeAttribute("instNivelEvaluacionVO");
				instNivelEvaluacionVO.setEdicion(true);
				// session.setAttribute("instNivelEvaluacionVO",
				// instNivelEvaluacionVO);

				// cargarListasEscalaConcep(request,session,usuVO,escalaConceptualVO);
			} else {
				// System.out.println("error al editar ");
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error: " + e.getMessage());
		}
	}
	
	public void buscarAsignaturaNivelEval(HttpServletRequest request, HttpSession session, FiltroNivelEvalVO filtroNivelEvalVO) throws ServletException {
		
		try {
			
			TipoEvaluacionInstAsigVO tipoEvaluacionInstAsigVO = new TipoEvaluacionInstAsigVO();
			
			tipoEvaluacionInstAsigVO.setTipEvalCodInst(filtroNivelEvalVO.getFilInst());
			tipoEvaluacionInstAsigVO.setTipEvalVigencia(filtroNivelEvalVO.getFilVigencia());
			tipoEvaluacionInstAsigVO.setTipEvalCodJorn(filtroNivelEvalVO.getFilJorn());
			
			tipoEvaluacionInstAsigVO = adminParametroInstDAO.buscarAsignaturaNivelEval(tipoEvaluacionInstAsigVO);
			
			session.removeAttribute("tipoEvaluacionInstAsigVO");
			session.setAttribute("tipoEvaluacionInstAsigVO", tipoEvaluacionInstAsigVO);
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error: " + e.getMessage());
		}
		
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param filtroNivelEvalVO
	 * @throws ServletException
	 */
	private void cargarFiltro(HttpServletRequest request, HttpSession session, Login usuVO, FiltroNivelEvalVO filtroNivelEvalVO) throws ServletException {
		
		try {

			if (filtroNivelEvalVO == null) {
				filtroNivelEvalVO = new FiltroNivelEvalVO();
				filtroNivelEvalVO.setFilInst(Long.parseLong(usuVO.getInstId()));
				filtroNivelEvalVO.setFilVigencia(adminParametroInstDAO.getVigenciaInst(Long.parseLong(usuVO.getInstId())));
			} else {
				filtroNivelEvalVO.setFilInst(Long.parseLong(usuVO.getInstId()));
				filtroNivelEvalVO.setFilVigencia(adminParametroInstDAO.getVigenciaInst(Long.parseLong(usuVO.getInstId())));
			}
			
			session.setAttribute("listaVigencia", adminParametroInstDAO.getlistaVigencia(usuVO));

			EscalaConceptualVO escalaConceptualVO = new EscalaConceptualVO();
			InstParVO instParVO = new InstParVO();
			instParVO.setInsparcodinst(Long.parseLong(usuVO.getInstId()));
			instParVO.setInsparvigencia(adminParametroInstDAO.getVigenciaInst(Long.parseLong(usuVO.getInstId())));
			instParVO = adminParametroInstDAO.getPeriodoInstActual(instParVO.getInsparvigencia(), instParVO.getInsparcodinst());

			escalaConceptualVO.setInsconniveval(instParVO.getInsparniveval());
			filtroNivelEvalVO.setFilniveval(instParVO.getInsparniveval());
			filtroNivelEvalVO.setFilVigencia(instParVO.getInsparvigencia());

			session.removeAttribute("filtroNivelEvalVO");
			session.setAttribute("filtroNivelEvalVO", filtroNivelEvalVO);

			if (filtroNivelEvalVO.getFilniveval() == ParamsVO.NVLEVA_SED_GRD) {
				if (filtroNivelEvalVO.getFilMetodo() > -99 && filtroNivelEvalVO.getFilNivel() > -99) {
					request.getSession().setAttribute("listaGrado", adminParametroInstDAO.getGrado(filtroNivelEvalVO.getFilInst(), filtroNivelEvalVO.getFilNivel(),	filtroNivelEvalVO.getFilMetodo()));
				} else if (filtroNivelEvalVO.getFilMetodo() > -99) {
					request.getSession().setAttribute("listaGrado", adminParametroInstDAO.getGradoMetodologia(filtroNivelEvalVO.getFilInst(), filtroNivelEvalVO.getFilMetodo()));
				} else if (filtroNivelEvalVO.getFilNivel() > -99) {
					request.getSession().setAttribute("listaGrado", adminParametroInstDAO.getGrado(filtroNivelEvalVO.getFilInst(), filtroNivelEvalVO.getFilNivel()));
				} else {
					request.getSession().setAttribute("listaGrado",	adminParametroInstDAO.getGrado(filtroNivelEvalVO.getFilInst()));
				}
			}

			cargarListas(request, session, usuVO);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("" + e.getMessage());
		}
	}

	
	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param filtroNivelEvalVO
	 * @throws ServletException
	 */
	private String isValidareliminarInstNivelEval(HttpServletRequest request,
			InstNivelEvaluacionVO instNivelEvaluacionVO)
			throws ServletException {
		String resp = null;
		try {
			// instNivelEvaluacionVO
			FiltroEscalaVO filtroEscalaVO = new FiltroEscalaVO();

			filtroEscalaVO.setFilVigencia(instNivelEvaluacionVO
					.getInsnivvigencia());
			filtroEscalaVO.setFilInst(instNivelEvaluacionVO.getInsnivcodinst());
			filtroEscalaVO.setFilniveval(instNivelEvaluacionVO
					.getInsnivcodniveleval());
			filtroEscalaVO.setFilSede(instNivelEvaluacionVO.getInsnivcodsede());
			filtroEscalaVO.setFilJorn(instNivelEvaluacionVO.getInsnivcodjorn());
			filtroEscalaVO.setFilMetodo(instNivelEvaluacionVO
					.getInsnivcodmetod());
			filtroEscalaVO.setFilNivel(instNivelEvaluacionVO
					.getInsnivcodnivel());
			filtroEscalaVO.setFilGrado(instNivelEvaluacionVO
					.getInsnivcodgrado());
			// escalaConceptualVO
			if (adminParametroInstDAO.getListaEscalaConcep(filtroEscalaVO)
					.size() > 0) {
				return ParamsVO.ESCALA_CONCEPTUAL_;
			}

			FiltroEscalaNumVO filtroEscalaNumVO = new FiltroEscalaNumVO();

			filtroEscalaNumVO.setFilNumVigencia(instNivelEvaluacionVO
					.getInsnivvigencia());
			filtroEscalaNumVO.setFilNumInst(instNivelEvaluacionVO
					.getInsnivcodinst());
			filtroEscalaNumVO.setFilNumniveval(instNivelEvaluacionVO
					.getInsnivcodniveleval());
			filtroEscalaNumVO.setFilNumSede(instNivelEvaluacionVO
					.getInsnivcodsede());
			filtroEscalaNumVO.setFilNumJorn(instNivelEvaluacionVO
					.getInsnivcodjorn());
			filtroEscalaNumVO.setFilNumMetodo(instNivelEvaluacionVO
					.getInsnivcodmetod());
			filtroEscalaNumVO.setFilNumNivel(instNivelEvaluacionVO
					.getInsnivcodnivel());
			filtroEscalaNumVO.setFilNumGrado(instNivelEvaluacionVO
					.getInsnivcodgrado());

			//
			if (adminParametroInstDAO.getListaEscalaNum(filtroEscalaNumVO)
					.size() > 0) {
				return ParamsVO.ESCALA_NUMERICA_;
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, e.getMessage());
			throw new ServletException("" + e.getMessage());

		}
		return resp;
	}

	/**
	 * @function: Valida si existe escalas numericas asociadas a este nivel de
	 *            evalucacion y que los rangos no hallan sido modificados
	 * @param request
	 * @param instNivelEvaluacionVO
	 * @return
	 * @throws ServletException
	 */

	/**
	 * @function:
	 * @param num
	 * @return
	 */
	private double getDouble(int num) {
		return (new Double(new Double(num).doubleValue() / 100.00))
				.doubleValue();
	}

	/**
	 * @function:
	 * @param valor
	 * @return
	 */
	private double convertir(String valor) {
		return Double.parseDouble(valor);
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param filtroNivelEvalVO
	 * @throws ServletException
	 */
	private void cargarListas(HttpServletRequest request, HttpSession session, Login usuVO) throws ServletException {

		try {

			InstParVO instParVO = new InstParVO();
			instParVO.setInsparcodinst(Long.parseLong(usuVO.getInstId()));
			instParVO.setInsparvigencia(adminParametroInstDAO.getVigenciaInst(Long.parseLong(usuVO.getInstId())));
			instParVO = adminParametroInstDAO.getPeriodoInstActual(instParVO.getInsparvigencia(), instParVO.getInsparcodinst());

			if (instParVO.getInsparniveval() == ParamsVO.NVLEVA_METD) {
				request.getSession().setAttribute("listaMetodo", adminParametroInstDAO.getMetodologia(Long.parseLong(usuVO.getInstId())));
			}

			if (instParVO.getInsparniveval() == ParamsVO.NVLEVA_JORN) {
				request.getSession().setAttribute("listaJornada", adminParametroInstDAO.getJornada(Long.parseLong(usuVO.getInstId()), -99));
			}

			if (instParVO.getInsparniveval() == ParamsVO.NVLEVA_NVL) {
				request.getSession().setAttribute("listaNivel", adminParametroInstDAO.getNivel(Long.parseLong(usuVO.getInstId())));
			}

			if (instParVO.getInsparniveval() == ParamsVO.NVLEVA_GRD) {
				request.getSession().setAttribute("listaGrado", adminParametroInstDAO.getGrado(Long.parseLong(usuVO.getInstId())));
			}
			
			session.setAttribute("listaSed", adminParametroInstDAO.getSede(Long.parseLong(usuVO.getInstId())));

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("" + e.getMessage());
		}
	}
	

	public void nuevoNivelEval(HttpServletRequest request, HttpSession session, Login usuVO) throws ServletException {

		try {

			session.removeAttribute("instNivelEvaluacionVO");
			session.setAttribute("instNivelEvaluacionVO", null);
			request.getSession().removeAttribute("instNivelEvaluacionVO");
			// InstNivelEvaluacionVO instNivelEvaluacionVO = new
			// InstNivelEvaluacionVO();
			// instNivelEvaluacionVO.setEdicion(false);
			// instNivelEvaluacionVO.setInsnivvigencia(
			// adminParametroInstDAO.getVigenciaInst(Long.parseLong(usuVO.getInstId()))
			// );
			// instNivelEvaluacionVO.setInsnivcodinst(Long.parseLong(usuVO.getInstId())
			// );
			//
			// InstParVO instParVO = new InstParVO();
			// instParVO.setInsparcodinst(Long.parseLong(usuVO.getInstId()) );
			// instParVO.setInsparvigencia(
			// adminParametroInstDAO.getVigenciaInst(Long.parseLong(usuVO.getInstId()))
			// );
			// instParVO =
			// adminParametroInstDAO.getPeriodoInstActual(instParVO.getInsparvigencia(),
			// instParVO.getInsparcodinst());
			// instNivelEvaluacionVO.setInsnivcodniveleval(instParVO.getInsparniveval());
			//
			// session.setAttribute("instNivelEvaluacionVO",instNivelEvaluacionVO);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	
}
