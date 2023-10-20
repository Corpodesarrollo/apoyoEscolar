package siges.datoMaestro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.Util;
import siges.datoMaestro.beans.DatoMaestroBean;
import siges.login.beans.Login;

/**
 * Nombre: DatoMaestroSeleccionEdit.java <BR>
 * Descripcinn: Recibe la peticion de un dato maestro y la redirige a la pagina
 * donde estan los frames 'lista' y 'nuevo'<BR>
 * Funciones de la pngina: Preguntar por el valor de 'dato', crear el bean
 * 'DatoMaestroBean', colocarlo en el ambito de la sesion y seder el control a
 * la pagina de frames 'DatoMaestro.jsp' <BR>
 * Entidades afectadas: No aplica <BR>
 * Fecha de modificacinn: 20/07/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */

public class DatoMaestroListaEdit extends HttpServlet {
	private Cursor cursor;// objeto que maneja las sentencias sql

	private Util util;

	private Dao dao;

	private Integer entero = new Integer(java.sql.Types.INTEGER);

	private Integer cadena = new Integer(java.sql.Types.VARCHAR);

	private Integer fecha = new Integer(java.sql.Types.TIMESTAMP);

	private Integer nulo = new Integer(java.sql.Types.NULL);

	private Integer doble = new Integer(java.sql.Types.DOUBLE);

	private Integer caracter = new Integer(java.sql.Types.CHAR);

	private Integer enterolargo = new Integer(java.sql.Types.BIGINT);

	/**
	 * Recibe la peticion por el metodo get de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
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
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String institucion;// variable que almacena el id de la institucion
		String metodologiaid;// variable que almacena el id de la metodologia de
								// la institucion
		String metodologia;// variable que almacena el nombre de la metodologia
							// de la institucion
		String nivel;// variable que almacena el id del nivel de la institucion
		Login login;

		int dato;// variable que captura numero de dato maestro del que vamos a
					// mostrar datos
		int inicio;// numero de la columna desde donde vamos a mostrar datos
		int fin;// numero de la columna hasta la que vamos a mostrar datos
		String tipo;// identifica si la tabla va a ser para solo lectura o para
					// editar los registros
		String sig;// nombre de la pagina a la que ira despues de ejecutar los
					// comandos de esta
		String er;// nombre de la pagina a la que ira si hay errores
		String home;// nombre de la pagina a la que ira si hay errores
		String editar;// nombre de la pagina que editara un dato maestro
		String buscar;// sentencia sql para generar el listado de registros
		String tabla;// variable que captura la lista de registros formateados
						// como filas y columnas html
		String filtro;// variable que captura los 'selects' html de los datos
						// maestros que necesitan de un filtro de busqueda
		String where;// condicion de la sentencia sql de busqueda con la cual se
						// filtra registros a partir de los resultados del
						// filtro
		String text;// variable que contiene el id del registro (inicialmente no
					// se usa en este servlet)
		String text2;// variable que contiene la columna 2 del registro
						// (inicialmente no se usa en este servlet)
		String lista[];// vector que captura los items seleccionados por el
						// usuario en el filtro de busqueda
		String texto[];// vector que contiene el texto digitado en los input de
						// tipo 'TEXT'
		String criterioLista[];// vector que contiene las sentencias sql que
								// permiten llenar de opciones los 'selects'
								// html
		String tituloLista[];// titulo que tendran los 'selects' html en el
								// formulario de busqueda
		String tituloTexto[];// titulo de las columnas que tendra la tabla de
								// resultados
		DatoMaestroBean datoMaestro;// objeto que maneja los metadatos de cada
									// dato maestro
		boolean busqueda;// variable que define si la pagina tiene que desplegar
							// un formulario de filtro o si es la lista de
							// respuesta
		String x, y, z;// valor de un select para hacer el siguiente select
						// dinamico
		String vigencia;

		sig = "/DatoMaestroLista.jsp";
		er = "/error.jsp";
		home = "/bienvenida.jsp";
		where = filtro = tabla = "";
		inicio = fin = 0;
		editar = null;
		text = text2 = null;
		busqueda = false;
		if (request.getParameter("tipo") == null)
			editar = "DatoMaestroEditarEdit.do";
		if (request.getParameter("busqueda") != null)
			busqueda = true;

		try {
			cursor = new Cursor();
			util = new Util(cursor);
			dao = new Dao(cursor);

			login = (Login) request.getSession().getAttribute("login");
			criterioLista = new String[8];
			lista = new String[8];
			texto = new String[10];
			tituloLista = new String[8];
			tituloTexto = new String[10];
			Collection list = new ArrayList();
			Object[] o = new Object[2];

			lista[1] = (request.getParameter("lista1") != null) ? request
					.getParameter("lista1") : new String("0");// captura el
																// valor de el
																// select cuyo
																// nombre es
																// 'lista1'
			lista[2] = (request.getParameter("lista2") != null) ? request
					.getParameter("lista2") : new String("0");// captura el
																// valor de el
																// select cuyo
																// nombre es
																// 'lista2'
			lista[3] = (request.getParameter("lista3") != null) ? request
					.getParameter("lista3") : new String("0");// captura el
																// valor de el
																// select cuyo
																// nombre es
																// 'lista3'
			lista[4] = (request.getParameter("lista4") != null) ? request
					.getParameter("lista4") : new String("0");// captura el
																// valor de el
																// select cuyo
																// nombre es
																// 'lista4'
			lista[5] = (request.getParameter("lista5") != null) ? request
					.getParameter("lista5") : new String("0");// captura el
																// valor de el
																// select cuyo
																// nombre es
																// 'lista5'
			lista[6] = (request.getParameter("lista6") != null) ? request
					.getParameter("lista6") : new String("0");// captura el
																// valor de el
																// select cuyo
																// nombre es
																// 'lista6'
			texto[1] = (request.getParameter("texto1") != null) ? request
					.getParameter("texto1") : new String("");// captura el valor
																// de el input
																// de tipo TEXT
																// cuyo nombre
																// es 'texto1'
			texto[2] = (request.getParameter("texto2") != null) ? request
					.getParameter("texto2") : new String("");// captura el valor
																// de el input
																// de tipo TEXT
																// cuyo nombre
																// es 'texto2'
			texto[3] = (request.getParameter("texto3") != null) ? request
					.getParameter("texto3") : new String("");// captura el valor
																// de el input
																// de tipo TEXT
																// cuyo nombre
																// es 'texto3'
			texto[4] = (request.getParameter("texto4") != null) ? request
					.getParameter("texto4") : new String("");// captura el valor
																// de el input
																// de tipo TEXT
																// cuyo nombre
																// es 'texto4'
			texto[5] = (request.getParameter("texto5") != null) ? request
					.getParameter("texto5") : new String("");// captura el valor
																// de el input
																// de tipo TEXT
																// cuyo nombre
																// es 'texto5'
			texto[6] = (request.getParameter("texto6") != null) ? request
					.getParameter("texto6") : new String("");// captura el valor
																// de el input
																// de tipo TEXT
																// cuyo nombre
																// es 'texto6'
			texto[7] = (request.getParameter("texto7") != null) ? request
					.getParameter("texto7") : new String("");// captura el valor
																// de el input
																// de tipo TEXT
																// cuyo nombre
																// es 'texto7'
			texto[8] = (request.getParameter("texto8") != null) ? request
					.getParameter("texto8") : new String("");// captura el valor
																// de el input
																// de tipo TEXT
																// cuyo nombre
																// es 'texto8'
			texto[9] = (request.getParameter("texto9") != null) ? request
					.getParameter("texto9") : new String("");// captura el valor
																// de el input
																// de tipo TEXT
																// cuyo nombre
																// es 'texto9'
			x = request.getParameter("x") != null ? request.getParameter("x")
					: new String("");
			y = request.getParameter("y") != null ? request.getParameter("y")
					: new String("");
			z = request.getParameter("z") != null ? request.getParameter("z")
					: new String("");

			datoMaestro = (DatoMaestroBean) request.getSession().getAttribute(
					"datoMaestro");// trae el objeto 'DatoMaestro' de la sesion

			// Datos de prueba de cndigo de la institucinn y el nivel de la
			// persona que ingresa al sistema

			if (datoMaestro == null) {
				ir(2, home, request, response);
				return;
			}

			institucion = login.getInstId();
			nivel = login.getNivel();
			metodologia = login.getMetodologia();
			metodologiaid = login.getMetodologiaId();
			vigencia = dao.getVigencia();
			// System.out.println("VIGENCIA: "+vigencia);

			if (vigencia == null) {
				// System.out.println("VIGENCIA NULA");
				ir(2, home, request, response);
				return;
			}
			dato = Integer.parseInt(datoMaestro.getDato());// numero del dato
															// maestro al que
															// hacemos
															// referencia

			switch (dato) {

			case 1:// G_Constante Jornada
				tituloTexto[1] = "Cndigo";// titulo de la columna 1
				tituloTexto[2] = "Nombre";// titulo de la columna 2
				tituloTexto[3] = "Abreviatura";// titulo de la columna 3
				tituloTexto[4] = "Orden";// titulo de la columna 4
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden from G_Constante ";// consulta
																									// para
																									// extraer
																									// los
																									// datos
				buscar += "where G_ConTipo = 5";
				buscar += " order by " + datoMaestro.getCampo(2);// filtro sobre
																	// la
																	// consulta
				inicio = 1;// desde cual columna de la tabla
				fin = 4;// hasta que columna de la tabla
				tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
						inicio, fin, dato);// creacion de la tabla de resultados
				break;

			case 2:// G_Constante TipoDocumento
				tituloTexto[1] = "Cndigo";// titulo de la columna 2
				tituloTexto[2] = "Nombre";// titulo de la columna 3
				tituloTexto[3] = "Abreviatura";// titulo de la columna 3
				tituloTexto[4] = "Orden";// titulo de la columna 4
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden  from G_Constante ";// consulta
																									// para
																									// extraer
																									// los
																									// datos
				buscar += " where G_ConTipo = 10";
				buscar += " order by " + datoMaestro.getCampo(2);// filtro sobre
																	// la
																	// consulta
				inicio = 1;// desde cual columna de la tabla
				fin = 4;// hasta que columna de la tabla
				tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
						inicio, fin, dato);// creacion de la tabla de resultados
				break;

			case 3:// G_Constante Departamento
				tituloTexto[1] = "Cndigo";// titulo de la columna 2
				tituloTexto[2] = "Nombre";// titulo de la columna 3
				tituloTexto[3] = "Abreviatura";// titulo de la columna 3
				tituloTexto[4] = "Orden";// titulo de la columna 4
				buscar = " select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden  from G_Constante ";
				buscar += " where G_ConTipo = 6 ";
				buscar += " order by G_ConCodigo ";// filtro sobre la consulta
				inicio = 1;// desde cual columna de la tabla
				fin = 4;// hasta que columna de la tabla
				tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
						inicio, fin, dato);// creacion de la tabla de resultados
				break;

			case 4:// G_Constante Nivel
				tituloTexto[1] = "Cndigo";// titulo de la columna 2
				tituloTexto[2] = "Nombre";// titulo de la columna 3
				tituloTexto[3] = "Abreviatura";// titulo de la columna 3
				tituloTexto[4] = "Orden";// titulo de la columna 4
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden  from G_Constante ";// consulta
																									// para
																									// extraer
																									// los
																									// datos
				buscar += " where G_ConTipo = 1";
				buscar += " order by G_ConCodigo";// filtro sobre la consulta
				inicio = 1;// desde cual columna de la tabla
				fin = 4;// hasta que columna de la tabla
				tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
						inicio, fin, dato);// creacion de la tabla de resultados
				break;

			case 5:// G_Constante Grupo
				tituloTexto[1] = "Cndigo";// titulo de la columna 2
				tituloTexto[2] = "Nombre";// titulo de la columna 3
				tituloTexto[3] = "Abreviatura";// titulo de la columna 3
				tituloTexto[4] = "Orden";// titulo de la columna 4
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden  from G_Constante ";// consulta
																									// para
																									// extraer
																									// los
																									// datos
				buscar += "where G_ConTipo = 2";
				buscar += " order by " + datoMaestro.getCampo(2);// filtro sobre
																	// la
																	// consulta
				inicio = 1;// desde cual columna de la tabla
				fin = 4;// hasta que columna de la tabla
				tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
						inicio, fin, dato);// creacion de la tabla de resultados
				break;

			case 6:
			case 8:// G_TipoEspacio,G_TipoReconocimiento
				tituloTexto[1] = "Cndigo";// titulo de la columna 1
				tituloTexto[2] = "Nombre";// titulo de la columna 2
				buscar = "select * from " + datoMaestro.getTabla()
						+ " order by " + datoMaestro.getCampo(1);// consulta
																	// para
																	// extraer
																	// los datos
				inicio = 1;// desde cual columna de la tabla
				fin = 2;// hasta que columna de la tabla
				tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
						inicio, fin, dato);// creacion de la tabla de resultados
				break;

			case 9:// G_Constante Metodologna
				tituloTexto[1] = "Cndigo";// titulo de la columna 2
				tituloTexto[2] = "Nombre";// titulo de la columna 3
				tituloTexto[3] = "Abreviatura";// titulo de la columna 3
				tituloTexto[4] = "Orden";// titulo de la columna 4
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden  from G_Constante ";// consulta
																									// para
																									// extraer
																									// los
																									// datos
				buscar += "where G_ConTipo = 3";
				buscar += " order by " + datoMaestro.getCampo(2);// filtro sobre
																	// la
																	// consulta
				inicio = 1;// desde cual columna de la tabla
				fin = 4;// hasta que columna de la tabla
				tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
						inicio, fin, dato);// creacion de la tabla de resultados
				break;

			case 10:// Localidad8 (municipio)7 (departamento)(6)
				tituloTexto[1] = "Cndigo";// titulo de la columna 1
				tituloTexto[2] = "Nombre";// titulo de la columna 2
				tituloTexto[3] = "Municipio";// titulo de la columna 2
				criterioLista[1] = "select G_ConCodigo,G_ConNombre from G_Constante where G_ConTipo = 7";// datos
																											// del
																											// primer
																											// 'select'
				tituloLista[1] = "Municipio";// titulo del primer 'select'
				buscar = "select ano.G_ConCodigo, ano.G_ConNombre,bul.G_ConCodigo, bul.G_ConNombre ";
				buscar += " from G_Constante ano,G_Constante bul";
				buscar += " where bul.G_ConTipo =7 and ano.G_ConTipo = 8 and ano.G_ConCodPadre=bul.G_ConCodigo ";
				if (!lista[1].equals("0"))
					buscar += " and bul.G_ConCodigo = " + lista[1]; // filtro
																	// sobre la
																	// consulta
				buscar += " order by  bul.G_ConCodigo";// filtro sobre la
														// consulta
				inicio = 1;// desde cual columna de la tabla
				fin = 3;// hasta que columna de la tabla
				if (busqueda)
					tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
							inicio, fin, dato);// creacion de la tabla de
												// resultados
				else
					filtro = cursor.filtro(criterioLista, lista, tituloLista,
							dato);// creacion del filtro de busqueda
				break;

			case 11:// G_Constante TipoOcupante
				tituloTexto[1] = "Cndigo";// titulo de la columna 2
				tituloTexto[2] = "Nombre";// titulo de la columna 3
				tituloTexto[3] = "Abreviatura";// titulo de la columna 3
				tituloTexto[4] = "Orden";// titulo de la columna 4
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden  from G_Constante ";// consulta
																									// para
																									// extraer
																									// los
																									// datos
				buscar += "where G_ConTipo = 9";
				buscar += " order by " + datoMaestro.getCampo(2);// filtro sobre
																	// la
																	// consulta
				inicio = 1;// desde cual columna de la tabla
				fin = 4;// hasta que columna de la tabla
				tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
						inicio, fin, dato);// creacion de la tabla de resultados
				break;

			case 12:// G_Constante Especialidad
				tituloTexto[1] = "Cndigo";// titulo de la columna 2
				tituloTexto[2] = "Nombre";// titulo de la columna 3
				tituloTexto[3] = "Abreviatura";// titulo de la columna 3
				tituloTexto[4] = "Orden";// titulo de la columna 4
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden  from G_Constante ";// consulta
																									// para
																									// extraer
																									// los
																									// datos
				buscar += "where G_ConTipo = 4";
				buscar += " order by " + datoMaestro.getCampo(2);// filtro sobre
																	// la
																	// consulta
				inicio = 1;// desde cual columna de la tabla
				fin = 4;// hasta que columna de la tabla
				tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
						inicio, fin, dato);// creacion de la tabla de resultados
				break;

			case 13:// Condicinn
				tituloTexto[1] = "Cndigo";// titulo de la columna 1
				tituloTexto[2] = "Nombre";// titulo de la columna 2
				buscar = "select * from " + datoMaestro.getTabla()
						+ " order by " + datoMaestro.getCampo(1);// consulta
																	// para
																	// extraer
																	// los datos
				inicio = 1;// desde cual columna de la tabla
				fin = 2;// hasta que columna de la tabla
				tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
						inicio, fin, dato);// creacion de la tabla de resultados
				break;

			case 14:// G_Constante TipoConvivencia
				tituloTexto[1] = "Cndigo";// titulo de la columna 2
				tituloTexto[2] = "Nombre";// titulo de la columna 3
				tituloTexto[3] = "Abreviatura";// titulo de la columna 3
				tituloTexto[4] = "Orden";// titulo de la columna 4
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden  from G_Constante ";// consulta
																									// para
																									// extraer
																									// los
																									// datos
				buscar += "where G_ConTipo = 11";
				buscar += " order by " + datoMaestro.getCampo(2);// filtro sobre
																	// la
																	// consulta
				inicio = 1;// desde cual columna de la tabla
				fin = 4;// hasta que columna de la tabla
				tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
						inicio, fin, dato);// creacion de la tabla de resultados
				break;

			case 15:// G_Constante Discapacidad_Estudiante
				tituloTexto[1] = "Cndigo";// titulo de la columna 2
				tituloTexto[2] = "Nombre";// titulo de la columna 3
				tituloTexto[3] = "Abreviatura";// titulo de la columna 3
				tituloTexto[4] = "Orden";// titulo de la columna 4
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden  from G_Constante ";// consulta
																									// para
																									// extraer
																									// los
																									// datos
				buscar += "where G_ConTipo = 12";
				buscar += " order by " + datoMaestro.getCampo(2);// filtro sobre
																	// la
																	// consulta
				inicio = 1;// desde cual columna de la tabla
				fin = 4;// hasta que columna de la tabla
				tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
						inicio, fin, dato);// creacion de la tabla de resultados
				break;

			case 16:// G_Constante CapacidadExcepcional
				tituloTexto[1] = "Cndigo";// titulo de la columna 2
				tituloTexto[2] = "Nombre";// titulo de la columna 3
				tituloTexto[3] = "Abreviatura";// titulo de la columna 3
				tituloTexto[4] = "Orden";// titulo de la columna 4
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden  from G_Constante ";// consulta
																									// para
																									// extraer
																									// los
																									// datos
				buscar += "where G_ConTipo = 13";
				buscar += " order by " + datoMaestro.getCampo(2);// filtro sobre
																	// la
																	// consulta
				inicio = 1;// desde cual columna de la tabla
				fin = 4;// hasta que columna de la tabla
				tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
						inicio, fin, dato);// creacion de la tabla de resultados
				break;

			case 17:// G_Constante Etnia
				tituloTexto[1] = "Cndigo";// titulo de la columna 2
				tituloTexto[2] = "Nombre";// titulo de la columna 3
				tituloTexto[3] = "Abreviatura";// titulo de la columna 3
				tituloTexto[4] = "Orden";// titulo de la columna 4
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden  from G_Constante ";// consulta
																									// para
																									// extraer
																									// los
																									// datos
				buscar += " where G_ConTipo = 14";
				buscar += " order by " + datoMaestro.getCampo(2);// filtro sobre
																	// la
																	// consulta
				inicio = 1;// desde cual columna de la tabla
				fin = 4;// hasta que columna de la tabla
				tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
						inicio, fin, dato);// creacion de la tabla de resultados
				break;

			case 18:// G_Constante Motivos de ausencia
				tituloTexto[1] = "Cndigo";// titulo de la columna 2
				tituloTexto[2] = "Nombre";// titulo de la columna 3
				tituloTexto[3] = "Abreviatura";// titulo de la columna 3
				tituloTexto[4] = "Orden";// titulo de la columna 4
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden  from G_Constante ";// consulta
																									// para
																									// extraer
																									// los
																									// datos
				buscar += " where G_ConTipo = 15 ";
				buscar += " order by " + datoMaestro.getCampo(2);// filtro sobre
																	// la
																	// consulta
				inicio = 1;// desde cual columna de la tabla
				fin = 4;// hasta que columna de la tabla
				tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
						inicio, fin, dato);// creacion de la tabla de resultados
				break;

			case 19:// G_Constante Tipos de programa para docentes
				tituloTexto[1] = "Cndigo";// titulo de la columna 2
				tituloTexto[2] = "Nombre";// titulo de la columna 3
				tituloTexto[3] = "Abreviatura";// titulo de la columna 3
				tituloTexto[4] = "Orden";// titulo de la columna 4
				buscar = " select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden  from G_Constante ";// consulta
																									// para
																									// extraer
																									// los
																									// datos
				buscar += " where G_ConTipo = 16 ";
				buscar += " order by " + datoMaestro.getCampo(2);// filtro sobre
																	// la
																	// consulta
				inicio = 1;// desde cual columna de la tabla
				fin = 4;// hasta que columna de la tabla
				tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
						inicio, fin, dato);// creacion de la tabla de resultados
				break;

			case 21:// LOCALIDAD
				tituloTexto[1] = "Cndigo";// titulo de la columna 1
				tituloTexto[2] = "Nombre";// titulo de la columna 2
				tituloTexto[3] = "Departamento";// titulo de la columna 2
				criterioLista[1] = "select G_ConCodigo,G_ConNombre from G_Constante where G_ConTipo = 6";
				tituloLista[1] = "Departamento";// titulo del primer 'select'
				buscar = "select a.G_ConCodigo, a.G_ConNombre,b.G_ConNombre ";
				buscar += " from G_Constante a,G_Constante b";
				buscar += " where b.G_ConTipo=6 and a.G_ConTipo = 7 and a.G_ConCodPadre=b.G_ConCodigo ";
				if (!lista[1].equals("0"))
					buscar += " and b.G_ConCodigo = " + lista[1]; // filtro
																	// sobre la
																	// consulta
				buscar += " order by G_ConCodigo";// filtro sobre la consulta
				inicio = 1;// desde cual columna de la tabla
				fin = 3;// hasta que columna de la tabla
				if (busqueda)
					tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
							inicio, fin, dato);// creacion de la tabla de
												// resultados
				else
					filtro = cursor.filtro(criterioLista, lista, tituloLista,
							dato);// creacion del filtro de busqueda
				break;

			case 22:// nrea
				tituloTexto[1] = "Cndigo";// titulo de la columna 1
				tituloTexto[2] = "Nombre";// titulo de la columna 2
				tituloTexto[3] = "Tipo de área";// titulo de la columna 2
				criterioLista[1] = "SELECT  1 id, 'Obligatoria' nombre FROM DUAL UNION  SELECT 2 id, 'Opcional' nombre FROM DUAL";
				tituloLista[1] = "Tipo de área";// titulo del primer 'select'
				buscar = "select G_AreCodigo, G_AreNombre, G_AreTipo ";
				buscar += " from G_Area ";
				if (!lista[1].equals("0"))
					buscar += " where G_AreTipo = " + lista[1]; // filtro sobre
																// la consulta
				buscar += " order by  G_AreNombre";// filtro sobre la consulta
				inicio = 1;// desde cual columna de la tabla
				fin = 3;// hasta que columna de la tabla
				if (busqueda)
					tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
							inicio, fin, dato);// creacion de la tabla de
												// resultados
				else
					filtro = cursor.filtro(criterioLista, lista, tituloLista,
							dato);// creacion del filtro de busqueda
				break;

			case 23:// g_grado

				tituloTexto[1] = "Cndigo";
				tituloTexto[2] = "Nombre";
				tituloTexto[3] = "Abreviatura";
				tituloTexto[4] = "Orden";
				tituloTexto[5] = "Nivel";
				criterioLista[1] = "select G_ConCodigo,G_ConNombre from G_Constante where G_ConTipo = 1";
				tituloLista[1] = "Nivel";

				buscar = "select bul.g_graCodigo, bul.g_graNombre,bul.g_graAbrev,bul.g_graOrden,ano.G_ConNombre ";
				buscar += "from G_Constante ano,G_Grado bul ";
				buscar += " where ano.G_ConTipo = 1 and bul.g_graNivel=ano.G_ConCodigo ";
				if (!lista[1].equals("0"))
					buscar += " and bul.g_graNivel = " + lista[1];
				buscar += " order by  bul.g_graCodigo";
				inicio = 1;
				fin = 5;
				if (busqueda)
					tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
							inicio, fin, dato);
				else
					filtro = cursor.filtro(criterioLista, lista, tituloLista,
							dato);

				break;

			case 24:// G_Constante Calendario
				tituloTexto[1] = "Cndigo";// titulo de la columna 2
				tituloTexto[2] = "Nombre";// titulo de la columna 3
				tituloTexto[3] = "Abreviatura";// titulo de la columna 3
				tituloTexto[4] = "Orden";// titulo de la columna 4
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden  from G_Constante ";// consulta
																									// para
																									// extraer
																									// los
																									// datos
				buscar += "where G_ConTipo = 17";
				buscar += " order by " + datoMaestro.getCampo(2);// filtro sobre
																	// la
																	// consulta
				inicio = 1;// desde cual columna de la tabla
				fin = 4;// hasta que columna de la tabla
				tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
						inicio, fin, dato);// creacion de la tabla de resultados
				break;

			case 25:// G_Constante Propnedad
				tituloTexto[1] = "Cndigo";// titulo de la columna 2
				tituloTexto[2] = "Nombre";// titulo de la columna 3
				tituloTexto[3] = "Abreviatura";// titulo de la columna 3
				tituloTexto[4] = "Orden";// titulo de la columna 4
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden  from G_Constante ";// consulta
																									// para
																									// extraer
																									// los
																									// datos
				buscar += "where G_ConTipo = 18";
				buscar += " order by " + datoMaestro.getCampo(2);// filtro sobre
																	// la
																	// consulta
				inicio = 1;// desde cual columna de la tabla
				fin = 4;// hasta que columna de la tabla
				tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
						inicio, fin, dato);// creacion de la tabla de resultados
				break;

			case 26:// G_Constante Discapacidad_Institucion
				tituloTexto[1] = "Cndigo";// titulo de la columna 2
				tituloTexto[2] = "Nombre";// titulo de la columna 3
				tituloTexto[3] = "Abreviatura";// titulo de la columna 3
				tituloTexto[4] = "Orden";// titulo de la columna 4
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden  from G_Constante ";// consulta
																									// para
																									// extraer
																									// los
																									// datos
				buscar += "where G_ConTipo = 19";
				buscar += " order by " + datoMaestro.getCampo(2);// filtro sobre
																	// la
																	// consulta
				inicio = 1;// desde cual columna de la tabla
				fin = 4;// hasta que columna de la tabla
				tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
						inicio, fin, dato);// creacion de la tabla de resultados
				break;

			case 27:// G_Nucleo
				tituloTexto[1] = "Cndigo";// titulo de la columna 1
				tituloTexto[2] = "Nombre";// titulo de la columna 2
				tituloTexto[3] = "Direccinn";// titulo de la columna 3
				tituloTexto[4] = "Telnfono";// titulo de la columna 4
				tituloTexto[5] = "Director";// titulo de la columna 5
				tituloTexto[6] = "Municipio";
				criterioLista[1] = "select G_ConCodigo,G_ConNombre from G_Constante where G_ConTipo = 7";
				tituloLista[1] = "Municipio";// titulo del primer 'select'

				buscar = "select bul.nucCodigo, bul.nucNombre,bul.nucDireccion,bul.nucTelefono,bul.nucDirector,ano.G_ConNombre ";
				buscar += "from G_Constante ano,G_Nucleo bul ";
				buscar += " where ano.G_ConTipo = 7 and bul.nucMunicipio=ano.G_ConCodigo ";
				if (!lista[1].equals("0"))
					buscar += " and bul.nucMunicipio = " + lista[1];
				buscar += " order by  bul.nucCodigo";
				inicio = 1;
				fin = 6;
				if (busqueda)
					tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
							inicio, fin, dato);
				else
					filtro = cursor.filtro(criterioLista, lista, tituloLista,
							dato);
				break;

			case 29:

				tituloTexto[2] = "Nombre";
				tituloTexto[3] = "área";
				criterioLista[1] = "select G_AreCodigo,G_AreNombre from G_Area order by G_AreNombre";
				tituloLista[1] = "área";

				buscar = "select bul.g_asiCodigo, bul.g_asiNombre,ano.g_areNombre ";
				buscar += "from G_Area ano,G_Asignatura bul ";
				buscar += " where bul.g_asicodarea=ano.g_areCodigo ";
				if (!lista[1].equals("0"))
					buscar += " and bul.g_asicodarea = " + lista[1];
				buscar += " order by bul.g_asiNombre";
				inicio = 1;
				fin = 3;
				if (busqueda)
					tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
							inicio, fin, dato);
				else
					filtro = cursor.filtro(criterioLista, lista, tituloLista,
							dato);
				break;

			case 28:// Areas de la institucinn OK

				tituloTexto[1] = "área";
				tituloTexto[2] = "Orden";
				tituloTexto[3] = "Abreviatura";

				// AreCodMetod,AreCodigo
				buscar = "select AreNombre,AreOrden,AreAbrev,AreCodMetod,AreCodigo,G_ConNombre from "
						+ datoMaestro.getTabla() + ",g_constante,metodologia";
				buscar += " where AreCodInst=" + institucion;
				buscar += " and g_contipo=3";
				buscar += " and MetCodigo=G_ConCodigo";
				buscar += " and Metcodinst=Arecodinst";
				buscar += " and AreCodMetod=Metcodigo";
				buscar += " and MetCodigo = " + metodologiaid;
				buscar += " and AreVigencia=" + vigencia;
				buscar += " order by AreNombre";

				inicio = 1;
				fin = 3;
				System.out.println("AREAS OC4J: " + buscar);
				tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
						inicio, fin, dato);

				break;

			case 30:// Asignaturas

				tituloTexto[1] = "área";
				tituloTexto[2] = "Asignatura";
				tituloTexto[3] = "Orden";
				tituloTexto[4] = "Abreviatura";

				criterioLista[1] = "select AreCodigo,AreNombre from Area where AreCodInst= -1";

				if (!metodologiaid.equals(""))
					criterioLista[1] = "select AreCodigo,AreNombre from Area where AreCodInst="
							+ institucion
							+ " and arecodmetod="
							+ metodologiaid
							+ " and arevigencia="
							+ vigencia
							+ " order by Arenombre";

				tituloLista[1] = "área";

				// AsiCodMetod,AsiCodigo
				buscar = "select AreNombre,Asinombre,AsiOrden,AsiAbrev,AsiCodMetod,AsiCodigo,G_ConNombre from "
						+ datoMaestro.getTabla()
						+ ", g_constante, Area, metodologia ";
				buscar += " where AsiCodInst=" + institucion;
				buscar += " and AsiVigencia=" + vigencia;
				buscar += " and AreVigencia=AsiVigencia";
				buscar += " and g_contipo=3";
				buscar += " and MetCodigo=G_ConCodigo ";
				buscar += " and Metcodinst=Asicodinst ";
				buscar += " and asicodinst=arecodinst ";
				buscar += " and arecodmetod=MetCodigo ";
				buscar += " and AsiCodMetod=MetCodigo ";
				buscar += " and asicodarea=arecodigo ";
				if (!lista[1].equals("0"))
					buscar += " and asicodarea = " + lista[1];
				buscar += " Order by G_ConNombre,AreNombre,Asinombre,AsiOrden";

				inicio = 1;
				fin = 4;

				if (busqueda)
					tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
							inicio, fin, dato);
				else
					filtro = cursor.filtro(criterioLista, lista, tituloLista,
							dato);
				break;

			/**
			 * /////////////////////////////////////// PLAN DE ESTUDIOS
			 * ///////////////////////////////////////
			 */
			case 31:

				tituloTexto[1] = "Plan de Estudios";

				buscar = "select substr(placriterio,1,100),G_ConNombre,G_ConCodigo from G_Constante,"
						+ datoMaestro.getTabla() + ",metodologia";
				buscar += " where PlaCodInst=" + institucion;
				buscar += " and PlaVigencia=" + vigencia;
				buscar += " and MetCodigo=G_ConCodigo";
				buscar += " and metcodinst=placodinst";
				buscar += " and g_contipo=3 and PlaCodMetod=MetCodigo";
				inicio = 1;
				fin = 1;
				tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
						inicio, fin, dato);
				break;

			case 37:// Logro

				tituloTexto[1] = "Grado";
				tituloTexto[2] = "Asignatura";
				tituloTexto[3] = "Nombre";
				tituloTexto[4] = "Abreviatura";

				criterioLista[2] = "select AsiCodigo,AsiNombre from Asignatura where AsiCodigo=-1";

				criterioLista[1] = "select distinct GraCodigo,GraNombre from Grado where GraCodInst= "
						+ institucion
						+ " and GraCodMetod="
						+ metodologiaid
						+ " and Gracodigo>=0 or (Gracodigo=40 or Gracodigo=41) Order by GraCodigo";
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
					criterioLista[2] += " order by AsiNombre";
				}
				tituloLista[2] = "Asignatura";

				// LogCodigo
				buscar = " select GraNombre,AsiNombre,LogNombre,LogAbrev,LogCodigo,Logcodjerar,SUBSTR(G_ConNombre,1,10) ";
				buscar += " from Logro,g_constante,grado,asignatura,g_jerarquia_tipo3_nivel4 ";
				buscar += " where logcodjerar=codigo_jerarquia ";
				buscar += " and logvigencia=" + vigencia;
				buscar += " and vigencia=logvigencia";
				buscar += " and cod_institucion=" + institucion + " ";
				buscar += " and gracodigo=cod_grado ";
				buscar += " and gracodinst=cod_institucion ";
				buscar += " and gracodmetod=cod_metod ";
				buscar += " and asicodigo=cod_asignatura ";
				buscar += " and asicodinst=cod_institucion ";
				buscar += " and asicodmetod=cod_metod ";
				buscar += " and asivigencia=vigencia ";
				buscar += " and g_contipo=3 ";
				buscar += " and g_concodigo=cod_metod ";

				if (!lista[1].equals("-99") && !lista[2].equals("-99")) {
					buscar += " and cod_grado = " + lista[1];
					buscar += " and cod_asignatura = " + lista[2];
				} else {
					if (!lista[1].equals("-99") && lista[2].equals("-99"))
						buscar += " and cod_grado = " + lista[1];
				}

				buscar += " order by GraCodigo,AsiNombre ";

				// System.out.println("QUERY LOGRO: "+buscar);

				inicio = 1;
				fin = 4;

				if (busqueda)
					tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
							inicio, fin, dato);// creacion de la tabla de
												// resultados
				else
					filtro = cursor.filtro(criterioLista, lista, tituloLista,
							dato);// creacion del filtro de busqueda
				break;

			case 39:// Descriptor Valorativo

				tituloTexto[1] = "Grado";
				tituloTexto[2] = "área";
				tituloTexto[3] = "Nombre";
				tituloTexto[4] = "Tipo";
				tituloTexto[5] = "Abreviatura";

				criterioLista[2] = "select AreCodigo,AreNombre from Area where AreCodigo=-1";

				criterioLista[3] = "select TipDesCodigo,TipDesNombre from Tipo_Descriptor ";
				tituloLista[3] = "Tipo Descriptor";

				criterioLista[1] = "select distinct GraCodigo,GraNombre from Grado where GraCodInst= "
						+ institucion
						+ " and GraCodMetod="
						+ metodologiaid
						+ "  and Gracodigo>=0 or (Gracodigo=40 or Gracodigo=41) Order by GraCodigo";
				tituloLista[1] = "Grado";

				if (!x.equals("")) {
					criterioLista[2] = " select AreCodigo,AreNombre from Area,Grado_Area where AreCodigo=GraAreCodArea ";
					criterioLista[2] += " and arecodinst=" + institucion;
					criterioLista[2] += " and arevigencia=" + vigencia;
					criterioLista[2] += " and graarevigencia=arevigencia ";
					criterioLista[2] += " and arecodinst=graarecodinst ";
					criterioLista[2] += " and arecodmetod=" + metodologiaid;
					criterioLista[2] += " and arecodmetod=graarecodmetod ";
					criterioLista[2] += " and GraAreCodGrado=" + x;
					criterioLista[2] += " order by AreNombre";
				}
				tituloLista[2] = "área";

				buscar = " select GraNombre,AreNombre,DesNombre,SUBSTR(TipDesNombre,1,1),DesAbrev,DesCodigo,Descodjerar,SUBSTR(G_ConNombre,1,10) ";
				buscar += " from Descriptor_Valorativo,g_constante,grado,area,g_jerarquia_tipo2_nivel4 a,Tipo_Descriptor ";
				buscar += " where Descodjerar=a.codigo_jerarquia ";
				buscar += " and Desvigencia=" + vigencia;
				buscar += " and a.vigencia=Desvigencia";
				buscar += " and a.cod_institucion=" + institucion + " ";
				buscar += " and gracodigo=a.cod_grado ";
				buscar += " and gracodinst=a.cod_institucion ";
				buscar += " and gracodmetod=a.cod_metod ";
				buscar += " and arecodigo=a.cod_area  ";
				buscar += " and arecodinst=a.cod_institucion ";
				buscar += " and arecodmetod=a.cod_metod ";
				buscar += " and arevigencia=a.vigencia ";
				buscar += " and g_contipo=3 ";
				buscar += " and g_concodigo=a.cod_metod ";
				buscar += " and TipDesCodigo=DesCodTipDes ";
				if (!lista[1].equals("-99") && !lista[2].equals("-99")
						&& !lista[3].equals("-99")) {
					buscar += " and a.cod_grado = " + lista[1];
					buscar += " and a.cod_area = " + lista[2];
					buscar += " and DesCodTipDes = " + lista[3];
				} else {
					if (!lista[1].equals("-99") && !lista[2].equals("-99")
							&& lista[3].equals("-99")) {
						buscar += " and a.cod_grado = " + lista[1];
						buscar += " and a.cod_area = " + lista[2];
					} else {
						if (!lista[1].equals("-99") && lista[2].equals("-99")
								&& lista[3].equals("-99"))
							buscar += " and a.cod_grado = " + lista[1];
						else {
							if (lista[1].equals("-99")
									&& lista[2].equals("-99")
									&& !lista[3].equals("-99"))
								buscar += " and DesCodTipDes = " + lista[3];
						}
					}
				}
				buscar += " order by GraCodigo,AreNombre";

				// System.out.println("query descriptor: "+buscar);
				inicio = 1;
				fin = 5;

				if (busqueda)
					tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
							inicio, fin, dato);
				else
					filtro = cursor.filtro(criterioLista, lista, tituloLista,
							dato);
				break;

			case 40:// tipo de descriptor
				tituloTexto[1] = "Cndigo";// titulo de la columna 1
				tituloTexto[2] = "Nombre";// titulo de la columna 2
				buscar = "select * from " + datoMaestro.getTabla()
						+ " order by " + datoMaestro.getCampo(1);// consulta
																	// para
																	// extraer
																	// los datos
				inicio = 1;// desde cual columna de la tabla
				fin = 2;// hasta que columna de la tabla
				editar = null;
				tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
						inicio, fin, dato);// creacion de la tabla de resultados
				break;

			case 42:// Escala valorativa
				tituloTexto[1] = "Cndigo";
				tituloTexto[2] = "Nombre";
				tituloTexto[3] = "Abreviatura";
				tituloTexto[4] = "Tipo de Escala";
				criterioLista[1] = "select G_ConCodigo,G_ConNombre from G_Constante where G_ConTipo = 23  order by G_ConCodigo ";
				tituloLista[1] = "Tipo de Escala";

				buscar = "select * from " + datoMaestro.getTabla()
						+ " order by " + datoMaestro.getCampo(1);

				inicio = 1;// desde cual columna de la tabla
				fin = 4;// hasta que columna de la tabla
				tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
						inicio, fin, dato);// creacion de la tabla de resultados
				break;

			case 43:// G_Constante Tipo escala valorativa
				tituloTexto[1] = "Cndigo";
				tituloTexto[2] = "Nombre";
				tituloTexto[3] = "Abreviatura";
				tituloTexto[4] = "Orden";
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden from G_Constante ";
				buscar += "where G_ConTipo = 23";
				buscar += " order by " + datoMaestro.getCampo(2);// filtro sobre
																	// la
																	// consulta
				inicio = 1;// desde cual columna de la tabla
				fin = 4;// hasta que columna de la tabla
				tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
						inicio, fin, dato);// creacion de la tabla de resultados
				break;

			case 44:// G_Constante RangoTarifa
				tituloTexto[1] = "Cndigo";
				tituloTexto[2] = "Nombre";
				tituloTexto[3] = "Abreviatura";
				tituloTexto[4] = "Orden";
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden from G_Constante ";
				buscar += "where G_ConTipo = 20";
				buscar += " order by " + datoMaestro.getCampo(2);// filtro sobre
																	// la
																	// consulta
				inicio = 1;// desde cual columna de la tabla
				fin = 4;// hasta que columna de la tabla
				tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
						inicio, fin, dato);
				break;

			case 45:// G_Constante Asociaciones privadas
				tituloTexto[1] = "Cndigo";
				tituloTexto[2] = "Nombre";
				tituloTexto[3] = "Abreviatura";
				tituloTexto[4] = "Orden";
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden from G_Constante ";
				buscar += "where G_ConTipo = 21";
				buscar += " order by " + datoMaestro.getCampo(2);// filtro sobre
																	// la
																	// consulta
				inicio = 1;// desde cual columna de la tabla
				fin = 4;// hasta que columna de la tabla
				tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
						inicio, fin, dato);
				break;

			case 46:// G_Constante Asociaciones Idioma
				tituloTexto[1] = "Cndigo";
				tituloTexto[2] = "Nombre";
				tituloTexto[3] = "Abreviatura";
				tituloTexto[4] = "Orden";
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden from G_Constante ";
				buscar += "where G_ConTipo = 22";
				buscar += " order by " + datoMaestro.getCampo(2);// filtro sobre
																	// la
																	// consulta
				inicio = 1;// desde cual columna de la tabla
				fin = 4;// hasta que columna de la tabla
				tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
						inicio, fin, dato);
				break;

			case 47:// G_Constante Cargo GE
				tituloTexto[1] = "Cndigo";
				tituloTexto[2] = "Nombre";
				tituloTexto[3] = "Abreviatura";
				tituloTexto[4] = "Orden";
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden from G_Constante ";
				buscar += "where G_ConTipo = 24";
				buscar += " order by " + datoMaestro.getCampo(2);// filtro sobre
																	// la
																	// consulta
				inicio = 1;
				fin = 4;
				tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
						inicio, fin, dato);
				break;

			case 48:// Metodolognas de la Institucinn
				editar = null;
				tituloTexto[1] = "Cndigo";
				tituloTexto[2] = "Nombre";
				buscar = " select g_concodigo,g_connombre from g_constante,metodologia  ";
				buscar += " where g_contipo=3 ";
				buscar += " and metcodigo=g_concodigo and MetCodInst="
						+ institucion;
				buscar += " order by " + datoMaestro.getCampo(2);
				inicio = 1;
				fin = 2;
				tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
						inicio, fin, dato);
				break;

			case 49:// Tipo Atencinn Especial
				tituloTexto[1] = "Cndigo";
				tituloTexto[2] = "Nombre";
				tituloTexto[3] = "Abreviatura";
				tituloTexto[4] = "Orden";
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden from G_Constante ";
				buscar += "where G_ConTipo = 31";
				buscar += " order by " + datoMaestro.getCampo(2);
				inicio = 1;
				fin = 4;
				tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
						inicio, fin, dato);
				break;

			case 50: // ROT_TIPO_RECESO
				tituloTexto[1] = "Cndigo";
				tituloTexto[2] = "Nombre";
				buscar = "select * from " + datoMaestro.getTabla()
						+ " order by " + datoMaestro.getCampo(3);
				inicio = 1;
				fin = 2;
				tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
						inicio, fin, dato);
				break;

			case 51:// Tipo Especialidad
				tituloTexto[1] = "Cndigo";
				tituloTexto[2] = "Nombre";
				tituloTexto[3] = "Abreviatura";
				tituloTexto[4] = "Orden";
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden from G_Constante ";
				buscar += "where G_ConTipo = 34";
				buscar += " order by " + datoMaestro.getCampo(2);
				inicio = 1;
				fin = 4;
				tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
						inicio, fin, dato);
				break;

			case 52:// Tipo Modalidad
				tituloTexto[1] = "Cndigo";
				tituloTexto[2] = "Nombre";
				tituloTexto[3] = "Abreviatura";
				tituloTexto[4] = "Orden";
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden from G_Constante ";
				buscar += "where G_ConTipo = 33";
				buscar += " order by " + datoMaestro.getCampo(2);
				inicio = 1;
				fin = 4;
				tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
						inicio, fin, dato);
				break;

			case 53:// Tipo Enfasis
				tituloTexto[1] = "Cndigo";
				tituloTexto[2] = "Nombre";
				tituloTexto[3] = "Abreviatura";
				tituloTexto[4] = "Orden";
				buscar = "select G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden from G_Constante ";
				buscar += "where G_ConTipo = 32";
				buscar += " order by " + datoMaestro.getCampo(2);
				inicio = 1;
				fin = 4;
				tabla = cursor.getTablaMaestra(editar, buscar, tituloTexto,
						inicio, fin, dato);
				break;

			}
			request.setAttribute("filtro", filtro);// asignacion del filtro de
													// busqueda al ambito de la
													// peticion
			request.setAttribute("tabla", tabla);// asignacion de la tabla de
													// respuesta al ambito de la
													// peticion
			request.setAttribute("titulo", datoMaestro.getTitulo());// asignacion
																	// del
																	// titulo de
																	// la tabla
																	// de
																	// resultados
																	// al ambito
																	// de la
																	// peticion
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
	 * @param int a: 1=redirigir como 'include', 2=redirigir como 'forward'
	 * @param String
	 *            s
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public void ir(int a, String s, HttpServletRequest rq,
			HttpServletResponse rs) throws ServletException, IOException {
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
	 */
	public void destroy() {
		if (cursor != null)
			cursor.cerrar();

		cursor = null;
		util = null;
	}

}
