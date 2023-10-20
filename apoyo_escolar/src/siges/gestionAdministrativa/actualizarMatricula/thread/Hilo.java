package siges.gestionAdministrativa.actualizarMatricula.thread;

import java.util.Map;

import siges.gestionAdministrativa.actualizarMatricula.dao.ActualizarMatrDAO;
import siges.dao.Cursor;


public class Hilo extends Thread {
	public static boolean running = true; 
	ActualizarMatrDAO actualizarMatrDAO=new ActualizarMatrDAO(new Cursor());

	
	
   /**
 * @function:  
 */
public  Hilo(){
	   Thread t = new Thread(this);
	   System.out.println("init Hilo, Nombre del Thread \"HiloUpdateMatricula\"" );
	   t.setName("HiloUpdateMatricula");
   }
	
	/**
	 * @function: E
	 */



   
	public void run() {
		long tempSleep=60000;
		System.out.println("[APOYO] CONTROLLER HILO INICIANDO 2.1 ");
		Map parametros=null;
		 int iniciar=0;
		int intervalo=0;
		try{
			//Thread.sleep(tempSleep * 3);
			 
				
			
				
				if (!running) {
					System.out.println("[APOYO] CONTROLLER THREAD FINALIZANDO");
					return;
				}
				//parametros = actualizarMatrDAO.getParametros(Param.PAR_HILO);
				//VALIDAR HILOS DE ALERTAS
//				iniciar=Integer.parseInt((String)parametros.get(Param.PAR_HILO_CIERRE));
//				intervalo=Integer.parseInt((String)parametros.get(Param.PAR_HILO_INTERVALO));
//				System.out.println("En hilo: validar hilos estado [iniciar]="+iniciar);
//				if(iniciar==1){
                  procesarHilo(actualizarMatrDAO);
					
				//}
				//DORMIR HILO 
				//Thread.sleep(tempSleep * intervalo);
		 
		}catch(Exception ex){
			System.out.println("[DUC] Error HILO: "+ex.getMessage());ex.printStackTrace();
		}
	}
	
	
	/**
	 * @function:  Se encarga hacer los camibos de estado del proceso de cierre
	 */
	private void procesarHilo(ActualizarMatrDAO actualizarMatrDAO){
		try{
			actualizarMatrDAO.llenarTablaDatosMatricula();
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
		System.out.println("[DUC]HILO TERMINANDO");
		running = false;
	}
}
