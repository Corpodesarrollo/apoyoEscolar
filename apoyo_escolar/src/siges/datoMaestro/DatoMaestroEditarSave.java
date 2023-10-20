package siges.datoMaestro;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletConfig;
import org.apache.commons.validator.*;
import java.io.IOException;
import siges.datoMaestro.beans.*;
import siges.dao.*;
import siges.util.Recursos;
import siges.login.beans.*;

/**
 * Nombre: DatoMaestroEditarSave.java <BR>
 * Descripcinn: Peticion desde DatoMaestroEditar.jsp y procesa la edicion de un registro de dato maestro<BR>
 * Funciones de la pngina: Usa el objeto DatoMaestroBean de la sesion para procesar los datos del registro <BR>
 * Entidades afectadas: La correspondiente al dato maestro que se especifica en el objeto DatoMaestroBean <BR>
 * Fecha de modificacinn: 20/07/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */
public class DatoMaestroEditarSave extends HttpServlet {

	private Cursor cursor;// objeto que maneja las sentencias sql

	private Util util;

	private GenericValidator validator;// validador del bean

	private Dao dao;

	/**
	 * Recibe la peticion por el metodo get de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);// redirecciona la peticion a doPost
	}

	/**
	 * Recibe la peticion por el metodo Post de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String texto[];// vector que contiene el texto digitado en los input de tipo 'TEXT'
		String lista[];// vector que contiene los items seleccionados de los 'select'
		String check[];// vector que contiene los items seleccionados de los 'select'
		String checksentencia[];// vector que contiene los items seleccionados de los 'select'
		String actualizargra[];// consulta sql para la actualizacion del registro
		String intensidad[];
		String[] codigo;
		String[] codigoj;
		String[] codigoje;
		String[] codigol;
		String[] codigod;
		String[][] matrix;
		String[][] matrixgrado;
		String[][] matrixgrado1;
		String[][] matrixgrado2;
		String institucion;
		String nivel;
		Login login;
		String metodologiaid;// variable que almacena el id de la metodologia de la institucion
		String metodologia;// variable que almacena el nombre de la metodologia de la institucion

		int dato;// variable que captura numero de dato maestro del que vamos a mostrar datos
		int validar;// variable que indica el tipo de validacion que se efectuara a los datos
		int id;// valor del ID para los datos maestros cuyo ID es generado automaticamente
		int posicion;
		String ant;// pagina a la que ira en caso de que no se pueda procesar esta pagina
		String sig;// nombre de la pagina a la que ira despues de ejecutar los comandos de esta
		String sigP;// nombre de la pagina a la que ira despues de ejecutar los comandos de esta
		String er;// nombre de la pagina a la que ira si hay errores
		String home;// nombre de la pagina a la que ira si hay errores
		String text;// variable que contiene el id del registro
		String text2;// variable que contiene el id del registro
		String text3;// variable que contiene el id del registro
		String text4;// variable que contiene el id del registro
		String text5;// variable que contiene el id del registro
		String text6;// variable que contiene el id del registro
		String text7;// variable que contiene el id del registro
		String text8;// variable que contiene el id del registro
		String text9;// variable que contiene el id del registro
		String boton;// captura el valor del boton pulsado en el formulario
		String insertar;// consulta sql para la insercion del nuevo registro
		String buscar;// consulta sql para validar la existencia de un registro igual
		String buscarhijo;// consulta sql para averiguar si el campo tiene registros asociados
		String buscarhijo1;// consulta sql para averiguar si el campo tiene registros asociados
		String buscarhijo2;// consulta sql para averiguar si el campo tiene registros asociados
		String buscarl;// consulta sql para validar la existencia de un registro igual
		String buscar2;// consulta sql para validar la existencia de un registro igual
		String buscargrado;// consulta sql para validar la existencia de un registro igual
		String buscargrado1;// consulta sql para validar la existencia de un registro igual
		String buscargrado2;// consulta sql para validar la existencia de un registro igual
		String buscarlogro;// consulta sql para validar la existencia de un logro igual
		String buscardesc;// consulta sql para validar la existencia de un logro igual
		String buscarcontenido;// consulta sql para validar la existencia de un logro igual
		String buscartema;// consulta sql para validar la existencia de un logro igual
		String searchcode;// consulta sql para encontrar el cndigo de la jerarquia
		String consecutivo;// consulta sql para buscar el valor del ID de las tablas maestras en la tabla 'consecutivoid'
		String actualizar;// consulta sql para la actualizacion del registro
		String actualizarg;// consulta sql para la actualizacion del registro
		String actualizar1;// consulta sql para la actualizacion del registro
		String actualizarj;// consulta sql para la actualizacion del registro
		String buscarhijastro;// consulta sql para averiguar si el campo tiene registros asociados
		String searchson[];// consulta sql para averiguar si el campo tiene registros asociados
		String searchrepeated[];// consulta sql para averiguar si el campo tiene registros asociados
		String buscarhijastro1;// consulta sql para averiguar si el campo tiene registros asociados
		String buscarhijastro2;// consulta sql para averiguar si el campo tiene registros asociados
		String buscarhijastro3;// consulta sql para averiguar si el campo tiene registros asociados
		String buscarhijastro4;// consulta sql para averiguar si el campo tiene registros asociados
		String buscarhijastro5;// consulta sql para averiguar si el campo tiene registros asociados
		String eliminar;// consulta sql para la eliminacion del registro
		String eliminar2;// consulta sql para la eliminacion del registro
		String eliminar3;// consulta sql para la eliminacion del registro
		String eliminarg;// consulta sql para la eliminacion del registro
		String eliminara;// consulta sql para la eliminacion del registro
		String eliminargrearea;
		String eliminarjer;// consulta sql para la eliminacion del registro
		String insertarjerarquia[];// consulta sql para insertar del registro
		String borrarjerarquia[];// consulta sql para insertar del registro
		String x, y, w;// valor de un select para hacer el siguiente select dinamico
		String lista1;
		String lista2;
		String lista3;
		String vigencia;
		String eliminar_transaccion[];
		String actualizar_transaccion[];
		String insertar_transaccion[];
		String r[];// vector que contiene todos los campos del registro a actualizar
		DatoMaestroBean datoMaestro;// objeto que tendra los datos referentes al dato maestro escogido
		validator = new GenericValidator();
		er = "/error.jsp";
		home = "/bienvenida.jsp";
		ant = "/datoMaestro/DatoMaestroEditarEdit.do?ext=1";// pagina a la que se dara el control si algo falla
		sig = "/datoMaestro/DatoMaestroNuevoEdit.do?ext=1";// pagina a la que se dara el control despues de procesar este servlet
		datoMaestro = (DatoMaestroBean) request.getSession().getAttribute("datoMaestro");

		if (datoMaestro == null) {
			ir(2, home, request, response);
			return;
		}
		boton = (request.getParameter("cmd") != null) ? request.getParameter("cmd") : new String("Guardar");
		validar = 0;
		consecutivo = null;
		actualizar = null;
		actualizargra = null;
		actualizar1 = null;
		actualizarg = null;
		insertarjerarquia = borrarjerarquia = null;
		codigo = codigoj = codigoje = codigol = codigod = null;
		matrix = matrixgrado = matrixgrado1 = matrixgrado2 = null;
		buscar = buscarhijo = buscarhijo1 = buscarhijo2 = buscarl = buscar2 = buscargrado = buscargrado1 = buscargrado2 = insertar = searchcode = null;
		buscarhijastro = buscarhijastro1 = buscarhijastro2 = buscarhijastro3 = buscarhijastro4 = buscarhijastro5 = null;
		buscarlogro = buscardesc = buscarcontenido = buscartema = null;
		eliminar = eliminar2 = eliminar3 = eliminarg = eliminara = eliminarjer = eliminargrearea = null;
		vigencia = null;
		eliminar_transaccion = actualizar_transaccion = insertar_transaccion = searchson = searchrepeated = null;

		try {

			cursor = new Cursor();
			util = new Util(cursor);
			dao = new Dao(cursor);
			login = (Login) request.getSession().getAttribute("login");

			lista = new String[9];
			texto = new String[10];
			eliminar_transaccion = new String[20];
			searchson = new String[20];
			searchrepeated = new String[20];
			actualizar_transaccion = new String[20];
			insertar_transaccion = new String[20];

			lista[1] = (request.getParameter("lista1") != null) ? request.getParameter("lista1") : new String("0");// captura el valor de el select cuyo nombre es 'lista1'
			lista[2] = (request.getParameter("lista2") != null) ? request.getParameter("lista2") : new String("0");// captura el valor de el select cuyo nombre es 'lista2'
			lista[3] = (request.getParameter("lista3") != null) ? request.getParameter("lista3") : new String("0");// captura el valor de el select cuyo nombre es 'lista3'
			lista[4] = (request.getParameter("lista4") != null) ? request.getParameter("lista4") : new String("0");// captura el valor de el select cuyo nombre es 'lista4'
			lista[5] = (request.getParameter("lista5") != null) ? request.getParameter("lista5") : new String("0");// captura el valor de el select cuyo nombre es 'lista5'
			lista[6] = (request.getParameter("lista6") != null) ? request.getParameter("lista6") : new String("0");// captura el valor de el select cuyo nombre es 'lista5'

			texto[1] = (request.getParameter("texto1") != null) ? request.getParameter("texto1").replace('\'', ' ') : new String("");// captura el valor de el input de tipo TEXT cuyo nombre es 'texto1'
			texto[2] = (request.getParameter("texto2") != null) ? request.getParameter("texto2").replace('\'', ' ') : new String("");// captura el valor de el input de tipo TEXT cuyo nombre es 'texto2'
			texto[3] = (request.getParameter("texto3") != null) ? request.getParameter("texto3").replace('\'', ' ') : new String("");// captura el valor de el input de tipo TEXT cuyo nombre es 'texto3'
			texto[4] = (request.getParameter("texto4") != null) ? request.getParameter("texto4").replace('\'', ' ') : new String("");// captura el valor de el input de tipo TEXT cuyo nombre es 'texto4'
			texto[5] = (request.getParameter("texto5") != null) ? request.getParameter("texto5").replace('\'', ' ') : new String("");// captura el valor de el input de tipo TEXT cuyo nombre es 'texto5'
			texto[6] = (request.getParameter("texto6") != null) ? request.getParameter("texto6").replace('\'', ' ') : new String("");// captura el valor de el input de tipo TEXT cuyo nombre es 'texto6'
			texto[7] = (request.getParameter("texto7") != null) ? request.getParameter("texto7").replace('\'', ' ') : new String("");// captura el valor de el input de tipo TEXT cuyo nombre es 'texto7'
			texto[8] = (request.getParameter("texto8") != null) ? request.getParameter("texto8") : new String("");// captura el valor de el input de tipo TEXT cuyo nombre es 'texto8'
			texto[9] = (request.getParameter("texto9") != null) ? request.getParameter("texto9") : new String("");// captura el valor de el input de tipo TEXT cuyo nombre es 'texto9'

			text = request.getParameter("text");
			text2 = request.getParameter("text2");
			text3 = request.getParameter("text3");
			text4 = request.getParameter("text4");
			text5 = request.getParameter("text5");
			text6 = request.getParameter("text6");
			text7 = request.getParameter("text7");
			text8 = request.getParameter("text8");
			text9 = request.getParameter("text9");

			x = (request.getParameter("x") != null) ? request.getParameter("x") : new String("");
			y = (request.getParameter("y") != null) ? request.getParameter("y") : new String("");
			w = (request.getParameter("z") != null) ? request.getParameter("z") : new String("");
			lista1 = (request.getParameter("lista1") != null) ? request.getParameter("lista1") : new String("");
			lista2 = (request.getParameter("lista2") != null) ? request.getParameter("lista2") : new String("");
			lista3 = (request.getParameter("lista3") != null) ? request.getParameter("lista3") : new String("");

			institucion = login.getInstId();
			nivel = login.getNivel();
			vigencia = dao.getVigencia();
			metodologia = login.getMetodologia();
			metodologiaid = login.getMetodologiaId();

			if (vigencia == null) {
				System.out.println("VIGENCIA NULA");
				ir(2, home, request, response);
				return;
			}

			check = request.getParameterValues("chk");
			intensidad = request.getParameterValues("ih");
			if (check != null)
				checksentencia = new String[check.length];

			dato = Integer.parseInt(datoMaestro.getDato());// numero del dato maestro al que hacemos referencia

			if (boton.equals("Eliminar")) {

				if (dato != 0) {

					switch (dato) {
					case 1:
						buscarhijastro = "select distinct jorcodigo from jornada where jorcodigo =" + text + "  and rownum<=1";
						eliminar = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar += "G_ConTipo = 5 and " + datoMaestro.getCampo(2) + "='" + text + "'";

						if (!buscarhijastro.equals("")) {
							if (cursor.existe(buscarhijastro)) {
								request.setAttribute("mensaje", setMensaje("No se puede eliminar, ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}

						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							Recursos.setRecurso(Recursos.JORNADA);
							ir(2, sig, request, response);
							return;
						} else {
							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 2:
						buscarhijastro = "select distinct estcodigo from estudiante where esttipodoc =" + text + " and rownum<=1";
						// construccion de la sentencia de eliminacion
						eliminar = " delete from " + datoMaestro.getTabla() + " where ";
						eliminar += " G_ConTipo = 10 and " + datoMaestro.getCampo(2) + "='" + text + "'";

						if (!buscarhijastro.equals("")) {
							if (cursor.existe(buscarhijastro)) {
								request.setAttribute("mensaje", setMensaje("No se puede eliminar, ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}

						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							Recursos.setRecurso(Recursos.TIPODOCUMENTO);
							ir(2, sig, request, response);
							return;
						} else {
							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 3:// Departamento en jerarquia
						searchson[1] = "select G_ConCodigo from G_Constante where G_contipo=7 and g_concodpadre =" + text + " and rownum<=1";
						searchson[2] = "select distinct g_jercodigo from g_jerarquia where g_jertipo=1 and g_jernivel=2 and g_jerdepto =" + text + " and rownum<=1";

						eliminar_transaccion[2] = " delete from " + datoMaestro.getTabla() + " where ";
						eliminar_transaccion[2] += " G_ConTipo = 6 and " + datoMaestro.getCampo(2) + "='" + text + "'";

						eliminar_transaccion[1] = " delete from g_jerarquia where G_JerDepto =" + text + " and g_jervigencia=" + vigencia;

						if (searchson != null) {
							if (cursor.existe1(searchson)) {
								request.setAttribute("mensaje", setMensaje("No se puede eliminar, ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}

						if (cursor.registrar_transaccion(eliminar_transaccion)) {
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							Recursos.setRecurso(Recursos.DEPARTAMENTO);
							ir(2, sig, request, response);
							return;
						} else {
							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 4:
						searchson[1] = "select distinct nivcodigo from nivel where nivcodigo =" + text + " and rownum<=1";
						searchson[2] = " select distinct g_gracodigo from g_grado where g_granivel=" + text + " and rownum<=1";
						// construccion de la sentencia de eliminacion
						eliminar = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar += "G_ConTipo = 1 and " + datoMaestro.getCampo(2) + "='" + text + "'";

						if (searchson != null) {
							if (cursor.existe1(searchson)) {
								request.setAttribute("mensaje", setMensaje("No se puede eliminar, ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}

						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							Recursos.setRecurso(Recursos.NIVEL);
							ir(2, sig, request, response);
							return;
						} else {
							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 5:
						// construccion de la sentencia de eliminacion
						eliminar = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar += "G_ConTipo = 2 and " + datoMaestro.getCampo(2) + "='" + text + "'";

						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							ir(2, sig, request, response);
							return;
						} else {
							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 6:// G_TipoEspacio
						buscarhijastro = "select distinct espcodigo from espacio_fisico where esptipo =" + text + " and rownum<=1";
						// construccion de la sentencia de eliminacion
						eliminar = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar += datoMaestro.getCampo(1) + "='" + text + "'";

						if (!buscarhijastro.equals("")) {
							if (cursor.existe(buscarhijastro)) {
								request.setAttribute("mensaje", setMensaje("No se puede eliminar, ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}

						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							Recursos.setRecurso(Recursos.TIPOESPACIO);
							ir(2, sig, request, response);
							return;
						} else {
							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 8:// G_TipoReconocimiento
						// construccion de la sentencia de eliminacion
						eliminar = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar += datoMaestro.getCampo(1) + "='" + text + "'";

						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							Recursos.setRecurso(Recursos.TIPORECONOCIMIENTO);
							ir(2, sig, request, response);
							return;
						} else {
							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 9:
						buscarhijastro = "select distinct inscodigo from institucion where insmetodologia =" + text + " and rownum<=1";
						// construccion de la sentencia de eliminacion
						eliminar = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar += "G_ConTipo = 3 and " + datoMaestro.getCampo(2) + "='" + text + "'";

						if (!buscarhijastro.equals("")) {
							if (cursor.existe(buscarhijastro)) {

								request.setAttribute("mensaje", setMensaje("No se puede eliminar, ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}

						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							Recursos.setRecurso(Recursos.METODOLOGIA);
							ir(2, sig, request, response);
							return;
						} else {

							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 10:// jerarquia
						// construccion de la sentencia de eliminacion
						eliminar_transaccion[2] = " delete from " + datoMaestro.getTabla() + " where ";
						eliminar_transaccion[2] += "G_ConTipo = 8 and " + datoMaestro.getCampo(2) + "='" + text + "'";

						eliminar_transaccion[1] = "delete from g_jerarquia where G_JerLocal =" + text + " and g_jervigencia=" + vigencia;

						if (cursor.registrar_transaccion(eliminar_transaccion)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							ir(2, sig, request, response);
							return;
						} else {

							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 11:
						buscarhijastro = "select distinct espcodigo from espacio_fisico where esptipoocupante =" + text + " and rownum<=1";
						// construccion de la sentencia de eliminacion
						eliminar = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar += "G_ConTipo = 9 and " + datoMaestro.getCampo(2) + "='" + text + "'";

						if (!buscarhijastro.equals("")) {
							if (cursor.existe(buscarhijastro)) {

								request.setAttribute("mensaje", setMensaje("No se puede eliminar, ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}

						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							Recursos.setRecurso(Recursos.TIPOOCUPANTE);
							ir(2, sig, request, response);
							return;
						} else {

							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}
					// break;

					case 12:
						// construccion de la sentencia de eliminacion
						eliminar = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar += "G_ConTipo = 4 and " + datoMaestro.getCampo(2) + "='" + text + "'";
						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							Recursos.setRecurso(Recursos.ESPECIALIDAD);
							ir(2, sig, request, response);
							return;
						} else {

							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 13:
						buscarhijastro = "select distinct estcodigo from estudiante where estcondicion =" + text + " and rownum<=1";
						// construccion de la sentencia de eliminacion
						eliminar = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar += datoMaestro.getCampo(1) + "='" + text + "'";

						if (!buscarhijastro.equals("")) {
							if (cursor.existe(buscarhijastro)) {

								request.setAttribute("mensaje", setMensaje("No se puede eliminar, ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}

						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							Recursos.setRecurso(Recursos.CONDICION);
							ir(2, sig, request, response);
							return;
						} else {

							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 14:
						buscarhijastro = "select distinct concodigo from convivencia where contipo =" + text + " and rownum<=1";
						// construccion de la sentencia de eliminacion
						eliminar = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar += "G_ConTipo = 11 and " + datoMaestro.getCampo(2) + "='" + text + "'";

						if (!buscarhijastro.equals("")) {
							if (cursor.existe(buscarhijastro)) {

								request.setAttribute("mensaje", setMensaje("No se puede eliminar, ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}

						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							Recursos.setRecurso(Recursos.CONVIVENCIA);
							ir(2, sig, request, response);
							return;
						} else {

							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 15:
						// construccion de la sentencia de eliminacion
						eliminar = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar += "G_ConTipo = 12 and " + datoMaestro.getCampo(2) + "='" + text + "'";

						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							Recursos.setRecurso(Recursos.DISCAPACIDAD);
							ir(2, sig, request, response);
							return;
						} else {

							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 16:
						// construccion de la sentencia de eliminacion
						eliminar = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar += "G_ConTipo = 13 and " + datoMaestro.getCampo(2) + "='" + text + "'";

						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							Recursos.setRecurso(Recursos.CAPACIDAD);
							ir(2, sig, request, response);
							return;
						} else {

							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 17:
						buscarhijastro = "select distinct estcodigo from estudiante where estetnia =" + text + " and rownum<=1";
						// construccion de la sentencia de eliminacion
						eliminar = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar += "G_ConTipo = 14 and " + datoMaestro.getCampo(2) + "='" + text + "'";

						if (!buscarhijastro.equals("")) {
							if (cursor.existe(buscarhijastro)) {

								request.setAttribute("mensaje", setMensaje("No se puede eliminar, ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}

						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							Recursos.setRecurso(Recursos.ETNIA);
							ir(2, sig, request, response);
							return;
						} else {

							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 18:
						searchson[1] = "select distinct evaarecodjerar from evaluacion_area where (evaaremotaus1=" + text + " or evaaremotaus2=" + text + " or evaaremotaus3=" + text + " or evaaremotaus4  =" + text + " or evaaremotaus5  =" + text + " ) and rownum<=1";
						searchson[2] = "select distinct evaasicodjerar from evaluacion_asignatura where (evaasimotaus1=" + text + " or evaasimotaus2=" + text + " or evaasimotaus3=" + text + " or evaasimotaus4=" + text + " or evaasimotaus5=" + text + ") and rownum<=1";
						// construccion de la sentencia de eliminacion
						eliminar = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar += "G_ConTipo = 15 and " + datoMaestro.getCampo(2) + "='" + text + "'";

						if (searchson != null) {
							if (cursor.existe1(searchson)) {

								request.setAttribute("mensaje", setMensaje("No se puede eliminar, ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}

						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							Recursos.setRecurso(Recursos.AUSENCIA);
							ir(2, sig, request, response);
							return;
						} else {

							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 19:
						// construccion de la sentencia de eliminacion
						eliminar = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar += "G_ConTipo = 16 and " + datoMaestro.getCampo(2) + "='" + text + "'";

						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							Recursos.setRecurso(Recursos.PROGRAMA);
							ir(2, sig, request, response);
							return;
						} else {

							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 21:
						// construccion de la sentencia de eliminacion
						searchson[1] = "select distinct g_jercodigo from g_jerarquia where g_jertipo=1 and g_jernivel>2 and g_jermunic =" + text + " and rownum<=1";

						eliminar_transaccion[2] = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar_transaccion[2] += "G_ConTipo = 7 and " + datoMaestro.getCampo(2) + "='" + text + "'";

						eliminar_transaccion[1] = "delete from g_jerarquia where G_JerMunic =" + text + " and g_jervigencia= " + vigencia;

						if (searchson != null) {
							if (cursor.existe1(searchson)) {

								request.setAttribute("mensaje", setMensaje("No se puede eliminar, ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}

						if (cursor.registrar_transaccion(eliminar_transaccion)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							Recursos.setRecurso(Recursos.LOCALIDAD);
							ir(2, sig, request, response);
							return;
						} else {

							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 22:// area
						searchson[1] = " select distinct arecodigo from area where arecodigo =" + text + " and rownum<=1";
						searchson[2] = " select distinct g_asicodigo from g_asignatura where g_asicodarea= " + text + " and rownum<=1";
						// construccion de la sentencia de eliminacion
						eliminar = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar += datoMaestro.getCampo(1) + "='" + text + "'";

						if (searchson != null) {
							if (cursor.existe1(searchson)) {

								request.setAttribute("mensaje", setMensaje("No se puede eliminar, ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}

						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							ir(2, sig, request, response);
							return;
						} else {

							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 23:// G_Grado
						buscarhijastro = "select distinct gracodigo from grado where gracodigo =" + text + " and rownum<=1";
						// construccion de la sentencia de eliminacion
						eliminar = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar += datoMaestro.getCampo(2) + "='" + text + "'";

						if (!buscarhijastro.equals("")) {
							if (cursor.existe(buscarhijastro)) {

								request.setAttribute("mensaje", setMensaje("No se puede eliminar, ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}

						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							Recursos.setRecurso(Recursos.GRADO);
							ir(2, sig, request, response);
							return;
						} else {

							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}
					// break;

					case 24:
						buscarhijastro = "select distinct inscodigo from institucion where inscalendario =" + text + " and rownum<=1";
						// construccion de la sentencia de eliminacion
						eliminar = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar += "G_ConTipo = 17 and " + datoMaestro.getCampo(2) + "='" + text + "'";

						if (!buscarhijastro.equals("")) {
							if (cursor.existe(buscarhijastro)) {

								request.setAttribute("mensaje", setMensaje("No se puede eliminar, ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}

						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							Recursos.setRecurso(Recursos.CALENDARIO);
							ir(2, sig, request, response);
							return;
						} else {

							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 25:
						buscarhijastro = "select distinct inscodigo from institucion where inspropiedad =" + text + " and rownum<=1";
						// construccion de la sentencia de eliminacion
						eliminar = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar += "G_ConTipo = 18 and " + datoMaestro.getCampo(2) + "='" + text + "'";

						if (!buscarhijastro.equals("")) {
							if (cursor.existe(buscarhijastro)) {

								request.setAttribute("mensaje", setMensaje("No se puede eliminar, ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}

						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							Recursos.setRecurso(Recursos.PROPIEDAD);
							ir(2, sig, request, response);
							return;
						} else {

							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 26:
						buscarhijastro = "select distinct inscodigo from institucion where insdiscapacidad =" + text + " and rownum<=1";
						// construccion de la sentencia de eliminacion
						eliminar = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar += "G_ConTipo = 19 and " + datoMaestro.getCampo(2) + "='" + text + "'";

						if (!buscarhijastro.equals("")) {
							if (cursor.existe(buscarhijastro)) {

								request.setAttribute("mensaje", setMensaje("No se puede eliminar, ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}

						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							Recursos.setRecurso(Recursos.DISCAPACIDADINSTITUCION);
							ir(2, sig, request, response);
							return;
						} else {

							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 27:// G_Nucleo
						// construccion de la sentencia de eliminacion
						eliminar_transaccion[2] = " delete from " + datoMaestro.getTabla() + " where ";
						eliminar_transaccion[2] += datoMaestro.getCampo(2) + "='" + text + "'";

						eliminar_transaccion[1] = "delete from g_jerarquia where G_JerLocal =" + text + " and g_jervigencia=" + vigencia;
						buscarhijastro1 = "select distinct g_jercodigo from g_jerarquia where g_jertipo=1 and g_jernivel>3 and g_jerlocal =" + text + " and rownum<=1";

						if (!buscarhijastro1.equals("")) {
							if (cursor.existe(buscarhijastro1)) {

								request.setAttribute("mensaje", setMensaje("No se puede eliminar, ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}

						if (cursor.registrar_transaccion(eliminar_transaccion)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							Recursos.setRecurso(Recursos.NUCLEO);
							ir(2, sig, request, response);
							return;
						} else {

							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 29:
						buscarhijastro = "select distinct asicodigo from asignatura where asicodigo =" + text + " and rownum<=1";
						// construccion de la sentencia de eliminacion
						eliminar = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar += datoMaestro.getCampo(2) + "='" + text + "'";

						if (!buscarhijastro.equals("")) {
							if (cursor.existe(buscarhijastro)) {

								request.setAttribute("mensaje", setMensaje("No se puede eliminar, ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}

						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							ir(2, sig, request, response);
							return;
						} else {

							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 28:// áreas por institucinn

						searchson[1] = "select distinct asicodigo from asignatura where asicodinst=" + institucion + " and asicodmetod=" + metodologiaid + " and asicodarea='" + text2 + "' and asivigencia=" + vigencia + " and rownum<=1";

						searchson[2] = " select distinct evaarecodjerar from evaluacion_area,g_jerarquia_tipo2_nivel4 where evaarecodjerar=codigo_jerarquia and evaarevigencia=" + vigencia + "";
						searchson[2] += " and vigencia=evaarevigencia and cod_institucion=" + institucion + " and cod_metod='" + text + "' and cod_area='" + text2 + "' and rownum<=1";

						searchson[3] = "select distinct codigo_jerarquia from g_jerarquia_tipo2_nivel4,descriptor_valorativo where cod_institucion=" + institucion;
						searchson[3] += " and descodjerar=codigo_jerarquia";
						searchson[3] += " and cod_metod='" + metodologiaid + "' and cod_area=" + text2 + " and vigencia='" + vigencia + "' and desvigencia=vigencia and rownum<=1";

						eliminar_transaccion[1] = "delete from g_jerarquia where G_JerTipo=2 and G_JerNivel=4 and G_JerInst=" + institucion + " and G_JerMetod='" + metodologiaid + "' and G_JerAreaAsig ='" + text2 + "' and G_JerVigencia=" + vigencia;
						eliminar_transaccion[2] = "delete from grado_area where GraAreCodInst= " + institucion + " and GraAreCodMetod='" + metodologiaid + "' and GraAreCodArea='" + text2 + "' and GraAreVigencia=" + vigencia;
						// construccion de la sentencia de eliminacion
						eliminar_transaccion[3] = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar_transaccion[3] += "AreCodInst=" + institucion + " and AreCodMetod='" + metodologiaid + "' and AreCodigo='" + text2 + "' and AreVigencia=" + vigencia;

						if (searchson != null) {
							if (cursor.existe1(searchson)) {

								request.setAttribute("mensaje", setMensaje("No se puede eliminar, ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}
						if (cursor.registrar_transaccion(eliminar_transaccion)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							ir(2, sig, request, response);
							return;
						} else {

							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 30:// Asignaturas
						searchson[1] = "select distinct codigo_jerarquia from g_jerarquia_tipo3_nivel4,contenido where cod_institucion=" + institucion;
						searchson[1] += " and concodjerar=codigo_jerarquia";
						searchson[1] += " and cod_metod='" + metodologiaid + "' and cod_asignatura=" + text2 + " and vigencia='" + vigencia + "' and rownum<=1";

						searchson[2] = " select distinct codigo_jerarquia from g_jerarquia_tipo3_nivel4,logro where cod_institucion=" + institucion;
						searchson[2] += " and logcodjerar=codigo_jerarquia";
						searchson[2] += " and cod_metod=" + metodologiaid + " and cod_asignatura=" + text2;
						searchson[2] += " and logvigencia=" + vigencia + " and vigencia=logvigencia and rownum<=1";

						searchson[3] = " select distinct evaasicodjerar from evaluacion_asignatura,g_jerarquia_tipo3_nivel4 where evaasicodjerar=codigo_jerarquia and evaasivigencia=" + vigencia + "";
						searchson[3] += " and vigencia=evaasivigencia and cod_institucion=" + institucion + " and cod_metod='" + metodologiaid + "' and cod_asignatura='" + text2 + "' and rownum<=1";

						eliminar_transaccion[1] = "delete from g_jerarquia where G_JerTipo=3 and G_JerNivel=4 and G_JerInst=" + institucion + " and G_JerMetod='" + metodologiaid + "' and G_JerAreaAsig =" + text2 + " and G_JerVigencia=" + vigencia;

						eliminar_transaccion[2] = "delete from grado_asignatura where GraAsiCodInst=" + institucion + " and GraAsiCodMetod='" + metodologiaid + "' and GraAsiCodAsign=" + text2 + " and GraAsiVigencia=" + vigencia;

						eliminar_transaccion[3] = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar_transaccion[3] += " AsiCodInst=" + institucion + " and AsiCodMetod='" + metodologiaid + "'and AsiCodigo=" + text2 + " and AsiVigencia=" + vigencia;

						if (searchson != null) {
							if (cursor.existe1(searchson)) {

								request.setAttribute("mensaje", setMensaje("No se puede eliminar, ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}

						if (cursor.registrar_transaccion(eliminar_transaccion)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							ir(2, sig, request, response);
							return;
						} else {

							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 31:

						eliminar = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar += " PlaCodInst=" + institucion + " and PlaCodMetod=" + metodologiaid + " and PlaVigencia=" + vigencia;

						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							ir(2, sig, request, response);
							return;
						} else {

							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 37:
						// construccion de la sentencia de eliminacion
						buscarhijastro = " select distinct evalogcodlogro from evaluacion_logro where evalogcodjerar='" + text2 + "' and evalogcodlogro ='" + text + "' and evalogvigencia=" + vigencia + " and rownum<=1";

						eliminar = " delete from LOGRO where ";
						eliminar += " LogCodJerar='" + text2 + "' and LogCodigo='" + text + "' and LogVigencia=" + vigencia;

						if (!buscarhijastro.equals("")) {
							if (cursor.existe(buscarhijastro)) {

								request.setAttribute("mensaje", setMensaje("No se puede eliminar, ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}

						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							ir(2, sig, request, response);
							return;
						} else {

							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 39:
						// construccion de la sentencia de eliminacion
						buscarhijastro = " select distinct EVADESCODDESC from evaluacion_descriptor where EVADESCODJERAR='" + text2 + "' AND EVADESCODDESC ='" + text + "' and evadesvigencia=" + vigencia + " and rownum<=1";

						eliminar = "delete from descriptor_valorativo where ";
						eliminar += " DesCodJerar ='" + text2 + "' and DesCodigo='" + text + "' and DesVigencia= " + vigencia;

						if (!buscarhijastro.equals("")) {
							if (cursor.existe(buscarhijastro)) {

								request.setAttribute("mensaje", setMensaje("No se puede eliminar, ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}

						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							ir(2, sig, request, response);
							return;
						} else {

							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 40:
						buscarhijastro = "select distinct descodigo from descriptor_valorativo where descodtipdes =" + text + " and desvigencia= " + vigencia + " and rownum<=1";
						// construccion de la sentencia de eliminacion
						eliminar = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar += datoMaestro.getCampo(1) + " ='" + text + "'";

						if (!buscarhijastro.equals("")) {
							if (cursor.existe(buscarhijastro)) {

								request.setAttribute("mensaje", setMensaje("No se puede eliminar, ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}

						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							ir(2, sig, request, response);
							return;
						} else {

							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 42:// Escala Valorativa
						searchson[1] = "select distinct evaarecodestu from evaluacion_area where (evaareeval1 =" + text + " or evaareeval2 =" + text + " or evaareeval3 =" + text + " or evaareeval4 =" + text + " or evaareeval5 =" + text + ") and evaarevigencia=" + vigencia + " and rownum<=1";
						searchson[2] = "select distinct evaasicodestud from evaluacion_asignatura where (evaasieval1 =" + text + " or evaasieval2 =" + text + " or evaasieval3 =" + text + " or evaasieval4 =" + text + " or evaasieval5 =" + text + ") and evaasivigencia=" + vigencia + " and rownum<=1";
						searchson[3] = "select distinct evalogcodestu from evaluacion_logro where evalogevalu =" + text + " and evalogvigencia=" + vigencia + " and rownum<=1";
						// construccion de la sentencia de eliminacion
						eliminar = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar += datoMaestro.getCampo(1) + " ='" + text + "'";

						if (searchson != null) {
							if (cursor.existe1(searchson)) {

								request.setAttribute("mensaje", setMensaje("No se puede eliminar, ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}

						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							ir(2, sig, request, response);
							return;
						} else {

							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 43:
						// construccion de la sentencia de eliminacion
						buscarhijastro = "select distinct esccodigo from Escala_Valorativa where EscTipo =" + text + " and rownum<=1";

						eliminar = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar += "G_ConTipo = 23 and " + datoMaestro.getCampo(2) + "='" + text + "'";

						if (!buscarhijastro.equals("")) {
							if (cursor.existe(buscarhijastro)) {

								request.setAttribute("mensaje", setMensaje("No se puede eliminar, ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}

						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							ir(2, sig, request, response);
							return;
						} else {

							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 44:
						buscarhijastro = "select distinct inscodigo from institucion where instarifacostos =" + text + " and rownum<=1";
						// construccion de la sentencia de eliminacion
						eliminar = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar += "G_ConTipo = 20 and " + datoMaestro.getCampo(2) + "='" + text + "'";

						if (!buscarhijastro.equals("")) {
							if (cursor.existe(buscarhijastro)) {

								request.setAttribute("mensaje", setMensaje("No se puede eliminar, ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}

						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							Recursos.setRecurso(Recursos.TARIFA);
							ir(2, sig, request, response);
							return;
						} else {

							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 45:
						buscarhijastro = "select distinct inscodigo from institucion where insasocprivada =" + text + " and rownum<=1";
						// construccion de la sentencia de eliminacion
						eliminar = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar += "G_ConTipo = 21 and " + datoMaestro.getCampo(2) + "='" + text + "'";

						if (!buscarhijastro.equals("")) {
							if (cursor.existe(buscarhijastro)) {

								request.setAttribute("mensaje", setMensaje("No se puede eliminar, ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}

						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							Recursos.setRecurso(Recursos.ASOCIACION);
							ir(2, sig, request, response);
							return;
						} else {

							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 46:
						buscarhijastro = "select distinct inscodigo from institucion where insidioma =" + text + " and rownum<=1";
						// construccion de la sentencia de eliminacion
						eliminar = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar += "G_ConTipo = 22 and " + datoMaestro.getCampo(2) + "='" + text + "'";

						if (!buscarhijastro.equals("")) {
							if (cursor.existe(buscarhijastro)) {

								request.setAttribute("mensaje", setMensaje("No se puede eliminar, ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}

						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							Recursos.setRecurso(Recursos.IDIOMA);
							ir(2, sig, request, response);
							return;
						} else {

							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 47:
						// construccion de la sentencia de eliminacion
						eliminar = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar += "G_ConTipo = 24 and " + datoMaestro.getCampo(2) + "='" + text + "'";

						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							Recursos.setRecurso(Recursos.TIPOCARGO);
							ir(2, sig, request, response);
							return;
						} else {

							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 50:// ROT_TIPO_RECESO
						buscarhijastro = "select distinct ROTREC_TIPREC from ROT_RECESO WHERE ROTREC_TIPREC=" + text + " and rownum<=1";
						// construccion de la sentencia de eliminacion
						eliminar = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar += datoMaestro.getCampo(1) + "='" + text + "'";

						if (!buscarhijastro.equals("")) {
							if (cursor.existe(buscarhijastro)) {

								request.setAttribute("mensaje", setMensaje("No se puede eliminar, ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}
						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							Recursos.setRecurso(Recursos.TIPORECESO);
							ir(2, sig, request, response);
							return;
						} else {

							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}
					// break;

					case 49:// G_Constante tipo atencinn especial
						buscarhijastro = "select distinct TIPOATENCION from ATENCION_ESPECIAL WHERE TIPOATENCION=" + text + " and rownum<=1";
						// construccion de la sentencia de eliminacion
						eliminar = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar += "G_ConTipo = 31 and " + datoMaestro.getCampo(2) + "='" + text + "'";

						if (!buscarhijastro.equals("")) {
							if (cursor.existe(buscarhijastro)) {

								request.setAttribute("mensaje", setMensaje("No se puede eliminar, ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}

						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							Recursos.setRecurso(Recursos.TIPOATENCIONESPECIAL);
							ir(2, sig, request, response);
							return;
						} else {

							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 51:// G_Constante tipo especialidad
						buscarhijastro = "select distinct ESPCODESP from ESPECIALIDAD WHERE ESPCODESP=" + text + " and rownum<=1";
						// construccion de la sentencia de eliminacion
						eliminar = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar += "G_ConTipo = 34 and " + datoMaestro.getCampo(2) + "='" + text + "'";

						if (!buscarhijastro.equals("")) {
							if (cursor.existe(buscarhijastro)) {

								request.setAttribute("mensaje", setMensaje("No se puede eliminar, ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}

						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							Recursos.setRecurso(Recursos.TIPOESPECIALIDAD);
							ir(2, sig, request, response);
							return;
						} else {

							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 52:// G_Constante Tipos de Modalidad
						buscarhijastro = "select distinct INSMODALIDAD from INSTITUCION WHERE INSMODALIDAD=" + text + " and rownum<=1";
						// construccion de la sentencia de eliminacion
						eliminar = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar += "G_ConTipo = 33 and " + datoMaestro.getCampo(2) + "='" + text + "'";

						if (!buscarhijastro.equals("")) {
							if (cursor.existe(buscarhijastro)) {

								request.setAttribute("mensaje", setMensaje("No se puede eliminar, ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}

						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							Recursos.setRecurso(Recursos.TIPOMODALIDAD);
							ir(2, sig, request, response);
							return;
						} else {

							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					case 53:// G_Constante Tipos de nnfasis
						buscarhijastro = "select distinct INSENFASIS from INSTITUCION WHERE INSENFASIS=" + text + " and rownum<=1";
						// construccion de la sentencia de eliminacion
						eliminar = "delete from " + datoMaestro.getTabla() + " where ";
						eliminar += "G_ConTipo = 32 and " + datoMaestro.getCampo(2) + "='" + text + "'";

						if (!buscarhijastro.equals("")) {
							if (cursor.existe(buscarhijastro)) {

								request.setAttribute("mensaje", setMensaje("No se puede eliminar, ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}

						if (cursor.registrar(eliminar)) {
							request.setAttribute("ok", "ok");
							request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
							Recursos.setRecurso(Recursos.TIPOENFASIS);
							ir(2, sig, request, response);
							return;
						} else {

							request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
							ir(2, ant, request, response);
							return;
						}

					// break;

					}
				}
			}/* Boton Eliminar */

			if (boton.equals("Guardar")) {

				switch (dato) {

				case 1: // G_Constante Jornada
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden from G_Constante ";
					buscar += "where G_ConTipo = 5 and " + datoMaestro.getCampo(2) + " = " + text;
					// obtencion del registro
					r = cursor.nombre(buscar, 1, 4);

					// construccion de la consulta del busqueda
					buscar = "select * from " + datoMaestro.getTabla() + " where G_ConTipo = 5 and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) = lower(ltrim(rtrim('" + texto[2] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) <> lower(ltrim(rtrim('" + r[2] + "')))";

					buscarl = "select * from " + datoMaestro.getTabla() + " where G_ConTipo = 5 and lower(ltrim(rtrim(" + datoMaestro.getCampo(5) + "))) = lower(ltrim(rtrim('" + texto[3] + "')))";
					buscarl += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(5) + "))) <> lower(ltrim(rtrim('" + r[3] + "')))";

					// construccion de la consulta de actualizacion
					actualizar = "update " + datoMaestro.getTabla() + " set ";
					actualizar += datoMaestro.getCampo(6) + " =ltrim(rtrim('" + texto[4] + "')), ";
					actualizar += datoMaestro.getCampo(5) + " =ltrim(rtrim('" + texto[3] + "')), ";
					actualizar += datoMaestro.getCampo(4) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[1] + "')) ";
					actualizar += "where G_ConTipo = 5 and " + datoMaestro.getCampo(2) + " = " + text;

					// busqueda de registros repetidos
					if (cursor.existe(buscar) || cursor.existe(buscarl)) {

						request.setAttribute("mensaje", setMensaje("El nombre o abreviatura indicados ya existen, cambielos"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[2].equals(texto[2]) && r[3].equals(texto[3]) && r[4].equals(texto[4]))
							ir(2, ant, request, response);
						else {
							// actualizacion del registro
							if (cursor.registrar(actualizar)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								Recursos.setRecurso(Recursos.JORNADA);
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
								ir(2, ant, request, response);
								return;
							}
						}
					}

					break;

				case 2: // G_Constante TipoDocumento
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden from G_Constante ";
					buscar += "where G_ConTipo = 10 and " + datoMaestro.getCampo(2) + " = " + text;

					// obtencion del registro
					r = cursor.nombre(buscar, 1, 4);

					// construccion de la consulta del busqueda
					buscar = "select * from " + datoMaestro.getTabla() + " where G_ConTipo = 10 and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) = lower(ltrim(rtrim('" + texto[2] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) <> lower(ltrim(rtrim('" + r[2] + "')))";

					// construccion de la consulta del busqueda
					buscarl = "select * from " + datoMaestro.getTabla() + " where G_ConTipo = 10 and lower(ltrim(rtrim(" + datoMaestro.getCampo(5) + "))) = lower(ltrim(rtrim('" + texto[3] + "')))";
					buscarl += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(5) + "))) <> lower(ltrim(rtrim('" + r[3] + "')))";

					// construccion de la consulta de actualizacion
					actualizar = "update G_Constante set ";
					actualizar += datoMaestro.getCampo(6) + " =ltrim(rtrim('" + texto[4] + "')), ";
					actualizar += datoMaestro.getCampo(5) + " =ltrim(rtrim('" + texto[3] + "')), ";
					actualizar += datoMaestro.getCampo(4) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[1] + "')) ";
					actualizar += "where G_ConTipo = 10 and " + datoMaestro.getCampo(2) + " = " + text;

					// busqueda de registros repetidos
					if (cursor.existe(buscar) || cursor.existe(buscarl)) {

						request.setAttribute("mensaje", setMensaje("El nombre o abreviatura indicados ya existen, cambielos"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[2].equals(texto[2]) && r[3].equals(texto[3]) && r[4].equals(texto[4]))
							ir(2, ant, request, response);
						else {
							// actualizacion del registro
							if (cursor.registrar(actualizar)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								Recursos.setRecurso(Recursos.TIPODOCUMENTO);
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
								ir(2, ant, request, response);
								return;
							}
						}
					}

					break;

				case 3: // G_Constante Departamento
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden from G_Constante ";
					buscar += "where G_ConTipo = 6 and " + datoMaestro.getCampo(2) + " = " + text;

					// obtencion del registro
					r = cursor.nombre(buscar, 1, 4);

					// construccion de la consulta del busqueda
					buscar = "select * from " + datoMaestro.getTabla() + " where G_ConTipo = 6 and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) = lower(ltrim(rtrim('" + texto[2] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) <> lower(ltrim(rtrim('" + r[2] + "')))";

					// construccion de la consulta del busqueda
					buscarl = "select * from " + datoMaestro.getTabla() + " where G_ConTipo = 6 and lower(ltrim(rtrim(" + datoMaestro.getCampo(5) + "))) = lower(ltrim(rtrim('" + texto[3] + "')))";
					buscarl += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(5) + "))) <> lower(ltrim(rtrim('" + r[3] + "')))";

					// construccion de la consulta de actualizacion
					actualizar_transaccion[1] = "update G_Constante set ";
					actualizar_transaccion[1] += datoMaestro.getCampo(6) + " =ltrim(rtrim('" + texto[4] + "')), ";
					actualizar_transaccion[1] += datoMaestro.getCampo(5) + " =ltrim(rtrim('" + texto[3] + "')), ";
					actualizar_transaccion[1] += datoMaestro.getCampo(4) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar_transaccion[1] += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[1] + "')) ";
					actualizar_transaccion[1] += "where G_ConTipo = 6 and " + datoMaestro.getCampo(2) + " = " + text;

					// actualizacinn en Municipio
					actualizar_transaccion[2] = " update G_Constante set ";
					actualizar_transaccion[2] += " G_ConCodPadre = ltrim(rtrim('" + texto[1] + "')) ";
					actualizar_transaccion[2] += " where G_ConCodPadre=" + r[1];

					// busqueda de registros repetidos
					if (cursor.existe(buscar) || cursor.existe(buscarl)) {

						request.setAttribute("mensaje", setMensaje("El nombre o abreviatura indicados ya existen, cambielos"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[2].equals(texto[2]) && r[3].equals(texto[3]) && r[4].equals(texto[4]))
							ir(2, ant, request, response);
						else {
							// actualizacion del registro
							if (cursor.registrar_transaccion(actualizar_transaccion)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								Recursos.setRecurso(Recursos.DEPARTAMENTO);
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
								ir(2, ant, request, response);
								return;
							}
						}
					}
					break;

				case 4: // G_Constante Nivel
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden from G_Constante ";
					buscar += "where G_ConTipo = 1 and " + datoMaestro.getCampo(2) + " = " + text;

					// obtencion del registro
					r = cursor.nombre(buscar, 1, 4);

					// construccion de la consulta del busqueda
					buscar = "select * from " + datoMaestro.getTabla() + " where G_ConTipo = 1 and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) = lower(ltrim(rtrim('" + texto[2] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) <> lower(ltrim(rtrim('" + r[2] + "')))";

					// construccion de la consulta de actualizacion
					actualizar = "update G_Constante set ";
					actualizar += datoMaestro.getCampo(6) + " =ltrim(rtrim('" + texto[4] + "')), ";
					actualizar += datoMaestro.getCampo(5) + " =ltrim(rtrim('" + texto[3] + "')), ";
					actualizar += datoMaestro.getCampo(4) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[1] + "')) ";
					actualizar += "where G_ConTipo = 1 and " + datoMaestro.getCampo(2) + " = " + text;

					// busqueda de registros repetidos
					if (cursor.existe(buscar)) {

						request.setAttribute("mensaje", setMensaje("El nombre indicado ya existe, cambielo"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[2].equals(texto[2]) && r[3].equals(texto[3]) && r[4].equals(texto[4]))
							ir(2, ant, request, response);
						else {
							// actualizacion del registro
							if (cursor.registrar(actualizar)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								Recursos.setRecurso(Recursos.NIVEL);
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
								ir(2, ant, request, response);
								return;
							}
						}
					}

					break;

				case 5: // G_Constante GRADO
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden from G_Constante ";
					buscar += "where G_ConTipo = 2 and " + datoMaestro.getCampo(2) + " = " + text;

					// obtencion del registro
					r = cursor.nombre(buscar, 1, 4);
					// construccion de la consulta del busqueda

					buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev from G_Constante";
					buscar += "where G_ConTipo = 2 and lower(ltrim(rtrim(";
					buscar += datoMaestro.getCampo(4) + "))) = lower(ltrim(rtrim('" + texto[2] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(2) + "))) <> lower(ltrim(rtrim('" + text + "')))";
					buscar += " or lower(ltrim(rtrim(" + datoMaestro.getCampo(2) + ")))=lower(ltrim(rtrim('" + texto[1] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) <> lower(ltrim(rtrim('" + r[2] + "')))";

					// construccion de la consulta de actualizacion
					actualizar = "update G_Constante set ";
					actualizar += datoMaestro.getCampo(6) + " =ltrim(rtrim('" + texto[4] + "')), ";
					actualizar += datoMaestro.getCampo(5) + " =ltrim(rtrim('" + texto[3] + "')), ";
					actualizar += datoMaestro.getCampo(4) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[1] + "')) ";
					actualizar += "where G_ConTipo = 2 and " + datoMaestro.getCampo(2) + " = " + text;
					// System.out.println(texto[4]);
					// busqueda de registros repetidos
					if (cursor.existe(buscar)) {

						request.setAttribute("mensaje", setMensaje("El nombre y/o cndigo indicado ya existen, cambielo"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[2].equals(texto[2]) && r[3].equals(texto[3]) && r[4].equals(texto[4]))
							ir(2, sig, request, response);
						else {
							// actualizacion del registro
							if (cursor.registrar(actualizar)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje("No se pudo actualizar ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}
					}

					break;

				case 6:// G_TipoEspacio
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select * from " + datoMaestro.getTabla() + " where lower(ltrim(rtrim(";
					buscar += datoMaestro.getCampo(1) + "))) = lower(ltrim(rtrim('" + text + "')))";
					// obtencion del registro
					r = cursor.nombre(buscar, 1, 2);

					// construccion de la consulta del busqueda
					buscar = "select * from " + datoMaestro.getTabla() + " where lower(ltrim(rtrim(";
					buscar += datoMaestro.getCampo(2) + "))) = lower(ltrim(rtrim('" + texto[2] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(1) + "))) <> lower(ltrim(rtrim('" + text + "')))";
					buscar += " or lower(ltrim(rtrim(" + datoMaestro.getCampo(1) + ")))=lower(ltrim(rtrim('" + texto[1] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(2) + "))) <> lower(ltrim(rtrim('" + r[2] + "')))";

					// construccion de la consulta de actualizacion
					actualizar = "update " + datoMaestro.getTabla() + " set ";
					actualizar += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar += datoMaestro.getCampo(1) + "=ltrim(rtrim('" + texto[1] + "')) ";
					actualizar += " where " + datoMaestro.getCampo(1) + " = '" + text + "'";

					// busqueda de registros repetidos
					if (cursor.existe(buscar)) {

						request.setAttribute("mensaje", setMensaje("El nombre y/o cndigo indicado ya existen, cambielo"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[2].equals(texto[2]))
							ir(2, ant, request, response);
						else {
							// actualizacion del registro
							if (cursor.registrar(actualizar)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								Recursos.setRecurso(Recursos.TIPOESPACIO);
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje("No se pudo actualizar ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}
					}

					break;

				case 8:// G_TipoReconocimiento
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select * from " + datoMaestro.getTabla() + " where lower(ltrim(rtrim(";
					buscar += datoMaestro.getCampo(1) + "))) = lower(ltrim(rtrim('" + text + "')))";
					// obtencion del registro
					r = cursor.nombre(buscar, 1, 2);

					// construccion de la consulta del busqueda
					buscar = "select * from " + datoMaestro.getTabla() + " where lower(ltrim(rtrim(";
					buscar += datoMaestro.getCampo(2) + "))) = lower(ltrim(rtrim('" + texto[2] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(1) + "))) <> lower(ltrim(rtrim('" + text + "')))";
					buscar += " or lower(ltrim(rtrim(" + datoMaestro.getCampo(1) + ")))=lower(ltrim(rtrim('" + texto[1] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(2) + "))) <> lower(ltrim(rtrim('" + r[2] + "')))";

					// construccion de la consulta de actualizacion
					actualizar = "update " + datoMaestro.getTabla() + " set ";
					actualizar += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar += datoMaestro.getCampo(1) + "=ltrim(rtrim('" + texto[1] + "')) ";
					actualizar += " where " + datoMaestro.getCampo(1) + " = '" + text + "'";

					// busqueda de registros repetidos
					if (cursor.existe(buscar)) {

						request.setAttribute("mensaje", setMensaje("El nombre y/o cndigo indicado ya existen, cambielo"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[2].equals(texto[2]))
							ir(2, ant, request, response);
						else {
							// actualizacion del registro
							if (cursor.registrar(actualizar)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								Recursos.setRecurso(Recursos.TIPORECONOCIMIENTO);
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje("No se pudo actualizar ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}
					}

					break;

				case 9: // G_Constante Metodologia
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden from G_Constante ";
					buscar += "where G_ConTipo = 3 and " + datoMaestro.getCampo(2) + " = " + text;

					// obtencion del registro
					r = cursor.nombre(buscar, 1, 4);

					// construccion de la consulta del busqueda
					buscar = "select * from " + datoMaestro.getTabla() + " where G_ConTipo = 3 and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) = lower(ltrim(rtrim('" + texto[2] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) <> lower(ltrim(rtrim('" + r[2] + "')))";

					// construccion de la consulta de actualizacion
					actualizar = "update " + datoMaestro.getTabla() + " set ";
					actualizar += datoMaestro.getCampo(6) + " =ltrim(rtrim('" + texto[4] + "')), ";
					actualizar += datoMaestro.getCampo(5) + " =ltrim(rtrim('" + texto[3] + "')), ";
					actualizar += datoMaestro.getCampo(4) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[1] + "')) ";
					actualizar += "where G_ConTipo = 3 and " + datoMaestro.getCampo(2) + " = " + text;

					// busqueda de registros repetidos
					if (cursor.existe(buscar)) {

						request.setAttribute("mensaje", setMensaje("El nombre indicado ya existe, cambielo"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[2].equals(texto[2]) && r[3].equals(texto[3]) && r[4].equals(texto[4]))
							ir(2, ant, request, response);
						else {
							// actualizacion del registro
							if (cursor.registrar(actualizar)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								Recursos.setRecurso(Recursos.METODOLOGIA);
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
								ir(2, ant, request, response);
								return;
							}
						}
					}

					break;

				case 10: // G_Constante
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select G_ConCodigo,G_ConNombre,G_ConCodPadre from G_Constante ";
					buscar += " where G_ConTipo = 8 and " + datoMaestro.getCampo(2) + " = " + text;

					// obtencion del registro
					r = cursor.nombre(buscar, 1, 3);

					// construccion de la consulta del busqueda
					buscar = "select * from " + datoMaestro.getTabla() + " where G_ConTipo = 8 and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) = lower(ltrim(rtrim('" + texto[2] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) <> lower(ltrim(rtrim('" + r[2] + "')))";

					// construccion de la consulta de actualizacion
					actualizar_transaccion[1] = "update G_Constante set ";
					actualizar_transaccion[1] += datoMaestro.getCampo(4) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar_transaccion[1] += datoMaestro.getCampo(3) + " =ltrim(rtrim('" + lista[1] + "')), ";
					actualizar_transaccion[1] += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[1] + "')) ";
					actualizar_transaccion[1] += "where G_ConTipo = 8 and " + datoMaestro.getCampo(2) + " = " + text;

					// actualizacinn en jerarquias
					actualizar_transaccion[2] = "update G_Jerarquia set ";
					actualizar_transaccion[2] += " G_JerLocal = ltrim(rtrim('" + texto[1] + "')) ";
					actualizar_transaccion[2] += " where G_JerTipo=1 and G_JerNivel=3 ";
					actualizar_transaccion[2] += " and G_JerLocal=" + r[1];

					// busqueda de registros repetidos
					if (cursor.existe(buscar)) {

						request.setAttribute("mensaje", setMensaje("El nombre indicado ya existe, cambielo"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[2].equals(texto[2]) && r[3].equals(texto[3]) && r[4].equals(texto[4]))
							ir(2, sig, request, response);
						else {
							if (cursor.registrar_transaccion(actualizar_transaccion)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								Recursos.setRecurso(Recursos.DEPARTAMENTO);
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
								ir(2, ant, request, response);
								return;
							}
						}
					}
					break;

				case 11: // G_Constante
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden from G_Constante ";
					buscar += "where G_ConTipo = 9 and " + datoMaestro.getCampo(2) + " = " + text;

					// obtencion del registro
					r = cursor.nombre(buscar, 1, 4);
					// construccion de la consulta del busqueda

					// construccion de la consulta del busqueda
					buscar = "select * from " + datoMaestro.getTabla() + " where G_ConTipo = 9 and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) = lower(ltrim(rtrim('" + texto[2] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) <> lower(ltrim(rtrim('" + r[2] + "')))";

					// construccion de la consulta de actualizacion
					actualizar = "update G_Constante set ";
					actualizar += datoMaestro.getCampo(6) + " =ltrim(rtrim('" + texto[4] + "')), ";
					actualizar += datoMaestro.getCampo(5) + " =ltrim(rtrim('" + texto[3] + "')), ";
					actualizar += datoMaestro.getCampo(4) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[1] + "')) ";
					actualizar += "where G_ConTipo = 9 and " + datoMaestro.getCampo(2) + " = " + text;

					// busqueda de registros repetidos
					if (cursor.existe(buscar)) {

						request.setAttribute("mensaje", setMensaje("El nombre indicado ya existe, cambielo"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[2].equals(texto[2]) && r[3].equals(texto[3]) && r[4].equals(texto[4]))
							ir(2, ant, request, response);
						else {
							// actualizacion del registro
							if (cursor.registrar(actualizar)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								Recursos.setRecurso(Recursos.TIPOOCUPANTE);
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
								ir(2, ant, request, response);
								return;
							}
						}
					}

					break;

				case 12: // G_Constante
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden from G_Constante ";
					buscar += "where G_ConTipo = 4 and " + datoMaestro.getCampo(2) + " = " + text;

					// obtencion del registro
					r = cursor.nombre(buscar, 1, 4);

					// construccion de la consulta del busqueda
					buscar = "select * from " + datoMaestro.getTabla() + " where G_ConTipo = 4 and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) = lower(ltrim(rtrim('" + texto[2] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) <> lower(ltrim(rtrim('" + r[2] + "')))";

					// construccion de la consulta de actualizacion
					actualizar = "update G_Constante set ";
					actualizar += datoMaestro.getCampo(6) + " =ltrim(rtrim('" + texto[4] + "')), ";
					actualizar += datoMaestro.getCampo(5) + " =ltrim(rtrim('" + texto[3] + "')), ";
					actualizar += datoMaestro.getCampo(4) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[1] + "')) ";
					actualizar += "where G_ConTipo = 4 and " + datoMaestro.getCampo(2) + " = " + text;

					// busqueda de registros repetidos
					if (cursor.existe(buscar)) {

						request.setAttribute("mensaje", setMensaje("El nombre indicado ya existe, cambielo"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[2].equals(texto[2]) && r[3].equals(texto[3]) && r[4].equals(texto[4]))
							ir(2, ant, request, response);
						else {
							// actualizacion del registro
							if (cursor.registrar(actualizar)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								Recursos.setRecurso(Recursos.ESPECIALIDAD);
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
								ir(2, ant, request, response);
								return;
							}
						}
					}

					break;

				case 13:// Parametro
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select * from " + datoMaestro.getTabla() + " where lower(ltrim(rtrim(";
					buscar += datoMaestro.getCampo(1) + "))) = lower(ltrim(rtrim('" + text + "')))";
					// obtencion del registro
					r = cursor.nombre(buscar, 1, 2);

					// construccion de la consulta del busqueda
					buscar = "select * from " + datoMaestro.getTabla() + " where lower(ltrim(rtrim(";
					buscar += datoMaestro.getCampo(2) + "))) = lower(ltrim(rtrim('" + texto[2] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(1) + "))) <> lower(ltrim(rtrim('" + text + "')))";
					buscar += " or lower(ltrim(rtrim(" + datoMaestro.getCampo(1) + ")))=lower(ltrim(rtrim('" + texto[1] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(2) + "))) <> lower(ltrim(rtrim('" + r[2] + "')))";

					// construccion de la consulta de actualizacion
					actualizar = "update " + datoMaestro.getTabla() + " set ";
					actualizar += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar += datoMaestro.getCampo(1) + "=ltrim(rtrim('" + texto[1] + "')) ";
					actualizar += " where " + datoMaestro.getCampo(1) + " = '" + text + "'";

					// busqueda de registros repetidos
					if (cursor.existe(buscar)) {

						request.setAttribute("mensaje", setMensaje("El nombre y/o cndigo indicado ya existen, cambielo"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[2].equals(texto[2]))
							ir(2, ant, request, response);
						else {
							// actualizacion del registro
							if (cursor.registrar(actualizar)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								Recursos.setRecurso(Recursos.CONDICION);
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
								ir(2, ant, request, response);
								return;
							}
						}
					}

					break;

				case 14: // G_Constante
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden from G_Constante ";
					buscar += "where G_ConTipo = 11 and " + datoMaestro.getCampo(2) + " = " + text;

					// obtencion del registro
					r = cursor.nombre(buscar, 1, 4);
					// construccion de la consulta del busqueda

					// construccion de la consulta del busqueda
					buscar = "select * from " + datoMaestro.getTabla() + " where G_ConTipo = 11 and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) = lower(ltrim(rtrim('" + texto[2] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) <> lower(ltrim(rtrim('" + r[2] + "')))";

					// construccion de la consulta de actualizacion
					actualizar = "update G_Constante set ";
					actualizar += datoMaestro.getCampo(6) + " =ltrim(rtrim('" + texto[4] + "')), ";
					actualizar += datoMaestro.getCampo(5) + " =ltrim(rtrim('" + texto[3] + "')), ";
					actualizar += datoMaestro.getCampo(4) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[1] + "')) ";
					actualizar += "where G_ConTipo = 11 and " + datoMaestro.getCampo(2) + " = " + text;

					// busqueda de registros repetidos
					if (cursor.existe(buscar)) {

						request.setAttribute("mensaje", setMensaje("El nombre indicado ya existe, cambielo"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[2].equals(texto[2]) && r[3].equals(texto[3]) && r[4].equals(texto[4]))
							ir(2, ant, request, response);
						else {
							// actualizacion del registro
							if (cursor.registrar(actualizar)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								Recursos.setRecurso(Recursos.CONVIVENCIA);
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
								ir(2, ant, request, response);
								return;
							}
						}
					}

					break;

				case 15: // G_Constante
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden from G_Constante ";
					buscar += "where G_ConTipo = 12 and " + datoMaestro.getCampo(2) + " = " + text;

					// obtencion del registro
					r = cursor.nombre(buscar, 1, 4);
					// construccion de la consulta del busqueda

					// construccion de la consulta del busqueda
					buscar = "select * from " + datoMaestro.getTabla() + " where G_ConTipo = 12 and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) = lower(ltrim(rtrim('" + texto[2] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) <> lower(ltrim(rtrim('" + r[2] + "')))";

					// construccion de la consulta de actualizacion
					actualizar = "update G_Constante set ";
					actualizar += datoMaestro.getCampo(6) + " =ltrim(rtrim('" + texto[4] + "')), ";
					actualizar += datoMaestro.getCampo(5) + " =ltrim(rtrim('" + texto[3] + "')), ";
					actualizar += datoMaestro.getCampo(4) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[1] + "')) ";
					actualizar += "where G_ConTipo = 12 and " + datoMaestro.getCampo(2) + " = " + text;

					// busqueda de registros repetidos
					if (cursor.existe(buscar)) {

						request.setAttribute("mensaje", setMensaje("El nombre indicado ya existe, cambielo"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[2].equals(texto[2]) && r[3].equals(texto[3]) && r[4].equals(texto[4]))
							ir(2, sig, request, response);
						else {
							// actualizacion del registro
							if (cursor.registrar(actualizar)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								ir(2, sig, request, response);
								Recursos.setRecurso(Recursos.DISCAPACIDAD);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
								ir(2, ant, request, response);
								return;
							}
						}
					}

					break;

				case 16: // G_Constante
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden from G_Constante ";
					buscar += "where G_ConTipo = 13 and " + datoMaestro.getCampo(2) + " = " + text;

					// obtencion del registro
					r = cursor.nombre(buscar, 1, 4);

					// construccion de la consulta del busqueda
					buscar = "select * from " + datoMaestro.getTabla() + " where G_ConTipo = 13 and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) = lower(ltrim(rtrim('" + texto[2] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) <> lower(ltrim(rtrim('" + r[2] + "')))";

					// construccion de la consulta de actualizacion
					actualizar = "update G_Constante set ";
					actualizar += datoMaestro.getCampo(6) + " =ltrim(rtrim('" + texto[4] + "')), ";
					actualizar += datoMaestro.getCampo(5) + " =ltrim(rtrim('" + texto[3] + "')), ";
					actualizar += datoMaestro.getCampo(4) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[1] + "')) ";
					actualizar += "where G_ConTipo = 13 and " + datoMaestro.getCampo(2) + " = " + text;

					// busqueda de registros repetidos
					if (cursor.existe(buscar)) {

						request.setAttribute("mensaje", setMensaje("El nombre indicado ya existe, cambielo"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[2].equals(texto[2]) && r[3].equals(texto[3]) && r[4].equals(texto[4]))
							ir(2, sig, request, response);
						else {
							// actualizacion del registro
							if (cursor.registrar(actualizar)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								Recursos.setRecurso(Recursos.CAPACIDAD);
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
								ir(2, ant, request, response);
								return;
							}
						}
					}

					break;

				case 17: // G_Constante
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden from G_Constante ";
					buscar += "where G_ConTipo = 14 and " + datoMaestro.getCampo(2) + " = " + text;

					// obtencion del registro
					r = cursor.nombre(buscar, 1, 4);

					// construccion de la consulta del busqueda
					buscar = "select * from " + datoMaestro.getTabla() + " where G_ConTipo = 14 and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) = lower(ltrim(rtrim('" + texto[2] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) <> lower(ltrim(rtrim('" + r[2] + "')))";

					// construccion de la consulta de actualizacion
					actualizar = "update G_Constante set ";
					actualizar += datoMaestro.getCampo(6) + " =ltrim(rtrim('" + texto[4] + "')), ";
					actualizar += datoMaestro.getCampo(5) + " =ltrim(rtrim('" + texto[3] + "')), ";
					actualizar += datoMaestro.getCampo(4) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[1] + "')) ";
					actualizar += "where G_ConTipo = 14 and " + datoMaestro.getCampo(2) + " = " + text;

					// busqueda de registros repetidos
					if (cursor.existe(buscar)) {

						request.setAttribute("mensaje", setMensaje("El nombre indicado ya existe, cambielo"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[2].equals(texto[2]) && r[3].equals(texto[3]) && r[4].equals(texto[4]))
							ir(2, ant, request, response);
						else {
							// actualizacion del registro
							if (cursor.registrar(actualizar)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								Recursos.setRecurso(Recursos.ETNIA);
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
								ir(2, ant, request, response);
								return;
							}
						}
					}

					break;

				case 18: // G_Constante
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden from G_Constante ";
					buscar += "where G_ConTipo = 15 and " + datoMaestro.getCampo(2) + " = " + text;

					// obtencion del registro
					r = cursor.nombre(buscar, 1, 4);

					// construccion de la consulta del busqueda
					buscar = "select * from " + datoMaestro.getTabla() + " where G_ConTipo = 15 and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) = lower(ltrim(rtrim('" + texto[2] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) <> lower(ltrim(rtrim('" + r[2] + "')))";

					// construccion de la consulta de actualizacion
					actualizar = "update G_Constante set ";
					actualizar += datoMaestro.getCampo(6) + " =ltrim(rtrim('" + texto[4] + "')), ";
					actualizar += datoMaestro.getCampo(5) + " =ltrim(rtrim('" + texto[3] + "')), ";
					actualizar += datoMaestro.getCampo(4) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[1] + "')) ";
					actualizar += "where G_ConTipo = 15 and " + datoMaestro.getCampo(2) + " = " + text;

					// busqueda de registros repetidos
					if (cursor.existe(buscar)) {

						request.setAttribute("mensaje", setMensaje("El nombre indicado ya existe, cambielo"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[2].equals(texto[2]) && r[3].equals(texto[3]) && r[4].equals(texto[4]))
							ir(2, ant, request, response);
						else {
							// actualizacion del registro
							if (cursor.registrar(actualizar)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								Recursos.setRecurso(Recursos.AUSENCIA);
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
								ir(2, ant, request, response);
								return;
							}
						}
					}

					break;

				case 19: // G_Constante
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden from G_Constante ";
					buscar += " where G_ConTipo = 16 and " + datoMaestro.getCampo(2) + " = " + text;

					// obtencion del registro
					r = cursor.nombre(buscar, 1, 4);

					// construccion de la consulta del busqueda
					buscar = "select * from " + datoMaestro.getTabla() + " where G_ConTipo = 16 and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) = lower(ltrim(rtrim('" + texto[2] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) <> lower(ltrim(rtrim('" + r[2] + "')))";

					// construccion de la consulta de actualizacion
					actualizar = "update G_Constante set ";
					actualizar += datoMaestro.getCampo(6) + " =ltrim(rtrim('" + texto[4] + "')), ";
					actualizar += datoMaestro.getCampo(5) + " =ltrim(rtrim('" + texto[3] + "')), ";
					actualizar += datoMaestro.getCampo(4) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[1] + "')) ";
					actualizar += "where G_ConTipo = 16 and " + datoMaestro.getCampo(2) + " = " + text;

					// busqueda de registros repetidos
					if (cursor.existe(buscar)) {

						request.setAttribute("mensaje", setMensaje("El nombre indicado ya existe, cambielo"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[2].equals(texto[2]) && r[3].equals(texto[3]) && r[4].equals(texto[4]))
							ir(2, ant, request, response);
						else {
							// actualizacion del registro
							if (cursor.registrar(actualizar)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								Recursos.setRecurso(Recursos.PROGRAMA);
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
								ir(2, ant, request, response);
								return;
							}
						}
					}

					break;

				case 21: // G_Constante municipio
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select G_ConCodigo,G_ConNombre,G_ConCodPadre from G_Constante ";
					buscar += " where G_ConTipo = 7 and " + datoMaestro.getCampo(2) + " = " + text;

					// obtencion del registro
					r = cursor.nombre(buscar, 1, 3);

					// construccion de la consulta del busqueda
					buscar = "select * from " + datoMaestro.getTabla() + " where G_ConTipo = 7 and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) = lower(ltrim(rtrim('" + texto[2] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) <> lower(ltrim(rtrim('" + r[2] + "')))";

					// construccion de la consulta de actualizacion
					actualizar_transaccion[1] = "update G_Constante set ";
					actualizar_transaccion[1] += datoMaestro.getCampo(4) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar_transaccion[1] += datoMaestro.getCampo(3) + " =ltrim(rtrim('" + lista[1] + "')), ";
					actualizar_transaccion[1] += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[1] + "')) ";
					actualizar_transaccion[1] += "where G_ConTipo = 7 and " + datoMaestro.getCampo(2) + " = " + text;

					// busqueda de registros repetidos
					if (cursor.existe(buscar)) {

						request.setAttribute("mensaje", setMensaje("El nombre indicado ya existe, cambielo"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[3].equals(lista[1]) && r[2].equals(texto[2]))
							ir(2, ant, request, response);
						else {
							if (cursor.registrar_transaccion(actualizar_transaccion)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								Recursos.setRecurso(Recursos.LOCALIDAD);
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje("No se puede eliminar el registro posible causa: " + cursor.getMensaje()));
								ir(2, ant, request, response);
								return;
							}
						}
					}
					break;

				case 22: // G_Area
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select G_AreCodigo, G_AreNombre, G_AreTipo ";
					buscar += " from G_Area where " + datoMaestro.getCampo(1) + " = " + text;

					// obtencion del registro
					r = cursor.nombre(buscar, 1, 3);

					// construccion de la consulta del busqueda
					buscar = "select * from " + datoMaestro.getTabla() + " where lower(ltrim(rtrim(";
					/* nombre */buscar += datoMaestro.getCampo(2) + "))) = lower(ltrim(rtrim('" + texto[2] + "')))";
					/* cndigo */buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(1) + "))) <> lower(ltrim(rtrim('" + text + "')))";
					/* cndigo */buscar += " or lower(ltrim(rtrim(" + datoMaestro.getCampo(1) + ")))= '" + texto[1] + "'";
					/* nombre */buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(2) + "))) <> lower(ltrim(rtrim('" + r[2] + "')))";

					// construccion de la consulta de actualizacion
					actualizar = "update " + datoMaestro.getTabla() + " set ";
					actualizar += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar += datoMaestro.getCampo(3) + " =ltrim(rtrim('" + lista[1] + "')), ";
					actualizar += datoMaestro.getCampo(1) + " =ltrim(rtrim('" + texto[1] + "')) ";
					actualizar += "where " + datoMaestro.getCampo(1) + " = " + text;

					// busqueda de registros repetidos
					if (cursor.existe(buscar)) {

						request.setAttribute("mensaje", setMensaje("El nombre y/o cndigo indicado ya existen, cambielo"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[2].equals(texto[2]) && r[3].equals(lista[1]))
							ir(2, ant, request, response);
						else {
							if (cursor.registrar(actualizar)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
								ir(2, ant, request, response);
							}
						}
					}
					break;

				case 23:// G_Grado
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select * from " + datoMaestro.getTabla() + " where lower(ltrim(rtrim(";
					buscar += datoMaestro.getCampo(2) + "))) = lower(ltrim(rtrim('" + text + "')))";
					// obtencion del registro
					r = cursor.nombre(buscar, 1, 5);

					buscarhijo = "select distinct gracodigo from grado where gracodigo =" + text + " and rownum<=1";

					// construccion de la consulta del busqueda
					buscar = "select * from " + datoMaestro.getTabla() + " where  lower(ltrim(rtrim(" + datoMaestro.getCampo(3) + "))) = lower(ltrim(rtrim('" + texto[3] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(3) + "))) <> lower(ltrim(rtrim('" + r[3] + "')))";

					buscarl = "select * from " + datoMaestro.getTabla() + " where lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) = lower(ltrim(rtrim('" + texto[4] + "')))";
					buscarl += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) <> lower(ltrim(rtrim('" + r[4] + "')))";

					// construccion de la consulta de actualizacion
					actualizar = "update " + datoMaestro.getTabla() + " set ";
					actualizar += datoMaestro.getCampo(5) + " =ltrim(rtrim('" + texto[5] + "')), ";
					actualizar += datoMaestro.getCampo(4) + " =ltrim(rtrim('" + texto[4] + "')), ";
					actualizar += datoMaestro.getCampo(3) + " =ltrim(rtrim('" + texto[3] + "')), ";
					actualizar += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar += datoMaestro.getCampo(1) + "=ltrim(rtrim('" + lista[1] + "')) ";
					actualizar += " where " + datoMaestro.getCampo(2) + " = '" + text + "'";

					// construccion de la consulta de actualizacion
					actualizar1 = "update " + datoMaestro.getTabla() + " set ";
					actualizar1 += datoMaestro.getCampo(5) + " =ltrim(rtrim('" + texto[5] + "')), ";
					actualizar1 += datoMaestro.getCampo(4) + " =ltrim(rtrim('" + texto[4] + "')), ";
					actualizar1 += datoMaestro.getCampo(3) + " =ltrim(rtrim('" + texto[3] + "')), ";
					actualizar1 += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar1 += datoMaestro.getCampo(1) + "=ltrim(rtrim('" + lista1 + "')) ";// cndigo del grado del input de tipo hidden
					actualizar1 += " where " + datoMaestro.getCampo(2) + " = '" + text + "'";

					// busqueda de registros repetidos
					if (cursor.existe(buscar) || cursor.existe(buscarl)) {

						request.setAttribute("mensaje", setMensaje("El nombre o abreviatura indicados ya existen, cambielos"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(lista[1]) && r[2].equals(texto[2]) && r[3].equals(texto[3]) && r[4].equals(texto[4]) && r[5].equals(texto[5]))
							ir(2, ant, request, response);
						else {
							if (!cursor.existe(buscarhijo)) {
								if (cursor.registrar(actualizar)) {
									request.setAttribute("ok", "ok");
									request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
									Recursos.setRecurso(Recursos.GRADO);
									ir(2, sig, request, response);
									return;
								} else {

									request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
									ir(2, ant, request, response);
									return;
								}
							}
							if (cursor.registrar(actualizar1)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
								ir(2, ant, request, response);
								return;
							}
						}
					}

					break;

				case 24: // G_Constante
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden from G_Constante ";
					buscar += "where G_ConTipo = 17 and " + datoMaestro.getCampo(2) + " = " + text;

					// obtencion del registro
					r = cursor.nombre(buscar, 1, 4);

					// construccion de la consulta del busqueda
					buscar = "select * from " + datoMaestro.getTabla() + " where G_ConTipo = 17 and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) = lower(ltrim(rtrim('" + texto[2] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) <> lower(ltrim(rtrim('" + r[2] + "')))";

					buscarl = "select * from " + datoMaestro.getTabla() + " where G_ConTipo = 17 and lower(ltrim(rtrim(" + datoMaestro.getCampo(5) + "))) = lower(ltrim(rtrim('" + texto[3] + "')))";
					buscarl += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(5) + "))) <> lower(ltrim(rtrim('" + r[3] + "')))";

					// construccion de la consulta de actualizacion
					actualizar = "update G_Constante set ";
					actualizar += datoMaestro.getCampo(6) + " =ltrim(rtrim('" + texto[4] + "')), ";
					actualizar += datoMaestro.getCampo(5) + " =ltrim(rtrim('" + texto[3] + "')), ";
					actualizar += datoMaestro.getCampo(4) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[1] + "')) ";
					actualizar += "where G_ConTipo = 17 and " + datoMaestro.getCampo(2) + " = " + text;

					// busqueda de registros repetidos
					if (cursor.existe(buscar) || cursor.existe(buscarl)) {

						request.setAttribute("mensaje", setMensaje("El nombre o abreviatura indicados ya existen, cambielos"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[2].equals(texto[2]) && r[3].equals(texto[3]) && r[4].equals(texto[4]))
							ir(2, ant, request, response);
						else {
							// actualizacion del registro
							if (cursor.registrar(actualizar)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								Recursos.setRecurso(Recursos.CALENDARIO);
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
								ir(2, ant, request, response);
								return;
							}
						}
					}

					break;

				case 25: // G_Constante
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden from G_Constante ";
					buscar += "where G_ConTipo = 18 and " + datoMaestro.getCampo(2) + " = " + text;

					// obtencion del registro
					r = cursor.nombre(buscar, 1, 4);
					// construccion de la consulta del busqueda

					// construccion de la consulta del busqueda
					buscar = "select * from " + datoMaestro.getTabla() + " where G_ConTipo = 18 and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) = lower(ltrim(rtrim('" + texto[2] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) <> lower(ltrim(rtrim('" + r[2] + "')))";

					// construccion de la consulta de actualizacion
					actualizar = "update G_Constante set ";
					actualizar += datoMaestro.getCampo(6) + " =ltrim(rtrim('" + texto[4] + "')), ";
					actualizar += datoMaestro.getCampo(5) + " =ltrim(rtrim('" + texto[3] + "')), ";
					actualizar += datoMaestro.getCampo(4) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[1] + "')) ";
					actualizar += "where G_ConTipo = 18 and " + datoMaestro.getCampo(2) + " = " + text;

					// busqueda de registros repetidos
					if (cursor.existe(buscar)) {

						request.setAttribute("mensaje", setMensaje("El nombre indicado ya existe, cambielo"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[2].equals(texto[2]) && r[3].equals(texto[3]) && r[4].equals(texto[4]))
							ir(2, ant, request, response);
						else {
							// actualizacion del registro
							if (cursor.registrar(actualizar)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								Recursos.setRecurso(Recursos.PROPIEDAD);
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
								ir(2, ant, request, response);
								return;
							}
						}
					}

					break;

				case 26: // G_Constante
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden from G_Constante ";
					buscar += "where G_ConTipo = 19 and " + datoMaestro.getCampo(2) + " = " + text;

					// obtencion del registro
					r = cursor.nombre(buscar, 1, 4);

					// construccion de la consulta del busqueda
					buscar = "select * from " + datoMaestro.getTabla() + " where G_ConTipo = 19 and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) = lower(ltrim(rtrim('" + texto[2] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) <> lower(ltrim(rtrim('" + r[2] + "')))";

					// construccion de la consulta de actualizacion
					actualizar = "update G_Constante set ";
					actualizar += datoMaestro.getCampo(6) + " =ltrim(rtrim('" + texto[4] + "')), ";
					actualizar += datoMaestro.getCampo(5) + " =ltrim(rtrim('" + texto[3] + "')), ";
					actualizar += datoMaestro.getCampo(4) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[1] + "')) ";
					actualizar += "where G_ConTipo = 19 and " + datoMaestro.getCampo(2) + " = " + text;

					// busqueda de registros repetidos
					if (cursor.existe(buscar)) {

						request.setAttribute("mensaje", setMensaje("El nombre indicado ya existe, cambielo"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[2].equals(texto[2]) && r[3].equals(texto[3]) && r[4].equals(texto[4]))
							ir(2, sig, request, response);
						else {
							// actualizacion del registro
							if (cursor.registrar(actualizar)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								Recursos.setRecurso(Recursos.DISCAPACIDADINSTITUCION);
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
								ir(2, ant, request, response);
								return;
							}
						}
					}

					break;

				case 27:// G_Nucleo
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select * from " + datoMaestro.getTabla() + " where lower(ltrim(rtrim(";
					buscar += datoMaestro.getCampo(2) + "))) = lower(ltrim(rtrim('" + text + "')))";
					// obtencion del registro
					r = cursor.nombre(buscar, 1, 6);

					// construccion de la consulta del busqueda
					buscar = "select * from " + datoMaestro.getTabla() + " where lower(ltrim(rtrim(";
					buscar += datoMaestro.getCampo(3) + "))) = lower(ltrim(rtrim('" + texto[3] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(2) + "))) <> lower(ltrim(rtrim('" + text + "')))";
					buscar += " or lower(ltrim(rtrim(" + datoMaestro.getCampo(2) + ")))=lower(ltrim(rtrim('" + texto[2] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(3) + "))) <> lower(ltrim(rtrim('" + r[3] + "')))";

					// construccion de la consulta de actualizacion
					actualizar = "update " + datoMaestro.getTabla() + " set ";
					actualizar += datoMaestro.getCampo(6) + " =ltrim(rtrim('" + texto[6] + "')), ";
					actualizar += datoMaestro.getCampo(5) + " =ltrim(rtrim('" + texto[5] + "')), ";
					actualizar += datoMaestro.getCampo(4) + " =ltrim(rtrim('" + texto[4] + "')), ";
					actualizar += datoMaestro.getCampo(3) + " =ltrim(rtrim('" + texto[3] + "')), ";
					actualizar += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar += datoMaestro.getCampo(1) + "=ltrim(rtrim('" + lista[1] + "')) ";
					actualizar += " where " + datoMaestro.getCampo(2) + " = '" + text + "'";

					// actualizacinn en jerarquias lo realiza el trigger de g_nucleo

					// busqueda de registros repetidos
					if (cursor.existe(buscar)) {

						request.setAttribute("mensaje", setMensaje("El nombre indicado ya existe, cambielo"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(lista[1]) && r[2].equals(texto[2]) && r[3].equals(texto[3]) && r[4].equals(texto[4]) && r[5].equals(texto[5]) && r[6].equals(texto[6]))
							ir(2, ant, request, response);
						else {
							// actualizacion del registro
							if (cursor.registrar(actualizar)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								Recursos.setRecurso(Recursos.NUCLEO);
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
								ir(2, ant, request, response);
							}

						}
					}
					break;

				case 29:// g_asignatura
					checksentencia = null;
					// consulta para extraer el registro
					buscar = "select G_AsiCodigo,G_AsiCodArea,G_AsiNombre from G_Asignatura ";
					buscar += " where G_AsiCodigo = " + text;

					// obtencion del registro
					r = cursor.nombre(buscar, 1, 3);
					// construccion de la consulta del busqueda

					buscarhijo = "select distinct asicodigo from asignatura where asicodigo =" + text + " and rownum<=1";

					// construccion de la consulta del busqueda
					buscar = "select * from " + datoMaestro.getTabla() + " where lower(ltrim(rtrim(";
					/* nombre */buscar += datoMaestro.getCampo(3) + "))) = lower(ltrim(rtrim('" + texto[3] + "')))";
					/* cndigo */buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(2) + "))) <> lower(ltrim(rtrim('" + text + "')))";
					/* cndigo */buscar += " or lower(ltrim(rtrim(" + datoMaestro.getCampo(2) + ")))= '" + texto[1] + "'";
					/* nombre */buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(3) + "))) <> lower(ltrim(rtrim('" + r[3] + "')))";

					// construccion de la consulta de actualizacion
					actualizar = "update " + datoMaestro.getTabla() + " set ";
					actualizar += datoMaestro.getCampo(3) + " =ltrim(rtrim('" + texto[3] + "')), ";
					actualizar += datoMaestro.getCampo(1) + " =ltrim(rtrim('" + lista[1] + "')), ";
					actualizar += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[1] + "')) ";
					actualizar += "where " + datoMaestro.getCampo(2) + " = " + text;

					// construccion de la consulta de actualizacion
					actualizar1 = "update " + datoMaestro.getTabla() + " set ";
					actualizar1 += datoMaestro.getCampo(3) + " =ltrim(rtrim('" + texto[3] + "')), ";
					actualizar1 += datoMaestro.getCampo(1) + " =ltrim(rtrim('" + lista1 + "')), ";// codigo area del input type hidden
					actualizar1 += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[1] + "')) ";
					actualizar1 += "where " + datoMaestro.getCampo(2) + " = " + text;

					// busqueda de registros repetidos
					if (cursor.existe(buscar)) {

						request.setAttribute("mensaje", setMensaje("El nombre y/o cndigo indicado ya existen, cambielo"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[2].equals(lista[1]) && r[3].equals(texto[3]))
							ir(2, ant, request, response);
						else {
							if (!cursor.existe(buscarhijo)) {
								if (cursor.registrar(actualizar)) {
									request.setAttribute("ok", "ok");
									request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
									ir(2, sig, request, response);
									return;
								} else {

									request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
									ir(2, ant, request, response);
									return;
								}
							}
							if (cursor.registrar(actualizar1)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
								ir(2, ant, request, response);
								return;
							}
						}
					}

					break;

				case 28: // Areas de la institucinn

					// consulta para extraer el registro
					buscar = "select AreCodigo,AreNombre,AreOrden,AreAbrev,Arevigencia from " + datoMaestro.getTabla() + "";
					buscar += " where AreCodInst = " + institucion;
					buscar += " and AreCodMetod = " + metodologiaid;
					buscar += " and AreCodigo = " + text2;
					buscar += " and AreVigencia = " + vigencia;

					// obtencion del registro
					r = cursor.nombre(buscar, 1, 5);

					// construccion de la consulta del busqueda
					searchson[1] = "select distinct Arecodigo from " + datoMaestro.getTabla() + " where Arecodinst=" + institucion + " and  Arecodmetod='" + metodologiaid + "' and AreVigencia='" + vigencia + "'  and (lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) = lower(ltrim(rtrim('" + texto[2] + "')))";
					searchson[1] += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) <> lower(ltrim(rtrim('" + r[2] + "'))))";

					searchson[2] = "select distinct Arecodigo from " + datoMaestro.getTabla() + " where Arecodinst=" + institucion + " and  Arecodmetod='" + metodologiaid + "' and AreVigencia='" + vigencia + "'  and (lower(ltrim(rtrim(" + datoMaestro.getCampo(6) + "))) = lower(ltrim(rtrim('" + texto[4] + "')))";
					searchson[2] += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(6) + "))) <> lower(ltrim(rtrim('" + r[4] + "'))))";

					buscargrado = " select GraAreCodGrado,granombre from grado_area,area,grado ";
					buscargrado += " where graarecodmetod=arecodmetod ";
					buscargrado += " and graarecodarea=arecodigo ";
					buscargrado += " and graarecodgrado=gracodigo ";
					buscargrado += " and arecodinst=" + institucion;
					buscargrado += " and arevigencia=" + vigencia;
					buscargrado += " and graarevigencia=arevigencia ";
					buscargrado += " and arecodinst=graarecodinst ";
					buscargrado += " and gracodinst=graarecodinst ";
					buscargrado += " and GraAreCodMetod=" + metodologiaid + "";
					buscargrado += " and AreCodMetod =GraAreCodMetod ";
					buscargrado += " and GraAreCodArea=" + text2 + "";
					buscargrado += " ORDER BY GraAreCodGrado";

					matrixgrado = cursor.getDatoMaestro(buscargrado);

					if (check != null && matrixgrado != null) {
						System.out.println("check: " + check.length);// LOS GRADOS Q EL USUARIO DEJn
						System.out.println("matrixgrado: " + matrixgrado.length);// EL TOTAL DE GRADOS DE LA BD
						actualizar_transaccion = new String[((check.length * 2) + ((matrixgrado.length) * 2)) + 1];
						System.out.println("LONG actualizar_transaccion: " + actualizar_transaccion.length);
						posicion = 0;
					} else {

						request.setAttribute("mensaje", setMensaje("nNo hay grados para la metodologia seleccionada! \nPor lo tanto no se puede actualizar el registro"));
						ir(2, ant, request, response);
						return;
					}

					// consulta para insertar en jerarquias
					String buscar5 = "SELECT '2' tipo,'4' nivel,InsCodDepto,InsCodMun,InsCodLocal,InsCodigo,'" + lista1 + "' area,'" + metodologiaid + "' metodologia FROM institucion ";
					buscar5 += " where InsCodigo = " + institucion + "";

					matrix = cursor.getDatoMaestro(buscar5);

					/* REVISAR SI HAY ELIMINADOS */
					int exis = 0;
					int cant = 0;
					int num = 0;
					int vec = 0;

					for (int t = 0; t < matrixgrado.length; t++) {
						exis = 0;
						if (matrixgrado[t][0] != null) {
							for (int g = 0; g < check.length; g++) {
								if (matrixgrado[t][0].equals(check[g])) {
									exis++;
									break;
								}
							}
							if (exis == 0) {
								/* validar que se pueda borrar */
								searchson[1] = "select distinct asinombre,graasicodgrado from asignatura,grado_asignatura  ";
								searchson[1] += " where graasicodasign=asicodigo";
								searchson[1] += " and graasicodmetod=asicodmetod";
								searchson[1] += " and asicodinst=" + institucion + " and asivigencia=" + vigencia + " and graasivigencia=asivigencia  and asicodinst=graasicodinst and asicodarea=" + text2 + "";
								searchson[1] += " and asicodmetod=" + metodologiaid + "";
								searchson[1] += " and graasicodgrado=" + matrixgrado[t][0] + " and rownum<=1";

								searchson[2] = "select distinct desnombre,cod_grado from descriptor_valorativo,g_jerarquia_tipo2_nivel4 ";
								searchson[2] += " where descodjerar=codigo_jerarquia";
								searchson[2] += " and cod_institucion=" + institucion + " and vigencia=" + vigencia + " and desvigencia=vigencia and cod_area=" + text2 + "";
								searchson[2] += " and cod_metod=" + metodologiaid + "";
								searchson[2] += " and cod_grado=" + matrixgrado[t][0] + " and rownum<=1";

								searchson[3] = " select distinct evaarecodjerar from evaluacion_area,g_jerarquia_tipo2_nivel4 where evaarecodjerar=codigo_jerarquia and evaarevigencia=" + vigencia + "";
								searchson[3] += " and vigencia=evaarevigencia and cod_institucion=" + institucion + " and cod_metod='" + metodologiaid + "' and cod_area='" + text2 + "' and cod_grado=" + matrixgrado[t][0] + " and rownum<=1";

								if (cursor.existe1(searchson)) {

									request.setAttribute("mensaje", setMensaje(" nNo se puede eliminar el grado " + matrixgrado[t][1] + " , ya que tiene registros asociados en Asignaturas n Descriptores Valorativos n Evaluacinn de áreas!"));
									ir(2, ant, request, response);
									return;
								}

								/* checksentencia */actualizar_transaccion[posicion++] = "delete from grado_area where GraAreCodInst=" + institucion + " and GraAreCodGrado=" + matrixgrado[t][0] + " and GraAreCodMetod='" + metodologiaid + "' and GraAreCodArea=" + text2 + " and GraAreVigencia=" + vigencia;
								/* borrarjerarquia */actualizar_transaccion[posicion++] = "delete from g_jerarquia where g_jertipo=2 and G_JerNivel=4 and G_JerInst=" + institucion + " and G_JerMetod='" + metodologiaid + "' and g_jergrado=" + matrixgrado[t][0] + " and g_jerAreaAsig='" + text2 + "' and G_JerVigencia=" + vigencia;
							}
						}
					}

					/* REVISAR SI HAY NUEVOS */
					for (int g = 0; g < check.length; g++) {
						exis = 0;
						for (int t = 0; t < matrixgrado.length; t++) {
							if (matrixgrado[t][0].equals(check[g])) {
								exis++;
								break;
							}
						}
						if (exis == 0) {
							/* checksentencia */actualizar_transaccion[posicion++] = "insert into Grado_Area (GraAreCodInst,GraAreCodGrado,GraAreCodMetod,GraAreCodArea,GraAreVigencia) values (" + institucion + "," + check[g] + "," + metodologiaid + "," + lista1 + "," + vigencia + ")";
							/* insertarjerarquia */actualizar_transaccion[posicion++] = "insert into g_jerarquia (G_JerTipo,G_JerNivel,G_JerCodigo,G_JerDepto,G_JerMunic,G_JerLocal,G_JerInst,G_JerAreaAsig,G_JerMetod,G_JerGrado,G_JerVigencia) values (" + matrix[0][0] + "," + matrix[0][1] + ", CODIGO_JERARQUIA.nextVal ," + matrix[0][2] + "," + matrix[0][3] + "," + matrix[0][4] + "," + matrix[0][5] + "," + matrix[0][6] + "," + matrix[0][7] + "," + check[g] + ","
								+ vigencia + ")";
						}
					}
					// construccion de la consulta de actualizacion
					/* actualizar */actualizar_transaccion[posicion++] = " update AREA set AreNombre =ltrim(rtrim('" + texto[2] + "')),AreOrden =ltrim(rtrim('" + texto[3] + "')),AreAbrev =ltrim(rtrim('" + texto[4] + "'))  where AreCodInst = " + institucion + " and AreCodMetod = " + metodologiaid + " and AreCodigo = " + text2 + " and AreVigencia= " + vigencia;

					for (int j = 0; j < actualizar_transaccion.length; j++) {
						System.out.println("Transaccinn área: " + actualizar_transaccion[j]);
					}

					// sentencias para la insercinn del registro
					if (cursor.existe1(searchson)) {

						request.setAttribute("mensaje", setMensaje("El nombre o abreviatura indicados ya existen, cambielos"));
						ir(2, ant, request, response);
						return;
					} else {
						if (actualizar_transaccion != null) {
							if (cursor.registrar_transaccion(actualizar_transaccion)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron registrados satisfactoriamente");
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
								ir(2, ant, request, response);
								return;
							}
						}
					}
					break;

				case 30:// Asignaturas por institucinn

					// consulta para extraer el registro
					buscar = "select AsiCodArea,AsiCodigo,AsiNombre,AsiOrden,AsiAbrev,AsiVigencia from " + datoMaestro.getTabla() + "";
					buscar += " where AsiCodInst = " + institucion;
					buscar += " and AsiCodMetod = " + metodologiaid;
					buscar += " and AsiCodigo = " + text2;
					buscar += " and AsiVigencia = " + vigencia;
					// obtencion del registro
					r = cursor.nombre(buscar, 1, 6);

					// construccion de la consulta del busqueda
					searchson[1] = "select distinct Asicodigo from " + datoMaestro.getTabla() + " where Asicodinst=" + institucion + " and  Asicodmetod='" + metodologiaid + "' and AsiVigencia='" + vigencia + "'  and (lower(ltrim(rtrim(" + datoMaestro.getCampo(5) + "))) = lower(ltrim(rtrim('" + texto[3] + "')))";
					searchson[1] += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(5) + "))) <> lower(ltrim(rtrim('" + r[3] + "'))))";

					searchson[2] = "select distinct Asicodigo from " + datoMaestro.getTabla() + " where Asicodinst=" + institucion + " and  Asicodmetod='" + metodologiaid + "' and AsiVigencia='" + vigencia + "'  and (lower(ltrim(rtrim(" + datoMaestro.getCampo(7) + "))) = lower(ltrim(rtrim('" + texto[5] + "')))";
					searchson[2] += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(7) + "))) <> lower(ltrim(rtrim('" + r[5] + "'))))";

					buscargrado = " select graasiCodGrado,granombre,GRAASIIH from grado_asignatura,asignatura,grado ";
					buscargrado += " where graasicodmetod=asicodmetod ";
					buscargrado += " and graasicodasign=asicodigo ";
					buscargrado += " and graasicodgrado=gracodigo ";
					buscargrado += " and asicodinst=" + institucion;
					buscargrado += " and asivigencia=" + vigencia;
					buscargrado += " and graasivigencia=asivigencia ";
					buscargrado += " and asicodinst=graasicodinst ";
					buscargrado += " and gracodinst=graasicodinst ";
					buscargrado += " and GraasiCodMetod=" + metodologiaid + "";
					buscargrado += " and asiCodMetod =GraasiCodMetod ";
					buscargrado += " and GraasiCodAsign=" + text2 + "";
					buscargrado += " ORDER BY GraAsiCodGrado";

					matrixgrado = cursor.getDatoMaestro(buscargrado);

					if (check != null && matrixgrado != null) {
						// insertarjerarquia=new String[check.length+matrixgrado.length];
						// borrarjerarquia=new String[check.length+matrixgrado.length];
						// checksentencia=new String[check.length+matrixgrado.length];
						// actualizargra=new String[check.length+matrixgrado.length];
						System.out.println("check: " + check.length);// LOS GRADOS Q EL USUARIO DEJn
						System.out.println("matrixgrado: " + matrixgrado.length);// EL TOTAL DE GRADOS DE LA BD
						actualizar_transaccion = new String[((check.length * 2) + ((matrixgrado.length) * 2)) + 1];
						System.out.println("Tamanio actualizar_transaccion: " + actualizar_transaccion.length);
						posicion = 0;
					} else {

						request.setAttribute("mensaje", setMensaje("nNo hay grados para la metodologia seleccionada! \nPor lo tanto no se puede actualizar el registro"));
						ir(2, ant, request, response);
						return;
					}

					// consulta para insertar en la tabla de jerarquias
					String buscar4 = "SELECT '3' tipo,'4' nivel,InsCodDepto,InsCodMun,InsCodLocal,InsCodigo,'" + lista2 + "' asignatura,'" + metodologiaid + "' metodologia FROM institucion";
					buscar4 += " where InsCodigo = " + institucion + " ";

					matrix = cursor.getDatoMaestro(buscar4);

					/* REVISAR SI HAY ELIMINADOS */
					exis = 0;
					cant = 0;
					num = 0;
					vec = 0;
					int gra = 0;

					for (int t = 0; t < matrixgrado.length; t++) {
						exis = 0;
						if (matrixgrado[t][0] != null) {
							for (int g = 0; g < check.length; g++) {
								if (matrixgrado[t][0].equals(check[g])) {
									exis++;
									break;
								}
							}
							if (exis == 0) {
								/* validar que se pueda borrar */
								searchson[1] = "select distinct codigo_jerarquia,cod_grado,granombre from g_jerarquia_tipo3_nivel4,logro,grado ";
								searchson[1] += " where cod_institucion=" + institucion + " and gracodinst=cod_institucion and logcodjerar=codigo_jerarquia and gracodigo=cod_grado and gracodmetod=cod_metod";
								searchson[1] += " and cod_asignatura=" + text2 + "";
								searchson[1] += " and cod_grado=" + matrixgrado[t][0] + "";
								searchson[1] += " and cod_metod=" + metodologiaid + "";
								searchson[1] += " and vigencia=" + vigencia + " and rownum<=1";

								searchson[2] = " select distinct evaasicodjerar from evaluacion_asignatura,g_jerarquia_tipo3_nivel4 where evaasicodjerar=codigo_jerarquia and evaasivigencia=" + vigencia + "";
								searchson[2] += " and vigencia=evaasivigencia and cod_institucion=" + institucion + " and cod_metod='" + metodologiaid + "' and cod_asignatura='" + text2 + "' and cod_grado=" + matrixgrado[t][0] + " and rownum<=1";

								// ejecucinn de la consulta
								// matrixgrado1=cursor.getDatoMaestro(buscargrado1);

								/* SI SE PUEDE BORRAR */
								if (cursor.existe1(searchson)) {// registros asociados

									request.setAttribute("mensaje", setMensaje("nNo se puede eliminar el grado " + matrixgrado[t][1] + " ya que tiene registros asociados en Logros n en Evaluacinn de Asignaturas!"));
									ir(2, ant, request, response);
									return;
								}
								actualizar_transaccion[posicion++] = "delete from grado_asignatura where GraAsiCodInst=" + institucion + " and GraAsiCodGrado=" + matrixgrado[t][0] + " and GraAsiCodMetod='" + metodologiaid + "' and GraAsiCodAsign=" + text2 + " and GraAsiVigencia=" + vigencia;
								actualizar_transaccion[posicion++] = "delete from g_jerarquia where g_jertipo=3 and G_JerNivel=4 and G_JerInst=" + institucion + " and G_JerMetod='" + metodologiaid + "' and g_jergrado=" + matrixgrado[t][0] + " and g_jerareaasig='" + text2 + "' and G_JerVigencia=" + vigencia;

							}
						}
					}
					/* REVISAR SI HAY NUEVOS */
					int cambio = 0;
					for (int g = 0; g < check.length; g++) {
						exis = 0;
						cambio = 0;
						for (int t = 0; t < matrixgrado.length; t++) {
							if (matrixgrado[t][0].equals(check[g])) {
								exis++;
								if (matrixgrado[t][2] != null && intensidad[g] != null) {
									if (!matrixgrado[t][2].equals(intensidad[g]))
										cambio++;
									break;
								}
							}
						}
						if (exis == 0) {
							actualizar_transaccion[posicion++] = "insert into Grado_Asignatura(GraAsiCodInst,GraAsiCodGrado,GraAsiCodMetod,GraAsiCodAsign,GraAsiIh,GraAsiVigencia) values (ltrim(rtrim('" + institucion + "'))," + check[g] + "," + metodologiaid + "," + lista2 + "," + intensidad[g] + "," + vigencia + ")";
							actualizar_transaccion[posicion++] = "insert into g_jerarquia (G_JerTipo,G_JerNivel,G_JerCodigo,G_JerDepto,G_JerMunic,G_JerLocal,G_JerInst,G_JerAreaAsig,G_JerMetod,G_JerGrado,G_JerVigencia) values (" + matrix[0][0] + "," + matrix[0][1] + ",  CODIGO_JERARQUIA.nextVal ," + matrix[0][2] + "," + matrix[0][3] + "," + matrix[0][4] + "," + matrix[0][5] + "," + matrix[0][6] + "," + matrix[0][7] + "," + check[g] + "," + vigencia + ")";
						} else {
							if (cambio >= 0) {
								actualizar_transaccion[posicion++] = " update Grado_Asignatura  set GraAsiIh=" + intensidad[g] + "  where GraAsiCodInst= " + institucion + " and GraAsiCodGrado = " + check[g] + " and GraAsiCodMetod = " + metodologiaid + " and GraAsiCodAsign = " + lista2 + " and GraAsiVigencia=" + vigencia;
							}
						}
					}
					// construccion de la consulta de actualizacion
					actualizar_transaccion[posicion++] = " update Asignatura set AsiNombre=ltrim(rtrim('" + texto[3] + "')),AsiOrden =ltrim(rtrim('" + texto[4] + "')),AsiAbrev =ltrim(rtrim('" + texto[5] + "')),AsiCodigo=ltrim(rtrim('" + lista2 + "')) where AsiCodInst = " + institucion + " and AsiCodArea = " + lista1 + " and AsiCodMetod = " + metodologiaid + " and AsiCodigo = " + text2 + " and AsiVigencia = " + vigencia;

					// *************************************************************************************
					// busqueda de registros repetidos
					if (cursor.existe1(searchson)) {

						request.setAttribute("mensaje", setMensaje("El nombre o abreviatura indicados ya existen, cambielos"));
						ir(2, ant, request, response);
						return;
					} else {
						if (actualizar_transaccion != null) {
							if (cursor.registrar_transaccion(actualizar_transaccion)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron registrados satisfactoriamente");
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
								ir(2, ant, request, response);
								return;
							}
						}
					}
					break;

				case 31:// PLAN DE ESTUDIOS
					checksentencia = null;
					// consulta para extraer el registro
					buscar = "select PlaCriterio,PlaProcedimiento,PlaPlanesEspeciales,PlaCodMetod,PlaVigencia from " + datoMaestro.getTabla() + " where PlaCodInst=" + institucion;
					buscar += "and PlaCodMetod = '" + metodologiaid + "' and PlaVigencia=" + vigencia;
					// obtencion del registro
					r = cursor.nombre(buscar, 1, 5);

					buscar = "select distinct PlaCodInst from " + datoMaestro.getTabla() + " where PlaCodInst=" + institucion + " and PlaVigencia= " + vigencia + " and (lower(ltrim(rtrim(";
					/* metod */buscar += datoMaestro.getCampo(2) + "))) = lower(ltrim(rtrim('" + metodologiaid + "')))";
					/* metod */buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(2) + "))) <> lower(ltrim(rtrim('" + r[4] + "'))))";

					// construccion de la consulta de actualizacion
					actualizar = "update " + datoMaestro.getTabla() + " set ";
					actualizar += " " + datoMaestro.getCampo(5) + " =ltrim(rtrim('" + texto[3] + "')), ";
					actualizar += " " + datoMaestro.getCampo(4) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar += " " + datoMaestro.getCampo(3) + " =ltrim(rtrim('" + texto[1] + "')), ";
					actualizar += " " + datoMaestro.getCampo(2) + " =ltrim(rtrim('" + metodologiaid + "')) ";
					actualizar += " where " + datoMaestro.getCampo(2) + " = " + metodologiaid;
					actualizar += " and " + datoMaestro.getCampo(1) + " = " + institucion;
					actualizar += " and PlaVigencia = " + vigencia;

					// busqueda de registros repetidos
					if (cursor.existe(buscar)) {

						request.setAttribute("mensaje", setMensaje("La metodologna indicada ya existe, cambiela"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[2].equals(texto[2]) && r[3].equals(texto[3]) && r[4].equals(metodologiaid) && r[5].equals(vigencia))
							ir(2, ant, request, response);
						else {
							// actualizacion del registro
							if (cursor.registrar(actualizar)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
								ir(2, ant, request, response);
							}

						}
					}
					break;

				case 37:// Logros
					checksentencia = null;
					// consulta para extraer el registro
					buscar = " select cod_grado,cod_asignatura,LogCodPerInicial,LogCodPerFinal,LogNombre,LogAbrev,LogDescripcion,LogVigencia,LogCodjerar ";
					buscar += " from " + datoMaestro.getTabla() + ",g_jerarquia_tipo3_nivel4 ";
					buscar += " where LogCodJerar=" + text2;
					buscar += " and LogCodigo = " + text;
					buscar += " and LogVigencia = " + vigencia;
					buscar += " and codigo_jerarquia=LogCodJerar ";
					buscar += " and vigencia=LogVigencia ";

					// llenar el vector 'texto' con el registro
					r = cursor.nombre(buscar, 1, 9);
					String codigologrobd = r[9];
					// System.out.println("codigologrobd:"+codigologrobd);

					// consulta para encontrar el cndigo de la jerarquia
					searchcode = " select distinct codigo_jerarquia from g_jerarquia_tipo3_nivel4 ";
					searchcode += " where cod_institucion=" + institucion + " and cod_metod='" + metodologiaid + "' and cod_grado='" + lista1 + "' and cod_asignatura='" + lista2 + "' and vigencia=" + vigencia;

					codigol = cursor.nombre(searchcode);

					if (codigol == null) {

						request.setAttribute("mensaje", setMensaje("Error: No se encontrn el cndigo de la jerarquia"));
						ir(2, ant, request, response);
						return;
					}

					// construccion de la consulta para verificar registros iguales Nombre
					searchson[1] = "select distinct LogNombre from " + datoMaestro.getTabla() + " where logcodjerar='" + codigol[0] + "' and LogVigencia='" + vigencia + "' and (lower(ltrim(rtrim(" + datoMaestro.getCampo(6) + "))) = lower(ltrim(rtrim('" + texto[5] + "')))";
					searchson[1] += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(6) + "))) <> lower(ltrim(rtrim('" + r[5] + "'))))";

					searchson[2] = "select distinct LogNombre from " + datoMaestro.getTabla() + " where logcodjerar='" + codigol[0] + "' and LogVigencia='" + vigencia + "' and (lower(ltrim(rtrim(" + datoMaestro.getCampo(7) + "))) = lower(ltrim(rtrim('" + texto[6] + "')))";
					searchson[2] += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(7) + "))) <> lower(ltrim(rtrim('" + r[6] + "'))))";

					// construccion de la consulta de actualizacinn
					actualizar = "update LOGRO set ";
					actualizar += " LOGCODJERAR =ltrim(rtrim('" + codigol[0] + "')), ";
					actualizar += " LOGCODPERINICIAL =ltrim(rtrim('" + lista[3] + "')), ";
					actualizar += " LOGCODPERFINAL =ltrim(rtrim('" + lista[4] + "')), ";
					actualizar += " LOGNOMBRE =ltrim(rtrim('" + texto[5] + "')), ";
					actualizar += " LOGABREV =ltrim(rtrim('" + texto[6] + "')), ";
					actualizar += " LOGDESCRIPCION =ltrim(rtrim('" + texto[7] + "')) ";
					actualizar += " where LOGCODJERAR = " + codigologrobd;
					actualizar += " and LOGCODIGO = " + text;
					actualizar += " and LOGVIGENCIA = " + vigencia;

					// busqueda de registros repetidos
					if (cursor.existe1(searchson)) {

						request.setAttribute("mensaje", setMensaje("El nombre o abreviatura indicados ya existen, cambielos"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(lista1) && r[2].equals(lista2) && r[3].equals(lista[3]) && r[4].equals(lista[4]) && r[5].equals(texto[5]) && r[6].equals(texto[6]) && r[7].equals(texto[7]) && r[8].equals(vigencia))
							ir(2, ant, request, response);
						else {
							// actualizacion del registro
							if (cursor.registrar(actualizar)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
								ir(2, ant, request, response);
							}
						}
					}

					break;

				case 39:// Descriptores Valorativos
					checksentencia = null;

					buscar = " select cod_grado,cod_area,DesCodTipDes,DesCodPerInicial,DesCodPerFinal,DesNombre,DesAbrev,DesDescripcion,DesVigencia,Descodjerar ";
					buscar += " from " + datoMaestro.getTabla() + ",g_jerarquia_tipo2_nivel4 ";
					buscar += " where DesCodJerar = " + text2;
					buscar += " and DesCodigo = " + text;
					buscar += " and DesVigencia = " + vigencia;
					buscar += " and codigo_jerarquia=DesCodJerar ";
					buscar += " and vigencia=DesVigencia";

					// llenar el vector 'texto' con el registro
					r = cursor.nombre(buscar, 1, 10);
					String codigobd = r[10];
					// System.out.println("codigobd:"+codigobd);

					// consulta para encontrar el cndigo de la jerarquia
					searchcode = " select distinct codigo_jerarquia from g_jerarquia_tipo2_nivel4 ";
					searchcode += " where cod_institucion=" + institucion + " and cod_metod='" + metodologiaid + "' and cod_grado='" + lista1 + "' and cod_area='" + lista[2] + "' and vigencia=" + vigencia;

					codigod = cursor.nombre(searchcode);

					if (codigod == null) {

						request.setAttribute("mensaje", setMensaje("Error: No se encontrn el cndigo de la jerarquia"));
						ir(2, ant, request, response);
						return;
					}
					// construccion de la consulta para verificar registros iguales Nombre
					searchson[1] = "select distinct DesNombre from " + datoMaestro.getTabla() + " where Descodjerar='" + codigod[0] + "' and DesVigencia='" + vigencia + "'  and (lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) = lower(ltrim(rtrim('" + texto[6] + "')))";
					searchson[1] += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) <> lower(ltrim(rtrim('" + r[6] + "'))))";

					searchson[2] = "select DesNombre from " + datoMaestro.getTabla() + " where Descodjerar='" + codigod[0] + "' and DesVigencia='" + vigencia + "' and (lower(ltrim(rtrim(" + datoMaestro.getCampo(5) + "))) = lower(ltrim(rtrim('" + texto[7] + "')))";
					searchson[2] += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(5) + "))) <> lower(ltrim(rtrim('" + r[7] + "'))))";

					// construccion de la consulta de actualizacinn
					actualizar = "update Descriptor_Valorativo set ";
					actualizar += " DESCODJERAR =ltrim(rtrim('" + codigod[0] + "')), ";
					actualizar += " DESCODTIPDES =ltrim(rtrim('" + lista[3] + "')), ";
					actualizar += " DESNOMBRE =ltrim(rtrim('" + texto[6] + "')), ";
					actualizar += " DESABREV =ltrim(rtrim('" + texto[7] + "')), ";
					actualizar += " DESDESCRIPCION =ltrim(rtrim('" + texto[8] + "')), ";
					actualizar += " DESCODPERINICIAL =ltrim(rtrim('" + lista[4] + "')), ";
					actualizar += " DESCODPERFINAL =ltrim(rtrim('" + lista[5] + "')) ";
					actualizar += " where DESCODJERAR = " + codigobd;
					actualizar += " and DESCODIGO = " + text;
					actualizar += " and DESVIGENCIA =" + vigencia;

					// busqueda de registros repetidos
					if (cursor.existe1(searchson)) {

						request.setAttribute("mensaje", setMensaje("El nombre o abreviatura indicados ya existen, cambielos"));
						ir(2, ant, request, response);
						return;
					} else {// select cod_grado,cod_area, DesCodTipDes, DesCodPerInicial,DesCodPerFinal, DesNombre,DesAbrev,DesDescripcion,DesVigencia,Descodjerar
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(lista1) && r[2].equals(lista2) && r[3].equals(lista[3]) && r[4].equals(lista[4]) && r[5].equals(lista[5]) && r[6].equals(texto[6]) && r[7].equals(texto[7]) && r[8].equals(texto[8]) && r[9].equals(vigencia))
							ir(2, ant, request, response);
						else {
							// actualizacion del registro
							if (cursor.registrar(actualizar)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
								ir(2, ant, request, response);
							}
						}
					}

					break;

				case 40:
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select * from " + datoMaestro.getTabla() + " where lower(ltrim(rtrim(";
					buscar += datoMaestro.getCampo(1) + "))) = lower(ltrim(rtrim('" + text + "')))";
					// obtencion del registro
					r = cursor.nombre(buscar, 1, 2);

					// construccion de la consulta del busqueda
					buscar = "select * from " + datoMaestro.getTabla() + " where lower(ltrim(rtrim(";
					buscar += datoMaestro.getCampo(2) + "))) = lower(ltrim(rtrim('" + texto[2] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(1) + "))) <> lower(ltrim(rtrim('" + text + "')))";
					buscar += " or lower(ltrim(rtrim(" + datoMaestro.getCampo(1) + ")))=lower(ltrim(rtrim('" + texto[1] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(2) + "))) <> lower(ltrim(rtrim('" + r[2] + "')))";

					// construccion de la consulta de actualizacion
					actualizar = "update " + datoMaestro.getTabla() + " set ";
					actualizar += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar += datoMaestro.getCampo(1) + "=ltrim(rtrim('" + texto[1] + "')) ";
					actualizar += " where " + datoMaestro.getCampo(1) + " = '" + text + "'";

					// busqueda de registros repetidos
					if (cursor.existe(buscar)) {

						request.setAttribute("mensaje", setMensaje("El nombre y/o cndigo indicado ya existen, cambielo"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[2].equals(texto[2]))
							ir(2, ant, request, response);
						else {
							// actualizacion del registro
							if (cursor.registrar(actualizar)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
								ir(2, ant, request, response);
								return;
							}
						}
					}

					break;

				case 42:// Escala Valorativa
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select * from " + datoMaestro.getTabla() + "";
					buscar += " where " + datoMaestro.getCampo(1) + " = '" + text + "'";
					r = cursor.nombre(buscar, 1, 4);

					searchson[1] = "select distinct evaarecodestu from evaluacion_area where (evaareeval1 =" + text + " or evaareeval2 =" + text + " or evaareeval3 =" + text + " or evaareeval4 =" + text + " or evaareeval5 =" + text + ") and evaarevigencia=" + vigencia + " and rownum<=1";
					searchson[2] = "select distinct evaasicodestud from evaluacion_asignatura where (evaasieval1 =" + text + " or evaasieval2 =" + text + " or evaasieval3 =" + text + " or evaasieval4 =" + text + " or evaasieval5 =" + text + ") and evaasivigencia=" + vigencia + " and rownum<=1";
					searchson[3] = "select distinct evalogcodestu from evaluacion_logro where evalogevalu =" + text + " and evalogvigencia=" + vigencia + " and rownum<=1";

					// construccion de la consulta del busqueda
					searchrepeated[1] = "select * from " + datoMaestro.getTabla() + " where lower(ltrim(rtrim(";
					/* nombre */searchrepeated[1] += datoMaestro.getCampo(2) + "))) = lower(ltrim(rtrim('" + texto[2] + "')))";
					/* nombre */searchrepeated[1] += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(2) + "))) <> lower(ltrim(rtrim('" + r[2] + "')))";

					// construccion de la consulta del busqueda
					searchrepeated[2] = "select * from " + datoMaestro.getTabla() + " where lower(ltrim(rtrim(" + datoMaestro.getCampo(3) + "))) = lower(ltrim(rtrim('" + texto[3] + "')))";
					searchrepeated[2] += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(3) + "))) <> lower(ltrim(rtrim('" + r[3] + "')))";

					// construccion de la consulta de actualizacion
					actualizar = "update " + datoMaestro.getTabla() + " set ";
					actualizar += datoMaestro.getCampo(4) + "=ltrim(rtrim('" + lista[1] + "')), ";
					actualizar += datoMaestro.getCampo(3) + "=ltrim(rtrim('" + texto[3] + "')), ";
					actualizar += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar += datoMaestro.getCampo(1) + " =ltrim(rtrim('" + texto[1] + "')) ";
					actualizar += " where " + datoMaestro.getCampo(1) + " = '" + text + "'";

					// construccion de la consulta de actualizacion
					actualizar1 = "update " + datoMaestro.getTabla() + " set ";
					actualizar1 += datoMaestro.getCampo(4) + "=ltrim(rtrim('" + lista1 + "')), ";
					actualizar1 += datoMaestro.getCampo(3) + "=ltrim(rtrim('" + texto[3] + "')), ";
					actualizar1 += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar1 += datoMaestro.getCampo(1) + " =ltrim(rtrim('" + texto[1] + "')) ";
					actualizar1 += " where " + datoMaestro.getCampo(1) + " = '" + text + "'";

					// busqueda de registros repetidos
					if (cursor.existe1(searchrepeated)) {

						request.setAttribute("mensaje", setMensaje("El nombre o abreviatura indicados ya existen, cambielos"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[2].equals(texto[2]) && r[3].equals(texto[3]) && r[4].equals(lista[1]))
							ir(2, ant, request, response);
						else {
							if (!cursor.existe1(searchson)) {
								if (cursor.registrar(actualizar)) {
									request.setAttribute("ok", "ok");
									request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
									ir(2, sig, request, response);
									return;
								} else {

									request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
									ir(2, ant, request, response);
									return;
								}
							}
							if (cursor.registrar(actualizar1)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
								ir(2, ant, request, response);
								return;
							}

						}
					}

					break;

				case 43: // G_Constante Tipo Escala valorativa
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden from G_Constante ";
					buscar += "where G_ConTipo = 23 and " + datoMaestro.getCampo(2) + " = " + text;

					// obtencion del registro
					r = cursor.nombre(buscar, 1, 4);
					// construccion de la consulta del busqueda

					// construccion de la consulta del busqueda
					buscar = "select * from " + datoMaestro.getTabla() + " where G_ConTipo = 23 and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) = lower(ltrim(rtrim('" + texto[2] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) <> lower(ltrim(rtrim('" + r[2] + "')))";

					// construccion de la consulta de actualizacion
					actualizar = "update G_Constante set ";
					actualizar += datoMaestro.getCampo(6) + " =ltrim(rtrim('" + texto[4] + "')), ";
					actualizar += datoMaestro.getCampo(5) + " =ltrim(rtrim('" + texto[3] + "')), ";
					actualizar += datoMaestro.getCampo(4) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[1] + "')) ";
					actualizar += "where G_ConTipo = 23 and " + datoMaestro.getCampo(2) + " = " + text;

					// busqueda de registros repetidos
					if (cursor.existe(buscar)) {

						request.setAttribute("mensaje", setMensaje("El nombre indicado ya existe, cambielo"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[2].equals(texto[2]) && r[3].equals(texto[3]) && r[4].equals(texto[4]))
							ir(2, ant, request, response);
						else {
							// actualizacion del registro
							if (cursor.registrar(actualizar)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
								ir(2, ant, request, response);
								return;
							}
						}
					}

					break;

				case 44: // G_Constante RangoTarifa
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden from G_Constante ";
					buscar += "where G_ConTipo = 20 and " + datoMaestro.getCampo(2) + " = " + text;

					// obtencion del registro
					r = cursor.nombre(buscar, 1, 4);
					// construccion de la consulta del busqueda

					// construccion de la consulta del busqueda
					buscar = "select * from " + datoMaestro.getTabla() + " where G_ConTipo = 20 and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) = lower(ltrim(rtrim('" + texto[2] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) <> lower(ltrim(rtrim('" + r[2] + "')))";

					// construccion de la consulta de actualizacion
					actualizar = "update G_Constante set ";
					actualizar += datoMaestro.getCampo(6) + " =ltrim(rtrim('" + texto[4] + "')), ";
					actualizar += datoMaestro.getCampo(5) + " =ltrim(rtrim('" + texto[3] + "')), ";
					actualizar += datoMaestro.getCampo(4) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[1] + "')) ";
					actualizar += "where G_ConTipo = 20 and " + datoMaestro.getCampo(2) + " = " + text;

					// busqueda de registros repetidos
					if (cursor.existe(buscar)) {

						request.setAttribute("mensaje", setMensaje("El nombre indicado ya existe, cambielo"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[2].equals(texto[2]) && r[3].equals(texto[3]) && r[4].equals(texto[4]))
							ir(2, ant, request, response);
						else {
							// actualizacion del registro
							if (cursor.registrar(actualizar)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								Recursos.setRecurso(Recursos.TARIFA);
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
								ir(2, ant, request, response);
								return;
							}
						}
					}

					break;

				case 45:// G_Constante Asociaciones privadas
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden from G_Constante ";
					buscar += "where G_ConTipo= 21 and " + datoMaestro.getCampo(2) + " = " + text;

					// obtencion del registro
					r = cursor.nombre(buscar, 1, 4);
					// construccion de la consulta del busqueda

					// construccion de la consulta del busqueda
					buscar = "select * from " + datoMaestro.getTabla() + " where G_ConTipo = 21 and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) = lower(ltrim(rtrim('" + texto[2] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) <> lower(ltrim(rtrim('" + r[2] + "')))";

					// construccion de la consulta de actualizacion
					actualizar = "update G_Constante set ";
					actualizar += datoMaestro.getCampo(6) + " =ltrim(rtrim('" + texto[4] + "')), ";
					actualizar += datoMaestro.getCampo(5) + " =ltrim(rtrim('" + texto[3] + "')), ";
					actualizar += datoMaestro.getCampo(4) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[1] + "')) ";
					actualizar += "where G_ConTipo = 21 and " + datoMaestro.getCampo(2) + " = " + text;

					// busqueda de registros repetidos
					if (cursor.existe(buscar)) {

						request.setAttribute("mensaje", setMensaje("El nombre indicado ya existe, cambielo"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[2].equals(texto[2]) && r[3].equals(texto[3]) && r[4].equals(texto[4]))
							ir(2, sig, request, response);
						else {
							// actualizacion del registro
							if (cursor.registrar(actualizar)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								Recursos.setRecurso(Recursos.ASOCIACION);
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
								ir(2, ant, request, response);
								return;
							}
						}
					}

					break;

				case 46: // G_Constante Idioma
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden from G_Constante ";
					buscar += "where G_ConTipo = 22 and " + datoMaestro.getCampo(2) + " = " + text;

					// obtencion del registro
					r = cursor.nombre(buscar, 1, 4);
					// construccion de la consulta del busqueda

					// construccion de la consulta del busqueda
					buscar = "select * from " + datoMaestro.getTabla() + " where G_ConTipo = 22 and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) = lower(ltrim(rtrim('" + texto[2] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) <> lower(ltrim(rtrim('" + r[2] + "')))";

					// construccion de la consulta de actualizacion
					actualizar = "update G_Constante set ";
					actualizar += datoMaestro.getCampo(6) + " =ltrim(rtrim('" + texto[4] + "')), ";
					actualizar += datoMaestro.getCampo(5) + " =ltrim(rtrim('" + texto[3] + "')), ";
					actualizar += datoMaestro.getCampo(4) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[1] + "')) ";
					actualizar += "where G_ConTipo = 22 and " + datoMaestro.getCampo(2) + " = " + text;

					// busqueda de registros repetidos
					if (cursor.existe(buscar)) {

						request.setAttribute("mensaje", setMensaje("El nombre indicado ya existe, cambielo"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[2].equals(texto[2]) && r[3].equals(texto[3]) && r[4].equals(texto[4]))
							ir(2, ant, request, response);
						else {
							// actualizacion del registro
							if (cursor.registrar(actualizar)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								Recursos.setRecurso(Recursos.IDIOMA);
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
								ir(2, ant, request, response);
								return;
							}
						}
					}

					break;

				case 47: // G_Constante Cargo GE
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden from G_Constante ";
					buscar += "where G_ConTipo = 24 and " + datoMaestro.getCampo(2) + " = " + text;

					// obtencion del registro
					r = cursor.nombre(buscar, 1, 4);
					// construccion de la consulta del busqueda

					// construccion de la consulta del busqueda
					buscar = "select * from " + datoMaestro.getTabla() + " where G_ConTipo = 24 and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) = lower(ltrim(rtrim('" + texto[2] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) <> lower(ltrim(rtrim('" + r[2] + "')))";

					// construccion de la consulta de actualizacion
					actualizar = "update G_Constante set ";
					actualizar += datoMaestro.getCampo(6) + " =ltrim(rtrim('" + texto[4] + "')), ";
					actualizar += datoMaestro.getCampo(5) + " =ltrim(rtrim('" + texto[3] + "')), ";
					actualizar += datoMaestro.getCampo(4) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[1] + "')) ";
					actualizar += "where G_ConTipo = 24 and " + datoMaestro.getCampo(2) + " = " + text;

					// busqueda de registros repetidos
					if (cursor.existe(buscar)) {

						request.setAttribute("mensaje", setMensaje("El nombre indicado ya existe, cambielo"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[2].equals(texto[2]) && r[3].equals(texto[3]) && r[4].equals(texto[4]))
							ir(2, ant, request, response);
						else {
							// actualizacion del registro
							if (cursor.registrar(actualizar)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								Recursos.setRecurso(Recursos.TIPOCARGO);
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
								ir(2, ant, request, response);
								return;
							}
						}
					}

					break;

				case 50:// ROT_TIPO_RECESO
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select * from " + datoMaestro.getTabla() + " where lower(ltrim(rtrim(";
					buscar += datoMaestro.getCampo(1) + "))) = lower(ltrim(rtrim('" + text + "')))";
					// obtencion del registro
					r = cursor.nombre(buscar, 1, 3);

					// construccion de la consulta del busqueda
					buscar = "select * from " + datoMaestro.getTabla() + " where lower(ltrim(rtrim(";
					buscar += datoMaestro.getCampo(2) + "))) = lower(ltrim(rtrim('" + texto[2] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(1) + "))) <> lower(ltrim(rtrim('" + text + "')))";
					buscar += " or lower(ltrim(rtrim(" + datoMaestro.getCampo(1) + ")))=lower(ltrim(rtrim('" + texto[1] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(2) + "))) <> lower(ltrim(rtrim('" + r[2] + "')))";

					// construccion de la consulta de actualizacion
					actualizar = "update " + datoMaestro.getTabla() + " set ";
					actualizar += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar += datoMaestro.getCampo(1) + "=ltrim(rtrim('" + texto[1] + "')) ";
					actualizar += " where " + datoMaestro.getCampo(1) + " = '" + text + "'";

					// busqueda de registros repetidos
					if (cursor.existe(buscar)) {

						request.setAttribute("mensaje", setMensaje("El nombre y/o cndigo indicado ya existen, cambielo"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[2].equals(texto[2]))
							ir(2, ant, request, response);
						else {
							// actualizacion del registro
							if (cursor.registrar(actualizar)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								Recursos.setRecurso(Recursos.TIPORECESO);
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje("No se pudo actualizar ya que tiene registros asociados"));
								ir(2, ant, request, response);
								return;
							}
						}
					}
					break;

				case 49: // G_Constante tipo atencinn especial
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden from G_Constante ";
					buscar += "where G_ConTipo = 31 and " + datoMaestro.getCampo(2) + " = " + text;
					r = cursor.nombre(buscar, 1, 4);

					// construccion de la consulta del busqueda
					buscar = "select * from " + datoMaestro.getTabla() + " where G_ConTipo = 31 and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) = lower(ltrim(rtrim('" + texto[2] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) <> lower(ltrim(rtrim('" + r[2] + "')))";

					// construccion de la consulta de actualizacion
					actualizar = "update G_Constante set ";
					actualizar += datoMaestro.getCampo(6) + " =ltrim(rtrim('" + texto[4] + "')), ";
					actualizar += datoMaestro.getCampo(5) + " =ltrim(rtrim('" + texto[3] + "')), ";
					actualizar += datoMaestro.getCampo(4) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[1] + "')) ";
					actualizar += "where G_ConTipo = 31 and " + datoMaestro.getCampo(2) + " = " + text;

					// busqueda de registros repetidos
					if (cursor.existe(buscar)) {

						request.setAttribute("mensaje", setMensaje("El nombre indicado ya existe, cambielo"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[2].equals(texto[2]) && r[3].equals(texto[3]) && r[4].equals(texto[4]))
							ir(2, ant, request, response);
						else {
							// actualizacion del registro
							if (cursor.registrar(actualizar)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								Recursos.setRecurso(Recursos.TIPOATENCIONESPECIAL);
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
								ir(2, ant, request, response);
								return;
							}
						}
					}

					break;

				case 51:// G_Constante tipo especialidad
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden from G_Constante ";
					buscar += "where G_ConTipo = 34 and " + datoMaestro.getCampo(2) + " = " + text;
					r = cursor.nombre(buscar, 1, 4);

					// construccion de la consulta del busqueda
					buscar = "select * from " + datoMaestro.getTabla() + " where G_ConTipo = 34 and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) = lower(ltrim(rtrim('" + texto[2] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) <> lower(ltrim(rtrim('" + r[2] + "')))";

					// construccion de la consulta de actualizacion
					actualizar = "update G_Constante set ";
					actualizar += datoMaestro.getCampo(6) + " =ltrim(rtrim('" + texto[4] + "')), ";
					actualizar += datoMaestro.getCampo(5) + " =ltrim(rtrim('" + texto[3] + "')), ";
					actualizar += datoMaestro.getCampo(4) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[1] + "')) ";
					actualizar += "where G_ConTipo = 34 and " + datoMaestro.getCampo(2) + " = " + text;

					// busqueda de registros repetidos
					if (cursor.existe(buscar)) {

						request.setAttribute("mensaje", setMensaje("El nombre indicado ya existe, cambielo"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[2].equals(texto[2]) && r[3].equals(texto[3]) && r[4].equals(texto[4]))
							ir(2, ant, request, response);
						else {
							// actualizacion del registro
							if (cursor.registrar(actualizar)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								Recursos.setRecurso(Recursos.TIPOESPECIALIDAD);
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
								ir(2, ant, request, response);
								return;
							}
						}
					}

					break;

				case 52:// G_Constante Tipos de Modalidad
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden from G_Constante ";
					buscar += "where G_ConTipo = 33 and " + datoMaestro.getCampo(2) + " = " + text;
					r = cursor.nombre(buscar, 1, 4);

					// construccion de la consulta del busqueda
					buscar = "select * from " + datoMaestro.getTabla() + " where G_ConTipo = 33 and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) = lower(ltrim(rtrim('" + texto[2] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) <> lower(ltrim(rtrim('" + r[2] + "')))";

					// construccion de la consulta de actualizacion
					actualizar = "update G_Constante set ";
					actualizar += datoMaestro.getCampo(6) + " =ltrim(rtrim('" + texto[4] + "')), ";
					actualizar += datoMaestro.getCampo(5) + " =ltrim(rtrim('" + texto[3] + "')), ";
					actualizar += datoMaestro.getCampo(4) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[1] + "')) ";
					actualizar += "where G_ConTipo = 33 and " + datoMaestro.getCampo(2) + " = " + text;

					// busqueda de registros repetidos
					if (cursor.existe(buscar)) {

						request.setAttribute("mensaje", setMensaje("El nombre indicado ya existe, cambielo"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[2].equals(texto[2]) && r[3].equals(texto[3]) && r[4].equals(texto[4]))
							ir(2, ant, request, response);
						else {
							// actualizacion del registro
							if (cursor.registrar(actualizar)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								Recursos.setRecurso(Recursos.TIPOMODALIDAD);
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
								ir(2, ant, request, response);
								return;
							}
						}
					}

					break;

				case 53:// G_Constante Tipos de nnfasis
					checksentencia = null;
					// construccion de la consulta del registro
					buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden from G_Constante ";
					buscar += "where G_ConTipo = 32 and " + datoMaestro.getCampo(2) + " = " + text;
					r = cursor.nombre(buscar, 1, 4);

					// construccion de la consulta del busqueda
					buscar = "select * from " + datoMaestro.getTabla() + " where G_ConTipo = 32 and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) = lower(ltrim(rtrim('" + texto[2] + "')))";
					buscar += " and lower(ltrim(rtrim(" + datoMaestro.getCampo(4) + "))) <> lower(ltrim(rtrim('" + r[2] + "')))";

					// construccion de la consulta de actualizacion
					actualizar = "update G_Constante set ";
					actualizar += datoMaestro.getCampo(6) + " =ltrim(rtrim('" + texto[4] + "')), ";
					actualizar += datoMaestro.getCampo(5) + " =ltrim(rtrim('" + texto[3] + "')), ";
					actualizar += datoMaestro.getCampo(4) + " =ltrim(rtrim('" + texto[2] + "')), ";
					actualizar += datoMaestro.getCampo(2) + " =ltrim(rtrim('" + texto[1] + "')) ";
					actualizar += "where G_ConTipo = 32 and " + datoMaestro.getCampo(2) + " = " + text;

					// busqueda de registros repetidos
					if (cursor.existe(buscar)) {

						request.setAttribute("mensaje", setMensaje("El nombre indicado ya existe, cambielo"));
						ir(2, ant, request, response);
						return;
					} else {
						// verificar si no se hicieron cambios en el registro
						if (r[1].equals(texto[1]) && r[2].equals(texto[2]) && r[3].equals(texto[3]) && r[4].equals(texto[4]))
							ir(2, ant, request, response);
						else {
							// actualizacion del registro
							if (cursor.registrar(actualizar)) {
								request.setAttribute("ok", "ok");
								request.setAttribute("mensaje", "Los datos fueron actualizados satisfactoriamente");
								Recursos.setRecurso(Recursos.TIPOENFASIS);
								ir(2, sig, request, response);
								return;
							} else {

								request.setAttribute("mensaje", setMensaje(cursor.getMensaje()));
								ir(2, ant, request, response);
								return;
							}
						}
					}

					break;

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		} finally {
			if (cursor != null)
				cursor.cerrar();
			if (util != null)
				util.cerrar();
		}

	}

	/**
	 * Concatena el parametro en una variable que contiene los mensajes que se van a enviar a la vista
	 * 
	 * @param String
	 *            s
	 */
	public String setMensaje(String s) {
		String mensaje = "VERIFIQUE LA SIGUIENTE información: \n\n";
		mensaje += "  - " + s + "\n";
		return mensaje;
	}

	/**
	 * Redirige el control a otro servlet
	 * 
	 * @param int
	 *            a: 1=redirigir como 'include', 2=redirigir como 'forward'
	 * @param String
	 *            s
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public void ir(int a, String s, HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
		if (cursor != null)
			cursor.cerrar();
		if (dao != null)
			dao.cerrar();
		if (util != null)
			util.cerrar();
		RequestDispatcher rd = getServletContext().getRequestDispatcher(s);
		if (a == 1)
			rd.include(rq, rs);
		else
			rd.forward(rq, rs);
	}

	/*
	 * Cierra cursor
	 * 
	 */
	public void destroy() {
		if (cursor != null)
			cursor.cerrar();

		cursor = null;
		util = null;
	}
}