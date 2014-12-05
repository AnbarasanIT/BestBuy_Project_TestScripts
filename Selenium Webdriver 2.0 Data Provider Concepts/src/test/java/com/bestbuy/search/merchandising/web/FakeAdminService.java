package com.bestbuy.search.merchandising.web;

import com.bestbuy.search.merchandising.service.HealthDiagnostics;

/**
 * Created with IntelliJ IDEA.
 * User: a998807
 * Date: 2/18/13
 * Time: 11:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class FakeAdminService implements HealthDiagnostics {

    public boolean databaseHealthCheck() {
        return true;
    }
}
