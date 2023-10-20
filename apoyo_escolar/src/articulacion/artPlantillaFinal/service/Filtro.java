/**
 * 
 */
package articulacion.artPlantillaFinal.service;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.artPlantillaFinal.dao.PlantillaFinalDAO;
import articulacion.artPlantillaFinal.io.Excel;
import articulacion.artPlantillaFinal.vo.PlantillaFinalVO;
import siges.common.service.Service;
import siges.common.vo.Url;
import siges.dao.Cursor;
import siges.dao.Ruta;
import siges.dao.Ruta2;
import siges.evaluacion.beans.FiltroComportamiento;
import siges.login.beans.Login;
import articulacion.artPlantillaFinal.vo.ParamsVO;

/**
 * 5/12/2007
 * 
 * @author Latined
 * @version 1.2
 */
public class Filtro extends Service {
	private String FICHA;
	private String FICHA_FILTRO;
	private String FICHA_FILTRO_IMP;
	private String FICHA_FILTRO_EVAL;
	private String FICHA_FILTRO_EVAL2;

	private PlantillaFinalDAO plantillaFinalDAO = new PlantillaFinalDAO(new Cursor());

	public String[] process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[] = new String[2];
		Login usuVO=(Login)session.getAttribute("login");
		PlantillaFinalVO filtro = (PlantillaFinalVO) session.getAttribute("plantillaFinalVO");
		FICHA_FILTRO = config.getInitParameter("FICHA_FILTRO");
		FICHA_FILTRO_IMP = config.getInitParameter("FICHA_FILTRO_IMP");
		FICHA_FILTRO_EVAL = config.getInitParameter("FICHA_FILTRO_EVAL");
		FICHA_FILTRO_EVAL2 = config.getInitParameter("FICHA_FILTRO_EVAL2");
		int CMD = getCmd(request, session, ParamsVO.CMD_NUEVO);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_FILTRO_EVAL);
//		System.out.println("LOS VALORES===" + CMD + "//" + TIPO);
		try {
			switch (TIPO) {
				case ParamsVO.FICHA_FILTRO:
					FICHA=FICHA_FILTRO;
					switch (CMD) {
					case ParamsVO.CMD_NUEVO:
						filtroNuevo(request,session,filtro,usuVO);
						break;
					case ParamsVO.CMD_BUSCAR:
						filtroNuevo(request,session,filtro,usuVO);
						filtroBuscar(request,filtro,usuVO);
						break;
					}
				break;
				case ParamsVO.FICHA_FILTRO_IMP:
					FICHA=FICHA_FILTRO_IMP;
					switch (CMD) {
					case ParamsVO.CMD_NUEVO:
						//filtroImpNuevo(request,session,filtro);
					break;
					}
				break;
				case ParamsVO.FICHA_FILTRO_EVAL:
					switch (CMD) {
					case ParamsVO.CMD_NUEVO:
						FICHA=FICHA_FILTRO_EVAL;
						filtroEvalNuevo(request,session,filtro,usuVO);
						break;
					case ParamsVO.CMD_BUSCAR:
						filtroEvalNuevo(request,session,filtro,usuVO);
						FICHA=filtroEvalBuscar(request,filtro,usuVO);
						break;
					case ParamsVO.CMD_GUARDAR:
						filtroEvalNuevo(request,session,filtro,usuVO);
						filtroEvalGuardar(request,filtro,usuVO);
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
	
	
	private void filtroNuevo(HttpServletRequest request,HttpSession session,PlantillaFinalVO filtro,Login usuVO)throws Exception{
		request.setAttribute("listaSedeVO",plantillaFinalDAO.getSedes(usuVO.getInstId()));
		request.setAttribute("listaJornadaVO",plantillaFinalDAO.getJornada(usuVO.getInstId(),usuVO.getSedeId()));
		request.setAttribute("listaVigencia",plantillaFinalDAO.getListaVigencia());
		if(filtro!=null && filtro.getPlaInstitucion()>0){
			request.setAttribute("listaGrupoVO", plantillaFinalDAO.getAjaxGrupo(filtro.getPlaInstitucion(),filtro.getPlaSede(),filtro.getPlaJornada(),filtro.getPlaGrado(),usuVO.getMetodologiaId()));
			if(filtro.getPlaComponente()==2){
				request.setAttribute("listaEspecialidadVO", plantillaFinalDAO.getAjaxEspecialidad(filtro.getPlaInstitucion()));
			}
			filtro.setFormaEstado("0");
		}else{
			filtro=new PlantillaFinalVO();
			filtro.setPlaAnhoVigencia((int)plantillaFinalDAO.getVigenciaNumerico());
			filtro.setFormaEstado("0");
			session.setAttribute("plantillaFinalVO",filtro);
		}
	}
	
	private void filtroBuscar(HttpServletRequest request,PlantillaFinalVO filtro,Login login)throws Exception{
		try {
			//estudiante con nota
			List listaAsignaturas = plantillaFinalDAO.getAsignaturaPlantilla(filtro);
			if(listaAsignaturas==null || listaAsignaturas.size()==0){
				request.setAttribute("mensaje", "No hay asignaturas en el grupo indicado");
				filtro.setFormaEstado("-1");
				filtro.setPlaMensaje("No hay asignaturas en el grupo indicado");
				return;
			}
			List listaEstudiantes = plantillaFinalDAO.getEstudiantePlantilla(filtro,listaAsignaturas);
			if(listaEstudiantes==null || listaEstudiantes.size()==0){
				request.setAttribute("mensaje", "No hay estudiantes en el grupo indicado");
				filtro.setFormaEstado("-1");
				filtro.setPlaMensaje("No hay estudiantes en el grupo indicado");
				return;
			}
			//poner atributos de encabezado
			filtro.setPlaNombreInstitucion(login.getInst());
			filtro.setPlaNombreSede(login.getSede());
			filtro.setPlaNombreJornada(login.getJornada());
			filtro.setPlaNombreMetodologia(login.getMetodologia());
			filtro.setPlaTotalAsignaturas(listaAsignaturas.size());
			filtro.setPlaTotalEstudiantes(listaEstudiantes.size());
			filtro.setPlaTipo(ParamsVO.PLANTILLA_FINAL);
			plantillaFinalDAO.getParametrosPlantilla(filtro);
			Url url=new Url();
			ResourceBundle rb= ResourceBundle.getBundle("path"); 
			url.setPathPlantilla(Ruta2.get(context,rb.getString("plantillaFinal.PathPlantilla")));
			url.setNombrePlantilla(rb.getString("plantillaFinal.Plantilla"));
			url.setPathDescarga(Ruta.get(context,rb.getString("plantillaFinal.PathDownload")+"."+login.getUsuarioId()));
			url.setNombreDescarga(getNombrePlantilla(filtro));
			url.setPathRelativo(Ruta.getRelativo(context,rb.getString("plantillaFinal.PathDownload")+"."+login.getUsuarioId()));
			Excel excel=new Excel();
			excel.plantillaFinal(filtro,listaEstudiantes,listaAsignaturas,url);
			filtro.setPlaUrl(url.getPathRelativo()+url.getNombreDescarga());
			filtro.setPlaMensaje("La plantilla fue generada satisfactoriamente");
			filtro.setFormaEstado("1");
		} catch (Exception th) {
			th.printStackTrace();
			filtro.setFormaEstado("-1");
			filtro.setPlaMensaje("Error interno: "+th.getMessage());
			//throw new ServletException(th);
		}
	}
	private String getNombrePlantilla(PlantillaFinalVO filtro) {
		java.sql.Date d = new java.sql.Date(new java.util.Date().getTime());
		String sed = formatear(filtro.getPlaNombreSede().length()>20?filtro.getPlaNombreSede().substring(0,20):filtro.getPlaNombreSede());
		String jo = formatear(filtro.getPlaNombreJornada());
		String met = formatear(filtro.getPlaNombreMetodologia());
		String gra = formatear(filtro.getPlaNombreGrado());
		String gru = formatear(filtro.getPlaNombreGrupo());
		String comp = formatear(filtro.getPlaNombreComponente());
		String esp = formatear(filtro.getPlaNombreEspecialidad());
		String per = formatear(String.valueOf(filtro.getPlaAnhoVigencia())+"-"+String.valueOf(filtro.getPlaPerVigencia()));
		String fec = formatear(d.toString());
		return ("Evaluacion_Final" + "_Sed_" + sed + "_Jor_" + jo + "_Met_" + met + "_Gra_" + gra + "_Gru_" + gru + "_Vig_" + per +"_Comp_"+comp+"_Esp_"+esp+ "_{" + fec + ").xls");
	}
	
	private static String formatear(String a) {
		return (a.replace(' ', '_').replace('n', 'a').replace('n', 'e').replace('n', 'i').replace('n', 'o').replace('n', 'u').replace('n', 'A').replace('n', 'E').replace('n', 'I').replace('n', 'O').replace('n', 'U').replace('n', 'n').replace('n', 'N').replace('n', 'a').replace('n', 'e').replace('n', 'i').replace('n', 'o').replace('n', 'u').replace('n', 'A').replace('n', 'E').replace('n', 'I').replace('n', 'O').replace('n', 'U').replace('n', 'c').replace(':', '_').replace('.', '_').replace('/', '_').replace('\\', '_'));
	}

	private void filtroEvalNuevo(HttpServletRequest request,HttpSession session,PlantillaFinalVO filtro,Login usuVO)throws Exception{
		request.setAttribute("listaSedeVO",plantillaFinalDAO.getSedes(usuVO.getInstId()));
		request.setAttribute("listaJornadaVO",plantillaFinalDAO.getJornada(usuVO.getInstId(),usuVO.getSedeId()));
		request.setAttribute("listaVigencia",plantillaFinalDAO.getListaVigencia());
		if(filtro!=null && filtro.getPlaInstitucion()>0){
			request.setAttribute("listaGrupoVO", plantillaFinalDAO.getAjaxGrupo(filtro.getPlaInstitucion(),filtro.getPlaSede(),filtro.getPlaJornada(),filtro.getPlaGrado(),usuVO.getMetodologiaId()));
			if(filtro.getPlaComponente()==2){
				request.setAttribute("listaEspecialidadVO", plantillaFinalDAO.getAjaxEspecialidad(filtro.getPlaInstitucion()));
			}
		}else{
			filtro=new PlantillaFinalVO();
			filtro.setPlaAnhoVigencia((int)plantillaFinalDAO.getVigenciaNumerico());
			session.setAttribute("plantillaFinalVO",filtro);
		}
	}
	private String filtroEvalBuscar(HttpServletRequest request,PlantillaFinalVO filtro,Login login)throws Exception{
		try {
			//estudiante con nota
			List listaAsignaturas = plantillaFinalDAO.getAsignaturaPlantilla(filtro);
			if(listaAsignaturas==null || listaAsignaturas.size()==0){
				request.setAttribute("mensaje", "No hay asignaturas en el grupo indicado");
				filtro.setFormaEstado("-1");
				return FICHA_FILTRO_EVAL;
			}
			List listaEstudiantes = plantillaFinalDAO.getEstudianteEval(filtro,listaAsignaturas);
			if(listaEstudiantes==null || listaEstudiantes.size()==0){
				request.setAttribute("mensaje", "No hay estudiantes en el grupo indicado");
				filtro.setFormaEstado("-1");
				return FICHA_FILTRO_EVAL;
			}
			filtro.setPlaNotaMax(plantillaFinalDAO.getNotaMax());
			request.setAttribute("listaEstudiante",listaEstudiantes);
			request.setAttribute("listaAsignatura",listaAsignaturas);
			filtro.setFormaEstado("1");
		} catch (Exception th) {
			th.printStackTrace();
			filtro.setFormaEstado("-1");
			request.setAttribute("mensaje", "Error interno: "+th.getMessage());
		}
		return FICHA_FILTRO_EVAL2;
	}

	private void filtroEvalGuardar(HttpServletRequest request,PlantillaFinalVO filtro,Login login)throws Exception{
		try {
			String est[]=request.getParameterValues("plaEst");
			String nota[]=request.getParameterValues("plaData");
			String asig[]=request.getParameterValues("plaAsig");
			plantillaFinalDAO.insertarEvaluacionFinal(filtro,est,asig,nota);
			request.setAttribute("mensaje", "La información fue registrada satisfactoriamente");
			filtro.setFormaEstado("1");
		} catch (Exception th) {
			th.printStackTrace();
			filtro.setFormaEstado("-1");
			request.setAttribute("mensaje", "Error interno: "+th.getMessage());
		}
	}
}
