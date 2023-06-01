package com.docmgmt.struts.actions.TRMS;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTODepartmentMst;
import com.docmgmt.dto.DTOEmployeeMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class EmployeeDtlAction extends ActionSupport {
	
	private static final long serialVersionUID = 3043363178949014471L;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	public ArrayList<DTOEmployeeMst> employeeList = new ArrayList<DTOEmployeeMst>();
	public Vector<DTODepartmentMst> deptMstList = new Vector<DTODepartmentMst>();
	public String deptCode;
	public String empCode;
	public String empName;
	public String remark;
	public String jod;
	public String emailId;
	public String mobileNo;
	public String empNo;
	public String htmlContent="";
	public boolean update = false;
	public DTOEmployeeMst dtoEmployeeMst = new DTOEmployeeMst();
	
	@Override
	public String execute() throws Exception 
	{
		deptMstList = getDepartmentList();
		//employeeList = docMgmtImpl.getAllEmployeeList(1);
		return SUCCESS;
	}
	
	public String save() throws ParseException
	{
		int userId=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		String msg="";
		ArrayList<DTOEmployeeMst> empMstList = new ArrayList<DTOEmployeeMst>();
		DTOEmployeeMst dtoEmpMst = new DTOEmployeeMst();
		dtoEmpMst.setEmpCode(empCode);
		dtoEmpMst.setEmpName(empName);
		dtoEmpMst.setDeptCode(deptCode);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		dtoEmpMst.setjOD(new Timestamp(dateFormat.parse(jod).getTime()));
		dtoEmpMst.setEmailId(emailId);
		dtoEmpMst.setMobileNo(mobileNo);
		dtoEmpMst.setRemark(remark);
		if (update)
		{
			dtoEmpMst.setStatusIndi('E');
			dtoEmpMst.setEmpNo(Integer.parseInt(empNo));
			msg = "Employee's Details Updated successfully.";
		}
		else
		{
			dtoEmpMst.setStatusIndi('N');
			msg = "Employee's Details Saved successfully.";
		}
		dtoEmpMst.setModifyBy(userId);
		empMstList.add(dtoEmpMst);
		procedureCall(empMstList);
		htmlContent = msg;
		return SUCCESS;
	}
	
	public String delete()
	{
		/*
		int userId=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		DTOEmployeeMst dtoEmpMst = new DTOEmployeeMst();
		dtoEmpMst.setEmpNo(Integer.parseInt(empNo));
		ArrayList<DTOEmployeeMst> empMstList = getEmpDtlList(dtoEmpMst);
		dtoEmpMst = empMstList.get(0);
		dtoEmpMst.setStatusIndi('D');
		dtoEmpMst.setModifyBy(userId);
		empMstList = new ArrayList<DTOEmployeeMst>();
		empMstList.add(dtoEmpMst);
		procedureCall(empMstList);
		htmlContent = "Employee's Details Removes successfully.";
		*/
		return SUCCESS;
	}
	
	public String edit()
	{
		/*
		DTOEmployeeMst dtoEmpMst = new DTOEmployeeMst();
		dtoEmpMst.setEmpNo(Integer.parseInt(empNo));
		ArrayList<DTOEmployeeMst> empMstList = getEmpDtlList(dtoEmpMst);
		dtoEmployeeMst = empMstList.get(0);
		deptMstList = getDepartmentList();
		*/
		return SUCCESS;
	}
	
	private void procedureCall(ArrayList<DTOEmployeeMst> empMstList)
	{
		//docMgmtImpl.insertEmployeeMst(empMstList);	
	}
	/*
	private ArrayList<DTOEmployeeMst> getEmpDtlList(DTOEmployeeMst dtoEmpMst)
	{
		//return docMgmtImpl.getEmployeeList(dtoEmpMst, 0);
	}
	*/
	private Vector<DTODepartmentMst> getDepartmentList()
	{
		Vector<DTODepartmentMst> deptMstList = docMgmtImpl.getDepartmentDetail();
		for (int itrDeptMstList = 0; itrDeptMstList < deptMstList.size(); itrDeptMstList++) 
		{
			DTODepartmentMst dtoDeptMst = deptMstList.get(itrDeptMstList);
			if (dtoDeptMst.getStatusIndi() == 'D') 
			{
				deptMstList.remove(itrDeptMstList);
				itrDeptMstList--;				
			}
		}
		return deptMstList;
	}
}