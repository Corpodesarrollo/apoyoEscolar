package articulacion.evaluacionArt.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.evaluacionArt.dao.EvaluacionDAO;
import articulacion.evaluacionArt.vo.DatosVO;
import articulacion.evaluacionArt.vo.EvaluacionVO;
import articulacion.evaluacionArt.vo.LimitesVO;
import articulacion.evaluacionArt.vo.ParamsVO;
import siges.exceptions.InternalErrorException;
import siges.login.beans.Login;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;



public class Lista extends Service{
	private String FICHA_LISTA;
	private Cursor cursor;
	private EvaluacionDAO evaluacionDAO;
	
	
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[]=new String[2];
		cursor=new Cursor();
		evaluacionDAO=new EvaluacionDAO(cursor);
		FICHA_LISTA=config.getInitParameter("FICHA_LISTA");
		int CMD=getCmd(request,session);
		int TIPO=getTipo(request,session,ParamsVO.FICHA_EVALUACION);
		DatosVO datosVO=(DatosVO)session.getAttribute("FilEvaluacionVO");
		request.setAttribute("listaEscalasVO",evaluacionDAO.getListaRangos(datosVO));
		switch(CMD){		
			case ParamsVO.CMD_FILTRAR:
				if(datosVO!=null){
					session.setAttribute("listaEstudiantesVO",evaluacionDAO.getEstudiantes(datosVO));
					request.setAttribute("rangosVO",evaluacionDAO.getListaRangos(datosVO));
					request.setAttribute("subPruebasVO",evaluacionDAO.getSubPruebas(datosVO));
					try {
						request.setAttribute("limites",evaluacionDAO.getLimites());
					} catch (SQLException e) {
						e.printStackTrace();
					} 
		//			System.out.println("valor de cerrada=---->"+evaluacionDAO.isCerrada(datosVO));
					if(evaluacionDAO.isCerrada(datosVO)){
						request.setAttribute("cerrada","true");
						request.setAttribute(Params.SMS,"Esta prueba actualmente se encuentra Cerrada");
					}
					else{
						request.setAttribute("cerrada","false");
					//	request.setAttribute(Params.SMS,"Esta prueba no esta cerrada");
					}
				}
			break;
			case ParamsVO.CMD_INSERTAR:
				EvaluacionVO evaluacionVO=(EvaluacionVO)session.getAttribute("evaluacionVO");
				if(evaluacionVO!=null){
					EvaluacionDAO evaluacionDAO=new EvaluacionDAO(cursor);
					if(evaluacionDAO.insertar(evaluacionVO, datosVO)){
						request.setAttribute(Params.SMS,"La evaluacinn fue actualizada correctamente");
					}else{
						request.setAttribute(Params.SMS,"La información no fue actualizada");
					}
				}
				session.removeAttribute("evaluacionVO");
				session.removeAttribute("listaEstudiantesVO");
				break;
			}
		
		
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FICHA_LISTA;
		
		return dispatcher;
	}
}
