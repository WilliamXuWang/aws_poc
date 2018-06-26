package com.example.aws.demo.service;

import org.springframework.stereotype.Service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.VerifyEmailIdentityRequest;
import com.example.aws.demo.config.Constant;

@Service
public class VerifyEmailService {
	
	public String verifyEmailAdr(String emailAdr) {
		
		try {
			AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
					.withRegion(Regions.US_WEST_2).build();
			VerifyEmailIdentityRequest verifyEmailIdentityRequest = new VerifyEmailIdentityRequest().withEmailAddress(emailAdr);
			client.verifyEmailIdentity(verifyEmailIdentityRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constant.SENDVERFIYSTATUS;
	}

}
