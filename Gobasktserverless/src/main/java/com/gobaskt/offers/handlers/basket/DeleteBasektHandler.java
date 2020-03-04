package com.gobaskt.offers.handlers.basket;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gobaskt.offers.entity.HttpResponse;
import com.gobaskt.offers.model.BasketActivity;
import com.gobaskt.offers.service.BasketService;
import com.gobaskt.offers.service.impl.BasketServiceImpl;

public class DeleteBasektHandler {

	private BasketService basketService;
	private final String DATA_NOT_FOUND = "no results";
	private final String DATA_FOUND = "Success";
	private final String BODY = "request body is empty";
	private final String CREATED = "created";

	public void setTaskDao(BasketService basketService) {
		this.basketService = basketService;
	}

	private BasketService getBasketService() {
		if (this.basketService == null) {
			this.basketService = new BasketServiceImpl();
		}
		return this.basketService;
	}

	
	public APIGatewayProxyResponseEvent deleteOffersByofferId(APIGatewayProxyRequestEvent request, Context context) throws JsonProcessingException {
		String taskId = request.getPathParameters().get("id");
		BasketActivity data=this.getBasketService().deleteBasketByOfferId(taskId);
		if(data.equals(null)) {
			HttpResponse http = new HttpResponse(true, DATA_NOT_FOUND, 204, data);
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(http);
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			return new APIGatewayProxyResponseEvent().withStatusCode(204).withHeaders(headers).withBody(jsonInString);
		}else {
		HttpResponse http = new HttpResponse(true, "deleted", 200, taskId);
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(http);
		return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(jsonInString);
		}
	}

	public APIGatewayProxyResponseEvent deleteBasketById(APIGatewayProxyRequestEvent request, Context context) throws JsonProcessingException {
		String taskId = request.getPathParameters().get("id");
		BasketActivity data=this.getBasketService().deleteByBasketId(taskId);
		if(data.equals(null)) {
			HttpResponse http = new HttpResponse(true, DATA_NOT_FOUND, 204, data);
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(http);
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			return new APIGatewayProxyResponseEvent().withStatusCode(204).withHeaders(headers).withBody(jsonInString);
		}else {
		HttpResponse http = new HttpResponse(true, "basket " + "deleted", 200, taskId);
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(http);
		return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(jsonInString);
		}
	}
}
