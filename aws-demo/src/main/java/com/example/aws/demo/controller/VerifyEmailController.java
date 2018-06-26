package com.example.aws.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.aws.demo.model.SendEmailResponse;
import com.example.aws.demo.model.VerifyEmailAdrRequest;
import com.example.aws.demo.service.VerifyEmailService;

@Controller
@RequestMapping("/email")
public class VerifyEmailController {
	
	@Autowired
	private VerifyEmailService  verifyEmailService;
	
	@RequestMapping(value = "/verify/address", consumes = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public SendEmailResponse verifyEmailAdr (@RequestBody VerifyEmailAdrRequest request) throws Exception {
		SendEmailResponse resp = new SendEmailResponse();
		String str = this.verifyEmailService.verifyEmailAdr(request.getEmailAdress());
		resp.setSentStatus(str);
		return resp;
	}

}