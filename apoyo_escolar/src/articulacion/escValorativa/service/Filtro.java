package articulacion.escValorativa.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.vo.Params;
import siges.common.service.Service;

import siges.login.beans.Login;
import siges.dao.Cursor;
import articulacion.escValorativa.dao.EscValorativaDAO;
import articulacion.escValorativa.vo.DatosVO;



public class Filtro extends Service{
	
	private String FILTRO;
	private EscValorativaDAO escValorativaDAO=new EscValorativaDAO(new Cursor());
	
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String dispatcher[]=new String[2];
		FILTRO=config.getInitParameter("FILTRO");
		request.setAttribute("listaVigencia",escValorativaDAO.getListaVigencia());		
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FILTRO;
		return dispatcher;
	}

}
