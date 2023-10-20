package articulacion.asignatura.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.asignatura.dao.AsignaturaDAO;
import articulacion.asignatura.dao.FiltroDAO;
import articulacion.asignatura.dao.PruebaDAO;
import siges.common.vo.Params;
import siges.common.service.Service;
import articulacion.asignatura.vo.DatosVO;
import articulacion.asignatura.vo.ParamsVO;
import siges.dao.Cursor;
import articulacion.especialidad.dao.EspecialidadDAO;
import siges.login.beans.Login;


public class Filtro extends Service{
	
	private String FILTRO_PRUEBA;
	private String FILTRO_ASIGNATURA;
	private Cursor cursor;
	private AsignaturaDAO asignaturaDAO;
	private PruebaDAO pruebaDAO;
	
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		String FICHA;
		String dispatcher[]=new String[2];
		cursor=new Cursor();
		asignaturaDAO=new AsignaturaDAO(cursor);
		pruebaDAO=new PruebaDAO(cursor);
		FILTRO_ASIGNATURA=config.getInitParameter("FILTRO_ASIGNATURA");
		FILTRO_PRUEBA=config.getInitParameter("FILTRO_PRUEBA");
		
		Login usuVO=(Login)session.getAttribute("login");
		int CMD=getCmd(request,session);
		int TIPO=getTipo(request,session,ParamsVO.FICHA_ASIGNATURA);
		FICHA=FILTRO_ASIGNATURA;
		FiltroDAO filtroDao=new FiltroDAO(cursor);
		request.setAttribute("listaVigencia",asignaturaDAO.getListaVigencia());
		switch (TIPO){
			case ParamsVO.FICHA_ASIGNATURA:
				DatosVO datosVO=(DatosVO)session.getAttribute("datosVO");
				FICHA=FILTRO_ASIGNATURA;
				request.setAttribute("listaEspecialidadVO",filtroDao.getListaEspecialidad(usuVO.getInstId()));
				if(datosVO!=null && datosVO.getComponente()!=null){
					long inst=datosVO.getInst();
					long anoVig=datosVO.getAnoVigencia();
					int perVig=datosVO.getPerVigencia();
					request.setAttribute("ajaxArea",asignaturaDAO.getAjaxArea(inst,(int)anoVig,perVig,Integer.parseInt(datosVO.getComponente())));
				}
			break;
			case ParamsVO.FICHA_PRUEBA:
				DatosVO datosVOP=(DatosVO)session.getAttribute("datosVOP");
				FICHA=FILTRO_PRUEBA;
				request.setAttribute("listaEspecialidadVO",filtroDao.getListaEspecialidad(usuVO.getInstId()));
				if(datosVOP!=null && datosVOP.getComponente()!=null){
					long inst=datosVOP.getInst();
					long anoVig=datosVOP.getAnoVigencia();
					int perVig=datosVOP.getPerVigencia();
					request.setAttribute("ajaxArea",asignaturaDAO.getAjaxArea(inst,(int)anoVig,perVig,Integer.parseInt(datosVOP.getComponente())));
					request.setAttribute("listaAsignaturaPVO",pruebaDAO.ajaxAsignatura(inst,datosVOP.getComponente(),datosVOP.getEspecialidad(),datosVOP.getArea(),datosVOP.getPeriodo(),datosVOP.getComplementaria(),datosVOP.getAnoVigencia(),datosVOP.getPerVigencia()));
				}
			break;
			
		}
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FICHA;
		
		return dispatcher;
	}

}
