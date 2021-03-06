package com.gobaskt.offers.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.gobaskt.offers.entity.BasketActivityDto;

import com.gobaskt.offers.model.BasketActivity;
import com.gobaskt.offers.service.BasketService;

@Service
public class BasketServiceImpl implements BasketService {

	private AmazonDynamoDB client;
	private DynamoDBMapper mapper;

	public BasketServiceImpl() {
		this.client = AmazonDynamoDBClientBuilder.standard().build();
		this.mapper = new DynamoDBMapper(this.client);
	}

//save offers to basket
	@Override
	public BasketActivity saveToDB(Object object) {

		BasketActivityDto basket = (BasketActivityDto) object;
		BasketActivity basketActivity = new BasketActivity();
		// baksetActivity.setBasket_userId(basket_userId);\

		basketActivity.setOffers(basket.getOffers());
		this.mapper.save(basketActivity);

		return basketActivity;
	}

	@Override
	public List<BasketActivity> saveToDB(List<?> objects) {
		// TODO Auto-generated method stub
		return null;
	}

	// get basket by Id
	@Override
	public BasketActivity getBasketOfferById(String basketId) {
		// TODO Auto-generated method stub
		return mapper.load(BasketActivity.class, basketId);
	}

	// get all basket offers
	@Override
	public List<BasketActivity> getBasket() {
		// TODO Auto-generated method stub
		return this.mapper.scan(BasketActivity.class, new DynamoDBScanExpression());
	}

	// get offers by Id inside basket
	@Override
	public List<BasketActivity> getBasketByOfferId(String offerId) {

		HashMap<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":v1", new AttributeValue().withS(offerId));

		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
				.withFilterExpression("offers.offerId = :v1").withExpressionAttributeValues(eav);

		List<BasketActivity> replies = mapper.scan(BasketActivity.class, scanExpression);
		return replies;

	}

//delete offers By Id
	@Override
	public BasketActivity deleteBasketByOfferId(String id) {
		BasketActivity task = this.getBasketOfferById(id);
		if (task != null) {
			this.mapper.delete(task);
		}
		return task;
	}

	@Override
	public BasketActivity deleteByBasketId(String id) {
		BasketActivity task = this.getBasketOfferById(id);
		if (task != null) {
			this.mapper.delete(task);
		}
		return task;

	}

}
