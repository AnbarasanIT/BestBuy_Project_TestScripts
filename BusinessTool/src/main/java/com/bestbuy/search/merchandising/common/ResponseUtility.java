/**
 * 
 */
package com.bestbuy.search.merchandising.common;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.MerchandisingBaseResponse;
import com.bestbuy.search.merchandising.wrapper.PromoWrapper;

/**
 * Utility Class to set and sort response rows in the response object
 * 
 * @author Chanchal.Kumari
 * Date - 05 Oct 2012.
 */
public class ResponseUtility {
	/**
	 * Method to get request entity
	 * 
	 * @param requestorId
	 * @param content
	 * @return HttpEntity
	 */
	public static HttpEntity getRequestEntity(String requestorId, String content){
		HttpHeaders requestHeaders = getRequestHeaders(requestorId);
		HttpEntity requestEntity = null;
		if (StringUtils.isNotBlank(content)) {
			requestEntity = new HttpEntity(content, requestHeaders);
		} else {
			requestEntity = new HttpEntity(requestHeaders);
		}
		return requestEntity;
	}
	
	/**
	 * Method to get request entity
	 * 
	 * @param requestorId
	 * @param content
	 * @return HttpEntity
	 */
	public static HttpEntity getRequestEntity(String requestorId, String content, String mediaType){
		HttpHeaders requestHeaders = getRequestHeaders(requestorId, mediaType);
		HttpEntity requestEntity = null;
		if (StringUtils.isNotBlank(content)) {
			requestEntity = new HttpEntity(content, requestHeaders);
		} else {
			requestEntity = new HttpEntity(requestHeaders);
		}
		return requestEntity;
	}
	
	/**
	 * Method to get request headers
	 * 
	 * @param requestorId
	 * @return HttpHeaders
	 */
	public static HttpHeaders getRequestHeaders(String requestorId) {
		HttpHeaders requestHeaders = new HttpHeaders();
		List<Charset> charset = new ArrayList<Charset>();
		charset.add(Charset.forName("UTF-8"));
		requestHeaders.setAcceptCharset(charset);
		if (StringUtils.isNotBlank(requestorId)) {
			requestHeaders.add("RequestorId", requestorId);
		}
		requestHeaders.add("Content-Type", "application/json;charset=UTF-8");
		return requestHeaders;
	}
	
	/**
	 * Method to get request headers
	 * 
	 * @param requestorId
	 * @return HttpHeaders
	 */
	public static HttpHeaders getRequestHeaders(String requestorId, String mediaType) {
		HttpHeaders requestHeaders = new HttpHeaders();
		List<Charset> charset = new ArrayList<Charset>();
		charset.add(Charset.forName("UTF-8"));
		requestHeaders.setAcceptCharset(charset);
		if (StringUtils.isNotBlank(requestorId)) {
			requestHeaders.add("RequestorId", requestorId);
		}
		requestHeaders.add("Content-Type", mediaType+";charset=UTF-8");
		return requestHeaders;
	}
	
	/**
	 * Method to set the sort the rows in the response object.
	 * 
	 * @param merchandisingBaseResponse
	 * @param wrappers
	 * @author Chanchal.Kumari
	 */
	public static void setAndSortRows(MerchandisingBaseResponse<IWrapper> merchandisingBaseResponse,List<IWrapper> wrappers){
		merchandisingBaseResponse.setRows(wrappers);
		merchandisingBaseResponse.sortRows(); // sort by ModifiedDate
		merchandisingBaseResponse.setRows(PromoWrapper.sortRowIds(merchandisingBaseResponse.getRows()));  // jqgrid needs sorted ids but they got unsorted when sorted by date
	}
}
