/**
 * 
 */
package articulacion.artAdminPlanEstudios.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.artAdminPlanEstudios.dao.AdminPlanEstudiosDAO;
import articulacion.artAdminPlanEstudios.vo.FiltroBorrarVO;
import articulacion.artAdminPlanEstudios.vo.ParamsVO;
import siges.common.service.Service;
import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.login.beans.Login;

/**
 * 11/12/2007 
 * @author Latined
 * @version 1.2
 */
public class Filtro extends Service{
	private String FICHA;
	private String FICHA_BORRAR;
	private String FICHA_DUPLICAR;

	private AdminPlanEstudiosDAO adminPlanEstudiosDAO=new AdminPlanEstudiosDAO(new Cursor());


	public String[] process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[] = new String[2];
		Login usuVO=(Login)session.getAttribute("login");
		FiltroBorrarVO filtroBorrarVO=(FiltroBorrarVO)session.getAttribute("filtroBorrarVO");
		FICHA_BORRAR = config.getInitParameter("FICHA_BORRAR");
		FICHA_DUPLICAR = config.getInitParameter("FICHA_DUPLICAR");
		int CMD = getCmd(request, session, ParamsVO.CMD_NUEVO);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_BORRAR);
//		System.out.println("LOS VALORES===" + CMD + "//" + TIPO);
		try {
			switch (TIPO) {
				case ParamsVO.FICHA_BORRAR:
					FICHA=FICHA_BORRAR;
					switch (CMD) {
					case ParamsVO.CMD_NUEVO:
						filtroBorrarNuevo(request,session,filtroBorrarVO,usuVO);
						break;
					case ParamsVO.CMD_BUSCAR:
						filtroBorrarNuevo(request,session,filtroBorrarVO,usuVO);
						filtroBorrarBuscar(request,session,filtroBorrarVO,usuVO);
						break;
					}
				break;
				case ParamsVO.FICHA_DUPLICAR:
					FICHA=FICHA_DUPLICAR;
					switch (CMD) {
					case ParamsVO.CMD_NUEVO:
						filtroDuplicarNuevo(request,session,filtroBorrarVO,usuVO);
					break;
					case ParamsVO.CMD_BUSCAR:
						filtroDuplicarBuscar(request,session,filtroBorrarVO,usuVO);
					break;
					}
				break;
			}
			dispatcher[0] = String.valueOf(ParamsVO.INCLUDE);
			dispatcher[1] = FICHA;
			return dispatcher;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e.getMessage());
		}
	}

	private void filtroBorrarNuevo(HttpServletRequest request,HttpSession session,FiltroBorrarVO filtro,Login usuVO)throws Exception{
		if(filtro==null){
			filtro=new FiltroBorrarVO();
			filtro.setFilAnhoVigencia((int)adminPlanEstudiosDAO.getVigenciaNumerico());
			filtro.setFilAnhoVigencia2((int)adminPlanEstudiosDAO.getVigenciaNumerico());
			session.setAttribute("filtroBorrarVO",filtro);
		}
		request.setAttribute("listaVigencia",adminPlanEstudiosDAO.getListaVigencia());			
	}
	
	private void filtroBorrarBuscar(HttpServletRequest request,HttpSession session,FiltroBorrarVO filtro,Login usuVO)throws Exception{
		try{
			request.setAttribute("listaResultado",adminPlanEstudiosDAO.borrarPlan(filtro));
			request.setAttribute(ParamsVO.SMS,"El plan de estudios fue borrado satisfactoriamente");
			request.setAttribute("formaEstado","1");
			request.setAttribute("listaVigencia",adminPlanEstudiosDAO.getListaVigencia());			
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS,"No se puede borrar el plan de estudios solicitado: "+e.getMessage());
		}
	}

	private void filtroDuplicarNuevo(HttpServletRequest request,HttpSession session,FiltroBorrarVO filtro,Login usuVO)throws Exception{
		if(filtro==null){
			filtro=new FiltroBorrarVO();
			filtro.setFilAnhoVigencia((int)adminPlanEstudiosDAO.getVigenciaNumerico());
			filtro.setFilAnhoVigencia2((int)adminPlanEstudiosDAO.getVigenciaNumerico());
			session.setAttribute("filtroBorrarVO",filtro);
		}
		request.setAttribute("listaVigencia",adminPlanEstudiosDAO.getListaVigencia());			
	}
	private void filtroDuplicarBuscar(HttpServletRequest request,HttpSession session,FiltroBorrarVO filtro,Login usuVO)throws Exception{
		try{
			request.setAttribute("listaResultado",adminPlanEstudiosDAO.duplicarPlan(filtro));
			request.setAttribute(ParamsVO.SMS,"El plan de estudios fue duplicado satisfactoriamente");
			request.setAttribute("formaEstado","1");
			request.setAttribute("listaVigencia",adminPlanEstudiosDAO.getListaVigencia());			
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS,"No se puede duplicar el plan de estudios solicitado: "+e.getMessage());
		}
	}
}
