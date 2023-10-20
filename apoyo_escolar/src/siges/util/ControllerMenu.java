package siges.util;

//	VERSION		FECHA		AUTOR			DESCRIPCION
//		1.0		17/03/2021	JORGE CAMACHO	Código espagueti

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.FilenameUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import siges.dao.Ruta;
import siges.login.beans.Login;
import siges.dao.DataSourceManager;
import siges.dao.OperacionesGenerales;


/**
 * Nombre: ControllerMenu<br>
 * Descripcinn: Controla el menu lateral de la pngina<br>
 * Funciones de la pngina: Envia al integrador lo que debe pintar el menu<br>
 * Fecha de modificacinn: 20/07/2005 <br>
 * 
 * @author Latined <br>
 * @version v 1.0 <br>
 */
public class ControllerMenu extends HttpServlet {
	
	private Integer cadena = new Integer(java.sql.Types.VARCHAR);
	private Integer entero = new Integer(java.sql.Types.INTEGER);

	/**
	 * Procesa la peticion HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String process(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		Statement ps = null;
		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement pst = null;
		
		HttpSession session = req.getSession();
		ResourceBundle rb = ResourceBundle.getBundle("common");
		String menu = getServletContext().getInitParameter("menu");
		String error = getServletContext().getInitParameter("error");
		
		int vigencia = 0;
		String view = null;
		String dane = null;
		String nombreArchivoEscudo = null;
		
		Collection list, list2, list3;
		
		try {
			
			Login login = (Login) session.getAttribute("login");
			int perf = login != null ? Integer.parseInt(login.getPerfil()) : -1;
			
			String userid  = login.getUsuarioId();
			String sede = login.getSedeId();
			String jornada = login.getJornadaId();
			
			long inst = (login != null && !login.getInstId().equals("")) ? Long.parseLong(login.getInstId()) : -1;
			
			cn = DataSourceManager.getConnection(1);
			
			// Escudo
			// Consultamos esta informacion para todos		
			if(login != null && login.getInstId() != null && !login.getInstId().equals("")) {
				
				ps = cn.createStatement();
				rs = ps.executeQuery("select inscoddane, insvigencia from institucion where inscodigo = " + login.getInstId());
				if (rs.next()) {
					dane = rs.getString(1);
					vigencia = rs.getInt(2);
				}
				rs.close();
				ps.close();
				
			}
			
			
			if (session.getAttribute("imagenEscudo") == null && login != null && !login.getInstId().equals("")) {	
				char separador = getSeparadorSO();
				String path = rb.getString("escudo.PathEscudoNew");
				System.out.println("///////////////path: "+path);
				String rutaArchivo = path.replace('.', separador) + separador;
				System.out.println("//////////////rutaArchivo: "+rutaArchivo);
				
					
				
				ps = cn.createStatement();
				rs = ps.executeQuery("select NOMBRE_ARCHIVO from LOG_ESCUDO_INSTITUCION where INSTITUCION_ID = " + login.getInstId());
				if (rs.next()) {
					nombreArchivoEscudo = rs.getString(1);
				}
				rs.close();
				ps.close();
				System.out.println("nombreArchivoEscudo: "+nombreArchivoEscudo);
				System.out.println("login.getInstId(): "+login.getInstId());
				System.out.println("dane: "+dane);
				
				
				File f = null;
				f = new File(rutaArchivo + nombreArchivoEscudo);
				System.out.println("rutaArchivo + nombreArchivoEscudo: "+rutaArchivo + nombreArchivoEscudo);
				
				boolean band = false;
				String extension = FilenameUtils.getExtension(nombreArchivoEscudo);
				if (f.exists()) {
					System.out.println("a");
					String Base64ImgA = ImgToBase64(f);
					session.setAttribute("imagenEscudo", rutaArchivo + nombreArchivoEscudo);
					session.setAttribute("imagenEscudoBase64", Base64ImgA);
					session.setAttribute("extensionEscudo", extension);
					System.out.println("extension: "+extension);
					session.setAttribute("tipo", extension);
					f = null;
					band = true;
				}
				
				if (!band) {
					System.out.println("b");
					session.setAttribute("imagenEscudo", null);
					session.setAttribute("imagenEscudoBase64", null);
				}
			}
			
			// Poner las categorias
			String sql = rb.getString("categoria");
			pst = cn.prepareStatement(sql);
			pst.setInt(1, perf);
			pst.setLong(2, inst);
			rs = pst.executeQuery();
			
			int m = rs.getMetaData().getColumnCount();
			list = new ArrayList();
			Object[] o;
			int i = 0, f = 0;
			
			while (rs.next()) {
				f++;
				o = new Object[m];
				for (i = 1; i <= m; i++)
					o[i - 1] = rs.getString(i);
				
				list.add(o);
			}
			rs.close();
			pst.close();
			session.setAttribute("mnucat", list);

			// Poner menu publico
			sql = rb.getString("menuPublico");
			pst = cn.prepareStatement(sql);
			pst.setLong(1, inst);
			rs = pst.executeQuery();
			
			m = rs.getMetaData().getColumnCount();
			list2 = new ArrayList();
			i = 0;
			f = 0;
			
			while (rs.next()) {
				f++;
				o = new Object[m];
				for (i = 1; i <= m; i++)
					o[i - 1] = rs.getString(i);
				list2.add(o);
			}
			rs.close();
			pst.close();
			session.setAttribute("mnuPublico", list2);

			// Poner menu privado
			sql = rb.getString("menuPrivado");
			pst = cn.prepareStatement(sql);
			pst.setInt(1, perf);
			pst.setLong(2, inst);
			rs = pst.executeQuery();
			
			m = rs.getMetaData().getColumnCount();
			list3 = new ArrayList();
			i = 0;
			f = 0;
			
			while (rs.next()) {
				f++;
				o = new Object[m];
				for (i = 1; i <= m; i++)
					o[i - 1] = rs.getString(i);
				
				String servicio = o[0].toString().toLowerCase();
				
				if(servicio.startsWith("http://") || servicio.startsWith("https://")){
					/**
					 * Modificado por Christiam Hernandez 
					 * 28/02/2013
					 * los (&) estaban llegando con caracteres especiales.
					 * 
					 *  original
					 *  o[0] = o[0] + "?login=" + userid + "&inst=" + inst + "&sede=" + sede + "&jornada=" + jornada;
					 *  modificacion
					 *  
					 */
					int nivel = Integer.parseInt(login.getNivel());
					if(servicio.contains("apoyoescolarbe")){
						switch (nivel) {
						case 0:
							o[0] = o[0] + "?inst=0";
							break;
						case 2:
							o[0] = o[0] + "?inst=0";
							break;
						default:
							//o[0] = o[0] + "inst="+ (inst > 0 ? inst : 0 ) + "&vigencia="+vigencia ;
							o[0] = o[0] + "?inst="+ (inst > 0 ? inst : 0 ) + "&usuario="+ login.getUsuarioId()+ "&vigencia="+vigencia+"&sede="+sede+"&jornada="+jornada ;
							break;
						}
						//ApoyoEscolarBE/#/PrincipalComponent?opt=porcentajeAsignatura&amp;usuario=1230010&amp;vigencia=2017&inst=614 bien
					}else if(servicio.contains("apex")){
						// En el caso de apex es para la homologacion de asignaturas en este caso solo se debe pasar la insttitucion y a igencia separados por ,
						o[0] = o[0] + "" +inst+","+vigencia;
					}
					else if(servicio.endsWith("students")){
						// En el caso de students es para el observador de estudiante y se le envia los datos 
						//    separados por (/) usuarioId, institucionId, sedeId,  y jornadaId
						o[0] = o[0] + "/index/"+ userid + "/" + (inst > 0 ? inst : 0 ) + "/" + sede+ "/" + jornada;
					}
					else if(servicio.endsWith("administrative/photo/")){
						// En el caso de students es para el observador de estudiante y se le envia los datos 
						//    separados por (/) usuarioId, institucionId, sedeId,  y jornadaId
						o[0] = o[0] + userid + "/" + (inst > 0 ? inst : 0 ) + "/" + sede+ "/" + jornada;
					}
					else if(servicio.contains("schools/photo")){
						// En el caso de students es para el observador de estudiante y se le envia los datos 
						//    separados por (/) usuarioId, institucionId, sedeId,  y jornadaId
						o[0] = o[0] + userid + "/" + (inst > 0 ? inst : 0 ) + "/" + sede+ "/" + jornada;
					}
					else if(servicio.contains("students/observersearch")){
						// En el caso de students/observersearch es para la consulta general de observador de estudiante y se le envia los datos 
						//    separados por (/) usuarioId, institucionId, sedeId,  y jornadaId
						o[0] = o[0] + "/"+ userid + "/" + (inst > 0 ? inst : 0 ) + "/" + sede+ "/" + jornada;
					}else{
						switch (nivel) {
						case 0:
							o[0] = o[0] + "?var=central";
							break;
						case 2:
							o[0] = o[0] + "?var=" + login.getMunId();
							break;
						default:
							o[0] = o[0] + "?var="+ userid + "-" + (inst > 0 ? inst : 0 ) + "-" + (String.valueOf(sede).equals("")  ? "0" : String.valueOf(sede)) + "-" + (String.valueOf(jornada).equals("")  ? "0" : String.valueOf(jornada));
							break;
						}						
					}
					
					if(!servicio.contains("apex")) {
						//o[0] = o[0]+ "&usuario=" + login.getUsuarioId();
					}

					System.out.println(o[0]);
				}
				list3.add(o);
			}
			rs.close();
			pst.close();
			session.setAttribute("mnuPrivado", list3);

			// Menu publico Param
			pst = cn.prepareStatement(rb.getString("MenuPublicoParam"));
			rs = pst.executeQuery();
			
			m = rs.getMetaData().getColumnCount();
			list = new ArrayList();
			i = f = 0;
			
			while (rs.next()) {
				f++;
				o = new Object[m];
				for (i = 1; i <= m; i++)
					o[i - 1] = rs.getString(i);
				list.add(o);
			}
			rs.close();
			pst.close();
			
			// Menu privado param
			pst = cn.prepareStatement(rb.getString("MenuPrivadoParam"));
			pst.setInt(1, perf);
			rs = pst.executeQuery();
			
			m = rs.getMetaData().getColumnCount();
			i = f = 0;
			
			while (rs.next()) {
				f++;
				o = new Object[m];
				for (i = 1; i <= m; i++)
					o[i - 1] = rs.getString(i);
				list.add(o);
			}
			session.setAttribute("menuParam", list);
			
			// menu privado param
			view = menu;
			
			return view;
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("MENU ERROR: " + e);
			req.setAttribute("mensaje", e.getMessage());
			return error;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		
	}
	
	
	
	
	
	/**
	 * Recibe la peticion por el metodo get de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}
	
	
	
	
	
	/**
	 * Recibe la peticion por el metodo Post de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String s = process(req, res);
		
		if (s != null && !s.equals(""))
			ir(2, s, req, res);
		
	}
	
	
	
	
	
	/**
	 * recibe el url de destino, el request y el response y manda el control a
	 * la pagina indicada
	 * 
	 * @param: int a
	 * @param: String s
	 * @param: HttpServletRequest request
	 * @param: HttpServletResponse response
	 */
	public void ir(int a, String s, HttpServletRequest request,	HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher(s);
		
		if (a == 1)
			rd.include(request, response);
		else
			rd.forward(request, response);
		
	}
	
	public static String ImgToBase64(File img){
	    String encodedBase64 = null;
	    try {   
	        FileInputStream fileInputStreamReader = new FileInputStream(img);
	        byte[] bytes = new byte[(int)img.length()];
	        fileInputStreamReader.read(bytes);
	        encodedBase64 = new String( DatatypeConverter.printBase64Binary(bytes) );
	      
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return encodedBase64;
	}
	
	public static char getSeparadorSO() {

		String sistemaOperativo = System.getProperty("os.name");
		char result = '\\';

		if (sistemaOperativo.startsWith("Windows")) {
			result = '\\';
        } else if (sistemaOperativo.startsWith("Linux")) {
        	result = '/';
        } else if (sistemaOperativo.startsWith("Mac")) {
        	result = '/';
        } else {
            System.err.println("Sistema operativo no compatible");
        }

		return result;
	}
}