package articulacion.horarioEstudiante.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import articulacion.horarioArticulacion.dao.HorarioArticulacionDAO;
import articulacion.horarioArticulacion.vo.FiltroHorarioVO;
import articulacion.horarioArticulacion.vo.HorarioVO;
import articulacion.horarioArticulacion.vo.ParametroVO;
import articulacion.horarioArticulacion.vo.ParamsVO;
import articulacion.horarioEstudiante.dao.HorarioEstudianteDAO;
import articulacion.horarioEstudiante.vo.LEncabezadoVO;
import siges.login.beans.Login;

public class Nuevo extends Service {

	// private HorarioArticulacionDAO horarioArticulacionDAO;
	private Cursor cursor;

	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[] = new String[2];
		// try{
		// System.out.print("Entra nuevo horest");
		cursor = new Cursor();
		Login usuVO = (Login) session.getAttribute("login");
		HorarioEstudianteDAO encDAO = new HorarioEstudianteDAO(cursor);
		session.setAttribute("listaJornadaVO",
				encDAO.getListaJornada(usuVO.getInstId(), usuVO.getSedeId()));
		// request.setAttribute("listaEncabezadoVO",encDAO.getHorario());

		dispatcher[0] = String.valueOf(Params.FORWARD);

		return dispatcher;
	}

}
