package com.gobaskt.offers.model;

import lombok.Data;

@Data	
public class HttpResponseOffers {

	private final Boolean success;
	private final String message;
	private final int statusCode;
	private final LocalMerchantOffer responseData;
}
