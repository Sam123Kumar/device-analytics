/*******************************************************************************
 * Copyright 2015 hubbleconnected.com, or its affiliates. All Rights Reserved.
 *
 * Created At	: 12 December 2015
 * Project   	: Device Analytics
 * Purpose		: DynamoDB mapping class for Device atmosphere table
 *******************************************************************************/
package com.hubble.device.analytics;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "device_atmosphere")
public class DeviceAtmosphere 
{
	
	private String device_id;
	private String captured_at;
	private String temperature;	
	private String humidity;
	private String noise;
	
	@DynamoDBHashKey(attributeName = "device_id")
	public String getDeviceId() 
	{
		return device_id;
	}
	public void setDeviceId(String device_id) 
	{
		this.device_id = device_id;
	}
	
	@DynamoDBRangeKey(attributeName = "captured_at")
	public String getCapturedAt() 
	{
		return captured_at;
	}
	public void setCapturedAt(String captured_at) 
	{
		this.captured_at = captured_at;
	}
	
	@DynamoDBAttribute(attributeName = "temperature")
	public String getTemperature() 
	{
		return temperature;
	}
	public void setTemperature(String temperature) 
	{
		this.temperature = temperature;
	}
	
	@DynamoDBAttribute(attributeName = "humidity")
	public String getHumidity() 
	{
		return humidity;
	}
	public void setHumidity(String humidity) 
	{
		this.humidity = humidity;
	}
	
	@DynamoDBAttribute(attributeName = "noise")
	public String getNoise() 
	{
		return noise;
	}
	public void setNoise(String noise) 
	{
		this.noise = noise;
	}

	public DeviceAtmosphere(String device_id, String captured_at, String temperature, String humidity, String noise) 
	{
		this.device_id = device_id;
		this.captured_at = captured_at;
		this.temperature = temperature;
		this.humidity = humidity;
		this.noise = noise;	
	}
	
	public DeviceAtmosphere() 
	{	
	}
	
}

