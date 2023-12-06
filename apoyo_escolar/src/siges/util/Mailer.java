package siges.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import siges.dao.Cursor;
import siges.institucion.correoLider.beans.ParamsMail;
import siges.util.dao.utilDAO;


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
	
	private String uriApiToken, uriApiMail;
	private ResourceBundle rbBol;
	
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
	
	public boolean notificacionReporte(String username, long inst, long sede, long jornada, MailDTO mail){
		rbBol = ResourceBundle.getBundle("siges.boletines.bundle.boletines");
		uriApiMail = rbBol.getString("boletines_uri_api_send_email");
		String bearerToken = getToken(username, inst, sede, jornada);
		try{
			if(!bearerToken.equals("")){
				final URL url = new URL(uriApiMail);// url API
				//String[] emails = {"jose.lopez@linktic.com"};
		        //mail.setEmails(emails);
				// report
				final java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
				StringBuilder response = new StringBuilder();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
				conn.setRequestProperty("Authorization", bearerToken);
				conn.setRequestProperty("Accept", "application/json");
				
				// Crear un objeto JSON
				String jsonInput = objectMapper.writeValueAsString(mail);				
				
				//System.out.println(jsonInput.toString());
	
				// Escribir la cadena JSON en el cuerpo de la solicitud
	            try (OutputStream os = conn.getOutputStream();
	                 OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8")) {
	                osw.write(jsonInput);
	                osw.flush();
	            }
	            // Leer la respuesta del servidor
	            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
	                String line;

	                while ((line = reader.readLine()) != null) {
	                    response.append(line);
	                }
	            }
	            // Cerrar la conexión
	            conn.disconnect();
				if(response.equals("true"))
					return true; // se envio la notificación
				} else {
					return false; // no se envio la notificación
				}
			
		}catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return false;
	}

	public String getToken(String username, long inst, long sede, long jornada){// {username}/{ins}/{sede}/{jornada}
		uriApiToken = rbBol.getString("boletines_uri_api_transversal_generate_token")
				.replace("{username}", username)
				.replace("{ins}", String.valueOf(inst))
				.replace("{sede}", String.valueOf(sede))
				.replace("{jornada}", String.valueOf(jornada));
		 String token = "";
		try{
			final URL url = new URL(uriApiToken);// url API
			// report
			final java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			conn.setRequestProperty("Accept", "application/json");
			// Obtener respuesta de la API
			int responseCode = conn.getResponseCode();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));		
			
            ObjectMapper objectMapper = new ObjectMapper();         

			String line;
			StringBuilder response = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			reader.close();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				System.out.println("Respuesta de la API Mail:" + response.toString()); 
				JsonNode jsonNode = objectMapper.readTree(response.toString());
				token = jsonNode.get("token").asText();
			} else {
				System.out.println("Error al llamar a la API de TOKEN. Código de respuesta:" + responseCode);
			}
			// Cerrar conexión
			conn.disconnect();
		}catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return token;
	}




	
}

