
package com.bestbuy.search.merchandising.common;

import static com.bestbuy.search.merchandising.common.MerchandisingConstants.DATE_FORMAT;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.springframework.stereotype.Component;

import com.bestbuy.search.merchandising.web.HealthController;

/**
 * 
 * Json Deserializer class for Dates
 * @author Lakshmi.Valluripalli
 */
@Component
public class JsonDateDeSerializer extends JsonDeserializer<Date> {
  private final static BTLogger log = (BTLogger) BTLogger.getBTLogger(HealthController.class.getName());
	
  @Override
	public Date deserialize(JsonParser jsonParser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		Date formattedDate = null;
	        String date = jsonParser.getText();
	        try {
	        	if(date != null && date.length() >0){
	             formattedDate = format.parse(date);
	        	}
	             return formattedDate;
	        	
	        } catch (ParseException e) {
	        	log.error("JsonDateDeSerializer", e , ErrorType.APPLICATION, "Error while Deserializing the Json Date");
	            throw new RuntimeException(e);
	        }
	}
}
