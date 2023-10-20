/**
 * 
 */
package siges.importarFotografia.service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import siges.common.service.Service;
import siges.dao.Cursor;
import siges.importarFotografia.dao.ImportarFotografiaDAO;
import siges.importarFotografia.io.ImportarFotografiaIO;
import siges.importarFotografia.vo.ParamsVO;
import siges.login.beans.Login;
import siges.util.Logger;

/**
 * 24/09/2008
 * 
 * @author Latined
 * @version 1.2
 */
public class Nuevo extends Service {

	private String FICHA_IMPORTAR_ESTUDIANTE;
	private String FICHA_IMPORTAR_PERSONAL;
	private ImportarFotografiaDAO importarFotografiaDAO = new ImportarFotografiaDAO(
			new Cursor());
	private String[] tipos = { "application/x-zip-compressed",
			"application/zip", "application/octet-stream" };

	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[] = new String[2];
		String FICHA;
		try {
			// System.out.println("IMPORTARFOTOGRAFIA" + ": inicio");
			FICHA_IMPORTAR_ESTUDIANTE = config
					.getInitParameter("FICHA_IMPORTAR_ESTUDIANTE");
			FICHA_IMPORTAR_PERSONAL = config
					.getInitParameter("FICHA_IMPORTAR_PERSONAL");
			Login usuVO = (Login) session.getAttribute("login");
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (!isMultipart) {
				// System.out.println("IMPORTARFOTOGRAFIA"
				// + ": formulario importacion");
				FICHA = processForm(request, session, usuVO);
			} else {
				// System.out.println("IMPORTARFOTOGRAFIA" +
				// ": importar archivo");
				FICHA = processMultipart(request, session, usuVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e.getMessage());
		}
		dispatcher[0] = String.valueOf(ParamsVO.FORWARD);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	public String processForm(HttpServletRequest request, HttpSession session,
			Login usuVO) throws ServletException, IOException {
		int CMD;
		int TIPO;
		String FICHA = null;
		CMD = getCmd(request, session, ParamsVO.CMD_NUEVO);
		TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		if (TIPO != ParamsVO.FICHA_IMPORTAR_ESTUDIANTE
				&& TIPO != ParamsVO.FICHA_IMPORTAR_PERSONAL) {
			TIPO = ParamsVO.FICHA_DEFAULT;
		}
		try {
			switch (TIPO) {
			case ParamsVO.FICHA_IMPORTAR_ESTUDIANTE:
				FICHA = FICHA_IMPORTAR_ESTUDIANTE;
				nuevoImportarEstudiante(request, usuVO);
				Logger.print(usuVO.getUsuarioId(), "Consulta de HOJAS DE VIDA, módulo Importar Fotografia -Importar Estudiante. ",
						7, 1, this.toString());				
				break;
			case ParamsVO.FICHA_IMPORTAR_PERSONAL:
				FICHA = FICHA_IMPORTAR_PERSONAL;
				nuevoImportarPersonal(request, usuVO);
				Logger.print(usuVO.getUsuarioId(), "Consulta de HOJAS DE VIDA, módulo Importar Fotografia -Importar Personal. ",
						7, 1, this.toString());	
				
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return FICHA;
		}
		return FICHA;
	}

	private void nuevoImportarEstudiante(HttpServletRequest request, Login usuVO)
			throws Exception {
		importarFotografiaDAO.deleteHilo(usuVO.getUsuarioId());
		List l = importarFotografiaDAO.getHilosUsuario(usuVO.getUsuarioId());
		if (l.size() > 0) {
			request.setAttribute("resultado", "1");
			request.setAttribute("resultadoImportar", l);
		} else {
			request.setAttribute("resultado", "0");
		}

	}

	private void nuevoImportarPersonal(HttpServletRequest request, Login usuVO)
			throws Exception {
	}

	public String processMultipart(HttpServletRequest request,
			HttpSession session, Login usuVO) throws ServletException,
			IOException {
		int CMD;
		int TIPO;
		Iterator i;
		List fileItems;
		FileItem item = null;
		CMD = ParamsVO.CMD_NUEVO;
		TIPO = ParamsVO.FICHA_DEFAULT;
		String FICHA = null;
		try {
			// System.out
			// .println("IMPORTARFOTOGRAFIA" + ": creando clases Upload");
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			fileItems = upload.parseRequest(request);
			i = fileItems.iterator();
			while (i.hasNext()) {
				// System.out.println("IMPORTARFOTOGRAFIA"
				// + ": iterando por los campos");
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
			case ParamsVO.FICHA_IMPORTAR_ESTUDIANTE:
				FICHA = FICHA_IMPORTAR_ESTUDIANTE;
				// System.out.println("IMPORTARFOTOGRAFIA" +
				// ": tipo estudiante");
				switch (CMD) {
				case ParamsVO.CMD_GUARDAR:
					// System.out.println("IMPORTARFOTOGRAFIA"
					// + ": comando guardar");
					i = fileItems.iterator();
					guardarImportarEstudiante(request, session, usuVO, i);
					break;
				}
				break;
			case ParamsVO.FICHA_IMPORTAR_PERSONAL:
				FICHA = FICHA_IMPORTAR_PERSONAL;
				switch (CMD) {
				case ParamsVO.CMD_GUARDAR:
					i = fileItems.iterator();
					guardarImportarPersonal(request, session, usuVO, i);
					break;
				}
				break;
			}
		} catch (FileUploadBase.InvalidContentTypeException e) {
			e.printStackTrace();
			System.out.println("IMPORTARFOTOGRAFIA" + ": error de contenido "
					+ e.getMessage());
			return FICHA;
		} catch (Exception e) {
			System.out.println("IMPORTARFOTOGRAFIA" + ": error interno "
					+ e.getMessage());
			e.printStackTrace();
			return FICHA;
		}
		return FICHA;
	}

	public void guardarImportarEstudiante(HttpServletRequest request,
			HttpSession session, Login usuVO, Iterator i) throws Exception {
		FileItem item = null;
		ImportarFotografiaIO importarFotografiaIO = new ImportarFotografiaIO(
				importarFotografiaDAO);
		try {
			// System.out.println("IMPORTARFOTOGRAFIA" +
			// ": inicia el guardado");
			while (i.hasNext()) {
				item = (FileItem) i.next();
				if (!item.isFormField()) {
					if (item.getFieldName().equals("filArchivo")) {
						// System.out.println("IMPORTARFOTOGRAFIA"+": encuentra archivo: "+item.getContentType());
						// System.out.println(item.getContentType());
						if (!validarTipo(item.getContentType())) {
							// System.out.println("IMPORTARFOTOGRAFIA"+": error de tipo de archivo");
							request.setAttribute(ParamsVO.SMS,
									"El archivo no corresponde a los tipos de contenido permitidos");
							Logger.print(usuVO.getUsuarioId(), "Fallo la importacion de Fotografia -Importar Estudiante. ",
									7, 1, this.toString());	
							return;
						}
						// System.out.println("IMPORTARFOTOGRAFIA"+": guardando en disco");
						List l = importarFotografiaIO.subirFileEstudiante(item,
								context.getRealPath("/"),
								Long.parseLong(usuVO.getUsuarioId()));
						request.setAttribute("resultado", "1");
						request.setAttribute("resultadoImportar", l);
						// System.out.println("IMPORTARFOTOGRAFIA"+": guardado exitoso");
					}
				}
			}
			request.setAttribute(ParamsVO.SMS,
					"El archivo fue cargado satisfactoriamente y se esta procesando");
			Logger.print(usuVO.getUsuarioId(), "Importo Fotografia -Importar Estudiante. ",
					7, 1, this.toString());	
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "El archivo no fue importado : "
					+ e.getMessage());
		}
	}

	public void guardarImportarPersonal(HttpServletRequest request,
			HttpSession session, Login usuVO, Iterator i) throws Exception {
		FileItem item = null;
		ImportarFotografiaIO importarFotografiaIO = new ImportarFotografiaIO(
				importarFotografiaDAO);
		try {
			while (i.hasNext()) {
				item = (FileItem) i.next();
				if (!item.isFormField()) {
					if (item.getFieldName().equals("filArchivo")) {
						// System.out.println(item.getContentType());
						if (!validarTipo(item.getContentType())) {
							request.setAttribute(ParamsVO.SMS,
									"El archivo no corresponde a los tipos de contenido permitidos");
							Logger.print(usuVO.getUsuarioId(), "Fallo Importración de Fotografia -Importar Personal. ",
									7, 1, this.toString());	
							return;
						}
						List l = importarFotografiaIO.subirFilePersonal(item,
								context.getRealPath("/"),
								Long.parseLong(usuVO.getUsuarioId()));
						request.setAttribute("resultado", "1");
						request.setAttribute("resultadoImportar", l);
					}
				}
			}
			request.setAttribute(ParamsVO.SMS,
					"El archivo fue importado satisfactoriamente");
			Logger.print(usuVO.getUsuarioId(), "Importo Fotografia -Importar Personal. ",
					7, 1, this.toString());	
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "El archivo no fue importado : "
					+ e.getMessage());
		}
	}

	public boolean validarTipo(String a) {
		boolean aceptado = false;
		for (int n = 0; n < tipos.length; n++) {
			if (a.equals((String) tipos[n])) {
				aceptado = true;
				break;
			}
		}
		return aceptado;
	}

}
