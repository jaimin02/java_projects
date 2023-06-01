package com.docmgmt.struts.actions.labelandpublish.ManualMode;

import java.io.IOException;
import java.io.StringReader;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.docmgmt.dto.DTOxmlParseDetails;

public class XmlParsingForReferenceNodeId {
	Document dom;

	public Vector<DTOxmlParseDetails> parseXmlFile(String filetoParse,
			String nodetoFind) {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {

			
			dbf.setValidating(false);
//			dbf.setNamespaceAware(true);
//			dbf.setFeature("http://xml.org/sax/features/namespaces", false);
//			dbf.setFeature("http://xml.org/sax/features/validation", false);
//			dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
//			dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
//			
//		
			
			DocumentBuilder db = dbf.newDocumentBuilder();

			db.setEntityResolver(new EntityResolver() {
		        public InputSource resolveEntity(String publicId, String systemId)
		                throws SAXException, IOException {
		            if (systemId.contains(".dtd")) {
		                return new InputSource(new StringReader(""));
		            } else {
		                return null;
		            }
		        }
		    });
			dom = db.parse(filetoParse);

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			System.gc();
		}

		return parseDocument(nodetoFind);

	}

	private Vector<DTOxmlParseDetails> parseDocument(String nodetoFind) {
		// get the root elememt

		Vector<DTOxmlParseDetails> xmlNodeDetails = new Vector<DTOxmlParseDetails>();

		Element docEle = dom.getDocumentElement();

		// search The Node and Get Nodeid--Used For Reference in replacing Node

		NodeList nl = docEle.getElementsByTagName(nodetoFind);
		System.out.println(" Total=" + nl.getLength());
		
		
		if (nl.getLength() > 0) {
			
			for(int i=0;i<nl.getLength();i++){

				Element el = (Element) nl.item(i);

				NodeList n2 = el.getElementsByTagName("leaf");
				for (int j = 0; j < n2.getLength(); j++) {

					DTOxmlParseDetails ndtl = new DTOxmlParseDetails();
					Element e2 = (Element) n2.item(j);
					if (!e2.getAttribute("xlink:href").equals(
							"10-cover/common/common-cover-tracking.xml")
							&& !e2.getAttribute("xlink:href").equals(
									"10-cover/common/common-cover-tracking.pdf")) {
						ndtl.setReferenceNodeId(e2.getAttribute("ID"));
						ndtl.setHref(e2.getAttribute("xlink:href"));
						// System.out.println("Leaf Id="+e2.getAttribute("ID"));
						xmlNodeDetails.addElement(ndtl);
					}

				}
			}
		
		}
		
		return xmlNodeDetails;

	}
}
