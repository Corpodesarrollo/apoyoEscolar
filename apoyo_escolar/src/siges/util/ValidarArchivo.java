package siges.util;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import siges.dao.Ruta;

/**
 * Servlet implementation class ValidarArchivo
 */
public class ValidarArchivo extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private String fileSystem;
	ServletContext context = null;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		context = config.getServletContext();
		fileSystem = Ruta.get(context, "");
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Obtén la ruta del archivo que deseas verificar desde un parámetro de solicitud		
        String rutaArchivo = request.getParameter("filepath");

        // Realiza la validación para verificar si el archivo existe
        boolean archivoExiste = validarExistenciaArchivo(rutaArchivo);

        // Crea una respuesta JSON con el resultado de la validación
        String jsonResponse = "{\"archivoExiste\": " + archivoExiste + "}";
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
	}
	
	private boolean validarExistenciaArchivo(String rutaArchivo) {
        // Utiliza la clase java.io.File para verificar si el archivo existe en la ruta proporcionada
        File archivo = new File(fileSystem + rutaArchivo.replace("/", System.getProperty("file.separator")));
        return archivo.exists();
    }

}
