package com.docmgmt.test;

import com.opensymphony.xwork2.ActionSupport;
 
public class LongProcessAction extends ActionSupport{
	@Override
	public String execute() throws Exception {
		for(int i =0; i<1000000; i++){
			System.out.println(i);
		}
		return SUCCESS;		
	}
}