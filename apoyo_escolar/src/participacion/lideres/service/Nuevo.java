/**
 * 
 */
package participacion.lideres.service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.sql.Connection;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import participacion.lideres.dao.LideresDAO;
import participacion.lideres.vo.LideresVO;
import participacion.lideres.vo.FiltroLideresVO;
import participacion.lideres.vo.ParamsVO;
import participacion.lideres.vo.ResultadoReportesVO;
import participacion.common.exception.IntegrityException;
import participacion.common.exception.ParticipacionException;
import participacion.common.vo.ParamParticipacion;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.login.beans.Login;
import siges.util.Logger;
import siges.dao.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.servlet.ServletOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.servlet.ServletContext;

import org.apache.commons.io.CopyUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;

/**
 * 28/04/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class Nuevo extends Service {
	private static final long serialVersionUID = 1L;
	public String FICHA_FILTRO;
	public String FICHA_LIDERES;
	public String FICHA_LIDERES_FILTRO;
	public String FICHA_LIDERES_OFICIALES;
	public String FICHA_LIDERES_OFICIALES_LOCALIDAD;
	public String FICHA_LIDERES_OFICIALES_DEPARTAMENTO;
	private String contextoTotal;
	/**
	 * 
	 */
	private LideresDAO lideresDAO = new LideresDAO(new Cursor());
	private ResourceBundle rb;

	/*
	 * @function:
	 * 
	 * @param config
	 * 
	 * @throws ServletException (non-Javadoc)
	 * 
	 * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		FICHA_FILTRO = config.getInitParameter("FICHA_FILTRO");
		FICHA_LIDERES = config.getInitParameter("FICHA_LIDERES");
		FICHA_LIDERES_FILTRO = config.getInitParameter("FICHA_LIDERES_FILTRO");
		FICHA_LIDERES_OFICIALES = config
				.getInitParameter("FICHA_LIDERES_OFICIALES");
		FICHA_LIDERES_OFICIALES_LOCALIDAD = config
				.getInitParameter("FICHA_LIDERES_OFICIALES_LOCALIDAD");
		FICHA_LIDERES_OFICIALES_DEPARTAMENTO = config
				.getInitParameter("FICHA_LIDERES_OFICIALES_DEPARTAMENTO");
	}

	/*
	 * Procesa la peticinn
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
	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		rb = ResourceBundle.getBundle("participacion.lideres.bundle.lideres");
		String dispatcher[] = new String[2];
		String FICHA = null;
		HttpSession session = request.getSession();
		Login usuVO = (Login) session.getAttribute("login");
		FiltroLideresVO filtroLideres = (FiltroLideresVO) session
				.getAttribute("filtroLideresVO");
		LideresVO lideres = (LideresVO) session.getAttribute("lideresVO");
		int CMD = getCmd(request, session, ParamsVO.CMD_NUEVO);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		int codigoSectorInstitucion = 0;
		ServletContext context = (ServletContext) request.getSession()
				.getServletContext();
		contextoTotal = context.getRealPath("/");
		int nivel = Integer.parseInt(usuVO.getNivel());
		/*
		 * MODIFICACION 08-03-10 VALIDACION TIPO DE ROL NUEVO 2, FORMULARIO
		 * MOSTRAR TODOS LOS DATOS
		 */
		int tipoRol = 0;
		if (filtroLideres != null) {
			tipoRol = lideresDAO.getTipoRolVal(filtroLideres.getFilRol());
		} else {
			tipoRol = -1;
		}
	
		if (nivel == ParamParticipacion.LOGIN_NIVEL_DEPARTAMENTO) {
			if (tipoRol == 2) {
				FICHA_LIDERES = config.getInitParameter("FICHA_LIDERES");
			} else
				FICHA_LIDERES = config
						.getInitParameter("FICHA_LIDERES_OFICIALES_DEPARTAMENTO");

			switch (TIPO) {
			case ParamsVO.FICHA_LIDERES:
				FICHA = FICHA_LIDERES;
				switch (CMD) {
				case ParamsVO.CMD_BUSCAR:
					
					lideresBuscar(request, session, usuVO, filtroLideres,
							codigoSectorInstitucion);
					lideresCancelar(request, session, usuVO);
					lideresNuevoOficial(request, session, usuVO, null,
							filtroLideres);
					lideresInit(request, session, usuVO, filtroLideres, lideres);
					request.setAttribute("nuevoRegistro", "false");
					break;
				case ParamsVO.CMD_EDITAR:
					Logger.print(usuVO.getUsuarioId(), "Editar de Participantes. ",
							7, 1, this.toString());
					codigoSectorInstitucion = getSectorInstitucion2(request,
							session,
							String.valueOf(filtroLideres.getFilColegio()));
					if (codigoSectorInstitucion == 1
							|| codigoSectorInstitucion == 2
							|| codigoSectorInstitucion == 3) {
						lideres = lideresEditarOficial(request, session, usuVO,
								filtroLideres);
						lideresBuscar(request, session, usuVO, filtroLideres,
								codigoSectorInstitucion);
						lideresInit(request, session, usuVO, filtroLideres,
								lideres);
						request.setAttribute("nuevoRegistro", "true");
					} else {
						FICHA_LIDERES = config
								.getInitParameter("FICHA_LIDERES");
						switch (TIPO) {
						case ParamsVO.FICHA_LIDERES:
							FICHA = FICHA_LIDERES;
							switch (CMD) {
							case ParamsVO.CMD_BUSCAR_PADRE_FAMILIA:
								lideresNuevoOficial(request, session, usuVO, null,
										filtroLideres);
								lideresBuscar(request, session, usuVO, filtroLideres,
										codigoSectorInstitucion);
								lideresInit(request, session, usuVO, filtroLideres,
										lideres);
								buscarPadreDeFamilia(request, session, usuVO, filtroLideres, lideres);

								
								break;
							case ParamsVO.CMD_BUSCAR:
								lideresBuscar(request, session, usuVO,
										filtroLideres, codigoSectorInstitucion);
								lideresCancelar(request, session, usuVO);
								lideresNuevo(request, session, usuVO, null,
										filtroLideres);
								request.setAttribute("nuevoRegistro", "false");
								break;
							case ParamsVO.CMD_EDITAR:
								lideres = lideresEditar(request, session,
										usuVO, filtroLideres);
								lideresBuscar(request, session, usuVO,
										filtroLideres, codigoSectorInstitucion);
								request.setAttribute("nuevoRegistro", "true");
								break;
							case ParamsVO.CMD_ELIMINAR:
								lideresEliminar(request, session, usuVO,
										filtroLideres);
								lideresBuscar(request, session, usuVO,
										filtroLideres, codigoSectorInstitucion);
								lideresNuevo(request, session, usuVO, lideres,
										filtroLideres);
								request.setAttribute("nuevoRegistro", "false");
								break;
							case ParamsVO.CMD_GUARDAR:
								lideresGuardar(request, session, usuVO,
										lideres, codigoSectorInstitucion);
								lideresBuscar(request, session, usuVO,
										filtroLideres, codigoSectorInstitucion);
								request.setAttribute("nuevoRegistro", "true");
								break;
							case ParamsVO.CMD_CANCELAR:
								lideresCancelar(request, session, usuVO);
								lideresNuevo(request, session, usuVO, null,
										filtroLideres);
								lideresBuscar(request, session, usuVO,
										filtroLideres, codigoSectorInstitucion);
								request.setAttribute("nuevoRegistro", "true");
								break;
							case ParamsVO.CMD_NUEVO:
								lideresNuevo(request, session, usuVO, lideres,
										filtroLideres);
								lideresBuscar(request, session, usuVO,
										filtroLideres, codigoSectorInstitucion);
								request.setAttribute("nuevoRegistro", "false");
								break;
							case ParamsVO.CMD_GENERAR:
								generarReporteParticipantes(request, response,
										session, usuVO, filtroLideres,
										codigoSectorInstitucion);
								request.setAttribute("nuevoRegistro", "false");
								FICHA = null;
								break;

							}
							lideresInit(request, session, usuVO, filtroLideres,
									lideres);
							break;
						}
					}

					break;

				case ParamsVO.CMD_ELIMINAR:
					Logger.print(usuVO.getUsuarioId(), "Eliminacion de Participantes. ",
							7, 1, this.toString());
					lideresEliminar(request, session, usuVO, filtroLideres);
					lideresBuscar(request, session, usuVO, filtroLideres,
							codigoSectorInstitucion);
					lideresNuevoOficial(request, session, usuVO, lideres,
							filtroLideres);
					lideresInit(request, session, usuVO, filtroLideres, lideres);
					request.setAttribute("nuevoRegistro", "false");
					break;

				case ParamsVO.CMD_GUARDAR:
					Logger.print(usuVO.getUsuarioId(), "Guardar Participantes. ",
							7, 1, this.toString());
					codigoSectorInstitucion = getSectorInstitucion2(request,
							session,
							String.valueOf(filtroLideres.getFilColegio()));
					if (codigoSectorInstitucion == 1
							|| codigoSectorInstitucion == 2
							|| codigoSectorInstitucion == 3) {
						if (tipoRol == 2) {
							lideresGuardar(request, session, usuVO, lideres,
									codigoSectorInstitucion);
						} else
							lideresGuardarOficial(request, session, usuVO,
									lideres, codigoSectorInstitucion);
						lideresBuscar(request, session, usuVO, filtroLideres,
								codigoSectorInstitucion);
						lideresInit(request, session, usuVO, filtroLideres,
								lideres);
						request.setAttribute("nuevoRegistro", "true");
					} else {
						FICHA_LIDERES = config
								.getInitParameter("FICHA_LIDERES");
						switch (TIPO) {
						case ParamsVO.FICHA_LIDERES:
							FICHA = FICHA_LIDERES;
							switch (CMD) {
							case ParamsVO.CMD_BUSCAR_PADRE_FAMILIA:
								lideresNuevoOficial(request, session, usuVO, null,
										filtroLideres);
								lideresBuscar(request, session, usuVO, filtroLideres,
										codigoSectorInstitucion);
								lideresInit(request, session, usuVO, filtroLideres,
										lideres);
								buscarPadreDeFamilia(request, session, usuVO, filtroLideres, lideres);

								
								break;
							case ParamsVO.CMD_BUSCAR:
								lideresBuscar(request, session, usuVO,
										filtroLideres, codigoSectorInstitucion);
								lideresCancelar(request, session, usuVO);
								lideresNuevo(request, session, usuVO, null,
										filtroLideres);
								request.setAttribute("nuevoRegistro", "false");
								break;
							case ParamsVO.CMD_EDITAR:
								lideres = lideresEditar(request, session,
										usuVO, filtroLideres);
								lideresBuscar(request, session, usuVO,
										filtroLideres, codigoSectorInstitucion);
								request.setAttribute("nuevoRegistro", "true");
								break;
							case ParamsVO.CMD_ELIMINAR:
								lideresEliminar(request, session, usuVO,
										filtroLideres);
								lideresBuscar(request, session, usuVO,
										filtroLideres, codigoSectorInstitucion);
								lideresNuevo(request, session, usuVO, lideres,
										filtroLideres);
								request.setAttribute("nuevoRegistro", "false");
								break;
							case ParamsVO.CMD_GUARDAR:
								lideresGuardar(request, session, usuVO,
										lideres, codigoSectorInstitucion);
								lideresBuscar(request, session, usuVO,
										filtroLideres, codigoSectorInstitucion);
								request.setAttribute("nuevoRegistro", "true");
								break;
							case ParamsVO.CMD_CANCELAR:
								lideresCancelar(request, session, usuVO);
								lideresNuevo(request, session, usuVO, null,
										filtroLideres);
								lideresBuscar(request, session, usuVO,
										filtroLideres, codigoSectorInstitucion);
								request.setAttribute("nuevoRegistro", "true");
								break;
							case ParamsVO.CMD_NUEVO:
								lideresNuevo(request, session, usuVO, lideres,
										filtroLideres);
								lideresBuscar(request, session, usuVO,
										filtroLideres, codigoSectorInstitucion);
								request.setAttribute("nuevoRegistro", "false");
								break;
							case ParamsVO.CMD_GENERAR:
								generarReporteParticipantes(request, response,
										session, usuVO, filtroLideres,
										codigoSectorInstitucion);
								request.setAttribute("nuevoRegistro", "false");
								FICHA = null;
								break;

							}
							lideresInit(request, session, usuVO, filtroLideres,
									lideres);
							break;
						}
					}
					break;

				case ParamsVO.CMD_CANCELAR:
					codigoSectorInstitucion = getSectorInstitucion2(request,session,String.valueOf(filtroLideres.getFilColegio()));
					if (codigoSectorInstitucion == 1 || codigoSectorInstitucion == 2 || codigoSectorInstitucion == 3) {
						lideresCancelar(request, session, usuVO);
						lideresNuevoOficial(request, session, usuVO, null, filtroLideres);
						lideresBuscar(request, session, usuVO, filtroLideres, codigoSectorInstitucion);
						lideresInit(request, session, usuVO, filtroLideres, lideres);
						request.setAttribute("nuevoRegistro", "true");
					} else {
						FICHA_LIDERES = config.getInitParameter("FICHA_LIDERES");
						switch (TIPO) {
						case ParamsVO.FICHA_LIDERES:
							FICHA = FICHA_LIDERES;
							switch (CMD) {
							case ParamsVO.CMD_BUSCAR_PADRE_FAMILIA:
								buscarPadreDeFamilia(request, session, usuVO, filtroLideres, lideres);
								lideresNuevoOficial(request, session, usuVO, null,
										filtroLideres);
								lideresBuscar(request, session, usuVO, filtroLideres,
										codigoSectorInstitucion);
								lideresInit(request, session, usuVO, filtroLideres,
										lideres);
								buscarPadreDeFamilia(request, session, usuVO, filtroLideres, lideres);

								
								break;
							case ParamsVO.CMD_BUSCAR:
								lideresBuscar(request, session, usuVO, filtroLideres, codigoSectorInstitucion);
								lideresCancelar(request, session, usuVO);
								lideresNuevo(request, session, usuVO, null,	filtroLideres);
								request.setAttribute("nuevoRegistro", "false");
								break;
							case ParamsVO.CMD_EDITAR:
								lideres = lideresEditar(request, session, usuVO, filtroLideres);
								lideresBuscar(request, session, usuVO, filtroLideres, codigoSectorInstitucion);
								request.setAttribute("nuevoRegistro", "true");
								break;
							case ParamsVO.CMD_ELIMINAR:
								lideresEliminar(request, session, usuVO, filtroLideres);
								lideresBuscar(request, session, usuVO, filtroLideres, codigoSectorInstitucion);
								lideresNuevo(request, session, usuVO, lideres, filtroLideres);
								request.setAttribute("nuevoRegistro", "false");
								break;
							case ParamsVO.CMD_GUARDAR:
								lideresGuardar(request, session, usuVO,	lideres, codigoSectorInstitucion);
								lideresBuscar(request, session, usuVO,	filtroLideres, codigoSectorInstitucion);
								request.setAttribute("nuevoRegistro", "true");
								break;
							case ParamsVO.CMD_CANCELAR:
								lideresCancelar(request, session, usuVO);
								lideresNuevo(request, session, usuVO, null,	filtroLideres);
								lideresBuscar(request, session, usuVO, filtroLideres, codigoSectorInstitucion);
								request.setAttribute("nuevoRegistro", "true");
								break;
							case ParamsVO.CMD_NUEVO:
								lideresNuevo(request, session, usuVO, lideres, filtroLideres);
								lideresBuscar(request, session, usuVO, filtroLideres, codigoSectorInstitucion);
								request.setAttribute("nuevoRegistro", "false");
								break;
							case ParamsVO.CMD_GENERAR:
								generarReporteParticipantes(request, response, session, usuVO, filtroLideres, codigoSectorInstitucion);
								request.setAttribute("nuevoRegistro", "false");
								FICHA = null;
								break;

							}
							lideresInit(request, session, usuVO, filtroLideres, lideres);
							break;
						}
					}
					break;
				case ParamsVO.CMD_NUEVO:
					lideresNuevoOficial(request, session, usuVO, lideres, filtroLideres);
					lideresBuscar(request, session, usuVO, filtroLideres, codigoSectorInstitucion);
					lideresInit(request, session, usuVO, filtroLideres, lideres);
					request.setAttribute("nuevoRegistro", "false");
					break;

				case ParamsVO.CMD_AJAX:
					filtroLider(request, session, usuVO, lideres);
					lideresInit(request, session, usuVO, filtroLideres, lideres);
					request.setAttribute("nuevoRegistro", "true");
					break;

				case ParamsVO.CMD_GENERAR:
					generarReporteParticipantes(request, response, session,
							usuVO, filtroLideres, codigoSectorInstitucion);
					request.setAttribute("nuevoRegistro", "false");
					FICHA = null;
					break;
				case ParamsVO.CMD_BUSCAR_PADRE_FAMILIA:
					lideresNuevoOficial(request, session, usuVO, null,
							filtroLideres);
					lideresBuscar(request, session, usuVO, filtroLideres,
							codigoSectorInstitucion);
					lideresInit(request, session, usuVO, filtroLideres,
							lideres);
					buscarPadreDeFamilia(request, session, usuVO, filtroLideres, lideres);

					break;

				} 
				

				break;
			}
		}

		if (nivel == ParamParticipacion.LOGIN_NIVEL_LOCALIDAD) {
			if (tipoRol == 2) {
				FICHA_LIDERES = config.getInitParameter("FICHA_LIDERES");
			} else
				FICHA_LIDERES = config
						.getInitParameter("FICHA_LIDERES_OFICIALES_LOCALIDAD");

			switch (TIPO) {
			case ParamsVO.FICHA_LIDERES:
				FICHA = FICHA_LIDERES;
				switch (CMD) {
				case ParamsVO.CMD_BUSCAR_PADRE_FAMILIA:
					lideresNuevoOficial(request, session, usuVO, null,
							filtroLideres);
					lideresBuscar(request, session, usuVO, filtroLideres,
							codigoSectorInstitucion);
					lideresInit(request, session, usuVO, filtroLideres,
							lideres);
					buscarPadreDeFamilia(request, session, usuVO, filtroLideres, lideres);

					break;
				case ParamsVO.CMD_BUSCAR:
					lideresBuscar(request, session, usuVO, filtroLideres, codigoSectorInstitucion);
					lideresCancelar(request, session, usuVO);
					lideresNuevoOficial(request, session, usuVO, null, filtroLideres);
					lideresInit(request, session, usuVO, filtroLideres, lideres);
					request.setAttribute("nuevoRegistro", "false");
					break;
				case ParamsVO.CMD_EDITAR:
					codigoSectorInstitucion = getSectorInstitucion2(request, session,
							String.valueOf(filtroLideres.getFilColegio()));
					if (codigoSectorInstitucion == 1 || codigoSectorInstitucion == 2 || codigoSectorInstitucion == 3) {
						lideres = lideresEditarOficial(request, session, usuVO, filtroLideres);
						lideresBuscar(request, session, usuVO, filtroLideres, codigoSectorInstitucion);
						lideresInit(request, session, usuVO, filtroLideres, lideres);
						request.setAttribute("nuevoRegistro", "true");
					} else {
						FICHA_LIDERES = config.getInitParameter("FICHA_LIDERES");
						switch (TIPO) {
						case ParamsVO.FICHA_LIDERES:
							FICHA = FICHA_LIDERES;
							switch (CMD) {
							case ParamsVO.CMD_BUSCAR_PADRE_FAMILIA:
								lideresNuevoOficial(request, session, usuVO, null,
										filtroLideres);
								lideresBuscar(request, session, usuVO, filtroLideres,
										codigoSectorInstitucion);
								lideresInit(request, session, usuVO, filtroLideres,
										lideres);
								buscarPadreDeFamilia(request, session, usuVO, filtroLideres, lideres);

								
								break;
							case ParamsVO.CMD_BUSCAR:
								lideresBuscar(request, session, usuVO, filtroLideres, codigoSectorInstitucion);
								lideresCancelar(request, session, usuVO);
								lideresNuevo(request, session, usuVO, null, filtroLideres);
								request.setAttribute("nuevoRegistro", "false");
								break;
							case ParamsVO.CMD_EDITAR:
								lideres = lideresEditar(request, session, usuVO, filtroLideres);
								lideresBuscar(request, session, usuVO, filtroLideres, codigoSectorInstitucion);
								request.setAttribute("nuevoRegistro", "true");
								break;
							case ParamsVO.CMD_ELIMINAR:
								lideresEliminar(request, session, usuVO, filtroLideres);
								lideresBuscar(request, session, usuVO, filtroLideres, codigoSectorInstitucion);
								lideresNuevo(request, session, usuVO, lideres, filtroLideres);
								request.setAttribute("nuevoRegistro", "false");
								break;
							case ParamsVO.CMD_GUARDAR:
								lideresGuardar(request, session, usuVO, lideres, codigoSectorInstitucion);
								lideresBuscar(request, session, usuVO, filtroLideres, codigoSectorInstitucion);
								request.setAttribute("nuevoRegistro", "true");
								break;
							case ParamsVO.CMD_CANCELAR:
								lideresCancelar(request, session, usuVO);
								lideresNuevo(request, session, usuVO, null, filtroLideres);
								lideresBuscar(request, session, usuVO, filtroLideres, codigoSectorInstitucion);
								request.setAttribute("nuevoRegistro", "true");
								break;
							case ParamsVO.CMD_NUEVO:
								lideresNuevo(request, session, usuVO, lideres, filtroLideres);
								lideresBuscar(request, session, usuVO, filtroLideres, codigoSectorInstitucion);
								request.setAttribute("nuevoRegistro", "false");
								break;
							case ParamsVO.CMD_GENERAR:
								generarReporteParticipantes(request, response,
										session, usuVO, filtroLideres,
										codigoSectorInstitucion);
								request.setAttribute("nuevoRegistro", "false");
								FICHA = null;
								break;

							}
							lideresInit(request, session, usuVO, filtroLideres,
									lideres);
							break;
						}
					}
					break;
				case ParamsVO.CMD_ELIMINAR:
					lideresEliminar(request, session, usuVO, filtroLideres);
					lideresBuscar(request, session, usuVO, filtroLideres,
							codigoSectorInstitucion);
					lideresNuevoOficial(request, session, usuVO, lideres,
							filtroLideres);
					lideresInit(request, session, usuVO, filtroLideres, lideres);
					request.setAttribute("nuevoRegistro", "false");
					break;
				case ParamsVO.CMD_GUARDAR:
					codigoSectorInstitucion = getSectorInstitucion2(request,
							session,
							String.valueOf(filtroLideres.getFilColegio()));
					if (codigoSectorInstitucion == 1
							|| codigoSectorInstitucion == 2
							|| codigoSectorInstitucion == 3) {
						if (tipoRol == 2) {
							lideres.setLidSede(0);
							lideresGuardar(request, session, usuVO, lideres,
									codigoSectorInstitucion);
						} else
							lideresGuardarOficial(request, session, usuVO,
									lideres, codigoSectorInstitucion);
						lideresBuscar(request, session, usuVO, filtroLideres,
								codigoSectorInstitucion);
						lideresInit(request, session, usuVO, filtroLideres,
								lideres);
						request.setAttribute("nuevoRegistro", "true");
					} else {
						FICHA_LIDERES = config
								.getInitParameter("FICHA_LIDERES");
						switch (TIPO) {
						case ParamsVO.FICHA_LIDERES:
							FICHA = FICHA_LIDERES;
							switch (CMD) {
							case ParamsVO.CMD_BUSCAR_PADRE_FAMILIA:
								lideresNuevoOficial(request, session, usuVO, null,
										filtroLideres);
								lideresBuscar(request, session, usuVO, filtroLideres,
										codigoSectorInstitucion);
								lideresInit(request, session, usuVO, filtroLideres,
										lideres);
								buscarPadreDeFamilia(request, session, usuVO, filtroLideres, lideres);

								
								break;
							case ParamsVO.CMD_BUSCAR:
								lideresBuscar(request, session, usuVO,
										filtroLideres, codigoSectorInstitucion);
								lideresCancelar(request, session, usuVO);
								lideresNuevo(request, session, usuVO, null,
										filtroLideres);
								request.setAttribute("nuevoRegistro", "false");
								break;
							case ParamsVO.CMD_EDITAR:
								lideres = lideresEditar(request, session,
										usuVO, filtroLideres);
								lideresBuscar(request, session, usuVO,
										filtroLideres, codigoSectorInstitucion);
								request.setAttribute("nuevoRegistro", "true");
								break;
							case ParamsVO.CMD_ELIMINAR:
								lideresEliminar(request, session, usuVO,
										filtroLideres);
								lideresBuscar(request, session, usuVO,
										filtroLideres, codigoSectorInstitucion);
								lideresNuevo(request, session, usuVO, lideres,
										filtroLideres);
								request.setAttribute("nuevoRegistro", "false");
								break;
							case ParamsVO.CMD_GUARDAR:
								lideresGuardar(request, session, usuVO,
										lideres, codigoSectorInstitucion);
								lideresBuscar(request, session, usuVO,
										filtroLideres, codigoSectorInstitucion);
								request.setAttribute("nuevoRegistro", "true");
								break;
							case ParamsVO.CMD_CANCELAR:
								lideresCancelar(request, session, usuVO);
								lideresNuevo(request, session, usuVO, null,
										filtroLideres);
								lideresBuscar(request, session, usuVO,
										filtroLideres, codigoSectorInstitucion);
								request.setAttribute("nuevoRegistro", "true");
								break;
							case ParamsVO.CMD_NUEVO:
								lideresNuevo(request, session, usuVO, lideres,
										filtroLideres);
								lideresBuscar(request, session, usuVO,
										filtroLideres, codigoSectorInstitucion);
								request.setAttribute("nuevoRegistro", "false");
								break;
							case ParamsVO.CMD_GENERAR:
								generarReporteParticipantes(request, response,
										session, usuVO, filtroLideres,
										codigoSectorInstitucion);
								request.setAttribute("nuevoRegistro", "false");
								FICHA = null;
								break;

							}
							lideresInit(request, session, usuVO, filtroLideres,
									lideres);
							break;
						}
					}
					break;
				case ParamsVO.CMD_CANCELAR:
					codigoSectorInstitucion = getSectorInstitucion2(request,
							session,
							String.valueOf(filtroLideres.getFilColegio()));
					if (codigoSectorInstitucion == 1
							|| codigoSectorInstitucion == 2
							|| codigoSectorInstitucion == 3) {
						lideresCancelar(request, session, usuVO);
						lideresNuevoOficial(request, session, usuVO, null,
								filtroLideres);
						lideresBuscar(request, session, usuVO, filtroLideres,
								codigoSectorInstitucion);
						lideresInit(request, session, usuVO, filtroLideres,
								lideres);
						request.setAttribute("nuevoRegistro", "true");
					} else {
						// if(codigoSectorInstitucion==4){
						// System.out.println("***SECTOR PRIVADO***");
						FICHA_LIDERES = config
								.getInitParameter("FICHA_LIDERES");
						// System.out.println("***ficha: ***" + FICHA_LIDERES);
						switch (TIPO) {
						case ParamsVO.FICHA_LIDERES:
							FICHA = FICHA_LIDERES;
							switch (CMD) {
							case ParamsVO.CMD_BUSCAR_PADRE_FAMILIA:
								// System.out.println("entra OCHO");
								
								lideresNuevoOficial(request, session, usuVO, null,
										filtroLideres);
								lideresBuscar(request, session, usuVO, filtroLideres,
										codigoSectorInstitucion);
								lideresInit(request, session, usuVO, filtroLideres,
										lideres);
								buscarPadreDeFamilia(request, session, usuVO, filtroLideres, lideres);
								break;
							case ParamsVO.CMD_BUSCAR:
								lideresBuscar(request, session, usuVO,
										filtroLideres, codigoSectorInstitucion);
								lideresCancelar(request, session, usuVO);
								lideresNuevo(request, session, usuVO, null,
										filtroLideres);
								request.setAttribute("nuevoRegistro", "false");
								break;
							case ParamsVO.CMD_EDITAR:
								lideres = lideresEditar(request, session,
										usuVO, filtroLideres);
								lideresBuscar(request, session, usuVO,
										filtroLideres, codigoSectorInstitucion);
								request.setAttribute("nuevoRegistro", "true");
								break;
							case ParamsVO.CMD_ELIMINAR:
								// System.out.println("**eliminar***");
								lideresEliminar(request, session, usuVO,
										filtroLideres);
								lideresBuscar(request, session, usuVO,
										filtroLideres, codigoSectorInstitucion);
								lideresNuevo(request, session, usuVO, lideres,
										filtroLideres);
								request.setAttribute("nuevoRegistro", "false");
								break;
							case ParamsVO.CMD_GUARDAR:
								lideresGuardar(request, session, usuVO,
										lideres, codigoSectorInstitucion);
								lideresBuscar(request, session, usuVO,
										filtroLideres, codigoSectorInstitucion);
								request.setAttribute("nuevoRegistro", "true");
								break;
							case ParamsVO.CMD_CANCELAR:
								lideresCancelar(request, session, usuVO);
								lideresNuevo(request, session, usuVO, null,
										filtroLideres);
								lideresBuscar(request, session, usuVO,
										filtroLideres, codigoSectorInstitucion);
								request.setAttribute("nuevoRegistro", "true");
								break;
							case ParamsVO.CMD_NUEVO:
								lideresNuevo(request, session, usuVO, lideres,
										filtroLideres);
								lideresBuscar(request, session, usuVO,
										filtroLideres, codigoSectorInstitucion);
								request.setAttribute("nuevoRegistro", "false");
								break;
							case ParamsVO.CMD_GENERAR:
								generarReporteParticipantes(request, response,
										session, usuVO, filtroLideres,
										codigoSectorInstitucion);
								request.setAttribute("nuevoRegistro", "false");
								FICHA = null;
								break;

							}
							lideresInit(request, session, usuVO, filtroLideres,
									lideres);
							break;
						}
					}
					break;
				case ParamsVO.CMD_NUEVO:
					lideresNuevoOficial(request, session, usuVO, lideres,
							filtroLideres);
					lideresBuscar(request, session, usuVO, filtroLideres,
							codigoSectorInstitucion);
					lideresInit(request, session, usuVO, filtroLideres, lideres);
					request.setAttribute("nuevoRegistro", "false");
					break;

				case ParamsVO.CMD_AJAX:
					filtroLider(request, session, usuVO, lideres);
					lideresInit(request, session, usuVO, filtroLideres, lideres);
					request.setAttribute("nuevoRegistro", "true");
					break;

				case ParamsVO.CMD_GENERAR:
					generarReporteParticipantes(request, response, session,
							usuVO, filtroLideres, codigoSectorInstitucion);
					request.setAttribute("nuevoRegistro", "false");
					FICHA = null;
					break;
				}

				break;
			}
		}

		codigoSectorInstitucion = getSectorInstitucion(request, session, usuVO);
		if (codigoSectorInstitucion != 0) {

			if (codigoSectorInstitucion == 1 || codigoSectorInstitucion == 2
					|| codigoSectorInstitucion == 3) {
				if (tipoRol == 2) {
					FICHA_LIDERES = config.getInitParameter("FICHA_LIDERES");
				} else
					FICHA_LIDERES = config
							.getInitParameter("FICHA_LIDERES_OFICIALES");

				switch (TIPO) {
				case ParamsVO.FICHA_LIDERES:
					FICHA = FICHA_LIDERES;
					switch (CMD) {
					case ParamsVO.CMD_BUSCAR_PADRE_FAMILIA:
						lideresNuevoOficial(request, session, usuVO, null,
								filtroLideres);
						lideresBuscar(request, session, usuVO, filtroLideres,
								codigoSectorInstitucion);
						lideresInit(request, session, usuVO, filtroLideres,
								lideres);
						buscarPadreDeFamilia(request, session, usuVO, filtroLideres, lideres);
						break;
					case ParamsVO.CMD_BUSCAR:
						lideresBuscar(request, session, usuVO, filtroLideres,
								codigoSectorInstitucion);
						lideresCancelar(request, session, usuVO);
						lideresNuevoOficial(request, session, usuVO, null,
								filtroLideres);
						lideresInit(request, session, usuVO, filtroLideres,
								lideres);
						request.setAttribute("nuevoRegistro", "false");
						break;
					case ParamsVO.CMD_EDITAR:
						lideres = lideresEditarOficial(request, session, usuVO,
								filtroLideres);
						lideresBuscar(request, session, usuVO, filtroLideres,
								codigoSectorInstitucion);
						lideresInit(request, session, usuVO, filtroLideres,
								lideres);
						request.setAttribute("nuevoRegistro", "true");
						break;
					case ParamsVO.CMD_ELIMINAR:
						lideresEliminar(request, session, usuVO, filtroLideres);
						lideresBuscar(request, session, usuVO, filtroLideres,
								codigoSectorInstitucion);
						lideresNuevoOficial(request, session, usuVO, lideres,
								filtroLideres);
						lideresInit(request, session, usuVO, filtroLideres,
								lideres);
						request.setAttribute("nuevoRegistro", "false");
						break;
					case ParamsVO.CMD_GUARDAR:
						if (tipoRol == 2) {
							lideresGuardar(request, session, usuVO, lideres,
									codigoSectorInstitucion);
						} else
							lideresGuardarOficial(request, session, usuVO,
									lideres, codigoSectorInstitucion);
						lideresBuscar(request, session, usuVO, filtroLideres,
								codigoSectorInstitucion);
						lideresInit(request, session, usuVO, filtroLideres,
								lideres);
						request.setAttribute("nuevoRegistro", "true");
						break;
					case ParamsVO.CMD_CANCELAR:
						lideresCancelar(request, session, usuVO);
						lideresNuevoOficial(request, session, usuVO, null,
								filtroLideres);
						lideresBuscar(request, session, usuVO, filtroLideres,
								codigoSectorInstitucion);
						lideresInit(request, session, usuVO, filtroLideres,
								lideres);
						request.setAttribute("nuevoRegistro", "true");
						break;
					case ParamsVO.CMD_NUEVO:
						lideresNuevoOficial(request, session, usuVO, lideres,
								filtroLideres);
						lideresBuscar(request, session, usuVO, filtroLideres,
								codigoSectorInstitucion);
						lideresInit(request, session, usuVO, filtroLideres,
								lideres);
						request.setAttribute("nuevoRegistro", "false");
						break;

					case ParamsVO.CMD_AJAX:
						filtroLider(request, session, usuVO, lideres);
						lideresInit(request, session, usuVO, filtroLideres,
								lideres);
						request.setAttribute("nuevoRegistro", "true");
						break;

					case ParamsVO.CMD_GENERAR:
						generarReporteParticipantes(request, response, session,
								usuVO, filtroLideres, codigoSectorInstitucion);
						request.setAttribute("nuevoRegistro", "false");
						FICHA = null;
						break;

					}

					break;
				}

			} else {
				if (codigoSectorInstitucion == 4) {
					FICHA_LIDERES = config.getInitParameter("FICHA_LIDERES");
					switch (TIPO) {
					case ParamsVO.FICHA_LIDERES:
						FICHA = FICHA_LIDERES;
						switch (CMD) {
						case ParamsVO.CMD_BUSCAR_PADRE_FAMILIA:
							lideresNuevoOficial(request, session, usuVO, null,
									filtroLideres);
							lideresBuscar(request, session, usuVO, filtroLideres,
									codigoSectorInstitucion);
							lideresInit(request, session, usuVO, filtroLideres,
									lideres);
							buscarPadreDeFamilia(request, session, usuVO, filtroLideres, lideres);

							break;
						case ParamsVO.CMD_BUSCAR:
							lideresBuscar(request, session, usuVO,
									filtroLideres, codigoSectorInstitucion);
							lideresCancelar(request, session, usuVO);
							lideresNuevo(request, session, usuVO, null,
									filtroLideres);
							request.setAttribute("nuevoRegistro", "false");
							break;
						case ParamsVO.CMD_EDITAR:
							lideres = lideresEditar(request, session, usuVO,
									filtroLideres);
							lideresBuscar(request, session, usuVO,
									filtroLideres, codigoSectorInstitucion);
							request.setAttribute("nuevoRegistro", "true");
							break;
						case ParamsVO.CMD_ELIMINAR:
							lideresEliminar(request, session, usuVO,
									filtroLideres);
							lideresBuscar(request, session, usuVO,
									filtroLideres, codigoSectorInstitucion);
							lideresNuevo(request, session, usuVO, lideres,
									filtroLideres);
							request.setAttribute("nuevoRegistro", "false");
							break;
						case ParamsVO.CMD_GUARDAR:
							lideresGuardar(request, session, usuVO, lideres,
									codigoSectorInstitucion);
							lideresBuscar(request, session, usuVO,
									filtroLideres, codigoSectorInstitucion);
							request.setAttribute("nuevoRegistro", "true");
							break;
						case ParamsVO.CMD_CANCELAR:
							lideresCancelar(request, session, usuVO);
							lideresNuevo(request, session, usuVO, null,
									filtroLideres);
							lideresBuscar(request, session, usuVO,
									filtroLideres, codigoSectorInstitucion);
							request.setAttribute("nuevoRegistro", "true");
							break;
						case ParamsVO.CMD_NUEVO:
							lideresNuevo(request, session, usuVO, lideres,
									filtroLideres);
							lideresBuscar(request, session, usuVO,
									filtroLideres, codigoSectorInstitucion);
							request.setAttribute("nuevoRegistro", "false");
							break;
						case ParamsVO.CMD_GENERAR:
							generarReporteParticipantes(request, response,
									session, usuVO, filtroLideres,
									codigoSectorInstitucion);
							request.setAttribute("nuevoRegistro", "false");
							FICHA = null;
							break;

						}
						lideresInit(request, session, usuVO, filtroLideres,
								lideres);
						break;
					}
				}
			}
		}
		dispatcher[0] = String.valueOf(Params.INCLUDE);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	private void lideresCancelar(HttpServletRequest request,
			HttpSession session, Login usuVO) throws ServletException {
		session.removeAttribute("lideresVO");
	}

	private void lideresInit(HttpServletRequest request, HttpSession session,
			Login usuVO, FiltroLideresVO filtro, LideresVO lideres)
			throws ServletException {
		int nivel = Integer.parseInt(usuVO.getNivel());
		int tipoRol = 0;
		try {
			if (filtro == null) {
				filtro = new FiltroLideresVO();
				if (nivel == ParamParticipacion.LOGIN_NIVEL_LOCALIDAD) {
					filtro.setFilLocalidad(Integer.parseInt(usuVO.getMunId()));
					filtro.setFilTieneLocalidad(1);
				}
				if (nivel == ParamParticipacion.LOGIN_NIVEL_COLEGIO
						|| nivel == ParamParticipacion.LOGIN_NIVEL_SEDE_JORNADA) {
					filtro.setFilLocalidad(Integer.parseInt(usuVO.getMunId()));
					filtro.setFilColegio(Integer.parseInt(usuVO.getInstId()));
					filtro.setFilTieneLocalidad(1);
					filtro.setFilTieneColegio(1);
				}
				session.setAttribute("filtroLideresVO", filtro);
			}
			request.setAttribute("listaLocalidadVO",
					lideresDAO.getListaLocalidad());
			request.setAttribute("listaNivelVO",
					lideresDAO.getListaNivel(nivel));
			if (filtro != null && filtro.getFilNivel() > 0) {
				request.setAttribute("listaInstanciaVO",
						lideresDAO.getListaInstancia(filtro.getFilNivel()));
				if (filtro.getFilInstancia() > 0) {
					request.setAttribute("listaRangoVO",
							lideresDAO.getListaRango(filtro.getFilInstancia()));
					request.setAttribute("listaRolVO",
							lideresDAO.getListaRol(filtro.getFilInstancia()));
				}
			}
			if (filtro != null && filtro.getFilLocalidad() > 0) {
				request.setAttribute("listaColegioVO",
						lideresDAO.getListaColegio(filtro.getFilLocalidad()));
			}

			if (nivel == ParamParticipacion.LOGIN_NIVEL_COLEGIO
					|| nivel == ParamParticipacion.LOGIN_NIVEL_SEDE_JORNADA) {
				if (usuVO != null) {
					Collection[] c = lideresDAO.getFiltrosLider(usuVO);
					if (c != null) {
						request.setAttribute("filtroSedeF", c[0]);
						request.setAttribute("filtroJornadaF", c[1]);
						request.setAttribute("filtroMetodologiaF", c[2]);
						request.setAttribute("filtroGradoF2", c[3]);
						request.setAttribute("filtroGrupoF", c[4]);
					}
				}
			}

			if (nivel == ParamParticipacion.LOGIN_NIVEL_DEPARTAMENTO) {
				if (lideres != null) {
					if (lideres.getLidInstancia() != 0
							&& lideres.getLidInstancia() != -99
							&& lideres.getLidRol() != 0
							&& lideres.getLidRol() != -99) {
						tipoRol = lideresDAO.getTipoRol(
								lideres.getLidInstancia(), lideres.getLidRol());
						if (tipoRol == 1) {
							request.setAttribute("porEstudiante", "true");
							request.setAttribute("porPersonal", "false");
						} else {
							if (tipoRol == 0) {
								request.setAttribute("porEstudiante", "false");
								request.setAttribute("porPersonal", "true");
							}
						}
					}
				}
			}

			if (nivel == ParamParticipacion.LOGIN_NIVEL_LOCALIDAD) {
				if (lideres != null) {
					if (lideres.getLidInstancia() != 0
							&& lideres.getLidInstancia() != -99
							&& lideres.getLidRol() != 0
							&& lideres.getLidRol() != -99) {
						tipoRol = lideresDAO.getTipoRol(
								lideres.getLidInstancia(), lideres.getLidRol());
						if (tipoRol == 1) {
							request.setAttribute("porEstudiante", "true");
							request.setAttribute("porPersonal", "false");
						} else {
							if (tipoRol == 0) {
								request.setAttribute("porEstudiante", "false");
								request.setAttribute("porPersonal", "true");
							}
						}
					}
				}
			}

			if (nivel == ParamParticipacion.LOGIN_NIVEL_COLEGIO
					|| nivel == ParamParticipacion.LOGIN_NIVEL_SEDE_JORNADA) {
				if (lideres != null) {
					if (lideres.getLidInstancia() != 0
							&& lideres.getLidInstancia() != -99
							&& lideres.getLidRol() != 0
							&& lideres.getLidRol() != -99) {
						tipoRol = lideresDAO.getTipoRol(
								lideres.getLidInstancia(), lideres.getLidRol());
						if (tipoRol == 1) {
							request.setAttribute("porEstudiante", "true");
							request.setAttribute("porPersonal", "false");
						} else {
							if (tipoRol == 0) {
								request.setAttribute("porEstudiante", "false");
								request.setAttribute("porPersonal", "true");
							}
						}
					}
				}
			}

		} catch (ParticipacionException e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	private void buscarPadreDeFamilia(HttpServletRequest request, HttpSession session, Login usuVO, FiltroLideresVO filtro, LideresVO lideres)
			throws ServletException {
		try {
			if (filtro != null) {
				LideresVO lider = lideresDAO.getPadreDeFamilia(filtro, lideres);
				if(lider != null){
					lider.setLidTipoDocumento(lideres.getLidTipoDocumento());
					lider.setLidNumeroDocumento(lideres.getLidNumeroDocumento());
					request.getSession().setAttribute("lideresVO", lider);
					request.setAttribute("padreEncontrado", ""+true);
				}else{
					request.getSession().removeAttribute("lideresVO");
					request.setAttribute("padreEncontrado", ""+false);
				}
				request.setAttribute("nuevoRegistro", "true");
			}
		} catch (ParticipacionException e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	private void lideresBuscar(HttpServletRequest request, HttpSession session,
			Login usuVO, FiltroLideresVO filtro, int codigoSector)
			throws ServletException {
		try {
			if (filtro != null) {
				request.setAttribute("listaLideresVO", lideresDAO.getListaLideres(filtro, codigoSector));
				Logger.print(usuVO.getUsuarioId(), "Busqueda de Participantes. ",
						7, 1, this.toString());
			}
			else {
				Logger.print(usuVO.getUsuarioId(), "Consulta de PARCTICIPACION,Módulo participantes. ",
						7, 1, this.toString());
			}
		} catch (ParticipacionException e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	private int getSectorInstitucion(HttpServletRequest request,
			HttpSession session, Login usuVO) throws ServletException {
		int codigoSector = 0;
		try {
			if (usuVO != null) {
				if (usuVO.getInstId() != null)
					codigoSector = lideresDAO.getSectorInstitucion(usuVO
							.getInstId());
			}
		} catch (ParticipacionException e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
		return codigoSector;
	}

	private int getSectorInstitucion2(HttpServletRequest request,
			HttpSession session, String institucion) throws ServletException {
		int codigoSector = 0;
		try {
			if (institucion != null)
				codigoSector = lideresDAO.getSectorInstitucion(institucion);

		} catch (ParticipacionException e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
		return codigoSector;
	}

	private LideresVO lideresEditar(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroLideresVO filtro)
			throws ServletException {
		LideresVO i = null;
		try {
			if (filtro != null && filtro.getFilParticipante() > 0) {
				i = lideresDAO.getLideres(filtro);

				if (i != null && i.getLidLocalidad() > 0) {
					request.setAttribute("listaColegioVO",
							lideresDAO.getListaColegio(i.getLidLocalidad()));
					request.setAttribute("listaSedeVO",
							lideresDAO.getListaSede(i.getLidColegio()));
					request.setAttribute("listaJornadaVO",
							lideresDAO.getListaJornada());
					request.setAttribute("listaGradoVO",
							lideresDAO.getListaGrado());
					request.setAttribute("listaGrupoVO",
							lideresDAO.getListaGrupo());
				}
				request.setAttribute("listaSedeVO",
						lideresDAO.getListaSede(i.getLidColegio()));

				request.setAttribute("listaColegioVO",
						lideresDAO.getListaColegio(i.getLidLocalidad()));
				// request.setAttribute("listaRolVO",lideresDAO.getListaRol());
				request.setAttribute("listaTipoDocumentoVO",
						lideresDAO.getListaTipoDocumento());
				request.setAttribute("listaEtniaVO", lideresDAO.getListaEtnia());

				request.setAttribute("listaLocalidadResidenciaVO",
						lideresDAO.getListaLocalidad());
				request.setAttribute("listaSectorEconomicoVO",
						lideresDAO.getListaSectorEconomico());
				request.setAttribute("listaDiscapacidadVO",
						lideresDAO.getListaDiscapacidad());
				request.setAttribute("listaLgtbVO", lideresDAO.getListaLgtb());
				request.setAttribute("listaOcupacionVO",
						lideresDAO.getListaOcupacion());

				session.setAttribute("lideresVO", i);
			}
		} catch (ParticipacionException e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
		return i;
	}

	private LideresVO lideresEditarOficial(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroLideresVO filtro)
			throws ServletException {
		LideresVO i = null;
		int nivel = Integer.parseInt(usuVO.getNivel());
		int tipoRol = 0;
		try {
			if (filtro != null && filtro.getFilParticipante() > 0) {
				i = lideresDAO.getLideres(filtro);

				if (nivel == ParamParticipacion.LOGIN_NIVEL_DEPARTAMENTO
						|| nivel == ParamParticipacion.LOGIN_NIVEL_LOCALIDAD) {
					request.setAttribute("listaSedeVO",
							lideresDAO.getListaSede(i.getLidColegio()));
					request.setAttribute("listaMetodologiaVO",
							lideresDAO.getListaMetodologia(i.getLidColegio()));
					request.setAttribute(
							"listaJornadaVO",
							lideresDAO.getListaJornada(i.getLidColegio(),
									i.getLidSede()));
					request.setAttribute(
							"listaGradoVO",
							lideresDAO.getListaGrado(i.getLidColegio(),
									i.getLidSede(), i.getLidJornada(),
									i.getLidMet()));
					request.setAttribute(
							"listaGrupoVO",
							lideresDAO.getListaGrupo(i.getLidColegio(),
									i.getLidSede(), i.getLidJornada(),
									i.getLidMet(), i.getLidGrado()));

					tipoRol = lideresDAO.getTipoRol(i.getLidInstancia(),
							i.getLidRol());
					if (tipoRol == 1) {
						request.setAttribute("listaEstudianteVO", lideresDAO
								.getListaEstudiante(i.getLidColegio(),
										i.getLidSede(), i.getLidJornada(),
										i.getLidMet(), i.getLidGrado(),
										i.getLidGrupo(), i.getLidRol(),
										i.getLidInstancia()));
					} else {
						if (tipoRol == 0) {
							request.setAttribute("listaPersonalVO", lideresDAO
									.getListaPersonal(i.getLidColegio(),
											i.getLidSede(), i.getLidJornada(),
											i.getLidRol(), i.getLidInstancia()));
						}
					}
				}

				if (nivel == ParamParticipacion.LOGIN_NIVEL_COLEGIO
						|| nivel == ParamParticipacion.LOGIN_NIVEL_SEDE_JORNADA) {
					Collection[] c = lideresDAO.getFiltrosLider(usuVO);
					tipoRol = lideresDAO.getTipoRol(i.getLidInstancia(),
							i.getLidRol());
					if (tipoRol == 1) {
						if (c != null) {
							System.out.println("***c!=null en filtroLider***");
							request.setAttribute("filtroSedeF", c[0]);
							request.setAttribute("filtroJornadaF", c[1]);
							request.setAttribute("filtroMetodologiaF", c[2]);
							request.setAttribute("filtroGradoF2", c[3]);
							request.setAttribute("filtroGrupoF", c[4]);
						}

						String[] params = { String.valueOf(i.getLidSede()),
								String.valueOf(i.getLidJornada()),
								String.valueOf(usuVO.getMetodologiaId()),
								String.valueOf(i.getLidGrado()),
								String.valueOf(i.getLidGrupo()),
								String.valueOf(i.getLidNumeroDocumento()) };
						request.setAttribute("listaEstudiantes", lideresDAO
								.getEstudiantesGrupoEstudiante(usuVO, params));
					} else {
						if (tipoRol == 0) {
							if (c != null) {
								request.setAttribute("filtroSedeF", c[0]);
								request.setAttribute("filtroJornadaF", c[1]);
							}

							String[] params = { String.valueOf(i.getLidSede()),
									String.valueOf(i.getLidJornada()),
									String.valueOf(i.getLidNumeroDocumento()) };
							request.setAttribute("listaPersonal", lideresDAO
									.getPersonalPersona(usuVO, params));
						}
					}
				}

				request.setAttribute("listaTipoDocumentoVO",
						lideresDAO.getListaTipoDocumento());
				request.setAttribute("listaEtniaVO", lideresDAO.getListaEtnia());

				request.setAttribute("listaLocalidadResidenciaVO",
						lideresDAO.getListaLocalidad());
				request.setAttribute("listaSectorEconomicoVO",
						lideresDAO.getListaSectorEconomico());
				request.setAttribute("listaDiscapacidadVO",
						lideresDAO.getListaDiscapacidad());
				request.setAttribute("listaLgtbVO", lideresDAO.getListaLgtb());
				request.setAttribute("listaOcupacionVO",
						lideresDAO.getListaOcupacion());

				i.setFormaEstado("1");
				session.setAttribute("lideresVO", i);
			}
		} catch (ParticipacionException e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
		return i;
	}

	private void lideresEliminar(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroLideresVO filtro)
			throws ServletException {
		try {
			if (filtro != null && filtro.getFilParticipante() > 0) {
				lideresDAO.eliminarLideres(filtro);
				request.setAttribute(ParamsVO.SMS,
						"El registro fue eliminado satisfactoriamente");
				session.removeAttribute("lideresVO");
			}
		} catch (IntegrityException e) {
			request.setAttribute(ParamsVO.SMS, "El registro fue no eliminada:"
					+ e.getMessage());
		} catch (ParticipacionException e) {
			request.setAttribute(ParamsVO.SMS,
					"El registro fue no eliminada: Error interno");
		}
	}

	private void lideresGuardar(HttpServletRequest request,
			HttpSession session, Login usuVO, LideresVO lideres,
			int codigoSector) throws ServletException {
		try {
			if (lideres != null) {
				lideres.setLidTipoColegio(codigoSector);
				if (lideres.getFormaEstado().equals("1")) {
					lideresDAO.actualizarLideres(lideres);
					request.setAttribute(ParamsVO.SMS,
							"El registro fue actualizado satisfactoriamente");
					session.removeAttribute("lideresVO");
				} else {
					System.out.println("**insertar lideres - participante--- ");
					lideresDAO.ingresarLideres(lideres);
					request.setAttribute(ParamsVO.SMS,
							"El registro fue ingresado satisfactoriamente");
					session.removeAttribute("lideresVO");
				}
			}
			lideresNuevo(request, session, usuVO, null, null);
		} catch (IntegrityException e) {
			request.setAttribute(
					ParamsVO.SMS,
					"El registro fue no ingresado/actualizado:"
							+ e.getMessage());
		} catch (ParticipacionException e) {
			request.setAttribute(ParamsVO.SMS,
					"El registro fue no ingresado/actualizado: Error interno");
		}
	}

	private void lideresGuardarOficial(HttpServletRequest request,
			HttpSession session, Login usuVO, LideresVO lideres,
			int codigoSector) throws ServletException {
		int nivel = Integer.parseInt(usuVO.getNivel());
		int tipoRol = 0;
		try {
			if (lideres != null) {
				lideres.setLidTipoColegio(codigoSector);

				if (nivel == ParamParticipacion.LOGIN_NIVEL_DEPARTAMENTO) {
					if (lideres != null) {
						if (lideres.getLidInstancia() != 0
								&& lideres.getLidInstancia() != -99
								&& lideres.getLidRol() != 0
								&& lideres.getLidRol() != -99) {
							tipoRol = lideresDAO.getTipoRol(
									lideres.getLidInstancia(),
									lideres.getLidRol());
							if (tipoRol == 1) {
								if (lideres.getFormaEstado().equals("1")) {
									lideresDAO
											.actualizarLideresOficial(lideres);
									request.setAttribute(ParamsVO.SMS,
											"El registro fue actualizado satisfactoriamente");
									session.removeAttribute("lideresVO");
								} else {
									lideresDAO.ingresarLideresOficial(lideres);
									request.setAttribute(ParamsVO.SMS,
											"El registro fue ingresado satisfactoriamente");
									session.removeAttribute("lideresVO");
								}
							} else {
								if (tipoRol == 0) {
									if (lideres.getFormaEstado().equals("1")) {
										lideresDAO
												.actualizarLideresOficialPersonal(lideres);
										request.setAttribute(ParamsVO.SMS,
												"El registro fue actualizado satisfactoriamente");
										session.removeAttribute("lideresVO");
									} else {
										lideresDAO
												.ingresarLideresOficialPersonal(lideres);
										request.setAttribute(ParamsVO.SMS,
												"El registro fue ingresado satisfactoriamente");
										session.removeAttribute("lideresVO");
									}
								}
							}
						}
					}
				} else {
					if (nivel == ParamParticipacion.LOGIN_NIVEL_LOCALIDAD) {
						if (lideres != null) {
							if (lideres.getLidInstancia() != 0
									&& lideres.getLidInstancia() != -99
									&& lideres.getLidRol() != 0
									&& lideres.getLidRol() != -99) {
								tipoRol = lideresDAO.getTipoRol(
										lideres.getLidInstancia(),
										lideres.getLidRol());
								if (tipoRol == 1) {
									if (lideres.getFormaEstado().equals("1")) {
										lideresDAO
												.actualizarLideresOficial(lideres);
										request.setAttribute(ParamsVO.SMS,
												"El registro fue actualizado satisfactoriamente");
										session.removeAttribute("lideresVO");
									} else {
										lideresDAO
												.ingresarLideresOficial(lideres);
										request.setAttribute(ParamsVO.SMS,
												"El registro fue ingresado satisfactoriamente");
										session.removeAttribute("lideresVO");
									}
								} else {
									if (tipoRol == 0) {
										if (lideres.getFormaEstado()
												.equals("1")) {
											lideresDAO
													.actualizarLideresOficialPersonal(lideres);
											request.setAttribute(ParamsVO.SMS,
													"El registro fue actualizado satisfactoriamente");
											session.removeAttribute("lideresVO");
										} else {
											lideresDAO
													.ingresarLideresOficialPersonal(lideres);
											request.setAttribute(ParamsVO.SMS,
													"El registro fue ingresado satisfactoriamente");
											session.removeAttribute("lideresVO");
										}
									}
								}
							}
						}
					} else {
						if (nivel == ParamParticipacion.LOGIN_NIVEL_COLEGIO
								|| nivel == ParamParticipacion.LOGIN_NIVEL_SEDE_JORNADA) {
							if (lideres != null) {
								if (lideres.getLidInstancia() != 0
										&& lideres.getLidInstancia() != -99
										&& lideres.getLidRol() != 0
										&& lideres.getLidRol() != -99) {
									tipoRol = lideresDAO.getTipoRol(
											lideres.getLidInstancia(),
											lideres.getLidRol());
									if (tipoRol == 1) {
										if (lideres.getFormaEstado()
												.equals("1")) {
											lideresDAO
													.actualizarLideresOficial(lideres);
											request.setAttribute(ParamsVO.SMS,
													"El registro fue actualizado satisfactoriamente");
											session.removeAttribute("lideresVO");
										} else {
											lideresDAO
													.ingresarLideresOficial(lideres);
											request.setAttribute(ParamsVO.SMS,
													"El registro fue ingresado satisfactoriamente");
											session.removeAttribute("lideresVO");
										}
									} else {
										if (tipoRol == 0) {
											if (lideres.getFormaEstado()
													.equals("1")) {
												lideresDAO
														.actualizarLideresOficialPersonal(lideres);
												request.setAttribute(
														ParamsVO.SMS,
														"El registro fue actualizado satisfactoriamente");
												session.removeAttribute("lideresVO");
											} else {
												lideresDAO
														.ingresarLideresOficialPersonal(lideres);
												request.setAttribute(
														ParamsVO.SMS,
														"El registro fue ingresado satisfactoriamente");
												session.removeAttribute("lideresVO");
											}
										}
									}
								}
							}
						}
					}
				}
			}
			lideresNuevo(request, session, usuVO, null, null);
		} catch (IntegrityException e) {
			request.setAttribute(
					ParamsVO.SMS,
					"El registro fue no ingresado/actualizado:"
							+ e.getMessage());
		} catch (ParticipacionException e) {
			request.setAttribute(ParamsVO.SMS,
					"El registro fue no ingresado/actualizado: Error interno");
		}
	}

	private void lideresNuevo(HttpServletRequest request, HttpSession session,
			Login usuVO, LideresVO lideres, FiltroLideresVO filtroLideres)
			throws ServletException {
		// System.out.println("***ENTRO A LIDERES NUEVO***");
		int nivel = Integer.parseInt(usuVO.getNivel());
		try {
			if (lideres == null) {
				lideres = new LideresVO();

				if (nivel == ParamParticipacion.LOGIN_NIVEL_LOCALIDAD) {
					lideres.setLidLocalidad(Integer.parseInt(usuVO.getMunId()));
					lideres.setLidTieneLocalidad(1);
				}
				if (nivel == ParamParticipacion.LOGIN_NIVEL_COLEGIO
						|| nivel == ParamParticipacion.LOGIN_NIVEL_SEDE_JORNADA) {
					lideres.setLidLocalidad(Integer.parseInt(usuVO.getMunId()));
					lideres.setLidColegio(Long.parseLong(usuVO.getInstId()));
					lideres.setLidTieneLocalidad(1);
					lideres.setLidTieneColegio(1);

				}
				if (nivel == ParamParticipacion.LOGIN_NIVEL_SEDE_JORNADA)
					lideres.setLidSede(Integer.parseInt(usuVO.getSedeId()));

				lideres.setFormaEstado("0");
				session.setAttribute("lideresVO", lideres);
			}
			if (lideres != null && lideres.getLidLocalidad() > 0) {
				request.setAttribute("listaColegioVO",
						lideresDAO.getListaColegio(lideres.getLidLocalidad()));
				request.setAttribute("listaSedeVO",
						lideresDAO.getListaSede(lideres.getLidColegio()));
				request.setAttribute("listaJornadaVO",
						lideresDAO.getListaJornada());
				request.setAttribute("listaGradoVO", lideresDAO.getListaGrado());
				request.setAttribute("listaGrupoVO", lideresDAO.getListaGrupo());
			} else {
				request.setAttribute("listaSedeVO",
						lideresDAO.getListaSede(lideres.getLidColegio()));
				request.setAttribute("listaJornadaVO",
						lideresDAO.getListaJornada());
				request.setAttribute("listaGradoVO", lideresDAO.getListaGrado());
				request.setAttribute("listaGrupoVO", lideresDAO.getListaGrupo());
			}
			if (lideres.getLidColegio() > 0)
				request.setAttribute("listaSedeVO",
						lideresDAO.getListaSede(lideres.getLidColegio()));

			if (filtroLideres != null) {
				request.setAttribute("listaSedeVO",
						lideresDAO.getListaSede(filtroLideres.getFilColegio()));
			}
			request.setAttribute("listaColegioVO",
					lideresDAO.getListaColegio(lideres.getLidLocalidad()));
			request.setAttribute("listaTipoDocumentoVO",
					lideresDAO.getListaTipoDocumento());
			request.setAttribute("listaEtniaVO", lideresDAO.getListaEtnia());

			request.setAttribute("listaLocalidadResidenciaVO",
					lideresDAO.getListaLocalidad());
			request.setAttribute("listaSectorEconomicoVO",
					lideresDAO.getListaSectorEconomico());
			request.setAttribute("listaDiscapacidadVO",
					lideresDAO.getListaDiscapacidad());
			request.setAttribute("listaLgtbVO", lideresDAO.getListaLgtb());
			request.setAttribute("listaOcupacionVO",
					lideresDAO.getListaOcupacion());

		} catch (ParticipacionException e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	private void lideresNuevoOficial(HttpServletRequest request,
			HttpSession session, Login usuVO, LideresVO lideres,
			FiltroLideresVO filtroLideres) throws ServletException {
		int nivel = Integer.parseInt(usuVO.getNivel());
		try {
			if (lideres == null) {
				lideres = new LideresVO();
				if (filtroLideres != null) {
					request.setAttribute("listaSedeVO", lideresDAO
							.getListaSede(filtroLideres.getFilColegio()));
					request.setAttribute("listaMetodologiaVO", lideresDAO
							.getListaMetodologia(filtroLideres.getFilColegio()));

				}
				if (nivel == ParamParticipacion.LOGIN_NIVEL_LOCALIDAD) {
					if (filtroLideres != null) {
						request.setAttribute("listaSedeVO", lideresDAO
								.getListaSede(filtroLideres.getFilColegio()));
					}

					lideres.setLidLocalidad(Integer.parseInt(usuVO.getMunId()));
					lideres.setLidTieneLocalidad(1);

				}
				if (nivel == ParamParticipacion.LOGIN_NIVEL_COLEGIO
						|| nivel == ParamParticipacion.LOGIN_NIVEL_SEDE_JORNADA) {
					lideres.setLidLocalidad(Integer.parseInt(usuVO.getMunId()));
					lideres.setLidColegio(Long.parseLong(usuVO.getInstId()));
					lideres.setLidTieneLocalidad(1);
					lideres.setLidTieneColegio(1);
				}
				if (nivel == ParamParticipacion.LOGIN_NIVEL_SEDE_JORNADA)
					lideres.setLidSede(Integer.parseInt(usuVO.getSedeId()));

				lideres.setFormaEstado("0");
				session.setAttribute("lideresVO", lideres);
			}

			if (nivel == ParamParticipacion.LOGIN_NIVEL_COLEGIO
					|| nivel == ParamParticipacion.LOGIN_NIVEL_SEDE_JORNADA) {
				if (usuVO != null) {
					Collection[] c = lideresDAO.getFiltrosLider(usuVO);
					if (c != null) {
						request.setAttribute("filtroSedeF", c[0]);
						request.setAttribute("filtroJornadaF", c[1]);
						request.setAttribute("filtroMetodologiaF", c[2]);
						request.setAttribute("filtroGradoF2", c[3]);
						request.setAttribute("filtroGrupoF", c[4]);
					}
				}
			}

			request.setAttribute("listaTipoDocumentoVO",
					lideresDAO.getListaTipoDocumento());
			request.setAttribute("listaEtniaVO", lideresDAO.getListaEtnia());
			request.setAttribute("listaLocalidadResidenciaVO",
					lideresDAO.getListaLocalidad());
			request.setAttribute("listaSectorEconomicoVO",
					lideresDAO.getListaSectorEconomico());
			request.setAttribute("listaDiscapacidadVO",
					lideresDAO.getListaDiscapacidad());
			request.setAttribute("listaLgtbVO", lideresDAO.getListaLgtb());
			request.setAttribute("listaOcupacionVO",
					lideresDAO.getListaOcupacion());

		} catch (ParticipacionException e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void filtroLider(HttpServletRequest request, HttpSession session,
			Login login, LideresVO lideres) throws ServletException,
			IOException {
		String accion = (request.getParameter("accion") != null) ? request
				.getParameter("accion") : new String("");
		int tipoRol = 0;
		try {
			if (lideres != null) {
				if (accion != null || !accion.equals("")) {

					if (lideres.getLidInstancia() != 0
							&& lideres.getLidInstancia() != -99
							&& lideres.getLidRol() != 0
							&& lideres.getLidRol() != -99) {
						tipoRol = lideresDAO.getTipoRol(
								lideres.getLidInstancia(), lideres.getLidRol());
						if (tipoRol == 1) {
							String[] params = { request.getParameter("sede"),
									request.getParameter("jornada"),
									request.getParameter("metod"),
									request.getParameter("grado"),
									request.getParameter("grupo") };

							request.setAttribute("listaEstudiantes", lideresDAO
									.getEstudiantesGrupo(login, params));

							lideres.setLidLocalidad(Integer.parseInt(login
									.getMunId()));
							lideres.setLidColegio(Long.parseLong(login
									.getInstId()));
							lideres.setLidSede(Integer.parseInt(params[0]));
							lideres.setLidJornada(Integer.parseInt(params[1]));
							lideres.setLidMet(Integer.parseInt(params[2]));
							lideres.setLidGrado(Integer.parseInt(params[3]));
							lideres.setLidGrupo(Integer.parseInt(params[4]));
						} else {
							if (tipoRol == 0) {
								String[] params = {
										request.getParameter("sede"),
										request.getParameter("jornada"), };

								request.setAttribute("listaPersonal",
										lideresDAO.getPersonal(login, params));

								lideres.setLidLocalidad(Integer.parseInt(login
										.getMunId()));
								lideres.setLidColegio(Long.parseLong(login
										.getInstId()));
								lideres.setLidSede(Integer.parseInt(params[0]));
								lideres.setLidJornada(Integer
										.parseInt(params[1]));
							}
						}
					}

					session.setAttribute("lideresVO", lideres);

					Collection[] c = lideresDAO.getFiltrosLider(login);
					if (c != null) {
						request.setAttribute("filtroSedeF", c[0]);
						request.setAttribute("filtroJornadaF", c[1]);
						request.setAttribute("filtroMetodologiaF", c[2]);
						request.setAttribute("filtroGradoF2", c[3]);
						request.setAttribute("filtroGrupoF", c[4]);
					}
				}

				request.setAttribute("listaTipoDocumentoVO",
						lideresDAO.getListaTipoDocumento());
				request.setAttribute("listaEtniaVO", lideresDAO.getListaEtnia());

				request.setAttribute("listaLocalidadResidenciaVO",
						lideresDAO.getListaLocalidad());
				request.setAttribute("listaSectorEconomicoVO",
						lideresDAO.getListaSectorEconomico());
				request.setAttribute("listaDiscapacidadVO",
						lideresDAO.getListaDiscapacidad());
				request.setAttribute("listaLgtbVO", lideresDAO.getListaLgtb());
				request.setAttribute("listaOcupacionVO",
						lideresDAO.getListaOcupacion());
			}
		} catch (ParticipacionException e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void generarReporteParticipantes(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Login usuVO,
			FiltroLideresVO filtro, int codigoSector) throws ServletException {
		try {
			if (filtro != null) {
				response.setContentType("application/zip");
				BufferedInputStream origin = null;
				ServletOutputStream ouputStream = null;
				FileInputStream fi = null;
				int BUFFER_SIZE = 100000;
				int count;
				try {
					filtro.setFilNombreNivel(request
							.getParameter("NOMBRE_NIVEL"));
					filtro.setFilNombreInstancia(request
							.getParameter("NOMBRE_INSTANCIA"));
					filtro.setFilNombreRango(request
							.getParameter("NOMBRE_RANGO"));
					filtro.setFilNombreLocalidad(request
							.getParameter("NOMBRE_LOCALIDAD"));
					filtro.setFilNombreColegio(request
							.getParameter("NOMBRE_COLEGIO"));

					if (request.getParameter("NOMBRE_ROL").equals(
							"-Seleccione uno-"))
						filtro.setFilNombreRol("TODOS");
					else
						filtro.setFilNombreRol(request
								.getParameter("NOMBRE_ROL"));

					ResultadoReportesVO resultado = null;
					resultado = buscarReporte(usuVO, filtro, codigoSector);

					if (resultado.isGenerado()) {
						StringBuffer contentDisposition = new StringBuffer();
						contentDisposition.append("attachment;");
						contentDisposition.append("filename=\"");
						contentDisposition.append(resultado.getArchivo());
						contentDisposition.append("\"");
						response.setHeader("Content-Disposition",
								contentDisposition.toString());
						File f = new File(resultado.getRutaDisco());
						if (!f.exists()) {
							return;
						}
						byte[] data = new byte[BUFFER_SIZE];
						fi = new FileInputStream(f);
						origin = new BufferedInputStream(fi, BUFFER_SIZE);
						ouputStream = response.getOutputStream();
						while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
							ouputStream.write(data, 0, count);
						}
						ouputStream.flush();
						ouputStream.close();
						origin.close();
						fi.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServletException(e.getMessage());
				} finally {
					try {
						if (ouputStream != null)
							ouputStream.close();
						if (origin != null)
							origin.close();
						if (fi != null)
							fi.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public ResultadoReportesVO buscarReporte(Login usuVO,
			FiltroLideresVO filtro, int codigo) throws Exception {
		ResultadoReportesVO resultado = new ResultadoReportesVO();
		try {
			Calendar c = Calendar.getInstance();
			String fecha = c.get(Calendar.DAY_OF_MONTH) + "_"+ (c.get(Calendar.MONTH) + 1) + "_" + c.get(Calendar.YEAR)+ "_" + c.get(Calendar.HOUR_OF_DAY) + "_"+ c.get(Calendar.MINUTE);
			String rutaJasper = Ruta2.get(contextoTotal,rb.getString("rutaJasper"));
			String archivoJasper = rb.getString("jasperParticipantes");
			String rutaExcel = Ruta.get(contextoTotal, rb.getString("path")+ usuVO.getUsuarioId());
			String rutaExcelRelativo = Ruta.getRelativo(contextoTotal,rb.getString("path") + usuVO.getUsuarioId());
			archivoJasper = rb.getString("jasperParticipantes");
			String archivoXLS = "Reporte_Listado_Participantes_Colegio_"+ usuVO.getInstId() + "_FECHA_" + fecha + ".xls";
			String archivoZIP = "Reporte_Listado_Participantes_Colegio_"+ usuVO.getInstId() + "_FECHA_" + fecha + ".zip";			
			String rutaImagen = Ruta.get(contextoTotal,rb.getString("rutaImagen"));
			String rutaLogos = Ruta.get(contextoTotal,rb.getString("rutaLogos"));
			String archivoImagen = rb.getString("imagen");
			String archivoImagen2 = rb.getString("imagen2");
			File reportFile = new File(rutaJasper + archivoJasper);

			// Parametros del Reporte
			Map map = new HashMap();
			map.put("SUBREPORT_DIR", rutaJasper);
			map.put("PATH_ICONO_SECRETARIA", rutaImagen + archivoImagen);
			map.put("PATH_ICONO_BOGOTA", rutaLogos + archivoImagen2);
			map.put("USUARIO", String.valueOf(usuVO.getUsuarioId()));
			map.put("LOCALIDAD", String.valueOf(filtro.getFilNombreLocalidad()));
			map.put("COLEGIO", String.valueOf(filtro.getFilNombreColegio()));
			map.put("NOMBRE_NIVEL", String.valueOf(filtro.getFilNombreNivel()));
			map.put("NOMBRE_INSTANCIA",	String.valueOf(filtro.getFilNombreInstancia()));
			map.put("NOMBRE_RANGO", String.valueOf(filtro.getFilNombreRango()));
			map.put("NOMBRE_ROL", String.valueOf(filtro.getFilNombreRol()));
			map.put("CODIGO_INSTANCIA", new Integer(filtro.getFilInstancia()));
			map.put("CODIGO_RANGO", new Integer(filtro.getFilRango()));
			map.put("CODIGO_ROL", new Integer(filtro.getFilRol()));
			map.put("CODIGO_COLEGIO", new Long(filtro.getFilColegio()));
			map.put("CODIGO_SECTOR_INSTITUCION", new Integer(codigo));

			String rutaXLS = getArchivoXLS(reportFile, map, rutaExcel,
					archivoXLS);
			List l = new ArrayList();
			l.add(rutaXLS);
			Zip zip = new Zip();
			if (zip.ponerZip(rutaExcel, archivoZIP, 100000, l)) {
				resultado.setGenerado(true);
				resultado.setRuta(rutaExcelRelativo + archivoZIP);
				resultado.setRutaDisco(rutaExcel + archivoZIP);
				resultado.setArchivo(archivoZIP);
				resultado.setTipo("zip");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error generando reporte " + e);
		}
		return resultado;
	}

	/**
	 * Construye el archivo y lo almacena en el servidor
	 * 
	 * @param reportFile
	 *            Archivo del reporte
	 * @param parameterscopy
	 *            Parametros del reporte
	 * @param rutaExcel
	 *            Ruta de almacenamiento del reporte
	 * @param archivo
	 *            Nombre del reporte
	 * @return Ruta de disco del reporte
	 * @throws Exception
	 */

	public String getArchivoXLS(File reportFile, Map parameterscopy,
			String rutaExcel, String archivo) throws Exception {
		Connection con = null;
		String archivoCompleto = null;
		try {
			con = lideresDAO.getConnection();
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					reportFile.getPath(), parameterscopy, con);
			String xlsFileName = archivo;
			String xlsFilesSource = rutaExcel;
			archivoCompleto = xlsFilesSource + xlsFileName;
			File f = new File(xlsFilesSource);
			if (!f.exists())
				FileUtils.forceMkdir(f);
			// USANDO API EXCEL
			JExcelApiExporter xslExporter = new JExcelApiExporter();
			xslExporter.setParameter(JRExporterParameter.JASPER_PRINT,
					jasperPrint);
			xslExporter.setParameter(
					JExcelApiExporterParameter.IS_ONE_PAGE_PER_SHEET,
					Boolean.FALSE);
			xslExporter.setParameter(
					JExcelApiExporterParameter.OUTPUT_FILE_NAME, xlsFilesSource
							+ xlsFileName);
			xslExporter.exportReport();
		} catch (JRException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (java.lang.OutOfMemoryError e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return archivoCompleto;
	}

	/**
	 * Construye el archivo y lo almacena en el servidor
	 * 
	 * @param reportFile
	 *            Archivo del reporte
	 * @param parameterscopy
	 *            Parametros del reporte
	 * @param rutaExcel
	 *            Ruta de almacenamiento del reporte
	 * @param archivo
	 *            Nombre del reporte
	 * @return Ruta de disco del reporte
	 * @throws Exception
	 */
	public String getArchivoPDF(File reportFile, Map parameterscopy,
			String rutaExcel, String archivo) throws Exception {
		Connection con = null;
		String archivoCompleto = null;
		try {
			con = lideresDAO.getConnection();
			byte[] bytes = JasperRunManager.runReportToPdf(
					reportFile.getPath(), parameterscopy, con);
			String xlsFileName = archivo;
			String xlsFilesSource = rutaExcel;
			archivoCompleto = xlsFilesSource + xlsFileName;
			File f = new File(xlsFilesSource);
			if (!f.exists())
				FileUtils.forceMkdir(f);
			FileOutputStream fileOut = new FileOutputStream(archivoCompleto);
			IOUtils.write(bytes, fileOut);
		} catch (JRException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (java.lang.OutOfMemoryError e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return archivoCompleto;
	}

}
