/**
 * 
 */
package siges.filtro;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import siges.dao.Cursor;
import siges.dao.Ruta;
import siges.dao.Ruta2;
import siges.filtro.dao.FiltroDAO;
import siges.filtro.vo.FichaVO;
import siges.filtro.vo.RecursoVO;

/**
 * 4/02/2009
 * 
 * @author Latined
 * @version 1.2
 */
public class Imagen extends HttpServlet {
	private FiltroDAO filtroDAO = new FiltroDAO(new Cursor());
	private ResourceBundle rb = ResourceBundle.getBundle("common");
	private String context;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);// redirecciona la peticion a doPost
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// System.out.println("entra a calcular imagen");
		int tipo;
		ServletContext sc = getServletContext();
		try {
			String t = request.getParameter("tipo");
			if (t == null) {
				return;
			}
			tipo = Integer.parseInt(t);
			String[] param = request.getParameterValues("param");
			if (param == null) {
				return;
			}
			switch (tipo) {
			case FichaVO.RECURSO_FOTOGRAFIA_ESTUDIANTE:
				fotografiaEstudiante(request, response, param, sc);
				break;
			case FichaVO.RECURSO_FOTOGRAFIA_PERSONAL:
				fotografiaPersonal(request, response, param);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}

	public void fotografiaEstudiante(HttpServletRequest request,
			HttpServletResponse response, String[] param, ServletContext sc)
			throws Exception {
		response.setContentType("image/jpg");
		BufferedInputStream origin = null;
		ServletOutputStream ouputStream = null;
		FileInputStream fi = null;
		int BUFFER_SIZE = 100000;
		int count;
		long estudiante = 0;
		try {
			if (param.length >= 1)
				estudiante = Long.parseLong(param[0].trim());
			else
				fotografiaDefault(request, response);

			String rutaFoto = getFoto(sc, estudiante);
			// String rutaFoto
			// ="C:\\Reportes\\Apoyo_Escolar\\WEB-INF\\private\\fotoEstudiante\\hola.jpg";
			if (rutaFoto != null && rutaFoto.length() > 0) {
				// System.out.println("RUTA ARCHIVO FOTO ESTUDIANTE : "+rutaFoto);
				File f = new File(rutaFoto);
				if (!f.exists()) {
					// System.out.println("RUTA ARCHIVO FOTO ESTUDIANTE : NO EXISTE * ");
					return;
				}
				// System.out.println("RUTA ARCHIVO FOTO ESTUDIANTE : EXISTE");
				byte[] data = new byte[BUFFER_SIZE];
				fi = new FileInputStream(f);
				origin = new BufferedInputStream(fi, BUFFER_SIZE);
				ouputStream = response.getOutputStream();
				while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
					ouputStream.write(data, 0, count);
				}
				// System.out.println("RUTA ARCHIVO FOTO ESTUDIANTE : ESCRIBIO BYTES EN RESPONSE OUTPUTSTREAM * ");
				fi.close();
				origin.close();
				ouputStream.flush();
				ouputStream.close();
			} else {
				fotografiaDefault(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			try {
				if (ouputStream != null)
					ouputStream.close();
				if (origin != null)
					origin.close();
				if (fi != null)
					fi.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// RecursoVO recursoVO = null;
		/*
		 * String contextoTotal; response.setContentType("image/jpg");
		 * BufferedInputStream origin = null; ServletOutputStream ouputStream =
		 * null; FileInputStream fi = null; int BUFFER_SIZE = 100000; int count;
		 * if (param.length >= 1) { long estudiante = Long.parseLong(param[0]);
		 * //recursoVO =
		 * filtroDAO.getFotografiaEstudiante(Long.parseLong(param[0])); String
		 * rutaImg = getFoto(sc, estudiante); try { if(rutaImg!=null){
		 * System.out.println("RUTA ARCHIVO FOTO ESTUDIANTE : "+rutaImg); File f
		 * = new File(rutaImg); if (!f.exists()) { return; } byte[] data = new
		 * byte[BUFFER_SIZE]; fi = new FileInputStream(f); origin = new
		 * BufferedInputStream(fi, BUFFER_SIZE); ouputStream =
		 * response.getOutputStream(); while ((count = origin.read(data, 0,
		 * BUFFER_SIZE)) != -1) { ouputStream.write(data, 0, count); }
		 * ouputStream.flush(); ouputStream.close(); origin.close(); fi.close();
		 * } else { fotografiaDefault(request, response); }
		 * 
		 * } catch (Exception e) { e.printStackTrace(); } finally { try { if
		 * (ouputStream != null) ouputStream.close(); if (origin != null)
		 * origin.close(); if (fi != null) fi.close(); } catch (Exception e) { }
		 * }
		 * 
		 * }
		 */
	}

	public void fotografiaPersonal(HttpServletRequest request,
			HttpServletResponse response, String[] param) throws Exception {
		RecursoVO recursoVO = null;
		if (param.length >= 1) {
			recursoVO = filtroDAO.getFotografiaPersonal(Long
					.parseLong(param[0]));
			if (recursoVO != null) {
				response.setContentType(recursoVO.getMime());
				response.getOutputStream().write(recursoVO.getBytes());
			} else {
				fotografiaDefault(request, response);
			}
		}
	}

	public void fotografiaDefault(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("image/jpg");
		BufferedInputStream origin = null;
		ServletOutputStream ouputStream = null;
		FileInputStream fi = null;
		int BUFFER_SIZE = 100000;
		int count;
		try {
			String rutaBase = Ruta2.get(request.getSession()
					.getServletContext(), rb
					.getString("estudiante.pathDefault"));
			// System.out.println("RUTA IMAGEN DEFAULT *  "+rutaBase +
			// rb.getString("estudiante.default"));
			File f = new File(rutaBase + rb.getString("estudiante.default"));
			if (!f.exists()) {
				return;
			}
			byte[] data = new byte[BUFFER_SIZE];
			fi = new FileInputStream(f);
			origin = new BufferedInputStream(fi, BUFFER_SIZE);
			ouputStream = response.getOutputStream();
			while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
				ouputStream.write(data, 0, count);
			}
			ouputStream.flush();
			ouputStream.close();
			origin.close();
			fi.close();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			try {
				if (ouputStream != null)
					ouputStream.close();
				if (origin != null)
					origin.close();
				if (fi != null)
					fi.close();
			} catch (Exception e) {
			}
		}
	}

	public String getFoto(ServletContext sc, long estudiante) {

		Connection con = null;
		String archivosalida = "";
		try {
			// con = cursor.getConnection();
			archivosalida = Ruta.get(sc, rb.getString("foto.pathSubir"));
			java.io.File f = new java.io.File(archivosalida);
			f = new java.io.File(archivosalida + estudiante + ".jpg");
			// System.out.println("DIBUJAR FOTO"+": CHECK EXIST ARCHIVO");
			//Buscar en el otro nodo
			if(!f.exists()) {
				saveStream("http://bladenodo4.redp.edu.co:7779/apoyo_escolar/recursos/imagen.jpg?tipo=1&param="+estudiante, archivosalida + estudiante + ".jpg");
			}
			//Verificar nuevamente
			f = new java.io.File(archivosalida + estudiante + ".jpg");
			if (f.exists()) {
				archivosalida = archivosalida + estudiante + ".jpg";
				// System.out.println("DIBUJAR FOTO"+": CHECK EXIST ARCHIVO SI ** ");
			} else {
				archivosalida = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			archivosalida = null;
		} finally {

		}
		return archivosalida;
	}
	
	public void saveStream( String mURL, String ofile ) throws Exception {
        InputStream in = null;
        FileOutputStream out = null;
        try {
            URL url = new URL(mURL);
            URLConnection urlConn = url.openConnection();
            in = urlConn.getInputStream();
            out = new FileOutputStream(ofile);
            int c;
            byte[] b = new byte[1024];
            while ((c = in.read(b)) != -1)
                out.write(b, 0, c);
        }catch(Exception e) {
        	
        } finally {
            if (in != null)
                in.close();
            if (out != null)
                out.close();
        }
    }

}
