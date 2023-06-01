package com.docmgmt.test.parsing;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

class Workflow {
	private String serviceType;
	private final String WORKFLOW_FILE_PATH = "D://vijay/Learning/xml files/workflow.xml";
	private final String TAG_TO_PARSE = "service-type";
	private ArrayList<String> discoverableWorkflow;
	private ArrayList<String> nonDiscoverableWorkflow;
	public void parseWorkflow() {
		try {
			
			XmlParsing xmlParsing = new XmlParsing();
			Document dom = xmlParsing.getDOMObject(WORKFLOW_FILE_PATH);
			Element docElement = dom.getDocumentElement();
			NodeList allServiceType = docElement
					.getElementsByTagName(TAG_TO_PARSE);
			if (allServiceType != null && allServiceType.getLength() > 0) {
				RestRegistory repository = new RestRegistory();
				discoverableWorkflow = new ArrayList<String>();
				nonDiscoverableWorkflow = new ArrayList<String>();
				for (int i = 0; i < allServiceType.getLength(); i++) {
					serviceType = allServiceType.item(i).getTextContent()
							.trim();
					if (repository.isServiceTypeExist(serviceType, false))
						discoverableWorkflow.add(serviceType);
					else
						nonDiscoverableWorkflow.add(serviceType);

				}
				System.out.println("Discoverable Workflow:");
				for (String s : discoverableWorkflow)
					System.out.println("\t" + s);
				System.out.println("Non Discoverable Workflow:");
				for (String s : nonDiscoverableWorkflow)
					System.out.println("\t" + s);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Planning

		Planning plan = new Planning();
		plan.processPlanning();

	}
}
