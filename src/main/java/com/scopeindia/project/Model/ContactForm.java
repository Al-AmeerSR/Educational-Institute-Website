package com.scopeindia.project.Model;

public class ContactForm {
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	private int id;
    private String username;
    private String email;
    private String subject;
    private String message;
    public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
    public ContactForm() {
    	super();
    }
    public  ContactForm (String username,String email,String subject,String message) {
    	
    	this.username=username;
    	this.email=email;
    	this.subject=subject;
    	this.message=message;
    	
    }
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
