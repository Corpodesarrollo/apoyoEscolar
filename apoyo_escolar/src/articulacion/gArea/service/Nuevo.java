package articulacion.gArea.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import articulacion.gArea.dao.AreaDAO;
import articulacion.gArea.vo.GAreaVO;
import articulacion.gArea.vo.ParamsVO;
import siges.login.beans.Login;

public class Nuevo extends Service {

	private Cursor c;
	private String FICHA_NUEVO;
	private AreaDAO areaDAO;

	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String dispatcher[] = new String[2];
		HttpSession session = request.getSession();

		Login usuVO = (Login) session.getAttribute("login");
		// **********
		c = new Cursor();
		areaDAO = new AreaDAO(c);

		GAreaVO areaVO = (GAreaVO) session.getAttribute("gAreaVO");
		GAreaVO areaVO2 = (GAreaVO) session.getAttribute("gAreaVO2");
		AreaDAO areaDAO = new AreaDAO(c);

		FICHA_NUEVO = config.getInitParameter("FICHA_NUEVO");
		int CMD = getCmd(request, session, Params.CMD_NUEVO);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		String codArea = request.getParameter("id");

		switch (CMD) {
		case Params.CMD_GUARDAR:
			// Guardar*******************************
			// System.out.println("entra al guardar ");
			if (areaVO.getFormaEstado().equals("1")) {
				// Actualizar****************************
				if (areaDAO.actualizar(areaVO, areaVO2)) {
					request.setAttribute(Params.SMS,
							"La información fue actualizada satisfactoriamente");
					session.removeAttribute("gAreaVO");
					session.removeAttribute("gAreaVO2");
				} else {
					request.setAttribute(
							Params.SMS,
							"No se pudo actualizar el Area: "
									+ areaDAO.getMensaje());
					session.setAttribute("gAreaVO", areaVO);
					session.removeAttribute("gAreaVO");
					session.removeAttribute("gAreaVO2");
				}
			} else {
				if (areaDAO.insertar(areaVO)) {
					request.setAttribute(Params.SMS,
							"La información fue ingresada satisfactoriamente");
					session.removeAttribute("gAreaVO");
				} else {
					request.setAttribute(
							Params.SMS,
							"La información no fue ingresada "
									+ areaDAO.getMensaje());
				}
			}

			break;

		case Params.CMD_EDITAR:
			areaVO = areaDAO.asignar(codArea);

			if (areaVO != null) {
				session.setAttribute("gAreaVO", areaVO);
				session.setAttribute("gAreaVO2", areaVO.clone());
				request.setAttribute("guia", codArea);

			} else {
				request.setAttribute(Params.SMS,
						"No se logro obtener los datos del objeto seleccionado");
			}
			break;
		case Params.CMD_ELIMINAR:
			// *************************************************************
			if (/* como hago para ver si el usuario tiene permisos? */true) {
				if (areaDAO.eliminar(codArea)) {
					request.setAttribute(Params.SMS,
							"La información fue eliminada satisfactoriamente");
					session.removeAttribute("gAreaVO");
					session.removeAttribute("gAreaVO2");
				} else {
					request.setAttribute(
							Params.SMS,
							"La información no fue eliminada "
									+ areaDAO.getMensaje());
				}
			} else {
				request.setAttribute(Params.SMS,
						"No tiene los permisos necesarios para realizar esta accinn");
			}
			break;
		case Params.CMD_NUEVO:
			session.removeAttribute("gAreaVO");
			session.removeAttribute("gAreaVO2");
			break;
		}
		dispatcher[0] = String.valueOf(Params.FORWARD);
		dispatcher[1] = FICHA_NUEVO;
		return dispatcher;
	}
}
