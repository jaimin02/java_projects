package com.docmgmt.struts.actions.authentic;



import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;

import com.docmgmt.dto.DTOPasswordPolicyMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.db.dbcp.ConnectionManager;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;


/**
 * login interceptor and keep tracks 
 * that is user exists in session or not 
 * @author Hardik Shah
 *
 */

public class AuthenticationInterceptor extends AuthenticSupport implements Interceptor {

	private static final long serialVersionUID = 1L;

	public void destroy () {}

    public void init() {}

    DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
    /**
     * main function for session validation
     * @return action to invoke during intercept
     */
    public String intercept(final ActionInvocation actionInvocation) throws Exception 
    {
    	ConnectionManager.printDriverStats();
    	//System.out.println(actionInvocation.getAction());
    	System.out.println(actionInvocation.getInvocationContext().getName());
    	
        Map session = actionInvocation.getInvocationContext().getSession();
    	//Map session=ActionContext.getContext().getSession();
        
        final ActionContext ctx1 = actionInvocation.getInvocationContext();
        final HttpServletRequest request1 = (HttpServletRequest) ctx1
            .get(StrutsStatics.HTTP_REQUEST);
        String url=request1.getRequestURL().toString();
        //System.out.println(url);
        session.put("URL", url);
       
        boolean isAuthenticated =false; 
        if(session  !=null )
        {
        	if(session.get("username") !=null)
        	{
        		isAuthenticated=true;
        	}
        	
        /*
         * Added By : Ashmak Shah
         * Prevents page to store in browser cache.
         * You can also create a new Intercepter for it if required.
         */
        	HttpServletResponse response = ServletActionContext.getResponse();
        	/*response.addHeader("Pragma","no-cache"); 
            response.setHeader("Cache-Control","no-cache,no-store,must-revalidate"); 
            response.addHeader("Cache-Control","pre-check=0,post-check=0"); 
            response.setDateHeader("Expires",0);*/
        	String key = UUID.randomUUID().toString();
        	
        	response.addHeader("X-CSRF-Token", key);
        	//response.addHeader("X-Xss-Protection", "0");
        	response.addHeader("X-Frame-Options","SAMEORIGIN");
        	//response.addHeader("X-Content-Type-Options", "nosniff");
        	response.setHeader("X-Content-Type-Options", "nosniff");
        	//response.addHeader("X-Frame-Options", "DENY");
        	response.addHeader("X-Xss-Protection", "1; mode=block");
        	response.addHeader("Cache-Control", "no-cache, no-store");
        	response.addHeader("Strict-Transport-Security", "max-age=31536000");
        	response.addHeader("Pragma","no-cache"); 
            //response.setHeader("Cache-Control","no-cache,no-store,must-revalidate"); 
           // response.addHeader("Cache-Control","pre-check=0,post-check=0"); 
            response.setDateHeader("Expires",0);
        /*
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *  
         */	
        	
        }
        if (!isAuthenticated) 
        {
        	
        	final ActionContext ctx = actionInvocation.getInvocationContext();
            final HttpServletRequest request = (HttpServletRequest) ctx
                .get(StrutsStatics.HTTP_REQUEST);
            if (request != null) {
              StringBuffer requestURL = request.getRequestURL();
              if (request.getQueryString() != null) 
              {
                requestURL.append("?").append(request.getQueryString());
              }
              ctx.put("postLoginURL", requestURL.toString()); 
          //    session.put("postLoginURL", requestURL.toString());
            }
        	
        /*	 String urlGoingTo = actionInvocation.getProxy().getNamespace()+"/"+
        	 actionInvocation.getProxy().getActionName()+".do";
        	 session.put( "actionurl", urlGoingTo); */
            
            
           // session.put("autousers",docMgmtImpl.getautousr());
            
            
//            session.put("Expire", "Session Time Out!");
       	 	session.put("Expire", "");
            return "sessionExpire";
            
           	
        }
        else {
        	ArrayList<DTOPasswordPolicyMst> pwdPolicy = new ArrayList<DTOPasswordPolicyMst>();
        	int timediff;
        	try {
				pwdPolicy = DocMgmtImpl.getPolicyDetails("MaxLoginMins");
			} catch (NullPointerException e) {
				return "sessionExpire";
			}
        	//session.clear();
        	//String clientIP = session.get("clientIp").toString(); 
        	char activeFlag='\u0000';
        	if(pwdPolicy.size()>0)
        	{
        		DTOPasswordPolicyMst dto = pwdPolicy.get(0);
        		activeFlag = dto.getActiveFlag(); 
        	}
        	if(activeFlag=='Y'){
        		//DocMgmtImpl.UserLoginDetailsOp(Integer.parseInt(session.get("userid").toString()), 2);
        		timediff = DocMgmtImpl.UserLoginDetailsOp(Integer.parseInt(session.get("userid").toString()), 2);
        		if(timediff<0){
        			//System.out.println("*****************"+timediff);
        		return "timeOut";
        		}
        	}
        	 session.put("Expire", "");
        	return actionInvocation.invoke();
        }

    }
}
