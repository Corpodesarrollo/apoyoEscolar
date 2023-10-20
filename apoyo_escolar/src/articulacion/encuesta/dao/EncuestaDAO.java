package articulacion.encuesta.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import articulacion.encuesta.vo.ColegioVO;
import articulacion.encuesta.vo.EncuestaVO;
import articulacion.encuesta.vo.EspecialidadVO;
import articulacion.encuesta.vo.SemestreVO;
import articulacion.encuesta.vo.UniversidadVO;

public class EncuestaDAO extends siges.dao.Dao {

	public EncuestaDAO(Cursor r) {
		super(r);
		rb = ResourceBundle.getBundle("articulacion.encuesta.bundle.encuesta");
	}

	public List getListaAsignatura() throws Exception {
		List lista = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			itemVO = new ItemVO(posicion++, "MATEMnTICAS");
			lista.add(itemVO);
			itemVO = new ItemVO(posicion++, "CIENCIAS NATURALES");
			lista.add(itemVO);
			itemVO = new ItemVO(posicion++, "CIENCIAS SOCIALES");
			lista.add(itemVO);
			itemVO = new ItemVO(posicion++, "ESPAnOL");
			lista.add(itemVO);
			itemVO = new ItemVO(posicion++, "INGLES");
			lista.add(itemVO);
			itemVO = new ItemVO(posicion++, "TECNOLOGnA E INFORMnTICA");
			lista.add(itemVO);
			itemVO = new ItemVO(posicion++, "nTICA");
			lista.add(itemVO);
			itemVO = new ItemVO(posicion++, "EDUCACInN FnSICA");
			lista.add(itemVO);
			itemVO = new ItemVO(posicion++, "EDUCACInN RELIGIOSA");
			lista.add(itemVO);
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}
		return lista;
	}

	public List getListaCiclo() throws Exception {
		List lista = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			itemVO = new ItemVO(posicion++, "Tncnico Laboral");
			lista.add(itemVO);
			itemVO = new ItemVO(posicion++, "Tncnico Profesional");
			lista.add(itemVO);
			itemVO = new ItemVO(posicion++, "Profesional");
			lista.add(itemVO);
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}
		return lista;
	}

	public List getListaInteres() throws Exception {
		List lista = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			itemVO = new ItemVO(posicion++,
					"Continuar Estudios en la Universidad");
			lista.add(itemVO);
			itemVO = new ItemVO(posicion++,
					"Hacer un Curso en el SENA u Otra Organizacinn");
			lista.add(itemVO);
			itemVO = new ItemVO(posicion++, "Conseguir Trabajo");
			lista.add(itemVO);
			itemVO = new ItemVO(posicion++, "Estudiar y Trabajar");
			lista.add(itemVO);
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}
		return lista;
	}

	public List getListaCarrera() throws Exception {
		List lista = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			itemVO = new ItemVO(posicion++, "Medicina", 0);
			lista.add(itemVO);
			itemVO = new ItemVO(posicion++, "Derecho", 0);
			lista.add(itemVO);
			itemVO = new ItemVO(posicion++, "Administracinn de Empresas", 0);
			lista.add(itemVO);
			itemVO = new ItemVO(posicion++, "Ingenierna ", 1);
			lista.add(itemVO);
			itemVO = new ItemVO(posicion++, "Odontologna", 0);
			lista.add(itemVO);
			itemVO = new ItemVO(posicion++, "Psicologna", 0);
			lista.add(itemVO);
			itemVO = new ItemVO(posicion++, "Contadurna", 0);
			lista.add(itemVO);
			itemVO = new ItemVO(posicion++, "Comunicacinn Social", 0);
			lista.add(itemVO);
			itemVO = new ItemVO(posicion++, "Arquitectura", 0);
			lista.add(itemVO);
			itemVO = new ItemVO(posicion++, "Economna", 0);
			lista.add(itemVO);
			itemVO = new ItemVO(posicion++, "Otra. nCunl?", 2);
			lista.add(itemVO);
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}
		return lista;
	}

	public List getListaIngenieria() throws Exception {
		List lista = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			itemVO = new ItemVO(posicion++, "Administrativa Y Afines");
			lista.add(itemVO);
			itemVO = new ItemVO(posicion++, "Agrncola, Forestal Y Afines");
			lista.add(itemVO);
			itemVO = new ItemVO(posicion++,
					"Agroindustrial, Alimentos Y Afines");
			lista.add(itemVO);
			itemVO = new ItemVO(posicion++, "Agronnmica, Pecuaria Y Afines");
			lista.add(itemVO);
			itemVO = new ItemVO(posicion++, "Ambiental, Sanitaria Y Afines");
			lista.add(itemVO);
			itemVO = new ItemVO(posicion++, "Biomndica Y Afines");
			lista.add(itemVO);
			itemVO = new ItemVO(posicion++, "Civil Y Afines");
			lista.add(itemVO);
			itemVO = new ItemVO(posicion++, "De Minas, Metalurgia Y Afines");
			lista.add(itemVO);
			itemVO = new ItemVO(posicion++, "De Sistemas, Telemntica Y Afines");
			lista.add(itemVO);
			itemVO = new ItemVO(posicion++, "Elnctrica Y Afines");
			lista.add(itemVO);
			itemVO = new ItemVO(posicion++,
					"Electrnnica, Telecomunicaciones Y Afines");
			lista.add(itemVO);
			itemVO = new ItemVO(posicion++, "Industrial Y Afines");
			lista.add(itemVO);
			itemVO = new ItemVO(posicion++, "Mecnnica Y Afines");
			lista.add(itemVO);
			itemVO = new ItemVO(posicion++, "Qunmica Y Afines");
			lista.add(itemVO);
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}
		return lista;
	}

	public List getListaEspecialidad() throws Exception {
		List lista = new ArrayList();
		UniversidadVO universidadVO = null;
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("getUniversidades"));
			posicion = 1;
			rs = pst.executeQuery();
			while (rs.next()) {
				posicion = 1;
				universidadVO = new UniversidadVO();
				universidadVO.setCodigo(rs.getLong(posicion++));
				universidadVO.setNombre(rs.getString(posicion++));
				universidadVO.setListaColegio(getAllColegio(cn,
						universidadVO.getCodigo()));
				lista.add(universidadVO);
			}
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
		}
		return lista;
	}

	public List getAllColegio(Connection cn, long univ) throws Exception {
		List lista = new ArrayList();
		ColegioVO colegioVO = null;
		int posicion = 1;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = cn.prepareStatement(rb.getString("getColegios"));
			posicion = 1;
			pst.setLong(posicion++, univ);
			rs = pst.executeQuery();
			while (rs.next()) {
				posicion = 1;
				colegioVO = new ColegioVO();
				colegioVO.setCodigo(rs.getLong(posicion++));
				colegioVO.setNombre(rs.getString(posicion++));
				colegioVO.setLocalidad(rs.getString(posicion++));
				colegioVO.setListaEspecialidad(getAllEspecialidad(cn, univ,
						colegioVO.getCodigo()));
				lista.add(colegioVO);
			}
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
		}
		return lista;
	}

	public List getAllEspecialidad(Connection cn, long univ, long col)
			throws Exception {
		List lista = new ArrayList();
		EspecialidadVO especialidadVO = null;
		int posicion = 1;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = cn.prepareStatement(rb.getString("getEspecialidades"));
			posicion = 1;
			pst.setLong(posicion++, col);
			pst.setLong(posicion++, univ);
			rs = pst.executeQuery();
			while (rs.next()) {
				posicion = 1;
				especialidadVO = new EspecialidadVO();
				especialidadVO.setCodigo(rs.getLong(posicion++));
				especialidadVO.setNombre(rs.getString(posicion++));
				especialidadVO.setCiclo(rs.getString(posicion++));
				lista.add(especialidadVO);
			}
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
		}
		return lista;
	}

	public List getListaImportancia() throws Exception {
		List lista = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			itemVO = new ItemVO(posicion, String.valueOf(posicion++));
			lista.add(itemVO);
			itemVO = new ItemVO(posicion, String.valueOf(posicion++));
			lista.add(itemVO);
			itemVO = new ItemVO(posicion, String.valueOf(posicion++));
			lista.add(itemVO);
			itemVO = new ItemVO(posicion, String.valueOf(posicion++));
			lista.add(itemVO);
			itemVO = new ItemVO(posicion, String.valueOf(posicion++));
			lista.add(itemVO);
			itemVO = new ItemVO(posicion, String.valueOf(posicion++));
			lista.add(itemVO);
			itemVO = new ItemVO(posicion, String.valueOf(posicion++));
			lista.add(itemVO);
			itemVO = new ItemVO(posicion, String.valueOf(posicion++));
			lista.add(itemVO);
			itemVO = new ItemVO(posicion, String.valueOf(posicion++));
			lista.add(itemVO);
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}
		return lista;
	}

	public EncuestaVO getEncuesta(long codigo) throws Exception {
		EncuestaVO encuestaVO = null;
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("getEncuesta2"));
			posicion = 1;
			pst.setLong(posicion++, codigo);
			rs = pst.executeQuery();
			// System.out.println("esto es encuesta"+codigo);
			if (rs.next()) {
				// System.out.println("Sube la encuesta"+codigo);
				posicion = 1;
				encuestaVO = new EncuestaVO();
				encuestaVO.setCodigo(rs.getLong(posicion++));
				encuestaVO.setPregunta2a(rs.getString(posicion++));
				encuestaVO.setPregunta2b(rs.getString(posicion++));
				encuestaVO.setPregunta3(rs.getInt(posicion++));
				encuestaVO.setPregunta4(rs.getInt(posicion++));
				encuestaVO.setPregunta5a(rs.getInt(posicion++));
				encuestaVO.setPregunta5b(rs.getInt(posicion++));
				encuestaVO.setPregunta5c(rs.getString(posicion++));
				encuestaVO.setPregunta6a(rs.getLong(posicion++));
				encuestaVO.setPregunta6b(rs.getLong(posicion++));
				encuestaVO.setPregunta6(encuestaVO.getPregunta6a() + "|"
						+ encuestaVO.getPregunta6b());
				encuestaVO.setPregunta7a(rs.getLong(posicion++));
				encuestaVO.setPregunta7b(rs.getLong(posicion++));
				encuestaVO.setPregunta7(encuestaVO.getPregunta7a() + "|"
						+ encuestaVO.getPregunta7b());
			}
			rs.close();
			pst.close();
			if (encuestaVO != null) {
				pst = cn.prepareStatement(rb.getString("getEncuesta1"));
				posicion = 1;
				pst.setLong(posicion++, codigo);
				rs = pst.executeQuery();
				List l = new ArrayList();
				while (rs.next()) {
					l.add(rs.getString(1) + "|" + rs.getString(2));

				}
				String a[] = new String[l.size()];
				for (int i = 0; i < l.size(); i++) {
					a[i] = (String) l.get(i);
				}
				encuestaVO.setPregunta1(a);
			}
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
		}
		return encuestaVO;
	}

	public EncuestaVO actualizarEncuesta(EncuestaVO encuestaVO)
			throws Exception {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			String[] a = encuestaVO.getPregunta6().replace('|', ':').split(":");
			encuestaVO.setPregunta6a(Long.parseLong(a[0]));
			encuestaVO.setPregunta6b(Long.parseLong(a[1]));
			a = encuestaVO.getPregunta7().replace('|', ':').split(":");
			encuestaVO.setPregunta7a(Long.parseLong(a[0]));
			encuestaVO.setPregunta7b(Long.parseLong(a[1]));
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("actualizarEncuesta2"));
			posicion = 1;
			pst.setString(posicion++, encuestaVO.getPregunta2a());
			pst.setString(posicion++, encuestaVO.getPregunta2b());
			pst.setLong(posicion++, encuestaVO.getPregunta3());
			pst.setLong(posicion++, encuestaVO.getPregunta4());
			pst.setLong(posicion++, encuestaVO.getPregunta5a());
			pst.setInt(posicion++, encuestaVO.getPregunta5b());
			pst.setString(posicion++, encuestaVO.getPregunta5c());
			pst.setLong(posicion++, encuestaVO.getPregunta6a());
			pst.setLong(posicion++, encuestaVO.getPregunta6b());
			pst.setLong(posicion++, encuestaVO.getPregunta7a());
			pst.setLong(posicion++, encuestaVO.getPregunta7b());
			pst.setLong(posicion++, encuestaVO.getCodigo());
			pst.executeUpdate();
			pst.close();
			a = encuestaVO.getPregunta1();
			String[] b = null;
			pst = cn.prepareStatement(rb.getString("actualizarEncuesta1"));
			pst.clearBatch();
			for (int i = 0; i < a.length; i++) {
				b = a[i].replace('|', ':').split(":");
				posicion = 1;
				pst.setLong(posicion++, Long.parseLong(b[1]));
				pst.setLong(posicion++, encuestaVO.getCodigo());
				pst.setLong(posicion++, Long.parseLong(b[0]));
				pst.addBatch();
			}
			pst.executeBatch();
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
		}
		return encuestaVO;
	}

	public void registrarEncuestado(long id) throws Exception {
		int posicion = 1;
		Connection cn = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		boolean band = false;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("getEncuestado"));
			posicion = 1;
			pst.setLong(posicion++, id);
			rs = pst.executeQuery();
			if (rs.next()) {
				band = true;
			}
			rs.close();
			pst.close();
			if (!band) {
				pst = cn.prepareStatement(rb.getString("ingresarEncuestado"));
				posicion = 1;
				pst.setLong(posicion++, id);
				pst.executeUpdate();
			}
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
		}
	}
}
