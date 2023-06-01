package com.docmgmt.struts.actions.workspace;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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

public class GetAllChilds extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	public String workSpaceId;
	public static int nodeId;
	private List<?> nodeInfoVector;
	ITreeNode root;
	public String htmlContent;
	Document treeDocument;
	Document childDoc;
	CommonTreeBean genTree = new CommonTreeBean();
	private ArrayList<DTOWorkSpaceNodeHistory> nodesHistory;
	public int tempcount = 0;
	public String client_name;
	public String project_type;
	public String project_name;
	public String location_name;
	public String htmlCode;
	public String allowDragandDrop;
	public boolean lockSeqFlag=false;
	public String srcTreehtmlCode;
	Vector<DTOWorkSpaceNodeDetail> getChildNode;
	public StringBuffer TreeNode_html=new StringBuffer();
	
	
	
	
	public String execute() throws ParserConfigurationException{
		
		/*GenerateChileNodes srcTreeObj = new GenerateChileNodes();
		
		srcTreehtmlCode = srcTreeObj.getWorkspaceTreeHtml(workSpaceId,nodeId);
		System.out.println("Tree:"+srcTreehtmlCode);*/
		
		TempTreeBean tree = new TempTreeBean();
		TempTreeBeanForAdmin treeadmin = new TempTreeBeanForAdmin();
		CommonTreeBean genTree = new CommonTreeBean();
		
		int usergrpcode = Integer.parseInt(ActionContext.getContext()
				.getSession().get("usergroupcode").toString());
		int usercode = Integer.parseInt(ActionContext.getContext().getSession()
				.get("userid").toString());
		String userType = ActionContext.getContext().getSession().get(
				"usertypecode").toString();
		
		
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		allowDragandDrop = propertyInfo
				.getValue("DragAndDropFile");
		
		docMgmtImpl.updateWorkspaceMstAccessedDate(workSpaceId,usergrpcode,usercode);		
		
		int countForArchival = docMgmtImpl.getCountForArchivleSequence(workSpaceId);
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
		
		nodesHistory = docMgmtImpl.getAllNodesLastHistory(workSpaceId, null);
		
		//Vector<DTOWorkSpaceNodeAttribute> data =docMgmtImpl.getAttributeDetailForDisplay(workSpaceId, nodeId);
		
		GetAllChilds gca=new GetAllChilds();
		gca.setNodeId(nodeId);
		//getChildNode=docMgmtImpl.getChildNodesForDynaTree(workSpaceId, nodeId,usergrpcode,usercode);
		getChildNode=docMgmtImpl.getChildNodesForDynaTreeCT(workSpaceId, nodeId,usergrpcode,usercode);
		//htmlContent=retTreeNode(getChildNode);
		//retTreeNode(getChildNode).toString();
		
		treeDocument=retTreeNode(getChildNode);
		//treeDocument=retTreeNode(docMgmtImpl.getNodeAndRightDetailNewTree(workSpaceId, usergrpcode,usercode));
		
		//treeDocument = genTree.retTreeNode(docMgmtImpl.getNodeAndRightDetailNewTree(workSpaceId, usergrpcode,usercode));
		
		//Vector<DTOWorkSpaceNodeAttribute> allNodesType0002Attrs = docMgmtImpl.getNodeAttributes(workSpaceId, -1);
		Vector<DTOWorkSpaceNodeAttribute> allNodesType0002Attrs = docMgmtImpl.getNodeAttributes_wsList(workSpaceId, -1);
		
		NodeList liList = treeDocument.getElementsByTagName("li");

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
						attrContent = "url: 'workspaceNodeAttrAction.do?nodeId="
								+ nodeId + "'";
					else
						attrContent += ",url: 'workspaceNodeAttrAction.do?nodeId="
								+ nodeId + "'";
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
							
							/*  boolean expand=false; if
							  (nodesHistory.get(k).getFileName
							 * ().endsWith("stf-fast123.xml")) { expand=true;
							 * System.out.println("expand=true");
							 * //((Element)liElement).setAttribute("expand",new
							 * Boolean(true).toString()); }*/
							 
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

						String attrValueStr = getAttributeString(
								allNodesType0002Attrs, Integer.parseInt(nodeId));
						/*int isLeaf = docMgmtImpl
								.isLeafNodes(workSpaceId, Integer.parseInt(nodeId));
						if (!attrValueStr.equals("") && isLeaf==0) {
							liChild.setTextContent(liChild.getTextContent()
									+ " - ");
							Element boldStart = treeDocument.createElement("i");
							boldStart
									.setTextContent("[ " + attrValueStr + " ]");
							liChild.appendChild(boldStart);
						}*/
						
						if (!attrValueStr.equals("")) {
							liChild.setTextContent(liChild.getTextContent()
									+ " - ");
							Element boldStart = treeDocument.createElement("i");
							boldStart
									.setTextContent("[ " + attrValueStr + " ]");
							liChild.appendChild(boldStart);
						}
					}
				}
			}// for
		}
		
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
		htmlContent=TreeNode_html.append(htmlCode).toString();
		//TreeNode_html.append(htmlCode);
		//htmlCode=TreeNode_html.toString();
		//TreeNode_html=htmlCode.toString();
		System.out.println("-->> Child Loaded Successfully <<--");
		//System.out.println(htmlCode);
		//htmlContent=htmlCode;

		 
		/*  } else { htmlCode =
		 * tree.checkIfFilePresent(workspaceID,usergrpcode,usercode);
		 * //System.out.println("htmlCode"+htmlCode);
		 * //htmlCode=tree.getWorkspaceTreeHtml(workspaceID, usergrpcode,
		 * usercode); }*/
		 

		/*Vector<Object[]> wsDetail = docMgmtImpl.getWorkspaceDesc(workSpaceId);

		if (wsDetail != null) {
			Object[] record = wsDetail.elementAt(0);
			if (record != null) {
				client_name = record[4].toString();

				project_type = record[5].toString();

				project_name = record[1].toString();

				location_name = record[2].toString();

			}
		}*/
		
		
		
		return SUCCESS;
	}
	
	
	
	public Document retTreeNode(Vector treeData) throws ParserConfigurationException
	{
		
		Vector<DTOWorkSpaceNodeDetail> childs=new Vector<>();
		nodeInfoVector=treeData;
		String htmlcode=null;
		Document doc = null;
		DocMgmtImpl docmgmptl=new DocMgmtImpl();
		
		//browse the vector of dtos
		//and generate the dto tree
		for (int i=0;i<nodeInfoVector.size();i++) //parent loop
		{
			//TreeNode is the interface of node
			ITreeNode parent=(ITreeNode)nodeInfoVector.get(i);	
			int usergrpcode = Integer.parseInt(ActionContext.getContext()
					.getSession().get("usergroupcode").toString());
			int usercode = Integer.parseInt(ActionContext.getContext()
					.getSession().get("userid").toString());
			//Vector<DTOWorkSpaceUserRightsMst> nodeRec = docmgmptl.getWorkspaceUserRightsForNodeId(parent.getWsId(), usergrpcode, usercode,parent.getNodeID());
			//char nodeRec = docmgmptl.getWorkspaceUserRightsForNodeId(parent.getWsId(), usergrpcode, usercode,parent.getNodeID());
			//String s;
			char statusIndi='q';
			//if(nodeRec.size()>0){
			//if(nodeRec!=null){
				//s=nodeRec[1].toString();
				//statusIndi= nodeRec.get(0).getStatusIndi();
				//statusIndi= parent.getStatusIndiForTree();
				//parent.setStatusIndiForTree(statusIndi);
			//}
			//char statusIndi=docmgmptl.getNodeAndRightDetail(parent.getWsId(), parent.getuserGroupCode(), parent.getuserCode()).get(i)[2];
			
			//String wS=parent.getWorkspaceId();
			if(parent.getParentID()==0){
				root=parent;
			}
			/*if (parent.getParentID()==0)
			{//set the parent node as the root of dom tree	
				root=parent;
			}*/
			
			for (int j=0;j<nodeInfoVector.size();j++) //child loop
			{
				ITreeNode child=(ITreeNode)nodeInfoVector.get(j);
				if (i!=j)
				{
					if (child.getParentID()==parent.getNodeID())
					{//found a child for the parent, add it to the parent child list
						
						ArrayList childlist= parent.getChildList();
						
						if (childlist==null)
							childlist=new ArrayList();
						childlist.add(child);
					
						parent.setChildList(childlist);
					
					}
				}
			}
		}
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder parser = factory.newDocumentBuilder();
			
			//create document
			doc = parser.newDocument();
			
			//main root of the tree
			Element domRoot=doc.createElement("ul");
			//CDATASection ne=doc.createCDATASection("nodedata");
			//ne.appendData("123");
			//domRoot.appendChild(ne);
						
			//append the root to tree
			doc.appendChild(domRoot);
			
			//generate the dom root, based on the dto tree
			
			generate(root,domRoot,doc);
			
		}catch(Exception e){}
		return doc;		
	}
	
	private void generate(ITreeNode parentNodeDetail, Element domParent,Document doc) 
	{
		Element newElement=doc.createElement("li");
		
		Element label=doc.createElement("label");
		//Element label1=doc.createElement("button");
		boolean isParent=false;
		if (parentNodeDetail.getChildList()!=null && parentNodeDetail.getChildList().size()>0)
			isParent=true;
		newElement.appendChild(doc.createCDATASection(parentNodeDetail.toString()));
		
		label.setAttribute("title", takeNodeDisplayNameFromStr(parentNodeDetail.toString()));
	
	//	label.setAttribute("onclick", "uploadFileDrag(this.id)");

	
		label.setAttribute("id", new Integer(parentNodeDetail.getNodeID()).toString());
	//	label.setAttribute("class", "testdrop");
	
		label.setTextContent(parentNodeDetail.getNodeLabel());
		newElement.appendChild(label);
		
		
//		if(!isParent){
//			label1.setAttribute("title","");
//			label1.setAttribute("onmouseover", "getFileInfo(this.id)");
//			label1.setAttribute("id", "history"+new Integer(parentNodeDetail.getNodeID()).toString());
//			label1.setAttribute("class", "filehistory");
//			label1.setTextContent("I");
//			newElement.appendChild(label1);
//		}
		
		newElement.setAttribute("id",new Integer(parentNodeDetail.getNodeID()).toString());
		domParent.appendChild(newElement);
		if (isParent)
			newElement.setAttribute("class", "folder");
		//System.out.println(newElement.getAttribute("data"));

		if (parentNodeDetail.getChildList()!=null && parentNodeDetail.getChildList().size()>0)
		{
			Element newElement1=doc.createElement("ul");
			newElement.appendChild(newElement1);
			for (ITreeNode child:(ArrayList<ITreeNode>)parentNodeDetail.getChildList())            	
			{
				generate(child,newElement1,doc);//,selectedID);
			}
		}
	}
	
	private String takeNodeDisplayNameFromStr(String nodeDetailStr)
	{
		String nodeDisplayStr="";
		String[] nodeDetailList=nodeDetailStr.split(";");
		for(int i=0;i<nodeDetailList.length;i++)
		{
			String nodeAttValue=nodeDetailList[i];
			if(nodeAttValue.substring(0,nodeAttValue.indexOf(":")).trim().equalsIgnoreCase("nodeDisplayName"))
				return nodeAttValue.substring(nodeAttValue.indexOf(":")+1, nodeAttValue.length());
		}
		return nodeDisplayStr;
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
	public static int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	
}
