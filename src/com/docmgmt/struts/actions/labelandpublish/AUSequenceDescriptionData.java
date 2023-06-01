package com.docmgmt.struts.actions.labelandpublish;

import java.util.Vector;

import com.docmgmt.dto.DTOSequenceDescriptionMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class AUSequenceDescriptionData extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	public String htmlContent;
	public String seqDesCode;
	public Vector<DTOSequenceDescriptionMst> getSequenceDescriptionDtl= new Vector<DTOSequenceDescriptionMst>();
	

public String getSequenceDescriptionData(){
		
		
		htmlContent = "";

		getSequenceDescriptionDtl.addAll(docMgmtImpl.getSequenceDescriptionByCode(seqDesCode));
		/*
		for (DTOSequenceDescriptionMst seqDescMst : getSequenceDescriptionDtl) {
			if(!htmlContent.equals("")){
				htmlContent += ","; 
			}
			htmlContent += seqDescMst.getLabelName()+"::"+seqDescMst.getFieldType();
		}*/
		
		for(int index=0;index <getSequenceDescriptionDtl.size();index++){
			DTOSequenceDescriptionMst dtoSeqDesDtl=getSequenceDescriptionDtl.get(index);
			if(!htmlContent.equals("")){
				htmlContent += ","; 
			}
			htmlContent += dtoSeqDesDtl.getLabelName()+"::"+dtoSeqDesDtl.getFieldType();
			System.out.println("Sequece Descriptino ajx data::::"+dtoSeqDesDtl.getLabelName()+":::::::"+dtoSeqDesDtl.getFieldType());
		}
		
		
		return "html";
}
	
public String getSeqDesCode() {
	return seqDesCode;
}
public void setSeqDesCode(String seqDesCode) {
	this.seqDesCode = seqDesCode;
}

}
