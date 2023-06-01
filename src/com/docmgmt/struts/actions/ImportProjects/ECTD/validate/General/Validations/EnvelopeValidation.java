/**
 * 
 */
package com.docmgmt.struts.actions.ImportProjects.ECTD.validate.General.Validations;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.docmgmt.struts.actions.ImportProjects.ECTD.EctdError;
import com.docmgmt.struts.actions.ImportProjects.ECTD.EctdErrorType;

/**
 * @author nagesh
 *
 */

class CheckEnvelopeTask implements ITask
{
	String prefix;
	
	public CheckEnvelopeTask(String prefix) 
	{
		this.prefix=prefix;
	}
	
	public void performTask(ArrayList<EctdError> errorList, Object... argObject) 
	{
		if (argObject.length<=0)
		{
			return;
		}
		else
		{
			if (argObject[0]!=null && argObject[0] instanceof File)
			{
				File file=(File)argObject[0];
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder parser;
				Document document = null;
				try 
				{
					parser = factory.newDocumentBuilder();
					document = parser.parse(file);
				} 
				catch (ParserConfigurationException e) 
				{				
					e.printStackTrace();
				} 
				catch (SAXException e) 
				{
					e.printStackTrace();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}			    
				NodeList nodeList=document.getElementsByTagName("procedure");
				Element procElement;
				if (nodeList!=null && nodeList.getLength()>0)
				{
					procElement=(Element)nodeList.item(0);					
				}
				else
					return;
				String procType=procElement.getAttribute("type");
				if (procType.equals("centralised") || procType.equals("national"))
				{
					NodeList envNodeList=document.getElementsByTagName("envelope");
					if (envNodeList.getLength()!=1)
					{
						errorList.add(new EctdError(EctdErrorType.ECTD_ERROR, prefix + procType + " procedure should have only one envelope.",file.getAbsolutePath()));
						return;
					}
					Element envElement=(Element)envNodeList.item(0);
					String countryCode=envElement.getAttribute("country");
					if (procType.equals("centralised") && !countryCode.equals("emea"))
					{
						errorList.add(new EctdError(EctdErrorType.ECTD_ERROR, prefix + "Envelope should be 'emea' only.",file.getAbsolutePath()));
						return;
					}
					NodeList countryNodeList=document.getElementsByTagName("specific");
					boolean valid=true;
					String parentTag = null;
					for (int i=0;i<countryNodeList.getLength();i++)
					{
						Element countryElement=(Element)countryNodeList.item(i);
						String fileCountryCode=countryElement.getAttribute("country");
						if (!fileCountryCode.equals(countryCode))
						{
							parentTag=countryElement.getParentNode().getNodeName();
							valid=false;
							break;
						}
					}
					if (!valid)
					{						
						errorList.add(new EctdError(EctdErrorType.ECTD_ERROR, prefix + "Invalid country found under '" + parentTag + "'.",file.getAbsolutePath()));
						return;
					}
				}				
			}
		}
	}	
}

public class EnvelopeValidation extends Validation 
{
	@Override
	public void validate(Node tagData, File dossier, ArrayList<EctdError> errorList) 
	{
		if (!init(tagData, dossier, errorList))
			return;		
		String filename=getChildNode(tagData,"filename").getTextContent();
		find(dossier,filename,new CheckEnvelopeTask(prefix));
	}
}
