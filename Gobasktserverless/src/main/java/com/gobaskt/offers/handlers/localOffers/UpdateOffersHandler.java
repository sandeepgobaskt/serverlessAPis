package com.gobaskt.offers.handlers.localOffers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gobaskt.offers.entity.LocalMerchantOfferDto;

import com.gobaskt.offers.model.HttpResponse;
import com.gobaskt.offers.model.LocalMerchantOffer;
import com.gobaskt.offers.service.LocalMerchantOffersServices;
import com.gobaskt.offers.service.impl.LocalMerchantOfferServicesImpl;

/**
 * this is lambda hanlder
 * for update offers to basket
 * 
 * */
public class UpdateOffersHandler {
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

	
	
	

	public APIGatewayProxyResponseEvent updateTask(APIGatewayProxyRequestEvent request, Context context)
			throws JsonProcessingException {
		String body = request.getBody();

		LocalMerchantOfferDto task;
		if (body.isEmpty()) {
			ObjectMapper mapper = new ObjectMapper();
			HttpResponse http = new HttpResponse(true, BODY, 204, body);
			String jsonInString = mapper.writeValueAsString(http);
			LambdaLogger logger = context.getLogger();
			logger.log("data null" + body);
			return new APIGatewayProxyResponseEvent().withStatusCode(204).withBody(jsonInString);
		}
		try {
			ObjectMapper mapper = new ObjectMapper();
			task = mapper.readValue(body, LocalMerchantOfferDto.class);

			task.setOfferStatus("Active");
			task.setPublished("No");

			HttpResponse http = new HttpResponse(true, CREATED, 200, task);
			this.getLocalMerchantOffersServices().update(task);
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers", "Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods", "GET,POST,OPTIONS");

			String jsonInString = mapper.writeValueAsString(http);

			return new APIGatewayProxyResponseEvent().withStatusCode(200).withHeaders(headers).withBody(jsonInString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();

			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers", "Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods", "GET,POST,OPTIONS");

			return new APIGatewayProxyResponseEvent().withStatusCode(500).withHeaders(headers).withBody(body);
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

	public APIGatewayProxyResponseEvent runCampaign(APIGatewayProxyRequestEvent request, Context context)
			throws JsonProcessingException {
		// String body = request.getBody();
		String taskId = request.getPathParameters().get("id");
		LocalMerchantOffer list = this.getLocalMerchantOffersServices().getLocalMerchantOfferById(taskId);
		// LocalMerchantOffer task;
		if (list.equals(null)) {
			ObjectMapper mapper = new ObjectMapper();
			HttpResponse http = new HttpResponse(true, BODY, 204, list);
			String jsonInString = mapper.writeValueAsString(http);
			LambdaLogger logger = context.getLogger();
			logger.log("data null" + list);
			return new APIGatewayProxyResponseEvent().withStatusCode(204).withBody(jsonInString);
		}
		try {
			ObjectMapper mapper = new ObjectMapper();
			// task = mapper.readValue(list, LocalMerchantOffer.class);

			list.setPublished("Yes");

			HttpResponse http = new HttpResponse(true, "found", 200, list.getPublished());
			this.getLocalMerchantOffersServices().runCampaign(list);
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers", "Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods", "GET,POST,OPTIONS");

			String jsonInString = mapper.writeValueAsString(http);

			return new APIGatewayProxyResponseEvent().withStatusCode(200).withHeaders(headers).withBody(jsonInString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();

			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers", "Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods", "GET,POST,OPTIONS");

			return new APIGatewayProxyResponseEvent().withStatusCode(500).withHeaders(headers)
					.withBody(list.toString());
		} catch (IOException e) {
			e.printStackTrace();
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers", "Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods", "GET,POST,OPTIONS");

			return new APIGatewayProxyResponseEvent().withStatusCode(500).withHeaders(headers)
					.withBody(list.getLMName());
		}
	}

	
	public APIGatewayProxyResponseEvent updateTasks(APIGatewayProxyRequestEvent request, Context context) {
		String body = request.getBody();
		ObjectMapper mapper = new ObjectMapper();

		List<LocalMerchantOfferDto> task;

		try {

			task = mapper.readValue(body, new TypeReference<List<LocalMerchantOfferDto>>() {
			});

			HttpResponse http = new HttpResponse(true, DATA_FOUND, 200, task);
			LocalMerchantOfferDto local = new LocalMerchantOfferDto();
			local.setPublished("Yes");
			task.add(local);
			this.getLocalMerchantOffersServices().saveToDB(task);
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers", "Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods", "GET,POST,OPTIONS");

			String jsonInString = mapper.writeValueAsString(http);

			return new APIGatewayProxyResponseEvent().withStatusCode(200).withHeaders(headers).withBody(jsonInString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers", "Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods", "GET,POST,OPTIONS");

			return new APIGatewayProxyResponseEvent().withStatusCode(500).withHeaders(headers).withBody(body);
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
