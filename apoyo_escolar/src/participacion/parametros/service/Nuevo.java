package participacion.parametros.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.service.Service;
import siges.common.vo.Params;
import participacion.parametros.dao.ParametrosDAO;
import participacion.parametros.vo.DiscapacidadVO;
import participacion.parametros.vo.EtniaVO;
import participacion.parametros.vo.LgtbVO;
import participacion.parametros.vo.OcupacionVO;
import participacion.parametros.vo.ParamsVO;
import participacion.parametros.vo.RolVO;

import siges.dao.Cursor;
import siges.login.beans.Login;
import siges.perfil.dao.PerfilDAO;

public class Nuevo extends Service {
	// private UniversidadDAO universidad;
	private Cursor c;
	private String FICHA_NUEVO_DISCAPACIDAD;
	private String FICHA_NUEVO_ETNIA;
	private String FICHA_NUEVO_LGTB;
	private String FICHA_NUEVO_OCUPACION;
	private String FICHA_NUEVO_ROL;
	private String FICHA;

	private ParametrosDAO parametrosDAO;

	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String dispatcher[] = new String[2];
		HttpSession session = request.getSession();
		// objeto usuario con datos del mismo
		Login usuVO = (Login) session.getAttribute("login");
		// **********
		c = new Cursor();
		parametrosDAO = new ParametrosDAO(c);

		DiscapacidadVO discapacidadVO = (DiscapacidadVO) session
				.getAttribute("discapacidadVO");
		DiscapacidadVO discapacidadVO2 = (DiscapacidadVO) session
				.getAttribute("discapacidadVO2");

		EtniaVO etniaVO = (EtniaVO) session.getAttribute("etniaVO");
		EtniaVO etniaVO2 = (EtniaVO) session.getAttribute("etniaVO");

		LgtbVO lgtbVO = (LgtbVO) session.getAttribute("lgtbVO");
		LgtbVO lgtbVO2 = (LgtbVO) session.getAttribute("lgtbVO2");

		OcupacionVO ocupacionVO = (OcupacionVO) session
				.getAttribute("ocupacionVO");
		OcupacionVO ocupacionVO2 = (OcupacionVO) session
				.getAttribute("ocupacionVO2");

		RolVO rolVO = (RolVO) session.getAttribute("rolVO");
		RolVO rolVO2 = (RolVO) session.getAttribute("rolVO2");

		// c=new Cursor();
		// universidad=new UniversidadDAO(c);

		FICHA_NUEVO_DISCAPACIDAD = config
				.getInitParameter("FICHA_NUEVO_DISCAPACIDAD");
		FICHA_NUEVO_ETNIA = config.getInitParameter("FICHA_NUEVO_ETNIA");
		FICHA_NUEVO_LGTB = config.getInitParameter("FICHA_NUEVO_LGTB");
		FICHA_NUEVO_OCUPACION = config
				.getInitParameter("FICHA_NUEVO_OCUPACION");
		FICHA_NUEVO_ROL = config.getInitParameter("FICHA_NUEVO_ROL");

		int CMD = getCmd(request, session, Params.CMD_NUEVO);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		String codDiscapacidad = request.getParameter("id");
		String codEtnia = request.getParameter("id2");
		String codLgtb = request.getParameter("id3");
		String codOcupacion = request.getParameter("id4");
		String codRol = request.getParameter("id5");

		// System.out.println(" PARTICIPACION PARAMETROS TIPO " + TIPO + " CMD "
		// + CMD );

		switch (TIPO) {
		// Discapacidad --------------
		case ParamsVO.FICHA_DISCAPACIDAD:
			switch (CMD) {
			case Params.CMD_GUARDAR:
				// Guardar*******************************
				if (discapacidadVO.getFormaEstado().equals("1")) {
					// Actualizar****************************
					// if(universidadVO.getGuniCodigo()==universidadVO2.getGuniCodigo()){

					if (parametrosDAO.actualizarDiscapacidad(discapacidadVO,
							discapacidadVO2)) {
						request.setAttribute(Params.SMS,
								"La información fue actualizada satisfactoriamente");
						session.removeAttribute("discapacidadVO");
						session.removeAttribute("discapacidadVO2");
					} else {
						request.setAttribute(Params.SMS,
								"La información no fue actualizada "
										+ parametrosDAO.getMensaje());
						session.setAttribute("discapacidadVO", discapacidadVO);
						session.removeAttribute("discapacidadVO");
						session.removeAttribute("discapacidadVO2");
					}
				} else {
					if (parametrosDAO.insertarDiscapacidad(discapacidadVO)) {
						request.setAttribute(Params.SMS,
								"La información fue ingresada satisfactoriamente");
						session.removeAttribute("discapacidadVO");
					} else {
						request.setAttribute(Params.SMS,
								"La información no fue ingresada "
										+ parametrosDAO.getMensaje());
					}
				}

				break;

			case Params.CMD_EDITAR:
				discapacidadVO = parametrosDAO
						.asignarDiscapacidad(codDiscapacidad);

				if (discapacidadVO != null) {
					session.setAttribute("discapacidadVO", discapacidadVO);
					session.setAttribute("discapacidadVO",
							discapacidadVO.clone());
					request.setAttribute("guia", codDiscapacidad);

				} else {
					request.setAttribute(Params.SMS,
							"No se logro obtener los datos del objeto seleccionado");
				}
				break;
			case Params.CMD_ELIMINAR:
				// *************************************************************

				/*
				 * if(universidadDAO.valPadre(codUniversidad)){
				 * request.setAttribute
				 * (Params.SMS,"La universidad esta asociada con una Especialidad"
				 * ); }
				 */
				if (parametrosDAO.eliminarDiscapacidad(codDiscapacidad)) {
					request.setAttribute(Params.SMS,
							"La información fue eliminada satisfactoriamente");
					session.removeAttribute("discapacidadVO");
					session.removeAttribute("discapacidadVO2");
				}

				else {
					request.setAttribute(
							Params.SMS,
							"La información no fue eliminada, "
									+ parametrosDAO.getMensaje());
				}
				break;
			case Params.CMD_NUEVO:
				session.removeAttribute("discapacidadVO");
				session.removeAttribute("discapacidadVO2");
				break;
			}
			FICHA = FICHA_NUEVO_DISCAPACIDAD;
			break;
		// Etnias
		case ParamsVO.FICHA_ETNIA:
			switch (CMD) {
			case Params.CMD_GUARDAR:
				// System.out.println("insercionEtnia ");
				// Guardar*******************************
				if (etniaVO.getFormaEstado().equals("1")) {
					// Actualizar****************************
					// if(universidadVO.getGuniCodigo()==universidadVO2.getGuniCodigo()){

					if (parametrosDAO.actualizarEtnia(etniaVO, etniaVO2)) {
						request.setAttribute(Params.SMS,
								"La información fue actualizada satisfactoriamente");
						session.removeAttribute("etniaVO");
						session.removeAttribute("etniaVO2");
					} else {
						request.setAttribute(Params.SMS,
								"La información no fue actualizada "
										+ parametrosDAO.getMensaje());
						session.setAttribute("etniaVO", etniaVO);
						session.removeAttribute("etniaVO");
						session.removeAttribute("etniaVO2");
					}
				} else {
					if (parametrosDAO.insertarEtnia(etniaVO)) {
						request.setAttribute(Params.SMS,
								"La información fue ingresada satisfactoriamente");
						session.removeAttribute("etniaVO");
					} else {
						request.setAttribute(Params.SMS,
								"La información no fue ingresada "
										+ parametrosDAO.getMensaje());
					}

				}
				// System.out.println("insercionEtnia");
				break;

			case Params.CMD_EDITAR:
				etniaVO = parametrosDAO.asignarEtnia(codEtnia);

				if (discapacidadVO != null) {
					session.setAttribute("etniaVO", etniaVO);
					session.setAttribute("etniaVO2", etniaVO.clone());
					request.setAttribute("guia", codDiscapacidad);

				} else {
					request.setAttribute(Params.SMS,
							"No se logro obtener los datos del objeto seleccionado");
				}
				break;
			case Params.CMD_ELIMINAR:
				// *************************************************************

				/*
				 * if(universidadDAO.valPadre(codUniversidad)){
				 * request.setAttribute
				 * (Params.SMS,"La universidad esta asociada con una Especialidad"
				 * ); }
				 */
				if (parametrosDAO.eliminarEtnia(codEtnia)) {
					request.setAttribute(Params.SMS,
							"La información fue eliminada satisfactoriamente");
					session.removeAttribute("etniaVO");
					session.removeAttribute("etniaVO2");
				}

				else {
					request.setAttribute(
							Params.SMS,
							"La información no fue eliminada, "
									+ parametrosDAO.getMensaje());
				}
				break;
			case Params.CMD_NUEVO:
				session.removeAttribute("etniaVO");
				session.removeAttribute("etniaVO2");
				break;
			}
			FICHA = FICHA_NUEVO_ETNIA;
			break;
		// LGTB
		case ParamsVO.FICHA_LGTB:
			switch (CMD) {
			case Params.CMD_GUARDAR:
				// Guardar*******************************
				if (lgtbVO.getFormaEstado().equals("1")) {
					// Actualizar****************************
					// if(universidadVO.getGuniCodigo()==universidadVO2.getGuniCodigo()){

					if (parametrosDAO.actualizarLgtb(lgtbVO, lgtbVO2)) {
						request.setAttribute(Params.SMS,
								"La información fue actualizada satisfactoriamente");
						session.removeAttribute("lgtbVO");
						session.removeAttribute("lgtbVO2");
					} else {
						request.setAttribute(Params.SMS,
								"La información no fue actualizada "
										+ parametrosDAO.getMensaje());
						session.setAttribute("lgtbVO", lgtbVO);
						session.removeAttribute("lgtbVO");
						session.removeAttribute("lgtbVO2");
					}
				} else {
					if (parametrosDAO.insertarLgtb(lgtbVO)) {
						request.setAttribute(Params.SMS,
								"La información fue ingresada satisfactoriamente");
						session.removeAttribute("lgtbVO");
					} else {
						request.setAttribute(Params.SMS,
								"La información no fue ingresada "
										+ parametrosDAO.getMensaje());
					}
				}

				break;

			case Params.CMD_EDITAR:
				lgtbVO = parametrosDAO.asignarLgtb(codLgtb);

				if (lgtbVO != null) {
					session.setAttribute("lgtbVO", lgtbVO);
					session.setAttribute("lgtbVO2", lgtbVO.clone());
					request.setAttribute("guia", codLgtb);

				} else {
					request.setAttribute(Params.SMS,
							"No se logro obtener los datos del objeto seleccionado");
				}
				break;
			case Params.CMD_ELIMINAR:
				// *************************************************************

				/*
				 * if(universidadDAO.valPadre(codUniversidad)){
				 * request.setAttribute
				 * (Params.SMS,"La universidad esta asociada con una Especialidad"
				 * ); }
				 */
				if (parametrosDAO.eliminarLgtb(codLgtb)) {
					request.setAttribute(Params.SMS,
							"La información fue eliminada satisfactoriamente");
					session.removeAttribute("lgtbVO");
					session.removeAttribute("lgtbVO2");
				}

				else {
					request.setAttribute(
							Params.SMS,
							"La información no fue eliminada, "
									+ parametrosDAO.getMensaje());
				}
				break;
			case Params.CMD_NUEVO:
				session.removeAttribute("lgtbVO");
				session.removeAttribute("lgtbVO2");
				break;
			}
			FICHA = FICHA_NUEVO_LGTB;
			break;
		// ocupacion
		case ParamsVO.FICHA_OCUPACION:
			switch (CMD) {
			case Params.CMD_GUARDAR:
				// Guardar*******************************
				if (ocupacionVO.getFormaEstado().equals("1")) {
					// Actualizar****************************
					// if(universidadVO.getGuniCodigo()==universidadVO2.getGuniCodigo()){

					if (parametrosDAO.actualizarOcupacion(ocupacionVO,
							ocupacionVO2)) {
						request.setAttribute(Params.SMS,
								"La información fue actualizada satisfactoriamente");
						session.removeAttribute("ocupacionVO");
						session.removeAttribute("ocupacionVO2");
					} else {
						request.setAttribute(Params.SMS,
								"La información no fue actualizada "
										+ parametrosDAO.getMensaje());
						session.setAttribute("ocupacionVO", ocupacionVO);
						session.removeAttribute("ocupacionVO");
						session.removeAttribute("ocupacionVO2");
					}
				} else {
					if (parametrosDAO.insertarOcupacion(ocupacionVO)) {
						request.setAttribute(Params.SMS,
								"La información fue ingresada satisfactoriamente");
						session.removeAttribute("ocupacionVO");
					} else {
						request.setAttribute(Params.SMS,
								"La información no fue ingresada "
										+ parametrosDAO.getMensaje());
					}
				}

				break;

			case Params.CMD_EDITAR:
				ocupacionVO = parametrosDAO.asignarOcupacion(codOcupacion);

				if (ocupacionVO != null) {
					session.setAttribute("ocupacionVO", ocupacionVO);
					session.setAttribute("ocupacionVO2", ocupacionVO.clone());
					request.setAttribute("guia", codOcupacion);

				} else {
					request.setAttribute(Params.SMS,
							"No se logro obtener los datos del objeto seleccionado");
				}
				break;
			case Params.CMD_ELIMINAR:
				// *************************************************************

				/*
				 * if(universidadDAO.valPadre(codUniversidad)){
				 * request.setAttribute
				 * (Params.SMS,"La universidad esta asociada con una Especialidad"
				 * ); }
				 */
				if (parametrosDAO.eliminarOcupacion(codOcupacion)) {
					request.setAttribute(Params.SMS,
							"La información fue eliminada satisfactoriamente");
					session.removeAttribute("ocupacionVO");
					session.removeAttribute("ocupacionVO2");
				}

				else {
					request.setAttribute(
							Params.SMS,
							"La información no fue eliminada, "
									+ parametrosDAO.getMensaje());
				}
				break;
			case Params.CMD_NUEVO:
				session.removeAttribute("ocupacionVO");
				session.removeAttribute("ocupacionVO2");
				break;
			}
			FICHA = FICHA_NUEVO_OCUPACION;
			break;
		// ROL
		case ParamsVO.FICHA_ROL:
			switch (CMD) {
			case Params.CMD_GUARDAR:
				// Guardar*******************************
				if (rolVO.getFormaEstado().equals("1")) {
					// Actualizar****************************
					// if(universidadVO.getGuniCodigo()==universidadVO2.getGuniCodigo()){

					if (parametrosDAO.actualizarRol(rolVO, rolVO2)) {
						request.setAttribute(Params.SMS,
								"La información fue actualizada satisfactoriamente");
						session.removeAttribute("rolVO");
						session.removeAttribute("rolVO2");
					} else {
						request.setAttribute(Params.SMS,
								"La información no fue actualizada "
										+ parametrosDAO.getMensaje());
						session.setAttribute("rolVO", rolVO);
						session.removeAttribute("rolVO");
						session.removeAttribute("rolVO2");
					}
				} else {
					if (parametrosDAO.insertarRol(rolVO)) {
						request.setAttribute(Params.SMS,
								"La información fue ingresada satisfactoriamente");
						session.removeAttribute("rolVO");
					} else {
						request.setAttribute(Params.SMS,
								"La información no fue ingresada "
										+ parametrosDAO.getMensaje());
					}
				}

				break;

			case Params.CMD_EDITAR:
				rolVO = parametrosDAO.asignarRol(codRol);

				if (rolVO != null) {
					session.setAttribute("rolVO", rolVO);
					session.setAttribute("rolVO2", rolVO.clone());
					request.setAttribute("guia", codRol);

				} else {
					request.setAttribute(Params.SMS,
							"No se logro obtener los datos del objeto seleccionado");
				}
				break;
			case Params.CMD_ELIMINAR:
				// *************************************************************

				/*
				 * if(universidadDAO.valPadre(codUniversidad)){
				 * request.setAttribute
				 * (Params.SMS,"La universidad esta asociada con una Especialidad"
				 * ); }
				 */
				if (parametrosDAO.eliminarRol(codRol)) {
					request.setAttribute(Params.SMS,
							"La información fue eliminada satisfactoriamente");
					session.removeAttribute("rolVO");
					session.removeAttribute("rolVO2");
				}

				else {
					request.setAttribute(
							Params.SMS,
							"La información no fue eliminada, "
									+ parametrosDAO.getMensaje());
				}
				break;
			case Params.CMD_NUEVO:
				session.removeAttribute("rolVO");
				session.removeAttribute("rolVO2");
				break;
			}
			FICHA = FICHA_NUEVO_ROL;

		}
		dispatcher[0] = String.valueOf(Params.FORWARD);
		// System.out.println("FICHA " + FICHA);
		dispatcher[1] = FICHA;
		return dispatcher;
	}
}
