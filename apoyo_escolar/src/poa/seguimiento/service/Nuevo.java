package poa.seguimiento.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import siges.dao.Cursor;
import siges.util.Logger;
import siges.common.vo.Params;
import siges.login.beans.Login;
import poa.seguimiento.vo.ParamsVO;
import poa.seguimiento.vo.SegFechasCompareVO;
import siges.common.service.Service;
import poa.seguimiento.vo.SeguimientoVO;
import poa.seguimiento.dao.SeguimientoDAO;
import poa.seguimiento.vo.FiltroSeguimientoVO;
import siges.common.vo.ItemVO;


/**
 * Procesa las peticiones del formulario de ingreso/edicion de actividades
 * 14/01/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class Nuevo extends Service {
	
	private static final long serialVersionUID = 6340013753285143307L;
	
	/**
	 * Ruta de la pagina de ingreso/edicinn de actividad con recursos
	 */
	public String FICHA_ACTIVIDAD;
	
	/**
	 * Objeto de acceso a datos
	 */
	private SeguimientoDAO seguimientoDAO = new SeguimientoDAO(new Cursor());
	String codigoColegio;
	String codigoSel;
	List<ItemVO> listaUnidadesMedida = new ArrayList<>();
	
	List listaArchivosSeguimiento1;
	List listaArchivosSeguimiento2;
	List listaArchivosSeguimiento3;
	List listaArchivosSeguimiento4;
	
	List<SeguimientoVO> listaActividades = new ArrayList<>();

	/*
	 *  Proceso de la peticinn
	 * @param request peticinn
	 * @param response respuesta
	 * @return Ruta de redireccinn
	 * @throws ServletException
	 * @throws IOException
	 * (non-Javadoc)
	 * @see siges.common.service.Service#process(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public String[] process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String FICHA = null;
		String dispatcher[] = new String[2];
		
		HttpSession session = request.getSession();
		
		FICHA_ACTIVIDAD = config.getInitParameter("FICHA_ACTIVIDAD");
		
		int CMD = getCmd(request, session);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		
		Login usuVO = (Login)session.getAttribute("login");
		SeguimientoVO seguimiento = (SeguimientoVO)session.getAttribute("seguimientoVO");
		FiltroSeguimientoVO filtro = (FiltroSeguimientoVO)session.getAttribute("filtroSeguimientoVO");
		
		switch (TIPO) {
			case ParamsVO.FICHA_ACTIVIDAD:
			
				FICHA = FICHA_ACTIVIDAD;
				
				switch (CMD) {
					case ParamsVO.CMD_NUEVO:
					
						session.removeAttribute("seguimientoVO");
						session.removeAttribute("filtroSeguimientoVO");
					
						planeacionBuscar(request, session, usuVO, filtro);
					
						break;
						
					case ParamsVO.CMD_EDITAR:
						
						planeacionEditar(request, session, usuVO, filtro);
						
						if(filtro.getFilRangoFechas2() != null)
							request.setAttribute(ParamsVO.SMS, "Trimestre a registrar: " + filtro.getFilRangoFechas2());
						else
							request.setAttribute(ParamsVO.SMS, "No hay trimestres a registrar.");
					
						planeacionBuscar(request, session, usuVO, filtro);
						seguimiento = (SeguimientoVO)session.getAttribute("seguimientoVO");
						seguimiento = indicarAlerta(seguimiento, filtro);
						
						listarArchivosTrimestres(request, session, usuVO, filtro);
					
						break;
					
					case ParamsVO.CMD_BUSCAR:
						
						planeacionBuscar(request, session, usuVO, filtro);
						
						break;
						
					case ParamsVO.CMD_GUARDAR:
						
						planeacionGuardar(request, session, usuVO, filtro, seguimiento);
						planeacionBuscar(request, session, usuVO, filtro);
						listarArchivosTrimestres(request, session, usuVO, filtro);
						
						break;
						
				}
				
				planeacionNuevo(request, session, usuVO, seguimiento);
				break;
				
		}
		
		dispatcher[0] = String.valueOf(Params.INCLUDE);
		dispatcher[1] = FICHA;
			
		return dispatcher;
	}
	

	private SeguimientoVO indicarAlerta(SeguimientoVO seguimiento, FiltroSeguimientoVO filtro){
		
		if(seguimiento != null){
			
			//seguimiento.setRojoSeguimiento1(false);
			seguimiento.setRojoNaranjaSeguimiento1(0);
			seguimiento.setRojoNaranjaSeguimiento2(0);
			seguimiento.setRojoNaranjaSeguimiento3(0);
			seguimiento.setRojoNaranjaSeguimiento4(0);	
//			seguimiento.setPlaPorcentaje1("800");
//			seguimiento.setPlaPorcentaje2("777");
//			seguimiento.setPlaCronograma3("0");
//			seguimiento.setPlaCronograma1("555");
			
//			seguimiento.setPlaSeguimiento3("0");
//			
			if(seguimiento.getPlaTipoMeta() > 0 
					&& seguimiento.getPlaTipoMeta() == 1){ // Para SUMATORIA
				
				if(seguimiento.getPlaSeguimiento1() != null && seguimiento.getPlaCronograma1() != null &&
						(Integer.parseInt(seguimiento.getPlaSeguimiento1().trim()) < Integer.parseInt(seguimiento.getPlaCronograma1()) )){
					//mostrar rojo
					seguimiento.setRojoNaranjaSeguimiento1(1);
					
				} else if(seguimiento.getPlaSeguimiento1() != null && seguimiento.getPlaCronograma1() != null &&
						(Integer.parseInt(seguimiento.getPlaSeguimiento1().trim()) > Integer.parseInt(seguimiento.getPlaCronograma1()) )){
					//mostrar naranja
					seguimiento.setRojoNaranjaSeguimiento1(2);
					
				}
				if(seguimiento.getPlaSeguimiento2() != null && seguimiento.getPlaCronograma2() != null && 
						(Integer.parseInt(seguimiento.getPlaSeguimiento2().trim()) < Integer.parseInt(seguimiento.getPlaCronograma2()))){
					//mostrar rojo
					seguimiento.setRojoNaranjaSeguimiento2(1);
					
				} else if(seguimiento.getPlaSeguimiento2() != null && seguimiento.getPlaCronograma2() != null && 
						(Integer.parseInt(seguimiento.getPlaSeguimiento2().trim()) > Integer.parseInt(seguimiento.getPlaCronograma2()))){
					//mostrar naranja
					seguimiento.setRojoNaranjaSeguimiento2(2);
					
				}
				if(seguimiento.getPlaSeguimiento3() != null && seguimiento.getPlaCronograma3() != null && 
						(Integer.parseInt(seguimiento.getPlaSeguimiento3().trim()) < Integer.parseInt(seguimiento.getPlaCronograma3()))){
					//mostrar rojo
					seguimiento.setRojoNaranjaSeguimiento3(1);
					
				} else if(seguimiento.getPlaSeguimiento3() != null && seguimiento.getPlaCronograma3() != null && 
						(Integer.parseInt(seguimiento.getPlaSeguimiento3().trim()) > Integer.parseInt(seguimiento.getPlaCronograma3()))){
					//mostrar naranja
					seguimiento.setRojoNaranjaSeguimiento3(2);
					
				}
				if(seguimiento.getPlaSeguimiento4() != null && seguimiento.getPlaCronograma4() != null && 
						(Integer.parseInt(seguimiento.getPlaSeguimiento4().trim()) < Integer.parseInt(seguimiento.getPlaCronograma4()))){
					//mostrar rojo
					seguimiento.setRojoNaranjaSeguimiento4(1);
					
				} else if(seguimiento.getPlaSeguimiento4() != null && seguimiento.getPlaCronograma4() != null && 
						(Integer.parseInt(seguimiento.getPlaSeguimiento4().trim()) > Integer.parseInt(seguimiento.getPlaCronograma4()))){
					//mostrar naranja
					seguimiento.setRojoNaranjaSeguimiento4(2);
				}
				
				
			} else if(seguimiento.getPlaTipoMeta() > 0 
					&& seguimiento.getPlaTipoMeta() == 2){ // Para DEMANDA
				
				if(seguimiento.getPlaSeguimiento1() != null && seguimiento.getPlaPorcentaje1() != null && 
						(Integer.parseInt(seguimiento.getPlaSeguimiento1().trim()) < Integer.parseInt(seguimiento.getPlaPorcentaje1()))){
					//mostrar rojo
					seguimiento.setRojoNaranjaSeguimiento1(1);
					
				}
				if(seguimiento.getPlaSeguimiento2() != null && seguimiento.getPlaPorcentaje2() != null && 
						(Integer.parseInt(seguimiento.getPlaSeguimiento2().trim()) < Integer.parseInt(seguimiento.getPlaPorcentaje2()))){
					//mostrar rojo
					seguimiento.setRojoNaranjaSeguimiento2(1);
					
				}
				if(seguimiento.getPlaSeguimiento3() != null && seguimiento.getPlaPorcentaje3() != null && 
						(Integer.parseInt(seguimiento.getPlaSeguimiento3().trim()) < Integer.parseInt(seguimiento.getPlaPorcentaje3()))){
					//mostrar rojo
					seguimiento.setRojoNaranjaSeguimiento3(1);
					
				}
				if(seguimiento.getPlaSeguimiento4() != null && seguimiento.getPlaPorcentaje4() != null && 
						(Integer.parseInt(seguimiento.getPlaSeguimiento4().trim()) < Integer.parseInt(seguimiento.getPlaPorcentaje4()))){
					//mostrar rojo
					seguimiento.setRojoNaranjaSeguimiento4(1);
				}
				
			} else if(seguimiento.getPlaTipoMeta() > 0 
					&& seguimiento.getPlaTipoMeta() == 3){ // Para CONSTANTE
				
				if(seguimiento.getPlaSeguimiento1() != null && seguimiento.getPlaCronograma1() != null && 
						(Integer.parseInt(seguimiento.getPlaSeguimiento1().trim()) < Integer.parseInt(seguimiento.getPlaCronograma1()))){
					//mostrar rojo
					seguimiento.setRojoNaranjaSeguimiento1(1);
					
				} else if(seguimiento.getPlaSeguimiento1() != null && seguimiento.getPlaCronograma1() != null && 
						(Integer.parseInt(seguimiento.getPlaSeguimiento1().trim()) > Integer.parseInt(seguimiento.getPlaCronograma1()))){
					//mostrar naranja
					seguimiento.setRojoNaranjaSeguimiento1(2);
					
				}
				if(seguimiento.getPlaSeguimiento2() != null && seguimiento.getPlaCronograma2() != null && 
						(Integer.parseInt(seguimiento.getPlaSeguimiento2().trim()) < Integer.parseInt(seguimiento.getPlaCronograma2()))){
					//mostrar rojo
					seguimiento.setRojoNaranjaSeguimiento2(1);
					
				} else if(seguimiento.getPlaSeguimiento2() != null && seguimiento.getPlaCronograma2() != null && 
						(Integer.parseInt(seguimiento.getPlaSeguimiento2().trim()) > Integer.parseInt(seguimiento.getPlaCronograma2()))){
					//mostrar naranja
					seguimiento.setRojoNaranjaSeguimiento2(2);
					
				}
				if(seguimiento.getPlaSeguimiento3() != null && seguimiento.getPlaCronograma3() != null && 
						(Integer.parseInt(seguimiento.getPlaSeguimiento3().trim()) < Integer.parseInt(seguimiento.getPlaCronograma3()))){
					//mostrar rojo
					seguimiento.setRojoNaranjaSeguimiento3(1);
					
				} else if(seguimiento.getPlaSeguimiento3() != null && seguimiento.getPlaCronograma3() != null && 
						(Integer.parseInt(seguimiento.getPlaSeguimiento3().trim()) > Integer.parseInt(seguimiento.getPlaCronograma3()))){
					//mostrar naranja
					seguimiento.setRojoNaranjaSeguimiento3(2);
					
				}
				if(seguimiento.getPlaSeguimiento4() != null && seguimiento.getPlaCronograma4() != null && 
						(Integer.parseInt(seguimiento.getPlaSeguimiento4().trim()) < Integer.parseInt(seguimiento.getPlaCronograma4()))){
					//mostrar rojo
					seguimiento.setRojoNaranjaSeguimiento4(1);
					
				} else if(seguimiento.getPlaSeguimiento4() != null && seguimiento.getPlaCronograma4() != null && 
						(Integer.parseInt(seguimiento.getPlaSeguimiento4().trim()) > Integer.parseInt(seguimiento.getPlaCronograma4()))){
					//mostrar naranja
					seguimiento.setRojoNaranjaSeguimiento4(2);
					
				}
				
			}
		}
		
	try {
			SegFechasCompareVO fechasSegCompare= new SegFechasCompareVO();
			fechasSegCompare = seguimientoDAO.compararFechaFinSeg(filtro.getFilVigencia());
			//fechasSegCompare.isFechaCompareSeg1() true == 1, false == 0
			//true la fecha Actual del sistema es > (mayor) que la fecha fin trimestre.
			//false la fecha Actual del sistema es < (menor) que la fecha fin trimestre.
		
		
		 if(seguimiento.getPlaCronograma1() == null 
				 || seguimiento.getPlaCronograma1().equals("0")){
			 
			 if(fechasSegCompare != null && fechasSegCompare.isFechaCompareSeg1()){ 
					//poner el N/A
				 seguimiento.setCalifEvalSeg1("N/A");
				}	
						
		 }
		 if(seguimiento.getPlaCronograma2() == null 
				 || seguimiento.getPlaCronograma2().equals("0")){
			 
			 if(fechasSegCompare != null && fechasSegCompare.isFechaCompareSeg2()){ 
					//poner el N/A
				 seguimiento.setCalifEvalSeg2("N/A");
				}	
						
		 }
		 if(seguimiento.getPlaCronograma3() == null 
				 || seguimiento.getPlaCronograma3().equals("0")){
			 
			 if(fechasSegCompare != null && fechasSegCompare.isFechaCompareSeg3()){ 
					//poner el N/A
				 seguimiento.setCalifEvalSeg3("N/A");
				}	
						
		 }
		 if(seguimiento.getPlaCronograma4() == null 
				 || seguimiento.getPlaCronograma4().equals("0")){
			 
			 if(fechasSegCompare != null && fechasSegCompare.isFechaCompareSeg4()){ 
					//poner el N/A
				 seguimiento.setCalifEvalSeg4("N/A");
				}	
						
		 }
		 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return seguimiento;
		
		
	}
	

	/**
	 *   Inicializa los objetos de la página de seguimiento de actividad con recursos 
	 * @param request peticinn
	 * @param session sesinn de usuario
	 * @param usuVO Login de usuario
	 * @param planeacion Actividad
	 * @throws ServletException
	 */
	public void planeacionNuevo(HttpServletRequest request, HttpSession session, Login usuVO, SeguimientoVO planeacion) throws ServletException {
		
		try {
			
			request.setAttribute("listaVigencia", seguimientoDAO.getListaVigenciaActual());
			request.setAttribute("listaTipoMeta", seguimientoDAO.getListaTipoMeta());
			request.setAttribute("listaUnidadMedida", seguimientoDAO.getListaUnidadMedida());
			request.getSession().setAttribute("listaObjetivos", seguimientoDAO.getlistaObjetivos());
			listaUnidadesMedida = (List<ItemVO>)request.getAttribute("listaUnidadMedida");
			
			if(planeacion == null) {
				planeacion = new SeguimientoVO();
				planeacion.setPlaVigencia((int)seguimientoDAO.getVigenciaNumerico());
				planeacion.setPlaInstitucion(Long.parseLong(usuVO.getInstId()));
				session.setAttribute("seguimientoVO", planeacion);
			}
			
			if(planeacion.getPlaVigencia() != 0) {
				request.setAttribute("listaAreaGestion", seguimientoDAO.getListaAreaGestion(planeacion.getPlaVigencia(), planeacion.getPlaInstitucion(), planeacion.getPlaCodigo()));
			}
			
			if(planeacion.getPlaAreaGestion() != 0) {
				request.setAttribute("listaLineaAccion", seguimientoDAO.getListaLineaAccion(planeacion.getPlaAreaGestion()));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Error interno: " + e.getMessage());
		}
	}
	
	
	/**
	 *   Actualiza el seguimiento de una actividad con recursos
	 * @param request peticinn 
	 * @param session sesinn de usuario
	 * @param usuVO login de usuario
	 * @param planeacion Actividad
	 * @throws ServletException
	 */
	public void planeacionGuardar(HttpServletRequest request, HttpSession session, Login usuVO, FiltroSeguimientoVO filtro, SeguimientoVO planeacion) throws ServletException {
		
		try {
			
			String nombreUnidadMedida = "";
			boolean errorValidacion = false;
			SeguimientoVO seguimiento = (SeguimientoVO)session.getAttribute("seguimientoVO");
						
			if(seguimiento.getPlaUnidadMedida() == 49){ //Unidad de medida Otro
				nombreUnidadMedida = seguimiento.getPLACOTROCUAL();
				System.out.println("nombreUnidadMedida: " + nombreUnidadMedida);
			} else{
				for (ItemVO uniMedidaTemp: listaUnidadesMedida){
					//System.out.println("nombres: " + uniMedidaTemp.getNombre());
					if(seguimiento.getPlaUnidadMedida() == uniMedidaTemp.getCodigo()){
						
						nombreUnidadMedida = uniMedidaTemp.getNombre();
						System.out.println("nombreUnidadMedida: " + nombreUnidadMedida);
						break;
					}
					
					
				}
				
			}
			

			if(seguimiento.getPlaDisabled1() == null){ //si es null indica que el trimestre está habilitado. De lo contrario trae el string 'disabled'
				 if(seguimiento.getPlaCronograma1() != null && 
					!seguimiento.getPlaCronograma1().equals("0")){
					 
					 if(listaArchivosSeguimiento1 == null || listaArchivosSeguimiento1.size() == 0){
						 	errorValidacion = true;
							request.setAttribute(ParamsVO.SMS, "Su solicitud no puede ser atendida hasta que primero cargue un(a) " + nombreUnidadMedida);
						}
						
					} 
			} else if(seguimiento.getPlaDisabled2() == null){
				 if(seguimiento.getPlaCronograma2() != null && 
					!seguimiento.getPlaCronograma2().equals("0")){
					 
					 if(listaArchivosSeguimiento2 == null || listaArchivosSeguimiento2.size() == 0){
						 errorValidacion = true;	
						 request.setAttribute(ParamsVO.SMS, "Su solicitud no puede ser atendida hasta que primero cargue un(a) " + nombreUnidadMedida);
						}
						
					} 				
			} else if(seguimiento.getPlaDisabled3() == null){
				 if(seguimiento.getPlaCronograma3() != null && 
					!seguimiento.getPlaCronograma3().equals("0")){
					 
					 if(listaArchivosSeguimiento3 == null || listaArchivosSeguimiento3.size() == 0){
						 errorValidacion = true;	
						 request.setAttribute(ParamsVO.SMS, "Su solicitud no puede ser atendida hasta que primero cargue un(a) " + nombreUnidadMedida);
						}
						
					} 				
			} else if(seguimiento.getPlaDisabled4() == null){
				 if(seguimiento.getPlaCronograma4() != null && 
					!seguimiento.getPlaCronograma4().equals("0")){
					 
					 if(listaArchivosSeguimiento4 == null || listaArchivosSeguimiento4.size() == 0){
						 errorValidacion = true;	
						 request.setAttribute(ParamsVO.SMS, "Su solicitud no puede ser atendida hasta que primero cargue un(a) " + nombreUnidadMedida);
						}
						
					} 				
			}
			
		if(!errorValidacion){
			if(planeacion != null) {
				
				planeacion.setUsuarioSesion(usuVO.getUsuarioId());
				
				seguimientoDAO.actualizarActividad(planeacion, filtro);
				request.setAttribute(ParamsVO.SMS, "La actividad fue actualizada satisfactoriamente");
				Logger.print(usuVO.getUsuarioId(), "POA: Actualizacion de actividad con recursos. " + planeacion.getPlaVigencia() + ". " + planeacion.getPlaInstitucion() + ". " + planeacion.getPlaCodigo(), 7, 1, this.toString());
				request.setAttribute("buscaSeguimiento", "1");
			}
		 }
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "La actividad no fue ingresada / actualizada / enviada: " + e.getMessage());
		}
		
	}

	
	/**
	 * Calcula la lista de actividades sin recursos
	 * @param request peticinn
	 * @param session sesinn de usuario
	 * @param usuVO Login de usuario
	 * @param filtro Filtro de busqueda de actividades
	 * @throws ServletException
	 */
	public void planeacionBuscar(HttpServletRequest request, HttpSession session, Login usuVO, FiltroSeguimientoVO filtro) throws ServletException {
		
		try {
			
			Float califTotal = null;
			
			if(filtro != null && filtro.getFilVigencia() != 0) {
				request.setAttribute("listaActividades", seguimientoDAO.getListaActividades(filtro));
				
				listaActividades = (List<SeguimientoVO>) request.getAttribute("listaActividades");
				SeguimientoVO seguimiento = (SeguimientoVO)session.getAttribute("seguimientoVO");
				
				for (SeguimientoVO itemVO : listaActividades) {
					
					if(itemVO.getCalifActividad() != null){						
						if(califTotal == null){							
							califTotal = 0f;
						}						
						califTotal= califTotal+ Float.parseFloat(itemVO.getCalifActividad());
												
					} 
					if(califTotal == null){
						seguimiento.setCalifTotalActividad("");
					} else{
					seguimiento.setCalifTotalActividad(califTotal.toString());
					}
					
				}
				Logger.print(usuVO.getUsuarioId(), "POA: Buscar actividad con recursos. " + filtro.getFilInstitucion() + ". " + filtro.getFilVigencia(), 2, 1, this.toString());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "No se pudo obtener la lista de actividades: " + e.getMessage());
		}
	}

	
	/**
	 * Obtiene una actividad para hacerle seguimiento
	 * @param request peticinn
	 * @param session sesinn de usuario 
	 * @param usuVO Login de usuario
	 * @param filtro Filtro de busqeueda de actividades
	 * @throws ServletException
	 */
	public void planeacionEditar(HttpServletRequest request, HttpSession session, Login usuVO, FiltroSeguimientoVO filtro) throws ServletException {
		
		try {
			
			String id = request.getParameter("id");
			String id2 = request.getParameter("id2");
			String id3 = request.getParameter("id3");
			
			codigoColegio = id2;
			codigoSel = id3;
			
			if(id != null && id2 != null && id3 != null) {
				session.setAttribute("seguimientoVO", seguimientoDAO.getActividad(Integer.parseInt(id), Long.parseLong(id2), Long.parseLong(id3), filtro.getFilEstatoPOA(), filtro.isFilFechaHabil(), filtro.getFilPeriodoHabil()));
				request.setAttribute("buscaSeguimiento", "1");
				Logger.print(usuVO.getUsuarioId(), "POA: Edicion de actividad con recursos. " + id + ". " + id2 + ". " + id3, 2, 1, this.toString());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
		
	}
	
	public void listarArchivosTrimestres(HttpServletRequest request, HttpSession session, Login usuVO, FiltroSeguimientoVO filtro) throws ServletException {
		
		try {
						
			if(filtro != null && filtro.getFilVigencia() != 0) {
													
				request.setAttribute("listaArchivos1", seguimientoDAO.getListaArchivosTrimestre(filtro,codigoSel,1, codigoColegio));
				request.setAttribute("listaArchivos2", seguimientoDAO.getListaArchivosTrimestre(filtro,codigoSel,2, codigoColegio));
				request.setAttribute("listaArchivos3", seguimientoDAO.getListaArchivosTrimestre(filtro,codigoSel,3, codigoColegio));
				request.setAttribute("listaArchivos4", seguimientoDAO.getListaArchivosTrimestre(filtro,codigoSel,4, codigoColegio));
				
				listaArchivosSeguimiento1 = (List<ArrayList>)request.getAttribute("listaArchivos1");
				listaArchivosSeguimiento2 = (List<ArrayList>)request.getAttribute("listaArchivos2");
				listaArchivosSeguimiento3 = (List<ArrayList>)request.getAttribute("listaArchivos3");
				listaArchivosSeguimiento4 = (List<ArrayList>)request.getAttribute("listaArchivos4");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "No se pudo obtener la lista de arhivos: " + e.getMessage());
		}
		
	}

}
