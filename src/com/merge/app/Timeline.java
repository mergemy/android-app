package com.merge.app;

import android.app.Activity;
import android.os.Bundle;
import android.content.SharedPreferences;
import com.loopj.android.http.*;
import android.widget.ListView;
import org.json.*;


public class Timeline extends Activity {
	ListView lv;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timeline);
		
		lv = (ListView) findViewById(R.id.timeline_list);
		
		makeTimeline();
	}
	
	public void makeTimeline() {		
		SharedPreferences settings = getSharedPreferences(Main.prefs_name, 0);
		
		String url = String.format("https://pixelmerge.me/api/posts/private?grant_type=password&client_id=%s&client_secret=%s&access_token=%s",
			Main.client_id,
			Main.client_secret,
			settings.getString("access_token", "0")
		);
		
		System.out.println(url);
		
		mergeClient.get(url, null, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray response) {
				TimelineAdapter tlAdapter = new TimelineAdapter(getApplicationContext(), response);
				lv.setAdapter(tlAdapter);
			}
		});
		
		
	}
}
