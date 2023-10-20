package articulacion.inscripcion.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.inscripcion.dao.InscripcionDAO;
import articulacion.inscripcion.vo.*;
import siges.login.beans.Login;
import articulacion.inscripcion.vo.DatosVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;


public class Lista extends Service{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String FICHA_LISTA;
	private InscripcionDAO espDAO=new InscripcionDAO(new Cursor());
	
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[]=new String[2];
		FICHA_LISTA=config.getInitParameter("FICHA_LISTA");
		DatosVO datosVO=(DatosVO)session.getAttribute("filtroInscripcionVO");
		Login usuVO=(Login)session.getAttribute("login");
		long colegio=0,estudiante=0;
		int sede=0;
		if(usuVO!=null){
		    colegio=Long.parseLong(usuVO.getInstId());
		    sede=Integer.parseInt(usuVO.getSedeId());
		    estudiante=Long.parseLong(usuVO.getCodigoEst());
		}
		if(datosVO!=null){
			FiltroInscripcionVO  filtro=new FiltroInscripcionVO();
			filtro.setFilInstitucion(colegio);
			filtro.setFilSede(sede);
			filtro.setFilJornada(datosVO.getJornada());
			filtro.setFilComponente(datosVO.getComponente());
			filtro.setFilEspecialidad(datosVO.getEspecialidad());
			filtro.setFilSemestre(datosVO.getSemestre());
			filtro.setFilEstudiante(estudiante);
			session.setAttribute("filtroInscripcion2VO",filtro);
			request.setAttribute("listaAsignaturaVO",espDAO.getAllListaAsignatura(filtro));
		}
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FICHA_LISTA;
		return dispatcher;
	}
}
