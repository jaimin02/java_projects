package com.docmgmt.struts.actions.workspace;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.docmgmt.dto.DTOSubmittedWorkspaceNodeDetail;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
//import com.docmgmt.dto.DTORepositoryMst;
import com.docmgmt.dto.DTOWorkSpaceNodeAttribute;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceUserMst;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.server.webinterface.beans.CommonTreeBean;
import com.docmgmt.server.webinterface.beans.TempTreeBean;
import com.docmgmt.server.webinterface.beans.TempTreeBeanForAdmin;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ReplicateWorkspaceRights extends ActionSupport {

	private static final long serialVersionUID = 1L;
	Document treeDocument;
	private ArrayList<DTOWorkSpaceNodeHistory> nodesHistory;
	private DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	DTOSubmittedWorkspaceNodeDetail dtowsnodedtl=new DTOSubmittedWorkspaceNodeDetail();

	public String workSpaceId;
	public String destWorkSpaceId;
	public String htmlCode;
	public String client_name;
	public String project_type;
	public String project_name;
	public int tempcount = 0; // added for maintain sequence

	public String location_name;
	public String htmlContent = "";
	public String nodeIds="";
	public String nodeId="";
	public String allowDragandDrop;
	//public String lastPublishedVersion;
	public boolean lockSeqFlag=false;
	public String projectTimeLine;
	public Vector<DTOWorkSpaceNodeAttribute> getAttrDtl=null;
	public boolean showTrackingReport=false;
	public String wsid;
	public String lbl_folderName;
	public String lbl_nodeName;
	private String dTreeFirst;
	public String dTreeSecond;
	public Vector<DTOWorkSpaceMst> getWorkspaceDetail;
	public Vector<DTOWorkSpaceNodeDetail> getSourceNodes;
	public Vector<DTOWorkSpaceNodeDetail> getDestNodes;
	public boolean stageAssigned = false;
	
	public String RightsType;
	public String remark;
	public int uCode;
	public int userCode;
	public Vector<DTOUserMst> assignWorkspaceRightsDetails;
	Vector<DTOWorkSpaceUserRightsMst> getSRFlagData=new Vector<>();
	Vector<DTOWorkSpaceUserRightsMst> getAdjustDateData=new Vector<>();
	public int duration;
	public Vector<DTOWorkSpaceNodeDetail> getAllNodeIds;
	
	Vector<DTOWorkSpaceUserRightsMst> hoursData=new Vector<>();
	public String wsId;
	public int  userGrpCode;
	public int stageId;
	public String ProjectTimeLine;
	KnetProperties knetProperties = KnetProperties.getPropInfo();
	DTOWorkSpaceUserRightsMst getdtl = new DTOWorkSpaceUserRightsMst();
	public String selectUsers;
	public String temp;
	public String OpenFileAndSignOff;
	public String redirectAction;
	public String TimelineCalculation;
	public String fromDt;
	public String toDt;
	
	StringBuffer tableHtml=new StringBuffer();
	String html="";
	boolean isOddRow = true;
	
	
    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String clientName) {
        client_name = clientName;
    }

    public String getProject_type() {
        return project_type;
    }

    public void setProject_type(String projectType) {
        project_type = projectType;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String projectName) {
        project_name = projectName;
    }

    static StringBuffer sbdTreeFirst = new StringBuffer();
    static StringBuffer sbdTreeSecond = new StringBuffer();

    public String getdTreeFirst() {
        return dTreeFirst;
    }

    public void setdTreeFirst(String dTreeFirst) {
        this.dTreeFirst = dTreeFirst;
    }

    public String getdTreeSecond() {
        return dTreeSecond;
    }

    public void setdTreeSecond(String dTreeSecond) {
        this.dTreeSecond = dTreeSecond;
    }

    public static StringBuffer getSbdTreeFirst() {
        return sbdTreeFirst;
    }

    public static void setSbdTreeFirst(StringBuffer sbdTreeFirst) {
        ReplicateWorkspaceRights.sbdTreeFirst = sbdTreeFirst;
    }

    public static StringBuffer getSbdTreeSecond() {
        return sbdTreeSecond;
    }

    public static void setSbdTreeSecond(StringBuffer sbdTreeSecond) {
        ReplicateWorkspaceRights.sbdTreeSecond = sbdTreeSecond;
    }

    public String execute() throws Exception {

		TempTreeBean tree = new TempTreeBean();
		// boolean isWsUser=true;
		TempTreeBeanForAdmin treeadmin = new TempTreeBeanForAdmin();
		String workspaceID = workSpaceId;
		/*String workspaceID = ActionContext.getContext().getSession().get(
				"ws_id").toString();*/
		/*********************/
		CommonTreeBean genTree = new CommonTreeBean();
		nodesHistory = docMgmtImpl.getAllNodesLastHistory(workspaceID, null);
		// System.out.println(nodesHistory.size());
		/*********************/
		// System.out.println("usergroupcode: " +
		// ActionContext.getContext().getSession().get("usergroupcode").toString());

		int usergrpcode = Integer.parseInt(ActionContext.getContext()
				.getSession().get("usergroupcode").toString());
		int usercode = Integer.parseInt(ActionContext.getContext().getSession()
				.get("userid").toString());
		String userType = ActionContext.getContext().getSession().get(
				"usertypecode").toString();
		
		 PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
			allowDragandDrop = propertyInfo.getValue("DragAndDropFile");
			projectTimeLine = propertyInfo.getValue("ProjectTimeLine");
			lbl_folderName = propertyInfo.getValue("ForlderName");
			lbl_nodeName = propertyInfo.getValue("NodeName");	
		/****************************************************/
		//updating workspace Accessed date in workspacemst table and workspaceusermst
		docMgmtImpl.updateWorkspaceMstAccessedDate(workspaceID,usergrpcode,usercode);
		
		getAttrDtl = docMgmtImpl.getTimelineAttrDtlForTree(workspaceID);
		
		if(getAttrDtl.size()>0 && projectTimeLine.equalsIgnoreCase("Yes")){
			showTrackingReport=true;
		}
		
		int countForArchival = docMgmtImpl.getCountForArchivleSequence(workspaceID);
		if(countForArchival>0)
			lockSeqFlag=true;
		
		
		Integer refreshNodeId = (Integer) ActionContext.getContext()
				.getSession().get("refreshNodeId");

		tree.setUserType(userType);
		treeadmin.setUserType(userType);

		if (refreshNodeId != null) {

			// System.out.println(refreshNodeId);
			tree.setRefreshNodeId(refreshNodeId.intValue());
			treeadmin.setRefreshNodeId(refreshNodeId.intValue());
			ActionContext.getContext().getSession().remove("refreshNodeId");

		}

		/*
		 * if(userType.equals("0002")) {
		 */
		// isWsUser=false;
		// System.out.println("here1");
		// htmlCode=genTree.retTreeHTML(docMgmtImpl.getNodeAndRightDetail1(workspaceID,
		// usergrpcode,
		// usercode));//("getNodeAndRightDetail1",workspaceID,usergrpcode,usercode);//new
		// Integer(workspaceID),new Integer(usergrpcode),new Integer(usercode));
		// treeadmin.checkIfFilePresent(workspaceID,usergrpcode,usercode);
		//treeDocument = genTree.retTreeNode(docMgmtImpl.getNodeAndRightDetailNewTree(workspaceID, usergrpcode,usercode));
		treeDocument = genTree.retTreeNode(docMgmtImpl.getNodeAndRightDetailNewTreeList(workspaceID, usergrpcode,usercode));
		Vector<DTOWorkSpaceNodeAttribute> allNodesType0002Attrs = docMgmtImpl
				.getNodeAttributes(workspaceID, -1);
		// System.out.println("lcd1: " +
		// treeDocument.getFirstChild().getNodeName());
		// System.out.println("lcd2: " +
		// treeDocument.getFirstChild().getNodeValue());
		// System.out.println("lcd3: " +
		// treeDocument.getFirstChild().getNodeType());
		// System.out.println("lcd4: " +
		// treeDocument.getFirstChild().getTextContent());

		// addLinks(treeDocument.getFirstChild().getFirstChild(),treeDocument);

		// get all li elements
		NodeList liList = treeDocument.getElementsByTagName("li");
		String displayLeafAttrs= propertyInfo.getValue("DisplayLeafAttr");
		for (int i = 0; i < liList.getLength(); i++) {
			String nodeId = "";
			String nodeName = "";
			String nodeDisplayName = "";
			String folderName = "";
			String publishFlag="";
			String nodeTypeIndi="";
			String RequiredFlag="";
			String LockedNodeFlag="";

			// get li element
			Node liElement = liList.item(i);
			// get child list of li element
			NodeList liChildList = liElement.getChildNodes();

			for (int j = 0; j < liChildList.getLength(); j++) {
				Node liChild = liChildList.item(j);
				if (liChild.getNodeType() == Document.CDATA_SECTION_NODE) {
					// this section extracts the data from cdata section
					String cdata[] = liChild.getTextContent().split(";");
					for (int k = 0; k < cdata.length; k++) {
						String propArray[] = cdata[k].split(":");
						String key = "";
						String value = "";
						if (propArray.length == 2) {
							key = propArray[0];
							value = propArray[1];
						}
						if (key.toLowerCase().equals("nodeid")) {
							nodeId = value;
							//flag=docMgmtImpl.submittedNodeIdDetail(workspaceID.toString(),Integer.parseInt(nodeId));
						} else if (key.toLowerCase().equals("nodename")) {
							nodeName = value;
						} else if (key.toLowerCase().equals("nodedisplayname")) {
							nodeDisplayName = value;
						} else if (key.toLowerCase().equals("foldername")) {
							folderName = value;
						}
						else if (key.toLowerCase().equals("publishflag")) {
							publishFlag = value;
						}
						else if (key.toLowerCase().equals("nodetypeindi")) {
							nodeTypeIndi = value;
						}
						else if (key.toLowerCase().equals("requiredflag")) {
							RequiredFlag = value;
						}
						else if (key.toLowerCase().equals("LockedNodeFlag")) {
							LockedNodeFlag = value;
						}
						
					}

					// now we put the appropriate links
					String attrContent = ((Element) liElement)
							.getAttribute("data");
					if (attrContent == null || attrContent.equals(""))
						/*attrContent += ",url: 'workspaceNodeAttrAction.do?nodeId="
								+ nodeId + "'";*/
						attrContent = "url: 'workspaceNodeAttrAction.do?nodeId="
								+ nodeId + "&wsid="+workspaceID +"'";
					else
						attrContent = "url: 'workspaceNodeAttrAction.do?nodeId="
								+ nodeId + "&wsid="+workspaceID +"'";
						/*attrContent += ",url: 'workspaceNodeAttrAction.do?nodeId="
								+ nodeId + "'";*/
					((Element) liElement).setAttribute("data", attrContent);

					// now we check whether this node has a file on it
					for (int k = 0; k < nodesHistory.size(); k++) {

						if (nodesHistory.get(k).getNodeID() == new Integer(
								nodeId).intValue()) {
							// System.out.println(nodesHistory.get(k).getNodeID()
							// + " - " + new Integer(nodeId).intValue());
							String nodeNameStr = nodesHistory.get(k)
									.getFileName();
							tempcount++;

							// updating node history details

							// WorkSpaceNodeHistory wnh=new
							// WorkSpaceNodeHistory();
							// wnh.updateNodeDetailsSerialNo(workspaceID,nodesHistory.get(k).getNodeID(),tempcount);

							String className = nodeNameStr.substring(
									nodeNameStr.lastIndexOf(".") + 1,
									nodeNameStr.length()).trim();

							attrContent = ((Element) liElement)
									.getAttribute("data");
							if (attrContent == null || attrContent.equals("")) {
								attrContent = "addClass: '" + className + "'";
							} else {
								attrContent += ",addClass: '" + className + "'";
							}

							((Element) liElement).setAttribute("data",
									attrContent);
							/*
							 * boolean expand=false; if
							 * (nodesHistory.get(k).getFileName
							 * ().endsWith("stf-fast123.xml")) { expand=true;
							 * System.out.println("expand=true");
							 * //((Element)liElement).setAttribute("expand",new
							 * Boolean(true).toString()); }
							 */
							// updating parents
							updateParents(liElement, "data",
									"addClass: 'parent1'", treeDocument);
							// if (expand)
							// updateParents(liElement,"data","addClass: 'expanded'",treeDocument);
							break;
						}
					}

				}// if
				else if (liChild.getNodeType() == Document.ELEMENT_NODE) {
					if (liChild.getNodeName().equals("label")) {

						String attrValueStr = getAttributeString(allNodesType0002Attrs, Integer.parseInt(nodeId));
						int isLeaf = docMgmtImpl.isLeafNodes(workspaceID, Integer.parseInt(nodeId));
						
						//if(isLeaf==1 && nodeName.equalsIgnoreCase("TC")){
						if(displayLeafAttrs.equalsIgnoreCase("Yes")){
							if(isLeaf==1 && !attrValueStr.equals("") ){
								liChild.setTextContent(folderName+ " - ");
								Element boldStart = treeDocument.createElement("i");
								boldStart.setTextContent("[ " + attrValueStr + " ]");
								liChild.appendChild(boldStart);
							}
						//}
					
						if (!attrValueStr.equals("") && isLeaf==0) {
							//if(!folderName.equalsIgnoreCase("PQ Scripts")){
								liChild.setTextContent(folderName+ " - ");
								Element boldStart = treeDocument.createElement("i");
								
								boldStart.setTextContent("[ " + attrValueStr + " ]");
								liChild.appendChild(boldStart);
							//}
						}
					}
					}
				}
			}// for
		}

		// create DOMSource for source tree
		Source xmlSource = new DOMSource(treeDocument);

		// create StreamResult for transformation result
		ByteArrayOutputStream docop = new ByteArrayOutputStream();
		Result result = new StreamResult(docop);

		// create TransformerFactory
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();

		// create Transformer for transformation
		Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty("indent", "yes");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
					"yes");

			// transform content
			transformer.transform(xmlSource, result);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		htmlCode = new String(docop.toByteArray());
		// System.out.println(htmlCode);

		/*
		 * } else { htmlCode =
		 * tree.checkIfFilePresent(workspaceID,usergrpcode,usercode);
		 * //System.out.println("htmlCode"+htmlCode);
		 * //htmlCode=tree.getWorkspaceTreeHtml(workspaceID, usergrpcode,
		 * usercode); }
		 */

		//Vector<Object[]> wsDetail = docMgmtImpl.getWorkspaceDesc(workspaceID);
		Vector<Object[]> wsDetail = docMgmtImpl.getWorkspaceDescList(workspaceID);

		if (wsDetail != null) {
			Object[] record = wsDetail.elementAt(0);
			if (record != null) {
				client_name = record[4].toString();

				project_type = record[5].toString();

				project_name = record[1].toString();

				location_name = record[2].toString();

			}
		}
		getWorkspaceDetail = docMgmtImpl.getUserWorkspaceListForRightsReplication(usergrpcode, usercode,workSpaceId); 
		return SUCCESS;
	   }
    

	void updateParents(Node node, String attrName, String appendStr,
			Document doc) {
		Node t = node.getParentNode();
		if (t == null)
			return;
		if (t.getNodeName().equals("li")) {
			NamedNodeMap attris = t.getAttributes();
			Node atrn = attris.getNamedItem(attrName);
			if (atrn == null) {
				atrn = doc.createAttribute(attrName);
				atrn.setTextContent(appendStr);
				attris.setNamedItem(atrn);
			} else {
				String tc = atrn.getNodeValue();

				if (tc.contains("addClass:")) {

					tc.replaceFirst("addClass:", appendStr);
				} else {
					tc = tc + "," + appendStr;
				}
				atrn.setNodeValue(tc);
				// System.out.println("tc"+tc);
			}
		}
		updateParents(t, attrName, appendStr, doc);
	}
	
	
	
	private String getAttributeString(
			Vector<DTOWorkSpaceNodeAttribute> allNodesType0002Attrs, int nodeId) {
		String attributeString = "";
		for (int indAttr = 0; indAttr < allNodesType0002Attrs.size(); indAttr++) {
			DTOWorkSpaceNodeAttribute nodeAttribute = allNodesType0002Attrs
					.get(indAttr);
			if (nodeAttribute.getNodeId() == nodeId
					&& nodeAttribute.getAttrValue() != null
					&& !nodeAttribute.getAttrValue().equals(""))
				attributeString += " " + nodeAttribute.getAttrValue() + ",";
		}
		if (!attributeString.equals("") && attributeString.endsWith(",")) {
			attributeString = attributeString.substring(0, attributeString
					.length() - 1);
		}

		return attributeString;
	}
	
	public String showDestinationProject(){
		TempTreeBean tree = new TempTreeBean();
		// boolean isWsUser=true;
		TempTreeBeanForAdmin treeadmin = new TempTreeBeanForAdmin();
		String workspaceID = workSpaceId;
		/*String workspaceID = ActionContext.getContext().getSession().get(
				"ws_id").toString();*/
		/*********************/
		CommonTreeBean genTree = new CommonTreeBean();
		nodesHistory = docMgmtImpl.getAllNodesLastHistory(workspaceID, null);
		// System.out.println(nodesHistory.size());
		/*********************/
		// System.out.println("usergroupcode: " +
		// ActionContext.getContext().getSession().get("usergroupcode").toString());

		int usergrpcode = Integer.parseInt(ActionContext.getContext()
				.getSession().get("usergroupcode").toString());
		int usercode = Integer.parseInt(ActionContext.getContext().getSession()
				.get("userid").toString());
		String userType = ActionContext.getContext().getSession().get(
				"usertypecode").toString();
		
		 PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
			allowDragandDrop = propertyInfo.getValue("DragAndDropFile");
			projectTimeLine = propertyInfo.getValue("ProjectTimeLine");
			lbl_folderName = propertyInfo.getValue("ForlderName");
			lbl_nodeName = propertyInfo.getValue("NodeName");	
		/****************************************************/
		//updating workspace Accessed date in workspacemst table and workspaceusermst
		docMgmtImpl.updateWorkspaceMstAccessedDate(workspaceID,usergrpcode,usercode);
		
		getAttrDtl = docMgmtImpl.getTimelineAttrDtlForTree(workspaceID);
		
		if(getAttrDtl.size()>0 && projectTimeLine.equalsIgnoreCase("Yes")){
			showTrackingReport=true;
		}
		
		int countForArchival = docMgmtImpl.getCountForArchivleSequence(workspaceID);
		if(countForArchival>0)
			lockSeqFlag=true;
		
		
		Integer refreshNodeId = (Integer) ActionContext.getContext()
				.getSession().get("refreshNodeId");

		tree.setUserType(userType);
		treeadmin.setUserType(userType);

		if (refreshNodeId != null) {

			// System.out.println(refreshNodeId);
			tree.setRefreshNodeId(refreshNodeId.intValue());
			treeadmin.setRefreshNodeId(refreshNodeId.intValue());
			ActionContext.getContext().getSession().remove("refreshNodeId");

		}

		/*
		 * if(userType.equals("0002")) {
		 */
		// isWsUser=false;
		// System.out.println("here1");
		// htmlCode=genTree.retTreeHTML(docMgmtImpl.getNodeAndRightDetail1(workspaceID,
		// usergrpcode,
		// usercode));//("getNodeAndRightDetail1",workspaceID,usergrpcode,usercode);//new
		// Integer(workspaceID),new Integer(usergrpcode),new Integer(usercode));
		// treeadmin.checkIfFilePresent(workspaceID,usergrpcode,usercode);
		//treeDocument = genTree.retTreeNode(docMgmtImpl.getNodeAndRightDetailNewTree(workspaceID, usergrpcode,usercode));
		treeDocument = genTree.retTreeNode(docMgmtImpl.getNodeAndRightDetailNewTreeList(workspaceID, usergrpcode,usercode));
		Vector<DTOWorkSpaceNodeAttribute> allNodesType0002Attrs = docMgmtImpl
				.getNodeAttributes(workspaceID, -1);
		// System.out.println("lcd1: " +
		// treeDocument.getFirstChild().getNodeName());
		// System.out.println("lcd2: " +
		// treeDocument.getFirstChild().getNodeValue());
		// System.out.println("lcd3: " +
		// treeDocument.getFirstChild().getNodeType());
		// System.out.println("lcd4: " +
		// treeDocument.getFirstChild().getTextContent());

		// addLinks(treeDocument.getFirstChild().getFirstChild(),treeDocument);

		// get all li elements
		NodeList liList = treeDocument.getElementsByTagName("li");
		String displayLeafAttrs= propertyInfo.getValue("DisplayLeafAttr");
		for (int i = 0; i < liList.getLength(); i++) {
			String nodeId = "";
			String nodeName = "";
			String nodeDisplayName = "";
			String folderName = "";
			String publishFlag="";
			String nodeTypeIndi="";
			String RequiredFlag="";
			String LockedNodeFlag="";

			// get li element
			Node liElement = liList.item(i);
			// get child list of li element
			NodeList liChildList = liElement.getChildNodes();

			for (int j = 0; j < liChildList.getLength(); j++) {
				Node liChild = liChildList.item(j);
				if (liChild.getNodeType() == Document.CDATA_SECTION_NODE) {
					// this section extracts the data from cdata section
					String cdata[] = liChild.getTextContent().split(";");
					for (int k = 0; k < cdata.length; k++) {
						String propArray[] = cdata[k].split(":");
						String key = "";
						String value = "";
						if (propArray.length == 2) {
							key = propArray[0];
							value = propArray[1];
						}
						if (key.toLowerCase().equals("nodeid")) {
							nodeId = value;
							//flag=docMgmtImpl.submittedNodeIdDetail(workspaceID.toString(),Integer.parseInt(nodeId));
						} else if (key.toLowerCase().equals("nodename")) {
							nodeName = value;
						} else if (key.toLowerCase().equals("nodedisplayname")) {
							nodeDisplayName = value;
						} else if (key.toLowerCase().equals("foldername")) {
							folderName = value;
						}
						else if (key.toLowerCase().equals("publishflag")) {
							publishFlag = value;
						}
						else if (key.toLowerCase().equals("nodetypeindi")) {
							nodeTypeIndi = value;
						}
						else if (key.toLowerCase().equals("requiredflag")) {
							RequiredFlag = value;
						}
						else if (key.toLowerCase().equals("LockedNodeFlag")) {
							LockedNodeFlag = value;
						}
						
					}

					// now we put the appropriate links
					String attrContent = ((Element) liElement)
							.getAttribute("data");
					if (attrContent == null || attrContent.equals(""))
						/*attrContent += ",url: 'workspaceNodeAttrAction.do?nodeId="
								+ nodeId + "'";*/
						attrContent = "url: 'workspaceNodeAttrAction.do?nodeId="
								+ nodeId + "&wsid="+workspaceID +"'";
					else
						attrContent = "url: 'workspaceNodeAttrAction.do?nodeId="
								+ nodeId + "&wsid="+workspaceID +"'";
						/*attrContent += ",url: 'workspaceNodeAttrAction.do?nodeId="
								+ nodeId + "'";*/
					((Element) liElement).setAttribute("data", attrContent);

					// now we check whether this node has a file on it
					for (int k = 0; k < nodesHistory.size(); k++) {

						if (nodesHistory.get(k).getNodeID() == new Integer(
								nodeId).intValue()) {
							// System.out.println(nodesHistory.get(k).getNodeID()
							// + " - " + new Integer(nodeId).intValue());
							String nodeNameStr = nodesHistory.get(k)
									.getFileName();
							tempcount++;

							// updating node history details

							// WorkSpaceNodeHistory wnh=new
							// WorkSpaceNodeHistory();
							// wnh.updateNodeDetailsSerialNo(workspaceID,nodesHistory.get(k).getNodeID(),tempcount);

							String className = nodeNameStr.substring(
									nodeNameStr.lastIndexOf(".") + 1,
									nodeNameStr.length()).trim();

							attrContent = ((Element) liElement)
									.getAttribute("data");
							if (attrContent == null || attrContent.equals("")) {
								attrContent = "addClass: '" + className + "'";
							} else {
								attrContent += ",addClass: '" + className + "'";
							}

							((Element) liElement).setAttribute("data",
									attrContent);
							/*
							 * boolean expand=false; if
							 * (nodesHistory.get(k).getFileName
							 * ().endsWith("stf-fast123.xml")) { expand=true;
							 * System.out.println("expand=true");
							 * //((Element)liElement).setAttribute("expand",new
							 * Boolean(true).toString()); }
							 */
							// updating parents
							updateParents(liElement, "data",
									"addClass: 'parent1'", treeDocument);
							// if (expand)
							// updateParents(liElement,"data","addClass: 'expanded'",treeDocument);
							break;
						}
					}

				}// if
				else if (liChild.getNodeType() == Document.ELEMENT_NODE) {
					if (liChild.getNodeName().equals("label")) {

						String attrValueStr = getAttributeString(allNodesType0002Attrs, Integer.parseInt(nodeId));
						int isLeaf = docMgmtImpl.isLeafNodes(workspaceID, Integer.parseInt(nodeId));
						
						//if(isLeaf==1 && nodeName.equalsIgnoreCase("TC")){
						if(displayLeafAttrs.equalsIgnoreCase("Yes")){
							if(isLeaf==1 && !attrValueStr.equals("") ){
								liChild.setTextContent(folderName+ " - ");
								Element boldStart = treeDocument.createElement("i");
								boldStart.setTextContent("[ " + attrValueStr + " ]");
								liChild.appendChild(boldStart);
							}
						//}
					
						if (!attrValueStr.equals("") && isLeaf==0) {
							//if(!folderName.equalsIgnoreCase("PQ Scripts")){
								liChild.setTextContent(folderName+ " - ");
								Element boldStart = treeDocument.createElement("i");
								
								boldStart.setTextContent("[ " + attrValueStr + " ]");
								liChild.appendChild(boldStart);
							//}
						}
					}
					}
				}
			}// for
		}

		// create DOMSource for source tree
		Source xmlSource = new DOMSource(treeDocument);

		// create StreamResult for transformation result
		ByteArrayOutputStream docop = new ByteArrayOutputStream();
		Result result = new StreamResult(docop);

		// create TransformerFactory
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();

		// create Transformer for transformation
		Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty("indent", "yes");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
					"yes");

			// transform content
			transformer.transform(xmlSource, result);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		dTreeSecond = new String(docop.toByteArray());
		return SUCCESS;
	} 
	
	public String ReplicateRights() throws NumberFormatException, SQLException, ParseException{

		//workspaceId=ActionContext.getContext().getSession().get("ws_id").toString();
		try{
			if(workSpaceId==null){
				htmlContent="Please select any node before performing any activity";
				return SUCCESS;
			}
			getSourceNodes=new Vector<DTOWorkSpaceNodeDetail>();
			
			//StringBuffer tableHtml=new StringBuffer();
			//String html="";
			  
			tableHtml.append("<TABLE class=\"datatable\"style=\"table-layout:fixed; margin-left: 20px; margin-top: 5px;"+
		                " margin-bottom: 5px;font-family: Calibri;border-collapse: collapse; width: 95%;\">");
			tableHtml.append("<TR style=\"color: white; border: 1px solid black;\">");
			tableHtml.append("<TH style=\"border: 1px solid; background: #b1b1b1; border-color: black; padding-left: 5px;\">"
						+"<b style=\"font-size: 17px;\">Node Name</b></TH></TR>");
			
			String[] selectValuesArray = nodeIds.split("_");
				
			if(selectValuesArray.length>=1){
				String sourceNodeDisplayNameToCheck;
				String destNodeDisplayNameToCheck;
				int sourceNodeId;
				int destNodeId;
				for(int i = 0; i<selectValuesArray.length; i++) {
					int isParentNode = docMgmtImpl.isLeafNodes(workSpaceId, Integer.parseInt(selectValuesArray[i]));
					if(isParentNode <= 0)
						continue;
					getSourceNodes = docMgmtImpl.getNodeDetail(workSpaceId, Integer.parseInt(selectValuesArray[i]));
					sourceNodeDisplayNameToCheck = getSourceNodes.get(0).getNodeDisplayName();
					sourceNodeId = getSourceNodes.get(0).getNodeId();
					getDestNodes = docMgmtImpl.getNodeDetailByNodeDisplayNameForReplication(destWorkSpaceId, sourceNodeDisplayNameToCheck);
					if(getDestNodes.size() > 0){
						destNodeDisplayNameToCheck = getDestNodes.get(0).getNodeDisplayName();
						destNodeId = getDestNodes.get(0).getNodeId(); 
						if(sourceNodeDisplayNameToCheck.equalsIgnoreCase(destNodeDisplayNameToCheck)){
							insertStages(sourceNodeId,destNodeId);
							stageAssigned = true;
						}
					}
				}
			}
			if(getDestNodes!=null && getDestNodes.size()==0){
				tableHtml.append("<tr class=\""+((isOddRow)?"odd":"even")+"\">");
				//tableHtml.append("<TD style=\"font-size:16px; padding-left: 5px; border: 1px solid; word-wrap:break-word;\">"+getNodeHistory.get(j).getNodeName()+"</TD>");
				tableHtml.append("<TD style=\"font-size:16px; padding-left: 5px; border: 1px solid; word-wrap:break-word;\">"+getSourceNodes.get(0).getFolderName()+"</TD></TR>");
				
			}
			tableHtml.append("</TABLE>");
			
			if( tableHtml.toString().contains(".pdf") || tableHtml.toString().contains(".PDF") ||
					tableHtml.toString().contains(".docx") || tableHtml.toString().contains(".DOCX") )
			htmlContent="true_"+tableHtml;
			else if(stageAssigned == false)
				htmlContent="noRights";
			else
				htmlContent="noDiv";
			return SUCCESS;
		}
		catch(Exception e){
			htmlContent="false_"+e.toString();	
		}
		
		/*boolean isOddRow = true;
		getNodeHistory=new Vector<DTOWorkSpaceNodeHistory>();
		getChildNodes=new Vector<DTOWorkSpaceNodeDetail>();
		StringBuffer tableHtml=new StringBuffer();
		String html="";
		  
		tableHtml.append("<TABLE class=\"datatable\"style=\"table-layout:fixed; margin-left: 20px; margin-top: 5px;"+
	                " margin-bottom: 5px;font-family: Calibri;border-collapse: collapse; width: 95%;\">");
		tableHtml.append("<TR style=\"color: white; border: 1px solid black;\">");
		tableHtml.append("<TH style=\"border: 1px solid; background: #b1b1b1; border-color: black; padding-left: 5px;\">"
					+"<b style=\"font-size: 17px;\">Node Name</b></TH></TR>");
		
		String[] selectValuesArray = nodeIds.split("_");
			
		if(selectValuesArray.length>=1){
		
			for(int i = 0; i<selectValuesArray.length; i++) {
			getChildNodes=docMgmtImpl.getChildNodesForSectionDeletion(workspaceId, Integer.parseInt(selectValuesArray[i]),"A");
			isLeafNode = docMgmtImpl.isLeafNodes(workspaceId, Integer.parseInt(selectValuesArray[i]));
			if(isLeafNode==1 && getChildNodes.size()==0){
				getNodeHistory=docMgmtImpl.getNodeHistoryForSectinDeletion(workspaceId, Integer.parseInt(selectValuesArray[0]));
				if(getNodeHistory.size()>0){
					if(!getNodeHistory.get(0).getFileName().equalsIgnoreCase("No File")){
				tableHtml.append("<tr class=\""+((isOddRow)?"odd":"even")+"\">");
				//tableHtml.append("<TD style=\"font-size:16px; padding-left: 5px; border: 1px solid; word-wrap:break-word;\">"+getNodeHistory.get(j).getNodeName()+"</TD>");
				tableHtml.append("<TD style=\"font-size:16px; padding-left: 5px; border: 1px solid; word-wrap:break-word;\">"+getNodeHistory.get(0).getFileName()+"</TD></TR>");
				if(isOddRow)
					isOddRow=false;
				else
					isOddRow=true;
				tableHtml.append("</TABLE>");
				html= tableHtml.toString();
		   		htmlContent=html;
				//htmlContent=extraHtmlCode;
				return SUCCESS;
				}
				//break;
				}
			}
			
			
			for(int j=0;j<getChildNodes.size();j++){
				isLeafNode = docMgmtImpl.isLeafNodes(workspaceId, getChildNodes.get(j).getNodeId());
				if(isLeafNode!=0){
					getNodeHistory=docMgmtImpl.getNodeHistoryForSectinDeletion(workspaceId, getChildNodes.get(j).getNodeId());
					if(getNodeHistory.size()>0){
						tableHtml.append("<tr class=\""+((isOddRow)?"odd":"even")+"\">");
						//tableHtml.append("<TD style=\"font-size:16px; padding-left: 5px; border: 1px solid; word-wrap:break-word;\">"+getNodeHistory.get(j).getNodeName()+"</TD>");
						tableHtml.append("<TD style=\"font-size:16px; padding-left: 5px; border: 1px solid; word-wrap:break-word;\">"+getNodeHistory.get(i).getFileName()+"</TD></TR>");
						if(isOddRow)
							isOddRow=false;
						else
							isOddRow=true;
						flag=false;
						//break;
					}
				}
			}
			if(flag==false){
				tableHtml.append("</TABLE>");
				html= tableHtml.toString();
		   		htmlContent=html;
				//htmlContent=extraHtmlCode;
				return SUCCESS;}
			//if(getChildNodes.size()>0){
				 Vector<DTOWorkSpaceNodeDetail> getWorkspaceNodeDetail=docMgmtImpl.getWorkspaceNodeDetailByNodeId(workspaceId, Integer.parseInt(selectValuesArray[i]));
				if(getWorkspaceNodeDetail.get(0).getStatusIndi()=='D'){
					docMgmtImpl.activeNodeDetail(workspaceId, Integer.parseInt(selectValuesArray[i]),remark);
					activatedFlag=true;
				}
			//}
			
		}
	}else{
		//DTOWorkSpaceNodeDetail dto = docMgmtImpl.getNodeDetail(workspaceId,nodeId).get(0);
		isLeafNode = docMgmtImpl.isLeafNodes(workspaceId, Integer.parseInt(selectValuesArray[0]));
		if(isLeafNode==0){
			getNodeHistory=docMgmtImpl.getNodeHistoryForSectinDeletion(workspaceId, Integer.parseInt(selectValuesArray[0]));
		}
		if(getNodeHistory.size()>0){
			tableHtml.append("<tr class=\""+((isOddRow)?"odd":"even")+"\">");
			//tableHtml.append("<TD style=\"font-size:16px; padding-left: 5px; border: 1px solid; word-wrap:break-word;\">"+getNodeHistory.get(j).getNodeName()+"</TD>");
			tableHtml.append("<TD style=\"font-size:16px; padding-left: 5px; border: 1px solid; word-wrap:break-word;\">"+getNodeHistory.get(0).getFileName()+"</TD></TR>");
			if(isOddRow)
				isOddRow=false;
			else
				isOddRow=true;
			tableHtml.append("</TABLE>");
			html= tableHtml.toString();
	   		htmlContent=html;
			//htmlContent=extraHtmlCode;
			return SUCCESS;
			//break;
		}else{
			docMgmtImpl.activeNodeDetail(workspaceId, Integer.parseInt(selectValuesArray[0]),remark);
			activatedFlag=true;
			}
	}
		if(activatedFlag==false){
			htmlContent="activatedFlagFalse";
			return SUCCESS;
		}*/
		return SUCCESS;
	}

	public void insertStages(int sourceNodeId,int destNodeId) throws ParseException, SQLException{
		
		Vector<DTOWorkSpaceNodeDetail> WsNodeDetail=new Vector<DTOWorkSpaceNodeDetail>();
		ArrayList<DTOWorkSpaceUserRightsMst> userRightsList = new ArrayList<DTOWorkSpaceUserRightsMst>();
		ArrayList<DTOWorkSpaceUserRightsMst> userRightsListForTimeLine = new ArrayList<DTOWorkSpaceUserRightsMst>();
		Vector<DTOWorkSpaceNodeDetail> WsUsrDtlFor=new Vector<DTOWorkSpaceNodeDetail>();
		
		DTOWorkSpaceUserRightsMst objWSUserRightsMstforModuleHistory = new DTOWorkSpaceUserRightsMst();
		String roleCode;
		//String workspaceID=ActionContext.getContext().getSession().get("ws_id").toString();
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		ProjectTimeLine = knetProperties.getValue("ProjectTimeLine");
		TimelineCalculation =knetProperties.getValue("TimelineCalculation");
		
		Vector<DTOWorkSpaceNodeHistory> getNodeHistory = docMgmtImpl.getNodeHistoryFolderName(destWorkSpaceId, destNodeId);
		if(getNodeHistory.size()>0){
			tableHtml.append("<tr class=\""+((isOddRow)?"odd":"even")+"\">");
			//tableHtml.append("<TD style=\"font-size:16px; padding-left: 5px; border: 1px solid; word-wrap:break-word;\">"+getNodeHistory.get(j).getNodeName()+"</TD>");
			tableHtml.append("<TD style=\"font-size:16px; padding-left: 5px; border: 1px solid; word-wrap:break-word;\">"+getNodeHistory.get(0).getFolderName()+"</TD></TR>");
			if(isOddRow)
				isOddRow=false;
			else
				isOddRow=true;
			return; 
		}
		else if(getNodeHistory.size()<=0){
			Vector<DTOWorkSpaceUserRightsMst> getUserRightsForWorksapce=new Vector<DTOWorkSpaceUserRightsMst>();
			//getting source project attached user list of source project
			getUserRightsForWorksapce=docMgmtImpl.getUserlistForReplicateRights(workSpaceId, sourceNodeId);
				if(getUserRightsForWorksapce.size()>0){
				//checking if dest project have rights in wsmst 
				for(int i=0;i<getUserRightsForWorksapce.size();i++){
					int userCode = getUserRightsForWorksapce.get(i).getUserCode();
					int userGrpCode = getUserRightsForWorksapce.get(i).getUserGroupCode();
					Vector<DTOWorkSpaceUserMst> dto = new Vector<DTOWorkSpaceUserMst>();
					dto = docMgmtImpl.getUserRightsForWorkspace(destWorkSpaceId,userCode,userGrpCode);
					//if rights for user not found, assign into workspaceusermst
					if(dto.size()<=0){
						String modulespecrights = "Y";
						String userGroupYN = "user";
						
						
						DTOWorkSpaceUserMst dtoWorkSpaceUserMst = new DTOWorkSpaceUserMst();
						DTOWorkSpaceUserMst deletewsurmdata = new DTOWorkSpaceUserMst();
						DTOWorkSpaceUserRightsMst dtowsurm = new DTOWorkSpaceUserRightsMst();
						
						dtoWorkSpaceUserMst.setWorkSpaceId(destWorkSpaceId);
						dtowsurm.setWorkSpaceId(destWorkSpaceId);
						deletewsurmdata.setWorkSpaceId(destWorkSpaceId);
						deletewsurmdata.setUserGroupCode(userGrpCode);
						dtoWorkSpaceUserMst.setUserGroupCode(userGrpCode);
						dtowsurm.setUserGroupCode(userGrpCode);
						dtoWorkSpaceUserMst.setAdminFlag('N');
						Timestamp ts = new Timestamp(new Date().getTime());
						dtoWorkSpaceUserMst.setLastAccessedOn(ts);
						dtoWorkSpaceUserMst.setRemark(remark);
						int ucd=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
						dtoWorkSpaceUserMst.setModifyBy(ucd);
						dtoWorkSpaceUserMst.setModifyOn(ts);
						
						DateFormat dateFormat = new SimpleDateFormat("d-MMM-yy");
						Calendar cal = Calendar.getInstance();
						Date today = cal.getTime();
						dtoWorkSpaceUserMst.setFromDt(today);
						cal.add(Calendar.YEAR, 50); // to get previous year add -1
						Date nextYear = cal.getTime();
						dtoWorkSpaceUserMst.setToDt(nextYear);
						
						dtoWorkSpaceUserMst.setRightsType("modulewise");
						deletewsurmdata.setRightsType("modulewise");
						
						deletewsurmdata.setUserCode(userCode);
						docMgmtImpl.DeleteProjectlevelRights(deletewsurmdata);
						
						docMgmtImpl.insertUpdateUsertoWorkspaceForAttachUser(dtoWorkSpaceUserMst, userCode);
						
						dtoWorkSpaceUserMst.setStages("-");
						
						dtoWorkSpaceUserMst.setMode(1);
						//docMgmtImpl.insertUpdateUsertoWorkspaceHistory(dtoWorkSpaceUserMst, userCode);
						docMgmtImpl.insertUpdateUsertoWorkspaceHistoryForAttachUser(dtoWorkSpaceUserMst, userCode);
						}		
					}
				//assigning rights in workspacesuserrightsmst
				
				for(int i=0;i<getUserRightsForWorksapce.size();i++){
					String  destWsId = destWorkSpaceId;
					int srcNid = destNodeId;
					String[] multiCheckUser = new String[1];
					multiCheckUser[0] = String.valueOf(getUserRightsForWorksapce.get(i).getUserCode());
					int[] stageIds=new int[1];
					stageIds[0] = getUserRightsForWorksapce.get(i).getStageId();
					
					String selectedUsers [] = multiCheckUser;
					String nodeIdsCSV = "";
					WsNodeDetail=docMgmtImpl.getChildNodesModulewise(destWsId,srcNid);
					for(int i1=0;i1<WsNodeDetail.size();i1++){	
						DTOWorkSpaceNodeDetail nodeIds = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(i1);
						nodeIdsCSV+=Integer.toString(nodeIds.getNodeId())+",";
					}
					nodeIdsCSV= nodeIdsCSV.substring(0, nodeIdsCSV.length() - 1);
					docMgmtImpl.RemoveFolderSpecificMultipleUserRights(destWsId, selectedUsers, stageIds,nodeIdsCSV);
					
					if(ProjectTimeLine.equalsIgnoreCase("yes")){
						docMgmtImpl.RemoveUserRightsfromTimeline(destWsId, selectedUsers, stageIds,nodeIdsCSV);
						}
					
					for(int wsnd=0;wsnd<WsNodeDetail.size();wsnd++){
						DTOWorkSpaceNodeDetail nodeDetail = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(wsnd);
						for(int k=0;k<selectedUsers.length;k++)
						{
							DTOUserMst objUserMst = docMgmtImpl.getUserInfo(Integer.parseInt(selectedUsers[k]));
							Integer userGroupCode = new Integer(objUserMst.getUserGroupCode());
							DTOWorkSpaceUserRightsMst objWorkSpaceUserRightsMst = new DTOWorkSpaceUserRightsMst();
							
							
							
							objWorkSpaceUserRightsMst.setWorkSpaceId(destWsId);
							objWorkSpaceUserRightsMst.setModifyBy(userId);
							objWorkSpaceUserRightsMst.setUserCode(Integer.parseInt(selectedUsers[k]));
							objWorkSpaceUserRightsMst.setUserGroupCode(userGroupCode.intValue());
							objWorkSpaceUserRightsMst.setNodeId(nodeDetail.getNodeId());
						
							objWorkSpaceUserRightsMst.setCanReadFlag('Y');
							objWorkSpaceUserRightsMst.setCanAddFlag('Y');
							objWorkSpaceUserRightsMst.setCanDeleteFlag('Y');
							objWorkSpaceUserRightsMst.setCanEditFlag('Y');
							objWorkSpaceUserRightsMst.setAdvancedRights("Y");
							roleCode= docMgmtImpl.getRoleCode(destWsId, Integer.parseInt(selectedUsers[k]));
							objWorkSpaceUserRightsMst.setRoleCode(roleCode);
							objWorkSpaceUserRightsMst.setMode(1);
							
							for (int istageIds = 0; istageIds < stageIds.length; istageIds++) {
								DTOWorkSpaceUserRightsMst dtoClone = objWorkSpaceUserRightsMst.clone();
								dtoClone.setStageId(stageIds[istageIds]);
								userRightsList.add(dtoClone);
							}
						}
						
					}
					docMgmtImpl.insertFolderSpecificMultipleUserRightsWithRoleCode(userRightsList);
					
					getAllNodeIds = docMgmtImpl.getAllLeafNodeIds(destWorkSpaceId);
					
					ArrayList<Integer> nodeIds = new ArrayList();
					{
						for(int k=0;k<getAllNodeIds.size();k++){
							nodeIds.add(getAllNodeIds.get(k).getNodeId());
						}
					}
					boolean showHours=false;
					Vector<DTOWorkSpaceNodeAttribute> getAttrDtl = docMgmtImpl.getTimelineAttrDtl(destWorkSpaceId);
					
					if(getAttrDtl.size()>0){
						showHours=true;
					}
					
					if(ProjectTimeLine.equalsIgnoreCase("yes") && showHours==true ){
						boolean skipCurrent=false;
						userRightsListForTimeLine = new ArrayList<DTOWorkSpaceUserRightsMst>();
						Vector<DTOWorkSpaceUserRightsMst> getLastRightsDtl = new Vector<>();
						//int nodeId = userRightsList.get(0).getNodeId();
						int stageId = userRightsList.get(0).getStageId();
						getLastRightsDtl = docMgmtImpl.getLastRightsRecordDtlForAdjustDate(destWorkSpaceId, destNodeId,stageId);
						if(getLastRightsDtl.size()<=0){
							getLastRightsDtl = docMgmtImpl.UserOnNodeTimelineTracking(destWorkSpaceId, destNodeId);
							
						}
						DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
						String attrValues=docMgmtImpl.getAttributeDetailByAttrName(destWorkSpaceId,1,"Project Start Date").getAttrValue();
						
						if(getLastRightsDtl.size()<=0 && (attrValues != null && !attrValues.equals(""))){
							skipCurrent=true;
							LocalDateTime startDate=null;
							LocalDateTime endDate = null;
							String s[]=attrValues.split("/");
							startDate=LocalDateTime.of(LocalDate.of(Integer.parseInt(s[0]),Integer.parseInt(s[1]),Integer.parseInt(s[2])), LocalTime.of(0,0));
							endDate=startDate.plusHours(duration*3);
							DayOfWeek dayOfWeek = endDate.getDayOfWeek();
							//System.out.println(dayOfWeek);
							if(dayOfWeek==DayOfWeek.SUNDAY){
								endDate=endDate.plusHours(24);
							}
							dto.setWorkSpaceId(destWorkSpaceId);
							dto.setNodeId(destNodeId);
							dto.setStartDate(Timestamp.valueOf(startDate));
							dto.setEndDate(Timestamp.valueOf(endDate));
							dto.setAdjustDate(Timestamp.valueOf(endDate));
							
						}
											
						if(getLastRightsDtl.size()>0){
							int lastIndex = getLastRightsDtl.size()-1;
							dto.setWorkSpaceDesc(getLastRightsDtl.get(lastIndex).getWorkSpaceId());
							dto.setNodeId(getLastRightsDtl.get(lastIndex).getNodeId());
							dto.setUserCode(getLastRightsDtl.get(lastIndex).getUserCode());
							dto.setUserGroupCode(getLastRightsDtl.get(lastIndex).getUserGroupCode());
							dto.setStageId(getLastRightsDtl.get(lastIndex).getStageId());
							dto.setHours(getLastRightsDtl.get(lastIndex).getHours());
							if(TimelineCalculation.equalsIgnoreCase("Date")){
								SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
							    Date frmDate = dateFormat.parse(fromDt);
							    Timestamp frmtimestamp = new java.sql.Timestamp(frmDate.getTime());
								dto.setFromDate(frmtimestamp);
								
								Date toDate = dateFormat.parse(toDt);
							    Timestamp totimestamp = new java.sql.Timestamp(toDate.getTime());
								dto.setToDate(totimestamp);
							}
							else{
								dto.setStartDate(getLastRightsDtl.get(lastIndex).getStartDate());
								dto.setEndDate(getLastRightsDtl.get(lastIndex).getEndDate());	
							}
							
							dto.setAdjustDate(getLastRightsDtl.get(lastIndex).getAdjustDate());
							dto.setModifyOn(getLastRightsDtl.get(lastIndex).getModifyOn());
						}
						
						for(int wsnd=0;wsnd<WsNodeDetail.size();wsnd++){
							DTOWorkSpaceNodeDetail nodeDetail = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(wsnd);
							if(nodeIds.contains(nodeDetail.getNodeId())){
							for(int k=0;k<selectedUsers.length;k++)
							{
								DTOUserMst objUserMst = docMgmtImpl.getUserInfo(Integer.parseInt(selectedUsers[k]));
								Integer userGroupCode = new Integer(objUserMst.getUserGroupCode());
								DTOWorkSpaceUserRightsMst objWorkSpaceUserRightsMst = new DTOWorkSpaceUserRightsMst();
								
								objWorkSpaceUserRightsMst.setWorkSpaceId(destWorkSpaceId);
								objWorkSpaceUserRightsMst.setModifyBy(userId);
								objWorkSpaceUserRightsMst.setUserCode(Integer.parseInt(selectedUsers[k]));
								objWorkSpaceUserRightsMst.setUserGroupCode(userGroupCode.intValue());
								objWorkSpaceUserRightsMst.setNodeId(nodeDetail.getNodeId());
							
								objWorkSpaceUserRightsMst.setCanReadFlag('Y');
								objWorkSpaceUserRightsMst.setCanAddFlag('Y');
								objWorkSpaceUserRightsMst.setCanDeleteFlag('Y');
								objWorkSpaceUserRightsMst.setCanEditFlag('Y');
								objWorkSpaceUserRightsMst.setAdvancedRights("Y");
								objWorkSpaceUserRightsMst.setDuration(duration);
								if(TimelineCalculation.equalsIgnoreCase("Date")){
									objWorkSpaceUserRightsMst.setFromDate(dto.getFromDate());
									objWorkSpaceUserRightsMst.setToDate(dto.getToDate());
									objWorkSpaceUserRightsMst.setStartDate(dto.getFromDate());
									objWorkSpaceUserRightsMst.setEndDate(dto.getToDate());
								}
								else{
									objWorkSpaceUserRightsMst.setStartDate(dto.getStartDate());
									objWorkSpaceUserRightsMst.setEndDate(dto.getEndDate());
								}
								objWorkSpaceUserRightsMst.setAdjustDate(dto.getAdjustDate());
								objWorkSpaceUserRightsMst.setMode(1);
								
								for (int istageIds = 0; istageIds < stageIds.length; istageIds++) {
									DTOWorkSpaceUserRightsMst dtoClone = objWorkSpaceUserRightsMst.clone();
									dtoClone.setStageId(stageIds[istageIds]);
									userRightsListForTimeLine.add(dtoClone);
									}
								}
							}
						}
					
						if(TimelineCalculation.equalsIgnoreCase("Date"))
							docMgmtImpl.AttachUserRightsForTimeLineWithExludedDate(userRightsListForTimeLine);
						else
							docMgmtImpl.AttachUserRightsForTimeLine(userRightsListForTimeLine);
						
						Timestamp signOffDate=dto.getAdjustDate();
						
						getSRFlagData=docMgmtImpl.getProjectTimelineSRFlagData(destWorkSpaceId);
						LocalDateTime startDate=null;
						LocalDateTime endDate = null;
						
						if(getSRFlagData.size() >0 && (attrValues != null && !attrValues.equals(""))){				
								int parentNodeIdforAdjustDate=docMgmtImpl.getParentNodeId(destWorkSpaceId, dto.getNodeId());
								
								getAdjustDateData=docMgmtImpl.getProjectTimelineAdjustHoursUpdate(destWorkSpaceId,dto.getNodeId(),parentNodeIdforAdjustDate,dto.getUserCode(),dto.getStageId());
								
								LocalDateTime date=signOffDate.toLocalDateTime();
								int t=0;
								if(skipCurrent){
									t=1;
								}
								for(int k=t;k<getAdjustDateData.size();k++){
									//System.out.println("Hours : "+hoursData.get(k).getHours());
									DTOWorkSpaceUserRightsMst dtotimeline = (DTOWorkSpaceUserRightsMst) getAdjustDateData
											.get(k);
									int noofhours=dtotimeline.getHours();
									if(startDate==null){
										startDate=date;
										endDate=startDate.plusHours(noofhours*3);
										DayOfWeek dayOfWeek = endDate.getDayOfWeek();
										//System.out.println(dayOfWeek);
										if(dayOfWeek==DayOfWeek.SUNDAY){
											endDate=endDate.plusHours(24);
										}
									}
									else{
										startDate=endDate;
										endDate=startDate.plusHours(noofhours*3);
										DayOfWeek dayOfWeek = endDate.getDayOfWeek();
										//System.out.println(dayOfWeek);
										if(dayOfWeek==DayOfWeek.SUNDAY){
											endDate=endDate.plusHours(24);
										}
									}	
									//dtotimeline.setStartDate(dto.getStartDate());
									//dtotimeline.setEndDate(dto.getEndDate());
									dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
						            
						            
						            docMgmtImpl.updateTimelineAdjustDate(dtotimeline);
								}	
							}
						else{

							String attrValue=docMgmtImpl.getAttributeDetailByAttrName(destWorkSpaceId,1,"Project Start Date").getAttrValue();
							
							if(attrValue != null && !attrValue.equals("")){
								hoursData=docMgmtImpl.getProjectTimelineDetailsForHours(destWorkSpaceId);
								
								String s[]=attrValue.split("/");
								
								LocalDateTime date=LocalDateTime.of(LocalDate.of(Integer.parseInt(s[0]),Integer.parseInt(s[1]),Integer.parseInt(s[2])), LocalTime.of(0,0));

								 startDate=null;
								endDate = null;
								for(int k=0;k<hoursData.size();k++){
									//System.out.println("Hours : "+hoursData.get(k).getHours());
									DTOWorkSpaceUserRightsMst dtotimeline = (DTOWorkSpaceUserRightsMst) hoursData
											.get(k);
									int noofhours=dtotimeline.getHours();
									if(startDate==null){
										startDate=date;
										endDate=startDate.plusHours(noofhours*3);
										DayOfWeek dayOfWeek = endDate.getDayOfWeek();
										//System.out.println(dayOfWeek);
										if(dayOfWeek==DayOfWeek.SUNDAY){
											endDate=endDate.plusHours(24);
										}
									}
									else{
										startDate=endDate;
										endDate=startDate.plusHours(noofhours*3);
										DayOfWeek dayOfWeek = endDate.getDayOfWeek();
										//System.out.println(dayOfWeek);
										if(dayOfWeek==DayOfWeek.SUNDAY){
											endDate=endDate.plusHours(24);
										}
									}	
									
									dtotimeline.setStartDate(Timestamp.valueOf(startDate));
								
									dtotimeline.setEndDate(Timestamp.valueOf(endDate));
									dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
									
									docMgmtImpl.updateTimelineDatesValue(dtotimeline);
							}
								}
							}
					}
					objWSUserRightsMstforModuleHistory.setWorkSpaceId(destWorkSpaceId);
					objWSUserRightsMstforModuleHistory.setNodeId(destNodeId);
					objWSUserRightsMstforModuleHistory.setModifyBy(userId);
					
					String stageDesc="";
					String stage;
					int stageid;
						for(int i1=0;i1<stageIds.length;i1++){
							stageid=stageIds[i1];
							stage= docMgmtImpl.getStageDesc(stageid);
							stageDesc+=stage+",";
						}
					System.out.println("StageId="+stageDesc);
					  if (stageDesc != null && stageDesc.length() > 0){
						  stageDesc = stageDesc.substring(0, stageDesc.length() - 1);
					  }
					  objWSUserRightsMstforModuleHistory.setStages(stageDesc);
					  objWSUserRightsMstforModuleHistory.setMode(1);
					  
					for(int i1=0;i1<selectedUsers.length;i1++)
					{
						DTOUserMst objUserMst = docMgmtImpl.getUserInfo(Integer.parseInt(selectedUsers[i1]));
						System.out.println(objUserMst.getUserGroupCode());
						Integer userGroupCode = new Integer(objUserMst.getUserGroupCode());
						objWSUserRightsMstforModuleHistory.setUserGroupCode(userGroupCode.intValue());
						
						objWSUserRightsMstforModuleHistory.setUserCode(Integer.parseInt(selectedUsers[i1]));
						 roleCode= docMgmtImpl.getRoleCode(destWorkSpaceId, Integer.parseInt(selectedUsers[i1]));
						  objWSUserRightsMstforModuleHistory.setRoleCode(roleCode);
						//docMgmtImpl.insertModuleWiseUserHistory(objWSUserRightsMstforModuleHistory);
						docMgmtImpl.insertModuleWiseUserHistoryWithRoleCode(objWSUserRightsMstforModuleHistory);
					}
					
					
				}
				
				
				
				
				}
				else{
					tableHtml.append("<tr class=\""+((isOddRow)?"odd":"even")+"\">");
					//tableHtml.append("<TD style=\"font-size:16px; padding-left: 5px; border: 1px solid; word-wrap:break-word;\">"+getNodeHistory.get(j).getNodeName()+"</TD>");
					tableHtml.append("<TD style=\"font-size:16px; padding-left: 5px; border: 1px solid; word-wrap:break-word;\">"+getSourceNodes.get(0).getFolderName()+"</TD></TR>");
					if(isOddRow)
						isOddRow=false;
					else
						isOddRow=true;
					return;
				}
		}
		}
		
		
/*		String selectedUsers [] = multiCheckUser;
		String nodeIdsCSV = "";
		
		if(stageIds==null || selectedUsers.length==0)
			return;
		if(RightsType!=null && RightsType.equalsIgnoreCase("modulewiserights")){
			WsNodeDetail=docMgmtImpl.getChildNodesModulewise(workspaceID,sourceNodeId);
			if(sourceNodeId==1)
			{
				WsNodeDetail.removeElementAt(1);
			}
			for(int i=0;i<WsNodeDetail.size();i++){	
				DTOWorkSpaceNodeDetail nodeIds = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(i);
				nodeIdsCSV+=Integer.toString(nodeIds.getNodeId())+",";
			}
			nodeIdsCSV= nodeIdsCSV.substring(0, nodeIdsCSV.length() - 1);
			docMgmtImpl.RemoveFolderSpecificMultipleUserRights(workspaceID, selectedUsers, stageIds,nodeIdsCSV);
			if(ProjectTimeLine.equalsIgnoreCase("yes")){
			docMgmtImpl.RemoveUserRightsfromTimeline(workspaceID, selectedUsers, stageIds,nodeIdsCSV);
			}
			for(int wsnd=0;wsnd<WsNodeDetail.size();wsnd++){
				DTOWorkSpaceNodeDetail nodeDetail = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(wsnd);
				for(int k=0;k<selectedUsers.length;k++)
				{
					DTOUserMst objUserMst = docMgmtImpl.getUserInfo(Integer.parseInt(selectedUsers[k]));
					Integer userGroupCode = new Integer(objUserMst.getUserGroupCode());
					DTOWorkSpaceUserRightsMst objWorkSpaceUserRightsMst = new DTOWorkSpaceUserRightsMst();
					
					
					
					objWorkSpaceUserRightsMst.setWorkSpaceId(workspaceID);
					objWorkSpaceUserRightsMst.setModifyBy(userId);
					objWorkSpaceUserRightsMst.setUserCode(Integer.parseInt(selectedUsers[k]));
					objWorkSpaceUserRightsMst.setUserGroupCode(userGroupCode.intValue());
					objWorkSpaceUserRightsMst.setNodeId(nodeDetail.getNodeId());
				
					objWorkSpaceUserRightsMst.setCanReadFlag('Y');
					objWorkSpaceUserRightsMst.setCanAddFlag('Y');
					objWorkSpaceUserRightsMst.setCanDeleteFlag('Y');
					objWorkSpaceUserRightsMst.setCanEditFlag('Y');
					objWorkSpaceUserRightsMst.setAdvancedRights("Y");
					roleCode= docMgmtImpl.getRoleCode(workspaceID, Integer.parseInt(selectedUsers[k]));
					objWorkSpaceUserRightsMst.setRoleCode(roleCode);
					objWorkSpaceUserRightsMst.setMode(1);
					
					for (int istageIds = 0; istageIds < stageIds.length; istageIds++) {
						DTOWorkSpaceUserRightsMst dtoClone = objWorkSpaceUserRightsMst.clone();
						dtoClone.setStageId(stageIds[istageIds]);
						userRightsList.add(dtoClone);
					}
				}
				
			}
			
			//docMgmtImpl.insertFolderSpecificMultipleUserRights(userRightsList);
			docMgmtImpl.insertFolderSpecificMultipleUserRightsWithRoleCode(userRightsList);
			getAllNodeIds = docMgmtImpl.getAllLeafNodeIds(workspaceID);
			
			ArrayList<Integer> nodeIds = new ArrayList();
			{
				for(int k=0;k<getAllNodeIds.size();k++){
					nodeIds.add(getAllNodeIds.get(k).getNodeId());
				}
			}
			boolean showHours=false;
			Vector<DTOWorkSpaceNodeAttribute> getAttrDtl = docMgmtImpl.getTimelineAttrDtl(workspaceID);
			
			if(getAttrDtl.size()>0){
				showHours=true;
			}
			
			if(ProjectTimeLine.equalsIgnoreCase("yes") && showHours==true ){
				boolean skipCurrent=false;
				userRightsListForTimeLine = new ArrayList<DTOWorkSpaceUserRightsMst>();
				Vector<DTOWorkSpaceUserRightsMst> getLastRightsDtl = new Vector<>();
				//int nodeId = userRightsList.get(0).getNodeId();
				int stageId = userRightsList.get(0).getStageId();
				getLastRightsDtl = docMgmtImpl.getLastRightsRecordDtlForAdjustDate(workspaceID,nodeId,stageId);
				if(getLastRightsDtl.size()<=0){
					getLastRightsDtl = docMgmtImpl.UserOnNodeTimelineTracking(workspaceID,nodeId);
					
				}
				DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
				String attrValues=docMgmtImpl.getAttributeDetailByAttrName(workspaceID,1,"Project Start Date").getAttrValue();
				
				if(getLastRightsDtl.size()<=0 && (attrValues != null && !attrValues.equals(""))){
					skipCurrent=true;
					LocalDateTime startDate=null;
					LocalDateTime endDate = null;
					String s[]=attrValues.split("/");
					startDate=LocalDateTime.of(LocalDate.of(Integer.parseInt(s[0]),Integer.parseInt(s[1]),Integer.parseInt(s[2])), LocalTime.of(0,0));
					endDate=startDate.plusHours(duration*3);
					DayOfWeek dayOfWeek = endDate.getDayOfWeek();
					//System.out.println(dayOfWeek);
					if(dayOfWeek==DayOfWeek.SUNDAY){
						endDate=endDate.plusHours(24);
					}
					dto.setWorkSpaceId(workspaceID);
					dto.setNodeId(nodeId);
					dto.setStartDate(Timestamp.valueOf(startDate));
					dto.setEndDate(Timestamp.valueOf(endDate));
					dto.setAdjustDate(Timestamp.valueOf(endDate));
					
				}
									
				if(getLastRightsDtl.size()>0){
					int lastIndex = getLastRightsDtl.size()-1;
					dto.setWorkSpaceDesc(getLastRightsDtl.get(lastIndex).getWorkSpaceId());
					dto.setNodeId(getLastRightsDtl.get(lastIndex).getNodeId());
					dto.setUserCode(getLastRightsDtl.get(lastIndex).getUserCode());
					dto.setUserGroupCode(getLastRightsDtl.get(lastIndex).getUserGroupCode());
					dto.setStageId(getLastRightsDtl.get(lastIndex).getStageId());
					dto.setHours(getLastRightsDtl.get(lastIndex).getHours());
					if(TimelineCalculation.equalsIgnoreCase("Date")){
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
					    Date frmDate = dateFormat.parse(fromDt);
					    Timestamp frmtimestamp = new java.sql.Timestamp(frmDate.getTime());
						dto.setFromDate(frmtimestamp);
						
						Date toDate = dateFormat.parse(toDt);
					    Timestamp totimestamp = new java.sql.Timestamp(toDate.getTime());
						dto.setToDate(totimestamp);
					}
					else{
						dto.setStartDate(getLastRightsDtl.get(lastIndex).getStartDate());
						dto.setEndDate(getLastRightsDtl.get(lastIndex).getEndDate());	
					}
					
					dto.setAdjustDate(getLastRightsDtl.get(lastIndex).getAdjustDate());
					dto.setModifyOn(getLastRightsDtl.get(lastIndex).getModifyOn());
				}
				
				for(int wsnd=0;wsnd<WsNodeDetail.size();wsnd++){
					DTOWorkSpaceNodeDetail nodeDetail = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(wsnd);
					if(nodeIds.contains(nodeDetail.getNodeId())){
					for(int k=0;k<selectedUsers.length;k++)
					{
						DTOUserMst objUserMst = docMgmtImpl.getUserInfo(Integer.parseInt(selectedUsers[k]));
						Integer userGroupCode = new Integer(objUserMst.getUserGroupCode());
						DTOWorkSpaceUserRightsMst objWorkSpaceUserRightsMst = new DTOWorkSpaceUserRightsMst();
						
						objWorkSpaceUserRightsMst.setWorkSpaceId(workspaceID);
						objWorkSpaceUserRightsMst.setModifyBy(userId);
						objWorkSpaceUserRightsMst.setUserCode(Integer.parseInt(selectedUsers[k]));
						objWorkSpaceUserRightsMst.setUserGroupCode(userGroupCode.intValue());
						objWorkSpaceUserRightsMst.setNodeId(nodeDetail.getNodeId());
					
						objWorkSpaceUserRightsMst.setCanReadFlag('Y');
						objWorkSpaceUserRightsMst.setCanAddFlag('Y');
						objWorkSpaceUserRightsMst.setCanDeleteFlag('Y');
						objWorkSpaceUserRightsMst.setCanEditFlag('Y');
						objWorkSpaceUserRightsMst.setAdvancedRights("Y");
						objWorkSpaceUserRightsMst.setDuration(duration);
						if(TimelineCalculation.equalsIgnoreCase("Date")){
							objWorkSpaceUserRightsMst.setFromDate(dto.getFromDate());
							objWorkSpaceUserRightsMst.setToDate(dto.getToDate());
							objWorkSpaceUserRightsMst.setStartDate(dto.getFromDate());
							objWorkSpaceUserRightsMst.setEndDate(dto.getToDate());
						}
						else{
							objWorkSpaceUserRightsMst.setStartDate(dto.getStartDate());
							objWorkSpaceUserRightsMst.setEndDate(dto.getEndDate());
						}
						objWorkSpaceUserRightsMst.setAdjustDate(dto.getAdjustDate());
						objWorkSpaceUserRightsMst.setMode(1);
						
						for (int istageIds = 0; istageIds < stageIds.length; istageIds++) {
							DTOWorkSpaceUserRightsMst dtoClone = objWorkSpaceUserRightsMst.clone();
							dtoClone.setStageId(stageIds[istageIds]);
							userRightsListForTimeLine.add(dtoClone);
							}
						}
					}
				}
			
				if(TimelineCalculation.equalsIgnoreCase("Date"))
					docMgmtImpl.AttachUserRightsForTimeLineWithExludedDate(userRightsListForTimeLine);
				else
					docMgmtImpl.AttachUserRightsForTimeLine(userRightsListForTimeLine);
				
				Timestamp signOffDate=dto.getAdjustDate();
				
				getSRFlagData=docMgmtImpl.getProjectTimelineSRFlagData(workspaceID);
				LocalDateTime startDate=null;
				LocalDateTime endDate = null;
				
				if(getSRFlagData.size() >0 && (attrValues != null && !attrValues.equals(""))){				
						int parentNodeIdforAdjustDate=docMgmtImpl.getParentNodeId(workspaceID, dto.getNodeId());
						
						getAdjustDateData=docMgmtImpl.getProjectTimelineAdjustHoursUpdate(workspaceID,dto.getNodeId(),parentNodeIdforAdjustDate,dto.getUserCode(),dto.getStageId());
						
						LocalDateTime date=signOffDate.toLocalDateTime();
						int t=0;
						if(skipCurrent){
							t=1;
						}
						for(int k=t;k<getAdjustDateData.size();k++){
							//System.out.println("Hours : "+hoursData.get(k).getHours());
							DTOWorkSpaceUserRightsMst dtotimeline = (DTOWorkSpaceUserRightsMst) getAdjustDateData
									.get(k);
							int noofhours=dtotimeline.getHours();
							if(startDate==null){
								startDate=date;
								endDate=startDate.plusHours(noofhours*3);
								DayOfWeek dayOfWeek = endDate.getDayOfWeek();
								//System.out.println(dayOfWeek);
								if(dayOfWeek==DayOfWeek.SUNDAY){
									endDate=endDate.plusHours(24);
								}
							}
							else{
								startDate=endDate;
								endDate=startDate.plusHours(noofhours*3);
								DayOfWeek dayOfWeek = endDate.getDayOfWeek();
								//System.out.println(dayOfWeek);
								if(dayOfWeek==DayOfWeek.SUNDAY){
									endDate=endDate.plusHours(24);
								}
							}	
							//dtotimeline.setStartDate(dto.getStartDate());
							//dtotimeline.setEndDate(dto.getEndDate());
							dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
				            
				            
				            docMgmtImpl.updateTimelineAdjustDate(dtotimeline);
						}	
					}
				else{

					String attrValue=docMgmtImpl.getAttributeDetailByAttrName(workspaceID,1,"Project Start Date").getAttrValue();
					
					if(attrValue != null && !attrValue.equals("")){
						hoursData=docMgmtImpl.getProjectTimelineDetailsForHours(workspaceID);
						
						String s[]=attrValue.split("/");
						
						LocalDateTime date=LocalDateTime.of(LocalDate.of(Integer.parseInt(s[0]),Integer.parseInt(s[1]),Integer.parseInt(s[2])), LocalTime.of(0,0));

						 startDate=null;
						endDate = null;
						for(int k=0;k<hoursData.size();k++){
							//System.out.println("Hours : "+hoursData.get(k).getHours());
							DTOWorkSpaceUserRightsMst dtotimeline = (DTOWorkSpaceUserRightsMst) hoursData
									.get(k);
							int noofhours=dtotimeline.getHours();
							if(startDate==null){
								startDate=date;
								endDate=startDate.plusHours(noofhours*3);
								DayOfWeek dayOfWeek = endDate.getDayOfWeek();
								//System.out.println(dayOfWeek);
								if(dayOfWeek==DayOfWeek.SUNDAY){
									endDate=endDate.plusHours(24);
								}
							}
							else{
								startDate=endDate;
								endDate=startDate.plusHours(noofhours*3);
								DayOfWeek dayOfWeek = endDate.getDayOfWeek();
								//System.out.println(dayOfWeek);
								if(dayOfWeek==DayOfWeek.SUNDAY){
									endDate=endDate.plusHours(24);
								}
							}	
							
							dtotimeline.setStartDate(Timestamp.valueOf(startDate));
						
							dtotimeline.setEndDate(Timestamp.valueOf(endDate));
							dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
							
							docMgmtImpl.updateTimelineDatesValue(dtotimeline);
					}
						}
					}
			}
			objWSUserRightsMstforModuleHistory.setWorkSpaceId(workspaceID);
			objWSUserRightsMstforModuleHistory.setNodeId(nodeId);
			objWSUserRightsMstforModuleHistory.setModifyBy(userId);
			
			String stageDesc="";
			String stage;
			int stageid;
				for(int i=0;i<stageIds.length;i++){
					stageid=stageIds[i];
					stage= docMgmtImpl.getStageDesc(stageid);
					stageDesc+=stage+",";
				}
			System.out.println("StageId="+stageDesc);
			  if (stageDesc != null && stageDesc.length() > 0){
				  stageDesc = stageDesc.substring(0, stageDesc.length() - 1);
			  }
			  objWSUserRightsMstforModuleHistory.setStages(stageDesc);
			  objWSUserRightsMstforModuleHistory.setMode(1);
			  
			for(int i=0;i<selectedUsers.length;i++)
			{
				DTOUserMst objUserMst = docMgmtImpl.getUserInfo(Integer.parseInt(selectedUsers[i]));
				System.out.println(objUserMst.getUserGroupCode());
				Integer userGroupCode = new Integer(objUserMst.getUserGroupCode());
				objWSUserRightsMstforModuleHistory.setUserGroupCode(userGroupCode.intValue());
				
				objWSUserRightsMstforModuleHistory.setUserCode(Integer.parseInt(selectedUsers[i]));
				 roleCode= docMgmtImpl.getRoleCode(workspaceID, Integer.parseInt(selectedUsers[i]));
				  objWSUserRightsMstforModuleHistory.setRoleCode(roleCode);
				//docMgmtImpl.insertModuleWiseUserHistory(objWSUserRightsMstforModuleHistory);
				docMgmtImpl.insertModuleWiseUserHistoryWithRoleCode(objWSUserRightsMstforModuleHistory);
			}
			
			
		}else{
			
			for(int i=0;i<selectedUsers.length;i++)
			{
				DTOUserMst objUserMst = docMgmtImpl.getUserInfo(Integer.parseInt(selectedUsers[i]));
				Integer userGroupCode = new Integer(objUserMst.getUserGroupCode());
				DTOWorkSpaceUserRightsMst objWorkSpaceUserRightsMst = new DTOWorkSpaceUserRightsMst();
				
				objWorkSpaceUserRightsMst.setWorkSpaceId(workspaceID);
				objWorkSpaceUserRightsMst.setUserCode(Integer.parseInt(selectedUsers[i]));
				objWorkSpaceUserRightsMst.setUserGroupCode(userGroupCode.intValue());
				objWorkSpaceUserRightsMst.setNodeId(nodeId);
			
				objWorkSpaceUserRightsMst.setCanReadFlag('Y');
				objWorkSpaceUserRightsMst.setCanAddFlag('Y');
				objWorkSpaceUserRightsMst.setCanDeleteFlag('Y');
				objWorkSpaceUserRightsMst.setCanEditFlag('Y');
				objWorkSpaceUserRightsMst.setAdvancedRights("Y");
				roleCode= docMgmtImpl.getRoleCode(workspaceID, Integer.parseInt(selectedUsers[i]));
				objWSUserRightsMstforModuleHistory.setRoleCode(roleCode);
				objWorkSpaceUserRightsMst.setMode(1);
				
				
				for (int istageIds = 0; istageIds < stageIds.length; istageIds++) {
					DTOWorkSpaceUserRightsMst dtoClone = objWorkSpaceUserRightsMst.clone();
					dtoClone.setStageId(stageIds[istageIds]);
					userRightsList.add(dtoClone);
				}
				//objWorkSpaceUserRightsMst.setStageId(stageId);
				
				if (rights.equalsIgnoreCase("readonly")) {
					objWorkSpaceUserRightsMst.setCanAddFlag('N');
					objWorkSpaceUserRightsMst.setCanDeleteFlag('N');
					objWorkSpaceUserRightsMst.setCanEditFlag('N');`
				}else {
					
				//}
				
				//docMgmtImpl.updateWorkSpaceUserRights(objWorkSpaceUserRightsMst);
				//objWorkSpaceUserRightsMst = null;
			}
			//docMgmtImpl.insertMultipleUserRights(userRightsList);
			docMgmtImpl.insertFolderSpecificMultipleUserRightsWithRoleCode(userRightsList);	
			}
		*/}
	