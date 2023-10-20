package siges.exceptions;

public class MailException extends Exception
{
	public MailException() 
	{
		super("Problemas al enviar el correo");
	}
	
	public MailException(String mensaje) 
	{
		super(mensaje);
	}	
}