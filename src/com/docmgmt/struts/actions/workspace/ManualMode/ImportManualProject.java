package com.docmgmt.struts.actions.workspace.ManualMode;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.docmgmt.dto.DTOCreateManualProject;
import com.docmgmt.dto.DTOSubmissionInfoEU20Dtl;
import com.docmgmt.dto.DTOSubmissionInfoUSDtl;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeAttribute;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.db.master.ManualModeSeqZipDtl;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.struts.actions.ectdviewer.xmlread.GenerateFullTreeView;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ImportManualProject extends ActionSupport
{
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = null;
	String path, nodetofind, inSequenceNo = "";
	String leafnodetofind;
	public String extraHtmlCode;
	private static final long serialVersionUID = 1L;
	Document m1Doc,m2ToM5Doc;
	private DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	String applicationNumber = "";
	String workSpaceDesc, deptCode, copyRights;
	String locationCode, clientCode, projectCode;
	String templateId;
	public String remark;
	int userCode ;
	String msg;
	String basePublishedFolder;
	File[] upload;
	String[] uploadFileName;
	String[] uploadContentType;
	String  startSequence;
	String ReferenceNo, htmlContent;
	String latestSequence = "";
	String submissionId = "";
	boolean isUSTemplete = false;
	boolean isEUTemplete = false;

	ArrayList<String> previousSequenceNumbers;
	String vWorkspaceId;
	String vParentNodeName = "";	
	int parentNodeId = -1;
	String vFileName = "";
	String vDisplayName = "";
	String region = "";
	CallableStatement proc = null;
	int iUserId ;
	String dossierName;
	String regionName = "";
	
	String m1RepeatNodeList[] = {"specific",
								"m1-3-pi",
								};
	
	ArrayList<String> m2_5_repeatNodeList = new ArrayList<String>
								(Arrays.asList
										("m2-3-s-drug-substance",
										"m2-3-p-drug-product",
										"m2-7-3-summary-of-clinical-efficacy",
										"m3-2-s-drug-substance",
										"m3-2-p-drug-product",
										"m3-2-a-1-facilities-and-equipment",
										"m3-2-a-2-adventitious-agents-safety-evaluation",
										"m3-2-p-4-control-of-excipients",
										"m5-3-5-reports-of-efficacy-and-safety-studies"
										));
			
	String m1_5_repeatNodePrefixList[]  = { 
			"specific",
			"m1-3-pi",
			"pi-doc",
			"m2-3-s",
			"m2-3-p",
			"m2-7-3",
			"m3-2-s",
			"m3-2-p",
			"m3-2-a-1",
			"m3-2-a-2",
			"m3-2-p-4",
			"m5-3-5"};
	
	ArrayList<String> usMultiLevelNodeList = new ArrayList<String>
	(Arrays.asList
			("m1-14-1-draft-labeling",
			"m1-3-1-applicant-information",
			"m1-3-5-patent-exclusivity",
			"m1-3-administrative-information"));
	
	public String execute()
	{
		setUserIdAndCode();
		//generateXML(); To save XML on drive for testing
		getXMLDocuments();
		setApplicationNumber();
		setBasePublisheFolder();
		getLatestSequence(path);
		generatePreviousSequenceNumbers();
		
		docMgmtImpl.insertInternalLableMst(vWorkspaceId,iUserId);
		
		String locationCode = docMgmtImpl.getLocationCode(vWorkspaceId);
		if(locationCode.equals("0001"))
		{
			isUSTemplete = true;
			processExternalDossierUS();
		}
		else if(locationCode.equals("0002"))
		{
			isEUTemplete = true;
			processExternalDossierEU();
		}
		return "success";
	}
	public void createAdvanceManualProject(String workspaceId,String selectedDossierName){
		
		vWorkspaceId=workspaceId;
		dossierName=selectedDossierName;
		setUserIdAndCode();
		//generateXML(); To save XML on drive for testing
		getXMLDocuments();
		setApplicationNumber();
		setBasePublisheFolder();
		getLatestSequence(path);
		generatePreviousSequenceNumbers();
		
		docMgmtImpl.insertInternalLableMst(vWorkspaceId,iUserId);
		
		String locationCode = docMgmtImpl.getLocationCode(vWorkspaceId);
		if(locationCode.equals("0001"))
		{
			isUSTemplete = true;
			processExternalDossierUS();
		}
		else if(locationCode.equals("0002"))
		{
			isEUTemplete = true;
			processExternalDossierEU();
		}
		
		
	}
	
	private void generatePreviousSequenceNumbers() 
	{
		int latestSeq = Integer.parseInt(latestSequence);
		int i = 0;
		previousSequenceNumbers = new ArrayList<String>();
		while(i <= latestSeq)
		{
			if(i <= 9)
			{
				previousSequenceNumbers.add("000" + i);
			}
			else if (i <= 99)
			{
				previousSequenceNumbers.add("00" + i);
			}
			else if(i <= 999)
			{
				previousSequenceNumbers.add("0" + i);
			}
			i++;
		}
	}

	private void setBasePublisheFolder() 
	{
		PropertyInfo objInfo = PropertyInfo.getPropInfo();
		basePublishedFolder  = objInfo.getValue("BasePublishFolder");
	}

	private void setApplicationNumber() 
	{
		try 
		{
			applicationNumber = m1Doc.getElementsByTagName("application-number").item(0).getTextContent();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	private void setUserIdAndCode() 
	{
		iUserId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		userCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
	}

	private void processExternalDossierUS() 
	{
		processM1MultiLevelNodes("m1-14-1-draft-labeling");
		processM1MultiLevelNodes("m1-3-1-applicant-information");
		
		processM1MultiLevelNodes("m1-3-5-patent-exclusivity");
		processM1MultiLevelNodes("m1-3-administrative-information");
		processM1Node();
		processM2ToM5();
		
		docMgmtImpl.updateLastPublishedVersion(vWorkspaceId,latestSequence);
		setSubmissionInfoUs();
	}

	private void setSubmissionInfoUs() 
	{
		int previousSeqCount = previousSequenceNumbers.size();
		String currentSeq = "";
		String previousSeq;// = "-999";
		int id = 0;
		for(int i = 0; i <= previousSeqCount ; i++)
		{
			currentSeq = previousSequenceNumbers.get(i);
			previousSeq = currentSeq; // Consider as confirm

			id = Integer.parseInt(ManualModeSeqZipDtl.getMaxId("submissionInfoUsDtl","vSubmissionInfoUSDtlId")) + 1;
			DTOSubmissionInfoUSDtl objDTOSubmissionInfoUSDtl = new DTOSubmissionInfoUSDtl();
			
			objDTOSubmissionInfoUSDtl.setSubmissionInfoUSDtlId(getFourDigitStringNumber(id));
			objDTOSubmissionInfoUSDtl.setWorkspaceId(vWorkspaceId);
			objDTOSubmissionInfoUSDtl.setCountryCode("0003");
			objDTOSubmissionInfoUSDtl.setCurrentSeqNumber(currentSeq);
			objDTOSubmissionInfoUSDtl.setLastPublishedVersion(previousSeq);
			objDTOSubmissionInfoUSDtl.setSubmissionPath(((basePublishedFolder + "/" + vWorkspaceId + "/" + id + "/" + applicationNumber )));
			objDTOSubmissionInfoUSDtl.setSubmitedOn((new Timestamp(new Date().getTime())));
			objDTOSubmissionInfoUSDtl.setSubmitedBy(iUserId);
			objDTOSubmissionInfoUSDtl.setSubmissionType("");
			objDTOSubmissionInfoUSDtl.setDateOfSubmission((new Timestamp(new Date().getTime())));
			objDTOSubmissionInfoUSDtl.setRelatedSeqNo(getRelatedSequence(currentSeq));
			objDTOSubmissionInfoUSDtl.setConfirm('Y');
			objDTOSubmissionInfoUSDtl.setModifyOn((new Timestamp(new Date().getTime())));
			objDTOSubmissionInfoUSDtl.setModifyBy(iUserId);
			objDTOSubmissionInfoUSDtl.setStatusIndi('N');
			objDTOSubmissionInfoUSDtl.setLabelId("L0001");
			objDTOSubmissionInfoUSDtl.setApplicationNo(applicationNumber);
			objDTOSubmissionInfoUSDtl.setSubmissionMode("ESG");
			
			docMgmtImpl.insertSubmissionInfoUSDtl(objDTOSubmissionInfoUSDtl, 1);
		}
	}
	
	private void setSubmissionInfoEu() 
	{
		int previousSeqCount = previousSequenceNumbers.size();
		String currentSeq = "";
		String previousSeq;
		int id = 0;
		for(int i = 0; i < previousSeqCount ; i++)
		{
			currentSeq = previousSequenceNumbers.get(i);
			previousSeq = currentSeq; // Consider as confirm

			id = Integer.parseInt(ManualModeSeqZipDtl.getMaxId("submissionInfoEU20Dtl","vSubmissionInfoEU20DtlId")) + 1;
			DTOSubmissionInfoEU20Dtl objDTOSubmissionInfoEU20Dtl = new DTOSubmissionInfoEU20Dtl();
			
			objDTOSubmissionInfoEU20Dtl.setSubmissionInfoEU20DtlId(getFourDigitStringNumber(id));
			objDTOSubmissionInfoEU20Dtl.setWorkspaceId(vWorkspaceId);
			objDTOSubmissionInfoEU20Dtl.setCountryCode("");//TODO
			objDTOSubmissionInfoEU20Dtl.setCurrentSeqNumber(currentSeq);
			objDTOSubmissionInfoEU20Dtl.setLastPublishedVersion(previousSeq);
			objDTOSubmissionInfoEU20Dtl.setSubmissionPath((basePublishedFolder + "/" + vWorkspaceId + "/" + id + "/" + applicationNumber));
			objDTOSubmissionInfoEU20Dtl.setSubmitedOn((new Timestamp(new Date().getTime())));
			objDTOSubmissionInfoEU20Dtl.setSubmitedBy(iUserId);
			objDTOSubmissionInfoEU20Dtl.setSubmissionType(""); //TODO
			objDTOSubmissionInfoEU20Dtl.setDateOfSubmission((new Timestamp(new Date().getTime())));
			objDTOSubmissionInfoEU20Dtl.setRelatedSeqNo(getRelatedSequence(currentSeq));
			objDTOSubmissionInfoEU20Dtl.setConfirm('Y');
			objDTOSubmissionInfoEU20Dtl.setModifyOn((new Timestamp(new Date().getTime())));
			objDTOSubmissionInfoEU20Dtl.setModifyBy(iUserId);
			objDTOSubmissionInfoEU20Dtl.setStatusIndi('N');
			objDTOSubmissionInfoEU20Dtl.setLabelId("L0001");
			objDTOSubmissionInfoEU20Dtl.setSubmissionMode("ESG");
			objDTOSubmissionInfoEU20Dtl.setSubVariationMode("single");
			objDTOSubmissionInfoEU20Dtl.setRMSSubmited('Y');
			objDTOSubmissionInfoEU20Dtl.setTrackingNo(applicationNumber);
			
			docMgmtImpl.insertSubmissionInfoEU20Dtl(objDTOSubmissionInfoEU20Dtl, 1);
		}
	}
	
	private String getRelatedSequence(String sequenceNum) 
	{
		String sequences = "";
		if(sequenceNum.equals("0000") == false)
		{
			PropertyInfo objInfo = PropertyInfo.getPropInfo();
			String manualProjectsServerPath  = objInfo.getValue("ManualProjectsServerPath");
			manualProjectsServerPath+="/"+dossierName;
			if(isUSTemplete)
			manualProjectsServerPath += "/" + sequenceNum + "/m1/us/us-regional.xml";
			else if(isEUTemplete)
				manualProjectsServerPath += "/" + sequenceNum + "/m1/eu/eu-regional.xml";
			File fXmlFile = new File(manualProjectsServerPath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			Document doc = null;
			try 
			{
				dBuilder = dbFactory.newDocumentBuilder();
				doc = dBuilder.parse(fXmlFile);
			} 
			catch (Exception e1) 
			{
				e1.printStackTrace();
			}
			
			doc.getDocumentElement().normalize();
			NodeList relatedSequences = doc.getElementsByTagName("related-sequence-number");
			int relatedSequenceCounts = relatedSequences.getLength();
			
			for(int i = 0; i < relatedSequenceCounts; i++)
			{
				if(i != 0)
				{
					sequences += "," + relatedSequences.item(i).getTextContent();
				}
				else
				{
					sequences += relatedSequences.item(i).getTextContent();
				}
			}
		}
		
		return sequences;
	}

	public String getFourDigitStringNumber(int i)
	{
		String number = "";
		if (i <= 9)
		{
			number = "000" + i;
		}
		else if (i <= 99)
		{
			number = "00" + i;
		}
		else if (i <= 999)
		{
			number = "0" + i;
		}
		else
		{
			number = "" + i;
		}
			 
		return number;
	}
	
	private void generateXML() 
	{
		GenerateFullTreeView gftv = new GenerateFullTreeView();
		ArrayList<Document> mergedDocs = gftv.getMergedXMLDocument("//90.0.0.15/docmgmtandpub//ectdviewer/unziped//012345");
	
		Document  regionalDoc,mergedDoc;
		regionalDoc = mergedDocs.get(0);	
		mergedDoc  = mergedDocs.get(1);
		
		try
		{
			Transformer transformer = TransformerFactory.newInstance()
					.newTransformer();
			Result output = new StreamResult(new File("D://m1.xml"));
			Source input = new DOMSource(regionalDoc);
			transformer.transform(input, output);
			
			Result outputM1 = new StreamResult(new File("D://mergedindex.xml"));
			Source inputM1 = new DOMSource(mergedDoc);
			transformer.transform(inputM1, outputM1);
		} 
		catch (Exception e)
		{
			System.out.println("Error");
			e.printStackTrace();
		} 
	}

	public void processExternalDossierEU()
	{
		processM1MultiLevelNodes("m1-3-pi");
		processM1Node();
		processM2ToM5();
		docMgmtImpl.updateLastPublishedVersion(vWorkspaceId,latestSequence);
		setSubmissionInfoEu();
	}

	private void processM2ToM5() 
	{
		processRepeatNodes();
		processNonRepeatNodes();
	}

	private void processM1Node() 
	{
		try 
		{
			Node m1 = null;
			ArrayList<Node> firstLevelChild = null;
			if(isEUTemplete)
			{
				m1 = m1Doc.getElementsByTagName("m1-eu").item(0);
				firstLevelChild  = getElementNodeList(m1.getChildNodes());
				firstLevelChild.remove(2); //Remove already processed nodes
			}
			else if(isUSTemplete)
			{
				m1 = m1Doc.getElementsByTagName("m1-regional").item(0);
				firstLevelChild  = getElementNodeList(m1.getChildNodes());
			
				//Remove already processed nodes
				firstLevelChild.remove(new String("m1-14-1-draft-labeling"));
				firstLevelChild.remove(new String("m1-3-1-applicant-information"));
				firstLevelChild.remove(new String("m1-3-5-patent-exclusivity"));
				firstLevelChild.remove(new String("m1-3-administrative-information"));
			}
			
			int index = 0;
			int firstLevelChildListCount = firstLevelChild.size();
			for(index = 0; index < firstLevelChildListCount; index++ ) // cover  
			{	
					Node currentNode =  firstLevelChild.get(index);
					System.out.println("Processing.......*." + currentNode.getNodeName());
					ArrayList<Node> currentNodeChildList = getElementNodeList(currentNode.getChildNodes());
					
					int currentNodeChildListCount = currentNodeChildList.size();
					for(int subIndex = 0; subIndex < currentNodeChildListCount; subIndex++ ) // Specific n other
					{
						Node currentSubNode = currentNodeChildList.get(subIndex);
						
						String currentSubNodeName = currentSubNode.getNodeName();
						int temp = getIdFromNodeName(0,currentNode.getNodeName().toString());
						if(currentSubNodeName.equals("leaf"))
						{
							processLeaf(currentSubNode, temp);
						}
						else
						{
							int refId = getIdFromNodeName(temp, currentSubNode.getNodeName().toString());
							if(isRepeatNode(currentSubNodeName) == true)	
							{
								int newNodeId = repeatNode(refId, currentSubNode);
								processRepeatLeafNodes(newNodeId,currentSubNode, true);
							}
							else
							{
								if(currentNode.getNodeName().startsWith("m1-3") == false)
								{ 
									ArrayList<Node> leafChild  = getElementNodeList(currentSubNode.getChildNodes());
									for(int i = 0 ; i < leafChild.size(); i++)
									{
										processLeaf(leafChild.get(i), refId);
									}
								}
							}
						}
					}
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	
	private void processM1MultiLevelNodes(String nodeName) 
	{
		Node m1 = m1Doc.getElementsByTagName(nodeName).item(0);
		if(m1 == null)
		{
			return;
		}
		ArrayList<Node> firstLevelChild = getElementNodeList(m1.getChildNodes());
		int index = 0;
		int firstLevelChildListCount = firstLevelChild.size();
		for(index = 0; index < firstLevelChildListCount; index++ )   
		{	
			Node currentNode =  firstLevelChild.get(index);
			System.out.println("Processing.......*." + currentNode.getNodeName());
			if(usMultiLevelNodeList.contains(currentNode.getNodeName()))
			{
				continue;
			}
			ArrayList<Node> currentNodeChildList = getElementNodeList(currentNode.getChildNodes());
			
			int currentNodeChildListCount = currentNodeChildList.size();
			for(int subIndex = 0; subIndex < currentNodeChildListCount; subIndex++ ) // Specific n other
			{
				Node currentSubNode = currentNodeChildList.get(subIndex);
				String currentSubNodeName = currentSubNode.getNodeName();
				int temp = getIdFromNodeName(0,currentNode.getNodeName().toString());
				if(currentSubNodeName.equals("leaf"))
				{
					processLeaf(currentSubNode, temp);
				}
				else
				{
					int refId = getIdFromNodeName(temp, currentSubNode.getNodeName().toString());
					int newNodeId = repeatNode(refId, currentSubNode);
				
					if(isRepeatNode(currentSubNodeName) == true)	
					{
						processRepeatLeafNodes(newNodeId,currentSubNode, true);
					}
				}
			}
		}
	}
	
	private void getXMLDocuments()
	{
		PropertyInfo objInfo = PropertyInfo.getPropInfo();
		path = objInfo.getValue("ManualProjectsServerPath");
		
		path=path+"/"+dossierName;
		GenerateFullTreeView objFullTreeView = new GenerateFullTreeView();
		
		System.out.println(path);
		ArrayList<Document> docs =  objFullTreeView.getMergedXMLDocument(path);
		
		m2ToM5Doc = docs.get(1);
		m1Doc = docs.get(0);
	}

	private void processRepeatNodes() 
	{
		int newNodeId =  0;
		try 
		{	
			int repeatNodeId = 0;
			NodeList list;
			for(String nodeToRepeat : m2_5_repeatNodeList)
			{
				System.out.println("Processing ........." + nodeToRepeat);
				list = m2ToM5Doc.getElementsByTagName(nodeToRepeat);

				int nodeCount = list.getLength();
				if(nodeCount > 1)
				{
					repeatNodeId = docMgmtImpl.getRepeatNodeId(vWorkspaceId,nodeToRepeat);
					
					System.out.println("Processing ........." + nodeToRepeat + "Id: " + repeatNodeId);
					for(int i = 0; i < nodeCount;i++)
					{
						Node node=list.item(i);
						if(node != null)
						{
								if(nodeToRepeat.equals("m3-2-p-4-control-of-excipients"))
								{	
									newNodeId = processM3_2_p_4(repeatNodeId,node);
									processRepeatLeafNodes(newNodeId,node,true);
								}
								else
								{
									newNodeId = repeatNode(repeatNodeId,node);
									processRepeatLeafNodes(newNodeId,node, false);
								}
							}
						}
				}
			}
		} 
		catch (Exception e1) 
		{
			e1.printStackTrace();
		}
	}

	private int repeatNode(int repeatNodeId, Node nodeToRepeat) 
	{
		int attrCount = nodeToRepeat.getAttributes().getLength();
		DTOWorkSpaceNodeAttribute wsnadto = new DTOWorkSpaceNodeAttribute();
		DTOWorkSpaceNodeDetail repeatNodeDetail = docMgmtImpl.getNodeDetail(vWorkspaceId, repeatNodeId).get(0);
		int  newNodeId =0;
		//all childs for the parent of repeatnodeid
		Vector<DTOWorkSpaceNodeDetail> allChilds = docMgmtImpl.getChildNodeByParent(repeatNodeDetail.getParentNodeId(),vWorkspaceId);
		System.out.println("allChilds" + allChilds.size());	
		for (int eachChild = 0 ; eachChild < allChilds.size() ; eachChild ++)
		{
			DTOWorkSpaceNodeDetail child = allChilds.get(eachChild);
			System.out.println("current child" + child.getNodeId());
			if (!child.getNodeName().equals(repeatNodeDetail.getNodeName()) && child.getNodeName().startsWith("m1") == false)
			{
				System.out.println("skipping " + child.getNodeName());
				continue;
			}

			//Creating new Section from the source Section
			newNodeId = docMgmtImpl.getmaxNodeId(vWorkspaceId)+1;
	    	
			if(repeatNodeDetail.getRequiredFlag() == 'S')
			{
				docMgmtImpl.CopyAndPasteWorkSpace(vWorkspaceId,repeatNodeId,vWorkspaceId,repeatNodeDetail.getParentNodeId(),iUserId,"1");//Status = '1' for 'Add Node Last'
				//		upddate folderName
				{
					NamedNodeMap attributes = nodeToRepeat.getAttributes();
					System.out.println(attributes.getLength());
					try
					{
						for(int  i = 0 ; i < attributes.getLength(); i++)
						{
							System.out.println(attributes.item(i));
						}
					}
					catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
			}
			else
			{
				docMgmtImpl.CopyAndPasteWorkSpace(vWorkspaceId,repeatNodeId,vWorkspaceId,repeatNodeId,iUserId,"2");//Status = '2' for 'Add Node After'
			}
						
	    	NamedNodeMap attributes = nodeToRepeat.getAttributes();
			for(int i=0;i<attrCount;i++)
			{
	    		String attributeItem = attributes.item(i).toString();
	    		String attributeName = attributeItem.substring(0,attributeItem.indexOf("=")); 
				String sttributeValue = attributeItem.substring(attributeItem.indexOf("\"") + 1,attributeItem.length()-1);
	    		
	    		wsnadto.setWorkspaceId(vWorkspaceId);
		    	wsnadto.setNodeId(newNodeId);
		    	wsnadto.setAttrId(docMgmtImpl.getAttribuuteIdByName(attributeName));
		    	wsnadto.setAttrName(attributeName);
		    	wsnadto.setAttrValue(sttributeValue);
		    	wsnadto.setModifyBy(iUserId);
			    
		    	docMgmtImpl.updateWorkspaceNodeAttributeValue(wsnadto);
			}
			break;
		}
		return newNodeId;
	}
	
	private void processRepeatLeafNodes(int newNodeId, Node node,boolean flag) 
	{
		ArrayList<Node> childNodes = getElementNodeList(node.getChildNodes());
		int childNodeCout = childNodes.size();
		if(childNodeCout > 0)
		{
			if(childNodes.get(0).getNodeName().equals( "leaf") )
			{
				for(int i = 0; i < childNodeCout; i++)
 				{
					if(childNodes.get(i).getNodeName().equals("leaf") )
					{
						processLeaf(childNodes.get(i),newNodeId);
					}
 				}
			}
			else
			{
 				for(int i = 0; i < childNodeCout; i++)
 				{
 					if(childNodes.get(i).getNodeName().startsWith("m3-2-p-4") == true && flag == false)
 					{
 						continue;
 					}
 					int nodeId = getIdFromNodeName(newNodeId, childNodes.get(i).getNodeName().toString());
					processRepeatLeafNodes(nodeId, childNodes.get(i),flag);
 				}
			}
		}
	}

	private int getIdFromNodeName(int parentId, String nodeName) 
	{
		return docMgmtImpl.getIdFromNodeName(parentId, nodeName, vWorkspaceId);
	}
	
	private int processM3_2_p_4(int repeatNodeId, Node node)
	{
		int newNodeId = 0;

		String dbAttributeName = "";
		String dbAttributeValue = "";
		
		String XMLAttrName = "";
		String XMLAttrValue = "";

		//		1) Get list of parent id
		ArrayList<String> parentIds = docMgmtImpl.getParentIdFromNodeNameAndWorkspaceId(vWorkspaceId,"m3-2-p-drug-product");
		
		try
		{
			NamedNodeMap parentNodeXMLAttr = node.getParentNode().getAttributes();

			int matchedCount;

			for(String parentId : parentIds)
			{
//				2) get attribute detail of all parent node

				ArrayList<DTOWorkSpaceNodeAttrDetail> workSpaceNodeAttrDetail = docMgmtImpl.getAttributeDetailfromWorkspaceIdAndNodeId(vWorkspaceId,parentId);
				int attrCount = workSpaceNodeAttrDetail.size();

				if(parentNodeXMLAttr.getLength() == attrCount)
				{
					matchedCount = 0;
//					3) compare each attribute and get final parent id
					//while(rs1.next())
					for(int i = 0; i <= attrCount; i++)
					{
						dbAttributeName = workSpaceNodeAttrDetail.get(i).getAttrName();
						dbAttributeValue = workSpaceNodeAttrDetail.get(i).getAttrValue();
						
						XMLAttrValue = getAttributeValue(parentNodeXMLAttr.getNamedItem(dbAttributeName).toString());
						XMLAttrName = getAttributeName(parentNodeXMLAttr.getNamedItem(dbAttributeName).toString());
						
						if(XMLAttrValue.equals(dbAttributeValue) == true && XMLAttrName.equals(dbAttributeName))
						{
							System.out.println("ndoeToRepeat : " + repeatNodeId + "Parent Id : " + parentId);
							System.out.println("AttrName : " + dbAttributeName + "Parent Id : " + XMLAttrName);
							System.out.println("AttrValue : " + dbAttributeValue + "Parent Id : " + XMLAttrValue);
							matchedCount++;
						
							if(matchedCount == attrCount)
							{
								int refId = docMgmtImpl.getReferenceId(vWorkspaceId,"m3-2-p-4-control-of-excipients",parentId);
								newNodeId = repeatNode(refId, node);
							}
						}
					}
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return newNodeId;
	}
	
	private String getAttributeName(String attributeItem)
	{
		String attributeName = attributeItem.substring(0,attributeItem.indexOf("=")); 
		return attributeName;
	}
	
	private String getAttributeValue(String attributeItem)
	{
		String sttributeValue = attributeItem.substring(attributeItem.indexOf("\"") + 1,attributeItem.length()-1);
		return sttributeValue;
	}
	
	private void processNonRepeatNodes()
	{
		m2ToM5Doc.getDocumentElement().normalize();
		if (m2ToM5Doc.hasChildNodes()) 
		{
			System.out.println("Root ==> " + m2ToM5Doc.getChildNodes().getLength() );
			if(isEUTemplete == true)
				{
					DTOSubmissionInfoEU20Dtl dto = new DTOSubmissionInfoEU20Dtl();
					
					dto.setSubmissionInfoEU20DtlId("");
					dto.setWorkspaceId(vWorkspaceId);
					dto.setCountryCode("2");
					dto.setCurrentSeqNumber(latestSequence);
					dto.setLastPublishedVersion(latestSequence);
					
					dto.setSubmissionPath(path);
					dto.setSubmitedBy(iUserId);
					dto.setSubmissionType("");
					dto.setDateOfSubmission((new Timestamp(new Date().getTime())));
					dto.setRelatedSeqNo("");
					dto.setConfirm('Y');
					dto.setModifyBy(iUserId);
					dto.setLabelId("L0001");
					dto.setSubmissionMode("ESG");
					dto.setSubVariationMode("single");
					dto.setRMSSubmited('Y');
					dto.setTrackingNo(inSequenceNo);
					
					docMgmtImpl.insertSubmissionInfoEU20Dtl(dto, 1);
				}
				else if(isUSTemplete == true)
				{
					DTOSubmissionInfoUSDtl dto = new DTOSubmissionInfoUSDtl();
					
					dto.setSubmissionInfoUSDtlId("");
					dto.setWorkspaceId(vWorkspaceId);
					dto.setCountryCode("2");
					dto.setCurrentSeqNumber(latestSequence);
					dto.setLastPublishedVersion(latestSequence);
					dto.setSubmissionPath(path);
					dto.setSubmitedBy(iUserId);
					dto.setSubmissionType("");
					dto.setDateOfSubmission((Timestamp) new Date());
					dto.setRelatedSeqNo("");
					dto.setConfirm('Y');
					dto.setModifyBy(iUserId);
					dto.setLabelId("L0001");
					dto.setSubmissionMode("ESG");
					dto.setApplicationNo(applicationNumber);
					
					docMgmtImpl.insertSubmissionInfoUSDtl(dto, 1);
				}
				processChilNodes(m2ToM5Doc.getChildNodes());
		}	
	}
	
	private void processChilNodes(NodeList nodeList)
	{
		boolean isRepeatNode = false;
		
		for (int count = 0; count < nodeList.getLength(); count++) 
		{
			Node currentNode = nodeList.item(count);

			if (currentNode.getNodeType() == Node.ELEMENT_NODE)
			{
				if(currentNode.getNodeName().equals("leaf")  && currentNode.getNodeName().startsWith("m1") == false) 
				{
					Node parentNode = currentNode.getParentNode();

					vParentNodeName = parentNode.getNodeName();
					isRepeatNode = isRepeatNode(vParentNodeName);
					
					if(vParentNodeName.startsWith("m1") == false && isRepeatNode == false)
					{
						parentNodeId = docMgmtImpl.getRepeatNodeId(vWorkspaceId, parentNode.getNodeName());
						processLeaf(currentNode,parentNodeId);
					}
				}
				if (currentNode.hasChildNodes()) 
				{
					processChilNodes(currentNode.getChildNodes());
				}
			}
		}
	}
	
	private boolean isRepeatNode(String parentNodeName)
	{
		boolean isRepeatNode = false;
		for(String repeatNode: m1_5_repeatNodePrefixList)
		{
			if(parentNodeName.startsWith(repeatNode))
			{
				isRepeatNode = true;
				break;
			}
		}
		return isRepeatNode; 
	}
	
	private void processLeaf(Node currentNode, int parentNodeId) 
	{
 		if(currentNode.getNodeType() != Node.ELEMENT_NODE || currentNode.getNodeName().equals("leaf") == false)
		{
			return;
		}
		NamedNodeMap nodeMap = currentNode.getAttributes();

		Node fileName = null;
		try 
		{
			fileName = nodeMap.getNamedItem("xlink:href");
			Node  titleNode = null;
			NodeList leafChildNodes = currentNode.getChildNodes();
			int leafChildNodesCount = leafChildNodes.getLength();
			for(int i = 0; i < leafChildNodesCount; i++)
			{
				titleNode = leafChildNodes.item(i);
				if(titleNode.getNodeName().equals("title"))
				{
					vDisplayName = titleNode.getTextContent();
					break;
				}
			}
		}
		catch (Exception e1) 
		{		
			System.out.println("Parent Node " + currentNode.getParentNode().getNodeName());
			e1.printStackTrace();
		}
		
		if(fileName != null)
		{
			vFileName = nodeMap.getNamedItem("xlink:href").toString();
			int index = vFileName.lastIndexOf("/");
			vFileName = vFileName.substring(index + 1, vFileName.length() - 1);
		}		
		DTOCreateManualProject objDtoCreateManualProject = new DTOCreateManualProject();
		
		objDtoCreateManualProject.setWorkspaceId(vWorkspaceId);
		objDtoCreateManualProject.setNodeName(currentNode.getNodeName());
		objDtoCreateManualProject.setParentNodeName(currentNode.getParentNode().getNodeName());
		objDtoCreateManualProject.setParentNodeId(parentNodeId);
		objDtoCreateManualProject.setFileName(vFileName);
		objDtoCreateManualProject.setDisplayName(vDisplayName);
		objDtoCreateManualProject.setSequenceNumber(inSequenceNo);
		objDtoCreateManualProject.setOperation( nodeMap.getNamedItem("operation").toString());
		objDtoCreateManualProject.setUserId(iUserId);
		objDtoCreateManualProject.setUserGroupCode(userCode);
		objDtoCreateManualProject.setLocationName(regionName);
		objDtoCreateManualProject.setLatestSequence(latestSequence);
		objDtoCreateManualProject.setSubmissionId(submissionId);
		objDtoCreateManualProject.setOperation(nodeMap.getNamedItem("ID").toString().substring(4,nodeMap.getNamedItem("ID").toString().length()-1));
	
		docMgmtImpl.createManualProject(objDtoCreateManualProject);
	}
	
	private  void getLatestSequence(String path)
	{
		File objFile = new File(path);
		String[] subFolders = objFile.list();

		List<String> dirList = Arrays.asList(subFolders);
		Collections.sort(dirList);
		latestSequence = getFourDigitStringNumber(Integer.parseInt(dirList.get(dirList.size() - 1)));
	}

	private ArrayList<Node> getElementNodeList(NodeList nodeList)
	{
		ArrayList<Node> elementNodeList= new ArrayList<Node>();
		
		for(int index = 0; index < nodeList.getLength(); index++)
		{
			if(nodeList.item(index).getNodeType() == Node.ELEMENT_NODE)
			{
				elementNodeList.add(nodeList.item(index));
			}
		}
		return elementNodeList;
	}
	
	public String getvWorkspaceId() 
	{
		return vWorkspaceId;
	}

	public void setvWorkspaceId(String vWorkspaceId) 
	{
		this.vWorkspaceId = vWorkspaceId;
	}
}
	