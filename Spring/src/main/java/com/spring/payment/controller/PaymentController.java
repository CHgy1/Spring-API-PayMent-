package com.spring.payment.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

@Controller
public class PaymentController {
	
	private final IamportClient iamportClient;
	
	@Value("${iamport.api_key}")
	private String apiKey;
	
	@Value("${iamport.api_secret}")
	private String apiSecret;
	public PaymentController() {
		this.iamportClient = new IamportClient(apiKey, apiSecret);
	}
	
	@ResponseBody
	@RequestMapping("/verify/{imp_uid}")
	public IamportResponse<Payment> paymentByImpUid(@PathVariable("imp_uid") String imp_uid) throws IamportResponseException, IOException{
		return iamportClient.paymentByImpUid(imp_uid);
	}

}
