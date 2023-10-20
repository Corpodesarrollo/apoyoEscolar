/**
 * 
 */
package siges.rotacion2.thread.thread;

import siges.dao.Cursor;
import siges.rotacion2.thread.dao.Rotacion2DAO;
import siges.rotacion2.thread.vo.ParamsVO;
import siges.rotacion2.thread.vo.RotacionVO;

/**
 * 31/08/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class Rotacion2Thread extends Thread {
	public static final long serialVersionUID=1;
	private static boolean running = true;
	private Rotacion2DAO dao=new Rotacion2DAO(new Cursor());
	
	public Rotacion2Thread() {
		super("Rotacion");
	}
	
	/**
	 * @param running The running to set.
	 */
	public static void setRunning(boolean running) {
		Rotacion2Thread.running = running;
	}
	
	public void run() {
		try {
			while (true) {
				if (!running) {
					System.out.println(this.getName()+". FINALIZANDO");
					return;
				}
				Thread.sleep(60000);
				procesar();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(this.getName()+". Error Hilo: " + ex);
		}
	}

	public void procesar() {
		RotacionVO rotacion=null;
		try {
			//verifica si el hilo esta activo
			if (!dao.isActivo()) {
				System.out.println(this.getName()+". inactivo:" + new java.sql.Timestamp(System.currentTimeMillis()).toString());
				return;
			}
			//restaura las peticiones que quedaron en ejecucion al momento de bajar el servicio
			dao.restaurarSolicitudes();
			//busca la solicitud mas antigua
			rotacion = dao.getSolicitud();
			//no hay solicitudes pendientes y retorna
			if (rotacion == null) {	System.out.println(this.getName()+". No hay solicitudes"); return;	}
			System.out.println(this.getName()+". Inicia a procesar solicitud");
			//cambia el estado de la solicitud
			rotacion.setRotEstado(ParamsVO.SOLICITUD_PROCESANDO);
			dao.actualizarSolicitud(rotacion);
			//inicia el procesamiento de la peticion
			rotacion=generarRotacion(rotacion);
			//cambia el estado
			//lo quito para saber si es por esto que no le cambia el estado real
			//dao.actualizarSolicitud(rotacion);
		} catch (Exception e) {
			System.out.println(this.getName()+". Error procesando solicitud");
			e.printStackTrace();
			if(rotacion!=null){
				rotacion.setRotEstado(ParamsVO.SOLICITUD_ERROR);
				rotacion.setRotMensaje(e.getMessage());
				try {
					dao.actualizarSolicitud(rotacion);
				} catch (Exception ex) {System.out.println("Error actualizando solicitud:"+ex.getMessage());};
			}
		}
		System.out.println(this.getName()+". Finaliza proceso solicitud");
	}
	
	private RotacionVO generarRotacion(RotacionVO rotacion)throws Exception{
		try{
			dao.procesarRotacion(rotacion.getRotId());
		}catch(Exception e){
			System.out.println("Error aqui: "+e.getMessage());
			rotacion.setRotEstado(ParamsVO.SOLICITUD_ERROR);
			rotacion.setRotMensaje("Error interno: "+e.getMessage());
		}
		return rotacion;
	}
}
