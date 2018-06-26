package com.example.aws.demo.model;

import java.io.Serializable;

public class VerifyEmailAdrRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 789381542711668685L;
	private String emailAdress;

	public VerifyEmailAdrRequest() {
		super();
	}

	public VerifyEmailAdrRequest(String emailAdress) {
		super();
		this.emailAdress = emailAdress;
	}

	public String getEmailAdress() {
		return emailAdress;
	}

	public void setEmailAdress(String emailAdress) {
		this.emailAdress = emailAdress;
	}
	
	

}
