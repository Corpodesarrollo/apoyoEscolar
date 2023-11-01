

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import siges.login.beans.Login;
import util.BitacoraCOM;

/**
 * Servlet implementation class GuardarBitacoraReporte
 */
public class GuardarBitacoraReporte extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GuardarBitacoraReporte() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String jsonString="";
		BitacoraCOM com = new BitacoraCOM();
		try{
			Login login = (Login)request.getSession().getAttribute("login");
//			login.setInstId("1");
//			login.setJornadaId("1");
//			login.setPerfil("180");
//			login.setSedeId("1");
//			login.setUsuarioId("21233646");
			String nombreArchivo = request.getParameter("archivo");
			Map<String, String> desc = new HashMap<String, String>();
			desc.put("Nombre del archivo", nombreArchivo);
			Gson gson = new Gson();
			jsonString = gson.toJson(desc);
			com.insertarBitacora(Long.valueOf(login.getInstId()), Integer.parseInt(login.getJornadaId()), 4, login.getPerfil(), Integer.parseInt(login.getSedeId()), 
					1002, 5/*eliminacion*/, login.getUsuarioId(), jsonString);	
		}catch(Exception e){
			
		}
		String newURL = response.encodeRedirectURL(request.getParameter("action"));
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		response.setHeader("Location", newURL);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
