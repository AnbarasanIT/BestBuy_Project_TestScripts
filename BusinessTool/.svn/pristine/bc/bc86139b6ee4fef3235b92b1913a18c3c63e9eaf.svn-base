package com.bestbuy.search.bt.uiconstants;

import java.lang.reflect.Field;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Class where constants of Env_Config.xml are defined
 */

public class Env_Config {
	
	private Log log = LogFactory.getLog("Env_Config");
	private String protocol = "protocol";
	private String context = "context";
	private String host = "host";
	private String port = "port";
	private String browser = "Browser";
	private String resolution = "Resolution";
/**
 * Method to read and assign data to variables
 */
	public Env_Config() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadMerchandisingUIEnvConstansts();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("Exception in Env_Config"+localException.getMessage());
		}
	}
	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
}