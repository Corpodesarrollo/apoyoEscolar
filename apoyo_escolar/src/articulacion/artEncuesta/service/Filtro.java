/**
 * 
 */
package articulacion.artEncuesta.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.artEncuesta.dao.EncuestaDAO;
import articulacion.artEncuesta.vo.Encuesta3VO;
import articulacion.artEncuesta.vo.Encuesta4VO;
import articulacion.artEncuesta.vo.EncuestaVO;
import articulacion.artEncuesta.vo.Encuesta2VO;
import articulacion.artEncuesta.vo.ParamsVO;
import siges.common.service.Service;
import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.io.Zip;
import siges.login.beans.Login;

/**
 * 5/12/2007
 * 
 * @author Latined
 * @version 1.2
 */
public class Filtro extends Service {
	private String FICHA;
	private String FICHA_FILTRO1;
	private String FICHA_FILTRO2;
	private String FICHA_FILTRO3;
	private String FICHA_FILTRO4;
	private String FICHA_RESULTADO1;
	private String FICHA_RESULTADO2;
	private String s;
	private EncuestaDAO encuestaDAO = new EncuestaDAO(new Cursor());

	public String[] process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[] = new String[2];
		Login usuVO=(Login)session.getAttribute("login");		
		EncuestaVO filtro = (EncuestaVO) session.getAttribute("encuestaVO");		
		Encuesta2VO filtro2 = (Encuesta2VO) session.getAttribute("encuesta2VO");
		Encuesta3VO filtro3 = (Encuesta3VO) session.getAttribute("encuesta3VO");
		Encuesta4VO filtro4 = (Encuesta4VO) session.getAttribute("encuesta4VO");
		FICHA_FILTRO1 = config.getInitParameter("FICHA_FILTRO1");
		FICHA_FILTRO2 = config.getInitParameter("FICHA_FILTRO2");
		FICHA_FILTRO3 = config.getInitParameter("FICHA_FILTRO3");
		FICHA_FILTRO4 = config.getInitParameter("FICHA_FILTRO4");
		FICHA_RESULTADO1 = config.getInitParameter("FICHA_RESULTADO1");
		FICHA_RESULTADO2 = config.getInitParameter("FICHA_RESULTADO2");
		s = config.getInitParameter("s");		
		int CMD = getCmd(request, session, ParamsVO.CMD_NUEVO);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_FILTRO);
		int modulo;
		//System.out.println("LOS VALORES===" + CMD + "//" + TIPO);
		try {
			switch (TIPO) {
				case ParamsVO.FICHA_FILTRO:
					switch (CMD) {
					case ParamsVO.CMD_NUEVO:
						FICHA=FICHA_FILTRO1;
						filtroEvalNuevo(request,session,filtro,usuVO);
						break;
					case ParamsVO.CMD_BUSCAR:
						filtroEvalNuevo(request,session,filtro,usuVO);
						FICHA=filtroEvalBuscar(request,filtro,usuVO);
						break;
					case ParamsVO.CMD_DESCARGAR:
						filtroEvalNuevo(request,session,filtro,usuVO);
						modulo = 48;
						request.setAttribute("modulo", String.valueOf(modulo));
						FICHA=filtroDescargar(request,filtro,usuVO,modulo);
						break;
					}
				break;
				
				case ParamsVO.FICHA_FILTRO2:
					switch (CMD) {
					case ParamsVO.CMD_NUEVO:
						FICHA=FICHA_FILTRO2;
						filtroConNuevo(request,session,filtro2,usuVO);
						break;
					case ParamsVO.CMD_BUSCAR:
						filtroConNuevo(request,session,filtro2,usuVO);
						FICHA=filtroConsolidadoBuscar(request,filtro2,usuVO);
						break;
					case ParamsVO.CMD_DESCARGAR:
						filtroConNuevo(request,session,filtro2,usuVO);
						modulo = 49;
						request.setAttribute("modulo", String.valueOf(modulo));
						FICHA=filtroDescargar2(request,filtro2,usuVO,modulo);
						break;
					}
				break;
				
				case ParamsVO.FICHA_FILTRO3:
					switch (CMD) {
					case ParamsVO.CMD_NUEVO:
						FICHA=FICHA_FILTRO3;
						filtroConEncNuevo(request,session,filtro3,usuVO);
						break;					
					case ParamsVO.CMD_DESCARGAR:
						filtroConEncNuevo(request,session,filtro3,usuVO);
						modulo = 50;
						request.setAttribute("modulo", String.valueOf(modulo));
						FICHA=filtroEncDescargar(request,filtro3,usuVO,modulo);
						break;
					}
				break;
				
				case ParamsVO.FICHA_FILTRO4:
					switch (CMD) {
					case ParamsVO.CMD_NUEVO:
						FICHA=FICHA_FILTRO4;
						filtroMotivoNuevo(request,session,filtro4,usuVO);
						break;					
					case ParamsVO.CMD_DESCARGAR:
						filtroMotivoNuevo(request,session,filtro4,usuVO);
						modulo = 51;
						request.setAttribute("modulo", String.valueOf(modulo));
						FICHA=filtroPrefDescargar(request,filtro4,usuVO,modulo);
						break;
					}
				break;
			}
			dispatcher[0] = String.valueOf(ParamsVO.INCLUDE);
			dispatcher[1] = FICHA;
			return dispatcher;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e.getMessage());
		}
	}
	
	
	private void filtroEvalNuevo(HttpServletRequest request,HttpSession session,EncuestaVO filtro,Login usuVO)throws Exception{		
		//LISTA DE ORDEN
		List l = new ArrayList();
		ItemVO itemCom = null;
		itemCom = new ItemVO();
		itemCom.setCodigo(1);
		itemCom.setNombre("Apellidos");
		l.add(itemCom);
		ItemVO itemCom2 = null;
		itemCom2 = new ItemVO();
		itemCom2.setCodigo(2);
		itemCom2.setNombre("Identificacinn");
		l.add(itemCom2);			
		request.setAttribute("listaOrdenVO",l);
		
		List l2 = new ArrayList();		
		ItemVO itemCom3 = null;
		itemCom3 = new ItemVO();
		itemCom3.setCodigo(1);
		itemCom3.setNombre("No ha Ingresado");
		l2.add(itemCom3);
		ItemVO itemCom4 = null;
		itemCom4 = new ItemVO();
		itemCom4.setCodigo(2);
		itemCom4.setNombre("Consultado");
		l2.add(itemCom4);
		ItemVO itemCom5 = null;
		itemCom5 = new ItemVO();
		itemCom5.setCodigo(3);
		itemCom5.setNombre("Guardado");
		l2.add(itemCom5);
		request.setAttribute("listaEstadoVO",l2);
		
		request.setAttribute("listaLocalidadesVO",encuestaDAO.getLocalidades());
		
		String nivel=usuVO.getNivel();		
		if(filtro!=null){
			if(filtro.getPlaInstitucion()>0 && filtro.getPlaLocalidad()>0){
			request.setAttribute("listaColegiosVO",encuestaDAO.getColegiosFiltro(filtro.getPlaLocalidad()));
			request.setAttribute("listaSedeVO",encuestaDAO.getSedes(String.valueOf(filtro.getPlaInstitucion())));
			request.setAttribute("listaJornadaVO",encuestaDAO.getJornada(String.valueOf(filtro.getPlaInstitucion()),String.valueOf(filtro.getPlaSede())));
			request.setAttribute("listaGradoVO",encuestaDAO.getAjaxGrado(filtro.getPlaInstitucion(),filtro.getPlaSede(),filtro.getPlaJornada(),String.valueOf(filtro.getPlaMetodologia())));
			request.setAttribute("listaGrupoVO",encuestaDAO.getAjaxGrupo(filtro.getPlaInstitucion(),filtro.getPlaSede(),filtro.getPlaJornada(),filtro.getPlaGrado(),String.valueOf(filtro.getPlaMetodologia())));		
			}
		}else{
			filtro=new EncuestaVO();			
			session.setAttribute("encuestaVO",filtro);
		}
		if(nivel.equals("4") || nivel.equals("6")){
			filtro.setPlaLocalidad(Long.parseLong(usuVO.getLocId()));
			filtro.setPlaInstitucion(Long.parseLong(usuVO.getInstId()));			
			filtro.setHabilitarLocalidad(1);
			filtro.setHabilitarColegio(1);			
			request.setAttribute("listaColegiosVO",encuestaDAO.getColegiosFiltro(filtro.getPlaLocalidad()));
			request.setAttribute("listaSedeVO",encuestaDAO.getSedes(String.valueOf(filtro.getPlaInstitucion())));			
		}else if(nivel.equals("1") || nivel.equals("2")){
			usuVO.setMetodologiaId("1");
			filtro.setHabilitarLocalidad(2);
			filtro.setHabilitarColegio(2);	
		}
		
		
	}
	private String filtroEvalBuscar(HttpServletRequest request,EncuestaVO filtro,Login login)throws Exception{
		try {
			//estudiante vs encuesta
			//HttpSession session = request.getSession();
			//Login usuVO = (Login) session.getAttribute("login");
			//metodologia = usuVO.getMetodologiaId();			
			List listaEstudiantes = encuestaDAO.getEstudianteEnc(filtro);
			if(listaEstudiantes==null || listaEstudiantes.size()==0){
				request.setAttribute("mensaje", "No hay estudiantes en el grupo indicado");
				filtro.setFormaEstado("-1");
				return FICHA_FILTRO1;
			}
			request.setAttribute("listaEstudiante",listaEstudiantes);
			filtro.setFormaEstado("1");
		} catch (Exception th) {
			th.printStackTrace();
			filtro.setFormaEstado("-1");
			request.setAttribute("mensaje", "Error interno: "+th.getMessage());
		}
		return FICHA_RESULTADO1;
	}
	
	private String filtroDescargar(HttpServletRequest request,EncuestaVO filtro,Login login, int modulo)throws Exception{
		Map parameters;
		try {
			//estudiante vs encuesta
			HttpSession session = request.getSession();
			Login usuVO = (Login) session.getAttribute("login");
			//metodologia = usuVO.getMetodologiaId();
			//usuarioID = Long.parseLong(usuVO.getUsuarioId());
			String nivel=usuVO.getNivel();
			//String usuario = usuVO.getUsuario();
			long idUsuario = Long.parseLong(login.getUsuarioId());			
			parameters = new HashMap();			
			boolean reporteEncuesta=false;
			Zip zip = new Zip();
			List listaEstudiantes = encuestaDAO.getEstudianteEnc(filtro);
			if(listaEstudiantes!=null && listaEstudiantes.size()>0){
				parameters.put("LOCALIDAD", new Integer((int)filtro.getPlaLocalidad()));
				parameters.put("INSTITUCION", new Long(filtro.getPlaInstitucion()));
				parameters.put("SEDE", new Long(filtro.getPlaSede()));
				parameters.put("JORNADA", new Long(filtro.getPlaJornada()));
				parameters.put("METODOLOGIA", new Long(filtro.getPlaMetodologia()));
				parameters.put("GRADO", new Long(filtro.getPlaGrado()));
				parameters.put("GRUPO", new Long(filtro.getPlaGrupo()));
				parameters.put("ORDEN", new Integer(filtro.getPlaOrden()));
				parameters.put("ESTADO", new Integer(filtro.getPlaEstado()));
				parameters.put("USUARIO", String.valueOf(idUsuario));
				parameters.put("ID_USUARIO", new Long(Long.parseLong(usuVO.getUsuarioId())));
				if(nivel.equals("4") || nivel.equals("6")){
					parameters.put("SECRETARIA", new Integer(2));
				}else if(nivel.equals("1") || nivel.equals("2")){
					parameters.put("SECRETARIA", new Integer(1));
				}				
				reporteEncuesta=encuestaDAO.generarReporte(filtro,request,idUsuario, parameters, modulo, nivel);
				request.setAttribute("modulo", String.valueOf(modulo));
				return s;
			}			
		} catch (Exception th) {
			th.printStackTrace();
			filtro.setFormaEstado("-1");
			request.setAttribute("mensaje", "Error interno: "+th.getMessage());
		}
		return FICHA_FILTRO1;
	}
	
	private String filtroDescargar2(HttpServletRequest request,Encuesta2VO filtro2,Login login, int modulo)throws Exception{
		Map parameters;
		try {
			//estudiante vs encuesta
			HttpSession session = request.getSession();
			Login usuVO = (Login) session.getAttribute("login");
			//metodologia = usuVO.getMetodologiaId();
			//usuarioID = Long.parseLong(usuVO.getUsuarioId());
			String nivel=usuVO.getNivel();
			String usuario = usuVO.getUsuario();
			long idUsuario = Long.parseLong(login.getUsuarioId());			
			parameters = new HashMap();			
			boolean reporteEncuesta=false;
			Zip zip = new Zip();
			List listaEstado = encuestaDAO.getConsolidadoEnc(filtro2);
			if(listaEstado!=null && listaEstado.size()>0){
				parameters.put("LOCALIDAD", new Integer((int)filtro2.getPlaLocalidad()));
				parameters.put("INSTITUCION", new Long(filtro2.getPlaInstitucion()));
				parameters.put("SEDE", new Long(filtro2.getPlaSede()));
				parameters.put("JORNADA", new Long(filtro2.getPlaJornada()));
				parameters.put("METODOLOGIA", new Long(filtro2.getPlaMetodologia()));
				parameters.put("GRADO", new Long(filtro2.getPlaGrado()));
				parameters.put("GRUPO", new Long(filtro2.getPlaGrupo()));				
				parameters.put("USUARIO", String.valueOf(idUsuario));
				parameters.put("ID_USUARIO", new Long(Long.parseLong(usuVO.getUsuarioId())));
				parameters.put("TOTAL_ESTUDIANTES", new Integer(filtro2.getTotalEstudiantes()));
				if(nivel.equals("4") || nivel.equals("6")){
					parameters.put("SECRETARIA", new Integer(2));
				}else if(nivel.equals("1") || nivel.equals("2")){
					parameters.put("SECRETARIA", new Integer(1));
				}				
				EncuestaVO encuesta = new EncuestaVO();
				encuesta.setPlaLocalidad(filtro2.getPlaLocalidad());
				encuesta.setPlaInstitucion(filtro2.getPlaInstitucion());
				encuesta.setPlaSede(filtro2.getPlaSede());
				encuesta.setPlaJornada(filtro2.getPlaJornada());
				encuesta.setPlaMetodologia(filtro2.getPlaMetodologia());
				encuesta.setPlaGrado(filtro2.getPlaGrado());
				encuesta.setPlaGrupo(filtro2.getPlaGrupo());				
				reporteEncuesta=encuestaDAO.generarReporte(encuesta,request,idUsuario, parameters, modulo, nivel);
				request.setAttribute("modulo", String.valueOf(modulo));
				return s;
			}			
		} catch (Exception th) {
			th.printStackTrace();
			filtro2.setFormaEstado("-1");
			request.setAttribute("mensaje", "Error interno: "+th.getMessage());
		}
		return FICHA_FILTRO2;
	}


	private String filtroConsolidadoBuscar(HttpServletRequest request,Encuesta2VO filtro2,Login login)throws Exception{
		try {
			//estudiante vs encuesta
			//HttpSession session = request.getSession();
			//Login usuVO = (Login) session.getAttribute("login");
			//metodologia = usuVO.getMetodologiaId();			
			List listaEstados = encuestaDAO.getConsolidadoEnc(filtro2);
			if(listaEstados==null || listaEstados.size()==0){
				request.setAttribute("mensaje", "No hay estudiantes en el grupo indicado para el consolidado");
				filtro2.setFormaEstado("-1");
				return FICHA_FILTRO2;
			}
			request.setAttribute("listaEstado",listaEstados);
			filtro2.setFormaEstado("1");
		} catch (Exception th) {
			th.printStackTrace();
			filtro2.setFormaEstado("-1");
			request.setAttribute("mensaje", "Error interno: "+th.getMessage());
		}
		return FICHA_RESULTADO2;
	}
	
	//FILTRO CONSOLIDADO NUM ESTUDIANTES NUEVO
	private void filtroConNuevo(HttpServletRequest request,HttpSession session,Encuesta2VO filtro2,Login usuVO)throws Exception{
		String nivel=usuVO.getNivel();		
		request.setAttribute("listaLocalidadesVO",encuestaDAO.getLocalidades());
		if(filtro2!=null){
			if(filtro2.getPlaLocalidad()>0){			
				request.setAttribute("listaColegiosVO",encuestaDAO.getColegiosFiltro(filtro2.getPlaLocalidad()));
				request.setAttribute("listaSedeVO",encuestaDAO.getSedes(String.valueOf(filtro2.getPlaInstitucion())));
				request.setAttribute("listaJornadaVO",encuestaDAO.getJornada(String.valueOf(filtro2.getPlaInstitucion()),String.valueOf(filtro2.getPlaSede())));
				request.setAttribute("listaGradoVO",encuestaDAO.getAjaxGrado(filtro2.getPlaInstitucion(),filtro2.getPlaSede(),filtro2.getPlaJornada(),String.valueOf(filtro2.getPlaMetodologia())));
				request.setAttribute("listaGrupoVO",encuestaDAO.getAjaxGrupo(filtro2.getPlaInstitucion(),filtro2.getPlaSede(),filtro2.getPlaJornada(),filtro2.getPlaGrado(),String.valueOf(filtro2.getPlaMetodologia())));
			}
		}else{
			filtro2=new Encuesta2VO();			
			session.setAttribute("encuesta2VO",filtro2);
		}
		if(nivel.equals("4") || nivel.equals("6")){
			filtro2.setPlaLocalidad(Long.parseLong(usuVO.getLocId()));
			filtro2.setPlaInstitucion(Long.parseLong(usuVO.getInstId()));			
			filtro2.setHabilitarLocalidad(1);
			filtro2.setHabilitarColegio(1);			
			request.setAttribute("listaColegiosVO",encuestaDAO.getColegiosFiltro(filtro2.getPlaLocalidad()));
			request.setAttribute("listaSedeVO",encuestaDAO.getSedes(String.valueOf(filtro2.getPlaInstitucion())));			
		}else if(nivel.equals("1") || nivel.equals("2")){
			usuVO.setMetodologiaId("1");
			filtro2.setHabilitarLocalidad(2);
			filtro2.setHabilitarColegio(2);	
		}
		
		
	
	}

	
//	FILTRO CONSOLIDADO ENCUESTA NUEVO
	private void filtroConEncNuevo(HttpServletRequest request,HttpSession session,Encuesta3VO filtro3,Login usuVO)throws Exception{
		
		String nivel=usuVO.getNivel();
//		LISTA DE ORDEN
		List l = new ArrayList();
		l = new ArrayList();
		ItemVO itemCom = null;
		itemCom = new ItemVO();
		itemCom.setCodigo(1);
		itemCom.setNombre("Pregunta 1");
		l.add(itemCom);
		ItemVO itemCom2 = null;
		itemCom2 = new ItemVO();
		itemCom2.setCodigo(2);
		itemCom2.setNombre("Pregunta 3");
		l.add(itemCom2);
		ItemVO itemCom3 = null;
		itemCom3 = new ItemVO();
		itemCom3.setCodigo(3);
		itemCom3.setNombre("Pregunta 4");
		l.add(itemCom3);
		ItemVO itemCom4 = null;
		itemCom4 = new ItemVO();
		itemCom4.setCodigo(4);
		itemCom4.setNombre("Pregunta 5");
		l.add(itemCom4);
		ItemVO itemCom5 = null;
		itemCom5 = new ItemVO();
		itemCom5.setCodigo(5);
		itemCom5.setNombre("Pregunta 6");
		l.add(itemCom5);		
		request.setAttribute("listaPreguntasVO",l);
		request.setAttribute("listaLocalidadesVO",encuestaDAO.getLocalidades());
		if(filtro3!=null){
			if(filtro3.getPlaLocalidad()>0){
				request.setAttribute("listaColegiosVO",encuestaDAO.getColegiosFiltro(filtro3.getPlaLocalidad()));
				request.setAttribute("listaSedeVO",encuestaDAO.getSedes(String.valueOf(filtro3.getPlaInstitucion())));
				request.setAttribute("listaJornadaVO",encuestaDAO.getJornada(String.valueOf(filtro3.getPlaInstitucion()),String.valueOf(filtro3.getPlaSede())));
				request.setAttribute("listaGradoVO",encuestaDAO.getAjaxGrado(filtro3.getPlaInstitucion(),filtro3.getPlaSede(),filtro3.getPlaJornada(),String.valueOf(filtro3.getPlaMetodologia())));
				request.setAttribute("listaGrupoVO",encuestaDAO.getAjaxGrupo(filtro3.getPlaInstitucion(),filtro3.getPlaSede(),filtro3.getPlaJornada(),filtro3.getPlaSede(),String.valueOf(filtro3.getPlaMetodologia())));
			}
		}else{
			filtro3=new Encuesta3VO();			
			session.setAttribute("encuesta3VO",filtro3);
		}
		if(nivel.equals("4") || nivel.equals("6")){
			filtro3.setPlaLocalidad(Long.parseLong(usuVO.getLocId()));
			filtro3.setPlaInstitucion(Long.parseLong(usuVO.getInstId()));			
			filtro3.setHabilitarLocalidad(1);
			filtro3.setHabilitarColegio(1);			
			request.setAttribute("listaColegiosVO",encuestaDAO.getColegiosFiltro(filtro3.getPlaLocalidad()));
			request.setAttribute("listaSedeVO",encuestaDAO.getSedes(String.valueOf(filtro3.getPlaInstitucion())));			
		}else if(nivel.equals("1") || nivel.equals("2")){
			usuVO.setMetodologiaId("1");
			filtro3.setHabilitarLocalidad(2);
			filtro3.setHabilitarColegio(2);	
		}
		
	}
	//FUNCION GNERACION REPORTE CONSOLIDADO ENCUESTA
	private String filtroEncDescargar(HttpServletRequest request,Encuesta3VO filtro3,Login login, int modulo)throws Exception{
		Map parameters;
		long usuarioID;
		try {
			
			HttpSession session = request.getSession();
			Login usuVO = (Login) session.getAttribute("login");
			//metodologia = usuVO.getMetodologiaId();
			usuarioID = Long.parseLong(usuVO.getUsuarioId());			
			String nivel=usuVO.getNivel();
			String usuario = usuVO.getUsuario();
			boolean reporteEncuesta=false;
			parameters = new HashMap();			
			Zip zip = new Zip();
			encuestaDAO.deleteEncuestados(usuarioID);
			encuestaDAO.insertarEncuestadosTemporal(filtro3,usuarioID);
			boolean existenEncuestados = encuestaDAO.getExistenEncuestados(usuarioID);
			if(existenEncuestados){
				parameters.put("LOCALIDAD", new Integer((int)filtro3.getPlaLocalidad()));
				parameters.put("INSTITUCION", new Long(filtro3.getPlaInstitucion()));
				parameters.put("SEDE", new Long(filtro3.getPlaSede()));
				parameters.put("JORNADA", new Long(filtro3.getPlaJornada()));
				parameters.put("METODOLOGIA", new Long(filtro3.getPlaMetodologia()));
				parameters.put("GRADO", new Long(filtro3.getPlaGrado()));
				parameters.put("GRUPO", new Long(filtro3.getPlaGrupo()));
				parameters.put("PARAM_ORDEN", new Integer(filtro3.getPlaOrden()));
				parameters.put("USUARIO", usuario);
				parameters.put("ID_USUARIO", new Long(Long.parseLong(usuVO.getUsuarioId())));
				parameters.put("PREGUNTA", new Integer(filtro3.getNumeroPregunta()));
				if(nivel.equals("4") || nivel.equals("6")){
					parameters.put("SECRETARIA", new Integer(2));
				}else if(nivel.equals("1") || nivel.equals("2")){
					parameters.put("SECRETARIA", new Integer(1));
				}				
				EncuestaVO encuesta = new EncuestaVO();
				encuesta.setPlaLocalidad(filtro3.getPlaLocalidad());
				encuesta.setPlaInstitucion(filtro3.getPlaInstitucion());
				encuesta.setPlaSede(filtro3.getPlaSede());
				encuesta.setPlaJornada(filtro3.getPlaJornada());
				encuesta.setPlaMetodologia(filtro3.getPlaMetodologia());
				encuesta.setPlaGrado(filtro3.getPlaGrado());
				encuesta.setPlaGrupo(filtro3.getPlaGrupo());				
				encuesta.setNumeroPregunta(filtro3.getNumeroPregunta());
				reporteEncuesta=encuestaDAO.generarReporte(encuesta,request,usuarioID, parameters, modulo, nivel);
				request.setAttribute("modulo", String.valueOf(modulo));
				return s;
			}else{
				request.setAttribute("mensaje", "No hay datos para generar el reporte consolidado encuesta general ");
			}		
		} catch (Exception th) {
			th.printStackTrace();
			filtro3.setFormaEstado("-1");
			request.setAttribute("mensaje", "Error interno: "+th.getMessage());
		}
		return FICHA_FILTRO3;
	}
	
	
	
	//FILTRO TAB MOTIVOS DE PREFERENCIAS 
	private void filtroMotivoNuevo(HttpServletRequest request,HttpSession session,Encuesta4VO filtro4,Login usuVO)throws Exception{
		
		String nivel=usuVO.getNivel();
//		LISTA DE ORDEN
		List l = new ArrayList();
		l = new ArrayList();
		ItemVO itemCom = null;
		itemCom = new ItemVO();
		itemCom.setCodigo(1);
		itemCom.setNombre("Opción 1");
		l.add(itemCom);
		ItemVO itemCom2 = null;
		itemCom2 = new ItemVO();
		itemCom2.setCodigo(2);
		itemCom2.setNombre("Opción 2");
		l.add(itemCom2);				
		request.setAttribute("listaOpcionesVO",l);
		request.setAttribute("listaAsignaturasVO",encuestaDAO.getListaAsignatura());
		request.setAttribute("listaLocalidadesVO",encuestaDAO.getLocalidades());
		if(filtro4!=null){
			if(filtro4.getPlaLocalidad()>0){
				request.setAttribute("listaColegiosVO",encuestaDAO.getColegiosFiltro(filtro4.getPlaLocalidad()));
				request.setAttribute("listaSedeVO",encuestaDAO.getSedes(String.valueOf(filtro4.getPlaInstitucion())));
				request.setAttribute("listaJornadaVO",encuestaDAO.getJornada(String.valueOf(filtro4.getPlaInstitucion()),String.valueOf(filtro4.getPlaSede())));
				request.setAttribute("listaGradoVO",encuestaDAO.getAjaxGrado(filtro4.getPlaInstitucion(),filtro4.getPlaSede(),filtro4.getPlaJornada(),String.valueOf(filtro4.getPlaMetodologia())));
				request.setAttribute("listaGrupoVO",encuestaDAO.getAjaxGrupo(filtro4.getPlaInstitucion(),filtro4.getPlaSede(),filtro4.getPlaJornada(),filtro4.getPlaSede(),String.valueOf(filtro4.getPlaMetodologia())));
			}
		}else{
			filtro4=new Encuesta4VO();			
			session.setAttribute("encuesta4VO",filtro4);
		}
		if(nivel.equals("4") || nivel.equals("6")){
			filtro4.setPlaLocalidad(Long.parseLong(usuVO.getLocId()));
			filtro4.setPlaInstitucion(Long.parseLong(usuVO.getInstId()));			
			filtro4.setHabilitarLocalidad(1);
			filtro4.setHabilitarColegio(1);			
			request.setAttribute("listaColegiosVO",encuestaDAO.getColegiosFiltro(filtro4.getPlaLocalidad()));
			request.setAttribute("listaSedeVO",encuestaDAO.getSedes(String.valueOf(filtro4.getPlaInstitucion())));			
		}else if(nivel.equals("1") || nivel.equals("2")){
			usuVO.setMetodologiaId("1");
			filtro4.setHabilitarLocalidad(2);
			filtro4.setHabilitarColegio(2);	
		}
		
	}
	
//	FUNCION GNERACION REPORTE CONSOLIDADO ENCUESTA
	private String filtroPrefDescargar(HttpServletRequest request,Encuesta4VO filtro4,Login login, int modulo)throws Exception{
		Map parameters;
		long usuarioID;
		try {
			
			HttpSession session = request.getSession();
			Login usuVO = (Login) session.getAttribute("login");
			//metodologia = usuVO.getMetodologiaId();
			usuarioID = Long.parseLong(usuVO.getUsuarioId());
			String nivel=usuVO.getNivel();
			String usuario = usuVO.getUsuario();
			boolean reporteEncuesta=false;
			parameters = new HashMap();			
			Zip zip = new Zip();
			encuestaDAO.deleteEncuestados(usuarioID);
			encuestaDAO.insertarEncuestadosTemporalPref(filtro4,usuarioID);
			boolean existenEncuestados = encuestaDAO.getExistenEncuestados(usuarioID);
			if(existenEncuestados){
				parameters.put("LOCALIDAD", new Integer((int)filtro4.getPlaLocalidad()));
				parameters.put("INSTITUCION", new Long(filtro4.getPlaInstitucion()));
				parameters.put("SEDE", new Long(filtro4.getPlaSede()));
				parameters.put("JORNADA", new Long(filtro4.getPlaJornada()));
				parameters.put("METODOLOGIA", new Long(filtro4.getPlaMetodologia()));
				parameters.put("GRADO", new Long(filtro4.getPlaGrado()));
				parameters.put("GRUPO", new Long(filtro4.getPlaGrupo()));				
				parameters.put("USUARIO", usuario);
				parameters.put("ID_USUARIO", new Long(Long.parseLong(usuVO.getUsuarioId())));
				parameters.put("ASIGNATURA", new Integer(filtro4.getFilAsignatura()));
				parameters.put("OPCION", new Integer(filtro4.getFilOpcion()));
				//parameters.put("PREGUNTA", new Integer(filtro4.getNumeroPregunta()));
				if(nivel.equals("4") || nivel.equals("6")){
					parameters.put("SECRETARIA", new Integer(2));
				}else if(nivel.equals("1") || nivel.equals("2")){
					parameters.put("SECRETARIA", new Integer(1));
				}				
				EncuestaVO encuesta = new EncuestaVO();
				encuesta.setPlaLocalidad(filtro4.getPlaLocalidad());
				encuesta.setPlaInstitucion(filtro4.getPlaInstitucion());
				encuesta.setPlaSede(filtro4.getPlaSede());
				encuesta.setPlaJornada(filtro4.getPlaJornada());
				encuesta.setPlaMetodologia(filtro4.getPlaMetodologia());
				encuesta.setPlaGrado(filtro4.getPlaGrado());				
				encuesta.setAsignatura(filtro4.getFilAsignatura());
				encuesta.setOpcion(filtro4.getFilOpcion());
				//encuesta.setNumeroPregunta(filtro4.getNumeroPregunta());
				reporteEncuesta=encuestaDAO.generarReporte(encuesta,request,usuarioID, parameters, modulo, nivel);
				request.setAttribute("modulo", String.valueOf(modulo));
				return s;
			}else{
				request.setAttribute("mensaje", "No hay datos para generar el reporte motivos de preferencia ");
			}					
		} catch (Exception th) {
			th.printStackTrace();
			filtro4.setFormaEstado("-1");
			request.setAttribute("mensaje", "Error interno: "+th.getMessage());
		}
		return FICHA_FILTRO4;
	}
}
