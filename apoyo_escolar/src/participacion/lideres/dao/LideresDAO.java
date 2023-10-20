/**
 * 
 */
package participacion.lideres.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import participacion.acta.vo.ActaVO;
import participacion.lideres.vo.LideresVO;

import participacion.acta.vo.FiltroActaVO;
import participacion.lideres.vo.FiltroLideresVO;

import participacion.common.exception.IntegrityException;
import participacion.common.exception.ParticipacionException;
import participacion.common.vo.ParamParticipacion;

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.login.beans.Login;

/**
 * 30/04/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class LideresDAO extends Dao {

	/**
	 * Constructor
	 * 
	 * @param c
	 *            Objeto para la obtencinn de conexiones
	 */
	public LideresDAO(Cursor c) {
		super(c);
		rb = ResourceBundle.getBundle("participacion.lideres.bundle.lideres");
	}

	/**
	 * Calcula la lista de niveles de instancia
	 * 
	 * @return Lista de areas de gestinn
	 * @throws Exception
	 */
	public List getListaNivel(int nivelUsuario) throws ParticipacionException {
		List l = new ArrayList();
		switch (nivelUsuario) {
		case ParamParticipacion.LOGIN_NIVEL_DEPARTAMENTO:
			l.add(new ItemVO(ParamParticipacion.NIVEL_INSTANCIA_DISTRITAL,
					ParamParticipacion.NIVEL_INSTANCIA_DISTRITAL_));
			l.add(new ItemVO(ParamParticipacion.NIVEL_INSTANCIA_CENTRAL,
					ParamParticipacion.NIVEL_INSTANCIA_CENTRAL_));
			l.add(new ItemVO(ParamParticipacion.NIVEL_INSTANCIA_LOCAL,
					ParamParticipacion.NIVEL_INSTANCIA_LOCAL_));
			l.add(new ItemVO(ParamParticipacion.NIVEL_INSTANCIA_COLEGIO,
					ParamParticipacion.NIVEL_INSTANCIA_COLEGIO_));
			break;
		case ParamParticipacion.LOGIN_NIVEL_LOCALIDAD:
			l.add(new ItemVO(ParamParticipacion.NIVEL_INSTANCIA_LOCAL,
					ParamParticipacion.NIVEL_INSTANCIA_LOCAL_));
			l.add(new ItemVO(ParamParticipacion.NIVEL_INSTANCIA_COLEGIO,
					ParamParticipacion.NIVEL_INSTANCIA_COLEGIO_));
			break;
		case ParamParticipacion.LOGIN_NIVEL_COLEGIO:
		case ParamParticipacion.LOGIN_NIVEL_SEDE_JORNADA:
			l.add(new ItemVO(ParamParticipacion.NIVEL_INSTANCIA_COLEGIO,
					ParamParticipacion.NIVEL_INSTANCIA_COLEGIO_));
			break;
		}
		return l;
	}

	/**
	 * Calcula la lista de instancias de un nivel
	 * 
	 * @return Lista de areas de gestinn
	 * @throws Exception
	 */
	public List getListaInstancia(int nivel) throws ParticipacionException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("common.listaInstancia"));
			st.setInt(i++, nivel);
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
			throw new ParticipacionException("Error de datos: "
					+ sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error interno");
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

	public LideresVO actualizarLideres(LideresVO item)
			throws ParticipacionException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("lideres.actualizar"));
			i = 1;
			st.setInt(i++, item.getLidTipoDocumento());
			st.setString(i++, item.getLidNumeroDocumento());
			st.setString(i++, item.getLidApellido1());
			st.setString(i++, item.getLidApellido2());
			st.setString(i++, item.getLidNombre1());
			st.setString(i++, item.getLidNombre2());
			st.setString(i++, item.getLidCorreoElectronico());
			st.setString(i++, item.getLidTelefono());
			st.setString(i++, item.getLidCelular());
			st.setInt(i++, item.getLidEdad());
			st.setInt(i++, item.getLidGenero());
			st.setInt(i++, item.getLidEtnia());
			st.setInt(i++, item.getLidRol());
			st.setInt(i++, item.getLidSuplente());
			if (item.getLidLocalidad() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidLocalidad());
			if (item.getLidTipoColegio() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidTipoColegio());
			if (item.getLidColegio() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setLong(i++, item.getLidColegio());
			if (item.getLidSede() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidSede());
			if (item.getLidJornada() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidJornada());
			if (item.getLidGrado() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidGrado());
			if (item.getLidGrupo() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidGrupo());
			st.setString(i++, item.getLidEntidad());
			if (item.getLidLocalidadResidencia() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidLocalidadResidencia());
			if (item.getLidSectorEconomico() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidSectorEconomico());
			if (item.getLidDiscapacidad() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidDiscapacidad());
			st.setInt(i++, item.getLidDesplazado());
			st.setInt(i++, item.getLidAmenazado());
			if (item.getLidLgtb() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidLgtb());
			st.setInt(i++, item.getLidOcupacion());
			if (item.getLidMet() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidMet());
			// w
			st.setInt(i++, item.getLidInstancia());
			st.setInt(i++, item.getLidRango());
			st.setLong(i++, item.getLidParticipante());
			st.executeUpdate();
			// System.out.println("***actualizn registro en LID_PARTICIPANTE***");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			if (sqle.getErrorCode() == 1) {
				throw new IntegrityException("Cndigo duplicado");
			}
			throw new ParticipacionException("Error de datos: "
					+ sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error interno");
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

	public LideresVO actualizarLideresOficial(LideresVO item)
			throws ParticipacionException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			// System.out.println(" actualizarLideresOficial ");
			cn = cursor.getConnection();
			// calcular datos del estudiante
			st = cn.prepareStatement(rb.getString("getDatosEstudiante"));
			i = 1;
			st.setString(i++, item.getLidNumeroDocumento());
			rs = st.executeQuery();
			if (rs.next()) {
				item.setLidTipoDocumento(rs.getInt(1));
				item.setLidApellido1(rs.getString(2));
				item.setLidApellido2(rs.getString(3));
				item.setLidNombre1(rs.getString(4));
				item.setLidNombre2(rs.getString(5));
			}
			rs.close();
			st.close();

			st = cn.prepareStatement(rb.getString("lideres.actualizar"));
			i = 1;
			st.setInt(i++, item.getLidTipoDocumento());
			st.setString(i++, item.getLidNumeroDocumento());
			st.setString(i++, item.getLidApellido1());
			st.setString(i++, item.getLidApellido2());
			st.setString(i++, item.getLidNombre1());
			st.setString(i++, item.getLidNombre2());
			st.setString(i++, item.getLidCorreoElectronico());
			st.setString(i++, item.getLidTelefono());
			st.setString(i++, item.getLidCelular());
			st.setInt(i++, item.getLidEdad());
			st.setInt(i++, item.getLidGenero());
			st.setInt(i++, item.getLidEtnia());
			st.setInt(i++, item.getLidRol());
			st.setInt(i++, item.getLidSuplente());
			if (item.getLidLocalidad() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidLocalidad());
			if (item.getLidTipoColegio() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidTipoColegio());
			if (item.getLidColegio() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setLong(i++, item.getLidColegio());
			if (item.getLidSede() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidSede());
			if (item.getLidJornada() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidJornada());
			if (item.getLidGrado() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidGrado());
			if (item.getLidGrupo() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidGrupo());
			st.setString(i++, item.getLidEntidad());
			if (item.getLidLocalidadResidencia() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidLocalidadResidencia());
			if (item.getLidSectorEconomico() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidSectorEconomico());
			if (item.getLidDiscapacidad() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidDiscapacidad());
			st.setInt(i++, item.getLidDesplazado());
			st.setInt(i++, item.getLidAmenazado());
			if (item.getLidLgtb() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidLgtb());
			st.setInt(i++, item.getLidOcupacion());
			if (item.getLidMet() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidMet());
			// w
			st.setInt(i++, item.getLidInstancia());
			st.setInt(i++, item.getLidRango());
			st.setLong(i++, item.getLidParticipante());
			st.executeUpdate();
			// System.out.println("***actualizn registro en LID_PARTICIPANTE***");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			if (sqle.getErrorCode() == 1) {
				throw new IntegrityException("Cndigo duplicado");
			}
			throw new ParticipacionException("Error de datos: "
					+ sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error interno");
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

	public LideresVO actualizarLideresOficialPersonal(LideresVO item)
			throws ParticipacionException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			// System.out.println(" actualizarLideresOficialPersonal ");
			cn = cursor.getConnection();
			// calcular datos del estudiante
			st = cn.prepareStatement(rb.getString("getDatosPersonal"));
			i = 1;
			st.setString(i++, item.getLidNumeroDocumento());
			rs = st.executeQuery();
			if (rs.next()) {
				item.setLidTipoDocumento(rs.getInt(1));
				item.setLidApellido1(rs.getString(2));
				item.setLidApellido2(rs.getString(3));
				item.setLidNombre1(rs.getString(4));
				item.setLidNombre2(rs.getString(5));
			}
			rs.close();
			st.close();

			st = cn.prepareStatement(rb.getString("lideres.actualizar"));
			i = 1;
			st.setInt(i++, item.getLidTipoDocumento());
			st.setString(i++, item.getLidNumeroDocumento());
			st.setString(i++, item.getLidApellido1());
			st.setString(i++, item.getLidApellido2());
			st.setString(i++, item.getLidNombre1());
			st.setString(i++, item.getLidNombre2());
			st.setString(i++, item.getLidCorreoElectronico());
			st.setString(i++, item.getLidTelefono());
			st.setString(i++, item.getLidCelular());
			st.setInt(i++, item.getLidEdad());
			st.setInt(i++, item.getLidGenero());
			st.setInt(i++, item.getLidEtnia());
			st.setInt(i++, item.getLidRol());
			st.setInt(i++, item.getLidSuplente());
			if (item.getLidLocalidad() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidLocalidad());
			if (item.getLidTipoColegio() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidTipoColegio());
			if (item.getLidColegio() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setLong(i++, item.getLidColegio());
			if (item.getLidSede() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidSede());
			if (item.getLidJornada() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidJornada());
			if (item.getLidGrado() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidGrado());
			if (item.getLidGrupo() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidGrupo());
			st.setString(i++, item.getLidEntidad());
			if (item.getLidLocalidadResidencia() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidLocalidadResidencia());
			if (item.getLidSectorEconomico() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidSectorEconomico());
			if (item.getLidDiscapacidad() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidDiscapacidad());
			st.setInt(i++, item.getLidDesplazado());
			st.setInt(i++, item.getLidAmenazado());
			if (item.getLidLgtb() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidLgtb());
			st.setInt(i++, item.getLidOcupacion());
			if (item.getLidMet() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidMet());
			// w
			st.setInt(i++, item.getLidInstancia());
			st.setInt(i++, item.getLidRango());
			st.setLong(i++, item.getLidParticipante());
			st.executeUpdate();
			// System.out.println("***actualizn registro en LID_PARTICIPANTE***");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			if (sqle.getErrorCode() == 1) {
				throw new IntegrityException("Cndigo duplicado");
			}
			throw new ParticipacionException("Error de datos: "
					+ sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error interno");
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

	public void eliminarLideres(FiltroLideresVO item)
			throws ParticipacionException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("lideres.eliminar"));
			i = 1;
			// w
			st.setInt(i++, item.getFilInstancia());
			st.setInt(i++, item.getFilRango());
			st.setLong(i++, item.getFilParticipante());
			st.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			if (sqle.getErrorCode() == 1) {
				throw new IntegrityException("Cndigo duplicado");
			}
			throw new ParticipacionException("Error de datos: "
					+ sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error interno");
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	public LideresVO getLideres(FiltroLideresVO filtro)
			throws ParticipacionException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		LideresVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			// System.out.println("**getLideres*** instancia: "+filtro.getFilInstancia()+" rango: "+filtro.getFilRango()+" particiapante: "+filtro.getFilParticipante());
			st = cn.prepareStatement(rb.getString("lideres.obtener"));
			st.setInt(i++, filtro.getFilInstancia());
			st.setInt(i++, filtro.getFilRango());
			st.setLong(i++, filtro.getFilParticipante());
			rs = st.executeQuery();
			if (rs.next()) {
				i = 1;
				item = new LideresVO();
				item.setFormaEstado("1");
				item.setFormaDisabled("disabled");
				// item.setLidNivel(rs.getInt(i++));
				item.setLidInstancia(rs.getInt(i++));
				item.setLidRango(rs.getInt(i++));
				item.setLidParticipante(rs.getLong(i++));
				item.setLidTipoDocumento(rs.getInt(i++));
				item.setLidNumeroDocumento(rs.getString(i++));
				item.setLidApellido1(rs.getString(i++));
				item.setLidApellido2(rs.getString(i++));
				item.setLidNombre1(rs.getString(i++));
				item.setLidNombre2(rs.getString(i++));
				item.setLidCorreoElectronico(rs.getString(i++));
				item.setLidTelefono(rs.getString(i++));
				item.setLidCelular(rs.getString(i++));
				item.setLidEdad(rs.getInt(i++));
				item.setLidGenero(rs.getInt(i++));
				item.setLidEtnia(rs.getInt(i++));
				item.setLidRol(rs.getInt(i++));
				item.setLidSuplente(rs.getInt(i++));
				item.setLidLocalidad(rs.getInt(i++));
				item.setLidTipoColegio(rs.getInt(i++));
				item.setLidColegio(rs.getLong(i++));
				item.setLidSede(rs.getInt(i++));
				item.setLidJornada(rs.getInt(i++));
				item.setLidGrado(rs.getInt(i++));
				item.setLidGrupo(rs.getInt(i++));
				item.setLidEntidad(rs.getString(i++));
				item.setLidLocalidadResidencia(rs.getInt(i++));
				item.setLidSectorEconomico(rs.getInt(i++));
				item.setLidDiscapacidad(rs.getInt(i++));
				item.setLidDesplazado(rs.getInt(i++));
				item.setLidAmenazado(rs.getInt(i++));
				item.setLidLgtb(rs.getInt(i++));
				item.setLidOcupacion(rs.getInt(i++));
				item.setLidFechaIngreso(rs.getString(i++));
				item.setLidFechaActualizacion(rs.getString(i++));
				item.setLidMet(rs.getInt(i++));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error de datos: "
					+ sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error interno");
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

	/*
	 * public ActaVO getActaDescarga(FiltroActaVO filtro) throws
	 * ParticipacionException{ Connection cn=null; PreparedStatement st=null;
	 * ResultSet rs=null; ActaVO item=null; int i=1; try{
	 * cn=cursor.getConnection();
	 * st=cn.prepareStatement(rb.getString("acta.obtenerDescarga"));
	 * st.setInt(i++,filtro.getFilInstancia());
	 * st.setInt(i++,filtro.getFilRango()); st.setInt(i++,filtro.getFilActa());
	 * rs=st.executeQuery(); if(rs.next()){ i=1; item=new ActaVO();
	 * item.setActNombre(rs.getString(i++)); Blob blob = rs.getBlob(i++); if
	 * (blob != null) { long len = blob.length();
	 * item.setActArchivo(blob.getBytes((long) 1, (int) len)); }
	 * item.setActTipoMime(rs.getString(i++)); } }catch(SQLException sqle){
	 * sqle.printStackTrace(); throw new
	 * ParticipacionException("Error de datos: "+sqle.getMessage());
	 * }catch(Exception sqle){ sqle.printStackTrace(); throw new
	 * ParticipacionException("Error interno"); }finally{ try{
	 * OperacionesGenerales.closeResultSet(rs);
	 * OperacionesGenerales.closeStatement(st);
	 * OperacionesGenerales.closeConnection(cn); }catch(InternalErrorException
	 * inte){} } return item; }
	 */

	public List getListaLideres(FiltroLideresVO filtro, int codigo)
			throws ParticipacionException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		LideresVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			if (filtro.getFilRol() != -99) {
				// System.out.println("**con rol***");
				st = cn.prepareStatement(rb.getString("lideres.lista"));
				st.setInt(i++, filtro.getFilInstancia());
				st.setInt(i++, filtro.getFilRango());
				st.setInt(i++, filtro.getFilRol());
				st.setLong(i++, filtro.getFilColegio());
				st.setLong(i++, filtro.getFilColegio());
				rs = st.executeQuery();
				while (rs.next()) {
					// System.out.println("***Hay registros en la lista***");
					i = 1;
					item = new LideresVO();
					item.setLidRol(rs.getInt(i++));
					item.setLidNombreRol(rs.getString(i++));
					item.setLidApellidos(rs.getString(i++));
					item.setLidNombres(rs.getString(i++));
					item.setLidTelefono(rs.getString(i++));
					item.setLidCorreoElectronico(rs.getString(i++));
					item.setLidTipoDocumento(rs.getInt(i++));
					item.setLidNumeroDocumento(rs.getString(i++));
					item.setLidParticipante(rs.getLong(i++));
					l.add(item);
				}
			} else {
				// System.out.println("**sin rol***");
				st = cn.prepareStatement(rb.getString("lideres.listaNoRol"));
				st.setInt(i++, filtro.getFilInstancia());
				st.setInt(i++, filtro.getFilRango());
				st.setInt(i++, codigo);
				st.setLong(i++, filtro.getFilColegio());
				rs = st.executeQuery();
				while (rs.next()) {
					// System.out.println("***Hay registros en la lista***");
					i = 1;
					item = new LideresVO();
					item.setLidRol(rs.getInt(i++));
					item.setLidNombreRol(rs.getString(i++));
					item.setLidApellidos(rs.getString(i++));
					item.setLidNombres(rs.getString(i++));
					item.setLidTelefono(rs.getString(i++));
					item.setLidCorreoElectronico(rs.getString(i++));
					item.setLidTipoDocumento(rs.getInt(i++));
					item.setLidNumeroDocumento(rs.getString(i++));
					item.setLidParticipante(rs.getLong(i++));
					l.add(item);
				}
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error de datos: "
					+ sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error interno");
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
	
	
	
	public LideresVO getPadreDeFamilia(FiltroLideresVO filtro, LideresVO lideres) throws ParticipacionException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		LideresVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			// System.out.println("**con rol***");
			st = cn.prepareStatement(rb.getString("lideres.madreDeFamilia"));
			st.setInt(i++, lideres.getLidTipoDocumento());
			st.setString(i++, lideres.getLidNumeroDocumento());
			st.setLong(i++, filtro.getFilColegio());
			rs = st.executeQuery();
			i = 1;
			if (rs.next()) {
				// System.out.println("***Hay registros en la lista***");
				item = new LideresVO();
				item.setLidNombre1(rs.getString(i++));
				item.setLidNombre2(rs.getString(i++));
				item.setLidApellido1(rs.getString(i++));
				item.setLidApellido2(rs.getString(i++));
				item.setLidCorreoElectronico(rs.getString(i++));
				item.setLidTelefono(rs.getString(i++));
				item.setLidCelular(rs.getString(i++));
				String prueba = rs.getString(i);
				item.setLidGenero(rs.getInt(i++));
			}else{
				// System.out.println("**con rol***");
				st = cn.prepareStatement(rb.getString("lideres.padreDeFamilia"));
				st.setInt(i++, lideres.getLidTipoDocumento());
				st.setString(i++, lideres.getLidNumeroDocumento());
				st.setLong(i++, filtro.getFilColegio());
				rs = st.executeQuery();
				i = 1;
				if (rs.next()) {
					// System.out.println("***Hay registros en la lista***");
					item = new LideresVO();
					item.setLidNombre1(rs.getString(i++));
					item.setLidNombre2(rs.getString(i++));
					item.setLidApellido1(rs.getString(i++));
					item.setLidApellido2(rs.getString(i++));
					item.setLidCorreoElectronico(rs.getString(i++));
					item.setLidCelular(rs.getString(i++));
					item.setLidGenero(rs.getInt(i++));
				}else{
					// System.out.println("**con rol***");
					st = cn.prepareStatement(rb.getString("lideres.acudiente"));
					st.setInt(i++, lideres.getLidTipoDocumento());
					st.setString(i++, lideres.getLidNumeroDocumento());
					st.setLong(i++, filtro.getFilColegio());
					rs = st.executeQuery();
					i = 1;
					if (rs.next()) {
						// System.out.println("***Hay registros en la lista***");
						item = new LideresVO();
						item.setLidNombre1(rs.getString(i++));
						item.setLidNombre2(rs.getString(i++));
						item.setLidApellido1(rs.getString(i++));
						item.setLidApellido2(rs.getString(i++));
						item.setLidCorreoElectronico(rs.getString(i++));
						item.setLidCelular(rs.getString(i++));
						item.setLidGenero(rs.getInt(i++));
					}
				}
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error interno");
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
	
	
	

	public synchronized LideresVO ingresarLideres(LideresVO item)
			throws ParticipacionException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			// calcular el codigo
			st = cn.prepareStatement(rb.getString("lideres.codigo"));
			st.setInt(i++, item.getLidInstancia());
			st.setInt(i++, item.getLidRango());
			rs = st.executeQuery();
			if (rs.next()) {
				item.setLidParticipante(rs.getLong(1));
			}
			rs.close();
			st.close();
			st = cn.prepareStatement(rb.getString("lideres.ingresar"));
			i = 1;
			st.setInt(i++, item.getLidInstancia());
			st.setInt(i++, item.getLidRango());
			st.setLong(i++, item.getLidParticipante());
			st.setInt(i++, item.getLidTipoDocumento());
			st.setString(i++, item.getLidNumeroDocumento());
			st.setString(i++, item.getLidApellido1());
			st.setString(i++, item.getLidApellido2());
			st.setString(i++, item.getLidNombre1());
			st.setString(i++, item.getLidNombre2());
			st.setString(i++, item.getLidCorreoElectronico());
			st.setString(i++, item.getLidTelefono());
			st.setString(i++, item.getLidCelular());
			st.setInt(i++, item.getLidEdad());
			st.setInt(i++, item.getLidGenero());
			st.setInt(i++, item.getLidEtnia());
			st.setInt(i++, item.getLidRol());
			st.setInt(i++, item.getLidSuplente());
			if (item.getLidLocalidad() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidLocalidad());
			if (item.getLidTipoColegio() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidTipoColegio());
			if (item.getLidColegio() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setLong(i++, item.getLidColegio());
			if (item.getLidSede() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidSede());
			if (item.getLidJornada() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidJornada());
			if (item.getLidGrado() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidGrado());
			if (item.getLidGrupo() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidGrupo());
			st.setString(i++, item.getLidEntidad());
			if (item.getLidLocalidadResidencia() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidLocalidadResidencia());
			if (item.getLidSectorEconomico() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidSectorEconomico());
			if (item.getLidDiscapacidad() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidDiscapacidad());
			st.setInt(i++, item.getLidDesplazado());
			st.setInt(i++, item.getLidAmenazado());
			if (item.getLidLgtb() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidLgtb());
			st.setInt(i++, item.getLidOcupacion());
			if (item.getLidMet() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidMet());
			st.executeUpdate();
			// System.out.println("***insertn registro en LID_PARTICIPANTE***");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			if (sqle.getErrorCode() == 1) {
				throw new IntegrityException("Cndigo duplicado");
			}
			throw new ParticipacionException("Error de datos: "
					+ sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error interno");
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

	public synchronized LideresVO ingresarLideresOficial(LideresVO item)
			throws ParticipacionException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			// System.out.println("***ingresarLideresOficial***");
			// calcular datos del estudiante
			st = cn.prepareStatement(rb.getString("getDatosEstudiante"));
			i = 1;
			st.setString(i++, item.getLidNumeroDocumento());
			rs = st.executeQuery();
			if (rs.next()) {
				item.setLidTipoDocumento(rs.getInt(1));
				item.setLidApellido1(rs.getString(2));
				item.setLidApellido2(rs.getString(3));
				item.setLidNombre1(rs.getString(4));
				item.setLidNombre2(rs.getString(5));
			}
			rs.close();
			st.close();
			// calcular el codigo
			st = cn.prepareStatement(rb.getString("lideres.codigo"));
			i = 1;
			st.setInt(i++, item.getLidInstancia());
			st.setInt(i++, item.getLidRango());
			rs = st.executeQuery();
			if (rs.next()) {
				item.setLidParticipante(rs.getLong(1));
			}
			rs.close();
			st.close();
			st = cn.prepareStatement(rb.getString("lideres.ingresar"));
			i = 1;
			st.setInt(i++, item.getLidInstancia());
			st.setInt(i++, item.getLidRango());
			st.setLong(i++, item.getLidParticipante());
			st.setInt(i++, item.getLidTipoDocumento());
			st.setString(i++, item.getLidNumeroDocumento());
			st.setString(i++, item.getLidApellido1());
			st.setString(i++, item.getLidApellido2());
			st.setString(i++, item.getLidNombre1());
			st.setString(i++, item.getLidNombre2());
			st.setString(i++, item.getLidCorreoElectronico());
			st.setString(i++, item.getLidTelefono());
			st.setString(i++, item.getLidCelular());
			st.setInt(i++, item.getLidEdad());
			st.setInt(i++, item.getLidGenero());
			st.setInt(i++, item.getLidEtnia());
			st.setInt(i++, item.getLidRol());
			st.setInt(i++, item.getLidSuplente());
			if (item.getLidLocalidad() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidLocalidad());
			if (item.getLidTipoColegio() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidTipoColegio());
			if (item.getLidColegio() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setLong(i++, item.getLidColegio());
			if (item.getLidSede() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidSede());
			if (item.getLidJornada() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidJornada());
			if (item.getLidGrado() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidGrado());
			if (item.getLidGrupo() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidGrupo());
			st.setString(i++, item.getLidEntidad());
			if (item.getLidLocalidadResidencia() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidLocalidadResidencia());
			if (item.getLidSectorEconomico() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidSectorEconomico());
			if (item.getLidDiscapacidad() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidDiscapacidad());
			st.setInt(i++, item.getLidDesplazado());
			st.setInt(i++, item.getLidAmenazado());
			if (item.getLidLgtb() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidLgtb());
			st.setInt(i++, item.getLidOcupacion());
			if (item.getLidMet() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidMet());
			st.executeUpdate();
			// System.out.println("***insertn registro en LID_PARTICIPANTE***");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			if (sqle.getErrorCode() == 1) {
				throw new IntegrityException("Cndigo duplicado");
			}
			throw new ParticipacionException("Error de datos: "
					+ sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error interno");
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

	public synchronized LideresVO ingresarLideresOficialPersonal(LideresVO item)
			throws ParticipacionException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			// System.out.println("***ingresarLideresOficialPersonal***");
			// calcular datos del estudiante
			st = cn.prepareStatement(rb.getString("getDatosPersonal"));
			i = 1;
			st.setString(i++, item.getLidNumeroDocumento());
			rs = st.executeQuery();
			if (rs.next()) {
				item.setLidTipoDocumento(rs.getInt(1));
				item.setLidApellido1(rs.getString(2));
				item.setLidApellido2(rs.getString(3));
				item.setLidNombre1(rs.getString(4));
				item.setLidNombre2(rs.getString(5));
			}
			rs.close();
			st.close();
			// calcular el codigo
			st = cn.prepareStatement(rb.getString("lideres.codigo"));
			i = 1;
			st.setInt(i++, item.getLidInstancia());
			st.setInt(i++, item.getLidRango());
			rs = st.executeQuery();
			if (rs.next()) {
				item.setLidParticipante(rs.getLong(1));
			}
			rs.close();
			st.close();
			st = cn.prepareStatement(rb.getString("lideres.ingresar"));
			i = 1;
			st.setInt(i++, item.getLidInstancia());
			st.setInt(i++, item.getLidRango());
			st.setLong(i++, item.getLidParticipante());
			st.setInt(i++, item.getLidTipoDocumento());
			st.setString(i++, item.getLidNumeroDocumento());
			st.setString(i++, item.getLidApellido1());
			st.setString(i++, item.getLidApellido2());
			st.setString(i++, item.getLidNombre1());
			st.setString(i++, item.getLidNombre2());
			st.setString(i++, item.getLidCorreoElectronico());
			st.setString(i++, item.getLidTelefono());
			st.setString(i++, item.getLidCelular());
			st.setInt(i++, item.getLidEdad());
			st.setInt(i++, item.getLidGenero());
			st.setInt(i++, item.getLidEtnia());
			st.setInt(i++, item.getLidRol());
			st.setInt(i++, item.getLidSuplente());
			if (item.getLidLocalidad() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidLocalidad());
			if (item.getLidTipoColegio() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidTipoColegio());
			if (item.getLidColegio() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setLong(i++, item.getLidColegio());
			if (item.getLidSede() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidSede());
			if (item.getLidJornada() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidJornada());
			if (item.getLidGrado() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidGrado());
			if (item.getLidGrupo() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidGrupo());
			st.setString(i++, item.getLidEntidad());
			if (item.getLidLocalidadResidencia() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidLocalidadResidencia());
			if (item.getLidSectorEconomico() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidSectorEconomico());
			if (item.getLidDiscapacidad() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidDiscapacidad());
			st.setInt(i++, item.getLidDesplazado());
			st.setInt(i++, item.getLidAmenazado());
			if (item.getLidLgtb() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidLgtb());
			st.setInt(i++, item.getLidOcupacion());
			if (item.getLidMet() == -99)
				st.setNull(i++, java.sql.Types.NUMERIC);
			else
				st.setInt(i++, item.getLidMet());
			st.executeUpdate();
			// System.out.println("***insertn registro en LID_PARTICIPANTE***");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			if (sqle.getErrorCode() == 1) {
				throw new IntegrityException("Cndigo duplicado");
			}
			throw new ParticipacionException("Error de datos: "
					+ sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error interno");
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
	 * Calcula la lista de instancias de un nivel
	 * 
	 * @return Lista de areas de gestinn
	 * @throws Exception
	 */
	public List getListaRango(int instancia) throws ParticipacionException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("common.listaRango"));
			st.setInt(i++, instancia);
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
			throw new ParticipacionException("Error de datos: "
					+ sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error interno");
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
	 * Calcula la lista de instancias de un nivel
	 * 
	 * @return Lista de areas de gestinn
	 * @throws Exception
	 */
	public List getListaLocalidad() throws ParticipacionException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("common.listaLocalidad"));
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
			throw new ParticipacionException("Error de datos: "
					+ sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error interno");
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
	 * Calcula la lista de instancias de un nivel
	 * 
	 * @return Lista de areas de gestinn
	 * @throws Exception
	 */
	public List getListaColegio(int localidad) throws ParticipacionException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("common.listaColegio"));
			st.setInt(i++, localidad);
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
			throw new ParticipacionException("Error de datos: "
					+ sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error interno");
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
	 * Calcula la lista de instancias de un nivel
	 * 
	 * @return Lista de areas de gestinn
	 * @throws Exception
	 */
	public List getListaSede(long colegio) throws ParticipacionException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("common.listaSede"));
			st.setLong(i++, colegio);
			rs = st.executeQuery();
			while (rs.next()) {
				// System.out.println("hay sedes");
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error de datos: "
					+ sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error interno");
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

	public List getListaMetodologia(long colegio) throws ParticipacionException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("common.listaMetodologia"));
			st.setLong(i++, colegio);
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
			throw new ParticipacionException("Error de datos: "
					+ sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error interno");
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
	 * Calcula la lista de instancias de un nivel
	 * 
	 * @return Lista de areas de gestinn
	 * @throws Exception
	 */
	public List getListaJornada() throws ParticipacionException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("common.listaJornada"));
			// st.setLong(i++,colegio);
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
			throw new ParticipacionException("Error de datos: "
					+ sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error interno");
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

	public List getListaJornada(long colegio, int sede)
			throws ParticipacionException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("common.listaJornada_"));
			st.setLong(i++, colegio);
			st.setInt(i++, sede);
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
			throw new ParticipacionException("Error de datos: "
					+ sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error interno");
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
	 * Calcula la lista de instancias de un nivel
	 * 
	 * @return Lista de areas de gestinn
	 * @throws Exception
	 */
	public List getListaGrado() throws ParticipacionException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("common.listaGrado"));
			// st.setLong(i++,colegio);
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
			throw new ParticipacionException("Error de datos: "
					+ sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error interno");
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

	public List getListaGrupo() throws ParticipacionException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("common.listaGrupo"));
			// st.setLong(i++,colegio);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(String.valueOf(rs.getInt(i++)));
				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error de datos: "
					+ sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error interno");
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

	public List getListaGrado(long colegio, int sede, int jornada,
			int metodologia) throws ParticipacionException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("common.listaGrado_"));
			st.setLong(i++, colegio);
			st.setInt(i++, sede);
			st.setInt(i++, jornada);
			st.setInt(i++, metodologia);
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
			throw new ParticipacionException("Error de datos: "
					+ sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error interno");
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

	public List getListaGrupo(long colegio, int sede, int jornada,
			int metodologia, int grado) throws ParticipacionException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("common.listaGrupo_"));
			st.setLong(i++, colegio);
			st.setInt(i++, sede);
			st.setInt(i++, jornada);
			st.setInt(i++, grado);
			st.setInt(i++, metodologia);
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
			throw new ParticipacionException("Error de datos: "
					+ sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error interno");
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

	public List getListaEstudiante(long colegio, int sede, int jornada,
			int metodologia, int grado, int grupo, int rol, int instancia)
			throws ParticipacionException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("common.listaEstudiante_"));
			i = 1;
			st.setLong(i++, colegio);
			st.setInt(i++, sede);
			st.setInt(i++, jornada);
			st.setInt(i++, metodologia);
			st.setInt(i++, grado);
			st.setInt(i++, grupo);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo2(rs.getString(i++));
				item.setNombre(rs.getString(i++));
				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error de datos: "
					+ sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error interno");
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

	public List getListaPersonal(long colegio, int sede, int jornada, int rol,
			int instancia) throws ParticipacionException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			// System.out.println("getListaPersonal: col " + colegio + " sede "
			// + sede + " jornada " + jornada);
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("common.listaPersonal_"));
			i = 1;
			st.setLong(i++, colegio);
			st.setInt(i++, sede);
			st.setInt(i++, jornada);
			st.setLong(i++, colegio);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo2(rs.getString(i++));
				item.setNombre(rs.getString(i++));
				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error de datos: "
					+ sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error interno");
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
	 * Calcula la lista de instancias de un nivel
	 * 
	 * @return Lista de areas de gestinn
	 * @throws Exception
	 */
	public List getListaRol(int codigoInstancia) throws ParticipacionException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("common.listaRol"));
			st.setInt(i++, codigoInstancia);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				item.setCodigo2(String.valueOf(rs.getInt(i++)));
				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error de datos: "
					+ sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error interno");
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
	 * Calcula la lista de instancias de un nivel
	 * 
	 * @return Lista de areas de gestinn
	 * @throws Exception
	 */
	public List getListaTipoDocumento() throws ParticipacionException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("common.listaTipoDocumento"));
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
			throw new ParticipacionException("Error de datos: "
					+ sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error interno");
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
	 * Calcula la lista de instancias de un nivel
	 * 
	 * @return Lista de areas de gestinn
	 * @throws Exception
	 */
	public List getListaEtnia() throws ParticipacionException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("common.listaEtnia"));
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
			throw new ParticipacionException("Error de datos: "
					+ sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error interno");
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
	 * Calcula la lista de instancias de un nivel
	 * 
	 * @return Lista de areas de gestinn
	 * @throws Exception
	 */
	public List getListaSectorEconomico() throws ParticipacionException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("common.listaSectorEconomico"));
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
			throw new ParticipacionException("Error de datos: "
					+ sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error interno");
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
	 * Calcula la lista de instancias de un nivel
	 * 
	 * @return Lista de areas de gestinn
	 * @throws Exception
	 */
	public List getListaDiscapacidad() throws ParticipacionException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("common.listaDiscapacidad"));
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
			throw new ParticipacionException("Error de datos: "
					+ sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error interno");
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
	 * Calcula la lista de instancias de un nivel
	 * 
	 * @return Lista de areas de gestinn
	 * @throws Exception
	 */
	public List getListaLgtb() throws ParticipacionException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("common.listaLgtb"));
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
			throw new ParticipacionException("Error de datos: "
					+ sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error interno");
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
	 * Calcula la lista de instancias de un nivel
	 * 
	 * @return Lista de areas de gestinn
	 * @throws Exception
	 */
	public List getListaOcupacion() throws ParticipacionException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("common.listaOcupacion"));
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
			throw new ParticipacionException("Error de datos: "
					+ sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error interno");
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

	public int getSectorInstitucion(String institucion)
			throws ParticipacionException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int codigo = 0;
		int i = 1;
		try {
			if (institucion != null && !institucion.equals("")) {
				cn = cursor.getConnection();
				st = cn.prepareStatement(rb.getString("getSectorInstitucion"));
				st.setLong(i++, Long.parseLong(institucion));
				rs = st.executeQuery();
				if (rs.next()) {
					codigo = rs.getInt(1);
					// System.out.println("***codigo del sector..." + codigo);
				}
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error de datos: "
					+ sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error interno");
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return codigo;
	}

	public int getTipoRol(int instancia, int rol) throws ParticipacionException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int codigo = 0;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getTipoRol"));
			st.setInt(i++, instancia);
			st.setInt(i++, rol);
			rs = st.executeQuery();
			if (rs.next()) {
				codigo = rs.getInt(1);
				// System.out.println("***codigo del tipo de rol..." + codigo);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error de datos: "
					+ sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new ParticipacionException("Error interno");
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return codigo;
	}

	public Collection[] getFiltrosLider(Login login) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection[] list = new Collection[5];
		try {
			long inst = Long.parseLong(login.getInstId());
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("filtroSedeInstitucion"));
			posicion = 1;
			ps.setLong(posicion++, inst);
			rs = ps.executeQuery();
			list[0] = getCollection(rs);
			rs.close();
			ps.close();
			ps = cn.prepareStatement(rb
					.getString("filtroSedeJornadaInstitucion"));
			posicion = 1;
			ps.setLong(posicion++, inst);
			rs = ps.executeQuery();
			list[1] = getCollection(rs);
			rs.close();
			ps.close();
			ps = cn.prepareStatement(rb
					.getString("listaMetodologiaInstitucion"));
			posicion = 1;
			ps.setLong(posicion++, inst);
			rs = ps.executeQuery();
			list[2] = getCollection(rs);
			rs.close();
			ps.close();
			ps = cn.prepareStatement(rb
					.getString("filtroSedeJornadaGradoInstitucion2"));
			posicion = 1;
			ps.setLong(posicion++, inst);
			rs = ps.executeQuery();
			list[3] = getCollection(rs);
			rs.close();
			ps.close();
			ps = cn.prepareStatement(rb
					.getString("filtroSedeJornadaGradoGrupoInstitucion"));
			posicion = 1;
			ps.setLong(posicion++, inst);
			rs = ps.executeQuery();
			list[4] = getCollection(rs);
			rs.close();
			ps.close();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener filtros de lideres. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return list;
	}

	public Collection[] getFiltrosLiderLocalidad(Login login) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection[] list = new Collection[5];
		try {
			int localidad = Integer.parseInt(login.getMunId());
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("filtroSedeInstitucion2"));
			posicion = 1;
			ps.setLong(posicion++, localidad);
			rs = ps.executeQuery();
			list[0] = getCollection(rs);
			rs.close();
			ps.close();
			ps = cn.prepareStatement(rb
					.getString("filtroSedeJornadaInstitucion2"));
			posicion = 1;
			ps.setLong(posicion++, localidad);
			rs = ps.executeQuery();
			list[1] = getCollection(rs);
			rs.close();
			ps.close();
			ps = cn.prepareStatement(rb
					.getString("listaMetodologiaInstitucion2"));
			posicion = 1;
			ps.setLong(posicion++, localidad);
			rs = ps.executeQuery();
			list[2] = getCollection(rs);
			rs.close();
			ps.close();
			ps = cn.prepareStatement(rb
					.getString("filtroSedeJornadaGradoInstitucion22"));
			posicion = 1;
			ps.setLong(posicion++, localidad);
			rs = ps.executeQuery();
			list[3] = getCollection(rs);
			rs.close();
			ps.close();
			ps = cn.prepareStatement(rb
					.getString("filtroSedeJornadaGradoGrupoInstitucion2"));
			posicion = 1;
			ps.setLong(posicion++, localidad);
			rs = ps.executeQuery();
			list[4] = getCollection(rs);
			rs.close();
			ps.close();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener filtros de lideres. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return list;
	}

	public Collection getEstudiantesGrupo(Login l, String[] params) {
		// System.out.println("entro por getEstudiantesGrupo");
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection list = null;
		try {
			long inst = Long.parseLong(l.getInstId());
			long sede = Long.parseLong(params[0]);
			long jor = Long.parseLong(params[1]);
			long met = Long.parseLong(params[2]);
			long gra = Long.parseLong(params[3]);
			long gru = Long.parseLong(params[4]);

			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("EstudiantesGrupo"));
			posicion = 1;
			// System.out.println("inst" + inst + " sede " + sede + " jor: " +
			// jor
			// + " met " + met + " gra " + gra + " gru " + gru);
			ps.setLong(posicion++, inst);
			ps.setLong(posicion++, sede);
			ps.setLong(posicion++, jor);
			ps.setLong(posicion++, met);
			ps.setLong(posicion++, gra);
			ps.setLong(posicion++, gru);
			rs = ps.executeQuery();
			
			//Se comenta if rs.next ya q el list estaba iniciando en  la posicion 1 y debe iniciar en la posicion 0
			//if (rs.next()) {
				// System.out.println("***si hay LISTA DE ESTUDIANTES***");
			//}
			list = getCollection(rs);
			rs.close();
			ps.close();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener Estudiantes de grupo. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return list;
	}

	public Collection getPersonal(Login l, String[] params) {
		// System.out.println("entro por getPersonal");
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection list = null;
		try {
			long inst = Long.parseLong(l.getInstId());
			long sede = Long.parseLong(params[0]);
			long jor = Long.parseLong(params[1]);

			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("getPersonal"));
			posicion = 1;
			// System.out
			// .println("inst" + inst + " sede " + sede + " jor: " + jor);
			ps.setLong(posicion++, inst);
			ps.setLong(posicion++, sede);
			ps.setLong(posicion++, jor);
			ps.setLong(posicion++, inst);
			rs = ps.executeQuery();
			if (rs.next()) {
				// System.out.println("***si hay LISTA DE PERSONAL***");
			}
			list = getCollection(rs);
			rs.close();
			ps.close();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener Estudiantes de grupo. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return list;
	}

	public Collection getEstudiantesGrupoEstudiante(Login l, String[] params) {
		// System.out.println("entro por getEstudiantesGrupoEstudiante");
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection list = null;
		Collection list2 = null;
		try {
			long inst = Long.parseLong(l.getInstId());
			long sede = Long.parseLong(params[0]);
			long jor = Long.parseLong(params[1]);
			long met = Long.parseLong(params[2]);
			long gra = Long.parseLong(params[3]);
			long gru = Long.parseLong(params[4]);
			String estudiante = (params[5]);

			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("EstudiantesGrupoEstudiante"));
			posicion = 1;
			// System.out.println("inst" + inst + " sede " + sede + " jor: " +
			// jor
			// + " met " + met + " gra " + gra + " gru " + gru + " est "
			// + estudiante);
			ps.setLong(posicion++, inst);
			ps.setLong(posicion++, sede);
			ps.setLong(posicion++, jor);
			ps.setLong(posicion++, met);
			ps.setLong(posicion++, gra);
			ps.setLong(posicion++, gru);
			ps.setString(posicion++, estudiante);
			rs = ps.executeQuery();
			if (rs.next()) {
				// System.out
				// .println("***si EXISTE EL REGISTRO UNICO DEL ESTUDIANTE***");
				int m = rs.getMetaData().getColumnCount();
				// System.out.println("VALOR DE m en DAO: " + m);
				list2 = new ArrayList();
				Object[] o;
				int i = 0;
				int ff = 0;
				ff++;
				o = new Object[m];
				for (i = 1; i <= m; i++) {
					// System.out.println("entroOOOOOO");
					o[i - 1] = rs.getString(i);
				}
				list2.add(o);

				// list = getCollection1(rs);
				list = list2;
			}
			rs.close();
			ps.close();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener Estudiantes de grupo. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return list;
	}

	public Collection getPersonalPersona(Login l, String[] params) {
		// System.out.println("entro por getPersonalPersona");
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection list = null;
		Collection list2 = null;
		try {
			long inst = Long.parseLong(l.getInstId());
			long sede = Long.parseLong(params[0]);
			long jor = Long.parseLong(params[1]);
			String estudiante = (params[2]);

			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("getPersonalPersona"));
			posicion = 1;
			// System.out.println("inst" + inst + " sede " + sede + " jor: " +
			// jor
			// + " est " + estudiante);
			ps.setLong(posicion++, inst);
			ps.setLong(posicion++, sede);
			ps.setLong(posicion++, jor);
			ps.setString(posicion++, estudiante);
			rs = ps.executeQuery();
			if (rs.next()) {
				// System.out
				// .println("***si EXISTE EL REGISTRO UNICO DEL PERSONAL***");
				int m = rs.getMetaData().getColumnCount();
				// System.out.println("VALOR DE m en DAO: " + m);
				list2 = new ArrayList();
				Object[] o;
				int i = 0;
				int ff = 0;
				ff++;
				o = new Object[m];
				for (i = 1; i <= m; i++) {
					// System.out.println("entroOOOOOO");
					o[i - 1] = rs.getString(i);
				}
				list2.add(o);
				// list = getCollection1(rs);
				list = list2;
			}
			rs.close();
			ps.close();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener Estudiantes de grupo. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return list;
	}

	/**
	 * Devuelve una conexinn activa
	 * 
	 * @return Conexinn
	 * @throws Exception
	 */
	public Connection getConnection() throws Exception {
		return cursor.getConnection();
	}

	/**
	 * Devuelve el tipo de rol
	 * 
	 * @return tipoRol
	 * @throws Exception
	 */
	public int getTipoRolVal(int rol) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int codigo = 0;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getTipoRolVal"));
			st.setInt(i++, rol);
			rs = st.executeQuery();
			if (rs.next()) {
				codigo = rs.getInt(1);
				// System.out.println("***codigo del tipo de rol..." + codigo);
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
		return codigo;
	}
}
