package articulacion.escValorativa.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import articulacion.escValorativa.dao.EscValorativaDAO;
import articulacion.escValorativa.vo.DatosVO;


public class Lista extends Service{
	
	private String FICHA_LISTA;
	private Cursor cursor;
	private EscValorativaDAO escValorativaDAO;
	
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[]=new String[2];
		cursor=new Cursor();
		escValorativaDAO=new EscValorativaDAO(cursor);
		FICHA_LISTA=config.getInitParameter("FICHA_LISTA");
		int CMD=getCmd(request,session);
		int TIPO=getTipo(request,session,0);
		EscValorativaDAO escValorativaDAO=new EscValorativaDAO(cursor);
		DatosVO datosVO=(DatosVO)session.getAttribute("filtroEscalasVO");
		if(datosVO!=null){
		//	System.out.println("llega al buscar lista");
			request.setAttribute("listaEscalasVO",escValorativaDAO.getListaEscala(datosVO.getInstitucion(),datosVO.getMetodologia(),datosVO.getAnVigencia(),datosVO.getPerVigencia()));
			request.setAttribute("guia",getRequest2(request,"guia","-1"));
		}
				
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FICHA_LISTA;
		return dispatcher;
	}

}
