package com.simplicite.objects.Monitor;

import java.util.Date;

import  org.quartz.CronExpression;

import com.simplicite.commons.Monitor.MailTool;
import com.simplicite.util.AppLog;
import com.simplicite.util.ObjectDB;
import com.simplicite.util.Tool;

/**
 * Business object MonInstance
 */
public class MonInstance extends ObjectDB {
	private static final long serialVersionUID = 1L;
	private static final String ObjHealth = "MonHealth";

	/**
	 * Unit tests method
	 */
	@Override
	public String unitTests() {
		try {
			return getNextCronExec("1 0 8 ? * *", 10);
		} catch (Exception e) {
			AppLog.error(getClass(), "unitTests", null, e, getGrant());
			return e.getMessage();
		}
	}

	private static String getNextCronExec(String cronString, int depth){
		try{
			CronExpression cron = new CronExpression(cronString);
			Date d = new Date();
			String s = "";
			for(int i=0; i<depth; i++){
				d = cron.getNextValidTimeAfter(d);
				s += Tool.toDatetime(d) + "\n";
			}
			return s;
		}
		catch(Exception e){
			return e.toString();
		}
	}

	public void callInstances(){
		search().forEach(row->callSingleInstance(row[getFieldIndex("row_id")],row[getFieldIndex("monInstUrl")]));
	}

	public void callInstance(){
		callSingleInstance(getFieldValue("row_id"), getFieldValue("monInstUrl"));
	}

	private void callSingleInstance(String instanceId, String instanceBaseUrl){
		boolean notifyManager = false;

		try{
			boolean[] oldcrud = getGrant().changeAccess("MonHealth", true,true,false,false);
			MonHealth health = MonHealth.getHealth(instanceBaseUrl, getGrant());
			health.setFieldValue("monHeaInstId", instanceId);
			health.create();
			notifyManager = health.hasProblem();
			getGrant().changeAccess("MonHealth", oldcrud);
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