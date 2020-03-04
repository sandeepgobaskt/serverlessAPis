package com.gobaskt.offers.service;

import java.util.List;
import java.util.Optional;

import com.gobaskt.offers.model.LocalMerchantOffer;

public interface LocalMerchantOffersServices {


	LocalMerchantOffer saveToDB(Object object);

	List<LocalMerchantOffer> saveToDB(List<?> objects);

	LocalMerchantOffer getLocalMerchantOfferById(String localMerchantOfferId);

	List<LocalMerchantOffer> getLocalMerchantOffers();

	List<LocalMerchantOffer> getoffersByProductName(String pName);

	long getLocalMerchantOfferCount();

	List<LocalMerchantOffer> getLocalMerchantOfferByMultipleIds(List<String> ids);

	LocalMerchantOffer update(Object object);

	LocalMerchantOffer runCampaign(Object object);

	List<LocalMerchantOffer> getoffersByLMName(String LMName);

	List<LocalMerchantOffer> getOffersPublished();

	List<LocalMerchantOffer> getOffersByExprydate(String lmName);

}