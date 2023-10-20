package articulacion.asigTutor.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.asigTutor.dao.AsigTutorDAO;
import articulacion.asigTutor.vo.AsigTutorVO;
import articulacion.asigTutor.vo.DatosVO;
import articulacion.asigTutor.vo.ParamsVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;

public class Lista extends Service {
	private String FICHA_LISTA;

	private Cursor cursor;

	private AsigTutorDAO asigTutorDAO;

	public String[] process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[] = new String[2];
		cursor = new Cursor();
		asigTutorDAO = new AsigTutorDAO(cursor);
		FICHA_LISTA = config.getInitParameter("FICHA_LISTA");
		int CMD = getCmd(request, session);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_ASIG_TUTOR);

		switch (CMD) {
		case ParamsVO.CMD_FILTRAR:
			buscar(request,session);
			break;
		case ParamsVO.CMD_MODIFICAR:
			guardar(request,session);
			buscar(request,session);
			break;
		}

		dispatcher[0] = String.valueOf(Params.INCLUDE);
		dispatcher[1] = FICHA_LISTA;

		return dispatcher;
	}
	
	private void buscar(HttpServletRequest request,HttpSession session) throws ServletException, IOException {
		DatosVO DatosVO = (DatosVO) session.getAttribute("FilAsigTutorVO");
		if (DatosVO != null) {
			request.setAttribute("listaEstudiantesVO", asigTutorDAO.getEstudiantes(DatosVO));
		}
	}

	private void guardar(HttpServletRequest request,HttpSession session) throws ServletException, IOException {
		AsigTutorVO asigTutorVO = (AsigTutorVO) session.getAttribute("asigTutorVO");
		if (asigTutorVO != null) {
			boolean nuevo = false;
			AsigTutorDAO asigTutorDAO = new AsigTutorDAO(cursor);
			for (int i = 0; i < asigTutorVO.getArtHisTutCodEst().length; i++) {
				if (asigTutorVO.getCt()[i] != asigTutorVO.getArtHisTutCodTutor())
					nuevo = true;
				if (asigTutorDAO.asignaTutor(asigTutorVO.getArtHisTutCodEst()[i], asigTutorVO.getArtHisTutCodTutor(), asigTutorVO.getArtHisTutSemestre(), asigTutorVO.getCheck()[i], nuevo))
					request.setAttribute(Params.SMS, "La información fue actualizada Satisfactoriamente");
				else
					request.setAttribute(Params.SMS, "La información no fue actualizada " + asigTutorDAO.getMensaje());
				nuevo = false;
			}
			session.removeAttribute("asigTutorVO");
		}
	}
}
