/**
 * 
 */
package siges.gestionAdministrativa.cierreVigencia.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;

import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.gestionAdministrativa.cierreVigencia.dao.CierreVigenciaDAO;
import siges.gestionAdministrativa.cierreVigencia.thread.Hilo;
import siges.gestionAdministrativa.cierreVigencia.vo.ItemVO;
import siges.gestionAdministrativa.cierreVigencia.vo.ParamsVO;
import siges.login.beans.Login;
import siges.util.Acceso;
import siges.util.Logger;

/**
 * 28/04/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class Nuevo extends Service {

	public String FICHA_CIERRE_VIG;
	public String FICHA_CONSULTA_CIERRE;
	public String FICHA_CONSULTAR_CIERRE;
	public String FICHA_REPORTE_CIERRE;
	private String FICHA_DUPLI_PLAN_ESTUD;
	private ResourceBundle rb;
	private String contextoTotal;
	private CierreVigenciaDAO cierreVigenciaDAO;
	private Login usuVO;
	private static Cursor cursor = new Cursor();

	/**
	 * 
	 /*
	 * 
	 * @function:
	 * @param config
	 * @throws ServletException
	 *             (non-Javadoc)
	 * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		cierreVigenciaDAO = new CierreVigenciaDAO(new Cursor());
		FICHA_CIERRE_VIG = config.getInitParameter("FICHA_CIERRE_VIG");
		FICHA_DUPLI_PLAN_ESTUD = config
				.getInitParameter("FICHA_DUPLI_PLAN_ESTUD");
		FICHA_CONSULTA_CIERRE = config
				.getInitParameter("FICHA_CONSULTA_CIERRE");
		FICHA_CONSULTAR_CIERRE = config
				.getInitParameter("FICHA_CONSULTAR_CIERRE");
		FICHA_REPORTE_CIERRE = config.getInitParameter("FICHA_REPORTE_CIERRE");
	}

	/**
	 * Procesa la peticinn
	 * 
	 * @param request
	 *            peticinn
	 * @param response
	 *            respuesta
	 * @return Ruta de redireccinn
	 * @throws ServletException
	 * @throws IOException
	 *             (non-Javadoc)
	 * @see siges.common.service.Service#process(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String dispatcher[] = new String[2];
		rb = ResourceBundle
				.getBundle("siges.gestionAdministrativa.cierreVigencia.bundle.cierreVigencia");
		contextoTotal = context.getRealPath("/");
		String FICHA = null;

		HttpSession session = request.getSession();
		usuVO = (Login) session.getAttribute("login");
		int CMD = getCmd(request, session, ParamsVO.CMD_NUEVO);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		String ins = usuVO.getInstId();
		String sed = request.getParameter("sede");
		String jor = request.getParameter("jornada");
		String gra = request.getParameter("grado");
		String gru = request.getParameter("grupo");
		String vig = request.getParameter("vigencia");
		String nomgru = request.getParameter("nombregp");
		request.setAttribute("inst_", ins);
		request.setAttribute("sede_", sed);
		request.setAttribute("jornada_", jor);
		request.setAttribute("grado_", gra);
		request.setAttribute("grupo_", gru);
		request.setAttribute("vigencia_", vig);
		request.setAttribute("nomgru_", nomgru);
		// System.out.println("ins " +ins + " sede " + sed+" jor "+jor);
		// System.out.println("cierre vigencia TIPO " +TIPO + " CMD " + CMD);
		String np = Long.toString(usuVO.getLogNumPer());
		switch (TIPO) {
		case ParamsVO.FICHA_CIERRE_VIG:
			// System.out.println("ins "+usuVO.getInstId()+"vig "+Long.toString(usuVO.getVigencia_inst())+"cierre vigencia np "
			// +np);
			validarcierre(request, usuVO.getInstId(),
					Long.toString(usuVO.getVigencia_inst()), np);
			FICHA = FICHA_CIERRE_VIG;
			cargarFiltro(request);
			switch (CMD) {
			case ParamsVO.CMD_NUEVO:
				actualVigencia(request, usuVO);

				break;
			case ParamsVO.CMD_GUARDAR:

				cierreVigencia(request);
				cargarFiltro(request);
				break;

			}

			break;
		case ParamsVO.FICHA_DUPLI_PLAN_ESTUD:

			FICHA = FICHA_DUPLI_PLAN_ESTUD;
			cargarFiltroDuplicarPlanEstd(request);
			switch (CMD) {
			case ParamsVO.CMD_NUEVO:
				nevoDuplicarPlanEstudios(request);

				break;
			case ParamsVO.CMD_GUARDAR:
				duplicarPlanEstd(request);
				cargarFiltroDuplicarPlanEstd(request);
				break;

			}

			break;
		case ParamsVO.FICHA_CONSULTA_CIERRE:
			FICHA = FICHA_CONSULTA_CIERRE;
			ponerFiltros(request, ins);
			break;
		case ParamsVO.FICHA_CONSULTAR_CIERRE:
			FICHA = FICHA_CONSULTA_CIERRE;
			request.setAttribute("include", "1");
			ponerFiltros(request, ins);
			cargarFiltro2(request, ins);
			break;
		case 5:
			FICHA = FICHA_REPORTE_CIERRE;
			break;
		case 6:
			break;
		}
		dispatcher[0] = String.valueOf(Params.INCLUDE);
		// System.out.println("FICHA " + FICHA + " param " + Params.INCLUDE);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	public void ponerFiltros(HttpServletRequest req, String ins) {
		Collection colgg[] = Acceso.getGradoGrupo(ins);
		req.setAttribute("LoginSedescol", colgg[0]);
		req.setAttribute("LoginJorcol", colgg[1]);
		req.setAttribute("LogiGrado", colgg[2]);
		req.setAttribute("LoginGrupo", colgg[3]);
		req.setAttribute("Loginjorcolf", colgg[4]);
		req.setAttribute("LoginGrupof", colgg[5]);
	}

	public void validarcierre(HttpServletRequest req, String ins, String vig,
			String np) {
		Collection colgg[] = Acceso.getvalidaciones(ins, vig);
		req.setAttribute("Estudiantesn", colgg[0]);
		req.setAttribute("Estudiantesp", colgg[1]);
		req.setAttribute("Periodosa", colgg[2]);
		req.setAttribute("nperiodos", np);
	}

	/**
	 * @function: Realiza el llamado al hilo que se encargar de ejecutar el
	 *            procedimiento de cierre
	 * 
	 * @param request
	 * @throws ServletException
	 */
	private void cierreVigencia(HttpServletRequest request)
			throws ServletException {
		// System.out.println(new Date() + " - cierreVigencia");
		try {

			String vigencia = request.getParameter("perVigencia");
			long codInst = Long.parseLong(usuVO.getInstId());
			String idUsuario = usuVO.getUsuarioId();

			// System.out.println(new Date() + " - vigencia " + vigencia);
			// System.out.println(new Date() + " - codInst" +
			// usuVO.getInstId());

			if (vigencia != null && GenericValidator.isInt(vigencia)) {

				if (!cierreVigenciaDAO.getValidarHiloVigencia(
						Integer.valueOf(vigencia).intValue(),
						Long.parseLong(usuVO.getInstId()))) {
					cierreVigenciaDAO.guardarHiloCierre(
							Integer.valueOf(vigencia).intValue(), codInst,
							idUsuario);
					Hilo t = new Hilo();
					t.start();
				} else {
					request.setAttribute(
							ParamsVO.SMS,
							"No se puede realizar la operacinn:\n. Existe un proceso realizando esta actualizacinn.");
				}
			} else {
				request.setAttribute(ParamsVO.SMS,
						"Error realizando cierre vigencia.");
			}

			request.setAttribute("perVigencia", vigencia);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			request.setAttribute(ParamsVO.SMS, e.getMessage());
		}

	}

	/**
	 * @function: Realiza el llamado al hilo que se encargar de ejecutar el
	 *            procedimiento de cierre
	 * @param request
	 * @throws ServletException
	 */
	private void actualVigencia(HttpServletRequest request, Login usuVO)
			throws ServletException {
		// System.out.println(new Date() + " - actualVigencia");
		try {

			request.setAttribute(
					"perVigencia",
					""
							+ cierreVigenciaDAO.getVigenciaInst(Long
									.parseLong(usuVO.getInstId())));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			request.setAttribute(ParamsVO.SMS, e.getMessage());
		}

	}

	/**
	 * @param request
	 * @throws ServletException
	 */
	private void cargarFiltro(HttpServletRequest request)
			throws ServletException {
		List listaVigencia = new ArrayList();
		long codInst = 0;
		int parVigencia = 0;
		try {

			codInst = Long.parseLong(usuVO.getInstId());
			parVigencia = cierreVigenciaDAO.getVigenciaInst(codInst);
			ItemVO itemVO = new ItemVO(parVigencia, parVigencia + "");
			listaVigencia.add(itemVO);
			request.setAttribute("listaVigencia", listaVigencia);
			List listaTablaHilo = cierreVigenciaDAO.getCierreHilosVO(codInst,
					usuVO.getUsuarioId());
			request.setAttribute("listaTablaHilo", listaTablaHilo);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			request.setAttribute(ParamsVO.SMS, e.getMessage());
		}

	}

	private void cargarFiltro2(HttpServletRequest request, String ins)
			throws ServletException {
		List listaVigencia = new ArrayList();
		long codInst = 0;
		int parVigencia = 0;
		try {

			codInst = Long.parseLong(ins);
			parVigencia = cierreVigenciaDAO.getVigenciaInst(codInst);
			ItemVO itemVO = new ItemVO(parVigencia, parVigencia + "");
			listaVigencia.add(itemVO);
			request.setAttribute("listaVigencia2", listaVigencia);
			List listaTablaHilo = cierreVigenciaDAO.getCierreHilosVO(codInst,
					usuVO.getUsuarioId());
			request.setAttribute("listaTablaHilo2", listaTablaHilo);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			request.setAttribute(ParamsVO.SMS, e.getMessage());
		}

	}

	// duplicar plan de estudio

	/**
	 * @function: Realiza el llamado al hilo que se encargar de ejecutar el
	 *            procedimiento de cierre
	 * @param request
	 * @throws ServletException
	 */
	private void nevoDuplicarPlanEstudios(HttpServletRequest request)
			throws ServletException {
		// System.out.println(new Date() + " - nevoDuplicarPlanEstudios");
		try {
			long codInst = Long.parseLong(usuVO.getInstId());
			// System.out.println("codInst " + codInst);

			request.setAttribute("advertencia", "-1");
			request.setAttribute("perVigencia",
					"" + cierreVigenciaDAO.getVigenciaInst(codInst));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			request.setAttribute(ParamsVO.SMS, e.getMessage());
		}
	}

	/**
	 * Metodo que realiza las validaciones y llama al procedimiento encargado de
	 * duplicar el plan de estudios.
	 * 
	 * @param request
	 * @throws ServletException
	 */
	private void duplicarPlanEstd(HttpServletRequest request)
			throws ServletException {
		// System.out.println(new Date() + " - duplicarPlanEstd");

		try {

			String vigenciaOrigen = request.getParameter("vigenciaOrigen");
			String vigenciaDestino = request.getParameter("vigenciaDestino");
			String advertencia = request.getParameter("advertencia");
			long codInst = Long.parseLong(usuVO.getInstId());
			String idUsuario = usuVO.getUsuarioId();

			// System.out.println(" - vigenciaOrigen " + vigenciaOrigen);
			// System.out.println(" - vigenciaDestino " + vigenciaDestino);
			// System.out.println(" - codInst" + codInst);
			// System.out.println(" - advertencia " + advertencia);

			request.setAttribute("vigenciaOrigen", vigenciaOrigen);
			request.setAttribute("vigenciaDestino", vigenciaDestino);

			if (vigenciaOrigen != null
					&& GenericValidator.isInt(vigenciaOrigen)
					&& vigenciaDestino != null
					&& GenericValidator.isInt(vigenciaDestino)) {

				/*
				 * 1. Validar que no existan evaluaciones para la vigencia
				 * destino
				 */

				if (cierreVigenciaDAO.isExisteEstEval(codInst,
						Long.parseLong(vigenciaDestino))) {
					request.setAttribute(
							ParamsVO.SMS,
							"No se puede copiar el plan de estudios, existen estudiantes evaluados para la vigencia "
									+ vigenciaDestino + " .");
					return;
				}

				/*
				 * 1. Validar que no existan estudiantes promocionados con
				 * estado SI o NO fue provomido para la vigencia destino
				 */

				if (cierreVigenciaDAO.isExistePromocion(codInst,
						Long.parseLong(vigenciaDestino))) {
					request.setAttribute(
							ParamsVO.SMS,
							"No se puede copiar el plan de estudios, existen estudiantes promocionados en la vigencia "
									+ vigenciaDestino + ".");
					return;
				}

				/*
				 * 1. Validar que no existan estudiantes promocionados con
				 * estado SI o NO fue provomido para la vigencia destino
				 */

				if (cierreVigenciaDAO.isExisteObserAsig(codInst,
						Long.parseLong(vigenciaDestino))) {
					request.setAttribute(
							ParamsVO.SMS,
							"No se puede copiar el plan de estudios, existen observaciones de asignatura para la vigencia "
									+ vigenciaDestino + ".");
					return;
				}

				/*
				 * 2. Validar que no existn registros de plan de estudios
				 */
				// System.out.println("av " + advertencia);
				if (advertencia != null
						&& advertencia.equals("-1")
						&& cierreVigenciaDAO.isExistePlanEstd(codInst,
								Long.parseLong(vigenciaDestino))) {

					request.setAttribute("msgAdvertencia",
							"Existe información de plan de Estudios para la vigencia "
									+ vigenciaDestino
									+ ". nDesea sobrescribir esta información?");

					request.setAttribute("advertencia", "1");
					return;
				} else {
					/*
					 * Llamar procedimiento
					 */

					request.setAttribute("advertencia", "-1");
					cierreVigenciaDAO.callDuplicarPlanEstd(codInst,
							Long.parseLong(vigenciaOrigen),
							Long.parseLong(vigenciaDestino));
					request.setAttribute(ParamsVO.SMS,
							"Proceso realizado satisfactoriamente.");
					Logger.print(usuVO.getUsuarioId(),
							"Realizo llamado al procedimiento de duplicar plan de estudios inst: "
									+ codInst + " vigencia Origen: "
									+ vigenciaOrigen + " vigencia Destiono: "
									+ vigenciaDestino, 7, 1, this.toString());
				}

			} else {
				request.setAttribute(ParamsVO.SMS,
						"Error realizando duplicacinn de plan de estudios.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			request.setAttribute(ParamsVO.SMS, e.getMessage());
		}
	}

	/**
	 * Cargar los listados de que muestran las vigencias
	 * 
	 * @param request
	 * @throws ServletException
	 */
	private void cargarFiltroDuplicarPlanEstd(HttpServletRequest request)
			throws ServletException {
		// System.out.println("cargarFiltroDuplicarPlanEstd");
		List listaVigencia = new ArrayList();
		long codInst = 0;
		int parVigencia = 0;
		try {

			codInst = Long.parseLong(usuVO.getInstId());
			parVigencia = cierreVigenciaDAO.getVigenciaInst(codInst);
			// System.out.println("vigencia actual de la institucinn "
			// + parVigencia);

			for (int i = 2007; i <= parVigencia; i++) {
				ItemVO itemVO = new ItemVO(i, i + "");
				listaVigencia.add(itemVO);
			}
			request.setAttribute("listaVigencia", listaVigencia);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			request.setAttribute(ParamsVO.SMS, e.getMessage());
		}

	}
}
