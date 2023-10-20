package articulacion.asigAcademica.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.asigAcademica.dao.AsignacionDAO;
import articulacion.asigAcademica.vo.DatosVO;
import articulacion.asigAcademica.vo.ParamsVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.exceptions.InternalErrorException;
import siges.login.beans.Login;

public class Lista extends Service {

	private String FICHA = null;
	private String FICHA_FILTRO;
	private Cursor cursor;
	private AsignacionDAO asignacionDAO;

	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String dispatcher[] = new String[2];
		// try{
		HttpSession session = request.getSession();
		Login usuVO = (Login) session.getAttribute("login");
		cursor = new Cursor();
		asignacionDAO = new AsignacionDAO(cursor);
		FICHA_FILTRO = config.getInitParameter("FICHA_FILTRO");
		int CMD = getCmd(request, session, ParamsVO.CMD_BUSCAR);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		// System.out.println("valor de tipo alli="+TIPO);
		switch (TIPO) {
		case ParamsVO.FICHA_ASIGNACION:
			// switch(CMD){
			// case ParamsVO.CMD_BUSCAR: case ParamsVO.CMD_EDITAR: case
			// ParamsVO.CMD_GUARDAR: case ParamsVO.CMD_ELIMINAR:
			FICHA = FICHA_FILTRO;
			initFiltroAsignacion(request, usuVO);
			// break;
			// }
			break;
		}
		// }catch(Exception e){e.printStackTrace();}
		dispatcher[0] = String.valueOf(Params.INCLUDE);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	private void initFiltroAsignacion(HttpServletRequest request, Login usuVO) {
		try {
			request.setAttribute("listaVigencia",
					asignacionDAO.getListaVigencia());
			DatosVO filtro = (DatosVO) request.getSession().getAttribute(
					"filAsigAcaVO");
			if (filtro != null && filtro.getFormaEstado().equals("1")) {
				request.setAttribute("lDocenteVO", asignacionDAO
						.getAjaxDocente(filtro.getInstitucion(),
								filtro.getSede(), filtro.getJornada()));
				/*
				 * List l=asignacionDAO.getAllAsignacion(filtro); if(l!=null &&
				 * l.size()>0){ filtro.setBuscado(true);
				 * filtro.setFormaEstado("1");
				 * request.setAttribute("lAsignacionVO",l); }
				 */
			} else {
				request.getSession().removeAttribute("filAsigAcaVO");
				filtro = new DatosVO();
				filtro.setAnho((int) asignacionDAO.getVigenciaNumerico());
				filtro.setPeriodo((int) asignacionDAO.getPeriodoNumerico());
				request.getSession().setAttribute("filAsigAcaVO", filtro);
			}
			long inst = -99;
			int sede = -99;
			int jor = -99;
			inst = Long.parseLong(usuVO.getInstId());
			if (usuVO.getSedeId() != null && !usuVO.getSedeId().equals(""))
				sede = Integer.parseInt(usuVO.getSedeId());
			if (usuVO.getJornadaId() != null
					&& !usuVO.getJornadaId().equals(""))
				jor = Integer.parseInt(usuVO.getJornadaId());
			// SEDE
			request.setAttribute("lSedeVO", asignacionDAO.getSede(inst, sede));
			// JORNADA
			request.setAttribute("lJornadaVO",
					asignacionDAO.getJornada(inst, sede, jor));
		} catch (Exception e) {
			// System.out.print("ocurrio aca");
			e.printStackTrace();
		}
	}
}
