package com.docmgmt.struts.actions.ImportProjects.ECTD;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.docmgmt.dto.DTOAttributeValueMatrix;
import com.docmgmt.dto.DTOSubmissionMst;
import com.docmgmt.dto.DTOTemplateNodeAttrDetail;
import com.docmgmt.dto.DTOTemplateNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.entities.AttributeType;
import com.docmgmt.server.webinterface.entities.Node.RequiredFlag;
import com.docmgmt.server.webinterface.services.pdf.PDFUtilities;
import com.docmgmt.struts.actions.ImportProjects.ECTD.validate.EUValidator;
import com.docmgmt.struts.actions.ImportProjects.ECTD.validate.General.Validator.EctdValidator;
import com.docmgmt.struts.resources.MD5;

public class EctdXmlReader {

	/*Class Constants*/
	public static final short INDEX_READ_SUCCESSFULLY = 0;
	public static final short FILE_NOT_FOUND = 1;
	public static final short INVALID_XML = 2;
	public static final String INDEX_XML_FILENAME = "index.xml";
	public static final String REGIONAL_MODULE_NODE_NAME = "m1-administrative-information-and-prescribing-information";
	public static final String INDEX_XML_ROOT = "ectd:ectd";
	public static final String INDEX_MD5_FILE = "index-md5.txt";
	public static final String ECTD_LEAF_NODE = "leaf";
	public static final String ECTD_CHKSUM_ATTR = "checksum";
	public static final String ECTD_XLINK_HREF_ATTR = "xlink:href";
	public static final String ECTD_OPERATION_ATTR = "operation";
	public static final String ECTD_MODIFIEDFILE_ATTR = "modified-file";
	public static final String ECTD_TITLE_NODE = "title";
	public static final String ECTD_STF_ROOT_NODE = "ectd:study";
	public static final String US_REGIONAL_ROOT_NODE = "fda-regional:fda-regional";
	public static final String EU_REGIONAL_ROOT_NODE = "eu:eu-backbone";
	
	DocumentBuilder documentBuilder;
	File indexXmlFile;
	File xmlParentDir;
	File currXmlFile;
	int currNodeId;
	DocMgmtImpl docMgmtImpl;
	MD5 md5;
	SupportingXmlReader supportingXmlReader;
	
	
	Vector<DTOAttributeValueMatrix> allParentAttrs;
	DTOWorkSpaceMst workSpaceMst;
	
	DTOTemplateNodeDetail templateRootNodeDtl;
	ArrayList<EctdError> ectdErrorList;
	
	//Constructor
	public EctdXmlReader(File indexXmlFile) {
		this.indexXmlFile = indexXmlFile;
		currNodeId = 0;
		docMgmtImpl = new DocMgmtImpl();
		md5 = new MD5();
		currXmlFile = this.indexXmlFile;
		
		//Set supportingXmlReader
		supportingXmlReader = SupportingXmlReader.getInstance();
	}
	
	public static void main(String args[]) throws Exception {
		
		File indexXml = new File("D:/IMP/KnowledgeNETSSPL/ELC (ALL)/ELC Dossiers/06-Jan-10 (B1+C1+0002)/Trimetazidine-Mylan/0000/index.xml");
		//File indexXml = new File("D:/temp/ViPharm-Error/Project/0001/index.xml");
		//File indexXml = new File("F:/Temp/091101/0000/index.xml");
		
		String templateId = "0002";
		DTOTemplateNodeDetail rootNode= DocMgmtImpl.getTemplateRootNode(templateId);
		if(rootNode == null || rootNode.getNodeId() == 0){
			//Invalid template found with no root or multiple roots...
			return;
		}
		//Get Template in tree form.
		int rootNodeId = rootNode.getNodeId();
		DTOTemplateNodeDetail rootNodeDtlWithTree = DocMgmtImpl.getTemplateNodeDtlTree(templateId, rootNodeId);
		
		EctdXmlReader ectdXmlReader = new EctdXmlReader(indexXml);	
		DTOWorkSpaceMst wsMst=ectdXmlReader.readEctdIndexXml(rootNodeDtlWithTree);
		DTOWorkSpaceNodeDetail rootNodeDtl= wsMst.getRootNodeDtl();
		DTOSubmissionMst subMst = wsMst.getSubmissionMst();
		
		//System.out.println("Agency:"+subMst.getAgencyName());
		
		for (EctdError errorMsg : wsMst.getEctdErrorList()) {
			System.out.println(errorMsg);
		}
		//if(ectdXmlReader.getEctdErrorList().size()==0)
		//ectdXmlReader.readTree(rootNodeDtl, 1);
		System.out.println("Done");
	}
	
	public DTOWorkSpaceMst readEctdIndexXml(DTOTemplateNodeDetail templateRootNodeDtl){
		
		/*Setting up for ECTD Reading*/
		setupForECTDReading(templateRootNodeDtl);
		
		//'index.xml' file to read.
		//indexXmlFile = new File(ectdIndexFilePath);
		if(!indexXmlFile.exists()){
			ectdErrorList.add(new EctdError(EctdErrorType.ECTD_ERROR,EctdErrorMsg.getErrMsgDesc(EctdErrorMsg.FILE_NOT_FOUND),indexXmlFile.getAbsolutePath()));
			workSpaceMst.setEctdErrorList(ectdErrorList);
			return workSpaceMst;
		}
		
		//Parent Dir of the index.xml(sequence dir)

		xmlParentDir=indexXmlFile.getParentFile(); 
		//Validate checksum for index.xml file 
		validateIndexXmlChecksum();
		
		try {
			//get an instance of factory
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setValidating(true);
			//get an instance of builder
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			documentBuilder.setErrorHandler(new org.xml.sax.ErrorHandler() {
		        //Ignore the fatal errors
		        public void fatalError(SAXParseException e)throws SAXException { }
		        //Validation errors 
		        public void error(SAXParseException e)throws SAXParseException {
		          System.out.println("Error at " +e.getLineNumber() + " line.");
		          System.out.println(e.getMessage());
		          System.out.println(e.getSystemId());
		          ectdErrorList.add(new EctdError(EctdErrorType.ECTD_ERROR,"Parsing Error at line: "+e.getLineNumber()+"::"+e.getMessage(),e.getSystemId()));
		          //System.exit(0);
		        }
		        //Show warnings
		        public void warning(SAXParseException err)throws SAXParseException{
		          System.out.println("Warning: "+err.getMessage());
		          //System.exit(0);
		        }
		      });
			//create an instance of Document
			Document document = documentBuilder.parse(indexXmlFile);
			//Start Reading Ectd
			readIndexDocument(document);
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			ectdErrorList.add(new EctdError(EctdErrorType.ECTD_ERROR,e.getMessage(),indexXmlFile.getAbsolutePath()));
			return workSpaceMst;
		} catch (SAXException e) {
			e.printStackTrace();
			ectdErrorList.add(new EctdError(EctdErrorType.ECTD_ERROR,e.getMessage(),indexXmlFile.getAbsolutePath()));
			return workSpaceMst;
		} catch (Exception e) {
			e.printStackTrace();
			ectdErrorList.add(new EctdError(EctdErrorType.ECTD_ERROR,e.getMessage(),indexXmlFile.getAbsolutePath()));
			return workSpaceMst;
		}
		
		///////////////////////////General Ectd Validations///////////////////////////
		ectdErrorList.addAll(EctdValidator.validateEctdSeq(indexXmlFile.getParentFile()));
		workSpaceMst.setEctdErrorList(ectdErrorList);
		
		return workSpaceMst;
	}
	
	private void readIndexDocument(Document document){
		//Set up of the root Node
		NodeList nodeList= document.getElementsByTagName(INDEX_XML_ROOT);
		Node xmlRoot = nodeList.item(0);
		int level = 1;
		DTOWorkSpaceNodeDetail rootNodeDtl = new DTOWorkSpaceNodeDetail();;
		if (templateRootNodeDtl != null) {
			
			int newNodeId = getNewNodeId();//This will update currNodeId from 0 to 1
			rootNodeDtl.setNodeId(newNodeId);//Set Root Node Details
			rootNodeDtl.setParentNodeId(0);//Set root node's parent to 'zero'
			rootNodeDtl.setNodeNo(1);//Set root node's Node No to 1
			//Set root Node's names from template's root Node 
			rootNodeDtl.setNodeName(templateRootNodeDtl.getNodeName());
			rootNodeDtl.setNodeDisplayName(templateRootNodeDtl.getNodeDisplayName());
			rootNodeDtl.setFolderName(templateRootNodeDtl.getFolderName());
			rootNodeDtl.setRequiredFlag(templateRootNodeDtl.getRequiredFlag());
			rootNodeDtl.setNodeTypeIndi(templateRootNodeDtl.getNodeTypeIndi());
		}
		/*Read All Ectd Nodes */
		readElementChildren(xmlRoot,level,rootNodeDtl,templateRootNodeDtl);
		//Set Root Node to workSpaceMst
		workSpaceMst.setRootNodeDtl(rootNodeDtl);
	}
	
	private DTOWorkSpaceNodeDetail readElementChildren(Node parentNode,int level,DTOWorkSpaceNodeDetail parentNodeDtl,DTOTemplateNodeDetail templateNodeDtl) {
		//Get Xml Node Children
		NodeList xmlNodeChildren = parentNode.getChildNodes();
		
		//Create Blank Children List.
		ArrayList<DTOWorkSpaceNodeDetail> childrenNodeDtlList = new ArrayList<DTOWorkSpaceNodeDetail>();
		//Template children List
		ArrayList<DTOTemplateNodeDetail> childrenTempNodeDtlList = null;
		
		//Set Parent Id For New Children and NodeNo
		int currParentNodeId = 0;
		if (parentNodeDtl != null) {
			currParentNodeId = parentNodeDtl.getNodeId();
		}
		int nodeNo=0;
		/****************************************************************/
		char [] indents = new char[level];
		Arrays.fill(indents, ' ');
		String indenting = Arrays.toString(indents).replaceAll(", ", "");
		indenting = indenting.substring(1, indenting.length()-1);
		/****************************************************************/
		
		
		/**Set Template Info*********************************************/
		//Get template node children
		if(templateNodeDtl != null){
			childrenTempNodeDtlList = templateNodeDtl.getChildNodeList();
		}
		/****************************************************************/
		
		//Start Process on Xml Children Node 
		for (int i = 0; i < xmlNodeChildren.getLength(); i++) {
			Node currXmlNode = xmlNodeChildren.item(i);
			
			/**
			 * Only Element ('ELEMENT_NODE') Nodes are valid
			 * This is to avoid invalid text ('TEXT_NODE') Nodes
			 */
			if(currXmlNode.getNodeType() == Node.ELEMENT_NODE){
				
				String nodeName = currXmlNode.getNodeName().trim();
				
				//Get New NodeId
				int newNodeId = getNewNodeId();
				
				//Creating New WorkspaceNodeDetail
				DTOWorkSpaceNodeDetail currChildNodeDtl = new DTOWorkSpaceNodeDetail();
				currChildNodeDtl.setNodeId(newNodeId);
				currChildNodeDtl.setParentNodeId(currParentNodeId);
				//Set nodeNo 
				currChildNodeDtl.setNodeNo(++nodeNo);
				currChildNodeDtl.setNodeName(nodeName);
				currChildNodeDtl.setNodeDisplayName(supportingXmlReader.getNodeDisplayName(nodeName));
				
				/***********************************************************************************************************************/
				/*For All Leaf Nodes*/
				if(nodeName.equalsIgnoreCase(ECTD_LEAF_NODE)){
					
					//Setup the leaf node into 'currChildNodeDtl'.
					File leafFile = supportingXmlReader.setupForLeafNode(currXmlNode, currChildNodeDtl, xmlParentDir, ectdErrorList);
					if(leafFile != null){
						//Check leaf file's type
						if(leafFile.getName().endsWith(".xml")){
							//If it points to another xml file...
							File prevXmlFile = currXmlFile; 
							currXmlFile = leafFile;
							
							File previousParentDir = xmlParentDir;
							xmlParentDir = currXmlFile.getParentFile();
							
							try{
								Document otherXmlDoc = documentBuilder.parse(currXmlFile);
								/*******************************************************************************************************************************************/
								//For reading the m1 regional xml file.
								if(prevXmlFile.getName().equalsIgnoreCase(indexXmlFile.getName()) && parentNode.getNodeName().equalsIgnoreCase(REGIONAL_MODULE_NODE_NAME)){
									
									DTOSubmissionMst projectSubmissionDetails = new DTOSubmissionMst();
									Node m1Child = supportingXmlReader.getFirstChild(otherXmlDoc);
									
									Node submissionHdrNode = supportingXmlReader.getFirstChild(m1Child);
									//Node submissionHdrNode = m1Child.removeChild(supportingXmlReader.getFirstChild(m1Child));
									//Read US-Regional Admin Info
									if(m1Child.getNodeName().equals(US_REGIONAL_ROOT_NODE)){
										projectSubmissionDetails=supportingXmlReader.readUSM1AdminDtls(submissionHdrNode);
										//System.out.println("m1--->"+templateNodeDtl.getChildNodeList().get(0).getNodeName());
									}
									//Read EU-Regional Envelopes Info
									else if(m1Child.getNodeName().equals(EU_REGIONAL_ROOT_NODE)){
										projectSubmissionDetails = supportingXmlReader.readEUM1Envelopes(submissionHdrNode);
										System.out.println("m1--->"+templateNodeDtl.getChildNodeList().get(0).getNodeName());
										ectdErrorList.addAll(EUValidator.validateEURegional(otherXmlDoc));
									}
									
									//Removing the header info nodes from regional node.
									m1Child.removeChild(submissionHdrNode);
									//Attach subDetails to workspaceMst
									workSpaceMst.setSubmissionMst(projectSubmissionDetails);
									
									//Read M1 Regional nodes (Recursive Call)
									//level is not increased as no entry for the leaf is required
									currChildNodeDtl = readElementChildren(m1Child,level,currChildNodeDtl,templateNodeDtl);
									
									//Ignoring the current 'leaf' node and adding its children directly to 'm1' node
									for (DTOWorkSpaceNodeDetail m1ChildNodeDlt : currChildNodeDtl.getChildNodeList()) {
										childrenNodeDtlList.add(m1ChildNodeDlt);
									}
									//Nullify currChildNodeDtl so this node won't be added as child 
									currChildNodeDtl = null;
									
								}
								/******************************************************************************************************************************************/
								else{
									//Check whether its an STF or not 
									NodeList stfNodeList = otherXmlDoc.getElementsByTagName(ECTD_STF_ROOT_NODE);
									
									if(stfNodeList.getLength() > 0){
										Node ectdStudyNode = stfNodeList.item(0);
										Node stfXmlNode = currXmlNode;
										File indexXmlParentDir = previousParentDir;
										currChildNodeDtl = supportingXmlReader.readSTF(ectdStudyNode, stfXmlNode, currChildNodeDtl,ectdErrorList,indexXmlParentDir);
										//Setup STF Nodes' ID, ParentID and NodeNos
										setNodesTreeProperty(currChildNodeDtl,currChildNodeDtl.getNodeId(),currChildNodeDtl.getParentNodeId(),currChildNodeDtl.getNodeNo());
									}
									//currChildNodeDtl = null;
								}
							} catch (IOException e) {
								System.out.println(e.getMessage());
								ectdErrorList.add(new EctdError(EctdErrorType.ECTD_ERROR,e.getMessage(),currXmlFile.getAbsolutePath()));
								//return null;
							} catch (SAXException e) {
								System.out.println(e.getMessage());
								ectdErrorList.add(new EctdError(EctdErrorType.ECTD_ERROR,e.getMessage(),currXmlFile.getAbsolutePath()));
								//return null;
							} catch (Exception e) {
								System.out.println("this"+e.getMessage());
								String error = e.getMessage();
								if(error == null){
									error = "";
								}
								ectdErrorList.add(new EctdError(EctdErrorType.ECTD_ERROR,"Unknown Error Detected. "+error,currXmlFile.getAbsolutePath()));
								//return null;
							}
							//Set the previous xml file as current again
							currXmlFile = prevXmlFile; 
							//Set the previous xml parent folder as current again
							xmlParentDir = previousParentDir;
						
						}else if(leafFile.getName().endsWith(".pdf")){
							//Check for PDF specific validations 
							
							String pdfPath = null;
							try {
								pdfPath = leafFile.getCanonicalPath();
							} catch (Exception e1) {
								
								e1.printStackTrace();
							}
							if(pdfPath != null){
								try {
									short pdfStat = PDFUtilities.checkAccessible(pdfPath);

									if(pdfStat == 0){
										ectdErrorList.add(new EctdError(EctdErrorType.ECTD_ERROR,"PDF document is corrupted/encrypted/unaccessible.",pdfPath));
									}
									else if(pdfStat == 1){
										ectdErrorList.add(new EctdError(EctdErrorType.ECTD_ERROR,"PDF document has not full permissions.",pdfPath));
									}
									else if(pdfStat == 2){
										
											boolean pdfTest = PDFUtilities.checkBookmarksValid(pdfPath);
											if(!pdfTest){
												ectdErrorList.add(new EctdError(EctdErrorType.ECTD_WARNING,"PDF document contains invalid bookmarks.",pdfPath));;
											}
											/*
											pdfTest = PDFUtilities.checkPageLayoutAndMode(pdfPath);
											if(!pdfTest){
												ectdErrorList.add(new EctdError(EctdErrorType.ECTD_WARNING,"PDF document has invalid initial view.",pdfPath));;
											}
											*/
										try {
											String pdfVersion = PDFUtilities.checkPDFversion(pdfPath);

											if(!pdfVersion.equals("1.4")){
												ectdErrorList.add(new EctdError(EctdErrorType.ECTD_WARNING,"PDF document has version other than 1.4.",pdfPath));;								
											}
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								} catch (Throwable e) {
									e.printStackTrace();
								}
							}
							else{
								ectdErrorList.add(new EctdError(EctdErrorType.ECTD_ERROR,"PDF document does not exists.",pdfPath));
							}
							
						}
					}
				}
				/************************************************************************************************************************************************/
				//If its a node other than 'leaf'
				else{
					//Match Template Node 
					DTOTemplateNodeDetail templateChildNodeDtl = null;
					try {
						if(childrenTempNodeDtlList != null){
							while(childrenTempNodeDtlList.size() > 0){
								templateChildNodeDtl = childrenTempNodeDtlList.remove(0);
								if (templateChildNodeDtl.getNodeName().equals(currChildNodeDtl.getNodeName())) {
									currChildNodeDtl.setFolderName(templateChildNodeDtl.getFolderName());
									currChildNodeDtl.setNodeName(templateChildNodeDtl.getNodeName());
									currChildNodeDtl.setNodeDisplayName(templateChildNodeDtl.getNodeDisplayName());
									currChildNodeDtl.setPublishedFlag(templateChildNodeDtl.getPublishFlag());
									currChildNodeDtl.setRequiredFlag(templateChildNodeDtl.getRequiredFlag());
									currChildNodeDtl.setNodeTypeIndi(templateChildNodeDtl.getNodeTypeIndi());
									currChildNodeDtl.setRemark(templateChildNodeDtl.getRemark());
									
									//If current Node matches a template node which is repeatable
									if(templateChildNodeDtl.getRequiredFlag() == RequiredFlag.ECTD_REPEATABLE_NODE
											|| templateChildNodeDtl.getRequiredFlag() == RequiredFlag.SAMPLE_NODE){
										
										//If ideal node (e.g. EU Regional specific nodes)
										if(templateChildNodeDtl.getRequiredFlag() == RequiredFlag.SAMPLE_NODE){
											/**Reset the node's required flag to normal node ******************************************************************/
											currChildNodeDtl.setRequiredFlag(RequiredFlag.NORMAL_NODE);
											/********************************************************************************************/
											//Check previous xml sibling node whether it is same or not 
											Node previousSibling = supportingXmlReader.getPreviousSibling(currXmlNode);
											//If previous node is not same then add template node to workspace
											if(previousSibling == null || !previousSibling.getNodeName().equals(currXmlNode.getNodeName())){
												//If previous node is not same or no previous node found then 
												DTOWorkSpaceNodeDetail wsnodeDtl = supportingXmlReader.convertTemplateNodeToWorkspaceNode(templateChildNodeDtl);
												//Set currChildNodeDtl's (Current Node's) NodeNo as new nodes' NodeNo
												setNodesTreeProperty(wsnodeDtl, getNewNodeId(), currParentNodeId, currChildNodeDtl.getNodeNo());
												//Add new node to list 
												childrenNodeDtlList.add(wsnodeDtl);
												//Set new NodeNo for 'currChildNodeDtl'
												currChildNodeDtl.setNodeNo(++nodeNo);
											}
										}
										//Check next xml sibling node whether it is same or not 
										Node nextSibling = supportingXmlReader.getNextSibling(currXmlNode);
										if(nextSibling != null && nextSibling.getNodeName().equals(currXmlNode.getNodeName())){
											//If next node is same then add the removed 'templateChildNodeDtl' again at 0th index
											childrenTempNodeDtlList.add(0, templateChildNodeDtl);
										}
									}
									else if(templateChildNodeDtl.getRequiredFlag() == RequiredFlag.ECTD_STF_NODE){
									}
									//Break the loop as required node is found	
									break;
								}else{
									//If template node not fond in the XML then add it to the workspace directly
									
									DTOWorkSpaceNodeDetail wsnodeDtl = supportingXmlReader.convertTemplateNodeToWorkspaceNode(templateChildNodeDtl);
									//Set currChildNodeDtl's (Current Node's) NodeNo as new nodes' NodeNo
									setNodesTreeProperty(wsnodeDtl, getNewNodeId(), currParentNodeId, currChildNodeDtl.getNodeNo());
									//Add new node to list 
									childrenNodeDtlList.add(wsnodeDtl);
									//Set new NodeNo for 'currChildNodeDtl'
									currChildNodeDtl.setNodeNo(++nodeNo);
								}
							}
						}
					} catch (RuntimeException e) {
						System.out.println("err__:"+e.getMessage());
						e.printStackTrace();
					}
					//Attach Node Attributes(If Any) to Current Child Node Detail.
					ArrayList<DTOTemplateNodeAttrDetail> templateNodeAttrs = null;
					if (templateChildNodeDtl != null) {
						templateNodeAttrs = templateChildNodeDtl.getNodeAttrList();
					}
					supportingXmlReader.setupNodeAttributes(currXmlNode,currChildNodeDtl,templateNodeAttrs,ectdErrorList);
					//Do recursion for getting further children
					currChildNodeDtl = readElementChildren(currXmlNode,level+1,currChildNodeDtl,templateChildNodeDtl);
					//String nodeValue = currChildNodeDtl.getNodeValue();
				}
				
				/*****************************************************************************************************************************************************/
				//Add current Node to the childrenNode List
				if(currChildNodeDtl != null){
					childrenNodeDtlList.add(currChildNodeDtl);
				}
			}
			else if(currXmlNode.getNodeType() == Node.TEXT_NODE){
				if(currXmlNode.getNodeValue() != null && !currXmlNode.getNodeValue().trim().equals("")){
					parentNodeDtl.setNodeValue(currXmlNode.getNodeValue().trim());
					parentNodeDtl.setChildNodeList(new ArrayList<DTOWorkSpaceNodeDetail>());
					return parentNodeDtl;
				}
			}
		}
		//Check if template node has more children left
		if(childrenTempNodeDtlList != null && childrenTempNodeDtlList.size()>0){
			while(childrenTempNodeDtlList.size() > 0){
				DTOTemplateNodeDetail templateChildNodeDtl = childrenTempNodeDtlList.remove(0);
				
				DTOWorkSpaceNodeDetail wsnodeDtl = supportingXmlReader.convertTemplateNodeToWorkspaceNode(templateChildNodeDtl);
				//Setup Node Id Property
				setNodesTreeProperty(wsnodeDtl, getNewNodeId(), currParentNodeId, ++nodeNo);
				
				//Add to list
				childrenNodeDtlList.add(wsnodeDtl);
			}
		}
		
		//Attach children to parentNode
		parentNodeDtl.setChildNodeList(childrenNodeDtlList);
		return parentNodeDtl;
	}
	
	/** Setup Nodes' ID, ParentID and NodeNos */
	private void setNodesTreeProperty(DTOWorkSpaceNodeDetail nodeDtl,int nodeId,int parentID,int nodeNo){
		
		//Set Node Keys
		nodeDtl.setNodeId(nodeId);
		nodeDtl.setParentNodeId(parentID);
		nodeDtl.setNodeNo(nodeNo);
		
		//Setup Node Attributes' NodeId value
		if(nodeDtl.getNodeAttrList() != null){
			for (Iterator<DTOWorkSpaceNodeAttrDetail> iterator = nodeDtl.getNodeAttrList().iterator(); iterator.hasNext();) {
				DTOWorkSpaceNodeAttrDetail nodeAttrDtl = iterator.next();
				nodeAttrDtl.setNodeId(nodeId);
			}
		}
		
		//Setup Children Node Keys
		int i=1;
		if(nodeDtl.getChildNodeList() != null){
			for (Iterator<DTOWorkSpaceNodeDetail> iterator = nodeDtl.getChildNodeList().iterator(); iterator.hasNext();) {
				DTOWorkSpaceNodeDetail childNodeDtl = iterator.next();
				setNodesTreeProperty(childNodeDtl,getNewNodeId(),nodeDtl.getNodeId(),i++);
			}
		}else{
			nodeDtl.setChildNodeList(new ArrayList<DTOWorkSpaceNodeDetail>());
		}
		
	}
	private void setupForECTDReading(DTOTemplateNodeDetail templateRootNodeDtl){
		//Set NodeId.
		currNodeId = 0;
		//Set template info.
		this.templateRootNodeDtl = templateRootNodeDtl;
		//Get Leaf Attributes and Parent Attributes
		
		allParentAttrs = docMgmtImpl.getAttributeDetailByType(AttributeType.PARENT_ATTR);
		ectdErrorList = new ArrayList<EctdError>();
		//Set current Xml File AS index file
		currXmlFile = indexXmlFile;
		//Set workspaceMst
		workSpaceMst = new DTOWorkSpaceMst();
	}
	
	
	private int getNewNodeId(){
		
		//Increment of 'curNodeId' to generate unique nodeIds 
		currNodeId++;
		
		return currNodeId;
	}
	private void validateIndexXmlChecksum(){
		File indexMd5Txt = new File(xmlParentDir,INDEX_MD5_FILE);
		String indexChksum = null;
		try {
			indexChksum = new BufferedReader(new FileReader(indexMd5Txt)).readLine();
		} catch (Exception e1) {
			e1.printStackTrace();
			ectdErrorList.add(new EctdError(EctdErrorType.ECTD_ERROR,EctdErrorMsg.getErrMsgDesc(EctdErrorMsg.FILE_NOT_FOUND),indexMd5Txt.getAbsolutePath()));
		}
		String generatedChksum = md5.getMd5HashCode(indexXmlFile.getAbsolutePath());
		if(indexChksum == null){
			ectdErrorList.add(new EctdError(EctdErrorType.ECTD_ERROR,"Unable to read checksum from "+INDEX_MD5_FILE+".",indexMd5Txt.getAbsolutePath()));
		}else if(generatedChksum == null){
			ectdErrorList.add(new EctdError(EctdErrorType.ECTD_ERROR,EctdErrorMsg.getErrMsgDesc(EctdErrorMsg.UNABLE_TO_GENERATE_CHKSUM),indexXmlFile.getAbsolutePath()));
		}else if(!indexChksum.equals(generatedChksum)){
			ectdErrorList.add(new EctdError(EctdErrorType.ECTD_ERROR,EctdErrorMsg.getErrMsgDesc(EctdErrorMsg.CHKSUM_VAL_NOT_MATCHED),indexXmlFile.getAbsolutePath()));
		}
	}
	
	
	private DTOWorkSpaceNodeDetail readTree(DTOWorkSpaceNodeDetail treeNode,int level){
		/****************************************************************/
		char [] indents = new char[level];
		Arrays.fill(indents, ' ');
		String indenting = Arrays.toString(indents).replaceAll(", ", "");
		indenting = indenting.substring(1, indenting.length()-1);
		/****************************************************************/
		
		System.out.println(indenting+" N"+treeNode.getNodeId()+" P"+treeNode.getParentNodeId()+" #"+treeNode.getNodeNo()+" Node:"+treeNode.getNodeName()+" " +
				"folder:"+treeNode.getFolderName()+" Required flag:"+treeNode.getRequiredFlag());
		if (treeNode.getChildNodeList() != null) {
			for (Iterator<DTOWorkSpaceNodeDetail> iterator = treeNode
					.getChildNodeList().iterator(); iterator.hasNext();) {
				DTOWorkSpaceNodeDetail currNode = iterator.next();
				readTree(currNode, level + 1);
			}
			//if(level>0){
		}
		//treeNode.setChildNodeList(childNodeList);
		//}
		//else{
			//treeNode.setChildNodeList(new ArrayList<DTOWorkSpaceNodeDetail>());
		//}
		return treeNode;
	}
	
	
	public ArrayList<EctdError> getEctdErrorList() {
		return ectdErrorList;
	}
}
