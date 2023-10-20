package navegador.adminNavegador.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import navegador.adminNavegador.vo.ParamsVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.dao.Ruta;
import navegador.adminNavegador.vo.SeccionNavegadorVO;
import navegador.adminNavegador.dao.AdminNavegadorDAO;
import siges.login.beans.Login;

public class Nuevo extends Service {
	private String FICHA_NUEVO;

	private List tipos;

	private List tiposImagen;

	private AdminNavegadorDAO adminNavegadorDAO = new AdminNavegadorDAO(new Cursor());

	public String[] process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dispatcher[] = new String[2];
		String FICHA;
		HttpSession session = request.getSession();
		SeccionNavegadorVO seccionNavegadorVO = (SeccionNavegadorVO) session.getAttribute("seccionNavegadorVO");
		FICHA_NUEVO = config.getInitParameter("FICHA_NUEVO");
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) {
			FICHA = processForm(request, session, seccionNavegadorVO);
		} else {
			FICHA = processMultipart(request, session, seccionNavegadorVO);
		}
		dispatcher[0] = String.valueOf(Params.FORWARD);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	public String processForm(HttpServletRequest request, HttpSession session, SeccionNavegadorVO seccionNavegadorVO) throws ServletException, IOException {
		int CMD;
		int TIPO;
		CMD = getCmd(request, session, ParamsVO.CMD_NUEVO);
		TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		try {
			switch (TIPO) {
			case ParamsVO.FICHA_SECCION:
				switch (CMD) {
				case ParamsVO.CMD_NUEVO:
					session.removeAttribute("seccionNavegadorVO");
					break;
				case ParamsVO.CMD_ELIMINAR:
					eliminar(request, session);
					break;
				case ParamsVO.CMD_EDITAR:
					editar(request, session, seccionNavegadorVO);
					break;
				}
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return FICHA_NUEVO;
		}
		return FICHA_NUEVO;
	}

	public String processMultipart(HttpServletRequest request, HttpSession session, SeccionNavegadorVO seccionNavegadorVO) throws ServletException, IOException {
		int CMD;
		int TIPO;
		Iterator i;
		List fileItems;
		FileItem item = null;
		CMD = ParamsVO.CMD_NUEVO;
		TIPO = ParamsVO.FICHA_DEFAULT;
		try {
			tipos = adminNavegadorDAO.getAllListaTipoMime();
			tiposImagen = adminNavegadorDAO.getAllListaTipoMimeImagen();
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
				case ParamsVO.FICHA_SECCION:
					switch (CMD) {
						case ParamsVO.CMD_GUARDAR:
							i = fileItems.iterator();
							guardar(request, session, i, seccionNavegadorVO);
						break;
					}
				break;
			}
		} catch (FileUploadBase.InvalidContentTypeException e) {
			return FICHA_NUEVO;
		} catch (Exception e) {
			e.printStackTrace();
			return FICHA_NUEVO;
		}
		return FICHA_NUEVO;
	}

	public void guardar(HttpServletRequest request, HttpSession session, Iterator i, SeccionNavegadorVO seccionNavegadorVO) throws Exception {
		FileItem item = null;
		try {
			while (i.hasNext()) {
				item = (FileItem) i.next();
				if (!item.isFormField()) {
					if (item.getFieldName().equals("archivo")) {
						if (!validarTipo(item.getContentType())) {
							request.setAttribute(Params.SMS, "El archivo no corresponde a los tipos de contenido permitidos");
							return;
						}
						seccionNavegadorVO.setTipoMime(item.getContentType());
						seccionNavegadorVO.setArchivo(item.get());
					}
					if (item.getFieldName().equals("archivoFondo")) {
						if (!validarTipoImagen(item.getContentType())) {
							request.setAttribute(Params.SMS, "El archivo de fondo no corresponde a los tipos de contenido permitidos");
							return;
						}
						seccionNavegadorVO.setTipoMimeFondo(item.getContentType());
						seccionNavegadorVO.setArchivoFondo(item.get());
					}
				} else {
					if (item.getFieldName().equals("nombre")) {
						seccionNavegadorVO.setNombre(item.getString());
					}
					if (item.getFieldName().equals("orden")) {
						seccionNavegadorVO.setOrden(Integer.parseInt(item.getString()));
					}
					if (item.getFieldName().equals("descripcion")) {
						seccionNavegadorVO.setDescripcion(item.getString());
					}
					if (item.getFieldName().equals("nombreArchivo")) {
						seccionNavegadorVO.setNombreArchivo(item.getString());
					}
					if (item.getFieldName().equals("nombreArchivoFondo")) {
						seccionNavegadorVO.setNombreArchivoFondo(item.getString());
					}
				}
			}
			if (seccionNavegadorVO.getEstado() == 0) {
				adminNavegadorDAO.insertar(seccionNavegadorVO);
				request.setAttribute(Params.SMS, "La información fue ingresada satisfactoriamente");
				session.removeAttribute("seccionNavegadorVO");
			} else {
				adminNavegadorDAO.actualizar(seccionNavegadorVO);
				request.setAttribute(Params.SMS, "La información fue actualizada satisfactoriamente");
				session.removeAttribute("seccionNavegadorVO");
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(Params.SMS, "La información no fue ingresada: " + e.getMessage());
		}
	}

	public void eliminar(HttpServletRequest request, HttpSession session) throws Exception {
		try {
			String id = request.getParameter("id");
			if (id != null) {
				adminNavegadorDAO.eliminar(Long.parseLong(id));
				request.setAttribute(Params.SMS, "La información fue eliminada satisfactoriamente");
				session.removeAttribute("seccionNavegadorVO");
			} else {
				request.setAttribute(Params.SMS, "La información no fue eliminada satisfactoriamente: Faltan parnmetros");
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(Params.SMS, "La información no fue eliminada: " + e.getMessage());
		}
	}

	public void editar(HttpServletRequest request, HttpSession session, SeccionNavegadorVO seccionNavegadorVO) throws Exception {
		try {
			String id = request.getParameter("id");
			if (id != null) {
				seccionNavegadorVO = adminNavegadorDAO.asignar(Long.parseLong(id));
				session.setAttribute("seccionNavegadorVO", seccionNavegadorVO);
				request.setAttribute("guia", id);
			} else {
				request.setAttribute(Params.SMS, "La información no fue obtenida: Faltan parnmetros");
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(Params.SMS, "La información no fue obtenida: " + e.getMessage());
		}
	}

	public boolean validarTipo(String a) {
		boolean aceptado = false;
		for (int n = 0; n < tipos.size(); n++) {
			if (a.equals((String) tipos.get(n))) {
				aceptado = true;
				break;
			}
		}
		return aceptado;
	}

	public boolean validarTipoImagen(String a) {
		boolean aceptado = false;
		for (int n = 0; n < tiposImagen.size(); n++) {
			if (a.equals((String) tiposImagen.get(n))) {
				aceptado = true;
				break;
			}
		}
		return aceptado;
	}
}
