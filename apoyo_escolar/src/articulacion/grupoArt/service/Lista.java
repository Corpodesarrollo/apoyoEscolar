package articulacion.grupoArt.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.grupoArt.dao.GrupoArtDAO;
import articulacion.grupoArt.vo.DatosVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;


public class Lista extends Service{
	
	private String FICHA_LISTA;
	private Cursor cursor;
	private GrupoArtDAO grupoDAO;
	
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[]=new String[2];
		cursor=new Cursor();
		grupoDAO=new GrupoArtDAO(cursor);
		FICHA_LISTA=config.getInitParameter("FICHA_LISTA");
		DatosVO datosVO=(DatosVO)session.getAttribute("filtroGrupoVO");
		if(datosVO!=null){
			request.setAttribute("listaGrupoVO",grupoDAO.getListaGrupo(datosVO.getInstitucion(),
																		datosVO.getSede(),datosVO.getJornada(),
																		datosVO.getAnVigencia(),datosVO.getPerVigencia(),
																		datosVO.getPeriodo(),datosVO.getComponente(),
																		datosVO.getEspecialidad()));
			request.setAttribute("guia",getRequest2(request,"guia","-1"));
		}
		
		
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FICHA_LISTA;
		return dispatcher;
	}

}
