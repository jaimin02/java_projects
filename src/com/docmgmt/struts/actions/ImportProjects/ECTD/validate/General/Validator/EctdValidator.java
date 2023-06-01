/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.docmgmt.struts.actions.ImportProjects.ECTD.validate.General.Validator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.struts.actions.ImportProjects.ECTD.EctdError;
import com.docmgmt.struts.actions.ImportProjects.ECTD.validate.General.Validations.Validation;



/**
 *
 * @author nagesh
 */
public class EctdValidator
{
	public static ArrayList<EctdError> validateEctdSeq(File seqDir)
    {
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		String GENERAL_VALIDATION_XML=propertyInfo.getValue("ECTD_VALIDATOR_GENERAL_LIST");
		ArrayList<EctdError> errList=new ArrayList<EctdError>();
		
		FileInputStream validationXML = null;
        try {
            validationXML = new FileInputStream(GENERAL_VALIDATION_XML);
           
            Document document;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();            
            document = db.parse(validationXML);            
            for (int i=0;i<document.getFirstChild().getChildNodes().getLength();i++)
            {
                if (document.getFirstChild().getChildNodes().item(i).getNodeType()==Node.ELEMENT_NODE)
                {                	
                	try
                	{
                		Node validationNode=document.getFirstChild().getChildNodes().item(i);
	                    Class c = Class.forName("com.docmgmt.struts.actions.ImportProjects.ECTD.validate.General.Validations."+validationNode.getNodeName());
	                    Validation validation=(Validation)c.newInstance();                    
//	                    validation.validate(document.getFirstChild().getChildNodes().item(i),new File("\\\\sspl47\\docmgmtandpub\\PublishDestinationFolder\\US_Test_Project\\0082\\321321\\0000"),errList);
	                    validation.validate(validationNode,seqDir,errList);
                	}
                	catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
           /*
            *  for (int i=0;i<errList.size();i++)
            {
            	System.out.println(errList.get(i));
            }*/
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                if (validationXML!=null)
                    validationXML.close();
            }
            catch (IOException ex)
            {

            }
        }
        
        return errList;
    }
	
	public static void main(String args[])
	{
		//ArrayList<EctdError> ectdErrors = validateEctdSeq(new File("/home/data/testdossiers/us/123456/0000"));
		//ArrayList<EctdError> ectdErrors = validateEctdSeq(new File("/home/data/Sample_eCTDs_INGAMERICA/EU/123430/0001"));
		//ArrayList<EctdError> ectdErrors = validateEctdSeq(new File("/home/data/testdossiers/eu/Abc/0000"));
		//ArrayList<EctdError> ectdErrors = validateEctdSeq(new File("/home/data/testdossiers/ca/e123456/0000"));
		ArrayList<EctdError> ectdErrors = validateEctdSeq(new File("/home/data/testdossiers/ca/e123457/0000"));
		System.out.println("ectdErrors" + ectdErrors.size());
		for (int i=0;i<ectdErrors.size();i++)
		{
			System.out.println("ectderror" + i + ": " + ectdErrors.get(i).Msg + " - " + ectdErrors.get(i).XmlFileName);
		}
		//validateEctdSeq("d:\\Escitalopram-Cinfa\\0000");
	}
}
