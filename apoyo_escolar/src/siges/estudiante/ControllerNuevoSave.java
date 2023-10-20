package siges.estudiante;


/**
 *	VERSION		FECHA			AUTOR				DESCRIPCION
 *		1.0		10/05/2021		JORGE CAMACHO		Código Espagueti
 *
*/

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import siges.dao.Cursor;
import siges.login.beans.Login;
import siges.estudiante.beans.Salud;
import siges.estudiante.beans.Basica;
import siges.estudiante.beans.Familiar;
import siges.estudiante.beans.AcademicaVO;
import siges.estudiante.beans.Convivencia;
import siges.estudiante.dao.EstudianteDAO;
import siges.estudiante.beans.AsistenciaVO;
import siges.estudiante.beans.AtencionEspecial;


/**
 * Nombre: ControllerNuevoSave<BR>
 * Descripcinn: Controla la negociacion de edicion de registros o insercion de
 * registros de informacion del estudiante <BR>
 * Funciones de la pngina: Guardar el registo o actualizarlo en la base de datos
 * y redirigir el control a la vista de formulario <BR>
 * Entidades afectadas: Todas las tablas del estudiante <BR>
 * Fecha de modificacinn: 20/07/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */
public class ControllerNuevoSave extends HttpServlet {
	
	private String er;						// nombre de la pagina a la que ira si hay errores
	private String sig;						// nombre de la pagina a la que ira despues de ejecutar los comandos de esta
	private String ant;						// pagina a la que ira en caso de que no se pueda procesar esta pagina
	private String home;					// nombre de la pagina a la que ira si hay errores
	private boolean err;					// variable que indica si hay o no errores en la validacion de los datos del formulario
	private boolean band;
	private Cursor cursor;					// objeto que maneja las sentencias sql
	private String mensaje;					// mensaje en caso de error
	private EstudianteDAO estudianteDAO;
	
	
	private static final int TIPO_BASICA = 1;
	private static final int TIPO_FAMILIAR = 2;
	private static final int TIPO_SALUD = 3;
	private static final int TIPO_CONVIVENCIA = 4;
	private static final int TIPO_ACADEMICA = 5;
	private static final int TIPO_ASISTENCIA = 6;
	private static final int TIPO_ATENCION = 7;
	
	
	/**
	 * Recibe la peticion por el metodo Post de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/
	public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Login login = null;
		
		Salud salud = null, salud2 = null;
		Familiar familiar, familiar2 = null;
		Basica basica = null, basica2 = null;
		AtencionEspecial atencion = null, atencion2 = null;
		Convivencia convivencia = null, convivencia2 = null;
		AcademicaVO academicaVO = null, academicaVO2 = null;
		AsistenciaVO asistenciaVO = null, asistenciaVO2 = null;
		
		int tipo;
		String boton;
		String buscar = null;
		String respuesta = "";
		
		er = "/error.jsp";
		home = "/bienvenida.jsp";
		sig = "/estudiante/NuevoResultado.jsp";
		ant = "/estudiante/ControllerNuevoEdit.do";
		
		err = false;
		band = true;
		mensaje = null;
		respuesta = "La información fue ingresada satisfactoriamente";
		
		cursor = new Cursor();
		
		try {
			
			estudianteDAO = new EstudianteDAO(cursor);
			boton = (request.getParameter("cmd") != null) ? request.getParameter("cmd") : new String("Cancelar");
			
			if (boton.equals("Cancelar")) {
				borrarBeans(request);
				return home;
			}
			
			if (boton.equals("Guardar")) {
				
				if (request.getParameter("tipo") == null || request.getParameter("tipo").equals("")) {
					setMensaje("Acceso denegado no hay una ficha definida");
					request.setAttribute("mensaje", getMensaje());
					return er;
				}
				
				tipo = Integer.parseInt((String) request.getParameter("tipo"));

				// Asignar beans
				login = (Login) request.getSession().getAttribute("login");
				basica = (Basica) request.getSession().getAttribute("nuevoBasica");
				familiar = (Familiar) request.getSession().getAttribute("nuevoFamiliar");
				salud = (Salud) request.getSession().getAttribute("nuevoSalud");
				convivencia = (Convivencia) request.getSession().getAttribute("nuevoConvivencia");
				academicaVO = (AcademicaVO) request.getSession().getAttribute("academicaVO");
				asistenciaVO = (AsistenciaVO) request.getSession().getAttribute("asistenciaVO");
				atencion = (AtencionEspecial) request.getSession().getAttribute("nuevoAtencion");
				
				if (basica.getEstado().equals("1") || basica.getEstado().equals("2"))
					basica2 = (Basica) request.getSession().getAttribute("nuevoBasica2");
				
				if (familiar.getEstado().equals("1"))
					familiar2 = (Familiar) request.getSession().getAttribute("nuevoFamiliar2");
				
				if (salud.getSalestado().equals("1"))
					salud2 = (Salud) request.getSession().getAttribute("nuevoSalud2");
				
				if (convivencia.getEstado().equals("1"))
					convivencia2 = (Convivencia) request.getSession().getAttribute("nuevoConvivencia2");
				
				if (academicaVO.getAcaEstado().equals("1"))
					academicaVO2 = (AcademicaVO) request.getSession().getAttribute("academicaVO2");
				
				if (asistenciaVO.getAsiEstado().equals("1"))
					asistenciaVO2 = (AsistenciaVO) request.getSession().getAttribute("asistenciaVO2");
				
				if (atencion.getEstado().equals("1"))
					atencion2 = (AtencionEspecial) request.getSession().getAttribute("nuevoAtencion2");
				
				// Asignar beans
				switch (tipo) {
				case TIPO_BASICA:
					
					if (basica.getEstado().equals("")) {
						insertarRegistroBasica(request, basica, login);
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					
					if (basica.getEstado().equals("1")) {
						actualizarRegistroBasica(request, basica, basica2, login);
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					
				case TIPO_FAMILIAR:
					
					if (familiar.getEstado().equals("")) {
						insertarRegistroFamiliar(request, familiar, login);
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					
					if (familiar.getEstado().equals("1")) {
						actualizarRegistroFamiliar(request, familiar, familiar2);
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					break;
					
				case TIPO_SALUD:
					
					if (basica2 == null || basica2.getEstcodigo().equals("")) {
						setMensaje("No se puede guardar la información de salud\nsi no se ha registrado un estudiante en la ficha\nde información básica");
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					
					if (salud.getSalestado().equals("")) {
						insertarRegistroSalud(request, salud, basica2);
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					
					if (salud.getSalestado().equals("1")) {
						actualizarRegistroSalud(request, salud, salud2, basica2);
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					break;
					
				case TIPO_CONVIVENCIA:
					
					if (basica2 == null || basica2.getEstcodigo().equals("")) {
						setMensaje("No se puede guardar la información de convivencia\nsi no se ha registrado un estudiante en la ficha\nde información básica");
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					
					if (convivencia.getEstado().equals("")) {
						insertarRegistroConvivencia(request, convivencia, login, basica2);
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					
					if (convivencia.getEstado().equals("1")) {
						actualizarRegistroConvivencia(request, convivencia,	convivencia2);
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					break;
					
				case TIPO_ACADEMICA:
					
					if (basica2 == null || basica2.getEstcodigo().equals("")) {
						setMensaje("No se puede guardar la información\nsi no se ha registrado un estudiante en la ficha\nde información básica");
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					
					if (academicaVO.getAcaEstado().equals("")) {
						insertarRegistroAcademica(request, academicaVO, basica2);
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					
					if (academicaVO.getAcaEstado().equals("1")) {
						actualizarRegistroAcademica(request, academicaVO, academicaVO2);
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					break;
					
				case TIPO_ASISTENCIA:
					
					if (basica2 == null || basica2.getEstcodigo().equals("")) {
						setMensaje("No se puede guardar la información\nsi no se ha registrado un estudiante en la ficha\nde información básica");
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					
					if (asistenciaVO.getAsiEstado().equals("")) {
						insertarRegistroAsistencia(request, asistenciaVO, basica2);
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					
					if (asistenciaVO.getAsiEstado().equals("1")) {
						actualizarRegistroAsistencia(request, asistenciaVO,	asistenciaVO2);
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					break;
					
				case TIPO_ATENCION:
					
					if (basica2 == null || basica2.getEstcodigo().equals("")) {
						setMensaje("No se puede guardar la información de atención especial\nsi no se ha registrado un estudiante en la ficha\nde información básica");
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					
					if (atencion.getEstado().equals("")) {
						insertarRegistroAtencion(request, atencion, login, basica2);
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					
					if (atencion.getEstado().equals("1")) {
						atencion.setUsuario(login.getUsuarioId());
						actualizarRegistroAtencion(request, atencion, atencion2);
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
					break;
					
				}
				
			}
			
			return er;
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error " + this + ":" + e.toString());
			throw new ServletException(e);
		} finally {
			if (cursor != null)
				cursor.cerrar();
			if (estudianteDAO != null)
				estudianteDAO.cerrar();
		}
		
	}
	
	
	
	
	
	public void actualizarRegistroAcademica(HttpServletRequest request,
			AcademicaVO academicaVO, AcademicaVO academicaVO2)
			throws ServletException, IOException {
		if (compararFichasAcademica(academicaVO, academicaVO2)) {
			setMensaje("La ficha seleccionada para guardar cambios no ha sido modificada");
			request.getSession().removeAttribute("academicaVO");
			request.getSession().removeAttribute("academicaVO2");
			return;
		}
		if (!estudianteDAO.actualizar(academicaVO)) {
			setMensaje(estudianteDAO.getMensaje());
			restaurarBeansAcademica(request, academicaVO2);
			return;
		}
		setMensaje("La información fue actualizada satisfactoriamente");
		request.getSession().removeAttribute("academicaVO");
		request.getSession().removeAttribute("academicaVO2");
	}

	public void actualizarRegistroAsistencia(HttpServletRequest request,
			AsistenciaVO asistenciaVO, AsistenciaVO asistenciaVO2)
			throws ServletException, IOException {
		if (compararFichasAsistencia(asistenciaVO, asistenciaVO2)) {
			setMensaje("La ficha seleccionada para guardar cambios no ha sido modificada");
			request.getSession().removeAttribute("asistenciaVO");
			request.getSession().removeAttribute("asistenciaVO2");
			return;
		}
		if (!estudianteDAO.actualizar(asistenciaVO)) {
			setMensaje(estudianteDAO.getMensaje());
			restaurarBeansAsistencia(request, asistenciaVO2);
			return;
		}
		setMensaje("La información fue actualizada satisfactoriamente");
		request.getSession().removeAttribute("asistenciaVO");
		request.getSession().removeAttribute("asistenciaVO2");
	}

	public void insertarRegistroAcademica(HttpServletRequest request,
			AcademicaVO academicaVO, Basica basica2) throws ServletException,
			IOException {
		academicaVO.setAcaEstudiante(basica2.getEstcodigo());
		if (!estudianteDAO.insertar(academicaVO)) {
			setMensaje(estudianteDAO.getMensaje());
			return;
		}
		request.getSession().removeAttribute("academicaVO");
		request.getSession().removeAttribute("academicaVO2");
		setMensaje("La información fue ingresada satisfactoriamente");
	}

	public void insertarRegistroAsistencia(HttpServletRequest request,
			AsistenciaVO asistenciaVO, Basica basica2) throws ServletException,
			IOException {
		asistenciaVO.setAsiTipoPer("1");
		asistenciaVO.setAsiCodPer(basica2.getEstcodigo());
		if (!estudianteDAO.insertar(asistenciaVO)) {
			setMensaje(estudianteDAO.getMensaje());
			return;
		}
		request.getSession().removeAttribute("asistenciaVO");
		request.getSession().removeAttribute("asistenciaVO2");
		setMensaje("La información fue ingresada satisfactoriamente");
	}

	public void insertarRegistroConvivencia(HttpServletRequest request,
			Convivencia convivencia, Login login, Basica basica2)
			throws ServletException, IOException {
		// System.out.println(" ** insertarRegistroConvivencia ** ");
		String resultado;
		String[] resul;
		convivencia.setConcodinst(login.getInstId());
		convivencia.setContipoperso("1");
		convivencia.setConcodperso(basica2.getEstcodigo());
		if (!estudianteDAO.insertar(convivencia)) {
			setMensaje(estudianteDAO.getMensaje());
			return;
		}
		request.getSession().removeAttribute("nuevoConvivencia");
		request.getSession().removeAttribute("nuevoConvivencia2");
		setMensaje("La información fue ingresada satisfactoriamente");
	}

	/**
	 * Funcion: Compara los beans de la categoria a actualizar para ver si hay o
	 * no cambios enlos datos<BR>
	 * 
	 * @return boolean
	 **/
	public boolean compararFichasBasica(Basica basica, Basica basica2) {
		return estudianteDAO.compararBeans(basica, basica2);
	}

	public boolean compararFichasFamiliar(Familiar familiar, Familiar familiar2) {
		return estudianteDAO.compararBeans(familiar, familiar2);
	}

	public boolean compararFichasSalud(Salud salud, Salud salud2) {
		return estudianteDAO.compararBeans(salud, salud2);
	}

	public boolean compararFichasConvivencia(Convivencia convivencia,
			Convivencia convivencia2) {
		return estudianteDAO.compararBeans(convivencia, convivencia2);
	}

	public boolean compararFichasAtencion(AtencionEspecial atencion,
			AtencionEspecial atencion2) {
		return estudianteDAO.compararBeans(atencion, atencion2);
	}

	public boolean compararFichasAcademica(AcademicaVO academicaVO,
			AcademicaVO academicaVO2) {
		return estudianteDAO.compararBeans(academicaVO, academicaVO2);
	}

	public boolean compararFichasAsistencia(AsistenciaVO asistenciaVO,
			AsistenciaVO asistenciaVO2) {
		return estudianteDAO.compararBeans(asistenciaVO, asistenciaVO2);
	}

	/**
	 * Funcion: Procesa la peticion de actualizacinn de la categoria dada, en la
	 * base de datos<BR>
	 * 
	 * @param HttpServletRequest
	 *            request
	 **/
	public void actualizarRegistroBasica(HttpServletRequest request,
			Basica basica, Basica basica2, Login login)
			throws ServletException, IOException {
		if (compararFichasBasica(basica, basica2)) {
			setMensaje("La ficha seleccionada para guardar cambios no ha sido modificada");
			return;
		}
		if (!basica.getEstnumdoc().equals(basica2.getEstnumdoc())) {
			if (validarExistencia(basica.getEsttipodoc(), basica.getEstnumdoc())) {
				setMensaje("El nuevo numero de identificacinn no se puede asignar, porque ya ha sido asignado a otro estudiante");
				restaurarBeansBasica(request, basica2);
				return;
			}
		}
		if (!estudianteDAO.actualizar(basica, login, basica2.getEstcodigo())) {
			setMensaje(estudianteDAO.getMensaje());
			restaurarBeansBasica(request, basica2);
			return;
		}
		setMensaje("La información fue actualizada satisfactoriamente");
		recargarBeansBasica(request, basica);
	}

	public void actualizarRegistroFamiliar(HttpServletRequest request,
			Familiar familiar, Familiar familiar2) throws ServletException,
			IOException {
		if (compararFichasFamiliar(familiar, familiar2)) {
			setMensaje("La ficha seleccionada para guardar cambios no ha sido modificada");
			return;
		}
		if (!estudianteDAO.actualizar(familiar)) {
			setMensaje(estudianteDAO.getMensaje());
			restaurarBeansFamiliar(request, familiar2);
			return;
		}
		setMensaje("La información fue actualizada satisfactoriamente");
		recargarBeansFamiliar(request, familiar);
	}

	public void actualizarRegistroSalud(HttpServletRequest request,
			Salud salud, Salud salud2, Basica basica2) throws ServletException,
			IOException {
		if (compararFichasSalud(salud, salud2)) {
			setMensaje("La ficha seleccionada para guardar cambios no ha sido modificada");
			return;
		}
		String valores[] = { "1", basica2.getEstcodigo() };
		if (!estudianteDAO.actualizar(salud, valores)) {
			setMensaje(estudianteDAO.getMensaje());
			restaurarBeansSalud(request, salud2);
			return;
		}
		setMensaje("La información fue actualizada satisfactoriamente");
		recargarBeansSalud(request, salud);
	}

	public void actualizarRegistroAtencion(HttpServletRequest request,
			AtencionEspecial atencion, AtencionEspecial atencion2)
			throws ServletException, IOException {
		if (compararFichasAtencion(atencion, atencion2)) {
			setMensaje("La ficha seleccionada para guardar cambios no ha sido modificada");
			request.getSession().removeAttribute("nuevoAtencion");
			request.getSession().removeAttribute("nuevoAtencion2");
			return;
		}
		if (!estudianteDAO.actualizar(atencion)) {
			setMensaje(estudianteDAO.getMensaje());
			restaurarBeansAtencion(request, atencion2);
			return;
		}
		setMensaje("La información fue actualizada satisfactoriamente");
		// recargarBeans(request);
		request.getSession().removeAttribute("nuevoAtencion");
		request.getSession().removeAttribute("nuevoAtencion2");
	}

	public void actualizarRegistroConvivencia(HttpServletRequest request,
			Convivencia convivencia, Convivencia convivencia2)
			throws ServletException, IOException {
		if (compararFichasConvivencia(convivencia, convivencia2)) {
			setMensaje("La ficha seleccionada para guardar cambios no ha sido modificada");
			request.getSession().removeAttribute("nuevoConvivencia");
			request.getSession().removeAttribute("nuevoConvivencia2");
			return;
		}
		if (!estudianteDAO.actualizar(convivencia)) {
			setMensaje(estudianteDAO.getMensaje());
			restaurarBeansConvivencia(request, convivencia2);
			return;
		}
		setMensaje("La información fue actualizada satisfactoriamente");
		// recargarBeans(request);
		request.getSession().removeAttribute("nuevoConvivencia");
		request.getSession().removeAttribute("nuevoConvivencia2");
	}

	/**
	 * Funcion: Procesa la peticion de insercinn de la categoria dada, en la
	 * base de datos<BR>
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public void insertarRegistroBasica(HttpServletRequest request,
			Basica basica, Login login) throws ServletException, IOException {
		String resultado;
		String[] resul;
		if (validarExistencia(basica.getEsttipodoc(), basica.getEstnumdoc())) {
			setMensaje("El Nnmero de identificacinn es de un estudiante ya esta registrado, no se puede registrar");
			return;
		}
		// CALCULAR LA EDAD DEL BEAN
		basica.setEstedad("1");
		if (!estudianteDAO.insertar(basica, login)) {
			setMensaje(estudianteDAO.getMensaje());
			return;
		}
		resultado = estudianteDAO.getCodigoEstudiante(basica.getEsttipodoc(),
				basica.getEstnumdoc());
		basica.setEstcodigo(resultado);
		basica.setEstado("1");
		request.getSession().setAttribute("nuevoBasica2",
				(Basica) basica.clone());
		setMensaje("La información fue ingresada satisfactoriamente");
	}
	
	
	
	
	
	@SuppressWarnings("unused")
	public void insertarRegistroFamiliar(HttpServletRequest request, Familiar familiar, Login login) throws ServletException, IOException {
		
		String[] resul;
		String resultado;
		
		if (!estudianteDAO.insertar(familiar)) {
			setMensaje(estudianteDAO.getMensaje());
			return;
		}

		String consulta = "select FamCodigo from familia where famnumdocacudi='" + familiar.getFamnumdocacudi()+"'";
		
		if (!familiar.getFamnumdocmadre().equals(""))
			consulta += " and famnumdocmadre='" + familiar.getFamnumdocmadre()+"'";
		
		if (!familiar.getFamnumdocpadre().equals(""))
			consulta += " and famnumdocpadre='" + familiar.getFamnumdocpadre()+"'";
		
		consulta += " order by FamCodigo desc";
		resul = cursor.getFila(consulta);
		familiar.setFamcodigo(resul[0]);
		familiar.setEstado("1");

		Basica b = (Basica) (request.getSession().getAttribute("nuevoBasica"));
		b.setEstcodfamilia(resul[0]);
		estudianteDAO.actualizar(b, login, b.getEstcodigo());

		request.getSession().setAttribute("nuevoFamiliar2",	(Familiar) familiar.clone());
		setMensaje("La información fue ingresada satisfactoriamente");
		setMensaje("El código de la familia ingresada es " + familiar.getFamcodigo());
		
	}
	
	
	
	
	
	public void insertarRegistroSalud(HttpServletRequest request, Salud salud,
			Basica basica2) throws ServletException, IOException {
		String resultado;
		String[] resul;
		salud.setSaltipoperso("1");// tipo estudiante
		salud.setSalcodperso(basica2.getEstcodigo());
		if (!estudianteDAO.insertar(salud)) {
			setMensaje(estudianteDAO.getMensaje());
			return;
		}
		salud.setSalestado("1");
		request.getSession().setAttribute("nuevoSalud2", (Salud) salud.clone());
		setMensaje("La información fue ingresada satisfactoriamente");
	}

	public void insertarRegistroAtencion(HttpServletRequest request,
			AtencionEspecial atencion, Login login, Basica basica2)
			throws ServletException, IOException {
		String resultado;
		String[] resul;
		atencion.setAtescodest(basica2.getEstcodigo());
		atencion.setUsuario(login.getUsuarioId());
		if (!estudianteDAO.insertar(atencion)) {
			setMensaje(estudianteDAO.getMensaje());
			return;
		}
		request.getSession().removeAttribute("nuevoAtencion");
		request.getSession().removeAttribute("nuevoAtencion2");
		setMensaje("La información fue ingresada satisfactoriamente");
	}

	/**
	 * Elimina del contexto de la sesion los beans de informacion del estudiante
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void borrarBeans(HttpServletRequest request)
			throws ServletException, IOException {
		request.getSession().removeAttribute("nuevoBasica");
		request.getSession().removeAttribute("nuevoFamiliar");
		request.getSession().removeAttribute("nuevoSalud");
		request.getSession().removeAttribute("nuevoConvivencia");
		request.getSession().removeAttribute("nuevoBasica2");
		request.getSession().removeAttribute("nuevoFamiliar2");
		request.getSession().removeAttribute("nuevoSalud2");
		request.getSession().removeAttribute("nuevoConvivencia2");
	}

	/**
	 * Busca en la base de datos un registro que coincida con el id ingresado
	 * por el usuario
	 * 
	 * @param String
	 *            cod
	 * @return boolean
	 */
	public boolean validarExistencia(String tipo, String cod) {
		return estudianteDAO.getEstudiante(tipo, cod);
		// String sentencia="Select * from estudiante where estnumdoc= "+cod;
		// if(cursor.existe(sentencia)){
		// return true;//si existe
		// }
		// return false;
	}

	/**
	 * Referencia al bean del usuario con la información proporcionada por el
	 * bean de respaldo
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void restaurarBeansBasica(HttpServletRequest request, Basica basica2)
			throws ServletException, IOException {
		request.getSession().setAttribute("nuevoBasica",
				(Basica) basica2.clone());
	}

	public void restaurarBeansFamiliar(HttpServletRequest request,
			Familiar familiar2) throws ServletException, IOException {
		request.getSession().setAttribute("nuevoFamiliar",
				(Familiar) familiar2.clone());
	}

	public void restaurarBeansSalud(HttpServletRequest request, Salud salud2)
			throws ServletException, IOException {
		request.getSession().setAttribute("nuevoSalud", (Salud) salud2.clone());
	}

	public void restaurarBeansConvivencia(HttpServletRequest request,
			Convivencia convivencia2) throws ServletException, IOException {
		request.getSession().setAttribute("nuevoConvivencia",
				(Convivencia) convivencia2.clone());
	}

	public void restaurarBeansAtencion(HttpServletRequest request,
			AtencionEspecial atencion2) throws ServletException, IOException {
		request.getSession().setAttribute("nuevoAtencion",
				(AtencionEspecial) atencion2.clone());
	}

	public void restaurarBeansAcademica(HttpServletRequest request,
			AcademicaVO academicaVO2) throws ServletException, IOException {
		request.getSession().setAttribute("academicaVO",
				(AcademicaVO) academicaVO2.clone());
	}

	public void restaurarBeansAsistencia(HttpServletRequest request,
			AsistenciaVO asistenciaVO2) throws ServletException, IOException {
		request.getSession().setAttribute("asistenciaVO",
				(AsistenciaVO) asistenciaVO2.clone());
	}

	/**
	 * Referencia al bean de respaldo con la nueva información proporcionada por
	 * el bean modificado por el usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void recargarBeansBasica(HttpServletRequest request, Basica basica)
			throws ServletException, IOException {
		request.getSession().setAttribute("nuevoBasica2",
				(Basica) basica.clone());
	}

	public void recargarBeansFamiliar(HttpServletRequest request,
			Familiar familiar) throws ServletException, IOException {
		request.getSession().setAttribute("nuevoFamiliar2",
				(Familiar) familiar.clone());
	}

	public void recargarBeansSalud(HttpServletRequest request, Salud salud)
			throws ServletException, IOException {
		request.getSession().setAttribute("nuevoSalud2", (Salud) salud.clone());
	}

	public void recargarBeansConvivencia(HttpServletRequest request,
			Convivencia convivencia) throws ServletException, IOException {
		request.getSession().setAttribute("nuevoConvivencia2",
				(Convivencia) convivencia.clone());
	}

	public void recargarBeansAtencion(HttpServletRequest request,
			AtencionEspecial atencion) throws ServletException, IOException {
		request.getSession().setAttribute("nuevoAtencion2",
				(AtencionEspecial) atencion.clone());
	}

	public void recargarBeansAcademica(HttpServletRequest request,
			AcademicaVO academicaVO) throws ServletException, IOException {
		request.getSession().setAttribute("academicaVO2",
				(AcademicaVO) academicaVO.clone());
	}

	public void recargarBeansAsistencia(HttpServletRequest request,
			AsistenciaVO asistenciaVO) throws ServletException, IOException {
		request.getSession().setAttribute("asistenciaVO2",
				(AsistenciaVO) asistenciaVO.clone());
	}

	/**
	 * Recibe la peticion por el metodo get de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);// redirecciona la peticion a doPost
	}

	/**
	 * Recibe la peticion por el metodo Post de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String s = process(request, response);
		if (s != null && !s.equals(""))
			ir(1, s, request, response);
	}

	/**
	 * Redirige el control a otro servlet
	 * 
	 * @param int a: 1=redirigir como 'include', 2=redirigir como 'forward'
	 * @param String
	 *            s
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/
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

	/**
	 * Concatena el parametro en una variable que contiene los mensajes que se
	 * van a enviar a la vista
	 * 
	 * @param String
	 *            s
	 **/
	public void setMensaje(String s) {
		band = false;
		if (!err) {
			err = true;
			mensaje = "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n";
		}
		mensaje += "  - " + s + "\n";
	}

	/**
	 * Retorna una variable que contiene los mensajes que se van a enviar a la
	 * vista
	 * 
	 * @return String
	 **/
	public String getMensaje() {
		return mensaje;
	}
}
