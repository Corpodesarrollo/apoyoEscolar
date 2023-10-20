package articulacion.asignatura.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.asignatura.dao.PreRequisitoDAO;
import articulacion.asignatura.vo.AsignaturaVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;


public class ListaPrereqIns extends Service{
	private String FICHA_LISTA;
	private PreRequisitoDAO preRequisitoDAO=new PreRequisitoDAO(new Cursor());
	
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[]=new String[2];
		FICHA_LISTA=config.getInitParameter("FICHA_LISTA");
		//int CMD=getCmd(request,session);
		//int TIPO=getTipo(request,session,0);
		AsignaturaVO asignaturaVo=(AsignaturaVO)session.getAttribute("asignaturaVO");
		request.setAttribute("listaPreRequisitoVO",preRequisitoDAO.getListaPreReqIns(asignaturaVo.getArtAsigCodigo()));
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FICHA_LISTA;
		return dispatcher;
	}
}
