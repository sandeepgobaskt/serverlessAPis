package com.gobaskt.offers.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;

import com.gobaskt.offers.entity.LocalMerchantOfferManualCsvData;
import com.gobaskt.offers.model.LocalMerchantOffer;
import com.gobaskt.offers.service.LocalMerchantOffersServices;

public class LocalMerchantOfferServicesImpl implements LocalMerchantOffersServices {

	private AmazonDynamoDB client;
	private DynamoDBMapper mapper;

	public LocalMerchantOfferServicesImpl() {
		this.client = AmazonDynamoDBClientBuilder.standard().build();
		this.mapper = new DynamoDBMapper(this.client);
	}

	@Override
	public LocalMerchantOffer saveToDB(Object object) {
		LocalMerchantOfferManualCsvData csvLMOffer = (LocalMerchantOfferManualCsvData) object;
		LocalMerchantOffer lMOffer = new LocalMerchantOffer();

		if (csvLMOffer.getId() == null) {
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

		} else

		{
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
			lMOffer.setFrontTemplateUrl(offer.getBackTemplateUrl());
			lMOffer.setBackTemplateUrl(offer.getBackTemplateUrl());
			lMOffer.setSearchData(offer.getSearchData());
			lMOffer.setOfferStatus("Active");
			lMOffer.setPublished("No");
			this.mapper.save(lMOffer);
		}

		return null;

	}

	@Override
	public LocalMerchantOffer update(Object object) {
		LocalMerchantOfferManualCsvData csvLMOffer = (LocalMerchantOfferManualCsvData) object;
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
		lMOffer.setFrontTemplateUrl(offer.getBackTemplateUrl());
		lMOffer.setBackTemplateUrl(offer.getBackTemplateUrl());
		lMOffer.setSearchData(offer.getSearchData());
		lMOffer.setOfferStatus(offer.getOfferStatus());
		lMOffer.setPublished(csvLMOffer.getPublished());
		this.mapper.save(lMOffer);

		return null;

	}

	@Override
	public List<LocalMerchantOffer> saveToDB(List<?> objects) {
		Iterable<LocalMerchantOffer> entities = new Iterable<LocalMerchantOffer>() {
			@Override
			public Iterator<LocalMerchantOffer> iterator() {
				return new Iterator<LocalMerchantOffer>() {
					int index = 0;

					@Override
					public LocalMerchantOffer next() {
						LocalMerchantOfferManualCsvData csvLMOffer = (LocalMerchantOfferManualCsvData) objects
								.get(index++);
						LocalMerchantOffer lMOffer = new LocalMerchantOffer();

						lMOffer.setPublished(csvLMOffer.getPublished());

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

	@Override
	public LocalMerchantOffer getLocalMerchantOfferById(String localMerchantOfferId) {
		return this.mapper.load(LocalMerchantOffer.class, localMerchantOfferId);
	}

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

	@Override
	public List<LocalMerchantOffer> getLocalMerchantOffers() {
		return this.mapper.scan(LocalMerchantOffer.class, new DynamoDBScanExpression());

	}

	@Override
	public long getLocalMerchantOfferCount() {
		return this.mapper.count(LocalMerchantOffer.class, new DynamoDBScanExpression());
	}

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

}
