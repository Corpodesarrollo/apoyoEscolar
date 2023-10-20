/**
 * 
 */
package participacion.reportes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import participacion.common.exception.IntegrityException;
import participacion.common.exception.ParticipacionException;
import participacion.common.vo.ParamParticipacion;
import participacion.lideres.vo.FiltroLideresVO;
import participacion.lideres.vo.LideresVO;
import participacion.reportes.vo.ActasVO;
import participacion.reportes.vo.FiltroItemsVO;
import participacion.reportes.vo.ItemsCaracterizacionVO;
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
public class ReportesDAO extends Dao {

	/**
	 * Constructor
	 * 
	 * @param c
	 *            Objeto para la obtencinn de conexiones
	 */
	public ReportesDAO(Cursor c) {
		super(c);
		rb = ResourceBundle.getBundle("participacion.reportes.bundle.reportes");
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
			// System.out.println("lista instancias "+l.size());
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
			// System.out.println("**getLideres*** instancia: "
			// + filtro.getFilInstancia() + " rango: "
			// + filtro.getFilRango() + " particiapante: "
			// + filtro.getFilParticipante());
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

	public List getListaLideres(FiltroItemsVO filtro, int codigo)
			throws ParticipacionException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemsCaracterizacionVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("lideres.listaNoRol"));
			// System.out
			// .println("REPORTES PARTICIPANTES: FILTRO VALORES INSTANCIA: "
			// + filtro.getFilInstancia()
			// + "  *** RANGO: "
			// + filtro.getFilRango()
			// + "   *** PARINST: "
			// + codigo
			// + "    ***COLEGIO:  "
			// + filtro.getFilColegio()
			// + "   NIVEL:"
			// + filtro.getFilNivel()
			// + "   LOCALIDAD: "
			// + filtro.getFilLocalidad());
			st.setInt(i++, filtro.getFilInstancia());
			st.setInt(i++, filtro.getFilInstancia());
			st.setInt(i++, filtro.getFilRango());
			st.setInt(i++, filtro.getFilRango());
			st.setLong(i++, filtro.getFilColegio());
			st.setLong(i++, filtro.getFilColegio());
			st.setInt(i++, codigo);
			st.setInt(i++, codigo);
			st.setLong(i++, filtro.getFilNivel());
			st.setLong(i++, filtro.getFilNivel());
			st.setLong(i++, filtro.getFilLocalidad());
			st.setLong(i++, filtro.getFilLocalidad());
			rs = st.executeQuery();
			while (rs.next()) {

				i = 1;
				item = new ItemsCaracterizacionVO();
				item.setNombreItemNivel(rs.getString(i++));
				item.setNombreItemInstancia(rs.getString(i++));
				item.setItemDaneColegio(rs.getLong(i++));
				item.setNombreItemColegio(rs.getString(i++));
				item.setNombreItemSede(rs.getString(i++));
				item.setNombreItemJornada(rs.getString(i++));
				item.setNombreItemTipoDocumento(rs.getString(i++));
				item.setItemNumeroDocumento(rs.getString(i++));
				item.setItemApellido1(rs.getString(i++));
				item.setItemApellido2(rs.getString(i++));
				item.setItemNombre1(rs.getString(i++));
				item.setItemNombre2(rs.getString(i++));
				item.setItemRol(rs.getInt(i++));
				item.setItemNombreRol(rs.getString(i++));
				item.setItemTelefono(rs.getString(i++));
				item.setItemCelular(rs.getString(i++));
				item.setItemCorreoElectronico(rs.getString(i++));
				item.setItemEdad(rs.getInt(i++));
				item.setItemGenero(rs.getString(i++));
				item.setNombreItemEtnia(rs.getString(i++));
				item.setNombreItemLocalidadEstudio(rs.getString(i++));
				item.setNombreItemLocalidadResidencia(rs.getString(i++));
				item.setItemEntidad(rs.getString(i++));
				item.setNombreItemSectorEconomico(rs.getString(i++));
				item.setNombreItemDiscapacidad(rs.getString(i++));
				item.setNombreItemLgtb(rs.getString(i++));
				item.setNombreItemOcupacion(rs.getString(i++));
				item.setNombreItemDesplazado(rs.getString(i++));
				item.setNombreItemAmenazado(rs.getString(i++));
				item.setItemParcodigo(rs.getInt(i++));
				l.add(item);
			}
			// if (l.size() > 0)
			// System.out
			// .println("***Hay registros en la lista de items de caracterizacion***");
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

	public List getListaActas(FiltroItemsVO filtro, int codigo)
			throws ParticipacionException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ActasVO acta = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("acta.listaReportes"));
			st.setInt(i++, filtro.getFilNivel());
			st.setInt(i++, filtro.getFilNivel());
			st.setInt(i++, filtro.getFilInstancia());
			st.setInt(i++, filtro.getFilInstancia());
			st.setInt(i++, filtro.getFilRango());
			st.setInt(i++, filtro.getFilRango());
			st.setLong(i++, filtro.getFilColegio());
			st.setLong(i++, filtro.getFilColegio());
			st.setLong(i++, filtro.getFilLocalidad());
			st.setLong(i++, filtro.getFilLocalidad());
			// st.setInt(i++,codigo);
			rs = st.executeQuery();
			while (rs.next()) {
				// System.out.println("***Hay registros en la lista de ACTAS***");
				i = 1;
				acta = new ActasVO();
				acta.setDaneColegio(rs.getString(i++));
				acta.setNombreColegio(rs.getString(i++));
				acta.setNombreInstancia(rs.getString(i++));
				acta.setFecha(rs.getString(i++));
				acta.setLugar(rs.getString(i++));
				acta.setAsunto(rs.getString(i++));
				acta.setFechaElaboracion(rs.getString(i++));
				acta.setActividades(rs.getString(i++));
				acta.setAcuerdos(rs.getString(i++));
				acta.setCompromisos(rs.getString(i++));
				acta.setResponsabilidades(rs.getString(i++));
				l.add(acta);
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

	public int getSectorInstitucion(long institucion)
			throws ParticipacionException {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int codigo = 0;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getSectorInstitucion"));
			st.setLong(i++, institucion);
			rs = st.executeQuery();
			if (rs.next()) {
				codigo = rs.getInt(1);
				// System.out.println("***codigo del sector..." + codigo);
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
			if (rs.next()) {
				// System.out.println("***si hay LISTA DE ESTUDIANTES***");
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

	/**
	 * Devuelve una conexinn activa
	 * 
	 * @return Conexinn
	 * @throws Exception
	 */
	public Connection getConnection() throws Exception {
		return cursor.getConnection();
	}

}
