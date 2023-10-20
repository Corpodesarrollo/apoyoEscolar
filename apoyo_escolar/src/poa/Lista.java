package poa;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.area.dao.AreaDAO;
import articulacion.area.vo.FiltroVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.login.beans.Login;

public class Lista  extends Service{

	private String FICHA_LISTA;
	private Cursor cursor;
	private AreaDAO areaDAO;

	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[]=new String[2];
		cursor=new Cursor();
		areaDAO=new AreaDAO(cursor);
		//Login usuVO=(Login)session.getAttribute("login");
		FiltroVO filtro=(FiltroVO)session.getAttribute("filAreVO");
		FICHA_LISTA=config.getInitParameter("FICHA_LISTA");
		int CMD=getCmd(request,session,Params.CMD_NUEVO);
		int TIPO=getTipo(request,session,0);
//		System.out.println("LOS VALORES==="+CMD+"//"+TIPO);
		try{
			request.setAttribute("listaVigencia",areaDAO.getListaVigencia());
			switch(CMD){
				case Params.CMD_NUEVO:
					if(filtro==null){
						filtro=new FiltroVO();
					}
					if(filtro.getFormaEstado().equals("1")){
						buscar(request,filtro);
					}else{						
						filtro.setFilAnho(areaDAO.getVigenciaNumerico());
						filtro.setFilPer((int)areaDAO.getPeriodoNumerico());
						session.setAttribute("filAreVO",filtro);
					}	
				break;
				case Params.CMD_BUSCAR: case Params.CMD_EDITAR: case Params.CMD_ELIMINAR: case Params.CMD_GUARDAR:
//					System.out.println("valores de filtro:"+filtro.getFilAnho()+"//"+filtro.getFilPer());
					buscar(request,filtro);
				break;
			}
			dispatcher[0]=String.valueOf(Params.INCLUDE);
			dispatcher[1]=FICHA_LISTA;
			return dispatcher;
		}catch(Exception e){
			e.printStackTrace();
			throw new ServletException(e.getMessage());
		}
	}
	
	private void buscar(HttpServletRequest request,FiltroVO filtro){
		request.setAttribute("listaAreaVO",areaDAO.getAllArea(filtro));
		filtro.setFormaEstado("1");
	}

}
