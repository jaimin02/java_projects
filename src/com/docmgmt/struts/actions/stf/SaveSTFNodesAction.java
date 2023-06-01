package com.docmgmt.struts.actions.stf;



import com.docmgmt.dto.DTOSTFNodeMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SaveSTFNodesAction extends ActionSupport  {
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		DTOSTFNodeMst stfnodemst = new DTOSTFNodeMst();
		
		stfnodemst.setNodeName(nodename);
    	stfnodemst.setNodeCategory(category);
    	stfnodemst.setRemark(remark);
    	stfnodemst.setModifyBy(userCode);
		
    	docMgmtImpl.insertIntoSTFNodeMst(stfnodemst);
		return SUCCESS;
	}
	
	public String category;
	public String remark;
	public String nodename;


	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getNodename() {
		return nodename;
	}
	public void setNodename(String nodename) {
		this.nodename = nodename;
	}
}