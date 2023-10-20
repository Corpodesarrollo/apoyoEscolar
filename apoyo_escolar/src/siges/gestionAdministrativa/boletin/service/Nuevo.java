/**
 * 
 */
package siges.gestionAdministrativa.boletin.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.service.Service;
import siges.common.vo.ItemVO;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.dao.Ruta;
import siges.gestionAdministrativa.boletin.dao.BoletinDAO;
import siges.gestionAdministrativa.boletin.io.BoletinIO;
import siges.gestionAdministrativa.boletin.vo.ParamsVO;
import siges.gestionAdministrativa.boletin.vo.PlantillaBoletionVO;
import siges.gestionAdministrativa.boletin.vo.ResultadoConsultaVO;
import siges.login.beans.Login;

/**
 * 28/04/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class Nuevo extends Service {

	private static final long serialVersionUID = 1L;
	public String FICHA_PLANTILLA_BOLENTIN;
	private ResourceBundle rb;
	private BoletinDAO boletinDAO;
	private Login usuVO;

	/**
	 * 
	 /*
	 * 
	 * @function:
	 * @param config
	 * @throws ServletException
	 *             (non-Javadoc)
	 * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		boletinDAO = new BoletinDAO(new Cursor());
		rb = ResourceBundle
				.getBundle("siges.gestionAdministrativa.boletin.bundle.boletin");
		FICHA_PLANTILLA_BOLENTIN = config
				.getInitParameter("FICHA_PLANTILLA_BOLETIN");
	}

	/*
	 * Procesa la peticinn
	 * 
	 * @param request peticinn
	 * 
	 * @param response respuesta
	 * 
	 * @return Ruta de redireccinn
	 * 
	 * @throws ServletException
	 * 
	 * @throws IOException (non-Javadoc)
	 * 
	 * @see
	 * siges.common.service.Service#process(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String dispatcher[] = new String[2];
		String FICHA = null;

		HttpSession session = request.getSession();
		usuVO = (Login) session.getAttribute("login");
		int CMD = getCmd(request, session, ParamsVO.CMD_NUEVO);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_PLANTILLA_BOLETIN);

		// System.out.println("BOELTIN TIPO " + TIPO +"   CMD "+ CMD);
		switch (TIPO) {
		case ParamsVO.FICHA_PLANTILLA_BOLETIN:

			FICHA = FICHA_PLANTILLA_BOLENTIN;

			switch (CMD) {
			case ParamsVO.CMD_DEFAULT:
			case ParamsVO.CMD_NUEVO:
				// System.out.println("ENTRO POR DEFAULT");
				request.getSession().removeAttribute("plantillaBoletionVO");
				request.getSession().removeAttribute("listaJornada");
				request.getSession().removeAttribute("listaMetodo");
				request.getSession().removeAttribute("listaGrado");
				request.getSession().removeAttribute("listaGrupo");

				request.getSession().removeAttribute("resultadoConsulta");
				break;
			case ParamsVO.CMD_GUARDAR:
				generar(request, session, usuVO);

				break;

			}
			cargarListasNivelEval(request, session, usuVO);
			break;
		}
		dispatcher[0] = String.valueOf(Params.INCLUDE);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	/**
	 * @function: Carga por defecto las lista
	 * @param request
	 * @param session
	 * @param usuVO
	 * @throws ServletException
	 */
	private void cargarListasNivelEval(HttpServletRequest request,
			HttpSession session, Login usuVO) throws ServletException {

		try {
			request.getSession().setAttribute("listaSede",
					boletinDAO.getSede(Long.parseLong(usuVO.getInstId())));
			request.getSession().setAttribute(
					"filtroPeriodo",
					getListaPeriodo(usuVO.getLogNumPer(),
							usuVO.getLogNomPerDef()));
			request.setAttribute("filtroTiposDoc", boletinDAO.getTiposDoc());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 * @function: Carga la lista de los periodos
	 * @param numPer
	 * @param nomPerDef
	 * @return
	 */
	public List getListaPeriodo(long numPer, String nomPerDef) {
		List l = new ArrayList();
		ItemVO item = null;
		for (int i = 1; i <= numPer; i++) {
			item = new ItemVO(i, "" + i);
			l.add(item);
		}
		item = new ItemVO(7, nomPerDef);
		l.add(item);
		return l;
	}

	/**
	 * @function:
	 * @param request
	 * @param session
	 * @param usuVO
	 * @throws ServletException
	 */
	public void generar(HttpServletRequest request, HttpSession session,
			Login usuVO) throws ServletException {
		// System.out.println("generar");
		try {
			ResultadoConsultaVO resultado = null;
			PlantillaBoletionVO plantillaBoletionVO = (PlantillaBoletionVO) request
					.getSession().getAttribute("plantillaBoletionVO");
			request.getSession().removeAttribute("resultadoConsulta");

			if (plantillaBoletionVO != null) {

				plantillaBoletionVO.setPlabolvigencia(boletinDAO
						.getVigenciaInst(Long.parseLong(usuVO.getInstId())));
				plantillaBoletionVO.setPlabolinstBNomb(usuVO.getInst());
				plantillaBoletionVO.setPlabolinst(Long.parseLong(usuVO
						.getInstId()));
				plantillaBoletionVO.setPlabolinstResolucion(boletinDAO
						.getResolInst(plantillaBoletionVO.getPlabolinst()));
				plantillaBoletionVO.setPlabolconvmen(boletinDAO
						.getListaMEN(plantillaBoletionVO.getPlabolinst()));
				plantillaBoletionVO.setPlabolconinst(boletinDAO
						.getEscalaNivelEval(plantillaBoletionVO));
				plantillaBoletionVO.setPlabolinstDane(boletinDAO
						.getDANE(plantillaBoletionVO));
				plantillaBoletionVO.setPlabolcoordNom(boletinDAO
						.getCoordinadorNombre(plantillaBoletionVO));
				plantillaBoletionVO.setFilRutaBase(context.getRealPath("/"));
				String path_escudo = Ruta.get(context.getRealPath("/"),
						rb.getString("boletines_imgs_inst"));
				plantillaBoletionVO.setFilUsuario(usuVO.getUsuarioId());

				BoletinIO consultaIO = new BoletinIO(boletinDAO);
				resultado = consultaIO.buscarPlantillaBoletion(
						plantillaBoletionVO, path_escudo);

				if (resultado.isGenerado()) {
					request.getSession().setAttribute("resultadoConsulta",
							resultado);
					request.getSession().setAttribute(ParamsVO.SMS,
							"Plantilla Boletin generada satisfactoriamente");
					// String modulo,String us,String rec,String tipo,String
					// nombre,String estado
					boletinDAO.ponerReporte("3", usuVO.getUsuarioId(),
							resultado.getRuta(), "zip",
							"" + resultado.getArchivo(), "1");// Estado -1
				} else {
					request.setAttribute(ParamsVO.SMS,
							"Plantilla Boletion no generada: No hay datos para generar el reporte");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, e.getMessage());
		}
	}

	/**
	 * @function: Metodo que se encarga de leer el archivo del disco y lo coloca
	 *            en el response para que el cliente lo pueda descargar,
	 *            finalmente lo borra de disco
	 * @param request
	 * @param response
	 * @param usuVO
	 * @param resultado
	 * @throws ServletException
	 */
	public void enviarArchivo(HttpServletRequest request,
			HttpServletResponse response, Login usuVO,
			ResultadoConsultaVO resultado) throws ServletException {
		// System.out.println("---------enviarArchivo");
		response.setContentType("application/zip");

		BufferedInputStream origin = null;
		ServletOutputStream ouputStream = null;
		FileInputStream fi = null;
		int BUFFER_SIZE = 1000000;
		int count;
		File f = null;
		try {

			if (resultado.isGenerado()) {
				StringBuffer contentDisposition = new StringBuffer();

				contentDisposition.append("attachment;");
				contentDisposition.append("filename=\"");
				contentDisposition.append(resultado.getArchivo());
				// System.out.println("resultado.getArchivo() " +
				// resultado.getArchivo());
				contentDisposition.append("\"");
				response.setHeader("Content-Disposition",
						contentDisposition.toString());
				response.setHeader("Content-Disposition",
						"attachment; filename=\"" + resultado.getArchivo()
								+ "\"");

				f = new File(resultado.getRutaDisco());
				if (!f.exists()) {
					return;
				}
				System.out.println("bien 1");
				byte[] data = new byte[BUFFER_SIZE];
				fi = new FileInputStream(f);
				origin = new BufferedInputStream(fi, BUFFER_SIZE);
				ouputStream = response.getOutputStream();
				while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
					// System.out.println("bien 12*");
					ouputStream.write(data, 0, count);
				}
				ouputStream.flush();
				ouputStream.close();
				origin.close();
				fi.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error " + e.getMessage());
			throw new ServletException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error " + e.getMessage());
			throw new ServletException(e.getMessage());
		} finally {
			try {
				if (ouputStream != null)
					ouputStream.close();
				if (origin != null)
					origin.close();
				if (fi != null)
					fi.close();

				try {
					f = new File(resultado.getRutaDisco());
					if (f.exists()) {

						f.delete();
						System.out.println("Borro sin problema "
								+ resultado.getRutaDisco());
					}
				} catch (Exception e) {

					e.printStackTrace();
					System.out.println("Error " + e.getMessage());
					throw new ServletException(e.getMessage());
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error finally " + e.getMessage());
			}
		}
	}
}
