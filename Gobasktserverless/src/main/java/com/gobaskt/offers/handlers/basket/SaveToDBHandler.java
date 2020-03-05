package com.gobaskt.offers.handlers.basket;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gobaskt.offers.entity.BasketActivityDto;
import com.gobaskt.offers.model.BasketActivity;
import com.gobaskt.offers.model.HttpResponseOffers;
import com.gobaskt.offers.model.LocalMerchantOffer;
import com.gobaskt.offers.model.OffersAdded;
import com.gobaskt.offers.response.HttpResponse;
import com.gobaskt.offers.service.BasketService;
import com.gobaskt.offers.service.LocalMerchantOffersServices;
import com.gobaskt.offers.service.impl.BasketServiceImpl;
import com.gobaskt.offers.service.impl.LocalMerchantOfferServicesImpl;


/**
 * this is lambda hanlder
 * for saving offers to basket
 * 
 * */
public class SaveToDBHandler {

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

	private LocalMerchantOffersServices localServices;

	public void setTaskDao(LocalMerchantOffersServices localServices) {
		this.localServices = localServices;
	}

	private LocalMerchantOffersServices getDao() {
		if (this.localServices == null) {
			this.localServices = new LocalMerchantOfferServicesImpl();
		}
		return this.localServices;
	}

	public APIGatewayProxyResponseEvent createTask(APIGatewayProxyRequestEvent request, Context context) {
		String body = request.getBody();
		ObjectMapper mapper = new ObjectMapper();
		BasketActivityDto task;

		try {
			HttpResponse http = new HttpResponse(true, "found", 200, body);
			String jsonInString = mapper.writeValueAsString(http);
			task = mapper.readValue(body, BasketActivityDto.class);
			this.getBasketService().saveToDB(task);
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

	public APIGatewayProxyResponseEvent getTask(APIGatewayProxyRequestEvent request, Context context)
			throws JsonProcessingException {
		String taskId = request.getPathParameters().get("id");
		LocalMerchantOffer local = this.getDao().getLocalMerchantOfferById(taskId);
		List<BasketActivity> task = this.getBasketService().getBasketByOfferId(taskId);

		HttpResponseOffers offer = new HttpResponseOffers(true, CREATED, 200, local);

		if (local.equals(null)) {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			HttpResponse http = new HttpResponse(true,DATA_NOT_FOUND, 200, local);
			String jsonInString = mapper.writeValueAsString(http);
			return new APIGatewayProxyResponseEvent().withStatusCode(404).withBody(jsonInString);
		}			
				try {

					ObjectMapper mapper = new ObjectMapper();

					BasketActivityDto basket = new BasketActivityDto();
					Date date = new Date();
					// List<OffersAdded> list = new ArrayList<>();

					long time = date.getTime();

					Timestamp ts = new Timestamp(time);

					OffersAdded offers = new OffersAdded();

					offers.setSave_timeStamp(ts.toString());
					offers.setOfferStatus("saved");
					offers.setOfferId(offer.getResponseData().getId());
					// System.out.println(offer.getResponseData().getId());
					offers.setLMName(offer.getResponseData().getLMName());
					offers.setLMLocation(offer.getResponseData().getLMLocation());
					offers.setApplicableProducts(offer.getResponseData().getApplicableProducts());
					offers.setEANCode(offer.getResponseData().getEANCode());
					offers.setLMDescription(offer.getResponseData().getLMDescription());
					offers.setLMID(offer.getResponseData().getLMID());
					offers.setLMOfferSubType(offer.getResponseData().getLMOfferSubType());
					offers.setLMOfferTitle(offer.getResponseData().getLMOfferTitle());
					offers.setLMOfferType(offer.getResponseData().getLMOfferType());
					offers.setLMOfferValue(offer.getResponseData().getLMOfferValue());
					offers.setOfferCurrency(offer.getResponseData().getOfferCurrency());
					offers.setOfferExpiryDate(offer.getResponseData().getOfferExpiryDate());
					offers.setOfferExpiryTime(offer.getResponseData().getOfferExpiryTime());
					offers.setOfferRegion(offer.getResponseData().getOfferRegion());
					offers.setOfferStartDate(offer.getResponseData().getOfferStartDate());
					offers.setOfferStartTime(offer.getResponseData().getOfferStartTime());
					offers.setOfferTermsandConditions(offer.getResponseData().getOfferTermsandConditions());
					offers.setWebOfferImage1(offer.getResponseData().getWebOfferImage1());
					offers.setWebOfferImage2(offer.getResponseData().getWebOfferImage2());
					offers.setMobileOfferImage1(offer.getResponseData().getMobileOfferImage1());
					offers.setMobileOfferImage2(offer.getResponseData().getMobileOfferImage2());
					// list.add(offers);
					basket.setOffers(offers);
					if(task.isEmpty()) {
					this.getBasketService().saveToDB(basket);
					Map<String, String> headers = new HashMap<String, String>();
					headers.put("Content-Type", "application/json");
					HttpResponse http = new HttpResponse(true, "offer saved", 200, basket);
					String jsonInString = mapper.writeValueAsString(http);

					return new APIGatewayProxyResponseEvent().withStatusCode(200).withHeaders(headers)
							.withBody(jsonInString);
					} else {
						//ObjectMapper mapper = new ObjectMapper();
						Map<String, String> headers = new HashMap<String, String>();
						headers.put("Content-Type", "application/json");
						HttpResponse http = new HttpResponse(true, "offer already saved", 200, task);
						String jsonInString = mapper.writeValueAsString(http);
						return new APIGatewayProxyResponseEvent().withStatusCode(200).withHeaders(headers)
								.withBody(jsonInString);
					}

				} catch (JsonProcessingException e) {
					e.printStackTrace();
					return new APIGatewayProxyResponseEvent().withStatusCode(500);
				}
			}
}
