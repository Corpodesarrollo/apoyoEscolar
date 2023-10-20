package siges.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 *	VERSION		FECHA			AUTOR				DESCRIPCION
 *		1.0		09/05/2018		JORGE CAMACHO		Se agregó la clase al proyecto
 *
*/

public class SMTPAuthenticator extends Authenticator {
	
    private String username;
    private String password;
    
    public SMTPAuthenticator(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }
    
    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }
}
