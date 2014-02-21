package com.bestbuy.search.bt.uiconstants;

import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.bestbuy.search.bt.selenium.util.MagicNumbers;

/**
 * Class to modify Date & Time
 * 
 */

public class ModifyDateTime {
	private Log log = LogFactory.getLog("ModifyDateTime");
	private String startMonth;
	private String endMonth;
	private String startDateIdentifier;
	private String endDateIdentifier;
	private String startDay;
	private String startYear;
	private String startHour;
	private String startMinute;
	private String endDay;
	private String endYear;
	private String endHour;
	private String endMinute;

	private static final String START_DATE_MONTH = "Start_Date_Month";
	private static final String START_DATE_DAY = "Start_Date_Day";
	private static final String START_DATE_YEAR = "Start_Date_Year";
	private static final String START_DATE_HOUR = "Start_Date_Hour";
	private static final String START_DATE_MINUTE = "Start_Date_Minute";
	private static final String START_DATE_IDENTIFIER = "Start_Date_Link";
	private static final String END_DATE_MONTH = "End_Date_Month";
	private static final String END_DATE_DAY = "End_Date_Day";
	private static final String END_DATE_YEAR = "End_Date_Year";
	private static final String END_DATE_HOUR = "End_Date_Hour";
	private static final String END_DATE_MINUTE = "End_Date_Minute";
	private static final String END_DATE_IDENTIFIER = "End_Date_Link";

	/**
	 * Method to update Date & Time in the xml file
	 * 
	 * @param filelocation
	 *            Location of the xml file
	 * 
	 * @param rootTag
	 *            Denotes the root tag of the xml
	 * 
	 * @param startDateTag
	 *            Denotes the Start date tag of the xml
	 * 
	 * @param endDateTag
	 *            Denotes the End date tag of the xml
	 */
	public void startDateSplitter(String dateTime) {
		String[] tokens = dateTime.split("-");
		startMonth = tokens[0];
		startDay = tokens[1];
		startYear = tokens[2];
		startHour = tokens[3];
		startMinute = tokens[4];
		startDateIdentifier = "//a[text()='" + startDay + "']";

	}

	public void endDateSplitter(String dateTime) {
		String[] tokens = dateTime.split("-");
		endMonth = tokens[0];
		endDay = tokens[1];
		endYear = tokens[2];
		endHour = tokens[3];
		endMinute = tokens[4];
		endDateIdentifier = "//a[text()='" + endDay + "']";
	}

	public void updateDateAndTime(String filelocation, String rootTag) {

		try {

			Calendar date = Calendar.getInstance();
			Format f = new SimpleDateFormat("MMM-d-yyyy-HH-mm");
			date.add(Calendar.MINUTE, 3);
			String startdate = f.format(date.getTime());
			startDateSplitter(startdate);
			date.add(Calendar.DATE, MagicNumbers.TEN_DAYS);
			String enddate = f.format(date.getTime());
			endDateSplitter(enddate);
			File file=new File(filelocation);
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filelocation);
			Node staff = doc.getElementsByTagName(rootTag).item(0);
			NodeList list = staff.getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {

				Node node = list.item(i);
				/**
				 * update values in xml for startdate
				 */
				if (START_DATE_MONTH.equals(node.getNodeName())) {
					node.setTextContent(startMonth);
				}
				if (START_DATE_DAY.equals(node.getNodeName())) {
					node.setTextContent(startDay);
				}
				if (START_DATE_YEAR.equals(node.getNodeName())) {
					node.setTextContent(startYear);
				}
				if (START_DATE_HOUR.equals(node.getNodeName())) {
					node.setTextContent(startHour);
				}
				if (START_DATE_MINUTE.equals(node.getNodeName())) {
					node.setTextContent(startMinute);
				}
				/**
				 * update values in xml for Enddate
				 */
				if (END_DATE_MONTH.equals(node.getNodeName())) {
					node.setTextContent(endMonth);
				}
				if (END_DATE_DAY.equals(node.getNodeName())) {
					node.setTextContent(endDay);
				}
				if (END_DATE_YEAR.equals(node.getNodeName())) {
					node.setTextContent(endYear);
				}
				if (END_DATE_HOUR.equals(node.getNodeName())) {
					node.setTextContent(endHour);
				}
				if (END_DATE_MINUTE.equals(node.getNodeName())) {
					node.setTextContent(endMinute);
				}
				/**
				 * update values in xml for userIdentifiesrs
				 */
				if (START_DATE_IDENTIFIER.equals(node.getNodeName())) {
					node.setTextContent(startDateIdentifier);
				}
				if (END_DATE_IDENTIFIER.equals(node.getNodeName())) {
					node.setTextContent(endDateIdentifier);
				}

			}
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(file.toURI().getPath());
			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			log.info("TransformerConfigurationException Exception in updateDateAndTime"
					+ e.getMessage());
		} catch (ParserConfigurationException e) {
			log.info("ParserConfigurationException Exception in updateDateAndTime"
					+ e.getMessage());
		} catch (SAXException e) {
			log.info("SAXException Exception in updateDateAndTime"
					+ e.getMessage());
		} catch (IOException e) {
			log.info("IOException Exception in updateDateAndTime"
					+ e.getMessage());
		} catch (TransformerFactoryConfigurationError e) {
			log.info("TransformerFactoryConfigurationError Exception in updateDateAndTime"
					+ e.getMessage());
		} catch (TransformerException e) {
			log.info("TransformerException Exception in updateDateAndTime"
					+ e.getMessage());
		}

	}
}
