package com.example.aws.demo.model;

import java.io.Serializable;

public class SendEmailResponse implements Serializable {
	
	private static final long serialVersionUID = -8819150840087306423L;
	
	private String sentStatus;

	public SendEmailResponse() {
		super();
	}

	public SendEmailResponse(String sentStatus) {
		super();
		this.sentStatus = sentStatus;
	}

	public String getSentStatus() {
		return sentStatus;
	}

	public void setSentStatus(String sentStatus) {
		this.sentStatus = sentStatus;
	}

}
