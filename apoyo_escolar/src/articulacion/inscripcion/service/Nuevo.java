package articulacion.inscripcion.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.inscripcion.vo.FiltroInscripcionVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import articulacion.inscripcion.dao.InscripcionDAO;
import siges.login.beans.Login;

public class Nuevo extends Service {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	InscripcionDAO insDAO = new InscripcionDAO(new Cursor());
	private String FICHA_NUEVO;

	public String[] process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dispatcher[] = new String[2];
		HttpSession session = request.getSession();
		Login usuVO = (Login) session.getAttribute("login");
		
		FICHA_NUEVO = config.getInitParameter("FICHA_NUEVO");
		int CMD = getCmd(request, session, Params.CMD_NUEVO);
		FiltroInscripcionVO filtroInscripcion = (FiltroInscripcionVO) session.getAttribute("filtroInscripcion2VO");
		switch (CMD) {
			case Params.CMD_GUARDAR:
			try {
				String []inscripcion=request.getParameterValues("x");
				if(insDAO.insertar(filtroInscripcion,inscripcion)){
					request.setAttribute(Params.SMS, "La información fue ingresada satisfactoriamente");
				}else{
					request.setAttribute(Params.SMS, "No se puede registrar la inscripción: " + insDAO.getMensaje());	
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute(Params.SMS, "La información no fue ingresada: Error interno");
			}
		}
		session.setAttribute("listaEspecialidad",insDAO.getListaespecialidades(usuVO.getUsuarioId()));
		dispatcher[0] = String.valueOf(Params.FORWARD);
		dispatcher[1] = FICHA_NUEVO;
		return dispatcher;
	}
}
