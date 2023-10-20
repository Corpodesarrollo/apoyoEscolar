/**
 * 
 */
package siges.indicadores.service;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.service.Service;
import siges.dao.Cursor;
import siges.indicadores.dao.IndicadoresDAO;
import siges.indicadores.vo.FiltroDescriptorVO;
import siges.indicadores.vo.FiltroLogroVO;
import siges.indicadores.vo.ParamsVO;
import siges.login.beans.Login;

/**
 * 22/01/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class Filtro extends Service {
	public String FICHA_LOGRO;
	public String FICHA_DESCRIPTOR;
	private IndicadoresDAO indicadoresDAO = new IndicadoresDAO(new Cursor());

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		FICHA_LOGRO = config.getInitParameter("FICHA_LOGRO");
		FICHA_DESCRIPTOR = config.getInitParameter("FICHA_DESCRIPTOR");
	}

	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String FICHA = null;
		HttpSession session = request.getSession();
		String dispatcher[] = new String[2];
		int CMD = getCmd(request, session);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		if (request.getParameter("tipo") == null
				|| ((String) request.getParameter("tipo")).equals("")) {
			session.removeAttribute("filtroDescriptorVO");
			session.removeAttribute("filtroLogroVO");
		}
		Login usuVO = (Login) session.getAttribute("login");
		FiltroLogroVO filtroLogro = (FiltroLogroVO) session
				.getAttribute("filtroLogroVO");
		FiltroDescriptorVO filtroDescriptor = (FiltroDescriptorVO) session
				.getAttribute("filtroDescriptorVO");
		switch (TIPO) {
		case ParamsVO.FICHA_LOGRO:
			FICHA = FICHA_LOGRO;
			session.removeAttribute("filtroDescriptorVO");
			logroNuevo(request, session, usuVO, filtroLogro);
			break;
		case ParamsVO.FICHA_DESCRIPTOR:
			FICHA = FICHA_DESCRIPTOR;
			session.removeAttribute("filtroLogroVO");
			descriptorNuevo(request, session, usuVO, filtroDescriptor);
			break;
		}
		dispatcher[0] = String.valueOf(ParamsVO.INCLUDE);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	public void logroNuevo(HttpServletRequest request, HttpSession session,
			Login usuVO, FiltroLogroVO filtro) throws ServletException {
		try {
			if (filtro == null) {
				filtro = new FiltroLogroVO();
				filtro.setFilInstitucion(Long.parseLong(usuVO.getInstId()));
				filtro.setFilUsuario(Long.parseLong(usuVO.getUsuarioId()));
				filtro.setFilVigencia((int) indicadoresDAO.getVigenciaInst(Long
						.parseLong(usuVO.getInstId())));
				filtro.setFilPlanEstudios(indicadoresDAO.getEstadoPlan(Long
						.parseLong(usuVO.getInstId())));
				filtro.setFilNivelPerfil(indicadoresDAO.getNivelPerfil(Integer
						.parseInt(usuVO.getPerfil())));
				session.setAttribute("filtroLogroVO", filtro);
			}
			request.setAttribute("listaMetodologia", indicadoresDAO
					.getListaMetodologia(filtro.getFilInstitucion()));
			if (filtro.getFilMetodologia() > 0) {
				request.setAttribute("listaGrado", indicadoresDAO
						.getListaGrado(filtro.getFilInstitucion(),
								filtro.getFilMetodologia()));
				if (filtro.getFilGrado() > -99) {
					request.setAttribute(
							"listaAsignatura",
							indicadoresDAO.getListaAsignatura(
									filtro.getFilInstitucion(),
									filtro.getFilMetodologia(),
									filtro.getFilVigencia(),
									filtro.getFilGrado()));
					if (filtro.getFilPlanEstudios() == 1) {
						request.setAttribute(
								"listaDocente",
								indicadoresDAO.getListaDocenteAsignatura(
										filtro.getFilInstitucion(),
										filtro.getFilMetodologia(),
										filtro.getFilVigencia(),
										filtro.getFilGrado(),
										filtro.getFilAsignatura()));
					}
				}
			}
		} catch (Exception e) {
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void descriptorNuevo(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroDescriptorVO filtro)
			throws ServletException {
		try {
			if (filtro == null) {
				filtro = new FiltroDescriptorVO();
				filtro.setFilInstitucion(Long.parseLong(usuVO.getInstId()));
				filtro.setFilUsuario(Long.parseLong(usuVO.getUsuarioId()));
				filtro.setFilVigencia((int) indicadoresDAO.getVigenciaInst(Long
						.parseLong(usuVO.getInstId())));
				filtro.setFilPlanEstudios(indicadoresDAO.getEstadoPlan(Long
						.parseLong(usuVO.getInstId())));
				filtro.setFilNivelPerfil(indicadoresDAO.getNivelPerfil(Integer
						.parseInt(usuVO.getPerfil())));
				session.setAttribute("filtroDescriptorVO", filtro);
			}
			request.setAttribute("listaMetodologia", indicadoresDAO
					.getListaMetodologia(filtro.getFilInstitucion()));
			if (filtro.getFilMetodologia() > 0) {
				request.setAttribute("listaGrado", indicadoresDAO
						.getListaGrado(filtro.getFilInstitucion(),
								filtro.getFilMetodologia()));
				if (filtro.getFilGrado() > -99) {
					request.setAttribute(
							"listaArea",
							indicadoresDAO.getListaArea(
									filtro.getFilInstitucion(),
									filtro.getFilMetodologia(),
									filtro.getFilVigencia(),
									filtro.getFilGrado()));
					if (filtro.getFilPlanEstudios() == 1) {
						request.setAttribute(
								"listaDocente",
								indicadoresDAO.getListaDocenteArea(
										filtro.getFilInstitucion(),
										filtro.getFilMetodologia(),
										filtro.getFilVigencia(),
										filtro.getFilGrado(),
										filtro.getFilArea()));
					}
				}
			}
		} catch (Exception e) {
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
}
