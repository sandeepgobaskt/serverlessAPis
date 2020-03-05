package com.gobaskt.offers.response;



import lombok.Data;

/**
 * this is response class
 * 
 * 
 * */
@Data
public class HttpResponse {
	private final Boolean success;
	private final String message;
	private final int statusCode;
	private final Object responseData;
}