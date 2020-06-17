package br.com.marketlist.api.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGenerateStrategy;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBGeneratedUuid;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.Data;


@Data
@DynamoDBTable(tableName = "Category")
public class Category {
	
	
	@DynamoDBHashKey
	@DynamoDBGeneratedUuid(DynamoDBAutoGenerateStrategy.CREATE)
	private Integer id;
	@DynamoDBAttribute
	private String name;
	
	
}
