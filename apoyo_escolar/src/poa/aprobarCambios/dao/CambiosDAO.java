package poa.aprobarCambios.dao;

//	VERSION		FECHA		AUTOR			DESCRIPCION
//		1.0		28/11/2019	JORGE CAMACHO	Se agregó el método actualizarTablasTipoMeta()

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import poa.common.vo.ParamPOA;
import poa.parametro.vo.FuenteFinanciacionVO;
import poa.aprobacionActividades.vo.ColegioPoaVO;
import poa.aprobacionActividades.vo.FiltroPlaneacionVO;
import poa.aprobacionActividades.vo.PlaneacionVO;
import poa.aprobarCambios.vo.FiltroCambiosVO;
import poa.aprobarCambios.vo.ParamsVO;
import poa.aprobarCambios.vo.CambioVO;

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;

/**
 * Objeto de acceso a datos para el modulo de actividadese 14/01/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class CambiosDAO extends Dao {

	/**
	 * Constructor
	 * 
	 * @param c
	 *            Objeto para la obtencinn de conexiones
	 */
	public CambiosDAO(Cursor c) {
		super(c);
		rb = ResourceBundle.getBundle("poa.aprobarCambios.bundle.cambios");
	}
	
public List getListaFuenteFinanciacion() throws Exception {
		
		List l = new ArrayList();
		//String itemLista[] = new String[2]; 
		/*
		Recursos Propios
		Transferencias DEE 2012
		Transferencias DEE 2013
		Otras Transferencias SED
		Recursos Alcaldna Local
		Recursos Privados
		Otros Recursos
		Sin Recursos
		*/
		l.add(new String[]{"Recursos Propios","recursos propios"});
		l.add(new String[]{"Transferencias DEE 2012","transferencias DEE 2012"});
		l.add(new String[]{"Transferencias DEE 2013","transferencias DEE 2013"});
		l.add(new String[]{"Otras Transferencias SED","otras transferencias sed"});
		l.add(new String[]{"Recursos Alcaldna Local","recursos alcaldia local"});
		l.add(new String[]{"Recursos Privados","recursos privados"});
		l.add(new String[]{"Otros Recursos","otros recursos"});
		l.add(new String[]{"Sin Recursos","sin recursos"});
	
		return l;
	}
	
public List getListaAccionMejoramiento() throws Exception {
		
		List l = new ArrayList();
		String itemLista[] = new String[2]; 
		/*
		Recursos Propios
		Transferencias DEE 2012
		Transferencias DEE 2013
		Otras Transferencias SED
		Recursos Alcaldna Local
		Recursos Privados
		Otros Recursos
		Sin Recursos
		*/
/*		USO EFICIENTE DEL AGUA 
		USO EFICIENTE DE LA ENERGIA
		GESTION INTEGRAL DE RESIDUOS
		MEJORAMIENTO DE LAS CONDICIONES AMBIENTALES INTERNAS
		CRnTERIOS AMBIENTALES PARA LAS COMPRAS Y GESTInN CONTRACTUAL 
		EXTENSInN DE BUENAS PRnCTICAS AMBIENTALES 
		OTRAS 
*/
		l.add(new String[]{"Uso Eficiente del Agua","uso eficiente del agua"});
		l.add(new String[]{"Uso Eficiente de la Energia","uso eficiente de la energia"});
		l.add(new String[]{"Gestion Integral de Residuos","gestion integral de residuos"});
		l.add(new String[]{"Mejoramiento de las Condiciones Ambientales Internas","mejoramiento de las condiciones ambientales internas"});
		l.add(new String[]{"Criterios Ambientales Para las Compras y Gestion Contractual","criterios ambientales para las compras y gestion contractual"});
		l.add(new String[]{"Extension de Buenas Practicas Ambientales","extension de buenas practicas ambientales"});
		l.add(new String[]{"Otras","otras"});
		/*int i = 0;
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("planeacion.listaAreaGestion"));
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				item.setPadre(rs.getLong(i++));
				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}*/
		return l;
	}
	
	public List getListaRubroGasto(String tipoRubro) throws Exception {
		
		List l = new ArrayList();

		if(tipoRubro == null){
			return l;
		}
		else if (tipoRubro.equalsIgnoreCase("Inversion")){

			l.add(new String[]{"Vitrina Pedagogica","vitrina pedagogica"});
			l.add(new String[]{"Escuela Ciudad Escuela","escuela ciudad escuela"});
			l.add(new String[]{"Medio Ambiente y Prevencion de Desastres","medio ambiente y prevencion de desastres"});
			l.add(new String[]{"Aprovechamiento del Tiempo Libre","aprovechamiento del tiempo libre"});
			l.add(new String[]{"Educacion Sexual","educacion sexual"});
			l.add(new String[]{"Compra Equipos Beneficio de los Estudiantes","compra equipos beneficio de los estudiantes"});
			l.add(new String[]{"Formacion de Valores","formacion de valores"});
			l.add(new String[]{"Formacion Tecnica y Para el Trabajo","formacion tecnica y para el trabajo"});
			l.add(new String[]{"Fomento de la Cultura","fomento de la cultura"});
			l.add(new String[]{"Otros Proyectos","otros proyectos"});
			l.add(new String[]{"Investigacinn y Estudios","Investigacinn y Estudios"});
			
		}else if(tipoRubro.equalsIgnoreCase("Funcionamiento")){
			l.add(new String[]{"Servicios Personales","servicios personales"});
			l.add(new String[]{"Gastos Generales","gastos generales"});
			l.add(new String[]{"Pasivos Exigibles Inversinn","Pasivos Exigibles Inversinn"});
		}
		
		
		return l;
	}

	/**
	 * calcula la lista de actividades con recursos
	 * 
	 * @param filtro
	 *            Filtro de busqueda de actividades
	 * @return lista de actividades con recursos
	 * @throws Exception
	 */
	public List getListaCambios(FiltroCambiosVO filtro) throws Exception {
		
		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement st = null;
		
		CambioVO item = null;
		List l = new ArrayList();
		
		int i = 0;
		
		try {
			
			cn = cursor.getConnection();
			// Calculo de la lista
			st = cn.prepareStatement(rb.getString("cambio.lista3"));
			
			i = 1;
			
			st.setInt(i++, filtro.getFilVigencia());
			st.setLong(i++, filtro.getFilNivel());
			st.setLong(i++, filtro.getFilVigencia());
			st.setLong(i++, filtro.getFilEntidad());
			
			rs = st.executeQuery();
			
			while (rs.next()) {
				
				i = 1;
				
				item = new CambioVO();
				item.setVigencia(rs.getInt(i++));
				item.setNivel(rs.getInt(i++));
				item.setEntidad(rs.getLong(i++));
				item.setFechaSol(rs.getString(i++));
				item.setFechaSol_(rs.getString(i++));
				item.setFechaSol_(item.getFechaSol_().replace(':', '|'));

				item.setEstado(rs.getInt(i++));
				if (item.getEstado() == ParamsVO.ESTADO_ENVIADO)
					item.setNombreEstado(ParamsVO.ESTADO_ENVIADO_);
				else if (item.getEstado() == ParamsVO.ESTADO_APROBADO)
					item.setNombreEstado(ParamsVO.ESTADO_APROBADO_);
				else if (item.getEstado() == ParamsVO.ESTADO_RECHAZADO)
					item.setNombreEstado(ParamsVO.ESTADO_RECHAZADO_);

				item.setFechaEstado(rs.getString(i++));
				item.setAsunto(rs.getString(i++));
				item.setSolicitud(rs.getString(i++));
				item.setUsuario(rs.getString(i++));
				item.setUsuarioEstado(rs.getString(i++));
				item.setObservacion(rs.getString(i++));
				l.add(item);
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		
		return l;
		
	}
	

	/**
	 * Obtiene una actividad con recursos
	 * 
	 * @param vigencia
	 *            anho de vigencia
	 * @param institucion
	 *            colegio
	 * @param codigo
	 *            actividad
	 * @param estadoPOA
	 *            estado de POA
	 * @return Actividad
	 * @throws Exception
	 */
	public CambioVO getCambio(int vigencia, long nivel, long entidad,
			String fechaSol, int estadoPOA, boolean habil) throws Exception {
		// si lo obtiene pero esta borrando la busqueda del filtro y ademas los
		// checkes estan descuadrados
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		CambioVO item = null;
		int i = 0;
		String fs = fechaSol.replace('-', ' ');
		fs = fs.replace('|', ':');
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("cambio.obtener"));
			i = 1;

			st.setInt(i++, vigencia);
			st.setLong(i++, nivel);
			st.setLong(i++, entidad);
			st.setString(i++, fs);
			rs = st.executeQuery();
			if (rs.next()) {
				i = 1;
				item = new CambioVO();
				item.setFormaEstado("1");
				item.setVigencia(rs.getInt(i++));
				item.setNivel(rs.getInt(i++));
				item.setEntidad(rs.getLong(i++));
				item.setFechaSol(rs.getString(i++));
				item.setEstado(rs.getInt(i++));

				if (item.getEstado() == ParamsVO.ESTADO_ENVIADO)
					item.setNombreEstado(ParamsVO.ESTADO_ENVIADO_);
				else if (item.getEstado() == ParamsVO.ESTADO_APROBADO)
					item.setNombreEstado(ParamsVO.ESTADO_APROBADO_);
				else if (item.getEstado() == ParamsVO.ESTADO_RECHAZADO)
					item.setNombreEstado(ParamsVO.ESTADO_RECHAZADO_);

				item.setFechaEstado(rs.getString(i++));
				item.setAsunto(rs.getString(i++));
				item.setSolicitud(rs.getString(i++));
				item.setUsuario(rs.getString(i++));
				item.setUsuarioEstado(rs.getString(i++));
				item.setObservacion(rs.getString(i++));
				if (estadoPOA != ParamPOA.ESTADO_POA_APROBADO_SED
						|| item.getEstado() == ParamsVO.ESTADO_APROBADO
						|| item.getEstado() == ParamsVO.ESTADO_RECHAZADO) {
					item.setDisabled("disabled");
					item.setDesHabilitado(true);
				}
			}
			// else {
			// // System.out.println("NOI ENCONTRO EL CAMBIO");
			// }
			rs.close();
			st.close();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return item;
	}

	/**
	 * Ingresa una actividad con recursos
	 * 
	 * @param item
	 *            Actividad
	 * @return Actividad
	 * @throws Exception
	 */
	public CambioVO ingresarCambio(CambioVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			// validar el estado de pOA
			st = cn.prepareStatement(rb.getString("cambio.validarEstado"));
			i = 1;
			st.setInt(i++, item.getVigencia());
			st.setLong(i++, item.getEntidad());
			rs = st.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) != ParamPOA.ESTADO_POA_APROBADO_SED) {
					throw new Exception(
							"El POA no ha sido aprobado y no se puede registrar cambios");
				}
			}
			rs.close();
			st.close();

			//
			st = cn.prepareStatement(rb.getString("cambio.ingresar"));
			i = 1;

			st.setInt(i++, item.getVigencia());
			st.setLong(i++, item.getNivel());
			st.setLong(i++, item.getEntidad());
			st.setLong(i++, ParamsVO.ESTADO_ENVIADO);
			st.setString(i++, item.getAsunto());
			st.setString(i++, item.getSolicitud());
			st.setString(i++, item.getUsuario());

			st.executeUpdate();
			cn.commit();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return item;
	}

	/**
	 * Actualiza una actividad con recursos
	 * 
	 * @param item
	 *            Actividad
	 * @return Actividad
	 * @throws Exception
	 */
	public CambioVO actualizarCambio(CambioVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);

			st = cn.prepareStatement(rb.getString("cambio.actualizar"));
			i = 1;

			st.setLong(i++, item.getEstado());
			st.setString(i++, item.getAsunto());
			st.setString(i++, item.getSolicitud());
			st.setString(i++, item.getUsuario());
			// w
			st.setInt(i++, item.getVigencia());
			st.setLong(i++, item.getNivel());
			st.setLong(i++, item.getEntidad());
			st.setString(i++, item.getFechaSol());
			st.executeUpdate();

			cn.commit();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return item;
	}

	/**
	 * Elimina una actividad con recursos
	 * 
	 * @param vigencia
	 *            anho de vigencia
	 * @param institucion
	 *            colegio
	 * @param codigo
	 *            actividad
	 * @throws Exception
	 */
	public void eliminarCambio(int vigencia, long nivel, long entidad,
			String fecha) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		String fs = fecha.replace('-', ' ');
		// System.out.println("CAMBIO1 POA: GET CAMBIO: FS: " + fs);
		fs = fs.replace('|', ':');
		// System.out.println("CAMBIO2 POA: GET CAMBIO: FS: " + fs);
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			// validar el estado de pOA
			st = cn.prepareStatement(rb.getString("cambio.obtenerEstado"));
			i = 1;
			st.setInt(i++, vigencia);
			st.setLong(i++, nivel);
			st.setLong(i++, entidad);
			st.setString(i++, fs);
			rs = st.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) == ParamsVO.ESTADO_APROBADO) {
					throw new Exception(
							"El cambio ya ha sido aprobado y no se puede eliminar");
				}
			}
			rs.close();
			st.close();

			// eliminar cambio
			st = cn.prepareStatement(rb.getString("cambio.eliminar"));
			i = 1;
			st.setInt(i++, vigencia);
			st.setLong(i++, nivel);
			st.setLong(i++, entidad);
			st.setString(i++, fs);
			st.executeUpdate();
			cn.commit();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	/**
	 * Calcula la lista de anos de vigencia
	 * 
	 * @return Lista de anhos de vigencia
	 * @throws Exception
	 */
	public List getListaVigenciaActual() throws Exception {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		ItemVO itemVO = null;
		List listaVigencia = new ArrayList();
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("cambio.listaVigencia"));
			rs = pst.executeQuery();
			if (rs.next()) {
				itemVO = new ItemVO(rs.getInt(1), String.valueOf(rs.getInt(1)));
				listaVigencia.add(itemVO);
				itemVO = new ItemVO(rs.getInt(2), String.valueOf(rs.getInt(2)));
				listaVigencia.add(itemVO);
				itemVO = new ItemVO(rs.getInt(3), String.valueOf(rs.getInt(3)));
				listaVigencia.add(itemVO);
			}
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return listaVigencia;
	}

	/**
	 * Obtiene los comentarios de los campos de las tablas de actividad con
	 * recursos
	 * 
	 * @param filtro
	 *            Filtro de busqueda de actividades
	 * @return Filtro de busqueda de actividades
	 * @throws Exception
	 */
	public FiltroCambiosVO getLabels(FiltroCambiosVO filtro) throws Exception {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		if (filtro == null)
			filtro = new FiltroCambiosVO();
		try {
			filtro.setFilVigencia((int) getVigenciaNumericoPOA());
			cn = cursor.getConnection();
			// calcular si es fecha habil para poder guardar actividades
			pst = cn.prepareStatement(rb.getString("cambio.rangoFecha"));
			pst.setInt(1, filtro.getFilVigencia());
			rs = pst.executeQuery();
			if (rs.next()) {
				filtro.setFilRangoFechas(rs.getString(1) + " - "
						+ rs.getString(2));
				filtro.setFilFechaHabil(rs.getInt(3) == 0 ? false : true);
			} else {
				filtro.setFilRangoFechas("No establecido");
				filtro.setFilFechaHabil(false);
			}
			rs.close();
			pst.close();
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return filtro;
	}

	/**
	 * Aprueba el POA por parte del colegio
	 * 
	 * @param filtro
	 *            Filtro de busqueda de actividades
	 * @throws Exception
	 */
	public boolean updateEstadoCambio(FiltroCambiosVO filtro) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		String fs = filtro.getFilFechaSol().replace('-', ' ');
		fs = fs.replace('|', ':');
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);

			st = cn.prepareStatement(rb.getString("cambio.updateEstado"));
			i = 1;

			st.setInt(i++, filtro.getFilEstadoCambio());
			st.setString(i++, filtro.getFilUsuario());
			st.setString(i++, filtro.getFilObservacion());
			// w
			st.setInt(i++, filtro.getFilVigencia());
			st.setLong(i++, filtro.getFilNivel());
			//st.setLong(i++, filtro.getFilEntidad());
			st.setString(i++, fs);
			st.executeUpdate();
			st.close();
			if (filtro.getFilEstadoCambio() == ParamsVO.ESTADO_APROBADO) {
				st = cn.prepareStatement(rb.getString("poa.updateVersion"));
				i = 1;
				// w
				st.setLong(i++, filtro.getFilEntidad());
				st.setInt(i++, filtro.getFilVigencia());
				st.executeUpdate();
			}
			cn.commit();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * Calcula la lista de localidades
	 * 
	 * @return Lista de localidades
	 * @throws Exception
	 */
	public List getListaLocalidadesTodas() throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("cambio.getLocalidades"));
			rs = st.executeQuery();
			while (rs.next()) {
				item = new ItemVO();
				item.setCodigo(rs.getLong(1));
				item.setNombre(rs.getString(2));
				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	/**
	 * Calcula la lista de localidades
	 * 
	 * @return Lista de localidades
	 * @throws Exception
	 */
	public List getListaLocalidades(long vigencia) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("cambio.getLocalidadesCambios"));
			st.setLong(1, vigencia);
			rs = st.executeQuery();
			while (rs.next()) {
				item = new ItemVO();
				item.setCodigo(rs.getLong(1));
				item.setNombre(rs.getString(2));
				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	/**
	 * Calcula la lista de localidades
	 * 
	 * @return Lista de localidades
	 * @throws Exception
	 */
	public List getListaColegios(long vigencia, long loc) throws Exception {
		
		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement st = null;
		
		List l = new ArrayList();
		
		ItemVO item = null;
		
		try {
			
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("cambio.listaColegio"));
			
			st.setLong(1, vigencia);
			st.setLong(2, loc);
			st.setLong(3, vigencia);
			
			rs = st.executeQuery();
			
			while (rs.next()) {
				item = new ItemVO();
				item.setCodigo(rs.getLong(1));
				item.setNombre(rs.getString(2));
				l.add(item);
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		
		return l;
		
	}

	
	public int getPonderadoArea(Connection cn, int vig, long inst, long codigo,
			long area) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			st = cn.prepareStatement(rb.getString("cambio.getPonderadoArea"));
			i = 1;
			st.setInt(i++, vig);
			st.setLong(i++, inst);
			st.setLong(i++, codigo);
			st.setLong(i++, area);
			rs = st.executeQuery();
			if (rs.next())
				return rs.getInt(1);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
					OperacionesGenerales.closeResultSet(rs);
					OperacionesGenerales.closeStatement(st);
				} catch (InternalErrorException inte) {
			}
		}
		return 0;
	}

	/**
	 * calcula la lista de actividades con recursos
	 * 
	 * @param filtro
	 *            Filtro de busqueda de actividades
	 * @return lista de actividades con recursos
	 * @throws Exception
	 */
	public float getTotalPonderado(FiltroCambiosVO filtro) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			// calculo del total ponderado
			st = cn.prepareStatement(rb.getString("cambio.totalPonderado"));
			st.setInt(1, filtro.getFilVigencia());
			st.setLong(2, filtro.getFilEntidad());
			rs = st.executeQuery();
			if (rs.next()) {
				return (rs.getFloat(1));
			}
			rs.close();
			st.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return 0;
	}

	/**
	 * Calcula la lista de estados de poa
	 * 
	 * @return Lista de localidades
	 * @throws Exception
	 */
	public String getNombreEstado(int estado) throws Exception {
		switch (estado) {
		case ParamPOA.ESTADO_POA_APROBADO_COLEGIO:
			return ParamPOA.ESTADO_POA_APROBADO_COLEGIO_;
		case ParamPOA.ESTADO_POA_APROBADO_SED:
			return ParamPOA.ESTADO_POA_APROBADO_SED_;
		case ParamPOA.ESTADO_POA_RECHAZADO_SED:
			return ParamPOA.ESTADO_POA_RECHAZADO_SED_;
		}
		return null;
	}

	// //////CAMBIO POA
	/**
	 * Calcula la lista de actividades con recursos
	 * 
	 * @param filtro
	 *            Filtro de bnsqueda
	 * @return Lista de actividades
	 * @throws Exception
	 */
	public List getListaActividades(FiltroCambiosVO filtro) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		PlaneacionVO item = null;
		int i = 0;
		int cons = 1;
		try {
			cn = cursor.getConnection();
			// calcular el nombre del colegio
			st = cn.prepareStatement(rb.getString("poa.getNombreColegio"));
			i = 1;
			st.setLong(i++, filtro.getFilEntidad());
			rs = st.executeQuery();
			if (rs.next()) {
				filtro.setFilNombreColegio(rs.getString(1));
			}
			rs.close();
			st.close();

			// calculo de la lista
			st = cn.prepareStatement(rb.getString("planeacion.lista2"));
			i = 1;
			st.setLong(i++, filtro.getFilEntidad());
			st.setInt(i++, filtro.getFilVigencia());
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new PlaneacionVO();
				item.setPlaConsecutivo(cons++);
				item.setPlaVigencia(rs.getInt(i++));
				item.setPlaInstitucion(rs.getLong(i++));
				item.setPlaCodigo(rs.getLong(i++));
				item.setPlaObjetivo(rs.getString(i++));
				item.setPlaActividad(rs.getString(i++));
				item.setPlaAreaGestionNombre(rs.getString(i++));
				item.setPlaLineaAccionNombre(rs.getString(i++));
				item.setPlaPonderador(rs.getFloat(i++));
				item.setPlaccodobjetivo(rs.getInt(i++));
				item.setPlaccodobjetivoText(rs.getString(i++));
				
				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	public PlaneacionVO getActividad(int vigencia, long institucion,
			long codigo, int estadoPOA) throws Exception {
		// si lo obtiene pero esta borrando la busqueda del filtro y ademas los
		// checkes estan descuadrados
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		PlaneacionVO item = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("planeacion.obtener2"));
			i = 1;
			st.setInt(i++, vigencia);
			st.setLong(i++, institucion);
			st.setLong(i++, codigo);
			rs = st.executeQuery();
			if (rs.next()) {
				i = 1;
				item = new PlaneacionVO();
				item.setFormaEstado("1");
				item.setPlaVigencia(rs.getInt(i++));
				item.setPlaInstitucion(rs.getLong(i++));
				item.setPlaCodigo(rs.getLong(i++));
				item.setPlaOrden(rs.getLong(i++));
				item.setPlaObjetivo(rs.getString(i++));
				item.setPlaActividad(rs.getString(i++));
				item.setPlaAreaGestion(rs.getInt(i++));
				item.setPlaPonderador(rs.getFloat(i++));
				item.setPlaLineaAccion(rs.getInt(i++));
				item.setPlaTipoMeta(rs.getInt(i++));
				item.setPlaMetaAnualCantidad(rs.getLong(i++));
				item.setPlaMetaAnualUnidad(rs.getInt(i++));
				item.setCUAL(rs.getString(i++));
				item.setPlaPresupuesto(rs.getLong(i++));
				item.setPlaResponsable(rs.getString(i++));
				item.setPlaFecha(rs.getString(i++));
				item.setPlaCronograma1(rs.getString(i++));
				item.setPlaCronograma2(rs.getString(i++));
				item.setPlaCronograma3(rs.getString(i++));
				item.setPlaCronograma4(rs.getString(i++));
				item.setPlaccodobjetivo(rs.getInt(i++));
				item.setPlaccodobjetivoText(rs.getString(i++));
				// item.setPlaDisabled("disabled");
				item.setPlaDesHabilitado(true);
				
				i = 23;
				item.setAccionMejoramiento(rs.getString(i++));
				item.setTipoGasto(rs.getString(i++));
				item.setRubroGasto(rs.getString(i++));
				item.setSubnivelGasto(rs.getString(i++));
				item.setFuenteFinanciacion(rs.getString(i++));				
				item.setFuenteFinanciacionOtros(rs.getString(i++));
				Long monto;
				try{
					monto = new Long (rs.getInt(i++));
				} catch (Exception e) {
					monto = new Long(0);
				}
				item.setMontoAnual(monto);
				
				Long presuP;
				try{
					presuP = new Long (rs.getInt(i++));
				} catch (Exception e) {
					presuP = new Long(0);
				}
				item.setPresupuestoParticipativo(presuP);
				
				String tipoA = rs.getString(i++);
				if(tipoA != null)
					item.setTipoActividad(tipoA.trim());
				
				
				item.setSEGFECHACUMPLIMT(rs.getString(i++));
				item.setSEGFECHAREALCUMPLIM(rs.getString(i++));
				
				
				
				int presuE = 0;
				try{
					presuE = rs.getInt(i++);
				} catch (Exception e) {
					presuE = 0;
				}
				item.setPRESUPUESTOEJECUTADO(presuE);
				//item.setCUAL(rs.getString(i++));
				 
			
			}
			rs.close();
			st.close();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return item;
	}

	/**
	 * Calcula la lista de actividades sin recursos
	 * 
	 * @param filtro
	 *            Filtro de busqueda de actividades
	 * @return Lista de actividades sin recursos
	 * @throws Exception
	 */
	public List getListaActividadesSin(FiltroCambiosVO filtro) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		PlaneacionVO item = null;
		int i = 0;
		int cons = 1;
		try {
			cn = cursor.getConnection();
			// calcular el nombre del colegio
			st = cn.prepareStatement(rb.getString("poa.getNombreColegio"));
			i = 1;
			st.setLong(i++, filtro.getFilEntidad());
			rs = st.executeQuery();
			if (rs.next()) {
				filtro.setFilNombreColegio(rs.getString(1));
			}
			rs.close();
			st.close();

			// calculo de la lista
			st = cn.prepareStatement(rb.getString("planeacionSin.lista2"));
			i = 1;
			st.setLong(i++, filtro.getFilEntidad());
			st.setInt(i++, filtro.getFilVigencia());

			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new PlaneacionVO();
				item.setPlaConsecutivo(cons++);
				item.setPlaVigencia(rs.getInt(i++));
				item.setPlaInstitucion(rs.getLong(i++));
				item.setPlaCodigo(rs.getLong(i++));
				item.setPlaObjetivo(rs.getString(i++));
				item.setPlaActividad(rs.getString(i++));
				item.setPlaAreaGestionNombre(rs.getString(i++));
				item.setPlaLineaAccionNombre(rs.getString(i++));
				item.setPlaccodobjetivo(rs.getInt(i++));
				item.setPlaccodobjetivoText(rs.getString(i++));

				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	/**
	 * Obtiene la actividad sin recursos
	 * 
	 * @param vigencia
	 *            Anho de vigencia
	 * @param institucion
	 *            codigo de la institucinn
	 * @param codigo
	 *            codigo de la actividad
	 * @param estadoPOA
	 *            codigo del estado del POA
	 * @return Actividad sin recursos solicitada
	 * @throws Exception
	 */
	public PlaneacionVO getActividadSin(int vigencia, long institucion,
			long codigo, int estadoPOA) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		PlaneacionVO item = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("planeacionSin.obtener2"));
			i = 1;
			st.setInt(i++, vigencia);
			st.setLong(i++, institucion);
			st.setLong(i++, codigo);
			rs = st.executeQuery();
			if (rs.next()) {
				i = 1;
				item = new PlaneacionVO();
				item.setFormaEstado("1");
				item.setFormaEstado("1");
				item.setPlaVigencia(rs.getInt(i++));
				item.setPlaInstitucion(rs.getLong(i++));
				item.setPlaCodigo(rs.getLong(i++));
				item.setPlaOrden(rs.getLong(i++));
				item.setPlaObjetivo(rs.getString(i++));
				item.setPlaActividad(rs.getString(i++));
				item.setPlaAreaGestion(rs.getInt(i++));
				item.setPlaLineaAccion(rs.getInt(i++));
				item.setPlaMetaAnualCantidad(rs.getLong(i++));
				item.setPlaMetaAnualUnidad(rs.getInt(i++));
				item.setPlaMetaAnualCual(rs.getString(i++));
				item.setPlaPresupuesto(rs.getLong(i++));
				item.setPlaccodobjetivo(rs.getInt(i++));
				item.setPlaccodobjetivoText(rs.getString(i++));
				// item.setPlaDisabled("disabled");
				item.setPlaDesHabilitado(true);
			}
			rs.close();
			st.close();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return item;
	}

	/**
	 * Calcula la lista de unidades de medida
	 * 
	 * @return Lista de unidades de medida
	 * @throws Exception
	 */
	public List getListaUnidadMedida() throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("planeacion.listaUnidadMedida"));
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				item.setPadre(rs.getLong(i++));
				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	/**
	 * Calcula la lista de Ã¡reas de gestinn
	 * 
	 * @return Lista de areas de gestinn
	 * @throws Exception
	 */
	public List getListaAreaGestion() throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("planeacion.listaAreaGestion"));
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				item.setPadre(rs.getLong(i++));
				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	/**
	 * Calcula la lista de tipo de meta
	 * 
	 * @return Lista de tipos de meta
	 * @throws Exception
	 */
	public List getListaTipoMeta() throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("planeacion.listaTipoMeta"));
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	/**
	 * Calcula la lista de lineas de accinn
	 * 
	 * @param areaGestion
	 *            codigo del area de gestinn
	 * @return Lista de lineas de accinn
	 * @throws Exception
	 */
	public List getListaLineaAccion(int areaGestion) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("planeacion.listaLineaAccion"));
			st.setInt(i++, areaGestion);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	public List getlistaObjetivos() throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("planeacion.getlistaObjetivos"));
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				// item.setPadre(rs.getLong(i++));
				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	/**
	 * Actualiza una actividad con recursos
	 * 
	 * @param item
	 *            Actividad
	 * @return Actividad
	 * @throws Exception
	 */
	public PlaneacionVO actualizarActividad(PlaneacionVO item) throws Exception {
		
		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement st = null;
		
		int i = 0;
		
		try {
			
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			
			// Validar que el ponderado no se pase de 100%
			st = cn.prepareStatement(rb.getString("planeacion.validarPonderadoActualizacion"));
			i = 1;
			st.setInt(i++, item.getPlaVigencia());
			st.setLong(i++, item.getPlaInstitucion());
			st.setLong(i++, item.getPlaCodigo());
			rs = st.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) + item.getPlaPonderador() > 100) {
					throw new Exception("El total ponderado supera el 100% permitido por colegio y vigencia");
				}
			}
			rs.close();
			st.close();
			
			// Actualizar
			st = cn.prepareStatement(rb.getString("planeacion.actualizar"));
			i = 1;
			
			st.setString(i++, item.getSEGFECHACUMPLIMT());
			st.setString(i++, item.getSEGFECHAREALCUMPLIM());
			st.setInt(i++, item.getPRESUPUESTOEJECUTADO());
			
			st.setLong(i++, item.getPlaOrden());
			st.setString(i++, item.getPlaObjetivo());
			st.setString(i++, item.getPlaActividad());
			st.setInt(i++, item.getPlaAreaGestion());
			st.setFloat(i++, item.getPlaPonderador());
			st.setInt(i++, item.getPlaLineaAccion());
			st.setInt(i++, item.getPlaTipoMeta());
			st.setLong(i++, item.getPlaMetaAnualCantidad());
			st.setInt(i++, item.getPlaMetaAnualUnidad());
			//st.setString(i++, item.getCUAL());
			st.setString( i++, item.getPlaMetaAnualCual());
			st.setLong(i++, item.getPlaPresupuesto());
			st.setString(i++, item.getPlaResponsable());
			st.setString(i++, item.getPlaFecha());
			st.setString(i++, item.getPlaCronograma1());
			st.setString(i++, item.getPlaCronograma2());
			st.setString(i++, item.getPlaCronograma3());
			st.setString(i++, item.getPlaCronograma4());

			if (item.getPlaccodobjetivo() > 0)
				st.setInt(i++, item.getPlaccodobjetivo());
			else
				st.setNull(i++, Types.VARCHAR);
			
			/*
			 * Cambios PIMA - PIGA
			 */
			if(item.getTipoActividad().equals("PIMA - PIGA")) {
				
				st.setString(i++, item.getAccionMejoramiento());
				st.setString(i++, item.getTipoGasto());
				st.setString(i++, item.getRubroGasto());
				st.setString(i++, item.getSubnivelGasto());
				st.setString(i++, item.getFuenteFinanciacion());
				
				if(item.getFuenteFinanciacion() != null && item.getFuenteFinanciacion().equalsIgnoreCase("otros recursos")) {
					st.setString(i++,item.getFuenteFinanciacionOtros());
				} else {
					st.setNull(i++,Types.VARCHAR);
				}
				
				st.setLong(i++,item.getMontoAnual().longValue());
				st.setLong(i++, item.getPresupuestoParticipativo().longValue());
				st.setString(i++, item.getTipoActividad());
				//st.setString(i++, item.getCUAL());
				
			} else { // POA 
				st.setString(i++, "");
				st.setString(i++, "");
				st.setString(i++, "");
				st.setString(i++, "");
				st.setString(i++, "");
				st.setString(i++, "");
				st.setLong(i++, 0);
				st.setLong(i++, 0);
				st.setString(i++, "POA");
			}
			
			st.setString(i++,item.getPlaIdUsuario());
			
			// Where
			st.setInt(i++, item.getPlaVigencia());
			st.setLong(i++, item.getPlaInstitucion());
			st.setLong(i++, item.getPlaCodigo());
			
			st.executeUpdate();
			cn.commit();
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		
		return item;
		
	}
	

	/**
	 * Actualiza una actividad sin recursos
	 * 
	 * @param item
	 *            Actividad
	 * @return Actividad
	 * @throws Exception
	 */
	public PlaneacionVO actualizarActividadSin(PlaneacionVO item)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			st = cn.prepareStatement(rb.getString("planeacionSin.actualizar"));
			i = 1;
			st.setLong(i++, item.getPlaOrden());
			st.setString(i++, item.getPlaObjetivo());
			st.setString(i++, item.getPlaActividad());
			st.setInt(i++, item.getPlaAreaGestion());
			st.setInt(i++, item.getPlaLineaAccion());
			st.setLong(i++, item.getPlaMetaAnualCantidad());
			st.setInt(i++, item.getPlaMetaAnualUnidad());
			st.setString(i++, item.getPlaMetaAnualCual());
			if (item.getPlaPresupuesto() != 0)
				st.setLong(i++, item.getPlaPresupuesto());
			else
				st.setNull(i++, Types.VARCHAR);

			if (item.getPlaccodobjetivo() > 0)
				st.setInt(i++, item.getPlaccodobjetivo());
			else
				st.setNull(i++, Types.VARCHAR);

			// System.out.println("POA:ACT PROBLEMA: OBJ:"+item.getPlaccodobjetivo());
			// w
			st.setInt(i++, item.getPlaVigencia());
			st.setLong(i++, item.getPlaInstitucion());
			st.setLong(i++, item.getPlaCodigo());
			st.executeUpdate();
			cn.commit();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return item;
	}
	
	
	/**
	 * Cuando el Tipo de Meta pasa de DEMANDA a SUMATORIA o CONSTANTE, se deben eliminar los datos para los campos demanda1, demanda2, demanda3 y demanda4
	 * 
	 */
	public void actualizarTablasTipoMeta(PlaneacionVO item) throws Exception {

		int i = 0;
		
		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement st = null;

		try {
			
			// 1 - SUMATORIA
			// 2 - DEMANDA
			// 3 - CONSTANTE
			if (item.getPlaTipoMeta() != 2) {
				
				cn = cursor.getConnection();
				cn.setAutoCommit(false);
				
				st = cn.prepareStatement(rb.getString("cambio.actualizarTablasTipoMeta"));

				i = 1;
				st.setInt(i++, item.getPlaVigencia());		
				st.setLong(i++, item.getPlaInstitucion());
				st.setLong(i++, item.getPlaCodigo());
								
				rs = st.executeQuery();

				rs.close();
				st.close();

				cn.commit();
				
			}

		} catch(SQLException sqle) {

			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());

		} catch(Exception sqle) {

			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());

		} finally {

			try {

				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);

			} catch(InternalErrorException inte) {}

		}

	}

}
