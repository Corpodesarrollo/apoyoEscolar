/**
 * 
 */
package siges.gestionAcademica.planDeEstudios.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import siges.adminParamsInst.dao.AdminParametroInstDAO;
import siges.adminParamsInst.vo.InstParVO;
import siges.common.service.Service;
import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.gestionAcademica.planDeEstudios.dao.PlanDeEstudiosDAO;
import siges.gestionAcademica.planDeEstudios.vo.AreaVO;
import siges.gestionAcademica.planDeEstudios.vo.AsignaturaVO;
import siges.gestionAcademica.planDeEstudios.vo.FiltroAreaVO;
import siges.gestionAcademica.planDeEstudios.vo.FiltroAsignaturaVO;
import siges.gestionAcademica.planDeEstudios.vo.FiltroPlanDeEstudiosVO;
import siges.gestionAcademica.planDeEstudios.vo.ParamsVO;
import siges.gestionAcademica.planDeEstudios.vo.PlanDeEstudiosVO;
import siges.login.beans.Login;
import util.BitacoraCOM;
import util.LogAreaDto;
import util.LogAsignaturaDto;
import util.LogDescriptorDto;
import util.LogGradoDto;
import util.LogPlanEstudioDto;

/**
 * 27/10/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class Nuevo extends Service {
	public String FICHA_PLAN;
	public String FICHA_AREA;
	public String FICHA_ASIGNATURA;
	private PlanDeEstudiosDAO planDeEstudiosDAO = new PlanDeEstudiosDAO(
			new Cursor());

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		FICHA_PLAN = config.getInitParameter("FICHA_PLAN");
		FICHA_AREA = config.getInitParameter("FICHA_AREA");
		FICHA_ASIGNATURA = config.getInitParameter("FICHA_ASIGNATURA");
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
		System.out.println(this.toString() + ": " + TIPO + "/" + CMD);
		switch (TIPO) {
		case ParamsVO.FICHA_PLAN:
			FICHA = FICHA_PLAN;
			FiltroPlanDeEstudiosVO filtroPlan = (FiltroPlanDeEstudiosVO) session
					.getAttribute("filtroPlanDeEstudiosVO");
			PlanDeEstudiosVO plan = (PlanDeEstudiosVO) session
					.getAttribute("planDeEstudiosVO");
			switch (CMD) {
			case ParamsVO.CMD_NUEVO:
				planNuevo(request, session, usuVO, plan);
				clean = true;
				break;
			case ParamsVO.CMD_EDITAR:
				plan = planEditar(request, session, usuVO, filtroPlan);
				break;
			case ParamsVO.CMD_ELIMINAR:
				planEliminar(request, session, usuVO, filtroPlan);
				clean = true;
				break;
			case ParamsVO.CMD_GUARDAR:
				if (planGuardar(request, session, usuVO, plan))
					clean = true;
				break;
			}
			planInit(request, session, usuVO, plan, clean, filtroPlan);
			break;
		case ParamsVO.FICHA_AREA:
			FICHA = FICHA_AREA;
			FiltroAreaVO filtroArea = (FiltroAreaVO) session
					.getAttribute("filtroAreaPlanVO");
			AreaVO area = (AreaVO) session.getAttribute("areaPlanVO");
			switch (CMD) {
			case ParamsVO.CMD_DEFAULT:
				System.out.println("Limpiar bean de area");
				filtroArea = null;
				area = null;
				session.removeAttribute("filtroAreaPlanVO");
				session.removeAttribute("areaPlanVO");
			case ParamsVO.CMD_NUEVO:

				areaNuevo(request, session, usuVO, area);
				// areaBuscar(request, session, usuVO,filtroArea);
				clean = true;
				break;
			case ParamsVO.CMD_BUSCAR:
				areaBuscar(request, session, usuVO, filtroArea);
				clean = true;
				break;
			case ParamsVO.CMD_EDITAR:
				area = areaEditar(request, session, usuVO, filtroArea);
				areaBuscar(request, session, usuVO, filtroArea);
				break;
			case ParamsVO.CMD_ELIMINAR:
				areaEliminar(request, session, usuVO, filtroArea);
				areaBuscar(request, session, usuVO, filtroArea);
				clean = true;
				break;
			case ParamsVO.CMD_GUARDAR:
				if (areaGuardar(request, session, usuVO, area))
					clean = true;
				areaBuscar(request, session, usuVO, filtroArea);

				break;
			}
			areaInit(request, session, usuVO, filtroArea, area, clean);
			break;
		case ParamsVO.FICHA_ASIGNATURA:
			FICHA = FICHA_ASIGNATURA;
			AsignaturaVO asignatura = (AsignaturaVO) session
					.getAttribute("asignaturaPlanVO");
			FiltroAsignaturaVO filtroAsignatura = (FiltroAsignaturaVO) session
					.getAttribute("filtroAsignaturaPlanVO");
			switch (CMD) {
			case ParamsVO.CMD_DEFAULT:
				System.out.println("remover bean de asig");
				if (session.getAttribute("filtroAsignaturaPlanVO") != null)
					session.removeAttribute("filtroAsignaturaPlanVO");
				asignatura = null;
				filtroAsignatura = null;
				session.removeAttribute("asignaturaPlanVO");
			case ParamsVO.CMD_NUEVO:
				asignaturaNuevo(request, session, usuVO, asignatura);
				asignaturaBuscar(request, session, usuVO, filtroAsignatura);
				clean = true;
				break;
			case ParamsVO.CMD_BUSCAR:
				asignaturaBuscar(request, session, usuVO, filtroAsignatura);
				clean = true;
				break;
			case ParamsVO.CMD_EDITAR:
				asignatura = asignaturaEditar(request, session, usuVO,
						filtroAsignatura);
				asignaturaBuscar(request, session, usuVO, filtroAsignatura);
				break;
			case ParamsVO.CMD_ELIMINAR:
				asignaturaEliminar(request, session, usuVO, filtroAsignatura);
				asignaturaBuscar(request, session, usuVO, filtroAsignatura);
				clean = true;
				break;
			case ParamsVO.CMD_GUARDAR:
				if (asignaturaGuardar(request, session, usuVO, asignatura))
					clean = true;
				asignaturaBuscar(request, session, usuVO, filtroAsignatura);

				break;
			case 14:
				try {
					String params[] = request.getParameterValues("ajax");
					request.setAttribute("metodvali", String.valueOf(params[1]));
					request.setAttribute("arevali", String.valueOf(params[3]));
					session.setAttribute(
							"listavalvalidaplan1",
							planDeEstudiosDAO.valorvalplans(
									String.valueOf(params[0]), "1", "5",
									String.valueOf(params[1]),
									String.valueOf(params[2]),
									String.valueOf(params[3])));
					session.setAttribute(
							"listavalvalidaplan2",
							planDeEstudiosDAO.valorvalplans(
									String.valueOf(params[0]), "6", "9",
									String.valueOf(params[1]),
									String.valueOf(params[2]),
									String.valueOf(params[3])));
					session.setAttribute("listavalvalidaplan3",
							planDeEstudiosDAO.valorvalplans(
									String.valueOf(params[0]), "10", "12",
									String.valueOf(params[1]),
									String.valueOf(params[2]),
									String.valueOf(params[3])));
					session.setAttribute("listavalvalidaplanciclo1",
							planDeEstudiosDAO.valorvalplans(
									String.valueOf(params[0]), "20", "21",
									String.valueOf(params[1]),
									String.valueOf(params[2]),
									String.valueOf(params[3])));
					session.setAttribute("listavalvalidaplanciclo2",
							planDeEstudiosDAO.valorvalplans(
									String.valueOf(params[0]), "22", "23",
									String.valueOf(params[1]),
									String.valueOf(params[2]),
									String.valueOf(params[3])));
					session.setAttribute("listavalvalidaplanciclo3",
							planDeEstudiosDAO.valorvalplans(
									String.valueOf(params[0]), "24", "25",
									String.valueOf(params[1]),
									String.valueOf(params[2]),
									String.valueOf(params[3])));
					System.out.println("valor var " + String.valueOf(params[0])
							+ " " + String.valueOf(params[1]) + " "
							+ String.valueOf(params[2]) + " "
							+ String.valueOf(params[3]));
					session.setAttribute(
							"listavalvalidaplan1ins",
							planDeEstudiosDAO.valorvalplansins(
									String.valueOf(params[0]),
									String.valueOf(params[1]),
									String.valueOf(params[2]),
									String.valueOf(params[3])));
				} catch (Exception e) {
					request.setAttribute(
							ParamsVO.SMS,
							"El asignatura no pudo ser obtenida: "
									+ e.getMessage());
				}
			}
			asignaturaInit(request, session, usuVO, filtroAsignatura,
					asignatura, clean);
			identificarParametroColegio(request, session, usuVO);
			break;
		}
		dispatcher[0] = String.valueOf(ParamsVO.INCLUDE);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	public PlanDeEstudiosVO planEditar(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroPlanDeEstudiosVO filtro)
			throws ServletException {
		PlanDeEstudiosVO plan = null;
		try {
			String params[] = request.getParameterValues("id");
			if (params == null || params.length < 3 || params[0] == null
					|| params[1] == null || params[2] == null)
				throw new ServletException("datos insuficientes");
			plan = new PlanDeEstudiosVO();
			plan.setPlaInstitucion(Long.parseLong(params[0]));
			plan.setPlaMetodologia(Integer.parseInt(params[1]));
			plan.setPlaVigencia(Integer.parseInt(params[2]));
			plan = planDeEstudiosDAO.getPlanEstudios(plan);
			session.setAttribute("planDeEstudiosVO", plan);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(
					ParamsVO.SMS,
					"El plan de estudios no pudo ser obtenido: "
							+ e.getMessage());
		}
		return plan;
	}

	public void planEliminar(HttpServletRequest request, HttpSession session,
			Login usuVO, FiltroPlanDeEstudiosVO filtro) throws ServletException {
		try {
			String params[] = request.getParameterValues("id");
			if (params == null || params.length < 3 || params[0] == null
					|| params[1] == null || params[2] == null)
				throw new ServletException("datos insuficientes");
			PlanDeEstudiosVO plan = new PlanDeEstudiosVO();
			plan.setPlaInstitucion(Long.parseLong(params[0]));
			plan.setPlaMetodologia(Integer.parseInt(params[1]));
			plan.setPlaVigencia(Integer.parseInt(params[2]));
			PlanDeEstudiosVO planE = planDeEstudiosDAO.getPlanEstudios(plan);
			planDeEstudiosDAO.eliminarPlanDeEstudios(plan);
				//insercion de bitacora
				String jsonString="";
				BitacoraCOM com = new BitacoraCOM();
				HttpSession session2 = request.getSession();
				String loginBitacora = (String)session2.getAttribute("loginBitacora");
				try{
					
					LogPlanEstudioDto log = new LogPlanEstudioDto();
					log.setCriterioEvaluacion(planE.getPlaCriterio());
					log.setPlanesApoyo(planE.getPlaPlanEspecial());
					log.setIdentificadorRegistro(planE.getPlaInstitucion());
					List<ItemVO> metodologias = planDeEstudiosDAO.getListaMetodologia(Long.parseLong(usuVO.getInstId()));
					for(int i=0;i<metodologias.size();i++){
						ItemVO obj = metodologias.get(i);
						if(obj.getCodigo()==planE.getPlaMetodologia()){
							log.setMetodologia(obj.getNombre());
							break;
						}
					}
					
					log.setPlanesApoyo(planE.getPlaPlanEspecial());
					log.setProcedimientoEvaluacion(planE.getPlaProcedimiento());
					
					List<ItemVO> vigencias = planDeEstudiosDAO.getListaVigenciaInst(Long.valueOf(usuVO.getInstId()));
					
					for(int i=0;i<vigencias.size();i++){
						ItemVO obj = vigencias.get(i);
						if(obj.getCodigo()==plan.getPlaVigencia()){
							log.setVigencia(obj.getNombre());
							break;
						}
					}
					
					Gson gson = new Gson();
					jsonString = gson.toJson(log);
					com.insertarBitacora(Long.valueOf(usuVO.getInstId()), Integer.parseInt(usuVO.getJornadaId()), 3, usuVO.getPerfil(), Integer.parseInt(usuVO.getSedeId()), 
							61, 3, loginBitacora, jsonString);
				}catch(Exception e){
					
				}	
			session.removeAttribute("planDeEstudiosVO");
			request.setAttribute(ParamsVO.SMS,
					"El plan de estudios fue eliminado satisfactoriamente");
		} catch (Exception e) {
			request.setAttribute(ParamsVO.SMS,
					"El plan de estudios no fue eliminado: " + e.getMessage());
		}
	}

	public boolean planGuardar(HttpServletRequest request, HttpSession session,
			Login usuVO, PlanDeEstudiosVO plan) throws ServletException {
		try {
			//insercion de bitacora
			String jsonString="";
			BitacoraCOM com = new BitacoraCOM();
			HttpSession session2 = request.getSession();
			String loginBitacora = (String)session2.getAttribute("loginBitacora");
			try{
				
				
				LogPlanEstudioDto log = new LogPlanEstudioDto();
				log.setCriterioEvaluacion(plan.getPlaCriterio());
				log.setPlanesApoyo(plan.getPlaPlanEspecial());
				log.setIdentificadorRegistro(plan.getPlaInstitucion());
				
				List<ItemVO> metodologias = planDeEstudiosDAO.getListaMetodologia(Long.parseLong(usuVO.getInstId()));
				for(int i=0;i<metodologias.size();i++){
					ItemVO obj = metodologias.get(i);
					if(obj.getCodigo()==plan.getPlaMetodologia()){
						log.setMetodologia(obj.getNombre());
						break;
					}
				}
				
				log.setPlanesApoyo(plan.getPlaPlanEspecial());
				log.setProcedimientoEvaluacion(plan.getPlaProcedimiento());
				
				List<ItemVO> vigencias = planDeEstudiosDAO.getListaVigenciaInst(Long.valueOf(usuVO.getInstId()));
				
				for(int i=0;i<vigencias.size();i++){
					ItemVO obj = vigencias.get(i);
					if(obj.getCodigo()==plan.getPlaVigencia()){
						log.setVigencia(obj.getNombre());
						break;
					}
				}
				
				Gson gson = new Gson();
				jsonString = gson.toJson(log);
			}catch(Exception e){
				
			}	
			if (plan.getFormaEstado().equals("1")) {
				planDeEstudiosDAO.actualizarPlanDeEstudios(plan);
				try{
					com.insertarBitacora(Long.valueOf(usuVO.getInstId()), Integer.parseInt(usuVO.getJornadaId()), 3, usuVO.getPerfil(), Integer.parseInt(usuVO.getSedeId()), 
							63, 2, loginBitacora, jsonString);
				}catch(Exception e){
					
				}
				
			} else {
				planDeEstudiosDAO.ingresarPlanDeEstudios(plan);
				try{
					com.insertarBitacora(Long.valueOf(usuVO.getInstId()), Integer.parseInt(usuVO.getJornadaId()), 3, usuVO.getPerfil(), Integer.parseInt(usuVO.getSedeId()), 
						63, 1, loginBitacora, jsonString);
				}catch(Exception e){
					
				}
			}
			session.removeAttribute("planDeEstudiosVO");
			request.setAttribute(ParamsVO.SMS,
					"El plan de estudios fue ingresado/actualizado satisfactoriamente");
		} catch (Exception e) {
			request.setAttribute(
					ParamsVO.SMS,
					"El plan de estudios no fue ingresado/actualizado: "
							+ e.getMessage());
			return false;
		}
		return true;
	}

	public void planInit(HttpServletRequest request, HttpSession session,
			Login usuVO, PlanDeEstudiosVO plan, boolean clean,
			FiltroPlanDeEstudiosVO filtro) throws ServletException {
		try {
			if (plan == null || clean) {
				plan = new PlanDeEstudiosVO();
				plan.setPlaInstitucion(Long.parseLong(usuVO.getInstId()));
				plan.setPlaVigencia((int) planDeEstudiosDAO
						.getVigenciaInst(plan.getPlaInstitucion()));
				session.setAttribute("planDeEstudiosVO", plan);
			}
			if (filtro == null || filtro.getFilInstitucion() == 0) {
				filtro = new FiltroPlanDeEstudiosVO();
				filtro.setFilInstitucion(Long.parseLong(usuVO.getInstId()));
				filtro.setFilVigencia((int) planDeEstudiosDAO
						.getVigenciaInst(filtro.getFilInstitucion()));
				session.setAttribute("filtroPlanDeEstudiosVO", filtro);
			}
			request.setAttribute("listaPlanDeEstudios",
					planDeEstudiosDAO.getListaPlanEstudios(filtro));
			request.setAttribute("listaMetodologia", planDeEstudiosDAO
					.getListaMetodologia(plan.getPlaInstitucion()));
			request.setAttribute("listaVigencia", planDeEstudiosDAO
					.getListaVigenciaInst(filtro.getFilInstitucion()));
		} catch (Exception e) {
			throw new ServletException("Error interno: " + e.getMessage());
		}
	}

	public void planNuevo(HttpServletRequest request, HttpSession session,
			Login usuVO, PlanDeEstudiosVO plan) throws ServletException {
		session.removeAttribute("planDeEstudiosVO");
	}

	public void areaBuscar(HttpServletRequest request, HttpSession session,
			Login usuVO, FiltroAreaVO filtro) throws ServletException {
		try {
			if (filtro != null && filtro.getFilMetodologia() > 0) {
				System.out.println("valores busqueda:"
						+ filtro.getFilInstitucion() + "//"
						+ filtro.getFilMetodologia());
				request.setAttribute("listaArea",
						planDeEstudiosDAO.getListaArea(filtro));
			}
		} catch (Exception e) {
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public AreaVO areaEditar(HttpServletRequest request, HttpSession session,
			Login usuVO, FiltroAreaVO filtro) throws ServletException {
		AreaVO area = null;
		try {
			String params[] = request.getParameterValues("id");
			if (params == null || params.length < 4 || params[0] == null
					|| params[1] == null || params[2] == null
					|| params[3] == null)
				throw new ServletException("datos insuficientes");
			area = new AreaVO();
			area.setAreInstitucion(Long.parseLong(params[0]));
			area.setAreMetodologia(Integer.parseInt(params[1]));
			area.setAreVigencia(Integer.parseInt(params[2]));
			area.setAreCodigo(Long.parseLong(params[3]));
			area = planDeEstudiosDAO.getArea(area);
			session.setAttribute("areaPlanVO", area);
		} catch (Exception e) {
			request.setAttribute(ParamsVO.SMS, "El área no pudo ser obtenida: "
					+ e.getMessage());
		}
		return area;
	}

	public void areaEliminar(HttpServletRequest request, HttpSession session,
			Login usuVO, FiltroAreaVO filtro) throws ServletException {
		try {
			String params[] = request.getParameterValues("id");
			if (params == null || params.length < 4 || params[0] == null
					|| params[1] == null || params[2] == null
					|| params[3] == null)
				throw new ServletException("datos insuficientes");
			AreaVO area = new AreaVO();
			area.setAreInstitucion(Long.parseLong(params[0]));
			area.setAreMetodologia(Integer.parseInt(params[1]));
			area.setAreVigencia(Integer.parseInt(params[2]));
			area.setAreCodigo(Long.parseLong(params[3]));
			AreaVO areaE = planDeEstudiosDAO.getArea(area);
			planDeEstudiosDAO.eliminarArea(area);
			session.removeAttribute("areaPlanVO");
			request.setAttribute(ParamsVO.SMS,
					"El área fue eliminada satisfactoriamente");
			
			try {
				
				//insercion de bitacora
				String jsonString="";
				BitacoraCOM com = new BitacoraCOM();
				HttpSession session2 = request.getSession();
				String loginBitacora = (String)session2.getAttribute("loginBitacora");
		
					LogAreaDto log = new LogAreaDto();
					log.setAbreviatura(area.getAreAbreviatura());
					
					List<ItemVO> areas = planDeEstudiosDAO.getListaAreaBase();
					for(int i=0;i<areas.size();i++){
						ItemVO obj = areas.get(i);
						if(obj.getCodigo()==area.getAreCodigo()){
							log.setArea(obj.getNombre());
							break;
						}
					}
					
					Gson gson = new Gson();
					String jsonGrados = gson.toJson(areaE.getAreGrado());
					log.setGrados(jsonGrados);
					log.setIdentificadorRegistro(String.valueOf(areaE.getAreCodigo()));
					
					List<ItemVO> metodologias = planDeEstudiosDAO.getListaMetodologia(Long.parseLong(usuVO.getInstId()));
					for(int i=0;i<metodologias.size();i++){
						ItemVO obj = metodologias.get(i);
						if(obj.getCodigo()==areaE.getAreMetodologia()){
							log.setMetodologia(obj.getNombre());
							break;
						}
					}
					log.setNombre(areaE.getAreNombre());
					log.setOrden(String.valueOf(areaE.getAreOrden()));
					List<ItemVO> vigencias = planDeEstudiosDAO.getListaVigenciaInst(Long.valueOf(usuVO.getInstId()));
					for(int i=0;i<vigencias.size();i++){
						ItemVO obj = vigencias.get(i);
						if(obj.getCodigo()==areaE.getAreVigencia()){
							log.setVigencia(obj.getNombre());
							break;
						}
					}
					gson = new Gson();
					jsonString = gson.toJson(log);
					
					com.insertarBitacora(Long.valueOf(usuVO.getInstId()), Integer.parseInt(usuVO.getJornadaId()), 3, usuVO.getPerfil(), Integer.parseInt(usuVO.getSedeId()), 
							63, 3, loginBitacora, jsonString);
				}catch(Exception e){
					
				}	
		
		} catch (Exception e) {
			request.setAttribute(ParamsVO.SMS,
					"El área no fue eliminada: " + e.getMessage());
		}
	}

	public boolean areaGuardar(HttpServletRequest request, HttpSession session,
			Login usuVO, AreaVO area) throws ServletException {
		try {
			//insercion de bitacora
			String jsonString="";
			BitacoraCOM com = new BitacoraCOM();
			HttpSession session2 = request.getSession();
			String loginBitacora = (String)session2.getAttribute("loginBitacora");
			try{
				
				LogAreaDto log = new LogAreaDto();
				log.setAbreviatura(area.getAreAbreviatura());
				
				List<ItemVO> areas = planDeEstudiosDAO.getListaAreaBase();
				for(int i=0;i<areas.size();i++){
					ItemVO obj = areas.get(i);
					if(obj.getCodigo()==area.getAreCodigo()){
						log.setArea(obj.getNombre());
						break;
					}
				}
				
				Gson gson = new Gson();
				List<Long> gradosLog = new ArrayList<>();
				for(int i=0;i<area.getAreGrado().length;i++){
					if(area.getAreGrado()[i]!=-1){
						gradosLog.add(area.getAreGrado()[i]);
					}
				}
				String jsonGrados = gson.toJson(gradosLog);
				log.setGrados(jsonGrados);
				log.setIdentificadorRegistro(String.valueOf(area.getAreCodigo()));
				
				List<ItemVO> metodologias = planDeEstudiosDAO.getListaMetodologia(Long.parseLong(usuVO.getInstId()));
				for(int i=0;i<metodologias.size();i++){
					ItemVO obj = metodologias.get(i);
					if(obj.getCodigo()==area.getAreMetodologia()){
						log.setMetodologia(obj.getNombre());
						break;
					}
				}
				log.setNombre(area.getAreNombre());
				log.setOrden(String.valueOf(area.getAreOrden()));
				List<ItemVO> vigencias = planDeEstudiosDAO.getListaVigenciaInst(Long.valueOf(usuVO.getInstId()));
				for(int i=0;i<vigencias.size();i++){
					ItemVO obj = vigencias.get(i);
					if(obj.getCodigo()==area.getAreVigencia()){
						log.setVigencia(obj.getNombre());
						break;
					}
				}
				gson = new Gson();
				jsonString = gson.toJson(log);
			}catch(Exception e){
				
			}	
			if (area.getFormaEstado().equals("1")) {
				planDeEstudiosDAO.actualizarArea(area);
				try{
					
					com.insertarBitacora(Long.valueOf(usuVO.getInstId()), Integer.parseInt(usuVO.getJornadaId()), 3, usuVO.getPerfil(), Integer.parseInt(usuVO.getSedeId()), 
							63, 2, loginBitacora, jsonString);
				}catch(Exception e){
					
				}	
			} else {
				planDeEstudiosDAO.ingresarArea(area);
				try{
					
					com.insertarBitacora(Long.valueOf(usuVO.getInstId()), Integer.parseInt(usuVO.getJornadaId()), 3, usuVO.getPerfil(), Integer.parseInt(usuVO.getSedeId()), 
							63, 1, loginBitacora, jsonString);
				}catch(Exception e){
					
				}	
			}
			session.removeAttribute("areaPlanVO");
			request.setAttribute(ParamsVO.SMS,
					"El área fue ingresada/actualizada satisfactoriamente");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS,
					"El área no fue ingresada/actualizada: " + e.getMessage());
			return false;
		}
		return true;
	}

	public void areaInit(HttpServletRequest request, HttpSession session,
			Login usuVO, FiltroAreaVO filtro, AreaVO area, boolean clean)
			throws ServletException {
		try {
			if (area == null || clean) {
				System.out.println("INIT AREA");
				area = new AreaVO();
				area.setFormaEstado("0");
				area.setAreInstitucion(Long.parseLong(usuVO.getInstId()));
				area.setAreVigencia((int) planDeEstudiosDAO
						.getVigenciaInst(area.getAreInstitucion()));
				session.setAttribute("areaPlanVO", area);
			}
			if (area.getFormaEstado().equals("1")) {
				// los grados pero con los checkeados propios
				request.setAttribute("listaGrado", planDeEstudiosDAO
						.getListaGradoAreaBase(area.getAreInstitucion(),
								area.getAreMetodologia(),
								area.getAreVigencia(), area.getAreCodigo()));
			} else {
				// la lista entera sin checkeados para dejar invisibles y
				// esperar el evento de metodologia ajax
				request.setAttribute("listaGrado",
						planDeEstudiosDAO.getListaGradoTotal());
			}
			if (filtro == null || filtro.getFilInstitucion() == 0) {
				filtro = new FiltroAreaVO();
				filtro.setFilInstitucion(Long.parseLong(usuVO.getInstId()));
				filtro.setFilVigencia((int) 0);
				session.setAttribute("filtroAreaPlanVO", filtro);
			}
			// los que siempre van
			// metodologia
			request.setAttribute("listaMetodologia", planDeEstudiosDAO
					.getListaMetodologia(filtro.getFilInstitucion()));
			request.setAttribute("listaVigencia", planDeEstudiosDAO
					.getListaVigenciaInst(filtro.getFilInstitucion()));
			// son las areas del sistema, sin ajax
			request.setAttribute("listaAreaBase",
					planDeEstudiosDAO.getListaAreaBase());
		} catch (Exception e) {
			throw new ServletException("Error interno: " + e.getMessage());
		}
	}

	public void areaNuevo(HttpServletRequest request, HttpSession session,
			Login usuVO, AreaVO area) throws ServletException {
		session.removeAttribute("areaPlanVO");
	}

	public void asignaturaBuscar(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroAsignaturaVO filtro)
			throws ServletException {
		try {
			if (filtro != null && filtro.getFilArea() > 0) {
				System.out.println("valores busqueda:"
						+ filtro.getFilInstitucion() + "//"
						+ filtro.getFilMetodologia() + "//"
						+ filtro.getFilVigencia() + "//" + filtro.getFilArea());
				request.setAttribute("listaAsignatura",
						planDeEstudiosDAO.getListaAsignatura(filtro));
				request.setAttribute("metodvali",
						String.valueOf(filtro.getFilMetodologia()));
				request.setAttribute("vigvali",
						String.valueOf(filtro.getFilVigencia()));
				request.setAttribute("areavali",
						String.valueOf(filtro.getFilArea()));
				session.setAttribute("listavalvalidaplan1", planDeEstudiosDAO
						.valorvalplans(
								String.valueOf(filtro.getFilInstitucion()),
								"1", "5",
								String.valueOf(filtro.getFilMetodologia()),
								String.valueOf(filtro.getFilVigencia()),
								String.valueOf(filtro.getFilArea())));
				session.setAttribute("listavalvalidaplan2", planDeEstudiosDAO
						.valorvalplans(
								String.valueOf(filtro.getFilInstitucion()),
								"6", "9",
								String.valueOf(filtro.getFilMetodologia()),
								String.valueOf(filtro.getFilVigencia()),
								String.valueOf(filtro.getFilArea())));
				session.setAttribute("listavalvalidaplan3", planDeEstudiosDAO
						.valorvalplans(
								String.valueOf(filtro.getFilInstitucion()),
								"10", "12",
								String.valueOf(filtro.getFilMetodologia()),
								String.valueOf(filtro.getFilVigencia()),
								String.valueOf(filtro.getFilArea())));
				session.setAttribute("listavalvalidaplanciclo1",
						planDeEstudiosDAO.valorvalplans(
								String.valueOf(filtro.getFilInstitucion()),
								"20", "21",
								String.valueOf(filtro.getFilMetodologia()),
								String.valueOf(filtro.getFilVigencia()),
								String.valueOf(filtro.getFilArea())));
				session.setAttribute("listavalvalidaplanciclo2",
						planDeEstudiosDAO.valorvalplans(
								String.valueOf(filtro.getFilInstitucion()),
								"22", "23",
								String.valueOf(filtro.getFilMetodologia()),
								String.valueOf(filtro.getFilVigencia()),
								String.valueOf(filtro.getFilArea())));
				session.setAttribute("listavalvalidaplanciclo3",
						planDeEstudiosDAO.valorvalplans(
								String.valueOf(filtro.getFilInstitucion()),
								"24", "25",
								String.valueOf(filtro.getFilMetodologia()),
								String.valueOf(filtro.getFilVigencia()),
								String.valueOf(filtro.getFilArea())));
				session.setAttribute("listavalvalidaplan1ins",
						planDeEstudiosDAO.valorvalplansins(
								String.valueOf(filtro.getFilInstitucion()),
								String.valueOf(filtro.getFilMetodologia()),
								String.valueOf(filtro.getFilVigencia()),
								String.valueOf(filtro.getFilArea())));
			}
		} catch (Exception e) {
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public AsignaturaVO asignaturaEditar(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroAsignaturaVO filtro)
			throws ServletException {
		AsignaturaVO asignatura = null;
		try {
			String params[] = request.getParameterValues("id");
			if (params == null || params.length < 4 || params[0] == null
					|| params[1] == null || params[2] == null
					|| params[3] == null)
				throw new ServletException("datos insuficientes");
			asignatura = new AsignaturaVO();
			asignatura.setAsiInstitucion(Long.parseLong(params[0]));
			asignatura.setAsiMetodologia(Integer.parseInt(params[1]));
			asignatura.setAsiVigencia(Integer.parseInt(params[2]));
			asignatura.setAsiCodigo(Long.parseLong(params[3]));
			asignatura = planDeEstudiosDAO.getAsignatura(asignatura);
			session.setAttribute("listavalvalidaplan1", planDeEstudiosDAO
					.valorvalplans(
							String.valueOf(asignatura.getAsiInstitucion()),
							"1", "5",
							String.valueOf(asignatura.getAsiMetodologia()),
							String.valueOf(asignatura.getAsiVigencia()),
							String.valueOf(asignatura.getAsiArea())));
			session.setAttribute("listavalvalidaplan2", planDeEstudiosDAO
					.valorvalplans(
							String.valueOf(asignatura.getAsiInstitucion()),
							"6", "9",
							String.valueOf(asignatura.getAsiMetodologia()),
							String.valueOf(asignatura.getAsiVigencia()),
							String.valueOf(asignatura.getAsiArea())));
			session.setAttribute("listavalvalidaplan3", planDeEstudiosDAO
					.valorvalplans(
							String.valueOf(asignatura.getAsiInstitucion()),
							"10", "12",
							String.valueOf(asignatura.getAsiMetodologia()),
							String.valueOf(asignatura.getAsiVigencia()),
							String.valueOf(asignatura.getAsiArea())));
			session.setAttribute("listavalvalidaplanciclo1", planDeEstudiosDAO
					.valorvalplans(
							String.valueOf(asignatura.getAsiInstitucion()),
							"20", "21",
							String.valueOf(asignatura.getAsiMetodologia()),
							String.valueOf(asignatura.getAsiVigencia()),
							String.valueOf(asignatura.getAsiArea())));
			session.setAttribute("listavalvalidaplanciclo2", planDeEstudiosDAO
					.valorvalplans(
							String.valueOf(asignatura.getAsiInstitucion()),
							"22", "23",
							String.valueOf(asignatura.getAsiMetodologia()),
							String.valueOf(asignatura.getAsiVigencia()),
							String.valueOf(asignatura.getAsiArea())));
			session.setAttribute("listavalvalidaplanciclo3", planDeEstudiosDAO
					.valorvalplans(
							String.valueOf(asignatura.getAsiInstitucion()),
							"24", "25",
							String.valueOf(asignatura.getAsiMetodologia()),
							String.valueOf(asignatura.getAsiVigencia()),
							String.valueOf(asignatura.getAsiArea())));
			session.setAttribute("listavalvalidaplan1ins", planDeEstudiosDAO
					.valorvalplansins(
							String.valueOf(asignatura.getAsiInstitucion()),
							String.valueOf(asignatura.getAsiMetodologia()),
							String.valueOf(asignatura.getAsiVigencia()),
							String.valueOf(asignatura.getAsiArea())));
			session.setAttribute("asignaturaPlanVO", asignatura);
		} catch (Exception e) {
			request.setAttribute(ParamsVO.SMS,
					"El asignatura no pudo ser obtenida: " + e.getMessage());
		}
		return asignatura;
	}

	public void asignaturaEliminar(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroAsignaturaVO filtro)
			throws ServletException {
		try {
			String params[] = request.getParameterValues("id");
			if (params == null || params.length < 4 || params[0] == null
					|| params[1] == null || params[2] == null
					|| params[3] == null)
				throw new ServletException("datos insuficientes");
			AsignaturaVO asignatura = new AsignaturaVO();
			asignatura.setAsiInstitucion(Long.parseLong(params[0]));
			asignatura.setAsiMetodologia(Integer.parseInt(params[1]));
			asignatura.setAsiVigencia(Integer.parseInt(params[2]));
			asignatura.setAsiCodigo(Long.parseLong(params[3]));
			planDeEstudiosDAO.eliminarAsignatura(asignatura);
			session.removeAttribute("asignaturaPlanVO");
			request.setAttribute(ParamsVO.SMS,
					"El asignatura fue eliminada satisfactoriamente");

				AsignaturaVO asignaturaE = planDeEstudiosDAO.getAsignatura(asignatura);
				//insercion de bitacora
				String jsonString="";
				BitacoraCOM com = new BitacoraCOM();
				HttpSession session2 = request.getSession();
				String loginBitacora = (String)session2.getAttribute("loginBitacora");
				try{
					
					LogAsignaturaDto log = new LogAsignaturaDto();
					log.setAbreviatura(asignaturaE.getAsiAbreviatura());
					log.setAsignatura(asignaturaE.getAsiNombre());
					List<ItemVO> areas = planDeEstudiosDAO.getListaAreaBase();
					for(int i=0;i<areas.size();i++){
						ItemVO obj = areas.get(i);
						if(obj.getCodigo()==asignaturaE.getAsiArea()){
							log.setArea(obj.getNombre());
							break;
						}
					}
					
					Gson gson = new Gson();
					
					List<LogGradoDto> gradosLog = new ArrayList<>();
					LogGradoDto grado;
					String gradoS;
					String [] infoGrado;
					for(int i=0;i<asignatura.getAsiGrado().length;i++){
						if(asignatura.getAsiGrado()[i]!="-1"){
							gradoS = asignatura.getAsiGrado()[i];
							infoGrado = gradoS.split(":");
							grado = new LogGradoDto();
							grado.setGrado(infoGrado[0]);
							grado.setIntensidadHoraria(infoGrado[1]);
							grado.setEstado(infoGrado[2]);
							gradosLog.add(grado);
						}
					}
					String jsonGrados = gson.toJson(gradosLog);
					log.setGrados(jsonGrados);

					log.setIdentificadorRegistro(String.valueOf(asignaturaE.getAsiCodigo()));
					List<ItemVO> metodologias = planDeEstudiosDAO.getListaMetodologia(Long.parseLong(usuVO.getInstId()));
					for(int i=0;i<metodologias.size();i++){
						ItemVO obj = metodologias.get(i);
						if(obj.getCodigo()==asignaturaE.getAsiMetodologia()){
							log.setMetodologia(obj.getNombre());
							break;
						}
					}
					log.setNombre(asignaturaE.getAsiNombre());
					log.setOrden(String.valueOf(asignaturaE.getAsiOrden()));
					List<ItemVO> vigencias = planDeEstudiosDAO.getListaVigenciaInst(Long.valueOf(usuVO.getInstId()));
					for(int i=0;i<vigencias.size();i++){
						ItemVO obj = vigencias.get(i);
						if(obj.getCodigo()==asignaturaE.getAsiVigencia()){
							log.setVigencia(obj.getNombre());
							break;
						}
					}
					gson = new Gson();
					jsonString = gson.toJson(log);
					
					com.insertarBitacora(Long.valueOf(usuVO.getInstId()), Integer.parseInt(usuVO.getJornadaId()), 3, usuVO.getPerfil(), Integer.parseInt(usuVO.getSedeId()), 
							63, 3, loginBitacora, jsonString);
				}catch(Exception e){
					
				}	
		} catch (Exception e) {
			request.setAttribute(ParamsVO.SMS,
					"El asignatura no fue eliminada: " + e.getMessage());
		}
	}

	public boolean asignaturaGuardar(HttpServletRequest request,
			HttpSession session, Login usuVO, AsignaturaVO asignatura)
			throws ServletException {
		String mspon = "";
		String mspon1 = "";
		String mspon2 = "";
		String mspon3 = "";

		try {
			//insercion de bitacora
			String jsonString="";
			BitacoraCOM com = new BitacoraCOM();
			HttpSession session2 = request.getSession();
			String loginBitacora = (String)session2.getAttribute("loginBitacora");
			try{
				
				LogAsignaturaDto log = new LogAsignaturaDto();
				log.setAbreviatura(asignatura.getAsiAbreviatura());
				log.setAsignatura(asignatura.getAsiNombre());
				List<ItemVO> areas = planDeEstudiosDAO.getListaAreaBase();
				for(int i=0;i<areas.size();i++){
					ItemVO obj = areas.get(i);
					if(obj.getCodigo()==asignatura.getAsiArea()){
						log.setArea(obj.getNombre());
						break;
					}
				}
				
				Gson gson = new Gson();
				List<LogGradoDto> gradosLog = new ArrayList<>();
				LogGradoDto grado;
				String gradoS;
				String [] infoGrado;
				for(int i=0;i<asignatura.getAsiGrado().length;i++){
					if(asignatura.getAsiGrado()[i]!="-1"){
						gradoS = asignatura.getAsiGrado()[i];
						infoGrado = gradoS.split(":");
						grado = new LogGradoDto();
						grado.setGrado(infoGrado[0]);
						grado.setIntensidadHoraria(infoGrado[1]);
						grado.setEstado(infoGrado[2]);
						gradosLog.add(grado);
					}
				}
				String jsonGrados = gson.toJson(gradosLog);
				log.setGrados(jsonGrados);
				log.setIdentificadorRegistro(String.valueOf(asignatura.getAsiCodigo()));
				List<ItemVO> metodologias = planDeEstudiosDAO.getListaMetodologia(Long.parseLong(usuVO.getInstId()));
				for(int i=0;i<metodologias.size();i++){
					ItemVO obj = metodologias.get(i);
					if(obj.getCodigo()==asignatura.getAsiMetodologia()){
						log.setMetodologia(obj.getNombre());
						break;
					}
				}
				log.setNombre(asignatura.getAsiNombre());
				log.setOrden(String.valueOf(asignatura.getAsiOrden()));
				List<ItemVO> vigencias = planDeEstudiosDAO.getListaVigenciaInst(Long.valueOf(usuVO.getInstId()));
				for(int i=0;i<vigencias.size();i++){
					ItemVO obj = vigencias.get(i);
					if(obj.getCodigo()==asignatura.getAsiVigencia()){
						log.setVigencia(obj.getNombre());
						break;
					}
				}
				gson = new Gson();
				jsonString = gson.toJson(log);
			}catch(Exception e){
				
			}	
			if (asignatura.getFormaEstado().equals("1")) {
				planDeEstudiosDAO.actualizarAsignatura(asignatura);
				try{
					com.insertarBitacora(Long.valueOf(usuVO.getInstId()), Integer.parseInt(usuVO.getJornadaId()), 3, usuVO.getPerfil(), Integer.parseInt(usuVO.getSedeId()), 
							63, 2, loginBitacora, jsonString);
				}catch (Exception e) {
					// TODO: handle exception
				}
				
			} else {
				planDeEstudiosDAO.ingresarAsignatura(asignatura);
				try{
					com.insertarBitacora(Long.valueOf(usuVO.getInstId()), Integer.parseInt(usuVO.getJornadaId()), 3, usuVO.getPerfil(), Integer.parseInt(usuVO.getSedeId()), 
							63, 1, loginBitacora, jsonString);
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
			session.removeAttribute("asignaturaPlanVO");
			if (((String) request.getParameter("jornpon")).equals("INS")) {
				if (request.getParameter("planinsf") != null
						|| !((String) request.getParameter("planinsf"))
								.equals("")) {
					if (((String) request.getParameter("planinsf")).equals("0"))
						mspon = ". Tenga en cuenta que ya asigno el 100% de ponderacion para el area";
					else
						mspon = ". Tenga en cuenta que debe asignar el "
								+ request.getParameter("planinsf")
								+ "% de ponderacion restante disponible para el area";
				}
			}
			if (((String) request.getParameter("jornpon")).equals("DIURNA")) {
				if (request.getParameter("plan1f") != null
						|| !((String) request.getParameter("plan1f"))
								.equals("")) {
					if (((String) request.getParameter("plan1f")).equals("0"))
						mspon1 = ".\nTenga en cuenta que ya asigno el 100% de ponderacion para el area en Primaria";
					else
						mspon1 = ".\nTenga en cuenta que debe asignar el "
								+ request.getParameter("plan1f")
								+ "% de ponderacion restante disponible para el area en Primaria";
					if (((String) request.getParameter("plan2f")).equals("0"))
						mspon2 = ".\nTenga en cuenta que ya asigno el 100% de ponderacion para el area en Basica";
					else
						mspon2 = ".\nTenga en cuenta que debe asignar el "
								+ request.getParameter("plan2f")
								+ "% de ponderacion restante disponible para el area en Basica";
					if (((String) request.getParameter("plan3f")).equals("0"))
						mspon3 = ".\nTenga en cuenta que ya asigno el 100% de ponderacion para el area en Media";
					else
						mspon3 = ".\nTenga en cuenta que debe asignar el "
								+ request.getParameter("plan3f")
								+ "% de ponderacion restante disponible para el area en Media";
					mspon = mspon1 + mspon2 + mspon3;
				}
			}
			if (((String) request.getParameter("jornpon")).equals("NOCTURNA")) {
				if (request.getParameter("planciclo1f") != null
						|| !((String) request.getParameter("planciclo1f"))
								.equals("")) {
					if (((String) request.getParameter("planciclo1f"))
							.equals("0"))
						mspon1 = ".\nTenga en cuenta que ya asigno el 100% de ponderacion para el area en Ciclo 1y2";
					else
						mspon1 = ".\nTenga en cuenta que debe asignar el "
								+ request.getParameter("planciclo1f")
								+ "% de ponderacion restante disponible para el area en Ciclo 1y2";
					if (((String) request.getParameter("planciclo2f"))
							.equals("0"))
						mspon2 = ".\nTenga en cuenta que ya asigno el 100% de ponderacion para el area en Ciclo 3y4";
					else
						mspon2 = ".\nTenga en cuenta que debe asignar el "
								+ request.getParameter("planciclo2f")
								+ "% de ponderacion restante disponible para el area en Ciclo 3y4";
					if (((String) request.getParameter("planciclo3f"))
							.equals("0"))
						mspon3 = ".\nTenga en cuenta que ya asigno el 100% de ponderacion para el area en Ciclo 5y6";
					else
						mspon3 = ".\nTenga en cuenta que debe asignar el "
								+ request.getParameter("planciclo3f")
								+ "% de ponderacion restante disponible para el area en Ciclo 5y6";
					mspon = mspon1 + mspon2 + mspon3;
				}
			}
			request.setAttribute(ParamsVO.SMS,
					"El asignatura fue ingresada/actualizada satisfactoriamente"
							+ mspon);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(
					ParamsVO.SMS,
					"El asignatura no fue ingresada/actualizada: "
							+ e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param request
	 * @param session
	 * @param usuVO
	 * @throws NumberFormatException
	 * @throws Exception
	 *             Metodo que se encarga de cargar los para,etrps del colegio
	 *             sobre para poder cuadrar la sponderaciones
	 * 
	 */
	public void identificarParametroColegio(HttpServletRequest request,
			HttpSession session, Login usuVO) {
		try {
			int idinst = Integer.parseInt(usuVO.getInstId());
			int vigencia;

			vigencia = (int) planDeEstudiosDAO.getVigenciaInst(Long
					.parseLong(usuVO.getInstId()));

			AdminParametroInstDAO adminParametro = new AdminParametroInstDAO(
					new Cursor());
			InstParVO instParVO = new InstParVO();
			instParVO = adminParametro.getPeriodoInstActual(vigencia, idinst);
			session.setAttribute("instParametros", instParVO);
			session.setAttribute("jornadaParametros", usuVO.getJornadaId());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void asignaturaInit(HttpServletRequest request, HttpSession session,
			Login usuVO, FiltroAsignaturaVO filtro, AsignaturaVO asignatura,
			boolean clean) throws ServletException {
		try {
			if (asignatura == null || clean) {
				System.out.println("INIT ASIGGNATURA");
				asignatura = new AsignaturaVO();
				asignatura.setFormaEstado("0");
				asignatura.setAsiInstitucion(Long.parseLong(usuVO.getInstId()));
				asignatura.setAsiVigencia((int) planDeEstudiosDAO
						.getVigenciaInst(asignatura.getAsiInstitucion()));
				session.setAttribute("asignaturaPlanVO", asignatura);
			}
			if (asignatura.getFormaEstado().equals("1")) {
				// los grados pero con los checkeados propios
				request.setAttribute("listaGrado", planDeEstudiosDAO
						.getListaGradoAsignaturaBase(
								asignatura.getAsiInstitucion(),
								asignatura.getAsiMetodologia(),
								asignatura.getAsiVigencia(),
								asignatura.getAsiArea(),
								asignatura.getAsiCodigo()));
			} else {
				// la lista entera sin checkeados para dejar invisibles y
				// esperar el evento de metodologia ajax
				request.setAttribute("listaGrado",
						planDeEstudiosDAO.getListaGradoTotal());
			}
			if (filtro == null || filtro.getFilInstitucion() == 0) {
				filtro = new FiltroAsignaturaVO();
				filtro.setFilInstitucion(Long.parseLong(usuVO.getInstId()));
				filtro.setFilVigencia((int) 0);
				session.setAttribute("filtroAsignaturaPlanVO", filtro);
			}
			if (filtro.getFilMetodologia() > 0) {
				request.setAttribute(
						"listaAreaBase0",
						planDeEstudiosDAO.getListaArea(
								filtro.getFilInstitucion(),
								filtro.getFilMetodologia(),
								filtro.getFilVigencia()));
			}
			if (asignatura.getAsiMetodologia() > 0) {
				// son las asignaturas del sistema, sin ajax
				request.setAttribute(
						"listaAreaBase",
						planDeEstudiosDAO.getListaArea(
								asignatura.getAsiInstitucion(),
								asignatura.getAsiMetodologia(),
								asignatura.getAsiVigencia()));
				if (asignatura.getAsiArea() > 0) {
					request.setAttribute("listaAsignaturaBase",
							planDeEstudiosDAO.getListaAsignaturaBase(asignatura
									.getAsiArea()));
				}
			}
			// los que siempre van
			// metodologia
			request.setAttribute("listaMetodologia", planDeEstudiosDAO
					.getListaMetodologia(filtro.getFilInstitucion()));
			request.setAttribute("listaVigencia", planDeEstudiosDAO
					.getListaVigenciaInst(filtro.getFilInstitucion()));
		} catch (Exception e) {
			throw new ServletException("Error interno: " + e.getMessage());
		}
	}

	public void asignaturaNuevo(HttpServletRequest request,
			HttpSession session, Login usuVO, AsignaturaVO asignatura)
			throws ServletException {
		session.removeAttribute("asignaturaPlanVO");
	}

}
