package siges.gestionAdministrativa.repResultadosAca.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import siges.common.service.Service;
import siges.common.vo.ItemVO;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.gestionAdministrativa.repResultadosAca.dao.RepResultadosAcaDAO;
import siges.gestionAdministrativa.repResultadosAca.vo.ParamsVO;
import siges.login.beans.Login;

public class Ajax extends Service {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String FICHA;
	private String LISTA_AJAX;
	private String LISTA_ASIG = null;
	private Cursor cursor;
	private RepResultadosAcaDAO comDAO = null;
	private SimpleDateFormat formaFecha = new SimpleDateFormat(
			"yyyy/MMM/dd HH:mm:ss.SSS");
	Login usuVO = null;

	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String dispatcher[] = new String[2];
		cursor = new Cursor();
		LISTA_AJAX = config.getInitParameter("LISTA_AJAX");
		LISTA_ASIG = config.getInitParameter("LISTA_ASIG");
		comDAO = new RepResultadosAcaDAO(cursor);
		usuVO = (Login) request.getSession().getAttribute("login");
		try {
			HttpSession session = request.getSession();
			cursor = new Cursor();
			int CMD = getCmd(request, session, ParamsVO.CMD_AJAX);
			int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
			FICHA = LISTA_AJAX;
			switch (TIPO) {
			case ParamsVO.FICHA_REPORTES:
				switch (CMD) {
				case ParamsVO.CMD_AJAX_INST:
					//getPeriodos(request, usuVO);
					break;
				}
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		dispatcher[0] = String.valueOf(Params.INCLUDE);
		dispatcher[1] = FICHA;
		return dispatcher;
	}


	private void getPeriodos(HttpServletRequest request, Login usuVO)
			throws ServletException {
		request.getSession().setAttribute("listaPeriodo", 
				getListaPeriodo(6,"Final"));
		request.getSession().setAttribute("ajaxParam",
				String.valueOf(ParamsVO.CMD_AJAX_PER));
	}

	public List getListaPeriodo(long numPer, String nomPerDef) {
		List l = new ArrayList();
		ItemVO item = null;
		for (int i = 1; i <= numPer; i++) {
			item = new ItemVO(i, "" + i);
			l.add(item);
		}
		item = new ItemVO(7, nomPerDef);
		l.add(item);
		return l;
	}

}
