package org.lukang.demo;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class WuliuInfoUtils {

	public static void getWuliuInfo(String id,String com, HttpListener listener){
		String url = "http://api.ickd.cn/?com="+com+"&nu="+id+"&id=D2BCB3066D4D77EC5567712ECA489FC8&type=json&encode=utf8";
		Log.d("DEBUG", "url: " + url);
		new HttpDownloadAsyncTask(listener).execute(url);
	}
	
	
	/**
	 * 从json解析，获取Book对象
	 * @param jsonStr
	 * @return
	 */
	public static WuliuInfo parseWuliuInfo(String jsonStr){
		
		JSONObject json = null;
		Log.d("DEBUG", "jsonStr: " + jsonStr);
		WuliuInfo wuliuInfo = new WuliuInfo();
		try {
			json = new JSONObject(jsonStr);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(json!=null) {
			wuliuInfo.init(json);
			return wuliuInfo;
		}else{
			return null;
		}
		
	}
	
	/**
	 * @param info
	 * @return
	 */
	public static Set<String> getLocations(WuliuInfo info){
		Set<String> locations = new LinkedHashSet<String>();
		
		for(TrackInfo trackInfo:info.getTrackInfoList()){
			String context  = trackInfo.getContext();
			if(StringUtils.isNotBlank(context)){
				context = StringUtils.substringBeforeLast(context, " ");
				context = StringUtils.substringBeforeLast(context, "分拨中心");
				locations.add(context);
			}
		}
		return locations;
	}

	
}
