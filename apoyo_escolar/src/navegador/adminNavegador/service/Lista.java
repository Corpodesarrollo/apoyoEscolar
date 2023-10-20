package navegador.adminNavegador.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import navegador.adminNavegador.dao.AdminNavegadorDAO;
import navegador.adminNavegador.vo.ParamsVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;


public class Lista extends Service{
	private String FICHA_LISTA;
	private AdminNavegadorDAO admDAO=new AdminNavegadorDAO(new Cursor());
	
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[]=new String[2];
		FICHA_LISTA=config.getInitParameter("FICHA_LISTA");
		int CMD=getCmd(request,session,ParamsVO.CMD_NUEVO);
		int TIPO=getTipo(request,session,ParamsVO.FICHA_SECCION);
		switch (TIPO) {
			case ParamsVO.FICHA_SECCION:
				switch (CMD) {
					case ParamsVO.CMD_NUEVO: case ParamsVO.CMD_EDITAR: case ParamsVO.CMD_ELIMINAR:
						nuevo(request,session);
					break;
				}
			break;
		}
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FICHA_LISTA;
		return dispatcher;
	}

	public void nuevo(HttpServletRequest request, HttpSession session) throws ServletException{
		try {
			request.setAttribute("listaSeccionNavegadorVO",admDAO.getAllListaSeccioNavegador());
			request.setAttribute("guia",getRequest2(request,"guia","-1"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e.getMessage());
		}
	}

}
