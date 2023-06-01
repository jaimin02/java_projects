package com.docmgmt.struts.actions.labelandpublish;

import java.util.Vector;

import com.docmgmt.dto.DTOSequenceDescriptionMst_CA;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class CASequenceDescriptionData extends ActionSupport {
	

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	public String htmlContent;
	public String seqDesCodeCA;
	public Vector<DTOSequenceDescriptionMst_CA> getSequenceDescriptionDtl_CA= new Vector<DTOSequenceDescriptionMst_CA>();
	

public String getSequenceDescriptionDataCA(){
		
		
	System.out.println("method call");
		htmlContent = "";

		getSequenceDescriptionDtl_CA.addAll(docMgmtImpl.getSequenceDescriptionByCode_CA(seqDesCodeCA));
		/*
		for (DTOSequenceDescriptionMst seqDescMst : getSequenceDescriptionDtl) {
			if(!htmlContent.equals("")){
				htmlContent += ","; 
			}
			htmlContent += seqDescMst.getLabelName()+"::"+seqDescMst.getFieldType();
		}*/
		
		for(int index=0;index <getSequenceDescriptionDtl_CA.size();index++){
			DTOSequenceDescriptionMst_CA dtoSeqDesDtl=getSequenceDescriptionDtl_CA.get(index);
			if(!htmlContent.equals("")){
				htmlContent += ","; 
			}
			htmlContent += dtoSeqDesDtl.getLabelName()+"::"+dtoSeqDesDtl.getFieldType();
			System.out.println("Sequece Descriptino ajx data::::"+dtoSeqDesDtl.getLabelName()+":::::::"+dtoSeqDesDtl.getFieldType());
		}
		
		
		return "html";
}


public String getSeqDesCodeCA() {
	return seqDesCodeCA;
}


public void setSeqDesCodeCA(String seqDesCodeCA) {
	this.seqDesCodeCA = seqDesCodeCA;
}





}
