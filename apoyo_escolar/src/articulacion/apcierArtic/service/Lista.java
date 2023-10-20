package articulacion.apcierArtic.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.apcierArtic.dao.AperCierArticDAO;
import articulacion.apcierArtic.vo.*;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;



public class Lista extends Service{
	private String FICHA_LISTA;
	private Cursor cursor;
	private AperCierArticDAO aperCierArticDAO;
	
	
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[]=new String[2];
		cursor=new Cursor();
		aperCierArticDAO=new AperCierArticDAO(cursor);
		FICHA_LISTA=config.getInitParameter("FICHA_LISTA");
		int CMD=getCmd(request,session);
		int TIPO=getTipo(request,session,ParamsVO.FICHA_EVALUACION);
		DatosVO datosVO=(DatosVO)session.getAttribute("filApCierreVO");
		switch(CMD){		
			case ParamsVO.CMD_FILTRAR:
				if(datosVO!=null){
					request.setAttribute("listaPruebasVO",aperCierArticDAO.getListaPrueba(datosVO));
				}
			break;
			case ParamsVO.CMD_ACTUALIZAR:
				PruebaVO pruebaVO=(PruebaVO)session.getAttribute("pruebaVO");
				if(pruebaVO!=null){
					if(aperCierArticDAO.actualizaEstado(datosVO,pruebaVO))
						request.setAttribute(Params.SMS,"La información fue Actualizada Satisfactoriamente");
					else
						request.setAttribute(Params.SMS,"La información no fue Actualizada "+aperCierArticDAO.getMensaje());
				}
					session.removeAttribute("evaluacionVO");
			break;	
		}
		
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FICHA_LISTA;
		
		return dispatcher;
	}

}
