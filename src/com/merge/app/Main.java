package com.merge.app;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.Toast;
import org.json.*;
import com.loopj.android.http.*;

public class Main extends Activity {
	public final static String client_id = "fcc179b89914026c4185e81ca1f041bacdadb7ef";
	public final static String client_secret = "d09409620da458726a6f6041350681c1a7c1a3e8";
	public final static String token_url = "https://pixelmerge.me/oauth2/access_token";
	public final static String prefs_name = "pixelmerge";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
	
	public void doLogin(View view) throws JSONException {		
		// get the credentials
		EditText usernameForm = (EditText) findViewById(R.id.username);
		EditText passwordForm = (EditText) findViewById(R.id.password);
		String username = usernameForm.getText().toString();
		String password = passwordForm.getText().toString();
		
		String url = String.format("%s?grant_type=password&client_id=%s&client_secret=%s&username=%s&password=%s&scope=get_private_posts",
			token_url,
			client_id,
			client_secret,
			username,
			password
		);
		
		// make the request
		System.out.println(url);
		mergeClient.get(url, null, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				Intent intent = new Intent(getApplicationContext(), Timeline.class);
				SharedPreferences settings = getSharedPreferences(prefs_name, 0);
				SharedPreferences.Editor editor = settings.edit();
				try {
					// le writing prefs
					editor.putString("access_token", response.getString("access_token"));
					editor.commit();
					
					System.out.println(response.getString("access_token"));
					
					// aaaand — the timeline!
					startActivity(intent);
				} catch (JSONException e) {
				}
			}
			@Override
			public void onFailure(Throwable e, JSONObject response) {
				try {
					Toast.makeText(Main.this, String.format("Fails because %s", response.getString("error_description")), Toast.LENGTH_SHORT).show();
				} catch (JSONException ej) {
				}
			}
		});
	}
}
		
		/*
		AsyncHttpClient auth = new AsyncHttpClient();
		auth.get(String.format("%s?grant_type=password&client_id=%s&client_secret=%s&username=%s&password=%s&scope=get_private_posts",
			token_url,
			client_id,
			client_secret,
			username,
			password), new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(JSONObject response) {
					try {
						// went fine
						if(response.has("access_token")) {
							SharedPreferences settings = getSharedPreferences(prefs_name, 0);
							SharedPreferences.Editor editor = settings.edit();
							editor.putString("access_token", response.getString("access_token"));
							editor.commit();
							
							Toast.makeText(Main.this, "wohoo?", Toast.LENGTH_SHORT).show();
							
							wentFine = true;
						} else if(response.has("error")) {
							Toast.makeText(Main.this, response.getString("description"), Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
					}
				}
			}
		);
		if(wentFine == true) {
			// redirect to the timeline
			Intent intent = new Intent(this, Timeline.class);
			startActivity(intent);
		}
	}
}*/
