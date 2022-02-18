package com.simplicite.objects.Monitor;

import java.io.IOException;

import org.json.JSONException;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * Business object MonInstance
 */
public class MonInstance extends ObjectDB {
	private static final long serialVersionUID = 1L;

	public void callInstancesOld(){
		//Shouldn't there be a getGrant().getObject("toto", "tutu").resetFilters(); before calling search() ? 
		// get all instances for which date() - lastPollDate() > pollFreq
		search().forEach(row->callSingleInstance(row[getFieldIndex("row_id")],row[getFieldIndex("monInstUrl")]));
	}
	
	public void callInstances(){
		String sql = 
			"select inst.row_id, inst.mon_inst_url, inst.mon_inst_poll_freq, hlth.mon_hea_date"
			+" from mon_instance inst"
			+" left join"
			+" (select hlth.mon_hea_inst_id, max(hlth.mon_hea_date) as mon_hea_date from mon_health hlth group by hlth.mon_hea_inst_id)"
			+" hlth on hlth.mon_hea_inst_id=inst.row_id";
			
		List<String> toUpdate = new ArrayList();
		for(String[] row : Grant.getSystemAdmin().query(sql)){
			// limitDate is the date before which the last health poll triggers a new poll. Defaults to 24H
			String limitDate = Tool.shiftMinutes(Tool.getCurrentDateTime(), -Tool.parseInt(row[2], 1440));
			
			if(Tool.isEmpty(row[3]) || Tool.diffDatetime(limitDate, row[3])<0)
				callSingleInstance(row[0], row[1]);
		}
	}

	public void callInstance(){
		callSingleInstance(getFieldValue("row_id"), getFieldValue("monInstUrl"));
	}

	protected void callSingleInstance(String instanceId, String instanceBaseUrl){
		try {
			createHealthRow(instanceId, instanceBaseUrl);
		}
		catch(Exception e) {
			AppLog.error(getClass(), "callSingleInstance", "Unable to request URL : " + instanceBaseUrl, e, getGrant());
		}
	}

	protected void createHealthRow(String instanceId, String instanceBaseUrl) throws JSONException, IOException {
		boolean[] oldcrud = getGrant().changeAccess("MonHealth", true, true, false, false);
		MonHealth health = MonHealth.getHealth(instanceBaseUrl, getGrant());
		health.setFieldValue("monHeaInstId", instanceId);
		health.create();
		getGrant().changeAccess("MonHealth", oldcrud);
	}
	
	protected MonHealth getLatestHealth(){
		return getLatestHealth(getRowId());
	}
	
	protected MonHealth getLatestHealth(String instanceId) {
		MonHealth health = (MonHealth) getGrant().getObject("latest_MonHealth", "MonHealth");
		String rowId = getGrant().simpleQuery("select row_id from mon_health where mon_hea_inst_id="+instanceId+" order by row_id desc limit 1");
		health.select(rowId);
		return health;
	}
}
