package poa.aprobarCambios.service;

//	VERSION		FECHA		AUTOR			DESCRIPCION
//		1.0		28/11/2019	JORGE CAMACHO	Se modificó el método planeacionGuardar() para que calcule internamente los valores del cronograma, la cantidad de la meta anual e invoque el método
//											que actualiza los campos de demanda cuando el tipo de meta es diferente a demanda 

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import siges.dao.Cursor;
import siges.util.Logger;
import poa.common.vo.ParamPOA;
import siges.common.vo.Params;
import siges.login.beans.Login;
import siges.common.service.Service;
import poa.aprobarCambios.vo.ParamsVO;
import poa.aprobarCambios.vo.CambioVO;
import poa.aprobarCambios.dao.CambiosDAO;
import poa.aprobarCambios.vo.FiltroCambiosVO;
import poa.aprobacionActividades.vo.PlaneacionVO;


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
	public String FICHA_CAMBIO;
	public String FICHA_CAMBIO_ACT;
	public String FICHA_CAMBIO_PRO;
	
	/**
	 * Ruta de la pagina de ingreso/edicinn de actividad sin recursos
	 */

	/**
	 * Objeto de acceso a datos
	 */
	private CambiosDAO cambiosDAO = new CambiosDAO(new Cursor());

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
	public String[] process(HttpServletRequest request,	HttpServletResponse response) throws ServletException, IOException {
		
		String FICHA = null;
		String dispatcher[] = new String[2];
		
		HttpSession session = request.getSession();
		
		FICHA_CAMBIO = config.getInitParameter("FICHA_CAMBIO");
		FICHA_CAMBIO_ACT = config.getInitParameter("FICHA_CAMBIO_ACTIVIDADES");
		FICHA_CAMBIO_PRO = config.getInitParameter("FICHA_CAMBIO_PROBLEMAS");
		
		int CMD = getCmd(request, session);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		
		CambioVO cambio = null;
		FiltroCambiosVO filtro = null;
		PlaneacionVO planeacion = null;
		PlaneacionVO planeacionSin = null;
		
		Login usuVO = (Login) session.getAttribute("login");
				
		try {
		
			filtro = (FiltroCambiosVO) session.getAttribute("filtroCambioVO");
			planeacion = (PlaneacionVO) session.getAttribute("cambioActividadesVO");
			planeacionSin = (PlaneacionVO) session.getAttribute("cambioActividadesSinVO");
			//cambio = (CambioVO) session.getAttribute("cambioVO");
			
		} catch (Exception e) {
			session.removeAttribute("filtroCambioVO");
			session.removeAttribute("cambioActividadesVO");
			session.removeAttribute("cambioActividadesSinVO");
			session.removeAttribute("cambioVO");
		}

		switch (TIPO) {
		case ParamsVO.FICHA_CAMBIO:
			FICHA = FICHA_CAMBIO;
			switch (CMD) {
			case ParamsVO.CMD_NUEVO:
				session.removeAttribute("cambioVO");
				session.removeAttribute("filtroCambioVO");
				cambioBuscar(request, session, usuVO, filtro);
				cambio = null;
				break;
			case ParamsVO.CMD_EDITAR:
				cambioEditar(request, session, usuVO, filtro);
				cambioBuscar(request, session, usuVO, filtro);
				cambio = (CambioVO) session.getAttribute("cambioVO");
				break;
			case ParamsVO.CMD_ELIMINAR:
				cambioEliminar(request, session, usuVO);
				cambioBuscar(request, session, usuVO, filtro);
				session.removeAttribute("cambioVO");
				cambio = null;
				break;
			case ParamsVO.CMD_BUSCAR:
				cambioBuscar(request, session, usuVO, filtro);
				if (filtro.getFilEstatoPOA() == ParamPOA.ESTADO_POA_RECHAZADO_SED) {
					request.setAttribute(ParamsVO.SMS, "Observación por la devolución del POA: \n" + filtro.getFilObservacionLinea());
				}
				break;
			case ParamsVO.CMD_GUARDAR:
				cambioGuardar(request, session, usuVO, filtro, cambio);
				cambioBuscar(request, session, usuVO, filtro);
				cambio = null;
				break;
			case ParamsVO.CMD_APROBAR_CAMBIO:
				cambioAprobar(request, session, usuVO, filtro);
				FICHA = FICHA_CAMBIO_ACT;
				break;
			case ParamsVO.CMD_RECHAZAR_CAMBIO:
				cambioRechazar(request, session, usuVO, filtro);
				cambioBuscar(request, session, usuVO, filtro);
				session.removeAttribute("cambioVO");
				break;
			case ParamsVO.CMD_REALIZAR_CAMBIO:
				cambioRealizar(request, session, usuVO, filtro);
				FICHA = FICHA_CAMBIO_ACT;
				break;
			}
			cambioNuevo(request, session, usuVO, filtro, cambio);
			break;

		case ParamsVO.FICHA_ACTIVIDAD:
			FICHA = FICHA_CAMBIO_ACT;
			switch (CMD) {
			case ParamsVO.CMD_EDITAR:
				planeacionEditar(request, session, usuVO, filtro);
				buscarActividades(request, session, usuVO, filtro);
				cambio = (CambioVO) session.getAttribute("cambioVO");
				break;
			case ParamsVO.CMD_ELIMINAR:

				break;
			case ParamsVO.CMD_BUSCAR:
				buscarActividades(request, session, usuVO, filtro);
				break;
			case ParamsVO.CMD_GUARDAR:
				planeacionGuardar(request, session, usuVO, filtro, planeacion);
				buscarActividades(request, session, usuVO, filtro);
				planeacion = null;
				break;
			}
			break;

		case ParamsVO.FICHA_ACTIVIDAD_SIN:
			FICHA = FICHA_CAMBIO_PRO;
			switch (CMD) {
			case ParamsVO.CMD_EDITAR:
				planeacionSinEditar(request, session, usuVO, filtro);
				buscarActividadesSin(request, session, usuVO, filtro);
				break;
			case ParamsVO.CMD_ELIMINAR:

				break;
			case ParamsVO.CMD_BUSCAR:
				buscarActividadesSin(request, session, usuVO, filtro);
				break;
			case ParamsVO.CMD_GUARDAR:
				planeacionSinGuardar(request, session, usuVO, filtro,
						planeacionSin);
				buscarActividadesSin(request, session, usuVO, filtro);
				planeacionSin = null;
				break;
			}
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
	 * @param cambio
	 *            Actividad
	 * @throws ServletException
	 */
	public void cambioNuevo(HttpServletRequest request, HttpSession session, Login usuVO, FiltroCambiosVO filtro, CambioVO cambio) throws ServletException {
		
		try {
			
			request.setAttribute("listaVigencia", cambiosDAO.getListaVigenciaActual());

			if (cambio == null) {
				
				cambio = new CambioVO();
				cambio.setVigencia((int) cambiosDAO.getVigenciaNumericoPOA());
				cambio.setNivel(3);
				cambio.setFormaEstado("0");
				cambio.setUsuario(usuVO.getUsuarioId());
				cambio.setEstado(ParamsVO.ESTADO_NUEVO);
				cambio.setNombreEstado(ParamsVO.ESTADO_NUEVO_);
				session.removeAttribute("cambioVO");
				session.setAttribute("cambioVO", cambio);

			}
			
			// Para la parte del total ponderado
			if (filtro != null && filtro.getFilVigencia() != 0) {
				filtro.setFilPonderado(cambiosDAO.getTotalPonderado(filtro));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Error interno: " + e.getMessage());
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
	 * @param cambio
	 *            Actividad
	 * @throws ServletException
	 */
	public void cambioGuardar(HttpServletRequest request, HttpSession session, Login usuVO, FiltroCambiosVO filtro, CambioVO cambio) throws ServletException {
		
		try {

			if (cambio != null) {
				if (cambio.getFormaEstado().equals("1")) {
					cambiosDAO.actualizarCambio(cambio);
					request.setAttribute(ParamsVO.SMS, "El cambio fue actualizado satisfactoriamente");
					Logger.print(usuVO.getUsuarioId(), "POA: Actualizacion de cambio con recursos. " + cambio.getVigencia() + ". " + cambio.getEntidad() + ".", 7, 1, this.toString());
				} else {
					cambio = cambiosDAO.ingresarCambio(cambio);
					request.setAttribute(ParamsVO.SMS, "El cambio fue ingresado satisfactoriamente");
					Logger.print(usuVO.getUsuarioId(), "POA: Ingreso de actividad con recursos. " + cambio.getVigencia() + ". "	+ cambio.getEntidad() + ".", 6, 1, this.toString());
				}
			}
			
			session.removeAttribute("cambioVO");
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "El cambio no fue ingresado / actualizado: " + e.getMessage());
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
	public void cambioBuscar(HttpServletRequest request, HttpSession session, Login usuVO, FiltroCambiosVO filtro) throws ServletException {
		
		try {
			
			if (filtro != null && filtro.getFilVigencia() != 0) {
				
				request.setAttribute("listaCambios", cambiosDAO.getListaCambios(filtro));
				
				Logger.print(usuVO.getUsuarioId(), "POA: Buscar actividad con recursos. " + filtro.getFilEntidad() + ". " + filtro.getFilVigencia(), 2, 1, this.toString());
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
	public void cambioEditar(HttpServletRequest request, HttpSession session, Login usuVO, FiltroCambiosVO filtro) throws ServletException {
		
		try {
			
			String id = request.getParameter("id");
			String id2 = request.getParameter("id2");
			String id3 = request.getParameter("id3");
			String id4 = request.getParameter("id4");
			
			if (id != null && id2 != null && id3 != null) {
				
				session.setAttribute("cambioVO", cambiosDAO.getCambio(Integer.parseInt(id), Long.parseLong(id2), Long.parseLong(id3), id4, filtro.getFilEstatoPOA(), filtro.isFilFechaHabil()));
				Logger.print(usuVO.getUsuarioId(), "POA: Edicion de solicitud cambio. " + id + ". " + id2 + ". " + id3 + ". " + id4, 2, 1, this.toString());
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
	public void cambioEliminar(HttpServletRequest request, HttpSession session, Login usuVO) throws ServletException {
		
		try {
			
			String id = request.getParameter("id");
			String id2 = request.getParameter("id2");
			String id3 = request.getParameter("id3");
			String id4 = request.getParameter("id4");
			
			if (id != null && id2 != null && id3 != null) {
				cambiosDAO.eliminarCambio(Integer.parseInt(id),	Long.parseLong(id2), Long.parseLong(id3), id4);
				Logger.print(usuVO.getUsuarioId(), "POA: Eliminacion de cambio con recursos. " + id + ". " + id2 + ". " + id3 + ". " + id4, 8, 1, this.toString());
			}
			
			request.setAttribute(ParamsVO.SMS, "El cambio fue eliminado satisfactoriamente");
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "El cambio no fue eliminado: " + e.getMessage());
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
	public void cambioAprobar(HttpServletRequest request, HttpSession session, Login usuVO, FiltroCambiosVO filtro) throws ServletException {
		
		try {
			
			if (filtro != null) {
				
				if (filtro.getFilVigencia() != 0) {
					
					filtro.setFilEstadoCambio(ParamsVO.ESTADO_APROBADO);
					cambiosDAO.updateEstadoCambio(filtro);
					request.setAttribute(ParamsVO.SMS, "El cambio fue aprobado satisfactoriamente");
					cambioRealizar(request, session, usuVO, filtro);
					Logger.print(usuVO.getUsuarioId(), "POA: Aprobacion de CAMBIO colegio. " + filtro.getFilVigencia() + ". " + filtro.getFilEntidad(), 6, 1, this.toString());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "El cambio no fue aprobado: "
					+ e.getMessage());
		}
		
	}
	

	public void cambioRealizar(HttpServletRequest request, HttpSession session, Login usuVO, FiltroCambiosVO filtro) throws ServletException {
		
		try {
			
			if (filtro != null) {
				
				if (filtro.getFilVigencia() != 0) {
					
					filtro.setFilEstadoCambio(ParamsVO.ESTADO_APROBADO);
					session.setAttribute("cambio2VO", cambiosDAO.getCambio(filtro.getFilVigencia(), filtro.getFilNivel(), filtro.getFilEntidad(), filtro.getFilFechaSol(), filtro.getFilEstatoPOA(), filtro.isFilFechaHabil()));
					request.setAttribute("listaActividades", cambiosDAO.getListaActividades(filtro));
					Logger.print(usuVO.getUsuarioId(), "POA: Aprobacion de CAMBIO colegio. " + filtro.getFilVigencia() + ". " + filtro.getFilEntidad(), 6, 1, this.toString());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "El cambio no fue aprobado: "
					+ e.getMessage());
		}
		
	}
	

	public void buscarActividades(HttpServletRequest request, HttpSession session, Login usuVO, FiltroCambiosVO filtro) throws ServletException {
		
		try {
			
			if (filtro != null) {
				
				if (filtro.getFilVigencia() != 0) {
					
					session.setAttribute("cambio2VO", cambiosDAO.getCambio(filtro.getFilVigencia(), filtro.getFilNivel(), filtro.getFilEntidad(), filtro.getFilFechaSol(), filtro.getFilEstatoPOA(), filtro.isFilFechaHabil()));
					request.setAttribute("listaActividades", cambiosDAO.getListaActividades(filtro));
					Logger.print(usuVO.getUsuarioId(), "POA: Aprobacion de CAMBIO colegio. " + filtro.getFilVigencia() + ". " + filtro.getFilEntidad(), 6, 1, this.toString());
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "El cambio no fue aprobado: " + e.getMessage());
		}
		
	}
	

	/**
	 * Obtiene la actividad con recursos solicitada
	 * 
	 * @param request
	 *            peticinn
	 * @param session
	 *            Sesinn de usuario
	 * @param usuVO
	 *            Login de usuario
	 * @param filtro
	 *            Filtro de bnsqueda de actividades
	 * @throws ServletException
	 */
	public void planeacionEditar(HttpServletRequest request, HttpSession session, Login usuVO, FiltroCambiosVO filtro) throws ServletException {
		
		try {
			
			String id = request.getParameter("id");
			String id2 = request.getParameter("id2");
			String id3 = request.getParameter("id3");
			
			if (id != null && id2 != null && id3 != null) {
				
				request.getSession().setAttribute("listaObjetivos", cambiosDAO.getlistaObjetivos());
				
				request.setAttribute("listaVigencia", cambiosDAO.getListaVigenciaActual());
				PlaneacionVO planeacion = cambiosDAO.getActividad(Integer.parseInt(id), Long.parseLong(id2), Long.parseLong(id3), filtro.getFilEstatoPOA());
				
				request.setAttribute("listaAreaGestion", cambiosDAO.getListaAreaGestion());
				request.setAttribute("listaTipoMeta", cambiosDAO.getListaTipoMeta());
				request.setAttribute("listaUnidadMedida", cambiosDAO.getListaUnidadMedida());
				
				/* PIMA - PIGA */
				request.setAttribute("listaAccionMejoramiento", cambiosDAO.getListaAccionMejoramiento());
				request.setAttribute("listaFuenteFinanciacion", cambiosDAO.getListaFuenteFinanciacion());
				request.setAttribute("listaRubroGasto", cambiosDAO.getListaRubroGasto(planeacion.getTipoGasto()));
				/* *********** */
				
				if (planeacion != null && planeacion.getPlaAreaGestion() != 0) {
					request.setAttribute("listaLineaAccion", cambiosDAO.getListaLineaAccion(planeacion.getPlaAreaGestion()));
				}
				
				session.setAttribute("cambioActividadesVO", planeacion);
				Logger.print(usuVO.getUsuarioId(), "POA: Edicion de actividad con recursos. " + id + ". " + id2 + ". " + id3, 2, 1, this.toString());
			}
			
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
	public void planeacionGuardar(HttpServletRequest request, HttpSession session, Login usuVO, FiltroCambiosVO filtro, PlaneacionVO planeacion) throws ServletException {
		
		try {

			if (planeacion != null) {
				
				if (planeacion.getFormaEstado().equals("1")) {
					
					long lngValorCantidad;
					
					
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

					cambiosDAO.actualizarTablasTipoMeta(planeacion);
					
					planeacion.setPlaIdUsuario(usuVO.getUsuarioId());
					cambiosDAO.actualizarActividad(planeacion);
					
					request.setAttribute(ParamsVO.SMS, "La actividad fue actualizada satisfactoriamente");
					Logger.print(usuVO.getUsuarioId(), "POA: Actualizacion de actividad con recursos. " + planeacion.getPlaVigencia() + ". " + planeacion.getPlaInstitucion() + ". " + planeacion.getPlaCodigo(), 7, 1,	this.toString());
				}
			}
			
			session.removeAttribute("cambioActividadesVO");
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS,
					"La actividad no fue actualizada: " + e.getMessage());
		}
		
	}

	public void buscarActividadesSin(HttpServletRequest request, HttpSession session, Login usuVO, FiltroCambiosVO filtro) throws ServletException {
		
		try {
			
			if (filtro != null) {
				
				if (filtro.getFilVigencia() != 0) {
					
					session.setAttribute("cambio2VO", cambiosDAO.getCambio(filtro.getFilVigencia(), filtro.getFilNivel(), filtro.getFilEntidad(), filtro.getFilFechaSol(), filtro.getFilEstatoPOA(), filtro.isFilFechaHabil()));
					request.setAttribute("listaActividades", cambiosDAO.getListaActividadesSin(filtro));
					Logger.print(usuVO.getUsuarioId(), "POA: Aprobacion de CAMBIO colegio. " + filtro.getFilVigencia() + ". " + filtro.getFilEntidad(), 6, 1, this.toString());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "El cambio no fue aprobado: " + e.getMessage());
		}
		
	}
	

	public void planeacionSinEditar(HttpServletRequest request, HttpSession session, Login usuVO, FiltroCambiosVO filtro) throws ServletException {
		
		try {
			
			String id = request.getParameter("id");
			String id2 = request.getParameter("id2");
			String id3 = request.getParameter("id3");
			
			if (id != null && id2 != null && id3 != null) {
				
				request.setAttribute("listaVigencia", cambiosDAO.getListaVigenciaActual());
				PlaneacionVO planeacion = cambiosDAO.getActividadSin(Integer.parseInt(id), Long.parseLong(id2),	Long.parseLong(id3), filtro.getFilEstatoPOA());
				request.setAttribute("listaAreaGestion", cambiosDAO.getListaAreaGestion());
				request.setAttribute("listaTipoMeta", cambiosDAO.getListaTipoMeta());
				request.setAttribute("listaUnidadMedida", cambiosDAO.getListaUnidadMedida());
				request.getSession().setAttribute("listaObjetivos", cambiosDAO.getlistaObjetivos());
				
				if (planeacion != null && planeacion.getPlaAreaGestion() != 0) {
					request.setAttribute("listaLineaAccion", cambiosDAO.getListaLineaAccion(planeacion.getPlaAreaGestion()));
				}

				session.setAttribute("cambioActividadesSinVO", planeacion);
				Logger.print(usuVO.getUsuarioId(), "POA: Edicion de actividad sin recursos. " + id + ". " + id2 + ". " + id3, 2, 1, this.toString());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
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
	public void planeacionSinGuardar(HttpServletRequest request, HttpSession session, Login usuVO, FiltroCambiosVO filtro, PlaneacionVO planeacion) throws ServletException {
		
		try {

			if (planeacion != null) {
				
				if (planeacion.getFormaEstado().equals("1")) {
					
					cambiosDAO.actualizarActividadSin(planeacion);
					request.setAttribute(ParamsVO.SMS, "La actividad fue actualizada satisfactoriamente");
					Logger.print(usuVO.getUsuarioId(), "POA: Actualizacion de actividad sin recursos. "	+ planeacion.getPlaVigencia() + ". " + planeacion.getPlaInstitucion() + ". " + planeacion.getPlaCodigo(), 7, 1,	this.toString());
				}
			}
			
			session.removeAttribute("cambioActividadesSinVO");
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS,
					"La actividad no fue actualizada: " + e.getMessage());
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
	public void cambioRechazar(HttpServletRequest request, HttpSession session, Login usuVO, FiltroCambiosVO filtro) throws ServletException {
		
		try {
			
			if (filtro != null) {
				
				if (filtro.getFilVigencia() != 0) {
					
					session.setAttribute("cambioVO", cambiosDAO.getCambio(filtro.getFilVigencia(), filtro.getFilNivel(), filtro.getFilEntidad(), filtro.getFilFechaSol(), filtro.getFilEstatoPOA(), filtro.isFilFechaHabil()));
					filtro.setFilEstadoCambio(2);
					cambiosDAO.updateEstadoCambio(filtro);
					request.setAttribute(ParamsVO.SMS, "El cambio fue rechazado satisfactoriamente");
					Logger.print(usuVO.getUsuarioId(), "POA: Aprobacion de CAMBIO colegio. " + filtro.getFilVigencia() + ". " + filtro.getFilEntidad(), 6, 1, this.toString());
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "El cambio no fue rechazado: "
					+ e.getMessage());
		}
		
	}
	
}

