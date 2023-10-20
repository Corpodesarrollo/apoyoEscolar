/**
 * 
 */
package siges.adminParamsInst.io;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.validator.GenericValidator;

import siges.adminParamsInst.vo.MailVO;
import siges.common.mail.CorreoManager;
import siges.dao.Ruta;
import siges.dao.Ruta2;

/**
 * 3/07/2008
 * 
 * @author Latined
 * @version 1.2
 */
public class MailIO {
	ResourceBundle rb = ResourceBundle
			.getBundle("siges.adminParamsInst.bundle.AdminParamsInst");

	public String[] subirFile(FileItem item, String path, String usuario)
			throws Exception {
		String pathSubir = Ruta.get(path, rb.getString("correo.pathSubir")
				+ "." + usuario);
		String pathTemporal = Ruta2.get(path, rb.getString("correo.temporal"));
		String fileName = null;
		try {
			fileName = item.getName();
			java.io.File f = new java.io.File(pathSubir);
			if (!f.exists()) {
				FileUtils.forceMkdir(f);
			}
			fileName = fileName.substring(fileName.lastIndexOf('/') + 1,
					fileName.length());
			fileName = fileName.substring(fileName.lastIndexOf('\\') + 1,
					fileName.length());
			f = new java.io.File(pathSubir + fileName);
			item.write(f);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return new String[] { pathSubir, fileName };
	}

	public MailVO enviarCorreo(MailVO correo) throws Exception {
		// System.out.println("enviarCorreo++++++++++++++++++++++++++++++++++");
		CorreoManager correoManager = new CorreoManager();
		try {
			Map mail = correo.getMailParams();
			// System.out.println("PARAMS CORREOS: FROM "+(String)mail.get("FROM")+"   MAILHOST: "+(String)mail.get("MAILHOST")+"  PASSS: "+(String)mail.get("PASSWORD"));
			correoManager.setFrom((String) mail.get("FROM"));// CORREO REMITENTE
			correoManager.setMailHost((String) mail.get("MAILHOST"));// HOST DE
																		// ENVIO
			correoManager.setFilename(correo.getMailNombreAdjunto());// ADJUNTO
			// System.out.println("JULIO15 PARAM FILE ADJUNTO : "+correo.getMailNombreAdjunto());
			correoManager.setSubject(correo.getMailAsunto());// ASUNTO
			correoManager.setMensaje(correo.getMailMensaje());// MENSAJE
			correoManager.setPassword((String) mail.get("PASSWORD"));// MENSAJE
			correoManager.setPort((String) mail.get("PORT"));// PORT
			List l = correo.getMailCorreos();
			int totCuentas = l.size();
			// System.out.println("totCuentas " + totCuentas);
			int correosErrados = 0;
			// System.out.println("l.size() " + l.size());
			correo.setMailTotal(l.size());
			// System.out.println("l.size() " + l.size());
			for (int i = 0; i < l.size(); i++) {
				String listVO = (String) l.get(i);
				if (!GenericValidator.isEmail(listVO)) {
					l.remove(i);
					correosErrados++;
					i--;
				}
			}
			// System.out.println("l.size() " + l.size());
			// System.out.println("DESPUES DE VALIDAD l.size() " + l.size());
			if (l.size() == 0) {
				throw new Exception(
						"No se pudo enviar a ningun destino. No hay correos asociados");
			}
			String toMany[] = new String[l.size()];
			correo.setMailEnviados(l.size());
			for (int i = 0; i < l.size(); i++) {
				String listVO = (String) l.get(i);
				toMany[i] = listVO;
			}
			// System.out.println("toMany " + toMany.length );
			correoManager.setToMany(toMany);// CORREO DESTINO
			// System.out.println("correo.getMailBandera() " +
			// correo.getMailBandera());
			if (correo.getMailBandera() != 1) {
				// System.out.println("ENVIANDO CORREO...........");

				// System.out.println("enviarCorreo:  - Inicia   Hora: "+new
				// java.sql.Timestamp(System.currentTimeMillis()).toString());
				correoManager.enviarCorreoPassword();
				// System.out.println("enviarCorreo:  - Termina   Hora: "
				// + new java.sql.Timestamp(System.currentTimeMillis())
				// .toString());
			} else {
				// correoManager.enviarCorreoAdjuntoPasword();
			}
			if (correosErrados == totCuentas) {
				throw new Exception(
						"No se pudo enviar a ningun destino. Error con los correos");
			}
			return correo;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			// System.out.println("finalizo el enviao de correo");
		}
	}

}
