package com.docmgmt.struts.actions.labelandpublish;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import com.docmgmt.dto.DTOAgencyMst;
import com.docmgmt.dto.DTOApplicationTypeMst;
import com.docmgmt.dto.DTOCountryMst;
import com.docmgmt.dto.DTORegulatoryActivityLeadMst;
import com.docmgmt.dto.DTOSubmissionMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SubmissionInfoAction extends ActionSupport {

    private static final long serialVersionUID = 1L;

    private static final String US_REGIONAL_VERSION2_01 = "21";
    private static final String US_REGIONAL_VERSION2_3 = "23";
    private DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
    public String clientName;
    public String submissionDesc;
    public String workSpaceId;
    public String submissionFlag;
    public String applicationNumber;
    public String applicant;
    public String agencyName;
    public String atc;
    public String procedureType;
    public String inventedName;
    public String inn;
    public String submissionDescription;
    public String companyName;
    public String dos;
    public String prodName;
    public String productType;
    public String applicationType;
    public String countryId;
    public String client_name;
    public String project_type;
    public String project_name;
    public Vector < DTOCountryMst > getCountryDetail;
    public Vector < DTOAgencyMst > getAgencyDetail;
    public String CountryRegion;
    public String userName;
    public Timestamp modifyDate;
    public char statusIndi;
    public String extraHtmlCode;
    public String highLvlNo;
    public String regionalDTDVersion;
    public String location_name;
    public String template_name;

    // following  fields are added for AU-TGA-v3.0 

    //public String esubmissionid;
    public String applicant_au;
    public String regactlead;
    public String aan;
    public String product_au;
    public String[] MultipleAAN;
    public String[] MultipleProduct;
    public ArrayList < String > AAN_List;
    public ArrayList < String > Product_List;

    // following  fields are added for TH-FDA-v.92 

    //public String esubmissionid;
    public String licensee;
    public String licensee_Type;
    public String licensee_Name;
    public String regactlead_th;
    public String inn_th;
    public String product_th;
    public String[] MultipleINN;
    public String[] MultipleProduct_th;
    public ArrayList < String > INN_List;
    public ArrayList < String > Product_List_th;

    // following  fields are added for CA-v2.2
    public String dossier_identifier;
    public String applicant_ca;
    public String prodName_ca;
    public String dossier_type;
    public String regactlead_ca;

    // following 7 fields are added for gcc--29-03-2013

    public String highLvlNo_gcc;
    public String atc_gcc;
    public String applicant_gcc;
    public String procedureType_gcc;
    public String inventedName_gcc;
    public String inn_gcc;
    public String submissionDescription_gcc;

    // Following 5 fields are added for ZA region on 25-06-2013

    public String proprietary_za;
    public String applicant_za;
    public String dosageForm_za;
    public String inn_za;
    public String submissionDescription_za;
    public String[] MultipleProprietary_Name;
    public ArrayList < String > Proprietary_Name_List;
    public String[] MultipleApplicationNumber;
    public ArrayList < String > ApplicationNumber_List;
    public String[] MultipleInn_ZA;
    public ArrayList < String > Inn_ZA_List;
    public String[] MultipleDosageForm_ZA;
    public ArrayList < String > DosageForm_ZA_List;

    // Following 8 fields are added for swissmedic on 30-07-2013

    public String applicant_ch;
    public String inventedName_ch;
    public String inn_ch;
    public String submissionDescription_ch;
    public String dmfNumber_ch;
    public String dmfHolder_ch;
    public String pmfNumber_ch;
    public String pmfHolder_ch;

    //For US2.3
    public String applicationId;
    public String submissionDescription_US;
    public String isGroupSub;
    public String submissionId;
    public Vector < DTOApplicationTypeMst > getApplictionTypes;
    public Vector < DTORegulatoryActivityLeadMst > getRegulatoryLead = new Vector < DTORegulatoryActivityLeadMst > ();
    public String applicationType_23;
    public String uuid;

    public String execute() throws Exception {
        return SUCCESS;
    }
    public String show() throws Exception // getSubmissionInfo
    {
            DTOWorkSpaceMst wsdto = docMgmtImpl.getWorkSpaceDetail(workSpaceId);
            client_name = wsdto.getClientName();
            project_type = wsdto.getProjectName();
            project_name = wsdto.getWorkSpaceDesc();
            location_name = wsdto.getLocationName();
            template_name = wsdto.getTemplateDesc();

            // getAgencyDetail = docMgmtImpl.getAllAgency();

            Calendar cal = GregorianCalendar.getInstance();
            java.util.Date now = cal.getTime();
            DateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
            dos = formater.format(now);

            /*
             * if (location_name.equals("GCC")) { DTOSubmissionMst submission =
             * docMgmtImpl .getSubmissionInfoGCC(workSpaceId); if (submission !=
             * null && submission.getWorkspaceId() != null &&
             * !submission.getWorkspaceId().equals("")) {
             * System.out.println("Details Found"); CountryRegion = "gcc";
             * highLvlNo_gcc = submission.getHighLvlNo(); applicant_gcc =
             * submission.getApplicant(); procedureType_gcc
             * =submission.getProcedureType(); inventedName_gcc =
             * submission.getInventedName(); inn_gcc = submission.getInn();
             * submissionDescription_gcc = submission.getSubmissionDescription();
             * atc_gcc =submission.getAtc(); applicationNumber =
             * submission.getApplicationNo(); countryId=submission.getCountrycode();
             * 
             * countryId = submission.getCountrycode()+ "," + "gcc";
             * getCountryDetail = new Vector<DTOCountryMst>();
             * getCountryDetail.addElement(docMgmtImpl.getCountry(submission
             * .getCountrycode())); } else { getCountryDetail =
             * docMgmtImpl.getAllCountryDetail(); }
             * 
             * 
             * 
             * return SUCCESS;
             * 
             * }
             */

            DTOSubmissionMst submission = docMgmtImpl
                .getSubmissionInfo(workSpaceId);
            if (submission != null && submission.getWorkspaceId() != null && !submission.getWorkspaceId().equals("")) {
                // workSpaceId = submission.getWorkspaceId();
                applicationNumber = submission.getApplicationNo();
                agencyName = submission.getAgencyName();
                countryId = submission.getCountrycode() + "," + submission.getCountryRegion();
                CountryRegion = submission.getCountryRegion();
                applicant = submission.getApplicant();
                atc = submission.getAtc();
                procedureType = submission.getProcedureType();
                inventedName = submission.getInventedName();
                inn = submission.getInn();
                submissionDescription = submission.getSubmissionDescription();
                companyName = submission.getCompanyName();
                prodName = submission.getProductName();
                productType = submission.getProductType();
                applicationType = submission.getApplicationType();
                userName = docMgmtImpl.getUserByCode(submission.getSubmittedBy())
                    .getUserName();
                java.sql.Date sqltDate = submission.getDateOfSubmission();
                dos = formater.format(sqltDate);
                getCountryDetail = new Vector < DTOCountryMst > ();
                getCountryDetail.addElement(docMgmtImpl.getCountry(submission
                    .getCountrycode()));

                modifyDate = submission.getSubmittedOn();
                statusIndi = submission.getStatusIndi();
                if (submission.getCountryRegion().equals("ca")) {
                    applicant_ca = submission.getApplicant();
                    prodName_ca = submission.getProductName();
                    applicationNumber = submission.getDossierIdentifier();
                    dossier_type = submission.getDossierType();
                    regactlead_ca = submission.getRegActLead();
                } else if (submission.getCountryRegion().equals("eu")) {

                    if (submission.getRegionalDTDVersion().equals("13")) {
                        applicationNumber = submission.getApplicationNo();
                    } else {
                        applicationNumber = submission.getTrackingNo();
                    }
                    highLvlNo = submission.getHighLvlNo();
                    uuid = submission.getUuid();

                } else if (submission.getCountryRegion().equals("za")) {
                    Proprietary_Name_List = new ArrayList < String > ();
                    ApplicationNumber_List = new ArrayList < String > ();
                    DosageForm_ZA_List = new ArrayList < String > ();
                    Inn_ZA_List = new ArrayList < String > ();

                    applicant_za = submission.getApplicant();
                    String[] proprietary = submission.getInventedName().split("#");

                    for (int i = 0; i < proprietary.length; i++) {

                        Proprietary_Name_List.add(proprietary[i]);
                    }

                    String[] applicationlist = submission.getApplicationNo().split(",");

                    for (int j = 0; j < applicationlist.length; j++) {
                        ApplicationNumber_List.add(applicationlist[j]);
                    }

                    String[] dosagelist = submission.getDosageForm().split("#");

                    for (int k = 0; k < dosagelist.length; k++) {
                        DosageForm_ZA_List.add(dosagelist[k]);
                    }

                    String[] Innlist = submission.getInn().split("#");

                    for (int l = 0; l < Innlist.length; l++) {
                        Inn_ZA_List.add(Innlist[l]);
                    }

                    submissionDescription_za = submission
                        .getSubmissionDescription();
                } else if (submission.getCountryRegion().equals("ch")) {

                    applicationNumber = submission.getApplicationNo();

                    applicant_ch = submission.getApplicant();

                    inventedName_ch = submission.getInventedName();
                    inn_ch = submission.getInn();
                    dmfHolder_ch = submission.getDmfHolder();
                    dmfNumber_ch = submission.getDmfNumber();
                    pmfHolder_ch = submission.getPmfHolder();
                    pmfNumber_ch = submission.getPmfNumber();
                    submissionDescription_ch = submission
                        .getSubmissionDescription();
                } else if (submission.getCountryRegion().equals("gcc")) {
                    highLvlNo_gcc = submission.getHighLvlNo();
                    applicationNumber = submission.getTrackingNo();
                    atc_gcc = submission.getAtc();
                    applicant_gcc = submission.getApplicant();
                    procedureType_gcc = submission.getProcedureType();
                    inventedName_gcc = submission.getInventedName();
                    inn_gcc = submission.getInn();
                    submissionDescription_gcc = submission
                        .getSubmissionDescription();

                } else if (submission.getCountryRegion().equalsIgnoreCase("us") && submission.getRegionalDTDVersion().equals(US_REGIONAL_VERSION2_3)) {
                    submissionDescription_US = submission.getSubmissionDescription();
                    applicationId = submission.getApplicationId();
                    isGroupSub = submission.getIsGroupSubmission();
                    submissionId = submission.getSubmissionId();
                    getApplictionTypes = new Vector < DTOApplicationTypeMst > ();
                    getApplictionTypes.addElement(docMgmtImpl.getApplicationTypeByCode(applicationType));
                    applicationType_23 = applicationType;
                } else if (submission.getCountryRegion().equalsIgnoreCase("au")) {
                    AAN_List = new ArrayList < String > ();
                    Product_List = new ArrayList < String > ();
                    applicant_au = submission.getApplicant();
                    applicationNumber = submission.geteSubmissionId();
                    regactlead = submission.getRegActLead();
                    String[] ann_au = submission.getAAN().split("#");
                    for (int i = 1; i < ann_au.length; i++) {
                        AAN_List.add(ann_au[i]);
                    }

                    String[] multiproduct = submission.getProductName().split("#");
                    for (int j = 1; j < multiproduct.length; j++) {
                        Product_List.add(multiproduct[j]);
                    }
                    getRegulatoryLead.addElement(docMgmtImpl.getRegulatoryActivityLeadByCode(regactlead));
                } else if (submission.getCountryRegion().equalsIgnoreCase("th")) {
                    INN_List = new ArrayList < String > ();
                    Product_List_th = new ArrayList < String > ();
                    licensee = submission.getLicensee();
                    licensee_Name = submission.getLicenseeName();
                    licensee_Type = submission.getLicenseeType();
                    applicationNumber = submission.geteSubmissionId();
                    regactlead_th = submission.getRegActLead();
                    String[] inn_th = submission.getInn().split("#");
                    for (int i = 1; i < inn_th.length; i++) {
                        INN_List.add(inn_th[i]);
                    }
                    String[] multiproduct_th = submission.getProductName().split("#");
                    for (int j = 1; j < multiproduct_th.length; j++) {
                        Product_List_th.add(multiproduct_th[j]);
                    }
                }
                if (submission.getCountryRegion().equalsIgnoreCase("eu")) {
                    //getCountryDetail = docMgmtImpl.getAllCountryDetail();
                	  getCountryDetail = docMgmtImpl.getCountriesRegionWise(location_name);
                }
                regionalDTDVersion = submission.getRegionalDTDVersion();
                System.out.println("DTD Version=" + regionalDTDVersion);
            } else {

                getRegulatoryLead = docMgmtImpl.getRegulatoryActivityLeadDetail();
                //getCountryDetail = docMgmtImpl.getAllCountryDetail();
                getCountryDetail = docMgmtImpl.getCountriesRegionWise(location_name);
                getApplictionTypes = docMgmtImpl.getApplicationType();
                System.out.println("Data Show not available");


            }
            return SUCCESS;
    }

    public String add() // addSubmissionInfo
    {
    	System.out.println("hello");
            int userid = Integer.parseInt(ActionContext.getContext().getSession()
                .get("userid").toString());
            DTOSubmissionMst submission = new DTOSubmissionMst();
            // System.out.println(countryId);
            String[] country = countryId.split(","); // countryId=countryCode,regionId
            submission.setCountrycode(country[0]); // countryCode
            if (country[1].equalsIgnoreCase("AU") || country[1].equalsIgnoreCase("TH")) {
                submission.setWorkspaceId(workSpaceId);
                submission.setAgencyName(agencyName);
                submission.setSubmittedBy(userid);
            } else {
                submission.setWorkspaceId(workSpaceId);
                submission.setApplicationNo(applicationNumber);
                submission.setAgencyName(agencyName);
                submission.setSubmittedBy(userid);
            }
            if (country[1].equals("eu")) // regionId
            {
                submission.setApplicant(applicant);
                submission.setAtc(atc);
                submission.setProcedureType(procedureType);
                submission.setInventedName(inventedName);
                submission.setInn(inn);
                submission.setSubmissionDescription(submissionDescription);
                submission.setTrackingNo(applicationNumber); // Imp change for EU
                submission.setHighLvlNo(highLvlNo);
                submission.setUuid(uuid);
                submission.setRegionalDTDVersion(regionalDTDVersion);
                DTOSubmissionMst submissiondtl = docMgmtImpl
                    .getSubmissionInfoEURegion(workSpaceId);
                if (submissiondtl.getWorkspaceId() == null) {
                    if (regionalDTDVersion.equals("14")) {
                        docMgmtImpl.insertSubmissionInfoEU14Mst(submission);
                    } else if (regionalDTDVersion.equals("13")) {
                        docMgmtImpl.insertSubmissionInfoEUMst(submission);
                    } else if (regionalDTDVersion.equals("20")) {
                        docMgmtImpl.insertSubmissionInfoEU20Mst(submission);
                    } else if (regionalDTDVersion.equals("301")) {
                        docMgmtImpl.insertSubmissionInfoEU20Mst(submission);
                    }

                } else {

                    if (submissiondtl.getEUDtdVersion().equals("13")) {
                        docMgmtImpl.insertSubmissionInfoEUMst(submission);
                    } else if (submissiondtl.getEUDtdVersion().equals("14")) {
                        docMgmtImpl.insertSubmissionInfoEU14Mst(submission);
                    } else if (submissiondtl.getEUDtdVersion().equals("20")) {
                        docMgmtImpl.insertSubmissionInfoEU20Mst(submission);
                    }
                }
                addActionMessage("Submission Details Saved Successfully.");

            } else if (country[1].equals("au")) {
                submission.seteSubmissionId(applicationNumber);
                submission.setApplicant(applicant_au);
                submission.setRegActLead(regactlead);
                aan = "";
                if (MultipleAAN != null) {
                    for (int i = 0; i < MultipleAAN.length; i++) {

                        aan = aan + "#" + MultipleAAN[i];
                    }
                }
                product_au = "";
                if (MultipleProduct != null) {
                    for (int i = 0; i < MultipleProduct.length; i++) {

                        product_au = product_au + "#" + MultipleProduct[i];
                    }
                }
                submission.setAAN(aan);
                submission.setProductName(product_au);
                docMgmtImpl.insertSubmissionInfoAUMst(submission);
                addActionMessage("Submission Details Saved Successfully.");
            } else if (country[1].equals("th")) {
                submission.seteSubmissionId(applicationNumber);
                submission.setLicensee(licensee);
                submission.setLicenseeName(licensee_Name);
                submission.setLicenseeType(licensee_Type);
                submission.setRegActLead(regactlead_th);
                inn_th = "";
                if (MultipleINN != null) {
                    for (int i = 0; i < MultipleINN.length; i++) {

                        inn_th = inn_th + "#" + MultipleINN[i];
                    }
                }
                product_th = "";
                if (MultipleProduct_th != null) {
                    for (int i = 0; i < MultipleProduct_th.length; i++) {

                        product_th = product_th + "#" + MultipleProduct_th[i];
                    }
                }
                submission.setInn(inn_th);
                submission.setProductName(product_th);
                docMgmtImpl.insertSubmissionInfoTHMst(submission);
                addActionMessage("Submission Details Saved Successfully.");
            } else if (country[1].equals("us")) {
                submission.setCompanyName(companyName);
                submission.setProductName(prodName);
                submission.setProductType(productType);
                submission.setDateOfSubmission(new Date(System.currentTimeMillis()));
                if (regionalDTDVersion.equalsIgnoreCase(US_REGIONAL_VERSION2_3)) {
                    submission.setApplicationId(applicationId);
                    submission.setIsGroupSubmission(isGroupSub);
                    submission.setSubmissionId(submissionId);
                    submission.setApplicationType(applicationType_23);
                    submission.setSubmissionDescription(submissionDescription_US == null ? "" : submissionDescription_US);
                    docMgmtImpl.insertSubmissionInfoUS23Mst(submission);
                } else {
                    submission.setApplicationType(applicationType);
                    docMgmtImpl.insertSubmissionInfoUSMst(submission);
                }
                addActionMessage("Submission Details Saved Successfully...");
            } else if (country[1].equals("ca")) {
                submission.setDossierIdentifier(applicationNumber);
                submission.setProductName(prodName_ca);
                submission.setApplicant(applicant_ca);
                submission.setRegActLead(regactlead_ca);
                submission.setDossierType(dossier_type);
                docMgmtImpl.insertSubmissionInfoCAMst(submission);
                addActionMessage("Submission Details Saved Successfully...");
            } else if (country[1].equals("za")) {
                // Added condition for sauth africa (za/sa)
                proprietary_za = "";
                if (MultipleProprietary_Name != null) {
                    for (int i = 0; i < MultipleProprietary_Name.length; i++) {

                        proprietary_za = proprietary_za + "#" + MultipleProprietary_Name[i];
                    }
                }
                applicationNumber = "";
                if (MultipleApplicationNumber != null) {
                    for (int j = 0; j < MultipleApplicationNumber.length; j++) {

                        applicationNumber = applicationNumber + "," + MultipleApplicationNumber[j];
                    }
                }
                dosageForm_za = "";
                if (MultipleDosageForm_ZA != null) {
                    for (int k = 0; k < MultipleDosageForm_ZA.length; k++) {

                        dosageForm_za = dosageForm_za + "#" + MultipleDosageForm_ZA[k];
                    }
                }

                inn_za = "";
                if (MultipleInn_ZA != null) {
                    for (int l = 0; l < MultipleInn_ZA.length; l++) {

                        inn_za = inn_za + "#" + MultipleInn_ZA[l];
                    }
                }
                submission.setApplicationNo(applicationNumber.substring(1));
                submission.setInn(inn_za.substring(1));
                submission.setSubmissionDescription(submissionDescription_za);
                submission.setDosageForm(dosageForm_za.substring(1));
                submission.setPropriateryName(proprietary_za.substring(1));
                submission.setApplicant(applicant_za);
                submission.setRegionalDTDVersion(regionalDTDVersion);
                docMgmtImpl.insertSubmissionInfoZAMst(submission);
                addActionMessage("Submission Details Saved Successfully...");
            } else if (country[1].equals("gcc")) {
                submission.setApplicant(applicant_gcc);
                submission.setAtc(atc_gcc);
                submission.setProcedureType(procedureType_gcc);
                submission.setInventedName(inventedName_gcc);
                submission.setInn(inn_gcc);
                submission.setSubmissionDescription(submissionDescription_gcc);
                submission.setTrackingNo(applicationNumber); // applicationNumber
                // contain Tracking
                // Number
                submission.setHighLvlNo(highLvlNo_gcc);
                submission.setRegionalDTDVersion(regionalDTDVersion);
                docMgmtImpl.insertSubmissionInfoGCCMst(submission);
                addActionMessage("Submission Details Saved Successfully...");
            } else if (country[1].equals("ch")) {
                submission.setApplicant(applicant_ch);
                submission.setInventedName(inventedName_ch);
                submission.setInn(inn_ch);
                submission.setSubmissionDescription(submissionDescription_ch);
                submission.setDmfNumber(dmfNumber_ch);
                submission.setDmfHolder(dmfHolder_ch);
                submission.setPmfNumber(pmfNumber_ch);
                submission.setPmfHolder(pmfHolder_ch);

                submission.setApplicationNo(applicationNumber); // applicationNumber

                docMgmtImpl.insertSubmissionInfoCHMst(submission);

                addActionMessage("Submission Details Saved Successfully...");
            } else {
                addActionMessage("Cannot Save Details For Selected Country...");
            }
            extraHtmlCode = "<input type=\"button\" class=\"button\" value=\"Back\" onclick=\"subinfo();\">";

            return "save";
     }

} // class end