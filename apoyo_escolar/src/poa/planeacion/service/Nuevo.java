package poa.planeacion.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import siges.dao.Cursor;
import siges.util.Logger;
import siges.common.vo.Params;
import poa.common.vo.ParamPOA;
import siges.login.beans.Login;
import poa.planeacion.vo.ParamsVO;
import siges.common.service.Service;
import poa.planeacion.vo.PlaneacionVO;
import poa.planeacion.dao.PlaneacionDAO;
import poa.planeacion.vo.FiltroPlaneacionVO;


/**
 * Procesa las peticiones del formulario de ingreso/edicion de actividades
 * 14/01/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class Nuevo extends Service {
	
	private static final long serialVersionUID = 6180013753285143307L;
	
	/**
	 * Ruta de la pagina de ingreso/edicinn de actividad con recursos
	 */
	public String FICHA_ACTIVIDAD;
	
	/**
	 * Ruta de la pagina de ingreso/edicinn de actividad sin recursos
	 */
	public String FICHA_ACTIVIDAD_SIN;
	
	/**
	 * Objeto de acceso a datos
	 */
	private PlaneacionDAO planeacionDAO = new PlaneacionDAO(new Cursor());
	
	/*
	 * Proceso de la peticinn
	 * 
	 * @param request peticinn
	 * 
	 * @param response respuesta
	 * 
	 * @return Ruta de redireccinn
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
		
		FICHA_ACTIVIDAD = config.getInitParameter("FICHA_ACTIVIDAD");
		FICHA_ACTIVIDAD_SIN = config.getInitParameter("FICHA_ACTIVIDAD_SIN");
		
		int CMD = getCmd(request, session, ParamsVO.CMD_NUEVO);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		
		Login usuVO = (Login) session.getAttribute("login");
		
		PlaneacionVO planeacion = (PlaneacionVO) session.getAttribute("planeacionVO");
		PlaneacionVO planeacionSin = (PlaneacionVO) session.getAttribute("planeacionSinVO");
		FiltroPlaneacionVO filtro = (FiltroPlaneacionVO) session.getAttribute("filtroPlaneacionVO");
		FiltroPlaneacionVO filtroSin = (FiltroPlaneacionVO) session.getAttribute("filtroPlaneacionSinVO");

		if (filtro == null || filtro.getLblVigencia() == null) {
			
			try {
				
				filtro = planeacionDAO.getLabels(filtro);
				filtro.setFilAreaGestion(-99);
				
				if (usuVO.getInstId() != null && !usuVO.getInstId().equals("")) {
					long instId = Long.parseLong(usuVO.getInstId());
					filtro.setFilInstitucion(instId);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			session.setAttribute("filtroPlaneacionVO", filtro);
			
		}
		
		if (filtroSin == null) {

			try {
				
				filtroSin = planeacionDAO.getLabelsSin(filtroSin);
				filtroSin.setFilAreaGestion(-99);
				
				if (usuVO.getInstId() != null && !usuVO.getInstId().equals("")) {
					long instId = Long.parseLong(usuVO.getInstId());
					filtroSin.setFilInstitucion(instId);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		switch (TIPO) {
		case ParamsVO.FICHA_ACTIVIDAD:
			FICHA = FICHA_ACTIVIDAD;
			switch (CMD) {
			case ParamsVO.CMD_NUEVO:
				session.removeAttribute("planeacionVO");
				filtro.setFilAreaGestion(-99);
				planeacionBuscar(request, session, usuVO, filtro);
				break;
			case ParamsVO.CMD_EDITAR:
				planeacionEditar(request, session, usuVO, filtro);
				planeacionBuscar(request, session, usuVO, filtro);
				planeacion = (PlaneacionVO) session.getAttribute("planeacionVO");
				break;
			case ParamsVO.CMD_ELIMINAR:
				planeacionEliminar(request, session, usuVO);
				planeacionBuscar(request, session, usuVO, filtro);
				break;
			case ParamsVO.CMD_BUSCAR:
				planeacionBuscar(request, session, usuVO, filtro);
				if (filtro.getFilEstatoPOA() == ParamPOA.ESTADO_POA_RECHAZADO_SED) {
					request.setAttribute(ParamsVO.SMS, "Observacinn por la devolucinn del POA: \n" + filtro.getFilObservacionLinea());
				}
				break;
			case ParamsVO.CMD_GUARDAR:
				planeacionGuardar(request, session, usuVO, filtro, planeacion);
				planeacionBuscar(request, session, usuVO, filtro);
				break;
			case ParamsVO.CMD_APROBAR_COLEGIO:
				planeacionAprobar(request, session, usuVO, filtro);
				planeacionBuscar(request, session, usuVO, filtro);
				session.removeAttribute("planeacionVO");
				break;
			}
			planeacionNuevo(request, session, usuVO, filtro, planeacion);
			break;
		case ParamsVO.FICHA_ACTIVIDAD_SIN:
			FICHA = FICHA_ACTIVIDAD_SIN;
			switch (CMD) {
			case ParamsVO.CMD_NUEVO:
				session.removeAttribute("planeacionSinVO");
				filtroSin.setFilAreaGestion(-99);
				planeacionSinBuscar(request, session, usuVO, filtroSin);
				break;
			case ParamsVO.CMD_EDITAR:
				planeacionSinEditar(request, session, usuVO, filtroSin);
				planeacionSinBuscar(request, session, usuVO, filtroSin);
				planeacionSin = (PlaneacionVO) session.getAttribute("planeacionSinVO");
				break;
			case ParamsVO.CMD_ELIMINAR:
				planeacionSinEliminar(request, session, usuVO);
				planeacionSinBuscar(request, session, usuVO, filtroSin);
				break;
			case ParamsVO.CMD_BUSCAR:
				planeacionSinBuscar(request, session, usuVO, filtroSin);
				if (filtroSin.getFilEstatoPOA() == ParamPOA.ESTADO_POA_RECHAZADO_SED) {
					request.setAttribute(ParamsVO.SMS, "Observacinn por la devolucinn del POA: \n" + filtroSin.getFilObservacionLinea());
				}
				break;
			case ParamsVO.CMD_GUARDAR:
				planeacionSinGuardar(request, session, usuVO, filtroSin, planeacionSin);
				planeacionSinBuscar(request, session, usuVO, filtroSin);
				break;
			}
			planeacionSinNuevo(request, session, usuVO, planeacionSin);
			break;
		}
		
		dispatcher[0] = String.valueOf(Params.INCLUDE);
		dispatcher[1] = FICHA;
		
		return dispatcher;
		
	}
	

	/**
	 * Inicializa los objetos de la pagina de ingreso/edicinn de actividad con
	 * recursos
	 * 
	 * @param request
	 *            peticinn
	 * @param session
	 *            sesinn de usuario
	 * @param usuVO
	 *            Login de usuario
	 * @param planeacion
	 *            Actividad
	 * @throws ServletException
	 */
	
	public void planeacionNuevo(HttpServletRequest request,	HttpSession session, Login usuVO, FiltroPlaneacionVO filtro,
			PlaneacionVO planeacion) throws ServletException {
		try {
			request.setAttribute("listaVigencia",planeacionDAO.getListaVigenciaActual());
			request.setAttribute("listaTipoMeta",planeacionDAO.getListaTipoMeta());
			request.setAttribute("listaUnidadMedida",planeacionDAO.getListaUnidadMedida());
			request.setAttribute("listaFuenteFinanciera",planeacionDAO.getListaFuenteFinanciera());
			
			request.getSession().setAttribute("listaObjetivos",planeacionDAO.getlistaObjetivos());
			
			//***** PIMA - PIGA *****
			request.setAttribute("listaFuenteFinanciacion",planeacionDAO.getListaFuenteFinanciacion());
			request.setAttribute("listaAccionMejoramiento",planeacionDAO.getListaAccionMejoramiento());
			//***********************
			
			if (planeacion == null) {
				planeacion = new PlaneacionVO();
				planeacion.setPlaVigencia((int) planeacionDAO.getVigenciaNumericoPOA());
				planeacion.setPlaInstitucion(Long.parseLong(usuVO.getInstId()));
				planeacion.setFormaEstado("0");
				session.setAttribute("planeacionVO", planeacion);
			}
			if (planeacion.getPlaVigencia() != 0) {
				request.setAttribute("listaAreaGestion",planeacionDAO.getListaAreaGestion(
						planeacion.getPlaVigencia(),
						planeacion.getPlaInstitucion(),
						planeacion.getPlaCodigo()));
			}
			if (planeacion.getPlaAreaGestion() != 0) {
				request.setAttribute("listaLineaAccion", planeacionDAO.getListaLineaAccion(planeacion.getPlaAreaGestion()));
			}
			// para la parte del total ponderado
			if (filtro != null && filtro.getFilVigencia() != 0) {
				filtro.setFilPonderado(planeacionDAO.getTotalPonderado(filtro));
				
			}
			
			Logger.print(usuVO.getUsuarioId(), "Consulta de POA, módulo de Planeación de Actividades-Actividades. ",
					7, 1, this.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Error interno: " + e.getMessage());
		}
	}

	/**
	 * Inicializa los objetos de la pagina de ingreso/edicinn de actividades sin
	 * recursos
	 * 
	 * @param request
	 *            peticinn
	 * @param session
	 *            sesinn de usuario
	 * @param usuVO
	 *            Login de usuario
	 * @param planeacion
	 *            Actividad
	 * @throws ServletException
	 */
	public void planeacionSinNuevo(HttpServletRequest request,
			HttpSession session, Login usuVO, PlaneacionVO planeacion)
			throws ServletException {
		try {
			request.setAttribute("listaVigencia",
					planeacionDAO.getListaVigenciaActual());
			request.setAttribute("listaAreaGestion",
					planeacionDAO.getListaAreaGestion());
			request.setAttribute("listaUnidadMedida",
					planeacionDAO.getListaUnidadMedida());
			request.setAttribute("listaFuenteFinanciera",
					planeacionDAO.getListaFuenteFinancieraSin());
			if (planeacion == null) {
				planeacion = new PlaneacionVO();
				planeacion.setPlaVigencia((int) planeacionDAO
						.getVigenciaNumericoPOA());
				planeacion.setPlaInstitucion(Long.parseLong(usuVO.getInstId()));
				session.setAttribute("planeacionSinVO", planeacion);
			}
			if (planeacion.getPlaAreaGestion() != 0) {
				request.setAttribute("listaLineaAccion", planeacionDAO
						.getListaLineaAccion(planeacion.getPlaAreaGestion()));
			}
			Logger.print(usuVO.getUsuarioId(), "Consulta de POA, módulo de Planeacion de Actividades-Problemas del colegio. ",
					7, 1, this.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 * Ingresa o actualiza un actividad con recursos
	 * 
	 * @param request
	 *            peticinn
	 * @param session
	 *            sesinn de usuario
	 * @param usuVO
	 *            login de usuario
	 * @param planeacion
	 *            Actividad
	 * @throws ServletException
	 */
	public void planeacionGuardar(HttpServletRequest request, HttpSession session, Login usuVO, FiltroPlaneacionVO filtro, PlaneacionVO planeacion) throws ServletException {
		
		try {
			
			if (!filtro.isFilFechaHabil()) {
				request.setAttribute(ParamsVO.SMS, "No se puede editar la información porque está por fuera de la fecha ["+ filtro.getFilRangoFechas() + "]");
				return;
			}
			
			planeacion.setTipoActividad((String) request.getParameter("plaTipoActividad"));
			
			if(planeacion.getTipoActividad().equalsIgnoreCase("PIMA - PIGA")) {
			
				planeacion.setAccionMejoramiento((String) request.getParameter("plaAccionMejoramiento"));
				if(planeacion.getAccionMejoramiento().equalsIgnoreCase("otras")){
					planeacion.setAccionMejoramientoOtras((String) request.getParameter("plaAccionMejoramientoTxt"));
				}
				
				planeacion.setTipoGasto((String) request.getParameter("plaTipoGasto"));
				planeacion.setRubroGasto((String) request.getParameter("plaRubroGasto"));
				
				if (planeacion.getTipoGasto().equalsIgnoreCase("Inversion")){
					planeacion.setProyectoInversion((String) request.getParameter("txtProyecto"));
				}else if (planeacion.getTipoGasto().equalsIgnoreCase("Funcionamiento")){
					planeacion.setSubnivelGasto((String) request.getParameter("txtProyecto"));
				}
				planeacion.setFuenteFinanciacion((String) request.getParameter("plaFuenteFinanciacion"));
				
				if(planeacion.getFuenteFinanciacion().equalsIgnoreCase("otros recursos")){
					planeacion.setFuenteFinanciacionOtros((String) request.getParameter("fuenteFinanciacionOtros"));
				}
				
				planeacion.setMontoAnual(Long.valueOf(request.getParameter("txtMontoAnual").toString()));
				if(request.getParameter("txtPresupuestoParticipativo").toString() != null && !request.getParameter("txtPresupuestoParticipativo").toString().equals("")){
					planeacion.setPresupuestoParticipativo(Long.valueOf(request.getParameter("txtPresupuestoParticipativo").toString()));
				}else{
					planeacion.setPresupuestoParticipativo(new Long(0));
				}
			}
		
			planeacion.setSEGFECHACUMPLIMT(request.getParameter("SEGFECHACUMPLIMT"));
			
			if (planeacion != null) {
				
				long lngValorCantidad;
				
				// 2019-10-28
				// Como desde la interfaz no llega actualizado el valor de la cantidad para los tipos de meta, entonces se debe hacer el cálculo acá
				if (planeacion.getPlaTipoMeta() == 1) {
				
					lngValorCantidad = Long.parseLong(planeacion.getPlaCronograma1()) + Long.parseLong(planeacion.getPlaCronograma2()) + Long.parseLong(planeacion.getPlaCronograma3()) + Long.parseLong(planeacion.getPlaCronograma4());
					
					planeacion.setPlaMetaAnualCantidad(lngValorCantidad);
					
				} else if (planeacion.getPlaTipoMeta() == 2) {
					
					planeacion.setPlaMetaAnualCantidad(1);
					planeacion.setPlaCronograma1("1");
					planeacion.setPlaCronograma2("1");
					planeacion.setPlaCronograma3("1");
					planeacion.setPlaCronograma4("1");
					
				} else if (planeacion.getPlaTipoMeta() == 3) {
					
					planeacion.setPlaCronograma1(Long.toString(planeacion.getPlaMetaAnualCantidad()));
					planeacion.setPlaCronograma2(Long.toString(planeacion.getPlaMetaAnualCantidad()));
					planeacion.setPlaCronograma3(Long.toString(planeacion.getPlaMetaAnualCantidad()));
					planeacion.setPlaCronograma4(Long.toString(planeacion.getPlaMetaAnualCantidad()));
					
				}
				
				// 2019-10-28
				planeacionDAO.actualizarTablasTipoMeta(planeacion);
				
				if (planeacion.getFormaEstado().equals("1")) {
					
					planeacion.setPlaIdUsuario(usuVO.getUsuarioId());
					planeacionDAO.actualizarActividad(planeacion);
					request.setAttribute(ParamsVO.SMS,"La actividad fue actualizada satisfactoriamente");
					Logger.print(usuVO.getUsuarioId(),"POA: Actualizacion de actividad con recursos. "
									+ planeacion.getPlaVigencia() + ". "
									+ planeacion.getPlaInstitucion() + ". "
									+ planeacion.getPlaCodigo(), 7, 1,this.toString());
					
				} else {
					
					planeacion.setPlaIdUsuario(usuVO.getUsuarioId());
					planeacion = planeacionDAO.ingresarActividad(planeacion);
					request.setAttribute(ParamsVO.SMS,"La actividad fue ingresada satisfactoriamente");
					Logger.print(usuVO.getUsuarioId(),"POA: Ingreso de actividad con recursos. "
									+ planeacion.getPlaVigencia() + ". "
									+ planeacion.getPlaInstitucion() + ". "
									+ planeacion.getPlaCodigo(), 6, 1,
							this.toString());
					
				}
				
			}
			
			session.removeAttribute("planeacionVO");
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "La actividad no fue ingresada / actualizada/ enviada: "	+ e.getMessage());
		}
		
	}

	/**
	 * Ingresa o actualiza una actividad sin recursos
	 * 
	 * @param request
	 *            peticinn
	 * @param session
	 *            sesinn de usuario
	 * @param usuVO
	 *            Login de usuario
	 * @param planeacion
	 *            Actividad
	 * @throws ServletException
	 */
	public void planeacionSinGuardar(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroPlaneacionVO filtro,
			PlaneacionVO planeacion) throws ServletException {
		try {
			if (!filtro.isFilFechaHabil()) {
				request.setAttribute(ParamsVO.SMS, "No se puede editar la información porque está por fuera de la fecha [" + filtro.getFilRangoFechas() + "]");
				return;
			}
			if (planeacion != null) {
				if (planeacion.getFormaEstado().equals("1")) {
					planeacionDAO.actualizarActividadSin(planeacion);
					request.setAttribute(ParamsVO.SMS,
							"La actividad fue actualizada satisfactoriamente");
					Logger.print(usuVO.getUsuarioId(),
							"POA: Actualizacion de actividad sin recursos. "
									+ planeacion.getPlaVigencia() + ". "
									+ planeacion.getPlaInstitucion() + ". "
									+ planeacion.getPlaCodigo(), 7, 1,this.toString());
				} else {
					planeacionDAO.ingresarActividadSin(planeacion);
					request.setAttribute(ParamsVO.SMS, "La actividad fue ingresada satisfactoriamente");
					Logger.print(usuVO.getUsuarioId(),
							"POA: Ingreso de actividad sin recursos. "
									+ planeacion.getPlaVigencia() + ". "
									+ planeacion.getPlaInstitucion() + ". "
									+ planeacion.getPlaCodigo(), 6, 1,this.toString());
				}
			}
			session.removeAttribute("planeacionSinVO");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(
					ParamsVO.SMS,
					"La actividad no fue ingresada / actualizada/ enviada: "
							+ e.getMessage());
		}
	}

	/**
	 * Calcula la lista de actividades sin recursos
	 * 
	 * @param request
	 *            peticinn
	 * @param session
	 *            sesinn de usuario
	 * @param usuVO
	 *            Login de usuario
	 * @param filtro
	 *            Filtro de busqueda de actividades
	 * @throws ServletException
	 */
	public void planeacionBuscar(HttpServletRequest request, HttpSession session, Login usuVO, FiltroPlaneacionVO filtro) throws ServletException {
		try {
			if (filtro != null && filtro.getFilVigencia() != 0) {
				// System.out.println("APOYO POA BUSCAR NUEVO FILTRO: "
				// + filtro.getFilVigencia());
				request.setAttribute("listaActividades",planeacionDAO.getListaActividades(filtro));
				request.setAttribute("listaFuenteFinanciacion",planeacionDAO.getListaFuenteFinanciacion());
				request.setAttribute("eliminar", new Boolean(planeacionDAO.eliminarActividadEnable(filtro.getFilVigencia(), filtro.getFilInstitucion())));
				// if(filtro.getFilEstatoPOA()==ParamPOA.ESTADO_POA_RECHAZADO_SED){
				// request.setAttribute(ParamsVO.SMS,
				// "Observacinn por el rechazo del POA: \n"+filtro.getFilObservacionLinea());
				// }
				Logger.print(usuVO.getUsuarioId(),"POA: Buscar actividad con recursos. "	+ filtro.getFilInstitucion() + ". "	+ filtro.getFilVigencia(), 2, 1,this.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 * calcula la lista de actividades sin recursos
	 * 
	 * @param request
	 *            peticion
	 * @param session
	 *            sesinn de usuario
	 * @param usuVO
	 *            Login de usuario
	 * @param filtro
	 *            Filtro de bnsqueda de actividades
	 * @throws ServletException
	 */
	public void planeacionSinBuscar(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroPlaneacionVO filtro)
			throws ServletException {
		try {
			if (filtro != null && filtro.getFilVigencia() != 0) {
				request.setAttribute("listaActividades", planeacionDAO.getListaActividadesSin(filtro));
				// if(filtro.getFilEstatoPOA()==ParamPOA.ESTADO_POA_RECHAZADO_SED){
				// request.setAttribute(ParamsVO.SMS,
				// "Observacinn por el rechazo del POA: \n"+filtro.getFilObservacionLinea());
				// }
				Logger.print(usuVO.getUsuarioId(), "POA: Buscar actividad sin recursos. "+ filtro.getFilInstitucion() + ". "+ filtro.getFilVigencia(), 2, 1,this.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 * Obtiene una actividad
	 * 
	 * @param request
	 *            peticinn
	 * @param session
	 *            sesinn de usuario
	 * @param usuVO
	 *            Login de usuario
	 * @param filtro
	 *            Filtro de busqeueda de actividades
	 * @throws ServletException
	 */
	public void planeacionEditar(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroPlaneacionVO filtro)
			throws ServletException {
		try {
			String id = request.getParameter("id");
			String id2 = request.getParameter("id2");
			String id3 = request.getParameter("id3");
			if (id != null && id2 != null && id3 != null) {
				PlaneacionVO planeacionVO = planeacionDAO.getActividad(Integer.parseInt(id),Long.parseLong(id2), Long.parseLong(id3),filtro.getFilEstatoPOA(),filtro.isFilFechaHabil());
				session.setAttribute("planeacionVO", planeacionVO);
				
				request.setAttribute("listaFuenteFinanciacion",planeacionDAO.getListaFuenteFinanciacion());
				request.setAttribute("listaRubroGasto",planeacionDAO.getListaRubroGasto(planeacionVO.getTipoGasto()));
				
				Logger.print(usuVO.getUsuarioId(),"POA: Edicion de actividad con recursos. " + id + ". "+ id2 + ". " + id3, 2, 1, this.toString());
			}
			if (!filtro.isFilFechaHabil()) {
				request.setAttribute(ParamsVO.SMS,"No se puede editar la información porque está por fuera de la fecha ["+ filtro.getFilRangoFechas() + "]");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 * Obtiene una actividad sin recursos
	 * 
	 * @param request
	 *            peticinn
	 * @param session
	 *            sesinn de usuario
	 * @param usuVO
	 *            Login de usuario
	 * @param filtro
	 *            Filtro de busqueda de actividades
	 * @throws ServletException
	 */
	public void planeacionSinEditar(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroPlaneacionVO filtro)
			throws ServletException {
		try {
			String id = request.getParameter("id");
			String id2 = request.getParameter("id2");
			String id3 = request.getParameter("id3");
			if (id != null && id2 != null && id3 != null) {
				session.setAttribute("planeacionSinVO", planeacionDAO
						.getActividadSin(Integer.parseInt(id),
								Long.parseLong(id2), Long.parseLong(id3),
								filtro.getFilEstatoPOA(),
								filtro.isFilFechaHabil()));
				Logger.print(usuVO.getUsuarioId(),
						"POA: Edicion de actividad sin recursos. " + id + ". "
								+ id2 + ". " + id3, 2, 1, this.toString());
			}
			if (!filtro.isFilFechaHabil()) {
				request.setAttribute(ParamsVO.SMS, "No se puede editar la información porque está por fuera de la fecha [" + filtro.getFilRangoFechas() + "]");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 * Elimina una actividad con recursos
	 * 
	 * @param request
	 *            peticinn
	 * @param session
	 *            sesinn de usuario
	 * @param usuVO
	 *            Login de usuario
	 * @throws ServletException
	 */
	public void planeacionEliminar(HttpServletRequest request,
			HttpSession session, Login usuVO) throws ServletException {
		try {
			String id = request.getParameter("id");
			String id2 = request.getParameter("id2");
			String id3 = request.getParameter("id3");
			if (id != null && id2 != null && id3 != null) {
				planeacionDAO.eliminarActividad(Integer.parseInt(id),
						Long.parseLong(id2), Long.parseLong(id3));
				Logger.print(usuVO.getUsuarioId(),
						"POA: Eliminacion de actividad con recursos. " + id
								+ ". " + id2 + ". " + id3, 8, 1,
						this.toString());
			}
			request.setAttribute(ParamsVO.SMS,
					"La actividad fue eliminada satisfactoriamente");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS,
					"La actividad no fue eliminada: " + e.getMessage());
		}
	}

	/**
	 * Elimina una actividad sin recursos
	 * 
	 * @param request
	 *            peticinn
	 * @param session
	 *            sesinn de usuario
	 * @param usuVO
	 *            Login de usuario
	 * @throws ServletException
	 */
	public void planeacionSinEliminar(HttpServletRequest request,
			HttpSession session, Login usuVO) throws ServletException {
		try {
			String id = request.getParameter("id");
			String id2 = request.getParameter("id2");
			String id3 = request.getParameter("id3");
			if (id != null && id2 != null && id3 != null) {
				planeacionDAO.eliminarActividadSin(Integer.parseInt(id),
						Long.parseLong(id2), Long.parseLong(id3));
				Logger.print(usuVO.getUsuarioId(),
						"POA: Eliminacion de actividad sin recursos. " + id
								+ ". " + id2 + ". " + id3, 8, 1,
						this.toString());
			}
			request.setAttribute(ParamsVO.SMS,
					"La actividad fue eliminada satisfactoriamente");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "La actividad no fue eliminada:"
					+ e.getMessage());
		}
	}

	/**
	 * Aprueba el POA para un colegio
	 * 
	 * @param request
	 *            peticinn
	 * @param session
	 *            sesinn de usuario
	 * @param usuVO
	 *            Login de usuario
	 * @param filtro
	 *            Filtro de busqueda de actividades
	 * @throws ServletException
	 */
	public void planeacionAprobar(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroPlaneacionVO filtro)
			throws ServletException {
		try {
			if (filtro != null) {
				if (filtro.getFilVigencia() != 0) {
					planeacionDAO.aprobarColegio(filtro);
					request.setAttribute(ParamsVO.SMS,
							"El POA fue enviado satisfactoriamente");
					Logger.print(
							usuVO.getUsuarioId(),
							"POA: Aprobacion de POA colegio. "
									+ filtro.getFilVigencia() + ". "
									+ filtro.getFilInstitucion(), 6, 1,
							this.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS,
					"El POA no fue enviado: " + e.getMessage());
		}
	}
}
