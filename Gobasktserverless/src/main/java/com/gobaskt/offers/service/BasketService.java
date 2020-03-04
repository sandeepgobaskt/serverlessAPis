package com.gobaskt.offers.service;

import java.util.List;

import com.gobaskt.offers.model.BasketActivity;


public interface BasketService {
	
	BasketActivity saveToDB(Object object);
	List<BasketActivity> saveToDB(List<?> objects);
	BasketActivity getBasketOfferById(String id);
	
	List<BasketActivity> getBasket();
	
	List<BasketActivity> getBasketByOfferId(String id);
	
	BasketActivity deleteBasketByOfferId(String id);
	public BasketActivity deleteByBasketId(String id);
	///void deleteOffers(String id);


}
