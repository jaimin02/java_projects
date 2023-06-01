package com.docmgmt.struts.actions.reports.SubmissionTracking;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import com.docmgmt.dto.DTOClientMst;
import com.docmgmt.dto.DTOLocationMst;
import com.docmgmt.dto.DTOSubmissionInfoAUDtl;
import com.docmgmt.dto.DTOSubmissionInfoCADtl;
import com.docmgmt.dto.DTOSubmissionInfoCHDtl;
import com.docmgmt.dto.DTOSubmissionInfoEU14Dtl;
import com.docmgmt.dto.DTOSubmissionInfoEU20Dtl;
import com.docmgmt.dto.DTOSubmissionInfoEUDtl;
import com.docmgmt.dto.DTOSubmissionInfoEUSubDtl;
import com.docmgmt.dto.DTOSubmissionInfoGCCDtl;
import com.docmgmt.dto.DTOSubmissionInfoTHDtl;
import com.docmgmt.dto.DTOSubmissionInfoUSDtl;
import com.docmgmt.dto.DTOSubmissionInfoZADtl;
import com.docmgmt.dto.DTOSubmissionMst;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.entities.Workspace.ProjectType;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SubmissionTracking extends ActionSupport {

	/**
	 * Created By : Rahul Goswami
	 * Created Date : 30-Nov-2010
	 */
	private static final long serialVersionUID = 1L;
	public Vector<DTOClientMst> clientMstList;
	public Vector<DTOLocationMst> regionMstList;
	public Vector<DTOWorkSpaceMst> workspaceMstList;
	public DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	public String region;
	public String client;
	public String workspaceMst;
	public String fromSubmissionDate;
	public String toSubmissionDate;
	public String currentDate;
	public String selectedProjectList;
	public ArrayList<DTOWorkSpaceMst> wsMstList = new ArrayList<DTOWorkSpaceMst>();
	public String reportType;
	public String htmlContent;
	
	@Override
	public String execute() throws Exception {
		int userGroupCode = Integer.parseInt( ActionContext.getContext().getSession().get("usergroupcode").toString());
		int userCode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
	 	regionMstList = docMgmtImpl.getLocationDtl();
		clientMstList = docMgmtImpl.getClientDetail();
		workspaceMstList = docMgmtImpl.getUserWorkspace(userGroupCode, userCode);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		currentDate = dateFormat.format(new Date());
		//System.out.println(currentDate);
		return SUCCESS;
	}
	
	public String Search()
	{
		ArrayList<String> wsIdsList = new ArrayList<String>();
		//check selected procjec list get from jsp page has more then one value
		//if yes then store it in arraylist for sending to master function.
		if(selectedProjectList.length() > 0)
		{
			if(selectedProjectList.matches(".*,.*"))
			{
				String[] wsIds = selectedProjectList.split(",");
				for(int itrWsIds =0; itrWsIds< wsIds.length;itrWsIds++)
					wsIdsList.add(wsIds[itrWsIds]);
			}
			else
				wsIdsList.add(selectedProjectList);
		}
		if(wsIdsList.size() > 0)
			getFullWorkspaceDtl(wsIdsList);
		
		return SUCCESS;
	}
	
	public void getFullWorkspaceDtl(ArrayList<String> wsIdsList)
	{
		wsMstList = new ArrayList<DTOWorkSpaceMst>();
		ArrayList<DTOSubmissionMst> submissionMstList = new ArrayList<DTOSubmissionMst>();
		Vector<DTOSubmissionInfoUSDtl> submissionInfoUSDtlList = null;
		Vector<DTOSubmissionInfoEU14Dtl> submissionInfoEU14DtlList = null;
		Vector<DTOSubmissionInfoEU20Dtl> submissionInfoEU20DtlList = null;
		Vector<DTOSubmissionInfoEUDtl> submissionInfoEUDtlList = null;
		Vector<DTOSubmissionInfoCADtl> submissionInfoCADtlList = null;
		Vector<DTOSubmissionInfoGCCDtl> submissionInfoGCCDtlList = null;
		Vector<DTOSubmissionInfoCHDtl> submissionInfoCHDtlList = null;
		Vector<DTOSubmissionInfoTHDtl> submissionInfoTHDtlList = null;
		Vector<DTOSubmissionInfoAUDtl> submissionInfoAUDtlList = null;
		Vector<DTOSubmissionInfoZADtl> submissionInfoZADtlList = null;
		Vector<DTOSubmissionInfoEUSubDtl> submissionInfoEUSubDtlList = null;
		Timestamp frmDtForComp = null;
		Timestamp toDtForComp = null;
		if (!fromSubmissionDate.trim().equals("")
				&& !toSubmissionDate.trim().equals("")) {
			// System.out.println("from date ::::"+fromSubmissionDate);
			// System.out.println("to date ::::"+toSubmissionDate);
			fromSubmissionDate = fromSubmissionDate.replaceAll("/", "-");
			toSubmissionDate = toSubmissionDate.replaceAll("/", "-");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date frmDt = dateFormat.parse(fromSubmissionDate);
				Date toDt = dateFormat.parse(toSubmissionDate);
				frmDtForComp = new Timestamp(frmDt.getTime());
				toDtForComp = new Timestamp(toDt.getTime());
				// System.out.println(frmDt+"**************************************************"+frmDtForComp);
				// System.out.println(toDt+"**************************************************"+toDtForComp);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		wsMstList = docMgmtImpl.getWorkSpaceDetail(wsIdsList);
		System.out.println("2");
		submissionMstList = docMgmtImpl
				.getSubmissionInfoForWorkspace(wsIdsList);

		for (int itrWsMst = 0; itrWsMst < wsMstList.size(); itrWsMst++) {
			DTOWorkSpaceMst dtoWsMst = new DTOWorkSpaceMst();
			dtoWsMst = wsMstList.get(itrWsMst);
			for (int itrsubMst = 0; itrsubMst < submissionMstList.size(); itrsubMst++) {
				DTOSubmissionMst dtoSubMst = new DTOSubmissionMst();
				dtoSubMst = submissionMstList.get(itrsubMst);
				if (dtoWsMst.getWorkSpaceId().equalsIgnoreCase(
						dtoSubMst.getWorkspaceId())) {
					String countryRegion = dtoSubMst.getCountryRegion();

					if (countryRegion.trim().equalsIgnoreCase("eu")) {
						String dtdVersion = dtoSubMst
								.getRegionalDTDVersion();
						DTOSubmissionMst submissiondtl = docMgmtImpl.getSubmissionInfoEURegion(dtoSubMst.getWorkspaceId());
						submissionInfoEUDtlList = new Vector<DTOSubmissionInfoEUDtl>();
						submissionInfoEUDtlList = docMgmtImpl
								.getWorkspaceSubmissionInfoEUDtl(dtoWsMst
										.getWorkSpaceId());
					if(submissionInfoEUDtlList.size() > 0) {
						for (int itrSubInfoEUDtl = 0; itrSubInfoEUDtl < submissionInfoEUDtlList
								.size(); itrSubInfoEUDtl++) {
							DTOSubmissionInfoEUDtl dto = new DTOSubmissionInfoEUDtl();
							dto = submissionInfoEUDtlList
									.get(itrSubInfoEUDtl);
							Timestamp dOS = dto.getSubmitedOn();
							// System.out.println(frmDtForComp +
							// "---------"+dOS+"----------"+toDtForComp);
							// System.out.println(dOS.after(frmDtForComp)
							// +
							// "=====" +dOS.before(toDtForComp));
							if (!((dOS.after(frmDtForComp) || dOS
									.equals(frmDtForComp)) && (dOS
									.before(toDtForComp) || dOS
									.equals(frmDtForComp)))) {
								submissionInfoEUDtlList
										.remove(itrSubInfoEUDtl);
								itrSubInfoEUDtl--;
							}
						}

						/*for (int itrSubInfoEUDtl = 0; itrSubInfoEUDtl < submissionInfoEUDtlList
								.size(); itrSubInfoEUDtl++) {
							DTOSubmissionInfoEUDtl dtoEuDtl = submissionInfoEUDtlList
									.get(itrSubInfoEUDtl);
							ArrayList<DTOSubmissionInfoEUSubDtl> dtoEuSubDtl = docMgmtImpl
									.getWorkspaceCMSSubmissionInfo(dtoEuDtl
											.getSubmissionInfoEUDtlId(),submissiondtl.getEUDtdVersion());
							if (dtoEuSubDtl.size() == 0) {
								DTOSubmissionInfoEUSubDtl dtoEuSubDtl2 = docMgmtImpl
										.getWorkspaceRMSSubmissionInfo(dtoEuDtl
												.getSubmissionInfoEUDtlId());
								dtoEuSubDtl = new ArrayList<DTOSubmissionInfoEUSubDtl>();
								dtoEuSubDtl.add(dtoEuSubDtl2);
							}
							dtoEuDtl.setCmsDtls(dtoEuSubDtl);
						}*/

						// add in workspacesubmissionmst list
						if (submissionInfoEUDtlList.size() > 0) {
							dtoSubMst
									.setSubmissionInfoEUDtls(submissionInfoEUDtlList);
							dtoWsMst.setSubmissionMst(dtoSubMst);
						}
					}

					submissionInfoEU14DtlList = new Vector<DTOSubmissionInfoEU14Dtl>();
						submissionInfoEU14DtlList = docMgmtImpl
								.getWorkspaceSubmissionInfoEU14Dtl(dtoWsMst
										.getWorkSpaceId());
					if(submissionInfoEU14DtlList.size()>0) {
						for (int itrSubInfoEU14Dtl = 0; itrSubInfoEU14Dtl < submissionInfoEU14DtlList
								.size(); itrSubInfoEU14Dtl++) {
							DTOSubmissionInfoEU14Dtl dto = new DTOSubmissionInfoEU14Dtl();
							dto = submissionInfoEU14DtlList
									.get(itrSubInfoEU14Dtl);
							Timestamp dOS = dto.getSubmitedOn();

							// System.out.println(frmDtForComp +
							// "---------"+dOS+"----------"+toDtForComp);
							// System.out.println(dOS.after(frmDtForComp)
							// +
							// "=====" +dOS.before(toDtForComp));
							if (!((dOS.after(frmDtForComp) || dOS
									.equals(frmDtForComp)) && (dOS
									.before(toDtForComp) || dOS
									.equals(frmDtForComp)))) {
								submissionInfoEU14DtlList
										.remove(itrSubInfoEU14Dtl);
								itrSubInfoEU14Dtl--;
							}
						}

						// for (int itrSubInfoEu14Dtl = 0;
						// itrSubInfoEu14Dtl < submissionInfoEU14DtlList
						// .size(); itrSubInfoEu14Dtl++) {
						//
						// DTOSubmissionInfoEU14Dtl dtoEu14Dtl =
						// submissionInfoEU14DtlList
						// .get(itrSubInfoEu14Dtl);
						// ArrayList<DTOSubmissionInfoEU14SubDtl>
						// dtoEu14SubDtl = docMgmtImpl
						// .getWorkspaceCMSSubmissionInfoEU14(dtoEu14Dtl
						// .getSubmissionInfoEU14DtlId());
						// if (dtoEu14SubDtl.size() == 0) {
						// DTOSubmissionInfoEU14SubDtl dtoEu14SubDtl2 =
						// docMgmtImpl
						// .getWorkspaceRMSSubmissionInfoEU14(dtoEu14Dtl
						// .getSubmissionInfoEU14DtlId());
						// dtoEu14SubDtl = new
						// ArrayList<DTOSubmissionInfoEU14SubDtl>();
						// dtoEu14SubDtl.add(dtoEu14SubDtl2);
						// }
						// dtoEu14Dtl.setCmsDtls(dtoEu14SubDtl);
						// }
						// add in workspacesubmissionmst list
						if (submissionInfoEU14DtlList.size() > 0) {
							dtoSubMst
									.setSubmissionInfoEU14Dtls(submissionInfoEU14DtlList);
							dtoWsMst.setSubmissionMst(dtoSubMst);
						}
					} 
				


						submissionInfoEU20DtlList = new Vector<DTOSubmissionInfoEU20Dtl>();
						submissionInfoEU20DtlList = docMgmtImpl
								.getWorkspaceSubmissionInfoEU20Dtl(dtoWsMst
										.getWorkSpaceId());
					if(submissionInfoEU20DtlList.size() > 0) {
						for (int itrSubInfoEU20Dtl = 0; itrSubInfoEU20Dtl < submissionInfoEU20DtlList
								.size(); itrSubInfoEU20Dtl++) {
							DTOSubmissionInfoEU20Dtl dto = new DTOSubmissionInfoEU20Dtl();
							dto = submissionInfoEU20DtlList
									.get(itrSubInfoEU20Dtl);
							Timestamp dOS = dto.getSubmitedOn();

							// System.out.println(frmDtForComp +
							// "---------"+dOS+"----------"+toDtForComp);
							// System.out.println(dOS.after(frmDtForComp)
							// +
							// "=====" +dOS.before(toDtForComp));
							if (!((dOS.after(frmDtForComp) || dOS
									.equals(frmDtForComp)) && (dOS
									.before(toDtForComp) || dOS
									.equals(frmDtForComp)))) {
								submissionInfoEU20DtlList
										.remove(itrSubInfoEU20Dtl);
								itrSubInfoEU20Dtl--;
							}
						}
						
						for (int itrSubInfoEu20Dtl = 0; itrSubInfoEu20Dtl < submissionInfoEU20DtlList
								.size(); itrSubInfoEu20Dtl++) {

							// DTOSubmissionInfoEU20Dtl dtoEu20Dtl =
							// submissionInfoEU20DtlList
							// .get(itrSubInfoEu20Dtl);
							// ArrayList<DTOSubmissionInfoEU20SubDtl>
							// dtoEu20SubDtl = docMgmtImpl
							// .getWorkspaceCMSSubmissionInfoEU20(dtoEu20Dtl
							// .getSubmissionInfoEU20DtlId());
							// if (dtoEu20SubDtl.size() == 0) {
							// DTOSubmissionInfoEU20SubDtl
							// dtoEu20SubDtl2 = docMgmtImpl
							// .getWorkspaceRMSSubmissionInfoEU20(dtoEu20Dtl
							// .getSubmissionInfoEU20DtlId());
							// dtoEu20SubDtl = new
							// ArrayList<DTOSubmissionInfoEU20SubDtl>();
							// dtoEu20SubDtl.add(dtoEu20SubDtl2);
							// }
							// dtoEu20Dtl.setCmsDtls(dtoEu20SubDtl);
						}
						// add in workspacesubmissionmst list
						if (submissionInfoEU20DtlList.size() > 0) {
							dtoSubMst
									.setSubmissionInfoEU20Dtls(submissionInfoEU20DtlList);
							dtoWsMst.setSubmissionMst(dtoSubMst);
						}

				}
			} else if (countryRegion.trim().equalsIgnoreCase("us")) {
						submissionInfoUSDtlList = new Vector<DTOSubmissionInfoUSDtl>();
						submissionInfoUSDtlList = docMgmtImpl
								.getWorkspaceSubmissionInfoUSDtl(dtoWsMst
										.getWorkSpaceId());
						for (int itrSubInfoUSDtl = 0; itrSubInfoUSDtl < submissionInfoUSDtlList
								.size(); itrSubInfoUSDtl++) {
							DTOSubmissionInfoUSDtl dto = new DTOSubmissionInfoUSDtl();
							dto = submissionInfoUSDtlList
									.get(itrSubInfoUSDtl);
							Timestamp dOS = dto.getSubmitedOn();
							// System.out.println(frmDtForComp +
							// "---------"+dOS+"----------"+toDtForComp);
							// System.out.println(dOS.after(frmDtForComp) +
							// "=====" +dOS.before(toDtForComp));
							if (!((dOS.after(frmDtForComp) || dOS
									.equals(frmDtForComp)) && (dOS
									.before(toDtForComp) || dOS
									.equals(frmDtForComp)))) {
								submissionInfoUSDtlList
										.remove(itrSubInfoUSDtl);
								itrSubInfoUSDtl--;
							}
						}

						
						if (submissionInfoUSDtlList.size() > 0) {
							dtoSubMst
									.setSubmissionInfoUSDtls(submissionInfoUSDtlList);
							dtoWsMst.setSubmissionMst(dtoSubMst);
						}
					} else if (countryRegion.trim().equalsIgnoreCase("ca")) {
						submissionInfoCADtlList = new Vector<DTOSubmissionInfoCADtl>();
						submissionInfoCADtlList = DocMgmtImpl
								.getWorkspaceSubmissionInfoCADtl(dtoWsMst
										.getWorkSpaceId());
						for (int itrSubInfoCADtl = 0; itrSubInfoCADtl < submissionInfoCADtlList
								.size(); itrSubInfoCADtl++) {
							DTOSubmissionInfoCADtl dto = new DTOSubmissionInfoCADtl();
							dto = submissionInfoCADtlList
									.get(itrSubInfoCADtl);
							Timestamp dOS = dto.getSubmitedOn();
							// System.out.println(frmDtForComp +
							// "---------"+dOS+"----------"+toDtForComp);
							// System.out.println(dOS.after(frmDtForComp) +
							// "=====" +dOS.before(toDtForComp));
							if (!((dOS.after(frmDtForComp) || dOS
									.equals(frmDtForComp)) && (dOS
									.before(toDtForComp) || dOS
									.equals(frmDtForComp)))) {
								submissionInfoCADtlList
										.remove(itrSubInfoCADtl);
								itrSubInfoCADtl--;
							}
						}

						// add in workspacesubmissionmst list
						if (submissionInfoCADtlList.size() > 0) {
							dtoSubMst
									.setSubmissionInfoCADtls(submissionInfoCADtlList);
							dtoWsMst.setSubmissionMst(dtoSubMst);
						}
					}
					else if (countryRegion.trim().equalsIgnoreCase("gcc")) {
						
						submissionInfoGCCDtlList = new Vector<DTOSubmissionInfoGCCDtl>();
						submissionInfoGCCDtlList = docMgmtImpl
								.getWorkspaceSubmissionInfoGCCDtl(dtoWsMst
										.getWorkSpaceId());
						for (int itrSubInfoGCCDtl = 0; itrSubInfoGCCDtl < submissionInfoGCCDtlList
						.size(); itrSubInfoGCCDtl++) {
							DTOSubmissionInfoGCCDtl dto = new DTOSubmissionInfoGCCDtl();
							dto = submissionInfoGCCDtlList
									.get(itrSubInfoGCCDtl);
							Timestamp dOS = dto.getSubmitedOn();
							// System.out.println(frmDtForComp +
							// "---------"+dOS+"----------"+toDtForComp);
							// System.out.println(dOS.after(frmDtForComp) +
							// "=====" +dOS.before(toDtForComp));
							if (!((dOS.after(frmDtForComp) || dOS
									.equals(frmDtForComp)) && (dOS
									.before(toDtForComp) || dOS
									.equals(frmDtForComp)))) {
								submissionInfoGCCDtlList
										.remove(itrSubInfoGCCDtl);
								itrSubInfoGCCDtl--;
							}
						}

					
						if (submissionInfoGCCDtlList.size() > 0) {
							dtoSubMst
									.setSubmissionInfoGCCDtls(submissionInfoGCCDtlList);
							dtoWsMst.setSubmissionMst(dtoSubMst);
						}
					}
					else if (countryRegion.trim().equalsIgnoreCase("ch")) {
						
						submissionInfoCHDtlList = new Vector<DTOSubmissionInfoCHDtl>();
						submissionInfoCHDtlList = docMgmtImpl
								.getWorkspaceSubmissionInfoCHDtl(dtoWsMst
										.getWorkSpaceId());
						for (int itrSubInfoCHDtl = 0; itrSubInfoCHDtl < submissionInfoCHDtlList
						.size(); itrSubInfoCHDtl++) {
							DTOSubmissionInfoCHDtl dto = new DTOSubmissionInfoCHDtl();
							dto = submissionInfoCHDtlList
									.get(itrSubInfoCHDtl);
							Timestamp dOS = dto.getSubmitedOn();
							// System.out.println(frmDtForComp +
							// "---------"+dOS+"----------"+toDtForComp);
							// System.out.println(dOS.after(frmDtForComp) +
							// "=====" +dOS.before(toDtForComp));
							if (!((dOS.after(frmDtForComp) || dOS
									.equals(frmDtForComp)) && (dOS
									.before(toDtForComp) || dOS
									.equals(frmDtForComp)))) {
								submissionInfoCHDtlList
										.remove(itrSubInfoCHDtl);
								itrSubInfoCHDtl--;
							}
						}

					
						if (submissionInfoCHDtlList.size() > 0) {
							System.out.println("CH entered----------------------------------");
							dtoSubMst
									.setSubmissionInfoCHDtls(submissionInfoCHDtlList);
							dtoWsMst.setSubmissionMst(dtoSubMst);
						}
					}
					else if (countryRegion.trim().equalsIgnoreCase("th")) {
						
						submissionInfoTHDtlList = new Vector<DTOSubmissionInfoTHDtl>();
						submissionInfoTHDtlList = docMgmtImpl
								.getWorkspaceSubmissionInfoTHDtl(dtoWsMst
										.getWorkSpaceId());
						for (int itrSubInfoTHDtl = 0; itrSubInfoTHDtl < submissionInfoTHDtlList
						.size(); itrSubInfoTHDtl++) {
							DTOSubmissionInfoTHDtl dto = new DTOSubmissionInfoTHDtl();
							dto = submissionInfoTHDtlList
									.get(itrSubInfoTHDtl);
							Timestamp dOS = dto.getSubmitedOn();
							// System.out.println(frmDtForComp +
							// "---------"+dOS+"----------"+toDtForComp);
							// System.out.println(dOS.after(frmDtForComp) +
							// "=====" +dOS.before(toDtForComp));
							if (!((dOS.after(frmDtForComp) || dOS
									.equals(frmDtForComp)) && (dOS
									.before(toDtForComp) || dOS
									.equals(frmDtForComp)))) {
								submissionInfoTHDtlList
										.remove(itrSubInfoTHDtl);
								itrSubInfoTHDtl--;
							}
						}

					
						if (submissionInfoTHDtlList.size() > 0) {
							//System.out.println("CH entered----------------------------------");
							dtoSubMst
									.setSubmissionInfoTHDtls(submissionInfoTHDtlList);
							dtoWsMst.setSubmissionMst(dtoSubMst);
						}
					}
					else if (countryRegion.trim().equalsIgnoreCase("au")) {
						
						submissionInfoAUDtlList = new Vector<DTOSubmissionInfoAUDtl>();
						submissionInfoAUDtlList = docMgmtImpl
								.getWorkspaceSubmissionInfoAUDtl(dtoWsMst
										.getWorkSpaceId());
						for (int itrSubInfoAUDtl = 0; itrSubInfoAUDtl < submissionInfoAUDtlList
						.size(); itrSubInfoAUDtl++) {
							DTOSubmissionInfoAUDtl dto = new DTOSubmissionInfoAUDtl();
							dto = submissionInfoAUDtlList
									.get(itrSubInfoAUDtl);
							Timestamp dOS = dto.getSubmitedOn();
							// System.out.println(frmDtForComp +
							// "---------"+dOS+"----------"+toDtForComp);
							// System.out.println(dOS.after(frmDtForComp) +
							// "=====" +dOS.before(toDtForComp));
							if (!((dOS.after(frmDtForComp) || dOS
									.equals(frmDtForComp)) && (dOS
									.before(toDtForComp) || dOS
									.equals(frmDtForComp)))) {
								submissionInfoAUDtlList
										.remove(itrSubInfoAUDtl);
								itrSubInfoAUDtl--;
							}
						}

					
						if (submissionInfoAUDtlList.size() > 0) {
							//System.out.println("CH entered----------------------------------");
							dtoSubMst
									.setSubmissionInfoAUDtls(submissionInfoAUDtlList);
							dtoWsMst.setSubmissionMst(dtoSubMst);
						}
					}
					else if (countryRegion.trim().equalsIgnoreCase("za")) {
						
						submissionInfoZADtlList = new Vector<DTOSubmissionInfoZADtl>();
						submissionInfoZADtlList = docMgmtImpl
								.getWorkspaceSubmissionInfoZADtl(dtoWsMst
										.getWorkSpaceId());
						for (int itrSubInfoZADtl = 0; itrSubInfoZADtl < submissionInfoZADtlList
						.size(); itrSubInfoZADtl++) {
							DTOSubmissionInfoZADtl dto = new DTOSubmissionInfoZADtl();
							dto = submissionInfoZADtlList
									.get(itrSubInfoZADtl);
							Timestamp dOS = dto.getSubmitedOn();
							// System.out.println(frmDtForComp +
							// "---------"+dOS+"----------"+toDtForComp);
							// System.out.println(dOS.after(frmDtForComp) +
							// "=====" +dOS.before(toDtForComp));
							if (!((dOS.after(frmDtForComp) || dOS
									.equals(frmDtForComp)) && (dOS
									.before(toDtForComp) || dOS
									.equals(frmDtForComp)))) {
								submissionInfoZADtlList
										.remove(itrSubInfoZADtl);
								itrSubInfoZADtl--;
							}
						}

					
						if (submissionInfoZADtlList.size() > 0) {
							System.out.println("ZA entered----------------------------------");
							dtoSubMst
									.setSubmissionInfoZADtls(submissionInfoZADtlList);
							dtoWsMst.setSubmissionMst(dtoSubMst);
						}
					}
				}
			}
		}
	
	}
	
	public String GetWorkspaces()
	{
		int userGroupCode = Integer.parseInt( ActionContext.getContext().getSession().get("usergroupcode").toString());
		int userCode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		String userType = ActionContext.getContext().getSession().get("usertypename").toString();
		System.out.println("getworkspaces.");
		DTOWorkSpaceMst dtoWsMst=new DTOWorkSpaceMst();
		ArrayList<Character> projectTypeList = new ArrayList<Character>();
		
		projectTypeList.add(ProjectType.ECTD_STANDARD);
		
		DTOUserMst userMst = new DTOUserMst();
		userMst.setUserGroupCode(userGroupCode);
		userMst.setUserCode(userCode);
		userMst.setUserType(userType);
		
		dtoWsMst.setClientCode(client);
		dtoWsMst.setLocationCode(region);
		Vector<DTOWorkSpaceMst> workspaceMstList = docMgmtImpl.searchWorkspaceByProjectType(dtoWsMst, userMst, projectTypeList);
					
		//Generate the option tag for replace in search page.
		for(int itrOption = 0; itrOption < workspaceMstList.size(); itrOption++) 
		{
			dtoWsMst = workspaceMstList.get(itrOption);
			htmlContent += "<option id=\""+dtoWsMst.getWorkSpaceId()+"##"+dtoWsMst.getLocationCode()+"##"+dtoWsMst.getClientCode()+"\" value=\""+dtoWsMst.getWorkSpaceId()+"##"+dtoWsMst.getLocationCode()+"##"+dtoWsMst.getClientCode()+"\" style=\"display: block;\">";
			htmlContent += dtoWsMst.getWorkSpaceDesc();
			htmlContent +="</option>";
		}
		return "WorspaceDetail";
	}
}
