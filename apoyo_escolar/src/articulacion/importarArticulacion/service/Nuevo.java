package articulacion.importarArticulacion.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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

import articulacion.grupoArt.dao.GrupoArtDAO;
import articulacion.grupoArt.vo.GrupoArtVO;
import articulacion.grupoArt.vo.ParamsVO;
import articulacion.importarArticulacion.dao.ImportarArticulacionDAO;
import articulacion.importarArticulacion.objetos.ResultadoVO;
import articulacion.importarArticulacion.service.Excel;
import siges.importar.dao.ImportarDAO;
import siges.login.beans.Login;
import siges.plantilla.beans.Logros;
import articulacion.plantillaArticulacion.io.ExcelIO;
import siges.perfil.vo.Url;
import articulacion.plantillaArticulacion.vo.DatosVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.dao.Ruta;
import siges.dao.Ruta2;
import articulacion.plantillaArticulacion.dao.FiltroPlantillaDAO;
import articulacion.plantillaArticulacion.dao.PlantillaDAO;
import siges.util.Properties;

public class Nuevo extends Service {

	private Cursor c;

	private String FICHA_NUEVO;

	private PlantillaDAO plantillaDAO;

	private Timestamp f2;

	private ResourceBundle rb;

	private final String[] tipos = { "application/vnd.ms-excel",
			"application/x-msexcel", "application/zip" };

	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String dispatcher[] = new String[2];
		HttpSession session = request.getSession();
		c = new Cursor();

		// ***Administracion de grupo
		DatosVO datosVO = (DatosVO) session
				.getAttribute("filtroPlantillaArticulacionVO");

		Login usuVO = (Login) session.getAttribute("login");
		plantillaDAO = new PlantillaDAO(c);
		// ****************************************************************
		FICHA_NUEVO = config.getInitParameter("FICHA_NUEVO");
		int CMD = getCmd(request, session);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);

		f2 = new java.sql.Timestamp(new java.util.Date().getTime());

		// System.out.println("llego al nuevo de plantillas "+CMD);
		ImportarArticulacionDAO importarADAO;
		String nombre[];
		nombre = new String[2];
		response.setContentType("text/html");

		String[] a = new String[4];
		int tipo = -1;
		Login login = (Login) request.getSession().getAttribute("login");

		// switch (CMD){
		// case Params.CMD_NUEVO:
		// *******************************
		try {
			importarADAO = new ImportarArticulacionDAO(c);
			String[] val = subirArchivo(request);
			tipo = Integer.parseInt(val[0]);

			if (tipo < 0) {
				request.setAttribute("mensaje", val[1]);
				dispatcher[0] = String.valueOf(Params.FORWARD);
				dispatcher[1] = FICHA_NUEVO;
				return dispatcher;
			}
			String msg = val[1];
			nombre[0] = val[2];
			nombre[1] = val[3];
			String resultado = validarArchivo(request, tipo, login, nombre,
					importarADAO);
			if (resultado != null) {
				request.setAttribute("mensaje", resultado);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("Error de este try inicio nuevo " + this +
			// ":");
			throw new ServletException(e);
		} finally {
			if (c != null)
				c.cerrar();
		}
		// break;
		// }
		dispatcher[0] = String.valueOf(Params.FORWARD);
		dispatcher[1] = FICHA_NUEVO;
		return dispatcher;

	}

	public String[] subirArchivo(javax.servlet.http.HttpServletRequest request)
			throws ServletException, IOException {
		String[] a = new String[4];
		int tipo = -1;
		Login login = (Login) request.getSession().getAttribute("login");
		rb = ResourceBundle
				.getBundle("articulacion.importarArticulacion.bundle.sentencias");
		String path = rb.getString("importarEstudiante.pathSubir") + "."
				+ login.getUsuarioId();
		String pathSubir = Ruta.get(context, path);
		String pathTemporal = Ruta.get(context,
				rb.getString("importarEstudiante.temporal"));
		String fileName = "";
		String nombreCampo, valorCampo;
		int cantidad = 0;
		Iterator i;
		List fileItems;
		File fichero = null;
		FileItem item = null;
		try {
			// DiskFileUpload fu = new DiskFileUpload();
			// fileItems = fu.parseRequest(request, 4096, 1024 *
			// 1024,pathTemporal);
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			fileItems = upload.parseRequest(request);
			// System.out.println("carga file" + fileItems.size());
			/* TRAER LOS CAMPOS QUE NO SON FILE */
			i = fileItems.iterator();
			while (i.hasNext()) {
				// System.out.println("file=null");
				fileName = null;
				item = (FileItem) i.next();
				if (item.isFormField()) {
					// System.out.println("file=null1");
					nombreCampo = item.getFieldName();
					valorCampo = item.getString();
					if (nombreCampo.equals("tipo")) {
						// System.out.println("tipo en
						// Importacion-"+valorCampo);
						// System.out.println("file===="+tipo);
						try {
							tipo = Integer.parseInt(valorCampo);
							a[0] = "" + tipo;
						} catch (NumberFormatException e) {
							e.printStackTrace();
							tipo = Integer.parseInt(valorCampo.substring(
									valorCampo.length() - 1,
									valorCampo.length()));
							a[0] = "" + tipo;
						}
					}
				}
			}
			/* VALIDAR TIPO DE LOS ARCHIVOS */
			// System.out.println("file=null3");
			i = fileItems.iterator();
			String validacion = validarTipo(i);
			if (validacion != null) {
				// System.out.println("file=null4");
				a[0] = "" + (tipo * -1);
				a[1] = validacion;
				return a;
			}
			/* SUBIR LOS ARCHIVOS */
			i = fileItems.iterator();
			while (i.hasNext()) {
				// System.out.println("ARCHIVOs1");
				cantidad++;
				fileName = null;
				item = (FileItem) i.next();
				if (!item.isFormField()) {
					// System.out.println("ARCHIVOs2");
					fileName = item.getName();
					if (fileName.trim().compareTo("") != 0) {
						// System.out.println("ARCHIVOs3");
						if (item.getFieldName().equals("archivo")) {

							File f = new File(pathSubir);
							// System.out.println("ARCHIVOsFFF" + f);

							if (!f.exists())
								FileUtils.forceMkdir(f);

							a[3] = getNombre(tipo);
							// System.out.println("A3" + getNombre(tipo));
							a[2] = pathSubir;
							// System.out.println("A3" + pathSubir);
							// System.out.println("ARCHIVOs4");
							fichero = new File(a[2] + a[3]);
							item.write(fichero);
						}
					}
				}
			}
		} catch (FileUploadBase.InvalidContentTypeException e) {
			e.printStackTrace();
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
			e.printStackTrace();
		} catch (FileUploadBase.UnknownSizeException e) {
			// setMensaje("El archivo no tiene un tamano especificado
			// :"+e.toString());
			// return (tipo*-1);
			a[1] = "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n  - "
					+ "El archivo no tiene un tamano especificado :"
					+ e.toString() + "\n";
			a[0] = "" + (tipo * -1);
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// setMensaje("No hay un archivo especificado :"+e.toString());
			// return (tipo*-1);
			a[1] = "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n  - "
					+ "No hay un archivo especificado :" + e.toString() + "\n";
			a[0] = "" + (tipo * -1);
			e.printStackTrace();
		} catch (FileUploadException e) {
			// setMensaje("Error tratando de subir el archivo: "+e.toString());
			// return (tipo*-1);
			a[1] = "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n  - "
					+ "Error tratando de subir el archivo: " + e.toString()
					+ "\n";
			a[0] = "" + (tipo * -1);
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("String " + e.toString());
			// setMensaje("Error de Aplicacinn : "+ e.toString());
			// return (tipo*-1);
			a[1] = "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n  - "
					+ "Error de Aplicacinn : " + e.toString() + "\n";
			a[0] = "" + (tipo * -1);
			e.printStackTrace();

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

	public String validarArchivo(HttpServletRequest request, int tipo,
			Login login, String[] nombre, ImportarArticulacionDAO importarADAO)
			throws ServletException, IOException {
		login = (Login) request.getSession().getAttribute("login");
		String pathPlantilla = null;
		String pathDownload = null;
		String relativo = null;
		String path = null;

		Excel excel = new Excel(importarADAO);
		String[] params = { login.getInstId(), login.getSedeId(),
				login.getJornadaId(), login.getUsuarioId() };
		path = rb.getString("estudiante.PathDownload") + "."
				+ login.getUsuarioId();// ruta de carpetas
		relativo = Ruta.getRelativo(context, path);
		pathPlantilla = Ruta2.get(context,
				rb.getString("estudiante.PathPlantilla"))
				+ rb.getString("estudiante.NombrePlantilla");// archivo
																// deplantilla
		pathDownload = Ruta.get(context, path);// path del nuevo archivo
		// validar estructura del archivo
		if (!excel.validarFormatoEstudiante(tipo, nombre, pathPlantilla,
				pathDownload, params, login.getUsuarioId(), login.getPerfil())) {
			if (!excel.getError()) {
				ponerReporte(
						login.getUsuarioId(),
						relativo + nombre[1],
						"xls",
						"Inconsistencias en importacinn de importacion de Estudiantes Articulacinn: ",
						10);
				request.setAttribute("plantilla", relativo + nombre[1]);
			}
			return "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n  - "
					+ excel.getMensaje() + "\n";
		}
		// importar archivo/
		ResultadoVO res = excel.importarEstudiante(tipo, nombre, pathPlantilla,
				pathDownload, login.getUsuarioId(), login.getMetodologiaId());
		if (res == null) {
			// System.out.println("Vacia esta cosa");

			if (!excel.getError()) {
				request.setAttribute("plantilla", relativo + nombre[1]);
			}

			return "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n  - "
					+ excel.getMensaje() + "\n";
		} else {
			request.setAttribute("resultado", res);
			// request.setAttribute("resultado2",importarDAO.getResultado2());
		}

		return null;
	}

	public String validarTipo(Iterator it) {
		String band = null;
		boolean archivo = false;
		String fileName;
		String a;
		FileItem item;
		while (it.hasNext()) {
			// System.out.println("llego al hasnext");
			fileName = null;
			item = (FileItem) it.next();
			if (!item.isFormField()) {
				fileName = item.getName();
				// System.out.println("el nombre del archiuvo es " + fileName);
				if (fileName.trim().compareTo("") != 0) {
					archivo = true;
					a = item.getContentType();
					String tipoa = a;
					// System.out.println("TipoArchivo=" + a);
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
		String nom = "Inconsistencias_Estudiantes " + f2.toString();
		nom = formatear(nom) + ".xls";

		return nom;
	}

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
		c.registrar(rb.getString("ReporteInsertar"), list);
	}

	public String formatear(String a) {
		return (a.replace(' ', '_').replace('n', 'a').replace('n', 'e')
				.replace('n', 'i').replace('n', 'o').replace('n', 'u')
				.replace('n', 'A').replace('n', 'E').replace('n', 'I')
				.replace('n', 'O').replace('n', 'U').replace('n', 'n')
				.replace('n', 'N').replace('n', 'a').replace('n', 'e')
				.replace('n', 'i').replace('n', 'o').replace('n', 'u')
				.replace('n', 'A').replace('n', 'E').replace('n', 'I')
				.replace('n', 'O').replace('n', 'U').replace('n', 'c')
				.replace(':', '_').replace('.', '_').replace('/', '_').replace(
				'\\', '_'));
	}
}
