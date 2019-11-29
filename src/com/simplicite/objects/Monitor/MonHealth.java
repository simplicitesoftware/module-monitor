package com.simplicite.objects.Monitor;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;
import org.json.JSONObject;

/**
 * Business object MonHealth
 */
public class MonHealth extends ObjectDB {
	private static final long serialVersionUID = 1L;
	public static final HashMap<String, String> corresp = getMapOf(
		"monHeaStatus", "platform.status",
		"monHeaVersion", "platform.version",
		"monHeaBuiltOn", "platform.builton",
		"monHeaAppVersion", "application.applicationversion",
		"monHeaSessions", "application.activesessions",
		"monHeaEnabledUsers", "application.enabledusers",
		"monHeaTotalUsers", "application.totalusers",
		"monHeaFreeDisk", "disk.diskfree",
		"monHeaUsableDisk", "disk.diskusable",
		"monHeaTotalDisk", "disk.disktotal",
		"monHeaFreeHeap", "javavm.heapfree",
		"monHeaHeapSize", "javavm.heapsize",
		"monHeaMaxHeapSize", "javavm.heapmaxsize",
		"monHeaTotalFreeSize", "javavm.totalfreesize",
		"monHeaGrantCache", "cache.grantcache",
		"monHeaMaxGrantCache", "cache.grantcachemax",
		"monHeaObjectCache", "cache.objectcache",
		"monHeaMaxObjectCache", "cache.objectcachemax",
		"monHeaDatabasePatchLevel", "database.dbpatchlevel"
	);
	
	public void feedJson(JSONObject json){
		corresp.forEach((attr, jsonField)->{
			String[] f = jsonField.split("\\.");
			setFieldValue(attr, json.getJSONObject(f[0]).get(f[1]));
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