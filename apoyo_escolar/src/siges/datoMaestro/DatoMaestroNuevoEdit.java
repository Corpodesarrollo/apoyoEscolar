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
 * Nombre: DatoMaestroNuevoEdit.java <BR>
 * Descripcinn: Solicitado desde la pagina 'DatoMaestro.jsp' como la pagina del frame '2', que mostrara el formulario de insercion del dato maestro solicitado.<BR>
 * Funciones de la pngina: Usar el objeto DatoMaestroBean de la sesion para generar un un formulario de insersion de registros de ese dato maestro <BR>
 * Entidades afectadas: No aplica <BR>
 * Fecha de modificacinn: 20/07/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */

public class DatoMaestroNuevoEdit extends HttpServlet {
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
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String institucion;
		String nivel;
		Login login;
		String[] codigou1 = null;
		String metodologiaid;// variable que almacena el id de la metodologia de la institucion
		String metodologia;// variable que almacena el nombre de la metodologia de la institucion

		int dato;// variable que captura numero de dato maestro del que vamos a mostrar datos
		String sig;// nombre de la pagina a la que ira despues de ejecutar los comandos de esta
		String ant;// pagina a la que ira en caso de que no se pueda procesar esta pagina
		String er;// nombre de la pagina a la que ira si hay errores
		String home;// nombre de la pagina a la que ira si hay errores
		String text;// variable que contiene el id del registro (inicialmente no se usa en este servlet)
		String text2;// variable que contiene la columna 2 del registro (inicialmente no se usa en este servlet)
		String text3;// variable que contiene la columna 3 del registro (inicialmente no se usa en este servlet)
		String text4;// variable que contiene la columna 4 del registro (inicialmente no se usa en este servlet)
		String text5;// variable que contiene la columna 5 del registro (inicialmente no se usa en este servlet)
		String text6;// variable que contiene la columna 6 del registro (inicialmente no se usa en este servlet)
		String text7;// variable que contiene la columna 7 del registro (inicialmente no se usa en este servlet)
		String text8;// variable que contiene la columna 8 del registro (inicialmente no se usa en este servlet)
		String text9;// variable que contiene la columna 9 del registro (inicialmente no se usa en este servlet)
		String filtro;// variable que captura los 'selects' html o los inputs de tipo 'TEXT' del formulario de insercion de el dato maestro seleccionado
		String algo[];// vector que contiene los valores
		String criterioLista[];// vector que contiene las sentencias sql que permiten llenar de opciones los 'selects' html
		String tituloLista[];// titulo que tendran los 'selects' html en el formulario de busqueda
		String lista[];// vector que contiene los items seleccionados de los 'select'
		String texto[];// vector que contiene el texto digitado en los input de tipo 'TEXT'
		String tituloTexto[];// titulo de las columnas que tendra la tabla de resultados
		String tituloCheck[];// titulo de las columnas que tendra la tabla de resultados
		String tituloHora[];// titulo de las columnas que tendra la tabla de resultados
		String searchcode;// consulta sql para encontrar el cndigo de la jerarquia
		String searchunique;// consulta sql para encontrar el registro en plan de estudios
		String consul;// consulta sql para encontrar el cndigo de la jerarquia
		String[] codigol;
		String vigencia;
		String x, y, z;// valor de un select para hacer el siguiente select dinamico
		DatoMaestroBean datoMaestro;// objeto que tendra los datos referentes al dato maestro escogido
		// Util util=new Util();
		sig = "/DatoMaestroNuevo.jsp";// pagina a la que se dara el control despues de procesar este servlet
		ant = "/index.jsp";// pagina a la que se dara el control si algo falla
		er = "/error.jsp";
		home = "/bienvenida.jsp";
		filtro = "";
		text = text2 = null;
		codigol = null;
		algo = null;
		consul = null;
		searchunique = null;
		text3 = text4 = text5 = text6 = text7 = text8 = text9 = null;
		searchcode = null;

		try {
			cursor = new Cursor();
			util = new Util(cursor);
			dao = new Dao(cursor);

			login = (Login) request.getSession().getAttribute("login");
			criterioLista = new String[8];
			tituloLista = new String[8];
			tituloTexto = new String[10];
			tituloCheck = new String[6];
			tituloHora = new String[6];
			lista = new String[8];
			texto = new String[10];
			datoMaestro = (DatoMaestroBean) request.getSession().getAttribute("datoMaestro");
			if (datoMaestro == null)
				sig = home;
			else {
				lista[1] = (request.getParameter("lista1") != null) ? request.getParameter("lista1") : new String("0");// captura el valor de el select cuyo nombre es 'lista1'
				lista[2] = (request.getParameter("lista2") != null) ? request.getParameter("lista2") : new String("0");// captura el valor de el select cuyo nombre es 'lista2'
				lista[3] = (request.getParameter("lista3") != null) ? request.getParameter("lista3") : new String("0");// captura el valor de el select cuyo nombre es 'lista3'
				lista[4] = (request.getParameter("lista4") != null) ? request.getParameter("lista4") : new String("0");// captura el valor de el select cuyo nombre es 'lista4'
				lista[5] = (request.getParameter("lista5") != null) ? request.getParameter("lista5") : new String("0");// captura el valor de el select cuyo nombre es 'lista5'
				lista[6] = (request.getParameter("lista6") != null) ? request.getParameter("lista6") : new String("0");// captura el valor de el select cuyo nombre es 'lista6'
				lista[7] = (request.getParameter("lista7") != null) ? request.getParameter("lista7") : new String("0");// captura el valor de el select cuyo nombre es 'lista6'
				texto[1] = (request.getParameter("texto1") != null) ? request.getParameter("texto1") : new String("");// captura el valor de el input de tipo TEXT cuyo nombre es 'texto1'
				texto[2] = (request.getParameter("texto2") != null) ? request.getParameter("texto2") : new String("");// captura el valor de el input de tipo TEXT cuyo nombre es 'texto2'
				texto[3] = (request.getParameter("texto3") != null) ? request.getParameter("texto3") : new String("");// captura el valor de el input de tipo TEXT cuyo nombre es 'texto3'
				texto[4] = (request.getParameter("texto4") != null) ? request.getParameter("texto4") : new String("");// captura el valor de el input de tipo TEXT cuyo nombre es 'texto4'
				texto[5] = (request.getParameter("texto5") != null) ? request.getParameter("texto5") : new String("");// captura el valor de el input de tipo TEXT cuyo nombre es 'texto5'
				texto[6] = (request.getParameter("texto6") != null) ? request.getParameter("texto6") : new String("");// captura el valor de el input de tipo TEXT cuyo nombre es 'texto6'
				texto[7] = (request.getParameter("texto7") != null) ? request.getParameter("texto7") : new String("");// captura el valor de el input de tipo TEXT cuyo nombre es 'texto7'
				texto[8] = (request.getParameter("texto8") != null) ? request.getParameter("texto8") : new String("");// captura el valor de el input de tipo TEXT cuyo nombre es 'texto8'
				texto[9] = (request.getParameter("texto9") != null) ? request.getParameter("texto9") : new String("");// captura el valor de el input de tipo TEXT cuyo nombre es 'texto9'
				String zz[] = { text, text2, text3, text4, text5, text6, text7, text8, text9 };

				x = request.getParameter("x") != null ? request.getParameter("x") : new String("");
				y = request.getParameter("y") != null ? request.getParameter("y") : new String("");
				z = request.getParameter("z") != null ? request.getParameter("z") : new String("");

				institucion = login.getInstId();
				nivel = login.getNivel();
				metodologia = login.getMetodologia();
				metodologiaid = login.getMetodologiaId();
				vigencia = dao.getVigencia();

				if (vigencia == null) {
					System.out.println("VIGENCIA NULA");
					ir(2, home, request, response);
					return;
				}

				dato = Integer.parseInt(datoMaestro.getDato());// numero del dato maestro al que hacemos referencia

				switch (dato) {

				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
				case 9:
				case 11:
				case 12:
				case 14:
				case 15:
				case 16:
				case 17:
				case 18:
				case 19:
				case 20:
				case 24:
				case 25:
				case 26:
				case 43:
				case 44:
				case 45:
				case 46:
				case 47:
				case 49:
				case 51:
				case 52:
				case 53:// G_Constante
					tituloTexto[1] = "Cndigo";// titulo de la columna 1
					tituloTexto[2] = "Nombre";// titulo de la columna 2
					tituloTexto[3] = "Abreviatura";// titulo de la columna 3
					tituloTexto[4] = "Orden";// titulo de la columna 4
					filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
					break;

				case 13:// condicion
					tituloTexto[1] = "Cndigo";// titulo del campo 1
					tituloTexto[2] = "Nombre";// titulo del campo 2
					filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
					break;

				case 6:
				case 8:// G_TipoEspacio,G_TipoReconocimiento
					tituloTexto[1] = "Cndigo";// titulo del campo 1
					tituloTexto[2] = "Nombre";// titulo del campo 2
					filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
					break;

				case 10:
					tituloTexto[1] = "Cndigo";// titulo del campo 1
					tituloTexto[2] = "Nombre";// titulo del campo 1
					criterioLista[1] = "select G_ConCodigo,G_ConNombre from G_Constante where G_ConTipo = 7";// datos del primer 'select'
					tituloLista[1] = "Municipio";// titulo del primer 'select'
					filtro = cursor.lista(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
					break;

				case 21:
					tituloTexto[1] = "Cndigo";// titulo del campo 1
					tituloTexto[2] = "Nombre";// titulo del campo 1
					criterioLista[1] = "select G_ConCodigo,G_ConNombre from G_Constante where G_ConTipo = 6";// datos del primer 'select'
					tituloLista[1] = "Departamento";// titulo del primer 'select'

					filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
					break;

				case 22:
					tituloTexto[1] = "Código";// titulo del campo 1
					tituloTexto[2] = "Nombre";// titulo del campo 1
					criterioLista[1] = "SELECT  1 id, 'Obligatoria' nombre FROM DUAL UNION  SELECT 2 id, 'Opcional' nombre FROM DUAL";
					tituloLista[1] = "Tipo de área";// titulo del primer 'select'

					filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
					break;

				case 23:// G_Constante Grado
					criterioLista[1] = "select G_ConCodigo,G_ConNombre from G_Constante where G_ConTipo = 1";
					tituloLista[1] = "Nivel";

					tituloTexto[2] = "Cndigo";
					tituloTexto[3] = "Nombre";
					tituloTexto[4] = "Abreviatura";
					tituloTexto[5] = "Orden";

					filtro = cursor.listadatogeneral(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
					break;

				case 27:// G_Nucleo

					criterioLista[1] = "select G_ConCodigo,G_ConNombre from G_Constante where G_ConTipo = 7";// datos del primer 'select'
					tituloLista[1] = "Municipio";// titulo del primer 'select'

					tituloTexto[2] = "Cndigo";
					tituloTexto[3] = "Nombre";
					tituloTexto[4] = "Direccinn";
					tituloTexto[5] = "Telnfono";
					tituloTexto[6] = "Director";

					filtro = cursor.listadivision(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
					break;

				case 29:
					criterioLista[1] = "select G_AreCodigo,G_AreNombre from G_Area order by G_AreNombre";// datos del primer 'select'
					tituloLista[1] = "área";// titulo del primer 'select'
					tituloTexto[1] = "Cndigo";// titulo del campo 1
					tituloTexto[2] = "Nombre";// titulo del campo 1

					filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
					break;

				case 28:// nreas de la institucinn

					criterioLista[1] = "select G_AreCodigo,G_AreNombre from G_Area  order by G_AreNombre";
					tituloLista[1] = "área";

					tituloTexto[2] = "Nombre";
					tituloTexto[3] = "Orden";
					tituloTexto[4] = "Abreviatura";

					if (!metodologiaid.equals(""))
						tituloCheck[1] = "select distinct GraCodigo,GraNombre from grado where GraCodInst= " + institucion + " and gracodmetod=" + metodologiaid + " and Gracodigo>=0 or (Gracodigo=40 or Gracodigo=41) order by GraCodigo";

					filtro = cursor.listaarea(tituloCheck, criterioLista, tituloLista, lista, tituloTexto, texto, zz, algo);
					break;

				case 30:// asignatura

					criterioLista[1] = "select AreCodigo,AreNombre from Area where AreCodInst= -1";
					criterioLista[2] = "select G_AsiCodigo,G_AsiNombre from G_Asignatura where G_AsiCodigo=-1";

					criterioLista[1] = "select AreCodigo,AreNombre from Area where AreCodInst=" + institucion + " and arecodmetod=" + metodologiaid;
					criterioLista[1] += " and AreVigencia=" + vigencia;
					criterioLista[1] += " order by AreNombre ";
					tituloLista[1] = "área";

					if (!x.equals(""))
						criterioLista[2] = "select G_AsiCodigo,G_AsiNombre from G_Asignatura where G_AsiCodArea=" + x + " order by G_AsiNombre";

					tituloLista[2] = "Asignatura";

					tituloTexto[3] = "Nombre";
					tituloTexto[4] = "Orden";
					tituloTexto[5] = "Abreviatura";

					if (!x.equals("")) {
						tituloCheck[1] = " select GraAreCodGrado,granombre from grado_area,grado";
						tituloCheck[1] += " where GraAreCodInst=" + institucion;
						tituloCheck[1] += " and GraAreVigencia=" + vigencia;
						tituloCheck[1] += " and graarecodinst=gracodinst ";
						tituloCheck[1] += " and graarecodgrado=gracodigo";
						tituloCheck[1] += " and GraAreCodMetod =" + metodologiaid;
						tituloCheck[1] += " and GraAreCodMetod=GraCodMetod";
						tituloCheck[1] += " and GraAreCodArea =" + x + " order by GraAreCodGrado";
					}

					filtro = cursor.listaasignatura(tituloCheck, criterioLista, tituloLista, lista, tituloTexto, texto, zz, algo);
					break;

				case 31:// Plan de estudios por metodologna de institucinn

					tituloTexto[1] = "Criterios de Evaluacinn";
					tituloTexto[2] = "Procedimientos de Evaluacinn";
					tituloTexto[3] = "Planes Especiales de Apoyo";

					filtro = cursor.listatextarea(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
					break;

				case 37:// Logro

					criterioLista[2] = "select AsiCodigo,AsiNombre from Asignatura where AsiCodigo=-1";

					criterioLista[1] = "select distinct GraCodigo,GraNombre from Grado where GraCodInst= " + institucion + " and GraCodMetod=" + metodologiaid + " and Gracodigo>=0 or (Gracodigo=40 or Gracodigo=41) Order by GraCodigo";
					tituloLista[1] = "Grado";

					if (!x.equals("")) {
						criterioLista[2] = " select AsiCodigo,AsiNombre from Asignatura,Grado_Asignatura where AsiCodigo=GraAsiCodAsign ";
						criterioLista[2] += " and asicodinst=" + institucion;
						criterioLista[2] += " and asivigencia=" + vigencia;
						criterioLista[2] += " and graasivigencia=asivigencia";
						criterioLista[2] += " and asicodinst=graasicodinst ";
						criterioLista[2] += " and asicodmetod=" + metodologiaid;
						criterioLista[2] += " and asicodmetod=graasicodmetod ";
						criterioLista[2] += " and GraAsiCodGrado=" + x;
						criterioLista[2] += " order by AsiNombre";
					}
					tituloLista[2] = "Asignatura";

					criterioLista[3] = "  select 1 as cod, 'Primero' as nombre from dual union ";
					criterioLista[3] += " select 2 as cod, 'Segundo' as nombre from dual union ";
					criterioLista[3] += " select 3 as cod, 'Tercero' as nombre from dual union ";
					criterioLista[3] += " select 4 as cod, 'Cuarto' as nombre from dual union ";
					criterioLista[3] += " select 5 as cod, 'Quinto' as nombre from dual";

					tituloLista[3] = "Periodo inicial";

					criterioLista[4] = "  select 1 as cod, 'Primero' as nombre from dual union ";
					criterioLista[4] += " select 2 as cod, 'Segundo' as nombre from dual union ";
					criterioLista[4] += " select 3 as cod, 'Tercero' as nombre from dual union ";
					criterioLista[4] += " select 4 as cod, 'Cuarto' as nombre from dual union ";
					criterioLista[4] += " select 5 as cod, 'Quinto' as nombre from dual";

					tituloLista[4] = "Periodo final";

					tituloTexto[5] = "Logro";
					tituloTexto[6] = "Abreviatura";
					tituloTexto[7] = "Descripcinn";

					filtro = cursor.listalogro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
					break;

				case 39:// Descriptores Valorativos

					criterioLista[2] = "select AreCodigo,AreNombre from Area where AreCodigo=-1";

					criterioLista[1] = "select distinct GraCodigo,GraNombre from Grado where GraCodInst= " + institucion + " and GraCodMetod=" + metodologiaid + "  and Gracodigo>=0 or (Gracodigo=40 or Gracodigo=41) order by GraCodigo";
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

					criterioLista[3] = "select TipDesCodigo,TipDesNombre from Tipo_Descriptor ";
					tituloLista[3] = "Tipo Descriptor";

					criterioLista[4] = "  select 1 as cod, 'Primero' as nombre from dual union ";
					criterioLista[4] += " select 2 as cod, 'Segundo' as nombre from dual union ";
					criterioLista[4] += " select 3 as cod, 'Tercero' as nombre from dual union ";
					criterioLista[4] += " select 4 as cod, 'Cuarto' as nombre from dual ";

					tituloLista[4] = "Periodo inicial";

					criterioLista[5] = "  select 1 as cod, 'Primero' as nombre from dual union ";
					criterioLista[5] += " select 2 as cod, 'Segundo' as nombre from dual union ";
					criterioLista[5] += " select 3 as cod, 'Tercero' as nombre from dual union ";
					criterioLista[5] += " select 4 as cod, 'Cuarto' as nombre from dual ";

					tituloLista[5] = "Periodo final";

					tituloTexto[6] = "Descriptor";
					tituloTexto[7] = "Abreviatura";
					tituloTexto[8] = "Descripcinn";

					filtro = cursor.listadescriptor(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
					break;

				case 42:// Escala valorativa

					tituloTexto[1] = "Cndigo";
					tituloTexto[2] = "Nombre";
					tituloTexto[3] = "Abreviatura";

					criterioLista[1] = "select G_ConCodigo,G_ConNombre from G_Constante where G_ConTipo=23 order by G_ConCodigo";
					tituloLista[1] = "Tipo Escala";

					filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
					break;

				case 50:
					tituloTexto[1] = "Cndigo";
					tituloTexto[2] = "Nombre";
					tituloTexto[3] = "Orden";
					filtro = cursor.listadatomaestro(criterioLista, tituloLista, lista, tituloTexto, texto, zz);
					break;

				}
				request.setAttribute("titulo", datoMaestro.getTitulo());// asignacion del titulo del formulario al ambito de la peticion
				request.setAttribute("filtro", filtro);// asignacion del filtro de busqueda al ambito de la peticion
			}
			ir(2, sig, request, response);// llama a la funcion que sede el control a la siguiente pagina
			return;
		} catch (Exception e) {
			System.out.println("Error " + this + ": " + e.toString());
			throw new ServletException(e);
		} finally {
			if (cursor != null)
				cursor.cerrar();
			if (dao != null)
				dao.cerrar();
			if (util != null)
				util.cerrar();
		}
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