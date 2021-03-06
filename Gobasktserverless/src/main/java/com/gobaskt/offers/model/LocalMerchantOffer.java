package com.gobaskt.offers.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.Data;

@Data
@DynamoDBTable(tableName = "local")
public class LocalMerchantOffer {

	public final static String search = "search";
	@Id
	@DynamoDBHashKey
	@DynamoDBAutoGeneratedKey
	private String id;

	private String LMOfferTitle;

	@DynamoDBAttribute
	private String LMID;

	@DynamoDBAttribute

	private String LMName;

	@DynamoDBAttribute
	private String LMDescription;

	@DynamoDBAttribute
	private String lMLocation;

	@DynamoDBAttribute
	private String LMOfferType;

	@DynamoDBAttribute
	private String LMOfferSubType;

	@DynamoDBAttribute
	private String LMOfferValue;

	@DynamoDBAttribute
	private String ApplicableProducts;

	@DynamoDBAttribute
	private String productName;

	@DynamoDBAttribute
	private String uPCCode;

	@DynamoDBAttribute
	private String eANCode;

	@DynamoDBAttribute
	private String WebOfferImage1;

	@DynamoDBAttribute
	private String WebOfferImage2;

	@DynamoDBAttribute
	private String MobileOfferImage1;

	@DynamoDBAttribute
	private String MobileOfferImage2;

	@DynamoDBAttribute
	private String OfferRegion;

	@DynamoDBAttribute
	private String OfferStartDate;

	@DynamoDBAttribute
	private String OfferStartTime;

	@DynamoDBAttribute
	private String OfferExpiryDate;

	@DynamoDBAttribute
	private String OfferExpiryTime;

	@DynamoDBAttribute
	private String OfferTermsandConditions;

	@DynamoDBAttribute
	private String OfferCurrency;
	@DynamoDBAttribute
	private String offerSubCategory;
	@DynamoDBAttribute
	private String offerCategory;
	@DynamoDBAttribute
	private String offerSuperCategory;
	@DynamoDBAttribute
	private String selectedTemplateId;
	@DynamoDBAttribute
	private String templateColor1;
	@DynamoDBAttribute
	private String templateColor2;
	@DynamoDBAttribute
	private String offer_TAndC_UsageTerms;
	@DynamoDBAttribute
	private String offer_TAndC_ApplicableOn;
	@DynamoDBAttribute
	private String offer_TAndC_ValidFor;
	@DynamoDBAttribute
	private String frontTemplateUrl;
	@DynamoDBAttribute
	private String backTemplateUrl;
	@DynamoDBAttribute
	private String published;
	@DynamoDBAttribute
	private String offerStatus;

	@DynamoDBAttribute
	@DynamoDBIndexHashKey(globalSecondaryIndexName = "search")
	private String searchData;

}
