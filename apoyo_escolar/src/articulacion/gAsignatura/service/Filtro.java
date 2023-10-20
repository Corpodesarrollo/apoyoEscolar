package articulacion.gAsignatura.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.asignatura.dao.AsignaturaDAO;
import articulacion.asignatura.dao.FiltroDAO;
import siges.common.vo.Params;
import siges.common.service.Service;
import articulacion.asignatura.vo.ListaAreaVO;
import siges.dao.Cursor;
import siges.login.beans.Login;


public class Filtro extends Service{
	
	private String FILTRO;
	private Cursor cursor;
	private AsignaturaDAO asignaturaDAO;
	
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[]=new String[2];
		cursor=new Cursor();
		asignaturaDAO=new AsignaturaDAO(cursor);
		FILTRO=config.getInitParameter("FILTRO");
		
		Login usuVO=(Login)session.getAttribute("login");
		int CMD=getCmd(request,session);
		int TIPO=getTipo(request,session,0);
		FiltroDAO filtroDao=new FiltroDAO(cursor);
		request.setAttribute("listaAreaVO",filtroDao.getListaAreas(usuVO.getInstId(),usuVO.getMetodologiaId(),2));
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FILTRO;
		
		return dispatcher;
	}

}
