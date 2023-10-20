package articulacion.plantillaArticulacion.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.login.beans.Login;
import articulacion.plantillaArticulacion.dao.FiltroPlantillaDAO;
import articulacion.plantillaArticulacion.dao.PlantillaDAO;
import articulacion.plantillaArticulacion.vo.DatosVO;
import articulacion.plantillaArticulacion.vo.ParamsVO;
import articulacion.plantillaArticulacion.vo.ListaGrupoVO;

public class Ajax extends Service{
	private String FICHA;
	private String FICHA_ASIG;
	private Cursor cursor;
	private PlantillaDAO plantillaDAO;

	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String dispatcher[]=new String[2];
		try{
		HttpSession session = request.getSession();
		Login usuVO=(Login)session.getAttribute("login");
		//DatosVO datosVO=(DatosVO)session.getAttribute("ajaxGrupo");

		//ListaGrupoVO listagrupoVO=new ListaGrupoVO();
		
		cursor=new Cursor();
		plantillaDAO=new PlantillaDAO(cursor);
		FICHA_ASIG=config.getInitParameter("FICHA_ASIG");
		int CMD=getCmd(request,session,ParamsVO.CMD_AJAX);
		int TIPO=getTipo(request,session,ParamsVO.FICHA_DEFAULT);
		//System.out.println("los valores="+CMD+"//"+TIPO);
		//FiltroPlantillaDAO fPlantilla=new FiltroPlantillaDAO(cursor);
		String params[]=null;
		switch(TIPO){
			case ParamsVO.FICHA_DEFAULT:
				FICHA=FICHA_ASIG;
				switch(CMD){
					case ParamsVO.CMD_AJAX_GRUPO:
						params=request.getParameterValues("ajax");
						if(params==null || params.length<3 || params[0]==null || params[1]==null || params[2]==null|| params[3]==null) break;
						String metodologia=usuVO.getMetodologiaId();
	
						request.setAttribute("ajaxGrupo",plantillaDAO.getAjaxGrupo(Long.parseLong(params[0]),Integer.parseInt(params[1]),Integer.parseInt(params[2]),Integer.parseInt(params[3]),metodologia));
						request.setAttribute("ajaxParam",String.valueOf(ParamsVO.CMD_AJAX_GRUPO));
					 
					break;
				}
			break;	
		}
		}catch(Exception e){e.printStackTrace();}	
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FICHA;
		return dispatcher;
	}
	
}
