package com.gobaskt.offers.handlers.brandOffers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gobaskt.offers.model.BrandOfferDummy;
import com.gobaskt.offers.model.HttpResponse;
import com.gobaskt.offers.service.BrandService;
import com.gobaskt.offers.service.impl.BrandServiceImpl;


/**
 * this is lambda hanlder
 * for get list of brand offers
 * 
 * */
public class ListBrandOffersHandler {
	private BrandService brandService;
	private final String DATA_NOT_FOUND = "no results";
	private final String DATA_FOUND = "Success";
	private final String BODY = "request body is empty";
	private final String CREATED = "created";

	public void setTaskDao(BrandService brandService) {
		this.brandService = brandService;
	}

	private BrandService getBrandService() {
		if (this.brandService == null) {
			this.brandService = new BrandServiceImpl();
		}
		return this.brandService;
	}
	
	public APIGatewayProxyResponseEvent listBrandOffers(APIGatewayProxyRequestEvent request, Context context) throws JsonProcessingException {

		List<BrandOfferDummy> tasks = this.getBrandService().listBrandOffers();
		if(tasks.isEmpty()) {
			HttpResponse http = new HttpResponse(true, DATA_NOT_FOUND, 204, tasks);
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(http);
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			return new APIGatewayProxyResponseEvent().withStatusCode(204).withHeaders(headers).withBody(jsonInString);
		}
		
		
		try {
			HttpResponse http = new HttpResponse(true, "found", 200, tasks);
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(http);

			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			return new APIGatewayProxyResponseEvent().withStatusCode(200).withHeaders(headers).withBody(jsonInString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return new APIGatewayProxyResponseEvent().withStatusCode(500);
		}

	}
}
