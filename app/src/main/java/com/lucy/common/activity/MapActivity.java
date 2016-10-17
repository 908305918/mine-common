package com.lucy.common.activity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.lucy.common.R;
import com.lucy.common.view.MapImageView;

public class MapActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_map);
		MapImageView imageView = (MapImageView) findViewById(R.id.map);
		imageView.setDataMap(getMapDataByFileName(this, "ahyd"));
		imageView.setMapAndTextBitmap(
				BitmapFactory.decodeResource(getResources(), R.drawable.ahyd),
				BitmapFactory.decodeResource(getResources(), R.drawable.ahyd_w));
	}

	public Map<String, JSONObject> getMapDataByFileName(Context context, String name) {
		try {
			InputStream in = context.getResources().getAssets().open("map/" + name);
			BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String line = "";
			String result = "";
			while ((line = br.readLine()) != null) {
				result = result + line;
			}
			JSONArray array = new JSONArray(result);
			Map<String, JSONObject> map = new HashMap<String, JSONObject>();
			for (int i = 0; i < array.length(); i++) {
				JSONObject object = array.optJSONObject(i);
				map.put(object.optString("color"), object);
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
