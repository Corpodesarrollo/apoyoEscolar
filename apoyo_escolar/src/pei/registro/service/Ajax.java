package pei.registro.service;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pei.registro.dao.RegistroDAO;
import pei.registro.vo.ParamsVO;
import siges.common.service.Service;
import siges.dao.Cursor;

public class Ajax extends Service {
	private static final long serialVersionUID = 6150013753285143307L;

	private RegistroDAO registroDAO = new RegistroDAO(new Cursor());
	public String FICHA_REGISTRO;

	public void init(ServletConfig config) throws ServletException {
		this.config = config;
		context = config.getServletContext();
		FICHA_REGISTRO = config.getInitParameter("FICHA_REGISTRO");
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
		// System.out.println("params: "+TIPO+"_"+CMD);
		switch (TIPO) {
		case ParamsVO.FICHA_IDENTIFICACION:
			FICHA = FICHA_REGISTRO;
			switch (CMD) {
			case ParamsVO.CMD_AJAX:
				identificacionInstitucion(request, session);
				break;
			}
			break;
		}
		dispatcher[0] = String.valueOf(ParamsVO.INCLUDE);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	private void identificacionInstitucion(HttpServletRequest request,
			HttpSession session) throws ServletException {
		try {
			String params[] = request.getParameterValues("ajax");
			if (params == null || params.length < 1 || params[0] == null)
				return;
			request.setAttribute("listaInstitucion", registroDAO
					.getListaInstitucion(Integer.parseInt(params[0])));
			request.setAttribute("ajax", String.valueOf(ParamsVO.CMD_AJAX));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
}
