package com.docmgmt.struts.actions.master.department;



import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.docmgmt.server.db.*;
import java.util.Vector;

public class AddDeptAction extends ActionSupport implements Preparable{
	
	private static final long serialVersionUID = 1L;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	@Override
	public String execute()
	{
		return SUCCESS;
	}
	
	public  void prepare()throws Exception{

		deptDetail=docMgmtImpl.getDepartmentDetail();
	}
	public String deptName;
	public Vector deptDetail;
	
	
	public Vector getDeptDetail() {
		return deptDetail;
	}


	public void setDeptDetail(Vector deptDetail) {
		this.deptDetail = deptDetail;
	}


	public String getDeptName() {
		return deptName;
	}


	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

}
