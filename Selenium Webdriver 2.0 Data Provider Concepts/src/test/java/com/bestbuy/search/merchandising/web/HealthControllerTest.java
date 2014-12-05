package com.bestbuy.search.merchandising.web;

import com.bestbuy.search.foundation.common.health.data.Health;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertFalse;

/**
 * Created with IntelliJ IDEA.
 * User: a998807
 * Date: 2/18/13
 * Time: 11:30 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring/applicationContext-Test.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class HealthControllerTest {

    @Autowired
    private HealthController healthController;

    @Test
    public void testGetHealth() {
        Health health = healthController.getHealth();

        assertFalse(health.get(Health.ARTIFACT).isEmpty());
        assertFalse(health.get(Health.REVISION).isEmpty());
        assertFalse(health.get(Health.BRANCH).isEmpty());
        assertFalse(health.get(Health.BUILD_NUMBER).isEmpty());
        assertFalse(health.get(Health.BUILD_TIME).isEmpty());
        assertFalse(health.get(Health.GROUP).isEmpty());
        assertFalse(health.get(Health.NAME).isEmpty());
        assertFalse(health.get(Health.VERSION).isEmpty());
        assertFalse(health.get(HealthController.DATABASE_STATUS).isEmpty());
    }
}