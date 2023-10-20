package articulacion.asignatura.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.asignatura.dao.AsignaturaDAO;
import articulacion.asignatura.vo.DatosVO;
import articulacion.asignatura.vo.ParamsVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import articulacion.asignatura.dao.PruebaDAO;
import articulacion.asignatura.vo.DatosPruebaVO;


public class Lista extends Service{
	private String FICHA_LISTA;
	private String FICHA_LISTA_ASIGNATURA;
	private String FICHA_LISTA_PRUEBA;
	private Cursor cursor;
	private AsignaturaDAO asignaturaDAO;
	private PruebaDAO pruebaDAO;
	
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[]=new String[2];
		cursor=new Cursor();
		asignaturaDAO=new AsignaturaDAO(cursor);
		FICHA_LISTA_ASIGNATURA=config.getInitParameter("FICHA_LISTA_ASIGNATURA");
		FICHA_LISTA_PRUEBA=config.getInitParameter("FICHA_LISTA_PRUEBA");
		int CMD=getCmd(request,session);
		int TIPO=getTipo(request,session,ParamsVO.FICHA_ASIGNATURA);
		FICHA_LISTA=FICHA_LISTA_ASIGNATURA;
		switch(TIPO){
			case ParamsVO.FICHA_ASIGNATURA:
				FICHA_LISTA=FICHA_LISTA_ASIGNATURA;
				DatosVO datosVO=(DatosVO)session.getAttribute("datosVO");
				if(datosVO!=null){
					String componente=datosVO.getComponente();
					int especialidad=datosVO.getEspecialidad();
					int area=datosVO.getArea();
					long inst=datosVO.getInst();
					//int tipoPeriodo=datosVO.getTipoPeriodo();
					int periodo=datosVO.getPeriodo();
					int complementaria=datosVO.getComplementaria();
					long anoVig=datosVO.getAnoVigencia();
					int perVig=datosVO.getPerVigencia();
					session.setAttribute("listaAsignaturaVO",asignaturaDAO.getListaAsignatura(inst,componente,especialidad,area,periodo,complementaria,anoVig,perVig));
					request.setAttribute("guia",getRequest2(request,"guia","-1"));
				}
			break;
			case ParamsVO.FICHA_PRUEBA:
				FICHA_LISTA=FICHA_LISTA_PRUEBA;
				//System.out.println("Ficha lista es: "+FICHA_LISTA);
				pruebaDAO=new PruebaDAO(cursor);
				DatosVO datosPruebaVO=(DatosVO)session.getAttribute("datosVOP");
				if(datosPruebaVO!=null){
					long codAsignatura=datosPruebaVO.getAsignatura();
					request.setAttribute("listaPruebaVO",pruebaDAO.getListaPrueba(codAsignatura));
					request.setAttribute("guia",getRequest2(request,"guia","-1"));
				}
			break;	
		}
		
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FICHA_LISTA;
		
		return dispatcher;
	}

}
