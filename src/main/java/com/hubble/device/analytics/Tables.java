/*******************************************************************************
 * Copyright 2015 hubbleconnected.com, or its affiliates. All Rights Reserved.
 *
 * Created At	: 12 December 2015
 * Project   	: Device Analytics
 * Purpose		: Table creation in DynamoDB
 *******************************************************************************/
package com.hubble.device.analytics;

import static com.hubble.device.analytics.Settings.dynamodb;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

public class Tables {

	public static void main(String[] args) throws Exception {
		// Call the method to create the table
		CreateDeviceAtmosphere();
	}

	public static void CreateDeviceAtmosphere() {

		try {
			String DEVICE_ATMOSPHERE_TABLE_NAME = "device_atmosphere";// Table
																		// name

			// Primary key combination
			String DEVICE_ID = "device_id"; // Hash Key
			String CAPTURED_AT = "captured_at"; // Partition Key

			// DynamoDB command to create new table
			dynamodb.createTable(new CreateTableRequest().withTableName(DEVICE_ATMOSPHERE_TABLE_NAME)
					.withKeySchema(new KeySchemaElement(DEVICE_ID, KeyType.HASH),
							new KeySchemaElement(CAPTURED_AT, KeyType.RANGE))
					.withAttributeDefinitions(new AttributeDefinition(DEVICE_ID, ScalarAttributeType.S),
							new AttributeDefinition(CAPTURED_AT, ScalarAttributeType.S))
					.withProvisionedThroughput(new ProvisionedThroughput(15L, 15L)));
		
			// Notify table is created
			System.out.println("New Table Created: " + DEVICE_ATMOSPHERE_TABLE_NAME);
		} catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, which means your request made it "
					+ "to AWS, but was rejected with an error response for some reason.");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {

			System.out.println("Caught an AmazonClientException, which means the client encountered "
					+ "a serious internal problem while trying to communicate with AWS, "
					+ "such as not being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
		}

	}

}
