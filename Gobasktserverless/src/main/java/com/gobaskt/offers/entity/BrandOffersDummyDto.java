package com.gobaskt.offers.entity;


import lombok.Data;

/**
 * this is dto(data transfer object)
 * 
 * 
 * */
@Data
public class BrandOffersDummyDto {

	private String id;
	private String Userid;
	private String InternalCouponID;
	private String offerTitle;
	private String offerValue;
	private String offerExpirydate;
	private String offerTerms;
	private String offerDescription;
	private String products;
	private String BrandName;
	private String offerSubCategory;
	private String offerCategory;
	private String offerSuperCategory;
	private String clipped;
	private String dateClipped;
	private String viewedDescription;
	private String viewIntensity;
	private String dateViewed;
	private String Redeemed;
	private String dateRedeemed;

	private String UPCCode="";

	private String EANCode="";
	private String WebOfferImage1;

	private String WebOfferImage2;
	

	private String MobileOfferImage1;

	private String MobileOfferImage2;
	private String YrForFreq;
	private String clippingFrequency;
	private String redemptionFrequency;
	private String couponAffinityScore;
	private String top_value_coupon_For_that_category_same_day;
	private String Top_coupon_for_that_brand_at_the_time_of_clipping;
	private String Top_coupon_for_that_category_at_the_time_of_clipping;
	private String Top_coupon_for_that_brand_at_the_time_of_redeeming;
	private String Top_coupon_for_that_category_at_the_time_of_redeeming;
	private String searchData;

}
