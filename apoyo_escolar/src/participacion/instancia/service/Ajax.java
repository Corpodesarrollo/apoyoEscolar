/**
 * 
 */
package participacion.instancia.service;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import participacion.instancia.dao.InstanciaDAO;
import participacion.instancia.vo.ParamsVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.login.beans.Login;

/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class Ajax extends Service{

	/**
	 * 
	 */
	public String FICHA_INSTANCIA;
	public String FICHA_RANGO;
	/**
	 * 
	 */
	private InstanciaDAO instanciaDAO=new InstanciaDAO(new Cursor());

	/*
	 * @function: 
	 * @param config
	 * @throws ServletException
	 * (non-Javadoc)
	 * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		FICHA_INSTANCIA=config.getInitParameter("FICHA_INSTANCIA");
		FICHA_RANGO=config.getInitParameter("FICHA_RANGO");
	}
	
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String dispatcher[]=new String[2];
		String FICHA=null;
		HttpSession session = request.getSession();
		int CMD=getCmd(request,session,ParamsVO.CMD_NUEVO);
		int TIPO=getTipo(request,session,ParamsVO.FICHA_DEFAULT);
		switch (TIPO){
			case ParamsVO.FICHA_RANGO:
				FICHA=FICHA_RANGO;
				switch (CMD){
					case ParamsVO.CMD_AJAX_INSTANCIA: case ParamsVO.CMD_AJAX_INSTANCIA0:
						rangoInstancia(request, session,CMD);	
					break;	
				}
			break;
		}
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FICHA;
		return dispatcher;
	}

	public void rangoInstancia(HttpServletRequest request, HttpSession session,int cmd) throws ServletException {
		try {
			String params[]=request.getParameterValues("ajax");
			if(params==null || params.length<1 || params[0]==null) return;
			request.setAttribute("listaInstanciaVO", instanciaDAO.getListaInstancia(Integer.parseInt(params[0])));
			request.setAttribute("ajaxParam",String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
}
