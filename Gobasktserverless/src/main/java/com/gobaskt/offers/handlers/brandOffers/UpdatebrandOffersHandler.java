package com.gobaskt.offers.handlers.brandOffers;

import com.gobaskt.offers.service.BrandService;
import com.gobaskt.offers.service.impl.BrandServiceImpl;

/**
 * this is lambda hanlder
 * for update offers to basket
 * (this  is feature is not  there in app
 * 
 * */
public class UpdatebrandOffersHandler {

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
}
