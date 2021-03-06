package com.gobaskt.offers.service;

import java.util.List;

import com.gobaskt.offers.entity.BrandOffersDummyDto;

import com.gobaskt.offers.model.BrandOfferDummy;


public interface BrandService {
	
	public List<BrandOfferDummy> listBrandOffers();

	public BrandOfferDummy getBrandOffer(String id);

	public void saveBrandOffer(Object task);
	public List<BrandOffersDummyDto> saveToDB(List<?> brandOffers);

	public BrandOfferDummy deleteBrandOffer(String id);

	public List<BrandOfferDummy> search(String searchData);
	
}
