package com.gobaskt.offers.handlers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.MultipartStream;
import org.json.simple.JSONObject;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.drew.imaging.ImageProcessingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gobaskt.offers.entity.LocalMerchantOfferManualCsvData;
import com.gobaskt.offers.model.HttpResponse;
import com.gobaskt.offers.model.LocalMerchantOffer;
import com.gobaskt.offers.service.LocalMerchantOffersServices;
import com.gobaskt.offers.service.impl.LocalMerchantOfferServicesImpl;

public class TaskRequestHandler {

	private LocalMerchantOffersServices dao;

	public void setTaskDao(LocalMerchantOffersServices dao) {
		this.dao = dao;
	}

	AWSCredentials credentials = new BasicAWSCredentials("AKIA3KDCP36Z56G6MQX5",
			"1M0uwk8Q9mSBAkRA00xMjVfbM9NEARP//vZRFLP7");
	AmazonS3 s3client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
			.withRegion(Regions.US_EAST_2).build();

	private LocalMerchantOffersServices getTaskDao() {
		if (this.dao == null) {
			this.dao = new LocalMerchantOfferServicesImpl();
		}
		return this.dao;
	}

	public APIGatewayProxyResponseEvent listTasks(APIGatewayProxyRequestEvent request, Context context)
			throws ParseException {

		List<LocalMerchantOffer> tasks = this.getTaskDao().getLocalMerchantOffers();

		try {
			HttpResponse http = new HttpResponse(true, "found", 200, tasks);
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(http);

			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers","Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods" ,"GET,POST,OPTIONS");

			return new APIGatewayProxyResponseEvent().withStatusCode(200).withHeaders(headers).withBody(jsonInString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			Map<String, String> headers = new HashMap<String, String>();
			
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers","Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods" ,"GET,POST,OPTIONS");

			return new APIGatewayProxyResponseEvent().withStatusCode(500).withHeaders(headers);
		}

	}

	public APIGatewayProxyResponseEvent listOffers(APIGatewayProxyRequestEvent request, Context context)
			throws ParseException {

		List<LocalMerchantOffer> tasks = this.getTaskDao().getOffersPublished();

		try {
			HttpResponse http = new HttpResponse(true, "found", 200, tasks);
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(http);

			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers","Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods" ,"GET,POST,OPTIONS");

			return new APIGatewayProxyResponseEvent().withStatusCode(200).withHeaders(headers).withBody(jsonInString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers","Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods" ,"GET,POST,OPTIONS");

			return new APIGatewayProxyResponseEvent().withStatusCode(500).withHeaders(headers);
		}

	}

	public APIGatewayProxyResponseEvent createTask(APIGatewayProxyRequestEvent request, Context context)
			throws ImageProcessingException {
		String body = request.getBody();
		ObjectMapper mapper = new ObjectMapper();
		LocalMerchantOfferManualCsvData task;

		try {

			HttpResponse http = new HttpResponse(true, "found", 200, body);
			String jsonInString = mapper.writeValueAsString(http);
			task = mapper.readValue(body, LocalMerchantOfferManualCsvData.class);
			task.setOfferStatus("InActive");
			task.setPublished("No");

			this.getTaskDao().saveToDB(task);
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers","Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods" ,"GET,POST,OPTIONS");

			return new APIGatewayProxyResponseEvent().withStatusCode(200).withHeaders(headers).withBody(jsonInString);
		} catch (JsonProcessingException e) {
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers","Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods" ,"GET,POST,OPTIONS");

			HttpResponse http = new HttpResponse(false, "json parsing error", 500, null);
			e.printStackTrace();
			return new APIGatewayProxyResponseEvent().withStatusCode(500).withHeaders(headers)
					.withBody(http.toString());
		} catch (IOException e) {
			e.printStackTrace();
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers","Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods" ,"GET,POST,OPTIONS");
			return new APIGatewayProxyResponseEvent().withStatusCode(500).withHeaders(headers).withBody(body);
		}
	}

	public APIGatewayProxyResponseEvent getTask(APIGatewayProxyRequestEvent request, Context context) {
		String taskId = request.getPathParameters().get("id");
		LocalMerchantOffer task = this.getTaskDao().getLocalMerchantOfferById(taskId);
		HttpResponse http = new HttpResponse(true, "found", 200, task);

		if (task == null) {
			return new APIGatewayProxyResponseEvent().withStatusCode(404);
		}

		try {
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(http);

			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers","Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods" ,"GET,POST,OPTIONS");

			return new APIGatewayProxyResponseEvent().withStatusCode(200).withHeaders(headers).withBody(jsonInString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return new APIGatewayProxyResponseEvent().withStatusCode(500);
		}
	}

	public APIGatewayProxyResponseEvent getTaskByIDs(APIGatewayProxyRequestEvent request, Context context) {
		List<String> taskId = request.getMultiValueQueryStringParameters().get("id");
		List<LocalMerchantOffer> task = this.getTaskDao().getLocalMerchantOfferByMultipleIds(taskId);
		HttpResponse http = new HttpResponse(true, "found", 200, task);

		if (task == null) {
			return new APIGatewayProxyResponseEvent().withStatusCode(404);
		}

		try {
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(http);

			this.getTaskDao().saveToDB(task);
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers","Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods" ,"GET,POST,OPTIONS");

			return new APIGatewayProxyResponseEvent().withStatusCode(200).withHeaders(headers).withBody(jsonInString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return new APIGatewayProxyResponseEvent().withStatusCode(500);
		}
	}

	public APIGatewayProxyResponseEvent getProduct(APIGatewayProxyRequestEvent request, Context context) {
		String taskId = request.getPathParameters().get("search");

		List<LocalMerchantOffer> task = this.getTaskDao().getoffersByProductName(taskId);
		HttpResponse http = new HttpResponse(true, "found", 200, task);

		if (task == null) {
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers","Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods" ,"GET,POST,OPTIONS");
			return new APIGatewayProxyResponseEvent().withStatusCode(404).withHeaders(headers);
		}

		try {
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(http);

			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers","Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods" ,"GET,POST,OPTIONS");

			return new APIGatewayProxyResponseEvent().withStatusCode(200).withHeaders(headers).withBody(jsonInString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers","Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods" ,"GET,POST,OPTIONS");

			return new APIGatewayProxyResponseEvent().withStatusCode(500).withHeaders(headers);
		}
	}

	public APIGatewayProxyResponseEvent updateTask(APIGatewayProxyRequestEvent request, Context context) {
		String body = request.getBody();
		ObjectMapper mapper = new ObjectMapper();
		LocalMerchantOfferManualCsvData task;

		try {

			task = mapper.readValue(body, LocalMerchantOfferManualCsvData.class);
			task.setPublished("Yes");
			HttpResponse http = new HttpResponse(true, "found", 200, task.getPublished());
			this.getTaskDao().update(task);
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers","Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods" ,"GET,POST,OPTIONS");

			String jsonInString = mapper.writeValueAsString(http);

			return new APIGatewayProxyResponseEvent().withStatusCode(200).withHeaders(headers).withBody(jsonInString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();

			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers","Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods" ,"GET,POST,OPTIONS");

			return new APIGatewayProxyResponseEvent().withStatusCode(500).withHeaders(headers).withBody(body);
		} catch (IOException e) {
			e.printStackTrace();
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers","Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods" ,"GET,POST,OPTIONS");

			return new APIGatewayProxyResponseEvent().withStatusCode(500).withHeaders(headers).withBody(body);
		}
	}

	public APIGatewayProxyResponseEvent imageUpload(APIGatewayProxyRequestEvent request, Context context) {
		LambdaLogger logger = context.getLogger();
		logger.log("Loading Java Lambda handler of Proxy");

		// Log the length of the incoming body
		logger.log(String.valueOf(request.getBody().getBytes().length));

		// Create the APIGatewayProxyResponseEvent response
		APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();

		// Set up contentType String
		String contentType = "";

		// Change these values to fit your region and bucket name
		String clientRegion = "us-east-2";
		String bucketName = "gobaskt-offers";

		// Every file will be named image.jpg in this example.
		// You will want to do something different here in production
		String fileObjKeyName = "image1.jpg";

		try {

			// Get the uploaded file and decode from base64
			byte[] bI = Base64.decodeBase64(request.getBody().getBytes());

			// Get the content-type header and extract the boundary
			Map<String, String> hps = request.getHeaders();
			if (hps != null) {
				contentType = hps.get("content-type");
			}
			String[] boundaryArray = contentType.split("=");

			// Transform the boundary to a byte array
			byte[] boundary = boundaryArray[1].getBytes();

			// Log the extraction for verification purposes
			logger.log(new String(bI, "UTF-8") + "\n");

			// Create a ByteArrayInputStream
			ByteArrayInputStream content = new ByteArrayInputStream(bI);

			// Create a MultipartStream to process the form-data
			MultipartStream multipartStream = new MultipartStream(content, boundary, bI.length, null);

			// Create a ByteArrayOutputStream
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			// Find first boundary in the MultipartStream
			boolean nextPart = multipartStream.skipPreamble();

			// Loop through each segment
			while (nextPart) {
				String header = multipartStream.readHeaders();

				// Log header for debugging
				logger.log("Headers:");
				logger.log(header);

				// Write out the file to our ByteArrayOutputStream
				multipartStream.readBodyData(out);
				// Get the next part, if any
				nextPart = multipartStream.readBoundary();
			}

			// Log completion of MultipartStream processing
			logger.log("Data written to ByteStream");

			// Prepare an InputStream from the ByteArrayOutputStream
			InputStream fis = new ByteArrayInputStream(out.toByteArray());

			// Create our S3Client Object
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(clientRegion).build();

			// Configure the file metadata
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(out.toByteArray().length);
			metadata.setContentType("image/jpeg");
			metadata.setCacheControl("public, max-age=31536000");

			// Put file into S3
			s3Client.putObject(bucketName, fileObjKeyName, fis, metadata);

			// Log status
			logger.log("Put object in S3");

			// Provide a response
			response.setStatusCode(200);
			Map<String, String> responseBody = new HashMap<String, String>();
			responseBody.put("Status", "File stored in S3");
			String responseBodyString = new JSONObject(responseBody).toJSONString();
			response.setBody(responseBodyString);

		} catch (AmazonServiceException e) {
			// The call was transmitted successfully, but Amazon S3 couldn't
			// process it, so it returned an error response.
			logger.log(e.getMessage());
		} catch (SdkClientException e) {
			// Amazon S3 couldn't be contacted for a response, or the client
			// couldn't parse the response from Amazon S3.
			logger.log(e.getMessage());
		} catch (IOException e) {
			// Handle MultipartStream class IOException
			logger.log(e.getMessage());
		}

		logger.log(response.toString());
		return response;

	}

	public APIGatewayProxyResponseEvent updateTasks(APIGatewayProxyRequestEvent request, Context context) {
		String body = request.getBody();
		ObjectMapper mapper = new ObjectMapper();

		List<LocalMerchantOfferManualCsvData> task;

		try {

			task = mapper.readValue(body, new TypeReference<List<LocalMerchantOfferManualCsvData>>() {
			});

			HttpResponse http = new HttpResponse(true, "found", 200, task);
			LocalMerchantOfferManualCsvData local = new LocalMerchantOfferManualCsvData();
			local.setPublished("Yes");
			task.add(local);
			this.getTaskDao().saveToDB(task);
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers","Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods" ,"GET,POST,OPTIONS");

			String jsonInString = mapper.writeValueAsString(http);

			return new APIGatewayProxyResponseEvent().withStatusCode(200).withHeaders(headers).withBody(jsonInString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers","Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods" ,"GET,POST,OPTIONS");

			return new APIGatewayProxyResponseEvent().withStatusCode(500).withHeaders(headers).withBody(body);
		} catch (IOException e) {
			e.printStackTrace();
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Access-Control-Allow-Origin", "*");
			headers.put("Access-Control-Allow-Headers","Content-Type,X-Amz-Date,Authorization,X-Requested-With");
			headers.put("Access-Control-Allow-Methods" ,"GET,POST,OPTIONS");

			return new APIGatewayProxyResponseEvent().withStatusCode(500).withHeaders(headers).withBody(body);
		}
	}

}
