package siges.gestionAdministrativa.cierreVigencia.thread;

import java.util.Date;
import java.util.Map;

 
import siges.dao.Cursor;
import siges.gestionAdministrativa.cierreVigencia.dao.CierreVigenciaDAO;


public class Hilo extends Thread {
	public static boolean running = true; 
	CierreVigenciaDAO cierreVigenciaDAO=new CierreVigenciaDAO(new Cursor());
	
	
	
	/**
	 * @function:  
	 */
	public  Hilo(){
		Thread t = new Thread(this);
		System.out.println("init Hilo, Nombre del Thread \"HiloCierreVigencia\"" );
		t.setName("HiloCierreVigencia");
	}
	
	/**
	 * @function: E
	 */
	
	
	
	
	public void run() {
		long tempSleep=60000;
		System.out.println("[APOYO] HiloCierreVigencia CONTROLLER HILO INICIANDO 2.1 ");
		Map parametros=null;
		int iniciar=0;
		int intervalo=0;
		try{
			if (!running) {
				System.out.println("[APOYO] HiloCierreVigencia CONTROLLER THREAD FINALIZANDO");
				return;
			}
			procesarHilo(cierreVigenciaDAO);
			
			
		}catch(Exception ex){
			System.out.println("[APOYO] Error HILO: "+ex.getMessage());ex.printStackTrace();
		}
	}
	
	
	/**
	 * @function:  Se encarga hacer los camibos de estado del proceso de cierre
	 */
	private void procesarHilo(CierreVigenciaDAO cierreVigenciaDAO){
		try{
			cierreVigenciaDAO.llenarTablaDatosCierreVig();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * @funct
	 * ion: Destructor
	 * 
	 */
	public void destroy() {
		System.out.println(new Date() +"[APOYO] HiloCierreVigencia CONTROLLER HILO INICIANDO 2.1  TERMINANDO");
		running = false;
	}
}
