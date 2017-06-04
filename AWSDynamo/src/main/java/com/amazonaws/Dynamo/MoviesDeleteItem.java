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
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteItemResult;

public class MoviesDeleteItem {
	static AmazonDynamoDBClient dynamoDB;

	private static void init() throws Exception {

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
		
			System.out.println("Attempting to create table; please wait...");
		Map<String, AttributeValue> key = new HashMap<>();
		key.put("year", new AttributeValue().withN(Integer.toString(2013)));
		key.put("title", new AttributeValue().withS("Dhoom 3"));
		try {
		DeleteItemRequest deleteitem = new DeleteItemRequest().withTableName(tableName).withKey(key);
		DeleteItemResult deleteitemresult = dynamoDB.deleteItem(deleteitem);
		System.out.println("item deleted");
		}catch (Exception e) {
			System.err.println("Unable to delete table: ");
			System.err.println(e.getMessage());
	}
	}
}
