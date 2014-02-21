package com.bestbuy.search.merchandising.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.StringUtils;

import com.bestbuy.search.merchandising.authentication.exception.AuthenticationException;

/**
 * Custom datasource
 * @author a948063
 * @Refactored By Chanchal.Kumari
 */
public class DataSource extends BasicDataSource {
  private final static BTLogger log = BTLogger.getBTLogger(DataSource.class.getName());
	
	/**
	 * Method to set password file
	 * 
	 * @param passwordFile
	 */
	public void setPasswordFile(String passwordFile) throws AuthenticationException{
		
		if(StringUtils.isBlank(passwordFile)){
			log.error("DataSource",ErrorType.APPLICATION,"The path for the password file is null");
			throw new AuthenticationException("The path for the password file is blank " + passwordFile);
		}
		if(StringUtils.isBlank(super.getUsername())){
			log.error("DataSource",ErrorType.APPLICATION,"The path for the password file is null");
			throw new AuthenticationException("The userName is blank " + super.getUsername());
		}
		
		File file = new File(passwordFile);
		if (file.exists()) {
				Properties properties = new Properties();
				FileInputStream fis = null;
				try {
					fis = new FileInputStream(file);
					properties.load(fis);
					if(properties.isEmpty()){
						log.error("DataSource",ErrorType.APPLICATION,"The password is empty" );
						throw new AuthenticationException("The password file is empty" ) ;
					}
					for (Object key : properties.keySet()) {
						if (((String)key).startsWith(super.getUsername())) {
							super.setPassword(properties.getProperty((String)key));
						}
						else{
							log.error("DataSource",ErrorType.APPLICATION,"The username in password file is not correct" );
							throw new AuthenticationException("The username in password file is not correct" ) ;
						}
					}
				} catch (IOException e) {
					log.error("DataSource", e ,ErrorType.APPLICATION,"An error occured while reading properties from password file");
					throw new AuthenticationException("An error occured while reading properties from password file" , e) ;
					
				} finally {
					if (fis != null) {
						try {
							fis.close();
						} catch (IOException e) {
							log.error("DataSource", e ,ErrorType.APPLICATION,"Error while closing the fileInputStream");
							throw new AuthenticationException("Error while closing the fileInputStream" , e) ;
						}
					}
					
				}
			}
			else{
				log.error("DataSource",ErrorType.APPLICATION,"Database password file doesn't exist");
				throw new AuthenticationException("Database password file doesn't exist") ;
			}
		}
}
