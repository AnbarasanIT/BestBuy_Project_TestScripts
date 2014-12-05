package com.bestbuy.search.merchandising.common;

import java.util.Date;

/**
 * Health response class
 * 
 * @author a948063
 *
 */
public class HealthResponse {
	private String name;
	private int buildNumber;
	private String version;
	private String status;
	private String message;
	private String buildDate;
	private String database;
	
	/**
	 * Method to get build date
	 * 
	 * @return String
	 */
	public String getBuildDate() {
		return buildDate;
	}

	/**
	 * Method to set build date
	 * 
	 * @param buildDate
	 */
	public void setBuildDate(String buildDate) {
		this.buildDate = buildDate;
	}

	/**
	 * Method to get the name
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Method to set name
	 * 
	 * @param name String
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Method to get the build number
	 * 
	 * @return int
	 */
	public int getBuildNumber() {
		return buildNumber;
	}
	
	/**
	 * Method to set the build number
	 * 
	 * @param buildNumber int
	 */
	public void setBuildNumber(int buildNumber) {
		this.buildNumber = buildNumber;
	}
	
	/**
	 * Method to get the version
	 * 
	 * @return String
	 */
	public String getVersion() {
		return version;
	}
	
	/**
	 * Method to set the version
	 * 
	 * @param version String
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	
	/**
	 * Method to get the status
	 * 
	 * @return String
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * Method to set the status
	 * 
	 * @param status String
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * Method to get the message
	 * 
	 * @return String
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Method to set the message
	 * 
	 * @param message String
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Method to get database status
	 * 
	 * @return String
	 */
	public String getDatabase() {
		return database;
	}

	/**
	 * Method to set database status
	 * 
	 * @param database
	 */
	public void setDatabase(String database) {
		this.database = database;
	}
}
