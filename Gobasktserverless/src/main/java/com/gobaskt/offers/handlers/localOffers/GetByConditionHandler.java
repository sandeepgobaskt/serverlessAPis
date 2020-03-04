package com.gobaskt.offers.handlers.localOffers;

import java.util.HashMap;
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

public class GetByConditionHandler {

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
	
	public APIGatewayProxyResponseEvent getTask(APIGatewayProxyRequestEvent request, Context context)
			throws JsonProcessingException {
		String taskId = request.getPathParameters().get("id");
		LocalMerchantOffer task = this.getLocalMerchantOffersServices().getLocalMerchantOfferById(taskId);

		LambdaLogger logger = context.getLogger();
		logger.log("request data" + taskId);
		if (task.equals(null)) {
			ObjectMapper mapper = new ObjectMapper();
			HttpResponse http = new HttpResponse(true, DATA_NOT_FOUND, 204, task);
			String jsonInString = mapper.writeValueAsString(http);
			logger.log("data null" + task);
			return new APIGatewayProxyResponseEvent().withStatusCode(204).withBody(jsonInString);
		}

		try {
			ObjectMapper mapper = new ObjectMapper();
			HttpResponse http = new HttpResponse(true, DATA_FOUND, 200, task);
			String jsonInString = mapper.writeValueAsString(http);

			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers", "Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods", "GET,POST,OPTIONS");

			return new APIGatewayProxyResponseEvent().withStatusCode(200).withHeaders(headers).withBody(jsonInString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return new APIGatewayProxyResponseEvent().withStatusCode(500);
		}
	}

	public APIGatewayProxyResponseEvent getTaskByIDs(APIGatewayProxyRequestEvent request, Context context)
			throws JsonProcessingException {
		List<String> taskId = request.getMultiValueQueryStringParameters().get("id");
		List<LocalMerchantOffer> task = this.getLocalMerchantOffersServices()
				.getLocalMerchantOfferByMultipleIds(taskId);

		if (task.isEmpty()) {
			ObjectMapper mapper = new ObjectMapper();
			HttpResponse http = new HttpResponse(true, DATA_NOT_FOUND, 204, task);
			String jsonInString = mapper.writeValueAsString(http);
			// logger.log("data null" + task);
			return new APIGatewayProxyResponseEvent().withStatusCode(204).withBody(jsonInString);
		}

		try {
			ObjectMapper mapper = new ObjectMapper();
			this.getLocalMerchantOffersServices().saveToDB(task);
			HttpResponse http = new HttpResponse(true, "updated", 200, task);
			String jsonInString = mapper.writeValueAsString(http);
			// mapper.readValue(task, new TypeReference<List<LocalMerchantOffer>>(){});

			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers", "Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods", "GET,POST,OPTIONS");

			return new APIGatewayProxyResponseEvent().withStatusCode(200).withHeaders(headers).withBody(jsonInString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return new APIGatewayProxyResponseEvent().withStatusCode(500);
		}
	}

	public APIGatewayProxyResponseEvent getProduct(APIGatewayProxyRequestEvent request, Context context)
			throws JsonProcessingException {
		String taskId = request.getPathParameters().get("search");

		List<LocalMerchantOffer> task = this.getLocalMerchantOffersServices()
				.getoffersByProductName(taskId.toLowerCase());

		if (task.isEmpty()) {
			ObjectMapper mapper = new ObjectMapper();
			HttpResponse http = new HttpResponse(true, DATA_NOT_FOUND, 204, task);
			String jsonInString = mapper.writeValueAsString(http);
			LambdaLogger logger = context.getLogger();
			logger.log("data null" + task);
			return new APIGatewayProxyResponseEvent().withStatusCode(204).withBody(jsonInString);
		}

		try {
			HttpResponse http = new HttpResponse(true, DATA_FOUND, 200, task);
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(http);

			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers", "Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods", "GET,POST,OPTIONS");

			return new APIGatewayProxyResponseEvent().withStatusCode(200).withHeaders(headers).withBody(jsonInString);
		} catch (JsonProcessingException e) {
			HttpResponse http = new HttpResponse(true, "json parse error", 500, task);
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(http);
			e.printStackTrace();
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers", "Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods", "GET,POST,OPTIONS");

			return new APIGatewayProxyResponseEvent().withStatusCode(500).withHeaders(headers).withBody(jsonInString);
		}
	}

	public APIGatewayProxyResponseEvent getOfferByLMName(APIGatewayProxyRequestEvent request, Context context)
			throws JsonProcessingException, Exception {
		String taskId = request.getPathParameters().get("lmname");

		List<LocalMerchantOffer> task = this.getLocalMerchantOffersServices().getOffersByExprydate(taskId);
//		Iterable<LocalMerchantOffer> entities = new Iterable<LocalMerchantOffer>() {
//			@Override
//			public Iterator<LocalMerchantOffer> iterator() {
//				return new Iterator<LocalMerchantOffer>() {
//					int index = 0;
//
//					@Override
//					public LocalMerchantOffer next() {
//						LocalMerchantOffer local = (LocalMerchantOffer) task.get(index++);
//						LocalDate twoWeeksAgo = LocalDate.now();
//						String twoWeeksAgoStr = DateTimeFormatter.ofPattern("MM/dd/yyyy").format(twoWeeksAgo);
//						SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
//
//						try {
//							Date date1 = format.parse(twoWeeksAgoStr);
//							Date date2 = format.parse(local.getOfferExpiryDate());
////) && date1.compareTo(date2) < 0
////							if (local.getPublished().equals("No")) {
////								return local;
////
////							} else {
////								return local;
////							}
//							return local;
//						} catch (ParseException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//							return null;
//						}
//
//					}
//
//					@Override
//					public boolean hasNext() {
//						return task.size() > index;
//					}
//				};
//			}
//		};

		LambdaLogger logger = context.getLogger();
		logger.log("request data" + task);

		if (task.isEmpty()) {
			ObjectMapper mapper = new ObjectMapper();
			HttpResponse http = new HttpResponse(true,DATA_NOT_FOUND , 204, task);
			String jsonInString = mapper.writeValueAsString(http);
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers", "Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
			return new APIGatewayProxyResponseEvent().withStatusCode(204).withHeaders(headers).withBody(jsonInString);
		}

		try {
			ObjectMapper mapper = new ObjectMapper();
			HttpResponse http = new HttpResponse(true, DATA_FOUND, 200, task);
			String jsonInString = mapper.writeValueAsString(http);

			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers", "Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods", "GET,POST,OPTIONS");

			return new APIGatewayProxyResponseEvent().withStatusCode(200).withHeaders(headers).withBody(jsonInString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			HttpResponse http = new HttpResponse(true, "json parse error", 500, task);
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(http);
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers", "Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods", "GET,POST,OPTIONS");

			return new APIGatewayProxyResponseEvent().withStatusCode(500).withHeaders(headers).withBody(jsonInString);
		}
	}
}
