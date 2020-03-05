package com.gobaskt.offers.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.springframework.data.annotation.Id;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;

import lombok.Data;
/**
 * Created by sandeep kathoju.
 */


@Data
@DynamoDBDocument

public class OffersAdded  {
	
	/**
	 *this data coming from localOffers table 
	 */
	private String offerId;
	
	@DynamoDBAttribute
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
	private String offerStatus;
	@DynamoDBAttribute
	private String save_timeStamp;
	@DynamoDBAttribute
	private String redeem_timeStamp;

}
