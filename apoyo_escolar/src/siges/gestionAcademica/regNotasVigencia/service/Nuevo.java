/**
 * 
 */
package siges.gestionAcademica.regNotasVigencia.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;

import siges.common.service.Service;
import siges.common.vo.ItemVO;
import siges.common.vo.Params;
import siges.common.vo.TipoEvalVO;
import siges.dao.Cursor;
import siges.gestionAcademica.regNotasVigencia.dao.RegNotasVigenciaDAO;
import siges.gestionAcademica.regNotasVigencia.vo.AreaVO;
import siges.gestionAcademica.regNotasVigencia.vo.AsignaturaVO;
import siges.gestionAcademica.regNotasVigencia.vo.EstudianteRegNotasVO;
import siges.gestionAcademica.regNotasVigencia.vo.FiltroRegNotasVO;
import siges.gestionAcademica.regNotasVigencia.vo.InstitucionParametroVO;
import siges.gestionAcademica.regNotasVigencia.vo.ParamsVO;
import siges.login.beans.Login;
import siges.util.Logger;

/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
/**
 * @author desarrollo
 * 
 */
public class Nuevo extends Service {

	public String FICHA_FILTRO_REG_NOTAS;
	private String FICHA_NUEVO_REG_NOTAS;
	private ResourceBundle rb;
	private String contextoTotal;
	private RegNotasVigenciaDAO regNotasVigenciaDAO;
	private Login usuVO;

	/**
	 * 
	 /*
	 * 
	 * @function:
	 * @param config
	 * @throws ServletException
	 *             (non-Javadoc)
	 * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		regNotasVigenciaDAO = new RegNotasVigenciaDAO(new Cursor());
		FICHA_FILTRO_REG_NOTAS = config
				.getInitParameter("FICHA_FILTRO_REG_NOTAS");
		FICHA_NUEVO_REG_NOTAS = config
				.getInitParameter("FICHA_NUEVO_REG_NOTAS");
	}

	/**
	 * Procesa la peticinn
	 * 
	 * @param request
	 *            peticinn
	 * @param response
	 *            respuesta
	 * @return Ruta de redireccinn
	 * @throws ServletException
	 * @throws IOException
	 *             (non-Javadoc)
	 * @see siges.common.service.Service#process(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String dispatcher[] = new String[2];
		rb = ResourceBundle
				.getBundle("siges.gestionAdministrativa.cierreVigencia.bundle.cierreVigencia");
		contextoTotal = context.getRealPath("/");
		String FICHA = null;

		HttpSession session = request.getSession();
		usuVO = (Login) session.getAttribute("login");
		int CMD = getCmd(request, session, ParamsVO.CMD_NUEVO);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		ArrayList s = new ArrayList();
		Logger.print(usuVO.getUsuarioId(), "Ingreso de REGUISTRO DE NOTAS POR VIGENCIA. ",
				7, 1, this.toString());

		// request.setAttribute("prom", s.get(0));
		// request.setAttribute("tipoProm", s.get(1));
		// request.setAttribute("obs", s.get(2));

		// System.out.println(this.getClass().getName() + " TIPO " + TIPO
		// + " CMD " + CMD);

		switch (TIPO) {
		case ParamsVO.FICHA_FILTRO_REG_NOTAS:

			FICHA = FICHA_FILTRO_REG_NOTAS;

			switch (CMD) {

			case ParamsVO.CMD_NUEVO:
				limpiarListados(request.getSession());
				request.getSession().removeAttribute("filtroRegNotasVO");
				iniFiltro(request);
				break;
			case ParamsVO.CMD_GUARDAR:
				buscar(request);
				iniFiltro(request);
				break;

			}

			break;
		case ParamsVO.FICHA_NUEVO_REG_NOTAS:

			FICHA = FICHA_NUEVO_REG_NOTAS;

			switch (CMD) {
			case ParamsVO.CMD_NUEVO:

				nuevoRegNotas(request);
				break;
			case ParamsVO.CMD_BUSCAR:
				buscarRegNotas(request);
				break;
			case ParamsVO.CMD_GUARDAR:
				guardarRegNotas(request, usuVO);
				break;
			case ParamsVO.CMD_GENERAR_HISTORICO:
				buscarRegNotas(request);
				break;
			}
			break;
		}
		dispatcher[0] = String.valueOf(Params.INCLUDE);
		// System.out.println("FICHA " + FICHA);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	/**
	 * Metodo que realiza la busqueda de los estudiantes segnn los parametros
	 * del filtro
	 * 
	 * @param request
	 * @throws ServletException
	 */
	private void buscar(HttpServletRequest request) throws ServletException {
		// System.out.println("buscar");
		try {
			FiltroRegNotasVO filtroRegNotasVO = (FiltroRegNotasVO) request
					.getSession().getAttribute("filtroRegNotasVO");
			if (filtroRegNotasVO != null) {
				// System.out.println("filtroRegNotasVO no es null");

				request.setAttribute("listaEst", regNotasVigenciaDAO
						.getListaEstudiante(filtroRegNotasVO));
				request.getSession().setAttribute("tipoProm1",
						filtroRegNotasVO.getFiltipo() + "");

			} else {
				// System.out.println("filtroRegNotasVO es n ull");
				throw new Exception("filtroRegNotasVO es  ull");
			}
			request.getSession().setAttribute("filtroRegNotasVO",
					filtroRegNotasVO);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "" + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @throws ServletException
	 */
	private void iniFiltro(HttpServletRequest request) throws ServletException {
		// System.out.println("iniFiltro");
		try {
			request.setAttribute("filtroTiposDoc",
					regNotasVigenciaDAO.getTiposDoc());
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "" + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @throws ServletException
	 */
	public void nuevoRegNotas(HttpServletRequest request)
			throws ServletException {
		String codigoEst = request.getParameter("codigoEst");
		try {
			if (codigoEst != null && GenericValidator.isInt(codigoEst)) {
				EstudianteRegNotasVO estudianteRegNotasVO = regNotasVigenciaDAO
						.getNombreEstudiante(Long.parseLong(codigoEst));
				request.getSession().setAttribute("estudianteRegNotasVO",
						estudianteRegNotasVO);
			} else {
				throw new Exception("Codigo Est es null");
			}

			request.getSession().setAttribute("listaVigencia",
					getListaVigenciaInst(Long.parseLong(usuVO.getInstId())));
		} catch (Exception e) {
			request.setAttribute(ParamsVO.SMS, e.getMessage());
		}
	}

	/**
	 * @param codInst
	 * @return
	 * @throws Exception
	 */
	private List getListaVigenciaInst(long codInst) throws Exception {
		// System.out.println("regNotasVigencia : getListaVigenciaInst");
		List lista = new ArrayList();
		try {
			int vigenciaInst = regNotasVigenciaDAO.getVigenciaInst(codInst) - 1;
			for (int i = 2006; i <= vigenciaInst; i++) {
				lista.add(new ItemVO(i, "" + i));
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return lista;
	}

	/**
	 * @param request
	 * @throws ServletException
	 */
	public void buscarRegNotas(HttpServletRequest request)
			throws ServletException {

		FiltroRegNotasVO filtroRegNotasVO = (FiltroRegNotasVO) request
				.getSession().getAttribute("filtroRegNotasVO");
		EstudianteRegNotasVO estudianteRegNotasVO = (EstudianteRegNotasVO) request
				.getSession().getAttribute("estudianteRegNotasVO");
		try {
			if (filtroRegNotasVO != null && estudianteRegNotasVO != null) {
				// obtener promocinn
				ArrayList s = regNotasVigenciaDAO.obtenerDatosPromocion(
						filtroRegNotasVO.getFilinst(),
						filtroRegNotasVO.getFilvigencia(),
						filtroRegNotasVO.getFilgrado(),
						filtroRegNotasVO.getFilmetod(),
						filtroRegNotasVO.getFiltipo(),
						estudianteRegNotasVO.getEstcodigo());
				if (!s.isEmpty()) {
					String a = "";
					request.setAttribute("prom", s.get(0));
					if (s.get(1) != null) {
						a = s.get(1) + "";
					}
					request.setAttribute("obs", a);
					request.setAttribute("tipoProm", s.get(2));
				}
				// hasta aqui

				InstitucionParametroVO listaPeriodos = regNotasVigenciaDAO
						.getListaPeriodos(filtroRegNotasVO);
				request.getSession().setAttribute("listaPeriodos",
						listaPeriodos);

				// listado de Periodos y niveles
				TipoEvalVO insParamsVO = regNotasVigenciaDAO
						.getTipoNivelEval(filtroRegNotasVO);

				List listaArea = regNotasVigenciaDAO.getListaAreas(
						filtroRegNotasVO, estudianteRegNotasVO, insParamsVO,
						listaPeriodos.getInsparnumper());

				for (int i = 0; i < listaArea.size(); i++) {
					AreaVO areaVO = (AreaVO) listaArea.get(i);
					List listaAsigs = regNotasVigenciaDAO.getListaAsignaturas(
							filtroRegNotasVO, areaVO, estudianteRegNotasVO,
							insParamsVO, listaPeriodos.getInsparnumper());
					areaVO.setListaAsig(listaAsigs);
				}

				// listaArea =
				// regNotasVigenciaDAO.getlistaNotas(filtroRegNotasVO,
				// listaArea,estudianteRegNotasVO,listaPeriodos.getInsparnumper()
				// );

				request.getSession().setAttribute("listaArea", listaArea);

				// Escala
				if (insParamsVO.getCod_tipo_eval() == ParamsVO.TIPO_EVAL_ASIG_CONCEPTUAL) {
					List listaEscala = regNotasVigenciaDAO.getEscalaConceptual(filtroRegNotasVO);
					request.getSession().setAttribute("listaEscala",listaEscala);
				}

				if (insParamsVO.getCod_tipo_eval() == ParamsVO.TIPO_EVAL_ASIG_NUM || insParamsVO.getCod_tipo_eval() == ParamsVO.TIPO_EVAL_ASIG_PORCENTUAL) {

					request.getSession().setAttribute("tipoEvalMax",new Double(insParamsVO.getEval_max()));
					request.getSession().setAttribute("tipoEvalMin",new Double(insParamsVO.getEval_min()));

				}
				request.getSession().setAttribute("tipoEval",new Long(insParamsVO.getCod_tipo_eval()));

			} else {
				// System.out.println("Error filtroRegNotasVO ");
				new Exception("Error filtroRegNotasVO ");
			}

			request.setAttribute("listaVigencia",
					getListaVigenciaInst(Long.parseLong(usuVO.getInstId())));
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param usuVO
	 * @throws ServletException
	 */
	public void guardarRegNotas(HttpServletRequest request, Login usuVO)
			throws ServletException {
		List listaArea = (ArrayList) request.getSession().getAttribute(
				"listaArea");

		FiltroRegNotasVO filtroRegNotasVO = (FiltroRegNotasVO) request
				.getSession().getAttribute("filtroRegNotasVO");
		EstudianteRegNotasVO estudianteRegNotasVO = (EstudianteRegNotasVO) request
				.getSession().getAttribute("estudianteRegNotasVO");
		try {
			if (filtroRegNotasVO != null && estudianteRegNotasVO != null) {
				// anadido por camilo
				int e = -2;
				String mensaje = "";
				if (filtroRegNotasVO.getFiltipoprom() != -99
						&& filtroRegNotasVO.getFilprom() != -99) {
					e = regNotasVigenciaDAO.guardarPromocion(
							filtroRegNotasVO.getFilinst(),
							filtroRegNotasVO.getFilvigencia(),
							filtroRegNotasVO.getFilgrado(),
							filtroRegNotasVO.getFilmetod(),
							estudianteRegNotasVO.getEstcodigo(),
							filtroRegNotasVO.getFilprom(),
							filtroRegNotasVO.getFiltipoprom(),
							filtroRegNotasVO.getFilobs(),
							filtroRegNotasVO.getFiltipo());
				}
				if (e > 0) {
					String a = "";
					request.setAttribute("prom", filtroRegNotasVO.getFilprom()
							+ "");
					if (filtroRegNotasVO.getFilobs() != null) {
						a = filtroRegNotasVO.getFilobs();
					}
					request.setAttribute("obs", a);
					request.setAttribute("tipoProm",
							filtroRegNotasVO.getFiltipoprom() + "");

				} else {
					mensaje = "\nLa promocion no fue actualizada, el estudiante no posee esta información para el tipo de promocion.";
				}
				// fin
				List listaAreaTemp = new ArrayList();
				for (int i = 0; i < listaArea.size(); i++) {
					// Ã¡rea
					AreaVO areaVO = (AreaVO) listaArea.get(i);
					// System.out.println(areaVO.getArenombre() + " "
					// + areaVO.getFinalEvalAreNota());
					int contadorAreas = 0;
					int contadorAsignaturas = 0;
					boolean removedAsig = false;
					for (int index = 0; index < areaVO.getNumPeriodos(); index++) {
						String params = appends("notaEst_",
								areaVO.getArecodJerar(), index + 1);
						if (GenericValidator.isDouble(request
								.getParameter(params))) {
							areaVO.setListaEvalAreNotaAdd(index,
									request.getParameter(params));
						} else {
							areaVO.setListaEvalAreNotaAdd(index, "");
							contadorAreas++;
						}
					}// fin for(int index = 0; index < areaVO.getNumPeriodos();
						// index ++ )

					String params = appends("notaEst_",
							areaVO.getArecodJerar(), 7);
					if (GenericValidator.isDouble(request.getParameter(params))) {
						areaVO.setFinalEvalAreNota(request.getParameter(params));
					} else {
						areaVO.setFinalEvalAreNota("");
						contadorAreas++;
					}
					// AIGNATURAS
					List listaAsig = areaVO.getListaAsig();
					List listaAsigTemp = new ArrayList();
					int numPeriodosAsig = 0;
					for (int j = 0; j < listaAsig.size(); j++) {
						AsignaturaVO asigVO = (AsignaturaVO) listaAsig.get(j);
						numPeriodosAsig = asigVO.getNumPeriodos();
						//System.out.println("nombre Asignatura: " +j+ " "+ asigVO.getAsinombre());
						for (int index = 0; index < asigVO.getNumPeriodos(); index++) {
							String params_Aisg = appends("notaEst_",
									asigVO.getAsicodjerar(), index + 1);
							if (GenericValidator.isDouble(request
									.getParameter(params_Aisg))) {
								asigVO.setListaEvalAsiNotaAdd(index,
										request.getParameter(params_Aisg));
							} else { 
								asigVO.setListaEvalAsiNotaAdd(index, "");
								contadorAsignaturas++;
							}
						}// fin for(int index = 0; index <
							// areaVO.getNumPeriodos(); index ++ )
						String params_Aisg = appends("notaEst_",
								asigVO.getAsicodjerar(), 7);
						if (GenericValidator.isDouble(request
								.getParameter(params_Aisg))) {
							asigVO.setFinalEvalAsiNota(request
									.getParameter(params_Aisg));
						} else {
							asigVO.setFinalEvalAsiNota("");
							contadorAsignaturas++;
						}
						if(contadorAsignaturas >= numPeriodosAsig){
							//areaVO.getListaAsig().remove(asigVO);
							listaAsigTemp.add(asigVO);
							removedAsig = true;
						}
					}
					areaVO.getListaAsig().removeAll(listaAsigTemp);
				
					 if(contadorAreas >= areaVO.getNumPeriodos() && removedAsig){
						 //listaArea.remove(areaVO);   
						 listaAreaTemp.add(areaVO);
					 	}
				}// fin for(int i=0;i< listaArea.size();i++ )
				listaArea.removeAll(listaAreaTemp);

				regNotasVigenciaDAO.guardarLogNotas(filtroRegNotasVO,
						listaArea, estudianteRegNotasVO, usuVO);
				regNotasVigenciaDAO.guardarRegNota(filtroRegNotasVO, listaArea,
						estudianteRegNotasVO);

				request.setAttribute(ParamsVO.SMS,
						"informaciónn registrada satisfactoriamente. " + mensaje);
			} else {
				throw new Exception("Listados esta null");
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, e.getMessage());
		}
	}

	private void limpiarListados(HttpSession session) {
		session.removeAttribute("estudianteRegNotasVO");

		session.removeAttribute("listaArea");
		session.removeAttribute("listaVigencia");
		session.removeAttribute("listaSede");
		session.removeAttribute("listaJornada");
		session.removeAttribute("listaMetodo");
		session.removeAttribute("listaGrado");
		session.removeAttribute("listaGrupo");
	}

	private String appends(String str1, long str2, int str3) throws Exception {
		StringBuffer strBuff = new StringBuffer();
		return strBuff.append(str1).append(str2).append("_").append(str3)
				.toString();
	}
}
