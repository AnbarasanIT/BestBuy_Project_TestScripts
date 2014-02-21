package com.bestbuy.search.merchandising.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bestbuy.search.foundation.common.health.data.AppInfo;
import com.bestbuy.search.foundation.common.health.data.Health;
import com.bestbuy.search.foundation.common.health.service.HealthServiceBase;
import com.bestbuy.search.merchandising.common.BTLogger;
import com.bestbuy.search.merchandising.common.ErrorType;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.jobs.FacetJob;
import com.bestbuy.search.merchandising.service.HealthDiagnostics;

/**
 * Created with IntelliJ IDEA.
 * User: a998807
 * Date: 2/18/13
 * Time: 11:28 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class HealthController extends HealthServiceBase {

    private final static BTLogger log = (BTLogger) BTLogger.getBTLogger(HealthController.class.getName());
    
    public static final String DATABASE_STATUS = "Database Status";

    @Autowired
    private HealthDiagnostics adminService;

    /**
     * Method to do health check
     *
     * @return Health
     */
    @RequestMapping(value = "/health", method = RequestMethod.GET)
    @ResponseBody
    @Override
    public Health getHealth() {
        AppInfo appInfo = this.getAppInfo();
        Health health = new Health(appInfo);
        health.put("Status", "SUCCESS");
        try {
            boolean dbStatus = adminService.databaseHealthCheck();
            if (dbStatus) {
                health.put(DATABASE_STATUS, "Database is up and running");
            }
        } catch (ServiceException e) {
            log.error("HealthController", e, ErrorType.APPLICATION, "An error occured while checking database health");
            health.put(DATABASE_STATUS, "Error : [" + e.getCause().getCause().getMessage() + "]");
        }
        return health;
    }
}
