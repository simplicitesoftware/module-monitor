package com.simplicite.objects.Monitor;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;
import org.json.JSONObject;
import com.simplicite.commons.Monitor.MailTool;

import  org.quartz.CronExpression;

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
		AppLog.info(getClass(), "callSingleInstance", "--- Checking instance :"+instanceBaseUrl, getGrant());
		JSONObject req = null;
		try{
			req = new JSONObject(Tool.readUrl(instanceBaseUrl+"/health?format=json"));
			boolean[] oldcrud = getGrant().changeAccess(ObjHealth, true,true,false,false);
			MonHealth health = (MonHealth) getGrant().getTmpObject(ObjHealth);
			synchronized(health){
				health.resetValues();
				health.setFieldValue("monHeaInstId", instanceId);
				health.setFieldValue("monHeaDate", Tool.getCurrentDateTime());
				health.feedJson(req);
				health.create();
			}
			getGrant().changeAccess(ObjHealth, oldcrud);
		}
		catch(Exception e){
			AppLog.error(getClass(), "callSingleInstance", "Unable to request URL : "+instanceBaseUrl, e, getGrant());
		}
		
		if(req==null || !"OK".equals(req.getJSONObject("platform").getString("status"))){
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