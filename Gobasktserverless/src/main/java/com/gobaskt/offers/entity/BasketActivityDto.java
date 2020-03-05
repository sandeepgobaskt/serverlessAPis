package com.gobaskt.offers.entity;

import java.util.List;

import com.gobaskt.offers.model.OffersAdded;

import lombok.Data;

/**
 * this is dto(data transfer object)
 * 
 * 
 * */
@Data
public class BasketActivityDto{

	//primary key
	private String id;
	
	//this is also unique coming from consumer table
	private String basket_userId;

//offers data
	private OffersAdded offers;


	
	
}
