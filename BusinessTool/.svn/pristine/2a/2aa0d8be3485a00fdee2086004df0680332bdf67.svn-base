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
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.bestbuy.search.merchandising.common.MerchandisingConstants;
import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.dao.BoostAndBlockDao;
import com.bestbuy.search.merchandising.domain.BoostAndBlock;
import com.bestbuy.search.merchandising.domain.BoostAndBlockProduct;
import com.bestbuy.search.merchandising.unittest.common.BaseData;
import com.bestbuy.search.merchandising.wrapper.KeyValueWrapper;
import com.bestbuy.search.merchandising.wrapper.PaginationWrapper;

/**
 * JUnit test for the BoostAndBlockDao.
 * 
 * @author Sreedhar Patlola
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath*:/spring/it-applicationContext*.xml"})
public class BoostAndBlockDaoTest {

  private final static Logger logger = Logger.getLogger(BoostAndBlockDaoTest.class);

  BoostAndBlockDao boostAndBlockDao = null;
  EntityManager entityManager = null;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {

    boostAndBlockDao = new BoostAndBlockDao();
    entityManager = mock(EntityManager.class);
    boostAndBlockDao.setEntityManager(entityManager);
  }

  /**
   * Test method for
   * {@link com.bestbuy.search.merchandising.dao.BoostAndBlockDao#retrieveBoostsAndBlocks(com.bestbuy.search.merchandising.common.SearchCriteria)}
   * .
   */
  @Test
  public void testRetrieveBoostsAndBlocks() {
    try {
      List<BoostAndBlock> boostAndBlocks = getBoostsAndBlocks();
      SearchCriteria criteria = new SearchCriteria();
      Map<String, Object> map = criteria.getNotEqCriteria();
      map.put("status.statusId", MerchandisingConstants.DELETE_STATUS);

      PaginationWrapper paginationWrapper = new PaginationWrapper();
     // paginationWrapper.setRowsPerPage(1);
     // paginationWrapper.setPageIndex(1);
      criteria.setPaginationWrapper(paginationWrapper);
      List<KeyValueWrapper> orCriteriaColumnValues = new ArrayList<KeyValueWrapper>();
      orCriteriaColumnValues.add(new KeyValueWrapper("term","testTerm"));
      orCriteriaColumnValues.add(new KeyValueWrapper("updatedBy","testUser"));
      criteria.setOrCriteriaColumnValues(orCriteriaColumnValues);

      String countSql = "SELECT count(*) FROM BoostAndBlock obj WHERE term = ?1";

      TypedQuery<BoostAndBlock> typedQuery = mock(TypedQuery.class);
      Query query = mock(Query.class);
      CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
      CriteriaQuery<BoostAndBlock> criteriaQuery = mock(CriteriaQuery.class);
      Root<BoostAndBlock> root = mock(Root.class);

      when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
      when(entityManager.createQuery(countSql)).thenReturn(query);

      when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
      when(criteriaBuilder.createQuery(BoostAndBlock.class)).thenReturn(criteriaQuery);
      when(criteriaQuery.from(BoostAndBlock.class)).thenReturn(root);

      when(query.getSingleResult()).thenReturn(1l);
      boostAndBlockDao.retrieveBoostsAndBlocks(criteria);
      assertNotNull(boostAndBlocks);
      assertTrue(boostAndBlocks.get(0).getTerm().equals("testTerm"));

    } catch (DataAcessException se) {
      logger.error("error at service layer while testing retrieveById:.", se);
    }
  }

  private List<BoostAndBlock> getBoostsAndBlocks() {
    List<BoostAndBlock> boostAndBlocks = new ArrayList<BoostAndBlock>();
    BoostAndBlock boostAndBlock = BaseData.getBoostAndBlock();
    boostAndBlocks.add(boostAndBlock);
    return boostAndBlocks;
  }

  /**
   * Test method for {@link com.bestbuy.search.merchandising.dao.BaseDAO#retrieveById(java.lang.Object)}.
   */
  @Test
  public void testRetrieveById() {
    try {
      BoostAndBlock boostAndBlock = BaseData.getBoostAndBlock();
      when(entityManager.find(BoostAndBlock.class, 123456789L)).thenReturn(boostAndBlock);
      boostAndBlock = boostAndBlockDao.retrieveById(123456789L);
      assertNotNull(boostAndBlock);
      assertTrue(boostAndBlock.getTerm().equals("testTerm"));
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
      BoostAndBlock boostAndBlock = BaseData.getBoostAndBlock();
      doAnswer(new Answer() {
        public Object answer(InvocationOnMock invocation) {
          Object[] args = invocation.getArguments();
          EntityManager mock = (EntityManager) invocation.getMock();
          return BaseData.getSynonymListType();
        }
      }).when(entityManager).persist(boostAndBlock);
      boostAndBlock = boostAndBlockDao.save(boostAndBlock);
      assertNotNull(boostAndBlock);
      assertTrue(boostAndBlock.getTerm().equals("testTerm"));
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
      BoostAndBlock boostAndBlock = BaseData.getBoostAndBlock();

      boostAndBlock.setTerm("testTerm1");
      when(entityManager.merge(boostAndBlock)).thenReturn(boostAndBlock);
      boostAndBlock = boostAndBlockDao.update(boostAndBlock);
      assertNotNull(boostAndBlock);
      assertTrue(boostAndBlock.getTerm().equals("testTerm1"));
    } catch (DataAcessException se) {
      logger.error("error at service layer while testing update:.", se);
    }
  }

  /**
   * Test method for {@link com.bestbuy.search.merchandising.dao.BaseDAO#delete(java.lang.Object)}.
   */
  @Test
  public void testDelete() {
    try {
      BoostAndBlock boostAndBlock = BaseData.getBoostAndBlock();
      doAnswer(new Answer() {
        public Object answer(InvocationOnMock invocation) {
          Object[] args = invocation.getArguments();
          EntityManager mock = (EntityManager) invocation.getMock();
          return null;
        }
      }).when(entityManager).remove(boostAndBlock);
      boostAndBlockDao.delete(boostAndBlock);
    } catch (DataAcessException se) {
      logger.error("error at service layer while testing delete:.", se);
    }
  }
  
  
  @Test
  @Transactional
  public void testSearch() throws DataAcessException {

    ApplicationContext ctx = new ClassPathXmlApplicationContext("/spring/it-applicationContext*.xml");
    boostAndBlockDao = (BoostAndBlockDao) ctx.getBean("boostAndBlockDao");

    SearchCriteria criteria = new SearchCriteria();
    PaginationWrapper pw = new PaginationWrapper();
    pw.setPageIndex(1);
    pw.setRowsPerPage(50);
    criteria.setPaginationWrapper(pw);

    Map<String, Object> notEqCriteria = criteria.getNotEqCriteria();

    // notEqCriteria.put("status.statusId",MerchandisingConstants.DELETE_STATUS);
    Collection<BoostAndBlock> boostsAndBlocksFromDB = boostAndBlockDao.executeQueryWithCriteria(criteria);
    for (BoostAndBlock boostAndBlock : boostsAndBlocksFromDB) {
      List<BoostAndBlockProduct> boostAndBlockProducts = boostAndBlock.getBoostAndBlockProducts();
    }
  }

}
