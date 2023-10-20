package siges.institucion.correoLider;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.dao.Ruta;
import siges.institucion.correoLider.beans.CorreoLiderVO;
import siges.institucion.correoLider.beans.ParamsMail;
import siges.institucion.correoLider.beans.ParamsVO;
import siges.institucion.correoLider.dao.CorreoDAO;
import siges.institucion.correoLider.io.CorreoLiderIO;
import siges.login.beans.Login;

/**
 * 24/08/2007 
 * @author Latined
 * @version 1.2
 */
public class ControllerCorreoLider   extends Service{

	private String FICHA;
	private String FICHA_CORREO;
	private Cursor cursor;
	private CorreoDAO correoDAO;
	private CorreoLiderIO correoLiderIO;
	
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[]=new String[2];
		cursor=new Cursor();
		correoDAO=new CorreoDAO(cursor);
		correoLiderIO=new CorreoLiderIO();
		int CMD=getCmd(request,session,Params.CMD_NUEVO);
		int TIPO=getTipo(request,session,ParamsVO.FICHA_DEFAULT);
		CorreoLiderVO correoLiderVO=(CorreoLiderVO)session.getAttribute("correoLiderVO");
		Login usuVO=(Login)session.getAttribute("login");
		FICHA_CORREO=config.getInitParameter("FICHA_CORREO");
		System.out.println("LOS VALORES(nuevo)==="+CMD+"//"+TIPO);
		try{
			switch(TIPO){
				case ParamsVO.FICHA_CORREO:
					FICHA=FICHA_CORREO;
					switch(CMD){
						case Params.CMD_NUEVO:
							initCorreo(request,correoLiderVO);
						break;
						case Params.CMD_GUARDAR:
							correoLiderVO=guardarCorreo(request,usuVO,correoLiderVO);
							initCorreo(request,correoLiderVO);
						break;
					}
				break;
			}
			dispatcher[0]=String.valueOf(Params.INCLUDE);
			dispatcher[1]=FICHA;
			return dispatcher;
		}catch(Exception e){
			e.printStackTrace();
			throw new ServletException(e.getMessage());
		}
	}
	
	private void initCorreo(HttpServletRequest request,CorreoLiderVO correoLiderVO){
		request.setAttribute("lLocalidad",correoDAO.getAllLocalidad());
		if(correoLiderVO!=null){
			if(correoLiderVO.getCorrLocalidad()!=-99){
				request.setAttribute("lInstitucion",correoDAO.getAjaxInstitucion(correoLiderVO.getCorrLocalidad()));
			}
			if(correoLiderVO.getCorrTipoCargo()!=-99){
				request.setAttribute("lCargo",correoDAO.getAjaxCargo(correoLiderVO.getCorrTipoCargo()));
			}
		}
	}
	
	private CorreoLiderVO guardarCorreo(HttpServletRequest request,Login login,CorreoLiderVO correoLiderVO){
			String soloArchivo=null;
		try{
			soloArchivo=correoLiderVO.getCorrAdjunto();
			correoLiderVO.setCorrNombreAdjunto(soloArchivo);
			if(correoLiderVO.getCorrBandera()==1){
				ResourceBundle rb = ResourceBundle.getBundle("siges.institucion.correoLider.bundle.correoLider");
				String pathSubir = Ruta.get(context, rb.getString("correo.pathSubir") + "." + login.getUsuarioId());
				if(correoLiderVO.getCorrAdjunto().indexOf(File.separator)==-1){//NO tiene ruta
					correoLiderVO.setCorrAdjunto(pathSubir+correoLiderVO.getCorrAdjunto());	
				}
			}
			List lista=correoDAO.getCorreos(correoLiderVO);
			System.out.println("cantidad de correos="+lista.size());
			if(lista==null || lista.size()==0){
				request.setAttribute(Params.SMS,"La bnsqueda no encontrn correos");
				if(correoLiderVO.getCorrBandera()==1){
					correoLiderVO.setCorrAdjunto(soloArchivo);
				}
				correoLiderVO.setCorrEstado(2);
				correoDAO.insertCorreo(correoLiderVO,login);
				return correoLiderVO;
			}
			correoLiderVO.setCorrCorreos(lista);
			ParamsMail mail=correoDAO.getParamsMail();
			if(mail==null){
				request.setAttribute(Params.SMS,"Los parnmetros para el envno del correo no existen");
				correoLiderVO.setCorrEstado(2);
				correoDAO.insertCorreo(correoLiderVO,login);
				return correoLiderVO;
			}
			correoLiderVO.setParamsMail(mail);
			correoLiderVO=correoLiderIO.enviarCorreo(correoLiderVO);
			if(correoLiderVO.getCorrBandera()==1){
				File f=new File(correoLiderVO.getCorrAdjunto());
				if(f.exists()){
					f.delete();
				}
			}
			correoDAO.insertCorreo(correoLiderVO,login);
			request.setAttribute(Params.SMS,"Correo enviado satisfactoriamente");
			request.getSession().removeAttribute("correoLiderVO");
		}catch(Exception e){
			correoLiderVO.setCorrAdjunto(soloArchivo);
			if(correoLiderVO.getCorrBandera()==1){
				File f=new File(correoLiderVO.getCorrAdjunto());
				if(f.exists()){
					f.delete();
				}
			}
			correoLiderVO.setCorrEstado(2);
			correoDAO.insertCorreo(correoLiderVO,login);
			request.setAttribute(Params.SMS,"Error intentando enviar correo: "+e.getMessage());
		}
		return correoLiderVO;
	}
}
