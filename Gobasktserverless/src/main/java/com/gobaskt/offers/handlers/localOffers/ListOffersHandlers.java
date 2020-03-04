package com.gobaskt.offers.handlers.localOffers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gobaskt.offers.model.HttpResponse;
import com.gobaskt.offers.model.LocalMerchantOffer;
import com.gobaskt.offers.service.LocalMerchantOffersServices;
import com.gobaskt.offers.service.impl.LocalMerchantOfferServicesImpl;

public class ListOffersHandlers {

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
	public APIGatewayProxyResponseEvent listOffers(APIGatewayProxyRequestEvent request, Context context)
			throws ParseException, JsonProcessingException {
		LambdaLogger logger = context.getLogger();

		List<LocalMerchantOffer> tasks = this.getLocalMerchantOffersServices().getOffersPublished();

		logger.log("request data" + tasks.size());
		if (tasks.isEmpty()) {
			ObjectMapper mapper = new ObjectMapper();
			HttpResponse http = new HttpResponse(true, DATA_NOT_FOUND, 204, tasks);
			String jsonInString = mapper.writeValueAsString(http);
			logger.log("data null" + tasks);
			return new APIGatewayProxyResponseEvent().withStatusCode(204).withBody(jsonInString);
		}
		try {
			HttpResponse http = new HttpResponse(true, DATA_FOUND, 200, tasks);
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(http);

			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers", "Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods", "GET,POST,OPTIONS");

			return new APIGatewayProxyResponseEvent().withStatusCode(200).withHeaders(headers).withBody(jsonInString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			logger.log("jsonParser" + e);
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers", "Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods", "GET,POST,OPTIONS");

			return new APIGatewayProxyResponseEvent().withStatusCode(500).withHeaders(headers);
		}

	}

	
	public APIGatewayProxyResponseEvent listTasks(APIGatewayProxyRequestEvent request, Context context)
			throws ParseException, JsonProcessingException {
		LambdaLogger logger = context.getLogger();
		logger.log("request data" + request.getBody());

		List<LocalMerchantOffer> tasks = this.getLocalMerchantOffersServices().getLocalMerchantOffers();
		Iterable<LocalMerchantOffer> entities = new Iterable<LocalMerchantOffer>() {
			@Override
			public Iterator<LocalMerchantOffer> iterator() {
				return new Iterator<LocalMerchantOffer>() {
					int index = 0;

					@Override
					public LocalMerchantOffer next() {
						LocalMerchantOffer local = (LocalMerchantOffer) tasks.get(index++);
						LocalDate twoWeeksAgo = LocalDate.now();
						String twoWeeksAgoStr = DateTimeFormatter.ofPattern("MM/dd/yyyy").format(twoWeeksAgo);
						SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

						try {
							Date date1 = format.parse(twoWeeksAgoStr);
							Date date2 = format.parse(local.getOfferExpiryDate());

							if ((local.getPublished().equals("Yes")) && date1.compareTo(date2) < 0) {
								return local;

							} else {
								return null;
							}
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return null;
						}

					}

					@Override
					public boolean hasNext() {
						return tasks.size() > index;
					}
				};
			}
		};

		if (tasks.isEmpty()) {
			ObjectMapper mapper = new ObjectMapper();
			HttpResponse http = new HttpResponse(true, DATA_NOT_FOUND, 204, tasks);
			String jsonInString = mapper.writeValueAsString(http);
			logger.log("data null" + tasks);
			return new APIGatewayProxyResponseEvent().withStatusCode(204).withBody(jsonInString);
		}

		try {
			HttpResponse http = new HttpResponse(true, DATA_FOUND, 200, entities);
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(http);

			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers", "Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods", "GET,POST,OPTIONS");

			return new APIGatewayProxyResponseEvent().withStatusCode(200).withHeaders(headers).withBody(jsonInString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			Map<String, String> headers = new HashMap<String, String>();
			logger.log("exception" + e);
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers", "Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods", "GET,POST,OPTIONS");

			return new APIGatewayProxyResponseEvent().withStatusCode(500).withHeaders(headers);
		}

	}
}