package com.bestbuy.search.merchandising.log4j;

import java.util.List;

/**
 * MBean for changing log4j settings 
 * @author a993519
 *
 */
public interface Log4jConfiguratorMXBean {
	
    /**
     * list of all the logger names and their levels
     */
    List<String> getLoggers();

    /**
     * Get the log level for a given logger
     */
    String getLogLevel(String logger);

    /**
     * Set the log level for a given logger
     */
    void setLogLevel(String logger, String level);
}
