package com.example.aws.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.aws.demo.model.SendEmailRequest;
import com.example.aws.demo.model.SendEmailResponse;
import com.example.aws.demo.service.SendEmailService;

@Controller
@RequestMapping("/email")
public class SendEmailController {
	
	@Autowired
	private SendEmailService sendEmailService;
	
	@RequestMapping(value = "/send", consumes = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public SendEmailResponse sendEmailSDK (@RequestBody SendEmailRequest request) throws Exception {
		SendEmailResponse resp = new SendEmailResponse();
		String str = this.sendEmailService.sendEmailSDK(request);
		resp.setSentStatus(str);
		return resp;
	}
	
	@RequestMapping(value = "/send/smtp", consumes = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public SendEmailResponse sendEmailSMTP (@RequestBody SendEmailRequest request) throws Exception {
		SendEmailResponse resp = new SendEmailResponse();
		String str = this.sendEmailService.sendEmailSMTP(request);
		resp.setSentStatus(str);
		return resp;
	}

}
