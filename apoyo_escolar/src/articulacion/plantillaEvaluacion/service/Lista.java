package articulacion.plantillaEvaluacion.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.perfil.vo.Url;
import articulacion.plantillaEvaluacion.io.ExcelIO;
import articulacion.plantillaEvaluacion.dao.EvaluacionDAO;
import articulacion.plantillaEvaluacion.vo.DatosPlanEvalVO;
import articulacion.plantillaEvaluacion.vo.EvaluacionVO;
import articulacion.plantillaEvaluacion.vo.ParamsVO;
import siges.login.beans.Login;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.dao.Ruta;
import siges.dao.Ruta2;

public class Lista extends Service {
	private String FICHA_LISTA;

	private Cursor cursor;

	private EvaluacionDAO evaluacionDAO;

	private Timestamp f2;

	public String[] process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[] = new String[2];
		cursor = new Cursor();
		evaluacionDAO = new EvaluacionDAO(cursor);
		FICHA_LISTA = config.getInitParameter("FICHA_LISTA");
		int CMD = getCmd(request, session);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_EVALUACION);
		DatosPlanEvalVO datosVO = (DatosPlanEvalVO) session.getAttribute("FilEvaluacionPVO");
		Login usuVO = (Login) session.getAttribute("login");
		// request.setAttribute("listaEscalasVO",evaluacionDAO.getListaRangos(datosVO));
		switch (CMD) {
		case ParamsVO.CMD_FILTRAR:
			if (datosVO != null) {
				List estudiantes = evaluacionDAO.getEstudiantes(datosVO);
				List pruebas = new ArrayList();
				pruebas = evaluacionDAO.getPruebas(datosVO);
				if (estudiantes == null || estudiantes.size() == 0 || pruebas == null || pruebas.size() == 0 ) {
					request.setAttribute("mensaje", "No existen estudiantes o pruebas para esta plantilla y no se puede generar");
				} else {
					try {
						Date date = new Date();

						f2 = new Timestamp(new java.util.Date().getTime());
						String fe1 = f2.toString().replace(':', '-').replace('.', '-').substring(0, 10) + "_" + f2.toString().replace(':', '-').replace('.', '-').substring(11, 19);
						String relativo = null;
						String docente = evaluacionDAO.getDocente(datosVO);

						// lista=plantillaDAO.getJerarquia(usuVO.getInstId(),sede,jor,grado,grupo,usuVO.getMetodologiaId());
						datosVO = evaluacionDAO.getSedeJornada(datosVO);
						List especialidades = new ArrayList();
						// especialidades=plantillaDAO.getEspecialidades(usuVO.getInstId());
						String fe = date.getYear() + "-" + date.getMonth() + "-" + date.getDay() + "_" + date.getHours() + "-" + date.getMinutes() + "-" + date.getSeconds();

						// long[]
						// tipos={Long.parseLong(usuVO.getInstId()),sede,jor,grado};

						ExcelIO excel = new ExcelIO();
						Url url = new Url();
						ResourceBundle rb = ResourceBundle.getBundle("articulacion.plantillaEvaluacion.bundle.sentencias");
						url.setPathPlantilla(Ruta2.get(context, rb.getString("plantillaEvaluacion.PathPlantilla")));
						url.setNombrePlantilla(rb.getString("plantillaEvaluacion.NombrePlantilla"));
						url.setPathDescarga(Ruta.get(context, rb.getString("plantillaEvaluacion.PathDescarga") + "." + usuVO.getUsuarioId()));
						String nombre = "Evaluacion_Articulacion" + "_Sed_" + datosVO.getSede_().trim() + "_Jor_" + datosVO.getJornada_() + "_Gru_" + datosVO.getGrupo_() + "_(" + fe1 + ")";
						nombre = excel.formatear(nombre) + ".xls";
						// excel.formatear(nombre);
						// url.setNombreDescarga(excel.formatear(nombre+".xls"));
						// url.setNombreDescarga(excel.formatear(nombre)+".xls");
						url.setNombreDescarga(nombre);
						url.setPathRelativo(Ruta.getRelativo(context, rb.getString("plantillaEvaluacion.PathDescarga") + "." + usuVO.getUsuarioId()));
						excel.plantilla(datosVO, estudiantes, url, docente, pruebas);
						request.setAttribute("rutaDescarga", url.getPathRelativo() + url.getNombreDescarga());
						request.setAttribute("plantilla", "Si");
						relativo = url.getPathRelativo();
						evaluacionDAO.setReporte(usuVO.getUsuarioId(), relativo + url.getNombreDescarga(), "xls", url.getNombreDescarga(), 1);
					} catch (Exception e) {
						e.printStackTrace();
						request.setAttribute("plantilla", "No");
					}
				}// cierra el else de donde nio hay estudiantes
			}
			break;

		}

		dispatcher[0] = String.valueOf(Params.INCLUDE);
		dispatcher[1] = FICHA_LISTA;

		return dispatcher;
	}

}
