package siges.plantilla.evaluacion;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.service.Service;
import siges.dao.Cursor;
import siges.login.beans.Login;
import siges.plantilla.beans.ParamsVO;
import siges.plantilla.dao.PlantillaDAO;
import siges.util.Properties;

/**
 * 22/01/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class Ajax extends Service {
	public String FICHA_LOGRO;
	public String FICHA_DESCRIPTOR;
	public String FICHA_RECUPERACION;
	private PlantillaDAO plantillaDAO = new PlantillaDAO(new Cursor());

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		FICHA_LOGRO = config.getInitParameter("FICHA_LOGRO");
		FICHA_DESCRIPTOR = config.getInitParameter("FICHA_DESCRIPTOR");
		FICHA_RECUPERACION = config.getInitParameter("FICHA_RECUPERACION");
	}

	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String FICHA = null;
		HttpSession session = request.getSession();
		String dispatcher[] = new String[2];
		int CMD = getCmd(request, session);
		int TIPO = getTipo(request, session, Properties.PLANTILLALOGROASIG);
		Login usuVO = (Login) session.getAttribute("login");
		// System.out.println("entra ajax: TIPO:"+TIPO+"//"+CMD);
		switch (TIPO) {
		case Properties.PLANTILLALOGROASIG:
			FICHA = FICHA_LOGRO;
			switch (CMD) {
			case ParamsVO.CMD_AJAX_DOCENTE:
				logroDocente(request, session, usuVO, CMD);
				break;
			}
			break;
		case Properties.PLANTILLAAREADESC:
			FICHA = FICHA_DESCRIPTOR;
			switch (CMD) {
			case ParamsVO.CMD_AJAX_DOCENTE:
				descriptorDocente(request, session, usuVO, CMD);
				break;
			}
			break;
		case Properties.PLANTILLARECUPERACION:
			FICHA = FICHA_RECUPERACION;
			switch (CMD) {
			case ParamsVO.CMD_AJAX_DOCENTE:
				logroDocente(request, session, usuVO, CMD);
				break;
			}
			break;
		}
		dispatcher[0] = String.valueOf(ParamsVO.INCLUDE);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	public void logroDocente(HttpServletRequest request, HttpSession session,
			Login usuVO, int cmd) throws ServletException {
		try {
			String params[] = request.getParameterValues("ajax");
			if (params == null || params.length < 5 || params[0] == null
					|| params[1] == null || params[2] == null
					|| params[3] == null || params[4] == null)
				return;
			long asig = Long.parseLong(params[4].substring(
					params[4].lastIndexOf("|") + 1, params[4].length()));
			request.setAttribute(
					"listaDocente",
					plantillaDAO.getListaDocenteAsignatura(
							Long.parseLong(params[0]),
							Integer.parseInt(params[1]),
							Integer.parseInt(params[2]),
							Integer.parseInt(params[3]), asig));
			request.setAttribute("ajaxParam", String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void descriptorDocente(HttpServletRequest request,
			HttpSession session, Login usuVO, int cmd) throws ServletException {
		try {
			String params[] = request.getParameterValues("ajax");
			if (params == null || params.length < 5 || params[0] == null
					|| params[1] == null || params[2] == null
					|| params[3] == null || params[4] == null)
				return;
			long area = Long.parseLong(params[4].substring(
					params[4].lastIndexOf("|") + 1, params[4].length()));
			request.setAttribute("listaDocente", plantillaDAO
					.getListaDocenteArea(Long.parseLong(params[0]),
							Integer.parseInt(params[1]),
							Integer.parseInt(params[2]),
							Integer.parseInt(params[3]), area));
			request.setAttribute("ajaxParam", String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
}
