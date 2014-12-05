package com.bestbuy.search.bt.uiconstants;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.bestbuy.search.bt.selenium.util.ScreenException;

/**
 * Class that loads the xml files
 */

public class ReadXMLFile {

	private static Element eElement;
	private Log log = LogFactory.getLog(getClass());
	private ModifyDateTime modifydatetime;
	private static final String MERCHUI = "./src/main/resources/MerchandisingUIIdentifiers.xml";
	private static final String MERCHUSERINFOCONSTANT = "./src/main/resources/MerchandisingUI_UserInfo.xml";
	private static final String MERCHENV = "./src/main/resources/env-config.xml";
	private static final String MERCHDATA = "./src/main/resources/MerchandisingUIData.xml";
	private static final String USERINFO = "userInfo";
	private static final String UIIDENTIFIERS = "uiidentifiers";
	private static final String ENVCONFIGURATION = "environment";
	private static final String BTUIDATA = "btuidata";

	public ReadXMLFile() throws ScreenException {
		log.info("Loading Data from XML");
	modifydatetime = new ModifyDateTime();
	}
	
	/**
	 * Implementation of the Method to load the data from xml's
	 * 
	 * @param properties
	 *            properties is the variable that stores the xml file
	 * 
	 * @param tagname
	 *            tagname is the name of the root tag read from the xml
	 * 
	 * @throws ScreenException
	 *             ScreenException is an exception to suppress warnings
	 */
	
	public void loadBTConstansts(String properties,String tagname) throws ScreenException {

		try {
			File fXmlFile = new File(properties);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			 dbFactory.setNamespaceAware(true);
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			NodeList nList = doc.getElementsByTagName(tagname);

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					eElement = (Element) nNode;

				}
			}
		} catch (Exception e) {
			log.info("Exception in loadBTConstansts"+e.getMessage());
		}
	}

	public void loadMerchandisingUIUserInfoConstants() throws ScreenException {
		loadBTConstansts(MERCHUSERINFOCONSTANT,USERINFO);
	}

	public void loadMerchandisingUIEnvConstansts() throws ScreenException {
		loadBTConstansts(MERCHENV,ENVCONFIGURATION);
	}

	public void loadMerchandisingUIConstants() throws ScreenException {
		modifydatetime.updateDateAndTime(MERCHUI,UIIDENTIFIERS);
		loadBTConstansts(MERCHUI,UIIDENTIFIERS);
	}

	public void loadMerchandisingUIData() throws ScreenException {
		loadBTConstansts(MERCHDATA,BTUIDATA);
	}

	/**
	 * Method to compare and get the value for each tag in the xml
	 * 
	 * @param elementName
	 *            Param to hold the value of the element
	 * 
	 * @return returns the value of the element
	 */
	public String getValue(String elementName) {

		NodeList nlList = eElement.getElementsByTagName(elementName).item(0)
				.getChildNodes();
		Node nValue = (Node) nlList.item(0);
		if (nValue == null) {
			return null;
		}

		return nValue.getNodeValue();
	}

}
