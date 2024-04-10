package siges.util;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import siges.dao.Ruta;
import siges.login.beans.Login;
import util.BitacoraCOM;
import util.LogDescargaArchivos;
import util.LogEstudianteDto;

/**
 * Servlet implementation class ValidarArchivo
 */
public class ValidarArchivo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BitacoraCOM bitacoraCOM;
    
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
        int tipo = Integer.parseInt(request.getParameter("tipo"));
        String archivo = request.getParameter("archivo");
        
        int modulo = 0;
        int submodulo = 0;
        switch (tipo) {
			case 1:
				submodulo = 3306;
				modulo = 3;
				break;
				
			case 3:
				submodulo = 854;
				modulo = 4;
				break;
				
			case 4:
				submodulo = 853;
				modulo = 4;
				break;
				
			case 7:
				submodulo = 855;
				modulo = 4;
				break;
				
			case 8:
				submodulo = 990;
				modulo = 4;
				break;
				
			case 9:
				submodulo = 61;
				modulo = 3;
				break;
				
			case 20:
				submodulo = 1050;
				modulo = 4;
				break;
				
			case 26:
				submodulo = 2230;
				modulo = 4;
				break;
				
			case 27:
				submodulo = 2210;
				modulo = 4;
				break;
				
			case 45:
				submodulo = 555553;
				modulo = 4;
				break;
				
			case 48:
				submodulo = 112608;
				modulo = 4;
				break;
				
			case 52:
				submodulo = 1950;
				modulo = 4;
				break;
				
			case 55:
				submodulo = 1960;
				modulo = 4;
				break;
				
			case 60:
				submodulo = 2210;
				modulo = 4;
				break;
				
			case 114:
				submodulo = 701;
				modulo = 3;
				break;
				
			default:
				submodulo = 128;
				modulo = 0;
				break;
		}
        

        // Realiza la validación para verificar si el archivo existe
        boolean archivoExiste = validarExistenciaArchivo(rutaArchivo);
        
        if (archivoExiste) {
        	bitacoraCOM = new BitacoraCOM();
        	HttpSession session = request.getSession();
        	String loginBitacora = (String)session.getAttribute("loginBitacora");
        	Login login = (Login)session.getAttribute("login");
        	LogDescargaArchivos log = new LogDescargaArchivos();
			log.setArchivo(archivo);
			bitacoraCOM.insertarBitacora(
				Long.parseLong(login.getInstId()), 
				Integer.parseInt(login.getJornadaId()),
				modulo,
				login.getPerfil(), 
				Integer.parseInt(login.getSedeId()), 
				submodulo, 
				6, 
				loginBitacora, 
				new Gson().toJson(log)
			);
		}

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
