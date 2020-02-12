package com.gobaskt.offers.model;



import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class HttpResponse {
	private final Boolean success;
	private final String message;
	private final int statusCode;
	private final Object responseData;
}