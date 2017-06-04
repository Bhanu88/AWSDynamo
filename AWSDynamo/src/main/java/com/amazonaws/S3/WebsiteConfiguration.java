package com.amazonaws.S3;

import java.io.File;
import java.io.IOException;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.BucketWebsiteConfiguration;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;



public class WebsiteConfiguration {
	private static String bucketName = "bhanu1985";
	private static String indexDoc   = "Index.html";
	private static String errorDoc   = "error.html";
	
	public static void main(String[] args) throws IOException {
        AmazonS3 s3Client = new AmazonS3Client(new ProfileCredentialsProvider());
        
        
        s3Client.setRegion(Region.getRegion(Regions.US_WEST_1));
        
        try {
            if(!(s3Client.doesBucketExist(bucketName)))
            {
            	// Note that CreateBucketRequest does not specify region. So bucket is 
            	// created in the region specified in the client.
            	s3Client.createBucket(new CreateBucketRequest(
						bucketName).withCannedAcl(CannedAccessControlList.PublicRead));
            }
        }catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which " +
            		"means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which " +
            		"means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
        
        
        s3Client.putObject(new PutObjectRequest(bucketName, "Index.html", 
				new File("C:\\Users\\gebruiker\\Desktop\\Index.html"))
				.withCannedAcl(CannedAccessControlList.PublicRead));
        s3Client.putObject(new PutObjectRequest(bucketName, "error.html", 
				new File("C:\\Users\\gebruiker\\Desktop\\error.html"))
				.withCannedAcl(CannedAccessControlList.PublicRead));
        
        
        
        
       //#################################################################### 
        
        
        
        
        
   
        try {
        	// Get existing website configuration, if any.
            getWebsiteConfig(s3Client);
    		
    		// Set new website configuration.
    		s3Client.setBucketWebsiteConfiguration(bucketName, 
    		   new BucketWebsiteConfiguration(indexDoc, errorDoc));
    		
    		// Verify (Get website configuration again).
            getWebsiteConfig(s3Client);
            
            // Delete
            //s3Client.deleteBucketWebsiteConfiguration(bucketName);

       		// Verify (Get website configuration again)
             //getWebsiteConfig(s3Client);
            
  
            
        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which" +
            		" means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means"+
            		" the client encountered " +
                    "a serious internal problem while trying to " +
                    "communicate with Amazon S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
    }

	private static BucketWebsiteConfiguration getWebsiteConfig(
	                                              AmazonS3 s3Client) {
		System.out.println("Get website config");   
		
		// 1. Get website config.
		BucketWebsiteConfiguration bucketWebsiteConfiguration = 
			  s3Client.getBucketWebsiteConfiguration(bucketName);
		if (bucketWebsiteConfiguration == null)
		{
			System.out.println("No website config.");
		}
		else
		{
		     System.out.println("Index doc:" + 
		       bucketWebsiteConfiguration.getIndexDocumentSuffix());
		     System.out.println("Error doc:" + 
		       bucketWebsiteConfiguration.getErrorDocument());
		}
		return bucketWebsiteConfiguration;
	}
}