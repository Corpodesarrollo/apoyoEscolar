package siges.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Base64;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import siges.dao.Ruta;
import siges.exceptions.InternalErrorException;
import siges.login.beans.Login;

public class Descargar extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ResourceBundle rPath, rb3;
	private Login login;
	private Cursor cursor;

	private String fileSystem;
	ServletContext context = null;

	@Override
	public void init(ServletConfig config) throws ServletException {
		context = config.getServletContext();
		rPath = ResourceBundle.getBundle("path");
		fileSystem = Ruta.get(context, "");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Ruta del archivo a cargar desde el sistema de archivos
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		String filePath = request.getParameter("filepath");
		String mensaje = "¡El archivo no existe o no está disponible para su descarga.!";
		
		login=(Login)session.getAttribute("login");
		rb3 = ResourceBundle.getBundle("siges.boletines.bundle.boletines");
		
		cursor = new Cursor();

		try {
			String base64Content = convertFileToBase64(fileSystem + filePath); //Base64
			// Verifica si el contenido Base64 existe
			if (!base64Content.equals("NOK")) {
				File fileTmp = new File(fileSystem + filePath);
				// Configura los encabezados de respuesta para la descarga del archivo
				response.setHeader("Content-Disposition", "attachment; filename=" + fileTmp.getName());
				response.setContentType("application/octet-stream");

				// Decodifica el contenido Base64
				byte[] fileBytes = Base64.getDecoder().decode(base64Content);

				// Escribe el contenido en la respuesta
				OutputStream outStream = response.getOutputStream();
				outStream.write(fileBytes);
				outStream.flush();
				outStream.close();
				request.setAttribute("errorDescarga", mensaje);
			} else {
				// El archivo está vacío o no se encuentra, envía una respuesta HTML con un cuadro de diálogo de alerta
//	            String mensajeError = "El archivo está vacío o no se encuentra.";
//	            String respuestaHTML = "<html><head><title>Error</title></head><body>";
//	            respuestaHTML += "<script>alert(<c:out value=\"${requestScope.errorDescarga}\"/>);</script>";
//	            respuestaHTML += "</body></html>";
//
//	            response.setContentType("text/html");
//	            response.getWriter().write(respuestaHTML);
				//File fileDownload = new File(fileSystem + filePath);
				request.setAttribute("errorDescarga", mensaje);
				//ponerReporteMensaje("2", "3", login.getUsuarioId(), rb3.getString("boletines.PathBoletines") + fileDownload.getName() + "", "zip", "" + fileDownload.getName(), "ReporteActualizarBoletin", "Ocurrio excepción en el Hilo");
			}
		} catch (Exception e) {
			request.setAttribute("errorDescarga", mensaje);
		}
	}	

	public static String convertFileToBase64(String filePath) throws IOException {
		File file = new File(filePath);

		// Verifica si el archivo existe
		if (!file.exists()) {
			return new String("NOK");
		}

		// Lee el contenido del archivo en un arreglo de bytes
		byte[] fileBytes = new byte[(int) file.length()];
		try (FileInputStream fileInputStream = new FileInputStream(file)) {
			fileInputStream.read(fileBytes);
		} catch (IOException e) {
			throw new IOException("Error al leer el archivo.", e);
		}

		// Codifica el arreglo de bytes en Base64
		byte[] base64Bytes = Base64.getEncoder().encode(fileBytes);

		// Convierte el arreglo de bytes en una cadena Base64
		return new String(base64Bytes);
	}

	public void ponerReporteMensaje(String estado, String modulo, String us,
			String rec, String tipo, String nombre, String prepared,
			String mensaje) {

		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3.getString(prepared));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (estado));
			pst.setString(posicion++, (mensaje));
			pst.setString(posicion++, (us));
			pst.setString(posicion++, (rec));
			pst.setString(posicion++, (tipo));
			pst.setString(posicion++, (nombre));
			pst.setString(posicion++, (modulo));
			pst.executeUpdate();
			pst.close();
			con.commit();
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}
}
