package com.amazonaws.Dynamo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;

public class MoviesReaditem {
	static AmazonDynamoDBClient dynamoDB;

	private static void init() throws Exception {
  
        AWSCredentials credentials = null;
        try {
            credentials = new ProfileCredentialsProvider("default").getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (C:\\Users\\gebruiker\\.aws\\credentials), and is in valid format.",
                    e);
        }
        dynamoDB = new AmazonDynamoDBClient(credentials);
        Region usWest2 = Region.getRegion(Regions.US_WEST_2);
        dynamoDB.setRegion(usWest2);
    }
	
	
	
	public static void main(String[] args) throws Exception {
		
		init();
		String tableName = "Movies";
		Map<String,String> expressionAttributesNames = new HashMap<>();
	    expressionAttributesNames.put("#title","title");
	    expressionAttributesNames.put("#year", "year");
	    Map<String,AttributeValue> expressionAttributeValues = new HashMap<>();
	    expressionAttributeValues.put(":titleValue",new AttributeValue().withS("Dhoom 3"));
	    expressionAttributeValues.put(":yearValue",new AttributeValue().withN(Integer.toString(2013) ));
	    QueryRequest queryRequest = new QueryRequest()
	        .withTableName(tableName)
	        .withKeyConditionExpression("#title = :titleValue and #year = :yearValue")
	        .withExpressionAttributeNames(expressionAttributesNames)
	        .withExpressionAttributeValues(expressionAttributeValues);
	    QueryResult queryResult = dynamoDB.query(queryRequest);
	    List<Map<String,AttributeValue>> attributeValues = queryResult.getItems();
	    if(attributeValues.size()>0) {
	       System.out.println("Results"+attributeValues.get(0));
	        } else {
	          System.out.println("Nothing found");
	        }
	    }
		
	}

