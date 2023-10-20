/**
 * 
 */
package siges.institucion.correoLider.io;

import java.util.List;

import org.apache.commons.validator.GenericValidator;

import siges.common.mail.CorreoManager;
import siges.institucion.correoLider.beans.CorreoLiderVO;
import siges.institucion.correoLider.beans.MailingListVO;
import siges.institucion.correoLider.beans.ParamsMail;

public class CorreoLiderIO {

	public CorreoLiderVO enviarCorreo(CorreoLiderVO correo) throws Exception {
		CorreoManager correoManager = new CorreoManager();
		ParamsMail mail = correo.getParamsMail();
		correoManager.setFrom(mail.getFrom());// CORREO REMITENTE
		correoManager.setMailHost(mail.getHost());// HOST DE ENVIO
		correoManager.setFilename(correo.getCorrAdjunto());// ADJUNTO
		correoManager.setSubject(correo.getCorrAsunto());// ASUNTO
		correoManager.setMensaje(correo.getCorrMensaje());// MENSAJE
		List l = correo.getCorrCorreos();
		int totCuentas=l.size();
		int correosErrados=0;
		correo.setCorrTotal(l.size());
		for (int i = 0; i < l.size(); i++) {
			MailingListVO listVO = (MailingListVO) l.get(i);
			if(!GenericValidator.isEmail(listVO.getMail())){
				l.remove(i);
				correosErrados++;
				i--;
			}	
		}
		String toMany[]=new String[l.size()];
		correo.setCorrEnviados(l.size());		
		for (int i = 0; i < l.size(); i++) {
			MailingListVO listVO = (MailingListVO) l.get(i);
			//System.out.println("corrreo="+listVO.getMail());
			toMany[i]=listVO.getMail();
		}	
		correoManager.setToMany(toMany);// CORREO DESTINO
		if (correo.getCorrBandera()!=1) {
			correoManager.enviarCorreo();
		} else {
			correoManager.enviarCorreoAdjunto();
		}
		if(correosErrados==totCuentas){
			throw new Exception("No se pudo enviar a ningun destino. Error con los correos");
		}
		return correo;
	}
}
