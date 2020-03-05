package com.gobaskt.offers.handlers.localOffers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.drew.imaging.ImageProcessingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gobaskt.offers.entity.LocalMerchantOfferDto;
import com.gobaskt.offers.model.HttpResponse;
import com.gobaskt.offers.service.LocalMerchantOffersServices;
import com.gobaskt.offers.service.impl.LocalMerchantOfferServicesImpl;

/**
 * this is lambda hanlder
 * for saving offers to db
 * 
 * */
public class SaveToDBHandler {

	private LocalMerchantOffersServices localMerchantOffersServices;

	private final String DATA_NOT_FOUND = "no results";
	private final String DATA_FOUND = "Success";
	private final String BODY = "request body is empty";
	private final String CREATED = "created";

	public void setTaskDao(LocalMerchantOffersServices dao) {
		this.localMerchantOffersServices = dao;
	}

	private LocalMerchantOffersServices getLocalMerchantOffersServices() {
		if (this.localMerchantOffersServices == null) {
			this.localMerchantOffersServices = new LocalMerchantOfferServicesImpl();
		}
		return this.localMerchantOffersServices;
	}
	public APIGatewayProxyResponseEvent createTask(APIGatewayProxyRequestEvent request, Context context)
			throws ImageProcessingException, JsonProcessingException {
		String body = request.getBody();

		LocalMerchantOfferDto task;
		LambdaLogger logger = context.getLogger();
		logger.log("request data" + request.getBody());
		if (body.isEmpty()) {
			ObjectMapper mapper = new ObjectMapper();
			HttpResponse http = new HttpResponse(true, BODY, 204, body);
			String jsonInString = mapper.writeValueAsString(http);
			logger.log("data null" + body);
			return new APIGatewayProxyResponseEvent().withStatusCode(204).withBody(jsonInString);
		}

		try {
			ObjectMapper mapper = new ObjectMapper();

			HttpResponse http = new HttpResponse(true, CREATED, 200, body);
			String jsonInString = mapper.writeValueAsString(http);
			task = mapper.readValue(body, LocalMerchantOfferDto.class);
			task.setOfferStatus("Active");
			task.setPublished("No");
			// validate(task);
			this.getLocalMerchantOffersServices().saveToDB(task);
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers", "Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
			logger.log("print message" + http.getMessage());
			return new APIGatewayProxyResponseEvent().withStatusCode(200).withHeaders(headers).withBody(jsonInString);
		} catch (JsonProcessingException e) {
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers", "Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods", "GET,POST,OPTIONS");

			HttpResponse http = new HttpResponse(false, "json parsing error", 500, null);
			e.printStackTrace();
			return new APIGatewayProxyResponseEvent().withStatusCode(500).withHeaders(headers)
					.withBody(http.toString());
		} catch (IOException e) {
			e.printStackTrace();
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers", "Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
			return new APIGatewayProxyResponseEvent().withStatusCode(500).withHeaders(headers).withBody(body);
		}
	}

}
