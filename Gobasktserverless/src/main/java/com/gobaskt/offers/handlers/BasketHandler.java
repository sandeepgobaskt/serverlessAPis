package com.gobaskt.offers.handlers;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gobaskt.offers.entity.BasketActivityRest;
import com.gobaskt.offers.entity.HttpResponse;
import com.gobaskt.offers.model.BasketActivity;
import com.gobaskt.offers.model.BrandOfferDummy;
import com.gobaskt.offers.model.HttpResponseOffers;
import com.gobaskt.offers.model.LocalMerchantOffer;
import com.gobaskt.offers.model.OffersAdded;
import com.gobaskt.offers.service.BasketService;
import com.gobaskt.offers.service.LocalMerchantOffersServices;
import com.gobaskt.offers.service.impl.BasketServiceImpl;
import com.gobaskt.offers.service.impl.LocalMerchantOfferServicesImpl;

public class BasketHandler {

	private BasketService dao;

	public void setTaskDao(BasketService dao) {
		this.dao = dao;
	}

	private BasketService getTaskDao() {
		if (this.dao == null) {
			this.dao = new BasketServiceImpl();
		}
		return this.dao;
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

	public APIGatewayProxyResponseEvent listTasks(APIGatewayProxyRequestEvent request, Context context) {

		List<BasketActivity> tasks = this.getTaskDao().getBasket();
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

	public APIGatewayProxyResponseEvent getbasketByOfferId(APIGatewayProxyRequestEvent request, Context context) {
		String taskId = request.getPathParameters().get("id");
		List<BasketActivity> task = this.getTaskDao().getBasketByOfferId(taskId);
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

	public APIGatewayProxyResponseEvent createTask(APIGatewayProxyRequestEvent request, Context context) {
		String body = request.getBody();
		ObjectMapper mapper = new ObjectMapper();
		BasketActivityRest task;

		try {
			HttpResponse http = new HttpResponse(true, "found", 200, body);
			String jsonInString = mapper.writeValueAsString(http);
			task = mapper.readValue(body, BasketActivityRest.class);
			this.getTaskDao().saveToDB(task);
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
		List<BasketActivity> task = this.getTaskDao().getBasketByOfferId(taskId);

		HttpResponseOffers offer = new HttpResponseOffers(true, "offer saved", 200, local);

		if (local == null) {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			HttpResponse http = new HttpResponse(true, "no offers found ", 200, local);
			String jsonInString = mapper.writeValueAsString(http);
			return new APIGatewayProxyResponseEvent().withStatusCode(404).withBody(jsonInString);
		}			
				try {

					ObjectMapper mapper = new ObjectMapper();

					BasketActivityRest basket = new BasketActivityRest();
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
					this.getTaskDao().saveToDB(basket);
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
	public APIGatewayProxyResponseEvent deleteOffers(APIGatewayProxyRequestEvent request, Context context) {
		String taskId = request.getPathParameters().get("id");
		this.getTaskDao().deleteBasketById(taskId);
		HttpResponse http = new HttpResponse(true, "deleted", 200, taskId);
		return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(http.toString());
	}
		}
