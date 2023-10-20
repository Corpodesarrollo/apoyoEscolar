package articulacion.asignatura.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.asignatura.dao.ComplementariaDAO;
import articulacion.asignatura.vo.AsignaturaVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;


public class ListaCompIns extends Service{
	
	private String FICHA_LISTA;
	private Cursor cursor;
	private ComplementariaDAO complementariaDAO;
	
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[]=new String[2];
		cursor=new Cursor();
		complementariaDAO=new ComplementariaDAO(cursor);
		FICHA_LISTA=config.getInitParameter("FICHA_LISTA");
		int CMD=getCmd(request,session);
		int TIPO=getTipo(request,session,0);
		
		AsignaturaVO asignaturaVo=(AsignaturaVO)session.getAttribute("asignaturaVO");
		int asigCodigo=asignaturaVo.getArtAsigCodigo();
		request.setAttribute("Complementarias",complementariaDAO.getListaComplementariaIns(asigCodigo));
		
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FICHA_LISTA;
		return dispatcher;
	}

}
