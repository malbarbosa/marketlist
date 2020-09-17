package br.com.marketlist.api.model;

import java.time.OffsetDateTime;
import java.util.Optional;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTyped;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperFieldModel.DynamoDBAttributeType;

import br.com.marketlist.api.converters.OffsetDateTimeConverter;
import lombok.Getter;
import lombok.Setter;

public abstract class AbstractModel {
	
	@DynamoDBAttribute
	@Getter @Setter
	private long code;
	
	@DynamoDBAttribute
	@Getter @Setter
	private String name;
	
	@DynamoDBAttribute
	@Getter 
	protected long version;
	
	@DynamoDBAttribute
	@Getter @Setter
	private boolean deleted;
	
	@DynamoDBAttribute
	@DynamoDBTypeConverted(converter = OffsetDateTimeConverter.class)
	@Getter @Setter
	private OffsetDateTime createdAt;
	@DynamoDBAttribute
	@DynamoDBTyped(DynamoDBAttributeType.M)
	@Getter @Setter
	private Optional<UserApp> createdFor;

	public void nextVersion() {
		this.version += 1;
	}
	
	public void setWhoAndWhenCreatedRegistry(Optional<UserApp> createdBy) {
		this.setCreatedAt(OffsetDateTime.now());
		this.setCreatedFor(createdBy);
	}
}
