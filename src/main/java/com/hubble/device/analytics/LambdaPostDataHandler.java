/*******************************************************************************
 * Copyright 2015 hubbleconnected.com, or its affiliates. All Rights Reserved.
 *
 * Created At	: 12 December 2015
 * Project   	: Device Analytics
 * Purpose		: Lambda handler for handling post requests
 *******************************************************************************/
package com.hubble.device.analytics;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import com.hubble.device.analytics.DeviceAtmosphere;

import static com.hubble.device.analytics.Settings.dynamoDBMapper;

public class LambdaPostDataHandler implements RequestHandler<Object, Object> 
{

	public Object handleRequest(Object input, Context context) 
	{        
        try
        {
		context.getLogger().log("Input: " + input);// Logging
				
		ArrayList<DeviceAtmosphere> listDeviceAtmosphere = new Gson().fromJson(input.toString(), new TypeToken<List<DeviceAtmosphere>>(){}.getType());
		dynamoDBMapper.batchSave(listDeviceAtmosphere);
        }
        catch (IllegalStateException e) 
        {
         System.out.println("Expected BEGIN_ARRAY but was BEGIN_OBJECT");
    	 e.printStackTrace();
    	
        }
        catch (JsonSyntaxException e1) 
        {
        System.out.println("Gson parsing exception "); 	
        e1.printStackTrace();
        }
	
		return null;
    }
}
