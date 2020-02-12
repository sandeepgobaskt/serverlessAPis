package com.gobaskt.offers.entity;



import lombok.Data;

@Data
public class HttpResponse {
	private final Boolean success;
	private final String message;
	private final int statusCode;
	private final Object data;
}