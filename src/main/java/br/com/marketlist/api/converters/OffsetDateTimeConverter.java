package br.com.marketlist.api.converters;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.util.StringUtils;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

public class OffsetDateTimeConverter implements DynamoDBTypeConverter<String, OffsetDateTime>{

	@Override
	public String convert(OffsetDateTime offsetDateTime) {
		if(offsetDateTime != null) {
			return offsetDateTime.toString();
		}
		return null;
	}

	@Override
	public OffsetDateTime unconvert(String offsetDateTime) {
		if(!StringUtils.isEmpty(offsetDateTime)) {
			DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
			OffsetDateTime dateConverted = OffsetDateTime.parse(offsetDateTime, DATE_TIME_FORMATTER);
			return dateConverted;
		}
		return null;
		
	}

}
