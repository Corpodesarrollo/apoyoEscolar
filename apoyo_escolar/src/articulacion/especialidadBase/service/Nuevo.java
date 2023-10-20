package articulacion.especialidadBase.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.especialidadBase.vo.ParamsVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import articulacion.especialidadBase.vo.EspecialidadBaseVO;
import articulacion.especialidadBase.dao.EspecialidadBaseDAO;
import siges.login.beans.Login;
import articulacion.universidad.dao.UniversidadDAO;




public class Nuevo extends Service {
	//private UniversidadDAO universidad;
	private Cursor c;
	private String FICHA_NUEVO;
	private EspecialidadBaseDAO espeDAO;
	String codigo=null;
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String dispatcher[]=new String[2];
		HttpSession session = request.getSession();
		//objeto usuario con datos del mismo
		Login usuVO=(Login)session.getAttribute("login");
		//**********
		
		c = new Cursor();
		
		espeDAO=new EspecialidadBaseDAO(c);
		EspecialidadBaseVO especialidadBaseVO=(EspecialidadBaseVO)session.getAttribute("especialidadBaseVO");
		EspecialidadBaseVO especialidadBaseVO2=(EspecialidadBaseVO)session.getAttribute("especialidadBaseVO2");
		EspecialidadBaseDAO especialidadBaseDAO=new EspecialidadBaseDAO(c);
		
		
		//universidad=new EspecialidadBaseDAO(c);
		
		FICHA_NUEVO=config.getInitParameter("FICHA_NUEVO");
		int CMD=getCmd(request,session,Params.CMD_NUEVO);
		int TIPO=getTipo(request,session,ParamsVO.FICHA_DEFAULT);
		String codUniversidad=request.getParameter("id");
		
		
		switch (CMD){			
			case Params.CMD_GUARDAR:
				//Guardar*******************************
				if(especialidadBaseVO.getFormaEstado().equals("1")){
					//Actualizar****************************
				//	if(especialidadBaseVO.getGespbasCodigo()==especialidadBaseVO2.getGespbasCodigo()){
					
					 if(especialidadBaseDAO.actualizar(especialidadBaseVO,especialidadBaseVO2)){
							request.setAttribute(Params.SMS,"La información fue actualizada satisfactoriamente");
							session.removeAttribute("especialidadBaseVO");
							session.removeAttribute("especialidadBaseVO2");
						}else{
							request.setAttribute(Params.SMS,"La información no fue actualizada "+especialidadBaseDAO.getMensaje());
							session.setAttribute("especialidadBaseVO",especialidadBaseVO);
							session.removeAttribute("especialidadBaseVO");
							session.removeAttribute("especialidadBaseVO2");
						}
				/*	}
					else{
						request.setAttribute(Params.SMS,"El Cndigo no se puede actualizar");
						session.removeAttribute("especialidadBaseVO");
						session.removeAttribute("especialidadBaseVO2");
					}*/
				
				}	
				else{
				/*	if(espeDAO.validars(especialidadBaseVO.getGespbasCodigo())){
						request.setAttribute(Params.SMS,"El Cndigo de Especialidad Base ya se encuentra registrado, no se puede registrar");
					}
					else*/
					if(especialidadBaseDAO.insertar(especialidadBaseVO)){
						request.setAttribute(Params.SMS,"La información fue ingresada satisfactoriamente ");
						session.removeAttribute("especialidadBaseVO");
					}else{
						request.setAttribute(Params.SMS,"La información no fue ingresada "+especialidadBaseDAO.getMensaje());
					}
				}
				
			break;
			
			case Params.CMD_EDITAR:
				especialidadBaseVO=especialidadBaseDAO.asignar(codUniversidad);
				
				if(especialidadBaseVO!=null){
					session.setAttribute("especialidadBaseVO",especialidadBaseVO);
					session.setAttribute("especialidadBaseVO2",especialidadBaseVO.clone());
					request.setAttribute("guia",codUniversidad);
					
				}else{
					request.setAttribute(Params.SMS,"No se logro obtener los datos del objeto seleccionado");
				}
			break;
			case Params.CMD_ELIMINAR:
				//*************************************************************
				if(/*como hago para ver si el usuario tiene permisos?*/true){
					codigo=getRequest(request,"id");
				/*	System.out.println("el codigo a eliminar es "+codigo);
					if(especialidadBaseDAO.valPadre(codUniversidad)){
						System.out.println("este registro es padre");
						request.setAttribute(Params.SMS,"La especialidad base esta asociada con una Especialidad");
						}
					else */
					if(especialidadBaseDAO.eliminar(codigo)){
						request.setAttribute(Params.SMS,"La información fue eliminada satisfactoriamente");
						session.removeAttribute("especialidadBaseVO");
						session.removeAttribute("especialidadBaseVO2");
					}else{
						request.setAttribute(Params.SMS,"La información no fue eliminada "+especialidadBaseDAO.getMensaje());
					}
				}else{
					request.setAttribute(Params.SMS,"No tiene los permisos necesarios para realizar esta accinn");
				}
			break;
	case Params.CMD_NUEVO:
		session.removeAttribute("especialidadBaseVO");
		session.removeAttribute("especialidadBaseVO2");
	break;
	}
	dispatcher[0]=String.valueOf(Params.FORWARD);
	dispatcher[1]=FICHA_NUEVO;
	return dispatcher;
	}
}
