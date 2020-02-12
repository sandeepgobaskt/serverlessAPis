package com.gobaskt.offers.service;

import java.util.List;
import java.util.Optional;

import com.gobaskt.offers.model.LocalMerchantOffer;

public interface LocalMerchantOffersServices {
	//User saveToDB(Object object);
	LocalMerchantOffer saveToDB(Object object);
	List<LocalMerchantOffer> saveToDB(List<?> objects);
	LocalMerchantOffer getLocalMerchantOfferById(String localMerchantOfferId);
	List<LocalMerchantOffer> getLocalMerchantOffers();
	//List<LocalMerchantOffer> getLocalMerchantOffers(int pageNo, int pageSize);
	List<LocalMerchantOffer> getoffersByProductName(String pName);
	long getLocalMerchantOfferCount();
	 List<LocalMerchantOffer> getLocalMerchantOfferByMultipleIds(List<String> ids);
	  LocalMerchantOffer update(Object object);
	  
	  public List<LocalMerchantOffer> getOffersPublished() ;
	/*
	 * boolean deleteLocalMerchantOffers(); boolean deleteLocalMerchantOffer(String
	 * localMerchantOfferId);
	 */

}