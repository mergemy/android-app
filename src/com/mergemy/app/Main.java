package com.mergemy.app;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;

public class Main extends Activity {
	public final static String client_id = "fcc179b89914026c4185e81ca1f041bacdadb7ef";
	public final static String client_secret = "d09409620da458726a6f6041350681c1a7c1a3e8";
	public final static String token_url = "https://pixelmerge.me/oauth2/access_token";
	public final static String prefs_name = "pixelmerge";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SharedPreferences settings = getSharedPreferences(prefs_name, 0);
		
		if(settings.getString("token", "0") == "0") {
			Intent intent = new Intent(getApplicationContext(), Login.class);
			startActivity(intent);
		} else {
			Intent intent = new Intent(getApplicationContext(), Timeline.class);
			startActivity(intent);
		}
		
		finish();
	}
}
