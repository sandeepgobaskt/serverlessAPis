package com.gobaskt.offers.handlers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gobaskt.offers.entity.BrandOffersDummyRest;

import com.gobaskt.offers.model.BrandOfferDummy;
import com.gobaskt.offers.model.HttpResponse;
import com.gobaskt.offers.model.LocalMerchantOffer;
import com.gobaskt.offers.service.BrandService;
import com.gobaskt.offers.service.LocalMerchantOffersServices;
import com.gobaskt.offers.service.impl.BrandServiceImpl;
import com.gobaskt.offers.service.impl.LocalMerchantOfferServicesImpl;


public class BrandOfferHanlder {

	private BrandService dao;

	public void setTaskDao(BrandService dao) {
		this.dao = dao;
	}

	private BrandService getDao() {
		if (this.dao == null) {
			this.dao = new BrandServiceImpl();
		}
		return this.dao;
	}
	

	public APIGatewayProxyResponseEvent listBrandOffers(APIGatewayProxyRequestEvent request, Context context) {

		List<BrandOfferDummy> tasks = this.getDao().listBrandOffers();
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

	public APIGatewayProxyResponseEvent createBrandOffers(APIGatewayProxyRequestEvent request, Context context) {
		String body = request.getBody();
		ObjectMapper mapper = new ObjectMapper();
		BrandOffersDummyRest task;

		try {
			HttpResponse http = new HttpResponse(true, "found", 200, body);
			String jsonInString = mapper.writeValueAsString(http);
			task = mapper.readValue(body, BrandOffersDummyRest.class);
			this.getDao().saveBrandOffer(task);
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

	public APIGatewayProxyResponseEvent getBrandOffer(APIGatewayProxyRequestEvent request, Context context) {
		String taskId = request.getPathParameters().get("id");
		BrandOfferDummy task = this.getDao().getBrandOffer(taskId);
		HttpResponse http = new HttpResponse(true, "found", 200, task);

		if (task == null) {
			return new APIGatewayProxyResponseEvent().withStatusCode(404);
		}

		try {
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
	public APIGatewayProxyResponseEvent deleteOffers(APIGatewayProxyRequestEvent request, Context context) {
		String taskId = request.getPathParameters().get("id");
		this.getDao().deleteBrandOffer(taskId);
		HttpResponse http = new HttpResponse(true, "found", 200, taskId);
		return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(http.toString());
	}
	public APIGatewayProxyResponseEvent getOffersBySearch(APIGatewayProxyRequestEvent request, Context context) {
		String taskId = request.getPathParameters().get("search");

		List<BrandOfferDummy> task = this.getDao().search(taskId);
		HttpResponse http = new HttpResponse(true, "found", 200, task);

		if (task == null) {
			return new APIGatewayProxyResponseEvent().withStatusCode(404);
		}

		try {
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
