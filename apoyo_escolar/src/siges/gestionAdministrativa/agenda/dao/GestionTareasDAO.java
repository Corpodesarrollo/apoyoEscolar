package siges.gestionAdministrativa.agenda.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.gestionAdministrativa.agenda.vo.CircularVO;
import siges.gestionAdministrativa.agenda.vo.FiltroCircularesVO;
import siges.gestionAdministrativa.agenda.vo.FiltroPermisosVO;
import siges.gestionAdministrativa.agenda.vo.PermisosVO;
import siges.gestionAdministrativa.agenda.vo.TareasVO;
import siges.gestionAdministrativa.boletin.vo.PlantillaBoletionVO;
import siges.login.beans.Login;
import siges.util.Logger;

public class GestionTareasDAO extends Dao {

	public GestionTareasDAO(Cursor cursor) {
		super(cursor);
		rb=ResourceBundle.getBundle("siges.gestionAdministrativa.agenda.bundle.agenda");
	}
	
	/**
	 * Crea un nuevo registro en la tabla AGE_ACTIVIDAD
	 * @param act
	 * @return
	 */
	public boolean saveTarea(HttpServletRequest request,
			HttpSession session, Login usuVO, TareasVO act){
		Connection cn = null;
		PreparedStatement st = null;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("tareas.Guardar"));
			
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Calendar cal = Calendar.getInstance();
			String date = dateFormat.format(cal.getTime()) + "";
			
			int posicion = 1;
			st.setLong(posicion++, getJerCodigo(usuVO, request));
			st.setLong(posicion++, Long.parseLong(act.getAsignaturas()));
			st.setString(posicion++, act.getNombre());
			st.setString(posicion++, act.getDescripcion());
			st.setString(posicion++, date);
			st.setString(posicion++, act.getFechaEntrega());
			st.setString(posicion++, act.getObservaciones());
			st.setLong(posicion++, Long.parseLong(act.getUsuario()));
			st.setLong(posicion++, act.getEstado());

			st.executeUpdate();
			
			Logger.print(usuVO.getUsuario(), "Agrego nueva Tarea", 0, 1, this.toString());
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}
	
	/**
	 *  Construye el archivo y lo almacena en el servidor
	 * @param reportFile Archivo del reporte
	 * @param parameterscopy Parametros del reporte
	 * @param rutaExcel Ruta de almacenamiento del reporte
	 * @param archivo Nombre del reporte
	 * @return Ruta de disco del reporte
	 * @throws Exception
	 */
	public String getArchivoPDF(File reportFile, Map parameterscopy, String rutaExcel,String archivo)throws Exception {
		Connection con = null;
		String archivoCompleto = null;
		try {
			con = cursor.getConnection();
			byte[] bytes=JasperRunManager.runReportToPdf(reportFile.getPath(),parameterscopy,con);
			String xlsFileName = archivo;
			String xlsFilesSource = rutaExcel;
			archivoCompleto = xlsFilesSource + xlsFileName;
			File f = new File(xlsFilesSource);
			if (!f.exists())
				FileUtils.forceMkdir(f);
			FileOutputStream fileOut= new FileOutputStream(archivoCompleto);
			IOUtils.write(bytes,fileOut);
			fileOut.close();
		} catch (JRException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (java.lang.OutOfMemoryError e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return archivoCompleto;
	}
	
	/**
	 * Crea una nueva circular
	 * @param request
	 * @param session
	 * @param usuVO2
	 * @param tareas
	 * @return
	 */
	public boolean saveCircular(HttpServletRequest request,
			HttpSession session, Login usuVO2, CircularVO c) {
		Connection cn = null;
		PreparedStatement st = null;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("circular.Guardar"));
			
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Calendar cal = Calendar.getInstance();
			String date = dateFormat.format(cal.getTime()) + "";
			
			int posicion = 1;
			st.setString(posicion++, c.getDato());
			st.setString(posicion++, c.getAsunto());
			st.setString(posicion++, c.getDescripcion());
			st.setString(posicion++, date);
			st.setString(posicion++, c.getResponsable());
			st.setString(posicion++, "tempData");
			st.setLong(posicion++, c.getUsuario());
			st.setString(posicion++, c.getFechaRegistro());
			st.setLong(posicion++, c.getEstado());
			st.setLong(posicion++, c.getNivel());

			st.executeUpdate();
			Logger.print(usuVO2.getUsuario(), "Agrego nueva Circular", 0, 1, this.toString());
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}
	
	/**
	 * Crea una nuevo permiso
	 * @param request
	 * @param session
	 * @param usuVO2
	 * @param tareas
	 * @return
	 */
	public boolean savePermiso(HttpServletRequest request,
			HttpSession session, Login usuVO2, PermisosVO c) {
		Connection cn = null;
		PreparedStatement st = null;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("permiso.Guardar"));
			
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Calendar cal = Calendar.getInstance();
			String date = dateFormat.format(cal.getTime()) + "";
			
			int posicion = 1;
			st.setLong(posicion++, 859743); 
			st.setLong(posicion++, 7496);
			st.setString(posicion++, c.getFecha());
			st.setInt(posicion++, c.getHoraInicio());
			st.setInt(posicion++, c.getMinIni());
			st.setInt(posicion++, c.getHoraFin());
			st.setInt(posicion++, c.getMinIni());
			st.setInt(posicion++, c.getMotivo());
			st.setString(posicion++, c.getObservaciones());
			st.setInt(posicion++, c.getEstado());
			st.setString(posicion++, c.getObservacionesResponsable());
			st.setLong(posicion++, c.getUsuario());
			st.setString(posicion++, date);

			st.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}
	
	/**
	 * Edita un registro en la tabla AGE_ACTIVIDAD
	 * @param act
	 * @return
	 */
	public boolean updateTarea(HttpServletRequest request,
			HttpSession session, Login usuVO, TareasVO act) {
		Connection cn = null;
		PreparedStatement st = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("tareas.Editar"));
			st.setLong(posicion++, getJerCodigo(usuVO, request));
			st.setLong(posicion++, Long.parseLong(act.getAsignaturas()));
			st.setString(posicion++, act.getNombre().trim());
			st.setString(posicion++, act.getDescripcion().trim());
			st.setString(posicion++, act.getFechaPublicacion().trim());
			st.setString(posicion++, act.getFechaEntrega().trim());
			st.setString(posicion++, act.getObservaciones().trim());
			st.setLong(posicion++, Long.parseLong(act.getUsuario()));
			st.setLong(posicion++, act.getEstado());
			
			st.setLong(posicion++, act.getCodigo());
			
			st.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}
	
	/**
	 * Edita un registro en la tabla AGE_ACTIVIDAD
	 * @param act
	 * @return
	 */
	public boolean updateCircular(HttpServletRequest request,
			HttpSession session, Login usuVO, CircularVO act) {
		Connection cn = null;
		PreparedStatement st = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("circular.Editar"));
			st.setString(posicion++, act.getDato().trim());
			st.setString(posicion++, act.getAsunto().trim());
			st.setString(posicion++, act.getDescripcion().trim());
			st.setString(posicion++, act.getFechaPublicacion().trim());
			st.setString(posicion++, act.getResponsable().trim());
			st.setString(posicion++, act.getArchivo().trim());
			st.setLong(posicion++, act.getUsuario());
			st.setString(posicion++, act.getFechaRegistro().trim());
			st.setInt(posicion++, act.getEstado());
			st.setInt(posicion++, act.getNivel());
			
			st.setLong(posicion++, act.getCodigo());
			
			st.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}
	
	/**
	 * Edita un registro en la tabla AGE_ACTIVIDAD
	 * @param act
	 * @return
	 */
	public boolean updatePermiso(HttpServletRequest request,
			HttpSession session, Login usuVO, PermisosVO act) {
		Connection cn = null;
		PreparedStatement st = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("permiso.Editar"));
			st.setLong(posicion++, act.getEstudiante());
			st.setLong(posicion++, act.getCodgrupo());
			st.setString(posicion++, act.getFecha().trim());
			st.setInt(posicion++, act.getHoraInicio() );
			st.setInt(posicion++, act.getHoraFin());
			st.setInt(posicion++, act.getMinIni());
			st.setInt(posicion++, act.getMinFin());
			st.setInt(posicion++, act.getMotivo());
			st.setString(posicion++, act.getObservaciones());
			st.setInt(posicion++, act.getEstado());
			st.setString(posicion++, act.getObservacionesResponsable().trim());
			st.setLong(posicion++, act.getUsuario());
			st.setString(posicion++, act.getFechaRegistro().trim());
			
			
			st.setLong(posicion++, act.getCodigo());
			
			st.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}
	
	/**
	 * Trae un registro en la tabla AGE_ACTIVIDAD dado su pk
	 * @param act
	 * @return
	 */
	public TareasVO getTarea(long id) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		TareasVO act = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("tareas.Consultar"));
			st.setLong(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				act = new TareasVO(); 
				act.setCodigo(id);
				act.setGrupo(rs.getLong(posicion++)+"");
				act.setAsignaturas(rs.getLong(posicion++)+"");
				act.setNombre(rs.getString(posicion++).trim());
				act.setDescripcion(rs.getString(posicion++).trim());
				act.setFechaPublicacion(rs.getString(posicion++).trim());
				act.setFechaEntrega(rs.getString(posicion++).trim());
				act.setObservaciones(rs.getString(posicion++).trim());
				act.setUsuario(rs.getString(posicion++).trim());
				act.setEstado(rs.getInt(posicion++));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return act;
	}
	
	/**
	 * Trae un registro en la tabla AGE_CIRCULAR dado su pk
	 * @param act
	 * @return
	 */
	public CircularVO getCircular(long id, HttpSession session) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		CircularVO act = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("circular.Consultar"));
			st.setLong(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				act = new CircularVO(); 
				act.setCodigo(id);
				act.setDato(rs.getString(posicion++).trim());
				act.setAsunto(rs.getString(posicion++).trim());
				act.setDescripcion(rs.getString(posicion++).trim());
				act.setFechaPublicacion(rs.getString(posicion++).trim());
				act.setResponsable(rs.getString(posicion++).trim());
				act.setArchivo(rs.getString(posicion++).trim());
				act.setUsuario(rs.getLong(posicion++));
				act.setFechaRegistro(rs.getString(posicion++).trim());
				act.setEstado(rs.getInt(posicion++));
				act.setNivel(rs.getInt(posicion++));
				session.setAttribute("idCircular", id+""); //Para cargar el archivo de la circular
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return act;
	}  
	
	/**
	 * Trae un registro en la tabla PERMISO dado su pk
	 * @param act
	 * @return
	 */
	public PermisosVO getPermiso(long id, Login u) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		PermisosVO act = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("permiso.Consultar"));
			st.setLong(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				act = new PermisosVO(); 
				act.setCodigo(id);
				act.setEstudiante(rs.getLong(posicion++));
				act.setCodgrupo(rs.getLong(posicion++));
				act.setFecha(rs.getString(posicion++).trim());
				act.setHoraInicio(rs.getInt(posicion++));
				act.setMinIni(rs.getInt(posicion++));
				act.setHoraFin(rs.getInt(posicion++));
				
				act.setMinFin(rs.getInt(posicion++));
				act.setMotivo(rs.getInt(posicion++));
				act.setObservaciones(rs.getString(posicion++).trim());
				act.setEstado(rs.getInt(posicion++));
				act.setObservacionesResponsable(rs.getString(posicion++).trim());
				act.setUsuario(rs.getLong(posicion++));
				act.setFechaRegistro(rs.getString(posicion++).trim());
				act.setNivel(Integer.parseInt(u.getPerfil()));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return act;
	}
	
	/**
	 * Trae las tareas que cumplen el criterio de busqueda
	 * @return Listado de tareas 
	 * @throws Exception
	 */
	public List getTarea( TareasVO filtroTareas, HttpServletRequest request, Login usuVO) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		TareasVO act = null;
		List lista = new ArrayList();
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("tareas.getlista"));
			st.setLong(1, getJerCodigo(usuVO, request));
			st.setLong(2, filtroTareas.getEstado());
			st.setLong(3, filtroTareas.getEstado());
			st.setString(4, filtroTareas.getAsignaturas());
			st.setString(5, filtroTareas.getAsignaturas());
			rs = st.executeQuery();
			while (rs.next()) {
				act = new TareasVO(); 
				act.setCodigo(rs.getLong(posicion++));
				act.setGrupo(rs.getString(posicion++).trim());
				act.setAsignaturas(rs.getString(posicion++).trim());
				act.setNombre(rs.getString(posicion++).trim());
				act.setDescripcion(rs.getString(posicion++).trim());
				act.setFechaPublicacion(rs.getString(posicion++).trim());
				act.setFechaEntrega(rs.getString(posicion++).trim());
				act.setObservaciones(rs.getString(posicion++).trim());
				act.setUsuario(rs.getString(posicion++).trim());
				act.setEstado(rs.getInt(posicion++));
				lista.add(act);
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
		return lista;
	}
	
	/**
	 * Trae las tareas que cumplen el criterio de busqueda
	 * @return Listado de tareas 
	 * @throws Exception
	 */
	public List getCircular( FiltroCircularesVO filtro, HttpServletRequest request, Login usuVO) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		CircularVO act = null;
		List lista = new ArrayList();
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("circular.getlista"));
			st.setString(1, filtro.getNivel());
			st.setString(2, filtro.getNivel());
			st.setInt(3, filtro.getEstado());
			st.setInt(4, filtro.getEstado());
			
			if(filtro.getMes() == null){
				filtro.setMes((Calendar.getInstance().get(Calendar.MONTH)+1)+"");
			}
			
			
			String fechaIni = "01/"+filtro.getMes()+"/"+usuVO.getVigencia_inst();
			String fechaFin = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)+"/"+filtro.getMes()+"/"+usuVO.getVigencia_inst();
			
			st.setString(5, fechaIni);
			st.setString(6, fechaFin);
			
			st.setString(7, usuVO.getUsuarioId());
			
			rs = st.executeQuery();
			while (rs.next()) {
				int posicion = 1;
				act = new CircularVO(); 
				act.setCodigo(rs.getInt(posicion++));
				act.setCodjerar(rs.getInt(posicion++));
				act.setAsunto(rs.getString(posicion++).trim());
				act.setDescripcion(rs.getString(posicion++).trim());
				act.setFechaPublicacion(rs.getString(posicion++).trim());
				act.setResponsable(rs.getString(posicion++).trim());
				act.setArchivo(rs.getString(posicion++).trim());
				act.setUsuario(rs.getLong(posicion++));
				act.setFechaRegistro(rs.getString(posicion++));
				act.setEstado(rs.getInt(posicion++));
				act.setNivel(rs.getInt(posicion++));
				lista.add(act);
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
		return lista;
	}
	
	/**
	 * Trae los permisos  que cumplen el criterio de busqueda
	 * @return Listado de permisos 
	 * @throws Exception
	 */
	public List getPermisos( FiltroPermisosVO filtro, HttpServletRequest request, Login usuVO) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		PermisosVO act = null;
		List lista = new ArrayList();
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("permiso.getlista"));
			st.setInt(1, filtro.getPermiso());
			st.setString(2, filtro.getFechaInicio());
			st.setString(3, filtro.getFechaFin());
			st.setInt(4, filtro.getEstado());

			rs = st.executeQuery();
			while (rs.next()) {
				int posicion = 1;
				act = new PermisosVO(); 
				act.setCodigo(rs.getInt(posicion++));
				act.setEstudiante(rs.getLong(posicion++));
				act.setCodgrupo(rs.getLong(posicion++));
				act.setFecha(rs.getString(posicion++).trim());
				act.setHoraInicio(rs.getInt(posicion++));
				act.setHoraFin(rs.getInt(posicion++));
				act.setMinIni(rs.getInt(posicion++));
				act.setMinFin(rs.getInt(posicion++));
				act.setMotivo(rs.getInt(posicion++));
				
				act.setMotivoString(getMotivoAusencia(act.getMotivo()));
				
				act.setObservaciones(rs.getString(posicion++));
				act.setEstado(rs.getInt(posicion++));
				act.setEstadoString(getEstadoNombre(act.getEstado()).trim());
				act.setObservacionesResponsable(rs.getString(posicion++));
				act.setUsuario(rs.getLong(posicion++));
				act.setFechaRegistro(rs.getString(posicion++));
				lista.add(act);
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
		return lista;
	}
	
	/**
	 * Retorna el nombre de un estado del permiso
	 * @param estado
	 * @return
	 */
	private String getEstadoNombre(int estado) {
		if(estado == 0)
			return "Pendiente";
		else if(estado == 1)
			return "Aprobado";
		else
			return "Rechazado";
	}

	/**
	 * Retorna el nombre de un motivo de ausencia dado su identificador
	 * Dominio: G_constantes tipo 15
	 * @param id
	 * @return
	 */
	private String getMotivoAusencia(int id){
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String nombre = "";
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("permiso.getMotivo"));
			st.setInt(1, id);

			rs = st.executeQuery();
			if (rs.next()) {
				nombre = rs.getString(1);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return nombre;
	}

	
	
	

	/**
	 * Trae todas las metodologias de un instituto 
	 * @param inst
	 * @return
	 * @throws Exception
	 */
	public List getListaMetodologia(long inst) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("common.listaMetodologia"));
			st.setLong(i++, inst);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++).trim());
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
	 * Lista de permisos 
	 * Dominio G_CONSTANTE 
	 * tipo 15
	 * @return
	 */
	public List getListaPermisos(){
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("permiso.getTipoPermisos"));
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++).trim());
				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception sqle) {
			sqle.printStackTrace();
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
	 * Trae todos los grados de una institucinn dado el codigo de la metodologi
	 * @param inst
	 * @param met
	 * @return
	 * @throws Exception
	 */
	public List getListaGradoBase(long inst, int met) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("common.listaGradoBase"));
			st.setLong(i++, inst);
			st.setInt(i++, met);
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
	 * Trae las asignaturas de un colegio dado el codigo del instituto, la metodologna y el ano
	 * @param plantillaBoletionVO PlantillaBoletinVO
	 * @return Lista de todas las asignaturas
	 * @throws Exception
	 */
	public List getAsignaturas(PlantillaBoletionVO plantillaBoletionVO) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("tareas.getlista"));
			
			st.setLong(posicion++,plantillaBoletionVO.getPlabolinst() );
			st.setLong(posicion++,plantillaBoletionVO.getPlabolmetodo() );
			st.setLong(posicion++, Calendar.getInstance().get(Calendar.YEAR));
			rs = st.executeQuery();
			
			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				list.add(itemVO);
			} 
			
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return list;
	}
	
	/**
	 * @function:  
	 * @param codInst
	 * @param tipoDoc
	 * @param numDOc
	 * @return
	 * @throws Exception
	 */
	public long getJerCodigo(Login usuVO, HttpServletRequest request) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		long jerCodigo = 0;
		int posicion = 1;
		
		String params[] = request.getParameterValues("ajax");
		
		long codInst = Long.parseLong( usuVO.getInstId());
	    long codSede = Long.parseLong( usuVO.getSedeId());
	    long codJord = Long.parseLong( usuVO.getJornadaId() );
		long codMetodo = Long.parseLong(params[0]);
		long codGrado = Long.parseLong(params[1]);
		long codGrupo = Long.parseLong(params[2]);
		
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("tareas.getJerCodigo"));
			
			st.setLong(posicion++,codGrupo );
			st.setLong(posicion++,codInst );
			st.setLong(posicion++,codSede );
			st.setLong(posicion++,codJord );
			st.setLong(posicion++,codMetodo );
			st.setLong(posicion++,codGrado );
			
			rs = st.executeQuery();
			
			if (rs.next()) {
				posicion = 1; 
				jerCodigo = rs.getLong(posicion++);
			} 
			
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return jerCodigo;
	}

	/**
	 * elimina el permiso del id suministrado
	 * @param id
	 * @return
	 */
	public boolean deletePermiso(long id) {
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("permiso.Eliminar"));
			st.setLong(1,id );
			if( st.executeUpdate() > 0)
				return true;
			
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}catch(Exception sqle){
			sqle.printStackTrace();
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return false;
	}

}
