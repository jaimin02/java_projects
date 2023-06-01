package com.docmgmt.struts.actions.ImportProjects.ECTD.validate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;


public class EnvelopeValidator 
{
	private static String ENVELOPE_PARENT="eu-envelope";
	private static String EU_DOCUMENT_PARENT="m1-eu";
	private static String COUNTRY_ATTRIBUTE="country";
		
	private static void buildMainList(Node node,Vector<String> mainList)
	{
		if (node.getNodeType()==Node.ELEMENT_NODE)
		{
			String country =((Element)node).getAttribute(COUNTRY_ATTRIBUTE);
			if (country != null && !country.equals("") && !country.equals("common"))
			{
				if (!isCountryPresent(country,mainList))
					mainList.add(country);
			}
		}
		for (int i=0;i<node.getChildNodes().getLength();i++)
			buildMainList(node.getChildNodes().item(i), mainList);		
	}
	
	private static boolean isCountryPresent(String country,Vector<String> mainList)
	{
		boolean found=false;
		for (int i=0;i<mainList.size();i++)
		{
			if (mainList.get(i).equals(country))
			{
				found=true;
				break;
			}
		}		
		return found;
	}
	
	public static ArrayList<String> validate(Document doc)
	{
		ArrayList<String> envAbsent = new ArrayList<String>();
		Node mainNode = doc.getElementsByTagName(EU_DOCUMENT_PARENT).item(0);
		Node envelopes=doc.getElementsByTagName(ENVELOPE_PARENT).item(0);		
		Vector<String> mainList=new Vector<String>();
		
		
		buildMainList(mainNode, mainList);
		for (int i=0;i<mainList.size();i++)
		{
			//System.out.println(mainList.get(i));		
			boolean found=false;
			
			for (int j=0;j<envelopes.getChildNodes().getLength();j++)
			{			
				Node node1=envelopes.getChildNodes().item(j);
				String envcountry=null;
				
				if (node1.getNodeType()==Node.ELEMENT_NODE)
				{
					envcountry=((Element)node1).getAttribute(COUNTRY_ATTRIBUTE).toString();
				}
				else
					continue;				
				if (envcountry!=null && !envcountry.equals("") && envcountry.equals(mainList.get(i)))
				{
					found=true;
					break;
				}
			}
			
			if (!found)
			{
				envAbsent.add(mainList.get(i));
			}
		}
		return envAbsent;
	}
		
	public static void main(String[] args) 
	{				
		try 
		{
			DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
			DocumentBuilder parser=dbf.newDocumentBuilder();
			//Document doc=parser.parse("D:\\nagesh\\misc\\ELC Published Dossiers\\Trimetazidine-Lupin-Ltd\\0000\\m1\\eu\\eu-regional.xml");
			Document doc=parser.parse("D:\\nagesh\\misc\\ELC Published Dossiers\\Trimetazidine-Mylan\\0000\\m1\\eu\\eu-regional.xml");			
			ArrayList<String> errList =new ArrayList<String>();
			EnvelopeValidator.validate(doc);
			System.out.println(errList.toString());
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
	}

}
