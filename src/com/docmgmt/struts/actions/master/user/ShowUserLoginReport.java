package com.docmgmt.struts.actions.master.user;

import java.lang.management.ManagementFactory;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.services.CryptoLibrary;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ShowUserLoginReport extends ActionSupport
{
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	private CryptoLibrary encryption = new CryptoLibrary();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//private DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		@Override
	public String execute() throws MalformedObjectNameException, AttributeNotFoundException, InstanceNotFoundException, MBeanException, ReflectionException
	{
			UserDtl();
			return SUCCESS;
	}
		
		public void UserDtl() throws MalformedObjectNameException, AttributeNotFoundException, InstanceNotFoundException, MBeanException, ReflectionException{
			
			MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
			ObjectName objectName = new ObjectName("Catalina:type=Manager,context=/,host=localhost");
			int activeSessions = (Integer) mBeanServer.getAttribute(objectName, "activeSessions");
			System.out.println(activeSessions);
					
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			userGroupCodeForEdit = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
			userCodeForEdit = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
			System.out.println(userTypeCode);
			System.out.println(fromSubmissionDate);
			System.out.println(toSubmissionDate);
					
			userdtl=docMgmtImpl.getUserLoginDetail(searchMode,userTypeCode,fromSubmissionDate,toSubmissionDate);		
		}
		public String searchMode;
		public String userTypeCode;
		ResultSet rs;
		public Vector<DTOUserMst> userdtl=new Vector<DTOUserMst>();

		public String fromSubmissionDate;
		public String toSubmissionDate;
		public int userCodeForEdit;
		public int userGroupCodeForEdit;
		public int mode;
		public String queryString;
		
		
		
		
		public String getQueryString() {
			return queryString;
		}

		public void setQueryString(String queryString) {
			this.queryString = queryString;
		}

		public int getUserCodeForEdit() {
			return userCodeForEdit;
		}

		public void setUserCodeForEdit(int userCodeForEdit) {
			this.userCodeForEdit = userCodeForEdit;
		}

		public int getUserGroupCodeForEdit() {
			return userGroupCodeForEdit;
		}
		
		public Vector getUserLogindata() {
			return userdtl;
		}

		public void setUserLogindata(Vector userdtl) {
			this.userdtl = userdtl;
		}
		
		

}
