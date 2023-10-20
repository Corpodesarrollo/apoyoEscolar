package articulacion.apcierArtic.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.vo.Params;
import siges.common.service.Service;
import siges.login.beans.Login;
import siges.dao.Cursor;
import articulacion.apcierArtic.dao.AperCierArticDAO;
import articulacion.apcierArtic.vo.ParamsVO;


public class Filtro extends Service{
	
	private String FILTRO;
	private AperCierArticDAO aperCierArticDAO=new AperCierArticDAO(new Cursor());
	
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[]=new String[2];
		FILTRO=config.getInitParameter("FILTRO");
		Login usuVO=(Login)session.getAttribute("login");
		request.setAttribute("listaEspecialidadVO",aperCierArticDAO.getListaEspecialidad(usuVO.getInstId()));
		request.setAttribute("listaVigencia",aperCierArticDAO.getListaVigencia());	
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FILTRO;
		return dispatcher;
	}

}
