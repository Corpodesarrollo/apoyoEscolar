package siges.gestionAdministrativa.boletinPublico.service;

//	VERSION		FECHA		AUTOR			DESCRIPCION
//		1.0		02/07/2020	JORGE CAMACHO	Se modificaron los métodos process() y generarBoletin() para hacer más comprensible el debug
//		2.0		28/09/2020	JORGE CAMACHO	Se modificó el método generarBoletin() para habilitar la verificación de si le está permitido al
//											estudiante o acuediente la generación del boletín

import java.io.File;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.util.ResourceBundle;
import java.text.SimpleDateFormat;
import java.io.BufferedInputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import siges.dao.Ruta;
import siges.dao.Ruta2;
import siges.dao.Cursor;
import siges.common.vo.ItemVO;
import siges.common.vo.Params;
import siges.login.beans.Login;
import siges.common.service.Service;
import siges.gestionAdministrativa.boletinPublico.vo.ParamsVO;
import siges.gestionAdministrativa.boletinPublico.vo.DatosBoletinVO;
import siges.gestionAdministrativa.boletinPublico.vo.ConsultaExterma;
import siges.gestionAdministrativa.boletinPublico.io.ConsultaPublicoIO;
import siges.gestionAdministrativa.boletinPublico.dao.BoletinPublicoDAO;
import siges.gestionAdministrativa.boletinPublico.vo.ResultadoConsultaVO;
import siges.gestionAdministrativa.boletinPublico.vo.PlantillaBoletionPubVO;

/**
 * 28/04/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class Nuevo extends Service {

	public String FICHA_PLANTILLA_BOLENTIN;
	
	private Login usuVO;
	private ResourceBundle rb;
	private String contextoTotal;
	private ResourceBundle rbPathGeneral;
	private BoletinPublicoDAO boletinPublicoDAO;
	private ConsultaPublicoIO consultaPublicoIO = null;
	private SimpleDateFormat formaFecha = new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss.SSS");

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
		
		boletinPublicoDAO = new BoletinPublicoDAO(new Cursor());

		rb = ResourceBundle.getBundle("siges.gestionAdministrativa.boletinPublico.bundle.boletinPublico");
		rbPathGeneral = ResourceBundle.getBundle("path");
		FICHA_PLANTILLA_BOLENTIN = config.getInitParameter("FICHA_PLANTILLA_BOLETIN");
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
	public String[] process(HttpServletRequest request,	HttpServletResponse response) throws ServletException, IOException {
		
		String FICHA = null;
		String dispatcher[] = new String[2];
		contextoTotal = context.getRealPath("/");

		HttpSession session = request.getSession();
		usuVO = (Login) session.getAttribute("login");

		int CMD = getCmd(request, session, ParamsVO.CMD_NUEVO);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_PLANTILLA_BOLETIN);
		
		String pin = "";
		long consecutivoBoletin = 0;
		
		if(request.getParameter("Pin_Documento") != null) {
			
			pin = request.getParameter("Pin_Documento").toString().trim();
			
			if(pin.length() >= 4 && !pin.trim().equalsIgnoreCase("")) {
				
				String tipo = pin.substring(0, 3);
				
				if(tipo.equalsIgnoreCase("CON") || tipo.equalsIgnoreCase("CER") || tipo.equalsIgnoreCase("BOL")) {
					CMD = ParamsVO.CMD_TRAER_CONSTANCIA;
				} else {
					request.setAttribute("mensaje", "El PIN que usted esta intentando consultar es invalido");
					CMD = ParamsVO.CMD_NUEVO;
				}
			}
		}
		
		switch (TIPO) {
		case ParamsVO.FICHA_PLANTILLA_BOLETIN:

			FICHA = FICHA_PLANTILLA_BOLENTIN;
			switch (CMD) {
			case ParamsVO.CMD_DEFAULT:
			case ParamsVO.CMD_NUEVO:
				
				nuevo(request);
				break;
				
			case ParamsVO.CMD_GUARDAR:
				
				generarBoletin(request, response, session, usuVO, consecutivoBoletin);
				break;

			case ParamsVO.CMD_GENERAR:

				ResultadoConsultaVO resultado = (ResultadoConsultaVO) request.getSession().getAttribute("resultadoConsulta");

				enviarArchivo(request, response, usuVO, resultado);
				request.getSession().removeAttribute("resultadoConsulta");
				return null;
				
			case ParamsVO.CMD_TRAER_CONSTANCIA:
				
				traerConstancia(request, response, session, usuVO, pin);
				break;
				/*ConsultaExterma consultaExterna = boletinPublicoDAO.getConsultaExterna(pin);
				File f = new File(consultaExterna.getConsultaExternaRutaArchivo()+consultaExterna.getConsultaExternaNombreArchivo());
				if (f.exists()) {
					request.setAttribute("rutaArchivoConsultasExternas", consultaExterna.getConsultaExternaNombreArchivo());
				}*/
				//enviarArchivoConstancia(request, response, usuVO, pin);
			case ParamsVO.CMD_ENVIAR_CONSTANCIA:
				
				if(request.getSession().getAttribute("consultaExterna") != null) {
					enviarArchivoConstancia(request, response, usuVO, (ConsultaExterma)request.getSession().getAttribute("consultaExterna"));
				}
				
				request.getSession().removeAttribute("consultaExterna");
				return null;
			}
			
			cargarListasNivelEval(request, session, usuVO);
			break;
			
		}
		
		dispatcher[0] = String.valueOf(Params.FORWARD);
		dispatcher[1] = FICHA;
		
		return dispatcher;
		
	}
	
	
	
	
	
	/**
	 * @function: Metodo que limpia de session los objetos implicados
	 * @param request
	 * @throws ServletException
	 */
	private void nuevo(HttpServletRequest request) throws ServletException {
		// System.out.println("ENTRO POR DEFAULT");
		request.getSession().removeAttribute("listaSedes");
		request.getSession().removeAttribute("listaJornada");
		request.getSession().removeAttribute("listaMetodo");
		request.getSession().removeAttribute("listaGrado");
		request.getSession().removeAttribute("listaGrupo");
		request.getSession().removeAttribute("resultadoConsulta");
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
		// System.out.println("cargarListasNivelEval ");
		try {
			request.setAttribute("filtroTiposDoc",
					boletinPublicoDAO.getTiposDoc());

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
	
	public void traerConstancia(HttpServletRequest request,HttpServletResponse response, HttpSession session, Login usuVO, String pin) throws ServletException {
		// System.out.println(formaFecha.format(new Date() ) + "  generar");
		ConsultaExterma consultaExterna = boletinPublicoDAO.getConsultaExterna(pin);
		
		
		File f = null;
		try {
			f = new File(consultaExterna.getConsultaExternaRutaArchivo()+consultaExterna.getConsultaExternaNombreArchivo());
			if (!f.exists()) {
				request.setAttribute("mensaje", "El PIN que usted esta intentando consultar expirn y no esta disponible");
				return;
			}else{
				request.getSession().setAttribute("consultaExterna",consultaExterna);
			}	
		}
		catch (Exception e) {
			System.out.println("Error al traer consulta externa: "+e.toString());
		}
	}
	

	/**
	 * @function: Metodo que llama el procedimiento y genera le reporte boletnn
	 * @param request
	 * @param session
	 * @param usuVO
	 * @throws ServletException
	 */
	public void generarBoletin(HttpServletRequest request, HttpServletResponse response, HttpSession session, Login usuVO, long consecutivoBoletin) throws ServletException {

		long plabolinst = 0;
		long plaboltipodoc = 0;
		String plabolnumdoc = "0";
		
		ResultadoConsultaVO resultado = null;
		
		PlantillaBoletionPubVO pbVO = (PlantillaBoletionPubVO) request.getSession().getAttribute("plantillaBoletionPubVO");
		
		request.getSession().removeAttribute("resultadoConsulta");
		
		DatosBoletinVO datosBoletinVO = null;
		
		// Cuadrar rutas
		String path_Imagen_sec = Ruta.get(context.getRealPath("/"), rb.getString("consulta.rutaImagen"));
		String path_Jasper = Ruta2.get(context.getRealPath("/"), rb.getString("boletines_ruta_jaspers"));
		System.out.println("Archivo jasper a utilizar: " + path_Jasper);
		String path_Imagen_inst = Ruta.get(context.getRealPath("/"), rb.getString("boletines_imgs_inst"));
		String path_archivosalida = Ruta.get(context.getRealPath("/"), rb.getString("consulta.path") + "666");
		String path_ReporteRelativo = Ruta.getRelativo(pbVO.getFilRutaBase(), rb.getString("consulta.path")	+ "666");

		try {
			
			if(consecutivoBoletin <= 0) {
				
				plabolnumdoc = request.getParameter("plabolnumdoc");
				plaboltipodoc = Long.parseLong((String) request.getParameter("plaboltipodoc"));
				plabolinst = Long.parseLong((String) request.getParameter("plabolinst"));
	
				pbVO.setPlabolinst(plabolinst);
				pbVO.setPlaboltipodoc(plaboltipodoc);
				pbVO.setPlabolnumdoc("" + plabolnumdoc);
				pbVO.setPlabolvigencia(boletinPublicoDAO.getVigenciaInst(plabolinst));
	
				if (!boletinPublicoDAO.isPerCerrado(pbVO)) {
					request.setAttribute(ParamsVO.SMS, "No se puede generar el boletín, el período aún no está cerrado.");
					return;
				}
	
				// 1. Insertar en datos boletion
				pbVO = boletinPublicoDAO.inserrDatosBoletin(pbVO, usuVO);
	
				// 2. Cargar datos de boletin DatosBoletinVO
				datosBoletinVO = new DatosBoletinVO();
				pbVO = boletinPublicoDAO.getReporteConsecutivo(pbVO);
				datosBoletinVO = boletinPublicoDAO.getBoletin(pbVO.getPlabolconsecRepBol());
				
			} else {
				
				datosBoletinVO = boletinPublicoDAO.getBoletin2(consecutivoBoletin);
				pbVO.setPlabolconsecRepBol(consecutivoBoletin);
				
			}
			
			if(datosBoletinVO != null) {
				
				// Validar el tipo de boletin
				int nivelGrado = boletinPublicoDAO.getNivelGrado((int) datosBoletinVO.getDABOLGRADO());
				
				if (nivelGrado == 1) {
					if (datosBoletinVO.getDABOLTIPOEVALPREES() == 1) {
						// System.out.println("Es por dimension");
						datosBoletinVO.setDABOLTIPOBOLETIN(ParamsVO.BOLETIN_DIM);
					} else {
						// System.out.println("Es por asignatura");
						datosBoletinVO.setDABOLTIPOBOLETIN(ParamsVO.BOLETIN_ASIG);
					}
				} else {
					// System.out.println("Es por asignatura");
					datosBoletinVO.setDABOLTIPOBOLETIN(ParamsVO.BOLETIN_ASIG);
				}
	
				// 3. Ejecutar procedimiento de boletin
				if (datosBoletinVO.getDABOLTIPOBOLETIN() == ParamsVO.BOLETIN_ASIG) {
					boletinPublicoDAO.callProcedimientoBoletinAsig(pbVO.getPlabolconsecRepBol());
				} else if (datosBoletinVO.getDABOLTIPOBOLETIN() == ParamsVO.BOLETIN_DIM) {
					boletinPublicoDAO.callProcedimientoBoletinDim(pbVO.getPlabolconsecRepBol());
				}
	
				datosBoletinVO = boletinPublicoDAO.datosConv(datosBoletinVO);
			}
			
			if (boletinPublicoDAO.validarDatosBoletin(pbVO.getPlabolconsecRepBol()) || (datosBoletinVO != null && consecutivoBoletin > 0)) {
				
				if (boletinPublicoDAO.validarPermiso(pbVO)) {

					consultaPublicoIO = new ConsultaPublicoIO(boletinPublicoDAO);
					
					// 4. Generar o llamar el jasper
					resultado = consultaPublicoIO.generarBoletin(datosBoletinVO, pbVO, path_Imagen_inst, path_Imagen_sec, path_archivosalida, path_ReporteRelativo, path_Jasper);

					// 5. Borrar tablas temporales
					if (datosBoletinVO.getDABOLTIPOBOLETIN() == ParamsVO.BOLETIN_ASIG) {
						boletinPublicoDAO.limpiarTablasAsig(pbVO.getPlabolconsecRepBol());
					} else if (datosBoletinVO.getDABOLTIPOBOLETIN() == ParamsVO.BOLETIN_DIM) {
						boletinPublicoDAO.limpiarTablasDim(pbVO.getPlabolconsecRepBol());
					}

					if (resultado.isGenerado()) {
						request.getSession().setAttribute("resultadoConsulta",resultado);
						// enviarArchivo(request, response, usuVO, resultado);
						// System.out.println("COLAR BOLETIN EN ESTADO GENERADO");
						boletinPublicoDAO.updateReporte(pbVO, usuVO, ParamsVO.ESTADO_GENERADO);
						return;
						// request.setAttribute(ParamsVO.SMS,
						// "Boletin generada satisfactoriamente");
					} else {
						request.setAttribute(ParamsVO.SMS,"Boletín no generado: No hay datos para generar el reporte");
						boletinPublicoDAO.updateReporte(pbVO, usuVO, ParamsVO.ESTADO_ERROR);
					}
					
				} else {
					request.setAttribute(ParamsVO.SMS, "No está permitida la consulta del boletín para el período " + pbVO.getPlabolperido() + ".");
					boletinPublicoDAO.updateReporte(pbVO, usuVO, ParamsVO.ESTADO_ERROR);
				}
				
			} else {
				
				request.setAttribute(ParamsVO.SMS, "No hay datos para generar el boletín.");
				boletinPublicoDAO.updateReporte(pbVO, usuVO, ParamsVO.ESTADO_ERROR);
				
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
	public void enviarArchivo(HttpServletRequest request,HttpServletResponse response, Login usuVO,ResultadoConsultaVO resultado) throws ServletException {
		// System.out.println("enviarArchivo");
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
				contentDisposition.append("\"");
				response.setHeader("Content-Disposition",contentDisposition.toString());
				f = new File(resultado.getRutaDisco());
				if (!f.exists()) {
					return;
				}
				// System.out.println("bien 1");
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
					// TODO: handle exception

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

	
	public void enviarArchivoConstancia(HttpServletRequest request,HttpServletResponse response, Login usuVO, ConsultaExterma consultaExterna) throws ServletException {
		// System.out.println("enviarArchivo");
		
		response.setContentType("application/zip");
		BufferedInputStream origin = null;
		ServletOutputStream ouputStream = null;
		FileInputStream fi = null;
		int BUFFER_SIZE = 1000000;
		int count;
		File f = null;
		try {
			f = new File(consultaExterna.getConsultaExternaRutaArchivo()+consultaExterna.getConsultaExternaNombreArchivo());
			if (!f.exists()) {
				return;
			}else{
				StringBuffer contentDisposition = new StringBuffer();
				contentDisposition.append("attachment;");
				contentDisposition.append("filename=\"");
				contentDisposition.append(consultaExterna.getConsultaExternaNombreArchivo());
				contentDisposition.append("\"");
				response.setHeader("Content-Disposition",contentDisposition.toString());
				
				// System.out.println("bien 1");
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

		}/* catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error " + e.getMessage());
			throw new ServletException(e.getMessage());
		}*/ catch (Exception e) {
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
					f = new File(consultaExterna.getConsultaExternaNombreArchivo());
					if (f.exists()) {

						f.delete();
						System.out.println("Borro sin problema " + consultaExterna.getConsultaExternaNombreArchivo());
					}
				} catch (Exception e) {
					// TODO: handle exception

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
