package articulacion.area.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.area.dao.AreaDAO;
import articulacion.area.vo.AreaVO;
import articulacion.area.vo.ParamsVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.login.beans.Login;

public class Nuevo  extends Service{

	private String FICHA_NUEVO;
	private String FICHA_AJAX;
	private AreaDAO areaDAO;
	private Cursor cursor;
	
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[]=new String[2];
		String inst=null;
		String metod=null;
		String cod=null;
		String anho=null;
		String per=null;
		cursor=new Cursor();
		areaDAO=new AreaDAO(cursor);
		FICHA_NUEVO=config.getInitParameter("FICHA_NUEVO");
		FICHA_AJAX=config.getInitParameter("FICHA_AJAX");
		Login usuVO=(Login)session.getAttribute("login");
		AreaVO areaVO=(AreaVO)session.getAttribute("areaVO");
		AreaVO area2VO=(AreaVO)session.getAttribute("area2VO");
		int CMD=getCmd(request,session);
		int TIPO=getTipo(request,session,ParamsVO.FICHA_DEFAULT);
		initForm(request);
		switch(CMD){
		case Params.CMD_GUARDAR:
			if(areaVO.getFormaEstado().equals("1")){
				//actualizar
				if(areaDAO.actualizar(areaVO,area2VO)){
					request.setAttribute(Params.SMS,"La información fue actualizada satisfactoriamente");
					session.removeAttribute("areaVO");
					session.removeAttribute("area2VO");
				}else{
					request.setAttribute(Params.SMS,"La información no fue actualizada: "+areaDAO.getMensaje());
					session.setAttribute("areaVO",area2VO.clone());
				}
			}else{
				//guardar
				areaVO.setAreInstitucion(Long.parseLong(usuVO.getInstId()));
				areaVO.setAreMetodologia(Integer.parseInt(usuVO.getMetodologiaId()));
//				System.out.println("valores: "+areaVO.getAreInstitucion()+"/"+areaVO.getAreMetodologia());
				if(areaDAO.insertar(areaVO)){
					request.setAttribute(Params.SMS,"La información fue ingresada satisfactoriamente");
					session.removeAttribute("areaVO");
				}else{
					request.setAttribute(Params.SMS,"La información no fue ingresada: "+areaDAO.getMensaje());
				}				
			}
		break;
		case Params.CMD_EDITAR:
			inst=getRequest(request,"id");
			metod=getRequest(request,"id2");
			cod=getRequest(request,"id3");
//			System.out.println(inst+"::"+metod+"::"+cod);
			areaVO=new AreaVO();
			areaVO.setAreInstitucion(Long.parseLong(inst));
			areaVO.setAreMetodologia(Integer.parseInt(metod));
			areaVO.setAreCodigo(Long.parseLong(cod));
			areaVO.setAreAnhoVigencia(Integer.parseInt(request.getParameter("id4")));
			areaVO.setArePerVigencia(Integer.parseInt(request.getParameter("id5")));
			areaVO=areaDAO.getArea(areaVO);
			if(areaVO!=null){
				session.setAttribute("areaVO",areaVO);
				session.setAttribute("area2VO",areaVO.clone());
				request.setAttribute("guia",inst);
				request.setAttribute("guia2",metod);
				request.setAttribute("guia3",cod);
				request.setAttribute("guia4",request.getParameter("id4"));
				request.setAttribute("guia5",request.getParameter("id5"));
			}else{
				request.setAttribute(Params.SMS,"No se logro obtener los datos ");
			}
		break;
		case Params.CMD_ELIMINAR:
			inst=getRequest(request,"id");
			metod=getRequest(request,"id2");
			cod=getRequest(request,"id3");
//			System.out.println("-*-*-"+inst+"::"+metod+"::"+cod);
			areaVO=new AreaVO();
			areaVO.setAreInstitucion(Long.parseLong(inst));
			areaVO.setAreMetodologia(Integer.parseInt(metod));
			areaVO.setAreCodigo(Long.parseLong(cod));
			areaVO.setAreAnhoVigencia(Integer.parseInt(request.getParameter("id4")));
			areaVO.setArePerVigencia(Integer.parseInt(request.getParameter("id5")));
			if(areaDAO.eliminar(areaVO)){
				request.setAttribute(Params.SMS,"La información fue eliminada satisfactoriamente");
				session.removeAttribute("areaVO");
				session.removeAttribute("area2VO");
			}else{
				request.setAttribute(Params.SMS,"La información no fue eliminada: "+areaDAO.getMensaje());
			}			
		break;
		case Params.CMD_NUEVO:
			session.removeAttribute("areaVO");
			session.removeAttribute("area2VO");
		break;
		case Params.CMD_AJAX:
			String id=request.getParameter("id");
			request.setAttribute("ajaxArea",areaDAO.getAjaxArea(id));
			FICHA_NUEVO=FICHA_AJAX;
		break;
		}
		dispatcher[0]=String.valueOf(Params.FORWARD);
		dispatcher[1]=FICHA_NUEVO;
		return dispatcher;
	}
	
	private void initForm(HttpServletRequest request){
		request.setAttribute("listaGArea",areaDAO.getAllGArea());
	}
}
