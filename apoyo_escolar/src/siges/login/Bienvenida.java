package siges.login;

import java.io.File;
import java.io.IOException;
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

import org.apache.commons.validator.GenericValidator;

import siges.dao.Cursor;
import siges.dao.Ruta;
import siges.login.beans.AlertaVO;
import siges.login.beans.Login;
import siges.login.beans.MensajesVO;
import siges.login.dao.LoginDAO;
import siges.login.beans.ParamsVO;

/**
 * Nombre: <BR>
 * Descripcinn: <BR>
 * Funciones de la pngina: <BR>
 * Entidades afectadas: <BR>
 * Fecha de modificacinn: 20/07/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */

public class Bienvenida extends HttpServlet {
	private Cursor cursor;// objeto que maneja las sentencias sql
	private Login usuVO;
	private ResourceBundle rb;
	private LoginDAO loginDAO = new LoginDAO(new Cursor());
	private static String FICHA_BIENVENIDAD = "/bienvenida.jsp";
	private static String FICHA_ALERTA = "/login/alerta.jsp";
	private static String FICHA_MSJ = "/login/alerMesaje.jsp";
	private String FICHA;
	
	public String process(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		HttpSession session;
		Login login;
		String mensaje = "";
		ArrayList listaalarmas=null;

		try {
			session = req.getSession(false);
			rb = ResourceBundle.getBundle("batch");
			usuVO = (Login) session.getAttribute("login");
			int TIPO = 0;
			String str = req.getParameter("tipos_");
			// System.out.println("str " + str);
			if (GenericValidator.isInt(str)) {
				TIPO = Integer.parseInt(str);
			}
			// System.out.println(this.getClass()+"  TIPO " + TIPO);
			

			
			
			switch (TIPO) {
			case ParamsVO.TIPO_ALERTA:
				cargarListadoMsj(req, usuVO);
				FICHA = FICHA_ALERTA;
				break;
			case ParamsVO.TIPO_MENSAJE:
				cargarMsj(req, usuVO);
				FICHA = FICHA_MSJ;
				break;
			default:
				if(listaalarmas==null && session.getAttribute("mostrarpopups")==null){
					listaalarmas=listadeAlertasxdocente(usuVO);
					if(listaalarmas!=null){
						for (Iterator it=listaalarmas.iterator(); it.hasNext(); ) {
							Object[] ele =  (Object[]) it.next();
							
							if(ele[0]!=null){
								session.setAttribute("listalarmas",listaalarmas);
								session.setAttribute("mostrarpopups","si");
							}
						break;
					}
						}
			}
		
				FICHA = FICHA_BIENVENIDAD;
			}

			// System.out.println("FICHA " + FICHA);
			return FICHA;

			// req.setAttribute("mensaje","Este es el pais de las maravilas");
			/*
			 * if (asignarBeans(req)) { login = (Login)
			 * session.getAttribute("login"); if (!login.getSedeId().equals("")
			 * && !login.getJornadaId().equals("")) { String path =
			 * rb.getString("path.raiz"); String pathAbsoluto =
			 * getServletContext().getRealPath("/");// path // del // origen //
			 * de // archivos String pathTotal; String pathRelativoTotal;
			 * pathTotal = path + "." + login.getInstId() + "." +
			 * login.getSedeId() + "." + login.getJornadaId(); pathTotal =
			 * Ruta.get(pathAbsoluto, pathTotal); pathRelativoTotal = path + "."
			 * + login.getInstId() + "." + login.getSedeId() + "." +
			 * login.getJornadaId(); pathRelativoTotal =
			 * Ruta.getRelativo(pathAbsoluto, pathRelativoTotal); File f = new
			 * File(pathTotal); String nuevoNombre = null; if (f.exists()) {
			 * String[] cont = f.list(); if (cont != null) { Collection list =
			 * new ArrayList(); Object[] o; for (int i = 0; i < cont.length;
			 * i++) { o = new Object[3]; nuevoNombre = getNombre(cont[i]); if
			 * (nuevoNombre != null) { o[0] = nuevoNombre; o[1] =
			 * pathRelativoTotal + cont[i]; o[2] = "zip"; list.add(o); } } //
			 * setMensaje("Los reportes a continuacinn son // generados el dia
			 * inmediatamente anterior en la // noche, \n Favor descargue el
			 * archivo de la // metodologia, grado y evaluacion que va a //
			 * utilizar. \nEsto es con el fin de facilitar a los // docentes la
			 * labor de generear plantillas."); //
			 * req.setAttribute("mensaje",mensaje); req.setAttribute("reporte",
			 * "1"); req.setAttribute("listarepo", list); } } } }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error en bienvenida:" + e);
		}
		return "/bienvenida.jsp";
	}

	private void cargarListadoMsj(HttpServletRequest req, Login usuVO)
			throws ServletException {
		String perfil = usuVO.getPerfil();
		String local;
		String inst;
		String sede;
		String jord;
		List listaMensajeVO = new ArrayList();

		try {
			/*
			 * if(usuVO.getLocId() != null &&
			 * GenericValidator.isInt(usuVO.getLocId())){ local =
			 * usuVO.getLocId(); }else{ local ="-99"; }
			 */

			if (usuVO.getMunId() != null
					&& GenericValidator.isInt(usuVO.getMunId())) {
				local = usuVO.getMunId();
			} else {
				local = "-99";
			}

			if (usuVO.getInstId() != null
					&& GenericValidator.isInt(usuVO.getInstId())) {
				inst = usuVO.getInstId();
			} else {
				inst = "-99";
			}

			if (usuVO.getSedeId() != null
					&& GenericValidator.isInt(usuVO.getSedeId())) {
				sede = usuVO.getSedeId();
			} else {
				sede = "-99";
			}

			if (usuVO.getJornadaId() != null
					&& GenericValidator.isInt(usuVO.getJornadaId())) {
				jord = usuVO.getJornadaId();
			} else {
				jord = "-99";
			}

			listaMensajeVO = loginDAO.getListaMensajes(perfil, local, inst,
					sede, jord);
		} catch (Exception e) {
			e.printStackTrace();
		}
		req.getSession().setAttribute("listaMensajeVO", listaMensajeVO);
	}

	private void cargarMsj(HttpServletRequest req, Login usuVO)
			throws ServletException {
		String id = req.getParameter("id");
		try {
			if (GenericValidator.isInt(id)) {
				MensajesVO mensajesVO = loginDAO.getMensaje(Integer
						.parseInt(id));
				req.getSession().setAttribute("mensajesVO", mensajesVO);
			}

		} catch (Exception e) {
			e.printStackTrace();
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
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
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
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String s = process(req, res);
		String tipo = req.getParameter("redireccion");
		if (s != null && !s.equals("")) {
			if (tipo != null && tipo.equals("2")) {
				ir(2, s, req, res);
			} else {
				ir(1, s, req, res);
			}
		}
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
	public void ir(int a, String s, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (cursor != null)
			cursor.cerrar();
		RequestDispatcher rd = getServletContext().getRequestDispatcher(s);
		if (a == 1)
			rd.include(request, response);
		else
			rd.forward(request, response);
	}

	public boolean asignarBeans(HttpServletRequest request)
			throws ServletException, IOException {
		if ((Login) request.getSession().getAttribute("login") == null)
			return false;
		return true;
	}

	public String getNombre(String s) {
		String nom = "";
		String[] algo = s.split("_");
		if (algo == null || algo.length < 2)
			return null;
		int nn = algo[0].indexOf("[");
		if (nn == -1)
			return null;
		String tit = s.substring(0, nn);
		if (tit.equals("Logro"))
			nom += "Evaluacinn de logros ";
		if (tit.equals("Asignatura"))
			nom += "Evaluacinn de Asignatura ";
		if (tit.equals("Area"))
			nom += "Evaluacinn de Area ";
		if (tit.equals("Descriptor"))
			nom += "Asignacinn de descriptores ";
		if (tit.equals("Preescolar"))
			nom += "Evaluacinn de preescolar ";
		nom += ". Metodologia " + algo[2];
		nom += ". Grado " + (algo[4]);
		nom += ". Periodo " + (algo[6].substring(0, algo[6].indexOf(".")));
		return nom;
	}
	
	/**
	 * Carga listado de alarmas por docente en session
	 * 
	 * @param request
	 * @param login
	 * @return
	 */
	public ArrayList listadeAlertasxdocente(Login login){
		ArrayList listtmp = new ArrayList();
		int eliminado=0;
		try {
			Collection areasyasig=loginDAO.listaAreasyAsigxDocente(login);
			for(Iterator it=areasyasig.iterator(); it.hasNext();){
				String[] ele =  (String[]) it.next();
				Object[] elemento=new Object[3];
				//asignatura
				elemento[0]=ele[0];
				//area
				elemento[1]=ele[1];
				//lista alarmas
				elemento[2]=loginDAO.listadeAlertasxdocente(login,ele[0],ele[1]);
				listtmp.add(elemento);	
			}
			
			if(listtmp!=null){
				if(listtmp.size()>0){
					eliminado=loginDAO.limpiarregistrodealarmas(login);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listtmp;
	}
}
