/**
 * Sep 10, 2012 Ramiro.Serrato created this file
 */
package com.bestbuy.search.merchandising.common;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.springframework.stereotype.Component;
import static com.bestbuy.search.merchandising.common.MerchandisingConstants.DATE_FORMAT;

@Component
public class JsonDateSerializer extends JsonSerializer<Date> {
    private static final SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
 
    @Override
    public void serialize(Date date, JsonGenerator generator, SerializerProvider provider) throws IOException, JsonProcessingException {
    	String serializedDate = "";
    	
    	if(date != null){
    		serializedDate = formatter.format(date);
    	}
    		
        generator.writeString(serializedDate);
    }
}
