package com.bestbuy.search.merchandising.log4j;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Implemenation class for changing the log4j levels using JMX
 * @author a993519
 *
 */
public class Log4jConfigurator implements Log4jConfiguratorMXBean {
	
    public List<String> getLoggers() {
        List<String> list = new ArrayList<String>();
        for (Enumeration e = LogManager.getCurrentLoggers();
             e.hasMoreElements(); ) {

            Logger log = (Logger) e.nextElement();
            if (log.getLevel() != null) {
                list.add(log.getName() + " = " + log.getLevel().toString());
            }
        }
        
        return list;
    }

    public String getLogLevel(String logger) {
        String level = "unavailable";

        if (StringUtils.isNotBlank(logger)) {
            Logger log = Logger.getLogger(logger);

            if (log != null) {
                level = log.getLevel().toString();
            }
        }
        return level;
    }
    
    public void setLogLevel(String logger, String level) {
        if (StringUtils.isNotBlank(logger)  &&  StringUtils.isNotBlank(level)) {
            Logger log = Logger.getLogger(logger);

            if (log != null) {
                log.setLevel(Level.toLevel(level.toUpperCase()));
            }
        }
    }

}
