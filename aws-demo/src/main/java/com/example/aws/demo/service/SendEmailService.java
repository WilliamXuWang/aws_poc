package com.example.aws.demo.service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.RawMessage;
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest;
import com.example.aws.demo.config.Constant;
import com.example.aws.demo.model.SendEmailRequest;

@Service
public class SendEmailService {
	
	@Autowired
	private UploadFileService uploadFile;
	
	@Autowired
	private DynamodbService dynamodbService;

	public String sendEmailSDK(final SendEmailRequest request) {

		// Try to send the email.
		try {
			String DefaultCharSet = MimeUtility.getDefaultJavaCharset();

			Session session = Session.getDefaultInstance(new Properties());

			// Create a new MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Add subject, from and to lines.
			message.setSubject(request.getEmailSubject(), "UTF-8");
			message.setFrom(new InternetAddress(request.getSenderEmailAddress(), request.getSenderName()));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(request.getRecipientEmailAddress()));

			// Create a multipart/alternative child container.
			MimeMultipart msg_body = new MimeMultipart("alternative");

			// Create a wrapper for the HTML and text parts.
			MimeBodyPart wrap = new MimeBodyPart();

			// Define the text part.
			MimeBodyPart textPart = new MimeBodyPart();
			// Encode the text content and set the character encoding. This step is
			// necessary if you're sending a message with characters outside the
			// ASCII range.
			String emailBody = "Hello, ".concat(request.getRecipientName()).concat("\r\n")
					.concat(request.getEmailBody());
			textPart.setContent(MimeUtility.encodeText(emailBody, DefaultCharSet, "B"), "text/plain; charset=UTF-8");
			textPart.setHeader("Content-Transfer-Encoding", "base64");

			// Add the text and HTML parts to the child container.
			msg_body.addBodyPart(textPart);
			// msg_body.addBodyPart(htmlPart);

			// Add the child container to the wrapper object.
			wrap.setContent(msg_body);

			// Create a multipart/mixed parent container.
			MimeMultipart msg = new MimeMultipart("mixed");

			// Add the parent container to the message.
			message.setContent(msg);

			// Add the multipart/alternative part to the message.
			msg.addBodyPart(wrap);

			// Define the attachment
			String attachmentUrl = request.getAttachmentUrl();
			if (null != attachmentUrl && !attachmentUrl.isEmpty()) {
				MimeBodyPart att = new MimeBodyPart();
				DataSource fds = new FileDataSource(uploadFile.saveUrlAs(attachmentUrl));
				att.setDataHandler(new DataHandler(fds));
				att.setFileName(fds.getName());
				// Add the attachment to the message.
				msg.addBodyPart(att);
				System.out.println("Attempting to send an email through Amazon SES " + "using the AWS SDK for Java...");
			}

			// Instantiate an Amazon SES client, which will make the service
			// call with the supplied AWS credentials.
			AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
					.withRegion(Regions.US_WEST_2).build();

			// Print the raw email content on the console
			PrintStream out = System.out;
			message.writeTo(out);

			// Send the email.
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			message.writeTo(outputStream);
			RawMessage rawMessage = new RawMessage(ByteBuffer.wrap(outputStream.toByteArray()));

			SendRawEmailRequest rawEmailRequest = new SendRawEmailRequest(rawMessage);

			client.sendRawEmail(rawEmailRequest);
			
			dynamodbService.insertData(request);
			System.out.println("Email sent!");
		} catch (Exception ex) {
			System.out.println("Email Failed");
			System.err.println("Error message: " + ex.getMessage());
			ex.printStackTrace();
		}

		return Constant.SENTSTATUS;
	}

	public String sendEmailSMTP(final SendEmailRequest request) throws Exception {

		// Create a Properties object to contain connection configuration information.
		Properties props = System.getProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.port", Constant.PORT);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");

		// Create a Session object to represent a mail session with the specified
		// properties.
		Session session = Session.getDefaultInstance(props);

		String DefaultCharSet = MimeUtility.getDefaultJavaCharset();

		// Create a new MimeMessage object.
		MimeMessage message = new MimeMessage(session);

		// Add subject, from and to lines.
		message.setSubject(request.getEmailSubject(), "UTF-8");
		message.setFrom(new InternetAddress(request.getSenderEmailAddress(), request.getSenderName()));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(request.getRecipientEmailAddress()));

		// Create a multipart/alternative child container.
		MimeMultipart msg_body = new MimeMultipart("alternative");

		// Create a wrapper for the HTML and text parts.
		MimeBodyPart wrap = new MimeBodyPart();

		// Define the HTML part.
		MimeBodyPart htmlPart = new MimeBodyPart();
		// Encode the HTML content and set the character encoding.
		String emailBody = "Hello, ".concat(request.getRecipientName()).concat("\r\n").concat(request.getEmailBody());
		htmlPart.setContent(MimeUtility.encodeText(emailBody, DefaultCharSet, "B"), "text/html; charset=UTF-8");
		htmlPart.setHeader("Content-Transfer-Encoding", "base64");

		// Add the text and HTML parts to the child container.
		msg_body.addBodyPart(htmlPart);

		// Add the child container to the wrapper object.
		wrap.setContent(msg_body);

		// Create a multipart/mixed parent container.
		MimeMultipart msg = new MimeMultipart("mixed");

		// Add the multipart/alternative part to the message.
		msg.addBodyPart(wrap);

		// Define the attachment
		String attachmentUrl = request.getAttachmentUrl();
		if (null != attachmentUrl && !attachmentUrl.isEmpty()) {
			MimeBodyPart att = new MimeBodyPart();
			DataSource fds = new FileDataSource(uploadFile.saveUrlAs(attachmentUrl));
			att.setDataHandler(new DataHandler(fds));
			att.setFileName(fds.getName());
			// Add the attachment to the message.
			msg.addBodyPart(att);
			System.out.println("Attempting to send an email through Amazon SES " + "using the AWS SDK for Java...");
		}

		// Add the parent container to the message.
		message.setContent(msg);

		// Add a configuration set header. Comment or delete the
		// next line if you are not using a configuration set
		// msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);

		// Create a transport.
		Transport transport = session.getTransport();

		// Send the message.
		try {
			System.out.println("Sending...");

			// Connect to Amazon SES using the SMTP username and password you specified
			// above.
			transport.connect(Constant.HOST, Constant.SMTP_USERNAME, Constant.SMTP_PASSWORD);

			// Send the email.
			transport.sendMessage(message, message.getAllRecipients());

			dynamodbService.insertData(request);
			System.out.println("Email sent!");
		} catch (Exception ex) {
			System.out.println("The email was not sent.");
			System.out.println("Error message: " + ex.getMessage());
		} finally {
			// Close and terminate the connection.
			transport.close();
		}
		return Constant.SENDVERFIYSTATUS;
	}

}
