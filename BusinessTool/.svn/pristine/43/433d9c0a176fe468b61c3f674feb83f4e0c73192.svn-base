
package com.bestbuy.search.merchandising.jobs;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.bestbuy.search.merchandising.common.JsonDateTimeSerializer;

/**
 * Class to Hold the DAAS Json Request Information. 
 * @author Lakshmi.Valluripalli
 */
public class DaasRequestWrapper<E> {
	
	private Header header;
	private List<E> content;
	
	/**
	 * Default Constructor
	 */
	public DaasRequestWrapper(){}
	
	/**
	 * DaasRequest Constructor
	 * @param type
	 * @param source
	 * @param count
	 * @param timeStamp
	 * @param contents
	 */
	public DaasRequestWrapper(String type,String source,List<E> contents){
		this.header = new Header(type,source,contents.size(),new Date());
		this.content = contents;
	}
	
	/**
	 * returns the Header content
	 * @return
	 */
	public Header getHeader() {
		return header;
	}
 
    /**
     * sets the Header Content
     * @param header
     */
	public void setHeader(Header header) {
		this.header = header;
	}
	
	/**
	 * returns the Contents of the Request Wrapper
	 * @return
	 */
	public List<E> getContent() {
		return content;
	}
	
	/**
	 * sets the contents of the Request Wrapper
	 * @param content
	 */
	public void setContent(List<E> content) {
		this.content = content;
	}
	
	
	/**
	 * Holds the Header contents for the DAAS Service
	 * @author Lakshmi.Valluripalli
	 *
	 */
	class Header {
		
		private String type;
		private String source;
		private Integer count;
		private Date timeStamp;
		private String format;
		
		/**
		 * Default Constructor
		 */
		public Header(){}
		
		/**
		 * Header Constructor
		 * @param type - Assets Type
		 * @param source - source of the request
		 * @param count - total number of assets
		 * @param timeStamp - timestamp at which the request is constructed
		 */
		public Header(String type,String source,Integer count,Date timeStamp){
			this.type = type;
			this.source = source;
			this.count = count;
			this.timeStamp = timeStamp;
			this.format = "SOLR";
		}
		
		/**
		 * gets the Asset Type
		 * @return
		 */
		public String getType() {
			return type;
		}
		
		/**
		 * sets the Asset Type
		 * @param type
		 */
		public void setType(String type) {
			this.type = type;
		}
		
		/**
		 * returns the source of Request
		 * @return
		 */
		public String getSource() {
			return source;
		}
		
		/**
		 * sets the source of the Request
		 * @param source
		 */
		public void setSource(String source) {
			this.source = source;
		}
		
		/**
		 * returns the total number of Assets
		 * @return
		 */
		public Integer getCount() {
			return count;
		}
		
		/**
		 * sets the total number of Assets
		 * @param count
		 */
		public void setCount(Integer count) {
			this.count = count;
		}
		
		/**
		 * returns the timeStamp at which the requested is constructed
		 * @return
		 */
		@JsonSerialize(using=JsonDateTimeSerializer.class)
		public Date getTimeStamp() {
			return timeStamp;
		}
		
		/**
		 * sets the timestamp at which the request is constructed
		 * @param timeStamp
		 */
		public void setTimeStamp(Date timeStamp) {
			this.timeStamp = timeStamp;
		}
		
		/**
		 * represents the SOLR Format 
		 * @return
		 */
		public String getFormat() {
			return format;
		}
		
		/**
		 * Represents the SOLR Format
		 * @param format
		 */
		public void setFormat(String format) {
			this.format = format;
		}
	}
}
