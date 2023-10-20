/**
 * 
 */
package siges.integracion;

import siges.dao.Cursor;
import siges.integracion.beans.Colegio;
import siges.integracion.beans.Datos;
import siges.integracion.beans.Estudiante;
import siges.integracion.beans.Params;
import siges.integracion.beans.Sede;
import siges.integracion.dao.IntegracionDAO;
import siges.integracion.dao.MatriculasDAO;
import siges.util.Logger;

/**
 * 1/07/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class Integrador extends Thread {
	public static final long serialVersionUID=1;
	private static boolean running = true;
	private MatriculasDAO matriculasDAO=null;
	private IntegracionDAO integracion=new IntegracionDAO(new Cursor());
	private int tipo;
	
	public Integrador(MatriculasDAO dao,int tipo) {
		super("INTEGRADOR"+tipo);
		this.matriculasDAO=dao;
		this.tipo=tipo;
	}

	public void run() {
		try {
			// int dormir = Integer.parseInt(rb.getString("integra.sleep"));
			// long tempSleep =
			// Long.parseLong(rb.getString("integra.tempSleep"));
			// int cont = 0;
			matriculasDAO.init();
			while (true) {
				if (!running) {
					System.out.println(this.getName()+". FINALIZANDO");
					return;
				}
				Thread.sleep(1000);
				procesarMatriculas();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(this.getName()+". Error Hilo Integrador: " + ex);
		}
	}

	public void procesarMatriculas() {
		Datos[] datos=null;
		int i = 0;
		try {
			// System.out.println("Empieza Integracinn");
			// int cat;// categoria
			// int act;// actividad
			// int tmp;// tiempo
			if (!integracion.isActivo()) {
				System.out.println(this.getName()+". inactivo:" + new java.sql.Timestamp(System.currentTimeMillis()).toString());
				return;
			}
			datos = matriculasDAO.getPila(tipo);
			if (datos == null || datos[0] == null) {
				return;
			}
			for (i = 0; i < datos.length; i++) {
				if (datos[i] != null) {
					matriculasDAO.actualizarActivo(datos[i]);
					// System.out.println("id=="+datos[i].getId());
					switch (datos[i].getCategoria()) {
					case Params.C_INSTITUCION:
						institucion(datos[i]);
						break;
					case Params.C_SEDE:
						sede(datos[i]);
						break;
					case Params.C_ESTUDIANTE:
						estudiante(datos[i]);
						break;
					default:
						return;
					}
					matriculasDAO.ActualizarPila(datos[i]);
				}
			}
		} catch (Exception e) {
			if(datos!=null && i<=datos.length){
				datos[i].setEstado(Params.ERROR);
				datos[i].setMensaje(e.getMessage());
				matriculasDAO.ActualizarPila(datos[i]);
			}
			e.printStackTrace();
		}
		// System.out.println("Termina Integracinn");
	}
	
	public void estudiante(Datos d) throws Exception {
		String mensaje = null;
		Estudiante estudiante = null;
		int nn =0;
		switch (d.getFuncion()) {
		case Params.F_ACTUALIZACIONIDESTUDIANTE:
			System.out.println(this.getName()+". Actualizar Id del Estudiante");
			estudiante = getEstudianteUpdateId(d);
			nn = integracion.existeEstudiateOld(estudiante);
			if (nn == 2) {//ya existe pero con otro codigo de estudiante
				mensaje = "Error Actualizando Id de Estudiante:" + estudiante.getNumId() + ": Codigo de estudiante no es el mismo";
				Logger.print("-1", mensaje, 9, 1, this.toString());
				d.setEstado(Params.ERROR);
				d.setMensaje(mensaje);
				return;
			}
			if (nn == 0) {//NO existe
				mensaje = "Error Actualizando Id de Estudiante :" + estudiante.getNumId() + " Estudiante no encontrado";
				Logger.print("-1", mensaje, 9, 1, this.toString());
				d.setEstado(Params.ERROR);
				d.setMensaje(mensaje);
				return;
			}
			if (nn == -1) {//error interno
				mensaje = "Error Actualizando Id de Estudiante :" + estudiante.getNumId() + " Error interno";
				Logger.print("-1", mensaje, 9, 1, this.toString());
				d.setEstado(Params.ERROR);
				d.setMensaje(mensaje);
				return;
			}
			if (!integracion.actualizarIdAlumno(estudiante)) {
				mensaje = "Error Actualizando Id de Estudiante :" + estudiante.getOldNumId() + " a " + estudiante.getNumId() + "." + integracion.getMensaje();
				Logger.print("-1", mensaje, 9, 1, this.toString());
				d.setEstado(Params.ERROR);
				d.setMensaje(mensaje);
				return;
			}
			d.setEstado(Params.TERMINADO);
			break;
		case Params.F_INSERCIONESTUDIANTE:
			System.out.println(this.getName()+". Insercion Estudiante");
			estudiante = getEstudianteInsert(d);
			nn = integracion.existeEstudiateNew(estudiante);
			if (nn == 1 || nn == 2) {//ya existe No se puede ingresar
				mensaje = "Error Insertando Estudiante :" + estudiante.getNumId() + ": Estudiante ya existe";
				Logger.print("-1", mensaje, 9, 1, this.toString());
				d.setEstado(Params.ERROR);
				d.setMensaje(mensaje);
				return;
			}
			if(nn==-1){
				mensaje = "Error Insertando Estudiante :" + estudiante.getNumId() + ": Error interno";
				Logger.print("-1", mensaje, 9, 1, this.toString());
				d.setEstado(Params.ERROR);
				d.setMensaje(mensaje);
				return;
			}
			if (!integracion.insertarAlumno(estudiante)) {
				mensaje = "Error Insertando Estudiante :" + estudiante.getNumId() + "." + integracion.getMensaje();
				Logger.print("-1", mensaje, 9, 1, this.toString());
				d.setEstado(Params.ERROR);
				d.setMensaje(mensaje);
				return;
			}
			d.setEstado(Params.TERMINADO);
			break;
		case Params.F_ACTUALIZACIONUBICACIONESTUDIANTE:
			estudiante = getEstudianteUpdateUbicacion(d);
			nn = integracion.existeEstudiateNew(estudiante);
			System.out.println(this.getName()+". Actualizar estudiante");
			//preguntar si tiene sede y grupo, de lo contrario, se registra el error
			if(estudiante.getSede()==0 && estudiante.getGrupo()==0){
				mensaje = "Error Actualizando Estudiante :" + estudiante.getNumId() + ". No tiene sede ni grupo";
				Logger.print("-1", mensaje, 9, 1, this.toString());
				d.setEstado(Params.ERROR);
				d.setMensaje(mensaje);
				d.setEliminar(true);
				integracion.registrarNovedad(d);
				return;
			}
			if (nn == 1) {//ya existe actualiza
				if (!integracion.actualizarAlumno(estudiante)) {
					mensaje = "Error Actualizando Estudiante :" + estudiante.getNumId() + "." + integracion.getMensaje();
					Logger.print("-1", mensaje, 9, 1, this.toString());
					d.setEstado(Params.ERROR);
					d.setMensaje(mensaje);
					return;
				}
				d.setEstado(Params.TERMINADO);
			}
			if (nn == 0) {//NO PUEDE INGRESAR EL ESTUDIANTE PORQUE NO TIENE TODOS LOS DATOS
				mensaje = "Error Actualizando Estudiante :" + estudiante.getNumId() + ". Estudiante ya registrado";
				Logger.print("-1", mensaje, 9, 1, this.toString());
				d.setEstado(Params.ERROR);
				d.setMensaje(mensaje);
				return;
			}
			if (nn == 2) {//ya existe pero con otro codigo de estudiante
				mensaje = "Error Actualizando Estudiante:" + estudiante.getNumId() + ". Codigo de estudiante no es el mismo";
				Logger.print("-1", mensaje, 9, 1, this.toString());
				d.setEstado(Params.ERROR);
				d.setMensaje(mensaje);
				return;
			}
			if (nn == -1) {
				mensaje = "Error Actualizando Estudiante: " + estudiante.getNumId() + ". Error Interno " + integracion.getMensaje();
				Logger.print("-1", mensaje, 9, 1, this.toString());
				d.setMensaje(mensaje);
				d.setEstado(Params.ERROR);
				return;
			}
			break;
		}
	}

	public void institucion(Datos d)throws Exception {
		String mensaje = null;
		Colegio colegio=null;
		switch (d.getFuncion()) {
		case Params.F_INSERCIONINSTITUCION:
			System.out.println(this.getName()+". Insertar Institucion");
			colegio=getColegioInsert(d);
			if (!integracion.insertarInstitucion(colegio)) {
				d.setEstado(Params.ERROR);
				mensaje = "Error insertando colegio: " + colegio.getDane11() + ".(" + integracion.getMensaje() + ")";
				Logger.print("-1", mensaje, 9, 1, this.toString());
				d.setMensaje(mensaje);
				return;
			}
			d.setEstado(Params.TERMINADO);
			break;
		case Params.F_ACTUALIZACIONDANE:
			System.out.println(this.getName()+". Actualizar Colegio");
			colegio=getColegioUpdate(d);
			if (!integracion.actualizarDaneInstitucion(colegio)) {
				mensaje = "Error actualizando Inst: " + colegio.getDane11Old() + " por Inst: " + colegio.getDane11() + ". (" + integracion.getMensaje() + ")";
				Logger.print("-1", mensaje, 9, 1, this.toString());
				d.setEstado(Params.ERROR);
				d.setMensaje(mensaje);
				return;
			}
			d.setEstado(Params.TERMINADO);
			break;
		case Params.F_ELIMINACION:
			System.out.println(this.getName()+". Eliminar Colegio");
			colegio=getColegioDelete(d);
			if (!integracion.eliminarInstitucion(colegio)) {
				mensaje = "Error Eliminando Inst: " + colegio.getDane11Old()+". (" + integracion.getMensaje() + ")";
				Logger.print("-1", mensaje, 9, 1, this.toString());
				d.setEstado(Params.ERROR);
				d.setMensaje(mensaje);
				return;
			}
			d.setEstado(Params.TERMINADO);
			break;
		}
	}

	public void sede(Datos d) throws Exception {
		String mensaje = null;
		Sede sede=null;
		switch (d.getFuncion()) {
		case Params.F_INSERCIONSEDE:
			System.out.println(this.getName()+". Insertar Sede");
			sede=getSedeInsert(d);
			if (!integracion.insertarSede(sede)) {
				mensaje = "Error insertando Sede: " + sede.getDaneSede() + ". Para Inst=" + sede.getDaneColegio() + "(" + integracion.getMensaje() + ")";
				Logger.print("-1", mensaje, 9, 1, this.toString());
				d.setEstado(Params.ERROR);
				d.setMensaje(mensaje);
				return;
			}
			d.setEstado(Params.TERMINADO);
			break;
		case Params.F_ACTUALIZACIONDANESEDE:
			System.out.println(this.getName()+". actualizar dane sede ");
			sede=getSedeUpdate(d);
			if (!integracion.actualizarDaneSede(sede)) {
				mensaje = "Error actualizando DANE de sede : " + sede.getDaneSedeOld() + " por DANE: " + sede.getDaneSede() + "." + integracion.getMensaje();
				Logger.print("-1", mensaje, 9, 1, this.toString());
				d.setEstado(Params.ERROR);
				d.setMensaje(mensaje);
				return;
			}
			d.setEstado(Params.TERMINADO);
			break;
		case Params.F_ELIMINACIONSEDE:
			System.out.println(this.getName()+". eliminar sede ");
			sede=getSedeDelete(d);
			if (!integracion.eliminarSede(sede)) {
				mensaje = "Error eliminando sede: " + sede.getDaneSedeOld() + " para Inst: " + sede.getDaneColegioOld() + "." + integracion.getMensaje();
				Logger.print("-1", mensaje, 9, 1, this.toString());
				d.setEstado(Params.ERROR);
				d.setMensaje(mensaje);
				return;
			}
			d.setEstado(Params.TERMINADO);
			break;
		}
	}

	private Colegio getColegioInsert(Datos d) throws Exception {
		Colegio item=new Colegio();
		item.setId(d.getId());
		item.setDane12(Long.parseLong(d.getP1()));
		item.setDane11(Long.parseLong(d.getP2()));
		item.setLocalidad(Integer.parseInt(d.getP3()));
		item.setNombre(d.getP4());
		item.setEstado(d.getP5());
		item.setTipo(d.getP6());
		item.setZona(Integer.parseInt(d.getP7()));
		item.setDireccion(d.getP8());
		return item;
	}

	private Colegio getColegioUpdate(Datos d) throws Exception {
		//dane12Old,dane11Old,dane12New,dane11New,localidad,nombre,estado,tipo, zona,direccion
		Colegio item=new Colegio();
		item.setId(d.getId());
		item.setDane12Old(Long.parseLong(d.getP1()));
		item.setDane11Old(Long.parseLong(d.getP2()));
		item.setDane12(Long.parseLong(d.getP3()));
		item.setDane11(Long.parseLong(d.getP4()));
		item.setLocalidad(Integer.parseInt(d.getP5()));
		item.setNombre(d.getP6());
		item.setEstado(d.getP7());
		item.setTipo(d.getP8());
		item.setZona(Integer.parseInt(d.getP9()));
		item.setDireccion(d.getP10());
		return item;
	}

	private Colegio getColegioDelete(Datos d) throws Exception {
		//dane12Old,dane11Old
		Colegio item=new Colegio();
		item.setId(d.getId());
		item.setDane12Old(Long.parseLong(d.getP1()));
		item.setDane11Old(Long.parseLong(d.getP2()));
		return item;
	}
	
	private Sede getSedeInsert(Datos d) throws Exception {
		//daneColegio,daneSede,codigo,nombre,direccion,telefono,estado
		Sede item=new Sede();
		item.setId(d.getId());
		item.setDaneColegio(Long.parseLong(d.getP1()));
		item.setDaneSede(Long.parseLong(d.getP2()));
		item.setCodigo(Integer.parseInt(d.getP3()));
		item.setNombre(d.getP4());
		item.setDireccion(d.getP5());
		item.setTelefono(d.getP6());
		item.setEstado(d.getP7());
		return item;
	}
	
	private Sede getSedeUpdate(Datos d) throws Exception {
		//daneColegioOld,daneColegioNew,daneSedeOld,daneSedeNew,codigoOld,
		//codigoNew,nombre,direccion,telefono,estado
		Sede item=new Sede();
		item.setId(d.getId());
		item.setDaneColegioOld(Long.parseLong(d.getP1()));
		item.setDaneColegio(Long.parseLong(d.getP2()));
		item.setDaneSedeOld(Long.parseLong(d.getP3()));
		item.setDaneSede(Long.parseLong(d.getP4()));
		item.setCodigoOld(Integer.parseInt(d.getP5()));
		item.setCodigo(Integer.parseInt(d.getP6()));
		item.setNombre(d.getP7());
		item.setDireccion(d.getP8());
		item.setTelefono(d.getP9());
		item.setEstado(d.getP10());
		return item;
	}
	
	private Sede getSedeDelete(Datos d) throws Exception {
		//daneColegioOld,daneSedeOld,codigoOld
		Sede item=new Sede();
		item.setId(d.getId());
		item.setDaneColegioOld(Long.parseLong(d.getP1()));
		item.setDaneSedeOld(Long.parseLong(d.getP2()));
		item.setCodigoOld(Integer.parseInt(d.getP3()));
		return item;
	}
	
	private Estudiante getEstudianteInsert(Datos d) throws Exception {
		/*
			codigo,
			anhovig,
			inst,
			sede,
			jorn,
			gra,
			gru,
			metod,
			tipo_Id,
			num_Id,
			apellido1,
			apellido2,
			nombre1,
			nombre2,
			estado,
			genero,
			fecha_naci,
			dd_naci, 
			dm_naci,
			dd_exp, 
			dm_exp,
			grupo,
		 */
		Estudiante estudiante = new Estudiante();
		estudiante.setId(d.getId());
		estudiante.setCodigo(Long.parseLong(d.getP1()));
		estudiante.setVigencia(Integer.parseInt(d.getP2()));
		estudiante.setDaneInst(Long.parseLong(d.getP3()));
		estudiante.setSede(Long.parseLong(d.getP4()));//
		estudiante.setJornada(Long.parseLong(d.getP5()));
		estudiante.setGrado(Long.parseLong(d.getP6()));
		estudiante.setGrupo(Long.parseLong(d.getP7()));//
		estudiante.setMetodologia(Integer.parseInt(d.getP8()));//
		estudiante.setTipoId(Integer.parseInt(d.getP9()));
		estudiante.setNumId(d.getP10());
		estudiante.setApellido1(d.getP11());
		estudiante.setApellido2(d.getP12());
		estudiante.setNombre1(d.getP13());
		estudiante.setNombre2(d.getP14());
		estudiante.setEstado(Integer.parseInt(d.getP15()));
		estudiante.setGenero(Integer.parseInt(d.getP16()));//
		estudiante.setFechaNaci(d.getP17());
		estudiante.setDdNaci(Long.parseLong(d.getP18()));//
		estudiante.setDmNaci(Long.parseLong(d.getP19()));//
		estudiante.setDdExp(Long.parseLong(d.getP20()));//
		estudiante.setDmExp(Long.parseLong(d.getP21()));//
		estudiante.setNomGrupo(d.getP22());
		return estudiante;
	}

	private Estudiante getEstudianteUpdateUbicacion(Datos d) throws Exception {
		/*
			codigo,
			anhovig,
			inst,
			sede,
			jorn,
			gra,
			gru,
			metod,
			tipo_Id,
			num_Id,
			estado,
			instOld,
			sedeOld,
			jornOld,
			graOld,
			gruOld,
			metodOld,
			grupo,
		 */
		Estudiante estudiante = new Estudiante();
		estudiante.setFuncion(Params.F_ACTUALIZACIONUBICACIONESTUDIANTE);
		estudiante.setId(d.getId());
		estudiante.setCodigo(Long.parseLong(d.getP1()));
		estudiante.setVigencia(Integer.parseInt(d.getP2()));
		estudiante.setDaneInst(Long.parseLong(d.getP3()));
		estudiante.setSede(Long.parseLong(d.getP4()));//
		estudiante.setJornada(Long.parseLong(d.getP5()));
		estudiante.setGrado(Long.parseLong(d.getP6()));
		estudiante.setGrupo(Long.parseLong(d.getP7()));//
		estudiante.setMetodologia(Integer.parseInt(d.getP8()));//

		estudiante.setTipoId(Integer.parseInt(d.getP9()));
		estudiante.setNumId(d.getP10());
		estudiante.setEstado(Integer.parseInt(d.getP11()));

		estudiante.setOldDaneInst(Long.parseLong(d.getP12()));
		estudiante.setOldSede(Long.parseLong(d.getP13()));//
		estudiante.setOldJornada(Long.parseLong(d.getP14()));
		estudiante.setOldGrado(Long.parseLong(d.getP15()));
		estudiante.setOldGrupo(Long.parseLong(d.getP16()));//
		estudiante.setOldMetodologia(Integer.parseInt(d.getP17()));//

		estudiante.setNomGrupo(d.getP18());
		return estudiante;
	}

	private Estudiante getEstudianteUpdateId(Datos d) throws Exception {
		/*
			 codigo,
			 anhovig,
			 tipo_IdNew,
			 num_IdNew,
			 apellido1,
			 apellido2,
			 nombre1,
			 nombre2,
			 estado,
			 dd_exp,
			 dm_exp,
			 genero
		 */
		Estudiante estudiante = new Estudiante();
		estudiante.setId(d.getId());
		estudiante.setCodigo(Long.parseLong(d.getP1()));
		estudiante.setVigencia(Integer.parseInt(d.getP2()));
		estudiante.setTipoId(Integer.parseInt(d.getP3()));
		estudiante.setNumId(d.getP4());
		estudiante.setApellido1(d.getP5());
		estudiante.setApellido2(d.getP6());
		estudiante.setNombre1(d.getP7());
		estudiante.setNombre2(d.getP8());
		estudiante.setEstado(Integer.parseInt(d.getP9()));
		estudiante.setDdExp(Long.parseLong(d.getP10()));//
		estudiante.setDmExp(Long.parseLong(d.getP11()));//
		estudiante.setGenero(Integer.parseInt(d.getP12()));//
		return estudiante;
	}

	/**
	 * @param running The running to set.
	 */
	public static void setRunning(boolean running) {
		Integrador.running = running;
	}
}
