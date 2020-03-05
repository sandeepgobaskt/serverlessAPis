package com.gobaskt.offers.handlers.brandOffers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gobaskt.offers.entity.BrandOffersDummyDto;

import com.gobaskt.offers.model.HttpResponse;
import com.gobaskt.offers.service.BrandService;
import com.gobaskt.offers.service.impl.BrandServiceImpl;


/**
 * this is lambda hanlder
 * for saving offers to brand offers
 * 
 * */
public class SaveToDBBrandOffersHandler {

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

	public APIGatewayProxyResponseEvent createBrandOffers(APIGatewayProxyRequestEvent request, Context context)
			throws JsonProcessingException {
		String body = request.getBody();
		ObjectMapper mapper = new ObjectMapper();
		BrandOffersDummyDto task;
		if (body.isEmpty()) {
			HttpResponse http = new HttpResponse(true, DATA_NOT_FOUND, 204, body);
			// ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(http);
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			return new APIGatewayProxyResponseEvent().withStatusCode(200).withHeaders(headers).withBody(jsonInString);
		}

		try {
			HttpResponse http = new HttpResponse(true, DATA_FOUND, 200, body);
			String jsonInString = mapper.writeValueAsString(http);
			task = mapper.readValue(body, BrandOffersDummyDto.class);
			this.getBrandService().saveBrandOffer(task);
			return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(jsonInString);
		} catch (JsonProcessingException e) {
			HttpResponse http = new HttpResponse(false, "json parsing error", 500, null);
			e.printStackTrace();
			return new APIGatewayProxyResponseEvent().withStatusCode(500).withBody(http.toString());
		} catch (IOException e) {
			e.printStackTrace();
			return new APIGatewayProxyResponseEvent().withStatusCode(500);
		}
	}
	public APIGatewayProxyResponseEvent createMultipleBrandOffers(APIGatewayProxyRequestEvent request, Context context)
			throws JsonProcessingException {
		String body = request.getBody();
		ObjectMapper mapper = new ObjectMapper();
		List<BrandOffersDummyDto> task;
		if (body.isEmpty()) {
			HttpResponse http = new HttpResponse(true, DATA_NOT_FOUND, 204, body);
			// ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(http);
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			return new APIGatewayProxyResponseEvent().withStatusCode(200).withHeaders(headers).withBody(jsonInString);
		}

		try {
			HttpResponse http = new HttpResponse(true, DATA_FOUND, 200, body);
			String jsonInString = mapper.writeValueAsString(http);
			task = mapper.readValue(body, new TypeReference<List<BrandOffersDummyDto>>(){});
			this.getBrandService().saveToDB(task);
			return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(jsonInString);
		} catch (JsonProcessingException e) {
			HttpResponse http = new HttpResponse(false, "json parsing error", 500, null);
			e.printStackTrace();
			return new APIGatewayProxyResponseEvent().withStatusCode(500).withBody(http.toString());
		} catch (IOException e) {
			e.printStackTrace();
			return new APIGatewayProxyResponseEvent().withStatusCode(500);
		}
	}
}
