package siges.util;

public class MailDTO {
	
public String[] getEmails() {
		return emails;
	}

	public void setEmails(String[] emails) {
		this.emails = emails;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

/** The emails. */
  private String[] emails;

  /** The content. */
  private String content;

  /** The subject. */
  private String subject;

}
