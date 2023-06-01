package com.docmgmt.test.parsing;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

class Planning {
	ArrayList<String> planningFiles;
	private final String TAG_TO_PARSE = "type";

	public Planning() {
		planningFiles = new ArrayList<String>();
		planningFiles.add("D://vijay/Learning/xml files/palnning0.xml");
		planningFiles.add("D://vijay/Learning/xml files/palnning1.xml");
		planningFiles.add("D://vijay/Learning/xml files/palnning2.xml");
	}

	public void processPlanning() {
		RestRegistory repository = new RestRegistory();
		int planCounter = 0;
		for (String planFile : planningFiles) {
			planCounter++;
			XmlParsing xmlParsing = new XmlParsing();
			Document dom = xmlParsing.getDOMObject(planFile);
			Element docElement = dom.getDocumentElement();
			NodeList allServiceType = docElement
					.getElementsByTagName(TAG_TO_PARSE);
			System.out.println("----------------------Planning->" + planCounter
					+ " -----------------------------\n");

			displayPlanning(allServiceType);
			System.out.println(" ");

			for (int i = 0; i < allServiceType.getLength(); i++) {
				String service = allServiceType.item(i).getTextContent().trim();
				if (repository.isServiceTypeExist(service, true)) {
					System.out.println("\n");
				}
				// System.out.println("\t"+allServiceType.item(i).getTextContent().trim());
			}
		}
	}

	public void displayPlanning(NodeList allServiceType) {
		for (int i = 0; i < allServiceType.getLength(); i++) {
			System.out.print("\t"
					+ allServiceType.item(i).getAttributes().getNamedItem(
							"attribute").getNodeValue());
			System.out.println("\t"
					+ allServiceType.item(i).getTextContent().trim());

		}

	}

}
