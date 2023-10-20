package articulacion.evaluacionArt.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.vo.Params;
import articulacion.evaluacionArt.dao.EvaluacionDAO;
import articulacion.evaluacionArt.vo.ParamsVO;
import siges.common.service.Service;
import siges.login.beans.Login;
import siges.dao.Cursor;



public class Filtro extends Service{
	
	private String FILTRO;
	private EvaluacionDAO evaluacionDAO=new EvaluacionDAO(new Cursor());
	
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[]=new String[2];
		FILTRO=config.getInitParameter("FILTRO");
		Login usuVO=(Login)session.getAttribute("login");
		request.setAttribute("listaEspecialidadVO",evaluacionDAO.getListaEspecialidad(usuVO.getInstId()));
		request.setAttribute("listaJornadaVO",evaluacionDAO.getJornada(usuVO.getInstId(),usuVO.getSedeId()));
		request.setAttribute("listaVigencia",evaluacionDAO.getListaVigencia());
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FILTRO;
		return dispatcher;
	}

}
