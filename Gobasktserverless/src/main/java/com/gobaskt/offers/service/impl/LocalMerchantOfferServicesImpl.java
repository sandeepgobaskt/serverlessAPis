package com.gobaskt.offers.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.gobaskt.offers.entity.LocalMerchantOfferManualCsvData;
import com.gobaskt.offers.model.BasketActivity;
import com.gobaskt.offers.model.LocalMerchantOffer;
import com.gobaskt.offers.service.LocalMerchantOffersServices;

public class LocalMerchantOfferServicesImpl implements LocalMerchantOffersServices {

	private AmazonDynamoDB client;
	private DynamoDBMapper mapper;

	public LocalMerchantOfferServicesImpl() {
		this.client = AmazonDynamoDBClientBuilder.standard().build();
		this.mapper = new DynamoDBMapper(this.client);
	}

//save offers to db
	@Override
	public LocalMerchantOffer saveToDB(Object object) {
		LocalMerchantOfferManualCsvData csvLMOffer = (LocalMerchantOfferManualCsvData) object;
		LocalMerchantOffer lMOffer = new LocalMerchantOffer();
		lMOffer.setLMID(csvLMOffer.getLMID());
		lMOffer.setLMLocation(csvLMOffer.getLMLocation());
		lMOffer.setLMName(csvLMOffer.getLMName());
		lMOffer.setProductName(csvLMOffer.getProductName());
		lMOffer.setLMDescription(csvLMOffer.getLMDescription());
		
		lMOffer.setLMOfferTitle(csvLMOffer.getLMOfferTitle());
		lMOffer.setLMOfferSubType(csvLMOffer.getLMOfferSubType());
		lMOffer.setLMOfferType(csvLMOffer.getLMOfferType());
		lMOffer.setLMOfferValue(csvLMOffer.getLMOfferValue());
		lMOffer.setOfferCurrency(csvLMOffer.getOfferCurrency());
		lMOffer.setOfferExpiryDate(csvLMOffer.getOfferExpiryDate());
		lMOffer.setOfferExpiryTime(csvLMOffer.getOfferExpiryTime());
		lMOffer.setWebOfferImage1(csvLMOffer.getWebOfferImage1());
		lMOffer.setWebOfferImage2(csvLMOffer.getWebOfferImage2());
		lMOffer.setMobileOfferImage1(csvLMOffer.getMobileOfferImage1());
		lMOffer.setMobileOfferImage2(csvLMOffer.getMobileOfferImage2());
		lMOffer.setOfferRegion(csvLMOffer.getOfferRegion());
		lMOffer.setOfferStartDate(csvLMOffer.getOfferStartDate());
		lMOffer.setOfferStartTime(csvLMOffer.getOfferStartTime());
		lMOffer.setOfferTermsandConditions(csvLMOffer.getOfferTermsandConditions());
		lMOffer.setOfferCategory(csvLMOffer.getOfferCategory());
		lMOffer.setOfferSubCategory(csvLMOffer.getOfferSubCategory());
		lMOffer.setOfferCategory(csvLMOffer.getOfferSuperCategory());
		lMOffer.setSelectedTemplateId(csvLMOffer.getSelectedTemplateId());
		lMOffer.setTemplateColor1(csvLMOffer.getTemplateColor1());
		lMOffer.setTemplateColor2(csvLMOffer.getTemplateColor2());
		lMOffer.setOffer_TAndC_UsageTerms(csvLMOffer.getOffer_TAndC_UsageTerms());
		lMOffer.setOffer_TAndC_ApplicableOn(csvLMOffer.getOffer_TAndC_ApplicableOn());
		lMOffer.setOffer_TAndC_ValidFor(csvLMOffer.getOffer_TAndC_ValidFor());
		lMOffer.setFrontTemplateUrl(csvLMOffer.getFrontTemplateUrl());
		lMOffer.setBackTemplateUrl(csvLMOffer.getBackTemplateUrl());
		lMOffer.setOfferStatus(csvLMOffer.getOfferStatus());
		lMOffer.setPublished(csvLMOffer.getPublished());


		StringBuilder sb = new StringBuilder();
		if (StringUtils.hasText(csvLMOffer.getProductName())) {
			sb.append(csvLMOffer.getProductName().toLowerCase());
			sb.append(" ");
		}
		if (StringUtils.hasText(csvLMOffer.getLMName())) {
			sb.append(csvLMOffer.getLMName().toLowerCase());
			sb.append(" ");
		}
		if (StringUtils.hasText(csvLMOffer.getLMDescription())) {
			sb.append(csvLMOffer.getLMDescription().toLowerCase());
			sb.append(" ");

		}
		if (StringUtils.hasText(csvLMOffer.getPublished())) {
			sb.append(csvLMOffer.getPublished().toLowerCase());
			sb.append(" ");

		}
		if (StringUtils.hasText(csvLMOffer.getEANCode())) {
			sb.append(csvLMOffer.getEANCode().toLowerCase());
			sb.append(" ");

		}
		if (StringUtils.hasText(csvLMOffer.getLMLocation())) {
			sb.append(csvLMOffer.getLMLocation().toLowerCase());
			sb.append(" ");

		}
		if (StringUtils.hasText(csvLMOffer.getLMOfferType())) {
			sb.append(csvLMOffer.getLMOfferType().toLowerCase());
			sb.append(" ");

		}
		if (StringUtils.hasText(csvLMOffer.getLMOfferValue())) {
			sb.append(csvLMOffer.getLMOfferValue().toLowerCase());
			sb.append(" ");

		}
		if (StringUtils.hasText(csvLMOffer.getOfferRegion())) {
			sb.append(csvLMOffer.getOfferRegion().toLowerCase());
			sb.append(" ");

		}
		if (StringUtils.hasText(csvLMOffer.getApplicableProducts())) {
			sb.append(csvLMOffer.getApplicableProducts().toLowerCase());
			sb.append(" ");

		}
		if (StringUtils.hasText(csvLMOffer.getLMOfferTitle())) {
			sb.append(csvLMOffer.getLMOfferTitle().toLowerCase());
			sb.append(" ");

		}
		if (StringUtils.hasText(csvLMOffer.getUPCCode())) {
			sb.append(csvLMOffer.getUPCCode().toLowerCase());
			sb.append(" ");

		}
		lMOffer.setSearchData(sb.toString());
		
		this.mapper.save(lMOffer);

		return null;

	}

	// update offer
	@Override
	public LocalMerchantOffer update(Object object) {
		LocalMerchantOfferManualCsvData csvLMOffer = (LocalMerchantOfferManualCsvData) object;
		LocalMerchantOffer lMOffer = new LocalMerchantOffer();

		LocalMerchantOffer offer = this.getLocalMerchantOfferById(csvLMOffer.getId());

		lMOffer.setLMID(offer.getLMID());
		lMOffer.setLMLocation(csvLMOffer.getLMLocation());
		lMOffer.setLMName(csvLMOffer.getLMName());
		lMOffer.setProductName(csvLMOffer.getProductName());
		lMOffer.setLMDescription(csvLMOffer.getLMDescription());
		
		lMOffer.setId(offer.getId());
		lMOffer.setLMOfferTitle(csvLMOffer.getLMOfferTitle());
		lMOffer.setLMOfferSubType(csvLMOffer.getLMOfferSubType());
		lMOffer.setLMOfferType(csvLMOffer.getLMOfferType());
		lMOffer.setLMOfferValue(csvLMOffer.getLMOfferValue());
		lMOffer.setOfferCurrency(csvLMOffer.getOfferCurrency());
		lMOffer.setOfferExpiryDate(csvLMOffer.getOfferExpiryDate());
		lMOffer.setOfferExpiryTime(csvLMOffer.getOfferExpiryTime());
		lMOffer.setWebOfferImage1(csvLMOffer.getWebOfferImage1());
		lMOffer.setWebOfferImage2(csvLMOffer.getWebOfferImage2());
		lMOffer.setMobileOfferImage1(csvLMOffer.getMobileOfferImage1());
		lMOffer.setMobileOfferImage2(csvLMOffer.getMobileOfferImage2());
		lMOffer.setOfferRegion(csvLMOffer.getOfferRegion());
		lMOffer.setOfferStartDate(csvLMOffer.getOfferStartDate());
		
		lMOffer.setOfferStartTime(csvLMOffer.getOfferStartTime());
		lMOffer.setOfferTermsandConditions(csvLMOffer.getOfferTermsandConditions());
		lMOffer.setOfferCategory(csvLMOffer.getOfferCategory());
		lMOffer.setOfferSubCategory(csvLMOffer.getOfferSubCategory());
		lMOffer.setOfferCategory(csvLMOffer.getOfferSuperCategory());
		lMOffer.setSelectedTemplateId(csvLMOffer.getSelectedTemplateId());
		lMOffer.setTemplateColor1(csvLMOffer.getTemplateColor1());
		lMOffer.setTemplateColor2(csvLMOffer.getTemplateColor2());
		lMOffer.setOffer_TAndC_UsageTerms(csvLMOffer.getOffer_TAndC_UsageTerms());
		lMOffer.setOffer_TAndC_ApplicableOn(csvLMOffer.getOffer_TAndC_ApplicableOn());
		lMOffer.setOffer_TAndC_ValidFor(csvLMOffer.getOffer_TAndC_ValidFor());
		lMOffer.setFrontTemplateUrl(csvLMOffer.getBackTemplateUrl());
		lMOffer.setBackTemplateUrl(csvLMOffer.getBackTemplateUrl());
		lMOffer.setOfferStatus(csvLMOffer.getOfferStatus());
		lMOffer.setFrontTemplateUrl(csvLMOffer.getFrontTemplateUrl());
		lMOffer.setBackTemplateUrl(csvLMOffer.getBackTemplateUrl());

		lMOffer.setPublished(csvLMOffer.getPublished());
		StringBuilder sb = new StringBuilder();
		if (StringUtils.hasText(csvLMOffer.getProductName())) {
			sb.append(csvLMOffer.getProductName().toLowerCase());
			sb.append(" ");
		}
		if (StringUtils.hasText(csvLMOffer.getLMName())) {
			sb.append(csvLMOffer.getLMName().toLowerCase());
			sb.append(" ");
		}
		if (StringUtils.hasText(csvLMOffer.getLMDescription())) {
			sb.append(csvLMOffer.getLMDescription().toLowerCase());
			sb.append(" ");

		}
		if (StringUtils.hasText(csvLMOffer.getEANCode())) {
			sb.append(csvLMOffer.getEANCode().toLowerCase());
			sb.append(" ");

		}
		if (StringUtils.hasText(csvLMOffer.getOfferRegion())) {
			sb.append(csvLMOffer.getOfferRegion().toLowerCase());
			sb.append(" ");

		}
		if (StringUtils.hasText(csvLMOffer.getApplicableProducts())) {
			sb.append(csvLMOffer.getApplicableProducts().toLowerCase());
			sb.append(" ");

		}
		if (StringUtils.hasText(csvLMOffer.getLMOfferTitle())) {
			sb.append(csvLMOffer.getApplicableProducts().toLowerCase());
			sb.append(" ");

		}
		if (StringUtils.hasText(csvLMOffer.getUPCCode())) {
			sb.append(csvLMOffer.getUPCCode().toLowerCase());
			sb.append(" ");

		}
		lMOffer.setSearchData(sb.toString());
		this.mapper.save(lMOffer);

		return null;

	}

	// save multiple offers to db
	@Override
	public List<LocalMerchantOffer> saveToDB(List<?> objects) {
		Iterable<LocalMerchantOffer> entities = new Iterable<LocalMerchantOffer>() {
			@Override
			public Iterator<LocalMerchantOffer> iterator() {
				return new Iterator<LocalMerchantOffer>() {
					int index = 0;

					@Override
					public LocalMerchantOffer next() {
						LocalMerchantOffer csvLMOffer = (LocalMerchantOffer) objects.get(index++);
						LocalMerchantOffer lMOffer = new LocalMerchantOffer();
						lMOffer.setId(csvLMOffer.getId());
						lMOffer.setLMID(csvLMOffer.getLMID());
						lMOffer.setLMLocation(csvLMOffer.getLMLocation());
						lMOffer.setLMName(csvLMOffer.getLMName());
						lMOffer.setProductName(csvLMOffer.getProductName());
						lMOffer.setLMDescription(csvLMOffer.getLMDescription());
						lMOffer.setId(csvLMOffer.getId());
						lMOffer.setLMName(csvLMOffer.getLMName());
						lMOffer.setLMOfferSubType(csvLMOffer.getLMOfferSubType());
						lMOffer.setLMOfferType(csvLMOffer.getLMOfferType());
						lMOffer.setLMOfferValue(csvLMOffer.getLMOfferValue());
						lMOffer.setOfferCurrency(csvLMOffer.getOfferCurrency());
						lMOffer.setOfferExpiryDate(csvLMOffer.getOfferExpiryDate());
						lMOffer.setOfferExpiryTime(csvLMOffer.getOfferExpiryTime());
						lMOffer.setWebOfferImage1(csvLMOffer.getWebOfferImage1());
						lMOffer.setWebOfferImage2(csvLMOffer.getWebOfferImage2());
						lMOffer.setMobileOfferImage1(csvLMOffer.getMobileOfferImage1());
						lMOffer.setMobileOfferImage2(csvLMOffer.getMobileOfferImage2());
						lMOffer.setOfferRegion(csvLMOffer.getOfferRegion());
						lMOffer.setOfferStartDate(csvLMOffer.getOfferStartDate());
						lMOffer.setOfferStartTime(csvLMOffer.getOfferStartTime());
						lMOffer.setOfferTermsandConditions(csvLMOffer.getOfferTermsandConditions());
						lMOffer.setOfferCategory(csvLMOffer.getOfferCategory());
						lMOffer.setOfferSubCategory(csvLMOffer.getOfferSubCategory());
						lMOffer.setOfferCategory(csvLMOffer.getOfferSuperCategory());
						lMOffer.setSelectedTemplateId(csvLMOffer.getSelectedTemplateId());
						lMOffer.setTemplateColor1(csvLMOffer.getTemplateColor1());
						lMOffer.setTemplateColor2(csvLMOffer.getTemplateColor2());
						lMOffer.setOffer_TAndC_UsageTerms(csvLMOffer.getOffer_TAndC_UsageTerms());
						lMOffer.setOffer_TAndC_ApplicableOn(csvLMOffer.getOffer_TAndC_ApplicableOn());
						lMOffer.setOffer_TAndC_ValidFor(csvLMOffer.getOffer_TAndC_ValidFor());
						lMOffer.setFrontTemplateUrl(csvLMOffer.getBackTemplateUrl());
						lMOffer.setBackTemplateUrl(csvLMOffer.getBackTemplateUrl());
						lMOffer.setOfferStatus(csvLMOffer.getOfferStatus());
						// lMOffer.setPublished(csvLMOffer.getPublished());

						/*
						 * StringBuilder sb = new StringBuilder(); if
						 * (StringUtils.hasText(csvLMOffer.getProductName())) {
						 * sb.append(csvLMOffer.getProductName().toLowerCase()); sb.append(" "); } if
						 * (StringUtils.hasText(csvLMOffer.getLMName())) {
						 * sb.append(csvLMOffer.getLMName().toLowerCase()); sb.append(" "); } if
						 * (StringUtils.hasText(csvLMOffer.getLMDescription())) {
						 * sb.append(csvLMOffer.getLMDescription().toLowerCase()); sb.append(" ");
						 * 
						 * } if (StringUtils.hasText(csvLMOffer.getEANCode())) {
						 * sb.append(csvLMOffer.getEANCode().toLowerCase()); sb.append(" ");
						 * 
						 * } if (StringUtils.hasText(csvLMOffer.getOfferRegion())) {
						 * sb.append(csvLMOffer.getOfferRegion().toLowerCase()); sb.append(" ");
						 * 
						 * } if (StringUtils.hasText(csvLMOffer.getApplicableProducts())) {
						 * sb.append(csvLMOffer.getApplicableProducts().toLowerCase()); sb.append(" ");
						 * 
						 * } if (StringUtils.hasText(csvLMOffer.getLMOfferTitle())) {
						 * sb.append(csvLMOffer.getApplicableProducts().toLowerCase()); sb.append(" ");
						 * 
						 * } if (StringUtils.hasText(csvLMOffer.getUPCCode())) {
						 * sb.append(csvLMOffer.getUPCCode().toLowerCase()); sb.append(" ");
						 * 
						 * }
						 */
						lMOffer.setSearchData(csvLMOffer.getSearchData());
						// this.mapper.save(lMOffer);
						lMOffer.setPublished("Yes");

						return lMOffer;

					}

					@Override
					public boolean hasNext() {
						return objects.size() > index;
					}
				};
			}
		};
		this.mapper.batchSave(entities);
		return null;

	}

	// get offer By Id from db
	@Override
	public LocalMerchantOffer getLocalMerchantOfferById(String localMerchantOfferId) {
		return this.mapper.load(LocalMerchantOffer.class, localMerchantOfferId);
	}

	// get multipleoffers by multiple ids
	@Override
	public List<LocalMerchantOffer> getLocalMerchantOfferByMultipleIds(List<String> ids) {

		List<LocalMerchantOffer> activityEntityList = new ArrayList<LocalMerchantOffer>();
		List<AttributeValue> attList = ids.stream().map(x -> new AttributeValue(x.toString()))
				.collect(Collectors.toList());

		DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression().withFilterConditionEntry("id",
				new Condition().withComparisonOperator(ComparisonOperator.IN).withAttributeValueList(attList));
		PaginatedScanList<LocalMerchantOffer> list = mapper.scan(LocalMerchantOffer.class, dynamoDBScanExpression);
		return list;
	}

	// get All Offers
	@Override
	public List<LocalMerchantOffer> getLocalMerchantOffers() {
		return this.mapper.scan(LocalMerchantOffer.class, new DynamoDBScanExpression());

	}

	// count offers
	@Override
	public long getLocalMerchantOfferCount() {
		return this.mapper.count(LocalMerchantOffer.class, new DynamoDBScanExpression());
	}

	// search by name,upc,ean,catogery.....
	@Override

	public List<LocalMerchantOffer> getoffersByProductName(String searchData) {

		final List<LocalMerchantOffer> users = mapper.scan(LocalMerchantOffer.class,
				new DynamoDBScanExpression().withFilterConditionEntry("searchData",
						new Condition().withComparisonOperator(ComparisonOperator.CONTAINS)
								.withAttributeValueList(Collections.singletonList(new AttributeValue(searchData)))));
		if (users == null) {
			return Collections.emptyList();
		} else {
			return users;
		}
	}

	// find by LMname
	@Override

	public List<LocalMerchantOffer> getoffersByLMName(String lmname) {

		final List<LocalMerchantOffer> users = mapper.scan(LocalMerchantOffer.class,
				new DynamoDBScanExpression().withFilterConditionEntry("lMName",

						new Condition().withComparisonOperator(ComparisonOperator.EQ)
								.withAttributeValueList(Collections.singletonList(new AttributeValue(lmname)))));
		if (users == null) {
			return Collections.emptyList();
		} else {
			return users;
		}

	}

	// get published offers only
	@Override

	public List<LocalMerchantOffer> getOffersPublished() {

		final List<LocalMerchantOffer> users = mapper.scan(LocalMerchantOffer.class,
				new DynamoDBScanExpression().withFilterConditionEntry("published",
						new Condition().withComparisonOperator(ComparisonOperator.EQ)
								.withAttributeValueList(Collections.singletonList(new AttributeValue("Yes")))));
		if (users == null) {
			return Collections.emptyList();
		} else {
			return users;
		}
	}

	@Override

	public List<LocalMerchantOffer> getOffersByExprydate(String lmName) {

		//LocalDate twoWeeksAgo = LocalDate.now();

		// SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
		// DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");

		//String twoWeeksAgoStr = DateTimeFormatter.ofPattern("MM/dd/yyyy").format(twoWeeksAgo);

		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":val1", new AttributeValue().withS(lmName));
		eav.put(":val2", new AttributeValue().withS("No"));
		//eav.put(":val3", new AttributeValue().withS("05/17/2020"));

		/*
		 * DynamoDBQueryExpression<LocalMerchantOffer> queryExpression = new
		 * DynamoDBQueryExpression<LocalMerchantOffer>()
		 * .withKeyConditionExpression("LMName = :val1 and offerExpiryDate > :val2").
		 * offerExpiryDate > :val2 withExpressionAttributeValues(eav);between :val2 and
		 * :val3
		 */
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
				.withFilterExpression("lMName = :val1 and published = :val2").withExpressionAttributeValues(eav);

		List<LocalMerchantOffer> latestReplies = mapper.scan(LocalMerchantOffer.class, scanExpression);
		return latestReplies;
		
	}

	// run campaign
	@Override 
	public LocalMerchantOffer runCampaign(Object object) {
		LocalMerchantOffer csvLMOffer = (LocalMerchantOffer) object;
		LocalMerchantOffer lMOffer = new LocalMerchantOffer();
		LocalMerchantOffer offer = this.getLocalMerchantOfferById(csvLMOffer.getId());

		lMOffer.setLMID(offer.getLMID());
		lMOffer.setLMLocation(offer.getLMLocation());
		lMOffer.setLMName(offer.getLMName());
		lMOffer.setLMDescription(offer.getLMDescription());
		lMOffer.setId(offer.getId());
		lMOffer.setProductName(offer.getProductName());
		lMOffer.setLMName(offer.getLMName());
		lMOffer.setLMOfferSubType(offer.getLMOfferSubType());
		lMOffer.setLMOfferType(offer.getLMOfferType());
		lMOffer.setLMOfferValue(offer.getLMOfferValue());
		lMOffer.setOfferCurrency(offer.getOfferCurrency());
		lMOffer.setOfferExpiryDate(offer.getOfferExpiryDate());
		lMOffer.setOfferExpiryTime(offer.getOfferExpiryTime());
		lMOffer.setWebOfferImage1(offer.getWebOfferImage1());
		lMOffer.setWebOfferImage2(offer.getWebOfferImage2());
		lMOffer.setMobileOfferImage1(offer.getMobileOfferImage1());
		lMOffer.setMobileOfferImage2(offer.getMobileOfferImage2());
		lMOffer.setOfferRegion(offer.getOfferRegion());
		lMOffer.setOfferStartDate(offer.getOfferStartDate());
		lMOffer.setOfferStartTime(offer.getOfferStartTime());
		lMOffer.setOfferTermsandConditions(offer.getOfferTermsandConditions());
		lMOffer.setOfferCategory(offer.getOfferCategory());
		lMOffer.setOfferSubCategory(offer.getOfferSubCategory());
		lMOffer.setOfferCategory(offer.getOfferSuperCategory());
		lMOffer.setSelectedTemplateId(offer.getSelectedTemplateId());
		lMOffer.setTemplateColor1(offer.getTemplateColor1());
		lMOffer.setTemplateColor2(offer.getTemplateColor2());
		lMOffer.setOffer_TAndC_UsageTerms(offer.getOffer_TAndC_UsageTerms());
		lMOffer.setOffer_TAndC_ApplicableOn(offer.getOffer_TAndC_ApplicableOn());
		lMOffer.setOffer_TAndC_ValidFor(offer.getOffer_TAndC_ValidFor());
		lMOffer.setFrontTemplateUrl(offer.getFrontTemplateUrl());
		lMOffer.setBackTemplateUrl(offer.getBackTemplateUrl());
		lMOffer.setSearchData(offer.getSearchData());
		lMOffer.setOfferStatus(offer.getOfferStatus());
		lMOffer.setPublished(csvLMOffer.getPublished());
		this.mapper.save(lMOffer);

		return null;
	}

	
	
}
