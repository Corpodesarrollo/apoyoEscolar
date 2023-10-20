/**
 * 
 */
package siges.gestionAdministrativa.enviarMensajes.io;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.validator.GenericValidator;

import siges.common.mail.CorreoManager;
import siges.dao.Ruta;
import siges.dao.Ruta2;
import siges.gestionAdministrativa.enviarMensajes.vo.MailVO;


/**
 * 3/07/2008 
 * @author Latined
 * @version 1.2
 */
public class MailIO {
	ResourceBundle rb = ResourceBundle.getBundle("liquidacion.liquidacion.reportes.bundle.liq_reportes");
	
	public String[] subirFile(FileItem item,String path,String usuario)throws Exception {
		String pathSubir = Ruta.get(path, rb.getString("correo.pathSubir") + "." +usuario);
		String pathTemporal = Ruta2.get(path, rb.getString("correo.temporal"));
		String fileName =null;
		try{
			fileName = item.getName();
			java.io.File f = new java.io.File(pathSubir);
			if (!f.exists()){ FileUtils.forceMkdir(f); }
			fileName=fileName.substring(fileName.lastIndexOf('/')+1,fileName.length());
			fileName=fileName.substring(fileName.lastIndexOf('\\')+1,fileName.length());
			f = new java.io.File(pathSubir + fileName);
			item.write(f);
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}		
		return new String[]{pathSubir,fileName};
	}
	
	public MailVO enviarCorreo(MailVO correo) throws Exception {
		CorreoManager correoManager = new CorreoManager();
		try{
			Map mail = correo.getMailParams();
			System.out.println("PARAMS CORREOS: FROM "+(String)mail.get("FROM")+"   MAILHOST: "+(String)mail.get("MAILHOST")+"  PASSS: "+(String)mail.get("PASSWORD"));
			correoManager.setFrom((String)mail.get("FROM"));// CORREO REMITENTE
			correoManager.setMailHost((String)mail.get("MAILHOST"));// HOST DE ENVIO
			correoManager.setFilename(correo.getMailNombreAdjunto());// ADJUNTO
			System.out.println("JULIO15 PARAM FILE ADJUNTO : "+correo.getMailNombreAdjunto());
			correoManager.setSubject(correo.getMailAsunto());// ASUNTO
			correoManager.setMensaje(correo.getMailMensaje());// MENSAJE			
			correoManager.setPassword((String)mail.get("PASSWORD"));// MENSAJE
			List l = correo.getMailCorreos();
			int totCuentas=l.size();
			int correosErrados=0;
			correo.setMailTotal(l.size());
			for (int i = 0; i < l.size(); i++) {
				String listVO = (String) l.get(i);
				if(!GenericValidator.isEmail(listVO)){
					l.remove(i);
					correosErrados++;
					i--;
				}	
			}
			if(l.size()==0){
				throw new Exception("No se pudo enviar a ningun destino. No hay correos asociados");
			}
			String toMany[]=new String[l.size()];
			correo.setMailEnviados(l.size());		
			for (int i = 0; i < l.size(); i++) {
				String listVO = (String) l.get(i);
				toMany[i]=listVO;
			}	
			correoManager.setToMany(toMany);// CORREO DESTINO
			if (correo.getMailBandera()!=1) {
				correoManager.enviarCorreoPassword();
			} else {
				//correoManager.enviarCorreoAdjuntoPasword();
			}
			if(correosErrados==totCuentas){
				throw new Exception("No se pudo enviar a ningun destino. Error con los correos");
			}
			return correo;
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} 
	}

}
