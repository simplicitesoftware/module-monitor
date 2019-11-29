package com.simplicite.objects.Monitor;

import com.simplicite.commons.Monitor.MailTool;
import com.simplicite.util.AppLog;
import com.simplicite.util.Tool;

/**
 * Business object MonInstance
 */
public class MonProjectInstance extends MonInstance {
	private static final long serialVersionUID = 1L;

	@Override
	protected void callSingleInstance(String instanceId, String instanceBaseUrl) {
		boolean notifyManager = false;
		try{
			createHealthRow(instanceId, instanceBaseUrl);
			notifyManager = getLatestHealth().hasProblem();
		}
		catch(Exception e){
			notifyManager = true;
			AppLog.error(getClass(), "callSingleInstance", "Unable to request URL : "+instanceBaseUrl, e, getGrant());
		}
		if(notifyManager){
			AppLog.info(getClass(), "callSingleInstance", "--- Instance has problem, notifying : "+instanceBaseUrl, getGrant());
			notifyContacts(instanceId, instanceBaseUrl);
		}
	}

	private void notifyContacts(String instanceId, String instanceBaseUrl){
		String sqlEmails =
			"select cnt.mon_cnt_email from mon_cnt_prj prj"
			+ " inner join mon_contact cnt on cnt.row_id=prj.mon_cntprj_cnt_id"
			+ " where prj.mon_cntprj_prj_id=(select mon_inst_prj_id from mon_instance where row_id="+instanceId+")";
		MailTool mail = new MailTool();
		mail.setSubject("Instance Monitor Alert");
		mail.addRcpt(Tool.getColumnOfMatrixAsArray(getGrant().query(sqlEmails), 0));
		mail.setContent("<p>Something fishy is going on with instance <a href='"+instanceBaseUrl+"'>"+instanceBaseUrl+"</a></p>");
		mail.send();
	}
}