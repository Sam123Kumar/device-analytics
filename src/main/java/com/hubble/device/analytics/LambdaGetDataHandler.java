/*******************************************************************************
 * Copyright 2015 hubbleconnected.com, or its affiliates. All Rights Reserved.
 *
 * Created At	: 12 December 2015
 * Project   	: Device Analytics
 * Purpose		: Lambda handler for handling get requests
 *******************************************************************************/
package com.hubble.device.analytics;

import java.util.LinkedHashMap;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.hubble.device.analytics.DeviceAtmosphere;

import static com.hubble.device.analytics.Settings.dynamoDBMapper;

public class LambdaGetDataHandler implements RequestHandler<Object, Object> 
{

	public Object handleRequest(Object input, Context context) 
	{

		context.getLogger().log("Input: " + input);// Logging

		LinkedHashMap<?, ?> inputHashMap = (LinkedHashMap<?, ?>) input;

		String device_id = getKeyValFromHashMap(inputHashMap, "device_id");
		String from_date = getKeyValFromHashMap(inputHashMap, "from_date");
		String to_date = getKeyValFromHashMap(inputHashMap, "to_date");
		String type = getKeyValFromHashMap(inputHashMap, "type");

		Object output = dataWithinTimePeriod(device_id, from_date, to_date, type);

		return output;
	}

	private static Object dataWithinTimePeriod(String device_id, String from_date, String to_date, String type) 
	{
		Condition sortKeyCondition = new Condition();
		List<DeviceAtmosphere> betweenDates = null;
		try 
		{

			if (from_date != "" && to_date != "") 
			{
				sortKeyCondition.withComparisonOperator(ComparisonOperator.BETWEEN.toString()//	Between is used to fetch the result between two date ranges

				);
				sortKeyCondition.withAttributeValueList(new AttributeValue().withS(from_date),
						new AttributeValue().withS(to_date));
			} else if (from_date != "") 
				
			{
				sortKeyCondition.withComparisonOperator(ComparisonOperator.GT.toString()//	GT is used to fetch the result greater than two date ranges

				);
				sortKeyCondition.withAttributeValueList(new AttributeValue().withS(from_date));
			} else if (to_date != "") 
				
			{
				sortKeyCondition.withComparisonOperator(ComparisonOperator.LT.toString()//	LT is used to fetch the result greater than two date ranges

				);
				sortKeyCondition.withAttributeValueList(new AttributeValue().withS(to_date));
			}
		}
		
	
		catch (AmazonServiceException ase) 
		   {
			System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to AWS, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
		   }
		catch (AmazonClientException ace) 
		   {
			System.out.println("Caught an AmazonClientException, which means the client encountered "
			+ "a serious internal problem while trying to communicate with AWS, "
			+ "such as not being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
	       }

		DeviceAtmosphere deviceAtmosphereKey = new DeviceAtmosphere();
		deviceAtmosphereKey.setDeviceId(device_id);

		try 
		  {
			// DynamoDB Query
			DynamoDBQueryExpression<DeviceAtmosphere> queryExpression = new DynamoDBQueryExpression<DeviceAtmosphere>()
					.withProjectionExpression("device_id," + type + ",captured_at")
					.withHashKeyValues(deviceAtmosphereKey).withRangeKeyCondition("captured_at", sortKeyCondition);

			// Casting Paginated query to List of objects belongs to
			// DeviceAtmosphere mapping class

			betweenDates = dynamoDBMapper.query(DeviceAtmosphere.class, queryExpression);

		  }  
		catch (AmazonServiceException ase) 
		  {
			System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to AWS, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());	    
		  }
		
		catch (AmazonClientException ace) 
		 {
			System.out.println("Caught an AmazonClientException, which means the client encountered "
			+ "a serious internal problem while trying to communicate with AWS, "
			+ "such as not being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
		 }
		return betweenDates;

	}

	private static String getKeyValFromHashMap(LinkedHashMap<?, ?> input, String key){
		
		String key_val;
		if (input.containsKey(key))
		{
			key_val = (String) input.get(key);
		}else{
			key_val = "";
		}
		return key_val;
	}
}
