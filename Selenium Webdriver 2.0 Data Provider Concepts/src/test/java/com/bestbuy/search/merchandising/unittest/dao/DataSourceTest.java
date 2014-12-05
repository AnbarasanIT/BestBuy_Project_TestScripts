package com.bestbuy.search.merchandising.unittest.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.bestbuy.search.merchandising.authentication.exception.AuthenticationException;
import com.bestbuy.search.merchandising.common.DataSource;

/**
 * @author Chanchal.KUmari
*/

public class DataSourceTest {
	DataSource dataSource;
	
	@Before
	public void init() {
		dataSource = new DataSource() ;
		dataSource.setUsername("testUserName");
	}
	
	@Rule
	public TemporaryFolder myfolder = new TemporaryFolder();
	
	
	@Test(expected = AuthenticationException.class)
	public void testSetPasswordFile_BlankPath() throws AuthenticationException{
		dataSource.setPasswordFile(null);
	}
	
	@Test(expected = AuthenticationException.class)
	public void testSetPasswordFile_BlankUserName() throws AuthenticationException{
		dataSource.setUsername(null);
		String filePath = myfolder.getRoot().getPath() +"/.dbpass" ;
		dataSource.setPasswordFile(filePath);
	}
	
	@Test(expected = AuthenticationException.class)
	public void testSetPasswordFile_FileNotFoundException() throws AuthenticationException{
		String filePath = myfolder.getRoot().getPath() +"/.dbpass" ;
		dataSource.setPasswordFile(filePath);
	}
	
	@Test(expected = AuthenticationException.class)
	public void testSetPasswordFile_EmptyPassWdFile() throws AuthenticationException,IOException {
		String filePath = myfolder.getRoot().getPath() +"/.dbpass" ;
		myfolder.newFile(".dbpass");
		dataSource.setPasswordFile(filePath);
		assertNull(dataSource.getPassword());
	}
	
	@Test
	public void testSetPasswordFile_StartsWithUserName() throws AuthenticationException,IOException,FileNotFoundException{
		String filePath = myfolder.getRoot().getPath() +"/.dbpass" ;
		File file  = myfolder.newFile(".dbpass");
		PrintWriter out = new PrintWriter(file);
	    out.println("testUserNamePCM=TestPasswd");
	    out.flush();
	    out.close();
		dataSource.setPasswordFile(filePath);
		assertNotNull(dataSource.getPassword());
		assertTrue(dataSource.getPassword().equals("TestPasswd"));
	}
	
	@Test(expected = AuthenticationException.class)
	public void testSetPasswordFile_DoesNotStartsWithUserName() throws AuthenticationException,IOException{
		String filePath = myfolder.getRoot().getPath() +"/.dbpass" ;
		File file  = myfolder.newFile(".dbpass");
		PrintWriter out = new PrintWriter(file);
	    out.println("notTestUserNamePCM=TestPasswd");
	    out.flush();
	    out.close();
		dataSource.setPasswordFile(filePath);
		assertNull(dataSource.getPassword());
	}
	
	@Test(expected = AuthenticationException.class)
	public void testSetPasswordFile_IOExcepetion() throws AuthenticationException, IOException {
		String filePath = myfolder.getRoot().getPath() +"/.dbpass" ;
		File file  = myfolder.newFile(".dbpass");
		file.setReadable(false);
		dataSource.setPasswordFile(filePath);
	}
}
