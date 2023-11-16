package siges.estudiante.dao;


/**
 *	VERSION		FECHA			AUTOR				DESCRIPCION
 *		1.0		10/05/2021		JORGE CAMACHO		Código Espagueti
 *													Se cambió el cast del dato del número de documento del
 *													estudiante de Long a String en el método actualizar()
 *
*/

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.io.CopyUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.validator.GenericValidator;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.dao.Ruta;
import siges.estudiante.beans.AcademicaVO;
import siges.estudiante.beans.AsistenciaVO;
import siges.estudiante.beans.AtencionEspecial;
import siges.estudiante.beans.Basica;
import siges.estudiante.beans.Convivencia;
import siges.estudiante.beans.Familiar;
import siges.estudiante.beans.FiltroBean;
import siges.estudiante.beans.Salud;
import siges.exceptions.InternalErrorException;
import siges.login.beans.Login;
//import weblogic.descriptor.descriptorgen.descriptorgen;

/**
 * @author USUARIO
 * 
 *         Ventana - Preferencias - Java - Estilo de cndigo - Plantillas de
 *         cndigo
 */
public class EstudianteDAO extends Dao {
	public String sentencia;
	public String buscar;
	private ResourceBundle rb;
	private java.text.SimpleDateFormat sdf;
	private static final int ESTADO_MATRICULADO=101;
	private static final int ESTADO_MATRICULA_INACTIVO=300;
	private static final int GRUPO_0=0;

	public EstudianteDAO(Cursor cur) {
		super(cur);
		sentencia = null;
		sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		rb = ResourceBundle.getBundle("siges.estudiante.bundle.estudiante");

	}

	/**
	 * Funcinn: Asignar Basica<br>
	 * 
	 * @param String
	 *            id
	 * @return boolean
	 **/
	public Basica asignarBasica(String id, Login login) {
		Basica b = null;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet r = null;
		int posicion = 1; // posicion inicial de llenado del preparedstatement
		long inst0 = Long.parseLong(login.getInstId());
		int i = 1;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("BasicaAsignar"));
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(id.trim()));
			r = pst.executeQuery();
			if (r.next()) {
				b = new Basica();
				b.setEstgrupo(r.getString(i++));
				b.setEstcodigo(r.getString(i++));
				b.setEstexpdoccoddep(r.getString(i++));
				b.setEstexpdoccodmun(r.getString(i++));
				b.setEsttipodoc(r.getString(i++));
				b.setEstnumdoc(r.getString(i++));
				b.setEstnombre1(r.getString(i++));
				b.setEstnombre2(r.getString(i++));
				b.setEstapellido1(r.getString(i++));
				b.setEstapellido2(r.getString(i++));
				b.setEstfechanac(r.getString(i++));
				b.setEstlugnaccoddep(r.getString(i++));
				b.setEstlugnaccodmun(r.getString(i++));
				b.setEstedad(r.getString(i++));
				b.setEstgenero(r.getString(i++));
				b.setEstetnia(r.getString(i++));
				b.setEstcondicion(r.getString(i++));
				b.setEstcodconv(r.getString(i++));
				b.setEstdireccion(r.getString(i++));
				b.setEsttelefono(r.getString(i++));
				b.setEstzona(r.getString(i++));
				b.setEstlocvereda(r.getString(i++));
				b.setEstbarcorreg(r.getString(i++));
				b.setEstestrato(r.getString(i++));
				b.setEstsisben(r.getString(i++));
				b.setEstcodfamilia(r.getString(i++));
				b.setEstado("1");
			}
			if (b != null && !b.getEstgrupo().equals("0")
					&& !b.getEstgrupo().equals("")) {
				r.close();
				pst.close();
				pst = cn.prepareStatement(rb.getString("UbicacionAsignar"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, Long.parseLong(b.getEstgrupo()));
				r = pst.executeQuery();
				i = 1;
				long inst1 = 0;
				if (r.next()) {
					inst1 = r.getLong(i++);
					if (inst0 == inst1) {
						b.setEstsede(r.getString(i++));
						b.setEstjor(r.getString(i++));
						b.setEstmet(r.getString(i++));
						b.setEstgra(r.getString(i++));
						b.setEstgru(r.getString(i++));
						b.setEstado(rb.getString("habilitadoEdicion"));
					} else {
						// no permitir la modificacion de la ubicacion
						b.setEstado("2");
					}
				}
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			setMensaje("Error intentando asignar información basica. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return b;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return b;
	}

	/**
	 * Funcinn: Asignar Familiar<br>
	 * 
	 * @param String
	 *            id
	 * @return boolean
	 **/
	public Familiar asignarFamiliar(String id) {
		Familiar f = null;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet r = null;
		int posicion = 1; // posicion inicial de llenado del preparedstatement
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("FamiliarAsignar"));
			pst.clearParameters();
			if (id.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(id.trim()));
			r = pst.executeQuery();
			if (r.next()) {
				f = new Familiar();
				int i = 1;
				f.setFamcodigo(r.getString(i++));
				f.setFamnommadre(r.getString(i++));
				if (!f.getFamnommadre().equals(""))
					f.setMadre("1");
				else
					f.setMadre("2");
				f.setFamtipdocmadre(r.getString(i++));
				f.setFamnumdocmadre(r.getString(i++));
				f.setFamocumadre(r.getString(i++));
				f.setFamdirmadre(r.getString(i++));
				f.setFamtelmadre(r.getString(i++));
				f.setFamnompadre(r.getString(i++));
				if (!f.getFamnompadre().equals(""))
					f.setPadre("1");
				else
					f.setPadre("2");
				f.setFamtipdocpadre(r.getString(i++));
				f.setFamnumdocpadre(r.getString(i++));
				f.setFamocupadre(r.getString(i++));
				f.setFamdirpadre(r.getString(i++));
				f.setFamtelpadre(r.getString(i++));
				f.setFamnomacudi(r.getString(i++));
				f.setFamtipdocacudi(r.getString(i++));
				f.setFamnumdocacudi(r.getString(i++));
				f.setFamocuacudi(r.getString(i++));
				f.setFamdiracudi(r.getString(i++));
				f.setFamtelacudi(r.getString(i++));
				f.setFamnumhermanos(r.getString(i++));
				f.setFamnomhijos(r.getString(i++));
				f.setEstado("1");
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			setMensaje("Error intentando asignar familia. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return f;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return f;
	}

	/**
	 * Funcinn: Asignar Salud<br>
	 * 
	 * @param String
	 *            id
	 * @param String
	 *            id2
	 * @return boolean
	 **/
	public Salud asignarSalud(String id, String id2) {
		Salud s = null;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet r = null;
		int posicion = 1; // posicion inicial de llenado del preparedstatement
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("SaludAsignar"));
			pst.clearParameters();
			if (id.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(id.trim()));
			if (id2.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(id2.trim()));
			r = pst.executeQuery();

			if (r.next()) {
				s = new Salud();
				int i = 1;
				s.setSaltipoperso(r.getString(i++));
				s.setSalcodperso(r.getString(i++));
				s.setSaltiposangre(r.getString(i++));
				s.setSaleps(r.getString(i++));
				s.setSalars(r.getString(i++));
				s.setSalalergias(r.getString(i++));
				s.setSalenfermedades(r.getString(i++));
				s.setSalmedicamentos(r.getString(i++));
				s.setSaltelemerg(r.getString(i++));
				s.setSalperemerg(r.getString(i++));
				s.setSalestado("1");
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			setMensaje("Error intentando asignar salud. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return s;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return s;
	}

	/**
	 * Funcinn: Eliminar Estudiante<br>
	 * 
	 * @param String
	 *            id
	 * @param String
	 *            id2
	 * @return boolean
	 **/
	public boolean eliminarEstudiante(String id, String id2) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			// BASICAELIMINAR
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("BasicaEliminar"));
			pst.clearParameters();
			if (id2.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(id2.trim()));
			pst.executeUpdate();
			pst = null;
			posicion = 1;
			// SALUDELIMINAR
			pst = cn.prepareStatement(rb.getString("SaludEliminar"));
			pst.clearParameters();
			if (id.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(id.trim()));
			if (id2.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(id2.trim()));
			pst.executeUpdate();
			pst = null;
			posicion = 1;
			// CONVIVENCIATOTALELIMINAR
			pst = cn.prepareStatement(rb.getString("ConvivenciaTotalEliminar"));
			pst.clearParameters();
			if (id.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(id.trim()));
			if (id2.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(id2.trim()));
			pst.executeUpdate();
			pst = null;
			posicion = 1;
			// ASISTENCIATOTALELIMINAR
			pst = cn.prepareStatement(rb.getString("AsistenciaTotalEliminar"));
			pst.clearParameters();
			if (id.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(id.trim()));
			if (id2.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(id2.trim()));
			pst.executeUpdate();
			pst = null;
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando eliminar estudiante. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public AtencionEspecial asignarAtencion(String id) {
		AtencionEspecial ae = null;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("AtencionAsignar"));
			pst.clearParameters();
			if (id.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(id.trim()));
			rs = pst.executeQuery();
			if (rs.next()) {
				ae = new AtencionEspecial();
				int i = 1;
				ae.setAtesid(rs.getString(i++));
				ae.setAtescodest(rs.getString(i++));
				ae.setAtestipo(rs.getString(i++));
				ae.setAtesperiodo(rs.getString(i++));
				ae.setAtesfecha(rs.getString(i++));
				ae.setAtesasunto(rs.getString(i++));
				ae.setAtesdescripcion(rs.getString(i++));
				ae.setAtesmostrar(rs.getString(i++));
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			setMensaje("Error SQL intentando asignar convivencia. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return ae;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return ae;
	}

	public Convivencia asignarConvivencia(String id) {
		Convivencia c = null;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet r = null;
		int posicion = 1; // posicion inicial de llenado del preparedstatement
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("ConvivenciaAsignar"));
			pst.clearParameters();
			if (id.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(id.trim()));
			r = pst.executeQuery();
			if (r.next()) {
				c = new Convivencia();
				int i = 1;
				c.setConcodigo(r.getString(i++));
				c.setConcodinst(r.getString(i++));
				c.setContipoperso(r.getString(i++));
				c.setConcodperso(r.getString(i++));
				c.setContipo(r.getString(i++));
				c.setCondescripcion(r.getString(i++));
				c.setConfecha(r.getString(i++));
				c.setConacuerdos(r.getString(i++));
				c.setConseguimiento(r.getString(i++));
				c.setPeriodo(r.getString(i++));
				c.setIdClase(r.getString(i++));
				c.setIdTipo(r.getString(i++));
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			setMensaje("Error SQL intentando asignar convivencia. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return c;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return c;
	}

	public boolean eliminarAtencion(String id) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("AtencionEliminar"));
			pst.clearParameters();
			if (id.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(id.trim()));
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error SQL intentando ELIMINAR convivencia. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: Eliminar Convivencia<br>
	 * 
	 * @param String
	 *            id
	 * @return boolean
	 **/
	public boolean eliminarConvivencia(String id) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("ConvivenciaEliminar"));
			pst.clearParameters();
			if (id.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(id.trim()));
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error SQL intentando ELIMINAR convivencia. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: Comparar beans de Basica<br>
	 * 
	 * @param Basica
	 *            b
	 * @param Basica
	 *            b2
	 * @return boolean
	 **/
	public boolean compararBeans(Basica b, Basica b2) {
		if (!b.getEstgrupo().equals(b2.getEstgrupo()))
			return false;
		if (!b.getEstcodigo().equals(b2.getEstcodigo()))
			return false;
		if (!b.getEstexpdoccoddep().equals(b2.getEstexpdoccoddep()))
			return false;
		if (!b.getEstexpdoccodmun().equals(b2.getEstexpdoccodmun()))
			return false;
		if (!b.getEsttipodoc().equals(b2.getEsttipodoc()))
			return false;
		if (!b.getEstnumdoc().equals(b2.getEstnumdoc()))
			return false;
		if (!b.getEstnombre1().equals(b2.getEstnombre1()))
			return false;
		if (!b.getEstnombre2().equals(b2.getEstnombre2()))
			return false;
		if (!b.getEstapellido1().equals(b2.getEstapellido1()))
			return false;
		if (!b.getEstapellido2().equals(b2.getEstapellido2()))
			return false;
		if (!b.getEstfechanac().equals(b2.getEstfechanac()))
			return false;
		if (!b.getEstlugnaccoddep().equals(b2.getEstlugnaccoddep()))
			return false;
		if (!b.getEstlugnaccodmun().equals(b2.getEstlugnaccodmun()))
			return false;
		if (!b.getEstedad().equals(b2.getEstedad()))
			return false;
		if (!b.getEstgenero().equals(b2.getEstgenero()))
			return false;
		if (!b.getEstetnia().equals(b2.getEstetnia()))
			return false;
		if (!b.getEstcondicion().equals(b2.getEstcondicion()))
			return false;
		if (!b.getEstcodconv().equals(b2.getEstcodconv()))
			return false;
		if (!b.getEstdireccion().equals(b2.getEstdireccion()))
			return false;
		if (!b.getEsttelefono().equals(b2.getEsttelefono()))
			return false;
		if (!b.getEstzona().equals(b2.getEstzona()))
			return false;
		if (!b.getEstlocvereda().equals(b2.getEstlocvereda()))
			return false;
		if (!b.getEstbarcorreg().equals(b2.getEstbarcorreg()))
			return false;
		if (!b.getEstestrato().equals(b2.getEstestrato()))
			return false;
		if (!b.getEstsisben().equals(b2.getEstsisben()))
			return false;
		if (!b.getEstcodfamilia().equals(b2.getEstcodfamilia()))
			return false;
		// nuevos de ubicacion
		if (!b.getEstsede().equals(b2.getEstsede()))
			return false;
		if (!b.getEstjor().equals(b2.getEstjor()))
			return false;
		if (!b.getEstmet().equals(b2.getEstmet()))
			return false;
		if (!b.getEstgra().equals(b2.getEstgra()))
			return false;
		if (!b.getEstgru().equals(b2.getEstgru()))
			return false;
		return true;
	}

	public boolean compararBeans(AcademicaVO a, AcademicaVO a2) {
		if (!a.getAcaEstudiante().equals(a2.getAcaEstudiante()))
			return false;
		if (!a.getAcaId().equals(a2.getAcaId()))
			return false;
		if (!a.getAcaInst().equals(a2.getAcaInst()))
			return false;
		if (!a.getAcaGrado().equals(a2.getAcaGrado()))
			return false;
		if (!a.getAcaTitulo().equals(a2.getAcaTitulo()))
			return false;
		if (!a.getAcaAnho().equals(a2.getAcaAnho()))
			return false;
		if (!a.getAcaGradoEstado().equals(a2.getAcaGradoEstado()))
			return false;
		return true;
	}

	public boolean compararBeans(AsistenciaVO a, AsistenciaVO a2) {
		if (!a.getAsiTipoPer().equals(a2.getAsiTipoPer()))
			return false;
		if (!a.getAsiCodPer().equals(a2.getAsiCodPer()))
			return false;
		if (!a.getAsiCodigo().equals(a2.getAsiCodigo()))
			return false;
		if (!a.getAsiFecha().equals(a2.getAsiFecha()))
			return false;
		if (!a.getAsiMotivo().equals(a2.getAsiMotivo()))
			return false;
		if (!a.getAsiObservacion().equals(a2.getAsiObservacion()))
			return false;
		if (!a.getAsiJustificada().equals(a2.getAsiJustificada()))
			return false;
		return true;
	}

	/**
	 * Funcinn: Comparar beans de Familiar<br>
	 * 
	 * @param Familiar
	 *            f
	 * @param Familiar
	 *            f2
	 * @return boolean
	 **/
	public boolean compararBeans(Familiar f, Familiar f2) {
		if (!f.getMadre().equals(f2.getMadre()))
			return false;
		if (!f.getPadre().equals(f2.getPadre()))
			return false;
		if (!f.getFamnommadre().equals(f2.getFamnommadre()))
			return false;
		if (!f.getFamtipdocmadre().equals(f2.getFamtipdocmadre()))
			return false;
		if (!f.getFamnumdocmadre().equals(f2.getFamnumdocmadre()))
			return false;
		if (!f.getFamocumadre().equals(f2.getFamocumadre()))
			return false;
		if (!f.getFamdirmadre().equals(f2.getFamdirmadre()))
			return false;
		if (!f.getFamtelmadre().equals(f2.getFamtelmadre()))
			return false;
		if (!f.getFamnompadre().equals(f2.getFamnompadre()))
			return false;
		if (!f.getFamtipdocpadre().equals(f2.getFamtipdocpadre()))
			return false;
		if (!f.getFamnumdocpadre().equals(f2.getFamnumdocpadre()))
			return false;
		if (!f.getFamocupadre().equals(f2.getFamocupadre()))
			return false;
		if (!f.getFamdirpadre().equals(f2.getFamdirpadre()))
			return false;
		if (!f.getFamtelpadre().equals(f2.getFamtelpadre()))
			return false;
		if (!f.getFamnomacudi().equals(f2.getFamnomacudi()))
			return false;
		if (!f.getFamtipdocacudi().equals(f2.getFamtipdocacudi()))
			return false;
		if (!f.getFamnumdocacudi().equals(f2.getFamnumdocacudi()))
			return false;
		if (!f.getFamocuacudi().equals(f2.getFamocuacudi()))
			return false;
		if (!f.getFamdiracudi().equals(f2.getFamdiracudi()))
			return false;
		if (!f.getFamtelacudi().equals(f2.getFamtelacudi()))
			return false;
		if (!f.getFamnumhermanos().equals(f2.getFamnumhermanos()))
			return false;
		if (!f.getFamnomhijos().equals(f2.getFamnomhijos()))
			return false;
		return true;
	}

	/**
	 * Funcinn: Comparar beans de Salud<br>
	 * 
	 * @param Salud
	 *            s
	 * @param Salud
	 *            s2
	 * @return boolean
	 **/
	public boolean compararBeans(Salud s, Salud s2) {
		if (!s.getSaltipoperso().equals(s2.getSaltipoperso()))
			return false;
		if (!s.getSalcodperso().equals(s2.getSalcodperso()))
			return false;
		if (!s.getSaltiposangre().equals(s2.getSaltiposangre()))
			return false;
		if (!s.getSaleps().equals(s2.getSaleps()))
			return false;
		if (!s.getSalars().equals(s2.getSalars()))
			return false;
		if (!s.getSalalergias().equals(s2.getSalalergias()))
			return false;
		if (!s.getSalenfermedades().equals(s2.getSalenfermedades()))
			return false;
		if (!s.getSalmedicamentos().equals(s2.getSalmedicamentos()))
			return false;
		if (!s.getSaltelemerg().equals(s2.getSaltelemerg()))
			return false;
		if (!s.getSalperemerg().equals(s2.getSalperemerg()))
			return false;
		if (!s.getSalestado().equals(s2.getSalestado()))
			return false;
		return true;
	}

	public boolean compararBeans(AtencionEspecial ae, AtencionEspecial ae2) {
		if (!ae.getAtesasunto().equals(ae2.getAtesasunto()))
			return false;
		if (!ae.getAtestipo().equals(ae2.getAtestipo()))
			return false;
		if (!ae.getAtesperiodo().equals(ae2.getAtesperiodo()))
			return false;
		if (!ae.getAtesfecha().equals(ae2.getAtesfecha()))
			return false;
		if (!ae.getAtesdescripcion().equals(ae2.getAtesdescripcion()))
			return false;
		if (!ae.getAtesmostrar().equals(ae2.getAtesmostrar()))
			return false;
		return true;
	}

	public boolean compararBeans(Convivencia c, Convivencia c2) {
		if (!c.getConcodinst().equals(c2.getConcodinst()))
			return false;
		if (!c.getContipoperso().equals(c2.getContipoperso()))
			return false;
		if (!c.getConcodperso().equals(c2.getConcodperso()))
			return false;
		if (!c.getContipo().equals(c2.getContipo()))
			return false;
		if (!c.getCondescripcion().equals(c2.getCondescripcion()))
			return false;
		if (!c.getConfecha().equals(c2.getConfecha()))
			return false;
		if (!c.getConacuerdos().equals(c2.getConacuerdos()))
			return false;
		if (!c.getConseguimiento().equals(c2.getConseguimiento()))
			return false;
		if (!c.getPeriodo().equals(c2.getPeriodo()))
			return false;
		if (!c.getIdClase().equals(c2.getIdClase()))
			return false;
		if (!c.getIdTipo().equals(c2.getIdTipo()))
			return false;
		return true;
	}
	
	
	
	
	
	/**
	 * Funcinn: Actualizar Bnsica<br>
	 * 
	 * @param Basica
	 *            basica
	 * @param String
	 *            id
	 * @return boolean
	 **/
	public boolean actualizar(Basica basica, Login login, String id) {
		
		long grupo = 0;
		int posicion = 1;
		
		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement pst = null;
		
		try {
			
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			
			pst = cn.prepareStatement(rb.getString("getGrupo"));
			pst.clearParameters();
			
			pst.setLong(posicion++, Long.parseLong(login.getInstId()));
			pst.setLong(posicion++, Long.parseLong(basica.getEstsede()));
			pst.setLong(posicion++, Long.parseLong(basica.getEstjor()));
			pst.setLong(posicion++, Long.parseLong(basica.getEstmet()));
			pst.setLong(posicion++, Long.parseLong(basica.getEstgra()));
			pst.setLong(posicion++, Long.parseLong(basica.getEstgru()));
			
			rs = pst.executeQuery();
			
			if (rs.next()) {
				grupo = rs.getLong(1);
			} else {
				setMensaje("Grupo no encontrado");
				return false;
			}
			rs.close();
			pst.close();
			
			posicion = 1;
			
			pst = cn.prepareStatement(rb.getString("BasicaActualizar"));
			pst.clearParameters();
			
			pst.setLong(posicion++, grupo);
			
			if (basica.getEstexpdoccoddep().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++, Integer.parseInt(basica.getEstexpdoccoddep().trim()));
			
			if (basica.getEstexpdoccodmun().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++, Integer.parseInt(basica.getEstexpdoccodmun().trim()));
			
			if (basica.getEsttipodoc().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++, Integer.parseInt(basica.getEsttipodoc().trim()));
			
			if (basica.getEstnumdoc().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, basica.getEstnumdoc().trim());
			
			if (basica.getEstnombre1().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, basica.getEstnombre1().trim());
			
			if (basica.getEstnombre2().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, basica.getEstnombre2().trim());
			
			if (basica.getEstapellido1().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, basica.getEstapellido1().trim());
			
			if (basica.getEstapellido2().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, basica.getEstapellido2().trim());
			
			if (basica.getEstfechanac().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setDate(posicion++,	new java.sql.Date(sdf.parse(basica.getEstfechanac()).getTime()));
			
			if (basica.getEstlugnaccoddep().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++, Integer.parseInt(basica.getEstlugnaccoddep().trim()));
			
			if (basica.getEstlugnaccodmun().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++, Integer.parseInt(basica.getEstlugnaccodmun().trim()));
			
			if (basica.getEstedad().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++, Integer.parseInt(basica.getEstedad().trim()));
			
			if (basica.getEstgenero().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++, Integer.parseInt(basica.getEstgenero().trim()));
			
			if (basica.getEstetnia().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++, Integer.parseInt(basica.getEstetnia().trim()));
			
			if (basica.getEstcondicion().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++, Integer.parseInt(basica.getEstcondicion().trim()));
			
			if (basica.getEstcodconv().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, basica.getEstcodconv().trim());
			
			if (basica.getEstdireccion().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, basica.getEstdireccion().trim());
			
			if (basica.getEsttelefono().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, basica.getEsttelefono().trim());
			
			if (basica.getEstzona().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++, Integer.parseInt(basica.getEstzona().trim()));
			
			if (basica.getEstlocvereda().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, basica.getEstlocvereda().trim());
			
			if (basica.getEstbarcorreg().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, basica.getEstbarcorreg().trim());
			
			if (basica.getEstestrato().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++, Integer.parseInt(basica.getEstestrato().trim()));
			
			if (basica.getEstsisben().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++, Integer.parseInt(basica.getEstsisben().trim()));
			
			if (basica.getEstcodfamilia().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(basica.getEstcodfamilia().trim()));
			
			pst.setLong(posicion++, Long.parseLong(id));
			pst.executeUpdate();
			
			cn.commit();
			cn.setAutoCommit(true);
			
		} catch (InternalErrorException in) {
			setMensaje("NO se puede establecer conexión con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error SQL intentando ingresar información básica. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
			}
			return false;
		} catch (java.text.ParseException e) {
			setMensaje("Error interpretando fecha: " + e.toString());
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		
		return true;
		
	}
	
	
	
	
	
	/**
	 * Funcinn: Actualizar Familiar<br>
	 * 
	 * @param Familiar
	 *            f
	 * @return boolean
	 **/
	public boolean actualizar(Familiar f) {
		int posicion = 1; // posicion inicial de llenado del preparedstatement
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("FamiliarActualizar"));
			pst.clearParameters();
			if (f.getFamnommadre().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getFamnommadre().trim());
			if (f.getFamtipdocmadre().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++,
						Integer.parseInt(f.getFamtipdocmadre().trim()));
			if (f.getFamnumdocmadre().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getFamnumdocmadre().trim());
			if (f.getFamocumadre().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getFamocumadre().trim());
			if (f.getFamdirmadre().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getFamdirmadre().trim());
			if (f.getFamtelmadre().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getFamtelmadre().trim());
			if (f.getFamnompadre().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getFamnompadre().trim());
			if (f.getFamtipdocpadre().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++,
						Integer.parseInt(f.getFamtipdocpadre().trim()));
			if (f.getFamnumdocpadre().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getFamnumdocpadre().trim());
			if (f.getFamocupadre().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getFamocupadre().trim());
			if (f.getFamdirpadre().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getFamdirpadre().trim());
			if (f.getFamtelpadre().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getFamtelpadre().trim());
			if (f.getFamnomacudi().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getFamnomacudi().trim());
			if (f.getFamtipdocacudi().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++,
						Integer.parseInt(f.getFamtipdocacudi().trim()));
			if (f.getFamnumdocacudi().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getFamnumdocacudi().trim());
			if (f.getFamocuacudi().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getFamocuacudi().trim());
			if (f.getFamdiracudi().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getFamdiracudi().trim());
			if (f.getFamtelacudi().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getFamtelacudi().trim());
			if (f.getFamnumhermanos().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++,
						Integer.parseInt(f.getFamnumhermanos().trim()));
			if (f.getFamnomhijos().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getFamnomhijos().trim());
			if (f.getFamcodigo().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(f.getFamcodigo().trim()));
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error SQL intentando ingresar información familiar. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: Actualizar Salud<br>
	 * 
	 * @param Salud
	 *            s
	 * @param String
	 *            [] valores
	 * @return boolean
	 **/
	public boolean actualizar(Salud s, String[] valores) {
		int posicion = 1; // posicion inicial de llenado del preparedstatement
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("SaludActualizar"));
			pst.clearParameters();
			if (s.getSaltipoperso().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++,
						Integer.parseInt(s.getSaltipoperso().trim()));
			if (s.getSalcodperso().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++,
						Long.parseLong(s.getSalcodperso().trim()));
			if (s.getSaltiposangre().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getSaltiposangre().trim());
			if (s.getSaleps().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getSaleps().trim());
			if (s.getSalars().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getSalars().trim());
			if (s.getSalalergias().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getSalalergias().trim());
			if (s.getSalenfermedades().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getSalenfermedades().trim());
			if (s.getSalmedicamentos().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getSalmedicamentos().trim());
			if (s.getSaltelemerg().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getSaltelemerg().trim());
			if (s.getSalperemerg().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getSalperemerg().trim());

			if (valores[0].equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++, Integer.parseInt(valores[0].trim()));
			if (valores[1].equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(valores[1].trim()));
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException se) {
			}
			setMensaje("Error SQL intentando ingresar información familiar. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public synchronized boolean insertar(AcademicaVO a) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			long est = Long.parseLong(a.getAcaEstudiante());
			long id = 1;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("AcademicaId"));
			pst.clearParameters();
			pst.setLong(posicion++, est);
			rs = pst.executeQuery();
			if (rs.next()) {
				id = rs.getLong(1);
			}
			rs.close();
			pst.close();
			posicion = 1;
			pst = cn.prepareStatement(rb.getString("AcademicaInsertar"));
			pst.clearParameters();
			pst.setLong(posicion++, est);
			pst.setLong(posicion++, id);
			if (a.getAcaInst().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, a.getAcaInst());
			if (a.getAcaGrado().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(a.getAcaGrado()));
			if (a.getAcaTitulo().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, a.getAcaTitulo());
			if (a.getAcaAnho().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(a.getAcaAnho()));
			if (a.getAcaGradoEstado().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(a.getAcaGradoEstado()));
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando ingresar información academica.("
					+ sqle.getErrorCode() + ") Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public synchronized boolean insertar(AsistenciaVO a) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			String est = (a.getAsiCodPer());
			long vigencia = getVigenciaNumerico();
			long id = 1;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("AsistenciaId"));
			pst.clearParameters();
			pst.setString(posicion++, a.getAsiTipoPer());
			pst.setString(posicion++, est);
			rs = pst.executeQuery();
			if (rs.next()) {
				id = rs.getLong(1);
			}
			rs.close();
			pst.close();
			posicion = 1;
			pst = cn.prepareStatement(rb.getString("AsistenciaInsertar"));
			pst.clearParameters();
			pst.setString(posicion++, a.getAsiTipoPer());
			pst.setString(posicion++, est);
			pst.setLong(posicion++, id);
			if (a.getAsiFecha().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setDate(posicion++,
						new java.sql.Date(sdf.parse(a.getAsiFecha()).getTime()));
			if (a.getAsiMotivo().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++, Integer.parseInt(a.getAsiMotivo()));
			if (a.getAsiObservacion().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, a.getAsiObservacion());
			if (a.getAsiJustificada().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(a.getAsiJustificada()));
			pst.setLong(posicion++, Long.parseLong(a.getAsiPeriodo()));
			pst.setLong(posicion++, vigencia);
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando ingresar información de asistencia.("
					+ sqle.getErrorCode() + ") Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} catch (java.text.ParseException e) {
			setMensaje("Error interpretando fecha:" + e.toString());
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * Inserta los datos del bean en la tabla de informacion basica del egresado
	 * 
	 * @param Cursor
	 *            cursor
	 * @param ActualizarNuevoBasica
	 *            basica
	 * @param int tipo
	 * @return boolean
	 */
	public boolean insertar(Basica basica, Login login) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		long grupo = 0;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("getGrupo"));
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(login.getInstId()));
			pst.setLong(posicion++, Long.parseLong(basica.getEstsede()));
			pst.setLong(posicion++, Long.parseLong(basica.getEstjor()));
			pst.setLong(posicion++, Long.parseLong(basica.getEstmet()));
			pst.setLong(posicion++, Long.parseLong(basica.getEstgra()));
			pst.setLong(posicion++, Long.parseLong(basica.getEstgru()));
			rs = pst.executeQuery();
			if (rs.next()) {
				grupo = rs.getLong(1);
			} else {
				setMensaje("Grupo no encontrado");
				return false;
			}
			rs.close();
			pst.close();
			posicion = 1;
			pst = cn.prepareStatement(rb.getString("BasicaInsertar"));
			pst.clearParameters();
			pst.setLong(posicion++, grupo);
			if (basica.getEstexpdoccoddep().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++,
						Integer.parseInt(basica.getEstexpdoccoddep().trim()));
			if (basica.getEstexpdoccodmun().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++,
						Integer.parseInt(basica.getEstexpdoccodmun().trim()));
			if (basica.getEsttipodoc().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++,
						Integer.parseInt(basica.getEsttipodoc().trim()));
			if (basica.getEstnumdoc().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++,
						Long.parseLong(basica.getEstnumdoc().trim()));
			if (basica.getEstnombre1().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, basica.getEstnombre1().trim());
			if (basica.getEstnombre2().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, basica.getEstnombre2().trim());
			if (basica.getEstapellido1().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, basica.getEstapellido1().trim());
			if (basica.getEstapellido2().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, basica.getEstapellido2().trim());
			if (basica.getEstfechanac().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setDate(posicion++,
						new java.sql.Date(sdf.parse(basica.getEstfechanac())
								.getTime()));
			if (basica.getEstlugnaccoddep().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++,
						Integer.parseInt(basica.getEstlugnaccoddep().trim()));
			if (basica.getEstlugnaccodmun().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++,
						Integer.parseInt(basica.getEstlugnaccodmun().trim()));
			if (basica.getEstedad().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++,
						Integer.parseInt(basica.getEstedad().trim()));
			if (basica.getEstgenero().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++,
						Integer.parseInt(basica.getEstgenero().trim()));
			if (basica.getEstetnia().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++,
						Integer.parseInt(basica.getEstetnia().trim()));
			if (basica.getEstcondicion().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++,
						Integer.parseInt(basica.getEstcondicion().trim()));
			if (basica.getEstcodconv().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, basica.getEstcodconv().trim());
			if (basica.getEstdireccion().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, basica.getEstdireccion().trim());
			if (basica.getEsttelefono().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, basica.getEsttelefono().trim());
			if (basica.getEstzona().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++,
						Integer.parseInt(basica.getEstzona().trim()));
			if (basica.getEstlocvereda().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, basica.getEstlocvereda().trim());
			if (basica.getEstbarcorreg().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, basica.getEstbarcorreg().trim());
			if (basica.getEstestrato().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++,
						Integer.parseInt(basica.getEstestrato().trim()));
			if (basica.getEstsisben().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++,
						Integer.parseInt(basica.getEstsisben().trim()));
			if (basica.getEstcodfamilia().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++,
						Integer.parseInt(basica.getEstcodfamilia().trim()));
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error SQL intentando ingresar información bnsica. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} catch (java.text.ParseException e) {
			setMensaje("Error interpretando fecha: " + e.toString());
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * Inserta los datos del bean en la tabla de informacion basica del egresado
	 * 
	 * @param Cursor
	 *            cursor
	 * @param ActualizarNuevoBasica
	 *            basica
	 * @param int tipo
	 * @return boolean
	 */
	public boolean insertar(Familiar f) {
		
		int posicion = 1; // posicion inicial de llenado del preparedstatement
		Connection cn = null;
		PreparedStatement pst = null;

		try {
			
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			
			pst = cn.prepareStatement(rb.getString("FamiliarInsertar"));
			pst.clearParameters();
			
			// pst.setInt(posicion++, 2);
			if (f.getFamnommadre().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getFamnommadre().trim());
			
			if (f.getFamtipdocmadre().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++, Integer.parseInt(f.getFamtipdocmadre().trim()));
			
			if (f.getFamnumdocmadre().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getFamnumdocmadre().trim());
			
			if (f.getFamocumadre().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getFamocumadre().trim());
			
			if (f.getFamdirmadre().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getFamdirmadre().trim());
			
			if (f.getFamtelmadre().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getFamtelmadre().trim());
			
			if (f.getFamnompadre().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getFamnompadre().trim());
			
			if (f.getFamtipdocpadre().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++, Integer.parseInt(f.getFamtipdocpadre().trim()));
			
			if (f.getFamnumdocpadre().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getFamnumdocpadre().trim());
			
			if (f.getFamocupadre().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getFamocupadre().trim());
			
			if (f.getFamdirpadre().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getFamdirpadre().trim());
			
			if (f.getFamtelpadre().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getFamtelpadre().trim());
			
			if (f.getFamnomacudi().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getFamnomacudi().trim());
			
			if (f.getFamtipdocacudi().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++, Integer.parseInt(f.getFamtipdocacudi().trim()));
			
			if (f.getFamnumdocacudi().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getFamnumdocacudi().trim());
			
			if (f.getFamocuacudi().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getFamocuacudi().trim());
			
			if (f.getFamdiracudi().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getFamdiracudi().trim());
			
			if (f.getFamtelacudi().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getFamtelacudi().trim());
			
			if (f.getFamnumhermanos().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++, Integer.parseInt(f.getFamnumhermanos().trim()));
			
			if (f.getFamnomhijos().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getFamnomhijos().trim());
			
			pst.executeUpdate();
			
			cn.commit();
			cn.setAutoCommit(true);
			
		} catch (InternalErrorException in) {
			setMensaje("NO se puede establecer conexión con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error SQL intentando ingresar información familiar. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		
		return true;
		
	}
	
	
	
	
	
	/**
	 * Inserta los datos del bean en la tabla de informacion basica del egresado
	 * 
	 * @param Cursor
	 *            cursor
	 * @param ActualizarNuevoBasica
	 *            basica
	 * @param int tipo
	 * @return boolean
	 */
	public boolean insertar(Salud s) {
		int posicion = 1; // posicion inicial de llenado del preparedstatement
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("SaludInsertar"));
			pst.clearParameters();
			if (s.getSaltipoperso().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++,
						Integer.parseInt(s.getSaltipoperso().trim()));
			if (s.getSalcodperso().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++,
						Long.parseLong(s.getSalcodperso().trim()));
			if (s.getSaltiposangre().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getSaltiposangre().trim());
			if (s.getSaleps().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getSaleps().trim());
			if (s.getSalars().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getSalars().trim());
			if (s.getSalalergias().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getSalalergias().trim());
			if (s.getSalenfermedades().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getSalenfermedades().trim());
			if (s.getSalmedicamentos().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getSalmedicamentos().trim());
			if (s.getSaltelemerg().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getSaltelemerg().trim());
			if (s.getSalperemerg().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getSalperemerg().trim());
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException se) {
			}
			setMensaje("Error SQL intentando ingresar información de salud. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean insertar(AtencionEspecial ae) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("AtencionInsertar"));
			pst.clearParameters();

			if (ae.getAtescodest().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++,
						Long.parseLong(ae.getAtescodest().trim()));
			if (ae.getAtestipo().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setInt(posicion++,
						Integer.parseInt(ae.getAtestipo().trim()));
			if (ae.getAtesperiodo().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setInt(posicion++,
						Integer.parseInt(ae.getAtesperiodo().trim()));
			if (ae.getAtesfecha().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setString(posicion++, ae.getAtesfecha().trim());
			if (ae.getAtesasunto().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setString(posicion++, ae.getAtesasunto().trim());
			if (ae.getAtesdescripcion().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setString(posicion++, ae.getAtesdescripcion().trim());
			if (ae.getAtesmostrar().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setInt(posicion++,
						Integer.parseInt(ae.getAtesmostrar().trim()));
			if (ae.getUsuario().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++, Long.parseLong(ae.getUsuario().trim()));
			pst.setTimestamp(posicion++,
					new Timestamp(System.currentTimeMillis()));

			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando ingresar información de atencion especial.("
					+ sqle.getErrorCode() + ") Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;

	}

	/**
	 * Inserta los datos del bean en la tabla de informacion basica del egresado
	 * 
	 * @param Cursor
	 *            cursor
	 * @param ActualizarNuevoBasica
	 *            basica
	 * @param int tipo
	 * @return boolean
	 */
	public boolean insertar(Convivencia c) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("ConvivenciaInsertar"));
			pst.clearParameters();
			if (c.getConcodinst().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++,
						Long.parseLong(c.getConcodinst().trim()));
			if (c.getContipoperso().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++,
						Integer.parseInt(c.getContipoperso().trim()));
			if (c.getConcodperso().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, c.getConcodperso().trim());
			if (c.getContipo().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++, Integer.parseInt(c.getContipo().trim()));
			if (c.getCondescripcion().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, c.getCondescripcion().trim());
			if (c.getConfecha().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setDate(posicion++,
						new java.sql.Date(sdf.parse(c.getConfecha()).getTime()));
			if (c.getConacuerdos().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, c.getConacuerdos().trim());
			if (c.getConseguimiento().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, c.getConseguimiento().trim());
			if (c.getPeriodo().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++, Integer.parseInt(c.getPeriodo().trim()));
			if (c.getIdClase().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getIdClase().trim()));
			if (c.getIdTipo().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getIdTipo().trim()));
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando ingresar información de convivencia.("
					+ sqle.getErrorCode() + ") Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} catch (java.text.ParseException e) {
			setMensaje("Error interpretando fecha:" + e.toString());
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean actualizar(AcademicaVO a) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("AcademicaActualizar"));
			pst.clearParameters();
			if (a.getAcaInst().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, a.getAcaInst());
			if (a.getAcaGrado().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(a.getAcaGrado()));
			if (a.getAcaTitulo().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, a.getAcaTitulo());
			if (a.getAcaAnho().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(a.getAcaAnho()));
			if (a.getAcaGradoEstado().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(a.getAcaGradoEstado()));
			// where
			pst.setLong(posicion++, Long.parseLong(a.getAcaEstudiante()));
			pst.setLong(posicion++, Long.parseLong(a.getAcaId()));
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando actualizar información de convivencia.("
					+ sqle.getErrorCode() + ") Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean actualizar(AsistenciaVO a) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			long vigencia = getVigenciaNumerico();
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("AsistenciaActualizar"));
			pst.clearParameters();
			if (a.getAsiFecha().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setDate(posicion++,
						new java.sql.Date(sdf.parse(a.getAsiFecha()).getTime()));
			if (a.getAsiMotivo().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++, Integer.parseInt(a.getAsiMotivo()));
			if (a.getAsiObservacion().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, a.getAsiObservacion());
			if (a.getAsiJustificada().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(a.getAsiJustificada()));
			pst.setLong(posicion++, Long.parseLong(a.getAsiPeriodo()));
			// where
			pst.setString(posicion++, a.getAsiTipoPer());
			pst.setString(posicion++, a.getAsiCodPer());
			pst.setLong(posicion++, Long.parseLong(a.getAsiCodigo()));
			pst.setLong(posicion++, vigencia);
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando actualizar información de asistencia.("
					+ sqle.getErrorCode() + ") Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} catch (java.text.ParseException e) {
			setMensaje("Error interpretando fecha:" + e.toString());
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean actualizar(AtencionEspecial ae) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("AtencionActualizar"));
			pst.clearParameters();

			if (ae.getAtestipo().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setInt(posicion++,
						Integer.parseInt(ae.getAtestipo().trim()));
			if (ae.getAtesperiodo().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setInt(posicion++,
						Integer.parseInt(ae.getAtesperiodo().trim()));
			if (ae.getAtesfecha().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setString(posicion++, ae.getAtesfecha().trim());
			if (ae.getAtesasunto().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setString(posicion++, ae.getAtesasunto().trim());
			if (ae.getAtesdescripcion().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setString(posicion++, ae.getAtesdescripcion().trim());
			if (ae.getAtesmostrar().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setInt(posicion++,
						Integer.parseInt(ae.getAtesmostrar().trim()));
			if (ae.getUsuario().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++, Long.parseLong(ae.getUsuario().trim()));
			pst.setTimestamp(posicion++,
					new Timestamp(System.currentTimeMillis()));

			if (ae.getAtesid().equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++, Long.parseLong(ae.getAtesid().trim()));
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando actualizar información de convivencia.("
					+ sqle.getErrorCode() + ") Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: Actualizar Convivencia<br>
	 * 
	 * @param Convivencia
	 *            c
	 * @return boolean
	 **/
	public boolean actualizar(Convivencia c) {
		int posicion = 1; // posicion inicial de llenado del preparedstatement
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("ConvivenciaActualizar"));
			pst.clearParameters();
			if (c.getConcodinst().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++,
						Long.parseLong(c.getConcodinst().trim()));
			if (c.getContipoperso().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++,
						Integer.parseInt(c.getContipoperso().trim()));
			if (c.getConcodperso().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, c.getConcodperso().trim());
			if (c.getContipo().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++, Integer.parseInt(c.getContipo().trim()));
			if (c.getCondescripcion().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, c.getCondescripcion().trim());
			if (c.getConfecha().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setDate(posicion++,
						new java.sql.Date(sdf.parse(c.getConfecha()).getTime()));
			if (c.getConacuerdos().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, c.getConacuerdos().trim());
			if (c.getConseguimiento().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, c.getConseguimiento().trim());
			if (c.getPeriodo().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++, Integer.parseInt(c.getPeriodo().trim()));
			if (c.getIdClase().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getIdClase().trim()));
			if (c.getIdTipo().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getIdTipo().trim()));
			// WHERE
			if (c.getConcodigo().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getConcodigo().trim()));
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando actualizar información de convivencia.("
					+ sqle.getErrorCode() + ") Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} catch (java.text.ParseException e) {
			setMensaje("Error interpretando fecha:" + e.toString());
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public String getCodigoEstudiante(String tipo, String num) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String codigo = null;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("codigoEstudiante"));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(tipo));
			ps.setString(posicion++, (num));
			rs = ps.executeQuery();
			if (rs.next()) {
				codigo = rs.getString(1);
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
			setMensaje("Error intentando obtener codigo Estudiante. Posible problema: ");
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
		return codigo;
	}

	public boolean getEstudiante(String tipo, String num) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("idEstudiante"));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(tipo));
			ps.setString(posicion++, (num));
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
			rs.close();
			ps.close();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener Estudiante. Posible problema: ");
			System.out
					.println("Error intentando obtener Estudiante. Posible problema: "
							+ sqle);
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return false;
	}

	public AcademicaVO asignarAcademica(Basica basica, String id) {
		AcademicaVO a = null;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet r = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("AcademicaAsignar"));
			pst.clearParameters();
			pst.setString(posicion++, (basica.getEstcodigo()));
			pst.setLong(posicion++, Long.parseLong(id));
			r = pst.executeQuery();
			if (r.next()) {
				a = new AcademicaVO();
				int i = 1;
				a.setAcaEstudiante(r.getString(i++));
				a.setAcaId(r.getString(i++));
				a.setAcaInst(r.getString(i++));
				a.setAcaGrado(r.getString(i++));
				a.setAcaTitulo(r.getString(i++));
				a.setAcaAnho(r.getString(i++));
				a.setAcaGradoEstado(r.getString(i++));
				a.setAcaEstado("1");
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			setMensaje("Error SQL intentando asignar academica. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return a;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return a;
	}

	public boolean eliminarAcademica(Basica b, String id) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("AcademicaEliminar"));
			pst.clearParameters();
			pst.setString(posicion++, (b.getEstcodigo()));
			pst.setLong(posicion++, Long.parseLong(id));
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error SQL intentando ELIMINAR academica. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public Collection getAcademicas(Basica b) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection list = null;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("listaAcademicas"));
			posicion = 1;
			ps.setString(posicion++, (b.getEstcodigo()));
			rs = ps.executeQuery();
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
			setMensaje("Error intentando obtener información acadnmica. Posible problema: ");
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

	public AsistenciaVO asignarAsistencia(Basica basica, String tipo, String id) {
		AsistenciaVO a = null;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet r = null;
		int posicion = 1;
		try {
			long vigencia = getVigenciaNumerico();
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("AsistenciaAsignar"));
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(tipo));
			pst.setString(posicion++, (basica.getEstcodigo()));
			pst.setLong(posicion++, Long.parseLong(id));
			pst.setLong(posicion++, vigencia);
			r = pst.executeQuery();
			if (r.next()) {
				a = new AsistenciaVO();
				int i = 1;
				a.setAsiTipoPer(r.getString(i++));
				a.setAsiCodPer(r.getString(i++));
				a.setAsiCodigo(r.getString(i++));
				a.setAsiFecha(r.getString(i++));
				a.setAsiMotivo(r.getString(i++));
				a.setAsiObservacion(r.getString(i++));
				a.setAsiJustificada(r.getString(i++));
				a.setAsiPeriodo(r.getString(i++));
				a.setAsiEstado("1");
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			setMensaje("Error SQL intentando asignar asistencia. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return a;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return a;
	}

	public boolean eliminarAsistencia(Basica b, String tipo, String id) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			long vigencia = getVigenciaNumerico();
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("AsistenciaEliminar"));
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(tipo));
			pst.setString(posicion++, (b.getEstcodigo()));
			pst.setLong(posicion++, Long.parseLong(id));
			pst.setLong(posicion++, vigencia);
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error SQL intentando ELIMINAR asistencia. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public Collection getComboMotivo() {
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection list = null;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("getComboMotivo"));
			rs = ps.executeQuery();
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
			setMensaje("Error intentando obtener Lista de motivos de insasitencia. Posible problema: ");
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

	public Collection getAsistencias(String tipo, Basica b) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection list = null;
		try {
			long vigencia = getVigenciaNumerico();
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("listaAsistencias"));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(tipo));
			ps.setString(posicion++, (b.getEstcodigo()));
			ps.setLong(posicion++, vigencia);
			rs = ps.executeQuery();
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
			setMensaje("Error intentando obtener información acadnmica. Posible problema: ");
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

	// REPORTE

	// OBTENER DANE INST
	public Long getDaneInstitucion(Long codigoInstitucion) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		Long daneInstitucion = new Long(0);

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb.getString("getDaneInstitucion"));
			posicion = 1;
			pst.clearParameters();
			pst.setLong(posicion++, codigoInstitucion.longValue());
			rs = pst.executeQuery();
			if (rs.next())
				daneInstitucion = new Long(rs.getLong(1));
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
		} catch (SQLException sqle) {
			try {
				con.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando getDaneInstitucion");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return daneInstitucion;
	}

	// FUNCION PARA VERIFICAR DATOS EN REPORTE ESTUDIANTES POR GRUPO
	public boolean isEmptyEstXGrupo(FiltroBean filtro) throws Exception {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			con = cursor.getConnection();
			rs = null;
			pst = con.prepareStatement(rb.getString("consultaVacia"));
			posicion = 1;
			pst.setLong(posicion++, filtro.getInstitucion().longValue());
			pst.setLong(posicion++, Long.parseLong(filtro.getSede()));
			pst.setLong(posicion++, Long.parseLong(filtro.getJornada()));
			pst.setLong(posicion++, Long.parseLong(filtro.getGrado()));
			pst.setLong(posicion++, Long.parseLong(filtro.getGrado()));
			pst.setLong(posicion++, Long.parseLong(filtro.getGrupo()));
			pst.setLong(posicion++, Long.parseLong(filtro.getGrupo()));
			rs = pst.executeQuery();
			if (rs.next())
				return false;

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception sqle) {
			sqle.printStackTrace();
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(con);
		}
		return true;
	}

	/**
	 * Funcinn: Pone el reporte generado por el reporteador en la tabla de
	 * reportes <BR>
	 * 
	 * @param String
	 *            us
	 * @param String
	 *            rec
	 * @param String
	 *            tipo
	 * @param String
	 *            nombre
	 * @param String
	 *            prepared
	 **/

	public void ponerReporte(String modulo, String us, String rec, String tipo,	String nombre, String estado, String prepared) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb.getString(prepared));
			// System.out.println("rb.getString(prepared) "
			// + rb.getString(prepared));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (estado));
			//pst.setString(posicion++, (mensaje));
			// System.out.println("mensaje " + mensaje);
			pst.setString(posicion++, (us));
			// System.out.println("us " + us);
			pst.setString(posicion++, (rec));
			// System.out.println("rec " + rec);
			pst.setString(posicion++, (tipo));
			// System.out.println("tipo " + tipo);
			pst.setString(posicion++, (nombre));
			// System.out.println("nombre " + nombre);
			pst.setString(posicion++, (modulo));
			// System.out.println("modulo " + modulo);
			int i = pst.executeUpdate();
			pst.close();
			con.commit();
			con.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
		} catch (SQLException sqle) {
			try {
				con.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando ponerReporte: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
	}

	/**
	 * Funcinn: Pone el reporte generado por el reporteador en la tabla de
	 * reportes <BR>
	 * 
	 * @param String
	 *            us
	 * @param String
	 *            rec
	 * @param String
	 *            tipo
	 * @param String
	 *            nombre
	 * @param String
	 *            prepared
	 **/

	public boolean ponerReporteMensaje(String estado, String modulo, String us,
			String rec, String tipo, String nombre, String prepared,
			String mensaje) {

		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb.getString(prepared));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (estado));
			pst.setString(posicion++, (mensaje));
			pst.setString(posicion++, (us));
			pst.setString(posicion++, (rec));
			pst.setString(posicion++, (tipo));
			pst.setString(posicion++, (nombre));
			pst.setString(posicion++, (modulo));
			pst.close();
			con.commit();
			con.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				con.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando ponerReporteMensaje: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: Pone el reporte generado por el reporteador en la tabla de
	 * reportes <BR>
	 * 
	 * @param String
	 *            us
	 * @param String
	 *            rec
	 * @param String
	 *            tipo
	 * @param String
	 *            nombre
	 * @param String
	 *            prepared
	 **/

	public void updateReporte(String modulo, String nombrereporte, String user,
			String estado, String prepared) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb.getString(prepared));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (modulo));
			pst.setString(posicion++, (nombrereporte));
			pst.setString(posicion++, (user));
			pst.setLong(posicion++, Long.parseLong(estado));
			pst.executeUpdate();
			pst.close();
			con.commit();
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Funcinn: Pone el reporte generado por el reporteador en la tabla de
	 * reportes <BR>
	 * 
	 * @param String
	 *            us
	 * @param String
	 *            rec
	 * @param String
	 *            tipo
	 * @param String
	 *            nombre
	 * @param String
	 *            prepared
	 **/

	public void ponerReporteMensajeListo(String modulo, String us, String rec,
			String tipo, String nombre, String prepared) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb.getString(prepared));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (us));
			pst.setString(posicion++, (rec));
			pst.setString(posicion++, (tipo));
			pst.setString(posicion++, (nombre));
			pst.setString(posicion++, (modulo));
			pst.executeUpdate();
			pst.close();
			con.commit();
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Funcinn: Pone el reporte generado por el reporteador en la tabla de
	 * reportes, y luego poder ser visualizado en <BR>
	 * la lista de reportes generados <BR>
	 * 
	 * @param byte bit
	 **/

	public void ponerArchivo(byte[] bit, String archivostatic, String context) {

		Connection con = null;
		try {
			con = cursor.getConnection();
			String archivosalida = Ruta.get(context,
					rb.getString("reportes.PathReporte"));
			File f = new File(archivosalida);
			if (!f.exists())
				FileUtils.forceMkdir(f);
			FileOutputStream fileOut = new FileOutputStream(f + File.separator
					+ archivostatic);
			CopyUtils.copy(bit, fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeConnection(con);
			} catch (InternalErrorException inte) {
			}
		}
	}

	public boolean isWorking(String idUsuario, String modulo) throws Exception {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb.getString("isWorking"));
			posicion = 1;
			pst.setString(posicion++, idUsuario);
			pst.setString(posicion++, modulo);
			rs = pst.executeQuery();
			if (rs.next())
				return true;

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception sqle) {
			sqle.printStackTrace();
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(con);
		}
		return false;
	}

	/**
	 * Funcinn: Ejecuta los prepared statements correspondientes para actualzar
	 * el estado y ponerlo en la cola<BR>
	 * 
	 * @param String
	 * @param String
	 * @param String
	 * @param String
	 **/

	public boolean ejecutarJasper(String usuarioId) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb.getString("queryEjecutarjasper"));
			posicion = 1;
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(usuarioId));
			rs = pst.executeQuery();

			if (!rs.next())
				return false;
			rs.close();
			pst.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return true;
	}

	public void updateReporteFecha(String preparedstatement, String modulo,
			String nombreboletin, String user, String estado) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb.getString(preparedstatement));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (modulo));
			pst.setString(posicion++, (nombreboletin));
			pst.setString(posicion++, (user));
			pst.setLong(posicion++, Long.parseLong(estado));
			pst.executeUpdate();
			pst.close();
			con.commit();
			// System.out
			// .println("nnSe actualizn la fecha en la tabla Reporte!!!!");

		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	public void updateReporteEstado(String modulo, String us, String rec,
			String tipo, String nombre, String prepared) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;
		// System.out.println("updateReporteEstado ");

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb.getString(prepared));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (us));
			// System.out.println("us " + us);
			pst.setString(posicion++, (rec));
			// System.out.println("rec " + rec);
			pst.setString(posicion++, (tipo));
			// System.out.println("tipo " + tipo);
			pst.setString(posicion++, (nombre));
			// System.out.println("nombre " + nombre);
			pst.setString(posicion++, (modulo));
			// System.out.println("modulo " + modulo);
			pst.executeUpdate();
			con.commit();
			pst.close();
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Funcinn: Ejecuta los prepared statements correspondientes para actualzar
	 * el estado y ponerlo en la cola<BR>
	 * 
	 * @param String
	 * @param String
	 * @param String
	 * @param String
	 **/
	public byte[] getArrayBytes(Long idInstitucion, String idUsuario,
			String nombreReporteZip, File reportFile, Map parameterscopy,
			String modulo) {
		byte[] bytes = null;
		Connection con = null;
		try {
			con = cursor.getConnection();
			if ((reportFile.getPath() != null) && (parameterscopy != null)
					&& (!parameterscopy.values().equals("0")) && (con != null)) {
				// System.out.println("***Se mandn ejecutar el jasper****");
				bytes = JasperRunManager.runReportToPdf(reportFile.getPath(),
						parameterscopy, con);
			}
		} catch (JRException e) {
			e.printStackTrace();
			ponerReporteMensaje("2", modulo, idUsuario,
					rb.getString("reportes.PathReportes") + nombreReporteZip
							+ "", "zip", "" + nombreReporteZip,
					"ReporteActualizarBoletinGenerando",
					"Ocurrio excepcion en el Jasper:_" + e);
			updateReporte(modulo, nombreReporteZip, idUsuario, "2",
					"update_reporte");
			siges.util.Logger.print(idUsuario,
					"Excepcinn al generar el reporte:_Institucion:_"
							+ idInstitucion + "_Usuario:_" + idUsuario
							+ "_NombreReporte:_" + nombreReporteZip + "", 3, 1,
					this.toString());
		} catch (java.lang.OutOfMemoryError e) {
			e.printStackTrace();
			// System.out.println(" WARNING: nMemoria Insuficiente para generar el reporte solicitado! : "+e.toString());
			ponerReporteMensaje("2", modulo, idUsuario,
					rb.getString("reportes.PathReportes") + nombreReporteZip
							+ "", "zip", "" + nombreReporteZip,
					"ReporteActualizarBoletinGenerando",
					"Memoria insuficiente para generar el reporte:_" + e);
			updateReporte(modulo, nombreReporteZip, idUsuario, "2",
					"update_reporte");
			siges.util.Logger.print(String.valueOf(idUsuario),
					"Excepcinn al generar el reporte:_Institucion:_"
							+ idInstitucion + "_Usuario:_" + idUsuario
							+ "_NombreReporte:_" + nombreReporteZip + "", 3, 1,
					this.toString());
		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje("2", modulo, idUsuario,
					rb.getString("reportes.PathReportes") + nombreReporteZip
							+ "", "zip", "" + nombreReporteZip,
					"ReporteActualizarBoletinGenerando", "Ocurrio Exception:_"
							+ e);
			updateReporte(modulo, nombreReporteZip, idUsuario, "2",
					"update_reporte");
			siges.util.Logger.print(idUsuario,
					"Excepcinn al generar el reporte:_Institucion:_"
							+ idInstitucion + "_Usuario:_" + idUsuario
							+ "_NombreReporte:_" + nombreReporteZip + "", 3, 1,
					this.toString());
		} finally {
			try {
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return bytes;
	}

	public String getArchivo(String idUsuario, String nombreReporteZip,
			File reportFile, Map parameterscopy, String contextoTotal,
			String archivo) {
		Connection con = null;
		String archivosalidaExcel = null;
		String archivoCompleto = null;
		try {
			con = cursor.getConnection();
			if ((reportFile.getPath() != null) && (parameterscopy != null)
					&& (!parameterscopy.values().equals("0")) && (con != null)) {
				archivosalidaExcel = Ruta.get(contextoTotal,
						rb.getString("reportes.PathReporte"));
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						reportFile.getPath(), parameterscopy, con);
				String xlsFileName = archivo;
				String xlsFilesSource = archivosalidaExcel;
				archivoCompleto = xlsFilesSource + xlsFileName;
				File f = new File(xlsFilesSource);
				if (!f.exists())
					FileUtils.forceMkdir(f);
				// USANDO API EXCEL
				JExcelApiExporter xslExporter = new JExcelApiExporter();
				xslExporter.setParameter(JRExporterParameter.JASPER_PRINT,
						jasperPrint);
				xslExporter.setParameter(
						JExcelApiExporterParameter.IS_ONE_PAGE_PER_SHEET,
						Boolean.FALSE);
				xslExporter.setParameter(
						JExcelApiExporterParameter.OUTPUT_FILE_NAME,
						xlsFilesSource + xlsFileName);
				xslExporter.exportReport();
			}
		} catch (JRException e) {
			e.printStackTrace();
		} catch (java.lang.OutOfMemoryError e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return archivoCompleto;
	}

	/**
	 * @function: Consulta que me trae el listado de estudiante segun el filtro
	 *            que se le pase como parametro
	 * @param consulta
	 * @param lista
	 * @return
	 * @throws InternalErrorException
	 */
	public Collection getFiltroEstudiante(String consulta, Collection lista)
			throws InternalErrorException {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(consulta);
			pst.clearParameters();
			Iterator iterator = lista.iterator();
			int posicion = 1;
			while (iterator.hasNext()) {
				Object[] fila = (Object[]) iterator.next();
				switch (((Integer) fila[0]).intValue()) {
				case java.sql.Types.VARCHAR:
					pst.setString(posicion++, ((String) fila[1]).trim());
					break;
				case java.sql.Types.INTEGER:
					pst.setInt(posicion++,
							Integer.parseInt(((String) fila[1]).trim()));
					break;
				case java.sql.Types.DATE:
					pst.setDate(posicion++,
							new java.sql.Date(sdf.parse((String) fila[1])
									.getTime()));
					break;
				case java.sql.Types.NULL:
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
					break;
				case java.sql.Types.DOUBLE:
					pst.setLong(posicion++, (((Long) fila[1])).longValue());
					break;
				case java.sql.Types.CHAR:
					pst.setString(posicion++, ((String) fila[1]).trim());
					break;
				case java.sql.Types.BIGINT:
					pst.setLong(posicion++,
							Long.parseLong(((String) fila[1]).trim()));
					break;
				}
			}
			rs = pst.executeQuery();
			int m = rs.getMetaData().getColumnCount();
			Collection list = new ArrayList();
			Object[] o;
			int i = 0;
			while (rs.next()) {
				o = new Object[m];
				for (i = 1; i <= m; i++)
					o[i - 1] = rs.getString(i);
				list.add(o);
			}
			return list;
		} catch (SQLException e) {
			throw new InternalErrorException(e);
		} catch (java.text.ParseException e) {
			throw new InternalErrorException(e);
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
			cursor.cerrar();
		}
	}

	/**
	 * @function: Consulta que me trae el listado de estudiante segun el filtro
	 *            que se le pase como parametro
	 * @param consulta
	 * @param lista
	 * @return
	 * @throws InternalErrorException
	 */
	public boolean isFiltroEstudiante(String consulta, Collection lista)
			throws InternalErrorException {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		boolean band = false;
		try {
			cn = cursor.getConnection();
			// System.out.println("FILTRO DE BUSQUEDA HOJA DE VIDA: " +
			// consulta);
			pst = cn.prepareStatement(consulta);
			pst.clearParameters();
			Iterator iterator = lista.iterator();
			int posicion = 1;
			while (iterator.hasNext()) {

				Object[] fila = (Object[]) iterator.next();

				switch (((Integer) fila[0]).intValue()) {
				case java.sql.Types.VARCHAR:
					pst.setString(posicion++, ((String) fila[1]).trim());
					break;
				case java.sql.Types.INTEGER:
					pst.setInt(posicion++,
							Integer.parseInt(((String) fila[1]).trim()));
					break;
				case java.sql.Types.DATE:
					pst.setDate(posicion++,
							new java.sql.Date(sdf.parse((String) fila[1])
									.getTime()));
					break;
				case java.sql.Types.NULL:
					pst.setNull(posicion++, java.sql.Types.VARCHAR);
					break;
				case java.sql.Types.DOUBLE:
					pst.setLong(posicion++, (((Long) fila[1])).longValue());
					break;
				case java.sql.Types.CHAR:
					pst.setString(posicion++, ((String) fila[1]).trim());
					break;
				case java.sql.Types.BIGINT:
					pst.setLong(posicion++,
							Long.parseLong(((String) fila[1]).trim()));
					break;
				}
			}
			rs = pst.executeQuery();

			if (rs.next()) {
				return true;
			}

		} catch (SQLException e) {
			throw new InternalErrorException(e);
		} catch (java.text.ParseException e) {
			throw new InternalErrorException(e);
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
			cursor.cerrar();
		}
		return band;
	}

	/**
	 * @function: Evalua si existe registro con los parametros del filtro que
	 *            recibe como parametro.
	 * @param filtro
	 * @return
	 * @throws Exception
	 */
	public boolean isEmptyEstXGrupo2(FiltroBean filtro) throws Exception {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		boolean band = true;
		try {
			con = cursor.getConnection();
			rs = null;
			pst = con.prepareStatement(rb.getString("consultaVacia"));
			posicion = 1;

			pst.setLong(posicion++, filtro.getInstitucion().longValue());
			pst.setLong(posicion++, Long.parseLong(filtro.getSede()));
			pst.setLong(posicion++, Long.parseLong(filtro.getSede()));
			pst.setLong(posicion++, Long.parseLong(filtro.getJornada()));
			pst.setLong(posicion++, Long.parseLong(filtro.getJornada()));

			pst.setLong(posicion++, Long.parseLong(filtro.getGrado()));
			pst.setLong(posicion++, Long.parseLong(filtro.getGrado()));
			pst.setLong(posicion++, Long.parseLong(filtro.getGrupo()));
			pst.setLong(posicion++, Long.parseLong(filtro.getGrupo()));

			if (filtro.getNombre1() != null
					&& filtro.getNombre1().trim().length() > 0) {
				pst.setString(posicion++, filtro.getNombre1());
			} else {
				pst.setString(posicion++, "-99");
			}
			pst.setString(posicion++, filtro.getNombre1());

			if (filtro.getNombre2() != null
					&& filtro.getNombre2().trim().length() > 0) {
				pst.setString(posicion++, filtro.getNombre2());
			} else {
				pst.setString(posicion++, "-99");
			}
			pst.setString(posicion++, filtro.getNombre2());

			if (filtro.getId() != null
					&& filtro.getApellido1().trim().length() > 0) {
				pst.setString(posicion++, filtro.getApellido1());
			} else {
				pst.setString(posicion++, "-99");
			}
			pst.setString(posicion++, filtro.getApellido1());

			if (filtro.getId() != null
					&& filtro.getApellido2().trim().length() > 0) {
				pst.setString(posicion++, filtro.getApellido2());
			} else {
				pst.setString(posicion++, "-99");
			}
			pst.setString(posicion++, filtro.getApellido2());

			if (filtro.getId() != null && filtro.getId().trim().length() > 0) {
				pst.setString(posicion++, filtro.getId());
			} else {
				pst.setString(posicion++, "-99");
			}
			pst.setString(posicion++, filtro.getId());

			rs = pst.executeQuery();
			while (rs.next()) {
				band = false;
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception sqle) {
			sqle.printStackTrace();
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(con);
		}
		return band;
	}

	/**
	 * @function: Llama el procedimiento que llena la tabla REP_HOJA_VIDA
	 * @param filtro
	 * @return
	 * @throws Exception
	 */
	public boolean callHojaVida(FiltroBean filtro) throws Exception {
		// System.out.println("filtro.getOrden() +++++++++++++++++++++ "
		// + filtro.getOrden());
		// System.out.println("callHojaVida +++");
		Connection con = null;
		CallableStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		boolean band = true;
		try {
			con = cursor.getConnection();
			rs = null;
			pst = con.prepareCall(rb.getString("callHojaVida"));
			posicion = 1;
			pst.setLong(posicion++, filtro.getInstitucion().longValue());
			pst.setString(posicion++, filtro.getNomInst());
			pst.setLong(posicion++, Long.parseLong(filtro.getSede()));
			pst.setLong(posicion++, Long.parseLong(filtro.getJornada()));
			pst.setLong(posicion++, Long.parseLong(filtro.getGrado()));
			pst.setLong(posicion++, Long.parseLong(filtro.getMetodologia()));
			pst.setLong(posicion++, Long.parseLong(filtro.getGrupo()));

			if (filtro.getApellido1() != null
					&& filtro.getApellido1().trim().length() > 0) {
				pst.setString(posicion++, filtro.getApellido1());
			} else {
				pst.setString(posicion++, "-99");
			}

			if (filtro.getApellido2() != null
					&& filtro.getApellido2().trim().length() > 0) {
				pst.setString(posicion++, filtro.getApellido2());
			} else {
				pst.setString(posicion++, "-99");
			}

			if (filtro.getNombre1() != null
					&& filtro.getNombre1().trim().length() > 0) {
				pst.setString(posicion++, filtro.getNombre1());
			} else {
				pst.setString(posicion++, "-99");
			}

			if (filtro.getNombre2() != null
					&& filtro.getNombre2().trim().length() > 0) {
				pst.setString(posicion++, filtro.getNombre2());
			} else {
				pst.setString(posicion++, "-99");
			}

			pst.setString(posicion++, "-99");

			if (filtro.getId() != null && filtro.getId().trim().length() > 0) {
				pst.setString(posicion++, filtro.getId());
			} else {
				pst.setString(posicion++, "-99");
			}

			pst.setString(posicion++, filtro.getUsuario());
			pst.setString(posicion++, filtro.getFecha());

			pst.executeUpdate();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle);
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle);
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeCallableStatement(pst);
			OperacionesGenerales.closeConnection(con);
		}
		return band;
	}

	/**
	 * @function:
	 * @param filtro
	 * @return
	 * @throws Exception
	 */
	public boolean borrarHojaVida(FiltroBean filtro) throws Exception {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;
		boolean band = true;
		try {
			con = cursor.getConnection();

			pst = con.prepareCall(rb.getString("hojaVida.borrarHojaVida"));
			posicion = 1;

			pst.setString(posicion++, filtro.getUsuario());
			pst.setString(posicion++, filtro.getFecha());
			int iI = pst.executeUpdate();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle);
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle);
		} finally {
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(con);
		}
		return band;
	}

	public Collection getBuscarEstudiante(FiltroBean filtro)
			throws InternalErrorException {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		long codInst = filtro.getInstitucion().longValue();
		String codSede = (filtro.getSede() != null && filtro.getSede().trim()
				.length() > 0) ? filtro.getSede().trim() : "-99";
		String codJorn = (filtro.getJornada() != null && filtro.getJornada()
				.trim().length() > 0) ? filtro.getJornada().trim() : "-9";
		String codGrad = (filtro.getGrado() != null && filtro.getGrado().trim()
				.length() > 0) ? filtro.getGrado().trim() : "-9";
		String codGrup = (filtro.getGrupo() != null && filtro.getGrupo().trim()
				.length() > 0) ? filtro.getGrupo().trim() : "-9";
		String codMetd = (filtro.getMetodologia() != null && filtro
				.getMetodologia().trim().length() > 0) ? filtro
				.getMetodologia().trim() : "-9";
		String numDoc = (filtro.getId() != null && filtro.getId().trim()
				.length() > 0) ? filtro.getId().trim() : "-9";
		String nom1 = (filtro.getNombre1() != null && filtro.getNombre1()
				.trim().length() > 0) ? filtro.getNombre1().trim() : "-9";
		String nom2 = (filtro.getNombre2() != null && filtro.getNombre2()
				.trim().length() > 0) ? filtro.getNombre2().trim() : "-9";
		String apell1 = (filtro.getApellido1() != null && filtro.getApellido1()
				.trim().length() > 0) ? filtro.getApellido1().trim() : "-9";
		String apell2 = (filtro.getApellido2() != null && filtro.getApellido2()
				.trim().length() > 0) ? filtro.getApellido2().trim() : "-9";
		String orden = (filtro.getOrden() != null && GenericValidator
				.isInt(filtro.getOrden().trim())) ? filtro.getOrden().trim()
				: "2";

		String sql = "select EstCodigo," + "       EstNumDoc,"
				+ "       CONCAT(EstNombre1,concat(' ',EstNombre2)),"
				+ "       CONCAT(EstApellido1,concat(' ',EstApellido2)), "
				+ "       grunombre " + "FROM  ESTUDIANTE E, "
				+ "  G_JERARQUIA a, " + "  G_JERARQUIA b,  " + "  GRUPO g "
				+ "WHERE a.G_JerTipo  =1 " + "  and   a.G_JerNivel =7 "
				+ "  AND   b.G_JERTIPO  = 1 " + "  AND   b.G_JERNIVEL = 8 "
				+ "  AND   b.G_JERINST = "
				+ codInst
				+ " "
				+ "  AND (( "
				+ codSede
				+ " = -99) OR ( b.G_JERSEDE  = "
				+ codSede
				+ " )) "
				+ "  AND (( "
				+ codJorn
				+ " = -9) OR ( b.G_JERJORN  = "
				+ codJorn
				+ " )) "
				+ "  AND (( "
				+ codGrad
				+ " = -9) OR ( b.G_JERGRADO = "
				+ codGrad
				+ " )) "
				+ "  AND (( "
				+ codMetd
				+ " = -9) OR ( b.G_JerMetod = "
				+ codMetd
				+ " )) "
				+ "  AND (( "
				+ codGrup
				+ " = -9) OR ( b.G_JERGRUPO = "
				+ codGrup
				+ " )) "
				+ "  AND b.G_JerInst =  a.G_JerInst  "
				+ "  and b.G_JerSede =  a.G_JerSede  "
				+ "  and b.G_JerJorn =  a.G_JerJorn  "
				+ "  and b.G_JerMetod = a.G_JerMetod "
				+ "  and b.G_JerGrado = a.G_JerGrado "
				+ "  and g.GruCodigo= b.G_Jergrupo "
				+ "  and b.g_jercodigo = estgrupo "
				+ "  AND g.GRUCODJERAR = a.g_jercodigo"
				+ "  AND (( '"
				+ nom1
				+ "' = '-9' ) OR (UPPER(ESTNOMBRE1) LIKE UPPER('"
				+ nom1
				+ "'||'%'))) "
				+ "  AND (( '"
				+ nom2
				+ "' = '-9' ) OR (UPPER( NVL(ESTNOMBRE2,' ')) LIKE UPPER('"
				+ nom2
				+ "' ||'%'))) "
				+ "  AND (( '"
				+ apell1
				+ "' = '-9' )  OR (UPPER(ESTAPELLIDO1) LIKE UPPER( '"
				+ apell1
				+ "' ||'%'))) "
				+ "  AND (( '"
				+ apell2
				+ "' = '-9' )  OR (UPPER(NVL(ESTAPELLIDO2,' ') ) LIKE UPPER('"
				+ apell2
				+ "'||'%'))) "
				+ "  AND (( '"
				+ numDoc
				+ "' = '-9' ) OR (UPPER(ESTNUMDOC) LIKE UPPER('"
				+ numDoc
				+ "'||'%')) )  "
				+ "  AND ESTESTADO BETWEEN 100 AND 199 "
				+ "  order by   b.G_JERINST, b.G_JERSEDE, b.G_JERJORN, b.G_JERGRADO, "
				+ "           b.G_JERGRUPO," + orden;

		try {
			cn = cursor.getConnection();

			pst = cn.prepareStatement(sql);
			pst.clearParameters();
			rs = pst.executeQuery();
			int m = rs.getMetaData().getColumnCount();
			Collection list = new ArrayList();
			Object[] o;
			int i = 0;
			while (rs.next()) {
				o = new Object[m];
				for (i = 1; i <= m; i++)
					o[i - 1] = rs.getString(i);
				list.add(o);
			}
			return list;
		} catch (SQLException e) {
			throw new InternalErrorException(e);
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
			cursor.cerrar();
		}
	}
	
	public boolean registrarAlumno(FiltroBean filtro) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		long grupo = 0;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("getGrupo"));
			pst.clearParameters();
			pst.setLong(posicion++, filtro.getInstitucion().longValue());
			pst.setLong(posicion++, Long.parseLong(filtro.getSede()));
			pst.setLong(posicion++, Long.parseLong(filtro.getJornada()));
			pst.setLong(posicion++, Long.parseLong(filtro.getMetodologia()));
			pst.setLong(posicion++, Long.parseLong(filtro.getGrado()));
			pst.setLong(posicion++, Long.parseLong(filtro.getFilgrupo()));
			rs = pst.executeQuery();
			if (rs.next()) {
				grupo = rs.getLong(1);
			} else {
				setMensaje("Grupo no encontrado");
				return false;
			}
			rs.close();
			pst.close();
			posicion = 1;
			pst = cn.prepareStatement(rb.getString("alumnoNuevo"));
			pst.clearParameters();
			pst.setLong(posicion++, grupo);
			if (filtro.getTipoDocumento().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++,
						Integer.parseInt(filtro.getTipoDocumento().trim()));
			if (filtro.getId().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++,
						filtro.getId().trim());
			if (filtro.getNombre1().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, filtro.getNombre1().trim());
			if (filtro.getNombre2().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, filtro.getNombre2().trim());
			if (filtro.getApellido1().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, filtro.getApellido1().trim());
			if (filtro.getApellido2().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, filtro.getApellido2().trim());
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error SQL intentando ingresar información bnsica. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		}finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}
	
	public boolean updateGrupoAlumno(FiltroBean filtro) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		long grupo = 0;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("getGrupo"));
			pst.clearParameters();
			pst.setLong(posicion++, filtro.getInstitucion().longValue());
			pst.setLong(posicion++, Long.parseLong(filtro.getSede()));
			pst.setLong(posicion++, Long.parseLong(filtro.getJornada()));
			pst.setLong(posicion++, Long.parseLong(filtro.getMetodologia()));
			pst.setLong(posicion++, Long.parseLong(filtro.getGrado()));
			pst.setLong(posicion++, Long.parseLong(filtro.getFilgrupo()));
			rs = pst.executeQuery();
			if (rs.next()) {
				grupo = rs.getLong(1);
			} else {
				setMensaje("Grupo no encontrado");
				return false;
			}
			rs.close();
			pst.close();
			posicion = 1;
			pst = cn.prepareStatement(rb.getString("estudiante_updategrupoEstado"));
			pst.clearParameters();
			pst.setLong(posicion++, grupo);
			pst.setLong(posicion++, ESTADO_MATRICULADO);
			if (filtro.getTipoDocumento().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++,
						Integer.parseInt(filtro.getTipoDocumento().trim()));
			if (filtro.getId().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++,filtro.getId().trim());
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexión con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error SQL intentando ingresar información básica. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', '"'));
			}
			return false;
		}finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}
	
	public boolean inactivarGrupoAlumno(FiltroBean filtro) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		long grupo = 0;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			posicion = 1;
			pst = cn.prepareStatement(rb.getString("estudiante_updategrupoEstado"));
			pst.clearParameters();
			pst.setLong(posicion++, GRUPO_0);
			pst.setLong(posicion++, ESTADO_MATRICULA_INACTIVO);
			if (filtro.getTipoDocumento().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++,
						Integer.parseInt(filtro.getTipoDocumento().trim()));
			if (filtro.getId().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++,filtro.getId().trim());
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexion con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error SQL intentando ingresar informacion basica. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', '"'));
			}
			return false;
		}finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}
	
	public HashMap getEstudianteRestriccion(String tipo, String num) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long resultado;
		int iteraciones=0;
		String mensaje="";
		int tipoDocumento=0;
		HashMap<Long, String> resultadoFinal = new HashMap<Long, String>();
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("idEstudianteRestricicon"));
			posicion = 1;
//			ps.setLong(posicion++, Long.parseLong(tipo));
			ps.setString(posicion++, (num));
			rs = ps.executeQuery();
			while (rs.next()) {
				iteraciones++;
				tipoDocumento = rs.getInt(1);
			}
			rs.close();
			ps.close();
			
			if(iteraciones == 1){
				if(tipo.equals(String.valueOf(tipoDocumento))){
					resultado = 1L;
					mensaje = "Se debe actualizar";
				}else{
					resultado = 0L;
					mensaje = "Existe un niño registrado con el mismo numero de documento y el tipo de documento: "+obtenerTipoDocumento(tipoDocumento)+". Por favor confirme los datos o comuniquese con apoyo escolar al correo <apoyoescolar@educacionbogota.gov.co>";
				}
			}else if(iteraciones > 1){
				resultado = 0L;
				mensaje = "Existen varios niños registrados con el mismo numero de documento.  Para adicionar este niño envíe los datos al correo <apoyoescolar@educacionbogota.gov.co> indicando nombres completos, tipo y número de identificación confirmados contra documento de físico.";
			}else{
				resultado = 2L;
				mensaje = "No se encontraron registros, se puede crear el niño.";
			}
			resultadoFinal.put(resultado, mensaje);
			
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener Estudiante. Posible problema: ");
			System.out
					.println("Error intentando obtener Estudiante. Posible problema: "
							+ sqle);
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
		return resultadoFinal;
	}
	
	public String obtenerTipoDocumento(int tipoDocumento){
		String resultado="";
		switch (tipoDocumento){
			case 1: resultado="NIP"; break;
			case 2: resultado="SED"; break;
			case 3: resultado="NUIP"; break;
			case 4: resultado="CC"; break;
			case 5: resultado="CE"; break;			
		}
		return resultado;
	}
	
	public boolean ejecutarScript(String cadena, String login, String descripcion) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			String cadenaAux = cadena.toUpperCase();
			if((!cadenaAux.contains("PROCEDURE") && !cadenaAux.contains("PACKAGE")) && (cadenaAux.contains("DELETE") || cadenaAux.contains("UPDATE"))){
				if(!cadenaAux.contains("WHERE")){
					setMensaje("Debe especificar la condicion WHERE");
					return false;
				}
			}
			
			cn = cursor.getConnection();
			posicion = 1;
			pst = cn.prepareStatement("call SP_EJECUTAR_PROCESO(?,?,?)");
			pst.clearParameters();
			pst.setString(posicion++, cadena);
			pst.setString(posicion++, login);
			pst.setString(posicion++, descripcion);
			pst.executeUpdate();
			cn.commit();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexion con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error SQL intentando ingresar informacion basica. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', '"'));
			}
			return false;
		}finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}
}
