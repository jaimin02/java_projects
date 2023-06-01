package com.docmgmt.struts.actions.authentic;

import java.util.Map;

import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

/*
 * used to implement skeleton for all class in authentic package
 * 
 * @author Hardik Shah
 *
 */
public class AuthenticSupport extends ActionSupport implements SessionAware, ApplicationAware{

	protected Map session;
	
	public void setSession(Map session) {
		this.session = session;	
	}
	
	public Map getSession() {
		return session;
	}

	private Map application;

    /**
     * <p>Store a new application context.</p>
     *
     * @param value A Map representing application state
     */
    public void setApplication(Map value) {
        application = value;
    }

    /**
     * <p>Provide application context.</p>
     */
    public Map getApplication() {
        return application;
    }
	
}


