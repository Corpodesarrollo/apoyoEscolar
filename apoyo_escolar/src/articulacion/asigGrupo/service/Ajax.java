package articulacion.asigGrupo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.asigGrupo.dao.AsignacionGrupoDAO;
import articulacion.asigGrupo.vo.DatosVO;
import articulacion.asigGrupo.vo.EstudianteArtVO;
import articulacion.asigGrupo.vo.ParamsVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.login.beans.Login;

public class Ajax extends Service {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String FICHA;

	private String FICHA_AJAX;
	private String FICHA_LISTA_CARGA;

	private Cursor cursor;

	private AsignacionGrupoDAO asignacionGrupoDAO;

	public String[] process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dispatcher[] = new String[2];
		try {
			HttpSession session = request.getSession();
			cursor = new Cursor();
			asignacionGrupoDAO = new AsignacionGrupoDAO(cursor);
			
			FICHA_AJAX = config.getInitParameter("FICHA_AJAX");
			FICHA_LISTA_CARGA = config.getInitParameter("FICHA_LISTA_CARGA");
			
			int CMD = getCmd(request, session, ParamsVO.CMD_AJAX);
			int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
			Login usuVO = (Login) session.getAttribute("login");
			DatosVO datos = (DatosVO) request.getSession().getAttribute("datosVO");
			switch (TIPO) {
			case ParamsVO.FICHA_ASIGNAR_GRUPO:
				FICHA = this.FICHA_AJAX;
				switch (CMD) {
				case ParamsVO.CMD_AJAX_GET_ASIGNATURA:
					try{
						long institucion = Long.parseLong(usuVO.getInstId());
						int anho = Integer.parseInt(request.getParameter("anho"));
						long especialidad = Long.parseLong(request.getParameter("especialidad")); 
						int semestre = Integer.parseInt(request.getParameter("semestre"));
						request.getSession().setAttribute("lAsignatura", asignacionGrupoDAO.getAjaxAsignatura(institucion, anho, especialidad, semestre));
						request.getSession().setAttribute("ajaxCMD", ""+ParamsVO.CMD_AJAX_GET_ASIGNATURA);
					}catch (Exception e) {
						System.out.println("Fallo Parsing en AJAX ASIG GRUPO - Get Asignatura: "+e.toString());
					}
					
					
					break;
				case ParamsVO.CMD_AJAX_GET_GRUPOS:
					try{
						long institucion = Long.parseLong(usuVO.getInstId());
						int grado = Integer.parseInt(request.getParameter("grado"));
						int metodologia = Integer.parseInt(request.getParameter("metodologia"));
						
						request.getSession().setAttribute("lGrupos", asignacionGrupoDAO.getAjaxGrupos(institucion, Integer.parseInt(usuVO.getSedeId()), Integer.parseInt(usuVO.getJornadaId()),grado, metodologia));
						request.getSession().setAttribute("ajaxCMD", ""+ParamsVO.CMD_AJAX_GET_GRUPOS);
					}catch (Exception e) {
						System.out.println("Fallo Parsing en AJAX ASIG GRUPO - Get Grupos: "+e.toString());
					}
					break;
				case ParamsVO.CMD_AJAX_GUARDAR:
					try{
						long asignatura = Long.parseLong(request.getParameter("asignatura"));
						long grupo = Long.parseLong(request.getParameter("grupo"));
						List listaGruposArt = (List)request.getSession().getAttribute("lGruposArt");
						List listaEstudiantes = (List)request.getSession().getAttribute("lEstudiantesArt");
						List listaEstudiantesAAsignar = new ArrayList();
						if(listaEstudiantes != null){
							Iterator iteradorListaEstudiantes = listaEstudiantes.iterator();
							while (iteradorListaEstudiantes.hasNext()){
								EstudianteArtVO estudiante = (EstudianteArtVO) iteradorListaEstudiantes.next();
								if(request.getParameter(""+estudiante.getEstCodigo()) != null){
									String[] seleccionados = request.getParameterValues(""+estudiante.getEstCodigo());
									for(int i = 0; i<seleccionados.length;i++){
										estudiante.setArtGrupoCodigo(seleccionados[i]);
										listaEstudiantesAAsignar.add(estudiante);
									}
								}
							}
							if(listaGruposArt != null && listaGruposArt.size() > 0){
								asignacionGrupoDAO.eliminarEstudiantesGrupoAsignatura(listaGruposArt, asignatura);
							}if(listaEstudiantesAAsignar != null && listaEstudiantesAAsignar.size() >0){
								asignacionGrupoDAO.asignarEstudiantes(listaEstudiantesAAsignar, asignatura);
							}
						}
						
						
					}catch (Exception e) {
						System.out.println("Fallo Parsing en AJAX GUARDAR - guardar: "+e.toString());
					}
					break;
				}
				break;
			case ParamsVO.FICHA_BUSCAR:
				switch (CMD) {
				case ParamsVO.CMD_AJAX_BUSCAR:
					try{
						long institucion = Long.parseLong(usuVO.getInstId());
						/*int anho = Integer.parseInt(request.getParameter("anho"));
						int semestre = Integer.parseInt(request.getParameter("semestre"));
						long especialidad = Long.parseLong(request.getParameter("especialidad"));
						long asignatura = Long.parseLong(request.getParameter("asignatura"));
						long grupo = Long.parseLong(request.getParameter("grupo"));
						*/
						List listaGrupos = asignacionGrupoDAO.getAjaxGruposArticulacion(institucion, Integer.parseInt(usuVO.getSedeId()), Integer.parseInt(usuVO.getJornadaId()), datos.getAnho(), datos.getSemestre(), datos.getEspecialidad(), datos.getAsignatura());
						request.getSession().setAttribute("lGruposArt", listaGrupos);
						request.getSession().setAttribute("lEstudiantesArt", asignacionGrupoDAO.getEstudiantes(datos.getGrupo(), datos.getAnho()));
						
						if(listaGrupos != null && listaGrupos.size() >0){
							request.removeAttribute("lAsignaciones");
							request.getSession().setAttribute("lAsignaciones",asignacionGrupoDAO.getListaAsignaciones(listaGrupos));
						}
						request.getSession().setAttribute("ajaxCMD", ""+ParamsVO.CMD_AJAX_BUSCAR);
						FICHA = FICHA_LISTA_CARGA;
					}catch (Exception e) {
						System.out.println("Fallo Parsing en AJAX ASIG GRUPO - Get Grupos: "+e.toString());
					}
					break;
				}
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		dispatcher[0] = String.valueOf(Params.INCLUDE);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

}
