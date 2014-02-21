/**
 * 
 */
package com.bestbuy.search.merchandising.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.BaseDAO;
import com.bestbuy.search.merchandising.dao.UsersDAO;
import com.bestbuy.search.merchandising.domain.Users;
import com.bestbuy.search.merchandising.service.UsersService;
import com.bestbuy.search.merchandising.unittest.common.BaseData;

/**
 *  Unit Test class for UserService
 *  @author Lakshmi Valluripalli :  04 AUG 2012	
 */
public class UsersServiceTest {
	private final static Logger logger = Logger.getLogger(UsersServiceTest.class);
	
	UsersDAO usersDAO;
	UsersService usersService;
	
	/**
	 * Before test case to create the mocks
	 * Creating the Service Layer and its dependent Mocks
	 */
	 @Before
	 public void init() {
		 	usersService = new UsersService();
		 	usersDAO = mock(UsersDAO.class);
		 	BaseDAO basedao = mock(BaseDAO.class);
		 	usersService.setBaseDAO(basedao);
			usersService.setDao(usersDAO);
			
			
	 }
	 
	 /**
	  * Clear all the Assigned Variables
	  */
	 @After
	 public void Destroy(){
		 usersService = null;
		 usersDAO = null;
		 
	 }
	 
	 /**
	  * TestCase for saveMethod
	  */
	 @Test
	 public void testSave(){
		 try{
			 	Users user = BaseData.getUsers();
				when(usersDAO.save(user)).thenReturn(user);
				Users savedUser = usersService.save(user);
				assertNotNull(savedUser);
				assertTrue(savedUser.getFirstName().equals("MerchandisingUI"));
		 }catch(DataAcessException da){
			 logger.error("error at data layer while testing save:.",da);
		 }catch(ServiceException se){
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
			 	        UsersDAO mock = (UsersDAO) invocation.getMock();
			 	        return null;
			 	     }
			 	    }).when(usersDAO).save(users);
			 	usersService.save(users);
		 }catch(DataAcessException da){
			 logger.error("error at data layer while testing save collection:.",da);
		 }catch(ServiceException se){
			 logger.error("error at service layer while testing save collection:.",se);
		 }
	 }
	 
	 /**
	  * Test case for update Method
	  */
	 @Test
	 public void testUpdate(){
		 try{
			    Users user = BaseData.getUsers();
				when(usersDAO.update(user)).thenReturn(BaseData.getDBUsers());
				Users savedUser = usersService.update(user);
				assertNotNull(savedUser);
				assertTrue(savedUser.getFirstName().equals("MerchandisingUI"));
		 }catch(DataAcessException da){
			 logger.error("error at data layer while testing update :.",da);
		 }catch(ServiceException se){
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
			 	        UsersDAO mock = (UsersDAO) invocation.getMock();
			 	        return null;
			 	     }
			 	    }).when(usersDAO).update(users);
			 	usersService.update(users);
		 }catch(DataAcessException da){
			 logger.error("error at data layer while testing update collection:.",da);
		 }catch(ServiceException se){
			 logger.error("error at service layer while testing update collection:.",se);
		 }
	 }
	 
	 /**
	  * Test case for RetrieveById Method
	  */
	 @Test
	 public void testRetrieveById(){
		 try{
			    Users user = BaseData.getUsers();
				when(usersDAO.retrieveById("a1234567")).thenReturn(user);
				Users savedUser = usersService.retrieveById("a1234567");
				assertNotNull(savedUser);
				assertTrue(savedUser.getFirstName().equals("MerchandisingUI"));
		 }catch(DataAcessException da){
			 logger.error("error at data layer while testing retrieveById :.",da);
		 }catch(ServiceException se){
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
				when(usersDAO.retrieveAll()).thenReturn(users);
				List<Users> loadedUsers = (List<Users>)usersService.retrieveAll();
				assertNotNull(loadedUsers);
				assertTrue(loadedUsers.size() > 1);
		 }catch(DataAcessException da){
			 logger.error("error at data layer while testing retrieveAll :.",da);
		 }catch(ServiceException se){
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
				when(usersDAO.delete(user)).thenReturn(null);
				usersService.delete(user);
		 }catch(DataAcessException da){
			 logger.error("error at data layer while testing retrieveAll :.",da);
		 }catch(ServiceException se){
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
			 	        UsersDAO mock = (UsersDAO) invocation.getMock();
			 	         return null;
			 	      }
			 	    }).when(usersDAO).delete(user.getLoginName(),user);
			    usersService.delete(user.getLoginName(),user);
		 }catch(DataAcessException da){
			 logger.error("error at data layer while testing delete:.",da);
		 }catch(ServiceException se){
			 logger.error("error at service layer while testing delete:.",se);
		 }
	 }
	 
	 /**
	  * TestCase for saveMethod ServiceException
	  * @Throws ServiceException
	  * @Throws DataAcessException
	  */
	 @Test(expected = ServiceException.class)
	 public void testSaveExcep() throws ServiceException,DataAcessException{
	 	Users user = BaseData.getUsers();
		when(usersDAO.save(user)).thenThrow(new DataAcessException());
		usersService.save(user);
	 }
	 
	 /**
	  * TestCase for saveAll Method ServiceException
	  * @Throws ServiceException
	  * @Throws DataAcessException
	  */
	 @Test(expected = ServiceException.class)
	 public void testSaveAllExcep() throws ServiceException,DataAcessException{
		 List<Users> users = new ArrayList<Users>();
		 users.add( BaseData.getUsers());
		 users.add(BaseData.getDBUsers());
		 doThrow(DataAcessException.class).when(usersDAO).save(users);
		 usersService.save(users);
	 }
	 
	 /**
	  * Test case for update Method ServiceException
	  * @Throws ServiceException
	  * @Throws DataAcessException
	  */
	 @Test(expected = ServiceException.class)
	 public void testUpdateExcep() throws ServiceException,DataAcessException{
		Users user = BaseData.getUsers();
		when(usersDAO.update(user)).thenThrow(new DataAcessException());
		usersService.update(user);
	 }
	 
	 /**
	  * TestCase for updateAll Method ServiceException
	  * @Throws ServiceException
	  * @Throws DataAcessException
	  */
	 @Test(expected = ServiceException.class)
	 public void testUpdateAllExcep() throws ServiceException,DataAcessException{
		 List<Users> users = new ArrayList<Users>();
		 users.add( BaseData.getUsers());
		 users.add(BaseData.getDBUsers());
		 doThrow(DataAcessException.class).when(usersDAO).update(users);
		 usersService.update(users);
	 }
	 
	 /**
	  * Test case for RetrieveById Method ServiceException
	  * @Throws ServiceException
	  * @Throws DataAcessException
	  */
	 @Test(expected = ServiceException.class)
	 public void testRetrieveByIdExcep() throws ServiceException,DataAcessException {
		when(usersDAO.retrieveById("A1007483")).thenThrow(new DataAcessException());
		usersService.retrieveById("A1007483");
	 }
	 
	 /**
	  * Test case for RetrieveAll ServiceException
	  * @Throws ServiceException
	  * @Throws DataAcessException
	  */
	 @Test(expected = ServiceException.class)
	 public void testRetrieveAllExcep() throws ServiceException,DataAcessException {
		when(usersDAO.retrieveAll()).thenThrow(new DataAcessException());
		usersService.retrieveAll();
	 }
	 
	 /**
	  * Test case for Delete Method ServiceException
	  * @Throws ServiceException
	  * @Throws DataAcessException
	  */
	 @Test(expected = ServiceException.class)
	 public void testDeleteExcep() throws ServiceException,DataAcessException {
		Users user = BaseData.getUsers();
		when(usersDAO.delete(user)).thenThrow(new DataAcessException());
		usersService.delete(user);
		
	 }
	 
	 /**
	  * Test case for Delete Entity Method ServiceException
	  * @Throws ServiceException
	  * @Throws DataAcessException
	  */
	 @Test(expected = ServiceException.class)
	 public void testDeleteEntityExcep() throws ServiceException,DataAcessException {
		Users user = BaseData.getUsers();
		when(usersDAO.delete(user.getLoginName(),user)).thenThrow(new DataAcessException());
		usersService.delete(user.getLoginName(),user);
	}
}
