package articulacion.inscripcion.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.inscripcion.dao.InscripcionDAO;
import siges.common.vo.Params;
import siges.common.service.Service;
import siges.dao.Cursor;
import siges.login.beans.Login;

public class Filtro extends Service {
	private String FILTRO_INSCRIPCION;
	InscripcionDAO filtroDao = new InscripcionDAO(new Cursor());

	public String[] process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[] = new String[2];
		FILTRO_INSCRIPCION = config.getInitParameter("FILTRO_INSCRIPCION");
		Login usuVO = (Login) session.getAttribute("login");
		session.setAttribute("listaJornadaVO", filtroDao.getListaJornada(Long.parseLong(usuVO.getInstId()), Long.parseLong(usuVO.getSedeId())));
		dispatcher[0] = String.valueOf(Params.INCLUDE);
		dispatcher[1] = FILTRO_INSCRIPCION;
		return dispatcher;
	}
}
