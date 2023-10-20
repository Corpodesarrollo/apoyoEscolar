package siges.adminGrupo.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;

import siges.adminGrupo.dao.AdminGrupoDAO;
import siges.adminGrupo.vo.ParamsVO;
import siges.adminParamsInst.dao.AdminParametroInstDAO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.login.beans.Login;

/**
 * Maneja las peticiones referentes a calculo de valores de los formularios
 * 14/01/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class Ajax extends Service {

	private AdminGrupoDAO gruposDAO = new AdminGrupoDAO(new Cursor());
	private String FICHA_CARGAR;

	public Ajax() {
	}

	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String FICHA = null;
		HttpSession session = request.getSession();
		FICHA_CARGAR = config.getInitParameter("FICHA_AJAX");
		String dispatcher[] = new String[2];
		int CMD = getCmd(request, session);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		Login usuVO = (Login) session.getAttribute("login");
		long inst = 0;
		long metod = 0;
		long sede = 0;
		long jorn = 0;
		long tipoEsp = 0;

		try {
			String inst_ = request.getParameter("cod_inst");
			inst = getLong(inst_);
			String sede_ = request.getParameter("cod_sede");
			sede = getLong(sede_);
			String metod_ = request.getParameter("cod_metod");
			metod = getLong(metod_);
			String jorn_ = request.getParameter("cod_jornada");
			jorn = getLong(jorn_);
			String tipoEsp_ = request.getParameter("cod_tipo_esp");
			tipoEsp = getLong(tipoEsp_);

			// System.out.println("GRUPOS ADMIN: SEDE: "+sede+"  JORNADA: "+jorn+"  METOD: "+metod+"  INST: "+inst);
			switch (TIPO) {
			// GRUPOS 16-04-10 WG
			case ParamsVO.FICHA_GRUPOS:
				FICHA = FICHA_CARGAR;
				switch (CMD) {
				case ParamsVO.CMD_JORN:
					// System.out.println("GRUPOS: ENTRO AJAX JORNADAS: ");
					request.setAttribute("listaJornadas",
							listaJornadas(inst, sede));
					request.setAttribute("ajaxParam", new Integer(
							ParamsVO.CMD_JORN));
					break;
				case ParamsVO.CMD_GRADO:
					// System.out.println("GRUPOS: ENTRO AJAX GRADOS: ");
					request.setAttribute("listaGrados",
							listaGrados(inst, metod));
					request.setAttribute("ajaxParam", new Integer(
							ParamsVO.CMD_GRADO));
					break;
				case ParamsVO.CMD_ESPACIO:
					// System.out.println("GRUPOS: ENTRO AJAX ESPACIO: ");
					request.setAttribute("listaEspacios",
							listaEspacios(inst, sede, tipoEsp));
					request.setAttribute("ajaxParam", new Integer(
							ParamsVO.CMD_ESPACIO));
					break;
				default:
					break;
				}
				break;
			}
			// System.out.println("GRUPOS: FILTRO AJAX GRUPOS: FICHA: " +
			// FICHA);
			dispatcher[0] = String.valueOf(Params.INCLUDE);
			dispatcher[1] = FICHA;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dispatcher;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List listaJornadas(long codInst, long codSede) throws Exception {
		try {
			return gruposDAO.getJornadas(codInst, codSede);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new Exception(e);
		}
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List listaGrados(long inst, long metod) throws Exception {
		try {
			return gruposDAO.getGrados(inst, metod);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new Exception(e);
		}
	}

	public List listaEspacios(long inst, long sede, long tipo) throws Exception {
		try {
			return gruposDAO.getEspaciosFisicos(tipo, inst, sede);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new Exception(e);
		}
	}

	/**
	 * @param inst
	 * @return
	 * @throws Exception
	 */
	public List listaMetodologia(String inst) throws Exception {
		try {
			return gruposDAO.getMetodologia(getLong(inst));
		} catch (Exception e) {
			// d
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new Exception(e);
		}
	}

	/**
	 * @param str
	 * @return
	 */
	private long getLong(String str) throws Exception {
		try {
			return GenericValidator.isLong(str) ? Long.valueOf(str).longValue()
					: -99;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new Exception(e);
		}
	}

}
