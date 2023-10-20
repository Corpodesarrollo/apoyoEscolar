package articulacion.universidad.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import articulacion.universidad.dao.UniversidadDAO;


public class Lista extends Service{
	
	private String FICHA_LISTA;
	private Cursor cursor;
	private UniversidadDAO uniDAO;
	
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[]=new String[2];
		cursor=new Cursor();
		uniDAO=new UniversidadDAO(cursor);
		FICHA_LISTA=config.getInitParameter("FICHA_LISTA");
		int CMD=getCmd(request,session);
		int TIPO=getTipo(request,session,0);
		
		
		request.setAttribute("listaUniversidadVO",uniDAO.getAllListaUniversidad());
		request.setAttribute("guia",getRequest2(request,"guia","-1"));
		
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FICHA_LISTA;
		return dispatcher;
	}

}
