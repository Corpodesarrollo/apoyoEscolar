package articulacion.grupoArt.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.asignatura.dao.FiltroDAO;
import siges.common.vo.Params;
import siges.common.service.Service;
import articulacion.grupoArt.dao.FiltroGrupoDAO;
import siges.login.beans.Login;
import siges.dao.Cursor;

public class Filtro extends Service{
	
	private String FILTRO;
	private FiltroDAO filtroDao=new FiltroDAO(new Cursor());
	private FiltroGrupoDAO fGrupo=new FiltroGrupoDAO(new Cursor());
	
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[]=new String[2];
		FILTRO=config.getInitParameter("FILTRO");
		Login usuVO=(Login)session.getAttribute("login");
		request.setAttribute("listaEspecialidadVO",filtroDao.getListaEspecialidad(usuVO.getInstId()));
		request.setAttribute("listaJornadaVO",fGrupo.getJornada(usuVO.getInstId(),usuVO.getSedeId()));
		request.setAttribute("listaSedeVO",fGrupo.getSedes(usuVO.getInstId()));
		request.setAttribute("listaVigencia",filtroDao.getListaVigencia());
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FILTRO;
		return dispatcher;
	}

}
