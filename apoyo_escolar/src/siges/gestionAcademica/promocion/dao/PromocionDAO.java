package siges.gestionAcademica.promocion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.ResourceBundle;
import siges.gestionAcademica.promocion.beans.ParamVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.gestionAcademica.promocion.beans.FiltroPromocion;


/**
 * @author USUARIO
 * 
 * Preferencias - Java - Estilo de cndigo - Plantillas de cndigo
 */
public class PromocionDAO extends Dao {
	public String sentencia;
	
	public String buscar;
	
	private ResourceBundle rb;
	
	private java.text.SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
	
	public PromocionDAO(Cursor cur) {
		super(cur);
		sentencia = null;
		sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		rb = ResourceBundle.getBundle("siges.gestionAcademica.promocion.bundle.promocion");
	}
	
	
	/**
	 * Funcinn: Insertar Evaluacinn Asignatura <br>
	 * 
	 * @param FiltroPromocion
	 *            f
	 * @return boolean
	 */
	public Collection getEstudiantes(FiltroPromocion f) {
		String grupo = f.getGrupo().substring(
				f.getGrupo().lastIndexOf("|") + 1, f.getGrupo().length());
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		String jer = "";
		Collection list = null;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("listaEstudianteLogro"
					+ (Integer.parseInt(f.getOrden()) + 1)));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(f.getInstitucion()));
			ps.setLong(posicion++, Long.parseLong(f.getSede()));
			ps.setLong(posicion++, Long.parseLong(f.getJornada()));
			ps.setLong(posicion++, Long.parseLong(f.getMetodologia()));
			ps.setLong(posicion++, Long.parseLong(f.getGrado()));
			ps.setLong(posicion++, Long.parseLong(grupo));
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
			setMensaje("Error intentando obtener estudiantes. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"',
				'n'));
			}
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return list;
	}
	
	/**
	 * @function:  
	 * @param f
	 * @return
	 */
	public Collection getNotasProm(FiltroPromocion f) {
		String grupo = f.getGrupo().substring(f.getGrupo().lastIndexOf("|") + 1, f.getGrupo().length());
		String asig = f.getAsignatura().substring(f.getAsignatura().lastIndexOf("|") + 1,f.getAsignatura().length());
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		String jer = null;
		Collection list = null;
		try {
			cn = cursor.getConnection(); 
			ps = cn.prepareStatement(rb.getString("Promocion.notas"));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(f.getJerarquiagrupo()));
			ps.setLong(posicion++, Long.parseLong(f.getVigencia()));
			ps.setInt(posicion++, f.getTipoPromocion());
			rs = ps.executeQuery();
			list = getCollection(rs);
			rs.close();
			ps.close();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexi髇 con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener notas de Asig. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"',
				'n'));
			}
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return list;
	}
	
	
	
	
	
	/**
	 * @function:  
	 * @return
	 */
	public Collection getTipoPromocion() {
		int posicion = 1;
		
		String jer = null;
		Collection list = new ArrayList();
		try {
			
			Object[] o = new Object[2];
			o[0] = "1";
			o[1] = "Semestre 1";
			
			list.add(o);
			
			o = new Object[2];
			o[0] = "2";
			o[1] = "Semestre 2";
			
			
			o = new Object[2];
			o[0] = "3";
			o[1] = "Anual";
			
			list.add(o);
			
			
			
			
			
			
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error intentando obtener tipos de semestre en promocinn. Posible problema:  " + sqle.getMessage());
			return null;
		} finally {
			
		}
		return list;
	}
	
	/**
	 * Funcinn: Retorna el codigo de jerarquia de <br>
	 * 
	 * @param FiltroPromocion
	 *            f
	 * @return boolean
	 */
	public String getJerarquiaGrupo(Collection c) {
		if (!c.isEmpty()) {
			Iterator iterator = c.iterator();
			Object[] o;
			if (iterator.hasNext()) {
				o = (Object[]) iterator.next();
				return ((String) (o[6] != null ? o[6] : ""));
			}
		}
		return "";
	}
	
	/**
	 * Funcinn: Insertar Evaluacinn Promocion <br>
	 * @param FiltroPromocion
	 * @return boolean
	 */
	public boolean insertarEvalPromocion(FiltroPromocion f ) throws Exception{
		System.out.println( new Date() + "::::: - insertarEvalPromocion  -");
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null, pst2 = null, pst3 = null, pst4=null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null; 
		try {
			long inst=Long.parseLong(f.getInstitucion());
			long grado=Long.parseLong(f.getGrado());
			long met=Long.parseLong(f.getMetodologia());
			long jer=Long.parseLong(!f.getJerarquiagrupo().equals("")?f.getJerarquiagrupo():"0");
			long vig=Long.parseLong(f.getVigencia());
			
			
			int tipoProm  =  f.getTipoPromocion();
			//System.out.println("tipoProm " + tipoProm);
			
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("Promocion.insertar"));
			pst2 = cn.prepareStatement(rb.getString("Promocion.actualizar"));
			pst3 = cn.prepareStatement(rb.getString("Promocion.eliminar"));
			pst4 = cn.prepareStatement(rb.getString("Promocion.eliminarest"));
			
			String notas[] = f.getNota();// listado de notas
			String motivos[] = f.getMotivo(); // listado de motivos
			String fechas[] = f.getfecha_estado(); // listado de fechas
			String update[] = f.getActualizar();//lista de registros que ya estan
			
			String update_[] = new String[f.getActualizar() != null ? f.getActualizar().length : 1];
			
			//Arreglos para guarda informaci贸n de un estudiante
			String nota_est[]    = new String[2];
			String update_est[]  = new String[2];
			String motivo_est[] = new String[2];
			String fecha_est[] = new String[2];
			
			int cont = 0;
			int ins = 0;
			int i, j;
			if (notas != null) {
				for (i = 0; i < notas.length; i++) {
					ins = 0;
					//System.out.println("notas form "+notas[i]);
					nota_est = notas[i].replace('|', '&').split("&");
					motivo_est = motivos[i].replace('|', '&').split("&");
					fecha_est = fechas[i].replace('|', '&').split("&");
					if (nota_est.length > 1) {
						ps = pst;
						if (update != null) {
							for (j = 0; j < update.length; j++) {
								update_est = update[j].replace('|', '&').split("&");
								if (nota_est[0].equals(update_est[0])) {
									update_[cont++] = nota_est[0];
									ps = pst2;
									ins = 1;
									break;
								}
							}
						}
						if (ps != null) {
							ps.clearParameters();
							posicion = 1;
							ps.setInt(posicion++, Integer.parseInt(nota_est[1].trim()));
							
							if (motivo_est == null || motivo_est[0].trim().length() == 0  ){
								ps.setNull(posicion++, Types.VARCHAR);
							}else{ 
								ps.setString(posicion++, motivo_est[1].trim());
							}
							ps.setLong(posicion++, inst);
							ps.setLong(posicion++, grado);
							ps.setLong(posicion++, met);
							
							
							if(fecha_est != null && fecha_est.length > 1){
								if(fecha_est[1]==""||fecha_est[1].indexOf(" ")!=-1)
									fecha_est[1]=null;
								
							}	
							
							if(fecha_est != null && fecha_est.length > 1){
								System.out.println("fechains "+fecha_est[1]);
								ps.setString(posicion++,fecha_est[1]);
							}else{
								ps.setString(posicion++,"");
							}
							
							
							if (jer==0) ps.setNull(posicion++, java.sql.Types.VARCHAR);
							else ps.setLong(posicion++, jer);
							ps.setLong(posicion++, Long.parseLong(nota_est[0].trim()));
							ps.setLong(posicion++, vig);
							//if(ins != 1)
							ps.setLong(posicion++, tipoProm);
							int r = ps.executeUpdate();
							/**
							 * Codigo: ag17Promocion01
							 * Modificado por: Grupo de Desarrollo REDP - MICUELLARI
							 * Se comentarea la linea que cierra la conexion, al final se cierran todas !
							 */
							//ps.close();
						}
					}
				}
				if (update != null) {
					String str;
					int band = -1;
					for (i = 0; i < update.length; i++) {
						band = 1;
						update_est = update[i].replace('|', '&').split("&");
						for (j = 0; j < (update_ != null ? update_.length : 0); j++) {
							if (update_[j] != null) {
								if (update_[j].equals(update_est[0])) {
									band = 2;
									break;
								}
							}
						}
						if (band == 1) {
							ps = pst3;
							ps.clearParameters();
							posicion = 1;
							if (jer==0) ps.setNull(posicion++, java.sql.Types.VARCHAR);
							else ps.setLong(posicion++, jer);//PROCODJERAR
							ps.setLong(posicion++, Long.parseLong(update_est[0].trim()));//PROCODESTUD
							ps.setLong(posicion++, vig);//PROVIGENCIA
							int r = ps.executeUpdate();
						}
					}
				}
			}
			ps = pst4;
			ps.clearParameters();
			posicion = 1;
			ps.setLong(posicion++, vig);
			System.out.println(ps.toString());
			int r = ps.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
			System.out.println("con existo insertarEvalPromocion");
			
		} catch (InternalErrorException in) {
			System.out.println("in " + in.getMessage());
			in.printStackTrace();
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			System.out.println("sqle " + sqle.getMessage() );
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando evaluar promocion. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"',
				'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeStatement(pst2);
				OperacionesGenerales.closeStatement(pst3);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}
	
	
	
	
	/**
	 * 
	 * @param f
	 * @return
	 * @throws Exception
	 */ 
	public String validarTipoPromocion(FiltroPromocion f ) throws Exception{
		System.out.println( new Date() + "::::: - validarTipoPromocion  -");
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null; 
		ResultSet rs = null;
		
		try {
			long inst=Long.parseLong(f.getInstitucion());
			long grado=Long.parseLong(f.getGrado());
			long met=Long.parseLong(f.getMetodologia());
			long jer=Long.parseLong(!f.getJerarquiagrupo().equals("")?f.getJerarquiagrupo():"0");
			long vig=Long.parseLong(f.getVigencia());
			long codJerGrupo = Long.parseLong(f.getJerarquiagrupo().trim());
			
			int tipoProm  =  f.getTipoPromocion();
			
			cn = cursor.getConnection();
		 
			
			String notas[] = f.getNota();// listado de notas
		    String nota_est[]    = new String[2];
 
		 
			if (notas != null) {
				for (int i = 0; i < notas.length; i++) {
				  nota_est = notas[i].replace('|', '&').split("&"); 
				 
				  pst = cn.prepareStatement(rb.getString("Promocion.validarTipoPromo"+tipoProm));
				// System.out.println("CONSULTA "  + rb.getString("Promocion.validarTipoPromo"+tipoProm));
				  posicion = 1;
				  pst.setLong(posicion++, codJerGrupo); // PROCODJERAR
				  System.out.println("codJerGrupo " + codJerGrupo);
				  pst.setLong(posicion++, Long.parseLong(nota_est[0].trim())); // PROCODESTUD
				  System.out.println("Long.parseLong(nota_est[0].trim()) " + Long.parseLong(nota_est[0].trim()));
				  pst.setLong(posicion++, vig);// PROVIGENCIA
				  System.out.println("vig " + vig);
				  //pst.setLong(posicion++, tipoProm);//PROTIPOPROM
				  rs = pst.executeQuery();
				  
				  if(rs.next()){
					  System.out.println("ENTRO " + rs.getInt(1));
					  posicion = 1;
					return "No se puede registrar la informaci贸n: " +
							"Existe al menos un estudiante que tienen informaci贸n de promocinn para la vigencia "+ vig + ", con el tipo de promocinn " 
					        +ParamVO.getTIPO_PROMO(rs.getInt(posicion++)).toUpperCase()  ;  
				  }
				  rs.close();
				  pst.close();
				}
			}
			
		} catch (InternalErrorException in) {
			System.out.println("in " + in.getMessage());
			in.printStackTrace();
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			throw new Exception(in.getMessage());
		} catch (SQLException sqle) {
			System.out.println("sqle " + sqle.getMessage() );
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando evaluar promocion. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"',
				'n'));
			}
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs); 
				OperacionesGenerales.closeStatement(pst); 
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return "";
	}
	
	
	public String validarTipoSemes2(FiltroPromocion f ) throws Exception{
		System.out.println( new Date() + "::::: - validarTipoSemes2 -");
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null; 
		ResultSet rs = null;
		
		try {
			long inst=Long.parseLong(f.getInstitucion());
			long grado=Long.parseLong(f.getGrado());
			String gradoNom = f.getGradoNom();
			long met=Long.parseLong(f.getMetodologia());
			long jer=Long.parseLong(!f.getJerarquiagrupo().equals("")?f.getJerarquiagrupo():"0");
			long vig=Long.parseLong(f.getVigencia());
			long codJerGrupo = Long.parseLong(f.getJerarquiagrupo().trim());
			
			int tipoProm  =  f.getTipoPromocion();
			
			cn = cursor.getConnection();
		 
			
			String notas[] = f.getNota();// listado de notas
		    String nota_est[]    = new String[2];
 
		 
			if (tipoProm == ParamVO.TIPO_PROMO_2_COD && notas != null) {
				
				for (int i = 0; i < notas.length; i++) {
				  nota_est = notas[i].replace('|', '&').split("&"); 
				 
				  pst = cn.prepareStatement(rb.getString("Promocion.validarTipoSemes2"));
				 //System.out.println("CONSULTA "  + rb.getString("Promocion.validarTipoSemes2"));
				  posicion = 1;
				  pst.setLong(posicion++, codJerGrupo); // PROCODJERAR
				  System.out.println("codJerGrupo " + codJerGrupo);
				  pst.setLong(posicion++, Long.parseLong(nota_est[0].trim())); // PROCODESTUD
				  System.out.println("Long.parseLong(nota_est[0].trim()) " + Long.parseLong(nota_est[0].trim()));
				  pst.setLong(posicion++, vig);// PROVIGENCIA
				  System.out.println("vig " + vig);
				  pst.setLong(posicion++, ParamVO.TIPO_PROMO_1_COD );//PROTIPOPROM
				  rs = pst.executeQuery();
				  
				  if(rs.next()){
					  System.out.println("ENTRO " + rs.getInt(1));
					  posicion = 1;
					return "No se puede registrar la informaci贸n: " +
							"Existe al menos un estudiante que  ya fue promovido en la vigencia "+ vig + ", para el grado "+ gradoNom.toUpperCase() + "  y con el tipo de promocinn " 
					        + ParamVO.getTIPO_PROMO(rs.getInt(posicion++)).toUpperCase()+" por lo tanto no es posible guardar esta informaci贸n."  ;  
				  }
				  rs.close();
				  pst.close();
				}
			}
			
		} catch (InternalErrorException in) {
			System.out.println("in " + in.getMessage());
			in.printStackTrace();
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			throw new Exception(in.getMessage());
		} catch (SQLException sqle) {
			System.out.println("sqle " + sqle.getMessage() );
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando evaluar promocion. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"',
				'n'));
			}
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs); 
				OperacionesGenerales.closeStatement(pst); 
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return "";
	}
	
}