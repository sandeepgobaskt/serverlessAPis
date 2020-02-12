package com.gobaskt.offers.entity;

import java.util.List;

import com.gobaskt.offers.model.OffersAdded;

import lombok.Data;

@Data
public class BasketActivityRest {

	private String id;
	private String basket_userId;


	private OffersAdded offers;


	
	
}
