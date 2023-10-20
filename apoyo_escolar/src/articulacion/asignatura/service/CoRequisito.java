package articulacion.asignatura.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.asignatura.dao.CoRequisitoDAO;
import articulacion.asignatura.vo.AsignaturaVO;
import articulacion.asignatura.vo.DatosVO;
import articulacion.asignatura.vo.ParamsVO;
import articulacion.asignatura.vo.RequisitoVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;

public class CoRequisito extends Service {

	private Cursor cursor;
	private String FICHA_NUEVO;
	private String FICHA_LISTA;

	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String dispatcher[] = new String[2];
		HttpSession session = request.getSession();
		cursor = new Cursor();
		FICHA_NUEVO = config.getInitParameter("FICHA_NUEVO");
		FICHA_LISTA = config.getInitParameter("FICHA_LISTA");
		String FICHA = FICHA_NUEVO;
		int CMD = getCmd(request, session, Params.CMD_NUEVO);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		CoRequisitoDAO coRequisitoDAO = new CoRequisitoDAO(cursor);
		AsignaturaVO asignaturaVO = (AsignaturaVO) session
				.getAttribute("asignaturaVO");
		DatosVO datosVO = (DatosVO) session.getAttribute("datosVO");
		// System.out.println(request.getParameter("cmd")+" Entra por aca: "+CMD);
		switch (CMD) {
		case Params.CMD_GUARDAR:
			FICHA = FICHA_LISTA;
			RequisitoVO nuevos = (RequisitoVO) session
					.getAttribute("requisitoVO");
			if (coRequisitoDAO.insertar(nuevos)) {
				request.setAttribute(Params.SMS,
						"Corequisitos actualizados satisfactoriamente");
				request.setAttribute("listaCoRequisitos", coRequisitoDAO
						.getListaCoReqIns(asignaturaVO.getArtAsigCodigo()));
			} else {
				request.setAttribute(Params.SMS,
						"Error interno actualizando corequisitos");
			}
			break;
		case Params.CMD_NUEVO:
			if (datosVO != null) {
				request.setAttribute("CoRequisitos",
						coRequisitoDAO.getCoRequisitos(datosVO, asignaturaVO));
			}
			break;
		}
		dispatcher[0] = String.valueOf(Params.FORWARD);
		dispatcher[1] = FICHA;

		return dispatcher;
	}
}
