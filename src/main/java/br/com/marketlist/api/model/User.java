package br.com.marketlist.api.model;

import java.time.OffsetDateTime;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGenerateStrategy;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBGeneratedUuid;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.Data;

@Data 
@DynamoDBTable(tableName = "User")
public class User {
	
	@DynamoDBHashKey
	@DynamoDBGeneratedUuid(DynamoDBAutoGenerateStrategy.CREATE)
	private String id;
	@DynamoDBAttribute
	private String name;
	@DynamoDBAttribute
	private String email;
	@DynamoDBAttribute
	private OffsetDateTime dateCreated;
	@DynamoDBAttribute
	private OffsetDateTime dateUpdated;
	
	
	
}
