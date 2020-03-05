package com.gobaskt.offers.handlers.brandOffers;

import java.util.HashMap;
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

/** delete handler
 * this is lambda function fro deleteing offers
 *  
 *  */
public class DeleteOffersHandler {

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
	public APIGatewayProxyResponseEvent deleteOffers(APIGatewayProxyRequestEvent request, Context context) throws JsonProcessingException {
		String taskId = request.getPathParameters().get("id");
		ObjectMapper mapper = new ObjectMapper();
		
		BrandOfferDummy task=this.getBrandService().deleteBrandOffer(taskId);
		if(task.equals(null)) {
			HttpResponse http = new HttpResponse(true, DATA_NOT_FOUND, 200, taskId);
			String jsonInString = mapper.writeValueAsString(http);
			return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(jsonInString);
		}
		try {
			HttpResponse http = new HttpResponse(true, "deleted", 200, taskId);
			
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
