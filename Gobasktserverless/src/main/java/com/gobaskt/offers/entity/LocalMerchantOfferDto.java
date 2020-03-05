package com.gobaskt.offers.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;

import lombok.Data;

/**
 * this is dto(data transfer object)
 * 
 * 
 * */
@Data
public class LocalMerchantOfferDto {

	private String id;
	private String LMOfferTitle;
	private String LMID;
	private String LMName;
	private String LMDescription;
	private String lMLocation;
	private String LMOfferType;
	private String LMOfferSubType;
	private String LMOfferValue;

	// private LocalMerchantOfferProduct availableProducts;

	private String ApplicableProducts;

	private String productName;

	private String uPCCode;

	private String eANCode;
	private String WebOfferImage1;

	private String WebOfferImage2;

	private String MobileOfferImage1;

	private String MobileOfferImage2;
	private String OfferRegion;
	private String OfferStartDate;
	private String OfferStartTime;
	private String OfferExpiryDate;
	private String OfferExpiryTime;
	private String OfferTermsandConditions;
	private String OfferCurrency;
	private String offerSubCategory;	
	private String offerCategory;	
	private String offerSuperCategory;
	private String selectedTemplateId;
	private String templateColor1;
	private String templateColor2;
	private String offer_TAndC_UsageTerms;
	private String offer_TAndC_ApplicableOn;
	private String offer_TAndC_ValidFor;
	private String frontTemplateUrl;
	private String backTemplateUrl;
	private String offerStatus;
	private String published;
	private String searchData;
}
