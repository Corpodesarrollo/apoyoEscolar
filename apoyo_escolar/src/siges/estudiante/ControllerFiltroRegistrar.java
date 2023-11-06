package siges.estudiante;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import siges.dao.Cursor;
import siges.dao.Ruta;
import siges.dao.Ruta2;
import siges.estudiante.beans.Basica;
import siges.estudiante.beans.Familiar;
import siges.estudiante.beans.FiltroBean;
import siges.estudiante.beans.Salud;
import siges.estudiante.dao.EstudianteDAO;
import siges.io.Zip;
import siges.login.beans.Login;
import siges.util.Logger;
import siges.util.beans.ReporteVO;
import siges.common.vo.FiltroCommonVO;
import siges.common.vo.ItemVO;
import util.BitacoraCOM;
import util.LogAccionDto;
import util.LogDetalleAccionEstudianteDto;
import util.LogEstudianteDto;

/**
 * Nombre: ControllerFiltroSave<BR>
 * Descripción: Controla la negociacion de busqueda para la vista de resultados <BR>
 * Funciones de la página: Busca los datos y redirige el control a la vista de
 * resultados <BR>
 * Entidades afectadas: Estudiante <BR>
 * Fecha de modificación: 20/07/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */

public class ControllerFiltroRegistrar extends HttpServlet {
	private String mensaje;// mensaje en caso de error
	private String buscar;
	private boolean err;// variable que inidica si hay o no errores en la
						// validacion de los datos del formulario
	private Cursor cursor;// objeto que maneja las sentencias sql
	private EstudianteDAO estudianteDAO;//
	private Basica basica;
	private Login login;
	private FiltroBean filtro;
	private ResourceBundle rb;
	// VARIABLE PARA GENERAR REPORTE
	private HttpServletRequest request;
	private String er;

	/**
	 * Procesa la peticion HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/
	public String process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		rb = ResourceBundle.getBundle("siges.estudiante.bundle.estudiante");
		String sig;
		String ant;
//		String er;
		String home;
//		String sentencia;
//		String boton;
//		String id;
		sig = "/estudiante/ControllerFiltroAdd.do?cmd=!";
//		sig2 = "/estudiante/ControllerNuevoEdit.do";
//		sig3 = "/estudiante/ControllerFiltroSave.do";
		ant = "/index.jsp";
		er = "/error.jsp";
		home = "/bienvenida.jsp";
		cursor = new Cursor();
		err = false;
		mensaje = null;

		try {
			estudianteDAO = new EstudianteDAO(cursor);
			if (!asignarBeans(request)) {
				setMensaje("Error capturando datos de sesión para el usuario");
				request.setAttribute("mensaje", mensaje);
				return er;
			}
			asignarListaNuevo(request);
			registrarAlumno(request);
			//Limpiamos las listas
			borrarBeans(request);
			filtro = new FiltroBean();
			request.getSession().setAttribute("filtroEst", filtro);
			Logger.print(login.getUsuarioId(), "Registro Estudiante de HOJAS DE VIDA, módulo de Adicionar Estudiante. ",
					7, 1, this.toString());
			return sig;
		} catch (Exception e) {
			System.out.println("Error " + this + ":" + e.toString());
			throw new ServletException(e);
		} finally {
			if (cursor != null)
				cursor.cerrar();
			if (estudianteDAO != null)
				estudianteDAO.cerrar();
		}
	}

	public void registrarAlumno(HttpServletRequest request)
			throws ServletException, IOException {
		//1. Validamos si el estudiantes ya está registrado.
		boolean resultado = false;
		String mensaje="";
		HashMap<Long, String> validarEstudiante = estudianteDAO.getEstudianteRestriccion(filtro.getTipoDocumento(),filtro.getId());
		Long idResultado = null;
		String msgResultado = null;
		if(validarEstudiante != null){
			Set<Map.Entry<Long,String>> mapa = validarEstudiante.entrySet();
			Iterator<Map.Entry<Long,String>> ite = mapa.iterator();
			while(ite.hasNext()){
				Map.Entry<Long, String> obj = ite.next();
				idResultado = obj.getKey();
				msgResultado = obj.getValue();
			}
			String accion = "Adición de estudiante";
			if(idResultado != null){
				int tipoAccion = 1;
				switch(String.valueOf(idResultado)){
					case "0": mensaje = msgResultado; break;
					case "1": 
						resultado = estudianteDAO.updateGrupoAlumno(filtro);
						mensaje = " El estudiante fue actualizado satisfactoriamente.";
						tipoAccion=1;
						accion = "Actualización de estudiante";
						break;
					case "2": 
						resultado = estudianteDAO.registrarAlumno(filtro);
						mensaje = " El estudiante fue registrado satisfactoriamente.";
						tipoAccion=2;
						break;
				}
				try
				{
					if (resultado) {
						LogEstudianteDto log = new LogEstudianteDto();
						List<ItemVO> tiposDoc = estudianteDAO.getTiposDoc();
						for(int i=0;i<tiposDoc.size();i++){
							ItemVO obj = tiposDoc.get(i);
							if(obj.getCodigo()==Long.parseLong(filtro.getTipoDocumento())){
								log.setTipodocumento(obj.getNombre());
								break;
							}
						}
						log.setNumeroIdentificacion(filtro.getId());
						log.setNombreCompleto(filtro.getNombre1()+' '+filtro.getNombre2()+' '+filtro.getApellido1()+' '+filtro.getApellido2());
						log.setEstado("Activo");
						LogDetalleAccionEstudianteDto logAccion= new LogDetalleAccionEstudianteDto();
						logAccion.setAccion(accion);
						log.setLogAccion(logAccion);
						
						List<ItemVO> instituciones = estudianteDAO.getListaInst(Long.parseLong(login.getLocId()));
						for(int i=0;i<instituciones.size();i++){
							ItemVO obj = instituciones.get(i);
							if(obj.getCodigo()==filtro.getInstitucion()){
								log.setNombreInstitucion(obj.getNombre());
								break;
							}
						}
						List<ItemVO> sedes = estudianteDAO.getListaSede(filtro.getInstitucion());
						for(int i=0;i<sedes.size();i++){
							ItemVO obj = sedes.get(i);
							if(obj.getCodigo()==Long.parseLong(filtro.getSede())){
								log.setSede(obj.getNombre());
								break;
							}
						}
						List<ItemVO> jornadas = estudianteDAO.getListaJornd(filtro.getInstitucion(), Long.parseLong(filtro.getSede()));
						for(int i=0;i<jornadas.size();i++){
							ItemVO obj = jornadas.get(i);
							if(obj.getCodigo()==Long.parseLong(filtro.getJornada())){
								log.setJornada(obj.getNombre());
								break;
							}
						}
						FiltroCommonVO filtrosVO = new FiltroCommonVO();
						filtrosVO.setFilinst(Integer.parseInt(filtro.getInstitucion().toString()));
						filtrosVO.setFilsede(Integer.parseInt(filtro.getSede().toString()));
						filtrosVO.setFiljornd(Integer.parseInt(filtro.getJornada()));
						filtrosVO.setFilmetod(Integer.parseInt(filtro.getMetodologia()));
						List<ItemVO> grados = estudianteDAO.getListaGrado(filtrosVO);
						for(int i=0;i<grados.size();i++){
							ItemVO obj = grados.get(i);
							if(obj.getCodigo()==Long.parseLong(filtro.getGrado())){
								log.setGrado(obj.getNombre());
								break;
							}
						}
						filtrosVO.setFilgrado(Integer.parseInt(filtro.getGrado()));
						List<ItemVO> grupos = estudianteDAO.getListaGrupo(filtrosVO);
						for(int i=0;i<grupos.size();i++){
							ItemVO obj = grupos.get(i);
							if(obj.getCodigo()==Long.parseLong(filtro.getFilgrupo())){
								log.setGrupo(obj.getNombre());
								break;
							}
						}
						Gson gson = new Gson();
						String jsonString = gson.toJson(log);
						BitacoraCOM.insertarBitacora(Long.parseLong(login.getInstId()), 
								Integer.parseInt(login.getJornadaId()), 2, 
								login.getPerfil(), Integer.parseInt(login.getSedeId()), 
								30, 4, login.getUsuarioId(), jsonString);
					}
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("Error " + this + ":" + e.toString());
				}
				request.setAttribute("mensaje",mensaje);
			}else{
				request.setAttribute("mensaje","Se presento un error.");
			}
					
		} else {
			request.setAttribute("mensaje","Se presento un error.");
		}
		
	}

	/**
	 * Elimina del contexto de la sesion los beans de informacion del usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void borrarBeans(HttpServletRequest request)
			throws ServletException, IOException {
		request.getSession().removeAttribute("listaGrupo");
		request.getSession().removeAttribute("listaGrado");
		request.getSession().removeAttribute("listaMetodo");
		request.getSession().removeAttribute("listaJornada");
		request.getSession().removeAttribute("listaSede");
		request.getSession().removeAttribute("filtroEst");
	}

	/**
	 * Trae de sesion los datos de la credencial del usuario y el formulario de
	 * filtro
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeans(HttpServletRequest request)
			throws ServletException, IOException {
		login = (Login) request.getSession().getAttribute("login");
		filtro = (FiltroBean) request.getSession().getAttribute("filtroEst");
		return true;
	}


	public void asignarListaNuevo(HttpServletRequest request)
			throws ServletException, IOException {

		try {
			Long inst = new Long(Long.parseLong(login.getInstId()));
			filtro.setInstitucion(inst);

//			request.getSession().setAttribute("filtroEstudiantes",
//					estudianteDAO.getBuscarEstudiante(filtro));

		} catch (Throwable th) {
			System.out.println("error " + th);
			th.printStackTrace();
			throw new ServletException(th);
		}
	}

	/**
	 * Recibe la peticion por el metodo get de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);// redirecciona la peticion a doPost
	}

	/**
	 * Recibe la peticion por el metodo Post de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String s = process(request, response);
		if (s != null && !s.equals(""))
			ir(1, s, request, response);
	}

	/**
	 * Redirige el control a otro servlet
	 * 
	 * @param int a: 1=redirigir como 'include', 2=redirigir como 'forward'
	 * @param String
	 *            s
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/
	public void ir(int a, String s, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (cursor != null)
			cursor.cerrar();
		RequestDispatcher rd = getServletContext().getRequestDispatcher(s);
		if (a == 1)
			rd.include(request, response);
		else
			rd.forward(request, response);
	}

	/**
	 * Concatena el parametro en una variable que contiene los mensajes que se
	 * van a enviar a la vista
	 * 
	 * @param String
	 *            s
	 **/
	public void setMensaje(String s) {
		if (!err) {
			err = true;
			mensaje = "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n";
		}
		mensaje += "  - " + s + "\n";
	}
}