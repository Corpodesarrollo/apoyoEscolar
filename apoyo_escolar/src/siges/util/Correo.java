package siges.util;

import java.util.Properties;
import java.util.Date;
import siges.exceptions.*;

import javax.mail.*;
import javax.mail.internet.*;

public class Correo
{
	private String to=null;
	private String subject=null;
	private String mensaje=null;
	private String from=null;
	private String mailHost=null;
	private String protocol=null;
	private String host=null;
	private String user=null;
	private String password=null;
	private String mailer = "msgsend";
	private boolean debug;

	public Correo(){}

	/**
	* Constructor de la clase
	* @param to
	* @param from
	* @param mensaje
	* @param mailHost
	*/
	public Correo(String to,String from,String mensaje,String mailHost)
	{
		this.to=to;
		this.from=from;
		this.mensaje=mensaje;
		this.mailHost=mailHost;
		this.to=to;
	}

	/**
	* Constructor de la clase
	* @param to
	* @param from
	* @param mensaje
	* @param mailHost
	* @param user
	* @param password
	*/
	public Correo(String to,String from,String mensaje,String mailHost,String user,String password)
	{
		this(to,from,mensaje,mailHost);
		this.user=user;
		this.password=password;
	}

	/**
	* Metedo para enviar un correo
	* @return
	* @throws MailException
	*/
	public boolean enviarCorreo() 
	{
	    try{
	        Properties props = System.getProperties();
	        if (mailHost != null)
	            props.put("mail.smtp.host", mailHost);

	        Session session = Session.getDefaultInstance(props, null);
	        if (debug)	session.setDebug(true);

	        Message msg = new MimeMessage(session);
	        if (from != null)	msg.setFrom(new InternetAddress(from));
	        else	msg.setFrom();
	        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
	        msg.setSubject(subject);

	        //collect(in, msg);
	        msg.setText(mensaje);
	        msg.setHeader("X-Mailer", mailer);
	        msg.setSentDate(new Date());

	        // send the thing off
	        Transport.send(msg);
	        
	        //System.out.println("\nMail was sent successfully.");
	        //  return true;
	    }
	    catch(MessagingException me){
	    	//me.printStackTrace();
//	      Control de Excepciones. Devuelve un cndigo de error.
		     Exception realException = me;
		     while ( (realException instanceof MessagingException) &&
		     ((MessagingException)realException).getNextException() != null) {
		     realException = ((MessagingException)realException).getNextException();
		     }

//	      Errores de JavaMail
		     if (realException instanceof javax.mail.MessagingException)
		     return false;
		     else if (realException instanceof java.net.ConnectException)
		     return false;
		     else if (realException instanceof java.net.UnknownHostException)
		     return false;
	     }catch(Exception e){
	    	 //e.printStackTrace();
//	      Otros errores.
	         return false;
	     } 
	    return true;
	}

	/**
	* @param mailHost
	*/
	public void setMailHost(String mailHost)
	{
		this.mailHost =mailHost;
	}

	/**
	* @param subject
	*/
	public void setSubject(String subject)
	{
		this.subject=subject;
	}

	/**
	* @param from
	*/
	public void setFrom(String from)
	{
		this.from=from;
	}

	/**
	* @param to
	*/
	public void setTo(String to)
	{
		this.to=to;
	}

	/**
	* @param user
	*/
	public void setUser(String user)
	{
		this.user=user;
	}

	/**
	* @param password
	*/
	public void setPassword(String password)
	{
		this.password=password;
	}

	/**
	* @param mensaje
	*/
	public void setMensaje(String mensaje)
	{
		this.mensaje=mensaje;
	}

	public static void main(String arg[])
	{
		Correo correo=new Correo();
		/*correo.setTo("jago1287@gmail.com");
		correo.setFrom("project_0@tutopia.com");
		correo.setMailHost("smtp.tutopia.com");
		correo.setSubject("Asignacion de Sala");
		correo.setMensaje("Asignada");*/
			//correo.enviarCorreo();
	}

}