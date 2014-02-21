package com.bestbuy.search.merchandising.webservice;

import java.util.Arrays;

import javax.ws.rs.QueryParam;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.bestbuy.search.foundation.common.XmlPropertyPlaceholderConfigurer;
import com.bestbuy.search.merchandising.common.BTLogger;
import com.bestbuy.search.merchandising.common.ErrorType;
import com.bestbuy.search.merchandising.common.MerchandisingConstants;
import com.bestbuy.search.merchandising.common.ResponseUtility;
import com.bestbuy.search.merchandising.service.ICategoryNodeService;

/**
 * This is a client class to call site search and auto suggest webervices of SAAS 
 * @author Lakshmi.Valluripalli
 */
@Controller
@RequestMapping("/externalWS")
public class ExternalWSClient {

  private final static BTLogger log = BTLogger.getBTLogger(ExternalWSClient.class.getName());

	private String searchURI;

	private String suggestURI;

	private String searchIp;
	
	private String port;

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	ICategoryNodeService categoryNodeService;
	
	/**
	 * This method calls the Saas WebService SuggestQuery Operation and returns the Json to UI
	 * @param queryParams
	 * @return
	 */
	@RequestMapping(value = "/suggestQuery", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> suggestQuery(@RequestParam("term") String queryParams,@RequestParam("ip") String ip){
		String url = null;
		try{
			String ipRecieved = "";
			if(ip == null || ip.trim().isEmpty()){
				searchIp = XmlPropertyPlaceholderConfigurer.getProperty("ExternalWS.host");
				ipRecieved = searchIp;
			}
			else{
				ipRecieved=ip;
			}
			log.info("calling suggest service with term:.."+queryParams);
			restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
			restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
			suggestURI = XmlPropertyPlaceholderConfigurer.getProperty("ExternalWS.suggest.query.uri");
			port=XmlPropertyPlaceholderConfigurer.getProperty("ExternalWS.port");
			url = "http://"+ipRecieved+":"+port+suggestURI+queryParams;
			String  response = restTemplate.getForObject(url,String.class);
			return new ResponseEntity<String>(response, ResponseUtility.getRequestHeaders(null), HttpStatus.OK);
		}catch(Exception e){
			log.error("ExternalWSClient", e ,ErrorType.APPLICATION, "Error from Suggest Service method with param.." + url);
			return new ResponseEntity<String>("Error", ResponseUtility.getRequestHeaders(null), HttpStatus.BAD_REQUEST);
		}

	}
	
	/**
	 * This method calls the searchSite method with category_facet as categoryId instead of displayName returns the Json to UI.
	 * @param collection_facet
	 * @param category_facet
	 * @param currentoffers_facet
 	 * @param currentprice_facet
	 * @param debug
	 * @param enableelevation
	 * @param page
 	 * @param q
 	 * @param querydate
	 * @param queryoptions
	 * @param rows
	 * @param sort
	 * @return
	 */
	@RequestMapping(value = "/searchBoostBlockSite", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> boostBlockSearchSite( @QueryParam("query") String query,@QueryParam("ip") String ip,@QueryParam("page") String page,
			@QueryParam("rows") String rows,@QueryParam("sort") String sort,@QueryParam("collection_facet") String collection_facet,
			@QueryParam("brand_facet") String brand_facet,@QueryParam("category_facet") String category_facet,
			@QueryParam("currentprice_facet") String currentprice_facet,@QueryParam("currentoffers_facet") String currentoffers_facet,
			@QueryParam("enableelevation") String enableelevation,@QueryParam("querydate") String querydate) throws Exception{
		category_facet = categoryNodeService.getCategoryNodeId(categoryNodeService.getCategoryPath(category_facet));
		int lastIndex = Arrays.asList(category_facet.split("\\|")).size();
		category_facet = (lastIndex-1)+"|"+category_facet;
		return searchSite(query,ip,page,rows,sort,collection_facet,brand_facet,category_facet,currentprice_facet,currentoffers_facet,enableelevation,querydate);
	}

	/**
	 * This method calls the Saas WebService search site Operation and returns the Json to UI
	 * @param collection_facet
	 * @param category_facet
	 * @param currentoffers_facet
 	 * @param currentprice_facet
	 * @param debug
	 * @param enableelevation
	 * @param page
 	 * @param q
 	 * @param querydate
	 * @param queryoptions
	 * @param rows
	 * @param sort
	 * @return
	 */
	@RequestMapping(value = "/searchSite", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> searchSite( @QueryParam("query") String query,@QueryParam("ip") String ip,@QueryParam("page") String page,
			@QueryParam("rows") String rows,@QueryParam("sort") String sort,@QueryParam("collection_facet") String collection_facet,
			@QueryParam("brand_facet") String brand_facet,@QueryParam("category_facet") String category_facet,@QueryParam("currentprice_facet") String currentprice_facet,@QueryParam("currentoffers_facet") String currentoffers_facet, @QueryParam("enableelevation") String enableelevation,@QueryParam("querydate") String querydate){
		String url = null;
		boolean hasFacet = false;
		try{
			String ipRecieved = "";
			if(ip == null || ip.trim().isEmpty()){
				searchIp= XmlPropertyPlaceholderConfigurer.getProperty("ExternalWS.host");
				ipRecieved = searchIp;
			}
			else{
				ipRecieved=ip;
			}
			//HttpHeaders requestHeaders = new HttpHeaders();
			//HttpEntity requestEntity = new HttpEntity(null, requestHeaders);
			restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
			restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
			searchURI= XmlPropertyPlaceholderConfigurer.getProperty("ExternalWS.search.site.uri");
			port= XmlPropertyPlaceholderConfigurer.getProperty("ExternalWS.port");
			url = "http://"+ipRecieved+":"+port+searchURI + query +"/";
			log.info("the searchsite URL is .."+url);
			//adding Facet params
			if(currentprice_facet != null && !currentprice_facet.isEmpty()){
				url +="currentprice_facet="+currentprice_facet;
				hasFacet = true;
			}
			if(currentoffers_facet != null && !currentoffers_facet.isEmpty()){
				if(hasFacet){
					url +="&currentoffers_facet="+currentoffers_facet;
				}else{
					url +="currentoffers_facet="+currentoffers_facet;
					hasFacet = true;
				}
				
			}
			if(collection_facet != null && !collection_facet.isEmpty()){
				if(hasFacet){
					url +="&collection_facet="+collection_facet;
				}else{
					url +="collection_facet="+collection_facet;
					hasFacet = true;
				}
				
			}
			if(brand_facet != null && !brand_facet.isEmpty()){
				
				if(hasFacet){
					url +="&brand_facet="+brand_facet;
				}else{
					url +="brand_facet="+brand_facet;
					hasFacet = true;
				}
			}
	
			if ("Best Buy".equals(category_facet)) {
			  category_facet = null;
			}
			if(category_facet != null && !category_facet.isEmpty() && !category_facet.equalsIgnoreCase(MerchandisingConstants.BESTBUY_CATEGORY_ID)){
			  if(hasFacet){
					url +="&category_facet="+category_facet;
				}else{
					url +="category_facet="+category_facet;
					hasFacet = true;
				}
			}
			//Rows/Page and Sort Params
			if(rows != null && !rows.isEmpty()){
				url += "/?rows="+ rows;
			}
			if(page != null && !page.isEmpty()){
				url += "&page="+ page;
			}
			if(sort != null && !sort.isEmpty()){
				url += "&sort="+ sort;
			}
			if(enableelevation != null && !enableelevation.isEmpty()){
				url += "&enableelevation="+ enableelevation;
			}
			if(querydate != null && !querydate.isEmpty()){
				url += "&querydate="+ querydate;
			}
			log.info("data sent for search WS..query- "+ url);
			@SuppressWarnings("rawtypes")
			ResponseEntity rssResponse = restTemplate.exchange(
					url,
					HttpMethod.GET,
					ResponseUtility.getRequestEntity(null, null),
					String.class);
			
			// Logging the document count
			String responseBody = rssResponse.getBody().toString();
      
			if( StringUtils.isNotEmpty(responseBody) ){ 
				JSONObject jsonRssResponse = new JSONObject(responseBody) ;
				log.info("The document count for the search term  "+ query +"  is  "+jsonRssResponse.getLong("num_found"));
			}
			return new ResponseEntity<String>(responseBody, ResponseUtility.getRequestHeaders(null), HttpStatus.OK);
		}catch(Exception e){
			log.error("ExternalWSClient", e, ErrorType.APPLICATION, "Error from Suggest Service method with param..query- "+url);
			return new ResponseEntity<String>("Error", ResponseUtility.getRequestHeaders(null), HttpStatus.BAD_REQUEST);
		}
	}
}
