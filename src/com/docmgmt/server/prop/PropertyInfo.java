package com.docmgmt.server.prop;

import java.io.File;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;


//Properties are Case-InSensitive

public class PropertyInfo  {
	private Hashtable propInfo;
	private Properties prop;
    private static PropertyInfo propertyInfo;
    
	
	private PropertyInfo() {
		propInfo = new Hashtable();
        readPropertyInfo();
	}
    
    public static PropertyInfo getPropInfo(){
        
       if(propertyInfo== null){
           propertyInfo = new PropertyInfo(); 
       }
       return propertyInfo;
    }

	
	private void readPropertyInfo() {
        
        try{
        	//KnowledgeNetConvert.prop
            
        	InputStream propIn = getClass().getResourceAsStream("KnowledgeNetConvert.properties");
        	//InputStream propIn = getClass().getResourceAsStream("KnowledgeNetDMS.properties");
        	prop = new Properties();
            prop.load(propIn);
            
            readProperties();
            propIn.close();
            
           // System.out.println(prop.getProperty("BaseWorkFolder"));
            
        }catch(Exception ex){
            System.out.println(ex.getStackTrace());
        }
	}
	
	public PropertyInfo(ObjectInputStream in) throws Exception {
		this();
		prop = (Properties) in.readObject();
		readProperties();
        
		//TODO: release prop. prop is not required once property is loaded
	}
	
	private void readProperties()  {
		String key;
		for (Enumeration keys = prop.propertyNames(); keys.hasMoreElements(); )  {
			key = (String) keys.nextElement();
			propInfo.put(key.toUpperCase(), prop.getProperty(key));
		}
	}
	
	public String getValue(String key)  {
		return (String) propInfo.get(key.toUpperCase());
	}

	public void setValue(String key, String val)  {
		propInfo.put(key.toUpperCase(), val);
	}
    
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
        
		for (Enumeration keys = propInfo.keys();keys.hasMoreElements();) {
			String key = (String) keys.nextElement();
			sb.append(key+"="+propInfo.get(key)+"\n");       
		}
        
		return sb.toString();
	}
	public File getDir(String key) {
		try{
			File propDir = new File(getValue(key));
			
			if(!propDir.exists()){
				
				System.out.println("File not Exist...");
				propDir.mkdirs();
				if(!propDir.exists()){
					return null;
				}
			}
			return propDir;
		}catch(Exception e){
			return null;
		}
	}
}