package com.docmgmt.struts.actions.util;

import com.docmgmt.server.prop.PropertyInfo;

public class WebServiceMethods {

	public String WS_MOBILEFFR = "WordToPDF.asmx";
	//public String WS_MOBILEFFR = "mobileffr.asmx";
	public String WS_SOAPACTION = "http://tempuri.org/";
	PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
	String WebServiceURL = propertyInfo.getValue("WebServiceIp");
	//	-------------------------------------- FFR DEMO NEW------------------------------------------
	//  public static String WS_URL_FFR ="http://90.0.0.30/ffr/FFRDemo_WS/";
	//	public String WS_HELPDBTABLE = "helpdbtable.asmx";
   //   public static String WS_URL_FFR ="http://90.0.0.7/Intas_wsFFR/";

	//  public static String WS_URL_FFR ="http://90.0.0.161/Intas_wsffr/";


	 // public static String WS_URL_FFR = "http://121.241.241.73/FFR/FFRDemoNew_ws/";  // FFR Demo New

	//	-------------------------------------- New Intas  Test------------------------------------------

	//	public static String WS_URL_FFR = "http://newffr.intaspharma.com/IntasFFRTest_ws/";  // IntasFFR test

	//	-------------------------------------- New Intas  Live------------------------------------------
	
	
	
	//public static String WS_URL_FFR = "http://sspl192/SSIntas_WS/";  // IntasFFR
	
	//public static String WS_URL_FFR = "http://90.0.0.110/pharmaNET/EOMS_WS/";  // IntasFFR
	
	//public static String WS_URL_FFR = "http://52.172.13.138/EOBSTest_WS/";  // Intas Test server
	
	public String WS_URL_FFR = WebServiceURL;  // Intas live server

	/////////////////////////////////// New WebMethods /////////////////////////////////////////////

	public String WSMETHOD_GETORDERDETAILS = "ConvertWordToPDF_001"; // for primary sales 
	public String WSMETHOD_EOBSSAVEDETAILS = "getEOBSSaveDetails_002"; // for primary sales
	
	public String WSMETHOD_GETORDERDETAILSS = "getorderdetailsSS_001"; // for secondary sales 
	public String WSMETHOD_EOBSSAVEDETAILSS = "getEOBSSaveDetailsSS_002"; // for secondary sales
	
	public String WSMETHOD_GETCAMPTYPE = "CheckLoginAuthDtl2_003";
	public String WSMETHOD_GETCAMPEXPTYPEMST = "GetCampExpTypeMst2_004";
	public String WSMETHOD_GETCAMPDTLINFO = "GetCampDetailInfo2_005";
	public String WSMETHOD_GETMATERIALMST = "GetMaterialMst2_006";
	public String WSMETHOD_GETSAMPLEMST = "GetMaterialMst2_007";
	public String WSMETHOD_GETAPPROVECAMPREQDTL = "GetApprovalCampDetailInfo2_008";
	public String WSMETHOD_GETPENDINGAPPROVECAMP = "GetPendingApprovalCamp2_009";
	public String WSMETHOD_GETPENDINGAPPROVECAMPFEEDBACK = "GetPendingApprovalCampFeedBack2_010";
	public String WSMETHOD_GETCAMPFEEDBACKINFO = "GetCampFeedBackInfo2_011";
	public String WSMETHOD_GETCAMPDASHBOARDINFO = "GetCampDashboardinfo2_012";
	public String WSMETHOD_GETCAMPPENDINGCOUNT = "GetCampPendingCount2_013";
	public String WSMETHOD_SAVECAMPMST = "Save_CampMst_014";
	public String WSMETHOD_SAVECAMPIMPHDR = "Save_CampImpHdr_015";
	public String WSMETHOD_SAVEEMPMST= "Save_EmpMst_016";

	public String WSMETHOD_REJECTCAMPFEEDBACK= "RejectCampFeedBack_017";

	public String WSMETHOD_GETCAMPREQFEEDBACKCOUNT= "GetCampReqFeedbackCount2_018";
	public String WSMETHOD_SAVECAMPPREDTL= "Save_CampPreDtl_019";
	public String WSMETHOD_GETSUBORDINATESUPERIOR= "GetSubordinateAndSuperior2_019";
	public String WSMETHOD_SENDMAILWITHATTACHMENT= "SendMailWithAttachment_019";


}
