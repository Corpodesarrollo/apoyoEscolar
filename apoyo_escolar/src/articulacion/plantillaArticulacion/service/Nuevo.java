package articulacion.plantillaArticulacion.service;

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

import articulacion.grupoArt.vo.ParamsVO;
import siges.login.beans.Login;
import articulacion.plantillaArticulacion.io.ExcelIO;
import siges.perfil.vo.Url;
import articulacion.plantillaArticulacion.vo.DatosVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.dao.Ruta;
import siges.dao.Ruta2;
import articulacion.plantillaArticulacion.dao.PlantillaDAO;

public class Nuevo extends Service {
	private String FICHA_NUEVO;

	private Timestamp f2;

	private PlantillaDAO plantillaDAO = new PlantillaDAO(new Cursor());

	public String[] process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dispatcher[] = new String[2];
		HttpSession session = request.getSession();
		long sede = 0;
		long jor = 0;
		long grado = 0;
		long grupo = 0;
		DatosVO datosVO = (DatosVO) session.getAttribute("filtroPlantillaArticulacionVO");
		Login usuVO = (Login) session.getAttribute("login");
		// ****************************************************************
		FICHA_NUEVO = config.getInitParameter("FICHA_NUEVO");
		//int CMD = getCmd(request, session, Params.CMD_NUEVO);
		//int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		if (datosVO != null) {
			sede = datosVO.getSede();
			jor = datosVO.getJornada();
			grado = datosVO.getGrado();
			grupo = datosVO.getGrupo();
			try {
				f2 = new Timestamp(new java.util.Date().getTime());
				String fe1 = f2.toString().replace(':', '-').replace('.', '-').substring(0, 10) + "_" + f2.toString().replace(':', '-').replace('.', '-').substring(11, 19);
				// System.out.println("esto es "+fe1);
				String relativo = null;
				List lista = new ArrayList();

				lista = plantillaDAO.getEstudiantes(usuVO.getInstId(), sede, jor, grado, grupo, usuVO.getMetodologiaId());
				DatosVO dat = new DatosVO();
				dat = plantillaDAO.getSedeJornada(usuVO.getInstId(), sede, jor, grado, grupo, usuVO.getMetodologiaId());
				List especialidades = new ArrayList();
				especialidades = plantillaDAO.getEspecialidades(usuVO.getInstId());
				long[] tipos = { Long.parseLong(usuVO.getInstId()), sede, jor, grado, grupo };

				ExcelIO excel = new ExcelIO();
				Url url = new Url();
				ResourceBundle rb = ResourceBundle.getBundle("articulacion.plantillaArticulacion.bundle.sentencias");
				url.setPathPlantilla(Ruta2.get(context, rb.getString("estudiante.PathPlantilla")));
				url.setNombrePlantilla(rb.getString("estudiante.NombrePlantilla"));
				url.setPathDescarga(Ruta.get(context, rb.getString("estudiante.PathDescarga") + "." + usuVO.getUsuarioId()));
				String nombre = "Estudiante" + "_Sed_" + dat.getSede_().trim() + "_Jor_" + dat.getJornada_() + "_Met_" + usuVO.getMetodologia() + "_Gra_" + datosVO.getGrado() + "_(" + fe1 + ")";
				nombre = excel.formatear(nombre) + ".xls";
				url.setNombreDescarga(nombre);
				url.setPathRelativo(Ruta.getRelativo(context, rb.getString("estudiante.PathDescarga") + "." + usuVO.getUsuarioId()));
				excel.plantilla(lista, usuVO.getInst(), datosVO.getGrado(), dat, especialidades, url, tipos);
				request.setAttribute("rutaDescarga", url.getPathRelativo() + url.getNombreDescarga());
				request.setAttribute("plantilla", "Si");
				// inserta el reporte con estado 1
				relativo = url.getPathRelativo();
				plantillaDAO.setReporte(usuVO.getUsuarioId(), relativo + url.getNombreDescarga(), "xls", url.getNombreDescarga(), 1);
				// System.out.println("llego
				// aca:"+url.getPathRelativo()+url.getNombreDescarga());
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("plantilla", "No");
			}
		}
		dispatcher[0] = String.valueOf(Params.FORWARD);
		dispatcher[1] = FICHA_NUEVO;
		return dispatcher;
	}
}
