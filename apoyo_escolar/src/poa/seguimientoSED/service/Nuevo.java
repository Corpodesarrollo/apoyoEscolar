package poa.seguimientoSED.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import poa.seguimientoSED.dao.SeguimientoDAO;
import poa.seguimientoSED.vo.FiltroSeguimientoVO;
import poa.seguimientoSED.vo.ParamsVO;
import poa.seguimientoSED.vo.SegFechasCompareVO;
import poa.seguimientoSED.vo.SeguimientoVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.login.beans.Login;
import siges.util.Logger;

/**
 * Procesa las peticiones del formulario de edicinn de actividades
 * 14/01/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class Nuevo extends Service{
	private static final long serialVersionUID = 6370013753285143307L;
	/**
	 * Ruta de la pagina de ingreso/edicion de actividad con recursos
	 */
	public String FICHA_ACTIVIDAD;
	/**
	 * Archivo de acceso a datos
	 */
	private SeguimientoDAO seguimientoDAO=new SeguimientoDAO(new Cursor());
	String codigoColegio;
	String codigoSel;
	
	List<SeguimientoVO> listaActividades = new ArrayList<>();

	/*
	 * Procesa la peticinn
	 * @param request peticinn
	 * @param response respuesta
	 * @return Ruta de direccionamiento
	 * @throws ServletException 
	 * @throws IOException
	 * (non-Javadoc)
	 * @see siges.common.service.Service#process(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
		String FICHA = null;
		String dispatcher[] = new String[2];
		
		HttpSession session = request.getSession();
		
		FICHA_ACTIVIDAD = config.getInitParameter("FICHA_ACTIVIDAD");
		
		int CMD = getCmd(request, session, ParamsVO.CMD_NUEVO);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		
		Login usuVO = (Login) session.getAttribute("login");
		SeguimientoVO planeacion = (SeguimientoVO)session.getAttribute("seguimientoSEDVO");
		FiltroSeguimientoVO filtro = (FiltroSeguimientoVO)session.getAttribute("filtroSeguimientoSEDVO");
		
		switch (TIPO) {
		case ParamsVO.FICHA_ACTIVIDAD:
			FICHA=FICHA_ACTIVIDAD;
			switch (CMD){
				case ParamsVO.CMD_NUEVO:
					session.removeAttribute("seguimientoSEDVO");
					session.removeAttribute("filtroSeguimientoSEDVO");
					
					planeacionBuscar(request, session, usuVO,filtro);
					planeacionBuscarCol(request, session, usuVO,filtro);
				break;
				case ParamsVO.CMD_BUSCAR_COL:
					planeacionBuscarCol(request, session, usuVO,filtro);
					session.removeAttribute("seguimientoSEDVO");
				break;
				case ParamsVO.CMD_BUSCAR:
					planeacionBuscar(request, session, usuVO,filtro);
					planeacionBuscarCol(request, session, usuVO,filtro);
				break;
				case ParamsVO.CMD_EDITAR:
					planeacionEditar(request, session, usuVO,filtro);
					planeacionBuscar(request, session, usuVO,filtro);
					planeacionBuscarCol(request, session, usuVO,filtro);
					planeacion=(SeguimientoVO)session.getAttribute("seguimientoSEDVO");
					
					planeacion = indicarAlerta(planeacion, filtro);
					listarArchivosTrimestres(request, session, usuVO, filtro);
				break;	
			}
			planeacionNuevo(request, session, usuVO,planeacion,filtro);
		break;
		}
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FICHA;
		return dispatcher;
	}
	

	private SeguimientoVO indicarAlerta(SeguimientoVO planeacion, FiltroSeguimientoVO filtro){
			
			if(planeacion != null){
				
				//planeacion.setRojoSeguimiento1(false);
				planeacion.setRojoNaranjaSeguimiento1(0);
				planeacion.setRojoNaranjaSeguimiento2(0);
				planeacion.setRojoNaranjaSeguimiento3(0);
				planeacion.setRojoNaranjaSeguimiento4(0);
				
//				planeacion.setPlaCronograma1("8");
//				planeacion.setPlaCronograma2("7");
//				planeacion.setPlaCronograma3("0");
//				planeacion.setPlaCronograma4("5");
				
//				planeacion.setPlaSeguimiento3("0");
			
			if(planeacion.getPlaTipoMeta() > 0 
					&& planeacion.getPlaTipoMeta() == 1){ // Para SUMATORIA
				
				if(planeacion.getPlaSeguimiento1() != null && planeacion.getPlaCronograma1() != null &&
						(Integer.parseInt(planeacion.getPlaSeguimiento1().trim()) < Integer.parseInt(planeacion.getPlaCronograma1()) )){
					//mostrar rojo
					planeacion.setRojoNaranjaSeguimiento1(1);
					
				} else if(planeacion.getPlaSeguimiento1() != null && planeacion.getPlaCronograma1() != null &&
						(Integer.parseInt(planeacion.getPlaSeguimiento1().trim()) > Integer.parseInt(planeacion.getPlaCronograma1()) )){
					//mostrar naranja
					planeacion.setRojoNaranjaSeguimiento1(2);
					
				}
				if(planeacion.getPlaSeguimiento2() != null && planeacion.getPlaCronograma2() != null && 
						(Integer.parseInt(planeacion.getPlaSeguimiento2().trim()) < Integer.parseInt(planeacion.getPlaCronograma2()))){
					//mostrar rojo
					planeacion.setRojoNaranjaSeguimiento2(1);
					
				} else if(planeacion.getPlaSeguimiento2() != null && planeacion.getPlaCronograma2() != null && 
						(Integer.parseInt(planeacion.getPlaSeguimiento2().trim()) > Integer.parseInt(planeacion.getPlaCronograma2()))){
					//mostrar naranja
					planeacion.setRojoNaranjaSeguimiento2(2);
					
				}
				if(planeacion.getPlaSeguimiento3() != null && planeacion.getPlaCronograma3() != null && 
						(Integer.parseInt(planeacion.getPlaSeguimiento3().trim()) < Integer.parseInt(planeacion.getPlaCronograma3()))){
					//mostrar rojo
					planeacion.setRojoNaranjaSeguimiento3(1);
					
				} else if(planeacion.getPlaSeguimiento3() != null && planeacion.getPlaCronograma3() != null && 
						(Integer.parseInt(planeacion.getPlaSeguimiento3().trim()) > Integer.parseInt(planeacion.getPlaCronograma3()))){
					//mostrar naranja
					planeacion.setRojoNaranjaSeguimiento3(2);
					
				}
				if(planeacion.getPlaSeguimiento4() != null && planeacion.getPlaCronograma4() != null && 
						(Integer.parseInt(planeacion.getPlaSeguimiento4().trim()) < Integer.parseInt(planeacion.getPlaCronograma4()))){
					//mostrar rojo
					planeacion.setRojoNaranjaSeguimiento4(1);
					
				} else if(planeacion.getPlaSeguimiento4() != null && planeacion.getPlaCronograma4() != null && 
						(Integer.parseInt(planeacion.getPlaSeguimiento4().trim()) > Integer.parseInt(planeacion.getPlaCronograma4()))){
					//mostrar naranja
					planeacion.setRojoNaranjaSeguimiento4(2);
				}
				
				
			} else if(planeacion.getPlaTipoMeta() > 0 
					&& planeacion.getPlaTipoMeta() == 2){ // Para DEMANDA
				
				if(planeacion.getPlaSeguimiento1() != null && planeacion.getPlaPorcentaje1() != null && 
						(Integer.parseInt(planeacion.getPlaSeguimiento1().trim()) < Integer.parseInt(planeacion.getPlaPorcentaje1()))){
					//mostrar rojo
					planeacion.setRojoNaranjaSeguimiento1(1);
					
				}
				if(planeacion.getPlaSeguimiento2() != null && planeacion.getPlaPorcentaje2() != null && 
						(Integer.parseInt(planeacion.getPlaSeguimiento2().trim()) < Integer.parseInt(planeacion.getPlaPorcentaje2()))){
					//mostrar rojo
					planeacion.setRojoNaranjaSeguimiento2(1);
					
				}
				if(planeacion.getPlaSeguimiento3() != null && planeacion.getPlaPorcentaje3() != null && 
						(Integer.parseInt(planeacion.getPlaSeguimiento3().trim()) < Integer.parseInt(planeacion.getPlaPorcentaje3()))){
					//mostrar rojo
					planeacion.setRojoNaranjaSeguimiento3(1);
					
				}
				if(planeacion.getPlaSeguimiento4() != null && planeacion.getPlaPorcentaje4() != null && 
						(Integer.parseInt(planeacion.getPlaSeguimiento4().trim()) < Integer.parseInt(planeacion.getPlaPorcentaje4()))){
					//mostrar rojo
					planeacion.setRojoNaranjaSeguimiento4(1);
				}
				
			} else if(planeacion.getPlaTipoMeta() > 0 
					&& planeacion.getPlaTipoMeta() == 3){ // Para CONSTANTE
				
				if(planeacion.getPlaSeguimiento1() != null && planeacion.getPlaCronograma1() != null && 
						(Integer.parseInt(planeacion.getPlaSeguimiento1().trim()) < Integer.parseInt(planeacion.getPlaCronograma1()))){
					//mostrar rojo
					planeacion.setRojoNaranjaSeguimiento1(1);
					
				} else if(planeacion.getPlaSeguimiento1() != null && planeacion.getPlaCronograma1() != null && 
						(Integer.parseInt(planeacion.getPlaSeguimiento1().trim()) > Integer.parseInt(planeacion.getPlaCronograma1()))){
					//mostrar naranja
					planeacion.setRojoNaranjaSeguimiento1(2);
					
				}
				if(planeacion.getPlaSeguimiento2() != null && planeacion.getPlaCronograma2() != null && 
						(Integer.parseInt(planeacion.getPlaSeguimiento2().trim()) < Integer.parseInt(planeacion.getPlaCronograma2()))){
					//mostrar rojo
					planeacion.setRojoNaranjaSeguimiento2(1);
					
				} else if(planeacion.getPlaSeguimiento2() != null && planeacion.getPlaCronograma2() != null && 
						(Integer.parseInt(planeacion.getPlaSeguimiento2().trim()) > Integer.parseInt(planeacion.getPlaCronograma2()))){
					//mostrar naranja
					planeacion.setRojoNaranjaSeguimiento2(2);
					
				}
				if(planeacion.getPlaSeguimiento3() != null && planeacion.getPlaCronograma3() != null && 
						(Integer.parseInt(planeacion.getPlaSeguimiento3().trim()) < Integer.parseInt(planeacion.getPlaCronograma3()))){
					//mostrar rojo
					planeacion.setRojoNaranjaSeguimiento3(1);
					
				} else if(planeacion.getPlaSeguimiento3() != null && planeacion.getPlaCronograma3() != null && 
						(Integer.parseInt(planeacion.getPlaSeguimiento3().trim()) > Integer.parseInt(planeacion.getPlaCronograma3()))){
					//mostrar naranja
					planeacion.setRojoNaranjaSeguimiento3(2);
					
				}
				if(planeacion.getPlaSeguimiento4() != null && planeacion.getPlaCronograma4() != null && 
						(Integer.parseInt(planeacion.getPlaSeguimiento4().trim()) < Integer.parseInt(planeacion.getPlaCronograma4()))){
					//mostrar rojo
					planeacion.setRojoNaranjaSeguimiento4(1);
					
				} else if(planeacion.getPlaSeguimiento4() != null && planeacion.getPlaCronograma4() != null && 
						(Integer.parseInt(planeacion.getPlaSeguimiento4().trim()) > Integer.parseInt(planeacion.getPlaCronograma4()))){
					//mostrar naranja
					planeacion.setRojoNaranjaSeguimiento4(2);
					
				}
				
			}
		}
		
	try {
			SegFechasCompareVO fechasSegCompare= new SegFechasCompareVO();
			fechasSegCompare = seguimientoDAO.compararFechaFinSeg(filtro.getFilVigencia());
			//fechasSegCompare.isFechaCompareSeg1() true == 1, false == 0
			//true la fecha Actual del sistema es > (mayor) que la fecha fin trimestre.
			//false la fecha Actual del sistema es < (menor) que la fecha fin trimestre.
		
		
		 if(planeacion.getPlaCronograma1() == null 
				 || planeacion.getPlaCronograma1().equals("0")){
			 
			 if(fechasSegCompare != null && fechasSegCompare.isFechaCompareSeg1()){ 
					//poner el N/A
				 planeacion.setCalifEvalSeg1("N/A");
				}	
						
		 }
		 if(planeacion.getPlaCronograma2() == null 
				 || planeacion.getPlaCronograma2().equals("0")){
			 
			 if(fechasSegCompare != null && fechasSegCompare.isFechaCompareSeg2()){ 
					//poner el N/A
				 planeacion.setCalifEvalSeg2("N/A");
				}	
						
		 }
		 if(planeacion.getPlaCronograma3() == null 
				 || planeacion.getPlaCronograma3().equals("0")){
			 
			 if(fechasSegCompare != null && fechasSegCompare.isFechaCompareSeg3()){ 
					//poner el N/A
				 planeacion.setCalifEvalSeg3("N/A");
				}	
						
		 }
		 if(planeacion.getPlaCronograma4() == null 
				 || planeacion.getPlaCronograma4().equals("0")){
			 
			 if(fechasSegCompare != null && fechasSegCompare.isFechaCompareSeg4()){ 
					//poner el N/A
				 planeacion.setCalifEvalSeg4("N/A");
				}	
						
		 }
		 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return planeacion;
		
		
	}


	/**
	 *  Inicializa los objetos necesarios para la pagina de ingreso/edicion de actividad con recursos
	 * @param request peticinn
	 * @param session sesinn de usuario
	 * @param usuVO Login de usuario
	 * @param planeacion Actividad
	 * @param filtro Filtro de bnsqueda de actividades
	 * @throws ServletException
	 */
	public void planeacionNuevo(HttpServletRequest request, HttpSession session, Login usuVO,SeguimientoVO planeacion,FiltroSeguimientoVO filtro) throws ServletException {
		try {
			request.setAttribute("listaVigencia", seguimientoDAO.getListaVigenciaActual());
			request.setAttribute("listaAreaGestion", seguimientoDAO.getListaAreaGestion());
			request.setAttribute("listaTipoMeta", seguimientoDAO.getListaTipoMeta());
			request.setAttribute("listaUnidadMedida", seguimientoDAO.getListaUnidadMedida());
			request.getSession().setAttribute("listaObjetivos", seguimientoDAO.getlistaObjetivos());
			/*PIMA - PIGA*/
			request.setAttribute("listaAccionMejoramiento",seguimientoDAO.getListaAccionMejoramiento());
			if(planeacion!=null && planeacion.getPlaAreaGestion()!=0){
				request.setAttribute("listaLineaAccion", seguimientoDAO.getListaLineaAccion(planeacion.getPlaAreaGestion()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	/**
	 *  Calcula la lista de actividades con recursos para los parametros de filtro indicados
	 * @param request peticinn
	 * @param session sesinn de usuario
	 * @param usuVO Login de usuario
	 * @param filtro Filtro de bnsqueda de actividades
	 * @throws ServletException
	 */
	public void planeacionBuscar(HttpServletRequest request, HttpSession session, Login usuVO,FiltroSeguimientoVO filtro) throws ServletException {
		try {
			
			Float califTotal = null;
			
			if(filtro!=null && filtro.getFilVigencia()!=0){
				request.setAttribute("listaActividades", seguimientoDAO.getListaActividades(filtro));
				
				listaActividades = (List<SeguimientoVO>) request.getAttribute("listaActividades");
				SeguimientoVO planeacion = (SeguimientoVO)session.getAttribute("seguimientoSEDVO");
				
				for (SeguimientoVO itemVO : listaActividades) {
					
					if(itemVO.getCalifActividad() != null){						
						if(califTotal == null){							
							califTotal = 0f;
						}						
						califTotal= califTotal+ Float.parseFloat(itemVO.getCalifActividad());
												
					} 
					if(califTotal == null){
						planeacion.setCalifTotalActividad("");
					} else{
						planeacion.setCalifTotalActividad(califTotal.toString());
					}
					
				}
				Logger.print(usuVO.getUsuarioId(),"POA: Buscar actividad con recursos. "+filtro.getFilInstitucion()+". "+filtro.getFilVigencia(),2,1,this.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 *  Calcula la lista de colegios
	 * @param request peticinn
	 * @param session sesinn de usuario
	 * @param usuVO Login de usuario
	 * @param filtro Filtro de bnsqueda de actividades
	 * @throws ServletException
	 */
	public void planeacionBuscarCol(HttpServletRequest request, HttpSession session, Login usuVO,FiltroSeguimientoVO filtro) throws ServletException {
		try {
			if(filtro!=null && filtro.getFilVigencia()!=0){
				request.setAttribute("listaColegio", seguimientoDAO.getListaColegio(filtro));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	
	/**
	 * Obtiene la actividad con recursos solicitada  
	 * @param request peticinn
	 * @param session Sesinn de usuario
	 * @param usuVO Login de usuario 
	 * @param filtro Filtro de bnsqueda de actividades
	 * @throws ServletException
	 */
	public void planeacionEditar(HttpServletRequest request, HttpSession session, Login usuVO, FiltroSeguimientoVO filtro) throws ServletException {
		
		try {
			
			String id = request.getParameter("id");
			String id2 = request.getParameter("id2");
			String id3 = request.getParameter("id3");
			codigoColegio = id2;
			codigoSel = id3;
			
			if (id != null && id2 != null && id3 != null) {
				
				SeguimientoVO actividad = seguimientoDAO.getActividad(Integer.parseInt(id), Long.parseLong(id2), Long.parseLong(id3));
				session.setAttribute("seguimientoSEDVO", actividad);
				Logger.print(usuVO.getUsuarioId(), "POA: Edicion de actividad con recursos. " + id + ". " + id2 + ". " + id3, 2, 1, this.toString());
				
				/*PIMA - PIGA*/
				request.setAttribute("listaAccionMejoramiento", seguimientoDAO.getListaAccionMejoramiento());
				request.setAttribute("listaFuenteFinanciacion", seguimientoDAO.getListaFuenteFinanciacion());
				request.setAttribute("listaRubroGasto", seguimientoDAO.getListaRubroGasto(actividad.getTIPOGASTO()));
				request.getSession().setAttribute("listaObjetivos", seguimientoDAO.getlistaObjetivos());
				/* ********** */
				
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
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "No se pudo obtener la lista de arhivos: " + e.getMessage());
		}
		
	}

}
