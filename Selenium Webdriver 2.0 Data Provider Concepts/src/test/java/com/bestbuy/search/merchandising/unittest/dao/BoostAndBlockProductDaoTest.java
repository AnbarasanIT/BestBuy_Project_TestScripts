/**
 * 
 */
package com.bestbuy.search.merchandising.unittest.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.dao.BoostAndBlockProductDao;
import com.bestbuy.search.merchandising.domain.BoostAndBlockProduct;
import com.bestbuy.search.merchandising.unittest.common.BaseData;

/**
 * JUnit test for the BoostAndBlockProductDao.
 * 
 * @author Sreedhar Patlola
 */
public class BoostAndBlockProductDaoTest {

  private final static Logger logger = Logger.getLogger(BoostAndBlockProductDaoTest.class);

  BoostAndBlockProductDao boostAndBlockProductDao = null;
  EntityManager entityManager = null;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {

    boostAndBlockProductDao = new BoostAndBlockProductDao();
    entityManager = mock(EntityManager.class);
    boostAndBlockProductDao.setEntityManager(entityManager);
  }

  /**
   * Test method for
   * {@link com.bestbuy.search.merchandising.dao.BoostAndBlockProductDao#retrieveBoostsAndBlocksProducts(com.bestbuy.search.merchandising.common.SearchCriteria)}
   * .
   */
  @Test
  public void testRetrieveBoostsAndBlocks() {
    try {
      List<BoostAndBlockProduct> boostAndBlockProducts = getBoostAndBlockProducts();
      SearchCriteria criteria = new SearchCriteria();
      Map<String, Object> map = criteria.getSearchTerms();
      map.put("productName", "testProduct");

      String queryStr = "SELECT obj FROM BoostAndBlockProduct obj WHERE productName = ?1";

      Query query = mock(Query.class);
      when(entityManager.createQuery(queryStr)).thenReturn(query);
      when(query.getResultList()).thenReturn(boostAndBlockProducts);
      boostAndBlockProductDao.retrieveBoostsAndBlocksProducts(criteria);
      assertNotNull(boostAndBlockProducts);
      assertTrue(boostAndBlockProducts.get(0).getProductName().equals("testProduct"));

    } catch (DataAcessException se) {
      logger.error("error at service layer while testing retrieveById:.", se);
    }
  }

  private List<BoostAndBlockProduct> getBoostAndBlockProducts() {
    List<BoostAndBlockProduct> boostAndBlocks = new ArrayList<BoostAndBlockProduct>();
    BoostAndBlockProduct boostAndBlockProduct = BaseData.getBoostAndBlockProduct();
    boostAndBlocks.add(boostAndBlockProduct);
    return boostAndBlocks;
  }

  /**
   * Test method for {@link com.bestbuy.search.merchandising.dao.BaseDAO#retrieveById(java.lang.Object)}.
   */
  @Test
  public void testRetrieveById() {
    try {
      BoostAndBlockProduct boostAndBlockProduct = BaseData.getBoostAndBlockProduct();
      when(entityManager.find(BoostAndBlockProduct.class, 123456789L)).thenReturn(boostAndBlockProduct);
      boostAndBlockProduct = boostAndBlockProductDao.retrieveById(123456789L);
      assertNotNull(boostAndBlockProduct);
      assertTrue(boostAndBlockProduct.getProductName().equals("testProduct"));
    } catch (DataAcessException se) {
      logger.error("error at service layer while testing retrieveById:.", se);
    }
  }

  /**
   * Test method for {@link com.bestbuy.search.merchandising.dao.BaseDAO#save(java.lang.Object)}.
   */
  @Test
  public void testSave() {
    try {
      BoostAndBlockProduct boostAndBlockProduct = BaseData.getBoostAndBlockProduct();
      doAnswer(new Answer() {
        public Object answer(InvocationOnMock invocation) {
          Object[] args = invocation.getArguments();
          EntityManager mock = (EntityManager) invocation.getMock();
          return BaseData.getSynonymListType();
        }
      }).when(entityManager).persist(boostAndBlockProduct);
      boostAndBlockProduct = boostAndBlockProductDao.save(boostAndBlockProduct);
      assertNotNull(boostAndBlockProduct);
      assertTrue(boostAndBlockProduct.getProductName().equals("testProduct"));
    } catch (DataAcessException se) {
      logger.error("error at service layer while testing save:.", se);
    }
  }

  /**
   * Test method for {@link com.bestbuy.search.merchandising.dao.BaseDAO#update(java.lang.Object)}.
   */
  @Test
  public void testUpdate() {
    try {
      BoostAndBlockProduct boostAndBlockProduct = BaseData.getBoostAndBlockProduct();

      boostAndBlockProduct.setProductName("testProduct1");
      when(entityManager.merge(boostAndBlockProduct)).thenReturn(boostAndBlockProduct);
      boostAndBlockProduct = boostAndBlockProductDao.update(boostAndBlockProduct);
      assertNotNull(boostAndBlockProduct);
      assertTrue(boostAndBlockProduct.getProductName().equals("testProduct1"));
    } catch (DataAcessException se) {
      logger.error("error at service layer while testing update:.", se);
    }}

  /**
   * Test method for {@link com.bestbuy.search.merchandising.dao.BaseDAO#delete(java.lang.Object)}.
   */
  @Test
  public void testDelete() {
    try {
      BoostAndBlockProduct boostAndBlockProduct = BaseData.getBoostAndBlockProduct();
      doAnswer(new Answer() {
        public Object answer(InvocationOnMock invocation) {
          Object[] args = invocation.getArguments();
          EntityManager mock = (EntityManager) invocation.getMock();
          return null;
        }
      }).when(entityManager).remove(boostAndBlockProduct);
      boostAndBlockProductDao.delete(boostAndBlockProduct);
    } catch (DataAcessException se) {
      logger.error("error at service layer while testing delete:.", se);
    }
  }

}
