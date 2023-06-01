package com.docmgmt.dto;

import java.sql.Timestamp;

public class DTOAssignNodeRights {

		private String workspaceId;
		private int nodeId;
		private int tranNo;
		private int stageId;
		private int modifyBy;
		private String flag;
		private Timestamp modifyOn;
		private char statusIndi;
					
		public String getWorkspaceId() {
			return workspaceId;
		}
		public void setWorkspaceId(String workspaceId) {
			this.workspaceId = workspaceId;
		}
		public int getNodeId() {
			return nodeId;
		}
		public void setNodeId(int nodeId) {
			this.nodeId = nodeId;
		}
		public int getTranNo() {
			return tranNo;
		}
		public void setTranNo(int tranNo) {
			this.tranNo = tranNo;
		}
		public int getStageId() {
			return stageId;
		}
		public void setStageId(int stageId) {
			this.stageId = stageId;
		}
		public int getModifyBy() {
			return modifyBy;
		}
		public void setModifyBy(int modifyBy) {
			this.modifyBy = modifyBy;
		}
		public String getFlag() {
			return flag;
		}
		public void setFlag(String flag) {
			this.flag = flag;
		}
		public Timestamp getModifyOn() {
			return modifyOn;
		}
		public void setModifyOn(Timestamp modifyOn) {
			this.modifyOn = modifyOn;
		}
		public char getStatusIndi() {
			return statusIndi;
		}
		public void setStatusIndi(char statusIndi) {
			this.statusIndi = statusIndi;
		}
		
		
		
		
	
}
