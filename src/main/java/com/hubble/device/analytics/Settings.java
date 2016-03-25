/*******************************************************************************
 * Copyright 2015 hubbleconnected.com, or its affiliates. All Rights Reserved.
 *
 * Created At	: 12 December 2015
 * Project   	: Device Analytics
 * Purpose		: Project settings
 *******************************************************************************/
package com.hubble.device.analytics;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

public class Settings 
{
	
	public static final AmazonDynamoDB dynamodb;
	public static final DynamoDBMapper dynamoDBMapper;
	public static final DynamoDB documentAPI;	
	
	static 
	{
            
		 BasicAWSCredentials credentials = new BasicAWSCredentials("AccessKey", "SecretKey");
		 AmazonDynamoDBClient client = new AmazonDynamoDBClient(credentials);
             
		 client.setRegion(Region.getRegion(Regions.US_WEST_2));
		
		 dynamodb = client;	    
		 dynamoDBMapper = new DynamoDBMapper(dynamodb);
		 documentAPI = new DynamoDB(dynamodb);
	}

}
