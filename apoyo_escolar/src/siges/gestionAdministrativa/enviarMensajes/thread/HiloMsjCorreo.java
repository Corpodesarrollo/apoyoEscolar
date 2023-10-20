package siges.gestionAdministrativa.enviarMensajes.thread;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.validator.GenericValidator;

import siges.dao.Cursor;
import siges.gestionAdministrativa.enviarMensajes.dao.EnviarMensajesDAO;
import siges.gestionAdministrativa.enviarMensajes.io.MailIO;
import siges.gestionAdministrativa.enviarMensajes.vo.MailVO;
import siges.gestionAdministrativa.enviarMensajes.vo.MensajesVO;
import siges.gestionAdministrativa.enviarMensajes.vo.PersonalVO;

public class HiloMsjCorreo extends Thread {
	public static boolean running = true; 
	public EnviarMensajesDAO enviarMensajesDAO = new EnviarMensajesDAO(new Cursor());
 
 
	private ResourceBundle rb; 
	/**
	 * @function:  
	 */
	public  HiloMsjCorreo(){
		System.out.println("[ENVIAR CORREOS] HILO INICIA...");
		Thread t = new Thread(this);
		System.out.println("init Hilo, Nombre del Thread \"HiloUpdateCorreos\"" );
		t.setName("HiloMsjCorreo");
	}
	
	/**
	 * @function: E
	 */
	
	
	
	
	public void run() { 
		System.out.println("[ENVIAR_CORREOS] CONTROLLER HILO INICIANDO 2.1 ");
	 
		try{
			
			if (!running) {
				System.out.println("[ENVIAR_CORREOS] CONTROLLER THREAD FINALIZANDO");
				return;
			}
			procesarHilo();
		}catch(Exception ex){
			System.out.println("[ENVIAR_CORREOS] Error HILO: "+ex.getMessage());ex.printStackTrace();
		}
	}
	
	
	private void procesarHilo(){
		try {
			Calendar cc = Calendar.getInstance(); 
			//int diaActual = cc.get(Calendar.DAY_OF_WEEK)-1;
			int horaActual = cc.get(Calendar.HOUR_OF_DAY); 
			//int minutoActual = cc.get(Calendar.MINUTE); 
			int horaProcesar[] = {10,13};
			  System.out.println("[ENVIAR CORREOS] horaActual " + horaActual);
			
			if( isValidarHora(horaActual, horaProcesar)){
				enviarCorreos();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR: Enviando correos " + e.getMessage() );
		}
		}
 
	  private void enviarCorreos() throws Exception{
 
			
			//1. OBtener lista personas
			
			List listaErrores = new ArrayList();
			Map p =  enviarMensajesDAO.getMailParams();
			
			
		
			
			
			List listaCorreo = new ArrayList();
			List listaMensaje = enviarMensajesDAO.getListaMensaje(); 
			System.out.println("listaMensaje "+ listaMensaje.size() );
		
			
			for (int j=0;j< listaMensaje.size();j++) {
				MensajesVO msjVO = (MensajesVO)listaMensaje.get(j);
				
				MailIO mIO = new MailIO(); 
				MailVO mailVO=new MailVO();
				mailVO.setMailParams(p);
				mailVO.setMailAsunto(msjVO.getMsjasunto());
				mailVO.setMailMensaje(msjVO.getMsjcontenido());
				mailVO.setMailBandera(1);
				
				String codPefiles =  msjVO.getMsjenviadoaperfil();
				String codLocals = msjVO.getMsjenviadoalocal();
				String codInsts = msjVO.getMsjenviadoacoleg();
				String codSedes = msjVO.getMsjenviadoasede();
				String codJords = msjVO.getMsjenviadoajorn();
				
				List listaPersonas = enviarMensajesDAO.getListaPersona(codPefiles, codLocals, codInsts, codSedes, codJords);
				 System.out.println("listaPersonas " + listaPersonas.size() );
				for (int i=0;i< listaPersonas.size();i++) {
					PersonalVO personaVO = new PersonalVO();
					if(personaVO.getPeremail() != null && GenericValidator.isEmail(personaVO.getPeremail())){
						listaCorreo.add(personaVO.getPeremail());
					}else{
						StringBuffer msjError = new StringBuffer();
						msjError.append("No se envio correo a la persona");
						msjError.append(" cedula ");
						msjError.append(personaVO.getPernombre1());
						msjError.append(personaVO.getPernumdocum());
						listaErrores.add(msjError.toString());
					}
				}  // for personas
				mailVO.setMailCorreos(listaCorreo);
				//mIO.enviarCorreo(mailVO);
			}// for mensajes
		}
	
	
	
	
	
	 private boolean isValidarHora(int hora, int horas[]){
		 for(int i=0;i < horas.length;i++){
			 if(horas[i] == hora){
				 return true;
			 }
		 }
		 return false;
	 }
	
	
	
	/**
	 * @funct
	 * ion: Destructor
	 * 
	 */
	public void destroy() {
		System.out.println("[ENVIAR CORREOS] HILO TERMINANDO");
		running = false;
	}
	
	 
}
