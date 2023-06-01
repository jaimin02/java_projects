package com.docmgmt.server.prop;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
*
* @author nagesh
*/
public class KnetProperties
{
	Properties properties;
	public static KnetProperties getPropInfo(String path)
	{
		KnetProperties kProperties=new KnetProperties();
		kProperties.properties=new Properties();
		try 
		{
			kProperties.properties.load(kProperties.getClass().getResourceAsStream(path));
		} 
		catch (IOException e) 
		{			
			e.printStackTrace();
			return null;
		}		
		return kProperties;
	}
	
	public static KnetProperties getPropInfo()
	{
		KnetProperties kProperties=new KnetProperties();
		kProperties.properties=new Properties();		
		try 
		{
			kProperties.properties.load(kProperties.getClass().getResourceAsStream("KnowledgeNetConvert.properties"));
		} 
		catch (IOException e) 
		{			
			e.printStackTrace();
			return null;
		}
		return kProperties;
	}   
	
	public String getValue(String key)
	{
		String val=properties.getProperty(key);
		return val;
	}
	
	public File getDir(String key) 
	{
		try
		{
			File propDir = new File(getValue(key));
			if(!propDir.exists())
			{
				propDir.mkdirs();
				if(!propDir.exists())
				{
					return null;
				}
			}
			return propDir;
		}
		catch(Exception e)
		{
			return null;
		}
	}
}
