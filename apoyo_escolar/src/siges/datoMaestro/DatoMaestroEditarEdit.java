package siges.datoMaestro;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletConfig;
import java.io.IOException;
import siges.datoMaestro.beans.*;
import siges.dao.*;
import siges.login.beans.*;

/**
 * Nombre: DatoMaestroEditarEdit.java <BR>
 * Descripcinn: Peticion desde DatoMaestroLista.jsp<BR>
 * Funciones de la pngina: Usar el bean DatoMaestroBean de la sesion para generar un un formulario de edicion de registro de ese dato maestro <BR>
 * Entidades afectadas: La correspondiente al dato maestro que se especifica en el objeto DatoMaestroBean (solo lectura) <BR>
 * Fecha de modificacinn: 20/07/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */

public class DatoMaestroEditarEdit extends HttpServlet {

	private Cursor cursor;// objeto que maneja las sentencias sql

	private Util util;

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
	 * @param: HttpServletRequest request
	 * @param: HttpServletResponse response
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String institucion;
		Login login;
		String metodologiaid;// variable que almacena el id de la metodologia de la institucion

		int dato;// variable que captura numero de dato maestro del que vamos a mostrar datos
		String sig;// nombre de la pagina a la que ira despues de ejecutar los comandos de esta
		String ant;// pagina a la que ira en caso de que no se pueda procesar esta pagina
		String er;// nombre de la pagina a la que ira si hay errores
		String home;// nombre de la pagina a la que ira si hay errores
		String id;
		String text;// variable que contiene el id del registro
		String text2;// variable que contiene la columna 2 del registro
		String text3;// variable que contiene la columna 3 del registro
		String text4;// variable que contiene la columna 4 del registro
		String text5;// variable que contiene la columna 5 del registro
		String text6;// variable que contiene la columna 6 del registro
		String text7;// variable que contiene la columna 7 del registro
		String text8;// variable que contiene la columna 8 del registro
		String text9;// variable que contiene la columna 9 del registro
		String criterioLista[];// vector que contiene las sentencias sql que permiten llenar de opciones los 'selects' html
		String tituloLista[];// titulo que tendran los 'selects' html en el formulario de busqueda
		String tituloTexto[];// titulo de las columnas que tendra la tabla de resultados
		String tituloCheck[];// titulo de las columnas que tendra la tabla de resultados
		String tituloCheckSelected[];// titulo de las columnas que tendra la tabla de resultados
		String searchson[];// consulta sql para averiguar si el campo tiene registros asociados
		String algow[];// vector que contiene los valores
		String searchcode;// consulta sql para encontrar el cndigo de la jerarquia
		String x, y, z;// valor de un select para hacer el siguiente select dinamico
		String checksentencia[];// vector que contiene los items seleccionados de los 'select'
		String lista[];// vector que contiene los items (de un select) del registro indicado por el usuario
		String listauno;
		String check[];// vector que contiene los items seleccionados de los 'check'
		String texto[];// vector que contiene el texto del registro inidicado por el usuario
		String[] codigol;
		String buscar;// sentencia sql para traer todo el registro a partir del parametro 'text'
		String buscarhijo;
		String buscarhijo1;
		String buscarhijo2;
		String vigencia;
		String filtro;// variable que captura los 'selects' html o los inputs de tipo 'TEXT' del formulario de edicion de el dato maestro seleccionado
		DatoMaestroBean datoMaestro;// objeto que tendra los datos referentes al dato maestro escogido
		datoMaestro = (DatoMaestroBean) request.getSession().getAttribute("datoMaestro");
		sig = "/DatoMaestroEditar.jsp";// pagina a la que se dara el control despues de procesar este servlet
		ant = "/index.jsp";// pagina a la que se dara el control si algo falla
		er = "/error.jsp";
		home = "/bienvenida.jsp";
		if (datoMaestro == null) {
			ir(2, home, request, response);
			return;
		}
		codigol = null;
		algow = null;
		searchson = null;
		login = (Login) request.getSession().getAttribute("login");

		buscar = buscarhijo = buscarhijo1 = buscarhijo2 = filtro = text2 = text3 = text4 = text5 = text6 = text7 = text8 = text9 = "";
		criterioLista = new String[8];
		tituloLista = new String[8];
		tituloTexto = new String[10];
		tituloCheck = new String[6];
		tituloCheckSelected = new String[6];
		check = new String[20];
		lista = new String[8];
		texto = new String[10];
		text = request.getParameter("text");
		text2 = request.getParameter("text2");
		// System.out.println("valor de text2: "+request.getParameter("text2"));
		text3 = request.getParameter("text3");
		text4 = request.getParameter("text4");
		text5 = request.getParameter("text5");
		text6 = request.getParameter("text6");
		text7 = request.getParameter("text7");
		text8 = request.getParameter("text8");
		text9 = request.getParameter("text9");

		x = (request.getParameter("x") != null) ? request.getParameter("x") : new String("");
		y = (request.getParameter("y") != null) ? request.getParameter("y") : new String("");
		z = (request.getParameter("z") != null) ? request.getParameter("z") : new String("");
		listauno = (request.getParameter("listauno") != null) ? request.getParameter("listauno") : new String("");

		String zz[] = { text, text2, text3, text4, text5, text6, text7, text8, text9 };

		lista[1] = (request.getParameter("lista1") != null) ? request.getParameter("lista1") : new String("0");// captura el valor de el select cuyo nombre es 'lista1'
		lista[2] = (request.getParameter("lista2") != null) ? request.getParameter("lista2") : new String("0");// captura el valor de el select cuyo nombre es 'lista2'
		lista[3] = (request.getParameter("lista3") != null) ? request.getParameter("lista3") : new String("0");// captura el valor de el select cuyo nombre es 'lista3'
		lista[4] = (request.getParameter("lista4") != null) ? request.getParameter("lista4") : new String("0");// captura el valor de el select cuyo nombre es 'lista4'
		lista[5] = (request.getParameter("lista5") != null) ? request.getParameter("lista5") : new String("0");// captura el valor de el select cuyo nombre es 'lista5'
		lista[6] = (request.getParameter("lista6") != null) ? request.getParameter("lista6") : new String("0");// captura el valor de el select cuyo nombre es 'lista6'

		institucion = login.getInstId();
		metodologiaid = login.getMetodologiaId();
		try {
			cursor = new Cursor();
			util = new Util(cursor);
			dao = new Dao(cursor);
			searchson = new String[20];

			vigencia = dao.getVigencia();

			if (vigencia == null) {
				System.out.println("VIGENCIA NULA");
				ir(2, home, request, response);
				return;
			}

			dato = Integer.parseInt(datoMaestro.getDato());// numero del dato maestro al que hacemos referencia

			switch (dato) {

			// Rige el comportamiento por el orden de las tablas

			case 1:// G_Constante Jornada

				buscarhijo = "select distinct jorcodins from jornada where jorcodigo =" + text + " and rownum<=1";

				// consulta para extraer el registro
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev, G_ConOrden from G_Constante ";
				buscar += "where G_ConTipo = 5 and " + datoMaestro.getCampo(2) + " = " + text;
				// llenar el vectFor 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 4);
				tituloTexto[1] = "Cndigo";
				tituloTexto[2] = "Nombre";
				tituloTexto[3] = "Abreviatura";
				tituloTexto[4] = "Orden";

				if (!cursor.existe(buscarhijo))
					filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				else
					filtro = cursor.listadatomaestronoedit(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 2:// G_Constante
				buscarhijo = "select distinct estcodigo from estudiante where esttipodoc =" + text + " and rownum<=1";
				// consulta para extraer el registro
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev, G_ConOrden from G_Constante ";
				buscar += "where G_ConTipo = 10 and " + datoMaestro.getCampo(2) + " = " + text;
				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 4);
				tituloTexto[1] = "Cndigo";
				tituloTexto[2] = "Nombre";
				tituloTexto[3] = "Abreviatura";
				tituloTexto[4] = "Orden";

				if (!cursor.existe(buscarhijo))
					filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				else
					filtro = cursor.listanoedit(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 3:// G_Constante Departamento
				// consulta para extraer el registro
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev, G_ConOrden from G_Constante ";
				buscar += "where G_ConTipo = 6 and " + datoMaestro.getCampo(2) + " = " + text;
				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 4);
				tituloTexto[1] = "Cndigo";// titulo del input 1
				tituloTexto[2] = "Nombre";// titulo del input 2
				tituloTexto[3] = "Abreviatura";// titulo del input 3
				tituloTexto[4] = "Orden";// titulo del input 4

				filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 4:// G_Constante Nivel

				searchson[1] = "select distinct nivcodigo from nivel where nivcodigo =" + text + " and rownum<=1";
				searchson[2] = " select distinct g_gracodigo from g_grado where g_granivel=" + text + " and rownum<=1";
				// consulta para extraer el registro
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev, G_ConOrden from G_Constante ";
				buscar += "where G_ConTipo = 1 and " + datoMaestro.getCampo(2) + " = " + text;
				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 4);
				tituloTexto[1] = "Cndigo";// titulo del input 1
				tituloTexto[2] = "Nombre";// titulo del input 2
				tituloTexto[3] = "Abreviatura";// titulo del input 3
				tituloTexto[4] = "Orden";// titulo del input 4

				if (!cursor.existe1(searchson))
					filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				else
					filtro = cursor.listadatomaestronoedit(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 5:
				// consulta para extraer el registro
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev, G_ConOrden from G_Constante ";
				buscar += "where G_ConTipo = 2 and " + datoMaestro.getCampo(2) + " = " + text;

				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 4);
				tituloTexto[1] = "Cndigo";// titulo del input 1
				tituloTexto[2] = "Nombre";// titulo del input 2
				tituloTexto[3] = "Abreviatura";// titulo del input 3
				tituloTexto[4] = "Orden";// titulo del input 4

				filtro = cursor.lista(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 6:// G_TipoEspacio
				buscarhijo = "select distinct espcodigo from espacio_fisico where esptipo =" + text + " and rownum<=1";
				// consulta para extraer el registro
				buscar = "select * from " + datoMaestro.getTabla() + " where " + datoMaestro.getCampo(1) + " = '" + text + "'";
				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 2);
				tituloTexto[1] = "Codigo";// titulo del input 1
				tituloTexto[2] = "Nombre";// titulo del input 2

				if (!cursor.existe(buscarhijo))
					filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				else
					filtro = cursor.listanoedit(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 8:// G_TipoReconocimiento
				// consulta para extraer el registro
				buscar = "select * from " + datoMaestro.getTabla() + " where " + datoMaestro.getCampo(1) + " = '" + text + "' and rownum<=1";
				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 2);
				tituloTexto[1] = "Codigo";// titulo del input 1
				tituloTexto[2] = "Nombre";// titulo del input 2

				filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 9:// G_Constante
				buscarhijo = "select distinct inscodigo from institucion where insmetodologia =" + text + " and rownum<=1";
				// consulta para extraer el registro
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev, G_ConOrden from G_Constante ";
				buscar += "where G_ConTipo = 3 and " + datoMaestro.getCampo(2) + " = " + text;
				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 4);
				tituloTexto[1] = "Cndigo";// titulo del input 1
				tituloTexto[2] = "Nombre";// titulo del input 2
				tituloTexto[3] = "Abreviatura";// titulo del input 3
				tituloTexto[4] = "Orden";// titulo del input 4

				if (!cursor.existe(buscarhijo))
					filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				else
					filtro = cursor.listadatomaestronoedit(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 10:// LOCALIDAD
				// consulta para extraer el registro
				buscar = "select G_ConCodigo,G_ConNombre,G_ConCodPadre from G_Constante ";
				buscar += " where G_ConTipo = 8 and " + datoMaestro.getCampo(2) + " = " + text;
				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 3);
				tituloTexto[1] = "Cndigo";// titulo del input 1
				tituloTexto[2] = "Nombre";// titulo del input 1
				// llenar el vector 'lista' con los datos del registro
				lista[1] = texto[3];
				criterioLista[1] = "select G_ConCodigo,G_ConNombre from G_Constante where G_ConTipo = 7";// datos del primer 'select'
				tituloLista[1] = "Municipio";// titulo del primer 'select'

				filtro = cursor.lista(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 11:// G_Constante

				buscarhijo = "select distinct espcodigo from espacio_fisico where esptipoocupante =" + text + " and rownum<=1";

				// consulta para extraer el registro
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev, G_ConOrden from G_Constante ";
				buscar += "where G_ConTipo = 9 and " + datoMaestro.getCampo(2) + " = " + text;
				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 4);
				tituloTexto[1] = "Cndigo";// titulo del input 1
				tituloTexto[2] = "Nombre";// titulo del input 2
				tituloTexto[3] = "Abreviatura";// titulo del input 3
				tituloTexto[4] = "Orden";// titulo del input 4

				if (!cursor.existe(buscarhijo))
					filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				else
					filtro = cursor.listanoedit(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 12:// G_Constante
				// consulta para extraer el registro
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev, G_ConOrden from G_Constante ";
				buscar += "where G_ConTipo = 4 and " + datoMaestro.getCampo(2) + " = " + text;

				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 4);
				tituloTexto[1] = "Cndigo";// titulo del input 1
				tituloTexto[2] = "Nombre";// titulo del input 2
				tituloTexto[3] = "Abreviatura";// titulo del input 3
				tituloTexto[4] = "Orden";// titulo del input 4

				filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 13:// Condicinn
				buscarhijo = "select distinct estcodigo from estudiante where estcondicion =" + text + " and rownum<=1";
				// consulta para extraer el registro
				buscar = "select * from " + datoMaestro.getTabla() + " where " + datoMaestro.getCampo(1) + " = '" + text + "'";
				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 2);
				tituloTexto[1] = "Cndigo";
				tituloTexto[2] = "Nombre";

				if (!cursor.existe(buscarhijo))
					filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				else
					filtro = cursor.listanoedit(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 14:// G_Constante

				buscarhijo = "select distinct concodigo from convivencia where contipo =" + text + " and rownum<=1";

				// consulta para extraer el registro
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev, G_ConOrden from G_Constante ";
				buscar += " where G_ConTipo = 11 and " + datoMaestro.getCampo(2) + " = " + text;

				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 4);
				tituloTexto[1] = "Cndigo";
				tituloTexto[2] = "Nombre";
				tituloTexto[3] = "Abreviatura";
				tituloTexto[4] = "Orden";

				if (!cursor.existe(buscarhijo))
					filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				else
					filtro = cursor.listanoedit(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 15:// G_Constante
				// consulta para extraer el registro
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev, G_ConOrden from G_Constante ";
				buscar += "where G_ConTipo = 12 and " + datoMaestro.getCampo(2) + " = " + text;

				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 4);
				tituloTexto[1] = "Cndigo";// titulo del input 1
				tituloTexto[2] = "Nombre";// titulo del input 2
				tituloTexto[3] = "Abreviatura";// titulo del input 3
				tituloTexto[4] = "Orden";// titulo del input 4

				filtro = cursor.lista(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 16:// G_Constante
				// consulta para extraer el registro
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev, G_ConOrden from G_Constante ";
				buscar += "where G_ConTipo = 13 and " + datoMaestro.getCampo(2) + " = " + text;
				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 4);
				tituloTexto[1] = "Cndigo";
				tituloTexto[2] = "Nombre";
				tituloTexto[3] = "Abreviatura";
				tituloTexto[4] = "Orden";

				filtro = cursor.lista(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 17:// G_Constante

				buscarhijo = "select distinct estcodigo from estudiante where estetnia =" + text + " and rownum<=1";
				// consulta para extraer el registro
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev, G_ConOrden from G_Constante ";
				buscar += "where G_ConTipo = 14 and " + datoMaestro.getCampo(2) + " = " + text;
				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 4);
				tituloTexto[1] = "Cndigo";// titulo del input 1
				tituloTexto[2] = "Nombre";// titulo del input 2
				tituloTexto[3] = "Abreviatura";// titulo del input 3
				tituloTexto[4] = "Orden";// titulo del input 4

				if (!cursor.existe(buscarhijo))
					filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				else
					filtro = cursor.listanoedit(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 18:// G_Constante

				searchson[1] = "select distinct evaarecodjerar from evaluacion_area where (evaaremotaus1=" + text + " or evaaremotaus2=" + text + " or evaaremotaus3=" + text + " or evaaremotaus4  =" + text + " or evaaremotaus5  =" + text + " ) and rownum<=1";
				searchson[2] = "select distinct evaasicodjerar from evaluacion_asignatura where (evaasimotaus1=" + text + " or evaasimotaus2=" + text + " or evaasimotaus3=" + text + " or evaasimotaus4=" + text + " or evaasimotaus5=" + text + ") and rownum<=1";

				// consulta para extraer el registro
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev, G_ConOrden from G_Constante ";
				buscar += "where G_ConTipo = 15 and " + datoMaestro.getCampo(2) + " = " + text;
				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 4);
				tituloTexto[1] = "Cndigo";
				tituloTexto[2] = "Nombre";
				tituloTexto[3] = "Abreviatura";
				tituloTexto[4] = "Orden";

				if (!cursor.existe1(searchson))
					filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				else
					filtro = cursor.listanoedit(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 19:// G_Constante
				// consulta para extraer el registro
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev, G_ConOrden from G_Constante ";
				buscar += " where G_ConTipo = 16 and " + datoMaestro.getCampo(2) + " = " + text;

				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 4);
				tituloTexto[1] = "Cndigo";// titulo del input 1
				tituloTexto[2] = "Nombre";// titulo del input 2
				tituloTexto[3] = "Abreviatura";// titulo del input 3
				tituloTexto[4] = "Orden";// titulo del input 4

				filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 21:
				// consulta para extraer el registro
				buscar = "select G_ConCodigo,G_ConNombre,G_ConCodPadre from G_Constante ";
				buscar += " where G_ConTipo = 7 and " + datoMaestro.getCampo(2) + " = " + text;

				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 3);
				tituloTexto[1] = "Cndigo";// titulo del input 1
				tituloTexto[2] = "Nombre";// titulo del input 1
				// llenar el vector 'lista' con los datos del registro
				lista[1] = texto[3];
				criterioLista[1] = "select G_ConCodigo,G_ConNombre from G_Constante where G_ConTipo = 6";// datos del primer 'select'
				tituloLista[1] = "Departamento";// titulo del primer 'select'

				filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 22:
				buscarhijo = "select distinct arecodigo from area where arecodigo =" + text + " and rownum<=1";
				// consulta para extraer el registro
				buscar = "select G_AreCodigo, G_AreNombre, G_AreTipo ";
				buscar += " from G_Area where " + datoMaestro.getCampo(1) + " = " + text;
				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 3);
				tituloTexto[1] = "Cndigo";// titulo del input 1
				tituloTexto[2] = "Nombre";// titulo del input 1
				// llenar el vector 'lista' con los datos del registro
				lista[1] = texto[3];
				criterioLista[1] = "SELECT  1 id, 'Obligatoria' nombre FROM DUAL UNION  SELECT 2 id, 'Opcional' nombre FROM DUAL";
				tituloLista[1] = "Tipo de área";// titulo del primer 'select'

				filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 23:// g_grado

				buscarhijo = "select distinct gracodigo from grado where gracodigo =" + text + " and rownum<=1";

				buscar = "select * from " + datoMaestro.getTabla() + " where " + datoMaestro.getCampo(2) + " = '" + text + "'";

				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 5);

				lista[1] = texto[1];
				criterioLista[1] = "select G_ConCodigo,G_ConNombre from G_Constante where G_ConTipo = 1";
				tituloLista[1] = "Nivel";

				tituloTexto[2] = "Cndigo";
				tituloTexto[3] = "Nombre";
				tituloTexto[4] = "Abreviatura";
				tituloTexto[5] = "Orden";

				if (!cursor.existe(buscarhijo))
					filtro = cursor.listadatogeneral(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				else
					filtro = cursor.listaggrado(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 24:// G_Constante

				buscarhijo = "select distinct inscodigo from institucion where inscalendario =" + text + " and rownum<=1";
				// consulta para extraer el registro
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev, G_ConOrden from G_Constante ";
				buscar += "where G_ConTipo = 17 and " + datoMaestro.getCampo(2) + " = " + text;
				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 4);
				tituloTexto[1] = "Cndigo";
				tituloTexto[2] = "Nombre";
				tituloTexto[3] = "Abreviatura";
				tituloTexto[4] = "Orden";

				if (!cursor.existe(buscarhijo))
					filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				else
					filtro = cursor.listadatomaestronoedit(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 25:// G_Constante

				buscarhijo = "select distinct inscodigo from institucion where inspropiedad =" + text + " and rownum<=1";
				// consulta para extraer el registro
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev, G_ConOrden from G_Constante ";
				buscar += "where G_ConTipo = 18 and " + datoMaestro.getCampo(2) + " = " + text;

				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 4);
				tituloTexto[1] = "Cndigo";// titulo del input 1
				tituloTexto[2] = "Nombre";// titulo del input 2
				tituloTexto[3] = "Abreviatura";// titulo del input 3
				tituloTexto[4] = "Orden";// titulo del input 4

				if (!cursor.existe(buscarhijo))
					filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				else
					filtro = cursor.listanoedit(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 26:// G_Constante
				buscarhijo = "select distinct inscodigo from institucion where insdiscapacidad =" + text + " and rownum<=1";

				// consulta para extraer el registro
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev, G_ConOrden from G_Constante ";
				buscar += "where G_ConTipo = 19 and " + datoMaestro.getCampo(2) + " = " + text;

				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 4);
				tituloTexto[1] = "Cndigo";// titulo del input 1
				tituloTexto[2] = "Nombre";// titulo del input 2
				tituloTexto[3] = "Abreviatura";// titulo del input 3
				tituloTexto[4] = "Orden";// titulo del input 4

				if (!cursor.existe(buscarhijo))
					filtro = cursor.lista(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				else
					filtro = cursor.listanoedit(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 27:// G_Nucleo
				// consulta para extraer el registro
				buscar = "select * from " + datoMaestro.getTabla() + " where " + datoMaestro.getCampo(2) + " = '" + text + "' and rownum<=1";

				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 6);

				lista[1] = texto[1];
				criterioLista[1] = "select G_ConCodigo,G_ConNombre from G_Constante where G_ConTipo = 7";
				tituloLista[1] = "Municipio";

				tituloTexto[2] = "Cndigo";
				tituloTexto[3] = "Nombre";
				tituloTexto[4] = "Direccinn";
				tituloTexto[5] = "Telnfono";
				tituloTexto[6] = "Director";

				filtro = cursor.listadivision(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 29:// G_Asignatura

				buscarhijo = "select distinct asicodigo from asignatura where asicodigo =" + text + " and rownum<=1";

				// consulta para extraer el registro
				buscar = "select G_AsiCodigo,G_AsiCodArea,G_AsiNombre from G_Asignatura ";
				buscar += " where G_AsiCodigo = " + text;

				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 3);

				tituloTexto[1] = "Cndigo";
				// llenar el vector 'lista' con los datos del registro
				lista[1] = texto[2];
				criterioLista[1] = "select G_AreCodigo,G_AreNombre from G_Area ";// datos del primer 'select'
				tituloLista[1] = "área";// titulo del primer 'select'

				tituloTexto[3] = "Nombre";

				if (!cursor.existe(buscarhijo))
					filtro = cursor.listaparametro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				else
					filtro = cursor.lista3(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 28:

				// consulta para extraer el registro
				buscar = "select AreCodigo,AreNombre,AreOrden,AreAbrev,Arevigencia from " + datoMaestro.getTabla() + "";
				buscar += " where AreCodInst = " + institucion;
				buscar += " and AreCodMetod = " + metodologiaid;
				buscar += " and AreCodigo = " + text2;
				buscar += " and AreVigencia = " + vigencia;

				texto = cursor.nombre(buscar, 1, 5);

				lista[1] = texto[1];
				criterioLista[1] = "select G_AreCodigo,G_AreNombre from G_Area  order by G_AreNombre";
				tituloLista[1] = "área";

				tituloTexto[2] = "Nombre";
				tituloTexto[3] = "Orden";
				tituloTexto[4] = "Abreviatura";

				buscar = "select distinct GraAreCodGrado from grado_area where GraAreCodMetod = " + metodologiaid + " and GraAreCodArea = " + text2 + " and GraAreCodInst= " + institucion + " and GraAreVigencia=" + vigencia + " order by GraAreCodGrado";

				String algo1[][] = cursor.getDatoMaestro(buscar);
				if (algo1 != null) {
					check = new String[algo1.length];
					for (int k = 0; k < algo1.length; k++)
						check[k] = algo1[k][0];
				}

				// llenar el vector 'lista' con los datos del registro

				tituloCheck[1] = "select distinct GraCodigo,GraNombre from grado where GraCodInst= " + institucion + " and gracodmetod=" + metodologiaid + " and Gracodigo>=0 or (Gracodigo=40 or Gracodigo=41)  order by GraCodigo";

				filtro = cursor.listaeditarea(tituloCheck, criterioLista, tituloLista, lista, tituloTexto, texto, zz, check);
				break;

			case 30:// Asignaturas por institucinn
				// consulta para extraer el registro
				buscar = "select AsiCodArea,AsiCodigo,AsiNombre,AsiOrden,AsiAbrev,AsiVigencia from " + datoMaestro.getTabla() + "";
				buscar += " where AsiCodInst = " + institucion;
				buscar += " and AsiCodMetod = " + metodologiaid;
				buscar += " and AsiCodigo = " + text2;
				buscar += " and AsiVigencia = " + vigencia;

				System.out.println("BUSCAR ASIGNATURA: " + buscar);
				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 6);

				buscar = "select distinct GraAsiCodGrado from grado_asignatura ";
				buscar += " where graasicodinst=" + institucion;
				buscar += " and graasicodmetod=" + metodologiaid;
				buscar += " and GraAsiCodAsign=" + text2;
				buscar += " and GraAsiVigencia=" + vigencia;

				String algo[][] = cursor.getDatoMaestro(buscar);
				if (algo != null) {
					check = new String[algo.length];
					for (int k = 0; k < algo.length; k++)
						check[k] = algo[k][0];
				}

				tituloTexto[3] = "Nombre";
				tituloTexto[4] = "Orden";
				tituloTexto[5] = "Abreviatura";

				// llenar el vector 'lista' con los datos del registro

				lista[1] = texto[1];
				criterioLista[1] = "select AreCodigo,AreNombre from Area where AreCodInst=" + institucion + " and arecodmetod=" + metodologiaid + " and arevigencia=" + vigencia + "  order by AreNombre";
				tituloLista[1] = "área";

				lista[2] = texto[2];
				criterioLista[2] = "select G_AsiCodigo,G_AsiNombre from G_Asignatura where G_AsiCodArea=" + lista[1] + " order by G_AsiNombre";
				tituloLista[2] = "Asignatura";

				tituloCheck[1] = " select distinct GraAreCodGrado,granombre from grado_area,grado";
				tituloCheck[1] += " where GraAreCodInst=" + institucion;
				tituloCheck[1] += " and graarecodinst=gracodinst ";
				tituloCheck[1] += " and graarecodgrado=gracodigo";
				tituloCheck[1] += " and GraAreCodMetod =" + metodologiaid;
				tituloCheck[1] += " and GraAreCodMetod=GraCodMetod";
				tituloCheck[1] += " and GraAreCodArea =" + lista[1];
				tituloCheck[1] += " and GraAreVigencia =" + vigencia;
				tituloCheck[1] += " ORDER BY GraAreCodGrado";

				// System.out.println("query tituloCheck: "+tituloCheck[1]);

				tituloCheckSelected[1] = " select distinct GraAreCodGrado,graasiih from grado_area,grado,grado_asignatura  ";
				tituloCheckSelected[1] += " where GraAreCodInst=" + institucion;
				tituloCheckSelected[1] += " and graarecodinst=graasicodinst ";
				tituloCheckSelected[1] += " and graarecodinst=gracodinst ";
				tituloCheckSelected[1] += " and graarecodmetod=graasicodmetod ";
				tituloCheckSelected[1] += " and graarecodmetod=gracodmetod ";
				tituloCheckSelected[1] += " and GraAreCodMetod =" + metodologiaid;
				tituloCheckSelected[1] += " and GraAreCodArea =" + lista[1];
				tituloCheckSelected[1] += " and GraAreVigencia =" + vigencia;
				tituloCheckSelected[1] += " and GraAsiVigencia=GraAreVigencia";
				tituloCheckSelected[1] += " and graasicodasign=" + lista[2];
				tituloCheckSelected[1] += " and GraAreCodGrado=gracodigo ";
				tituloCheckSelected[1] += " and graasicodgrado=gracodigo ";
				tituloCheckSelected[1] += " ORDER BY GraAreCodGrado";

				filtro = cursor.listeditaasignatura(tituloCheck, tituloCheckSelected, criterioLista, tituloLista, lista, tituloTexto, texto, zz, check);
				break;

			case 31:
				// consulta para extraer el registro
				buscar = "select PlaCriterio,PlaProcedimiento,PlaPlanesEspeciales,PlaVigencia from " + datoMaestro.getTabla() + " where PlaCodInst=" + institucion + " and PlaCodMetod = '" + metodologiaid + "' and PlaVigencia=" + vigencia;
				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 4);

				tituloTexto[1] = "Criterios de Evaluacinn";
				tituloTexto[2] = "Procedimientos de Evaluacinn";
				tituloTexto[3] = "Planes Especiales de Apoyo";

				filtro = cursor.listatextarea(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 37:// Logros
				// consulta para extraer el registro
				buscar = " select distinct cod_grado,cod_asignatura,LogCodPerInicial,LogCodPerFinal,LogNombre,LogAbrev,LogDescripcion,LogVigencia ";
				buscar += " from " + datoMaestro.getTabla() + ",g_jerarquia_tipo3_nivel4 ";
				buscar += " where LogCodJerar=" + text2;
				buscar += " and LogCodigo = " + text;
				buscar += " and LogVigencia = " + vigencia;
				buscar += " and codigo_jerarquia=LogCodJerar ";
				buscar += " and vigencia=LogVigencia ";

				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 8);

				lista[1] = texto[1];
				criterioLista[1] = "select distinct GraCodigo,GraNombre from Grado where GraCodInst= " + institucion + " and GraCodMetod=" + metodologiaid + "  and Gracodigo>=0 or (Gracodigo=40 or Gracodigo=41) order by GraCodigo";

				lista[2] = texto[2];
				criterioLista[2] = " select AsiCodigo,AsiNombre from Asignatura,Grado_Asignatura where AsiCodigo=GraAsiCodAsign ";
				criterioLista[2] += " and asicodinst=" + institucion;
				criterioLista[2] += " and asivigencia=" + vigencia;
				criterioLista[2] += " and graasivigencia=asivigencia ";
				criterioLista[2] += " and asicodinst=graasicodinst ";
				criterioLista[2] += " and asicodmetod=" + metodologiaid;
				criterioLista[2] += " and asicodmetod=graasicodmetod ";
				criterioLista[2] += " and GraAsiCodGrado=" + lista[1];
				criterioLista[2] += " order by Asinombre";

				criterioLista[1] = "select distinct GraCodigo,GraNombre from Grado where GraCodInst= " + institucion + " and GraCodMetod=" + metodologiaid + "  and Gracodigo>=0 or (Gracodigo=40 or Gracodigo=41) order by GraCodigo";
				tituloLista[1] = "Grado";

				if (!x.equals("")) {
					criterioLista[2] = " select AsiCodigo,AsiNombre from Asignatura,Grado_Asignatura where AsiCodigo=GraAsiCodAsign ";
					criterioLista[2] += " and asicodinst=" + institucion;
					criterioLista[2] += " and asivigencia=" + vigencia;
					criterioLista[2] += " and graasivigencia=asivigencia ";
					criterioLista[2] += " and asicodinst=graasicodinst ";
					criterioLista[2] += " and asicodmetod=" + metodologiaid;
					criterioLista[2] += " and asicodmetod=graasicodmetod ";
					criterioLista[2] += " and GraAsiCodGrado=" + x;
					criterioLista[2] += " order by Asinombre";
				}
				tituloLista[2] = "Asignatura";

				lista[3] = texto[3];
				criterioLista[3] = "  select 1 as cod, 'Primero' as nombre from dual union ";
				criterioLista[3] += " select 2 as cod, 'Segundo' as nombre from dual union ";
				criterioLista[3] += " select 3 as cod, 'Tercero' as nombre from dual union ";
				criterioLista[3] += " select 4 as cod, 'Cuarto' as nombre from dual union ";
				criterioLista[3] += " select 5 as cod, 'Quinto' as nombre from dual";

				tituloLista[3] = "Periodo inicial";

				lista[4] = texto[4];
				criterioLista[4] = "  select 1 as cod, 'Primero' as nombre from dual union ";
				criterioLista[4] += " select 2 as cod, 'Segundo' as nombre from dual union ";
				criterioLista[4] += " select 3 as cod, 'Tercero' as nombre from dual union ";
				criterioLista[4] += " select 4 as cod, 'Cuarto' as nombre from dual union ";
				criterioLista[4] += " select 5 as cod, 'Quinto' as nombre from dual";

				tituloLista[4] = "Periodo final";

				tituloTexto[5] = "Logro";
				tituloTexto[6] = "Abreviatura";
				tituloTexto[7] = "Descripcinn";

				filtro = cursor.listaeditlogro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 39:// Descriptores Valorativos

				// consulta para extraer el registro
				buscar = " select distinct cod_grado,cod_area,DesCodTipDes,DesCodPerInicial,DesCodPerFinal,DesNombre,DesAbrev,DesDescripcion,DesVigencia ";
				buscar += " from " + datoMaestro.getTabla() + ",g_jerarquia_tipo2_nivel4 ";
				buscar += " where DesCodJerar= " + text2;
				buscar += " and DesCodigo= " + text;
				buscar += " and DesVigencia= " + vigencia;
				buscar += " and codigo_jerarquia=DesCodJerar ";
				buscar += " and vigencia=DesVigencia ";

				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 9);

				lista[1] = texto[1];
				criterioLista[1] = "select distinct GraCodigo,GraNombre from Grado where GraCodInst= " + institucion + " and GraCodMetod=" + metodologiaid + " and Gracodigo>=0 or (Gracodigo=40 or Gracodigo=41)  order by GraCodigo";

				lista[2] = texto[2];
				criterioLista[2] = " select AreCodigo,AreNombre from Area,Grado_Area where AreCodigo=GraAreCodArea ";
				criterioLista[2] += " and arecodinst=" + institucion;
				criterioLista[2] += " and arevigencia=" + vigencia;
				criterioLista[2] += " and graarevigencia=arevigencia";
				criterioLista[2] += " and arecodinst=graarecodinst ";
				criterioLista[2] += " and arecodmetod=" + metodologiaid;
				criterioLista[2] += " and arecodmetod=graarecodmetod ";
				criterioLista[2] += " and GraAreCodGrado=" + lista[1];
				criterioLista[2] += " order by AreNombre";

				criterioLista[1] = "select distinct GraCodigo,GraNombre from Grado where GraCodInst= " + institucion + " and GraCodMetod=" + metodologiaid + "  and Gracodigo>=0 or (Gracodigo=40 or Gracodigo=41) order by GraCodigo";
				tituloLista[1] = "Grado";

				if (!x.equals("")) {
					criterioLista[2] = " select AreCodigo,AreNombre from Area,Grado_Area where AreCodigo=GraAreCodArea ";
					criterioLista[2] += " and arecodinst=" + institucion;
					criterioLista[2] += " and arevigencia=" + vigencia;
					criterioLista[2] += " and graarevigencia=arevigencia";
					criterioLista[2] += " and arecodinst=graarecodinst ";
					criterioLista[2] += " and arecodmetod=" + metodologiaid;
					criterioLista[2] += " and arecodmetod=graarecodmetod ";
					criterioLista[2] += " and GraAreCodGrado=" + x;
					criterioLista[2] += " order by AreNombre";
				}

				tituloLista[2] = "área";

				lista[3] = texto[3];
				criterioLista[3] = "select TipDesCodigo,TipDesNombre from Tipo_Descriptor ";
				tituloLista[3] = "Tipo Descriptor";

				lista[4] = texto[4];
				criterioLista[4] = "  select 1 as cod, 'Primero' as nombre from dual union ";
				criterioLista[4] += " select 2 as cod, 'Segundo' as nombre from dual union ";
				criterioLista[4] += " select 3 as cod, 'Tercero' as nombre from dual union ";
				criterioLista[4] += " select 4 as cod, 'Cuarto' as nombre from dual ";

				tituloLista[4] = "Periodo inicial";

				lista[5] = texto[5];
				criterioLista[5] = "  select 1 as cod, 'Primero' as nombre from dual union ";
				criterioLista[5] += " select 2 as cod, 'Segundo' as nombre from dual union ";
				criterioLista[5] += " select 3 as cod, 'Tercero' as nombre from dual union ";
				criterioLista[5] += " select 4 as cod, 'Cuarto' as nombre from dual ";

				tituloLista[5] = "Periodo final";

				tituloTexto[6] = "Descriptor";
				tituloTexto[7] = "Abreviatura";
				tituloTexto[8] = "Descripcinn";

				filtro = cursor.listaeditdescriptor(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 40:// Tipo Descriptor

				buscarhijo = "select distinct descodigo from descriptor_valorativo where descodtipdes =" + text + " and rownum<=1";

				// consulta para extraer el registro
				buscar = "select * from " + datoMaestro.getTabla() + " where " + datoMaestro.getCampo(1) + " = '" + text + "'";
				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 2);
				tituloTexto[1] = "Cndigo";
				tituloTexto[2] = "Nombre";

				if (!cursor.existe(buscarhijo))
					filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				else
					filtro = cursor.listanoedit(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 41:

				// consulta para extraer el registro
				buscar = " select G_JerGrado,G_JerAreaAsig,IndCodLogro,IndCodPerio,IndNombre,IndAbrev,IndDescripcion ";
				buscar += " from Indicador_Logro,G_Jerarquia ";
				buscar += " where G_JerCodigo=IndCodJerar ";
				buscar += " and IndCodigo = " + text;

				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 7);

				break;

			case 42:// Escalas Valorativas

				searchson[1] = "select distinct evaarecodestu from evaluacion_area where (evaareeval1 =" + text + " or evaareeval2 =" + text + " or evaareeval3 =" + text + " or evaareeval4 =" + text + " or evaareeval5 =" + text + ") and evaarevigencia=" + vigencia + " and rownum<=1";
				searchson[2] = "select distinct evaasicodestud from evaluacion_asignatura where (evaasieval1 =" + text + " or evaasieval2 =" + text + " or evaasieval3 =" + text + " or evaasieval4 =" + text + " or evaasieval5 =" + text + ") and evaasivigencia=" + vigencia + " and rownum<=1";
				searchson[3] = "select distinct evalogcodestu from evaluacion_logro where evalogevalu =" + text + " and evalogvigencia=" + vigencia + " and rownum<=1";

				// consulta para extraer el registro
				buscar = "select * from " + datoMaestro.getTabla() + "";
				buscar += " where " + datoMaestro.getCampo(1) + " = '" + text + "'";

				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 4);

				tituloTexto[1] = "Cndigo";
				tituloTexto[2] = "Nombre";
				tituloTexto[3] = "Abreviatura";

				lista[1] = texto[4];
				criterioLista[1] = "select G_ConCodigo,G_ConNombre from G_Constante where G_ConTipo=23  order by G_ConCodigo";
				tituloLista[1] = "Tipo Escala";

				if (!cursor.existe1(searchson))
					filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				else
					filtro = cursor.listanoedit(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 43:// G_Constante tipo escala
				buscarhijo = "select * from escala_valorativa where esctipo =" + text + " and rownum<=1";

				// consulta para extraer el registro
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev, G_ConOrden from G_Constante ";
				buscar += "where G_ConTipo = 23 and " + datoMaestro.getCampo(2) + " = " + text;

				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 4);
				tituloTexto[1] = "Cndigo";
				tituloTexto[2] = "Nombre";
				tituloTexto[3] = "Abreviatura";
				tituloTexto[4] = "Orden";

				if (!cursor.existe(buscarhijo))
					filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				else
					filtro = cursor.listadatomaestronoedit(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 44:// G_Constante RangoTarifa
				buscarhijo = "select * from institucion where instarifacostos =" + text + " and rownum<=1";
				// consulta para extraer el registro
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev, G_ConOrden from G_Constante ";
				buscar += "where G_ConTipo = 20 and " + datoMaestro.getCampo(2) + " = " + text;

				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 4);
				tituloTexto[1] = "Cndigo";
				tituloTexto[2] = "Nombre";
				tituloTexto[3] = "Abreviatura";
				tituloTexto[4] = "Orden";

				if (!cursor.existe(buscarhijo))
					filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				else
					filtro = cursor.listanoedit(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 45:// G_Constante Asociaciones privadas
				buscarhijo = "select * from institucion where insasocprivada =" + text + " and rownum<=1";
				// consulta para extraer el registro
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev, G_ConOrden from G_Constante ";
				buscar += "where G_ConTipo = 21 and " + datoMaestro.getCampo(2) + " = " + text;

				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 4);
				tituloTexto[1] = "Cndigo";// titulo del input 1
				tituloTexto[2] = "Nombre";// titulo del input 2
				tituloTexto[3] = "Abreviatura";// titulo del input 3
				tituloTexto[4] = "Orden";// titulo del input 4

				if (!cursor.existe(buscarhijo))
					filtro = cursor.lista(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				else
					filtro = cursor.listanoedit(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 46:// G_Constante Idioma

				buscarhijo = "select * from institucion where insidioma =" + text + " and rownum<=1";

				// consulta para extraer el registro
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev, G_ConOrden from G_Constante ";
				buscar += "where G_ConTipo = 22 and " + datoMaestro.getCampo(2) + " = " + text;

				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 4);
				tituloTexto[1] = "Cndigo";// titulo del input 1
				tituloTexto[2] = "Nombre";// titulo del input 2
				tituloTexto[3] = "Abreviatura";// titulo del input 3
				tituloTexto[4] = "Orden";// titulo del input 4

				if (!cursor.existe(buscarhijo))
					filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				else
					filtro = cursor.listadatomaestronoedit(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 47:// G_Constante Cargo GE
				// consulta para extraer el registro
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev, G_ConOrden from G_Constante ";
				buscar += "where G_ConTipo = 24 and " + datoMaestro.getCampo(2) + " = " + text;

				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 4);
				tituloTexto[1] = "Cndigo";// titulo del input 1
				tituloTexto[2] = "Nombre";// titulo del input 2
				tituloTexto[3] = "Abreviatura";// titulo del input 3
				tituloTexto[4] = "Orden";// titulo del input 4

				filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 50:// ROT_TIPO_RECESO
				buscarhijo = "select distinct ROTREC_TIPREC from ROT_RECESO WHERE ROTREC_TIPREC=" + text + " and rownum<=1";
				buscar = "select * from " + datoMaestro.getTabla() + " where " + datoMaestro.getCampo(1) + " = '" + text + "'";
				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 3);
				tituloTexto[1] = "Codigo";
				tituloTexto[2] = "Nombre";
				tituloTexto[3] = "Orden";

				if (!cursor.existe(buscarhijo))
					filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				else
					filtro = cursor.listanoedit(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 49:// G_Constante tipo atencinn especial
				buscarhijo = "select distinct TIPOATENCION from ATENCION_ESPECIAL WHERE TIPOATENCION=" + text + " and rownum<=1";
				// consulta para extraer el registro
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev, G_ConOrden from G_Constante ";
				buscar += "where G_ConTipo = 31 and " + datoMaestro.getCampo(2) + " = " + text;

				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 4);
				tituloTexto[1] = "Cndigo";// titulo del input 1
				tituloTexto[2] = "Nombre";// titulo del input 2
				tituloTexto[3] = "Abreviatura";// titulo del input 3
				tituloTexto[4] = "Orden";// titulo del input 4

				if (!cursor.existe(buscarhijo))
					filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				else
					filtro = cursor.listanoedit(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 51:// G_Constante tipo especialidad
				buscarhijo = "select distinct ESPCODESP from ESPECIALIDAD WHERE ESPCODESP=" + text + " and rownum<=1";
				// consulta para extraer el registro
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev, G_ConOrden from G_Constante ";
				buscar += "where G_ConTipo = 34 and " + datoMaestro.getCampo(2) + " = " + text;

				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 4);
				tituloTexto[1] = "Cndigo";
				tituloTexto[2] = "Nombre";
				tituloTexto[3] = "Abreviatura";
				tituloTexto[4] = "Orden";

				if (!cursor.existe(buscarhijo))
					filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				else
					filtro = cursor.listanoedit(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 52:// G_Constante Tipos de Modalidad
				buscarhijo = "select distinct INSMODALIDAD from INSTITUCION WHERE INSMODALIDAD=" + text + " and rownum<=1";
				// consulta para extraer el registro
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev, G_ConOrden from G_Constante ";
				buscar += "where G_ConTipo = 33 and " + datoMaestro.getCampo(2) + " = " + text;

				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 4);
				tituloTexto[1] = "Cndigo";
				tituloTexto[2] = "Nombre";
				tituloTexto[3] = "Abreviatura";
				tituloTexto[4] = "Orden";

				if (!cursor.existe(buscarhijo))
					filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				else
					filtro = cursor.listanoedit(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			case 53:// G_Constante Tipos de nnfasis
				buscarhijo = "select distinct INSENFASIS from INSTITUCION WHERE INSENFASIS=" + text + " and rownum<=1";
				// consulta para extraer el registro
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev, G_ConOrden from G_Constante ";
				buscar += "where G_ConTipo = 32 and " + datoMaestro.getCampo(2) + " = " + text;

				// llenar el vector 'texto' con el registro
				texto = cursor.nombre(buscar, 1, 4);
				tituloTexto[1] = "Cndigo";
				tituloTexto[2] = "Nombre";
				tituloTexto[3] = "Abreviatura";
				tituloTexto[4] = "Orden";

				if (!cursor.existe(buscarhijo))
					filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				else
					filtro = cursor.listanoedit(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
				break;

			}
			request.setAttribute("filtro", filtro);// asignacion del formulario de edicion al ambito de la peticion
			request.setAttribute("titulo", datoMaestro.getTitulo());// asignacion del titulo de la tabla de edicion al ambito de la peticion
			ir(2, sig, request, response);
			return;
		} catch (Exception e) {
			System.out.println("Error " + this + ": " + e.toString());
			throw new ServletException(e);
		} finally {
			if (cursor != null)
				cursor.cerrar();
			if (util != null)
				util.cerrar();
		}
	}

	/**
	 * Redirige el control a otro servlet
	 * 
	 * @param: int a: 1=redirigir como 'include', 2=redirigir como 'forward'
	 * @param: String s
	 * @param: HttpServletRequest request
	 * @param: HttpServletResponse response
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