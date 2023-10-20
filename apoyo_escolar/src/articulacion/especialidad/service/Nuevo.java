package articulacion.especialidad.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.especialidadBase.vo.ParamsVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import articulacion.especialidad.vo.EspecialidadVO;
import articulacion.especialidad.dao.EspecialidadDAO;
import siges.login.beans.Login;




public class Nuevo extends Service {
	//private UniversidadDAO universidad;
	private Cursor c;
	private String FICHA_NUEVO;
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String dispatcher[]=new String[2];
		HttpSession session = request.getSession();
		//objeto usuario con datos del mismo
		Login usuVO=(Login)session.getAttribute("login");
		String inst=usuVO.getInstId();
		//**********
		//System.out.println("llego al metodo insertar del nuevo ");
		c = new Cursor();
		EspecialidadVO especialidadVO=(EspecialidadVO)session.getAttribute("especialidadVO");
		EspecialidadVO especialidadVO2=(EspecialidadVO)session.getAttribute("especialidadVO2");
		EspecialidadDAO especialidadDAO=new EspecialidadDAO(c);
		//universidad=new EspecialidadBaseDAO(c);
		
		FICHA_NUEVO=config.getInitParameter("FICHA_NUEVO");
		int CMD=getCmd(request,session,Params.CMD_NUEVO);
		int TIPO=getTipo(request,session,ParamsVO.FICHA_DEFAULT);
		String codUniversidad=request.getParameter("id");
		String parametro=request.getParameter("id2");
		//System.out.println("CMD="+CMD);
		
		//request.setAttribute("listaespecialidadBase", especialidadDAO.getListaCarBase("getListaCarreraBase"));	
		//request.setAttribute("listaPeriodos", especialidadDAO.getListaPeriodos());
		request.setAttribute("listaUniversidad", especialidadDAO.getListaUniversidad());
		request.setAttribute("listaTipoPrograma",especialidadDAO.getListaTipoPrograma());
		
		switch (CMD){			
			case Params.CMD_GUARDAR:
				//Guardar*******************************
				//System.out.println("entra al guardar ");
				if(especialidadVO.getFormaEstado().equals("1")){
					//Actualizar****************************
					//if(especialidadVO.getCarCodinst()==especialidadVO2.getCarCodinst()){
					
						if(especialidadDAO.actualizar(especialidadVO,especialidadVO2)){
							request.setAttribute(Params.SMS,"La información fue actualizada satisfactoriamente");
							session.removeAttribute("especialidadVO");
							session.removeAttribute("especialidadVO2");
						}else{
							request.setAttribute(Params.SMS,"La información no fue actualizada");
							session.setAttribute("especialidadVO",especialidadVO);
							session.removeAttribute("especialidadVO2");
						}
					/*}
					else{
						request.setAttribute(Params.SMS,"El Codigo de la Universidad ya se encuentra Registrado");
					}*/
				
				}	
				else{
					//System.out.println("entra al if ");
					request.setAttribute("listaespecialidadBase", especialidadDAO.getListaCarBase("getListaCarreraBase2",inst));	
					if(especialidadDAO.insertar(especialidadVO)){
						request.setAttribute(Params.SMS,"La información fue ingresada satisfactoriamente");
						session.removeAttribute("especialidadVO");
					}else{
						request.setAttribute(Params.SMS,"La información no fue ingresada");
					}
				}
				
			break;
			
			case Params.CMD_EDITAR:
				especialidadVO=especialidadDAO.asignar(codUniversidad,parametro);
				
				if(especialidadVO!=null){
					session.setAttribute("especialidadVO",especialidadVO);
					session.setAttribute("especialidadVO2",especialidadVO.clone());
					request.setAttribute("guia",codUniversidad);
					
				}else{
					request.setAttribute(Params.SMS,"No se logro obtener los datos del objeto seleccionado");
				}
			break;
			case Params.CMD_ELIMINAR:
				//*************************************************************
				if(true){
					//System.out.println("ELIMINA codins "+codUniversidad+"carcodigo "+parametro);
					if(especialidadDAO.eliminar(codUniversidad,parametro)){
						request.setAttribute(Params.SMS,"La información fue eliminada satisfactoriamente");
						session.removeAttribute("especialidadVO");
						session.removeAttribute("especialidadVO2");
					}else{
						request.setAttribute(Params.SMS,"La información no fue eliminada: La especialidad tiene estudiantes asociados");
					}
				}else{
					request.setAttribute(Params.SMS,"No tiene los permisos necesarios para realizar esta accinn");
				}
			break;
	case Params.CMD_NUEVO:
		request.setAttribute("listaespecialidadBase", especialidadDAO.getListaCarBase("getListaCarreraBase2",inst));
		session.removeAttribute("especialidadVO");
		session.removeAttribute("especialidadVO2");
	break;
	}
		if(CMD==Params.CMD_NUEVO||CMD==Params.CMD_GUARDAR||CMD==Params.CMD_ELIMINAR)
		{
			//System.out.println("estro a la primera");
			request.setAttribute("listaespecialidadBase", especialidadDAO.getListaCarBase("getListaCarreraBase2",inst));		
		}
		else if(CMD==Params.CMD_EDITAR)
		{
			//System.out.println("estro a la segundo");
			request.setAttribute("listaespecialidadBase", especialidadDAO.getListaCarBase("getListaCarreraBase",inst));	
		}

	//request.setAttribute("listaespecialidadBase", especialidadDAO.getListaCarBase("getListaCarreraBase2"));
	dispatcher[0]=String.valueOf(Params.FORWARD);
	dispatcher[1]=FICHA_NUEVO;
	return dispatcher;
	}
}
