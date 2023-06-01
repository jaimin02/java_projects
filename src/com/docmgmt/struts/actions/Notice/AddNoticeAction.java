package com.docmgmt.struts.actions.Notice;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.docmgmt.dto.DTONoticeMst;
import com.docmgmt.dto.DTOUserTypeMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.struts.actions.labelandpublish.FileManager;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AddNoticeAction extends ActionSupport
{
	public String subject;
	public String description;
	public File attachment;
	public String attachmentFileName;
	public String startDate;
	public String endDate;
	public String AllUserTypes;
	public String active;	
	static PropertyInfo propertyInfo = PropertyInfo.getPropInfo();	
	public String userTypesList;
	DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute() throws Exception 
	{	
		DTONoticeMst dtoNoticeMst=new DTONoticeMst();
		int userCode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		if (attachmentFileName!=null && !attachmentFileName.equals(""))
		{
			try
			{
				File attachmentsFolder = propertyInfo.getDir("AttachmentsFolder");
				FileManager fileManager=new FileManager();
				fileManager.copyDirectory(attachment, new File(attachmentsFolder.getAbsolutePath() + "/" + attachmentFileName));				
			}
			catch(Exception e)
			{
				attachmentFileName="";
			}
		}		
		Vector<DTOUserTypeMst> userTypes=docMgmtImpl.getAllUserType();
		userTypesList="";
		if (AllUserTypes.equals("All"))
		{
			for (int i=0;i<userTypes.size();i++)
			{
				DTOUserTypeMst dto=userTypes.get(i);
				userTypesList+=dto.getUserTypeCode()+",";
			}			
		}
		else
		{
			HttpServletRequest request=ServletActionContext.getRequest();			
			for (int i=0;i<userTypes.size();i++)
			{
				DTOUserTypeMst dto=userTypes.get(i);
				String paramValue=request.getParameter(dto.getUserTypeName());
				if (paramValue!=null && paramValue.equals("Y"))
					userTypesList+=dto.getUserTypeCode()+",";			
			}
		}		
		if (!userTypesList.equals("") && userTypesList.endsWith(","))
			userTypesList=userTypesList.substring(0,userTypesList.length()-1);
		dtoNoticeMst.setSubject(subject);
		dtoNoticeMst.setDescription(description);
		dtoNoticeMst.setAttachment(attachmentFileName);		
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
		Timestamp sDate= new Timestamp(dateFormat.parse(startDate).getTime());			
		dtoNoticeMst.setStartdate(sDate);
		Timestamp eDate= new Timestamp(dateFormat.parse(endDate).getTime());
		dtoNoticeMst.setEndDate(eDate);				
		dtoNoticeMst.setUserTypeCode(userTypesList);
		if (active!=null && active.equals("active"))
			dtoNoticeMst.setIsActive('Y');
		else
			dtoNoticeMst.setIsActive('N');
		dtoNoticeMst.setRemark("");
		dtoNoticeMst.setModifyBy(userCode);
		docMgmtImpl.insertIntoNoticeMst(dtoNoticeMst);			
		return SUCCESS;
	}
}