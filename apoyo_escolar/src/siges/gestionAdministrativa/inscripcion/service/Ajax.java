package siges.gestionAdministrativa.inscripcion.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.inscripcion.dao.InscripcionDAO;

 

import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor; 
import siges.gestionAdministrativa.agenda.vo.ParamsVO;
import siges.login.beans.Login;

public class Ajax extends Service{
	
	private static final long serialVersionUID = 1L;
	private String FICHA;
	Login usuVO = null;
	InscripcionDAO insDAO = new InscripcionDAO(new Cursor());
	
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String dispatcher[]=new String[2];
		HttpSession session = request.getSession();
		usuVO = (Login)request.getSession().getAttribute("login");
		int CMD=getCmd(request,session,ParamsVO.CMD_AJAX);
		FICHA = config.getInitParameter("FICHA_AJAX"); 
		
		request.setAttribute("listaAsignaturas",insDAO.getListaAsignaturasFromEspecialidad(usuVO.getInstId(), Integer.toString(CMD)));
		
		dispatcher[0] = String.valueOf(Params.INCLUDE);
		dispatcher[1] = FICHA;
		return dispatcher;
	}
	
}
