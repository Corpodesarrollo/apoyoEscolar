package siges.importar.evaluacion;

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

import siges.dao.Cursor;
import siges.dao.Ruta;
import siges.dao.Ruta2;
import siges.importar.Excel;
import siges.importar.dao.ImportarDAO;
import siges.login.beans.Login;
import siges.util.Properties;

/**
 * siges.importar.evaluacion<br>
 * Funcinn: Servicio que recibe el formulario de importacion, sube el archivo a
 * disco local del servidor e instancia las clses que validan e importan el
 * archivo de datos <br>
 */
public class ControllerImportarSave extends HttpServlet {
	private String er;// nombre de la pagina a la que ira si hay errores

	private ResourceBundle rb;

	private Cursor cursor;// objeto que maneja las sentencias sql

	private java.sql.Timestamp f2;

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
		ImportarDAO importarDAO;
		HttpSession session = request.getSession();
		f2 = new java.sql.Timestamp(new java.util.Date().getTime());
		String nombre[];
		nombre = new String[2];
		// String buscar = null;
		String respuesta = "La información fue ingresada satisfactoriamente ";
		String ant = getServletConfig().getInitParameter("ant");
		er = getServletContext().getInitParameter("error");
		// String home = getServletContext().getInitParameter("home");
		cursor = new Cursor();
		try {
			request.removeAttribute("mensaje");
			importarDAO = new ImportarDAO(cursor);
			Login login = (Login) session.getAttribute("login");
			// System.out.println("-SIGES-"+(login!=null?login.getUsuarioId():"")+"Recepcion
			// Importar Save");
			String[] val = subirArchivo(request);
			tipo = Integer.parseInt(val[0]);
			String msg = val[1];
			if (tipo < 0) {
				request.setAttribute("mensaje", msg);
				return (ant += "?tipo=" + (tipo * -1));
			}
			// System.out.println("-ARCHIVO-"+val[0]+"_"+val[1]+"_"+val[2]+"_"+val[3]);
			nombre[0] = val[2];
			nombre[1] = val[3];
			String resultado = validarArchivo(request, tipo, login, nombre,
					importarDAO);
			if (resultado != null) {
				request.setAttribute("mensaje", resultado);
				return (ant += "?tipo=" + tipo);
			}
			request.setAttribute("mensaje",
					"VERIFIQUE LA SIGUIENTE INFORMACION: \n\n  - " + respuesta
							+ "\n");
			return (ant += "?tipo=" + tipo);
		} catch (Exception e) {
			System.out.println("Error " + this + ":" + e.toString());
			throw new ServletException(e);
		} finally {
			if (cursor != null)
				cursor.cerrar();
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
	public String[] subirArchivo(HttpServletRequest request)
			throws ServletException, IOException {
		String[] a = new String[4];
		int tipo = -1;
		Login login = (Login) request.getSession().getAttribute("login");
		rb = ResourceBundle.getBundle("siges.importar.bundle.importar");
		String path = rb.getString("importarLogro.pathSubir") + "."
				+ login.getUsuarioId();
		String pathSubir = Ruta.get(getServletContext(), path);
		String pathTemporal = Ruta.get(getServletContext(),
				rb.getString("importarLogro.temporal"));
		String fileName = "";
		String nombreCampo, valorCampo;
		int cantidad = 0;
		Iterator i;
		List fileItems;
		File fichero = null;
		FileItem item = null;
		try {
			// DiskFileUpload fu = new DiskFileUpload();
			// fileItems = fu.parseRequest(request, 4096, 1024 * 1024,
			// pathTemporal);
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
						// System.out.println("tipo en
						// Importacion-"+valorCampo);
						try {
							tipo = Integer.parseInt(valorCampo);
							a[0] = "" + tipo;
						} catch (NumberFormatException e) {
							tipo = Integer.parseInt(valorCampo.substring(
									valorCampo.length() - 1,
									valorCampo.length()));
							a[0] = "" + tipo;
						}
					}
				}
			}
			/* VALIDAR TIPO DE LOS ARCHIVOS */
			i = fileItems.iterator();
			String validacion = validarTipo(i);
			if (validacion != null) {
				a[0] = "" + (tipo * -1);
				a[1] = validacion;
				return a;
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
							a[3] = getNombre(tipo);
							a[2] = pathSubir;
							// System.out.println("ARCHIVO
							// "+nombre[0]+nombre[1]);
							fichero = new File(a[2] + a[3]);
							item.write(fichero);
						}
					}
				}
			}
		} catch (FileUploadBase.InvalidContentTypeException e) {
			// setMensaje("El archivo no es del tipo solicitado:"+e.toString());
			a[1] = "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n  - "
					+ "El archivo no es del tipo solicitado:" + e.toString()
					+ "\n";
			a[0] = "" + (tipo * -1);
			// return (tipo*-1);
		} catch (FileUploadBase.SizeLimitExceededException e) {
			// setMensaje("El archivo se pasa del tamano permitido
			// :"+e.toString());
			// return (tipo*-1);
			a[1] = "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n  - "
					+ "El archivo excede del tamano permitido :" + e.toString()
					+ "\n";
			a[0] = "" + (tipo * -1);
		} catch (FileUploadBase.UnknownSizeException e) {
			// setMensaje("El archivo no tiene un tamano especificado
			// :"+e.toString());
			// return (tipo*-1);
			a[1] = "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n  - "
					+ "El archivo no tiene un tamano especificado :"
					+ e.toString() + "\n";
			a[0] = "" + (tipo * -1);
		} catch (FileNotFoundException e) {
			// setMensaje("No hay un archivo especificado :"+e.toString());
			// return (tipo*-1);
			a[1] = "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n  - "
					+ "No hay un archivo especificado :" + e.toString() + "\n";
			a[0] = "" + (tipo * -1);
		} catch (FileUploadException e) {
			// setMensaje("Error tratando de subir el archivo: "+e.toString());
			// return (tipo*-1);
			a[1] = "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n  - "
					+ "Error tratando de subir el archivo: " + e.toString()
					+ "\n";
			a[0] = "" + (tipo * -1);
		} catch (Exception e) {
			// setMensaje("Error de Aplicacinn : "+ e.toString());
			// return (tipo*-1);
			a[1] = "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n  - "
					+ "Error de Aplicacinn : " + e.toString() + "\n";
			a[0] = "" + (tipo * -1);
		}
		if (cantidad == 0) {
			a[1] = "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n  - "
					+ "No se han selecionado archivos a importar" + "\n";
			// setMensaje("No se han selecionado archivos a importar");
			// return (tipo*-1);
			a[0] = "" + (tipo * -1);
		}
		return a;
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
	public String validarArchivo(HttpServletRequest request, int tipo,
			Login login, String[] nombre, ImportarDAO importarDAO)
			throws ServletException, IOException {
		login = (Login) request.getSession().getAttribute("login");
		String pathPlantilla = null;
		String pathDownload = null;
		String relativo = null;
		String path = null;
		Excel excel = new Excel(importarDAO);
		String[] params = { login.getInstId(), login.getSedeId(),
				login.getJornadaId(), login.getUsuarioId() };
		switch (tipo) {
		case Properties.PLANTILLALOGROASIG:// ASIGNATURA
			path = rb.getString("asignatura.PathAsignatura") + "."
					+ login.getUsuarioId();// ruta de carpetas
			relativo = Ruta.getRelativo(getServletContext(), path);
			pathPlantilla = Ruta2.get(getServletContext(),
					rb.getString("asignatura.PathPlantilla"))
					+ rb.getString("asignatura.Plantilla");// archivo de
															// plantilla
			pathDownload = Ruta.get(getServletContext(), path);// path del nuevo
																// archivo
			// validar estructura del archivo*/
			if (!excel.validarFormatoAsignatura(tipo, nombre, pathPlantilla,
					pathDownload, params, login.getUsuarioId(),
					login.getPerfil())) {
				if (!excel.getError()) {
					ponerReporte(
							login.getUsuarioId(),
							relativo + nombre[1],
							"xls",
							"Inconsistencias en importacinn de evaluacinn de Asignaturas: ",
							6);
					request.setAttribute("plantilla", relativo + nombre[1]);
				}
				return "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n  - "
						+ excel.getMensaje() + "\n";
			}
			// importar archivo/
			if (!excel.importarAsignatura(tipo, nombre, pathPlantilla,
					pathDownload, login.getUsuarioId())) {
				if (!excel.getError()) {
					request.setAttribute("plantilla", relativo + nombre[1]);
				}
				return "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n  - "
						+ excel.getMensaje() + "\n";
			} else {
				if (excel.getAdvertencia() != null) {
					request.setAttribute("resultado3", excel.getAdvertencia());
				}
				request.setAttribute("resultado", importarDAO.getResultado());
				request.setAttribute("resultado2", importarDAO.getResultado2());
			}
			break;
		case Properties.PLANTILLAAREADESC:// AREA
			path = rb.getString("area.PathArea") + "." + login.getUsuarioId();// ruta
																				// de
																				// carpetas
			relativo = Ruta.getRelativo(getServletContext(), path);
			pathPlantilla = Ruta2.get(getServletContext(),
					rb.getString("area.PathPlantilla"))
					+ rb.getString("area.Plantilla");// archivo
														// de
														// plantilla
			pathDownload = Ruta.get(getServletContext(), path);// path del
			// nuevo archivo
			/* validar estructura del archivo */
			if (!excel.validarFormatoArea(tipo, nombre, pathPlantilla,
					pathDownload, params, login.getUsuarioId(),
					login.getPerfil())) {
				if (!excel.getError()) {
					ponerReporte(
							login.getUsuarioId(),
							relativo + nombre[1],
							"xls",
							"Inconsistencias en importacinn de evaluación de área: ",
							6);
					request.setAttribute("plantilla", relativo + nombre[1]);
				}
				return "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n  - "
						+ excel.getMensaje() + "\n";
			}
			// importar archivo/
			if (!excel.importarArea(tipo, nombre, pathPlantilla, pathDownload,
					login.getUsuarioId())) {
				if (!excel.getError()) {
					request.setAttribute("plantilla", relativo + nombre[1]);
				}
				return "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n  - "
						+ excel.getMensaje() + "\n";
			} else {
				if (excel.getAdvertencia() != null) {
					request.setAttribute("resultado3", excel.getAdvertencia());
				}
				request.setAttribute("resultado", importarDAO.getResultado());
				request.setAttribute("resultado2", importarDAO.getResultado2());
			}
			break;
		case Properties.PLANTILLAPREE:// PREESCOLAR

			path = rb.getString("preescolar.PathPreescolar") + "."
					+ login.getUsuarioId();// ruta
											// de
											// carpetas
			relativo = Ruta.getRelativo(getServletContext(), path);
			pathPlantilla = Ruta2.get(getServletContext(),
					rb.getString("preescolar.PathPlantilla"))
					+ rb.getString("preescolar.Plantilla");// archivo
															// de
															// plantilla
			pathDownload = Ruta.get(getServletContext(), path);// path del

			/**
			 * Traer dimensiones del colegio
			 * */
			List listaDim = new ArrayList();

			/* validar estructura del archivo */
			if (!excel.validarFormatoPreescolar(tipo, nombre, pathPlantilla,
					pathDownload, params, login.getUsuarioId(),
					login.getPerfil(), listaDim)) {
				if (!excel.getError()) {
					ponerReporte(
							login.getUsuarioId(),
							relativo + nombre[1],
							"xls",
							"Inconsistencias en importacinn de evaluacinn de Preescolar: ",
							6);
					request.setAttribute("plantilla", relativo + nombre[1]);
				}
				return "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n  - "
						+ excel.getMensaje() + "\n";
			}
			// importar archivo/
			if (!excel.importarPreescolar(tipo, nombre, pathPlantilla,
					pathDownload, login.getUsuarioId(), listaDim)) {
				if (!excel.getError()) {
					request.setAttribute("plantilla", relativo + nombre[1]);
				}
				return "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n  - "
						+ excel.getMensaje() + "\n";
			} else {
				if (excel.getAdvertencia() != null) {
					request.setAttribute("resultado3", excel.getAdvertencia());
				}
				request.setAttribute("resultado", importarDAO.getResultado());
			}
			break;
		}
		return null;
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
	public String validarTipo(Iterator it) {
		String band = null;
		boolean archivo = false;
		String fileName;
		String a;
		FileItem item;
		while (it.hasNext()) {
			fileName = null;
			item = (FileItem) it.next();
			if (!item.isFormField()) {
				fileName = item.getName();
				if (fileName.trim().compareTo("") != 0) {
					archivo = true;
					a = item.getContentType();
					String tipoa = a;
					// System.out.println("TipoArchivo="+a);
					boolean aceptado = false;
					for (int n = 0; n < tipos.length; n++) {
						if (a.equals(tipos[n])) {
							aceptado = true;
							break;
						}
					}
					if (!aceptado) {
						a = "Error con el archivo. posible problema:\n";
						a += "1. No es una hoja de calculo de Microsoft Excel ("
								+ tipoa + ")\n";
						a += "2. El archivo esta abierto(cierrelo e intente de nuevo)";
						// setMensaje(a);
						band = "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n  - "
								+ a + "\n";
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
		case Properties.PLANTILLALOGROASIG:// ASIGNATURA
			nom = "Inconsistencias_Evaluacion_Asignatura_Logro_"
					+ f2.toString().replace(':', '-').replace('.', '-')
					+ ".xls";
			break;
		case Properties.PLANTILLAAREADESC:// AREA
			nom = "Inconsistencias_Evaluacion_Area_Descriptor_"
					+ f2.toString().replace(':', '-').replace('.', '-')
					+ ".xls";
			break;
		case Properties.PLANTILLAPREE:// PREESCOLAR
			nom = "Inconsistencias_Evaluacion_Preescolar_"
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
}