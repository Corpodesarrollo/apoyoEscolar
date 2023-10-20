package siges.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;

/**
 * Nombre: Recursos <br>
 * Descripcinn: Cargar Recursos <br>
 * Funciones de la pngina: Carga todos los recursos a ser usados por la
 * aplicacinn <br>
 * Fecha de modificacinn: 20/07/2005 <br>
 * 
 * @author Latined <br>
 * @version v 1.0 <br>
 */
public class Recursos {
	private static ResourceBundle rb;
	private static Cursor cursor = new Cursor();
	public static int vigencia = 0, periodo = 0;
	public static int vigenciaPOA = 0, periodoPOA = 0;
	private static int i = 0, j = 0;
	public static long numper;
	public static String nomup;

	private static String[] consulta;

	public static final int NIVEL = i++;

	public static final int GRADO = i++;

	public static final int METODOLOGIA = i++;

	public static final int ESPECIALIDAD = i++;

	public static final int JORNADA = i++;

	public static final int DEPARTAMENTO = i++;

	public static final int MUNICIPIO = i++;

	public static final int LOCALIDAD = i++;

	public static final int TIPOOCUPANTE = i++;

	public static final int TIPODOCUMENTO = i++;

	public static final int CONVIVENCIA = i++;

	public static final int DISCAPACIDAD = i++;

	public static final int CAPACIDAD = i++;

	public static final int ETNIA = i++;

	public static final int AUSENCIA = i++;

	public static final int PROGRAMA = i++;

	public static final int CONDICION = i++;

	public static final int TIPOESPACIO = i++;

	public static final int TIPORECONOCIMIENTO = i++;

	public static final int CONVENIO = i++;

	public static final int FAMILIA = i++;

	public static final int CALENDARIO = i++;

	public static final int NUCLEO = i++;

	public static final int PROPIEDAD = i++;

	public static final int DISCAPACIDADINSTITUCION = i++;

	public static final int IDIOMA = i++;

	public static final int ASOCIACION = i++;

	public static final int TARIFA = i++;

	public static final int TIPOCARGO = i++;

	public static final int PERFIL = i++;

	public static final int GRUPO = i++;

	public static final int SERVICIOPERFIL = i++;

	public static final int TIPODESCRIPTOR = i++;

	public static final int CATEGORIA = i++;

	public static final int VIGENCIA = i++;

	public static final int TIPORECESO = i++;

	public static final int TIPOATENCIONESPECIAL = i++;

	public static final int TIPOESPECIALIDAD = i++;

	public static final int TIPOMODALIDAD = i++;

	public static final int TIPOENFASIS = i++;

	public static final int TIPOPROGRAMA = i++;

	public static final int PERIODO = j++;

	public static String[] NOMBRE = { "Nivel", "Grado", "Metodologia",
			"Especialidad", "Jornada", "Departamento", "Municipio",
			"Localidad", "TipoOcupante", "TipoDocumento", "Convivencia",
			"Discapacidad", "Capacidad", "Etnia", "Ausencia", "Programa",
			"Condicion", "TipoEspacio", "TipoReconocimiento", "Convenio",
			"Familia", "Calendario", "Nucleo", "Propiedad",
			"DiscapacidadInstitucion", "Idioma", "Asociacion", "Tarifa",
			"TipoCargo", "Perfil", "Grupo", "ServicioPerfil", "TipoDescriptor",
			"Categoria", "Vigencia", "TipoReceso", "TipoAtencionEspecial",
			"TipoEspecialidad", "TipoModalidad", "TipoEnfasis", "TipoPrograma" };

	public static final int TOTAL = NOMBRE.length;

	public static Collection[] recurso = new ArrayList[TOTAL];

	public static Collection[] recursoEstatico = new ArrayList[j];

	static public long getnumper() {
		return numper;
	}

	static public void setnumper(long numperi) {
		numper = numperi;
	}

	static public void setnomup(String s) {
		nomup = s;
	}

	static public String getnomup() {
		return nomup != null ? nomup : "";
	}

	private Recursos() {
	}

	/**
	 * Funcinn: Cargar Recursos <br>
	 */
	static public void Cargar() {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int m;
		Collection list;
		Object[] o;
		int i = 0, f = 0;
		try {
			System.out.println("Recursos -Iniciando-");
			rb = ResourceBundle.getBundle("statements");
			consulta = new String[TOTAL];
			cn = cursor.getConnection();
			for (int j = 0; j < TOTAL; j++) {
				// System.out.println("-- " + NOMBRE[j]);
				pst = cn.prepareStatement(rb.getString(NOMBRE[j]));
				rs = pst.executeQuery();
				m = rs.getMetaData().getColumnCount();
				// System.out.println("-- " + NOMBRE[j]+":"+m);
				list = new ArrayList();
				i = f = 0;
				while (rs.next()) {
					f++;
					o = new Object[m];
					for (i = 1; i <= m; i++)
						o[i - 1] = rs.getString(i);
					list.add(o);
				}
				recurso[j] = list;
				rs.close();
				pst.close();
			}
			// poner la vigencia
			pst = cn.prepareStatement(rb.getString("Vigencia"));
			rs = pst.executeQuery();
			if (rs.next()) {
				vigencia = rs.getInt(1);
				periodo = rs.getInt(2);
			}
			rs.close();
			pst.close();
			// poner la vigencia POA
			pst = cn.prepareStatement(rb.getString("VigenciaPOA"));
			rs = pst.executeQuery();
			if (rs.next()) {
				vigenciaPOA = rs.getInt(1);
				periodoPOA = rs.getInt(2);
			}
			rs.close();
			pst.close();
			
			numper = 4;
			
			ponerRecursosEstaticos();
			System.out.println("Recursos -Listo-");
			// System.out.println("---------------");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error cargando recursos a la aplicacion: " + e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	static public void CargarPOA() {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int m;
		Collection list;
		Object[] o;
		int i = 0, f = 0;
		try {
			rb = ResourceBundle.getBundle("statements");

			// poner la vigencia POA
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("VigenciaPOA"));
			rs = pst.executeQuery();
			if (rs.next()) {
				vigenciaPOA = rs.getInt(1);
				periodoPOA = rs.getInt(2);
			}
			rs.close();
			pst.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error cargando recursos POA: " + e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}

	static private void ponerRecursosEstaticos() {
		/* PERIODO */
		if (numper == 4) {
			recursoEstatico[PERIODO] = new ArrayList();
			Object[] o = new Object[2];
			o[0] = "1";
			o[1] = "Primero";
			recursoEstatico[PERIODO].add(o);
			o = new Object[2];
			o[0] = "2";
			o[1] = "Segundo";
			recursoEstatico[PERIODO].add(o);
			o = new Object[2];
			o[0] = "3";
			o[1] = "Tercero";
			recursoEstatico[PERIODO].add(o);
			o = new Object[2];
			o[0] = "4";
			o[1] = "Cuarto";
			recursoEstatico[PERIODO].add(o);
			o = new Object[2];
			o[0] = "7";
			o[1] = nomup;
			recursoEstatico[PERIODO].add(o);
		}
		if (numper == 1) {
			recursoEstatico[PERIODO] = new ArrayList();
			Object[] o = new Object[2];
			o[0] = "1";
			o[1] = "Primero";
			recursoEstatico[PERIODO].add(o);
			o = new Object[2];
			o[0] = "7";
			o[1] = nomup;
			recursoEstatico[PERIODO].add(o);
		}

		if (numper == 2) {
			recursoEstatico[PERIODO] = new ArrayList();
			Object[] o = new Object[2];
			o[0] = "1";
			o[1] = "Primero";
			recursoEstatico[PERIODO].add(o);
			o = new Object[2];
			o[0] = "2";
			o[1] = "Segundo";
			recursoEstatico[PERIODO].add(o);
			o = new Object[2];
			o[0] = "7";
			o[1] = nomup;
			recursoEstatico[PERIODO].add(o);
		}

		if (numper == 3) {
			recursoEstatico[PERIODO] = new ArrayList();
			Object[] o = new Object[2];
			o[0] = "1";
			o[1] = "Primero";
			recursoEstatico[PERIODO].add(o);
			o = new Object[2];
			o[0] = "2";
			o[1] = "Segundo";
			recursoEstatico[PERIODO].add(o);
			o = new Object[2];
			o[0] = "3";
			o[1] = "Tercero";
			recursoEstatico[PERIODO].add(o);
			o = new Object[2];
			o[0] = "7";
			o[1] = nomup;
			recursoEstatico[PERIODO].add(o);
		}

		if (numper == 5) {
			recursoEstatico[PERIODO] = new ArrayList();
			Object[] o = new Object[2];
			o[0] = "1";
			o[1] = "Primero";
			recursoEstatico[PERIODO].add(o);
			o = new Object[2];
			o[0] = "2";
			o[1] = "Segundo";
			recursoEstatico[PERIODO].add(o);
			o = new Object[2];
			o[0] = "3";
			o[1] = "Tercero";
			recursoEstatico[PERIODO].add(o);
			o = new Object[2];
			o[0] = "4";
			o[1] = "Cuarto";
			recursoEstatico[PERIODO].add(o);
			o = new Object[2];
			o[0] = "5";
			o[1] = "Quinto";
			recursoEstatico[PERIODO].add(o);
			o = new Object[2];
			o[0] = "7";
			o[1] = nomup;
			recursoEstatico[PERIODO].add(o);
		}

		if (numper == 6) {
			recursoEstatico[PERIODO] = new ArrayList();
			Object[] o = new Object[2];
			o[0] = "1";
			o[1] = "Primero";
			recursoEstatico[PERIODO].add(o);
			o = new Object[2];
			o[0] = "2";
			o[1] = "Segundo";
			recursoEstatico[PERIODO].add(o);
			o = new Object[2];
			o[0] = "3";
			o[1] = "Tercero";
			recursoEstatico[PERIODO].add(o);
			o = new Object[2];
			o[0] = "4";
			o[1] = "Cuarto";
			recursoEstatico[PERIODO].add(o);
			o = new Object[2];
			o[0] = "5";
			o[1] = "Quinto";
			recursoEstatico[PERIODO].add(o);
			o = new Object[2];
			o[0] = "6";
			o[1] = "Sexto";
			recursoEstatico[PERIODO].add(o);
			o = new Object[2];
			o[0] = "7";
			o[1] = nomup;
			recursoEstatico[PERIODO].add(o);
		}
		/**/
	}

	/**
	 * Funcinn: obtener recurso <br>
	 * 
	 * @param int n
	 * @return Collection
	 */
	static public Collection getRecurso(int n) {
		if (n >= 0 && n < TOTAL)
			return recurso[n];
		return null;
	}

	/**
	 * Funcinn: obtener recurso <br>
	 * 
	 * @param int n
	 * @return Collection
	 */
	static public Collection getRecursoEstatico(int n) {
		if (n >= 0 && n < recursoEstatico.length)
			return recursoEstatico[n];
		return null;
	}

	/**
	 * Funcinn: fijar recurso <br>
	 * 
	 * @param int n
	 * @param Collection
	 *            c
	 */
	static public void setRecurso(int n, Collection c) {
		if (n >= 0 && n < TOTAL)
			recurso[n] = c;
	}

	/**
	 * Funcinn: Fijar recurso <br>
	 * 
	 * @param int n
	 */
	static public void setRecurso(int n) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int m;
		Collection list;
		Object[] o;
		int i = 0, f = 0;
		try {
			if (n >= 0 && n < TOTAL) {
				cn = cursor.getConnection();
				pst = cn.prepareStatement(rb.getString(NOMBRE[n]));
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
				recurso[n] = list;
			}
		} catch (Exception e) {
			e.printStackTrace();
			siges.util.Logger.print("0", "Error cargando recurso [" + n + "]: "
					+ e, 7, 1, "siges.util.Recursos");
			System.out.println("Error cargando recurso [" + n + "]: " + e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
	}
}