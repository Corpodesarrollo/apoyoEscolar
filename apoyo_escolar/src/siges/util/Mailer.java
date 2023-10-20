package siges.util;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Session;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;

import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.institucion.correoLider.beans.ParamsMail;
import siges.institucion.correoLider.dao.CorreoDAO;
import siges.util.dao.utilDAO;

import javax.mail.internet.InternetAddress;


/**
 *	VERSION		FECHA			AUTOR				DESCRIPCION
 *		1.0		03/05/2018		JORGE CAMACHO		Se agregó la clase al proyecto
 *
*/

public class Mailer {
	
    private String d_host;
    private String d_port;
	private String d_fromAddress;
	
	private utilDAO utilDAO;
	private Cursor cursor;
	
	public boolean getParametrosCorreo() {
		
        try {
        	
            ResourceBundle props = ResourceBundle.getBundle("mail");
            
            d_host = props.getString("mail.host");
            d_port = props.getString("mail.port");
            //d_fromAddress = props.getString("mail.fromAddress");
            
            return true;
            
        } catch (Exception ex) {
            System.out.println("Error leyendo el archivo: mail.properties: " + ex.toString());
            return false;
        }
        
    }
	
	public void enviarCorreo(String strCorreo, String strAsunto, String strCuerpo)  throws MessagingException  {
		
				
		/*
		this.getParametrosCorreo(); 
		final String clave = "Apoyo2016%";
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", d_host);
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.starttls.enable", "true");
		props.setProperty("mail.smtp.submitter", d_fromAddress);
		props.put("mail.smtp.port", d_port);
		props.put("mail.smtp.socketFactory.port", d_port);
		*/
	
		
			
		utilDAO=new utilDAO();	
		try{
		ParamsMail mail=utilDAO.getParamsMail();
		Properties props = new Properties();
		props.setProperty("mail.smtp.host",  mail.getHost());
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.starttls.enable", "true");
		props.setProperty("mail.smtp.submitter", mail.getFrom());
		props.put("mail.smtp.port", mail.getPort());
		props.put("mail.smtp.socketFactory.port", mail.getPort());
		
		SMTPAuthenticator authenticator = new SMTPAuthenticator(mail.getFrom(), mail.getPassword());
		Session session = Session.getInstance(props, authenticator);		
		
		
			
			MimeMessage msg = new MimeMessage(session);
			InternetAddress address[] = { new InternetAddress(strCorreo) };
			
			msg.setSubject(strAsunto);
			msg.setSentDate(new Date());
			msg.setHeader("X-Mailer", "HEADER");
			msg.setFrom(new InternetAddress(mail.getFrom()));
			msg.setRecipients(javax.mail.Message.RecipientType.TO, address);
			
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setContent(strCuerpo, "text/html; charset=utf-8");
			
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp1);
			
			msg.setContent(mp);
			Transport.send(msg);
			
		} catch (MessagingException mex) {
			throw mex;
		}
		catch (Exception mex) {
			// TODO Auto-generated catch block
			mex.printStackTrace();
		}
		
	}
	
	public void enviarCorreoGrupoApoyo(String strAsunto, String strCuerpo) throws MessagingException {
		
		/*this.getParametrosCorreo();
		
		final String clave = "Apoyo2016%";

		Properties props = new Properties();
		props.setProperty("mail.smtp.host", d_host);
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.starttls.enable", "true");
		props.setProperty("mail.smtp.submitter", d_fromAddress);
		props.put("mail.smtp.port", d_port);
		props.put("mail.smtp.socketFactory.port", d_port);
		
		SMTPAuthenticator authenticator = new SMTPAuthenticator(d_fromAddress, clave);
		Session session = Session.getInstance(props, authenticator);
		*/
		
		
		utilDAO=new utilDAO();	
		
		ParamsMail mail;
		try {
			mail = utilDAO.getParamsMail();
		
		Properties props = new Properties();
		props.setProperty("mail.smtp.host",  mail.getHost());
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.starttls.enable", "true");
		props.setProperty("mail.smtp.submitter", mail.getFrom());
		props.put("mail.smtp.port", mail.getPort());
		props.put("mail.smtp.socketFactory.port", mail.getPort());
		
		SMTPAuthenticator authenticator = new SMTPAuthenticator(mail.getFrom(), mail.getPassword());
		Session session = Session.getInstance(props, authenticator);		
		
		
			
			MimeMessage msg = new MimeMessage(session);
			InternetAddress address[] = { new InternetAddress( mail.getFrom()) };
			//InternetAddress address[] = { new InternetAddress("jecamacho@educacionbogota.gov.co") };
			
			msg.setSubject(strAsunto);
			msg.setSentDate(new Date());
			msg.setHeader("X-Mailer", "HEADER");
			msg.setFrom(new InternetAddress( mail.getFrom()));
			msg.setRecipients(javax.mail.Message.RecipientType.TO, address);
			
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setContent(strCuerpo, "text/html; charset=utf-8");
			
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp1);
			
			msg.setContent(mp);
			Transport.send(msg);
			
		} catch (MessagingException mex) {
			throw mex;
		}
		catch (Exception mex) {
			// TODO Auto-generated catch block
			mex.printStackTrace();
		}
		
	}
	
	






	
}

