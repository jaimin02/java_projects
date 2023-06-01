package com.docmgmt.server.webinterface.beans;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.TreeMap;
import java.util.Vector;

import com.docmgmt.dto.DTOWorkSpaceNodeAttribute;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
public class GenerateDTDBean {

	private Object docMgmtLog;
	private int userCode;
	private Vector nodeInfoVector;
	private Vector nodeDetail;
	private Vector nodeAttr;
	private String userType;
	private String dtdPath;

	public GenerateDTDBean() {
		nodeInfoVector = new Vector();
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		dtdPath = propertyInfo.getValue("DTDPath");
	}

	DocMgmtImpl docMgmt = new DocMgmtImpl();

	public void getWorkspaceTreeHtml(String workspaceID, int userGroupCode,
			int userCode, String stype) {
		try {

			// if(stype.toUpperCase().trim().equals("US") ||
			// stype.toUpperCase().trim().equals("EU"))
			// {
			// System.out.println("-------------------------- INSIDE DTD CREATION ------------------");
			// System.out.println("-------------------------- INSIDE DTD CREATION ------------------");
			// System.out.println("-------------------------- INSIDE DTD CREATION ------------------");
			// System.out.println("-------------------------- INSIDE DTD CREATION ------------------");
			// System.out.println("-------------------------- INSIDE DTD CREATION ------------------");
			System.out
					.println("-------------------------- INSIDE DTD CREATION ------------------");

			BufferedWriter outm2m5 = new BufferedWriter(new FileWriter(dtdPath
					+ "/" + stype + "m2m5.dtd"));
			BufferedWriter outm1 = new BufferedWriter(new FileWriter(dtdPath
					+ "/" + stype + "m1.dtd"));
			// System.out.println("INSIDE DTD FILE PATH : " +
			// dtdPath+"\\"+stype+"m2m5.dtd");
			System.out.println("INSIDE DTD FILE PATH : " + dtdPath + "/"
					+ stype + "m1.dtd");

			setFinxdContentForM1toM5(stype, outm1, outm2m5);

			Vector workspaceTreeVector = getTreeNodes(workspaceID,
					userGroupCode, userCode);

			if (workspaceTreeVector.size() > 0) {
				TreeMap childNodeHS = new TreeMap();
				Hashtable nodeDtlHS = new Hashtable();
				Integer firstNode = null;

				for (int i = 0; i < workspaceTreeVector.size(); i++) {
					Object[] nodeRec = (Object[]) workspaceTreeVector.get(i);
					Integer parentNodeId = (Integer) nodeRec[2];
					Integer currentNodeId = (Integer) nodeRec[0];
					if (i == 0) {
						firstNode = currentNodeId;
					}
					if (childNodeHS.containsKey(parentNodeId)) {

						List childList = (List) childNodeHS.get(parentNodeId);
						childList.add(currentNodeId);
						if (!childNodeHS.containsKey(currentNodeId)) {
							childNodeHS.put(currentNodeId, new ArrayList());
						}
					} else {
						List childList = new ArrayList();
						childList.add(currentNodeId);
						childNodeHS.put(parentNodeId, childList);
						if (!childNodeHS.containsKey(currentNodeId)) {
							childNodeHS.put(currentNodeId, new ArrayList());
						}
					}
					nodeDtlHS.put(currentNodeId, new Object[] { nodeRec[1],
							nodeRec[2], nodeRec[3], nodeRec[4], nodeRec[5],
							nodeRec[6], nodeRec[7], nodeRec[8] });
					// Object[] = Display Name,Parent Id,Folder Name,Can Read
					// Flag,Can Add Flag,Can Edit Flag,Can Delete Flag
				}

				if (firstNode != null) {
					Object[] firstNodeObj = (Object[]) nodeDtlHS.get(firstNode);
					List childList = (List) childNodeHS.get(firstNode);
					for (int i = 0; i < childList.size(); i++) {
						getChildStructure((Integer) childList.get(i),
								childNodeHS, nodeDtlHS, outm2m5, outm1);
					}
				}
			}
			outm1.close();
			outm2m5.close();
			// }
		} catch (Exception e) {
		}
	}

	private void getChildStructure(Integer nodeId, TreeMap childNodeHS,
			Hashtable nodeDtlHS, BufferedWriter outm2m5, BufferedWriter outm1) {
		try {
			List childList = (List) childNodeHS.get(nodeId);
			Object[] nodeDtlObj = (Object[]) nodeDtlHS.get(nodeId);
			String displayName = (String) nodeDtlObj[0];

			if (childList.size() > 0) {
				String attrList = "%att;\n";
				String leafNodes = " (";
				if (!displayName.startsWith("m1"))
					leafNodes = " (leaf*,";

				boolean attrFound = false;

				for (int j = 0; j < nodeDetail.size(); j++) // Find leaf
				// nodes....
				{
					Object[] nodeRec = (Object[]) nodeDetail.get(j);
					Integer parentId = (Integer) nodeRec[2];
					String nDisplay = (String) nodeRec[1];
					Integer currId = (Integer) nodeRec[0];

					if (parentId.intValue() == nodeId.intValue()) {
						leafNodes += nDisplay;

						// Check leaf node has an attributes or not
						// If attributes attached with leaf nodes place * after
						// nodeDisplayName or place ?

						for (int attCount = 0; attCount < nodeAttr.size(); attCount++) {
							DTOWorkSpaceNodeAttribute dto = (DTOWorkSpaceNodeAttribute) nodeAttr
									.get(attCount);
							if (dto.getNodeId() == currId.intValue())
								attrFound = true;
							dto = null;
						}
						if (attrFound)
							leafNodes += "*,";
						else if (!displayName.startsWith("m1")
								&& attrFound == false)
							leafNodes += "?,";
						else if (displayName.startsWith("m1"))
							leafNodes += "*,";

						attrFound = false;
					}
				}

				for (int attCount = 0; attCount < nodeAttr.size(); attCount++) {
					DTOWorkSpaceNodeAttribute dto = (DTOWorkSpaceNodeAttribute) nodeAttr
							.get(attCount);

					if (dto.getNodeId() == nodeId.intValue()) {
						System.out.println(dto.getNodeId() + " "
								+ nodeId.intValue());
						attrList += dto.getAttrName() + " "
								+ " CDATA #REQUIRED \n";
					}
					dto = null;
				}
				leafNodes = leafNodes.substring(0, leafNodes.length() - 1); // remove
				// last
				// ,

				if (!displayName.startsWith("m1")) {
					outm2m5.write("<!ELEMENT " + displayName + leafNodes
							+ ")>\n");
					outm2m5.write("<!ATTLIST " + displayName + "\n" + attrList
							+ ">\n");
				} else if (!displayName.startsWith("m1-administrative")) {
					outm1
							.write("<!ELEMENT " + displayName + leafNodes
									+ ")>\n");
					outm1.write("<!ATTLIST " + displayName + "\n" + attrList
							+ ">\n");
				}

				for (int i = 0; i < childList.size(); i++) {
					getChildStructure((Integer) childList.get(i), childNodeHS,
							nodeDtlHS, outm2m5, outm1);
				}
			} else {
				String attrList = "%att;\n";

				if (!displayName.startsWith("m1"))
					outm2m5.write("<!ELEMENT " + displayName
							+ " ((leaf | node-extension)*)>\n");
				else if (!displayName.startsWith("m1-administrative"))
					outm1.write("<!ELEMENT " + displayName
							+ " ((leaf | node-extension)*)>\n");

				for (int attCount = 0; attCount < nodeAttr.size(); attCount++) {
					DTOWorkSpaceNodeAttribute dto = (DTOWorkSpaceNodeAttribute) nodeAttr
							.get(attCount);
					if (dto.getNodeId() == nodeId.intValue()) {
						System.out.println(dto.getNodeId() + " "
								+ nodeId.intValue());
						attrList += dto.getAttrName() + " "
								+ " CDATA #REQUIRED \n";
					}
					dto = null;
				}
				if (!displayName.startsWith("m1"))
					outm2m5.write("<!ATTLIST " + displayName + "\n" + attrList
							+ ">\n");
				else if (!displayName.startsWith("m1-administrative"))
					outm1.write("<!ATTLIST " + displayName + "\n" + attrList
							+ ">\n");
			}
		} catch (Exception e) {
		}
	}

	public Vector getTreeNodes(String workspaceID, int userGroupCode,
			int userCode) {
		try {
			nodeInfoVector = docMgmt.getNodeDetailForDTD(workspaceID,
					userGroupCode, userCode);

			nodeDetail = new Vector();
			nodeDetail = nodeInfoVector;

			nodeAttr = new Vector();
			nodeAttr = docMgmt.getNodeAttributes(workspaceID, -1);

			return nodeInfoVector;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nodeInfoVector;

	}

	public int getUserCode() {
		return userCode;
	}

	public void setUserCode(int i) {
		userCode = i;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public void writeCommonContentM1(BufferedWriter out) {
		try {
			out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			out
					.write("<!-- ===================== TOP LEVEL ELEMENTS ======================= -->\n");
			out.write("<!ENTITY % att \" ID       ID     #IMPLIED\n");
			out.write("xml:lang CDATA  #IMPLIED\">\n");
			out
					.write("<!ELEMENT fda-regional:fda-regional (admin, m1-regional?)>\n");
			out.write("<!ATTLIST fda-regional:fda-regional\n");
			out
					.write("xmlns:fda-regional CDATA #FIXED \"http://www.ich.org/fda\"\n");
			out
					.write("xmlns:xlink CDATA #FIXED \"http://www.w3c.org/1999/xlink\"\n");
			out.write("xml:lang CDATA #IMPLIED\n");
			out.write("dtd-version CDATA #FIXED \"2.01\"\n");
			out.write(">\n");
			out
					.write("<!-- ===================== LEAF CONTENT ============================= -->\n");
			out.write("<!ELEMENT leaf (title, link-text?)>\n");
			out.write("<!ATTLIST leaf\n");
			out.write("ID ID #REQUIRED\n");
			out.write("application-version CDATA #IMPLIED\n");
			out.write("version CDATA #IMPLIED\n");
			out.write("font-library CDATA #IMPLIED\n");
			out
					.write("operation (new | append | replace | delete) #REQUIRED\n");
			out.write("modified-file CDATA #IMPLIED\n");
			out.write("checksum CDATA #IMPLIED\n");
			out.write("checksum-type CDATA #IMPLIED\n");
			out.write("keywords CDATA #IMPLIED\n");
			out
					.write("xmlns:xlink CDATA #FIXED \"http://www.w3c.org/1999/xlink\"\n");
			out.write("xlink:type CDATA #FIXED \"simple\"\n");
			out.write("xlink:role CDATA #IMPLIED\n");
			out.write("xlink:href CDATA #IMPLIED\n");
			out
					.write("xlink:show (new | replace | embed | other | none) #IMPLIED\n");
			out
					.write("xlink:actuate (onLoad | onRequest | other | none) #IMPLIED\n");
			out.write("xml:lang CDATA #IMPLIED\n");
			out.write(">\n");
			out.write("<!ELEMENT title (#PCDATA)>\n");
			out.write("<!ATTLIST title\n");
			out.write("ID ID #IMPLIED\n");
			out.write(">\n");
			out.write("<!ELEMENT link-text (#PCDATA | xref)*>\n");
			out.write("<!ATTLIST link-text\n");
			out.write("ID ID #IMPLIED\n");
			out.write(">\n");
			out.write("<!ELEMENT xref EMPTY>\n");
			out.write("<!ATTLIST xref\n");
			out.write("ID ID #IMPLIED\n");
			out
					.write("xmlns:xlink CDATA #FIXED \"http://www.w3c.org/1999/xlink\"\n");
			out.write("xlink:type CDATA #FIXED \"simple\"\n");
			out.write("xlink:role CDATA #IMPLIED\n");
			out.write("xlink:title CDATA #REQUIRED\n");
			out.write("xlink:href CDATA #REQUIRED\n");
			out
					.write("xlink:show (new | replace | embed | other | none) #IMPLIED\n");
			out
					.write("xlink:actuate (onLoad | onRequest | other | none) #IMPLIED\n");
			out.write(">\n");
			out
					.write("<!ELEMENT node-extension (title, (leaf | node-extension)+)>\n");
			out.write("<!ATTLIST node-extension\n");
			out.write("ID ID #IMPLIED\n");
			out.write("xml:lang CDATA #IMPLIED\n");
			out.write(">\n");
			out
					.write("<!-- ===================== ADMIN ==================================== -->\n");
			out
					.write("<!ELEMENT admin (applicant-info, product-description, application-information)>\n");
			out
					.write("<!-- ********************* Applicant Information ******************** -->\n");
			out
					.write("<!ELEMENT applicant-info (company-name, date-of-submission)>\n");
			out.write("<!ELEMENT company-name (#PCDATA)>\n");
			out.write("<!ELEMENT date-of-submission (date)>\n");
			out.write("<!ELEMENT date (#PCDATA)>\n");
			out.write("<!ATTLIST date\n");
			out.write("format (yyyymmdd) #REQUIRED\n");
			out.write(">\n");
			out
					.write("<!-- ********************* Product Description ********************** -->\n");
			out
					.write("<!ELEMENT product-description (application-number, prod-name+)>\n");
			out.write("<!ELEMENT application-number (#PCDATA)>\n");
			out.write("<!ELEMENT prod-name (#PCDATA)>\n");
			out.write("<!ATTLIST prod-name\n");
			out
					.write("type (established | proprietary | chemical | code) #REQUIRED\n");
			out.write(">\n");
			out
					.write("<!-- ********************* Application Information ****************** -->\n");
			out.write("<!ELEMENT application-information (submission)>\n");
			out.write("<!ATTLIST application-information\n");
			out
					.write("application-type (nda | anda | bla | dmf | ind | master-file) #REQUIRED\n");
			out.write(">\n");
			out
					.write("<!ELEMENT submission (sequence-number, related-sequence-number*)>\n");
			out.write("<!ATTLIST submission\n");
			out
					.write("submission-type (original-application | amendment | resubmission | presubmission | annual-report | establishment-description-supplement | efficacy-supplement | labeling-supplement | chemistry-manufacturing-controls-supplement | other) #REQUIRED\n");
			out.write(">\n");
			out.write("<!ELEMENT sequence-number (#PCDATA)>\n");
			out.write("<!ELEMENT related-sequence-number (#PCDATA)>\n");
			out
					.write("<!-- ===================== M1 REGIONAL STRUCTURE ==================== -->\n");
		} catch (Exception e) {
			System.out.println("write Common Content " + e);
		}
	}

	public void writeCommonContentM2M5(BufferedWriter out) {
		try {
			out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			out.write("<!ENTITY % att \" ID      ID     #IMPLIED\n");
			out.write("xml:lang CDATA  #IMPLIED\">\n");
			out
					.write("<!-- ============================================================= -->\n");
			out.write("<!-- Top-level element -->\n");
			out
					.write("<!-- ============================================================= -->\n");
			out
					.write("<!ELEMENT ectd:ectd (m1-administrative-information-and-prescribing-information?, m2-common-technical-document-summaries?, m3-quality?, m4-nonclinical-study-reports?, m5-clinical-study-reports?)>\n");
			out.write("<!ATTLIST ectd:ectd\n");
			out.write("xmlns:ectd CDATA #FIXED \"http://www.ich.org/ectd\"\n");
			out
					.write("xmlns:xlink CDATA #FIXED \"http://www.w3c.org/1999/xlink\"\n");
			out.write("xml:lang CDATA #IMPLIED\n");
			out.write("dtd-version CDATA #FIXED \"3.2\"\n");
			out.write(">\n");
			out
					.write("<!-- ============================================================= -->\n");
			out.write("<!-- Leaf content -->\n");
			out
					.write("<!-- ============================================================= -->\n");
			out.write("<!ELEMENT leaf (title, link-text?)>\n");
			out.write("<!ATTLIST leaf\n");
			out.write("ID ID #REQUIRED\n");
			out.write("application-version CDATA #IMPLIED\n");
			out.write("version CDATA #IMPLIED\n");
			out.write("font-library CDATA #IMPLIED\n");
			out
					.write("operation (new | append | replace | delete) #REQUIRED\n");
			out.write("modified-file CDATA #IMPLIED\n");
			out.write("checksum CDATA #REQUIRED\n");
			out.write("checksum-type CDATA #REQUIRED\n");
			out.write("keywords CDATA #IMPLIED\n");
			out
					.write("xmlns:xlink CDATA #FIXED \"http://www.w3c.org/1999/xlink\"\n");
			out.write("xlink:type CDATA #FIXED \"simple\"\n");
			out.write("xlink:role CDATA #IMPLIED\n");
			out.write("xlink:href CDATA #IMPLIED\n");
			out
					.write("xlink:show (new | replace | embed | other | none) #IMPLIED\n");
			out
					.write("xlink:actuate (onLoad | onRequest | other | none) #IMPLIED\n");
			out.write("xml:lang CDATA #IMPLIED\n");
			out.write(">\n");
			out.write("<!ELEMENT title (#PCDATA)>\n");
			out.write("<!ATTLIST title\n");
			out.write("ID ID #IMPLIED\n");
			out.write(">\n");
			out.write("<!ELEMENT link-text (#PCDATA | xref)*>\n");
			out.write("<!ATTLIST link-text\n");
			out.write("ID ID #IMPLIED\n");
			out.write(">\n");
			out.write("<!ELEMENT xref EMPTY>\n");
			out.write("<!ATTLIST xref\n");
			out.write("ID ID #REQUIRED\n");
			out
					.write("xmlns:xlink CDATA #FIXED \"http://www.w3c.org/1999/xlink\"\n");
			out.write("xlink:type CDATA #FIXED \"simple\"\n");
			out.write("xlink:role CDATA #IMPLIED\n");
			out.write("xlink:title CDATA #REQUIRED\n");
			out.write("xlink:href CDATA #REQUIRED\n");
			out
					.write("xlink:show (new | replace | embed | other | none) #IMPLIED\n");
			out
					.write("xlink:actuate (onLoad | onRequest | other | none) #IMPLIED\n");
			out.write(">\n");
			out
					.write("<!ELEMENT node-extension (title, (leaf | node-extension)+)>\n");
			out.write("<!ATTLIST node-extension\n");
			out.write("ID ID #IMPLIED\n");
			out.write("xml:lang CDATA #IMPLIED\n");
			out.write(">\n");
			out
					.write("<!-- ============================================================= -->\n");
			out.write("<!-- CTD Backbone structures -->\n");
			out
					.write("<!-- ============================================================= -->\n");
			out
					.write("<!ELEMENT m1-administrative-information-and-prescribing-information (leaf*)>\n");
			out
					.write("<!ATTLIST m1-administrative-information-and-prescribing-information\n");
			out.write("%att;\n");
			out.write(">\n");
		} catch (Exception e) {
			System.out.println("write Common Content " + e);
		}
	}

	public String readFixedContent(String fileName) {
		String result = "File Not Found";
		try {
			FileInputStream file = new FileInputStream(fileName);
			DataInputStream in = new DataInputStream(file);
			byte[] b = new byte[in.available()];
			in.readFully(b);
			in.close();
			result = new String(b, 0, b.length, "Cp850");

			in = null;
			file = null;
		} catch (FileNotFoundException fileNotFound) {
			System.out.println("File Data : " + fileNotFound);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void setFinxdContentForM1toM5(String stype, BufferedWriter outm1,
			BufferedWriter outm2m5) {
		try {
			PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
			if (stype.equals("us")) {
				String fileName = propertyInfo.getValue(stype.toUpperCase()
						+ "M1Regional");
				System.out.println("*********** File Name ***********"
						+ fileName);
				String fixedContent = readFixedContent(fileName);
				System.out.println(fixedContent);
				if (fixedContent.trim().equals("File Not Found"))
					writeCommonContentM1(outm1);
				else
					outm1.write(fixedContent);

				System.out.println(fixedContent);
			}

			String fileName = propertyInfo.getValue(stype.toUpperCase()
					+ "M2M5");
			System.out.println("*********** File Name ***********" + fileName);
			String fixedContent = readFixedContent(fileName);

			if (fixedContent.trim().equals("File Not Found"))
				writeCommonContentM2M5(outm2m5);
			else
				outm2m5.write(fixedContent);

			propertyInfo = null;
			fixedContent = null;
			fileName = null;
		} catch (Exception e) {
			System.out.println("set Fixed Content : " + e);
		}
	}
}
