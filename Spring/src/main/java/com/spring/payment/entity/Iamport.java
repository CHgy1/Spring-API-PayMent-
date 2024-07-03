package com.spring.payment.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.siot.IamportRestClient.IamportClient;
@Configuration
public class Iamport {
	


	    @Value("${iamport.api_key}")
	    private String apiKey;

	    @Value("${iamport.api_secret}")
	    private String apiSecret;

	    @Bean
	    public IamportClient  iamportClient() {
	        return new IamportClient(apiKey, apiSecret);
	  }
}

