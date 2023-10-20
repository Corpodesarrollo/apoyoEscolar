/**
 * 
 */
package articulacion.artEncuesta.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import articulacion.artEncuesta.vo.ParamsVO;
import siges.common.service.Service;
import siges.dao.Cursor;
import articulacion.artEncuesta.dao.EncuestaDAO;
import articulacion.artEncuesta.vo.MostrarEncuestaVO;

/**
 * 15/02/2008 
 * @author Latined
 * @version 1.2
 */
public class PopUp extends Service {
	private String FICHA;

	private EncuestaDAO encuestaDAO = new EncuestaDAO(new Cursor());

	public String[] process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dispatcher[] = new String[2];
		FICHA = config.getInitParameter("FICHA");
		try {
			encuestaNuevo(request);
			dispatcher[0] = String.valueOf(ParamsVO.INCLUDE);
			dispatcher[1] = FICHA;
			return dispatcher;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e.getMessage());
		}
	}

	private void encuestaNuevo(HttpServletRequest request) throws ServletException{
		try{			
			Long cod = new Long((String)request.getParameter("id"));
			//long codigo=Long.parseLong(cod);
			MostrarEncuestaVO rencuestaVO=encuestaDAO.getEncuesta(cod.longValue());
			request.getSession().setAttribute("rencuestaVO",rencuestaVO);
			request.setAttribute("listaAsignatura",encuestaDAO.getListaAsignatura());
			request.setAttribute("listaImportancia",encuestaDAO.getListaImportancia());
			request.setAttribute("listaCiclo",encuestaDAO.getListaCiclo());
			request.setAttribute("listaInteres",encuestaDAO.getListaInteres());
			request.setAttribute("listaCarrera",encuestaDAO.getListaCarrera());
			request.setAttribute("listaIngenieria",encuestaDAO.getListaIngenieria());
			request.setAttribute("listaEspecialidad",encuestaDAO.getListaEspecialidad());
		}catch(Exception e){e.printStackTrace();
			throw new ServletException("Error interno: "+e.getMessage());
		}
	}

}
