package pei.parametro.service;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pei.parametro.dao.ParametroDAO;
import pei.parametro.vo.FiltroParametroVO;
import pei.parametro.vo.ParametroVO;
import pei.parametro.vo.ParamsVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.login.beans.Login;
import siges.util.Logger;

public class Nuevo extends Service {
	private static final long serialVersionUID = 615001375328514876L;

	private ParametroDAO parametroDAO = new ParametroDAO(new Cursor());
	public String FICHA_PARAMETRO;

	public void init(ServletConfig config) throws ServletException {
		this.config = config;
		context = config.getServletContext();
		FICHA_PARAMETRO = config.getInitParameter("FICHA_PARAMETRO");
	}

	/*
	 * (non-Javadoc)
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
		int CMD = getCmd(request, session, ParamsVO.CMD_NUEVO);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		Login usuVO = (Login) session.getAttribute("login");
		// System.out.println("params: "+TIPO+"_"+CMD);
		switch (TIPO) {
		case ParamsVO.FICHA_PARAMETRO:
			FICHA = FICHA_PARAMETRO;
			ParametroVO parametro = (ParametroVO) session
					.getAttribute(ParamsVO.BEAN_PARAMETRO);
			FiltroParametroVO filtro = (FiltroParametroVO) session
					.getAttribute(ParamsVO.BEAN_FILTRO_PARAMETRO);
			switch (CMD) {
			case ParamsVO.CMD_BUSCAR:
				parametroBuscar(request, session, usuVO, filtro, parametro);
				break;
			case ParamsVO.CMD_NUEVO:
				parametroNuevo(request, session, usuVO, filtro, parametro);
				parametroBuscar(request, session, usuVO, filtro, parametro);
				break;
			case ParamsVO.CMD_EDITAR:
				parametroEditar(request, session, usuVO, filtro, parametro);
				parametroBuscar(request, session, usuVO, filtro, parametro);
				break;
			case ParamsVO.CMD_GUARDAR:
				parametroGuardar(request, session, usuVO, filtro, parametro);
				parametroBuscar(request, session, usuVO, filtro, parametro);
				break;
			case ParamsVO.CMD_ELIMINAR:
				parametroEliminar(request, session, usuVO, filtro, parametro);
				parametroBuscar(request, session, usuVO, filtro, parametro);
				break;
			}
			parametroInit(request, session, usuVO, filtro, parametro);
			break;
		}
		dispatcher[0] = String.valueOf(Params.INCLUDE);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param desarrolloCurricular
	 * @param otroProyecto
	 * @throws ServletException
	 */
	private void parametroBuscar(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroParametroVO filtro,
			ParametroVO parametro) throws ServletException {
		try {
			if (filtro != null && filtro.getFilTipo() > 0)
				request.setAttribute("listaParametro",
						parametroDAO.getListaParametro(filtro));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param desarrolloCurricular
	 * @param parametro
	 * @throws ServletException
	 */
	private void parametroEditar(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroParametroVO filtro,
			ParametroVO parametro) throws ServletException {
		try {
			if (filtro == null) {
				request.setAttribute(ParamsVO.SMS,
						"Error editando parnmetro: No hay parnmetro seleccionado");
				return;
			}
			parametro = new ParametroVO();
			parametro.setParTipo(filtro.getFilTipo());
			parametro.setParCodigo(filtro.getFilCodigo());
			session.setAttribute(ParamsVO.BEAN_PARAMETRO,
					parametroDAO.getParametro(parametro));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Error interno: " + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param desarrolloCurricular
	 * @param parametro
	 * @throws ServletException
	 */
	private void parametroEliminar(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroParametroVO filtro,
			ParametroVO parametro) throws ServletException {
		try {
			if (filtro == null) {
				request.setAttribute(ParamsVO.SMS,
						"Error eliminando parnmetro: No hay parnmetro seleccionado");
				return;
			}
			parametro = new ParametroVO();
			parametro.setParTipo(filtro.getFilTipo());
			parametro.setParCodigo(filtro.getFilCodigo());
			parametro = parametroDAO.eliminarParametro(parametro);
			Logger.print(usuVO.getUsuarioId(), "PEI: Eliminar Parametro. "
					+ parametro.getParTipo() + " " + parametro.getParCodigo(),
					7, 1, this.toString());
			session.removeAttribute(ParamsVO.BEAN_PARAMETRO);
			request.setAttribute(ParamsVO.SMS,
					"La información ha sigo eliminada satisfactoriamente");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error eliminando parnmetro: "
					+ e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param desarrolloCurricular
	 * @param parametro
	 * @throws ServletException
	 */
	private void parametroGuardar(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroParametroVO filtro,
			ParametroVO parametro) throws ServletException {
		try {
			if (parametro == null)
				return;
			if (!parametro.isEdicion()) {
				parametro = parametroDAO.ingresarParametro(parametro);
				Logger.print(usuVO.getUsuarioId(),
						"PEI: Registro parametro. " + parametro.getParTipo()
								+ " " + parametro.getParCodigo(), 7, 1,
						this.toString());
			} else {
				parametro = parametroDAO.actualizarParametro(parametro);
				Logger.print(
						usuVO.getUsuarioId(),
						"PEI: Actualizacion parametro. "
								+ parametro.getParTipo() + " "
								+ parametro.getParCodigo(), 7, 1,
						this.toString());
			}
			session.removeAttribute(ParamsVO.BEAN_PARAMETRO);
			request.setAttribute(ParamsVO.SMS,
					"La información ha sigo ingresada satisfactoriamente");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error ingresando parnmetro: "
					+ e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param desarrolloCurricular
	 * @param parametro
	 * @throws ServletException
	 */
	private void parametroNuevo(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroParametroVO filtro,
			ParametroVO parametro) throws ServletException {
		session.removeAttribute(ParamsVO.BEAN_PARAMETRO);
	}

	private void parametroInit(HttpServletRequest request, HttpSession session,
			Login usuVO, FiltroParametroVO filtro, ParametroVO parametro)
			throws ServletException {
		try {
			request.setAttribute("listaTipo", parametroDAO.getListaTipo());
			request.setAttribute("listaEstado", parametroDAO.getListaEstado());
		} catch (Exception e) {
			throw new ServletException(e.getMessage());
		}
	}
}
