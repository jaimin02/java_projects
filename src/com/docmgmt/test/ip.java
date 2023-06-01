package com.docmgmt.test;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ip 
{
  public static void main ( String[] args ) throws IOException 
  {
		
		
	  
    String hostname = "sspl37";

    try 
    {
      InetAddress ipaddress = InetAddress.getByName(hostname);
      System.out.println("IP address: " + ipaddress.getHostAddress());
    }
    catch ( UnknownHostException e )
    {
      System.out.println("Could not find IP address for: " + hostname);
    }
  }
}