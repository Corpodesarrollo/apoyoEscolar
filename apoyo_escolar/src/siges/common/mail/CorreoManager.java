package siges.common.mail;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import siges.exceptions.MailException;

public class CorreoManager {

	private String to = null;

	private String[] toMany = null;

	private String subject = null;

	private String mensaje = null;

	private String from = null;
	private String port = null;

	private String mailHost = null;

	private String protocol = null;

	private String host = null;

	private String user = null;

	private String password = null;

	private String filename = null;

	private int id;

	private String mailer = "msgsend";

	private boolean debug;

	boolean band;

	public CorreoManager() {
	}

	/**
	 * Constructor de la clase
	 * 
	 * @param to
	 * @param from
	 * @param mensaje
	 * @param mailHost
	 */
	public CorreoManager(String to, String from, String mensaje, String mailHost) {
		this.to = to;
		this.from = from;
		this.mensaje = mensaje;
		this.mailHost = mailHost;
		this.to = to;
	}

	/**
	 * Constructor de la clase
	 * 
	 * @param to
	 * @param from
	 * @param mensaje
	 * @param mailHost
	 * @param user
	 * @param password
	 */
	public CorreoManager(String to, String from, String mensaje,
			String mailHost, String user, String password) {
		this(to, from, mensaje, mailHost);
		this.user = user;
		this.password = password;
	}

	/**
	 * Metedo para enviar un correo
	 * 
	 * @return
	 * @throws MailException
	 */
	public void enviarCorreo() throws Exception {
		try {
			Properties props = System.getProperties();
			if (mailHost != null)
				props.put("mail.smtp.host", mailHost);

			Session session = Session.getDefaultInstance(props, null);
			if (debug)
				session.setDebug(true);

			Message msg = new MimeMessage(session);
			if (from != null)
				msg.setFrom(new InternetAddress(from));
			else
				msg.setFrom();
			if (to != null) {
				msg.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(to, false));
			}
			if (toMany != null) {
				for (int j = 0; j < toMany.length; j++) {
					msg.addRecipients(Message.RecipientType.TO,
							InternetAddress.parse(toMany[j], false));
				}
			}
			msg.setSubject(subject);

			// collect(in, msg);
			msg.setText(mensaje);
			msg.setHeader("X-Mailer", mailer);
			msg.setSentDate(new Date());

			/*
			 * BodyPart messageBodyPart = new MimeBodyPart();
			 * messageBodyPart.setText("Te adjunto el archivo"); //Se crea el
			 * objeto Multipart y se le anade el contenido Multipart multipart =
			 * new MimeMultipart(); multipart.addBodyPart(messageBodyPart); //Se
			 * adjunta el archivo messageBodyPart = new MimeBodyPart();
			 * DataSource source = new FileDataSource(filename);
			 * messageBodyPart.setDataHandler(new DataHandler(source));
			 * messageBodyPart.setFileName(filename);
			 * multipart.addBodyPart(messageBodyPart); //Se incluye en el objeto
			 * Multipart y se envna msg.setContent(multipart);
			 */

			// send the thing off
			Transport.send(msg);
			msg.setFlag(Flags.Flag.DELETED, true);
			// System.out.println("ENVIAR CORREO: " +
			// "\nCorreo enviado satisfactoriamente.");
			// return true;
		} catch (MessagingException me) {
			me.printStackTrace();
			throw new Exception(me.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Metedo para enviar un correo
	 * 
	 * @return
	 * @throws MailException
	 */
	public void reenviarCorreo(Message message, String mensaje)
			throws Exception {
		try {
			Properties props = System.getProperties();
			if (mailHost != null)
				props.put("mail.smtp.host", mailHost);

			Session session = Session.getDefaultInstance(props, null);
			if (debug)
				session.setDebug(true);

			// Creacinn del mensaje a reenviar.
			Message forward = new MimeMessage(session);
			// A continuacinn se rellena la cabecera
			forward.setSubject("Re: " + message.getSubject() + " [" + mensaje
					+ "]");
			forward.setFrom(new InternetAddress(from));
			forward.addRecipient(Message.RecipientType.TO, new InternetAddress(
					message.getFrom()[0].toString()));
			// Se crea la parte del mensaje correspondiente al texto
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText("Te reenvno el mensaje original:\n\n");
			// Se crea un objeto Multipart
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			// Se crea la parte en la que se incluirn el mensaje inicial
			messageBodyPart = new MimeBodyPart();
			messageBodyPart.setDataHandler(message.getDataHandler());
			// Se anade esta parte al objeto Multipart
			multipart.addBodyPart(messageBodyPart);
			// Finalmente se asocia el objeto Multipart con el mensaje
			forward.setContent(multipart);
			// Y se envna.
			Transport.send(forward);
			message.setFlag(Flags.Flag.SEEN, true);
			message.setFlag(Flags.Flag.DELETED, true);
			// System.out.println("REENVIAR CORREO: " +
			// "\nCorreo reenviado satisfactoriamente.");
			// return true;
		} catch (MessagingException me) {
			me.printStackTrace();
			// Control de Excepciones. Devuelve un cndigo de error.
			Exception realException = me;
			while ((realException instanceof MessagingException)
					&& ((MessagingException) realException).getNextException() != null) {
				realException = ((MessagingException) realException)
						.getNextException();
			}
			// Errores de JavaMail
			if (realException instanceof javax.mail.MessagingException)
				throw new Exception("Error de mensaje: " + me.getMessage());
			else if (realException instanceof java.net.ConnectException)
				throw new Exception("Error de conexinn: " + me.getMessage());
			else if (realException instanceof java.net.UnknownHostException)
				throw new Exception("Error de host: " + me.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			// Otros errores.
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Metedo para enviar un correo
	 * 
	 * @return
	 * @throws MailException
	 */
	public void enviarCorreoAdjunto() throws Exception {
		try {
			Properties props = System.getProperties();
			if (mailHost != null)
				props.put("mail.smtp.host", mailHost);

			Session session = Session.getDefaultInstance(props, null);
			if (debug)
				session.setDebug(true);

			Message msg = new MimeMessage(session);
			if (from != null)
				msg.setFrom(new InternetAddress(from));
			else
				msg.setFrom();
			// System.out.println("Enviando...");
			if (to != null) {
				// System.out.println("Envia a: "+to);
				msg.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(to, false));
			}
			if (toMany != null) {
				for (int j = 0; j < toMany.length; j++) {
					// System.out.println("Envia a: "+toMany[j]);
					msg.addRecipients(Message.RecipientType.TO,
							InternetAddress.parse(toMany[j], false));
				}
			}
			msg.setSubject(subject);

			// collect(in, msg);
			msg.setText(mensaje);
			msg.setHeader("X-Mailer", mailer);
			msg.setSentDate(new Date());

			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(mensaje);
			// Se crea el objeto Multipart y se le anade el contenido
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			// Se adjunta el archivo
			messageBodyPart = new MimeBodyPart();
			// mirar: File arch = new File(filename);
			File arch = new File(filename);

			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));

			// messageBodyPart.setFileName(filename);
			messageBodyPart
					.setFileName(filename.substring(
							filename.lastIndexOf(File.separator) + 1,
							filename.length()));
			multipart.addBodyPart(messageBodyPart);
			// Se incluye en el objeto Multipart y se envna
			msg.setContent(multipart);

			// send the thing off
			Transport.send(msg);
			msg.setFlag(Flags.Flag.DELETED, true);
			// if (arch.exists()) arch.delete();
			// System.out.println("ENVIAR CORREO ADJUNTO: " +
			// "\nCorreo con adjunto enviado satisfactoriamente."+filename);
			// return true;
		} catch (MessagingException me) {
			me.printStackTrace();
			throw new Exception(me.getMessage());
		}
	}

	/**
	 * @param mailHost
	 */
	public void setMailHost(String mailHost) {
		this.mailHost = mailHost;
	}

	/**
	 * @param subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @param from
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * @param to
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * @param user
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public String getFilename() {
		return (filename != null) ? filename : "";
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @param mensaje
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * @param toMany
	 *            The toMany to set.
	 */
	public void setToMany(String[] toMany) {
		this.toMany = toMany;
	}

	public void enviarCorreoPassword() throws Exception {
		try {

			// System.out.println("Con autentificacion+++++++ ");
			Properties props = System.getProperties();
			// System.out.println("mailHost " + mailHost);
			if (mailHost != null) {

				// System.out.println("mailHost " + mailHost);
				props.put("mail.smtp.host", mailHost);
				props.setProperty("mail.smtp.starttls.enable", "true");
				props.setProperty("mail.smtp.port", port);
				props.setProperty("mail.smtp.user", this.from);
				props.setProperty("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.required", "true");

			}

			Session session = Session.getDefaultInstance(props, null);
			if (debug)
				session.setDebug(true);

			Message msg = new MimeMessage(session);
			if (from != null)
				msg.setFrom(new InternetAddress(from));
			else
				msg.setFrom();
			if (to != null) {
				msg.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(to, false));
			}
			if (toMany != null) {
				for (int j = 0; j < toMany.length; j++) {
					// System.out.println("Correo: "+toMany[j]);
					msg.addRecipients(Message.RecipientType.TO,
							InternetAddress.parse(toMany[j], false));
				}
			}
			msg.setSubject(subject);

			// collect(in, msg);
			msg.setText(mensaje);
			msg.setHeader("X-Mailer", mailer);
			msg.setSentDate(new Date());

			/*
			 * BodyPart messageBodyPart = new MimeBodyPart();
			 * messageBodyPart.setText("Te adjunto el archivo"); //Se crea el
			 * objeto Multipart y se le anade el contenido Multipart multipart =
			 * new MimeMultipart(); multipart.addBodyPart(messageBodyPart); //Se
			 * adjunta el archivo messageBodyPart = new MimeBodyPart();
			 * DataSource source = new FileDataSource(filename);
			 * messageBodyPart.setDataHandler(new DataHandler(source));
			 * messageBodyPart.setFileName(filename);
			 * multipart.addBodyPart(messageBodyPart); //Se incluye en el objeto
			 * Multipart y se envna msg.setContent(multipart);
			 */

			// send the thing off
			// Transport.send(msg);
			// msg.setFlag(Flags.Flag.DELETED, true);
			//
			Transport t = session.getTransport("smtp");
			t.connect(mailHost, from, password);
			t.sendMessage(msg, msg.getAllRecipients());
			/*
			 * Transport.send(msg); msg.setFlag(Flags.Flag.DELETED, true);
			 */

			// System.out.println("ENVIAR CORREO PASSWORD: " +
			// "\nCorreo enviado satisfactoriamente.");
			// return true;
		} catch (SendFailedException me) {
			me.printStackTrace();
			System.out.println("Error " + me.getMessage());
			throw new Exception("Dominio de correo no soportado");
		} catch (MessagingException me) {
			me.printStackTrace();
			throw new Exception(me.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	/*
	 * 
	 * 
	 * private static void send( final String username, final String password,
	 * final String recipients, final String subject, final String body) throws
	 * Exception { final Session session =
	 * Session.getInstance(System.getProperties(), null); final Message msg =
	 * new MimeMessage(session); final String senderEmail =
	 * username.contains("@") ? username : (username + "@gmail.com");
	 * msg.setFrom(new InternetAddress(senderEmail));
	 * 
	 * final Address[] recipientAddresses = InternetAddress.parse(recipients);
	 * msg.setRecipients(Message.RecipientType.TO, recipientAddresses);
	 * 
	 * msg.setSentDate(new Date()); msg.setSubject(subject); msg.setText(body);
	 * 
	 * final Transport transport = session.getTransport("smtps");
	 * transport.connect(GMAIL_SMTP_HOST, GMAIL_SMTP_PORT, username, password);
	 * transport.sendMessage(msg, recipientAddresses); transport.close(); }
	 */
}
