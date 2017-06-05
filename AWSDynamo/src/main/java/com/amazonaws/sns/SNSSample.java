package com.amazonaws.sns;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.DeleteTopicRequest;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.SubscribeRequest;

public class SNSSample {

	static AmazonSNSClient snsClient;
	
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
		snsClient = new AmazonSNSClient(credentials);
		Region usWest2 = Region.getRegion(Regions.US_WEST_2);
		snsClient.setRegion(usWest2);
	}
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
init();


//create a new SNS topic
CreateTopicRequest createTopicRequest = new CreateTopicRequest("MyNewTopic");
CreateTopicResult createTopicResult = snsClient.createTopic(createTopicRequest);
//print TopicArn
System.out.println(createTopicResult);
//get request id for CreateTopicRequest from SNS metadata		
System.out.println("CreateTopicRequest - " + snsClient.getCachedResponseMetadata(createTopicRequest));
String topicArn = "arn:aws:sns:us-west-2:486174305632:MyNewTopic";
//subscribe to an SNS topic
SubscribeRequest subRequest = new SubscribeRequest(topicArn, "email", "pat.bhanu@gmail.com");
snsClient.subscribe(subRequest);
//get request id for SubscribeRequest from SNS metadata
System.out.println("SubscribeRequest - " + snsClient.getCachedResponseMetadata(subRequest));
System.out.println("Check your email and confirm subscription.");


//publish to an SNS topic
String msg = "My text published to SNS topic with email endpoint";
PublishRequest publishRequest = new PublishRequest(topicArn, msg);
PublishResult publishResult = snsClient.publish(publishRequest);
//print MessageId of message published to SNS topic
System.out.println("MessageId - " + publishResult.getMessageId());

//delete an SNS topic
DeleteTopicRequest deleteTopicRequest = new DeleteTopicRequest(topicArn);
snsClient.deleteTopic(deleteTopicRequest);
//get request id for DeleteTopicRequest from SNS metadata
System.out.println("DeleteTopicRequest - " + snsClient.getCachedResponseMetadata(deleteTopicRequest));
	}

}
