package articulacion.universidad.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.universidad.vo.ParamsVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.login.beans.Login;
import siges.perfil.dao.PerfilDAO;
import articulacion.universidad.dao.UniversidadDAO;
import articulacion.universidad.vo.UniversidadVO;



public class Nuevo extends Service {
	//private UniversidadDAO universidad;
	private Cursor c;
	private String FICHA_NUEVO;
	private UniversidadDAO uniDAO;
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String dispatcher[]=new String[2];
		HttpSession session = request.getSession();
		//objeto usuario con datos del mismo
		Login usuVO=(Login)session.getAttribute("login");
		//**********
		c = new Cursor();
		uniDAO=new UniversidadDAO(c);
		
		UniversidadVO universidadVO=(UniversidadVO)session.getAttribute("universidadVO");
		UniversidadVO universidadVO2=(UniversidadVO)session.getAttribute("universidadVO2");
		UniversidadDAO universidadDAO=new UniversidadDAO(c);
		//c=new Cursor();
		//universidad=new UniversidadDAO(c);
		
		FICHA_NUEVO=config.getInitParameter("FICHA_NUEVO");
		int CMD=getCmd(request,session,Params.CMD_NUEVO);
		int TIPO=getTipo(request,session,ParamsVO.FICHA_DEFAULT);
		String codUniversidad=request.getParameter("id");
				
		switch (CMD){			
			case Params.CMD_GUARDAR:
				//Guardar*******************************
				if(universidadVO.getFormaEstado().equals("1")){
					//Actualizar****************************
				//	if(universidadVO.getGuniCodigo()==universidadVO2.getGuniCodigo()){
						
					if(universidadDAO.actualizar(universidadVO,universidadVO2)){
							request.setAttribute(Params.SMS,"La información fue actualizada satisfactoriamente");
							session.removeAttribute("universidadVO");
							session.removeAttribute("universidadVO2");
						}else{
							request.setAttribute(Params.SMS,"La información no fue actualizada "+universidadDAO.getMensaje());
							session.setAttribute("universidadVO",universidadVO);
							session.removeAttribute("universidadVO");
							session.removeAttribute("universidadVO2");
						}
				/*	}
					else{
						request.setAttribute(Params.SMS,"La Universidad no se puede actualizar ");
						session.removeAttribute("universidadVO");
						session.removeAttribute("universidadVO2");
					}*/
				
				}	
				else{
				/*	if(uniDAO.validars(universidadVO.getGuniCodigo())){
						request.setAttribute(Params.SMS,"El Cndigo de la universidad ya se encuentra registrado, no se puede registrar");
					}
					
					else*/
					if(universidadDAO.insertar(universidadVO)){
						request.setAttribute(Params.SMS,"La información fue ingresada satisfactoriamente");
						session.removeAttribute("universidadVO");
					}else{
						request.setAttribute(Params.SMS,"La información no fue ingresada "+universidadDAO.getMensaje());
					}
				}
				
			break;
			
			case Params.CMD_EDITAR:
				universidadVO=universidadDAO.asignar(codUniversidad);
				
				if(universidadVO!=null){
					session.setAttribute("universidadVO",universidadVO);
					session.setAttribute("universidadVO2",universidadVO.clone());
					request.setAttribute("guia",codUniversidad);
					
				}else{
					request.setAttribute(Params.SMS,"No se logro obtener los datos del objeto seleccionado");
				}
			break;
			case Params.CMD_ELIMINAR:
				//*************************************************************
				
			/*	if(universidadDAO.valPadre(codUniversidad)){
						request.setAttribute(Params.SMS,"La universidad esta asociada con una Especialidad");
						}*/
				if(universidadDAO.eliminar(codUniversidad)){
						request.setAttribute(Params.SMS,"La información fue eliminada satisfactoriamente");
						session.removeAttribute("universidadVO");
						session.removeAttribute("universidadVO2");
					}
				
				else{
						request.setAttribute(Params.SMS,"La información no fue eliminada, "+universidadDAO.getMensaje());
					}
				
			break;
	case Params.CMD_NUEVO:
		session.removeAttribute("universidadVO");
		session.removeAttribute("universidadVO2");
	break;
	}
	dispatcher[0]=String.valueOf(Params.FORWARD);
	dispatcher[1]=FICHA_NUEVO;
	return dispatcher;
	}
}
