/**
 * 
 */
package com.bestbuy.search.merchandising.category;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXParseException;

import com.bestbuy.search.merchandising.unittest.common.BaseData;

/**
 * @author Lakshmi.Valluripalli
 * Junit Layer to test the Parsing of CategoryTree
 */
public class CategoryParserTest {
	
	private final static Logger logger = Logger.getLogger(CategoryParserTest.class);
	CategoryParser parser;
	
	@Before
	public void setUp() {
		parser = new CategoryParser();
	}
	 
	
	/**
	 *Junit Method for testing category tree parse
	 */
	@Test
	public void testParseCategoryTree(){
		try{
			String categoryTree = BaseData.getCategoryTree();
			String parsedXml = parser.parseCategoryTree("/META-INF/stylesheets/categoryTree.xsl", categoryTree);
			assertNotNull(parsedXml);
		}catch(Exception e){
			logger.error("Error while running junit for parsing CategoryTree");
		}
	}
	
	/**
	 *Junit Method for testing parsing of category Nodes
	 */
	@Test
	public void testParseCategoryNodes(){
		try{
			String categoryTree = BaseData.getCategoryTree();
			Categories categories = parser.parseCategoryNodes("/META-INF/stylesheets/categoryNodes.xsl", categoryTree);
			assertNotNull(categories);
			assertNotNull(categories.getNodes());
			assertTrue(categories.getNodes().size() > 0);
		}catch(Exception e){
			logger.error("Error while running junit for parsing CategoryTree");
		}
	}
	
	/**
	 * Junit Method to test the Exception Handling when the xml is wrong
	 * @throws Exception
	 */
	@Test(expected = SAXParseException.class)
	public void testParseInvalidTree() throws Exception{
		String categoryTree = BaseData.getCategoryTree()+"</category>";
		parser.parseCategoryTree("/META-INF/stylesheets/categoryTree.xsl", categoryTree);
	}
}
