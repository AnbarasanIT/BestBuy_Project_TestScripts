package com.bestbuy.search.merchandising.common;

import org.apache.log4j.Logger;

import com.bestbuy.search.foundation.common.logger.SplunkLogger;

public class BTLogger extends SplunkLogger{

  protected BTLogger(String name) {
    super(name);
  }

  public void error(String action, Throwable t, ErrorType errorType, String message) {
    
    String messages = formatErrorMessage(errorType, message);
    super.error(action, t, messages);
  }
  
  public void error(String action, ErrorType errorType, String message) {
	    
	    String messages = formatErrorMessage(errorType, message);
	    super.error(action, messages);
	  }
  
  String formatErrorMessage( ErrorType errorType, String message) {
    StringBuffer buffer = new StringBuffer();
    buffer.append("errorType=\"");
    buffer.append(errorType);
    buffer.append("\",message=\"");
    buffer.append(message);
    buffer.append("\"");
    
    return buffer.toString();
  }
  
  public static BTLogger getBTLogger(String name){
    return (BTLogger)Logger.getLogger(name, new BTLog4JFactory());
  }
}
