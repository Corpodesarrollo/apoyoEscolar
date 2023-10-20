package siges.reporte;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;

import siges.common.service.AjaxCommon;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.login.beans.Login;
import siges.reporte.beans.ParamsVO;
import siges.reporte.dao.CarneDAO;

public class AjaxCarne extends AjaxCommon{
	
	private static final long serialVersionUID = 1L;
	private String FICHA;
	private String LISTA_AJAX;
	private Cursor cursor; 
	private CarneDAO carneDAO= null;
	private SimpleDateFormat formaFecha = new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss.SSS");
	Login usuVO = null;
	
 
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String dispatcher[]=new String[2];
		cursor=new Cursor();
		LISTA_AJAX = config.getInitParameter("LISTA_AJAX");  
		carneDAO = new CarneDAO( cursor); 
		dao =  carneDAO;
		usuVO = (Login)request.getSession().getAttribute("login");
		
		try{
			HttpSession session = request.getSession();
			cursor=new Cursor();  
			int CMD=getCmd(request,session,ParamsVO.CMD_AJAX);
			int TIPO=getTipo(request,session,ParamsVO.FICHA_DEFAULT);
			
			System.out.println("padre flia ajax CMD " + CMD +" TIPO " + TIPO);
			String params[]=null;
			FICHA=LISTA_AJAX;
			switch(TIPO){
			case ParamsVO.FICHA_CARME:
				getLocal(request,usuVO, ParamsVO.CMD_AJAX_INST);
				switch(CMD){
				case ParamsVO.CMD_AJAX_LOC:
					getLocal(request,usuVO, CMD);
					break;
				case ParamsVO.CMD_AJAX_INST:
					getInst(request,usuVO, CMD);
					break;
				case ParamsVO.CMD_AJAX_SED:
					getSede(request,usuVO,CMD);
					break;
				case ParamsVO.CMD_AJAX_JORD:
					getJornada(request,usuVO,CMD);
					break;
				case ParamsVO.CMD_AJAX_METD:
					getMetodologia(request,usuVO,CMD);
					break;	
			 	case ParamsVO.CMD_AJAX_GRAD:
					getGrado(request,usuVO,CMD);
					break;		
				case ParamsVO.CMD_AJAX_GRUP:
					getGrupo(request,usuVO,CMD);
					break;		
		 	
					
				}
				
				break;	
				
				
			}
		}catch(Exception e){e.printStackTrace();}	
		dispatcher[0]=String.valueOf(Params.FORWARD);
		dispatcher[1]=FICHA;
		System.out.println("**********ficha="+FICHA);
		return dispatcher;
	}
	
	
	/**
	 * @param request
	 * @param usuVO
	 * @param cmd
	 * @throws ServletException
	 */
	protected void getLocal(HttpServletRequest request, Login usuVO, int cmd )  throws ServletException{
		System.out.println(formaFecha.format(new Date()) +  " Ajax getLocal");
		try{
			
			long codLoc = getCodigo(usuVO.getMunId());
			long codInst = getCodigo(usuVO.getInstId());
			long codJor = getCodigo(usuVO.getJornadaId());
			long codSed = getCodigo(usuVO.getSedeId());
			long codMetodo = getCodigo(usuVO.getMetodologiaId());
			long codGrad = getCodigo(usuVO.getGradoId());
			long codGru = getCodigo(usuVO.getGrupoId());
			this.cursor = new Cursor();
			
			 
			
			request.getSession().setAttribute("listaLocalidad", carneDAO.getListaLocal() );
			request.getSession().setAttribute("listaTipoColegio", carneDAO.getListaTipoColegio() );
			
			/*System.out.println("codLoc " + codLoc);
			 System.out.println("codInst " + codInst);
			 System.out.println("codJor " + codJor);
			 System.out.println("codSed " + codSed);
			 System.out.println("codMetodo " + codMetodo);
			 System.out.println("codGrad "+ codGrad);
			 System.out.println("codGru " + codGru);*/
			
			if(codLoc > -99){
				request.getSession().setAttribute("listaColegio",carneDAO.getListaInst(codLoc));
			}else{
				request.getSession().removeAttribute("listaColegio");
			}
			
			if(codInst > -99){
				request.getSession().setAttribute("sectorId",""+carneDAO.getInstSector(codInst));
				request.getSession().setAttribute("listaSede",carneDAO.getSede(codInst) );
			}else{
				request.getSession().removeAttribute("listaSede");
			}
			
			if(codInst > -99 &&  codSed > -99){
				request.getSession().setAttribute("listaJornada",carneDAO.getListaJornd(codInst, codSed));
			}else{
				request.getSession().removeAttribute("listaJornada");
			}
			
			if(codInst > -99){
				request.getSession().setAttribute("listaMetodo",carneDAO.getListaMetod(codInst));
			}else{
				request.getSession().removeAttribute("listaMetodo");
			}
			
			
			
			request.getSession().setAttribute("ajaxParam",String.valueOf(cmd));
		}catch (Exception e) {
			System.out.println(e.getMessage() );
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * @function:  
	 * @param request
	 * @param usuVO
	 * @throws ServletException
	 */
	protected void getInst(HttpServletRequest request, Login usuVO, int cmd )  throws ServletException{
		System.out.println(formaFecha.format(new Date()) +  " Ajax getInst");
		try{
			String params[]=null;
			params=request.getParameterValues("ajax"); 
			if(params != null && GenericValidator.isLong(params[0]) ){
				long codLoc = Long.parseLong(params[0]);
				long codSect = Long.parseLong(params[1]);
				//carneDAO = new Util(cursor); 
				request.getSession().setAttribute("listaColegio",carneDAO.getListaColegio(codLoc, codSect));
				request.getSession().setAttribute("ajaxParam",String.valueOf(cmd));
				
			}else{
				System.out.println("params es null o invalido en ajax");
			}
			
			
		}catch (Exception e) {
			System.out.println(e.getMessage() );
			e.printStackTrace();
		}
	} 
	
	
	
	} 
