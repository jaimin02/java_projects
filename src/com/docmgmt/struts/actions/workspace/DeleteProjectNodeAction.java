package com.docmgmt.struts.actions.workspace;



import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Vector;

import com.docmgmt.dto.DTOTimelineWorkspaceUserRightsMst;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public class DeleteProjectNodeAction extends ActionSupport  {
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	Vector<DTOWorkSpaceUserRightsMst> hoursData=new Vector<>();
	Vector<DTOWorkSpaceUserRightsMst> getSRFlagData=new Vector<>();
	Vector<DTOWorkSpaceUserRightsMst> getAdjustDateData=new Vector<>();
	
	
	@Override
	public String execute()
	{
		workspaceId=ActionContext.getContext().getSession().get("ws_id").toString();
		
				
		getSRFlagData=docMgmtImpl.getProjectTimelineSRFlagData(workspaceId);
		int parentNodeId = docMgmtImpl.getParentNodeId(workspaceId, nodeId);
		DTOTimelineWorkspaceUserRightsMst dto=new DTOTimelineWorkspaceUserRightsMst();
		Vector<DTOWorkSpaceNodeDetail> allSiblings = docMgmtImpl.getChildNodeByParent(parentNodeId, workspaceId);
		
		if(allSiblings.size() > 1)
			getSRFlagData=docMgmtImpl.getProjectTimelineSRFlagData(workspaceId);
			int isLeafNode = docMgmtImpl.isLeafNodes(workspaceId, nodeId);
			if(getSRFlagData.size()>0 && isLeafNode==1){
				dto=docMgmtImpl.getTimelineDataForRemove(workspaceId,nodeId).get(0);
			}
			docMgmtImpl.deleteNodeDetail(workspaceId, nodeId,remark);
		
			if(getSRFlagData.size()>0 && isLeafNode==1){
				
				Timestamp attrValue=dto.getAdjustDate();
				//String attrVal=attrValue.toLocalDateTime().toLocalDate().toString();
				//attrVal=attrVal.replace("-", "/");
				int parentNodeIdforAdjustDate=docMgmtImpl.getParentNodeId(workspaceId, nodeId);
				getAdjustDateData=docMgmtImpl.getProjectTimelineAdjustDateRepeatNodeInfo(workspaceId,nodeId,parentNodeIdforAdjustDate);
				
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
				
				
				String attrValue=docMgmtImpl.getAttributeDetailByAttrName(workspaceId,1,"Project Start Date").getAttrValue();
				
				if(attrValue != null && !attrValue.equals("")){
					hoursData=docMgmtImpl.getProjectTimelineDetailsForHours(workspaceId);
					
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
		
		return SUCCESS;
	}
	
	public String CheckDeleteSectionFromChkBox() throws NumberFormatException, SQLException{

		//workspaceId=ActionContext.getContext().getSession().get("ws_id").toString();
		if(workspaceId==null){
			htmlContent="Please select any node before performing any activity";
			return SUCCESS;
		}
		boolean isOddRow = true;
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
			getChildNodes=docMgmtImpl.getChildNodesForSectionDeletion(workspaceId, Integer.parseInt(selectValuesArray[i]),"D");
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
			if(getWorkspaceNodeDetail.get(0).getStatusIndi()!='D'){
					//docMgmtImpl.deleteNodeDetail(workspaceId, Integer.parseInt(selectValuesArray[i]),remark);
					deletedFlag=true;
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
			//docMgmtImpl.deleteNodeDetail(workspaceId, Integer.parseInt(selectValuesArray[0]),remark);
			deletedFlag=true;
		}
		//htmlContent="true";
	}
		if(deletedFlag==false){
			htmlContent="deletedFlagFalse";
			return SUCCESS;
		}
		htmlContent="true";
		return SUCCESS;
	}
	
	public String DeleteSectionFromChkBox() throws NumberFormatException, SQLException{

		//workspaceId=ActionContext.getContext().getSession().get("ws_id").toString();
		if(workspaceId==null){
			htmlContent="Please select any node before performing any activity";
			return SUCCESS;
		}
			
		boolean isOddRow = true;
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
			getChildNodes=docMgmtImpl.getChildNodesForSectionDeletion(workspaceId, Integer.parseInt(selectValuesArray[i]),"D");
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
						tableHtml.append("<TD style=\"font-size:16px; padding-left: 5px; border: 1px solid; word-wrap:break-word;\">"+getNodeHistory.get(0).getFileName()+"</TD></TR>");
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
			if(getWorkspaceNodeDetail.get(0).getStatusIndi()!='D'){
					docMgmtImpl.deleteNodeDetail(workspaceId, Integer.parseInt(selectValuesArray[i]),remark);
					deletedFlag=true;
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
			docMgmtImpl.deleteNodeDetail(workspaceId, Integer.parseInt(selectValuesArray[0]),remark);
			deletedFlag=true;
		}
		//htmlContent="true";
	}
		if(deletedFlag==false){
			htmlContent="deletedFlagFalse";
			return SUCCESS;
		}
		htmlContent="true";
		return SUCCESS;
	}
	
	public String CheckActiveSectionFromChkBox() throws NumberFormatException, SQLException{

		//workspaceId=ActionContext.getContext().getSession().get("ws_id").toString();
		if(workspaceId==null){
			htmlContent="Please select any node before performing any activity";
			return SUCCESS;
		}
		boolean isOddRow = true;
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
						tableHtml.append("<TD style=\"font-size:16px; padding-left: 5px; border: 1px solid; word-wrap:break-word;\">"+getNodeHistory.get(0).getFileName()+"</TD></TR>");
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
				//if(getWorkspaceNodeDetail.get(0).getStatusIndi()=='D' || getWorkspaceNodeDetail.get(0).getStatusIndi()=='E'){
				 if(getWorkspaceNodeDetail.get(0).getStatusIndi()=='D'){
					//docMgmtImpl.activeNodeDetail(workspaceId, Integer.parseInt(selectValuesArray[i]),remark);
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
			//docMgmtImpl.activeNodeDetail(workspaceId, Integer.parseInt(selectValuesArray[0]),remark);
			activatedFlag=true;
			}
	}
		if(activatedFlag==false){
			htmlContent="activatedFlagFalse";
			return SUCCESS;
		}
		htmlContent="true";
		return SUCCESS;
	}
	
	
	public String ActiveSectionFromChkBox() throws NumberFormatException, SQLException{

		//workspaceId=ActionContext.getContext().getSession().get("ws_id").toString();
		if(workspaceId==null){
			htmlContent="Please select any node before performing any activity";
			return SUCCESS;
		}
		boolean isOddRow = true;
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
		}
		htmlContent="true";
		return SUCCESS;
	}
	
	public int nodeId;
	public String htmlContent="";
	public String nodeIds;
	public int isLeafNode;
	public Vector<DTOWorkSpaceNodeHistory> getNodeHistory;
	public Vector<DTOWorkSpaceNodeDetail> getChildNodes;
	public String extraHtmlCode = "";
	public boolean flag=true;
	public boolean activatedFlag=false;
	public boolean deletedFlag=false;
	
	public String workspaceId;
	public String remark;


	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public String getWorkspaceId() {
		return workspaceId;
	}
	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}

	public String getNodeIds() {
		return nodeIds;
	}

	public void setNodeIds(String nodeIds) {
		this.nodeIds = nodeIds;
	}

	public int getIsLeafNode() {
		return isLeafNode;
	}

	public void setIsLeafNode(int isLeafNode) {
		this.isLeafNode = isLeafNode;
	}

	
}
