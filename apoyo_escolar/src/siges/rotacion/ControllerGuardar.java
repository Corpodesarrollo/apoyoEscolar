package siges.rotacion;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import siges.dao.Cursor;
import siges.dao.Util;
import siges.login.beans.Login;
import siges.rotacion.beans.Horario;
import siges.rotacion.beans.ParamsVO;
import siges.rotacion.beans.Rotacion;
import siges.rotacion.dao.RotacionDAO;

/**
 * @author Administrador
 * 
 * Preferencias - Java - Estilo de cndigo - Plantillas de cndigo
 */
public class ControllerGuardar extends HttpServlet {
	public static final long serialVersionUID = 1;
	private Cursor cursor;// objeto que maneja las sentencias sql
	private Util util;//
	private RotacionDAO rotacionDAO;

	/**
	 * Funcinn: Procesa la peticion HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			String sig, sig11, sig21, sig31, sig41, sig41l, sig51, sig61, sig71, sig81, sig82, sig101, sig121;
			int tipo;
			sig = "/rotacion/ControllerGuardar.do";
			sig11 = "/rotacion/NuevoEstructura.jsp";
			sig21 = "/rotacion/NuevoFijarEspacios.jsp";
			sig31 = "/rotacion/NuevoFijarDocentes.jsp";
			sig41 = "/rotacion/NuevoInhabilitarEspacios.jsp";
			sig51 = "/rotacion/NuevoInhabilitarDocentes.jsp";
			sig61 = "/rotacion/NuevoFijarAsignatura.jsp";
			sig71 = "/rotacion/NuevoFijarEspacioDocente.jsp";
			sig81 = "/rotacion/NuevoPriorizar.jsp";
			sig82 = "/rotacion/ControllerEditar.do?tipo=80";
			sig101 = "/rotacion/Horario.jsp";
			sig121 = "/rotacion/NuevoInhabilitarHora.jsp";
			String p_error = this.getServletContext().getInitParameter("er");			
			cursor = new Cursor();
			util = new Util(cursor);
			rotacionDAO = new RotacionDAO(cursor);
			if (request.getParameter("tipo") == null || ((String) request.getParameter("tipo")).equals("")) {
				borrarBeans(request);
				tipo = 0;
			} else {
				tipo = Integer.parseInt((String) request.getParameter("tipo"));
			}
			if (!asignarBeans(request)) {
				request.setAttribute("mensaje", this.getMensaje("Error capturando datos de sesinn para el usuario"));
				return p_error;
			}
			Login login = (Login) session.getAttribute("login");
			Rotacion r = (Rotacion) session.getAttribute("rotacion");
			Rotacion r2 = (Rotacion) session.getAttribute("rotacionEditar");
			Horario horario = (Horario) session.getAttribute("horario");
			if (horario != null)
				horario.setInst(login.getInstId());
			switch (tipo) {
				case 11:case 21:case 31:case 51:case 61:case 71:case 81:case 121: case ParamsVO.FICHA_DOCENTE_GRUPO_NUEVO:case ParamsVO.FICHA_ESPACIO_GRADO:case 10:case 12:case 13:case 16:case 20:case 22:case 23:case 26:case 29:case 30:case 32:case 33:case 36:case 39:case 40:case 41:case 47:case 42:case 43:case 46:case 49:case 50:case 52:case 53:case 56:case 59:case 60:case 62:case 63:case 66:case 69:case 70:case 72:case 73:case 76:case 79:case 80:case 90:case 100:case 101:case 102:case 110:case 111:case 112:case 120:case 122:case 123:case 126:case 129:					
					break;
				default:
					tipo=11;
			}
			System.out.println("GUARDAR ROTACION=" + tipo);

			switch (tipo) {
			case 11:
				r.setRotstrjercodigo(rotacionDAO.obtenerJerar_1_6(r.getRotstrsede(), r.getRotstrjornada(), login));
				// VALIDAR LA ih DE LA ESTRUCTURA
				if (!rotacionDAO.validarIHEstructura(r, login)) {
					if (!rotacionDAO.getMensaje().equals("")) {
						getMensaje(rotacionDAO.getMensaje());
						restaurarBeans(request);
						return p_error;
					} else {
						request.removeAttribute("guia");
						request.setAttribute("refrescar", "1");
						borrarBeans(request);
						request.setAttribute("mensaje", getMensaje("La intensidad horaria de algunos grados es mayor que el total de horas asignadas a la estructura"));
					}
				}
				// GUARDAR ESTRUCUTRA
				else if (!r.getEstado().equals("1")) {// guardar Estructura
					if (!rotacionDAO.validaGradoEstructura(r)) {
						if (!rotacionDAO.getMensaje().equals("")) {
							request.setAttribute("mensaje", getMensaje(rotacionDAO.getMensaje()));
							restaurarBeans(request);
							return p_error;
						} else{
							request.setAttribute("mensaje", getMensaje("La estructura no puede ser creada debido a que algunos grados seleccionados estan asignados a otra estructura"));
						}	
					} else if (!rotacionDAO.insertarEstructura(r, login)) {
						request.setAttribute("mensaje", getMensaje(rotacionDAO.getMensaje()));
						return p_error;
					} else{
						request.setAttribute("mensaje", getMensaje("La información fue actualizada satisfactoriamente"));
					}
					request.removeAttribute("guia");
					request.setAttribute("refrescar", "1");
					borrarBeans(request);					
					siges.util.Logger.print(login != null ? login.getUsuarioId() : "", "Insercinn en Tabla 'rot_estructura'", 7, 1, this.toString());
				}
				// actualizar estrucutra
				if (r.getEstado().equals("1")) {// actualizar Estructura
					if (compararFichas(tipo, request)) {
						request.setAttribute("mensaje", getMensaje("La información fue actualizada satisfactoriamente"));
						return sig11;
					}
					if (!rotacionDAO.validaGradoEstructura(r)) {
						if (!rotacionDAO.getMensaje().equals("")) {
							request.setAttribute("mensaje", getMensaje(rotacionDAO.getMensaje()));
							restaurarBeans(request);
							return p_error;
						}else{
							request.setAttribute("mensaje", getMensaje("La estructura no puede ser actualizada debido hay que los algunos grados seleccionados estan asignados a otra estructura"));
						}	
					} else if (!rotacionDAO.actualizarEstructura(r, r2)) {
						request.setAttribute("mensaje", getMensaje(rotacionDAO.getMensaje()));
						restaurarBeans(request);
						return p_error;
					} else{
						request.setAttribute("mensaje", getMensaje("La información fue actualizada satisfactoriamente"));
					}
					request.removeAttribute("guia");
					request.setAttribute("refrescar", "1");
					borrarBeans(request);					
					siges.util.Logger.print(login != null ? login.getUsuarioId() : "", "Actualizacinn en Tabla 'rot_estructura'", 7, 1, this.toString());
				}
				sig = sig11;
				break;
			case 21:
				r.setRoteagjerjornada(rotacionDAO.obtenerJerar_1_6(r.getRoteagsede(), r.getRoteagjornada(), login));
				if (!r.getEstado().equals("1")) {// guardar espacio-grado-asignatura
					if (validarFijarEspacio(r.getRoteagjerjornada(), r.getRoteagespacio(), r.getRoteagasignatura(),r.getVigencia(),r.getRotstrmetodologia())) {
						request.setAttribute("mensaje", getMensaje("Ya existe una Relacinn Espacio-Grado-Asignatura, no se puede registrar"));
						return sig21;
					}
					if (!rotacionDAO.insertarFijarEspacio(r, login)) {
						request.setAttribute("mensaje", getMensaje(rotacionDAO.getMensaje()));
						return p_error;
					}
					request.removeAttribute("guia");
					request.setAttribute("refrescar", "1");
					request.setAttribute("mensaje", getMensaje("La información fue ingresada satisfactoriamente"));
					siges.util.Logger.print(login != null ? login.getUsuarioId() : "", "Insercinn en Tabla 'rot_espacio_asig_grado'", 7, 1, this.toString());
				}
				if (r.getEstado().equals("1")) {// actualizar
												// espacio-grado-asignatura
					if (compararFichas(tipo, request)) {
						request.setAttribute("mensaje", getMensaje("La información fue actualizada satisfactoriamente"));
						return sig21;
					}
					if (!rotacionDAO.actualizarFijarEspacio(r, r2, login)) {
						request.setAttribute("mensaje", getMensaje(rotacionDAO.getMensaje()));
						restaurarBeans(request);
						return p_error;
					}
					request.removeAttribute("guia");
					request.setAttribute("refrescar", "1");
					request.setAttribute("mensaje", getMensaje("La información fue actualizada satisfactoriamente"));
					siges.util.Logger.print(login != null ? login.getUsuarioId() : "", "Actualizacinn en Tabla 'rot_espacio_asig_grado'", 7, 1, this.toString());
				}
				//reset(r);
				borrarBeans(request);
				sig = sig21;
				break;
			case 31:
				r.setRotdagjerjornada(rotacionDAO.obtenerJerar_1_6(r.getRotdagsede(), r.getRotdagjornada(), login));
				if (!r.getEstado().equals("1")) {// guardar docente-grado-asignatura
					if (validarFijarDocente(r.getRotdagjerjornada(), r.getRotdagdocente(), r.getRotdagasignatura())) {
						request.setAttribute("mensaje", getMensaje("Ya existe una Relacinn Docente-Grado-Asignatura, no se puede registrar"));
						return sig31;
					}
					if (!rotacionDAO.insertarFijarDocente(r, login)) {
						request.setAttribute("mensaje", getMensaje(rotacionDAO.getMensaje()));
						return p_error;
					}
					request.removeAttribute("guia");
					request.setAttribute("refrescar", "1");
					borrarBeans(request);
					request.setAttribute("mensaje", getMensaje("La información fue ingresada satisfactoriamente"));
					siges.util.Logger.print(login != null ? login.getUsuarioId() : "", "Insercinn en Tabla 'rot_doc_asig_grado'", 7, 1, this.toString());
				}
				if (r.getEstado().equals("1")) {// actualizar
												// docente-grado-asignatura
					if (compararFichas(tipo, request)) {
						request.setAttribute("mensaje", getMensaje("La información fue actualizada satisfactoriamente"));
						return sig31;
					}
					if (!rotacionDAO.actualizarFijarDocente(r, r2)){
						request.setAttribute("mensaje", getMensaje(rotacionDAO.getMensaje()));
						restaurarBeans(request);
						return p_error;
					}
					request.removeAttribute("guia");
					request.setAttribute("refrescar", "1");
					borrarBeans(request);
					request.setAttribute("mensaje", getMensaje("La información fue actualizada satisfactoriamente"));
					siges.util.Logger.print(login != null ? login.getUsuarioId() : "", "Actualizacinn en Tabla 'rot_doc_asig_grado'", 7, 1, this.toString());
				}
				sig = sig31;
				break;
			case 41:
				if (!r.getEstado().equals("1")) {// guardar espacio-jornada
					if (validarEspFisJor(r.getRotefjestructura(), r.getRotefjespacio(), r.getRotefjhoraini(), r.getRotefjhorafin(), r.getRotefjdia())) {
						request.setAttribute("mensaje", getMensaje("Ya existe un Espacio Fisico Inhabilitado, no se puede registrar"));
						return sig41;
					}
					if (!rotacionDAO.insertarInhEspacios(r, login)) {
						request.setAttribute("mensaje", getMensaje(rotacionDAO.getMensaje()));
						return p_error;
					}
					request.removeAttribute("guia");
					request.setAttribute("refrescar", "1");
					borrarBeans(request);
					request.setAttribute("mensaje", getMensaje("La información fue ingresada satisfactoriamente"));
					siges.util.Logger.print(login != null ? login.getUsuarioId() : "", "Insercinn en Tabla 'rot_espfis_jor'", 7, 1, this.toString());
				}
				if (r.getEstado().equals("1")) {// actualizar espacio-jornada
					if (compararFichas(tipo, request)) {
						borrarBeans(request);
						request.setAttribute("mensaje", getMensaje("La información fue actualizada satisfactoriamente."));
						return sig41;
					}
					if (!rotacionDAO.actualizarInhEspacios(r, r2)) {
						request.setAttribute("mensaje", getMensaje(rotacionDAO.getMensaje()));
						restaurarBeans(request);
						return p_error;
					}
					request.removeAttribute("guia");
					request.setAttribute("refrescar", "1");
					borrarBeans(request);
					request.setAttribute("mensaje", getMensaje("La información fue actualizada satisfactoriamente"));
					siges.util.Logger.print(login != null ? login.getUsuarioId() : "", "Actualizacinn en Tabla 'rot_espfis_jor'", 7, 1, this.toString());
				}
				sig = sig41;
				break;
			case 51:// PARA GUARDAR O ACTUALIZAR ESTRUCTURA
				if (!r.getEstado().equals("1")) {// guardar inhabilitar
													// docente
					if (validarInhDocente(r.getRotihdestructura(), r.getRotihddocente(), r.getRotihdhoraini(), r.getRotihdhorafin(), r.getRotihddia())) {
						request.setAttribute("mensaje", getMensaje("Ya existe un Docente Inhabilitado para esta sede y jornada, no se puede registrar"));
						return sig51;
					}
					if (!rotacionDAO.insertarInhDocente(r, login)) {
						request.setAttribute("mensaje", getMensaje(rotacionDAO.getMensaje()));
						return p_error;
					}
					request.removeAttribute("guia");
					request.setAttribute("refrescar", "1");
					borrarBeans(request);
					request.setAttribute("mensaje", getMensaje("La información fue ingresada satisfactoriamente"));
					siges.util.Logger.print(login != null ? login.getUsuarioId() : "", "Insercinn en Tabla 'rot_inhabilitar'", 7, 1, this.toString());
				}
				if (r.getEstado().equals("1")) {// actualizar inhabilitar
												// docente
					if (compararFichas(tipo, request)) {
						request.setAttribute("mensaje", getMensaje("La información fue actualizada satisfactoriamente."));
						borrarBeans(request);
						return sig51;
					}
					if (!rotacionDAO.actualizarInhDocente(r, r2)) {
						request.setAttribute("mensaje", getMensaje(rotacionDAO.getMensaje()));
						restaurarBeans(request);
						return p_error;
					}
					request.removeAttribute("guia");
					request.setAttribute("refrescar", "1");
					borrarBeans(request);
					request.setAttribute("mensaje", getMensaje("La información fue actualizada satisfactoriamente"));
					siges.util.Logger.print(login != null ? login.getUsuarioId() : "", "Actualizacinn en Tabla 'rot_inhabilitar_docente'", 7, 1, this.toString());
				}
				sig = sig51;
				break;
			case 61:
				if (!r.getEstado().equals("1")) {// guardar fijar asignatura
					if (validarFijarAsignatura(r.getRotfasestructura(), r.getRotfasasignatura())) {
						request.setAttribute("mensaje", getMensaje("Ya existe una Asignatura Fijada para esta sede y jornada, no se puede registrar"));
						return sig61;
					}
					if (!rotacionDAO.insertarFijarAsignatura(r)) {
						request.setAttribute("mensaje", getMensaje(rotacionDAO.getMensaje()));
						return p_error;
					}
					request.removeAttribute("guia");
					request.setAttribute("refrescar", "1");
					borrarBeans(request);
					request.setAttribute("mensaje", getMensaje("La información fue ingresada satisfactoriamente"));
					siges.util.Logger.print(login != null ? login.getUsuarioId() : "", "Insercinn en Tabla 'rot_fijar_asignatura'", 7, 1, this.toString());
				}
				if (r.getEstado().equals("1")) {// actualizar inhabilitar
												// docente
					if (compararFichas(tipo, request)) {
						request.setAttribute("mensaje", getMensaje("La información fue actualizada satisfactoriamente"));
						return sig61;
					}
					if (!rotacionDAO.actualizarFijarAsignatura(r, r2)) {
						request.setAttribute("mensaje", getMensaje(rotacionDAO.getMensaje()));
						restaurarBeans(request);
						return p_error;
					}
					request.removeAttribute("guia");
					request.setAttribute("refrescar", "1");
					borrarBeans(request);
					request.setAttribute("mensaje", getMensaje("La información fue actualizada satisfactoriamente"));
					siges.util.Logger.print(login != null ? login.getUsuarioId() : "", "Actualizacinn en Tabla 'rot_fijar_asignatura'", 7, 1, this.toString());
				}
				sig = sig61;
				break;
			case 71:
				r.setRtefdojercodigo(rotacionDAO.obtenerJerar_1_6(r.getRtefdosede(), r.getRtefdojornada(), login));
				if (!r.getEstado().equals("1")) {// guardar fijar espacio por
													// docente
					if (validarFijarEspacioDocente(r.getRtefdoespacio(), r.getRtefdodocente(), r.getRtefdojercodigo(), r.getVigencia())) {
						request.setAttribute("mensaje", getMensaje("Ya existe una Asignatura Fijada para esta sede y jornada, no se puede registrar"));
						return sig71;
					}
					if (!rotacionDAO.insertarFijarEspacioDocente(r)) {
						request.setAttribute("mensaje", getMensaje(rotacionDAO.getMensaje()));
						return p_error;
					}
					request.removeAttribute("guia");
					request.setAttribute("refrescar", "1");
					borrarBeans(request);
					request.setAttribute("mensaje", getMensaje("La información fue ingresada satisfactoriamente"));
					siges.util.Logger.print(login != null ? login.getUsuarioId() : "", "Insercinn en Tabla 'rot_espacio_docente'", 7, 1, this.toString());
				}
				if (r.getEstado().equals("1")) {// actualizar fijar espacio por
												// docente
					if (compararFichas(tipo, request)) {
						request.setAttribute("mensaje", getMensaje("La información fue actualizada satisfactoriamente."));
						return sig71;
					}
					if (!rotacionDAO.actualizarFijarEspacioDocente(r, r2)) {
						
						request.setAttribute("mensaje", getMensaje(rotacionDAO.getMensaje()));
						restaurarBeans(request);
						return p_error;
					}
					request.removeAttribute("guia");
					request.setAttribute("refrescar", "1");
					borrarBeans(request);
					request.setAttribute("mensaje", getMensaje("La información fue actualizada satisfactoriamente"));
					siges.util.Logger.print(login != null ? login.getUsuarioId() : "", "Actualizacinn en Tabla 'rot_espacio_docente'", 7, 1, this.toString());
				}
				sig = sig71;
				break;
			case 81:
				r.setInstjerar_(rotacionDAO.obtenerJerar_1_4(login));
				if (!rotacionDAO.actualizarPrioridad(r, r2, login)) {
					request.setAttribute("mensaje", getMensaje(rotacionDAO.getMensaje()));
					restaurarBeans(request);
					return p_error;
				}
				request.removeAttribute("guia");
				request.setAttribute("refrescar", "1");
				borrarBeans(request);
				request.setAttribute("mensaje", getMensaje("La información fue actualizada satisfactoriamente"));
				siges.util.Logger.print(login != null ? login.getUsuarioId() : "", "Actualizacinn en Tabla 'rot_estructura_grado'", 7, 1, this.toString());
				sig = sig82;
				break;
			case 121:// PARA GUARDAR O ACTUALIZAR inhabilitar horas
				if (!r.getEstado().equals("1")) {// guardar inhabilitar HOras
					if (rotacionDAO.validarInhHora(r)) {
						request.setAttribute("mensaje", getMensaje("Ya existe una inhabilitacinn igual y no se puede registrar"));
						return sig121;
					}
					if (!rotacionDAO.insertarInhHora(r)) {
						request.setAttribute("mensaje", getMensaje(rotacionDAO.getMensaje()));
						return p_error;
					}
					request.removeAttribute("guia");
					request.setAttribute("refrescar", "1");
					borrarBeans(request);
					request.setAttribute("mensaje", getMensaje("La información fue ingresada satisfactoriamente"));
					siges.util.Logger.print(login != null ? login.getUsuarioId() : "", "Insercinn en Tabla 'rot_inhabilitar'", 7, 1, this.toString());
				}
				if (r.getEstado().equals("1")) {// actualizar inhabilitar HOras
					if (compararFichas(tipo, request)) {
						request.setAttribute("mensaje", getMensaje("La información fue actualizada satisfactoriamente"));
						borrarBeans(request);
						return sig121;
					}
					if (!rotacionDAO.actualizarInhHora(r, r2)) {
						request.setAttribute("mensaje", getMensaje(rotacionDAO.getMensaje()));
						restaurarBeans(request);
						return p_error;
					}
					request.removeAttribute("guia");
					request.setAttribute("refrescar", "1");
					borrarBeans(request);
					request.setAttribute("mensaje", getMensaje("La información fue actualizada satisfactoriamente"));
					siges.util.Logger.print(login != null ? login.getUsuarioId() : "", "Actualizacinn en Tabla 'rot_inhabilitar_docente'", 7, 1, this.toString());
				}
				sig = sig121;
				break;
			}

			return sig;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error " + this + ": " + e.toString());
			throw new ServletException(e);
		} finally {
			if (cursor != null)
				cursor.cerrar();
			if (util != null)
				util.cerrar();
		}
	}

	/**
	 * Funcinn: Compara los beans para encontrar algun cambio entre ellos<br>
	 * 
	 * @param
	 * @return
	 */
	public boolean compararFichas(int n, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Rotacion r = (Rotacion) session.getAttribute("rotacion");
		Rotacion r2 = (Rotacion) session.getAttribute("rotacionEditar");
		switch (n) {
		case 11:
			return rotacionDAO.compararBeansEstructura(r, r2);
		case 21:
			return rotacionDAO.compararBeansFijarEspacio(r, r2);
		case 31:
			return rotacionDAO.compararBeansFijarDocente(r, r2);
		case 41:
			return rotacionDAO.compararBeansInhEspacios(r, r2);
		case 51:
			return rotacionDAO.compararBeansInhDocente(r, r2);
		case 61:
			return rotacionDAO.compararBeansFijarAsignatura(r, r2);
		case 71:
			return rotacionDAO.compararBeansFijarEspacioDocente(r, r2);
		case 81:
			return rotacionDAO.compararBeansPriorizar(r, r2);
		case 121:
			return rotacionDAO.compararBeansInhHora(r, r2);
		default:
			return false;
		}
	}

	/**
	 * Funcinn: Validar existencia de centro<br>
	 * 
	 * @param
	 * @return
	 */
	public boolean validarFijarEspacioDocente(String cod, String cod2, String cod3,int vigencia) {
		String sentencia = "Select RTEFDOJERCODIGO from ROT_ESPACIO_DOCENTE where RTEFDOESPACIO = " + cod + " and RTEFDODOCENTE = " + cod2 + " and RTEFDOJERCODIGO=" + cod3 + " and RTEFDOVIGENCIA="+vigencia;
		if (cursor.existe(sentencia)) {
			return true;// si existe
		}
		return false;
	}

	/**
	 * Funcinn: Validar existencia de centro<br>
	 * 
	 * @param
	 * @return
	 */
	public boolean validarFijarDocente(String cod, String cod2, String cod3) {
		String sentencia = "Select rotdagjerjornada from rot_doc_asig_grado where rotdagjerjornada = " + cod + " and rotdagdocente = " + cod2 + " and rotdagasignatura=" + cod3 + "";
		//System.out.println("SENTENCIA: " + sentencia);
		if (cursor.existe(sentencia)) {
			return true;// si existe
		}
		return false;
	}

	/**
	 * Funcinn: Validar existencia de centro<br>
	 * 
	 * @param
	 * @return
	 */
	public boolean validarFijarAsignatura(String cod, String cod2) {
		String sentencia = "Select ROTFASESTRUCTURA from ROT_FIJAR_ASIGNATURA where ROTFASESTRUCTURA = " + cod + " and ROTFASASIGNATURA = " + cod2 + "";
		//System.out.println("SENTENCIA: " + sentencia);
		if (cursor.existe(sentencia)) {
			return true;// si existe
		}
		return false;
	}

	/**
	 * Funcinn: Validar existencia de centro<br>
	 * 
	 * @param
	 * @return
	 */
	public boolean validarFijarEspacio(String cod, String cod2, String cod3, int cod4,String met) {
		String sentencia = "Select roteagjerjornada from rot_espacio_asig_grado where roteagjerjornada = " + cod + " and roteagespacio = " + cod2 + " and roteagasignatura=" + cod3 + " AND ROTEAGVIGENCIA="+cod4 +" AND ROTEAGMETOD="+met;
		if (cursor.existe(sentencia)) {
			return true;// si existe
		}
		return false;
	}

	/**
	 * Funcinn: Validar existencia de centro<br>
	 * 
	 * @param
	 * @return
	 */
	public boolean validarEspFisJor(String cod, String cod2, String cod3, String cod4, String cod5) {
		String sentencia = "Select rotefjestructura from rot_espfis_jor where rotefjestructura = " + cod + " and rotefjespacio = " + cod2 + " and ROTEFJDIA=" + cod5 + " and (" + cod3 + " between ROTEFJHORAINI and ROTEFJHORAFIN or " + cod4 + " between ROTEFJHORAINI and ROTEFJHORAFIN)";
		//System.out.println("valida: " + sentencia);
		if (cursor.existe(sentencia)) {
			return true;// si existe
		}
		return false;
	}

	/**
	 * Funcinn: Validar existencia de centro<br>
	 * 
	 * @param
	 * @return
	 */
	public boolean validarInhDocente(String cod, String cod2, String cod3, String cod4, String cod5) {
		String sentencia = "Select rotihdestructura from rot_inhabilitar_docente where rotihdestructura = " + cod + " and rotihddocente = " + cod2 + " and rotihddia=" + cod5 + " and (" + cod3 + " between rotihdhoraini and rotihdhorafin or " + cod4 + " between rotihdhoraini and rotihdhorafin)";
		if (cursor.existe(sentencia)) {
			return true;// si existe
		}
		return false;
	}

	/**
	 * Funcinn: Validar existencia de centro<br>
	 * 
	 * @param
	 * @return
	 */
	public boolean validarEstructura(String cod) {
		String sentencia = "Select rotstrcodigo from rot_estructura where rotstrjercodigo = " + cod + "";
		if (cursor.existe(sentencia)) {
			return true;// si existe
		}
		return false;
	}

	/**
	 * Funcinn: Inserta los valores por defecto para el bean de información
	 * bnsica con respecto a la informacion de la institucinn educativa
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeans(HttpServletRequest request) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("login") == null) {
			return false;
		}
		return true;
	}

	/**
	 * Funcinn: Elimina del contexto de la sesion los beans de informacion del
	 * usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void borrarBeans(HttpServletRequest request) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("rotacion");
		session.removeAttribute("rotacionEditar");
	}

	/**
	 * Referencia al bean del usuario con la información proporcionada por el
	 * bean de respaldo
	 * 
	 * @param int
	 *            n
	 * @param HttpServletRequest
	 *            request
	 */
	public void restaurarBeans(HttpServletRequest request) throws ServletException, IOException {
		HttpSession session = request.getSession();
		//Rotacion r = (Rotacion) session.getAttribute("rotacion");
		Rotacion r2 = (Rotacion) session.getAttribute("rotacionEditar");
		session.setAttribute("rotacion", (Rotacion) r2.clone());
	}

	/**
	 * Funcinn: Recibe la peticion por el metodo get de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);// redirecciona la peticion a doPost
	}

	/**
	 * Funcinn: Recibe la peticion por el metodo Post de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String s = process(request, response);
		if (s != null && !s.equals(""))
			ir(1, s, request, response);
	}

	/**
	 * Funcinn: Redirige el control a otro servlet
	 * 
	 * @param int
	 *            a: 1=redirigir como 'include', 2=redirigir como 'forward'
	 * @param String
	 *            s
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public void ir(int a, String s, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (cursor != null)
			cursor.cerrar();
		RequestDispatcher rd = getServletContext().getRequestDispatcher(s);
		if (a == 1)
			rd.include(request, response);
		else
			rd.forward(request, response);
	}

	/**
	 * Funcinn: Concatena el parametro en una variable que contiene los mensajes
	 * que se van a enviar a la vista
	 * 
	 * @param String
	 *            s
	 */
	public String getMensaje(String s) {
		return "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n"+"  - " + s + "\n";
	}
}