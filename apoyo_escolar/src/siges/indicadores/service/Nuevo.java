/**
 * 
 */
package siges.indicadores.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.python.core.exceptions;

import com.google.gson.Gson;

import articulacion.asigGrupo.dao.AsignacionGrupoDAO;
import articulacion.asignatura.dao.AsignaturaDAO;
import siges.common.service.Service;
import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.indicadores.dao.IndicadoresDAO;
import siges.indicadores.vo.DescriptorVO;
import siges.indicadores.vo.FiltroDescriptorVO;
import siges.indicadores.vo.FiltroLogroVO;
import siges.indicadores.vo.LogroVO;
import siges.indicadores.vo.ParamsVO;
import siges.login.beans.Login;
import siges.rotacion.beans.forma.GradoVO;
import siges.util.Acceso;
import util.BitacoraCOM;
import util.LogDescriptorDto;
import util.LogLogroDto;

/**
 * 22/01/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class Nuevo extends Service {
	public String FICHA_LOGRO;
	public String FICHA_DESCRIPTOR;
	private IndicadoresDAO indicadoresDAO = new IndicadoresDAO(new Cursor());

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		FICHA_LOGRO = config.getInitParameter("FICHA_LOGRO");
		FICHA_DESCRIPTOR = config.getInitParameter("FICHA_DESCRIPTOR");
	}

	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String FICHA = null;
		boolean clean = false;
		HttpSession session = request.getSession();
		String dispatcher[] = new String[2];
		int CMD = getCmd(request, session, ParamsVO.CMD_NUEVO);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		Login usuVO = (Login) session.getAttribute("login");
		LogroVO logro = (LogroVO) session.getAttribute("indicadorLogroVO");
		DescriptorVO descriptor = (DescriptorVO) session
				.getAttribute("indicadorDescriptorVO");
		FiltroLogroVO filtroLogro = (FiltroLogroVO) session
				.getAttribute("filtroLogroVO");
		FiltroDescriptorVO filtroDescriptor = (FiltroDescriptorVO) session
				.getAttribute("filtroDescriptorVO");
		request.getSession().setAttribute(
				"logroanddesc",
				Acceso.getlogrosdesc(usuVO.getInstId(),
						Long.toString(usuVO.getVigencia_inst())));
		switch (TIPO) {
		case ParamsVO.FICHA_LOGRO:
			FICHA = FICHA_LOGRO;
			switch (CMD) {
			case ParamsVO.CMD_NUEVO:
				logroNuevo(request, session, usuVO, logro);
				logroBuscar(request, session, usuVO, filtroLogro);
				clean = true;
				break;
			case ParamsVO.CMD_BUSCAR:
				logroBuscar(request, session, usuVO, filtroLogro);
				clean = true;
				break;
			case ParamsVO.CMD_EDITAR:
				logro = logroEditar(request, session, usuVO, filtroLogro);
				logroBuscar(request, session, usuVO, filtroLogro);
				break;
			case ParamsVO.CMD_ELIMINAR:
				logroEliminar(request, session, usuVO, filtroLogro);
				logroBuscar(request, session, usuVO, filtroLogro);
				clean = true;
				break;
			case ParamsVO.CMD_GUARDAR:
				logroGuardar(request, session, usuVO, logro);
				logroBuscar(request, session, usuVO, filtroLogro);
				request.setAttribute("metodologiaRef",
						Integer.toString(logro.getLogMetodologia()));
				request.setAttribute("gradoRef",
						Integer.toString(logro.getLogGrado()));
				request.setAttribute("asignaturaRef",
						Long.toString(logro.getLogAsignatura()));
				clean = true;
				break;
			}
			logroInit(request, session, usuVO, logro, clean);
			break;
		case ParamsVO.FICHA_DESCRIPTOR:
			FICHA = FICHA_DESCRIPTOR;
			switch (CMD) {
			case ParamsVO.CMD_NUEVO:
				descriptorNuevo(request, session, usuVO, descriptor);
				descriptorBuscar(request, session, usuVO, filtroDescriptor);
				clean = true;
				break;
			case ParamsVO.CMD_BUSCAR:
				descriptorBuscar(request, session, usuVO, filtroDescriptor);
				clean = true;
				break;
			case ParamsVO.CMD_EDITAR:
				descriptor = descriptorEditar(request, session, usuVO,
						filtroDescriptor);
				descriptorBuscar(request, session, usuVO, filtroDescriptor);
				break;
			case ParamsVO.CMD_ELIMINAR:
				descriptorEliminar(request, session, usuVO, filtroDescriptor);
				descriptorBuscar(request, session, usuVO, filtroDescriptor);
				clean = true;
				break;
			case ParamsVO.CMD_GUARDAR:
				descriptorGuardar(request, session, usuVO, descriptor);
				descriptorBuscar(request, session, usuVO, filtroDescriptor);
				request.setAttribute("metodologiaRefdes",
						Integer.toString(descriptor.getDesMetodologia()));
				request.setAttribute("gradoRefdes",
						Integer.toString(descriptor.getDesGrado()));
				request.setAttribute("areaRefdes",
						Long.toString(descriptor.getDesArea()));
				request.setAttribute("tipoRefdes",
						Integer.toString(descriptor.getDesTipo()));
				clean = true;
				break;
			}
			descriptorInit(request, session, usuVO, descriptor, clean);
			break;
		}
		dispatcher[0] = String.valueOf(ParamsVO.INCLUDE);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	public void logroInit(HttpServletRequest request, HttpSession session,
			Login usuVO, LogroVO logro, boolean clean) throws ServletException {
		try {
			if (logro == null || clean) {
				System.out.println("INIT LOGRO");
				logro = new LogroVO();
				logro.setLogInstitucion(Long.parseLong(usuVO.getInstId()));
				logro.setLogVigencia((int) indicadoresDAO.getVigenciaInst(Long
						.parseLong(usuVO.getInstId())));
				logro.setLogPlanEstudios(indicadoresDAO.getEstadoPlan(Long
						.parseLong(usuVO.getInstId())));
				session.setAttribute("indicadorLogroVO", logro);
			}
			request.setAttribute("listaMetodologia", indicadoresDAO
					.getListaMetodologia(logro.getLogInstitucion()));
			// request.setAttribute("listaPeriodo",
			// indicadoresDAO.getListaPeriodo());
			request.setAttribute("listaPeriodo",
					indicadoresDAO.getListaPeriodo(usuVO.getLogNumPer()));
			if (logro.getLogMetodologia() > 0) {
				request.setAttribute("listaGrado", indicadoresDAO
						.getListaGrado(logro.getLogInstitucion(),
								logro.getLogMetodologia()));
				if (logro.getLogGrado() > -99) {
					request.setAttribute(
							"listaAsignatura",
							indicadoresDAO.getListaAsignatura(
									logro.getLogInstitucion(),
									logro.getLogMetodologia(),
									logro.getLogVigencia(), logro.getLogGrado()));
					if (logro.getLogPlanEstudios() == 1) {
						request.setAttribute(
								"listaDocente",
								indicadoresDAO.getListaDocenteAsignatura(
										logro.getLogInstitucion(),
										logro.getLogMetodologia(),
										logro.getLogVigencia(),
										logro.getLogGrado(),
										logro.getLogAsignatura()));
					}
				}
			}
		} catch (Exception e) {
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void descriptorInit(HttpServletRequest request, HttpSession session,
			Login usuVO, DescriptorVO descriptor, boolean clean)
			throws ServletException {
		try {
			if (descriptor == null || clean) {
				System.out.println("INIT DESC");
				descriptor = new DescriptorVO();
				descriptor.setDesInstitucion(Long.parseLong(usuVO.getInstId()));
				descriptor.setDesVigencia((int) indicadoresDAO
						.getVigenciaInst(Long.parseLong(usuVO.getInstId())));
				descriptor.setDesPlanEstudios(indicadoresDAO.getEstadoPlan(Long
						.parseLong(usuVO.getInstId())));
				session.setAttribute("indicadorDescriptorVO", descriptor);
			}
			// request.setAttribute("listaPeriodo",
			// indicadoresDAO.getListaPeriodo());
			request.setAttribute("listaPeriodo",
					indicadoresDAO.getListaPeriodo(usuVO.getLogNumPer()));
			request.setAttribute("listaTipo",
					indicadoresDAO.getListaTipoDescriptor());
			request.setAttribute("listaMetodologia", indicadoresDAO
					.getListaMetodologia(descriptor.getDesInstitucion()));
			if (descriptor.getDesMetodologia() > 0) {
				request.setAttribute(
						"listaGrado",
						indicadoresDAO.getListaGrado(
								descriptor.getDesInstitucion(),
								descriptor.getDesMetodologia()));
				if (descriptor.getDesGrado() > -99) {
					request.setAttribute(
							"listaArea",
							indicadoresDAO.getListaArea(
									descriptor.getDesInstitucion(),
									descriptor.getDesMetodologia(),
									descriptor.getDesVigencia(),
									descriptor.getDesGrado()));
					if (descriptor.getDesPlanEstudios() == 1) {
						request.setAttribute("listaDocente", indicadoresDAO
								.getListaDocenteArea(
										descriptor.getDesInstitucion(),
										descriptor.getDesMetodologia(),
										descriptor.getDesVigencia(),
										descriptor.getDesGrado(),
										descriptor.getDesArea()));
					}
				}
			}
		} catch (Exception e) {
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void logroNuevo(HttpServletRequest request, HttpSession session,
			Login usuVO, LogroVO logro) throws ServletException {
		session.removeAttribute("indicadorLogroVO");
		request.setAttribute("logroNuevo", "1");
	}

	public void descriptorNuevo(HttpServletRequest request,
			HttpSession session, Login usuVO, DescriptorVO desc)
			throws ServletException {
		session.removeAttribute("indicadorDescriptorVO");
		request.setAttribute("descriptorNuevo", "1");
	}

	public void logroBuscar(HttpServletRequest request, HttpSession session,
			Login usuVO, FiltroLogroVO filtro) throws ServletException {
		try {
			request.setAttribute("listaLogro",
					indicadoresDAO.getListaLogro(filtro));
		} catch (Exception e) {
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void descriptorBuscar(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroDescriptorVO filtro)
			throws ServletException {
		try {
			request.setAttribute("listaDescriptor",
					indicadoresDAO.getListaDescriptor(filtro));
		} catch (Exception e) {
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public LogroVO logroEditar(HttpServletRequest request, HttpSession session,
			Login usuVO, FiltroLogroVO filtro) throws ServletException {
		LogroVO logro = null;
		try {
			String params[] = request.getParameterValues("id");
			if (params == null || params.length < 3 || params[0] == null
					|| params[1] == null || params[2] == null)
				throw new ServletException("datos insuficientes");
			logro = new LogroVO();
			logro.setLogCodigoJerarquia(Long.parseLong(params[0]));
			logro.setLogCodigo(Long.parseLong(params[1]));
			logro.setLogVigencia(Integer.parseInt(params[2]));
			logro = indicadoresDAO.getLogro(logro);
			session.setAttribute("indicadorLogroVO", logro);
		} catch (Exception e) {
			request.setAttribute(ParamsVO.SMS,
					"El logro no pudo ser obtenido: " + e.getMessage());
		}
		return logro;
	}

	public DescriptorVO descriptorEditar(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroDescriptorVO filtro)
			throws ServletException {
		DescriptorVO desc = null;
		try {
			String params[] = request.getParameterValues("id");
			if (params == null || params.length < 3 || params[0] == null
					|| params[1] == null || params[2] == null)
				throw new ServletException("datos insuficientes");
			desc = new DescriptorVO();
			desc.setDesCodigoJerarquia(Long.parseLong(params[0]));
			desc.setDesCodigo(Long.parseLong(params[1]));
			desc.setDesVigencia(Integer.parseInt(params[2]));
			desc = indicadoresDAO.getDescriptor(desc);
			session.setAttribute("indicadorDescriptorVO", desc);
		} catch (Exception e) {
			request.setAttribute(ParamsVO.SMS,
					"El descriptor no pudo ser obtenido: " + e.getMessage());
		}
		return desc;
	}

	public void logroEliminar(HttpServletRequest request, HttpSession session,
			Login usuVO, FiltroLogroVO filtro) throws ServletException {
		try {
			String params[] = request.getParameterValues("id");
			if (params == null || params.length < 3 || params[0] == null
					|| params[1] == null || params[2] == null)
				throw new ServletException("datos insuficientes");
			LogroVO logro = new LogroVO();
			logro.setLogCodigoJerarquia(Long.parseLong(params[0]));
			logro.setLogCodigo(Long.parseLong(params[1]));
			logro.setLogVigencia(Integer.parseInt(params[2]));
			indicadoresDAO.eliminarLogro(logro);
			//insercion de bitacora
			BitacoraCOM com = new BitacoraCOM();
			String jsonString="";
			try{
				
				
				LogLogroDto log = new LogLogroDto();
				log.setAbreviatura(logro.getLogAbreviatura());
				
				List<ItemVO> asignaturas = indicadoresDAO.getListaAsignatura(
						logro.getLogInstitucion(),
						logro.getLogMetodologia(),
						logro.getLogVigencia(), logro.getLogGrado());
				
				for(int i=0;i<asignaturas.size();i++){
					ItemVO obj = asignaturas.get(i);
					if(obj.getCodigo()==logro.getLogMetodologia()){
						log.setAsignatura(obj.getNombre());
						break;
					}
				}

				log.setAsignatura(logro.getLogAbreviatura());
				log.setComentario(logro.getLogDescripcion());
				log.setGrado(logro.getLogGrado());
				log.setIdentificadorRegistro(String.valueOf(logro.getLogCodigo()));
				log.setLogro(String.valueOf(logro.getLogCodigo()));
				
				List<ItemVO> metodologias = indicadoresDAO
						.getListaMetodologia(logro.getLogInstitucion());

				for(int i=0;i<metodologias.size();i++){
					ItemVO obj = metodologias.get(i);
					if(obj.getCodigo()==logro.getLogMetodologia()){
						log.setMetodología(obj.getNombre());
						break;
					}
				}
				
				
				log.setOrden(logro.getLogOrden());
				log.setPeriodoFinal(logro.getLogPeriodoFin());
				log.setPeriodoInicial(logro.getLogPeriodoIni());
				log.setTipoCargue("individual");
				log.setVigencia(logro.getLogPeriodoIni()+" - "+logro.getLogPeriodoFin());
				
				Gson gson = new Gson();
				jsonString = gson.toJson(log);
				com.insertarBitacora(logro.getLogInstitucion(), Integer.parseInt(usuVO.getJornadaId()), 3, usuVO.getPerfil(), Integer.parseInt(usuVO.getSedeId()), 
						1301, 3/*eliminacion*/, usuVO.getUsuarioId(), jsonString);
			}catch(Exception e){
				
			}
			session.removeAttribute("indicadorLogroVO");
			request.setAttribute(ParamsVO.SMS,
					"El logro fue eliminado satisfactoriamente");
		} catch (Exception e) {
			request.setAttribute(ParamsVO.SMS, "El logro no fue eliminado: "
					+ e.getMessage());
		}
	}

	public void descriptorEliminar(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroDescriptorVO filtro)
			throws ServletException {
		try {
			String params[] = request.getParameterValues("id");
			if (params == null || params.length < 3 || params[0] == null
					|| params[1] == null || params[2] == null)
				throw new ServletException("datos insuficientes");
			DescriptorVO desc = new DescriptorVO();
			desc.setDesCodigoJerarquia(Long.parseLong(params[0]));
			desc.setDesCodigo(Long.parseLong(params[1]));
			desc.setDesVigencia(Integer.parseInt(params[2]));
			indicadoresDAO.eliminarDescriptor(desc);
			//insercion de bitacora
			String jsonString="";
			BitacoraCOM com = new BitacoraCOM();
			try{
				
				
				LogDescriptorDto log = new LogDescriptorDto();
				log.setAbreviatura(desc.getDesAbreviatura());
				
				List<ItemVO> areas = indicadoresDAO.getListaArea(desc.getDesInstitucion(), desc.getDesMetodologia(), desc.getDesMetodologia(), desc.getDesGrado());
				
				for(int i=0;i<areas.size();i++){
					ItemVO obj = areas.get(i);
					if(obj.getCodigo()==desc.getDesArea()){
						log.setArea(obj.getNombre());
						break;
					}
				}

				log.setComentario(desc.getDesDescripcion());
				List<ItemVO> docentes = indicadoresDAO.getListaDocenteArea(desc.getDesInstitucion(), desc.getDesMetodologia(), desc.getDesMetodologia(), desc.getDesGrado(), desc.getDesArea());
				
				for(int i=0;i<docentes.size();i++){
					ItemVO obj = docentes.get(i);
					if(obj.getCodigo()==desc.getDesArea()){
						log.setDocente(obj.getNombre());
						break;
					}
				}
				log.setGrado(desc.getDesGrado());
				log.setIdentificadorRegistro(String.valueOf(desc.getDesCodigo()));
				
				List<ItemVO> metodologias = indicadoresDAO
						.getListaMetodologia(desc.getDesInstitucion());

				for(int i=0;i<metodologias.size();i++){
					ItemVO obj = metodologias.get(i);
					if(obj.getCodigo()==desc.getDesMetodologia()){
						log.setMetodología(obj.getNombre());
						break;
					}
				}
				
				log.setOrden(desc.getDesOrden());
				log.setPeriodoFinal(desc.getDesPeriodoFin());
				log.setPeriodoInicial(desc.getDesPeriodoIni());
				List<ItemVO> tDescriptor = indicadoresDAO.getListaTipoDescriptor();
				
				for(int i=0;i<tDescriptor.size();i++){
					ItemVO obj = docentes.get(i);
					if(obj.getCodigo()==desc.getDesArea()){
						log.setTipoDescriptor(obj.getNombre());
						break;
					}
				}
				log.setVigencia(desc.getDesPeriodoIni()+" - "+desc.getDesPeriodoFin());
				
				Gson gson = new Gson();
				jsonString = gson.toJson(log);
				com.insertarBitacora(desc.getDesInstitucion(), Integer.parseInt(usuVO.getJornadaId()), 3, usuVO.getPerfil(), Integer.parseInt(usuVO.getSedeId()), 
						1301, 3/*eliminacion*/, usuVO.getUsuarioId(), jsonString);
			}catch(Exception e){
				
			}
			
			session.removeAttribute("indicadorDescriptorVO");
			request.setAttribute(ParamsVO.SMS,
					"El descriptor fue eliminado satisfactoriamente");
		} catch (Exception e) {
			request.setAttribute(ParamsVO.SMS,
					"El descriptor no fue eliminado: " + e.getMessage());
		}
	}

	public void logroGuardar(HttpServletRequest request, HttpSession session,
			Login usuVO, LogroVO logro) throws ServletException {
		try {
			request.setAttribute("listaGrado", indicadoresDAO.getListaGrado(
					logro.getLogInstitucion(), logro.getLogMetodologia()));
			request.setAttribute("listaAsignatura", indicadoresDAO
					.getListaAsignatura(logro.getLogInstitucion(),
							logro.getLogMetodologia(), logro.getLogVigencia(),
							logro.getLogGrado()));
			
			//insercion de bitacora
			BitacoraCOM com = new BitacoraCOM();
			String jsonString="";
			try{
				
				
				LogLogroDto log = new LogLogroDto();
				log.setAbreviatura(logro.getLogAbreviatura());
				
				List<ItemVO> asignaturas = indicadoresDAO.getListaAsignatura(
						logro.getLogInstitucion(),
						logro.getLogMetodologia(),
						logro.getLogVigencia(), logro.getLogGrado());
				
				for(int i=0;i<asignaturas.size();i++){
					ItemVO obj = asignaturas.get(i);
					if(obj.getCodigo()==logro.getLogMetodologia()){
						log.setAsignatura(obj.getNombre());
						break;
					}
				}

				log.setAsignatura(logro.getLogAbreviatura());
				log.setComentario(logro.getLogDescripcion());
				log.setGrado(logro.getLogGrado());
				log.setIdentificadorRegistro(String.valueOf(logro.getLogCodigo()));
				log.setLogro(String.valueOf(logro.getLogCodigo()));
				
				List<ItemVO> metodologias = indicadoresDAO
						.getListaMetodologia(logro.getLogInstitucion());

				for(int i=0;i<metodologias.size();i++){
					ItemVO obj = metodologias.get(i);
					if(obj.getCodigo()==logro.getLogMetodologia()){
						log.setMetodología(obj.getNombre());
						break;
					}
				}
				
				
				log.setOrden(logro.getLogOrden());
				log.setPeriodoFinal(logro.getLogPeriodoFin());
				log.setPeriodoInicial(logro.getLogPeriodoIni());
				log.setTipoCargue("individual");
				log.setVigencia(logro.getLogPeriodoIni()+" - "+logro.getLogPeriodoFin());
				
				Gson gson = new Gson();
				jsonString = gson.toJson(log);
			}catch(Exception e){
				
			}
			
			
			if (logro.getFormaEstado().equals("1")) {
				indicadoresDAO.actualizarLogro(logro);
				try{
					com.insertarBitacora(logro.getLogInstitucion(), Integer.parseInt(usuVO.getJornadaId()), 3, usuVO.getPerfil(), Integer.parseInt(usuVO.getSedeId()), 
							1301, 2/*modificacion*/, usuVO.getUsuarioId(), jsonString);
				}catch (Exception e) {
					// TODO: handle exception
				}
			} else {
				indicadoresDAO.ingresarLogro(logro);
				try{
					com.insertarBitacora(logro.getLogInstitucion(), Integer.parseInt(usuVO.getJornadaId()), 3, usuVO.getPerfil(), Integer.parseInt(usuVO.getSedeId()), 
							1301, 1/*registro*/, usuVO.getUsuarioId(), jsonString);
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
			session.removeAttribute("indicadorLogroVO");
			request.setAttribute(ParamsVO.SMS,
					"El logro fue ingresado/actualizado satisfactoriamente");	
			
		} catch (Exception e) {
			request.setAttribute(ParamsVO.SMS,
					"El logro no fue ingresado/actualizado: " + e.getMessage());
			request.setAttribute("perinirefdes",
					Integer.toString(logro.getLogPeriodoIni()));
			request.setAttribute("perfinrefdes",
					Integer.toString(logro.getLogPeriodoFin()));
			request.setAttribute("logrorefdes", logro.getLogNombre());
			request.setAttribute("abrerefdes", logro.getLogAbreviatura());
			request.setAttribute("comrefdes", logro.getLogDescripcion());
			request.setAttribute("ordrefdes",
					Integer.toString(logro.getLogOrden()));
		}
	}

	public void descriptorGuardar(HttpServletRequest request,
			HttpSession session, Login usuVO, DescriptorVO desc)
			throws ServletException {
		try {
			request.setAttribute(
					"listaGrado",
					indicadoresDAO.getListaGrado(desc.getDesInstitucion(),
							desc.getDesMetodologia()));
			request.setAttribute("listaArea", indicadoresDAO.getListaArea(
					desc.getDesInstitucion(), desc.getDesMetodologia(),
					desc.getDesVigencia(), desc.getDesGrado()));
			
			//insercion de bitacora
			String jsonString="";
			BitacoraCOM com = new BitacoraCOM();
			try{
				
				
				LogDescriptorDto log = new LogDescriptorDto();
				log.setAbreviatura(desc.getDesAbreviatura());
				
				List<ItemVO> areas = indicadoresDAO.getListaArea(desc.getDesInstitucion(), desc.getDesMetodologia(), desc.getDesMetodologia(), desc.getDesGrado());
				
				for(int i=0;i<areas.size();i++){
					ItemVO obj = areas.get(i);
					if(obj.getCodigo()==desc.getDesArea()){
						log.setArea(obj.getNombre());
						break;
					}
				}

				log.setComentario(desc.getDesDescripcion());
				List<ItemVO> docentes = indicadoresDAO.getListaDocenteArea(desc.getDesInstitucion(), desc.getDesMetodologia(), desc.getDesMetodologia(), desc.getDesGrado(), desc.getDesArea());
				
				for(int i=0;i<docentes.size();i++){
					ItemVO obj = docentes.get(i);
					if(obj.getCodigo()==desc.getDesArea()){
						log.setDocente(obj.getNombre());
						break;
					}
				}
				log.setGrado(desc.getDesGrado());
				log.setIdentificadorRegistro(String.valueOf(desc.getDesCodigo()));
				
				List<ItemVO> metodologias = indicadoresDAO
						.getListaMetodologia(desc.getDesInstitucion());

				for(int i=0;i<metodologias.size();i++){
					ItemVO obj = metodologias.get(i);
					if(obj.getCodigo()==desc.getDesMetodologia()){
						log.setMetodología(obj.getNombre());
						break;
					}
				}
				
				log.setOrden(desc.getDesOrden());
				log.setPeriodoFinal(desc.getDesPeriodoFin());
				log.setPeriodoInicial(desc.getDesPeriodoIni());
				List<ItemVO> tDescriptor = indicadoresDAO.getListaTipoDescriptor();
				
				for(int i=0;i<tDescriptor.size();i++){
					ItemVO obj = docentes.get(i);
					if(obj.getCodigo()==desc.getDesArea()){
						log.setTipoDescriptor(obj.getNombre());
						break;
					}
				}
				log.setVigencia(desc.getDesPeriodoIni()+" - "+desc.getDesPeriodoFin());
				
				Gson gson = new Gson();
				jsonString = gson.toJson(log);
			}catch(Exception e){
				
			}
			
			if (desc.getFormaEstado().equals("1")) {
				indicadoresDAO.actualizarDescriptor(desc);
				try{
					com.insertarBitacora(desc.getDesInstitucion(), Integer.parseInt(usuVO.getJornadaId()), 3, usuVO.getPerfil(), Integer.parseInt(usuVO.getSedeId()), 
							1301, 2/*modificacion*/, usuVO.getUsuarioId(), jsonString);
				}catch (Exception e) {
					// TODO: handle exception
				}
			} else {
				indicadoresDAO.ingresarDescriptor(desc);
				try{
					com.insertarBitacora(desc.getDesInstitucion(), Integer.parseInt(usuVO.getJornadaId()), 3, usuVO.getPerfil(), Integer.parseInt(usuVO.getSedeId()), 
							1301, 1/*insercion*/, usuVO.getUsuarioId(), jsonString);
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
			session.removeAttribute("indicadorDescriptorVO");
			request.setAttribute(ParamsVO.SMS,
					"El descriptor fue ingresado/actualizado satisfactoriamente");
		} catch (Exception e) {
			request.setAttribute(
					ParamsVO.SMS,
					"El descriptor no fue ingresado/actualizado: "
							+ e.getMessage());
			request.setAttribute("periniref",
					Integer.toString(desc.getDesPeriodoIni()));
			request.setAttribute("perfinref",
					Integer.toString(desc.getDesPeriodoFin()));
			request.setAttribute("logroref", desc.getDesNombre());
			request.setAttribute("abreref", desc.getDesAbreviatura());
			request.setAttribute("comref", desc.getDesDescripcion());
			request.setAttribute("ordref", Integer.toString(desc.getDesOrden()));
		}
	}
}
