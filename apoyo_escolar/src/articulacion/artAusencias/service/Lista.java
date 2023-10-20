package articulacion.artAusencias.service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.artAusencias.dao.AusenciasDAO;
import articulacion.artAusencias.vo.DatosVO;
import articulacion.artAusencias.vo.AusenciasVO;
import articulacion.artAusencias.vo.EstDiasVO;
import articulacion.artAusencias.vo.ListaMotivosVO;
import articulacion.artAusencias.vo.ParamsVO;
import articulacion.artAusencias.vo.TempMotivoVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;



public class Lista extends Service{
	private String FICHA_LISTA;
	private Cursor cursor;
	private AusenciasDAO ausenciasDAO;
	
	
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[]=new String[2];
		cursor=new Cursor();
		ausenciasDAO=new AusenciasDAO(cursor);
		FICHA_LISTA=config.getInitParameter("FICHA_LISTA");
		int CMD=getCmd(request,session,ParamsVO.CMD_FILTRAR);
//		int TIPO=getTipo(request,session,ParamsVO.FICHA_EVALUACION);
		DatosVO datosVO=(DatosVO)session.getAttribute("FilAusenciasVO");
		//System.out.println("estamos en Lista "+CMD);
		switch(CMD){		
			case ParamsVO.CMD_FILTRAR:
				session.removeAttribute("listaMotivosVO");//********lo borro por si al usuario le dn por cambiar el filtro antes de guardar
				if(datosVO!=null){
					try {
						datosVO=ausenciasDAO.paramsHorario(datosVO);
						List estu=ausenciasDAO.getEstudiantes(datosVO);
						session.setAttribute("FilAusenciasVO",datosVO);
						session.setAttribute("listaEstAusenciaVO",estu);
						session.setAttribute("listaMotivosVO",ausenciasDAO.inicio(estu,datosVO));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			break;
			case ParamsVO.CMD_INSERTAR:
				EstDiasVO estDiasVO=(EstDiasVO)session.getAttribute("estDiasVO");
				ListaMotivosVO listaMotivosVO=(ListaMotivosVO)session.getAttribute("listaMotivosVO");
			
				ausenciasDAO.borrarAusencias(datosVO);
				
				if(datosVO!=null && estDiasVO!=null && listaMotivosVO!=null){
					if(ausenciasDAO.adminInsercinn(datosVO,estDiasVO,listaMotivosVO.getTempMotivoVO())){
						request.setAttribute(Params.SMS,"La información fue actualizada satisfactoriamente");
					}else{
						request.setAttribute(Params.SMS,"La información no fue actualizada"+ausenciasDAO.getMensaje());
					}
				}else{
					if(ausenciasDAO.adminInsercinn(datosVO,estDiasVO)){
						request.setAttribute(Params.SMS,"La información fue actualizada satisfactoriamente");
					}else{
						request.setAttribute(Params.SMS,"La información no fue actualizada"+ausenciasDAO.getMensaje());
					}
				}
				session.setAttribute("listaEstAusenciaVO",ausenciasDAO.getEstudiantes(datosVO));
				session.removeAttribute("estDiasVO");
		//		session.removeAttribute("listaMotivosVO");
			break;
		}
		try{
			if(datosVO!=null){
				request.setAttribute("listaDias",ausenciasDAO.getDias(datosVO));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FICHA_LISTA;
		
		return dispatcher;
	}
}
