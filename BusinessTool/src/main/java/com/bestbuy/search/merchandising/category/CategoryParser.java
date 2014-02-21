
package com.bestbuy.search.merchandising.category;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.xml.sax.InputSource;

import com.bestbuy.search.merchandising.common.BTLogger;
import com.bestbuy.search.merchandising.common.ErrorType;

/**
 * Category Parser class that parses and also validates the
 * Category Path Updates received from Daas
 * @author Lakshmi.Valluripalli
 */
public class CategoryParser {
	
  private final static BTLogger log = BTLogger.getBTLogger(CategoryParser.class.getName());
  
	
	/**
	 * Method parses the categoryTree to the tree structure used by UI
	 * @param xslPath
	 * @param xml
	 * @return
	 */
	public String parseCategoryTree(String xslPath,String xml) throws Exception{
		String parsedXml = parseTree(xslPath,xml);
		return parsedXml;
	}
	
	/**
	 * Method to unmarshall the received category tree to paths
	 * @param xslPath
	 * @param xml
	 * @return
	 */
	public Categories parseCategoryNodes(String xslPath,String xml) throws Exception{
		InputStream xmlStream = null;
		Categories categories = null;
		try{
			String parsedXml = parseTree(xslPath, xml);
			if(parsedXml != null){
				xmlStream = new ByteArrayInputStream(parsedXml.getBytes("UTF-8"));
			}
			//Unmarshalling the xml content to Category
			JAXBContext jaxBContext = JAXBContext.newInstance(Categories.class);
			Unmarshaller unmarshaller = jaxBContext.createUnmarshaller();
			if(xmlStream != null){
				categories = (Categories)unmarshaller.unmarshal(xmlStream);
			}
		}
		catch(Exception e){
			log.error("CategoryParser.parseCategoryNodes", e , ErrorType.CATEGORY_TREE, "Error while parsing categoryNodes:..");
			throw e;
		}
		finally{
			if(xmlStream != null){
				try{
					xmlStream.close();
				}catch(IOException io){
					log.error("CategoryParser.parseCategoryNodes", io , ErrorType.CATEGORY_TREE, "Error while closing xmlStream - ");
				}
			}
		}
		return categories;
	}
	
	/**
	 * Method to parse the XML recived to the other format using style sheets 
	 * @param xslPath
	 * @param xml
	 * @return
	 */
	private String parseTree(String xslPath,String xml) throws Exception{
		InputStream xslStream = null;
		String parsedXml = null;
		try{
			xslStream = this.getClass().getResourceAsStream(xslPath);
			StreamSource xsltSource = new StreamSource(xslStream);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			org.w3c.dom.Document document = builder.parse(new InputSource(new StringReader(xml)));    
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer(xsltSource);
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.MEDIA_TYPE, "text/xml");
			DOMSource source = new DOMSource(document);
			StreamResult result =  new StreamResult(new StringWriter());
			transformer.transform(source, result);
			parsedXml =  result.getWriter().toString();
		}
		catch(Exception e){
			log.error("CategoryParser.parseTree", e , ErrorType.CATEGORY_TREE, "Error Parser category tree");
			throw e;
		}
		finally{
			if(xslStream != null){
				try{
					xslStream.close();
				}catch(IOException io){
					log.error("CategoryParser.parseTree", io , ErrorType.CATEGORY_TREE, "Error while closing xslStream");
					throw io;
				}
			}
		}
		return parsedXml;
	}
}
