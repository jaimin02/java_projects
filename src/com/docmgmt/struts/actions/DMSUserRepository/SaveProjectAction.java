package com.docmgmt.struts.actions.DMSUserRepository;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import com.docmgmt.dto.DTOStageMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceUserMst;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;
import com.docmgmt.server.webinterface.entities.Workspace.ProjectType;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public class SaveProjectAction extends ActionSupport {

private static final long serialVersionUID = 1L;
	
	private DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	
	public String nodeName;
	
	public String remark;
	public String folderName;
	public String defaultFileFormat;
	public String frmDate;
	public String toDate;
	public String htmlContent;
	public String templateDtl;
		
	public ArrayList<DTOWorkSpaceUserMst> usersList;
	public Vector<DTOStageMst> getStageDtl;
	public ArrayList<DTOWorkSpaceUserRightsMst> workspaceUserRightsList;
	
	@Override
	public String execute()
	{
		usersList = new ArrayList<DTOWorkSpaceUserMst>();
		workspaceUserRightsList = new ArrayList<DTOWorkSpaceUserRightsMst>();
		
		KnetProperties knetproperties = KnetProperties.getPropInfo();
		String baseWorkFolder = knetproperties.getValue("BaseWorkFolder");
		String basePublishFolder = knetproperties.getValue("BasePublishFolder");
		
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		DTOWorkSpaceMst dto = new DTOWorkSpaceMst();
		
		dto.setWorkSpaceDesc(nodeName);
		dto.setLocationCode("-999");
		dto.setDeptCode("-999");
		dto.setClientCode("-999");
		dto.setProjectCode("-999");
		dto.setDocTypeCode("-999");
		dto.setTemplateId(templateDtl.split("##")[0]);
		dto.setTemplateDesc( templateDtl.split("##")[1] != null ? templateDtl.split("##")[1] : "");
        dto.setBaseWorkFolder(baseWorkFolder);
        dto.setBasePublishFolder(basePublishFolder);
        dto.setCreatedBy(userId);
        dto.setLastAccessedBy(userId);
        dto.setProjectType(ProjectType.DMS_DOC_CENTRIC);
		dto.setModifyBy(userId);
		dto.setRemark(remark);
		dto.setModifyBy(userId);
		
		String newWrkSpaceId = docMgmtImpl.insertWorkspaceMst(dto);
		
		DTOWorkSpaceNodeDetail dtoWsNodeDtl = new DTOWorkSpaceNodeDetail();
		dtoWsNodeDtl.setWorkspaceId(newWrkSpaceId);
		dtoWsNodeDtl.setNodeID(1);
		dtoWsNodeDtl.setNodeNo(1);
		dtoWsNodeDtl.setNodeName(nodeName);
		dtoWsNodeDtl.setNodeDisplayName(nodeName);
		dtoWsNodeDtl.setNodeTypeIndi('N');
		dtoWsNodeDtl.setParentNodeId(0);
		dtoWsNodeDtl.setFolderName(folderName);
		dtoWsNodeDtl.setCloneFlag('N');
		dtoWsNodeDtl.setRequiredFlag('N');
		dtoWsNodeDtl.setCheckOutBy(0);
		dtoWsNodeDtl.setPublishedFlag('Y');
		dtoWsNodeDtl.setRemark(remark);
		dtoWsNodeDtl.setModifyBy(userId);
		dtoWsNodeDtl.setDefaultFileFormat(defaultFileFormat);
		
		docMgmtImpl.insertWorkspaceNodeDetail(dtoWsNodeDtl, 1);
		
		//dto = docMgmtImpl.getWorkSpaceDetail(newWrkSpaceId);
		DTOWorkSpaceUserMst dtoWsUserMst = new DTOWorkSpaceUserMst();
		
		dtoWsUserMst.setWorkSpaceId(newWrkSpaceId);
		dtoWsUserMst.setUserGroupCode(userGroupCode);
		dtoWsUserMst.setUserCode(userId);
		dtoWsUserMst.setAdminFlag('N');
		dtoWsUserMst.setRemark("");
		dtoWsUserMst.setModifyBy(userId);
		Calendar c=new GregorianCalendar();		
		
		int date=c.get(Calendar.DATE);
		String month="0" + (c.get(Calendar.MONTH)+1);
		month=month.substring(month.length()-2);
		int year=c.get(Calendar.YEAR);
		frmDate=year + "/" + month + "/" + date;
		//hardcode of 50 years
		year+=50;		
		toDate=year + "/" + month + "/" + date;
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		 
        try {
            Date frmDt =df.parse(frmDate);            
            Date toDt = df.parse(toDate);
            dtoWsUserMst.setFromDt(frmDt);
            dtoWsUserMst.setToDt(toDt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
     	usersList.add(dtoWsUserMst);
		docMgmtImpl.insertUpdateUsertoWorkspace(usersList);
		
		
		DTOWorkSpaceUserRightsMst dtoWsUserRightsMst = new DTOWorkSpaceUserRightsMst();
		
		getStageDtl = docMgmtImpl.getStageDetail();
		
		/*int stage = 10;
		for (int iStages = 0; iStages < getStageDtl.size(); iStages++) {
			DTOStageMst dtoStage = getStageDtl.get(iStages);
			
		}*/
		dtoWsUserRightsMst.setWorkSpaceId(newWrkSpaceId);
		dtoWsUserRightsMst.setUserGroupCode(userGroupCode);
		dtoWsUserRightsMst.setUserCode(userId);
		dtoWsUserRightsMst.setNodeId(1);
		dtoWsUserRightsMst.setCanReadFlag('Y');
		dtoWsUserRightsMst.setCanEditFlag('Y');
		dtoWsUserRightsMst.setCanAddFlag('Y');
		dtoWsUserRightsMst.setCanDeleteFlag('Y');
		dtoWsUserRightsMst.setAdvancedRights("Y");
		dtoWsUserRightsMst.setStageId(10);
		dtoWsUserRightsMst.setRemark("");
		dtoWsUserRightsMst.setModifyBy(userId);
		dtoWsUserRightsMst.setStatusIndi('N');
		
		workspaceUserRightsList.add(dtoWsUserRightsMst);
		docMgmtImpl.insertMultipleUserRights(workspaceUserRightsList);
		
		htmlContent = "Project created successfully.";
		return SUCCESS;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
}
