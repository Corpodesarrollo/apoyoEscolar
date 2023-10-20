package articulacion.especialidad.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.especialidad.dao.EspecialidadDAO;
import siges.login.beans.Login;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;


public class Lista extends Service{
	
	private String FICHA_LISTA;
	private Cursor cursor;
	private EspecialidadDAO espDAO;
	
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[]=new String[2];
		Login usuVO=(Login)session.getAttribute("login");
		cursor=new Cursor();
		espDAO=new EspecialidadDAO(cursor);
		FICHA_LISTA=config.getInitParameter("FICHA_LISTA");
		int CMD=getCmd(request,session);
		int TIPO=getTipo(request,session,0);
		
		String colegio=usuVO.getInstId();
		request.setAttribute("listaEspecialidadVO",espDAO.getAllListaEspecialidad(colegio));
		
		request.setAttribute("guia",getRequest2(request,"guia","-1"));
		
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FICHA_LISTA;
		
		return dispatcher;
		
	}

}
