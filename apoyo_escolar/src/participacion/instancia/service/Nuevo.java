/**
 * 
 */
package participacion.instancia.service;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import participacion.common.exception.IntegrityException;
import participacion.common.exception.ParticipacionException;
import participacion.common.exception.ValidateException;
import participacion.instancia.dao.InstanciaDAO;
import participacion.instancia.vo.DocumentoVO;
import participacion.instancia.vo.FiltroInstanciaVO;
import participacion.instancia.vo.FiltroRangoVO;
import participacion.instancia.vo.InstanciaVO;
import participacion.instancia.vo.ObjetivoVO;
import participacion.instancia.vo.ParamsVO;
import participacion.instancia.vo.RangoVO;
import participacion.instancia.vo.RepresentanteVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.dao.Ruta;
import siges.login.beans.Login;

/**
 * 28/04/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class Nuevo extends Service {

	public String FICHA_INSTANCIA;

	public String FICHA_OBJETIVO;

	public String FICHA_REPRESENTANTE;

	public String FICHA_DOCUMENTO;

	public String FICHA_RANGO;

	private String[] tipos = { "application/vnd.ms-excel", "application/zip",
			"application/msword", "application/pdf", "application/powerpoint",
			"application/vnd.ms-powerpoint", "application/x-msexcel",
			"application/ms-excel", "application/ms-powerpoint" };

	/**
	 * 
	 */
	private InstanciaDAO instanciaDAO = new InstanciaDAO(new Cursor());

	/*
	 * @function: @param config @throws ServletException (non-Javadoc)
	 * 
	 * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		FICHA_INSTANCIA = config.getInitParameter("FICHA_INSTANCIA");
		FICHA_OBJETIVO = config.getInitParameter("FICHA_OBJETIVO");
		FICHA_REPRESENTANTE = config.getInitParameter("FICHA_REPRESENTANTE");
		FICHA_DOCUMENTO = config.getInitParameter("FICHA_DOCUMENTO");
		FICHA_RANGO = config.getInitParameter("FICHA_RANGO");
	}

	/*
	 * Procesa la peticinn @param request peticinn @param response respuesta
	 * 
	 * @return Ruta de redireccinn @throws ServletException @throws IOException
	 * (non-Javadoc)
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
		Login usuVO = (Login) session.getAttribute("login");
		FiltroInstanciaVO filtroInstancia = (FiltroInstanciaVO) session
				.getAttribute("filtroInstanciaVO");
		InstanciaVO instancia = (InstanciaVO) session
				.getAttribute("instanciaVO");
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			FICHA = processMultipart(request, session, usuVO, filtroInstancia,
					instancia);
		} else {
			int CMD = getCmd(request, session, ParamsVO.CMD_CANCELAR);
			int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
			// System.out.println("valores: "+TIPO+"_"+CMD);
			switch (TIPO) {
			case ParamsVO.FICHA_INSTANCIA:
				FICHA = FICHA_INSTANCIA;
				switch (CMD) {
				case ParamsVO.CMD_BUSCAR:
					instanciaBuscar(request, session, usuVO, filtroInstancia);
					break;
				case ParamsVO.CMD_EDITAR:
					instancia = instanciaEditar(request, session, usuVO,
							filtroInstancia);
					instanciaBuscar(request, session, usuVO, filtroInstancia);
					objetivoBuscar(request, session, usuVO, instancia);
					representanteBuscar(request, session, usuVO, instancia);
					documentoBuscar(request, session, usuVO, instancia);
					break;
				case ParamsVO.CMD_ELIMINAR:
					instanciaEliminar(request, session, usuVO, filtroInstancia);
					instanciaBuscar(request, session, usuVO, filtroInstancia);
					break;
				case ParamsVO.CMD_GUARDAR:
					instanciaGuardar(request, session, usuVO, instancia);
					instanciaBuscar(request, session, usuVO, filtroInstancia);
					break;
				case ParamsVO.CMD_CANCELAR:
					instanciaCancelar(request, session, usuVO);
					break;
				}
				instanciaInit(request, session, usuVO, filtroInstancia);
				break;
			case ParamsVO.FICHA_RANGO:
				FICHA = FICHA_RANGO;
				FiltroRangoVO filtroRango = (FiltroRangoVO) session
						.getAttribute("filtroRangoVO");
				RangoVO rango = (RangoVO) session.getAttribute("rangoVO");
				switch (CMD) {
				case ParamsVO.CMD_BUSCAR:
					rangoBuscar(request, session, usuVO, filtroRango);
					break;
				case ParamsVO.CMD_EDITAR:
					rangoEditar(request, session, usuVO, filtroRango);
					rangoBuscar(request, session, usuVO, filtroRango);
					break;
				case ParamsVO.CMD_ELIMINAR:
					rangoEliminar(request, session, usuVO, filtroRango);
					rangoBuscar(request, session, usuVO, filtroRango);
					break;
				case ParamsVO.CMD_GUARDAR:
					rangoGuardar(request, session, usuVO, rango);
					rangoBuscar(request, session, usuVO, filtroRango);
					break;
				case ParamsVO.CMD_CANCELAR:
					rangoCancelar(request, session, usuVO);
					rangoBuscar(request, session, usuVO, filtroRango);
					break;
				}
				rangoInit(request, session, usuVO, filtroRango);
				break;
			case ParamsVO.FICHA_OBJETIVO:
				ObjetivoVO objetivo = (ObjetivoVO) session
						.getAttribute("objetivoVO");
				FICHA = FICHA_INSTANCIA;
				switch (CMD) {
				case ParamsVO.CMD_NUEVO:
					objetivoNuevo(request, session, usuVO);
					FICHA = FICHA_OBJETIVO;
					break;
				case ParamsVO.CMD_EDITAR:
					objetivoEditar(request, session, usuVO, filtroInstancia);
					FICHA = FICHA_OBJETIVO;
					break;
				case ParamsVO.CMD_ELIMINAR:
					objetivoEliminar(request, session, usuVO, filtroInstancia);
					instanciaBuscar(request, session, usuVO, filtroInstancia);
					objetivoBuscar(request, session, usuVO, instancia);
					representanteBuscar(request, session, usuVO, instancia);
					documentoBuscar(request, session, usuVO, instancia);
					break;
				case ParamsVO.CMD_GUARDAR:
					objetivoGuardar(request, session, usuVO, objetivo);
					instanciaBuscar(request, session, usuVO, filtroInstancia);
					objetivoBuscar(request, session, usuVO, instancia);
					representanteBuscar(request, session, usuVO, instancia);
					documentoBuscar(request, session, usuVO, instancia);
					break;
				case ParamsVO.CMD_CANCELAR:
					objetivoCancelar(request, session, usuVO);
					break;
				}
				instanciaInit(request, session, usuVO, filtroInstancia);
				break;
			case ParamsVO.FICHA_REPRESENTANTE:
				RepresentanteVO representanteVO = (RepresentanteVO) session
						.getAttribute("representanteVO");
				FICHA = FICHA_INSTANCIA;
				switch (CMD) {
				case ParamsVO.CMD_NUEVO:
					representanteEditar(request, session, usuVO,
							filtroInstancia);
					FICHA = FICHA_REPRESENTANTE;
					break;
				case ParamsVO.CMD_ELIMINAR:
					representanteEliminar(request, session, usuVO,
							filtroInstancia);
					instanciaBuscar(request, session, usuVO, filtroInstancia);
					objetivoBuscar(request, session, usuVO, instancia);
					representanteBuscar(request, session, usuVO, instancia);
					documentoBuscar(request, session, usuVO, instancia);
					break;
				case ParamsVO.CMD_GUARDAR:
					representanteGuardar(request, session, usuVO, instancia,
							representanteVO);
					instanciaBuscar(request, session, usuVO, filtroInstancia);
					objetivoBuscar(request, session, usuVO, instancia);
					representanteBuscar(request, session, usuVO, instancia);
					documentoBuscar(request, session, usuVO, instancia);
					break;
				case ParamsVO.CMD_CANCELAR:
					representanteCancelar(request, session, usuVO);
					break;
				}
				instanciaInit(request, session, usuVO, filtroInstancia);
				break;
			case ParamsVO.FICHA_DOCUMENTO:
				FICHA = FICHA_INSTANCIA;
				DocumentoVO documentoVO = (DocumentoVO) session
						.getAttribute("documentoVO");
				switch (CMD) {
				case ParamsVO.CMD_DESCARGAR:
					documentoDescargar(request, response, session, usuVO,
							filtroInstancia);
					return null;
					// FICHA=null;
					// break;
				case ParamsVO.CMD_NUEVO:
					documentoNuevo(request, session, usuVO);
					FICHA = FICHA_DOCUMENTO;
					break;
				case ParamsVO.CMD_EDITAR:
					documentoEditar(request, session, usuVO, filtroInstancia);
					FICHA = FICHA_DOCUMENTO;
					break;
				case ParamsVO.CMD_ELIMINAR:
					documentoEliminar(request, session, usuVO, filtroInstancia);
					instanciaBuscar(request, session, usuVO, filtroInstancia);
					objetivoBuscar(request, session, usuVO, instancia);
					representanteBuscar(request, session, usuVO, instancia);
					documentoBuscar(request, session, usuVO, instancia);
					break;
				case ParamsVO.CMD_GUARDAR:
					documentoGuardar(request, session, usuVO, documentoVO);
					instanciaBuscar(request, session, usuVO, filtroInstancia);
					objetivoBuscar(request, session, usuVO, instancia);
					representanteBuscar(request, session, usuVO, instancia);
					documentoBuscar(request, session, usuVO, instancia);
					break;
				case ParamsVO.CMD_CANCELAR:
					documentoCancelar(request, session, usuVO);
					break;
				}
				instanciaInit(request, session, usuVO, filtroInstancia);
				break;
			}
		}
		dispatcher[0] = String.valueOf(Params.INCLUDE);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	public String processMultipart(HttpServletRequest request,
			HttpSession session, Login usuVO,
			FiltroInstanciaVO filtroInstancia, InstanciaVO instancia)
			throws ServletException, IOException {
		int CMD;
		int TIPO;
		Iterator i;
		List fileItems;
		FileItem item = null;
		CMD = ParamsVO.CMD_NUEVO;
		TIPO = ParamsVO.FICHA_DEFAULT;
		try {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			fileItems = upload.parseRequest(request);
			i = fileItems.iterator();
			while (i.hasNext()) {
				item = (FileItem) i.next();
				if (item.isFormField()) {
					if (item.getFieldName().equals("cmd")) {
						CMD = Integer.parseInt(item.getString());
					}
					if (item.getFieldName().equals("tipo")) {
						TIPO = Integer.parseInt(item.getString());
					}
				}
			}
			switch (TIPO) {
			case ParamsVO.FICHA_DOCUMENTO:
				switch (CMD) {
				case ParamsVO.CMD_GUARDAR:
					i = fileItems.iterator();
					DocumentoVO documentoVO = getDocumento(i);
					documentoGuardar(request, session, usuVO, documentoVO);
					instanciaBuscar(request, session, usuVO, filtroInstancia);
					objetivoBuscar(request, session, usuVO, instancia);
					representanteBuscar(request, session, usuVO, instancia);
					documentoBuscar(request, session, usuVO, instancia);
					break;
				}
				instanciaInit(request, session, usuVO, filtroInstancia);
				break;
			}
		} catch (FileUploadBase.InvalidContentTypeException e) {
			return FICHA_INSTANCIA;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "Error interno");
			return FICHA_INSTANCIA;
		}
		return FICHA_INSTANCIA;
	}

	private void instanciaCancelar(HttpServletRequest request,
			HttpSession session, Login usuVO) throws ServletException {
		session.removeAttribute("instanciaVO");
	}

	private void instanciaInit(HttpServletRequest request, HttpSession session,
			Login usuVO, FiltroInstanciaVO filtro) throws ServletException {
		try {
			request.setAttribute("listaNivelVO", instanciaDAO.getListaNivel());
		} catch (ParticipacionException e) {
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	private void instanciaBuscar(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroInstanciaVO filtro)
			throws ServletException {
		try {
			if (filtro != null && filtro.getFilNivel() > 0) {
				request.setAttribute("listaInstanciaVO",
						instanciaDAO.getListaInstancia(filtro));
			}
		} catch (ParticipacionException e) {
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	private InstanciaVO instanciaEditar(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroInstanciaVO filtro)
			throws ServletException {
		InstanciaVO i = null;
		try {
			if (filtro != null && filtro.getFilInstancia() > 0) {
				i = instanciaDAO.getInstancia(filtro);
				session.setAttribute("instanciaVO", i);
			}
		} catch (ParticipacionException e) {
			throw new ServletException("Errror interno: " + e.getMessage());
		}
		return i;
	}

	private void instanciaEliminar(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroInstanciaVO filtro)
			throws ServletException {
		try {
			if (filtro != null && filtro.getFilInstancia() > 0) {
				instanciaDAO.eliminarInstancia(filtro);
				request.setAttribute(ParamsVO.SMS,
						"La instancia fue eliminada satisfactoriamente");
			}
		} catch (IntegrityException e) {
			request.setAttribute(ParamsVO.SMS, "La instancia fue no eliminada:"
					+ e.getMessage());
		} catch (ParticipacionException e) {
			request.setAttribute(ParamsVO.SMS,
					"La instancia fue no eliminada: Error interno");
		}
	}

	private void instanciaGuardar(HttpServletRequest request,
			HttpSession session, Login usuVO, InstanciaVO instancia)
			throws ServletException {
		try {
			if (instancia != null) {
				if (instancia.getFormaEstado().equals("1")) {
					instanciaDAO.actualizarInstancia(instancia);
					request.setAttribute(ParamsVO.SMS,
							"La instancia fue actualizada satisfactoriamente");
					session.removeAttribute("instanciaVO");
				} else {
					instanciaDAO.ingresarInstancia(instancia);
					request.setAttribute(ParamsVO.SMS,
							"La instancia fue ingresada satisfactoriamente");
					session.removeAttribute("instanciaVO");
				}
			}
		} catch (IntegrityException e) {
			request.setAttribute(
					ParamsVO.SMS,
					"La instancia fue no ingresada/actualizada:"
							+ e.getMessage());
		} catch (ParticipacionException e) {
			request.setAttribute(ParamsVO.SMS,
					"La instancia fue no ingresada/actualizada: Error interno");
		}
	}

	private void rangoCancelar(HttpServletRequest request, HttpSession session,
			Login usuVO) throws ServletException {
		session.removeAttribute("rangoVO");
	}

	private void rangoInit(HttpServletRequest request, HttpSession session,
			Login usuVO, FiltroRangoVO filtro) throws ServletException {
		try {
			request.setAttribute("listaNivelVO", instanciaDAO.getListaNivel());
			if (filtro != null && filtro.getFilNivel() > 0) {
				request.setAttribute("listaInstanciaVO",
						instanciaDAO.getListaInstancia(filtro.getFilNivel()));
			}
		} catch (ParticipacionException e) {
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	private void rangoBuscar(HttpServletRequest request, HttpSession session,
			Login usuVO, FiltroRangoVO filtro) throws ServletException {
		try {
			if (filtro != null && filtro.getFilInstancia() > 0) {
				request.setAttribute("listaRangoVO",
						instanciaDAO.getListaRango(filtro));
			}
		} catch (ParticipacionException e) {
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	private void rangoEditar(HttpServletRequest request, HttpSession session,
			Login usuVO, FiltroRangoVO filtro) throws ServletException {
		try {
			if (filtro != null && filtro.getFilRango() > 0) {
				session.setAttribute("rangoVO", instanciaDAO.getRango(filtro));
			}
		} catch (ParticipacionException e) {
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	private void rangoEliminar(HttpServletRequest request, HttpSession session,
			Login usuVO, FiltroRangoVO filtro) throws ServletException {
		try {
			if (filtro != null && filtro.getFilRango() > 0) {
				instanciaDAO.eliminarRango(filtro);
				request.setAttribute(ParamsVO.SMS,
						"El rango fue eliminado satisfactoriamente");
			}
		} catch (IntegrityException e) {
			request.setAttribute(ParamsVO.SMS, "La instancia no fue eliminada:"
					+ e.getMessage());
		} catch (ParticipacionException e) {
			request.setAttribute(ParamsVO.SMS,
					"La instancia no fue eliminada: Error interno");
		}
	}

	private void rangoGuardar(HttpServletRequest request, HttpSession session,
			Login usuVO, RangoVO rango) throws ServletException {
		try {
			if (rango != null) {
				if (rango.getFormaEstado().equals("1")) {
					instanciaDAO.actualizarRango(rango);
					request.setAttribute(ParamsVO.SMS,
							"El rango fue actualizado satisfactoriamente");
				} else {
					instanciaDAO.ingresarRango(rango);
					request.setAttribute(ParamsVO.SMS,
							"El rango fue ingresado satisfactoriamente");
				}
				session.removeAttribute("rangoVO");
			}
		} catch (IntegrityException e) {
			request.setAttribute(ParamsVO.SMS,
					"El rango no fue ingresada/actualizada:" + e.getMessage());
		} catch (ParticipacionException e) {
			request.setAttribute(ParamsVO.SMS,
					"El rango no fue ingresada/actualizada: Error interno");
		}
	}

	private void objetivoBuscar(HttpServletRequest request,
			HttpSession session, Login usuVO, InstanciaVO instancia)
			throws ServletException {
		try {
			if (instancia != null && instancia.getInstCodigo() > 0) {
				request.setAttribute("listaObjetivoVO",
						instanciaDAO.getListaObjetivo(instancia));
			}
		} catch (ParticipacionException e) {
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	private void objetivoEditar(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroInstanciaVO filtro)
			throws ServletException {
		try {
			if (filtro != null && filtro.getFilObjetivo() > 0) {
				session.setAttribute("objetivoVO",
						instanciaDAO.getObjetivo(filtro));
			}
		} catch (ParticipacionException e) {
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	private void objetivoEliminar(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroInstanciaVO filtro)
			throws ServletException {
		try {
			if (filtro != null && filtro.getFilObjetivo() > 0) {
				instanciaDAO.eliminarObjetivo(filtro);
				request.setAttribute(ParamsVO.SMS,
						"La funcinn fue eliminada satisfactoriamente");
			}
		} catch (IntegrityException e) {
			request.setAttribute(ParamsVO.SMS, "La funcinn no fue eliminada:"
					+ e.getMessage());
		} catch (ParticipacionException e) {
			request.setAttribute(ParamsVO.SMS,
					"La funcinn no fue eliminada: Error interno");
		}
	}

	private void objetivoGuardar(HttpServletRequest request,
			HttpSession session, Login usuVO, ObjetivoVO objetivo)
			throws ServletException {
		try {
			if (objetivo != null) {
				if (objetivo.getFormaEstado().equals("1")) {
					instanciaDAO.actualizarObjetivo(objetivo);
					request.setAttribute(ParamsVO.SMS,
							"La funcinn fue actualizada satisfactoriamente");
				} else {
					instanciaDAO.ingresarObjetivo(objetivo);
					request.setAttribute(ParamsVO.SMS,
							"La funcinn fue ingresada satisfactoriamente");
				}
			}
		} catch (IntegrityException e) {
			request.setAttribute(ParamsVO.SMS,
					"La funcinn no fue ingresada/actualizada:" + e.getMessage());
		} catch (ParticipacionException e) {
			request.setAttribute(ParamsVO.SMS,
					"La funcinn no fue ingresada/actualizada: Error interno");
		}
	}

	private void objetivoCancelar(HttpServletRequest request,
			HttpSession session, Login usuVO) throws ServletException {
		session.removeAttribute("objetivoVO");
	}

	private void representanteBuscar(HttpServletRequest request,
			HttpSession session, Login usuVO, InstanciaVO instancia)
			throws ServletException {
		try {
			if (instancia != null && instancia.getInstCodigo() > 0) {
				request.setAttribute("listaRepresentanteVO",
						instanciaDAO.getListaRepresentante(instancia));
			}
		} catch (ParticipacionException e) {
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	private void representanteEditar(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroInstanciaVO filtro)
			throws ServletException {
		try {
			if (filtro != null && filtro.getFilInstancia() > 0) {
				request.setAttribute("representanteVO",
						instanciaDAO.getRepresentante(filtro));
			}
		} catch (ParticipacionException e) {
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	private void representanteEliminar(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroInstanciaVO filtro)
			throws ServletException {
		try {
			if (filtro != null && filtro.getFilRepresentante() > 0) {
				instanciaDAO.eliminarRepresentante(filtro);
				request.setAttribute(ParamsVO.SMS,
						"El participante fue eliminado satisfactoriamente");
			}
		} catch (ParticipacionException e) {
			request.setAttribute(ParamsVO.SMS,
					"El participante no fue eliminado: Error interno");
		}
	}

	private void representanteGuardar(HttpServletRequest request,
			HttpSession session, Login usuVO, InstanciaVO instancia,
			RepresentanteVO representante) throws ServletException {
		try {
			if (representante != null) {
				instanciaDAO.ingresarRepresentante(instancia, representante);
				request.setAttribute(ParamsVO.SMS,
						"Los participantes fueron actualizados satisfactoriamente");
			}
		} catch (IntegrityException e) {
			request.setAttribute(
					ParamsVO.SMS,
					"Los participantes no fueron actualizados:"
							+ e.getMessage());
		} catch (ParticipacionException e) {
			request.setAttribute(ParamsVO.SMS,
					"Los participantes no fueron actualizados: Error interno");
		}
	}

	private void representanteCancelar(HttpServletRequest request,
			HttpSession session, Login usuVO) throws ServletException {
		session.removeAttribute("representanteVO");
	}

	private void documentoCancelar(HttpServletRequest request,
			HttpSession session, Login usuVO) throws ServletException {
		session.removeAttribute("documentoVO");
	}

	private void documentoBuscar(HttpServletRequest request,
			HttpSession session, Login usuVO, InstanciaVO instancia)
			throws ServletException {
		try {
			if (instancia != null && instancia.getInstCodigo() > 0) {
				request.setAttribute("listaDocumentoVO",
						instanciaDAO.getListaDocumento(instancia));
			}
		} catch (ParticipacionException e) {
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	private void documentoEditar(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroInstanciaVO filtro)
			throws ServletException {
		try {
			if (filtro != null && filtro.getFilDocumento() > 0) {
				session.setAttribute("documentoVO",
						instanciaDAO.getDocumento(filtro));
			}
		} catch (ParticipacionException e) {
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	private void documentoGuardar(HttpServletRequest request,
			HttpSession session, Login usuVO, DocumentoVO documento)
			throws ServletException {
		try {
			if (documento != null) {
				if (documento.getFormaEstado().equals("1")) {
					instanciaDAO.actualizarDocumento(documento);
					request.setAttribute(ParamsVO.SMS,
							"El documento fue actualizado satisfactoriamente");
				} else {
					ResourceBundle rb = ResourceBundle
							.getBundle("participacion.instancia.bundle.instancia");
					String path = rb.getString("documento.ruta") + "."
							+ documento.getDocInstancia();
					String pathSubir = Ruta.get(context, path);
					java.io.File f = new java.io.File(pathSubir);
					if (!f.exists()) {
						FileUtils.forceMkdir(f);
					}
					documento.setDocRuta(pathSubir);
					instanciaDAO.ingresarDocumento(documento);
					request.setAttribute(ParamsVO.SMS,
							"El documento fue ingresado satisfactoriamente");
				}
			}
		} catch (IntegrityException e) {
			request.setAttribute(
					ParamsVO.SMS,
					"El documento no fue ingresada/actualizada:"
							+ e.getMessage());
		} catch (ParticipacionException e) {
			request.setAttribute(ParamsVO.SMS,
					"El documento no fue ingresada/actualizada: Error interno");
		} catch (Exception e) {
			request.setAttribute(ParamsVO.SMS,
					"El documento no fue ingresada/actualizada: Error interno");
		}
	}

	private DocumentoVO getDocumento(Iterator i) throws ParticipacionException {
		FileItem item = null;
		DocumentoVO doc = null;
		try {
			doc = new DocumentoVO();
			while (i.hasNext()) {
				item = (FileItem) i.next();
				if (!item.isFormField()) {
					if (item.getFieldName().equals("archivo")) {
						// System.out.println(item.getContentType());
						if (!validarTipo(item.getContentType())) {
							throw new ValidateException(
									"El archivo no corresponde a los tipos de contenido permitidos");
						}
						doc.setDocArchivo(item.get());
						doc.setDocTipoMime(item.getContentType());
						doc.setDocExtension(item.getName().substring(
								item.getName().lastIndexOf('.'),
								item.getName().length()));
					}
				} else {
					if (item.getFieldName().equals("docInstancia")) {
						doc.setDocInstancia(Integer.parseInt(item.getString()));
					}
					if (item.getFieldName().equals("docNombre")) {
						doc.setDocNombre(item.getString());
					}
					if (item.getFieldName().equals("docDescripcion")) {
						doc.setDocDescripcion(item.getString());
					}
					if (item.getFieldName().equals("docFecha")) {
						doc.setDocFecha(item.getString());
					}
				}
			}
		} catch (ValidateException e) {
			throw new ValidateException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ParticipacionException(e.getMessage());
		}
		return doc;
	}

	public boolean validarTipo(String a) {
		for (int n = 0; n < tipos.length; n++) {
			if (a.equals((String) tipos[n])) {
				return true;
			}
		}
		return false;
	}

	private void documentoEliminar(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroInstanciaVO filtro)
			throws ServletException {
		try {
			if (filtro != null && filtro.getFilDocumento() > 0) {
				instanciaDAO.eliminarDocumento(filtro);
				request.setAttribute(ParamsVO.SMS,
						"El documento fue eliminado satisfactoriamente");
			}
		} catch (IntegrityException e) {
			request.setAttribute(ParamsVO.SMS, "El documento no fue eliminada:"
					+ e.getMessage());
		} catch (ParticipacionException e) {
			request.setAttribute(ParamsVO.SMS,
					"El documento no fue eliminada: Error interno");
		}
	}

	private void objetivoNuevo(HttpServletRequest request, HttpSession session,
			Login usuVO) throws ServletException {
		session.removeAttribute("objetivoVO");
	}

	private void representanteNuevo(HttpServletRequest request,
			HttpSession session, Login usuVO) throws ServletException {
		session.removeAttribute("representanteVO");
	}

	private void documentoNuevo(HttpServletRequest request,
			HttpSession session, Login usuVO) throws ServletException {
		session.removeAttribute("documentoVO");
	}

	private void documentoDescargar(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Login usuVO,
			FiltroInstanciaVO filtro) throws ServletException {
		ServletOutputStream ouputStream = null;
		FileInputStream fi = null;
		BufferedInputStream origin = null;
		int BUFFER_SIZE = 100000;
		int count;
		try {
			String inst = request.getParameter("filInstancia");
			String doc = request.getParameter("filDocumento");
			if (filtro != null && inst != null && doc != null) {
				filtro.setFilInstancia(Integer.parseInt(inst));
				filtro.setFilDocumento(Integer.parseInt(doc));
				ResourceBundle rb = ResourceBundle
						.getBundle("participacion.instancia.bundle.instancia");
				String pathSubir = Ruta.get(context,
						rb.getString("documento.ruta") + "." + inst);
				java.io.File f = new java.io.File(pathSubir);
				if (!f.exists()) {
					FileUtils.forceMkdir(f);
				}
				DocumentoVO docVO = instanciaDAO.getDocumentoDescarga(filtro,
						pathSubir);
				if (docVO != null) {
					ouputStream = response.getOutputStream();
					byte[] data = new byte[BUFFER_SIZE];
					fi = new FileInputStream(docVO.getDocRuta());
					origin = new BufferedInputStream(fi, BUFFER_SIZE);
					response.setContentType(docVO.getDocTipoMime());
					response.setHeader("Content-Disposition",
							"attachment; filename=\"" + docVO.getDocNombre()
									+ "\"");
					while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
						ouputStream.write(data, 0, count);
					}
					ouputStream.flush();
					ouputStream.close();
					// origin.close();
					// fi.close();

					// response.getOutputStream().write(docVO.getDocArchivo());
					// System.out.println("descarga de archivo");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		} finally {
			try {
				if (ouputStream != null)
					;
				ouputStream.close();
				if (origin != null)
					;
				origin.close();
				if (fi != null)
					;
				fi.close();
			} catch (Exception e) {
			}
		}
	}
}
