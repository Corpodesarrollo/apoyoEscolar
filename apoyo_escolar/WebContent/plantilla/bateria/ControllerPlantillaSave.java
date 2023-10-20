package siges.plantilla.bateria;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Ruta;
import siges.dao.Ruta2;
import siges.io.Zip;
import siges.login.beans.Login;
import siges.plantilla.Excel;
import siges.plantilla.beans.Logros;
import siges.plantilla.dao.PlantillaDAO;
import siges.util.Logger;
import siges.util.Properties;

/**
 * siges.plantilla.bateria<br>
 * Funcinn: Procesa la solicitud de plantillas y llama al servicio que la genera
 * <br>
 */
public class ControllerPlantillaSave extends HttpServlet {
	private Cursor cursor;

	private PlantillaDAO plantillaDAO;

	private ResourceBundle rb;

	private Timestamp f2;

	private Integer cadena = new Integer(java.sql.Types.VARCHAR);

	private Integer entero = new Integer(java.sql.Types.INTEGER);

	//private Integer enterolargo = new Integer(java.sql.Types.BIGINT);

	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 *             <br>
	 *             Return Type: String<br>
	 *             Version 1.1.<br>
	 */
	public synchronized String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		f2 = new Timestamp(new java.util.Date().getTime());
		rb = ResourceBundle.getBundle("siges.plantilla.bundle.plantilla");
		String[] ubicacion = new String[3];
		String[] ubicacionPlantilla = new String[4];
		String sig = null, sig4, sig5, ant, er, home, boton;
		sig4 = getServletConfig().getInitParameter("sig4");
		sig5 = getServletConfig().getInitParameter("sig5");
		ant = getServletConfig().getInitParameter("ant");
		er = getServletContext().getInitParameter("error");
		home = getServletContext().getInitParameter("home");
		cursor = new Cursor();
		plantillaDAO = new PlantillaDAO(cursor);
		boton = (request.getParameter("cmd") != null) ? request.getParameter("cmd") : new String("");
		if (!asignarBeans(request)) {
			request.setAttribute("mensaje", setMensaje("Error capturando datos de sesinn para el usuario"));
			return er;
		}
		/**/
		Login login = (Login) session.getAttribute("login");
		Logros logros = null;
		if (session.getAttribute("logros") != null) {
			logros = (Logros) session.getAttribute("logros");
			logros.setPlantillaInstitucion(login.getInstId());
			logros.setPlantillaInstitucion_(login.getInst());
		}
		/**/
		if (request.getParameter("tipo") == null || request.getParameter("tipo").equals("")) {
			request.setAttribute("mensaje", setMensaje("Acceso denegado no hay una ficha definida"));
			return er;
		}
		int tipo = Integer.parseInt((String) request.getParameter("tipo"));
		if (boton.equals("Buscar")) {
			//String alert = "Las plantillas se estan generando en este momento. \nPulse en el hipervinculo 'Listado de Reportes' para ver si el reporte ya fue generado. \nO vaya a la opción de menu 'Reportes generados'";
			//FiltroPlantilla filtro = (FiltroPlantilla) filtroPlantilla.clone();
			//Login log = (Login) login.clone();
			ubicacion = getUbicacionZip(login.getUsuarioId(), f2, tipo,ubicacion);
			ubicacionPlantilla = getUbicacionPlantilla(login.getUsuarioId(), tipo,ubicacionPlantilla);
			switch (tipo) {
			case Properties.PLANTILLABATLOGRO:// bateria de logros
				asignarListaBateriaLogro(request, login, logros, tipo,ubicacion);
				return (sig4 += "?tipo=" + tipo);
			case Properties.PLANTILLABATDESCRIPTOR:// bateria de descriptor
				asignarListaBateriaDescriptor(request, login, logros, tipo,ubicacion);
				return (sig5 += "?tipo=" + tipo);
			}
		}
		if (boton.equals("Cancelar")) {
			borrarBeans(request);
			sig = home;
		}
		return sig;
	}

	/**
	 * Elimina del contexto de la session los beans de informacion del
	 * formulario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	/**
	 * @param request
	 *            <br>
	 *            Return Type: void<br>
	 *            Version 1.1.<br>
	 */
	public static void borrarBeans(HttpServletRequest request) {
		request.getSession().removeAttribute("filtroPlantilla");
	}

	/**
	 * @param request
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 *             <br>
	 *             Return Type: boolean<br>
	 *             Version 1.1.<br>
	 */
	public boolean asignarBeans(HttpServletRequest request) throws ServletException, IOException {
		HttpSession sess = request.getSession();
		if (sess.getAttribute("login") == null)
			return false;
		if (sess.getAttribute("logros") == null)
			return false;
		return true;
	}

	/**
	 * @param request
	 * @throws ServletException
	 * @throws IOException
	 *             <br>
	 *             Return Type: void<br>
	 *             Version 1.1.<br>
	 */
	public void asignarListaBateriaLogro(HttpServletRequest request, Login login, Logros logros, int tipo,String[] ubicacion) throws ServletException, IOException {
		String asig;
		Collection files = new ArrayList();
		Collection[] col = new Collection[4];
		List lista=null;
		Logros item=null;
		try {
			if(logros.getPlantillaDocente()!=0 && logros.getPlantillaDocente()!=-99){
				logros.setPlantillaDocente_(plantillaDAO.getNombreDocente(logros.getPlantillaDocente()));
			}
			// periodos
			//col[0] = Recursos.recursoEstatico[Recursos.PERIODO];
			col[0] = getListaPeriodo(login.getLogNumPer(), login.getLogNomPerDef());
			
			/* todo fue escogido en los combos */
			if (!logros.getPlantillaMetodologia().equals("-99") && !logros.getPlantillaGrado().equals("-99") && !logros.getPlantillaAsignatura().equals("-99")) {
				asig = logros.getPlantillaAsignatura().substring(logros.getPlantillaAsignatura().lastIndexOf("|") + 1, logros.getPlantillaAsignatura().length());
				request.setAttribute("plantilla", plantillaBateriaLogroDesc(asig, login, logros, tipo, col));
				request.setAttribute("tipoArchivo", "zip");
				Logger.print(login.getUsuarioId(), "Bateria Logro Inst:" + logros.getPlantillaInstitucion() + " Metod:" + logros.getPlantillaMetodologia() + " Grado:" + logros.getPlantillaGrado() + " Asig:" + asig, "Generacinn de Plantilla de Bateria de Logros: Metodologia '" + logros.getPlantillaMetodologia_() + "', Grado '" + logros.getPlantillaGrado_() + "', Asignatura '"
					+ logros.getPlantillaAsignatura_() + "'", 3, 1, this.toString());
				return;
			}
			/* no selecciono asignatura */
			if (!logros.getPlantillaMetodologia().equals("-99") && !logros.getPlantillaGrado().equals("-99") && logros.getPlantillaAsignatura().equals("-99")) {
				if(logros.getEstadoPlanEstudios()==0){//es plan de estudios unificado
					lista=plantillaDAO.getListaAsignatura(Long.parseLong(logros.getPlantillaInstitucion()),Integer.parseInt(logros.getPlantillaMetodologia()),logros.getPlantillaVigencia(),Integer.parseInt(logros.getPlantillaGrado()));
				}else{//es plan de estudios por docente
					lista=plantillaDAO.getListaDocenteAsignatura(Long.parseLong(logros.getPlantillaInstitucion()),Integer.parseInt(logros.getPlantillaMetodologia()),logros.getPlantillaVigencia(),Integer.parseInt(logros.getPlantillaGrado()));
				}
				if(lista.size()>0){
					for(int i=0;i<lista.size();i++){
						item=(Logros)lista.get(i);
						logros.setPlantillaAsignatura(item.getPlantillaAsignatura());
						logros.setPlantillaAsignatura_(item.getPlantillaAsignatura_());
						if(logros.getEstadoPlanEstudios()==1){
							logros.setPlantillaDocente(item.getPlantillaDocente());
							logros.setPlantillaDocente_(item.getPlantillaDocente_());
						}
						files.add(plantillaBateriaZip(item.getPlantillaAsignatura(), login, logros, tipo, col));
					}
					request.setAttribute("plantilla", ponerZip(files, login.getUsuarioId(), ubicacion, logros, tipo));
					request.setAttribute("tipoArchivo", "zip");
					Logger.print(login.getUsuarioId(), "Bateria Logro Inst:" + logros.getPlantillaInstitucion() + " Metod:" + logros.getPlantillaMetodologia() + " Grado:" + logros.getPlantillaGrado(), "Generacinn de Plantilla de Bateria de Logros:", 3, 1, this.toString());
				}else{
					request.setAttribute("mensaje", setMensaje("No se pudo generar la plantilla de Excel.\nPorque no se ha establecido el plan de estudios.\nHasta que no se ingrese esta información no sera posible generar archivos"));
					request.setAttribute("plantilla", "--");
				}
				return;
			}
			/* no selecciono grado y asignatura */
			if (!logros.getPlantillaMetodologia().equals("-99") && logros.getPlantillaGrado().equals("-99") && logros.getPlantillaAsignatura().equals("-99")) {
				// traer la lista de grados y asignatura para esa metodologia y
				// grado
				if(logros.getEstadoPlanEstudios()==0){//es plan de estudios unificado
					lista=plantillaDAO.getListaAsignaturaGrado(Long.parseLong(logros.getPlantillaInstitucion()),Integer.parseInt(logros.getPlantillaMetodologia()),logros.getPlantillaVigencia());
				}else{//es plan de estudios por docente
					lista=plantillaDAO.getListaDocenteAsignaturaGrado(Long.parseLong(logros.getPlantillaInstitucion()),Integer.parseInt(logros.getPlantillaMetodologia()),logros.getPlantillaVigencia());
				}
				if(lista.size()>0){
					for(int i=0;i<lista.size();i++){
						item=(Logros)lista.get(i);
						logros.setPlantillaGrado(item.getPlantillaGrado());
						logros.setPlantillaGrado_(item.getPlantillaGrado_());
						logros.setPlantillaAsignatura(item.getPlantillaAsignatura());
						logros.setPlantillaAsignatura_(item.getPlantillaAsignatura_());
						if(logros.getEstadoPlanEstudios()==1){
							logros.setPlantillaDocente(item.getPlantillaDocente());
							logros.setPlantillaDocente_(item.getPlantillaDocente_());
						}
						files.add(plantillaBateriaZip(item.getPlantillaAsignatura(), login, logros, tipo, col));
					}
					request.setAttribute("plantilla", ponerZip(files, login.getUsuarioId(), ubicacion, logros, tipo));
					request.setAttribute("tipoArchivo", "zip");
					Logger.print(login.getUsuarioId(), "Bateria Logro Inst:" + logros.getPlantillaInstitucion() + " Metod:" + logros.getPlantillaMetodologia() + " Grado:" + logros.getPlantillaGrado(), "Generacinn de Plantilla de Bateria de Logros:", 3, 1, this.toString());
				}else{
					request.setAttribute("mensaje", setMensaje("No se pudo generar la plantilla de Excel.\nPorque no se ha establecido el plan de estudios.\nHasta que no se ingrese esta información no sera posible generar archivos"));
					request.setAttribute("plantilla", "--");
				}
				
				return;
			}
		} catch (Exception th) {
			System.out.println("PLANTILLA Bateria Logro con errores: " + th);
			throw new ServletException(th);
		}
	}

	/**
	 * @param request
	 * @throws ServletException
	 * @throws IOException
	 *             <br>
	 *             Return Type: void<br>
	 *             Version 1.1.<br>
	 */
	public void asignarListaBateriaDescriptor(HttpServletRequest request, Login login, Logros logros, int tipo,String[] ubicacion) throws ServletException, IOException {
		String asig;
		Collection files = new ArrayList();
		Collection list;
		Object[] o;
		Collection[] col = new Collection[4];
		List lista=null;
		Logros item=null;
		try {
			if(logros.getPlantillaDocente()!=0 && logros.getPlantillaDocente()!=-99){
				logros.setPlantillaDocente_(plantillaDAO.getNombreDocente(logros.getPlantillaDocente()));
			}
			// periodos
			//col[0] = Recursos.recursoEstatico[Recursos.PERIODO];
			col[0] = getListaPeriodo(login.getLogNumPer(), login.getLogNomPerDef());
			/* todo fue escogido en los combos */
			if (!logros.getPlantillaMetodologia().equals("-99") && !logros.getPlantillaGrado().equals("-99") && !logros.getPlantillaAsignatura().equals("-99")) {
				asig = logros.getPlantillaAsignatura().substring(logros.getPlantillaAsignatura().lastIndexOf("|") + 1, logros.getPlantillaAsignatura().length());
				request.setAttribute("plantilla", plantillaBateriaLogroDesc(asig, login, logros, tipo, col));
				request.setAttribute("tipoArchivo", "zip");
				Logger.print(login.getUsuarioId(), "Bateria Descriptor Inst:" + logros.getPlantillaInstitucion() + " Met:" + logros.getPlantillaMetodologia() + " Gra:" + logros.getPlantillaGrado() + " Area:" + asig, "Generacinn de Plantilla de Bateria de Descriptores: Metodologia '" + logros.getPlantillaMetodologia_() + "', Grado '" + logros.getPlantillaGrado_() + "', Area '"
					+ logros.getPlantillaAsignatura_() + "'", 3, 1, this.toString());
				return;
			}
			/* no selecciono area */
			if (!logros.getPlantillaMetodologia().equals("-99") && !logros.getPlantillaGrado().equals("-99") && logros.getPlantillaAsignatura().equals("-99")) {
				// traer la lista de areas para esa metodologia y grado
				if(logros.getEstadoPlanEstudios()==0){//es plan de estudios unificado
					lista=plantillaDAO.getListaArea(Long.parseLong(logros.getPlantillaInstitucion()),Integer.parseInt(logros.getPlantillaMetodologia()),logros.getPlantillaVigencia(),Integer.parseInt(logros.getPlantillaGrado()));
				}else{//es plan de estudios por docente
					lista=plantillaDAO.getListaDocenteArea(Long.parseLong(logros.getPlantillaInstitucion()),Integer.parseInt(logros.getPlantillaMetodologia()),logros.getPlantillaVigencia(),Integer.parseInt(logros.getPlantillaGrado()));
				}
				if(lista.size()>0){
					for(int i=0;i<lista.size();i++){
						item=(Logros)lista.get(i);
						logros.setPlantillaAsignatura(item.getPlantillaAsignatura());
						logros.setPlantillaAsignatura_(item.getPlantillaAsignatura_());
						if(logros.getEstadoPlanEstudios()==1){
							logros.setPlantillaDocente(item.getPlantillaDocente());
							logros.setPlantillaDocente_(item.getPlantillaDocente_());
						}
						files.add(plantillaBateriaZip(item.getPlantillaAsignatura(), login, logros, tipo, col));
					}
					request.setAttribute("plantilla", ponerZip(files, login.getUsuarioId(), ubicacion, logros, tipo));
					request.setAttribute("tipoArchivo", "zip");
					Logger.print(login.getUsuarioId(), "Bateria Descriptor Inst:" + logros.getPlantillaInstitucion() + " Met:" + logros.getPlantillaMetodologia() + " Gra:" + logros.getPlantillaGrado(), "Generacinn de Plantilla de Bateria de Descriptores:", 3, 1, this.toString());
				}else{
					request.setAttribute("mensaje", setMensaje("No se pudo generar la plantilla de Excel.\nPorque no se ha establecido el plan de estudios.\nHasta que no se ingrese esta información no sera posible generar archivos"));
					request.setAttribute("plantilla", "--");
				}
				return;
				/*
				list = new ArrayList();
				o = new Object[2];// institucion
				o[0] = enterolargo;
				o[1] = login.getInstId();
				list.add(o);
				o = new Object[2];// metodologia
				o[0] = entero;
				o[1] = logros.getPlantillaMetodologia();
				list.add(o);
				o = new Object[2];// grado
				o[0] = entero;
				o[1] = logros.getPlantillaGrado();
				list.add(o);
				o = new Object[2];// Vigencia
				o[0] = entero;
				o[1] = plantillaDAO.getVigencia();
				list.add(o);
				String[][] a = plantillaDAO.getFiltroMatriz(rb.getString("listaPlantillaDescriptor1"), list);
				if (a != null) {
					for (int i = 0; i < a.length; i++) {
						logros.setPlantillaAsignatura(a[i][0]);
						logros.setPlantillaAsignatura_(a[i][1]);
						asig = logros.getPlantillaAsignatura();
						files.add(plantillaBateriaZip(asig, login, logros, tipo, col));
					}
					request.setAttribute("plantilla", ponerZip(files, login.getUsuarioId(), ubicacion, logros, tipo));
					request.setAttribute("tipoArchivo", "zip");
					Logger.print(login.getUsuarioId(), "Bateria Descriptor Inst:" + logros.getPlantillaInstitucion() + " Met:" + logros.getPlantillaMetodologia() + " Gra:" + logros.getPlantillaGrado(), "Generacinn de Plantilla de Bateria de Descriptores:", 3, 1, this.toString());
				} else {
					request.setAttribute("mensaje", setMensaje("No se pudo generar la plantilla de Excel.\nPorque no se ha establecido el plan de estudios.\nHasta que no se ingrese esta información no sera posible generar archivos"));
					request.setAttribute("plantilla", "--");
				}
				return;
				*/
			}
			/* no selecciono grado y asignatura */
			if (!logros.getPlantillaMetodologia().equals("-99") && logros.getPlantillaGrado().equals("-99") && logros.getPlantillaAsignatura().equals("-99")) {
				// traer la lista de grados y asignatura para esa metodologia y grado
				if(logros.getEstadoPlanEstudios()==0){//es plan de estudios unificado
					lista=plantillaDAO.getListaAreaGrado(Long.parseLong(logros.getPlantillaInstitucion()),Integer.parseInt(logros.getPlantillaMetodologia()),logros.getPlantillaVigencia());
				}else{//es plan de estudios por docente
					lista=plantillaDAO.getListaDocenteAreaGrado(Long.parseLong(logros.getPlantillaInstitucion()),Integer.parseInt(logros.getPlantillaMetodologia()),logros.getPlantillaVigencia());
				}
				if(lista.size()>0){
					for(int i=0;i<lista.size();i++){
						item=(Logros)lista.get(i);
						logros.setPlantillaGrado(item.getPlantillaGrado());
						logros.setPlantillaGrado_(item.getPlantillaGrado_());
						logros.setPlantillaAsignatura(item.getPlantillaAsignatura());
						logros.setPlantillaAsignatura_(item.getPlantillaAsignatura_());
						if(logros.getEstadoPlanEstudios()==1){
							logros.setPlantillaDocente(item.getPlantillaDocente());
							logros.setPlantillaDocente_(item.getPlantillaDocente_());
						}
						files.add(plantillaBateriaZip(item.getPlantillaAsignatura(), login, logros, tipo, col));
					}
					request.setAttribute("plantilla", ponerZip(files, login.getUsuarioId(), ubicacion, logros, tipo));
					request.setAttribute("tipoArchivo", "zip");
					Logger.print(login.getUsuarioId(), "Bateria Descriptor Inst:" + logros.getPlantillaInstitucion() + " Met:" + logros.getPlantillaMetodologia() + " Gra:" + logros.getPlantillaGrado(), "Generacinn de Plantilla de Bateria de Descriptores:", 3, 1, this.toString());
				}else{
					request.setAttribute("mensaje", setMensaje("No se pudo generar la plantilla de Excel.\nPorque no se ha establecido el plan de estudios.\nHasta que no se ingrese esta información no sera posible generar archivos"));
					request.setAttribute("plantilla", "--");
				}
				/*
				list = new ArrayList();
				o = new Object[2];// institucion
				o[0] = enterolargo;
				o[1] = login.getInstId();
				list.add(o);
				o = new Object[2];// metodologia
				o[0] = entero;
				o[1] = logros.getPlantillaMetodologia();
				list.add(o);
				o = new Object[2];// Vigencia
				o[0] = entero;
				o[1] = plantillaDAO.getVigencia();
				list.add(o);
				String[][] a = plantillaDAO.getFiltroMatriz(rb.getString("listaPlantillaDescriptor2"), list);
				if (a != null) {
					for (int i = 0; i < a.length; i++) {
						logros.setPlantillaGrado(a[i][0]);
						logros.setPlantillaGrado_(a[i][1]);
						logros.setPlantillaAsignatura(a[i][2]);
						logros.setPlantillaAsignatura_(a[i][3]);
						asig = logros.getPlantillaAsignatura();
						files.add(plantillaBateriaZip(asig, login, logros, tipo, col));
					}
					request.setAttribute("plantilla", ponerZip(files, login.getUsuarioId(), ubicacion, logros, tipo));
					request.setAttribute("tipoArchivo", "zip");
					Logger.print(login.getUsuarioId(), "Bateria Descriptor Inst:" + logros.getPlantillaInstitucion() + " Met:" + logros.getPlantillaMetodologia() + " Gra:" + logros.getPlantillaGrado(), "Generacinn de Plantilla de Bateria de Descriptores:", 3, 1, this.toString());
				} else {
					request.setAttribute("mensaje", setMensaje("No se pudo generar la plantilla de Excel.\nPorque no se ha establecido el plan de estudios.\nHasta que no se ingrese esta información no sera posible generar archivos"));
					request.setAttribute("plantilla", "--");
				}
				return;
				*/
			}
			/* no selecciono metodologia ni grado y asignatura */
			/*
			if (logros.getPlantillaMetodologia().equals("-99") && logros.getPlantillaGrado().equals("-99") && logros.getPlantillaAsignatura().equals("-99")) {
				// traer la lista de metodologias, grados y asignaturas para esa
				// institucion
				list = new ArrayList();
				o = new Object[2];// institucion
				o[0] = enterolargo;
				o[1] = login.getInstId();
				list.add(o);
				o = new Object[2];// Vigencia
				o[0] = entero;
				o[1] = plantillaDAO.getVigencia();
				list.add(o);
				String[][] a = plantillaDAO.getFiltroMatriz(rb.getString("listaPlantillaDescriptor3"), list);
				if (a != null) {
					for (int i = 0; i < a.length; i++) {
						logros.setPlantillaMetodologia(a[i][0]);
						logros.setPlantillaMetodologia_(a[i][1]);
						logros.setPlantillaGrado(a[i][2]);
						logros.setPlantillaGrado_(a[i][3]);
						logros.setPlantillaAsignatura(a[i][4]);
						logros.setPlantillaAsignatura_(a[i][5]);
						asig = logros.getPlantillaAsignatura();
						files.add(plantillaBateriaZip(asig, login, logros, tipo, col));
					}
					request.setAttribute("plantilla", ponerZip(files, login.getUsuarioId(), ubicacion, logros, tipo));
					request.setAttribute("tipoArchivo", "zip");
					Logger.print(login.getUsuarioId(), "Bateria Descriptor Inst:" + logros.getPlantillaInstitucion() + " Met:" + logros.getPlantillaMetodologia() + " Gra:" + logros.getPlantillaGrado(), "Generacinn de Plantilla de Bateria de Descriptores:", 3, 1, this.toString());
				} else {
					request.setAttribute("mensaje", setMensaje("No se pudo generar la plantilla de Excel.\nPorque no se ha establecido el plan de estudios.\nHasta que no se ingrese esta información no sera posible generar archivos"));
					request.setAttribute("plantilla", "--");
				}
				return;
			}
			*/
		} catch (Exception th) {
			System.out.println("PLANTILLA Bateria Descriptor con errores:" + th);
			throw new ServletException(th);
		}
	}

	/**
	 * @param id
	 * @param t
	 * @return<br>
	 *         Return Type: String[]<br>
	 *         Version 1.1.<br>
	 */
	public String[] getUbicacionZip(String id, Timestamp t, int tipo,String[] ubicacion) {
		String path;
		String archivo = null;
		String relativo = null;
		String ruta = null;
		switch (tipo) {
		case Properties.PLANTILLABATLOGRO:
			path = rb.getString("plantillaLogro.PathPlantillaLogro") + "." + id;
			archivo = "PlantillaLogro_" + t.toString().replace(':', '-').replace('.', '-').replace(' ', '_') + ".zip";
			relativo = Ruta.getRelativo(getServletContext(), path);
			ruta = Ruta.get(getServletContext(), path);// path del nuevo
														// archivo
			break;
		case Properties.PLANTILLABATDESCRIPTOR:
			path = rb.getString("plantillaDescriptor.PathPlantillaDescriptor") + "." + id;
			archivo = "PlantillaDescriptor_" + t.toString().replace(':', '-').replace('.', '-').replace(' ', '_') + ".zip";
			relativo = Ruta.getRelativo(getServletContext(), path);
			ruta = Ruta.get(getServletContext(), path);// path del nuevo
														// archivo
			break;
		}
		ubicacion[0] = archivo;
		ubicacion[1] = relativo;
		ubicacion[2] = ruta;
		return ubicacion;
	}

	public String[] getUbicacionPlantilla(String id, int tipo,String[] ubicacionPlantilla) {
		String path;
		String archivo = null;
		String relativo = null;
		int i = 0;
		switch (tipo) {
		case Properties.PLANTILLABATLOGRO:
			path = rb.getString("plantillaLogro.PathPlantillaLogro") + "." + id;
			relativo = Ruta.getRelativo(getServletContext(), path);
			ubicacionPlantilla[i++] = Ruta2.get(getServletContext(), rb.getString("plantillaLogro.PathPlantilla"));// path de la plantilla
			ubicacionPlantilla[i++] = rb.getString("plantillaLogro.Plantilla");// nombre de la plantilla
			ubicacionPlantilla[i++] = Ruta.get(getServletContext(), path);// path del nuevo archivo
			ubicacionPlantilla[i++] = archivo;// nombre del nuevo archivo
			break;
		case Properties.PLANTILLABATDESCRIPTOR:
			path = rb.getString("plantillaDescriptor.PathPlantillaDescriptor") + "." + id;
			relativo = Ruta.getRelativo(getServletContext(), path);
			ubicacionPlantilla[i++] = Ruta2.get(getServletContext(), rb.getString("plantillaDescriptor.PathPlantilla"));// path de la plantilla
			ubicacionPlantilla[i++] = rb.getString("plantillaDescriptor.Plantilla");// nombre de la plantilla
			ubicacionPlantilla[i++] = Ruta.get(getServletContext(), path);// path del nuevo archivo
			ubicacionPlantilla[i++] = archivo;// nombre del nuevo archivo
			break;
		}
		return ubicacionPlantilla;
	}

	/**
	 * @param filtro
	 * @param list
	 * @param id
	 * @param ubi
	 * @return<br>
	 *         Return Type: String<br>
	 *         Version 1.1.<br>
	 */
	public String ponerZip(Collection list, String id, String[] ubi, Logros logros, int tipo) {
		Zip zip = new Zip();
		String path;
		String archivo;
		String relativo;
		String ruta;
		int zise;
		switch (tipo) {
		case Properties.PLANTILLABATLOGRO:
			path = rb.getString("plantillaLogro.PathPlantillaLogro") + "." + id;
			archivo = "PlantillaLogro_" + f2.toString().replace(':', '-').replace('.', '-').replace(' ', '_') + ".zip";
			relativo = Ruta.getRelativo(getServletContext(), path);
			ruta = Ruta.get(getServletContext(), path);// path del nuevo archivo
			zise = 100000;
			if (zip.ponerZip(ruta, archivo, zise, list)) {
				f2 = new java.sql.Timestamp(new java.util.Date().getTime());
				ponerReporte(id, ubi[1] + ubi[0], "zip", "Plantillas de Importacinn de logros " + f2.toString().replace(':', '-').replace('.', '-').substring(0, 10) + "_[" + f2.toString().replace(':', '-').replace('.', '-').substring(11, 19) + "]", 5);
				return relativo + archivo;
			}
			break;
		case Properties.PLANTILLABATDESCRIPTOR:
			path = rb.getString("plantillaDescriptor.PathPlantillaDescriptor") + "." + id;
			archivo = "PlantillaDescriptor_" + f2.toString().replace(':', '-').replace('.', '-').replace(' ', '_') + ".zip";
			relativo = Ruta.getRelativo(getServletContext(), path);
			ruta = Ruta.get(getServletContext(), path);// path del nuevo archivo
			zise = 100000;
			if (zip.ponerZip(ruta, archivo, zise, list)) {
				ponerReporte(id, ubi[1] + ubi[0], "zip", "Plantillas de Importacinn de Descriptores" + f2.toString().replace(':', '-').replace('.', '-').substring(0, 10) + "_[" + f2.toString().replace(':', '-').replace('.', '-').substring(11, 19) + "]", 5);
				return relativo + archivo;
			}
			break;
		}
		return "--";
	}

	/**
	 * @param asig
	 * @return<br>
	 *         Return Type: String<br>
	 *         Version 1.1.<br>
	 */
	public String plantillaBateriaLogroDesc(String asig, Login login, Logros logros, int tipo, Collection[] col) throws Exception{
		Excel excel = new Excel();
		String relativo = "";
		String path = "", archivo = "";
		logros.setPlantillaAsignatura(asig);
		int i = 0;
		String[] config = new String[4];
		/* CONFIGURACION DE ARCHIVOS */
		switch (tipo) {
		case Properties.PLANTILLABATLOGRO:
			logros.setTipoPlantilla("" + Properties.PLANTILLABATLOGRO);
			logros.setNivelLogro("4");
			path = rb.getString("plantillaLogro.PathPlantillaLogro") + "." + login.getUsuarioId();
			/* calcular el nombre del archivo */
			archivo = getNombre("PlantillaLogro", logros.getPlantillaMetodologia(), logros.getPlantillaGrado(), logros.getPlantillaAsignatura_(),logros.getPlantillaDocente(),logros.getEstadoPlanEstudios());
			relativo = Ruta.getRelativo(getServletContext(), path);
			i = 0;
			config[i++] = Ruta2.get(getServletContext(), rb.getString("plantillaLogro.PathPlantilla"));// path de la plantilla
			config[i++] = rb.getString("plantillaLogro.Plantilla");// nombre de la plantilla
			config[i++] = Ruta.get(getServletContext(), path);// path del nuevo archivo
			config[i++] = archivo;// nombre del nuevo archivo
			if (!excel.plantillaBateriaLogro(config, col, logros)) {
				setMensaje(excel.getMensaje());
				return "--";
			} 
			ponerReporte(login.getUsuarioId(), relativo + config[3], "xls", config[3], 5);
			break;
		case Properties.PLANTILLABATDESCRIPTOR:
			logros.setTipoPlantilla("" + Properties.PLANTILLABATDESCRIPTOR);
			logros.setNivelLogro("4");
			path = rb.getString("plantillaDescriptor.PathPlantillaDescriptor") + "." + login.getUsuarioId();
			/* calcular el nombre del archivo */
			archivo = getNombre("PlantillaDescriptor", logros.getPlantillaMetodologia(), logros.getPlantillaGrado(), logros.getPlantillaAsignatura_(),logros.getPlantillaDocente(),logros.getEstadoPlanEstudios());
			relativo = Ruta.getRelativo(getServletContext(), path);
			i = 0;
			config[i++] = Ruta2.get(getServletContext(), rb.getString("plantillaDescriptor.PathPlantilla"));// path de la plantilla
			config[i++] = rb.getString("plantillaDescriptor.Plantilla");// nombre de la plantilla
			config[i++] = Ruta.get(getServletContext(), path);// path del nuevo archivo
			config[i++] = archivo;// nombre del nuevo archivo
			if (!excel.plantillaBateriaDescriptor(config, col, logros)) {
				setMensaje(excel.getMensaje());
				return "--";
			}
			ponerReporte(login.getUsuarioId(), relativo + config[3], "xls", config[3], 5);
			break;
		}
		return relativo + config[3];
	}

	/**
	 * @param ini
	 * @param meto
	 * @param grad
	 * @param asig
	 * @return<br>
	 *         Return Type: String<br>
	 *         Version 1.1.<br>
	 */
	public String getNombre(String ini, String meto, String grad, String asig,long doc, int estado) {
		String met = formatear((meto.length() > 21 ? meto.substring(0, 21) : meto));
		String gra = formatear((grad.length() > 8 ? grad.substring(0, 8) : grad));
		String asi = formatear(asig);
		String fe = f2.toString().replace(':', '-').replace('.', '-').substring(0, 10) + "_" + f2.toString().replace(':', '-').replace('.', '-').substring(11, 19);
		if(estado==0)
			return (ini + "_Met_" + met + "_Gra_" + gra + "_" + asi + "_(" + fe + ")[" + (int) (Math.random() * 1000) + "].xls");
		else
			return (ini + "_Met_" + met + "_Gra_" + gra + "_" + asi + "_Doc_"+doc+"(" + fe + ")[" + (int) (Math.random() * 1000) + "].xls");
	}

	/**
	 * @param a
	 * @return<br>
	 *         Return Type: String<br>
	 *         Version 1.1.<br>
	 */
	public String formatear(String a) {
		return (a.replace(' ', '_').replace('n', 'a').replace('n', 'e').replace('n', 'i').replace('n', 'o').replace('n', 'u').replace('n', 'A').replace('n', 'E').replace('n', 'I').replace('n', 'O').replace('n', 'U').replace('n', 'n').replace('n', 'N').replace('n', 'a').replace('n', 'e').replace('n', 'i').replace('n', 'o').replace('n', 'u').replace('n', 'A').replace('n', 'E').replace('n', 'I')
			.replace('n', 'O').replace('n', 'U').replace('n', 'c').replace(':', '_').replace('.', '_').replace('/', '_').replace('\\', '_'));
	}

	/**
	 * @param asig
	 * @return<br>
	 *         Return Type: String<br>
	 *         Version 1.1.<br>
	 */
	public String plantillaBateriaZip(String asig, Login login, Logros logros, int tipo, Collection[] col) {
		Excel excel = new Excel();
		String relativo = "";
		String path = "", archivo = "";
		logros.setPlantillaAsignatura(asig);
		int i = 0;
		String[] config = new String[4];
		/* CONFIGURACION DE ARCHIVOS */
		switch (tipo) {
		case Properties.PLANTILLABATLOGRO:
			logros.setTipoPlantilla("" + Properties.PLANTILLABATLOGRO);
			logros.setNivelLogro("4");
			// System.out.println("enrtro por el 4");
			path = rb.getString("plantillaLogro.PathPlantillaLogro") + "." + login.getUsuarioId();
			/* calcular el nombre del archivo */
			archivo = getNombre("PlantillaLogro", logros.getPlantillaMetodologia(), logros.getPlantillaGrado(), logros.getPlantillaAsignatura_(), logros.getPlantillaDocente(),logros.getEstadoPlanEstudios());
			relativo = Ruta.getRelativo(getServletContext(), path);
			i = 0;
			config[i++] = Ruta2.get(getServletContext(), rb.getString("plantillaLogro.PathPlantilla"));// path de la plantilla
			config[i++] = rb.getString("plantillaLogro.Plantilla");// nombre de la plantilla
			config[i++] = Ruta.get(getServletContext(), path);// path del nuevo archivo
			config[i++] = archivo;// nombre del nuevo archivo
			if (!excel.plantillaBateriaLogro(config, col, logros)) {
				setMensaje(excel.getMensaje());
				return "";
			}
			break;
		case Properties.PLANTILLABATDESCRIPTOR:
			logros.setTipoPlantilla("" + Properties.PLANTILLABATDESCRIPTOR);
			logros.setNivelLogro("4");
			path = rb.getString("plantillaDescriptor.PathPlantillaDescriptor") + "." + login.getUsuarioId();
			/* calcular el nombre del archivo */
			archivo = getNombre("PlantillaDescriptor", logros.getPlantillaMetodologia(), logros.getPlantillaGrado(), logros.getPlantillaAsignatura_(),logros.getPlantillaDocente(),logros.getEstadoPlanEstudios());
			relativo = Ruta.getRelativo(getServletContext(), path);
			i = 0;
			config[i++] = Ruta2.get(getServletContext(), rb.getString("plantillaDescriptor.PathPlantilla"));// path de la plantilla
			config[i++] = rb.getString("plantillaDescriptor.Plantilla");// nombre de la plantilla
			config[i++] = Ruta.get(getServletContext(), path);// path del nuevo archivo
			config[i++] = archivo;// nombre del nuevo archivo
			if (!excel.plantillaBateriaDescriptor(config, col, logros)) {
				setMensaje(excel.getMensaje());
				return "";
			}
			break;
		}
		return config[2] + config[3];
	}

	/**
	 * Funcinn: Pone el registro del reporte generado <BR>
	 * 
	 * @param String
	 *            us
	 * @param String
	 *            rec
	 * @param String
	 *            tipo
	 * @param String
	 *            nombre
	 */
	public void ponerReporte(String us, String rec, String tipo, String nombre, int modulo) {
		Collection list;
		Object[] o;
		list = new ArrayList();
		o = new Object[2];
		o[0] = cadena;
		o[1] = us;// usuario
		list.add(o);
		o = new Object[2];
		o[0] = cadena;
		o[1] = rec;// rec
		list.add(o);
		o = new Object[2];
		o[0] = cadena;
		o[1] = tipo;// tipo
		list.add(o);
		o = new Object[2];
		o[0] = cadena;
		o[1] = nombre;// nombre
		list.add(o);
		o = new Object[2];
		o[0] = entero;
		o[1] = "" + modulo;// nombre
		list.add(o);
		cursor.registrar(rb.getString("ReporteInsertar"), list);
	}

	/**
	 * @param us
	 * @param rec
	 * @param tipo
	 * @param nombre
	 * @param modulo
	 * @param estado
	 *            <br>
	 *            Return Type: void<br>
	 *            Version 1.1.<br>
	 */
	public void ponerReporteZip(String us, String rec, String tipo, String nombre, int modulo, int estado) {
		// System.out.println("Poner Reporte:"+us+" | "+rec);
		Collection list;
		Object[] o;
		list = new ArrayList();
		o = new Object[2];
		o[0] = cadena;
		o[1] = us;// usuario
		list.add(o);
		o = new Object[2];
		o[0] = cadena;
		o[1] = rec;// rec
		list.add(o);
		o = new Object[2];
		o[0] = cadena;
		o[1] = tipo;// tipo
		list.add(o);
		o = new Object[2];
		o[0] = cadena;
		o[1] = nombre;// nombre
		list.add(o);
		o = new Object[2];
		o[0] = entero;
		o[1] = "" + modulo;// modulo
		list.add(o);
		o = new Object[2];
		o[0] = entero;
		o[1] = "" + estado;// estado
		list.add(o);
		cursor.registrar(rb.getString("ReporteInsertar2"), list);
	}

	/**
	 * @param us
	 * @param rec
	 * @param estado
	 * @param mensaje
	 * @param c
	 *            <br>
	 *            Return Type: void<br>
	 *            Version 1.1.<br>
	 */
	public void actualizarReporte(String us, String rec, int estado, String mensaje, Cursor c) {
		Collection list;
		Object[] o;
		list = new ArrayList();
		o = new Object[2];
		o[0] = entero;
		o[1] = "" + estado;// estado
		list.add(o);
		o = new Object[2];
		o[0] = cadena;
		o[1] = mensaje;// mensaje
		list.add(o);
		o = new Object[2];
		o[0] = cadena;
		o[1] = us;// usuario
		list.add(o);
		o = new Object[2];
		o[0] = cadena;
		o[1] = rec;// rec
		list.add(o);
		// System.out.println("actualizar:"+us+" | "+rec);
		if (!c.registrar(rb.getString("ReporteActualizar"), list))
			System.out.println("Error actualizando registro" + c.getMensaje());
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);// redirecciona la peticion a doPost
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	 *             <br>
	 *             Return Type: void<br>
	 *             Version 1.1.<br>
	 */
	public void ir(int a, String s, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher(s);
		if (a == 1)
			rd.include(request, response);
		else
			rd.forward(request, response);
	}

	/**
	 * @param s
	 *            <br>
	 *            Return Type: void<br>
	 *            Version 1.1.<br>
	 */
	public String setMensaje(String s) {
		String mensaje = "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n";
		return mensaje += "  - " + s + "\n";
	}

	
	
	
	

	
	/*MODIFICACION PERIODOS EVVALUACION EN LINEA 12-03-10 WG
	 * 
	 * */
	
	public Collection getListaPeriodo(long numPer, String nomPerDef){
		Collection collection=new ArrayList();
		ItemVO item=null;
//		System.out.println("ENTRO A CARGAR PERIODOS ** FILTRO EVALUACION EDIT"+numPer);
		for(int i=1;i<=numPer;i++){
			Object[] o=new Object[2];
			o[0]= ""+(i);
			o[1]=""+(i);
			collection.add(o);
		}		
	  
			
		
		
	/*	Object[] o=new Object[2];
			o[0]= ""+(numPer+1);
			o[1]=nomPerDef;
			collection.add(o);*/
		//item=new ItemVO(numPer+1,nomPerDef); l.add(item);
		return collection;
	}
}


