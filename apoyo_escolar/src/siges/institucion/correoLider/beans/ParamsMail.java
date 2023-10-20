/**
 * 
 */
package siges.institucion.correoLider.beans;

/**
 * 25/08/2007 
 * @author Latined
 * @version 1.2
 */
public class ParamsMail {
	private String from;
	private String host;
	private String password;
	private String port;
	
	/**
	 * @return Return the from.
	 */
	public String getFrom() {
		return from;
	}
	/**
	 * @param from The from to set.
	 */
	public void setFrom(String from) {
		this.from = from;
	}
	/**
	 * @return Return the host.
	 */
	public String getHost() {
		return host;
	}
	/**
	 * @param host The host to set.
	 */
	public void setHost(String host) {
		this.host = host;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
}
