/*Date		Author	Activity	Details
 *=====================================
 *8th June	Nagesh	Created		Added execute method
 *9th June	Nagesh	Modified	Added methods for mark as read, reply, delete
 *10thJune	Nagesh	Modified	Added methods for getting rootpath,received comments for paging
 * */

package com.docmgmt.struts.actions.WelcomePage.comments;

import java.util.ArrayList;
import java.util.UUID;

import com.docmgmt.dto.DTOForumDtl;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * This class is the action class of the node comments shown on welcome page.
 * @author Nagesh
 **/
public class ShowComments extends ActionSupport
{
	public int receiverUserCode;
	DocMgmtImpl docMgmtImpl;
	public ArrayList<DTOForumDtl> allComments;
	public ArrayList<DTOForumDtl> sentComments;
	public ArrayList<DTOForumDtl> delComments;
	public String workspaceId;
	public int nodeId;
	public int userCode;
	public String message;		
	public String subjectId;
	public String htmlContent;
	public int pageNo=1;
	public int noOfRecords=5;
	public int noOfPages;
	public int showWhat;
	public String SubmissionDesc;
	public String alloweTMFCustomization;
	
	public String showCommentDocPath;
	public String showSentCommentDocPath;
	
	public int commentCount;
	public ArrayList<Integer> checkResolvedComments;
	public String resolvedComments;

	
	public ShowComments() 
	{
		docMgmtImpl=new DocMgmtImpl();		
		if (showWhat==0)
			showWhat=1;
	}
	
	/**
	 * recursive function to generate rootpath, ie to get list of all parents
	 * till root node for given commented node
	 * */
	String generateRootPath(DTOWorkSpaceNodeDetail dtoCurrNode,int noOfChars,String prefix)
	{
		if (dtoCurrNode.getParentNodeId()==-999 || dtoCurrNode.getParentNodeId()==0)
			return ((dtoCurrNode.getNodeName().length()>noOfChars)?dtoCurrNode.getNodeName().substring(0,noOfChars)+"...":dtoCurrNode.getNodeName());
		DTOWorkSpaceNodeDetail dto=docMgmtImpl.getNodeDetail(dtoCurrNode.getWorkspaceId(), dtoCurrNode.getParentNodeId()).get(0);
		return generateRootPath(dto,noOfChars,prefix+"") + ";" + ((dtoCurrNode.getNodeName().length()>noOfChars)?dtoCurrNode.getNodeName().substring(0,noOfChars)+"...":dtoCurrNode.getNodeName());
	}
	
	/**
	 * this method is called onclick of the welcome page tab
	 * it returns all the received, sent, and deleted comments
	 * for received comments uses the default pageNo=1 and noOfRecords=5
	 * */
	@Override
	public String execute() throws Exception 
	{
		
//		if(dispType.equals("1") && flag==0){
//			dispType="2";
//			
//		}
//		
//		if(dispType.equals("2") && flag==1)
//		{
//			dispType="3";
//		}
//		flag=1;
		
		//System.out.println("receiverUserCode"+receiverUserCode);
		//checkResolvedComments =docMgmtImpl.checkResolvedComments(nodeId,workspaceId);
		allComments=docMgmtImpl.getComments(receiverUserCode,pageNo,noOfRecords,nodeId,workspaceId);
		noOfPages=docMgmtImpl.getNumOfComments(receiverUserCode, noOfRecords,nodeId,workspaceId);
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		showCommentDocPath=propertyInfo.getValue("CommentDoc");
		//System.out.println("numPages"+noOfPages);
		//System.out.println("PageNo"+pageNo);
		for (int i=0;i<allComments.size();i++)
		{
			DTOForumDtl dto=allComments.get(i);
			dto.setRootPath(generateRootPath(docMgmtImpl.getNodeDetail(dto.getWorkspaceId(),dto.getNodeId()).get(0),80,""));
			String targetStr="";
			String str=dto.getRootPath();
			String appendStr="<br>&nbsp;";
			int nextInd;
			System.out.println("str="+str);
			if (str.indexOf(';')!=-1)
			{
				while(true)
				{
					nextInd=str.indexOf(';');
					if (nextInd==-1)
						break;
					if (targetStr.equals(""))
						targetStr=str.substring(0,nextInd);
					else
						targetStr+=appendStr+"-- "+str.substring(0,nextInd);
					appendStr+="&nbsp;&nbsp;";
					str=str.substring(nextInd+1);
				}
				//Added for Last node in the hierarchy (i.e. commented node itself)
				targetStr+=appendStr+"-- "+str;
				dto.setRootPath(targetStr);
				//System.out.println("rootpath"+targetStr);				
			}
		}
		if(nodeId==0){
		showSentCommentDocPath=propertyInfo.getValue("CommentDoc");
		
		System.out.println(showCommentDocPath.toString());
		sentComments=docMgmtImpl.getSentComments(receiverUserCode);
		for (int i=0;i<sentComments.size();i++)
		{
			DTOForumDtl dto=sentComments.get(i);
			dto.setRootPath(generateRootPath(docMgmtImpl.getNodeDetail(dto.getWorkspaceId(),dto.getNodeId()).get(0),80,""));
			String targetStr="";
			String str=dto.getRootPath();
			String appendStr="<br>&nbsp;";
			int nextInd;
			//System.out.println("str"+str);
			if (str.indexOf(';')!=-1)
			{
				while(true)
				{
					nextInd=str.indexOf(';');
					if (nextInd==-1)
						break;
					if (targetStr.equals(""))
						targetStr=str.substring(0,nextInd);
					else
						targetStr+=appendStr+"-- "+str.substring(0,nextInd);
					appendStr+="&nbsp;&nbsp;";
					str=str.substring(nextInd+1);
				}
				//Added for Last node in the hierarchy (i.e. commented node itself)
				targetStr+=appendStr+"-- "+str;
				dto.setRootPath(targetStr);
				//System.out.println("rootpath"+targetStr);				
			}
		}
		delComments=docMgmtImpl.getDeletedComments(receiverUserCode);
		for (int i=0;i<delComments.size();i++)
		{
			DTOForumDtl dto=delComments.get(i);
			dto.setRootPath(generateRootPath(docMgmtImpl.getNodeDetail(dto.getWorkspaceId(),dto.getNodeId()).get(0),80,""));
			String targetStr="";
			String str=dto.getRootPath();
			String appendStr="<br>&nbsp;";
			int nextInd;
			//System.out.println("str"+str);
			if (str.indexOf(';')!=-1)
			{
				while(true)
				{
					nextInd=str.indexOf(';');
					if (nextInd==-1)
						break;
					if (targetStr.equals(""))
						targetStr=str.substring(0,nextInd);
					else
						targetStr+=appendStr+"<img src='skin/tree.gif'>"+str.substring(0,nextInd);
					appendStr+="&nbsp;&nbsp;";
					str=str.substring(nextInd+1);
				}
				//Added for Last node in the hierarchy (i.e. commented node itself)
				targetStr+=appendStr+"-- "+str;
				dto.setRootPath(targetStr);
				//System.out.println("rootpath"+targetStr);				
			}
		}
	}
		System.out.println("allComments"+allComments.size());
		return SUCCESS;
	}	
	
	/**
	 * this is called on click of show button to mark the shown comment as read
	 * */
	public String markAsRead()
	{
		DTOForumDtl dto=new DTOForumDtl();
		dto.setSubjectId(subjectId);
		docMgmtImpl.insertForumDetails(dto,2);
		return SUCCESS;
	}
	
	/**
	 * delete a particular comment (cstatusindi='D')
	 * */
	public String deleteComment()
	{
		DTOForumDtl dto=new DTOForumDtl();
		dto.setSubjectId(subjectId);
		//System.out.println(docMgmtImpl.insertForumDetails(dto,3));
		htmlContent=ActionContext.getContext().getSession().get("userid").toString();		
		//System.out.println(htmlContent);
		return SUCCESS;
	}
	
	/**
	 * send reply to a particular comment
	 * */
	public String sendReply()
	{
	int	userId = Integer.parseInt(ActionContext.getContext().getSession()
				.get("userid").toString());
	String uuid =UUID.randomUUID().toString();
		//docMgmtImpl.updateForumDetails(subjectId,workspaceId,userCode);
		htmlContent=docMgmtImpl.insertForumComment(workspaceId, nodeId, userCode, message, "R",subjectId,uuid);
		
		//SubmissionDesc = docMgmtImpl.getupdatedNodedetails(nodeId, workspaceId, subjectId);
		
		//System.out.println(SubmissionDesc);
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		alloweTMFCustomization = propertyInfo.getValue("ETMFCustomization");
		alloweTMFCustomization= alloweTMFCustomization.toLowerCase();
		if(alloweTMFCustomization.equals("yes"))
		{
		docMgmtImpl.updateForumDetails(subjectId,workspaceId,nodeId,userId);
		}
		if (htmlContent!=null && !htmlContent.equals(""))
		{
			htmlContent="Reply Sent Successfully!!!";
		}
		else
		{
			htmlContent="<font color='red'>Error In Sending Reply!!!</font>";
		}
		return SUCCESS;
	}
	
	/**
	 * this method would return only the received comments according to the
	 * pageNo and noOfRecords
	 * */
	public String getReceivedComments()
	{
		allComments=docMgmtImpl.getComments(receiverUserCode,pageNo,noOfRecords,nodeId,workspaceId);
		//System.out.println("getReceivedComments"+allComments.size());
		noOfPages=docMgmtImpl.getNumOfComments(receiverUserCode, noOfRecords,nodeId,workspaceId);
		//System.out.println("numPages"+noOfPages);
		return SUCCESS;
	}
	public String getNodeStrucure()
	{
		String str=generateRootPath(docMgmtImpl.getNodeDetail(workspaceId,nodeId).get(0),80,"");
		String targetStr="";
		String appendStr="<br>&nbsp;";
		int nextInd;
		//System.out.println("str"+str);
		if (str.indexOf(';')!=-1)
		{
			while(true)
			{
				nextInd=str.indexOf(';');
				if (nextInd==-1)
					break;
				if (targetStr.equals(""))
					targetStr=str.substring(0,nextInd);
				else
					targetStr+=appendStr+"-- "+str.substring(0,nextInd);
				appendStr+="&nbsp;&nbsp;";
				str=str.substring(nextInd+1);
			}
			//Added for Last node in the hierarchy (i.e. commented node itself)
			targetStr+=appendStr+"-- "+str;
			//System.out.println("rootpath"+targetStr);				
		}
		else
			targetStr=str;
		//System.out.println("rootpath"+targetStr);
		htmlContent = targetStr;
		return SUCCESS;
	}
	public String commentCount(){
		int iReceiverUserCode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		commentCount=docMgmtImpl.getCommentsCount(iReceiverUserCode);
		htmlContent=Integer.valueOf(commentCount).toString();
		//return Integer.valueOf(commentCount).toString();
		return "html";
	}
	
}