package articulacion.asigTutor.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.asigTutor.dao.AsigTutorDAO;
import siges.common.vo.Params;
import articulacion.asigTutor.vo.ParamsVO;
import siges.common.service.Service;
import siges.login.beans.Login;
import siges.dao.Cursor;

public class Filtro extends Service {

	private String FILTRO;

	private Cursor cursor;

	public String[] process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[] = new String[2];
		cursor = new Cursor();
		FILTRO = config.getInitParameter("FILTRO");
		int CMD = getCmd(request, session);
		int TIPO = getTipo(request, session, 0);
		Login usuVO = (Login) session.getAttribute("login");

		AsigTutorDAO asigTutorDAO = new AsigTutorDAO(cursor);
		request.setAttribute("listaEspecialidadVO", asigTutorDAO.getListaEspecialidad(usuVO.getInstId()));
		request.setAttribute("listaJornadaVO", asigTutorDAO.getJornada(usuVO.getInstId(), usuVO.getSedeId()));
		request.setAttribute("listaSedeVO", asigTutorDAO.getSedes(usuVO.getInstId()));

		dispatcher[0] = String.valueOf(Params.INCLUDE);
		dispatcher[1] = FILTRO;

		return dispatcher;
	}

}
