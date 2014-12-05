package com.bestbuy.search.merchandising.service;

import com.bestbuy.search.merchandising.common.exception.ServiceException;

/**
 * Created with IntelliJ IDEA.
 * User: a998807
 * Date: 2/18/13
 * Time: 12:01 PM
 * To change this template use File | Settings | File Templates.
 */
public interface HealthDiagnostics {

    /**
     * Method to check health check
     *
     * @return boolean
     * @throws com.bestbuy.search.merchandising.common.exception.ServiceException
     */
    boolean databaseHealthCheck() throws ServiceException;
}
