package com.bestbuy.search.merchandising.service;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.LukeRequest;
import org.apache.solr.client.solrj.response.LukeResponse;
import org.apache.solr.client.solrj.response.LukeResponse.FieldInfo;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.stereotype.Component;

import com.bestbuy.search.foundation.common.XmlPropertyPlaceholderConfigurer;
import com.bestbuy.search.merchandising.common.exception.ServiceException;


/**
 * This class is used for loading the data from Solr
 * server 
 *@author Lakshmi.Valluripalli
 */
@Component
public class SolrSearchService  implements ISolrSearchService {

	private static Logger logger = Logger.getLogger(SolrSearchService.class);

	private String solrTimeOut;

	private String serverProtocol;

	private String serverHost;

	private String port;

	private String context;

	private HttpSolrServer server;
	

	/**
	 * Method to get server instance
	 * 
	 * @return SolrServer
	 * @throws IOException
	 */
	private SolrServer getServer() throws ServiceException{
		try{
			if (server == null) {
				serverProtocol = XmlPropertyPlaceholderConfigurer.getProperty("solr.protocol");
				serverHost = XmlPropertyPlaceholderConfigurer.getProperty("solr.host");
				port = XmlPropertyPlaceholderConfigurer.getProperty("solr.port");
				context = XmlPropertyPlaceholderConfigurer.getProperty("solr.context");
				String serverUrl = serverProtocol+"://"+serverHost+":"+port+"/"+context;
				server = new HttpSolrServer(serverUrl);
				solrTimeOut = XmlPropertyPlaceholderConfigurer.getProperty("solr.timeout");
				int serverTimeOut =Integer.parseInt(solrTimeOut);
				server.setSoTimeout(serverTimeOut);
			}  
		}catch(Exception e){
			logger.error("Error while retrieving the Solr Connection");
			throw new ServiceException(e);
		}

		return server;
	}

	/**
	 * Loads all the Facet Fields from the Schema.xml
	 * and loads the Facet Values for each facet Field
	 */
	public  QueryResponse loadFacetFields() throws ServiceException{
		QueryResponse queryResponse = null;
		String[] facetsFields = null;
		try{
			LukeRequest request = new LukeRequest();
			request.setShowSchema(true);
			request.setMethod(METHOD.GET);
			LukeResponse response = request.process(getServer());
			if(response != null && response.getFieldInfo() != null){
				Map<String,FieldInfo> fieldInfoMap = response.getFieldInfo();
				Set<String> fieldkeys = fieldInfoMap.keySet();
				String facets = "";//string buffer
				for (String fieldKey : fieldkeys) {
					if(fieldKey.endsWith("_facet")){
						facets +=fieldKey+",";
					}
				}
				if (StringUtils.isNotBlank(facets)) {
					facetsFields = facets.split(",");
					queryResponse = executeQuery(facetsFields);
				}
			}
		}catch(Exception e){
			throw new ServiceException("Error while Loading Facet Fields from Solr",e);
		}
		return queryResponse;
	}

	/**
	 * Loads the Facet Fields Values for each Facet
	 */
	private QueryResponse executeQuery(String facetsFields[]) throws Exception{
		QueryResponse response =  null;
		try{
			SolrQuery query = new SolrQuery();
			query.addFacetField(facetsFields);
			query.setFacetLimit(-1);
			query.setFacet(true);
			query.setParam("wt", "json");
			query.setIncludeScore(false);
			response = getServer().query(query);
		}catch(Exception e){
			logger.error("Error while Executing the SolrQuery:.."+facetsFields.toString());
			throw e;
		}
		return response;
	}
}
