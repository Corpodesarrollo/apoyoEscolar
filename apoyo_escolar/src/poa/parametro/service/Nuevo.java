/**
 * 
 */
package poa.parametro.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;

import poa.parametro.dao.ParametroDAO;
import poa.parametro.vo.AreaGestionVO;
import poa.parametro.vo.FiltroLineaAccionVO;
import poa.parametro.vo.FuenteFinanciacionVO;
import poa.parametro.vo.LineaAccionVO;
import poa.parametro.vo.ParamsVO;
import poa.parametro.vo.ProgramacionFechasVO;
import poa.parametro.vo.TipoMetaVO;
import poa.parametro.vo.UnidadMedidaVO;
import poa.parametro.vo.VigenciaPoaVO;
import siges.common.service.Service;
import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.login.beans.Login;

/**
 * 14/02/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class Nuevo  extends Service{
	private static final long serialVersionUID = 6280013753285143307L;	
	public String FICHA_AREA_GESTION;
	public String FICHA_LINEA_ACCION;
	public String FICHA_FUENTE_FINANCIACION;
	public String FICHA_TIPO_META;
	public String FICHA_UNIDAD_MEDIDA;
	public String FICHA_PROGRAMACION_FECHAS;
	public String FICHA_VIG;
	
	private ParametroDAO parametroDAO=new ParametroDAO(new Cursor());
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		FICHA_AREA_GESTION=config.getInitParameter("FICHA_AREA_GESTION");
		FICHA_LINEA_ACCION=config.getInitParameter("FICHA_LINEA_ACCION");
		FICHA_FUENTE_FINANCIACION=config.getInitParameter("FICHA_FUENTE_FINANCIACION");
		FICHA_TIPO_META=config.getInitParameter("FICHA_TIPO_META");
		FICHA_UNIDAD_MEDIDA=config.getInitParameter("FICHA_UNIDAD_MEDIDA");
		FICHA_PROGRAMACION_FECHAS=config.getInitParameter("FICHA_PROGRAMACION_FECHAS");
		FICHA_VIG=config.getInitParameter("FICHA_VIG");
	}
	
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String FICHA=null;
		HttpSession session = request.getSession();
		String dispatcher[]=new String[2];
		int CMD=getCmd(request,session,ParamsVO.CMD_NUEVO);
		int TIPO=getTipo(request,session,ParamsVO.FICHA_DEFAULT);
		Login usuVO = (Login) session.getAttribute("login");
		
		switch (TIPO){
			case ParamsVO.FICHA_AREA_GESTION:
				FICHA=FICHA_AREA_GESTION;
				AreaGestionVO areaGestion=(AreaGestionVO)session.getAttribute("parametroAreaGestion");
				switch (CMD){
					case ParamsVO.CMD_NUEVO:
						areaGestionNuevo(request, session, usuVO,areaGestion);
						break;
					case ParamsVO.CMD_EDITAR:
						areaGestion=areaGestionEditar(request, session, usuVO);
						break;
					case ParamsVO.CMD_ELIMINAR:
						areaGestionEliminar(request, session, usuVO,areaGestion);
						break;
					case ParamsVO.CMD_GUARDAR:
						areaGestionGuardar(request, session, usuVO,areaGestion);
						break;
				}
				areaGestionInit(request, session, usuVO,areaGestion);
			break;
			case ParamsVO.FICHA_FUENTE_FINANCIACION:
				FICHA=FICHA_FUENTE_FINANCIACION;
				FuenteFinanciacionVO fuenteFinanciacion=(FuenteFinanciacionVO)session.getAttribute("parametroFuenteFinanciacion");
				switch (CMD){
					case ParamsVO.CMD_NUEVO:
						fuenteFinanciacionNuevo(request, session, usuVO,fuenteFinanciacion);
						break;
					case ParamsVO.CMD_EDITAR:
						fuenteFinanciacion=fuenteFinanciacionEditar(request, session, usuVO);
						break;
					case ParamsVO.CMD_ELIMINAR:
						fuenteFinanciacionEliminar(request, session, usuVO,fuenteFinanciacion);
						break;
					case ParamsVO.CMD_GUARDAR:
						fuenteFinanciacionGuardar(request, session, usuVO,fuenteFinanciacion);
						break;
				}
				fuenteFinanciacionInit(request, session, usuVO,fuenteFinanciacion);
			break;
			case ParamsVO.FICHA_TIPO_META:
				FICHA=FICHA_TIPO_META;
				TipoMetaVO tipoMeta=(TipoMetaVO)session.getAttribute("parametroTipoMeta");
				switch (CMD){
					case ParamsVO.CMD_NUEVO:
						tipoMetaNuevo(request, session, usuVO,tipoMeta);
						break;
					case ParamsVO.CMD_EDITAR:
						tipoMeta=tipoMetaEditar(request, session, usuVO);
						break;
					case ParamsVO.CMD_ELIMINAR:
						tipoMetaEliminar(request, session, usuVO,tipoMeta);
						break;
					case ParamsVO.CMD_GUARDAR:
						tipoMetaGuardar(request, session, usuVO,tipoMeta);
						break;
				}
				tipoMetaInit(request, session, usuVO,tipoMeta);
			break;
			case ParamsVO.FICHA_UNIDAD_MEDIDA:
				FICHA=FICHA_UNIDAD_MEDIDA;
				UnidadMedidaVO unidadMedida=(UnidadMedidaVO)session.getAttribute("parametroUnidadMedida");
				switch (CMD){
					case ParamsVO.CMD_NUEVO:
						unidadMedidaNuevo(request, session, usuVO,unidadMedida);
						break;
					case ParamsVO.CMD_EDITAR:
						unidadMedida=unidadMedidaEditar(request, session, usuVO);
						break;
					case ParamsVO.CMD_ELIMINAR:
						unidadMedidaEliminar(request, session, usuVO,unidadMedida);
						break;
					case ParamsVO.CMD_GUARDAR:
						unidadMedidaGuardar(request, session, usuVO,unidadMedida);
						break;
				}
				unidadMedidaInit(request, session, usuVO,unidadMedida);
			break;
			case ParamsVO.FICHA_LINEA_ACCION:
				FICHA=FICHA_LINEA_ACCION;
				LineaAccionVO lineaAccion=(LineaAccionVO)session.getAttribute("parametroLineaAccion");
				FiltroLineaAccionVO filtroLineaAccion=(FiltroLineaAccionVO)session.getAttribute("parametroFiltroLineaAccion");
				switch (CMD){
					case ParamsVO.CMD_NUEVO:
						lineaAccionNuevo(request, session, usuVO,lineaAccion);
						break;
					case ParamsVO.CMD_BUSCAR:
						lineaAccionBuscar(request, session, usuVO,filtroLineaAccion);
						break;
					case ParamsVO.CMD_EDITAR:
						lineaAccion=lineaAccionEditar(request, session, usuVO);
						lineaAccionBuscar(request, session, usuVO,filtroLineaAccion);
						break;
					case ParamsVO.CMD_ELIMINAR:
						lineaAccionEliminar(request, session, usuVO,lineaAccion);
						lineaAccionBuscar(request, session, usuVO,filtroLineaAccion);
						break;
					case ParamsVO.CMD_GUARDAR:
						lineaAccionGuardar(request, session, usuVO,lineaAccion);
						lineaAccionBuscar(request, session, usuVO,filtroLineaAccion);
						break;
				}
				lineaAccionInit(request, session, usuVO,lineaAccion);
			break;
			case ParamsVO.FICHA_PROGRAMACION_FECHAS:
				FICHA=FICHA_PROGRAMACION_FECHAS;
				ProgramacionFechasVO programacionFechas=(ProgramacionFechasVO)session.getAttribute("parametroProgramacionFechas");
				switch (CMD){
					case ParamsVO.CMD_NUEVO:
						programacionFechasNuevo(request, session, usuVO,programacionFechas);
						break;
					case ParamsVO.CMD_EDITAR:
						programacionFechas=programacionFechasEditar(request, session, usuVO);
						break;
					case ParamsVO.CMD_ELIMINAR:
						programacionFechasEliminar(request, session, usuVO,programacionFechas);
						break;
					case ParamsVO.CMD_GUARDAR:
						programacionFechasGuardar(request, session, usuVO,programacionFechas);
						break;
				}
				programacionFechasInit(request, session, usuVO,programacionFechas);
			break;
			case ParamsVO.FICHA_VIG:
				FICHA = FICHA_VIG;
				session.removeAttribute("filtroSeguimientoVO");
				session.removeAttribute("filtroPlaneacionSinVO");
				session.removeAttribute("filtroPlaneacionVO");
				session.removeAttribute("planeacionVO");
				session.removeAttribute("planeacionSinVO");
				
				
				switch (CMD){
					case ParamsVO.CMD_GUARDAR:
						vigenciaGuardar(request);
						break;
				}
				vigenciaNuevo(request);
			break;
		}
		dispatcher[0]=String.valueOf(ParamsVO.INCLUDE);
		dispatcher[1]=FICHA;
		return dispatcher;
	}
	

	public void areaGestionNuevo(HttpServletRequest request, HttpSession session, Login usuVO,AreaGestionVO areaGestion) throws ServletException {
		session.removeAttribute("parametroAreaGestion");
	}

	public void fuenteFinanciacionNuevo(HttpServletRequest request, HttpSession session, Login usuVO,FuenteFinanciacionVO fuente) throws ServletException {
		session.removeAttribute("parametroFuenteFinanciacion");
	}
	
	public void tipoMetaNuevo(HttpServletRequest request, HttpSession session, Login usuVO,TipoMetaVO tipoMeta) throws ServletException {
		session.removeAttribute("parametroTipoMeta");
	}

	public void unidadMedidaNuevo(HttpServletRequest request, HttpSession session, Login usuVO,UnidadMedidaVO unidadMedida) throws ServletException {
		session.removeAttribute("parametroUnidadMedida");
	}
	
	public void lineaAccionNuevo(HttpServletRequest request, HttpSession session, Login usuVO,LineaAccionVO lineaAccion) throws ServletException {
		session.removeAttribute("parametroLineaAccion");
	}
	
	public void areaGestionInit(HttpServletRequest request, HttpSession session, Login usuVO,AreaGestionVO area) throws ServletException {
		try {
			request.setAttribute("listaAreaGestion", parametroDAO.getListaAreaGestion());
		} catch (Exception e) {
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void fuenteFinanciacionInit(HttpServletRequest request, HttpSession session, Login usuVO,FuenteFinanciacionVO fuente) throws ServletException {
		try {
			request.setAttribute("listaFuenteFinanciacion", parametroDAO.getListaFuenteFinanciacion());
		} catch (Exception e) {
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	public void tipoMetaInit(HttpServletRequest request, HttpSession session, Login usuVO,TipoMetaVO tipo) throws ServletException {
		try {
			request.setAttribute("listaTipoMeta", parametroDAO.getListaTipoMeta());
		} catch (Exception e) {
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	public void unidadMedidaInit(HttpServletRequest request, HttpSession session, Login usuVO,UnidadMedidaVO unidad) throws ServletException {
		try {
			request.setAttribute("listaUnidadMedida", parametroDAO.getListaUnidadMedida());
		} catch (Exception e) {
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	public void lineaAccionInit(HttpServletRequest request, HttpSession session, Login usuVO,LineaAccionVO linea) throws ServletException {
		try {
			request.setAttribute("listaAreaGestion", parametroDAO.getListaAreaGestion());
		} catch (Exception e) {
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	public void lineaAccionBuscar(HttpServletRequest request, HttpSession session, Login usuVO,FiltroLineaAccionVO filtro) throws ServletException {
		try {
			request.setAttribute("listaLineaAccion", parametroDAO.getListaLineaAccion(filtro.getFilAreaGestion()));
		} catch (Exception e) {
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	public AreaGestionVO areaGestionEditar(HttpServletRequest request, HttpSession session, Login usuVO) throws ServletException {
		AreaGestionVO areaGestion=null;
		try {
			String params[]=request.getParameterValues("id");
			if(params==null || params.length<1 || params[0]==null) throw new ServletException("datos insuficientes");
			areaGestion=parametroDAO.getAreaGestion(Integer.parseInt(params[0]));
			session.setAttribute("parametroAreaGestion", areaGestion);
		} catch (Exception e) {
			request.setAttribute(ParamsVO.SMS, "El area de gestinn no pudo ser obtenido: "+e.getMessage());		
		}
		return areaGestion;
	}

	public FuenteFinanciacionVO fuenteFinanciacionEditar(HttpServletRequest request, HttpSession session, Login usuVO) throws ServletException {
		FuenteFinanciacionVO fuenteFinanciacion=null;
		try {
			String params[]=request.getParameterValues("id");
			if(params==null || params.length<1 || params[0]==null) throw new ServletException("datos insuficientes");
			fuenteFinanciacion=parametroDAO.getFuenteFinanciacion(Integer.parseInt(params[0]));
			session.setAttribute("parametroFuenteFinanciacion", fuenteFinanciacion);
		} catch (Exception e) {
			request.setAttribute(ParamsVO.SMS, "La fuente de financiación no pudo ser obtenida: "+e.getMessage());		
		}
		return fuenteFinanciacion;
	}
	
	public TipoMetaVO tipoMetaEditar(HttpServletRequest request, HttpSession session, Login usuVO) throws ServletException {
		TipoMetaVO tipoMeta=null;
		try {
			String params[]=request.getParameterValues("id");
			if(params==null || params.length<1 || params[0]==null) throw new ServletException("datos insuficientes");
			tipoMeta=parametroDAO.getTipoMeta(Integer.parseInt(params[0]));
			session.setAttribute("parametroTipoMeta", tipoMeta);
		} catch (Exception e) {
			request.setAttribute(ParamsVO.SMS, "El tipo de meta no pudo ser obtenido: "+e.getMessage());		
		}
		return tipoMeta;
	}
	
	public UnidadMedidaVO unidadMedidaEditar(HttpServletRequest request, HttpSession session, Login usuVO) throws ServletException {
		UnidadMedidaVO unidadMedida=null;
		try {
			String params[]=request.getParameterValues("id");
			if(params==null || params.length<1 || params[0]==null) throw new ServletException("datos insuficientes");
			unidadMedida=parametroDAO.getUnidadMedida(Integer.parseInt(params[0]));
			session.setAttribute("parametroUnidadMedida", unidadMedida);
		} catch (Exception e) {
			request.setAttribute(ParamsVO.SMS, "La unidad de medida no pudo ser obtenida: "+e.getMessage());		
		}
		return unidadMedida;
	}
	
	public LineaAccionVO lineaAccionEditar(HttpServletRequest request, HttpSession session, Login usuVO) throws ServletException {
		LineaAccionVO lineaAccion=null;
		try {
			String params[]=request.getParameterValues("id");
			if(params==null || params.length<2 || params[0]==null || params[1]==null) throw new ServletException("datos insuficientes");
			lineaAccion=parametroDAO.getLineaAccion(Integer.parseInt(params[0]),Integer.parseInt(params[1]));
			session.setAttribute("parametroLineaAccion", lineaAccion);
		} catch (Exception e) {
			request.setAttribute(ParamsVO.SMS, "La línea de acción no pudo ser obtenida: "+e.getMessage());		
		}
		return lineaAccion;
	}
	
	public void areaGestionEliminar(HttpServletRequest request, HttpSession session, Login usuVO,AreaGestionVO area) throws ServletException {
		try {
			String params[]=request.getParameterValues("id");
			if(params==null || params.length<1 || params[0]==null) throw new ServletException("datos insuficientes");
			parametroDAO.eliminarAreaGestion(Integer.parseInt(params[0]));
			session.removeAttribute("parametroAreaGestion");
			request.setAttribute(ParamsVO.SMS, "El área de Gestinn fue eliminada satisfactoriamente");
		} catch (Exception e) {
			request.setAttribute(ParamsVO.SMS, "El área de Gestinn no fue eliminado: "+e.getMessage());		
		}
	}
	
	public void fuenteFinanciacionEliminar(HttpServletRequest request, HttpSession session, Login usuVO,FuenteFinanciacionVO fuenteFinanciacion) throws ServletException {
		try {
			String params[]=request.getParameterValues("id");
			if(params==null || params.length<1 || params[0]==null) throw new ServletException("datos insuficientes");
			parametroDAO.eliminarFuenteFinanciacion(Integer.parseInt(params[0]));
			session.removeAttribute("parametroFuenteFinanciacion");
			request.setAttribute(ParamsVO.SMS, "La fuente de financiación fue eliminada satisfactoriamente");
		} catch (Exception e) {
			request.setAttribute(ParamsVO.SMS, "La fuente de financiación no fue eliminada: "+e.getMessage());		
		}
	}
	
	public void tipoMetaEliminar(HttpServletRequest request, HttpSession session, Login usuVO,TipoMetaVO tipoMeta) throws ServletException {
		try {
			String params[]=request.getParameterValues("id");
			if(params==null || params.length<1 || params[0]==null) throw new ServletException("datos insuficientes");
			parametroDAO.eliminarTipoMeta(Integer.parseInt(params[0]));
			session.removeAttribute("parametroTipoMeta");
			request.setAttribute(ParamsVO.SMS, "El tipo de meta fue eliminado satisfactoriamente");
		} catch (Exception e) {
			request.setAttribute(ParamsVO.SMS, "El tipo de meta no fue eliminado: "+e.getMessage());		
		}
	}
	
	public void unidadMedidaEliminar(HttpServletRequest request, HttpSession session, Login usuVO,UnidadMedidaVO unidadMedida) throws ServletException {
		try {
			String params[]=request.getParameterValues("id");
			if(params==null || params.length<1 || params[0]==null) throw new ServletException("datos insuficientes");
			parametroDAO.eliminarUnidadMedida(Integer.parseInt(params[0]));
			session.removeAttribute("parametroUnidadMedida");
			request.setAttribute(ParamsVO.SMS, "La unidad de medida fue eliminada satisfactoriamente");
		} catch (Exception e) {
			request.setAttribute(ParamsVO.SMS, "La unidad de medida no fue eliminada: "+e.getMessage());		
		}
	}
	
	public void lineaAccionEliminar(HttpServletRequest request, HttpSession session, Login usuVO,LineaAccionVO lineaAccion) throws ServletException {
		try {
			String params[]=request.getParameterValues("id");
			if(params==null || params.length<2 || params[0]==null || params[1]==null) throw new ServletException("datos insuficientes");
			parametroDAO.eliminarLineaAccion(Integer.parseInt(params[0]),Integer.parseInt(params[1]));
			session.removeAttribute("parametroLineaAccion");
			request.setAttribute(ParamsVO.SMS, "La lnnea de acción fue eliminada satisfactoriamente");
		} catch (Exception e) {
			request.setAttribute(ParamsVO.SMS, "La línea de acción no fue eliminada: "+e.getMessage());		
		}
	}
	
	public void areaGestionGuardar(HttpServletRequest request, HttpSession session, Login usuVO,AreaGestionVO areaGestion) throws ServletException {
		try {
			if(areaGestion.getFormaEstado().equals("1")){
				parametroDAO.actualizarAreaGestion(areaGestion);
			}else{
				parametroDAO.ingresarAreaGestion(areaGestion);
			}
			session.removeAttribute("parametroAreaGestion");
			request.setAttribute(ParamsVO.SMS, "El área de Gestión fue ingresado/actualizado satisfactoriamente");
		} catch (Exception e) {
			request.setAttribute(ParamsVO.SMS, "El área de Gestinn no fue ingresado/actualizado: "+e.getMessage());
		}
	}
	
	public void fuenteFinanciacionGuardar(HttpServletRequest request, HttpSession session, Login usuVO,FuenteFinanciacionVO fuenteFinanciacion) throws ServletException {
		try {
			if(fuenteFinanciacion.getFormaEstado().equals("1")){
				parametroDAO.actualizarFuenteFinanciacion(fuenteFinanciacion);
			}else{
				parametroDAO.ingresarFuenteFinanciacion(fuenteFinanciacion);
			}
			session.removeAttribute("parametroFuenteFinanciacion");
			request.setAttribute(ParamsVO.SMS, "La Fuente de Financiación fue ingresado/actualizado satisfactoriamente");
		} catch (Exception e) {
			request.setAttribute(ParamsVO.SMS, "La Fuente de Financiación no fue ingresado/actualizado: "+e.getMessage());
		}
	}

	public void tipoMetaGuardar(HttpServletRequest request, HttpSession session, Login usuVO,TipoMetaVO tipoMeta) throws ServletException {
		try {
			if(tipoMeta.getFormaEstado().equals("1")){
				parametroDAO.actualizarTipoMeta(tipoMeta);
			}else{
				parametroDAO.ingresarTipoMeta(tipoMeta);
			}
			session.removeAttribute("parametroTipoMeta");
			request.setAttribute(ParamsVO.SMS, "El tipo de meta fue ingresado/actualizado satisfactoriamente");
		} catch (Exception e) {
			request.setAttribute(ParamsVO.SMS, "El tipo de meta no fue ingresado/actualizado: "+e.getMessage());
		}
	}

	public void unidadMedidaGuardar(HttpServletRequest request, HttpSession session, Login usuVO,UnidadMedidaVO unidadMedida) throws ServletException {
		try {
			if(unidadMedida.getFormaEstado().equals("1")){
				parametroDAO.actualizarUnidadMedida(unidadMedida);
			}else{
				parametroDAO.ingresarUnidadMedida(unidadMedida);
			}
			session.removeAttribute("parametroUnidadMedida");
			request.setAttribute(ParamsVO.SMS, "La unidad de medida fue ingresado/actualizado satisfactoriamente");
		} catch (Exception e) {
			request.setAttribute(ParamsVO.SMS, "La unidad de medida no fue ingresado/actualizado: "+e.getMessage());
		}
	}

	public void lineaAccionGuardar(HttpServletRequest request, HttpSession session, Login usuVO,LineaAccionVO lineaAccion) throws ServletException {
		try {
			if(lineaAccion.getFormaEstado().equals("1")){
				parametroDAO.actualizarLineaAccion(lineaAccion);
			}else{
				parametroDAO.ingresarLineaAccion(lineaAccion);
			}
			session.removeAttribute("parametroLineaAccion");
			request.setAttribute(ParamsVO.SMS, "La línea de acción fue ingresado/actualizado satisfactoriamente");
		} catch (Exception e) {e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "La línea de acción no fue ingresado/actualizado: "+e.getMessage());
		}
	}

	public void programacionFechasInit(HttpServletRequest request, HttpSession session, Login usuVO,ProgramacionFechasVO programacionFechas) throws ServletException {
		try {
			request.setAttribute("listaProgramacionFechas", parametroDAO.getListaProgramacionFechas());
			int vigPoa = parametroDAO.getVigenciaPOA();
			List lista = new ArrayList();
		 	for (int i = vigPoa - 4; i <= vigPoa + 1; i++) {
		 		lista.add(new ItemVO(i,i+""));
			}
		 
		 	request.setAttribute("listaVigencia_POA", lista);
		} catch (Exception e) {
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	public void programacionFechasNuevo(HttpServletRequest request, HttpSession session, Login usuVO,ProgramacionFechasVO programacionFechas) throws ServletException {
		session.removeAttribute("parametroProgramacionFechas");
	}

	public ProgramacionFechasVO programacionFechasEditar(HttpServletRequest request, HttpSession session, Login usuVO) throws ServletException {
		ProgramacionFechasVO programacionFechasVO=null;
		try {
			String params[]=request.getParameterValues("id");
			if(params==null || params.length<1 || params[0]==null) throw new ServletException("datos insuficientes");
			programacionFechasVO=parametroDAO.getProgramacionFechas(Integer.parseInt(params[0]));
			session.setAttribute("parametroProgramacionFechas", programacionFechasVO);
		} catch (Exception e) {
			request.setAttribute(ParamsVO.SMS, "La programación de fechas no pudo ser obtenida: "+e.getMessage());		
		}
		return programacionFechasVO;
	}

	public void programacionFechasEliminar(HttpServletRequest request, HttpSession session, Login usuVO,ProgramacionFechasVO programacionFechas) throws ServletException {
		try {
			String params[]=request.getParameterValues("id");
			if(params==null || params.length<1 || params[0]==null) throw new ServletException("datos insuficientes");
			parametroDAO.eliminarProgramacionFechas(Integer.parseInt(params[0]));
			session.removeAttribute("parametroProgramacionFechas");
			request.setAttribute(ParamsVO.SMS, "La programación de fechas fue eliminada satisfactoriamente");
		} catch (Exception e) {
			request.setAttribute(ParamsVO.SMS, "La programación de fechas no fue eliminada: "+e.getMessage());		
		}
	}

	public void programacionFechasGuardar(HttpServletRequest request, HttpSession session, Login usuVO,ProgramacionFechasVO programacionFechas) throws ServletException {
		try {
			if(programacionFechas.getFormaEstado().equals("1")){
				parametroDAO.actualizarProgramacionFechas(programacionFechas);
			}else{
				parametroDAO.ingresarProgramacionFechas(programacionFechas);
			}
			session.removeAttribute("parametroProgramacionFechas");
			request.setAttribute(ParamsVO.SMS, "La programaci�n de fechas fue ingresada / actualizada satisfactoriamente");
		} catch (Exception e) {
			request.setAttribute(ParamsVO.SMS, "La programaci�n de fechas no fue ingresada / actualizada: " + e.getMessage());
		}
	}
	

	public void vigenciaGuardar(HttpServletRequest request) throws ServletException {
		try {
			String vig = (String)request.getParameter("vigenciaPoa");
			
			if(vig !=null && GenericValidator.isInt(vig)){
				parametroDAO.guardarVigenciaPOA(Integer.valueOf(vig).intValue());
			} 
			 request.setAttribute(ParamsVO.SMS, "La vigencia de POA fue ingresada/actualizada satisfactoriamente");
		} catch (Exception e) {
			request.setAttribute(ParamsVO.SMS, "La programación de fechas no fue ingresada/actualizada: "+e.getMessage());
		}
	}
	
	public void vigenciaNuevo(HttpServletRequest request) throws ServletException {
		try {
			
			
			VigenciaPoaVO vigenciaPoaVO = new VigenciaPoaVO(parametroDAO.getVigenciaPOA());
			request.getSession().setAttribute("vigenciaPoaVO",vigenciaPoaVO);
			request.setAttribute("listaVigencias",parametroDAO.listaVigencia());
			 
		} catch (Exception e) {
			request.setAttribute(ParamsVO.SMS, "Obteniendo vigencia de POA: "+e.getMessage());
		}
	}
	
}
