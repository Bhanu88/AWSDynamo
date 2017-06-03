package com.amazonaws.Dynamo;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateItemResult;

public class MoviesUpdateItem {
	static AmazonDynamoDBClient dynamoDB;

	private static void init() throws Exception {
		/*
		 * The ProfileCredentialsProvider will return your [default] credential
		 * profile by reading from the credentials file located at
		 * (C:\\Users\\gebruiker\\.aws\\credentials).
		 */
		AWSCredentials credentials = null;
		try {
			credentials = new ProfileCredentialsProvider("default").getCredentials();
		} catch (Exception e) {
			throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
					+ "Please make sure that your credentials file is at the correct "
					+ "location (C:\\Users\\gebruiker\\.aws\\credentials), and is in valid format.", e);
		}
		dynamoDB = new AmazonDynamoDBClient(credentials);
		Region usWest2 = Region.getRegion(Regions.US_WEST_2);
		dynamoDB.setRegion(usWest2);
	}

	public static void main(String[] args) throws Exception {

		init();
		String tableName = "Movies";
		Map<String, AttributeValue> key = new HashMap<>();
		key.put("year", new AttributeValue().withN(Integer.toString(2013)));
		key.put("title", new AttributeValue().withS("Dhoom 3"));

		// Map<String,String> expressionAttributesNames = new HashMap<>();
		// expressionAttributesNames.put("#title","title");
		// expressionAttributesNames.put("#year", "year");
		Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
		expressionAttributeValues.put(":r", new AttributeValue().withN(Integer.toString(4)));

		UpdateItemRequest updateItemRequest = new UpdateItemRequest().withTableName(tableName).withKey(key)
				.withUpdateExpression("set info.rating = :r").withExpressionAttributeValues(expressionAttributeValues);
		UpdateItemResult updateItemResult = dynamoDB.updateItem(updateItemRequest);
		System.out.println("item updated");
	}

}
