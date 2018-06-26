package com.example.aws.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.example.aws.demo.model.SendEmailRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DynamodbService {

	private static AmazonDynamoDB dynamoDB;

	private static void init() {
		try {

			dynamoDB = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_2).build();

		} catch (Exception e) {
			throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
					+ "Please make sure that your credentials file is at the correct "
					+ "location (/Users/mobiler_dev_10/.aws/credentials), and is in valid format.", e);
		}
	}

	public static void createTable() {
		init();
		CreateTableRequest request = new CreateTableRequest()
				.withAttributeDefinitions(new AttributeDefinition("Name", ScalarAttributeType.S))
				.withKeySchema(new KeySchemaElement("Name", KeyType.HASH))
				.withProvisionedThroughput(new ProvisionedThroughput(new Long(10), new Long(10)))
				.withTableName("wiliam-test-dynamodb");

		try {
			CreateTableResult result = dynamoDB.createTable(request);

		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
			System.exit(1);
		}
	}

	public String insertData(SendEmailRequest request) throws JsonProcessingException {
		init();

		String requestData = new ObjectMapper().writeValueAsString(request);
		Map<String, AttributeValue> item = newItem(request.getSenderName(), requestData);
		PutItemRequest putItemRequest = new PutItemRequest("wiliam-test-dynamodb", item);
		PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);
		System.out.println("Result: " + putItemResult);

		return putItemResult.toString();
	}

	private static Map<String, AttributeValue> newItem(String name, String requestData) {
		Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
		item.put("Name", new AttributeValue(name));
		item.put("requestData", new AttributeValue(requestData));

		return item;
	}

}
