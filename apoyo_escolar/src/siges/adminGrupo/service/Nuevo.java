package siges.adminGrupo.service;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.adminGrupo.dao.AdminGrupoDAO;
import siges.adminGrupo.vo.GrupoVO;
import siges.adminGrupo.vo.ParamsVO;
import siges.common.service.Service;
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

	public String FICHA_GRUPOS;

	private AdminGrupoDAO adminGruposDAO = new AdminGrupoDAO(new Cursor());

	public void init(ServletConfig config_) throws ServletException {
		super.init(config_);
		this.config = config_;
		FICHA_GRUPOS = config.getInitParameter("FICHA_GRUPO");

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
		int TIPO = getTipo(request, session, ParamsVO.FICHA_GRUPOS);
		Login usuVO = (Login) session.getAttribute("login");

		// System.out.println("CMD ADMIN_GRUPO" + CMD + " TIPO " + TIPO);

		GrupoVO grupoVO = (GrupoVO) request.getSession()
				.getAttribute("grupoVO");
		switch (TIPO) {

		// GRUPOS
		case ParamsVO.FICHA_GRUPOS:
			FICHA = FICHA_GRUPOS;
			switch (CMD) {
			case ParamsVO.CMD_DEFAULT:
				session.removeAttribute("filtroGrupos");
			case ParamsVO.CMD_NUEVO:
				nuevoGrupo(request, session, usuVO);
				break;
			case ParamsVO.CMD_GUARDAR:
				// System.out.println("GRUPOS: GUARDAR");
				guardarGrupo(request, session, usuVO, grupoVO);
				session.removeAttribute("grupoVO");
				break;
			}
			FICHA = FICHA_GRUPOS;
			break;

		}
		// System.out.println("ADMIN GRUPO: FICHA: " + FICHA);
		dispatcher[0] = String.valueOf(Params.INCLUDE);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	// GRUPOS

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @throws ServletException
	 */
	public void nuevoGrupo(HttpServletRequest request, HttpSession session,
			Login usuVO) throws ServletException {
		try {
			session.removeAttribute("grupoVO");
			GrupoVO d = new GrupoVO();
			session.setAttribute("grupoVO", d);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Error interno: " + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param competenciasVO
	 * @throws ServletException
	 */
	public void guardarGrupo(HttpServletRequest request, HttpSession session,
			Login usuVO, GrupoVO d) throws ServletException {
		try {

			if (d != null) {
				if (d.getInsertar() != 1) {
					// Actualizar
					adminGruposDAO.actualizarGrupo(d);
					request.setAttribute(ParamsVO.SMS,
							"La información fue actualizada satisfactoriamente.");
					// Logger.print(usuVO.getUsuarioId(),"Actualizo información de competencias. "+competenciasVO.getComcodinst()+". "+competenciasVO.getComcodigo(),6,1,this.toString());

				} else {
					/*
					 * // if(!adminGruposDAO.validarGrupo(d)){
					 * adminGruposDAO.guardarGrupo(d);
					 * request.setAttribute(ParamsVO.SMS,
					 * "La información fue registrada satisfactoriamente.");
					 * }else{ request.setAttribute(ParamsVO.SMS,
					 * "La información ya se encuentra registrada en el sistema."
					 * ); }
					 */
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, e.getMessage());
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

}
