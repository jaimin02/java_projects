package com.docmgmt.struts.actions.workspace;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
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

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.docmgmt.dto.DTOSubmittedWorkspaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeAttribute;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.ITreeNode;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.server.webinterface.beans.CommonTreeBean;
import com.docmgmt.server.webinterface.beans.TempTreeBean;
import com.docmgmt.server.webinterface.beans.TempTreeBeanForAdmin;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Nagesh
 * */
public class WorkspaceNodes extends ActionSupport {
	private static final long serialVersionUID = 1L;
	Document treeDocument;
	private ArrayList<DTOWorkSpaceNodeHistory> nodesHistory;
	private DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	DTOSubmittedWorkspaceNodeDetail dtowsnodedtl=new DTOSubmittedWorkspaceNodeDetail();

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
	
	void addLinks(Node nodeRoot, Document doc) {
		if (nodeRoot == null)
			return;
		// System.out.println(nodeRoot.getNodeName() + " = = " +
		// nodeRoot.getFirstChild()==null?"":nodeRoot.getFirstChild().getNodeName());//
		// + " - " + nodeRoot.getNodeValue());
		if (nodeRoot.getNodeName().equals("li")) {
			// System.out.println("li: " +
			// nodeRoot.getChildNodes().getLength());
			if (nodeRoot.getFirstChild().getNodeType() == Node.CDATA_SECTION_NODE) {
				String txtCnt = nodeRoot.getFirstChild().getTextContent();
				String s[] = txtCnt.split(";");
				String url = "workspaceNodeAttrAction.do?";
				String nid = "";
				for (int i = 0; i < s.length; i++) {
					// System.out.println(s[i]);
					String z[] = s[i].split(":");
					if (z.length >= 2) {
						// System.out.println(z[0] + " - " + z[1]);
						if (z[0].toLowerCase().equals("nodeid")) {
							url += z[0] + "=" + z[1] + "&";
							nid = z[1];
							// System.out.print(" nodeid = " + nid);
						} else if (z[0].toLowerCase().equals("displayname")) {
							url += z[0] + "=" + z[1] + "&";

						}
					}
					// else
					// System.out.println(z[0]);
				}
				Attr urlAttr = doc.createAttribute("data");
				urlAttr.setTextContent("url: '" + url + "'");

				nodeRoot.getAttributes().setNamedItem(urlAttr);

				for (ITreeNode hisNode : nodesHistory) {
					// System.out.println("hid: " + hisNode.getNodeID() + " - "
					// + new Integer(nid.trim()).intValue());
					if (hisNode.getNodeID() == Integer.parseInt((nid.trim()))) {
						Node clsAttr;
						if (nodeRoot.getAttributes().getNamedItem("data") == null) {
							clsAttr = doc.createAttribute("data");
							clsAttr.setTextContent("addClass: 'lntempty'");
							nodeRoot.getAttributes().setNamedItem(clsAttr);
						} else {
							// clsAttr= doc.createAttribute("data");
							// clsAttr=nodeRoot.getAttributes().getNamedItem("data");
							// clsAttr.setTextContent(clsAttr.getTextContent() +
							// ",addClass: 'lntempty'");
							for (int i = 0; i < nodeRoot.getAttributes()
									.getLength(); i++) {
								if (nodeRoot.getAttributes().item(i).equals(
										"data")) {
									clsAttr = nodeRoot.getAttributes().item(i);
									clsAttr.setTextContent(clsAttr
											.getTextContent()
											+ ",addClass: 'lntempty'");
								}
							}
						}
						break;
					}
				}
			}
		}
		for (int i = 0; i < nodeRoot.getChildNodes().getLength(); i++)// (Node
		// n:nodeRoot.getChildNodes())
		{
			if (nodeRoot.getChildNodes().item(i).getNodeName().equals("ul"))
				addLinks(nodeRoot.getChildNodes().item(i).getFirstChild(), doc);
		}
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
			if (nodeAttribute.getNodeId() == nodeId && nodeAttribute.getAttrValue() != null && !nodeAttribute.getAttrValue().equals("")){
				if(!nodeAttribute.getAttrName().contains("URS Reference No.") && !nodeAttribute.getAttrName().contains("FS Reference No.") )
				attributeString += " " + nodeAttribute.getAttrValue() + ",";
			}
		}
		if (!attributeString.equals("") && attributeString.endsWith(",")) {
			attributeString = attributeString.substring(0, attributeString
					.length() - 1);
		}

		return attributeString;
	}
	
	
	public String reArrangeNodeNo() {
		htmlContent="";
		DocMgmtImpl doc=new DocMgmtImpl();
		
		String workspaceId = ActionContext.getContext().getSession().get(
				"ws_id").toString();
		System.out.println("Call Successfully->"+workspaceId+" ("+nodeIds);
		
		if(doc.reArangeNodeNumbers(workspaceId, nodeIds))
				htmlContent = "<b>Re-arrange Successfully</b>";
		else
			htmlContent = "<b>Problem in Re-arrange</b>";
		
		return SUCCESS;

	}

	public String getWorkspacenodeHistory()
	{
		htmlContent="";
		
		Vector<DTOWorkSpaceNodeDetail> dto=new Vector<DTOWorkSpaceNodeDetail>();
		
		DocMgmtImpl doc=new DocMgmtImpl();
		
		String workspaceId = ActionContext.getContext().getSession().get(
				"ws_id").toString();
		dto=doc.getWorkspacenodeHistory(workspaceId, nodeId,"operation");
		
		String table="";
		
		table="<table>";
		table=table+"<tr>";
		
		if(dto.size()>0)
		{
			htmlContent="<span></span>";
		}
		else
			htmlContent="<span>No Data Available</span>";
		for(int i=0;i<dto.size();i++)
		{
			DTOWorkSpaceNodeDetail data=new DTOWorkSpaceNodeDetail();
			data=dto.elementAt(i);
			table=table+"<td colspan='2'>"+data.getSequenceno()+ " ("+ data.getAttrValue() +") ";
			table=table+"</td></tr>";
			table=table+"<tr>";
			data=null;
		}
		table=table+"</table>";
		htmlContent=htmlContent+table;
		System.out.println(htmlContent);
		return SUCCESS;
	}
	

	@Override
	public String execute() {

	

		TempTreeBean tree = new TempTreeBean();
		// boolean isWsUser=true;
		TempTreeBeanForAdmin treeadmin = new TempTreeBeanForAdmin();
		String workspaceID =wsid;
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
		// if (isWsUser)
		// return "wsuser";
		return SUCCESS;
	}

	
	public String checkTreeRights() {
		htmlContent="";
		CommonTreeBean genTree = new CommonTreeBean();
		String workspaceID = ActionContext.getContext().getSession().get(
				"ws_id").toString();
		int usergrpcode = Integer.parseInt(ActionContext.getContext()
				.getSession().get("usergroupcode").toString());
		int usercode = Integer.parseInt(ActionContext.getContext().getSession()
				.get("userid").toString());
		
		treeDocument = genTree.retTreeNode(docMgmtImpl
				.getNodeAndRightDetailNewTree(workspaceID, usergrpcode,
						usercode));
		NodeList liList = treeDocument.getElementsByTagName("li");

		if(liList.getLength()>0){
			htmlContent="true";
		}else{
			htmlContent="false";
		}
		
		return SUCCESS;
		
	}
	
	public String getHtmlCode() {
		return htmlCode;
	}

	public void setHtmlCode(String htmlCode) {
		this.htmlCode = htmlCode;
	}

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}

	public String getProject_type() {
		return project_type;
	}

	public void setProject_type(String project_type) {
		this.project_type = project_type;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
}
