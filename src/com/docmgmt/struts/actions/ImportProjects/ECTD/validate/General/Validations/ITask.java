/**
 * 
 */
package com.docmgmt.struts.actions.ImportProjects.ECTD.validate.General.Validations;

import java.util.ArrayList;

import com.docmgmt.struts.actions.ImportProjects.ECTD.EctdError;

/**
 * @author nagesh
 *
 */
public interface ITask 
{
	 void performTask(ArrayList<EctdError> errorList,Object...argObject);
}
