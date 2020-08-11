package br.com.marketlist.api.request;

import java.io.Serializable;
import java.time.OffsetDateTime;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;

import br.com.marketlist.api.converters.OffsetDateTimeConverter;
import lombok.Data;

@Data
public class CategoryRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9014606199977832674L;
	
	private String id;
	@NotBlank
	@Min(value = 1)
	@Max(value = 30)
	private String name;
	@DynamoDBAttribute
	@DynamoDBTypeConverted(converter = OffsetDateTimeConverter.class)
	private OffsetDateTime dateCreated;
	@DynamoDBAttribute
	@DynamoDBTypeConverted(converter = OffsetDateTimeConverter.class)
	private OffsetDateTime dateUpdated;

}
