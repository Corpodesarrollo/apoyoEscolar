package poa.solicitudCambios.service;

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
import siges.common.service.Service;
import poa.solicitudCambios.vo.ParamsVO;
import poa.solicitudCambios.vo.CambioVO;
import poa.solicitudCambios.dao.CambiosDAO;
import poa.solicitudCambios.vo.FiltroCambiosVO;


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
	public String[] process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String FICHA = null;
		String dispatcher[] = new String[2];
		
		HttpSession session = request.getSession();
		
		FICHA_CAMBIO = config.getInitParameter("FICHA_CAMBIO");

		int CMD = getCmd(request, session);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		
		try {
			
			Login usuVO = (Login) session.getAttribute("login");
			FiltroCambiosVO filtro = (FiltroCambiosVO) session.getAttribute("filtroCambioVO");
			
			CambioVO cambio = (CambioVO) session.getAttribute("cambioVO");
			
			switch (TIPO) {
			case ParamsVO.FICHA_CAMBIO:
				FICHA = FICHA_CAMBIO;
				switch (CMD) {
				case ParamsVO.CMD_NUEVO:
					session.removeAttribute("cambioVO");
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
						if(filtro.getFilObservacionLinea() != null ){
						request.setAttribute(ParamsVO.SMS, "Observación por la devolución del POA: \n" + filtro.getFilObservacionLinea());
						} 
					}
					break;
				case ParamsVO.CMD_GUARDAR:
					cambioGuardar(request, session, usuVO, filtro, cambio);
					cambioBuscar(request, session, usuVO, filtro);
					cambio = null;
					break;
				case ParamsVO.CMD_APROBAR_COLEGIO:
					planeacionAprobar(request, session, usuVO, filtro);
					cambioBuscar(request, session, usuVO, filtro);
					session.removeAttribute("cambioVO");
					break;
				}
				
				cambioNuevo(request, session, usuVO, filtro, cambio);
				break;
			}
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
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
				cambio.setEntidad(Long.parseLong(usuVO.getInstId()));
				cambio.setFormaEstado("0");
				cambio.setUsuario(usuVO.getUsuarioId());
				cambio.setEstado(ParamsVO.ESTADO_NUEVO);
				cambio.setNombreEstado(ParamsVO.ESTADO_NUEVO_);
				session.setAttribute("cambioVO", cambio);
			}
			
			// para la parte del total ponderado
			if (filtro != null && filtro.getFilVigencia() != 0) {
				filtro.setFilPonderado(cambiosDAO.getTotalPonderado(filtro));
			}
			
			Logger.print(usuVO.getUsuarioId(), "Consulta de POA, módulo de Solicitud Cambio. ",	7, 1, this.toString());
			
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
					Logger.print(
							usuVO.getUsuarioId(),
							"POA: Ingreso de actividad con recursos. "
									+ cambio.getVigencia() + ". "
									+ cambio.getEntidad() + ".", 6, 1,
							this.toString());
				}
				
			}
			
			session.removeAttribute("cambioVO");
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "El cambio no fue ingresado / actualizado: "	+ e.getMessage());
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
	public void cambioBuscar(HttpServletRequest request, HttpSession session,
			Login usuVO, FiltroCambiosVO filtro) throws ServletException {
		try {
			if (filtro != null && filtro.getFilVigencia() != 0) {
				request.setAttribute("listaCambios",
						cambiosDAO.getListaCambios(filtro));
				Logger.print(
						usuVO.getUsuarioId(),
						"POA: Buscar actividad con recursos. "
								+ filtro.getFilEntidad() + ". "
								+ filtro.getFilVigencia(), 2, 1,
						this.toString());
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
	public void cambioEditar(HttpServletRequest request, HttpSession session,
			Login usuVO, FiltroCambiosVO filtro) throws ServletException {
		try {
			String id = request.getParameter("id");
			String id2 = request.getParameter("id2");
			String id3 = request.getParameter("id3");
			String id4 = request.getParameter("id4");
			if (id != null && id2 != null && id3 != null) {
				session.setAttribute("cambioVO", cambiosDAO.getCambio(
						Integer.parseInt(id), Long.parseLong(id2),
						Long.parseLong(id3), id4, filtro.getFilEstatoPOA(),
						filtro.isFilFechaHabil()));
				Logger.print(usuVO.getUsuarioId(),
						"POA: Edicion de solicitud cambio. " + id + ". " + id2
								+ ". " + id3 + ". " + id4, 2, 1,
						this.toString());
			}
			/*
			 * f(!filtro.isFilFechaHabil()){ request.setAttribute(ParamsVO.SMS,
			 * "No se puede editar la informaciÃ³n porque esta por fuera de la fecha ["
			 * +filtro.getFilRangoFechas()+"]"); }
			 */
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
	public void cambioEliminar(HttpServletRequest request, HttpSession session,
			Login usuVO) throws ServletException {
		try {
			String id = request.getParameter("id");
			String id2 = request.getParameter("id2");
			String id3 = request.getParameter("id3");
			String id4 = request.getParameter("id4");
			if (id != null && id2 != null && id3 != null) {
				cambiosDAO.eliminarCambio(Integer.parseInt(id),
						Long.parseLong(id2), Long.parseLong(id3), id4);
				Logger.print(usuVO.getUsuarioId(),
						"POA: Eliminacion de cambio con recursos. " + id + ". "
								+ id2 + ". " + id3 + ". " + id4, 8, 1,
						this.toString());
			}
			request.setAttribute(ParamsVO.SMS,
					"El cambio fue eliminado satisfactoriamente");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "El cambio no fue eliminado: "
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
			HttpSession session, Login usuVO, FiltroCambiosVO filtro)
			throws ServletException {
		try {
			if (filtro != null) {
				if (filtro.getFilVigencia() != 0) {
					cambiosDAO.aprobarColegio(filtro);
					request.setAttribute(ParamsVO.SMS,
							"El POA fue enviado satisfactoriamente");
					Logger.print(
							usuVO.getUsuarioId(),
							"POA: Aprobacion de POA colegio. "
									+ filtro.getFilVigencia() + ". "
									+ filtro.getFilEntidad(), 6, 1,
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
