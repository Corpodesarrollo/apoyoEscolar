package pei.registro.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import pei.registro.dao.RegistroDAO;
import pei.registro.io.RegistroIO;
import pei.registro.vo.AjusteVO;
import pei.registro.vo.CapacitacionVO;
import pei.registro.vo.CiudadaniaVO;
import pei.registro.vo.PoblacionesVO;
import pei.registro.vo.PrimeraInfanciaVO;
import pei.registro.vo.Proyecto40HorasVO;
import pei.registro.vo.CriterioEvaluacionVO;
import pei.registro.vo.CurriculoVO;
import pei.registro.vo.DesarrolloAdministrativoVO;
import pei.registro.vo.DesarrolloCurricularVO;
import pei.registro.vo.DocumentoVO;
import pei.registro.vo.FiltroRegistroVO;
import pei.registro.vo.HorizonteVO;
import pei.registro.vo.IdentificacionVO;
import pei.registro.vo.ParamsVO;
import pei.registro.vo.ProgramaVO;
import pei.registro.vo.ProyectoVO;
import pei.registro.vo.ResultadoConsultaVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.login.beans.Login;
import siges.util.Logger;

/**
 * @author john
 * 
 */
public class Nuevo extends Service {
	// private static final long serialVersionUID = 6150013753285143302L;

	private RegistroDAO registroDAO = new RegistroDAO(new Cursor());
	public String FICHA_IDENTIFICACION;
	public String FICHA_HORIZONTE;
	public String FICHA_DESARROLLO_CURRICULAR;
	public String FICHA_DESARROLLO_ADMINISTRATIVO;
	/* Ajustes 2013 */
	public String FICHA_CURRICULO_LISTA;
	public String FICHA_CURRICULO_NUEVO;
	
	public String FICHA_CIUDADANIA_LISTA;
	public String FICHA_CIUDADANIA_NUEVO;
	
	public String FICHA_PROYECTO40HORAS_LISTA;
	public String FICHA_PROYECTO40HORAS_NUEVO;
	
	public String FICHA_POBLACIONES_LISTA;
	public String FICHA_POBLACIONES_NUEVO;
	
	public String FICHA_PRIMERA_INFANCIA_LISTA;
	public String FICHA_PRIMERA_INFANCIA_NUEVO;
	
	public String FICHA_AJUSTE_LISTA;
	public String FICHA_AJUSTE_NUEVO;
	
	public String FICHA_CRITERIO_EVALUACION_LISTA;
	public String FICHA_CRITERIO_EVALUACION_NUEVO;
	
	
	
	/* ************ */
	public String FICHA_PROGRAMA_SENA_LISTA;
	public String FICHA_PROGRAMA_UNIVERSIDAD_LISTA;
	public String FICHA_OTRO_PROYECTO_LISTA;
	public String FICHA_CAPACITACION_LISTA;
	public String FICHA_PROGRAMA_SENA_NUEVO;
	public String FICHA_PROGRAMA_UNIVERSIDAD_NUEVO;
	public String FICHA_OTRO_PROYECTO_NUEVO;
	public String FICHA_CAPACITACION_NUEVO;
	public String FICHA_DOCUMENTO;
	

	public String manualColegio;
	public String manualAdmin;
	private String[] tipos = { "application/pdf" };

	/*
	 * (non-Javadoc)
	 * 
	 * @see siges.common.service.Service#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		this.config = config;
		context = config.getServletContext();
		FICHA_IDENTIFICACION = config.getInitParameter("FICHA_IDENTIFICACION");
		FICHA_HORIZONTE = config.getInitParameter("FICHA_HORIZONTE");
		FICHA_DESARROLLO_CURRICULAR = config.getInitParameter("FICHA_DESARROLLO_CURRICULAR");
		FICHA_DESARROLLO_ADMINISTRATIVO = config.getInitParameter("FICHA_DESARROLLO_ADMINISTRATIVO");
		/* Ajustes Junio  2013*/
		FICHA_CURRICULO_LISTA = config.getInitParameter("FICHA_CURRICULO_LISTA");
		FICHA_CURRICULO_NUEVO = config.getInitParameter("FICHA_CURRICULO_NUEVO");
		
		FICHA_CIUDADANIA_LISTA = config.getInitParameter("FICHA_CIUDADANIA_LISTA");
		FICHA_CIUDADANIA_NUEVO = config.getInitParameter("FICHA_CIUDADANIA_NUEVO");
		
		FICHA_PROYECTO40HORAS_LISTA = config.getInitParameter("FICHA_PROYECTO40HORAS_LISTA");
		FICHA_PROYECTO40HORAS_NUEVO = config.getInitParameter("FICHA_PROYECTO40HORAS_NUEVO");
		
		FICHA_POBLACIONES_LISTA = config.getInitParameter("FICHA_POBLACIONES_LISTA");
		FICHA_POBLACIONES_NUEVO = config.getInitParameter("FICHA_POBLACIONES_NUEVO");
		
		FICHA_PRIMERA_INFANCIA_LISTA = config.getInitParameter("FICHA_PRIMERA_INFANCIA_LISTA");
		FICHA_PRIMERA_INFANCIA_NUEVO = config.getInitParameter("FICHA_PRIMERA_INFANCIA_NUEVO");
		
		FICHA_CRITERIO_EVALUACION_LISTA = config.getInitParameter("FICHA_CRITERIO_EVALUACION_LISTA");
		FICHA_CRITERIO_EVALUACION_NUEVO = config.getInitParameter("FICHA_CRITERIO_EVALUACION_NUEVO");
		
		FICHA_AJUSTE_LISTA = config.getInitParameter("FICHA_AJUSTE_LISTA");
		FICHA_AJUSTE_NUEVO = config.getInitParameter("FICHA_AJUSTE_NUEVO");
		/* ****************** */
		FICHA_PROGRAMA_SENA_LISTA = config.getInitParameter("FICHA_PROGRAMA_SENA_LISTA");
		FICHA_PROGRAMA_UNIVERSIDAD_LISTA = config.getInitParameter("FICHA_PROGRAMA_UNIVERSIDAD_LISTA");
		FICHA_OTRO_PROYECTO_LISTA = config.getInitParameter("FICHA_OTRO_PROYECTO_LISTA");
		FICHA_CAPACITACION_LISTA = config.getInitParameter("FICHA_CAPACITACION_LISTA");
		FICHA_PROGRAMA_SENA_NUEVO = config.getInitParameter("FICHA_PROGRAMA_SENA_NUEVO");
		FICHA_PROGRAMA_UNIVERSIDAD_NUEVO = config.getInitParameter("FICHA_PROGRAMA_UNIVERSIDAD_NUEVO");
		FICHA_OTRO_PROYECTO_NUEVO = config.getInitParameter("FICHA_OTRO_PROYECTO_NUEVO");
		FICHA_CAPACITACION_NUEVO = config.getInitParameter("FICHA_CAPACITACION_NUEVO");
		FICHA_DOCUMENTO = config.getInitParameter("FICHA_DOCUMENTO");
		manualColegio = config.getInitParameter("manualColegio");
		manualAdmin = config.getInitParameter("manualAdmin");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * siges.common.service.Service#process(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String FICHA = null;
		HttpSession session = request.getSession();
		String dispatcher[] = new String[2];
		Login usuVO = (Login) session.getAttribute("login");
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			FICHA = processMultipart(request, session, usuVO);
		} else {
			int CMD = getCmd(request, session, ParamsVO.CMD_NUEVO);
			int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
			FiltroRegistroVO filtro = (FiltroRegistroVO) session
					.getAttribute(ParamsVO.BEAN_FILTRO_REGISTRO);
			// validar el filtro
			if (filtro == null) {
				filtro = new FiltroRegistroVO();
			}
			if (!filtro.isFilEvaluado()) {
				if (usuVO.getInstId() != null && !usuVO.getInstId().equals("")) {
					filtro.setFilLocalidad(Integer.parseInt(usuVO.getLocId()));
					filtro.setFilInstitucion(Long.parseLong(usuVO.getInstId()));
					filtro.setFilDisabled(false);
					filtro.setFilDisabled_(null);
					filtro.setFilManual(manualColegio);
				} else {
					if (usuVO.getMunId() != null
							&& !usuVO.getMunId().equals("")) {
						filtro.setFilLocalidad(Integer.parseInt(usuVO
								.getMunId()));
						filtro.setFilLocalidadBloqueada(ParamsVO.DISABLED);
					}
					filtro.setFilDisabled(true);
					filtro.setFilDisabled_(ParamsVO.DISABLED);
					filtro.setFilManual(manualAdmin);
				}
				filtro.setFilEvaluado(true);
				filtro = registroDAO.getLabels(filtro);
				session.setAttribute(ParamsVO.BEAN_FILTRO_REGISTRO, filtro);
			}
			if (filtro.isFilDisabled()) {
				registroInit(request, session, usuVO, filtro);
			}
			IdentificacionVO identificacion = (IdentificacionVO) session.getAttribute(ParamsVO.BEAN_IDENTIFICACION);
			DesarrolloCurricularVO desarrolloCurricular = null;
			DesarrolloAdministrativoVO desarrolloAdministrativo = null;
			ProgramaVO programa = null;
			// System.out.println("params: "+TIPO+"_"+CMD);
			switch (TIPO) {
			case ParamsVO.FICHA_IDENTIFICACION:
				FICHA = FICHA_IDENTIFICACION;
				switch (CMD) {
				case ParamsVO.CMD_NUEVO:
					identificacionEditar(request, session, usuVO,
							identificacion, filtro);
					break;
				case ParamsVO.CMD_GUARDAR:
					identificacionGuardar(request, session, usuVO,
							identificacion, filtro);
					break;
				case ParamsVO.CMD_GENERAR:

					ResultadoConsultaVO resultadoConsultaVO = identificacionGenerarReporte(
							identificacion, usuVO);
					// enviarArchivo(request,response,usuVO,resultadoConsultaVO
					// );
					// return null;
					request.setAttribute("resultadoConsultaVO",
							resultadoConsultaVO);
					break;
				}
				identificacionInit(request, session, usuVO, identificacion,
						filtro);
				break;

			case ParamsVO.FICHA_HORIZONTE:
				HorizonteVO horizonte = (HorizonteVO) session
						.getAttribute(ParamsVO.BEAN_HORIZONTE);
				FICHA = FICHA_HORIZONTE;
				switch (CMD) {
				case ParamsVO.CMD_NUEVO:
					horizonteEditar(request, session, usuVO, horizonte, filtro);
					break;
				case ParamsVO.CMD_GUARDAR:
					horizonteGuardar(request, session, usuVO, horizonte, filtro);
					break;
				}
				horizonteInit(request, session, usuVO, horizonte, filtro);
				break;
			case ParamsVO.FICHA_DESARROLLO_CURRICULAR:
				desarrolloCurricular = (DesarrolloCurricularVO) session
						.getAttribute(ParamsVO.BEAN_DESARROLLO_CURRICULAR);
				FICHA = FICHA_DESARROLLO_CURRICULAR;
				switch (CMD) {
				case ParamsVO.CMD_NUEVO:
					desarrolloCurricularEditar(request, session, usuVO,
							desarrolloCurricular, filtro);
					break;
				case ParamsVO.CMD_GUARDAR:
					desarrolloCurricularGuardar(request, session, usuVO,
							desarrolloCurricular, filtro);
					break;
				}
				desarrolloCurricularInit(request, session, usuVO,
						desarrolloCurricular, filtro);
				break;
			
			/*Ajustes Junio 2013*/
			case ParamsVO.FICHA_CURRICULO:
				desarrolloCurricular = (DesarrolloCurricularVO) session.getAttribute(ParamsVO.BEAN_DESARROLLO_CURRICULAR);
				CurriculoVO curriculo = (CurriculoVO) session.getAttribute(ParamsVO.BEAN_CURRICULO);
				FICHA = FICHA_CURRICULO_LISTA;
				switch (CMD) {
				case ParamsVO.CMD_BUSCAR:
					curriculoBuscar(request, session, usuVO,desarrolloCurricular, curriculo);
					break;
				case ParamsVO.CMD_NUEVO:
					curriculoNuevo(request, session, usuVO,desarrolloCurricular, curriculo);
					FICHA = FICHA_CURRICULO_NUEVO;
					break;
				case ParamsVO.CMD_GUARDAR:
					curriculoGuardar(request, session, usuVO,desarrolloCurricular, curriculo);
					curriculoBuscar(request, session, usuVO,desarrolloCurricular, curriculo);
					break;
				case ParamsVO.CMD_ELIMINAR:
					curriculoEliminar(request, session, usuVO,desarrolloCurricular, curriculo);
					curriculoBuscar(request, session, usuVO,desarrolloCurricular, curriculo);
					break;
				}
				break;
				
			case ParamsVO.FICHA_CRITERIO_EVALUACION:
				desarrolloCurricular = (DesarrolloCurricularVO) session.getAttribute(ParamsVO.BEAN_DESARROLLO_CURRICULAR);
				CriterioEvaluacionVO criterioEvaluacion = (CriterioEvaluacionVO) session.getAttribute(ParamsVO.BEAN_CRITERIO_EVALUACION);
				FICHA = FICHA_CRITERIO_EVALUACION_LISTA;
				switch (CMD) {
				case ParamsVO.CMD_BUSCAR:
					criterioEvaluacionBuscar(request, session, usuVO,desarrolloCurricular, criterioEvaluacion);
					break;
				case ParamsVO.CMD_NUEVO:
					criterioEvaluacionNuevo(request, session, usuVO,desarrolloCurricular, criterioEvaluacion);
					FICHA = FICHA_CRITERIO_EVALUACION_NUEVO;
					break;
				case ParamsVO.CMD_GUARDAR:
					criterioEvaluacionGuardar(request, session, usuVO,desarrolloCurricular, criterioEvaluacion);
					criterioEvaluacionBuscar(request, session, usuVO,desarrolloCurricular, criterioEvaluacion);
					break;
				case ParamsVO.CMD_ELIMINAR:
					criterioEvaluacionEliminar(request, session, usuVO,desarrolloCurricular, criterioEvaluacion);
					criterioEvaluacionBuscar(request, session, usuVO,desarrolloCurricular, criterioEvaluacion);
					break;
				}
				break;
				
			case ParamsVO.FICHA_PROYECTO_40_HORAS:
				desarrolloCurricular = (DesarrolloCurricularVO) session.getAttribute(ParamsVO.BEAN_DESARROLLO_CURRICULAR);
				Proyecto40HorasVO proyecto40Horas = (Proyecto40HorasVO) session.getAttribute(ParamsVO.BEAN_PROYECTO_40_HORAS);
				FICHA = FICHA_PROYECTO40HORAS_LISTA;
				switch (CMD) {
				case ParamsVO.CMD_BUSCAR:
					proyecto40HorasBuscar(request, session, usuVO,desarrolloCurricular, proyecto40Horas);
					break;
				case ParamsVO.CMD_NUEVO:
					proyecto40HorasNuevo(request, session, usuVO,desarrolloCurricular, proyecto40Horas);
					FICHA = FICHA_PROYECTO40HORAS_NUEVO;
					break;
				case ParamsVO.CMD_GUARDAR:
					proyecto40HorasGuardar(request, session, usuVO,desarrolloCurricular, proyecto40Horas);
					proyecto40HorasBuscar(request, session, usuVO,desarrolloCurricular, proyecto40Horas);
					break;
				case ParamsVO.CMD_ELIMINAR:
					proyecto40HorasEliminar(request, session, usuVO,desarrolloCurricular, proyecto40Horas);
					proyecto40HorasBuscar(request, session, usuVO,desarrolloCurricular, proyecto40Horas);
					break;
				}
				break;
				
			case ParamsVO.FICHA_POBLACIONES:
				desarrolloCurricular = (DesarrolloCurricularVO) session.getAttribute(ParamsVO.BEAN_DESARROLLO_CURRICULAR);
				PoblacionesVO poblaciones = (PoblacionesVO) session.getAttribute(ParamsVO.BEAN_POBLACIONES);
				FICHA = FICHA_POBLACIONES_LISTA;
				switch (CMD) {
				case ParamsVO.CMD_BUSCAR:
					poblacionesBuscar(request, session, usuVO,desarrolloCurricular, poblaciones);
					break;
				case ParamsVO.CMD_NUEVO:
					poblacionesNuevo(request, session, usuVO,desarrolloCurricular, poblaciones);
					FICHA = FICHA_POBLACIONES_NUEVO;
					break;
				case ParamsVO.CMD_GUARDAR:
					poblacionesGuardar(request, session, usuVO,desarrolloCurricular, poblaciones);
					poblacionesBuscar(request, session, usuVO,desarrolloCurricular, poblaciones);
					break;
				case ParamsVO.CMD_ELIMINAR:
					poblacionesEliminar(request, session, usuVO,desarrolloCurricular, poblaciones);
					poblacionesBuscar(request, session, usuVO,desarrolloCurricular, poblaciones);
					break;
				}
				break;
				
			case ParamsVO.FICHA_PRIMERA_INFANCIA:
				desarrolloCurricular = (DesarrolloCurricularVO) session.getAttribute(ParamsVO.BEAN_DESARROLLO_CURRICULAR);
				PrimeraInfanciaVO primeraInfancia = (PrimeraInfanciaVO) session.getAttribute(ParamsVO.BEAN_PRIMERA_INFANCIA);
				FICHA = FICHA_PRIMERA_INFANCIA_LISTA;
				switch (CMD) {
				case ParamsVO.CMD_BUSCAR:
					primeraInfanciaBuscar(request, session, usuVO,desarrolloCurricular, primeraInfancia);
					break;
				case ParamsVO.CMD_NUEVO:
					primeraInfanciaNuevo(request, session, usuVO,desarrolloCurricular, primeraInfancia);
					FICHA = FICHA_PRIMERA_INFANCIA_NUEVO;
					break;
				case ParamsVO.CMD_GUARDAR:
					primeraInfanciaGuardar(request, session, usuVO,desarrolloCurricular, primeraInfancia);
					primeraInfanciaBuscar(request, session, usuVO,desarrolloCurricular, primeraInfancia);
					break;
				case ParamsVO.CMD_ELIMINAR:
					primeraInfanciaEliminar(request, session, usuVO,desarrolloCurricular, primeraInfancia);
					primeraInfanciaBuscar(request, session, usuVO,desarrolloCurricular, primeraInfancia);
					break;
				}
				break;
			case ParamsVO.FICHA_CIUDADANIA:
				desarrolloCurricular = (DesarrolloCurricularVO) session.getAttribute(ParamsVO.BEAN_DESARROLLO_CURRICULAR);
				CiudadaniaVO ciudadania = (CiudadaniaVO) session.getAttribute(ParamsVO.BEAN_CIUDADANIA);
				FICHA = FICHA_CIUDADANIA_LISTA;
				switch (CMD) {
				case ParamsVO.CMD_BUSCAR:
					ciudadaniaBuscar(request, session, usuVO,desarrolloCurricular, ciudadania);
					break;
				case ParamsVO.CMD_NUEVO:
					ciudadaniaNuevo(request, session, usuVO,desarrolloCurricular, ciudadania);
					FICHA = FICHA_CIUDADANIA_NUEVO;
					break;
				case ParamsVO.CMD_GUARDAR:
					ciudadaniaGuardar(request, session, usuVO,desarrolloCurricular, ciudadania);
					ciudadaniaBuscar(request, session, usuVO,desarrolloCurricular, ciudadania);
					break;
				case ParamsVO.CMD_ELIMINAR:
					ciudadaniaEliminar(request, session, usuVO,desarrolloCurricular, ciudadania);
					ciudadaniaBuscar(request, session, usuVO,desarrolloCurricular, ciudadania);
					break;
				}
				break;
				
			case ParamsVO.FICHA_AJUSTES:
				AjusteVO ajuste = (AjusteVO) session.getAttribute(ParamsVO.BEAN_AJUSTE);
				FICHA = FICHA_AJUSTE_LISTA;
				switch (CMD) {
				case ParamsVO.CMD_BUSCAR:
					ajusteBuscar(request, session, usuVO,identificacion, ajuste);
					break;
				case ParamsVO.CMD_NUEVO:
					ajusteNuevo(request, session, usuVO,identificacion, ajuste);
					FICHA = FICHA_AJUSTE_NUEVO;
					break;
				case ParamsVO.CMD_GUARDAR:
					ajuste.setAjuUsuario(usuVO.getUsuarioId());
					ajusteGuardar(request, session, usuVO,identificacion, ajuste);
					ajusteBuscar(request, session, usuVO,identificacion, ajuste);
					break;
				case ParamsVO.CMD_ELIMINAR:
					ajusteEliminar(request, session, usuVO,identificacion, ajuste);
					ajusteBuscar(request, session, usuVO,identificacion, ajuste);
					break;
				}
				break;
			/* ******************* */
				
			case ParamsVO.FICHA_PROGRAMA_SENA:
				desarrolloCurricular = (DesarrolloCurricularVO) session.getAttribute(ParamsVO.BEAN_DESARROLLO_CURRICULAR);
				programa = (ProgramaVO) session.getAttribute(ParamsVO.BEAN_PROGRAMA_SENA);
				FICHA = FICHA_PROGRAMA_SENA_LISTA;
				switch (CMD) {
				case ParamsVO.CMD_BUSCAR:
					programaSenaBuscar(request, session, usuVO,
							desarrolloCurricular, programa);
					break;
				case ParamsVO.CMD_NUEVO:
					programaSenaNuevo(request, session, usuVO,
							desarrolloCurricular, programa);
					FICHA = FICHA_PROGRAMA_SENA_NUEVO;
					break;
				case ParamsVO.CMD_GUARDAR:
					programaSenaGuardar(request, session, usuVO,
							desarrolloCurricular, programa);
					programaSenaBuscar(request, session, usuVO,
							desarrolloCurricular, programa);
					break;
				case ParamsVO.CMD_ELIMINAR:
					programaSenaEliminar(request, session, usuVO,
							desarrolloCurricular, programa);
					programaSenaBuscar(request, session, usuVO,
							desarrolloCurricular, programa);
					break;
				}
				break;
			case ParamsVO.FICHA_PROGRAMA_UNIVERSIDAD:
				desarrolloCurricular = (DesarrolloCurricularVO) session
						.getAttribute(ParamsVO.BEAN_DESARROLLO_CURRICULAR);
				programa = (ProgramaVO) session
						.getAttribute(ParamsVO.BEAN_PROGRAMA_UNIVERSIDAD);
				FICHA = FICHA_PROGRAMA_UNIVERSIDAD_LISTA;
				switch (CMD) {
				case ParamsVO.CMD_BUSCAR:
					programaUniversidadBuscar(request, session, usuVO,
							desarrolloCurricular, programa);
					break;
				case ParamsVO.CMD_NUEVO:
					programaUniversidadNuevo(request, session, usuVO,
							desarrolloCurricular, programa);
					FICHA = FICHA_PROGRAMA_UNIVERSIDAD_NUEVO;
					break;
				case ParamsVO.CMD_GUARDAR:
					programaUniversidadGuardar(request, session, usuVO,
							desarrolloCurricular, programa);
					programaUniversidadBuscar(request, session, usuVO,
							desarrolloCurricular, programa);
					break;
				case ParamsVO.CMD_ELIMINAR:
					programaUniversidadEliminar(request, session, usuVO,
							desarrolloCurricular, programa);
					programaUniversidadBuscar(request, session, usuVO,
							desarrolloCurricular, programa);
					break;
				}
				break;
			case ParamsVO.FICHA_CAPACITACION:
				desarrolloCurricular = (DesarrolloCurricularVO) session
						.getAttribute(ParamsVO.BEAN_DESARROLLO_CURRICULAR);
				CapacitacionVO capacitacion = (CapacitacionVO) session
						.getAttribute(ParamsVO.BEAN_CAPACITACION);
				FICHA = FICHA_CAPACITACION_LISTA;
				switch (CMD) {
				case ParamsVO.CMD_BUSCAR:
					capacitacionBuscar(request, session, usuVO,
							desarrolloCurricular, capacitacion);
					break;
				case ParamsVO.CMD_NUEVO:
					capacitacionNuevo(request, session, usuVO,
							desarrolloCurricular, capacitacion);
					FICHA = FICHA_CAPACITACION_NUEVO;
					break;
				case ParamsVO.CMD_GUARDAR:
					capacitacionGuardar(request, session, usuVO,
							desarrolloCurricular, capacitacion);
					capacitacionBuscar(request, session, usuVO,
							desarrolloCurricular, capacitacion);
					break;
				case ParamsVO.CMD_ELIMINAR:
					capacitacionEliminar(request, session, usuVO,
							desarrolloCurricular, capacitacion);
					capacitacionBuscar(request, session, usuVO,
							desarrolloCurricular, capacitacion);
					break;
				}
				break;
			case ParamsVO.FICHA_OTRO_PROYECTO:
				desarrolloCurricular = (DesarrolloCurricularVO) session.getAttribute(ParamsVO.BEAN_DESARROLLO_CURRICULAR);
				ProyectoVO proyecto = (ProyectoVO) session.getAttribute(ParamsVO.BEAN_OTRO_PROYECTO);
				FICHA = FICHA_OTRO_PROYECTO_LISTA;
				switch (CMD) {
				case ParamsVO.CMD_BUSCAR:
					otroProyectoBuscar(request, session, usuVO,desarrolloCurricular, proyecto);
					break;
				case ParamsVO.CMD_NUEVO:
					otroProyectoNuevo(request, session, usuVO,desarrolloCurricular, proyecto);
					FICHA = FICHA_OTRO_PROYECTO_NUEVO;
					break;
				case ParamsVO.CMD_EDITAR:
					otroProyectoEditar(request, session, usuVO,desarrolloCurricular, proyecto);
					FICHA = FICHA_OTRO_PROYECTO_NUEVO;
					break;
				case ParamsVO.CMD_GUARDAR:
					otroProyectoGuardar(request, session, usuVO,desarrolloCurricular, proyecto);
					otroProyectoBuscar(request, session, usuVO,desarrolloCurricular, proyecto);
					break;
				case ParamsVO.CMD_ELIMINAR:
					otroProyectoEliminar(request, session, usuVO,desarrolloCurricular, proyecto);
					otroProyectoBuscar(request, session, usuVO,desarrolloCurricular, proyecto);
					break;
				}
				break;
				
			
			case ParamsVO.FICHA_DESARROLLO_ADMINISTRATIVO:
				desarrolloAdministrativo = (DesarrolloAdministrativoVO) session
						.getAttribute(ParamsVO.BEAN_DESARROLLO_ADMINISTRATIVO);
				FICHA = FICHA_DESARROLLO_ADMINISTRATIVO;
				switch (CMD) {
				case ParamsVO.CMD_NUEVO:
					desarrolloAdministrativoEditar(request, session, usuVO,
							desarrolloAdministrativo, filtro);
					break;
				case ParamsVO.CMD_GUARDAR:
					desarrolloAdministrativoGuardar(request, session, usuVO,
							desarrolloAdministrativo, filtro);
					break;
				}
				desarrolloAdministrativoInit(request, session, usuVO,
						desarrolloAdministrativo, filtro);
				break;
			case ParamsVO.FICHA_DOCUMENTO:
				DocumentoVO documento = (DocumentoVO) session
						.getAttribute(ParamsVO.BEAN_DOCUMENTO);
				FICHA = FICHA_DOCUMENTO;
				switch (CMD) {
				case ParamsVO.CMD_NUEVO:
					documentoNuevo(request, session, usuVO, identificacion,
							null);
					break;
				case ParamsVO.CMD_EDITAR:
					documentoEditar(request, session, usuVO, identificacion,
							documento);
					break;
				case ParamsVO.CMD_GUARDAR:
					documentoGuardar(request, session, usuVO, identificacion,
							documento);
					break;
				case ParamsVO.CMD_ELIMINAR:
					documentoEliminar(request, session, usuVO, identificacion,
							documento);
					break;
				}
				documentoBuscar(request, session, usuVO, identificacion,
						documento);
				break;
			}
		}
		dispatcher[0] = String.valueOf(Params.INCLUDE);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param identificacion
	 * @throws ServletException
	 */
	private void identificacionEditar(HttpServletRequest request,
			HttpSession session, Login usuVO, IdentificacionVO identificacion,
			FiltroRegistroVO filtro) throws ServletException {
		try {
			session.removeAttribute(ParamsVO.BEAN_IDENTIFICACION);
			if (filtro.getFilInstitucion() > 0) {
				session.setAttribute(ParamsVO.BEAN_IDENTIFICACION, registroDAO
						.getIdentificacion(filtro.getFilInstitucion()));
				Logger.print(usuVO.getUsuarioId(), "Consulta de PEI, m骴ulo de identificaci髇. ",
							7, 1, this.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param identificacion
	 * @throws ServletException
	 */
	private void identificacionInit(HttpServletRequest request,
			HttpSession session, Login usuVO, IdentificacionVO identificacion,
			FiltroRegistroVO filtro) throws ServletException {
		try {
			request.setAttribute("listaEtapa", registroDAO.getListaEtapaDesarrollo());
			request.setAttribute("listaEnfoque", registroDAO.getListaEnfoque());
			request.setAttribute("listaEnfasis", registroDAO.getListaEnfasis());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param identificacion
	 * @throws ServletException
	 */
	private void identificacionGuardar(HttpServletRequest request,
			HttpSession session, Login usuVO, IdentificacionVO identificacion,
			FiltroRegistroVO filtro) throws ServletException {
		try {
			if (identificacion != null) {
				if (!identificacion.isEdicion()) {
					identificacion = registroDAO.ingresarIdentificacion(identificacion);
					Logger.print(usuVO.getUsuarioId(),"PEI: Ingreso de Identificacion. "+ identificacion.getIdenInstitucion(), 6,1, this.toString());
				} else {
					identificacion = registroDAO.actualizarIdentificacion(identificacion);
					Logger.print(usuVO.getUsuarioId(),"PEI: Actualizacion de Identificacion. "+ identificacion.getIdenInstitucion(), 7,1, this.toString());
				}
				request.setAttribute(ParamsVO.SMS,"La informaci贸n ha sigo guardada satisfactoriamente");
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS,"Error interno: " + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param horizonte
	 * @throws ServletException
	 */
	private void horizonteInit(HttpServletRequest request, HttpSession session, Login usuVO, HorizonteVO horizonte, FiltroRegistroVO filtro) throws ServletException {
		try {
			request.setAttribute("listaDiagnostico", registroDAO.getListaDiagnostico());
			request.setAttribute("listaProceso", registroDAO.getListaProceso());
			request.setAttribute("listaEtapa", registroDAO.getListaEtapa());
			Logger.print(usuVO.getUsuarioId(), "Consulta de PEI, m骴ulo de Horizonte Institucional. ",
					7, 1, this.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param identificacion
	 * @throws ServletException
	 */
	private void horizonteEditar(HttpServletRequest request, HttpSession session, Login usuVO, HorizonteVO horizonte, FiltroRegistroVO filtro) throws ServletException {
		try {
			session.removeAttribute(ParamsVO.BEAN_HORIZONTE);
			session.setAttribute(ParamsVO.BEAN_HORIZONTE, registroDAO.getHorizonte(filtro.getFilInstitucion(), filtro.isFilDisabled()));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param horizonte
	 * @throws ServletException
	 */
	private void horizonteGuardar(HttpServletRequest request, HttpSession session, Login usuVO, HorizonteVO horizonte, FiltroRegistroVO filtro) throws ServletException {
		try {
			if (horizonte != null) {
				if (horizonte.isEdicion()) {
					horizonte = registroDAO.actualizarHorizonte(horizonte);
					Logger.print(usuVO.getUsuarioId(), "PEI: Actualizacion de Horizonte. "+ horizonte.getHorInstitucion(), 7, 1,this.toString());
				}
				request.setAttribute(ParamsVO.SMS,"La informaci贸n ha sigo actualizada satisfactoriamente");
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS,
					"Error interno: " + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param desarrolloCurricular
	 * @throws ServletException
	 */
	private void desarrolloCurricularInit(HttpServletRequest request,
			HttpSession session, Login usuVO,
			DesarrolloCurricularVO desarrolloCurricular, FiltroRegistroVO filtro)
			throws ServletException {
		try {
			/* Ajustes Junio 2013 */
			request.setAttribute("listaEstructuraCurricular",registroDAO.getListaEstructuraCurricular());
			request.setAttribute("listaPlanEstudios",registroDAO.getListaPlanEstudios());
			request.setAttribute("listaSistemaEvaluacion", registroDAO.getListaSistemaEvaluacion());
			request.setAttribute("listaProyectoCiudadania", registroDAO.getListaProyectoCiudadania());
			/* ***************** */
			request.setAttribute("listaFase",registroDAO.getListaFaseCurricular());
			request.setAttribute("listaElemento",registroDAO.getListaElemento());
			request.setAttribute("listaEtapa",registroDAO.getListaEtapaProyecto());
			request.setAttribute("listaSiNo",registroDAO.getListaSiNo());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param identificacion
	 * @throws ServletException
	 */
	private void desarrolloCurricularEditar(HttpServletRequest request,HttpSession session, Login usuVO,DesarrolloCurricularVO desarrolloCurricular, FiltroRegistroVO filtro)throws ServletException {
		try {
			session.removeAttribute(ParamsVO.BEAN_DESARROLLO_CURRICULAR);
			session.setAttribute(ParamsVO.BEAN_DESARROLLO_CURRICULAR,registroDAO.getDesarrolloCurricular(filtro.getFilInstitucion(), filtro.isFilDisabled()));
			Logger.print(usuVO.getUsuarioId(), "Consulta de PEI, m骴ulo de Desarrollo Curricular. ",
					7, 1, this.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param desarrolloCurricular
	 * @throws ServletException
	 */
	private void desarrolloCurricularGuardar(HttpServletRequest request,
			HttpSession session, Login usuVO,
			DesarrolloCurricularVO desarrolloCurricular, FiltroRegistroVO filtro)
			throws ServletException {
		try {
			if (desarrolloCurricular != null) {
				if (desarrolloCurricular.isEdicion()) {
					desarrolloCurricular = registroDAO.actualizarDesarrolloCurricular(desarrolloCurricular);
					Logger.print(usuVO.getUsuarioId(),"PEI: Actualizacion de Desarrollo Curricular. "+ desarrolloCurricular.getDesInstitucion(),7, 1, this.toString());
					session.setAttribute(ParamsVO.BEAN_DESARROLLO_CURRICULAR,registroDAO.getDesarrolloCurricular(desarrolloCurricular.getDesInstitucion(),filtro.isFilDisabled()));
				}
				request.setAttribute(ParamsVO.SMS,"La informaci贸n ha sigo actualizada satisfactoriamente");
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS,
					"Error interno: " + e.getMessage());
		}
	}
	
	/* Ajustes Junio 2013 */
	
	/* ******** CURRICULO ********* */
	private void curriculoBuscar(HttpServletRequest request, HttpSession session, Login usuVO, DesarrolloCurricularVO desarrolloCurricular, CurriculoVO curriculoVO) throws ServletException {
		try {
			request.setAttribute("listaCurriculo",registroDAO.getListaCurriculo(desarrolloCurricular));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Error interno: " + e.getMessage());
		}
	}
	
	private void curriculoNuevo(HttpServletRequest request,HttpSession session, Login usuVO,DesarrolloCurricularVO desarrolloCurricular, CurriculoVO curriculoVO)throws ServletException {
		try {
			session.removeAttribute(ParamsVO.BEAN_CURRICULO);
			//session.setAttribute(ParamsVO.BEAN_CURRICULO, new);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	private void curriculoGuardar(HttpServletRequest request,HttpSession session, Login usuVO,DesarrolloCurricularVO desarrolloCurricular, CurriculoVO curriculoVO)throws ServletException {
		try {
			if (curriculoVO == null)
				return;
			curriculoVO = registroDAO.ingresarCurriculo(curriculoVO);
			Logger.print(
					usuVO.getUsuarioId(),
					"PEI: Registro Curriculo. "+ curriculoVO.getCurCodigoInstitucion() + " "+ curriculoVO.getCurConsecutivo(), 7, 1,	this.toString());
					session.removeAttribute(ParamsVO.BEAN_CURRICULO);
					request.setAttribute(ParamsVO.SMS,"La informaci贸n ha sigo ingresada satisfactoriamente");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error ingresando programa: "
					+ e.getMessage());
		}
	}
	
	private void curriculoEliminar(HttpServletRequest request,HttpSession session, Login usuVO,DesarrolloCurricularVO desarrolloCurricular, CurriculoVO curriculoVO)throws ServletException {
		try {
			if (curriculoVO == null) {
				request.setAttribute(ParamsVO.SMS, "Error eliminando programa: No hay programa seleccionado");
				return;
			}
			curriculoVO = registroDAO.eliminarCurriculo(curriculoVO);
			Logger.print(usuVO.getUsuarioId(),"PEI: Eliminar Curriculo. "+ curriculoVO.getCurCodigoInstitucion() + " " + curriculoVO.getCurConsecutivo(), 7, 1,this.toString());
			session.removeAttribute(ParamsVO.BEAN_CURRICULO);
			request.setAttribute(ParamsVO.SMS,
					"La informaci贸n ha sigo eliminada satisfactoriamente");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error eliminando programa: "
					+ e.getMessage());
		}
	}
	
	/* ******** CURRICULO ********* */
	
	
	/* ******** CIUDADANIA ********* */
	private void ciudadaniaBuscar(HttpServletRequest request, HttpSession session, Login usuVO, DesarrolloCurricularVO desarrolloCurricular, CiudadaniaVO ciudadaniaVO) throws ServletException {
		try {
			request.setAttribute("listaCiudadania",registroDAO.getListaCiudadania(desarrolloCurricular));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Error interno: " + e.getMessage());
		}
	}
	
	private void ciudadaniaNuevo(HttpServletRequest request,HttpSession session, Login usuVO,DesarrolloCurricularVO desarrolloCurricular, CiudadaniaVO ciudadaniaVO)throws ServletException {
		try {
			session.removeAttribute(ParamsVO.BEAN_CIUDADANIA);
			//session.setAttribute(ParamsVO.BEAN_CURRICULO, new);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	private void ciudadaniaGuardar(HttpServletRequest request,HttpSession session, Login usuVO,DesarrolloCurricularVO desarrolloCurricular, CiudadaniaVO ciudadaniaVO)throws ServletException {
		try {
			if (ciudadaniaVO == null)
				return;
			ciudadaniaVO = registroDAO.ingresarCiudadania(ciudadaniaVO);
			Logger.print(
					usuVO.getUsuarioId(),
					"PEI: Registro Curriculo. "+ ciudadaniaVO.getCiuCodigoInstitucion() + " "+ ciudadaniaVO.getCiuConsecutivo(), 7, 1,	this.toString());
					session.removeAttribute(ParamsVO.BEAN_CIUDADANIA);
					request.setAttribute(ParamsVO.SMS,"La informaci贸n ha sigo ingresada satisfactoriamente");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error ingresando programa: "
					+ e.getMessage());
		}
	}
	
	private void ciudadaniaEliminar(HttpServletRequest request,HttpSession session, Login usuVO,DesarrolloCurricularVO desarrolloCurricular, CiudadaniaVO ciudadaniaVO)throws ServletException {
		try {
			if (ciudadaniaVO == null) {
				request.setAttribute(ParamsVO.SMS, "Error eliminando programa: No hay programa seleccionado");
				return;
			}
			ciudadaniaVO = registroDAO.eliminarCiudadania(ciudadaniaVO);
			Logger.print(usuVO.getUsuarioId(),"PEI: Eliminar Curriculo. "+ ciudadaniaVO.getCiuCodigoInstitucion() + " " + ciudadaniaVO.getCiuConsecutivo(), 7, 1,this.toString());
			session.removeAttribute(ParamsVO.BEAN_CIUDADANIA);
			request.setAttribute(ParamsVO.SMS,
					"La informaci贸n ha sigo eliminada satisfactoriamente");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error eliminando programa: "
					+ e.getMessage());
		}
	}
	
	/* ******** CIUDADANIA ********* */
	
	/* ******** CRITERIO DE EVALUACION ********* */
	private void criterioEvaluacionBuscar(HttpServletRequest request, HttpSession session, Login usuVO, DesarrolloCurricularVO desarrolloCurricular,CriterioEvaluacionVO criterioEvaluacionVO) throws ServletException {
		try {
			request.setAttribute("listaCriterioEvaluacion",registroDAO.getListaCriterioEvaluacion(desarrolloCurricular));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Error interno: " + e.getMessage());
		}
	}
	
	private void criterioEvaluacionNuevo(HttpServletRequest request,HttpSession session, Login usuVO,DesarrolloCurricularVO desarrolloCurricular, CriterioEvaluacionVO criterioEvaluacionVO)throws ServletException {
		try {
			session.removeAttribute(ParamsVO.BEAN_CRITERIO_EVALUACION);
			//session.setAttribute(ParamsVO.BEAN_CURRICULO, new);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	private void criterioEvaluacionGuardar(HttpServletRequest request,HttpSession session, Login usuVO,DesarrolloCurricularVO desarrolloCurricular, CriterioEvaluacionVO criterioEvaluacionVO)throws ServletException {
		try {
			if (criterioEvaluacionVO == null)
				return;
			criterioEvaluacionVO = registroDAO.ingresarCriterioEvaluacion(criterioEvaluacionVO);
			Logger.print(
					usuVO.getUsuarioId(),
					"PEI: Registro Curriculo. "+ criterioEvaluacionVO.getCriCodigoInstitucion() + " "+ criterioEvaluacionVO.getCriConsecutivo(), 7, 1,	this.toString());
					session.removeAttribute(ParamsVO.BEAN_CRITERIO_EVALUACION);
					request.setAttribute(ParamsVO.SMS,"La informaci贸n ha sigo ingresada satisfactoriamente");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error ingresando programa: "
					+ e.getMessage());
		}
	}
	
	private void criterioEvaluacionEliminar(HttpServletRequest request,HttpSession session, Login usuVO,DesarrolloCurricularVO desarrolloCurricular, CriterioEvaluacionVO criterioEvaluacionVO)throws ServletException {
		try {
			if (criterioEvaluacionVO == null) {
				request.setAttribute(ParamsVO.SMS, "Error eliminando programa: No hay programa seleccionado");
				return;
			}
			criterioEvaluacionVO = registroDAO.eliminarCriterioEvaluacion(criterioEvaluacionVO);
			Logger.print(usuVO.getUsuarioId(),"PEI: Eliminar Curriculo. "+ criterioEvaluacionVO.getCriCodigoInstitucion() + " " + criterioEvaluacionVO.getCriConsecutivo(), 7, 1,this.toString());
			session.removeAttribute(ParamsVO.BEAN_CRITERIO_EVALUACION);
			request.setAttribute(ParamsVO.SMS,
					"La informaci贸n ha sigo eliminada satisfactoriamente");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error eliminando programa: "
					+ e.getMessage());
		}
	}
	
	/* ******** CRITERIO EVALUACION ********* */
	
	/* ******** PROYECTO 40 HORAS ********* */
	private void proyecto40HorasBuscar(HttpServletRequest request, HttpSession session, Login usuVO, DesarrolloCurricularVO desarrolloCurricular,Proyecto40HorasVO proyecto40HorasVO) throws ServletException {
		try {
			request.setAttribute("listaProyecto40Horas",registroDAO.getListaProyecto40Horas(desarrolloCurricular));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Error interno: " + e.getMessage());
		}
	}
	
	private void proyecto40HorasNuevo(HttpServletRequest request,HttpSession session, Login usuVO,DesarrolloCurricularVO desarrolloCurricular, Proyecto40HorasVO proyecto40HorasVO)throws ServletException {
		try {
			session.removeAttribute(ParamsVO.BEAN_PROYECTO_40_HORAS);
			//session.setAttribute(ParamsVO.BEAN_CURRICULO, new);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	private void proyecto40HorasGuardar(HttpServletRequest request,HttpSession session, Login usuVO,DesarrolloCurricularVO desarrolloCurricular, Proyecto40HorasVO proyecto40HorasVO)throws ServletException {
		try {
			if (proyecto40HorasVO == null)
				return;
			proyecto40HorasVO = registroDAO.ingresarProyecto40Horas(proyecto40HorasVO);
			Logger.print(
					usuVO.getUsuarioId(),
					"PEI: Registro Curriculo. "+ proyecto40HorasVO.getHorCodigoInstitucion() + " "+ proyecto40HorasVO.getHorConsecutivo(), 7, 1,	this.toString());
					session.removeAttribute(ParamsVO.BEAN_PROYECTO_40_HORAS);
					request.setAttribute(ParamsVO.SMS,"La informaci贸n ha sigo ingresada satisfactoriamente");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error ingresando programa: "
					+ e.getMessage());
		}
	}
	
	private void proyecto40HorasEliminar(HttpServletRequest request,HttpSession session, Login usuVO,DesarrolloCurricularVO desarrolloCurricular, Proyecto40HorasVO proyecto40HorasVO)throws ServletException {
		try {
			if (proyecto40HorasVO == null) {
				request.setAttribute(ParamsVO.SMS, "Error eliminando programa: No hay programa seleccionado");
				return;
			}
			proyecto40HorasVO = registroDAO.eliminarProyecto40Horas(proyecto40HorasVO);
			Logger.print(usuVO.getUsuarioId(),"PEI: Eliminar Curriculo. "+ proyecto40HorasVO.getHorConsecutivo() + " " + proyecto40HorasVO.getHorConsecutivo(), 7, 1,this.toString());
			session.removeAttribute(ParamsVO.BEAN_PROYECTO_40_HORAS);
			request.setAttribute(ParamsVO.SMS,
					"La informaci贸n ha sigo eliminada satisfactoriamente");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error eliminando programa: "
					+ e.getMessage());
		}
	}
	
	/* ******** PROYECTO 40 HORAS ********* */
	
	/* ******** POBLACIONES ********* */
	private void poblacionesBuscar(HttpServletRequest request, HttpSession session, Login usuVO, DesarrolloCurricularVO desarrolloCurricular,PoblacionesVO poblacionesVO) throws ServletException {
		try {
			request.setAttribute("listaPoblaciones",registroDAO.getListaPoblaciones(desarrolloCurricular));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Error interno: " + e.getMessage());
		}
	}
	
	private void poblacionesNuevo(HttpServletRequest request,HttpSession session, Login usuVO,DesarrolloCurricularVO desarrolloCurricular,PoblacionesVO poblacionesVO)throws ServletException {
		try {
			session.removeAttribute(ParamsVO.BEAN_POBLACIONES);
			//session.setAttribute(ParamsVO.BEAN_CURRICULO, new);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	private void poblacionesGuardar(HttpServletRequest request,HttpSession session, Login usuVO,DesarrolloCurricularVO desarrolloCurricular,PoblacionesVO poblacionesVO)throws ServletException {
		try {
			if (poblacionesVO == null)
				return;
			poblacionesVO = registroDAO.ingresarPoblaciones(poblacionesVO);
					request.setAttribute(ParamsVO.SMS,"La informaci贸n ha sigo ingresada satisfactoriamente");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error ingresando programa: "
					+ e.getMessage());
		}
	}
	
	private void poblacionesEliminar(HttpServletRequest request,HttpSession session, Login usuVO,DesarrolloCurricularVO desarrolloCurricular,PoblacionesVO poblacionesVO)throws ServletException {
		try {
			if (poblacionesVO == null) {
				request.setAttribute(ParamsVO.SMS, "Error eliminando programa: No hay programa seleccionado");
				return;
			}
			poblacionesVO = registroDAO.eliminarPoblaciones(poblacionesVO);
			
			session.removeAttribute(ParamsVO.BEAN_POBLACIONES);
			request.setAttribute(ParamsVO.SMS,
					"La informaci贸n ha sigo eliminada satisfactoriamente");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error eliminando programa: "
					+ e.getMessage());
		}
	}
	
	/* ******** POBLACIONES ********* */
	
	
	/* ******** PRIMERA INFANCIA ********* */
	private void primeraInfanciaBuscar(HttpServletRequest request, HttpSession session, Login usuVO, DesarrolloCurricularVO desarrolloCurricular,PrimeraInfanciaVO primeraInfanciaVO) throws ServletException {
		try {
			request.setAttribute("listaPrimeraInfancia",registroDAO.getListaPrimeraInfancia(desarrolloCurricular));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Error interno: " + e.getMessage());
		}
	}
	
	private void primeraInfanciaNuevo(HttpServletRequest request,HttpSession session, Login usuVO,DesarrolloCurricularVO desarrolloCurricular,PrimeraInfanciaVO primeraInfanciaVO)throws ServletException {
		try {
			session.removeAttribute(ParamsVO.BEAN_PRIMERA_INFANCIA);
			//session.setAttribute(ParamsVO.BEAN_CURRICULO, new);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	private void primeraInfanciaGuardar(HttpServletRequest request,HttpSession session, Login usuVO,DesarrolloCurricularVO desarrolloCurricular,PrimeraInfanciaVO primeraInfanciaVO)throws ServletException {
		try {
			if (primeraInfanciaVO == null)
				return;
			primeraInfanciaVO = registroDAO.ingresarPrimeraInfancia(primeraInfanciaVO);
					request.setAttribute(ParamsVO.SMS,"La informaci贸n ha sigo ingresada satisfactoriamente");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error ingresando programa: "
					+ e.getMessage());
		}
	}
	
	private void primeraInfanciaEliminar(HttpServletRequest request,HttpSession session, Login usuVO,DesarrolloCurricularVO desarrolloCurricular,PrimeraInfanciaVO primeraInfanciaVO)throws ServletException {
		try {
			if (primeraInfanciaVO == null) {
				request.setAttribute(ParamsVO.SMS, "Error eliminando programa: No hay programa seleccionado");
				return;
			}
			primeraInfanciaVO = registroDAO.eliminarPrimeraInfancia(primeraInfanciaVO);
			
			session.removeAttribute(ParamsVO.BEAN_POBLACIONES);
			request.setAttribute(ParamsVO.SMS,
					"La informaci贸n ha sigo eliminada satisfactoriamente");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error eliminando programa: "
					+ e.getMessage());
		}
	}
	
	/* ******** PRIMERA INFANCIA ********* */
	
	
	
	
	/* ******** AJUSTE ********* */
	
	private void ajusteBuscar(HttpServletRequest request, HttpSession session, Login usuVO, IdentificacionVO identificacion, AjusteVO ajusteVO) throws ServletException {
		try {
			request.setAttribute("listaAjuste",registroDAO.getListaAjuste(identificacion));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Error interno: " + e.getMessage());
		}
	}
	
	private void ajusteNuevo(HttpServletRequest request,HttpSession session, Login usuVO,IdentificacionVO identificacion, AjusteVO ajusteVO)throws ServletException {
		try {
			session.removeAttribute(ParamsVO.BEAN_AJUSTE);
			session.setAttribute("vigenciaActual", ""+usuVO.getVigencia_actual());
			//session.setAttribute(ParamsVO.BEAN_CURRICULO, new);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	private void ajusteGuardar(HttpServletRequest request,HttpSession session, Login usuVO,IdentificacionVO identificacion, AjusteVO ajusteVO)throws ServletException {
		try {
			if (ajusteVO == null)
				return;
			ajusteVO = registroDAO.ingresarAjuste(ajusteVO);
			Logger.print(
					usuVO.getUsuarioId(),
					"PEI: Registro Ajuste. "+ ajusteVO.getAjuCodigoInstitucion() + " "+ ajusteVO.getAjuResolucion(), 7, 1,	this.toString());
					session.removeAttribute(ParamsVO.BEAN_AJUSTE);
					request.setAttribute(ParamsVO.SMS,"La informaci贸n ha sigo ingresada satisfactoriamente");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error ingresando programa: "
					+ e.getMessage());
		}
	}
	
	private void ajusteEliminar(HttpServletRequest request,HttpSession session, Login usuVO,IdentificacionVO identificacion, AjusteVO ajusteVO)throws ServletException {
		try {
			if (ajusteVO == null) {
				request.setAttribute(ParamsVO.SMS, "Error eliminando ajuste: No hay ajuste seleccionado");
				return;
			}
			ajusteVO = registroDAO.eliminarAjuste(ajusteVO);
			Logger.print(usuVO.getUsuarioId(),"PEI: Eliminar Ajuste. "+ ajusteVO.getAjuCodigoInstitucion() + " " + ajusteVO.getAjuResolucion(), 7, 1,this.toString());
			session.removeAttribute(ParamsVO.BEAN_AJUSTE);
			request.setAttribute(ParamsVO.SMS,
					"La informaci贸n ha sigo eliminada satisfactoriamente");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error eliminando programa: "
					+ e.getMessage());
		}
	}
	
	
	
	/* ******** AJUSTE ********* */
	
	
	
	
	
	

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param desarrolloCurricular
	 * @param programaSena
	 * @throws ServletException
	 */
	private void programaSenaNuevo(HttpServletRequest request,
			HttpSession session, Login usuVO,
			DesarrolloCurricularVO desarrolloCurricular, ProgramaVO programaSena)
			throws ServletException {
		try {
			request.setAttribute("listaPrograma",
					registroDAO.getListaProgramas());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param desarrolloCurricular
	 * @param programaSena
	 * @throws ServletException
	 */
	private void programaSenaBuscar(HttpServletRequest request,
			HttpSession session, Login usuVO,
			DesarrolloCurricularVO desarrolloCurricular, ProgramaVO programaSena)
			throws ServletException {
		try {
			request.setAttribute("listaProgramaSena",
					registroDAO.getListaProgramaSENA(desarrolloCurricular));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param desarrolloCurricular
	 * @param programaSena
	 * @throws ServletException
	 */
	private void programaSenaGuardar(HttpServletRequest request,
			HttpSession session, Login usuVO,
			DesarrolloCurricularVO desarrolloCurricular, ProgramaVO programaSena)
			throws ServletException {
		try {
			if (programaSena == null)
				return;
			programaSena = registroDAO.ingresarProgramaSENA(programaSena);
			Logger.print(
					usuVO.getUsuarioId(),
					"PEI: Registro Programa SENA. "
							+ programaSena.getProInstitucion() + " "
							+ programaSena.getProCodigo(), 7, 1,
					this.toString());
			session.removeAttribute(ParamsVO.BEAN_PROGRAMA_SENA);
			request.setAttribute(ParamsVO.SMS,
					"La informaci贸n ha sigo ingresada satisfactoriamente");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error ingresando programa: "
					+ e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param desarrolloCurricular
	 * @param programaSena
	 * @throws ServletException
	 */
	private void programaSenaEliminar(HttpServletRequest request,
			HttpSession session, Login usuVO,
			DesarrolloCurricularVO desarrolloCurricular, ProgramaVO programaSena)
			throws ServletException {
		try {
			if (programaSena == null) {
				request.setAttribute(ParamsVO.SMS,
						"Error eliminando programa: No hay programa seleccionado");
				return;
			}
			programaSena = registroDAO.eliminarProgramaSENA(programaSena);
			Logger.print(
					usuVO.getUsuarioId(),
					"PEI: Eliminar Programa SENA. "
							+ programaSena.getProInstitucion() + " "
							+ programaSena.getProCodigo(), 7, 1,
					this.toString());
			session.removeAttribute(ParamsVO.BEAN_PROGRAMA_SENA);
			request.setAttribute(ParamsVO.SMS,
					"La informaci贸n ha sigo eliminada satisfactoriamente");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error eliminando programa: "
					+ e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param desarrolloCurricular
	 * @param programaUniversidad
	 * @throws ServletException
	 */
	private void programaUniversidadNuevo(HttpServletRequest request,
			HttpSession session, Login usuVO,
			DesarrolloCurricularVO desarrolloCurricular,
			ProgramaVO programaUniversidad) throws ServletException {
		try {
			request.setAttribute("listaPrograma",
					registroDAO.getListaProgramas());
			request.setAttribute("listaUniversidad",
					registroDAO.getListaUniversidad());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param desarrolloCurricular
	 * @param programaUniversidad
	 * @throws ServletException
	 */
	private void programaUniversidadBuscar(HttpServletRequest request,
			HttpSession session, Login usuVO,
			DesarrolloCurricularVO desarrolloCurricular,
			ProgramaVO programaUniversidad) throws ServletException {
		try {
			request.setAttribute("listaProgramaUniversidad", registroDAO
					.getListaProgramaUniversidad(desarrolloCurricular));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param desarrolloCurricular
	 * @param programaUniversidad
	 * @throws ServletException
	 */
	private void programaUniversidadGuardar(HttpServletRequest request,
			HttpSession session, Login usuVO,
			DesarrolloCurricularVO desarrolloCurricular,
			ProgramaVO programaUniversidad) throws ServletException {
		try {
			if (programaUniversidad == null)
				return;
			programaUniversidad = registroDAO
					.ingresarProgramaUniversidad(programaUniversidad);
			Logger.print(usuVO.getUsuarioId(),
					"PEI: Registro Programa Universidad. "
							+ programaUniversidad.getProInstitucion() + " "
							+ programaUniversidad.getProUniversidad() + " "
							+ programaUniversidad.getProCodigo(), 7, 1,
					this.toString());
			session.removeAttribute(ParamsVO.BEAN_PROGRAMA_UNIVERSIDAD);
			request.setAttribute(ParamsVO.SMS,
					"La informaci贸n ha sigo ingresada satisfactoriamente");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error ingresando programa: "
					+ e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param desarrolloCurricular
	 * @param programaUniversidad
	 * @throws ServletException
	 */
	private void programaUniversidadEliminar(HttpServletRequest request,
			HttpSession session, Login usuVO,
			DesarrolloCurricularVO desarrolloCurricular,
			ProgramaVO programaUniversidad) throws ServletException {
		try {
			if (programaUniversidad == null) {
				request.setAttribute(ParamsVO.SMS,
						"Error eliminando programa: No hay programa seleccionado");
				return;
			}
			programaUniversidad = registroDAO
					.eliminarProgramaUniversidad(programaUniversidad);
			Logger.print(usuVO.getUsuarioId(),
					"PEI: Eliminar Programa Universidad. "
							+ programaUniversidad.getProInstitucion() + " "
							+ programaUniversidad.getProUniversidad() + " "
							+ programaUniversidad.getProCodigo(), 7, 1,
					this.toString());
			session.removeAttribute(ParamsVO.BEAN_PROGRAMA_UNIVERSIDAD);
			request.setAttribute(ParamsVO.SMS,
					"La informaci贸n ha sigo eliminada satisfactoriamente");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error eliminando programa: "
					+ e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param desarrolloCurricular
	 * @param capacitacion
	 * @throws ServletException
	 */
	private void capacitacionBuscar(HttpServletRequest request,
			HttpSession session, Login usuVO,
			DesarrolloCurricularVO desarrolloCurricular,
			CapacitacionVO capacitacion) throws ServletException {
		try {
			request.setAttribute("listaCapacitacion",
					registroDAO.getListaCapacitacion(desarrolloCurricular));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param desarrolloCurricular
	 * @param capacitacion
	 * @throws ServletException
	 */
	private void capacitacionNuevo(HttpServletRequest request,
			HttpSession session, Login usuVO,
			DesarrolloCurricularVO desarrolloCurricular,
			CapacitacionVO capacitacion) throws ServletException {
		try {
			request.setAttribute("listaUniversidad",
					registroDAO.getListaUniversidad());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param desarrolloCurricular
	 * @param capacitacion
	 * @throws ServletException
	 */
	private void capacitacionGuardar(HttpServletRequest request,
			HttpSession session, Login usuVO,
			DesarrolloCurricularVO desarrolloCurricular,
			CapacitacionVO capacitacion) throws ServletException {
		try {
			if (capacitacion == null)
				return;
			capacitacion = registroDAO.ingresarCapacitacion(capacitacion);
			Logger.print(
					usuVO.getUsuarioId(),
					"PEI: Registro Capacitacion. "
							+ capacitacion.getCapInstitucion() + " "
							+ capacitacion.getCapCodigo(), 7, 1,
					this.toString());
			session.removeAttribute(ParamsVO.BEAN_CAPACITACION);
			request.setAttribute(ParamsVO.SMS,
					"La informaci贸n ha sigo ingresada satisfactoriamente");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS,
					"Error ingresando capacitacion: " + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param desarrolloCurricular
	 * @param capacitacion
	 * @throws ServletException
	 */
	private void capacitacionEliminar(HttpServletRequest request,
			HttpSession session, Login usuVO,
			DesarrolloCurricularVO desarrolloCurricular,
			CapacitacionVO capacitacion) throws ServletException {
		try {
			if (capacitacion == null) {
				request.setAttribute(ParamsVO.SMS,
						"Error eliminando capacitacion: No hay capacitacion seleccionada");
				return;
			}
			capacitacion = registroDAO.eliminarCapacitacion(capacitacion);
			Logger.print(
					usuVO.getUsuarioId(),
					"PEI: Eliminar Capacitacion. "
							+ capacitacion.getCapInstitucion() + " "
							+ capacitacion.getCapCodigo(), 7, 1,
					this.toString());
			session.removeAttribute(ParamsVO.BEAN_CAPACITACION);
			request.setAttribute(ParamsVO.SMS,
					"La informaci贸n ha sigo eliminada satisfactoriamente");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS,
					"Error eliminando capacitacion: " + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param desarrolloCurricular
	 * @param otroProyecto
	 * @throws ServletException
	 */
	private void otroProyectoNuevo(HttpServletRequest request,
			HttpSession session, Login usuVO,
			DesarrolloCurricularVO desarrolloCurricular, ProyectoVO otroProyecto)
			throws ServletException {
		session.removeAttribute(ParamsVO.BEAN_OTRO_PROYECTO);
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param desarrolloCurricular
	 * @param otroProyecto
	 * @throws ServletException
	 */
	private void otroProyectoBuscar(HttpServletRequest request,
			HttpSession session, Login usuVO,
			DesarrolloCurricularVO desarrolloCurricular, ProyectoVO otroProyecto)
			throws ServletException {
		try {
			request.setAttribute("listaOtroProyecto",
					registroDAO.getListaOtroProyecto(desarrolloCurricular));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param desarrolloCurricular
	 * @param otroProyecto
	 * @throws ServletException
	 */
	private void otroProyectoGuardar(HttpServletRequest request,
			HttpSession session, Login usuVO,
			DesarrolloCurricularVO desarrolloCurricular, ProyectoVO otroProyecto)
			throws ServletException {
		try {
			if (otroProyecto == null)
				return;
			if (!otroProyecto.isEdicion()) {
				otroProyecto = registroDAO.ingresarOtroProyecto(otroProyecto);
				Logger.print(
						usuVO.getUsuarioId(),
						"PEI: Registro otro Proyecto. "
								+ otroProyecto.getProyInstitucion() + " "
								+ otroProyecto.getProyCodigo(), 7, 1,
						this.toString());
			} else {
				otroProyecto = registroDAO.actualizarOtroProyecto(otroProyecto);
				Logger.print(
						usuVO.getUsuarioId(),
						"PEI: Actualizacion otro Proyecto. "
								+ otroProyecto.getProyInstitucion() + " "
								+ otroProyecto.getProyCodigo(), 7, 1,
						this.toString());
			}
			session.removeAttribute(ParamsVO.BEAN_OTRO_PROYECTO);
			request.setAttribute(ParamsVO.SMS,
					"La informaci贸n ha sigo ingresada satisfactoriamente");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error ingresando proyecto: "
					+ e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param desarrolloCurricular
	 * @param otroProyecto
	 * @throws ServletException
	 */
	private void otroProyectoEliminar(HttpServletRequest request,
			HttpSession session, Login usuVO,
			DesarrolloCurricularVO desarrolloCurricular, ProyectoVO otroProyecto)
			throws ServletException {
		try {
			if (otroProyecto == null) {
				request.setAttribute(ParamsVO.SMS,
						"Error eliminando proyecto: No hay proyecto seleccionado");
				return;
			}
			otroProyecto = registroDAO.eliminarOtroProyecto(otroProyecto);
			Logger.print(
					usuVO.getUsuarioId(),
					"PEI: Eliminar otro Proyecto. "
							+ otroProyecto.getProyInstitucion() + " "
							+ otroProyecto.getProyCodigo(), 7, 1,
					this.toString());
			session.removeAttribute(ParamsVO.BEAN_OTRO_PROYECTO);
			request.setAttribute(ParamsVO.SMS,
					"La informaci贸n ha sigo eliminada satisfactoriamente");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error eliminando proyecto: "
					+ e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param desarrolloCurricular
	 * @param otroProyecto
	 * @throws ServletException
	 */
	private void otroProyectoEditar(HttpServletRequest request,
			HttpSession session, Login usuVO,
			DesarrolloCurricularVO desarrolloCurricular, ProyectoVO otroProyecto)
			throws ServletException {
		try {
			if (otroProyecto == null) {
				request.setAttribute(ParamsVO.SMS,
						"Error editando proyecto: No hay proyecto seleccionado");
				return;
			}
			session.setAttribute(ParamsVO.BEAN_OTRO_PROYECTO,
					registroDAO.getOtroProyecto(otroProyecto));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Error interno: " + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param desarrolloCurricular
	 * @throws ServletException
	 */
	private void desarrolloAdministrativoInit(HttpServletRequest request,
			HttpSession session, Login usuVO,
			DesarrolloAdministrativoVO desarrolloAdministrativo,FiltroRegistroVO filtro) throws ServletException {
		try {
			request.setAttribute("listaManualConvivencia",registroDAO.getListaManualConvivencia());
			request.setAttribute("listaReglamentoDocentes",registroDAO.getListaReglamentoDocentes());
			request.setAttribute("listaGobiernoEscolar",registroDAO.getListaGobiernoEscolar());
			request.setAttribute("listaFrecuencia",registroDAO.getListaFrecuencia());
			request.setAttribute("listaFrecuenciaEvaluacion",registroDAO.getListaFrecuenciaEvaluacion());
			request.setAttribute("listaEtapa",registroDAO.getListaEtapaProyecto());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param identificacion
	 * @throws ServletException
	 */
	private void desarrolloAdministrativoEditar(HttpServletRequest request,
			HttpSession session, Login usuVO,
			DesarrolloAdministrativoVO desarrolloAdministrativo,
			FiltroRegistroVO filtro) throws ServletException {
		try {
			session.removeAttribute(ParamsVO.BEAN_DESARROLLO_ADMINISTRATIVO);
			session.setAttribute(
					ParamsVO.BEAN_DESARROLLO_ADMINISTRATIVO,
					registroDAO.getDesarrolloAdministrativo(
							filtro.getFilInstitucion(), filtro.isFilDisabled()));
			Logger.print(usuVO.getUsuarioId(), "Consulta de PEI, m骴ulo de Desarrolo Administrativo. ",
					7, 1, this.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param desarrolloCurricular
	 * @throws ServletException
	 */
	private void desarrolloAdministrativoGuardar(HttpServletRequest request,
			HttpSession session, Login usuVO,
			DesarrolloAdministrativoVO desarrolloAdministrativo,
			FiltroRegistroVO filtro) throws ServletException {
		try {
			if (desarrolloAdministrativo != null) {
				if (desarrolloAdministrativo.isEdicion()) {
					desarrolloAdministrativo = registroDAO
							.actualizarDesarrolloAdministrativo(desarrolloAdministrativo);
					Logger.print(
							usuVO.getUsuarioId(),
							"PEI: Actualizacion de Desarrollo Curricular. "
									+ desarrolloAdministrativo
											.getDesInstitucion(), 7, 1,
							this.toString());
					session.setAttribute(
							ParamsVO.BEAN_DESARROLLO_ADMINISTRATIVO,
							registroDAO.getDesarrolloAdministrativo(
									desarrolloAdministrativo
											.getDesInstitucion(), filtro
											.isFilDisabled()));
				}
				request.setAttribute(ParamsVO.SMS,
						"La informaci贸n ha sigo actualizada satisfactoriamente");
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS,
					"Error interno: " + e.getMessage());
		}
	}

	private void registroInit(HttpServletRequest request, HttpSession session,
			Login usuVO, FiltroRegistroVO filtro) throws ServletException {
		try {
			request.setAttribute("listaLocalidad",
					registroDAO.getListaLocalidad());
			if (filtro.getFilLocalidad() > 0) {
				request.setAttribute("listaInstitucion", registroDAO
						.getListaInstitucion(filtro.getFilLocalidad()));
			}
			if (filtro.getFilInstitucion() > 0
					&& filtro.getFilNombreInstitucion() == null) {
				filtro.setFilNombreInstitucion(registroDAO
						.getNombreInstitucion(filtro.getFilInstitucion()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param desarrolloCurricular
	 * @param otroProyecto
	 * @throws ServletException
	 */
	private void documentoBuscar(HttpServletRequest request,
			HttpSession session, Login usuVO, IdentificacionVO identificacion,
			DocumentoVO documento) throws ServletException {
		try {
			request.setAttribute("listaDocumento",
					registroDAO.getListaDocumento(identificacion));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param desarrolloCurricular
	 * @param otroProyecto
	 * @throws ServletException
	 */
	private void documentoEditar(HttpServletRequest request,
			HttpSession session, Login usuVO, IdentificacionVO identificacion,
			DocumentoVO documento) throws ServletException {
		try {
			if (documento == null) {
				request.setAttribute(ParamsVO.SMS,
						"Error editando documento: No hay documento seleccionado");
				return;
				
			}
			session.setAttribute(ParamsVO.BEAN_DOCUMENTO,
					registroDAO.getDocumento(documento));
			Logger.print(usuVO.getUsuarioId(), "Consulta de PEI, m骴ulo de Documento. ",
					7, 1, this.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Error interno: " + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param desarrolloCurricular
	 * @param otroProyecto
	 * @throws ServletException
	 */
	private void documentoEliminar(HttpServletRequest request,
			HttpSession session, Login usuVO, IdentificacionVO identificacion,
			DocumentoVO documento) throws ServletException {
		try {
			if (documento == null) {
				request.setAttribute(ParamsVO.SMS,
						"Error eliminando documento: No hay documento seleccionado");
				return;
			}
			documento = registroDAO.eliminarDocumento(documento);
			RegistroIO.borrarDocumento(context.getRealPath("/"),
					documento.getDocRuta());
			Logger.print(usuVO.getUsuarioId(),
					"PEI: Eliminar Documento. " + documento.getDocInstitucion()
							+ " " + documento.getDocCodigo(), 7, 1,
					this.toString());
			session.removeAttribute(ParamsVO.BEAN_DOCUMENTO);
			request.setAttribute(ParamsVO.SMS,
					"La informaci贸n ha sigo eliminada satisfactoriamente");
			documentoNuevo(request, session, usuVO, identificacion, null);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error eliminando documento: "
					+ e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param desarrolloCurricular
	 * @param documento
	 * @throws ServletException
	 */
	private void documentoGuardar(HttpServletRequest request,
			HttpSession session, Login usuVO, IdentificacionVO identificacion,
			DocumentoVO documento) throws ServletException {
		try {
			if (documento == null)
				return;
			if (!documento.isEdicion()) {
				documento = registroDAO.ingresarDocumento(documento);
				Logger.print(
						usuVO.getUsuarioId(),
						"PEI: Registro Documento. "
								+ documento.getDocInstitucion() + " "
								+ documento.getDocCodigo(), 7, 1,
						this.toString());
			} else {
				documento = registroDAO.actualizarDocumento(documento);
				Logger.print(
						usuVO.getUsuarioId(),
						"PEI: Actualizacion Documento. "
								+ documento.getDocInstitucion() + " "
								+ documento.getDocCodigo(), 7, 1,
						this.toString());
			}
			session.removeAttribute(ParamsVO.BEAN_DOCUMENTO);
			request.setAttribute(ParamsVO.SMS,
					"La informaci贸n ha sigo ingresada satisfactoriamente");
			documentoNuevo(request, session, usuVO, identificacion, null);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error ingresando documento: "
					+ e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param desarrolloCurricular
	 * @param documento
	 * @throws ServletException
	 */
	private void documentoNuevo(HttpServletRequest request,
			HttpSession session, Login usuVO, IdentificacionVO identificacion,
			DocumentoVO documento) throws ServletException {
		if (documento == null || documento.getDocUsuario() <= 0) {
			documento = new DocumentoVO();
			if (!usuVO.getInstId().equals(""))
				documento.setDocInstitucion(Long.parseLong(usuVO.getInstId()));
			documento.setDocUsuario(Long.parseLong(usuVO.getUsuarioId()));
			documento.setDocUsuarioNombre(usuVO.getUsuario());
			documento.setDocFecha(getFechaActual());
			session.setAttribute(ParamsVO.BEAN_DOCUMENTO, documento);
		}
	}

	private String getFechaActual() {
		String fecha = null;
		Calendar c = Calendar.getInstance();
		fecha = c.get(Calendar.DAY_OF_MONTH) > 9 ? String.valueOf(c
				.get(Calendar.DAY_OF_MONTH)) : ("0" + c
				.get(Calendar.DAY_OF_MONTH));
		fecha += "/";
		fecha += (c.get(Calendar.MONTH) + 1) > 9 ? String.valueOf(c
				.get(Calendar.MONTH) + 1) : ("0" + (c.get(Calendar.MONTH) + 1));
		fecha += "/";
		fecha += String.valueOf(c.get(Calendar.YEAR));
		return fecha;
	}

	public String processMultipart(HttpServletRequest request,
			HttpSession session, Login usuVO) throws ServletException,
			IOException {
		int CMD;
		int TIPO;
		Iterator i;
		List fileItems;
		FileItem item = null;
		CMD = ParamsVO.CMD_NUEVO;
		TIPO = ParamsVO.FICHA_DEFAULT;
		String FICHA = null;
		try {
			// System.out.println("Multipart");
			IdentificacionVO identificacion = (IdentificacionVO) session
					.getAttribute(ParamsVO.BEAN_IDENTIFICACION);
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			fileItems = upload.parseRequest(request);
			i = fileItems.iterator();
			while (i.hasNext()) {
				item = (FileItem) i.next();
				if (item.isFormField()) {
					if (item.getFieldName().equals("cmd")) {
						CMD = Integer.parseInt(item.getString());
					}
					if (item.getFieldName().equals("tipo")) {
						TIPO = Integer.parseInt(item.getString());
					}
				}
			}
			switch (TIPO) {
			case ParamsVO.FICHA_DOCUMENTO:
				FICHA = FICHA_DOCUMENTO;
				switch (CMD) {
				case ParamsVO.CMD_GUARDAR:
					i = fileItems.iterator();
					validarDocumento(request, session, usuVO, i, identificacion);
					break;
				}
				break;
			}
		} catch (FileUploadBase.InvalidContentTypeException e) {
			e.printStackTrace();
			System.out.println("Multipart" + ": error de contenido "
					+ e.getMessage());
			return FICHA;
		} catch (Exception e) {
			System.out.println("Multipart" + ": error interno "
					+ e.getMessage());
			e.printStackTrace();
			return FICHA;
		}
		return FICHA;
	}

	public void validarDocumento(HttpServletRequest request,
			HttpSession session, Login usuVO, Iterator i,
			IdentificacionVO identificacion) throws Exception {
		FileItem item = null;
		RegistroIO io = new RegistroIO();
		DocumentoVO vo = new DocumentoVO();
		try {
			vo.setDocCodigo(registroDAO.getDocumentoId(identificacion
					.getIdenInstitucion()));
			while (i.hasNext()) {
				item = (FileItem) i.next();
				if (!item.isFormField()) {
					if (item.getFieldName().equals("docArchivo")) {
						if (!validarTipo(item.getContentType())) {
							request.setAttribute(ParamsVO.SMS,
									"El archivo no corresponde a los tipos de contenido permitidos");
							return;
						}
						vo.setDocRuta(io.subirDocumento(item, context
								.getRealPath("/"), String
								.valueOf(identificacion.getIdenInstitucion()),
								vo.getDocCodigo()));
					}
				} else {
					if (item.getFieldName().equals("docNombre")) {
						vo.setDocNombre(item.getString());
					}
					if (item.getFieldName().equals("docDescripcion")) {
						vo.setDocDescripcion(item.getString());
					}
					if (item.getFieldName().equals("docFecha")) {
						vo.setDocFecha(item.getString());
					}
					if (item.getFieldName().equals("docInstitucion")) {
						vo.setDocInstitucion(Long.parseLong(item.getString()));
					}
					if (item.getFieldName().equals("docUsuario")) {
						vo.setDocUsuario(Long.parseLong(item.getString()));
					}
				}
			}
			documentoGuardar(request, session, usuVO, identificacion, vo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("El documento no fue importado : "
					+ e.getMessage());
		} finally {
			documentoBuscar(request, session, usuVO, identificacion, vo);
		}
	}

	public boolean validarTipo(String a) {
		boolean aceptado = false;
		for (int n = 0; n < tipos.length; n++) {
			if (a.equals((String) tipos[n])) {
				aceptado = true;
				break;
			}
		}
		return aceptado;
	}

	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param identificacion
	 * @throws ServletException
	 */
	private ResultadoConsultaVO identificacionGenerarReporte(
			IdentificacionVO identificacionVO, Login usuVO)
			throws ServletException {

		ResultadoConsultaVO resultado = null;
		try {
			String rutaBase = context.getRealPath("/");
			String rutaReal = context.getRealPath("/");

			// 1. Generar reporte
			RegistroIO registroIO = new RegistroIO(registroDAO);
			resultado = registroIO.generarReportePEI(identificacionVO,
					rutaBase, rutaReal, usuVO);

			return resultado;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error finally " + e.getMessage());
		}

		return null;
	}

	/**
	 * @param request
	 * @param response
	 * @param usuVO
	 * @param resultado
	 * @throws ServletException
	 */
	public void enviarArchivo(HttpServletRequest request,
			HttpServletResponse response, Login usuVO,
			ResultadoConsultaVO resultado) throws ServletException {
		// System.out.println("enviarArchivo");
		response.setContentType("application/zip");
		BufferedInputStream origin = null;
		ServletOutputStream ouputStream = null;
		FileInputStream fi = null;
		int BUFFER_SIZE = 1000000;
		int count;
		File f = null;
		try {

			if (resultado.isGenerado()) {
				StringBuffer contentDisposition = new StringBuffer();
				contentDisposition.append("attachment;");
				contentDisposition.append("filename=\"");
				contentDisposition.append(resultado.getArchivo());
				contentDisposition.append("\"");
				response.setHeader("Content-Disposition",
						contentDisposition.toString());
				f = new File(resultado.getRutaDisco());
				if (!f.exists()) {
					return;
				}
				// System.out.println("bien 1");
				byte[] data = new byte[BUFFER_SIZE];
				fi = new FileInputStream(f);
				origin = new BufferedInputStream(fi, BUFFER_SIZE);
				ouputStream = response.getOutputStream();
				while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
					// System.out.println("bien 12*");
					ouputStream.write(data, 0, count);
				}
				ouputStream.flush();
				ouputStream.close();
				origin.close();
				fi.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error " + e.getMessage());
			throw new ServletException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error " + e.getMessage());
			throw new ServletException(e.getMessage());
		} finally {
			try {
				if (ouputStream != null)
					ouputStream.close();
				if (origin != null)
					origin.close();
				if (fi != null)
					fi.close();

				try {
					f = new File(resultado.getRutaDisco());
					if (f.exists()) {

						// f.delete();
						System.out.println("Borro sin problema "
								+ resultado.getRutaDisco());
					}
				} catch (Exception e) {
					// TODO: handle exception

					e.printStackTrace();
					System.out.println("Error " + e.getMessage());
					throw new ServletException(e.getMessage());
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error finally " + e.getMessage());
			}
		}
	}
}
