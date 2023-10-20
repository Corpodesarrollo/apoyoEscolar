package siges.plantilla.bateria;

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
	private PlantillaDAO plantillaDAO = new PlantillaDAO(new Cursor());

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
		int TIPO = getTipo(request, session, Properties.TIPOBATERIALOGRO);
		Login usuVO = (Login) session.getAttribute("login");
		System.out.println("entra ajax: TIPO:" + TIPO + "//" + CMD);
		switch (TIPO) {
		case Properties.TIPOBATERIALOGRO:
			FICHA = FICHA_LOGRO;
			switch (CMD) {
			case ParamsVO.CMD_AJAX_DOCENTE:
				logroDocente(request, session, usuVO, CMD);
				break;
			}
			break;
		case Properties.TIPOBATERIADESCRIPTOR:
			FICHA = FICHA_DESCRIPTOR;
			switch (CMD) {
			case ParamsVO.CMD_AJAX_DOCENTE:
				descriptorDocente(request, session, usuVO, CMD);
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
			String[] val = params[4].replace('|', ':').split(":");
			if (val != null && val.length == 3) {
				long asig = Long.parseLong(val[2]);
				request.setAttribute(
						"listaDocente",
						plantillaDAO.getListaDocenteAsignatura(
								Long.parseLong(params[0]),
								Integer.parseInt(params[1]),
								Integer.parseInt(params[2]),
								Integer.parseInt(params[3]), asig));
				session.setAttribute(
						"sessionDocente",
						plantillaDAO.getListaDocenteAsignatura(
								Long.parseLong(params[0]),
								Integer.parseInt(params[1]),
								Integer.parseInt(params[2]),
								Integer.parseInt(params[3]), asig));
				request.setAttribute("ajaxParam", String.valueOf(cmd));
			}
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
			String[] val = params[4].replace('|', ':').split(":");
			System.out.println("valor de area" + params[4]);
			if (val != null && val.length == 2) {
				long asig = Long.parseLong(val[1]);
				request.setAttribute(
						"listaDocente",
						plantillaDAO.getListaDocenteArea(
								Long.parseLong(params[0]),
								Integer.parseInt(params[1]),
								Integer.parseInt(params[2]),
								Integer.parseInt(params[3]), asig));
				request.setAttribute("ajaxParam", String.valueOf(cmd));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
}
