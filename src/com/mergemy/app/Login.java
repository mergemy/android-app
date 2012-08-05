package com.mergemy.app;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.Toast;
import org.json.*;
import com.loopj.android.http.*;
import android.view.View;

public class Login extends Activity {
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
			Main.token_url,
			Main.client_id,
			Main.client_secret,
			username,
			password
		);
		
		// make the request
		System.out.println(url);
		mergeClient.get(url, null, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				
				Intent intent = new Intent(getApplicationContext(), Timeline.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				
				SharedPreferences settings = getSharedPreferences(Main.prefs_name, 0);
				SharedPreferences.Editor editor = settings.edit();
				
				try {
					// le writing prefs
					editor.putString("token", response.getString("access_token"));
					editor.commit();					
					
					System.out.println(response.getString("access_token"));
					
					// aaaand — the timeline!
					startActivity(intent);
					
					finish();
				} catch (JSONException e) {
				}
			}
			@Override
			public void onFailure(Throwable e, JSONObject response) {
				try {
					Toast.makeText(Login.this, String.format("Fails because %s", response.getString("error_description")), Toast.LENGTH_SHORT).show();
				} catch (JSONException ej) {
				}
			}
		});
	}
}
