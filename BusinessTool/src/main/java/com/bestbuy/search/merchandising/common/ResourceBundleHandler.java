/**
 * Sep 10, 2012 File created by Ramiro.Serrato
 */
package com.bestbuy.search.merchandising.common;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * This Class is an utility for working with the properties file by reading them through a ResourceBundle
 * @author Ramiro.Serrato
 *
 */
public class ResourceBundleHandler {
	@Autowired private ResourceBundle errorMessagesProperties;
	@Autowired private ResourceBundle successMessagesProperties;
	
	private ResourceBundle customBundle;
	
	public void setCustomBundle(String bundle){
		customBundle = ResourceBundle.getBundle("bundle");
	}
	
	/**
	 * Gets an error message from the error messages properties file, if the key does not exist it will return a null object
	 * @param key The key of the error message
	 * @return A String with the message or null
	 * @author Ramiro.Serrato
	 */
	public String getErrorString(String key){
		String errorMessage;
		
		try{
			errorMessage = errorMessagesProperties.getString(key);
		}
		
		catch(MissingResourceException e) {
			errorMessage = "Not able to retrieve the error message";
		}
		
		return errorMessage; 
	}
		
	public void setErrorMessagesProperties(ResourceBundle errorMessagesProperties) {
		this.errorMessagesProperties = errorMessagesProperties;
	}

	public void setSuccessMessagesProperties(
			ResourceBundle successMessagesProperties) {
		this.successMessagesProperties = successMessagesProperties;
	}

	public void setCustomBundle(ResourceBundle customBundle) {
		this.customBundle = customBundle;
	}

	/**
	 * Gets the error message from the properties file and substitutes the params in the param places {i} present in the message
	 * @param key The key from the properties file pointing to the message
	 * @param params The array of params that we want to replace in the message
	 * @return The message after the params replacement
	 * @author Ramiro.Serrato
	 */
	public String getErrorString(String key, String... params){
		String value = errorMessagesProperties.getString(key);
		value = replaceParams(value, params);
		return value;
	}	
	
	public String getSuccessString(String key){
		return successMessagesProperties.getString(key);
	}
	
	/**
	 * Gets the success message from the properties file and substitutes the params in the param places {i} present in the message
	 * @param key The key from the properties file pointing to the message
	 * @param params The array of params that we want to replace in the message
	 * @return The message after the params replacement
	 * @author Ramiro.Serrato
	 */	
	public String getSuccessString(String key, String... params){
		String value = successMessagesProperties.getString(key);
		value = replaceParams(value, params);
		return value;
	}	
	
	public String getCustomString(String key){
		return customBundle.getString(key);
	}
	
	/**
	 * Gets the message from the custom properties file and substitutes the params in the param places {i} present in the message
	 * @param key The key from the properties file pointing to the message
	 * @param params The array of params that we want to replace in the message
	 * @return The message after the params replacement
	 * @author Ramiro.Serrato
	 */	
	public String getCustomString(String key, String... params){
		String value = customBundle.getString(key);
		value = replaceParams(value, params);		
		return value;
	}	
	
	/**
	 * Replaces the param places in the string, a param place is a {i} string that will be replaced in proper order with the params array elements
	 * @param target The target string
	 * @param params A String[] with the params to be replaced in the param places
	 * @return The string with the final string after parameters insertion
	 * @author Ramiro.Serrato
	 */
	private String replaceParams(String target, String... params){
		String message = target;
		
		for(String param : params){
			message = message.replaceFirst("\\{[0-9]\\}", param);
		}
			
		return message;
	}
}
