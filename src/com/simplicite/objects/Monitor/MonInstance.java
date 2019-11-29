package com.simplicite.objects.Monitor;

import java.io.IOException;

import org.json.JSONException;

import com.simplicite.util.AppLog;
import com.simplicite.util.ObjectDB;

/**
 * Business object MonInstance
 */
public class MonInstance extends ObjectDB {
	private static final long serialVersionUID = 1L;

	public void callInstances(){
		search().forEach(row->callSingleInstance(row[getFieldIndex("row_id")],row[getFieldIndex("monInstUrl")]));
	}

	public void callInstance(){
		callSingleInstance(getFieldValue("row_id"), getFieldValue("monInstUrl"));
	}

	protected void callSingleInstance(String instanceId, String instanceBaseUrl){
		try{
			createHealthRow(instanceId, instanceBaseUrl);
		}
		catch(Exception e){
			AppLog.error(getClass(), "callSingleInstance", "Unable to request URL : "+instanceBaseUrl, e, getGrant());
		}
	}

	protected void createHealthRow(String instanceId, String instanceBaseUrl) throws JSONException, IOException {
		boolean[] oldcrud = getGrant().changeAccess("MonHealth", true,true,false,false);
		MonHealth health = MonHealth.getHealth(instanceBaseUrl, getGrant());
		health.setFieldValue("monHeaInstId", instanceId);
		health.create();
		getGrant().changeAccess("MonHealth", oldcrud);
	}

	protected MonHealth getLatestHealth() {
		MonHealth health = (MonHealth) getGrant().getObject("latest_MonHealth", "MonHealth");
		String rowId = getGrant().simpleQuery("select row_id from mon_health where mon_hea_inst_id="+getRowId()+" order by row_id desc limit 1");
		health.select(rowId);
		return health;
	}
}
