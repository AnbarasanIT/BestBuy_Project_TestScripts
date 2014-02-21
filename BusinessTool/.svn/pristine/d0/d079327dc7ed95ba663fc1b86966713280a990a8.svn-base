/**
 * 
 */
package com.bestbuy.search.merchandising.unittest.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.dao.UsersDAO;
import com.bestbuy.search.merchandising.domain.Users;
import com.bestbuy.search.merchandising.unittest.common.BaseData;

/**
 * Junit TestClass for UserDAO Layer
 * @author Lakshmi - 06 Sep 2012
 */
public class UsersDaoTest {
	private final static Logger logger = Logger.getLogger(UsersDaoTest.class);
	
	UsersDAO userDao = null;
	EntityManager entityManager = null;
	
	/**
	 * Before test case to create the mocks
	 * Creating the dao Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		userDao=new UsersDAO();
		entityManager=mock(EntityManager.class);
		userDao.setEntityManager(entityManager);
		
	}

	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		userDao = null;
		entityManager = null;
	}
	
	/**
	  * TestCase for saveMethod
	  */
	 @Test
	 public void testSave(){
		 try{
			 	Users user = BaseData.getUsers();
			 	doAnswer(new Answer() {
			 	     public Object answer(InvocationOnMock invocation) {
			 	         Object[] args = invocation.getArguments();
			 	        EntityManager mock = (EntityManager) invocation.getMock();
			 	         return BaseData.getUsers();
			 	     }
			 	   }).when(entityManager).persist(user);
			 	Users savedUser = userDao.save(user);
				assertNotNull(savedUser);
				assertTrue(savedUser.getFirstName().equals("MerchandisingUI"));
		 }catch(DataAcessException se){
			 logger.error("error at service layer while testing save:.",se);
		 }
	 }
	 
	 
	 /**
	  * TestCase for save Collection Method
	  */
	 @Test
	 public void testSaveCollection(){
		 try{
			    List<Users> users = new ArrayList<Users>();
			    users.add( BaseData.getUsers());
				users.add(BaseData.getDBUsers());
			 	doAnswer(new Answer() {
			 	     public Object answer(InvocationOnMock invocation) {
			 	         Object[] args = invocation.getArguments();
			 	        EntityManager mock = (EntityManager) invocation.getMock();
			 	        return null;
			 	     }
			 	    }).when(entityManager).persist(users);
			 	userDao.save(users);
		}catch(DataAcessException se){
			 logger.error("error at service layer while testing save:.",se);
		 }
	 }
	 
	 /**
	  * Test case for update  Method
	  */
	 @Test
	 public void testUpdate(){
		 try{
			    Users user = BaseData.getUsers();
				when(entityManager.merge(user)).thenReturn(BaseData.getDBUsers());
				Users savedUser = userDao.update(user);
				assertNotNull(savedUser);
				assertTrue(savedUser.getFirstName().equals("MerchandisingUI"));
		 }catch(DataAcessException se){
			 logger.error("error at service layer while testing update:.",se);
		 }
	 }
	 
	 /**
	  * Test Case for update Collection Method
	  */
	 @Test
	 public void testUpdateCollection(){
		 try{
			    List<Users> users = new ArrayList<Users>();
			    users.add( BaseData.getUsers());
				users.add(BaseData.getDBUsers());
				doAnswer(new Answer() {
			 	     public Object answer(InvocationOnMock invocation) {
			 	         Object[] args = invocation.getArguments();
			 	        EntityManager mock = (EntityManager) invocation.getMock();
			 		    return null;
			 	     }
			 	    }).when(entityManager).persist(users);
				userDao.update(users);
		 }catch(DataAcessException se){
			 logger.error("error at service layer while testing update:.",se);
		 }
	 }
 
	 /**
	  * Test case for RetrieveById Method
	  */
	 @Test
	 public void testRetrieveById(){
		 try{
			    Users user = BaseData.getUsers();
				when(entityManager.find(Users.class,"a1234567")).thenReturn(user);
				Users savedUser = userDao.retrieveById("a1234567");
				assertNotNull(savedUser);
				assertTrue(savedUser.getFirstName().equals("MerchandisingUI"));
		 }catch(DataAcessException se){
			 logger.error("error at service layer while testing retrieveById:.",se);
		 }
	 }
	
	 /**
	  * Test case for RetrieveAll
	  */
	 @Test 
	 public void testRetrieveAll(){
		 try{
			   List<Users> users = new ArrayList<Users>();
			    users.add( BaseData.getUsers());
				users.add(BaseData.getDBUsers());
				Query mockQuery = mock(Query.class);
				when(entityManager.createQuery(Mockito.anyString())).thenReturn(mockQuery);
				when(mockQuery.getResultList()).thenReturn(users);
				List<Users> loadedUsers = (List<Users>)userDao.retrieveAll();
				assertNotNull(loadedUsers);
				assertTrue(loadedUsers.size() > 1);
		 }catch(DataAcessException se){
			 logger.error("error at service layer while testing retrieveAll:.",se);
		 }
	 }
	
	 /**
	  * Test case for Delete Method
	  */
	 @Test
	 public void testDelete(){
		 try{
			    Users user = BaseData.getUsers();
			    doAnswer(new Answer() {
			 	     public Object answer(InvocationOnMock invocation) {
			 	         Object[] args = invocation.getArguments();
			 	        EntityManager mock = (EntityManager) invocation.getMock();
			 	         return null;
			 	      }
			 	    }).when(entityManager).remove(user);
				userDao.delete(user);
		 }catch(DataAcessException se){
			 logger.error("error at service layer while testing delete:.",se);
		 }
	 }
	 
	 /**
	  * Test case for delete method with argument entity and primary key
	  */
	 @Test
	 public void testDeleteEntity(){
		 try{
			    Users user = BaseData.getUsers();
			    doAnswer(new Answer() {
			 	     public Object answer(InvocationOnMock invocation) {
			 	         Object[] args = invocation.getArguments();
			 	        EntityManager mock = (EntityManager) invocation.getMock();
			 	         return null;
			 	      }
			 	    }).when(entityManager).remove(entityManager.getReference(Users.class,
			 	    		user.getLoginName()));
				userDao.delete(user.getLoginName(),user);
		 }catch(DataAcessException se){
			 logger.error("error at service layer while testing delete:.",se);
		 }
	 }

	 /**
	  * TestCase for saveMethod DataAcessException
	  * @throws DataAcessException
	  */
	 @Test(expected = DataAcessException.class)
	 public void testSaveExcep() throws  DataAcessException{
	 	Users user = BaseData.getUsers();
		doThrow(RuntimeException.class).when(entityManager).persist(user);
		userDao.save(user);
	 }
	 
	 /**
	  * TestCase for saveMethod with collection Argument DataAcessException
	  * @throws DataAcessException
	  */
	 @Test(expected = DataAcessException.class)
	 public void testSaveAllExcep() throws  DataAcessException{
	 	Users user = BaseData.getUsers();
		doThrow(RuntimeException.class).when(entityManager).persist(user);
		 List<Users> users = new ArrayList<Users>();
		    users.add( BaseData.getUsers());
			users.add(BaseData.getDBUsers());
		userDao.save(users);
	 }
	 
	 /**
	  * Test case for update Method DataAcessException
	  * @throws DataAcessException
	  */
	 @Test(expected = DataAcessException.class)
	 public void testUpdateExcep() throws  DataAcessException{
		Users user = BaseData.getUsers();
		when(entityManager.merge(user)).thenThrow(new RuntimeException());
		userDao.update(user);
	 }
	 
	 /**
	  * Test case for update Method with Collection Argument DataAcessException
	  * @throws DataAcessException
	  */
	 @Test(expected = DataAcessException.class)
	 public void testUpdateAllExcep() throws  DataAcessException{
		Users user = BaseData.getUsers();
		when(entityManager.merge(user)).thenThrow(new RuntimeException());
		 List<Users> users = new ArrayList<Users>();
		    users.add( BaseData.getUsers());
			users.add(BaseData.getDBUsers());
		userDao.update(users);
	 }
	 
	 /**
	  * Test case for RetrieveById Method DataAcessException
	  * @throws DataAcessException
	  */
	 @Test(expected = DataAcessException.class)
	 public void testRetrieveByIdExcep() throws  DataAcessException {
		when(entityManager.find(Users.class,"A1007483")).thenThrow(new RuntimeException());
		userDao.retrieveById("A1007483");
	 }
	 
	
	 /**
	  * Test case for RetrieveAll DataAcessException
	  * @throws DataAcessException
	  */
	 @Test(expected = DataAcessException.class) 
	 public void testRetrieveAllExcep() throws  DataAcessException{
			Query mockQuery = mock(Query.class);
			when(entityManager.createQuery(Mockito.anyString())).thenReturn(mockQuery);
			when(mockQuery.getResultList()).thenThrow(new RuntimeException());
			userDao.retrieveAll();
	 }
	 
	 /**
	  * Test case for Delete Method DataAcessException
	  * @throws DataAcessException
	  */
	 @Test(expected = DataAcessException.class)
	 public void testDeleteExcep() throws DataAcessException {
		Users user = BaseData.getUsers();
		doThrow(RuntimeException.class).when(entityManager).remove(user);
		userDao.delete(user);
	}
	 
	 /**
	  * Test Case for Delete Method DataAcessException
	  * @throws DataAcessException
	  */
	 @Test(expected = DataAcessException.class)
	 public void testDeleteEntityExcep() throws  DataAcessException {
			Users user = BaseData.getUsers();
			doThrow(RuntimeException.class).when(entityManager).remove(entityManager.getReference(Users.class,
	 	    		user.getLoginName()));
			userDao.delete(user.getLoginName(),user);
	}
	 
	@Test
	public void testUserEntity(){
		Users user1 = BaseData.getUsers();
		Users user2 = new Users();
		user2.setEmail(user1.getEmail());
		user2.setFirstName(user1.getFirstName());
		user2.setLastName(user1.getLastName());
		user2.setLoginName(user1.getLoginName());
		user2.setPassword(user1.getPassword());
		assertNotNull(user2.toString());
		assertTrue(user1.equals(user2));
		//Equal Objects
		user1 = user2;
		assertTrue(user1.equals(user2));
		assertFalse(user1.hashCode() != user2.hashCode() );
		//objct with null
		assertFalse(user2.equals(null));
		assertFalse( user2.hashCode() != user1.hashCode() );
		
		//two empty objects
		user1 = new Users();
		user2 = new Users();
		assertTrue(user2.equals(user1));
		assertFalse(user2.hashCode() != user1.hashCode() );
		
	}
}
