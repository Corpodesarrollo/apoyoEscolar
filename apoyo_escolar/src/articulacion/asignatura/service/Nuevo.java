package articulacion.asignatura.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.asignatura.dao.AsignaturaDAO;
import articulacion.asignatura.dao.CoRequisitoDAO;
import articulacion.asignatura.dao.ComplementariaDAO;
import articulacion.asignatura.dao.PreRequisitoDAO;
import articulacion.asignatura.vo.AsignaturaVO;
import articulacion.asignatura.vo.DatosVO;
import articulacion.asignatura.vo.ParamsVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import articulacion.asignatura.dao.PruebaDAO;
import articulacion.asignatura.vo.DatosPruebaVO;
import articulacion.asignatura.vo.PruebaVO;


public class Nuevo extends Service {
	
	private Cursor c;
	
	private String FICHA_NUEVO_PRUEBA=null;
	private String FICHA_NUEVO;
	private String FICHA_CO;
	private String FICHA_PRE;
	
	private String FICHA_COMP;
	
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String dispatcher[]=new String[2];
		String FICHA;
		HttpSession session = request.getSession();
		c = new Cursor();
		FICHA_NUEVO=config.getInitParameter("FICHA_NUEVO");
		FICHA_NUEVO_PRUEBA=config.getInitParameter("FICHA_NUEVO_PRUEBA");
		FICHA_CO=config.getInitParameter("FICHA_NUEVO_CO");
		FICHA_PRE=config.getInitParameter("FICHA_NUEVO_PRE");
		FICHA_COMP=config.getInitParameter("FICHA_NUEVO_COM");
		int CMD=getCmd(request,session,Params.CMD_NUEVO);
		int TIPO=getTipo(request,session,ParamsVO.FICHA_DEFAULT);
		FICHA=FICHA_NUEVO;
		switch (TIPO){
		case ParamsVO.FICHA_ASIGNATURA:

			FICHA=FICHA_NUEVO;
			AsignaturaVO asignaturaVO=(AsignaturaVO)session.getAttribute("asignaturaVO");
			AsignaturaVO asignaturaVO2=(AsignaturaVO)session.getAttribute("asignaturaVO2");
			DatosVO datosVO=(DatosVO)session.getAttribute("datosVO");
			int area=0;
			if(datosVO!=null)
				area=datosVO.getArea();
			AsignaturaDAO asignaturaDAO=new AsignaturaDAO(c);
			CoRequisitoDAO coRequisitoDAO=new CoRequisitoDAO(c);
			ComplementariaDAO complementariaDAO=new ComplementariaDAO(c);
			PreRequisitoDAO preRequisitoDAO=new PreRequisitoDAO(c);
			String codAsignatura=request.getParameter("id");
			String codRequisito=request.getParameter("idReq");
			String codComplementaria=request.getParameter("idCom");
			request.setAttribute("listaNombres",asignaturaDAO.getListaNombresAsignaturas(area));
			switch (CMD){			
				case Params.CMD_GUARDAR:
					
							//actualizacion******************************************************
						if(asignaturaVO.getFormaEstado().equals("1")){
							if(asignaturaVO.getArtAsigCodigo()==asignaturaVO2.getArtAsigCodigo()){
								if(asignaturaDAO.actualizar(asignaturaVO,asignaturaVO2)){
									request.setAttribute(Params.SMS,"La información fue actualizada satisfactoriamente");
									session.removeAttribute("asignaturaVO");
									session.removeAttribute("asignaturaVO2");
								}else{
									request.setAttribute(Params.SMS,"La información no fue actualizada "+asignaturaDAO.getMensaje());
									session.setAttribute("asignaturaVO",asignaturaVO);
									session.removeAttribute("asignaturaVO2");
								}
							}
						}
						//********insercion****
						else{
							if(!asignaturaDAO.checkCodigo(datosVO, asignaturaVO)){
								if(asignaturaDAO.insertar(asignaturaVO)){
									request.setAttribute(Params.SMS,"La información fue ingresada satisfactoriamente");
									session.removeAttribute("asignaturaVO");
								}else{
									request.setAttribute(Params.SMS,"La información no fue ingresada "+asignaturaDAO.getMensaje());
								}
							}else{
								request.setAttribute(Params.SMS,"La información no fue ingresada, el codigo de la asignatura ya se encuentra registrado");
							}
						}	
				break;
				case Params.CMD_EDITAR:
					asignaturaVO=asignaturaDAO.asignar(codAsignatura);
					if(asignaturaVO!=null){
						session.setAttribute("asignaturaVO",asignaturaVO);
						session.setAttribute("asignaturaVO2",asignaturaVO.clone());
						request.setAttribute("guia",codAsignatura);
					}else{
						request.setAttribute(Params.SMS,"No se logro obtener los datos del objeto seleccionado");
					}
				break;
				case Params.CMD_ELIMINAR:
					//*************************************************************
					if(/*como hago para ver si el usuario tiene permisos?*/true){
						if(asignaturaDAO.eliminar(codAsignatura)){
							request.setAttribute(Params.SMS,"La información fue eliminada satisfactoriamente");
							session.removeAttribute("asignaturaVO");
							session.removeAttribute("asignaturaVO2");
						}else{
							request.setAttribute(Params.SMS,"La información no fue eliminada "+asignaturaDAO.getMensaje());
						}
					}else{
						request.setAttribute(Params.SMS,"No tiene los permisos necesarios para realizar esta accinn");
					}
				break;
				case Params.CMD_NUEVO:
					session.removeAttribute("asignaturaVO");
					session.removeAttribute("asignaturaVO2");
					request.removeAttribute("listaPreRequisitoVO");
					request.removeAttribute("listaCoRequisitoVO");
				break;
				case ParamsVO.CMD_ELIM_COREQ:
					if(coRequisitoDAO.eliminar(asignaturaVO.getArtAsigCodigo(),codRequisito))
						request.setAttribute(Params.SMS,"El Co-Requisito fue eliminado");
					else
						request.setAttribute(Params.SMS,"El Co-Requisito no pudo ser eliminado");
					FICHA=FICHA_CO;
				break;
				case ParamsVO.CMD_ELIM_PREREQ:
					if(preRequisitoDAO.eliminar(asignaturaVO.getArtAsigCodigo(),codRequisito))
						request.setAttribute(Params.SMS,"El Pre-Requisito fue eliminado");
					else
						request.setAttribute(Params.SMS,"El Pre-Requisito no pudo ser eliminado");
					FICHA=FICHA_PRE;
				break;
				case ParamsVO.CMD_ELIM_COMPLE:
					if(complementariaDAO.eliminar(asignaturaVO.getArtAsigCodigo(),codComplementaria))
						request.setAttribute(Params.SMS,"La materia complementaria fue eliminada");
					else
						request.setAttribute(Params.SMS,"La materia complementaria no pudo ser eliminada");
					FICHA=FICHA_COMP;
				break;
			}
		
		break;
		
		case ParamsVO.FICHA_PRUEBA:
			FICHA=FICHA_NUEVO_PRUEBA;
			PruebaDAO pruebaDAO=new PruebaDAO(c);
			PruebaVO pruebaVO=(PruebaVO)session.getAttribute("pruebaVO");
			PruebaVO pruebaVO2=(PruebaVO)session.getAttribute("pruebaVO2");
			String codPru=request.getParameter("cod");
			String codAsig=request.getParameter("asig");
			String codSub=request.getParameter("sub");
			DatosVO datosPruebaVO=(DatosVO)session.getAttribute("datosVOP");
			
			switch(CMD){
				case Params.CMD_GUARDAR:
				//actualizacion******************************************************
				if(pruebaVO.getFormaEstado().equals("1")){
					if(pruebaVO.getArtPruCodigo()==pruebaVO.getArtPruCodigo()){
						if(pruebaDAO.actualizar(pruebaVO,pruebaVO2)){
							request.setAttribute(Params.SMS,"La información fue actualizada satisfactoriamente");
							session.removeAttribute("pruebaVO");
							session.removeAttribute("pruebaVO2");
						}else{
							request.setAttribute(Params.SMS,"La información no fue actualizada "+pruebaDAO.getMensaje());
							session.setAttribute("pruebaVO",pruebaVO);
							session.removeAttribute("pruebaVO2");
						}
					}
				}
				//********insercion****
				else{
					if(pruebaDAO.insertar(pruebaVO)){
						request.setAttribute(Params.SMS,"La información fue ingresada satisfactoriamente");
						session.removeAttribute("pruebaVO");
					}else{
						request.setAttribute(Params.SMS,"La información no fue ingresada "+pruebaDAO.getMensaje());
					}
				}
			break;
			case Params.CMD_EDITAR:
								
				pruebaVO=pruebaDAO.asignar(codAsig,codPru,codSub);
				if(pruebaVO!=null){
					session.setAttribute("pruebaVO",pruebaVO);
					session.setAttribute("pruebaVO2",pruebaVO.clone());
					request.setAttribute("guia",codAsig);
				}else{
					request.setAttribute(Params.SMS,"No se logro obtener los datos del objeto seleccionado");
				}
			break;
			case Params.CMD_ELIMINAR:
				//*************************************************************
				if(/*como hago para ver si el usuario tiene permisos?*/true){
					if(pruebaDAO.eliminar(codAsig,codPru,codSub)){
						request.setAttribute(Params.SMS,"La información fue eliminada satisfactoriamente");
						session.removeAttribute("pruebaVO");
						session.removeAttribute("pruebaVO2");
					}else{
						request.setAttribute(Params.SMS,"La información no fue eliminada. "+pruebaDAO.getMensaje());
					}
				}else{
					request.setAttribute(Params.SMS,"No tiene los permisos necesarios para realizar esta accinn");
				}
			break;
			case Params.CMD_NUEVO:
				session.removeAttribute("pruebaVO");
				session.removeAttribute("pruebaVO2");
			break;
			}
			if(datosPruebaVO!=null){
				//System.out.println("Recalcula lista de pruebas");
				long s=datosPruebaVO.getAsignatura();
				request.setAttribute("listaPruebasPrinVO",pruebaDAO.getListaPruebaPrinc(s));
			}
			break;
		}
		dispatcher[0]=String.valueOf(Params.FORWARD);
		dispatcher[1]=FICHA;
		
		
		return dispatcher;
	}
}
