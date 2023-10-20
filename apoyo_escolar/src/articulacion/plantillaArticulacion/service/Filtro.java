package articulacion.plantillaArticulacion.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.vo.Params;
import siges.common.service.Service;
import articulacion.plantillaArticulacion.dao.FiltroPlantillaDAO;
import siges.login.beans.Login;
import siges.dao.Cursor;

public class Filtro extends Service{
	private String FILTRO;
	private FiltroPlantillaDAO fPlantilla=new FiltroPlantillaDAO(new Cursor());
	
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[]=new String[2];
		FILTRO=config.getInitParameter("FILTRO_PLANTILLA");
		//DatosVO datosVO=(DatosVO)session.getAttribute("filtroPlantillaArticulacionVO");
		//int CMD=getCmd(request,session);
		//int TIPO=getTipo(request,session,0);
		Login usuVO=(Login)session.getAttribute("login");
		request.setAttribute("listaSedeVO",fPlantilla.getSedes(usuVO.getInstId()));
		request.setAttribute("listaJornadaVO",fPlantilla.getJornada(usuVO.getInstId(),usuVO.getSedeId()));
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FILTRO;
		return dispatcher;
	}
}
