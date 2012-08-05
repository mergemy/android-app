package com.mergemy.app;

import android.widget.BaseAdapter;
import android.view.LayoutInflater;
import org.json.*;
import android.widget.TextView;
import android.widget.ImageView;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import android.text.Html;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TimelineAdapter extends BaseAdapter {
	Context context;
	JSONArray posts;
	
	public TimelineAdapter(Context _context, JSONArray _posts) {
		context = _context;
		posts = _posts;
	}
	
	@Override public int getCount() {

		return posts.length();
	}

	@Override public JSONObject getItem(int position) {

		return posts.optJSONObject(position);
	}

	@Override public long getItemId(int position) {
		JSONObject jsonObject = getItem(position);

		return jsonObject.optLong("id");
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View res = convertView;
		if (res == null) {
			LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			res = vi.inflate(R.layout.list_item, null);
		}
		
		JSONObject jsonObject = getItem(position);
		
		TextView content = (TextView) res.findViewById(R.id.content);
		TextView author = (TextView) res.findViewById(R.id.author);
		TextView time = (TextView) res.findViewById(R.id.time);
		ImageView avatar = (ImageView) res.findViewById(R.id.avatar);

		try {
			content.setText(Html.fromHtml(jsonObject.get("content").toString()));
			
			// the author
			JSONObject ath = jsonObject.optJSONObject("user");
			author.setText(ath.get("realname").toString());
			
			// the time — http://stackoverflow.com/questions/8034956/unix-timestamp-to-datetime-in-android
			long dv = Long.valueOf(jsonObject.get("time").toString())*1000;
			Date df = new Date(dv);
			String vv = new SimpleDateFormat("kk:mm, d.M").format(df);
			time.setText(vv);
			
			// the avatar image
			ImageLoader imageLoader = ImageLoader.getInstance();
			imageLoader.init(ImageLoaderConfiguration.createDefault(context));
			imageLoader.displayImage(jsonObject.get("user_avatar").toString(), avatar);
		} catch(JSONException e) {
		}

		return res;
	}
}
