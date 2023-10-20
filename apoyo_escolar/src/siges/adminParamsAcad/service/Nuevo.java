package siges.adminParamsAcad.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.adminParamsAcad.dao.AdminParametroAcadDAO;
import siges.adminParamsAcad.vo.DimensionVO;
import siges.adminParamsAcad.vo.NivelEvaluacionVO;
import siges.adminParamsAcad.vo.ParamsVO;
import siges.common.service.Service;
import siges.common.vo.ItemVO;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.login.beans.Login;
import siges.util.Logger;

/**
 * 27/01/2010
 * 
 * @author Latined
 * @version 1.2
 */
public class Nuevo extends Service {
	public String FICHA_NIVEL_EVAL;
	public String FICHA_DIMENSION;

	private AdminParametroAcadDAO adminParametroAcadDAO = new AdminParametroAcadDAO(
			new Cursor());

	public void init(ServletConfig config_) throws ServletException {
		super.init(config_);
		this.config = config_;
		FICHA_NIVEL_EVAL = config.getInitParameter("FICHA_NIVEL_EVAL");
		FICHA_DIMENSION = config.getInitParameter("FICHA_DIMENSION");
	}

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
		// System.out.println(" parametros");
		String FICHA = null;
		HttpSession session = request.getSession();
		String dispatcher[] = new String[2];

		int CMD = getCmd(request, session, ParamsVO.CMD_DEFAULT);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_NIVEL_EVAL);
		Login usuVO = (Login) session.getAttribute("login");

		// System.out.println("academicos  CMD " + CMD + " TIPO " + TIPO);

		switch (TIPO) {

		case ParamsVO.FICHA_NIVEL_EVAL:
			FICHA = FICHA_NIVEL_EVAL;
			NivelEvaluacionVO nivelEvaluacionVO = (NivelEvaluacionVO) session
					.getAttribute("nivelEvaluacionVO");
			switch (CMD) {
			case ParamsVO.CMD_DEFAULT:
			case ParamsVO.CMD_NUEVO:
				nuevoNivelEval(request, session, usuVO, nivelEvaluacionVO);
				break;

			case ParamsVO.CMD_GUARDAR:
				guardarNivelEval(request, session, usuVO, nivelEvaluacionVO);
				nuevoNivelEval(request, session, usuVO, nivelEvaluacionVO);
				break;
			}
			FICHA = FICHA_NIVEL_EVAL;
			break;
		// CAMBIO DIMENSIONES
		case ParamsVO.FICHA_DIMENSION:
			// System.out.println("PARAACADEMICOS: ENTRO POR DIMENSION");
			FICHA = FICHA_DIMENSION;
			DimensionVO dimensionVO = (DimensionVO) session
					.getAttribute("dimensionVO");
			switch (CMD) {
			case ParamsVO.CMD_DEFAULT:
			case ParamsVO.CMD_NUEVO:
				nuevoDimension(request, session, usuVO, dimensionVO);
				break;
			case ParamsVO.CMD_EDITAR:
				editarDimension(request, session, usuVO);
				break;
			case ParamsVO.CMD_ELIMINAR:
				eliminarDimension(request, session, usuVO);
				nuevoDimension(request, session, usuVO, dimensionVO);
				break;
			case ParamsVO.CMD_GUARDAR:
				guardarDimension(request, session, usuVO, dimensionVO);
				nuevoDimension(request, session, usuVO, dimensionVO);
				break;

			}
			listaDimension(request, session, usuVO, dimensionVO);
			FICHA = FICHA_DIMENSION;
			break;

		}
		// System.out.println("FICHA " + FICHA);
		dispatcher[0] = String.valueOf(Params.INCLUDE);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	// NIVEL EVALUACION
	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param nivelEvaluacionVO
	 * @throws ServletException
	 */
	public void nuevoNivelEval(HttpServletRequest request, HttpSession session,
			Login usuVO, NivelEvaluacionVO nivelEvaluacionVO)
			throws ServletException {
		try {
			// System.out.println("NUEVO ");
			session.removeAttribute("listanivelEvaluacionVO");
			List listanivelEvaluacionVO = adminParametroAcadDAO
					.getNivelEvaluacion();
			request.setAttribute("listanivelEvaluacionVO",
					listanivelEvaluacionVO);
			List listaEstdos = new ArrayList();
			listaEstdos.add(new ItemVO(0, ("Inactivo").toUpperCase()));
			listaEstdos.add(new ItemVO(1, ("Activo").toUpperCase()));
			request.setAttribute("listaEstados", listaEstdos);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param nivelEvaluacionVO
	 * @throws ServletException
	 */
	public void guardarNivelEval(HttpServletRequest request,
			HttpSession session, Login usuVO,
			NivelEvaluacionVO nivelEvaluacionVO) throws ServletException {
		try {

			String[] listaEstados_ = request
					.getParameterValues("listaEstados_");
			List listaNivelEval = new ArrayList();
			if (listaEstados_ != null) {

				for (int i = 0; i < listaEstados_.length - 1; i++) {
					//
					NivelEvaluacionVO n = new NivelEvaluacionVO();
					long estado = Long.valueOf(
							listaEstados_[i].substring(0,
									listaEstados_[i].lastIndexOf("|")))
							.longValue();
					long codigo = Long.valueOf(
							listaEstados_[i].substring(
									listaEstados_[i].lastIndexOf("|") + 1,
									listaEstados_[i].length())).longValue();
					n.setG_nivevacodigo(codigo);
					n.setG_nivevaestado(estado);
					listaNivelEval.add(n);
				}

				adminParametroAcadDAO.actualizarNivelEvaluacion(listaNivelEval);
				request.setAttribute(ParamsVO.SMS,
						"La información fue registrada satisfactoriamente.");
				Logger.print(usuVO.getUsuarioId(),
						"Actualizo información de nivel de evaluacion. "
								+ usuVO.getInstId(), 6, 1, this.toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error: " + e.getMessage());
		}
	}

	// DIMENSIONES
	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param nivelEvaluacionVO
	 * @throws ServletException
	 */
	public void nuevoDimension(HttpServletRequest request, HttpSession session,
			Login usuVO, DimensionVO dimensionVO) throws ServletException {
		try {
			// System.out.println("NUEVO ");
			session.removeAttribute("dimensionVO");
			DimensionVO d = new DimensionVO();
			d.setCodigo(adminParametroAcadDAO.getCodigoNuevoDimension());
			d.setInsertar(1);
			session.setAttribute("dimensionVO", d);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("DIMENSION NUEVO:Errror interno: "
					+ e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param nivelEvaluacionVO
	 * @throws ServletException
	 */
	public void listaDimension(HttpServletRequest request, HttpSession session,
			Login usuVO, DimensionVO dimensionVO) throws ServletException {
		try {
			// System.out.println("LISTA ");
			session.removeAttribute("listaDimensiones");
			List listaDimensiones = adminParametroAcadDAO.getDimensiones();
			request.setAttribute("listaDimensiones", listaDimensiones);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("DIMENSION NUEVO:Errror interno: "
					+ e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param nivelEvaluacionVO
	 * @throws ServletException
	 */
	public void guardarDimension(HttpServletRequest request,
			HttpSession session, Login usuVO, DimensionVO dimensionVO)
			throws ServletException {
		try {
			// System.out.println("GUARDAR");
			if (dimensionVO != null) {
				if (dimensionVO.getInsertar() == 1) {
					DimensionVO d = adminParametroAcadDAO
							.editarDimension(dimensionVO.getCodigo());
					if (d == null) {
						// System.out.println("****INSERTAR");
						if (adminParametroAcadDAO
								.insertarDimension(dimensionVO))
							request.setAttribute(ParamsVO.SMS,
									"La información fue registrada satisfactoriamente.");
						else
							request.setAttribute(ParamsVO.SMS,
									"Error al tratar de insertar la información");
					} else {
						request.setAttribute(ParamsVO.SMS,
								"La información ya se encuentra registrada.");
					}
				} else {
					// System.out.println("****ACTUALIZAR");
					if (adminParametroAcadDAO.actualizarDimension(dimensionVO))
						request.setAttribute(ParamsVO.SMS,
								"La información fue actualizada satisfactoriamente.");
					else
						request.setAttribute(ParamsVO.SMS,
								"Error al tratar de actualizar la información");
				}
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
	 * @param nivelEvaluacionVO
	 * @throws ServletException
	 */
	public void editarDimension(HttpServletRequest request,
			HttpSession session, Login usuVO) throws ServletException {
		try {
			// System.out.println("****EDITAR");
			String id = request.getParameter("id");
			long idDimension = 0;
			if (id != null && id.length() > 0) {
				idDimension = Long.parseLong(id);
			}
			DimensionVO d = adminParametroAcadDAO.editarDimension(idDimension);
			if (d != null) {
				session.setAttribute("dimensionVO", d);
				// request.setAttribute(ParamsVO.SMS,
				// "La información fue registrada satisfactoriamente.");
			} else {
				request.setAttribute(ParamsVO.SMS,
						"No fue posible cargar la información para ser editada.");
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
	 * @param nivelEvaluacionVO
	 * @throws ServletException
	 */
	public void eliminarDimension(HttpServletRequest request,
			HttpSession session, Login usuVO) throws ServletException {
		try {
			// System.out.println("****ELIMINAR");
			String id = request.getParameter("id");
			long idDimension = 0;
			if (id != null && id.length() > 0) {
				idDimension = Long.parseLong(id);
			}
			// DimensionVO
			// d=adminParametroAcadDAO.editarDimension(dimensionVO.getCodigo());
			if (!adminParametroAcadDAO.validarDimension(idDimension)) {
				if (adminParametroAcadDAO.eliminarDimension(idDimension)) {
					// session.setAttribute("dimensionVO",d);
					request.setAttribute(ParamsVO.SMS,
							"La información fue eliminada satisfactoriamente.");
				} else {
					request.setAttribute(ParamsVO.SMS,
							"No fue posible eliminar la información.");
				}
			} else
				request.setAttribute(ParamsVO.SMS,
						"No es posible elimnar el registro debido a datos asociados.");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error: " + e.getMessage());
		}
	}

}