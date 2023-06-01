package com.docmgmt.struts.actions.RepeatSection;



import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.docmgmt.dto.DTOAttributeMst;
import com.docmgmt.dto.DTOTimelineWorkspaceUserRightsMst;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeAttribute;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.server.webinterface.entities.Node.NodeTypeIndi;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class RepeatSectionAction extends ActionSupport  {
	
	public int userCode;
	private static final long serialVersionUID = 1L;
	public String str ;
	public String result1;
	public String finalString;
	public String[] tempNames;
	public String nodeNames;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public String location_name;
	
	public int nodeHistorySize;
	
	public String alloweTMFCustomization;
	
	public int nId;
	
	public String usertypecode;
	public String usertypename;
	
	public Vector nodeHistory;
	public int stageId;
	public String senfForReview;
	Vector<DTOWorkSpaceUserRightsMst> hoursData=new Vector<>();
	Vector<DTOWorkSpaceUserRightsMst> beforeNodeTree=new Vector<>();
	Vector<DTOWorkSpaceUserRightsMst> afterNodeTree=new Vector<>();
	Vector<DTOWorkSpaceUserRightsMst> getSRFlagData=new Vector<>();
	Vector<DTOWorkSpaceUserRightsMst> getRepeatAdjustDate=new Vector<>();
	Vector<DTOWorkSpaceUserRightsMst> getAdjustDateData=new Vector<>();
	
	public Vector<DTOWorkSpaceNodeAttribute> attrDtl;
	public String workspaceID;
	public int newRepetedNodeId;
	public int nodeId;
	public String folderName;
	public int scriptNodeId;
	public String lbl_folderName;
	public String lbl_nodeName;
	PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
	public String scriptCode;
	
	public String Automated_TM_Required;
	public String Automated_Doc_Type;
	public boolean showAutomateButton=false;
	public Vector <DTOWorkSpaceNodeDetail> URSTracebelityMatrixDtl=new Vector();
	KnetProperties knetProperties = KnetProperties.getPropInfo();
	public String isScriptCodeAutoGenrate;
	
	@Override
	public String execute()
	{
		userCode=Integer.parseInt(ActionContext.getContext().getSession()
				.get("userid").toString());
		usertypecode = ActionContext.getContext().getSession().get("usertypecode").toString();
		usertypename = ActionContext.getContext().getSession().get("usertypename").toString();
		
		lbl_folderName = propertyInfo.getValue("ForlderName");
		lbl_nodeName = propertyInfo.getValue("NodeName");	
		alloweTMFCustomization = propertyInfo.getValue("ETMFCustomization");
		alloweTMFCustomization= alloweTMFCustomization.toLowerCase();
		
		if(removeNodeId != 0){
			removeSection();
		}
		String workspaceID=ActionContext.getContext().getSession().get("ws_id").toString();
		
		//added for gcc
		//Vector<Object[]> wsDetail = docMgmtImpl.getWorkspaceDesc(workspaceID);
		Vector<Object[]> wsDetail = docMgmtImpl.getWorkspaceDescList(workspaceID);
		
		if(wsDetail != null){
			Object[] record = wsDetail.elementAt(0);
			if(record != null)
			{ 
				location_name=record[2].toString();
			}
		}
		
		//Vector childNodes = docMgmtImpl.getChildNodeByParent(repeatNodeId, workspaceID);
		int isLeafNode = docMgmtImpl.isLeafNodes(workspaceID, repeatNodeId);
		
		if(isLeafNode == 0) { //User has selected a section to repeat
			isSection = true;
			repeatNodeAttrDtl=docMgmtImpl.getAttributeDetailForDisplay(workspaceID,repeatNodeId);
		}
		else { //User has selected a Leaf Node to repeat
			isSection = false;
			
		}
		
		sourceNodeDetail = docMgmtImpl.getNodeDetail(workspaceID, repeatNodeId).get(0);
		finalString = sourceNodeDetail.getNodeDisplayName();
		if(finalString.contains("{")){
		int startingIndex = finalString.indexOf("{");
		int closingIndex = finalString.indexOf("}");
		result1 = finalString.substring(startingIndex , closingIndex+1);
		
		System.out.println(result1);
		finalString=finalString.replace(result1, "  ");
		System.out.println(finalString);
		}
		else{
			finalString = sourceNodeDetail.getNodeDisplayName();
		}
		originalNodeWithAllclones = new Vector<DTOWorkSpaceNodeDetail>();
		int parentNodeId= docMgmtImpl.getParentNodeId(workspaceID, repeatNodeId);
		
		Vector<DTOWorkSpaceNodeDetail> repeatNodeAndSiblingsDtl = docMgmtImpl.getChildNodeByParent(parentNodeId, workspaceID);
		
		for(int i = 0; i < repeatNodeAndSiblingsDtl.size(); i++) {
			DTOWorkSpaceNodeDetail currentSibling = repeatNodeAndSiblingsDtl.get(i);
			//System.out.println(currentSibling.getNodeName());
			if(currentSibling.getNodeName().equalsIgnoreCase(sourceNodeDetail.getNodeName())) {
				
				DTOWorkSpaceNodeDetail repetnodesibling = (DTOWorkSpaceNodeDetail) repeatNodeAndSiblingsDtl
						.get(i);
				nodeNames=repetnodesibling.getNodeDisplayName();
				if(nodeNames.contains("{")){
					int startingIndex = nodeNames.indexOf("{");
					int closingIndex = nodeNames.indexOf("}");
					result1 = nodeNames.substring(startingIndex , closingIndex+1);
										
					nodeNames=nodeNames.replace(result1, "  ");
					
					repetnodesibling.setNodeDisplayName(nodeNames);
					}
				
				originalNodeWithAllclones.addElement(repetnodesibling);
			}
		}
		//System.out.println("size vector:"+originalNodeWithAllclones.size());
		/*for(int i=0;i<originalNodeWithAllclones.size();i++){
			tempNames=new String[originalNodeWithAllclones.size()];
			nodeNames=new String[originalNodeWithAllclones.size()];
			tempNames[i]=originalNodeWithAllclones.get(i).getNodeDisplayName();
		}
		for(int i=0;i<nodeNames.length;i++){
			System.out.println(nodeNames[i]);
		}*/
		
		nodeHistory = docMgmtImpl.getNodeHistory(workspaceID, repeatNodeId);
	 	if(nodeHistory.size()>0){
		DTOWorkSpaceNodeHistory lastnodedetail = (DTOWorkSpaceNodeHistory)nodeHistory.get(nodeHistory.size()-1);
		stageId =lastnodedetail.getStageId();
	 	}
	 	senfForReview = docMgmtImpl.checkSendForReview(workspaceID,repeatNodeId);
		return SUCCESS;
	}
	
	public String saveSection() throws ParseException, ClassNotFoundException {
		
		String workspaceID=ActionContext.getContext().getSession().get("ws_id").toString();
		int userId=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		if(isSection) {//Repeat Section
			
			HttpServletRequest request = ServletActionContext.getRequest();
			
			DTOWorkSpaceNodeAttribute wsnadto = new DTOWorkSpaceNodeAttribute();
			String AttributeValueForNodeDisplayName="";
			
			
			DTOWorkSpaceNodeDetail repeatNodeDetail = docMgmtImpl.getNodeDetail(workspaceID, repeatNodeId).get(0);
			
			//all childs for the parent of repeatnodeid
			Vector<DTOWorkSpaceNodeDetail> allChilds = docMgmtImpl.getChildNodeByParent(repeatNodeDetail.getParentNodeId(),workspaceID);
			System.out.println("allChilds" + allChilds.size());
			for (int eachChild = 0 ; eachChild < allChilds.size() ; eachChild ++)
			{
				//for each child check attributes
				DTOWorkSpaceNodeDetail child = allChilds.get(eachChild);
			//	System.out.println("current child" + child.getNodeId());
				//if (child.getNodeId() == repeatNodeId)
				//	continue;
				if (!child.getNodeName().equals(repeatNodeDetail.getNodeName()))
				{
				//	System.out.println("skipping " + child.getNodeName());
					continue;
				}
				Vector<DTOWorkSpaceNodeAttrDetail> attrDetails = docMgmtImpl.getNodeAttrDetail(workspaceID,child.getNodeId());
			//	System.out.println("attr for this " + attrDetails.size());
				boolean sameNodes = true;
				boolean attrFound = false;
				for(int i=1;i<attrCount;i++)
				{
					 String attrValueId = "attrValueId"+i;
			    	 String attrType = "attrType"+i;
			    	 String attrId = "attrId"+i;
			    	 String[] attribute = null;
			    	 if(request.getParameter(attrType).equalsIgnoreCase("Combo"))
			    		 attribute = request.getParameter(attrValueId).split("&&&&");
			    	 else
			    		 attribute = new String[]{request.getParameter(attrValueId)};
			    	 
			    	 Integer attribId=Integer.parseInt(request.getParameter(attrId));
			    	// System.out.println("attribId"+attribId);
			    	 //System.out.println("attrval" + attribute[0]);
			    	 for (int indAttr=0;indAttr<attrDetails.size();indAttr++)
			    	 {
			    		 DTOWorkSpaceNodeAttrDetail dtoWsNdAttrDtl = attrDetails.get(indAttr);
			    		 if (dtoWsNdAttrDtl.getAttrId() == attribId && sameNodes)
			    		 {
			    			// System.out.println("dtoWsNdAttrDtl.getAttrId()" + dtoWsNdAttrDtl.getAttrId());
			    			// System.out.println("dtoWsNdAttrDtl.getAttrValue()" + dtoWsNdAttrDtl.getAttrValue());
			    			 sameNodes = dtoWsNdAttrDtl.getAttrValue().equals(attribute[0]);
			    			 attrFound = true; //if the attributes are not found
			    			 break;
			    		 }
			    	 }
				}
				if (sameNodes && attrFound)
				{
					errorMsg="Current attribute combination exists.";
					addActionError(errorMsg);
					return "show";
				}
			}			
			//Creating new Section from the source Section
			int  newNodeId = docMgmtImpl.getmaxNodeId(workspaceID)+1;
	    	
			if(repeatNodeDetail.getRequiredFlag() == 'S'){
				docMgmtImpl.CopyAndPasteWorkSpace(workspaceID,repeatNodeId,workspaceID,repeatNodeDetail.getParentNodeId(),userId,"1");//Status = '1' for 'Add Node Last'
			}
			else{
				docMgmtImpl.CopyAndPasteWorkSpace(workspaceID,repeatNodeId,workspaceID,repeatNodeId,userId,"2");//Status = '2' for 'Add Node After'
			}
			System.out.println("Ok");	
	    	
			//Updating the Attributes values of the Section's root Node
	    	for(int i=1;i<attrCount;i++)	
			{
	    		
				 String attrValueId = "attrValueId"+i;
		    	 String attrType = "attrType"+i;
		    	 String attrId = "attrId"+i;
		    	 String attrName = "attrName"+i;
		    	
		    	 String[] attribute = null;
		    	 if(request.getParameter(attrType).equalsIgnoreCase("Combo")){
		    	 	 
		    		 /* 'request.getParameter(attrValueId)' will return
		    		  * in format 'attrValue&&&&attrDisplayValue'
		    		  * 
		    		  */ 
		    		 attribute = request.getParameter(attrValueId).split("&&&&");
		    	 }
		    	 else{
		    		 attribute = new String[]{request.getParameter(attrValueId)};
		    	 }
		    	 
		    	 Integer attribId=Integer.parseInt(request.getParameter(attrId));
		    	 
		    	 wsnadto.setWorkspaceId(workspaceID);
		    	 wsnadto.setNodeId(newNodeId);
		    	 wsnadto.setAttrId(attribId);
		    	 wsnadto.setAttrName(request.getParameter(attrName));
		    	 wsnadto.setAttrValue(attribute[0]);//Attribute value
		    	 wsnadto.setModifyBy(userId);
			    
		    	 docMgmtImpl.updateWorkspaceNodeAttributeValue(wsnadto);
		    	 
		    	 if(AttributeValueForNodeDisplayName==""){
		    		 AttributeValueForNodeDisplayName = attribute[0];
		    	 }else if(!attribute[0].equals("")){
		    		 AttributeValueForNodeDisplayName=AttributeValueForNodeDisplayName+","+attribute[0];
		    	 }
		    	    
			}
	    	
	    	String attrValue=docMgmtImpl.getAttributeDetailByAttrName(workspaceID,1,"Project Start Date").getAttrValue();
			
			if(attrValue != null && !attrValue.equals("")){
				getSRFlagData=docMgmtImpl.getProjectTimelineSRFlagData(workspaceID);
				
				LocalDateTime startDate=null;
				LocalDateTime endDate = null;
				//
				int firstNode = docMgmtImpl.getChildNodeByParent(newNodeId,workspaceID).get(0).getNodeId();
				if(getSRFlagData.size()>0){
					if(docMgmtImpl.getTimelineDataForRepeatSection(workspaceID,firstNode).size()>0){
					DTOTimelineWorkspaceUserRightsMst dtor=new DTOTimelineWorkspaceUserRightsMst();
					/*dtor=docMgmtImpl.getTimelineDataForRepeatSection(workspaceID,firstNode).get(0);
					
					
					Timestamp attrValuer=dtor.getAdjustDate();
					LocalDateTime date=attrValuer.toLocalDateTime();*/
					Vector<DTOWorkSpaceUserRightsMst> getLastRightsDtl = new Vector<>();
					LocalDateTime date = null;
					getLastRightsDtl = docMgmtImpl.UserOnNodeTimelineTracking(workspaceID,firstNode);
					
					int temp= getLastRightsDtl.size()-1;
					date= getLastRightsDtl.get(temp).getStartDate().toLocalDateTime();
					//date= getLastRightsDtl.get(temp).getAdjustDate().toLocalDateTime();
					
					//int repeatNodeId = docMgmtImpl.getmaxNodeId(workspaceID);
					int parentNodeIdforAdjustDate=docMgmtImpl.getParentNodeId(workspaceID, firstNode);
					getRepeatAdjustDate=docMgmtImpl.getProjectTimelineAdjustDateRepeatNodeandChildNodeInfo(workspaceID,firstNode,parentNodeIdforAdjustDate);
					
					for(int k=0;k<getRepeatAdjustDate.size();k++){
						//System.out.println("Hours : "+hoursData.get(k).getHours());
						DTOWorkSpaceUserRightsMst dtotimeline = (DTOWorkSpaceUserRightsMst) getRepeatAdjustDate.get(k);
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
						
						//dtotimeline.setStartDate(Timestamp.valueOf(startDate));
						
						//dtotimeline.setEndDate(Timestamp.valueOf(endDate));
						
			            dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
			    							
						docMgmtImpl.updateTimelineDatesValue(dtotimeline);
					}
				}
				}else{
					hoursData=docMgmtImpl.getProjectTimelineDetailsForHours(workspaceID);
					String s[]=attrValue.split("/");
					Vector<DTOWorkSpaceUserRightsMst> getLastRightsDtl = new Vector<>();
					LocalDateTime date = null;
					getLastRightsDtl = docMgmtImpl.UserOnNodeTimelineTracking(workspaceID,firstNode);
					if(getLastRightsDtl.size()>0){
						int temp= getLastRightsDtl.size()-1;
						date= getLastRightsDtl.get(temp).getStartDate().toLocalDateTime();							
					}else{
						date=LocalDateTime.of(LocalDate.of(Integer.parseInt(s[0]),Integer.parseInt(s[1]),Integer.parseInt(s[2])), LocalTime.of(0,0));
					}
					for(int k=0;k<hoursData.size();k++){
						//System.out.println("Hours : "+hoursData.get(k).getHours());
						DTOWorkSpaceUserRightsMst dtotimeline = (DTOWorkSpaceUserRightsMst) hoursData.get(k);
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
						
						//dtotimeline.setStartDate(Timestamp.valueOf(startDate));
						
						//dtotimeline.setEndDate(Timestamp.valueOf(endDate));
						
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			            Date date1 = sdf.parse("1900-01-01 00:00:00.000");
			            Date date2 = sdf.parse(dtotimeline.getAdjustDate().toString());
			            if(!date1.before(date2) && getSRFlagData.size()==0){
			            	dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
			            }
			            else{
			            	
			            	dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
			            }
						
						docMgmtImpl.updateTimelineDatesValue(dtotimeline);
					}
				
			}
					
			}
	    	// Setting cCloneFlag to 'Y' of the root node of newly created section 
			// AND Updating Nodes' Node Display Name
	    	
	    	
	    	
	    	DTOWorkSpaceNodeDetail wsnd = docMgmtImpl.getNodeDetail(workspaceID, newNodeId).get(0);
	    	/*if(AttributeValueForNodeDisplayName.equals("")){
	    		
	    		//pattern used for attr values appended at the end of nodeDisplayName between '(' and ')'.
	    		wsnd.setNodeDisplayName(wsnd.getNodeDisplayName().replaceAll("\\(.+\\)$",""));
	    	}
	    	else{
	    		//pattern used for attr values appended at the end of nodeDisplayName between '(' and ')'.
	    		wsnd.setNodeDisplayName(wsnd.getNodeDisplayName().replaceAll("\\(.+\\)$","")+"("+AttributeValueForNodeDisplayName+")");
	    	}*/
	    	if (AttributeValueForNodeDisplayName.equals("")) {

				if(wsnd.getNodeDisplayName().contains("{"))
				{
				wsnd.setNodeDisplayName(wsnd.getNodeDisplayName().substring(0,wsnd.getNodeDisplayName().indexOf('{')));
				}
				else
				{
					wsnd.setNodeDisplayName(wsnd.getNodeDisplayName());
				}
			} else {
				// pattern used for attr values appended at the end of
				// nodeDisplayName between '(' and ')'.
				if(wsnd.getNodeDisplayName().contains("{"))
				{
				wsnd.setNodeDisplayName(wsnd.getNodeDisplayName().substring(0,wsnd.getNodeDisplayName().indexOf('{'))
						+ "{" + AttributeValueForNodeDisplayName + "}");
				}
				else
				{
					wsnd.setNodeDisplayName(wsnd.getNodeDisplayName()+"{" + AttributeValueForNodeDisplayName + "}");
				}
			}
	    	
	    		wsnd.setCloneFlag('Y');
	    	
	    		
	    		System.out.println("Ok0.2->"+wsnd.getNodeDisplayName().length());
	    	docMgmtImpl.insertWorkspaceNodeDetail(wsnd, 2);//edit mode
	    	/*//Updating Nodes' Node Display Name
			DTOWorkSpaceNodeDetail wsnd = new DTOWorkSpaceNodeDetail();
			wsnd.setWorkspaceId(workspaceID);
			wsnd.setNodeId(newNodeId);
			
			docMgmtImpl.updateNodeDisplayNameAccToEctdAttribute(wsnd, AttributeValueForNodeDisplayName);
			*/
 	
	    	/** Updating Node Display Name, Folder Name And Required Flag 
	    	 *  for nodes repeated from nodes with required flag 'S'.
	    	 *  Folder Name and Node Display Name will be updated as per the attribute name,value
	    	 */
	    	
	    	System.out.println("Ok1");
	    	
			if(repeatNodeDetail.getRequiredFlag() == 'S'){
				
				Vector<Integer> allNodeDtl = docMgmtImpl.getAllChildNodes(workspaceID, newNodeId, new Vector());
				
				allNodeDtl.addElement(new Integer(newNodeId));//Adding node itself
				
				for (Iterator<Integer> iterator = allNodeDtl.iterator(); iterator.hasNext();) {
					Integer nodeId = iterator.next();
					
					DTOWorkSpaceNodeDetail childNodeDtl = docMgmtImpl.getNodeDetail(workspaceID, nodeId.intValue()).get(0);
					
					for(int i=1;i<attrCount;i++)
					{
						 //Get attribute value to replace
						 String attrValueId = "attrValueId"+i;
						 String attrType = "attrType"+i;
				    	 String attrName = "attrName"+i;
				    
				    	 String[] attribute = null;
				    	 String newReplaceValue = null,newReplaceDisplayValue=null;
				    	 if(request.getParameter(attrType).equalsIgnoreCase("Combo")){
				    	 	 attribute = request.getParameter(attrValueId).split("&&&&");
				    	 }
				    	 else{
				    		 attribute = new String[]{request.getParameter(attrValueId)};
				    	 }
				    	 
				    	 newReplaceValue = attribute[0]; //Attribute Value
				    	 if(attribute.length > 1){
				    		 newReplaceDisplayValue= attribute[1]; //Attribute Display Value
				    	 }
				    	 else{
				    		 newReplaceDisplayValue = attribute[0]; //Attribute Value
				    	 }
				    	 
				    	 //Set replace value to node detail
				    	 childNodeDtl.setNodeDisplayName(childNodeDtl.getNodeDisplayName().replaceAll("\\["+request.getParameter(attrName)+"\\]", newReplaceDisplayValue));
				    	 childNodeDtl.setFolderName(childNodeDtl.getFolderName().replaceAll("\\["+request.getParameter(attrName)+"\\]", newReplaceValue.toLowerCase()));
					}
					
					childNodeDtl.setRequiredFlag('N');
					
					docMgmtImpl.insertWorkspaceNodeDetail(childNodeDtl, 2);//Update mode
				}
			}
			
			
			
		}
		else {//Repeat Leaf Node
			
			//System.out.println("repeatNodeId::"+repeatNodeId);
			
			int numOfRepetition;
			int startIndex;
			try {
				numOfRepetition = Integer.parseInt(numberOfRepetitions);
				startIndex = Integer.parseInt(suffixStart);
				
				if(numOfRepetition < 1 || startIndex < 1) {
					//System.out.println(numOfRepetition);
					throw new NumberFormatException();
				}
			}
			catch (NumberFormatException e) {
				return SUCCESS;
			}
			
			DTOWorkSpaceNodeDetail sourceNodeDtl = docMgmtImpl.getNodeDetail(workspaceID, repeatNodeId).get(0);
			
			String nodeDisplayName = sourceNodeDtl.getNodeDisplayName();
			int ind = sourceNodeDtl.getFolderName().lastIndexOf(".");
			String folderName="";
			if(ind != -1){
				folderName = sourceNodeDtl.getFolderName().substring(0, ind);
			}
			else{
				folderName = sourceNodeDtl.getFolderName();
			}
			
			
			int sourceNodeId = repeatNodeId;
			String fileExtension = "";
			if(sourceNodeDtl.getFolderName().lastIndexOf(".") != -1)
				fileExtension = sourceNodeDtl.getFolderName().substring(sourceNodeDtl.getFolderName().lastIndexOf("."));
			
			
			//Fetch All siblings in 'childNodes'
			int parentNodeId = docMgmtImpl.getParentNodeId(workspaceID, sourceNodeDtl.getNodeId());
			
			int nodeNo  = docMgmtImpl.getFailedScriptData(workspaceID,parentNodeId,"FailedScript");
			Vector<DTOWorkSpaceNodeDetail> FailScriptNodeCount = new Vector<DTOWorkSpaceNodeDetail>();
			DTOWorkSpaceNodeDetail getFaildScriptDtl = new DTOWorkSpaceNodeDetail();  
			int maxNodeNo = docMgmtImpl.getmaxNodeNo(workspaceID,parentNodeId);
			Vector<DTOWorkSpaceNodeDetail> getNodeDetailBynodeNo = docMgmtImpl.getNodeDetailByNodeNo(workspaceID, parentNodeId,maxNodeNo);
			
			if(nodeNo!=0){
				DTOWorkSpaceNodeDetail getFailedScriptDetail =new DTOWorkSpaceNodeDetail();

				getFailedScriptDetail = docMgmtImpl.getFailedScriptNodeDetail(workspaceID,parentNodeId,nodeNo+1);
				System.out.println("NodeName: "+getFailedScriptDetail.getNodeName());
				
				String nodeName = getNodeDetailBynodeNo.get(0).getNodeName();
				System.out.println("currentNodeName: "+nodeName);
				//Repeat TC node After Last TC
				if(getFailedScriptDetail.getNodeName().equalsIgnoreCase("TC") && nodeName.equalsIgnoreCase("TC")){
					FailScriptNodeCount = docMgmtImpl.getNodeDetailForNodeCountForFailedSctipt(workspaceID, parentNodeId,"TC");
					if(FailScriptNodeCount.size()>0){
						getFaildScriptDtl = docMgmtImpl.getNodeDetail(workspaceID, FailScriptNodeCount.lastElement().getNodeId()).get(0);
					}
				}
				else{  //Repeat TC node After Last Failed Script
					FailScriptNodeCount = docMgmtImpl.getNodeDetailForNodeCountForFailedSctipt(workspaceID, parentNodeId,"FailedScript");
					if(FailScriptNodeCount.size()>0){
						getFaildScriptDtl = docMgmtImpl.getNodeDetail(workspaceID, FailScriptNodeCount.lastElement().getNodeId()).get(0);
					}
				}
			}
			Vector childNodes = docMgmtImpl.getChildNodeByParent(parentNodeId, workspaceID);
			
			 //if selected node is clone node then find its original node
			if(sourceNodeDtl.getCloneFlag() == 'Y') {
				for(int i = 0; i < childNodes.size(); i++) {
					DTOWorkSpaceNodeDetail currentChild = (DTOWorkSpaceNodeDetail)childNodes.get(i);
					if(currentChild.getNodeName().equalsIgnoreCase(sourceNodeDtl.getNodeName())){
						if(currentChild.getCloneFlag() == 'N') {//Original Node of clone found
							nodeDisplayName = currentChild.getNodeDisplayName();
							
							ind = currentChild.getFolderName().lastIndexOf(".");
							if(ind != -1){
								folderName = currentChild.getFolderName().substring(0, ind);
							}
							else{
								folderName = currentChild.getFolderName();
							}
							
							//System.out.println("folderName::"+folderName);
						}
					}
				}
				
			}
			folderName = folderName.replaceAll("-\\d$", "");
			//Search if Node(fileName) already exists...
			for(int i = 0; i < childNodes.size(); i++) {
				DTOWorkSpaceNodeDetail currentChild = (DTOWorkSpaceNodeDetail)childNodes.get(i);					
				
				for(int j = startIndex ; j < startIndex + numOfRepetition ; j++)
				{
					if (currentChild.getFolderName().equals(folderName + "-" + j + fileExtension))
					{
						errorMsg="The Document (" + currentChild.getFolderName() + ") exists.";
						addActionError(errorMsg);
						return "show";
					}
				}
			}
			
			
			
			//source node has attributes with attrForIndiId='0002'
			String attriVals = "";
			if(nodeDisplayName.contains("(") && nodeDisplayName.endsWith(")")){
				attriVals = nodeDisplayName.substring(nodeDisplayName.indexOf("("));
				nodeDisplayName = nodeDisplayName.substring(0, nodeDisplayName.indexOf("("));
			}
			nodeDisplayName = nodeDisplayName.replaceAll("-\\d$", "");
			for(int i = startIndex ; i < startIndex + numOfRepetition ; i++){
			
				DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
				dto.setWorkspaceId(workspaceID);
				dto.setNodeId(sourceNodeId);
				dto.setNodeName(sourceNodeDtl.getNodeName());
			if(dto.getNodeName().equalsIgnoreCase("TC") && getFaildScriptDtl.getNodeId() !=0){
				dto.setAfterRepeatNodeId(getFaildScriptDtl.getNodeId());
			}
			else{
				dto.setAfterRepeatNodeId(sourceNodeId);
			}
				dto.setNodeDisplayName(nodeDisplayName+"-"+i+attriVals);
				dto.setFolderName(folderName+"-"+i+fileExtension);
				dto.setCloneFlag('Y');// Its a clone node
				dto.setNodeTypeIndi(sourceNodeDtl.getNodeTypeIndi());
				dto.setRequiredFlag(sourceNodeDtl.getRequiredFlag());
				dto.setCheckOutBy(0);
				dto.setPublishedFlag(sourceNodeDtl.getPublishedFlag());
				//dto.setRemark(sourceNodeDtl.getRemark());
				dto.setRemark(remark);
				dto.setModifyBy(userId);
				dto.setDefaultFileFormat(sourceNodeDtl.getDefaultFileFormat());
				//dto.setModifyOn();
				

				if(dto.getNodeName().equalsIgnoreCase("TC")){
					docMgmtImpl.addChildNodeAfterTC(dto);
				}else{
					docMgmtImpl.addChildNodeAfter(dto);
				}
				
				String attrValue=docMgmtImpl.getAttributeDetailByAttrName(workspaceID,1,"Project Start Date").getAttrValue();
				
				if(attrValue != null && !attrValue.equals("")){
					getSRFlagData=docMgmtImpl.getProjectTimelineSRFlagData(workspaceID);
					
					LocalDateTime startDate=null;
					LocalDateTime endDate = null;
					//
					if(getSRFlagData.size()>0){
						if(docMgmtImpl.getTimelineDataForRepeatSection(workspaceID,sourceNodeId).size()>0){
						DTOTimelineWorkspaceUserRightsMst dtor=new DTOTimelineWorkspaceUserRightsMst();
						dtor=docMgmtImpl.getTimelineDataForRepeatSection(workspaceID,sourceNodeId).get(0);
						
						
						Timestamp attrValuer=dtor.getAdjustDate();
						LocalDateTime date=attrValuer.toLocalDateTime();
						int repeatNodeId = docMgmtImpl.getmaxNodeId(workspaceID);
						int parentNodeIdforAdjustDate=docMgmtImpl.getParentNodeId(workspaceID, repeatNodeId);
						getRepeatAdjustDate=docMgmtImpl.getProjectTimelineAdjustDateRepeatNodeandChildNodeInfo(workspaceID,repeatNodeId,parentNodeIdforAdjustDate);
						
						for(int k=0;k<getRepeatAdjustDate.size();k++){
							//System.out.println("Hours : "+hoursData.get(k).getHours());
							DTOWorkSpaceUserRightsMst dtotimeline = (DTOWorkSpaceUserRightsMst) getRepeatAdjustDate									.get(k);
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
							
							//dtotimeline.setStartDate(Timestamp.valueOf(startDate));
							
							//dtotimeline.setEndDate(Timestamp.valueOf(endDate));
							
				            dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
				    							
							docMgmtImpl.updateTimelineDatesValue(dtotimeline);
						}
					}
					}else{
						hoursData=docMgmtImpl.getProjectTimelineDetailsForHours(workspaceID);
						String s[]=attrValue.split("/");
						Vector<DTOWorkSpaceUserRightsMst> getLastRightsDtl = new Vector<>();
						LocalDateTime date = null;
						getLastRightsDtl = docMgmtImpl.UserOnNodeTimelineTracking(workspaceID,repeatNodeId);
						if(getLastRightsDtl.size()>0){
							int temp= getLastRightsDtl.size()-1;
							date= getLastRightsDtl.get(temp).getStartDate().toLocalDateTime();							
						}else{
							date=LocalDateTime.of(LocalDate.of(Integer.parseInt(s[0]),Integer.parseInt(s[1]),Integer.parseInt(s[2])), LocalTime.of(0,0));
						}
						for(int k=0;k<hoursData.size();k++){
							//System.out.println("Hours : "+hoursData.get(k).getHours());
							DTOWorkSpaceUserRightsMst dtotimeline = (DTOWorkSpaceUserRightsMst) hoursData.get(k);
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
							
							//dtotimeline.setStartDate(Timestamp.valueOf(startDate));
							
							//dtotimeline.setEndDate(Timestamp.valueOf(endDate));
							
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				            Date date1 = sdf.parse("1900-01-01 00:00:00.000");
				            Date date2 = sdf.parse(dtotimeline.getAdjustDate().toString());
				            if(!date1.before(date2) && getSRFlagData.size()==0){
				            	dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
				            }
				            else{
				            	
				            	dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
				            }
							
							docMgmtImpl.updateTimelineDatesValue(dtotimeline);
						}
					}
					
						
				}

				sourceNodeId = docMgmtImpl.getmaxNodeId(workspaceID);
			}
			
			
		}
		return "show";
	}

	public String DeleteSectionForScripts(){
		//String workspaceID=ActionContext.getContext().getSession().get("ws_id").toString();
		DTOTimelineWorkspaceUserRightsMst dto=new DTOTimelineWorkspaceUserRightsMst();
		int parentNodeId = docMgmtImpl.getParentNodeId(workspaceID, removeNodeId);
		getChildNodes=docMgmtImpl.getChildNodesForSectionDeletion(workspaceID, removeNodeId);
		for(int j=0;j<getChildNodes.size();j++){
			getNodeHistory=docMgmtImpl.getNodeHistoryForSectinDeletion(workspaceID, getChildNodes.get(j).getNodeId());
			if(getNodeHistory.size()>0){
				if(!getNodeHistory.get(0).getFileName().equalsIgnoreCase("No File") && !getNodeHistory.get(0).getFileName().equalsIgnoreCase("")){
					//docMgmtImpl.deleteNodeDetail(workspaceID, nodeIdForSectionDelete,remark);
					flag=false;
					//break;
				}
				else{
					flag=true;
					//docMgmtImpl.deleteNodeDetail(workspaceID, nodeIdForSectionDelete,remark);
				}
			}
			
		}
		if(flag==false)
			htmlContent="false";
		else{
			htmlContent="true";
			docMgmtImpl.deleteNodeDetail(workspaceID, removeNodeId,remark);
		}
		
		Vector<DTOWorkSpaceNodeDetail> allSiblings = docMgmtImpl.getChildNodeByParent(parentNodeId, workspaceID);
		if(allSiblings.size() > 1)
		getSRFlagData=docMgmtImpl.getProjectTimelineSRFlagData(workspaceID);
		int isLeafNode = docMgmtImpl.isLeafNodes(workspaceID, removeNodeId);
		if(getSRFlagData.size()>0 && isLeafNode==1){
			dto=docMgmtImpl.getTimelineDataForRemove(workspaceID,removeNodeId).get(0);
		}
		//docMgmtImpl.deleteNodeDetail(workspaceID, removeNodeId,remark);
			
		if(getSRFlagData.size()>0 && isLeafNode==1){
		
			Timestamp attrValue=dto.getAdjustDate();
			//String attrVal=attrValue.toLocalDateTime().toLocalDate().toString();
			//attrVal=attrVal.replace("-", "/");
			int parentNodeIdforAdjustDate=docMgmtImpl.getParentNodeId(workspaceID, removeNodeId);
			getAdjustDateData=docMgmtImpl.getProjectTimelineAdjustDateRepeatNodeInfo(workspaceID,removeNodeId,parentNodeIdforAdjustDate);
			
			//String s[]=attrVal.split("/");
			
			//LocalDateTime date=LocalDateTime.of(LocalDate.of(Integer.parseInt(s[0]),Integer.parseInt(s[1]),Integer.parseInt(s[2])), LocalTime.of(0,0));
			LocalDateTime date=attrValue.toLocalDateTime();
			LocalDateTime startDate=null;
			LocalDateTime endDate = null;
			for(int k=0;k<getAdjustDateData.size();k++){
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
				//dtotimeline.setStartDate(Timestamp.valueOf(startDate));
				
				//dtotimeline.setEndDate(Timestamp.valueOf(endDate));
				dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
				
				docMgmtImpl.updateTimelineDatesValue(dtotimeline);
			}	
		
		}else{
			
			
			String attrValue=docMgmtImpl.getAttributeDetailByAttrName(workspaceID,1,"Project Start Date").getAttrValue();
			
			if(attrValue != null && !attrValue.equals("")){
				hoursData=docMgmtImpl.getProjectTimelineDetailsForHours(workspaceID);
				
				String s[]=attrValue.split("/");
				
				LocalDateTime date=LocalDateTime.of(LocalDate.of(Integer.parseInt(s[0]),Integer.parseInt(s[1]),Integer.parseInt(s[2])), LocalTime.of(0,0));

				LocalDateTime startDate=null;
				LocalDateTime endDate = null;
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
		return "html";
		
	}
	
	public String removeSection() {
		DTOTimelineWorkspaceUserRightsMst dto=new DTOTimelineWorkspaceUserRightsMst();
		String workspaceID=ActionContext.getContext().getSession().get("ws_id").toString();
		
		int parentNodeId = docMgmtImpl.getParentNodeId(workspaceID, removeNodeId);
		if(removeNodeId == repeatNodeId) {
			
			Vector childNodes = docMgmtImpl.getChildNodeByParent(parentNodeId, workspaceID);
			
			for(int i = 0; i < childNodes.size(); i++) {
				DTOWorkSpaceNodeDetail currentChild = (DTOWorkSpaceNodeDetail)childNodes.get(i);
				if(currentChild.getNodeId() == removeNodeId) {
					break;
				}
				else {//Setting repeatNodeId to the nodeId before of the node deleted
					repeatNodeId = currentChild.getNodeId();
				}
			}
		}
		
		Vector<DTOWorkSpaceNodeDetail> allSiblings = docMgmtImpl.getChildNodeByParent(parentNodeId, workspaceID);
		if(allSiblings.size() > 1)
		getSRFlagData=docMgmtImpl.getProjectTimelineSRFlagData(workspaceID);
		int isLeafNode = docMgmtImpl.isLeafNodes(workspaceID, removeNodeId);
		Vector<DTOTimelineWorkspaceUserRightsMst> getTimelineDetail= new Vector<DTOTimelineWorkspaceUserRightsMst>();
		if(getSRFlagData.size()>0 && isLeafNode==1){
			
			getTimelineDetail=docMgmtImpl.getTimelineDataForRemove(workspaceID,removeNodeId);
			if(getTimelineDetail.size()>0){
				dto = getTimelineDetail.get(0);
			}
			
		}
		docMgmtImpl.deleteNodeDetail(workspaceID, removeNodeId,remark);
			
		if(getSRFlagData.size()>0 && isLeafNode==1 && getTimelineDetail.size()>0){
		
			Timestamp attrValue=dto.getAdjustDate();
			//String attrVal=attrValue.toLocalDateTime().toLocalDate().toString();
			//attrVal=attrVal.replace("-", "/");
			int parentNodeIdforAdjustDate=docMgmtImpl.getParentNodeId(workspaceID, removeNodeId);
			getAdjustDateData=docMgmtImpl.getProjectTimelineAdjustDateRepeatNodeInfo(workspaceID,removeNodeId,parentNodeIdforAdjustDate);
			
			//String s[]=attrVal.split("/");
			
			//LocalDateTime date=LocalDateTime.of(LocalDate.of(Integer.parseInt(s[0]),Integer.parseInt(s[1]),Integer.parseInt(s[2])), LocalTime.of(0,0));
			LocalDateTime date=attrValue.toLocalDateTime();
			LocalDateTime startDate=null;
			LocalDateTime endDate = null;
			for(int k=0;k<getAdjustDateData.size();k++){
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
				//dtotimeline.setStartDate(Timestamp.valueOf(startDate));
				
				//dtotimeline.setEndDate(Timestamp.valueOf(endDate));
				dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
				
				docMgmtImpl.updateTimelineDatesValue(dtotimeline);
			}	
		
		}else{
			
			
			String attrValue=docMgmtImpl.getAttributeDetailByAttrName(workspaceID,1,"Project Start Date").getAttrValue();
			
			if(attrValue != null && !attrValue.equals("")){
				hoursData=docMgmtImpl.getProjectTimelineDetailsForHours(workspaceID);
				
				String s[]=attrValue.split("/");
				
				LocalDateTime date=LocalDateTime.of(LocalDate.of(Integer.parseInt(s[0]),Integer.parseInt(s[1]),Integer.parseInt(s[2])), LocalTime.of(0,0));

				LocalDateTime startDate=null;
				LocalDateTime endDate = null;
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
	
		
		return "show";
	}
	
	public String editLeafNode() {
		
		/*get the application mode*/
		PropertyInfo propertyInfo=PropertyInfo.getPropInfo();
		appMode = propertyInfo.getValue("EditNodeAsEctdNode");
		lbl_folderName = propertyInfo.getValue("ForlderName");
		lbl_nodeName = propertyInfo.getValue("NodeName");	
		if (appMode==null || appMode.equals(""))
			appMode="no";
		appMode=appMode.toLowerCase();
		/*************************************/
		
		String workspaceID=ActionContext.getContext().getSession().get("ws_id").toString();
		
		sourceNodeDetail = docMgmtImpl.getNodeDetail(workspaceID, editNodeId).get(0);
		
		nodeDisplayName = sourceNodeDetail.getNodeDisplayName(); 
		
		//if node has attributes with attrForIndiId='0002'
		if(nodeDisplayName.contains("(") && nodeDisplayName.endsWith(")")){
			
			//attriValues = nodeDisplayName.substring(nodeDisplayName.indexOf("("));
			//nodeDisplayName = nodeDisplayName.substring(0, nodeDisplayName.indexOf("("));
		}
		
		int lastInd = sourceNodeDetail.getFolderName().lastIndexOf(".");
		if(lastInd != -1){
			fileName = sourceNodeDetail.getFolderName().substring(0,lastInd); 	
			fileExt = sourceNodeDetail.getFolderName().substring(lastInd);
		}
		else {
			fileName = sourceNodeDetail.getFolderName();
		}
		
		return SUCCESS;
	}
	
	public String updateLeafNode() {
		
		String workspaceID=ActionContext.getContext().getSession().get("ws_id").toString();
		
		DTOWorkSpaceNodeDetail nodeDetail = docMgmtImpl.getNodeDetail(workspaceID, editNodeId).get(0);
		nodeDetail.setNodeDisplayName(nodeDisplayName);
		
		nodeDetail.setFolderName(fileName+fileExt.toLowerCase());//e.g fileExt = ".pdf"
		
		docMgmtImpl.insertWorkspaceNodeDetail(nodeDetail, 2);//update mode
		nodeDetail.setStatusIndi('E');
		docMgmtImpl.insertWorkspaceNodeDetailhistory(nodeDetail, 1);//update mode
		return SUCCESS;
	}
	
	public void updateNodeDtlAttributeWise(String workspaceId,int nodeId){
		
		//Get node attributes of type '0002'
		Vector nodeAttributes = docMgmtImpl.getNodeAttributes(workspaceId, nodeId);
		
		for (Object obj : nodeAttributes) {
			DTOWorkSpaceNodeAttribute nodeAttribute =(DTOWorkSpaceNodeAttribute)obj; 
			
		}
	}
	
	public String RemoveNodeSection(){
		DTOTimelineWorkspaceUserRightsMst dto=new DTOTimelineWorkspaceUserRightsMst();
		String workspaceID=ActionContext.getContext().getSession().get("ws_id").toString();
		
		int parentNodeId = docMgmtImpl.getParentNodeId(workspaceID, removeNodeId);
		if(removeNodeId == repeatNodeId) {
			
			Vector childNodes = docMgmtImpl.getChildNodeByParent(parentNodeId, workspaceID);
			
			for(int i = 0; i < childNodes.size(); i++) {
				DTOWorkSpaceNodeDetail currentChild = (DTOWorkSpaceNodeDetail)childNodes.get(i);
				if(currentChild.getNodeId() == removeNodeId) {
					break;
				}
				else {//Setting repeatNodeId to the nodeId before of the node deleted
					repeatNodeId = currentChild.getNodeId();
				}
			}
		}
		//docMgmtImpl.deleteNodeDetail(workspaceID, removeNodeId,remark);
		
		Vector<DTOWorkSpaceNodeDetail> allSiblings = docMgmtImpl.getChildNodeByParent(parentNodeId, workspaceID);
		Vector<DTOTimelineWorkspaceUserRightsMst> getTimeLineDta= docMgmtImpl.getTimelineDataForRepeatSection(workspaceID,removeNodeId);
		if(allSiblings.size() > 1)
		{
			getSRFlagData=docMgmtImpl.getProjectTimelineSRFlagData(workspaceID);
		}
		//if(getSRFlagData.size()>0){
		if(getSRFlagData.size()>0 && getTimeLineDta.size()>0){
			dto=docMgmtImpl.getTimelineDataForRemove(workspaceID,removeNodeId).get(0);
		}
		docMgmtImpl.deleteNodeDetail(workspaceID, removeNodeId,remark);
		htmlContent="true";
		if(getSRFlagData.size()>0){
		
			Timestamp attrValue=dto.getAdjustDate();
			//String attrVal=attrValue.toLocalDateTime().toLocalDate().toString();
			//attrVal=attrVal.replace("-", "/");
			/*Vector<DTOWorkSpaceUserRightsMst> getLastRightsDtl = new Vector<>();
			LocalDateTime date = null;
			getLastRightsDtl = docMgmtImpl.UserOnNodeTimelineTracking(workspaceID,removeNodeId);
			
			int temp= getLastRightsDtl.size()-1;
			date= getLastRightsDtl.get(temp).getStartDate().toLocalDateTime();		*/
			int parentNodeIdforAdjustDate=docMgmtImpl.getParentNodeId(workspaceID, removeNodeId);
			getAdjustDateData=docMgmtImpl.getProjectTimelineAdjustDateRepeatNodeInfo(workspaceID,removeNodeId,parentNodeIdforAdjustDate);
			
			//String s[]=attrVal.split("/");
			
			//LocalDateTime date=LocalDateTime.of(LocalDate.of(Integer.parseInt(s[0]),Integer.parseInt(s[1]),Integer.parseInt(s[2])), LocalTime.of(0,0));
			LocalDateTime date=attrValue.toLocalDateTime();
			LocalDateTime startDate=null;
			LocalDateTime endDate = null;
			for(int k=0;k<getAdjustDateData.size();k++){
				//System.out.println("Hours : "+hoursData.get(k).getHours());
				DTOWorkSpaceUserRightsMst dtotimeline = (DTOWorkSpaceUserRightsMst) getAdjustDateData.get(k);
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
				//dtotimeline.setStartDate(Timestamp.valueOf(startDate));
				
				//dtotimeline.setEndDate(Timestamp.valueOf(endDate));
				dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
				
				docMgmtImpl.updateTimelineDatesValue(dtotimeline);
			}	
		
		}else{
			
			
			String attrValue=docMgmtImpl.getAttributeDetailByAttrName(workspaceID,1,"Project Start Date").getAttrValue();
			
			if(attrValue != null && !attrValue.equals("")){
				hoursData=docMgmtImpl.getProjectTimelineDetailsForHours(workspaceID);
				
				String s[]=attrValue.split("/");
				
				LocalDateTime date=LocalDateTime.of(LocalDate.of(Integer.parseInt(s[0]),Integer.parseInt(s[1]),Integer.parseInt(s[2])), LocalTime.of(0,0));

				LocalDateTime startDate=null;
				LocalDateTime endDate = null;
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
		return "html";
	}
	
	public String getParentAttributes(){		
		attrDtl = docMgmtImpl.getAttributeDetailForDisplay(workspaceID, nodeId);
		DTOWorkSpaceNodeDetail sourceNodeDtl = docMgmtImpl.getNodeDetail(workspaceID, nodeId).get(0);
		lbl_folderName = propertyInfo.getValue("ForlderName");
		lbl_nodeName = propertyInfo.getValue("NodeName");
		folderName=sourceNodeDtl.getFolderName();
		scriptNodeId = sourceNodeDtl.getNodeId();
		
		String temp;
		isScriptCodeAutoGenrate= propertyInfo.getValue("isScriptCodeAutogenerate");
	if(isScriptCodeAutoGenrate.equalsIgnoreCase("Yes")){
		 int parentNodeId = docMgmtImpl.getParentNodeId(workspaceID, nodeId);
		 DTOWorkSpaceNodeDetail getNodeDetail =  new DTOWorkSpaceNodeDetail();
		 getNodeDetail = docMgmtImpl.getNodeDetail(workspaceID, parentNodeId).get(0);
		 String nodeName = getNodeDetail.getNodeName();
		 int totalPQScripts = docMgmtImpl.getPQScriptsCount(workspaceID, parentNodeId,nodeName);
		 DecimalFormat decimalFormat = new DecimalFormat("0000");                
		 System.out.println(decimalFormat.format(totalPQScripts));
		 if(nodeName.equalsIgnoreCase("PQ Scripts")){
			 temp = "PQSC-"+decimalFormat.format(totalPQScripts+1);
		 }
		 else if(nodeName.equalsIgnoreCase("OQ Scripts")){
			 temp = "OQSC-"+decimalFormat.format(totalPQScripts+1);
		 }
		 else{
			 temp = "IQSC-"+decimalFormat.format(totalPQScripts+1);
		 }
		 scriptCode = temp;
	}
		 

		 try{
			 	Automated_TM_Required = docMgmtImpl.getAttributeDetailByAttrName(workspaceID, 1, "Automated TM Required").getAttrValue();
				System.out.println("Automated_TM_Required---"+Automated_TM_Required);
				
				//Automated_Doc_Type=docMgmtImpl.getAttrDtlForPageSetting(workspaceID, nodeId);
				Automated_Doc_Type=docMgmtImpl.getAttributeDetailByAttrName(workspaceID, nodeId, "AutomatedDocumentType").getAttrValue();
				System.out.println("Automated_Doc_Type---"+Automated_Doc_Type);
				
				int tranNo = docMgmtImpl.getMaxTranNoByStageId(workspaceID,nodeId);
				if(Automated_Doc_Type!=null){
					if(docMgmtImpl.getTracebelityMatrixDtlForDocTypeHistory(workspaceID,nodeId,tranNo,Automated_Doc_Type).size()<=0){
						showAutomateButton=true;}
					}
				}
				catch(Exception e){
					Automated_TM_Required="";
					Automated_Doc_Type="";
				}
		 Vector<DTOWorkSpaceNodeDetail> getNodeDetail = docMgmtImpl.getNodeDetailByNodeDisplayName(workspaceID,"PQ Scripts");
			if(getNodeDetail.size()>0 ){
		 if(Automated_TM_Required.equalsIgnoreCase("Yes")){
					//URSTracebelityMatrixDtl=docMgmtImpl.getURSTracebelityMatrixDtl(workspaceID,3,4);
					URSTracebelityMatrixDtl=docMgmtImpl.getURSTracebelityMatrixDtl(workspaceID);
				}
			}
		
		
		return SUCCESS;
	}
	
	//LeafNode Repeat
	public String RepeatSectionForNode() throws ParseException, ClassNotFoundException{
		
		userCode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		usertypecode = ActionContext.getContext().getSession().get("usertypecode").toString();
		usertypename = ActionContext.getContext().getSession().get("usertypename").toString();
		String numberOfRepetitionsForParent="1";
		String suffixStartForParent="";
		int repeatNodeIdForParent;
		 int numOfRepetition = 1;
			int startIndex=1;
		//Repeat Leaf Node
		//DTOWorkSpaceNodeDetail  maxSourceNodeDtl = docMgmtImpl.getNodeDetailForparent(workspaceID, repeatNodeId).get(0);
		String separator ="-";
/*	    int sepPos = maxSourceNodeDtl.getNodeDisplayName().indexOf("-");
	    int numOfRepetition;
		int startIndex=0;
	    if (sepPos != -1) {
	    	suffixStartForParent=maxSourceNodeDtl.getNodeDisplayName().substring(sepPos + separator.length());
	    }else{
			//suffixStartForParent=maxSourceNodeDtl.getNodeDisplayName().substring(maxSourceNodeDtl.getNodeDisplayName().length() - 1);
	    	suffixStartForParent="1";
	    }

	    
		repeatNodeIdForParent=maxSourceNodeDtl.getNodeId();*/
		
		
		DTOWorkSpaceNodeDetail sourceNodeDtl = docMgmtImpl.getNodeDetail(workspaceID, repeatNodeId).get(0);
		int parentNodeIdForChilds = docMgmtImpl.getParentNodeId(workspaceID, sourceNodeDtl.getNodeId());
		int  maxSourceNodeDtl = docMgmtImpl.getNodeDetailForNodename(workspaceID, parentNodeIdForChilds,sourceNodeDtl.getNodeName());
		
		/* int sepPos = maxSourceNodeDtl.getNodeDisplayName().indexOf("-");
		 if (sepPos != -1) {
		   	suffixStartForParent=maxSourceNodeDtl.getNodeDisplayName().substring(sepPos + separator.length());
		   	int temp=Integer.parseInt(suffixStartForParent);
		   	temp+=1;
		   	suffixStartForParent=String.valueOf(temp);
		 }else{
				//suffixStartForParent=maxSourceNodeDtl.getNodeDisplayName().substring(maxSourceNodeDtl.getNodeDisplayName().length() - 1);
		    	suffixStartForParent="0";
		    }*/
		int nodeNo  = docMgmtImpl.getFailedScriptData(workspaceID,parentNodeIdForChilds,"FailedScript");
		Vector<DTOWorkSpaceNodeDetail> FailScriptNodeCount = new Vector<DTOWorkSpaceNodeDetail>();
		DTOWorkSpaceNodeDetail getFaildScriptDtl = new DTOWorkSpaceNodeDetail();  
		int maxNodeNo = docMgmtImpl.getmaxNodeNo(workspaceID,parentNodeIdForChilds);
		Vector<DTOWorkSpaceNodeDetail> getNodeDetailBynodeNo = docMgmtImpl.getNodeDetailByNodeNo(workspaceID, parentNodeIdForChilds,maxNodeNo);
		
		if(nodeNo!=0){
			DTOWorkSpaceNodeDetail getFailedScriptDetail =new DTOWorkSpaceNodeDetail();

			getFailedScriptDetail = docMgmtImpl.getFailedScriptNodeDetail(workspaceID,parentNodeIdForChilds,nodeNo+1);
			System.out.println("NodeName: "+getFailedScriptDetail.getNodeName());
			
			String nodeName = getNodeDetailBynodeNo.get(0).getNodeName();
			System.out.println("currentNodeName: "+nodeName);
			
			//Repeat TC node After Last TC
			if(getFailedScriptDetail.getNodeName().equalsIgnoreCase("TC") && nodeName.equalsIgnoreCase("TC")){
				FailScriptNodeCount = docMgmtImpl.getNodeDetailForNodeCountForFailedSctipt(workspaceID, parentNodeIdForChilds,"TC");
				if(FailScriptNodeCount.size()>0){
					getFaildScriptDtl = docMgmtImpl.getNodeDetail(workspaceID, FailScriptNodeCount.lastElement().getNodeId()).get(0);
				}
			}
			else{  //Repeat TC node After Last Failed Script
				FailScriptNodeCount = docMgmtImpl.getNodeDetailForNodeCountForFailedSctipt(workspaceID, parentNodeIdForChilds,"FailedScript");
				if(FailScriptNodeCount.size()>0){
					getFaildScriptDtl = docMgmtImpl.getNodeDetail(workspaceID, FailScriptNodeCount.lastElement().getNodeId()).get(0);
				}
			}
		}
		 
		 try {
				numOfRepetition = Integer.parseInt(numberOfRepetitionsForParent);
				//startIndex = Integer.parseInt(suffixStartForParent);
				if(maxSourceNodeDtl==1){
					maxSourceNodeDtl=0;
					startIndex = maxSourceNodeDtl;
					startIndex=startIndex+1;
				}
				else{
					//startIndex = maxSourceNodeDtl;
					startIndex=maxSourceNodeDtl;
				}
				if(numOfRepetition < 1 || startIndex < 1) {
					//System.out.println(numOfRepetition);
					throw new NumberFormatException();
				}
			}
			catch (NumberFormatException e) {
				return "error";
			}
		
		
		
		String nodeDisplayName = sourceNodeDtl.getNodeDisplayName();
		int ind = sourceNodeDtl.getFolderName().lastIndexOf(".");
		String folderName="";
		if(ind != -1){
			folderName = sourceNodeDtl.getFolderName().substring(0, ind);
		}
		else{
			folderName = sourceNodeDtl.getFolderName();
		}
		
		
		int sourceNodeId = repeatNodeId;
		String fileExtension = "";
		if(sourceNodeDtl.getFolderName().lastIndexOf(".") != -1)
			fileExtension = sourceNodeDtl.getFolderName().substring(sourceNodeDtl.getFolderName().lastIndexOf("."));
		
		
		//Fetch All siblings in 'childNodes'
		int parentNodeId = docMgmtImpl.getParentNodeId(workspaceID, sourceNodeDtl.getNodeId());
		Vector childNodes = docMgmtImpl.getChildNodeByParent(parentNodeId, workspaceID);
		
		 //if selected node is clone node then find its original node
		if(sourceNodeDtl.getCloneFlag() == 'Y') {
			for(int i = 0; i < childNodes.size(); i++) {
				DTOWorkSpaceNodeDetail currentChild = (DTOWorkSpaceNodeDetail)childNodes.get(i);
				if(currentChild.getNodeName().equalsIgnoreCase(sourceNodeDtl.getNodeName())){
					if(currentChild.getCloneFlag() == 'N') {//Original Node of clone found
						nodeDisplayName = currentChild.getNodeDisplayName();
						
						ind = currentChild.getFolderName().lastIndexOf(".");
						if(ind != -1){
							folderName = currentChild.getFolderName().substring(0, ind);
						}
						else{
							folderName = currentChild.getFolderName();
						}
						
						//System.out.println("folderName::"+folderName);
					}
				}
			}
			
		}
		folderName = folderName.replaceAll("-\\d$", "");
		//Search if Node(fileName) already exists...
		for(int i = 0; i < childNodes.size(); i++) {
			DTOWorkSpaceNodeDetail currentChild = (DTOWorkSpaceNodeDetail)childNodes.get(i);					
			
			for(int j = startIndex ; j < startIndex + numOfRepetition ; j++)
			{
				if (currentChild.getFolderName().equals(folderName + "-" + j + fileExtension))
				{
					nodeId=parentNodeIdForChilds;
					errorMsg="The FileName (" + currentChild.getFolderName() + ") exists!";
					addActionError(errorMsg);
					return "show";
				}
			}
		}
		
		
		
		//source node has attributes with attrForIndiId='0002'
		String attriVals = "";
		if(nodeDisplayName.contains("(") && nodeDisplayName.endsWith(")")){
			attriVals = nodeDisplayName.substring(nodeDisplayName.indexOf("("));
			nodeDisplayName = nodeDisplayName.substring(0, nodeDisplayName.indexOf("("));
		}
		nodeDisplayName = nodeDisplayName.replaceAll("-\\d$", "");
		for(int i = startIndex ; i < startIndex + numOfRepetition ; i++){
		
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setWorkspaceId(workspaceID);
			dto.setNodeId(sourceNodeId);
			if(getFaildScriptDtl.getNodeId()!=0)
				dto.setAfterRepeatNodeId(getFaildScriptDtl.getNodeId());
			else
				dto.setAfterRepeatNodeId(sourceNodeId);
			dto.setNodeName(sourceNodeDtl.getNodeName());
			dto.setNodeDisplayName(nodeDisplayName+"-"+i+attriVals);
			dto.setFolderName(folderName+"-"+i+fileExtension);
			dto.setCloneFlag('Y');// Its a clone node
			dto.setNodeTypeIndi(sourceNodeDtl.getNodeTypeIndi());
			dto.setRequiredFlag(sourceNodeDtl.getRequiredFlag());
			dto.setCheckOutBy(0);
			dto.setPublishedFlag(sourceNodeDtl.getPublishedFlag());
			dto.setRemark(sourceNodeDtl.getRemark());
			dto.setModifyBy(userCode);
			//dto.setModifyOn();
			
			//docMgmtImpl.addChildNodeAfter(dto);
			docMgmtImpl.addChildNodeAfterTC(dto);
			
			
			//sourceNodeId = docMgmtImpl.getmaxNodeId(workspaceID);
		
			
			String attrValue=docMgmtImpl.getAttributeDetailByAttrName(workspaceID,1,"Project Start Date").getAttrValue();
			
			if(attrValue != null && !attrValue.equals("")){
				getSRFlagData=docMgmtImpl.getProjectTimelineSRFlagData(workspaceID);
				
				LocalDateTime startDate=null;
				LocalDateTime endDate = null;
				//
				if(getSRFlagData.size()>0){
					if(docMgmtImpl.getTimelineDataForRepeatSection(workspaceID,parentNodeIdForChilds).size()>0){
					DTOTimelineWorkspaceUserRightsMst dtor=new DTOTimelineWorkspaceUserRightsMst();
					dtor=docMgmtImpl.getTimelineDataForRepeatSection(workspaceID,parentNodeIdForChilds).get(0);
					
					
					Timestamp attrValuer=dtor.getAdjustDate();
					LocalDateTime date=attrValuer.toLocalDateTime();
					int repeatNodeId = docMgmtImpl.getmaxNodeId(workspaceID);
										
					int parentNodeIdforAdjustDate=docMgmtImpl.getParentNodeId(workspaceID, repeatNodeId);
					getRepeatAdjustDate=docMgmtImpl.getProjectTimelineAdjustDateRepeatNodeandChildNodeInfo(workspaceID,repeatNodeId,parentNodeIdforAdjustDate);
					
					for(int k=0;k<getRepeatAdjustDate.size();k++){
						//System.out.println("Hours : "+hoursData.get(k).getHours());
						DTOWorkSpaceUserRightsMst dtotimeline = (DTOWorkSpaceUserRightsMst) getRepeatAdjustDate.get(k);
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
						
						//dtotimeline.setStartDate(Timestamp.valueOf(startDate));
						
						//dtotimeline.setEndDate(Timestamp.valueOf(endDate));
						
			            dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
			    							
						docMgmtImpl.updateTimelineDatesValue(dtotimeline);
					}
				}else{
					hoursData=docMgmtImpl.getProjectTimelineDetailsForHours(workspaceID);
					String s[]=attrValue.split("/");
					Vector<DTOWorkSpaceUserRightsMst> getLastRightsDtl = new Vector<>();
					LocalDateTime date = null;
					getLastRightsDtl = docMgmtImpl.UserOnNodeTimelineTracking(workspaceID,sourceNodeId);
					if(getLastRightsDtl.size()>0){
						int temp= getLastRightsDtl.size()-1;
						date= getLastRightsDtl.get(temp).getStartDate().toLocalDateTime();							
					}else{
						date=LocalDateTime.of(LocalDate.of(Integer.parseInt(s[0]),Integer.parseInt(s[1]),Integer.parseInt(s[2])), LocalTime.of(0,0));
					}
					for(int k=0;k<hoursData.size();k++){
						//System.out.println("Hours : "+hoursData.get(k).getHours());
						DTOWorkSpaceUserRightsMst dtotimeline = (DTOWorkSpaceUserRightsMst) hoursData.get(k);
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
						
						//dtotimeline.setStartDate(Timestamp.valueOf(startDate));
						
						//dtotimeline.setEndDate(Timestamp.valueOf(endDate));
						
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			            Date date1 = sdf.parse("1900-01-01 00:00:00.000");
			            Date date2 = sdf.parse(dtotimeline.getAdjustDate().toString());
			            if(!date1.before(date2) && getSRFlagData.size()==0){
			            	dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
			            }
			            else{
			            	
			            	dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
			            }
						
						docMgmtImpl.updateTimelineDatesValue(dtotimeline);
					}
				}
				}else{
					hoursData=docMgmtImpl.getProjectTimelineDetailsForHours(workspaceID);
					String s[]=attrValue.split("/");
					Vector<DTOWorkSpaceUserRightsMst> getLastRightsDtl = new Vector<>();
					LocalDateTime date = null;
					getLastRightsDtl = docMgmtImpl.UserOnNodeTimelineTracking(workspaceID,parentNodeIdForChilds);
					if(getLastRightsDtl.size()>0){
						int temp= getLastRightsDtl.size()-1;
						date= getLastRightsDtl.get(temp).getStartDate().toLocalDateTime();							
					}else{
						date=LocalDateTime.of(LocalDate.of(Integer.parseInt(s[0]),Integer.parseInt(s[1]),Integer.parseInt(s[2])), LocalTime.of(0,0));
					}
					for(int k=0;k<hoursData.size();k++){
						//System.out.println("Hours : "+hoursData.get(k).getHours());
						DTOWorkSpaceUserRightsMst dtotimeline = (DTOWorkSpaceUserRightsMst) hoursData.get(k);
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
						
						//dtotimeline.setStartDate(Timestamp.valueOf(startDate));
						
						//dtotimeline.setEndDate(Timestamp.valueOf(endDate));
						
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			            Date date1 = sdf.parse("1900-01-01 00:00:00.000");
			            Date date2 = sdf.parse(dtotimeline.getAdjustDate().toString());
			            if(!date1.before(date2) && getSRFlagData.size()==0){
			            	dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
			            }
			            else{
			            	
			            	dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
			            }
						
						docMgmtImpl.updateTimelineDatesValue(dtotimeline);
					}
				}
				
					
			}

			sourceNodeId = docMgmtImpl.getmaxNodeId(workspaceID);
		}
		nodeId=parentNodeIdForChilds;
		return "show";
	}
	
	
	
	//Section repeat
	public String RepeatSectionForParent() throws ParseException{

		userCode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		usertypecode = ActionContext.getContext().getSession().get("usertypecode").toString();
		usertypename = ActionContext.getContext().getSession().get("usertypename").toString();
		isScriptCodeAutoGenrate =knetProperties.getValue("isScriptCodeAutogenerate");

		//Repeat Section
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		DTOWorkSpaceNodeAttribute wsnadto = new DTOWorkSpaceNodeAttribute();
		String AttributeValueForNodeDisplayName="";
		
		
		DTOWorkSpaceNodeDetail repeatNodeDetail = docMgmtImpl.getNodeDetail(workspaceID, nodeId).get(0);
		
		//all childs for the parent of repeatnodeid
		Vector<DTOWorkSpaceNodeDetail> allChilds = docMgmtImpl.getChildNodeByParent(repeatNodeDetail.getParentNodeId(),workspaceID);
		System.out.println("allChilds" + allChilds.size());
		for (int eachChild = 0 ; eachChild < allChilds.size() ; eachChild ++)
		{
			//for each child check attributes
			DTOWorkSpaceNodeDetail child = allChilds.get(eachChild);
		//	System.out.println("current child" + child.getNodeId());
			//if (child.getNodeId() == repeatNodeId)
			//	continue;
			if (!child.getNodeName().equals(repeatNodeDetail.getNodeName()))
			{
			//	System.out.println("skipping " + child.getNodeName());
				continue;
			}
			Vector<DTOWorkSpaceNodeAttrDetail> attrDetails = docMgmtImpl.getNodeAttrDetail(workspaceID,child.getNodeId());
		//	System.out.println("attr for this " + attrDetails.size());
			boolean sameNodes = true;
			boolean attrFound = false;
			for(int i=1;i<attrCount;i++)
			{
				 String attrValueId = "attrValueId"+i;
		    	 String attrType = "attrType"+i;
		    	 String attrId = "attrId"+i;
		    	 String[] attribute = null;
		    	 if(request.getParameter(attrType).equalsIgnoreCase("Combo"))
		    		 attribute = request.getParameter(attrValueId).split("&&&&");
		    	 else
		    		 attribute = new String[]{request.getParameter(attrValueId)};
		    	 
		    	 Integer attribId=Integer.parseInt(request.getParameter(attrId));
		    	// System.out.println("attribId"+attribId);
		    	 //System.out.println("attrval" + attribute[0]);
		    	 for (int indAttr=0;indAttr<attrDetails.size();indAttr++)
		    	 {
		    		 DTOWorkSpaceNodeAttrDetail dtoWsNdAttrDtl = attrDetails.get(indAttr);
		    		 if (dtoWsNdAttrDtl.getAttrId() == attribId && sameNodes)
		    		 {
		    			// System.out.println("dtoWsNdAttrDtl.getAttrId()" + dtoWsNdAttrDtl.getAttrId());
		    			// System.out.println("dtoWsNdAttrDtl.getAttrValue()" + dtoWsNdAttrDtl.getAttrValue());
		    			 if(attribute[0]!=null && !attribute[0].equals("")){
		    				 sameNodes = dtoWsNdAttrDtl.getAttrValue().equals(attribute[0]);
		    			 	attrFound = true; //if the attributes are not found
		    			 }
		    			 break;
		    		 }
		    	 }
			}
			if (sameNodes && attrFound)
			{
				errorMsg="Current attribute combination exists.";
				addActionError(errorMsg);
				return "show";
			}
		}			
		//Creating new Section from the source Section
		int  newNodeId = docMgmtImpl.getmaxNodeId(workspaceID)+1;
    	
		if(repeatNodeDetail.getRequiredFlag() == 'S'){
			docMgmtImpl.CopyAndPasteWorkSpace(workspaceID,repeatNodeId,workspaceID,repeatNodeDetail.getParentNodeId(),userCode,"1");//Status = '1' for 'Add Node Last'
		}
		else{
			docMgmtImpl.CopyAndPasteWorkSpace(workspaceID,nodeId,workspaceID,nodeId,userCode,"2");//Status = '2' for 'Add Node After'
		}
		System.out.println("Ok");	
    	
		//Updating the Attributes values of the Section's root Node
    	for(int i=1;i<attrCount;i++)	
		{
    		
			 String attrValueId = "attrValueId"+i;
	    	 String attrType = "attrType"+i;
	    	 String attrId = "attrId"+i;
	    	 String attrName = "attrName"+i;
	    	
	    	 String[] attribute = null;
	    	 if(request.getParameter(attrType).equalsIgnoreCase("Combo")){
	    	 	 
	    		 /* 'request.getParameter(attrValueId)' will return
	    		  * in format 'attrValue&&&&attrDisplayValue'
	    		  * 
	    		  */ 
	    		 attribute = request.getParameter(attrValueId).split("&&&&");
	    	 }
	    	 else{
	    		 attribute = new String[]{request.getParameter(attrValueId)};
	    	 }
	    	 
	    	 Integer attribId=Integer.parseInt(request.getParameter(attrId));
	    	 
	    	 wsnadto.setWorkspaceId(workspaceID);
	    	 wsnadto.setNodeId(newNodeId);
	    	 wsnadto.setAttrId(attribId);
	    	 wsnadto.setAttrName(request.getParameter(attrName));
	    	 wsnadto.setAttrValue(attribute[0]);//Attribute value
	    	 if(wsnadto.getAttrName().equalsIgnoreCase("Script Code") && isScriptCodeAutoGenrate.equalsIgnoreCase("Yes")){
	    		 String temp;
	    		 int parentNodeId = docMgmtImpl.getParentNodeId(workspaceID, nodeId);
	    		 DTOWorkSpaceNodeDetail getNodeDetail =  new DTOWorkSpaceNodeDetail();
	    		 getNodeDetail = docMgmtImpl.getNodeDetail(workspaceID, parentNodeId).get(0);
	    		 String nodeName = getNodeDetail.getNodeName();
	    		 int totalPQScripts = docMgmtImpl.getPQScriptsCount(workspaceID, parentNodeId,nodeName);
	    		 
	    			/*DTOWorkSpaceNodeAttrDetail getAttrValue = new DTOWorkSpaceNodeAttrDetail();
	    		 getAttrValue = docMgmtImpl.getNodeAttrDetail(workspaceID,nodeId).get(0);
	    		 
	    	 	String lstAttrVal = getAttrValue.getAttrValue();
	    		 
	    		 lstAttrVal = lstAttrVal.split("SC_")[1];
	    		 
	    		 int scriptNo = Integer.parseInt(lstAttrVal); 
	    		 scriptNo = scriptNo+1;*/
	    		 DecimalFormat decimalFormat = new DecimalFormat("0000");                
	    		 System.out.println(decimalFormat.format(totalPQScripts));
	    		 if(nodeName.equalsIgnoreCase("PQ Scripts")){
	    			 temp = "PQSC-"+decimalFormat.format(totalPQScripts);
	    		 }
	    		 else if(nodeName.equalsIgnoreCase("OQ Scripts")){
	    			 temp = "OQSC-"+decimalFormat.format(totalPQScripts);
	    		 }
	    		 else{
	    			 temp = "IQSC-"+decimalFormat.format(totalPQScripts);
	    		 }
	    		 wsnadto.setAttrValue(temp);
	    	 }
	    	 wsnadto.setModifyBy(userCode);
		    
	    	 docMgmtImpl.updateWorkspaceNodeAttributeValue(wsnadto);
	    	 
	    	 if(AttributeValueForNodeDisplayName==""){
	    		 AttributeValueForNodeDisplayName = attribute[0];
	    	 }else if(attribute[0]!=null && !attribute[0].equals("")){
	    		 AttributeValueForNodeDisplayName=AttributeValueForNodeDisplayName+","+attribute[0];
	    	 }
	    	    
		}
    	String attrValue=docMgmtImpl.getAttributeDetailByAttrName(workspaceID,1,"Project Start Date").getAttrValue();
		
		if(attrValue != null && !attrValue.equals("")){
			getSRFlagData=docMgmtImpl.getProjectTimelineSRFlagData(workspaceID);
			
			LocalDateTime startDate=null;
			LocalDateTime endDate = null;
			//
			int firstNode = docMgmtImpl.getChildNodeByParent(newNodeId,workspaceID).get(0).getNodeId();
			if(getSRFlagData.size()>0){
				if(docMgmtImpl.getTimelineDataForRepeatSection(workspaceID,firstNode).size()>0){
				DTOTimelineWorkspaceUserRightsMst dtor=new DTOTimelineWorkspaceUserRightsMst();
				/*dtor=docMgmtImpl.getTimelineDataForRepeatSection(workspaceID,firstNode).get(0);
				
				
				Timestamp attrValuer=dtor.getAdjustDate();
				LocalDateTime date=attrValuer.toLocalDateTime();*/
				Vector<DTOWorkSpaceUserRightsMst> getLastRightsDtl = new Vector<>();
				LocalDateTime date = null;
				getLastRightsDtl = docMgmtImpl.UserOnNodeTimelineTracking(workspaceID,firstNode);
				
				int temp= getLastRightsDtl.size()-1;
				date= getLastRightsDtl.get(temp).getStartDate().toLocalDateTime();							
				
				//int repeatNodeId = docMgmtImpl.getmaxNodeId(workspaceID);
				int parentNodeIdforAdjustDate=docMgmtImpl.getParentNodeId(workspaceID, firstNode);
				getRepeatAdjustDate=docMgmtImpl.getProjectTimelineAdjustDateRepeatNodeandChildNodeInfo(workspaceID,firstNode,parentNodeIdforAdjustDate);
				
				for(int k=0;k<getRepeatAdjustDate.size();k++){
					//System.out.println("Hours : "+hoursData.get(k).getHours());
					DTOWorkSpaceUserRightsMst dtotimeline = (DTOWorkSpaceUserRightsMst) getRepeatAdjustDate.get(k);
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
					
					//dtotimeline.setStartDate(Timestamp.valueOf(startDate));
					
					//dtotimeline.setEndDate(Timestamp.valueOf(endDate));
					
		            dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
		    							
					docMgmtImpl.updateTimelineDatesValue(dtotimeline);
				}
			}
			}else{
				hoursData=docMgmtImpl.getProjectTimelineDetailsForHours(workspaceID);
				String s[]=attrValue.split("/");
				Vector<DTOWorkSpaceUserRightsMst> getLastRightsDtl = new Vector<>();
				LocalDateTime date = null;
				getLastRightsDtl = docMgmtImpl.UserOnNodeTimelineTracking(workspaceID,firstNode);
				if(getLastRightsDtl.size()>0){
					int temp= getLastRightsDtl.size()-1;
					date= getLastRightsDtl.get(temp).getStartDate().toLocalDateTime();							
				}else{
					date=LocalDateTime.of(LocalDate.of(Integer.parseInt(s[0]),Integer.parseInt(s[1]),Integer.parseInt(s[2])), LocalTime.of(0,0));
				}
				for(int k=0;k<hoursData.size();k++){
					//System.out.println("Hours : "+hoursData.get(k).getHours());
					DTOWorkSpaceUserRightsMst dtotimeline = (DTOWorkSpaceUserRightsMst) hoursData.get(k);
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
					
					//dtotimeline.setStartDate(Timestamp.valueOf(startDate));
					
					//dtotimeline.setEndDate(Timestamp.valueOf(endDate));
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		            Date date1 = sdf.parse("1900-01-01 00:00:00.000");
		            Date date2 = sdf.parse(dtotimeline.getAdjustDate().toString());
		            if(!date1.before(date2) && getSRFlagData.size()==0){
		            	dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
		            }
		            else{
		            	
		            	dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
		            }
					
					docMgmtImpl.updateTimelineDatesValue(dtotimeline);
				}
			}
			
				
		}
    	   	
    	
    	// Setting cCloneFlag to 'Y' of the root node of newly created section 
		// AND Updating Nodes' Node Display Name
    	
    	
    	
    	DTOWorkSpaceNodeDetail wsnd = docMgmtImpl.getNodeDetail(workspaceID, newNodeId).get(0);
    	/*if(AttributeValueForNodeDisplayName.equals("")){
    		
    		//pattern used for attr values appended at the end of nodeDisplayName between '(' and ')'.
    		wsnd.setNodeDisplayName(wsnd.getNodeDisplayName().replaceAll("\\(.+\\)$",""));
    	}
    	else{
    		//pattern used for attr values appended at the end of nodeDisplayName between '(' and ')'.
    		wsnd.setNodeDisplayName(wsnd.getNodeDisplayName().replaceAll("\\(.+\\)$","")+"("+AttributeValueForNodeDisplayName+")");
    	}*/
    	if (AttributeValueForNodeDisplayName.equals("")) {

			if(wsnd.getNodeDisplayName().contains("{"))
			{
			wsnd.setNodeDisplayName(wsnd.getNodeDisplayName().substring(0,wsnd.getNodeDisplayName().indexOf('{')));
			}
			else
			{
				wsnd.setNodeDisplayName(wsnd.getNodeDisplayName());
			}
		} else {
			// pattern used for attr values appended at the end of
			// nodeDisplayName between '(' and ')'.
			if(wsnd.getNodeDisplayName().contains("{"))
			{
			wsnd.setNodeDisplayName(wsnd.getNodeDisplayName().substring(0,wsnd.getNodeDisplayName().indexOf('{'))
					+ "{" + AttributeValueForNodeDisplayName + "}");
			}
			else
			{
				wsnd.setNodeDisplayName(wsnd.getNodeDisplayName()+"{" + AttributeValueForNodeDisplayName + "}");
			}
		}
    	
    		wsnd.setCloneFlag('Y');
    		wsnd.setFolderName(folderName);
    		System.out.println("Ok0.2->"+wsnd.getNodeDisplayName().length());
    		docMgmtImpl.insertWorkspaceNodeDetail(wsnd, 2);//edit mode
    	/*//Updating Nodes' Node Display Name
		DTOWorkSpaceNodeDetail wsnd = new DTOWorkSpaceNodeDetail();
		wsnd.setWorkspaceId(workspaceID);
		wsnd.setNodeId(newNodeId);
		
		docMgmtImpl.updateNodeDisplayNameAccToEctdAttribute(wsnd, AttributeValueForNodeDisplayName);
		*/
	
    	/** Updating Node Display Name, Folder Name And Required Flag 
    	 *  for nodes repeated from nodes with required flag 'S'.
    	 *  Folder Name and Node Display Name will be updated as per the attribute name,value
    	 */
    	
    	System.out.println("Ok1");
    	
		if(repeatNodeDetail.getRequiredFlag() == 'S'){
			
			Vector<Integer> allNodeDtl = docMgmtImpl.getAllChildNodes(workspaceID, newNodeId, new Vector());
			
			allNodeDtl.addElement(new Integer(newNodeId));//Adding node itself
			
			for (Iterator<Integer> iterator = allNodeDtl.iterator(); iterator.hasNext();) {
				Integer nodeId = iterator.next();
				
				DTOWorkSpaceNodeDetail childNodeDtl = docMgmtImpl.getNodeDetail(workspaceID, nodeId.intValue()).get(0);
				
				for(int i=1;i<attrCount;i++)
				{
					 //Get attribute value to replace
					 String attrValueId = "attrValueId"+i;
					 String attrType = "attrType"+i;
			    	 String attrName = "attrName"+i;
			    
			    	 String[] attribute = null;
			    	 String newReplaceValue = null,newReplaceDisplayValue=null;
			    	 if(request.getParameter(attrType).equalsIgnoreCase("Combo")){
			    	 	 attribute = request.getParameter(attrValueId).split("&&&&");
			    	 }
			    	 else{
			    		 attribute = new String[]{request.getParameter(attrValueId)};
			    	 }
			    	 
			    	 newReplaceValue = attribute[0]; //Attribute Value
			    	 if(attribute.length > 1){
			    		 newReplaceDisplayValue= attribute[1]; //Attribute Display Value
			    	 }
			    	 else{
			    		 newReplaceDisplayValue = attribute[0]; //Attribute Value
			    	 }
			    	 
			    	 //Set replace value to node detail
			    	 childNodeDtl.setNodeDisplayName(childNodeDtl.getNodeDisplayName().replaceAll("\\["+request.getParameter(attrName)+"\\]", newReplaceDisplayValue));
			    	 childNodeDtl.setFolderName(childNodeDtl.getFolderName().replaceAll("\\["+request.getParameter(attrName)+"\\]", newReplaceValue.toLowerCase()));
				}
				
				childNodeDtl.setRequiredFlag('N');
				childNodeDtl.setFolderName(folderName);
				docMgmtImpl.insertWorkspaceNodeDetail(childNodeDtl, 2);//Update mode
			}
		}
		
		nodeId=nodeId;
		return SUCCESS;
	}
	
	public String EditSectionAttr(){
		
		isScriptCodeAutoGenrate= propertyInfo.getValue("isScriptCodeAutogenerate");
		String AttributeValueForNodeDisplayName="";
		DTOWorkSpaceNodeDetail repeatNodeDetail = docMgmtImpl.getNodeDetail(workspaceID, nodeId).get(0);
		HttpServletRequest request = ServletActionContext.getRequest();
		//all childs for the parent of repeatnodeid
		Vector<DTOWorkSpaceNodeDetail> allChilds = docMgmtImpl.getChildNodeByParent(repeatNodeDetail.getParentNodeId(),workspaceID);
		System.out.println("allChilds" + allChilds.size());
		for (int eachChild = 0 ; eachChild < allChilds.size() ; eachChild ++)
		{
			//for each child check attributes
			DTOWorkSpaceNodeDetail child = allChilds.get(eachChild);
		//	System.out.println("current child" + child.getNodeId());
			//if (child.getNodeId() == repeatNodeId)
			//	continue;
			if (!child.getNodeName().equals(repeatNodeDetail.getNodeName()))
			{
			//	System.out.println("skipping " + child.getNodeName());
				continue;
			}
			Vector<DTOWorkSpaceNodeAttrDetail> attrDetails = docMgmtImpl.getNodeAttrDetail(workspaceID,child.getNodeId());
		//	System.out.println("attr for this " + attrDetails.size());
			boolean sameNodes = true;
			boolean attrFound = false;
			for(int i=1;i<attrCount;i++)
			{
				 String attrValueId = "attrValueId"+i;
		    	 String attrType = "attrType"+i;
		    	 String attrId = "attrId"+i;
		    	 String[] attribute = null;
		    	 if(request.getParameter(attrType).equalsIgnoreCase("Combo"))
		    		 attribute = request.getParameter(attrValueId).split("&&&&");
		    	 else
		    		 attribute = new String[]{request.getParameter(attrValueId)};
		    	 
		    	 
		    	 
		    	 Integer attribId=Integer.parseInt(request.getParameter(attrId));
		    	// System.out.println("attribId"+attribId);
		    	 //System.out.println("attrval" + attribute[0]);
		    	 for (int indAttr=0;indAttr<attrDetails.size();indAttr++)
		    	 {
		    		 DTOWorkSpaceNodeAttrDetail dtoWsNdAttrDtl = attrDetails.get(indAttr);
		    		 if (dtoWsNdAttrDtl.getAttrId() == attribId && sameNodes)
		    		 {
		    			// System.out.println("dtoWsNdAttrDtl.getAttrId()" + dtoWsNdAttrDtl.getAttrId());
		    			// System.out.println("dtoWsNdAttrDtl.getAttrValue()" + dtoWsNdAttrDtl.getAttrValue());
		    			 if(!dtoWsNdAttrDtl.getAttrName().equalsIgnoreCase("Script Code")){
		    				 sameNodes = dtoWsNdAttrDtl.getAttrValue().equals(attribute[0]);
		    			 }
		    			 else{
		    				 sameNodes = false; 
		    			 }
		    			 attrFound = true; //if the attributes are not found
		    			 break;
		    		 }
		    	 }
			}
			if (sameNodes && attrFound)
			{
				errorMsg="Current attribute combination exists.";
				addActionError(errorMsg);
				return "show";
			}
		}		
		
		
		
		for(int i=1;i<attrCount;i++)	
	{
		DTOWorkSpaceNodeAttribute wsnadto = new DTOWorkSpaceNodeAttribute();
		//HttpServletRequest request = ServletActionContext.getRequest();
		String attrValueId = "attrValueId"+i;
   	 	String attrType = "attrType"+i;
   	 	String attrId = "attrId"+i;
	   	 String attrName = "attrName"+i;
	   	
	   	 String[] attribute = null;
	   	 if(request.getParameter(attrType).equalsIgnoreCase("Combo")){
	   	 	 
	   		 /* 'request.getParameter(attrValueId)' will return
	   		  * in format 'attrValue&&&&attrDisplayValue'
	   		  * 
	   		  */ 
	   		 attribute = request.getParameter(attrValueId).split("&&&&");
	   	 }
	   	 else{
	   		 attribute = new String[]{request.getParameter(attrValueId)};
	   	 }
	   	 
	   	 Integer attribId=Integer.parseInt(request.getParameter(attrId));
   	 
   	 DTOAttributeMst dtoAttr = docMgmtImpl.getAttribute(attribId);
   	 if (dtoAttr.getAttrForIndiId().equals("0002")
					&& request.getParameter(attrValueId)!=null && !request.getParameter(attrValueId).equals("")) {
				if (AttributeValueForNodeDisplayName == "")
					AttributeValueForNodeDisplayName = request
							.getParameter(attrValueId);
				else
					AttributeValueForNodeDisplayName += ","
							+ request.getParameter(attrValueId);
			}
	   	 wsnadto.setWorkspaceId(workspaceID);
	   	 wsnadto.setNodeId(nodeId);
	   	 wsnadto.setAttrId(attribId);
	   	 wsnadto.setAttrName(request.getParameter(attrName));
	   	 if(wsnadto.getAttrName().equalsIgnoreCase("Script Code") && attribute[0].equals("") && isScriptCodeAutoGenrate.equalsIgnoreCase("Yes")){
	   		String temp;
    		 int parentNodeId = docMgmtImpl.getParentNodeId(workspaceID, nodeId);
    		 DTOWorkSpaceNodeDetail getNodeDetail =  new DTOWorkSpaceNodeDetail();
    		 getNodeDetail = docMgmtImpl.getNodeDetail(workspaceID, parentNodeId).get(0);
    		 String nodeName = getNodeDetail.getNodeName();
    		 int totalPQScripts = docMgmtImpl.getPQScriptsCount(workspaceID, parentNodeId,nodeName);
 
    		 DecimalFormat decimalFormat = new DecimalFormat("0000");                
    		 System.out.println(decimalFormat.format(totalPQScripts));
    		 if(nodeName.equalsIgnoreCase("PQ Scripts")){
    			 temp = "PQSC-"+decimalFormat.format(totalPQScripts);
    		 }
    		 else if(nodeName.equalsIgnoreCase("OQ Scripts")){
    			 temp = "OQSC-"+decimalFormat.format(totalPQScripts);
    		 }
    		 else{
    			 temp = "IQSC-"+decimalFormat.format(totalPQScripts);
    		 }
    		 wsnadto.setAttrValue(temp);
    	 }
		 else{
			 wsnadto.setAttrValue(attribute[0]);//Attribute value
		 }
	   	
	   	 wsnadto.setModifyBy(userCode);
	    
   	 	docMgmtImpl.updateWorkspaceNodeAttributeValue(wsnadto);
   	}	
		
		DTOWorkSpaceNodeDetail wsnd = docMgmtImpl.getNodeDetail(workspaceID, nodeId).get(0);
		
		if (AttributeValueForNodeDisplayName.equals("")) {

			if(wsnd.getNodeDisplayName().contains("{"))
			{
			wsnd.setNodeDisplayName(wsnd.getNodeDisplayName().substring(0,wsnd.getNodeDisplayName().indexOf('{')));
			}
			else
			{
				wsnd.setNodeDisplayName(wsnd.getNodeDisplayName());
			}
		} else {
			// pattern used for attr values appended at the end of
			// nodeDisplayName between '(' and ')'.
			if(wsnd.getNodeDisplayName().contains("{"))
			{
			wsnd.setNodeDisplayName(wsnd.getNodeDisplayName().substring(0,wsnd.getNodeDisplayName().indexOf('{'))
					+ "{" + AttributeValueForNodeDisplayName + "}");
			}
			else
			{
				wsnd.setNodeDisplayName(wsnd.getNodeDisplayName()+"{" + AttributeValueForNodeDisplayName + "}");
			}
		}
    	
		int userId = Integer.parseInt(ActionContext.getContext().getSession()
				.get("userid").toString());
		//DTOWorkSpaceNodeDetail wsnd = new DTOWorkSpaceNodeDetail();
		
		/*wsnd.setNodeName(nodeName);
		wsnd.setNodeDisplayName(nodeDisplayName);*/
		wsnd.setFolderName(folderName);
		/*wsnd.setNodeId(nodeId);
		wsnd.setWorkspaceId(workspaceID);
		wsnd.setRemark("This is first node");*/
		
		docMgmtImpl.insertWorkspaceNodeDetail(wsnd, 2);//edit mode
		
		DTOWorkSpaceNodeDetail dto = docMgmtImpl.getNodeDetail(workspaceID,nodeId).get(0);
		
		dto.setWorkspaceId(workspaceID);
		dto.setNodeId(nodeId);
		dto.setNodeDisplayName(dto.getNodeDisplayName());
		dto.setNodeName(dto.getNodeName());
		dto.setFolderName(folderName);
		dto.setUserCode(userId);
		dto.setModifyBy(userId);
		dto.setRemark(remark);
		//dto.setPublishedFlag((isPublishable == null)?'N':'Y');
		dto.setPublishedFlag('Y');
		if (isOnlyFolder == 'Y')
			dto.setNodeTypeIndi(NodeTypeIndi.FOLDER_NODE);
		else
			dto.setNodeTypeIndi(dto.getNodeTypeIndi());

		docMgmtImpl.insertWorkspaceNodeDetail(dto, 2);
		dto.setStatusIndi('E');
		docMgmtImpl.insertWorkspaceNodeDetailhistory(dto, 1);
		
		nodeId=nodeId;
		return SUCCESS;
	}

	public char isOnlyFolder;
	public String isPublishable;
	public String htmlContent;
	public int repeatNodeId;
	public boolean isSection;
	public Vector repeatNodeAttrDtl;
	public DTOWorkSpaceNodeDetail sourceNodeDetail;
	public int attrCount;
	public String numberOfRepetitions;
	public String suffixStart;
	public Vector<DTOWorkSpaceNodeDetail> originalNodeWithAllclones;
	public int removeNodeId = 0;
	public int editNodeId;
	public String nodeDisplayName;
	public String fileName;
	public String fileExt; 
	public String remark;
	//public String attriValues;
	
	public Vector<DTOWorkSpaceNodeDetail> getChildNodes;
	public Vector<DTOWorkSpaceNodeHistory> getNodeHistory;
	public boolean flag=true;
	

	String appMode;
	String errorMsg;

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getAppMode() {
		return appMode;
	}

	public void setAppMode(String appMode) {
		this.appMode = appMode;
	}

	/*public String getAttriValues() {
		return attriValues;
	}

	public void setAttriValues(String attriValues) {
		this.attriValues = attriValues;
	}*/

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getRemoveNodeId() {
		return removeNodeId;
	}
	
	public void setRemoveNodeId(int removeNodeId) {
		this.removeNodeId = removeNodeId;
	}

	public int getRepeatNodeId() {
		return repeatNodeId;
	}

	public void setRepeatNodeId(int repeatNodeId) {
		this.repeatNodeId = repeatNodeId;
	}

	public boolean isSection() {
		return isSection;
	}

	public void setSection(boolean isSection) {
		this.isSection = isSection;
	}

	public Vector getRepeatNodeAttrDtl() {
		return repeatNodeAttrDtl;
	}

	public void setRepeatNodeAttrDtl(Vector repeatNodeAttrDtl) {
		this.repeatNodeAttrDtl = repeatNodeAttrDtl;
	}

	public DTOWorkSpaceNodeDetail getSourceNodeDetail() {
		return sourceNodeDetail;
	}

	public void setSourceNodeDetail(DTOWorkSpaceNodeDetail sourceNodeDetail) {
		this.sourceNodeDetail = sourceNodeDetail;
	}

	public int getAttrCount() {
		return attrCount;
	}

	public void setAttrCount(int attrCount) {
		this.attrCount = attrCount;
	}

	public String getNumberOfRepetitions() {
		return numberOfRepetitions;
	}

	public void setNumberOfRepetitions(String numberOfRepetitions) {
		this.numberOfRepetitions = numberOfRepetitions;
	}

	public String getSuffixStart() {
		return suffixStart;
	}

	public void setSuffixStart(String suffixStart) {
		this.suffixStart = suffixStart;
	}

	public Vector<DTOWorkSpaceNodeDetail> getOriginalNodeWithAllclones() {
		return originalNodeWithAllclones;
	}

	public void setOriginalNodeWithAllclones(Vector<DTOWorkSpaceNodeDetail> originalNodeWithAllclones) {
		this.originalNodeWithAllclones = originalNodeWithAllclones;
	}

	public int getEditNodeId() {
		return editNodeId;
	}

	public void setEditNodeId(int editNodeId) {
		this.editNodeId = editNodeId;
	}

	public String getNodeDisplayName() {
		return nodeDisplayName;
	}

	public void setNodeDisplayName(String nodeDisplayName) {
		this.nodeDisplayName = nodeDisplayName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getScriptCode() {
		return scriptCode;
	}

	public void setScriptCode(String scriptCode) {
		this.scriptCode = scriptCode;
	}
	
	
}
