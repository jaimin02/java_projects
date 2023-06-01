package com.docmgmt.struts.actions.TRMS;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.docmgmt.dto.DTOEmployeeMst;
import com.docmgmt.dto.DTOTrainingAttendanceMst;
import com.docmgmt.dto.DTOTrainingRecordDetails;
import com.docmgmt.dto.DTOTrainingScheduleingDetails;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class TrainingScheduleDtlsAction extends ActionSupport {

	private static final long serialVersionUID = 3623386085162852881L;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public String trainingScheduleNo="0";
	public String trainingRecordNo;
	public String trainingScheduleDesc;
	public String trainingStartDate;
	public String trainingStartTime;
	public String trainingEndDate;
	public String trainingEndTime;
	public String trainingRefDocPath;
	public String trainingRefNodeId = "0";
	public String trainingRefTranNo = "0";
	public String remark;
	public ArrayList<DTOTrainingRecordDetails> tRDtlsList = new ArrayList<DTOTrainingRecordDetails>();
	public ArrayList<DTOTrainingScheduleingDetails> tSDtlList = new ArrayList<DTOTrainingScheduleingDetails>();
	public ArrayList<DTOEmployeeMst> empList = new ArrayList<DTOEmployeeMst>();
	public ArrayList<DTOTrainingAttendanceMst> tAMstList = new ArrayList<DTOTrainingAttendanceMst>();
	public String htmlContent = "";
	public boolean refDoc = true;
	public String status = "N";
	public String trainee = "";
	public String trainer = "";
	public String presentEmp = "";
	public String absentEmp = "";
	public String selectdate = "";
	public String search = "";
	public String conditionOn = "";
	public String trainingDate = "";
	public String empNo= "0";
	@Override
	public String execute() throws Exception 
	{
		//tRDtlsList = docMgmtImpl.getAllTRDetailList(1);
		return SUCCESS;
	}

	public String getDetailByTRNo()
	{  
		/*
		tSDtlList = docMgmtImpl.getTRNoWiseTSDetailList(Integer.parseInt(trainingRecordNo));
		empList = docMgmtImpl.getAllEmployeeList(1);
			/*Collections.sort(tSDtlList, new Comparator<DTOTrainingScheduleingDetails>() {
				public int compare(DTOTrainingScheduleingDetails arg0,
						DTOTrainingScheduleingDetails arg1) {
					if(arg0.getTrainingStartDate().before(arg1.getTrainingStartDate()))
						return -1;
					else if(arg0.getTrainingStartDate().after(arg1.getTrainingStartDate()))
						return 1;
					else
						return arg0.getTrainingStartTime().compareTo(arg1.getTrainingStartTime());
				}
			});*/
		return SUCCESS;
	}
	
	public String save() throws ParseException
	{
		/*
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss");
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		DTOTrainingScheduleingDetails dto = new DTOTrainingScheduleingDetails();
		dto.setTrainingScheduleNo(Integer.parseInt(trainingScheduleNo));
		dto.setTrainingRecordNo(Integer.parseInt(trainingRecordNo));
		dto.setTrainingScheduleDesc(trainingScheduleDesc);
		dto.setTrainingStartDate(new Timestamp(df.parse(trainingStartDate).getTime()));
		dto.setTrainingEndDate(new Timestamp(df.parse(trainingEndDate).getTime()));
		dto.setTrainingStartTime(new Time(tf.parse(trainingStartTime).getTime()));
		dto.setTrainingEndTime(new Time(tf.parse(trainingEndTime).getTime()));
		
		if(refDoc)
			dto.setTrainingRefDocPath("");
		else
		{
			dto.setTrainingRefNodeId(0);
			dto.setTrainingRefTranNo(0);
		}		
		dto.setRemark(remark);
		dto.setStatusIndi(status.trim().equalsIgnoreCase("E")?'E':'N');
		dto.setModifyBy(userId);
		ArrayList<DTOTrainingScheduleingDetails> tSDtlsList = new ArrayList<DTOTrainingScheduleingDetails>();
		tSDtlsList.add(dto);
		trainingScheduleNo =String.valueOf(docMgmtImpl.InsertTrainingScheduleDetail(tSDtlsList));

		//Trainee and Trainer assignemnt and attendance.
		// Default is present.
		String []traineeList = trainee.split(",");
		String []trainerList = trainer.split(",");

		if (status.trim().equalsIgnoreCase("E"))
		{
			tAMstList = docMgmtImpl.getTSNoWiseTADetailList(Integer.parseInt(trainingScheduleNo));
			if (traineeList.length > 0) 
				traineeList = removeCommonEmp(traineeList,'N');
			if (trainerList.length > 0)
				trainerList = removeCommonEmp(trainerList,'Y');
			for (int itrTAMstList = 0; itrTAMstList < tAMstList.size(); itrTAMstList++) 
				tAMstList.get(itrTAMstList).setStatusIndi('D');
			
			tAMstList.addAll(addTrainerAndTrainee(userId, traineeList, trainerList));
			
		}
		else
			tAMstList = addTrainerAndTrainee(userId,traineeList,trainerList );
		
		docMgmtImpl.insert_TrainingAttendanceMst(tAMstList);
		
		if (dto.getStatusIndi() == 'N') 
			htmlContent = "Training Detailed Successfully.";
		else
			htmlContent = "Training Revised Successfully.";
		*/
		return SUCCESS;
	}
	
	private ArrayList<DTOTrainingAttendanceMst> addTrainerAndTrainee(int userId,String []traineeList,String []trainerList )
	{
		/*
		ArrayList<DTOTrainingAttendanceMst> tAMstListNew = new ArrayList<DTOTrainingAttendanceMst>();
		DTOTrainingAttendanceMst dtoTAMst = new DTOTrainingAttendanceMst();
		if (trainerList.length > 0) {
			for (String trainer1 : trainerList) {
				if (trainer1 != null && !(trainer1.trim().equalsIgnoreCase(""))) 
				{
					dtoTAMst = new DTOTrainingAttendanceMst();
					dtoTAMst.setTrainingScheduleNo(Integer.parseInt(trainingScheduleNo));
					dtoTAMst.setEmpNo(Integer.parseInt(trainer1));
					dtoTAMst.setIsPresent('Y'); ///Default is present.
					dtoTAMst.setIsTraner('Y');
					dtoTAMst.setModifyBy(userId);
					dtoTAMst.setStatusIndi('N');
					tAMstListNew.add(dtoTAMst);
					dtoTAMst = null;
				}
			}
		}
		if (traineeList.length > 0) {
			for (String trainee1 : traineeList) {
				if (trainee1 != null && !(trainee1.trim().equalsIgnoreCase(""))) 
				{
					dtoTAMst = new DTOTrainingAttendanceMst();
					dtoTAMst.setTrainingScheduleNo(Integer.parseInt(trainingScheduleNo));
					dtoTAMst.setEmpNo(Integer.parseInt(trainee1));
					dtoTAMst.setIsPresent('Y'); ///Default is present.
					dtoTAMst.setIsTraner('N');
					dtoTAMst.setModifyBy(userId);
					dtoTAMst.setStatusIndi('N');
					tAMstListNew.add(dtoTAMst);
					dtoTAMst = null;
				}
			}
		}
		*/
		//return tAMstListNew;
		return null;
	}
	
	private String[] removeCommonEmp(String []empList,char isTrainer)
	{
		for (int itrEmpList = 0; itrEmpList < empList.length; itrEmpList++) 
		{
				for (int itrTAMstList = 0; itrTAMstList < tAMstList.size(); itrTAMstList++) 
				{
					DTOTrainingAttendanceMst dto = tAMstList.get(itrTAMstList);
					if (dto.getIsTraner() == isTrainer && empList[itrEmpList] != null && !(empList[itrEmpList].trim().equalsIgnoreCase("")) && dto.getEmpNo() == Integer.parseInt(empList[itrEmpList])) 
					{
						tAMstList.remove(itrTAMstList);
						itrTAMstList--;
						empList[itrEmpList] = null;
						continue;
					}
				}
			}
		return empList;
	}
	/*
	public String delete()
	{
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		ArrayList<DTOTrainingScheduleingDetails> tSDtlsList = docMgmtImpl.getTSNoWiseTSDetailList(Integer.parseInt(trainingScheduleNo));
		tSDtlsList.get(0).setStatusIndi('D');
		tSDtlsList.get(0).setModifyBy(userId);
		docMgmtImpl.InsertTrainingScheduleDetail(tSDtlsList);
		htmlContent = "Training Schedule Removed Successfully.";
		return SUCCESS;
	}
	
	public String edit()
	{
		tSDtlList = docMgmtImpl.getTSNoWiseTSDetailList(Integer.parseInt(trainingScheduleNo));
		empList = docMgmtImpl.getAllEmployeeList(1);
		tAMstList = docMgmtImpl.getTSNoWiseTADetailList(Integer.parseInt(trainingScheduleNo));
		DTOEmployeeMst dtoEmpMst;
		for (int itrEmpList = 0; itrEmpList < empList.size(); itrEmpList++) 
		{
			dtoEmpMst = empList.get(itrEmpList);
			for (int itrTAList = 0; itrTAList < tAMstList.size(); itrTAList++) 
			{
				if (dtoEmpMst.getEmpNo() == tAMstList.get(itrTAList).getEmpNo()) 
				{
					empList.remove(itrEmpList);
					itrEmpList--;
					continue;
				}
			}
			dtoEmpMst  = null;
		}
		return SUCCESS;
	}
	
	public String getAttendanceDtl()
	{
		tAMstList = docMgmtImpl.getTSNoWiseTADetailList(Integer.parseInt(trainingScheduleNo));
		return SUCCESS;
	}
	
	public String setAttendanceDtl()
	{
		tAMstList = docMgmtImpl.getTSNoWiseTADetailList(Integer.parseInt(trainingScheduleNo));
		String []empList = presentEmp.split(",");
		if (empList.length > 0){
			for (int itrEmpList = 0; itrEmpList < empList.length; itrEmpList++){
				for (int itrTAMstList = 0; itrTAMstList < tAMstList.size(); itrTAMstList++){
					DTOTrainingAttendanceMst dto = tAMstList.get(itrTAMstList);
					if ( empList[itrEmpList] != null && !(empList[itrEmpList].trim().equalsIgnoreCase("")) && dto.getTrAttNo() == Integer.parseInt(empList[itrEmpList])){
						if (dto.getIsPresent() == 'Y') {
							tAMstList.remove(itrTAMstList);
							itrTAMstList--;
							empList[itrEmpList] = null;
							continue;	
						}
						else{
							dto.setIsPresent('Y');
							dto.setStatusIndi('E');
						}
						
					}
				}
			}
		}
				
		empList = absentEmp.split(",");
		if (empList.length > 0){
			for (int itrEmpList = 0; itrEmpList < empList.length; itrEmpList++) {
				for (int itrTAMstList = 0; itrTAMstList < tAMstList.size(); itrTAMstList++){
					DTOTrainingAttendanceMst dto = tAMstList.get(itrTAMstList);
					if ( empList[itrEmpList] != null && !(empList[itrEmpList].trim().equalsIgnoreCase("")) && dto.getTrAttNo() == Integer.parseInt(empList[itrEmpList])){
						if (dto.getIsPresent() == 'N') {
							tAMstList.remove(itrTAMstList);
							itrTAMstList--;
							empList[itrEmpList] = null;
							continue;	
						}
						else{
							dto.setIsPresent('N');
							dto.setStatusIndi('E');
						}
					}
				}
			}
		}
			
		docMgmtImpl.insert_TrainingAttendanceMst(tAMstList);
		
		htmlContent = "Training Attendace Updated Successfully.";
		return SUCCESS;
	}
	*/
	public String getAttRpt()
	{
		//if selectedate = 'N ' then trainingstartdate means "from" date and trainingenddate means "to" date  
		//if selectedate = 'Y ' then trainingstartdate means "on" or "before" of "after" that date.
		int conditionOnDate = 0;
		/* if 0 then trainingStartDate or trainingEndDate condition
		 * if 1 then trainingStratDate condtion
		 * if 2 then trainingEndDate condition
		 * */
		
		int conditionOperater = 0;
		/* if 0 then On condtion[==] (same date on condtionDate parameter) 
		 * if 1 then Before condition
		 * if 2 then After condition
		 * if 3 then Between condition
		 * */
		if (selectdate != null && !selectdate.trim().equalsIgnoreCase("") ) 
		{
			if (selectdate.trim().equalsIgnoreCase("Y")) 
			{
				if (search != null && !search.trim().equalsIgnoreCase("")) 
				{
					if (search.trim().equalsIgnoreCase("O")) 
						conditionOperater = 0;
					else if (search.trim().equalsIgnoreCase("B"))
						conditionOperater = 1;
					else if (search.trim().equalsIgnoreCase("A")) 
						conditionOperater = 2;
				}
				trainingEndDate = trainingStartDate = trainingDate;
			}
			else
			{
				if (trainingStartDate != null && !trainingStartDate.trim().equalsIgnoreCase("") && trainingEndDate != null && !trainingEndDate.trim().equalsIgnoreCase("")) 
					conditionOperater = 3;
			}
		}
		if (conditionOn != null && !conditionOn.trim().equalsIgnoreCase("")) {
			if (conditionOn.trim().equalsIgnoreCase("A"))
				conditionOnDate = 0;
			else if (conditionOn.trim().equalsIgnoreCase("S"))
				conditionOnDate = 1;
			else if (conditionOn.trim().equalsIgnoreCase("E"))
				conditionOnDate = 2;
		}
		if (trainingRecordNo != null && !trainingRecordNo.trim().equalsIgnoreCase("")) {
			if (trainingRecordNo.trim().equalsIgnoreCase("-999"))
				trainingRecordNo = "0";
		}
		else
			trainingRecordNo = "0";
		
		//tRDtlsList = docMgmtImpl.getTrainingAttendaceReport(Integer.parseInt(trainingRecordNo),trainingStartDate,trainingEndDate,conditionOnDate,conditionOperater);
		return SUCCESS;
	}

	public String getEmpDtl()
	{
		//empList = docMgmtImpl.getTrainingEmployeeDtl(0);
		return SUCCESS;
	}
	
	public String getEmpRpt()
	{
		if (empNo != null && !empNo.trim().equalsIgnoreCase("")) {
			if (empNo.trim().equalsIgnoreCase("-999"))
				empNo = "0";
		}
		else
			empNo = "0";
		int conditionOnDate = 0;
		/* if 0 then trainingStartDate or trainingEndDate condition
		 * if 1 then trainingStratDate condtion
		 * if 2 then trainingEndDate condition
		 * */
		
		int conditionOperater = 0;
		/* if 0 then On condtion[==] (same date on condtionDate parameter) 
		 * if 1 then Before condition
		 * if 2 then After condition
		 * if 3 then Between condition
		 * */
		if (selectdate != null && !selectdate.trim().equalsIgnoreCase("") ) 
		{
			if (selectdate.trim().equalsIgnoreCase("Y")) 
			{
				if (search != null && !search.trim().equalsIgnoreCase("")) 
				{
					if (search.trim().equalsIgnoreCase("O")) 
						conditionOperater = 0;
					else if (search.trim().equalsIgnoreCase("B"))
						conditionOperater = 1;
					else if (search.trim().equalsIgnoreCase("A")) 
						conditionOperater = 2;
				}
				trainingEndDate = trainingStartDate = trainingDate;
			}
			else
			{
				if (trainingStartDate != null && !trainingStartDate.trim().equalsIgnoreCase("") && trainingEndDate != null && !trainingEndDate.trim().equalsIgnoreCase("")) 
					conditionOperater = 3;
			}
		}
		if (conditionOn != null && !conditionOn.trim().equalsIgnoreCase("")) {
			if (conditionOn.trim().equalsIgnoreCase("A"))
				conditionOnDate = 0;
			else if (conditionOn.trim().equalsIgnoreCase("S"))
				conditionOnDate = 1;
			else if (conditionOn.trim().equalsIgnoreCase("E"))
				conditionOnDate = 2;
		}
	//	empList = docMgmtImpl.getEmployeeWiseTrainingReport(Integer.parseInt(empNo),trainingStartDate,trainingEndDate,conditionOnDate,conditionOperater);
		return SUCCESS;
	}
	
	public static  void main(String []a) throws Exception
	{
		//Date d = new Date(System.currentTimeMillis());
		/*System.out.println(d.getTime()+"------"+d+"--------------");
		String s = "";
		s = String.valueOf(d.getTime());
		System.out.println(s+"--------------------------s");*/
		//Timestamp t = new Timestamp(0);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss"); 
		java.util.Date date = sdf.parse("14:30:00"); 
		Time t = new Time(date.getTime());
		//java.sql.Timestamp timest = new java.sql.Timestamp(date.getTime()); 
		System.out.println(t+"----------------ttttttttttttttt");
		//System.out.println(t.getHours() - 12 + ":" + t.getMinutes()+"------------"+t);
		/*ArrayList<String> a1 = new ArrayList<String>();
		a1.add("a");
		a1.add("b");
		a1.add("d");
		a1.add("c");
		a1.add("e");
		a1.add("f");
		System.out.println(a1);
		for (int i = 0; i < a1.size(); i++) 
			a1.set(i, "\""+a1.get(i)+"\"");*/
		//System.out.println(a1.toString().replace("[", "(").replace("]", ")"));
	}
}
