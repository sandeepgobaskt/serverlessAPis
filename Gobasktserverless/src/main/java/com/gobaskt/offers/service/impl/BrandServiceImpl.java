package com.gobaskt.offers.service.impl;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.util.StringUtils;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.gobaskt.offers.entity.BrandOffersDummyDto;

import com.gobaskt.offers.model.BrandOfferDummy;
import com.gobaskt.offers.model.LocalMerchantOffer;
import com.gobaskt.offers.service.BrandService;

//implementation of brandService
public class BrandServiceImpl implements BrandService {

	/**
	 *connecting to dynamodb  
	 * */
	private AmazonDynamoDB client;
	
	/**It also enables you to perform various create, read, update, and delete (CRUD) operations on items,
	 *  and execute queries and scans against tables.
	                                                   */
	private DynamoDBMapper mapper;

	public BrandServiceImpl() {
		this.client = AmazonDynamoDBClientBuilder.standard().build();
		this.mapper = new DynamoDBMapper(this.client);
	}

	/**get all brandOffers from DynamoDB 
	 * 
	  */
	public List<BrandOfferDummy> listBrandOffers() {
		return this.mapper.scan(BrandOfferDummy.class, new DynamoDBScanExpression());
	}

	/** Get brandoffers By id */
	public BrandOfferDummy getBrandOffer(String id) {
		return this.mapper.load(BrandOfferDummy.class, id);
	}

	/** save brand offers to brandOffers Table
	 *  */
	public void saveBrandOffer(Object task) {
		BrandOffersDummyDto manualData = (BrandOffersDummyDto) task;
		BrandOfferDummy data = new BrandOfferDummy();
		data.setOfferTitle(manualData.getOfferTitle());
		data.setOfferValue(manualData.getOfferValue());
		data.setOfferTerms(manualData.getOfferTerms());
		data.setOfferCategory(manualData.getOfferCategory());
		data.setOfferSubCategory(manualData.getOfferSubCategory());
		data.setOfferSuperCategory(manualData.getOfferSuperCategory());
		data.setBrandName(manualData.getBrandName());
		data.setClipped(manualData.getClipped());
		data.setClippingFrequency(manualData.getClippingFrequency());
		data.setCouponAffinityScore(manualData.getCouponAffinityScore());
		data.setDateClipped(manualData.getDateClipped());
		data.setDateRedeemed(manualData.getDateRedeemed());
		data.setDateViewed(manualData.getDateViewed());
		data.setViewIntensity(manualData.getViewIntensity());
		data.setViewedDescription(manualData.getViewedDescription());
		data.setOfferDescription(manualData.getOfferDescription());
		data.setMobileOfferImage1(manualData.getMobileOfferImage1());
		data.setMobileOfferImage2(manualData.getMobileOfferImage2());
		data.setWebOfferImage1(manualData.getWebOfferImage1());
		data.setWebOfferImage2(manualData.getWebOfferImage2());
		data.setInternalCouponID(manualData.getInternalCouponID());
		data.setTop_coupon_for_that_brand_at_the_time_of_clipping(
				manualData.getTop_coupon_for_that_brand_at_the_time_of_clipping());
		data.setTop_coupon_for_that_brand_at_the_time_of_redeeming(
				manualData.getTop_coupon_for_that_brand_at_the_time_of_redeeming());
		data.setTop_coupon_for_that_category_at_the_time_of_clipping(
				manualData.getTop_coupon_for_that_category_at_the_time_of_clipping());
		data.setTop_coupon_for_that_category_at_the_time_of_redeeming(
				manualData.getTop_coupon_for_that_category_at_the_time_of_redeeming());
		data.setTop_value_coupon_For_that_category_same_day(
				manualData.getTop_value_coupon_For_that_category_same_day());
		StringBuilder sb = new StringBuilder();
		if (StringUtils.hasText(manualData.getBrandName())) {
			sb.append(manualData.getBrandName().toLowerCase());
			sb.append(" ");
		}
		if (StringUtils.hasText(manualData.getProducts())) {
			sb.append(manualData.getProducts().toLowerCase());
			sb.append(" ");
		}
		if (StringUtils.hasText(manualData.getOfferTerms())) {
			sb.append(manualData.getOfferTerms().toLowerCase());
			sb.append(" ");

		}
		if (StringUtils.hasText(manualData.getOfferDescription())) {
			sb.append(manualData.getOfferDescription().toLowerCase());
			sb.append(" ");

		}
		if (StringUtils.hasText(manualData.getOfferCategory())) {
			sb.append(manualData.getOfferCategory().toLowerCase());
			sb.append(" ");

		}

		data.setSearchData(sb.toString());
		this.mapper.save(data);
	}

	// delete brand Offers By id
	public BrandOfferDummy deleteBrandOffer(String id) {
		BrandOfferDummy task = this.getBrandOffer(id);
		if (task != null) {
			this.mapper.delete(task);
			return task;
		}
		return task;
	}

	// search offers by name,catogery,upc...

	@Override
	public List<BrandOfferDummy> search(String searchData) {
		final List<BrandOfferDummy> users = mapper.scan(BrandOfferDummy.class,
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
	public List<BrandOffersDummyDto> saveToDB(List<?> brandOffers) {
		Iterable<BrandOfferDummy> entities = new Iterable<BrandOfferDummy>() {
			@Override
			public Iterator<BrandOfferDummy> iterator() {
				return new Iterator<BrandOfferDummy>() {
					int index = 0;

					@Override
					public BrandOfferDummy next() {
						BrandOffersDummyDto manualData = (BrandOffersDummyDto) brandOffers.get(index++);
						BrandOfferDummy data = new BrandOfferDummy();
						data.setOfferTitle(manualData.getOfferTitle());
						data.setOfferValue(manualData.getOfferValue());
						data.setOfferTerms(manualData.getOfferTerms());
						data.setOfferCategory(manualData.getOfferCategory());
						data.setOfferSubCategory(manualData.getOfferSubCategory());
						data.setOfferSuperCategory(manualData.getOfferSuperCategory());
						data.setBrandName(manualData.getBrandName());
						data.setClipped(manualData.getClipped());
						data.setClippingFrequency(manualData.getClippingFrequency());
						data.setCouponAffinityScore(manualData.getCouponAffinityScore());
						data.setDateClipped(manualData.getDateClipped());
						data.setDateRedeemed(manualData.getDateRedeemed());
						data.setDateViewed(manualData.getDateViewed());
						data.setViewIntensity(manualData.getViewIntensity());
						data.setViewedDescription(manualData.getViewedDescription());
						data.setOfferDescription(manualData.getOfferDescription());
						data.setMobileOfferImage1(manualData.getMobileOfferImage1());
						data.setMobileOfferImage2(manualData.getMobileOfferImage2());
						data.setWebOfferImage1(manualData.getWebOfferImage1());
						data.setWebOfferImage2(manualData.getWebOfferImage2());
						data.setInternalCouponID(manualData.getInternalCouponID());
						data.setTop_coupon_for_that_brand_at_the_time_of_clipping(
								manualData.getTop_coupon_for_that_brand_at_the_time_of_clipping());
						data.setTop_coupon_for_that_brand_at_the_time_of_redeeming(
								manualData.getTop_coupon_for_that_brand_at_the_time_of_redeeming());
						data.setTop_coupon_for_that_category_at_the_time_of_clipping(
								manualData.getTop_coupon_for_that_category_at_the_time_of_clipping());
						data.setTop_coupon_for_that_category_at_the_time_of_redeeming(
								manualData.getTop_coupon_for_that_category_at_the_time_of_redeeming());
						data.setTop_value_coupon_For_that_category_same_day(
								manualData.getTop_value_coupon_For_that_category_same_day());
						StringBuilder sb = new StringBuilder();
						if (StringUtils.hasText(manualData.getBrandName())) {
							sb.append(manualData.getBrandName().toLowerCase());
							sb.append(" ");
						}
						if (StringUtils.hasText(manualData.getProducts())) {
							sb.append(manualData.getProducts().toLowerCase());
							sb.append(" ");
						}
						if (StringUtils.hasText(manualData.getOfferTerms())) {
							sb.append(manualData.getOfferTerms().toLowerCase());
							sb.append(" ");

						}
						if (StringUtils.hasText(manualData.getOfferDescription())) {
							sb.append(manualData.getOfferDescription().toLowerCase());
							sb.append(" ");

						}
						if (StringUtils.hasText(manualData.getOfferCategory())) {
							sb.append(manualData.getOfferCategory().toLowerCase());
							sb.append(" ");

						}

						data.setSearchData(sb.toString());
						return data;
					}

					@Override
					public boolean hasNext() {
						return brandOffers.size() > index;
					}
				};
			}
		};
		this.mapper.batchSave(entities);
		return null;

	}
}
