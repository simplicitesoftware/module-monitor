package com.simplicite.objects.Monitor;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.simplicite.util.AppLog;
import com.simplicite.util.Grant;
import com.simplicite.util.ObjectDB;
import com.simplicite.util.Tool;
import com.simplicite.util.exceptions.*;

/**
 * Business object MonHealth
 */
public class MonHealth extends ObjectDB {
	private static final long serialVersionUID = 1L;
	
	private static final String ObjHealth = "MonHealth";
	
	@Override
	public String postCreate() {
		// update parent instance
		boolean[] oldcrud = getGrant().changeAccess("MonInstance", false,true,true,false);
		try{
			ObjectDB inst = getGrant().getTmpObject("MonInstance");
			inst.getTool().getForUpdate(getFieldValue("monHeaInstId"));
			inst.setFieldValue("monInstModelSize", getTshirtSize(getField("monHeaObjects").getDouble(0), 100.0));
			inst.setFieldValue("monInstUserUsage", getTshirtSize(getField("monHeaActiveUsers").getDouble(0), 100.0));
			inst.setFieldValue("monInstMemoryUsage", getTshirtSize(getField("monHeaHeapUsage").getDouble(0), 1.0));
			inst.getTool().validateAndUpdate();
		}
		catch(Exception e){
			AppLog.error("Error updating instance with health data", e, getGrant());
		}
		finally{
			getGrant().changeAccess("MonInstance", oldcrud);
		}
		
		return null;
	}
	
	private String getTshirtSize(double val, double max){
		double ratio = val / max;
		
		if(ratio < 0.2)
			return "XS";
		else if (ratio < 0.4)
			return "S";
		else if (ratio < 0.6)
			return "M";
		else if (ratio < 0.8)
			return "L";
		else
			return "XL";
	}
	
	public static final HashMap<String, String> corresp = getMapOf(
		"monHeaStatus", "platform.status",
		"monHeaVersion", "platform.version",
		"monHeaBuiltOn", "platform.builton",
		"monHeaAppVersion", "application.applicationversion",
		"monHeaSessions", "application.activesessions",
		"monHeaEnabledUsers", "application.enabledusers",
		"monHeaActiveUsers", "application.totalactiveusers",
		"monHeaInactiveUsers", "application.totalinactiveusers",
		"monHeaPendingUsers", "application.totalpendingusers",
		"monHeaWebserviceUsers", "application.totalwebservicesusers",
		"monHeaTotalUsers", "application.totalusers",
		"monHeaLastLogin", "application.lastlogindate",
		"monHeaFreeHeap", "javavm.heapfree",
		"monHeaHeapSize", "javavm.heapsize",
		"monHeaMaxHeapSize", "javavm.heapmaxsize",
		"monHeaTotalFreeSize", "javavm.totalfreesize",
		"monHeaGrantCache", "cache.apigrantcache",
		"monHeaObjectCache", "cache.objectcache",
		"monHeaMaxObjectCache", "cache.objectcachemax",
		"monHeaDatabasePatchLevel", "database.dbpatchlevel",
		"monHeaObjects", "userconfiguration.m_object",
		"monHeaAttributes", "userconfiguration.m_field",
		"monHeaFunctions", "userconfiguration.m_function",
		"monHeaGroups", "userconfiguration.m_group",
		"monHeaStates", "userconfiguration.bpm_state",
		"monHeaConstraints", "userconfiguration.m_constraint",
		"monHeaCrosstables", "userconfiguration.m_crosstab",
		"monHeaTemplates", "userconfiguration.m_template",
		"monHeaScripts", "userconfiguration.m_script",
		"monHeaActions", "userconfiguration.m_action",
		"monHeaPublications", "userconfiguration.m_printtemplate"
	);
	
	public static void createHealthRow(String instanceId, String instanceBaseUrl, Grant g) throws JSONException, ValidateException, CreateException, IOException
	{
		JSONObject req = new JSONObject(Tool.readUrl(instanceBaseUrl+"/health?format=json&full=true"));
		boolean[] oldcrud = g.changeAccess("MonHealth", true, true, false, false);
		
		MonHealth health = (MonHealth) g.getTmpObject(ObjHealth);
		synchronized(health.getLock()){
			health.resetValues();
			health.setFieldValue("monHeaDate", Tool.getCurrentDateTime());
			health.setFieldValue("monHeaInstId", instanceId);
			health.feedJson(req);
			health.populate(false);
			health.getTool().validateAndCreate();
		}
		
		g.changeAccess("MonHealth", oldcrud);
	}

	public boolean hasProblem() {
		return !"OK".equals(getFieldValue("monHeaStatus"));
	}

	private void feedJson(JSONObject json){
		corresp.forEach((attr, jsonField)->{
			String[] f = jsonField.split("\\.");
			try{
				setFieldValue(attr, json.getJSONObject(f[0]).get(f[1]));
			} catch(JSONException e){
				AppLog.warning("Field not found : "+jsonField, null, getGrant());
			}
		});
	}

	// Java 9 Map.of() polyfill
	public static HashMap<String, String> getMapOf(String... args){
		HashMap<String, String> mp = new HashMap<String, String>();
		for(int i=0; i<args.length; i=i+2){
			mp.put(args[i], i+1!=args.length?args[i+1]:null);
		}
		return mp;
	}
}