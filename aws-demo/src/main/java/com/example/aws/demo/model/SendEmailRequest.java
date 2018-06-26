package com.example.aws.demo.model;

import java.io.Serializable;

public class SendEmailRequest implements Serializable {
	
	private static final long serialVersionUID = 9178818862063693638L;
	
	private String senderEmailAddress;
	private String recipientEmailAddress;
	private String senderName;
	private String recipientName;
	private String emailSubject;
	private String attachmentUrl;
	private String emailBody;
	
	public SendEmailRequest() {
		super();
	}

	public SendEmailRequest(String senderEmailAddress, String recipientEmailAddress, String senderName,
			String recipientName, String emailSubject, String attachmentUrl, String emailBody) {
		super();
		this.senderEmailAddress = senderEmailAddress;
		this.recipientEmailAddress = recipientEmailAddress;
		this.senderName = senderName;
		this.recipientName = recipientName;
		this.emailSubject = emailSubject;
		this.attachmentUrl = attachmentUrl;
		this.emailBody = emailBody;
	}

	public String getSenderEmailAddress() {
		return senderEmailAddress;
	}

	public void setSenderEmailAddress(String senderEmailAddress) {
		this.senderEmailAddress = senderEmailAddress;
	}

	public String getRecipientEmailAddress() {
		return recipientEmailAddress;
	}

	public void setRecipientEmailAddress(String recipientEmailAddress) {
		this.recipientEmailAddress = recipientEmailAddress;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public String getAttachmentUrl() {
		return attachmentUrl;
	}

	public void setAttachmentUrl(String attachmentUrl) {
		this.attachmentUrl = attachmentUrl;
	}

	public String getEmailBody() {
		return emailBody;
	}

	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}
	
}
