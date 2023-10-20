package siges.gestionAcademica.inasistencia.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.gestionAcademica.inasistencia.dao.InasistenciaDAO;
import siges.gestionAcademica.inasistencia.vo.FiltroInasistencia;
import siges.gestionAcademica.inasistencia.vo.ParamVO;
import siges.login.beans.Login;
import siges.util.Recursos;

public class Filtro extends Service {

	private String FICHA;
	private String FICHA_INASISTENCIA;
	private String FICHA_RETARDO;
	private String FICHA_SALIDA_TARDE;
	private String FICHA_INASISTENCIA_SIMPLE;
	private InasistenciaDAO inasistenciaDAO;
	private Cursor cursor;

	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[] = new String[2];
		// try{
		cursor = new Cursor();
		inasistenciaDAO = new InasistenciaDAO(cursor);
		FICHA_INASISTENCIA = config.getInitParameter("FICHA_INASISTENCIA");
		FICHA_RETARDO = config.getInitParameter("FICHA_RETARDO");
		FICHA_SALIDA_TARDE = config.getInitParameter("FICHA_SALIDA_TARDE");
		FICHA_INASISTENCIA_SIMPLE = config
				.getInitParameter("FICHA_INASISTENCIA_SIMPLE");
		Login usuVO = (Login) session.getAttribute("login");
		// FiltroInasistencia
		// filtroInasistencia=(FiltroInasistencia)session.getAttribute("filtroInasistencia");
		int CMD = getCmd(request, session, Params.CMD_NUEVO);
		int TIPO = getTipo(request, session, ParamVO.FICHA_DEFAULT);
		// System.out.println("Valores inasistenciaFiltro: " + TIPO + "//" +
		// CMD);

		FiltroInasistencia filtroInasistencia = (FiltroInasistencia) request
				.getSession().getAttribute("filtroInasistencia");
		// if (filtroInasistencia != null) {
		// System.out.println("filtroInasistencia "
		// + filtroInasistencia.getFilGrado());
		// }
		switch (TIPO) {
		case ParamVO.FICHA_INASISTENCIA:
			if (inasistenciaDAO.tieneHorario(Long.parseLong(usuVO.getInstId()))) {
				initInasistencia(request, usuVO);
				FICHA = FICHA_INASISTENCIA;
				switch (CMD) {
				case Params.CMD_NUEVO:
					session.removeAttribute("filtroInasistencia");
					break;
				}
			} else {
				initInasistenciaSimple(request, usuVO);
				FICHA = FICHA_INASISTENCIA_SIMPLE;
				switch (CMD) {
				case Params.CMD_NUEVO:
					session.removeAttribute("filtroInasistencia");
					break;
				}
			}
			break;
		case ParamVO.FICHA_RETARDO:
			initRetardo(request, usuVO);
			FICHA = FICHA_RETARDO;
			switch (CMD) {
			case Params.CMD_NUEVO:
				session.removeAttribute("filtroInasistencia");
				break;
			}
			break;
		case ParamVO.FICHA_SALIDA_TARDE:
			initSalidaTarde(request, usuVO);
			FICHA = FICHA_SALIDA_TARDE;
			switch (CMD) {
			case Params.CMD_NUEVO:
				session.removeAttribute("filtroInasistencia");
				break;
			}
			break;
		case ParamVO.FICHA_INASISTENCIA_SIMPLE:
			initInasistenciaSimple(request, usuVO);
			FICHA = FICHA_INASISTENCIA_SIMPLE;
			switch (CMD) {
			case Params.CMD_NUEVO:
				session.removeAttribute("filtroInasistencia");
				break;
			}
			break;
		}
		// }catch(Exception e){e.printStackTrace();}
		dispatcher[0] = String.valueOf(Params.FORWARD);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	private void initInasistencia(HttpServletRequest request, Login usuVO) {
		long inst = Long.parseLong(usuVO.getInstId());
		int sede = -99;
		int jor = -99;
		if (usuVO.getSedeId() != null && !usuVO.getSedeId().equals(""))
			sede = Integer.parseInt(usuVO.getSedeId());
		if (usuVO.getJornadaId() != null && !usuVO.getJornadaId().equals(""))
			jor = Integer.parseInt(usuVO.getJornadaId());
		// System.out.println("valors:"+inst+"//"+sede+"//"+jor);
		Recursos.setnumper(usuVO.getLogNumPer());
		Recursos.setnomup(inasistenciaDAO.getnomulp(inst,
				usuVO.getVigencia_inst()));
		// Recursos.Cargar();
		request.setAttribute("lMesesVal",
				inasistenciaDAO.getlismeses(inst, usuVO.getVigencia_inst()));
		request.setAttribute("lMetodologia",
				inasistenciaDAO.getAllMetodologia(inst));
		request.setAttribute("lGrado",
				inasistenciaDAO.getAllGrado(inst, sede, jor));
		request.setAttribute("lGrupo",
				inasistenciaDAO.getAllGrupo(inst, sede, jor));
		request.setAttribute("lPeriodo",
				Recursos.recursoEstatico[Recursos.PERIODO]);
		request.setAttribute("lMes", inasistenciaDAO.getAllMes());
		request.setAttribute("lQuincena", inasistenciaDAO.getAllQuincena());

		FiltroInasistencia filtroInasistencia = (FiltroInasistencia) request
				.getSession().getAttribute("filtroInasistencia");
		if (filtroInasistencia != null) {
			if (filtroInasistencia.getFilMetodologia() != 0
					&& filtroInasistencia.getFilGrado() != -99)
				request.setAttribute("lAsignatura", inasistenciaDAO
						.getAjaxAsignatura(inst,
								filtroInasistencia.getFilMetodologia(),
								filtroInasistencia.getFilGrado()));
		}
	}

	private void initInasistenciaSimple(HttpServletRequest request, Login usuVO) {
		long inst = Long.parseLong(usuVO.getInstId());
		int sede = -99;
		int jor = -99;
		if (usuVO.getSedeId() != null && !usuVO.getSedeId().equals(""))
			sede = Integer.parseInt(usuVO.getSedeId());
		if (usuVO.getJornadaId() != null && !usuVO.getJornadaId().equals(""))
			jor = Integer.parseInt(usuVO.getJornadaId());
		// System.out.println("valors: " + usuVO.getLogNumPer());
		Recursos.setnumper(usuVO.getLogNumPer());
		Recursos.setnomup(inasistenciaDAO.getnomulp(inst,
				usuVO.getVigencia_inst()));
		Recursos.Cargar();
		request.setAttribute("lMesesVal",
				inasistenciaDAO.getlismeses(inst, usuVO.getVigencia_inst()));
		request.setAttribute("lMetodologia",
				inasistenciaDAO.getAllMetodologia(inst));
		request.setAttribute("lGrado",
				inasistenciaDAO.getAllGrado(inst, sede, jor));
		request.setAttribute("lGrupo",
				inasistenciaDAO.getAllGrupo(inst, sede, jor));
		request.setAttribute("lPeriodo",
				Recursos.recursoEstatico[Recursos.PERIODO]);
		request.setAttribute("lMes", inasistenciaDAO.getAllMes());
		request.setAttribute("lQuincena", inasistenciaDAO.getAllQuincena());
	}

	private void initRetardo(HttpServletRequest request, Login usuVO) {
		long inst = Long.parseLong(usuVO.getInstId());
		int sede = -99;
		int jor = -99;
		if (usuVO.getSedeId() != null && !usuVO.getSedeId().equals(""))
			sede = Integer.parseInt(usuVO.getSedeId());
		if (usuVO.getJornadaId() != null && !usuVO.getJornadaId().equals(""))
			jor = Integer.parseInt(usuVO.getJornadaId());
		// System.out.println("valors:"+inst+"//"+sede+"//"+jor);
		Recursos.setnumper(usuVO.getLogNumPer());
		Recursos.setnomup(inasistenciaDAO.getnomulp(inst,
				usuVO.getVigencia_inst()));
		Recursos.Cargar();
		request.setAttribute("lMesesVal",
				inasistenciaDAO.getlismeses(inst, usuVO.getVigencia_inst()));
		request.setAttribute("lMetodologia",
				inasistenciaDAO.getAllMetodologia(inst));
		request.setAttribute("lGrado",
				inasistenciaDAO.getAllGrado(inst, sede, jor));
		request.setAttribute("lGrupo",
				inasistenciaDAO.getAllGrupo(inst, sede, jor));
		request.setAttribute("lPeriodo",
				Recursos.recursoEstatico[Recursos.PERIODO]);
		request.setAttribute("lMes", inasistenciaDAO.getAllMes());
		request.setAttribute("lQuincena", inasistenciaDAO.getAllQuincena());
	}

	private void initSalidaTarde(HttpServletRequest request, Login usuVO) {
		long inst = Long.parseLong(usuVO.getInstId());
		int sede = -99;
		int jor = -99;
		if (usuVO.getSedeId() != null && !usuVO.getSedeId().equals(""))
			sede = Integer.parseInt(usuVO.getSedeId());
		if (usuVO.getJornadaId() != null && !usuVO.getJornadaId().equals(""))
			jor = Integer.parseInt(usuVO.getJornadaId());
		// System.out.println("valors:"+inst+"//"+sede+"//"+jor);
		Recursos.setnumper(usuVO.getLogNumPer());
		Recursos.setnomup(inasistenciaDAO.getnomulp(inst,
				usuVO.getVigencia_inst()));
		Recursos.Cargar();
		request.setAttribute("lMesesVal",
				inasistenciaDAO.getlismeses(inst, usuVO.getVigencia_inst()));
		request.setAttribute("lMetodologia",
				inasistenciaDAO.getAllMetodologia(inst));
		request.setAttribute("lGrado",
				inasistenciaDAO.getAllGrado(inst, sede, jor));
		request.setAttribute("lGrupo",
				inasistenciaDAO.getAllGrupo(inst, sede, jor));
		request.setAttribute("lPeriodo",
				Recursos.recursoEstatico[Recursos.PERIODO]);
		request.setAttribute("lMes", inasistenciaDAO.getAllMes());
		request.setAttribute("lQuincena", inasistenciaDAO.getAllQuincena());
	}
}
