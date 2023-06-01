package com.docmgmt.test;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParsingNew {

	Document dom;

	public XMLParsingNew() {
		// create a list to hold the employee objects

	}

	public void runExample() {

		parseXmlFile();

		parseDocument();

	}

	private void parseXmlFile() {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {

			DocumentBuilder db = dbf.newDocumentBuilder();

			dom = db.parse("D://dtest/m1/eu/eu-regional.xml");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			System.gc();
		}
	}

	private void parseDocument() {
		// get the root elememt
		Element docEle = dom.getDocumentElement();

		// search The Node and Get Nodeid--Used For Reference in replacing Node
		NodeList nl = docEle.getElementsByTagName("m1-3-pi");
		System.out.println("Cover Total=" + nl.getLength());
		Element el = (Element) nl.item(0);
	
		NodeList n2 = el.getElementsByTagName("leaf");
		for(int j=0;j<n2.getLength();j++){
			
			Element e2 = (Element) n2.item(j);
			System.out.println("Leaf Id="+e2.getAttribute("ID"));
			
			NodeList n3 = e2.getElementsByTagName("title");
			
			System.out.println("N3="+n3.item(0).getTextContent());
		}
	}

	public static void main(String[] args) {
		// create an instance
		XMLParsingNew dpe = new XMLParsingNew();
		// call run example
		dpe.runExample();
	}

}
