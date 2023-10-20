package siges.adminParamsInst.service;

import java.util.Calendar;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;

import siges.adminParamsInst.dao.AdminParametroInstDAO;
import siges.adminParamsInst.vo.EscalaConceptualVO;
import siges.adminParamsInst.vo.EscalaNumericaVO;
import siges.adminParamsInst.vo.FiltroEscalaNumVO;
import siges.adminParamsInst.vo.FiltroEscalaVO;
import siges.adminParamsInst.vo.FiltroNivelEvalVO;
import siges.adminParamsInst.vo.InstNivelEvaluacionVO;
import siges.adminParamsInst.vo.InstParVO;
import siges.adminParamsInst.vo.ParamsVO;
import siges.adminParamsInst.vo.PeriodoPrgfechasVO;
import siges.adminParamsInst.vo.PonderacionPorPeriodoVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.gestionAcademica.planDeEstudios.dao.PlanDeEstudiosDAO;
import siges.gestionAcademica.repEstadisticos.thread.RepEstadisticos;
import siges.gestionAdministrativa.enviarMensajes.dao.EnviarMensajesDAO;
import siges.gestionAdministrativa.enviarMensajes.vo.MensajesVO;
import siges.login.beans.Login;
import siges.util.Logger;
import siges.adminParamsInst.vo.TipoEvaluacionInstAsigVO;


/**
 * @author desarrollo
 * 
 */
public class Nuevo extends Service {
	
	public String FICHA_PERIODO;
	public String FICHA_INST_NIVEL_EVAL;
	public String FICHA_ESCL_CONCEPT;
	public String FICHA_ESCL_NUMERICA;
	public String FICHA_PER_PROG_FECHAS;
	public String FICHA_PONDERACION_POR_PERIODO;

	private SimpleDateFormat formaFecha = new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss.SSS");
	private AdminParametroInstDAO adminParametroInstDAO = new AdminParametroInstDAO(new Cursor());
	private java.util.logging.Logger logger = java.util.logging.Logger.getLogger(RepEstadisticos.class.getName());

	public void init(ServletConfig config_) throws ServletException {
		
		super.init(config_);
		this.config = config_;
		
		FICHA_PERIODO = config.getInitParameter("FICHA_PERIODO");
		FICHA_INST_NIVEL_EVAL = config.getInitParameter("FICHA_INST_NIVEL_EVAL");
		FICHA_ESCL_CONCEPT = config.getInitParameter("FICHA_ESCL_CONCEPT");
		FICHA_ESCL_NUMERICA = config.getInitParameter("FICHA_ESCL_NUMERICA");
		FICHA_PER_PROG_FECHAS = config.getInitParameter("FICHA_PER_PROG_FECHAS");
		FICHA_PONDERACION_POR_PERIODO = config.getInitParameter("FICHA_PONDERACION_POR_PERIODO");
		
	}

	/*
	 * Procesa la peticinn
	 * 
	 * @param request peticinn
	 * 
	 * @param response respuesta
	 * 
	 * @return Ruta de direccionamiento
	 * 
	 * @throws ServletException
	 * 
	 * @throws IOException (non-Javadoc)
	 * 
	 * @see
	 * siges.common.service.Service#process(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	public String[] process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String FICHA = null;
		String dispatcher[] = new String[2];
		HttpSession session = request.getSession();

		int CMD = getCmd(request, session, ParamsVO.CMD_DEFAULT);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_PERIODO);
		Login usuVO = (Login) session.getAttribute("login");

		
		if (TIPO == 4 && CMD == 5)
			request.setAttribute("nuevoNivelEvalref", "1");
		
		switch (TIPO) {
		
			case ParamsVO.FICHA_PERIODO:
				
				FICHA = FICHA_PERIODO;
				InstParVO instParVO = (InstParVO) session.getAttribute("instParVO");
				
				switch (CMD) {
					case ParamsVO.CMD_DEFAULT:
					case ParamsVO.CMD_DEFAULT2:
					case ParamsVO.CMD_NUEVO:
					case ParamsVO.CMD_ELIMINAR:
						nuevoPeriodos(request, session, usuVO);
						break;
					case ParamsVO.CMD_GUARDAR:
						guardarPeriodos(request, session, usuVO, instParVO);
						// nuevoPeriodos(request, session, usuVO);
						break;
				}
				
				FICHA = FICHA_PERIODO;
				
				break;
				
			case ParamsVO.FICHA_INST_NIVEL_EVAL:
				
				InstNivelEvaluacionVO instNivelEvaluacionVO = (InstNivelEvaluacionVO) session.getAttribute("instNivelEvaluacionVO");
				TipoEvaluacionInstAsigVO tipoEvaluacionInstAsigVO = (TipoEvaluacionInstAsigVO) session.getAttribute("tipoEvaluacionInstAsigVO");
				
				if (isExistePeriodo(request, usuVO)) {
					
					FICHA = FICHA_INST_NIVEL_EVAL;
							
					switch (CMD) {
						case ParamsVO.CMD_DEFAULT:
							session.removeAttribute("instNivelEvaluacionVO");
							session.removeAttribute("filtroNivelEvalVO");
							break;
						case ParamsVO.CMD_ELIMINAR:
						case ParamsVO.CMD_NUEVO:
							nuevoNivelEval(request, session, usuVO);
							break;
						case ParamsVO.CMD_GUARDAR:
							guardarNivelEval(request, session, usuVO, instNivelEvaluacionVO, tipoEvaluacionInstAsigVO);
							break;
					}
					
					FICHA = FICHA_INST_NIVEL_EVAL;
					
				} else {
					request.setAttribute(ParamsVO.SMS,
							"Primero debe configurar  los parnmetros que se deben manejar a nivel de institucinn para la vigencia actual "
									+ getVigenciaActual(request, usuVO));
					nuevoPeriodos(request, session, usuVO);
					FICHA = FICHA_PERIODO;
				}
	
				break;

			case ParamsVO.FICHA_ESCL_CONCEPT:
	
				EscalaConceptualVO escalaConceptualVO = (EscalaConceptualVO) session.getAttribute("escalaConceptualVO");
				
				if (isExistePeriodo(request, usuVO)) {
					
					if (isExisteNivelEval_(request, usuVO)) {
	
						FICHA = FICHA_ESCL_CONCEPT;
						cargarListas(request, session, usuVO);
						
						switch (CMD) {
							case ParamsVO.CMD_DEFAULT:
								session.removeAttribute("escalaConceptualVO");
								session.removeAttribute("filtroEscalaVO");
								nuevoEscalaConcep(request, session, usuVO);
								break;
							case ParamsVO.CMD_ELIMINAR:
							case ParamsVO.CMD_NUEVO:
								nuevoEscalaConcep(request, session, usuVO);
								break;
							case ParamsVO.CMD_GUARDAR:
								guardarEscalaConcep(request, session, usuVO, escalaConceptualVO);
								break;
						}
						
						
					} else {
						request.setAttribute(ParamsVO.SMS,
								"Primero debe configurar los datos de Nivel de Evaluacinn de institucinn para la vigencia actual "
										+ getVigenciaActual(request, usuVO));
						nuevoNivelEval(request, session, usuVO);
						FICHA = FICHA_INST_NIVEL_EVAL;
					}
					
				} else {
					
					request.setAttribute(ParamsVO.SMS,
							"Primero debe configurar  los parnmetros que se deben manejar a nivel de institucinn para la vigencia actual "
									+ getVigenciaActual(request, usuVO));
					nuevoPeriodos(request, session, usuVO);
					FICHA = FICHA_PERIODO;
				}
	
				break;
			
			case ParamsVO.FICHA_ESCL_NUM:
	
				EscalaNumericaVO escalaNumericaVO = (EscalaNumericaVO) session.getAttribute("escalaNumericaVO");
				
				if (isExistePeriodo(request, usuVO)) {
					
					if (isExisteNivelEval_(request, usuVO)) {
						
						FICHA = FICHA_ESCL_NUMERICA;
						cargarListas(request, session, usuVO);
						
						switch (CMD) {
							case ParamsVO.CMD_DEFAULT:
								session.removeAttribute("escalaNumericaVO");
								session.removeAttribute("filtroEscalaNumVO");
								nuevoEscalaNumerica(request, session, usuVO);
								break;
							case ParamsVO.CMD_ELIMINAR:
							case ParamsVO.CMD_NUEVO:
								nuevoEscalaNumerica(request, session, usuVO);
								break;
							case ParamsVO.CMD_GUARDAR:
								guardarEscalaNum(request, session, usuVO, escalaNumericaVO);
								break;
						}
						
					} else {
						
						request.setAttribute(ParamsVO.SMS,
								"Primero debe configurar los datos de Nivel de Evaluacinn de institucinn para la vigencia actual "
										+ getVigenciaActual(request, usuVO));
						nuevoNivelEval(request, session, usuVO);
						FICHA = FICHA_INST_NIVEL_EVAL;
						
					}
					
				} else {
					
					request.setAttribute(ParamsVO.SMS,
							"Primero debe configurar  los parnmetros que se deben manejar a nivel de institucinn para la vigencia actual "
									+ getVigenciaActual(request, usuVO));
					nuevoPeriodos(request, session, usuVO);
					FICHA = FICHA_PERIODO;
				}
	
				break;
				
			case ParamsVO.FICHA_PER_PROG_FECHAS:
				
				FICHA = FICHA_PER_PROG_FECHAS;
				PeriodoPrgfechasVO periodoPrgfechasVO = (PeriodoPrgfechasVO) session.getAttribute("periodoPrgfechasVO");
				
				switch (CMD) {
					case ParamsVO.CMD_DEFAULT:
					case ParamsVO.CMD_DEFAULT2:
					case ParamsVO.CMD_NUEVO:
						nuevoPerProgFechas(request, session, usuVO);
						break;
					case ParamsVO.CMD_GUARDAR:
						guardarPerProgFechas(request, session, usuVO, periodoPrgfechasVO);
						break;
				}
				
				FICHA = FICHA_PER_PROG_FECHAS;
				
				break;
				
			case ParamsVO.FICHA_PONDERACION_POR_PERIODO:
				
				FICHA = FICHA_PONDERACION_POR_PERIODO;
				
				// Para validar el tipo de evaluacinn
				identificarParametroColegio(request, session, usuVO);
				
				PonderacionPorPeriodoVO ponderacionPrgPeriodoVO = (PonderacionPorPeriodoVO) session.getAttribute("ponderacionPrgPeriodoVO");
				
				switch (CMD) {
					case ParamsVO.CMD_DEFAULT:
					case ParamsVO.CMD_DEFAULT2:
					case ParamsVO.CMD_NUEVO:
						nuevoPonderacionPrgPeriodo(request, session, usuVO);
						break;
					case ParamsVO.CMD_GUARDAR:
						guardarPonderacionPrgPeriodo(request, session, usuVO, ponderacionPrgPeriodoVO);
						break;
				}
				
				FICHA = FICHA_PONDERACION_POR_PERIODO;
				break;

		}
		
		dispatcher[0] = String.valueOf(Params.INCLUDE);
		dispatcher[1] = FICHA;
		
		return dispatcher;
		
	}
	
	/**
	 * 
	 * @param request
	 * @param session
	 * @param usuVO
	 * @throws NumberFormatException
	 * @throws Exception
	 * Metodo que se encarga de cargar los para,etrps del colegio sobre para poder cuadrar la sponderaciones
	 * 
	 */
	public void identificarParametroColegio(HttpServletRequest request, HttpSession session, Login usuVO) {
		try {
		session.removeAttribute("instParametros");
		int idinst=Integer.parseInt(usuVO.getInstId());
		int vigencia;
		PlanDeEstudiosDAO planDeEstudiosDAO=new PlanDeEstudiosDAO(new Cursor());
		
			vigencia = (int)planDeEstudiosDAO.getVigenciaInst(Long.parseLong(usuVO.getInstId()));
		
		AdminParametroInstDAO adminParametro=new AdminParametroInstDAO(new Cursor());
		InstParVO instParVO=new InstParVO();
		instParVO=adminParametro.getPeriodoInstActual(vigencia, idinst);
		session.setAttribute("instParametros",instParVO);
		
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Cargar formulario de ponderacinn de periodo
	 * 
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param planeacion
	 * @throws ServletException
	 */
	public void nuevoPonderacionPrgPeriodo(HttpServletRequest request, HttpSession session,	Login usuVO) throws ServletException {
		
		try {
			
			session.removeAttribute("instParVO");
			session.removeAttribute("ponderacionPorPromedioVO");
			
			PonderacionPorPeriodoVO ponderacionPorPeriodoVO = new PonderacionPorPeriodoVO();
			
			ponderacionPorPeriodoVO.setPrfcodinst(Integer.parseInt(usuVO.getInstId()));
			ponderacionPorPeriodoVO.setPrfvigencia(adminParametroInstDAO.getVigenciaInst(Long.parseLong(usuVO.getInstId())));
			
			if (adminParametroInstDAO.isExistePonderacionPrgPeriodo(ponderacionPorPeriodoVO)) {
				ponderacionPorPeriodoVO = adminParametroInstDAO.consultarPonderacionPrgPeriodo(ponderacionPorPeriodoVO);
			}
			
			request.setAttribute("ponderacionPrgPeriodoVO", ponderacionPorPeriodoVO);
			
			session.setAttribute("numeroperiodosparam", String.valueOf(adminParametroInstDAO.obtenerNumerodePeriodo(ponderacionPorPeriodoVO.getPrfvigencia(), ponderacionPorPeriodoVO.getPrfcodinst())));
						
			InstParVO instParVO = new InstParVO();
			instParVO = adminParametroInstDAO.getPeriodoInstActual(usuVO.getVigencia_inst(), Long.parseLong(usuVO.getInstId()));

			session.setAttribute("instParVO", instParVO);
			
			String tipoNivelEvaluacion = adminParametroInstDAO.getConsultarTipoEvaluacion(instParVO.getInsparvigencia(), instParVO.getInsparcodinst());
			
			// Se crea esta variable de sesion para comparar posteriormente si cambia el nivel de evaluacinn 
			// de: Nivel a Institucion y viceversa ya que se deben actualizar las ponderaciones de las asignaturas si esto sucede.
			session.setAttribute("niveldeevaluacionactualparaponderizacion", tipoNivelEvaluacion);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Error interno: " + e.getMessage());
		}

	}
	
	
	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param PonderacionPorPeriodoVO
	 * @throws ServletException
	 */
	public void guardarPonderacionPrgPeriodo(HttpServletRequest request, HttpSession session, Login usuVO, PonderacionPorPeriodoVO pVO) throws ServletException {
		
		try {
			
			// Sólo se almacena la información para los Establecimientos Educativos que escogieron: PONDERACION POR PERIODOS
			if (pVO.getPrfTipoEvaluacion() == 3) {
				
				pVO.setPrfcodinst(Integer.parseInt(usuVO.getInstId()));
				pVO.setPrfvigencia((int) usuVO.getVigencia_inst());
				
				session.setAttribute("numeroperiodosparam", String.valueOf(adminParametroInstDAO.obtenerNumerodePeriodo(pVO.getPrfvigencia(), pVO.getPrfcodinst())));
				
				adminParametroInstDAO.actualizarPonderacionPrgPeriodo(pVO);
				request.setAttribute(ParamsVO.SMS, "La informacion fue actualizada satisfactoriamente.");
				
			} else {
				
				request.setAttribute(ParamsVO.SMS, "El Modo de Evaluación del Período no es: Ponderación por período, por lo que no se actualizó la información.");
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error: " + e.getMessage());
		}
		
	}
	
	

	/**
	 * Cargar formulario de periodos nuevo
	 * 
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param planeacion
	 * @throws ServletException
	 */
	public void nuevoPeriodos(HttpServletRequest request, HttpSession session,
			Login usuVO) throws ServletException {
		try {
			session.removeAttribute("instParVO");
			session.removeAttribute("filtroEscalarVO");
			session.removeAttribute("niveldeevaluacionactual");
			
			

			InstParVO instParVO = new InstParVO();
			instParVO.setInsparcodinst(Long.parseLong(usuVO.getInstId()));
			instParVO.setInsparvigencia(adminParametroInstDAO
					.getVigenciaInst(Long.parseLong(usuVO.getInstId())));
			instParVO = adminParametroInstDAO
					.getPeriodoInstActual(instParVO.getInsparvigencia(),
							instParVO.getInsparcodinst());
			// instParVO.setInsparsubtitbol(ParamsVO.SUBTIT_BOLETIN);

			/** Valida si existe nivel de evalacinn de institucinn */
			if (adminParametroInstDAO
					.isExisteNivelEval(instParVO.getInsparvigencia(),
							instParVO.getInsparcodinst())) {
				String msg = "";
				msg = "\n ADVERTENCIA"
						+ "\n Si usted cambia la opciÃ³n 'nivel de evaluacinn' perdern toda la informaciÃ³n"
						+ "\n que  haya configurado para las escalas valorativas."
						+ "\n nEsta seguro de realizar este cambio?.";
				instParVO.setMsgValidarNivelEval(msg);
			}

			session.setAttribute("instParVO", instParVO);

			//se crea esta variable de session para comparar posteriormente si cambia nivel de evaluacinn 
			//de: Nivel a Institucion n viceversa..ya qye se deben actualiozar las ponderaciones de las asignaturas si esto sucede.
			session.setAttribute("niveldeevaluacionactual", String.valueOf(instParVO.getInsparniveval()));

			
			cargarListas(request, session, usuVO);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @throws Exception
	 */
	private void cargarListas(HttpServletRequest request, HttpSession session,
			Login usuVO) throws ServletException {
		try {
			session.setAttribute("listaNumeroPeriodos",
					adminParametroInstDAO.getNumeroPeriodos());
			session.setAttribute("listaAllPeriodoInst",
					adminParametroInstDAO.getAllPeriodoInst(usuVO.getInstId()));
			session.setAttribute("listaTipoPeriodos",
					adminParametroInstDAO.getTipoPeriodos());
			session.setAttribute("listaTipoCombinacion",
					adminParametroInstDAO.getTipoCombinacion());
			request.setAttribute("listaMen",
					adminParametroInstDAO.getEscalaMEN());

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @throws Exception
	 */
	private void cargarListasNivelEval(HttpServletRequest request,
			HttpSession session, Login usuVO, long nivelEval)
			throws ServletException {
		try {
			// //System.out.println("nivelEval-- " + nivelEval);
			request.getSession().setAttribute(
					"listaSed",
					adminParametroInstDAO.getSede(Long.parseLong(usuVO
							.getInstId())));

			request.getSession().setAttribute(
					"listaMetodo",
					adminParametroInstDAO.getMetodologia(Long.parseLong(usuVO
							.getInstId())));

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 * Guarda o actualizar la informaciÃ³n sobre periodos
	 * 
	 * @param request
	 *            peticinn
	 * @param session
	 *            sesinn de usuario
	 * @param usuVO
	 *            login de usuario
	 * @param filtro
	 *            Filtro de bnsqueda de actividadees
	 * @throws ServletException
	 */
	public void guardarPeriodos(HttpServletRequest request,
			HttpSession session, Login usuVO, InstParVO instParVO)
			throws ServletException {
		try {
			if (instParVO != null) {
				String validar = "";

				/** Valida si ya se han utilizado las escalas valorativas */
				validar = adminParametroInstDAO.isExisteInstNivelEval(
						instParVO.getInsparvigencia(),
						instParVO.getInsparcodinst());
				if (instParVO.getInsparniveval() != instParVO
						.getInsparnivevalAntes() && validar.trim().length() > 0) {
					String msg = "";
					msg = "\n No es posible modificar el nivel de evaluacinn"
							+ "\n existen estudiantes que ya fuernn evaluados:"
							+ "\n " + validar;
					request.setAttribute(ParamsVO.SMS, msg);
					return;
				}

				if (instParVO.getInsparnumper() != instParVO
						.getInsparnumperAntes()
						&& adminParametroInstDAO.isNumPeriodoInstNivelEval(
								instParVO.getInsparvigencia(),
								instParVO.getInsparcodinst(),
								instParVO.getInsparnumper())) {
					String msg = "";
					msg = "\n No es posible modificar el nnmero de periodos"
							+ "\n existen estudiantes que ya fuernn evaluados."
							+ "\n ";
					request.setAttribute(ParamsVO.SMS, msg);
					return;
				}

				if (instParVO.getEliminarCascada() == 1) {
					adminParametroInstDAO.eliminarEscalaEnCascada(
							instParVO.getInsparvigencia(),
							instParVO.getInsparcodinst());
				}

				if (!adminParametroInstDAO.isExistePeriodo(
						instParVO.getInsparvigencia(),
						instParVO.getInsparcodinst())) {

					adminParametroInstDAO.guardarPeriodoInstActual(instParVO);
					request.setAttribute(ParamsVO.SMS,
							"La informaciÃ³n fue registrada satisfactoriamente.");
				
					
					Logger.print(
							usuVO.getUsuarioId(),
							"Registrada informaciÃ³n de periodo. "
									+ instParVO.getInsparvigencia() + ". "
									+ instParVO.getInsparcodinst(), 6, 1,
							this.toString());

				} else {
					adminParametroInstDAO
							.actualizarPeriodoInstActual(instParVO);
					request.setAttribute(ParamsVO.SMS,
							"La informaciÃ³n fue actualizada satisfactoriamente.");
					
					//evalua si el valor de nivel de evaluacinn cargado antes de estos cambios
					//es de institucion:1 n nivel:5
					if(session.getAttribute("niveldeevaluacionactual").equals("1") || 
							session.getAttribute("niveldeevaluacionactual").equals("5")){
						//evalua si el nuevo valor de nivel es institucion:1 n nivel:5
						if(String.valueOf(instParVO.getInsparniveval()).equals("1") || 
								String.valueOf(instParVO.getInsparniveval()).equals("5")){
						
					             if(!session.getAttribute("niveldeevaluacionactual").equals(String.valueOf(instParVO.getInsparniveval()))){
					            	 //aca debe ir actualizar las asignaturas
					            	 PlanDeEstudiosDAO planDeEstudiosDAO=new PlanDeEstudiosDAO(new Cursor()); 
					            	 
					            	 int tipoponderacion=0;
					            	 
					            	 switch(Integer.parseInt(String.valueOf(instParVO.getInsparniveval()))){
					            	 //institucion
					            	 case 1:
					            		 tipoponderacion=2;
					            		 break;
					            	//nivel
					            	 case 5:
					            		 tipoponderacion=1;
					            		 break;
					            	 }
					            	 
					            	 
					            	 
					            	 planDeEstudiosDAO.actualizarMasivoPonderacionesAsignatura(instParVO.getInsparcodinst(),
					            			 Integer.parseInt(String.valueOf(instParVO.getInsparvigencia())),tipoponderacion );
					             }    
					         }
						}
					session.removeAttribute("niveldeevaluacionactual");
					
					session.setAttribute("niveldeevaluacionactual",String.valueOf(instParVO.getInsparniveval()));
					
					
					Logger.print(
							usuVO.getUsuarioId(),
							"Actualizada informaciÃ³n de periodo. "
									+ instParVO.getInsparvigencia() + ". "
									+ instParVO.getInsparcodinst(), 6, 1,
							this.toString());

				}
				cargarListas(request, session, usuVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe("Error en guardarPeriodos " + e);
			request.setAttribute(ParamsVO.SMS, "Error: " + e.getMessage());
		}
	}

	// NIVEL EVALUACION
	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param nivelEvaluacionVO
	 * @throws ServletException
	 */
	public void nuevoNivelEval(HttpServletRequest request, HttpSession session, Login usuVO) throws ServletException {
		
		try {
			
			session.removeAttribute("instNivelEvaluacionVO");
			request.getSession().removeAttribute("instNivelEvaluacionVO");
			
			InstNivelEvaluacionVO instNivelEvaluacionVO = new InstNivelEvaluacionVO();
			instNivelEvaluacionVO.setEdicion(false);
			instNivelEvaluacionVO.setInsnivvigencia(adminParametroInstDAO.getVigenciaInst(Long.parseLong(usuVO.getInstId())));
			instNivelEvaluacionVO.setInsnivcodinst(Long.parseLong(usuVO.getInstId()));

			InstParVO instParVO = new InstParVO();
			instParVO.setInsparcodinst(Long.parseLong(usuVO.getInstId()));
			instParVO.setInsparvigencia(adminParametroInstDAO.getVigenciaInst(Long.parseLong(usuVO.getInstId())));
			instParVO = adminParametroInstDAO.getPeriodoInstActual(instParVO.getInsparvigencia(), instParVO.getInsparcodinst());
			instNivelEvaluacionVO.setInsnivcodniveleval(instParVO.getInsparniveval());

			session.setAttribute("instNivelEvaluacionVO", instNivelEvaluacionVO);

			adminParametroInstDAO.obtenerNumerodePeriodo((int)instParVO.getInsparvigencia(), (int)instParVO.getInsparcodinst());
			
			cargarListasNivelEval(request, session, usuVO, instNivelEvaluacionVO.getInsnivcodniveleval());

		} catch (Exception e) {
			e.printStackTrace();
			logger.severe("Error en nuevoNivelEval " + e);
			throw new ServletException("Errror interno: " + e.getMessage());
		}
		
	}
	

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param nivelEvaluacionVO
	 * @throws ServletException
	 */
	public void guardarNivelEval(HttpServletRequest request, HttpSession session, Login usuVO, InstNivelEvaluacionVO instNivelEvaluacionVO, TipoEvaluacionInstAsigVO tipoEvaluacionInstAsigVO) throws ServletException {
		
		try {
			
			if (instNivelEvaluacionVO != null) {
				
				request.setAttribute("nuevoNivelEvalref", "1");
				
				FiltroNivelEvalVO filtroNivelEvalVO = (FiltroNivelEvalVO) session.getAttribute("filtroNivelEvalVO");

				// Cargar filtro
				if (filtroNivelEvalVO == null) {
					filtroNivelEvalVO = new FiltroNivelEvalVO();
				}
				
				filtroNivelEvalVO.setFilVigencia(instNivelEvaluacionVO.getInsnivvigencia());
				filtroNivelEvalVO.setFilInst(instNivelEvaluacionVO.getInsnivcodinst());
				filtroNivelEvalVO.setFilniveval(instNivelEvaluacionVO.getInsnivcodniveleval());
				filtroNivelEvalVO.setFilSede(instNivelEvaluacionVO.getInsnivcodsede());
				filtroNivelEvalVO.setFilJorn(instNivelEvaluacionVO.getInsnivcodjorn());
				filtroNivelEvalVO.setFilMetodo(instNivelEvaluacionVO.getInsnivcodmetod());
				filtroNivelEvalVO.setFilNivel(instNivelEvaluacionVO.getInsnivcodnivel());
				filtroNivelEvalVO.setFilGrado(instNivelEvaluacionVO.getInsnivcodgrado());

				// Validacion para saber si existen escalas asociadas a este nivel de evaluacion de la institucion
				String validarEliminar = isValidareliminarInstNivelEval(request, instNivelEvaluacionVO);
				
				if (validarEliminar != null	&& instNivelEvaluacionVO.getInsnivtipoevalasig() != instNivelEvaluacionVO.getInsnivtipoevalasigAntes()) {
					
					request.setAttribute(ParamsVO.SMS,
							" No se puede realizar el cambio del tipo de 'Evaluación de Asignatura' porque"
									+ "\n existen escalas relacionadas a este nivel de evaluación. Si desea modificar "
									+ "esta información primero debe eliminar dichas escalas.");
					return;
				}
				
				// Validacion para saber si existe o no la institucion
				String validarEliminar_2 = adminParametroInstDAO.isExisteInstNivelEvalPree(instNivelEvaluacionVO.getInsnivvigencia(), instNivelEvaluacionVO.getInsnivcodinst());

				if (validarEliminar_2 != null && validarEliminar_2.trim().length() > 0 && instNivelEvaluacionVO.getInsnivtipoevalpreesAntes() != instNivelEvaluacionVO.getInsnivtipoevalprees()) {
					
					request.setAttribute(ParamsVO.SMS,
							" No se puede realizar el cambio del tipo de 'Evaluacion para Preescolar' porque "
									+ "\n existen estudiante evaluados"
									+ validarEliminar_2);
					return;
				}
				

				// Valida que no existan rangos creados para poder modificar el valor maximo y minimo del nivel de evaluacion
				String validar = adminParametroInstDAO.isValidarModificarInstNivelEval(instNivelEvaluacionVO);
				
				if (validar != null) {
					request.setAttribute(ParamsVO.SMS, validar);
					return;
				}

				if (instNivelEvaluacionVO.isEdicion() && adminParametroInstDAO.isExiteNivelEvaluacion(instNivelEvaluacionVO)) {

					// Actualiza los datos del Nivel de Evaluacion
					instNivelEvaluacionVO = adminParametroInstDAO.actualizarNivelEvaluacion(instNivelEvaluacionVO);
					
					
					// Guarda los datos en la tabla PONDERACION_INST_PERIODO dependiendo de lo seleccionado en Periodo
					if (instNivelEvaluacionVO.getInsnivmodoeval() == ParamsVO.PONDERACION_X_PERIODO || instNivelEvaluacionVO.getInsnivmodoeval() == ParamsVO.ACUMULATIVO) {
						instNivelEvaluacionVO = adminParametroInstDAO.guardarPonderacionPeriodos(instNivelEvaluacionVO);
					} else {
						// Elimina los datos en la tabla PONDERACION_INST_PERIODO porque se escogió un periodo que no almacena datos allí
						instNivelEvaluacionVO = adminParametroInstDAO.eliminarPonderacionPeriodos(instNivelEvaluacionVO);
					}
					
					
					// Pregunta si el criterio seleccionado guarda o no datos en la tabla TIPO_EVALUACION_INST_ASIG
					if (instNivelEvaluacionVO.getInsnivmodoeval() == ParamsVO.CRITERIO_DOCENTE) {
						// Elimina los datos en la tabla TIPO_EVALUACION_INST_ASIG porque se escogió CRITERIO DOCENTE y éste no almacena datos allí
						instNivelEvaluacionVO = adminParametroInstDAO.eliminarPonderacionAsignatura(instNivelEvaluacionVO);
					} else {
						// Guarda los datos en la tabla TIPO_EVALUACION_INST_ASIG dependiendo de lo seleccionado en Asignatura
						instNivelEvaluacionVO = adminParametroInstDAO.guardarPonderacionAsignatura(instNivelEvaluacionVO, tipoEvaluacionInstAsigVO);
					}
					
					
					instNivelEvaluacionVO.setEdicion(true);

					request.setAttribute(ParamsVO.SMS, "La informacion fue registrada satisfactoriamente.");
					Logger.print(usuVO.getUsuarioId(), "Registro informacion de nivel de evaluacion. "	+ usuVO.getInstId(), 6, 1, this.toString());

				} else if (!adminParametroInstDAO.isExiteNivelEvaluacion(instNivelEvaluacionVO)) {

					// Guarda los datos del Nivel de Evaluacion
					instNivelEvaluacionVO = adminParametroInstDAO.guardarNivelEvaluacion(instNivelEvaluacionVO);
					
					// Guarda los datos en la tabla PONDERACION_INST_PERIODO dependiendo de lo seleccionado en Periodo
					if (instNivelEvaluacionVO.getInsnivmodoeval() == ParamsVO.PONDERACION_X_PERIODO || instNivelEvaluacionVO.getInsnivmodoeval() == ParamsVO.ACUMULATIVO) {
						instNivelEvaluacionVO = adminParametroInstDAO.guardarPonderacionPeriodos(instNivelEvaluacionVO);
					} else {
						// Elimina los datos en la tabla PONDERACION_INST_PERIODO porque se escogió un periodo que no almacena datos allí
						instNivelEvaluacionVO = adminParametroInstDAO.eliminarPonderacionPeriodos(instNivelEvaluacionVO);
					}
					
					
					// Pregunta si el criterio seleccionado guarda o no datos en la tabla TIPO_EVALUACION_INST_ASIG
					if (instNivelEvaluacionVO.getInsnivmodoeval() == ParamsVO.CRITERIO_DOCENTE) {
						// Elimina los datos en la tabla TIPO_EVALUACION_INST_ASIG porque se escogió CRITERIO DOCENTE y éste no almacena datos allí
						instNivelEvaluacionVO = adminParametroInstDAO.eliminarPonderacionAsignatura(instNivelEvaluacionVO);
					} else {
						// Guarda los datos en la tabla TIPO_EVALUACION_INST_ASIG dependiendo de lo seleccionado en Asignatura
						instNivelEvaluacionVO = adminParametroInstDAO.guardarPonderacionAsignatura(instNivelEvaluacionVO, tipoEvaluacionInstAsigVO);
					}
					
					instNivelEvaluacionVO.setEdicion(true);

					request.setAttribute(ParamsVO.SMS, "La informacion fue registrada satisfactoriamente.");
					Logger.print(usuVO.getUsuarioId(), "Actualizo informacion de nivel de evaluacion. " + usuVO.getInstId(), 6, 1, this.toString());

				} else {

					request.setAttribute(ParamsVO.SMS, "La informacion no fue registrada. Existe un registro con esta informacion.");
					Logger.print(usuVO.getUsuarioId(), "Actualizo informacion de nivel de evaluacion. " + usuVO.getInstId(), 6, 1, this.toString());

				}

				session.removeAttribute("instNivelEvaluacionVO");
				session.setAttribute("instNivelEvaluacionVO", instNivelEvaluacionVO);
				nuevoNivelEval(request, session, usuVO);

			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe("Error en nuevoNivelEval " + e);
			request.setAttribute(ParamsVO.SMS, "Error: " + e.getMessage());
		}
		
	}

	// ESCALA CONCEPTUAL

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param planeacion
	 * @throws ServletException
	 */
	public void nuevoEscalaConcep(HttpServletRequest request,
			HttpSession session, Login usuVO) throws ServletException {
		try {
			session.removeAttribute("escalaConceptualVO");
			EscalaConceptualVO escalaConceptualVO = new EscalaConceptualVO();
			escalaConceptualVO.setInsconcodinst(Long.parseLong(usuVO
					.getInstId()));
			escalaConceptualVO.setInsconvigencia(adminParametroInstDAO
					.getVigenciaInst(Long.parseLong(usuVO.getInstId())));
			// cargarListasEscalaConcep(request, session, usuVO,
			// escalaConceptualVO);
			InstParVO instParVO = new InstParVO();
			instParVO = adminParametroInstDAO
					.getPeriodoInstActual(instParVO.getInsparvigencia(),
							instParVO.getInsparcodinst());

			cargarListasNivelEval(request, session, usuVO,
					instParVO.getInsparniveval());
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe("Error en nuevoEscalaConcep " + e);
			request.setAttribute(Params.SMS, e.getMessage());
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @throws Exception
	 */
	/*
	 * private void cargarListasEscalaConcep(HttpServletRequest request,
	 * HttpSession session,Login usuVO, EscalaConceptualVO escalaConceptualVO)
	 * throws Exception { try {
	 * 
	 * 
	 * InstParVO instParVO = new InstParVO();
	 * instParVO.setInsparcodinst(Long.parseLong(usuVO.getInstId()) );
	 * instParVO.setInsparvigencia(
	 * adminParametroInstDAO.getVigenciaInst(Long.parseLong(usuVO.getInstId()))
	 * ); instParVO =
	 * adminParametroInstDAO.getPeriodoInstActual(instParVO.getInsparvigencia(),
	 * instParVO.getInsparcodinst());
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * } catch (Exception e) { e.printStackTrace();
	 * 
	 * throw new Exception("" + e.getMessage());
	 * 
	 * } }
	 */

	/**
	 * Metodo que guarda o actualiza la informaci{on de escala conceptual
	 * 
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param filtro
	 * @throws ServletException
	 */
	public void guardarEscalaConcep(HttpServletRequest request,
			HttpSession session, Login usuVO,
			EscalaConceptualVO escalaConceptualVO) throws ServletException {
		try {
			if (escalaConceptualVO != null) {
				FiltroEscalaVO filtroEscalaVO = (FiltroEscalaVO) session
						.getAttribute("filtroEscalaVO");

				// cargar filtro
				if (filtroEscalaVO == null) {
					filtroEscalaVO = new FiltroEscalaVO();
				}
				filtroEscalaVO.setFilVigencia(escalaConceptualVO
						.getInsconvigencia());
				filtroEscalaVO
						.setFilInst(escalaConceptualVO.getInsconcodinst());
				filtroEscalaVO.setFilniveval(escalaConceptualVO
						.getInsconniveval());
				filtroEscalaVO
						.setFilSede(escalaConceptualVO.getInsconcodsede());
				filtroEscalaVO
						.setFilJorn(escalaConceptualVO.getInsconcodjorn());
				filtroEscalaVO.setFilMetodo(escalaConceptualVO
						.getInsconcodmetod());
				filtroEscalaVO.setFilNivel(escalaConceptualVO
						.getInsconcodnivel());
				filtroEscalaVO.setFilGrado(escalaConceptualVO
						.getInsconcodgrado());

				// Valida que nivel evaluacion de institucion ya este creado
				String resp_ = adminParametroInstDAO.isExisteNivelEval_(
						escalaConceptualVO, ParamsVO.ESCALA_CONCEPTUAL);
				if (resp_ != null && resp_.trim().length() > 0) {
					request.setAttribute(ParamsVO.SMS, resp_);
					return;
				}

				// Validacion para saber si existe un literal con la misma letra
				// o el mismo nombre
				if (adminParametroInstDAO
						.isExisteNombreLiteral(escalaConceptualVO)) {
					request.setAttribute(ParamsVO.SMS,
							"El \"Literal\" o el \"Nombre\" ya existen. Por favor, modifnquelo.");
					return;
				}
				// Validacion para saber si el valor numerico ya fue creado
				if (adminParametroInstDAO
						.isExisteValorNumerico(escalaConceptualVO)) {
					request.setAttribute(ParamsVO.SMS,
							"Este valor numnrico ya fue asignado a un literal. Por favor, modifnquelo.");
					return;
				}

				// Valida si la equivalencia del men ya halla sino asignada
				/*
				 * if
				 * (adminParametroInstDAO.isExisteEquiMEN(escalaConceptualVO)){
				 * request.setAttribute(ParamsVO.SMS,
				 * "Esta equivalencia del MEN ya fue asignada a un literal. Por favor modifnquela."
				 * ); return; }
				 */

				if (!escalaConceptualVO.isEdicion()) {

					EscalaConceptualVO escalaConceptualVO2 = adminParametroInstDAO
							.guardarEscalaConp(escalaConceptualVO);

					session.removeAttribute("escalaConceptualVO");

					escalaConceptualVO2.setEdicion(true);
					session.setAttribute("escalaConceptualVO",
							escalaConceptualVO2);

					String escalaMEN = adminParametroInstDAO
							.isAsignarEquiMEN(escalaConceptualVO);
					if (escalaMEN == null) {
						request.setAttribute(ParamsVO.SMS,
								"La informaciÃ³n fue registrada satisfactoriamente.");
					} else {
						request.setAttribute(ParamsVO.SMS,
								"La informaciÃ³n fue registrada satisfactoriamente."
										+ escalaMEN);
					}
					request.setAttribute(ParamsVO.SMS,
							"La informaciÃ³n fue registrada satisfactoriamente.");
					Logger.print(
							usuVO.getUsuarioId(),
							"Registrada informaciÃ³n de Escala conceptual. "
									+ escalaConceptualVO.getInsconvigencia()
									+ ". "
									+ escalaConceptualVO.getInsconcodinst(), 6,
							1, this.toString());

				} else {
					// if (adminParametroInstDAO.isCambioLireral(filtro)){
					adminParametroInstDAO
							.actualizarEscalaConp(escalaConceptualVO);
					String escalaMEN = adminParametroInstDAO
							.isAsignarEquiMEN(escalaConceptualVO);
					if (escalaMEN == null) {
						request.setAttribute(ParamsVO.SMS,
								"La informaciÃ³n fue actualizada satisfactoriamente.");
					} else {
						request.setAttribute(ParamsVO.SMS,
								"La informaciÃ³n fue actualizada satisfactoriamente."
										+ escalaMEN);
					}

					Logger.print(
							usuVO.getUsuarioId(),
							"Actualizada informaciÃ³n de Escala conceptual. "
									+ escalaConceptualVO.getInsconvigencia()
									+ ". "
									+ escalaConceptualVO.getInsconcodinst(), 6,
							1, this.toString());
					// }else {
					// request.setAttribute(ParamsVO.SMS,
					// "Este literal ya fue utilizado en al menos una evaluacinn de un estudiante.");
					// }

				}
				nuevoEscalaConcep(request, session, usuVO);
				// cargarListasEscalaConcep(request,
				// session,usuVO,escalaConceptualVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error: " + e.getMessage());
		}
	}

	// ESCALA NUMERICA

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @throws ServletException
	 */
	public void nuevoEscalaNumerica(HttpServletRequest request,
			HttpSession session, Login usuVO) throws ServletException {
		try {
			session.removeAttribute("escalaNumericaVO");
			EscalaNumericaVO escalaNumericaVO = new EscalaNumericaVO();
			escalaNumericaVO
					.setInsnumcodinst(Long.parseLong(usuVO.getInstId()));
			escalaNumericaVO.setInsnumvigencia(adminParametroInstDAO
					.getVigenciaInst(Long.parseLong(usuVO.getInstId())));

			request.setAttribute("listaMen",
					adminParametroInstDAO.getEscalaMEN());

			InstParVO instParVO = new InstParVO();
			instParVO = adminParametroInstDAO
					.getPeriodoInstActual(instParVO.getInsparvigencia(),
							instParVO.getInsparcodinst());

			cargarListasNivelEval(request, session, usuVO,
					instParVO.getInsparniveval());

			// cargarListasEscalaNum(request, session, usuVO, escalaNumericaVO);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(Params.SMS, e.getMessage());
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param escalaNumericaVO
	 * @throws Exception
	 */
	/*
	 * private void cargarListasEscalaNum(HttpServletRequest request,
	 * HttpSession session,Login usuVO, EscalaNumericaVO escalaNumericaVO)
	 * throws Exception { try {
	 * 
	 * 
	 * InstParVO instParVO = new InstParVO();
	 * instParVO.setInsparcodinst(Long.parseLong(usuVO.getInstId()) );
	 * instParVO.setInsparvigencia(
	 * adminParametroInstDAO.getVigenciaInst(Long.parseLong(usuVO.getInstId()))
	 * ); instParVO =
	 * adminParametroInstDAO.getPeriodoInstActual(instParVO.getInsparvigencia(),
	 * instParVO.getInsparcodinst());
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * } catch (Exception e) { e.printStackTrace();
	 * 
	 * throw new Exception("" + e.getMessage());
	 * 
	 * } }
	 */

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param escalaNumericaVO
	 * @throws ServletException
	 */
	public void guardarEscalaNum(HttpServletRequest request,
			HttpSession session, Login usuVO, EscalaNumericaVO escalaNumericaVO)
			throws ServletException {
		try {
			if (escalaNumericaVO != null) {

				FiltroEscalaNumVO filtroEscalaNumVO2 = (FiltroEscalaNumVO) session
						.getAttribute("filtroEscalaNumVO");

				// cargar filtro
				if (filtroEscalaNumVO2 == null) {
					filtroEscalaNumVO2 = new FiltroEscalaNumVO();
				}
				filtroEscalaNumVO2.setFilNumVigencia(escalaNumericaVO
						.getInsnumvigencia());
				filtroEscalaNumVO2.setFilNumInst(escalaNumericaVO
						.getInsnumcodinst());
				filtroEscalaNumVO2.setFilNumniveval(escalaNumericaVO
						.getInsnumniveval());
				filtroEscalaNumVO2.setFilNumSede(escalaNumericaVO
						.getInsnumcodsede());
				filtroEscalaNumVO2.setFilNumJorn(escalaNumericaVO
						.getInsnumcodjorn());
				filtroEscalaNumVO2.setFilNumMetodo(escalaNumericaVO
						.getInsnumcodmetod());
				filtroEscalaNumVO2.setFilNumNivel(escalaNumericaVO
						.getInsnumcodnivel());
				filtroEscalaNumVO2.setFilNumGrado(escalaNumericaVO
						.getInsnumcodgrado());

				// Valida que nivel evaluacion de institucion ya alla sido
				// creado con la
				// equivalencia MEN
				String resp_ = adminParametroInstDAO.isExisteNivelEval_(
						escalaNumericaVO, ParamsVO.ESCALA_NUMERICA,
						ParamsVO.ESCALA_PORCENTUAL,
						"''Tipo de Evaluacinn Numnrica o Porcentual''");

				if (resp_ != null && resp_.trim().length() > 0) {
					request.setAttribute(ParamsVO.SMS, resp_);
					return;
				}

				// Valida si el rango de escala conceptual esta dentro
				// del rango definido en INSTITUCION_NIVEL_EVALUACION
				String valorRango = adminParametroInstDAO
						.isValidarRangosMaxMin(escalaNumericaVO);
				if (valorRango != null) {
					request.setAttribute(
							ParamsVO.SMS,
							"El rango de escala numnrica \""
									+ escalaNumericaVO.getInsnumvalmin()
									+ " - "
									+ escalaNumericaVO.getInsnumvalmax()
									+ " \" no esta dentro de los valores definidos en Nivel Evaluacinn."
									+ "\n - Rango definido en Nivel de Evaluacinn \""
									+ valorRango + "\" ");

					return;
				}

				// Valida que el valor minimo no se sobrelape
				// con otro rango
				String rangoSobrelapenMin = adminParametroInstDAO
						.isValidarRangosSobreLapenMinMax(escalaNumericaVO);
				if (rangoSobrelapenMin != null) {
					request.setAttribute(ParamsVO.SMS, "El rango [ "
							+ escalaNumericaVO.getInsnumvalmin() + " - "
							+ escalaNumericaVO.getInsnumvalmax()
							+ "]  no debe estar dentro del rango ["
							+ rangoSobrelapenMin + "].");
					return;
				}
				/*
				 * // Valida que el valor maximo no se sobrelapen // con otro
				 * rango String rangoSobrelapenMax =
				 * adminParametroInstDAO.isValidarRangosSobreLapenMax
				 * (escalaNumericaVO); if(rangoSobrelapenMax != null){
				 * request.setAttribute(ParamsVO.SMS, "El valor \"Rango hasta: "
				 * + escalaNumericaVO.getInsnumvalmax() +
				 * "\" del rango no debe estar dentro del rango " +
				 * rangoSobrelapenMax); return; }
				 */

				// Valida que la suma del nuevo rango y los rangos
				// registrados no sobre pasen el rango establecido en
				// nivel evaluacinn.
				String sumaTotal = adminParametroInstDAO
						.isValidarRangosTotalMaxMin(escalaNumericaVO);
				if (sumaTotal != null) {
					request.setAttribute(
							ParamsVO.SMS,
							"La suma total de rangos sobrepasa el valor del rango establecido en Nivel Evaluacinn. \n Por favor modifique los valores del rango."
									+ sumaTotal);
					return;
				}

				if (!escalaNumericaVO.isEdicion()) {

					// Valida que la escala numerica no exista y la equvalencia
					// MEN
					// if
					// (adminParametroInstDAO.isExisteEscalaNumerica(escalaNumericaVO)){
					// request.setAttribute(ParamsVO.SMS,
					// "Esta equivalencia del MEN ya fue asignada a una escala nnmerica. Por favor modifnque esta informaciÃ³n.");
					// return;
					// }

					// Valida que no existan un rango identico
					String rangoSobrelapenMinIguales = adminParametroInstDAO
							.isValidarRangosSobreLapenMinMaxIguales(escalaNumericaVO);
					if (rangoSobrelapenMinIguales != null) {
						request.setAttribute(ParamsVO.SMS,
								"Ya existe un registro con el rango  \" "
										+ rangoSobrelapenMinIguales
										+ "\",  por favor modifiquelo. ");
						return;
					}

					EscalaNumericaVO escalaNumericaVO2 = adminParametroInstDAO
							.guardarEscalaNumerica(escalaNumericaVO);

					session.removeAttribute("escalaNumericaVO");
					session.setAttribute("escalaNumericaVO", escalaNumericaVO2);
					escalaNumericaVO2.setEdicion(true);
					request.setAttribute(ParamsVO.SMS,
							"La informaciÃ³n fue registrada satisfactoriamente y los rangos estan completos");
					Logger.print(
							usuVO.getUsuarioId(),
							"Registrada informaciÃ³n de Escala numerica. "
									+ escalaNumericaVO2.getInsnumvigencia()
									+ ". "
									+ escalaNumericaVO2.getInsnumcodinst(), 6,
							1, this.toString());

					// Valida si faltan rangos por asignar
					// Valida si se cubrio el rango registrado en
					// INSTITUCION_NIVEL_EVALUACION
					String rangoPorAsignar = adminParametroInstDAO
							.isValidarRangosAsignar(escalaNumericaVO2);
					if (rangoPorAsignar == null) {

						request.setAttribute(
								ParamsVO.SMS,
								"La informaciÃ³n fue registrada satisfactoriamente y los rangos  estan completos.");

					} else {

						request.setAttribute(ParamsVO.SMS,
								"La informaciÃ³n fue registrada satisfactoriamente."
										+ rangoPorAsignar);

					}

				} else {
					// if (adminParametroInstDAO.isCambioLireral(filtro)){

					EscalaNumericaVO escalaNumericaVO2 = adminParametroInstDAO
							.actualizarEscalaNum(escalaNumericaVO);
					// request.setAttribute(ParamsVO.SMS,
					// "La informaciÃ³n fue actualizada satisfactoriamente. Y los rangos  estan completos.");
					session.removeAttribute("escalaNumericaVO");

					escalaNumericaVO2.setEdicion(true);
					session.setAttribute("escalaNumericaVO", escalaNumericaVO2);
					Logger.print(usuVO.getUsuarioId(),
							"Registrada informaciÃ³n de Escala numerica. "
									+ usuVO.getInstId(), 6, 1, this.toString());
					String rangoPorAsignar_ = adminParametroInstDAO
							.isValidarRangosAsignar(escalaNumericaVO2);
					if (rangoPorAsignar_ == null) {
						request.setAttribute(
								ParamsVO.SMS,
								"La informaciÃ³n fue registrada satisfactoriamente y  los rangos  estan completos.");
						// //System.out.println("no falta rangos");

					} else {

						request.setAttribute(ParamsVO.SMS,
								"La informaciÃ³n fue registrada satisfactoriamente."
										+ rangoPorAsignar_);
						// //System.out.println("falta rangos");

					}

				}
				// cargarListasEscalaConcep(request,
				// session,usuVO,escalaConceptualVO);

			}
			nuevoEscalaNumerica(request, session, usuVO);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error: " + e.getMessage());
		}
	}

	/**
	 * @function: Valida si existe un registro parametro institucion con la
	 *            vigencia y la institucion
	 * 
	 * @param request
	 * @return
	 * @throws ServletException
	 */
	public boolean isExistePeriodo(HttpServletRequest request, Login usuVO) throws ServletException {
		
		try {

			long vigencia = adminParametroInstDAO.getVigenciaInst(Long.parseLong(usuVO.getInstId()));
			long inst = Long.parseLong(usuVO.getInstId());

			if (adminParametroInstDAO.isExistePeriodo(vigencia, inst)) {
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(Params.SMS, e.getMessage());
			throw new ServletException("Error interno: " + e.getMessage());
		}
		
		return false;
		
	}

	/**
	 * @function: Valida si existe un registro parametro institucion con la
	 *            vigencia y la institucion
	 * 
	 * @param request
	 * @return
	 * @throws ServletException
	 */
	public boolean isExisteNivelEval(HttpServletRequest request, Login usuVO)
			throws ServletException {
		try {

			long vigencia = adminParametroInstDAO.getVigenciaInst(Long
					.parseLong(usuVO.getInstId()));
			long inst = Long.parseLong(usuVO.getInstId());

			if (adminParametroInstDAO.isExistePeriodo(vigencia, inst)) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(e.getMessage());
			request.setAttribute(Params.SMS, e.getMessage());
			throw new ServletException("Errror interno: " + e.getMessage());
		}
		return false;
	}

	/**
	 * @function: Retorna la vigencia del sistema
	 * 
	 * @param request
	 * @return
	 * @throws ServletException
	 */
	public String getVigenciaActual(HttpServletRequest request, Login usuVO)
			throws ServletException {
		long vigencia = 2009;
		try {

			vigencia = adminParametroInstDAO.getVigenciaInst(Long
					.parseLong(usuVO.getInstId()));

		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(e.getMessage());
			request.setAttribute(Params.SMS, e.getMessage());
			throw new ServletException("Errror interno: " + e.getMessage());
		}
		return "" + vigencia;
	}

	/**
	 * @function: Valida si existe un registro parametro institucion con la
	 *            vigencia y la institucion
	 * 
	 * @param request
	 * @return
	 * @throws ServletException
	 */
	public boolean isExisteNivelEval_(HttpServletRequest request, Login usuVO)
			throws ServletException {
		try {

			long vigencia = adminParametroInstDAO.getVigenciaInst(Long
					.parseLong(usuVO.getInstId()));
			long inst = Long.parseLong(usuVO.getInstId());

			if (adminParametroInstDAO.isExisteNivelEval(vigencia, inst)) {
				// //System.out.println("isExisteNivelEval_ si " );
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(e.getMessage());
			request.setAttribute(Params.SMS, e.getMessage());
			throw new ServletException("Errror interno: " + e.getMessage());
		}
		return false;
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param filtroNivelEvalVO
	 * @throws ServletException
	 */
	private String isValidareliminarInstNivelEval(HttpServletRequest request, InstNivelEvaluacionVO instNivelEvaluacionVO) throws ServletException {
		
		String resp = null;
		
		try {
			// instNivelEvaluacionVO
			FiltroEscalaVO filtroEscalaVO = new FiltroEscalaVO();

			filtroEscalaVO.setFilVigencia(instNivelEvaluacionVO.getInsnivvigencia());
			filtroEscalaVO.setFilInst(instNivelEvaluacionVO.getInsnivcodinst());
			filtroEscalaVO.setFilniveval(instNivelEvaluacionVO.getInsnivcodniveleval());
			filtroEscalaVO.setFilSede(instNivelEvaluacionVO.getInsnivcodsede());
			filtroEscalaVO.setFilJorn(instNivelEvaluacionVO.getInsnivcodjorn());
			filtroEscalaVO.setFilMetodo(instNivelEvaluacionVO.getInsnivcodmetod());
			filtroEscalaVO.setFilNivel(instNivelEvaluacionVO.getInsnivcodnivel());
			filtroEscalaVO.setFilGrado(instNivelEvaluacionVO.getInsnivcodgrado());
			// escalaConceptualVO
			if (adminParametroInstDAO.getListaEscalaConcep(filtroEscalaVO).size() > 0) {
				return ParamsVO.ESCALA_CONCEPTUAL_;
			}

			FiltroEscalaNumVO filtroEscalaNumVO = new FiltroEscalaNumVO();

			filtroEscalaNumVO.setFilNumVigencia(instNivelEvaluacionVO.getInsnivvigencia());
			filtroEscalaNumVO.setFilNumInst(instNivelEvaluacionVO.getInsnivcodinst());
			filtroEscalaNumVO.setFilNumniveval(instNivelEvaluacionVO.getInsnivcodniveleval());
			filtroEscalaNumVO.setFilNumSede(instNivelEvaluacionVO.getInsnivcodsede());
			filtroEscalaNumVO.setFilNumJorn(instNivelEvaluacionVO.getInsnivcodjorn());
			filtroEscalaNumVO.setFilNumMetodo(instNivelEvaluacionVO.getInsnivcodmetod());
			filtroEscalaNumVO.setFilNumNivel(instNivelEvaluacionVO.getInsnivcodnivel());
			filtroEscalaNumVO.setFilNumGrado(instNivelEvaluacionVO.getInsnivcodgrado());

			if (adminParametroInstDAO.getListaEscalaNum(filtroEscalaNumVO).size() > 0) {
				return ParamsVO.ESCALA_NUMERICA_;
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, e.getMessage());
			throw new ServletException("" + e.getMessage());

		}
		
		return resp;
		
	}

	
	public void editarInstNivelEval(HttpServletRequest request,
			HttpSession session, Login usuVO) throws ServletException {
		try {

			String param[] = request.getParameterValues("param");
			String codInst = param[0];
			String codVig = param[1];
			String codNivelEva = param[2];
			String codsede = param[3];
			String codjornd = param[4];
			String codmetodo = param[5];
			String codnivel = param[6];
			String codgrado = param[7];

			if (GenericValidator.isLong(codInst)
					&& GenericValidator.isLong(codVig)
					&& GenericValidator.isLong(codNivelEva)) {

				session.removeAttribute("escalaNumericaVO");
				InstNivelEvaluacionVO instNivelEvaluacionVO = new InstNivelEvaluacionVO();
				instNivelEvaluacionVO.setInsnivcodinst(Long.parseLong(codInst));
				instNivelEvaluacionVO.setInsnivvigencia(Long.parseLong(codVig));
				instNivelEvaluacionVO.setInsnivcodniveleval(Long
						.parseLong(codNivelEva));
				instNivelEvaluacionVO.setInsnivcodsede(Long.parseLong(codsede));
				instNivelEvaluacionVO
						.setInsnivcodjorn(Long.parseLong(codjornd));
				instNivelEvaluacionVO.setInsnivcodmetod(Long
						.parseLong(codmetodo));
				instNivelEvaluacionVO.setInsnivcodnivel(Long
						.parseLong(codnivel));
				instNivelEvaluacionVO.setInsnivcodgrado(Long
						.parseLong(codgrado));

				instNivelEvaluacionVO = adminParametroInstDAO
						.obtenerInstNivelEval(instNivelEvaluacionVO);

				if (adminParametroInstDAO
						.isUtilizadoNivelEval(instNivelEvaluacionVO)) {
					instNivelEvaluacionVO.setYaFueUtilizado(1);
				}
				// //System.out.println(" instNivelEvaluacionVO.setYaFueUtilizado  "
				// + instNivelEvaluacionVO.getYaFueUtilizado());
				session.removeAttribute("instNivelEvaluacionVO");
				instNivelEvaluacionVO.setEdicion(true);
				session.setAttribute("instNivelEvaluacionVO",
						instNivelEvaluacionVO);

				// cargarListasEscalaConcep(request,session,usuVO,escalaConceptualVO);
			} else {
				// //System.out.println("error al editar ");
			}

			cargarFiltro(request, session, usuVO);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error: " + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param filtroNivelEvalVO
	 * @throws ServletException
	 */
	private void cargarFiltro(HttpServletRequest request, HttpSession session,
			Login usuVO) throws ServletException {
		try {

			InstParVO instParVO = new InstParVO();
			instParVO.setInsparcodinst(Long.parseLong(usuVO.getInstId()));
			instParVO.setInsparvigencia(adminParametroInstDAO
					.getVigenciaInst(Long.parseLong(usuVO.getInstId())));
			instParVO = adminParametroInstDAO
					.getPeriodoInstActual(instParVO.getInsparvigencia(),
							instParVO.getInsparcodinst());

			/*
			 * //System.out.println(instParVO.getInsparniveval() +" =}}= " +
			 * ParamsVO.NVLEVA_SED); if( instParVO.getInsparniveval() ==
			 * ParamsVO.NVLEVA_SED) { request.setAttribute("listaSed",
			 * adminParametroInstDAO.getSede(Long.parseLong(usuVO.g etInstId())
			 * )); } if( instParVO.getInsparniveval() == ParamsVO.NVLEVA_JORN) {
			 * request.setAttribute("listaJord",
			 * adminParametroInstDAO.getJornada
			 * (Long.parseLong(usuVO.getInstId()) ,-99 )); }
			 */
		} catch (Exception e) {
			e.printStackTrace();

			throw new ServletException("" + e.getMessage());

		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param pVO
	 * @throws ServletException
	 */
	public void guardarPerProgFechas(HttpServletRequest request,
			HttpSession session, Login usuVO, PeriodoPrgfechasVO pVO)
			throws ServletException {
		try {
			if (pVO != null) {
				pVO.setPrf_usuario(Long.parseLong(usuVO.getUsuarioId()));
				if (!adminParametroInstDAO.isExistePerProFecha(
						pVO.getPrfvigencia(), pVO.getPrfcodinst())) {
					adminParametroInstDAO.guardarPerProgFechas(pVO);
					request.setAttribute(ParamsVO.SMS,
							"La informaciÃ³n fue registrada satisfactoriamente.");
					Logger.print(
							usuVO.getUsuarioId(),
							"Registrada informaciÃ³n de periodo programacinn fechas."
									+ pVO.getPrfvigencia() + ". "
									+ pVO.getPrfcodinst(), 6, 1,
							this.toString());

				} else {
					adminParametroInstDAO.actualizarPerProgFechas(pVO);
					request.setAttribute(ParamsVO.SMS,
							"La informaciÃ³n fue actualizada satisfactoriamente.");
					Logger.print(
							usuVO.getUsuarioId(),
							"Actualizada informaciÃ³n de periodo programacinn fechas. "
									+ pVO.getPrfvigencia() + ". "
									+ pVO.getPrfcodinst(), 6, 1,
							this.toString());
				}

				EnviarMensajesDAO enviarMensajesDAO = new EnviarMensajesDAO(
						adminParametroInstDAO.getCursor());
				MensajesVO msg = null;

				/*
				 * Crear la instanciade MensajesVO para registrar los mensajes
				 * alerta correspondientes
				 */
				msg = new MensajesVO();
				msg.setMsjenviadopor(ParamsVO.ENV_SISTEM);
				msg.setMsjenviadoacoleg("" + pVO.getPrfcodinst());
				msg.setMsjenviadoaperfil(adminParametroInstDAO
						.getListaPerfiles());
				msg.setMsjusuario(usuVO.getUsuarioId());

				/*
				 * Se enviar la informaciÃ³n de cada uno de los periodos
				 */
				configurarMensaje(enviarMensajesDAO, pVO,
						pVO.getPrf_alertap1(), 1, pVO.getPrf_diasp1(),
						pVO.getPrfvigencia(), msg, pVO.getPrf_ini_per1(),
						pVO.getPrf_fin_per1());
				configurarMensaje(enviarMensajesDAO, pVO,
						pVO.getPrf_alertap2(), 2, pVO.getPrf_diasp2(),
						pVO.getPrfvigencia(), msg, pVO.getPrf_ini_per2(),
						pVO.getPrf_fin_per2());
				configurarMensaje(enviarMensajesDAO, pVO,
						pVO.getPrf_alertap3(), 3, pVO.getPrf_diasp3(),
						pVO.getPrfvigencia(), msg, pVO.getPrf_ini_per3(),
						pVO.getPrf_fin_per3());
				configurarMensaje(enviarMensajesDAO, pVO,
						pVO.getPrf_alertap4(), 4, pVO.getPrf_diasp4(),
						pVO.getPrfvigencia(), msg, pVO.getPrf_ini_per4(),
						pVO.getPrf_fin_per4());
				configurarMensaje(enviarMensajesDAO, pVO,
						pVO.getPrf_alertap5(), 5, pVO.getPrf_diasp5(),
						pVO.getPrfvigencia(), msg, pVO.getPrf_ini_per5(),
						pVO.getPrf_fin_per5());
				configurarMensaje(enviarMensajesDAO, pVO,
						pVO.getPrf_alertap6(), 6, pVO.getPrf_diasp6(),
						pVO.getPrfvigencia(), msg, pVO.getPrf_ini_per6(),
						pVO.getPrf_fin_per6());
				configurarMensaje(enviarMensajesDAO, pVO,
						pVO.getPrf_alertap7(), 7, pVO.getPrf_diasp7(),
						pVO.getPrfvigencia(), msg, pVO.getPrf_ini_per7(),
						pVO.getPrf_fin_per7());
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error: " + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @throws ServletException
	 */
	public void nuevoPerProgFechas(HttpServletRequest request,
			HttpSession session, Login usuVO) throws ServletException {
		try {
			session.removeAttribute("periodoPrgfechasVO");
			PeriodoPrgfechasVO periodoPrgfechasVO = new PeriodoPrgfechasVO();
			periodoPrgfechasVO.setPrfvigencia(adminParametroInstDAO
					.getVigenciaInst(Long.parseLong(usuVO.getInstId())));
			periodoPrgfechasVO
					.setPrfcodinst(Integer.parseInt(usuVO.getInstId()));
			periodoPrgfechasVO = adminParametroInstDAO
					.getPerProgFechas(periodoPrgfechasVO);
			session.setAttribute("periodoPrgfechasVO", periodoPrgfechasVO);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 * Se encarga de crear los mensajes o alertas segnn lo configurado por el
	 * usuario en el formulario de promagracinn de periodo.
	 * 
	 * @param enviarMensajesDAO
	 * @param bandAlert
	 * @param perd
	 * @param diaMsj
	 * @param vig
	 * @param msg
	 * @param fechIni
	 * @param fechFin
	 * @throws Exception
	 */
	private void configurarMensaje(EnviarMensajesDAO enviarMensajesDAO,
			PeriodoPrgfechasVO pVO, int bandAlert, int perd, int diaMsj,
			int vig, MensajesVO msg, String fechIni, String fechFin)
			throws Exception {

		StringBuffer sbAsunto = new StringBuffer();
		StringBuffer sbContenido = new StringBuffer();
		int horaCierre = 0;
		msg.setMsjfechaini(fechIni);
		msg.setMsjfechafin(fechFin);

		sbAsunto.append("Cierre automntico del periodo ");
		sbAsunto.append(perd + "");
		sbAsunto.append(" - ");
		sbAsunto.append(vig + "");

		msg.setMsjasunto(sbAsunto.toString());

		if (msg.getMsjfechafin() != null) {

			// Calcular los datos referentes a la fecha
			String str[] = msg.getMsjfechafin().split("\\/");
			Calendar fechaActual = Calendar.getInstance();
			Calendar fechaFinal = new GregorianCalendar(
					Integer.parseInt(str[2]), Integer.parseInt(str[1]) - 1,
					Integer.parseInt(str[0]));

			int dias = fechaFinal.get(Calendar.DAY_OF_YEAR)
					- fechaActual.get(Calendar.DAY_OF_YEAR);
			// int horas = PeriodoPrgfechasVO.DIA_HORA -
			// fechaActual.get(Calendar.HOUR_OF_DAY);
			String horaProcesar[] = adminParametroInstDAO.getHorasTipo(6)
					.split("\\,");
			if (horaProcesar != null && horaProcesar.length > 0
					&& GenericValidator.isInt(horaProcesar[0])) {
				horaCierre = Integer.parseInt(horaProcesar[0]);
			} else {
				horaCierre = PeriodoPrgfechasVO.DIA_HORA;
			}

			int horas = horaCierre - fechaActual.get(Calendar.HOUR_OF_DAY);

			/*
			 * Si checkearon mostrar alerta, configurar mensaje si no borrar, si
			 * ya existe el mensaje
			 */
			if (bandAlert == PeriodoPrgfechasVO.ALERTA_ACTIVA && dias <= diaMsj) {
				/***/
				/*
				 * Si el resultado de restar las dos fecha (fechaActual y
				 * fechaFinal para el cierre automatico) es menor a los dias
				 * registrado en para mostrar el mensaje de alerta, entoces
				 * Insertar un registro en la tabla MENSAJES
				 */
				// MensajesVO
				sbContenido.append(MensajesVO.MSJ_CONTENIDO_01);
				sbContenido.append(perd);
				sbContenido.append(MensajesVO.MSJ_RAYITA);
				sbContenido.append(vig);
				sbContenido.append(MensajesVO.MSJ_CONTENIDO_02);
				sbContenido.append(adminParametroInstDAO.getNombreInst(pVO
						.getPrfcodinst()));

				if (dias > 0) {
					sbContenido.append(MensajesVO.MSJ_CONTENIDO_1_03);
					sbContenido.append(fechFin);
					sbContenido.append(MensajesVO.MSJ_CONTENIDO_1_04);
					sbContenido.append(dias + "");
					sbContenido.append(MensajesVO.MSJ_CONTENIDO_1_05);
				} else {

					sbContenido.append(MensajesVO.MSJ_CONTENIDO_2_03);
					sbContenido.append(MensajesVO.MSJ_ABRE_PARENTESIS);
					sbContenido.append(fechFin);
					sbContenido.append(MensajesVO.MSJ_CIERRE_PARENTESIS);
					sbContenido.append(MensajesVO.MSJ_CONTENIDO_2_04);
					sbContenido.append(horas);
					sbContenido.append(MensajesVO.MSJ_CONTENIDO_2_05);
				}// fin de if if(dias > 0){
				msg.setMsjcontenido(sbContenido.toString());

				/*
				 * Si el mensaje ann no a sido registro, lo insertamos en la
				 * tabla MENSAJES, si no lo actualizamos.
				 */
				if (enviarMensajesDAO.isValidaExisteMsg(msg) < 1) {
					enviarMensajesDAO.guardarMensajes(msg);
				} else {
					msg.setMsjcodigo(enviarMensajesDAO.isValidaExisteMsg(msg));
					enviarMensajesDAO.updateMensajes(msg);
				}// fin if if( enviarMensajesDAO.isValidaExisteMsg(msg)< 1 ){

				/***/

			} else {
				/*
				 * Si existe el mensaje se debe eliminar
				 */
				if (!(enviarMensajesDAO.isValidaExisteMsg(msg) < 1)) {
					msg.setMsjcodigo(enviarMensajesDAO.isValidaExisteMsg(msg));
					enviarMensajesDAO.deleteMensaje(msg.getMsjcodigo());
				}
			}// fin if(bandAlert == 1 && dias <= diaMsj ){
		}
	}
	
}

