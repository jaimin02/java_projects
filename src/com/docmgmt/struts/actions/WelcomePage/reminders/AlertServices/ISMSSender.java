/**
 * 
 */
package com.docmgmt.struts.actions.WelcomePage.reminders.AlertServices;

import java.util.ArrayList;

/**
 * @author Rahul Goswami
*/
public interface ISMSSender {
	void init();
	boolean sendSMSAlerts(String msgBody,ArrayList<String> sendTo);
}