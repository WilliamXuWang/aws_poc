package com.example.aws.demo.config;

public final class Constant {
	
	// Replace smtp_username with your Amazon SES SMTP user name.
	public static final String SMTP_USERNAME = "";
    
    // Replace smtp_password with your Amazon SES SMTP password.
	public static final String SMTP_PASSWORD = "";
    
    // Amazon SES SMTP host name. This example uses the US West (Oregon) region.
    // See http://docs.aws.amazon.com/ses/latest/DeveloperGuide/regions.html#region-endpoints
    // for more information.
	public static final String HOST = "email-smtp.us-west-2.amazonaws.com";
    
    // The port you will connect to on the Amazon SES SMTP endpoint. 
	public static final int PORT = 587;
	
	public static final String SENTSTATUS = "Sent Email Successfully!!!";
	public static final String SENDVERFIYSTATUS = "Send Verify Email Address Successfully!!!";
	
	public static final String METHOD = "GET";
	//local
//	public static final String FILEBASEPATH = "/Users/mobiler_dev_10/Desktop/dummy/";
	// Aws ec2
	public static final String FILEBASEPATH = "/home/ubuntu/tools/img/";

}
