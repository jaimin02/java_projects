package com.docmgmt.server.db.dbcp;


import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.docmgmt.server.db.master.DataTable;

public class ExternalConfiguration {
	
	private static final String CONFIG_FILENAME = "externalconfig.properties";

    private String dbDriverName = null;
    private String dbUser = null;
    private String dbPassword = null;
    private String dbURI = null;
    private String dbServer=null;
    private String dbPort=null;
    private String dbDatabase=null;
    private int dbPoolMinSize = 0;
    private int dbPoolMaxSize = 0;
    
   
	private long dbDestroyProcessTime = -1; 
    private long dbDestroyIdleConnTime = 30 * 60 * 1000; //30 mins 

    public ExternalConfiguration()
    {
    	Properties dbProps = new Properties();
        try {
        	
        	InputStream is = getClass().getResourceAsStream(CONFIG_FILENAME);
           	
            dbProps.load(is);
        }
        catch (Exception e) {
            System.err.println("Can't read the properties file. " +
                "Make sure config.properties is in the CLASSPATH");
            return;
        }
        
         dbDriverName=dbProps.getProperty("driver").trim();
         dbUser=dbProps.getProperty("user").trim();
         dbPassword=dbProps.getProperty("password").trim();
         dbServer=dbProps.getProperty("server").trim();
         dbDatabase=dbProps.getProperty("database").trim();
         dbPort=dbProps.getProperty("port").trim();
        
        //jdbc:microsoft:sqlserver://SSPLNETSERVER:1433;DatabaseName=KnowledgeNETConvert;User=developer;Password=d123
        dbURI="jdbc:microsoft:sqlserver://"+dbServer+":"+dbPort+";"+"DatabaseName="+dbDatabase+";"+"User\\="+dbUser+";"+"Password=\\"+dbPassword;
        dbPoolMaxSize=Integer.parseInt(dbProps.getProperty("maxpool").trim());
        dbPoolMinSize=Integer.parseInt(dbProps.getProperty("minpool").trim());
        dbDestroyProcessTime = Long.parseLong(dbProps.getProperty("DestroyProcessTimeInMin").trim()) * 60 * 1000; 
        dbDestroyIdleConnTime = Long.parseLong(dbProps.getProperty("DestroyIdleConnTimeInMin").trim())* 60 * 1000;
        
    }
    
    private static final Log LOG = 
    	LogFactory.getLog( ExternalConfiguration.class );


    public String getDbDriverName() {
        return dbDriverName;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public String getDbURI() {
        return dbURI;
    }

    public int getDbPoolMinSize() {
        return dbPoolMinSize;
    }

    public int getDbPoolMaxSize() {
        return dbPoolMaxSize;
    }

    public long getDbDestroyProcessTime() {
		return dbDestroyProcessTime;
	}

	public long getDbDestroyIdleConnTime() {
		return dbDestroyIdleConnTime;
	}

	
	public static void main(String []args)
	{
		try
		  {
			
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
    		  // connect using Thin driver
			Properties props = new Properties(); 
			props.put("user", "sys"); 
			props.put("password", "knettest123"); 
			props.put("internal_logon", "sysdba"); 

		  Connection con = DriverManager.getConnection("jdbc:oracle:thin:@90.0.0.15:1521:KNETTest",props);
		  ResultSet rs = null;
			//Pstmt=con.prepareStatement(sb.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		  DatabaseMetaData meta = con.getMetaData();
	      // gets driver info:
	      System.out.println("JDBC driver version is " + meta.getDriverVersion());
	      DataTable dt = new DataTable();
	      
		  int i=0;
		  //String SQL = "insert into rahul values(4,'laxmi')";
		  //Statement stat = con.createStatement();
		  //rs = stat.executeQuery(SQL);
 		  CallableStatement cs=con.prepareCall("{call proc_insertrahul(?,?)}");
 		  cs.setInt(1, 11);
 		  cs.setString(2, "Vimal");
 		  //cs.execute();
 		  PreparedStatement pstmt = con.prepareStatement("select * from rahul");
 		  
 		  rs =pstmt.executeQuery(); 
		  //rs = dt.getDataSet(con, " * ", " rahul ", "", "");
		  while (rs.next()) 
		  {
			  System.out.println(rs.getString(1)+"------> "+rs.getString(2));
			  i++;
		  }
		  
		  System.out.println("total records : == "+i);
		  System.out.println("Connected Successfully To Oracle");
		 // rs.close();
		  //stat.close();
		  con.close();
		  }
		  catch(Exception ex)
		  {
		    ex.printStackTrace();
		  }
	}
	
}
