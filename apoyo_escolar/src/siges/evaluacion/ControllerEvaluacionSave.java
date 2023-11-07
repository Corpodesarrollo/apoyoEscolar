package siges.evaluacion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import siges.adminParamsInst.dao.AdminParametroInstDAO;
import siges.adminParamsInst.vo.InstParVO;
import siges.common.vo.ItemVO;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.estudiante.dao.EstudianteDAO;
import siges.evaluacion.beans.FiltroBeanEvaluacion;
import siges.evaluacion.beans.FiltroComportamiento;
import siges.evaluacion.beans.ParamsVO;
import siges.evaluacion.beans.TipoEvalVO;
import siges.evaluacion.dao.Evaluacion2DAO;
import siges.evaluacion.dao.EvaluacionDAO;
import siges.login.beans.Login;
import siges.util.Logger;
import util.BitacoraCOM;
import util.EstudianteEvalDto;
import util.LogEvaluacionDto;

/**
 * Nombre: ControllerEvaluacionSave<BR>
 * Descripcinn: Controla la negociacinn para buscar los datos de evaluacion e
 * ingresar y actualizar la evaluacinn en linea de logros,
 * descriptores,areas,asignaturas, promocion <br>
 * Funciones de la pngina: Ingresa a la bese de datos para buscar los
 * estudiantes a evaluar y para ingresar las notas dadas por el usuario en el
 * formulario de evaluacinn<BR>
 * Entidades afectadas: Todas las tablas de evaluacion y promocion de los
 * estudiantes <BR>
 * Fecha de modificacinn: 20/07/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */
public class ControllerEvaluacionSave extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private String mensaje;

	private Cursor cursor;

	private EvaluacionDAO evaluacionDAO;//

	private Evaluacion2DAO evaluacion2DAO;//

	private ResourceBundle rb;

	/**
	 * Procesa la peticion HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public String process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Login login = null;
		FiltroBeanEvaluacion filtroEvaluacion = null;
		FiltroComportamiento filtroComportamiento = null;
		int tipo = 1;
		rb = ResourceBundle.getBundle("siges.evaluacion.bundle.evaluacion");
		String sig0, sig, sig2, sig3, sig4, sig5, sig6, sigComp;
		String ant;
		String er;
		String home;
		String boton;
		sig0 = getServletConfig().getInitParameter("sig0");
		sig = getServletConfig().getInitParameter("sig");
		sig2 = getServletConfig().getInitParameter("sig2");
		sig3 = getServletConfig().getInitParameter("sig3");
		sig4 = getServletConfig().getInitParameter("sig4");
		sig5 = getServletConfig().getInitParameter("sig5");
		sig6 = getServletConfig().getInitParameter("sig6");
		sigComp = getServletConfig().getInitParameter("sigComp");
		ant = getServletConfig().getInitParameter("ant");
		er = getServletContext().getInitParameter("error");
		home = getServletContext().getInitParameter("home");
		cursor = new Cursor();
		mensaje = null;
		evaluacionDAO = new EvaluacionDAO(cursor);
		evaluacion2DAO = new Evaluacion2DAO(cursor);
		try {
			boton = (request.getParameter("cmd") != null) ? request
					.getParameter("cmd") : new String("");
			if (!asignarBeans(request)) {
				setMensaje("Error capturando datos de sesinn para el usuario");
				request.setAttribute("mensaje", mensaje);
				return er;
			}
			// traer de session los beans de datos
			login = (Login) session.getAttribute("login");
			filtroEvaluacion = (FiltroBeanEvaluacion) session.getAttribute("filtroEvaluacion");
			if (filtroEvaluacion != null) {
				filtroEvaluacion.setMunicipio(login.getMunId());
				filtroEvaluacion.setLocalidad(login.getLocId());
				filtroEvaluacion.setInstitucion(login.getInstId());
				filtroEvaluacion.setJornada(login.getJornadaId());
				filtroEvaluacion.setSede(login.getSedeId());
				filtroEvaluacion.setVigencia(evaluacionDAO.getVigencia());
				filtroEvaluacion.setFilPlanEstudios(evaluacionDAO
						.getEstadoPlan(Long.parseLong(login.getInstId())));
				session.setAttribute("filtroEvaluacion", filtroEvaluacion);
			}
			if (session.getAttribute("filtroComportamiento") != null)
				filtroComportamiento = (FiltroComportamiento) session
						.getAttribute("filtroComportamiento");
			else
				filtroComportamiento = null;

			if (filtroComportamiento != null) {
				filtroComportamiento.setFilInstitucion(Long.parseLong(login
						.getInstId()));
				filtroComportamiento.setFilSede(Integer.parseInt(login
						.getSedeId()));
				filtroComportamiento.setFilJornada(Integer.parseInt(login
						.getJornadaId()));
			}
			if (request.getParameter("tipo") == null
					|| request.getParameter("tipo").equals("")) {
				setMensaje("Acceso denegado no hay una ficha definida");
				request.setAttribute("mensaje", getMensaje());
				return er;
			}
			tipo = Integer.parseInt((String) request.getParameter("tipo"));
			//System.out.println("En el Save="+tipo+"//"+boton);
			if (boton.equals("Buscar")) {
				switch (tipo) {
				case 1:// logro
					asignarListaLogro(request, login, filtroEvaluacion);
					return (sig);
				case ParamsVO.EVAL_ASI:// asignatura
					asignarListaAsignatura(request, login, filtroEvaluacion);
					session.setAttribute("minimoinasistenciaasignatura",new Integer(Integer.parseInt(String.valueOf(consultartotalasistenciasporasignatura(request, login, filtroEvaluacion)))));
					return (sig2);
				case 3:// desc
					asignarListaDescriptor(request, login, filtroEvaluacion);
					return (sig3);
				case ParamsVO.EVAL_ARE:// area
					asignarListaArea(request, login, filtroEvaluacion);
					session.setAttribute("minimoinasistenciaarea",new Integer(Integer.parseInt(String.valueOf(consultartotalasistenciasporarea(request, login, filtroEvaluacion)))));
					return (sig4);
				case 5:// recu
					asignarListaRecuperacion(request, login, filtroEvaluacion);
					return (sig5);
				case ParamsVO.EVAL_PRE:// preescolar
					asignarListaPreescolar(request, login, filtroComportamiento);
					return (sig6);
				case ParamsVO.EVAL_COMP:
					asignarListaComportamiento(request, login,
							filtroComportamiento);
					return (sigComp);
				}
			}
			
			if (boton.equals("Aceptar")) {
				switch (tipo) {
				case 1:// logros
					if (!insertarLogro(request, login, filtroEvaluacion)) {
						request.setAttribute("mensaje", mensaje);
					} else{
						request.setAttribute("mensaje", "Los datos fueron registrados satisfactoriamente");
					}
				case ParamsVO.EVAL_ASI:// asig
					if (!insertarAsignatura(request, login, filtroEvaluacion)){
						request.setAttribute("mensaje", mensaje);
					} else{
						request.setAttribute("mensaje", "Los datos fueron registrados satisfactoriamente");
						BitacoraCOM.insertarBitacora(Long.parseLong(login.getInstId()), 
								Integer.parseInt(login.getJornadaId()), 2, 
								login.getPerfil(), Integer.parseInt(login.getSedeId()), 
								30, 4, login.getUsuarioId(), this.stringEvalAsignatura(notasOld, filtroEvaluacion));
					}
				case 3:// desc
					if (!insertarDescriptor(request, login, filtroEvaluacion)) {
						request.setAttribute("mensaje", mensaje);
					} else {
						request.setAttribute("mensaje", "Los datos fueron registrados satisfactoriamente");
					}
				case ParamsVO.EVAL_ARE:// area
					if (!insertarArea(request, login, filtroEvaluacion)) {
						request.setAttribute("mensaje", mensaje);
					} else {
						request.setAttribute("mensaje", "Los datos fueron registrados satisfactoriamente");
					}
				case 5:// recuperacion
					if (!insertarRecuperacion(request, login, filtroEvaluacion)) {
						request.setAttribute("mensaje", mensaje);
					} else{
						request.setAttribute("mensaje", "Los datos fueron registrados satisfactoriamente");
					}
				case ParamsVO.EVAL_PRE:// PREESCOLAR
					if (!insertarPreescolar(request, login, filtroComportamiento)) {
						request.setAttribute("mensaje", mensaje);
					} else {
						request.setAttribute("mensaje", "Los datos fueron registrados satisfactoriamente");
					}
				case ParamsVO.EVAL_COMP:
					if (!insertarComportamiento(request, login, filtroComportamiento)) {
						request.setAttribute("mensaje", mensaje);
					} else {
						request.setAttribute("mensaje", "Los datos fueron registrados satisfactoriamente");
					}
				}
				return sig0 += "?tipo=" + tipo;
			}
			if (boton.equals("Cancelar")) {
				borrarBeans(request);
				sig = home;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sig0 += "?tipo=" + tipo;
	}
	
	private String stringEvalAsignatura(List<EstudianteEvalDto> notasOld, FiltroBeanEvaluacion filtroEvaluacion){
		try {
			LogEvaluacionDto logEvaluacionDto = new LogEvaluacionDto();
			List<LogEvaluacionDto> list = new ArrayList<LogEvaluacionDto>();
			String[] notas = filtroEvaluacion.getNota();
			for (EstudianteEvalDto nota : notasOld) {
				LogEvaluacionDto dto = new LogEvaluacionDto();
				dto.setNumeroIdentificacion(nota.getNumeroIdentificacion());
				dto.setNombreCompleto(nota.getNombreCompleto());
				dto.setGrado(filtroEvaluacion.getGrado_());
				dto.setGrupo(filtroEvaluacion.getGrupo_());
				dto.setPeriodo(Integer.parseInt(filtroEvaluacion.getPeriodo()));
				dto.setMateria(filtroEvaluacion.getAsignatura_());
				dto.setNotaAnterior(nota.getNota());
				for (String newNota: notas) {
					String[] param = newNota.replace('|', ':').split(":");
					if (nota.getCodigo()==Long.parseLong(param[0].trim())) {
						dto.setNotaActualizada(Float.parseFloat(param[1].trim()));
						dto.setNotaRecuperada(Float.parseFloat(param[2].trim()));
					}
				}
				list.add(dto);
			}
			Gson gson = new Gson();
			return gson.toJson(list);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	/**
	 * Busca los estudiantes y descriptores para la evaluciacion de descriptores
	 * y pone los datos en la session de usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void asignarListaDescriptor(HttpServletRequest request, Login login,
			FiltroBeanEvaluacion filtroEvaluacion) throws ServletException,
			IOException {
		// String s;
		HttpSession session = request.getSession();
		Collection co, fil;
		try {
			String area = filtroEvaluacion.getArea().substring(
					filtroEvaluacion.getArea().lastIndexOf("|") + 1,
					filtroEvaluacion.getArea().length());
			String grupo = filtroEvaluacion.getGrupo().substring(
					filtroEvaluacion.getGrupo().lastIndexOf("|") + 1,
					filtroEvaluacion.getGrupo().length());
			// DESCRIPTORES VALORATIVOS
			fil = evaluacionDAO.getDescriptor(filtroEvaluacion);
			int max = Integer.parseInt(rb.getString("maximosDescriptores"));
			if (fil != null && max != -1 && max < fil.size()) {
				request.setAttribute("evalMax", "1");
				return;
			}
			request.setAttribute("evalMax", "0");
			session.setAttribute("filtroDescriptores", fil);
			// ESTUDIANTES
			co = evaluacionDAO.getEstudiantes(filtroEvaluacion);
			session.setAttribute("filtroResultado", co);
			// JERARQUIA DEL GRUPO AL QUE PERTENECE
			filtroEvaluacion.setJerarquiagrupo(evaluacionDAO
					.getJerarquiaGrupo(co));
			// REVISAR SI ESTA O NO ABIERTA PARA EDICION DEL GRUPO
			filtroEvaluacion.setCerrar(evaluacionDAO
					.getEstadoNotasArea(filtroEvaluacion));
			// RESTRICCIONES DE HORARIO PARA EL AREA.
			filtroEvaluacion.setLectura(evaluacionDAO.getEstadoHorarioArea(
					filtroEvaluacion, login));
			// NOTAS QUE YA ESTAN EN LA TABLA DE EVALUACION
			Collection c = evaluacionDAO.getNotasDesc(filtroEvaluacion);
			int[] zz = { 0 };
			int y = 0, z = 0;
			String valor = "", valor_ = "";
			String[][] filtro = evaluacionDAO.getFiltroMatriz(fil);
			String[] estu = evaluacionDAO.getFiltroArray(co, zz);
			String[][] not = evaluacionDAO.getFiltroMatriz(c);
			if (estu != null) {
				String[] estunot = new String[estu.length];
				String[] estunot_ = new String[estu.length];
				for (int m = 0; m < (estu != null ? estu.length : 0); m++) {
					estunot[m] = estu[m] + "|";
					estunot_[m] = estu[m] + "|";
					valor = valor_ = "";
					for (y = 0; y < (not != null ? not.length : 0); y++) {
						if (estu[m].equals(not[y][0])) {
							valor += not[y][2] + ",";
							for (z = 0; z < (filtro != null ? filtro.length : 0); z++) {
								if (filtro[z][0].equals(not[y][2])) {
									valor_ += filtro[z][1] + ",";
									break;
								}
							}
						}
					}
					if (valor_.lastIndexOf(",") != -1)
						estunot_[m] += valor_.substring(0,
								valor_.lastIndexOf(","));
					if (valor.lastIndexOf(",") != -1)
						estunot[m] += valor
								.substring(0, valor.lastIndexOf(","));
				}
				filtroEvaluacion.setNota(estunot);
				filtroEvaluacion.setMotivo(estunot_);
				filtroEvaluacion.setActualizar(estunot);
			}
			Logger.print(
					login.getUsuarioId(),
					"Evaluacinn Descriptor Inst:"
							+ filtroEvaluacion.getInstitucion() + " Sede:"
							+ filtroEvaluacion.getSede() + " Jorn:"
							+ filtroEvaluacion.getJornada() + " Met:"
							+ filtroEvaluacion.getMetodologia() + " Gra:"
							+ filtroEvaluacion.getGrado() + " Grupo:" + grupo
							+ " Area:" + area + " TipoDesc:"
							+ filtroEvaluacion.getDescriptor() + " Per:"
							+ filtroEvaluacion.getPeriodo(),
					"Consulta a asignacinn de Descriptores: Periodo '"
							+ filtroEvaluacion.getPeriodo_()
							+ "' Metodologia '"
							+ filtroEvaluacion.getMetodologia_() + "', Ã¡rea '"
							+ filtroEvaluacion.getArea_() + "', Grado '"
							+ filtroEvaluacion.getGrado() + "', Grupo '"
							+ grupo + "', Descriptor '"
							+ filtroEvaluacion.getDescriptor_() + "'",
					filtroEvaluacion.getPeriodo(), 2, 1, this.toString());
		} catch (Exception th) {
			throw new ServletException(th);
		}
	}

	/**
	 * Busca los estudiantes y descriptores para la evaluciacion de nrea y pone
	 * los datos en la session de usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void asignarListaArea(HttpServletRequest request, Login login,
			FiltroBeanEvaluacion filtroEvaluacion) throws ServletException,
			IOException {
		String s;
		HttpSession session = request.getSession();
		Collection[] col = new Collection[4];
		try {

			/*
			 * CAMBIO PARA CARGAR TIPO DE EVALUACION
			 */
			TipoEvalVO tipoEval = evaluacionDAO.getTipoEval(
					filtroEvaluacion.getMetodologia(),
					filtroEvaluacion.getGrado(), login);

			// ESTUDIANTES
			String area = filtroEvaluacion.getArea().substring(
					filtroEvaluacion.getArea().lastIndexOf('|') + 1,
					filtroEvaluacion.getArea().length());
			String grupo = filtroEvaluacion.getGrupo().substring(
					filtroEvaluacion.getGrupo().lastIndexOf("|") + 1,
					filtroEvaluacion.getGrupo().length());
			col[0] = evaluacionDAO.getEstudiantesArea(filtroEvaluacion,
					tipoEval.getCod_tipo_eval());
			session.setAttribute("filtroResultado", col[0]);
			// VALIDACION DE CIERRE DEL AREA
			filtroEvaluacion.setCerrar(evaluacionDAO
					.getEstadoNotasArea(filtroEvaluacion));
			// RESTRICCIONES DE HORARIO PARA EL AREA.
			filtroEvaluacion.setLectura(evaluacionDAO.getEstadoHorarioArea(
					filtroEvaluacion, login));
			// ESCALA
			if (tipoEval.getCod_tipo_eval() == ParamsVO.TIPO_EVAL_CONCEPTUAL) {
				col[1] = evaluacionDAO.getEscalaConceptual(
						filtroEvaluacion.getMetodologia(),
						filtroEvaluacion.getGrado(), login);
				session.setAttribute("filtroNota2", col[1]);
			}

			// System.out.println("***************************************************************************TIPOEVAL : ");
			session.setAttribute("tipoEval",
					new Long(tipoEval.getCod_tipo_eval()));
			if (tipoEval.getCod_tipo_eval() == ParamsVO.TIPO_EVAL_NUM
					|| tipoEval.getCod_tipo_eval() == ParamsVO.TIPO_EVAL_POR) {
				session.setAttribute("modo_eval",
						new Integer(tipoEval.getModo_eval()));
				session.setAttribute("tipoEvalMax",
						new Double(tipoEval.getEval_max()));
				session.setAttribute("tipoEvalMin",
						new Double(tipoEval.getEval_min()));
				session.setAttribute("tipoEvalAprobMin",
						new Double(tipoEval.getTipoEvalAprobMin()));
			}

			Logger.print(
					login.getUsuarioId(),
					"Evaluacinn Area Inst:" + filtroEvaluacion.getInstitucion()
							+ " Sede:" + filtroEvaluacion.getSede() + " Jorn:"
							+ filtroEvaluacion.getJornada() + " Met:"
							+ filtroEvaluacion.getMetodologia() + " Gra:"
							+ filtroEvaluacion.getGrado() + " Grupo:" + grupo
							+ " Area:" + area + " Per:"
							+ filtroEvaluacion.getPeriodo(),
					"Consulta a evaluacinn de Area: Periodo '"
							+ filtroEvaluacion.getPeriodo_()
							+ "', Metodologia '"
							+ filtroEvaluacion.getMetodologia_() + "', Area '"
							+ filtroEvaluacion.getArea_() + "', Grado '"
							+ filtroEvaluacion.getGrado_() + "', Grupo '"
							+ grupo + "'", filtroEvaluacion.getPeriodo_(), 2,
					1, this.toString());
		} catch (Exception th) {
			throw new ServletException(th);
		}
	}

	/**
	 * Busca los estudiantes para la evaluciacion de Preescolar y pone los datos
	 * en la session de usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void asignarListaPreescolar(HttpServletRequest request, Login login,
			FiltroComportamiento filtroComportamiento) throws ServletException,
			IOException {
		try {
			// System.out.println("Dimension: "+filtroComportamiento.getFilDimension());
			// estudiante con nota
			/*
			 * CAMBIO PARA CARGAR TIPO DE EVALUACION
			 */
			TipoEvalVO tipoEval = evaluacionDAO.getTipoEval(
					String.valueOf(filtroComportamiento.getFilMetodologia()),
					String.valueOf(filtroComportamiento.getFilGrado()), login);

			List l = evaluacion2DAO
					.getEstudianteDimension(filtroComportamiento);
			request.setAttribute("estudiantesDim", l);
			// escala valorativa
			l = evaluacion2DAO.getEscalaComportamiento();
			request.setAttribute("escalaDim", l);
			// REVISAR SI ESTA O NO ABIERTA PARA EDICION DEL GRUPO
			filtroComportamiento.setFilCerrar(evaluacion2DAO
					.getEstadoCierreDim(filtroComportamiento));
			// RESTRICCIONES DE HORARIO PARA EL AREA.
			//@Mcuellar: Para dimensiones no se debe validar la restriccion de horario			
//			filtroComportamiento.setFilLectura(evaluacion2DAO.getEstadoHorarioDim(filtroComportamiento, login));
			filtroComportamiento.setFilLectura(0);
			Logger.print(
					login.getUsuarioId(),
					"Evaluacinn Preescolar Inst:"
							+ filtroComportamiento.getFilInstitucion()
							+ " Sede:" + filtroComportamiento.getFilSede()
							+ " Jorn:" + filtroComportamiento.getFilJornada()
							+ " Met:"
							+ filtroComportamiento.getFilMetodologia()
							+ " Gra:" + filtroComportamiento.getFilGrado()
							+ " Grupo:" + filtroComportamiento.getFilGrupo()
							+ " Dim:" + filtroComportamiento.getFilDimension()
							+ " Per:" + filtroComportamiento.getFilPeriodo(),
					"Consulta a  evaluacinn de Preescolar:  Periodo '"
							+ filtroComportamiento.getFilPeriodo()
							+ "', Metodologia '"
							+ filtroComportamiento.getFilMetodologia()
							+ "', Dimensinn '"
							+ filtroComportamiento.getFilDimension()
							+ "', Grado '" + filtroComportamiento.getFilGrado()
							+ "', Grupo '" + filtroComportamiento.getFilGrupo()
							+ "'",
					String.valueOf(filtroComportamiento.getFilPeriodo()), 2, 1,
					this.toString());

			if (tipoEval != null) {
				System.out
						.println("NO ES NULO SE PUEDE VALIDAR TIPO PREESCOLAR: "
								+ tipoEval.getTipo_eval_prees());
				request.getSession().setAttribute("TIPO_EVAL_PREESCOLAR",
						new Integer(tipoEval.getTipo_eval_prees()));
			} else {
				request.getSession().setAttribute("TIPO_EVAL_PREESCOLAR",
						new Integer(1));
			}

		} catch (Exception th) {
			th.printStackTrace();
			throw new ServletException(th);
		}
	}

	/*
	 * public void asignarListaPreescolar(HttpServletRequest request, Login
	 * login, FiltroBeanEvaluacion filtroEvaluacion) throws ServletException,
	 * IOException { String s; HttpSession session = request.getSession();
	 * Collection[] col = new Collection[4]; List escala = null; try { //
	 * ESTUDIANTES String grupo =
	 * filtroEvaluacion.getGrupo().substring(filtroEvaluacion
	 * .getGrupo().lastIndexOf("|") + 1, filtroEvaluacion.getGrupo().length());
	 * col[0] = evaluacionDAO.getEstudiantes(filtroEvaluacion);
	 * session.setAttribute("filtroResultado", col[0]); // JERARQUIA DEL GRUPO
	 * AL QUE PERTENECE
	 * filtroEvaluacion.setJerarquiagrupo(evaluacionDAO.getJerarquiaGrupo
	 * (col[0])); // REVISAR SI ESTA O NO ABIERTA PARA EDICION DEL GRUPO
	 * filtroEvaluacion
	 * .setCerrar(evaluacionDAO.getEstadoNotasPree(filtroEvaluacion)); //
	 * RESTRICCIONES DE HORARIO PARA EL AREA.
	 * filtroEvaluacion.setLectura(evaluacionDAO
	 * .getEstadoHorarioArea(filtroEvaluacion, login)); // NOTAS QUE YA ESTAN EN
	 * LA TABLA DE EVALUACION DE PREESCOLAR col[2] =
	 * evaluacionDAO.getNotasPree(filtroEvaluacion);
	 * filtroEvaluacion.setNota(evaluacionDAO.getFiltroArray(col[2])); //
	 * filtroEvaluacion.setActualizar(filtroEvaluacion.getNota());
	 * 
	 * // Escala valorativa de asignatura
	 * escala=evaluacion2DAO.getEscalaComportamiento();
	 * request.setAttribute("escalaDimension",escala);
	 * Logger.print(login.getUsuarioId(), "Evaluacinn Preescolar Inst:" +
	 * filtroEvaluacion.getInstitucion() + " Sede:" + filtroEvaluacion.getSede()
	 * + " Jorn:" + filtroEvaluacion.getJornada() + " Gra:" +
	 * filtroEvaluacion.getGrado() + " Grupo:" + grupo + " Dim:" +
	 * filtroEvaluacion.getArea() + " Per:" + filtroEvaluacion.getPeriodo(), 2,
	 * 1, this.toString()); } catch (Exception th) { throw new
	 * ServletException(th); } }
	 */
	/**
	 * Ingresa o actualiza la evaluacinn de logros de los estudiantes del
	 * formulario de evaluacinn
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean insertarLogro(HttpServletRequest request, Login login,
			FiltroBeanEvaluacion filtroEvaluacion) throws ServletException,
			IOException {
		if (!evaluacionDAO.insertarEvalLogro(filtroEvaluacion)) {
			setMensaje(evaluacionDAO.getMensaje());
			return false;
		}
		String grupo = filtroEvaluacion.getGrupo().substring(
				filtroEvaluacion.getGrupo().lastIndexOf("|") + 1,
				filtroEvaluacion.getGrupo().length());
		String asig = filtroEvaluacion.getAsignatura().substring(
				filtroEvaluacion.getAsignatura().lastIndexOf("|") + 1,
				filtroEvaluacion.getAsignatura().length());
		Logger.print(
				login.getUsuarioId(),
				"Evaluacinn Logro Inst:" + filtroEvaluacion.getInstitucion()
						+ " Sede:" + filtroEvaluacion.getSede() + " Jorn:"
						+ filtroEvaluacion.getJornada() + " Met:"
						+ filtroEvaluacion.getMetodologia() + " Gra:"
						+ filtroEvaluacion.getGrado() + " Grupo:" + grupo
						+ " Asig:" + asig + " Per:"
						+ filtroEvaluacion.getPeriodo(),
				"Registro de Evaluacinn de Logro Periodo '"
						+ filtroEvaluacion.getPeriodo_() + "', Metodologia '"
						+ filtroEvaluacion.getMetodologia_()
						+ "', Asignatura '" + filtroEvaluacion.getAsignatura_()
						+ "', Grado '" + filtroEvaluacion.getGrado_()
						+ "', Grupo '" + grupo + "'",
				filtroEvaluacion.getPeriodo(), 6, 1, this.toString());
		return true;
	}

	/**
	 * Ingresa o actualiza la evaluacinn de logros de los estudiantes del
	 * formulario de evaluacinn
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean insertarRecuperacion(HttpServletRequest request,
			Login login, FiltroBeanEvaluacion filtroEvaluacion)
			throws ServletException, IOException {
		if (!evaluacionDAO.insertarEvalRecuperacion(filtroEvaluacion)) {
			setMensaje(evaluacionDAO.getMensaje());
			return false;
		}
		String grupo = filtroEvaluacion.getGrupo().substring(
				filtroEvaluacion.getGrupo().lastIndexOf("|") + 1,
				filtroEvaluacion.getGrupo().length());
		String asig = filtroEvaluacion.getAsignatura().substring(
				filtroEvaluacion.getAsignatura().lastIndexOf("|") + 1,
				filtroEvaluacion.getAsignatura().length());
		Logger.print(
				login.getUsuarioId(),
				"Recuperacinn Logro Inst:" + filtroEvaluacion.getInstitucion()
						+ " Sede:" + filtroEvaluacion.getSede() + " Jorn:"
						+ filtroEvaluacion.getJornada() + " Met:"
						+ filtroEvaluacion.getMetodologia() + " Gra:"
						+ filtroEvaluacion.getGrado() + " Grupo:" + grupo
						+ " Asig:" + asig + " PerAct:"
						+ filtroEvaluacion.getPeriodo() + " PerRec:"
						+ filtroEvaluacion.getPeriodo2(),
				"Registro de recuperacinn de Logro Periodo Actual '"
						+ filtroEvaluacion.getPeriodo_()
						+ "', Periodo Recuperado '"
						+ filtroEvaluacion.getPeriodo2() + "', Metodologia '"
						+ filtroEvaluacion.getMetodologia_()
						+ "', Asignatura '" + filtroEvaluacion.getAsignatura_()
						+ "', Grado '" + filtroEvaluacion.getGrado_()
						+ "', Grupo '" + grupo + "'",
				filtroEvaluacion.getPeriodo(), 6, 1, this.toString());
		return true;
	}

	/**
	 * Ingresa o actualiza la evaluacinn de asignaturas de los estudiantes del
	 * formulario de evaluacinn
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean insertarAsignatura(HttpServletRequest request, Login login,
			FiltroBeanEvaluacion filtroEvaluacion) throws ServletException,
			IOException {
		TipoEvalVO tipoEval = null;
		try {
			tipoEval = evaluacionDAO.getTipoEval(
					filtroEvaluacion.getMetodologia(),
					filtroEvaluacion.getGrado(), login);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!evaluacionDAO.insertarEvalAsignatura(filtroEvaluacion, tipoEval,
				login.getLogNivelEval())) {
			setMensaje(evaluacionDAO.getMensaje());
			return false;
		}
		String asig = filtroEvaluacion.getAsignatura().substring(
				filtroEvaluacion.getAsignatura().lastIndexOf('|') + 1,
				filtroEvaluacion.getAsignatura().length());
		String grupo = filtroEvaluacion.getGrupo().substring(
				filtroEvaluacion.getGrupo().lastIndexOf("|") + 1,
				filtroEvaluacion.getGrupo().length());
		Logger.print(
				login.getUsuarioId(),
				"Evaluacinn Asignatura Inst:"
						+ filtroEvaluacion.getInstitucion() + " Sede:"
						+ filtroEvaluacion.getSede() + " Jorn:"
						+ filtroEvaluacion.getJornada() + " Met:"
						+ filtroEvaluacion.getMetodologia() + " Gra:"
						+ filtroEvaluacion.getGrado() + " Grupo:" + grupo
						+ " Asig:" + asig + " Per:"
						+ filtroEvaluacion.getPeriodo(),
				"Registro de evaluacinn de Asignatura: Periodo '"
						+ filtroEvaluacion.getPeriodo_() + "', Metodologia '"
						+ filtroEvaluacion.getMetodologia_()
						+ "', Asignatura '" + filtroEvaluacion.getAsignatura_()
						+ "', Grado '" + filtroEvaluacion.getGrado_()
						+ "', Grupo '" + grupo + "'",
				filtroEvaluacion.getPeriodo(), 6, 1, this.toString());
		return true;
	}

	/**
	 * Ingresa o actualiza la evaluacinn de descriptores de los estudiantes del
	 * formulario de evaluacinn
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean insertarDescriptor(HttpServletRequest request, Login login,
			FiltroBeanEvaluacion filtroEvaluacion) throws ServletException,
			IOException {
		if (!evaluacionDAO.insertarEvalDescriptor(filtroEvaluacion)) {
			setMensaje(evaluacionDAO.getMensaje());
			return false;
		}
		String area = filtroEvaluacion.getArea().substring(
				filtroEvaluacion.getArea().lastIndexOf("|") + 1,
				filtroEvaluacion.getArea().length());
		String grupo = filtroEvaluacion.getGrupo().substring(
				filtroEvaluacion.getGrupo().lastIndexOf("|") + 1,
				filtroEvaluacion.getGrupo().length());
		Logger.print(
				login.getUsuarioId(),
				"Evaluacinn Descriptor Inst:"
						+ filtroEvaluacion.getInstitucion() + " Sede:"
						+ filtroEvaluacion.getSede() + " Jorn:"
						+ filtroEvaluacion.getJornada() + " Met:"
						+ filtroEvaluacion.getMetodologia() + " Gra:"
						+ filtroEvaluacion.getGrado() + " Grupo:" + grupo
						+ " Area:" + area + " TipoDesc:"
						+ filtroEvaluacion.getDescriptor() + " Per:"
						+ filtroEvaluacion.getPeriodo(),
				"Registro de evaluacinn de descriptor: Periodo '"
						+ filtroEvaluacion.getPeriodo_() + "', Metodologia '"
						+ filtroEvaluacion.getMetodologia_()
						+ "', Descriptor '" + filtroEvaluacion.getDescriptor_()
						+ "', Grado '" + filtroEvaluacion.getGrado_()
						+ "', Grupo '" + grupo + "', Area '"
						+ filtroEvaluacion.getArea_() + "'",
				filtroEvaluacion.getPeriodo(), 6, 1, this.toString());
		return true;
	}

	/**
	 * Ingresa o actualiza la evaluacinn de Area de los estudiantes del
	 * formulario de evaluacinn
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean insertarArea(HttpServletRequest request, Login login,
			FiltroBeanEvaluacion filtroEvaluacion) throws ServletException,
			IOException {
		if (!evaluacionDAO.insertarEvalArea(filtroEvaluacion)) {
			setMensaje(evaluacionDAO.getMensaje());
			return false;
		}
		String area = filtroEvaluacion.getArea().substring(
				filtroEvaluacion.getArea().lastIndexOf('|') + 1,
				filtroEvaluacion.getArea().length());
		String grupo = filtroEvaluacion.getGrupo().substring(
				filtroEvaluacion.getGrupo().lastIndexOf("|") + 1,
				filtroEvaluacion.getGrupo().length());
		Logger.print(
				login.getUsuarioId(),
				"Evaluacinn Area Inst:" + filtroEvaluacion.getInstitucion()
						+ " Sede:" + filtroEvaluacion.getSede() + " Jorn:"
						+ filtroEvaluacion.getJornada() + " Met:"
						+ filtroEvaluacion.getMetodologia() + " Gra:"
						+ filtroEvaluacion.getGrado() + " Grupo:" + grupo
						+ " Area:" + area + " Per:"
						+ filtroEvaluacion.getPeriodo(),
				"Registro de evaluacinn de Ã¡rea: Periodo '"
						+ filtroEvaluacion.getPeriodo_() + "', Metodologia '"
						+ filtroEvaluacion.getMetodologia_() + "', Area '"
						+ filtroEvaluacion.getArea_() + "', Grado '"
						+ filtroEvaluacion.getGrado_() + "', Grupo '" + grupo
						+ "'", filtroEvaluacion.getPeriodo(), 6, 1,
				this.toString());
		return true;
	}

	/**
	 * Ingresa o actualiza la evaluacinn de preescolar de los estudiantes del
	 * formulario de evaluacinn
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean insertarPreescolar(HttpServletRequest request, Login login,
			FiltroComportamiento filtroComportamiento) throws ServletException,
			IOException {
		try {
			// System.out.println("Valor de cierre:"+filtroComportamiento.getFilCerrar());
			evaluacion2DAO.insertarEvalDimension(filtroComportamiento);
			Logger.print(
					login.getUsuarioId(),
					"Evaluacinn Preescolar Inst:"
							+ filtroComportamiento.getFilInstitucion()
							+ " Sede:" + filtroComportamiento.getFilSede()
							+ " Jorn:" + filtroComportamiento.getFilJornada()
							+ " Met:"
							+ filtroComportamiento.getFilMetodologia()
							+ " Gra:" + filtroComportamiento.getFilGrado()
							+ " Grupo:" + filtroComportamiento.getFilGrupo()
							+ " Dim:" + filtroComportamiento.getFilDimension()
							+ " Per:" + filtroComportamiento.getFilPeriodo(),
					"Registro de evaluacinn de preescolar: Periodo '"
							+ filtroComportamiento.getFilPeriodo()
							+ "', Metodologia '"
							+ filtroComportamiento.getFilMetodologia()
							+ "', Dimensinn '"
							+ filtroComportamiento.getFilDimension()
							+ "', Grado '" + filtroComportamiento.getFilGrado()
							+ "', Grupo '" + filtroComportamiento.getFilGrupo()
							+ "'",
					String.valueOf(filtroComportamiento.getFilPeriodo()), 6, 1,
					this.toString());
		} catch (Exception e) {
			e.printStackTrace();
			setMensaje("Error ingresando evaluación por dimensiones: "
					+ e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Elimina del contexto de la sesinn los beans de informaciÃ³n del formulario
	 * de evaluacinn
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void borrarBeans(HttpServletRequest request) {
		request.getSession().removeAttribute("filtroEvaluacion");
	}

	/**
	 * Trae de sesion los datos de la credencial de acceso del usuario y del
	 * formulario de filtro de busqueda y evaluacion de logros,etc
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeans(HttpServletRequest request)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("login") == null) {
			return false;
		}
		if (session.getAttribute("filtroEvaluacion") == null) {
			return false;
		}
		return true;
	}

	/**
	 * Busca los estudiantes y logros para la evaluciacion de logros y pone los
	 * datos en la session de usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void asignarListaLogro(HttpServletRequest request, Login login,
			FiltroBeanEvaluacion filtroEvaluacion) throws ServletException,
			IOException {
		HttpSession session = request.getSession();
		Collection[] col = new Collection[4];
		// String nivel = "1";
		try {
			// LOGROS
			col[1] = evaluacionDAO.getLogro(filtroEvaluacion);

			int max = Integer.parseInt(rb.getString("maximosLogros"));
			if (col[1] != null && max != -1 && max < col[1].size()) {
				request.setAttribute("evalMax", "1");
				return;
			}
			request.setAttribute("evalMax", "0");
			session.setAttribute("filtroLogros", col[1]);
			String grupo = filtroEvaluacion.getGrupo().substring(
					filtroEvaluacion.getGrupo().lastIndexOf("|") + 1,
					filtroEvaluacion.getGrupo().length());
			String asig = filtroEvaluacion.getAsignatura().substring(
					filtroEvaluacion.getAsignatura().lastIndexOf("|") + 1,
					filtroEvaluacion.getAsignatura().length());
			
			filtroEvaluacion = evaluacionDAO.getCodigoJerarquiaGrupo(filtroEvaluacion);
			session.removeAttribute("filtroResultado");
			
			// ESTUDIANTES
			col[0] = evaluacionDAO.getEstudiantes(filtroEvaluacion);
			session.setAttribute("filtroResultado", col[0]);
			// JERARQUIA DEL GRUPO AL QUE PERTENECE
			filtroEvaluacion.setJerarquiagrupo(evaluacionDAO
					.getJerarquiaGrupo(col[0]));
			// REVISAR SI ESTA O NO ABIERTA PARA EDICION DEL GRUPO
			filtroEvaluacion.setCerrar(evaluacionDAO
					.getEstadoNotasAsig(filtroEvaluacion));
			// RESTRICCIONES DE HORARIO PARA EVALUAR
			filtroEvaluacion.setLectura(evaluacionDAO.getEstadoHorarioAsig(
					filtroEvaluacion, login));
			// NOTAS
			col[2] = evaluacionDAO.getEscala(1);
			session.setAttribute("filtroNota", col[2]);
			// notas actuales
			col[3] = evaluacionDAO.getNotasLogro(filtroEvaluacion);
			filtroEvaluacion.setNota(evaluacionDAO.getFiltroArray(col[3]));
			filtroEvaluacion.setActualizar(filtroEvaluacion.getNota());
			Logger.print(
					login.getUsuarioId(),
					"Evaluacinn Logro Inst:"
							+ filtroEvaluacion.getInstitucion() + " Sede:"
							+ filtroEvaluacion.getSede() + " Jorn:"
							+ filtroEvaluacion.getJornada() + " Met:"
							+ filtroEvaluacion.getMetodologia() + " Gra:"
							+ filtroEvaluacion.getGrado() + " Grupo:" + grupo
							+ " Asig:" + asig + " Per:"
							+ filtroEvaluacion.getPeriodo(),
					"Consulta a evaluacinn de Logro: Periodo '"
							+ filtroEvaluacion.getPeriodo_()
							+ "', Metodologia '"
							+ filtroEvaluacion.getMetodologia_()
							+ "', Asignatura '"
							+ filtroEvaluacion.getAsignatura_() + "', Grado '"
							+ filtroEvaluacion.getGrado_() + "', Grupo '"
							+ grupo + "'", filtroEvaluacion.getPeriodo(), 2, 1,
					this.toString());
		} catch (Exception th) {
			throw new ServletException(th);
		}
	}

	/**
	 * Busca los estudiantes y logros para la recuperacion de logros y pone los
	 * datos en la session de usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void asignarListaRecuperacion(HttpServletRequest request,
			Login login, FiltroBeanEvaluacion filtroEvaluacion)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Collection[] col = new Collection[4];
		try {
			// ESTUDIANTES
			String grupo = filtroEvaluacion.getGrupo().substring(
					filtroEvaluacion.getGrupo().lastIndexOf("|") + 1,
					filtroEvaluacion.getGrupo().length());
			String asig = filtroEvaluacion.getAsignatura().substring(
					filtroEvaluacion.getAsignatura().lastIndexOf("|") + 1,
					filtroEvaluacion.getAsignatura().length());
			col[0] = evaluacionDAO.getEstudiantes(filtroEvaluacion);
			session.setAttribute("filtroResultado", col[0]);
			// JERARQUIA DEL GRUPO AL QUE PERTENECE
			filtroEvaluacion.setJerarquiagrupo(evaluacionDAO
					.getJerarquiaGrupo(col[0]));
			// REVISAR SI ESTA O NO ABIERTA PARA EDICION DEL GRUPO
			filtroEvaluacion.setCerrar("0");
			// RESTRICCIONES DE HORARIO PARA EVALUAR
			filtroEvaluacion.setLectura(evaluacionDAO.getEstadoHorarioAsig(
					filtroEvaluacion, login));
			// LOGROS
			col[1] = evaluacionDAO.getLogro(filtroEvaluacion);
			session.setAttribute("filtroLogros", col[1]);
			// NOTAS
			col[2] = evaluacionDAO.getEscala(1);
			session.setAttribute("filtroNotaRec", col[2]);
			// NOTAS QUE YA ESTAN EN LA TABLA DE EVALUACION
			col[3] = evaluacionDAO.getNotasRecLogro(filtroEvaluacion);
			int zz[] = { 0, 1, 2, 3 };
			filtroEvaluacion.setNota(evaluacionDAO.getFiltroArray(col[3], zz));
			filtroEvaluacion.setMotivo(evaluacionDAO.getFiltroArray(col[3]));
			filtroEvaluacion.setActualizar(filtroEvaluacion.getNota());
			Logger.print(
					login.getUsuarioId(),
					"Recuperacinn Logro Inst:"
							+ filtroEvaluacion.getInstitucion() + " Sede:"
							+ filtroEvaluacion.getSede() + " Jorn:"
							+ filtroEvaluacion.getJornada() + " Met:"
							+ filtroEvaluacion.getMetodologia() + " Gra:"
							+ filtroEvaluacion.getGrado() + " Grupo:" + grupo
							+ " Asig:" + asig + " PerAct:"
							+ filtroEvaluacion.getPeriodo() + " PerRec:"
							+ filtroEvaluacion.getPeriodo2(),
					"Consulta a recuperacinn de Logro: Periodo Actual '"
							+ filtroEvaluacion.getPeriodo_()
							+ "', Periodo a Recuperar '"
							+ filtroEvaluacion.getPeriodo2()
							+ "', Metodologia '"
							+ filtroEvaluacion.getMetodologia_()
							+ "', Asignatura '"
							+ filtroEvaluacion.getAsignatura_() + "', Grado '"
							+ filtroEvaluacion.getGrado_() + "', Grupo '"
							+ grupo + "'", filtroEvaluacion.getPeriodo2(), 2,
					1, this.toString());
		} catch (Exception th) {
			throw new ServletException(th);
		}
	}

	/**
	 * Busca los estudiantes para la evaluciacion de asignaturas y pone los
	 * datos en la session de usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void asignarListaAsignatura(HttpServletRequest request, Login login,
			FiltroBeanEvaluacion filtroEvaluacion) throws ServletException,
			IOException {
		HttpSession session = request.getSession();
		Collection[] col = new Collection[4];
		try {

			/*
			 * CAMBIO PARA CARGAR TIPO DE EVALUACION
			 */
			TipoEvalVO tipoEval = evaluacionDAO.getTipoEval(
					filtroEvaluacion.getMetodologia(),
					filtroEvaluacion.getGrado(), login);

			String asig = filtroEvaluacion.getAsignatura().substring(
					filtroEvaluacion.getAsignatura().lastIndexOf('|') + 1,
					filtroEvaluacion.getAsignatura().length());
			String grupo = filtroEvaluacion.getGrupo().substring(
					filtroEvaluacion.getGrupo().lastIndexOf("|") + 1,
					filtroEvaluacion.getGrupo().length());
			
			filtroEvaluacion = evaluacionDAO.getCodigoJerarquiaGrupo(filtroEvaluacion);
			session.removeAttribute("filtroResultado");
			
			// estudiantes
			col[0] = evaluacionDAO.getEstudiantesAsignatura(filtroEvaluacion,
					tipoEval.getCod_tipo_eval());
			session.setAttribute("filtroResultado", col[0]);
			// REVISAR SI ESTA O NO ABIERTA PARA EDICION DEL GRUPO
			filtroEvaluacion.setCerrar(evaluacionDAO
					.getEstadoNotasAsig(filtroEvaluacion));
			// RESTRICCIONES DE HORARIO PARA EVALUAR
			filtroEvaluacion.setLectura(evaluacionDAO.getEstadoHorarioAsig(filtroEvaluacion, login));
			// escala
			// col[1] = evaluacionDAO.getEscala(2);
			if (tipoEval.getCod_tipo_eval() == ParamsVO.TIPO_EVAL_CONCEPTUAL) {
				col[1] = evaluacionDAO.getEscalaConceptual(
						filtroEvaluacion.getMetodologia(),
						filtroEvaluacion.getGrado(), login);
				session.setAttribute("filtroNota2", col[1]);
			}

			session.setAttribute("tipoEval",
					new Long(tipoEval.getCod_tipo_eval()));
			if (tipoEval.getCod_tipo_eval() == ParamsVO.TIPO_EVAL_NUM
					|| tipoEval.getCod_tipo_eval() == ParamsVO.TIPO_EVAL_POR) {
				session.setAttribute("modo_eval",
						new Integer(tipoEval.getModo_eval()));
				session.setAttribute("tipoEvalMax",
						new Double(tipoEval.getEval_max()));
				session.setAttribute("tipoEvalMin",
						new Double(tipoEval.getEval_min()));
				session.setAttribute("tipoEvalAprobMin",
						new Double(tipoEval.getTipoEvalAprobMin()));
			}

			// LOG DE TRANSACCIONES
			Logger.print(
					login.getUsuarioId(),
					"Evaluacinn Asignatura Inst:"
							+ filtroEvaluacion.getInstitucion() + " Sede:"
							+ filtroEvaluacion.getSede() + " Jorn:"
							+ filtroEvaluacion.getJornada() + " Met:"
							+ filtroEvaluacion.getMetodologia() + " Gra:"
							+ filtroEvaluacion.getGrado() + " Grupo:" + grupo
							+ " Asig:" + asig + " Per:"
							+ filtroEvaluacion.getPeriodo(),
					"Consulta a evaluacinn de Asignatura: Periodo '"
							+ filtroEvaluacion.getPeriodo_()
							+ "', Metodologia '"
							+ filtroEvaluacion.getMetodologia_()
							+ "', Asignatura '"
							+ filtroEvaluacion.getAsignatura_() + "', Grado '"
							+ filtroEvaluacion.getGrado_() + "', Grupo '"
							+ grupo + "'", filtroEvaluacion.getPeriodo(), 2, 1,
					this.toString());
		} catch (Exception th) {
			th.printStackTrace();
			throw new ServletException(th);
		}
	}

	/**
	 * Busca los estudiantes para la evaluciacion de asignaturas y pone los
	 * datos en la session de usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void asignarListaComportamiento(HttpServletRequest request,
			Login login, FiltroComportamiento filtroComportamiento)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			// System.out.println("periodo: "+filtroComportamiento.getFilPeriodo());
			// estudiante con nota
			/*
			 * CAMBIO PARA CARGAR TIPO DE EVALUACION
			 */
			TipoEvalVO tipoEval = evaluacionDAO.getTipoEval(
					String.valueOf(filtroComportamiento.getFilMetodologia()),
					String.valueOf(filtroComportamiento.getFilGrado()), login);
			int nivelGrado = evaluacion2DAO.getNivelGrado(filtroComportamiento
					.getFilGrado());
			long numerico = 0;

			if (nivelGrado != 1) {
				if (tipoEval.getCod_tipo_eval() == ParamsVO.TIPO_EVAL_NUM)
					numerico = 1;
			} else {
				if (tipoEval.getTipo_eval_prees() == ParamsVO.TIPO_EVAL_PREES_ASI) {
					if (tipoEval.getCod_tipo_eval() == ParamsVO.TIPO_EVAL_NUM)
						numerico = 1;
				}
			}

			List l = evaluacion2DAO.getEstudianteComportamiento(
					filtroComportamiento, numerico);
			request.setAttribute("estudiantesCom", l);
			if (nivelGrado != 1) {
				if (tipoEval.getCod_tipo_eval() == ParamsVO.TIPO_EVAL_CONCEPTUAL) {
					l = (ArrayList) evaluacionDAO.getEscalaConceptualComp(
							String.valueOf(filtroComportamiento
									.getFilMetodologia()),
							String.valueOf(filtroComportamiento.getFilGrado()),
							login);
					// System.out.println("EVAL_COMP: ****LISTA ESCALA CONCEPTUAL : "+l.size());
					request.setAttribute("escalaCom", l);
				}
				// System.out.println("***************************************************************************TIPOEVAL 1: "+tipoEval.getCod_tipo_eval());
				session.setAttribute("tipoEval",
						new Long(tipoEval.getCod_tipo_eval()));
				if (tipoEval.getCod_tipo_eval() == ParamsVO.TIPO_EVAL_NUM
						|| tipoEval.getCod_tipo_eval() == ParamsVO.TIPO_EVAL_POR) {
					session.setAttribute("tipoEvalMax",
							new Double(tipoEval.getEval_max()));
					session.setAttribute("tipoEvalMin",
							new Double(tipoEval.getEval_min()));
					session.setAttribute("tipoEvalAprobMin",
							new Double(tipoEval.getTipoEvalAprobMin()));
					
				}
			} else {
				// System.out.println("EVAL COMPORTAMIENTO, EVAL PREES: "+tipoEval.getTipo_eval_prees());
				if (tipoEval.getTipo_eval_prees() == ParamsVO.TIPO_EVAL_PREES_ASI) {
					if (tipoEval.getCod_tipo_eval() == ParamsVO.TIPO_EVAL_CONCEPTUAL) {
						l = (ArrayList) evaluacionDAO.getEscalaConceptualComp(
								String.valueOf(filtroComportamiento
										.getFilMetodologia()), String
										.valueOf(filtroComportamiento
												.getFilGrado()), login);
						request.setAttribute("escalaCom", l);
					}
					// System.out.println("***************************************************************************TIPOEVAL2: "+tipoEval.getCod_tipo_eval());
					session.setAttribute("tipoEval",
							new Long(tipoEval.getCod_tipo_eval()));
					if (tipoEval.getCod_tipo_eval() == ParamsVO.TIPO_EVAL_NUM
							|| tipoEval.getCod_tipo_eval() == ParamsVO.TIPO_EVAL_POR) {
						session.setAttribute("tipoEvalMax",
								new Double(tipoEval.getEval_max()));
						session.setAttribute("tipoEvalMin",
								new Double(tipoEval.getEval_min()));
						session.setAttribute("tipoEvalAprobMin",
								new Double(tipoEval.getTipoEvalAprobMin()));
					}
				} else if (tipoEval.getTipo_eval_prees() == ParamsVO.TIPO_EVAL_PREES_DIM) {
					// System.out.println("EVAL COMPORTAMIENTO: entro por MEN");
					session.setAttribute("tipoEval", new Long(
							ParamsVO.TIPO_EVAL_MEN));
					l = (ArrayList) evaluacion2DAO.getEscalaComportamiento();
					request.setAttribute("escalaCom", l);
				}
			}

			// System.out.println("periodo: "+filtroComportamiento.getFilPeriodo());
			// request.getSession().setAttribute("filtroComportamiento",
			// filtroComportamiento);
			Logger.print(
					login.getUsuarioId(),
					"Evaluacinn Comportamiento Inst:"
							+ filtroComportamiento.getFilInstitucion()
							+ " Sede:" + filtroComportamiento.getFilSede()
							+ " Jorn:" + filtroComportamiento.getFilJornada()
							+ " Met:"
							+ filtroComportamiento.getFilMetodologia()
							+ " Gra:" + filtroComportamiento.getFilGrado()
							+ " Grupo:" + filtroComportamiento.getFilGrupo()
							+ " Per:" + filtroComportamiento.getFilPeriodo(),
					"Consulta a  evaluacinn de Comportamiento:  Periodo '"
							+ filtroComportamiento.getFilPeriodo()
							+ "', Metodologia '"
							+ filtroComportamiento.getFilMetodologia()
							+ "', Grado '" + filtroComportamiento.getFilGrado()
							+ "', Grupo '" + filtroComportamiento.getFilGrupo()
							+ "'",
					String.valueOf(filtroComportamiento.getFilPeriodo()), 2, 1,
					this.toString());
		} catch (Exception th) {
			th.printStackTrace();
			throw new ServletException(th);
		}
	}

	/**
	 * Ingresa o actualiza la evaluacinn de asignaturas de los estudiantes del
	 * formulario de evaluacinn
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean insertarComportamiento(HttpServletRequest request,
			Login login, FiltroComportamiento filtroComportamiento)
			throws ServletException, IOException {
		try {
			evaluacion2DAO.insertarEvalComportamiento(filtroComportamiento);
			Logger.print(
					login.getUsuarioId(),
					"Evaluacinn Comportamiento Inst:"
							+ filtroComportamiento.getFilInstitucion()
							+ " Sede:" + filtroComportamiento.getFilSede()
							+ " Jorn:" + filtroComportamiento.getFilJornada()
							+ " Met:"
							+ filtroComportamiento.getFilMetodologia()
							+ " Gra:" + filtroComportamiento.getFilGrado()
							+ " Grupo:" + filtroComportamiento.getFilGrupo()
							+ " Per:" + filtroComportamiento.getFilPeriodo(),
					"Registro de evaluacinn de Comportamiento: Periodo '"
							+ filtroComportamiento.getFilPeriodo()
							+ "', Metodologia '"
							+ filtroComportamiento.getFilMetodologia()
							+ "', Grado '" + filtroComportamiento.getFilGrado()
							+ "', Grupo '" + filtroComportamiento.getFilGrupo()
							+ "'",
					String.valueOf(filtroComportamiento.getFilPeriodo()), 6, 1,
					this.toString());
		} catch (Exception e) {
			setMensaje("Error ingresando evaluación de comportamiento: "
					+ e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Recibe la peticion por el metodo get de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);// redirecciona la peticion a doPost
	}

	/**
	 * Recibe la peticion por el metodo Post de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String s = process(request, response);
		// System.out.println("EVALUACION: ========= " + s);
		if (s != null && !s.equals(""))
			ir(1, s, request, response);
		return;
	}

	/**
	 * Redirige el control a otro servlet
	 * 
	 * @param int a: 1=redirigir como 'include', 2=redirigir como 'forward'
	 * @param String
	 *            s
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public void ir(int a, String s, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Login login = (Login) request.getSession().getAttribute("login");
		// System.out.println("-SIGES-"+(login!=null?login.getUsuarioId():"")+"Despacho
		// Evaluacion Save");
		if (cursor != null)
			cursor.cerrar();
		RequestDispatcher rd = getServletContext().getRequestDispatcher(s);
		if (a == 1)
			rd.include(request, response);
		else
			rd.forward(request, response);
	}

	/**
	 * Concatena el parametro en una variable que contiene los mensajes que se
	 * van a enviar a la vista
	 * 
	 * @param String
	 *            s
	 */
	public void setMensaje(String s) {
		mensaje = "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n" + "  - " + s
				+ "\n";
	}

	/**
	 * Retorna una variable que contiene los mensajes que se van a enviar a la
	 * vista
	 * 
	 * @return  String
	 */
	public String getMensaje() {
		return mensaje;
	}
	
	
	/**
	 * Metodo que calcula total de minimas inasistencias para determinar si un estudianteb pierde o no una materia 
	 * 
	 * @param request
	 * @param login
	 * @param filtroEvaluacion
	 * @return
	 * @throws Exception
	 */
	public int consultartotalasistenciasporasignatura(HttpServletRequest request, Login login,
			FiltroBeanEvaluacion filtroEvaluacion) throws Exception{
		
		filtroEvaluacion.setAsignatura(filtroEvaluacion.getAsignatura().substring(
				filtroEvaluacion.getAsignatura().lastIndexOf("|") + 1,
				filtroEvaluacion.getAsignatura().length()));
		 
		Cursor cursor = new Cursor();
		 AdminParametroInstDAO adminDAO=new AdminParametroInstDAO(cursor);
		 InstParVO instParVO=adminDAO.getInstdeVigenciaMayor(Integer.parseInt(login.getInstId()));
		
		 int ih_asignatura=evaluacionDAO.consultarIntensidadHorariaporasignatura(login.getInstId(),filtroEvaluacion.getGrado(),
				 filtroEvaluacion.getMetodologia(),filtroEvaluacion.getAsignatura(),String.valueOf(instParVO.getInsparvigencia()));
		 int semanasAcademicas=Integer.parseInt(rb.getString("semanasacademicas"));
		 
		 

		return (int)((ih_asignatura*semanasAcademicas)*instParVO.getPorcentajeperdida())/100;
	}
	
	/**
	 * Metodo que calcula total de minimas inasistencias para determinar si un estudianteb pierde o no un area
	 * 
	 * @param request
	 * @param login
	 * @param filtroEvaluacion
	 * @return
	 * @throws Exception
	 */
	public int consultartotalasistenciasporarea(HttpServletRequest request, Login login,
			FiltroBeanEvaluacion filtroEvaluacion) throws Exception{
		
		filtroEvaluacion.setArea(filtroEvaluacion.getArea().substring(
				filtroEvaluacion.getArea().lastIndexOf("|") + 1,
				filtroEvaluacion.getArea().length()));
		
		Cursor cursor = new Cursor();
		AdminParametroInstDAO adminDAO=new AdminParametroInstDAO(cursor);
		 InstParVO instParVO=adminDAO.getInstdeVigenciaMayor(Integer.parseInt(login.getInstId()));
		 
		 int ih_asignatura=evaluacionDAO.consultarIntensidadHorariaporarea(login.getInstId(),filtroEvaluacion.getGrado(),
				 filtroEvaluacion.getMetodologia(),String.valueOf(instParVO.getInsparvigencia()),filtroEvaluacion.getArea());
		 
		 int semanasAcademicas=Integer.parseInt(rb.getString("semanasacademicas"));
		 
		 
		 
		 
		 
		return (int)((ih_asignatura*semanasAcademicas)*instParVO.getPorcentajeperdida())/100;
	}
	
}