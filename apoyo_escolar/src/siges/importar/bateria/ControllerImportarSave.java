package siges.importar.bateria;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Ruta;
import siges.dao.Ruta2;
import siges.importar.Excel;
import siges.importar.dao.ImportarDAO;
import siges.login.beans.Login;
import siges.util.Properties;

/**
 * siges.importar.bateria<br>
 * Funcinn: Recibe el formulario de peticion de importacion de bateria, sube el
 * archivo a disco local del servidor y llama a la clase que valida e importa
 * los datos de la plantilla de bateira <br>
 */
public class ControllerImportarSave extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private Cursor cursor;// objeto que maneja las sentencias sql

	private String mensaje;// mensaje en caso de error


	private String ant;// pagina a la que ira en caso de que no se pueda

	private String nombre[];

	private ResourceBundle rb;

	private java.sql.Timestamp f2;

	// private Login login;
	private final String[] tipos = { "application/vnd.ms-excel",
			"application/x-msexcel", "application/zip" };



	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * <br>
	 *             Return Type: String<br>
	 *             Version 1.1.<br>
	 */
	public String process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int tipo;
		HttpSession session = request.getSession();
		f2 = new java.sql.Timestamp(new java.util.Date().getTime());
		nombre = new String[2];
		String respuesta = "La información fue ingresada satisfactoriamente ";
		ant = getServletConfig().getInitParameter("ant");
		mensaje = null;
		cursor = new Cursor();
		ImportarDAO importarDAO = null;
		try {
			importarDAO = new ImportarDAO(cursor);
			Login login = (Login) session.getAttribute("login");
			// System.out.println("-SIGES-"+(login!=null?login.getUsuarioId():"")+"Recepcion
			// Importar Save");
			tipo = subirArchivo(request);
			if (tipo < 0) {
				request.setAttribute("mensaje", getMensaje());
				return (ant += "?tipo=" + (tipo * -1));
			}
			if (!validarArchivo(request, tipo, login, importarDAO)) {
				request.setAttribute("mensaje", getMensaje());
				return (ant += "?tipo=" + tipo);
			}
			setMensaje(respuesta);
			request.setAttribute("mensaje", getMensaje());
			return (ant += "?tipo=" + tipo);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error " + this + ":" + e.toString());
			throw new ServletException(e);
		} finally {
			if (cursor != null)
				cursor.cerrar();
			if (importarDAO != null)
				importarDAO.cerrar();
		}
	}

	/**
	 * @param request
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * <br>
	 *             Return Type: int<br>
	 *             Version 1.1.<br>
	 */
	public int subirArchivo(HttpServletRequest request)
			throws ServletException, IOException {
		int tipo = -1;
		Login login = (Login) request.getSession().getAttribute("login");
		rb = ResourceBundle.getBundle("siges.importar.bundle.importar");
		String path = rb.getString("importarLogro.pathSubir") + "."
				+ login.getUsuarioId();
		String pathSubir = Ruta.get(getServletContext(), path);
		String fileName = "";
		String nombreCampo, valorCampo;
		int cantidad = 0;
		Iterator i;
		List fileItems;
		File fichero = null;
		FileItem item = null;
		try {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			fileItems = upload.parseRequest(request);
			/* TRAER LOS CAMPOS QUE NO SON FILE */
			i = fileItems.iterator();
			while (i.hasNext()) {
				fileName = null;
				item = (FileItem) i.next();
				if (item.isFormField()) {
					nombreCampo = item.getFieldName();
					valorCampo = item.getString();
					if (nombreCampo.equals("tipo")) {
						try {
							tipo = Integer.parseInt(valorCampo);
						} catch (NumberFormatException e) {
							tipo = Integer.parseInt(valorCampo.substring(
									valorCampo.length() - 1,
									valorCampo.length()));
						}
					}
				}
			}
			/* VALIDAR TIPO DE LOS ARCHIVOS */
			i = fileItems.iterator();
			if (!validarTipo(i)) {
				return (tipo * -1);
			}
			/* SUBIR LOS ARCHIVOS */
			i = fileItems.iterator();
			while (i.hasNext()) {
				cantidad++;
				fileName = null;
				item = (FileItem) i.next();
				if (!item.isFormField()) {
					fileName = item.getName();
					if (fileName.trim().compareTo("") != 0) {
						if (item.getFieldName().equals("archivo")) {
							File f = new File(pathSubir);
							if (!f.exists())
								FileUtils.forceMkdir(f);
							nombre[1] = getNombre(tipo);
							nombre[0] = pathSubir;
							fichero = new File(nombre[0] + nombre[1]);
							item.write(fichero);
						}
					}
				}
			}
		} catch (FileUploadBase.InvalidContentTypeException e) {
			setMensaje("El archivo no es del tipo solicitado:" + e.toString());
			return (tipo * -1);
		} catch (FileUploadBase.SizeLimitExceededException e) {
			setMensaje("El archivo se pasa del tamano permitido :"
					+ e.toString());
			return (tipo * -1);
		} catch (FileUploadBase.UnknownSizeException e) {
			setMensaje("El archivo no tiene un tamano especificado :"
					+ e.toString());
			return (tipo * -1);
		} catch (FileNotFoundException e) {
			setMensaje("No hay un archivo especificado :" + e.toString());
			return (tipo * -1);
		} catch (FileUploadException e) {
			setMensaje("Error tratando de subir el archivo: " + e.toString());
			return (tipo * -1);
		} catch (Exception e) {
			setMensaje("Error de Aplicacinn : " + e.toString());
			return (tipo * -1);
		}
		if (cantidad == 0) {
			setMensaje("No se han selecionado archivos a importar");
			return (tipo * -1);
		}
		return tipo;
	}

	/**
	 * @param request
	 * @param tipo
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * <br>
	 *             Return Type: boolean<br>
	 *             Version 1.1.<br>
	 */
	public boolean validarArchivo(HttpServletRequest request, int tipo,
			Login login, ImportarDAO importarDAO) throws ServletException,
			IOException {
		// System.out.println("validarArchivo");
		login = (Login) request.getSession().getAttribute("login");
		String pathPlantilla = null;
		String pathDownload = null;
		String relativo = null;
		String path = null;
		Excel excel = new Excel(importarDAO);
		String[] params = { login.getInstId(), login.getSedeId(),
				login.getJornadaId(), login.getUsuarioId() };
		switch (tipo) {
		case Properties.PLANTILLABATLOGRO:
			path = rb.getString("plantillaLogro.PathPlantillaLogro") + "."
					+ login.getUsuarioId();// ruta
											// de
											// carpetas
			relativo = Ruta.getRelativo(getServletContext(), path);
			pathPlantilla = Ruta2.get(getServletContext(),
					rb.getString("plantillaLogro.PathPlantilla"))
					+ rb.getString("plantillaLogro.Plantilla");// archivo
																// de
																// plantilla
			pathDownload = Ruta.get(getServletContext(), path);// path del
																// nuevo archivo

			/* validar estructura del archivo */
			// System.out.println("antes validarFormatoBateriaLogro");
			if (!excel.validarFormatoBateriaLogro(tipo, nombre, pathPlantilla,
					pathDownload, params, login.getLogNumPer())) {
				if (!excel.getError()) {
					ponerReporte(
							login.getUsuarioId(),
							relativo + nombre[1],
							"xls",
							"Inconsistencias en importacinn de bateria de Logros: ",
							6);
					request.setAttribute("plantilla", relativo + nombre[1]);
				}
				setMensaje(excel.getMensaje());
				return false;
			}
			/* importar archivo */
			// System.out.println("importarBateriaLogro");
			if (!excel.importarBateriaLogro(tipo, nombre, pathPlantilla,
					pathDownload, login.getUsuarioId())) {
				if (!excel.getError()) {
					request.setAttribute("plantilla", relativo + nombre[1]);
				}
				setMensaje(excel.getMensaje());
				return false;
			} else {
				request.setAttribute("resultado", importarDAO.getResultado());
			}
			break;
		case Properties.PLANTILLABATDESCRIPTOR:// BATERIA DESCRIPTOR
			path = rb.getString("plantillaDescriptor.PathPlantillaDescriptor")
					+ "." + login.getUsuarioId();// ruta
													// de
													// carpetas
			relativo = Ruta.getRelativo(getServletContext(), path);
			pathPlantilla = Ruta2.get(getServletContext(),
					rb.getString("plantillaDescriptor.PathPlantilla"))
					+ rb.getString("plantillaDescriptor.Plantilla");// archivo
																	// de
																	// plantilla
			pathDownload = Ruta.get(getServletContext(), path);// path del
																// nuevo archivo
			/* validar estructura del archivo */
			if (!excel.validarFormatoBateriaDescriptor(tipo, nombre,
					pathPlantilla, pathDownload, params, login.getLogNumPer())) {
				if (!excel.getError()) {
					ponerReporte(
							login.getUsuarioId(),
							relativo + nombre[1],
							"xls",
							"Inconsistencias en importacinn de bateria de Descriptores: ",
							6);
					request.setAttribute("plantilla", relativo + nombre[1]);
				}
				setMensaje(excel.getMensaje());
				return false;
			}
			/* importar archivo */
			if (!excel.importarBateriaDescriptor(tipo, nombre, pathPlantilla,
					pathDownload, login.getUsuarioId())) {
				if (!excel.getError()) {
					request.setAttribute("plantilla", relativo + nombre[1]);
				}
				setMensaje(excel.getMensaje());
				return false;
			} else {
				request.setAttribute("resultado", importarDAO.getResultado());
			}
			break;
		}
		return true;
	}

	/**
	 * @param us
	 * @param rec
	 * @param tipo
	 * @param nombre
	 * @param modulo
	 * <br>
	 *            Return Type: void<br>
	 *            Version 1.1.<br>
	 */
	public void ponerReporte(String us, String rec, String tipo, String nombre,
			int modulo) {
		Collection list = new ArrayList();
		Object[] o = new Object[2];
		Integer cadena = new Integer(Types.VARCHAR);
		Integer entero = new Integer(Types.INTEGER);
		o[0] = cadena;
		o[1] = us;
		list.add(o);// usuario
		o = new Object[2];
		o[0] = cadena;
		o[1] = rec;
		list.add(o);// rec
		o = new Object[2];
		o[0] = cadena;
		o[1] = tipo;
		list.add(o);// tipo
		o = new Object[2];
		o[0] = cadena;
		o[1] = nombre;
		list.add(o);// nombre
		o = new Object[2];
		o[0] = entero;
		o[1] = "" + modulo;
		list.add(o);// modulo
		cursor.registrar(rb.getString("ReporteInsertar"), list);
	}

	/**
	 * @param it
	 * @return<br> Return Type: boolean<br>
	 *             Version 1.1.<br>
	 */
	public boolean validarTipo(Iterator it) {
		boolean band = true;
		String fileName;
		String a;
		FileItem item;
		while (it.hasNext()) {
			fileName = null;
			item = (FileItem) it.next();
			if (!item.isFormField()) {
				fileName = item.getName();
				if (fileName.trim().compareTo("") != 0) {
					a = item.getContentType();
					boolean aceptado = false;
					for (int n = 0; n < tipos.length; n++) {
						if (a.equals(tipos[n])) {
							aceptado = true;
							break;
						}
					}
					if (!aceptado) {
						a = "Error con el archivo, posible problema:\n";
						a += "1. No es una hoja de calculo de Microsoft Excel\n";
						a += "2. El archivo esta abierto(cierrelo e intente de nuevo)\n";
						setMensaje(a);
						band = false;
					}
				}
			}
		}
		return band;
	}

	/**
	 * @param tipo
	 * @return<br> Return Type: String<br>
	 *             Version 1.1.<br>
	 */
	public String getNombre(int tipo) {
		String nom = "";
		switch (tipo) {
		case Properties.PLANTILLABATLOGRO:
			nom = "Inconsistencias_BateriaLogro_"
					+ f2.toString().replace(':', '-').replace('.', '-')
					+ ".xls";
			break;
		case Properties.PLANTILLABATDESCRIPTOR:
			nom = "Inconsistencias_BateriaDescriptor_"
					+ f2.toString().replace(':', '-').replace('.', '-')
					+ ".xls";
			break;
		}
		return nom;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);// redirecciona la peticion a doPost
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String s = process(request, response);
		if (s != null && !s.equals(""))
			ir(1, s, request, response);
	}

	/**
	 * @param a
	 * @param s
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * <br>
	 *             Return Type: void<br>
	 *             Version 1.1.<br>
	 */
	public void ir(int a, String s, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// System.out.println("-SIGES-"+(login!=null?login.getUsuarioId():"")+"Despacho
		// Importar Save");
		if (cursor != null)
			cursor.cerrar();
		RequestDispatcher rd = getServletContext().getRequestDispatcher(s);
		if (a == 1)
			rd.include(request, response);
		else
			rd.forward(request, response);
	}

	/**
	 * @param s
	 * <br>
	 *            Return Type: void<br>
	 *            Version 1.1.<br>
	 */
	public String setMensaje(String s) {
		mensaje = "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n";
		return mensaje += "  - " + s + "\n";
	}

	/**
	 * @return<br> Return Type: String<br>
	 *             Version 1.1.<br>
	 */
	public String getMensaje() {
		return mensaje;
	}

	/*
	 * MODIFICACION PERIODOS EVVALUACION EN LINEA 12-03-10 WG
	 */

	public Collection getListaPeriodo(long numPer, String nomPerDef) {
		Collection collection = new ArrayList();
		for (int i = 1; i <= numPer; i++) {
			Object[] o = new Object[2];
			o[0] = "" + (i);
			o[1] = "" + (i);
			collection.add(o);
		}

		return collection;
	}
}