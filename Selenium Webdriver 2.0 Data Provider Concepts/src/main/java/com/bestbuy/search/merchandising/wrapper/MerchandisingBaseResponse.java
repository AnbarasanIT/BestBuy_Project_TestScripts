/** 
 * Sep 7, 2012 File created by Ramiro.Serrato
 */
package com.bestbuy.search.merchandising.wrapper;

import static com.bestbuy.search.merchandising.wrapper.ResponseStatusEnum.ERROR;
import static com.bestbuy.search.merchandising.wrapper.ResponseStatusEnum.SUCCESS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;

import com.bestbuy.search.merchandising.common.ResourceBundleHandler;

/**
 * Class that encapsulates the general structure and logic for the responses in the MerchandisingUI application, this class uses the 
 * JsonAutoDetect and JsonProperty annotations, the objects are defined as proxied beans with request scope in order to have one response per request,
 * but spring is inserting proxy fields into the object that jackson is trying to serialize to json some of those properties are failing
 * to serialize, also we dont want the other proxy fields that are not failing, therefore the JsonIgnoreProperties annotation is being used 
 * to ignore those proxy fields.
 * @author Ramiro.Serrato
 *
 * @param <E> This is the wrapper generic interface that must implement the IWrapper interface
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties({ "proxyTargetClass", "exposeProxy", "frozen", "preFiltered" })
public class MerchandisingBaseResponse<E extends IWrapper> {     
	@JsonIgnore @Autowired private ResourceBundleHandler resourceBundleHandler;
	
	private ResponseStatusEnum status = ResponseStatusEnum.SUCCESS;  // success by default
	private String message;
	private String errorCode;
	private String successCode;
	private E data;
	private String page ="1";
	
	private List<E> rows = new ArrayList<E>();
	private List<String> generalPurposeMessage;
	
	public void setResourceBundleHandler(ResourceBundleHandler resourceBundleHandler) {
		this.resourceBundleHandler = resourceBundleHandler;
	}

	@JsonProperty
	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = "1";
	}

	@JsonProperty
	public List<String> getGeneralPurposeMessage() {
		return generalPurposeMessage;
	}

	public void setGeneralPurposeMessage(List<String> generalPurposeMessage) {
		this.generalPurposeMessage = generalPurposeMessage;
	}

	@JsonProperty
	public ResponseStatusEnum getStatus() {
		return status;
	}

	public void setStatus(ResponseStatusEnum string) {
		this.status = string;
	}

	@JsonProperty
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String string) {
		this.message = string;
	}

	@JsonProperty
	public String getErrorCode() {
		return errorCode;
	}

	public ResourceBundleHandler getResourceBundleHandler() {
		return resourceBundleHandler;
	}

	/**
	 * This method sets the error code and also fills the message with the appropriate value from the errorMessages properties
	 * and sets the status to ERROR as well
	 * @param errorCode The error code from the errorMessages.properties
	 * @author Ramiro.Serrato
	 */
	public void setErrorCode(String errorCode) {  
		this.errorCode = errorCode;
		message = resourceBundleHandler.getErrorString(errorCode);
		status = ERROR;
		clearData();  // to be sure that no data is going to the UI when there is an error
	}
	
	/**
	 * This method sets the error code and also fills the message with the appropriate value from the errorMessages properties, it will
	 * also insert string params into the message, finally sets the status to ERROR as well
	 * @param errorCode The error code from the errorMessages.properties
	 * @param params An array of String with the params to be inserted in the error message
	 * @author Ramiro.Serrato
	 */
	public void setErrorCode(String errorCode, String... params) {  
		this.errorCode = errorCode;
		message = resourceBundleHandler.getErrorString(errorCode, params);
		status = ERROR;
		clearData();  // to be sure that no data is going to the UI when there is an error
	}	

	@JsonProperty
	public String getSuccessCode() {
		return successCode;
	}

	/**
	 * This method sets the success code and also fills the message with the appropriate value from the successMessages properties
	 * and sets the status to SUCCESS as well
	 * @param successCode The success code from the errorMessages.properties
	 * @author Ramiro.Serrato
	 */
	public void setSuccessCode(String successCode) {
		this.successCode = successCode;
		message = resourceBundleHandler.getSuccessString(successCode);
		status = SUCCESS;		
	}
	
	/**
	 * This method sets the success code and also fills the message with the appropriate value from the successMessages properties, it will
	 * also insert string params into the message, finally it sets the status to SUCCESS as well
	 * @param successCode The success code from the errorMessages.properties
	 * @param params An array of String with the params to be inserted in the success message
	 * @author Ramiro.Serrato
	 */
	public void setSuccessCode(String successCode, String... params) {
		this.successCode = successCode;
		message = resourceBundleHandler.getSuccessString(successCode, params);
		status = SUCCESS;		
	}	

	@JsonProperty
	public E getData() {
		return data;
	}

	public void setData(E data) {
		this.data = data;
	}

	@JsonProperty
	public List<E> getRows() {
		return rows;
	}

	public void setRows(List<E> rows) {
		this.rows = rows;
	}
	
	public void sortRows(){
		Collections.sort(rows);
	}
	
	private void clearData() {
		rows = null;
		data = null;
	}
}
